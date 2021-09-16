var txnformatArr = [];

function loadtransactioncodes()
{
	var storeid = $('#storeselect').val();
	$.ajax({
		type:'GET',
		contentType:'application/json',
		url:'transactioncodelist/'+storeid,
		success:function(data,textStatus,jqXHR)
		{
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			
			$('#txncodesDT').DataTable( {
		    	data: responseValue,
		    	destroy : true,
		        columns: [
		            { data: 'usertypedesc' },
		            { data: 'txndesc' },
		            { data: 'txnnolength' },
		            { data: 'startfrom' },
		            { data: 'endto' },
		            { data: 'action' },
		        ]
	            
		    } ); 
			selectTxncodeItem();
		}
	});
	
}

function selectTxncodeItem() {
	$('#txncodesDT tbody').on('click', '.delete.myBtnTab', function() {
		var table = $('#txncodesDT').DataTable();
		/*var productObject = [];
		productObject.push(table.row( $(this).parents('tr') ).data());
		
		console.log(productObject)
		var object = productObject[0];
		
		var table = $('#productDT').DataTable();
		confirmProductDelete(table.row($(this).parents('tr')),object);*/
		
	});
	$('#txncodesDT tbody').on('click', '.edit.myBtnTab', function() {
		var table = $('#txncodesDT').DataTable();
		var txncodeObject = [];
		txncodeObject.push(table.row( $(this).parents('tr') ).data());
		
		console.log(txncodeObject)
		var object = txncodeObject[0];

		window.location.href="addtransactioncode?txncodeid="+object.seqid;
		
	});
}



function loadaddtxnmaster()
{
	setdefaultfromvalue('txnnoendid','E');
	var txncodeid = getParameterByName('txncodeid');
	if(parseInt(txncodeid) > 0)
		{
		
		$.ajax({
			type:'GET',
			contentType:'application/json',
			url:'transactioncodedetail/'+txncodeid,
			success:function(data,textStatus,jqXHR)
			{
				var responseType = getResponseType(data);
				var responseValue = getResponseValue(data);

				var txnCodesList = responseValue.txnCodesList;
				
				if((parseInt(txnCodesList.length)) > 0 && responseType == 'S')
					{
					var txncodeobj = txnCodesList[0];
					$('#storeselect').val(txncodeobj.storeid);
					$('#usergroupselect').val(txncodeobj.usertypeid);
					$('#txntypeselect').val(txncodeobj.txntype);
					$('#txncodeid').val(txncodeobj.txncode);
					$('#txnnolengthid').val(txncodeobj.txnnolen);
					
					$('#txnnostartid').val(txncodeobj.txnnostart);
					$('#txnnoendid').val(txncodeobj.txnnoend);
					$('#txncodesplitid').val(txncodeobj.txncodesplit);
					$('#oprntext').val('UPD');
					
					}
				
				var txnDetailList = responseValue.txnDetailList;
				for(var loop = 0;loop < txnDetailList.length;loop++)
					{
					var txnObj = txnDetailList[loop];
					var obj = new Object();
					obj.key = txnObj.txncode
					obj.label = txnObj.txncodedesc
					obj.index = parseInt(loop)+1;
					
					txnformatArr.push(obj);
					}
				
				writetxnformat();
				formattxnno();
			}
		});
		}
}

function setdefaultfromvalue(id,type)
{
	var value = $('#'+id).val();
if(value.length == 0)
	{
	$('#'+id).val("");
	if(type == 'S')
		{
		$('#'+id).val(0);
		}
	else if(type == 'E')
		{
		var nolength = $('#txnnolengthid').val();
		console.log(nolength);
		var endno = "";
		for(var loop = 0;loop < parseInt(nolength);loop++)
			{
			endno += "9";
			}
		$('#'+id).val(endno);
		}
	}
formattxnno();
}

function addtransactioncode()
{
	var obj = new Object();
	obj.key = $('#txnnoformatid').val();
	obj.label = $('#txnnoformatid').find(":selected").text();
	obj.index = parseInt(txnformatArr.length)+1;
	
	var selectedTxnCode = $('#txnnoformatid').find(":selected").text();
	
	var exists = 0;
	var found = $.map(txnformatArr, function(val) {
	    if(val.label == selectedTxnCode )
	    	{
	    	exists = 1;
	    	}
	});
	
	if(exists == 0)
		{
		
		txnformatArr.push(obj);

		writetxnformat();
		formattxnno();
		}
	else
		{
		displayFailureMsg("","Selected Format Already Exists");
		return false;
		}
	
		
		 
}

function writetxnformat()
{
	var txnFormatHTML = "";
	$('#txnformatdispid').empty();
	$.each(txnformatArr, function (index, value) {
		var obj = txnformatArr[index];
		txnFormatHTML += "<span class='label label-primary' id='txnspanid"+index+"' onclick='removetxncode("+index+")'>"+obj.label+"&nbsp;<i class='fa fa-close' style='cursor: pointer;'></i></span>&nbsp;"
		$('#txnformatdispid').html(txnFormatHTML);
	});	
}

function removetxncode(removeItemIndex)
{
	var removeItem = txnformatArr[removeItemIndex];
	
/*txnformatArr.splice( $.inArray(removeItem,txnformatArr) ,1 );*/
 
	/*txnformatArr = $.grep(txnformatArr, function(e){ 
	     return e.key != removeItem.key; 
	});
	console.log(txnformatArr);*/
	
	for(var i = 0; i < txnformatArr.length; i++) {
	    if(txnformatArr[i].key == removeItem.key) {
	    	txnformatArr.splice(i, 1);
	        break;
	    }
	}

var txnFormatHTML = "";
$('#txnformatdispid').empty();
$.each(txnformatArr, function (index, value) {
	var obj = txnformatArr[index];
	txnFormatHTML += "<span class='label label-primary' id='txnspanid"+index+"' onclick='removetxncode("+value+")'>"+obj.label+"&nbsp;<i class='fa fa-close' style='cursor: pointer;'></i></span>&nbsp;"
	$('#txnformatdispid').html(txnFormatHTML);
});
formattxnno();
}

function gettxncode()
{
	var txncodearr = $('#txntypeselect').val().split("-");
$('#txncodeid').val(txncodearr[0]);
}

function formattxnno()
{
$('#sampletxnno').html('');
var formatno = "";
var finalResult = "";
var split = $('#txncodesplitid').val();
var nolength = $('#txnnolengthid').val();

for(var loop = 0;loop < (parseInt(nolength) - 1);loop++)
{
	formatno += "0";
}
formatno += "1";

$.each(txnformatArr, function (index, value) {
	var keyArr = (value.key).split("-");
	var key = keyArr[0];
	var compid = keyArr[1];
	var value = "";
	var date = new Date();
	
	if(compid == 'nil')
		{
		if(key == 1)
			{
			value = date.getDate();
			}
		else if(key == 2)
			{
			value = date.getMonth();
			}
		else if(key == 3)
			{
			value = date.getFullYear();
			}
		else if(key == 4)
		{
		value = date.getDate()+""+date.getMonth()+""+date.getFullYear();
		}
		
		}
	else if(key == 5)
			{
			value = $('#'+compid).val();
			if(value.length > 3)
				{
				value = value.substring(0,4);
				}
			}
		else
			{
			value = $('#'+compid).find(":selected").text();
			if(value.length > 3)
			{
				value = value.substring(0,4);
			}
			}	
	finalResult += value+split;
});

console.log(finalResult);
$('#sampletxnno').html(finalResult+formatno);
}

function savetxncode()
{
	if(txnformatArr.length == 0)
		{
		displayFailureMsg("","Please Select any Transaction Code");
		return false;
		}
	
	var txncodeobj = new Object();
	txncodeobj.txnid = $('#txnid').val();
	txncodeobj.storeid = $('#storeselect').val();
	txncodeobj.usertype = $('#usergroupselect').val();
	var txntypeArr = $('#txntypeselect').val().split("-");
	txncodeobj.txntype = txntypeArr[2];
	txncodeobj.txncode = $('#txncodeid').val();
	
	txncodeobj.txnnolength = $('#txnnolengthid').val();
	txncodeobj.txnstartno = $('#txnnostartid').val();
	txncodeobj.txnendno = $('#txnnoendid').val();
	txncodeobj.txncodesplit = $('#txncodesplitid').val();
	txncodeobj.userid = $('#inventoryuseridtext').val();
	
	txncodeobj.oprn = $('#oprntext').val();
	txncodeobj.txntypelist = JSON.stringify(txnformatArr);
	
	$.ajax(
			{
				contentType:'application/json',
				success : function(data,textStatus,jqXHR)
				{
					var responseType = getResponseType(data);
					var responseValue = getResponseValue(data);
				        if(responseType == 'S')        
				        	{
				        	displaySuccessMsg(data);
				        	window.location.href = "transactioncodeconfig";
				        	}
				        else
				        	{
				        	if(responseMsg(responseValue) == 'undefined')
				        		{
				        		displayFailureMsg("",responseValue)
				        		}
				        	else
				        		{
				        		heroNotifications.showFailure(responseMsg(responseValue));
				        		}
				        	}
				},
				type:'POST',
				url:"savetxncodemasterdata",
				data:JSON.stringify(txncodeobj)
				}
			);
}