function listdeletedpo(){
		$.ajax({
			type:'GET',
			contentType:'application/json',
			url:'purchaseorderdelhistory',
			success:function(data,textStatus,jqXHR)
			{
			
				var responseType = getResponseType(data);
				var responseValue = getResponseValue(data);
				
				$('#listeddelDT').DataTable( {
					"destroy": true,
					"bPaginate": false,
					"ordering":false,
			    	data: responseValue,
			        columns: [
			            { data: 'index' },
			            { data: 'purchasecode' },
			            { data: 'suppliername' },
			            { data: 'amount' },
			            { data: 'purchasestatusdesc' },
			            { data: 'purchasedate' },
			            { data: 'remarks' },
			            { data: 'action' }
			        ]
		            
			    } );
			}
		});
}

function opendelpoview(pid){
	$.ajax({
		type:'POST',
		contentType:'application/json',
		url:'updatereadnotificationstatus/'+pid,
		
		success:function(data,textStatus,jqXHR)
		{
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			
			console.log(responseValue);
		}
	});
}

function viewdeletedpo(){
	var pid=getParameterByName('pid');
	$.ajax({
		type:'GET',
		contentType:'application/json',
		url:'purchaseorderdelhistoryview/'+pid,
		success:function(data,textStatus,jqXHR)
		{
		
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			
			$('#viewdelDT').DataTable( {
				"destroy": true,
				"bPaginate": false,
				"ordering":false,
		    	data: responseValue,
		        columns: [
		            { data: 'index' },
		            { data: 'purchasecode' },
		            { data: 'productname' },
		            { data: 'fullqty' },
		            { data: 'looseqty' },
		            { data: 'totalqty' }
		        ]
	            
		    } );
		}
	});
}