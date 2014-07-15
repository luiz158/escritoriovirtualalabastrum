package escritoriovirtualalabastrum.service;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import escritoriovirtualalabastrum.auxiliar.BonificacaoGraduacaoAuxiliar;
import escritoriovirtualalabastrum.auxiliar.MalaDireta;
import escritoriovirtualalabastrum.controller.PontuacaoController;
import escritoriovirtualalabastrum.hibernate.HibernateUtil;
import escritoriovirtualalabastrum.modelo.ControlePedido;
import escritoriovirtualalabastrum.modelo.Usuario;
import escritoriovirtualalabastrum.sessao.SessaoUsuario;
import escritoriovirtualalabastrum.util.Util;

public class BonificacaoGraduacaoService {

	private HibernateUtil hibernateUtil;
	private Validator validator;
	private Result result;
	private BigDecimal somatorioPedidosRede = BigDecimal.ZERO;
	private List<Integer> usuariosQueDeramAlgumaBonificacao = new ArrayList<Integer>();

	public BonificacaoGraduacaoService(HibernateUtil hibernateUtil, Validator validator, Result result) {

		this.hibernateUtil = hibernateUtil;
		this.validator = validator;
		this.result = result;
	}

	public BonificacaoGraduacaoAuxiliar calcularBonificacoes(Usuario usuario, Integer ano, Integer mes, BigDecimal pontuacao, Integer quantidadeGraduados, TreeMap<Integer, MalaDireta> malaDireta) {

		BonificacaoGraduacaoAuxiliar bonificacaoGraduacaoAuxiliar = new BonificacaoGraduacaoAuxiliar();

		GraduacaoService graduacaoService = new GraduacaoService();

		String graduacao = graduacaoService.verificaGraduacao(pontuacao, quantidadeGraduados);
		BigDecimal porcentagem = graduacaoService.calcularPorcentagemDeAcordoComGraduacao(graduacao);

		if (porcentagem.compareTo(BigDecimal.ZERO) > 0) {

			DateTime dataInicial = new DateTime(ano, mes, 1, 0, 0, 0);
			DateTime dataFinal = new DateTime(ano, mes, dataInicial.dayOfMonth().withMaximumValue().dayOfMonth().get(), 0, 0, 0);

			HashMap<Usuario, BigDecimal> graduadosEPorcentagensComPedidos = new HashMap<Usuario, BigDecimal>();

			BigDecimal porcentagemFinalGanha = calcularPorcentagemFinalGanha(usuario, porcentagem, dataInicial, dataFinal, graduadosEPorcentagensComPedidos, ano, mes);

			BigDecimal bonificacao = calcularBonificacoesDeAcordoComNivel(usuario, ano, mes, malaDireta, dataInicial, dataFinal, porcentagem, porcentagemFinalGanha);

			bonificacaoGraduacaoAuxiliar.setPorcentagemUsuarioLogado(porcentagem);
			bonificacaoGraduacaoAuxiliar.setSomatorioPedidosrede(this.somatorioPedidosRede);
			bonificacaoGraduacaoAuxiliar.setGraduadosEPorcentagens(graduadosEPorcentagensComPedidos);
			bonificacaoGraduacaoAuxiliar.setBonificacao(bonificacao);
			bonificacaoGraduacaoAuxiliar.setUsuariosQueDeramAlgumaBonificacao(this.usuariosQueDeramAlgumaBonificacao);
		}

		return bonificacaoGraduacaoAuxiliar;
	}

	public List<ControlePedido> buscarPedidosDaRede(Usuario usuario, Integer ano, Integer mes, TreeMap<Integer, MalaDireta> malaDireta, List<Integer> usuariosQueDeramAlgumaBonificacao) {

		List<Integer> idsRede = new ArrayList<Integer>();

		for (Entry<Integer, MalaDireta> malaDiretaEntry : malaDireta.entrySet()) {

			if (!malaDiretaEntry.getValue().getUsuario().getId_Codigo().equals(usuario.getId_Codigo())) {

				if (usuariosQueDeramAlgumaBonificacao != null) {

					if (usuariosQueDeramAlgumaBonificacao.contains(malaDiretaEntry.getValue().getUsuario().getId_Codigo())) {

						idsRede.add(malaDiretaEntry.getValue().getUsuario().getId_Codigo());
					}
				} else {

					idsRede.add(malaDiretaEntry.getValue().getUsuario().getId_Codigo());
				}
			}
		}

		if (Util.preenchido(idsRede)) {

			DateTime dataInicial = new DateTime(ano, mes, 1, 0, 0, 0);
			DateTime dataFinal = new DateTime(ano, mes, dataInicial.dayOfMonth().withMaximumValue().dayOfMonth().get(), 0, 0, 0);

			List<Criterion> restricoes = new ArrayList<Criterion>();
			restricoes.add(Restrictions.between("pedData", dataInicial.toGregorianCalendar(), dataFinal.toGregorianCalendar()));
			restricoes.add(Restrictions.in("id_Codigo", idsRede));
			restricoes.add(Restrictions.ne("BaseCalculo", BigDecimal.ZERO));

			List<ControlePedido> pedidos = this.hibernateUtil.buscar(new ControlePedido(), restricoes);

			for (ControlePedido pedido : pedidos) {

				Usuario usuarioDoPedido = this.hibernateUtil.selecionar(new Usuario(pedido.getId_Codigo()));

				pedido.setUsuario(usuarioDoPedido);
			}

			return pedidos;
		}

		return new ArrayList<ControlePedido>();
	}

	private BigDecimal calcularBonificacoesDeAcordoComNivel(Usuario usuario, Integer ano, Integer mes, TreeMap<Integer, MalaDireta> malaDireta, DateTime dataInicial, DateTime dataFinal, BigDecimal porcentagemUsuarioLogado, BigDecimal porcentagemCalculada) {

		BigDecimal bonificacao = BigDecimal.ZERO;

		for (Entry<Integer, MalaDireta> malaDiretaEntry : malaDireta.entrySet()) {

			if (!malaDiretaEntry.getValue().getUsuario().getId_Codigo().equals(usuario.getId_Codigo())) {

				BigDecimal compraPessoal = new BonificacaoCompraPessoalService(hibernateUtil).calcularTotalBaseCalculo(malaDiretaEntry.getValue().getUsuario(), ano, mes);

				if (compraPessoal.compareTo(BigDecimal.ZERO) != 0) {

					this.somatorioPedidosRede = this.somatorioPedidosRede.add(compraPessoal);

					BigDecimal pontuacao = calcularPontuacaoDaRedeDoPossivelGraduado(dataInicial, dataFinal, malaDiretaEntry.getValue().getUsuario());

					Integer quantidadeGraduados = calcularQuantidadeGraduadosPossivelGraduado(dataInicial, dataFinal, malaDiretaEntry.getValue().getUsuario());

					BigDecimal porcentagem = new GraduacaoService().calcularPorcentagemDeAcordoComGraduacao(new GraduacaoService().verificaGraduacao(pontuacao, quantidadeGraduados));

					BigDecimal porcentagemFinal;

					if (malaDiretaEntry.getValue().getNivel() == 1) {

						porcentagemFinal = porcentagemUsuarioLogado.subtract(porcentagem);

					} else {

						porcentagemFinal = porcentagemCalculada.subtract(porcentagem);
					}

					if (porcentagemFinal.compareTo(BigDecimal.ZERO) < 0) {

						porcentagemFinal = BigDecimal.ZERO;
					}

					if (porcentagemFinal.compareTo(BigDecimal.ZERO) > 0) {

						usuariosQueDeramAlgumaBonificacao.add(malaDiretaEntry.getValue().getUsuario().getId_Codigo());
					}

					bonificacao = bonificacao.add(porcentagemFinal.multiply(compraPessoal).divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP));
				}
			}
		}

		return bonificacao;
	}

	private BigDecimal calcularPorcentagemFinalGanha(Usuario usuario, BigDecimal porcentagem, DateTime dataInicial, DateTime dataFinal, HashMap<Usuario, BigDecimal> graduadosEPorcentagensComPedidos, Integer ano, Integer mes) {

		HashMap<Usuario, BigDecimal> graduadosEPorcentagens = new HashMap<Usuario, BigDecimal>();

		descerMalaDiretaAteEncontrarGraduados(usuario.getId_Codigo(), new TreeMap<Integer, MalaDireta>(), 1, "id_Patroc", dataInicial, dataFinal, graduadosEPorcentagens);

		BigDecimal porcentagemFinalGanha = BigDecimal.ZERO;

		if (graduadosEPorcentagens.size() > 0) {

			for (Entry<Usuario, BigDecimal> graduadoEPorcentagem : graduadosEPorcentagens.entrySet()) {

				MalaDiretaService malaDiretaService = new MalaDiretaService();
				malaDiretaService.setHibernateUtil(hibernateUtil);
				malaDiretaService.setValidator(validator);

				TreeMap<Integer, MalaDireta> malaDireta = new TreeMap<Integer, MalaDireta>();
				malaDiretaService.pesquisarMalaDiretaComRecursividade(graduadoEPorcentagem.getKey().getId_Codigo(), malaDireta, 1, "id_Patroc");

				List<ControlePedido> pedidosDaRede = buscarPedidosDaRede(graduadoEPorcentagem.getKey(), ano, mes, malaDireta, null);

				if (pedidosDaRede.size() != 0) {

					graduadosEPorcentagensComPedidos.put(graduadoEPorcentagem.getKey(), graduadoEPorcentagem.getValue());
				}
			}

			if (graduadosEPorcentagensComPedidos.size() > 0) {

				BigDecimal porcentagemTotalDoUsuarioLogado = porcentagem.multiply(new BigDecimal(graduadosEPorcentagensComPedidos.size()));

				BigDecimal porcentagemTotalDosGraduados = BigDecimal.ZERO;

				for (Entry<Usuario, BigDecimal> graduadoEPorcentagem : graduadosEPorcentagensComPedidos.entrySet()) {

					porcentagemTotalDosGraduados = porcentagemTotalDosGraduados.add(graduadoEPorcentagem.getValue());
				}

				porcentagemFinalGanha = porcentagemTotalDoUsuarioLogado.subtract(porcentagemTotalDosGraduados);

			} else {

				porcentagemFinalGanha = porcentagem;
			}

		} else {

			porcentagemFinalGanha = porcentagem;
		}

		return porcentagemFinalGanha;
	}

	public void descerMalaDiretaAteEncontrarGraduados(Integer codigo, TreeMap<Integer, MalaDireta> malaDireta, int nivel, String posicao, DateTime dataInicial, DateTime dataFinal, HashMap<Usuario, BigDecimal> graduadosEPorcentagens) {

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

					malaDireta.put(usuarioPatrocinado.getId_Codigo(), new MalaDireta(usuarioPatrocinado, nivel));

					if (GraduacaoService.GRADUACOES.contains(usuarioPatrocinado.getPosAtual().toLowerCase())) {

						BigDecimal pontuacao = calcularPontuacaoDaRedeDoPossivelGraduado(dataInicial, dataFinal, usuarioPatrocinado);

						Integer quantidadeGraduados = calcularQuantidadeGraduadosPossivelGraduado(dataInicial, dataFinal, usuarioPatrocinado);

						GraduacaoService graduacaoService = new GraduacaoService();

						String graduacao = graduacaoService.verificaGraduacao(pontuacao, quantidadeGraduados);
						BigDecimal porcentagem = graduacaoService.calcularPorcentagemDeAcordoComGraduacao(graduacao);

						if (porcentagem.compareTo(BigDecimal.ZERO) > 0 && usuarioPatrocinado.isAtivo(dataInicial.toGregorianCalendar(), dataFinal.toGregorianCalendar())) {

							graduadosEPorcentagens.put(usuarioPatrocinado, porcentagem);

						} else {

							descerMalaDiretaAteEncontrarGraduados(usuarioPatrocinado.getId_Codigo(), malaDireta, nivel + 1, "id_Patroc", dataInicial, dataFinal, graduadosEPorcentagens);
						}

					} else {

						descerMalaDiretaAteEncontrarGraduados(usuarioPatrocinado.getId_Codigo(), malaDireta, nivel + 1, "id_Patroc", dataInicial, dataFinal, graduadosEPorcentagens);
					}
				}
			}
		}
	}

	private Integer calcularQuantidadeGraduadosPossivelGraduado(DateTime dataInicial, DateTime dataFinal, Usuario usuario) {

		MalaDiretaService malaDiretaService = new MalaDiretaService();
		malaDiretaService.setHibernateUtil(hibernateUtil);
		malaDiretaService.setValidator(validator);

		Integer quantidadeGraduados = malaDiretaService.calcularQuantidadeGraduados(usuario, result, hibernateUtil, validator, dataInicial, dataFinal, new HashMap<Integer, MalaDireta>());

		return quantidadeGraduados;
	}

	private BigDecimal calcularPontuacaoDaRedeDoPossivelGraduado(DateTime dataInicial, DateTime dataFinal, Usuario usuarioPatrocinado) {

		MalaDiretaService malaDiretaService = new MalaDiretaService();
		malaDiretaService.setHibernateUtil(hibernateUtil);
		malaDiretaService.setValidator(validator);

		TreeMap<Integer, MalaDireta> malaDiretaDoPossivelGraduado = new TreeMap<Integer, MalaDireta>();
		malaDiretaService.pesquisarMalaDiretaComRecursividade(usuarioPatrocinado.getId_Codigo(), malaDiretaDoPossivelGraduado, 1, "id_Patroc");

		SessaoUsuario sessaoUsuario = new SessaoUsuario();
		sessaoUsuario.login(usuarioPatrocinado);

		BigDecimal pontuacao = new PontuacaoController(result, hibernateUtil, sessaoUsuario, validator).gerarRelatorioPontuacaoRetornandoPontuacaoDaRede(dataInicial.toGregorianCalendar(), dataFinal.toGregorianCalendar(), malaDiretaDoPossivelGraduado, PontuacaoController.TODOS, PontuacaoController.TODOS, usuarioPatrocinado.getId_Codigo());

		return pontuacao;
	}
}