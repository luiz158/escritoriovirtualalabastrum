package escritoriovirtualalabastrum.controller;

import org.joda.time.DateTime;

import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import escritoriovirtualalabastrum.anotacoes.Funcionalidade;
import escritoriovirtualalabastrum.hibernate.HibernateUtil;
import escritoriovirtualalabastrum.modelo.InformacoesUltimaAtualizacao;
import escritoriovirtualalabastrum.util.Util;

@Resource
public class HomeController {

	private Result result;
	private HibernateUtil hibernateUtil;

	public HomeController(Result result, HibernateUtil hibernateUtil) {

		this.result = result;
		this.hibernateUtil = hibernateUtil;
	}

	@Funcionalidade
	public void home() {

		InformacoesUltimaAtualizacao informacoesUltimaAtualizacao = this.hibernateUtil.selecionar(new InformacoesUltimaAtualizacao());

		if (Util.preenchido(informacoesUltimaAtualizacao)) {

			DateTime dateTime = new DateTime(informacoesUltimaAtualizacao.getDataHora());

			result.include("dataHoraUltimaAtualizacao", dateTime.toString("dd/MM/YYYY HH:mm"));
		}
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

}
