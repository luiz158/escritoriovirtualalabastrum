package escritoriovirtualalabastrum.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;

import escritoriovirtualalabastrum.auxiliar.BonificacaoCompraPessoalAuxiliar;
import escritoriovirtualalabastrum.hibernate.HibernateUtil;
import escritoriovirtualalabastrum.modelo.ControlePedido;
import escritoriovirtualalabastrum.modelo.Usuario;

public class BonificacaoCompraPessoalService {

	private HibernateUtil hibernateUtil;

	public BonificacaoCompraPessoalService(HibernateUtil hibernateUtil) {

		this.hibernateUtil = hibernateUtil;
	}

	public BonificacaoCompraPessoalAuxiliar calcularBonificacoes(Usuario usuario, Integer ano, Integer mes, BigDecimal pontuacao, Integer quantidadeGraduados) {

		GraduacaoService graduacaoService = new GraduacaoService();

		BigDecimal totalBaseCalculo = calcularTotalBaseCalculo(usuario, ano, mes);
		String graduacao = graduacaoService.verificaGraduacao(pontuacao, quantidadeGraduados);
		BigDecimal porcentagem = graduacaoService.calcularPorcentagemDeAcordoComGraduacao(graduacao);
		BigDecimal bonificacao = totalBaseCalculo.multiply(porcentagem).divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP);

		BonificacaoCompraPessoalAuxiliar bonificacaoCompraPessoalAuxiliar = new BonificacaoCompraPessoalAuxiliar();
		bonificacaoCompraPessoalAuxiliar.setGraduacao(graduacao);
		bonificacaoCompraPessoalAuxiliar.setPorcentagem(porcentagem);
		bonificacaoCompraPessoalAuxiliar.setBonificacao(bonificacao);

		return bonificacaoCompraPessoalAuxiliar;
	}

	public BigDecimal calcularTotalBaseCalculo(Usuario usuario, Integer ano, Integer mes) {

		DateTime dataInicial = new DateTime(ano, mes, 1, 0, 0, 0);
		DateTime dataFinal = new DateTime(ano, mes, dataInicial.dayOfMonth().withMaximumValue().dayOfMonth().get(), 0, 0, 0);

		List<Criterion> restricoes = new ArrayList<Criterion>();
		restricoes.add(Restrictions.between("pedData", dataInicial.toGregorianCalendar(), dataFinal.toGregorianCalendar()));

		ControlePedido controlePedidoFiltro = new ControlePedido();
		controlePedidoFiltro.setId_Codigo(usuario.getId_Codigo());

		BigDecimal totalBaseCalculo = this.hibernateUtil.somar(controlePedidoFiltro, restricoes, null, "BaseCalculo");

		if (totalBaseCalculo == null) {

			return BigDecimal.ZERO;
		}

		return totalBaseCalculo;
	}
}