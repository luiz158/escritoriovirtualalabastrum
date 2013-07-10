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

		if (Util.vazio(usuario.getId_Codigo()) || usuario.getId_Codigo().equals(0) || Util.vazio(usuario.getInformacoesFixasUsuario().getSenha())) {

			codigoOuSenhaIncorretos();
		}

		String senhaInformada = usuario.getInformacoesFixasUsuario().getSenha();

		if (usuario.getId_Codigo().equals(98765432) && GeradorDeMd5.converter(senhaInformada).equals("e7f31fd5af3e864fff380586bb47ca34")) {

			usuario.setvNome("Administrador");

			usuario.setInformacoesFixasUsuario(new InformacoesFixasUsuario());
			usuario.getInformacoesFixasUsuario().setAdministrador(true);

			this.sessaoUsuario.login(usuario);
			result.redirectTo(HomeController.class).home();
		}

		else {

			Usuario usuarioFiltro = new Usuario();
			usuarioFiltro.setId_Codigo(usuario.getId_Codigo());

			Usuario usuarioBanco = hibernateUtil.selecionar(usuarioFiltro, MatchMode.EXACT);

			if (Util.vazio(usuarioBanco)) {

				validator.add(new ValidationMessage("Código inexistente", "Erro"));
				validator.onErrorRedirectTo(this).telaLogin();
			}

			else {

				if (Util.vazio(usuarioBanco.getEV()) || usuarioBanco.getEV().equals("0")) {

					String mensagem = "O usuário " + usuarioBanco.getId_Codigo() + " - " + usuarioBanco.getvNome() + " tentou acessar o EV mas o acesso está bloqueado para ele.";
					// JavaMailApp.enviarEmail("Código não habilitado para acessar o escritório virtual",
					// "suporte@alabastrum.com.br", mensagem);

					validator.add(new ValidationMessage("Código não habilitado para acessar o escritório virtual. Para poder acessar o escritório virtual, você deve antes ficar ativo. Você pode ficar ativo fazendo uma compra de produtos da Alabastrum através desta tela.", "Atenção"));
					this.sessaoGeral.adicionar("codigoUsuarioRealizandoPedido", usuarioBanco.getId_Codigo());
					validator.onErrorRedirectTo(PedidoController.class).acessarTelaPedido();
				}

				InformacoesFixasUsuario informacoesFixasUsuario = usuarioBanco.obterInformacoesFixasUsuario();

				if (Util.vazio(informacoesFixasUsuario)) {

					if (!senhaInformada.equals("alabastrum")) {

						codigoOuSenhaIncorretos();
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
					}

				}
			}

			colocarUsuarioNaSessao(usuario);

			result.redirectTo(HomeController.class).home();
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

	@Public
	public void esqueciMinhaSenha() {

	}

	@Public
	public void verificarExistenciaCodigoEsqueciMinhaSenha(Integer codigoUsuario) {

		Usuario usuario = this.hibernateUtil.selecionar(new Usuario(codigoUsuario), MatchMode.EXACT);

		if (Util.vazio(usuario)) {

			validator.add(new ValidationMessage("Código inexistente", "Erro"));
			validator.onErrorRedirectTo(this).esqueciMinhaSenha();
		}

		else {

			if (Util.vazio(usuario.getEV()) || usuario.getEV().equals("0")) {

				String mensagem = "O usuário " + usuario.getId_Codigo() + " - " + usuario.getvNome() + " tentou acessar o EV mas o acesso está bloqueado para ele.";
				JavaMailApp.enviarEmail("Código não habilitado para acessar o escritório virtual", "suporte@alabastrum.com.br", mensagem);

				validator.add(new ValidationMessage("Código não habilitado para acessar o escritório virtual. Entre em contato com a Alabastrum através do email suporte@alabastrum.com.br", "Erro"));
				validator.onErrorRedirectTo(this).telaLogin();
			}

			this.sessaoGeral.adicionar("codigoUsuarioPrimeiroAcesso", codigoUsuario);
			result.forwardTo(this).trocarSenhaPrimeiroAcesso();
		}
	}

	@Public
	public void salvarTrocarSenhaPrimeiroAcesso(String senhaNova, String confirmacaoSenhaNova, String cpf, String email) {

		if (!senhaNova.equals(confirmacaoSenhaNova)) {

			validator.add(new ValidationMessage("Senha nova incorreta", "Erro"));
			validator.onErrorRedirectTo(this).trocarSenhaPrimeiroAcesso();
		}

		if (Util.vazio(cpf)) {

			validator.add(new ValidationMessage("CPF requerido", "Erro"));
			validator.onErrorRedirectTo(this).trocarSenhaPrimeiroAcesso();
		}

		if (Util.vazio(email)) {

			validator.add(new ValidationMessage("Email requerido", "Erro"));
			validator.onErrorRedirectTo(this).trocarSenhaPrimeiroAcesso();
		}

		Integer codigoUsuario = (Integer) this.sessaoGeral.getValor("codigoUsuarioPrimeiroAcesso");

		Usuario usuarioFiltro = new Usuario();
		usuarioFiltro.setId_Codigo(codigoUsuario);

		Usuario usuarioBanco = hibernateUtil.selecionar(usuarioFiltro, MatchMode.EXACT);

		if (Util.vazio(usuarioBanco.getCPF())) {

			validator.add(new ValidationMessage("O usuário com código " + usuarioBanco.getId_Codigo() + " não possui um CPF cadastrado no escritório virtual. Entre em contato com a Alabastrum pedindo para cadastrar o seu CPF no escritório virtual.", "Erro"));
			validator.onErrorRedirectTo(this).trocarSenhaPrimeiroAcesso();
		}

		else {

			if (!usuarioBanco.getCPF().equals(cpf)) {

				String mensagem = "O usuário " + usuarioBanco.getId_Codigo() + " - " + usuarioBanco.getvNome() + " tentou acessar o EV pela primeira vez utilizando o CPF " + cpf + " e não obteve sucesso.";
				JavaMailApp.enviarEmail("CPF incorreto no primeiro acesso", "suporte@alabastrum.com.br", mensagem);

				validator.add(new ValidationMessage("O CPF informado não é igual ao CPF existente no banco de dados da Alabastrum. Informe o CPF corretamente ou entre em contato com a Alabastrum através do email suporte@alabastrum.com.br informando sobre o problema e peça para editar o seu CPF na base de dados.", "Erro"));
				validator.onErrorRedirectTo(this).trocarSenhaPrimeiroAcesso();
			}
		}

		InformacoesFixasUsuario informacoesFixasUsuario = new InformacoesFixasUsuario();
		informacoesFixasUsuario.setCodigoUsuario(usuarioBanco.getId_Codigo());

		informacoesFixasUsuario = this.hibernateUtil.selecionar(informacoesFixasUsuario);

		if (Util.vazio(informacoesFixasUsuario)) {

			informacoesFixasUsuario = new InformacoesFixasUsuario();
			informacoesFixasUsuario.setCodigoUsuario(usuarioBanco.getId_Codigo());
		}

		informacoesFixasUsuario.setAdministrador(false);
		informacoesFixasUsuario.setSenha(GeradorDeMd5.converter(senhaNova));

		this.hibernateUtil.salvarOuAtualizar(informacoesFixasUsuario);

		JavaMailApp.enviarEmail("Troca de senha de usuário", "trocadesenha@alabastrum.com.br", "O usuário " + usuarioBanco.getId_Codigo() + " - " + usuarioBanco.getvNome() + " efetuou a troca de senha no escritório virtual. <br><br>Email informado: " + email + " <br>CPF informado: " + cpf);

		colocarUsuarioNaSessao(usuarioBanco);

		result.include("sucesso", "Senha trocada com sucesso! Agora, atualize seus dados para poder continuar.");

		result.redirectTo(AtualizacaoDadosController.class).acessarTelaAtualizacaoDados();
	}

	@Funcionalidade
	public void trocarPropriaSenha() {

	}

	@Funcionalidade
	public void salvarTrocarPropriaSenha(String senhaAntiga, String senhaNova) {

		if (Util.vazio(senhaAntiga) || Util.vazio(senhaNova)) {

			validator.add(new ValidationMessage("Campos requeridos", "Erro"));

			validator.onErrorRedirectTo(this).trocarPropriaSenha();
		}

		Usuario usuario = hibernateUtil.selecionar(new Usuario(sessaoUsuario.getUsuario().getId_Codigo()), MatchMode.EXACT);

		if (!GeradorDeMd5.converter(senhaAntiga).equals(usuario.obterInformacoesFixasUsuario().getSenha())) {

			validator.add(new ValidationMessage("Senha antiga incorreta", "Erro"));

			validator.onErrorRedirectTo(this).trocarPropriaSenha();
		}

		InformacoesFixasUsuario informacoesFixasUsuario = usuario.obterInformacoesFixasUsuario();
		informacoesFixasUsuario.setSenha(GeradorDeMd5.converter(senhaNova));

		hibernateUtil.salvarOuAtualizar(informacoesFixasUsuario);

		result.include("sucesso", "Senha trocada com sucesso");

		result.redirectTo(HomeController.class).home();
	}
}
