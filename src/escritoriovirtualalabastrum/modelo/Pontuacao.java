package escritoriovirtualalabastrum.modelo;

import java.math.BigDecimal;
import java.util.GregorianCalendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.Index;

import escritoriovirtualalabastrum.hibernate.Entidade;

@Entity
public class Pontuacao implements Entidade {

	@Id
	@GeneratedValue
	private Integer id;

	GregorianCalendar Dt_Pontos;
	BigDecimal ParametroProduto;
	BigDecimal ParametroAtividade;
	BigDecimal PntIngresso;
	BigDecimal PntProduto;
	BigDecimal PntAtividade;

	@Index(name = "index_id_Codigo_pontuacao")
	private Integer id_Codigo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public GregorianCalendar getDt_Pontos() {
		return Dt_Pontos;
	}

	public void setDt_Pontos(GregorianCalendar dt_Pontos) {
		Dt_Pontos = dt_Pontos;
	}

	public BigDecimal getParametroProduto() {
		return ParametroProduto;
	}

	public void setParametroProduto(BigDecimal parametroProduto) {
		ParametroProduto = parametroProduto;
	}

	public BigDecimal getParametroAtividade() {
		return ParametroAtividade;
	}

	public void setParametroAtividade(BigDecimal parametroAtividade) {
		ParametroAtividade = parametroAtividade;
	}

	public BigDecimal getPntIngresso() {
		return PntIngresso;
	}

	public void setPntIngresso(BigDecimal pntIngresso) {
		PntIngresso = pntIngresso;
	}

	public BigDecimal getPntProduto() {
		return PntProduto;
	}

	public void setPntProduto(BigDecimal pntProduto) {
		PntProduto = pntProduto;
	}

	public BigDecimal getPntAtividade() {
		return PntAtividade;
	}

	public void setPntAtividade(BigDecimal pntAtividade) {
		PntAtividade = pntAtividade;
	}

	public Integer getId_Codigo() {
		return id_Codigo;
	}

	public void setId_Codigo(Integer id_Codigo) {
		this.id_Codigo = id_Codigo;
	}

}