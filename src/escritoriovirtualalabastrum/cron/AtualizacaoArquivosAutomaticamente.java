package escritoriovirtualalabastrum.cron;

import it.sauronsoftware.cron4j.Scheduler;

import java.io.File;
import java.util.List;

import escritoriovirtualalabastrum.controller.ImportacaoArquivoController;
import escritoriovirtualalabastrum.hibernate.HibernateUtil;
import escritoriovirtualalabastrum.modelo.PreCadastro;
import escritoriovirtualalabastrum.modelo.Usuario;
import escritoriovirtualalabastrum.util.JavaMailApp;

public class AtualizacaoArquivosAutomaticamente implements Runnable {

	public void run() {

		try {

			if (new File(ImportacaoArquivoController.PASTA_ATUALIZACAO_CSV).list().length >= 9) {

				HibernateUtil hibernateUtil = new HibernateUtil();

				ImportacaoArquivoController importacaoArquivoController = new ImportacaoArquivoController(null, null, hibernateUtil);
				importacaoArquivoController.processarArquivos();

				apagarArquivos();

				enviarEmailParaNovosDistribuidores(hibernateUtil);

				hibernateUtil.fecharSessao();
			}

		} catch (Exception e) {
		}
	}

	private void enviarEmailParaNovosDistribuidores(HibernateUtil hibernateUtil) {

		List<PreCadastro> preCadastros = hibernateUtil.buscar(new PreCadastro());

		for (PreCadastro preCadastro : preCadastros) {

			Usuario usuario = new Usuario();
			usuario.setCPF(preCadastro.getCpf());

			usuario = hibernateUtil.selecionar(usuario);

			JavaMailApp.enviarEmail("Pré-cadastro / Código Alabastrum", "atendimento@alabastrum.com.br, " + usuario.geteMail(), montarTextoEmail(usuario));
		}

		hibernateUtil.executarSQL("delete from precadastro");
	}

	private String montarTextoEmail(Usuario usuario) {

		String textoEmail = "Olá " + usuario.getvNome();
		textoEmail += ", o seu cadastro foi realizado com sucesso nos sistemas da Alabastrum. <br><br>";
		textoEmail += "O seu código é: " + usuario.getId_Codigo() + " <br><br>";
		textoEmail += "Com este código, você pode acessar o escritório virtual da alabastrum através do endereço: <a href='http://escritoriovirtual.alabastrum.com.br'> http://escritoriovirtual.alabastrum.com.br </a> <br>";
		textoEmail += "No seu primeiro acesso, você deve fazer login utilizando o seu código recebido e a senha padrão \"alabastrum\" (sem as aspas). <br><br>";
		textoEmail += "Você deverá, obrigatoriamente, trocar sua senha e atualizar seus dados cadastrais no sistema. <br><br>";
		textoEmail += "Qualquer problema ou dificuldade, entre em contato conosco através do e-mail: suporte@alabastrum.com.br <br><br>";
		textoEmail += "Sucesso!!!";

		return textoEmail;
	}

	private void apagarArquivos() {

		File folder = new File(ImportacaoArquivoController.PASTA_ATUALIZACAO_CSV);

		File[] files = folder.listFiles();

		for (File file : files) {

			file.delete();
		}
	}

	public void iniciarRotina() {

		AtualizacaoArquivosAutomaticamente task = new AtualizacaoArquivosAutomaticamente();

		Scheduler scheduler = new Scheduler();

		scheduler.schedule("*/10 * * * *", task);

		scheduler.start();
	}
}
