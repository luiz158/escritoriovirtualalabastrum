package escritoriovirtualalabastrum.geradorCrud.controller;

import java.util.List;

import escritoriovirtualalabastrum.anotacoes.Funcionalidade;
import escritoriovirtualalabastrum.geradorCrud.modelo.OIAHPSODFIH349823OISHFD;
import escritoriovirtualalabastrum.hibernate.HibernateUtil;
import escritoriovirtualalabastrum.sessao.SessaoGeral;
import escritoriovirtualalabastrum.util.Util;
import escritoriovirtualalabastrum.util.UtilController;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;

@Resource
public class OIAHPSODFIH349823OISHFDController {

	private final Result result;
	private SessaoGeral sessaoGeral;
	private HibernateUtil hibernateUtil;

	public OIAHPSODFIH349823OISHFDController(Result result, SessaoGeral sessaoGeral, HibernateUtil hibernateUtil) {
		this.result = result;
		this.sessaoGeral = sessaoGeral;
		this.hibernateUtil = hibernateUtil;
		this.hibernateUtil.setResult(result);
	}

	@Funcionalidade(administrativa = "true")
	public void criarOIAHPSODFIH349823OISHFD() {

		sessaoGeral.adicionar("idOIAHPSODFIH349823OISHFD", null);
		result.forwardTo(this).criarEditarOIAHPSODFIH349823OISHFD();
	}

	@Path("/mohoidfgoih98745oihdog/editarOIAHPSODFIH349823OISHFD/{mohoidfgoih98745oihdog.id}")
	@Funcionalidade(administrativa = "true")
	public void editarOIAHPSODFIH349823OISHFD(OIAHPSODFIH349823OISHFD mohoidfgoih98745oihdog) {

		mohoidfgoih98745oihdog = hibernateUtil.selecionar(mohoidfgoih98745oihdog);

		sessaoGeral.adicionar("idOIAHPSODFIH349823OISHFD", mohoidfgoih98745oihdog.getId());
		result.include(mohoidfgoih98745oihdog);
		result.forwardTo(this).criarEditarOIAHPSODFIH349823OISHFD();
	}

	@Funcionalidade(administrativa = "true")
	public void criarEditarOIAHPSODFIH349823OISHFD() {
	}

	@Path("/mohoidfgoih98745oihdog/excluirOIAHPSODFIH349823OISHFD/{mohoidfgoih98745oihdog.id}")
	@Funcionalidade(administrativa = "true")
	public void excluirOIAHPSODFIH349823OISHFD(OIAHPSODFIH349823OISHFD mohoidfgoih98745oihdog) {

		hibernateUtil.deletar(mohoidfgoih98745oihdog);
		result.include("sucesso", "OIAHPSODFIH349823OISHFD excluído(a) com sucesso");
		result.forwardTo(this).listarOIAHPSODFIH349823OISHFDs(null, null);
	}

	@Funcionalidade(administrativa = "true")
	public void salvarOIAHPSODFIH349823OISHFD(OIAHPSODFIH349823OISHFD mohoidfgoih98745oihdog) {

		if (Util.preenchido(sessaoGeral.getValor("idOIAHPSODFIH349823OISHFD"))) {

			mohoidfgoih98745oihdog.setId((Integer) sessaoGeral.getValor("idOIAHPSODFIH349823OISHFD"));
		}

		hibernateUtil.salvarOuAtualizar(mohoidfgoih98745oihdog);
		result.include("sucesso", "OIAHPSODFIH349823OISHFD salvo(a) com sucesso");
		result.redirectTo(this).listarOIAHPSODFIH349823OISHFDs(new OIAHPSODFIH349823OISHFD(), null);
	}

	@Funcionalidade(administrativa = "true")
	public void listarOIAHPSODFIH349823OISHFDs(OIAHPSODFIH349823OISHFD mohoidfgoih98745oihdog, Integer pagina) {

		mohoidfgoih98745oihdog = (OIAHPSODFIH349823OISHFD) UtilController.preencherFiltros(mohoidfgoih98745oihdog, "mohoidfgoih98745oihdog", sessaoGeral);
		if (Util.vazio(mohoidfgoih98745oihdog)) {
			mohoidfgoih98745oihdog = new OIAHPSODFIH349823OISHFD();
		}

		List<OIAHPSODFIH349823OISHFD> mohoidfgoih98745oihdogs = hibernateUtil.buscar(mohoidfgoih98745oihdog, pagina);
		result.include("mohoidfgoih98745oihdogs", mohoidfgoih98745oihdogs);

	}
}
