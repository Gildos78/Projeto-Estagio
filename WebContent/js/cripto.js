function validaCampos(){

var pass = document.formul.pass.value;


// Convertendo para Base64
	
var emBase64 = btoa(pass);
document.formul.password.value=emBase64


}
