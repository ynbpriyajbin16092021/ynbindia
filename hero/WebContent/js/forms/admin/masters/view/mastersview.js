function loadproductview()
{
var productlistsize = $('#productlistsize').val();
var firstid = $('#fistproductid').val();

for(var index = 0;index < parseInt(productlistsize); index++)
	{
	if(parseInt($('#productcheck'+index).val()) == parseInt(firstid))
		{
		$('#productcheck'+index).prop('checked',true);
		}
	else
		{
		$('#productcheck'+index).prop('checked',false);
		}
	}

getproductdetails(firstid);
}

function getproductdetails(productid)
{
	var userid = $('#inventoryuseridtext').val();
$.ajax({
	type:'GET',
	url:'productviewinfo/'+productid+'/'+userid,
	contentType:'application/json',
	success:function(data,textStatus,jqXHR)
	{
		var responseType = getResponseType(data);
		var responseValue = getResponseValue(data);
		
		if(responseType == 'S')
			{
			var productlist = responseValue['productlist'];
			var storelist = responseValue['storeList'];
			var availstock = responseValue['availstock'];
			var tobemoved = responseValue['tobemovedcount'];
			var tobeshipped = parseInt(availstock) - parseInt(tobemoved);
			var batchlist = responseValue['batchList'];
			var purchaselist = responseValue['purchaseList'];
			var transferlist = responseValue['transferList'];
			var saleslist = responseValue['salesList'];
			
			var productObj = productlist[0];
			productinfo(productObj);
			displayStoreList(storelist);
			$('#availstockid').text(availstock);
			$('#tobemovedtd').text(tobemoved);
			$('#tobeshippedtd').text(tobeshipped);
			displayBatchList(batchlist);
			displayPurchaseList(purchaselist);
			displayTransferList(transferlist);
			displaySalesList(saleslist);
			
			}
		
	}
});	
}

function productinfo(productObj)
{
	$('#productnametd').text(productObj.productname);
	$('#productNameHeadingText').text(productObj.productname);
	$('#skutdid').text(productObj.productcode);
	$('#producttypetd').text(productObj.categoryname);
	$('#uomtd').text(productObj.unit);
	$('#notifytd').text(productObj.alertcount);
	$('#manufacturertd').text(productObj.manufacturerdesc);
	$('#descriptiontd').text(productObj.description);

	if(parseInt(productObj.statusid) == 1)
		{
		$('#statusinactivetd').hide();
		$('#statusactivetd').show();
		}
	else
		{
		$('#statusinactivetd').show();
		$('#statusactivetd').hide();
		}	
}

function displayStoreList(storelist)
{
	$('#storelistDT').DataTable( {
		"destroy": true,
		"bPaginate": true,
    	data: storelist,
        columns: [
            { data: 'storename' },
            { data: 'productcount' }
        ]
        
    } );
}

function displayBatchList(batchlist)
{
	$('#batchlistDT').DataTable( {
		"destroy": true,
		"bPaginate": true,
    	data: batchlist,
        columns: [
            { data: 'batchno' },
            { data: 'orderedqty' },
            { data: 'availqty' },
            { data: 'mfddate' },
            { data: 'expirydate' },
            { data: 'unitprice' }
        ]
        
    } );
}

function displayPurchaseList(purchaselist)
{
	$('#purchaselistDT').DataTable( {
		"destroy": true,
		"bPaginate": true,
    	data: purchaselist,
        columns: [
            { data: 'purchasedate' },
            { data: 'purchasecode' },
            { data: 'suppliername' },
            { data: 'purchaseqty' },
            { data: 'totalamount' },
            { data: 'taxamount' },
            { data: 'grandtotalamount' }
        ]
        
    } );
}

function displayTransferList(transferlist)
{
	$('#transferlistDT').DataTable( {
		"destroy": true,
		"bPaginate": true,
    	data: transferlist,
        columns: [
            { data: 'transferdate' },
            { data: 'transferno' },
            { data: 'productcount' },
            { data: 'storename' },
            { data: 'batchid' },
            { data: 'amount' }
        ]
        
    } );
}

function displaySalesList(saleslist)
{
	$('#salesDT').DataTable( {
		"destroy": true,
		"bPaginate": true,
    	data: saleslist,
        columns: [
            { data: 'posbatchid' },
            { data: 'custfirstname' },
            { data: 'possalescount' },
            { data: 'storename' },
            { data: 'possubtotal' }
        ]
        
    } );
}

function loadsupplierview()
{
var supplierlistsize = $('#supplierlistsize').val();
var firstid = $('#fistsupplierid').val();

for(var index = 0;index < parseInt(supplierlistsize); index++)
	{
	if(parseInt($('#suppliercheck'+index).val()) == parseInt(firstid))
		{
		$('#suppliercheck'+index).prop('checked',true);
		}
	else
		{
		$('#suppliercheck'+index).prop('checked',false);
		}
	}

getsupplierdetails(firstid);
}


function getsupplierdetails(supplierid)
{
	$('#fistsupplierid').val(supplierid);
	var supplierstatusid = $('#statusselect').val();
	$.ajax({
		type:'GET',
		url:'supplierviewinfo/'+supplierid+'/'+supplierstatusid,
		contentType:'application/json',
		success:function(data,textStatus,jqXHR)
		{
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			
			if(responseType == 'S')
				{
				var supplierlist = responseValue['supplierlist'];
				var purchaseList = responseValue['purchaseList'];
				var paymenttypelist = responseValue['paymenttypeList'];
				var contactList=responseValue['contactList'];
				
				var supplierObj = supplierlist[0];
				supplierinfo(supplierObj);
				displaysupplierpurchaselist(purchaseList);
				displaypaymenttypelist(paymenttypelist);
				displaycontactlist(contactList);
				}
			
		}
	});	
}

function displaycontactlist(contactList)
{
	console.log(contactList);
	var oprn = "UPD";
	var html= "";
	$.each(contactList, function(key,value){
		 html+='<tr>\
            <td>'+ value.iscprefix+'  '+value.isccontactname+'<br>\
        '+value.iscemail+'<br>\
       '+value.iscid+'<br>\
       '+value.iscphoneno+'<br>\
       '+value.iscdesignation+'<br>\
      </td>\
      <td>\
        <div class="btn-group pull-right cursor-pointer">\
          <button data-toggle="dropdown" class="btn btn-default btn-sm dropdown-toggle">\
            <i class="fa fa-cog" aria-hidden="true"></i><b class="caret"></b>\
          </button>\
          <ul class="dropdown-menu" >\
            <li><a  data-ember-action-1926="1926" onclick="setcontact(\''+value.iscid+'\',\''+value.supplierid+'\',\''+ value.iscprefix+'\',\
            	\''+value.isccontactname+'\',\''+value.iscemail+'\',\''+value.iscphoneno+'\',\''+value.iscdesignation+'\',\''+oprn+'\')">Edit</a></li>\
            <li><a  data-ember-action-1928="1928" onclick="deletecontact('+value.iscid+')">Delete</a>\
            <a  data-ember-action-1928="1928" id="deletecontactid" style="display: none;"\
             class="delete myBtnTab" data-toggle="modal" data-target="#modal-delet">Delete</a>\
            </li>\
      </ul>\
        </div>\
      </td>\
    </tr>';
	});
	
	console.log("html"+html);
	$(".dynamicdatafromload").html(html);
}

function supplierinfo(supplierObj)
{
	$('#supplierNameStrongId').text(supplierObj.suppliername);
	$('#suppliernametdid').text(supplierObj.suppliername);
	$('#mobiletdid').text(supplierObj.mobile);
	$('#mailidtdid').text(supplierObj.emailid);
	$('#addresstdid').text(supplierObj.address);
	$('#citytdid').text(supplierObj.city);
	$('#statetdid').text(supplierObj.state);
	$('#zipcodetdid').text(supplierObj.zipcode);
	$('#countrytdid').text(supplierObj.countryid);
	
}

function displaysupplierpurchaselist(purchaselist)
{
	
	$('#purchaseDT').DataTable( {
		"destroy": true,
		"bPaginate": true,
    	data: purchaselist,
        columns: [
            { data: 'purchasedate' },
            { data: 'purchasecode' },
            { data: 'purchaseqty' },
            { data: 'status' },
            { data: 'totalamt' },
            { data: 'paidstatus' },
            { data: 'balance' }
        ]
        
    } );
}

function changestatus(value)
{
	var firstid = $('#fistsupplierid').val();
	getsupplierdetails(firstid);
}

function displaypaymenttypelist(paymenttypeList)
{
	
	$('#paymenttypeDT').DataTable( {
		"destroy": true,
		"bPaginate": true,
    	data: paymenttypeList,
        columns: [
            { data: 'paymentdate' },
            { data: 'billno' },
            { data: 'refno' },
            { data: 'totalamount' },
            { data: 'receivedby' },
            { data: 'paymenttype' }
        ]
        
    } );
}

function savesuppliercontact()
{
var contactObj = new Object();

contactObj.iscid = $('#iscidtext').val();
contactObj.supplierid = $('#fistsupplierid').val();
contactObj.prefix = $('#contactprefixselect').val();
contactObj.contactname = $('#contactnametext').val();
contactObj.email = $('#emailtext').val();
contactObj.contactphone = $('#contactphonetext').val();
contactObj.designation = $('#designationtext').val();
contactObj.userid = $('#inventoryuseridtext').val();
contactObj.oprn = $('#oprntext').val();
console.log(contactObj);
$.ajax({
	type:'POST',
	url:'savesuppliercontact',
	contentType:'applicatoin/json',
	data:JSON.stringify(contactObj),
	success:function(data,textStatus,jqXHR)
	{
		var responseType = getResponseType(data);
		var responseValue = getResponseValue(data);
		alert(responseValue);
		/*clearsuppliercontactfields();*/
		if(responseType == 'S')
			{
			location.reload(false);	
			}
		
	}
});
}

function clearsuppliercontactfields()
{
	$('#iscidtext').val('0');
	$('#contactprefixselect').val('Mr.');
	$('#contactnametext').val('');
	$('#emailtext').val('');
	$('#contactphonetext').val('');
	$('#designationtext').val('');
	$('#oprntext').val('INS');
}

function setcontact(iscid,supplierid,prefix,contactname,email,phoneno,desgn,oprn)
{
	$('#iscidtext').val(iscid);
	$('#fistsupplierid').val(supplierid);
	$('#contactprefixselect').val(prefix);
	$('#contactnametext').val(contactname);
	$('#emailtext').val(email);
	$('#contactphonetext').val(phoneno);
	$('#designationtext').val(desgn);
	$('#oprntext').val(oprn);
	$("#contact").modal('show');
	//$('#contactpopup').click();	
}

function deletecontact(iscid)
{
	$('#iscidtext').val(iscid);
	$('#oprntext').val('DEL');	
	$('#modal-delet').modal('show');
}

function setnewsupplier()
{
	$('#iscidtext').val('0');
	$('#contactprefixselect').val('Mr.');
	$('#contactnametext').val('');
	$('#emailtext').val('');
	$('#contactphonetext').val('');
	$('#designationtext').val('');
	$('#oprntext').val('INS');
	$("#contact").modal('show');
	//$('#contactpopup').click();
}