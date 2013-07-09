package escritoriovirtualalabastrum.controller;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import escritoriovirtualalabastrum.anotacoes.Public;
import escritoriovirtualalabastrum.hibernate.HibernateUtil;
import escritoriovirtualalabastrum.listener.CounterListener;

@Resource
public class AnaliseController {

	private final Result result;

	public AnaliseController(Result result) {

		this.result = result;
	}

	@Public
	@Path("/analise")
	public void analisar() {

		result.include("ultimaAtualizacaoSistema", "08/07/2013 21:00");

		result.include("sessoesTomcat", CounterListener.getCount());

		HibernateUtil.gerarEstatisticas();

		result.include("sessoesAbertasHibernate", HibernateUtil.getQuantidadeSessoesAbertasHibernate());
		result.include("sessoesFechadasHibernate", HibernateUtil.getQuantidadeSessoesFechadasHibernate());
	}

}
