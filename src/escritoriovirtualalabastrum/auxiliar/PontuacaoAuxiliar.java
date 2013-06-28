package escritoriovirtualalabastrum.auxiliar;

import java.math.BigDecimal;

public class PontuacaoAuxiliar {

	private MalaDireta malaDireta;
	private BigDecimal pontuacaoIngresso;
	private BigDecimal pontuacaoProdutos;
	private BigDecimal pontuacaoAtividade;

	public PontuacaoAuxiliar() {

		this.pontuacaoAtividade = BigDecimal.ZERO;
		this.pontuacaoProdutos = BigDecimal.ZERO;
		this.pontuacaoIngresso = BigDecimal.ZERO;
	}

	public BigDecimal getTotal() {

		return pontuacaoAtividade.add(pontuacaoProdutos).add(pontuacaoIngresso);
	}

	public MalaDireta getMalaDireta() {
		return malaDireta;
	}

	public void setMalaDireta(MalaDireta malaDireta) {
		this.malaDireta = malaDireta;
	}

	public BigDecimal getPontuacaoIngresso() {
		return pontuacaoIngresso;
	}

	public void setPontuacaoIngresso(BigDecimal pontuacaoIngresso) {
		this.pontuacaoIngresso = pontuacaoIngresso;
	}

	public BigDecimal getPontuacaoProdutos() {
		return pontuacaoProdutos;
	}

	public void setPontuacaoProdutos(BigDecimal pontuacaoProdutos) {
		this.pontuacaoProdutos = pontuacaoProdutos;
	}

	public BigDecimal getPontuacaoAtividade() {
		return pontuacaoAtividade;
	}

	public void setPontuacaoAtividade(BigDecimal pontuacaoAtividade) {
		this.pontuacaoAtividade = pontuacaoAtividade;
	}

}