package escritoriovirtualalabastrum.modelo;

import java.math.BigDecimal;
import java.util.GregorianCalendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import escritoriovirtualalabastrum.hibernate.Entidade;

@Entity
public class PorcentagemIngresso implements Entidade {

	@Id
	@GeneratedValue
	private Integer id;

	private GregorianCalendar data_referencia;
	private BigDecimal basico_porcentagem;
	private BigDecimal basico_pontuacao;
	private BigDecimal especial_porcentagem;
	private BigDecimal especial_pontuacao;
	private BigDecimal vip_porcentagem;
	private BigDecimal vip_pontuacao;
	private BigDecimal top_porcentagem;
	private BigDecimal top_pontuacao;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public GregorianCalendar getData_referencia() {
		return data_referencia;
	}

	public void setData_referencia(GregorianCalendar data_referencia) {
		this.data_referencia = data_referencia;
	}

	public BigDecimal getBasico_porcentagem() {
		return basico_porcentagem;
	}

	public void setBasico_porcentagem(BigDecimal basico_porcentagem) {
		this.basico_porcentagem = basico_porcentagem;
	}

	public BigDecimal getBasico_pontuacao() {
		return basico_pontuacao;
	}

	public void setBasico_pontuacao(BigDecimal basico_pontuacao) {
		this.basico_pontuacao = basico_pontuacao;
	}

	public BigDecimal getEspecial_porcentagem() {
		return especial_porcentagem;
	}

	public void setEspecial_porcentagem(BigDecimal especial_porcentagem) {
		this.especial_porcentagem = especial_porcentagem;
	}

	public BigDecimal getEspecial_pontuacao() {
		return especial_pontuacao;
	}

	public void setEspecial_pontuacao(BigDecimal especial_pontuacao) {
		this.especial_pontuacao = especial_pontuacao;
	}

	public BigDecimal getVip_porcentagem() {
		return vip_porcentagem;
	}

	public void setVip_porcentagem(BigDecimal vip_porcentagem) {
		this.vip_porcentagem = vip_porcentagem;
	}

	public BigDecimal getVip_pontuacao() {
		return vip_pontuacao;
	}

	public void setVip_pontuacao(BigDecimal vip_pontuacao) {
		this.vip_pontuacao = vip_pontuacao;
	}

	public BigDecimal getTop_porcentagem() {
		return top_porcentagem;
	}

	public void setTop_porcentagem(BigDecimal top_porcentagem) {
		this.top_porcentagem = top_porcentagem;
	}

	public BigDecimal getTop_pontuacao() {
		return top_pontuacao;
	}

	public void setTop_pontuacao(BigDecimal top_pontuacao) {
		this.top_pontuacao = top_pontuacao;
	}
}
