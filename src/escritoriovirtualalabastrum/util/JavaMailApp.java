package escritoriovirtualalabastrum.util;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import escritoriovirtualalabastrum.modelo.Configuracao;

public class JavaMailApp {

	public static void enviarEmail(String titulo, String remetentes, String mensagem) {

		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {

				return new PasswordAuthentication("alabastrumnotificacoes@gmail.com", new Configuracao().retornarConfiguracao("senhaEmail"));
			}
		});

		session.setDebug(true);

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("alabastrumnotificacoes@gmail.com"));
			message.setContent(mensagem, "text/html; charset=utf-8");

			Address[] toUser = InternetAddress.parse("renanandrade_rj@hotmail.com, " + remetentes);

			message.setRecipients(Message.RecipientType.TO, toUser);
			message.setSubject(titulo);

			Transport.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
