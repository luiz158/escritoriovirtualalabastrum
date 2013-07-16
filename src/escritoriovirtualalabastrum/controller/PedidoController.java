package escritoriovirtualalabastrum.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.joda.time.DateTime;

import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import escritoriovirtualalabastrum.anotacoes.Public;
import escritoriovirtualalabastrum.hibernate.HibernateUtil;
import escritoriovirtualalabastrum.modelo.Categoria;
import escritoriovirtualalabastrum.modelo.Produto;
import escritoriovirtualalabastrum.sessao.SessaoGeral;
import escritoriovirtualalabastrum.sessao.SessaoPedido;
import escritoriovirtualalabastrum.sessao.SessaoUsuario;
import escritoriovirtualalabastrum.util.Util;

@Resource
public class PedidoController {

	private Result result;
	private HibernateUtil hibernateUtil;
	private SessaoPedido sessaoPedido;
	private SessaoGeral sessaoGeral;
	private SessaoUsuario sessaoUsuario;

	public PedidoController(Result result, HibernateUtil hibernateUtil, SessaoPedido sessaoPedido, SessaoGeral sessaoGeral, SessaoUsuario sessaoUsuario) {

		this.result = result;
		this.hibernateUtil = hibernateUtil;
		this.sessaoPedido = sessaoPedido;
		this.sessaoGeral = sessaoGeral;
		this.sessaoUsuario = sessaoUsuario;
	}

	@Public
	public void acessarTelaPedido() {

		this.sessaoPedido.setProdutosEQuantidades(new LinkedHashMap<String, Integer>());

		String codigoPedido = "";

		DateTime agora = new DateTime();

		String agoraString = agora.toString("dd-MM-YYYY HH:mm:ss");

		if (Util.preenchido(this.sessaoUsuario.getUsuario())) {

			codigoPedido = this.sessaoUsuario.getUsuario().getId_Codigo() + agoraString.replaceAll("-", "").replaceAll(":", "").replaceAll(" ", "");

			this.sessaoPedido.setCodigoPedido(codigoPedido);
		}

		else if (Util.preenchido(this.sessaoGeral.getValor("codigoUsuarioRealizandoPedido"))) {

			Integer codigoUsuario = (Integer) this.sessaoGeral.getValor("codigoUsuarioRealizandoPedido");

			codigoPedido = codigoUsuario + agoraString.replaceAll("-", "").replaceAll(":", "").replaceAll(" ", "");

			this.sessaoPedido.setCodigoPedido(codigoPedido);
		}

		if (Util.preenchido(codigoPedido)) {

			result.forwardTo(this).etapaSelecaoProdutos();
		}

		else {

			result.forwardTo(LoginController.class).telaLogin();
		}
	}

	@Public
	public void etapaSelecaoProdutos() {

		result.include("categorias", this.hibernateUtil.buscar(new Categoria(), Order.asc("catNome")));
	}

	@Public
	public void voltarParaSelecaoProdutos() {

		List<Produto> produtos = new ArrayList<Produto>();

		for (Entry<String, Integer> produtoEQuantidade : this.sessaoPedido.getProdutosEQuantidades().entrySet()) {

			Produto produtoFiltro = new Produto();
			produtoFiltro.setId_Produtos(produtoEQuantidade.getKey());

			Produto produto = this.hibernateUtil.selecionar(produtoFiltro, MatchMode.EXACT);

			produto.setQuantidade(produtoEQuantidade.getValue());

			produtos.add(produto);
		}

		result.include("produtos", produtos);

		result.forwardTo(this).etapaSelecaoProdutos();
	}

	@Public
	public void etapaFormasPagamento(String hashProdutosEQuantidades) {

		this.sessaoPedido.setProdutosEQuantidades(new LinkedHashMap<String, Integer>());

		String[] produtosEQuantidades = hashProdutosEQuantidades.split(",");

		for (int i = 0; i < produtosEQuantidades.length; i++) {

			try {

				String idProduto = produtosEQuantidades[i].split("=")[0];
				String quantidade = produtosEQuantidades[i].split("=")[1];

				this.sessaoPedido.getProdutosEQuantidades().put(idProduto, Integer.valueOf(quantidade));
			}

			catch (Exception e) {

			}
		}
	}

	@Public
	public void etapaComoDesejaReceberOsProdutos(SessaoPedido sessaoPedido) {

		preencherSessao(sessaoPedido);
	}

	private void preencherSessao(SessaoPedido sessaoPedido) {

		this.sessaoPedido.setFormaPagamento(sessaoPedido.getFormaPagamento());
		this.sessaoPedido.setNomeNoCartao(sessaoPedido.getNomeNoCartao());
		this.sessaoPedido.setBandeiraCartao(sessaoPedido.getBandeiraCartao());
		this.sessaoPedido.setNumeroCartao(sessaoPedido.getNumeroCartao());
		this.sessaoPedido.setDataValidadeCartao(sessaoPedido.getDataValidadeCartao());
		this.sessaoPedido.setCodigoSegurancaCartao(sessaoPedido.getCodigoSegurancaCartao());
		this.sessaoPedido.setQuantidadeParcelas(sessaoPedido.getQuantidadeParcelas());
		this.sessaoPedido.setCentroDistribuicao(sessaoPedido.getCentroDistribuicao());
		this.sessaoPedido.setDataHoraEscolhida(sessaoPedido.getDataHoraEscolhida());
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
}
