package escritoriovirtualalabastrum.controller;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
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

		List<String> posicoes = obterPosicoes();

		for (String posicaoLoop : posicoes) {

			if (posicaoLoop.equals("id_Patroc")) {

				this.pesquisarMalaDiretaSemRecursividade(codigoUsuario, malaDireta, 1, posicaoLoop);
			}

			else {

				this.pesquisarMalaDireta(codigoUsuario, malaDireta, 1, posicaoLoop);
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

	private List<String> obterPosicoes() {

		List<String> posicoes = new ArrayList<String>();

		posicoes.add("id_Patroc");
		posicoes.add("id_Dem");
		posicoes.add("id_S");
		posicoes.add("id_M");
		posicoes.add("id_M1");
		posicoes.add("id_M2");
		posicoes.add("id_M3");
		posicoes.add("id_M4");
		posicoes.add("id_M5");
		posicoes.add("id_GB");
		posicoes.add("id_GP");
		posicoes.add("id_GO");
		posicoes.add("id_GE");
		posicoes.add("id_LA");
		posicoes.add("id_CR");
		posicoes.add("id_DR");
		posicoes.add("id_DD");
		posicoes.add("id_Pres");

		return posicoes;
	}

}
