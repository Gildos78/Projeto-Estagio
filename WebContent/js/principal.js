SENAI.producao = new Object();
SENAI.leituras = new Object();

$(document).ready(function(){

	SENAI.PATH = "/Senai/rest/"
		
	SENAI.producao.buscarDetails = function(id){
		console.log(id)
		$.ajax({
			type: "GET",
			url: SENAI.PATH + "leituras/buscarDetails",
			data: "id="+id,
			success: function(dados){

				dados = JSON.parse(dados);
				console.log(dados)
				$("#listaLeiturasDetails").html(SENAI.leituras.exibirDetails(dados));


			},
			error: function(info){
				var erro="Erro ao consultar os cadastros de leitura: "+info.status+" - "+info.statusText;
				var b = erro.replace(/['"]+/g, '');
				Swal.fire(b);
			}
		});
		SENAI.leituras.exibirDetails = function(listaDeLeituras){
			var arrayDate = [];
			var arrayS = [];
			var arrayI = [];
			var arrayLeituras = [];
			var costumerName;
			var tabela = 
				"<table>"+
				"<tr>"+	
				"<th> Ordem de produção</th>"+
				"<th> Operador</th>"+
				"<th> Valor</th>"+
				"<th> Total de Amostras</th>"+
				"<th> Data</th>"+
				"</tr>";

			if(listaDeLeituras != undefined && listaDeLeituras.length >0){

				
				for(var i=0; i<listaDeLeituras.length; i++){
					costumerName = listaDeLeituras[i].cliente;
					arrayDate.push(listaDeLeituras[i].data)	
					arrayS.push(listaDeLeituras[i].especMax)
					arrayI.push(listaDeLeituras[i].especMin)
					arrayLeituras.push(listaDeLeituras[i].valor)
					tabela+="<tr>"+	
					"<td>"+listaDeLeituras[i].producao+"</td>"+
					"<td>"+listaDeLeituras[i].operador+"</td>"+
					"<td>"+listaDeLeituras[i].valor+"</td>"+
					"<td>"+(i+1)+" / "+listaDeLeituras[i].amostras+"</td>"+
					"<td>"+listaDeLeituras[i].data+"</td>"+
					"</tr>";
				}

			}else if (listaDeLeituras == ""){
				tabela += "<tr><td colspan='6'>Nenhum registro encontrado</td></tr>";
			}
			tabela +="</table>";
			console.log(arrayI)
			$("#costumer").html(costumerName);
			var ctx = document.getElementsByClassName("line-chart");

			var chartGraph = new Chart(ctx, {
				type : 'line',
				data : {
					labels : arrayDate,
					datasets : [ {
						label : "Especificações Máximas",
						data : arrayS,
						borderWidth : 2,
						borderColor : 'blue',
						backgroundColor : 'transparent',
					}, {
						label : "Especificações Mínimas",
						data : arrayI,
						borderWidth : 2,
						borderColor : 'rgba(88, 144,2, 0.85)',
						backgroundColor : 'transparent',
					},{
						label : "Leituras",
						data : arrayLeituras,
						borderWidth : 1,
						borderColor : 'red',
						backgroundColor : 'transparent',
					},

					]
				},
//				options: {
//					title:{
//						display:true,
//						fontSize:20,
//						text: "RELATORIO"	
//						},
//						labels:{
//							fontStyle: "bold"
//						}
//					}
			});

			
			
			return tabela;

			$("#listaLeiturasDetails").html(tabela);
		}
	}
})