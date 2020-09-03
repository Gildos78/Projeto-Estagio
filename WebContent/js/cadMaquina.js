
SENAI.cadMaquina = new Object();

$(document).ready(function(){

	SENAI.PATH = "/Senai/rest/";
	SENAI.cadMaquina.cadastrar = function(){


		var cadMaquina = new Object();
		cadMaquina.nome = document.frmAddCadMaquina.maquina.value;

		$.ajax({
			type: "POST",
			url: SENAI.PATH + "cadMaquina/inserir",
			data:JSON.stringify(cadMaquina),
			success:function(msg){
				Swal.fire(msg);
				$("#addCadMaquina").trigger("reset");
				SENAI.cadMaquina.buscar();
			},
			error:function(info){
				Swal.fire("Erro ao cadastrar uma nova maquina: "+ info.status + " - "+ info.statusText);	
			}
		});	
	};
	SENAI.cadMaquina.buscar = function(){
		var valorBusca = $("#campoBusca").val();
		$.ajax({
			type: "GET",
			url: SENAI.PATH + "cadMaquina/buscar",
			data: "valorBusca="+valorBusca,
			success: function(dados){

				dados = JSON.parse(dados);

				$("#listaCadMaquinas").html(SENAI.cadMaquina.exibir(dados));


			},
			error: function(info){
				SENAI.exibirAviso("Erro ao consultar os cadastros de maquina: "+info.status+" - "+info.statusText);
			}
		});

		SENAI.cadMaquina.exibir = function(listaDeCadMaquinas){
			var tabela = 
				"<table>"+
				"<tr>"+	
				"<th> Nome</th>"+
				"<th class='acoes'>Editar</th>"+
				"</tr>";

			if(listaDeCadMaquinas != undefined && listaDeCadMaquinas.length >0){


				for(var i=0; i<listaDeCadMaquinas.length; i++){

					tabela+="<tr>"+	
					"<td>"+listaDeCadMaquinas[i].nome+"</td>"+
					"<td>"+
					"<a onclick=\"SENAI.cadMaquina.exibirEdicao('"+listaDeCadMaquinas[i].id+"')\"><img src='css/img/edit.png' alt='Editar'></a>" +
					"<a onclick=\"SENAI.cadMaquina.excluir('"+listaDeCadMaquinas[i].id+"')\"><img src='css/img/delete.png' alt='Apagar'></a>" +
					"</td>"+
					"</tr>";
				}

			}else if (listaDeCadMaquinas == ""){
				tabela += "<tr><td colspan='6'>Nenhum registro encontrado</td></tr>";
			}
			tabela +="</table>";

			return tabela;

			$("#listaCadMaquinas").html(tabela);
		}
	}
	SENAI.cadMaquina.buscar();
	SENAI.cadMaquina.exibirEdicao = function(id){
		$.ajax({
			type:"GET",
			url: SENAI.PATH +"cadMaquina/checkId",
			data: "id="+id,
			success: function(cadMaquina){
					document.frmEditaCadMaquina.idCadMaquina.value=cadMaquina.id;			
					document.frmEditaCadMaquina.maquina.value = cadMaquina.nome;
					
					var modalEditaCadMaquina = {
							title: "Editar Maquinas",
							height: 200,
							width: 400,
							modal: true,
							buttons:{
								"Salvar":function(){
									
									SENAI.cadMaquina.editar();							
								},
								"Cancelar": function(){
									$(this).dialog("close");
								}
							},
							close:function(){
								//caso o usuário simplesmente feche a caixa de edição
								//não deve acontecer nada
							}
					};
					$("#modalEditaCadMaquina").dialog(modalEditaCadMaquina);
		
			},
			error: function(info){
				Swal.fire("Erro ao buscar cadastro para edição: "+info.status+" - "+info.statusText);
			}

		});
	}
	
	SENAI.cadMaquina.editar = function(){
		 
		
		var cadMaquina = new Object();
		cadMaquina.id =document.frmEditaCadMaquina.idCadMaquina.value;
		cadMaquina.nome=document.frmEditaCadMaquina.maquina.value;
		
		$.ajax({
			type:"PUT",
			url: SENAI.PATH + "cadMaquina/alterar",
			data:JSON.stringify(cadMaquina),
			success: function(msg){
				Swal.fire(msg);
				SENAI.cadMaquina.buscar();
				$("#modalEditaCadMaquina").dialog("close");
			},
			error: function(info){
				Swal.fire("Erro ao editar cadastro: "+ info.status+" - "+info.statusText);
			}
		});
	};
	SENAI.cadMaquina.excluir = function(id){
		$.ajax({
			type:"DELETE",
			url: SENAI.PATH +"cadMaquina/excluir/"+id,
			success: function(msg){
				Swal.fire(msg);
				SENAI.cadMaquina.buscar();
			},
			error: function(info){
				Swal.fire("Erro ao excluir maquina: " + info.status + " - " + info.statusText);
			}
		});
	};
})
