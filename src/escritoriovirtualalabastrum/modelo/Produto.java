package escritoriovirtualalabastrum.modelo;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.hibernate.annotations.Index;

import escritoriovirtualalabastrum.hibernate.Entidade;

@Entity
public class Produto implements Entidade {

	@Id
	@GeneratedValue
	private Integer id;

	private String prdNome;
	private BigDecimal prdPreco_Unit;
	private BigDecimal prdBrinde;
	private BigDecimal prdComissionado;
	private BigDecimal prdMatApoio;
	private BigDecimal prdPromocao;
	private BigDecimal prdBonus;
	private BigDecimal prdPeso;
	private BigDecimal prdPontos;

	@Transient
	private Integer quantidade;

	@Index(name = "index_id_Produtos")
	private String id_Produtos;

	@Index(name = "index_id_Categoria_tabela_produto")
	private Integer id_Categoria;

	public BigDecimal getTotal() {

		return this.prdPreco_Unit.multiply(BigDecimal.valueOf((this.quantidade)));
	}
	
	public BigDecimal getPontuacaoTotal() {

		return this.prdPontos.multiply(BigDecimal.valueOf((this.quantidade)));
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPrdNome() {
		return prdNome;
	}

	public void setPrdNome(String prdNome) {
		this.prdNome = prdNome;
	}

	public BigDecimal getPrdPreco_Unit() {
		return prdPreco_Unit;
	}

	public void setPrdPreco_Unit(BigDecimal prdPreco_Unit) {
		this.prdPreco_Unit = prdPreco_Unit;
	}

	public BigDecimal getPrdBrinde() {
		return prdBrinde;
	}

	public void setPrdBrinde(BigDecimal prdBrinde) {
		this.prdBrinde = prdBrinde;
	}

	public BigDecimal getPrdComissionado() {
		return prdComissionado;
	}

	public void setPrdComissionado(BigDecimal prdComissionado) {
		this.prdComissionado = prdComissionado;
	}

	public BigDecimal getPrdMatApoio() {
		return prdMatApoio;
	}

	public void setPrdMatApoio(BigDecimal prdMatApoio) {
		this.prdMatApoio = prdMatApoio;
	}

	public BigDecimal getPrdPromocao() {
		return prdPromocao;
	}

	public void setPrdPromocao(BigDecimal prdPromocao) {
		this.prdPromocao = prdPromocao;
	}

	public BigDecimal getPrdBonus() {
		return prdBonus;
	}

	public void setPrdBonus(BigDecimal prdBonus) {
		this.prdBonus = prdBonus;
	}

	public BigDecimal getPrdPeso() {
		return prdPeso;
	}

	public void setPrdPeso(BigDecimal prdPeso) {
		this.prdPeso = prdPeso;
	}

	public BigDecimal getPrdPontos() {
		return prdPontos;
	}

	public void setPrdPontos(BigDecimal prdPontos) {
		this.prdPontos = prdPontos;
	}

	public String getId_Produtos() {
		return id_Produtos;
	}

	public void setId_Produtos(String id_Produtos) {
		this.id_Produtos = id_Produtos;
	}

	public Integer getId_Categoria() {
		return id_Categoria;
	}

	public void setId_Categoria(Integer id_Categoria) {
		this.id_Categoria = id_Categoria;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
}
