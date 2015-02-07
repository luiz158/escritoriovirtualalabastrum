package escritoriovirtualalabastrum.modelo;

import java.math.BigDecimal;
import java.util.GregorianCalendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import escritoriovirtualalabastrum.hibernate.Entidade;

@Entity
public class Atividade implements Entidade {

	@Id
	@GeneratedValue
	private Integer id;

	private GregorianCalendar data_referencia;
	private BigDecimal valorAtividade;
	private BigDecimal bonusAtividade;

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

	public BigDecimal getValorAtividade() {
		return valorAtividade;
	}

	public void setValorAtividade(BigDecimal valorAtividade) {
		this.valorAtividade = valorAtividade;
	}

	public BigDecimal getBonusAtividade() {
		return bonusAtividade;
	}

	public void setBonusAtividade(BigDecimal bonusAtividade) {
		this.bonusAtividade = bonusAtividade;
	}

}
