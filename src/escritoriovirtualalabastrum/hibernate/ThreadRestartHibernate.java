package escritoriovirtualalabastrum.hibernate;

public class ThreadRestartHibernate implements Runnable {

	private static final Integer TEMPO = 21600000;

	public void run() {

		while (true) {

			try {

				Thread.sleep(TEMPO);

				HibernateUtil.sessionFactory.close();
				HibernateUtil.reiniciarSessionFactory();
			}

			catch (InterruptedException e) {

				e.printStackTrace();
			}
		}
	}

	public static void iniciarThread() {

		ThreadRestartHibernate threadRestartHibernate = new ThreadRestartHibernate();
		new Thread(threadRestartHibernate).start();
	}
}