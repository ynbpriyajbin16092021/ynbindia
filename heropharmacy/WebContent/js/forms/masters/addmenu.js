var dishArray=[]
var customerid = 0;
function setday(day){
	$('#daytext').val(day);
	loadtable()
}
function loadmenu(){
	$('#foottimetext').val('Breakfast')
	$('#daytext').val('Monday')
	 customerid = getParameterByName('customerid');
	getdishtype();
	loadmenubyfood();
}
function tabevent(id){
	$('#foottimetext').val(id)
	loadtable();
}
function getdishtype(){
	
	$('#dishtypeselect').empty();
	
	var dishid = $('#dishnameselect').val();
	
	    $.ajax({
		type:'GET',
		contentType:'application/json',
		url:'getcustomerDishtypeList/'+dishid+'/'+customerid,
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
function loadmenubyfood(){
    $.ajax({
		type:'GET',
		contentType:'application/json',
		url:'getcustomermenuById/'+customerid,
		success:function(data,textStatus,jqXHR)
		{
		
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			
			var lsit= responseValue
			if(lsit.length > 0){
			 $.each(lsit, function (index, value) {
			var indexposition =dishArray.length +1;
			  var obj={"index":indexposition,"customerid":customerid,"day":lsit[index]["day"],"foodtime":lsit[index]["foodtime"],"dishid":lsit[index]["dishId"],
			"dishtypeid":lsit[index]["dishTypeId"],"dishcount":lsit[index]["dishcount"],"qtyperperson":lsit[index]["qtyperperson"],"dishname":lsit[index]["dishName"],"dishtypename":lsit[index]["dishtypeName"]}
			  dishArray.push(obj);
			  }); 
			 
		loadtable();
			}
		}
	})	
}
function loadtable(){
	$('table#addmenuDT > tbody').empty();
	jQuery.grep(dishArray, function(value) {
		
		if(value.day === $('#daytext').val() && value.foodtime === $('#foottimetext').val() ){
			var markup = "<tr>" +
			"<td>" + value.dishname + " </td>" +
			"<td>" +  value.dishtypename + " </td>" +
			"<td><input type='number' id='dishcount"+value.index+"' name='dishcount"+value.index+"' value='"+value.dishcount+"' onblur='updatequantity("+value.index+")'  style='width:80px';> </td>" +
			"<td><input type='number' id='totalcount"+value.index+"' name='totalcount"+value.index+"' value='"+value.qtyperperson+"'  onblur='updatequantity("+value.index+")'  style='width:80px';> </td>" +
			"<td><input class='btn white-bg fa-input red-font' type='button' value='&#xf014;' onclick='deletearray("+value.index+")' name='record"+value.index+"'> </td><tr>" 
		$(markup).prependTo("table#addmenuDT > tbody");
		}
		   
		 });
}
function addmenu(){

   var indexposition =dishArray.length +1;
	
	 var dishname = $( "#dishnameselect option:selected" ).text();
	 var dishTypeName = $( "#dishtypeselect option:selected" ).text();
	 if(dishArray.length > 0){
		 var additem = 0;
			jQuery.grep(dishArray, function(value) {
				if(value.day === $('#daytext').val() && value.foodtime === $('#foottimetext').val() &&  value.dishid ===$('#dishnameselect').val() && value.dishtypeid ===$('#dishtypeselect').val()){
					additem = 1
					displayFailureMsg("","Menu already added");
					return false;
				}else{
			
					additem = 0
				}
				   
				 });
			if(additem == 0){
				var obj={"index":indexposition,"customerid":customerid,"day":$('#daytext').val(),"foodtime":$('#foottimetext').val(),"dishid":$('#dishnameselect').val(),
						"dishtypeid":$('#dishtypeselect').val(),"dishcount":1,"qtyperperson":1,
						"dishname":dishname,"dishtypename":dishTypeName}
				dishArray.push(obj);
				loadtable()
			}
	 }else{
			var obj={"index":indexposition,"customerid":customerid,"day":$('#daytext').val(),"foodtime":$('#foottimetext').val(),"dishid":$('#dishnameselect').val(),
					"dishtypeid":$('#dishtypeselect').val(),"dishcount":1,"qtyperperson":1,
					"dishname":dishname,"dishtypename":dishTypeName}
			dishArray.push(obj);
			loadtable()
	 }

	
}
function updatequantity(indexPosition)
{
		var dishcount = $("td input[name='dishcount"+indexPosition+"']").val();
		var totalcount = $("td input[name='totalcount"+indexPosition+"']").val();
		
		var updateProdObj;
		var removeItem;
		for (var indx in dishArray) {
		console.log(dishArray)
		updateProdObj = (dishArray[indx])
		console.log(updateProdObj)
		var prodArrayindex = updateProdObj.index;
     console.log("indexPosition "+indexPosition+"prodArrayindex"+prodArrayindex)
        if(indexPosition == prodArrayindex)
    	 {
        	
    	 removeItem = dishArray[indx];
    	 
    	 updateProdObj.dishcount = dishcount;
    	 updateProdObj.qtyperperson = totalcount;
    	 updateProdObj.index = indexPosition
    	 dishArray = jQuery.grep(dishArray, function(value) {
  		   return value != removeItem;
  		 });

    	 dishArray.push(updateProdObj);
    	 break;
    	 }
     
   }
		 
	console.log("final product array ");
	console.log(dishArray)
}
function deletearray(indexPosition){
	console.log(indexPosition)
	$("table#addmenuDT tbody").find("input[name='record"+indexPosition+"']").each(function(){
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
	console.log(prodArrayindex +" "+indexPosition)
     if(indexPosition == prodArrayindex)
    	 {
    	 removeItem = dishArray[indx];
    	 updateProdObj.dishCount = quantity;
    	console.log(removeItem)
    	 dishArray = jQuery.grep(dishArray, function(value) {
  		   return value != removeItem;
  		 });
  		 
      	break;
    	 }
     
   }
    		
    });	
	console.log(dishArray)	
}
function savecustomermenu(){
	$(".changeContent").html("<img src='../../heroadmin/resources/images/loader_green.gif' width='100%' />");
	
	if(dishArray.length > 0 ){
		var obj = new Object();
		obj.customerid = customerid;
		obj.itemlist = JSON.stringify(dishArray);
		obj.userid = $('#inventoryuseridtext').val();
		$.ajax(
				{
					contentType:'application/json',
					success : function(data,textStatus,jqXHR)
					{
						var responseType = getResponseType(data);
						var responseValue = getResponseValue(data);
						/*displaySuccessMsg("Menu Created successfully");   */
						  setTimeout("location.href = 'companymaster'",2000);
					 
					},
					type:'POST',
					url:"savecustomermenu",
					data:JSON.stringify(obj)
					}
				);
	}else{
		displayAlertMsg("Please Select any one item");
	}	
}