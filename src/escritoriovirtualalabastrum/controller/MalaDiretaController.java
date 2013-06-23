package escritoriovirtualalabastrum.controller;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.ValidationMessage;
import escritoriovirtualalabastrum.anotacoes.Funcionalidade;
import escritoriovirtualalabastrum.auxiliar.MalaDireta;
import escritoriovirtualalabastrum.hibernate.HibernateUtil;
import escritoriovirtualalabastrum.modelo.Usuario;
import escritoriovirtualalabastrum.sessao.SessaoUsuario;
import escritoriovirtualalabastrum.util.Util;

@Resource
public class MalaDiretaController {

	private Result result;
	private HibernateUtil hibernateUtil;
	private SessaoUsuario sessaoUsuario;
	private Validator validator;

	public MalaDiretaController(Result result, HibernateUtil hibernateUtil, SessaoUsuario sessaoUsuario, Validator validator) {

		this.result = result;
		this.hibernateUtil = hibernateUtil;
		this.sessaoUsuario = sessaoUsuario;
		this.validator = validator;
	}

	@Funcionalidade
	public void acessarTelaMalaDireta() {

		result.include("posicoes", obterPosicoes());
	}

	@Funcionalidade
	public void gerarMalaDireta(String posicao, Integer codigoUsuario) {

		Integer codigoUsuarioLogado = this.sessaoUsuario.getUsuario().getId_Codigo();

		if (Util.vazio(codigoUsuario)) {

			codigoUsuario = codigoUsuarioLogado;
		}

		if (codigoUsuario.equals(codigoUsuarioLogado)) {

			gerarMalaDiretaDeAcordoComFiltros(posicao, codigoUsuario);
		}

		else {

			TreeMap<Integer, MalaDireta> malaDiretaUsuarioLogado = new TreeMap<Integer, MalaDireta>();

			gerarMalaDiretaTodasPosicoes(codigoUsuarioLogado, malaDiretaUsuarioLogado);

			if (!malaDiretaUsuarioLogado.containsKey(codigoUsuario)) {

				validator.add(new ValidationMessage("O código informado não está na sua mala direta", "Erro"));
				validator.onErrorRedirectTo(this).acessarTelaMalaDireta();
				return;
			}

			else {

				gerarMalaDiretaDeAcordoComFiltros(posicao, codigoUsuario);
			}
		}

		Usuario usuarioPesquisado = new Usuario();
		usuarioPesquisado.setId_Codigo(codigoUsuario);

		usuarioPesquisado = this.hibernateUtil.selecionar(usuarioPesquisado);

		result.include("usuarioPesquisado", usuarioPesquisado);
		result.include("posicaoConsiderada", obterPosicoes().get(posicao));
	}

	private void gerarMalaDiretaDeAcordoComFiltros(String posicao, Integer codigoUsuario) {

		TreeMap<Integer, MalaDireta> malaDireta = new TreeMap<Integer, MalaDireta>();

		if (posicao.equals("id_Patroc")) {

			this.pesquisarMalaDiretaSemRecursividade(codigoUsuario, malaDireta, 1, posicao);
		}

		else if (posicao.equals("Todas")) {

			gerarMalaDiretaTodasPosicoes(codigoUsuario, malaDireta);
		}

		else {

			this.pesquisarMalaDireta(codigoUsuario, malaDireta, 1, posicao);
		}

		result.include("malaDireta", malaDireta.values());
		result.include("quantidadeElementosMalaDireta", malaDireta.values().size());
	}

	private void gerarMalaDiretaTodasPosicoes(Integer codigoUsuario, TreeMap<Integer, MalaDireta> malaDireta) {

		LinkedHashMap<String, String> posicoes = obterPosicoes();

		for (Entry<String, String> posicao : posicoes.entrySet()) {

			if (!posicao.getKey().equals("Todas")) {

				if (posicao.getKey().equals("id_Patroc")) {

					this.pesquisarMalaDiretaSemRecursividade(codigoUsuario, malaDireta, 1, posicao.getKey());
				}

				else {

					this.pesquisarMalaDireta(codigoUsuario, malaDireta, 1, posicao.getKey());
				}
			}
		}
	}

	private void pesquisarMalaDireta(Integer codigo, TreeMap<Integer, MalaDireta> malaDireta, int nivel, String posicao) {

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

					pesquisarMalaDireta(usuarioPatrocinado.getId_Codigo(), malaDireta, nivel + 1, "id_Patroc");
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
