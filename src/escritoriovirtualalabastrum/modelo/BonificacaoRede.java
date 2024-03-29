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
	private BigDecimal bonificacaoAtivacao2;
	private BigDecimal bonificacaoAtivacao3;
	private BigDecimal bonificacaoCompraPessoal;
	private BigDecimal bonificacaoInicioRapido;
	private BigDecimal bonificacaoGraduacao;
	private BigDecimal bonificacaoUniLevel;
	private BigDecimal bonificacaoExpansao;
	private BigDecimal bonificacaoPontoDeApoio;
	private BigDecimal BonificacaoDivisao;
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

	public BigDecimal getBonificacaoAtivacao2() {
		return bonificacaoAtivacao2;
	}

	public void setBonificacaoAtivacao2(BigDecimal bonificacaoAtivacao2) {
		this.bonificacaoAtivacao2 = bonificacaoAtivacao2;
	}

	public BigDecimal getBonificacaoAtivacao3() {
		return bonificacaoAtivacao3;
	}

	public void setBonificacaoAtivacao3(BigDecimal bonificacaoAtivacao3) {
		this.bonificacaoAtivacao3 = bonificacaoAtivacao3;
	}

	public BigDecimal getBonificacaoCompraPessoal() {
		return bonificacaoCompraPessoal;
	}

	public void setBonificacaoCompraPessoal(BigDecimal bonificacaoCompraPessoal) {
		this.bonificacaoCompraPessoal = bonificacaoCompraPessoal;
	}

	public BigDecimal getBonificacaoInicioRapido() {
		return bonificacaoInicioRapido;
	}

	public void setBonificacaoInicioRapido(BigDecimal bonificacaoInicioRapido) {
		this.bonificacaoInicioRapido = bonificacaoInicioRapido;
	}

	public BigDecimal getBonificacaoGraduacao() {
		return bonificacaoGraduacao;
	}

	public void setBonificacaoGraduacao(BigDecimal bonificacaoGraduacao) {
		this.bonificacaoGraduacao = bonificacaoGraduacao;
	}

	public BigDecimal getBonificacaoUniLevel() {
		return bonificacaoUniLevel;
	}

	public void setBonificacaoUniLevel(BigDecimal bonificacaoUniLevel) {
		this.bonificacaoUniLevel = bonificacaoUniLevel;
	}

	public BigDecimal getBonificacaoExpansao() {
		return bonificacaoExpansao;
	}

	public void setBonificacaoExpansao(BigDecimal bonificacaoExpansao) {
		this.bonificacaoExpansao = bonificacaoExpansao;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public BigDecimal getBonificacaoPontoDeApoio() {
		return bonificacaoPontoDeApoio;
	}

	public void setBonificacaoPontoDeApoio(BigDecimal bonificacaoPontoDeApoio) {
		this.bonificacaoPontoDeApoio = bonificacaoPontoDeApoio;
	}

	public BigDecimal getBonificacaoDivisao() {
		return BonificacaoDivisao;
	}

	public void setBonificacaoDivisao(BigDecimal bonificacaoDivisao) {
		BonificacaoDivisao = bonificacaoDivisao;
	}

}
