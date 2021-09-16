var reportObj = new Object();
var productLabels = [];


function changecxustomerselect(){
	var customerid = $('#customeridselect').val();
		reportObj = new Object();
//	reportObj.startdate = start.format('D/MM/YYYY');
//	reportObj.enddate = end.format('D/MM/YYYY');
	reportObj.startdate = $("#purchasedatetext").val();
	reportObj.enddate = $("#purchasedatetext").val();
	
	$('#startdatelabel').text(reportObj.startdate);
	$('#enddatelabel').text(reportObj.enddate);
	console.log(reportObj )
	$.ajax({
		type:'POST',
		contentType:'application/json',
		url:'orderReports/2/'+customerid,
		data:JSON.stringify(reportObj),
		success:function(data,textStatus,jqXHR)
		{
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
		
		    var originalLength = responseValue.length
		    if(originalLength ==0){
		    	$('#response').text("No data available")
		    			    }
		    if(originalLength > 0){
		    	$('#response').text("")
			var refno = responseValue[0]["ord_ref_no"]
			$('#refno').text(refno)
		    var remainingLength = 34-originalLength;
		    console.log(originalLength + " "+remainingLength )
		  
		    if(originalLength > 0){
		    if(originalLength == 17){
		    	  $.each(responseValue, function (index, value) {
		    		  var markup = "<tr><td>" +value.index  + " </td>" +
						"<td>" + value.dishtype_name + " </td>" +
						"<td>" + value.dish_count + " </td></tr>" 
						 $("table#deliverychallanfirst > tbody").append(markup);
		    	  })
		    	  for(i=0;i<17;i++){
		    		  index = originalLength+i+1;
						 var markup = "<tr><td>" + index  + " </td>" +
							"<td></td>" +
							"<td> </td></tr>" 
							 $("table#deliverychallansecond > tbody").append(markup);
					 }
		    }else if(originalLength < 17){
		    	  $.each(responseValue, function (index, value) {
		    		  var markup = "<tr><td>" +value.index  + " </td>" +
						"<td>" + value.dishtype_name + " </td>" +
						"<td>" + value.dish_count + " </td></tr>" 
						 $("table#deliverychallanfirst > tbody").append(markup);
		    	  })
		    	  for(i=0;i<remainingLength-17;i++){
		    		  index = originalLength+i+1;
						 var markup = "<tr><td>" + index  + " </td>" +
							"<td></td>" +
							"<td> </td></tr>" 
							 $("table#deliverychallanfirst > tbody").append(markup);
					 }
		    	  for(i=0;i<17;i++){
		    		  index = 17+i+1;
						 var markup = "<tr><td>" + index  + " </td>" +
							"<td></td>" +
							"<td> </td></tr>" 
							 $("table#deliverychallansecond > tbody").append(markup);
					 }
		    }else if(originalLength > 17){
		  	  $.each(responseValue, function (index, value) {
		  	  if(index < 17){
				  var markup = "<tr><td>" +value.index  + " </td>" +
					"<td>" + value.dishtype_name + " </td>" +
					"<td>" + value.dish_count + " </td></tr>" 
					 $("table#deliverychallanfirst > tbody").append(markup);
			  }
			  if(index > 16){
				  var markup = "<tr><td>" + value.index  + " </td>" +
					"<td>" + value.dishtype_name + " </td>" +
					"<td>" + value.dish_count + " </td></tr>" 
					 $("table#deliverychallansecond > tbody").append(markup);
			  }
		  	  })
			  for(i=0;i<remainingLength;i++){
				  index = originalLength+i+1;
					 var markup = "<tr><td> "+index+"</td>" +
						"<td></td>" +
						"<td> </td></tr>" 
						 $("table#deliverychallansecond > tbody").append(markup);
				 } 
		    }
		    
			$.ajax({
				type:'POST',
				contentType:'application/json',
				url:'getorgndetails',
				data:JSON.stringify(reportObj),
				success:function(data,textStatus,jqXHR)
				{
					var responseType = getResponseType(data);
					var responseValue = getResponseValue(data);
					console.log(responseValue)
					var orgnaddress = responseValue[0]["orgn_address"]
					var date = reportObj.startdate
					
					$('#address').text(orgnaddress)
					$('#date').text(date)
				}
			})
		}
		    
		}
		}
	} ); 
}
function setdatevalues()
{
	var usergroup = $('#inventoryusertypetext').val();
	if(parseInt(usergroup) > 2)
	{
	$('#storeselectdiv').attr('style','display:block');
	}
else
	{
	$('#storeselectdiv').attr('style','display:""');
	}
	
	
	var rid = getParameterByName('rid');
	
	if(rid==16 || rid==14){
		$('#storeselectdiv').attr('style','display:block');	
	}else{
		$('#storeselectdiv').attr('style','display:none');
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
	$("#purchasedatetext").val($.datepicker.formatDate("dd/mm/yy", new Date()));
	setdatevalues();
	
}
function loadstorerequestreport(){
	$("#storerequestreportDT thead").empty();
	$("#storerequestreportDT tbody").empty()
	$.ajax({
		type:'POST',
		contentType:'application/json',
		url:'loadstorerequestreport',
		data:JSON.stringify(reportObj),
		success:function(data,textStatus,jqXHR)
		{
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
          
           if(responseType == 'S'){
        	   var companylist = responseValue["customerList"];
        	   var distypelist = responseValue["dishList"];
        	   var dishcountlist = responseValue["dishcountList"];
        	   var finallist = [];
        	   var totallist = [];
        	   $.each(distypelist, function (dishindex, dishvalue) {
        		  var  finalsinglelist= [];
        		  finalsinglelist.push(dishindex+1);
        		  finalsinglelist.push(dishvalue.dishtype_name);
        		   $.each(companylist, function (companyindex, companyvalue) {
            		   var dishid = dishvalue.dishtypeid;
            		  
            		  var singledishcountlist = dishcountlist[dishid];
            		  
            		   var customerid = companyvalue.customer_id;
            		
            		   var result = $.grep(singledishcountlist, function(e){ 
            			
            			   return  e.customerid == customerid; 
            		   });
            		  
            		   if(result.length >0 && result[0].dishcount !=null && result[0].dishcount >0  ){
            			   finalsinglelist.push(result[0].dishcount)
            		   }else{
            			   finalsinglelist.push(0);
            		   }
            	   })
            	   
            	   finallist.push(finalsinglelist);
        		   totallist.push(finalsinglelist);
        		
        	   });
        	  
        	   var someRow=" <tr class='someClass' ><th > SL.NO</th><th>Food</th>" 
        	   $.each(companylist, function (index, value) {
        		   
        		   someRow += " <th>"+companylist[index].companyname+"</th>";
        	   })
        	   someRow += " <th>Total</th><tr>";
        	 
        	   
        	   var tableDetailsData = "";''
        	  $(totallist).each(function(key,value){
        		   var removeValFrom = [0, 1];
        		   var total = 0;
        		   totallist[key] = totallist[key].filter(function(value, index) {
        			     return removeValFrom.indexOf(index) == -1;
        			})
        			
        		   var total = 0;
        		   for (var i = 0; i < totallist[key].length; i++) {
        		       total += totallist[key][i] << 0;
        		   }
        		   finallist[key].push(total)
        		   
        	   });
        	  
        	   
        	   $(finallist).each(function(key,value){
        		  
        		   tableDetailsData += "<tr>";
        		   		$(value).each(function(keyv,valuev){
        		   		
        		   			tableDetailsData += "<td>"+valuev+"</td>";
        		   		});
        		   	 tableDetailsData += "</tr>";
        	   });
        	 
        		   $("#storerequestreportDT thead").append(someRow);
        	   $("#storerequestreportDT tbody").append(tableDetailsData);
           }
		}
	} ); 
}
function generateorderreceivingreport(status){
	
	var customerId = $('#exampleFormControlSelect1').val()
	var customername ="Customer Name:" +$('#exampleFormControlSelect1 option:selected').text()
	$('#customername').html(customername)
	$.ajax({
		type:'POST',
		contentType:'application/json',
		url:'orderReports/'+status+'/'+customerId,
		data:JSON.stringify(reportObj),
		success:function(data,textStatus,jqXHR)
		{
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			if(status == 1){
	$('#orderreceivngDT').DataTable( {
		"destroy":true,
    	
    	dom: 'lBfrtip',
        buttons: [
         
             { extend: 'pdf', className: 'dataTableButton' },
			 { extend: 'excel', className: 'dataTableButton' }
        ],
       
        data: responseValue,
        columns: [
      			            { data: 'index' },
      			            { data: 'dish_name' },
      			            { data: 'dishtype_name' },
      			          { data: 'dish_count' }
      			        ]
    } ); 
		}else{
			$('#ordermakingDT').DataTable( {
				"destroy":true,
		    	
		    	dom: 'lBfrtip',
		        buttons: [
		         
		             { extend: 'pdf', className: 'dataTableButton' },
					 { extend: 'excel', className: 'dataTableButton' }
		        ],
		       
		        data: responseValue,
		        columns: [
		      			            { data: 'index' },
		      			            { data: 'dish_name' },
		      			            { data: 'dishtype_name' },
		      			          { data: 'dish_count' }
		      			        ]
		    } ); 
		}
		}
	} ); 
}

function printClick() {  
    var w = window.open('', 'printform', 'width=600,height=600');  
    var html = $("#section-to-print").html(); 
    $(w.document.body).html(html+"<style>thead{background:black;color:white;font-size:20px;}table{width:1200;border-collapse: separate;border-spacing: 0px 10px;}tr{border-spacing: 0px 20px;height:45px;}h5{text-align:center;}h2{text-align:center;};</style>");  
    w.print();
}

function generatereport(tableid)
{
console.log("generate report")
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
reportObj.storeid = $('#reportsstoreidselect').val();
console.log(reportObj)
$.ajax({
	type:'POST',
	contentType:'application/json',
	url:'inventoryreports/'+reportid,
	data:JSON.stringify(reportObj),
	success:function(data,textStatus,jqXHR)
	{
		console.log(data);
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
	//	            { data: 'prhdr_id' },
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
	//	            { data: 'prec_batchno' },
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
		            //{ data: 'retun' },
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
		//            { data: 'store_name' },
		            { data: 'product_name' },
		//            { data: 'batch_id' },
		            { data: 'company_name' },
		            { data: 'productcount' },
		            { data: 'transferdate' },
		//            { data: 'amount' }
		        ];
		}
		else if(parseInt(reportid) == 17)
		{
		columns = [
		           
		            { data: 'product_name' },
		            { data: 'opening' },
		            { data: 'purchase' },
		            { data: 'retrn' },
		            { data: 'transfer' },		           
		            { data: 'closing' }
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
    	dom: 'lBfrtip',
        buttons: [
            /* 'excel', 'pdf'*/
             { extend: 'pdf', className: 'dataTableButton' },
			 { extend: 'excel', className: 'dataTableButton' }
        ],
        columns: columns,
     
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

function loadproducts()
{
	var userid = $('#inventoryuseridtext').val();
	
	$.ajax(
			{
				contentType:'application/json',
				success : function(data,textStatus,jqXHR)
				{
					
					
					var responseType = getResponseType(data);
					var responseValue = getResponseValue(data);
					//console.log(responseValue)
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
				          console.log(ui.item.label)
				          console.log(ui.item.value)
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




function checkAvailableProduct(){
	var productValue = $('#itemcodetext').val();
	console.log(productValue)
	if(productValue != ""){
		
	 var productvalid = jQuery.inArray( productValue, productLabels );
	 
	if(productvalid < 0){
		heroNotifications.showFailure("Please Select the Product in your Product list otherwise Create a New Product and try later");
		$('#itemcodetext').val("");
	}
	}

}

function split( val ) {
    return val.split( /,\s*/ );
}
function extractLast( term ) {
  return split( term ).pop();
}

function generatePurchaseByProductreport(tableid)
{
console.log(tableid)
var reportid = getParameterByName('rid');
var usergroup = $('#inventoryusertypetext').val();
var storeid = 0;
var productid =$('#productcodetext').val();


$.ajax({
	type:'GET',
	contentType:'application/json',
	url:'getpurchasebyproduct/'+productid,
	success:function(data,textStatus,jqXHR)
	{
		
		var responseType = getResponseType(data);
		var responseValue = getResponseValue(data);
		console.log(responseValue);
		
		if(responseValue.length > 0){
			
	
		var columns = [];
		if(parseInt(reportid) == 16)
			{
			columns = [
			           /* { data: 'productname' },*/
			            { data: 'purchasecode' },
			            { data: 'qty' },
			            { data: 'suppliername' },
			            { data: 'amount' },
			            { data: 'date' }
			        ];
			}
		//$('#itemcodetext').val("");
		 
		displayRecordtoTable(tableid,responseValue,columns);
		}else{
			$('#itemcodetext').val("");
			displayAlertMsg("Product Not purchased Yet");
		}
			}
});
}
function generateOutputQtyReport(tableid)
{
var usergroup = $('#inventoryusertypetext').val();
var storeid = 0;
var transferid =$('#transferid').val();
console.log(transferid)
$.ajax({
	type:'GET',
	contentType:'application/json',
	url:'getOutputQtyReport/'+transferid,
	success:function(data,textStatus,jqXHR)
	{
		
		var responseType = getResponseType(data);
		var responseValue = getResponseValue(data);
		console.log(responseValue);
		
		
			
			$('#outputQtyDT').DataTable( {
				data: responseValue,	
				destroy: true,
				dom: 'lBfrtip',
		        buttons: [		          
		             { extend: 'pdf', className: 'dataTableButton' },
					 { extend: 'excel', className: 'dataTableButton' }
		        ],
		        columns: [
		            { data: 'transfercode' },
		            { data: 'productname' },
		            { data: 'qty' }	
		        ]		            
		    } );	
		
	
			}
});
}


function generateStockReport()
{
var usergroup = $('#inventoryusertypetext').val();
var storeid = 0;

$.ajax({
	type:'GET',
	contentType:'application/json',
	url:'generateStockReport/'+reportObj.startdate+'/'+reportObj.enddate,
	success:function(data,textStatus,jqXHR)
	{
		
		var responseType = getResponseType(data);
		var responseValue = getResponseValue(data);
		console.log(responseValue);
		
		$('#stockDT').DataTable( {
				data: responseValue,	
				destroy: true,
				dom: 'lBfrtip',
		        buttons: [		          
		             { extend: 'pdf', className: 'dataTableButton' },
					 { extend: 'excel', className: 'dataTableButton' }
		        ],
		        columns: [
		            { data: 'transfercode' },
		            { data: 'productname' },
		            { data: 'qty' }	
		        ]		            
		    } );	
		
	
			}
});
}

