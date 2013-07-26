package escritoriovirtualalabastrum.auxiliar;

import java.math.BigDecimal;

public class PontuacaoAnualAuxiliar {

	private MalaDireta malaDireta;

	private BigDecimal pontuacaoProdutosJaneiro;
	private BigDecimal pontuacaoAtividadeJaneiro;

	private BigDecimal pontuacaoProdutosFevereiro;
	private BigDecimal pontuacaoAtividadeFevereiro;

	private BigDecimal pontuacaoProdutosMarco;
	private BigDecimal pontuacaoAtividadeMarco;

	private BigDecimal pontuacaoProdutosAbril;
	private BigDecimal pontuacaoAtividadeAbril;

	private BigDecimal pontuacaoProdutosMaio;
	private BigDecimal pontuacaoAtividadeMaio;

	private BigDecimal pontuacaoProdutosJunho;
	private BigDecimal pontuacaoAtividadeJunho;

	private BigDecimal pontuacaoProdutosJulho;
	private BigDecimal pontuacaoAtividadeJulho;

	private BigDecimal pontuacaoProdutosAgosto;
	private BigDecimal pontuacaoAtividadeAgosto;

	private BigDecimal pontuacaoProdutosSetembro;
	private BigDecimal pontuacaoAtividadeSetembro;

	private BigDecimal pontuacaoProdutosOutubro;
	private BigDecimal pontuacaoAtividadeOutubro;

	private BigDecimal pontuacaoProdutosNovembro;
	private BigDecimal pontuacaoAtividadeNovembro;

	private BigDecimal pontuacaoProdutosDezembro;
	private BigDecimal pontuacaoAtividadeDezembro;

	public PontuacaoAnualAuxiliar() {

		this.pontuacaoProdutosJaneiro = BigDecimal.ZERO;
		this.pontuacaoAtividadeJaneiro = BigDecimal.ZERO;

		this.pontuacaoProdutosFevereiro = BigDecimal.ZERO;
		this.pontuacaoAtividadeFevereiro = BigDecimal.ZERO;

		this.pontuacaoProdutosMarco = BigDecimal.ZERO;
		this.pontuacaoAtividadeMarco = BigDecimal.ZERO;

		this.pontuacaoProdutosAbril = BigDecimal.ZERO;
		this.pontuacaoAtividadeAbril = BigDecimal.ZERO;

		this.pontuacaoProdutosMaio = BigDecimal.ZERO;
		this.pontuacaoAtividadeMaio = BigDecimal.ZERO;

		this.pontuacaoProdutosJunho = BigDecimal.ZERO;
		this.pontuacaoAtividadeJunho = BigDecimal.ZERO;

		this.pontuacaoProdutosJulho = BigDecimal.ZERO;
		this.pontuacaoAtividadeJulho = BigDecimal.ZERO;

		this.pontuacaoProdutosAgosto = BigDecimal.ZERO;
		this.pontuacaoAtividadeAgosto = BigDecimal.ZERO;

		this.pontuacaoProdutosSetembro = BigDecimal.ZERO;
		this.pontuacaoAtividadeSetembro = BigDecimal.ZERO;

		this.pontuacaoProdutosOutubro = BigDecimal.ZERO;
		this.pontuacaoAtividadeOutubro = BigDecimal.ZERO;

		this.pontuacaoProdutosNovembro = BigDecimal.ZERO;
		this.pontuacaoAtividadeNovembro = BigDecimal.ZERO;

		this.pontuacaoProdutosDezembro = BigDecimal.ZERO;
		this.pontuacaoAtividadeDezembro = BigDecimal.ZERO;
	}

	public BigDecimal getPontuacaoProdutosTotal() {

		return pontuacaoProdutosJaneiro.add(pontuacaoProdutosFevereiro.add(pontuacaoProdutosMarco.add(pontuacaoProdutosAbril.add(pontuacaoProdutosMaio.add(pontuacaoProdutosJunho.add(pontuacaoProdutosJulho.add(pontuacaoProdutosAgosto.add(pontuacaoProdutosSetembro.add(pontuacaoProdutosOutubro.add(pontuacaoProdutosNovembro.add(pontuacaoProdutosDezembro)))))))))));
	}

	public BigDecimal getPontuacaoAtividadeTotal() {

		return pontuacaoAtividadeJaneiro.add(pontuacaoAtividadeFevereiro.add(pontuacaoAtividadeMarco.add(pontuacaoAtividadeAbril.add(pontuacaoAtividadeMaio.add(pontuacaoAtividadeJunho.add(pontuacaoAtividadeJulho.add(pontuacaoAtividadeAgosto.add(pontuacaoAtividadeSetembro.add(pontuacaoAtividadeOutubro.add(pontuacaoAtividadeNovembro.add(pontuacaoAtividadeDezembro)))))))))));
	}

	public MalaDireta getMalaDireta() {
		return malaDireta;
	}

	public void setMalaDireta(MalaDireta malaDireta) {
		this.malaDireta = malaDireta;
	}

	public BigDecimal getPontuacaoProdutosJaneiro() {
		return pontuacaoProdutosJaneiro;
	}

	public void setPontuacaoProdutosJaneiro(BigDecimal pontuacaoProdutosJaneiro) {
		this.pontuacaoProdutosJaneiro = pontuacaoProdutosJaneiro;
	}

	public BigDecimal getPontuacaoAtividadeJaneiro() {
		return pontuacaoAtividadeJaneiro;
	}

	public void setPontuacaoAtividadeJaneiro(BigDecimal pontuacaoAtividadeJaneiro) {
		this.pontuacaoAtividadeJaneiro = pontuacaoAtividadeJaneiro;
	}

	public BigDecimal getPontuacaoProdutosFevereiro() {
		return pontuacaoProdutosFevereiro;
	}

	public void setPontuacaoProdutosFevereiro(BigDecimal pontuacaoProdutosFevereiro) {
		this.pontuacaoProdutosFevereiro = pontuacaoProdutosFevereiro;
	}

	public BigDecimal getPontuacaoAtividadeFevereiro() {
		return pontuacaoAtividadeFevereiro;
	}

	public void setPontuacaoAtividadeFevereiro(BigDecimal pontuacaoAtividadeFevereiro) {
		this.pontuacaoAtividadeFevereiro = pontuacaoAtividadeFevereiro;
	}

	public BigDecimal getPontuacaoProdutosMarco() {
		return pontuacaoProdutosMarco;
	}

	public void setPontuacaoProdutosMarco(BigDecimal pontuacaoProdutosMarco) {
		this.pontuacaoProdutosMarco = pontuacaoProdutosMarco;
	}

	public BigDecimal getPontuacaoAtividadeMarco() {
		return pontuacaoAtividadeMarco;
	}

	public void setPontuacaoAtividadeMarco(BigDecimal pontuacaoAtividadeMarco) {
		this.pontuacaoAtividadeMarco = pontuacaoAtividadeMarco;
	}

	public BigDecimal getPontuacaoProdutosAbril() {
		return pontuacaoProdutosAbril;
	}

	public void setPontuacaoProdutosAbril(BigDecimal pontuacaoProdutosAbril) {
		this.pontuacaoProdutosAbril = pontuacaoProdutosAbril;
	}

	public BigDecimal getPontuacaoAtividadeAbril() {
		return pontuacaoAtividadeAbril;
	}

	public void setPontuacaoAtividadeAbril(BigDecimal pontuacaoAtividadeAbril) {
		this.pontuacaoAtividadeAbril = pontuacaoAtividadeAbril;
	}

	public BigDecimal getPontuacaoProdutosMaio() {
		return pontuacaoProdutosMaio;
	}

	public void setPontuacaoProdutosMaio(BigDecimal pontuacaoProdutosMaio) {
		this.pontuacaoProdutosMaio = pontuacaoProdutosMaio;
	}

	public BigDecimal getPontuacaoAtividadeMaio() {
		return pontuacaoAtividadeMaio;
	}

	public void setPontuacaoAtividadeMaio(BigDecimal pontuacaoAtividadeMaio) {
		this.pontuacaoAtividadeMaio = pontuacaoAtividadeMaio;
	}

	public BigDecimal getPontuacaoProdutosJunho() {
		return pontuacaoProdutosJunho;
	}

	public void setPontuacaoProdutosJunho(BigDecimal pontuacaoProdutosJunho) {
		this.pontuacaoProdutosJunho = pontuacaoProdutosJunho;
	}

	public BigDecimal getPontuacaoAtividadeJunho() {
		return pontuacaoAtividadeJunho;
	}

	public void setPontuacaoAtividadeJunho(BigDecimal pontuacaoAtividadeJunho) {
		this.pontuacaoAtividadeJunho = pontuacaoAtividadeJunho;
	}

	public BigDecimal getPontuacaoProdutosJulho() {
		return pontuacaoProdutosJulho;
	}

	public void setPontuacaoProdutosJulho(BigDecimal pontuacaoProdutosJulho) {
		this.pontuacaoProdutosJulho = pontuacaoProdutosJulho;
	}

	public BigDecimal getPontuacaoAtividadeJulho() {
		return pontuacaoAtividadeJulho;
	}

	public void setPontuacaoAtividadeJulho(BigDecimal pontuacaoAtividadeJulho) {
		this.pontuacaoAtividadeJulho = pontuacaoAtividadeJulho;
	}

	public BigDecimal getPontuacaoProdutosAgosto() {
		return pontuacaoProdutosAgosto;
	}

	public void setPontuacaoProdutosAgosto(BigDecimal pontuacaoProdutosAgosto) {
		this.pontuacaoProdutosAgosto = pontuacaoProdutosAgosto;
	}

	public BigDecimal getPontuacaoAtividadeAgosto() {
		return pontuacaoAtividadeAgosto;
	}

	public void setPontuacaoAtividadeAgosto(BigDecimal pontuacaoAtividadeAgosto) {
		this.pontuacaoAtividadeAgosto = pontuacaoAtividadeAgosto;
	}

	public BigDecimal getPontuacaoProdutosSetembro() {
		return pontuacaoProdutosSetembro;
	}

	public void setPontuacaoProdutosSetembro(BigDecimal pontuacaoProdutosSetembro) {
		this.pontuacaoProdutosSetembro = pontuacaoProdutosSetembro;
	}

	public BigDecimal getPontuacaoAtividadeSetembro() {
		return pontuacaoAtividadeSetembro;
	}

	public void setPontuacaoAtividadeSetembro(BigDecimal pontuacaoAtividadeSetembro) {
		this.pontuacaoAtividadeSetembro = pontuacaoAtividadeSetembro;
	}

	public BigDecimal getPontuacaoProdutosOutubro() {
		return pontuacaoProdutosOutubro;
	}

	public void setPontuacaoProdutosOutubro(BigDecimal pontuacaoProdutosOutubro) {
		this.pontuacaoProdutosOutubro = pontuacaoProdutosOutubro;
	}

	public BigDecimal getPontuacaoAtividadeOutubro() {
		return pontuacaoAtividadeOutubro;
	}

	public void setPontuacaoAtividadeOutubro(BigDecimal pontuacaoAtividadeOutubro) {
		this.pontuacaoAtividadeOutubro = pontuacaoAtividadeOutubro;
	}

	public BigDecimal getPontuacaoProdutosNovembro() {
		return pontuacaoProdutosNovembro;
	}

	public void setPontuacaoProdutosNovembro(BigDecimal pontuacaoProdutosNovembro) {
		this.pontuacaoProdutosNovembro = pontuacaoProdutosNovembro;
	}

	public BigDecimal getPontuacaoAtividadeNovembro() {
		return pontuacaoAtividadeNovembro;
	}

	public void setPontuacaoAtividadeNovembro(BigDecimal pontuacaoAtividadeNovembro) {
		this.pontuacaoAtividadeNovembro = pontuacaoAtividadeNovembro;
	}

	public BigDecimal getPontuacaoProdutosDezembro() {
		return pontuacaoProdutosDezembro;
	}

	public void setPontuacaoProdutosDezembro(BigDecimal pontuacaoProdutosDezembro) {
		this.pontuacaoProdutosDezembro = pontuacaoProdutosDezembro;
	}

	public BigDecimal getPontuacaoAtividadeDezembro() {
		return pontuacaoAtividadeDezembro;
	}

	public void setPontuacaoAtividadeDezembro(BigDecimal pontuacaoAtividadeDezembro) {
		this.pontuacaoAtividadeDezembro = pontuacaoAtividadeDezembro;
	}

}