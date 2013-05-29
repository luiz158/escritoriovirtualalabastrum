package escritoriovirtualalabastrum.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import au.com.bytecode.opencsv.CSVReader;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.interceptor.multipart.UploadedFile;
import escritoriovirtualalabastrum.anotacoes.Funcionalidade;

@Resource
public class ImportacaoArquivoController {

	private Result result;

	public ImportacaoArquivoController(Result result) {

		this.result = result;
	}

	@Funcionalidade(administrativa = "true")
	public void acessarTelaImportacaoArquivoTabelaRelacionamentos() {

	}

	@Funcionalidade(administrativa = "true")
	public void importarArquivoTabelaRelacionamentos(UploadedFile arquivo) throws IOException {

		InputStream file = arquivo.getFile();

		@SuppressWarnings("resource")
		CSVReader reader = new CSVReader(new InputStreamReader(file));

		String[] nextLine;
		while ((nextLine = reader.readNext()) != null) {

			System.out.println(nextLine[0]);
		}

		arquivo.getFile().close();
	}
}
