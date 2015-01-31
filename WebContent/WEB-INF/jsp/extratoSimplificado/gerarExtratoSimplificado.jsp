<%@ include file="/base.jsp"%>

<style>
.menu {
	display: none;
	width: 0px;
	min-width: 0px;
}

.conteudo {
	width: 94%;
}

.table:hover {
	cursor: auto;
}

.table tbody tr:hover td, .table tbody tr:hover th {
	background-color: #FFF;
}
</style>

<a class="btn" onclick="window.history.back()"> Voltar </a>

<br>
<br>

<h3>Extrato simplificado</h3>

<h6 style="color: rgb(100, 100, 100);">Mês/ano: ${mes}/${ano}</h6>
<h6 style="color: rgb(100, 100, 100);">Distribuidor:
	${sessaoUsuario.usuario.id_Codigo} - ${sessaoUsuario.usuario.posAtual}
	- ${sessaoUsuario.usuario.vNome}</h6>

<br>
<br>

<table class="table">

	<tr>
		<td>Bônus de início rápido</td>
		<td>R$<fmt:formatNumber value="${bonificacaoInicioRapido}"
				pattern="#,##0.00" />
		</td>
		<td></td>
	</tr>

	<tr>
		<td>Bônus de Ativação</td>
		<td>R$<fmt:formatNumber value="${bonificacaoAtivacao2}"
				pattern="#,##0.00" />
		</td>
		<td></td>
	</tr>

	<tr>
		<td>Bônus Unilevel</td>
		<td>R$<fmt:formatNumber value="${bonificacaoUniLevel}"
				pattern="#,##0.00" />
		</td>
		<td></td>
	</tr>

	<tr>
		<td>Bônus de Expansão</td>
		<td>R$<fmt:formatNumber value="${bonificacaoExpansao}"
				pattern="#,##0.00" />
		</td>
		<td></td>
	</tr>

	<tr style="background-color: rgb(245, 250, 138);">
		<td class="centralizado"><b> Total </b></td>
		<td>R$<fmt:formatNumber
				value="${bonificacaoAtivacao2 + bonificacaoInicioRapido + bonificacaoUniLevel + bonificacaoExpansao}"
				pattern="#,##0.00" />
		</td>
		<td></td>
	</tr>
</table>
