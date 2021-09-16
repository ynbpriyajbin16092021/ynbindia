/*$(document).ready(function() {
	//userlist();

});*/
var flow = 0;
function signin() {
	console.log("login");
	var userName = $("#name").val();
	var Password = $("#password").val();
	if(userName != null && Password != null && userName != "" &&  Password != ""){
	var loginObject = new Object();
	loginObject.Username = userName;
	loginObject.Password = Password;
	loginObject.Applntype = 2;
	$(".changeTitle").text("Please Wait your Request will be processing");
	$(".changeContent").html("<img src='../../heroadmin/resources/images/loader_green.gif' width='100%' />");
	$("#beforeSendLoader").click();
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : 'validlogin',
		data : JSON.stringify(loginObject),
		success : function(data, textStatus, jqXHR) {
			console.log(data);
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			
			if(responseType == "S"){
				var tokenkey = responseValue['tokenkey'];	
				if(tokenkey != null){
				localStorage.setItem("tokenkey",tokenkey);
				console.log(tokenkey);
				var userdetail = responseValue['userDetails'];
				if(userdetail.length == 0)
				{
				$("#password").val('')
				$(".changeTitle").text("Info");
				$(".changeContent").html("Invalid Credentials");
				setTimeout("location.href = 'login'",2000);
				}	
				else
				{
				var userid = userdetail[0].userid;
				window.location.href="dashboard";
				}
				}else{
					var userid = responseValue[0].userid;
					window.location.href="dashboard";
				}
			}else{
				$("#password").val('')
				$(".changeTitle").text("Info");
				$(".changeContent").html("Invalid Credentials");
				setTimeout("location.href = 'login'",2000);
			}
		}
	});
}
	else
	{
		$(".changeTitle").text("Info");
		$(".changeContent").html("Please Enter Username and Password");
		$("#modalLoading").modal("show");
		//alert("please enter username and password")
	}
}
	

	


function loadherosettings(modulename)
{
	var url = "",urllist = [],urllistlength = 0;
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : 'herosettingsurllist/'+modulename,
		beforeSend: function() 
		{
			$("#herosettingsdiv").html('<div class="loader"><img src="../../heroadmin/resources/images/loader_green.gif" alt="HERO" class="responsive p" ></div>');
         },
		success : function(data, textStatus, jqXHR) {
			urllist = (data.inventoryResponse.responseObj);
			urllistlength = parseInt(urllist.length) - 1;
			console.log(urllist);
			url = (urllist[flow].url);
			console.log("flow   "+flow+"   url   "+url+"   length   "+urllistlength);
			
			$.get(url, function(data) {
				$('#herosettingsdiv').html(data);
			});	
			
			if(parseInt(flow) == 0)
			{
			$('#prevbtn').hide();
			$('#nextbtn').show();
			$('#finishbtn').hide();
			}
			else if(parseInt(flow) > 0)
			{
			$('#prevbtn').show();
			$('#nextbtn').show();
			$('#finishbtn').hide();
			}
		
			if(parseInt(flow) == urllistlength)
			{
			$('#nextbtn').hide();
			$('#finishbtn').show();
			}

		}
	});

}

function confignext(modulename)
{
	flow = parseInt(flow)+1;
	loadherosettings(modulename);
}
function configprev(modulename)
{
	if(parseInt(flow) > 0)
	flow = parseInt(flow)-1;
	loadherosettings(modulename);
}
function configfinish(modulename)
{
	
	window.location.href="dashboard";
}
function saveforgotpassword()
{
	
	var loginObject = new Object();
	loginObject.Username = $("#forgotusername").val();
	console.log(loginObject);
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : 'registerforgotpassword',
		data : JSON.stringify(loginObject),
		success : function(data, textStatus, jqXHR) {
			
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			if(responseType == 'S')
				{
				$('#myModal').modal('hide');
				displayAlertMsg(responseValue)
				setTimeout("location.href = 'login'",2000);
				}
			else
				{
				$('#myModal').modal('hide');
				displayAlertMsg(responseValue)
				setTimeout("location.href = 'login'",2000);
				}
			}
		 
	});
}