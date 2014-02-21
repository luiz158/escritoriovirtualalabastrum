package escritoriovirtualalabastrum.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import br.com.caelum.vraptor.Resource;
import escritoriovirtualalabastrum.hibernate.Entidade;

@Resource
@Entity
public class PreCadastro implements Entidade {

	@Id
	@GeneratedValue
	private Integer id;

	private String cpf;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
}
