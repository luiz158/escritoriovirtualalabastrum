package escritoriovirtualalabastrum.controller;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import escritoriovirtualalabastrum.auxiliar.MalaDireta;
import escritoriovirtualalabastrum.hibernate.HibernateUtil;
import escritoriovirtualalabastrum.modelo.Usuario;

@Resource
public class MalaDiretaController {

	private Result result;
	private Validator validator;
	private HibernateUtil hibernateUtil;

	public MalaDiretaController(Result result, Validator validator, HibernateUtil hibernateUtil) {

		this.result = result;
		this.validator = validator;
		this.hibernateUtil = hibernateUtil;
	}

	public void acessarTelaMalaDireta() {

	}

	public void gerarMalaDireta() {

		Integer codigoUsuario = 20;

		List<MalaDireta> malaDireta = new ArrayList<MalaDireta>();

		this.pesquisarMalaDireta(codigoUsuario, malaDireta, 1);
	}

	private void pesquisarMalaDireta(Integer codigo, List<MalaDireta> malaDireta, int nivel) {

		Usuario usuario = new Usuario();
		usuario.setId_Patroc(codigo);

		List<Usuario> usuariosPatrocinados = hibernateUtil.buscar(usuario);

		for (Usuario usuarioPatrocinado : usuariosPatrocinados) {

			if (!codigo.equals(usuarioPatrocinado.getId_Codigo())) {

				malaDireta.add(new MalaDireta(usuarioPatrocinado, nivel));
				pesquisarMalaDireta(usuarioPatrocinado.getId_Codigo(), malaDireta, nivel + 1);
			}
		}
	}
}
