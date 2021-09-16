var productLabels = [];
var productArray =[]

function loaddishrequest(){
	$('#uompackingselect').hide();
	var hdrid = getParameterByName('id');
	var dishid = getParameterByName('did');
	if(hdrid > 0){
		$('#dishhdrid').val(hdrid);
		$('#oprntext').val("UPD");
		$('#dishnameselect').val(dishid)
		getDetails(hdrid)
	}else{
		$('#dishhdrid').val("0");
		$('#oprntext').val("INS");
	}
	getdishtype();
	
	$.ajax(
			{
				contentType:'application/json',
				success : function(data,textStatus,jqXHR)
				{
					
					
					var responseType = getResponseType(data);
					var responseValue = getResponseValue(data);
					console.log(responseValue)
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
	                  

	
}
function editDish(id,dishid){
	window.location.href = 'adddishrequest?id='+id+'&did='+dishid;
}
function extractLast( term ) {
	  return split( term ).pop();
	}
function split( val ) {
    return val.split( /,\s*/ );
}
function getdishtype(){
	$('#dishtypeselect').empty();
	
	var dishid = $('#dishnameselect').val();
	var hdrid = $('#dishhdrid').val()
	
	
	    $.ajax({
		type:'GET',
		contentType:'application/json',
		url:'getDishtypeList/'+dishid+'/'+hdrid,
		success:function(data,textStatus,jqXHR)
		{
		
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			var dishTypeList=responseValue["dishtypeList"];
			$.each(dishTypeList, function (index, value) {
			   $('#dishtypeselect').append('<option value='+value.dishTypeId+' >'+value.dishTypeDesc+'</option>');
			});    
		}
	})
	
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
function getDetails(id){
	
	$.ajax({
		type:'GET',
		contentType:'application/json',
		url:'getDishProductDetails/'+id,
		success:function(data,textStatus,jqXHR)
		{
		
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			var productEntry = 0;
			var list = responseValue["dishtypeList"]
			productArray=[]
            
				$.each(list,function(index,value){
					console.log(index)
					var uom = list[index]["productId"]
					var indexPosition = index + 1;
					var object = new Object();
				
					object.productid = list[index]["productId"]
					object.fulluom = list[index]["fullypeId"]
					object.looseuom = list[index]["looseTypeId"]
					object.fulluomqty = list[index]["fullQty"]
					object.looseuomqty = list[index]["looseQty"]
					object.uompacking = list[index]["uompacking"]
					object.quantity = list[index]["totalqty"]
					object.index =indexPosition;
					object.productname = list[index]["productName"]
					productArray.push(object);
					
					
					var uompacking = object.uompacking;
					$.ajax({
						type:'GET',
						contentType:'application/json',
						url:'uomforpacking/'+uompacking,
						success:function(data,textStatus,jqXHR)
						{
							//addproduct(data.inventoryResponse.responseObj.fulluomsel,data.inventoryResponse.responseObj.looseuomsel);
							var fulluompacks = data.inventoryResponse.responseObj.fulluomsel;
							var looseuompacks = data.inventoryResponse.responseObj.looseuomsel;
							var fulluomsel = fulluompacks;
							var looseuomsel = looseuompacks;
							fulluomsel = fulluomsel.replace('uompackingsel','fulluomsel'+indexPosition);
							var calcloosecountmethod = "calculatelooseqty('"+indexPosition+"',1)";
							fulluomsel = fulluomsel.replace('calculatelooseqty()',calcloosecountmethod);
							
							looseuomsel = looseuomsel.replace('uompackingsel','looseuomsel'+indexPosition);
							looseuomsel = looseuomsel.replace('calculatelooseqty()',calcloosecountmethod);
							
							var markup = "<tr><td>" + object.productname + " " +
							
							"<input type='hidden' id='fulluomqty"+indexPosition+"' value='"+object.fulluomqty+"' style='width:50px;' " +
							"onblur=calculatelooseqty('"+indexPosition+"',1);>&nbsp;"+fulluomsel+" " +
							
							"<input type='hidden' id='looseuomqty"+indexPosition+"' value='"+object.looseuomqty+"' style='width:50px;' onblur=calculatelooseqty('"+indexPosition+"',1); />&nbsp;"
							+looseuomsel+"<input type='hidden' id='uompackingselect"+indexPosition+"' value='"+object.uompacking+"' /></td>"+
							"<td><input type='number' id='looseqty"+indexPosition+"' name='quantity"+indexPosition+"' value='"+object.quantity+"' onblur='updatequantity("+indexPosition+")'  style='margin:5px;width:80px'><span style='font-weight:bold;font-size:16px;'>"+list[index]["looseuomdesc"]+"</span></td>" +
							"<td><input class='btn white-bg fa-input red-font' type='button' value='&#xf014;' onclick='deleteproduct("+indexPosition+","+object.productid+")' name='record"+indexPosition+"'>" +
						"</tr>";
					$(markup).prependTo("table#adddishDT > tbody");
					$("#itemcodetext").val("");
					$("#itemcounttext").val("1");
					$("#email").val("");
						}
					});	
					
				
				});
			
			
		}
	})
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
	console.log(fulluompacks + "  "+looseuompacks)
var formvalid = validateonbuttonclick('#itemcodetext','input');	

if(formvalid)
{
var productId= $('#productcodetext').val();
$.ajax({
	type:'GET',
	contentType:'application/json',
	url:'productinfo/'+productId,
	success:function(data,textStatus,jqXHR)
	{
	
		var responseType = getResponseType(data);
		var responseValue = getResponseValue(data);
		var productEntry = 0;
		console.log(responseValue[0]["unitdesc"])
		var uom = responseValue[0]["unitdesc"]
		var productname = responseValue[0]["productname"]
		var indexPosition = productArray.length + 1;
		$.each(productArray,function(index,value){
	var productObj = value;
	
	if(parseInt(productObj.productid) == productId)
		{
		productEntry = parseInt(productEntry) + 1;
		}
	});
		
		
		
			if(parseInt(productEntry) == 0)
			{
				
				/*var object = new Object();
				object.productid = responseValue[0]["productid"]
				
				object.index=  indexPosition
				productObj.fulluom=uomobj.fulluom;
				productObj.fulluomqty = uomobj.fulluomqty;
				productObj.looseuom = uomobj.looseuom;
				productObj.looseuomqty = uomobj.looseuomqty;
				productObj.productid = productid;
				productObj.quantity = looseqtyinuom[0];
				productObj.uompacking = $('#uompackingselect'+index).val();
				productArray.push(object);*/
				
				var uompackingid = $("#uompackingselect").val();
				var uomid = 'uompackingsel'+indexPosition;
				var fulluomsel = fulluompacks;
				var looseuomsel = looseuompacks;
				fulluomsel = fulluomsel.replace('uompackingsel','fulluomsel'+indexPosition);
				var calcloosecountmethod = "calculatelooseqty('"+indexPosition+"',1)";
				fulluomsel = fulluomsel.replace('calculatelooseqty()',calcloosecountmethod);
				
				looseuomsel = looseuomsel.replace('uompackingsel','looseuomsel'+indexPosition);
				looseuomsel = looseuomsel.replace('calculatelooseqty()',calcloosecountmethod);
				
		var markup = "<tr><td>" + productname + " " +
		"<input type='hidden' id='fulluomqty"+indexPosition+"' value='0' style='width:50px;' " +
					"onblur=calculatelooseqty('"+indexPosition+"',1);>&nbsp;"+fulluomsel+" " +
					"<input type='hidden' id='looseuomqty"+indexPosition+"' value='0' style='width:50px;' onblur=calculatelooseqty('"+indexPosition+"',1); />&nbsp;"
					+looseuomsel+"<input type='hidden' id='uompackingselect"+indexPosition+"' value='"+uompackingid+"' /></td>"+
		"<td><input type='number' id='looseqty"+indexPosition+"' name='quantity"+indexPosition+"' value='0' onblur='updatequantity("+indexPosition+")'  style='width:80px';><span style='margin:5px;font-size:16px;font-weight:bold;'>"+uom+"<span></td>" +
		"<td><input class='btn white-bg fa-input red-font' type='button' value='&#xf014;' onclick='deleteproduct("+indexPosition+","+productId+")' name='record"+indexPosition+"'>" +
	"</tr>";
$(markup).prependTo("table#adddishDT > tbody");
$("#itemcodetext").val("");
$("#itemcounttext").val("1");
$("#email").val("");
var fulluomqty = $('#fulluomqty'+indexPosition).val();
var looseuomqty = $('#looseuomqty'+indexPosition).val();
var fulluomval = $('#fulluomsel'+indexPosition).val();
var looseuomval = $('#looseuomsel'+indexPosition).val();
/*fulluomval = fulluomval.split("-");
looseuomval = looseuomval.split("-");*/
/*var looseuomsno = looseuomval[0];
var looseuom = looseuomval[1];
var fulluomsno = fulluomval[0];
var fulluom = fulluomval[1];*/
var looseuomsno =0
var looseuom=0
var fulluomsno =0
var fulluom =0
var uomobj = new Object();
uomobj.fulluom = fulluom;
uomobj.fulluomsno = fulluomsno;
uomobj.looseuomsno = looseuomsno;
uomobj.looseuom = looseuom;
uomobj.fulluomqty = fulluomqty;
uomobj.looseuomqty = looseuomqty;
uomobj.uompacking=$('#uompackingselect'+indexPosition).val();
uomobj.productid = productId;
uomobj.quantity =0
uomobj.index =indexPosition
productArray.push(uomobj);
			}
			else
			{
			$("#itemcodetext").val('');
			$('#productcodetext').val('');
			displayFailureMsg("","Product "+name+" is Already added.")
			return false;
			}	
	}
})

		}
else
	{
	displayFailureMsg("","Please Select anyone Product");
	return false;
	}

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
			
					$('#looseqty'+index).val(looseqtyinuom[0]);
					
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
					productObj.quantity = looseqtyinuom[0];
					productObj.uompacking = $('#uompackingselect'+index).val();
				console.log(productObj)
						console.log(flag)
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

function setuomobj(index)
{
	var fulluomqty = $('#fulluomqty'+index).val();
	var looseuomqty = $('#looseuomqty'+index).val();
	var fulluomval = $('#fulluomsel'+index).val();
	var looseuomval = $('#looseuomsel'+index).val();
/*	fulluomval = fulluomval.split("-");
	looseuomval = looseuomval.split("-");
	var looseuomsno = looseuomval[0];
	var looseuom = looseuomval[1];
	var fulluomsno = fulluomval[0];
	var fulluom = fulluomval[1];*/
	var looseuomsno =0
	var looseuom=0
	var fulluomsno =0
	var fulluom =0
	var uomobj = new Object();
	uomobj.fulluom = fulluom;
	uomobj.fulluomsno = fulluomsno;
	uomobj.looseuomsno = looseuomsno;
	uomobj.looseuom = looseuom;
	uomobj.fulluomqty = fulluomqty;
	uomobj.looseuomqty = looseuomqty;
	uomobj.uompacking=$('#uompackingselect'+index).val();
	uomobj.index = index
	return uomobj;
}
function deleteproduct(indexPosition,productId)
{
	
	
	$("table#adddishDT tbody").find("input[name='record"+indexPosition+"']").each(function(){
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

function updatequantity(indexPosition)
{
		var quantity = $("td input[name='quantity"+indexPosition+"']").val();
		
		var updateProdObj;
		var removeItem;
		for (var indx in productArray) {
		console.log(productArray)
		updateProdObj = (productArray[indx])
		console.log(updateProdObj)
		var prodArrayindex = updateProdObj.index;
     console.log("indexPosition "+indexPosition+"prodArrayindex"+prodArrayindex)
        if(indexPosition == prodArrayindex)
    	 {
        	 var uomobj = setuomobj(prodArrayindex);
        	 console.log("======")
        	 console.log(uomobj)
    	 removeItem = productArray[indx];
    	 updateProdObj.quantity = quantity;
    	 updateProdObj.fulluom=uomobj.fulluom;
    	 updateProdObj.fulluomqty = uomobj.fulluomqty;
    	 updateProdObj.looseuom = uomobj.looseuom;
    	 updateProdObj.looseuomqty = uomobj.looseuomqty;
    	 updateProdObj.uompacking = $('#uompackingselect'+indexPosition).val();
    	 updateProdObj.index = indexPosition
    	 productArray = jQuery.grep(productArray, function(value) {
  		   return value != removeItem;
  		 });

    	 productArray.push(updateProdObj);
    	 break;
    	 }
     
   }
		 
	console.log("final product array ");
	console.log(productArray)
}
function saveDishRequest(){
	if(productArray.length > 0){
		var count = 0;
		var dishobj = new Object();
		dishobj.dishhdrid = $('#dishhdrid').val();
		dishobj.dishid = $('#dishnameselect').val();
		dishobj.dishtypeid = $('#dishtypeselect').val();
		dishobj.itemlist = JSON.stringify(productArray);
		dishobj.dishcount = $('#dishcounttext').val();
		dishobj.userid=$('#herouseridtext').val();
		dishobj.oprn = $('#oprntext').val();
		console.log(dishobj)
		for(var i=0; i<productArray.length; i++){
		    var name = productArray[i]["quantity"];
		    if(name == 0){
		    	count =1;
		      break;
		    }
		  }

		if(count == 0){
		$.ajax(
				{
					contentType:'application/json',
					success : function(data,textStatus,jqXHR)
					{
						var responseType = getResponseType(data);
						var responseValue = getResponseValue(data);
						displaySuccessMsg(data);   
						  setTimeout("location.href = 'dishrequesthistory'",2000);
					  /*if(responseType == 'S')    {    
					        
					         displaySuccessMsg(data);   
								  setTimeout("location.href = 'dishrequesthistory'",2000); 
					        	
					}else{
						displayFailureMsg("",responseValue)
					}
					       */	
					  
					},
					type:'POST',
					url:"saveadddishrequest",
					data:JSON.stringify(dishobj)
					}
				);
		}else{
			displayAlertMsg("Qunatity must be greater than one");
		}
	}else{
		displayAlertMsg("Please Select any one Product");
	}
}