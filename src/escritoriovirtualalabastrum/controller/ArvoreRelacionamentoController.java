package escritoriovirtualalabastrum.controller;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import escritoriovirtualalabastrum.anotacoes.Funcionalidade;
import escritoriovirtualalabastrum.hibernate.HibernateUtil;
import escritoriovirtualalabastrum.modelo.Usuario;
import escritoriovirtualalabastrum.sessao.SessaoUsuario;
import escritoriovirtualalabastrum.util.Util;

@Resource
public class ArvoreRelacionamentoController {

	private Result result;
	private HibernateUtil hibernateUtil;
	private SessaoUsuario sessaoUsuario;

	public ArvoreRelacionamentoController(Result result, HibernateUtil hibernateUtil, SessaoUsuario sessaoUsuario) {

		this.result = result;
		this.hibernateUtil = hibernateUtil;
		this.sessaoUsuario = sessaoUsuario;
	}

	@Funcionalidade
	public void acessarTelaArvoreRelacionamento() {

	}

	@Funcionalidade
	public void gerarArvoreRelacionamento(Integer codigo) {

		if (Util.vazio(codigo)) {

			codigo = this.sessaoUsuario.getUsuario().getId_Codigo();
		}

		Usuario usuario = this.hibernateUtil.selecionar(new Usuario(codigo));

		usuario.calcularPontuacao();

		result.include("usuarioSelecionado", usuario);
	}

	@Funcionalidade
	public void buscarUsuariosPatrocinados(Integer codigoUsuario) {

		Usuario usuarioPatrocinadoFiltro = new Usuario();
		usuarioPatrocinadoFiltro.setId_Patroc(codigoUsuario);

		List<Criterion> restricoes = new ArrayList<Criterion>();
		restricoes.add(Restrictions.ne("id_Codigo", codigoUsuario));

		List<Usuario> usuariosPatrocinados = this.hibernateUtil.buscar(usuarioPatrocinadoFiltro, restricoes, Order.asc("id_Codigo"));

		for (Usuario usuario : usuariosPatrocinados) {

			usuario.calcularPontuacao();
		}

		result.use(Results.json()).from(usuariosPatrocinados).include("pontuacaoAuxiliar").serialize();
	}

	@Funcionalidade
	public void buscarInformacoesUsuarioSelecionado(Integer codigoUsuario) {

		Usuario usuario = new Usuario(codigoUsuario);

		usuario = this.hibernateUtil.selecionar(usuario, MatchMode.EXACT);

		usuario.calcularPontuacao();

		result.use(Results.json()).from(usuario).include("pontuacaoAuxiliar").serialize();
	}
}
