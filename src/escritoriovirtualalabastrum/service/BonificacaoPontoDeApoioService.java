package escritoriovirtualalabastrum.service;

import java.math.BigDecimal;
import java.util.List;

import org.joda.time.DateTime;

import escritoriovirtualalabastrum.hibernate.HibernateUtil;
import escritoriovirtualalabastrum.modelo.Usuario;
import escritoriovirtualalabastrum.util.Util;

public class BonificacaoPontoDeApoioService {

	private HibernateUtil hibernateUtil;

	public BonificacaoPontoDeApoioService(HibernateUtil hibernateUtil) {

		this.hibernateUtil = hibernateUtil;
	}

	public BigDecimal calcularBonificacoes(Usuario usuario, Integer ano, Integer mes) {

		DateTime dataInicial = new DateTime(ano, mes, 1, 0, 0, 0);
		DateTime dataFinal = new DateTime(ano, mes, dataInicial.dayOfMonth().withMaximumValue().dayOfMonth().get(), 0, 0, 0);

		BigDecimal bonificacao = calcularValorBonusPA(usuario, dataInicial, dataFinal);

		return bonificacao;
	}

	private BigDecimal calcularValorBonusPA(Usuario usuario, DateTime dataInicial, DateTime dataFinal) {

		@SuppressWarnings("unchecked")
		List<Object> kit = hibernateUtil.buscaPorHQL("select sum(ValorBonusPA) from CentroDistribuicao where id_indicantePA = " + usuario.getId_Codigo() + " and data_referencia between '" + dataInicial.toString("YYYY-MM-dd") + "' and '" + dataFinal.toString("YYYY-MM-dd") + "'");

		if (Util.preenchido(kit.get(0))) {

			return (BigDecimal) kit.get(0);

		} else {

			return BigDecimal.ZERO;
		}
	}
}