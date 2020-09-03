

SENAI.producao = new Object();
SENAI.leituras = new Object();
var idPes
var codigoP
$(document).ready(function(){

	SENAI.PATH = "/Senai/rest/"
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
		
		SENAI.producao.carregaMaquina = function(id){

		if(id!=undefined){
			selectM = "#selMaquinaEdicao";
		}else{
			selectM = "#selMaquina";
		}

		$.ajax({
			type:"GET",
			url: SENAI.PATH + "cadMaquina/buscarSelM",
			success:function(maquinas){

				if(maquinas!=""){
					$(selectM).html("");
					var option = document.createElement("option");
					option.setAttribute ("value","");
					option.innerHTML = ("Escolha");
					$(selectM).append(option);

					for (var i=0;i<maquinas.length;i++){
						var option = document.createElement("option");
						option.setAttribute ("value",maquinas[i].id);

						if((id!=undefined)&&(id==maquinas[i].id))
							option.setAttribute ("selected", "selected");

						option.innerHTML = (maquinas[i].nome);
						$(selectM).append(option);

					}
				}else{

					$(selectM).html("");
					var option = document.createElement("option");
					option.setAttribute ("value","");
					$(selectM).append(option);
					$(selectM).addClass("aviso");

				}


			},
			error:function(info){
				SENAI.exibirAviso("Erro ao buscar as maquinas: "+info.status+" - "+info.statusText);
				$(selectM).html(".");
				var option = document.createElement("option");
				option.setAttribute ("value","");
				option.innerHTML = ("Erro ao carregar maquinas!");
				$(selectM).append(option);
				$(selectM).addClass("aviso");
			}
		});
	}
	SENAI.producao.carregaMaquina();
	SENAI.producao.carregaOperacao = function(id){

		if(id!=undefined){
			select = "#selOperacao";
		}else{
			select = "#selOperacao";
		}

		$.ajax({
			type:"GET",
			url: SENAI.PATH + "cadOperacao/buscarSel",
			success:function(operacoes){

				if(operacoes!=""){
					$(select).html("");
					var option = document.createElement("option");
					option.setAttribute ("value","");
					option.innerHTML = ("Escolha");
					$(select).append(option);

					for (var i=0;i<operacoes.length;i++){
						var option = document.createElement("option");
						option.setAttribute ("value",operacoes[i].id);

						if((id!=undefined)&&(id==operacoes[i].id))
							option.setAttribute ("selected", "selected");

						option.innerHTML = (operacoes[i].nome);
						$(select).append(option);

					}
				}else{

					$(select).html("");
					var option = document.createElement("option");
					option.setAttribute ("value","");
					$(select).append(option);
					$(select).addClass("aviso");

				}


			},
			error:function(info){
				SENAI.exibirAviso("Erro ao buscar as operacoes: "+info.status+" - "+info.statusText);
				$(select).html("");
				var option = document.createElement("option");
				option.setAttribute ("value","");
				option.innerHTML = ("Erro ao carregar operacoes!");
				$(select).append(option);
				$(select).addClass("aviso");
			}
		});
	}
	SENAI.producao.carregaOperacao();
	
	SENAI.producao.oP = function(){
		var numAl = Math.floor(Math.random() * 10000);
		document.getElementById('ordemP').value = numAl;
	}
	SENAI.producao.oP();

	SENAI.producao.cadastrar = function(){
		
		cliente = document.frmLimitesProd.cliente.value;
		dataInicio = document.frmLimitesProd.dataI.value;
		dataFinal = document.frmLimitesProd.dataF.value;
		descricao = document.frmLimitesProd.descricao.value;
		espMin = document.frmLimitesProd.espMin.value;
		espMin = document.frmLimitesProd.espMax.value;
		numAmostras = document.frmLimitesProd.qtdAmostras.value;
		subgrupo = document.frmLimitesProd.subgrupo.value;
		operacaoId = document.frmLimitesProd.operacaoId.value;
		maquinaId = document.frmLimitesProd.maquinaId.value;

		if(cliente==""||dataInicio==""||dataFinal==""||descricao==""||espMin==""||espMin==""||numAmostras==""||subgrupo==""||operacaoId==""||maquinaId==""){
			Swal.fire({
				icon: 'error',
				title: 'Oops...',
				text: 'Preencha todos os campos.'
			})
		}else{


			var producao = new Object();
			producao.codigo = document.frmLimitesProd.ordemP.value;
			producao.cliente = document.frmLimitesProd.cliente.value;
			producao.dataInicio = document.frmLimitesProd.dataI.value;
			producao.dataFinal = document.frmLimitesProd.dataF.value;
			producao.descricao = document.frmLimitesProd.descricao.value;
			producao.espMin = document.frmLimitesProd.espMin.value;
			producao.espMax = document.frmLimitesProd.espMax.value;
			producao.numAmostras = document.frmLimitesProd.qtdAmostras.value;
			producao.subgrupo = document.frmLimitesProd.subgrupo.value;
			producao.operacaoId = document.frmLimitesProd.operacaoId.value;
			producao.maquinaId = document.frmLimitesProd.maquinaId.value;

			$.ajax({
				type: "POST",
				url: SENAI.PATH + "producao/inserir",
				data:JSON.stringify(producao),
				success:function(msg){
					$("#addCadProducao").trigger("reset");
					var b = msg.replace(/['"]+/g, '');
					Swal.fire(b);
					SENAI.producao.buscar();
				},
				error:function(info){
					var erro = "Erro ao cadastrar uma nova maquina: "+ info.status + " - "+ info.statusText;
					var b = erro.replace(/['"]+/g, '');
					Swal.fire(b);	
				}
			});	
		}
	}
	
	SENAI.producao.buscar = function(id, somaQtd){
		var valorBusca = $("#campoBusca").val();
		$.ajax({
			type: "GET",
			url: SENAI.PATH + "producao/buscar",
			data: "valorBusca="+valorBusca,
			success: function(dados){
				SENAI.producao.oP();
				dados = JSON.parse(dados);

				$("#listaProducoes").html(SENAI.producao.exibir(dados,id, somaQtd));


			},
			error: function(info){
				var a="Erro ao consultar os cadastros de producao: "+info.status+" - "+info.statusText;
				var b = a.replace(/'/g, '');
				Swal.fire(b);
			}
		});
		SENAI.producao.exibir = function(listaDeProducoes,id, somaQtd){
			var tabela = 
				"<table>"+
				"<tr>"+	
				"<th> Ordem de produção</th>"+
				"<th> Cliente</th>"+
				"<th> Data inícial</th>"+
				"<th> Data final</th>"+
				"<th> Descrição da peça</th>"+
				"<th> Especificação Mínima</th>"+
				"<th> Especificação Máxima</th>"+
				"<th> Número de amostras</th>"+
				"<th> Máquina</th>"+
				"<th> Operação</th>"+
				"<th> Subgrupo</th>"+
				"<th class='acoes'>Deletar</th>"+
				"<th class='acoes'>Escolher</th>"+
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
					"<a onclick=\"SENAI.producao.excluir('"+listaDeProducoes[i].id+"')\"><img src='css/img/delete.png' alt='Apagar'></a>" +
					"</td>"+
					"<td>"+
					"<a onclick=\"SENAI.producao.buscarCodigo('"+listaDeProducoes[i].id+"')\"><i class='fas fa-mouse-pointer'></i></a>" +
					"</td>"+
					"</tr>";
				}

			}else if (listaDeProducoes == ""){
				tabela += "<tr><td colspan='6'>Nenhum registro encontrado</td></tr>";
			}
			tabela +="</table>";
			
			return tabela;

			$("#listaProducoes").html(tabela);
		}
	}

	SENAI.producao.buscar();

	SENAI.producao.buscarCodigo = function(id){

		$.ajax({
			type: "GET",
			url: SENAI.PATH + "producao/buscarCodigo",
			data: "id="+id,
			success: function(producao){
				 idPes=producao.id
				var codigoP=producao.codigo
				var quant=producao.numAmostras
				document.getElementById("selProducao").innerHTML = producao.codigo;
				 SENAI.leituras.buscarCount(producao)
			},
			error: function(info){
				var erro = "Erro ao consultar os cadastros de producao: "+info.status+" - "+info.statusText;
				var b = erro.replace(/['"]+/g, '');
				Swal.fire(b);	
			}
		});
	}
	SENAI.leituras.buscarCount = function(producao){
		$.ajax({
			type: "GET",
			url: SENAI.PATH + "leituras/buscarCod",
			data: "id="+producao.id,
			success: function(leituras){
				var Qtd = leituras.count;
				var quant = producao.numAmostras*producao.subgrupo;
				
				
				if(quant>(Qtd)){
					openButton(event, 'insLei')
				}else{
					var msgJS = "Não há mais amostras para cadastrar";
					var b = msgJS.replace(/['"]+/g, '');
					Swal.fire(b)
				}
			},
			error: function(info){
				var erro ="Erro ao consultar os cadastros de leitura: "+info.status+" - "+info.statusText;
				var b = erro.replace(/['"]+/g, '');
				Swal.fire(b);
			}
		});
	}
	
	
	
	
	
	
	SENAI.leituras.buscarCod = function(){
		var id = idPes
		$.ajax({
			type: "GET",
			url: SENAI.PATH + "leituras/buscarCod",
			data: "id="+id,
			success: function(leituras){
				
				var Qtd = leituras.count;
				if(Qtd==0){
					Qtd = Qtd+1;
				}
				
				
				SENAI.leituras.cadastrar(Qtd, id);
			},
			error: function(info){
				var erro ="Erro ao consultar os cadastros de leitura: "+info.status+" - "+info.statusText;
				var b = erro.replace(/['"]+/g, '');
				Swal.fire(b);
			}
		});
	}
	SENAI.leituras.cadastrar = function(Qtd, id){		
		operadorId = document.frmLimitesLeit.operadorId.value;
		valor = document.frmLimitesLeit.valor.value;
		data = document.frmLimitesLeit.data.value;
		obs = document.frmLimitesLeit.obs.value;

		if(operadorId==""||valor==""||data==""){
			Swal.fire({
				icon: 'error',
				title: 'Oops...',
				text: 'Preencha todos os campos.'
			})
		}else{


			var leituras = new Object();
			leituras.operadorId = document.frmLimitesLeit.operadorId.value;
			leituras.producaoId = id;
			leituras.valor = document.frmLimitesLeit.valor.value;
			//leituras.lmQtd = Qtd;
			leituras.data = document.frmLimitesLeit.data.value;
			leituras.obs = document.frmLimitesLeit.obs.value;
			
			$.ajax({
				type: "POST",
				url: SENAI.PATH + "leituras/inserir",
				data:JSON.stringify(leituras),
				success:function(msg){
					$("#addCadLeituras").trigger("reset");
					var b = msg.replace(/['"]+/g, '');
					Swal.fire(b);
					openButton(event, 'verH')
					SENAI.leituras.buscar();
				},
				error:function(info){
					
					var erro= "Erro ao cadastrar uma nova leitura: "+ info.status + " - "+ info.statusText;
					var b = erro.replace(/['"]+/g, '');
					Swal.fire(b);
				}
			});	
		}
	}

	
	SENAI.producao.excluir = function(id){
		$.ajax({
			type:"DELETE",
			url: SENAI.PATH +"producao/excluir/"+id,
			success: function(msg){
				
				var b = msg.replace(/['"]+/g, '');
				Swal.fire(b);
				SENAI.producao.buscar();
			},
			error: function(info){
				var erro="Erro ao excluir producao: " + info.status + " - " + info.statusText;
				var b = erro.replace(/['"]+/g, '');
				Swal.fire(b);
			}
		});
	};
})