package escritoriovirtualalabastrum.controller;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import org.joda.time.DateTime;

import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.ValidationMessage;
import escritoriovirtualalabastrum.anotacoes.Funcionalidade;
import escritoriovirtualalabastrum.auxiliar.BonificacaoAtivacaoAuxiliar;
import escritoriovirtualalabastrum.auxiliar.BonificacaoCompraPessoalAuxiliar;
import escritoriovirtualalabastrum.auxiliar.BonificacaoIngressoAuxiliar;
import escritoriovirtualalabastrum.auxiliar.ExtratoSimplificadoAuxiliar;
import escritoriovirtualalabastrum.auxiliar.MalaDireta;
import escritoriovirtualalabastrum.hibernate.HibernateUtil;
import escritoriovirtualalabastrum.service.BonificacaoAtivacaoService;
import escritoriovirtualalabastrum.service.BonificacaoCompraPessoalService;
import escritoriovirtualalabastrum.service.BonificacaoGraduacaoService;
import escritoriovirtualalabastrum.service.BonificacaoIngressoService;
import escritoriovirtualalabastrum.service.MalaDiretaService;
import escritoriovirtualalabastrum.sessao.SessaoBonificacao;
import escritoriovirtualalabastrum.sessao.SessaoUsuario;
import escritoriovirtualalabastrum.util.Util;

@Resource
public class ExtratoSimplificadoController {

	private Result result;
	private HibernateUtil hibernateUtil;
	private SessaoUsuario sessaoUsuario;
	private SessaoBonificacao sessaoBonificacao;
	private Validator validator;
	private boolean realizarValidacoes = true;

	public ExtratoSimplificadoController(Result result, HibernateUtil hibernateUtil, SessaoUsuario sessaoUsuario, SessaoBonificacao sessaoBonificacao, Validator validator) {

		this.result = result;
		this.hibernateUtil = hibernateUtil;
		this.sessaoUsuario = sessaoUsuario;
		this.sessaoBonificacao = sessaoBonificacao;
		this.validator = validator;
	}

	@Funcionalidade
	public void acessarTelaExtratoSimplificado(Integer ano, Integer mes) {

	}

	@Funcionalidade
	public void gerarExtratoSimplificado(Integer ano, Integer mes) {

		realizarValidacoes(ano, mes);

		BonificacaoIngressoService bonificacaoIngressoService = new BonificacaoIngressoService(hibernateUtil, validator, result);
		BonificacaoIngressoAuxiliar informacoesBonificacoesIngresso = bonificacaoIngressoService.calcularBonificacoes(this.sessaoUsuario.getUsuario(), ano, mes);
		result.include("bonificacaoIngresso", bonificacaoIngressoService.calcularSomatorioBonificacoes(informacoesBonificacoesIngresso.getBonificacoes()).add(bonificacaoIngressoService.calcularSomatorioBonificacoes(informacoesBonificacoesIngresso.getBonificacoesDiamante())));

		TreeMap<Integer, MalaDireta> malaDireta = informacoesBonificacoesIngresso.getMalaDireta();

		BonificacaoAtivacaoService bonificacaoAtivacaoService = new BonificacaoAtivacaoService(hibernateUtil, result, validator);
		List<BonificacaoAtivacaoAuxiliar> bonificacoesAtivacao = bonificacaoAtivacaoService.calcularBonificacoes(this.sessaoUsuario.getUsuario(), ano, mes);
		result.include("bonificacaoAtivacao", bonificacaoAtivacaoService.calcularSomatorioBonificacoes(bonificacoesAtivacao));

		ExtratoSimplificadoAuxiliar extratoSimplificadoAuxiliar = calcularPontuacaoEGraduados(ano, mes, informacoesBonificacoesIngresso);
		result.include("pontuacao", extratoSimplificadoAuxiliar.getPontuacao());
		result.include("quantidadeGraduados", extratoSimplificadoAuxiliar.getQuantidadeGraduados());

		BonificacaoCompraPessoalAuxiliar bonificacaoCompraPessoal = new BonificacaoCompraPessoalService(hibernateUtil).calcularBonificacoes(this.sessaoUsuario.getUsuario(), ano, mes, extratoSimplificadoAuxiliar.getPontuacao(), extratoSimplificadoAuxiliar.getQuantidadeGraduados());
		result.include("bonificacaoCompraPessoal", bonificacaoCompraPessoal.getBonificacao());
		result.include("graduacaoAtual", bonificacaoCompraPessoal.getGraduacao());
		result.include("porcentagemCompraPessoal", bonificacaoCompraPessoal.getPorcentagem());

		result.include("bonificacaoGraduacao", new BonificacaoGraduacaoService(hibernateUtil, validator, result).calcularBonificacoes(sessaoUsuario.getUsuario(), ano, mes, extratoSimplificadoAuxiliar.getPontuacao(), extratoSimplificadoAuxiliar.getQuantidadeGraduados(), malaDireta));

		sessaoBonificacao.setMalaDireta(malaDireta);
		sessaoBonificacao.setExtratoSimplificadoAuxiliar(extratoSimplificadoAuxiliar);

		result.include("mes", mes);
		result.include("ano", ano);
	}

	public ExtratoSimplificadoAuxiliar calcularPontuacaoEGraduados(Integer ano, Integer mes, BonificacaoIngressoAuxiliar informacoesBonificacoesIngresso) {

		ExtratoSimplificadoAuxiliar extratoSimplificadoAuxiliar = new ExtratoSimplificadoAuxiliar();

		if (Util.preenchido(informacoesBonificacoesIngresso.getPontuacaoAlcancadaPeloDiamante()) && Util.preenchido(informacoesBonificacoesIngresso.getGraduadosAlcancadosPeloDiamante())) {

			extratoSimplificadoAuxiliar.setPontuacao(informacoesBonificacoesIngresso.getPontuacaoAlcancadaPeloDiamante());
			extratoSimplificadoAuxiliar.setQuantidadeGraduados(informacoesBonificacoesIngresso.getGraduadosAlcancadosPeloDiamante());

		} else {

			DateTime dataInicial = new DateTime(ano, mes, 1, 0, 0, 0);
			DateTime dataFinal = new DateTime(ano, mes, dataInicial.dayOfMonth().withMaximumValue().dayOfMonth().get(), 0, 0, 0);

			SessaoUsuario sessaoUsuario = new SessaoUsuario();
			sessaoUsuario.login(this.sessaoUsuario.getUsuario());
			PontuacaoController pontuacaoController = new PontuacaoController(result, hibernateUtil, sessaoUsuario, validator);
			BonificacaoIngressoAuxiliar bonificacaoAuxiliar = pontuacaoController.gerarMalaDiretaECalcularPontuacaoDaRede(MalaDiretaService.TODAS, sessaoUsuario.getUsuario().getId_Codigo(), dataInicial.toGregorianCalendar(), dataFinal.toGregorianCalendar(), PontuacaoController.TODOS, PontuacaoController.TODOS, sessaoUsuario.getUsuario().getId_Codigo());

			Integer quantidadeGraduados = new MalaDiretaService().calcularQuantidadeGraduados(this.sessaoUsuario.getUsuario(), result, hibernateUtil, validator, dataInicial, dataFinal, new HashMap<Integer, MalaDireta>());

			extratoSimplificadoAuxiliar.setPontuacao(bonificacaoAuxiliar.getPontuacaoDiamante());
			extratoSimplificadoAuxiliar.setQuantidadeGraduados(quantidadeGraduados);
		}

		return extratoSimplificadoAuxiliar;
	}

	private void realizarValidacoes(Integer ano, Integer mes) {

		if (realizarValidacoes) {

			if (!this.sessaoUsuario.getUsuario().isAtivo()) {

				validator.add(new ValidationMessage("Você não está ativo. Só quem está ativo pode receber bonificação", "Erro"));
				validator.onErrorRedirectTo(this).acessarTelaExtratoSimplificado(ano, mes);
			}

			if (new GregorianCalendar(ano, mes - 1, 1).before(new GregorianCalendar(2014, 2, 1))) {

				validator.add(new ValidationMessage("Este relatório só passou a existir no escritório virtual a partir de março de 2014", "Erro"));
				validator.onErrorRedirectTo(this).acessarTelaExtratoSimplificado(ano, mes);
			}
		}
	}

	public void setRealizarValidacoes(boolean realizarValidacoes) {
		this.realizarValidacoes = realizarValidacoes;
	}
}
