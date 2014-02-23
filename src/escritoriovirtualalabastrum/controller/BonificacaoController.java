package escritoriovirtualalabastrum.controller;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TreeMap;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;

import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.ValidationMessage;
import escritoriovirtualalabastrum.anotacoes.Funcionalidade;
import escritoriovirtualalabastrum.auxiliar.MalaDireta;
import escritoriovirtualalabastrum.hibernate.HibernateUtil;
import escritoriovirtualalabastrum.modelo.HistoricoKit;
import escritoriovirtualalabastrum.modelo.PorcentagemIngresso;
import escritoriovirtualalabastrum.service.ListaIngressoService;
import escritoriovirtualalabastrum.sessao.SessaoUsuario;

@Resource
public class BonificacaoController {

	private Result result;
	private HibernateUtil hibernateUtil;
	private SessaoUsuario sessaoUsuario;
	private Validator validator;

	public BonificacaoController(Result result, HibernateUtil hibernateUtil, SessaoUsuario sessaoUsuario, Validator validator) {

		this.result = result;
		this.hibernateUtil = hibernateUtil;
		this.sessaoUsuario = sessaoUsuario;
		this.validator = validator;
	}

	@Funcionalidade
	public void acessarTelaBonificacao() {

	}

	@Funcionalidade
	public void gerarRelatorioBonificacao(Integer ano, Integer mes) {

		realizarValidacoes(ano, mes);

		System.out.println(encontrarPorcentagemDeAcordoComKit(ano, mes));

		ListaIngressoService listaIngressoService = new ListaIngressoService();
		listaIngressoService.setHibernateUtil(hibernateUtil);

		DateTime dataInicial = new DateTime(ano, mes, 1, 0, 0, 0);
		DateTime dataFinal = new DateTime(ano, mes, dataInicial.dayOfMonth().withMaximumValue().dayOfMonth().get(), 0, 0, 0);

		TreeMap<Integer, MalaDireta> malaDireta = listaIngressoService.gerarMalaDiretaFiltrandoPorDataDeIngresso(this.sessaoUsuario.getUsuario().getId_Codigo(), dataInicial.toGregorianCalendar(), dataFinal.toGregorianCalendar(), "id_Patroc");
	}

	private BigDecimal encontrarPorcentagemDeAcordoComKit(Integer ano, Integer mes) {

		String kit = encontrarHistoricoKitDeAcordoComDataInformada(ano, mes);

		PorcentagemIngresso porcentagemIngresso = new PorcentagemIngresso();
		porcentagemIngresso.setData_referencia(new GregorianCalendar(ano, mes - 1, 1));

		porcentagemIngresso = this.hibernateUtil.selecionar(porcentagemIngresso);

		try {

			Field field = porcentagemIngresso.getClass().getDeclaredField(kit + "_porcentagem");

			field.setAccessible(true);

			return (BigDecimal) field.get(porcentagemIngresso);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private void realizarValidacoes(Integer ano, Integer mes) {

		if (!this.sessaoUsuario.getUsuario().isAtivo()) {

			// validator.add(new
			// ValidationMessage("Você não está ativo. Só quem está ativo pode receber bonificação",
			// "Erro"));
			// validator.onErrorRedirectTo(this).acessarTelaBonificacao();
		}

		if (new GregorianCalendar(ano, mes - 1, 1).before(new GregorianCalendar(2014, 2, 1))) {

			validator.add(new ValidationMessage("Este relatório só passou a existir no escritório virtual a partir de março de 2014", "Erro"));
			validator.onErrorRedirectTo(this).acessarTelaBonificacao();
		}
	}

	private String encontrarHistoricoKitDeAcordoComDataInformada(Integer ano, Integer mes) {

		HistoricoKit historicoKitfiltro = new HistoricoKit();
		historicoKitfiltro.setId_codigo(this.sessaoUsuario.getUsuario().getId_Codigo());

		List<Criterion> restricoes = new ArrayList<Criterion>();
		restricoes.add(Restrictions.between("data_referencia", new GregorianCalendar(1990, 1, 1), new GregorianCalendar(ano, mes - 1, 1)));

		HistoricoKit historicoKit = (HistoricoKit) this.hibernateUtil.buscar(historicoKitfiltro, 1, restricoes, Order.desc("id"), null).get(0);

		return historicoKit.getKit();
	}
}
