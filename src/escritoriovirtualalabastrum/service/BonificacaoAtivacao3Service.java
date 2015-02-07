package escritoriovirtualalabastrum.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;

import escritoriovirtualalabastrum.auxiliar.MalaDireta;
import escritoriovirtualalabastrum.hibernate.HibernateUtil;
import escritoriovirtualalabastrum.modelo.Atividade;
import escritoriovirtualalabastrum.modelo.Pontuacao;
import escritoriovirtualalabastrum.modelo.Usuario;

public class BonificacaoAtivacao3Service {

	private HibernateUtil hibernateUtil;

	public BonificacaoAtivacao3Service(HibernateUtil hibernateUtil) {

		this.hibernateUtil = hibernateUtil;
	}

	public BigDecimal calcularBonificacoes(Usuario usuario, Integer ano, Integer mes) {

		BigDecimal bonificacao = BigDecimal.ZERO;

		DateTime dataInicial = new DateTime(ano, mes, 1, 0, 0, 0);
		DateTime dataFinal = new DateTime(ano, mes, dataInicial.dayOfMonth().withMaximumValue().dayOfMonth().get(), 0, 0, 0);

		if (usuario.isAtivo(dataInicial.toGregorianCalendar(), dataFinal.toGregorianCalendar()) && new MalaDiretaService(this.hibernateUtil).contarIndicacoes(usuario) >= 3) {

			TreeMap<Integer, MalaDireta> malaDiretaCompleta = new TreeMap<Integer, MalaDireta>();

			MalaDiretaService malaDiretaService = new MalaDiretaService(hibernateUtil);
			malaDiretaService.setHibernateUtil(hibernateUtil);
			malaDiretaService.gerarMalaDiretaDeAcordoComAtividade("id_Patroc", usuario.getId_Codigo(), dataInicial.toGregorianCalendar(), dataFinal.toGregorianCalendar(), 1, malaDiretaCompleta);

			for (Entry<Integer, MalaDireta> malaDiretaEntry : malaDiretaCompleta.entrySet()) {

				MalaDireta malaDireta = malaDiretaEntry.getValue();

				Usuario usuarioMalaDireta = malaDireta.getUsuario();

				if (malaDireta.getNivel() <= 10 && usuarioMalaDireta.isAtivo(dataInicial.toGregorianCalendar(), dataFinal.toGregorianCalendar())) {

					List<Criterion> restricoes = new ArrayList<Criterion>();
					restricoes.add(Restrictions.between("Dt_Pontos", dataInicial.toGregorianCalendar(), dataFinal.toGregorianCalendar()));

					Pontuacao pontuacaoFiltro = new Pontuacao();
					pontuacaoFiltro.setId_Codigo(usuarioMalaDireta.getId_Codigo());

					BigDecimal valorAtividade = this.hibernateUtil.somar(pontuacaoFiltro, restricoes, null, "valorAtividade");

					Atividade atividadeFiltro = new Atividade();
					atividadeFiltro.setValorAtividade(valorAtividade);
					atividadeFiltro.setData_referencia(dataInicial.toGregorianCalendar());

					Atividade atividade = this.hibernateUtil.selecionar(atividadeFiltro);

					if (atividade != null) {

						bonificacao = bonificacao.add(atividade.getBonusAtividade());
					}
				}
			}
		}

		return bonificacao;
	}
}