package escritoriovirtualalabastrum.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
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

		validator.add(new ValidationMessage("O formato de arquivo escolhido não é suportado. Utilize csv ou um csv compactado no formato zip. As colunas dentro do csv devem estar separadas por ponto-virgula ';' ", "Erro"));

		validator.onErrorForwardTo(this).acessarTelaImportacaoArquivoTabelaRelacionamentos();
	}

	private void lerCSV(CSVReader reader) throws IOException {

		List<Usuario> usuarios = new ArrayList<Usuario>();

		String[] nextLine;
		while ((nextLine = reader.readNext()) != null) {

			String[] colunas = nextLine[0].split(";");

			if (colunas.length <= 2) {

				validarFormato();
				return;
			}

			else {

				if (!colunas[0].contains("id_Codigo")) {

					Usuario usuario = new Usuario(Integer.valueOf(colunas[0]));

					usuario.setPosAtual(colunas[1]);
					usuario.setvNome(colunas[2]);
					usuario.setPosIngresso(colunas[3]);
					usuario.setDt_Ingresso(colunas[4]);
					usuario.setTel(colunas[5]);
					usuario.seteMail(colunas[6]);
					usuario.setDt_Nasc(colunas[7]);
					usuario.setId_Patroc(colunas[8]);
					usuario.setId_Dem(colunas[9]);
					usuario.setId_S(colunas[10]);
					usuario.setId_M(colunas[11]);
					usuario.setId_GB(colunas[12]);
					usuario.setId_GP(colunas[13]);
					usuario.setId_GO(colunas[14]);
					usuario.setId_GE(colunas[15]);
					usuario.setId_M1(colunas[16]);
					usuario.setId_M2(colunas[17]);
					usuario.setId_M3(colunas[18]);
					usuario.setId_M4(colunas[19]);
					usuario.setId_M5(colunas[20]);
					usuario.setId_LA(colunas[21]);
					usuario.setId_LA1(colunas[22]);
					usuario.setId_LA2(colunas[23]);
					usuario.setId_CR(colunas[24]);
					usuario.setId_CR1(colunas[25]);
					usuario.setId_CR2(colunas[26]);
					usuario.setId_DR(colunas[27]);
					usuario.setId_DD(colunas[28]);
					usuario.setId_DS(colunas[29]);
					usuario.setId_Pres(colunas[30]);
					usuario.setEV(colunas[31]);

					usuarios.add(usuario);
				}
			}
		}

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
