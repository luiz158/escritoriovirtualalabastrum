package escritoriovirtualalabastrum.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Days;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import escritoriovirtualalabastrum.anotacoes.Public;
import escritoriovirtualalabastrum.auxiliar.AnaliseAuxiliar;
import escritoriovirtualalabastrum.hibernate.HibernateUtil;
import escritoriovirtualalabastrum.listener.CounterListener;
import escritoriovirtualalabastrum.modelo.HistoricoAcesso;

@Resource
public class AnaliseController {

	private final Result result;

	public AnaliseController(Result result) {

		this.result = result;
	}

	@SuppressWarnings("rawtypes")
	@Public
	@Path("/analise")
	public void analisar() {

		result.include("ultimaAtualizacaoSistema", "21/02/2014 19:50");

		result.include("sessoesTomcat", CounterListener.getCount());

		HibernateUtil.gerarEstatisticas();

		result.include("sessoesAbertasHibernate", HibernateUtil.getQuantidadeSessoesAbertasHibernate());
		result.include("sessoesFechadasHibernate", HibernateUtil.getQuantidadeSessoesFechadasHibernate());

		HibernateUtil hibernateUtil = new HibernateUtil();

		List acessosUsuariosOrdenadosPorMaisAtivos = hibernateUtil.buscaPorHQL("SELECT codigoUsuario, count(codigoUsuario) FROM HistoricoAcesso group by codigoUsuario order by count(codigoUsuario) desc");
		List<AnaliseAuxiliar> acessos = new ArrayList<AnaliseAuxiliar>();

		for (Object acesso : acessosUsuariosOrdenadosPorMaisAtivos) {

			AnaliseAuxiliar analiseAuxiliar = new AnaliseAuxiliar();

			Object[] acessoArray = (Object[]) acesso;

			analiseAuxiliar.setCodigoUsuario((Integer) acessoArray[0]);
			analiseAuxiliar.setContagemAcessos((Long) acessoArray[1]);

			acessos.add(analiseAuxiliar);
		}
		result.include("acessosUsuariosOrdenadosPorMaisAtivos", acessos);

		Integer total = hibernateUtil.contar(new HistoricoAcesso());
		result.include("total", total);

		HistoricoAcesso historicoAcesso = new HistoricoAcesso();
		historicoAcesso.setId(1);
		historicoAcesso = hibernateUtil.selecionar(historicoAcesso);

		DateTime primeiroAcesso = new DateTime(historicoAcesso.getDataHora());
		DateTime hoje = new DateTime();

		int quantidadeDias = Days.daysBetween(primeiroAcesso, hoje).getDays();

		result.include("mediaDiaria", BigDecimal.valueOf(total).divide(BigDecimal.valueOf(quantidadeDias), 2, BigDecimal.ROUND_HALF_UP));

		hibernateUtil.fecharSessao();
	}
}
