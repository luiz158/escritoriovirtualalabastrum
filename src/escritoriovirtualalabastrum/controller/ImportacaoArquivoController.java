package escritoriovirtualalabastrum.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import au.com.bytecode.opencsv.CSVReader;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.interceptor.multipart.UploadedFile;
import escritoriovirtualalabastrum.anotacoes.Funcionalidade;

@Resource
public class ImportacaoArquivoController {

	private static final String PASTA_PADRAO_WINDOWS = "C:\\csvExtraido";
	private static final String PASTA_PADRAO_LINUX = "/csvExtraido";

	private Result result;

	public ImportacaoArquivoController(Result result) {

		this.result = result;
	}

	@Funcionalidade(administrativa = "true")
	public void acessarTelaImportacaoArquivoTabelaRelacionamentos() {

	}

	@Funcionalidade(administrativa = "true")
	public void importarArquivoTabelaRelacionamentos(UploadedFile arquivo) throws IOException {

		String caminho = null;

		if (System.getProperty("os.name").toLowerCase().contains("windows")) {

			caminho = PASTA_PADRAO_WINDOWS;
		}

		else {

			caminho = PASTA_PADRAO_LINUX;
		}

		InputStream file = arquivo.getFile();

		if (arquivo.getFileName().endsWith(".zip")) {

			byte[] buffer = new byte[1024];

			ZipInputStream zis = new ZipInputStream(file);

			ZipEntry ze = zis.getNextEntry();

			while (ze != null) {

				String fileName = ze.getName();

				File newFile = new File(caminho + File.separator + fileName);

				new File(newFile.getParent()).mkdirs();

				FileOutputStream fos = new FileOutputStream(newFile);

				int len;
				while ((len = zis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}

				fos.close();
				ze = zis.getNextEntry();
			}

			zis.closeEntry();
			zis.close();

			@SuppressWarnings("resource")
			CSVReader reader = new CSVReader(new FileReader(new File(caminho + File.separator + arquivo.getFileName().split(".zip")[0] + ".csv")));

			String[] nextLine;
			while ((nextLine = reader.readNext()) != null) {

				System.out.println(nextLine[0]);
			}
		}

		else if (arquivo.getFileName().endsWith(".csv")) {

			@SuppressWarnings("resource")
			CSVReader reader = new CSVReader(new InputStreamReader(file));

			String[] nextLine;
			while ((nextLine = reader.readNext()) != null) {

				System.out.println(nextLine[0]);
			}
		}

		arquivo.getFile().close();
	}
}
