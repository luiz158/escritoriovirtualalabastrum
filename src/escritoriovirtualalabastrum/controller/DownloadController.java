package escritoriovirtualalabastrum.controller;

import java.io.File;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import escritoriovirtualalabastrum.anotacoes.Funcionalidade;

@Resource
public class DownloadController {

	public static final String PASTA_DOWNLOADS = ImportacaoArquivoController.PASTA_RAIZ + "Dropbox/downloads-alabastrum/";

	@Path("/download/{arquivo}")
	@Funcionalidade
	public File baixar(String arquivo) {

		return new File(PASTA_DOWNLOADS + arquivo);
	}
}
