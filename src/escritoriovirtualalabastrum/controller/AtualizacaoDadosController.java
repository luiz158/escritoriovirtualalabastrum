package escritoriovirtualalabastrum.controller;

import java.lang.reflect.Field;

import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import escritoriovirtualalabastrum.anotacoes.Funcionalidade;
import escritoriovirtualalabastrum.hibernate.HibernateUtil;
import escritoriovirtualalabastrum.modelo.Usuario;
import escritoriovirtualalabastrum.sessao.SessaoAtualizacaoDados;
import escritoriovirtualalabastrum.sessao.SessaoUsuario;
import escritoriovirtualalabastrum.util.JavaMailApp;
import escritoriovirtualalabastrum.util.Util;

@Resource
public class AtualizacaoDadosController {

	private Result result;
	private SessaoAtualizacaoDados sessaoAtualizacaoDados;
	private SessaoUsuario sessaoUsuario;
	private HibernateUtil hibernateUtil;

	public AtualizacaoDadosController(Result result, SessaoAtualizacaoDados sessaoAtualizacaoDados, SessaoUsuario sessaoUsuario, HibernateUtil hibernateUtil) {

		this.result = result;
		this.sessaoAtualizacaoDados = sessaoAtualizacaoDados;
		this.sessaoUsuario = sessaoUsuario;
		this.hibernateUtil = hibernateUtil;
	}

	@Funcionalidade
	public void acessarTelaAtualizacaoDados() {

		Usuario usuario = this.hibernateUtil.selecionar(new Usuario(this.sessaoUsuario.getUsuario().getId()));

		Field[] fields = usuario.getClass().getDeclaredFields();

		for (Field field : fields) {

			try {

				field.setAccessible(true);

				Field fieldByName = this.sessaoAtualizacaoDados.getClass().getDeclaredField(field.getName());

				fieldByName.setAccessible(true);

				fieldByName.set(this.sessaoAtualizacaoDados, field.get(usuario));
			}

			catch (Exception e) {
			}
		}

		if (Util.preenchido(this.sessaoAtualizacaoDados.getDt_Nasc())) {

			this.sessaoAtualizacaoDados.setDt_Nasc(this.sessaoAtualizacaoDados.getDt_Nasc().substring(0, 10));
		}

		if (Util.preenchido(this.sessaoAtualizacaoDados.getDt_NascTitular2())) {

			this.sessaoAtualizacaoDados.setDt_NascTitular2(this.sessaoAtualizacaoDados.getDt_NascTitular2().substring(0, 10));
		}

	}

	@Funcionalidade
	public void salvarAtualizacaoDados(SessaoAtualizacaoDados sessaoAtualizacaoDados) {

		this.sessaoAtualizacaoDados = sessaoAtualizacaoDados;

		Integer codigoUsuario = this.sessaoUsuario.getUsuario().getId_Codigo();

		String textoEmail = "O usuário com código " + codigoUsuario + " atualizou seus dados no escritório virtual<br><br>";

		textoEmail += "Informações:<br>";

		textoEmail += "<br> <b>Nome: </b> " + this.sessaoAtualizacaoDados.getvNome();
		textoEmail += "<br> <b>Data de nascimento: </b> " + this.sessaoAtualizacaoDados.getDt_Nasc();
		textoEmail += "<br> <b> Sexo: </b> " + this.sessaoAtualizacaoDados.getCadSexo();
		textoEmail += "<br> <b> Estado civil: </b> " + this.sessaoAtualizacaoDados.getCadEstCivil();
		textoEmail += "<br> <b> CEP: </b> " + this.sessaoAtualizacaoDados.getCadCEP();
		textoEmail += "<br> <b> Endereço: </b> " + this.sessaoAtualizacaoDados.getCadEndereco();
		textoEmail += "<br> <b> Bairro: </b> " + this.sessaoAtualizacaoDados.getCadBairro();
		textoEmail += "<br> <b> Cidade: </b> " + this.sessaoAtualizacaoDados.getCadCidade();
		textoEmail += "<br> <b> Estado: </b> " + this.sessaoAtualizacaoDados.getCadUF();
		textoEmail += "<br> <b> Telefone residencial: </b> " + this.sessaoAtualizacaoDados.getTel();
		textoEmail += "<br> <b> Telefone celular: </b> " + this.sessaoAtualizacaoDados.getCadCelular();
		textoEmail += "<br> <b> Email: </b> " + this.sessaoAtualizacaoDados.geteMail();
		textoEmail += "<br><br><br> <b> Dados do segundo titular: </b> <br>";
		textoEmail += "<br> <b> Nome: </b> " + this.sessaoAtualizacaoDados.getvNomeTitular2();
		textoEmail += "<br> <b> Data de nascimento: </b> " + this.sessaoAtualizacaoDados.getDt_NascTitular2();
		textoEmail += "<br> <b> Sexo: </b> " + this.sessaoAtualizacaoDados.getSexoTitular2();
		textoEmail += "<br> <b> Estado civil: </b> " + this.sessaoAtualizacaoDados.getEstCivilTitular2();
		textoEmail += "<br> <b> CPF: </b> " + this.sessaoAtualizacaoDados.getCPFTitular2();
		textoEmail += "<br> <b> RG: </b> " + this.sessaoAtualizacaoDados.getRGTitular2();
		textoEmail += "<br> <b> Emissor: </b> " + this.sessaoAtualizacaoDados.getEmissorTitular2();
		textoEmail += "<br> <b> Email: </b> " + this.sessaoAtualizacaoDados.geteMailTitular2();
		textoEmail += "<br><br><br> <b> Dados bancários: </b> <br>";
		textoEmail += "<br> <b> Banco: </b> " + this.sessaoAtualizacaoDados.getCadBanco();
		textoEmail += "<br> <b> Tipo da conta: </b> " + this.sessaoAtualizacaoDados.getCadTipoConta();
		textoEmail += "<br> <b> Número da agência: </b> " + this.sessaoAtualizacaoDados.getCadAgencia();
		textoEmail += "<br> <b> Número da conta: </b> " + this.sessaoAtualizacaoDados.getCadCCorrente();

		JavaMailApp.enviarEmail("Atualização de dados de usuário", "atualizacaocadastro@alabastrum.com.br", textoEmail);

		result.include("sucesso", "Foi enviado um email para a Alabastrum solicitando a atualização dos seus dados cadastrais. Você receberá um email assim que a atualização for concluída.");

		result.redirectTo(HomeController.class).home();

	}
}
