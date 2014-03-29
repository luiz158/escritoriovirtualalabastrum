package escritoriovirtualalabastrum.controller;

import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import escritoriovirtualalabastrum.anotacoes.Funcionalidade;
import escritoriovirtualalabastrum.hibernate.HibernateUtil;
import escritoriovirtualalabastrum.modelo.BonificacaoRede;
import escritoriovirtualalabastrum.modelo.InformacoesGeraisCalculoBonificacaoRede;

@Resource
public class BonificacaoRedeController {

	private Result result;
	private HibernateUtil hibernateUtil;

	public BonificacaoRedeController(Result result, HibernateUtil hibernateUtil) {

		this.result = result;
		this.hibernateUtil = hibernateUtil;
	}

	@Funcionalidade(administrativa = "true")
	public void acessarBonificacaoRede() {

		result.include("InformacoesGeraisCalculoBonificacaoRede", this.hibernateUtil.selecionar(new InformacoesGeraisCalculoBonificacaoRede()));
		result.include("bonificacoesRede", this.hibernateUtil.buscar(new BonificacaoRede()));
		result.include("somatorioBonificacoes", this.hibernateUtil.somar(new BonificacaoRede(), null, null, "total"));
	}
}
