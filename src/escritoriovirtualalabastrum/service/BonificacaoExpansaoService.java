package escritoriovirtualalabastrum.service;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.hibernate.criterion.MatchMode;
import org.joda.time.DateTime;

import escritoriovirtualalabastrum.auxiliar.MalaDireta;
import escritoriovirtualalabastrum.hibernate.HibernateUtil;
import escritoriovirtualalabastrum.modelo.FixoIngresso;
import escritoriovirtualalabastrum.modelo.Usuario;
import escritoriovirtualalabastrum.util.Util;

public class BonificacaoExpansaoService {

	private HibernateUtil hibernateUtil;

	public BonificacaoExpansaoService(HibernateUtil hibernateUtil) {

		this.hibernateUtil = hibernateUtil;
	}

	public BigDecimal calcularBonificacoes(Usuario usuario, Integer ano, Integer mes) {

		BigDecimal bonificacao = BigDecimal.ZERO;

		DateTime dataInicial = new DateTime(ano, mes, 1, 0, 0, 0);
		DateTime dataFinal = new DateTime(ano, mes, dataInicial.dayOfMonth().withMaximumValue().dayOfMonth().get(), 0, 0, 0);

		TreeMap<Integer, MalaDireta> malaDiretaCompleta = new TreeMap<Integer, MalaDireta>();

		MalaDiretaService malaDiretaService = new MalaDiretaService(hibernateUtil);
		malaDiretaService.setHibernateUtil(hibernateUtil);
		malaDiretaService.gerarMalaDiretaDeAcordoComAtividade("id_Patroc", usuario.getId_Codigo(), dataInicial.toGregorianCalendar(), dataFinal.toGregorianCalendar(), 1, malaDiretaCompleta);

		for (Entry<Integer, MalaDireta> malaDiretaEntry : malaDiretaCompleta.entrySet()) {

			MalaDireta malaDireta = malaDiretaEntry.getValue();

			Usuario usuarioMalaDireta = malaDireta.getUsuario();

			if ((usuarioMalaDireta.getDt_Ingresso().equals(dataInicial.toGregorianCalendar()) || usuarioMalaDireta.getDt_Ingresso().after(dataInicial.toGregorianCalendar())) && (usuarioMalaDireta.getDt_Ingresso().equals(dataFinal.toGregorianCalendar()) || usuarioMalaDireta.getDt_Ingresso().before(dataFinal.toGregorianCalendar()))) {

				bonificacao = bonificacao.add(calcularBonificacoesFixas(ano, mes, malaDireta));
			}
		}

		return bonificacao;
	}

	private BigDecimal calcularBonificacoesFixas(Integer ano, Integer mes, MalaDireta malaDireta) {

		String kit = encontrarHistoricoKitDeAcordoComUsuarioEDataInformada(malaDireta.getUsuario(), ano, mes);

		if (kit == null) {

			return BigDecimal.ZERO;
		}

		FixoIngresso fixoIngresso = new FixoIngresso();
		fixoIngresso.setData_referencia(new GregorianCalendar(ano, mes - 1, 1));
		fixoIngresso.setGeracao(String.valueOf(malaDireta.getNivel()));

		fixoIngresso = this.hibernateUtil.selecionar(fixoIngresso, MatchMode.EXACT);

		try {

			Field field = fixoIngresso.getClass().getDeclaredField(kit);
			field.setAccessible(true);
			return (BigDecimal) field.get(fixoIngresso);

		} catch (Exception e) {

			return BigDecimal.ZERO;
		}
	}

	private String encontrarHistoricoKitDeAcordoComUsuarioEDataInformada(Usuario usuario, Integer ano, Integer mes) {

		@SuppressWarnings("unchecked")
		List<Object> kit = hibernateUtil.buscaPorHQL("select hk.kit from HistoricoKit hk where id_Codigo = " + usuario.getId_Codigo() + " and data_referencia between '2014-01-01' and '" + ano + "-" + mes + "-01' order by id desc limit 1");

		if (Util.preenchido(kit)) {

			return (String) kit.get(0);

		} else {

			return null;
		}
	}
}