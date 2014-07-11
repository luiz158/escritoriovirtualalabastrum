package escritoriovirtualalabastrum.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;

import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.ValidationMessage;
import escritoriovirtualalabastrum.anotacoes.Funcionalidade;
import escritoriovirtualalabastrum.auxiliar.BonificacaoIngressoAuxiliar;
import escritoriovirtualalabastrum.auxiliar.MalaDireta;
import escritoriovirtualalabastrum.auxiliar.PontuacaoAuxiliar;
import escritoriovirtualalabastrum.hibernate.HibernateUtil;
import escritoriovirtualalabastrum.modelo.Pontuacao;
import escritoriovirtualalabastrum.modelo.Usuario;
import escritoriovirtualalabastrum.service.MalaDiretaService;
import escritoriovirtualalabastrum.sessao.SessaoUsuario;
import escritoriovirtualalabastrum.util.Util;

@Resource
public class PontuacaoController {

	public static final String TODOS = "Todos";

	private Result result;
	private HibernateUtil hibernateUtil;
	private SessaoUsuario sessaoUsuario;
	private Validator validator;

	public PontuacaoController(Result result, HibernateUtil hibernateUtil, SessaoUsuario sessaoUsuario, Validator validator) {

		this.result = result;
		this.hibernateUtil = hibernateUtil;
		this.sessaoUsuario = sessaoUsuario;
		this.validator = validator;
	}

	@Funcionalidade
	public void acessarTelaPontuacao() {

		DateTime hoje = new DateTime();
		DateTime primeiroDiaDoMesAtual = hoje.withDayOfMonth(1);

		result.include("primeiroDiaMes", primeiroDiaDoMesAtual.toGregorianCalendar());
		result.include("dataAtual", hoje.toGregorianCalendar());

		result.include("posicoes", new MalaDiretaService().obterPosicoes());
	}

	@Funcionalidade
	public void gerarRelatorioVindoDaTelaInicial() {

		DateTime hoje = new DateTime();
		DateTime primeiroDiaDoMesAtual = hoje.withDayOfMonth(1);

		primeiroDiaDoMesAtual = primeiroDiaDoMesAtual.withMillisOfDay(0);

		gerarRelatorioPontuacao(MalaDiretaService.TODAS, this.sessaoUsuario.getUsuario().getId_Codigo(), primeiroDiaDoMesAtual.toGregorianCalendar(), hoje.toGregorianCalendar(), TODOS, TODOS);

		result.forwardTo("/WEB-INF/jsp/pontuacao/gerarRelatorioPontuacao.jsp");
	}

	@Funcionalidade
	public void gerarRelatorioPontuacao(String posicao, Integer codigoUsuario, GregorianCalendar dataInicial, GregorianCalendar dataFinal, String possuiMovimentacao, String ativo) {

		Integer codigoUsuarioLogado = this.sessaoUsuario.getUsuario().getId_Codigo();

		gerarMalaDiretaECalcularPontuacaoDaRede(posicao, codigoUsuario, dataInicial, dataFinal, possuiMovimentacao, ativo, codigoUsuarioLogado);

		result.include("posicaoConsiderada", new MalaDiretaService().obterPosicoes().get(posicao));
		result.include("possuiMovimentacao", possuiMovimentacao);
		result.include("ativo", ativo);
	}

	public BonificacaoIngressoAuxiliar gerarMalaDiretaECalcularPontuacaoDaRede(String posicao, Integer codigoUsuario, GregorianCalendar dataInicial, GregorianCalendar dataFinal, String possuiMovimentacao, String ativo, Integer codigoUsuarioLogado) {

		MalaDiretaService malaDiretaService = new MalaDiretaService();
		malaDiretaService.setHibernateUtil(hibernateUtil);
		malaDiretaService.setValidator(validator);

		TreeMap<Integer, MalaDireta> malaDireta = malaDiretaService.gerarMalaDireta(posicao, codigoUsuario, codigoUsuarioLogado);

		BigDecimal pontuacaoDiamante = gerarRelatorioPontuacaoRetornandoPontuacaoDaRede(dataInicial, dataFinal, malaDireta, possuiMovimentacao, ativo, codigoUsuario);

		BonificacaoIngressoAuxiliar bonificacaoAuxiliar = new BonificacaoIngressoAuxiliar();
		bonificacaoAuxiliar.setMalaDireta(malaDireta);
		bonificacaoAuxiliar.setPontuacaoDiamante(pontuacaoDiamante);

		return bonificacaoAuxiliar;
	}

	private void calcularPontuacaoPessoalUsuarioPesquisado(GregorianCalendar dataInicial, GregorianCalendar dataFinal, Usuario usuarioPesquisado) {

		List<Criterion> restricoes = definirRestricoesDatas(dataInicial, dataFinal);

		PontuacaoAuxiliar pontuacaoAuxiliar = calcularPontuacoes(restricoes, new MalaDireta(usuarioPesquisado, 0));

		result.include("pontuacaoPessoalUsuarioPesquisado", pontuacaoAuxiliar.getTotal());

		if (pontuacaoAuxiliar.isAtivo()) {

			result.include("situacaoPessoalAtividade", "Ativo");
		}

		else {

			result.include("situacaoPessoalAtividade", "Inativo");
		}
	}

	public List<Criterion> definirRestricoesDatas(GregorianCalendar dataInicial, GregorianCalendar dataFinal) {

		DateTime hoje = new DateTime();
		DateTime primeiroDiaDoMesAtual = hoje.withDayOfMonth(1);

		primeiroDiaDoMesAtual = primeiroDiaDoMesAtual.withMillisOfDay(0);

		if (Util.vazio(dataInicial)) {

			dataInicial = primeiroDiaDoMesAtual.toGregorianCalendar();
		}

		if (Util.vazio(dataFinal)) {

			dataFinal = hoje.toGregorianCalendar();
		}

		int mesDataInicial = dataInicial.get(GregorianCalendar.MONTH);
		int mesDataFinal = dataFinal.get(GregorianCalendar.MONTH);

		if (mesDataInicial != mesDataFinal) {

			validator.add(new ValidationMessage("Só é possível consultar o período de 1 mês por vez", "Erro"));
			validator.onErrorRedirectTo(this).acessarTelaPontuacao();
		}

		List<Criterion> restricoes = new ArrayList<Criterion>();

		restricoes.add(Restrictions.between("Dt_Pontos", dataInicial, dataFinal));

		return restricoes;
	}

	public BigDecimal gerarRelatorioPontuacaoRetornandoPontuacaoDaRede(GregorianCalendar dataInicial, GregorianCalendar dataFinal, TreeMap<Integer, MalaDireta> malaDireta, String possuiMovimentacao, String ativo, Integer codigoUsuario) {

		List<PontuacaoAuxiliar> pontuacoesConformeMovimentacoes = new ArrayList<PontuacaoAuxiliar>();
		List<PontuacaoAuxiliar> pontuacoesConformeAtividade = new ArrayList<PontuacaoAuxiliar>();

		List<Criterion> restricoes = definirRestricoesDatas(dataInicial, dataFinal);

		Usuario usuarioPesquisado = new Usuario();
		usuarioPesquisado.setId_Codigo(codigoUsuario);

		usuarioPesquisado = this.hibernateUtil.selecionar(usuarioPesquisado, MatchMode.EXACT);
		result.include("usuarioPesquisado", usuarioPesquisado);

		calcularPontuacaoPessoalUsuarioPesquisado(dataInicial, dataFinal, usuarioPesquisado);

		malaDireta.put(0, new MalaDireta(usuarioPesquisado, 0));

		for (Entry<Integer, MalaDireta> usuario : malaDireta.entrySet()) {

			PontuacaoAuxiliar pontuacaoAuxiliar = calcularPontuacoes(restricoes, usuario.getValue());

			adicionarConformeMovimentacoes(possuiMovimentacao, pontuacoesConformeMovimentacoes, pontuacaoAuxiliar);
		}

		adicionarConformeAtividade(ativo, pontuacoesConformeMovimentacoes, pontuacoesConformeAtividade);

		contarAtivosDiretos(pontuacoesConformeAtividade);
		contarTodosAtivos(pontuacoesConformeAtividade);

		BigDecimal pontuacaoRede = calcularPontuacaoRede(pontuacoesConformeAtividade);

		result.include("pontuacaoRede", pontuacaoRede);
		result.include("relatorioPontuacao", pontuacoesConformeAtividade);
		result.include("quantidadeElementos", pontuacoesConformeAtividade.size());
		result.include("dataInicialPesquisada", dataInicial);
		result.include("dataFinalPesquisada", dataFinal);

		return pontuacaoRede;
	}

	private void contarAtivosDiretos(List<PontuacaoAuxiliar> pontuacoesConformeAtividade) {

		Integer ativosDiretos = 0;

		for (PontuacaoAuxiliar pontuacaoAuxiliar : pontuacoesConformeAtividade) {

			if (pontuacaoAuxiliar.isAtivo() && pontuacaoAuxiliar.getMalaDireta().getNivel() == 1) {

				ativosDiretos++;
			}
		}

		result.include("ativosDiretos", ativosDiretos);
	}

	private void contarTodosAtivos(List<PontuacaoAuxiliar> pontuacoesConformeAtividade) {

		Integer todosAtivos = 0;

		for (PontuacaoAuxiliar pontuacaoAuxiliar : pontuacoesConformeAtividade) {

			if (pontuacaoAuxiliar.isAtivo()) {

				todosAtivos++;
			}
		}

		result.include("todosAtivos", todosAtivos);
	}

	private BigDecimal calcularPontuacaoRede(List<PontuacaoAuxiliar> pontuacoesConformeAtividade) {

		BigDecimal pontuacaoRede = BigDecimal.ZERO;

		for (PontuacaoAuxiliar pontuacaoAuxiliar : pontuacoesConformeAtividade) {

			pontuacaoRede = pontuacaoRede.add(pontuacaoAuxiliar.getTotal());
		}
		return pontuacaoRede;
	}

	private void adicionarConformeAtividade(String ativo, List<PontuacaoAuxiliar> pontuacoes, List<PontuacaoAuxiliar> pontuacoesConformeAtividade) {

		for (PontuacaoAuxiliar pontuacaoAuxiliar : pontuacoes) {

			if (ativo.equals(TODOS)) {

				pontuacoesConformeAtividade.add(pontuacaoAuxiliar);
			}

			else {

				if (ativo.equals("Sim")) {

					if (pontuacaoAuxiliar.isAtivo()) {

						pontuacoesConformeAtividade.add(pontuacaoAuxiliar);
					}
				}

				else if (ativo.equals("Não")) {

					if (!pontuacaoAuxiliar.isAtivo()) {

						pontuacoesConformeAtividade.add(pontuacaoAuxiliar);
					}
				}
			}
		}
	}

	public PontuacaoAuxiliar calcularPontuacoes(List<Criterion> restricoes, MalaDireta informacoesUsuario) {

		PontuacaoAuxiliar pontuacaoAuxiliar = new PontuacaoAuxiliar();
		pontuacaoAuxiliar.setMalaDireta(informacoesUsuario);

		Pontuacao pontuacaoFiltro = new Pontuacao();
		pontuacaoFiltro.setId_Codigo(informacoesUsuario.getUsuario().getId_Codigo());

		List<Pontuacao> pontuacoesBanco = hibernateUtil.buscar(pontuacaoFiltro, restricoes);

		for (Pontuacao pontuacaoBanco : pontuacoesBanco) {

			pontuacaoAuxiliar.setParametroAtividade(pontuacaoBanco.getParametroAtividade());

			pontuacaoAuxiliar.setPontuacaoAtividade(pontuacaoAuxiliar.getPontuacaoAtividade().add(pontuacaoBanco.getPntAtividade()));
			pontuacaoAuxiliar.setPontuacaoIngresso(pontuacaoAuxiliar.getPontuacaoIngresso().add(pontuacaoBanco.getPntIngresso()));
			pontuacaoAuxiliar.setPontuacaoProdutos(pontuacaoAuxiliar.getPontuacaoProdutos().add(pontuacaoBanco.getPntProduto()));
		}
		return pontuacaoAuxiliar;
	}

	private void adicionarConformeMovimentacoes(String possuiMovimentacao, List<PontuacaoAuxiliar> pontuacoes, PontuacaoAuxiliar pontuacaoAuxiliar) {

		if (possuiMovimentacao.equals(TODOS)) {

			pontuacoes.add(pontuacaoAuxiliar);
		}

		else {

			if (possuiMovimentacao.equals("Sim")) {

				if (pontuacaoAuxiliar.getTotal().compareTo(BigDecimal.ZERO) > 0) {

					pontuacoes.add(pontuacaoAuxiliar);
				}
			}

			else if (possuiMovimentacao.equals("Não")) {

				if (pontuacaoAuxiliar.getTotal().compareTo(BigDecimal.ZERO) == 0) {

					pontuacoes.add(pontuacaoAuxiliar);
				}
			}
		}
	}
}
