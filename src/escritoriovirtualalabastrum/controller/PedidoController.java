package escritoriovirtualalabastrum.controller;

import java.math.BigDecimal;
import java.util.HashMap;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;

import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import escritoriovirtualalabastrum.anotacoes.Public;
import escritoriovirtualalabastrum.hibernate.HibernateUtil;
import escritoriovirtualalabastrum.modelo.Categoria;
import escritoriovirtualalabastrum.modelo.Produto;
import escritoriovirtualalabastrum.sessao.SessaoPedido;

@Resource
public class PedidoController {

	private Result result;
	private HibernateUtil hibernateUtil;
	private SessaoPedido sessaoPedido;

	public PedidoController(Result result, HibernateUtil hibernateUtil, SessaoPedido sessaoPedido) {

		this.result = result;
		this.hibernateUtil = hibernateUtil;
		this.sessaoPedido = sessaoPedido;
	}

	@Public
	public void acessarTelaPedido() {

		this.sessaoPedido.setProdutosEQuantidades(new HashMap<String, BigDecimal>());

		result.include("categorias", this.hibernateUtil.buscar(new Categoria(), Order.asc("catNome")));
	}

	@Public
	public void listarProdutos(Integer idCategoria) {

		Produto produto = new Produto();
		produto.setId_Categoria(idCategoria);

		result.use(Results.json()).from(this.hibernateUtil.buscar(produto, Order.asc("prdNome"))).serialize();
	}

	@Public
	public void buscarInformacoesProdutoSelecionado(String idProduto) {

		Produto produto = new Produto();
		produto.setId_Produtos(idProduto);

		result.use(Results.json()).from(this.hibernateUtil.selecionar(produto, MatchMode.EXACT)).serialize();
	}

	@Public
	public void adicionarProdutoEQuantidade(String idProduto, BigDecimal quantidade) {

		this.sessaoPedido.getProdutosEQuantidades().put(idProduto, quantidade);

		result.use(Results.json()).from("").serialize();
	}
}
