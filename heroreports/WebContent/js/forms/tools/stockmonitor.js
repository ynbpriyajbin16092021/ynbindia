var rowClick = 0;
function loadstockmonitor()
{
	var storeid = $("#storeselect option:first").val();
	var manufacturerid = $("#manufacturerselect option:first").val();
	var categoryid = $("#categoryselect option:first").val();
	var usertype = $('#inventoryusertypetext').val();
	
	if(parseInt(usertype) > 2)
		{
		storeid = $('#storeidtext').val();
		$('#storediv').attr('style','display:none');
		}
	
	stockdetails(storeid,manufacturerid,categoryid);
}
function onchangefields()
{
	var storeid = $("#storeselect").val();
	var manufacturerid = $("#manufacturerselect").val();
	var categoryid = $("#categoryselect").val();
	
	stockdetails(storeid,manufacturerid,categoryid);
}
function stockdetails(storeid,manufacturerid,categoryid)
{
	
	
$.ajax({
	type:'GET',
	contentType:'applictaion/json',
	url:'stockmonitorlist/'+storeid+'/'+manufacturerid+'/'+categoryid,
	success:function(data,textStatus,jqXHR)
	{
		var responseType = getResponseType(data);
		var responseValue = getResponseValue(data);
		
		$('#stockmonitorDT').DataTable( {
			"destroy":true,
	    	data: responseValue,
	    	columns: [
	            
	            { data: 'productname' },
	            { data: 'categoryname' },
	            { data: 'productcode' },
	            { data: 'companyname' },
	            { data: 'productcount' }
	        ]
            
	    } );
		rowClick = 0;
		selectStockMtrItem();
	}
});
}

function selectStockMtrItem() {
	$('#stockmonitorDT tbody').on('click', 'tr', function() {
		rowClick = parseInt(rowClick) + 1;
		
		if(parseInt(rowClick) == 1)
			{
			var table = $('#stockmonitorDT').DataTable();
			console.log(table.row( $(this) ).data())
			var stockMonitorOBJ = table.row( $(this) ).data();
			/*showbatchdetails(stockMonitorOBJ.manufacturerid,stockMonitorOBJ.storeid,stockMonitorOBJ.categoryid);*/
			
			var storeid = $("#storeselect").val();
			var manufacturerid = $("#manufacturerselect").val();
			var categoryid = $("#categoryselect").val();
			
			showbatchdetails(stockMonitorOBJ.productid,storeid,categoryid);
			}
		
	});
}

function showbatchdetails(productid,storeid,categoryid)
{
	var productindex = 0;
$.ajax({
	type:'GET',
	url:'stockmonitorlistdetails/'+storeid+'/'+productid+'/'+categoryid,
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
		                  
		            { data: 'categoryname' },      
		            { data: 'productname' },
		            { data: 'batchno' },
		            { data: 'productcode' },
		            { data: 'productrate' },
		            { data: 'productcount' }
		        ]
	            
		    } ); 
			
			$('#togglebutton').click();
			}
	}
});
		
}