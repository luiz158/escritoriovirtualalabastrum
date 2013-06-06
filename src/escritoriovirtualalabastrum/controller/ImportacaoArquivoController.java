package escritoriovirtualalabastrum.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import au.com.bytecode.opencsv.CSVReader;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.interceptor.multipart.UploadedFile;
import br.com.caelum.vraptor.validator.ValidationMessage;
import escritoriovirtualalabastrum.anotacoes.Funcionalidade;
import escritoriovirtualalabastrum.hibernate.HibernateUtil;
import escritoriovirtualalabastrum.modelo.Usuario;

@Resource
public class ImportacaoArquivoController {

	private static final String PASTA_PADRAO_WINDOWS = "C:\\csvExtraido";
	private static final String PASTA_PADRAO_LINUX = "/csvExtraido";

	private Result result;
	private Validator validator;
	private HibernateUtil hibernateUtil;

	public ImportacaoArquivoController(Result result, Validator validator, HibernateUtil hibernateUtil) {

		this.result = result;
		this.validator = validator;
		this.hibernateUtil = hibernateUtil;
	}

	@Funcionalidade(administrativa = "true")
	public void acessarTelaImportacaoArquivoTabelaRelacionamentos() {

	}

	@Funcionalidade(administrativa = "true")
	public void importarArquivoTabelaRelacionamentos(UploadedFile arquivo) throws IOException {

		String caminho = verificaSistemaOperacional();

		InputStream file = arquivo.getFile();

		if (arquivo.getFileName().endsWith(".zip")) {

			CSVReader reader = descompactarZip(arquivo, caminho, file);

			lerCSV(reader);
		}

		else if (arquivo.getFileName().endsWith(".csv")) {

			CSVReader reader = new CSVReader(new InputStreamReader(file), '\t');

			lerCSV(reader);
		}

		else {

			validarFormato();

			arquivo.getFile().close();

			return;
		}

		arquivo.getFile().close();

		result.include("sucesso", "Arquivo importado com sucesso");

		result.forwardTo(this).acessarTelaImportacaoArquivoTabelaRelacionamentos();

	}

	private void validarFormato() {

		validator.add(new ValidationMessage("O formato de arquivo escolhido não é suportado. Utilize csv ou um csv compactado no formato zip. As colunas dentro do csv devem estar separadas por ponto-virgula ';' . E a primeira linha do csv deve conter o cabeçalho com os nomes das colunas. ", "Erro"));

		validator.onErrorForwardTo(this).acessarTelaImportacaoArquivoTabelaRelacionamentos();
	}

	private void lerCSV(CSVReader reader) throws IOException {

		List<Usuario> usuarios = new ArrayList<Usuario>();

		HashMap<Integer, String> hashColunas = new HashMap<Integer, String>();

		String[] nextLine;
		while ((nextLine = reader.readNext()) != null) {

			String[] colunas = nextLine[0].split(";");

			if (colunas.length <= 2) {

				validarFormato();
				return;
			}

			else {

				for (int i = 0; i < colunas.length; i++) {

					colunas[i] = colunas[i].replaceAll("\"", "");
				}

				if (!colunas[0].contains("id_Codigo")) {

					if (hashColunas.size() == 0) {

						validarFormato();
					}

					Usuario usuario = new Usuario();

					for (int i = 0; i < colunas.length; i++) {

						Field field = null;

						try {

							field = usuario.getClass().getDeclaredField(hashColunas.get(i));

							field.setAccessible(true);

							try {

								DecimalFormatSymbols dsf = new DecimalFormatSymbols();

								field.set(usuario, (int) Double.parseDouble(colunas[i].replace(dsf.getDecimalSeparator(), '.')));
							}

							catch (Exception e) {

								field.set(usuario, colunas[i]);
							}

						} catch (Exception e) {

							//e.printStackTrace();
						}
					}

					usuarios.add(usuario);
				}

				else {

					for (int i = 0; i < colunas.length; i++) {

						hashColunas.put(i, colunas[i]);
					}
				}
			}
		}

		this.hibernateUtil.executarSQL("delete from usuario");

		this.hibernateUtil.salvarOuAtualizar(usuarios);

	}

	private String verificaSistemaOperacional() {

		String caminho;

		if (System.getProperty("os.name").toLowerCase().contains("windows")) {

			caminho = PASTA_PADRAO_WINDOWS;
		}

		else {

			caminho = PASTA_PADRAO_LINUX;
		}
		return caminho;
	}

	private CSVReader descompactarZip(UploadedFile arquivo, String caminho, InputStream file) throws IOException, FileNotFoundException {

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

		CSVReader reader = new CSVReader(new FileReader(new File(caminho + File.separator + arquivo.getFileName().split(".zip")[0] + ".csv")), '\t');
		return reader;
	}
}
