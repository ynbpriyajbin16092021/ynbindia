/*Customer Master Start*/
var customeraddressArray = [];
var barcodeclick = 0;

function loadcustomer()
{
$.ajax({
	type:'GET',
	contentType:'application/json',
	url:'loadcustomers',
	success:function(data,textStatus,jqXHR)
	{
		var responseType = getResponseType(data);
		var responseValue = getResponseValue(data);
		
		if(responseType == 'S')
		{
			
		$('#customerDT').DataTable( {
			"destroy":true,
	    	data: responseValue,
	        columns: [
	            { data: 'customername' },
	            { data: 'customermobileno' },
	            { data: 'customeremail' },
	            { data: 'customergroup' },
	            { data: 'action' }
	        ]
            
	    } );
		
		selectCustomerItem();
		}
	}
});	
}

function selectCustomerItem() {
	$('#customerDT tbody').on('click', '.delete.myBtnTab', function() {
		var table = $('#customerDT').DataTable();
		var customerObject = [];
		customerObject.push(table.row( $(this).parents('tr') ).data());
		/*categoryObject.push(table.row(this).data());*/
		/*console.log(customerObject)*/
		var object = customerObject[0];
		
		var table = $('#customerDT').DataTable();
		confirmProductDelete(table.row($(this).parents('tr')),object);
		
	});
	$('#customerDT tbody').on('click', '.edit.myBtnTab', function() {
		var table = $('#customerDT').DataTable();
		var customerObject = [];
		customerObject.push(table.row( $(this).parents('tr') ).data());
		/*categoryObject.push(table.row(this).data());*/
		
		var object = customerObject[0];
		window.location.href="addcustomer?customerid="+object['customerid'];
		
	});
}

function loadaddcustomer()
{
	var customerid = getParameterByName('customerid');
$.ajax({
	type:'GET',
	contentType:'application/json',
	url:'loadaddcustomer/'+customerid,
	success:function(data,textStatus,jqXHR)
	{
		var responseType = getResponseType(data);
		var responseValue = getResponseValue(data);
		
		var customerList = responseValue['customerList'];
		var customerAddressList = responseValue['customerAddressList'];
		
		var customerobject = customerList[0];
		
		if(customerid.length > 0)
			{
			$('#custidtext').val(customerid);	
			}
		
		$('#firstnametext').val(customerobject['customerfirstname']);
		$('#lastnametext').val(customerobject['customerlastname']);
		$('#emailidtext').val(customerobject['customeremail']);
		$('#mobilenotext').val(customerobject['customermobileno']);

		$('#groupselect').val(customerobject['customergroup']);
		$('#oprntext').val("UPD");
		
		for (var indx in customerAddressList) {
			var customerAAddressObj = customerAddressList[indx];
		addtoAddresstable(customerAAddressObj);
		}
	}
});	


}

function addcustomertolist()
{
	var indexPosition = customeraddressArray.length + 1;
	var streetaddress = '';
	var city = '';
	var state = '';
	var country = '';
	var zipcode = '';
    
	 var customerObj = new Object();
	    customerObj.streetaddress = streetaddress;
	    customerObj.city = city;
	    customerObj.state = state;
	    customerObj.country = country;
	    customerObj.zipcode = zipcode;
	    customerObj.index = indexPosition;
	    customerObj.customerid= $('#custidtext').val();
	    customerObj.oprn = "INS";
	    
	    addtoAddresstable(customerObj);
}

function addtoAddresstable(customerObj)
{
	/*var markup = "<tr><td><input type='checkbox' name='record'></td><td>" + name + "</td><td><input type='text' name='record' value='" + email + "'></td></tr>";*/
	var indexPosition = customerObj.index;
    var markup = "<tr style='height:40px'>" +
"<td><input type='text' class='form-control form-white form-Tabel' name='streetaddress"+indexPosition+"' value='' onchange='updateitem(1,"+indexPosition+")'></input></td>" +
"<td><input type='text' class='form-control form-white form-Tabel' name='city"+indexPosition+"' value='' onchange='updateitem(2,"+indexPosition+")'></td>" +
"<td><input type='text' class='form-control form-white form-Tabel' name='state"+indexPosition+"' value='' onchange='updateitem(3,"+indexPosition+")'></td>" +
"<td><input type='text' class='form-control form-white form-Tabel' name='country"+indexPosition+"' value='' onchange='updateitem(4,"+indexPosition+")'></td>" +
"<td><input type='text' class='form-control form-white form-Tabel' name='zipcode"+indexPosition+"' value='' onchange='updateitem(5,"+indexPosition+")'></td>" +
    							  			  "<td><input type='button' value='Del' onclick='deletecustomeraddress("+customerObj.index+")' " +
    							  			  		"name='record"+customerObj.index+"'></td>" +
    						"</tr>";
    /*$("table tbody").append(markup);*/
	$("#custaddresstable").find('tbody').append(markup);
    addtoCustomeraddressArray(customerObj);
    clearaddressfields();
}

function clearaddressfields()
{
	$("#streetaddresstext").val("");
	$("#citytext").val("");
	$("#statetext").val("");
	$("#countrytext").val("");
	$("#zipcodetext").val("");	
}
function updateitem(itemsno,index)
{
	 
	var idname="";
	if(parseInt(itemsno) == 1)
		{
		idname = "streetaddress";
		}
	else if(parseInt(itemsno) == 2)
		{
		idname = "city";
		}
	else if(parseInt(itemsno) == 3)
		{
		idname = "state";
		}
	else if(parseInt(itemsno) == 4)
		{
		idname = "country";
		}
	else if(parseInt(itemsno) == 5)
		{
		idname = "zipcode";
		}
	
	var bluredIdname = idname+index;
	updateitemvalue(bluredIdname,index,itemsno)
}

function updateitemvalue(bluredIdname,indexPosition,itemsno)
{
	
	$("td input[name="+bluredIdname+"]").each(function(){
		
		var enteredvalue = ($(this).val());
		 var updateAddrObj;
		 var removeItem;
		for (var indx in customeraddressArray) {
			
	updateAddrObj = (customeraddressArray[indx])
     var cusaddrsArrayindex = updateAddrObj.index;
     
     if(indexPosition == cusaddrsArrayindex)
    	 {
    	 removeItem = customeraddressArray[indx];
    	 
    	 if(parseInt(itemsno) == 1)
    		 {
    		 updateAddrObj.streetaddress = enteredvalue;	 
    		 }
    	 else if(parseInt(itemsno) == 2)
    		 {
    		 updateAddrObj.city = enteredvalue;	 
    		 }
    	 else if(parseInt(itemsno) == 3)
    		 {
    		 updateAddrObj.state = enteredvalue;	 
    		 }
    	 else if(parseInt(itemsno) == 4)
    		 {
    		 updateAddrObj.country = enteredvalue;	 
    		 }
    	 else if(parseInt(itemsno) == 5)
    		 {
    		 updateAddrObj.zipcode = enteredvalue;	 
    		 }
    	 
    	 customeraddressArray = jQuery.grep(customeraddressArray, function(value) {
  		   return value != removeItem;
  		 });
  		 
  		 customeraddressArray.push(updateAddrObj);
  		 
    	 break;
    	 }
     
   }
	});
}

function deletecustomeraddress(index)
{
	$("table tbody").find("input[name='record"+index+"']").each(function(){
    	/*if($(this).is(":checked")){*/
            $(this).parents("tr").remove();
        /*}*/
            

    		
    		 var updateAddrObj;
    		 var removeItem;
    		for (var indx in customeraddressArray) {
    			
    	updateAddrObj = (customeraddressArray[indx])
         var cusaddrsArrayindex = updateAddrObj.index;
         
         if(index == cusaddrsArrayindex)
        	 {
        	 removeItem = customeraddressArray[indx];
        	
        	 
        	 customeraddressArray = jQuery.grep(customeraddressArray, function(value) {
      		   return value != removeItem;
      		 });
      		 
	      	break;
        	 }
         
       }
    });	
}
function addtoCustomeraddressArray(customerObj)
{
	customeraddressArray.push(customerObj);	
		
}

function savecustomer()
{
	if(customeraddressArray.length == 0)
		{
		alert("Please Enter atleast one Address");
		return false;
		}
	else
		{
		var customerObj = new Object();
		customerObj.customerid = $('#custidtext').val();
		customerObj.customerfirstname = $('#firstnametext').val();
		customerObj.customerlastname = $('#lastnametext').val();
		customerObj.emailid = $('#emailidtext').val();
		customerObj.mobileno = $('#mobilenotext').val();
		customerObj.userid = $('#inventoryuseridtext').val();
		customerObj.oprn = oprn = $('#oprntext').val();
		customerObj.customergroupid = $('#groupselect').val();
		
		var status = "active";
		if($('#myonoffswitch1').prop('checked'))
			{
			status = "active";
			}
		else
			{
			status = "inactive";
			}
		customerObj.status = status;
		
		customerObj.addresslist = JSON.stringify(customeraddressArray);
		/*alert(JSON.stringify(customerObj.addresslist))*/
		$.ajax({
			type:'POST',
			contentType:'application/json',
			url:'savecustomer',
			data:JSON.stringify(customerObj),
			success:function(data,textStatus,jqXHR)
			{
				var responseType = getResponseType(data);
				var responseValue = getResponseValue(data);
				
				if(responseType == 'S')
					{
					alert(responseMsg(responseValue));
					/*clearcustomerdatas();	
					$('#customertoggleclosebtn').click();*/
					location.reload(false);
					}
				else
					{
					alert(responseMsg(responseValue));
					}
				/*window.location.href="customer"*/
			}
		});
		}

}

function clearcustomerdatas()
{
	$('#custidtext').val('0');
	$('#firstnametext').val('');
	$('#lastnametext').val('');
	$('#emailidtext').val('');
	$('#mobilenotext').val('');
	/*$('#groupselect').val($("#groupselect option:first").val());*/
	$('#groupselect').val('1');
	
}
/*Customer Master End*/

var productFilteredItems = [];

/*Autocomplete was developed here*/

var currdecimal = 2;
var currsymbol="";

function loadpos()
{
	/*getproduct(2);*/
	focuscursor('#productsearchtext');
	var storeid = $('#storeidtext').val();
	if($('#currdecimaltext').val() != '')
		{
		currdecimal = parseInt($('#currdecimaltext').val());	
		}
	currsymbol = $('#currsymboltext').val();
	
	var posid = getParameterByName('posid');
	var type = getParameterByName('type');
	
	$('#posidtext').val(posid);
	
	if(parseInt(posid) > 0)
		{
		setValuestoPOS(posid,storeid,type);
		$('#savesalebtn').attr('style','display:none');
		$('#saveorderbtn').attr('style','display:none');
		}
	else
		{
		$('#savesalebtn').attr('style','display:""');
		$('#saveorderbtn').attr('style','display:""');
		}
	
	var customerid = $('#customeridtext').val();
	if(parseInt(customerid) == 0)
		{
		$('#custnametext').text("POS Customer");
		$('#defgrouptext').text("Walk-in");	
		}
	
}

function getBarCodeValue(){
	
	 $(document).scannerDetection({
		    
		  //https://github.com/kabachello/jQuery-Scanner-Detection

		    timeBeforeScanTest: 200, // wait for the next character for upto 200ms
		    avgTimeByChar: 40, // it's not a barcode if a character takes longer than 100ms
		  endChar: [13],
		  //preventDefault: true, //this would prevent text appearing in the current input field as typed 
		        onComplete: function(barcode, qty){
		   		console.log("Encryption     "+barcode);
		   		barcodeclick=1;
		    } // main callback function 
		});
}

function setValuestoPOS(posid,storeid,type)
{
	
	$.ajax({
		type:'GET',
		url:'getposdetails/'+posid+'/'+storeid+'/'+type,
		contentType:'application/json',
		success:function(data,textStatus,jqXHR)
		{
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			var posMainList = responseValue['posMainList'];
			var posOrderList = responseValue['posOrderList'];
			var storeList = responseValue['storeList'];
			
			if(posMainList.length > 0)
				{
				var posMainObj = posMainList[0];
				
				$('#oprntext').val("UPD");
				
				if(parseInt(posMainObj.custid) > 0)
					{
					$('#customersearchtext').val(posMainObj.custname);	
					}
				
				$('#customeridtext').val(posMainObj.custid);
				$('#customeraddressidtext').val(posMainObj.deliveryaddress)
				customerDetails();
				$('#grandtotaltext').text(currsymbol+" "+parseFloat(posMainObj.totalamount).toFixed(currdecimal));
				$('#netamounttext').text(currsymbol+" "+parseFloat(posMainObj.netamount).toFixed(currdecimal));
				
				totalTax = 0;
				var posTaxList = responseValue['posTaxList'];
				for(var taxloop = 0;taxloop < posTaxList.length;taxloop++)
				{
					var taxObj = posTaxList[taxloop];
					var taxamount = parseFloat(taxObj.taxamount).toFixed(currdecimal);
				$('#taxamount'+taxloop).val(taxamount);
				$('#taxvalue'+taxloop).text(currsymbol+" "+taxamount);
				totalTax = parseFloat(totalTax) + parseFloat(taxamount);
				}
				
				$('#totalamounttext').text(currsymbol+" "+(parseFloat(posMainObj.totalamount) + parseFloat(totalTax)).toFixed(currdecimal));
				$('#discamountlabel').text(currsymbol+" "+parseFloat(posMainObj.discountamount));
				$('#discselect').val(posMainObj.discounttype);
				$('#discamttext').val(posMainObj.discounttypevalue);
				$('#discountamount').val(posMainObj.discountamount);
				$('#cardnumbertext').val(posMainObj.cardnumber);
				$('#cardtype').val(posMainObj.cardtype);
				$('#cardtenderedamount').val(posMainObj.paidamount);
				$('#banknameselect').val(posMainObj.bankid);
				$('#chequenotext').val(posMainObj.chequeno);
				$('#chequetenderedamount').val(posMainObj.paidamount);
				
				if(posMainObj.salestype == 'S')
					{
					$('#returnevtbtn').attr('style','display:none');
					$('#payevtbtn').attr('style','display:""');	
					}
				else
					{
					$('#returnevtbtn').attr('style','display:""');
					$('#payevtbtn').attr('style','display:none');
					}
				
				if(parseFloat(posMainObj.discounttypevalue) > 0)
					{
					showdisclabel();	
					}
				else
					{
					hidedisclabel();
					}
				
				var purchaseList = responseValue['posProdList'];
				$.each(purchaseList, function(index, value) {
					var productObj = purchaseList[index];
					purchaseItems.push(productObj);
					addinstoretable(productObj);
					updatepurchaseqty(index,productObj.purchaseqty);
				});
				
				if(posOrderList.length > 0)
					{
					$('#salesordertable').attr('style','display:""');
					}
				$.each(posOrderList, function(index, value) {
					var productObj = posOrderList[index];
					orderItems.push(productObj);
					addordertable(productObj);
					updateorderqty(index,productObj.purchaseqty);
				});
				
				$('#tenderedamt').val(posMainObj.paidamount);
				
				
				if(posMainObj.paymenttype == 1)
					{
					$('#cashstylediv').attr('style','background-color:#000000');
					}
				else if(posMainObj.paymenttype == 2 || posMainObj.paymenttype == 3)
					{
					$('#cardstylediv').attr('style','background-color:#000000');
					}
				else if(posMainObj.paymenttype == 4)
				{
				$('#chequestylediv').attr('style','background-color:#000000');
				}
				
				$('#storenametext').text(storeList[0].value);
				$('#locationtext').text(storeList[0].label);
				
				}
			
		}
	});	
}
function showProductSearch()
{
		$(this).closest('.inputFildHalf').addClass("big").find("i").addClass("fa fa-close ");
		$(this).closest('.inputFildHalf').find("span.input-group-addon").addClass("closeAuto");
		$(this).closest('.TetFildSearch').addClass("searFull");
		$(".ProdectSearch").show();
	
}

function hideProductSearch()
{
	$(".ProdectSearch").hide();
	$(this).closest('.inputFildHalf').removeClass("big");
	$(this).closest('.TetFildSearch').removeClass("searFull");
} 

var rowClick = 0;
function customerDetails()
{
	console.log("cusotomer pos.js");
	var customerid = $('#customeridtext').val();
	if(parseInt(customerid) > 0)
		{
		
	$.ajax({
		type:'GET',
		contentType : 'application/json',
		url:'getcustomerdetail/'+customerid,
		success:function(data,textStatus,jqXHR)
		{
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			console.log(responseValue);
			var customerList = responseValue['customerList'];
			var customerBalanceList = responseValue['customerBalanceList'];
			var posList = responseValue['posList'];
			var customerTipList = responseValue['customerTipList'];
			var currencysymbol = $("#currsymboltext").val();
			
			var address = "",index = 1;
			for(var loop =0;loop<customerList.length;loop++)
				{
				var custObj = customerList[loop];
				$('#grouptdtext').text(custObj.customergroup);
				$('#emailtdtext').text(custObj.email);
				$('#mobiletdtext').text(custObj.mobile);
				$('#customermobiletext').val(custObj.mobile);
				
				address += index+" ) "+custObj.address + " , "
				index++;
				
				$('#custnametext').text($('#customersearchtext').val());
				$('#defgrouptext').text(custObj.customergroup);
				
				$('#customerlogoclass').attr('class','fa fa-user');
				}
			$('#addresstdtext').text(address);
			
			var balanceObj = customerBalanceList[0];
			var balanceamt = parseFloat(balanceObj.balanceamt).toFixed(currdecimal);
			var creditAmount = parseFloat(balanceObj.creditamount).toFixed(currdecimal);
			
				$('#balanceduetdtext').text(currencysymbol+" "+parseFloat(balanceamt) * -1);
				$('#credittdtext').text(currencysymbol+" "+creditAmount);
			
			$('#balanceid').text($('#balanceduetdtext').text());
			
			/*$('#customerPurchasesDT').DataTable( {
				"destroy":true,
				"searching": false,
		    	data: posList,
		        columns: [
		            { data: 'poscode' },
		            { data: 'salesat' },
		            { data: 'amount' },
		            { data: 'amount' }
		        ]
	            
		    } );*/
			/*var purchaseDIVhtml = "";
			$.each(posList, function(index, value) {
				purchaseSelectedObj = (value)
purchaseDIVhtml += ('<span class="bold" style="width: 100px;" '
		+'onclick="getpurchaseitems()">'+value.poscode+'</span>'+value.salesat+' '
		+'<span class="pull-right bold" align="left" >'+value.amount+'</span><br>');
			});	
			$('#purchaseDIV').html(purchaseDIVhtml);*/
			console.log("posList"+posList);
			$('#customerPurchasesDT').DataTable( {
				"destroy":true,
		    	data: posList,
		        columns: [
		            { data: 'poscode' },
		            { data: 'salesat' },
		            { data: 'custname' },
		            { data: 'storename' },
		            { data: 'posstatusdesc' },
		            { data: 'amount' }
		        ]
	            
		    } );
			rowClick = 0;
			selectPurchaseProdItem();
			
			var favTip = "Customer's favorite product:";
			
			$.each(customerTipList, function(index, value) {
				var tipObj = customerTipList[index];
				favTip += tipObj.productname +" "+tipObj.batchid+" "+tipObj.salescount+"<br>"
				 
			});		
			favTip += "<b>Balance Due</b> "+$('#balanceduetdtext').text()+"<br>";
			favTip += "<b>Credit Due</b> "+$('#credittdtext').text()+"<br>";
			
			$('#customertipdiv').html(favTip);
			
			var custAddressList = responseValue['custAddressList'];
			/*$('#customeraddressDT').DataTable( {
				"destroy":true,
		    	data: custAddressList,
		        columns: [
		            { data: 'selectcustaddress' },
		            { data: 'address' }
		        ]
	            
		    } );*/
			$("#customeraddressDT > tbody").html("");
			$.each(custAddressList, function(index, value) {
				var itemObj = value;
				
				var addressmarkup = "<tr>" +
				"<td>" +
				"<input type='radio' id='custaddressradio"+index+"' name='custaddress' value='"+itemObj.custaddressid+"' onclick='setcustaddress(this.value);'/></td>" +
				"<td>" + itemObj.address + "</td>" +
				"</tr>";
				$("#customeraddressDT").find('tbody').append(addressmarkup);
				 
			});
			
			for(var loop = 0;loop<custAddressList.length;loop++)
			{
			var custdelvaddressloopid = parseInt($('#custaddressradio'+loop).val());
			var delvaddressid = parseInt($('#customeraddressidtext').val());
			
			if(custdelvaddressloopid == delvaddressid)
				{
				$('#custaddressradio'+loop).attr('checked',true);
				}
			else
				{
				$('#custaddressradio'+loop).attr('checked',false);
				}
			}
		}
	});
	
}
	else
		{
		$('#customerlogoclass').attr('class','fa fa-plus-circle');
		}
}

function selectPurchaseProdItem() {
	
	$('#customerPurchasesDT tbody').on('click', 'tr', function() {
rowClick = parseInt(rowClick) + 1;
		
		if(parseInt(rowClick) == 1)
			{
		var table = $('#customerPurchasesDT').DataTable();
		var purchaseorderObject = [];
		purchaseorderObject.push(table.row( $(this) ).data());
		/*categoryObject.push(table.row(this).data());*/
		 
		var object = purchaseorderObject[0];
		/*console.log(object)*/
		getpurchaseitems(object);
			}
		
	});
	
}

function getpurchaseitems(purchaseSelectedObj)
{
	$('#customerPurchasesProdDT').DataTable( {
		"destroy":true,
    	data: purchaseSelectedObj.posProductList,
        columns: [
            { data: 'productname' },
            { data: 'batchno' },
            { data: 'salescount' },
            { data: 'subtotal' }
        ]
        
    } );
	rowClick = 0;
}

function getproduct(tprid)
{
	$.ajax({
		type:'GET',
		url:'getproductfromtxr/'+tprid+'/'+$('#inventoryuseridtext').val(),
		contentType:'application/json',
		success:function(data,textStatus,jqXHR)
		{
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			
			var productObject = responseValue[0];
			
			if(responseValue.length > 0)
				{
				setitemtoPurchaseDT(productObject);	
				}
			else
				{
				displayAlertMsg("Product not Avail in stock")
				}
		}
	});
}

function setcustaddress(addressid)
{
$('#customeraddressidtext').val(addressid);	
$('#closepopup').click();

}
var purchaseItems = [];
var returnItems = [];
var orderItems = [];
var dummyorderItems = [];
var selectedItemObj; 
function setitemtoPurchaseDT(productObject)
{
	console.log("order items length :"+orderItems.length);
	var formvalid = true;
	
	selectedItemObj = productObject;
	
	if(parseInt(productObject.productcount) > 0)
		{
		$.each(purchaseItems, function(index, value) {
			/*var itemObj = purchaseItems[index];*/
			var itemObj = value;
	
			if(itemObj.productid == productObject.productid && itemObj.batchno == productObject.batchno)
				{
				/*alert("Item "+productObject.productname+" is Already Added");*/
				formvalid = false;
				$('#productsearchtext').val('');
				}
			 
		});		
		}
	else if(parseInt(productObject.productcount) == 0)
		{
		
		$.each(dummyorderItems, function(index, value) {
			var itemObj = dummyorderItems[index];
			
			if(itemObj.productid == productObject.productid && itemObj.batchno == productObject.batchno)
				{
				/*alert("Item "+productObject.productname+" is Already Added");*/
				formvalid = false;
				$('#productsearchtext').val('');
				}
			 
		});	
		}
	
/*	console.log("formvalid      "+formvalid)*/
	if(formvalid)
		{
		formvalid = false;
		/*console.log("productcount     "+productObject.productcount)*/
		productObject.index = (purchaseItems.length);
		if(parseInt(productObject.productcount) > 0)
			{
			purchaseItems.push(productObject);	
			}
		else
			{
			var customerid = $('#customeridtext').val();
			if(parseInt(customerid) > 0)
				{
				
			dummyorderItems.push(productObject);
			
				}
			$('#orderconfirmbtn').click();
			}
		
		hideProductSearch();
		
		if(parseInt(productObject.productcount) > 0)
			{
			addinstoretable(productObject);	
			}
		else
			{
			//addordertable(productObject);
			}
		
		}
	$('#productsearchtext').val('');
}

function makeOrder()
{
	var customerid = $('#customeridtext').val();
	if(parseInt(customerid) > 0)
		{
		orderItems.push(selectedItemObj);
		dummyorderItems.push(selectedItemObj);
		$('#salesordertable').attr('style','display:""');
		addordertable(selectedItemObj);	
		
		$('#custaddressbtn').click();
		}
	else
		{
		displayAlertMsg("Registered Customers can only make an Order.");
		}
}

function makeOrderCancel()
{
	var indexPosition = selectedItemObj.index;
	var updateProdObj;
	 var removeItem;
	for (var indx in dummyorderItems) {
		
updateProdObj = (dummyorderItems[indx])
var prodArrayindex = updateProdObj.index;

if(indexPosition == prodArrayindex)
	 {
	 removeItem = dummyorderItems[indx];
	 
	 dummyorderItems = jQuery.grep(dummyorderItems, function(value) {
		   return value != removeItem;
		 });
		 
 	break;
	 }
}
}

function addinstoretable(productObject)
{
	var posid = $('#posidtext').val();
	/*var indexPosition = productObject.index;*/
	var indexPosition = purchaseItems.length - 1;
	
	var deleteBtn = "";
	
	if(parseInt(posid) == 0)
		{
				deleteBtn = "<input type='button' value='Del' onclick='deletepurchaseproduct("+indexPosition+")' name='record"+indexPosition+"'>";
		}
	else
		{
		deleteBtn = "";
		}
	
	
	var markup = "<tr>" +
	"<td>" + productObject.productname + "</td>" +
	"<td>" + productObject.batchno + "</td>" +
	"<td><input type='number' id='purchasequantity"+indexPosition+"' min='1' value='1' onblur='updatepurchaseqty("+indexPosition+",this.value)'></td>" +
	"<td>" + parseFloat(productObject.price).toFixed(currdecimal) + "</td>" +
	"<td id='purchasesubtotal"+indexPosition+"'>" + parseFloat(productObject.price).toFixed(currdecimal) + "</td>" +
	"<td>" +deleteBtn+"</td>" +
	"</tr>";
    $("#instoretable").find('tbody').append(markup); 
    /*console.log(markup);*/
    calcgrandtotal(productObject);
    
    focuscursor('#purchasequantity'+indexPosition);
}

function addordertable(productObject)
{
	var posid = $('#posidtext').val();
	var indexPosition = productObject.index;
	
	var deleteBtn = "";
	
	if(parseInt(posid) == 0)
		{
				deleteBtn = "<input type='button' value='Del' onclick='deleteorderproduct("+indexPosition+")' name='record"+indexPosition+"'>";
		}
	else
		{
		deleteBtn = "";
		}
	
	
	var markup = "<tr>" +
	"<td>" + productObject.productname + "</td>" +
	"<td>" + productObject.batchno + "</td>" +
	"<td><input type='number' id='orderquantity"+indexPosition+"' value='1' min='1' onblur='updateorderqty("+indexPosition+",this.value)'></td>" +
	"<td>" + productObject.price + "</td>" +
	"<td id='purchasesubtotal"+indexPosition+"'>" + productObject.price + "</td>" +
	"<td>" +deleteBtn+"</td>" +
	"</tr>";
    $("#ordertable").find('tbody').append(markup); 
    
    calcgrandtotal(productObject);	
    
    focuscursor('#orderquantity'+indexPosition);
}

function addreturnitems(productObject)
{
	var posid = $('#posidtext').val();
	var indexPosition = productObject.index;
	
	var deleteBtn = "";
	
	if(parseInt(posid) == 0)
		{
				/*deleteBtn = "<input type='button' value='Del' onclick='deletepurchaseproduct("+indexPosition+")' name='record"+indexPosition+"'>";*/
		deleteBtn = "";
		}
	else
		{
		deleteBtn = "";
		}
	
	
	var markup = "<tr>" +
	"<td>" + productObject.productname + "</td>" +
	"<td>" + productObject.batchno + "</td>" +
	"<td><input type='number' class='allownumericwithoutdecimal' max='"+productObject.purchaseqty+"' min='"+productObject.productcount+
	"' id='purchasequantity"+indexPosition+"' value='"+productObject.purchaseqty+"' onblur='updatereturnqty("+indexPosition+",this.value)'></td>" +
	"<td>" + parseFloat(productObject.price).toFixed(currdecimal) + "</td>" +
	"<td id='purchasesubtotal"+indexPosition+"'>" + parseFloat(parseFloat(productObject.price) * -1).toFixed(currdecimal) + "</td>" +
	"<td>" +deleteBtn+"</td>" +
	"</tr>";
    $("#instoretable").find('tbody').append(markup); 
    
    calcgrandtotal(productObject);	
}

var subtotal = 0;
var taxamt = 0,totalTax = 0,netAmount = 0,totalAmount=0,discountamount=0;
var currency = "",paymenttype="";

function calcgrandtotal(productObject)
{
	subtotal = 0;
	totalTax = 0;
	totalAmount = 0;
	netAmount = 0;
	discountamount = 0;
	
/*	console.log(purchaseItems);*/
	currency = currsymbol;
	$.each(purchaseItems, function(index, value) {
		var itemObj = purchaseItems[index];
		subtotal = parseFloat(subtotal) + (parseFloat(itemObj.price) * parseInt(itemObj.purchaseqty));
		
	});	
	
	$.each(orderItems, function(index, value) {
		var itemObj = orderItems[index];
		subtotal = parseFloat(subtotal) + (parseFloat(itemObj.price) * parseInt(itemObj.purchaseqty));
		
	});	
	subtotal = parseFloat(subtotal).toFixed(currdecimal);
	/*console.log("subtotal     "+subtotal)*/
	var subtotalText = currency+" "+subtotal;
	$('#grandtotaltext').text(subtotalText);
	
	for(var taxloop = 0;taxloop < $('#taxlistsizetext').val();taxloop++)
		{
		taxamt = (parseFloat(subtotal) * parseFloat($('#taxrate'+taxloop).val()))/100;
		taxamt = taxamt.toFixed(currdecimal);
		$('#taxamount'+taxloop).val(taxamt);
		$('#taxvalue'+taxloop).text(currency+" "+taxamt);
		totalTax = parseFloat(totalTax) + parseFloat(taxamt);
		}
	currency = currsymbol;
	
	totalTax = totalTax.toFixed(currdecimal);
	
	totalAmount = (parseFloat(subtotal) + parseFloat(totalTax)).toFixed(currdecimal);
	
	$('#totalamounttext').text(currency+" "+totalAmount);
	
	discountamount = $('#discountamount').val();
	
	netAmount = ( (parseFloat(subtotal) + parseFloat(totalTax) + parseFloat($('#shippingcosttext').val()))
			- parseFloat(discountamount) ).toFixed(currdecimal);
	
	$('#netamounttext').text(currency+" "+netAmount);

	$('#tobepaidtd').text(currency+" "+netAmount);
	$('#cardtenderedamount').val(netAmount);
	$('#tenderedamt').val(netAmount);
	$('#chequetenderedamount').val(netAmount);
}

function discchange()
{
	var disctype = $('#discselect').val();
	var discountamt = $("#discamttext").val();
	if(parseFloat(discountamt) > 0)
		{
		
		showdisclabel();
		
		var calcValid = true;
		
		if(disctype == 'F')
			{
			if(parseFloat(discountamt) > parseFloat(totalAmount))
				{
				displayAlertMsg("Discount Amount is not gretaer than "+totalAmount)
				$('#discamountlabel').text(0);
				$('#discamttext').val(0);
				calcValid = false;
				hidedisclabel();
				}
			else
				{
				$('#discamountlabel').text(currency+" "+discountamt);	
				}
			
			}
		else if(disctype == 'P')
			{
			if(parseFloat(discountamt) > 100)
				{
				displayAlertMsg("Discount Percentage is not gretaer than 100")
				$('#discamountlabel').text(0);
				$('#discamttext').val(0);
				calcValid = false;
				hidedisclabel();
				}
			else
				{
				discountamt = (parseFloat(totalAmount) * parseFloat(discountamt))/100;
				discountamt = discountamt.toFixed(currdecimal);
				$('#discamountlabel').text(currency+" "+discountamt);	
				}
			
			}
		}
	else
		{
		hidedisclabel();
		}
	
	if(calcValid)
		{
		netAmount =  (parseFloat(totalAmount) + parseFloat($('#shippingcosttext').val())) - parseFloat(discountamt);
		
		netAmount = netAmount.toFixed(currdecimal);
		
		$('#netamounttext').text(currency+" "+netAmount);	
		}
	$("#discountamount").val(discountamt);
	if(disctype == 'P')
		{
		$('#disclabel').text("Discount "+$('#discamttext').val()+" %");	
		}
	else
		{
		$('#disclabel').text("Discount Rs. "+$('#discamttext').val());
		}
	$('#tobepaidtd').text(currency+" "+netAmount);
	$('#cardtenderedamount').val(netAmount);
	$('#tenderedamt').val(netAmount);
	$('#chequetenderedamount').val(netAmount);
	
}

function showdisclabel()
{
	$('#disclabel').attr('style','display:""');
	$('#discamountlabel').attr('style','display:""');
	
	$('#totalamtlabel').attr('style','display:""');
	$('#totalamounttext').attr('style','display:""');
}

function hidedisclabel()
{
	$('#disclabel').attr('style','display:none');
	$('#discamountlabel').attr('style','display:none');
	
	$('#totalamtlabel').attr('style','display:none');
	$('#totalamounttext').attr('style','display:none');
}

function paybuttonclick()
{
	var customerid = $('#customeridtext').val();
	/*if(customerid == '')
		{
		alert("Please Enter CustomerId")
		}
	else*/ if(purchaseItems.length == 0 && orderItems.length == 0)
	{
		displayAlertMsg("Select anyone Product")
	return false;
	}
	else if(parseInt(customerid) > 0)
	{
		if(orderItems.length > 0 && $('#customeraddressidtext').val() == 0)
		{
			/*alert("Please Select Delivery Address");
			return false;*/
			$('#custaddresshdebtn').click();
		}
		else
			{
			$('#paybutton').click();
			}
		}
else
	{
	$('#paybutton').click();
	}
}

function addressselect()
{
	$('#customerAddressPopupClose').click();
	
	netAmount = ( (parseFloat(subtotal) + parseFloat(totalTax) + parseFloat($('#shippingcosttext').val()))
			- parseFloat(discountamount) ).toFixed(currdecimal);
	
	$('#tobepaidtd').text(currency+" "+netAmount);
	$('#cardtenderedamount').val(netAmount);
	$('#tenderedamt').val(netAmount);
	$('#chequetenderedamount').val(netAmount);
	$('#paybutton').click();
}

function returnbuttonclick()
{
	$('#tenderedamt').val(netAmount);
	paymenttype = "0";
	savepos();
}

function updatepurchaseqty(indexPosition,purchaseqty)
{
	 var updateProdObj;
	 var removeItem;
	for (var indx in purchaseItems) {
			
	updateProdObj = (purchaseItems[indx])
	
     var prodArrayindex = updateProdObj.index;
	/*prodArrayindex = parseInt(prodArrayindex) + 1;*/
	
     if(indexPosition == prodArrayindex)
    	 {
    	 removeItem = purchaseItems[indx];
    	 var oldEnteredQty = updateProdObj.purchaseqty;
    	 
    	 if(parseInt(updateProdObj.productcount) >= purchaseqty)
    		 {
    		 updateProdObj.purchaseqty = purchaseqty;	
    		 $('#purchasequantity'+indexPosition).val(purchaseqty);
    		 }
    	 else
    		 {
    		 displayAlertMsg("Total available product in stock is "+updateProdObj.productcount);
    		 updateProdObj.purchaseqty = oldEnteredQty;
    		 $('#purchasequantity'+indexPosition).val(oldEnteredQty);
    		 }
    	 
    	 var subtotal = parseFloat(updateProdObj.price) * parseInt(purchaseqty);
    	 
    	 updateProdObj.subtotal = subtotal.toFixed(currdecimal);
    	 
    	 $('#purchasesubtotal'+indexPosition).text(updateProdObj.subtotal);
    	 
    	 calcgrandtotal(updateProdObj);
    	 
    	 purchaseItems = jQuery.grep(purchaseItems, function(value) {
  		   return value != removeItem;
  		 });
  		  
    	 
    	 purchaseItems.push(updateProdObj);
  		 
    	
    	 
    	 break;
    	 }
     
   }
	
}

function deletepurchaseproduct(indexPosition)
{
	console.log("indexPosition   "+indexPosition);
	$("#instoretable").find("input[id='purchasequantity"+indexPosition+"']").each(function(){
			 
            $(this).parents("tr").remove();
        
    		var quantity = ($(this).val());
    		 var updateProdObj;
    		 var removeItem;
    		for (var indx in purchaseItems) {
    			
    	updateProdObj = (purchaseItems[indx])
         var prodArrayindex = updateProdObj.index;
    	prodArrayindex = parseInt(prodArrayindex);
         console.log(indexPosition+"   "+prodArrayindex);
         if(indexPosition == prodArrayindex)
        	 {
        	 removeItem = purchaseItems[indx];
        	 
        	 purchaseItems = jQuery.grep(purchaseItems, function(value) {
      		   return value != removeItem;
      		 });
      		 
	      	break;
        	 }
         
       }
    		calcgrandtotal(updateProdObj);
    });	
	
}

function updateorderqty(indexPosition,purchaseqty)
{
	 var updateProdObj;
	 var removeItem;
	for (var indx in orderItems) {
			
	updateProdObj = (orderItems[indx])
	
     var prodArrayindex = updateProdObj.index;
     
     if(indexPosition == prodArrayindex)
    	 {
    	 removeItem = orderItems[indx];
    	 updateProdObj.purchaseqty = purchaseqty;
    	 
    	 $('#orderquantity'+indexPosition).val(purchaseqty);
    	 
    	 var subtotal = parseFloat(updateProdObj.price) * parseInt(purchaseqty);
    	 
    	 updateProdObj.subtotal = subtotal.toFixed(currdecimal);
    	 
    	 $('#purchasesubtotal'+indexPosition).text(updateProdObj.subtotal);
    	 
    	 
    	 
    	 calcgrandtotal(updateProdObj);
    	 
    	 orderItems = jQuery.grep(orderItems, function(value) {
  		   return value != removeItem;
  		 });
  		 
    	 orderItems.push(updateProdObj);
  		 
    	 break;
    	 }
   }
}

function deleteorderproduct(indexPosition)
{
	
	$("#ordertable").find("input[id='orderquantity"+indexPosition+"']").each(function(){
	console.log(indexPosition);
            $(this).parents("tr").remove();
        
    		var quantity = ($(this).val());
    		 var updateProdObj;
    		 var removeItem;
    		for (var indx in orderItems) {
    			
    	updateProdObj = (orderItems[indx])
         var prodArrayindex = updateProdObj.index;
         
         if(indexPosition == prodArrayindex)
        	 {
        	 removeItem = orderItems[indx];
        	 
        	 orderItems = jQuery.grep(orderItems, function(value) {
      		   return value != removeItem;
      		 });
      		 
	      	break;
        	 }
         
       }
    		calcgrandtotal(updateProdObj);
    });	
	
}

function updatereturnqty(indexPosition,purchaseqty)
{
	 var updateProdObj;
	 var removeItem;
	for (var indx in purchaseItems) {
			
	updateProdObj = (purchaseItems[indx])
	
     var prodArrayindex = updateProdObj.index;
     
     if(indexPosition == prodArrayindex)
    	 {
    	 removeItem = purchaseItems[indx];
    	 var oldEnteredQty = updateProdObj.purchaseqty;
    	 
    	 var purchaseCount = parseInt(updateProdObj.productcount) * -1;
    	 var purchaseqtyCopy = parseInt(purchaseqty) * -1;
    	 
    	 if(parseInt(purchaseCount) >= purchaseqtyCopy)
    		 {
    		 updateProdObj.purchaseqty = purchaseqty;	
    		 $('#purchasequantity'+indexPosition).val(purchaseqty);
    		 }
    	 else
    		 {
    		 displayAlertMsg("You can return upto "+purchaseCount);
    		 updateProdObj.purchaseqty = oldEnteredQty;
    		 $('#purchasequantity'+indexPosition).val(oldEnteredQty);
    		 }
    	 
    	 var subtotal = parseFloat(updateProdObj.price) * parseInt(purchaseqty);
    	 
    	 updateProdObj.subtotal = subtotal.toFixed(currdecimal);
    	 
    	 $('#purchasesubtotal'+indexPosition).text(updateProdObj.subtotal);
    	 
    	 calcgrandtotal(updateProdObj);
    	 
    	 purchaseItems = jQuery.grep(purchaseItems, function(value) {
  		   return value != removeItem;
  		 });
  		  
    	 
    	 purchaseItems.push(updateProdObj);
  		 
    	
    	 
    	 break;
    	 }
     
   }
	
}

function calctendamt()
{
var tenderedamt = $('#tenderedamt').val();
if(tenderedamt == '')
	{
	displayAlertMsg("Enter Amount")
	}
/*else if(parseFloat(tenderedamt) < parseFloat(netAmount))
	{
	alert("Your NetAmount is "+netAmount+" Amount must be greater than "+netAmount)
	}*/
else
	{
	var balance = parseFloat(tenderedamt) - parseFloat(netAmount);
	/*console.log(tenderedamt+"   "+netAmount+"   "+balance)*/
	balance = balance.toFixed(currdecimal);
	$('#balanceamttext').text(currency+" "+balance);
	/*$('#tendbutton').click();*/	
	
	savepos();
	}

}

function savepos()
{
var pos = new Object();
var tax = new Object();
var taxAmountArray = [];
var customerid = $('#customeridtext').val();
var prodobject;
var cardtype = $('#cardtype').val();

if(purchaseItems.length > 0)
	{
	prodobject = purchaseItems[0];
	}
else
	{
	prodobject = orderItems[0];
	}

	pos.mobile = $('#customermobiletext').val();
	pos.posid = $('#posidtext').val();
	pos.customerid = customerid;
	pos.productlist = JSON.stringify(purchaseItems);
	pos.orderlist = JSON.stringify(orderItems);
	pos.grandtotal = subtotal;
	/*pos.currencysymbol = prodobject.currencysymbol;*/
	pos.currencysymbol = $('#currsymboltext').val();
	if(parseInt(paymenttype) == 2)
		{
		pos.paymenttype = cardtype;
		}
	else
		{
		pos.paymenttype = paymenttype;	
		}
	pos.oprn = $('#oprntext').val();
	pos.totaltaxamount = totalTax;
	pos.paidamount = $('#tenderedamt').val();
	pos.storeid = $('#storeidtext').val();
	pos.discounttype = $('#discselect').val();
	pos.discounttypevalue = $('#discamttext').val();
	pos.cardnumber=$('#cardnumbertext').val();
	pos.cardtype = cardtype;
	pos.bankid = $('#banknameselect').val();
	pos.chequeno = $('#chequenotext').val();
	pos.deliveryaddressid = $('#customeraddressidtext').val();
	pos.salescode = $('#returnbillno').val();
	pos.poscode = $('#returnbillno').val();
	pos.shippingcost = $('#shippingcosttext').val();
	
	for(var taxloop = 0;taxloop < $('#taxlistsizetext').val();taxloop++)
	{
	tax = new Object();
	tax.taxrate = $('#taxrate'+taxloop).val();
	tax.taxid = $('#taxid'+taxloop).val();
	tax.taxamount = $('#taxamount'+taxloop).val();
	taxAmountArray.push(tax);
	}
	pos.taxamountdetails = JSON.stringify(taxAmountArray);
	pos.discount = $('#discountamount').val();
	pos.netamount = netAmount;
	pos.userid = $('#inventoryuseridtext').val();
	
	$.ajax({
		type:'POST',
		contentType:'application/json',
		data:JSON.stringify(pos),
		url:'savepos',
		success:function(data,textStatus,jqXHR)
		{
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			/*alert(responseType)
			if(responseType == 'S')
				{*/
			var inventoryResponse = $.parseJSON(JSON.stringify(data));
			var responseObj = $.parseJSON((JSON
					.stringify(inventoryResponse['inventoryResponse'])));
			var presponseValue = (responseObj['responseObj']);
			var preResponseValue;
			if(presponseValue instanceof Object)
				{
				preResponseValue = responseMsg(presponseValue);
				}
			 
				alert(responseValue)
				/*$('#invoicedonebtn').attr('disabled',true);*/
				var responseObject = $.parseJSON((JSON.stringify(presponseValue)));
				var generateId = responseObject['id'];
				
				$('#posidtext').val(generateId);
				
				if(parseInt(paymenttype) == 1)
				$('#tendbutton').click();
				else if(parseInt(paymenttype) == 2 || parseInt(paymenttype) == 3)
					{
					$('#cardclosebtn').click();
					$('#cashtendbutton').click();
					}
				else if(parseInt(paymenttype) == 4)
					{
					$('#cashtendbutton').click();
					}
				
				/*window.location.href="pos?posid=0"*/
				/*}*/
		}
	});
	

}

function invoicedone()
{
	window.location.href="pos?posid=0";	
}
function printinvoice()
{
	var posid = $('#posidtext').val();
	window.open('report/invoiceschedule/'+posid,'_blank')
}

function setpaymenttype(paytype,btnname)
{
	paymenttype = paytype;
	
	if(paytype == 1)
		{
		$('#paymenttypetdtext').text('Cash');
		$('#cashbtn').click();	
		}
	
	else if(paytype == 2 || paytype == 3)
		{
		/*if($('#cardtype').val() == 'C')*/
		if($('#cardtype').val() == 2)
			{
			$('#paymenttypetdtext').text('Credit Card');			
			}
		else
			{
			$('#paymenttypetdtext').text('Debit Card');
			}
		$('#cardbtn').click();	
		}
	
	else if(paytype == 4)
	{
	 
	$('#chequebtn').click();	
	}
		
}

function calccardtendamt()
{
var tenderedamt = $('#tenderedamt').val();
if($('#cardnumbertext').val() == '')
{
alert("Please Enter Card Number");
return false;
}
if(tenderedamt == '')
	{
	displayAlertMsg("Enter Amount");
	return false;
	} 
else
	{
	var balance = parseFloat(tenderedamt) - parseFloat(netAmount);
	/*console.log(tenderedamt+"   "+netAmount+"   "+balance)*/
	balance = balance.toFixed(currdecimal);
	$('#balanceamttext').text(currency+" "+balance);
		
	savepos();
	}

}

function setbankdetails(bankname)
{
	$('#paymenttypetdtext').text("Cheque "+bankname);
			
}

function setchequedetails()
{
	
}


function calcchequetendamt()
{
	$('#paymenttypetdtext').text("Cheque ");
var tenderedamt = $('#chequetenderedamount').val();
if($('#banknameselect').val() == '')
{
	displayAlertMsg("Please Select Bank");
return false;
}
if($('#chequenotext').val() == '')
	{
	displayAlertMsg("Enter Cheque No");
	return false;
	}
if(tenderedamt == '')
	{
	displayAlertMsg("Enter Amount");
	return false;
	} 
else
	{
	var balance = parseFloat(tenderedamt) - parseFloat(netAmount);
	
	balance = balance.toFixed(currdecimal);
	$('#balanceamttext').text(currency+" "+balance);
	
	savepos();
	}

}

function loadposhistory()
{
var storeid = $('#storeidtext').val();
	$.ajax({
		type:'GET',
		contentType:'application/json',
		url:'poshistorydetails/'+storeid,
		success:function(data,textStatus,jqXHR)
		{
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			
			if(responseType == 'S')
				{
				 
					$('#invoiceDT').DataTable( {
						"order": [[ 0, "desc" ]],
				    	data: responseValue,
				        columns: [
				            { data: 'poscode' },
			            {data: 'salesat'},
			            {data: 'customername'},
			            {data:'storename'},
			            {data:'netamtdisp'},
			            {data:'statusdesc'},
			            {data:'balanceamountdisp'},
			            {data:'action'}
				        ]
			            
				    } );	
					selectPOSItem();
					}
			 

		}
	});	
}

function selectPOSItem() {
	$('#invoiceDT tbody').on('click', '.delete.myBtnTab', function() {
		
		var table = $('#invoiceDT').DataTable();
		var posObject = [];
		posObject.push(table.row( $(this).parents('tr') ).data());
		/*categoryObject.push(table.row(this).data());*/
		 
		var object = posObject[0];
		
		var table = $('#invoiceDT').DataTable();
		confirmPOSDelete(table.row($(this).parents('tr')),object);
		
	});
	$('#invoiceDT tbody').on('click', '.edit.myBtnTab', function() {
		var table = $('#invoiceDT').DataTable();
		var invoiceObject = [];
		invoiceObject.push(table.row( $(this).parents('tr') ).data());
		/*categoryObject.push(table.row(this).data());*/
		
		var object = invoiceObject[0];
		
		/*window.location.href="pos?posid="+object['posid'];*/
		window.open("pos?posid="+object['posid']+"&type="+object['salestype'],'_blank')
		
	});
}
 
function confirmPOSDelete(tableRow,editedObj)
{
	$('.btn.btn-white').on('click',function()
			{
	
	$.ajax({
		type:'POST',
		contentType:'application/json',
		url:'deletepos/'+editedObj.posid,
		success:function(data,textStatus,jqXHR)
		{
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			
			displayFailureMsg("DEL",responseValue);
			if(responseType == 'S')
				{
				tableRow.remove().draw();	
				}
			
		}
	});	
			});
}

function clicknewsale()
{
	var confm = confirm("Do you want to create New Sale?");
	if(confm)
		{
		window.location.href="pos?posid=0";
		$('#newsaleidtoggle').click();	
		}
	
}

function clicksavesale()
{
	var changedOrderItemCount = 0 ;
if(orderItems.length == 0)
	{
	displayAlertMsg("Please make any one order")
	}
else
	{
	var totalorderItems = orderItems.length;
	$.each(orderItems, function(index, value) {
		var productObj = value;
		var prodcount = parseInt(productObj.productcount);
		
		if(prodcount > 0)
			{
			changedOrderItemCount = parseInt(changedOrderItemCount)+1;
			purchaseItems.push(productObj);
			deleteorderproduct(index);
			addinstoretable(productObj);
			updatepurchaseqty(index,productObj.purchaseqty);	
			}
		
	});
	if(changedOrderItemCount == totalorderItems)
		{
		$('#salesordertable').attr('style','display:none');
		$("#salesordertable > tbody").html("");
		orderItems = [];	
		}
	
	if(changedOrderItemCount == 0)
		{
		displayAlertMsg("No items to be moved for Order items")
		}
	
	$('#functiontoggle').click();
	}
}

function clickordersale()
{
	$("#ordertable > tbody").html("");
	var customerid = $('#customeridtext').val();
	if(parseInt(customerid) == 0)
		{
		displayAlertMsg("Registered Customers can only make an Order.");
		return false;
		}
	if(purchaseItems.length == 0)
	{
		displayAlertMsg("Please add anyone product");
	return false;
	}
else
	{
	
	$.each(purchaseItems, function(index, value) {
		var productObj = value;
	if(productObj != undefined)
		{
		orderItems.push(productObj);
		/*deletepurchaseproduct(parseInt(index) + 1);*/
		deletepurchaseproduct(parseInt(index));
		addordertable(productObj);
		updateorderqty(index,productObj.purchaseqty);
		}
			 
		
	});
	/*console.log(orderItems.length)*/
	$('#salesordertable').attr('style','display:""');
	
	/*$("#instoretable > tbody").html("");*/
    
	purchaseItems = [];
	$('#functiontoggle').click();
	}
}

function settotenderedamount(amount)
{
	$('#tenderedamt').val(amount);	
}

function setcardtype()
{
	if($('#cardtype').val() == 'C')
	{
	$('#paymenttypetdtext').text('Credit Card');			
	}
else
	{
	$('#paymenttypetdtext').text('Debit Card');
	}	
}

function openreturnpopup()
{
	$('#returnbillno').val("");
	billList = [];
}

var billList = [];
function getbillitemdetils()
{
var returnbillno = $('#returnbillno').val();
$.ajax({
	type:'GET',
	url:'getbilldetails/'+returnbillno,
	contentType:'application/json',
	success:function(data,textStatus,jqXHR)
	{
		var responseType = getResponseType(data);
		var responseValue = getResponseValue(data);
		billList = responseValue;
		
		$('#returnDT').DataTable( {
			"destroy":true,
			"ordering": false,
	        "info":     false,
	        "bLengthChange": false,
	    	data: billList,
	        columns: [
	        { data: 'selprodid' },
            {data: 'productname'},
            {data: 'productcode'},
            {data: 'salesqty' },
            {data:'subtotal'}
	        ]
            
	    } );	
		/*var billList = responseValue;
		$.each(billList, function(index, value) {
			var billObj = value;
			var billMarkup = "<tr style='height:40px'>" +
		"<td><input type='checkbox' id='selprodid"+index+"' value='"+billObj.productid+"' ></td>" +
		"<td>"+billObj.productname+"</td>" +
		"<td>"+billObj.productcode+"</td>" +
		"<td>"+billObj.price+"</td>" +
		"</tr>";
		
		$("#returnDT").find('tbody').append(billMarkup);
		
		});*/
		
		$('#returnDT_paginate').attr('style','display:none');
	}
});
}

function savereturnitems()
{
	purchaseItems = [];
	orderItems = [];
	
	var table = $('#returnDT').DataTable();
	var billListLength = (table.data().count());
	var selectProdCount = 0;
	$.each(billList, function(index, value) {
		
		if($('#selprodid'+index).prop('checked'))
			{
			var prodid = ($('#selprodid'+index).val());
			var prodObj = value;
			selectProdCount = parseInt(selectProdCount) + 1;
			purchaseItems.push(prodObj);
			addreturnitems(prodObj);
			}
		});	
	
	if(selectProdCount == 0)
		{
		displayAlertMsg("Please Select anyone Product");
		return false;
		}
	$('#oprntext').val("RETN");
	$('#returnpopupclose').click();
	$('#returnevtbtn').attr('style','display:""');
	$('#payevtbtn').attr('style','display:none');
	 
}

function loadorderhistory()
{
$.ajax({
	type:'GET',
	url:'getcustomerorders/'+$('#storeidtext').val(),
	contentType:'application/json',
	success:function(data,textStatus,jqXHR)
	{
		var responseType = getResponseType(data);
		var responseValue = getResponseValue(data);
		
		if(responseType == 'S')
			{
			$('#ordersDT').DataTable( {
				"destroy":true,
		    	data: responseValue,
		        columns: [
		        { data: 'poscodehtml' },
	            {data: 'billdate'},
	            {data: 'custname'},
	            {data:'address'},
	            {data:'paymentstatus'},
	            {data:'balanceamt'},
	            {data:'orderstatus'}/*,
	            {data:'action'}*/
		        ]
	            
		    } );
			 
			}
	}
});	
}

var orderdetaillist = [];

function clickposcode(posid,index,status)
{
	$("#orderlistDT > tbody").html("");
	$('#posidtext').val(posid);
	$('#indexidtext').val(index);
	
	$.ajax({
		type:'GET',
		url:'getorderitems/'+posid,
		contentType:'application/json',
		success:function(data,textStatus,jqXHR)
		{
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			if(responseType == 'S')
				{
				 if(parseInt(status) == 5)
					 {
					 $('#ordersavebtn').attr('style','display:""')
					 }
				 else
					 {
					 $('#ordersavebtn').attr('style','display:none')
					 }
				 $('orderstatuselect').val(status);
				 
				$.each(responseValue, function(index, value) {
				var productObject = value;
				var indexPosition = productObject.index;
				
				orderdetaillist = responseValue;
				
				var markup = "<tr>" +
				"<td>" + productObject.productname + "</td>" +
				"<td>" + productObject.batchids + "</td>" +
				"<td>" + productObject.stockqty+"</td>" +
				"<td>" + productObject.purchaseqty + "</td>" +
				"<td>" + productObject.price + "</td>" +
				"<td>" + productObject.subtotal +"</td>" +
				"</tr>";
			    $("#orderlistDT").find('tbody').append(markup); 
			    
			    $('#grandtotaltext').text(productObject.currency+" "+productObject.grandtotal);
			    $('#taxamounttext').text(productObject.currency+" "+productObject.taxamount);
			    
			    if(parseFloat(productObject.discamount) > 0)
			    	{
			    	$('#discounttd').attr('style','display:""')
			    	}
			    else
			    	{
			    	$('#discounttd').attr('style','display:none')
			    	}
			    
			    $('#discounttext').text(productObject.currency+" "+productObject.discamount);
			    $('#totalamounttext').text(productObject.currency+" "+productObject.netamount);
			    $('#paidamounttext').text(productObject.currency+" "+productObject.paidamount);
			    
				});
				
				$('#order-list-popup').modal('show');
				}
		}
	});
	
}

function saveorders()
{
	var posid = $('#posidtext').val();
	var index = $('#indexidtext').val();
	saveorderstatus(index,posid);
}

function saveorderstatus(index,posid)
{
var deliverystatus = $('#orderstatuselect').val();
if(parseInt(deliverystatus) != 5)
	{
	
	var processOrder = 0;
	
	var orderlist = [];
	$.each(orderdetaillist,function(index,value){
		var productObject = value;
		
		if(productObject.purchaseqty > productObject.stockqty)
			{
			processOrder = parseInt(processOrder) + 1;
			}
		
		var order = new Object();
		order.batchno = $('#batchid'+index).val();
		order.purchaseqty = productObject.purchaseqty;
		order.productid = productObject.productid;
		order.deliverystatus = deliverystatus;
		order.posid = $('#posidtext').val();
		order.userid = $('#inventoryuseridtext').val();
		order.storeid = $('#storeidtext').val();
		
		orderlist.push(order);
		
	});
	
	if(parseInt(processOrder) == 0)
		{
		$.ajax({
			type:'POST',
			contentType:'application/json',
			url:'saveorderstatus',
			data:JSON.stringify(orderlist),
			success:function(data,textStatus,jqXHR)
			{
				var responseType = getResponseType(data);
				var responseValue = getResponseValue(data);
				if(responseType == 'S')
					{
					alert(responseValue);
					window.location.href="orderhistory";
					}
			}
		});
		}
	else
		{
		displayAlertMsg("Currently Product is not available in stock");
		return false;
		}
	}
else
	{
	displayAlertMsg("Restricted to Update as Pending status");
	return false;
	}
}


function generateinvoicereport(posid)
{
	var printwindow = window.open('report/invoiceschedule/'+posid,'_blank');
	printwindow.print();
}

function opennewcustomerpanel()
{
	var customerid = $('#customeridtext').val();
	if(parseInt(customerid) == 0)
		{
$('#customeraddbtn').click();
		}
}