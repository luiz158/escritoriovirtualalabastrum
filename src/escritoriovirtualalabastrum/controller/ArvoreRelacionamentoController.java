package escritoriovirtualalabastrum.controller;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import escritoriovirtualalabastrum.anotacoes.Funcionalidade;
import escritoriovirtualalabastrum.hibernate.HibernateUtil;
import escritoriovirtualalabastrum.modelo.Usuario;

@Resource
public class ArvoreRelacionamentoController {

	private Result result;
	private HibernateUtil hibernateUtil;

	public ArvoreRelacionamentoController(Result result, HibernateUtil hibernateUtil) {

		this.result = result;
		this.hibernateUtil = hibernateUtil;
	}

	@Funcionalidade
	public void acessarTelaArvoreRelacionamento() {

	}

	@Funcionalidade
	public void buscarUsuariosPatrocinados(Integer codigoUsuario) {

		Usuario usuarioPatrocinadoFiltro = new Usuario();
		usuarioPatrocinadoFiltro.setId_Patroc(codigoUsuario);

		List<Criterion> restricoes = new ArrayList<Criterion>();
		restricoes.add(Restrictions.ne("id_Codigo", codigoUsuario));

		List<Usuario> usuariosPatrocinados = this.hibernateUtil.buscar(usuarioPatrocinadoFiltro, restricoes, Order.asc("vNome"));

		result.use(Results.json()).from(usuariosPatrocinados).serialize();
	}
}
