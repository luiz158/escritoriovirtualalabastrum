package escritoriovirtualalabastrum.controller;

import org.hibernate.criterion.MatchMode;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import escritoriovirtualalabastrum.anotacoes.Public;
import escritoriovirtualalabastrum.hibernate.HibernateUtil;
import escritoriovirtualalabastrum.modelo.InformacoesFixasUsuario;
import escritoriovirtualalabastrum.modelo.Usuario;
import escritoriovirtualalabastrum.util.Util;

@Resource
public class LoginAPIController {

	private Result result;
	private HibernateUtil hibernateUtil;

	public LoginAPIController(Result result, HibernateUtil hibernateUtil) {

		this.result = result;
		this.hibernateUtil = hibernateUtil;
	}

	@Public
	@Path("/loginAPI/autenticar")
	public void autenticar(String codigo, String senha) {

		try {

			Usuario usuarioFiltro = new Usuario();
			usuarioFiltro.setId_Codigo(Integer.valueOf(codigo));

			Usuario usuarioBanco = hibernateUtil.selecionar(usuarioFiltro, MatchMode.EXACT);

			if (Util.vazio(usuarioBanco)) {

				forbidden();
				return;
			}

			InformacoesFixasUsuario informacoesFixasUsuario = usuarioBanco.obterInformacoesFixasUsuario();

			if (!informacoesFixasUsuario.getSenha().equals(senha)) {

				forbidden();
				return;
			}

			result.use(Results.json()).from(usuarioBanco).serialize();

		} catch (Exception e) {

			forbidden();
			return;
		}
	}

	private void forbidden() {

		result.use(Results.http()).sendError(403, "Codigo ou senha incorretos");
	}
}