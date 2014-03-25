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
import escritoriovirtualalabastrum.auxiliar.BonificacaoIngressoAuxiliar;
import escritoriovirtualalabastrum.auxiliar.MalaDireta;
import escritoriovirtualalabastrum.hibernate.HibernateUtil;
import escritoriovirtualalabastrum.modelo.FixoIngresso;
import escritoriovirtualalabastrum.modelo.PorcentagemIngresso;
import escritoriovirtualalabastrum.modelo.Usuario;
import escritoriovirtualalabastrum.util.Util;

public class BonificacaoIngressoService {

	private static final String KIT_INGRESSO_NAO_DEFINIDO_PARA_O_DISTRIBUIDOR = "Kit de ingresso não definido para o distribuidor";

	public static BigDecimal META_GRADUACAO = new BigDecimal("2000");
	public static BigDecimal META_DIAMANTE_PONTUACAO = new BigDecimal("60000");
	public static Integer META_DIAMANTE_LINHAS_GRADUADOS = 5;

	public static final BigDecimal PORCENTAGEM_QUE_A_ALABASTRUM_DISTRIBUI = new BigDecimal("40");

	private HibernateUtil hibernateUtil;
	private Validator validator;
	private Result result;

	public BonificacaoIngressoService(HibernateUtil hibernateUtil, Validator validator, Result result) {

		this.hibernateUtil = hibernateUtil;
		this.validator = validator;
		this.result = result;
	}

	public BonificacaoIngressoAuxiliar calcularBonificacoes(Usuario usuario, Integer ano, Integer mes) {

		DateTime dataInicial = new DateTime(ano, mes, 1, 0, 0, 0);
		DateTime dataFinal = new DateTime(ano, mes, dataInicial.dayOfMonth().withMaximumValue().dayOfMonth().get(), 0, 0, 0);

		String kit = encontrarHistoricoKitDeAcordoComUsuarioEDataInformada(usuario, ano, mes);

		BigDecimal porcentagemUsuario = encontrarPorcentagemDeAcordoComKit(kit, usuario, ano, mes);

		BonificacaoIngressoAuxiliar informacoesBonificacoes = new BonificacaoIngressoAuxiliar();
		informacoesBonificacoes.setKit(kit);
		informacoesBonificacoes.setPorcentagem(porcentagemUsuario);

		List<BonificacaoIngressoAuxiliar> bonificacoes = new ArrayList<BonificacaoIngressoAuxiliar>();

		TreeMap<Integer, MalaDireta> malaDiretaCompleta = new TreeMap<Integer, MalaDireta>();

		MalaDiretaService malaDiretaService = new MalaDiretaService(hibernateUtil);
		malaDiretaService.setHibernateUtil(hibernateUtil);
		malaDiretaService.gerarMalaDiretaDeAcordoComAtividade("id_Patroc", usuario.getId_Codigo(), dataInicial.toGregorianCalendar(), dataFinal.toGregorianCalendar(), 1, malaDiretaCompleta);

		for (Entry<Integer, MalaDireta> malaDiretaEntry : malaDiretaCompleta.entrySet()) {

			MalaDireta malaDireta = malaDiretaEntry.getValue();

			Usuario usuarioMalaDireta = malaDireta.getUsuario();

			if ((usuarioMalaDireta.getDt_Ingresso().equals(dataInicial.toGregorianCalendar()) || usuarioMalaDireta.getDt_Ingresso().after(dataInicial.toGregorianCalendar())) && (usuarioMalaDireta.getDt_Ingresso().equals(dataFinal.toGregorianCalendar()) || usuarioMalaDireta.getDt_Ingresso().before(dataFinal.toGregorianCalendar()))) {

				if (malaDireta.getNivel() == 1) {

					calcularBonificacoesPorPorcentagem(ano, mes, bonificacoes, porcentagemUsuario, malaDireta.getNivel(), usuarioMalaDireta);

				} else {

					calcularBonificacoesFixas(ano, mes, bonificacoes, malaDireta);
				}
			}
		}

		if (usuario.getPosAtual().toLowerCase().contains(MalaDiretaService.DIAMANTE.toLowerCase())) {

			informacoesBonificacoes.setIsDiamante(true);

			boolean alcancouMetaDiamante = false;

			BonificacaoIngressoAuxiliar calculosDiamante = new MalaDiretaService().verificaSeMetaDeDiamanteFoiAtingida(usuario, result, hibernateUtil, validator, ano, mes);
			if (calculosDiamante.getQuantidadeGraduados() >= META_DIAMANTE_LINHAS_GRADUADOS && calculosDiamante.getPontuacaoDiamante().compareTo(META_DIAMANTE_PONTUACAO) >= 0) {

				alcancouMetaDiamante = true;
			}

			calcularBonificacaoFixaDeDiamante(usuario, ano, mes, bonificacoes, alcancouMetaDiamante, malaDiretaCompleta);
			calcularBonificacoesVariaveisDeDiamante(usuario, ano, mes, informacoesBonificacoes, calculosDiamante, alcancouMetaDiamante);
		}

		informacoesBonificacoes.setBonificacoes(bonificacoes);
		informacoesBonificacoes.setMalaDireta(malaDiretaCompleta);

		return informacoesBonificacoes;
	}

	private void calcularBonificacoesVariaveisDeDiamante(Usuario usuario, Integer ano, Integer mes, BonificacaoIngressoAuxiliar informacoesBonificacoes, BonificacaoIngressoAuxiliar calculosDiamante, boolean alcancouMetaDiamante) {

		if (alcancouMetaDiamante) {

			ListaIngressoService listaIngressoService = new ListaIngressoService();
			listaIngressoService.setHibernateUtil(hibernateUtil);

			DateTime dataInicial = new DateTime(ano, mes, 1, 0, 0, 0);
			DateTime dataFinal = new DateTime(ano, mes, dataInicial.dayOfMonth().withMaximumValue().dayOfMonth().get(), 0, 0, 0);

			List<BonificacaoIngressoAuxiliar> bonificacoes = new ArrayList<BonificacaoIngressoAuxiliar>();

			TreeMap<Integer, MalaDireta> diamantesComMetasAlcancadas = new TreeMap<Integer, MalaDireta>();

			TreeMap<Integer, MalaDireta> malaDiretaIgnorandoDiamantesComMetasAlcancadas = gerarMalaDiretaIgnorandoDiamantesComMetasAlcancadas(usuario.getId_Codigo(), ano, mes, diamantesComMetasAlcancadas);

			for (Entry<Integer, MalaDireta> malaDiretaDoDiamante : malaDiretaIgnorandoDiamantesComMetasAlcancadas.entrySet()) {

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

			for (BonificacaoIngressoAuxiliar bonificacaoAuxiliar : bonificacoes) {

				BigDecimal porcentagemQueODiamanteIraReceber = PORCENTAGEM_QUE_A_ALABASTRUM_DISTRIBUI.subtract(bonificacaoAuxiliar.getPorcentagem());
				BigDecimal bonificacao = porcentagemQueODiamanteIraReceber.multiply(bonificacaoAuxiliar.getPontuacao()).divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP);
				bonificacaoAuxiliar.setBonificacao(bonificacao);
				bonificacaoAuxiliar.setComoFoiCalculado(Util.formatarBigDecimal(porcentagemQueODiamanteIraReceber) + "% de " + Util.formatarBigDecimal(bonificacaoAuxiliar.getPontuacao()));
			}

			informacoesBonificacoes.setBonificacoesDiamante(bonificacoes);
			informacoesBonificacoes.setDiamantesComMetasAlcancadas(diamantesComMetasAlcancadas);
		}

		informacoesBonificacoes.setMetaDiamantePontuacao(META_DIAMANTE_PONTUACAO);
		informacoesBonificacoes.setMetaDiamanteGraduados(META_DIAMANTE_LINHAS_GRADUADOS);
		informacoesBonificacoes.setPontuacaoAlcancadaPeloDiamante(calculosDiamante.getPontuacaoDiamante());
		informacoesBonificacoes.setGraduadosAlcancadosPeloDiamante(calculosDiamante.getQuantidadeGraduados());
		informacoesBonificacoes.setGraduados(calculosDiamante.getGraduados());
	}

	private TreeMap<Integer, MalaDireta> gerarMalaDiretaIgnorandoDiamantesComMetasAlcancadas(Integer codigoUsuario, Integer ano, Integer mes, TreeMap<Integer, MalaDireta> diamantesComMetasAlcancadas) {

		TreeMap<Integer, MalaDireta> malaDireta = new TreeMap<Integer, MalaDireta>();

		this.pesquisarMalaDiretaComRecursividadeIgnorandoDiamantesComMetasAlcancadas(codigoUsuario, malaDireta, 1, "id_Patroc", ano, mes, diamantesComMetasAlcancadas);

		return malaDireta;
	}

	private void pesquisarMalaDiretaComRecursividadeIgnorandoDiamantesComMetasAlcancadas(Integer codigo, TreeMap<Integer, MalaDireta> malaDireta, int nivel, String posicao, Integer ano, Integer mes, TreeMap<Integer, MalaDireta> diamantesComMetasAlcancadas) {

		DateTime dataInicial = new DateTime(ano, mes, 1, 0, 0, 0);
		DateTime dataFinal = new DateTime(ano, mes, dataInicial.dayOfMonth().withMaximumValue().dayOfMonth().get(), 0, 0, 0);

		Usuario usuario = new Usuario();

		try {

			Field field = usuario.getClass().getDeclaredField(posicao);

			field.setAccessible(true);

			field.set(usuario, codigo);
		}

		catch (Exception e) {

			e.printStackTrace();
		}

		List<Usuario> usuariosPatrocinados = hibernateUtil.buscar(usuario);

		for (Usuario usuarioPatrocinado : usuariosPatrocinados) {

			if (!codigo.equals(usuarioPatrocinado.getId_Codigo())) {

				if (!malaDireta.containsKey(usuarioPatrocinado.getId_Codigo())) {

					if (usuarioPatrocinado.getPosAtual().toLowerCase().contains(MalaDiretaService.DIAMANTE.toLowerCase())) {

						BonificacaoIngressoAuxiliar calculosDiamante = new MalaDiretaService().verificaSeMetaDeDiamanteFoiAtingida(usuarioPatrocinado, result, hibernateUtil, validator, ano, mes);

						if (usuarioPatrocinado.isAtivo(dataInicial.toGregorianCalendar(), dataFinal.toGregorianCalendar()) && calculosDiamante.getQuantidadeGraduados() >= META_DIAMANTE_LINHAS_GRADUADOS && calculosDiamante.getPontuacaoDiamante().compareTo(META_DIAMANTE_PONTUACAO) >= 0) {

							diamantesComMetasAlcancadas.put(usuarioPatrocinado.getId_Codigo(), new MalaDireta(usuarioPatrocinado, 0));

						} else {

							adicionarNoHashEContinuarPercorrendo(malaDireta, nivel, ano, mes, usuarioPatrocinado, diamantesComMetasAlcancadas);
						}

					} else {

						adicionarNoHashEContinuarPercorrendo(malaDireta, nivel, ano, mes, usuarioPatrocinado, diamantesComMetasAlcancadas);
					}
				}
			}
		}
	}

	private void adicionarNoHashEContinuarPercorrendo(TreeMap<Integer, MalaDireta> malaDireta, int nivel, Integer ano, Integer mes, Usuario usuarioPatrocinado, TreeMap<Integer, MalaDireta> diamantesComMetasAlcancadas) {

		malaDireta.put(usuarioPatrocinado.getId_Codigo(), new MalaDireta(usuarioPatrocinado, nivel));

		pesquisarMalaDiretaComRecursividadeIgnorandoDiamantesComMetasAlcancadas(usuarioPatrocinado.getId_Codigo(), malaDireta, nivel + 1, "id_Patroc", ano, mes, diamantesComMetasAlcancadas);
	}

	private void calcularBonificacaoFixaDeDiamante(Usuario usuarioDiamante, Integer ano, Integer mes, List<BonificacaoIngressoAuxiliar> bonificacoes, boolean alcancouMetaDiamante, TreeMap<Integer, MalaDireta> malaDireta) {

		if (alcancouMetaDiamante) {

			calcularBonificacoesDoDiamante(usuarioDiamante, ano, mes, bonificacoes);

			verificarSeOutrosDiamantesBateramSuasMetas(ano, mes, bonificacoes, malaDireta);
		}
	}

	private void verificarSeOutrosDiamantesBateramSuasMetas(Integer ano, Integer mes, List<BonificacaoIngressoAuxiliar> bonificacoes, TreeMap<Integer, MalaDireta> malaDireta) {

		List<Usuario> diamantesAbaixo = encontrarDiamantesAbaixo(malaDireta);

		for (Usuario diamanteAbaixo : diamantesAbaixo) {

			BonificacaoIngressoAuxiliar calculosDiamante = new MalaDiretaService().verificaSeMetaDeDiamanteFoiAtingida(diamanteAbaixo, result, hibernateUtil, validator, ano, mes);
			if (!(calculosDiamante.getQuantidadeGraduados() >= META_DIAMANTE_LINHAS_GRADUADOS && calculosDiamante.getPontuacaoDiamante().compareTo(META_DIAMANTE_PONTUACAO) >= 0)) {

				calcularBonificacoesDoDiamante(diamanteAbaixo, ano, mes, bonificacoes);
			}
		}
	}

	private List<Usuario> encontrarDiamantesAbaixo(TreeMap<Integer, MalaDireta> malaDireta) {

		List<Usuario> diamantesAbaixo = new ArrayList<Usuario>();

		for (Entry<Integer, MalaDireta> malaDiretaEntry : malaDireta.entrySet()) {

			if (malaDiretaEntry.getValue().getUsuario().getPosAtual().toLowerCase().contains(MalaDiretaService.DIAMANTE.toLowerCase())) {

				diamantesAbaixo.add(malaDiretaEntry.getValue().getUsuario());
			}
		}

		return diamantesAbaixo;
	}

	private void calcularBonificacoesDoDiamante(Usuario usuarioDiamante, Integer ano, Integer mes, List<BonificacaoIngressoAuxiliar> bonificacoes) {

		List<Usuario> distribuidoresDoDiamante = buscarDistribuidoresDoDiamante(usuarioDiamante, ano, mes);

		FixoIngresso fixoIngresso = new FixoIngresso();
		fixoIngresso.setData_referencia(new GregorianCalendar(ano, mes - 1, 1));
		fixoIngresso.setGeracao("Diamante");

		fixoIngresso = this.hibernateUtil.selecionar(fixoIngresso);

		for (Usuario usuario : distribuidoresDoDiamante) {

			if (!usuario.getId_Codigo().equals(usuarioDiamante.getId_Codigo())) {

				String kit = encontrarHistoricoKitDeAcordoComUsuarioEDataInformada(usuario, ano, mes);

				BonificacaoIngressoAuxiliar bonificacaoAuxiliar = new BonificacaoIngressoAuxiliar();
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

	private void calcularBonificacoesPorPorcentagem(Integer ano, Integer mes, List<BonificacaoIngressoAuxiliar> bonificacoes, BigDecimal porcentagem, Integer nivel, Usuario usuario) {

		String kit = encontrarHistoricoKitDeAcordoComUsuarioEDataInformada(usuario, ano, mes);

		BonificacaoIngressoAuxiliar bonificacaoAuxiliar = new BonificacaoIngressoAuxiliar();
		bonificacaoAuxiliar.setKit(kit);
		bonificacaoAuxiliar.setUsuario(usuario);
		bonificacaoAuxiliar.setGeracao(nivel);
		bonificacaoAuxiliar.setPorcentagem(porcentagem);
		bonificacaoAuxiliar.setPontuacao(encontrarPontuacaoDeAcordoComKit(kit, ano, mes));

		if (bonificacaoAuxiliar.getPontuacao().equals(BigDecimal.ZERO)) {

			bonificacaoAuxiliar.setBonificacao(BigDecimal.ZERO);

		} else {

			bonificacaoAuxiliar.setBonificacao(porcentagem.multiply(bonificacaoAuxiliar.getPontuacao()).divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP));
			bonificacaoAuxiliar.setComoFoiCalculado(Util.formatarBigDecimal(porcentagem) + "% de " + Util.formatarBigDecimal(bonificacaoAuxiliar.getPontuacao()));
		}

		bonificacoes.add(bonificacaoAuxiliar);
	}

	private void calcularBonificacoesFixas(Integer ano, Integer mes, List<BonificacaoIngressoAuxiliar> bonificacoes, MalaDireta malaDireta) {

		String kit = encontrarHistoricoKitDeAcordoComUsuarioEDataInformada(malaDireta.getUsuario(), ano, mes);

		FixoIngresso fixoIngresso = new FixoIngresso();
		fixoIngresso.setData_referencia(new GregorianCalendar(ano, mes - 1, 1));
		fixoIngresso.setGeracao(String.valueOf(malaDireta.getNivel()));

		fixoIngresso = this.hibernateUtil.selecionar(fixoIngresso);

		BonificacaoIngressoAuxiliar bonificacaoAuxiliar = new BonificacaoIngressoAuxiliar();
		bonificacaoAuxiliar.setUsuario(malaDireta.getUsuario());
		bonificacaoAuxiliar.setKit(kit);
		bonificacaoAuxiliar.setGeracao(malaDireta.getNivel());
		bonificacaoAuxiliar.setComoFoiCalculado("Ganho de " + malaDireta.getNivel() + "ª Geração");

		try {

			Field field = fixoIngresso.getClass().getDeclaredField(kit);
			field.setAccessible(true);
			bonificacaoAuxiliar.setBonificacao((BigDecimal) field.get(fixoIngresso));

		} catch (Exception e) {

			bonificacaoAuxiliar.setBonificacao(BigDecimal.ZERO);
		}

		bonificacoes.add(bonificacaoAuxiliar);
	}

	private BigDecimal encontrarPontuacaoDeAcordoComKit(String kit, Integer ano, Integer mes) {

		if (kit.equals(KIT_INGRESSO_NAO_DEFINIDO_PARA_O_DISTRIBUIDOR)) {

			return BigDecimal.ZERO;
		}

		PorcentagemIngresso porcentagemIngresso = new PorcentagemIngresso();
		porcentagemIngresso.setData_referencia(new GregorianCalendar(ano, mes - 1, 1));

		porcentagemIngresso = this.hibernateUtil.selecionar(porcentagemIngresso);

		try {

			Field field = porcentagemIngresso.getClass().getDeclaredField(kit + "_pontuacao");

			field.setAccessible(true);

			return (BigDecimal) field.get(porcentagemIngresso);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public BigDecimal calcularSomatorioBonificacoes(List<BonificacaoIngressoAuxiliar> bonificacoes) {

		BigDecimal somatorioBonificacao = new BigDecimal("0");

		if (Util.preenchido(bonificacoes)) {

			for (BonificacaoIngressoAuxiliar bonificacao : bonificacoes) {

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
}