SENAI.colaborador = new Object();

$(document).ready (function(){

	SENAI.PATH = "/Senai/rest/";

	validaCampos = function(){

		var nome = document.frmAddColaborador.nome.value;
		var expRegNome = new RegExp(/[A-zÀ-ü]{3,}([ ]{1}[A-zÀ-ü]{2,})|([A-zÀ-ü]{3,})+$/);
		if (!expRegNome.test(nome)){
			Swal.fire('Preencha o campo Nome corretamente.')
			document.frmAddColaborador.nome.focus();
			return false;
		}


		var mat = document.frmAddColaborador.matricula.value;
		var expRegMat = new RegExp("^[0-9]{6}$");

		if (!expRegMat.test(mat)){
			Swal.fire("Preencha o campo Matricula corretamente.");
			document.frmAddColaborador.matricula.focus();
			return false;
		}	

		var fone = document.frmAddColaborador.foneCol.value;
		var expRegFone = new RegExp("^[(]{1}[1-9]{2}[)]{1}[0-9]{4,5}[-]{1}[0-9]{4}$");

		if (!expRegFone.test(fone)){
			Swal.fire("Preencha o campo Telefone corretamente.");
			document.frmAddColaborador.foneCol.focus();
			return false;
		}	



		var senha = document.frmAddColaborador.senhaCol.value;
		var senhaRep = document.frmAddColaborador.senhaRepCol.value;
		var expRegSenha = new RegExp(/^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,}$/);

		if(senhaRep==""||senha==""){
			Swal.fire({
				icon: 'error',
				title: 'Oops...',
				text: 'O campo da senha esta vazio.'
			})
		}else{
			if (!expRegSenha.test(senha)){
				Swal.fire('A senha deve ter letras e números!')
				document.frmAddColaborador.senhaCol.focus();
			}else{
				if (senhaRep==senha&&!senhaRep==""){
					SENAI.colaborador.cadastrar();
					$("#addColaborador").trigger("reset");
				}else{
					Swal.fire({
						icon: 'error',
						title: 'Oops...',
						text: 'A senha tem que ser igual.'
					})

					document.frmAddColaborador.senhaRepCol.focus();
				}
			}

		}
	}

	SENAI.colaborador.buscar = function(){
		var valorBusca = $("#campoBusca").val();
		$.ajax({
			type: "GET",
			url: SENAI.PATH + "colaborador/buscar",
			data: "valorBusca="+valorBusca,
			success: function(dados){

				dados = JSON.parse(dados);

				$("#listaColaboradores").html(SENAI.colaborador.exibirCol(dados));


			},
			error: function(info){
				SENAI.exibirAviso("Erro ao consultar os colabordores: "+info.status+" - "+info.statusText);
			}
		});

		SENAI.colaborador.exibirCol = function(listaDeColaboradores){
			var tabela = 
				"<table>"+
				"<tr>"+	
				"<th> Nome</th>"+
				"<th> Matricula</th>"+
				"<th> Telefone</th>"+
				"<th class='acoes'>Editar</th>"+
				"<th class='acoes'>Nova Senha</th>"+
				"</tr>";

			if(listaDeColaboradores != undefined && listaDeColaboradores.length >0){


				for(var i=0; i<listaDeColaboradores.length; i++){

					tabela+="<tr>"+	
					"<td>"+listaDeColaboradores[i].nome+"</td>"+
					"<td>"+listaDeColaboradores[i].matricula+"</td>"+
					"<td>"+listaDeColaboradores[i].fone+"</td>"+
					"<td>"+
					"<a onclick=\"SENAI.colaborador.exibirEdicao('"+listaDeColaboradores[i].id+"')\"><img src='css/img/edit.png' alt='Editar'></a>" +
					"<a onclick=\"SENAI.colaborador.excluir('"+listaDeColaboradores[i].id+"')\"><img src='css/img/delete.png' alt='Apagar'></a>" +
					"</td>"+
					"<td><a onclick=\"SENAI.colaborador.exibirEdicaoSenha('"+listaDeColaboradores[i].id+"')\"><img src='css/img/edit.png' alt='Editar'></a></td>"+
					"</tr>";
				}

			}else if (listaDeColaboradores == ""){
				tabela += "<tr><td colspan='6'>Nenhum registro encontrado</td></tr>";
			}
			tabela +="</table>";

			return tabela;
			"</table>";

			$("#listaColaboradores").html(tabela);
		}
	}
	SENAI.colaborador.buscar();

	SENAI.colaborador.cadastrar = function(){

		var passCad = document.frmAddColaborador.senhaCol.value;
		var emBase64Cad = btoa(passCad);
		var colaborador = new Object();
		colaborador.nome = document.frmAddColaborador.nome.value;
		colaborador.matricula = document.frmAddColaborador.matricula.value;
		colaborador.senha = emBase64Cad;
		colaborador.fone = document.frmAddColaborador.foneCol.value;


		$.ajax({
			type: "POST",
			url: SENAI.PATH + "colaborador/inserir",
			data:JSON.stringify(colaborador),
			success:function(msg){
				Swal.fire(msg);
				SENAI.colaborador.buscar();
			},
			error:function(info){
				Swal.fire({
					icon: 'error',
					title: 'Oops...',
					text: 'Nao foi cadastrado.'
				})	
			}
		});	
	}

	SENAI.colaborador.exibirEdicao = function(id){
		$.ajax({
			type:"GET",
			url: SENAI.PATH +"colaborador/checkId",
			data: "id="+id,
			success: function(colaborador){
				document.frmEditaColaborador.idColaborador.value=colaborador.id;			
				document.frmEditaColaborador.nome.value = colaborador.nome;
				document.frmEditaColaborador.matricula.value = colaborador.matricula;
				document.frmEditaColaborador.foneCol.value = colaborador.fone;


				var modalEditaColaborador = {
						title: "Editar Dados",
						height: 300,
						width: 600,
						modal: true,
						buttons:{
							"Salvar":function(){
								var nome = document.frmEditaColaborador.nome.value;
								var expRegNome = new RegExp(/[A-zÀ-ü]{3,}([ ]{1}[A-zÀ-ü]{2,})|([A-zÀ-ü]{3,})+$/);
								if (!expRegNome.test(nome)){
									Swal.fire('Preencha o campo Nome com letras maiusculas e/ou minusculas.')
									document.frmEditaColaborador.nome.focus();
									return false;
								}

								var mat = document.frmEditaColaborador.matricula.value;
								var expRegMat = new RegExp("^[0-9]{6}$");

								if (!expRegMat.test(mat)){
									Swal.fire("Preencha o campo Matricula com 6 digitos.");
									document.frmEditaColaborador.matricula.focus();
									return false;
								}	


								var fone = document.frmEditaColaborador.foneCol.value;
								var expRegFone = new RegExp("^[(]{1}[1-9]{2}[)]{1}[0-9]{4,5}[-]{1}[0-9]{4}$");

								if (!expRegFone.test(fone)){
									Swal.fire("Preencha o campo Telefone. Ex:(00)00000-0000");
									document.frmEditaColaborador.foneCol.focus();
									return false;
								}	

								SENAI.colaborador.editar();							
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
				$("#modalEditaColaborador").dialog(modalEditaColaborador);

			},
			error: function(info){
				SENAI.exibirAviso("Erro ao buscar cadastro para edição: "+info.status+" - "+info.statusText);
			}

		});
	}

	SENAI.colaborador.editar = function(){


		var colaborador = new Object();
		colaborador.id = document.frmEditaColaborador.idColaborador.value;
		colaborador.nome=document.frmEditaColaborador.nome.value;
		colaborador.matricula=document.frmEditaColaborador.matricula.value;
		colaborador.fone=document.frmEditaColaborador.foneCol.value;

		$.ajax({
			type:"PUT",
			url: SENAI.PATH + "colaborador/alterar",
			data:JSON.stringify(colaborador),
			success: function(msg){
				Swal.fire(msg);
				SENAI.colaborador.buscar();
				$("#modalEditaColaborador").dialog("close");
			},
			error: function(info){
				Swal.fire("Erro ao editar cadastro: "+ info.status+" - "+info.statusText);
			}
		});
	};

	SENAI.colaborador.excluir = function(id){
		$.ajax({
			type:"DELETE",
			url: SENAI.PATH +"colaborador/excluir/"+id,
			success: function(msg){
				Swal.fire(msg);
				SENAI.colaborador.buscar();
			},
			error: function(info){
				Swal.fire("Erro ao excluir produto: " + info.status + " - " + info.statusText);
			}
		});
	};
	SENAI.colaborador.exibirEdicaoSenha = function(id){	
		$.ajax({
			type:"GET",
			url: SENAI.PATH +"colaborador/checkIdSenha",
			data: "id="+id,
			success: function(colaborador){
				document.frmEditaColaboradorSenha.idColaboradorSenha.value=colaborador.id;
				document.frmEditaColaboradorSenha.senhaNova.value="";
				document.frmEditaColaboradorSenha.senhaRep.value="";

				var modalEditaColaboradorSenha = {
						title: "Trocar Senha",
						height: 300,
						width: 600,
						modal: true,
						buttons:{
							"Salvar":function(){
								var Senha = document.frmEditaColaboradorSenha.senhaNova.value;
								var expRegSenha = new RegExp(/^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,}$/);	

								if (!expRegSenha.test(Senha)){
									Swal.fire("Preencha o campo Senha com letras e numeros e minimo 6 caracteres.");
									document.frmEditaColaboradorSenha.senhaNova.focus();
									return false;
								}	

								var SenhaRep = document.frmEditaColaboradorSenha.senhaRep.value;

								if (Senha!=SenhaRep){
									Swal.fire("A Senha tem que ser igual.");
									document.frmEditaColaboradorSenha.senhaRep.focus();
									return false;
								}	


								SENAI.colaborador.editarSenha();							
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
				$("#modalEditaColaboradorSenha").dialog(modalEditaColaboradorSenha);

			},
			error: function(info){
				SENAI.exibirAviso("Erro ao buscar cadastro para edição: "+info.status+" - "+info.statusText);
			}

		});
	}
	SENAI.colaborador.editarSenha = function(){

		var passCad = document.frmEditaColaboradorSenha.senhaNova.value;
		var emBase64Cad = btoa(passCad);

		var colaborador = new Object();
		colaborador.id = document.frmEditaColaboradorSenha.idColaboradorSenha.value;
		colaborador.senha=emBase64Cad;


		$.ajax({
			type:"PUT",
			url: SENAI.PATH + "colaborador/alterarSenha",
			data:JSON.stringify(colaborador),
			success: function(msg){
				Swal.fire(msg);
				SENAI.colaborador.buscar();
				$("#modalEditaColaboradorSenha").dialog("close");
			},
			error: function(info){
				Swal.fire("Erro ao trocar senha: "+ info.status+" - "+info.statusText);
			}
		});
	};
});