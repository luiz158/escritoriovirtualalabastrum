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

.dica{
	color: rgb(170, 170, 170);
}

b{
	font-weight: bold;
	color: rgb(120, 120, 120);
	font-size: 14px;
}

#relacionamentos{
	
	background-color: white;
	border: 1px solid #ddd;
	border-radius: 7px;
	width: 700px;
	position: fixed;
	top: 1%;
	left: 20%;
	padding: 20px;
	padding-top: 10px;
	box-shadow: 4px 4px 4px #888;
	font-size: 11px;
}

#relacionamentos span{
	font-size: 10px;
}

#relacionamentos b{
	font-size: 10px;
}

label.labelInformacoes{
	display: inline-block;
	font-weight: bold;
	font-size: 12px;
}

#fecharJanelaRelacionamentos{

	float: right;
	font-size: 21px;
	color: grey;
	cursor: pointer;
}

</style>

<a class="btn" href="<c:url value="/malaDireta/acessarTelaMalaDireta"/>" > Voltar </a>

<br><br>

<h3> Mala Direta </h3>

<h6 style="color: rgb(100, 100, 100);" >Líder da equipe: ${usuarioPesquisado.id_Codigo} - ${usuarioPesquisado.posAtual} - ${usuarioPesquisado.vNome} / ${usuarioPesquisado.vNomeTitular2} </h6>

<h6 style="color: rgb(100, 100, 100);" >Posição considerada: ${posicaoConsiderada}    </h6>

<h6 style="color: rgb(160, 30, 30);" > 
	Quantidade de registros: ${quantidadeElementosMalaDireta} <br><br>
	<span class="dica" > (<b>Dica 1:</b> Para encontrar registros mais rapidamente, utilize a pesquisa através do atalho <b>CTRL + F</b>) </span> <br>
	<span class="dica" > (<b>Dica 2:</b> Clique sobre um distribuidor para ver os seus relacionamentos) </span> 
</h6>  

<br> 

<c:choose>
	<c:when test="${!empty malaDireta}">

		<table class="table table-striped table-bordered">
			<thead>
		    	<tr>
                    <th> Código </th>
                    <th> Posição </th>
                    <th> Nome </th>
                    <th> Telefones </th>
                    <th> Celular </th>
                    <th> Email </th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${malaDireta}" var="item">
					<tr class="usuario" id="${item.usuario.id_Codigo}" >
                        <td class="centralizado" > 
                        	${item.usuario.id_Codigo}
                        </td>
                        <td class="centralizado" > ${item.usuario.posAtual} </td>
                        <td> ${item.usuario.vNome} </td>
                        <td> ${item.usuario.tel} </td>
                        <td> ${item.usuario.cadCelular} </td>
                        <td> ${item.usuario.eMail} </td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:when>
	<c:otherwise>
		<br>  <br>  <h4> Nenhum registro foi encontrado </h4>
	</c:otherwise>
</c:choose>

<br>

<a class="btn" href="<c:url value="/malaDireta/acessarTelaMalaDireta"/>" > Voltar </a>


<div id="relacionamentos" style="display: none" >
	
	<p title="Fechar" id="fecharJanelaRelacionamentos" > x </p>
	<h4 style="text-align: center;font-size: 14px;" > Relacionamentos </h4>
	
	<table style="width: 70%" >
		<tr>
			<td> <b> Patroc: </b> </td>
			<td id="informacaoNomePatroc" >  </td>
			<td id="informacaoCodigoPatroc" >  </td>
		</tr>	
		<tr>
			<td> <b> Ex: </b> </td>
			<td id="informacaoNomeS" >  </td>
			<td id="informacaoCodigoS" >  </td>
		</tr>	
		<tr>
			<td> <b> G1: </b> </td>
			<td id="informacaoNomeM1" >  </td>
			<td id="informacaoCodigoM1" >  </td>
		</tr>	
		<tr>
			<td> <b> G2: </b> </td>
			<td id="informacaoNomeM2" >  </td>
			<td id="informacaoCodigoM2" >  </td>
		</tr>	
		<tr>
			<td> <b> G3: </b> </td>
			<td id="informacaoNomeM3" >  </td>
			<td id="informacaoCodigoM3" >  </td>
		</tr>
		<tr>
			<td> <b> G4: </b> </td>
			<td id="informacaoNomeM4" >  </td>
			<td id="informacaoCodigoM4" >  </td>
		</tr>
		<tr>
			<td> <b> G5: </b> </td>
			<td id="informacaoNomeM5" >  </td>
			<td id="informacaoCodigoM5" >  </td>
		</tr>
		<tr>
			<td> <b> G6: </b> </td>
			<td id="informacaoNomeM6" >  </td>
			<td id="informacaoCodigoM6" >  </td>
		</tr>
		<tr>
			<td> <b> G7: </b> </td>
			<td id="informacaoNomeM7" >  </td>
			<td id="informacaoCodigoM7" >  </td>
		</tr>
		<tr>
			<td> <b> G8: </b> </td>
			<td id="informacaoNomeM8" >  </td>
			<td id="informacaoCodigoM8" >  </td>
		</tr>
		<tr>
			<td> <b> G9: </b> </td>
			<td id="informacaoNomeM9" >  </td>
			<td id="informacaoCodigoM9" >  </td>
		</tr>
		<tr>
			<td> <b> G10: </b> </td>
			<td id="informacaoNomeM10" >  </td>
			<td id="informacaoCodigoM10" >  </td>
		</tr>
		<tr>
			<td> <b> GB: </b> </td>
			<td id="informacaoNomeGb" >  </td>
			<td id="informacaoCodigoGb" >  </td>
		</tr>	
		<tr>
			<td> <b> GP: </b> </td>
			<td id="informacaoNomeGp" >  </td>
			<td id="informacaoCodigoGp" >  </td>
		</tr>	
		<tr>
			<td> <b> GO: </b> </td>
			<td id="informacaoNomeGo" >  </td>
			<td id="informacaoCodigoGo" >  </td>
		</tr>
		<tr>
			<td> <b> Esm: </b> </td>
			<td id="informacaoNomeM" >  </td>
			<td id="informacaoCodigoM" >  </td>
		</tr>
		<tr>
			<td> <b> Top: </b> </td>
			<td id="informacaoNomeLa" >  </td>
			<td id="informacaoCodigoLa" >  </td>
		</tr>	
		<tr>
			<td> <b> Dmt: </b> </td>
			<td id="informacaoNomeCr" >  </td>
			<td id="informacaoCodigoCr" >  </td>
		</tr>	
		<tr>
			<td> <b> DD: </b> </td>
			<td id="informacaoNomeDr" >  </td>
			<td id="informacaoCodigoDr" >  </td>
		</tr>	
		<tr>
			<td> <b> DT: </b> </td>
			<td id="informacaoNomeDd" >  </td>
			<td id="informacaoCodigoDd" >  </td>
		</tr>	
		<tr>
			<td> <b> DP: </b> </td>
			<td id="informacaoNomeDs" >  </td>
			<td id="informacaoCodigoDs" >  </td>
		</tr>	
		<tr>
			<td> <b> DR: </b> </td>
			<td id="informacaoNomeGe" >  </td>
			<td id="informacaoCodigoGe" >  </td>
		</tr>				
	</table>

</div>


<script>
	
	jQuery(document).ready(function(){
		
		jQuery(".usuario").click(function(){
			
			var idUsuario = jQuery(this).attr("id");

			jQuery.ajax({ 
		        type: 'GET',
		        url: '<c:url value="/malaDireta/buscarRelacionamentosUsuarioSelecionado?codigoUsuario='+idUsuario + ' "/>',
		        dataType: 'json', 
		        success: function(data) { 
		        	
		        	jQuery("#informacaoNomePatroc").text(data.relacionamentos.nomePatroc);
		        	jQuery("#informacaoCodigoPatroc").text(data.relacionamentos.codigoPatroc);
		        	
		        	jQuery("#informacaoNomeS").text(data.relacionamentos.nomeS);
		        	jQuery("#informacaoCodigoS").text(data.relacionamentos.codigoS);
		        	
		        	jQuery("#informacaoNomeM").text(data.relacionamentos.nomeM);
		        	jQuery("#informacaoCodigoM").text(data.relacionamentos.codigoM);
		        	
		        	jQuery("#informacaoNomeM1").text(data.relacionamentos.nomeM1);
		        	jQuery("#informacaoCodigoM1").text(data.relacionamentos.codigoM1);
		        	
		        	jQuery("#informacaoNomeM2").text(data.relacionamentos.nomeM2);
		        	jQuery("#informacaoCodigoM2").text(data.relacionamentos.codigoM2);
		        	
		        	jQuery("#informacaoNomeM3").text(data.relacionamentos.nomeM3);
		        	jQuery("#informacaoCodigoM3").text(data.relacionamentos.codigoM3);
		        	
		        	jQuery("#informacaoNomeM4").text(data.relacionamentos.nomeM4);
		        	jQuery("#informacaoCodigoM4").text(data.relacionamentos.codigoM4);
		        	
		        	jQuery("#informacaoNomeM5").text(data.relacionamentos.nomeM5);
		        	jQuery("#informacaoCodigoM5").text(data.relacionamentos.codigoM5);
		        	
		        	jQuery("#informacaoNomeM6").text(data.relacionamentos.nomeM6);
		        	jQuery("#informacaoCodigoM6").text(data.relacionamentos.codigoM6);
		        	
		        	jQuery("#informacaoNomeM7").text(data.relacionamentos.nomeM7);
		        	jQuery("#informacaoCodigoM7").text(data.relacionamentos.codigoM7);
		        	
		        	jQuery("#informacaoNomeM8").text(data.relacionamentos.nomeM8);
		        	jQuery("#informacaoCodigoM8").text(data.relacionamentos.codigoM8);
		        	
		        	jQuery("#informacaoNomeM9").text(data.relacionamentos.nomeM9);
		        	jQuery("#informacaoCodigoM9").text(data.relacionamentos.codigoM9);
		        	
		        	jQuery("#informacaoNomeM10").text(data.relacionamentos.nomeM10);
		        	jQuery("#informacaoCodigoM10").text(data.relacionamentos.codigoM10);
		        	
		        	jQuery("#informacaoNomeGb").text(data.relacionamentos.nomeGb);
		        	jQuery("#informacaoCodigoGb").text(data.relacionamentos.codigoGb);
		        	
		        	jQuery("#informacaoNomeGp").text(data.relacionamentos.nomeGp);
		        	jQuery("#informacaoCodigoGp").text(data.relacionamentos.codigoGp);
		        	
		        	jQuery("#informacaoNomeGo").text(data.relacionamentos.nomeGo);
		        	jQuery("#informacaoCodigoGo").text(data.relacionamentos.codigoGo);
		        	
		        	jQuery("#informacaoNomeGe").text(data.relacionamentos.nomeGe);
		        	jQuery("#informacaoCodigoGe").text(data.relacionamentos.codigoGe);
		        	
		        	jQuery("#informacaoNomeLa").text(data.relacionamentos.nomeLa);
		        	jQuery("#informacaoCodigoLa").text(data.relacionamentos.codigoLa);
		        	
		        	jQuery("#informacaoNomeLa1").text(data.relacionamentos.nomeLa1);
		        	jQuery("#informacaoCodigoLa1").text(data.relacionamentos.codigoLa1);
		        	
		        	jQuery("#informacaoNomeLa2").text(data.relacionamentos.nomeLa2);
		        	jQuery("#informacaoCodigoLa2").text(data.relacionamentos.codigoLa2);
		        	
		        	jQuery("#informacaoNomeCr").text(data.relacionamentos.nomeCr);
		        	jQuery("#informacaoCodigoCr").text(data.relacionamentos.codigoCr);
		        	
		        	jQuery("#informacaoNomeCr1").text(data.relacionamentos.nomeCr1);
		        	jQuery("#informacaoCodigoCr1").text(data.relacionamentos.codigoCr1);
		        	
		        	jQuery("#informacaoNomeCr2").text(data.relacionamentos.nomeCr2);
		        	jQuery("#informacaoCodigoCr2").text(data.relacionamentos.codigoCr2);
		        	
		        	jQuery("#informacaoNomeDr").text(data.relacionamentos.nomeDr);
		        	jQuery("#informacaoCodigoDr").text(data.relacionamentos.codigoDr);
		        	
		        	jQuery("#informacaoNomeDd").text(data.relacionamentos.nomeDd);
		        	jQuery("#informacaoCodigoDd").text(data.relacionamentos.codigoDd);
		        	
		        	jQuery("#informacaoNomeDs").text(data.relacionamentos.nomeDs);
		        	jQuery("#informacaoCodigoDs").text(data.relacionamentos.codigoDs);

		        }
			});
			
			jQuery("#relacionamentos").show();
		});
		
		jQuery("#fecharJanelaRelacionamentos").click(function(){
			
			jQuery("#relacionamentos").hide();
		});
	});

</script>

