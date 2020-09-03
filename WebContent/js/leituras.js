

SENAI.leituras = new Object();

$(document).ready(function(){

	SENAI.PATH = "/Senai/rest/"

		SENAI.leituras.carregaProducao = function(id){

		if(id!=undefined){
			selectP = "#selProducao";
		}else{
			selectP = "#selProducao";
		}

		$.ajax({
			type:"GET",
			url: SENAI.PATH + "producao/buscarSelP",
			success:function(producao){

				if(producao!=""){
					$(selectP).html("");
					var option = document.createElement("option");
					option.setAttribute ("value","");
					option.innerHTML = ("Escolha");
					$(selectP).append(option);

					for (var i=0;i<producao.length;i++){
						var option = document.createElement("option");
						option.setAttribute ("value",producao[i].id);

						if((id!=undefined)&&(id==producao[i].id))
							option.setAttribute ("selected", "selected");

						option.innerHTML = (producao[i].codigo);
						$(selectP).append(option);

					}
				}else{

					$(selectP).html("");
					var option = document.createElement("option");
					option.setAttribute ("value","");
					$(selectP).append(option);
					$(selectP).addClass("aviso");

				}


			},
			error:function(info){
				SENAI.exibirAviso("Erro ao buscar as produções: "+info.status+" - "+info.statusText);
				$(selectP).html(".");
				var option = document.createElement("option");
				option.setAttribute ("value","");
				option.innerHTML = ("Erro ao carregar produções!");
				$(selectP).append(option);
				$(selectP).addClass("aviso");
			}
		});
	}
	SENAI.leituras.carregaProducao();
	SENAI.leituras.carregaOperador = function(id){

		if(id!=undefined){
			selectO = "#selOperador";
		}else{
			selectO = "#selOperador";
		}

		$.ajax({
			type:"GET",
			url: SENAI.PATH + "colaborador/buscarSelO",
			success:function(operador){

				if(operador!=""){
					$(selectO).html("");
					var option = document.createElement("option");
					option.setAttribute ("value","");
					option.innerHTML = ("Escolha");
					$(selectO).append(option);

					for (var i=0;i<operador.length;i++){
						var option = document.createElement("option");
						option.setAttribute ("value",operador[i].id);

						if((id!=undefined)&&(id==operador[i].id))
							option.setAttribute ("selected", "selected");

						option.innerHTML = (operador[i].nome);
						$(selectO).append(option);

					}
				}else{

					$(selectO).html("");
					var option = document.createElement("option");
					option.setAttribute ("value","");
					$(selectO).append(option);
					$(selectO).addClass("aviso");

				}


			},
			error:function(info){
				SENAI.exibirAviso("Erro ao buscar os operadores: "+info.status+" - "+info.statusText);
				$(selectO).html("");
				var option = document.createElement("option");
				option.setAttribute ("value","");
				option.innerHTML = ("Erro ao carregar operadores!");
				$(selectO).append(option);
				$(selectO).addClass("aviso");
			}
		});
	}
	SENAI.leituras.carregaOperador();


	SENAI.leituras.buscar = function(){
		var valorBusca = $("#campoBuscaP").val();
		console.log("Campo"+valorBusca)
		$.ajax({
			type: "GET",
			url: SENAI.PATH + "leituras/buscar",
			data: "valorBusca="+valorBusca,
			success: function(dados){
				
				dados = JSON.parse(dados);
				$("#listaLeituras").html(SENAI.leituras.exibir(dados));

			},
			error: function(info){
				var erro="Erro ao consultar os cadastros de leitura: "+info.status+" - "+info.statusText;
				var b = erro.replace(/['"]+/g, '');
				Swal.fire(b);
			}
		});
		SENAI.leituras.exibir = function(listaDeLeituras){
			console.log(listaDeLeituras)
			var tabela = 
				"<table>"+
				"<tr>"+	
				"<th> Ordem de produção</th>"+
				"<th> Operador</th>"+
				"<th> Valor</th>"+
				"<th> Amostras/Subgrupo</th>"+
				"<th> Total de leituras</th>"+
				"<th> Data</th>"+
				"<th> Observação</th>"+
				"<th class='acoes'>Deletar</th>"+
				"</tr>";

			if(listaDeLeituras != undefined && listaDeLeituras.length >0){

var sg=1;
				for(var i=0; i<listaDeLeituras.length; i++){
sg++;
var  totalLeit = listaDeLeituras[i].amostras*listaDeLeituras[i].subgrupo;
					tabela+="<tr>"+	
					"<td>"+listaDeLeituras[i].producao+"</td>"+
					"<td>"+listaDeLeituras[i].operador+"</td>"+
					"<td>"+listaDeLeituras[i].valor+"</td>"+
					"<td>"+listaDeLeituras[i].amostras+" / "+listaDeLeituras[i].subgrupo+"</td>"+
					"<td>"+totalLeit+"</td>"+
					"<td>"+listaDeLeituras[i].data+"</td>"+
					"<td>"+listaDeLeituras[i].obs+"</td>"+
					"<td>"+
					"<a onclick=\"SENAI.leituras.excluir('"+listaDeLeituras[i].id+"')\"><img src='css/img/delete.png' alt='Apagar'></a>" +
					"</td>"+
					"</tr>";
					if(sg>listaDeLeituras[i].subgrupo){
						sg==1;
					}
					
				}

			}else if (listaDeLeituras == ""){
				tabela += "<tr><td colspan='6'>Nenhum registro encontrado</td></tr>";
			}
			tabela +="</table>";

			return tabela;

			$("#listaLeituras").html(tabela);
		}
	}

	SENAI.leituras.buscar();
	SENAI.leituras.excluir = function(id){
		$.ajax({
			type:"DELETE",
			url: SENAI.PATH +"leituras/excluir/"+id,
			success: function(msg){
				var b = msg.replace(/['"]+/g, '');
				Swal.fire(b);
				
				//SENAI.leituras.atualizarCont(id);
				
				SENAI.leituras.buscar();
			},
			error: function(info){
				var erro ="Erro ao excluir leitura: " + info.status + " - " + info.statusText;
				var b = erro.replace(/['"]+/g, '');
				Swal.fire(b);
			}
		});
	};
	
})