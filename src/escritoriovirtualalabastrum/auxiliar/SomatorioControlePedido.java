package escritoriovirtualalabastrum.auxiliar;

import java.math.BigDecimal;

public class SomatorioControlePedido {

	private BigDecimal somatorioBaseCalculo;
	private BigDecimal somatorioPedOutrosValores;
	private BigDecimal somatorioPedValorPago;
	private BigDecimal somatorioQuantProdutos;
	private BigDecimal somatorioPontoProduto;
	private BigDecimal somatorioPontoAtividade;
	private BigDecimal somatorioPontoIngresso;

	public SomatorioControlePedido() {

		this.somatorioBaseCalculo = BigDecimal.ZERO;
		this.somatorioPedOutrosValores = BigDecimal.ZERO;
		this.somatorioPedValorPago = BigDecimal.ZERO;
		this.somatorioQuantProdutos = BigDecimal.ZERO;
		this.somatorioPontoProduto = BigDecimal.ZERO;
		this.somatorioPontoAtividade = BigDecimal.ZERO;
		this.somatorioPontoIngresso = BigDecimal.ZERO;
	}

	public BigDecimal getSomatorioBaseCalculo() {
		return somatorioBaseCalculo;
	}

	public void setSomatorioBaseCalculo(BigDecimal somatorioBaseCalculo) {
		this.somatorioBaseCalculo = somatorioBaseCalculo;
	}

	public BigDecimal getSomatorioPedOutrosValores() {
		return somatorioPedOutrosValores;
	}

	public void setSomatorioPedOutrosValores(BigDecimal somatorioPedOutrosValores) {
		this.somatorioPedOutrosValores = somatorioPedOutrosValores;
	}

	public BigDecimal getSomatorioPedValorPago() {
		return somatorioPedValorPago;
	}

	public void setSomatorioPedValorPago(BigDecimal somatorioPedValorPago) {
		this.somatorioPedValorPago = somatorioPedValorPago;
	}

	public BigDecimal getSomatorioQuantProdutos() {
		return somatorioQuantProdutos;
	}

	public void setSomatorioQuantProdutos(BigDecimal somatorioQuantProdutos) {
		this.somatorioQuantProdutos = somatorioQuantProdutos;
	}

	public BigDecimal getSomatorioPontoProduto() {
		return somatorioPontoProduto;
	}

	public void setSomatorioPontoProduto(BigDecimal somatorioPontoProduto) {
		this.somatorioPontoProduto = somatorioPontoProduto;
	}

	public BigDecimal getSomatorioPontoAtividade() {
		return somatorioPontoAtividade;
	}

	public void setSomatorioPontoAtividade(BigDecimal somatorioPontoAtividade) {
		this.somatorioPontoAtividade = somatorioPontoAtividade;
	}

	public BigDecimal getSomatorioPontoIngresso() {
		return somatorioPontoIngresso;
	}

	public void setSomatorioPontoIngresso(BigDecimal somatorioPontoIngresso) {
		this.somatorioPontoIngresso = somatorioPontoIngresso;
	}

}