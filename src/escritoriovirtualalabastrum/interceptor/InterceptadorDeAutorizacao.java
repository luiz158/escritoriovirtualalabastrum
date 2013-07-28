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

		return contemAnotacaoPublic;
	}

	public void intercept(InterceptorStack stack, ResourceMethod method, Object resourceInstance) throws InterceptionException {

		if (Util.vazio(sessaoUsuario.getUsuario())) {

			usuarioNaoLogadoNoSistema();
		}

		else {

			if (possuiPermissao(stack, method, resourceInstance, sessaoUsuario.getUsuario())) {
				
				validarQualificacao();

				stack.next(method, resourceInstance);
			}

			else {

				permissaoNegada();
				return;
			}
		}
	}
	
	private void validarQualificacao() {

		Usuario usuarioLogado = this.sessaoUsuario.getUsuario();

		if (Util.preenchido(usuarioLogado.getDesquaLider()) && usuarioLogado.getDesquaLider().equals("1")) {

			result.include("alerta", "Atenção - Em virtude de sua inatividade há 2 meses ou mais, sua rede foi transferida para o 1º gerente ATIVO da sua linha ascendente. <br>Procure seu líder e saiba o que precisa ser feito para recuperar sua rede.");
		}
	}

	private boolean possuiPermissao(InterceptorStack stack, ResourceMethod method, Object resourceInstance, Usuario usuario) {

		Method metodo = method.getMethod();

		if (metodo.isAnnotationPresent(Funcionalidade.class)) {

			Funcionalidade anotacao = metodo.getAnnotation(Funcionalidade.class);

			if (Util.preenchido(anotacao.administrativa())) {

				if (Util.preenchido(usuario.obterInformacoesFixasUsuario()) && usuario.obterInformacoesFixasUsuario().getAdministrador()) {

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
