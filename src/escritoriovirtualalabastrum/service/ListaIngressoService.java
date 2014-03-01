package escritoriovirtualalabastrum.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import escritoriovirtualalabastrum.auxiliar.MalaDireta;
import escritoriovirtualalabastrum.hibernate.HibernateUtil;
import escritoriovirtualalabastrum.modelo.Usuario;
import escritoriovirtualalabastrum.util.Util;

public class ListaIngressoService {

	private HibernateUtil hibernateUtil;

	public TreeMap<Integer, MalaDireta> gerarMalaDiretaFiltrandoPorDataDeIngresso(Integer codigoUsuario, GregorianCalendar dataInicial, GregorianCalendar dataFinal, String posicaoFiltro) {

		TreeMap<Integer, MalaDireta> malaDireta = new TreeMap<Integer, MalaDireta>();

		if (Util.preenchido(posicaoFiltro)) {

			this.pesquisarMalaDiretaComRecursividadeFiltrandoPorDataDeIngresso(codigoUsuario, malaDireta, 1, posicaoFiltro, dataInicial, dataFinal);
		}

		else {

			LinkedHashMap<String, String> posicoes = new MalaDiretaService().obterPosicoes();

			for (Entry<String, String> posicao : posicoes.entrySet()) {

				if (!posicao.getKey().equals("Todas")) {

					this.pesquisarMalaDiretaComRecursividadeFiltrandoPorDataDeIngresso(codigoUsuario, malaDireta, 1, posicao.getKey(), dataInicial, dataFinal);
				}
			}
		}

		return malaDireta;
	}

	private void pesquisarMalaDiretaComRecursividadeFiltrandoPorDataDeIngresso(Integer codigo, TreeMap<Integer, MalaDireta> malaDireta, int nivel, String posicao, GregorianCalendar dataInicial, GregorianCalendar dataFinal) {

		Usuario usuario = new Usuario();

		List<Criterion> restricoes = new ArrayList<Criterion>();
		restricoes.add(Restrictions.between("Dt_Ingresso", dataInicial, dataFinal));

		try {

			Field field = usuario.getClass().getDeclaredField(posicao);

			field.setAccessible(true);

			field.set(usuario, codigo);
		}

		catch (Exception e) {

			e.printStackTrace();
		}

		List<Usuario> usuariosPatrocinados = hibernateUtil.buscar(usuario, restricoes);

		for (Usuario usuarioPatrocinado : usuariosPatrocinados) {

			if (!codigo.equals(usuarioPatrocinado.getId_Codigo())) {

				if (!malaDireta.containsKey(usuarioPatrocinado.getId_Codigo())) {

					malaDireta.put(usuarioPatrocinado.getId_Codigo(), new MalaDireta(usuarioPatrocinado, nivel));

					pesquisarMalaDiretaComRecursividadeFiltrandoPorDataDeIngresso(usuarioPatrocinado.getId_Codigo(), malaDireta, nivel + 1, "id_Patroc", dataInicial, dataFinal);
				}
			}
		}
	}

	public TreeMap<Integer, MalaDireta> pesquisarMalaDiretaSemRecursividadeFiltrandoPorDataDeIngresso(Integer codigoUsuario, GregorianCalendar dataInicial, GregorianCalendar dataFinal) {

		TreeMap<Integer, MalaDireta> malaDireta = new TreeMap<Integer, MalaDireta>();

		Usuario usuario = new Usuario();

		List<Criterion> restricoes = new ArrayList<Criterion>();
		restricoes.add(Restrictions.between("Dt_Ingresso", dataInicial, dataFinal));

		try {

			Field field = usuario.getClass().getDeclaredField("id_Patroc");

			field.setAccessible(true);

			field.set(usuario, codigoUsuario);
		}

		catch (Exception e) {

			e.printStackTrace();
		}

		List<Usuario> usuariosPatrocinados = hibernateUtil.buscar(usuario, restricoes);

		for (Usuario usuarioPatrocinado : usuariosPatrocinados) {

			if (!codigoUsuario.equals(usuarioPatrocinado.getId_Codigo())) {

				if (!malaDireta.containsKey(usuarioPatrocinado.getId_Codigo())) {

					malaDireta.put(usuarioPatrocinado.getId_Codigo(), new MalaDireta(usuarioPatrocinado, 1));
				}
			}
		}

		return malaDireta;
	}

	public HibernateUtil getHibernateUtil() {
		return hibernateUtil;
	}

	public void setHibernateUtil(HibernateUtil hibernateUtil) {
		this.hibernateUtil = hibernateUtil;
	}
}