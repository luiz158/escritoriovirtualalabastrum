package escritoriovirtualalabastrum.controller;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
import escritoriovirtualalabastrum.auxiliar.PontuacaoAuxiliar;
import escritoriovirtualalabastrum.hibernate.HibernateUtil;
import escritoriovirtualalabastrum.modelo.Pontuacao;
import escritoriovirtualalabastrum.modelo.Usuario;
import escritoriovirtualalabastrum.sessao.SessaoUsuario;
import escritoriovirtualalabastrum.util.Util;

@Resource
public class PontuacaoController {

	private Result result;
	private HibernateUtil hibernateUtil;
	private SessaoUsuario sessaoUsuario;
	private Validator validator;

	public PontuacaoController(Result result, HibernateUtil hibernateUtil, SessaoUsuario sessaoUsuario, Validator validator) {

		this.result = result;
		this.hibernateUtil = hibernateUtil;
		this.sessaoUsuario = sessaoUsuario;
		this.validator = validator;
	}

	@Funcionalidade
	public void acessarTelaPontuacao() {

		DateTime hoje = new DateTime();
		DateTime primeiroDiaDoMesAtual = hoje.withDayOfMonth(1);

		result.include("primeiroDiaMes", primeiroDiaDoMesAtual.toGregorianCalendar());
		result.include("dataAtual", hoje.toGregorianCalendar());

		result.include("posicoes", obterPosicoes());
	}

	@Funcionalidade
	public void gerarRelatorioPontuacao(String posicao, Integer codigoUsuario, GregorianCalendar dataInicial, GregorianCalendar dataFinal) {

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
				validator.onErrorRedirectTo(this).acessarTelaPontuacao();
				return;
			}

			else {

				malaDireta = gerarMalaDiretaDeAcordoComFiltros(posicao, codigoUsuario);
			}
		}

		gerarRelatorioPontuacao(dataInicial, dataFinal, malaDireta);

		Usuario usuarioPesquisado = new Usuario();
		usuarioPesquisado.setId_Codigo(codigoUsuario);

		usuarioPesquisado = this.hibernateUtil.selecionar(usuarioPesquisado, MatchMode.EXACT);

		result.include("usuarioPesquisado", usuarioPesquisado);
		result.include("posicaoConsiderada", obterPosicoes().get(posicao));
	}

	private void gerarRelatorioPontuacao(GregorianCalendar dataInicial, GregorianCalendar dataFinal, TreeMap<Integer, MalaDireta> malaDireta) {

		List<PontuacaoAuxiliar> pontuacoes = new ArrayList<PontuacaoAuxiliar>();

		for (Entry<Integer, MalaDireta> usuario : malaDireta.entrySet()) {

			PontuacaoAuxiliar pontuacaoAuxiliar = new PontuacaoAuxiliar();
			pontuacaoAuxiliar.setMalaDireta(usuario.getValue());

			DateTime hoje = new DateTime();
			DateTime primeiroDiaDoMesAtual = hoje.withDayOfMonth(1);

			if (Util.vazio(dataInicial)) {

				dataInicial = primeiroDiaDoMesAtual.toGregorianCalendar();
			}

			if (Util.vazio(dataFinal)) {

				dataFinal = hoje.toGregorianCalendar();
			}

			dataFinal.add(Calendar.DAY_OF_MONTH, +1);

			List<Criterion> restricoes = new ArrayList<Criterion>();

			restricoes.add(Restrictions.between("Dt_Pontos", dataInicial, dataFinal));

			Pontuacao pontuacaoFiltro = new Pontuacao();
			pontuacaoFiltro.setId_Codigo(usuario.getValue().getUsuario().getId_Codigo());

			List<Pontuacao> pontuacoesBanco = hibernateUtil.buscar(pontuacaoFiltro, restricoes);

			for (Pontuacao pontuacaoBanco : pontuacoesBanco) {

				pontuacaoAuxiliar.setPontuacaoAtividade(pontuacaoAuxiliar.getPontuacaoAtividade().add(pontuacaoBanco.getPntAtividade()));
				pontuacaoAuxiliar.setPontuacaoIngresso(pontuacaoAuxiliar.getPontuacaoIngresso().add(pontuacaoBanco.getPntIngresso()));
				pontuacaoAuxiliar.setPontuacaoProdutos(pontuacaoAuxiliar.getPontuacaoProdutos().add(pontuacaoBanco.getPntProduto()));
			}

			pontuacoes.add(pontuacaoAuxiliar);
		}

		for (PontuacaoAuxiliar x : pontuacoes) {

			if (x.getMalaDireta().getUsuario().getId_Codigo().equals(71619)) {

				System.out.println(x.getMalaDireta().getUsuario().getvNome());
				System.out.println(x.getTotal());
				System.out.println();
			}
		}

		result.include("relatorioPontuacao", pontuacoes);
		result.include("quantidadeElementos", pontuacoes.size());
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
