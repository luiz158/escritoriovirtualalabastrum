package escritoriovirtualalabastrum.controller;

import java.util.GregorianCalendar;

import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.ValidationMessage;
import escritoriovirtualalabastrum.anotacoes.Funcionalidade;
import escritoriovirtualalabastrum.auxiliar.BonificacaoIngressoAuxiliar;
import escritoriovirtualalabastrum.hibernate.HibernateUtil;
import escritoriovirtualalabastrum.service.BonificacaoIngressoService;
import escritoriovirtualalabastrum.sessao.SessaoUsuario;

@Resource
public class BonificacaoIngressoController {

	private Result result;
	private HibernateUtil hibernateUtil;
	private SessaoUsuario sessaoUsuario;
	private Validator validator;
	private boolean realizarValidacoes = true;

	public BonificacaoIngressoController(Result result, HibernateUtil hibernateUtil, SessaoUsuario sessaoUsuario, Validator validator) {

		this.result = result;
		this.hibernateUtil = hibernateUtil;
		this.sessaoUsuario = sessaoUsuario;
		this.validator = validator;
	}

	@Funcionalidade
	public void gerarRelatorioBonificacaoIngresso(Integer ano, Integer mes) {

		realizarValidacoes(ano, mes);

		BonificacaoIngressoService bonificacaoIngressoService = new BonificacaoIngressoService(hibernateUtil, validator, result);

		BonificacaoIngressoAuxiliar informacoesBonificacoes = bonificacaoIngressoService.calcularBonificacoes(this.sessaoUsuario.getUsuario(), ano, mes);

		result.include("bonificacoes", informacoesBonificacoes.getBonificacoes());
		result.include("somatorioBonificacao", bonificacaoIngressoService.calcularSomatorioBonificacoes(informacoesBonificacoes.getBonificacoes()));

		this.result.include("bonificacoesDiamante", informacoesBonificacoes.getBonificacoesDiamante());
		result.include("somatorioBonificacoesDiamante", bonificacaoIngressoService.calcularSomatorioBonificacoes(informacoesBonificacoes.getBonificacoesDiamante()));

		this.result.include("metaDiamantePontuacao", BonificacaoIngressoService.META_DIAMANTE_PONTUACAO);
		this.result.include("metaDiamanteGraduados", BonificacaoIngressoService.META_DIAMANTE_LINHAS_GRADUADOS);
		this.result.include("pontuacaoAlcancadaPeloDiamante", informacoesBonificacoes.getPontuacaoAlcancadaPeloDiamante());
		this.result.include("graduadosAlcancadosPeloDiamante", informacoesBonificacoes.getGraduadosAlcancadosPeloDiamante());
		this.result.include("graduadosEncontrados", informacoesBonificacoes.getGraduados());
		this.result.include("diamantesComMetasAlcancadas", informacoesBonificacoes.getDiamantesComMetasAlcancadas());

		this.result.include("kitUsuarioLogado", informacoesBonificacoes.getKit());
		this.result.include("porcentagemKitUsuarioLogado", informacoesBonificacoes.getPorcentagem());
		this.result.include("isDiamante", informacoesBonificacoes.getIsDiamante());

		result.include("mesAno", mes + "/" + ano);
	}

	private void realizarValidacoes(Integer ano, Integer mes) {

		if (realizarValidacoes) {

			if (!this.sessaoUsuario.getUsuario().isAtivo()) {

				validator.add(new ValidationMessage("Você não está ativo. Só quem está ativo pode receber bonificação", "Erro"));
				validator.onErrorRedirectTo(ExtratoSimplificadoController.class).acessarTelaExtratoSimplificado(ano, mes);
			}

			if (new GregorianCalendar(ano, mes - 1, 1).before(new GregorianCalendar(2014, 2, 1))) {

				validator.add(new ValidationMessage("Este relatório só passou a existir no escritório virtual a partir de março de 2014", "Erro"));
				validator.onErrorRedirectTo(ExtratoSimplificadoController.class).acessarTelaExtratoSimplificado(ano, mes);
			}
		}
	}

	public void setRealizarValidacoes(boolean realizarValidacoes) {
		this.realizarValidacoes = realizarValidacoes;
	}
}
