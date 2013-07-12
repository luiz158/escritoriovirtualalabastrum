package escritoriovirtualalabastrum.sessao;

import java.util.LinkedHashMap;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.SessionScoped;

@Component
@SessionScoped
public class SessaoPedido {

	private LinkedHashMap<String, Integer> produtosEQuantidades;

	private String codigoPedido;

	public LinkedHashMap<String, Integer> getProdutosEQuantidades() {
		return produtosEQuantidades;
	}

	public void setProdutosEQuantidades(LinkedHashMap<String, Integer> produtosEQuantidades) {
		this.produtosEQuantidades = produtosEQuantidades;
	}

	public String getCodigoPedido() {
		return codigoPedido;
	}

	public void setCodigoPedido(String codigoPedido) {
		this.codigoPedido = codigoPedido;
	}

}
