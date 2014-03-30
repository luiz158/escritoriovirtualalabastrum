package escritoriovirtualalabastrum.modelo;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import escritoriovirtualalabastrum.hibernate.Entidade;
import escritoriovirtualalabastrum.hibernate.HibernateUtil;
import escritoriovirtualalabastrum.util.Util;

@Entity
public class BonificacaoRede implements Entidade {

	@Id
	@GeneratedValue
	private Integer id;

	private Integer id_Codigo;
	private String qualificacao;

	private BigDecimal bonificacaoIngresso;
	private BigDecimal bonificacaoAtivacao;
	private BigDecimal bonificacaoCompraPessoal;
	private BigDecimal bonificacaoGraduacao;
	private BigDecimal total;

	public Usuario getUsuario() {

		if (Util.preenchido(this.id_Codigo)) {

			HibernateUtil hibernateUtil = new HibernateUtil();

			Usuario usuario = new Usuario(this.id_Codigo);

			usuario = hibernateUtil.selecionar(usuario);

			hibernateUtil.fecharSessao();

			return usuario;
		}

		return null;
	}

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

	public String getQualificacao() {
		return qualificacao;
	}

	public void setQualificacao(String qualificacao) {
		this.qualificacao = qualificacao;
	}

	public BigDecimal getBonificacaoIngresso() {
		return bonificacaoIngresso;
	}

	public void setBonificacaoIngresso(BigDecimal bonificacaoIngresso) {
		this.bonificacaoIngresso = bonificacaoIngresso;
	}

	public BigDecimal getBonificacaoAtivacao() {
		return bonificacaoAtivacao;
	}

	public void setBonificacaoAtivacao(BigDecimal bonificacaoAtivacao) {
		this.bonificacaoAtivacao = bonificacaoAtivacao;
	}

	public BigDecimal getBonificacaoCompraPessoal() {
		return bonificacaoCompraPessoal;
	}

	public void setBonificacaoCompraPessoal(BigDecimal bonificacaoCompraPessoal) {
		this.bonificacaoCompraPessoal = bonificacaoCompraPessoal;
	}

	public BigDecimal getBonificacaoGraduacao() {
		return bonificacaoGraduacao;
	}

	public void setBonificacaoGraduacao(BigDecimal bonificacaoGraduacao) {
		this.bonificacaoGraduacao = bonificacaoGraduacao;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}
}
