package escritoriovirtualalabastrum.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.joda.time.DateTime;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import escritoriovirtualalabastrum.auxiliar.BonificacaoAtivacaoAuxiliar;
import escritoriovirtualalabastrum.auxiliar.BonificacaoIngressoAuxiliar;
import escritoriovirtualalabastrum.auxiliar.MalaDireta;
import escritoriovirtualalabastrum.hibernate.HibernateUtil;
import escritoriovirtualalabastrum.modelo.Usuario;
import escritoriovirtualalabastrum.util.Util;

public class BonificacaoAtivacaoService {

	private static final BigDecimal BONIFICACAO_FIXA_LIDERES_ATIVOS = new BigDecimal("8");
	private static final BigDecimal BONIFICACAO_FIXA_DIAMANTES = new BigDecimal("2");
	private static final Integer QUANTIDADE_GERACOES_QUE_DISTRIBUIRAO_BONUS = 5;
	private static final Integer QUANTIDADE_GERACOES_QUE_DISTRIBUIRAO_BONUS_PARA_DIAMANTE = 10;

	private HibernateUtil hibernateUtil;
	private Result result;
	private Validator validator;

	public BonificacaoAtivacaoService(HibernateUtil hibernateUtil, Result result, Validator validator) {

		this.hibernateUtil = hibernateUtil;
		this.result = result;
		this.validator = validator;
	}

	public List<BonificacaoAtivacaoAuxiliar> calcularBonificacoes(Usuario usuario, Integer ano, Integer mes) {

		List<BonificacaoAtivacaoAuxiliar> bonificacoesAtivacao = new ArrayList<BonificacaoAtivacaoAuxiliar>();

		DateTime dataInicial = new DateTime(ano, mes, 1, 0, 0, 0);
		DateTime dataFinal = new DateTime(ano, mes, dataInicial.dayOfMonth().withMaximumValue().dayOfMonth().get(), 0, 0, 0);

		if (usuario.isAtivo(dataInicial.toGregorianCalendar(), dataFinal.toGregorianCalendar())) {

			TreeMap<Integer, MalaDireta> malaDiretaCompleta = new TreeMap<Integer, MalaDireta>();

			MalaDiretaService malaDiretaService = new MalaDiretaService(hibernateUtil);
			malaDiretaService.gerarMalaDiretaDeAcordoComAtividade("id_Patroc", usuario.getId_Codigo(), dataInicial.toGregorianCalendar(), dataFinal.toGregorianCalendar(), 1, malaDiretaCompleta);

			for (Entry<Integer, MalaDireta> malaDireta : malaDiretaCompleta.entrySet()) {

				if (malaDireta.getValue().getUsuario().isAtivo(dataInicial.toGregorianCalendar(), dataFinal.toGregorianCalendar()) && malaDireta.getValue().getNivel() <= QUANTIDADE_GERACOES_QUE_DISTRIBUIRAO_BONUS) {

					BonificacaoAtivacaoAuxiliar bonificacaoAtivacaoAuxiliar = new BonificacaoAtivacaoAuxiliar();
					bonificacaoAtivacaoAuxiliar.setMalaDireta(malaDireta.getValue());
					bonificacaoAtivacaoAuxiliar.setBonificacao(BONIFICACAO_FIXA_LIDERES_ATIVOS);

					bonificacoesAtivacao.add(bonificacaoAtivacaoAuxiliar);
				}
			}

			if (usuario.getPosAtual().toLowerCase().contains(MalaDiretaService.DIAMANTE.toLowerCase())) {

				calcularBonificacoesDeDiamante(usuario, ano, mes, bonificacoesAtivacao, dataInicial, dataFinal, malaDiretaCompleta);
			}
		}

		return bonificacoesAtivacao;
	}

	private void calcularBonificacoesDeDiamante(Usuario usuario, Integer ano, Integer mes, List<BonificacaoAtivacaoAuxiliar> bonificacoesAtivacao, DateTime dataInicial, DateTime dataFinal, TreeMap<Integer, MalaDireta> malaDiretaCompleta) {

		BonificacaoIngressoAuxiliar calculosDiamante = new MalaDiretaService().verificaSeMetaDeDiamanteFoiAtingida(usuario, result, hibernateUtil, validator, ano, mes);
		if (calculosDiamante.getQuantidadeGraduados() >= BonificacaoIngressoService.META_DIAMANTE_LINHAS_GRADUADOS && calculosDiamante.getPontuacaoDiamante().compareTo(BonificacaoIngressoService.META_DIAMANTE_PONTUACAO) >= 0) {

			for (Entry<Integer, MalaDireta> malaDireta : malaDiretaCompleta.entrySet()) {

				if (malaDireta.getValue().getUsuario().isAtivo(dataInicial.toGregorianCalendar(), dataFinal.toGregorianCalendar()) && (malaDireta.getValue().getNivel() > QUANTIDADE_GERACOES_QUE_DISTRIBUIRAO_BONUS && malaDireta.getValue().getNivel() <= QUANTIDADE_GERACOES_QUE_DISTRIBUIRAO_BONUS_PARA_DIAMANTE)) {

					BonificacaoAtivacaoAuxiliar bonificacaoAtivacaoAuxiliar = new BonificacaoAtivacaoAuxiliar();
					bonificacaoAtivacaoAuxiliar.setMalaDireta(malaDireta.getValue());
					bonificacaoAtivacaoAuxiliar.setBonificacao(BONIFICACAO_FIXA_DIAMANTES);

					bonificacoesAtivacao.add(bonificacaoAtivacaoAuxiliar);
				}
			}
		}
	}

	public BigDecimal calcularSomatorioBonificacoes(List<BonificacaoAtivacaoAuxiliar> bonificacoes) {

		BigDecimal somatorioBonificacao = new BigDecimal("0");

		if (Util.preenchido(bonificacoes)) {

			for (BonificacaoAtivacaoAuxiliar bonificacao : bonificacoes) {

				if (Util.preenchido(bonificacao.getBonificacao())) {

					somatorioBonificacao = somatorioBonificacao.add(bonificacao.getBonificacao());
				}
			}
		}

		return somatorioBonificacao;
	}
}