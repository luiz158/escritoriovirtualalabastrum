package escritoriovirtualalabastrum.controller;

import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import escritoriovirtualalabastrum.anotacoes.Funcionalidade;
import escritoriovirtualalabastrum.hibernate.HibernateUtil;
import escritoriovirtualalabastrum.modelo.Usuario;
import escritoriovirtualalabastrum.sessao.SessaoUsuario;

@Resource
public class CadastrosPendentesController {

	private Result result;
	private HibernateUtil hibernateUtil;
	private SessaoUsuario sessaoUsuario;

	public CadastrosPendentesController(Result result, HibernateUtil hibernateUtil, SessaoUsuario sessaoUsuario) {

		this.result = result;
		this.hibernateUtil = hibernateUtil;
		this.sessaoUsuario = sessaoUsuario;
	}

	@Funcionalidade
	public void gerarCadastrosPendentes() {

		Usuario usuarioFiltro = new Usuario();
		usuarioFiltro.setCadPosRede(0);
		usuarioFiltro.setId_Indicante(this.sessaoUsuario.getUsuario().getId_Codigo());

		result.include("usuarios", this.hibernateUtil.buscar(usuarioFiltro));
	}
}
