package escritoriovirtualalabastrum.controller;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.hibernate.criterion.Criterion;
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

		List<BonificacaoAuxiliar> bonificacoes = calcularBonificacoes(this.sessaoUsuario.getUsuario(), ano, mes);
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

	private List<BonificacaoAuxiliar> calcularBonificacoes(Usuario usuario, Integer ano, Integer mes) {

		List<BonificacaoAuxiliar> bonificacoes = new ArrayList<BonificacaoAuxiliar>();

		BonificacaoAuxiliar porcentagemUsuario = encontrarPorcentagemDeAcordoComKit(usuario, ano, mes);
		this.result.include("kitUsuarioLogado", porcentagemUsuario.getKit());
		this.result.include("porcentagemKitUsuarioLogado", porcentagemUsuario.getPorcentagem());

		TreeMap<Integer, MalaDireta> malaDiretaHash = gerarMalaDiretaDeAcordoComFiltros(usuario, ano, mes);

		for (Entry<Integer, MalaDireta> malaDiretaEntry : malaDiretaHash.entrySet()) {

			MalaDireta malaDireta = malaDiretaEntry.getValue();

			Usuario usuarioMalaDireta = malaDireta.getUsuario();

			if (malaDireta.getNivel() == 1) {

				calcularBonificacoesPorPorcentagem(ano, mes, bonificacoes, porcentagemUsuario.getPorcentagem(), malaDireta.getNivel(), usuarioMalaDireta);

			} else {

				calcularBonificacoesFixas(ano, mes, bonificacoes, malaDireta, usuario);
			}
		}

		if (usuario.getPosAtual().toLowerCase().contains(MalaDiretaService.DIAMANTE.toLowerCase())) {

			this.result.include("isDiamante", true);

			calcularBonificacaoFixaDeDiamante(usuario, ano, mes, bonificacoes);
			calcularBonificacoesVariaveisDeDiamante(usuario, ano, mes);
		}

		return bonificacoes;
	}

	private void calcularBonificacoesVariaveisDeDiamante(Usuario usuario, Integer ano, Integer mes) {

		BonificacaoAuxiliar calculosDiamante = new MalaDiretaService().verificaSeMetaDeDiamanteFoiAtingida(usuario, result, hibernateUtil, validator, ano, mes);

		if (calculosDiamante.getQuantidadeGraduados() >= PontuacaoController.META_DIAMANTE_LINHAS_GRADUADOS && calculosDiamante.getPontuacaoDiamante().compareTo(PontuacaoController.META_DIAMANTE_PONTUACAO) >= 0) {

			ListaIngressoService listaIngressoService = new ListaIngressoService();
			listaIngressoService.setHibernateUtil(hibernateUtil);

			DateTime dataInicial = new DateTime(ano, mes, 1, 0, 0, 0);
			DateTime dataFinal = new DateTime(ano, mes, dataInicial.dayOfMonth().withMaximumValue().dayOfMonth().get(), 0, 0, 0);

			List<BonificacaoAuxiliar> bonificacoes = new ArrayList<BonificacaoAuxiliar>();

			for (Entry<Integer, MalaDireta> malaDiretaDoDiamante : calculosDiamante.getMalaDireta().entrySet()) {

				if (!malaDiretaDoDiamante.getValue().getUsuario().getId_Codigo().equals(usuario.getId_Codigo())) {

					TreeMap<Integer, MalaDireta> malaDireta = listaIngressoService.pesquisarMalaDiretaSemRecursividadeFiltrandoPorDataDeIngresso(malaDiretaDoDiamante.getValue().getUsuario().getId_Codigo(), dataInicial.toGregorianCalendar(), dataFinal.toGregorianCalendar());

					BigDecimal porcentagem = BigDecimal.ZERO;

					if (malaDiretaDoDiamante.getValue().getUsuario().isAtivo(dataInicial.toGregorianCalendar(), dataFinal.toGregorianCalendar())) {

						porcentagem = encontrarPorcentagemDeAcordoComKit(malaDiretaDoDiamante.getValue().getUsuario(), ano, mes).getPorcentagem();
					}

					for (Entry<Integer, MalaDireta> malaDiretaEntry : malaDireta.entrySet()) {

						calcularBonificacoesPorPorcentagem(ano, mes, bonificacoes, porcentagem, 1, malaDiretaEntry.getValue().getUsuario());
					}
				}
			}

			for (BonificacaoAuxiliar bonificacaoAuxiliar : bonificacoes) {

				BigDecimal porcentagemQueODiamanteIraReceber = PontuacaoController.PORCENTAGEM_QUE_A_ALABASTRUM_DISTRIBUI.subtract(bonificacaoAuxiliar.getPorcentagem());
				BigDecimal bonificacao = porcentagemQueODiamanteIraReceber.multiply(bonificacaoAuxiliar.getPontuacao()).divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP);
				bonificacaoAuxiliar.setBonificacao(bonificacao);
				bonificacaoAuxiliar.setComoFoiCalculado(Util.formatarBigDecimal(porcentagemQueODiamanteIraReceber) + "% de " + Util.formatarBigDecimal(bonificacaoAuxiliar.getPontuacao()));
			}

			this.result.include("bonificacoesDiamante", bonificacoes);
			result.include("somatorioBonificacoesDiamante", calcularSomatorioBonificacoes(bonificacoes));
		}

		this.result.include("metaDiamantePontuacao", PontuacaoController.META_DIAMANTE_PONTUACAO);
		this.result.include("metaDiamanteGraduados", PontuacaoController.META_DIAMANTE_LINHAS_GRADUADOS);
		this.result.include("pontuacaoAlcancadaPeloDiamante", calculosDiamante.getPontuacaoDiamante());
		this.result.include("graduadosAlcancadosPeloDiamante", calculosDiamante.getQuantidadeGraduados());
	}

	private void calcularBonificacoesFixas(Integer ano, Integer mes, List<BonificacaoAuxiliar> bonificacoes, MalaDireta malaDireta, Usuario usuario) {

		String kit = encontrarHistoricoKitDeAcordoComUsuarioEDataInformada(usuario, ano, mes);

		FixoIngresso fixoIngresso = new FixoIngresso();
		fixoIngresso.setData_referencia(new GregorianCalendar(ano, mes - 1, 1));
		fixoIngresso.setGeracao(String.valueOf(malaDireta.getNivel()));

		fixoIngresso = this.hibernateUtil.selecionar(fixoIngresso);

		BonificacaoAuxiliar bonificacaoAuxiliar = new BonificacaoAuxiliar();
		bonificacaoAuxiliar.setUsuario(malaDireta.getUsuario());
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

	private void calcularBonificacoesPorPorcentagem(Integer ano, Integer mes, List<BonificacaoAuxiliar> bonificacoes, BigDecimal porcentagem, Integer nivel, Usuario usuario) {

		BonificacaoAuxiliar bonificacaoAuxiliar = encontrarPontuacaoDeAcordoComKit(usuario, ano, mes);
		bonificacaoAuxiliar.setGeracao(nivel);
		bonificacaoAuxiliar.setPorcentagem(porcentagem);

		if (bonificacaoAuxiliar.getPontuacao().equals(BigDecimal.ZERO)) {

			bonificacaoAuxiliar.setBonificacao(BigDecimal.ZERO);

		} else {

			bonificacaoAuxiliar.setBonificacao(porcentagem.multiply(bonificacaoAuxiliar.getPontuacao()).divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP));
			bonificacaoAuxiliar.setComoFoiCalculado(Util.formatarBigDecimal(porcentagem) + "% de " + Util.formatarBigDecimal(bonificacaoAuxiliar.getPontuacao()));
		}

		bonificacoes.add(bonificacaoAuxiliar);
	}

	private void calcularBonificacaoFixaDeDiamante(Usuario usuarioDiamante, Integer ano, Integer mes, List<BonificacaoAuxiliar> bonificacoes) {

		List<Usuario> distribuidoresDoDiamante = buscarDistribuidoresDoDiamante(usuarioDiamante, ano, mes);

		for (Usuario usuario : distribuidoresDoDiamante) {

			if (!usuario.getId_Codigo().equals(usuarioDiamante.getId_Codigo())) {

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
	}

	private List<Usuario> buscarDistribuidoresDoDiamante(Usuario usuarioDiamante, Integer ano, Integer mes) {

		ListaIngressoService listaIngressoService = new ListaIngressoService();
		listaIngressoService.setHibernateUtil(hibernateUtil);

		DateTime dataInicial = new DateTime(ano, mes, 1, 0, 0, 0);
		DateTime dataFinal = new DateTime(ano, mes, dataInicial.dayOfMonth().withMaximumValue().dayOfMonth().get(), 0, 0, 0);

		Usuario usuarioFiltro = new Usuario();

		List<Criterion> restricoes = new ArrayList<Criterion>();
		restricoes.add(Restrictions.between("Dt_Ingresso", dataInicial.toGregorianCalendar(), dataFinal.toGregorianCalendar()));

		usuarioFiltro.setId_CR(usuarioDiamante.getId_Codigo());

		List<Usuario> distribuidoresDoDiamante = hibernateUtil.buscar(usuarioFiltro, restricoes);

		return distribuidoresDoDiamante;
	}

	private TreeMap<Integer, MalaDireta> gerarMalaDiretaDeAcordoComFiltros(Usuario usuario, Integer ano, Integer mes) {

		ListaIngressoService listaIngressoService = new ListaIngressoService();
		listaIngressoService.setHibernateUtil(hibernateUtil);

		DateTime dataInicial = new DateTime(ano, mes, 1, 0, 0, 0);
		DateTime dataFinal = new DateTime(ano, mes, dataInicial.dayOfMonth().withMaximumValue().dayOfMonth().get(), 0, 0, 0);

		TreeMap<Integer, MalaDireta> malaDireta = listaIngressoService.gerarMalaDiretaFiltrandoPorDataDeIngresso(usuario.getId_Codigo(), dataInicial.toGregorianCalendar(), dataFinal.toGregorianCalendar(), "id_Patroc");

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

		@SuppressWarnings("unchecked")
		List<Object> kit = hibernateUtil.buscaPorHQL("select hk.kit from HistoricoKit hk where id_Codigo = " + usuario.getId_Codigo() + " and data_referencia between '2014-01-01' and '" + ano + "-" + mes + "-01' order by id desc limit 1");

		if (Util.preenchido(kit)) {

			return (String) kit.get(0);

		} else {

			return KIT_INGRESSO_NAO_DEFINIDO_PARA_O_DISTRIBUIDOR;
		}
	}
}
