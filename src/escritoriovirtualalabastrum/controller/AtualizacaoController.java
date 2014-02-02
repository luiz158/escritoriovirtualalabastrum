package escritoriovirtualalabastrum.controller;

import java.io.IOException;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import escritoriovirtualalabastrum.anotacoes.Funcionalidade;

@Resource
public class AtualizacaoController {

  @Path("/atualizar")
	@Funcionalidade(administrativa = "true")
	public void atualizar() throws IOException {
	
	  Runtime.getRuntime().exec(new String[] { "/bin/sh", "-c", "/atualizar.sh > /dev/null 2>&1 &" });
	}
}
