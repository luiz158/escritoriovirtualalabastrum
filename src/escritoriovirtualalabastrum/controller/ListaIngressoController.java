package escritoriovirtualalabastrum.controller;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import escritoriovirtualalabastrum.anotacoes.Funcionalidade;
import escritoriovirtualalabastrum.auxiliar.MalaDireta;
import escritoriovirtualalabastrum.hibernate.HibernateUtil;
import escritoriovirtualalabastrum.modelo.Usuario;
import escritoriovirtualalabastrum.sessao.SessaoUsuario;

@Resource
public class ListaIngressoController {

	private Result result;
	private HibernateUtil hibernateUtil;
	private SessaoUsuario sessaoUsuario;

	public ListaIngressoController(Result result, HibernateUtil hibernateUtil, SessaoUsuario sessaoUsuario) {

		this.result = result;
		this.hibernateUtil = hibernateUtil;
		this.sessaoUsuario = sessaoUsuario;
	}

	@Funcionalidade
	public void acessarTelaListaIngresso() {

	}

	@Funcionalidade
	public void gerarListaIngresso(GregorianCalendar dataInicial, GregorianCalendar dataFinal) {

		Integer codigoUsuarioLogado = this.sessaoUsuario.getUsuario().getId_Codigo();

		TreeMap<Integer, MalaDireta> malaDireta = new TreeMap<Integer, MalaDireta>();

		gerarMalaDiretaTodasPosicoesFiltrandoPorDataDeIngresso(codigoUsuarioLogado, malaDireta, dataInicial, dataFinal);

		result.include("malaDireta", malaDireta);

		result.include("dataInicialPesquisada", dataInicial);
		result.include("dataFinalPesquisada", dataFinal);
	}

	private void gerarMalaDiretaTodasPosicoesFiltrandoPorDataDeIngresso(Integer codigoUsuario, TreeMap<Integer, MalaDireta> malaDireta, GregorianCalendar dataInicial, GregorianCalendar dataFinal) {

		LinkedHashMap<String, String> posicoes = obterPosicoes();

		for (Entry<String, String> posicao : posicoes.entrySet()) {

			if (!posicao.getKey().equals("Todas")) {

				this.pesquisarMalaDiretaComRecursividadeFiltrandoPorDataDeIngresso(codigoUsuario, malaDireta, 1, posicao.getKey(), dataInicial, dataFinal);
			}
		}
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

	private LinkedHashMap<String, String> obterPosicoes() {

		LinkedHashMap<String, String> posicoes = new LinkedHashMap<String, String>();

		posicoes.put("Todas", "Todas");
		posicoes.put("id_Patroc", "Patrocinador");
		posicoes.put("id_Dem", "Demonstrador");
		posicoes.put("id_S", "Sênior");
		posicoes.put("id_M", "Gerente");
		posicoes.put("id_M1", "M1");
		posicoes.put("id_M2", "M2");
		posicoes.put("id_M3", "M3");
		posicoes.put("id_M4", "M4");
		posicoes.put("id_M5", "M5");
		posicoes.put("id_GB", "Gerente Bronze");
		posicoes.put("id_GP", "Gerente Prata");
		posicoes.put("id_GO", "Gerente Ouro");
		posicoes.put("id_GE", "Esmeralda");
		posicoes.put("id_LA", "Topázio");
		posicoes.put("id_CR", "Diamante");
		posicoes.put("id_DR", "Diamante Duplo");
		posicoes.put("id_DD", "Diamante Triplo");
		posicoes.put("id_DS", "Diamante Plenus");
		posicoes.put("id_Pres", "Presidente");

		return posicoes;
	}

}
