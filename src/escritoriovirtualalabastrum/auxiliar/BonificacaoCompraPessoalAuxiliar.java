package escritoriovirtualalabastrum.auxiliar;

import java.math.BigDecimal;

public class BonificacaoCompraPessoalAuxiliar {

	private String graduacao;
	private BigDecimal porcentagem;
	private BigDecimal bonificacao;

	public String getGraduacao() {
		return graduacao;
	}

	public void setGraduacao(String graduacao) {
		this.graduacao = graduacao;
	}

	public BigDecimal getPorcentagem() {
		return porcentagem;
	}

	public void setPorcentagem(BigDecimal porcentagem) {
		this.porcentagem = porcentagem;
	}

	public BigDecimal getBonificacao() {
		return bonificacao;
	}

	public void setBonificacao(BigDecimal bonificacao) {
		this.bonificacao = bonificacao;
	}
}