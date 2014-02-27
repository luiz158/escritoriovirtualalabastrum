package escritoriovirtualalabastrum.auxiliar;

import java.math.BigDecimal;
import java.util.TreeMap;

import escritoriovirtualalabastrum.modelo.Usuario;

public class BonificacaoAuxiliar {

	private Usuario usuario;
	private Integer geracao;
	private BigDecimal bonificacao;
	private BigDecimal pontuacao;
	private BigDecimal porcentagem;
	private String kit;
	private String comoFoiCalculado;
	private BigDecimal pontuacaoDiamante;
	private Integer quantidadeGraduados;
	private TreeMap<Integer, MalaDireta> malaDireta;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Integer getGeracao() {
		return geracao;
	}

	public void setGeracao(Integer geracao) {
		this.geracao = geracao;
	}

	public BigDecimal getBonificacao() {
		return bonificacao;
	}

	public void setBonificacao(BigDecimal bonificacao) {
		this.bonificacao = bonificacao;
	}

	public BigDecimal getPontuacao() {
		return pontuacao;
	}

	public void setPontuacao(BigDecimal pontuacao) {
		this.pontuacao = pontuacao;
	}

	public BigDecimal getPorcentagem() {
		return porcentagem;
	}

	public void setPorcentagem(BigDecimal porcentagem) {
		this.porcentagem = porcentagem;
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

	public BigDecimal getPontuacaoDiamante() {
		return pontuacaoDiamante;
	}

	public void setPontuacaoDiamante(BigDecimal pontuacaoDiamante) {
		this.pontuacaoDiamante = pontuacaoDiamante;
	}

	public Integer getQuantidadeGraduados() {
		return quantidadeGraduados;
	}

	public void setQuantidadeGraduados(Integer quantidadeGraduados) {
		this.quantidadeGraduados = quantidadeGraduados;
	}

	public TreeMap<Integer, MalaDireta> getMalaDireta() {
		return malaDireta;
	}

	public void setMalaDireta(TreeMap<Integer, MalaDireta> malaDireta) {
		this.malaDireta = malaDireta;
	}
}