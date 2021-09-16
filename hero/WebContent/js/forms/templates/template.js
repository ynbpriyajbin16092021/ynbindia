function loadorganization()
{
$.ajax({
	type:'POST',
	contentType:'application/json',
	url:'loadorganization',
	success:function(data,textStatus,jqXHR)
	{
		console.log(data)
		var responseType = getResponseType(data);
		var responseValue = getResponseValue(data);
		if(responseValue.length>0){
		var orgnObj = (responseValue[0]);
		$('#compnametext').val(orgnObj.orgnname);
		$('#mobnotext').val(orgnObj.orgnmobno);
		$('#emailtext').val(orgnObj.orgnemail);
		$('#orgnaddresstext').val(orgnObj.orgnaddress);
		$('#startyear').val(orgnObj.orgnstartyear);
		$('#endyear').val(orgnObj.orgnendyear);
		$('#startdate').val(orgnObj.orgndate);
		$('#oprntext').val("UPD");
		}else{
			$('#oprntext').val("INS");
			
		}
		
	}
});	
}

function saveorgn()
{
	
	var isValidArray = [];
	isValidArray.push(validateonbuttonclick('#compnametext','input'));
	isValidArray.push(validateonbuttonclick('#mobnotext','input'));
	isValidArray.push(validateonbuttonclick('#emailtext','input'));
	isValidArray.push(validateonbuttonclick('#orgnaddresstext','input'));
	
	var totalValid = jQuery.inArray( false, isValidArray );

	if(totalValid < 0)
		{
		console.log(length)
		var addresslength = $("#orgnaddresstext").val();
		if(addresslength.length<512)
		{
var orgnObj = new Object();
orgnObj.transfer = $("input[name=stocktransfertext]:checked").val();
orgnObj.orgnname = $('#compnametext').val();
orgnObj.startyear = $('#startyear').val();
orgnObj.endyear = $('#endyear').val();
orgnObj.startdate = $('#startdate').val();
orgnObj.mobno = $('#mobnotext').val();
orgnObj.email = $('#emailtext').val();
orgnObj.orgnlogo = $('#logotext').val();
orgnObj.orgnaddress = $('#orgnaddresstext').val();
orgnObj.oprn = $('#oprntext').val();
orgnObj.userid = $('#inventoryuseridtext').val();
console.log(orgnObj);

var regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;

if(regex.test(orgnObj.email)){

var html = $("#imgpathtext").contents().find("body").html();
var imgvalid = true;
if(orgnObj.oprn != "UPD"){
if(html != "File Successfully Uploaded"){
	imgvalid = false;
}
}
if(imgvalid){

$.ajax({
	type:'POST',
	contentType:'application/json',
	url:'saveorganization',
	data:JSON.stringify(orgnObj),
	success:function(data,textStatus,jqXHR)
	{ 
		//alert("#logotext");
		console.log(data);
		
		orgnObj = Object.keys(orgnObj).length;
		
		//displayAlertMsg("saved successfully");
		var responseType = getResponseType(data);
		var responseValue = getResponseValue(data);
		/*displaySuccessMsg(data);*/
		/*var str = window.location.pathname;
		var res = str.split("/");*/
		
		/*if(res[3] == "organization"){	
			//window.location.href="login";
		}
		else{
			setTimeout("location.href = 'currency'",900);
			setTimeout("location.href = 'redirect_currency.html'",60000000000000000000);
		}*/
		if(responseType == 'S')
			{
			//displaySuccessMsg(data);
			//setTimeout("location.href = 'login'",4000);
			//alert(responseValue)
			var str = window.location.pathname;
			var res = str.split("/");
			if(res[3] == "organization"){	
				
				 displayAlertMsg(responseValue);
                		
			}
			else{
				displayAlertMsg("Organization updated successfully")
				setTimeout("location.href = 'apporganization'",2000);
				//setTimeout("location.href = 'redirect_currency.html'",60000000000000000000);
			}
			
			}
		
	}

	});
	
	}else{
		heroNotifications.showFailure("Please Upload the logo image");
		return false;
	}

	}else{
	    heroNotifications.showFailure("Your Email-id is In correct");
	    return false;
	}
		}
		else{
			heroNotifications.showFailure("Company address is too long");
			return false;
		}
				}
		
		else{
			heroNotifications.showFailure("Please fill the Mandatory fields");
			return false;
		}
}

function loadsmstemplate()
{
	/*getsmscontent(1);*/
	getsmsapidetails();
}

function getsmscontent(templateid)
{
	$('#templateidtext').val(templateid);
	$.ajax({
		type:'GET',
		url:'getsmscontent/'+templateid,
		contentType:'application/json',
		success:function(data,textStatus,jqXHR)
		{
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			
			var smsContentObj = responseValue[0];
			
			/*$('#templatenametext').val(smsContentObj.smstempname);*/
			$('#messagecontenttext').val(smsContentObj.smsmsgcontent);
		}
	});
}

function displaymsgcontent(msgcontent,templateid)
{
	
	$('#templateidtext').val(templateid);
	$('#messagecontenttext').val(msgcontent);
	
}
/*function displayemailcontent(msgcontent,templateid,msgsubject)
{
	
	$('#emailsubjecttext').val(msgsubject);
	$('#emailcontenttext').val(msgcontent);
	$('#emailtemplateidtext').val(templateid);
}*/

function getsmsapidetails()
{
	
	$.ajax({
		type:'GET',
		url:'loadsmssettings',
		contentType:'application/json',
		success:function(data,textStatus,jqXHR)
		{
			
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			
			if(responseType == 'S')
				{
				console.log(length)
				if(responseValue['emailsettingList'].length>0){
    				var emailObj = responseValue['emailsettingList'][0];	
					$('#emailidtext').val(emailObj.emailid);
					$('#emailpasswordtext').val(emailObj.emailpassword);
					$("#oprntextemail").val("SAVEEMAILSETTINGS");
				}
				else{
					$("#oprntextemail").val("INSEMAILSETTINGS");
				}
				if(responseValue['smssettingList'].length>0){
    				var smsObj = responseValue['smssettingList'][0];	
					
				$('#apikeytext').val(smsObj.apikey);
				$('#usernametext').val(smsObj.apiusername);
				$('#passwordtext').val(smsObj.apipassword);
				
				if(parseInt(smsObj.smssend) == 1)
					{
					$('#smssendcheck').attr('checked',true);
					}
				else
					{
					$('#smssendcheck').attr('checked',false);
					}
				$("#oprntext").val("SAVESETTINGS");
				//alert("updated successfully");

				}
				else{
					$("#oprntext").val("INSSMSSETTINGS");
//alert("saved successfully");
						
				}
			}
		}
	});		
}

function savemsgcontent()
{
	var msgContent = $('#messagecontenttext').val();
	var smsObject = new Object();
	smsObject.messagecontent = $('#messagecontenttext').val();
	smsObject.templateid = $('#templateidtext').val();
	smsObject.oprn = "SAVEMSG";
	
	$.ajax({
		type:'POST',
		contentType:'application/json',
		url:'savesmscontent',
		data:JSON.stringify(smsObject),
		success:function(data,textStatus,jqXHR)
		{
			
		      var responseType = getResponseType(data);
		      var responseValue = getResponseValue(data);
		      console.log(responseType)
		      displaySuccessMsg(data);	
		    // window.location.href="smstemplate";
		      
		}
	});
}
function saveemailsettings()
{
	var isValidArray = [];
	isValidArray.push(validateonbuttonclick('#emailidtext','input'));
	isValidArray.push(validateonbuttonclick('#emailpasswordtext','input'));	
	var totalValid = jQuery.inArray( false, isValidArray );
	if(totalValid < 0)
	{

		var emailsettingsobj = new Object();
		emailsettingsobj.emailid = $('#emailidtext').val();
		emailsettingsobj.emailpassword= $('#emailpasswordtext').val();
		emailsettingsobj.oprn= $('#oprntextemail').val();
		
		var regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
		 
		if(regex.test(emailsettingsobj.emailid )){


		$.ajax({
			type:'POST',
			contentType:'application/json',
			url:'saveemailsettings',
			data:JSON.stringify(emailsettingsobj),
			success:function(data,textStatus,jqXHR)
			{
				var responseType = getResponseType(data);
				var responseValue = getResponseValue(data);
				console.log(responseType)
				displaySuccessMsg(data);	
			}
		});
		}
		else{
			heroNotifications.showFailure("Your Email-id is In correct");
			return false;
		}

}
}
	
function saveemailcontent()
{
	//alert("priya")
	var emailObject = new Object();
	emailObject.templateid = $('#emailtemplateidtext').val();
	emailObject.emailcontent = $('#emailcontenttext').val();
	emailObject.emailsubject = $('#emailsubjecttext').val();
	emailObject.oprn = "SAVEEMAIL";
	$.ajax({
		type:'POST',
		contentType:'application/json',
		url:'saveemailcontent',
		data:JSON.stringify(emailObject),
		success:function(data,textStatus,jqXHR)
		{
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			console.log(responseType)
			displaySuccessMsg(data);	
		}
	});

}
function savesettings()
{

	var isValidArray = [];
	isValidArray.push(validateonbuttonclick('#apikeytext','input'));
	isValidArray.push(validateonbuttonclick('#usernametext','input'));
	isValidArray.push(validateonbuttonclick('#passwordtext','input'));
	
	var totalValid = jQuery.inArray( false, isValidArray );

	if(totalValid < 0)
		{
var settingsobj = new Object();
settingsobj.apikey = $('#apikeytext').val();
settingsobj.username = $('#usernametext').val();
settingsobj.password = $('#passwordtext').val();

if($('#smssendcheck').prop('checked'))
	{
	settingsobj.smssend = "1";
	}
else
	{
	settingsobj.smssend = "0";
	}

settingsobj.oprn = "SAVESETTINGS";

//window.location.href="smstemplate";
//alert(JSON.stringify(settingsobj));
settingsobj.oprn = $('#oprntext').val();

$.ajax({
	type:'POST',
	contentType:'application/json',
	url:'savesmscontent',
	data:JSON.stringify(settingsobj),
	success:function(data,textStatus,jqXHR)
	{
		var responseType = getResponseType(data);
		var responseValue = getResponseValue(data);
		
		
		displaySuccessMsg(data);	
		//clearsmscontentFields();
		//alert(responseMsg(responseValue));
		var str = window.location.pathname;
		var res = str.split("/");
		
		if(res[3] == "heroadminsettings"){	
			//window.location.href="login";
		}
		else{
			setTimeout("location.href = 'smstemplate'",1000);
		}
	}
});
		}else{
			heroNotifications.showFailure("Please fill the Mandatory fields");
		}

}
function clearsmscontentFields()
{
	$('#apikeytext').val("");
	$('#usernametext').val("");
	/*$('#taxtypeselect').val($("#taxtypeselect option:first").val());*/
	$('#passwordtext').val("");
	/*$('#taxratetext').val("");*/
	
	$('#oprntext').val("INS");
}


function reset_orgn()
{
var confm = confirm("If you leave this page, all unsaved changes will be lost. Are you sure you want to leave this page?");
if(confm)
	{
	loadorganization();
	}
}

