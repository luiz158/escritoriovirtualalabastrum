package escritoriovirtualalabastrum.controller;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import escritoriovirtualalabastrum.anotacoes.Funcionalidade;

@Resource
public class AtualizacaoController {

  @Path("/atualizar")
	@Funcionalidade(administrativa = "true")
	public void atualizar() {
	
	  Runtime.getRuntime().exec("/atualizar.sh");
	}
}
