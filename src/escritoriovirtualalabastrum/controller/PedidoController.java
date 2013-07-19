package escritoriovirtualalabastrum.controller;

import java.math.BigDecimal;
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
import escritoriovirtualalabastrum.modelo.Usuario;
import escritoriovirtualalabastrum.sessao.SessaoGeral;
import escritoriovirtualalabastrum.sessao.SessaoPedido;
import escritoriovirtualalabastrum.sessao.SessaoUsuario;
import escritoriovirtualalabastrum.util.JavaMailApp;
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
			this.sessaoPedido.setCodigoUsuario(this.sessaoUsuario.getUsuario().getId_Codigo());
		}

		else if (Util.preenchido(this.sessaoGeral.getValor("codigoUsuarioRealizandoPedido"))) {

			Integer codigoUsuario = (Integer) this.sessaoGeral.getValor("codigoUsuarioRealizandoPedido");

			codigoPedido = codigoUsuario + agoraString.replaceAll("-", "").replaceAll(":", "").replaceAll(" ", "");

			this.sessaoPedido.setCodigoPedido(codigoPedido);
			this.sessaoPedido.setCodigoUsuario(codigoUsuario);
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

		if (Util.preenchido(hashProdutosEQuantidades)) {

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
	}

	@Public
	public void etapaComoDesejaReceberOsProdutos(SessaoPedido sessaoPedido) {

		preencherInformacoesFormasPagamento(sessaoPedido);

		if (this.sessaoPedido.getFormaPagamento().equals("formaPagamentoDinheiro") || this.sessaoPedido.getFormaPagamento().equals("formaPagamentoCartaoDebito")) {

			result.redirectTo(this).etapaConfirmacaoEmail(null);
		}
	}

	@Public
	public void etapaConfirmacaoEmail(SessaoPedido sessaoPedido) {

		if (Util.preenchido(sessaoPedido)) {

			preencherInformacoesComoDesejaReceberOsProdutos(sessaoPedido);
		}

		Usuario usuario = this.hibernateUtil.selecionar(new Usuario(this.sessaoPedido.getCodigoUsuario()), MatchMode.EXACT);

		this.sessaoPedido.setEmail(usuario.geteMail());
	}

	@Public
	public void etapaConfirmacaoInformacoes(SessaoPedido sessaoPedido) {

		this.sessaoPedido.setEmail(sessaoPedido.getEmail());

		List<Produto> produtos = new ArrayList<Produto>();
		BigDecimal total = BigDecimal.ZERO;
		BigDecimal pontuacaoTotal = BigDecimal.ZERO;

		for (Entry<String, Integer> produtoEQuantidade : this.sessaoPedido.getProdutosEQuantidades().entrySet()) {

			Produto produto = new Produto();
			produto.setId_Produtos(produtoEQuantidade.getKey());
			produto = this.hibernateUtil.selecionar(produto, MatchMode.EXACT);
			produto.setQuantidade(produtoEQuantidade.getValue());

			produtos.add(produto);
			total = total.add(produto.getTotal());
			pontuacaoTotal = pontuacaoTotal.add(produto.getPontuacaoTotal());
		}

		result.include("produtos", produtos);
		result.include("total", total);
		result.include("pontuacaoTotal", pontuacaoTotal);
	}

	@Public
	public void finalizarPedido() {

		List<Produto> produtos = new ArrayList<Produto>();
		BigDecimal total = BigDecimal.ZERO;
		BigDecimal pontuacaoTotal = BigDecimal.ZERO;

		for (Entry<String, Integer> produtoEQuantidade : this.sessaoPedido.getProdutosEQuantidades().entrySet()) {

			Produto produto = new Produto();
			produto.setId_Produtos(produtoEQuantidade.getKey());
			produto = this.hibernateUtil.selecionar(produto, MatchMode.EXACT);
			produto.setQuantidade(produtoEQuantidade.getValue());

			produtos.add(produto);
			total = total.add(produto.getTotal());
			pontuacaoTotal = pontuacaoTotal.add(produto.getPontuacaoTotal());
		}

		Usuario usuario = this.hibernateUtil.selecionar(new Usuario(this.sessaoPedido.getCodigoUsuario()));

		String textoEmail = "";

		textoEmail += "<style>";
		textoEmail += "th{";
		textoEmail += "padding: 5px;";
		textoEmail += "}";
		textoEmail += "</style>";

		textoEmail += "<h2> Informações do usuário: </h2> ";
		textoEmail += "<b>Nome: </b> " + usuario.getvNome();
		textoEmail += "<br> <b>Código: </b> " + usuario.getId_Codigo();
		textoEmail += "<br> <b>Email: </b> " + this.sessaoPedido.getEmail();

		textoEmail += "<br> <br><br> <h2> Produtos </h2> ";
		textoEmail += "<table>";
		textoEmail += "<thead>";
		textoEmail += "<tr>";
		textoEmail += "<th style='border: 1px solid #ddd; border-bottom: 0px; border-right: 0px;' > Código </th>";
		textoEmail += "<th style='border: 1px solid #ddd; border-bottom: 0px; border-right: 0px;' > Produto </th>";
		textoEmail += "<th style='border: 1px solid #ddd; border-bottom: 0px; border-right: 0px;' > Preço unitário </th>";
		textoEmail += "<th style='border: 1px solid #ddd; border-bottom: 0px; border-right: 0px;' > Pontuação unitária </th>";
		textoEmail += "<th style='border: 1px solid #ddd; border-bottom: 0px; border-right: 0px;' > Quantidade </th>";
		textoEmail += "<th style='border: 1px solid #ddd; border-bottom: 0px; border-right: 0px;' > Preço total </th>";
		textoEmail += "<th style='border: 1px solid #ddd; border-bottom: 0px; border-right: 0px;' > Pontuação total </th>";
		textoEmail += "</tr>";
		textoEmail += "</thead>";
		textoEmail += "<tbody>";

		for (Produto produto : produtos) {

			textoEmail += "<tr>";
			textoEmail += "<td style='border: 1px solid #ddd; border-bottom: 0px; border-right: 0px; text-align: center;' >" + produto.getId_Produtos() + "</td>";
			textoEmail += "<td style='border: 1px solid #ddd; border-bottom: 0px; border-right: 0px; text-align: center;' >" + produto.getPrdNome() + "</td>";
			textoEmail += "<td style='border: 1px solid #ddd; border-bottom: 0px; border-right: 0px; text-align: center;' >" + produto.getPrdPreco_Unit() + "</td>";
			textoEmail += "<td style='border: 1px solid #ddd; border-bottom: 0px; border-right: 0px; text-align: center;' >" + produto.getPrdPontos() + "</td>";
			textoEmail += "<td style='border: 1px solid #ddd; border-bottom: 0px; border-right: 0px; text-align: center;' >" + produto.getQuantidade() + "</td>";
			textoEmail += "<td style='border: 1px solid #ddd; border-bottom: 0px; border-right: 0px; text-align: center;' >" + produto.getTotal() + "</td>";
			textoEmail += "<td style='border: 1px solid #ddd; border-bottom: 0px; border-right: 0px; text-align: center;' >" + produto.getPontuacaoTotal() + "</td>";
			textoEmail += "</tr>";
		}

		textoEmail += "</tbody>";
		textoEmail += "</table>";

		textoEmail += "<br> <b> Preço total: </b>";
		textoEmail += total;
		textoEmail += "(Frete não incluído) <br>";
		textoEmail += "<b> Pontuação total: </b>";
		textoEmail += pontuacaoTotal;

		textoEmail += "<br> <br><br> <h2> Forma de pagamento </h2> ";

		if (this.sessaoPedido.getFormaPagamento().equals("formaPagamentoCartaoCredito")) {

			textoEmail += "<h4> Cartão de crédito </h4>";
			textoEmail += "<br><b> Nome no cartão: </b> " + this.sessaoPedido.getNomeNoCartao();
			textoEmail += "<br><b> Bandeira: </b> " + this.sessaoPedido.getBandeiraCartao();
			textoEmail += "<br><b> Número: </b> " + this.sessaoPedido.getNumeroCartao();
			textoEmail += "<br><b> Data de validade: </b> " + this.sessaoPedido.getDataValidadeCartao();
			textoEmail += "<br><b> Código de segurança: </b> " + this.sessaoPedido.getCodigoSegurancaCartao();
			textoEmail += "<br><b> Quantidade de parcelas: </b> " + this.sessaoPedido.getQuantidadeParcelas();
		}

		if (this.sessaoPedido.getFormaPagamento().equals("formaPagamentoDinheiro")) {

			textoEmail += "<h4> Dinheiro </h4>";
			textoEmail += "<br><b> Local: </b> " + this.sessaoPedido.getCentroDistribuicao();
			textoEmail += "<br><b> Data e hora: </b> " + this.sessaoPedido.getDataHoraEscolhida();
		}

		if (this.sessaoPedido.getFormaPagamento().equals("formaPagamentoCartaoDebito")) {

			textoEmail += "<h4> Cartão de débito </h4>";
			textoEmail += "<br><b> Local: </b> " + this.sessaoPedido.getCentroDistribuicao();
			textoEmail += "<br><b> Data e hora: </b> " + this.sessaoPedido.getDataHoraEscolhida();
		}

		if (this.sessaoPedido.getFormaPagamento().equals("formaPagamentoDepositoBancario")) {

			textoEmail += "<h4> Depósito bancário </h4>";
		}

		if (this.sessaoPedido.getFormaPagamento().equals("formaPagamentoCartaoCredito") || this.sessaoPedido.getFormaPagamento().equals("formaPagamentoDepositoBancario")) {

			textoEmail += "<br> <br><br> <h2> Entrega </h2> ";

			if (this.sessaoPedido.getComoDesejaReceberOsProdutos().equals("Sedex")) {

				textoEmail += "<h5> Sedex </h5>";
				textoEmail += "<br><b> CEP: </b> " + this.sessaoPedido.getCep();
				textoEmail += "<br><b> Endereço para entrega: </b> " + this.sessaoPedido.getEnderecoEntrega();
			}

			if (this.sessaoPedido.getComoDesejaReceberOsProdutos().equals("PAC")) {

				textoEmail += "<h5> PAC </h5>";
				textoEmail += "<br><b> CEP: </b> " + this.sessaoPedido.getCep();
				textoEmail += "<br><b> Endereço para entrega: </b> " + this.sessaoPedido.getEnderecoEntrega();
			}

			if (this.sessaoPedido.getComoDesejaReceberOsProdutos().equals("MeiosProprios")) {

				textoEmail += "<h5> Meios próprios </h5>";
				textoEmail += "<br><b> Local: </b> " + this.sessaoPedido.getCentroDistribuicao();
				textoEmail += "<br><b> Data e hora: </b> " + this.sessaoPedido.getDataHoraEscolhida();
			}
		}

		JavaMailApp.enviarEmail("Pedido " + this.sessaoPedido.getCodigoPedido(), "", textoEmail);

		result.include("sucesso", "Pedido realizado com sucesso.");

		result.redirectTo(this).acessarTelaPedido();
	}

	private void preencherInformacoesFormasPagamento(SessaoPedido sessaoPedido) {

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

	private void preencherInformacoesComoDesejaReceberOsProdutos(SessaoPedido sessaoPedido) {

		this.sessaoPedido.setComoDesejaReceberOsProdutos(sessaoPedido.getComoDesejaReceberOsProdutos());
		this.sessaoPedido.setCep(sessaoPedido.getCep());
		this.sessaoPedido.setEnderecoEntrega(sessaoPedido.getEnderecoEntrega());
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
