package escritoriovirtualalabastrum.auxiliar;

import escritoriovirtualalabastrum.hibernate.HibernateUtil;
import escritoriovirtualalabastrum.modelo.Usuario;

public class AnaliseAuxiliar {

	private Integer codigoUsuario;
	private Long contagemAcessos;

	public Usuario getUsuario() {

		HibernateUtil hibernateUtil = new HibernateUtil();

		Usuario usuario = new Usuario(this.codigoUsuario);

		usuario = hibernateUtil.selecionar(usuario);

		hibernateUtil.fecharSessao();

		return usuario;
	}

	public Integer getCodigoUsuario() {
		return codigoUsuario;
	}

	public void setCodigoUsuario(Integer codigoUsuario) {
		this.codigoUsuario = codigoUsuario;
	}

	public Long getContagemAcessos() {
		return contagemAcessos;
	}

	public void setContagemAcessos(Long contagemAcessos) {
		this.contagemAcessos = contagemAcessos;
	}

}