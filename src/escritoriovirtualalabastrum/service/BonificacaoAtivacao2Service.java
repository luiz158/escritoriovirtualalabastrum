package escritoriovirtualalabastrum.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.joda.time.DateTime;

import escritoriovirtualalabastrum.auxiliar.MalaDireta;
import escritoriovirtualalabastrum.hibernate.HibernateUtil;
import escritoriovirtualalabastrum.modelo.Usuario;
import escritoriovirtualalabastrum.util.Util;

public class BonificacaoAtivacao2Service {

	public static final Integer QUANTIDADE_GERACOES_QUE_DISTRIBUIRAO_BONUS = 10;
	public static final BigDecimal PORCENTAGEM_COMISSAO_POR_ATIVIDADE = new BigDecimal("4.14");

	private HibernateUtil hibernateUtil;

	public BonificacaoAtivacao2Service(HibernateUtil hibernateUtil) {

		this.hibernateUtil = hibernateUtil;
	}

	public BigDecimal calcularBonificacoes(Usuario usuario, Integer ano, Integer mes) {

		DateTime dataInicial = new DateTime(ano, mes, 1, 0, 0, 0);
		DateTime dataFinal = new DateTime(ano, mes, dataInicial.dayOfMonth().withMaximumValue().dayOfMonth().get(), 0, 0, 0);

		BigDecimal comissao = BigDecimal.ZERO;

		if (usuario.isAtivo(dataInicial.toGregorianCalendar(), dataFinal.toGregorianCalendar()) && new MalaDiretaService(this.hibernateUtil).contarIndicacoes(usuario) >= 3) {

			TreeMap<Integer, MalaDireta> malaDiretaDeAcordoComAtividade = new TreeMap<Integer, MalaDireta>();

			new MalaDiretaService(hibernateUtil).gerarMalaDiretaDeAcordoComAtividade("id_Patroc", usuario.getId_Codigo(), dataInicial.toGregorianCalendar(), dataFinal.toGregorianCalendar(), 1, malaDiretaDeAcordoComAtividade);

			for (Entry<Integer, MalaDireta> malaDireta : malaDiretaDeAcordoComAtividade.entrySet()) {

				Usuario usuarioDaMalaDireta = malaDireta.getValue().getUsuario();

				if (usuarioDaMalaDireta.isAtivo(dataInicial.toGregorianCalendar(), dataFinal.toGregorianCalendar()) && malaDireta.getValue().getNivel() <= QUANTIDADE_GERACOES_QUE_DISTRIBUIRAO_BONUS) {

					BigDecimal valorAtividade = encontrarValorDaAtividade(usuarioDaMalaDireta, dataInicial, dataFinal);

					if (valorAtividade != null) {
						comissao = comissao.add(valorAtividade.multiply(PORCENTAGEM_COMISSAO_POR_ATIVIDADE).divide(new BigDecimal("100")));
					}
				}
			}
		}

		return comissao;
	}

	private BigDecimal encontrarValorDaAtividade(Usuario usuario, DateTime dataInicial, DateTime dataFinal) {

		@SuppressWarnings("unchecked")
		List<Object> kit = hibernateUtil.buscaPorHQL("select pnt.valorAtividade from Pontuacao pnt where id_Codigo = " + usuario.getId_Codigo() + " and Dt_Pontos between '" + dataInicial.toString("YYYY-MM-dd") + "' and '" + dataFinal.toString("YYYY-MM-dd") + "' order by id desc limit 1");

		if (Util.preenchido(kit)) {

			return (BigDecimal) kit.get(0);

		} else {

			return null;
		}
	}
}