package escritoriovirtualalabastrum.auxiliar;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import escritoriovirtualalabastrum.modelo.Usuario;

public class BonificacaoGraduacaoAuxiliar {

	private BigDecimal porcentagemUsuarioLogado;
	private HashMap<Usuario, BigDecimal> graduadosEPorcentagens;
	private BigDecimal somatorioPedidosrede;
	private BigDecimal bonificacao;
	private List<Integer> usuariosQueDeramAlgumaBonificacao;

	public BigDecimal getPorcentagemUsuarioLogado() {
		return porcentagemUsuarioLogado;
	}

	public void setPorcentagemUsuarioLogado(BigDecimal porcentagemUsuarioLogado) {
		this.porcentagemUsuarioLogado = porcentagemUsuarioLogado;
	}

	public HashMap<Usuario, BigDecimal> getGraduadosEPorcentagens() {
		return graduadosEPorcentagens;
	}

	public void setGraduadosEPorcentagens(HashMap<Usuario, BigDecimal> graduadosEPorcentagens) {
		this.graduadosEPorcentagens = graduadosEPorcentagens;
	}

	public BigDecimal getSomatorioPedidosrede() {
		return somatorioPedidosrede;
	}

	public void setSomatorioPedidosrede(BigDecimal somatorioPedidosrede) {
		this.somatorioPedidosrede = somatorioPedidosrede;
	}

	public BigDecimal getBonificacao() {
		return bonificacao;
	}

	public void setBonificacao(BigDecimal bonificacao) {
		this.bonificacao = bonificacao;
	}

	public List<Integer> getUsuariosQueDeramAlgumaBonificacao() {
		return usuariosQueDeramAlgumaBonificacao;
	}

	public void setUsuariosQueDeramAlgumaBonificacao(List<Integer> usuariosQueDeramAlgumaBonificacao) {
		this.usuariosQueDeramAlgumaBonificacao = usuariosQueDeramAlgumaBonificacao;
	}
}