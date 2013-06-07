package escritoriovirtualalabastrum.controller;

import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import escritoriovirtualalabastrum.anotacoes.Funcionalidade;
import escritoriovirtualalabastrum.sessao.SessaoAtualizacaoDados;
import escritoriovirtualalabastrum.sessao.SessaoUsuario;
import escritoriovirtualalabastrum.util.JavaMailApp;

@Resource
public class AtualizacaoDadosController {

	private Result result;
	private SessaoAtualizacaoDados sessaoAtualizacaoDados;
	private SessaoUsuario sessaoUsuario;

	public AtualizacaoDadosController(Result result, SessaoAtualizacaoDados sessaoAtualizacaoDados, SessaoUsuario sessaoUsuario) {

		this.result = result;
		this.sessaoAtualizacaoDados = sessaoAtualizacaoDados;
		this.sessaoUsuario = sessaoUsuario;
	}

	@Funcionalidade
	public void acessarTelaAtualizacaoDados() {

		this.sessaoAtualizacaoDados = new SessaoAtualizacaoDados();
	}

	@Funcionalidade
	public void salvarAtualizacaoDados(SessaoAtualizacaoDados sessaoAtualizacaoDados) {

		this.sessaoAtualizacaoDados = sessaoAtualizacaoDados;

		Integer codigoUsuario = this.sessaoUsuario.getUsuario().getId_Codigo();

		String textoEmail = "O usuário com código " + codigoUsuario + " atualizou seus dados no escritório virtual<br><br>";

		textoEmail += "Informações:<br>";

		textoEmail += "<br> <b>Data de nascimento: </b> " + this.sessaoAtualizacaoDados.getDt_Nasc();
		textoEmail += "<br> <b> Sexo: </b> " + this.sessaoAtualizacaoDados.getCadSexo();
		textoEmail += "<br> <b> Estado civil: </b> " + this.sessaoAtualizacaoDados.getCadEstCivil();
		textoEmail += "<br> <b> CEP: </b> " + this.sessaoAtualizacaoDados.getCadCEP();
		textoEmail += "<br> <b> Endereço: </b> " + this.sessaoAtualizacaoDados.getCadEndereco();
		textoEmail += "<br> <b> Bairro: </b> " + this.sessaoAtualizacaoDados.getCadBairro();
		textoEmail += "<br> <b> Cidade: </b> " + this.sessaoAtualizacaoDados.getCadCidade();
		textoEmail += "<br> <b> Estado: </b> " + this.sessaoAtualizacaoDados.getCadUF();
		textoEmail += "<br> <b> Telefone residencial: </b> " + this.sessaoAtualizacaoDados.getTel();
		textoEmail += "<br> <b> Telefone celular 1: </b> " + this.sessaoAtualizacaoDados.getCadCelular();
		textoEmail += "<br> <b> Email: </b> " + this.sessaoAtualizacaoDados.geteMail();
		
//		textoEmail += "<br><br><br> <b> Endereço para entrega de mercadoria: </b> <br>";
//		textoEmail += "<br> <b> CEP: </b> " + this.sessaoAtualizacaoDados.getCadCEP();
//		textoEmail += "<br> <b> Endereço: </b> " + this.sessaoAtualizacaoDados.gete
//		textoEmail += "<br> <b> Número: </b> " + this.sessaoAtualizacaoDados.getNumeroEnderecoEntregaMercadoria();
//		textoEmail += "<br> <b> Complemento: </b> " + this.sessaoAtualizacaoDados.getComplementoEnderecoEntregaMercadoria();
//		textoEmail += "<br> <b> Bairro: </b> " + this.sessaoAtualizacaoDados.getBairroEntregaMercadoria();
//		textoEmail += "<br> <b> Cidade: </b> " + this.sessaoAtualizacaoDados.getCidadeEntregaMercadoria();
//		textoEmail += "<br> <b> Estado: </b> " + this.sessaoAtualizacaoDados.getEstadoEntregaMercadoria();
		
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

		JavaMailApp.enviarEmail("Atualização de dados de usuário", textoEmail);

		result.include("sucesso", "Foi enviado um email para a Alabastrum solicitando a atualização dos seus dados cadastrais. Você receberá um email assim que a atualização for concluída.");

		result.redirectTo(HomeController.class).home();

	}
}
