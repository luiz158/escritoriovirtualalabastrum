<%@ include file="/base.jsp" %> 

<style>

.menu {
	display: none;
    width: 0px;
	min-width: 0px;
}

.conteudo {
    width: 94%;
}

</style>

<a class="btn" onclick="window.history.back()" > Voltar </a>

<br><br>

<h3> Extrato simplificado </h3>

<h6 style="color: rgb(100, 100, 100);" >M�s/ano: ${mes}/${ano}</h6>
<h6 style="color: rgb(100, 100, 100);" >Distribuidor: ${sessaoUsuario.usuario.id_Codigo} - ${sessaoUsuario.usuario.posAtual} - ${sessaoUsuario.usuario.vNome}</h6>

<br> <br>

<table class="table">
	<tr>
		<td>
			B�nus de Venda Direta
		</td>
		<td>
			(AINDA EM CONSTRU��O)
		</td>
		<td> </td>		
	</tr>
	<tr>
		<td>
			B�nus de Ingresso
		</td>
		<td>
			 R$<fmt:formatNumber value="${bonificacaoIngresso}" pattern="#,##0.00" />
		</td>	
		<td> <a href="<c:url value="/bonificacaoIngresso/gerarRelatorioBonificacaoIngresso?ano=${ano}&mes=${mes}"/>"> Clique para ver detalhes </a> </td>		
	</tr>
	<tr>
		<td>
			B�nus de Ativa��o
		</td>
		<td>
			 (AINDA EM CONSTRU��O)
		</td>	
		<td> </td>		
	</tr>
	<tr>
		<td>
			B�nus de Gradua��o
		</td>
		<td>
			 (AINDA EM CONSTRU��O)
		</td>	
		<td> </td>		
	</tr>
	<tr>
		<td>
			B�nus de Lideran�a
		</td>
		<td>
			 (AINDA EM CONSTRU��O)
		</td>	
		<td> </td>		
	</tr>
	<tr>
		<td>
			B�nus Global
		</td>
		<td>
			 (AINDA EM CONSTRU��O)
		</td>	
		<td> </td>		
	</tr>
	<tr style="background-color: rgb(245, 250, 138);" >
		<td class="centralizado"> <b> Total </b> </td>
		<td > R$<fmt:formatNumber value="${bonificacaoIngresso + 0 + 0}" pattern="#,##0.00" /> </td>
		<td></td>
	</tr>
</table>
