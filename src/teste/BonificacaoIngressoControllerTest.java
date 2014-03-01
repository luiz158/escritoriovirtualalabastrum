package teste;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.GregorianCalendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;
import escritoriovirtualalabastrum.controller.BonificacaoIngressoController;
import escritoriovirtualalabastrum.hibernate.HibernateUtil;
import escritoriovirtualalabastrum.modelo.FixoIngresso;
import escritoriovirtualalabastrum.modelo.HistoricoKit;
import escritoriovirtualalabastrum.modelo.Pontuacao;
import escritoriovirtualalabastrum.modelo.PorcentagemIngresso;
import escritoriovirtualalabastrum.modelo.Usuario;
import escritoriovirtualalabastrum.service.MalaDiretaService;
import escritoriovirtualalabastrum.sessao.SessaoUsuario;

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

		GregorianCalendar data = new GregorianCalendar(2014, 01, 01);

		adicionarUsuarios();
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
		pontuacao77466.setPntAtividade(new BigDecimal("10000"));
		pontuacao77466.setPntIngresso(new BigDecimal("10000"));
		pontuacao77466.setPntProduto(new BigDecimal("10000"));

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
		fixoIngresso2.setBasico(new BigDecimal("10"));
		fixoIngresso2.setEspecial(new BigDecimal("10"));
		fixoIngresso2.setVip(new BigDecimal("10"));
		fixoIngresso2.setTop(new BigDecimal("10"));

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
		historicoKit1.setId_codigo(77466);
		historicoKit1.setKit("especial");

		HistoricoKit historicoKit2 = new HistoricoKit();
		historicoKit2.setData_referencia(data);
		historicoKit2.setId_codigo(77479);
		historicoKit2.setKit("basico");

		HistoricoKit historicoKit3 = new HistoricoKit();
		historicoKit3.setData_referencia(data);
		historicoKit3.setId_codigo(77495);
		historicoKit3.setKit("top");

		HistoricoKit historicoKit4 = new HistoricoKit();
		historicoKit4.setData_referencia(data);
		historicoKit4.setId_codigo(77501);
		historicoKit4.setKit("especial");

		HistoricoKit historicoKit5 = new HistoricoKit();
		historicoKit5.setData_referencia(data);
		historicoKit5.setId_codigo(77514);
		historicoKit5.setKit("top");

		HistoricoKit historicoKit6 = new HistoricoKit();
		historicoKit6.setData_referencia(data);
		historicoKit6.setId_codigo(77527);
		historicoKit6.setKit("vip");

		hibernateUtil.salvarOuAtualizar(historicoKit1);
		hibernateUtil.salvarOuAtualizar(historicoKit2);
		hibernateUtil.salvarOuAtualizar(historicoKit3);
		hibernateUtil.salvarOuAtualizar(historicoKit4);
		hibernateUtil.salvarOuAtualizar(historicoKit5);
		hibernateUtil.salvarOuAtualizar(historicoKit6);
	}

	private void adicionarUsuarios() {

		Usuario usuario77466 = new Usuario(77466);
		usuario77466.setPosAtual(MalaDiretaService.DIAMANTE);

		Usuario usuario77479 = new Usuario(77479);
		usuario77479.setId_Patroc(77466);

		Usuario usuario77495 = new Usuario(77495);
		usuario77495.setId_Patroc(77479);

		Usuario usuario77501 = new Usuario(77501);
		usuario77501.setId_Patroc(77479);

		Usuario usuario77514 = new Usuario(77514);
		usuario77514.setId_Patroc(77479);

		Usuario usuario77527 = new Usuario(77527);
		usuario77527.setId_Patroc(77479);

		hibernateUtil.salvarOuAtualizar(usuario77466);
		hibernateUtil.salvarOuAtualizar(usuario77479);
		hibernateUtil.salvarOuAtualizar(usuario77495);
		hibernateUtil.salvarOuAtualizar(usuario77501);
		hibernateUtil.salvarOuAtualizar(usuario77514);
		hibernateUtil.salvarOuAtualizar(usuario77527);
	}

	@Test
	public void FDAOIFHAOSDFHDPASOFHDOASIHFDASF() {

		SessaoUsuario sessaoUsuario = new SessaoUsuario();
		sessaoUsuario.login((Usuario) hibernateUtil.selecionar(new Usuario(77466)));

		MockResult mockResult = new MockResult();

		BonificacaoIngressoController controller = new BonificacaoIngressoController(mockResult, hibernateUtil, sessaoUsuario, validator);

		controller.gerarRelatorioBonificacao(2014, 2);

		assertEquals(new BigDecimal("1000000"), mockResult.included("somatorioBonificacao"));
	}
}
