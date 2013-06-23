package escritoriovirtualalabastrum.modelo;

import java.util.GregorianCalendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import escritoriovirtualalabastrum.hibernate.Entidade;

@Entity
public class InformacoesUltimaAtualizacao implements Entidade {

	@Id
	@GeneratedValue
	private Integer id;

	private GregorianCalendar dataHora;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public GregorianCalendar getDataHora() {
		return dataHora;
	}

	public void setDataHora(GregorianCalendar dataHora) {
		this.dataHora = dataHora;
	}

}
