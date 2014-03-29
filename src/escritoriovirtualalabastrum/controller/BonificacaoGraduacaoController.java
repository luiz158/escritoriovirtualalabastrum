package escritoriovirtualalabastrum.controller;

import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import escritoriovirtualalabastrum.anotacoes.Funcionalidade;
import escritoriovirtualalabastrum.auxiliar.BonificacaoGraduacaoAuxiliar;
import escritoriovirtualalabastrum.hibernate.HibernateUtil;
import escritoriovirtualalabastrum.service.BonificacaoGraduacaoService;
import escritoriovirtualalabastrum.sessao.SessaoBonificacao;
import escritoriovirtualalabastrum.sessao.SessaoUsuario;

@Resource
public class BonificacaoGraduacaoController {

	private Result result;
	private HibernateUtil hibernateUtil;
	private SessaoUsuario sessaoUsuario;
	private SessaoBonificacao sessaoBonificacao;
	private Validator validator;

	public BonificacaoGraduacaoController(Result result, HibernateUtil hibernateUtil, SessaoUsuario sessaoUsuario, SessaoBonificacao sessaoBonificacao, Validator validator) {

		this.result = result;
		this.hibernateUtil = hibernateUtil;
		this.sessaoUsuario = sessaoUsuario;
		this.sessaoBonificacao = sessaoBonificacao;
		this.validator = validator;
	}

	@Funcionalidade
	public void gerarRelatorioBonificacaoGraduacao(Integer ano, Integer mes) {

		BonificacaoGraduacaoService bonificacaoGraduacaoService = new BonificacaoGraduacaoService(hibernateUtil, validator, result);

		BonificacaoGraduacaoAuxiliar bonificacao = bonificacaoGraduacaoService.calcularBonificacoes(sessaoUsuario.getUsuario(), ano, mes, sessaoBonificacao.getExtratoSimplificadoAuxiliar().getPontuacao(), sessaoBonificacao.getExtratoSimplificadoAuxiliar().getQuantidadeGraduados(), sessaoBonificacao.getMalaDireta());
		result.include("bonificacaoGraduacao", bonificacao);

		result.include("pedidosDaRede", bonificacaoGraduacaoService.buscarPedidosDaRede(sessaoUsuario.getUsuario(), ano, mes, sessaoBonificacao.getMalaDireta(), bonificacao.getUsuariosQueDeramAlgumaBonificacao()));

		result.include("mesAno", mes + "/" + ano);
	}
}
