package escritoriovirtualalabastrum.controller;

import java.util.HashMap;

import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import escritoriovirtualalabastrum.anotacoes.Funcionalidade;
import escritoriovirtualalabastrum.hibernate.HibernateUtil;
import escritoriovirtualalabastrum.modelo.Usuario;
import escritoriovirtualalabastrum.sessao.SessaoUsuario;
import escritoriovirtualalabastrum.util.Util;

@Resource
public class LinhaAscendenteController {

	private Result result;
	private HibernateUtil hibernateUtil;
	private SessaoUsuario sessaoUsuario;

	public LinhaAscendenteController(Result result, HibernateUtil hibernateUtil, SessaoUsuario sessaoUsuario) {

		this.result = result;
		this.hibernateUtil = hibernateUtil;
		this.sessaoUsuario = sessaoUsuario;
	}

	@Funcionalidade
	public void exibirLinhaAscendente() {

		Usuario usuario = this.sessaoUsuario.getUsuario();

		Usuario patrocinador = hibernateUtil.selecionar(new Usuario(usuario.getId_Patroc()));
		Usuario gerente = hibernateUtil.selecionar(new Usuario(usuario.getId_M()));
		Usuario gb = hibernateUtil.selecionar(new Usuario(usuario.getId_GB()));
		Usuario gp = hibernateUtil.selecionar(new Usuario(usuario.getId_GP()));
		Usuario go = hibernateUtil.selecionar(new Usuario(usuario.getId_GO()));
		Usuario ge = hibernateUtil.selecionar(new Usuario(usuario.getId_GE()));
		Usuario topazio = hibernateUtil.selecionar(new Usuario(usuario.getId_LA()));
		Usuario diamante = hibernateUtil.selecionar(new Usuario(usuario.getId_CR()));

		HashMap<Integer, Usuario> linhaAscendente = new HashMap<Integer, Usuario>();

		if (Util.preenchido(patrocinador) && !patrocinador.getId_Codigo().equals(usuario.getId_Codigo())) {

			linhaAscendente.put(patrocinador.getId_Codigo(), patrocinador);
		}

		if (Util.preenchido(gerente) && !gerente.getId_Codigo().equals(usuario.getId_Codigo())) {

			linhaAscendente.put(gerente.getId_Codigo(), gerente);
		}

		if (Util.preenchido(gb) && !gb.getId_Codigo().equals(usuario.getId_Codigo())) {

			linhaAscendente.put(gb.getId_Codigo(), gb);
		}

		if (Util.preenchido(gp) && !gp.getId_Codigo().equals(usuario.getId_Codigo())) {

			linhaAscendente.put(gp.getId_Codigo(), gp);
		}

		if (Util.preenchido(go) && !go.getId_Codigo().equals(usuario.getId_Codigo())) {

			linhaAscendente.put(go.getId_Codigo(), go);
		}

		if (Util.preenchido(ge) && !ge.getId_Codigo().equals(usuario.getId_Codigo())) {

			linhaAscendente.put(ge.getId_Codigo(), ge);
		}

		if (Util.preenchido(topazio) && !topazio.getId_Codigo().equals(usuario.getId_Codigo())) {

			linhaAscendente.put(topazio.getId_Codigo(), topazio);
		}

		if (Util.preenchido(diamante) && !diamante.getId_Codigo().equals(usuario.getId_Codigo())) {

			linhaAscendente.put(diamante.getId_Codigo(), diamante);
		}

		result.include("linhaAscendente", linhaAscendente);
	}
}
