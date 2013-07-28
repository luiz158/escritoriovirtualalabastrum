package escritoriovirtualalabastrum.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.Index;

import escritoriovirtualalabastrum.hibernate.Entidade;

@Entity
public class CentroDistribuicao implements Entidade {

	@Id
	@GeneratedValue
	private Integer id;

	private String estqNome;
	private String estqEndereco;
	private String estqBairro;
	private String estqCidade;
	private String estqUF;
	private String estqCEP;
	private String estqTelefone;
	private String estqPercentual;
	private String estqEmail;

	@Index(name = "index_id_Codigo_tabela_centro_distribuicao")
	private Integer id_Codigo;

	@Index(name = "index_id_Estoque_tabela_centro_distribuicao")
	private Integer id_Estoque;

	public String getNomeCentroSemEspacos() {

		return this.estqNome.replaceAll(" ", "");
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEstqNome() {
		return estqNome;
	}

	public void setEstqNome(String estqNome) {
		this.estqNome = estqNome;
	}

	public String getEstqEndereco() {
		return estqEndereco;
	}

	public void setEstqEndereco(String estqEndereco) {
		this.estqEndereco = estqEndereco;
	}

	public String getEstqBairro() {
		return estqBairro;
	}

	public void setEstqBairro(String estqBairro) {
		this.estqBairro = estqBairro;
	}

	public String getEstqCidade() {
		return estqCidade;
	}

	public void setEstqCidade(String estqCidade) {
		this.estqCidade = estqCidade;
	}

	public String getEstqUF() {
		return estqUF;
	}

	public void setEstqUF(String estqUF) {
		this.estqUF = estqUF;
	}

	public String getEstqCEP() {
		return estqCEP;
	}

	public void setEstqCEP(String estqCEP) {
		this.estqCEP = estqCEP;
	}

	public String getEstqTelefone() {
		return estqTelefone;
	}

	public void setEstqTelefone(String estqTelefone) {
		this.estqTelefone = estqTelefone;
	}

	public String getEstqPercentual() {
		return estqPercentual;
	}

	public void setEstqPercentual(String estqPercentual) {
		this.estqPercentual = estqPercentual;
	}

	public String getEstqEmail() {
		return estqEmail;
	}

	public void setEstqEmail(String estqEmail) {
		this.estqEmail = estqEmail;
	}

	public Integer getId_Codigo() {
		return id_Codigo;
	}

	public void setId_Codigo(Integer id_Codigo) {
		this.id_Codigo = id_Codigo;
	}

	public Integer getId_Estoque() {
		return id_Estoque;
	}

	public void setId_Estoque(Integer id_Estoque) {
		this.id_Estoque = id_Estoque;
	}

}
