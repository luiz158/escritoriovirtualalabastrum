package escritoriovirtualalabastrum.modelo;

import java.util.GregorianCalendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.Index;

import escritoriovirtualalabastrum.hibernate.Entidade;

@Entity
public class AutorizacaoAtivacaoAutomatica implements Entidade {

	@Id
	@GeneratedValue
	private Integer id;

	@Index(name = "index_id_Codigo")
	private Integer id_Codigo;

	private GregorianCalendar dataAutorizacao;

	private String autorizacao;

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

	public GregorianCalendar getDataAutorizacao() {
		return dataAutorizacao;
	}

	public void setDataAutorizacao(GregorianCalendar dataAutorizacao) {
		this.dataAutorizacao = dataAutorizacao;
	}

	public String getAutorizacao() {
		return autorizacao;
	}

	public void setAutorizacao(String autorizacao) {
		this.autorizacao = autorizacao;
	}
}