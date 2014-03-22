package escritoriovirtualalabastrum.service;

import java.math.BigDecimal;

public class GraduacaoService {

	public static final String BRONZE = "BRONZE";
	public static final BigDecimal BRONZE_PONTUACAO = new BigDecimal("2000");

	public static final String PRATA = "PRATA";
	public static final BigDecimal PRATA_PONTUACAO = new BigDecimal("4000");
	public static final Integer PRATA_GRADUADOS = 1;

	public static final String OURO = "OURO";
	public static final BigDecimal OURO_PONTUACAO = new BigDecimal("6000");
	public static final Integer OURO_GRADUADOS = 2;

	public static final String ESMERALDA = "ESMERALDA";
	public static final BigDecimal ESMERALDA_PONTUACAO = new BigDecimal("20000");
	public static final Integer ESMERALDA_GRADUADOS = 3;

	public static final String TOPAZIO = "TOPAZIO";
	public static final BigDecimal TOPAZIO_PONTUACAO = new BigDecimal("40000");
	public static final Integer TOPAZIO_GRADUADOS = 4;

	public static final String DIAMANTE = "DIAMANTE";
	public static final BigDecimal DIAMANTE_PONTUACAO = new BigDecimal("60000");
	public static final Integer DIAMANTE_GRADUADOS = 5;

	public String verificaGraduacao(BigDecimal pontuacao, Integer quantidadeGraduados) {

		String graduacao = null;

		if (pontuacao.compareTo(BRONZE_PONTUACAO) >= 0) {

			graduacao = BRONZE;
		}

		if (pontuacao.compareTo(PRATA_PONTUACAO) >= 0 && quantidadeGraduados >= PRATA_GRADUADOS) {

			graduacao = PRATA;
		}

		if (pontuacao.compareTo(OURO_PONTUACAO) >= 0 && quantidadeGraduados >= OURO_GRADUADOS) {

			graduacao = OURO;
		}

		if (pontuacao.compareTo(ESMERALDA_PONTUACAO) >= 0 && quantidadeGraduados >= ESMERALDA_GRADUADOS) {

			graduacao = ESMERALDA;
		}

		if (pontuacao.compareTo(TOPAZIO_PONTUACAO) >= 0 && quantidadeGraduados >= TOPAZIO_GRADUADOS) {

			graduacao = TOPAZIO;
		}

		if (pontuacao.compareTo(DIAMANTE_PONTUACAO) >= 0 && quantidadeGraduados >= DIAMANTE_GRADUADOS) {

			graduacao = DIAMANTE;
		}

		return graduacao;
	}
}