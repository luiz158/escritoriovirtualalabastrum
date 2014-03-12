package teste;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TreeMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;
import escritoriovirtualalabastrum.auxiliar.BonificacaoIngressoAuxiliar;
import escritoriovirtualalabastrum.auxiliar.MalaDireta;
import escritoriovirtualalabastrum.controller.BonificacaoIngressoController;
import escritoriovirtualalabastrum.hibernate.HibernateUtil;
import escritoriovirtualalabastrum.modelo.FixoIngresso;
import escritoriovirtualalabastrum.modelo.HistoricoKit;
import escritoriovirtualalabastrum.modelo.Pontuacao;
import escritoriovirtualalabastrum.modelo.PorcentagemIngresso;
import escritoriovirtualalabastrum.modelo.Usuario;
import escritoriovirtualalabastrum.service.BonificacaoIngressoService;
import escritoriovirtualalabastrum.service.MalaDiretaService;
import escritoriovirtualalabastrum.sessao.SessaoUsuario;
import escritoriovirtualalabastrum.util.Util;

public class BonificacaoIngressoControllerTest {

	MockResult result;
	MockValidator validator;
	HibernateUtil hibernateUtil;

	@Before
	public void limpaEPopulaBanco() {

		if (hibernateUtil == null) {

			hibernateUtil = new HibernateUtil();
		}

		result = new MockResult();
		validator = new MockValidator();

		limparBanco();
		popularBanco();
	}

	@After
	public void fecharSessao() {

		hibernateUtil.fecharSessao();
	}

	private void limparBanco() {

		hibernateUtil.deletar(hibernateUtil.buscar(new Usuario()));
		hibernateUtil.deletar(hibernateUtil.buscar(new HistoricoKit()));
		hibernateUtil.deletar(hibernateUtil.buscar(new PorcentagemIngresso()));
		hibernateUtil.deletar(hibernateUtil.buscar(new FixoIngresso()));
		hibernateUtil.deletar(hibernateUtil.buscar(new Pontuacao()));
	}

	private void popularBanco() {

		GregorianCalendar data = new GregorianCalendar(2014, 02, 01);

		adicionarUsuarios(data);
		adicionarHistoricoKit(data);
		salvarPorcentagemIngresso(data);
		adicionarFixoIngresso(data);
		adicionarPontuacao(data);
	}

	private void adicionarPontuacao(GregorianCalendar data) {

		Pontuacao pontuacao77466 = new Pontuacao();
		pontuacao77466.setDt_Pontos(data);
		pontuacao77466.setId_Codigo(77466);
		pontuacao77466.setParametroAtividade(new BigDecimal("10"));
		pontuacao77466.setParametroProduto(new BigDecimal("10"));
		pontuacao77466.setPntAtividade(new BigDecimal("20000"));
		pontuacao77466.setPntIngresso(new BigDecimal("20000"));
		pontuacao77466.setPntProduto(new BigDecimal("20000"));

		Pontuacao pontuacao77479 = new Pontuacao();
		pontuacao77479.setDt_Pontos(data);
		pontuacao77479.setId_Codigo(77479);
		pontuacao77479.setParametroAtividade(new BigDecimal("10"));
		pontuacao77479.setParametroProduto(new BigDecimal("10"));
		pontuacao77479.setPntAtividade(new BigDecimal("10000"));
		pontuacao77479.setPntIngresso(new BigDecimal("10000"));
		pontuacao77479.setPntProduto(new BigDecimal("10000"));

		hibernateUtil.salvarOuAtualizar(pontuacao77466);
		hibernateUtil.salvarOuAtualizar(pontuacao77479);
	}

	private void adicionarFixoIngresso(GregorianCalendar data) {

		FixoIngresso fixoIngresso2 = new FixoIngresso();
		fixoIngresso2.setData_referencia(data);
		fixoIngresso2.setGeracao("2");
		fixoIngresso2.setBasico(new BigDecimal("1"));
		fixoIngresso2.setEspecial(new BigDecimal("2"));
		fixoIngresso2.setVip(new BigDecimal("3"));
		fixoIngresso2.setTop(new BigDecimal("4"));

		FixoIngresso fixoIngresso3 = new FixoIngresso();
		fixoIngresso3.setData_referencia(data);
		fixoIngresso3.setGeracao("3");
		fixoIngresso3.setBasico(new BigDecimal("10"));
		fixoIngresso3.setEspecial(new BigDecimal("10"));
		fixoIngresso3.setVip(new BigDecimal("10"));
		fixoIngresso3.setTop(new BigDecimal("10"));

		FixoIngresso fixoIngressoDiamante = new FixoIngresso();
		fixoIngressoDiamante.setData_referencia(data);
		fixoIngressoDiamante.setGeracao("Diamante");
		fixoIngressoDiamante.setBasico(new BigDecimal("10"));
		fixoIngressoDiamante.setEspecial(new BigDecimal("15"));
		fixoIngressoDiamante.setVip(new BigDecimal("20"));
		fixoIngressoDiamante.setTop(new BigDecimal("50"));

		hibernateUtil.salvarOuAtualizar(fixoIngresso2);
		hibernateUtil.salvarOuAtualizar(fixoIngresso3);
		hibernateUtil.salvarOuAtualizar(fixoIngressoDiamante);
	}

	private void salvarPorcentagemIngresso(GregorianCalendar data) {

		PorcentagemIngresso porcentagemIngresso = new PorcentagemIngresso();
		porcentagemIngresso.setData_referencia(data);
		porcentagemIngresso.setBasico_pontuacao(new BigDecimal("45"));
		porcentagemIngresso.setBasico_porcentagem(new BigDecimal("7"));
		porcentagemIngresso.setEspecial_pontuacao(new BigDecimal("56"));
		porcentagemIngresso.setEspecial_porcentagem(new BigDecimal("10"));
		porcentagemIngresso.setVip_pontuacao(new BigDecimal("44"));
		porcentagemIngresso.setVip_porcentagem(new BigDecimal("20"));
		porcentagemIngresso.setTop_pontuacao(new BigDecimal("56"));
		porcentagemIngresso.setTop_porcentagem(new BigDecimal("40"));

		hibernateUtil.salvarOuAtualizar(porcentagemIngresso);
	}

	private void adicionarHistoricoKit(GregorianCalendar data) {

		HistoricoKit historicoKit1 = new HistoricoKit();
		historicoKit1.setData_referencia(data);
		historicoKit1.setId_Codigo(77466);
		historicoKit1.setKit("especial");

		HistoricoKit historicoKit2 = new HistoricoKit();
		historicoKit2.setData_referencia(data);
		historicoKit2.setId_Codigo(77479);
		historicoKit2.setKit("basico");

		HistoricoKit historicoKit3 = new HistoricoKit();
		historicoKit3.setData_referencia(data);
		historicoKit3.setId_Codigo(77495);
		historicoKit3.setKit("top");

		HistoricoKit historicoKit4 = new HistoricoKit();
		historicoKit4.setData_referencia(data);
		historicoKit4.setId_Codigo(77501);
		historicoKit4.setKit("especial");

		HistoricoKit historicoKit5 = new HistoricoKit();
		historicoKit5.setData_referencia(data);
		historicoKit5.setId_Codigo(77514);
		historicoKit5.setKit("top");

		HistoricoKit historicoKit6 = new HistoricoKit();
		historicoKit6.setData_referencia(data);
		historicoKit6.setId_Codigo(77527);
		historicoKit6.setKit("vip");

		HistoricoKit historicoKit7 = new HistoricoKit();
		historicoKit7.setData_referencia(data);
		historicoKit7.setId_Codigo(77453);
		historicoKit7.setKit("vip");

		hibernateUtil.salvarOuAtualizar(historicoKit1);
		hibernateUtil.salvarOuAtualizar(historicoKit2);
		hibernateUtil.salvarOuAtualizar(historicoKit3);
		hibernateUtil.salvarOuAtualizar(historicoKit4);
		hibernateUtil.salvarOuAtualizar(historicoKit5);
		hibernateUtil.salvarOuAtualizar(historicoKit6);
		hibernateUtil.salvarOuAtualizar(historicoKit7);
	}

	private void adicionarUsuarios(GregorianCalendar data) {

		Usuario usuario77453 = new Usuario(77453);
		usuario77453.setPosAtual(MalaDiretaService.DIAMANTE);

		Usuario usuario77466 = new Usuario(77466);
		usuario77466.setPosAtual(MalaDiretaService.DIAMANTE);
		usuario77466.setId_Patroc(77453);
		usuario77466.setId_CR(77453);
		usuario77466.setDt_Ingresso(data);

		Usuario usuario77479 = new Usuario(77479);
		usuario77479.setPosAtual("Bucha");
		usuario77479.setId_Patroc(77466);
		usuario77479.setId_CR(77466);
		usuario77479.setDt_Ingresso(data);

		Usuario usuario77495 = new Usuario(77495);
		usuario77495.setPosAtual("Troxa");
		usuario77495.setId_Patroc(77479);
		usuario77495.setId_CR(77466);
		usuario77495.setDt_Ingresso(data);

		Usuario usuario77501 = new Usuario(77501);
		usuario77501.setPosAtual("Troxa");
		usuario77501.setId_Patroc(77479);
		usuario77501.setId_CR(77466);
		usuario77501.setDt_Ingresso(data);

		Usuario usuario77514 = new Usuario(77514);
		usuario77514.setPosAtual("Bucha");
		usuario77514.setId_Patroc(77479);
		usuario77514.setId_CR(77466);
		usuario77514.setDt_Ingresso(data);

		Usuario usuario77527 = new Usuario(77527);
		usuario77527.setPosAtual("Bucha");
		usuario77527.setId_Patroc(77479);
		usuario77527.setId_CR(77466);
		usuario77527.setDt_Ingresso(data);

		hibernateUtil.salvarOuAtualizar(usuario77453);
		hibernateUtil.salvarOuAtualizar(usuario77466);
		hibernateUtil.salvarOuAtualizar(usuario77479);
		hibernateUtil.salvarOuAtualizar(usuario77495);
		hibernateUtil.salvarOuAtualizar(usuario77501);
		hibernateUtil.salvarOuAtualizar(usuario77514);
		hibernateUtil.salvarOuAtualizar(usuario77527);
	}

	private MockResult gerarRelatorio(int id_Codigo) {

		SessaoUsuario sessaoUsuario = new SessaoUsuario();
		sessaoUsuario.login((Usuario) hibernateUtil.selecionar(new Usuario(id_Codigo)));

		MockResult mockResult = new MockResult();

		BonificacaoIngressoController controller = new BonificacaoIngressoController(mockResult, hibernateUtil, sessaoUsuario, validator);
		controller.setRealizarValidacoes(false);
		controller.gerarRelatorioBonificacaoIngresso(2014, 3);

		return mockResult;
	}

	@Test
	public void gerarRelatorioBonificacao77479() {

		int id_Codigo = 77479;

		MockResult mockResult = gerarRelatorio(id_Codigo);

		List<BonificacaoIngressoAuxiliar> bonificacoes = mockResult.included("bonificacoes");

		assertEquals(4, bonificacoes.size());

		assertEquals(new Integer("77495"), bonificacoes.get(0).getUsuario().getId_Codigo());
		assertEquals(new Integer("1"), bonificacoes.get(0).getGeracao());
		assertEquals("3,92", Util.formatarBigDecimal(bonificacoes.get(0).getBonificacao()));
		assertEquals("7% de 56", bonificacoes.get(0).getComoFoiCalculado());

		assertEquals(new Integer("77501"), bonificacoes.get(1).getUsuario().getId_Codigo());
		assertEquals(new Integer("1"), bonificacoes.get(1).getGeracao());
		assertEquals("3,92", Util.formatarBigDecimal(bonificacoes.get(1).getBonificacao()));
		assertEquals("7% de 56", bonificacoes.get(1).getComoFoiCalculado());

		assertEquals(new Integer("77527"), bonificacoes.get(3).getUsuario().getId_Codigo());
		assertEquals(new Integer("1"), bonificacoes.get(3).getGeracao());
		assertEquals("3,08", Util.formatarBigDecimal(bonificacoes.get(3).getBonificacao()));
		assertEquals("7% de 44", bonificacoes.get(3).getComoFoiCalculado());

		assertEquals("14,84", Util.formatarBigDecimal((BigDecimal) mockResult.included("somatorioBonificacao")));
	}

	@Test
	public void gerarRelatorioBonificacao77466() {

		BonificacaoIngressoService.META_DIAMANTE_LINHAS_GRADUADOS = 1;

		int id_Codigo = 77466;

		MockResult mockResult = gerarRelatorio(id_Codigo);

		List<BonificacaoIngressoAuxiliar> bonificacoes = mockResult.included("bonificacoes");

		assertEquals(10, bonificacoes.size());

		assertEquals(new Integer("77479"), bonificacoes.get(0).getUsuario().getId_Codigo());
		assertEquals(new Integer("1"), bonificacoes.get(0).getGeracao());
		assertEquals("4,50", Util.formatarBigDecimal(bonificacoes.get(0).getBonificacao()));
		assertEquals("10% de 45", bonificacoes.get(0).getComoFoiCalculado());

		assertEquals(new Integer("77495"), bonificacoes.get(1).getUsuario().getId_Codigo());
		assertEquals(new Integer("2"), bonificacoes.get(1).getGeracao());
		assertEquals("top", bonificacoes.get(1).getKit());
		assertEquals("4", Util.formatarBigDecimal(bonificacoes.get(1).getBonificacao()));

		assertEquals(new Integer("77501"), bonificacoes.get(2).getUsuario().getId_Codigo());
		assertEquals(new Integer("2"), bonificacoes.get(2).getGeracao());
		assertEquals("especial", bonificacoes.get(2).getKit());
		assertEquals("2", Util.formatarBigDecimal(bonificacoes.get(2).getBonificacao()));

		assertEquals(new Integer("77514"), bonificacoes.get(3).getUsuario().getId_Codigo());
		assertEquals(new Integer("2"), bonificacoes.get(3).getGeracao());
		assertEquals("top", bonificacoes.get(3).getKit());
		assertEquals("4", Util.formatarBigDecimal(bonificacoes.get(3).getBonificacao()));

		assertEquals(new Integer("77527"), bonificacoes.get(4).getUsuario().getId_Codigo());
		assertEquals(new Integer("2"), bonificacoes.get(4).getGeracao());
		assertEquals("vip", bonificacoes.get(4).getKit());
		assertEquals("3", Util.formatarBigDecimal(bonificacoes.get(4).getBonificacao()));

		assertEquals(new Integer("77479"), bonificacoes.get(5).getUsuario().getId_Codigo());
		assertEquals("basico", bonificacoes.get(5).getKit());
		assertEquals("10", Util.formatarBigDecimal(bonificacoes.get(5).getBonificacao()));

		assertEquals(new Integer("77495"), bonificacoes.get(6).getUsuario().getId_Codigo());
		assertEquals("top", bonificacoes.get(6).getKit());
		assertEquals("50", Util.formatarBigDecimal(bonificacoes.get(6).getBonificacao()));

		assertEquals(new Integer("77501"), bonificacoes.get(7).getUsuario().getId_Codigo());
		assertEquals("especial", bonificacoes.get(7).getKit());
		assertEquals("15", Util.formatarBigDecimal(bonificacoes.get(7).getBonificacao()));

		assertEquals(new Integer("77514"), bonificacoes.get(8).getUsuario().getId_Codigo());
		assertEquals("top", bonificacoes.get(8).getKit());
		assertEquals("50", Util.formatarBigDecimal(bonificacoes.get(8).getBonificacao()));

		assertEquals(new Integer("77527"), bonificacoes.get(9).getUsuario().getId_Codigo());
		assertEquals("vip", bonificacoes.get(9).getKit());
		assertEquals("20", Util.formatarBigDecimal(bonificacoes.get(9).getBonificacao()));

		assertEquals("162,50", Util.formatarBigDecimal((BigDecimal) mockResult.included("somatorioBonificacao")));

		assertEquals(new BigDecimal("90000"), mockResult.included("pontuacaoAlcancadaPeloDiamante"));
		assertEquals(new Integer("1"), mockResult.included("graduadosAlcancadosPeloDiamante"));

		List<BonificacaoIngressoAuxiliar> bonificacoesDiamante = mockResult.included("bonificacoesDiamante");
		assertEquals(4, bonificacoesDiamante.size());

		assertEquals(new Integer("77495"), bonificacoesDiamante.get(0).getUsuario().getId_Codigo());
		assertEquals("18,48", Util.formatarBigDecimal(bonificacoesDiamante.get(0).getBonificacao()));
		assertEquals("33% de 56", bonificacoesDiamante.get(0).getComoFoiCalculado());

		assertEquals(new Integer("77501"), bonificacoesDiamante.get(1).getUsuario().getId_Codigo());
		assertEquals("18,48", Util.formatarBigDecimal(bonificacoesDiamante.get(1).getBonificacao()));
		assertEquals("33% de 56", bonificacoesDiamante.get(1).getComoFoiCalculado());

		assertEquals(new Integer("77514"), bonificacoesDiamante.get(2).getUsuario().getId_Codigo());
		assertEquals("18,48", Util.formatarBigDecimal(bonificacoesDiamante.get(2).getBonificacao()));
		assertEquals("33% de 56", bonificacoesDiamante.get(2).getComoFoiCalculado());

		assertEquals(new Integer("77527"), bonificacoesDiamante.get(3).getUsuario().getId_Codigo());
		assertEquals("14,52", Util.formatarBigDecimal(bonificacoesDiamante.get(3).getBonificacao()));
		assertEquals("33% de 44", bonificacoesDiamante.get(3).getComoFoiCalculado());

		assertEquals("69,96", Util.formatarBigDecimal((BigDecimal) mockResult.included("somatorioBonificacoesDiamante")));
	}

	@Test
	public void gerarRelatorioBonificacao77453() {

		int id_Codigo = 77453;

		MockResult mockResult = gerarRelatorio(id_Codigo);

		List<BonificacaoIngressoAuxiliar> bonificacoes = mockResult.included("bonificacoes");

		assertEquals(7, bonificacoes.size());

		assertEquals(new Integer("77466"), bonificacoes.get(0).getUsuario().getId_Codigo());
		assertEquals(new Integer("1"), bonificacoes.get(0).getGeracao());
		assertEquals("11,20", Util.formatarBigDecimal(bonificacoes.get(0).getBonificacao()));
		assertEquals("20% de 56", bonificacoes.get(0).getComoFoiCalculado());

		assertEquals(new Integer("77479"), bonificacoes.get(1).getUsuario().getId_Codigo());
		assertEquals(new Integer("2"), bonificacoes.get(1).getGeracao());
		assertEquals("1", Util.formatarBigDecimal(bonificacoes.get(1).getBonificacao()));

		assertEquals(new Integer("77495"), bonificacoes.get(2).getUsuario().getId_Codigo());
		assertEquals(new Integer("3"), bonificacoes.get(2).getGeracao());
		assertEquals("10", Util.formatarBigDecimal(bonificacoes.get(2).getBonificacao()));

		assertEquals(new Integer("77501"), bonificacoes.get(3).getUsuario().getId_Codigo());
		assertEquals(new Integer("3"), bonificacoes.get(3).getGeracao());
		assertEquals("10", Util.formatarBigDecimal(bonificacoes.get(3).getBonificacao()));

		assertEquals(new Integer("77514"), bonificacoes.get(4).getUsuario().getId_Codigo());
		assertEquals(new Integer("3"), bonificacoes.get(4).getGeracao());
		assertEquals("10", Util.formatarBigDecimal(bonificacoes.get(4).getBonificacao()));

		assertEquals(new Integer("77527"), bonificacoes.get(5).getUsuario().getId_Codigo());
		assertEquals(new Integer("3"), bonificacoes.get(5).getGeracao());
		assertEquals("10", Util.formatarBigDecimal(bonificacoes.get(5).getBonificacao()));

		assertEquals(new Integer("77466"), bonificacoes.get(6).getUsuario().getId_Codigo());
		assertEquals("15", Util.formatarBigDecimal(bonificacoes.get(6).getBonificacao()));

		assertEquals("67,20", Util.formatarBigDecimal((BigDecimal) mockResult.included("somatorioBonificacao")));

		TreeMap<Integer, MalaDireta> diamantesComMetasAlcancadasHash = mockResult.included("diamantesComMetasAlcancadas");

		List<MalaDireta> diamantesComMetasAlcancadas = new ArrayList<MalaDireta>();

		for (MalaDireta malaDireta : diamantesComMetasAlcancadasHash.values()) {

			diamantesComMetasAlcancadas.add(malaDireta);
		}

		assertEquals(1, diamantesComMetasAlcancadas.size());

		assertEquals(new Integer("77466"), diamantesComMetasAlcancadas.get(0).getUsuario().getId_Codigo());
	}
}
