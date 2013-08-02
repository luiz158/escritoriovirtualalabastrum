package escritoriovirtualalabastrum.cron;

import it.sauronsoftware.cron4j.Scheduler;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import escritoriovirtualalabastrum.hibernate.HibernateUtil;
import escritoriovirtualalabastrum.modelo.Usuario;
import escritoriovirtualalabastrum.util.JavaMailApp;
import escritoriovirtualalabastrum.util.Util;

public class NotificacaoNovatosNaRede implements Runnable {

	public void run() {

		Usuario usuarioFiltro = new Usuario();
		usuarioFiltro.setDt_Ingresso(new DateTime().withMillisOfDay(0).toGregorianCalendar());

		HibernateUtil hibernateUtil = new HibernateUtil();

		List<Usuario> usuarios = hibernateUtil.buscar(usuarioFiltro);

		for (Usuario usuario : usuarios) {

			Usuario patroc = hibernateUtil.selecionar(new Usuario(usuario.getId_Patroc()));
			Usuario dem = hibernateUtil.selecionar(new Usuario(usuario.getId_Dem()));
			Usuario s = hibernateUtil.selecionar(new Usuario(usuario.getId_S()));
			Usuario m = hibernateUtil.selecionar(new Usuario(usuario.getId_M()));
			Usuario gb = hibernateUtil.selecionar(new Usuario(usuario.getId_GB()));
			Usuario gp = hibernateUtil.selecionar(new Usuario(usuario.getId_GP()));
			Usuario go = hibernateUtil.selecionar(new Usuario(usuario.getId_GO()));
			Usuario ge = hibernateUtil.selecionar(new Usuario(usuario.getId_GE()));
			Usuario m1 = hibernateUtil.selecionar(new Usuario(usuario.getId_M1()));
			Usuario m2 = hibernateUtil.selecionar(new Usuario(usuario.getId_M2()));
			Usuario m3 = hibernateUtil.selecionar(new Usuario(usuario.getId_M3()));
			Usuario m4 = hibernateUtil.selecionar(new Usuario(usuario.getId_M4()));
			Usuario m5 = hibernateUtil.selecionar(new Usuario(usuario.getId_M5()));
			Usuario la = hibernateUtil.selecionar(new Usuario(usuario.getId_LA()));
			Usuario la1 = hibernateUtil.selecionar(new Usuario(usuario.getId_LA1()));
			Usuario la2 = hibernateUtil.selecionar(new Usuario(usuario.getId_LA2()));
			Usuario cr = hibernateUtil.selecionar(new Usuario(usuario.getId_CR()));
			Usuario cr1 = hibernateUtil.selecionar(new Usuario(usuario.getId_CR1()));
			Usuario cr2 = hibernateUtil.selecionar(new Usuario(usuario.getId_CR2()));
			Usuario dr = hibernateUtil.selecionar(new Usuario(usuario.getId_DR()));
			Usuario dd = hibernateUtil.selecionar(new Usuario(usuario.getId_DD()));
			Usuario ds = hibernateUtil.selecionar(new Usuario(usuario.getId_DS()));
			Usuario pres = hibernateUtil.selecionar(new Usuario(usuario.getId_Pres()));

			List<String> emails = new ArrayList<String>();

			if (Util.preenchido(patroc) && !emails.contains(patroc.geteMail())) {

				emails.add(patroc.geteMail());
			}

			if (Util.preenchido(dem) && !emails.contains(dem.geteMail())) {

				emails.add(dem.geteMail());
			}

			if (Util.preenchido(s) && !emails.contains(s.geteMail())) {

				emails.add(s.geteMail());
			}

			if (Util.preenchido(m) && !emails.contains(m.geteMail())) {

				emails.add(m.geteMail());
			}

			if (Util.preenchido(gb) && !emails.contains(gb.geteMail())) {

				emails.add(gb.geteMail());
			}

			if (Util.preenchido(gp) && !emails.contains(gp.geteMail())) {

				emails.add(gp.geteMail());
			}

			if (Util.preenchido(go) && !emails.contains(go.geteMail())) {

				emails.add(go.geteMail());
			}

			if (Util.preenchido(ge) && !emails.contains(ge.geteMail())) {

				emails.add(ge.geteMail());
			}

			if (Util.preenchido(m1) && !emails.contains(m1.geteMail())) {

				emails.add(m1.geteMail());
			}

			if (Util.preenchido(m2) && !emails.contains(m2.geteMail())) {

				emails.add(m2.geteMail());
			}

			if (Util.preenchido(m3) && !emails.contains(m3.geteMail())) {

				emails.add(m3.geteMail());
			}

			if (Util.preenchido(m4) && !emails.contains(m4.geteMail())) {

				emails.add(m4.geteMail());
			}

			if (Util.preenchido(m5) && !emails.contains(m5.geteMail())) {

				emails.add(m5.geteMail());
			}

			if (Util.preenchido(la) && !emails.contains(la.geteMail())) {

				emails.add(la.geteMail());
			}

			if (Util.preenchido(la1) && !emails.contains(la1.geteMail())) {

				emails.add(la1.geteMail());
			}

			if (Util.preenchido(la2) && !emails.contains(la2.geteMail())) {

				emails.add(la2.geteMail());
			}

			if (Util.preenchido(cr) && !emails.contains(cr.geteMail())) {

				emails.add(cr.geteMail());
			}

			if (Util.preenchido(cr1) && !emails.contains(cr1.geteMail())) {

				emails.add(cr1.geteMail());
			}

			if (Util.preenchido(cr2) && !emails.contains(cr2.geteMail())) {

				emails.add(cr2.geteMail());
			}

			if (Util.preenchido(dr) && !emails.contains(dr.geteMail())) {

				emails.add(dr.geteMail());
			}

			if (Util.preenchido(dd) && !emails.contains(dd.geteMail())) {

				emails.add(dd.geteMail());
			}

			if (Util.preenchido(ds) && !emails.contains(ds.geteMail())) {

				emails.add(ds.geteMail());
			}

			if (Util.preenchido(pres) && !emails.contains(pres.geteMail())) {

				emails.add(pres.geteMail());
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

			textoEmail += "<b>Nome: </b> " + usuario.getvNome();
			textoEmail += "<br> <b>Código: </b> " + usuario.getId_Codigo();
			textoEmail += "<br> <b>Email: </b> " + usuario.geteMail();
			textoEmail += "<br> <b>Telefone: </b> " + usuario.getTel();
			textoEmail += "<br> <b>Celular: </b> " + usuario.getCadCelular();
			textoEmail += "<br><br> Para mais informações, consulte o escritório virtual. ";

			JavaMailApp.enviarEmail("Alabastrum - Novo distribuidor na sua rede", emailsString.toString(), textoEmail);
		}

		hibernateUtil.fecharSessao();
	}

	public void iniciarRotina() {

		NotificacaoNovatosNaRede task = new NotificacaoNovatosNaRede();

		Scheduler scheduler = new Scheduler();

		scheduler.schedule("50 23 * * *", task);

		scheduler.start();
	}
}
