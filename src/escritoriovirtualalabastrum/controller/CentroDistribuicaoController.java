package escritoriovirtualalabastrum.controller;

import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import escritoriovirtualalabastrum.anotacoes.Funcionalidade;
import escritoriovirtualalabastrum.hibernate.HibernateUtil;
import escritoriovirtualalabastrum.modelo.CentroDistribuicao;

@Resource
public class CentroDistribuicaoController {

	private Result result;
	private HibernateUtil hibernateUtil;

	public CentroDistribuicaoController(Result result, HibernateUtil hibernateUtil) {

		this.result = result;
		this.hibernateUtil = hibernateUtil;
	}

	@Funcionalidade
	public void listarCentrosDistribuicao() {

		result.include("centrosDistribuicao", this.hibernateUtil.buscar(new CentroDistribuicao()));
	}

}
