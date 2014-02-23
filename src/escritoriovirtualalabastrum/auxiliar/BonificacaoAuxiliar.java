package escritoriovirtualalabastrum.auxiliar;

import java.math.BigDecimal;

import escritoriovirtualalabastrum.modelo.Usuario;

public class BonificacaoAuxiliar {

	private Usuario usuario;
	private BigDecimal bonificacao;
	private BigDecimal pontuacao;
	private String kit;
	private String comoFoiCalculado;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public BigDecimal getBonificacao() {
		return bonificacao;
	}

	public void setBonificacao(BigDecimal bonificacao) {
		this.bonificacao = bonificacao;
	}

	public String getKit() {
		return kit;
	}

	public void setKit(String kit) {
		this.kit = kit;
	}

	public String getComoFoiCalculado() {
		return comoFoiCalculado;
	}

	public void setComoFoiCalculado(String comoFoiCalculado) {
		this.comoFoiCalculado = comoFoiCalculado;
	}

	public BigDecimal getPontuacao() {
		return pontuacao;
	}

	public void setPontuacao(BigDecimal pontuacao) {
		this.pontuacao = pontuacao;
	}
}