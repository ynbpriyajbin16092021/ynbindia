var productArray = [];
var dishArray = [];
var savetransfer = 0;
var approveArray = [];
var cusRequestedarray = [];
var Foodsetuparray = [];
var companylist = [];
var foodArray = [];
var myArray = [];
var distypelist = [];
var dishcountlist = [];
var approvalArray=[];

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
			    window.location.href="stockviewhistory";
				}
		}
	});		
		}
	else
		{
		displayAlertMsg("Cannot be same Price")
		}
	
}
function addmorerequest(transferId){
	window.location.href="addmoretransferProducts?stocktransferid="+transferId;
}

function existingOrderNo(index){
	
	$( "button" ).removeClass( "active")
	$('#transferelect'+index).addClass("active")
	var transferid = index;
	console.log(transferid)
	
	$.ajax({
		type:'GET',
		contentType:'application/json',
		url:'getTransferProducts/'+transferid,
		success:function(data,textStatus,jqXHR)
		{
			
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			console.log(responseValue);
			
			$('#dishexistingDT').DataTable( {
					data: responseValue,
					  destroy: true,
			        columns: [
			            { data: 'companyname' },
			            { data: 'dishname' },
			            { data: 'dishtypename' },
			            { data: 'productname' },
			            { data: 'productcount' }	
			        ]		            
			    } );	
			
		
				}
	});
	
	
}

function updatesellprice(index,value)
{
	$('#sellprice'+index).val(value);
}

function loadstocktransfer()
{
	getcustomerTypeList()
	$('#ReasonDiv').hide();

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
		/*$('#oprntext').val("UPD");*/
		$('#oprntext').val("INS");
		$(".stocktransfernumberDiv").css({"display":"none"});
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
/*					indexPosition++;
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
			    console.log(transferordernotext)*/
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

function loadadjproductsuggestion()
{
	
	var storeid = $("#storeselect").val();
	console.log(storeid)
$.ajax(
		{
			type:'GET',
			contentType:'application/json',
			url:'loadadjproductsuggestion/'+storeid,
			success:function(data,textStatus,jqXHR)
			{
				var responseType = getResponseType(data);
				var responseValue = getResponseValue(data);
				console.log(responseValue)
				var productSuggestion = [];
				if(responseType == 'S')
					{
					
				
				
				$.each(responseValue, function(index, value) {
					productSuggestion.push(responseValue[index]);
				});
					}
				
				
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
url:'loadbatchidsuggestion/'+$('#itemcodetext').val(),
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
	
	for (var i=0;i<batchidSuggestion.length;i++){
		
	   option += '<option value="'+ batchidSuggestion[i].value + '">' + batchidSuggestion[i].label + '</option>';
	}
	$('#batchidselect').append(option);
	
		}
	
	$( "#batchidtext" ).autocomplete({
	    source: batchidSuggestion,
          onSelect: function (suggestion) {
            	alert(suggestion)
                }
	  });
}
}		
);	
}

function getUOMforPacking()
{
	var uompacking = $('#uompackingselect').val();
	$.ajax({
		type:'GET',
		contentType:'application/json',
		url:'uomforpacking/'+uompacking,
		success:function(data,textStatus,jqXHR)
		{
			addproduct(data.inventoryResponse.responseObj.fulluomsel,data.inventoryResponse.responseObj.looseuomsel);
		}
	});	
}


function addproduct(fulluompacks,looseuompacks)
{

	var formvalid = validateonbuttonclick('#itemcodetext','input');	
	 
	var productname = $("#itemcodetext").val();
	
	var count = $("#itemcounttext").val();
	var productcode = $('#productcodetext').val();
	console.log(productcode)
	var batchid = $("#batchidselect").val();
	console.log(batchid)
	if(batchid == 0)
		{
		displayAlertMsg("Please Select Batch");
		return false;
		}
	else
		{
		
	$.each(productArray, function(index, value) {
		var itemObj = productArray[index];

		if(itemObj.productid == productname && itemObj.batchid == batchid)
			{
			displayAlertMsg("Item Already Added");
			formvalid = false;
			return false;
			}
	});
	
	if(formvalid)
		{
		
		var indexPosition = productArray.length + 1;
		var storeid = $('#storeselect').val();
		console.log(storeid)
		$.ajax(
		{
			type:'GET',
			contentType:'application/json',
			/*url:'getstockproductdetail/'+productname+"/"+batchid+"/"+storeid,*/
			/*url:'getstockproductdetail/'+productcode+"/"+batchid+"/"+storeid,*/
			url:'getstockproductdetail/'+productcode+"/"+batchid+"/"+storeid+"/T",
			success:function(data,textStatus,jqXHR)
			{
				
				
				var responseType = getResponseType(data);
				var responseValue = getResponseValue(data);
				var responseObj = responseValue[0];
				
				if((responseObj.conversionrate) == null){
					displayAlertMsg("Currency conversion rate is not available at this time period")
					return false;
				}
				
				else
				{
				   $("#uompackingselect").val(responseObj.uompackingid);
				   $.ajax({
					type:'GET',
					contentType:'application/json',
					url:'uomforpacking/'+responseObj.uompackingid,
					success:function(data,textStatus,jqXHR)
					{
						var fulluompacks = data.inventoryResponse.responseObj.fulluomsel;
						var looseuompacks =  data.inventoryResponse.responseObj.looseuomsel;
					
						
				
				var indexPosition = productArray.length + 1;
				var uomid = 'uompackingsel'+indexPosition;
				var uompackingid = $("#uompackingselect").val();
				var fulluomsel = fulluompacks;
				var looseuomsel = looseuompacks;
				fulluomsel = fulluomsel.replace('uompackingsel','fulluomsel'+indexPosition);
				var calcloosecountmethod = "calculatelooseqty('"+indexPosition+"',1)";
				fulluomsel = fulluomsel.replace('calculatelooseqty()',calcloosecountmethod);
				
				looseuomsel = looseuomsel.replace('uompackingsel','looseuomsel'+indexPosition);
				looseuomsel = looseuomsel.replace('calculatelooseqty()',calcloosecountmethod);
				var markup = "<tr>" +
			    		
			   /* "<td>" + responseObj.productname + "</td>" +
				"<td align='center'>" + batchid + "</td>" +
				"<td class='line-item-column item-currentavail transferorder-lineitem'><div class='row text-muted font-sm'><div class='col-md-6'>Source Stock</div>" +
				"<div class='separationline col-md-6'>Destination Stock</div></div><div class='row font-xs'><div class='col-md-6'>"+responseObj.sourcecount+"</div>" +
				"<div class='separationline col-md-6'>"+responseObj.destinationcount+"</div></div></td> <td align='center'>" + responseObj.existproductcount + "</td>" +
				"<td><input type='number' id='fulluomqty"+indexPosition+"' value='1' style='width:50px;' " +
				"onblur=calculatelooseqty('"+indexPosition+"',1);>&nbsp;"+fulluomsel+"</td>" +
				"<td><input type='number' id='looseuomqty"+indexPosition+"' value='0' style='width:50px;' onblur=calculatelooseqty('"+indexPosition+"',1); />&nbsp;"
				+looseuomsel+"</td><td><input type='number' id='looseqty"+indexPosition+"' name='quantity"+indexPosition+"' value='"+count+"' " +
				"<td><input type='number' id='quantity"+indexPosition+"' value='1'  class='form-Tabel' onblur='updatequantity("+indexPosition+")' disabled style='width:80px';>" +
				"<input type='hidden' id='sourcequantity"+indexPosition+"' value='"+responseObj.sourcecount+"'  class='form-control form-Tabel'>" +
						"<input type='hidden' id='existquantity"+indexPosition+"' value='"+responseObj.sourcecount+"'  class='form-control form-Tabel'></td>" +
				"<td align='center'>" + responseObj.unitpricedisp + "</td>" +
				
				"<td><input type='button' value='Del' onclick='deleteproduct("+indexPosition+")' name='record"+indexPosition+"'></td>" +
			    		
			    		"</tr>";*/
				"<td>" + responseObj.productname + "</td>" +
				"<td align='center'>" + batchid + "</td>" +
				"<td align='center'>"+responseObj.sourcecount+"</td>" +
				"<td align='center'>"+responseObj.destinationcount+"</td> <td align='center'>" + responseObj.existproductcount + "</td>" +
				"<td><input type='number' id='fulluomqty"+indexPosition+"' value='0' style='width:50px;' " +
				"onblur=calculatelooseqty('"+indexPosition+"',1);>&nbsp;"+fulluomsel+"</td>" +
				"<td><input type='number' id='looseuomqty"+indexPosition+"' value='1' style='width:50px;' onblur=calculatelooseqty('"+indexPosition+"',1); />&nbsp;"
				+looseuomsel+"<input type='hidden' id='uompackingselect"+indexPosition+"' value='"+uompackingid+"' /></td><td><input type='number' id='quantity"+indexPosition+"' value='0' disabled style='width:75px'   class='form-Tabel' onchange='updatequantity("+indexPosition+")' style='width:80px';>" +
				"<input type='hidden' id='sourcequantity"+indexPosition+"' value='"+responseObj.sourcecount+"'  class='form-control form-Tabel'>" +
						"<input type='hidden' id='existquantity"+indexPosition+"' value='"+responseObj.sourcecount+"'  class='form-control form-Tabel'></td>" +
				"<td align='center'>" + responseObj.unitpricedisp + "</td>" +
				/*"<td align='center'><input type='number' id='conversionrate"+indexPosition+"'  value='"+ responseObj.conversionrate +"' /> </td>" +""*/
				"<td align='center'>" + responseObj.conversionrate + "</td>" +
				"<td><input class='btn padd-input-icon white-bg fa-input red-font' type='button' value='&#xf014;' onclick='deleteproduct("+indexPosition+")' name='record"+indexPosition+"'></td>" +
			    		
			    		"</tr>";
			    
			    
			    $("table tbody").append(markup);
			    $("#itemcodetext").val("");
				$("#itemcounttext").val("1");
				$("#email").val("");
			    
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
			    productObj.conversionrate = responseObj.conversionrate;
			    productObj.sourcecount = responseObj.sourcecount;
			    productObj.productname = responseObj.productname;
			    
			    productObj.cgst = responseObj.cgst;
			    productObj.sgst = responseObj.sgst;
			    productObj.expirydate = responseObj.expirydate;
			    calculatelooseqty(indexPosition,0); 
			    addtoProductArray(productObj)
			    
			    	
			    $('#batchidselect').empty();
				var option = '<option value="0">-- Select --</option>';
				$('#batchidselect').append(option);
			
			    $("#itemcodetext").val("");
			    $("#batchidselect").val("0");
			    
					}
				});	
			    
			    }
			}
		});

		}
	
	$('#storeselect').prop('disabled', true);
	$('#batchidselect').val("0");
		}
}

function setuomobj(index)
{
	var fulluomqty = $('#fulluomqty'+index).val();
	var looseuomqty = $('#looseuomqty'+index).val();
	var fulluomval = $('#fulluomsel'+index).val();
	var looseuomval = $('#looseuomsel'+index).val();
	
	fulluomval = fulluomval.split("-");
	looseuomval = looseuomval.split("-");
	var looseuomsno = looseuomval[0];
	var looseuom = looseuomval[1];
	var fulluomsno = fulluomval[0];
	var fulluom = fulluomval[1];
	
	var uomobj = new Object();
	uomobj.fulluom = fulluom;
	uomobj.fulluomsno = fulluomsno;
	uomobj.looseuomsno = looseuomsno;
	uomobj.looseuom = looseuom;
	uomobj.fulluomqty = fulluomqty;
	uomobj.looseuomqty = looseuomqty;
	uomobj.uompacking=$('#uompackingselect'+index).val();
	
	return uomobj;
}

function calculatelooseqty(index,flag)
{
	var uomobj = setuomobj(index);
	var looseqtyinuom="0 ";
	
	$.ajax(
			{
				contentType:'application/json',
				success : function(data,textStatus,jqXHR)
				{
					
					var responseType = getResponseType(data);
					var responseValue = getResponseValue(data);
					
					looseqtyinuom = (responseValue);
					looseqtyinuom=looseqtyinuom.split(" ");
					$('#quantity'+index).val(looseqtyinuom[0]);
					
					var name = $("#itemcodetext").val();
					var count = $("#itemcounttext").val();
					var productid = $('#productcodetext').val();
					var productObj = new Object();
					productObj.index = index;
					productObj.fulluom=uomobj.fulluom;
					productObj.fulluomsno=uomobj.fulluomsno;
					productObj.fulluomqty = uomobj.fulluomqty;
					productObj.looseuom = uomobj.looseuom;
					productObj.looseuomqty = uomobj.looseuomqty;
					productObj.productid = name;
					productObj.productid = productid;
					productObj.quantity = looseqtyinuom[0];
					productObj.uompacking = $('#uompackingselect').val();
				
					updatequantity(index)
					
				},
				type:'POST',
				url:"calculatelooseqty",
				data:JSON.stringify(uomobj)
				}
			);
	
	return looseqtyinuom;
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
			displayAlertMsg("Quantity must be less than Source Quantity "+maxCount)
			}
		
		var updateProdObj;
		var removeItem;
		for (var indx in productArray) {
			
	updateProdObj = (productArray[indx])
     var prodArrayindex = updateProdObj.index;
     
     if(indexPosition == prodArrayindex)
    	 {
    	 var uomobj = setuomobj((parseInt(indx)+1));
    	 removeItem = productArray[indx];
    	 updateProdObj.productcount = productcount;
    	 updateProdObj.existproductcount = productcount;
    	 updateProdObj.fulluom=uomobj.fulluom;
    	 updateProdObj.fulluomsno=uomobj.fulluomsno;
    	 updateProdObj.fulluomqty = uomobj.fulluomqty;
    	 updateProdObj.looseuom = uomobj.looseuom;
    	 updateProdObj.looseuomqty = uomobj.looseuomqty;
    	 updateProdObj.uompacking = uomobj.uompacking;
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
/*var transferdate = $('#storeselect').val();*/
var transferdate = 2;
var store =	$('#userstoreid').val();

var invalidQty = 0;
var itemNil = 0,nilProductNames="";
var formvalid = false;
formvalid = validateonbuttonclick('#transferdatetext','input');	

if(productArray.length == 0)
{
	displayAlertMsg("Please Select any one Product");
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
	displayAlertMsg("Quantity must be greater than Zero");
return false;
}

if(itemNil > 0)
{
	displayAlertMsg("Product( "+nilProductNames+" ) is not available in Stock");
return false;
}


if(formvalid)
{

var stocktransferObj = new Object();
stocktransferObj.itemlist = JSON.stringify(productArray);

productArray.sort(function(a, b){
	return a.index-b.index
});

stocktransferObj.transferid=$('#transferorderidtext').val();
stocktransferObj.conversionrate=$('#conversionrate').val();

stocktransferObj.transferno=$('#transferordernotext').val();
/*stocktransferObj.pharmacyid=$('#storeselect').val();*/
stocktransferObj.pharmacyid= 2;
stocktransferObj.deliverystatus=0;
stocktransferObj.transferdate=$('#transferdatetext').val();
stocktransferObj.oprn=$('#oprntext').val();
stocktransferObj.userid = $('#inventoryuseridtext').val(); 
stocktransferObj.status = 1;

console.log(stocktransferObj)

if(store !=transferdate)

{
	
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
	        	setTimeout("location.href = 'stocktransferhistory'",2000);
	        	//window.location.href="stocktransferhistory";
	        	}
	        else
	        	{
	        	displayFailureMsg("",responseMsg(responseValue));
	        	}
	},
	type:'POST',
	url:"savestocktransferdetails",
	data:JSON.stringify(stocktransferObj)
	}
);
}
else{
	displayAlertMsg("Please Select Valid Store");
	$('#storeselect').prop('disabled', false);
}
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
		            { data: 'orderRefNo' },
		            { data: 'transferdate' },
		            { data: 'storename' },
		            { data: 'productcount' },
		            { data: 'amount' },
		            { data: 'status' },
		            { data: 'action' }
		        ]
	            
		    } );	
			
			/*selectStockTxrItem();*/
		}
	});			
}

/*function selectStockTxrItem() {
	$('#transferDT tbody').on('click', '.delete.myBtnTab', function() {
		var table = $('#transferDT').DataTable();
		var transferObject = [];
		transferObject.push(table.row( $(this).parents('tr') ).data());
		categoryObject.push(table.row(this).data());
		
		var object = transferObject[0];
		$('#oprntext').val("DEL");
		console.log(object);
		var table = $('#transferDT').DataTable();
		confirmTransferorderDelete(table.row($(this).parents('tr')),object);
		
	});
	$('#transferDT tbody').on('click', '.edit.myBtnTab', function() {
		var table = $('#transferDT').DataTable();
		var transferObject = [];
		transferObject.push(table.row( $(this).parents('tr') ).data());
		categoryObject.push(table.row(this).data());
		
		var object = transferObject[0];
		
		window.location.href="stocktransfer?stocktransferid="+object['transferid'];
		
	});
}*/

function deleteStockTransferHistory(transferdate, transferid,orderid){
	
	
	$('.btn.btn-white').on('click',function()
			{
					var transferObject = [];
					transferObject[0] = { "oprn": "DEL", "status": "1", "transferid":transferid, "transferdate" : transferdate,"orderId": orderid};
					
					$.ajax(
					{
					type:'POST',
					contentType:'application/json',
					url:'savestocktransferdetails',
					data:JSON.stringify(transferObject[0]),
					success:function(data,textStatus,jqXHR)
					{
						var responseType = getResponseType(data);
						var responseValue = getResponseValue(data);
						
						if(responseType == 'S')
							{
							displaySuccessMsg(data);
							setTimeout("location.href = 'stocktransferhistory'",2000);
							}
						else if(responseType == 'F')
							{
							displayFailureMsg("",responseMsg(responseValue));
							}
						}
					}		
					);
				
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
				displaySuccessMsg(data);
				tableRow.remove().draw();
				}
			else if(responseType == 'F')
				{
				displayFailureMsg("",responseMsg(responseValue));
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
function readytodeliver(txrid)
{
	var stocktransferObj = new Object();
	
	
	stocktransferObj.transferid = txrid;
	stocktransferObj.userid = $('#inventoryuseridtext').val();
	stocktransferObj.status = 2;
	stocktransferObj.deliverystatus = 2;
	stocktransferObj.transferdate = $.datepicker.formatDate("dd/mm/yy", new Date());
	
	console.log(stocktransferObj);
	//return false;
	
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
				        	//window.location.href="stocktransferhistory";
				        	setTimeout("location.href = 'stocktransferhistory'",2000);
				        	}
				        else
				        	{
				        

				        	displayFailureMsg("",responseMsg(responseValue));

				        	//window.location.href="stocktransferhistory";
				        	}
				},
				type:'POST',
				url:"savestockstatusupdate",
				data:JSON.stringify(stocktransferObj)
				}
			);
	
}
function saverecvstatus(index,txrid)
{
	var stocktransferObj = new Object();
	var deliverystatus = $('#statusselect'+index).val();
	
	stocktransferObj.transferid = txrid;
	stocktransferObj.userid = $('#inventoryuseridtext').val();
	stocktransferObj.deliverystatus = deliverystatus;
	stocktransferObj.transferdate = $.datepicker.formatDate("dd/mm/yy", new Date());
	stocktransferObj.status = 1;
	console.log(stocktransferObj);
	//return false;
	
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
				        	//window.location.href="stocktransferhistory";
				        	setTimeout("location.href = 'stocktransferhistory'",2000);
				        	}
				        else
				        	{
				        

				        	displayFailureMsg("",responseMsg(responseValue));

				        	//window.location.href="stocktransferhistory";
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
		
			
			 $('#productname').text(responseValue[0].productname);
			$('#itemDetailDT').DataTable( {
				"destroy":true,
		    	data: responseValue,
		        columns: [
		            { data: 'batchno' },
		            { data: 'purchasedate' },
		            { data: 'unitprice' },
		            { data: 'expirydate' },
		            { data: 'suppliername' },
		            /*{ data: 'salespriceaction' },*/
		            { data: 'productcount' },
		            { data: 'action' }
		        ]
	            
		    } ); 
		
			$('#myModal').modal('show');
			 $('.datepicker').datepicker({
			      format: 'dd/mm/yyyy',autoclose: true
			      });
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

//function setitemstoDT(selectedtxnid)
//{
//	$.ajax({
//		type:'GET',
//		url:'stockdetail/'+selectedtxnid,
//		contentType:'application/json',
//		success:function(data,textStatus,jqXWR)
//		{
//			var responseType = getResponseType(data);
//			var responseValue = getResponseValue(data);
//			
//			var stockHdrList = responseValue['receivestockList'];
//			var stockProdList = responseValue['receivestockProdList'];		
//			console.log(stockProdList)
//			var transferDate = responseValue['transferDate'];
//			
//			$('#tmp_due_date').text(transferDate);
//			
//			$('#stockHeaderDT').DataTable({
//				"destroy":true,
//		    	data: stockHdrList,
//		        columns: [
//		            { data: 'transferno' },
//		            { data: 'transferdate' },
//		            { data: 'storename' }
//		        ]
//	            
//		    }); 
//			/*var groupColumn = 1;
//			var groupColumn2 = 2;*/
//			$('#stockProdDT').DataTable({
//				"destroy":true,
//		    	data: stockProdList,
//		        columns: [
//		            { data: 'index' },
//		            { data: 'productname' },
//		            { data: 'productname' },  
//		               { data: 'productname' },    
//		            { data: 'productcount' }
//		         
//		        ],
//		        
//                 "columnDefs": [
//                     { "visible": false, "targets": [1,2] }
//                 ],
//               
//		        rowGroup: {
//		            dataSrc: [2,1]
//		        },	
//                 "drawCallback": function ( settings ) {
//                     var api = this.api();
//                     var rows = api.rows( {page:'current'} ).nodes();
//                     //console.log(rows)
//                     var last=null; 
//                     var last1=null; 
//                     var selectedtxnid = ($('#selectedtxnidtext').val());
//                 	     
//                 			
//                 			
//             $(stockProdList).each( function(i, group) {                         
//                 					
//                              if ( last !== group.companyname ) {
//                            	  
//                            	  
//                            	  if ( last1 != group.dishtypename) {
//                            		  
//                            		  $(rows).eq( i ).before(
//                                            
//                                              '<tr> <td colspan="4">'+group.companyname+'</td></tr>'
//                                          );
//                            		   last1 = group.dishtypename;
//                            	  }
//                                    	
//                                        $(rows).eq( i ).before(
//                                            '<tr class="group"><td colspan="4">'+group.dishtypename+'</td> </tr>'
//                                           
//                                        );
//                                  
//                                        last = group.companyname;
//                                     
//                                    }
//                                   
//                 				
//                                })            
//                                
//                               
//                 }
//			
//             })
//		}
//		})
//}



function setitemstoDT(selectedtxnid)
{
	$.ajax({
		type:'GET',
		url:'stockdetail/'+selectedtxnid,
		contentType:'application/json',
		success:function(data,textStatus,jqXWR)
		{
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			
			var stockHdrList = responseValue['receivestockList'];
			var stockProdList = responseValue['receivestockProdList'];		
			console.log(stockProdList)
			var transferDate = responseValue['transferDate'];
			
			$('#tmp_due_date').text(transferDate);
			
			$('#stockHeaderDT').DataTable({
				"destroy":true,
		    	data: stockHdrList,
		        columns: [
		            { data: 'transferno' },
		            { data: 'transferdate' },
		            { data: 'storename' }
		        ]
	            
		    }); 
			/*var groupColumn = 1;
			var groupColumn2 = 2;*/
			$('#stockProdDT').DataTable({
				"destroy":true,
		    	data: stockProdList,
		        columns: [
		        	{ data: 'index' },
		            { data: 'productname' },
		            { data: 'dishtypename' },
		            { data: 'companyname' },  
		            { data: 'productcount' }
		            
		         
		        ],
		        
		        order: [[3, 'asc'], [0, 'asc']],
		                rowGroup: {
		                    dataSrc: [ 3, 2 ]
		                },
		                columnDefs: [ {
		                    targets: [ 3,2 ],
		                    visible: false
		                } ],
		                
		                
		                     "drawCallback": function ( settings ) {
		                     var api = this.api();
		                     var rows = api.rows( {page:'current'} ).nodes();
		                     //console.log(rows)
		                     var last=null; 
		                     var last1=null; 
		                     var selectedtxnid = ($('#selectedtxnidtext').val());
		                 	     
		                 			
		                 			
		             $(stockProdList).each( function(i, group) {                         
		                 					
		                              if ( last !== group.companyname ) {
		                            	  
		                            	  $(rows).eq( i ).before(
		                                         '<tr class="group"> <td colspan="4"> <b> '+group.companyname+' </b></td></tr>'
		                                   );
		                            	
		                            	  last = group.companyname;
		                                     
		                                    }
		                              
		                              if ( last1 !== group.dishtypename ) {
		                            	  
		                            	  $(rows).eq( i ).before(
		                                         '<tr class="group1"> <td colspan="4"> '+group.dishtypename+'</td></tr>'
		                                   );
		                            	
		                            	  last1 = group.dishtypename;
		                                     
		                                    }
		                              
		                              
		                                   
		                 				
		                                })            
		                                
		                               
		                 }
		       
			
             })
		}
		})
}

function closetransfer()
{
	
window.location.href="stocktransferhistory";
}

function generatereport()
{
var txrid = $('#selectedtxnidtext').val();

//var printwindow = window.open('../../heroreports/forms/report/purchaseorderschedule/'+txrid[0],'_blank')
var printwindow = window.open('../../heroreports/forms/report/stocktransferschedule/'+txrid[0]+"/"+$('#selectedbillnotext').text(),'_blank')
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
loadadjproductsuggestion();	
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
		displayAlertMsg("Please Select Batch");
		return false;
		}
	else
		{
		
	$.each(productArray, function(index, value) {
		var itemObj = productArray[index];
		if(itemObj.productid == productname && itemObj.batchid == batchid)
			{
			displayAlertMsg("Item Already Added");
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
	         url:'getstockproductdetail/'+productcode+"/"+batchid+"/"+storeid+"/A",
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
				"<div class='separationline col-md-6'>Destination Stock</div></div><div class='row font-xs'><div class='col-md-6'>"+responseObj.destinationcount+"</div>" +
				"<div class='separationline col-md-6'>"+responseObj.sourcecount+"</div></div></td> <td align='center'>" + responseObj.destinationcount + "</td>" +
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

/*function addproduct()
{

	var formvalid = validateonbuttonclick('#itemcodetext','input');	
	var productname = $("#itemcodetext").val();
	var productcode = $('#productcodetext').val();
	var batchid = $("#batchidselect").val();
	
	if(batchid == 0)
		{
		displayAlertMsg("Please Select Batch");
		return false;
		}
	else
		{
		
	$.each(productArray, function(index, value) {
		var itemObj = productArray[index];
		if(itemObj.productid == productname && itemObj.batchid == batchid)
			{
			displayAlertMsg("Item Already Added");
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
			url:'getstockproductdetail/'+productname+"/"+batchid+"/"+storeid,
			url:'getstockproductdetail/'+productcode+"/"+batchid+"/"+storeid+"/T",
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
			    
			    
			   // addtoAdjustmentProductArray(productObj)
			    addtoProductArray(productObj)
			    $("#itemcodetext").val("");
			    $("#batchidselect").val("0");
			    
			     
			}
		});

		}
	
	$('#storeselect').prop('disabled', true);
	$('#batchidselect').val("0");
		}
}
*/
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
	console.log(adjustmentObj)
	//return false;
	
	$.ajax({
		type:'POST',
		contentType:'application/json',
		url:'saveadjustments',
		data:JSON.stringify(adjustmentObj),
		success:function(data,textStatus,jqXHR)
		{
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			displaySuccessMsg(data);
		}
	});
	window.location.href="adjustmenthistory";
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
			displayAlertMsg("Quantity must be less than Source Quantity "+maxCount)
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

function loadadjustmenthistory()
{
	
	$.ajax({
			
			type:'GET',
			contentType : 'application/json',
			url:'loadadjustmenthistory',
			success:function(data,textStatus,jqXHR)
			{
		
				var responseType = getResponseType(data);
				var responseValue = getResponseValue(data);
				console.log(responseValue)
				$('#adjlistDT').DataTable( {
			    	data: responseValue,
			    	"order": [[ 0, "desc" ]],
			        columns: [
			            { data: 'adjdate' },
			            { data: 'productname' },
			            { data: 'adjbatch' },
			            { data: 'adjqty' },
			            { data: 'price' },
			            { data: 'adjreason' },
			         /*   { data: 'status' },
			            { data: 'action' }*/
			        ]
		            
			    } );	
				
				/*selectStockTxrItem();*/
			}
		});			
}


function loadPurchasedProductItems(){

	var purchaseid=$("#pridselect").val();
	
	if(purchaseid > 0){
		
		$("table > tbody").html("");
		$("#PORcontents").css({display: 'block'});
		productArray= [];
		showArray= [];
		
		$.ajax({
			type:'GET',
			contentType:'application/json',
			url:'loadPurchasedProductItems/'+purchaseid,
			success:function(data,textStatus,jqXHR)
			{
			
				var responseType = getResponseType(data);
				var responseValue = getResponseValue(data);
				var batchidSuggestion = [];
				
				$.each(responseValue, function(index, value) {
					batchidSuggestion.push(responseValue[index]);
				});
				
				$('#batchidselect').empty();
				var option = '<option value="0">-- Select --</option>';
				
				for (var i=0;i<batchidSuggestion.length;i++){
					
				   option += '<option value="'+ batchidSuggestion[i].value + '">' + batchidSuggestion[i].label + '</option>';
				}
				$('#batchidselect').html(option);
				
				}
		});	
		
	}else{
		$("#PORcontents").css({display: 'none'});
		$("table > tbody").html("");
		productArray= [];
	}
}


function getStkTransferProducts(){
	var purchaseid = $("#pridselect").val();
	var batchid = $("#batchidselect").val();
	$("table tbody").html("");
	productArray = [];
	$.ajax(
			{
				type:'GET',
				contentType:'application/json',
				url:'getstocktransferproducts/'+purchaseid+"/"+batchid,
				success:function(data,textStatus,jqXHR)
				{
					var responseType = getResponseType(data);
					var responseValue = getResponseValue(data);
					
					$(responseValue).each(function(key,value){
						
					// add product start
					
					var indexPosition = productArray.length + 1;
					var storeid = $('#storeselect').val();
					var productcode = value.value;
					console.log(storeid)
					
					$.ajax(
					{
						type:'GET',
						contentType:'application/json',
						url:'getstockproductdetail/'+productcode+"/"+batchid+"/"+storeid+"/T",
						success:function(data,textStatus,jqXHR)
						{
							
							
							var responseType = getResponseType(data);
							var responseValue = getResponseValue(data);
							var responseObj = responseValue[0];
							
							if((responseObj.conversionrate) == null){
								displayAlertMsg("Currency conversion rate is not available at this time period")
								return false;
							}
							
							else
							{
							   $("#uompackingselect").val(responseObj.uompackingid);
							   $.ajax({
								type:'GET',
								contentType:'application/json',
								url:'uomforpacking/'+responseObj.uompackingid,
								success:function(data,textStatus,jqXHR)
								{
									var fulluompacks = data.inventoryResponse.responseObj.fulluomsel;
									var looseuompacks =  data.inventoryResponse.responseObj.looseuomsel;
								
									
							
							var indexPosition = productArray.length + 1;
							var uomid = 'uompackingsel'+indexPosition;
							var uompackingid = $("#uompackingselect").val();
							var fulluomsel = fulluompacks;
							var looseuomsel = looseuompacks;
							fulluomsel = fulluomsel.replace('uompackingsel','fulluomsel'+indexPosition);
							var calcloosecountmethod = "calculatelooseqty('"+indexPosition+"',1)";
							fulluomsel = fulluomsel.replace('calculatelooseqty()',calcloosecountmethod);
							
							looseuomsel = looseuomsel.replace('uompackingsel','looseuomsel'+indexPosition);
							looseuomsel = looseuomsel.replace('calculatelooseqty()',calcloosecountmethod);
							var markup = "<tr>" +
						    		
							"<td>" + responseObj.productname + "</td>" +
							"<td align='center'>" + batchid + "</td>" +
							"<td align='center'>"+responseObj.sourcecount+"</td>" +
							"<td align='center'>"+responseObj.destinationcount+"</td> <td align='center'>" + responseObj.existproductcount + "</td>" +
							"<td><input type='number' id='fulluomqty"+indexPosition+"' value='0' style='width:50px;' " +
							"onblur=calculatelooseqty('"+indexPosition+"',1);>&nbsp;"+fulluomsel+"</td>" +
							"<td><input type='number' id='looseuomqty"+indexPosition+"' value='1' style='width:50px;' onblur=calculatelooseqty('"+indexPosition+"',1); />&nbsp;"
							+looseuomsel+"<input type='hidden' id='uompackingselect"+indexPosition+"' value='"+uompackingid+"' /></td><td><input type='number' id='quantity"+indexPosition+"' value='0' disabled style='width:75px'   class='form-Tabel' onchange='updatequantity("+indexPosition+")' style='width:80px';>" +
							"<input type='hidden' id='sourcequantity"+indexPosition+"' value='"+responseObj.sourcecount+"'  class='form-control form-Tabel'>" +
									"<input type='hidden' id='existquantity"+indexPosition+"' value='"+responseObj.sourcecount+"'  class='form-control form-Tabel'></td>" +
							"<td align='center'>" + responseObj.unitpricedisp + "</td>" +
							/*"<td align='center'>" + responseObj.conversionrate + "</td>" +*/
							"<td><input class='btn padd-input-icon white-bg fa-input red-font' type='button' value='&#xf014;' onclick='deleteproduct("+indexPosition+")' name='record"+indexPosition+"'></td>" +
						    		
						    		"</tr>";
						    
						    
						    $("table tbody").append(markup);
						    $("#itemcodetext").val("");
							$("#itemcounttext").val("1");
							$("#email").val("");
						    
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
						    productObj.conversionrate = responseObj.conversionrate;
						    productObj.sourcecount = responseObj.sourcecount;
						    productObj.productname = responseObj.productname;
						    
						    productObj.cgst = responseObj.cgst;
						    productObj.sgst = responseObj.sgst;
						    productObj.expirydate = responseObj.expirydate;
						    calculatelooseqty(indexPosition,0); 
						    addtoProductArray(productObj)
						    
						    	
						    /*$('#batchidselect').empty();
							var option = '<option value="0">-- Select --</option>';
							$('#batchidselect').append(option);
						
						    $("#itemcodetext").val("");
						    $("#batchidselect").val("0");*/
						    
								}
							});	
						    
						    }
						}
					});
					
					//add product end
					
				});
				}
			});
	
}


function openDishDetails(){
	$("#dishDetails").modal('show');
}


function addtodishArray(){
	
	var index = dishArray.length + 1;	
	var dishcount = $('#dishcount').val();	
	var dishtypeid = $('#availdishtypeselect').val();	
	var dishid = $('#dishselect').val();
	var customerid = $('#customerselect').val();
	var customerName = $( "#customerselect option:selected" ).text();
	var dishName = $( "#dishselect option:selected" ).text();
	var dishTypeName = $( "#availdishtypeselect option:selected" ).text();
	var changereason =$('#changeReason').val();
	if(dishid > 0 && dishtypeid > 0  && dishcount> 0){
		$('#ReasonDiv').hide();
		

	$.ajax({
		type:'GET',
		contentType:'application/json',
		url:'getIngredientsList'+'/'+dishid+'/'+dishtypeid+'/'+dishcount,
		success:function(data,textStatus,jqXHR)
		{
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			
			if(responseValue != "undefined" && responseValue.length > 0){				
		
			    var dishList=responseValue;
				var markup = "";
				for(i=0;i<responseValue.length;i++){
					console.log(responseValue[i])
					var dishObj = new Object();
					dishObj.index =i+1;
					dishObj.productname =responseValue[i]["productname"];
					dishObj.fullqty =responseValue[i]["fullqty"];
					dishObj.fulluomqty =responseValue[i]["fullqty"];
					dishObj.fulluom =responseValue[i]["fulluom"];
					dishObj.looseqty =responseValue[i]["looseqty"];
					dishObj.looseuomqty =responseValue[i]["looseqty"];
					dishObj.looseuom =responseValue[i]["looseuom"];	
					dishObj.productid =responseValue[i]["productid"];
					dishObj.totalqty =responseValue[i]["totalqty"];	
					dishObj.fulluomid =responseValue[i]["fulluomid"];
					dishObj.looseuomid =responseValue[i]["looseuomid"];						
					dishObj.cgst =responseValue[i]["cgst"];
					dishObj.sgst =responseValue[i]["sgst"];
					dishObj.batchid =responseValue[i]["batchid"];
					dishObj.expirydate =responseValue[i]["expirydate"];
					dishObj.unitprice =responseValue[i]["sellprice"];
					dishObj.productcount =responseValue[i]["productcount"];					
					dishObj.dishid =responseValue[i]["dishid"];
					dishObj.dishtypeid =responseValue[i]["dishtypeid"];
					dishObj.addproduct = 0;
					dishObj.customerid = customerid;
					dishObj.changereason = changereason;
					dishObj.dishCount = dishcount;
					if(dishObj.productcount < dishObj.totalqty){
						displayFailureMsg("", "Not Enough Quantity for Product ' "+dishObj.productname+" '  in Stock");
						savetransfer = 1;
						return false;
					}
					dishArray.push(dishObj);
					
					 markup = "<tr>" +
					"<td>" +customerName + "</td>" +
					"<td>" +dishName + "</td>" +
					"<td>" +dishTypeName + "</td>" +
					"<td>" +dishcount + "</td>" +
					"<td>" + dishObj.productname + "</td>" +
					/*	"<td>" + dishObj.fullqty+"     "+ dishObj.fulluom+" </td>" +
					"<td>" + dishObj.fulluom + "</td>" +
					"<td>" + dishObj.looseqty +"   "+ dishObj.looseuom+" </td>" +*/
					"<td>" + dishObj.totalqty +"    "+ dishObj.looseuom+" </td>" +
					/*"<td>" + dishObj.looseuom +"</td>" +*/
					/*"<td><input class='btn white-bg fa-input red-font' type='button' value='&#xf014;' onclick='deletedish("+index+")' name='dishrecord"+index+"' /> </td>"+*/
					"</tr>";
				    $("#dishDT").find('tbody').append(markup); 
				    
					 $('#dishcount').val(0);	
					$('#dishtypeselect').val("");	
					 $('#dishselect').val("");
					 $('#changeReason').val("")
					  $('#orderselect').prop('disabled', true);
				}
		}else{
			//alert("Dish Setup Not Available")
			displayAlertMsg("Dish Setup Not Available.Ask store manager to make a set up for this dish");
		}
					
		}
	});
	}




}

function deletedish(indexPosition){
	
	$("table#dishDT tbody").find("input[name='dishrecord"+indexPosition+"']").each(function(){
	    
        $(this).parents("tr").remove();
        var position = parseInt(indexPosition)+1;
    	
    	for (var indx in dishArray) {
    		var tempObj = (dishArray[indx]);
    		if(tempObj.index >= position){
    			tempObj.index = tempObj.index - 1;
    			dishArray[indx].index = tempObj.index;
    		}
    	}
    	
	var quantity = ($(this).val());
	var updateProdObj;
	var removeItem;
	for (var indx in dishArray) {
			
	updateProdObj = (dishArray[indx])
     var prodArrayindex = updateProdObj.index;
    
     if(indexPosition == prodArrayindex)
    	 {
    	 removeItem = dishArray[indx];
    	 
    	 dishArray = jQuery.grep(dishArray, function(value) {
  		   return value != removeItem;
  		 });
  		 
      	break;
    	 }
     
   }
		
});	

}

function getdishTypeList(){
	var dishid= $('#dishselect').val();
	//console.log(dishid)
	
	$.ajax({
		type:'GET',
		contentType:'application/json',
		url:'getdishTypeList'+'/'+dishid,
		success:function(data,textStatus,jqXHR)
		{
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			console.log(responseValue);		
			var dishType = [];
			if(responseType == 'S')
				{
			$.each(responseValue, function(index, value) {
				dishType.push(responseValue[index]);
			});
			
			$('#dishtypeselect').empty();
			var option = '<option value="0">-- Select --</option>';
			
			for (var i=0;i<dishType.length;i++){
				
			   option += '<option value="'+ dishType[i].value + '">' + dishType[i].label + '</option>';
			}
			$('#dishtypeselect').append(option);
				}
		}
	});
}


function TransferIngredients()
{

var transferorderno = $('#transferordernotext').val();
var transferdate = $('#storeselect').val();
var store =	$('#userstoreid').val();

var invalidQty = 0;
var itemNil = 0,nilProductNames="";
var formvalid = false;
formvalid = validateonbuttonclick('#transferdatetext','input');	

/*if(dishArray.length == 0)
{
	displayAlertMsg("Please Select any one Product");
return false;
}
else
{
$.each(dishArray, function(index, value) {
	var itemObj = dishArray[index];
	
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

}*/

/*if(invalidQty > 0)
{
	displayAlertMsg("Quantity must be greater than Zero");
return false;
}

if(itemNil > 0)
{
	displayAlertMsg("Product( "+nilProductNames+" ) is not available in Stock");
return false;
}
*/

/*if(formvalid)
{*/

var stocktransferObj = new Object();
var result = [];
/*$.each(dishArray, function (i, e) {
    var matchingItems = $.grep(result, function (item) {
       return item.dishid === e.dishid && item.dishtypeid === e.dishtypeid && item.productid === e.productid && item.customerid === e.customerid;
    });
    if (matchingItems.length === 0){
        result.push(e);
    }else{
    	displayAlertMsg("Some menu already added and will be deleted  while saving");
    }
});*/


//stocktransferObj.itemlist = JSON.stringify(result);
var itemARRAYFOOD = [];
$(NeededProductsArray).each(function(key, value){
	var itemOBJECTFOOD = {};
	itemOBJECTFOOD.dish = value;
	console.log(value)
	itemARRAYFOOD.push(itemOBJECTFOOD);
});
console.log("array syntac");
console.log(JSON.stringify(itemARRAYFOOD));

stocktransferObj.itemlist = JSON.stringify(itemARRAYFOOD);

/*result.sort(function(a, b){
	return a.index-b.index
});*/
var id = getParameterByName('stocktransferid');
$('#transferorderidtext').val(id)
stocktransferObj.transferid=getParameterByName('stocktransferid');
/*stocktransferObj.conversionrate=$('#conversionrate').val();*/
stocktransferObj.conversionrate=1;

stocktransferObj.transferno=$('#transferordernotext').val()+"_"+$('#updtxrrefno').val();
/*stocktransferObj.pharmacyid=$('#storeselect').val();*/
stocktransferObj.pharmacyid= 1;
stocktransferObj.deliverystatus=0;
stocktransferObj.transferdate=$('#transferdatetext').val();
stocktransferObj.oprn=$('#oprntext').val();
stocktransferObj.userid = $('#inventoryuseridtext').val(); 

stocktransferObj.dishid=$('#dishselect').val();
stocktransferObj.dishtypeid=$('#dishtypeselect').val();
stocktransferObj.dishcount = $('#dishcount').val();

stocktransferObj.status = 1;
stocktransferObj.orderId=1;

//console.log(itemARRAYFOOD)
console.log(stocktransferObj)
//return false;
if( savetransfer == 0)

{
	
$.ajax(
{
	contentType:'application/json',
	success : function(data,textStatus,jqXHR)
	{
		
		var responseType = getResponseType(data);
		var responseValue = getResponseValue(data);
	console.log(responseValue)
	//return false;
	        if(responseType == 'S')        
	        	{
	        	displaySuccessMsg(data);
	        	setTimeout("location.href = 'stocktransferhistory'",2000);
	        	//window.location.href="stocktransferhistory";
	        	}
	        else
	        	{
	        	displayFailureMsg("",responseMsg(responseValue));
	        	}
	},
	type:'POST',
//	url:"savestocktransferdetails",
	url:"transferToKitchen",
	data:JSON.stringify(stocktransferObj)
	}
);
}
else{
	displayAlertMsg("You cannot transfer this as some products Not available in Stock");
	$('#storeselect').prop('disabled', false);
}
/*}*/

}

function addmoreproducts(){
	
	var addproductname = $('#itemcodetext').val();
	var addproductid = $('#productcodetext').val();
	var addproductcount = $('#productcount').val();
	console.log(addproductid)
	console.log(addproductname)
	console.log(addproductcount)
	
	if(addproductid > 0){
	$.each(dishArray, function(index, value) {
		if(addproductid == dishArray[index]["productid"]){
				if(dishArray[index]["addproduct"] == 0){
			
				 $('#itemcodetext').val("");			 
			
			}else{
//				displayAlertMsg("Product already added");
				 $('#itemcodetext').val("");				 
				   addproductid=-1;
			}
		}
	});
	}
	
	
	if(addproductid > 0){
		
	$.ajax({
			type:'GET',
			contentType:'application/json',
			url:'getStockProductDetails'+'/'+addproductid,
			success:function(data,textStatus,jqXHR)
			{
				var responseType = getResponseType(data);
				var responseValue = getResponseValue(data);			
				
				if(responseValue != "undefined" && responseValue.length > 0){				
			
					var markup = "";
			
						var dishObj = new Object();
						dishObj.index =dishArray.length+1;
						dishObj.productname =responseValue[0]["productname"];
						dishObj.fullqty =0;
						dishObj.fulluomqty =0;
						dishObj.fulluom =0;
						dishObj.looseqty =0;
						dishObj.looseuomqty =addproductcount;
						dishObj.looseuom =responseValue[0]["looseuom"];	
						dishObj.productid =responseValue[0]["productid"];
						dishObj.totalqty = addproductcount;	
						dishObj.fulluomid =0;
						dishObj.looseuomid =responseValue[0]["looseuomid"];						
						dishObj.cgst =responseValue[0]["cgst"];
						dishObj.sgst =responseValue[0]["sgst"];
						dishObj.batchid =responseValue[0]["batchid"];
						dishObj.expirydate =responseValue[0]["expirydate"];
						dishObj.unitprice =responseValue[0]["sellprice"];
						dishObj.productcount =responseValue[0]["stockproductcount"];					
						dishObj.dishid = 0;
						dishObj.dishtypeid =0;
						dishObj.addproduct  = 1;
						if(dishObj.productcount < dishObj.totalqty){
							displayFailureMsg("", "Not Enough Quantity for Product ' "+dishObj.productname+" '  in Stock");
							savetransfer = 1;
							return false;
						}
						dishArray.push(dishObj);
						NeededProductsArray.push(dishObj);
						console.log(dishArray)
						 markup = "<tr>" +
						 "<td>" + dishObj.productname + "</td>" +						
						"<td>" + dishObj.totalqty +"    "+ dishObj.looseuom+" </td>" + 
						"<td><input type='button' class='btn padd-input-icon white-bg fa-input red-font' value='Delete' onclick='deleteaddproduct("+dishObj.index +")' name='record"+dishObj.index +"'></td>" + "</tr>";
					    $("#addorderrequest").find('tbody').append(markup); 
				
					     $('#itemcodetext').val("");
						 $('#productcodetext').val(0);
						 $('#productcount').val(0);
						
			}
		}
	})
	
}else if(addproductid == 0){
	displayAlertMsg("search a product, enter Product count then add product");
}else{
	displayAlertMsg("Product already added");
	
}
	
}

function getcustomerTypeList(){
	var orderId=$('#orderselect').val();
	$.ajax({
		type:'GET',
		contentType:'application/json',
		url:'getCustomerList'+'/'+orderId,
		success:function(data,textStatus,jqXHR)
		{
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			console.log(responseValue);		
			
			if(responseType == 'S')
				{
				$('#customerselect').empty();
				var option = '<option value="0">-- Select --</option>';
			$.each(responseValue, function(index, value) {
				 option += '<option value="'+ responseValue[index].customerId + '">' + responseValue[index].companyname + '</option>';
			});
			$('#customerselect').append(option);
		
			
				}
		}
	});
}
function loadstorerequestItems(){
	var transferId = $('#pridselect').val();
	$('table#approveDT > tbody').empty();
	$('table#approveDT > thead').empty();
	$.ajax({
		type:'GET',
		contentType:'application/json',
		url:'getPendingtyransferItemList'+'/'+transferId,
		success:function(data,textStatus,jqXHR)
		{
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			console.log(responseValue);		
			$("#PORcontents").css({display: 'block'});
		/*	if(responseValue["pendinglistwithproduct"].length > 0){
				$('#totalorderlength').val(responseValue["pendinglistwithproduct"].length);
				$("#PORcontents").css({display: 'block'});
			}*/
			if(responseType == 'S')
				{
		/*	var withoutproductlist = responseValue["pendinglistwithoutproduct"];
			var withproductlist = responseValue["pendinglistwithproduct"];
				 $.each(withoutproductlist, function (index, value) {
					 var markup = "<tr><td>" +  withoutproductlist[index]["index"]  + " </td>" + 
					 "<td>" + withoutproductlist[index]["companyname"] + " </td>" + 
					 "<td>" + withoutproductlist[index]["diahName"] + " </td>" + 
					 "<td>" + withoutproductlist[index]["diahTypeName"] + " </td>" + 
					 "<td>" + responseValue[index]["productname"] + " </td>" + 
					 "<td>" + withoutproductlist[index]["dishCount"] + " </td></tr>" 
					 "<td id='detailInfo"+responseValue[index]["index"]+"' > <i class='fa fa-check color-icon-check' aria-hidden='true' onclick='approverequest("+responseValue[index]["index"] +","+responseValue[index]["tprId"]+")'></i>" +
					 		"<i class='fa fa-times color-icon-close' aria-hidden='true' onclick='rejectrequest("+responseValue[index]["index"] +","+responseValue[index]["tprId"]+")'></i> </td>" +
					 "<td><input type='text' placeholder='Reason' class='border-line' id='rejectreasons"+responseValue[index]["index"] +"' name='rejectreason"+responseValue[index]["index"] +" ' onblur='updatereason("+responseValue[index]["index"]+")'></td></tr>" 
					 
					 $(markup).prependTo("table#approveDT > tbody");
					 var obj = new Object();
					 obj.index = responseValue[index]["index"];
					 obj.tprid = responseValue[index]["tprId"];
					 obj.isapproved = -1;
					 obj.isapproved = 1
					 obj.reason = "";
					 $("#rejectreasons"+obj.index).hide();
					 approveArray.push(obj);
				 });  
				 $.each(withproductlist, function (index, value) {
					 var obj = new Object();
					 obj.index = withproductlist[index]["index"];
					 obj.tprid = withproductlist[index]["tprId"];
					
					 obj.isapproved = 1
					 obj.reason = "";
					 $("#rejectreasons"+obj.index).hide();
					 approveArray.push(obj);
				 });  */
		    	    companylist = responseValue["customerList"];
	        	    distypelist = responseValue["dishList"];
	        	    dishcountlist = responseValue["dishcountList"];
	        	   var finallist = [];
	        	   var totallist = [];
	        	   $.each(distypelist, function (dishindex, dishvalue) {
	        		 
	        		  var  finalsinglelist= [];
	        		  finalsinglelist.push(dishindex+1);
	        		 /* finalsinglelist.push(dishvalue.dishtype_name);*/
	        		  finalsinglelist.push(dishvalue.dishtypelist)
	        		   $.each(companylist, function (companyindex, companyvalue) {
	            		   var dishid = dishvalue.dishtypeid;
	            		  
	            		  var singledishcountlist = dishcountlist[dishid];
	            		  
	            		   var customerid = companyvalue.customer_id;
	            		
	            		   var result = $.grep(singledishcountlist, function(e){ 
	            			
	            			   return  e.customerid == customerid; 
	            		   });
	            		  
	            		   if(result.length >0 && result[0].dishcount !=null && result[0].dishcount >0  ){
	            			   finalsinglelist.push(result[0].dishcount)
	            		   }else{
	            			   finalsinglelist.push(0);
	            		   }
	            	   })
	            	   
	            	   finallist.push(finalsinglelist);
	        		   totallist.push(finalsinglelist);
	        		
	        	   });
	        	  
	        	   var someRow=" <tr class='someClass' ><th > SL.NO</th><th>Food</th>" 
	        	   $.each(companylist, function (index, value) {
	        		   
	        		   someRow += " <th>"+companylist[index].companyname+"</th>";
	        	   })
	        	   someRow += " <th>Total</th><tr>";
	        	 
	        	   
	        	   var tableDetailsData = "";''
	        	  $(totallist).each(function(key,value){
	        		   var removeValFrom = [0, 1];
	        		   var total = 0;
	        		   totallist[key] = totallist[key].filter(function(value, index) {
	        			     return removeValFrom.indexOf(index) == -1;
	        			})
	        			
	        		   var total = 0;
	        		   for (var i = 0; i < totallist[key].length; i++) {
	        		       total += totallist[key][i] << 0;
	        		   }
	        		   finallist[key].push(total)
	        		   
	        	   });
	        	  
	        	  
	        	   $(finallist).each(function(key,value){
	        		 
	        		   tableDetailsData += "<tr>";
	        		   		$(value).each(function(keyv,valuev){
	        		   		
	        		   		if(keyv == 1){
	        		   		
	        		   			var tableDetailsData1 = "<td><select name='row"+key+"_"+keyv+"' onchange='changedishtype(event,"+key+","+keyv+")' onfocus='focusdishtype(event,"+key+","+keyv+")' style='display:none'>" 
	        		   			$(valuev).each(function(selectkey,selectvalue){
	        		   				tableDetailsData1 += "<option value="+selectvalue.dishtypeid+">"+selectvalue.dishtypename+"</option>"
         		   					
	        		   			})
	        		   			tableDetailsData1 +="</select></td>";
	        		   			tableDetailsData +="<td>"+valuev[0].dishtypename+"</td>";
	        		   			
	        		   		}else if(keyv == 0){
	        		   			tableDetailsData += "<td>"+valuev+"</td>";
	        		   		}else{
	        		   			if(valuev == 0){
	        		   				tableDetailsData += "<td><input name='row"+key+"_"+keyv+"'  value="+valuev+" disabled class='form-control form-white' style='width:75px'></td>";
	        		   			}else{
	        		   				tableDetailsData += "<td><input name='row"+key+"_"+keyv+"' onchange='changecount(event,"+key+","+keyv+")' value="+valuev+" class='form-control form-white' style='width:75px'></td>";
	        		   			}
	        		   			
	        		   		}
	        		   			
	        		   		});
	        		   	 tableDetailsData += "</tr>";
	        	   });
	        	 
	        		   $("#approveDT thead").append(someRow);
	        	   $("#approveDT tbody").append(tableDetailsData);
				}
		}
	});
	
}
var previousobj = {};
var approvaldishtypearray = []
function focusdishtype(event,key,keyv){
	
	var row = key;
	var coloumn = keyv;
	var prevdishtypeid = $("select[name=row"+row+"_1]").val();
	var currentdishtypeid = 0;
	previousobj={"previousdishtypeid":prevdishtypeid,"dishtypeid":currentdishtypeid}
	
}
function changedishtype(event,key,keyv){
	
	var row = key;
	var coloumn = keyv;
	var currentdishtypeid = $("select[name=row"+row+"_1]").val();

	previousobj.dishtypeid=currentdishtypeid;
	
	approvaldishtypearray.push(previousobj)
}
function changecount(event,key,keyv){
	var obj = {};
	var row = key;
	var coloumn = keyv;
	var cusid = companylist[coloumn-2]["companyid"];
	var dishtypeid = $("select[name=row"+row+"_1]").val();
	var dishcount = event.target.value;
	
	var obj ={"customerid":cusid,"dishtypeid":dishtypeid,"dishcount":dishcount}
	approvalArray.push(obj);
	var total = 0;
	var companylength = companylist.length;
	 for(i=2;i < companylength + 2; i++){
         total = parseInt(total) + parseInt($("input[name=row"+row+"_"+i+"]").val());
 }
 $("input[name=row"+row+"_"+parseInt(companylength+2)+"]").val(total);

	console.log(approvalArray)
}
function updatereason(indexPosition)
{
	console.log(indexPosition)
		var reason = $("#rejectreasons"+indexPosition).val();
		console.log(reason)
		var updateProdObj;
		var removeItem;
		for (var indx in approveArray) {
		console.log(approveArray)
		updateProdObj = (approveArray[indx])
		console.log(updateProdObj)
		var prodArrayindex = updateProdObj.index;
     console.log("indexPosition "+indexPosition+"prodArrayindex"+prodArrayindex)
        if(indexPosition == prodArrayindex)
    	 {
        	
    	 removeItem = approveArray[indx];
    	 
    	 updateProdObj.reason = reason;
    	 updateProdObj.index = indexPosition
    	 approveArray = jQuery.grep(approveArray, function(value) {
  		   return value != removeItem;
  		 });

    	 approveArray.push(updateProdObj);
    	 break;
    	 }
     
   }
		 
	console.log("final product array ");
	console.log(approveArray)
}
function approverequest(indexPosition,tprid){
	$('#detailInfo'+indexPosition).html('Approved');
	var updateProdObj;
	var removeItem;
	for (var indx in approveArray) {
	console.log(approveArray)
	updateProdObj = (approveArray[indx])
	console.log(updateProdObj)
	var prodArrayindex = updateProdObj.index;
 console.log("indexPosition "+indexPosition+"prodArrayindex"+prodArrayindex)
    if(indexPosition == prodArrayindex)
	 {
    	console.log("indexPosition")
	 removeItem = approveArray[indx];
	 
	 updateProdObj.isapproved = 1;
	 updateProdObj.index = indexPosition
	 approveArray = jQuery.grep(approveArray, function(value) {
		   return value != removeItem;
		 });

	 approveArray.push(updateProdObj);
	 console.log(approveArray)
	 break;
	 }

}
}
function rejectrequest(indexPosition,tprid){
	$('#detailInfo'+indexPosition).html('Rejected');
	var updateProdObj;
	var removeItem;
	for (var indx in approveArray) {
	console.log(approveArray)
	updateProdObj = (approveArray[indx])
	console.log(updateProdObj)
	var prodArrayindex = updateProdObj.index;
 console.log("indexPosition "+indexPosition+"prodArrayindex"+prodArrayindex)
    if(indexPosition == prodArrayindex)
	 {
    	 console.log("indexPosition "+indexPosition+"prodArrayindex"+prodArrayindex)
    	 $("#rejectreasons"+indexPosition).show();
	 removeItem = approveArray[indx];
	 
	 updateProdObj.isapproved = 0;
	 updateProdObj.index = indexPosition
	 approveArray = jQuery.grep(approveArray, function(value) {
		   return value != removeItem;
		 });

	 approveArray.push(updateProdObj);
	 console.log(approveArray)
	 break;
	 }
}
}
function approvestoreRequest(){
	var invalid = 0;
	var checkedall = 1;
	/*$.each(approveArray, function(index, value) {
		var itemObj = approveArray[index];
		
		if(itemObj.isapproved == 0)
			{
			if(itemObj.reason == null || itemObj.reason == "" || itemObj.reason == " " || itemObj.reason == undefined){
				invalid=1;
			}
			}
		if(itemObj.isapproved == -1){
			checkedall = 0
		}
	
	});*/
	/*if(checkedall == 1){*/
	/*if(invalid == 0){*/
		var obj = new Object();
		obj.orderId= $('#pridselect').val();
		obj.itemlist=JSON.stringify(approvalArray)
		obj.dishtypearray =JSON.stringify(approvaldishtypearray) 
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
					        	setTimeout("location.href = 'approvestorerequest'",2000);
					        	//window.location.href="stocktransferhistory";
					        	}
					        else
					        	{
					        	displayFailureMsg("",responseMsg(responseValue));
					        	}
					},
					type:'POST',
					url:"approveStoreRequest",
					data:JSON.stringify(obj)
					}
				);
	/*}else{
		displayAlertMsg("Please enter the rejected reason");
	}*/
	/*}else{
		displayAlertMsg("Please check the orders as approve/reject");
	}*/
}
function getdishList(){
	var customerId=$('#customerselect').val();
	var orderId=$('#orderselect').val();
	$.ajax({
		type:'GET',
		contentType:'application/json',
		url:'getDishList'+'/'+customerId+'/'+orderId,
		success:function(data,textStatus,jqXHR)
		{
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			console.log(responseValue);		
			
			if(responseType == 'S')
				{
				$('#dishselect').empty();
				var option = '<option value="0">-- Select --</option>';
			$.each(responseValue, function(index, value) {
				 option += '<option value="'+ responseValue[index].dishId + '">' + responseValue[index].dishName + '</option>';
			});
			$('#dishselect').append(option);
		
			
				}
		}
	});
}
function getcusdishTypeList(){
	var customerId=$('#customerselect').val();
	var orderId=$('#orderselect').val();
	var dishId = $('#dishselect').val();
	$.ajax({
		type:'GET',
		contentType:'application/json',
		url:'getcusdishTypeList'+'/'+customerId+'/'+orderId+'/'+dishId,
		success:function(data,textStatus,jqXHR)
		{
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			if(responseType == 'S')
			{
			console.log(responseValue["custdishTypeList"][0].dishtypeName);		
			var cusRequestedDishType = responseValue["custdishTypeList"][0].dishtypeName;
			var cusRequestedDishTypeId = responseValue["custdishTypeList"][0].dishTypeId;
			var dishCount = responseValue["custdishTypeList"][0].dishCount;
		/*$('#cusDishTypeSelect').val(cusRequestedDishTypeId)
			$('#cusReqDishType').val(cusRequestedDishType)*/
			$('#dishcount').val(dishCount)
			
				$('#availdishtypeselect').empty();
				var option = '';
				var list = responseValue["availabledishTypeList"]
				var cuslist = responseValue["custdishTypeList"]
			$.each(list, function(index, value) {
				 option += '<option value="'+ list[index].dishTypeId + '">' + list[index].dishtypeName + '</option>';
			});
			$('#availdishtypeselect').append(option);
			$('#cusDishTypeSelect').empty();
			var cusoption = '';
			$.each(cuslist, function(index, value) {
				cusoption += '<option value="'+ cuslist[index].dishTypeId + '">' + cuslist[index].dishtypeName + '</option>';
				var obj = new Object();
				obj.dishTypeId = cuslist[index].dishTypeId;
				obj.count = cuslist[index].dishCount;
				cusRequestedarray.push(obj);
			});
			$('#cusDishTypeSelect').append(cusoption);
				}
		}
	});
}
function getavailabledishTypeList(){
	var cusreqTypeId = $('#cusDishTypeSelect').val();
	var availReqTypeId = $('#availdishtypeselect').val();
	if(cusreqTypeId !=availReqTypeId){
	
		$('#ReasonDiv').show();
	}else{
		
		$('#ReasonDiv').hide();
	}
	 for (var i=0; i < cusRequestedarray.length ; ++i){
		 if(cusRequestedarray[i]["dishTypeId"]== availReqTypeId){
			 $('#dishcount').val(cusRequestedarray[i]["count"])
		 }
	 }
}


function loadstorerequest(){
	
	$("#storerequesttDT thead").empty();
	$("#storerequesttDT tbody").empty();
	
	$.ajax({
		type:'GET',
		contentType:'application/json',
		url:'loadstorerequest',
		success:function(data,textStatus,jqXHR)
		{
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
 
           if(responseType == 'S'){
        	   
        	    companylist = responseValue["customerList"];
        	   var dishList = responseValue["dishList"];
        	   var dishcountlist = responseValue["dishcountList"];
        	   var dishtypeList = responseValue["list"];
        	   console.log(dishList)
        	   console.log(dishcountlist)
        	    console.log(dishtypeList)
        	    
        	   var finallist = [];
        	   var totallist = [];
        	   
        	   $.each(dishList, function (dishindex, dishvalue) {
        		   
        		  var  finalsinglelist= [];
        		  
        		  finalsinglelist.push(dishindex+1);
        		  finalsinglelist.push(dishvalue.dish_name);
        		  finalsinglelist.push(dishvalue.dishtypeList);
        		 // console.log(finalsinglelist)
        		  
        		   $.each(companylist, function (companyindex, companyvalue) {
            		   var dishid = dishvalue.dish_id;
            		   var dishtype = dishvalue.dishtypeList;
            		// console.log(dishtype)
            		  var singledishcountlist = dishcountlist[dishtype[0].dishtypeid];
            		  // console.log(singledishcountlist)
            		   var customerid = companyvalue.cust_id;
            		  // console.log(customerid)
            		   var result = $.grep(singledishcountlist, function(e){ 
            			
            			   return  e.customerid == customerid; 
            		   });
            		   console.log(result)
            		   
            		   if(result.length >0 && result[0].dishcount !=null && result[0].dishcount >0  ){
            			   finalsinglelist.push(result[0].dishcount)
            		   }else{
            			   finalsinglelist.push(0);
            		   }
            	   })
            	   
            	   finallist.push(finalsinglelist);
        		   totallist.push(finalsinglelist);
        		
        	   });
        	  
        	   var someRow=" <tr class='someClass' ><th > SL.NO</th> <th>Food</th> <th>Food Type</th>" 
        	   $.each(companylist, function (index, value) {
        		   
        		   someRow += " <th>"+companylist[index].companyname+"</th>";
        		   //console.log(someRow)
        	   })
        	   someRow += " <th>Total</th> <th>View Products</th> <tr>";
        	 
        	   
        	   var tableDetailsData = "";''
        	   //console.log(totallist)
        	  $(totallist).each(function(key,value){
        		   var removeValFrom = [0, 1];
        		   var total = 0;
        		   totallist[key] = totallist[key].filter(function(value, index) {
        			     return removeValFrom.indexOf(index) == -1;
        			})
        			
        		   var total = 0;
        		   for (var i = 0; i < totallist[key].length; i++) {
        			  // console.log(totallist[key][i])
        		       total += totallist[key][i] << 0;
        		   }
        		   finallist[key].push(total)
        		   
        	   });
        	  console.log(finallist)
        	  Foodsetuparray.push(finallist);
        	   $(finallist).each(function(key,value){
        		  
        		   tableDetailsData += "<tr>";
        		   		$(value).each(function(keyv,valuev){
        		   		
        		   			if(keyv == 2 ){
        		   				tableDetailsData += "<td><select class='dishtypechangewhenload' name='row"+key+"_"+keyv+"' onchange='changefooddetails("+key+")'"+
        		   						"style='width:120px;' >" 
        		   				$(valuev).each(function(index,data){
        		   				
        		   					tableDetailsData += "<option value="+data.dishtypeid+">"+data.dishtypename+"</option>"
        		   					});    	
        		   				tableDetailsData +="</select></td>";
        		   			}else if(keyv > 2){
        		   				tableDetailsData += "<td><input name='row"+key+"_"+keyv+"' onchange='changecountmenu(event,"+key+","+keyv+")'" +
        		   						"style='width:50px;' value="+valuev+"></td>";

        		   			}else{
        		   				tableDetailsData += "<td>"+valuev+"</td>";
        		   			}
        		   			
        		   			
        		   			
        		   			
        		   		});
        		   		tableDetailsData += "<td><input type='button' value='View' onclick='viewmodal("+key+")' name='record"+key+"'></td>";
        		   	 tableDetailsData += "</tr>";
        		   
        	   });
        	 
        		   $("#storerequesttDT thead").append(someRow);
        	       $("#storerequesttDT tbody").append(tableDetailsData);
        	       pushtoarray();
           }
		}
	}); 
}


function changefooddetails(index){
	
	var currentdishtypeid = $("select[name=row"+index+"_2]").val();
	console.log(currentdishtypeid)
	
	var companyKey =parseInt(companylist.length)+3;
	var itemcount = parseInt($("input[name=row"+index+"_"+parseInt(companyKey)+"]").val());
	console.log(itemcount)
	
			$.ajax({
			type:'GET',
			async: false,
			contentType:'application/json',
			url:'getIngredientsList'+'/'+currentdishtypeid+'/'+currentdishtypeid+'/'+itemcount,
			success:function(data,textStatus,jqXHR)
			{
				
				var responseType = getResponseType(data);
				var responseValue = getResponseValue(data);
				replaceneededfood(index,responseValue);
				
			}
			})
}



function pushtoarray(){
	
	$(Foodsetuparray[0]).each(function(foodKey,foodValue){
		var companyArray = [];
		$(companylist).each(function(companyKey,companyValue){
			var companyObj = {};
			var customerid = companyValue.cust_id;
			var itemcount = parseInt($("input[name=row"+foodKey+"_"+parseInt(companyKey+3)+"]").val());
			var itemid = parseInt($("select[name=row"+foodKey+"_2]").val());
			var itemname = $("#storerequesttDT").find("td:eq(1)").text();
			
			companyObj.customerid = customerid;
			companyObj.dishcount = itemcount;
			companyObj.dishtypeid = itemid;
			companyObj.itemname = itemname;
			
			$.ajax({
			type:'GET',
			async: false,
			contentType:'application/json',
			url:'getIngredientsList'+'/'+itemid+'/'+itemid+'/'+itemcount,
			success:function(data,textStatus,jqXHR)
			{
				
				var responseType = getResponseType(data);
				var responseValue = getResponseValue(data);
				companyObj.products = responseValue;
				
				companyArray.push(companyObj)
			}
			})
		
		})
		
		foodArray.push(companyArray);
		
	});
	
	setmodal();
	
	var currentdishtypeid = $("select[name=row0_2]").val();
	
	return false;	

var coloumn = obj;


var companylength = companylist.length;
var row = index;
var total = 0;
var objArray = [];
for(i=3;i < companylength + 3; i++){
	var customerid = companylist[i-3]["cust_id"];
	var cusotmeritemcount = parseInt($("input[name=row"+row+"_"+i+"]").val());
	total = parseInt(total) + parseInt($("input[name=row"+row+"_"+companylength + 3+"]").val());
	var obj = {};
	obj.customerid = customerid;
	obj.cusotmeritemcount = cusotmeritemcount;
	obj.currentdishtypeid = currentdishtypeid;
	objArray.push(obj);
}
console.log(objArray)
console.log(total);
//var menuObj={"cusid":0,"dishtypeid":currentdishtypeid,"dishcount":dishcount};
//console.log(menuObj)

}

var NeededProductsArray = [];
function viewmodal(index){
	console.log(NeededProductsArray)
	$('#myModal').modal('show');
				var markup = "";
				var afterdishArray = NeededProductsArray[index];
				$('#itemDetailDT').find('tbody').html("");
				$(afterdishArray).each(function(key,value){
					
					 markup += "<tr>" +
						"<td>" + value.productname + "</td>" +			
						"<td><input type='number' id='totalqty"+i+"' value='"+value.totalqty+"' style='width:50px;' " +
						"onblur=blurneededproductcount('"+index+"','"+value.productid+"',event);>"+ value.looseuom+"</td>" +
						"</tr>";
				});
				
					
				 $("#itemDetailDT").find('tbody').append(markup);
}


function setmodal(){
	//event.preventdefault();
	
	$(foodArray).each(function(foodArraykey,foodArrayvalue){
		
		var currentdishtypeid = $("select[name=row"+foodArraykey+"_2]").val();
		var productlist=foodArray[foodArraykey];
				console.log(foodArray);
				var customerid = [];
				var dishcountarray = [];
				
				var itemid = 0;
				$(foodArray[foodArraykey]).each(function(key,value){
					console.log(value.dishcount)
					if(value.dishcount > 0){
						console.log(value.customerid)
						customerid.push(value.customerid);
						dishcountarray.push(value.dishcount);
						itemid = value.dishtypeid;
					}
				});
				var markup = "";
				dishArray = [];
				for(j=0;j<productlist.length;j++){
					console.log("inside set modal")
					var responseValue = productlist[j]["products"];
					console.log(productlist)
					var singleDishArray = [];
					for(i=0;i<responseValue.length;i++){
					
					var dishObj = new Object();
					dishObj.index =i+1;
					//console.log(responseValue[i])
					dishObj.productname =responseValue[i]["productname"];					
					dishObj.fullqty =responseValue[i]["fullqty"];
					dishObj.dishName =responseValue[i]["dishname"];					
					dishObj.dishTypeName =responseValue[i]["dishtypename"];
					dishObj.fulluomqty =responseValue[i]["fullqty"];
					dishObj.fulluom =responseValue[i]["fulluom"];
					dishObj.looseqty =responseValue[i]["looseqty"];
					dishObj.looseuomqty =responseValue[i]["looseqty"];
					dishObj.looseuom =responseValue[i]["looseuom"];	
					dishObj.productid =responseValue[i]["productid"];
					dishObj.totalqty =responseValue[i]["totalqty"];	
					dishObj.fulluomid =responseValue[i]["fulluomid"];
					dishObj.looseuomid =responseValue[i]["looseuomid"];						
					dishObj.cgst =responseValue[i]["cgst"];
					dishObj.sgst =responseValue[i]["sgst"];
					dishObj.batchid =responseValue[i]["batchid"];
					dishObj.expirydate =responseValue[i]["expirydate"];
					dishObj.unitprice =responseValue[i]["sellprice"];
					dishObj.productcount =responseValue[i]["productcount"];					
					dishObj.dishid =responseValue[i]["dishid"];
					dishObj.dishtypeid =responseValue[i]["dishtypeid"];
					dishObj.addproduct = 0;
					dishObj.customerid = productlist[j]["customerid"];					
					dishObj.dishCount = productlist[j]["dishcount"];
					dishObj.itemid = itemid;
					dishObj.customerarray = customerid;
					dishObj.dishcountarray = dishcountarray;
//					$('#productname').text(responseValue[i]["dishtypename"]+'  For  '+productlist[j]["dishcount"]+' Numbers');
					$('#productname').text(responseValue[i]["dishtypename"]);
					if(dishObj.productcount < dishObj.totalqty){
						displayFailureMsg("", "Not Enough Quantity for Product ' "+dishObj.productname+" '  in Stock");
						savetransfer = 1;
						return false;
					}
					console.log(dishObj)
					
					
					//dishArray.push(dishObj);
					
					//let op = singleDishArray.findIndex(x => x.productid === dishObj.productid);
					//console.log(dishArray.length);
					var result = $.grep(dishArray, function(e){ return e.productid == dishObj.productid; });
					if(dishArray.length > 0){
						var result = $.grep(dishArray, function(e){ return e.productid == dishObj.productid; });
						if (result.length === 0) {
							dishArray.push(dishObj);
						} else if (result.length === 1) {
							var index = dishArray.findIndex(function(dish) {
								  return dish.productid == dishObj.productid
							});
							console.log(index);
							dishArray[index].totalqty = dishObj.totalqty + dishArray[index].totalqty;
						} 
					}else{
						dishArray.push(dishObj);
					}
					
					
					
					}
					
					}
				NeededProductsArray.push(dishArray);
				
	});
	console.log("console.log(NeededProductsArray)")
	console.log(NeededProductsArray)
	console.log("console.log(NeededProductsArray)")
	console.log(foodArray)
}


function changedishtypemenu(event,key,keyv){
	
	var row = key;
	var coloumn = keyv;
	var currentdishtypeid = $("select[name=row"+row+"_1]").val();
	console.log(currentdishtypeid)
	previousobj.dishtypeid=currentdishtypeid;	
	approvaldishtypearray.push(previousobj)
	
}


function calprodqty(index,value){

	$("td input[id='totalqty"+index+"']").val();
	console.log($("td input[id='totalqty"+index+"']").val());
}

function changecountmenu(event,key,keyv){
	var obj = {};
	var row = key;
	var coloumn = parseInt(keyv - 3);
	foodArray[row][coloumn].dishcount = event.target.value;
	
	var total = 0;
	var companylength = companylist.length;
	for(i=3;i < companylength + 3; i++){
		total = parseInt(total) + parseInt($("input[name=row"+row+"_"+i+"]").val());
	}
	$("input[name=row"+row+"_"+parseInt(companylength+3)+"]").val(total);
	var currentdishtypeid = $("select[name=row"+row+"_2]").val();
	console.log(currentdishtypeid)
	
			$.ajax({
			type:'GET',
			async: false,
			contentType:'application/json',
			url:'getIngredientsList'+'/'+currentdishtypeid+'/'+currentdishtypeid+'/'+total,
			success:function(data,textStatus,jqXHR)
			{
				
				var responseType = getResponseType(data);
				var responseValue = getResponseValue(data);
				
				changeneededfoodcount(key,responseValue);
				console.log(responseValue)
			}
			})
	
}

function replaceneededfood(index, obj){
	var currentdishtypeid = $("select[name=row"+index+"_2]").val();
	var customerid = [];
	var dishcountarray = [];
	var itemid = 0;
	$(foodArray[index]).each(function(key,value){
					console.log(value.dishcount)
					if(value.dishcount > 0){
						console.log(value.customerid)
						customerid.push(value.customerid);
						dishcountarray.push(value.dishcount);
						itemid = value.dishtypeid;
					}
	});
	console.log("customerid  "+customerid+"itemid "+itemid+" currentdishtypeid "+currentdishtypeid);
	
	var markup = "";
				dishArray = [];
					
				var singleDishArray = [];
					for(i=0;i<obj.length;i++){
					var responseValue = obj
					var dishObj = new Object();
					dishObj.index =i+1;
					//console.log(responseValue[i])
					dishObj.productname =responseValue[i]["productname"];					
					dishObj.fullqty =responseValue[i]["fullqty"];
					dishObj.dishName =responseValue[i]["dishname"];					
					dishObj.dishTypeName =responseValue[i]["dishtypename"];
					dishObj.fulluomqty =responseValue[i]["fullqty"];
					dishObj.fulluom =responseValue[i]["fulluom"];
					dishObj.looseqty =responseValue[i]["looseqty"];
					dishObj.looseuomqty =responseValue[i]["looseqty"];
					dishObj.looseuom =responseValue[i]["looseuom"];	
					dishObj.productid =responseValue[i]["productid"];
					dishObj.totalqty =responseValue[i]["totalqty"];	
					dishObj.fulluomid =responseValue[i]["fulluomid"];
					dishObj.looseuomid =responseValue[i]["looseuomid"];						
					dishObj.cgst =responseValue[i]["cgst"];
					dishObj.sgst =responseValue[i]["sgst"];
					dishObj.batchid =responseValue[i]["batchid"];
					dishObj.expirydate =responseValue[i]["expirydate"];
					dishObj.unitprice =responseValue[i]["sellprice"];
					dishObj.productcount =responseValue[i]["productcount"];					
					dishObj.dishid =responseValue[i]["dishid"];
					dishObj.dishtypeid =responseValue[i]["dishtypeid"];
					dishObj.addproduct = 0;				
					//dishObj.dishCount = responseValue[i]["totaldishcount"];
					dishObj.dishCount = productlist[j]["dishcount"];
					dishObj.dishcountarray = dishcountarray;
					dishObj.itemid = itemid;
					dishObj.customerarray = customerid;
					$('#productname').text(responseValue[i]["dishtypename"]);
					if(dishObj.productcount < dishObj.totalqty){
						displayFailureMsg("", "Not Enough Quantity for Product ' "+dishObj.productname+" '  in Stock");
						savetransfer = 1;
						return false;
					}
					
					var result = $.grep(dishArray, function(e){ return e.productid == dishObj.productid; });
					if(dishArray.length > 0){
						var result = $.grep(dishArray, function(e){ return e.productid == dishObj.productid; });
						if (result.length === 0) {
							dishArray.push(dishObj);
						} else if (result.length === 1) {
							var index = dishArray.findIndex(function(dish) {
								  return dish.productid == dishObj.productid
							});
							console.log(index);
							dishArray[index].totalqty = dishObj.totalqty + dishArray[index].totalqty;
						} 
					}else{
						dishArray.push(dishObj);
					}
				}
		NeededProductsArray[index]	= dishArray;
		
}
function changeneededfoodcount(index, obj)
{
	console.log(obj);
	
	$(obj).each(function(key, value){
		var productid = obj[key].productid;
		var totalqty = obj[key].totalqty;
		var product_index = NeededProductsArray[index].findIndex(function(product) {
			  return product.productid == productid
		});
		NeededProductsArray[index][product_index].totalqty = totalqty;
	});
	
}

function blurneededproductcount(index, productid, event){
	var productid = productid;
	var totalqty = event.target.value;
	var product_index = NeededProductsArray[index].findIndex(function(product) {
		  return product.productid == productid
	});
	NeededProductsArray[index][product_index].totalqty = totalqty;
}


function deleteaddproduct(indexPosition)
{
	
	$("table tbody").find("input[name='record"+indexPosition+"']").each(function(){
    	/*if($(this).is(":checked")){*/
            $(this).parents("tr").remove();
        /*}*/
            

    		var quantity = ($(this).val());
    		 var updateProdObj;
    		 var removeItem;
    		for (var indx in dishArray) {
    			
    	updateProdObj = (dishArray[indx])
         var prodArrayindex = updateProdObj.index;
         
         if(indexPosition == prodArrayindex)
        	 {
        	 removeItem = dishArray[indx];
        	 updateProdObj.productcount = quantity;
        	 
        	 dishArray = jQuery.grep(dishArray, function(value) {
      		   return value != removeItem;
      		 });
      		 
	      	break;
        	 }
         
       }
    });	
}


function saveaddtransferproduct()
{

var stocktransferObj = new Object();

var itemARRAYFOOD = [];
$(NeededProductsArray).each(function(key, value){
	var itemOBJECTFOOD = {};
	itemOBJECTFOOD.dish = value;
	console.log(value)
	itemARRAYFOOD.push(itemOBJECTFOOD);
});


stocktransferObj.itemlist = JSON.stringify(itemARRAYFOOD);
stocktransferObj.transferid = getParameterByName('stocktransferid');
stocktransferObj.conversionrate=1;
stocktransferObj.transferno='';
stocktransferObj.pharmacyid= 1;
stocktransferObj.deliverystatus=0;
stocktransferObj.transferdate=$('#transferdatetext').val();
stocktransferObj.oprn="UPD";
stocktransferObj.userid = $('#inventoryuseridtext').val(); 
stocktransferObj.status = 1;
stocktransferObj.orderId=1;

console.log(stocktransferObj)
//return false;
if( savetransfer == 0)

{
	
$.ajax(
{
	contentType:'application/json',
	success : function(data,textStatus,jqXHR)
	{
		
		var responseType = getResponseType(data);
		var responseValue = getResponseValue(data);
	console.log(responseValue)
	//return false;
	        if(responseType == 'S')        
	        	{
	        	displaySuccessMsg(data);
	        	setTimeout("location.href = 'stocktransferhistory'",2000);
	        	//window.location.href="stocktransferhistory";
	        	}
	        else
	        	{
	        	displayFailureMsg("",responseMsg(responseValue));
	        	}
	},
	type:'POST',
//	url:"savestocktransferdetails",
	url:"saveaddtransferproduct",
	data:JSON.stringify(stocktransferObj)
	}
);
}
else{
	displayAlertMsg("You cannot transfer this as some products Not available in Stock");
	$('#storeselect').prop('disabled', false);
}
/*}*/

}