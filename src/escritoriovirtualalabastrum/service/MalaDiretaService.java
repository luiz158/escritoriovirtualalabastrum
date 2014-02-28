package escritoriovirtualalabastrum.service;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.joda.time.DateTime;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.ValidationMessage;
import escritoriovirtualalabastrum.auxiliar.BonificacaoAuxiliar;
import escritoriovirtualalabastrum.auxiliar.MalaDireta;
import escritoriovirtualalabastrum.controller.MalaDiretaController;
import escritoriovirtualalabastrum.controller.PontuacaoController;
import escritoriovirtualalabastrum.hibernate.HibernateUtil;
import escritoriovirtualalabastrum.modelo.Usuario;
import escritoriovirtualalabastrum.sessao.SessaoUsuario;
import escritoriovirtualalabastrum.util.Util;

public class MalaDiretaService {

	public static final String TODAS = "Todas";
	public static final String DIAMANTE = "Diamante";

	private HibernateUtil hibernateUtil;
	private Validator validator;

	public TreeMap<Integer, MalaDireta> gerarMalaDireta(String posicao, Integer codigoUsuario, Integer codigoUsuarioLogado) {

		TreeMap<Integer, MalaDireta> malaDireta = new TreeMap<Integer, MalaDireta>();

		if (Util.vazio(codigoUsuario)) {

			codigoUsuario = codigoUsuarioLogado;
		}

		if (codigoUsuario.equals(codigoUsuarioLogado)) {

			malaDireta = gerarMalaDiretaDeAcordoComFiltros(posicao, codigoUsuario);
		}

		else {

			malaDireta = gerarMalaDiretaTodasPosicoes(codigoUsuarioLogado);

			if (!malaDireta.containsKey(codigoUsuario)) {

				validator.add(new ValidationMessage("O código informado não está na sua mala direta", "Erro"));
				validator.onErrorRedirectTo(MalaDiretaController.class).acessarTelaMalaDireta();
			}

			else {

				malaDireta = gerarMalaDiretaDeAcordoComFiltros(posicao, codigoUsuario);
			}
		}

		return malaDireta;
	}

	private TreeMap<Integer, MalaDireta> gerarMalaDiretaDeAcordoComFiltros(String posicao, Integer codigoUsuario) {

		TreeMap<Integer, MalaDireta> malaDireta = new TreeMap<Integer, MalaDireta>();

		if (posicao.equals(TODAS)) {

			malaDireta = gerarMalaDiretaTodasPosicoes(codigoUsuario);
		}

		else {

			malaDireta = pesquisarMalaDiretaSemRecursividade(codigoUsuario, 1, posicao);
		}

		return malaDireta;
	}

	private TreeMap<Integer, MalaDireta> gerarMalaDiretaTodasPosicoes(Integer codigoUsuario) {

		TreeMap<Integer, MalaDireta> malaDireta = new TreeMap<Integer, MalaDireta>();

		LinkedHashMap<String, String> posicoes = obterPosicoes();

		for (Entry<String, String> posicao : posicoes.entrySet()) {

			if (!posicao.getKey().equals(TODAS)) {

				this.pesquisarMalaDiretaComRecursividade(codigoUsuario, malaDireta, 1, posicao.getKey());
			}
		}

		return malaDireta;
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

	private TreeMap<Integer, MalaDireta> pesquisarMalaDiretaSemRecursividade(Integer codigo, int nivel, String posicao) {

		TreeMap<Integer, MalaDireta> malaDireta = new TreeMap<Integer, MalaDireta>();

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

		return malaDireta;
	}

	public BonificacaoAuxiliar verificaSeMetaDeDiamanteFoiAtingida(Usuario usuario, Result result, HibernateUtil hibernateUtil, Validator validator, Integer ano, Integer mes) {

		DateTime dataInicial = new DateTime(ano, mes, 1, 0, 0, 0);
		DateTime dataFinal = new DateTime(ano, mes, dataInicial.dayOfMonth().withMaximumValue().dayOfMonth().get(), 0, 0, 0);

		SessaoUsuario sessaoUsuario = new SessaoUsuario();
		sessaoUsuario.login(usuario);
		PontuacaoController pontuacaoController = new PontuacaoController(result, hibernateUtil, sessaoUsuario, validator);
		BonificacaoAuxiliar bonificacaoAuxiliar = pontuacaoController.gerarMalaDiretaECalcularPontuacaoDaRede(TODAS, sessaoUsuario.getUsuario().getId_Codigo(), dataInicial.toGregorianCalendar(), dataFinal.toGregorianCalendar(), PontuacaoController.TODOS, PontuacaoController.TODOS, sessaoUsuario.getUsuario().getId_Codigo());

		Integer quantidadeGraduados = calcularQuantidadeGraduados(usuario, result, hibernateUtil, validator, dataInicial, dataFinal);

		bonificacaoAuxiliar.setQuantidadeGraduados(quantidadeGraduados);

		return bonificacaoAuxiliar;
	}

	private Integer calcularQuantidadeGraduados(Usuario usuario, Result result, HibernateUtil hibernateUtil, Validator validator, DateTime dataInicial, DateTime dataFinal) {

		TreeMap<Integer, MalaDireta> malaDiretaPrimeiroNivel = new TreeMap<Integer, MalaDireta>();

		LinkedHashMap<String, String> posicoes = obterPosicoes();

		for (Entry<String, String> posicao : posicoes.entrySet()) {

			if (!posicao.getKey().equals(TODAS)) {

				encontrarMalaDiretaPrimeiroNivel(usuario, hibernateUtil, malaDiretaPrimeiroNivel, posicao.getKey());
			}
		}

		Integer quantidadeGraduados = 0;

		for (Entry<Integer, MalaDireta> primeiroNivel : malaDiretaPrimeiroNivel.entrySet()) {

			for (Entry<String, String> posicao : posicoes.entrySet()) {

				if (!posicao.getKey().equals(TODAS)) {

					quantidadeGraduados = encontrarGraduadosRecursivamente(primeiroNivel.getValue().getUsuario(), hibernateUtil, quantidadeGraduados, posicao.getKey(), 0, dataInicial.toGregorianCalendar(), dataFinal.toGregorianCalendar(), result, validator);

					if (quantidadeGraduados >= PontuacaoController.META_DIAMANTE_LINHAS_GRADUADOS) {

						break;
					}
				}
			}

			if (quantidadeGraduados >= PontuacaoController.META_DIAMANTE_LINHAS_GRADUADOS) {

				break;
			}
		}

		return quantidadeGraduados;
	}

	private int encontrarGraduadosRecursivamente(Usuario usuario, HibernateUtil hibernateUtil, int quantidadeGraduados, String posicao, int nivel, GregorianCalendar dataInicial, GregorianCalendar dataFinal, Result result, Validator validator) {

		Usuario usuarioFiltro = new Usuario();

		try {

			Field field = usuarioFiltro.getClass().getDeclaredField(posicao);

			field.setAccessible(true);

			field.set(usuarioFiltro, usuario.getId_Codigo());
		}

		catch (Exception e) {

			e.printStackTrace();
		}

		List<Usuario> usuariosPatrocinados = hibernateUtil.buscar(usuarioFiltro);

		for (Usuario usuarioPatrocinado : usuariosPatrocinados) {

			if (!usuario.getId_Codigo().equals(usuarioPatrocinado.getId_Codigo())) {

				if (usuarioPatrocinado.isAtivo(dataInicial, dataFinal)) {

					SessaoUsuario sessaoUsuario = new SessaoUsuario();
					sessaoUsuario.login(usuarioPatrocinado);

					PontuacaoController pontuacaoController = new PontuacaoController(result, hibernateUtil, sessaoUsuario, validator);
					BigDecimal pontuacao = pontuacaoController.gerarMalaDiretaECalcularPontuacaoDaRede(TODAS, sessaoUsuario.getUsuario().getId_Codigo(), dataInicial, dataFinal, PontuacaoController.TODOS, PontuacaoController.TODOS, sessaoUsuario.getUsuario().getId_Codigo()).getPontuacaoDiamante();

					if (pontuacao.compareTo(PontuacaoController.META_GRADUACAO) >= 0) {

						quantidadeGraduados++;
						break;
					}

				} else {

					quantidadeGraduados = encontrarGraduadosRecursivamente(usuarioPatrocinado, hibernateUtil, quantidadeGraduados, posicao, nivel + 1, dataInicial, dataFinal, result, validator);
				}
			}
		}

		return quantidadeGraduados;
	}

	private void encontrarMalaDiretaPrimeiroNivel(Usuario usuario, HibernateUtil hibernateUtil, TreeMap<Integer, MalaDireta> malaDireta, String posicao) {

		Usuario usuarioFiltro = new Usuario();

		try {

			Field field = usuarioFiltro.getClass().getDeclaredField(posicao);

			field.setAccessible(true);

			field.set(usuarioFiltro, usuario.getId_Codigo());
		}

		catch (Exception e) {

			e.printStackTrace();
		}

		List<Usuario> usuariosPatrocinados = hibernateUtil.buscar(usuarioFiltro);

		for (Usuario usuarioPatrocinado : usuariosPatrocinados) {

			if (!usuario.getId_Codigo().equals(usuarioPatrocinado.getId_Codigo())) {

				if (!malaDireta.containsKey(usuarioPatrocinado.getId_Codigo())) {

					malaDireta.put(usuarioPatrocinado.getId_Codigo(), new MalaDireta(usuarioPatrocinado, 1));
				}
			}
		}
	}

	public LinkedHashMap<String, String> obterPosicoes() {

		LinkedHashMap<String, String> posicoes = new LinkedHashMap<String, String>();

		posicoes.put(TODAS, TODAS);
		posicoes.put("id_Patroc", "Patrocinador");
		posicoes.put("id_S", "Executivo");
		posicoes.put("id_M1", "G1");
		posicoes.put("id_M2", "G2");
		posicoes.put("id_M3", "G3");
		posicoes.put("id_M4", "G4");
		posicoes.put("id_M5", "G5");
		posicoes.put("id_M6", "G6");
		posicoes.put("id_M7", "G7");
		posicoes.put("id_M8", "G8");
		posicoes.put("id_M9", "G9");
		posicoes.put("id_M10", "G10");
		posicoes.put("id_GB", "Gerente Bronze");
		posicoes.put("id_GP", "Gerente Prata");
		posicoes.put("id_GO", "Gerente Ouro");
		posicoes.put("id_M", "Esmeralda");
		posicoes.put("id_LA", "Topázio");
		posicoes.put("id_CR", DIAMANTE);
		posicoes.put("id_DR", "Diamante Duplo");
		posicoes.put("id_DD", "Diamante Triplo");
		posicoes.put("id_DS", "Diamante Plenus");
		posicoes.put("id_GE", "Diamante Real");
		posicoes.put("id_Pres", "Presidente");

		return posicoes;
	}

	public HibernateUtil getHibernateUtil() {
		return hibernateUtil;
	}

	public void setHibernateUtil(HibernateUtil hibernateUtil) {
		this.hibernateUtil = hibernateUtil;
	}

	public Validator getValidator() {
		return validator;
	}

	public void setValidator(Validator validator) {
		this.validator = validator;
	}
}