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

		String textoEmail = "O usuário com código " + codigoUsuario + " atualizou seus dados no escritório virtual\n\n";

		textoEmail += "Informações:\n";

		textoEmail += "\n Data de nascimento: " + this.sessaoAtualizacaoDados.getDataNascimento();
		textoEmail += "\n Sexo: " + this.sessaoAtualizacaoDados.getSexo();
		textoEmail += "\n Estado civil: " + this.sessaoAtualizacaoDados.getEstadoCivil();
		textoEmail += "\n CEP: " + this.sessaoAtualizacaoDados.getCEP();
		textoEmail += "\n Endereço: " + this.sessaoAtualizacaoDados.getEndereco();
		textoEmail += "\n Número: " + this.sessaoAtualizacaoDados.getNumeroEndereco();
		textoEmail += "\n Complemento: " + this.sessaoAtualizacaoDados.getComplementoEndereco();
		textoEmail += "\n Bairro: " + this.sessaoAtualizacaoDados.getBairro();
		textoEmail += "\n Cidade: " + this.sessaoAtualizacaoDados.getCidade();
		textoEmail += "\n Estado: " + this.sessaoAtualizacaoDados.getEstado();
		textoEmail += "\n Telefone residencial: " + this.sessaoAtualizacaoDados.getTelefoneResidencialDDD() + " " + this.sessaoAtualizacaoDados.getTelefoneResidencial();
		textoEmail += "\n Telefone comercial: " + this.sessaoAtualizacaoDados.getTelefoneComercialDDD() + " " + this.sessaoAtualizacaoDados.getTelefoneComercial();
		textoEmail += "\n Telefone celular 1: " + this.sessaoAtualizacaoDados.getTelefoneCelular1DDD() + " " + this.sessaoAtualizacaoDados.getTelefoneCelular1();
		textoEmail += "\n Telefone celular 2: " + this.sessaoAtualizacaoDados.getTelefoneCelular2DDD() + " " + this.sessaoAtualizacaoDados.getTelefoneCelular2();
		textoEmail += "\n Email: " + this.sessaoAtualizacaoDados.getEmail();
		textoEmail += "\n\n\n Endereço para entrega de mercadoria: \n";
		textoEmail += "\n CEP: " + this.sessaoAtualizacaoDados.getCEPEntregaMercadoria();
		textoEmail += "\n Endereço: " + this.sessaoAtualizacaoDados.getEnderecoEntregaMercadoria();
		textoEmail += "\n Número: " + this.sessaoAtualizacaoDados.getNumeroEnderecoEntregaMercadoria();
		textoEmail += "\n Complemento: " + this.sessaoAtualizacaoDados.getComplementoEnderecoEntregaMercadoria();
		textoEmail += "\n Bairro: " + this.sessaoAtualizacaoDados.getBairroEntregaMercadoria();
		textoEmail += "\n Cidade: " + this.sessaoAtualizacaoDados.getCidadeEntregaMercadoria();
		textoEmail += "\n Estado: " + this.sessaoAtualizacaoDados.getEstadoEntregaMercadoria();
		textoEmail += "\n\n\n Dados do segundo titular: \n";
		textoEmail += "\n Nome: " + this.sessaoAtualizacaoDados.getNomeSegundoTitular();
		textoEmail += "\n Data de nascimento: " + this.sessaoAtualizacaoDados.getDataNascimentoSegundoTitular();
		textoEmail += "\n Sexo: " + this.sessaoAtualizacaoDados.getSexoSegundoTitular();
		textoEmail += "\n Estado civil: " + this.sessaoAtualizacaoDados.getSexoSegundoTitular();
		textoEmail += "\n CPF: " + this.sessaoAtualizacaoDados.getCPFSegundoTitular();
		textoEmail += "\n RG: " + this.sessaoAtualizacaoDados.getRGSegundoTitular();
		textoEmail += "\n Emissor: " + this.sessaoAtualizacaoDados.getEmissorSegundoTitular();
		textoEmail += "\n Email: " + this.sessaoAtualizacaoDados.getEmailSegundoTitular();
		textoEmail += "\n\n\n Dados bancários: \n";
		textoEmail += "\n Titular da conta: " + this.sessaoAtualizacaoDados.getTitularConta();
		textoEmail += "\n Banco: " + this.sessaoAtualizacaoDados.getBanco();
		textoEmail += "\n Tipo da conta: " + this.sessaoAtualizacaoDados.getTipoConta();
		textoEmail += "\n Número da agência: " + this.sessaoAtualizacaoDados.getNumeroAgencia();
		textoEmail += "\n Número da conta: " + this.sessaoAtualizacaoDados.getNumeroConta();

		JavaMailApp.enviarEmail("Atualização de dados de usuário", textoEmail);

		result.include("sucesso", "Foi enviado um email para a Alabastrum solicitando a atualização dos seus dados cadastrais. Você receberá um email assim que a atualização for concluída.");

		result.redirectTo(HomeController.class).home();

	}
}
