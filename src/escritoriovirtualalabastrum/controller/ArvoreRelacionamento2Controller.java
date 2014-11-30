package escritoriovirtualalabastrum.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

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
		acharPatrocinadosRecursivamente(usuario, 1);

		TreeMap<Integer, Integer> niveis = new TreeMap<Integer, Integer>();
		acharNiveis(usuario, niveis);
		result.include("niveis", niveis);
		result.include("previstos", getPrevistos());

		Integer total = 0;
		for (Entry<Integer, Integer> nivel : niveis.entrySet()) {
			total += nivel.getValue();
		}
		result.include("total", total);

		StringBuilder htmlUsuarios = new StringBuilder();
		gerarHtmlUsuarios(usuario, htmlUsuarios);
		result.include("htmlUsuarios", htmlUsuarios.toString());
	}

	private HashMap<Integer, Integer> getPrevistos() {

		HashMap<Integer, Integer> previstos = new HashMap<Integer, Integer>();
		previstos.put(1, 3);
		previstos.put(2, 9);
		previstos.put(3, 27);
		previstos.put(4, 81);
		previstos.put(5, 243);
		previstos.put(6, 729);
		previstos.put(7, 2187);
		previstos.put(8, 6561);
		previstos.put(9, 19683);
		previstos.put(10, 59049);

		return previstos;
	}

	private void acharNiveis(Usuario usuario, TreeMap<Integer, Integer> niveis) {

		if (usuario.getNivel() != null && usuario.getNivel() <= 10) {

			if (niveis.get(usuario.getNivel()) == null) {
				niveis.put(usuario.getNivel(), 1);
			} else {
				niveis.put(usuario.getNivel(), niveis.get(usuario.getNivel()) + 1);
			}
		}

		if (usuario.getUsuariosPatrocinados() != null) {
			for (Usuario usuarioPatrocinado : usuario.getUsuariosPatrocinados()) {
				acharNiveis(usuarioPatrocinado, niveis);
			}
		}
	}

	private void acharPatrocinadosRecursivamente(Usuario usuario, Integer nivel) {

		Usuario usuarioPatrocinadoFiltro = new Usuario();
		usuarioPatrocinadoFiltro.setId_Patroc(usuario.getId_Codigo());

		List<Criterion> restricoes = new ArrayList<Criterion>();
		restricoes.add(Restrictions.ne("id_Codigo", usuario.getId_Codigo()));

		List<Usuario> usuariosPatrocinados = this.hibernateUtil.buscar(usuarioPatrocinadoFiltro, restricoes, Order.asc("cadPosRede"));

		for (Usuario usuarioPatrocinado : usuariosPatrocinados) {

			usuarioPatrocinado.setNivel(nivel);
			usuarioPatrocinado.calcularPontuacao();
			acharPatrocinadosRecursivamente(usuarioPatrocinado, nivel + 1);
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
