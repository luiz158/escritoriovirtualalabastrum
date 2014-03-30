package escritoriovirtualalabastrum.controller;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import escritoriovirtualalabastrum.anotacoes.Funcionalidade;
import escritoriovirtualalabastrum.modelo.Usuario;
import escritoriovirtualalabastrum.service.EnviadorEmailsService;

@Resource
public class EnviadorEmailsController {

	private Result result;
	public static List<Usuario> emailsEnviados;

	public EnviadorEmailsController(Result result) {

		this.result = result;
	}

	@Funcionalidade(administrativa = "true")
	public void acessarEnviadorEmails() {

		result.include("emailsEnviados", emailsEnviados);
	}

	@Funcionalidade(administrativa = "true")
	public void enviarEmails(String assunto, String corpo, String action) {

		result.include("assunto", assunto);
		result.include("corpo", corpo);

		if ("Enviar".equals(action)) {

			emailsEnviados = new ArrayList<Usuario>();
			emailsEnviados.add(new Usuario());

			new EnviadorEmailsService(assunto, corpo).start();
		}

		result.forwardTo(this).acessarEnviadorEmails();
	}
}
