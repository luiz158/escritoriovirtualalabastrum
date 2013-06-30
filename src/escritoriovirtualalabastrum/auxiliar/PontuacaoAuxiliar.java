package escritoriovirtualalabastrum.auxiliar;

import java.math.BigDecimal;

import escritoriovirtualalabastrum.util.Util;

public class PontuacaoAuxiliar {

	private MalaDireta malaDireta;
	private BigDecimal pontuacaoIngresso;
	private BigDecimal pontuacaoProdutos;
	private BigDecimal pontuacaoAtividade;
	private BigDecimal ParametroAtividade;
	private BigDecimal total;
	private String totalFormatado;
	private String ativo;

	public PontuacaoAuxiliar() {

		this.pontuacaoAtividade = BigDecimal.ZERO;
		this.pontuacaoProdutos = BigDecimal.ZERO;
		this.pontuacaoIngresso = BigDecimal.ZERO;
	}

	public boolean isAtivo() {

		if (Util.preenchido(ParametroAtividade) && pontuacaoAtividade.compareTo(ParametroAtividade) >= 0) {

			return true;
		}

		else
			return false;
	}

	public BigDecimal getTotal() {

		return pontuacaoAtividade.add(pontuacaoProdutos).add(pontuacaoIngresso);
	}

	public void calcularTotal() {

		this.total = getTotal();

		this.totalFormatado = Util.formatarBigDecimal(this.total);
	}

	public void verificarAtividade() {

		if (isAtivo()) {

			this.ativo = "Sim";
		}

		else {

			this.ativo = "Não";
		}
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

	public BigDecimal getParametroAtividade() {
		return ParametroAtividade;
	}

	public void setParametroAtividade(BigDecimal parametroAtividade) {
		ParametroAtividade = parametroAtividade;
	}

	public String getAtivo() {
		return ativo;
	}

	public void setAtivo(String ativo) {
		this.ativo = ativo;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

}