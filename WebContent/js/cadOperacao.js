
SENAI.cadOperacao = new Object();

$(document).ready(function(){

	SENAI.PATH = "/Senai/rest/";
	SENAI.cadOperacao.cadastrar = function(){


		var cadOperacao = new Object();
		cadOperacao.nome = document.frmAddCadOperacao.operacao.value;

		$.ajax({
			type: "POST",
			url: SENAI.PATH + "cadOperacao/inserir",
			data:JSON.stringify(cadOperacao),
			success:function(msg){
				Swal.fire(msg);
				$("#addCadOperacao").trigger("reset");
				SENAI.cadOperacao.buscar();
			},
			error:function(info){
				Swal.fire("Erro ao cadastrar uma nova operação: "+ info.status + " - "+ info.statusText);	
			}
		});	
	};
	SENAI.cadOperacao.buscar = function(){
		var valorBusca = $("#campoBusca").val();
		$.ajax({
			type: "GET",
			url: SENAI.PATH + "cadOperacao/buscar",
			data: "valorBusca="+valorBusca,
			success: function(dados){

				dados = JSON.parse(dados);

				$("#listaCadOperacoes").html(SENAI.cadOperacao.exibir(dados));


			},
			error: function(info){
				SENAI.exibirAviso("Erro ao consultar os cadastros de operação: "+info.status+" - "+info.statusText);
			}
		});

		SENAI.cadOperacao.exibir = function(listaDeCadOperacoes){
			var tabela = 
				"<table>"+
				"<tr>"+	
				"<th> Nome</th>"+
				"<th class='acoes'>Editar</th>"+
				"</tr>";

			if(listaDeCadOperacoes != undefined && listaDeCadOperacoes.length >0){


				for(var i=0; i<listaDeCadOperacoes.length; i++){

					tabela+="<tr>"+	
					"<td>"+listaDeCadOperacoes[i].nome+"</td>"+
					"<td>"+
					"<a onclick=\"SENAI.cadOperacao.exibirEdicao('"+listaDeCadOperacoes[i].id+"')\"><img src='css/img/edit.png' alt='Editar'></a>" +
					"<a onclick=\"SENAI.cadOperacao.excluir('"+listaDeCadOperacoes[i].id+"')\"><img src='css/img/delete.png' alt='Apagar'></a>" +
					"</td>"+
					"</tr>";
				}

			}else if (listaDeCadOperacoes == ""){
				tabela += "<tr><td colspan='6'>Nenhum registro encontrado</td></tr>";
			}
			tabela +="</table>";

			return tabela;

			$("#listaCadOperacoess").html(tabela);
		}
	}
	SENAI.cadOperacao.buscar();
	SENAI.cadOperacao.exibirEdicao = function(id){
		$.ajax({
			type:"GET",
			url: SENAI.PATH +"cadOperacao/checkId",
			data: "id="+id,
			success: function(cadOperacao){
					document.frmEditaCadOperacao.idCadOperacao.value=cadOperacao.id;			
					document.frmEditaCadOperacao.operacao.value = cadOperacao.nome;
					
					var modalEditaCadOperacao = {
							title: "Editar Operações",
							height: 200,
							width: 400,
							modal: true,
							buttons:{
								"Salvar":function(){
									
									SENAI.cadOperacao.editar();							
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
					$("#modalEditaCadOperacao").dialog(modalEditaCadOperacao);
		
			},
			error: function(info){
				Swal.fire("Erro ao buscar cadastro para edição: "+info.status+" - "+info.statusText);
			}

		});
	}
	
	SENAI.cadOperacao.editar = function(){
		 
		
		var cadOperacao = new Object();
		cadOperacao.id =document.frmEditaCadOperacao.idCadOperacao.value;
		cadOperacao.nome=document.frmEditaCadOperacao.operacao.value;
		
		$.ajax({
			type:"PUT",
			url: SENAI.PATH + "cadOperacao/alterar",
			data:JSON.stringify(cadOperacao),
			success: function(msg){
				Swal.fire(msg);
				SENAI.cadOperacao.buscar();
				$("#modalEditaCadOperacao").dialog("close");
			},
			error: function(info){
				Swal.fire("Erro ao editar cadastro: "+ info.status+" - "+info.statusText);
			}
		});
	};
	SENAI.cadOperacao.excluir = function(id){
		$.ajax({
			type:"DELETE",
			url: SENAI.PATH +"cadOperacao/excluir/"+id,
			success: function(msg){
				Swal.fire(msg);
				SENAI.cadOperacao.buscar();
			},
			error: function(info){
				Swal.fire("Erro ao excluir operação: " + info.status + " - " + info.statusText);
			}
		});
	};
})
