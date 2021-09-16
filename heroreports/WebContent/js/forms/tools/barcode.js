function changestore(storeid)
{
	loaditems(storeid,$('#productselect').val(),$('#batchselect').val(),'C','S')
}

function changeproduct(productid)
{
	loaditems($('#storeselect').val(),productid,$('#batchselect').val(),'C','P')
}

function changebatch(batchid)
{
	loaditems($('#storeselect').val(),$('#productselect').val(),batchid,'C','B')
}

function loaditems(storeid,productid,batchid,loadtype,changecomp)
{
	$.ajax({
		type:'GET',
		contentType : 'application/json',
		url:'getbarcodeproducts/'+storeid+'/'+productid+'/'+batchid+'/'+loadtype+"/"+changecomp,
		success:function(data,textStatus,jqXHR)
		{
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			
			var productList = responseValue['productList'];
			var batchList = responseValue['batchList'];
			var rateList = responseValue['rateList'];
			
			if(changecomp == 'S')
				{
				$('#productselect').empty();
				var productListoption = '';
				for (var i=0;i<productList.length;i++){
					
					productListoption += '<option value="'+ productList[i].value + '">' + productList[i].label + '</option>';
					}
				$('#productselect').append(productListoption);	
				}
			
			if(changecomp != 'B')
			{
			$('#batchselect').empty();
			var batchListoption = '';
			for (var i=0;i<batchList.length;i++){
				
				batchListoption += '<option value="'+ batchList[i].value + '">' + batchList[i].label + '</option>';
				}
			$('#batchselect').append(batchListoption);
			}
			
			$('#rateselect').empty();
			var rateListoption = '';
			for (var i=0;i<rateList.length;i++){
				
				rateListoption += '<option value="'+ rateList[i].value + '">' + rateList[i].label + '</option>';
				}
			$('#rateselect').append(rateListoption);
		}
	});		
}

function printbarcode()
{
var barcode = new Object();
barcode.storeid = $('#storeselect').val();
barcode.product = $('#productselect').val();
barcode.batchno = $('#batchselect').val();
barcode.style = $('#styleselect').val();
barcode.rate = $('#rateselect').val();
barcode.continuous = $('#continuoscountid').val();

var printtype = "";

if($("#productnamecheck").is(':checked'))
	{
	printtype = "PROD_NAME";
	}
if($("#skucheck").is(':checked'))
{
printtype += ",SKU";
}
if($("#pricecheck").is(':checked'))
{
printtype += ",PRICE";
}
if($("#expirydatecheck").is(':checked'))
{
printtype += ",EXP_DATE";
}
if($("#batchnocheck").is(':checked'))
{
printtype += ",BATCH_NO";
}
if($("#storenamecheck").is(':checked'))
{
printtype += ",STORE_NAME";
}

barcode.printtype = printtype;

/*$.ajax({
	type:'POST',
	contentType:'application/json',
	data:JSON.stringify(barcode),
	url:'printbarcode'
});*/

if(barcode.style == '0')
	{
	alert('Please Select Anyone Printing style');
	return false;
	}
else
	{
	var url = 'report/printbarcode/'+barcode.storeid+'/'+barcode.product+'/'+barcode.batchno+'/'+barcode.rate+'/'+barcode.style+'/'+barcode.printtype+'/'+barcode.continuous;
	  /*var url = 'report/barcode/1/1'*/
	var printwindow = window.open(url, '_blank');
	printwindow.print();
	}
	    
}

function stylechange(value)
{
if(parseInt(value) == 9)	
	{
	$('#continuoscountid').attr('readonly',false);
	}
else
	{
	$('#continuoscountid').attr('readonly',true);
	}
}