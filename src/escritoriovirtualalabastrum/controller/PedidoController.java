package escritoriovirtualalabastrum.controller;

import org.hibernate.criterion.Order;

import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import escritoriovirtualalabastrum.anotacoes.Public;
import escritoriovirtualalabastrum.hibernate.HibernateUtil;
import escritoriovirtualalabastrum.modelo.Categoria;
import escritoriovirtualalabastrum.sessao.SessaoUsuario;

@Resource
public class PedidoController {

	private Result result;
	private HibernateUtil hibernateUtil;
	private SessaoUsuario sessaoUsuario;
	private Validator validator;

	public PedidoController(Result result, HibernateUtil hibernateUtil, SessaoUsuario sessaoUsuario, Validator validator) {

		this.result = result;
		this.hibernateUtil = hibernateUtil;
		this.sessaoUsuario = sessaoUsuario;
		this.validator = validator;
	}

	@Public
	public void acessarTelaPedido() {

		result.include("categorias", this.hibernateUtil.buscar(new Categoria(), Order.asc("catNome")));
	}
}
