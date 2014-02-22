package escritoriovirtualalabastrum.controller;

import java.math.BigDecimal;

import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.ValidationMessage;
import escritoriovirtualalabastrum.anotacoes.Funcionalidade;
import escritoriovirtualalabastrum.hibernate.HibernateUtil;
import escritoriovirtualalabastrum.modelo.Usuario;
import escritoriovirtualalabastrum.sessao.SessaoGeral;
import escritoriovirtualalabastrum.sessao.SessaoUsuario;
import escritoriovirtualalabastrum.util.JavaMailApp;
import escritoriovirtualalabastrum.util.Util;

@Resource
public class TransferenciaCreditoController {

	private Result result;
	private HibernateUtil hibernateUtil;
	private SessaoUsuario sessaoUsuario;
	private SessaoGeral sessaoGeral;
	private Validator validator;

	public TransferenciaCreditoController(Result result, HibernateUtil hibernateUtil, SessaoUsuario sessaoUsuario, Validator validator, SessaoGeral sessaoGeral) {

		this.result = result;
		this.hibernateUtil = hibernateUtil;
		this.sessaoUsuario = sessaoUsuario;
		this.validator = validator;
		this.sessaoGeral = sessaoGeral;
	}

	@Funcionalidade
	public void acessarTelaTransferenciaCredito() {

	}

	@Funcionalidade
	public void transferir(Integer codigoQuemVaiReceber, BigDecimal quantidadeASerTransferida) {

		Usuario usuario = null;

		if (Util.preenchido(codigoQuemVaiReceber)) {

			usuario = this.hibernateUtil.selecionar(new Usuario(codigoQuemVaiReceber));

			if (Util.vazio(usuario)) {

				codigoUsuarioInvalido();
			}
		}

		else {

			codigoUsuarioInvalido();
		}

		if (Util.vazio(quantidadeASerTransferida) || quantidadeASerTransferida.equals(BigDecimal.ZERO)) {

			validator.add(new ValidationMessage("Quantidade deve ser maior do que zero.", "Atenção"));
			validator.onErrorRedirectTo(this).acessarTelaTransferenciaCredito();
		}

		if (quantidadeASerTransferida.compareTo(Util.converterStringParaBigDecimal(this.sessaoUsuario.getUsuario().getCadCredito())) > 0) {

			validator.add(new ValidationMessage("Quantidade a ser transferida maior do que o seu crédito atual.", "Atenção"));
			validator.onErrorRedirectTo(this).acessarTelaTransferenciaCredito();
		}

		this.sessaoGeral.adicionar("codigoQuemVaiReceber", codigoQuemVaiReceber);
		this.sessaoGeral.adicionar("nomeQuemVaiReceber", usuario.getvNome());
		this.sessaoGeral.adicionar("quantidadeASerTransferida", quantidadeASerTransferida);
	}

	@Funcionalidade
	public void confirmarTransferencia() {

		String textoEmail = "";

		textoEmail += "<br><b> De: </b> " + this.sessaoUsuario.getUsuario().getId_Codigo() + " - " + this.sessaoUsuario.getUsuario().getvNome();
		textoEmail += "<br><b> Para: </b> " + this.sessaoGeral.getValor("codigoQuemVaiReceber") + " - " + this.sessaoGeral.getValor("nomeQuemVaiReceber");
		textoEmail += "<br><b> Quantidade a ser transferida: </b> R$ " + this.sessaoGeral.getValor("quantidadeASerTransferida");

		JavaMailApp.enviarEmail("Transferência de crédito", "pedidos@alabastrum.com.br", textoEmail);

		result.include("sucesso", "Transferência realizada com sucesso. As modificações estarão concluídas na próxima atualização das informações do sistema.");

		result.redirectTo(HomeController.class).home();
	}

	private void codigoUsuarioInvalido() {

		validator.add(new ValidationMessage("Código de usuário inválido.", "Atenção"));
		validator.onErrorRedirectTo(this).acessarTelaTransferenciaCredito();
	}

}
