package escritoriovirtualalabastrum.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.Index;

import escritoriovirtualalabastrum.hibernate.Entidade;

@Entity
public class Categoria implements Entidade {

	@Id
	@GeneratedValue
	private Integer id;

	private String catNome;

	@Index(name = "index_id_Categoria_tabela_categoria")
	private Integer id_Categoria;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCatNome() {
		return catNome;
	}

	public void setCatNome(String catNome) {
		this.catNome = catNome;
	}

	public Integer getId_Categoria() {
		return id_Categoria;
	}

	public void setId_Categoria(Integer id_Categoria) {
		this.id_Categoria = id_Categoria;
	}

}
