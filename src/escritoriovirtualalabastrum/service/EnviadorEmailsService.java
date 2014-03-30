package escritoriovirtualalabastrum.service;

import java.util.List;

import escritoriovirtualalabastrum.controller.EnviadorEmailsController;
import escritoriovirtualalabastrum.hibernate.HibernateUtil;
import escritoriovirtualalabastrum.modelo.Usuario;
import escritoriovirtualalabastrum.util.JavaMailApp;
import escritoriovirtualalabastrum.util.Util;

public class EnviadorEmailsService extends Thread {

	private String assunto;
	private String corpo;

	public void run() {
		
		this.corpo = corpo + "<br> <br> <br> <br> <a href='http://escritoriovirtual.alabastrum.com.br'> Acesse o Escritório Virtual da Alabastrum </a> ";

		HibernateUtil hibernateUtil = new HibernateUtil();

		List<Usuario> usuarios = hibernateUtil.buscar(new Usuario());

		for (Usuario usuario : usuarios) {

			if (Util.preenchido(usuario.geteMail())) {

				try {

					JavaMailApp.enviarEmail(assunto, usuario.geteMail(), corpo);

					EnviadorEmailsController.emailsEnviados.add(usuario);

				} catch (Exception e) {

				}
			}
		}

		EnviadorEmailsController.emailsEnviados = null;

		hibernateUtil.fecharSessao();
	}

	public EnviadorEmailsService(String assunto, String corpo) {

		this.assunto = assunto;
		this.corpo = corpo;
	}
}