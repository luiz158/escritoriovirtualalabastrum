package escritoriovirtualalabastrum.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
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
import escritoriovirtualalabastrum.anotacoes.Funcionalidade;
import escritoriovirtualalabastrum.auxiliar.MalaDireta;
import escritoriovirtualalabastrum.auxiliar.PontuacaoAnualAuxiliar;
import escritoriovirtualalabastrum.hibernate.HibernateUtil;
import escritoriovirtualalabastrum.modelo.Pontuacao;
import escritoriovirtualalabastrum.modelo.Usuario;
import escritoriovirtualalabastrum.service.MalaDiretaService;
import escritoriovirtualalabastrum.sessao.SessaoUsuario;

@Resource
public class PontuacaoAnualController {

	private Result result;
	private HibernateUtil hibernateUtil;
	private SessaoUsuario sessaoUsuario;
	private Validator validator;

	public PontuacaoAnualController(Result result, HibernateUtil hibernateUtil, SessaoUsuario sessaoUsuario, Validator validator) {

		this.result = result;
		this.hibernateUtil = hibernateUtil;
		this.sessaoUsuario = sessaoUsuario;
		this.validator = validator;
	}

	@Funcionalidade
	public void acessarTelaPontuacaoAnual() {

		result.include("posicoes", new MalaDiretaService().obterPosicoes());
	}

	@Funcionalidade
	public void gerarRelatorioPontuacaoAnual(String posicao, Integer codigoUsuario, Integer ano, String possuiMovimentacao) {

		Integer codigoUsuarioLogado = this.sessaoUsuario.getUsuario().getId_Codigo();

		MalaDiretaService malaDiretaService = new MalaDiretaService();
		malaDiretaService.setHibernateUtil(hibernateUtil);
		malaDiretaService.setValidator(validator);

		TreeMap<Integer, MalaDireta> malaDireta = malaDiretaService.gerarMalaDireta(posicao, codigoUsuario, codigoUsuarioLogado);

		gerarRelatorioPontuacaoAnual(ano, malaDireta, codigoUsuario, possuiMovimentacao);

		result.include("posicaoConsiderada", new MalaDiretaService().obterPosicoes().get(posicao));
		result.include("ano", ano);
		result.include("possuiMovimentacao", possuiMovimentacao);
	}

	private void gerarRelatorioPontuacaoAnual(Integer ano, TreeMap<Integer, MalaDireta> malaDireta, Integer codigoUsuario, String possuiMovimentacao) {

		Usuario usuarioPesquisado = new Usuario();
		usuarioPesquisado.setId_Codigo(codigoUsuario);

		usuarioPesquisado = this.hibernateUtil.selecionar(usuarioPesquisado, MatchMode.EXACT);
		result.include("usuarioPesquisado", usuarioPesquisado);

		malaDireta.put(0, new MalaDireta(usuarioPesquisado, 0));

		List<PontuacaoAnualAuxiliar> pontuacoes = calcularPontuacoesMensalmente(ano, malaDireta);

		adicionarConformeMovimentacoes(possuiMovimentacao, pontuacoes);
	}

	private void adicionarConformeMovimentacoes(String possuiMovimentacao, List<PontuacaoAnualAuxiliar> pontuacoes) {

		if (possuiMovimentacao.equals("Todos")) {

			result.include("pontuacoes", pontuacoes);
			result.include("quantidadeElementos", pontuacoes.size());
		}

		else {

			List<PontuacaoAnualAuxiliar> pontuacoesDeAcordoComMovimentacoes = new ArrayList<PontuacaoAnualAuxiliar>();

			if (possuiMovimentacao.equals("Sim")) {

				for (PontuacaoAnualAuxiliar pontuacao : pontuacoes) {

					if (pontuacao.getPontuacaoAtividadeTotal().compareTo(BigDecimal.ZERO) > 0 || pontuacao.getPontuacaoProdutosTotal().compareTo(BigDecimal.ZERO) > 0) {

						pontuacoesDeAcordoComMovimentacoes.add(pontuacao);
					}
				}
			}

			if (possuiMovimentacao.equals("Não")) {

				for (PontuacaoAnualAuxiliar pontuacao : pontuacoes) {

					if (pontuacao.getPontuacaoAtividadeTotal().compareTo(BigDecimal.ZERO) == 0 && pontuacao.getPontuacaoProdutosTotal().compareTo(BigDecimal.ZERO) == 0) {

						pontuacoesDeAcordoComMovimentacoes.add(pontuacao);
					}
				}
			}

			result.include("pontuacoes", pontuacoesDeAcordoComMovimentacoes);
			result.include("quantidadeElementos", pontuacoesDeAcordoComMovimentacoes.size());
		}
	}

	private List<PontuacaoAnualAuxiliar> calcularPontuacoesMensalmente(Integer ano, TreeMap<Integer, MalaDireta> malaDireta) {

		List<PontuacaoAnualAuxiliar> pontuacoes = new ArrayList<PontuacaoAnualAuxiliar>();

		for (Entry<Integer, MalaDireta> usuario : malaDireta.entrySet()) {

			PontuacaoAnualAuxiliar pontuacaoAnualAuxiliar = new PontuacaoAnualAuxiliar();
			pontuacaoAnualAuxiliar.setMalaDireta(usuario.getValue());

			Pontuacao pontuacaoFiltro = new Pontuacao();
			pontuacaoFiltro.setId_Codigo(pontuacaoAnualAuxiliar.getMalaDireta().getUsuario().getId_Codigo());

			DateTime primeiroDiaJaneiro = new DateTime(ano, 1, 1, 0, 0, 0);
			DateTime ultimoDiaJaneiro = new DateTime(ano, 1, primeiroDiaJaneiro.dayOfMonth().withMaximumValue().dayOfMonth().get(), 0, 0, 0);

			List<Criterion> restricoesJaneiro = new ArrayList<Criterion>();
			restricoesJaneiro.add(Restrictions.between("Dt_Pontos", primeiroDiaJaneiro.toGregorianCalendar(), ultimoDiaJaneiro.toGregorianCalendar()));

			List<Pontuacao> pontuacoesJaneiro = hibernateUtil.buscar(pontuacaoFiltro, restricoesJaneiro);

			for (Pontuacao pontuacaoBanco : pontuacoesJaneiro) {

				pontuacaoAnualAuxiliar.setPontuacaoAtividadeJaneiro(pontuacaoAnualAuxiliar.getPontuacaoAtividadeJaneiro().add(pontuacaoBanco.getPntAtividade()));
				pontuacaoAnualAuxiliar.setPontuacaoProdutosJaneiro(pontuacaoAnualAuxiliar.getPontuacaoProdutosJaneiro().add(pontuacaoBanco.getPntProduto()));
			}

			DateTime primeiroDiaFevereiro = new DateTime(ano, 2, 1, 0, 0, 0);
			DateTime ultimoDiaFevereiro = new DateTime(ano, 2, primeiroDiaFevereiro.dayOfMonth().withMaximumValue().dayOfMonth().get(), 0, 0, 0);

			List<Criterion> restricoesFevereiro = new ArrayList<Criterion>();
			restricoesFevereiro.add(Restrictions.between("Dt_Pontos", primeiroDiaFevereiro.toGregorianCalendar(), ultimoDiaFevereiro.toGregorianCalendar()));

			List<Pontuacao> pontuacoesFevereiro = hibernateUtil.buscar(pontuacaoFiltro, restricoesFevereiro);

			for (Pontuacao pontuacaoBanco : pontuacoesFevereiro) {

				pontuacaoAnualAuxiliar.setPontuacaoAtividadeFevereiro(pontuacaoAnualAuxiliar.getPontuacaoAtividadeFevereiro().add(pontuacaoBanco.getPntAtividade()));
				pontuacaoAnualAuxiliar.setPontuacaoProdutosFevereiro(pontuacaoAnualAuxiliar.getPontuacaoProdutosFevereiro().add(pontuacaoBanco.getPntProduto()));
			}

			DateTime primeiroDiaMarco = new DateTime(ano, 3, 1, 0, 0, 0);
			DateTime ultimoDiaMarco = new DateTime(ano, 3, primeiroDiaMarco.dayOfMonth().withMaximumValue().dayOfMonth().get(), 0, 0, 0);

			List<Criterion> restricoesMarco = new ArrayList<Criterion>();
			restricoesMarco.add(Restrictions.between("Dt_Pontos", primeiroDiaMarco.toGregorianCalendar(), ultimoDiaMarco.toGregorianCalendar()));

			List<Pontuacao> pontuacoesMarco = hibernateUtil.buscar(pontuacaoFiltro, restricoesMarco);

			for (Pontuacao pontuacaoBanco : pontuacoesMarco) {

				pontuacaoAnualAuxiliar.setPontuacaoAtividadeMarco(pontuacaoAnualAuxiliar.getPontuacaoAtividadeMarco().add(pontuacaoBanco.getPntAtividade()));
				pontuacaoAnualAuxiliar.setPontuacaoProdutosMarco(pontuacaoAnualAuxiliar.getPontuacaoProdutosMarco().add(pontuacaoBanco.getPntProduto()));
			}

			DateTime primeiroDiaAbril = new DateTime(ano, 4, 1, 0, 0, 0);
			DateTime ultimoDiaAbril = new DateTime(ano, 4, primeiroDiaAbril.dayOfMonth().withMaximumValue().dayOfMonth().get(), 0, 0, 0);

			List<Criterion> restricoesAbril = new ArrayList<Criterion>();
			restricoesAbril.add(Restrictions.between("Dt_Pontos", primeiroDiaAbril.toGregorianCalendar(), ultimoDiaAbril.toGregorianCalendar()));

			List<Pontuacao> pontuacoesAbril = hibernateUtil.buscar(pontuacaoFiltro, restricoesAbril);

			for (Pontuacao pontuacaoBanco : pontuacoesAbril) {

				pontuacaoAnualAuxiliar.setPontuacaoAtividadeAbril(pontuacaoAnualAuxiliar.getPontuacaoAtividadeAbril().add(pontuacaoBanco.getPntAtividade()));
				pontuacaoAnualAuxiliar.setPontuacaoProdutosAbril(pontuacaoAnualAuxiliar.getPontuacaoProdutosAbril().add(pontuacaoBanco.getPntProduto()));
			}

			DateTime primeiroDiaMaio = new DateTime(ano, 5, 1, 0, 0, 0);
			DateTime ultimoDiaMaio = new DateTime(ano, 5, primeiroDiaMaio.dayOfMonth().withMaximumValue().dayOfMonth().get(), 0, 0, 0);

			List<Criterion> restricoesMaio = new ArrayList<Criterion>();
			restricoesMaio.add(Restrictions.between("Dt_Pontos", primeiroDiaMaio.toGregorianCalendar(), ultimoDiaMaio.toGregorianCalendar()));

			List<Pontuacao> pontuacoesMaio = hibernateUtil.buscar(pontuacaoFiltro, restricoesMaio);

			for (Pontuacao pontuacaoBanco : pontuacoesMaio) {

				pontuacaoAnualAuxiliar.setPontuacaoAtividadeMaio(pontuacaoAnualAuxiliar.getPontuacaoAtividadeMaio().add(pontuacaoBanco.getPntAtividade()));
				pontuacaoAnualAuxiliar.setPontuacaoProdutosMaio(pontuacaoAnualAuxiliar.getPontuacaoProdutosMaio().add(pontuacaoBanco.getPntProduto()));
			}

			DateTime primeiroDiaJunho = new DateTime(ano, 6, 1, 0, 0, 0);
			DateTime ultimoDiaJunho = new DateTime(ano, 6, primeiroDiaJunho.dayOfMonth().withMaximumValue().dayOfMonth().get(), 0, 0, 0);

			List<Criterion> restricoesJunho = new ArrayList<Criterion>();
			restricoesJunho.add(Restrictions.between("Dt_Pontos", primeiroDiaJunho.toGregorianCalendar(), ultimoDiaJunho.toGregorianCalendar()));

			List<Pontuacao> pontuacoesJunho = hibernateUtil.buscar(pontuacaoFiltro, restricoesJunho);

			for (Pontuacao pontuacaoBanco : pontuacoesJunho) {

				pontuacaoAnualAuxiliar.setPontuacaoAtividadeJunho(pontuacaoAnualAuxiliar.getPontuacaoAtividadeJunho().add(pontuacaoBanco.getPntAtividade()));
				pontuacaoAnualAuxiliar.setPontuacaoProdutosJunho(pontuacaoAnualAuxiliar.getPontuacaoProdutosJunho().add(pontuacaoBanco.getPntProduto()));
			}

			DateTime primeiroDiaJulho = new DateTime(ano, 7, 1, 0, 0, 0);
			DateTime ultimoDiaJulho = new DateTime(ano, 7, primeiroDiaJulho.dayOfMonth().withMaximumValue().dayOfMonth().get(), 0, 0, 0);

			List<Criterion> restricoesJulho = new ArrayList<Criterion>();
			restricoesJulho.add(Restrictions.between("Dt_Pontos", primeiroDiaJulho.toGregorianCalendar(), ultimoDiaJulho.toGregorianCalendar()));

			List<Pontuacao> pontuacoesJulho = hibernateUtil.buscar(pontuacaoFiltro, restricoesJulho);

			for (Pontuacao pontuacaoBanco : pontuacoesJulho) {

				pontuacaoAnualAuxiliar.setPontuacaoAtividadeJulho(pontuacaoAnualAuxiliar.getPontuacaoAtividadeJulho().add(pontuacaoBanco.getPntAtividade()));
				pontuacaoAnualAuxiliar.setPontuacaoProdutosJulho(pontuacaoAnualAuxiliar.getPontuacaoProdutosJulho().add(pontuacaoBanco.getPntProduto()));
			}

			DateTime primeiroDiaAgosto = new DateTime(ano, 8, 1, 0, 0, 0);
			DateTime ultimoDiaAgosto = new DateTime(ano, 8, primeiroDiaAgosto.dayOfMonth().withMaximumValue().dayOfMonth().get(), 0, 0, 0);

			List<Criterion> restricoesAgosto = new ArrayList<Criterion>();
			restricoesAgosto.add(Restrictions.between("Dt_Pontos", primeiroDiaAgosto.toGregorianCalendar(), ultimoDiaAgosto.toGregorianCalendar()));

			List<Pontuacao> pontuacoesAgosto = hibernateUtil.buscar(pontuacaoFiltro, restricoesAgosto);

			for (Pontuacao pontuacaoBanco : pontuacoesAgosto) {

				pontuacaoAnualAuxiliar.setPontuacaoAtividadeAgosto(pontuacaoAnualAuxiliar.getPontuacaoAtividadeAgosto().add(pontuacaoBanco.getPntAtividade()));
				pontuacaoAnualAuxiliar.setPontuacaoProdutosAgosto(pontuacaoAnualAuxiliar.getPontuacaoProdutosAgosto().add(pontuacaoBanco.getPntProduto()));
			}

			DateTime primeiroDiaSetembro = new DateTime(ano, 9, 1, 0, 0, 0);
			DateTime ultimoDiaSetembro = new DateTime(ano, 9, primeiroDiaSetembro.dayOfMonth().withMaximumValue().dayOfMonth().get(), 0, 0, 0);

			List<Criterion> restricoesSetembro = new ArrayList<Criterion>();
			restricoesSetembro.add(Restrictions.between("Dt_Pontos", primeiroDiaSetembro.toGregorianCalendar(), ultimoDiaSetembro.toGregorianCalendar()));

			List<Pontuacao> pontuacoesSetembro = hibernateUtil.buscar(pontuacaoFiltro, restricoesSetembro);

			for (Pontuacao pontuacaoBanco : pontuacoesSetembro) {

				pontuacaoAnualAuxiliar.setPontuacaoAtividadeSetembro(pontuacaoAnualAuxiliar.getPontuacaoAtividadeSetembro().add(pontuacaoBanco.getPntAtividade()));
				pontuacaoAnualAuxiliar.setPontuacaoProdutosSetembro(pontuacaoAnualAuxiliar.getPontuacaoProdutosSetembro().add(pontuacaoBanco.getPntProduto()));
			}

			DateTime primeiroDiaOutubro = new DateTime(ano, 10, 1, 0, 0, 0);
			DateTime ultimoDiaOutubro = new DateTime(ano, 10, primeiroDiaOutubro.dayOfMonth().withMaximumValue().dayOfMonth().get(), 0, 0, 0);

			List<Criterion> restricoesOutubro = new ArrayList<Criterion>();
			restricoesOutubro.add(Restrictions.between("Dt_Pontos", primeiroDiaOutubro.toGregorianCalendar(), ultimoDiaOutubro.toGregorianCalendar()));

			List<Pontuacao> pontuacoesOutubro = hibernateUtil.buscar(pontuacaoFiltro, restricoesOutubro);

			for (Pontuacao pontuacaoBanco : pontuacoesOutubro) {

				pontuacaoAnualAuxiliar.setPontuacaoAtividadeOutubro(pontuacaoAnualAuxiliar.getPontuacaoAtividadeOutubro().add(pontuacaoBanco.getPntAtividade()));
				pontuacaoAnualAuxiliar.setPontuacaoProdutosOutubro(pontuacaoAnualAuxiliar.getPontuacaoProdutosOutubro().add(pontuacaoBanco.getPntProduto()));
			}

			DateTime primeiroDiaNovembro = new DateTime(ano, 11, 1, 0, 0, 0);
			DateTime ultimoDiaNovembro = new DateTime(ano, 11, primeiroDiaNovembro.dayOfMonth().withMaximumValue().dayOfMonth().get(), 0, 0, 0);

			List<Criterion> restricoesNovembro = new ArrayList<Criterion>();
			restricoesNovembro.add(Restrictions.between("Dt_Pontos", primeiroDiaNovembro.toGregorianCalendar(), ultimoDiaNovembro.toGregorianCalendar()));

			List<Pontuacao> pontuacoesNovembro = hibernateUtil.buscar(pontuacaoFiltro, restricoesNovembro);

			for (Pontuacao pontuacaoBanco : pontuacoesNovembro) {

				pontuacaoAnualAuxiliar.setPontuacaoAtividadeNovembro(pontuacaoAnualAuxiliar.getPontuacaoAtividadeNovembro().add(pontuacaoBanco.getPntAtividade()));
				pontuacaoAnualAuxiliar.setPontuacaoProdutosNovembro(pontuacaoAnualAuxiliar.getPontuacaoProdutosNovembro().add(pontuacaoBanco.getPntProduto()));
			}

			DateTime primeiroDiaDezembro = new DateTime(ano, 12, 1, 0, 0, 0);
			DateTime ultimoDiaDezembro = new DateTime(ano, 12, primeiroDiaDezembro.dayOfMonth().withMaximumValue().dayOfMonth().get(), 0, 0, 0);

			List<Criterion> restricoesDezembro = new ArrayList<Criterion>();
			restricoesDezembro.add(Restrictions.between("Dt_Pontos", primeiroDiaDezembro.toGregorianCalendar(), ultimoDiaDezembro.toGregorianCalendar()));

			List<Pontuacao> pontuacoesDezembro = hibernateUtil.buscar(pontuacaoFiltro, restricoesDezembro);

			for (Pontuacao pontuacaoBanco : pontuacoesDezembro) {

				pontuacaoAnualAuxiliar.setPontuacaoAtividadeDezembro(pontuacaoAnualAuxiliar.getPontuacaoAtividadeDezembro().add(pontuacaoBanco.getPntAtividade()));
				pontuacaoAnualAuxiliar.setPontuacaoProdutosDezembro(pontuacaoAnualAuxiliar.getPontuacaoProdutosDezembro().add(pontuacaoBanco.getPntProduto()));
			}

			pontuacoes.add(pontuacaoAnualAuxiliar);
		}

		return pontuacoes;
	}
}
