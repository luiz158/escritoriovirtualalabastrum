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

public class BonificacaoInicioRapidoService {

	public static BigDecimal PORCENTAGEM_COMISSAO = new BigDecimal("10");

	private HibernateUtil hibernateUtil;

	public BonificacaoInicioRapidoService(HibernateUtil hibernateUtil) {

		this.hibernateUtil = hibernateUtil;
	}

	public BigDecimal calcularBonificacao(Usuario usuario, Integer ano, Integer mes) {

		DateTime dataInicial = new DateTime(ano, mes, 1, 0, 0, 0);
		DateTime dataFinal = new DateTime(ano, mes, dataInicial.dayOfMonth().withMaximumValue().dayOfMonth().get(), 0, 0, 0);

		MalaDiretaService malaDiretaService = new MalaDiretaService(hibernateUtil);
		malaDiretaService.setHibernateUtil(hibernateUtil);
		TreeMap<Integer, MalaDireta> malaDiretaHash = malaDiretaService.gerarMalaDireta("id_Indicante", usuario.getId_Codigo(), usuario.getId_Codigo());

		BigDecimal comissao = BigDecimal.ZERO;

		for (Entry<Integer, MalaDireta> malaDiretaEntry : malaDiretaHash.entrySet()) {

			MalaDireta malaDireta = malaDiretaEntry.getValue();

			Usuario usuarioMalaDireta = malaDireta.getUsuario();

			if ((usuarioMalaDireta.getDt_Ingresso().equals(dataInicial.toGregorianCalendar()) || usuarioMalaDireta.getDt_Ingresso().after(dataInicial.toGregorianCalendar())) && (usuarioMalaDireta.getDt_Ingresso().equals(dataFinal.toGregorianCalendar()) || usuarioMalaDireta.getDt_Ingresso().before(dataFinal.toGregorianCalendar()))) {

				BigDecimal valorDoIngresso = encontrarValorDoIngresso(usuarioMalaDireta, dataInicial, dataFinal);

				if (valorDoIngresso != null) {
					comissao = comissao.add(valorDoIngresso.multiply(PORCENTAGEM_COMISSAO).divide(new BigDecimal("100")));
				}
			}
		}

		return comissao;
	}

	private BigDecimal encontrarValorDoIngresso(Usuario usuario, DateTime dataInicial, DateTime dataFinal) {

		@SuppressWarnings("unchecked")
		List<Object> kit = hibernateUtil.buscaPorHQL("select pnt.valorIngresso from Pontuacao pnt where id_Codigo = " + usuario.getId_Codigo() + " and Dt_Pontos between '" + dataInicial.toString("YYYY-MM-dd") + "' and '" + dataFinal.toString("YYYY-MM-dd") + "' order by id desc limit 1");

		if (Util.preenchido(kit)) {

			return (BigDecimal) kit.get(0);

		} else {

			return null;
		}
	}
}