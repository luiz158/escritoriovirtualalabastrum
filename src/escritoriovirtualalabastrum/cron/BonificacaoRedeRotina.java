package escritoriovirtualalabastrum.cron;

import it.sauronsoftware.cron4j.Scheduler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.joda.time.DateTime;
import org.joda.time.Minutes;

import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;
import escritoriovirtualalabastrum.auxiliar.BonificacaoAtivacaoAuxiliar;
import escritoriovirtualalabastrum.auxiliar.BonificacaoIngressoAuxiliar;
import escritoriovirtualalabastrum.auxiliar.ExtratoSimplificadoAuxiliar;
import escritoriovirtualalabastrum.auxiliar.MalaDireta;
import escritoriovirtualalabastrum.controller.ExtratoSimplificadoController;
import escritoriovirtualalabastrum.hibernate.HibernateUtil;
import escritoriovirtualalabastrum.modelo.BonificacaoRede;
import escritoriovirtualalabastrum.modelo.Configuracao;
import escritoriovirtualalabastrum.modelo.InformacoesGeraisCalculoBonificacaoRede;
import escritoriovirtualalabastrum.modelo.Usuario;
import escritoriovirtualalabastrum.service.BonificacaoAtivacaoService;
import escritoriovirtualalabastrum.service.BonificacaoCompraPessoalService;
import escritoriovirtualalabastrum.service.BonificacaoGraduacaoService;
import escritoriovirtualalabastrum.service.BonificacaoIngressoService;
import escritoriovirtualalabastrum.service.GraduacaoService;
import escritoriovirtualalabastrum.sessao.SessaoBonificacao;
import escritoriovirtualalabastrum.sessao.SessaoUsuario;
import escritoriovirtualalabastrum.util.Util;

public class BonificacaoRedeRotina implements Runnable {

	private static Scheduler scheduler = new Scheduler();
	private static String idScheduler;
	private HibernateUtil hibernateUtil;

	public void run() {

		DateTime dataInicioCalculo = new DateTime();

		Integer ano = Integer.valueOf(new Configuracao().retornarConfiguracao("anoRotinaBonificacoes"));
		Integer mes = Integer.valueOf(new Configuracao().retornarConfiguracao("mesRotinaBonificacoes"));

		DateTime dataInicial = new DateTime(ano, mes, 1, 0, 0, 0);
		DateTime dataFinal = new DateTime(ano, mes, dataInicial.dayOfMonth().withMaximumValue().dayOfMonth().get(), 0, 0, 0);

		HibernateUtil hibernateUtil = new HibernateUtil();
		this.hibernateUtil = hibernateUtil;

		List<Usuario> usuarios = hibernateUtil.buscar(new Usuario());

		List<BonificacaoRede> bonificacoesDaRede = new ArrayList<BonificacaoRede>();

		for (Usuario usuario : usuarios) {

			if (usuario.getId_Codigo() != 1 && usuario.getId_Codigo() != 2 && usuario.isAtivo(dataInicial.toGregorianCalendar(), dataFinal.toGregorianCalendar())) {

				BonificacaoRede bonificacoes = calcularBonificacoes(usuario, ano, mes);

				if (bonificacoes.getTotal().compareTo(BigDecimal.ZERO) > 0) {

					bonificacoesDaRede.add(bonificacoes);
				}
			}
		}

		hibernateUtil.executarSQL("delete from informacoesgeraiscalculobonificacaorede");
		hibernateUtil.executarSQL("delete from bonificacaorede");

		hibernateUtil.salvarOuAtualizar(bonificacoesDaRede);

		InformacoesGeraisCalculoBonificacaoRede informacoesGeraisCalculoBonificacaoRede = new InformacoesGeraisCalculoBonificacaoRede();
		informacoesGeraisCalculoBonificacaoRede.setAno(ano);
		informacoesGeraisCalculoBonificacaoRede.setMes(mes);
		informacoesGeraisCalculoBonificacaoRede.setDataHoraCalculo(dataInicioCalculo.toGregorianCalendar());
		informacoesGeraisCalculoBonificacaoRede.setTempoDeProcessamentoRelatorio(Minutes.minutesBetween(dataInicioCalculo, new DateTime()).getMinutes());

		hibernateUtil.salvarOuAtualizar(informacoesGeraisCalculoBonificacaoRede);

		hibernateUtil.fecharSessao();
	}

	private BonificacaoRede calcularBonificacoes(Usuario usuario, Integer ano, Integer mes) {

		MockValidator validator = new MockValidator();
		MockResult result = new MockResult();
		SessaoUsuario sessaoUsuario = new SessaoUsuario();
		sessaoUsuario.login(usuario);

		BonificacaoIngressoService bonificacaoIngressoService = new BonificacaoIngressoService(hibernateUtil, validator, result);
		BonificacaoIngressoAuxiliar informacoesBonificacoesIngresso = bonificacaoIngressoService.calcularBonificacoes(sessaoUsuario.getUsuario(), ano, mes);
		BigDecimal bonificacaoIngresso = bonificacaoIngressoService.calcularSomatorioBonificacoes(informacoesBonificacoesIngresso.getBonificacoes()).add(bonificacaoIngressoService.calcularSomatorioBonificacoes(informacoesBonificacoesIngresso.getBonificacoesDiamante()));

		TreeMap<Integer, MalaDireta> malaDireta = informacoesBonificacoesIngresso.getMalaDireta();

		BonificacaoAtivacaoService bonificacaoAtivacaoService = new BonificacaoAtivacaoService(hibernateUtil, result, validator);
		List<BonificacaoAtivacaoAuxiliar> bonificacoesAtivacao = bonificacaoAtivacaoService.calcularBonificacoes(usuario, ano, mes);
		BigDecimal bonificacaoAtivacao = bonificacaoAtivacaoService.calcularSomatorioBonificacoes(bonificacoesAtivacao);

		ExtratoSimplificadoAuxiliar extratoSimplificadoAuxiliar = new ExtratoSimplificadoController(result, hibernateUtil, sessaoUsuario, new SessaoBonificacao(), validator).calcularPontuacaoEGraduados(ano, mes, informacoesBonificacoesIngresso);

		String graduacao = new GraduacaoService().verificaGraduacao(extratoSimplificadoAuxiliar.getPontuacao(), extratoSimplificadoAuxiliar.getQuantidadeGraduados());
		if (graduacao == null) {
			graduacao = "Executivo";
		}

		BigDecimal bonificacaoCompraPessoal = new BonificacaoCompraPessoalService(hibernateUtil).calcularBonificacoes(sessaoUsuario.getUsuario(), ano, mes, extratoSimplificadoAuxiliar.getPontuacao(), extratoSimplificadoAuxiliar.getQuantidadeGraduados()).getBonificacao();

		BigDecimal bonificacaoGraduacao = new BonificacaoGraduacaoService(hibernateUtil, validator, result).calcularBonificacoes(sessaoUsuario.getUsuario(), ano, mes, extratoSimplificadoAuxiliar.getPontuacao(), extratoSimplificadoAuxiliar.getQuantidadeGraduados(), malaDireta).getBonificacao();
		if (Util.vazio(bonificacaoGraduacao)) {
			bonificacaoGraduacao = BigDecimal.ZERO;
		}

		BonificacaoRede bonificacaoRede = new BonificacaoRede();
		bonificacaoRede.setId_Codigo(usuario.getId_Codigo());
		bonificacaoRede.setQualificacao(graduacao);
		bonificacaoRede.setBonificacaoIngresso(bonificacaoIngresso);
		bonificacaoRede.setBonificacaoAtivacao(bonificacaoAtivacao);
		bonificacaoRede.setBonificacaoCompraPessoal(bonificacaoCompraPessoal);
		bonificacaoRede.setBonificacaoGraduacao(bonificacaoGraduacao);

		bonificacaoRede.setTotal(bonificacaoIngresso.add(bonificacaoAtivacao).add(bonificacaoCompraPessoal).add(bonificacaoGraduacao));

		return bonificacaoRede;
	}

	public void iniciarRotina() {

		String horario = new Configuracao().retornarConfiguracao("horarioRotinaBonificacoes");

		String schedulerString = horario.split(":")[1] + " " + horario.split(":")[0] + " * * *";

		if (Util.preenchido(idScheduler)) {

			scheduler.reschedule(idScheduler, schedulerString);
		}

		else {

			idScheduler = scheduler.schedule(schedulerString, this);
		}

		if (!scheduler.isStarted()) {

			scheduler.start();
		}
	}
}
