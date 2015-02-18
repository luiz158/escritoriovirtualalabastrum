package escritoriovirtualalabastrum.service;

import java.math.BigDecimal;
import java.util.List;

import org.joda.time.DateTime;

import escritoriovirtualalabastrum.hibernate.HibernateUtil;
import escritoriovirtualalabastrum.modelo.HistoricoKit;
import escritoriovirtualalabastrum.modelo.Usuario;
import escritoriovirtualalabastrum.util.Util;

public class BonificacaoDivisaoService {

	private HibernateUtil hibernateUtil;

	public BonificacaoDivisaoService(HibernateUtil hibernateUtil) {

		this.hibernateUtil = hibernateUtil;
	}

	public BigDecimal calcularBonificacoes(Usuario usuario, Integer ano, Integer mes) {

		DateTime dataInicial = new DateTime(ano, mes, 1, 0, 0, 0);
		DateTime dataFinal = new DateTime(ano, mes, dataInicial.dayOfMonth().withMaximumValue().dayOfMonth().get(), 0, 0, 0);

		BigDecimal totalIngressoMensal = calcularTotalIngressoMensal(dataInicial, dataFinal);
		BigDecimal porcentagemDoTotalIngressoMensal = totalIngressoMensal.multiply(new BigDecimal(2)).divide(new BigDecimal(100));
		List<HistoricoKit> historicosKit = getUsuariosComCotas(dataInicial, dataFinal);

		BigDecimal totalCotas = BigDecimal.ZERO;
		BigDecimal quantidadeDeCotasDoUsuarioPesquisado = BigDecimal.ZERO;

		if (Util.preenchido(historicosKit)) {
			for (HistoricoKit historicoKit : historicosKit) {

				Usuario usuarioComCota = this.hibernateUtil.selecionar(new Usuario(historicoKit.getId_Codigo()));

				if (usuarioComCota.isAtivo(dataInicial.toGregorianCalendar(), dataFinal.toGregorianCalendar()) && new MalaDiretaService(this.hibernateUtil).contarIndicacoes(usuarioComCota) >= 3) {

					totalCotas = totalCotas.add(historicoKit.getNr_cotas());

					if (usuarioComCota.getId_Codigo().equals(usuario.getId_Codigo())) {
						quantidadeDeCotasDoUsuarioPesquisado = historicoKit.getNr_cotas();
					}
				}
			}
		}

		BigDecimal bonificacao = BigDecimal.ZERO;

		if (!totalCotas.equals(BigDecimal.ZERO)) {

			BigDecimal valorDeCadaCota = porcentagemDoTotalIngressoMensal.divide(totalCotas);

			bonificacao = quantidadeDeCotasDoUsuarioPesquisado.multiply(valorDeCadaCota);
		}

		return bonificacao;
	}

	private BigDecimal calcularTotalIngressoMensal(DateTime dataInicial, DateTime dataFinal) {

		@SuppressWarnings("unchecked")
		List<Object> kit = hibernateUtil.buscaPorHQL("select sum(valorIngresso) from Pontuacao where Dt_Pontos between '" + dataInicial.toString("YYYY-MM-dd") + "' and '" + dataFinal.toString("YYYY-MM-dd") + "'");

		if (Util.preenchido(kit.get(0))) {
			return (BigDecimal) kit.get(0);
		} else {
			return BigDecimal.ZERO;
		}
	}

	private List<HistoricoKit> getUsuariosComCotas(DateTime dataInicial, DateTime dataFinal) {

		@SuppressWarnings("unchecked")
		List<HistoricoKit> codigos = hibernateUtil.buscaPorHQL("from HistoricoKit where data_referencia between '" + dataInicial.toString("YYYY-MM-dd") + "' and '" + dataFinal.toString("YYYY-MM-dd") + " and nr_cotas > 0" + "'");

		return codigos;
	}
}