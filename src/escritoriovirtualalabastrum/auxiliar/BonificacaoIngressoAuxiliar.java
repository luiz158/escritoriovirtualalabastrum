package escritoriovirtualalabastrum.auxiliar;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import escritoriovirtualalabastrum.modelo.Usuario;

public class BonificacaoIngressoAuxiliar {

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
	private List<BonificacaoIngressoAuxiliar> bonificacoes;
	private Boolean isDiamante;
	private List<BonificacaoIngressoAuxiliar> bonificacoesDiamante;
	private BigDecimal metaDiamantePontuacao;
	private Integer metaDiamanteGraduados;
	private BigDecimal pontuacaoAlcancadaPeloDiamante;
	private Integer graduadosAlcancadosPeloDiamante;
	private TreeMap<Integer, MalaDireta> diamantesComMetasAlcancadas;
	private HashMap<Integer, MalaDireta> graduados;

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

	public List<BonificacaoIngressoAuxiliar> getBonificacoes() {
		return bonificacoes;
	}

	public void setBonificacoes(List<BonificacaoIngressoAuxiliar> bonificacoes) {
		this.bonificacoes = bonificacoes;
	}

	public Boolean getIsDiamante() {
		return isDiamante;
	}

	public void setIsDiamante(Boolean isDiamante) {
		this.isDiamante = isDiamante;
	}

	public List<BonificacaoIngressoAuxiliar> getBonificacoesDiamante() {
		return bonificacoesDiamante;
	}

	public void setBonificacoesDiamante(List<BonificacaoIngressoAuxiliar> bonificacoesDiamante) {
		this.bonificacoesDiamante = bonificacoesDiamante;
	}

	public BigDecimal getMetaDiamantePontuacao() {
		return metaDiamantePontuacao;
	}

	public void setMetaDiamantePontuacao(BigDecimal metaDiamantePontuacao) {
		this.metaDiamantePontuacao = metaDiamantePontuacao;
	}

	public Integer getMetaDiamanteGraduados() {
		return metaDiamanteGraduados;
	}

	public void setMetaDiamanteGraduados(Integer metaDiamanteGraduados) {
		this.metaDiamanteGraduados = metaDiamanteGraduados;
	}

	public BigDecimal getPontuacaoAlcancadaPeloDiamante() {
		return pontuacaoAlcancadaPeloDiamante;
	}

	public void setPontuacaoAlcancadaPeloDiamante(BigDecimal pontuacaoAlcancadaPeloDiamante) {
		this.pontuacaoAlcancadaPeloDiamante = pontuacaoAlcancadaPeloDiamante;
	}

	public Integer getGraduadosAlcancadosPeloDiamante() {
		return graduadosAlcancadosPeloDiamante;
	}

	public void setGraduadosAlcancadosPeloDiamante(Integer graduadosAlcancadosPeloDiamante) {
		this.graduadosAlcancadosPeloDiamante = graduadosAlcancadosPeloDiamante;
	}

	public TreeMap<Integer, MalaDireta> getDiamantesComMetasAlcancadas() {
		return diamantesComMetasAlcancadas;
	}

	public void setDiamantesComMetasAlcancadas(TreeMap<Integer, MalaDireta> diamantesComMetasAlcancadas) {
		this.diamantesComMetasAlcancadas = diamantesComMetasAlcancadas;
	}

	public HashMap<Integer, MalaDireta> getGraduados() {
		return graduados;
	}

	public void setGraduados(HashMap<Integer, MalaDireta> graduados) {
		this.graduados = graduados;
	}
}