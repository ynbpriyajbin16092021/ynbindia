var selectedrowObj;
var productArray = [];
var returnProductArray = [];


function loadpurchaseorder()
{
	getPurchaseorderitem(0);
}

function getPurchaseorderitem(purchaseorderid)
{
	
	$.ajax({
		type:'GET',
		contentType:'application/json',
		url:'purchaseorderlist/'+purchaseorderid,
		success:function(data,textStatus,jqXHR)
		{
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			
			if(responseType == 'S')
				{
				if(purchaseorderid == '0')
					{
					$('#purchaseorderDT').DataTable( {
						"order": [[ 0, "desc" ]],
				    	data: responseValue,
				    	dom: 'Bfrtip',
				        buttons: [
				             'excel', 'pdf'
				        ],
				        columns: [
				            { data: 'purchaseid' },
				            { data: 'purchasecodenavigate' },
				            { data: 'purchasedate' },
				            { data: 'suppliername' },
				            { data: 'recvstatusdesc' },
				            { data: 'totalamtdisp' },
				            { data: 'paidamtdisp' },
				            { data: 'balanceamtdisp' },
				            { data: 'purchasestatusdesc' },
				            { data: 'action' }
				        ]
			            
				    } );	
					selectPurchaseorderItem();
					}
				else
					{
					
					var purchaseorderList = responseValue['purchaseorderList'];
					var responseObj = purchaseorderList[0];
					
					$('#suppliernameselect').val(responseObj['supplierid']);
					$('#purchaseordernotext').val(responseObj['purchaseid']);
					$('#purchasecodetext').val(responseObj['purchasecode']);
					$('#referencenotext').val(responseObj['purchaserefno']);
					$('#purchasedatetext').val(responseObj['purchasedate']);
					$('#notestext').val(responseObj['purchasenotes']);
					$('#termstext').val(responseObj['purchasetnc']);
					$('#oprntext').val("UPD");
					
					/*var name = $("#itemcodetext").val();
				    var markup = "<tr>" +
				    		"<td>" + name + "</td>" +
				    				"<td><input type='number' name='quantity"+indexPosition+"' value='0' onblur='updatequantity("+indexPosition+")'></td>" +
				    						"<td><input type='button' value='Del' onclick='deleteproduct("+indexPosition+")' name='record"+indexPosition+"'>" +
				    						"</tr>";
				    $("table tbody").append(markup);
				    $("#itemcodetext").val("");
				    $("#email").val("");*/
					
					var itemList = responseValue['itemList'];
					var indexPosition = 0;
					for (var indx in itemList) {
						var itemObj = itemList[indx];
						indexPosition++;
						var markup = "<tr>" +
			    		/*"<td>" + itemObj.productid + "</td>" +*/
						"<td>" + itemObj.productname + "</td>" +
			    				"<td><input type='number' name='quantity"+indexPosition+"' value='"+itemObj.quantity+"' onblur='updatequantity("+indexPosition+")'></td>" +
			    						"<td><input type='button' value='Del' onclick='deleteproduct("+indexPosition+")' name='record"+indexPosition+"'>" +
			    						"</tr>";
			    $("table tbody").append(markup);
			    
			    addtoProductArray(itemObj)
			    
					}
					}
				 
				}

		}
	});	
}

function selectPurchaseorderItem() {
	$('#purchaseorderDT tbody').on('click', '.delete.myBtnTab', function() {
		var table = $('#purchaseorderDT').DataTable();
		var purchaseorderObject = [];
		purchaseorderObject.push(table.row( $(this).parents('tr') ).data());
		/*categoryObject.push(table.row(this).data());*/
		 
		var object = purchaseorderObject[0];
		
		var table = $('#purchaseorderDT').DataTable();
		confirmpurchaseorderDelete(table.row($(this).parents('tr')),object);
		
	});
	$('#purchaseorderDT tbody').on('click', '.edit.myBtnTab', function() {
		var table = $('#purchaseorderDT').DataTable();
		var purchaseorderObject = [];
		purchaseorderObject.push(table.row( $(this).parents('tr') ).data());
		/*categoryObject.push(table.row(this).data());*/
		
		var object = purchaseorderObject[0];
		
		window.location.href="addpurchaseorder?purchaseorderid="+object['purchaseid'];
		
	});
}
 
function confirmpurchaseorderDelete(tablerow,editedObj)
{
	$('.btn.btn-white').on('click',function()
			{
	
	$.ajax({
		type:'POST',
		contentType:'application/json',
		url:'deletepurchaseorder/'+editedObj.purchaseid,
		success:function(data,textStatus,jqXHR)
		{
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			
			displayFailureMsg("DEL",responseValue);
			location.reload();
		}
	});	
			});
}

function addproduct()
{
	var formvalid = validateonbuttonclick('#itemcodetext','input');	
	if(formvalid)
		{
		
		
	var indexPosition = productArray.length + 1;
	var name = $("#itemcodetext").val();
	var productid = $('#productcodetext').val();
	var productEntry = 0;
	
	$.each(productArray,function(index,value){
		var productObj = value;
		if(parseInt(productObj.productid) == productid)
			{
			productEntry = parseInt(productEntry) + 1;
			}
	});
	
	if(parseInt(productEntry) == 0)
		{
		/*var markup = "<tr><td><input type='checkbox' name='record'></td><td>" + name + "</td><td><input type='text' name='record' value='" + email + "'></td></tr>";*/
	    var markup = "<tr>" +
	    		"<td>" + name + "</td>" +
	    				"<td><input type='number' name='quantity"+indexPosition+"' value='1' onblur='updatequantity("+indexPosition+")'></td>" +
	    						"<td><input type='button' value='Del' onclick='deleteproduct("+indexPosition+")' name='record"+indexPosition+"'></td>" +
	    						"</tr>";
	    $("table tbody").append(markup);
	    $("#itemcodetext").val("");
	    $("#email").val("");
	 
	    var productObj = new Object();
	    productObj.index = indexPosition;
	    /*productObj.productid = name;*/
	    productObj.productid = productid;
	    productObj.quantity = 1;
	    addtoProductArray(productObj)
	
		}
	else
		{
		$("#itemcodetext").val('');
		$('#productcodetext').val('');
		alert("Product "+name+" is Already added.")
		return false;
		}
		}
}

function addtoProductArray(productObj)
{
	productArray.push(productObj);	
		
}

function updatequantity(indexPosition)
{
	
	$("td input[name='quantity"+indexPosition+"']").each(function(){
		var quantity = ($(this).val());
		 var updateProdObj;
		 var removeItem;
		for (var indx in productArray) {
			
	updateProdObj = (productArray[indx])
     var prodArrayindex = updateProdObj.index;
     
     if(indexPosition == prodArrayindex)
    	 {
    	 removeItem = productArray[indx];
    	 updateProdObj.quantity = quantity;
    	 
    	 productArray = jQuery.grep(productArray, function(value) {
  		   return value != removeItem;
  		 });
  		 
  		 productArray.push(updateProdObj);
  		 
    	 break;
    	 }
     
   }
		 /*productArray = jQuery.grep(productArray, function(value) {
		   return value != removeItem;
		 });
		 
		 productArray.push(updateProdObj);*/
		 
	});
}
function deleteproduct(indexPosition)
{
	
	$("table tbody").find("input[name='record"+indexPosition+"']").each(function(){
    	/*if($(this).is(":checked")){*/
            $(this).parents("tr").remove();
        /*}*/
            
            var position = parseInt(indexPosition)+1;
        	
        	for (var indx in productArray) {
        		var tempObj = (productArray[indx]);
        		if(tempObj.index >= position){
        			tempObj.index = tempObj.index - 1;
        			productArray[indx].index = tempObj.index;
        		}
        	}
        	
    	var quantity = ($(this).val());
    	var updateProdObj;
    	var removeItem;
    	for (var indx in productArray) {
    			
    	updateProdObj = (productArray[indx])
         var prodArrayindex = updateProdObj.index;
         console.log(productArray[indx].index);
         if(indexPosition == prodArrayindex)
        	 {
        	 removeItem = productArray[indx];
        	 updateProdObj.quantity = quantity;
        	 console.log(updateProdObj);
        	 productArray = jQuery.grep(productArray, function(value) {
      		   return value != removeItem;
      		 });
      		 
	      	break;
        	 }
         
       }
    		
    });	
}

function savepurchaseorder()
{
	var formvalid = false;
	formvalid = validateonbuttonclick('#referencenotext','input');	
	var invalidQty = 0;
	
	if(($('#purchasedatetext').val()).length == 0)
	{
	alert("Please Select Purchase Date");
	return false;
	}
	
	else if(productArray.length == 0)
		{
		alert("Please Select any one Product");
		return false;
		}
	else
		{
		$.each(productArray, function(index, value) {
			var itemObj = productArray[index];
			if(itemObj.quantity == 0)
				{
				invalidQty++;
				}
		});
		
		}
	
	if(invalidQty > 0)
		{
		alert("Quantity must be greater than Zero");
		return false;
		}
	
	if(formvalid)
		{
		
var addpurchaseObj = new Object();
addpurchaseObj.itemlist = JSON.stringify(productArray);
console.log(productArray);
addpurchaseObj.purchaseid=$('#purchaseordernotext').val();
addpurchaseObj.purchasecode=$('#purchasecodetext').val();
addpurchaseObj.purchaserefno=$('#referencenotext').val();
addpurchaseObj.purchasedate=$('#purchasedatetext').val();
addpurchaseObj.receiveddate=$('#purchasedatetext').val();
addpurchaseObj.totalamt=0;    
addpurchaseObj.paidamt=0;
addpurchaseObj.supplierid=$('#suppliernameselect').val();
addpurchaseObj.purchasenotes=$('#notestext').val();
addpurchaseObj.purchasetnc=$('#termstext').val();
addpurchaseObj.receivestatus="";
addpurchaseObj.purchasestatus="1";
addpurchaseObj.userid="";
addpurchaseObj.oprn=$('#oprntext').val();

$.ajax(
		{
			contentType:'application/json',
			success : function(data,textStatus,jqXHR)
			{
				var responseType = getResponseType(data);
				var responseValue = getResponseValue(data);
			        if(responseType == 'S')        
			        	{
			        	window.location.href="purchaseorderhistory"
			        	}
			        else
			        	{
			        	if(responseMsg(responseValue) == 'undefined')
			        		{
			        		alert(responseValue)
			        		}
			        	else
			        		{
			        		alert(responseMsg(responseValue))
			        		}
			        	}
			},
			type:'POST',
			url:"saveaddpurchaseorder",
			data:JSON.stringify(addpurchaseObj)
			}
		);
		}
}

function loadaddpurchaseorder()
{
	focuscursor('#referencenotext');
	var userid = $('#inventoryuseridtext').val();
	$.ajax(
			{
				contentType:'application/json',
				success : function(data,textStatus,jqXHR)
				{
					console.log(data);
					var responseType = getResponseType(data);
					var responseValue = getResponseValue(data);
					var productsuggestions = [];
					
					$.each(responseValue, function(index, value) {
						productsuggestions.push(responseValue[index]);
					});

					/*$( "#itemcodetext" ).autocomplete({
					    source: productsuggestions,
		                  onSelect: function (suggestion) {
			                	alert(suggestion)
			                    }
					  });*/
					
					$( "#itemcodetext" )
				      .autocomplete({
				        minLength: 0,
				        source: function( request, response ) {
				        	
				          /*console.log ($.ui.autocomplete.filter(productsuggestions, extractLast( request.term ) ));*/
				          response( $.ui.autocomplete.filter(
				        		  productsuggestions, extractLast( request.term ) ) );
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
	                
				},
				type:'GET',
				url:"productsuggestions"
				/*url:'getproductautocomplete/'+userid*/
				}
			);	
	
	/*var availableTags = [
	                     "product -1231 -asds",
	                     "State - 625 -ashd",
	                     "product -1231 -asds",
	                     "State",
	                     "State"
	                   ];
	                   $( "#itemcodetext" ).autocomplete({
	                     source: availableTags
	                   });*/
	                   
	var purchaseorderid = getParameterByName('purchaseorderid');
	if(purchaseorderid == 0)
		{
		$("#purchasedatetext").val($.datepicker.formatDate("dd/mm/yy", new Date()));
		$(".purchaseordernumberDiv").css({"display":"none"});
		}
	else{
		$(".purchaseordernumberDiv").css({"display":"block"});
	}
	getPurchaseorderitem(purchaseorderid);
}

function split( val ) {
    return val.split( /,\s*/ );
}
function extractLast( term ) {
  return split( term ).pop();
}

function displaypurchasedetails(purchaseheaderid)
{
	
	window.location.href ="receivestock?purchaseid="+purchaseheaderid
}

function loadreceivestock()
{
	
$('#pidtext').val(getParameterByName('pid'));	
/*$('#purchaseid').val(getParameterByName('pid'));*/

if(parseInt($('#taxidtext').val()) != 0)
	{
	$('#taxselect').val($('#taxidtext').val())
	}
}
function cancelpurchaseview()
{
	window.location.href="purchaseorderview?pid="+$('#pidtext').val();
}	
function cancelreturn()
{
	window.location.href="purchaseorderview?pid="+$('#phidtext').val();
}

function savereceivestock()
{
	var tableJSON = getTableJSON('#receivestocktable tr.receiverow','.form-control');
	console.log("tableJSON");
	console.log(tableJSON);
	 
	var receivestockObj = new Object();
	receivestockObj.receivelist = JSON.stringify(tableJSON);
	receivestockObj.precid=$('#precid').val();
	receivestockObj.preceiveorderid=$('#preceiveorderid').val();
	receivestockObj.purchaseorderno=$('#purchaseorderno').val();
	receivestockObj.receivedate=$('#receivedate').val();
	receivestockObj.discount=$('#discountselect').find('option:selected').val();
	receivestockObj.discountvalue=$('#discounttext').val();
	receivestockObj.orderedtax=$('#taxselect').find('option:selected').val();
	receivestockObj.shippingcost=$('#shippingcodttext').val();
	receivestockObj.notes=$('#notestext').val();
	receivestockObj.precid=$('#purchaseorderhdrno').val();
	receivestockObj.userid = $('#inventoryuseridtext').val();
	receivestockObj.oprn=$('#oprntext').val();
	
	
	$.ajax(
			{
				contentType:'application/json',
				success : function(data,textStatus,jqXHR)
				{
					var responseType = getResponseType(data);
					var responseValue = getResponseValue(data);
					
				        if(responseType == 'S')        
				        	{
				        	window.location.href="purchaseorderview?pid="+$('#purchaseid').val();
				        	 
				        	}
				        else
				        	{
				        	if(responseMsg(responseValue) == 'undefined')
				        		{
				        		alert(responseValue)
				        		}
				        	else
				        		{
				        		alert((responseValue))
				        		}
				        	window.location.href="purchaseorderview?pid="+$('#purchaseid').val();
				        	}
				},
				type:'POST',
				url:"processreceivestock",
				data:JSON.stringify(receivestockObj)
				}
			);
}
function loadpurchaseorderview()
{
	var totalpurchase = ($('#totalpurchasetext').val());
	var pid=getParameterByName('pid');
	
	$('#pidtext').val(pid);
	
	selectedid = pid;
	
	checkpurchaseorder();
	
$('#paymentdatetext').val($.datepicker.formatDate("dd/mm/yy", new Date()));
refreshpurchaselist();
}


function refreshpurchaselist()
{
	var firstidarr = [];
	firstidarr = ($('#firstidtext').val()).split("$$$");
	getPurchaseList(firstidarr[0],firstidarr[1]);	
}

function checkpurchaseorder()
{
	var selectedtxnid = ($('#pidtext').val());
	var txnlistsize = parseInt($('#totalpurchasetext').val());
	
	$('#firstidtext').val("");
	
	for(var loop = 0;loop < txnlistsize;loop++)
		{
		var currenttxnid = $('#headerid'+loop).val();
		var recvstatus =  $('#recvstatus'+loop).val();
		
		if(parseInt(currenttxnid) == parseInt(selectedtxnid))
			{
			var purchaseid = $('#headerid'+loop).val();
			var purchasecode = $('#purchasecodetext'+loop).val();
			
			$('#firstidtext').val(purchaseid+"$$$"+purchasecode);
			
			$('#purchaseorderidcheck'+loop).attr('checked', true);
			
			if(parseInt(recvstatus) >=3)
				{
				$('#recvbtn').prop('disabled', true);
				}
			else
				{
				$('#recvbtn').prop('disabled', false);
				}
			}
		else
			{
			$('#purchaseorderidcheck'+loop).attr('checked', false);
			}
		
		}	
	
}

function getPurchaseList(purchaseorderid,purchasecode)
{
	/*$('#receivelistDT').DataTable().ajax.reload();
	$('#itemlistDT').DataTable().ajax.reload();*/

	$('#postrongid').text(purchasecode)
	$.ajax({
		type:'GET',
		contentType:'application/json',
		url:'receivelist/'+purchaseorderid+'/'+purchasecode,
		success:function(data,textStatus,jqXHR)
		{
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			
			if(responseType == 'S')
				{
				 var receiveList = responseValue['purchaseorderList']
					$('#receivelistDT').DataTable( {
						"destroy": true,
						"bPaginate": false,
				    	data: receiveList,
				        columns: [
				            { data: 'purchaseid' },
				            { data: 'receiveddate' },
				            { data: 'billno' },
				            { data: 'grandtotal' },
				            {data:'action'}
				        ]
			            
				    } );	
					 
				 
				 var billList = responseValue['billList']
					$('#billDT').DataTable( {
						"destroy": true,
						"bPaginate": false,
				    	data: billList,
				        columns: [
				            { data: 'billno' },
				            { data: 'receiveddate' },
				            { data: 'status' },
				            { data: 'paymenttype' },
				            { data: 'chequeno' },
				            { data: 'grandtotal' },
				            { data: 'paidamt'},
				            { data: 'balanceamt' },
				            { data: 'paidtime'}
				           
				        ]
			            
				    } );
				 
				 getitemGrid();
				  
				 if(receiveList.length > 0)
					 {
					 var object = receiveList[0];
					 
					 /*setsupplierdetails(object);*/
					 setBillItem(object);
					 }
				 else
					 {
					 var object = new Object();
					 object.purchaseid='0';
					 object.purchasecode='0';
					 	
					 }
				 
				/* if(billList.length > 0)
				 {
				 var billobject = billList[0];
				 $('#selectedbillnotext').text(billobject.billno);
				 }*/
			 
				 
				 selectedrowObj = object;
				 setItemDetails(object);	
				 
				}
			
			$('#subtotallabel').text(responseValue['subtotal']);
			$('#taxamtlabel').text(responseValue['taxamt']);
			$('#totalalbel').text(responseValue['grandtotalamt']);
			$('#tmp_entity_date').text(responseValue['purchasedate'])
			$('#tmp_ref_number').text(responseValue['refno']);
			$('#tmp_due_date').text(responseValue['recievedate']);
			
			$('#discountvaluelabel').text(object.discountvalue);
			 $('#discounttypelabel').text(object.discounttype);
			 $('#discountamtlabel').text(object.discountamt);
			 $('#shippingamtlabel').text(object.shippingamt);
		}
	});		 
}

function setsupplierdetails(object)
{
	$('#suppliernametext').text(object.suppliername);
	$('#tmp_shipping_address').text(object.address)
	$('#tmp_shipping_city').text(object.city)
	$('#tmp_shipping_state').text(object.state)
	$('#tmp_shipping_zipcode').text(object.zipcode)
	$('#tmp_shipping_country').text(object.country)
	$('#tmp_entity_number').text("#"+object.purchasecode)
}
function selectpayment(referenceno,totalamount,headerid)
{
	  
	$('#payment_refno').val(referenceno);
	$('#paymentdatetext').val($.datepicker.formatDate("dd/mm/yy", new Date()));
	$('#payingamount').val(totalamount);
	$('#headerid').val(headerid);
}

function updatereceiveorderpayment()
{
	console.log("saras");
	var paymentobj = new Object();
	paymentobj.paidamount = $('#payingamount').val();
	paymentobj.precid = selectedrowObj.purchaseid;
	
	paymentobj.receivedate = $.datepicker.formatDate("dd/mm/yy", new Date());
	paymentobj.bank = $('#banknameselect').val();
	paymentobj.chequeno = $('#chequenotext').val();
	paymentobj.paymenttype = $('#paymenttypeselect').val();
	
	paymentobj.oprn = "UPD_PAYMT";
	paymentobj.purchaseorderno = selectedrowObj.purchasecode;
	paymentobj.userid = $('#inventoryuseridtext').val();
	
	
	if(paymentobj.paidamount == 0)
		{
		alert("Purchase order is already paid")
		}
	else if(parseFloat($('#tobepayamount').val()) < parseFloat(paymentobj.paidamount))
		{
		alert("Amount must be less than or equal to "+$('#tobepayamount').val());
		return false;
		}
	else
		{
		
	/*console.log("paymentobj     "+paymentobj)*/
	$.ajax({
		type:'GET',
		contentType:'application/json',
		success:function(data,textStatus,jqXHR)
		{
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			 alert(responseValue)
				window.location.href="purchaseorderview?pid="+$('#pidtext').val();
				 
				 
			 
		},
		type:'POST',
		url:'updatereceiveorderpayment',
		data:JSON.stringify(paymentobj)
	});	
		}
	
	
}

function getPurchaseid(purchaseid,receive_status,index,listSize)
{
	for(loop=0;loop<listSize;loop++)
		{
		if(loop != index)
			{
			$('#purchaseorderidcheck'+loop).attr('checked', false);
			
			
			
			}
		else
			{
			var purchaseid = $('#headerid'+loop).val();
			var purchasecode = $('#purchasecodetext'+loop).val();
			
			$('#firstidtext').val(purchaseid+"$$$"+purchasecode);
			}
	    }
	
	if(receive_status == 3)
		{
		/*alert("Item already fully Received")
		selectedid = 0;
		$('#purchaseorderidcheck'+index).attr('checked', false); // Checks it
*/		
		 $('#recvbtn').prop('disabled', true);
		}
	else
		{
		selectedid = purchaseid;	
		$('#recvbtn').prop('disabled', false);
		$('#purchaseorderidcheck'+index).attr('checked', true); // Unchecks it
		}
		
	refreshpurchaselist();
}

function receivestockprocess()
{
	window.location.href ="receivestock?pid="+selectedid	
}

function redirecttoReturn()
{
window.location.href = "purchasereturn?pid="+selectedrowObj.purchaseid+"&phid="+$('#pidtext').val();;	
}

function loadpurchasereturn()
{
	var purchaseorderid = getParameterByName('pid');
	var purchaseheaderid = getParameterByName('phid');
	$('#pidtext').val(purchaseorderid);
	$('#phidtext').val(purchaseheaderid);
	
	$('#returndatetext').val($.datepicker.formatDate("dd/mm/yy", new Date()));
	
	$.ajax({
		type:'GET',
		url:'getpurchasereturndata/'+purchaseorderid,
		contentType:'application/json',
		success:function(data,textStatus,jqXHR)
		{
			 var responseType = getResponseType(data);
			 var responseValue = getResponseValue(data);
			 
			 $('#returndatetext').val(responseValue['returnDate']);
			 $('#returnchargetext').val(responseValue['returnCharge']);
			 $('#returnnotestext').val(responseValue['notes']);
			 
			 if($('#returndatetext').val() == '')
				 {
				 $('#returndatetext').val($.datepicker.formatDate("dd/mm/yy", new Date()));
				 }
			 
			 var returnProductList = responseValue['returnList'];
			 
				$('#returnproductDT').DataTable( {
					"destroy": true,
					"bPaginate": false,
			    	data: returnProductList,
			        columns: [
			            { data: 'productname' },
			            { data: 'batchno' },
			            { data: 'sellprice' },
			            { data: 'productcount' },
			            { data: 'receivingqty' },
			            { data: 'bonusqty' },
			            { data: 'returnedqty' },
			            { data: 'returnqtydisp' },
			            { data: 'discountamt'},
			            { data: 'taxamt' },
			            { data: 'subtotal'},
			            { data: 'action'}
			        ]
		            
			    } );
				
				var parsed = JSON.parse(JSON.stringify(responseValue['returnList']));

				var arr = [];

				for(var x in parsed){
					addtoReturnProductArray(parsed[x]);
				}
		}
	});
}

function processreturn()
{
	
		/*var prodNotUpdate = 0;
		$.each(returnProductArray, function(index, value) {
			var itemObj = returnProductArray[index];
			
			if(itemObj.returnqty == 0)
				{
				prodNotUpdate = parseInt(prodNotUpdate)+1;
				}
		});
			if(parseInt(prodNotUpdate) > 0)			
		{
		alert("Please choose anyone Product");
		return false;
		}
	else
		{*/
		var returnPurchaseObj = new Object();
		returnPurchaseObj.itemlist = JSON.stringify(returnProductArray);
		console.log(returnProductArray);
		returnPurchaseObj.receiveddate = $('#returndatetext').val();
		returnPurchaseObj.returncharge = $('#returnchargetext').val();
		returnPurchaseObj.notes = $('#returnnotestext').val();
		returnPurchaseObj.purchaseid = $('#pidtext').val();
		returnPurchaseObj.oprn = 'RET_STK';
		console.log(returnPurchaseObj);
		
		$.ajax({
		type:'POST',
		url:'updatepurchasereturn',
		contentType:'application/json',
		data:JSON.stringify(returnPurchaseObj),
		success:function(data,textStatus,jqXWR)
		{
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			
				alert(responseValue)
				window.location.href="purchaseorderview?pid="+$('#phidtext').val();
				
		}
		});
		
		/*}*/
}

function updatereturnqty(index,returnqty,productcount,bonuscount,recvqty)
{
	console.log(returnqty);
	console.log("updatereturn quantity");
	var maxreturnqty = 0;
	var chkReturnQty = 0;
	var actualreturnqty = $('#actualreturnqtytext'+index).val();
	var billedqty = parseInt(recvqty)+parseInt(bonuscount);
	console.log("billedqty"+billedqty);
	console.log("productcount"+productcount);
	if(parseInt(billedqty) < parseInt(productcount))
		{
	maxreturnqty = billedqty;	
		}
	else
		{
		maxreturnqty = productcount;
		}
	
	if(parseInt(returnqty) > parseInt(maxreturnqty))
		{
		/*alert("Return Quantity must be less than "+maxreturnqty);*/
		alert("Return Quantity must be less than "+maxreturnqty);
		$('#returnqtytext'+index).val(0);
		return false;
		}
	/*else if(parseInt(returnqty) == 0)
		{
		alert("Return Quantity must not be Zero");
		$('#returnqtytext'+index).val(0);
		return false;
		}*/
	else
		{
		updateexistreturnqty(index);
		}
	console.log(JSON.stringify(returnProductArray));
}

function updateexistreturnqty(indexPosition)
{
	
	$("td input[name='returnqtyname"+indexPosition+"']").each(function(){
		var returnqty = ($(this).val());
		/*var returnqty = parseInt(enteredReturnqty) + parseInt($('#actualreturnqtytext'+indexPosition).val())*/
		console.log(returnProductArray);
		 var updateProdObj;
		 var removeItem;
		for (var indx in returnProductArray) {
			updateProdObj = (returnProductArray[indx])
     var prodArrayindex = updateProdObj.index;
     
     if(indexPosition == prodArrayindex)
    	 {
    	 removeItem = returnProductArray[indx];
    	 updateProdObj.returnqty = returnqty;
    	 updateProdObj.returndate = $('#returndatetext').val();
    	 updateProdObj.returnnotes = $('#returnnotestext').val();
    	 
    	 updateProdObj.returnqty = returnqty;
    	 console.log("sras"+updateProdObj.returnqty);
    	 returnProductArray = jQuery.grep(returnProductArray, function(value) {
  		   return value != removeItem;
  		 });
  		 
    	 updateProdObj.oprn = 'RET_STK';
    	 
    	 
    	 returnProductArray.push(updateProdObj);
    	 console.log(returnProductArray);
    	 break;
    	 }
     
   }
		
		 
	});
}

function addtoReturnProductArray(productObj)
{
	returnProductArray.push(productObj);	
		
}

function handlebilloperations(value)
{
	
	if(value == 'E')
		{
		
		window.location.href="receivestockedit?pid="+selectedrowObj.purchaseid
		}
	else if(value == 'A')
		{
		
		}
}
function getitemGrid()
{
	$('#receivelistDT tbody').on('click', '.edit.myBtnTab', function() {
		var table = $('#receivelistDT').DataTable();
		var receivelistDTObject = [];
		var editedObj = table.row( $(this).parents('tr') ).data();
		/*console.log("Edit Functionality happened")*/
		window.location.href="receivestockedit?pid="+editedObj.purchaseid
	});
	
	$('#receivelistDT tbody').on('click', '.delete.myBtnTab', function() {
		var table = $('#receivelistDT').DataTable();
		var receivelistDTObject = [];
		var editedObj = table.row( $(this).parents('tr') ).data();
		confirmBillDelete(editedObj);
		
	});
	
	$('#receivelistDT tbody').on('click', 'tr', function() {
		var table = $('#receivelistDT').DataTable();
		var receivelistDTObject = [];
		var tableRow = table.row($(this));
		
		receivelistDTObject.push(table.row($(this)).data());
		var object = receivelistDTObject[0];
		setBillItem(object);		 
				
	});
		
			
}

function setBillItem(object)
{

	 $('#paymentheaderid').val(object.purchaseid);
	 $('#payment_refno').val(object.referenceno);
	 $('#payingamount').val(object.tobepaid);
	 $('#tobepayamount').val(object.tobepaid);
	 $('#selectedbillnotext').text(object.billno);
	 $('#discountvaluelabel').text(object.discountvalue);
	 $('#discounttypelabel').text(object.discounttype);
	 $('#discountamtlabel').text(object.discountamt);
	 $('#shippingamtlabel').text(object.shippingamt);

	 
	 setsupplierdetails(object);
	 
	 if(object.tobepaid == 0)
		 {
		 $('#paymentsavebtn').prop('disabled',true);
		 }
	 else
		 {
		 $('#paymentsavebtn').prop('disabled',false);
		 }
selectedrowObj = object;

setItemDetails(object);
setBillDetails(object);
	
}

function confirmBillDelete(editedObj)
{
	$('.btn.btn-white').on('click',function()
			{
	
	$.ajax({
		type:'POST',
		contentType:'application/json',
		url:'deletebill/'+editedObj.purchaseid+"/"+editedObj.billno+"/"+$('#inventoryuseridtext').val(),
		success:function(data,textStatus,jqXHR)
		{
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			
			displayFailureMsg("DEL",responseValue);
			location.reload();
		}
	});	
			});
}

function setItemDetails(object)
{
	
	 
	$.ajax({
		type:'GET',
		contentType:'application/json',
		url:'receivelist/'+object.purchaseid+'/'+object.purchasecode,
		success:function(data,textStatus,jqXHR)
		{
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			
			if(responseType == 'S')
				{
				var itemList = responseValue['itemList']
				
				$('#itemlistDT').DataTable( {
					"destroy": true,
					"bPaginate": false,
					"bLengthChange": false,
			         "bFilter": false,
			         "bInfo": false,
			         "ordering":false,
			    	data: itemList,
			        columns: [
			            { data: 'index' },
			            { data: 'productname' },
			            { data: 'batchno' },
			            { data: 'recvqty' },
			            { data: 'bonusqty' },
			            { data: 'productprice' },
			            { data: 'recvingamt' }

			        ]
			        
			    } );
				
				$('#subtotallabel').text(responseValue['subtotal']);
				$('#taxamtlabel').text(responseValue['taxamt']);
				$('#totalalbel').text(responseValue['grandtotalamt']);
				$('#tmp_entity_date').text(responseValue['purchasedate'])
				$('#tmp_ref_number').text(responseValue['refno'])
				console.log(responseValue);
				console.log(responseValue['recievedate']);
				$('#tmp_due_date').text(responseValue['recievedate']);
				
				}
			}
	});		
}

function setBillDetails(object)
{
	 
	$.ajax({
		type:'GET',
		contentType:'application/json',
		url:'billdetailslist/'+object.purchaseid+'/'+object.billno,
		success:function(data,textStatus,jqXHR)
		{
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			
			if(responseType == 'S')
				{
				var billList = responseValue['billList']
				$('#billDT').DataTable( {
					"destroy": true,
					"bPaginate": false,
			    	data: billList,
			        columns: [
			            { data: 'billno' },
				            { data: 'receiveddate' },
				            { data: 'status' },
				            { data: 'paymenttype' },
				            { data: 'chequeno' },
				            { data: 'grandtotal' },
				            {data:'paidamt'},
				            { data: 'balanceamt' },
				            {data:'paidtime'}

			        ]
			        
			    } );
				
			
				}
			}
	});		
}

function selectpaymenttype()
{
var paymenttype = ($('#paymenttypeselect').val())
if(paymenttype == 4)
	{
	$('#chequenotext').prop('disabled',false);
	
	}
else
	{
	$('#chequenotext').prop('disabled',true);
	}

if(paymenttype == 1)
	{
	$('#banknameselect').prop('disabled',true);
	}
else
	{
	$('#banknameselect').prop('disabled',false);
	}
$('#chequenotext').val("")
}

function closepurchaseview()
{
window.location.href="purchaseorderhistory";	
}

function calsubtotal(index)
{
	var currdecimal = parseInt($('#currdecimaltext').val());
	var received = ($('#recvdqty'+index).val());
	var receiving = ($('#receivingqty'+index).val());
	var bonus = ($('#bonusqty'+index).val());
	var purchaseprice = ($('#purchaseprice'+index).val());
	var salesprice = ($('#salesprice'+index).val());
	var ordered = $('#ordered'+index).val();
	
	var allowedOrder = ordered - received;
	
	/*if(receiving > allowedOrder)
		{
		alert("Quantity must not be greater than "+allowedOrder);
		$('#receivingqty'+index).val(allowedOrder);
		return false;
		}*/
	var subtotal = receiving * purchaseprice;
	subtotal = parseFloat(subtotal);
	/*console.log("subtotal        "+subtotal)*/
	$('#subtotal'+index).text(subtotal);
	console.log($('#subtotal'+index).val());
	calculatetotal();
}

function findbarcode(index,productid)
{
	console.log(productid);
	var barcodevalue = ($('#batchno'+index).val());
	console.log(barcodevalue);

	$.ajax({
		type:'GET',
		contentType:'application/json',
		url:'pobarcode/'+productid+'/'+barcodevalue,
		success:function(data,textStatus,jqXHR)
		{
			console.log(data);
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			if(responseType == "S"){
				console.log(responseValue.barcode[0]["barcode"]);
				var barcode = responseValue.barcode[0]["barcode"];
				$("#barcode"+index).val(barcode);
				$("#barcode"+index).attr('readonly', true);
			}
			else{
				$("#barcode"+index).attr('readonly', false);
			}
			
		}
	});
	
}

function checkbarcode(index){
	var checkbarcodevalue = ($('#barcode'+index).val());
	$.ajax({
		type:'GET',
		contentType:'application/json',
		url:'pocheckbarcode/'+checkbarcodevalue,
		success:function(data,textStatus,jqXHR)
		{
			console.log(data);
			var responseType = getResponseType(data);
			if(responseType == "S"){
				alert("barcode already available");
				$('#barcode'+index).val('');
			}
			
		}
	});
}

function calculatetotal()
{
	/*var currsymbol = $('#currsymboltext').val();
	var currdecimal = parseInt($('#currdecimaltext').val());*/
	var currsymbol = "Rs."
	var currdecimal = 2;
	var totalcount = ($('#recvstockcount').val());
	var totalamount = 0;
	for(var index = 0;index<totalcount;index++)
		{
		var subtotal = parseFloat(($('#subtotal'+index).text()));
		totalamount = parseFloat(totalamount)+parseFloat(subtotal);
		}
	totalamount = totalamount.toFixed(currdecimal);
	
	$('#totalamountdisptext').text(currsymbol+" "+totalamount);
	
	var discountAmount = 0;
	var discountType = $('#discountselect').val();
	var discountValue = $('#discounttext').val();
	if(discountType == '%')
		{
		if(parseFloat(discountValue) > 100)
			{
			$('#discounttext').val(0);
			discountValue = 0;
			alert("Discount Percentage is not greater than 100");
			}
		discountAmount = parseFloat(totalamount) * (parseFloat(discountValue) / 100);
		}
	else if(discountType == 'Rs')
		{
		discountAmount = discountValue;
		}
	
	if(parseFloat(totalamount) < parseFloat(discountAmount))
		{
		$('#discounttext').val(0);
		discountAmount = 0;
		alert("Discount Amount is not greater than TotalAmount");
		/*return false;*/
		}
	discountAmount = parseFloat(discountAmount).toFixed(currdecimal);
	$('#discountamounttext').text(currsymbol+" "+discountAmount);
	
	var taxType = $('#taxselect').val();
	
	var taxSize = $('#totaltaxtext').val();
	var taxRate = 0;
	for(var loop = 0;loop < taxSize;loop++)
		{
		var taxTextname = "taxid"+loop;
		var taxid = $('#'+taxTextname).val();
		var taxrateidvalue = $('#'+("taxrate"+loop)).val();
		if(parseInt(taxType) == parseInt(taxid))
			{
			taxRate = taxrateidvalue;
			break;
			}
		
		}
	
	totalamount = parseFloat(totalamount) - parseFloat(discountAmount);
	
	var taxAmount = parseFloat(totalamount) * (parseFloat(taxRate) / 100);
	taxAmount = taxAmount.toFixed(currdecimal);
	$('#taxamounttext').text(currsymbol+" "+taxAmount);
	
	var shippingCost = $('#shippingcodttext').val();
	shippingCost = parseFloat(shippingCost).toFixed(currdecimal);
	$('#shippingcosttext').text(currsymbol+" "+shippingCost);
	
	/*console.log(totalamount + "   "+taxAmount + "   "+ shippingCost)*/
	var grandtotal = 0;
	/*grandtotal = (parseFloat(totalamount) + parseFloat(taxAmount) + parseFloat(shippingCost)) - parseFloat(discountAmount);*/
	grandtotal = (parseFloat(totalamount) + parseFloat(taxAmount) + parseFloat(shippingCost));
	grandtotal = parseFloat(grandtotal).toFixed(currdecimal);
	$('#grandtotaltext').text(currsymbol+" "+grandtotal);
}

function runreport()
{
	window.open = 'Contract/Print/';	
}

function generatepurchasereport()
{
var purchaseidcode = ($('#firstidtext').val()).split('$$$');
var printwindow = window.open('../../heroreports/forms/report/purchaseorderschedule/'+purchaseidcode[0]+"/"+$('#selectedbillnotext').text(),'_blank')
printwindow.print();
}

var billsList = [];
function loadbills()
{
var supplierid = $("#supplierselect option:first").val();
getBillList(supplierid);
}

function getBillList(supplierid)
{
	$.ajax({
		type:"GET",
		url:'billslist/'+supplierid,
		contentType:'application/json',
		success:function(data,textStatus,jqXHR)
		{
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			billsList = responseValue;
			
			$('#billsDT').DataTable( {
				"destroy": true,
				/*"bPaginate": false,*/
			    "bLengthChange": false,
			    /*"bFilter": true,*/
			    /*"bInfo": false,
			    "bAutoWidth": false,*/
				data: responseValue,
		        columns: [
		            { data: 'action' },
		            { data: 'purchaseid' },
		            { data: 'purchasecode' },
		            { data: 'billno' },
		            {data:'grandtotalamt'},
		            {data:'billdate'},
		            {data:'paidamount'},
		            {data:'status'}
		        ]
	            
		    } );
			
		}
	});
}

function checkAllBills()
{
	var table = $('#billsDT').DataTable();
	var info = table.page.info();
	var pageno = parseInt(info.page);
	var start = parseInt(pageno);
	var end = parseInt(start) + 10;
	
	if(end > billsList.length)
		{
		end = billsList.length;
		}
	
	var pageLength = parseInt(($("select[name=billsDT_length]").val()));
	var topCheck = $('#topcheck').prop('checked');
	for(var loop = start;loop < end; loop++)
		{
		if(topCheck)
			{
			
			$('#billselect'+loop).prop('checked',true);
			
			}
			else
			{
			
			$('#billselect'+loop).prop('checked',false);
			
			}
				
		}
}


function paybulkbill()
{
var bills = [];
var totalAmount = 0;

var table = $('#billsDT').DataTable();
var info = table.page.info();
var pageno = parseInt(info.page);
var start = parseInt(pageno);
var end = parseInt(start) + 10;

if(end > billsList.length)
	{
	end = billsList.length;
	}

var totalAmount = 0;


for(var loop = start;loop < end; loop++)
	{
	if($('#billselect'+loop).prop('checked'))
		{
		
		
		totalAmount += parseFloat($('#amounthidden'+loop).val());	
		}
			
	}
var currDecimal = $('#currdecimaltext').val();
$('#paymentdatetext').val($.datepicker.formatDate("dd/mm/yy", new Date()));
totalAmount = parseFloat(totalAmount).toFixed(currDecimal);
$('#totalbillamttext').val(totalAmount);
$('#payingamount').val(totalAmount);
resetPaypopupFields();
//$('#paybillbtnhidden').click();
$('#purchase_payment').modal('show');
}

function paybulkbillsave()
{
var tobeUpdateBillList = [];
var payingAmt = $('#payingamount').val();
var totalAmt = $('#totalbillamttext').val();

if(payingAmt == '')
	{
	alert("Please Enter Paying Amount");
	return false;
	}
else
	{
	if(parseFloat(payingAmt) != parseFloat(totalAmt))
		{
		alert("Paying Amont must be "+totalAmt);
		return false;
		}
	else
		{
		var table = $('#billsDT').DataTable();
		var info = table.page.info();
		var pageno = parseInt(info.page);
		var start = parseInt(pageno);
		var end = parseInt(start) + 10;

		if(end > billsList.length)
			{
			end = billsList.length;
			}
		for(var loop = start;loop < end; loop++)
		{
		if($('#billselect'+loop).prop('checked'))
			{
			var billObj = new Object();
			billObj.billno = $('#billselect'+loop).val();
			billObj.amount = $('#amounthidden'+loop).val();
			billObj.paiddate = $('#paymentdatetext').val();
			billObj.paymenttype = $('#paymenttypeselect').val();
			billObj.bankid = $('#banknameselect').val();
			billObj.chequeno = $('#chequenotext').val();
			billObj.userid = $('#inventoryuseridtext').val();
			
			tobeUpdateBillList.push(billObj);
			
			}
				
		}
		
		$.ajax({
			type:'POST',
			contentType:'application/json',
			url:'savebulkbill',
			data:JSON.stringify(tobeUpdateBillList),
			success:function(data,textStatus,jqXHR)
			{
				var responseType = getResponseType(data);
				var responseValue = getResponseValue(data);
				
				if(responseType == 'S')
					{
				$('#paymentsavebtnhide').click();
				alert(responseValue);
				window.location.href="bills";
					}
				
				
			}
		});
			
		}
	
	}
}

function resetPaypopupFields()
{
	$('#paymenttypeselect').val($('#paymenttypeselect option:first').val());
	$('#banknameselect').val($('#banknameselect option:first').val());
	$('#banknameselect').attr('disabled',true)
	$('#chequenotext').val('');
	$('#chequenotext').val('');
}

function gettxndetails(pageno)
{
	var pid = getParameterByName('pid');
$('#txrtable > tbody').html("");
$.ajax({
	type:'GET',
	contentType:'application/json',
	url:'loadpurchasepagewise/'+pageno+"/"+pid,
	success:function(data,textStatus,jqXHR)
	{
		var responseType = getResponseType(data);
		var responseValue = getResponseValue(data);

		$.each(responseValue, function(index, value) {
			/*console.log(value)*/
			
var markUp = "<tr> <td width='20px'>" +
		"<input type='radio' id='purchaseorderidcheck"+value.index +"' " +
		"onclick='getPurchaseid("+value.headerid+","+value.receive_status +","+value.index +","+responseValue.length+")'> " +
		"<input type='hidden' value='"+value.purchasecode+"' id='purchasecodetext"+value.index +"'></td>" +
		"<td onclick='getPurchaseList"+value.headerid+"','"+value.purchasecode+"')'>" +
		"<div>"+value.suppliername+" <span class='pull-right'> "+value.totalamt+" </span> </div>" +
		"<div class='leftTableAdd'><a> "+value.purchasecode+" </a>"+value.purchasedate+"" +
		"<span class='text-success pull-right'>"+value.receivestatusdesc+"</span></div>" +
		"<input type='hidden' id='headerid"+value.index +"' value='"+value.headerid +"'></td></tr>";
			
			$("#txrtable").find('tbody').append(markUp);
		});
		
		$('#totalpurchasetext').val(responseValue.length);
		
	}
});
}