package escritoriovirtualalabastrum.controller;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.hibernate.criterion.MatchMode;

import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.ValidationMessage;
import br.com.caelum.vraptor.view.Results;
import escritoriovirtualalabastrum.anotacoes.Funcionalidade;
import escritoriovirtualalabastrum.auxiliar.MalaDireta;
import escritoriovirtualalabastrum.auxiliar.Relacionamentos;
import escritoriovirtualalabastrum.hibernate.HibernateUtil;
import escritoriovirtualalabastrum.modelo.Usuario;
import escritoriovirtualalabastrum.sessao.SessaoUsuario;
import escritoriovirtualalabastrum.util.Util;

@Resource
public class MalaDiretaController {

	private Result result;
	private HibernateUtil hibernateUtil;
	private SessaoUsuario sessaoUsuario;
	private Validator validator;

	public MalaDiretaController(Result result, HibernateUtil hibernateUtil, SessaoUsuario sessaoUsuario, Validator validator) {

		this.result = result;
		this.hibernateUtil = hibernateUtil;
		this.sessaoUsuario = sessaoUsuario;
		this.validator = validator;
	}

	@Funcionalidade
	public void acessarTelaMalaDireta() {

		result.include("posicoes", obterPosicoes());
	}

	@Funcionalidade
	public void gerarMalaDireta(String posicao, Integer codigoUsuario) {

		Integer codigoUsuarioLogado = this.sessaoUsuario.getUsuario().getId_Codigo();

		if (Util.vazio(codigoUsuario)) {

			codigoUsuario = codigoUsuarioLogado;
		}

		if (codigoUsuario.equals(codigoUsuarioLogado)) {

			gerarMalaDiretaDeAcordoComFiltros(posicao, codigoUsuario);
		}

		else {

			TreeMap<Integer, MalaDireta> malaDiretaUsuarioLogado = new TreeMap<Integer, MalaDireta>();

			gerarMalaDiretaTodasPosicoes(codigoUsuarioLogado, malaDiretaUsuarioLogado);

			if (!malaDiretaUsuarioLogado.containsKey(codigoUsuario)) {

				validator.add(new ValidationMessage("O código informado não está na sua mala direta", "Erro"));
				validator.onErrorRedirectTo(this).acessarTelaMalaDireta();
			}

			else {

				gerarMalaDiretaDeAcordoComFiltros(posicao, codigoUsuario);
			}
		}

		Usuario usuarioPesquisado = new Usuario();
		usuarioPesquisado.setId_Codigo(codigoUsuario);

		usuarioPesquisado = this.hibernateUtil.selecionar(usuarioPesquisado, MatchMode.EXACT);

		result.include("usuarioPesquisado", usuarioPesquisado);
		result.include("posicaoConsiderada", obterPosicoes().get(posicao));
	}

	private void gerarMalaDiretaDeAcordoComFiltros(String posicao, Integer codigoUsuario) {

		TreeMap<Integer, MalaDireta> malaDireta = new TreeMap<Integer, MalaDireta>();

		if (posicao.equals("Todas")) {

			gerarMalaDiretaTodasPosicoes(codigoUsuario, malaDireta);
		}

		else {

			this.pesquisarMalaDiretaSemRecursividade(codigoUsuario, malaDireta, 1, posicao);
		}

		result.include("malaDireta", malaDireta.values());
		result.include("quantidadeElementosMalaDireta", malaDireta.values().size());
	}

	private void gerarMalaDiretaTodasPosicoes(Integer codigoUsuario, TreeMap<Integer, MalaDireta> malaDireta) {

		LinkedHashMap<String, String> posicoes = obterPosicoes();

		for (Entry<String, String> posicao : posicoes.entrySet()) {

			if (!posicao.getKey().equals("Todas")) {

				this.pesquisarMalaDiretaComRecursividade(codigoUsuario, malaDireta, 1, posicao.getKey());
			}
		}
	}

	private void pesquisarMalaDiretaComRecursividade(Integer codigo, TreeMap<Integer, MalaDireta> malaDireta, int nivel, String posicao) {

		Usuario usuario = new Usuario();

		try {

			Field field = usuario.getClass().getDeclaredField(posicao);

			field.setAccessible(true);

			field.set(usuario, codigo);
		}

		catch (Exception e) {

			e.printStackTrace();
		}

		List<Usuario> usuariosPatrocinados = hibernateUtil.buscar(usuario);

		for (Usuario usuarioPatrocinado : usuariosPatrocinados) {

			if (!codigo.equals(usuarioPatrocinado.getId_Codigo())) {

				if (!malaDireta.containsKey(usuarioPatrocinado.getId_Codigo())) {

					malaDireta.put(usuarioPatrocinado.getId_Codigo(), new MalaDireta(usuarioPatrocinado, nivel));

					pesquisarMalaDiretaComRecursividade(usuarioPatrocinado.getId_Codigo(), malaDireta, nivel + 1, "id_Patroc");
				}
			}
		}
	}

	private void pesquisarMalaDiretaSemRecursividade(Integer codigo, TreeMap<Integer, MalaDireta> malaDireta, int nivel, String posicao) {

		Usuario usuario = new Usuario();

		try {

			Field field = usuario.getClass().getDeclaredField(posicao);

			field.setAccessible(true);

			field.set(usuario, codigo);
		}

		catch (Exception e) {

			e.printStackTrace();
		}

		List<Usuario> usuariosPatrocinados = hibernateUtil.buscar(usuario);

		for (Usuario usuarioPatrocinado : usuariosPatrocinados) {

			if (!codigo.equals(usuarioPatrocinado.getId_Codigo())) {

				malaDireta.put(usuarioPatrocinado.getId_Codigo(), new MalaDireta(usuarioPatrocinado, nivel));
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

	@Funcionalidade
	public void buscarRelacionamentosUsuarioSelecionado(Integer codigoUsuario) {

		Usuario usuario = new Usuario(codigoUsuario);

		usuario = this.hibernateUtil.selecionar(usuario);

		Relacionamentos relacionamentos = new Relacionamentos();

		relacionamentos.setCodigo(usuario.getId_Codigo());
		relacionamentos.setNome(usuario.getvNome());

		Usuario patroc = hibernateUtil.selecionar(new Usuario(usuario.getId_Patroc()));
		Usuario s = hibernateUtil.selecionar(new Usuario(usuario.getId_S()));
		Usuario m = hibernateUtil.selecionar(new Usuario(usuario.getId_M()));
		Usuario gb = hibernateUtil.selecionar(new Usuario(usuario.getId_GB()));
		Usuario gp = hibernateUtil.selecionar(new Usuario(usuario.getId_GP()));
		Usuario go = hibernateUtil.selecionar(new Usuario(usuario.getId_GO()));
		Usuario ge = hibernateUtil.selecionar(new Usuario(usuario.getId_GE()));
		Usuario m1 = hibernateUtil.selecionar(new Usuario(usuario.getId_M1()));
		Usuario m2 = hibernateUtil.selecionar(new Usuario(usuario.getId_M2()));
		Usuario m3 = hibernateUtil.selecionar(new Usuario(usuario.getId_M3()));
		Usuario m4 = hibernateUtil.selecionar(new Usuario(usuario.getId_M4()));
		Usuario m5 = hibernateUtil.selecionar(new Usuario(usuario.getId_M5()));
		Usuario la = hibernateUtil.selecionar(new Usuario(usuario.getId_LA()));
		Usuario la1 = hibernateUtil.selecionar(new Usuario(usuario.getId_LA1()));
		Usuario la2 = hibernateUtil.selecionar(new Usuario(usuario.getId_LA2()));
		Usuario cr = hibernateUtil.selecionar(new Usuario(usuario.getId_CR()));
		Usuario cr1 = hibernateUtil.selecionar(new Usuario(usuario.getId_CR1()));
		Usuario cr2 = hibernateUtil.selecionar(new Usuario(usuario.getId_CR2()));
		Usuario dr = hibernateUtil.selecionar(new Usuario(usuario.getId_DR()));
		Usuario dd = hibernateUtil.selecionar(new Usuario(usuario.getId_DD()));
		Usuario ds = hibernateUtil.selecionar(new Usuario(usuario.getId_DS()));

		if (Util.preenchido(patroc)) {

			relacionamentos.setCodigoPatroc(patroc.getId_Codigo());
			relacionamentos.setNomePatroc(patroc.getvNome());
		}

		if (Util.preenchido(s)) {

			relacionamentos.setCodigoS(s.getId_Codigo());
			relacionamentos.setNomeS(s.getvNome());
		}

		if (Util.preenchido(m)) {

			relacionamentos.setCodigoM(m.getId_Codigo());
			relacionamentos.setNomeM(m.getvNome());
		}

		if (Util.preenchido(gb)) {

			relacionamentos.setCodigoGb(gb.getId_Codigo());
			relacionamentos.setNomeGb(gb.getvNome());
		}

		if (Util.preenchido(gp)) {

			relacionamentos.setCodigoGp(gp.getId_Codigo());
			relacionamentos.setNomeGp(gp.getvNome());
		}

		if (Util.preenchido(go)) {

			relacionamentos.setCodigoGo(go.getId_Codigo());
			relacionamentos.setNomeGo(go.getvNome());
		}

		if (Util.preenchido(ge)) {

			relacionamentos.setCodigoGe(ge.getId_Codigo());
			relacionamentos.setNomeGe(ge.getvNome());
		}

		if (Util.preenchido(m1)) {

			relacionamentos.setCodigoM1(m1.getId_Codigo());
			relacionamentos.setNomeM1(m1.getvNome());
		}

		if (Util.preenchido(m2)) {

			relacionamentos.setCodigoM2(m2.getId_Codigo());
			relacionamentos.setNomeM2(m2.getvNome());
		}

		if (Util.preenchido(m3)) {

			relacionamentos.setCodigoM3(m3.getId_Codigo());
			relacionamentos.setNomeM3(m3.getvNome());
		}

		if (Util.preenchido(m4)) {

			relacionamentos.setCodigoM4(m4.getId_Codigo());
			relacionamentos.setNomeM4(m4.getvNome());
		}

		if (Util.preenchido(m5)) {

			relacionamentos.setCodigoM5(m5.getId_Codigo());
			relacionamentos.setNomeM5(m5.getvNome());
		}

		if (Util.preenchido(la)) {

			relacionamentos.setCodigoLa(la.getId_Codigo());
			relacionamentos.setNomeLa(la.getvNome());
		}

		if (Util.preenchido(la1)) {

			relacionamentos.setCodigoLa1(la1.getId_Codigo());
			relacionamentos.setNomeLa1(la1.getvNome());
		}

		if (Util.preenchido(la2)) {

			relacionamentos.setCodigoLa2(la2.getId_Codigo());
			relacionamentos.setNomeLa2(la2.getvNome());
		}

		if (Util.preenchido(cr)) {

			relacionamentos.setCodigoCr(cr.getId_Codigo());
			relacionamentos.setNomeCr(cr.getvNome());
		}

		if (Util.preenchido(cr1)) {

			relacionamentos.setCodigoCr1(cr1.getId_Codigo());
			relacionamentos.setNomeCr1(cr1.getvNome());
		}

		if (Util.preenchido(cr2)) {

			relacionamentos.setCodigoCr2(cr2.getId_Codigo());
			relacionamentos.setNomeCr2(cr2.getvNome());
		}

		if (Util.preenchido(dr)) {

			relacionamentos.setCodigoDr(dr.getId_Codigo());
			relacionamentos.setNomeDr(dr.getvNome());
		}

		if (Util.preenchido(dd)) {

			relacionamentos.setCodigoDd(dd.getId_Codigo());
			relacionamentos.setNomeDd(dd.getvNome());
		}

		if (Util.preenchido(ds)) {

			relacionamentos.setCodigoDs(ds.getId_Codigo());
			relacionamentos.setNomeDs(ds.getvNome());
		}

		result.use(Results.json()).from(relacionamentos).serialize();
	}

}
