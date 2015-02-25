package escritoriovirtualalabastrum.modelo;

import java.math.BigDecimal;
import java.util.GregorianCalendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.Index;

import escritoriovirtualalabastrum.hibernate.Entidade;

@Entity
public class CotasDivisao implements Entidade {

	@Id
	@GeneratedValue
	private Integer id;

	@Index(name = "index_id_Codigo_cotas_divisao")
	private Integer id_Codigo;

	private GregorianCalendar data_referencia;
	private BigDecimal nr_cotas;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId_Codigo() {
		return id_Codigo;
	}

	public void setId_Codigo(Integer id_Codigo) {
		this.id_Codigo = id_Codigo;
	}

	public GregorianCalendar getData_referencia() {
		return data_referencia;
	}

	public void setData_referencia(GregorianCalendar data_referencia) {
		this.data_referencia = data_referencia;
	}

	public BigDecimal getNr_cotas() {
		return nr_cotas;
	}

	public void setNr_cotas(BigDecimal nr_cotas) {
		this.nr_cotas = nr_cotas;
	}
}
