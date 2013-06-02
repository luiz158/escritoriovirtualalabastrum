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

		String codigoUsuario = this.sessaoUsuario.getUsuario().getId_Codigo();

		String textoEmail = "O usuário com código " + codigoUsuario + " atualizou seus dados no escritório virtual<br><br>";

		textoEmail += "Informações:<br>";

		textoEmail += "<br> <b>Data de nascimento: </b> " + this.sessaoAtualizacaoDados.getDataNascimento();
		textoEmail += "<br> <b> Sexo: </b> " + this.sessaoAtualizacaoDados.getSexo();
		textoEmail += "<br> <b> Estado civil: </b> " + this.sessaoAtualizacaoDados.getEstadoCivil();
		textoEmail += "<br> <b> CEP: </b> " + this.sessaoAtualizacaoDados.getCEP();
		textoEmail += "<br> <b> Endereço: </b> " + this.sessaoAtualizacaoDados.getEndereco();
		textoEmail += "<br> <b> Número: </b> " + this.sessaoAtualizacaoDados.getNumeroEndereco();
		textoEmail += "<br> <b> Complemento: </b> " + this.sessaoAtualizacaoDados.getComplementoEndereco();
		textoEmail += "<br> <b> Bairro: </b> " + this.sessaoAtualizacaoDados.getBairro();
		textoEmail += "<br> <b> Cidade: </b> " + this.sessaoAtualizacaoDados.getCidade();
		textoEmail += "<br> <b> Estado: </b> " + this.sessaoAtualizacaoDados.getEstado();
		textoEmail += "<br> <b> Telefone residencial: </b> " + this.sessaoAtualizacaoDados.getTelefoneResidencialDDD() + " " + this.sessaoAtualizacaoDados.getTelefoneResidencial();
		textoEmail += "<br> <b> Telefone comercial: </b> " + this.sessaoAtualizacaoDados.getTelefoneComercialDDD() + " " + this.sessaoAtualizacaoDados.getTelefoneComercial();
		textoEmail += "<br> <b> Telefone celular 1: </b> " + this.sessaoAtualizacaoDados.getTelefoneCelular1DDD() + " " + this.sessaoAtualizacaoDados.getTelefoneCelular1();
		textoEmail += "<br> <b> Telefone celular 2: </b> " + this.sessaoAtualizacaoDados.getTelefoneCelular2DDD() + " " + this.sessaoAtualizacaoDados.getTelefoneCelular2();
		textoEmail += "<br> <b> Email: </b> " + this.sessaoAtualizacaoDados.getEmail();
		textoEmail += "<br><br><br> <b> Endereço para entrega de mercadoria: </b> <br>";
		textoEmail += "<br> <b> CEP: </b> " + this.sessaoAtualizacaoDados.getCEPEntregaMercadoria();
		textoEmail += "<br> <b> Endereço: </b> " + this.sessaoAtualizacaoDados.getEnderecoEntregaMercadoria();
		textoEmail += "<br> <b> Número: </b> " + this.sessaoAtualizacaoDados.getNumeroEnderecoEntregaMercadoria();
		textoEmail += "<br> <b> Complemento: </b> " + this.sessaoAtualizacaoDados.getComplementoEnderecoEntregaMercadoria();
		textoEmail += "<br> <b> Bairro: </b> " + this.sessaoAtualizacaoDados.getBairroEntregaMercadoria();
		textoEmail += "<br> <b> Cidade: </b> " + this.sessaoAtualizacaoDados.getCidadeEntregaMercadoria();
		textoEmail += "<br> <b> Estado: </b> " + this.sessaoAtualizacaoDados.getEstadoEntregaMercadoria();
		textoEmail += "<br><br><br> <b> Dados do segundo titular: </b> <br>";
		textoEmail += "<br> <b> Nome: </b> " + this.sessaoAtualizacaoDados.getNomeSegundoTitular();
		textoEmail += "<br> <b> Data de nascimento: </b> " + this.sessaoAtualizacaoDados.getDataNascimentoSegundoTitular();
		textoEmail += "<br> <b> Sexo: </b> " + this.sessaoAtualizacaoDados.getSexoSegundoTitular();
		textoEmail += "<br> <b> Estado civil: </b> " + this.sessaoAtualizacaoDados.getSexoSegundoTitular();
		textoEmail += "<br> <b> CPF: </b> " + this.sessaoAtualizacaoDados.getCPFSegundoTitular();
		textoEmail += "<br> <b> RG: </b> " + this.sessaoAtualizacaoDados.getRGSegundoTitular();
		textoEmail += "<br> <b> Emissor: </b> " + this.sessaoAtualizacaoDados.getEmissorSegundoTitular();
		textoEmail += "<br> <b> Email: </b> " + this.sessaoAtualizacaoDados.getEmailSegundoTitular();
		textoEmail += "<br><br><br> <b> Dados bancários: </b> <br>";
		textoEmail += "<br> <b> Titular da conta: </b> " + this.sessaoAtualizacaoDados.getTitularConta();
		textoEmail += "<br> <b> Banco: </b> " + this.sessaoAtualizacaoDados.getBanco();
		textoEmail += "<br> <b> Tipo da conta: </b> " + this.sessaoAtualizacaoDados.getTipoConta();
		textoEmail += "<br> <b> Número da agência: </b> " + this.sessaoAtualizacaoDados.getNumeroAgencia();
		textoEmail += "<br> <b> Número da conta: </b> " + this.sessaoAtualizacaoDados.getNumeroConta();

		JavaMailApp.enviarEmail("Atualização de dados de usuário", textoEmail);

		result.include("sucesso", "Foi enviado um email para a Alabastrum solicitando a atualização dos seus dados cadastrais. Você receberá um email assim que a atualização for concluída.");

		result.redirectTo(HomeController.class).home();

	}
}
