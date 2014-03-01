package escritoriovirtualalabastrum.controller;

import java.util.GregorianCalendar;

import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import escritoriovirtualalabastrum.anotacoes.Funcionalidade;
import escritoriovirtualalabastrum.auxiliar.BonificacaoAuxiliar;
import escritoriovirtualalabastrum.hibernate.HibernateUtil;
import escritoriovirtualalabastrum.service.BonificacaoIngressoService;
import escritoriovirtualalabastrum.sessao.SessaoUsuario;

@Resource
public class BonificacaoIngressoController {

	private Result result;
	private HibernateUtil hibernateUtil;
	private SessaoUsuario sessaoUsuario;
	private Validator validator;

	public BonificacaoIngressoController(Result result, HibernateUtil hibernateUtil, SessaoUsuario sessaoUsuario, Validator validator) {

		this.result = result;
		this.hibernateUtil = hibernateUtil;
		this.sessaoUsuario = sessaoUsuario;
		this.validator = validator;
	}

	@Funcionalidade
	public void acessarTelaBonificacao() {

	}

	@Funcionalidade
	public void gerarRelatorioBonificacao(Integer ano, Integer mes) {

		realizarValidacoes(ano, mes);

		BonificacaoIngressoService bonificacaoIngressoService = new BonificacaoIngressoService();
		bonificacaoIngressoService.setHibernateUtil(hibernateUtil);
		bonificacaoIngressoService.setResult(result);

		BonificacaoAuxiliar informacoesBonificacoes = bonificacaoIngressoService.calcularBonificacoes(this.sessaoUsuario.getUsuario(), ano, mes);

		result.include("bonificacoes", informacoesBonificacoes.getBonificacoes());
		result.include("somatorioBonificacao", bonificacaoIngressoService.calcularSomatorioBonificacoes(informacoesBonificacoes.getBonificacoes()));

		this.result.include("bonificacoesDiamante", informacoesBonificacoes.getBonificacoesDiamante());
		result.include("somatorioBonificacoesDiamante", bonificacaoIngressoService.calcularSomatorioBonificacoes(informacoesBonificacoes.getBonificacoesDiamante()));

		this.result.include("metaDiamantePontuacao", PontuacaoController.META_DIAMANTE_PONTUACAO);
		this.result.include("metaDiamanteGraduados", PontuacaoController.META_DIAMANTE_LINHAS_GRADUADOS);
		this.result.include("pontuacaoAlcancadaPeloDiamante", informacoesBonificacoes.getPontuacaoAlcancadaPeloDiamante());
		this.result.include("graduadosAlcancadosPeloDiamante", informacoesBonificacoes.getGraduadosAlcancadosPeloDiamante());

		this.result.include("kitUsuarioLogado", informacoesBonificacoes.getKit());
		this.result.include("porcentagemKitUsuarioLogado", informacoesBonificacoes.getPorcentagem());
		this.result.include("isDiamante", informacoesBonificacoes.getIsDiamante());

		result.include("mesAno", mes + "/" + ano);
	}

	private void realizarValidacoes(Integer ano, Integer mes) {

		if (!this.sessaoUsuario.getUsuario().isAtivo()) {

			// validator.add(new
			// ValidationMessage("Você não está ativo. Só quem está ativo pode receber bonificação",
			// "Erro"));
			// validator.onErrorRedirectTo(this).acessarTelaBonificacao();
		}

		if (new GregorianCalendar(ano, mes - 1, 1).before(new GregorianCalendar(2014, 2, 1))) {

			// validator.add(new
			// ValidationMessage("Este relatório só passou a existir no escritório virtual a partir de março de 2014",
			// "Erro"));
			// validator.onErrorRedirectTo(this).acessarTelaBonificacao();
		}
	}
}
