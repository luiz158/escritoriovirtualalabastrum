package escritoriovirtualalabastrum.controller;

import java.util.TreeMap;

import org.hibernate.criterion.MatchMode;

import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.view.Results;
import escritoriovirtualalabastrum.anotacoes.Funcionalidade;
import escritoriovirtualalabastrum.auxiliar.MalaDireta;
import escritoriovirtualalabastrum.auxiliar.Relacionamentos;
import escritoriovirtualalabastrum.hibernate.HibernateUtil;
import escritoriovirtualalabastrum.modelo.Usuario;
import escritoriovirtualalabastrum.service.MalaDiretaService;
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

		result.include("posicoes", new MalaDiretaService().obterPosicoes());
	}

	@Funcionalidade
	public void gerarMalaDireta(String posicao, Integer codigoUsuario) {

		Integer codigoUsuarioLogado = this.sessaoUsuario.getUsuario().getId_Codigo();

		MalaDiretaService malaDiretaService = new MalaDiretaService();
		malaDiretaService.setHibernateUtil(hibernateUtil);
		malaDiretaService.setValidator(validator);

		TreeMap<Integer, MalaDireta> malaDireta = malaDiretaService.gerarMalaDireta(posicao, codigoUsuario, codigoUsuarioLogado);

		Usuario usuarioPesquisado = new Usuario();
		usuarioPesquisado.setId_Codigo(codigoUsuario);

		usuarioPesquisado = this.hibernateUtil.selecionar(usuarioPesquisado, MatchMode.EXACT);

		result.include("usuarioPesquisado", usuarioPesquisado);
		result.include("posicaoConsiderada", malaDiretaService.obterPosicoes().get(posicao));
		result.include("malaDireta", malaDireta.values());
		result.include("quantidadeElementosMalaDireta", malaDireta.values().size());
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
		Usuario m6 = hibernateUtil.selecionar(new Usuario(usuario.getId_M6()));
		Usuario m7 = hibernateUtil.selecionar(new Usuario(usuario.getId_M7()));
		Usuario m8 = hibernateUtil.selecionar(new Usuario(usuario.getId_M8()));
		Usuario m9 = hibernateUtil.selecionar(new Usuario(usuario.getId_M9()));
		Usuario m10 = hibernateUtil.selecionar(new Usuario(usuario.getId_M10()));
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
		
		if (Util.preenchido(m6)) {

			relacionamentos.setCodigoM6(m6.getId_Codigo());
			relacionamentos.setNomeM6(m6.getvNome());
		}
		
		if (Util.preenchido(m7)) {

			relacionamentos.setCodigoM7(m7.getId_Codigo());
			relacionamentos.setNomeM7(m7.getvNome());
		}
		
		if (Util.preenchido(m8)) {

			relacionamentos.setCodigoM8(m8.getId_Codigo());
			relacionamentos.setNomeM8(m8.getvNome());
		}
		
		if (Util.preenchido(m9)) {

			relacionamentos.setCodigoM9(m9.getId_Codigo());
			relacionamentos.setNomeM9(m9.getvNome());
		}
		
		if (Util.preenchido(m10)) {

			relacionamentos.setCodigoM10(m10.getId_Codigo());
			relacionamentos.setNomeM10(m10.getvNome());
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
