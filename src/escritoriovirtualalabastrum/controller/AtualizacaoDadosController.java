package escritoriovirtualalabastrum.controller;

import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import escritoriovirtualalabastrum.anotacoes.Funcionalidade;
import escritoriovirtualalabastrum.sessao.SessaoAtualizacaoDados;

@Resource
public class AtualizacaoDadosController {

	private Result result;
	private Validator validator;
	private SessaoAtualizacaoDados sessaoAtualizacaoDados;

	public AtualizacaoDadosController(Result result, Validator validator, SessaoAtualizacaoDados sessaoAtualizacaoDados) {

		this.result = result;
		this.validator = validator;
		this.sessaoAtualizacaoDados = sessaoAtualizacaoDados;
	}

	@Funcionalidade
	public void acessarTelaAtualizacaoDados() {

		this.sessaoAtualizacaoDados = new SessaoAtualizacaoDados();
	}

	@Funcionalidade
	public void salvarAtualizacaoDados() {

	}
}
