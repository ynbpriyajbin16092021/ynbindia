function loadorganization()
{
$.ajax({
	type:'POST',
	contentType:'application/json',
	url:'loadorganization',
	success:function(data,textStatus,jqXHR)
	{
		var responseType = getResponseType(data);
		var responseValue = getResponseValue(data);

		var orgnObj = (responseValue[0]);
		$('#compnametext').val(orgnObj.orgnname);
		$('#mobnotext').val(orgnObj.orgnmobno);
		$('#emailtext').val(orgnObj.orgnemail);
		$('#orgnaddresstext').val(orgnObj.orgnaddress);
	}
});	
}

function saveorgn()
{
var orgnObj = new Object();
orgnObj.orgnname = $('#compnametext').val();
orgnObj.mobno = $('#mobnotext').val();
orgnObj.email = $('#emailtext').val();
orgnObj.orgnaddress = $('#orgnaddresstext').val();
orgnObj.oprn = $('#oprntext').val();
orgnObj.userid = $('#inventoryuseridtext').val();
orgnObj.filepath = $('#logofiletype').val();

$.ajax({
	type:'POST',
	contentType:'application/json',
	url:'saveorganization',
	data:JSON.stringify(orgnObj),
	success:function(data,textStatus,jqXHR)
	{
		var responseType = getResponseType(data);
		var responseValue = getResponseValue(data);
		alert(responseMsg(responseValue))
	}
});
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
				var smsObj = responseValue[0];
				
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
		      
		      alert(responseMsg(responseValue))
		      window.location.href="smstemplate";
		      
		}
	});
}

function savesettings()
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

alert(JSON.stringify(settingsobj));

$.ajax({
	type:'POST',
	contentType:'application/json',
	url:'savesmscontent',
	data:JSON.stringify(settingsobj),
	success:function(data,textStatus,jqXHR)
	{
		var responseType = getResponseType(data);
		var responseValue = getResponseValue(data);
		
		alert(responseMsg(responseValue));
		window.location.href="smstemplate";
	}
});
}

function reset_orgn()
{
var confm = confirm("If you leave this page, all unsaved changes will be lost. Are you sure you want to leave this page?");
if(confm)
	{
	loadorganization();
	}
}