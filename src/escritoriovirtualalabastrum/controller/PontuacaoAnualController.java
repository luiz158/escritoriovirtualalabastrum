package escritoriovirtualalabastrum.controller;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;

import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.ValidationMessage;
import escritoriovirtualalabastrum.anotacoes.Funcionalidade;
import escritoriovirtualalabastrum.auxiliar.MalaDireta;
import escritoriovirtualalabastrum.auxiliar.PontuacaoAnualAuxiliar;
import escritoriovirtualalabastrum.hibernate.HibernateUtil;
import escritoriovirtualalabastrum.modelo.Pontuacao;
import escritoriovirtualalabastrum.modelo.Usuario;
import escritoriovirtualalabastrum.sessao.SessaoUsuario;
import escritoriovirtualalabastrum.util.Util;

@Resource
public class PontuacaoAnualController {

	private Result result;
	private HibernateUtil hibernateUtil;
	private SessaoUsuario sessaoUsuario;
	private Validator validator;

	public PontuacaoAnualController(Result result, HibernateUtil hibernateUtil, SessaoUsuario sessaoUsuario, Validator validator) {

		this.result = result;
		this.hibernateUtil = hibernateUtil;
		this.sessaoUsuario = sessaoUsuario;
		this.validator = validator;
	}

	@Funcionalidade
	public void acessarTelaPontuacaoAnual() {

		result.include("posicoes", obterPosicoes());
	}

	private void validarQualificacao() {

		Usuario usuarioLogado = this.hibernateUtil.selecionar(new Usuario(this.sessaoUsuario.getUsuario().getId_Codigo()));

		if (Util.preenchido(usuarioLogado.getDesquaLider()) && usuarioLogado.getDesquaLider().equals("1")) {

			validator.add(new ValidationMessage("Em virtude de sua inatividade há 2 meses ou mais, sua rede foi transferida para o 1º gerente ATIVO da sua linha ascendente. <br><br>FIQUE ATIVO NESTE MÊS E RECUPERE SUA REDE. <b> ÚLTIMA CHANCE!!! </b>", "Atenção"));
			validator.onErrorRedirectTo(this).acessarTelaPontuacaoAnual();
		}
	}

	@Funcionalidade
	public void gerarRelatorioPontuacaoAnual(String posicao, Integer codigoUsuario, Integer ano) {

		validarQualificacao();

		Integer codigoUsuarioLogado = this.sessaoUsuario.getUsuario().getId_Codigo();

		TreeMap<Integer, MalaDireta> malaDireta = null;

		if (Util.vazio(codigoUsuario)) {

			codigoUsuario = codigoUsuarioLogado;
		}

		if (codigoUsuario.equals(codigoUsuarioLogado)) {

			malaDireta = gerarMalaDiretaDeAcordoComFiltros(posicao, codigoUsuario);
		}

		else {

			TreeMap<Integer, MalaDireta> malaDiretaUsuarioLogado = new TreeMap<Integer, MalaDireta>();

			gerarMalaDiretaTodasPosicoes(codigoUsuarioLogado, malaDiretaUsuarioLogado);

			if (!malaDiretaUsuarioLogado.containsKey(codigoUsuario)) {

				validator.add(new ValidationMessage("O código informado não está na sua rede", "Erro"));
				validator.onErrorRedirectTo(this).acessarTelaPontuacaoAnual();
			}

			else {

				malaDireta = gerarMalaDiretaDeAcordoComFiltros(posicao, codigoUsuario);
			}
		}

		gerarRelatorioPontuacaoAnual(ano, malaDireta, codigoUsuario);

		result.include("posicaoConsiderada", obterPosicoes().get(posicao));
	}

	private void gerarRelatorioPontuacaoAnual(Integer ano, TreeMap<Integer, MalaDireta> malaDireta, Integer codigoUsuario) {

		Usuario usuarioPesquisado = new Usuario();
		usuarioPesquisado.setId_Codigo(codigoUsuario);

		usuarioPesquisado = this.hibernateUtil.selecionar(usuarioPesquisado, MatchMode.EXACT);
		result.include("usuarioPesquisado", usuarioPesquisado);

		malaDireta.put(0, new MalaDireta(usuarioPesquisado, 0));

		List<PontuacaoAnualAuxiliar> pontuacoes = calcularPontuacoesMensalmente(ano, malaDireta);

		result.include("pontuacoes", pontuacoes);
	}

	private List<PontuacaoAnualAuxiliar> calcularPontuacoesMensalmente(Integer ano, TreeMap<Integer, MalaDireta> malaDireta) {

		List<PontuacaoAnualAuxiliar> pontuacoes = new ArrayList<PontuacaoAnualAuxiliar>();

		for (Entry<Integer, MalaDireta> usuario : malaDireta.entrySet()) {

			PontuacaoAnualAuxiliar pontuacaoAnualAuxiliar = new PontuacaoAnualAuxiliar();
			pontuacaoAnualAuxiliar.setMalaDireta(usuario.getValue());

			Pontuacao pontuacaoFiltro = new Pontuacao();
			pontuacaoFiltro.setId_Codigo(pontuacaoAnualAuxiliar.getMalaDireta().getUsuario().getId_Codigo());

			DateTime primeiroDiaJaneiro = new DateTime(ano, 1, 1, 0, 0, 0);
			DateTime ultimoDiaJaneiro = new DateTime(ano, 1, primeiroDiaJaneiro.dayOfMonth().withMaximumValue().dayOfMonth().get(), 0, 0, 0);

			List<Criterion> restricoesJaneiro = new ArrayList<Criterion>();
			restricoesJaneiro.add(Restrictions.between("Dt_Pontos", primeiroDiaJaneiro.toGregorianCalendar(), ultimoDiaJaneiro.toGregorianCalendar()));

			List<Pontuacao> pontuacoesJaneiro = hibernateUtil.buscar(pontuacaoFiltro, restricoesJaneiro);

			for (Pontuacao pontuacaoBanco : pontuacoesJaneiro) {

				pontuacaoAnualAuxiliar.setPontuacaoAtividadeJaneiro(pontuacaoAnualAuxiliar.getPontuacaoAtividadeJaneiro().add(pontuacaoBanco.getPntAtividade()));
				pontuacaoAnualAuxiliar.setPontuacaoProdutosJaneiro(pontuacaoAnualAuxiliar.getPontuacaoProdutosJaneiro().add(pontuacaoBanco.getPntProduto()));
			}

			DateTime primeiroDiaFevereiro = new DateTime(ano, 2, 1, 0, 0, 0);
			DateTime ultimoDiaFevereiro = new DateTime(ano, 2, primeiroDiaFevereiro.dayOfMonth().withMaximumValue().dayOfMonth().get(), 0, 0, 0);

			List<Criterion> restricoesFevereiro = new ArrayList<Criterion>();
			restricoesFevereiro.add(Restrictions.between("Dt_Pontos", primeiroDiaFevereiro.toGregorianCalendar(), ultimoDiaFevereiro.toGregorianCalendar()));

			List<Pontuacao> pontuacoesFevereiro = hibernateUtil.buscar(pontuacaoFiltro, restricoesFevereiro);

			for (Pontuacao pontuacaoBanco : pontuacoesFevereiro) {

				pontuacaoAnualAuxiliar.setPontuacaoAtividadeFevereiro(pontuacaoAnualAuxiliar.getPontuacaoAtividadeFevereiro().add(pontuacaoBanco.getPntAtividade()));
				pontuacaoAnualAuxiliar.setPontuacaoProdutosFevereiro(pontuacaoAnualAuxiliar.getPontuacaoProdutosFevereiro().add(pontuacaoBanco.getPntProduto()));
			}

			DateTime primeiroDiaMarco = new DateTime(ano, 3, 1, 0, 0, 0);
			DateTime ultimoDiaMarco = new DateTime(ano, 3, primeiroDiaMarco.dayOfMonth().withMaximumValue().dayOfMonth().get(), 0, 0, 0);

			List<Criterion> restricoesMarco = new ArrayList<Criterion>();
			restricoesMarco.add(Restrictions.between("Dt_Pontos", primeiroDiaMarco.toGregorianCalendar(), ultimoDiaMarco.toGregorianCalendar()));

			List<Pontuacao> pontuacoesMarco = hibernateUtil.buscar(pontuacaoFiltro, restricoesMarco);

			for (Pontuacao pontuacaoBanco : pontuacoesMarco) {

				pontuacaoAnualAuxiliar.setPontuacaoAtividadeMarco(pontuacaoAnualAuxiliar.getPontuacaoAtividadeMarco().add(pontuacaoBanco.getPntAtividade()));
				pontuacaoAnualAuxiliar.setPontuacaoProdutosMarco(pontuacaoAnualAuxiliar.getPontuacaoProdutosMarco().add(pontuacaoBanco.getPntProduto()));
			}

			DateTime primeiroDiaAbril = new DateTime(ano, 4, 1, 0, 0, 0);
			DateTime ultimoDiaAbril = new DateTime(ano, 4, primeiroDiaAbril.dayOfMonth().withMaximumValue().dayOfMonth().get(), 0, 0, 0);

			List<Criterion> restricoesAbril = new ArrayList<Criterion>();
			restricoesAbril.add(Restrictions.between("Dt_Pontos", primeiroDiaAbril.toGregorianCalendar(), ultimoDiaAbril.toGregorianCalendar()));

			List<Pontuacao> pontuacoesAbril = hibernateUtil.buscar(pontuacaoFiltro, restricoesAbril);

			for (Pontuacao pontuacaoBanco : pontuacoesAbril) {

				pontuacaoAnualAuxiliar.setPontuacaoAtividadeAbril(pontuacaoAnualAuxiliar.getPontuacaoAtividadeAbril().add(pontuacaoBanco.getPntAtividade()));
				pontuacaoAnualAuxiliar.setPontuacaoProdutosAbril(pontuacaoAnualAuxiliar.getPontuacaoProdutosAbril().add(pontuacaoBanco.getPntProduto()));
			}

			DateTime primeiroDiaMaio = new DateTime(ano, 5, 1, 0, 0, 0);
			DateTime ultimoDiaMaio = new DateTime(ano, 5, primeiroDiaMaio.dayOfMonth().withMaximumValue().dayOfMonth().get(), 0, 0, 0);

			List<Criterion> restricoesMaio = new ArrayList<Criterion>();
			restricoesMaio.add(Restrictions.between("Dt_Pontos", primeiroDiaMaio.toGregorianCalendar(), ultimoDiaMaio.toGregorianCalendar()));

			List<Pontuacao> pontuacoesMaio = hibernateUtil.buscar(pontuacaoFiltro, restricoesMaio);

			for (Pontuacao pontuacaoBanco : pontuacoesMaio) {

				pontuacaoAnualAuxiliar.setPontuacaoAtividadeMaio(pontuacaoAnualAuxiliar.getPontuacaoAtividadeMaio().add(pontuacaoBanco.getPntAtividade()));
				pontuacaoAnualAuxiliar.setPontuacaoProdutosMaio(pontuacaoAnualAuxiliar.getPontuacaoProdutosMaio().add(pontuacaoBanco.getPntProduto()));
			}

			DateTime primeiroDiaJunho = new DateTime(ano, 6, 1, 0, 0, 0);
			DateTime ultimoDiaJunho = new DateTime(ano, 6, primeiroDiaJunho.dayOfMonth().withMaximumValue().dayOfMonth().get(), 0, 0, 0);

			List<Criterion> restricoesJunho = new ArrayList<Criterion>();
			restricoesJunho.add(Restrictions.between("Dt_Pontos", primeiroDiaJunho.toGregorianCalendar(), ultimoDiaJunho.toGregorianCalendar()));

			List<Pontuacao> pontuacoesJunho = hibernateUtil.buscar(pontuacaoFiltro, restricoesJunho);

			for (Pontuacao pontuacaoBanco : pontuacoesJunho) {

				pontuacaoAnualAuxiliar.setPontuacaoAtividadeJunho(pontuacaoAnualAuxiliar.getPontuacaoAtividadeJunho().add(pontuacaoBanco.getPntAtividade()));
				pontuacaoAnualAuxiliar.setPontuacaoProdutosJunho(pontuacaoAnualAuxiliar.getPontuacaoProdutosJunho().add(pontuacaoBanco.getPntProduto()));
			}

			DateTime primeiroDiaJulho = new DateTime(ano, 7, 1, 0, 0, 0);
			DateTime ultimoDiaJulho = new DateTime(ano, 7, primeiroDiaJulho.dayOfMonth().withMaximumValue().dayOfMonth().get(), 0, 0, 0);

			List<Criterion> restricoesJulho = new ArrayList<Criterion>();
			restricoesJulho.add(Restrictions.between("Dt_Pontos", primeiroDiaJulho.toGregorianCalendar(), ultimoDiaJulho.toGregorianCalendar()));

			List<Pontuacao> pontuacoesJulho = hibernateUtil.buscar(pontuacaoFiltro, restricoesJulho);

			for (Pontuacao pontuacaoBanco : pontuacoesJulho) {

				pontuacaoAnualAuxiliar.setPontuacaoAtividadeJulho(pontuacaoAnualAuxiliar.getPontuacaoAtividadeJulho().add(pontuacaoBanco.getPntAtividade()));
				pontuacaoAnualAuxiliar.setPontuacaoProdutosJulho(pontuacaoAnualAuxiliar.getPontuacaoProdutosJulho().add(pontuacaoBanco.getPntProduto()));
			}

			DateTime primeiroDiaAgosto = new DateTime(ano, 8, 1, 0, 0, 0);
			DateTime ultimoDiaAgosto = new DateTime(ano, 8, primeiroDiaAgosto.dayOfMonth().withMaximumValue().dayOfMonth().get(), 0, 0, 0);

			List<Criterion> restricoesAgosto = new ArrayList<Criterion>();
			restricoesAgosto.add(Restrictions.between("Dt_Pontos", primeiroDiaAgosto.toGregorianCalendar(), ultimoDiaAgosto.toGregorianCalendar()));

			List<Pontuacao> pontuacoesAgosto = hibernateUtil.buscar(pontuacaoFiltro, restricoesAgosto);

			for (Pontuacao pontuacaoBanco : pontuacoesAgosto) {

				pontuacaoAnualAuxiliar.setPontuacaoAtividadeAgosto(pontuacaoAnualAuxiliar.getPontuacaoAtividadeAgosto().add(pontuacaoBanco.getPntAtividade()));
				pontuacaoAnualAuxiliar.setPontuacaoProdutosAgosto(pontuacaoAnualAuxiliar.getPontuacaoProdutosAgosto().add(pontuacaoBanco.getPntProduto()));
			}

			DateTime primeiroDiaSetembro = new DateTime(ano, 9, 1, 0, 0, 0);
			DateTime ultimoDiaSetembro = new DateTime(ano, 9, primeiroDiaSetembro.dayOfMonth().withMaximumValue().dayOfMonth().get(), 0, 0, 0);

			List<Criterion> restricoesSetembro = new ArrayList<Criterion>();
			restricoesSetembro.add(Restrictions.between("Dt_Pontos", primeiroDiaSetembro.toGregorianCalendar(), ultimoDiaSetembro.toGregorianCalendar()));

			List<Pontuacao> pontuacoesSetembro = hibernateUtil.buscar(pontuacaoFiltro, restricoesSetembro);

			for (Pontuacao pontuacaoBanco : pontuacoesSetembro) {

				pontuacaoAnualAuxiliar.setPontuacaoAtividadeSetembro(pontuacaoAnualAuxiliar.getPontuacaoAtividadeSetembro().add(pontuacaoBanco.getPntAtividade()));
				pontuacaoAnualAuxiliar.setPontuacaoProdutosSetembro(pontuacaoAnualAuxiliar.getPontuacaoProdutosSetembro().add(pontuacaoBanco.getPntProduto()));
			}

			DateTime primeiroDiaOutubro = new DateTime(ano, 10, 1, 0, 0, 0);
			DateTime ultimoDiaOutubro = new DateTime(ano, 10, primeiroDiaOutubro.dayOfMonth().withMaximumValue().dayOfMonth().get(), 0, 0, 0);

			List<Criterion> restricoesOutubro = new ArrayList<Criterion>();
			restricoesOutubro.add(Restrictions.between("Dt_Pontos", primeiroDiaOutubro.toGregorianCalendar(), ultimoDiaOutubro.toGregorianCalendar()));

			List<Pontuacao> pontuacoesOutubro = hibernateUtil.buscar(pontuacaoFiltro, restricoesOutubro);

			for (Pontuacao pontuacaoBanco : pontuacoesOutubro) {

				pontuacaoAnualAuxiliar.setPontuacaoAtividadeOutubro(pontuacaoAnualAuxiliar.getPontuacaoAtividadeOutubro().add(pontuacaoBanco.getPntAtividade()));
				pontuacaoAnualAuxiliar.setPontuacaoProdutosOutubro(pontuacaoAnualAuxiliar.getPontuacaoProdutosOutubro().add(pontuacaoBanco.getPntProduto()));
			}

			DateTime primeiroDiaNovembro = new DateTime(ano, 11, 1, 0, 0, 0);
			DateTime ultimoDiaNovembro = new DateTime(ano, 11, primeiroDiaNovembro.dayOfMonth().withMaximumValue().dayOfMonth().get(), 0, 0, 0);

			List<Criterion> restricoesNovembro = new ArrayList<Criterion>();
			restricoesNovembro.add(Restrictions.between("Dt_Pontos", primeiroDiaNovembro.toGregorianCalendar(), ultimoDiaNovembro.toGregorianCalendar()));

			List<Pontuacao> pontuacoesNovembro = hibernateUtil.buscar(pontuacaoFiltro, restricoesNovembro);

			for (Pontuacao pontuacaoBanco : pontuacoesNovembro) {

				pontuacaoAnualAuxiliar.setPontuacaoAtividadeNovembro(pontuacaoAnualAuxiliar.getPontuacaoAtividadeNovembro().add(pontuacaoBanco.getPntAtividade()));
				pontuacaoAnualAuxiliar.setPontuacaoProdutosNovembro(pontuacaoAnualAuxiliar.getPontuacaoProdutosNovembro().add(pontuacaoBanco.getPntProduto()));
			}

			DateTime primeiroDiaDezembro = new DateTime(ano, 12, 1, 0, 0, 0);
			DateTime ultimoDiaDezembro = new DateTime(ano, 12, primeiroDiaDezembro.dayOfMonth().withMaximumValue().dayOfMonth().get(), 0, 0, 0);

			List<Criterion> restricoesDezembro = new ArrayList<Criterion>();
			restricoesDezembro.add(Restrictions.between("Dt_Pontos", primeiroDiaDezembro.toGregorianCalendar(), ultimoDiaDezembro.toGregorianCalendar()));

			List<Pontuacao> pontuacoesDezembro = hibernateUtil.buscar(pontuacaoFiltro, restricoesDezembro);

			for (Pontuacao pontuacaoBanco : pontuacoesDezembro) {

				pontuacaoAnualAuxiliar.setPontuacaoAtividadeDezembro(pontuacaoAnualAuxiliar.getPontuacaoAtividadeDezembro().add(pontuacaoBanco.getPntAtividade()));
				pontuacaoAnualAuxiliar.setPontuacaoProdutosDezembro(pontuacaoAnualAuxiliar.getPontuacaoProdutosDezembro().add(pontuacaoBanco.getPntProduto()));
			}

			pontuacoes.add(pontuacaoAnualAuxiliar);
		}

		return pontuacoes;
	}

	private TreeMap<Integer, MalaDireta> gerarMalaDiretaDeAcordoComFiltros(String posicao, Integer codigoUsuario) {

		TreeMap<Integer, MalaDireta> malaDireta = new TreeMap<Integer, MalaDireta>();

		if (posicao.equals("Todas")) {

			gerarMalaDiretaTodasPosicoes(codigoUsuario, malaDireta);
		}

		else {

			this.pesquisarMalaDiretaSemRecursividade(codigoUsuario, malaDireta, 1, posicao);
		}

		return malaDireta;
	}

	private void gerarMalaDiretaTodasPosicoes(Integer codigoUsuario, TreeMap<Integer, MalaDireta> malaDireta) {

		LinkedHashMap<String, String> posicoes = obterPosicoes();

		for (Entry<String, String> posicao : posicoes.entrySet()) {

			if (!posicao.getKey().equals("Todas")) {

				this.pesquisarMalaDiretaComRecursividade(codigoUsuario, malaDireta, 1, posicao.getKey());
			}
		}
	}

	private void pesquisarMalaDiretaComRecursividade(Integer codigo, TreeMap<Integer, MalaDireta> malaDireta, int nivel, String posicao) {

		Usuario usuario = new Usuario();

		try {

			Field field = usuario.getClass().getDeclaredField(posicao);

			field.setAccessible(true);

			field.set(usuario, codigo);
		}

		catch (Exception e) {

			e.printStackTrace();
		}

		List<Usuario> usuariosPatrocinados = hibernateUtil.buscar(usuario);

		for (Usuario usuarioPatrocinado : usuariosPatrocinados) {

			if (!codigo.equals(usuarioPatrocinado.getId_Codigo())) {

				if (!malaDireta.containsKey(usuarioPatrocinado.getId_Codigo())) {

					malaDireta.put(usuarioPatrocinado.getId_Codigo(), new MalaDireta(usuarioPatrocinado, nivel));

					pesquisarMalaDiretaComRecursividade(usuarioPatrocinado.getId_Codigo(), malaDireta, nivel + 1, "id_Patroc");
				}
			}
		}
	}

	private void pesquisarMalaDiretaSemRecursividade(Integer codigo, TreeMap<Integer, MalaDireta> malaDireta, int nivel, String posicao) {

		Usuario usuario = new Usuario();

		try {

			Field field = usuario.getClass().getDeclaredField(posicao);

			field.setAccessible(true);

			field.set(usuario, codigo);
		}

		catch (Exception e) {

			e.printStackTrace();
		}

		List<Usuario> usuariosPatrocinados = hibernateUtil.buscar(usuario);

		for (Usuario usuarioPatrocinado : usuariosPatrocinados) {

			if (!codigo.equals(usuarioPatrocinado.getId_Codigo())) {

				malaDireta.put(usuarioPatrocinado.getId_Codigo(), new MalaDireta(usuarioPatrocinado, nivel));
			}
		}
	}

	private LinkedHashMap<String, String> obterPosicoes() {

		LinkedHashMap<String, String> posicoes = new LinkedHashMap<String, String>();

		posicoes.put("Todas", "Todas");
		posicoes.put("id_Patroc", "Patrocinador");
		posicoes.put("id_Dem", "Demonstrador");
		posicoes.put("id_S", "Sênior");
		posicoes.put("id_M", "Gerente");
		posicoes.put("id_M1", "M1");
		posicoes.put("id_M2", "M2");
		posicoes.put("id_M3", "M3");
		posicoes.put("id_M4", "M4");
		posicoes.put("id_M5", "M5");
		posicoes.put("id_GB", "Gerente Bronze");
		posicoes.put("id_GP", "Gerente Prata");
		posicoes.put("id_GO", "Gerente Ouro");
		posicoes.put("id_GE", "Esmeralda");
		posicoes.put("id_LA", "Topázio");
		posicoes.put("id_CR", "Diamante");
		posicoes.put("id_DR", "Diamante Duplo");
		posicoes.put("id_DD", "Diamante Triplo");
		posicoes.put("id_DS", "Diamante Plenus");
		posicoes.put("id_Pres", "Presidente");

		return posicoes;
	}

}
