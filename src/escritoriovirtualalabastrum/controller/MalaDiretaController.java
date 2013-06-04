package escritoriovirtualalabastrum.controller;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
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

		Integer codigoUsuario = 1;

		List<Usuario> malaDireta = new ArrayList<Usuario>();

		this.pesquisarMalaDireta(codigoUsuario, malaDireta);
	}

	public void pesquisarMalaDireta(Integer codigo, List<Usuario> malaDireta) {

		Usuario usuario = new Usuario();
		usuario.setId_Patroc(codigo);

		List<Usuario> usuariosPatrocinados = hibernateUtil.buscar(usuario);

		for (Usuario usuarioPatrocinado : usuariosPatrocinados) {

			if (!codigo.equals(usuarioPatrocinado.getId_Codigo())) {

				malaDireta.add(usuarioPatrocinado);
				pesquisarMalaDireta(usuarioPatrocinado.getId_Codigo(), malaDireta);
			}
		}
	}
}
