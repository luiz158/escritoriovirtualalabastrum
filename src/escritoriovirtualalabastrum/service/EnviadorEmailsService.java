package escritoriovirtualalabastrum.service;

import java.util.ArrayList;
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

		List<String> remetentes = new ArrayList<String>();

		int numeroMaximoRemetentesPorEmail = 300;

		for (Usuario usuario : usuarios) {

			if (Util.preenchido(usuario.geteMail())) {

				remetentes.add(usuario.geteMail());
				EnviadorEmailsController.emailsEnviados.add(usuario);

				if (remetentes.size() >= numeroMaximoRemetentesPorEmail) {

					String remetentesString = "";

					for (String remetente : remetentes) {

						remetentesString = remetentesString + remetente + ", ";
					}

					try {

						JavaMailApp.enviarEmail(assunto, remetentesString, corpo);

					} catch (Exception e) {

					} finally {

						remetentes = new ArrayList<String>();
					}
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