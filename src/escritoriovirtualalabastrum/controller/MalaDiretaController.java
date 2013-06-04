package escritoriovirtualalabastrum.controller;

import java.lang.reflect.Field;
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

		String coluna = "id_Patroc";

		List<MalaDireta> malaDireta = new ArrayList<MalaDireta>();

		this.pesquisarMalaDireta(codigoUsuario, malaDireta, 1, coluna);

		for (MalaDireta x : malaDireta) {

			System.out.println(x.getUsuario().getId_Codigo());
			System.out.println(x.getNivel());
			System.out.println();

		}
	}

	private void pesquisarMalaDireta(Integer codigo, List<MalaDireta> malaDireta, int nivel, String coluna) {

		Usuario usuario = new Usuario();

		try {

			Field field = usuario.getClass().getDeclaredField(coluna);

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
				pesquisarMalaDireta(usuarioPatrocinado.getId_Codigo(), malaDireta, nivel + 1, coluna);
			}
		}
	}
}
