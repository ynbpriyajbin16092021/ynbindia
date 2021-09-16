function getResponseValue(data)
{
	var inventoryResponse = $.parseJSON(JSON.stringify(data));
	var responseObj = $.parseJSON((JSON
			.stringify(inventoryResponse['inventoryResponse'])));
	var responseValue = (responseObj['responseObj']);
	var preResponseValue;
	if(responseValue instanceof Object)
		{
		preResponseValue = responseMsg(responseValue);
		}
	if(preResponseValue != undefined)
		{
		responseValue = preResponseValue;
		}
	
	return responseValue;
}

function getResponseType(data)
{
	var inventoryResponse = $.parseJSON(JSON.stringify(data));
	var responseObj = $.parseJSON((JSON
			.stringify(inventoryResponse['inventoryResponse'])));
	var responseValue = (responseObj['responseType']);
	if(responseValue == undefined)
		{
		responseValue = responseMsg(responseValue);
		}
	return responseValue;
}

function generatedidValue(responseValue)
{
	var responseObject = $.parseJSON((JSON.stringify(responseValue)));
	return responseObject['id'];
}
function responseMsg(responseValue)
{
	var responseObject = $.parseJSON((JSON.stringify(responseValue)));
	var preResponseValue;
	preResponseValue = responseObject['msg'];
	
	if(preResponseValue == undefined)
		{
	return responseValue;	
		}
	else
		{
		return preResponseValue;
		}
	
}
function validateoninputtyping(idname,inputtype)
{
	$(idname).on(inputtype, function() {
		var input=$(this);
		var is_name=input.val();
	
		if(is_name)
		{
			input.removeClass("invalid").addClass("valid");
			return true;
			}
		else
		{
			input.removeClass("valid").addClass("invalid");
			return false;
			}
	});
}

function validateonbuttonclick(idname,inputtype)
{
	/* $(idname).on('click', function() { */
		var input=$(idname);
		var is_name=input.val();
	
		if(is_name)
		{
			input.removeClass("invalid").addClass("valid");
			return true;
			}
		else
		{
			input.removeClass("valid").addClass("invalid");
			return false;
			}
	/* }); */
}

function getParameterByName(name, url) {
    if (!url) url = window.location.href;
    name = name.replace(/[\[\]]/g, "\\$&");
    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, " "));
}

function getTableJSON(tablerowid,classname)
{
	   var json = '{';
	   var otArr = [];
	   var tbl2 = $(tablerowid).each(function(i) {        
	      x = $(classname, this);
	      var itArr = [];
	      var obj = {};
	      x.each(function() {
		     var enteredValue = $(this).val()||$(this).text();
	         /*itArr.push('"'+$(this).attr('id')+'":'+'"' + enteredValue + '"');*/
		     
		     obj[$(this).attr('name')] = $(this).val()
		     itArr.push(obj)
	      });
	      /*otArr.push('"' + i + '": {' + itArr.join(',') + '}');*/
	      otArr.push(obj);
	   })
	   json += otArr.join(",") + '}'
	   /* console.log($(this).find('option:selected').val()); */
	   
return otArr;		   
}
function displaySuccessMsg(data)
{
	var message = "";
	message = getResponseValue(data);
	heroNotifications.showSuccess(message);	
}
function displayFailureMsg(oprn,responseValue)
{
	
	if(oprn == 'DEL')
	{
	$('#deletehdrivid').text("Not Deleted");
	$('#deletedtlivid').text(responseMsg(responseValue));
	}
else
	{
	heroNotifications.showFailure(responseMsg(responseValue));	
	}	
}
function displayAlertMsg(msg)
{
	$("#alertModalMsg").text(msg);
	$("#alertModalMsg-hero").modal('show');
}

function displayConfirmMsg(msg)
{
	$("#confirmationMsg").text(msg);
	$("#modalConfirm").modal('show');
	var output = $("input[name = 'confirmResult']").val();
	console.log(output);
}

function loadheader()
{
	
}

function focuscursor(focusidis)
{
	var searchInput = $(focusidis);
	 
	 searchInput.focus();
	 var tmpStr = searchInput.val();
	 searchInput.val('');
	 searchInput.val(tmpStr);	
}

function openchangepwpopup()
{
$('#currentpasswordtext').val('');
$('#newpasswordtext').val('');
$('#confirmpasswordtext').val('');
focuscursor('#currentpasswordtext');
$('#changepwbtn').click();
}

function specialcharactercheck(textValue)
{
	var specialCharacterCount = 0;
		var iChars = "!@#$%^&*()+=-[]\\\';,./{}|\":<>?";
		for (var i = 0; i < textValue.length; i++) {
		    if (iChars.indexOf(textValue.charAt(i)) != -1) {
		    	specialCharacterCount = parseInt(specialCharacterCount) + 1;
		   
		        }
		    }
		 return specialCharacterCount;	
}

function changepassword()
{
var currentpassword = $('#currentpasswordtext').val();
var newpassword = $('#newpasswordtext').val();
var confirmpassword = $('#confirmpasswordtext').val();
var Userid = $('#inventoryuseridtext').val();

if(newpassword != confirmpassword)
	{
	displayAlertMsg("New Password and Confirm Password must be same");
	return false;
	}
if(currentpassword == confirmpassword)
{
	displayAlertMsg("Current Password and New Password must not be same");
return false;
}

else
	{
	var nwspecialCharacterCount = specialcharactercheck(newpassword);
	var cfspecialCharacterCount = specialcharactercheck(confirmpassword);
	var specialCharCheck = parseInt(nwspecialCharacterCount) + parseInt(cfspecialCharacterCount);
	if(newpassword.length >= 8 && confirmpassword.length >= 8)
		{
		if(parseInt(specialCharCheck) >= 2)
		{
		var passwordObj = new Object();
		passwordObj.Password = currentpassword;
		passwordObj.confirmPassword = newpassword;
		passwordObj.Userid = Userid;
		
		$.ajax({
			type:'POST',
			url:'changepassword',
			contentType:'application/json',
			data:JSON.stringify(passwordObj),
			success:function(data,textStatus,jqXHR)
			{
				
				var responseType = getResponseType(data);
				var responseValue = (getResponseValue(data));
				
				alert(responseValue);
				if(responseType == 'S')
					{
					window.location.href="signout"
					}
			}
		});
	
		}
	else
		{
		displayAlertMsg("Password must contain Special Characters");
		return false;
		}
		}
	else
		{
		displayAlertMsg("Password length must be greater than 8");
		return false;
		}
		}
}

function confirm_reset() {
    return confirm("If you leave this page, all unsaved changes will be lost. Are you sure you want to leave this page?");
}

function leave_function()
{
	$('#formid').reset();	
}