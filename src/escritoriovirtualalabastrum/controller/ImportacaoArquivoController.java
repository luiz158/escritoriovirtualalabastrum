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
import escritoriovirtualalabastrum.modelo.Atividade;
import escritoriovirtualalabastrum.modelo.Categoria;
import escritoriovirtualalabastrum.modelo.CentroDistribuicao;
import escritoriovirtualalabastrum.modelo.ControlePedido;
import escritoriovirtualalabastrum.modelo.CotasDivisao;
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
	public void importarArquivo(UploadedFile arquivo) throws Exception {

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

	public void processarArquivos() throws Exception {

		processarCSVRelacionamentos();
		processarCSVPontuacao();
		processarCSVProdutos();
		processarCSVCategorias();
		processarCSVCentroDistribuicao();
		processarCSVControlePedido();
		processarCSVFixoIngresso();
		processarCSVHistoricoKit();
		processarCSVCotasDivisao();
		processarCSVPorcentagemIngresso();
		processarCSVAtividade();

		atualizarInformacoesUltimaAtualizacao();
	}

	private void processarCSVFixoIngresso() throws Exception {

		CSVReader reader = lerArquivo("tblFixoIngresso.csv");

		List<FixoIngresso> fixosIngresso = new ArrayList<FixoIngresso>();

		String nomeDaPrimeiraColuna = "data_referencia";

		preencherObjeto(reader, fixosIngresso, nomeDaPrimeiraColuna, "FixoIngresso");

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

	private void processarCSVPorcentagemIngresso() throws Exception {

		CSVReader reader = lerArquivo("tblPorcentagemIngresso.csv");

		List<PorcentagemIngresso> porcentagensIngresso = new ArrayList<PorcentagemIngresso>();

		String nomeDaPrimeiraColuna = "data_referencia";

		preencherObjeto(reader, porcentagensIngresso, nomeDaPrimeiraColuna, "PorcentagemIngresso");

		this.hibernateUtil.executarSQL("delete from porcentagemingresso");

		this.hibernateUtil.salvarOuAtualizar(porcentagensIngresso);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void preencherObjeto(CSVReader reader, List listaDeEntidades, String nomeDaPrimeiraColuna, String nomeDaClasse) throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException {

		HashMap<Integer, String> hashColunas = new HashMap<Integer, String>();

		String[] nextLine;
		while ((nextLine = reader.readNext()) != null) {

			String[] colunas = nextLine[0].split(";");

			for (int i = 0; i < colunas.length; i++) {

				colunas[i] = colunas[i].replaceAll("\"", "");
			}

			if (!colunas[0].contains(nomeDaPrimeiraColuna)) {

				Object entidade = Class.forName("escritoriovirtualalabastrum.modelo." + nomeDaClasse).newInstance();

				for (int i = 0; i < colunas.length; i++) {

					Field field = null;

					try {

						field = entidade.getClass().getDeclaredField(hashColunas.get(i));

						field.setAccessible(true);

						try {

							BigDecimal numero = Util.converterStringParaBigDecimal(colunas[i]);
							field.set(entidade, numero);
						}

						catch (Exception e1) {

							try {

								DecimalFormatSymbols dsf = new DecimalFormatSymbols();
								field.set(entidade, (int) Double.parseDouble(colunas[i].replace(dsf.getDecimalSeparator(), '.')));
							}

							catch (Exception e2) {

								try {

									DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yy");
									DateTime data = formatter.parseDateTime("01/" + colunas[i]);

									field.set(entidade, data.toGregorianCalendar());
								}

								catch (Exception e3) {

									try {

										DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yy");
										DateTime data = formatter.parseDateTime(colunas[i]);

										field.set(entidade, data.toGregorianCalendar());

									} catch (Exception e4) {

										field.set(entidade, colunas[i]);
									}
								}
							}
						}

					} catch (Exception e) {
					}
				}

				listaDeEntidades.add(entidade);
			}

			else {

				for (int i = 0; i < colunas.length; i++) {

					hashColunas.put(i, colunas[i]);
				}
			}
		}
	}

	private void processarCSVAtividade() throws Exception {

		CSVReader reader = lerArquivo("tblAtividade.csv");

		List<Atividade> atividades = new ArrayList<Atividade>();

		String nomeDaPrimeiraColuna = "data_referencia";

		preencherObjeto(reader, atividades, nomeDaPrimeiraColuna, "Atividade");

		this.hibernateUtil.executarSQL("delete from atividade");

		this.hibernateUtil.salvarOuAtualizar(atividades);
	}

	private void processarCSVHistoricoKit() throws Exception {

		CSVReader reader = lerArquivo("tblHistoricoKit.csv");

		List<HistoricoKit> historicosKit = new ArrayList<HistoricoKit>();

		String nomeDaPrimeiraColuna = "id_Codigo";

		preencherObjeto(reader, historicosKit, nomeDaPrimeiraColuna, "HistoricoKit");

		this.hibernateUtil.executarSQL("delete from historicokit");

		this.hibernateUtil.salvarOuAtualizar(historicosKit);
	}

	private void processarCSVCotasDivisao() throws Exception {

		CSVReader reader = lerArquivo("tblCotasDivisao.csv");

		List<CotasDivisao> cotasDivisao = new ArrayList<CotasDivisao>();

		String nomeDaPrimeiraColuna = "id_Codigo";

		preencherObjeto(reader, cotasDivisao, nomeDaPrimeiraColuna, "CotasDivisao");

		this.hibernateUtil.executarSQL("delete from cotasdivisao");

		this.hibernateUtil.salvarOuAtualizar(cotasDivisao);
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

	private void processarCSVRelacionamentos() throws Exception {

		CSVReader reader = lerArquivo("tblRelacionamentos.csv");

		List<Usuario> usuarios = new ArrayList<Usuario>();

		String nomeDaPrimeiraColuna = "id_Codigo";

		preencherObjeto(reader, usuarios, nomeDaPrimeiraColuna, "Usuario");

		this.hibernateUtil.executarSQL("delete from usuario");

		this.hibernateUtil.salvarOuAtualizar(usuarios);
	}

	private void processarCSVPontuacao() throws Exception {

		CSVReader reader = lerArquivo("tblPontuacao.csv");

		List<Pontuacao> pontuacoes = new ArrayList<Pontuacao>();

		String nomeDaPrimeiraColuna = "id_Codigo";

		preencherObjeto(reader, pontuacoes, nomeDaPrimeiraColuna, "Pontuacao");

		this.hibernateUtil.executarSQL("delete from pontuacao");

		this.hibernateUtil.salvarOuAtualizar(pontuacoes);
	}

	private void processarCSVControlePedido() throws Exception {

		CSVReader reader = lerArquivo("tblControlePedidos.csv");

		List<ControlePedido> controlesPedidos = new ArrayList<ControlePedido>();

		String nomeDaPrimeiraColuna = "id_Codigo";

		preencherObjeto(reader, controlesPedidos, nomeDaPrimeiraColuna, "ControlePedido");

		this.hibernateUtil.executarSQL("delete from controlepedido");

		this.hibernateUtil.salvarOuAtualizar(controlesPedidos);
	}

	private void processarCSVProdutos() throws Exception {

		CSVReader reader = lerArquivo("tblProdutos.csv");

		List<Produto> produtos = new ArrayList<Produto>();

		String nomeDaPrimeiraColuna = "id_Produtos";

		preencherObjeto(reader, produtos, nomeDaPrimeiraColuna, "Produto");

		this.hibernateUtil.executarSQL("delete from produto");

		this.hibernateUtil.salvarOuAtualizar(produtos);
	}

	private void processarCSVCategorias() throws Exception {

		CSVReader reader = lerArquivo("tblCategorias.csv");

		List<Categoria> categorias = new ArrayList<Categoria>();

		String nomeDaPrimeiraColuna = "id_Categoria";

		preencherObjeto(reader, categorias, nomeDaPrimeiraColuna, "Categoria");

		this.hibernateUtil.executarSQL("delete from categoria");

		this.hibernateUtil.salvarOuAtualizar(categorias);
	}

	private void processarCSVCentroDistribuicao() throws Exception {

		CSVReader reader = lerArquivo("tblCDA.csv");

		List<CentroDistribuicao> centrosDistribuicao = new ArrayList<CentroDistribuicao>();

		String nomeDaPrimeiraColuna = "id_Estoque";

		preencherObjeto(reader, centrosDistribuicao, nomeDaPrimeiraColuna, "CentroDistribuicao");

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
