var selectedrowObj;
var productArray = [];
var returnProductArray = [];
var productLabels = [];
var dishArray = [];

function loadpurchaseorder()
{
	getPurchaseorderitem(0);
}
function close(){
	$('#productModal').modal("hide");
}


function openConfirmationModal(){
	$('#productModal').modal("show");
	/*if(dishArray.length > 0){
		$('#productModal').modal("show");
	}else{
		displayFailureMsg("","Dish Details not Available, Please Add Dish and continue");
	}*/
	
}

function loadpurchaseRequest()
{
	getPurchaseRequestitem(0);
}

function viewPurchaseReqeustProducts(puchaseid){
	
    $.ajax({
	type:'GET',
	contentType:'application/json',
	url:'getPurchaseRequestDishList/'+puchaseid,
	success:function(data,textStatus,jqXHR)
	{
	
		var responseType = getResponseType(data);
		var responseValue = getResponseValue(data);
		var dishList=responseValue["dishList"];
		console.log(dishList)
		if(responseType == "S"){
			$('#productListModal').modal("show");
			
			$('#dishDT').DataTable( {
				"order": [[ 0, "desc" ]],
		    	data: dishList,			   
		        columns: [
		            { data: 'dishname' },
		            { data: 'dishcount' },
		            { data: 'createddate' }	
		        ]		            
		    } );	
		}		
	}
})

}

function closeRequestmodal(){
	$('#productListModal').modal("hide");
	window.location.href="purchaserequesthistory";
}

function rejectConfirmation(){
	if($("#notestext").val() != "" && $("#notestext").val() != null){
		$('#productModal').modal("show");
	}else{
		displayFailureMsg("","Reason is mandatory for Rejection");
	}
}

function getPurchaseRequestitem(purchaseorderid)
{
	
	$.ajax({
		type:'GET',
		contentType:'application/json',
		url:'purchaserequestlist/'+purchaseorderid,
		success:function(data,textStatus,jqXHR)
		{
		
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			console.log(responseValue)
			if(responseType == 'S')
				{
				if(purchaseorderid == '0')
					{

				$('#purchaseRequestDT').DataTable( {
						"order": [[ 0, "desc" ]],
				    	data: responseValue,
				    	dom: 'Bfrtip',
				        buttons: [
				             'excel', 'pdf'
				        ],
				        columns: [
				            { data: 'purchaseid' },
				           /* { data: 'purchasecode' },*/
				            { data: 'purchasecodenavigate' },
				            { data: 'purchasestatusdesc' },
				            { data: 'purchasedate' }			          
				          /*  { data: 'action' }*/
				        ]
			            
				    } );	
					//selectPurchaseorderItem();
					}
			/*	else
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
					
					
					
					var itemList = responseValue['itemList'];
					var indexPosition = 0;
					for (var indx in itemList) {
						var itemObj = itemList[indx];
						indexPosition++;
				
						var fulluomsel = itemObj.uomsel.fulluomsel;
						var looseuomsel = itemObj.uomsel.looseuomsel;
						var uompackingid = itemObj.uompacking;
						fulluomsel = fulluomsel.replace('uompackingsel','fulluomsel'+indexPosition);
						var calcloosecountmethod = "calculatelooseqty('"+indexPosition+"',1)";
						fulluomsel = fulluomsel.replace('calculatelooseqty()',calcloosecountmethod);
						
						looseuomsel = looseuomsel.replace('uompackingsel','looseuomsel'+indexPosition);
						looseuomsel = looseuomsel.replace('calculatelooseqty()',calcloosecountmethod);
						
						var markup = "<tr><td>" + itemObj.productname + "</td><td><input type='number' id='fulluomqty"+indexPosition+"' " +
								"value='"+itemObj.fulluomqty+"' style='width:50px;' onblur=calculatelooseqty('"+indexPosition+"',1);>&nbsp;"+fulluomsel+"</td>" +
								"<td><input type='number' id='looseuomqty"+indexPosition+"' value='"+itemObj.looseuomqty+"' style='width:50px;' " +
								"onblur=calculatelooseqty('"+indexPosition+"',1); />&nbsp;"+looseuomsel+"</td>" +
			    				"<td><input type='number' id='looseqty"+indexPosition+"' name='quantity"+indexPosition+"' value='"+itemObj.quantity+"' onblur='updatequantity("+indexPosition+")'" +
			    						" disabled style='width:75px';><input type='hidden' id='uompackingselect"+indexPosition+"' value='"+uompackingid+"' /></td>" +
			    				"<td><input class='btn white-bg fa-input color-font' type='button' value='&#xf044;' onclick='editproduct("+indexPosition+")'>" +
			    						"<input class='btn white-bg fa-input red-font' type='button' value='&#xf014;' onclick='deleteproduct("+indexPosition+")' " +
			    								"name='record"+indexPosition+"'></td></tr>";
			    $("table > tbody").append(markup);
			    
			     productArray.push(itemObj);
			    $('#fulluomsel'+indexPosition).val(itemObj.fulluomsel);
			    $('#looseuomsel'+indexPosition).val(itemObj.looseuomsel);
			   
					}
					}*/
				 
				}

		}
	});	
	
}



function viewProducts(puchaseid){
	
	    $.ajax({
		type:'GET',
		contentType:'application/json',
		url:'getPurchaseDishList/'+puchaseid,
		success:function(data,textStatus,jqXHR)
		{
		
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			var dishList=responseValue["dishList"];
			console.log(dishList)
			if(responseType == "S"){
				$('#productListModal').modal("show");
				
				$('#dishDT').DataTable( {
					"order": [[ 0, "desc" ]],
			    	data: dishList,			   
			        columns: [
			            { data: 'dishname' },
			            { data: 'dishcount' },
			            { data: 'createddate' }	
			        ]		            
			    } );	
			}		
		}
	})
	
}

function closemodal(){
	$('#productListModal').modal("hide");
	window.location.href="purchaseorderhistory";
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
				$('#purchaseorderDT').DataTable( {
			    	dom: 'Bfrtip',
			        buttons: [
			             'excel', 'pdf'
			        ]
				});
				
				if(purchaseorderid == '0')
					{

					/*$('#purchaseorderDT').DataTable( {
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
					selectPurchaseorderItem();*/
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
					
					
					
					var itemList = responseValue['itemList'];
					var indexPosition = 0;
					for (var indx in itemList) {
						var itemObj = itemList[indx];
						indexPosition++;
				
						var fulluomsel = itemObj.uomsel.fulluomsel;
						var looseuomsel = itemObj.uomsel.looseuomsel;
						var uompackingid = itemObj.uompacking;
						fulluomsel = fulluomsel.replace('uompackingsel','fulluomsel'+indexPosition);
						var calcloosecountmethod = "calculatelooseqty('"+indexPosition+"',1)";
						fulluomsel = fulluomsel.replace('calculatelooseqty()',calcloosecountmethod);
						
						looseuomsel = looseuomsel.replace('uompackingsel','looseuomsel'+indexPosition);
						looseuomsel = looseuomsel.replace('calculatelooseqty()',calcloosecountmethod);
						
						var markup = "<tr><td>" + itemObj.productname + "</td><td><input type='hidden' id='fulluomqty"+indexPosition+"' " +
								"value='"+itemObj.fulluomqty+"' style='width:50px;' onblur=calculatelooseqty('"+indexPosition+"',1);>&nbsp;"+fulluomsel+"</td>" +
								"<td><input type='hidden' id='looseuomqty"+indexPosition+"' value='"+itemObj.looseuomqty+"' style='width:50px;' " +
								"onblur=calculatelooseqty('"+indexPosition+"',1); />&nbsp;"+looseuomsel+"</td>" +
			    				"<td><input type='number' id='looseqty"+indexPosition+"' name='quantity"+indexPosition+"' value='"+itemObj.quantity+"' onblur='updatequantity("+indexPosition+")'" +
			    						"  style='width:75px';><input type='hidden' id='uompackingselect"+indexPosition+"' value='"+uompackingid+"' /></td>" +
			    				"<td><input class='btn white-bg fa-input color-font' type='button' value='&#xf044;' onclick='editproduct("+indexPosition+")'>" +
			    						"<input class='btn white-bg fa-input red-font' type='button' value='&#xf014;' onclick='deleteproduct("+indexPosition+")' " +
			    								"name='record"+indexPosition+"'></td></tr>";
			    $("table > tbody").append(markup);
			    
			     productArray.push(itemObj);
			    $('#fulluomsel'+indexPosition).val(itemObj.fulluomsel);
			    $('#looseuomsel'+indexPosition).val(itemObj.looseuomsel);
			   
					
					
					
					}
					}
				 
				}

		}
	});	
	
}







function editPurchaseHistory(purchaseid){
	window.location.href="addpurchaseorder?purchaseorderid="+purchaseid;
}

function confirmpurchaseorderDelete(purchaseid)
{
	$('.btn.btn-white').on('click',function()
			{
	  var remarks = $("#poRemarks").val();
	  
	  if(remarks != null && remarks != "" && remarks.length > 0){
		  var remarkObj = new Object();
			remarkObj.remarks = remarks;
			
		$.ajax({
			type:'POST',
			contentType:'application/json',
			url:'deletepurchaseorder/'+purchaseid,
			data : JSON.stringify(remarkObj),
			success:function(data,textStatus,jqXHR)
			{
				var responseType = getResponseType(data);
				var responseValue = getResponseValue(data);
		
				if(responseType == 'S'){
					  displaySuccessMsg(data);   
					  setTimeout("location.href = 'purchaseorderhistory'",2000); 
				}else if(responseType == 'F'){
					displayFailureMsg("",responseMsg(responseValue));
					 setTimeout("location.href = 'purchaseorderhistory'",2000); 
				}
				 
			}
		});	
	  }else{
		  displayFailureMsg("","Please enter remarks to continue this process")
	  }
		
		
	
	
			});
}


function getUOMforPacking()
{
	$(".changeContent").html("<img src='../resources/images/loader_green.gif' width='100%' />");
	
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

if(formvalid)
{	
	var uompacking = $('#uompackingselect').val();
	$.ajax({
		type:'GET',
		contentType:'application/json',
		url:'productinfo/'+ $('#productcodetext').val(),
		success:function(data,textStatus,jqXHR)
		{
			console.log(data.inventoryResponse.responseObj[0]["unitdesc"])
			var unitdesc  = data.inventoryResponse.responseObj[0]["unitdesc"]
			var indexPosition = productArray.length + 1;
			var name = $("#itemcodetext").val();
			var count = $("#itemcounttext").val();
			var productid = $('#productcodetext').val();
			var productEntry = 0;
			var unitrate  = data.inventoryResponse.responseObj[0]["unitrate"]
			var totalamount  = 0;
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
				var uompackingid = $("#uompackingselect").val();
				var selproductid = $("#productcodetext").val();
				var uomid = 'uompackingsel'+indexPosition;
				var fulluomsel = fulluompacks;
				var looseuomsel = looseuompacks;
				fulluomsel = fulluomsel.replace('uompackingsel','fulluomsel'+indexPosition);
				var calcloosecountmethod = "calculatelooseqty('"+indexPosition+"',1)";
				fulluomsel = fulluomsel.replace('calculatelooseqty()',calcloosecountmethod);
				
				looseuomsel = looseuomsel.replace('uompackingsel','looseuomsel'+indexPosition);
				looseuomsel = looseuomsel.replace('calculatelooseqty()',calcloosecountmethod);
				
				var markup = "<tr> " +
					"<td>" + name + " " +
					"<input type='hidden' id='fulluomqty"+indexPosition+"' value='1' style='width:50px;' " +
					"onblur=calculatelooseqty('"+indexPosition+"',1);>&nbsp;"+fulluomsel+" " +
					"<input type='hidden' id='looseuomqty"+indexPosition+"' value='0' style='width:50px;' onblur=calculatelooseqty('"+indexPosition+"',1); />&nbsp;"
					+looseuomsel+"<input type='hidden' id='uompackingselect"+indexPosition+"' value='"+uompackingid+"' /><input type='hidden' id='productcodetext"+indexPosition+"' value='"+selproductid+"' /></td>" +
					
					"<td><input type='number' id='looseqty"+indexPosition+"' name='quantity"+indexPosition+"' value='1' " +
					"onblur='updatequantity("+indexPosition+",1)'  style='width:80px';><span style='margin:5px;font-size: 16px;font-weight: bold;'>"+unitdesc+"</span></td>" +
					/*"<td><input class='btn white-bg fa-input color-font' type='button' value='&#xf044;' onclick='editproduct("+indexPosition+")'>" +*/
					"<td><input type='number' id='unitrate"+indexPosition+"' name='unitrate"+indexPosition+"'  disabled  value='"+unitrate+"' " +
					"onblur='updatequantity("+indexPosition+",1)'  style='width:80px';><span style='margin:5px;font-size: 16px;font-weight: bold;'></span></td>" +
					"<td><input type='number' id='amount"+indexPosition+"' name='amount"+indexPosition+"' value='"+totalamount+"' disabled" +
					" onblur='updatequantity("+indexPosition+",1)' style='width:80px';><span style='margin:5px;font-size: 16px;font-weight: bold;'></span></td>" +
					"<td><input class='btn white-bg fa-input red-font' type='button' value='&#xf014;' onclick='deleteproduct("+indexPosition+")' name='record"+indexPosition+"'>" +
					"</td></tr>";
				
			$('#uompackingsel'+indexPosition).hide()
		    $(markup).prependTo("table#addpurchaseDT > tbody");
			$("#itemcodetext").val("");
			$("#itemcounttext").val("1");
			$("#email").val("");
			$(".changeContent").hide()
			calculatelooseqty(indexPosition,0);
			
			
			}
			else
			{
			$("#itemcodetext").val('');
			$('#productcodetext').val('');
			displayFailureMsg("","Product "+name+" is Already added.")
			return false;
			}
			
		}
	});	
	
		}
else
	{
	displayFailureMsg("","Please Select anyone Product");
	$(".changeContent").hide();
	return false;
	}

}

function setuomobj(index)
{
//	var fulluomqty = $('#fulluomqty'+index).val();
//	var looseuomqty = $('#looseuomqty'+index).val();
//	var fulluomval = $('#fulluomsel'+index).val();
//	var looseuomval = $('#looseuomsel'+index).val();
//	
//	fulluomval = fulluomval.split("-");
//	looseuomval = looseuomval.split("-");
//	var looseuomsno = looseuomval[0];
//	var looseuom = looseuomval[1];
//	var fulluomsno = fulluomval[0];
//	var fulluom = fulluomval[1];
//	
//	var uomobj = new Object();
//	uomobj.fulluom = fulluom;
//	uomobj.fulluomsno = fulluomsno;
//	uomobj.looseuomsno = looseuomsno;
//	uomobj.looseuom = looseuom;
//	uomobj.fulluomqty = fulluomqty;
//	uomobj.looseuomqty = looseuomqty;
//	uomobj.uompacking=$('#uompackingselect'+index).val();
	
	//return uomobj;3
	
	return 1;
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
			
					//$('#looseqty'+index).val(looseqtyinuom[0]);
					$('#looseqty'+index).val(0)
					var name = $("#itemcodetext").val();
					var count = $("#itemcounttext").val();
					var productid = $('#productcodetext'+index).val();
					var productObj = new Object();
					productObj.index = index;
					productObj.fulluom=uomobj.fulluom;
					productObj.fulluomqty = uomobj.fulluomqty;
					productObj.looseuom = uomobj.looseuom;
					productObj.looseuomqty = uomobj.looseuomqty;
					productObj.productid = productid;
					//productObj.quantity = looseqtyinuom[0];
					productObj.quantity = 0
					productObj.uompacking = $('#uompackingselect'+index).val();
				
					
					if(flag == 0)
						{
						addtoProductArray(productObj)	
						}
					else
						{
						updatequantity(index)
						}
				},
				type:'POST',
				url:"calculatelooseqty",
				data:JSON.stringify(uomobj)
				}
			);
	
}

function addtoProductArray(productObj)
{
	productArray.push(productObj);	
		
}

function updatequantity(indexPosition)
{
		var quantity = $("td input[name='quantity"+indexPosition+"']").val();
		var rate = $("td input[name='unitrate"+indexPosition+"']").val();
		var amount = $("td input[name='amount"+indexPosition+"']").val();
		
		console.log("quantity  "+quantity)
		console.log("rate  "+rate)
		console.log("amount  "+amount)	
		
	var tax1 = 	$('#cgst'+indexPosition).find(':selected').attr('data-value')
	var tax2 = 	$('#sgst'+indexPosition).find(':selected').attr('data-value')
	console.log(tax1)
	console.log(tax2)
	
	var setamount = parseFloat(quantity) * parseFloat(rate);
	console.log(setamount)
	var taxamount = 0;
	var totaltax = 0;
	 totaltax = parseFloat(tax1)+parseFloat(tax2);
	console.log(totaltax)
	
	if(totaltax > 0){
		taxamount = setamount * (totaltax);
		taxamount = taxamount/100;
		setamount = parseFloat(taxamount) + parseFloat(setamount);
	}else{
		
		setamount = parseFloat(setamount);
	}
		$('#amount'+indexPosition).val(setamount);
		
		//$('#netamounttext').text(setamount);
		
		var updateProdObj;
		var removeItem;
		for (var indx in productArray) {
		
		updateProdObj = (productArray[indx])
		var prodArrayindex = updateProdObj.index;
     
        if(indexPosition == prodArrayindex)
    	 {
    	 
    	 var uomobj = setuomobj(prodArrayindex);
    	 console.log("uomobj  "+uomobj)
    	 
    	 removeItem = productArray[indx];
    	 updateProdObj.quantity = quantity;
    	 updateProdObj.fulluom=uomobj.fulluom;
    	 updateProdObj.fulluomqty = uomobj.fulluomqty;
    	 updateProdObj.looseuom = uomobj.looseuom;
    	 updateProdObj.looseuomqty = uomobj.looseuomqty;
    	 updateProdObj.subtotal = setamount;
    	 console.log("before================="+indexPosition)
    	 console.log(productArray)
    	 
    	 productArray = jQuery.grep(productArray, function(value) {
  		   return value != removeItem;
  		 });

    	 productArray.push(updateProdObj);
    	 
    	 console.log("after================="+indexPosition)
    	 console.log(productArray)	
    	 break;
    	 }
     
   }
		var netamount = 0
		$.each(productArray, function (index, value) {
			console.log(value)
			console.log(value.subtotal)
			netamount +=value.subtotal
			}); 
		console.log(netamount)
		$('#netamounttext').text(netamount); 
	
}

function updateactivestatus(indexPosition, status){
		console.log("status  "+"#delview"+indexPosition);
		if(status == 0){
			$("#delview"+indexPosition).css({display: 'none'});
			$("#addview"+indexPosition).css({display: 'block'});
		}else if(status == 1){
			$("#delview"+indexPosition).css({display: 'block'});
			$("#addview"+indexPosition).css({display: 'none'});
		}
	
		var updateProdObj;
		var removeItem;
		for (var indx in productArray) {
		
		updateProdObj = (productArray[indx]);
		var prodArrayindex = updateProdObj.index;
     
        if(indexPosition == prodArrayindex)
    	 {
    	 
    	 var uomobj = setuomobj(prodArrayindex);

    	 removeItem = productArray[indx];
    	 
    	 updateProdObj.activestatus=status;
		 
    	 console.log("before================="+indexPosition)
    	 console.log(productArray)
    	 
    	 productArray = jQuery.grep(productArray, function(value) {
  		   return value != removeItem;
  		 });

    	 productArray.push(updateProdObj);
    	 
    	 console.log("after================="+indexPosition)
    	 console.log(productArray)	
    	 break;
    	 }
     
   }	 
	
}

function deleteproductrequest(indexPosition)
{
	
	$("table#addpurchaseDT tbody").find("input[name='record"+indexPosition+"']").each(function(){
			$("#delview"+indexPosition).css({display: 'none'});
			$("#addview"+indexPosition).css({display: 'block'});
            //$(this).parents("tr").remove();
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
        
         if(indexPosition == prodArrayindex)
        	 {
        	 removeItem = productArray[indx];
        	 updateProdObj.quantity = quantity;
        	
        	 productArray = jQuery.grep(productArray, function(value) {
      		   return value != removeItem;
      		 });
      		 
	      	break;
        	 }
         
       }
    		
    });	
}

function deleteproduct(indexPosition)
{
	
	$("table#addpurchaseDT tbody").find("input[name='record"+indexPosition+"']").each(function(){
			
            $(this).parents("tr").remove();
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
        
         if(indexPosition == prodArrayindex)
        	 {
        	 removeItem = productArray[indx];
        	 updateProdObj.quantity = quantity;
        	
        	 productArray = jQuery.grep(productArray, function(value) {
      		   return value != removeItem;
      		 });
      		 
	      	break;
        	 }
         
       }
    		
    });	
}

function editproduct(indexPosition){
	$("table tbody").find("input[name='quantity"+indexPosition+"']").attr("disabled",false);
}

function savepurchaserequest()
{

	$('#productModal').modal("hide");
	var formvalid = false;
	formvalid = validateonbuttonclick('#referencenotext','input');	
	var invalidQty = 0;
	
	if(($('#purchasedatetext').val()).length == 0)
	{
	displayAlertMsg("Please Select Purchase Date");
	return false;
	}
	
	else if(productArray.length == 0)
		{
		displayAlertMsg("Please Select any one Product");
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
		displayAlertMsg("Quantity must be greater than Zero");
		return false;
		}
	
	
	
if(formvalid)
{
var reflength = $("#referencenotext").val();
if(reflength.length<13)
{
var addpurchaseObj = new Object();
productArray.sort(function(a, b){
	return a.index-b.index
});

addpurchaseObj.itemlist = JSON.stringify(productArray);
addpurchaseObj.dishlist = JSON.stringify(dishArray);
addpurchaseObj.storeid=$('#storeidselect').val();
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
addpurchaseObj.userid=$('#herouseridtext').val();
addpurchaseObj.oprn=$('#oprntext').val();
console.log(addpurchaseObj)
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
			        	
			        	
			        	window.location.href="purchaserequesthistory"
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
			url:"saveaddpurchaserequest",
			data:JSON.stringify(addpurchaseObj)
			}
		);
}	else{
	heroNotifications.showFailure("Reference number must be less than 12 number");
	return false;
}
		}
	else{
		heroNotifications.showFailure("Please fill the Mandatory fields");
		return false;
	}
}


function approveitems(){
	var addpurchaseObj = new Object();
	addpurchaseObj.purchasereqeustid = $("#pridselect").val();
	
	var approvedArr = [];
	$(productArray).each(function(key, value){
		if(value.activestatus == 1){
			approvedArr.push(value);
		}
	});
	
	addpurchaseObj.itemlist = JSON.stringify(approvedArr);
	
	console.log(approvedArr);
	

	$.ajax(
			{
				contentType:'application/json',
				success : function(data,textStatus,jqXHR)
				{
					var responseType = getResponseType(data);
					var responseValue = getResponseValue(data);
					
				        if(responseType == 'S')        
				        	{
				        	
				        	window.location.href="viewpurchaseorderrequest?purchaseorderid=0";
				        	}
				        else
				        	{
				        	if(responseMsg(responseValue) == 'undefined')
				        		{
				        		displayFailureMsg("",responseValue);
				        		window.location.href="viewpurchaseorderrequest?purchaseorderid=0";
				        		}
				        	else
				        		{
				        		heroNotifications.showFailure(responseMsg(responseValue));
				        		}
				        	}
				},
				type:'POST',
				url:"approveitemrequest",
				data:JSON.stringify(addpurchaseObj)
				}
			);
}

function savepurchaseorder()
{

	$('#productModal').modal("hide");
	var formvalid = true;
	//formvalid = validateonbuttonclick('#referencenotext','input');	
	var invalidQty = 0;
	
	if(($('#purchasedatetext').val()).length == 0)
	{
	displayAlertMsg("Please Select Purchase Date");
	return false;
	}
	
	else if(productArray.length == 0)
		{
		displayAlertMsg("Please Select any one Product");
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
		displayAlertMsg("Quantity must be greater than Zero");
		return false;
		}
	
	
	
if(formvalid)
{
//var reflength = $("#referencenotext").val();
/*if(reflength.length<13)
{*/
var addpurchaseObj = new Object();
productArray.sort(function(a, b){
	return a.index-b.index
});

addpurchaseObj.itemlist = JSON.stringify(productArray);
addpurchaseObj.dishlist = JSON.stringify(dishArray);
addpurchaseObj.storeid=$('#storeidselect').val();
addpurchaseObj.purchaseid=$('#purchaseordernotext').val();
addpurchaseObj.purchasereqeustid=$('#pridselect').val();
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
addpurchaseObj.userid=$('#herouseridtext').val();
addpurchaseObj.oprn=$('#oprntext').val();
addpurchaseObj.netamount=$('#netamounttext').text();
console.log(addpurchaseObj.itemlist)
console.log(addpurchaseObj.netamount)
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
			        	
			        	
			        	window.location.href="purchaseorderhistory"
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
			url:"saveaddpurchaseorder",
			data:JSON.stringify(addpurchaseObj)
			}
		);
}	
	else{
		heroNotifications.showFailure("Please fill the Mandatory fields");
		return false;
	}
}





function loadaddpurchaserequest()
{
	focuscursor('#referencenotext');
	$('#uompackingselect').hide()
	var userid = $('#inventoryuseridtext').val();
	$.ajax(
			{
				contentType:'application/json',
				success : function(data,textStatus,jqXHR)
				{
					
					
					var responseType = getResponseType(data);
					var responseValue = getResponseValue(data);
					var productsuggestions = [];
					
					
					$.each(responseValue, function(index, value) {
						productsuggestions.push(responseValue[index]);
						productLabels.push(responseValue[index]['label']);
					});

					$( "#itemcodetext" )
				      .autocomplete({
				        minLength: 0,
				        source: function( request, response ) {
				        	
				        
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
				      }).autocomplete( "widget" ).addClass( "autoCompleteWidth" );
	                
				},
				type:'GET',
				url:"productsuggestions"
				
				}
			);	
	                  
	var purchaseorderid = getParameterByName('purchaserequestid');
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


function loadaddpurchaseorder()
{
	focuscursor('#referencenotext');
	
	var userid = $('#inventoryuseridtext').val();
	$.ajax(
			{
				contentType:'application/json',
				success : function(data,textStatus,jqXHR)
				{
					
					
					var responseType = getResponseType(data);
					var responseValue = getResponseValue(data);
					var productsuggestions = [];
					
					
					$.each(responseValue, function(index, value) {
						productsuggestions.push(responseValue[index]);
						productLabels.push(responseValue[index]['label']);
					});

					$( "#itemcodetext" )
				      .autocomplete({
				        minLength: 0,
				        source: function( request, response ) {
				        	
				        
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
				      }).autocomplete( "widget" ).addClass( "autoCompleteWidth" );
	                
				},
				type:'GET',
				url:"productsuggestions"
				
				}
			);	
	                  
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


if(parseInt($('#taxidtext').val()) != 0)
	{
	$('#taxselect').val($('#taxidtext').val());
	var tableJSON = getTableJSON('#receivestocktable tr.receiverow','.form-control');
	$(tableJSON).each(function(key, value){
		var receivedfullromvalueforselect = $("#receivedfullromvalueforselect"+key).val();
		var receivedlooseuomvalueforselect = $("#receivedlooseuomvalueforselect"+key).val();
		$("#recfulluomsel"+key+" option:contains("+receivedfullromvalueforselect+")").attr('selected', 'selected');
		$("#reclooseuomsel"+key+" option:contains("+receivedlooseuomvalueforselect+")").attr('selected', 'selected');
	});
	
	
	}

	var newValue =$("#totalamountdisptext").text().replace(',', '');
	var gt = parseFloat(newValue);
	
	var newValued =$("#discountamounttext").text().replace(',', '');
	var gd = parseFloat(newValued);
	
	var newValuetax =$("#taxamounttext").text().replace(',', '');
	var gtax = parseFloat(newValuetax);
	
	var newValues =$("#shippingcosttext").text().replace(',', '');
	var gs = parseFloat(newValues);

    var myt = gt+gtax+gs-gd;


	$("#totalWithDecimaltext").text(myt.toFixed(2));
	
	var firstidarr = [];
	
	firstidarr = ($('#receiveditemsdetailsfirstid').val()).split("$$$");
	getReceiveListReceiveStock(firstidarr[0],firstidarr[1]);
	
}

function receivelooseqty(index,flag)
{
	var uomobj = new Object();
	var fulluomqty = $('#recfullqtytext'+index).val();
	var looseuomqty = $('#reclooseqtytext'+index).val();
	var fulluomval = $('#recfulluomsel'+index).val();
	var looseuomval = $('#reclooseuomsel'+index).val();

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
					$('#receivingqty'+index).val(looseqtyinuom[0]);
					
					checkRecQty(index);
				},
				type:'POST',
				url:"calculatelooseqty",
				data:JSON.stringify(uomobj)
				}
			);
	
}

function cancelpurchaseview()
{
	window.location.href="purchaseorderview?pid="+$('#pidtext').val();
}	
function cancelreturn()
{
	window.location.href="purchasereturnhistory";
}

function savereceivestock()
{
	
	var supplierinvoiceno = $('#supplierInvoiceNo').val();
	var supplierinvoicedate = $('#supplierInvoiceDate').val();
	
	var qtystatus = 1
	if(supplierinvoiceno != null && supplierinvoiceno != "" && supplierinvoiceno != 0){
		if($('#termsidselect').val() != null && $('#termsidselect').val() != "" && $('#termsidselect').val() != 0){
		if(supplierinvoicedate != null && supplierinvoicedate != " " && supplierinvoicedate != 0 ){

	
	var tableJSON = getTableJSON('#receivestocktable tr.receiverow','.form-control');
	console.log(tableJSON)
	var receiveData = [];
	var receivingqty = 0
	$(tableJSON).each(function(key,value){
		if(value.expirydate == "" && value.expirydate.length == 0 ){
			var OBJ = value;
			OBJ.expirydate = $('#receivedate').val();
			receiveData.push(OBJ);
		}else{
			receiveData.push(value);
		}
		receivingqty +=value.receivingqty
		
	});
	
	var receivestockObj = new Object();
	receivestockObj.receivelist = JSON.stringify(receiveData);
	receivestockObj.precid=$('#precid').val();
	receivestockObj.preceiveorderid=$('#preceiveorderid').val();
	receivestockObj.purchaseorderno=$('#purchaseorderno').val();
	receivestockObj.receivedate=$('#receivedate').val();
	receivestockObj.discount=$('#discountselect').find('option:selected').val();
	receivestockObj.discountvalue=$('#discounttext').val();
	receivestockObj.orderedtax=$('#taxselect').find('option:selected').val();
	receivestockObj.shippingcost=$('#shippingcodttext').val();
	receivestockObj.notes=$('#notestext').val();
	receivestockObj.inclusive=$('input[name=taxper]:checked').val();
	//receivestockObj.orderedtax=$('#taxamounttext').val();
	receivestockObj.precid=$('#purchaseorderhdrno').val();
	receivestockObj.userid = $('#inventoryuseridtext').val();
	receivestockObj.supplierinvoiceno = $('#supplierInvoiceNo').val();
	receivestockObj.supplierinvoicedate = supplierinvoicedate;
	receivestockObj.termsid=$('#termsidselect').val();
	receivestockObj.oprn=$('#oprntext').val();
	
	console.log(receivestockObj);
	
	if(receivingqty > 0){
	$.ajax(
			{
				contentType:'application/json',
				success : function(data,textStatus,jqXHR)
				{
					var responseType = getResponseType(data);
					var responseValue = getResponseValue(data);
						
					 if(responseType == 'S')        
			        	{
			        	
			        	//setting payment json
			        	var object = new Object();
			        	object.purchasecode = receivestockObj.purchaseorderno;
			        	object.purchaseid = data["inventoryResponse"]["responseObj"]["id"];
			        	$('#payingamount').val("0");
			        	$('#paymenttypeselect').val("5");
			        	$('#creditedwithintext').val("90");
			        	
			        	selectedrowObj = object;
			        	console.log(selectedrowObj)
			        	//window.location.href="purchaseorderview?pid="+receivestockObj.purchaseid;
			        	updatereceiveorderpayment();
			        	}
			        else
			        	{
			        	if(responseMsg(responseValue) == 'undefined')
			        		{
			        		displayFailureMsg("",responseValue)
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
		}else{
			displayFailureMsg("","Receiving QTY greater than zero");
		}
		}else{
			displayFailureMsg("","Please enter supplier invoice date");
		}
	}else{
		displayFailureMsg("","Please select terms");
	}
	}else{
		displayFailureMsg("","Please enter supplier invoice number");
	}
}

function updatereceiveorderpayment()
{
	
	var payment = $('#paymenttypeselect').val();
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
	paymentobj.supplierinvoiceno = $('#supplierInvoiceNo').val();
	paymentobj.supplierinvoicedate = $('#supplierInvoiceDate').val();

	if($('#paymenttypeselect').val() == "4")
	   {
		   paymentobj.chequeno = $('#chequenotext').val();
		   paymentobj.bank = $('#banknameselect').val();
	   }
	else
	   {
		   paymentobj.chequeno = "";
		   paymentobj.chequeno = "";
	   }	
	
	if($('#paymenttypeselect').val() == "5")
	   {
		   paymentobj.reqdays = $('#creditedwithintext').val();
	   }
	else
	   {
		   paymentobj.reqdays = 0;
	   }
 
	if(parseFloat($('#tobepayamount').val()) < parseFloat(paymentobj.paidamount))
		{
		alert("Amount must be less than or equal to "+$('#tobepayamount').val());
		return false;
		}
	else
	{
 
	
	$.ajax({
		type:'GET',
		contentType:'application/json',
		success:function(data,textStatus,jqXHR)
		{
			
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			if(responseType == "S"){
				displaySuccessMsg({"inventoryResponse":{"responseType":"S","responseObj":{"msg":"Goods Received Successfully","id":"GRN-00006"}}});
				
				//var printwindow = window.open('../../heroreports/forms/report/purchaseorderschedule/'+$('#pidtext').val()+"/"+data["inventoryResponse"]["responseObj"]["id"],'_blank')
				var p_url = "grnhistory";
				setTimeout("location.href = '"+p_url+"'",2000);
				
				/*var p_url = "purchaseorderview?pid="+$('#pidtext').val();
				
				setTimeout("location.href = '"+p_url+"'",2000);*/ 
			}
			else if(responseType == "F"){
				var p_url = "grnhistory";
				displaySuccessMsg({"inventoryResponse":{"responseType":"S","responseObj":{"msg":"Goods Received Successfully","id":"GRN-00006"}}});
				
				//displayFailureMsg("",responseMsg(responseValue));
				setTimeout("location.href = '"+p_url+"'",2000); 
			}	 
		},
		type:'POST',
		url:'updatereceiveorderpayment',
		data:JSON.stringify(paymentobj)
	});	
		}
	
	
}

function loadpurchaseorderview()
{
	var totalpurchase = ($('#totalpurchasetext').val());
	var pid=getParameterByName('pid');
	
	$('#pidtext').val(pid);
	
	selectedid = pid;
	
	checkpurchaseorder();
	
$('#paymentdatetext').val($.datepicker.formatDate("dd/mm/yy", new Date()));
//refreshpurchaselist();
}


function refreshpurchaselist()
{
	var firstidarr = [];
	firstidarr = ($('#firstidtext').val()).split("$$$");
	getPurchaseList(firstidarr[0],firstidarr[1]);	
}


function loadpurchaserequestview()
{
	var purchaserequestid = getParameterByName('pid');
	getPurchaseRequestList(purchaserequestid);	
}


function getPurchaseRequestList(purchaserequestid){
	
	$.ajax({
		type:'GET',
		contentType:'application/json',
		url:'getPurchaseRequestList/'+purchaserequestid,
		success:function(data,textStatus,jqXHR)
		{
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			var itemList = responseValue['itemList']
			console.log(itemList)
			if(responseType == 'S')
				{
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
			          /*  { data: 'fullQty' },
			            { data: 'looseqty' },*/
			            { data: 'totalQty' },
			            { data: 'purchasedate' }
			        ]
			        
			    } );	 
				}
			
		 	
						
			 
		}
	});		
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
	refreshpurchaselist();
}

function getReceiveListReceiveStock(purchaseorderid,purchasecode)
{
  
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
				  
					$('#receivelistDTReceivestock').DataTable( {
						"destroy": true,
						"bPaginate": false,
				    	data: receiveList,
				        columns: [
				            { data: 'purchaseid' },
				            { data: 'receiveddate' },
				            { data: 'billno' },
				            { data: 'grandtotal' }
				            
				        ]
			            
				    } );	
				 
				 console.log(responseValue['billList'])
				 
				 var billList = responseValue['billList']
					$('#billDT').DataTable( {
						"destroy": true,
						"bPaginate": false,
				    	data: billList,
				        columns: [
				            { data: 'billno' },
				            { data: 'supplierinvoiceno' },
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
					 $("input[name=taxper][value=" + object.inorexclusive + "]").prop('checked', true);			
					  setBillItem(object);
					 }
				 else
					 {
					 var object = new Object();
					 object.purchaseid='0';
					 object.purchasecode='0';
					 	
					 }
				 selectedrowObj = object;
				// setItemDetails(object);	
				 
				}
			
		    $('.supplier_name_text').text(responseValue['suppliername']);	
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



function getPurchaseList(purchaseorderid,purchasecode)
{

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
				            {data:  'action'}
				        ]
			            
				    } );	
				 console.log("==============")
				console.log(responseValue['billList']);
				 var billList = responseValue['billList']
					$('#billDT').DataTable( {
						"destroy": true,
						"bPaginate": false,
				    	data: billList,
				        columns: [
				            { data: 'billno' },
				            { data: 'supplierinvoiceno' },
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
					
					  setBillItem(object);
					  
					  setDefaultPaymentMode(object);
					 }
				 else
					 {
					 var object = new Object();
					 object.purchaseid='0';
					 object.purchasecode='0';
					 	
					 }
		         selectedrowObj = object;
				 //setItemDetails(object);	
				 
				}
			
		    //$("input[name=taxper][value=" + object.inorexclusive + "]").prop('checked', true);			
			$('#subtotallabel').text(responseValue['subtotal']);
			$('#taxamtlabel').text(responseValue['taxamt']);
			$('#totalalbel').text(responseValue['grandtotalamt']);
			$('#tmp_entity_date').text(responseValue['purchasedate'])
			$('#tmp_ref_number').text(responseValue['refno']);
			$('#tmp_due_date').text(responseValue['recievedate']);
			$('#discountselect').text(object.discounttype);
			$('#shippingamtlabel').text(object.shippingamt);
			
			 
		}
	});		 
}
function setsupplierdetails(object)
{
	$('#supplier_name_text').text(object.suppliername);
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


function getPurchaseid(purchaseid,receive_status ,index,listSize)
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
	
	if(receive_status  == 3)
		{
				
		 $('#recvbtn').prop('disabled', true);
		
		}
	else
		{
		selectedid = purchaseid;	
		$('#recvbtn').prop('disabled', false);
		$('#purchaseorderidcheck'+index).attr('checked', true); 
		
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
			 
			 console.log(responseValue);
			
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
					/*"bPaginate": false,*/
					dom: 'lBfrtip',
			    	data: returnProductList,
			    	buttons: [
			 		         
					             { extend: 'pdf', className: 'dataTableButton' },
								 { extend: 'excel', className: 'dataTableButton' }
					        ],
			        columns: [
			            { data: 'productname' },
			          /*  { data: 'batchno' },*/
			            { data: 'sellprice' },
			            { data: 'productcount' },
			            { data: 'receivingqty' },
			           /* { data: 'bonusqty' },*/
			           /* { data: 'fullqty' },
			            { data: 'fulluom' },
			            { data: 'looseqty' },
			            { data: 'looseuom' },*/
			            { data: 'returnedqty' },
			            { data: 'returnqtydisp' },
			            { data: 'unit' },
			            
			            /*{ data: 'discountamt'},
			            { data: 'taxamt' },*/
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

		var returnPurchaseObj = new Object();
		returnPurchaseObj.itemlist = JSON.stringify(returnProductArray);
		
		returnPurchaseObj.receiveddate = $('#returndatetext').val();
		returnPurchaseObj.returncharge = $('#returnchargetext').val();
		returnPurchaseObj.notes = $('#returnnotestext').val();
		returnPurchaseObj.purchaseid = $('#pidtext').val();
		returnPurchaseObj.oprn = 'RET_STK';
		
		console.log(returnPurchaseObj)
		
		$.ajax({
		type:'POST',
		url:'updatepurchasereturn',
		contentType:'application/json',
		data:JSON.stringify(returnPurchaseObj),
		success:function(data,textStatus,jqXWR)
		{
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
		
			    displaySuccessMsg(data);
				window.location.href="purchasereturnhistory";
				
		}
		});
		
		/*}*/
}

function updatereturnqty(index,returnqty,productcount,bonuscount,recvqty)
{

	var maxreturnqty = 0;
	var chkReturnQty = 0;
	var actualreturnqty = $('#actualreturnqtytext'+index).val();
	var billedqty = parseInt(recvqty)+parseInt(bonuscount);
	
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
		
		displayAlertMsg("Return Quantity must be less than "+maxreturnqty);
		$('#returnqtytext'+index).val(0);
		return false;
		}
	
	else
		{
		updateexistreturnqty(index);
		}
	
}

function updateexistreturnqty(indexPosition)
{
	
	$("td input[name='returnqtyname"+indexPosition+"']").each(function(){
		var returnqty = ($(this).val());
		
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
    	 returnProductArray = jQuery.grep(returnProductArray, function(value) {
  		   return value != removeItem;
  		 });
  		 
    	 updateProdObj.oprn = 'RET_STK';
    	 
    	 
    	 returnProductArray.push(updateProdObj);
    	
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
		console.log(editedObj.purchaseid)
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
	
	$('#receivelistDTReceivestock tbody').on('click', 'tr', function() {
		var table = $('#receivelistDTReceivestock').DataTable();
		var receivelistDTObject = [];
		var tableRow = table.row($(this));
		
		receivelistDTObject.push(table.row($(this)).data());
		var object = receivelistDTObject[0];
		
		setBillItem(object);
			
	});
			
}

var StorageBillAmount = 0;
function setBillItem(object)
{
	StorageBillAmount = object.tobepaid;
	
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
			if(responseType == 'S')        
        	{
				displaySuccessMsg(data);
        	}
			else
        	{
				displayFailureMsg("",responseMsg(responseValue));
        	}
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
			           /* { data: 'batchno' },*/
			            { data: 'billno' },
			          /* { data: 'fullqty' },
			            { data: 'looseqty' },*/
			            { data: 'unitnetdescwithqty' },
			      //      { data: 'bonusqty' },
			            { data: 'productprice' },
			            { data: 'recvingamt' }
			          
			        ]
			        
			    } );
					var totalamount = parseFloat(responseValue['grandtotalamt']).toFixed(2);
			    $('#subtotallabel').text(responseValue['subtotal']);
			    $('#taxamtlabel').text(responseValue['taxamt']);
		        $("#currency_code").html(responseValue.currencycode+" ");
				$('#totalalbel').text(totalamount);
				$('#tmp_entity_date').text(responseValue['purchasedate'])
				$('#tmp_ref_number').text(responseValue['refno'])
		        $('#tmp_due_date').text(responseValue['recievedate']);
				$('#discountselect').text(object.discounttype);
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
				var billList = responseValue['billList'];
				console.log("+++++++++++++")
				console.log(billList)
				$('#billDT').DataTable( {
					"destroy": true,
					"bPaginate": false,
			    	data: billList,
			        columns: [
			            { data: 'billno' },
			            { data: 'supplierinvoiceno' },
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
var paymode = ($('#paymenttypeselect').val())

if(paymode == '5')
			   { 
					
        	      $('#Debitno').css({"display":"none"});
	              $('#creditedwithin').css({"display":"block"});
	              $('#chequeno').css({"display":"none"});
	              $('#bankname').css({"display":"none"});
	              $('#paymenttypeselect').val(5);
		          $('#creditedwithintext').val(0);
		       
			   }
		if(paymode == '1')
		       {	
     	          $('#Debitno').css({"display":"none"});
                  $('#Creditno').css({"display":"none"});
                  $('#chequeno').css({"display":"none"});
                  $('#bankname').css({"display":"none"});
                  $('#creditedwithin').css({"display":"none"});
			      $('#paymenttypeselect').val(1);

		        }
		 
	    if(paymode == '4')
	         	{   
	  		      $('#Debitno').css({"display":"none"});
	              $('#Creditno').css({"display":"none"});
	              $('#chequeno').css({"display":"block"});
	              $('#bankname').css({"display":"block"});
	              $('#paymenttypeselect').val(4); 
	              $('#creditedwithin').css({"display":"none"});
	         	}
	    if(paymode == '3')
	            {        
	    	      $('#Debitno').css({"display":"block"});
	              $('#Creditno').css({"display":"none"});
	              $('#chequeno').css({"display":"none"});
	              $('#bankname').css({"display":"none"});
	              $('#paymenttypeselect').val(3);
	              $('#creditedwithin').css({"display":"none"});
				}
	    if(paymode == '2')
	            {     
	    	      $('#Debitno').css({"display":"none"});
	              $('#Creditno').css({"display":"block"});
	              $('#chequeno').css({"display":"none"});
	              $('#bankname').css({"display":"none"});
	              $('#creditedwithin').css({"display":"none"});
	              $('#paymenttypeselect').val(2); 
		        }
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
	console.log(received +" "+receiving + " "+ bonus + " "+ purchaseprice+" "+salesprice + " "+ordered)
	var taxpertype = $('#taxper'+index).find('option:selected').data("type")
		var sgttype = $('#sgst'+index).find('option:selected').data("type")
	var taxselecttype=($('#taxdefaultoptionaltext'+index).val());
	console.log(taxselecttype)
	if(taxselecttype == 'Default'){
		var taxper = $('#taxper'+index).data("value");
		var sgst = $('#sgst'+index).data("value");
		
	}else{
		
		var taxper = $('#taxper'+index).find('option:selected').data("value");
		var sgst = $('#sgst'+index).find('option:selected').data("value");
		console.log("tax not default"+taxper+"  "+sgst)
	}
	
	var selvalue = $('input[name="taxper"]:checked').val();

	if(selvalue == "exclusive" || "inclusive" || "both")
		{
		$("input[type=radio]").attr('disabled', true);
		
		}
	
	if(selvalue == "exclusive")
	{
		var taxper = 0;
		var sgst = 0;
	}
	else
	{
		/*var taxper = $('#taxper'+index).val();
		var sgst = $('#sgst'+index).val();*/
		var taxper = $('#taxper'+index).find('option:selected').data("value");
		var sgst = $('#sgst'+index).find('option:selected').data("value");
		console.log($('#taxper'+index).find('option:selected').data("type"))
		
	}
	var allowedOrder = ordered - received;

	var subtotal = receiving * purchaseprice;
	if(taxpertype == 'P'){
		taxper = subtotal*(parseFloat(taxper)/100)
	}else{
		taxper = parseFloat(taxper)
	}
	if(sgttype == 'P'){
		sgst =subtotal*(parseFloat(sgst)/100)
	}else{
		sgst =parseFloat(sgst)
	}
	console.log(taxpertype +" "+sgttype +" "+taxper +" "+sgst)
	subtotal=subtotal+((parseFloat(taxper)+parseFloat(sgst)));
	subtotal = parseFloat(subtotal);
	$('#subtotal'+index).text(subtotal);
	
	calculatetotal();
}
function taxpercalculate(index)
{
	var currdecimal = parseInt($('#currdecimaltext').val());	
	var received = ($('#recvdqty'+index).val());	
	var receiving = ($('#receivingqty'+index).val());
	var bonus = ($('#bonusqty'+index).val());
	var purchaseprice = ($('#purchaseprice'+index).val());
	var salesprice = ($('#salesprice'+index).val());
	var ordered = $('#ordered'+index).val();
	var taxper = $('#taxper'+index).val();
	var allowedOrder = ordered - received;
	
	var subtotal = receiving * purchaseprice;
	subtotal=subtotal+(subtotal*0/100);
	subtotal = parseFloat(subtotal);
	$('#subtotal'+index).text(subtotal);
	calculatetotal();
	}
function findbarcode(index,productid)
{
	
	var barcodevalue = ($('#batchno'+index).val());
	if(barcodevalue == ''){
		displayAlertMsg("Please Enter Batch No");
		return false;
	}

	$.ajax({
		type:'GET',
		contentType:'application/json',
		url:'pobarcode/'+productid+'/'+barcodevalue,
		success:function(data,textStatus,jqXHR)
		{
		
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			if(responseType == "S"){
		
				var barcode = responseValue.barcode[0]["barcode"];
				var expirydate = responseValue.barcode[0]["expirydate"];
				var purprice = responseValue.barcode[0]["purprice"];
				var saleprice = responseValue.barcode[0]["saleprice"];
				$("#barcode"+index).val(barcode);
				$("#barcode"+index).attr('readonly', true);
				$("#expirydate"+index).val(expirydate);
				$("#expirydate"+index).attr('disabled', true);
				$("#purchaseprice"+index).val(purprice);
				$("#purchaseprice"+index).attr('readonly', true);
				$("#salesprice"+index).val(saleprice);
				$("#salesprice"+index).attr('readonly', true);
				calsubtotal(index);
			}
			else{
				$("#barcode"+index).attr('readonly', false);
				$("#expirydate"+index).attr('disabled', false);
				$("#purchaseprice"+index).attr('readonly', false);
				$("#salesprice"+index).attr('readonly', false);
			}
			
		}
	});
	
}

function checkbarcode(index,productid){
	var checkbarcodevalue = ($('#barcode'+index).val());
	var batchno = ($('#batchno'+index).val());
	$.ajax({
		type:'GET',
		contentType:'application/json',
		url:'pocheckbarcode/'+checkbarcodevalue+'/'+productid+'/'+batchno,
		success:function(data,textStatus,jqXHR)
		{
			
			var responseType = getResponseType(data);
			if(responseType == "S"){
				displayAlertMsg("barcode already available");
				$('#barcode'+index).val('');
			}
			
		}
	});
}

function calculatetotal()
{
	var currsymbol = $('#currsymboltext').val();

	var currdecimal = parseInt($('#currdecimaltext').val());
	
	var totalcount = ($('#recvstockcount').val());
	var totalamount = 0;
	for(var index = 0;index<totalcount;index++)
		{

		var newValuesub = $('#subtotal'+index).text().replace(',', '');
		var subtotal = parseFloat(newValuesub);
		
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
			displayAlertMsg("Discount Percentage is not greater than 100");
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
		displayAlertMsg("Discount Amount is not greater than TotalAmount");
		
		}
	discountAmount = parseFloat(discountAmount).toFixed(currdecimal);
	$('#discountamounttext').text(currsymbol+" "+discountAmount);
	
	var taxType = $('#taxselect').val();
	
	var taxSize = $('#totaltaxtext').val();
	var taxRate = 0;
	
	var selvalue = $('input[name="taxper"]:checked').val();

	if(selvalue == "exclusive" || selvalue == "both"){
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
	}
	else{
		taxRate =0;
	}
	totalamount = parseFloat(totalamount) - parseFloat(discountAmount);
	totalamount = totalamount.toFixed(currdecimal);
	
	var taxAmount = parseFloat(totalamount) * (parseFloat(taxRate) / 100);
	taxAmount = taxAmount.toFixed(currdecimal);
	$('#taxamounttext').text(currsymbol+" "+taxAmount);

	
	var shippingCost = $('#shippingcodttext').val();
	shippingCost = parseFloat(shippingCost).toFixed(currdecimal);
	$('#shippingcosttext').text(currsymbol+" "+shippingCost);

	var grandtotal = 0;
	grandtotal = (parseFloat(totalamount) + parseFloat(taxAmount) + parseFloat(shippingCost));

	grandtotal = parseFloat(grandtotal).toFixed(currdecimal);
	$('#grandtotaltext').text(currsymbol+" "+Math.round(grandtotal));
	$('#totalWithDecimaltext').text(currsymbol+" "+grandtotal);
	taxAmount = $('#taxamounttext').text(currsymbol+" "+taxAmount);
	
}

function runreport()
{
	window.open = 'Contract/Print/';	
}


function generatepurchasereport()
{
	
var purchaseidcode = ($('#firstidtext').val()).split('$$$');

//var printwindow = window.open('http://localhost:8080/heroreports/forms/report/purchaseorderschedule/'+purchaseidcode[0]+"/"+$('#selectedbillnotext').text(),'_blank')
var printwindow = window.open('../../heroreports/forms/report/purchaseorderschedule/'+purchaseidcode[0]+"/"+$('#selectedbillnotext').text(),'_blank')
/*var printwindow = window.open('/heropharmacy/forms/purchaseorderprint/'+purchaseidcode[0]+"/"+$('#selectedbillnotext').text(),'_blank')*/
printwindow.print();
}

var billsList = [];
function loadbills()
{
	var supplieridwithoption = $("#supplierselect option:first").val();
    var supplierid=supplieridwithoption.split(',')[0];
    getBillList(supplierid);
}

function getBillList(supplierid)
{
	var supplieridwithoption = $("#supplierselect").val();
	var supplierid = supplieridwithoption.split(',')[0];
	var paymode = supplieridwithoption.split(',')[2];
	var suppliertype = supplieridwithoption.split(',')[1];
	var crediteddays = supplieridwithoption.split(',')[3];
	
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
				"bLengthChange": false,
				/*"bPaginate": false,
			    "bFilter": true,
			    "bInfo": false,
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
		
			
			if(suppliertype == "Default"){
				
				if((paymode) == '5')
				{ 
				
				 $('#paymenttypeselect').css({"display":"none"});
				 $('#supplierdefault').css({"display":"block"});
				 $('#paymodename').val("Credit");
				 $('#paymenttypeselect').val(paymode);
				 $('#creditedwithin').css({"display":"block"});
				 $('#creditedwithintext').val(crediteddays);
				 $('#default').css({"display":"none"});
				 $('#supplieroptional').css({"display":"none"});
				 $("#creditedwithintext").prop('disabled', true);
				}
			if((paymode) == '1')
			    {
				   $('#creditedwithin').css({"display":"none"});
				   $('#paymenttypeselect').css({"display":"none"});
				   $('#paymodename').val("Cash");
				   $('#paymenttypeselect').val(paymode);
				   $('#default').css({"display":"none"});
				   $('#supplieroptional').css({"display":"none"});
				   $('#creditedwithin').css({"display":"none"});
			    }
			if((paymode) == '4')
		         	{ 
				 $('#creditedwithin').css({"display":"none"});
		         $('#paymenttypeselect').css({"display":"none"});
		         $('#paymodename').val("Cheque");
		         $('#paymenttypeselect').val(paymode);
				 $('#supplieroptional').css({"display":"none"});
		         $('#creditedwithin').css({"display":"none"});
		         $('#chequeno').css({"display":"block"});
		         $('#bankname').css({"display":"block"});
		         $('#Creditno').css({"display":"none"});
		         $('#Debitno').css({"display":"none"});
		         $('#creditedwithin').css({"display":"none"});
		         	}
		    if((paymode) == '3')
		            { 
		    	 $('#supplierdefault').css({"display":"block"});
		    	 $('#default').css({"display":"none"});
		    	 $('#creditedwithin').css({"display":"none"});
		         $('#paymenttypeselect').css({"display":"none"});
		         $('#paymodename').val("DebitCard");
		         $('#paymenttypeselect').val(paymode);
				 $('#supplieroptional').css({"display":"none"});
		         $('#creditedwithin').css({"display":"none"});
		         $('#chequeno').css({"display":"none"});
		         $('#bankname').css({"display":"none"});
		         $('#Creditno').css({"display":"none"});
		         $('#Debitno').css({"display":"block"});
		         $('#creditedwithin').css({"display":"none"});
		        
					}
		    if((paymode) == '2')
		            {   
		    	$('#supplierdefault').css({"display":"block"});
		    	 $('#default').css({"display":"none"});
		    	 $('#creditedwithin').css({"display":"none"});
		         $('#paymenttypeselect').css({"display":"none"});
		         $('#paymodename').val("Credit Card");
		         $('#paymenttypeselect').val(paymode);
				 $('#supplieroptional').css({"display":"none"});
		         $('#creditedwithin').css({"display":"none"});
		         $('#chequeno').css({"display":"none"});
		         $('#bankname').css({"display":"none"});
		         $('#Creditno').css({"display":"block"});
		         $('#Debitno').css({"display":"none"});
		         $('#creditedwithin').css({"display":"none"});
			    	}
				
				
			}
			else if((suppliertype) == "Optional"){
				
				 $('#supplieroptional').css({"display":"block"});
				 $('#supplierdefault').css({"display":"none"});
				 $('#chequeno').css({"display":"none"});
		        $('#bankname').css({"display":"none"});
		        $('#Creditno').css({"display":"none"});
		        $('#Debitno').css({"display":"none"});
		        $('#creditedwithin').css({"display":"none"});
			}
			
			if(responseValue.length == 0){
				$("#recvbtn").css({'display':'none'});
			}
			else{
				$("#recvbtn").css({'display':'block'});
			}
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
	displayAlertMsg("Please Enter Paying Amount");
	return false;
	}
else
	{
	if(parseFloat(payingAmt) != parseFloat(totalAmt))
		{
		displayAlertMsg("Paying Amont must be "+totalAmt);
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
			billObj.popaidstatus = $('#paidstatushidden'+loop).val();
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
				displayAlertMsg(responseValue);
			
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

function checkAvailableProduct(){
	var productValue = $('#itemcodetext').val();
	if(productValue != ""){
	var productvalid = jQuery.inArray( productValue, productLabels );
	if(productvalid < 0){
		heroNotifications.showFailure("Please Select the Product in your Product list otherwise Create a New Product and try later");
		$('#itemcodetext').val("");
	}
	}

}

function pohistoryDetails(pid){
	$.ajax({
		type:"GET",
		url:'pohistoryDetails/'+pid,
		contentType:'application/json',
		success:function(data,textStatus,jqXHR)
		{
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
		
			
			/*$('#billsDT').DataTable( {
				"destroy": true,
				"bPaginate": false,
			    "bLengthChange": false,
			    "bFilter": true,
			    "bInfo": false,
			    "bAutoWidth": false,
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
			
			if(responseValue.length == 0){
				$("#recvbtn").css({'display':'none'});
			}
			else{
				$("#recvbtn").css({'display':'block'});
			}*/
		}
	});
}

function checkRecQty(index){

	var recvqty = $("#recvdqty"+index).val();
	var receivingqty = $("#receivingqty"+index).val();
	var orderedqty = $("#ordered"+index).val();

	var balanceqty = parseInt(orderedqty) - parseInt(recvqty);

	if(parseInt(receivingqty) > parseInt(balanceqty)){
		alert("Entered Quantity is greater than ordered quantity")
		$("#recfullqtytext"+index).val(0);
		$("#receivingqty"+index).val(0);
		//$("#purchaseprice"+index).val(0);
		//$("#salesprice"+index).val(0);
		$("#reclooseqtytext"+index).val(0);
		calsubtotal(index);
	}else{
		calsubtotal(index);
	}
	
}

function setDefaultPaymentMode(object){
console.log(object)
	  if((object.opttype) == "Default")
		{
			
			if((object.paymode) == '5')
				{ 
				
				 $('#paymenttypeselect').css({"display":"none"});
				 $('#supplierdefault').css({"display":"block"});
				 $('#paymodename').val("Credit");
				 $('#creditedwithin').css({"display":"block"});
				 $('#paymenttypeselect').val(object.paymode);
				 $('#creditedwithintext').val(object.reqdays);
				 $('#default').css({"display":"none"});
				 $('#supplieroptional').css({"display":"none"});
				 $("#creditedwithintext").prop('disabled', true);
				 $("#payingamount").val(0);
				 $("#payingamount").prop("disabled",true);
				}
			if((object.paymode) == '1')
			    {
				
				 $('#creditedwithin').css({"display":"none"});
				 $('#paymenttypeselect').css({"display":"none"});
				 $('#paymodename').val("Cash");
				 $('#paymenttypeselect').val(object.paymode);
				 $('#default').css({"display":"none"});
			     $('#supplieroptional').css({"display":"none"});
				 $('#creditedwithin').css({"display":"none"});
				 $("#payingamount").val(StorageBillAmount);
				 $("#payingamount").prop("disabled",false);
			    }
			if((object.paymode) == '4')
		       { 
			
				 $('#default').css({"display":"block"});
				 $('#creditedwithin').css({"display":"none"});
	             $('#paymenttypeselect').css({"display":"none"});
	             $('#paymodename').val("Cheque");
	             $('#paymenttypeselect').val(object.paymode);
				 $('#supplieroptional').css({"display":"none"});
	             $('#creditedwithin').css({"display":"none"});
	             $('#chequeno').css({"display":"block"});
	             $('#bankname').css({"display":"block"});
	             $('#Creditno').css({"display":"none"});
	             $('#Debitno').css({"display":"none"});
	             $('#creditedwithin').css({"display":"none"});
	             $("#payingamount").val(StorageBillAmount);
	             $("#payingamount").prop("disabled",false);
		      }
		    if((object.paymode) == '3')
		      {        
		    	
		    	 $('#creditedwithin').css({"display":"none"});
	             $('#paymenttypeselect').css({"display":"none"});
	             $('#paymodename').val("DebitCard");
	             $('#paymenttypeselect').val(object.paymode);
				 $('#supplieroptional').css({"display":"none"});
	             $('#creditedwithin').css({"display":"none"});
	             $('#chequeno').css({"display":"none"});
	             $('#bankname').css({"display":"none"});
	             $('#Creditno').css({"display":"none"});
	             $('#Debitno').css({"display":"block"});
	             $('#creditedwithin').css({"display":"none"});
	             $("#payingamount").val(StorageBillAmount);
	             $("#payingamount").prop("disabled",false);
	         }
		    if((object.paymode) == '2')
           {   
		    	
		    	 $('#creditedwithin').css({"display":"none"});
	             $('#paymenttypeselect').css({"display":"none"});
	             $('#paymodename').val("Credit Card");
	             $('#paymenttypeselect').val(object.paymode);
				 $('#supplieroptional').css({"display":"none"});
	             $('#creditedwithin').css({"display":"none"});
	             $('#chequeno').css({"display":"none"});
	             $('#bankname').css({"display":"none"});
	             $('#Creditno').css({"display":"block"});
	             $('#Debitno').css({"display":"none"});
	             $('#creditedwithin').css({"display":"none"});
	             $("#payingamount").val(StorageBillAmount);
	             $("#payingamount").prop("disabled",false);
			 }
		         	
		}
		else if((object.opttype) == "Custom")
		     {
			   
			    $('#supplieroptional').css({"display":"block"});
			    $('#supplierdefault').css({"display":"none"});
			    $('#chequeno').css({"display":"none"});
              $('#bankname').css({"display":"none"});
              $('#Creditno').css({"display":"none"});
              $('#Debitno').css({"display":"none"});
              $('#creditedwithin').css({"display":"none"});
              $("#payingamount").val(StorageBillAmount);
              $("#payingamount").prop("disabled",false);
		    }	
}


function addtodishArray(){

	var index = dishArray.length + 1;
	var dishname = $('#dishname').val();
	
	var dishcount = $('#dishcount').val();
	var dishObj = new Object();
	dishObj.index = index;
	dishObj.dishname = dishname;
	dishObj.dishcount = dishcount;
	
	dishArray.push(dishObj);
	var html = "<tr>"+
				"<td>"+index+"</td>"+
				"<td>"+dishname+"</td>"+
				"<td>"+dishcount+"</td>"+
				"<td><input class='btn white-bg fa-input red-font' type='button' value='&#xf014;' onclick='deletedish("+index+")' name='dishrecord"+index+"' /> </td>"+
				"</tr>";
	
	$("table#dishDT tbody").append(html);
	 $('#dishname').val(" ");
	 $('#dishcount').val(0);
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

function openDishDetails(){
	$("#dishDetails").modal('show');
}

function loadPurchaseRequestItems(){
	if($("#pridselect").val() > 0){
		$("table > tbody").html("");
		$("#PORcontents").css({display: 'block'});
		productArray= [];
		showArray= [];
		$.ajax({
			type:'GET',
			contentType:'application/json',
			url:'purchaserequestlist/'+$("#pridselect").val(),
			success:function(data,textStatus,jqXHR)
			{
			
				var responseType = getResponseType(data);
				var responseValue = getResponseValue(data);
				
				if(responseType == 'S')
					{
						var purchaseorderList = responseValue['purchaseorderList'];
						var responseObj = purchaseorderList[0];
						
						//$('#suppliernameselect').val(responseObj['supplierid']);
						//$('#purchaseordernotext').val(responseObj['purchaseid']);
						//$('#purchasecodetext').val(responseObj['purchasecode']);
						//$('#referencenotext').val(responseObj['purchaserefno']);
						//$('#purchasedatetext').val(responseObj['purchasedate']);
						//$('#notestext').val(responseObj['purchasenotes']);
						//$('#termstext').val(responseObj['purchasetnc']);
						//$('#oprntext').val("UPD");
						
						var itemList = responseValue['itemList'];
						var indexPosition = 0;
						for (var indx in itemList) {
							var itemObj = itemList[indx];
							indexPosition++;
					
							var fulluomsel = itemObj.uomsel.fulluomsel;
							var looseuomsel = itemObj.uomsel.looseuomsel;
							var uompackingid = itemObj.uompacking;
							fulluomsel = fulluomsel.replace('uompackingsel','fulluomsel'+indexPosition);
							var calcloosecountmethod = "calculatelooseqty('"+indexPosition+"',1)";
							fulluomsel = fulluomsel.replace('calculatelooseqty()',calcloosecountmethod);
							
							looseuomsel = looseuomsel.replace('uompackingsel','looseuomsel'+indexPosition);
							looseuomsel = looseuomsel.replace('calculatelooseqty()',calcloosecountmethod);
							console.log(looseuomsel)
							var markup = "<tr><td>" + itemObj.productname + "<input type='hidden' id='fulluomqty"+indexPosition+"' " +
									"value='"+itemObj.fulluomqty+"' style='width:50px;' onblur=calculatelooseqty('"+indexPosition+"',1);>&nbsp;"+fulluomsel+" " +
									"<input type='hidden' id='looseuomqty"+indexPosition+"' value='"+itemObj.looseuomqty+"' style='width:50px;' " +
									"onblur=calculatelooseqty('"+indexPosition+"',1); />&nbsp;"+looseuomsel+"</td>" +
				    				"<td><input type='number' id='looseqty"+indexPosition+"' name='quantity"+indexPosition+"' value='"+itemObj.quantity+"' onblur='updatequantity("+indexPosition+")'" +
				    						"  style='width:75px';><span style='margin:5px;font-size:16px;font-weight:bold;'>"+itemObj.unitdescnet+"</span><input type='hidden' id='uompackingselect"+indexPosition+"' value='"+uompackingid+"' /></td>" +
				    				
				    				/*"<td><input class='btn white-bg fa-input color-font' type='button' value='&#xf044;' onclick='editproduct("+indexPosition+")'>" +*/
				    				"<td><input type='hidden' name='activestatus"+indexPosition+"' value='"+itemObj.activestatus+"' />"+
				    				"<div id='delview"+indexPosition+"' style='display:block;'><input class='btn white-bg fa-input red-font' type='button' value='&#xf014;' onclick='updateactivestatus("+indexPosition+",0)' " +
				    								"name='record"+indexPosition+"'></div>" +
				    				"<div id='addview"+indexPosition+"' style='display:none;'><span style='font-size:14px;' class='red-font'> Removed </span><input class='btn white-bg fa-input' style='font-size:17px;color:#0e7bdd !important;' type='button' value='&#xf055' onclick='updateactivestatus("+indexPosition+",1)' " +
				    								"name='record"+indexPosition+"'>"
				    				+"</div>" +
				    				"</td></tr>";
				    						
				    $("table > tbody").append(markup);
				    
				     productArray.push(itemObj);
				     showArray.push(itemObj);
				    $('#fulluomsel'+indexPosition).val(itemObj.fulluomsel);
				    $('#looseuomsel'+indexPosition).val(itemObj.looseuomsel);
						}
						}
				}
		});	
		
	}else{
		$("#PORcontents").css({display: 'none'});
		$("table > tbody").html("");
		productArray= [];
	}
}

function loadPurchaseOrderReqSelectBox(){
	$.ajax({
		type:'GET',
		contentType:'application/json',
		url:'loadPurchaseOrderReqSelectBox',
		success:function(data,textStatus,jqXHR)
		{
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			
			console.log(responseValue);
			
		}
	})
}

function rejectpurchaseorder(){

	var purchaseObj = new Object();
	purchaseObj.purchasereqeustid = $("#pridselect").val();
	purchaseObj.remarks = $("#notestext").val();
	purchaseObj.oprn = "DEL";
	
	$.ajax({
		type:'POST',
		contentType:'application/json',
		url:'delPurchaseOrderRequest',
		data: JSON.stringify(purchaseObj),
		success:function(data,textStatus,jqXHR)
		{
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			
			window.location.href = "viewpurchaseorderrequest?purchaseorderid=0";
			
			
		}
	})
	
}
function loadPurchaseApprovedItems(){
	if($("#pridselect").val() > 0){
		$("table > tbody").html("");
		productArray= [];
		$.ajax({
			type:'GET',
			contentType:'application/json',
			url:'purchaseapprovedlist/'+$("#pridselect").val(),
			success:function(data,textStatus,jqXHR)
			{
			
				var responseType = getResponseType(data);
				var responseValue = getResponseValue(data);
				
				if(responseType == 'S')
					{
						var purchaseorderList = responseValue['purchaseorderList'];
						var responseObj = purchaseorderList[0];				
						
						var itemList = responseValue['itemList'];
						
						var indexPosition = 0;
						for (var indx in itemList) {
							var itemObj = itemList[indx];
							//console.log(itemObj)
							console.log(itemObj.cgstselect)
							
							indexPosition++;
					
							var fulluomsel = itemObj.uomsel.fulluomsel;
							var looseuomsel = itemObj.uomsel.looseuomsel;
							var uompackingid = itemObj.uompacking;
							//fulluomsel = fulluomsel.replace('uompackingsel','fulluomsel'+indexPosition);
							var calcloosecountmethod = "calculatelooseqty('"+indexPosition+"',1)";
							//fulluomsel = fulluomsel.replace('calculatelooseqty()',calcloosecountmethod);
							
							//looseuomsel = looseuomsel.replace('uompackingsel','looseuomsel'+indexPosition);
							//ooseuomsel = looseuomsel.replace('calculatelooseqty()',calcloosecountmethod);
							
							var markup = "<tr> <td>" + itemObj.productname + " </td>" +
									
				    				"<td><input type='number' id='looseqty"+indexPosition+"' name='quantity"+indexPosition+"' value='"+itemObj.quantity+"' onblur='updatequantity("+indexPosition+")'" +
				    						" style='width:75px';><span style='font-size:16px;font-weight:bold;'>"+itemObj.netunitdesc+"</span><input type='hidden' id='uompackingselect"+indexPosition+"' value='"+uompackingid+"' /></td>" +
				    			
				    			"<td><input type='number' id='unitrate"+indexPosition+"' name='unitrate"+indexPosition+"' value='"+itemObj.unitrate+"' " +
				    						" style='width:75px'; class='form-control form-white' readonly></td>" +
				    			"<td>" +itemObj.cgstselect+" </td>"+
				    			"<td>" +itemObj.sgstselect+" </td>"+
				    			"<td><input type='number' id='amount"+indexPosition+"' name='amount"+indexPosition+"' value='"+itemObj.amount+"' onblur='updatequantity("+indexPosition+")'" +
	    						" style='width:75px'; class='form-control form-white' readonly></td>" +
				    			"<td><input class='btn white-bg fa-input color-font' type='button' value='&#xf044;' onclick='editproduct("+indexPosition+")'>" +
				    				 "<input class='btn white-bg fa-input red-font' type='button' value='&#xf014;' onclick='deleteproduct("+indexPosition+")' " +
				    			     "name='record"+indexPosition+"'></td></tr>";
							 
				    $("table > tbody").append(markup);
				    itemObj.subtotal =$('#amount'+indexPosition).val();
				    itemObj.cgsttax = $('#cgst'+indexPosition).val();
				    itemObj.sgsttax = $('#sgst'+indexPosition).val();
				     productArray.push(itemObj);
				     calculatesubtotal(indexPosition);
				    $('#fulluomsel'+indexPosition).val(itemObj.fulluomsel);
				    $('#looseuomsel'+indexPosition).val(itemObj.looseuomsel);
						}
						}
				}
		});	
		
	}
}

function openModal(){
	
		$('#productModal').modal("show");
	
}


function changeSalePrice(index){
	var pur_value = $("#purchaseprice"+index).val();
	$("#salesprice"+index).val(pur_value);
	
}

function getGRNreportdetails(purchasecode)
{
	  		$.ajax({
			type:'GET',
			contentType:'application/json',
			url:'getPurchaseBillNoList/'+purchasecode,
			success:function(data,textStatus,jqXHR)
			{
				var responseType = getResponseType(data);
				var responseValue = getResponseValue(data);
				console.log(responseValue.length)
				if(responseType == 'S' && responseValue.length > 0)
					{
					        var purchasebill = [];							
					        $("#GRNReportDetails").modal('show');
							$.each(responseValue, function(index, value) {
								purchasebill.push(responseValue[index]);
							});
							
							$('#purchasebillnoGRN').empty();
							var option = '<option value="0">-- Select --</option>';
							
							for (var i=0;i<purchasebill.length;i++){
								
							   option += '<option value="'+ purchasebill[i].value + '">' + purchasebill[i].label + '</option>';
							}
							$('#purchasebillnoGRN').append(option);
					}else{
						//alert("inside")
						displayFailureMsg(" ","Products Not Available in STOCK for this Purchase Order");
					}
				}
		});	
		
}


function gotopurchasereturn(){
	
	var prhdrid=$('#purchasebillnoGRN').val();
	window.location.href="purchasereturn?pid="+prhdrid;
	
}


function deleteReturnproduct(indexPosition)
{
	
	$("table#returnproductDT tbody").find("input[name='returnqtyname"+indexPosition+"']").each(function(){
			
            $(this).parents("tr").remove();
            var position = parseInt(indexPosition)+1;
        	console.log(position)
        	for (var indx in returnProductArray) {
        		var tempObj = (returnProductArray[indx]);
        		
        		if(tempObj.index >= position){
        			tempObj.index = tempObj.index - 1;
        			returnProductArray[indx].index = tempObj.index;
        		}
        	}
        	
    	var quantity = ($(this).val());
    	console.log(tempObj)
    	var updateProdObj;
    	var removeItem;
    	for (var indx in returnProductArray) {
    			
    	 updateProdObj = (returnProductArray[indx])
    	 	console.log(updateProdObj)
         var prodArrayindex = updateProdObj.index;
    		console.log(prodArrayindex)
         if(indexPosition == prodArrayindex)
        	 {
        	 removeItem = returnProductArray[indx];
        	 updateProdObj.quantity = quantity;
        	
        	 returnProductArray = jQuery.grep(returnProductArray, function(value) {
      		   return value != removeItem;
      		 });
      		 
	      	break;
        	 }
         
       }
    		
    });	
	console.log(returnProductArray)
}


function openModalpurchaselistGRN(purchaseid,purchasecode){
	$("#purchasecodegrntext").text(" "+purchasecode);
	$("#purchaseprintid").val(purchaseid);
	$('#suuplierbillnoGRN').empty();
	$.ajax({
		type:'GET',
		contentType:'application/json',
		url:'getPurchaseGRNList/'+purchasecode,
		success:function(data,textStatus,jqXHR)
		{
		
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			var batchidSuggestion = [];
			
			$.each(responseValue, function(index, value) {
				batchidSuggestion.push(responseValue[index]);
			});
			
			$('#suuplierbillnoGRN').empty();
			var option = '<option value="0">-- Select --</option>';
			
			for (var i=0;i<batchidSuggestion.length;i++){
				
			   option += '<option value="'+ batchidSuggestion[i].label + '">' + batchidSuggestion[i].label + '</option>';
			}
			$('#suuplierbillnoGRN').html(option);
			
			}
	});	
	
	$("#GRNReportDetails").modal('show');
	
}

function getPrint(){
	var purchaseid = $("#purchaseprintid").val();
	var grnid = $("#suuplierbillnoGRN").val();
	
	var printwindow = window.open('../../heroreports/forms/report/purchaseorderschedule/'+purchaseid+"/"+grnid,'_blank')
	var p_url = "grnhistory";
	setTimeout("location.href = '"+p_url+"'",2000);
	//console.log(purchaseid+ "   "+grnid)
}


function calculatesubtotal(indexPosition)
{
	console.log(indexPosition)
	var quantity = $("td input[name='quantity"+indexPosition+"']").val();
	var rate = 	   $("td input[name='unitrate"+indexPosition+"']").val();
	var amount =   $("td input[name='amount"  +indexPosition+"']").val();
	
	var tax1 = 	$('#cgst'+indexPosition).find(':selected').attr('data-value')
	var tax2 = 	$('#sgst'+indexPosition).find(':selected').attr('data-value')
	console.log(tax1)
	console.log(tax2)
	
	var setamount = parseFloat(quantity) * parseFloat(rate);
	console.log(setamount)
	var taxamount = 0;
	var totaltax = parseFloat(tax1)+parseFloat(tax2);
	console.log(totaltax)
	taxamount = setamount * (totaltax);
	taxamount = taxamount/100;
	console.log(taxamount)
	setamount = parseFloat(taxamount) + parseFloat(setamount);
	console.log(setamount)
	$('#amount'+indexPosition).val(setamount);
	
	//$('#netamounttext').text(setamount);
	
	
	var updateProdObj;
	var removeItem;
	for (var indx in productArray) {
	
	updateProdObj = (productArray[indx])
	
	var prodArrayindex = updateProdObj.index;
 console.log("indexPosition "+indexPosition+"prodArrayindex"+prodArrayindex)
    if(indexPosition == prodArrayindex)
	 {
    	
	 removeItem = productArray[indx];
	 updateProdObj.subtotal = setamount;
	 updateProdObj.cgsttax  = $('#cgst'+indexPosition).val();
	 updateProdObj.sgsttax   = $('#sgst'+indexPosition).val();
	 updateProdObj.index = indexPosition
	 productArray = jQuery.grep(productArray, function(value) {
		   return value != removeItem;
		 });

	 productArray.push(updateProdObj);
	 break;
	 }
	}
	var netamount = 0
	$.each(productArray, function (index, value) {
		console.log(value)
		netamount +=value.subtotal
		}); 
	console.log(productArray)
	$('#netamounttext').text(netamount);
}

function calculatetaxsubtotal(){
	
//	$("#addpurchaseDT").on('click','.cgstbtn',function(){
//        // get the current row
//        var currentRow=$(this).closest("tr"); 
//        
//        var col1=currentRow.find("td:eq(0)").text(); // get current row 1st TD value
//        var col2=currentRow.find("td:eq(1)").text(); // get current row 2nd TD
//        var col3=currentRow.find("td:eq(2)").text(); // get current row 3rd TD
//        var col4=currentRow.find("td:eq(3)").text(); // get current row 4nd TD
//        var col5=currentRow.find("td:eq(4)").text(); // get current row 5rd TD
//        
//        var data=col1+"\n"+col2+"\n"+col3+"\n"+col4+"\n"+col5;
//        
//        console.log(data);
//   });
	
//	$("#addpurchaseDT").on('click','.sgstbtn',function(){
//        // get the current row
//        var currentRow=$(this).closest("tr"); 
//        
//        var col1=currentRow.find("td:eq(0)").text(); // get current row 1st TD value
//        var col2=currentRow.find("td:eq(1)").text(); // get current row 2nd TD
//        var col3=currentRow.find("td:eq(2)").text(); // get current row 3rd TD
//        var data=col1+"\n"+col2+"\n"+col3;
//        
//        console.log(data);
//   });
}
