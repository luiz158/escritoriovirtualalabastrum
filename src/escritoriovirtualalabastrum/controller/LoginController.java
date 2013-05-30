package escritoriovirtualalabastrum.controller;

import org.hibernate.criterion.MatchMode;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.ValidationMessage;
import escritoriovirtualalabastrum.anotacoes.Public;
import escritoriovirtualalabastrum.hibernate.HibernateUtil;
import escritoriovirtualalabastrum.modelo.Usuario;
import escritoriovirtualalabastrum.sessao.SessaoUsuario;
import escritoriovirtualalabastrum.util.GeradorDeMd5;
import escritoriovirtualalabastrum.util.Util;

@Resource
public class LoginController {

	private final Result result;
	private SessaoUsuario sessaoUsuario;
	private Validator validator;
	private HibernateUtil hibernateUtil;

	public LoginController(Result result, SessaoUsuario sessaoUsuario, Validator validator, HibernateUtil hibernateUtil) {
		this.result = result;
		this.sessaoUsuario = sessaoUsuario;
		this.validator = validator;
		this.hibernateUtil = hibernateUtil;
	}

	@Public
	@Path("/")
	public void telaLogin() {

	}

	@Public
	public void efetuarLogin(Usuario usuario) {

		tentarEfetuarLogin(usuario);
		colocarUsuarioNaSessao(usuario);
		result.redirectTo(HomeController.class).home();
	}

	private void tentarEfetuarLogin(Usuario usuario) {

		if (Util.vazio(usuario.getId()) || usuario.getId().equals(0) || Util.vazio(usuario.getInformacoesFixasUsuario().getSenha())) {

			validator.add(new ValidationMessage("Login ou senha incorretos", "Erro"));
			validator.onErrorRedirectTo(this).telaLogin();
			return;
		}

		usuario.getInformacoesFixasUsuario().setSenha(GeradorDeMd5.converter(usuario.getInformacoesFixasUsuario().getSenha()));

		Usuario usuarioBanco = null;

		if (Util.preenchido(usuario.getId()) && usuario.getId() != 0) {

			Usuario usuarioFiltro = new Usuario();
			usuarioFiltro.setId(usuario.getId());

			usuarioBanco = hibernateUtil.selecionar(usuarioFiltro, MatchMode.EXACT);
		}

		if (Util.vazio(usuarioBanco) || !usuarioBanco.getInformacoesFixasUsuario().getSenha().equals(usuario.getInformacoesFixasUsuario().getSenha())) {

			validator.add(new ValidationMessage("Login ou senha incorretos", "Erro"));
			validator.onErrorRedirectTo(this).telaLogin();
			return;
		}

	}

	private void colocarUsuarioNaSessao(Usuario usuario) {

		usuario = (Usuario) this.hibernateUtil.selecionar(usuario, MatchMode.EXACT);

		usuario.setInformacoesFixasUsuario(usuario.getInformacoesFixasUsuario());

		this.sessaoUsuario.login(usuario);

	}

	@Public
	public void permissaoNegada() {

	}

	@Public
	public void logout() {

		sessaoUsuario.logout();

		result.redirectTo(this).telaLogin();
	}

	@Public
	public void trocarPropriaSenha() {

		verificarUsuarioLogado();
	}

	@Public
	public void salvarTrocarPropriaSenha(String senhaAntiga, String senhaNova) {

		if (!verificarUsuarioLogado()) {

			return;
		}

		Usuario usuario = hibernateUtil.selecionar(new Usuario(sessaoUsuario.getUsuario().getId()));

		if (!GeradorDeMd5.converter(senhaAntiga).equals(usuario.getInformacoesFixasUsuario().getSenha())) {

			validator.add(new ValidationMessage("Senha antiga incorreta", "Erro"));

			validator.onErrorRedirectTo(this).trocarPropriaSenha();
		}

		usuario.getInformacoesFixasUsuario().setSenha(GeradorDeMd5.converter(senhaNova));

		hibernateUtil.salvarOuAtualizar(usuario);

		result.include("sucesso", "Senha trocada com sucesso");

		result.redirectTo(HomeController.class).home();
	}

	private boolean verificarUsuarioLogado() {

		if (sessaoUsuario.getUsuario() == null) {

			result.redirectTo(this).telaLogin();
			return false;
		}

		return true;
	}
}
