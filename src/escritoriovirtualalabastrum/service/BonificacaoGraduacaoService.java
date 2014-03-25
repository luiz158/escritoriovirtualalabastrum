package escritoriovirtualalabastrum.service;

import java.math.BigDecimal;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.joda.time.DateTime;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import escritoriovirtualalabastrum.auxiliar.MalaDireta;
import escritoriovirtualalabastrum.controller.PontuacaoController;
import escritoriovirtualalabastrum.hibernate.HibernateUtil;
import escritoriovirtualalabastrum.modelo.Usuario;
import escritoriovirtualalabastrum.sessao.SessaoUsuario;

public class BonificacaoGraduacaoService {

	private HibernateUtil hibernateUtil;
	private Validator validator;
	private Result result;

	public BonificacaoGraduacaoService(HibernateUtil hibernateUtil, Validator validator, Result result) {

		this.hibernateUtil = hibernateUtil;
		this.validator = validator;
		this.result = result;
	}

	public void calcularBonificacoes(Usuario usuario, Integer ano, Integer mes, TreeMap<Integer, MalaDireta> malaDireta) {

		DateTime dataInicial = new DateTime(ano, mes, 1, 0, 0, 0);
		DateTime dataFinal = new DateTime(ano, mes, dataInicial.dayOfMonth().withMaximumValue().dayOfMonth().get(), 0, 0, 0);

		for (Entry<Integer, MalaDireta> malaDiretaEntry : malaDireta.entrySet()) {

			MalaDiretaService malaDiretaService = new MalaDiretaService();
			malaDiretaService.setHibernateUtil(hibernateUtil);
			malaDiretaService.setValidator(validator);

			TreeMap<Integer, MalaDireta> malaDiretaAbaixo = malaDiretaService.gerarMalaDireta("id_Patroc", malaDiretaEntry.getValue().getUsuario().getId_Codigo(), malaDiretaEntry.getValue().getUsuario().getId_Codigo());

			if (GraduacaoService.GRADUACOES.contains(malaDiretaEntry.getValue().getUsuario().getPosAtual().toLowerCase())) {

				SessaoUsuario sessaoUsuario = new SessaoUsuario();
				sessaoUsuario.login(malaDiretaEntry.getValue().getUsuario());

				BigDecimal pontuacao = new PontuacaoController(result, hibernateUtil, sessaoUsuario, validator).gerarRelatorioPontuacaoRetornandoPontuacaoDaRede(dataInicial.toGregorianCalendar(), dataFinal.toGregorianCalendar(), malaDiretaAbaixo, PontuacaoController.TODOS, PontuacaoController.TODOS, malaDiretaEntry.getValue().getUsuario().getId_Codigo());
			}
		}
	}
}