package escritoriovirtualalabastrum.controller;

import java.math.BigDecimal;

import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import escritoriovirtualalabastrum.anotacoes.Funcionalidade;
import escritoriovirtualalabastrum.hibernate.HibernateUtil;
import escritoriovirtualalabastrum.sessao.SessaoUsuario;

@Resource
public class TransferenciaCreditoController {

	private Result result;
	private HibernateUtil hibernateUtil;
	private SessaoUsuario sessaoUsuario;
	private Validator validator;

	public TransferenciaCreditoController(Result result, HibernateUtil hibernateUtil, SessaoUsuario sessaoUsuario, Validator validator) {

		this.result = result;
		this.hibernateUtil = hibernateUtil;
		this.sessaoUsuario = sessaoUsuario;
		this.validator = validator;
	}

	@Funcionalidade
	public void acessarTelaTransferenciaCredito() {

	}

	@Funcionalidade
	public void transferir(Integer codigoQuemVaiReceber, BigDecimal quantidadeASerTransferida) {

		System.out.println(codigoQuemVaiReceber);
		System.out.println(quantidadeASerTransferida);

	}

}
