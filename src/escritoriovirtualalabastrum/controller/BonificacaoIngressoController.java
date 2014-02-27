package escritoriovirtualalabastrum.controller;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;

import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import escritoriovirtualalabastrum.anotacoes.Funcionalidade;
import escritoriovirtualalabastrum.auxiliar.BonificacaoAuxiliar;
import escritoriovirtualalabastrum.auxiliar.MalaDireta;
import escritoriovirtualalabastrum.hibernate.HibernateUtil;
import escritoriovirtualalabastrum.modelo.FixoIngresso;
import escritoriovirtualalabastrum.modelo.HistoricoKit;
import escritoriovirtualalabastrum.modelo.PorcentagemIngresso;
import escritoriovirtualalabastrum.modelo.Usuario;
import escritoriovirtualalabastrum.service.ListaIngressoService;
import escritoriovirtualalabastrum.service.MalaDiretaService;
import escritoriovirtualalabastrum.sessao.SessaoUsuario;
import escritoriovirtualalabastrum.util.Util;

@Resource
public class BonificacaoIngressoController {

	private static final String KIT_INGRESSO_NAO_DEFINIDO_PARA_O_DISTRIBUIDOR = "Kit de ingresso não definido para o distribuidor";
	private Result result;
	private HibernateUtil hibernateUtil;
	private SessaoUsuario sessaoUsuario;
	private Validator validator;

	public BonificacaoIngressoController(Result result, HibernateUtil hibernateUtil, SessaoUsuario sessaoUsuario, Validator validator) {

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

		List<BonificacaoAuxiliar> bonificacoes = calcularBonificacoes(ano, mes);
		result.include("bonificacoes", bonificacoes);

		result.include("somatorioBonificacao", calcularSomatorioBonificacoes(bonificacoes));

		result.include("mesAno", mes + "/" + ano);
	}

	private BigDecimal calcularSomatorioBonificacoes(List<BonificacaoAuxiliar> bonificacoes) {

		BigDecimal somatorioBonificacao = new BigDecimal("0");

		for (BonificacaoAuxiliar bonificacao : bonificacoes) {

			if (Util.preenchido(bonificacao.getBonificacao())) {

				somatorioBonificacao = somatorioBonificacao.add(bonificacao.getBonificacao());
			}
		}

		return somatorioBonificacao;
	}

	private List<BonificacaoAuxiliar> calcularBonificacoes(Integer ano, Integer mes) {

		List<BonificacaoAuxiliar> bonificacoes = new ArrayList<BonificacaoAuxiliar>();

		BonificacaoAuxiliar porcentagemUsuarioLogado = encontrarPorcentagemDeAcordoComKit(this.sessaoUsuario.getUsuario(), ano, mes);
		this.result.include("kitUsuarioLogado", porcentagemUsuarioLogado.getKit());
		this.result.include("porcentagemKitUsuarioLogado", porcentagemUsuarioLogado.getPorcentagem());

		TreeMap<Integer, MalaDireta> malaDiretaHash = gerarMalaDiretaDeAcordoComFiltros(ano, mes);

		for (Entry<Integer, MalaDireta> malaDiretaEntry : malaDiretaHash.entrySet()) {

			MalaDireta malaDireta = malaDiretaEntry.getValue();

			Usuario usuario = malaDireta.getUsuario();

			if (malaDireta.getNivel() == 1) {

				calcularBonificacoesPorPorcentagem(ano, mes, bonificacoes, porcentagemUsuarioLogado, malaDireta, usuario);

			} else {

				calcularBonificacoesFixas(ano, mes, bonificacoes, malaDireta, usuario);
			}
		}

		calcularBonificacaoFixaDeDiamante(ano, mes, bonificacoes);

		if (this.sessaoUsuario.getUsuario().getPosAtual().toLowerCase().contains(MalaDiretaService.DIAMANTE.toLowerCase())) {

			calcularBonificacoesVariaveisDeDiamante(ano, mes);
		}

		return bonificacoes;
	}

	private void calcularBonificacoesVariaveisDeDiamante(Integer ano, Integer mes) {

		boolean metaAtingida = new MalaDiretaService().verificaSeMetaDeDiamanteFoiAtingida(this.sessaoUsuario, result, hibernateUtil, validator, ano, mes);

		if (metaAtingida) {

			System.out.println("META ATINGIDA");
		}
	}

	private void calcularBonificacoesFixas(Integer ano, Integer mes, List<BonificacaoAuxiliar> bonificacoes, MalaDireta malaDireta, Usuario usuario) {

		String kit = encontrarHistoricoKitDeAcordoComUsuarioEDataInformada(usuario, ano, mes);

		FixoIngresso fixoIngresso = new FixoIngresso();
		fixoIngresso.setData_referencia(new GregorianCalendar(ano, mes - 1, 1));
		fixoIngresso.setGeracao(String.valueOf(malaDireta.getNivel()));

		fixoIngresso = this.hibernateUtil.selecionar(fixoIngresso);

		BonificacaoAuxiliar bonificacaoAuxiliar = new BonificacaoAuxiliar();
		bonificacaoAuxiliar.setUsuario(usuario);
		bonificacaoAuxiliar.setKit(kit);
		bonificacaoAuxiliar.setGeracao(malaDireta.getNivel());

		try {

			Field field = fixoIngresso.getClass().getDeclaredField(kit);
			field.setAccessible(true);
			bonificacaoAuxiliar.setBonificacao((BigDecimal) field.get(fixoIngresso));

		} catch (Exception e) {
		}

		bonificacoes.add(bonificacaoAuxiliar);
	}

	private void calcularBonificacoesPorPorcentagem(Integer ano, Integer mes, List<BonificacaoAuxiliar> bonificacoes, BonificacaoAuxiliar porcentagemUsuarioLogado, MalaDireta malaDireta, Usuario usuario) {

		BonificacaoAuxiliar bonificacaoAuxiliar = encontrarPontuacaoDeAcordoComKit(usuario, ano, mes);
		bonificacaoAuxiliar.setGeracao(malaDireta.getNivel());

		if (bonificacaoAuxiliar.getPontuacao().equals(BigDecimal.ZERO)) {

			bonificacaoAuxiliar.setBonificacao(BigDecimal.ZERO);

		} else {

			bonificacaoAuxiliar.setBonificacao(porcentagemUsuarioLogado.getPorcentagem().divide(bonificacaoAuxiliar.getPontuacao()).multiply(new BigDecimal("100")));
			bonificacaoAuxiliar.setComoFoiCalculado(Util.formatarBigDecimal(porcentagemUsuarioLogado.getPorcentagem()) + "% de " + Util.formatarBigDecimal(bonificacaoAuxiliar.getPontuacao()));
		}

		bonificacoes.add(bonificacaoAuxiliar);
	}

	private void calcularBonificacaoFixaDeDiamante(Integer ano, Integer mes, List<BonificacaoAuxiliar> bonificacoes) {

		List<Usuario> distribuidoresDoDiamante = buscarDistribuidoresDoDiamante(ano, mes);

		for (Usuario usuario : distribuidoresDoDiamante) {

			String kit = encontrarHistoricoKitDeAcordoComUsuarioEDataInformada(usuario, ano, mes);

			FixoIngresso fixoIngresso = new FixoIngresso();
			fixoIngresso.setData_referencia(new GregorianCalendar(ano, mes - 1, 1));
			fixoIngresso.setGeracao("Diamante");

			fixoIngresso = this.hibernateUtil.selecionar(fixoIngresso);

			BonificacaoAuxiliar bonificacaoAuxiliar = new BonificacaoAuxiliar();
			bonificacaoAuxiliar.setUsuario(usuario);
			bonificacaoAuxiliar.setKit(kit);
			bonificacaoAuxiliar.setComoFoiCalculado("Bonificação fixa de diamante");

			try {

				Field field = fixoIngresso.getClass().getDeclaredField(kit);
				field.setAccessible(true);
				bonificacaoAuxiliar.setBonificacao((BigDecimal) field.get(fixoIngresso));

			} catch (Exception e) {
			}

			bonificacoes.add(bonificacaoAuxiliar);
		}
	}

	private List<Usuario> buscarDistribuidoresDoDiamante(Integer ano, Integer mes) {

		ListaIngressoService listaIngressoService = new ListaIngressoService();
		listaIngressoService.setHibernateUtil(hibernateUtil);

		DateTime dataInicial = new DateTime(ano, mes, 1, 0, 0, 0);
		DateTime dataFinal = new DateTime(ano, mes, dataInicial.dayOfMonth().withMaximumValue().dayOfMonth().get(), 0, 0, 0);

		Usuario usuario = new Usuario();

		List<Criterion> restricoes = new ArrayList<Criterion>();
		restricoes.add(Restrictions.between("Dt_Ingresso", dataInicial.toGregorianCalendar(), dataFinal.toGregorianCalendar()));

		usuario.setId_CR(this.sessaoUsuario.getUsuario().getId_Codigo());

		List<Usuario> distribuidoresDoDiamante = hibernateUtil.buscar(usuario, restricoes);

		return distribuidoresDoDiamante;
	}

	private TreeMap<Integer, MalaDireta> gerarMalaDiretaDeAcordoComFiltros(Integer ano, Integer mes) {

		ListaIngressoService listaIngressoService = new ListaIngressoService();
		listaIngressoService.setHibernateUtil(hibernateUtil);

		DateTime dataInicial = new DateTime(ano, mes, 1, 0, 0, 0);
		DateTime dataFinal = new DateTime(ano, mes, dataInicial.dayOfMonth().withMaximumValue().dayOfMonth().get(), 0, 0, 0);

		TreeMap<Integer, MalaDireta> malaDireta = listaIngressoService.gerarMalaDiretaFiltrandoPorDataDeIngresso(this.sessaoUsuario.getUsuario().getId_Codigo(), dataInicial.toGregorianCalendar(), dataFinal.toGregorianCalendar(), "id_Patroc");

		return malaDireta;
	}

	private BonificacaoAuxiliar encontrarPorcentagemDeAcordoComKit(Usuario usuario, Integer ano, Integer mes) {

		BonificacaoAuxiliar bonificacaoAuxiliar = new BonificacaoAuxiliar();

		String kit = encontrarHistoricoKitDeAcordoComUsuarioEDataInformada(usuario, ano, mes);
		bonificacaoAuxiliar.setKit(kit);

		if (kit.equals(KIT_INGRESSO_NAO_DEFINIDO_PARA_O_DISTRIBUIDOR)) {

			bonificacaoAuxiliar.setPorcentagem(BigDecimal.ZERO);
			return bonificacaoAuxiliar;
		}

		PorcentagemIngresso porcentagemIngresso = new PorcentagemIngresso();
		porcentagemIngresso.setData_referencia(new GregorianCalendar(ano, mes - 1, 1));

		porcentagemIngresso = this.hibernateUtil.selecionar(porcentagemIngresso);

		try {

			Field field = porcentagemIngresso.getClass().getDeclaredField(kit + "_porcentagem");

			field.setAccessible(true);

			bonificacaoAuxiliar.setPorcentagem((BigDecimal) field.get(porcentagemIngresso));
			return bonificacaoAuxiliar;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private BonificacaoAuxiliar encontrarPontuacaoDeAcordoComKit(Usuario usuario, Integer ano, Integer mes) {

		BonificacaoAuxiliar bonificacaoAuxiliar = new BonificacaoAuxiliar();
		bonificacaoAuxiliar.setUsuario(usuario);

		String kit = encontrarHistoricoKitDeAcordoComUsuarioEDataInformada(usuario, ano, mes);
		bonificacaoAuxiliar.setKit(kit);

		if (kit.equals(KIT_INGRESSO_NAO_DEFINIDO_PARA_O_DISTRIBUIDOR)) {

			bonificacaoAuxiliar.setPontuacao(BigDecimal.ZERO);
			return bonificacaoAuxiliar;
		}

		PorcentagemIngresso porcentagemIngresso = new PorcentagemIngresso();
		porcentagemIngresso.setData_referencia(new GregorianCalendar(ano, mes - 1, 1));

		porcentagemIngresso = this.hibernateUtil.selecionar(porcentagemIngresso);

		try {

			Field field = porcentagemIngresso.getClass().getDeclaredField(kit + "_pontuacao");

			field.setAccessible(true);

			bonificacaoAuxiliar.setPontuacao((BigDecimal) field.get(porcentagemIngresso));

			return bonificacaoAuxiliar;

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

			// validator.add(new
			// ValidationMessage("Este relatório só passou a existir no escritório virtual a partir de março de 2014",
			// "Erro"));
			// validator.onErrorRedirectTo(this).acessarTelaBonificacao();
		}
	}

	private String encontrarHistoricoKitDeAcordoComUsuarioEDataInformada(Usuario usuario, Integer ano, Integer mes) {

		HistoricoKit historicoKitfiltro = new HistoricoKit();
		historicoKitfiltro.setId_codigo(usuario.getId_Codigo());

		List<Criterion> restricoes = new ArrayList<Criterion>();
		restricoes.add(Restrictions.between("data_referencia", new GregorianCalendar(1990, 1, 1), new GregorianCalendar(ano, mes - 1, 1)));

		List<HistoricoKit> historicos = this.hibernateUtil.buscar(historicoKitfiltro, 1, restricoes, Order.desc("id"), null);

		if (Util.preenchido(historicos)) {

			return historicos.get(0).getKit();

		} else {

			return KIT_INGRESSO_NAO_DEFINIDO_PARA_O_DISTRIBUIDOR;
		}
	}
}
