var productArray = [];
function savestockprice(index,stockid,currentPrice)
{
	
	var sellprice = $('#sellprice'+index).val();
	if(sellprice != currentPrice)
		{
		var stockObj = new Object();
		stockObj.sellprice = sellprice;
		stockObj.oprn = "UPD_PRICE";
		stockObj.stockid = stockid;
		stockObj.userid = $('#inventoryuseridtext').val();
		
	$.ajax({
		
		type:'POST',
		contentType : 'application/json',
		url:'updatestockprice',
		data:JSON.stringify(stockObj),
		success:function(data,textStatus,jqXHR)
		{
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			
			if(responseType == 'S')
				{
				alert(responseMsg(responseValue))
/*				window.location.href="stockviewhistory";*/
				}
		}
	});		
		}
	else
		{
		alert("Cannot be same Price")
		}
	
}

function updatesellprice(index,value)
{
	$('#sellprice'+index).val(value);
}

function loadstocktransfer()
{

	$("#transferdatetext").val($.datepicker.formatDate("dd/mm/yy", new Date()));
	
	loadproductsuggestion();
	
	var transferorderid = getParameterByName('stocktransferid');
	$('#transferorderidtext').val(transferorderid);
	
	console.log(transferorderid);
	
	
	if(parseInt(transferorderid) == 0)
		{
		$('#oprntext').val("INS");
		$(".stocktransfernumberDiv").css({"display":"none"});
		
		}
	else
		{
		$('#oprntext').val("UPD");
		$(".stocktransfernumberDiv").css({"display":"block"});
		}
	var storeid = $('#storeselect').val();
	$.ajax({
			type:'GET',
			contentType:'application/json',
			url:'loadtransferlist/'+transferorderid+"/"+storeid,
			success:function(data,textStatus,jqXHR)
			{
				var responseType = getResponseType(data);
				var responseValue = getResponseValue(data);
				/*$.each(responseValue, function(index, value) {
					productArray.push(responseValue[index]);
				});*/
				 
				var itemList = responseValue;
				
				for (var indx in itemList) {
					var responseObj = itemList[indx];
					indexPosition++;
				var indexPosition = productArray.length + 1;
				
				var markup = "<tr>" +
			    		
			    "<td>" + responseObj.productname + "</td>" +
				"<td align='center'>" + responseObj.batchid + "</td>" +
				"<td class='line-item-column item-currentavail transferorder-lineitem'><div class='row text-muted font-sm'><div class='col-md-6'>Source Stock</div>" +
				"<div class='separationline col-md-6'>Destination Stock</div></div><div class='row font-xs'><div class='col-md-6'>"+responseObj.sourcecount+"</div>" +
				"<div class='separationline col-md-6'>"+responseObj.destcount+"</div></div></td><td align='center'>" + responseObj.existproductcount + "</td>" +
				"<td><input type='number' id='quantity"+indexPosition+"' value="+responseObj.productcount+"  class='form-control form-Tabel'" +
						" onblur='updatequantity("+indexPosition+")'>" +
				"<input type='hidden' id='sourcequantity"+indexPosition+"' value='"+responseObj.sourcecount+"'  class='form-control form-Tabel'>" +
				"<input type='hidden' id='existquantity"+indexPosition+"' value='"+responseObj.existproductcount+"'  class='form-control form-Tabel'></td>" +
				"<td align='center'>" + responseObj.unitpricedisp + "</td>" +
				
				"<td><input type='button' value='Del' onclick='deleteproduct("+indexPosition+")' name='record"+indexPosition+"'></td>" +
			    		
			    		"</tr>";
			    
			    
			    $("table tbody").append(markup);
			    
			    
			    var productObj = new Object();
			    productObj.index = indexPosition;
			    productObj.tprid = responseObj.tprid;
			    productObj.productid = responseObj.productid;
			    productObj.transferid = responseObj.transferid;
			    productObj.batchid = responseObj.batchid;
			    productObj.oprn = "UPD";
			    productObj.productcount = responseObj.productcount;
			    productObj.unitprice = responseObj.unitprice;
			    productObj.unitpricedisp = responseObj.unitpricedisp;
			    
			    addtoProductArray(productObj)
			    
			    $('#transferordernotext').val(responseObj.transferno);
			    $('#storeselect').val(responseObj.pharmacyid);
			    $('#storeselect').prop('disabled', true);
				}
				/*var arr = jQuery.makeArray( responseValue );*/
				
			}
	});
}

function loadproductsuggestion()
{
$.ajax(
		{
			type:'GET',
			contentType:'application/json',
			url:'loadproductsuggestion',
			success:function(data,textStatus,jqXHR)
			{
				var responseType = getResponseType(data);
				var responseValue = getResponseValue(data);
				var productSuggestion = [];
				if(responseType == 'S')
					{
					
				
				
				$.each(responseValue, function(index, value) {
					productSuggestion.push(responseValue[index]);
				});
					}
				
				/*$( "#itemcodetext" ).autocomplete({
				    source: productSuggestion,
	                  onSelect: function (suggestion) {
		                	alert(suggestion)
		                    }
				  });*/
				
				$( "#itemcodetext" )
			      .autocomplete({
			        minLength: 0,
			        source: function( request, response ) {
			        
			          response( $.ui.autocomplete.filter(
			        		  productSuggestion, extractLast( request.term ) ) );
			        },
			        focus: function() {
			          return false;
			        },
			        select: function( event, ui ) {
			          var terms = split( this.value );
			          terms.pop();
			          $('#itemcodetext').val(ui.item.label);
			          $('#productcodetext').val(ui.item.value);
			          terms.push( ui.item.label );
			          terms.push( "" );
			          
			          return false;
			        }
			      });
				
			}
			}
		);	
}

function split( val ) {
    return val.split( /,\s*/ );
}
function extractLast( term ) {
  return split( term ).pop();
}

function loadbatchidlist()
{
$.ajax(
{
type:'GET',
contentType:'application/json',
/*url:'loadbatchidsuggestion/'+$('#itemcodetext').val(),*/
url:'loadbatchidsuggestion/'+$('#productcodetext').val(),
success:function(data,textStatus,jqXHR)
{
	var responseType = getResponseType(data);
	var responseValue = getResponseValue(data);
	var batchidSuggestion = [];
	if(responseType == 'S')
		{
		
	$.each(responseValue, function(index, value) {
		batchidSuggestion.push(responseValue[index]);
	});
	
	$('#batchidselect').empty();
	var option = '<option value="0">-- Select --</option>';
	/*$('#batchidselect').append(option);*/
	
	for (var i=0;i<batchidSuggestion.length;i++){
		
	   option += '<option value="'+ batchidSuggestion[i].value + '">' + batchidSuggestion[i].label + '</option>';
	}
	$('#batchidselect').append(option);
	
		}
	
	/*$( "#batchidtext" ).autocomplete({
	    source: batchidSuggestion,
          onSelect: function (suggestion) {
            	alert(suggestion)
                }
	  });*/
}
}		
);	
}

function addproduct()
{
	var formvalid = validateonbuttonclick('#itemcodetext','input');	
	 
	var productname = $("#itemcodetext").val();
	var productcode = $('#productcodetext').val();
	var batchid = $("#batchidselect").val();
	
	if(batchid == 0)
		{
		alert("Please Select Batch");
		return false;
		}
	else
		{
		
	$.each(productArray, function(index, value) {
		var itemObj = productArray[index];
		if(itemObj.productid == productname && itemObj.batchid == batchid)
			{
			alert("Item Already Added");
			formvalid = false;
			return false;
			}
	});
	
	if(formvalid)
		{
		
		var indexPosition = productArray.length + 1;
		var storeid = $('#storeselect').val();
		
		$.ajax(
		{
			type:'GET',
			contentType:'application/json',
			/*url:'getstockproductdetail/'+productname+"/"+batchid+"/"+storeid,*/
			url:'getstockproductdetail/'+productcode+"/"+batchid+"/"+storeid,
			success:function(data,textStatus,jqXHR)
			{
				var responseType = getResponseType(data);
				var responseValue = getResponseValue(data);
				
				var responseObj = responseValue[0];
				
				var indexPosition = productArray.length + 1;
				var markup = "<tr>" +
			    		
			    "<td>" + responseObj.productname + "</td>" +
				"<td align='center'>" + batchid + "</td>" +
				"<td class='line-item-column item-currentavail transferorder-lineitem'><div class='row text-muted font-sm'><div class='col-md-6'>Source Stock</div>" +
				"<div class='separationline col-md-6'>Destination Stock</div></div><div class='row font-xs'><div class='col-md-6'>"+responseObj.sourcecount+"</div>" +
				"<div class='separationline col-md-6'>"+responseObj.destinationcount+"</div></div></td> <td align='center'>" + responseObj.existproductcount + "</td>" +
				"<td><input type='number' id='quantity"+indexPosition+"' value='1'  class='form-control form-Tabel' onblur='updatequantity("+indexPosition+")'>" +
				"<input type='hidden' id='sourcequantity"+indexPosition+"' value='"+responseObj.sourcecount+"'  class='form-control form-Tabel'>" +
						"<input type='hidden' id='existquantity"+indexPosition+"' value='"+responseObj.sourcecount+"'  class='form-control form-Tabel'></td>" +
				"<td align='center'>" + responseObj.unitpricedisp + "</td>" +
				
				"<td><input type='button' value='Del' onclick='deleteproduct("+indexPosition+")' name='record"+indexPosition+"'></td>" +
			    		
			    		"</tr>";
			    
			    
			    $("table tbody").append(markup);
			    
			    var productObj = new Object();
			    productObj.index = indexPosition;
			    productObj.tprid = responseObj.tprid;
			    productObj.productid = responseObj.productid;
			    productObj.transferid = responseObj.transferid;
			    productObj.batchid = batchid;
			    productObj.oprn = "INS";
			    productObj.productcount = 1;
			    productObj.unitprice = responseObj.unitprice;
			    productObj.unitpricedisp = responseObj.unitpricedisp;
			    productObj.sourcecount = responseObj.sourcecount;
			    productObj.productname = responseObj.productname;
			    
			    
			    addtoProductArray(productObj)
			    	
			    $('#batchidselect').empty();
				var option = '<option value="0">-- Select --</option>';
				$('#batchidselect').append(option);
			
			    $("#itemcodetext").val("");
			    $("#batchidselect").val("0");
			    
			    }
	
		});

		}
	
	$('#storeselect').prop('disabled', true);
	$('#batchidselect').val("0");
		}
}

function addtoProductArray(productObj)
{
	productArray.push(productObj);	
		
}

function updatequantity(indexPosition)
{
	
	$("td input[id='quantity"+indexPosition+"']").each(function(){
		var productcount = parseInt(($(this).val()));
		var sourceCount = parseInt($('#sourcequantity'+indexPosition).val());
		var existCount = parseInt($('#existquantity'+indexPosition).val());
		
		var maxCount = 0;
		var oprn = $('#oprntext').val();
		if(oprn == 'INS')
			{
			maxCount = sourceCount;
			}
		else if(oprn == 'UPD')
		{
			maxCount = parseInt(sourceCount) + parseInt(existCount);
		}
		
		if(maxCount < productcount)
			{
			productcount = 1;
			$('#quantity'+indexPosition).val(1);
			alert("Quantity must be less than Source Quantity "+maxCount)
			}
		
		var updateProdObj;
		var removeItem;
		for (var indx in productArray) {
			
	updateProdObj = (productArray[indx])
     var prodArrayindex = updateProdObj.index;
     
     if(indexPosition == prodArrayindex)
    	 {
    	 removeItem = productArray[indx];
    	 updateProdObj.productcount = productcount;
    	 updateProdObj.existproductcount = productcount;
    	 
    	 productArray = jQuery.grep(productArray, function(value) {
  		   return value != removeItem;
  		 });
  		 
  		 productArray.push(updateProdObj);
  		 
    	 break;
    	 }
     
   }
 
		        
		 
	});
}

function savestocktransfer()
{
var transferorderno = $('#transferordernotext').val();
var transferdate = $('#storeselect').val();

var invalidQty = 0;
var itemNil = 0,nilProductNames="";
var formvalid = false;
formvalid = validateonbuttonclick('#transferdatetext','input');	

if(productArray.length == 0)
{
alert("Please Select any one Product");
return false;
}
else
{
$.each(productArray, function(index, value) {
	var itemObj = productArray[index];
	
	if(itemObj.productcount == 0)
		{
		invalidQty++;
		}
	if(itemObj.sourcecount == 0)
		{
		itemNil++;
		nilProductNames += itemObj.productname + ",";
		}
});

}

if(invalidQty > 0)
{
alert("Quantity must be greater than Zero");
return false;
}

if(itemNil > 0)
{
alert("Product( "+nilProductNames+" ) is not available in Stock");
return false;
}


if(formvalid)
{

var stocktransferObj = new Object();
stocktransferObj.itemlist = JSON.stringify(productArray);

stocktransferObj.transferid=$('#transferorderidtext').val();
stocktransferObj.transferno=$('#transferordernotext').val();
stocktransferObj.pharmacyid=$('#storeselect').val();
stocktransferObj.deliverystatus=0;
stocktransferObj.transferdate=$('#transferdatetext').val();
stocktransferObj.oprn=$('#oprntext').val();
stocktransferObj.userid = $('#inventoryuseridtext').val(); 
stocktransferObj.status = 1;

$.ajax(
{
	contentType:'application/json',
	success : function(data,textStatus,jqXHR)
	{
		var responseType = getResponseType(data);
		var responseValue = getResponseValue(data);
		
	        if(responseType == 'S')        
	        	{
	        	alert(responseMsg(responseValue))
	        	window.location.href="stocktransferhistory";
	        	}
	        else
	        	{
	        	alert((responseValue))
	        	}
	},
	type:'POST',
	url:"savestocktransferdetails",
	data:JSON.stringify(stocktransferObj)
	}
);
}

}

function loadstocktxrhistory()
{
	var transferorderid = getParameterByName('stocktransferid');
$.ajax({
		
		type:'POST',
		contentType : 'application/json',
		url:'loadstocktxrhistory',
		success:function(data,textStatus,jqXHR)
		{
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			
			$('#transferDT').DataTable( {
		    	data: responseValue,
		    	"order": [[ 0, "desc" ]],
		        columns: [
		            { data: 'transferid' },
		            { data: 'stocktransfernavigate' },
		            { data: 'transferdate' },
		            { data: 'storename' },
		            { data: 'productcount' },
		            { data: 'amountdisp' },
		            { data: 'status' },
		            { data: 'action' }
		        ]
	            
		    } );	
			
			selectStockTxrItem();
		}
	});			
}

function selectStockTxrItem() {
	$('#transferDT tbody').on('click', '.delete.myBtnTab', function() {
		var table = $('#transferDT').DataTable();
		var transferObject = [];
		transferObject.push(table.row( $(this).parents('tr') ).data());
		/*categoryObject.push(table.row(this).data());*/
		 
		var object = transferObject[0];
		$('#oprntext').val("DEL");
		
		var table = $('#transferDT').DataTable();
		confirmTransferorderDelete(table.row($(this).parents('tr')),object);
		
	});
	$('#transferDT tbody').on('click', '.edit.myBtnTab', function() {
		var table = $('#transferDT').DataTable();
		var transferObject = [];
		transferObject.push(table.row( $(this).parents('tr') ).data());
		/*categoryObject.push(table.row(this).data());*/
		
		var object = transferObject[0];
		
		window.location.href="stocktransfer?stocktransferid="+object['transferid'];
		
	});
}

function confirmTransferorderDelete(tableRow,transferObject)
{
$('.btn.btn-white').on('click',function()
{

	var isValid = true;
	var oprn = $('#oprntext').val();
	if(oprn != 'DEL')
	{
		/*isValid = validateonbuttonclick('#productnametext','input');	
		isValid = validateonbuttonclick('#productcodetext','input');*/
	}

	if(isValid)
		{
		
		transferObject.oprn="DEL";
		transferObject.status = 1;
		
		$.ajax(
		{
		type:'POST',
		contentType:'application/json',
		url:'savestocktransferdetails',
		data:JSON.stringify(transferObject),
		success:function(data,textStatus,jqXHR)
		{
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			
			if(responseType == 'S')
				{
				tableRow.remove().draw();
				}
			else if(responseType == 'F')
				{
				alert(responseValue);
				}
			}
		}		
		);
		}
	
	});
}

function deleteproduct(indexPosition)
{
	
	$("table tbody").find("input[name='record"+indexPosition+"']").each(function(){
    	/*if($(this).is(":checked")){*/
            $(this).parents("tr").remove();
        /*}*/
            

    		var quantity = ($(this).val());
    		 var updateProdObj;
    		 var removeItem;
    		for (var indx in productArray) {
    			
    	updateProdObj = (productArray[indx])
         var prodArrayindex = updateProdObj.index;
         
         if(indexPosition == prodArrayindex)
        	 {
        	 removeItem = productArray[indx];
        	 updateProdObj.productcount = quantity;
        	 
        	 productArray = jQuery.grep(productArray, function(value) {
      		   return value != removeItem;
      		 });
      		 
	      	break;
        	 }
         
       }
    });	
}

function changerecvstatus(index)
{

}

function saverecvstatus(index,txrid)
{
	var stocktransferObj = new Object();
	var deliverystatus = $('#statusselect'+index).val();
	
	stocktransferObj.transferid = txrid;
	stocktransferObj.userid = $('#inventoryuseridtext').val();
	stocktransferObj.deliverystatus = deliverystatus;
	stocktransferObj.transferdate = $.datepicker.formatDate("dd/mm/yy", new Date());
	
	console.log(stocktransferObj);
	
	$.ajax(
			{
				contentType:'application/json',
				success : function(data,textStatus,jqXHR)
				{
					var responseType = getResponseType(data);
					var responseValue = getResponseValue(data);
					
				        if(responseType == 'S')        
				        	{
				        	alert(responseMsg(responseValue))
				        	window.location.href="stocktransferhistory";
				        	}
				        else
				        	{
				        	alert(responseMsg(responseValue))
				        	window.location.href="stocktransferhistory";
				        	}
				},
				type:'POST',
				url:"savestockstatusupdate",
				data:JSON.stringify(stocktransferObj)
				}
			);
	
}

function showbatchdetails(productid)
{
	var productindex = 0;
	$.ajax({
	type:'GET',
	url:'productbatchdetails/'+productid,
	contentType:'application/json',
	success:function(data,textStatus,jqXWR)
	{
		var responseType = getResponseType(data);
		var responseValue = getResponseValue(data);
		if(responseType == 'S')
			{
			productindex = parseInt(productindex) + 1;
			
			var itemList = responseValue;
			
			
			$('#itemDetailDT').DataTable( {
				"destroy":true,
		    	data: responseValue,
		        columns: [
		            { data: 'batchno' },
		            { data: 'purchasedate' },
		            { data: 'unitprice' },
		            { data: 'expirydate' },
		            { data: 'suppliername' },
		            { data: 'salespriceaction' },
		            { data: 'productcount' },
		            { data: 'action' }
		        ]
	            
		    } ); 
			//alert("fadsf")
			//$('#wareproductinnerview').click();
			$('#myModal').modal('show');
			}
	}
});
		
}

function loadstocktransferhistoryview()
{
	var selectedtxnid = ($('#selectedtxnidtext').val());
	var txnlistsize = parseInt($('#stocklistsizetext').val());
	$('#tmp_entity_date').text($.datepicker.formatDate("dd/mm/yy", new Date()));
	$('#stocknodisptext').text('# '+$('#firststockno').val());
	$('#stocknotitledisptext').text($('#firststockno').val());
	checkstockorder();
	
}

function selecttransferno(selectedtxrno,index,storename,city,state,country,zipcode)
{
	$('#selectedtxnidtext').val(selectedtxrno);
	var txnno = $('#stocktxrtd'+index).text();
	$('#stocknodisptext').text('# '+txnno);
	$('#stocknotitledisptext').text(txnno);
	$('#storenametext').text(storename);
	$('#tmp_shipping_city').text(city);
	$('#tmp_shipping_state').text(state);
	$('#tmp_shipping_country').text(country);
	$('#tmp_shipping_zipcode').text(zipcode);
	$('#tmp_shipping_address').text($('#txraddresstext'+index).val());
	
	checkstockorder();
}

function selecttransfernoscript(selectedtxrno,rowindex)
{
	$('#selectedtxnidtext').val(selectedtxrno);
	var txnno = $('#stocktxrtd'+rowindex).text();
	$('#stocknodisptext').text('# '+txnno);
	$('#stocknotitledisptext').text(txnno);
	var txrObj;
	$('#storenametext').text('');
	$('#tmp_shipping_city').text('');
	$('#tmp_shipping_state').text('');
	$('#tmp_shipping_country').text('');
	$('#tmp_shipping_zipcode').text('');
	$('#tmp_shipping_address').text('');
	
	$.each(txrList, function(index, value) {
		
		if(parseInt(rowindex) == parseInt(value.index))
			{
			txrObj = value;
				
			}
	});
	
	$('#storenametext').text(txrObj.storename);
	$('#tmp_shipping_city').text(txrObj.city);
	$('#tmp_shipping_state').text(txrObj.state);
	$('#tmp_shipping_country').text(txrObj.country);
	$('#tmp_shipping_zipcode').text(txrObj.zipcode);
	$('#tmp_shipping_address').text(txrObj.address);
	
	checkstockorder();
}

function checkstockorder()
{
	var selectedtxnid = ($('#selectedtxnidtext').val());
	var txnlistsize = parseInt($('#stocklistsizetext').val());
	
	for(var loop = 0;loop < txnlistsize;loop++)
		{
		var currenttxnid = $('#txridtext'+loop).val();
		
		if(parseInt(currenttxnid) == parseInt(selectedtxnid))
			{
			$('#stockchk'+loop).attr('checked', true);
			}
		else
			{
			$('#stockchk'+loop).attr('checked', false);
			}
		
		}	
		setitemstoDT(selectedtxnid);
}

function setitemstoDT(selectedtxnid)
{
	$.ajax({
		type:'GET',
		url:'stockdetail/'+selectedtxnid,
		contentType:'applicatin/json',
		success:function(data,textStatus,jqXWR)
		{
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			
			var stockHdrList = responseValue['receivestockList'];
			var stockProdList = responseValue['receivestockProdList'];
			var transferDate = responseValue['transferDate'];
			
			$('#tmp_due_date').text(transferDate);
			
			$('#stockHeaderDT').DataTable( {
				"destroy":true,
		    	data: stockHdrList,
		        columns: [
		            { data: 'transferno' },
		            { data: 'transferdate' },
		            { data: 'storename' }
		        ]
	            
		    } ); 
			
			$('#stockProdDT').DataTable( {
				"destroy":true,
		    	data: stockProdList,
		        columns: [
		            { data: 'index' },
		            { data: 'productname' },
		            { data: 'batchno' },
		            { data: 'productcount' },
		            { data: 'productRate' },
		            { data: 'totalamount' }
		        ]
	            
		    } ); 
		}
	});	
}

function closetransfer()
{
	
window.location.href="stocktransferhistory";
}

function generatereport()
{
var txrid = $('#selectedtxnidtext').val();
var printwindow = window.open('report/stocktransferschedule/'+txrid,'_blank')
printwindow.print();
}
var txrList;
function gettxndetails(pageno)
{
$('#txrtable > tbody').html("");
$.ajax({
	type:'GET',
	contentType:'application/json',
	url:'loadstocktxrpagewise/'+pageno,
	success:function(data,textStatus,jqXHR)
	{
		var responseType = getResponseType(data);
		var responseValue = getResponseValue(data);
		txrList = responseValue;
		
		$.each(responseValue, function(index, value) {
			
			
var markUp = "<tr><td width='50px'><input type='radio' name='stockchk' id=stockchk'"+value.index+
"' onclick='selecttransfernoscript("+value.transferid+","+value.index+")'></td>" +
		"<td id='stocktxrtd"+value.index+"'> "+value.transferno+"<input type='hidden' value='"+value.transferid+"' id='txridtext"+value.index+"'></td>" +
				"<td align='right>"+value.status_desc+"</td>" +
						"</tr>";
			
			$("#txrtable").find('tbody').append(markUp);
		});
		
		$('#stocklistsizetext').val(responseValue.length);
		
	}
});
}

var adjustmentProductArray = [];
function loadadjustment()
{
$("#adjustmentdatetext").val($.datepicker.formatDate("dd/mm/yy", new Date()));	
loadproductsuggestion();	
}

function loadadjustmentbatchidlist()
{
$.ajax(
{
type:'GET',
contentType:'application/json',
/*url:'loadbatchidsuggestion/'+$('#itemcodetext').val(),*/
url:'loadadjustmentbatches/'+$('#productcodetext').val(),
success:function(data,textStatus,jqXHR)
{
	var responseType = getResponseType(data);
	var responseValue = getResponseValue(data);
	var batchidSuggestion = [];
	if(responseType == 'S')
		{
		
	$.each(responseValue, function(index, value) {
		batchidSuggestion.push(responseValue[index]);
	});
	
	$('#batchidselect').empty();
	var option = '<option value="0">-- Select --</option>';
	/*$('#batchidselect').append(option);*/
	
	for (var i=0;i<batchidSuggestion.length;i++){
		
	   option += '<option value="'+ batchidSuggestion[i].value + '">' + batchidSuggestion[i].label + '</option>';
	}
	$('#batchidselect').append(option);
	
		}
	
	/*$( "#batchidtext" ).autocomplete({
	    source: batchidSuggestion,
          onSelect: function (suggestion) {
            	alert(suggestion)
                }
	  });*/
}
}		
);	
}

function addadjustmentproduct()
{
	var formvalid = validateonbuttonclick('#itemcodetext','input');	
	 
	var productname = $("#itemcodetext").val();
	var productcode = $('#productcodetext').val();
	var batchid = $("#batchidselect").val();
	
	if(batchid == 0)
		{
		alert("Please Select Batch");
		return false;
		}
	else
		{
		
	$.each(productArray, function(index, value) {
		var itemObj = productArray[index];
		if(itemObj.productid == productname && itemObj.batchid == batchid)
			{
			alert("Item Already Added");
			formvalid = false;
			return false;
			}
	});
	
	if(formvalid)
		{
		
		var indexPosition = productArray.length + 1;
		var storeid = $('#storeselect').val();
		
		$.ajax(
		{
			type:'GET',
			contentType:'application/json',
			/*url:'getstockproductdetail/'+productname+"/"+batchid+"/"+storeid,*/
			url:'getstockproductdetail/'+productcode+"/"+batchid+"/"+storeid,
			success:function(data,textStatus,jqXHR)
			{
				var responseType = getResponseType(data);
				var responseValue = getResponseValue(data);
				
				var responseObj = responseValue[0];
				
				var indexPosition = productArray.length + 1;
				var markup = "<tr>" +
			    		
			    "<td>" + responseObj.productname + "</td>" +
				"<td align='center'>" + batchid + "</td>" +
				"<td class='line-item-column item-currentavail transferorder-lineitem'><div class='row text-muted font-sm'><div class='col-md-6'>Source Stock</div>" +
				"<div class='separationline col-md-6'>Destination Stock</div></div><div class='row font-xs'><div class='col-md-6'>"+responseObj.sourcecount+"</div>" +
				"<div class='separationline col-md-6'>"+responseObj.destinationcount+"</div></div></td> <td align='center'>" + responseObj.destinationcount + "</td>" +
				"<td><input type='number' id='quantity"+indexPosition+"' value='1'  class='form-control form-Tabel' " +
						"onblur='updateAdjustmentquantity("+indexPosition+")'>" +
				"<input type='hidden' id='sourcequantity"+indexPosition+"' value='"+responseObj.destinationcount+"'  class='form-control form-Tabel'>" +
						"<input type='hidden' id='existquantity"+indexPosition+"' value='"+responseObj.destinationcount+"'  class='form-control form-Tabel'></td>" +
				"<td align='center'>" + responseObj.unitpricedisp + "</td>" +
				
				"<td><input type='button' value='Del' onclick='deleteproduct("+indexPosition+")' name='record"+indexPosition+"'></td>" +
			    		
			    		"</tr>";
			    
			    
			    $("table tbody").append(markup);
			    
			    var productObj = new Object();
			    productObj.index = indexPosition;
			    productObj.tprid = responseObj.tprid;
			    productObj.productid = responseObj.productid;
			    productObj.transferid = responseObj.transferid;
			    productObj.batchid = batchid;
			    productObj.oprn = "INS";
			    productObj.productcount = 1;
			    productObj.unitprice = responseObj.unitprice;
			    productObj.unitpricedisp = responseObj.unitpricedisp;
			    productObj.sourcecount = responseObj.sourcecount;
			    productObj.productname = responseObj.productname;
			    
			    
			    addtoAdjustmentProductArray(productObj)
			    
			    $("#itemcodetext").val("");
			    $("#batchidselect").val("0");
			    
			     
			}
		});

		}
	
	$('#storeselect').prop('disabled', true);
	$('#batchidselect').val("0");
		}
}

function saveadjustments()
{
	var adjustmentNo = $('#adjustmenttext').val();
	var adjustmentdate = $('#adjustmentdatetext').val();
	var storeid = $('#storeselect').val();
	var reason = $('#reasonSelect').val();
	var notes = $('#notes').val();
	
	var adjustmentObj = new Object();
	adjustmentObj.adjustmentNo = adjustmentNo;
	adjustmentObj.adjustmentdate = adjustmentdate;
	adjustmentObj.storeid = storeid;
	adjustmentObj.reason = reason;
	adjustmentObj.notes = notes;
	adjustmentObj.adjustmentProductArray = JSON.stringify(adjustmentProductArray);
	adjustmentObj.userid = $('#inventoryuseridtext').val();
	adjustmentObj.oprn = "INS";
	
	$.ajax({
		type:'POST',
		contentType:'application/json',
		url:'saveadjustments',
		data:JSON.stringify(adjustmentObj),
		success:function(data,textStatus,jqXHR)
		{
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			alert(responseType);
		}
	});
}

function addtoAdjustmentProductArray(productObj)
{
	adjustmentProductArray.push(productObj);	
		
}

function updateAdjustmentquantity(indexPosition)
{
	
	$("td input[id='quantity"+indexPosition+"']").each(function(){
		var productcount = parseInt(($(this).val()));
		var sourceCount = parseInt($('#sourcequantity'+indexPosition).val());
		var existCount = parseInt($('#existquantity'+indexPosition).val());
		
		var maxCount = 0;
		var oprn = $('#oprntext').val();
		if(oprn == 'INS')
			{
			maxCount = sourceCount;
			}
		else if(oprn == 'UPD')
		{
			maxCount = parseInt(sourceCount) + parseInt(existCount);
		}
		
		if(maxCount < productcount)
			{
			productcount = 1;
			$('#quantity'+indexPosition).val(1);
			alert("Quantity must be less than Source Quantity "+maxCount)
			}
		
		var updateProdObj;
		var removeItem;
		for (var indx in adjustmentProductArray) {
			
	updateProdObj = (adjustmentProductArray[indx])
     var prodArrayindex = updateProdObj.index;
     
     if(indexPosition == prodArrayindex)
    	 {
    	 removeItem = adjustmentProductArray[indx];
    	 updateProdObj.productcount = productcount;
    	 updateProdObj.existproductcount = productcount;
    	 
    	 adjustmentProductArray = jQuery.grep(adjustmentProductArray, function(value) {
  		   return value != removeItem;
  		 });
  		 
  		 adjustmentProductArray.push(updateProdObj);
  		 
    	 break;
    	 }
     
   }
 
		        
		 
	});
}