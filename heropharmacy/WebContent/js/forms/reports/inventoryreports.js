var reportObj = new Object();
function setdatevalues()
{
	var usergroup = $('#inventoryusertypetext').val();
	if(parseInt(usergroup) > 2)
	{
	$('#storeselectdiv').attr('style','display:none');
	}
else
	{
	$('#storeselectdiv').attr('style','display:""');
	}
	
	var start = moment().subtract(29, 'days');
    var end = moment();

    function cb(start, end) {
        $('#reportrange span').html(start.format('MMMM D, YYYY') + ' - ' + end.format('MMMM D, YYYY'));
    	/*$('#reportrange span').html(start.format('D/MM/YYYY') + ' - ' + end.format('D/MM/YYYY'));*/
    	reportObj = new Object();
    	reportObj.startdate = start.format('D/MM/YYYY');
    	reportObj.enddate = end.format('D/MM/YYYY');
    	
    	$('#startdatelabel').text(reportObj.startdate);
    	$('#enddatelabel').text(reportObj.enddate);
    }

    $('#reportrange').daterangepicker({
        startDate: start,
        endDate: end,
        ranges: {
           'Today': [moment(), moment()],
           'Yesterday': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
           'Last 7 Days': [moment().subtract(6, 'days'), moment()],
           'Last 30 Days': [moment().subtract(29, 'days'), moment()],
           'This Month': [moment().startOf('month'), moment().endOf('month')],
           'Last Month': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
        }
    }, cb);

    cb(start, end); 		
}

function loadreport()
{
	var rid = getParameterByName('rid');
	/*if(parseInt(rid) == 9 || parseInt(rid) == 15)*/
	if(parseInt(rid) == 9)
		{
		$('#reportrange').attr('style','display:none');
			
		}
	else
		{
		/*$('#reportrange').attr('style','display:""');*/
		}
	setdatevalues();
	
}

function generatereport(tableid)
{
var reportid = getParameterByName('rid');
var usergroup = $('#inventoryusertypetext').val();
var storeid = 0;
if(parseInt(usergroup) > 2)
	{
	storeid = $('#storeidtext').val();
	}
else
	{
	storeid = $('#reportsstoreidselect').val();
	}
reportObj.storeid = storeid;
console.log(reportObj)
$.ajax({
	type:'POST',
	contentType:'application/json',
	url:'inventoryreports/'+reportid,
	data:JSON.stringify(reportObj),
	success:function(data,textStatus,jqXHR)
	{
		var responseType = getResponseType(data);
		var responseValue = getResponseValue(data);
		var columns = [];
		if(parseInt(reportid) == 1)
			{
			columns = [
			            { data: 'store_name' },
			            { data: 'product_name' },
			            { data: 'product_code' },
			            { data: 'margin' },
			            { data: 'salescount' },
			            { data: 'salesprice' }
			        ];
			}
		else if(parseInt(reportid) == 2)
			{
			columns = [
			            { data: 'store_name' },
			            { data: 'customername' },
			            { data: 'email' },
			            { data: 'balance' },
			            { data: 'orderamount' },
			            { data: 'grosssales' },
			            { data: 'netsales' }
			        ];
			}
		else if(parseInt(reportid) == 4)
		{
		columns = [
		            { data: 'store_name' },
		            { data: 'orderdate' },
		            { data: 'pos_order_code' },
		            { data: 'custname' },
		            { data: 'pos_grand_total' },
		            { data: 'pos_status_desc' },
		            { data: 'pos_shipping_cost' },
		            { data: 'pos_netamount' }
		        ];
		}
		/*else if(parseInt(reportid) == 5)
		{
		columns = [
		            { data: 'description' },
		            { data: 'amount' }
		        ];
		}*/
		else if(parseInt(reportid) == 6)
		{
		columns = [
		            { data: 'orderedmonth' },
		            { data: 'orderedcount' },
		            { data: 'grosssales' },
		            { data: 'discount' },
		            { data: 'netsales' },
		            { data: 'shipping' },
		            { data: 'pos_tax_amount' }
		        ];
		}
		else if(parseInt(reportid) == 7)
		{
		columns = [
		            { data: 'ct_name' },
		            { data: 'grosssales' },
		            { data: 'discount' },
		            { data: 'netsales' }
		        ];
		}
		else if(parseInt(reportid) == 8)
		{
		columns = [
		            { data: 'TAX_NAME' },
		            { data: 'taxamount' }
		        ];
		}
		else if(parseInt(reportid) == 9)
		{
		columns = [
		            { data: 'store_name' },
		            { data: 'product_name' },
		            { data: 'company_name' },
		            { data: 'ordercount' }
		        ];
		}
		else if(parseInt(reportid) == 10)
		{
		columns = [
		            { data: 'receiveddate' },
		            { data: 'prhdr_id' },
		            { data: 'pur_req_id' },
		            { data: 'suppliername' },
		            { data: 'totalamount' },
		            { data: 'recvgqty' }
		        ];
		}
		else if(parseInt(reportid) == 11)
		{
		columns = [
		            { data: 'product_name' },
		            { data: 'prec_batchno' },
		            { data: 'recvgqty' },
		            { data: 'suppliername' },
		            { data: 'amount' },
		            { data: 'receiveddate' }
		        ];
		}
		else if(parseInt(reportid) == 12)
		{
		columns = [
		            { data: 'billstatus' },
		            { data: 'createdat' },
		            { data: 'prhdr_bill_no' },
		            { data: 'suppliername' },
		            { data: 'totalamount' },
		            { data: 'balance' }
		        ];
		}
		else if(parseInt(reportid) == 13)
		{
		columns = [
		            { data: 'suppliername' },
		            { data: 'total' },
		            { data: 'retun' },
		            { data: 'paid' },
		            { data: 'balance' }
		        ];
		}
		else if(parseInt(reportid) == 14)
		{
		columns = [
		            { data: 'store_name' },
		            { data: 'product_name' },
		            { data: 'company_name' },
		            { data: 'productcount' }
		        ];
		}
		else if(parseInt(reportid) == 15)
		{
		columns = [
		            { data: 'store_name' },
		            { data: 'product_name' },
		            { data: 'batch_id' },
		            { data: 'company_name' },
		            { data: 'productcount' },
		            { data: 'transferdate' },
		            { data: 'amount' }
		        ];
		}
		console.log(responseValue)
		displayRecordtoTable(tableid,responseValue,columns)
			}
});
}

function displayRecordtoTable(tableid,tableDataset,columns)
{
	var tableid = '#'+tableid;
	var currency = $('#currsymboltext').val();
	$(tableid).DataTable( {
		"destroy":true,
    	data: tableDataset,
    	dom: 'Bfrtip',
        buttons: [
             'excel', 'pdf'
        ],
        columns: columns,
        
        /*"footerCallback": function ( row, data, start, end, display ) {
            var api = this.api(), data;
 
            // Remove the formatting to get integer data for summation
            var intVal = function ( i ) {
                return typeof i === 'string' ?
                    i.replace(/[\$,]/g, '')*1 :
                    typeof i === 'number' ?
                        i : 0;
            };
 
            // Total over all pages
            total = api
                .column( 5 )
                .data()
                .reduce( function (a, b) {
                    return intVal(a) + intVal(b);
                }, 0 );
 
            // Total over this page
            pageTotal = api
                .column( 5, { page: 'current'} )
                .data()
                .reduce( function (a, b) {
                    return intVal(a) + intVal(b);
                }, 0 );
 
            // Update footer
            $( api.column( 4 ).footer() ).html(
                currency+" "+pageTotal +' ( '+currency+" ' "+ total +' total)'
            );
        }
*/    
        
    } ); 

}

function generatesalesfinancereport()
{
	
	var reportid = getParameterByName('rid');
	var usergroup = $('#inventoryusertypetext').val();
	var storeid = 0;
	if(parseInt(usergroup) > 2)
		{
		storeid = $('#storeidtext').val();
		}
	else
		{
		storeid = $('#reportsstoreidselect').val();
		}
	reportObj.storeid = storeid;
	console.log(reportObj)
	$.ajax({
		type:'POST',
		contentType:'application/json',
		url:'inventoryreports/'+reportid,
		data:JSON.stringify(reportObj),
		success:function(data,textStatus,jqXHR)
		{
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			
			var withoutReturnObj = responseValue[0];
			var withReturnObj = responseValue[1];
			
			console.log(withoutReturnObj['gross']);
			$('#grosssalesidtext').text((withoutReturnObj['gross']));
			$('#discountidtext').text((withoutReturnObj['discount']));
			$('#returnsidtext').text((withReturnObj['net']));
			$('#shippingcostidtext').text((withoutReturnObj['shippingcost']));
			$('#taxesidtext').text((withoutReturnObj['tax']));
			$('#totalsalesidtext').text((withoutReturnObj['net']));
		}
	
	});
}