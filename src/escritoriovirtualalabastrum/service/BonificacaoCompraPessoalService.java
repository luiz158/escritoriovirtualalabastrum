package escritoriovirtualalabastrum.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;

import escritoriovirtualalabastrum.hibernate.HibernateUtil;
import escritoriovirtualalabastrum.modelo.ControlePedido;
import escritoriovirtualalabastrum.modelo.Usuario;

public class BonificacaoCompraPessoalService {

	public static final BigDecimal BRONZE_PORCENTAGEM = new BigDecimal("3");
	public static final BigDecimal PRATA_PORCENTAGEM = new BigDecimal("6");
	public static final BigDecimal OURO_PORCENTAGEM = new BigDecimal("9");
	public static final BigDecimal ESMERALDA_PORCENTAGEM = new BigDecimal("12");
	public static final BigDecimal TOPAZIO_PORCENTAGEM = new BigDecimal("15");
	public static final BigDecimal DIAMANTE_PORCENTAGEM = new BigDecimal("18");

	private HibernateUtil hibernateUtil;

	public BonificacaoCompraPessoalService(HibernateUtil hibernateUtil) {

		this.hibernateUtil = hibernateUtil;
	}

	public BigDecimal calcularBonificacoes(Usuario usuario, Integer ano, Integer mes, BigDecimal pontuacao, Integer quantidadeGraduados) {

		BigDecimal totalBaseCalculo = calcularTotalBaseCalculo(usuario, ano, mes);

		BigDecimal porcentagem = calcularPorcentagemDeAcordoComGraduacao(pontuacao, quantidadeGraduados, totalBaseCalculo);

		return totalBaseCalculo.multiply(porcentagem).divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP);
	}

	private BigDecimal calcularPorcentagemDeAcordoComGraduacao(BigDecimal pontuacao, Integer quantidadeGraduados, BigDecimal totalBaseCalculo) {

		BigDecimal porcentagem = BigDecimal.ZERO;

		if (totalBaseCalculo.compareTo(BigDecimal.ZERO) > 0) {

			String graduacao = new GraduacaoService().verificaGraduacao(pontuacao, quantidadeGraduados);

			if (graduacao != null) {

				if (graduacao.equals(GraduacaoService.BRONZE)) {

					porcentagem = BRONZE_PORCENTAGEM;
				}

				if (graduacao.equals(GraduacaoService.PRATA)) {

					porcentagem = PRATA_PORCENTAGEM;
				}

				if (graduacao.equals(GraduacaoService.OURO)) {

					porcentagem = OURO_PORCENTAGEM;
				}

				if (graduacao.equals(GraduacaoService.ESMERALDA)) {

					porcentagem = ESMERALDA_PORCENTAGEM;
				}

				if (graduacao.equals(GraduacaoService.TOPAZIO)) {

					porcentagem = TOPAZIO_PORCENTAGEM;
				}

				if (graduacao.equals(GraduacaoService.DIAMANTE)) {

					porcentagem = DIAMANTE_PORCENTAGEM;
				}
			}
		}

		return porcentagem;
	}

	private BigDecimal calcularTotalBaseCalculo(Usuario usuario, Integer ano, Integer mes) {

		DateTime dataInicial = new DateTime(ano, mes, 1, 0, 0, 0);
		DateTime dataFinal = new DateTime(ano, mes, dataInicial.dayOfMonth().withMaximumValue().dayOfMonth().get(), 0, 0, 0);

		List<Criterion> restricoes = new ArrayList<Criterion>();
		restricoes.add(Restrictions.between("pedData", dataInicial.toGregorianCalendar(), dataFinal.toGregorianCalendar()));

		ControlePedido controlePedidoFiltro = new ControlePedido();
		controlePedidoFiltro.setId_Codigo(usuario.getId_Codigo());

		BigDecimal totalBaseCalculo = this.hibernateUtil.somar(controlePedidoFiltro, restricoes, null, "BaseCalculo");

		return totalBaseCalculo;
	}
}