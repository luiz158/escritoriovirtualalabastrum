package escritoriovirtualalabastrum.controller;

import java.util.GregorianCalendar;

import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.ValidationMessage;
import escritoriovirtualalabastrum.anotacoes.Funcionalidade;
import escritoriovirtualalabastrum.auxiliar.BonificacaoAuxiliar;
import escritoriovirtualalabastrum.hibernate.HibernateUtil;
import escritoriovirtualalabastrum.service.BonificacaoIngressoService;
import escritoriovirtualalabastrum.sessao.SessaoUsuario;

@Resource
public class ExtratoSimplificadoController {

	private Result result;
	private HibernateUtil hibernateUtil;
	private SessaoUsuario sessaoUsuario;
	private Validator validator;
	private boolean realizarValidacoes = true;

	public ExtratoSimplificadoController(Result result, HibernateUtil hibernateUtil, SessaoUsuario sessaoUsuario, Validator validator) {

		this.result = result;
		this.hibernateUtil = hibernateUtil;
		this.sessaoUsuario = sessaoUsuario;
		this.validator = validator;
	}

	@Funcionalidade
	public void acessarTelaExtratoSimplificado(Integer ano, Integer mes) {

	}

	@Funcionalidade
	public void gerarExtratoSimplificado(Integer ano, Integer mes) {

		realizarValidacoes(ano, mes);

		BonificacaoIngressoService bonificacaoIngressoService = new BonificacaoIngressoService(hibernateUtil, validator, result);
		BonificacaoAuxiliar informacoesBonificacoesIngresso = bonificacaoIngressoService.calcularBonificacoes(this.sessaoUsuario.getUsuario(), ano, mes);
		result.include("bonificacaoIngresso", bonificacaoIngressoService.calcularSomatorioBonificacoes(informacoesBonificacoesIngresso.getBonificacoes()).add(bonificacaoIngressoService.calcularSomatorioBonificacoes(informacoesBonificacoesIngresso.getBonificacoesDiamante())));

		result.include("mes", mes);
		result.include("ano", ano);
	}

	private void realizarValidacoes(Integer ano, Integer mes) {

		if (realizarValidacoes) {

			if (!this.sessaoUsuario.getUsuario().isAtivo()) {

				validator.add(new ValidationMessage("Você não está ativo. Só quem está ativo pode receber bonificação", "Erro"));
				validator.onErrorRedirectTo(this).acessarTelaExtratoSimplificado(ano, mes);
			}

			if (new GregorianCalendar(ano, mes - 1, 1).before(new GregorianCalendar(2014, 2, 1))) {

				validator.add(new ValidationMessage("Este relatório só passou a existir no escritório virtual a partir de março de 2014", "Erro"));
				validator.onErrorRedirectTo(this).acessarTelaExtratoSimplificado(ano, mes);
			}
		}
	}

	public void setRealizarValidacoes(boolean realizarValidacoes) {
		this.realizarValidacoes = realizarValidacoes;
	}
}
