package escritoriovirtualalabastrum.modelo;

import java.math.BigDecimal;
import java.util.GregorianCalendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.hibernate.annotations.Index;

import escritoriovirtualalabastrum.hibernate.Entidade;

@Entity
public class ControlePedido implements Entidade {

	@Id
	@GeneratedValue
	private Integer id;

	private String pedNumero;
	private GregorianCalendar pedData;
	private BigDecimal BaseCalculo;
	private BigDecimal pedOutrosValores;
	private BigDecimal pedValorPago;
	private BigDecimal QuantProdutos;
	private BigDecimal PontoProduto;
	private BigDecimal PontoAtividade;
	private BigDecimal PontoIngresso;

	@Index(name = "index_id_Codigo_controle_pedido")
	private Integer id_Codigo;

	@Transient
	private Usuario usuario;

	public Usuario getUsuario() {

		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPedNumero() {
		return pedNumero;
	}

	public void setPedNumero(String pedNumero) {
		this.pedNumero = pedNumero;
	}

	public GregorianCalendar getPedData() {
		return pedData;
	}

	public void setPedData(GregorianCalendar pedData) {
		this.pedData = pedData;
	}

	public BigDecimal getBaseCalculo() {
		return BaseCalculo;
	}

	public void setBaseCalculo(BigDecimal baseCalculo) {
		BaseCalculo = baseCalculo;
	}

	public BigDecimal getPedOutrosValores() {
		return pedOutrosValores;
	}

	public void setPedOutrosValores(BigDecimal pedOutrosValores) {
		this.pedOutrosValores = pedOutrosValores;
	}

	public BigDecimal getPedValorPago() {
		return pedValorPago;
	}

	public void setPedValorPago(BigDecimal pedValorPago) {
		this.pedValorPago = pedValorPago;
	}

	public BigDecimal getQuantProdutos() {
		return QuantProdutos;
	}

	public void setQuantProdutos(BigDecimal quantProdutos) {
		QuantProdutos = quantProdutos;
	}

	public BigDecimal getPontoProduto() {
		return PontoProduto;
	}

	public void setPontoProduto(BigDecimal pontoProduto) {
		PontoProduto = pontoProduto;
	}

	public BigDecimal getPontoAtividade() {
		return PontoAtividade;
	}

	public void setPontoAtividade(BigDecimal pontoAtividade) {
		PontoAtividade = pontoAtividade;
	}

	public BigDecimal getPontoIngresso() {
		return PontoIngresso;
	}

	public void setPontoIngresso(BigDecimal pontoIngresso) {
		PontoIngresso = pontoIngresso;
	}

	public Integer getId_Codigo() {
		return id_Codigo;
	}

	public void setId_Codigo(Integer id_Codigo) {
		this.id_Codigo = id_Codigo;
	}
}
