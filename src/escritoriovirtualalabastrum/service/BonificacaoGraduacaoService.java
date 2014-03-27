package escritoriovirtualalabastrum.service;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.joda.time.DateTime;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import escritoriovirtualalabastrum.auxiliar.BonificacaoGraduacaoAuxiliar;
import escritoriovirtualalabastrum.auxiliar.MalaDireta;
import escritoriovirtualalabastrum.controller.PontuacaoController;
import escritoriovirtualalabastrum.hibernate.HibernateUtil;
import escritoriovirtualalabastrum.modelo.Usuario;
import escritoriovirtualalabastrum.sessao.SessaoUsuario;

public class BonificacaoGraduacaoService {

	private HibernateUtil hibernateUtil;
	private Validator validator;
	private Result result;

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

			HashMap<Usuario, BigDecimal> graduadosEPorcentagens = new HashMap<Usuario, BigDecimal>();

			BigDecimal porcentagemFinalGanha = calcularPorcentagemFinalGanha(usuario, porcentagem, dataInicial, dataFinal, graduadosEPorcentagens);

			BigDecimal somatorioPedidosrede = calcularSomatorioPedidosRede(usuario, ano, mes, malaDireta);

			bonificacaoGraduacaoAuxiliar.setPorcentagemUsuarioLogado(porcentagem);
			bonificacaoGraduacaoAuxiliar.setSomatorioPedidosrede(somatorioPedidosrede);
			bonificacaoGraduacaoAuxiliar.setGraduadosEPorcentagens(graduadosEPorcentagens);
			bonificacaoGraduacaoAuxiliar.setBonificacao(porcentagemFinalGanha.multiply(somatorioPedidosrede).divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP));
		}

		return bonificacaoGraduacaoAuxiliar;
	}

	private BigDecimal calcularSomatorioPedidosRede(Usuario usuario, Integer ano, Integer mes, TreeMap<Integer, MalaDireta> malaDireta) {

		BigDecimal somatorioPedidosrede = BigDecimal.ZERO;

		for (Entry<Integer, MalaDireta> malaDiretaEntry : malaDireta.entrySet()) {

			if (!malaDiretaEntry.getValue().getUsuario().getId_Codigo().equals(usuario.getId_Codigo())) {

				BigDecimal compraPessoal = new BonificacaoCompraPessoalService(hibernateUtil).calcularTotalBaseCalculo(malaDiretaEntry.getValue().getUsuario(), ano, mes);
				somatorioPedidosrede = somatorioPedidosrede.add(compraPessoal);
			}
		}

		return somatorioPedidosrede;
	}

	private BigDecimal calcularPorcentagemFinalGanha(Usuario usuario, BigDecimal porcentagem, DateTime dataInicial, DateTime dataFinal, HashMap<Usuario, BigDecimal> graduadosEPorcentagens) {

		descerMalaDiretaAteEncontrarGraduados(usuario.getId_Codigo(), new TreeMap<Integer, MalaDireta>(), 1, "id_Patroc", dataInicial, dataFinal, graduadosEPorcentagens);

		BigDecimal porcentagemFinalGanha = BigDecimal.ZERO;

		if (graduadosEPorcentagens.size() > 0) {

			BigDecimal porcentagemTotalDoUsuarioLogado = porcentagem.multiply(new BigDecimal(graduadosEPorcentagens.size()));

			BigDecimal porcentagemTotalDosGraduados = BigDecimal.ZERO;

			for (Entry<Usuario, BigDecimal> graduadoEPorcentagem : graduadosEPorcentagens.entrySet()) {

				porcentagemTotalDosGraduados = porcentagemTotalDosGraduados.add(graduadoEPorcentagem.getValue());
			}

			porcentagemFinalGanha = porcentagemTotalDoUsuarioLogado.subtract(porcentagemTotalDosGraduados);

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

						if (porcentagem.compareTo(BigDecimal.ZERO) > 0) {

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