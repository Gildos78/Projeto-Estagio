SENAI.producao = new Object();
SENAI.leituras = new Object();

$(document).ready(function(){

	SENAI.producao.formatDate = function(input) {
		var datePart = input.match(/\d+/g),
		ano = datePart[0].substring(0), //0 = 4 digitos e 2 = 2 digitos
		mes = datePart[1], 
		dia = datePart[2], 
		hora = datePart[3], 
		minuto = datePart[4]
		segundo = datePart[5];

		return dia+'/'+mes+'/'+ano+' '+hora+':'+minuto+':'+segundo;
	}


	SENAI.PATH = "/Senai/rest/"
		SENAI.producao.buscarData = function(){
		var valorDataIni = $(".dataI").val();
		var valorDataFin = $(".dataF").val();
		if(valorDataIni > valorDataFin){
			Swal.fire({
				icon: 'error',
				title: 'Erro!',
				text: 'Primeira data não pode ser maior que a segunda data!'
			})
		}else{

			$.ajax({
				type: "GET",
				url: SENAI.PATH + "producao/buscarData",
				data: {valorDataIni: valorDataIni, valorDataFin: valorDataFin},
				success: function(dados){

					dados = JSON.parse(dados);

					$("#listaProducoesD").html(SENAI.producao.exibirD(dados));


				},
				error: function(info){
					Swal.fire("Erro ao consultar os cadastros de producao: "+info.status+" - "+info.statusText);
				}
			});
		}
	}
	SENAI.producao.buscarDes = function(){
		var valorBusca = $(".campoBusca").val();
		var valorDataIni = $(".dataI").val();
		var valorDataFin = $(".dataF").val();

		$.ajax({
			type: "GET",
			url: SENAI.PATH + "producao/buscarDes",
			data: {valorBusca: valorBusca, valorDataIni: valorDataIni, valorDataFin: valorDataFin},
			success: function(dados){

				dados = JSON.parse(dados);
				$("#listaProducoesD").html(SENAI.producao.exibirD(dados));


			},
			error: function(info){
				Swal.fire("Erro ao consultar os cadastros de producao: "+info.status+" - "+info.statusText);
			}
		});

		SENAI.producao.exibirD = function(listaDeProducoes){

			var tabela = 
				"<table>"+
				"<tr>"+	
				"<th> Ordem de produção</th>"+
				"<th> Cliente</th>"+
				"<th> Data Inícial</th>"+
				"<th> Data Final</th>"+
				"<th> Descrição</th>"+
				"<th> Espec. Mínima</th>"+
				"<th> Espec. Máxima</th>"+
				"<th> Nº amostras</th>"+
				"<th> Máquina</th>"+
				"<th> Operação</th>"+
				"<th> Subgrupo</th>"+
				"<th class='acoes'>Detalhes</th>"+
				"</tr>";

			if(listaDeProducoes != undefined && listaDeProducoes.length >0){


				for(var i=0; i<listaDeProducoes.length; i++){

					tabela+="<tr>"+	
					"<td>"+listaDeProducoes[i].codigo+"</td>"+
					"<td>"+listaDeProducoes[i].cliente+"</td>"+
					"<td>"+SENAI.producao.formatDate(listaDeProducoes[i].dataInicio)+"</td>"+
					"<td>"+SENAI.producao.formatDate(listaDeProducoes[i].dataFinal)+"</td>"+
					"<td>"+listaDeProducoes[i].descricao+"</td>"+
					"<td>"+listaDeProducoes[i].espMin+"</td>"+
					"<td>"+listaDeProducoes[i].espMax+"</td>"+
					"<td>"+listaDeProducoes[i].numAmostras+"</td>"+
					"<td>"+listaDeProducoes[i].maquinaId+"</td>"+
					"<td>"+listaDeProducoes[i].operacao+"</td>"+
					"<td>"+listaDeProducoes[i].subgrupo+"</td>"+
					"<td>"+
					"<a onclick=\"SENAI.leituras.buscarMinino('"+listaDeProducoes[i].id+"')\"><i class='fas fa-mouse-pointer'></i></a>" +
					"</td>"+
					"</tr>";
				}

			}else if (listaDeProducoes == ""){
				tabela += "<tr><td colspan='6'>Nenhum registro encontrado</td></tr>";
			}
			tabela +="</table>";

			return tabela;

			$("#listaProducoesD").html(tabela);
		}
	}
	SENAI.producao.buscarDes();
	SENAI.leituras.buscarMinino = function(id){
		$.ajax({
			type: "GET",
			url: SENAI.PATH + "leituras/buscarCod",
			data: "id="+id,
			success: function(leituras){
				var Qtd = leituras.count;
				var SubGrupo = leituras.subgrupo;

				if((SubGrupo==1&&Qtd<5)||Qtd<SubGrupo){
					var msgJS = "Não há leituras suficientes para análise";
					var b = msgJS.replace(/['"]+/g, '');
					Swal.fire(b)
				}else{
					SENAI.producao.buscarDetails(id)

				}
			},
			error: function(info){
				var erro ="Erro ao consultar os cadastros de leitura: "+info.status+" - "+info.statusText;
				var b = erro.replace(/['"]+/g, '');
				Swal.fire(b);
			}
		});
	}

	SENAI.producao.buscarDetails = function(id){
		$.ajax({
			type: "GET",
			url: SENAI.PATH + "leituras/buscarDetails",
			data: "id="+id,
			success: function(dados){

				dados = JSON.parse(dados);
				$("#listaLeiturasDetails").html(SENAI.leituras.exibirDetails(dados));
				openButton(event, 'detalhes')
				SENAI.leituras.exibirMediaX(dados)
				//SENAI.leituras.exibirAmplitude(dados)
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
				"<th> Subgrupo/Leitura</th>"+
				"<th> Amostras/Leitura</th>"+
				"<th> Total/Leitura</th>"+
				"<th> Data</th>"+
				"</tr>";

			if(listaDeLeituras != undefined && listaDeLeituras.length >0){

				var sub = 0;
				var lei = 1;
				for(var i=0; i<listaDeLeituras.length; i++){
					if(sub>=listaDeLeituras[i].subgrupo){
						sub=0;
						lei++
					}
					sub++;
					var totalLeit = listaDeLeituras[i].amostras*listaDeLeituras[i].subgrupo;
					costumerName = listaDeLeituras[i].cliente;
					arrayDate.push(listaDeLeituras[i].data)	
					arrayS.push(listaDeLeituras[i].especMax)
					arrayI.push(listaDeLeituras[i].especMin)
					arrayLeituras.push(listaDeLeituras[i].valor)
					tabela+="<tr>"+	
					"<td>"+listaDeLeituras[i].producao+"</td>"+
					"<td>"+listaDeLeituras[i].operador+"</td>"+
					"<td>"+listaDeLeituras[i].valor+"</td>"+
					"<td>"+listaDeLeituras[i].subgrupo+" / "+sub+"</td>"+
					"<td>"+listaDeLeituras[i].amostras+" / "+lei+"</td>"+
					"<td>"+totalLeit+" / "+(i+1)+"</td>"+
					"<td>"+listaDeLeituras[i].data+"</td>"+
					"</tr>";

				}

			}else if (listaDeLeituras == ""){
				tabela += "<tr><td colspan='6'>Nenhum registro encontrado</td></tr>";
			}
			tabela +="</table>";
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
//				title:{
//				display:true,
//				fontSize:20,
//				text: "RELATORIO"	
//				},
//				labels:{
//				fontStyle: "bold"
//				}
//				}
			});



			return tabela;

		}
	}
	SENAI.leituras.exibirMediaX = function(listaDeLeituras){
		var arrayDate = [];
		var arrayDateAm = [];
		var arrayS = [];
		var arrayI = [];
		var arraySA = [];
		var arrayIA = [];
		var arrayLeituras = [];
		var arrayAmplitude = [];
		var soma = 0;
		var valLeit = 0;
		var totalAm = 0;
		for(var i=0; i<listaDeLeituras.length; i++){
			if(listaDeLeituras[i].subgrupo==1){
				soma = listaDeLeituras[i].valor;
				valLeit = valLeit+soma;
				totalAm = listaDeLeituras[i].amostras;
			}
		}
		var mediaX = valLeit/totalAm
		var somaAmp = 0;
		var totalAmAmpl = 0;
		var subAmpl = 0;
		var limite = 0
		var lim= 0
		var arraySub = [];
		var arraySub2 = [];
		var somaSub = 0;
		var maiorSub=0;
		var menorSub=10000;
		for(var i=0; i<listaDeLeituras.length; i++){
			limite = listaDeLeituras[i].subgrupo
			if(listaDeLeituras[i].subgrupo==1){
				totalAmAmpl = listaDeLeituras[i].amostras;
				var value = listaDeLeituras[i].valor
				value = parseFloat(value.toFixed(6))
				arrayLeituras.push(value)
				arrayDate.push(listaDeLeituras[i].data)
			}
			if(listaDeLeituras[i].subgrupo==1&&i>=1){
				subAmpl = listaDeLeituras[i].valor-listaDeLeituras[i-1].valor
				var numAbs = Math.abs(subAmpl)
				numAbs = parseFloat(numAbs.toFixed(6))
				console.log(numAbs)
				somaAmp = somaAmp + numAbs;	
				arrayDateAm.push(listaDeLeituras[i-1].data)
				arrayAmplitude.push(numAbs)
			}
			if(listaDeLeituras[i].subgrupo>1&&i>=1){
				subAmpl = listaDeLeituras[i].valor-listaDeLeituras[i-1].valor
				var numAbs = Math.abs(subAmpl)
				arrayDateAm.push(listaDeLeituras[i-1].data)
				numAbs = parseFloat(numAbs.toFixed(8))
				arrayAmplitude.push(numAbs)
			}
			if(listaDeLeituras[i].subgrupo>1&&lim<listaDeLeituras[i].subgrupo){
				arrayDate.push(listaDeLeituras[i].data)	
				somaSub = somaSub+listaDeLeituras[i].valor
				
			
				if(listaDeLeituras[i].valor>maiorSub){
					maiorSub=listaDeLeituras[i].valor
				}
				if(listaDeLeituras[i].valor<menorSub){
					menorSub=listaDeLeituras[i].valor
				}
				lim=lim+1

				if(lim==listaDeLeituras[i].subgrupo){

					var amplitudeSub = (maiorSub-menorSub)
					amplitudeSub = parseFloat(amplitudeSub.toFixed(6))				
					arraySub.push(amplitudeSub)
					somaSub=somaSub/lim
					arraySub2.push(somaSub)
					lim=0;
					somaSub=0;
					maiorSub=0;
					menorSub=10000;
				}				
			}		
		}
		var totalMediaSub = 0
		var finalMedSub = 0
		var amplSub = 0
		var finalAmplSub = 0
		if(limite>1){
			for(var i=0; i<arraySub2.length;i++) {
				totalMediaSub+=arraySub2[i]
			}
			for(var i=0; i<arraySub.length;i++) {
				amplSub+=arraySub[i]
			}
			finalMedSub = (totalMediaSub/arraySub2.length)
			finalAmplSub = (amplSub/arraySub.length)
		}
//Tabela de valores para subgrupos
		var d4 = 0
		var d3 = 0
		var a2 = 0
		
		if(limite == 2) {
    		d4 = 3.267;
    		d3 = 0;
    		a2 = 1.880;
    	}else if(limite == 3) {
    		d4 = 2.575;
    		d3 = 0;
    		a2 = 1.023;
    	}else if(limite == 4) {
    		d4 = 2.115;
    		d3 = 0;
    		a2 = 0.729;
    	}else if(limite == 5) {
    		d4 = 2.115;
    		d3 = 0;
    		a2 = 0.577;
    	}else if(limite == 6) {
    		d4 = 2.004;
    		d3 = 0;
    		a2 = 0.483;
    	}else if(limite == 7) {
    		d4 = 1.924;
    		d3 = 0.076;
    		a2 = 0.419;
    	}else if(limite == 8) {
    		d4 = 1.864;
    		d3 = 0.136;
    		a2 = 0.373;
    	}else if(limite == 9) {
    		d4 = 1.182;
    		d3 = 0.184;
    		a2 = 0.337;
    	}else if(limite == 10) {
    		d4 = 1.777;
    		d3 = 0.223;
    		a2 = 0.308;
    	}
		
		
		//Individuais
		totalAmAmpl = totalAmAmpl-1
		var mediaAmplitude = somaAmp/totalAmAmpl
		var limSupControl = mediaX+(mediaAmplitude*2.66)
		 limSupControl = parseFloat(limSupControl.toFixed(6))
		var limInfControl = mediaX-(mediaAmplitude*2.66)
		 limInfControl = parseFloat(limInfControl.toFixed(6))
		
		var limSupControleAmpMovel = 3.267*mediaAmplitude;
		 limSupControleAmpMovel = parseFloat(limSupControleAmpMovel.toFixed(6))
		var limInfControleAmpMovel = 0*mediaAmplitude
		 limInfControleAmpMovel = parseFloat(limInfControleAmpMovel.toFixed(6))

		//Subgrupos
		var limSupControleSub = finalMedSub+(finalAmplSub*a2);
		limSupControleSub = parseFloat(limSupControleSub.toFixed(6))
		var limInfControleSub = finalMedSub-(finalAmplSub*a2);
		limInfControleSub = parseFloat(limInfControleSub.toFixed(6))

		var limSupControleAmpMovelSub = (finalAmplSub*d4);
		 limSupControleAmpMovelSub = parseFloat(limSupControleAmpMovelSub.toFixed(6))
		var limInfControleAmpMovelSub = (finalAmplSub*d3);
		 limInfControleAmpMovelSub = parseFloat(limInfControleAmpMovelSub.toFixed(6))
//Criação dos arrays com valores individuais ou subgrupos
		for(var i=0; i<listaDeLeituras.length; i++){
			if(listaDeLeituras[i].subgrupo==1){
				arrayS.push(limSupControl)
				arrayI.push(limInfControl)
				arraySA.push(limSupControleAmpMovel)
				arrayIA.push(limInfControleAmpMovel)
			}
			if(listaDeLeituras[i].subgrupo>1){		
				arrayS.push(limSupControleSub)
				arrayI.push(limInfControleSub)
				var value = listaDeLeituras[i].valor
				value = parseFloat(value.toFixed(6))
				arrayLeituras.push(value)
				arraySA.push(limSupControleAmpMovelSub)
				arrayIA.push(limInfControleAmpMovelSub)			
			}
		}
		 console.log(arrayAmplitude)
		//PLotagem dos gráficos
		var ctx = document.getElementsByClassName("line-chart1");

		var chartGraph = new Chart(ctx, {
			type : 'line',
			data : {
				labels : arrayDate,
				datasets : [ {
					label : "Limite Superior de Controle",
					data : arrayS,
					borderWidth : 2,
					borderColor : 'blue',
					backgroundColor : 'transparent',
				}, {
					label : "Limite Inferior de Controle",
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
//			options: {
//			title:{
//			display:true,
//			fontSize:20,
//			text: "RELATORIO"	
//			},
//			labels:{
//			fontStyle: "bold"
//			}
//			}
		});
		var ctx = document.getElementsByClassName("line-chart2");

		var chartGraph = new Chart(ctx, {
			type : 'line',
			data : {
				labels : arrayDateAm,
				datasets : [ {
					label : "Limite Superior de Controle Amplitude",
					data : arraySA,
					borderWidth : 2,
					borderColor : 'blue',
					backgroundColor : 'transparent',
				}, {
					label : "Limite Inferior de Controle Amplitude",
					data : arrayIA,
					borderWidth : 2,
					borderColor : 'rgba(88, 144,2, 0.85)',
					backgroundColor : 'transparent',
				},{
					label : "Amplitude",
					data : arrayAmplitude,
					borderWidth : 1,
					borderColor : 'red',
					backgroundColor : 'transparent',
				},

				]
			},
//			options: {
//			title:{
//			display:true,
//			fontSize:20,
//			text: "RELATORIO"	
//			},
//			labels:{
//			fontStyle: "bold"
//			}
//			}
		});
	}

})