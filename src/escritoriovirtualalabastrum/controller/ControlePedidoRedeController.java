package escritoriovirtualalabastrum.controller;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import escritoriovirtualalabastrum.anotacoes.Funcionalidade;
import escritoriovirtualalabastrum.auxiliar.MalaDireta;
import escritoriovirtualalabastrum.auxiliar.SomatorioControlePedido;
import escritoriovirtualalabastrum.hibernate.HibernateUtil;
import escritoriovirtualalabastrum.modelo.ControlePedido;
import escritoriovirtualalabastrum.modelo.Usuario;
import escritoriovirtualalabastrum.service.MalaDiretaService;
import escritoriovirtualalabastrum.sessao.SessaoUsuario;
import escritoriovirtualalabastrum.util.Util;

@Resource
public class ControlePedidoRedeController {

	private Result result;
	private HibernateUtil hibernateUtil;
	private SessaoUsuario sessaoUsuario;
	private Validator validator;

	public ControlePedidoRedeController(Result result, HibernateUtil hibernateUtil, SessaoUsuario sessaoUsuario, Validator validator) {

		this.result = result;
		this.hibernateUtil = hibernateUtil;
		this.sessaoUsuario = sessaoUsuario;
		this.validator = validator;
	}

	@Funcionalidade
	public void acessarTelaControlePedidoRede() {

		DateTime hoje = new DateTime();
		DateTime primeiroDiaDoMesAtual = hoje.withDayOfMonth(1);

		result.include("primeiroDiaMes", primeiroDiaDoMesAtual.toGregorianCalendar());
		result.include("dataAtual", hoje.toGregorianCalendar());
	}

	@Funcionalidade
	@Path("/controlePedidoRede/gerarRecebendoAnoEMes")
	public void gerarRelatorioControlePedidoRede(Integer ano, Integer mes) {

		DateTime dataInicial = new DateTime(ano, mes, 1, 0, 0, 0);
		DateTime dataFinal = new DateTime(ano, mes, dataInicial.dayOfMonth().withMaximumValue().dayOfMonth().get(), 0, 0, 0);

		gerarRelatorioControlePedidoRede(null, dataInicial.toGregorianCalendar(), dataFinal.toGregorianCalendar());
	}

	@Funcionalidade
	@Path("/controlePedidoRede/gerarRecebendoDataInicialEFinal")
	public void gerarRelatorioControlePedidoRede(Integer codigoUsuario, GregorianCalendar dataInicial, GregorianCalendar dataFinal) {

		Integer codigoUsuarioLogado = this.sessaoUsuario.getUsuario().getId_Codigo();

		if (Util.vazio(codigoUsuario)) {

			codigoUsuario = codigoUsuarioLogado;
		}

		MalaDiretaService malaDiretaService = new MalaDiretaService();
		malaDiretaService.setHibernateUtil(hibernateUtil);
		malaDiretaService.setValidator(validator);

		TreeMap<Integer, MalaDireta> malaDireta = new TreeMap<Integer, MalaDireta>();
		malaDiretaService.pesquisarMalaDiretaComRecursividade(codigoUsuario, malaDireta, 1, "id_Patroc");

		List<ControlePedido> pedidosDaRede = buscarPedidosDaRede(codigoUsuario, malaDireta, dataInicial, dataFinal);

		result.include("controlesPedidos", pedidosDaRede);

		SomatorioControlePedido somatorioControlePedido = calcularSomatorio(pedidosDaRede);
		result.include("somatorioControlePedido", somatorioControlePedido);

		result.include("usuarioPesquisado", this.hibernateUtil.selecionar(new Usuario(codigoUsuario)));
		result.include("dataInicialPesquisada", dataInicial);
		result.include("dataFinalPesquisada", dataFinal);
	}

	private SomatorioControlePedido calcularSomatorio(List<ControlePedido> controlesPedidos) {

		SomatorioControlePedido somatorioControlePedido = new SomatorioControlePedido();

		for (ControlePedido controlePedido : controlesPedidos) {

			somatorioControlePedido.setSomatorioBaseCalculo(somatorioControlePedido.getSomatorioBaseCalculo().add(controlePedido.getBaseCalculo()));
			somatorioControlePedido.setSomatorioPedOutrosValores(somatorioControlePedido.getSomatorioPedOutrosValores().add(controlePedido.getPedOutrosValores()));
			somatorioControlePedido.setSomatorioPedValorPago(somatorioControlePedido.getSomatorioPedValorPago().add(controlePedido.getPedValorPago()));
			somatorioControlePedido.setSomatorioQuantProdutos(somatorioControlePedido.getSomatorioQuantProdutos().add(controlePedido.getQuantProdutos()));
			somatorioControlePedido.setSomatorioPontoProduto(somatorioControlePedido.getSomatorioPontoProduto().add(controlePedido.getPontoProduto()));
			somatorioControlePedido.setSomatorioPontoAtividade(somatorioControlePedido.getSomatorioPontoAtividade().add(controlePedido.getPontoAtividade()));
			somatorioControlePedido.setSomatorioPontoIngresso(somatorioControlePedido.getSomatorioPontoIngresso().add(controlePedido.getPontoIngresso()));
		}

		return somatorioControlePedido;
	}

	public List<ControlePedido> buscarPedidosDaRede(Integer codigoUsuario, TreeMap<Integer, MalaDireta> malaDireta, GregorianCalendar dataInicial, GregorianCalendar dataFinal) {

		List<Integer> idsRede = new ArrayList<Integer>();

		for (Entry<Integer, MalaDireta> malaDiretaEntry : malaDireta.entrySet()) {

			if (!malaDiretaEntry.getValue().getUsuario().getId_Codigo().equals(codigoUsuario)) {

				idsRede.add(malaDiretaEntry.getValue().getUsuario().getId_Codigo());
			}
		}

		if (Util.preenchido(idsRede)) {

			List<Criterion> restricoes = new ArrayList<Criterion>();
			restricoes.add(Restrictions.between("pedData", dataInicial, dataFinal));
			restricoes.add(Restrictions.in("id_Codigo", idsRede));

			List<ControlePedido> pedidos = this.hibernateUtil.buscar(new ControlePedido(), restricoes);

			for (ControlePedido pedido : pedidos) {

				Usuario usuarioDoPedido = this.hibernateUtil.selecionar(new Usuario(pedido.getId_Codigo()));

				pedido.setUsuario(usuarioDoPedido);
			}

			return pedidos;
		}

		return new ArrayList<ControlePedido>();
	}
}
