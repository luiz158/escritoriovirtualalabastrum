package escritoriovirtualalabastrum.cron;

import it.sauronsoftware.cron4j.Scheduler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Minutes;

import escritoriovirtualalabastrum.hibernate.HibernateUtil;
import escritoriovirtualalabastrum.modelo.BonificacaoRede;
import escritoriovirtualalabastrum.modelo.Configuracao;
import escritoriovirtualalabastrum.modelo.InformacoesGeraisCalculoBonificacaoRede;
import escritoriovirtualalabastrum.modelo.Usuario;
import escritoriovirtualalabastrum.service.BonificacaoAtivacao3Service;
import escritoriovirtualalabastrum.service.BonificacaoDivisaoService;
import escritoriovirtualalabastrum.service.BonificacaoExpansaoService;
import escritoriovirtualalabastrum.service.BonificacaoInicioRapidoService;
import escritoriovirtualalabastrum.service.BonificacaoPontoDeApoioService;
import escritoriovirtualalabastrum.service.BonificacaoUniLevelService;
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

		HibernateUtil hibernateUtil = new HibernateUtil();
		this.hibernateUtil = hibernateUtil;

		List<Usuario> usuarios = hibernateUtil.buscar(new Usuario());

		List<BonificacaoRede> bonificacoesDaRede = new ArrayList<BonificacaoRede>();

		for (Usuario usuario : usuarios) {

			BonificacaoRede bonificacoes = calcularBonificacoes(usuario, ano, mes);

			if (bonificacoes.getTotal().compareTo(BigDecimal.ZERO) > 0) {

				bonificacoesDaRede.add(bonificacoes);
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

		// MockValidator validator = new MockValidator();
		// MockResult result = new MockResult();
		// BonificacaoIngressoService bonificacaoIngressoService = new
		// BonificacaoIngressoService(hibernateUtil, validator, result);
		// BonificacaoIngressoAuxiliar informacoesBonificacoesIngresso =
		// bonificacaoIngressoService.calcularBonificacoes(sessaoUsuario.getUsuario(),
		// ano, mes);
		// BigDecimal bonificacaoIngresso =
		// bonificacaoIngressoService.calcularSomatorioBonificacoes(informacoesBonificacoesIngresso.getBonificacoes()).add(bonificacaoIngressoService.calcularSomatorioBonificacoes(informacoesBonificacoesIngresso.getBonificacoesDiamante()));
		//
		// TreeMap<Integer, MalaDireta> malaDireta =
		// informacoesBonificacoesIngresso.getMalaDireta();
		//
		// BonificacaoAtivacaoService bonificacaoAtivacaoService = new
		// BonificacaoAtivacaoService(hibernateUtil, result, validator);
		// List<BonificacaoAtivacaoAuxiliar> bonificacoesAtivacao =
		// bonificacaoAtivacaoService.calcularBonificacoes(usuario, ano, mes);
		// BigDecimal bonificacaoAtivacao =
		// bonificacaoAtivacaoService.calcularSomatorioBonificacoes(bonificacoesAtivacao);
		//
		// ExtratoSimplificadoAuxiliar extratoSimplificadoAuxiliar = new
		// ExtratoSimplificadoController(result, hibernateUtil, sessaoUsuario,
		// new SessaoBonificacao(), validator).calcularPontuacaoEGraduados(ano,
		// mes, informacoesBonificacoesIngresso);
		//
		// String graduacao = new
		// GraduacaoService().verificaGraduacao(extratoSimplificadoAuxiliar.getPontuacao(),
		// extratoSimplificadoAuxiliar.getQuantidadeGraduados());
		// if (graduacao == null) {
		// graduacao = "Executivo";
		// }
		//
		// BigDecimal bonificacaoCompraPessoal = new
		// BonificacaoCompraPessoalService(hibernateUtil).calcularBonificacoes(sessaoUsuario.getUsuario(),
		// ano, mes, extratoSimplificadoAuxiliar.getPontuacao(),
		// extratoSimplificadoAuxiliar.getQuantidadeGraduados()).getBonificacao();
		//
		// BigDecimal bonificacaoGraduacao = new
		// BonificacaoGraduacaoService(hibernateUtil, validator,
		// result).calcularBonificacoes(sessaoUsuario.getUsuario(), ano, mes,
		// extratoSimplificadoAuxiliar.getPontuacao(),
		// extratoSimplificadoAuxiliar.getQuantidadeGraduados(),
		// malaDireta).getBonificacao();
		// if (Util.vazio(bonificacaoGraduacao)) {
		// bonificacaoGraduacao = BigDecimal.ZERO;
		// }

		// BigDecimal bonificacaoAtivacao2 = new
		// BonificacaoAtivacao2Service(hibernateUtil).calcularBonificacoes(sessaoUsuario.getUsuario(),
		// ano, mes);

		SessaoUsuario sessaoUsuario = new SessaoUsuario();
		sessaoUsuario.login(usuario);

		BigDecimal bonificacaoInicioRapido = new BonificacaoInicioRapidoService(hibernateUtil).calcularBonificacao(sessaoUsuario.getUsuario(), ano, mes);
		BigDecimal bonificacaoUniLevel = new BonificacaoUniLevelService(hibernateUtil).calcularBonificacoes(sessaoUsuario.getUsuario(), ano, mes);
		BigDecimal bonificacaoExpansao = new BonificacaoExpansaoService(hibernateUtil).calcularBonificacoes(sessaoUsuario.getUsuario(), ano, mes);
		BigDecimal bonificacaoAtivacao3 = new BonificacaoAtivacao3Service(hibernateUtil).calcularBonificacoes(sessaoUsuario.getUsuario(), ano, mes);
		BigDecimal bonificacaoPontoDeApoio = new BonificacaoPontoDeApoioService(hibernateUtil).calcularBonificacoes(sessaoUsuario.getUsuario(), ano, mes);
		BigDecimal bonificacaoDivisao = new BonificacaoDivisaoService(hibernateUtil).calcularBonificacoes(sessaoUsuario.getUsuario(), ano, mes);

		BonificacaoRede bonificacaoRede = new BonificacaoRede();
		bonificacaoRede.setId_Codigo(usuario.getId_Codigo());
		bonificacaoRede.setBonificacaoAtivacao3(bonificacaoAtivacao3);
		bonificacaoRede.setBonificacaoInicioRapido(bonificacaoInicioRapido);
		bonificacaoRede.setBonificacaoUniLevel(bonificacaoUniLevel);
		bonificacaoRede.setBonificacaoExpansao(bonificacaoExpansao);
		bonificacaoRede.setBonificacaoPontoDeApoio(bonificacaoPontoDeApoio);
		bonificacaoRede.setBonificacaoDivisao(bonificacaoDivisao);

		bonificacaoRede.setTotal(bonificacaoAtivacao3.add(bonificacaoInicioRapido.add(bonificacaoUniLevel.add(bonificacaoExpansao.add(bonificacaoPontoDeApoio.add(bonificacaoDivisao))))));

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
