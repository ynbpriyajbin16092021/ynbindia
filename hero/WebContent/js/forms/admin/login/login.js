/*$(document).ready(function() {
	//userlist();

});*/
var flow = 0;


function signin() {
	//console.log("login");
	var userName = $("#name").val();
	var Password = $("#password").val();

	if(userName != null && Password != null && userName != "" &&  Password != ""){
	
	var loginObject = new Object();
	loginObject.Username = userName;
	loginObject.Password = Password;
	loginObject.Applntype = 1;
	$(".changeTitle").text("Please Wait your Request will be processing");
	$(".changeContent").html("<img src='../resources/images/loader_green.gif' width='100%' />");

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
				var moduleUrl = responseValue['moduleUrl'];
				localStorage.setItem("tokenkey",tokenkey);
				
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
					
					window.location.href = '../'+moduleUrl+tokenkey;
					//window.open('../'+moduleUrl+tokenkey , '' , 'width=1024, height= 678, toolbar = no, directories= no, status = yes, scrollbars = yes, resizable = yes, left = 0, top = 0');
					//window.open('','_self','');
					//window.close();
				}
			}else{
				$("#password").val('')
				$(".changeTitle").text("Info");
				$(".changeContent").html("Invalid Credentials");
				setTimeout("location.href = 'login'",2000);
			}
		}
	});
	
	}else{
		$(".changeTitle").text("Info");
		$(".changeContent").html("Please Enter Username and Password");
		$("#modalLoading").modal("show");
		//alert("please enter username and password")
	}
}

function loadherosettings(modulename)
{
	var urllist = [],urllistlength = 0;
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : 'herosettingsurllist/'+modulename,
		beforeSend: function() 
		
		{
$("#herosettingsdiv").html('<div class="loader"><img src="/heroadmin/resources/images/logos/loader.gif" alt="HERO" class="responsive p" ></div>');
         },
		success : function(data, textStatus, jqXHR) {
			urllist = (data.inventoryResponse.responseObj);
			urllistlength = parseInt(urllist.length) - 1;
			console.log(urllist);
			url = (urllist[flow].url);
	console.log("flow   "+flow+"   url   "+url+"   length   "+urllistlength);
	
	$.get(url, function(data) {
		$("#herosettingsdiv").html(data);
		$(".datepicker").datepicker(datePickerOptions);
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
	
	window.location.href="herohome";
}
function saveforgotpassword()
{
	
	var loginObject = new Object();
	loginObject.Username = $("#forgotusername").val();
	//console.log(loginObject);
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : 'registerforgotpassword',
		data : JSON.stringify(loginObject),
		success : function(data, textStatus, jqXHR) {
			console.log(data)
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			console.log(responseValue)
			console.log(responseType)
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

function loadsettinguserrole(url){
	$.get(url, function(data) {
		$("#herosettingsdiv").html(data);
		$(".datepicker").datepicker(datePickerOptions);
	});	
}

function checkOrgnAvailabe(){
	$.ajax({
		type:'POST',
		contentType:'application/json',
		url:'loadorganization',
		success:function(data,textStatus,jqXHR)
		{
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			if(responseValue.length>0){
				displayAlertMsg("Organization already Available. Please try to login and Update your Details");
				setTimeout("location.href = 'login'",2000);
			}else{
				window.location.href = "organization?disp=none";
			}
			
		}
	});	
}
