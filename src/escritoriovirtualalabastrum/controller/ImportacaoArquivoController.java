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
import escritoriovirtualalabastrum.modelo.Categoria;
import escritoriovirtualalabastrum.modelo.CentroDistribuicao;
import escritoriovirtualalabastrum.modelo.ControlePedido;
import escritoriovirtualalabastrum.modelo.FixoIngresso;
import escritoriovirtualalabastrum.modelo.HistoricoKit;
import escritoriovirtualalabastrum.modelo.InformacoesUltimaAtualizacao;
import escritoriovirtualalabastrum.modelo.Pontuacao;
import escritoriovirtualalabastrum.modelo.PorcentagemIngresso;
import escritoriovirtualalabastrum.modelo.Produto;
import escritoriovirtualalabastrum.modelo.Usuario;
import escritoriovirtualalabastrum.util.Util;

@Resource
public class ImportacaoArquivoController {

	public static final String PASTA_RAIZ = "/root/";
	public static final String PASTA_ATUALIZACAO_CSV = ImportacaoArquivoController.PASTA_RAIZ + "Dropbox/do-desktop-para-o-escritorio";

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

			processarArquivos();
		}

		else {

			validarFormato();

			arquivo.getFile().close();
		}

		arquivo.getFile().close();

		result.include("sucesso", "Arquivo importado com sucesso");

		result.forwardTo(this).acessarTelaImportacaoArquivo();
	}

	public void processarArquivos() throws IOException {

		processarCSVRelacionamentos();
		processarCSVPontuacao();
		processarCSVProdutos();
		processarCSVCategorias();
		processarCSVCentroDistribuicao();
		processarCSVControlePedido();
		processarCSVFixoIngresso();
		processarCSVHistoricoKit();
		processarCSVPorcentagemIngresso();

		atualizarInformacoesUltimaAtualizacao();
	}

	private void processarCSVFixoIngresso() throws IOException {

		CSVReader reader = lerArquivo("tblFixoIngresso.csv");

		HashMap<Integer, String> hashColunas = new HashMap<Integer, String>();

		List<FixoIngresso> fixosIngresso = new ArrayList<FixoIngresso>();

		String[] nextLine;
		while ((nextLine = reader.readNext()) != null) {

			String[] colunas = nextLine[0].split(";");

			if (colunas.length <= 2) {

				validarFormato();
			}

			else {

				for (int i = 0; i < colunas.length; i++) {

					colunas[i] = colunas[i].replaceAll("\"", "");
				}

				if (!colunas[0].contains("data_referencia")) {

					if (hashColunas.size() == 0) {

						validarFormato();
					}

					FixoIngresso fixoIngresso = new FixoIngresso();

					for (int i = 0; i < colunas.length; i++) {

						Field field = null;

						try {

							field = fixoIngresso.getClass().getDeclaredField(hashColunas.get(i));

							field.setAccessible(true);

							try {

								BigDecimal numero = Util.converterStringParaBigDecimal(colunas[i]);
								field.set(fixoIngresso, numero);
							}

							catch (Exception e) {

								try {

									DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yy");
									DateTime data = formatter.parseDateTime("01/" + colunas[i]);

									field.set(fixoIngresso, data.toGregorianCalendar());
								}

								catch (Exception e3) {

									field.set(fixoIngresso, colunas[i]);
								}
							}

						} catch (Exception e) {
						}
					}

					fixosIngresso.add(fixoIngresso);
				}

				else {

					for (int i = 0; i < colunas.length; i++) {

						hashColunas.put(i, colunas[i]);
					}
				}
			}
		}

		this.hibernateUtil.executarSQL("delete from fixoingresso");

		this.hibernateUtil.salvarOuAtualizar(fixosIngresso);
	}

	private CSVReader lerArquivo(String nomeCsv) throws IOException, FileNotFoundException {

		String caminho = PASTA_ATUALIZACAO_CSV;

		String caminhoCompletoArquivo = caminho + File.separator + nomeCsv;

		File arquivoNoDisco = new File(caminhoCompletoArquivo);
		String content = FileUtils.readFileToString(arquivoNoDisco, "ISO8859_1");
		FileUtils.write(arquivoNoDisco, content, "UTF-8");

		return new CSVReader(new FileReader(new File(caminhoCompletoArquivo)), '\t');
	}

	private void processarCSVPorcentagemIngresso() throws IOException {

		CSVReader reader = lerArquivo("tblPorcentagemIngresso.csv");

		List<PorcentagemIngresso> porcentagensIngresso = new ArrayList<PorcentagemIngresso>();

		HashMap<Integer, String> hashColunas = new HashMap<Integer, String>();

		String[] nextLine;
		while ((nextLine = reader.readNext()) != null) {

			String[] colunas = nextLine[0].split(";");

			if (colunas.length <= 2) {

				validarFormato();
			}

			else {

				for (int i = 0; i < colunas.length; i++) {

					colunas[i] = colunas[i].replaceAll("\"", "");
				}

				if (!colunas[0].contains("data_referencia")) {

					if (hashColunas.size() == 0) {

						validarFormato();
					}

					PorcentagemIngresso porcentagemIngresso = new PorcentagemIngresso();

					for (int i = 0; i < colunas.length; i++) {

						Field field = null;

						try {

							field = porcentagemIngresso.getClass().getDeclaredField(hashColunas.get(i));

							field.setAccessible(true);

							try {

								BigDecimal numero = Util.converterStringParaBigDecimal(colunas[i]);
								field.set(porcentagemIngresso, numero);
							}

							catch (Exception e) {

								try {

									DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yy");
									DateTime data = formatter.parseDateTime("01/" + colunas[i]);

									field.set(porcentagemIngresso, data.toGregorianCalendar());
								}

								catch (Exception e3) {

									field.set(porcentagemIngresso, colunas[i]);
								}
							}

						} catch (Exception e) {
						}
					}

					porcentagensIngresso.add(porcentagemIngresso);
				}

				else {

					for (int i = 0; i < colunas.length; i++) {

						hashColunas.put(i, colunas[i]);
					}
				}
			}
		}

		this.hibernateUtil.executarSQL("delete from porcentagemingresso");

		this.hibernateUtil.salvarOuAtualizar(porcentagensIngresso);
	}

	private void processarCSVHistoricoKit() throws IOException {

		CSVReader reader = lerArquivo("tblHistoricoKit.csv");

		List<HistoricoKit> historicosKit = new ArrayList<HistoricoKit>();

		HashMap<Integer, String> hashColunas = new HashMap<Integer, String>();

		String[] nextLine;
		while ((nextLine = reader.readNext()) != null) {

			String[] colunas = nextLine[0].split(";");

			if (colunas.length < 2) {

				validarFormato();
			}

			else {

				for (int i = 0; i < colunas.length; i++) {

					colunas[i] = colunas[i].replaceAll("\"", "");
				}

				if (!colunas[0].contains("id_Codigo")) {

					if (hashColunas.size() == 0) {

						validarFormato();
					}

					HistoricoKit historicoKit = new HistoricoKit();

					for (int i = 0; i < colunas.length; i++) {

						Field field = null;

						try {

							field = historicoKit.getClass().getDeclaredField(hashColunas.get(i));

							field.setAccessible(true);

							try {

								field.set(historicoKit, Integer.valueOf(colunas[i]));
							}

							catch (Exception e) {

								try {

									DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yy");
									DateTime data = formatter.parseDateTime("01/" + colunas[i]);

									field.set(historicoKit, data.toGregorianCalendar());
								}

								catch (Exception e3) {

									field.set(historicoKit, colunas[i]);
								}
							}

						} catch (Exception e) {
						}
					}

					historicosKit.add(historicoKit);
				}

				else {

					for (int i = 0; i < colunas.length; i++) {

						hashColunas.put(i, colunas[i]);
					}
				}
			}
		}

		this.hibernateUtil.executarSQL("delete from historicokit");

		this.hibernateUtil.salvarOuAtualizar(historicosKit);
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

		CSVReader reader = lerArquivo("tblRelacionamentos.csv");

		List<Usuario> usuarios = new ArrayList<Usuario>();

		HashMap<Integer, String> hashColunas = new HashMap<Integer, String>();

		String[] nextLine;
		while ((nextLine = reader.readNext()) != null) {

			String[] colunas = nextLine[0].split(";");

			if (colunas.length <= 2) {

				validarFormato();
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

								try {

									DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
									DateTime data = formatter.parseDateTime(colunas[i]);

									field.set(usuario, data.toGregorianCalendar());
								}

								catch (Exception e2) {

									field.set(usuario, colunas[i]);
								}
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

		CSVReader reader = lerArquivo("tblPontuacao.csv");

		List<Pontuacao> pontuacoes = new ArrayList<Pontuacao>();

		HashMap<Integer, String> hashColunas = new HashMap<Integer, String>();

		String[] nextLine;
		while ((nextLine = reader.readNext()) != null) {

			String[] colunas = nextLine[0].split(";");

			if (colunas.length <= 2) {

				validarFormato();
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

	private void processarCSVControlePedido() throws IOException {

		CSVReader reader = lerArquivo("tblControlePedidos.csv");

		List<ControlePedido> controlesPedidos = new ArrayList<ControlePedido>();

		HashMap<Integer, String> hashColunas = new HashMap<Integer, String>();

		String[] nextLine;
		while ((nextLine = reader.readNext()) != null) {

			String[] colunas = nextLine[0].split(";");

			if (colunas.length <= 2) {

				validarFormato();
			}

			else {

				for (int i = 0; i < colunas.length; i++) {

					colunas[i] = colunas[i].replaceAll("\"", "");
				}

				if (!colunas[0].contains("id_Codigo")) {

					if (hashColunas.size() == 0) {

						validarFormato();
					}

					ControlePedido controlePedido = new ControlePedido();

					for (int i = 0; i < colunas.length; i++) {

						Field field = null;

						try {

							field = controlePedido.getClass().getDeclaredField(hashColunas.get(i));

							field.setAccessible(true);

							try {

								BigDecimal numero = Util.converterStringParaBigDecimal(colunas[i]);
								field.set(controlePedido, numero);
							}

							catch (Exception e) {

								try {

									DecimalFormatSymbols dsf = new DecimalFormatSymbols();
									field.set(controlePedido, (int) Double.parseDouble(colunas[i].replace(dsf.getDecimalSeparator(), '.')));
								}

								catch (Exception e2) {

									try {

										DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
										DateTime data = formatter.parseDateTime(colunas[i].split(" ")[0]);

										field.set(controlePedido, data.toGregorianCalendar());
									}

									catch (Exception e3) {

										field.set(controlePedido, colunas[i]);
									}
								}
							}

						} catch (Exception e) {

							// e.printStackTrace();
						}
					}

					controlesPedidos.add(controlePedido);
				}

				else {

					for (int i = 0; i < colunas.length; i++) {

						hashColunas.put(i, colunas[i]);
					}
				}
			}
		}

		this.hibernateUtil.executarSQL("delete from controlepedido");

		this.hibernateUtil.salvarOuAtualizar(controlesPedidos);
	}

	private void processarCSVProdutos() throws IOException {

		CSVReader reader = lerArquivo("tblProdutos.csv");

		List<Produto> produtos = new ArrayList<Produto>();

		HashMap<Integer, String> hashColunas = new HashMap<Integer, String>();

		String[] nextLine;
		while ((nextLine = reader.readNext()) != null) {

			String[] colunas = nextLine[0].split(";");

			if (colunas.length <= 2) {

				validarFormato();
			}

			else {

				for (int i = 0; i < colunas.length; i++) {

					colunas[i] = colunas[i].replaceAll("\"", "");
				}

				if (!colunas[0].contains("id_Produtos")) {

					if (hashColunas.size() == 0) {

						validarFormato();
					}

					Produto produto = new Produto();

					for (int i = 0; i < colunas.length; i++) {

						Field field = null;

						try {

							field = produto.getClass().getDeclaredField(hashColunas.get(i));

							field.setAccessible(true);

							try {

								BigDecimal numero = Util.converterStringParaBigDecimal(colunas[i]);
								field.set(produto, numero);
							}

							catch (Exception e) {

								try {

									DecimalFormatSymbols dsf = new DecimalFormatSymbols();
									field.set(produto, (int) Double.parseDouble(colunas[i].replace(dsf.getDecimalSeparator(), '.')));
								}

								catch (Exception e2) {

									field.set(produto, colunas[i]);
								}
							}
						}

						catch (Exception e) {

							// e.printStackTrace();
						}
					}

					produtos.add(produto);
				}

				else {

					for (int i = 0; i < colunas.length; i++) {

						hashColunas.put(i, colunas[i].replaceAll(" ", ""));
					}
				}
			}
		}

		this.hibernateUtil.executarSQL("delete from produto");

		this.hibernateUtil.salvarOuAtualizar(produtos);
	}

	private void processarCSVCategorias() throws IOException {

		CSVReader reader = lerArquivo("tblCategorias.csv");

		List<Categoria> categorias = new ArrayList<Categoria>();

		HashMap<Integer, String> hashColunas = new HashMap<Integer, String>();

		String[] nextLine;
		while ((nextLine = reader.readNext()) != null) {

			String[] colunas = nextLine[0].split(";");

			if (colunas.length < 2) {

				validarFormato();
			}

			else {

				for (int i = 0; i < colunas.length; i++) {

					colunas[i] = colunas[i].replaceAll("\"", "");
				}

				if (!colunas[0].contains("id_Categoria")) {

					if (hashColunas.size() == 0) {

						validarFormato();
					}

					Categoria categoria = new Categoria();

					for (int i = 0; i < colunas.length; i++) {

						Field field = null;

						try {

							field = categoria.getClass().getDeclaredField(hashColunas.get(i));

							field.setAccessible(true);

							try {

								DecimalFormatSymbols dsf = new DecimalFormatSymbols();
								field.set(categoria, (int) Double.parseDouble(colunas[i].replace(dsf.getDecimalSeparator(), '.')));
							}

							catch (Exception e) {

								field.set(categoria, colunas[i]);
							}
						}

						catch (Exception e) {

							// e.printStackTrace();
						}
					}

					categorias.add(categoria);
				}

				else {

					for (int i = 0; i < colunas.length; i++) {

						hashColunas.put(i, colunas[i].replaceAll(" ", ""));
					}
				}
			}
		}

		this.hibernateUtil.executarSQL("delete from categoria");

		this.hibernateUtil.salvarOuAtualizar(categorias);
	}

	private void processarCSVCentroDistribuicao() throws IOException {

		CSVReader reader = lerArquivo("tblCDA.csv");

		List<CentroDistribuicao> centrosDistribuicao = new ArrayList<CentroDistribuicao>();

		HashMap<Integer, String> hashColunas = new HashMap<Integer, String>();

		String[] nextLine;
		while ((nextLine = reader.readNext()) != null) {

			String[] colunas = nextLine[0].split(";");

			if (colunas.length < 2) {

				validarFormato();
			}

			else {

				for (int i = 0; i < colunas.length; i++) {

					colunas[i] = colunas[i].replaceAll("\"", "");
				}

				if (!colunas[0].contains("id_Estoque")) {

					if (hashColunas.size() == 0) {

						validarFormato();
					}

					CentroDistribuicao centroDistribuicao = new CentroDistribuicao();

					for (int i = 0; i < colunas.length; i++) {

						Field field = null;

						try {

							field = centroDistribuicao.getClass().getDeclaredField(hashColunas.get(i));

							field.setAccessible(true);

							try {

								DecimalFormatSymbols dsf = new DecimalFormatSymbols();
								field.set(centroDistribuicao, (int) Double.parseDouble(colunas[i].replace(dsf.getDecimalSeparator(), '.')));
							}

							catch (Exception e) {

								field.set(centroDistribuicao, colunas[i]);
							}
						}

						catch (Exception e) {

							// e.printStackTrace();
						}
					}

					centrosDistribuicao.add(centroDistribuicao);
				}

				else {

					for (int i = 0; i < colunas.length; i++) {

						hashColunas.put(i, colunas[i].replaceAll(" ", ""));
					}
				}
			}
		}

		this.hibernateUtil.executarSQL("delete from centrodistribuicao");

		this.hibernateUtil.salvarOuAtualizar(centrosDistribuicao);
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

		String caminho = PASTA_ATUALIZACAO_CSV;

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
