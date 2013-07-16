package escritoriovirtualalabastrum.sessao;

import java.util.LinkedHashMap;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.SessionScoped;

@Component
@SessionScoped
public class SessaoPedido {

	private LinkedHashMap<String, Integer> produtosEQuantidades;

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

	public LinkedHashMap<String, Integer> getProdutosEQuantidades() {
		return produtosEQuantidades;
	}

	public void setProdutosEQuantidades(LinkedHashMap<String, Integer> produtosEQuantidades) {
		this.produtosEQuantidades = produtosEQuantidades;
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

}
