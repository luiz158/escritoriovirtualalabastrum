package escritoriovirtualalabastrum.modelo;

import java.util.GregorianCalendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.Index;

import escritoriovirtualalabastrum.hibernate.Entidade;

@Entity
public class HistoricoKit implements Entidade {

	@Id
	@GeneratedValue
	private Integer id;

	@Index(name = "index_id_Codigo_historico_kit")
	private Integer id_codigo;

	private GregorianCalendar data_referencia;
	private String kit;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId_codigo() {
		return id_codigo;
	}

	public void setId_codigo(Integer id_codigo) {
		this.id_codigo = id_codigo;
	}

	public GregorianCalendar getData_referencia() {
		return data_referencia;
	}

	public void setData_referencia(GregorianCalendar data_referencia) {
		this.data_referencia = data_referencia;
	}

	public String getKit() {
		return kit;
	}

	public void setKit(String kit) {
		this.kit = kit;
	}
}
