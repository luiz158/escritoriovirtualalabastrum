package escritoriovirtualalabastrum.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import escritoriovirtualalabastrum.anotacoes.Funcionalidade;
import escritoriovirtualalabastrum.hibernate.HibernateUtil;
import escritoriovirtualalabastrum.modelo.BonificacaoRede;
import escritoriovirtualalabastrum.modelo.Configuracao;
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

		if (Boolean.valueOf(new Configuracao().retornarConfiguracao("exibicaoRedeFakeRelatorioBonificacao"))) {

			result.include("bonificacoesRede", this.hibernateUtil.buscar(new BonificacaoRede()));
			result.include("somatorioBonificacoes", this.hibernateUtil.somar(new BonificacaoRede(), null, null, "total"));

		} else {

			List<BonificacaoRede> bonificacoes = this.hibernateUtil.buscar(new BonificacaoRede());

			List<BonificacaoRede> bonificacoesIgnorandoFakes = new ArrayList<BonificacaoRede>();
			BigDecimal somatorioBonificacoes = BigDecimal.ZERO;

			for (BonificacaoRede bonificacaoRede : bonificacoes) {

				if (bonificacaoRede.getUsuario().getFake().equals("0")) {

					bonificacoesIgnorandoFakes.add(bonificacaoRede);
					somatorioBonificacoes = somatorioBonificacoes.add(bonificacaoRede.getTotal());
				}
			}

			result.include("bonificacoesRede", bonificacoesIgnorandoFakes);
			result.include("somatorioBonificacoes", somatorioBonificacoes);
		}

		result.include("InformacoesGeraisCalculoBonificacaoRede", this.hibernateUtil.selecionar(new InformacoesGeraisCalculoBonificacaoRede()));

	}
}
