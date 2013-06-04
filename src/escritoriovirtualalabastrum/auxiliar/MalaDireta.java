package escritoriovirtualalabastrum.auxiliar;

import escritoriovirtualalabastrum.modelo.Usuario;

public class MalaDireta {

	public MalaDireta(Usuario usuario, int nivel) {

		this.usuario = usuario;
		this.nivel = nivel;
	}

	private Usuario usuario;
	private int nivel;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

}