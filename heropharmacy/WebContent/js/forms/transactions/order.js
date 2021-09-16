var orderArray=[]
function loadorderReqview(){
	var hdrid = getParameterByName('id');
   
	loadviewlist(hdrid)
}
function loadviewlist(hdrid){
	 $.ajax({
	    	type:'GET',
	    	contentType:'application/json',
	    	url:'getOrderRequestDetails/'+hdrid,
	    	success:function(data,textStatus,jqXHR)
	    	{
	    	
	    		var responseType = getResponseType(data);
	    		var responseValue = getResponseValue(data);
	    		var detilslist =responseValue["orderDtlList"]
	    		 var refNo=responseValue["orderHeaderList"][0]["ord_ref_no"];
	    		console.log(refNo)
	     $('#postrongid').html(refNo)
	    		if(responseType == 'S')
				{
				$('#itemlistDT').DataTable( {
					"destroy": true,
					"bPaginate": false,
					"bLengthChange": false,
			         "bFilter": false,
			         "bInfo": false,
			         "ordering":false,
			    	data: detilslist,
			        columns: [
			            { data: 'index' },
			            { data: 'companyname' },
			            { data: 'dish_name' },
			            { data: 'dishtype_name' },
			            { data: 'ord_dish_count' }
			        ]
			        
			    } );	 
				}
	    	}
	    })
}
function loadorderrequest(){
	var d = new Date();

	var currDate = d.getDate();
	var currMonth = d.getMonth()+1;
	var currYear = d.getFullYear();
	var today = ("0" + currDate).slice(-2) + "/" + ("0" + currMonth).slice(-2) + "/" + currYear;
    console.log(today)
    $('#orderdatetext').val(today);
	var hdrid = getParameterByName('id');
	$('#orderhdrid').val(hdrid);
	if(hdrid > 0){
		$('#oprntext').val("UPD")
		getOrderRequestDetails(hdrid);
	}else{
		$('#oprntext').val("INS")
	}
	getdishtype()
}
function getOrderRequestDetails(hdrid){
	
    $.ajax({
	type:'GET',
	contentType:'application/json',
	url:'getOrderRequestDetails/'+hdrid,
	success:function(data,textStatus,jqXHR)
	{
	
		var responseType = getResponseType(data);
		var responseValue = getResponseValue(data);
		    console.log(responseValue["orderHeaderList"][0]["date"])
		    var date = responseValue["orderHeaderList"][0]["date"];
		    var refNo=responseValue["orderHeaderList"][0]["ord_ref_no"];
		    $('#orderdatetext').val(date)
		     $('#referencenotext').val(refNo)
		     var detilslist = responseValue["orderDtlList"]
		   
		    $.each(detilslist, function (index, value) {
		    	 var indexPosition = orderArray.length+1;
		    	console.log(detilslist[index])
		    	var markup = "<tr><td>" + indexPosition + " </td>" +
	"<td>" + detilslist[index]["companyname"] + " </td>" +
	"<td>" + detilslist[index]["dish_name"] + " </td>" +
	"<td>" + detilslist[index]["dishtype_name"] + " </td>" +
	"<td><input type='number' id='quantity"+indexPosition+"' name='quantity"+indexPosition+"' value='"+detilslist[index]["ord_dish_count"]+"' onblur='updatequantity("+indexPosition+")'  style='width:80px';> </td>" +
	"<td><input class='btn white-bg fa-input red-font' type='button' value='&#xf014;' onclick='deleteproduct("+indexPosition+")' name='record"+indexPosition+"'> </td><tr>" 
$(markup).prependTo("table#addorderrequest > tbody");
    var object = new Object();
    object.index = indexPosition
    object.customername=detilslist[index]["companyname"]
    object.customerId=detilslist[index]["ord_customer_id"]
    object.dishname=detilslist[index]["dish_name"]
    object.dishid=detilslist[index]["ord_dish_id"]
    object.dishTypeName=detilslist[index]["dishtype_name"]
    object.dishtypeid=detilslist[index]["ord_dishtype_id"]
    object.dishCount = detilslist[index]["ord_dish_count"];
    orderArray.push(object)
    
		    });  
		   
	}
})

}
function getdishtype(){
	
	$('#dishtypeselect').empty();
	
	var dishid = $('#dishnameselect').val();
	var hdrid = $('#orderhdrid').val()
	
	
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
function changedishtype(){
	$('#dishtype').val($('#dishtypeselect').text())
}
function addmenu(){
   var additem = 1;
	var indexPosition = orderArray.length+1
	var customername = 	$( "#customerid option:selected" ).text();
	var customerId =$('#customerid').val()
    var dishname = $( "#dishnameselect option:selected" ).text();
    var dishId=$('#dishnameselect').val()
    var dishTypeName = $( "#dishtypeselect option:selected" ).text();
    var dishTypeId = $('#dishtypeselect').val()
	if(orderArray.length > 0){
		jQuery.grep(orderArray, function(value) {
			if(value.customerId === customerId && value.dishid === dishId && value.dishtypeid === dishTypeId){
				console.log("item 1")
				additem = 0
				displayFailureMsg("","Menu already added");
				return false;
			}
			   
			 });
	}else{
		console.log("item 3")
		additem = 1
	}
if(additem == 1){
	var markup = "<tr><td>" + indexPosition + " </td>" +
	"<td>" + customername + " </td>" +
	"<td>" + dishname + " </td>" +
	"<td>" + dishTypeName + " </td>" +
	"<td><input type='number' id='quantity"+indexPosition+"' name='quantity"+indexPosition+"' value='1' onblur='updatequantity("+indexPosition+")'  style='width:80px';> </td>" +
	"<td><input class='btn white-bg fa-input red-font' type='button' value='&#xf014;' onclick='deleteproduct("+indexPosition+")' name='record"+indexPosition+"'> </td><tr>" 
$(markup).prependTo("table#addorderrequest > tbody");
    var object = new Object();
    object.index = indexPosition
    object.customername=customername
    object.customerId=customerId
    object.dishname=dishname
    object.dishid=dishId
    object.dishTypeName=dishTypeName
    object.dishtypeid=dishTypeId
    object.dishCount = 1
    orderArray.push(object)
    console.log(orderArray)
}
}
function updatequantity(indexPosition)
{
		var quantity = $("td input[name='quantity"+indexPosition+"']").val();
		
		var updateProdObj;
		var removeItem;
		for (var indx in orderArray) {
		console.log(orderArray)
		updateProdObj = (orderArray[indx])
		console.log(updateProdObj)
		var prodArrayindex = updateProdObj.index;
     console.log("indexPosition "+indexPosition+"prodArrayindex"+prodArrayindex)
        if(indexPosition == prodArrayindex)
    	 {
        	
    	 removeItem = orderArray[indx];
    	 
    	 updateProdObj.dishCount = quantity;
    	 updateProdObj.index = indexPosition
    	 orderArray = jQuery.grep(orderArray, function(value) {
  		   return value != removeItem;
  		 });

    	 orderArray.push(updateProdObj);
    	 break;
    	 }
     
   }
		 
	console.log("final product array ");
	console.log(orderArray)
}
function deleteproduct(indexPosition){
	console.log(indexPosition)
	$("table#addorderrequest tbody").find("input[name='record"+indexPosition+"']").each(function(){
		$(this).parents("tr").remove();
        var position = parseInt(indexPosition)+1;
    	
    	for (var indx in orderArray) {
    		var tempObj = (orderArray[indx]);
    		if(tempObj.index >= position){
    			tempObj.index = tempObj.index - 1;
    			orderArray[indx].index = tempObj.index;
    			console.log(orderArray[indx].index)
    		}
    	}
    	
	var quantity = ($(this).val());
	var updateProdObj;
	var removeItem;
	for (var indx in orderArray) {
			
	updateProdObj = (orderArray[indx])
     var prodArrayindex = updateProdObj.index;
	console.log(prodArrayindex +" "+indexPosition)
     if(indexPosition == prodArrayindex)
    	 {
    	 removeItem = orderArray[indx];
    	 updateProdObj.dishCount = quantity;
    	console.log(removeItem)
    	 orderArray = jQuery.grep(orderArray, function(value) {
  		   return value != removeItem;
  		 });
  		 
      	break;
    	 }
     
   }
    		
    });	
	console.log(orderArray)
}
function editOrder(orderid){
	window.location.href="addOrderRequest?id="+orderid;
}
function deleteOrder(orderid){

confirmdelete(orderid)
}
function confirmdelete(orderid){
$('#myModal').modal('show');
$('.btn.btn-white').on('click',function()
		{
	var obj = new Object();
	obj.ordId = orderid;
	obj.date = "04/08/2021"
	obj.statusDesc = 'Pending';
	obj.userid=$('#herouseridtext').val();
	obj.oprn = "DEL";
	console.log(obj)
	
	$.ajax(
				{
					contentType:'application/json',
					success : function(data,textStatus,jqXHR)
					{
						var responseType = getResponseType(data);
						var responseValue = getResponseValue(data);
						$("#modal-delet").modal('hide');
						displaySuccessMsg(data);   
						  setTimeout("location.href = 'OrderRequestHistory'",2000);
		
					  
					},
					type:'POST',
					url:"saveorderrequest",
					data:JSON.stringify(obj)
					}
				);
			
			
			});
}
function saveorderRequest(){
	
	if(orderArray.length > 0 ){
		var obj = new Object();
		obj.ordId = $('#orderhdrid').val();
		obj.refNo = $('#referencenotext').val();
		obj.date = $('#orderdatetext').val();
		obj.itemlist = JSON.stringify(orderArray);
		obj.status = 1;
		obj.statusDesc = 'Pending';
		obj.userid=$('#herouseridtext').val();
		obj.oprn = $('#oprntext').val();
		console.log(obj)
		
		$.ajax(
				{
					contentType:'application/json',
					success : function(data,textStatus,jqXHR)
					{
						var responseType = getResponseType(data);
						var responseValue = getResponseValue(data);
						console.log(data)
						displaySuccessMsg(data);   
						  setTimeout("location.href = 'OrderRequestHistory'",2000);
					  /*if(responseType == 'S')    {    
					        
					         displaySuccessMsg(data);   
								  setTimeout("location.href = 'dishrequesthistory'",2000); 
					        	
					}else{
						displayFailureMsg("",responseValue)
					}
					       */	
					  
					},
					type:'POST',
					url:"saveorderrequest",
					data:JSON.stringify(obj)
					}
				);
	}else{
		displayAlertMsg("Please Select any one item");
	}
	
}