package escritoriovirtualalabastrum.controller;

import java.util.List;

import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import escritoriovirtualalabastrum.anotacoes.Funcionalidade;
import escritoriovirtualalabastrum.auxiliar.BonificacaoAtivacaoAuxiliar;
import escritoriovirtualalabastrum.hibernate.HibernateUtil;
import escritoriovirtualalabastrum.service.BonificacaoAtivacaoService;
import escritoriovirtualalabastrum.sessao.SessaoUsuario;

@Resource
public class BonificacaoAtivacaoController {

	private Result result;
	private HibernateUtil hibernateUtil;
	private SessaoUsuario sessaoUsuario;
	private Validator validator;

	public BonificacaoAtivacaoController(Result result, HibernateUtil hibernateUtil, SessaoUsuario sessaoUsuario, Validator validator) {

		this.result = result;
		this.hibernateUtil = hibernateUtil;
		this.sessaoUsuario = sessaoUsuario;
		this.validator = validator;
	}

	@Funcionalidade
	public void gerarRelatorioBonificacaoAtivacao(Integer ano, Integer mes) {

		BonificacaoAtivacaoService bonificacaoAtivacaoService = new BonificacaoAtivacaoService(hibernateUtil, result, validator);

		List<BonificacaoAtivacaoAuxiliar> bonificacoes = bonificacaoAtivacaoService.calcularBonificacoes(this.sessaoUsuario.getUsuario(), ano, mes);

		result.include("bonificacoes", bonificacoes);
		result.include("somatorioBonificacao", bonificacaoAtivacaoService.calcularSomatorioBonificacoes(bonificacoes));

		result.include("mesAno", mes + "/" + ano);
	}
}
