package escritoriovirtualalabastrum.sessao;

import java.util.LinkedHashMap;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.SessionScoped;

@Component
@SessionScoped
public class SessaoPedido {

	private LinkedHashMap<String, Integer> produtosEQuantidades;
	private Integer codigoUsuario;
	private String codigoPedido;
	private String formaPagamento;
	private String nomeNoCartao;
	private String bandeiraCartao;
	private String numeroCartao;
	private String dataValidadeCartao;
	private String codigoSegurancaCartao;
	private String quantidadeParcelas;
	private String centroDistribuicao;
	private String dataHoraEscolhida;
	private String comoDesejaReceberOsProdutos;
	private String cep;
	private String enderecoEntrega;
	private String email;
	private String centroDistribuicaoDoResponsavel;
	private String tipoPedido;
	private String codigoOutroDistribuidor;
	private String credito;

	public LinkedHashMap<String, Integer> getProdutosEQuantidades() {
		return produtosEQuantidades;
	}

	public void setProdutosEQuantidades(LinkedHashMap<String, Integer> produtosEQuantidades) {
		this.produtosEQuantidades = produtosEQuantidades;
	}

	public Integer getCodigoUsuario() {
		return codigoUsuario;
	}

	public void setCodigoUsuario(Integer codigoUsuario) {
		this.codigoUsuario = codigoUsuario;
	}

	public String getCodigoPedido() {
		return codigoPedido;
	}

	public void setCodigoPedido(String codigoPedido) {
		this.codigoPedido = codigoPedido;
	}

	public String getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(String formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public String getNomeNoCartao() {
		return nomeNoCartao;
	}

	public void setNomeNoCartao(String nomeNoCartao) {
		this.nomeNoCartao = nomeNoCartao;
	}

	public String getBandeiraCartao() {
		return bandeiraCartao;
	}

	public void setBandeiraCartao(String bandeiraCartao) {
		this.bandeiraCartao = bandeiraCartao;
	}

	public String getNumeroCartao() {
		return numeroCartao;
	}

	public void setNumeroCartao(String numeroCartao) {
		this.numeroCartao = numeroCartao;
	}

	public String getDataValidadeCartao() {
		return dataValidadeCartao;
	}

	public void setDataValidadeCartao(String dataValidadeCartao) {
		this.dataValidadeCartao = dataValidadeCartao;
	}

	public String getCodigoSegurancaCartao() {
		return codigoSegurancaCartao;
	}

	public void setCodigoSegurancaCartao(String codigoSegurancaCartao) {
		this.codigoSegurancaCartao = codigoSegurancaCartao;
	}

	public String getQuantidadeParcelas() {
		return quantidadeParcelas;
	}

	public void setQuantidadeParcelas(String quantidadeParcelas) {
		this.quantidadeParcelas = quantidadeParcelas;
	}

	public String getCentroDistribuicao() {
		return centroDistribuicao;
	}

	public void setCentroDistribuicao(String centroDistribuicao) {
		this.centroDistribuicao = centroDistribuicao;
	}

	public String getDataHoraEscolhida() {
		return dataHoraEscolhida;
	}

	public void setDataHoraEscolhida(String dataHoraEscolhida) {
		this.dataHoraEscolhida = dataHoraEscolhida;
	}

	public String getComoDesejaReceberOsProdutos() {
		return comoDesejaReceberOsProdutos;
	}

	public void setComoDesejaReceberOsProdutos(String comoDesejaReceberOsProdutos) {
		this.comoDesejaReceberOsProdutos = comoDesejaReceberOsProdutos;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getEnderecoEntrega() {
		return enderecoEntrega;
	}

	public void setEnderecoEntrega(String enderecoEntrega) {
		this.enderecoEntrega = enderecoEntrega;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCentroDistribuicaoDoResponsavel() {
		return centroDistribuicaoDoResponsavel;
	}

	public void setCentroDistribuicaoDoResponsavel(String centroDistribuicaoDoResponsavel) {
		this.centroDistribuicaoDoResponsavel = centroDistribuicaoDoResponsavel;
	}

	public String getTipoPedido() {
		return tipoPedido;
	}

	public void setTipoPedido(String tipoPedido) {
		this.tipoPedido = tipoPedido;
	}

	public String getCodigoOutroDistribuidor() {
		return codigoOutroDistribuidor;
	}

	public void setCodigoOutroDistribuidor(String codigoOutroDistribuidor) {
		this.codigoOutroDistribuidor = codigoOutroDistribuidor;
	}

	public String getCredito() {
		return credito;
	}

	public void setCredito(String credito) {
		this.credito = credito;
	}

}
