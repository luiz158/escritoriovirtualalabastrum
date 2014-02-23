package escritoriovirtualalabastrum.controller;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import escritoriovirtualalabastrum.anotacoes.Funcionalidade;
import escritoriovirtualalabastrum.auxiliar.MalaDireta;
import escritoriovirtualalabastrum.hibernate.HibernateUtil;
import escritoriovirtualalabastrum.modelo.Usuario;
import escritoriovirtualalabastrum.service.MalaDiretaService;
import escritoriovirtualalabastrum.sessao.SessaoUsuario;
import escritoriovirtualalabastrum.util.Util;

@Resource
public class AniversariantesController {

	private Result result;
	private HibernateUtil hibernateUtil;
	private SessaoUsuario sessaoUsuario;

	public AniversariantesController(Result result, HibernateUtil hibernateUtil, SessaoUsuario sessaoUsuario) {

		this.result = result;
		this.hibernateUtil = hibernateUtil;
		this.sessaoUsuario = sessaoUsuario;
	}

	@Funcionalidade
	public void acessarTelaAniversariantes() {

	}

	@Funcionalidade
	public void gerarAniversariantes(String mes) {

		mes = "/" + mes + "/";

		Integer codigoUsuario = this.sessaoUsuario.getUsuario().getId_Codigo();

		TreeMap<Integer, MalaDireta> malaDireta = new TreeMap<Integer, MalaDireta>();

		gerarMalaDiretaTodasPosicoesFiltrandoPorMes(codigoUsuario, malaDireta, mes);

		List<MalaDireta> aniversariantes = new ArrayList<MalaDireta>();

		for (Entry<Integer, MalaDireta> usuario : malaDireta.entrySet()) {

			aniversariantes.add(usuario.getValue());
		}

		ordenarPorData(aniversariantes);

		result.include("aniversariantes", aniversariantes);
	}

	private void ordenarPorData(List<MalaDireta> aniversariantes) {

		if (Util.preenchido(aniversariantes)) {

			Collections.sort(aniversariantes, new Comparator<MalaDireta>() {

				public int compare(MalaDireta obj1, MalaDireta obj2) {

					String data1 = obj1.getUsuario().getDt_Nasc();
					String data2 = obj2.getUsuario().getDt_Nasc();

					return data1.compareTo(data2);
				}
			});
		}
	}

	private void gerarMalaDiretaTodasPosicoesFiltrandoPorMes(Integer codigoUsuario, TreeMap<Integer, MalaDireta> malaDireta, String mes) {

		LinkedHashMap<String, String> posicoes = new MalaDiretaService().obterPosicoes();

		for (Entry<String, String> posicao : posicoes.entrySet()) {

			if (!posicao.getKey().equals("Todas")) {

				this.pesquisarMalaDiretaComRecursividade(codigoUsuario, malaDireta, 1, posicao.getKey(), mes);
			}
		}
	}

	private void pesquisarMalaDiretaComRecursividade(Integer codigo, TreeMap<Integer, MalaDireta> malaDireta, int nivel, String posicao, String mes) {

		Usuario usuario = new Usuario();
		usuario.setDt_Nasc(mes);

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

					pesquisarMalaDiretaComRecursividade(usuarioPatrocinado.getId_Codigo(), malaDireta, nivel + 1, "id_Patroc", mes);
				}
			}
		}
	}
}
