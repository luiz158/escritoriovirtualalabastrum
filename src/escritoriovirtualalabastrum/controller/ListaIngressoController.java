package escritoriovirtualalabastrum.controller;

import java.util.GregorianCalendar;
import java.util.TreeMap;

import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import escritoriovirtualalabastrum.anotacoes.Funcionalidade;
import escritoriovirtualalabastrum.auxiliar.MalaDireta;
import escritoriovirtualalabastrum.hibernate.HibernateUtil;
import escritoriovirtualalabastrum.service.ListaIngressoService;
import escritoriovirtualalabastrum.sessao.SessaoUsuario;

@Resource
public class ListaIngressoController {

	private Result result;
	private HibernateUtil hibernateUtil;
	private SessaoUsuario sessaoUsuario;

	public ListaIngressoController(Result result, HibernateUtil hibernateUtil, SessaoUsuario sessaoUsuario) {

		this.result = result;
		this.hibernateUtil = hibernateUtil;
		this.sessaoUsuario = sessaoUsuario;
	}

	@Funcionalidade
	public void acessarTelaListaIngresso() {

	}

	@Funcionalidade
	public void gerarListaIngresso(GregorianCalendar dataInicial, GregorianCalendar dataFinal) {

		Integer codigoUsuarioLogado = this.sessaoUsuario.getUsuario().getId_Codigo();

		ListaIngressoService listaIngressoService = new ListaIngressoService();
		listaIngressoService.setHibernateUtil(hibernateUtil);

		TreeMap<Integer, MalaDireta> malaDireta = listaIngressoService.gerarMalaDiretaFiltrandoPorDataDeIngresso(codigoUsuarioLogado, dataInicial, dataFinal, null);

		result.include("malaDireta", malaDireta);

		result.include("dataInicialPesquisada", dataInicial);
		result.include("dataFinalPesquisada", dataFinal);
	}

}
