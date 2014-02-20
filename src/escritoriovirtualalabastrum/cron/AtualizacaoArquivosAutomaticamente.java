package escritoriovirtualalabastrum.cron;

import it.sauronsoftware.cron4j.Scheduler;

import java.io.File;
import java.io.IOException;

import escritoriovirtualalabastrum.controller.ImportacaoArquivoController;
import escritoriovirtualalabastrum.hibernate.HibernateUtil;

public class AtualizacaoArquivosAutomaticamente implements Runnable {

	public void run() {

		if (new File(ImportacaoArquivoController.PASTA_ATUALIZACAO_CSV).list().length > 0) {

			try {

				ImportacaoArquivoController importacaoArquivoController = new ImportacaoArquivoController(null, null, new HibernateUtil());
				importacaoArquivoController.processarArquivos();
				importacaoArquivoController.getHibernateUtil().fecharSessao();

			} catch (IOException e) {
			}

			apagarArquivos();
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

		scheduler.schedule("*/5 * * * *", task);

		scheduler.start();
	}
}
