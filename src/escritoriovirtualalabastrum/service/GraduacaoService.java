package escritoriovirtualalabastrum.service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class GraduacaoService {

	public static final BigDecimal BRONZE_PONTUACAO = new BigDecimal("2000");
	public static final BigDecimal BRONZE_PORCENTAGEM = new BigDecimal("3");

	public static final BigDecimal PRATA_PONTUACAO = new BigDecimal("4000");
	public static final BigDecimal PRATA_PORCENTAGEM = new BigDecimal("6");
	public static final Integer PRATA_GRADUADOS = 1;

	public static final BigDecimal OURO_PONTUACAO = new BigDecimal("6000");
	public static final BigDecimal OURO_PORCENTAGEM = new BigDecimal("9");
	public static final Integer OURO_GRADUADOS = 2;

	public static final BigDecimal ESMERALDA_PONTUACAO = new BigDecimal("20000");
	public static final BigDecimal ESMERALDA_PORCENTAGEM = new BigDecimal("12");
	public static final Integer ESMERALDA_GRADUADOS = 3;

	public static final BigDecimal TOPAZIO_PONTUACAO = new BigDecimal("40000");
	public static final BigDecimal TOPAZIO_PORCENTAGEM = new BigDecimal("15");
	public static final Integer TOPAZIO_GRADUADOS = 4;

	public static final BigDecimal DIAMANTE_PONTUACAO = new BigDecimal("60000");
	public static final BigDecimal DIAMANTE_PORCENTAGEM = new BigDecimal("18");
	public static final Integer DIAMANTE_GRADUADOS = 5;

	public static final List<String> GRADUACOES = Arrays.asList(MalaDiretaService.GERENTE_BRONZE.toLowerCase(), MalaDiretaService.GERENTE_PRATA.toLowerCase(), MalaDiretaService.GERENTE_OURO.toLowerCase(), MalaDiretaService.ESMERALDA.toLowerCase(), MalaDiretaService.TOPÁZIO.toLowerCase(), MalaDiretaService.DIAMANTE.toLowerCase());

	public String verificaGraduacao(BigDecimal pontuacao, Integer quantidadeGraduados) {

		String graduacao = null;

		if (pontuacao.compareTo(BRONZE_PONTUACAO) >= 0) {

			graduacao = MalaDiretaService.GERENTE_BRONZE;
		}

		if (pontuacao.compareTo(PRATA_PONTUACAO) >= 0 && quantidadeGraduados >= PRATA_GRADUADOS) {

			graduacao = MalaDiretaService.GERENTE_PRATA;
		}

		if (pontuacao.compareTo(OURO_PONTUACAO) >= 0 && quantidadeGraduados >= OURO_GRADUADOS) {

			graduacao = MalaDiretaService.GERENTE_OURO;
		}

		if (pontuacao.compareTo(ESMERALDA_PONTUACAO) >= 0 && quantidadeGraduados >= ESMERALDA_GRADUADOS) {

			graduacao = MalaDiretaService.ESMERALDA;
		}

		if (pontuacao.compareTo(TOPAZIO_PONTUACAO) >= 0 && quantidadeGraduados >= TOPAZIO_GRADUADOS) {

			graduacao = MalaDiretaService.TOPÁZIO;
		}

		if (pontuacao.compareTo(DIAMANTE_PONTUACAO) >= 0 && quantidadeGraduados >= DIAMANTE_GRADUADOS) {

			graduacao = MalaDiretaService.DIAMANTE;
		}

		return graduacao;
	}

	public BigDecimal calcularPorcentagemDeAcordoComGraduacao(String graduacao) {

		BigDecimal porcentagem = BigDecimal.ZERO;

		if (graduacao != null) {

			if (graduacao.equals(MalaDiretaService.GERENTE_BRONZE)) {

				porcentagem = BRONZE_PORCENTAGEM;
			}

			if (graduacao.equals(MalaDiretaService.GERENTE_PRATA)) {

				porcentagem = PRATA_PORCENTAGEM;
			}

			if (graduacao.equals(MalaDiretaService.GERENTE_OURO)) {

				porcentagem = OURO_PORCENTAGEM;
			}

			if (graduacao.equals(MalaDiretaService.ESMERALDA)) {

				porcentagem = ESMERALDA_PORCENTAGEM;
			}

			if (graduacao.equals(MalaDiretaService.TOPÁZIO)) {

				porcentagem = TOPAZIO_PORCENTAGEM;
			}

			if (graduacao.equals(MalaDiretaService.DIAMANTE)) {

				porcentagem = DIAMANTE_PORCENTAGEM;
			}
		}

		return porcentagem;
	}
}