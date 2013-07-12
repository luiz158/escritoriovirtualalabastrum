package escritoriovirtualalabastrum.sessao;

import java.math.BigDecimal;
import java.util.HashMap;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.SessionScoped;

@Component
@SessionScoped
public class SessaoPedido {

	private HashMap<String, BigDecimal> produtosEQuantidades;

	public HashMap<String, BigDecimal> getProdutosEQuantidades() {
		return produtosEQuantidades;
	}

	public void setProdutosEQuantidades(HashMap<String, BigDecimal> produtosEQuantidades) {
		this.produtosEQuantidades = produtosEQuantidades;
	}
}
