package escritoriovirtualalabastrum.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import escritoriovirtualalabastrum.hibernate.Entidade;

@Entity
public class Usuario implements Entidade {

	@Id
	@GeneratedValue
	private Integer id;

	@NotNull
	private String login;

	@NotNull
	private String nome;

	@NotNull
	private String senha;

	private Boolean administrador;

	public Usuario() {

	}

	public Usuario(Integer id) {

		this.setId(id);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Boolean getAdministrador() {
		return administrador;
	}

	public void setAdministrador(Boolean administrador) {
		this.administrador = administrador;
	}

}
