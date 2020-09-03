SENAI.gerente = new Object();
SENAI.funcionario = new Object();

$(document).ready (function(){

	SENAI.PATH = "/Senai/rest/";
	
	SENAI.gerente.checkId = function(){
		
		$.ajax({
			type: "GET",
			url: SENAI.PATH + "gerente/checkId",
			data: "id="+1,
			success: function(gerente){	
					SENAI.funcionario.id(gerente);					
			},
			error: function(info){
				
			}
		})
	}
	
	
	SENAI.funcionario.id = function(gerente){	
		$.ajax({
			type: "GET",
			url: SENAI.PATH + "funcionario/checkMatricula",
			data: "matricula="+gerente.matricula,
			success: function(funcionario){	
					SENAI.funcionario.exibir(funcionario);
			},
			error: function(info){
				
			}
		})
	}
				
	$("#listaFuncionarios").html(SENAI.gerente.checkId());		
 SENAI.funcionario.exibir = function(funcionario){
		var tabela = 
		"<table>"+
		"<tr>"+	
		"<th> Nome</th>"+
		"<th> Email</th>"+
		"<th class='acoes'>Editar</th>"+
		"<th class='acoes'>Nova Senha</th>"+
		"</tr>"+
		"<tr>"+	
		"<td>"+funcionario.nome+"</td>"+
		"<td>"+funcionario.email+"</td>"+
		"<td>"+
		"<a onclick=\"SENAI.funcionario.exibirEdicao('"+funcionario.id+"')\"><img src='css/img/edit.png' alt='Editar'></a>" +
		"<td><a onclick=\"SENAI.funcionario.exibirEdicaoSenha('"+funcionario.id+"')\"><img src='css/img/edit.png' alt='Editar'></a></td>"+
		"</td>"+
		"</tr>"+
		"</table>";

		$("#listaFuncionarios").html(tabela);
	}
	SENAI.funcionario.exibirEdicao = function(id){
		$.ajax({
			type:"GET",
			url: SENAI.PATH +"funcionario/checkMatricula",
			data: "matricula="+id,
			success: function(funcionario){
								
					document.frmEditaFuncionario.idFuncionario.value = funcionario.id;
					document.frmEditaFuncionario.nome.value = funcionario.nome;
					document.frmEditaFuncionario.email.value = funcionario.email;
	
					
					var modalEditaFuncionario = {
							title: "Editar Dados",
							height: 300,
							width: 600,
							modal: true,
							buttons:{
								"Salvar":function(){								
									SENAI.funcionario.editar();							
								},
								"Cancelar": function(){
									$(this).dialog("close");
								}
							},
							close:function(){
							}
					};
					$("#modalEditaFuncionario").dialog(modalEditaFuncionario);
		
			},
			error: function(info){
				SENAI.exibirAviso("Erro ao buscar cadastro para edição: "+info.status+" - "+info.statusText);
			}

		});
	}
	
	SENAI.funcionario.editar = function(){
		 
		var funcionario = new Object();
		funcionario.id = document.frmEditaFuncionario.idFuncionario.value;
		funcionario.nome = document.frmEditaFuncionario.nome.value;
		funcionario.email = document.frmEditaFuncionario.email.value;
		
		$.ajax({
			type:"PUT",
			url: SENAI.PATH + "funcionario/alterar",
			data:JSON.stringify(funcionario),
			success: function(msg){
				Swal.fire(msg, "\n Para efetivar a alteração no cadastro, faça o logout e o login novamente!");
				SENAI.gerente.checkId();
				$("#modalEditaFuncionario").dialog("close");
			},
			error: function(info){
				Swal.fire("Erro ao editar cadastro: "+ info.status+" - "+info.statusText);
			}
		});
	};
	SENAI.funcionario.exibirEdicaoSenha = function(matricula){
		$.ajax({
			type:"GET",
			url: SENAI.PATH +"funcionario/checkMatriculaSenha",
			data: "matricula="+matricula,
			success: function(funcionario){
					document.frmEditaFuncionarioSenha.idFuncionarioSenha.value = funcionario.id;
					document.frmEditaFuncionarioSenha.senhaNova.value = "";
					document.frmEditaFuncionarioSenha.senhaRep.value = "";
	
					
					var modalEditaFuncionarioSenha = {
							title: "Trocar Senha",
							height: 300,
							width: 600,
							modal: true,
							buttons:{
								"Salvar":function(){
									var Senha = document.frmEditaFuncionarioSenha.senhaNova.value;
									var expRegSenha = new RegExp(/^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,}$/);	
									
									if (!expRegSenha.test(Senha)){
										Swal.fire("Preencha o campo Senha com letras e numeros e minimo 6 caracteres.");
										document.frmEditaFuncionarioSenha.senhaNova.focus();
										return false;
									}	
										
									var SenhaRep = document.frmEditaFuncionarioSenha.senhaRep.value;
										
										if (Senha!=SenhaRep){
											Swal.fire("A Senha tem que ser igual.");
											document.frmEditaFuncionarioSenha.senhaRep.focus();
											return false;
										}	
									SENAI.funcionario.editarSenha();							
								},
								"Cancelar": function(){
									$(this).dialog("close");
								}
							},
							close:function(){
							}
					};
					$("#modalEditaFuncionarioSenha").dialog(modalEditaFuncionarioSenha);
		
			},
			error: function(info){
				SENAI.exibirAviso("Erro ao buscar cadastro para edição: "+info.status+" - "+info.statusText);
			}

		});
	}
	
	SENAI.funcionario.editarSenha = function(){
		var passCad = document.frmEditaFuncionarioSenha.senhaNova.value;
		var emBase64Cad = btoa(passCad);
		var funcionario = new Object();
		funcionario.id = document.frmEditaFuncionarioSenha.idFuncionarioSenha.value;
		funcionario.senha = emBase64Cad;
		
		
		$.ajax({
			type:"PUT",
			url: SENAI.PATH + "funcionario/alterarSenha",
			data:JSON.stringify(funcionario),
			success: function(msg){
				Swal.fire(msg);
				SENAI.gerente.checkId();
				$("#modalEditaFuncionarioSenha").dialog("close");
			},
			error: function(info){
				Swal.fire("Erro ao editar cadastro: "+ info.status+" - "+info.statusText);
			}
		});
	};
	
	
});