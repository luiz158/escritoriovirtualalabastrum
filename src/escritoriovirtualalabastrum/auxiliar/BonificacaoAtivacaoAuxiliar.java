package escritoriovirtualalabastrum.auxiliar;

import java.math.BigDecimal;

public class BonificacaoAtivacaoAuxiliar {

	private MalaDireta malaDireta;
	private BigDecimal bonificacao;

	public MalaDireta getMalaDireta() {
		return malaDireta;
	}

	public void setMalaDireta(MalaDireta malaDireta) {
		this.malaDireta = malaDireta;
	}

	public BigDecimal getBonificacao() {
		return bonificacao;
	}

	public void setBonificacao(BigDecimal bonificacao) {
		this.bonificacao = bonificacao;
	}
}