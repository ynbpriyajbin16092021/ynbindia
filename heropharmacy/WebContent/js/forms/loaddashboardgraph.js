var calanderarray = [];
var vars = {};
var vars2 = {};
var varsP = {};
var varsP2 = {};
function loaddashboardgraph()
{
	$.ajax(
			{
			type:'GET',
			contentType:'application/json',
			url:'loaddashboardgraph',
			success:function(data,textStatus,jqXHR)
			{
				console.log("--------------------------------------")
				console.log(data);
				if(data['responseType'] == "S"){
					var objectArray = [];
					objectArray = data['responseObj']['graphStoreList'];
					var x_axis = [];
					var storeIds = [];
					$.each(objectArray, function(key, value){
						var inArrOutput = $.inArray( value.createddate, x_axis);
						if(inArrOutput< 0 ){
						x_axis.push(value.createddate);
						}
						var inArrOutputSI = $.inArray( value.storeid, storeIds);
							if(inArrOutputSI< 0 ){
								storeIds.push(value.storeid);
								vars['storeName' + value.storeid] = value.storename;
								vars2['storePoints' + value.storeid] = [];
								vars2['storePoints' + value.storeid].push(value.paidamount);
							}
							else{
								vars2['storePoints' + value.storeid].push(value.paidamount);
							}
					
					});
					/*console.log(x_axis);
					console.log(storeIds);
					console.log(vars);*/
					localStorage.setItem("x_axis", JSON.stringify(x_axis));
					localStorage.setItem("storeName", JSON.stringify(vars));
					localStorage.setItem("storePoints", JSON.stringify(vars2));
					console.log(vars2);

					var objectProductArray = [];
					objectProductArray = data['responseObj']['graphManufacturerList'];
					var x_axis_manufacturer = [];
					var manufacturerIds = [];
					
					$.each(objectProductArray, function(keyP, valueP){
						var inArrProductOutput = $.inArray( valueP.createddate, x_axis_manufacturer);
						if(inArrProductOutput< 0 ){
							x_axis_manufacturer.push(valueP.createddate);
						}
						var inArrOutputPI = $.inArray( valueP.manufacturerid, manufacturerIds);
							if(inArrOutputPI< 0 ){
								manufacturerIds.push(valueP.manufacturerid);
								varsP['manufacturerName' + valueP.manufacturerid] = valueP.manufacturername;
								varsP2['manufacturerPoints' + valueP.manufacturerid] = [];
								varsP2['manufacturerPoints' + valueP.manufacturerid].push(valueP.paidamount);
							}
							else{
								varsP2['manufacturerPoints' + valueP.manufacturerid].push(valueP.paidamount);
							}
					
					});
					/*console.log("---------------------------------");
					console.log(varsP)*/
					localStorage.setItem("x_axis_manufacturer", JSON.stringify(x_axis_manufacturer));
					localStorage.setItem("manufacturerName", JSON.stringify(varsP));
					localStorage.setItem("manufacturerPoints", JSON.stringify(varsP2));
					
					var objectSalesArray = [];
					objectSalesArray = data['responseObj']['graphSalesList'];
					var x_axis_sales = [];
					var yaxis_sales_amount = [];
					$.each(objectSalesArray, function(keyS, valueS){
						var inArrProductOutput = $.inArray( valueS.createddate, x_axis_sales);
						if(inArrProductOutput< 0 ){
							x_axis_sales.push(valueS.createddate);
							yaxis_sales_amount.push(valueS.paidamount);
						}
					});
					localStorage.setItem("x_axis_sales", JSON.stringify(x_axis_sales));
					localStorage.setItem("yaxis_sales_amount", JSON.stringify(yaxis_sales_amount));
					
					
					var objectCustomerDueArray = [];
					objectCustomerDueArray = data['responseObj']['graphCustomerDueList'];
					var x_axis_customer_due = [];
					var yaxis_customer_due = [];
					$.each(objectCustomerDueArray, function(keyC, valueC){
						var inArrProductOutput = $.inArray( valueC.createddate, x_axis_customer_due);
						if(inArrProductOutput< 0 ){
							x_axis_customer_due.push(valueC.createddate);
							yaxis_customer_due.push(valueC.paidamount);
						}
					});
					localStorage.setItem("x_axis_customer_due", JSON.stringify(x_axis_customer_due));
					localStorage.setItem("yaxis_customer_due", JSON.stringify(yaxis_customer_due));
					
					
					var objectTaxArray = [];
					objectTaxArray = data['responseObj']['graphSalesList'];
					var x_axis_tax = [];
					var yaxis_tax = [];
					$.each(objectSalesArray, function(keyT, valueT){
						var inArrProductOutput = $.inArray( valueT.createddate, x_axis_tax);
						if(inArrProductOutput< 0 ){
							x_axis_tax.push(valueT.createddate);
							yaxis_tax.push(valueT.paidamount);
						}
					});
					localStorage.setItem("x_axis_tax", JSON.stringify(x_axis_tax));
					localStorage.setItem("yaxis_tax", JSON.stringify(yaxis_tax));
					
					
					
					$.getScript('../../lib/dashboard.js');
				}
				
			}
				
			});	
}
