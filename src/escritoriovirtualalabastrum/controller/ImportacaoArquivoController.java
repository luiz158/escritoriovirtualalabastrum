package escritoriovirtualalabastrum.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import au.com.bytecode.opencsv.CSVReader;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.interceptor.multipart.UploadedFile;
import br.com.caelum.vraptor.validator.ValidationMessage;
import escritoriovirtualalabastrum.anotacoes.Funcionalidade;
import escritoriovirtualalabastrum.hibernate.HibernateUtil;
import escritoriovirtualalabastrum.modelo.InformacoesUltimaAtualizacao;
import escritoriovirtualalabastrum.modelo.Pontuacao;
import escritoriovirtualalabastrum.modelo.Usuario;
import escritoriovirtualalabastrum.util.Util;

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
	public void acessarTelaImportacaoArquivo() {

	}

	@Funcionalidade(administrativa = "true")
	public void importarArquivo(UploadedFile arquivo) throws IOException {

		InputStream file = arquivo.getFile();

		if (arquivo.getFileName().endsWith(".zip")) {

			descompactarZip(arquivo, file);

			processarCSVRelacionamentos();
			processarCSVPontuacao();

			atualizarInformacoesUltimaAtualizacao();
		}

		else {

			validarFormato();

			arquivo.getFile().close();

			return;
		}

		arquivo.getFile().close();

		result.include("sucesso", "Arquivo importado com sucesso");

		result.forwardTo(this).acessarTelaImportacaoArquivo();

	}

	private void atualizarInformacoesUltimaAtualizacao() {

		InformacoesUltimaAtualizacao informacoesUltimaAtualizacao = this.hibernateUtil.selecionar(new InformacoesUltimaAtualizacao());

		if (Util.vazio(informacoesUltimaAtualizacao)) {

			informacoesUltimaAtualizacao = new InformacoesUltimaAtualizacao();
		}

		informacoesUltimaAtualizacao.setDataHora(new GregorianCalendar());

		this.hibernateUtil.salvarOuAtualizar(informacoesUltimaAtualizacao);
	}

	private void validarFormato() {

		validator.add(new ValidationMessage("O formato de arquivo escolhido não é suportado. Utilize um arquivo csv compactado no formato zip. As colunas dentro do csv devem estar separadas por ponto-virgula ';' . E a primeira linha do csv deve conter o cabeçalho com os nomes das colunas. ", "Erro"));

		validator.onErrorForwardTo(this).acessarTelaImportacaoArquivo();
	}

	private void processarCSVRelacionamentos() throws IOException {

		String caminho = verificaSistemaOperacional();

		String caminhoCompletoArquivo = caminho + File.separator + "tblRelacionamentos" + ".csv";

		File arquivoNoDisco = new File(caminhoCompletoArquivo);
		String content = FileUtils.readFileToString(arquivoNoDisco, "ISO8859_1");
		FileUtils.write(arquivoNoDisco, content, "UTF-8");

		@SuppressWarnings("resource")
		CSVReader reader = new CSVReader(new FileReader(new File(caminhoCompletoArquivo)), '\t');

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

							// e.printStackTrace();
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

	private void processarCSVPontuacao() throws IOException {

		String caminho = verificaSistemaOperacional();

		String caminhoCompletoArquivo = caminho + File.separator + "tblPontuacao" + ".csv";

		File arquivoNoDisco = new File(caminhoCompletoArquivo);
		String content = FileUtils.readFileToString(arquivoNoDisco, "ISO8859_1");
		FileUtils.write(arquivoNoDisco, content, "UTF-8");

		@SuppressWarnings("resource")
		CSVReader reader = new CSVReader(new FileReader(new File(caminhoCompletoArquivo)), '\t');

		List<Pontuacao> pontuacoes = new ArrayList<Pontuacao>();

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

					Pontuacao pontuacao = new Pontuacao();

					for (int i = 0; i < colunas.length; i++) {

						Field field = null;

						try {

							field = pontuacao.getClass().getDeclaredField(hashColunas.get(i));

							field.setAccessible(true);

							try {

								BigDecimal numero = Util.converterStringParaBigDecimal(colunas[i]);
								field.set(pontuacao, numero);
							}

							catch (Exception e) {

								try {

									DecimalFormatSymbols dsf = new DecimalFormatSymbols();
									field.set(pontuacao, (int) Double.parseDouble(colunas[i].replace(dsf.getDecimalSeparator(), '.')));
								}

								catch (Exception e2) {

									DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
									DateTime data = formatter.parseDateTime(colunas[i]);

									field.set(pontuacao, data.toGregorianCalendar());
								}
							}

						} catch (Exception e) {

							// e.printStackTrace();
						}
					}

					pontuacoes.add(pontuacao);
				}

				else {

					for (int i = 0; i < colunas.length; i++) {

						hashColunas.put(i, colunas[i]);
					}
				}
			}
		}

		this.hibernateUtil.executarSQL("delete from pontuacao");

		this.hibernateUtil.salvarOuAtualizar(pontuacoes);
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

	private void removerArquivosDaPasta(File f) {

		if (f.isDirectory()) {

			File[] files = f.listFiles();

			for (File file : files) {
				file.delete();
			}
		}
	}

	private void descompactarZip(UploadedFile arquivo, InputStream file) throws IOException, FileNotFoundException {

		String caminho = verificaSistemaOperacional();

		removerArquivosDaPasta(new File(caminho));

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
	}
}
