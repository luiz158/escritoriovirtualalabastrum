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
public class ArvoreRelacionamento2Controller {

	private Result result;
	private HibernateUtil hibernateUtil;
	private SessaoUsuario sessaoUsuario;

	public ArvoreRelacionamento2Controller(Result result, HibernateUtil hibernateUtil, SessaoUsuario sessaoUsuario) {

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
		acharPatrocinadosRecursivamente(usuario);

		StringBuilder htmlUsuarios = new StringBuilder();
		gerarHtmlUsuarios(usuario, htmlUsuarios);
		result.include("htmlUsuarios", htmlUsuarios.toString());
	}

	private void acharPatrocinadosRecursivamente(Usuario usuario) {

		Usuario usuarioPatrocinadoFiltro = new Usuario();
		usuarioPatrocinadoFiltro.setId_Patroc(usuario.getId_Codigo());

		List<Criterion> restricoes = new ArrayList<Criterion>();
		restricoes.add(Restrictions.ne("id_Codigo", usuario.getId_Codigo()));

		List<Usuario> usuariosPatrocinados = this.hibernateUtil.buscar(usuarioPatrocinadoFiltro, restricoes, Order.asc("cadPosRede"));

		for (Usuario usuarioPatrocinado : usuariosPatrocinados) {

			usuarioPatrocinado.calcularPontuacao();
			acharPatrocinadosRecursivamente(usuarioPatrocinado);
		}

		usuario.setUsuariosPatrocinados(usuariosPatrocinados);
	}

	private void gerarHtmlUsuarios(Usuario usuario, StringBuilder htmlUsuarios) {

		htmlUsuarios.append("<div id='" + usuario.getId_Codigo() + "'>");
		htmlUsuarios.append(usuario.getApelido());
		htmlUsuarios.append("</div>");

		if (Util.preenchido(usuario.getUsuariosPatrocinados())) {

			htmlUsuarios.append("<ul>");
			for (Usuario usuarioPatrocinado : usuario.getUsuariosPatrocinados()) {

				htmlUsuarios.append("<li>");
				gerarHtmlUsuarios(usuarioPatrocinado, htmlUsuarios);
				htmlUsuarios.append("</li>");
			}
			htmlUsuarios.append("</ul>");
		}
	}

	@Funcionalidade
	public void buscarInformacoesUsuarioSelecionado(Integer codigoUsuario) {

		Usuario usuario = new Usuario(codigoUsuario);

		usuario = this.hibernateUtil.selecionar(usuario, MatchMode.EXACT);

		usuario.calcularPontuacao();

		result.use(Results.json()).from(usuario).include("pontuacaoAuxiliar").serialize();
	}
}
