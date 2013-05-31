package escritoriovirtualalabastrum.controller;

import org.hibernate.criterion.MatchMode;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.ValidationMessage;
import escritoriovirtualalabastrum.anotacoes.Funcionalidade;
import escritoriovirtualalabastrum.anotacoes.Public;
import escritoriovirtualalabastrum.hibernate.HibernateUtil;
import escritoriovirtualalabastrum.modelo.InformacoesFixasUsuario;
import escritoriovirtualalabastrum.modelo.Usuario;
import escritoriovirtualalabastrum.sessao.SessaoGeral;
import escritoriovirtualalabastrum.sessao.SessaoUsuario;
import escritoriovirtualalabastrum.util.GeradorDeMd5;
import escritoriovirtualalabastrum.util.JavaMailApp;
import escritoriovirtualalabastrum.util.Util;

@Resource
public class LoginController {

	private final Result result;
	private SessaoUsuario sessaoUsuario;
	private SessaoGeral sessaoGeral;
	private Validator validator;
	private HibernateUtil hibernateUtil;

	public LoginController(Result result, SessaoUsuario sessaoUsuario, SessaoGeral sessaoGeral, Validator validator, HibernateUtil hibernateUtil) {
		this.result = result;
		this.sessaoUsuario = sessaoUsuario;
		this.sessaoGeral = sessaoGeral;
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

		if (Util.vazio(usuario.getId_Codigo()) || usuario.getId_Codigo().equals("0") || Util.vazio(usuario.getInformacoesFixasUsuario().getSenha())) {

			codigoOuSenhaIncorretos();
			return;
		}

		String senhaInformada = usuario.getInformacoesFixasUsuario().getSenha();

		Usuario usuarioFiltro = new Usuario();
		usuarioFiltro.setId_Codigo(usuario.getId_Codigo());

		Usuario usuarioBanco = hibernateUtil.selecionar(usuarioFiltro, MatchMode.EXACT);

		if (Util.vazio(usuarioBanco)) {

			validator.add(new ValidationMessage("Código inexistente", "Erro"));
			validator.onErrorRedirectTo(this).telaLogin();
			return;
		}

		else {

			if (Util.vazio(usuarioBanco.getEV()) || usuarioBanco.getEV().equals("0")) {

				validator.add(new ValidationMessage("Código não habilitado para acessar o escritório virtual", "Erro"));
				validator.onErrorRedirectTo(this).telaLogin();
				return;
			}

			InformacoesFixasUsuario informacoesFixasUsuario = usuarioBanco.obterInformacoesFixasUsuario();

			if (Util.vazio(informacoesFixasUsuario)) {

				if (!senhaInformada.equals("alabastrum")) {

					codigoOuSenhaIncorretos();
					return;
				}

				else {

					this.sessaoGeral.adicionar("codigoUsuarioPrimeiroAcesso", usuarioBanco.getId_Codigo());
					result.forwardTo(this).trocarSenhaPrimeiroAcesso();
					return;
				}
			}

			else {

				if (!informacoesFixasUsuario.getSenha().equals(GeradorDeMd5.converter(senhaInformada))) {

					codigoOuSenhaIncorretos();
					return;
				}

			}
		}
	}

	private void codigoOuSenhaIncorretos() {

		validator.add(new ValidationMessage("Código ou senha incorretos", "Erro"));
		validator.onErrorRedirectTo(this).telaLogin();
	}

	private void colocarUsuarioNaSessao(Usuario usuario) {

		Usuario usuarioFiltro = new Usuario();
		usuarioFiltro.setId_Codigo(usuario.getId_Codigo());

		usuario = (Usuario) this.hibernateUtil.selecionar(usuarioFiltro, MatchMode.EXACT);

		usuario.setInformacoesFixasUsuario(usuario.obterInformacoesFixasUsuario());

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
	public void trocarSenhaPrimeiroAcesso() {

	}

	@Funcionalidade
	public void salvarTrocarSenhaPrimeiroAcesso(String senhaNova, String confirmacaoSenhaNova, String cpf, String email) {

		if (!senhaNova.equals(confirmacaoSenhaNova)) {

			validator.add(new ValidationMessage("Senha nova incorreta", "Erro"));
			validator.onErrorRedirectTo(this).trocarSenhaPrimeiroAcesso();
			return;
		}

		if (Util.vazio(cpf)) {

			validator.add(new ValidationMessage("CPF requerido", "Erro"));
			validator.onErrorRedirectTo(this).trocarSenhaPrimeiroAcesso();
			return;
		}

		if (Util.vazio(email)) {

			validator.add(new ValidationMessage("Email requerido", "Erro"));
			validator.onErrorRedirectTo(this).trocarSenhaPrimeiroAcesso();
			return;
		}

		String codigoUsuario = (String) this.sessaoGeral.getValor("codigoUsuarioPrimeiroAcesso");

		Usuario usuarioFiltro = new Usuario();
		usuarioFiltro.setId_Codigo(codigoUsuario);

		Usuario usuarioBanco = hibernateUtil.selecionar(usuarioFiltro, MatchMode.EXACT);

		if (Util.vazio(usuarioBanco.getCPF())) {

			validator.add(new ValidationMessage("O usuário com código " + usuarioBanco.getId_Codigo() + " não possui um CPF cadastrado no escritório virtual. Entre em contato com a Alabastrum pedindo para cadastrar o seu CPF no escritório virtual.", "Erro"));
			validator.onErrorRedirectTo(this).trocarSenhaPrimeiroAcesso();
			return;
		}

		else {

			if (!usuarioBanco.getCPF().equals(cpf)) {

				validator.add(new ValidationMessage("O CPF informado não é igual ao CPF existente no banco de dados da Alabastrum. Informe o CPF corretamente ou entre em contato com a Alabastrum informando sobre o problema e peça para editar o seu CPF na base de dados.", "Erro"));
				validator.onErrorRedirectTo(this).trocarSenhaPrimeiroAcesso();
				return;
			}
		}

		InformacoesFixasUsuario informacoesFixasUsuario = new InformacoesFixasUsuario();
		informacoesFixasUsuario.setCodigoUsuario(usuarioBanco.getId_Codigo());
		informacoesFixasUsuario.setAdministrador(false);
		informacoesFixasUsuario.setSenha(GeradorDeMd5.converter(senhaNova));

		this.hibernateUtil.salvarOuAtualizar(informacoesFixasUsuario);

		JavaMailApp.enviarEmail("Troca de senha de usuário", "O usuário com código " + usuarioBanco.getId_Codigo() + " efetuou a troca de senha no escritório virtual. \n\nSenha informada: " + senhaNova + " \nEmail informado: " + email + " \nCPF informado: " + cpf);

		result.redirectTo(this).telaLogin();
	}

	@Funcionalidade
	public void trocarPropriaSenha() {

	}

	@Funcionalidade
	public void salvarTrocarPropriaSenha(String senhaAntiga, String senhaNova) {

		Usuario usuario = hibernateUtil.selecionar(new Usuario(sessaoUsuario.getUsuario().getId()));

		if (!GeradorDeMd5.converter(senhaAntiga).equals(usuario.obterInformacoesFixasUsuario().getSenha())) {

			validator.add(new ValidationMessage("Senha antiga incorreta", "Erro"));

			validator.onErrorRedirectTo(this).trocarPropriaSenha();
		}

		usuario.obterInformacoesFixasUsuario().setSenha(GeradorDeMd5.converter(senhaNova));

		hibernateUtil.salvarOuAtualizar(usuario);

		result.include("sucesso", "Senha trocada com sucesso");

		result.redirectTo(HomeController.class).home();
	}
}
