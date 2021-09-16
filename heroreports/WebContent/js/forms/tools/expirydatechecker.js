var expirytype = 0;
function loadexpirydatechecker()
{
	var storeid = $("#storeselect option:first").val();
	
	$('#radio1').attr('checked',true);
	expirytype = "0";
	$('#expireddaytext').attr('readonly',true);
	$('#expireddaytext').val(0);
	
	$('#expirydateDTdiv').attr('style','display:none');
	/*productdetails(storeid);*/
}
function onchangefields()
{
	var storeid = $("#storeselect").val();
	
	productdetails(storeid);
}
function checkradio(value)
{
	
if(parseInt(value) == 0)	
	{
	$('#expireddaytext').attr('readonly',true)
	$('#expireddaytext').val(0)
	}
else
	{
	$('#expireddaytext').attr('readonly',false)
	$('#expireddaytext').val(0)
	}
	expirytype = value;
	$('#expirydateDTdiv').attr('style','display:none');
	/*onchangefields();*/
}

function getproducts()
{
	onchangefields();
}
function productdetails(storeid)
{

	var expirydays = $('#expireddaytext').val();
	
$.ajax({
	type:'GET',
	contentType:'applictaion/json',
	url:'expiryproductlist/'+storeid+"/"+expirytype+"/"+expirydays,
	success:function(data,textStatus,jqXHR)
	{
		var responseType = getResponseType(data);
		var responseValue = getResponseValue(data);
		console.log("saraswathy responsevalue");
		console.log(responseValue);
		$('#expirydateDTdiv').attr('style','display:""');
		
		$('#expirydateDT').DataTable( {
			"destroy":true,
	    	data: responseValue,
	    	columns: [
	            
	            { data: 'productname' },
	            { data: 'manufacturername' },
	            { data: 'batchno' },
	            { data: 'expirydate' },
	            { data: 'productcount'}
	        ]
            
	    } );
		
	}
});
}
