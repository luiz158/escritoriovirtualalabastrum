package escritoriovirtualalabastrum.controller;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import escritoriovirtualalabastrum.anotacoes.Funcionalidade;
import escritoriovirtualalabastrum.auxiliar.MalaDireta;
import escritoriovirtualalabastrum.hibernate.HibernateUtil;
import escritoriovirtualalabastrum.modelo.Usuario;
import escritoriovirtualalabastrum.sessao.SessaoUsuario;

@Resource
public class MalaDiretaController {

	private Result result;
	private HibernateUtil hibernateUtil;
	private SessaoUsuario sessaoUsuario;

	public MalaDiretaController(Result result, HibernateUtil hibernateUtil, SessaoUsuario sessaoUsuario) {

		this.result = result;
		this.hibernateUtil = hibernateUtil;
		this.sessaoUsuario = sessaoUsuario;
	}

	@Funcionalidade
	public void acessarTelaMalaDireta() {

	}

	@Funcionalidade
	public void gerarMalaDireta(String posicao) {

		//Integer codigoUsuario = this.sessaoUsuario.getUsuario().getId_Codigo();
		Integer codigoUsuario = 1;

		List<MalaDireta> malaDireta = new ArrayList<MalaDireta>();

		this.pesquisarMalaDireta(codigoUsuario, malaDireta, 1, posicao);

		result.include("malaDireta", malaDireta);
	}

	private void pesquisarMalaDireta(Integer codigo, List<MalaDireta> malaDireta, int nivel, String posicao) {

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

				malaDireta.add(new MalaDireta(usuarioPatrocinado, nivel));
				pesquisarMalaDireta(usuarioPatrocinado.getId_Codigo(), malaDireta, nivel + 1, posicao);
			}
		}
	}
}
