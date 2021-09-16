function loadlowstock()
{
	$.ajax({
		type:'GET',
		url:'lowstockdatas',
		contentTYpe:'application/json',
		success:function(data,textStatus,jqXHR)
		{
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			//console.log(data);
			$('#lowstockDT').DataTable( {
				"destroy": true,
				"bPaginate": false,
				"ordering":false,
		    	data: responseValue,
		        columns: [
		            { data: 'productname' },
		            { data: 'notifyqty' },
		            { data: 'productcount' },
		            { data: 'storename' }
		        ]
	            
		    } );
		}
	})
}

function productdetails(storeid)
{
$.ajax({
	type:'GET',
	contentType:'applictaion/json',
	url:'lowstockproductlist/'+storeid,
	success:function(data,textStatus,jqXHR)
	{
		var responseType = getResponseType(data);
		var responseValue = getResponseValue(data);
		//console.log(" responsevalue");
		//console.log(responseValue);
		$('#lowstockDT').DataTable( {
			"destroy": true,
			"bPaginate": false,
			"ordering":false,
	    	data: responseValue,
	        columns: [
	            { data: 'productname' },
	            { data: 'notifyqty' },
	            { data: 'productcount' },
	            { data: 'storename' }
	        ]
            
	    } );
	}
});
}