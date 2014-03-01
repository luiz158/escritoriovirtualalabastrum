package escritoriovirtualalabastrum.service;

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

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import escritoriovirtualalabastrum.auxiliar.BonificacaoAuxiliar;
import escritoriovirtualalabastrum.auxiliar.MalaDireta;
import escritoriovirtualalabastrum.controller.PontuacaoController;
import escritoriovirtualalabastrum.hibernate.HibernateUtil;
import escritoriovirtualalabastrum.modelo.FixoIngresso;
import escritoriovirtualalabastrum.modelo.PorcentagemIngresso;
import escritoriovirtualalabastrum.modelo.Usuario;
import escritoriovirtualalabastrum.util.Util;

public class BonificacaoIngressoService {

	private static final String KIT_INGRESSO_NAO_DEFINIDO_PARA_O_DISTRIBUIDOR = "Kit de ingresso não definido para o distribuidor";

	private HibernateUtil hibernateUtil;
	private Validator validator;
	private Result result;

	public BonificacaoAuxiliar calcularBonificacoes(Usuario usuario, Integer ano, Integer mes) {

		DateTime dataInicial = new DateTime(ano, mes, 1, 0, 0, 0);
		DateTime dataFinal = new DateTime(ano, mes, dataInicial.dayOfMonth().withMaximumValue().dayOfMonth().get(), 0, 0, 0);

		String kit = encontrarHistoricoKitDeAcordoComUsuarioEDataInformada(usuario, ano, mes);

		BigDecimal porcentagemUsuario = encontrarPorcentagemDeAcordoComKit(kit, usuario, ano, mes);

		BonificacaoAuxiliar informacoesBonificacoes = new BonificacaoAuxiliar();
		informacoesBonificacoes.setKit(kit);
		informacoesBonificacoes.setPorcentagem(porcentagemUsuario);

		List<BonificacaoAuxiliar> bonificacoes = new ArrayList<BonificacaoAuxiliar>();

		MalaDiretaService malaDiretaService = new MalaDiretaService();
		malaDiretaService.setHibernateUtil(hibernateUtil);
		TreeMap<Integer, MalaDireta> malaDiretaCompletaHash = malaDiretaService.gerarMalaDireta(MalaDiretaService.TODAS, usuario.getId_Codigo(), usuario.getId_Codigo());

		for (Entry<Integer, MalaDireta> malaDiretaEntry : malaDiretaCompletaHash.entrySet()) {

			MalaDireta malaDireta = malaDiretaEntry.getValue();

			Usuario usuarioMalaDireta = malaDireta.getUsuario();

			if ((usuarioMalaDireta.getDt_Ingresso().equals(dataInicial.toGregorianCalendar()) || usuarioMalaDireta.getDt_Ingresso().after(dataInicial.toGregorianCalendar())) && (usuarioMalaDireta.getDt_Ingresso().equals(dataFinal.toGregorianCalendar()) || usuarioMalaDireta.getDt_Ingresso().before(dataFinal.toGregorianCalendar()))) {

				if (malaDireta.getNivel() == 1 && malaDireta.getUsuario().getId_Patroc().equals(usuario.getId_Codigo())) {

					calcularBonificacoesPorPorcentagem(ano, mes, bonificacoes, porcentagemUsuario, malaDireta.getNivel(), usuarioMalaDireta);

				} else {

					calcularBonificacoesFixas(ano, mes, bonificacoes, malaDireta);
				}
			}
		}

		if (usuario.getPosAtual().toLowerCase().contains(MalaDiretaService.DIAMANTE.toLowerCase())) {

			informacoesBonificacoes.setIsDiamante(true);

			calcularBonificacaoFixaDeDiamante(usuario, ano, mes, bonificacoes);
			calcularBonificacoesVariaveisDeDiamante(usuario, ano, mes, malaDiretaCompletaHash, informacoesBonificacoes);
		}

		informacoesBonificacoes.setBonificacoes(bonificacoes);

		return informacoesBonificacoes;
	}

	private void calcularBonificacoesVariaveisDeDiamante(Usuario usuario, Integer ano, Integer mes, TreeMap<Integer, MalaDireta> malaDiretaCompletaHash, BonificacaoAuxiliar informacoesBonificacoes) {

		BonificacaoAuxiliar calculosDiamante = new MalaDiretaService().verificaSeMetaDeDiamanteFoiAtingida(usuario, result, hibernateUtil, validator, ano, mes, malaDiretaCompletaHash);

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

						String kit = encontrarHistoricoKitDeAcordoComUsuarioEDataInformada(malaDiretaDoDiamante.getValue().getUsuario(), ano, mes);

						porcentagem = encontrarPorcentagemDeAcordoComKit(kit, malaDiretaDoDiamante.getValue().getUsuario(), ano, mes);
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

			informacoesBonificacoes.setBonificacoesDiamante(bonificacoes);
		}

		informacoesBonificacoes.setMetaDiamantePontuacao(PontuacaoController.META_DIAMANTE_PONTUACAO);
		informacoesBonificacoes.setMetaDiamanteGraduados(PontuacaoController.META_DIAMANTE_LINHAS_GRADUADOS);
		informacoesBonificacoes.setPontuacaoAlcancadaPeloDiamante(calculosDiamante.getPontuacaoDiamante());
		informacoesBonificacoes.setGraduadosAlcancadosPeloDiamante(calculosDiamante.getQuantidadeGraduados());
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

		DateTime dataInicial = new DateTime(ano, mes, 1, 0, 0, 0);
		DateTime dataFinal = new DateTime(ano, mes, dataInicial.dayOfMonth().withMaximumValue().dayOfMonth().get(), 0, 0, 0);

		Usuario usuarioFiltro = new Usuario();

		List<Criterion> restricoes = new ArrayList<Criterion>();
		restricoes.add(Restrictions.between("Dt_Ingresso", dataInicial.toGregorianCalendar(), dataFinal.toGregorianCalendar()));

		usuarioFiltro.setId_CR(usuarioDiamante.getId_Codigo());

		List<Usuario> distribuidoresDoDiamante = hibernateUtil.buscar(usuarioFiltro, restricoes);

		return distribuidoresDoDiamante;
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

	private void calcularBonificacoesFixas(Integer ano, Integer mes, List<BonificacaoAuxiliar> bonificacoes, MalaDireta malaDireta) {

		String kit = encontrarHistoricoKitDeAcordoComUsuarioEDataInformada(malaDireta.getUsuario(), ano, mes);

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

	public BigDecimal calcularSomatorioBonificacoes(List<BonificacaoAuxiliar> bonificacoes) {

		BigDecimal somatorioBonificacao = new BigDecimal("0");

		if (Util.preenchido(bonificacoes)) {

			for (BonificacaoAuxiliar bonificacao : bonificacoes) {

				if (Util.preenchido(bonificacao.getBonificacao())) {

					somatorioBonificacao = somatorioBonificacao.add(bonificacao.getBonificacao());
				}
			}
		}

		return somatorioBonificacao;
	}

	private BigDecimal encontrarPorcentagemDeAcordoComKit(String kit, Usuario usuario, Integer ano, Integer mes) {

		if (kit.equals(KIT_INGRESSO_NAO_DEFINIDO_PARA_O_DISTRIBUIDOR)) {

			return BigDecimal.ZERO;
		}

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

	private String encontrarHistoricoKitDeAcordoComUsuarioEDataInformada(Usuario usuario, Integer ano, Integer mes) {

		@SuppressWarnings("unchecked")
		List<Object> kit = hibernateUtil.buscaPorHQL("select hk.kit from HistoricoKit hk where id_Codigo = " + usuario.getId_Codigo() + " and data_referencia between '2014-01-01' and '" + ano + "-" + mes + "-01' order by id desc limit 1");

		if (Util.preenchido(kit)) {

			return (String) kit.get(0);

		} else {

			return KIT_INGRESSO_NAO_DEFINIDO_PARA_O_DISTRIBUIDOR;
		}
	}

	public void setHibernateUtil(HibernateUtil hibernateUtil) {

		this.hibernateUtil = hibernateUtil;
	}

	public void setValidator(Validator validator) {
		this.validator = validator;
	}

	public void setResult(Result result) {
		this.result = result;
	}
}