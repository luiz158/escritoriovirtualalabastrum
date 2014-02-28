import java.util.List;

import escritoriovirtualalabastrum.hibernate.HibernateUtil;
import escritoriovirtualalabastrum.util.Util;

public class MainTest {

	public static void main(String[] args) {

		while (true) {

			Integer codigo = 77495;
			Integer ano = 2014;
			Integer mes = 2;

			HibernateUtil hibernateUtil = new HibernateUtil();
			@SuppressWarnings("unchecked")
			List<Object> kit = hibernateUtil.buscaPorHQL("select hk.kit from HistoricoKit hk where id_Codigo = " + codigo + " and data_referencia between '2014-01-01' and '" + ano + "-" + mes + "-01'");

			if (Util.preenchido(kit)) {

				System.out.println(kit.get(0));
			}

			else {

				System.out.println("vida loka");
			}

			hibernateUtil.fecharSessao();
		}
	}
}