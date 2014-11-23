package escritoriovirtualalabastrum.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;

import escritoriovirtualalabastrum.auxiliar.MalaDireta;
import escritoriovirtualalabastrum.hibernate.HibernateUtil;
import escritoriovirtualalabastrum.modelo.ControlePedido;
import escritoriovirtualalabastrum.modelo.Usuario;

public class BonificacaoUniLevelService {

	public static final Integer QUANTIDADE_GERACOES_QUE_DISTRIBUIRAO_BONUS = 10;
	public static final BigDecimal PORCENTAGEM_COMISSAO_POR_PEDIDO = new BigDecimal("1.8");
	public static final BigDecimal COMPRA_PESSOAL_CONDICAO_PARA_RECEBIMENTO = new BigDecimal("79");

	private HibernateUtil hibernateUtil;

	public BonificacaoUniLevelService(HibernateUtil hibernateUtil) {

		this.hibernateUtil = hibernateUtil;
	}

	public BigDecimal calcularBonificacoes(Usuario usuario, Integer ano, Integer mes) {

		DateTime dataInicial = new DateTime(ano, mes, 1, 0, 0, 0);
		DateTime dataFinal = new DateTime(ano, mes, dataInicial.dayOfMonth().withMaximumValue().dayOfMonth().get(), 0, 0, 0);

		BigDecimal comissao = BigDecimal.ZERO;

		if (usuario.isAtivo(dataInicial.toGregorianCalendar(), dataFinal.toGregorianCalendar()) && new MalaDiretaService(this.hibernateUtil).contarIndicacoes(usuario) >= 3 && calcularCompraPessoal(usuario, dataInicial, dataFinal).compareTo(COMPRA_PESSOAL_CONDICAO_PARA_RECEBIMENTO) >= 0) {

			TreeMap<Integer, MalaDireta> malaDireta = new TreeMap<Integer, MalaDireta>();
			new MalaDiretaService(hibernateUtil).pesquisarMalaDiretaComRecursividade(usuario.getId_Codigo(), malaDireta, 1, "id_Patroc");

			List<ControlePedido> pedidosDaRede = new ControlePedidoRedeService(this.hibernateUtil).buscarPedidosDaRede(usuario.getId_Codigo(), malaDireta, dataInicial.toGregorianCalendar(), dataFinal.toGregorianCalendar(), QUANTIDADE_GERACOES_QUE_DISTRIBUIRAO_BONUS);

			for (ControlePedido controlePedido : pedidosDaRede) {

				comissao = comissao.add(controlePedido.getBaseCalculo().multiply(PORCENTAGEM_COMISSAO_POR_PEDIDO).divide(new BigDecimal("100")));
			}
		}

		return comissao;
	}

	private BigDecimal calcularCompraPessoal(Usuario usuario, DateTime dataInicial, DateTime dataFinal) {

		List<Criterion> restricoes = new ArrayList<Criterion>();
		restricoes.add(Restrictions.between("pedData", dataInicial.toGregorianCalendar(), dataFinal.toGregorianCalendar()));

		ControlePedido controlePedidoFiltro = new ControlePedido();
		controlePedidoFiltro.setId_Codigo(usuario.getId_Codigo());

		return this.hibernateUtil.somar(controlePedidoFiltro, restricoes, null, "BaseCalculo");
	}
}