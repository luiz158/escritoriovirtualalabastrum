package escritoriovirtualalabastrum.modelo;

import java.math.BigDecimal;
import java.util.GregorianCalendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import escritoriovirtualalabastrum.hibernate.Entidade;

@Entity
public class FixoIngresso implements Entidade {

	@Id
	@GeneratedValue
	private Integer id;

	private GregorianCalendar data_referencia;
	private String geracao;
	private BigDecimal basico;
	private BigDecimal especial;
	private BigDecimal vip;
	private BigDecimal top;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public GregorianCalendar getData_referencia() {
		return data_referencia;
	}

	public void setData_referencia(GregorianCalendar data_referencia) {
		this.data_referencia = data_referencia;
	}

	public String getGeracao() {
		return geracao;
	}

	public void setGeracao(String geracao) {
		this.geracao = geracao;
	}

	public BigDecimal getBasico() {
		return basico;
	}

	public void setBasico(BigDecimal basico) {
		this.basico = basico;
	}

	public BigDecimal getEspecial() {
		return especial;
	}

	public void setEspecial(BigDecimal especial) {
		this.especial = especial;
	}

	public BigDecimal getVip() {
		return vip;
	}

	public void setVip(BigDecimal vip) {
		this.vip = vip;
	}

	public BigDecimal getTop() {
		return top;
	}

	public void setTop(BigDecimal top) {
		this.top = top;
	}
}
