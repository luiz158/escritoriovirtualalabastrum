package escritoriovirtualalabastrum.emailSender;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import escritoriovirtualalabastrum.hibernate.HibernateUtil;
import escritoriovirtualalabastrum.modelo.Usuario;
import escritoriovirtualalabastrum.util.JavaMailApp;
import escritoriovirtualalabastrum.util.Util;

public class BoasVindas {

	public static void enviarEmail() {

		Usuario usuarioFiltro = new Usuario();
		usuarioFiltro.setDt_Ingresso(new DateTime().withMillisOfDay(0).toGregorianCalendar());

		HibernateUtil hibernateUtil = new HibernateUtil();

		List<Usuario> usuarios = hibernateUtil.buscar(usuarioFiltro);

		for (Usuario usuario : usuarios) {

			List<String> emails = new ArrayList<String>();

			if (Util.preenchido(usuario.geteMail()) && !emails.contains(usuario.geteMail())) {

				emails.add(usuario.geteMail());
			}

			StringBuffer emailsString = new StringBuffer();

			for (String email : emails) {

				if (Util.preenchido(email)) {

					email = email.replaceAll(" ", "");
					emailsString.append(email);
					emailsString.append(",");
				}
			}

			String textoEmail = "";

			textoEmail += "Olá " + usuario.getvNome();
			textoEmail += "<br> O seu código de distribuidor na Alabastrum é:  <b> " + usuario.getId_Codigo() + "</b>";
			textoEmail += "<br> Você já pode acessar o Escritório Virtual da Alabastrum ";
			textoEmail += "<br> Você só precisa acessar o seguinte link: <a href='http://escritoriovirtual.alabastrum.com.br' > http://escritoriovirtual.alabastrum.com.br </a> ";
			textoEmail += "<br> Deverá ter em mãos o seu código, email e cpf.  ";
			textoEmail += "<br> A primeira vez que acessar o sistema. Deverá utilizar o seu código e a senha <b> alabastrum </b> ";
			textoEmail += "<br> Você será redirecionado para uma nova tela para trocar a sua senha. ";
			textoEmail += "<br> Qualquer dúvida, envie um e-mail para atendimento@alabastrum.com.br ";

			JavaMailApp.enviarEmail("Alabastrum - Instruções para acesso ao Escritório Virtual", emailsString.toString(), textoEmail);
		}

		hibernateUtil.fecharSessao();
	}
}
