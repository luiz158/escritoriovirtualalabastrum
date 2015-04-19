package escritoriovirtualalabastrum.cron;

import it.sauronsoftware.cron4j.Scheduler;

import java.io.File;

import escritoriovirtualalabastrum.controller.ImportacaoArquivoController;
import escritoriovirtualalabastrum.emailSender.BoasVindas;
import escritoriovirtualalabastrum.emailSender.NotificacaoNovatosNaRede;
import escritoriovirtualalabastrum.hibernate.HibernateUtil;

public class AtualizacaoArquivosAutomaticamente implements Runnable {

	public void run() {

		try {

			if (new File(ImportacaoArquivoController.PASTA_ATUALIZACAO_CSV).list().length >= 10) {

				HibernateUtil hibernateUtil = new HibernateUtil();

				ImportacaoArquivoController importacaoArquivoController = new ImportacaoArquivoController(null, null, hibernateUtil);
				importacaoArquivoController.processarArquivos();

				apagarArquivos();

				NotificacaoNovatosNaRede.enviarEmail();
				BoasVindas.enviarEmail();

				hibernateUtil.fecharSessao();
			}

		} catch (Exception e) {
		}
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
