package escritoriovirtualalabastrum.controller;

import java.io.PrintWriter;
import java.lang.reflect.Field;

import org.hibernate.criterion.MatchMode;
import org.joda.time.DateTime;

import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import escritoriovirtualalabastrum.anotacoes.Funcionalidade;
import escritoriovirtualalabastrum.anotacoes.Public;
import escritoriovirtualalabastrum.hibernate.HibernateUtil;
import escritoriovirtualalabastrum.modelo.PreCadastro;
import escritoriovirtualalabastrum.modelo.Usuario;
import escritoriovirtualalabastrum.sessao.SessaoAtualizacaoDados;
import escritoriovirtualalabastrum.sessao.SessaoUsuario;
import escritoriovirtualalabastrum.util.JavaMailApp;
import escritoriovirtualalabastrum.util.Util;

@Resource
public class AtualizacaoDadosController {

	public static final String CAMINHO_PASTA_PRE_CADASTRO = ImportacaoArquivoController.PASTA_RAIZ + "Dropbox/do-escritorio-para-o-desktop/pre-cadastro-de-distribuidor-pelo-site/";

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

		Usuario usuario = this.hibernateUtil.selecionar(new Usuario(this.sessaoUsuario.getUsuario().getId_Codigo()), MatchMode.EXACT);

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

	@Public
	public void acessarTelaAtualizacaoDadosPeloSite(boolean exibirMensagemAgradecimento) {

		this.sessaoAtualizacaoDados = new SessaoAtualizacaoDados();

		result.include("exibirMensagemAgradecimento", exibirMensagemAgradecimento);
	}

	@Public
	public void salvarPreCadastroDistribuidorPeloSite(SessaoAtualizacaoDados sessaoAtualizacaoDados) {

		this.sessaoAtualizacaoDados = sessaoAtualizacaoDados;

		criarArquivoNoDisco(montarTextoArquivo());

		JavaMailApp.enviarEmail("Pré-cadastro de distribuidor pelo site", "atendimento@alabastrum.com.br", montarTextoEmail());

		salvarPreCadastroNoBanco();

		result.forwardTo(this).acessarTelaAtualizacaoDadosPeloSite(true);
	}

	private void salvarPreCadastroNoBanco() {

		PreCadastro preCadastro = new PreCadastro();
		preCadastro.setCpf(this.sessaoAtualizacaoDados.getCPF());

		this.hibernateUtil.salvarOuAtualizar(preCadastro);
	}

	private String montarTextoEmail() {

		String textoEmail = "";

		textoEmail += "<br> <b>Nome: </b> " + this.sessaoAtualizacaoDados.getvNome();
		textoEmail += "<br> <b>Data de nascimento: </b> " + this.sessaoAtualizacaoDados.getDt_Nasc();
		textoEmail += "<br> <b>CPF: </b> " + this.sessaoAtualizacaoDados.getCPF();
		textoEmail += "<br> <b>RG: </b> " + this.sessaoAtualizacaoDados.getCadRG();
		textoEmail += "<br> <b>Emissor: </b> " + this.sessaoAtualizacaoDados.getCadOrgaoExpedidor();
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
		textoEmail += "<br> <b> Telefone residencial: </b> " + this.sessaoAtualizacaoDados.getTelTitular2();
		textoEmail += "<br> <b> Telefone celular: </b> " + this.sessaoAtualizacaoDados.getCelTitular2();
		textoEmail += "<br><br><br> <b> Dados bancários: </b> <br>";
		textoEmail += "<br> <b> Banco: </b> " + this.sessaoAtualizacaoDados.getCadBanco();
		textoEmail += "<br> <b> Tipo da conta: </b> " + this.sessaoAtualizacaoDados.getCadTipoConta();
		textoEmail += "<br> <b> Número da agência: </b> " + this.sessaoAtualizacaoDados.getCadAgencia();
		textoEmail += "<br> <b> Número da conta: </b> " + this.sessaoAtualizacaoDados.getCadCCorrente();
		textoEmail += "<br><br><br> <b> Quem te indicou? </b> <br>";
		textoEmail += "<br> <b> Código: </b> " + this.sessaoAtualizacaoDados.getCodigoQuemIndicou();
		textoEmail += "<br> <b> Nome: </b> " + this.sessaoAtualizacaoDados.getNomeQuemIndicou();
		textoEmail += "<br> <b> Observações: </b> " + this.sessaoAtualizacaoDados.getObservacoes();

		return textoEmail;
	}

	private String montarTextoArquivo() {

		String textoArquivo = "";

		textoArquivo += "Nome: \'" + this.sessaoAtualizacaoDados.getvNome() + "\'\r\n";
		textoArquivo += "Data_de_nascimento: \'" + this.sessaoAtualizacaoDados.getDt_Nasc() + "\'\r\n";
		textoArquivo += "CPF: \'" + this.sessaoAtualizacaoDados.getCPF() + "\'\r\n";
		textoArquivo += "RG: \'" + this.sessaoAtualizacaoDados.getCadRG() + "\'\r\n";
		textoArquivo += "Emissor: \'" + this.sessaoAtualizacaoDados.getCadOrgaoExpedidor() + "\'\r\n";
		textoArquivo += "Sexo: \'" + this.sessaoAtualizacaoDados.getCadSexo() + "\'\r\n";
		textoArquivo += "Estado_civil: \'" + this.sessaoAtualizacaoDados.getCadEstCivil() + "\'\r\n";
		textoArquivo += "CEP: \'" + this.sessaoAtualizacaoDados.getCadCEP() + "\'\r\n";
		textoArquivo += "Endereco: \'" + this.sessaoAtualizacaoDados.getCadEndereco() + "\'\r\n";
		textoArquivo += "Bairro: \'" + this.sessaoAtualizacaoDados.getCadBairro() + "\'\r\n";
		textoArquivo += "Cidade: \'" + this.sessaoAtualizacaoDados.getCadCidade() + "\'\r\n";
		textoArquivo += "Estado: \'" + this.sessaoAtualizacaoDados.getCadUF() + "\'\r\n";
		textoArquivo += "Telefone_residencial: \'" + this.sessaoAtualizacaoDados.getTel() + "\'\r\n";
		textoArquivo += "Telefone_celular: \'" + this.sessaoAtualizacaoDados.getCadCelular() + "\'\r\n";
		textoArquivo += "Email: \'" + this.sessaoAtualizacaoDados.geteMail() + "\'\r\n";
		textoArquivo += "Banco: \'" + this.sessaoAtualizacaoDados.getCadBanco() + "\'\r\n";
		textoArquivo += "Tipo_da_conta: \'" + this.sessaoAtualizacaoDados.getCadTipoConta() + "\'\r\n";
		textoArquivo += "Numero_da_agencia: \'" + this.sessaoAtualizacaoDados.getCadAgencia() + "\'\r\n";
		textoArquivo += "Numero_da_conta: \'" + this.sessaoAtualizacaoDados.getCadCCorrente() + "\'\r\n";
		textoArquivo += "Codigo: \'" + this.sessaoAtualizacaoDados.getCodigoQuemIndicou() + "\'\r\n";
		textoArquivo += "nomepatroc: \'" + this.sessaoAtualizacaoDados.getNomeQuemIndicou() + "\'\r\n";
		textoArquivo += "Observacoes: \'" + this.sessaoAtualizacaoDados.getObservacoes() + "\'\r\n";

		return textoArquivo;
	}

	private void criarArquivoNoDisco(String textoEmail) {

		PrintWriter writer = null;

		try {

			writer = new PrintWriter(CAMINHO_PASTA_PRE_CADASTRO + new DateTime().toString("dd-MM-YYYY_HH-mm-ss") + ".txt", "UTF-8");

			writer.println(textoEmail);
			writer.close();

		} catch (Exception e) {

			e.printStackTrace();
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
		textoEmail += "<br> <b>CPF: </b> " + this.sessaoUsuario.getUsuario().getCPF();
		textoEmail += "<br> <b>RG: </b> " + this.sessaoAtualizacaoDados.getCadRG();
		textoEmail += "<br> <b>Emissor: </b> " + this.sessaoAtualizacaoDados.getCadOrgaoExpedidor();
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
		textoEmail += "<br> <b> Telefone residencial: </b> " + this.sessaoAtualizacaoDados.getTelTitular2();
		textoEmail += "<br> <b> Telefone celular: </b> " + this.sessaoAtualizacaoDados.getCelTitular2();
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
