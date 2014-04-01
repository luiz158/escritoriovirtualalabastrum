package escritoriovirtualalabastrum.controller;

import java.util.GregorianCalendar;

import org.joda.time.DateTime;

import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import escritoriovirtualalabastrum.anotacoes.Funcionalidade;
import escritoriovirtualalabastrum.hibernate.HibernateUtil;
import escritoriovirtualalabastrum.modelo.AutorizacaoAtivacaoAutomatica;
import escritoriovirtualalabastrum.modelo.InformacoesUltimaAtualizacao;
import escritoriovirtualalabastrum.sessao.SessaoUsuario;
import escritoriovirtualalabastrum.util.JavaMailApp;
import escritoriovirtualalabastrum.util.Util;

@Resource
public class HomeController {

	private Result result;
	private HibernateUtil hibernateUtil;
	private SessaoUsuario sessaoUsuario;

	public HomeController(Result result, HibernateUtil hibernateUtil, SessaoUsuario sessaoUsuario) {

		this.result = result;
		this.hibernateUtil = hibernateUtil;
		this.sessaoUsuario = sessaoUsuario;
	}

	@Funcionalidade
	public void home() {

		AutorizacaoAtivacaoAutomatica autorizacaoAtivacaoAutomaticaFiltro = new AutorizacaoAtivacaoAutomatica();
		autorizacaoAtivacaoAutomaticaFiltro.setId_Codigo(this.sessaoUsuario.getUsuario().getId_Codigo());

		if (this.hibernateUtil.contar(autorizacaoAtivacaoAutomaticaFiltro) == 0) {

			result.include("exibirMensagemAutorizacaoAtivacaoAutomatica", true);
		}

		InformacoesUltimaAtualizacao informacoesUltimaAtualizacao = this.hibernateUtil.selecionar(new InformacoesUltimaAtualizacao());

		if (Util.preenchido(informacoesUltimaAtualizacao)) {

			DateTime dateTime = new DateTime(informacoesUltimaAtualizacao.getDataHora());

			result.include("dataHoraUltimaAtualizacao", dateTime.toString("dd/MM/YYYY HH:mm"));
		}
	}

	@Funcionalidade
	public void autorizarAtivacaoAutomatica(String autorizacao) {

		String corpo = "Eu, " + this.sessaoUsuario.getUsuario().getvNome() + ", autorizo o débito da minha bonificação o valor para minha ATIVAÇÃO AUTOMÁTICA";
		corpo += "<br> <br>";
		corpo += "Autorização: " + autorizacao;

		new JavaMailApp("Autorização de débito de bonificação para ativação automática ", "financeiro@alabastrum.com.br, " + this.sessaoUsuario.getUsuario().geteMail(), corpo, null).start();

		AutorizacaoAtivacaoAutomatica autorizacaoAtivacaoAutomatica = new AutorizacaoAtivacaoAutomatica();
		autorizacaoAtivacaoAutomatica.setId_Codigo(this.sessaoUsuario.getUsuario().getId_Codigo());
		autorizacaoAtivacaoAutomatica.setDataAutorizacao(new GregorianCalendar());
		autorizacaoAtivacaoAutomatica.setAutorizacao(autorizacao);

		this.hibernateUtil.salvarOuAtualizar(autorizacaoAtivacaoAutomatica);

		result.forwardTo(this).home();
	}

	@Funcionalidade
	public void emails() {
	}

	@Funcionalidade
	public void kitIngresso() {
	}

	@Funcionalidade
	public void qualificacao() {
	}

	@Funcionalidade
	public void downloads() {
	}
}
