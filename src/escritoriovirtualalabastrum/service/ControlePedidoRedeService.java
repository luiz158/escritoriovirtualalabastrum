package escritoriovirtualalabastrum.service;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import escritoriovirtualalabastrum.auxiliar.MalaDireta;
import escritoriovirtualalabastrum.hibernate.HibernateUtil;
import escritoriovirtualalabastrum.modelo.ControlePedido;
import escritoriovirtualalabastrum.modelo.Usuario;
import escritoriovirtualalabastrum.util.Util;

public class ControlePedidoRedeService {

	private HibernateUtil hibernateUtil;

	public ControlePedidoRedeService(HibernateUtil hibernateUtil) {

		this.hibernateUtil = hibernateUtil;
	}

	public List<ControlePedido> buscarPedidosDaRede(Integer codigoUsuario, TreeMap<Integer, MalaDireta> malaDireta, GregorianCalendar dataInicial, GregorianCalendar dataFinal, Integer quantidadeGeracoesQueDistribuiraoBonus) {

		List<Integer> idsRede = new ArrayList<Integer>();

		for (Entry<Integer, MalaDireta> malaDiretaEntry : malaDireta.entrySet()) {

			if (!malaDiretaEntry.getValue().getUsuario().getId_Codigo().equals(codigoUsuario)) {

				boolean adiciona = true;

				if (quantidadeGeracoesQueDistribuiraoBonus != null && malaDiretaEntry.getValue().getNivel() > quantidadeGeracoesQueDistribuiraoBonus) {
					adiciona = false;
				}

				if (adiciona) {
					idsRede.add(malaDiretaEntry.getValue().getUsuario().getId_Codigo());
				}
			}
		}

		if (Util.preenchido(idsRede)) {

			List<Criterion> restricoes = new ArrayList<Criterion>();
			restricoes.add(Restrictions.between("pedData", dataInicial, dataFinal));
			restricoes.add(Restrictions.in("id_Codigo", idsRede));

			List<ControlePedido> pedidos = this.hibernateUtil.buscar(new ControlePedido(), restricoes);

			for (ControlePedido pedido : pedidos) {

				Usuario usuarioDoPedido = this.hibernateUtil.selecionar(new Usuario(pedido.getId_Codigo()));

				pedido.setUsuario(usuarioDoPedido);
			}

			return pedidos;
		}

		return new ArrayList<ControlePedido>();
	}
}