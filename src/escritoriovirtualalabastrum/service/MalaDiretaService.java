package escritoriovirtualalabastrum.service;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.joda.time.DateTime;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.ValidationMessage;
import escritoriovirtualalabastrum.auxiliar.BonificacaoIngressoAuxiliar;
import escritoriovirtualalabastrum.auxiliar.MalaDireta;
import escritoriovirtualalabastrum.controller.MalaDiretaController;
import escritoriovirtualalabastrum.controller.PontuacaoController;
import escritoriovirtualalabastrum.hibernate.HibernateUtil;
import escritoriovirtualalabastrum.modelo.Usuario;
import escritoriovirtualalabastrum.sessao.SessaoUsuario;
import escritoriovirtualalabastrum.util.Util;

public class MalaDiretaService {

	public static final String DIAMANTE = "Diamante";
	public static final String TOPÁZIO = "Topázio";
	public static final String ESMERALDA = "Esmeralda";
	public static final String GERENTE_OURO = "Gerente Ouro";
	public static final String GERENTE_PRATA = "Gerente Prata";
	public static final String GERENTE_BRONZE = "Gerente Bronze";
	public static final String TODAS = "Todas";

	private HibernateUtil hibernateUtil;
	private Validator validator;

	public MalaDiretaService() {
	}

	public MalaDiretaService(HibernateUtil hibernateUtil) {

		this.hibernateUtil = hibernateUtil;
	}

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

	public void gerarMalaDiretaDeAcordoComAtividade(String posicao, Integer codigoUsuario, GregorianCalendar dataInicial, GregorianCalendar dataFinal, Integer nivel, TreeMap<Integer, MalaDireta> malaDireta) {

		Usuario usuario = new Usuario();

		try {

			Field field = usuario.getClass().getDeclaredField(posicao);

			field.setAccessible(true);

			field.set(usuario, codigoUsuario);
		}

		catch (Exception e) {

			e.printStackTrace();
		}

		List<Usuario> usuariosPatrocinados = hibernateUtil.buscar(usuario);

		for (Usuario usuarioPatrocinado : usuariosPatrocinados) {

			if (!codigoUsuario.equals(usuarioPatrocinado.getId_Codigo())) {

				if (!malaDireta.containsKey(usuarioPatrocinado.getId_Codigo())) {

					malaDireta.put(usuarioPatrocinado.getId_Codigo(), new MalaDireta(usuarioPatrocinado, nivel));

					if (usuarioPatrocinado.isAtivo(dataInicial, dataFinal)) {

						gerarMalaDiretaDeAcordoComAtividade(posicao, usuarioPatrocinado.getId_Codigo(), dataInicial, dataFinal, nivel + 1, malaDireta);

					} else {

						gerarMalaDiretaDeAcordoComAtividade(posicao, usuarioPatrocinado.getId_Codigo(), dataInicial, dataFinal, nivel, malaDireta);
					}
				}
			}
		}
	}

	public void gerarMalaDiretaComCompressaoDinamicaDeDiamante(String posicao, Integer codigoUsuario, GregorianCalendar dataInicial, GregorianCalendar dataFinal, Integer nivel, TreeMap<Integer, MalaDireta> malaDireta, Result result) {

		Usuario usuario = new Usuario();

		try {

			Field field = usuario.getClass().getDeclaredField(posicao);

			field.setAccessible(true);

			field.set(usuario, codigoUsuario);
		}

		catch (Exception e) {

			e.printStackTrace();
		}

		List<Usuario> usuariosPatrocinados = hibernateUtil.buscar(usuario);

		for (Usuario usuarioPatrocinado : usuariosPatrocinados) {

			if (!codigoUsuario.equals(usuarioPatrocinado.getId_Codigo())) {

				if (!malaDireta.containsKey(usuarioPatrocinado.getId_Codigo())) {

					malaDireta.put(usuarioPatrocinado.getId_Codigo(), new MalaDireta(usuarioPatrocinado, nivel));

					if (usuarioPatrocinado.getPosAtual().toLowerCase().contains(MalaDiretaService.DIAMANTE.toLowerCase())) {

						BonificacaoIngressoAuxiliar calculosDiamante = new MalaDiretaService().verificaSeMetaDeDiamanteFoiAtingida(usuarioPatrocinado, result, hibernateUtil, validator, dataInicial.get(Calendar.YEAR), dataInicial.get(Calendar.MONTH) + 1);
						if (calculosDiamante.getQuantidadeGraduados() >= BonificacaoIngressoService.META_DIAMANTE_LINHAS_GRADUADOS && calculosDiamante.getPontuacaoDiamante().compareTo(BonificacaoIngressoService.META_DIAMANTE_PONTUACAO) >= 0) {

							gerarMalaDiretaComCompressaoDinamicaDeDiamante(posicao, usuarioPatrocinado.getId_Codigo(), dataInicial, dataFinal, nivel + 1, malaDireta, result);

						} else {

							gerarMalaDiretaComCompressaoDinamicaDeDiamante(posicao, usuarioPatrocinado.getId_Codigo(), dataInicial, dataFinal, nivel, malaDireta, result);
						}

					} else {

						if (usuarioPatrocinado.isAtivo(dataInicial, dataFinal)) {

							gerarMalaDiretaComCompressaoDinamicaDeDiamante(posicao, usuarioPatrocinado.getId_Codigo(), dataInicial, dataFinal, nivel + 1, malaDireta, result);

						} else {

							gerarMalaDiretaComCompressaoDinamicaDeDiamante(posicao, usuarioPatrocinado.getId_Codigo(), dataInicial, dataFinal, nivel, malaDireta, result);
						}
					}
				}
			}
		}
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

	public void pesquisarMalaDiretaComRecursividade(Integer codigo, TreeMap<Integer, MalaDireta> malaDireta, int nivel, String posicao) {

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

	public BonificacaoIngressoAuxiliar verificaSeMetaDeDiamanteFoiAtingida(Usuario usuario, Result result, HibernateUtil hibernateUtil, Validator validator, Integer ano, Integer mes) {

		DateTime dataInicial = new DateTime(ano, mes, 1, 0, 0, 0);
		DateTime dataFinal = new DateTime(ano, mes, dataInicial.dayOfMonth().withMaximumValue().dayOfMonth().get(), 0, 0, 0);

		SessaoUsuario sessaoUsuario = new SessaoUsuario();
		sessaoUsuario.login(usuario);
		PontuacaoController pontuacaoController = new PontuacaoController(result, hibernateUtil, sessaoUsuario, validator);
		BonificacaoIngressoAuxiliar bonificacaoAuxiliar = pontuacaoController.gerarMalaDiretaECalcularPontuacaoDaRede(TODAS, sessaoUsuario.getUsuario().getId_Codigo(), dataInicial.toGregorianCalendar(), dataFinal.toGregorianCalendar(), PontuacaoController.TODOS, PontuacaoController.TODOS, sessaoUsuario.getUsuario().getId_Codigo());

		HashMap<Integer, MalaDireta> graduados = new HashMap<Integer, MalaDireta>();
		Integer quantidadeGraduados = calcularQuantidadeGraduados(usuario, result, hibernateUtil, validator, dataInicial, dataFinal, graduados);

		bonificacaoAuxiliar.setGraduados(graduados);
		bonificacaoAuxiliar.setQuantidadeGraduados(quantidadeGraduados);

		return bonificacaoAuxiliar;
	}

	public Integer calcularQuantidadeGraduados(Usuario usuario, Result result, HibernateUtil hibernateUtil, Validator validator, DateTime dataInicial, DateTime dataFinal, HashMap<Integer, MalaDireta> graduados) {

		TreeMap<Integer, MalaDireta> malaDiretaPrimeiroNivel = new TreeMap<Integer, MalaDireta>();

		encontrarMalaDiretaPrimeiroNivel(usuario, hibernateUtil, malaDiretaPrimeiroNivel, "id_Patroc");

		for (Entry<Integer, MalaDireta> primeiroNivel : malaDiretaPrimeiroNivel.entrySet()) {

			if (verificaSeEstaGraduado(primeiroNivel.getValue().getUsuario(), hibernateUtil, dataInicial.toGregorianCalendar(), dataFinal.toGregorianCalendar(), result, validator)) {

				graduados.put(primeiroNivel.getValue().getUsuario().getId_Codigo(), new MalaDireta(primeiroNivel.getValue().getUsuario(), 1));

			} else {

				encontrarGraduadosRecursivamente(primeiroNivel.getValue().getUsuario(), hibernateUtil, graduados, "id_Patroc", 0, dataInicial.toGregorianCalendar(), dataFinal.toGregorianCalendar(), result, validator);
			}
		}

		return graduados.size();
	}

	private boolean encontrarGraduadosRecursivamente(Usuario usuario, HibernateUtil hibernateUtil, HashMap<Integer, MalaDireta> graduados, String posicao, int nivel, GregorianCalendar dataInicial, GregorianCalendar dataFinal, Result result, Validator validator) {

		boolean encontrouGraduado = false;

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

				if (verificaSeEstaGraduado(usuarioPatrocinado, hibernateUtil, dataInicial, dataFinal, result, validator)) {

					graduados.put(usuarioPatrocinado.getId_Codigo(), new MalaDireta(usuarioPatrocinado, 0));
					encontrouGraduado = true;
					return encontrouGraduado;

				} else {

					 encontrouGraduado = encontrarGraduadosRecursivamente(usuarioPatrocinado, hibernateUtil, graduados, posicao, nivel + 1, dataInicial, dataFinal, result, validator);
					 
					 if(encontrouGraduado){
						 
						 return true;						 
					 }
				}
			}
		}

		return encontrouGraduado;
	}

	private boolean verificaSeEstaGraduado(Usuario usuario, HibernateUtil hibernateUtil, GregorianCalendar dataInicial, GregorianCalendar dataFinal, Result result, Validator validator) {

		if (usuario.isAtivo(dataInicial, dataFinal)) {

			SessaoUsuario sessaoUsuario = new SessaoUsuario();
			sessaoUsuario.login(usuario);

			PontuacaoController pontuacaoController = new PontuacaoController(result, hibernateUtil, sessaoUsuario, validator);
			BigDecimal pontuacao = pontuacaoController.gerarMalaDiretaECalcularPontuacaoDaRede(TODAS, sessaoUsuario.getUsuario().getId_Codigo(), dataInicial, dataFinal, PontuacaoController.TODOS, PontuacaoController.TODOS, sessaoUsuario.getUsuario().getId_Codigo()).getPontuacaoDiamante();

			if (pontuacao.compareTo(BonificacaoIngressoService.META_GRADUACAO) >= 0) {

				return true;
			}
		}

		return false;
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
		posicoes.put("id_GB", GERENTE_BRONZE);
		posicoes.put("id_GP", GERENTE_PRATA);
		posicoes.put("id_GO", GERENTE_OURO);
		posicoes.put("id_M", ESMERALDA);
		posicoes.put("id_LA", TOPÁZIO);
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