package escritoriovirtualalabastrum.interceptor;

import java.lang.reflect.Method;
import java.util.Arrays;

import br.com.caelum.vraptor.InterceptionException;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.interceptor.Interceptor;
import br.com.caelum.vraptor.resource.ResourceMethod;
import br.com.caelum.vraptor.validator.ValidationMessage;
import escritoriovirtualalabastrum.anotacoes.Funcionalidade;
import escritoriovirtualalabastrum.anotacoes.Public;
import escritoriovirtualalabastrum.controller.LoginController;
import escritoriovirtualalabastrum.modelo.Usuario;
import escritoriovirtualalabastrum.sessao.SessaoUsuario;
import escritoriovirtualalabastrum.util.Util;

@Intercepts
public class InterceptadorDeAutorizacao implements Interceptor {

	private SessaoUsuario sessaoUsuario;
	private Result result;

	public InterceptadorDeAutorizacao(SessaoUsuario sessaoUsuario, Result result) {

		this.sessaoUsuario = sessaoUsuario;
		this.result = result;
	}

	public boolean accepts(ResourceMethod method) {

		boolean contemAnotacaoPublic = !method.containsAnnotation(Public.class);

		return false;
	}

	public void intercept(InterceptorStack stack, ResourceMethod method, Object resourceInstance) throws InterceptionException {

		if (Util.vazio(sessaoUsuario.getUsuario())) {

			usuarioNaoLogadoNoSistema();
		}

		else {

			if (possuiPermissao(stack, method, resourceInstance, sessaoUsuario.getUsuario())) {

				stack.next(method, resourceInstance);
			}

			else {

				permissaoNegada();
				return;
			}
		}
	}

	private boolean possuiPermissao(InterceptorStack stack, ResourceMethod method, Object resourceInstance, Usuario usuario) {

		Method metodo = method.getMethod();

		if (metodo.isAnnotationPresent(Funcionalidade.class)) {

			Funcionalidade anotacao = metodo.getAnnotation(Funcionalidade.class);

			if (Util.preenchido(anotacao.administrativa())) {

				if (usuario.obterInformacoesFixasUsuario().getAdministrador()) {

					return true;
				}

				else {

					return false;
				}
			}

			else
				return true;
		}

		return false;
	}

	private void usuarioNaoLogadoNoSistema() {

		result.include("errors", Arrays.asList(new ValidationMessage("O usuario não está logado no sistema", "Erro")));
		result.redirectTo(LoginController.class).telaLogin();
	}

	private void permissaoNegada() {

		result.redirectTo(LoginController.class).permissaoNegada();
	}
}
