package escritoriovirtualalabastrum.controller;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import escritoriovirtualalabastrum.anotacoes.Funcionalidade;
import escritoriovirtualalabastrum.auxiliar.SomatorioControlePedido;
import escritoriovirtualalabastrum.hibernate.HibernateUtil;
import escritoriovirtualalabastrum.modelo.ControlePedido;
import escritoriovirtualalabastrum.modelo.Usuario;
import escritoriovirtualalabastrum.sessao.SessaoUsuario;
import escritoriovirtualalabastrum.util.Util;

@Resource
public class ControlePedidoController {

	private Result result;
	private HibernateUtil hibernateUtil;
	private SessaoUsuario sessaoUsuario;

	public ControlePedidoController(Result result, HibernateUtil hibernateUtil, SessaoUsuario sessaoUsuario) {

		this.result = result;
		this.hibernateUtil = hibernateUtil;
		this.sessaoUsuario = sessaoUsuario;
	}

	@Funcionalidade
	public void acessarTelaControlePedido() {

		DateTime hoje = new DateTime();
		DateTime primeiroDiaDoMesAtual = hoje.withDayOfMonth(1);

		result.include("primeiroDiaMes", primeiroDiaDoMesAtual.toGregorianCalendar());
		result.include("dataAtual", hoje.toGregorianCalendar());
	}

	@Funcionalidade
	@Path("/controlePedido/gerarRecebendoAnoEMes")
	public void gerarRelatorioControlePedido(Integer ano, Integer mes) {

		DateTime dataInicial = new DateTime(ano, mes, 1, 0, 0, 0);
		DateTime dataFinal = new DateTime(ano, mes, dataInicial.dayOfMonth().withMaximumValue().dayOfMonth().get(), 0, 0, 0);

		gerarRelatorioControlePedido(null, dataInicial.toGregorianCalendar(), dataFinal.toGregorianCalendar());
	}

	@Funcionalidade
	@Path("/controlePedido/gerarRecebendoDataInicialEFinal")
	public void gerarRelatorioControlePedido(Integer codigoUsuario, GregorianCalendar dataInicial, GregorianCalendar dataFinal) {

		Integer codigoUsuarioLogado = this.sessaoUsuario.getUsuario().getId_Codigo();

		if (Util.vazio(codigoUsuario)) {

			codigoUsuario = codigoUsuarioLogado;
		}

		List<Criterion> restricoes = new ArrayList<Criterion>();
		restricoes.add(Restrictions.between("pedData", dataInicial, dataFinal));

		ControlePedido controlePedidoFiltro = new ControlePedido();
		controlePedidoFiltro.setId_Codigo(codigoUsuario);

		List<ControlePedido> controlesPedidos = this.hibernateUtil.buscar(controlePedidoFiltro, restricoes);
		result.include("controlesPedidos", controlesPedidos);

		SomatorioControlePedido somatorioControlePedido = calcularSomatorio(controlesPedidos);
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
}
