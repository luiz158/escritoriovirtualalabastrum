package escritoriovirtualalabastrum.cron;

import it.sauronsoftware.cron4j.Scheduler;
import escritoriovirtualalabastrum.modelo.Configuracao;
import escritoriovirtualalabastrum.util.Util;

public class BonificacaoDaRede implements Runnable {

	private static String id;

	public void run() {

		System.out.println("fdsfsdf");
	}

	public void iniciarRotina() {

		BonificacaoDaRede task = new BonificacaoDaRede();

		Scheduler scheduler = new Scheduler();

		String horario = new Configuracao().retornarConfiguracao("horarioRotinaBonificacoes");

		String schedulerString = horario.split(":")[1] + " " + horario.split(":")[0] + " * * *";

		if (Util.preenchido(id)) {

			scheduler.deschedule(id);
		}

		id = scheduler.schedule(schedulerString, task);

		scheduler.start();
	}
}
