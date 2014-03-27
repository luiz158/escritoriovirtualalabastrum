package escritoriovirtualalabastrum.sessao;

import java.util.TreeMap;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.SessionScoped;
import escritoriovirtualalabastrum.auxiliar.ExtratoSimplificadoAuxiliar;
import escritoriovirtualalabastrum.auxiliar.MalaDireta;

@Component
@SessionScoped
public class SessaoBonificacao {

	private TreeMap<Integer, MalaDireta> malaDireta;
	private ExtratoSimplificadoAuxiliar extratoSimplificadoAuxiliar;

	public TreeMap<Integer, MalaDireta> getMalaDireta() {
		return malaDireta;
	}

	public void setMalaDireta(TreeMap<Integer, MalaDireta> malaDireta) {
		this.malaDireta = malaDireta;
	}

	public ExtratoSimplificadoAuxiliar getExtratoSimplificadoAuxiliar() {
		return extratoSimplificadoAuxiliar;
	}

	public void setExtratoSimplificadoAuxiliar(ExtratoSimplificadoAuxiliar extratoSimplificadoAuxiliar) {
		this.extratoSimplificadoAuxiliar = extratoSimplificadoAuxiliar;
	}
}
