/*$(document).ready(function() {
	//userlist();

});*/

function signin() {
	console.log("login");
	var userName = $("#name").val();
	var Password = $("#password").val();
 
	var loginObject = new Object();
	loginObject.Username = userName;
	loginObject.Password = Password;
	$('#loading').html("<div style='width:150px;margin:0 auto' ><img src='../resources/images/loader_green.gif' width='100%' /></div>");
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : 'validlogin',
		data : JSON.stringify(loginObject),
		success : function(data, textStatus, jqXHR) {
			console.log(data);
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			$('#loading').html("").hide();
			
			if(responseValue == 0)
				{
				$("#password").val('')
				alert("Invalid Credentials");
				window.location.href="login";
				}
			else
				{
				var userid = responseValue[0].userid;
				if(responseValue[0].role == 1){
					alert("User not permitted to login here")
					window.location.href="login";
				}
				else{
				window.location.href="inventoryreports";
				}
				}
		}
	});
}
