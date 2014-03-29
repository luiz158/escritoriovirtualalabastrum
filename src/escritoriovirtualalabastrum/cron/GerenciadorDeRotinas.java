package escritoriovirtualalabastrum.cron;

import escritoriovirtualalabastrum.hibernate.ThreadRestartHibernate;

public class GerenciadorDeRotinas {

	public static void iniciarRotinas() {

		ThreadRestartHibernate.iniciarThread();
		new NotificacaoNovatosNaRede().iniciarRotina();
		new AtualizacaoArquivosAutomaticamente().iniciarRotina();
		new BonificacaoRedeRotina().iniciarRotina();
	}
}
