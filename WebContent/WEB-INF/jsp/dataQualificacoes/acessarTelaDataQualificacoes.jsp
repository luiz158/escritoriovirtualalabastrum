<%@ include file="/base.jsp" %> 

<h4>  Datas de qualificações  </h4>

<br>

<table style="width: 300px;" >
	<tr>
		<td> <b> Data de ingresso: </b> </td>
		<td> <fmt:formatDate value="${sessaoUsuario.usuario.dt_Ingresso.time}" type="DATE" /> </td>
	</tr>
	<tr>
		<td> <b> Data Executivo: </b> </td>
		<td> ${sessaoUsuario.usuario.dt_S} </td>
	</tr>
	<tr>
		<td> <b> Data Gerente Bronze: </b> </td>
		<td> ${sessaoUsuario.usuario.dt_GB} </td>
	</tr>
	<tr>
		<td> <b> Data Gerente Prata: </b> </td>
		<td> ${sessaoUsuario.usuario.dt_GP} </td>
	</tr>
	<tr>
		<td> <b> Data Gerente Ouro: </b> </td>
		<td> ${sessaoUsuario.usuario.dt_GO} </td>
	</tr>
	<tr>
		<td> <b> Data Esmeralda: </b> </td>
		<td> ${sessaoUsuario.usuario.dt_M} </td>
	</tr>
	<tr>
		<td> <b> Data Topázio: </b> </td>
		<td> ${sessaoUsuario.usuario.dt_LA} </td>
	</tr>
	<tr>
		<td> <b> Data Diamante: </b> </td>
		<td> ${sessaoUsuario.usuario.dt_CR} </td>
	</tr>
	<tr>
		<td> <b> Data Diamante Duplo: </b> </td>
		<td> ${sessaoUsuario.usuario.dt_DR} </td>
	</tr>
	<tr>
		<td> <b> Data Diamante Triplo: </b> </td>
		<td> ${sessaoUsuario.usuario.dt_DD} </td>
	</tr>
	<tr>
		<td> <b> Data Diamante Plenus: </b> </td>
		<td> ${sessaoUsuario.usuario.dt_DS} </td>
	</tr>
	<tr>
		<td> <b> Data Diamante Real: </b> </td>
		<td> ${sessaoUsuario.usuario.dt_GE} </td>
	</tr>
	<tr>
		<td> <b> Data Presidente: </b> </td>
		<td> ${sessaoUsuario.usuario.dt_Pres} </td>
	</tr>
</table>