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
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.ValidationMessage;
import br.com.caelum.vraptor.view.Results;
import escritoriovirtualalabastrum.anotacoes.Public;
import escritoriovirtualalabastrum.hibernate.HibernateUtil;
import escritoriovirtualalabastrum.modelo.Categoria;
import escritoriovirtualalabastrum.modelo.CentroDistribuicao;
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
	private Validator validator;

	public PedidoController(Result result, HibernateUtil hibernateUtil, SessaoPedido sessaoPedido, SessaoGeral sessaoGeral, SessaoUsuario sessaoUsuario, Validator validator) {

		this.result = result;
		this.hibernateUtil = hibernateUtil;
		this.sessaoPedido = sessaoPedido;
		this.sessaoGeral = sessaoGeral;
		this.sessaoUsuario = sessaoUsuario;
		this.validator = validator;
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

			Usuario usuario = this.hibernateUtil.selecionar(new Usuario(this.sessaoPedido.getCodigoUsuario()));
			this.sessaoPedido.setCredito(usuario.getCadCredito());

			this.sessaoPedido.setTipoPedido("");
			this.sessaoPedido.setCentroDistribuicaoDoResponsavel("");
			this.sessaoPedido.setCodigoOutroDistribuidor("");

			CentroDistribuicao centroDistribuicaoFiltro = new CentroDistribuicao();
			centroDistribuicaoFiltro.setId_Codigo(this.sessaoPedido.getCodigoUsuario());

			List<CentroDistribuicao> centrosDistribuicaoDoResponsavel = this.hibernateUtil.buscar(centroDistribuicaoFiltro);

			if (Util.preenchido(centrosDistribuicaoDoResponsavel)) {

				this.sessaoPedido.setCentroDistribuicaoDoResponsavel(centrosDistribuicaoDoResponsavel.get(0).getEstqNome());
			}

			result.forwardTo(this).etapaSelecaoProdutos();
		}

		else {

			result.forwardTo(LoginController.class).telaLogin();
		}
	}

	@Public
	public void etapaSelecaoProdutos() {

		result.include("categorias", this.hibernateUtil.buscar(new Categoria(), Order.asc("catNome")));

		result.include("todosCentrosDistribuicao", this.hibernateUtil.buscar(new CentroDistribuicao()));
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
	public void etapaFormasPagamento(String hashProdutosEQuantidades, String tipoPedido, String codigoOutroDistribuidor, String centroDistribuicaoDoResponsavel) {

		preencherInformarcoesTipoPedido(tipoPedido, codigoOutroDistribuidor, centroDistribuicaoDoResponsavel);

		result.include("centrosDistribuicao", this.hibernateUtil.buscar(new CentroDistribuicao()));

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

	private void preencherInformarcoesTipoPedido(String tipoPedido, String codigoOutroDistribuidor, String centroDistribuicaoDoResponsavel) {

		if (Util.preenchido(tipoPedido)) {

			this.sessaoPedido.setTipoPedido(tipoPedido);
			this.sessaoPedido.setCentroDistribuicaoDoResponsavel(centroDistribuicaoDoResponsavel);
			this.sessaoPedido.setCodigoOutroDistribuidor(codigoOutroDistribuidor);
		}
	}

	@Public
	public void etapaComoDesejaReceberOsProdutos(SessaoPedido sessaoPedido) {

		result.include("centrosDistribuicao", this.hibernateUtil.buscar(new CentroDistribuicao()));

		if (Util.preenchido(sessaoPedido)) {

			preencherInformacoesFormasPagamento(sessaoPedido);

			if (this.sessaoPedido.getFormaPagamento().equals("formaPagamentoCreditoBonificacao")) {

				BigDecimal totalPedido = calcularTotalPedido();
				BigDecimal creditoUsuario = Util.converterStringParaBigDecimal(this.sessaoPedido.getCredito());

				if (totalPedido.compareTo(creditoUsuario) > 0) {

					validator.add(new ValidationMessage("O valor do seu pedido é " + totalPedido.toString() + " mas você só tem " + creditoUsuario.toString() + " em créditos.", "Atenção"));
					validator.onErrorRedirectTo(this).etapaFormasPagamento(null, null, null, null);
				}
			}

			if (this.sessaoPedido.getFormaPagamento().equals("formaPagamentoCartaoCredito")) {

				if (Util.vazio(sessaoPedido.getNomeNoCartao()) || Util.vazio(sessaoPedido.getBandeiraCartao()) || Util.vazio(sessaoPedido.getNumeroCartao()) || Util.vazio(sessaoPedido.getDataValidadeCartao()) || Util.vazio(sessaoPedido.getCodigoSegurancaCartao())) {

					validator.add(new ValidationMessage("Todos os campos referentes ao cartão de crédito são obrigatórios", "Atenção"));
					validator.onErrorRedirectTo(this).etapaFormasPagamento(null, null, null, null);
				}
			}

			if (this.sessaoPedido.getFormaPagamento().equals("formaPagamentoDinheiro") || this.sessaoPedido.getFormaPagamento().equals("formaPagamentoCartaoDebito")) {

				if (Util.vazio(sessaoPedido.getCentroDistribuicao()) || Util.vazio(sessaoPedido.getDataHoraEscolhida())) {

					validator.add(new ValidationMessage("É obrigatória a escolha do centro de distribuição e o preenchimento da data/hora", "Atenção"));
					validator.onErrorRedirectTo(this).etapaFormasPagamento(null, null, null, null);
				}

				result.redirectTo(this).etapaConfirmacaoEmail(null);
			}
		}
	}

	@Public
	public void etapaConfirmacaoEmail(SessaoPedido sessaoPedido) {

		if (Util.preenchido(sessaoPedido)) {

			preencherInformacoesComoDesejaReceberOsProdutos(sessaoPedido);

			if (sessaoPedido.getComoDesejaReceberOsProdutos().equals("Sedex") || sessaoPedido.getComoDesejaReceberOsProdutos().equals("PAC")) {

				if (Util.vazio(sessaoPedido.getCep()) || Util.vazio(sessaoPedido.getEnderecoEntrega())) {

					validator.add(new ValidationMessage("É obrigatório o preenchimento do CEP e do endereço para entrega", "Atenção"));
					validator.onErrorRedirectTo(this).etapaComoDesejaReceberOsProdutos(null);
				}
			}

			if (sessaoPedido.getComoDesejaReceberOsProdutos().equals("MeiosProprios")) {

				if (Util.vazio(sessaoPedido.getCentroDistribuicao()) || Util.vazio(sessaoPedido.getDataHoraEscolhida())) {

					validator.add(new ValidationMessage("É obrigatória a escolha do centro de distribuição e o preenchimento da data/hora", "Atenção"));
					validator.onErrorRedirectTo(this).etapaComoDesejaReceberOsProdutos(null);
				}
			}
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

		if (this.sessaoPedido.getTipoPedido().equals("realizarPedidoPorOutroDistribuidor")) {

			obterNomeDistribuidor();
		}
	}

	private BigDecimal calcularTotalPedido() {

		BigDecimal total = BigDecimal.ZERO;

		for (Entry<String, Integer> produtoEQuantidade : this.sessaoPedido.getProdutosEQuantidades().entrySet()) {

			Produto produto = new Produto();
			produto.setId_Produtos(produtoEQuantidade.getKey());
			produto = this.hibernateUtil.selecionar(produto, MatchMode.EXACT);
			produto.setQuantidade(produtoEQuantidade.getValue());

			total = total.add(produto.getTotal());
		}

		return total;
	}

	private String obterNomeDistribuidor() {

		String nome = "";

		if (Util.preenchido(this.sessaoPedido.getCodigoOutroDistribuidor())) {

			Usuario usuarioFiltro = new Usuario(Integer.valueOf(this.sessaoPedido.getCodigoOutroDistribuidor()));

			List<Usuario> usuarios = this.hibernateUtil.buscar(usuarioFiltro, MatchMode.EXACT);

			if (Util.preenchido(usuarios)) {

				nome = usuarios.get(0).getvNome();
				result.include("nomeOutroDistribuidor", nome);
			}
		}

		return nome;
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
		textoEmail += "<br> <b>Telefone: </b> " + usuario.getTel();
		textoEmail += "<br> <b>Celular: </b> " + usuario.getCadCelular();

		if (this.sessaoPedido.getTipoPedido().equals("realizarPedidoPorOutroDistribuidor")) {

			textoEmail += "<br> <br><br> ";
			textoEmail += "<b> Tipo do pedido: </b> <span> Realizar pedido por outro distribuidor </span> <br> ";
			textoEmail += "<b> Código do distribuidor: </b> <span>" + this.sessaoPedido.getCodigoOutroDistribuidor() + " </span> <br>";
			textoEmail += "<b> Nome do distribuidor: </b> <span>" + obterNomeDistribuidor() + " </span> <br>";
		}

		if (this.sessaoPedido.getTipoPedido().equals("realizarPedidoParaUmCentroDeDistribuicao")) {

			textoEmail += "<br> <br><br> ";
			textoEmail += "<b> Tipo do pedido: </b> <span> Realizar pedido para um centro de distribuição </span> <br> ";
			textoEmail += "<b> Centro de distribuição: </b> <span>" + this.sessaoPedido.getCentroDistribuicaoDoResponsavel() + " </span> <br>";
		}

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

		textoEmail += "<br> <b> Preço total: R$ </b>";
		textoEmail += Util.formatarBigDecimal(total);
		textoEmail += " (Frete não incluído) <br>";
		textoEmail += "<b> Pontuação total: </b>";
		textoEmail += Util.formatarBigDecimal(pontuacaoTotal);

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
			textoEmail += "<br><b> Data/hora/observações: </b> " + this.sessaoPedido.getDataHoraEscolhida();
		}

		if (this.sessaoPedido.getFormaPagamento().equals("formaPagamentoCartaoDebito")) {

			textoEmail += "<h4> Cartão de débito </h4>";
			textoEmail += "<br><b> Local: </b> " + this.sessaoPedido.getCentroDistribuicao();
			textoEmail += "<br><b> Data/hora/observações: </b> " + this.sessaoPedido.getDataHoraEscolhida();
		}

		if (this.sessaoPedido.getFormaPagamento().equals("formaPagamentoDepositoBancario")) {

			textoEmail += "<h4> Depósito bancário </h4>";
		}

		if (this.sessaoPedido.getFormaPagamento().equals("formaPagamentoCreditoBonificacao")) {

			textoEmail += "<h4> Crédito (bonificação a receber) </h4>";
			textoEmail += "<h4> Crédito atual: " + this.sessaoPedido.getCredito() + " </h4>";
		}

		if (this.sessaoPedido.getFormaPagamento().equals("formaPagamentoCartaoCredito") || this.sessaoPedido.getFormaPagamento().equals("formaPagamentoDepositoBancario") || this.sessaoPedido.getFormaPagamento().equals("formaPagamentoCreditoBonificacao")) {

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
				textoEmail += "<br><b> Data/hora/observações: </b> " + this.sessaoPedido.getDataHoraEscolhida();
			}
		}

		JavaMailApp.enviarEmail("Pedido " + this.sessaoPedido.getCodigoPedido() + " (Via Escritório Virtual)", "pedidos@alabastrum.com.br," + this.sessaoPedido.getEmail(), textoEmail);

		result.include("sucesso", "Pedido realizado com sucesso!");

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

	@Public
	public void buscarNomeDistribuidor(Integer codigoDistribuidor) {

		if (Util.preenchido(codigoDistribuidor)) {

			Usuario usuarioFiltro = new Usuario(codigoDistribuidor);

			List<Usuario> usuarios = this.hibernateUtil.buscar(usuarioFiltro, MatchMode.EXACT);

			if (Util.preenchido(usuarios)) {

				result.use(Results.json()).from(usuarios.get(0).getvNome()).serialize();
			}

			else {

				result.use(Results.json()).from("").serialize();
			}
		}

		else {

			result.use(Results.json()).from("").serialize();
		}
	}

}
