/*Category Master Script Start*/

function loadcategory()
{
	validateoninputtyping('#categorytext','input');
$.ajax({
	type:'GET',
	contentType:'application/json',
	url:'categorylist',
	success:function(data,textStatus,jqXHR)
	{
		var responseType = getResponseType(data);
		var responseValue = getResponseValue(data);
		console.log(data);
		if(responseType == 'S')
			{
			var dataTableSet = $.parseJSON(responseValue);
			displayCategoryDatalist(dataTableSet);
			}

	}
});
}

function loadbank()
{
	validateoninputtyping('#banktext','input');
$.ajax({
	type:'GET',
	contentType:'application/json',
	url:'banklist',
	success:function(data,textStatus,jqXHR)
	{
		var responseType = getResponseType(data);
		var responseValue = getResponseValue(data);
		if(responseType == 'S')
		{
		var dataTableSet = $.parseJSON(responseValue);
		displayBankDatalist(dataTableSet);
		} 
		
	}
});
}


function displayCategoryDatalist(dataTableSet) {
	$('#categoryDT').DataTable({
		destroy : true,
		data : dataTableSet,
		"fnCreatedRow": function( nRow, aData, iDataIndex ) {
			/*$('td:eq(1)', nRow).append("<label style='cursor:pointer;'>Edit</label><img src='../resources/images/delete.jpg' width='30px;' height='30px' id='editbtn'></img>");*/
			$('td:eq(1)', nRow).append('<button class="edit myBtnTab" > <i class="fa fa-edit"></i> </button><button class="delete myBtnTab" data-toggle="modal" data-target="#modal-delet" > <i class="fa fa-remove"></i> </button>  ');
		}
	});
	selectCategoryItem();
}

function displayBankDatalist(dataTableSet) {
	$('#bankDT').DataTable({
		destroy : true,
		data : dataTableSet,
		"fnCreatedRow": function( nRow, aData, iDataIndex ) {
			/*$('td:eq(1)', nRow).append("<label style='cursor:pointer;'>Edit</label><img src='../resources/images/delete.jpg' width='30px;' height='30px' id='editbtn'></img>");*/
			$('td:eq(1)', nRow).append('<button class="edit myBtnTab" > <i class="fa fa-edit"></i> </button><button class="delete myBtnTab" data-toggle="modal" data-target="#modal-delet" > <i class="fa fa-remove"></i> </button>  ');
		}
	});
	selectBankItem();
}
 
function selectCategoryItem() {
	$('#categoryDT tbody').on('click', '.delete.myBtnTab', function() {
		var table = [];
		table = $('#categoryDT').DataTable();
		var categoryObject = [];
		categoryObject.push(table.row( $(this).parents('tr') ).data());
		/*categoryObject.push(table.row(this).data());*/
		console.log(categoryObject)
		var object = categoryObject[0];
		$.each(object, function(index, value) {
			$('#categoryidtext').val(object[2]);
			$('#categorytext').val(object[0]);
			console.log(object[2]);
			$('#oprntext').val("UPD");
			
		});
		
		var table = $('#categoryDT').DataTable();
		confirmCategoryDelete(table.row($(this).parents('tr')));
		
	});
	$('#categoryDT tbody').on('click', '.edit.myBtnTab', function() {
		var table = $('#categoryDT').DataTable();
		var categoryObject = [];
		categoryObject.push(table.row( $(this).parents('tr') ).data());
		/*categoryObject.push(table.row(this).data());*/
		console.log(categoryObject)
		var object = categoryObject[0];
		$.each(object, function(index, value) {
			$('#categoryidtext').val(object[2]);
			console.log(object[2]);
			$('#categorytext').val(object[0]);
			$('#oprntext').val("UPD");
			
		});
		$('#myModal').modal('show');
		/* $this = $(this).parents('tr').find('.sorting_1');
		    var input = $('<input />', {
		        'type': 'text',
		            'class': 'test',
		            'value': $(this).text()
		    });
		    $(this).replaceWith(input);*/
		
	});
}

function selectBankItem() {
	$('#bankDT tbody').on('click', '.delete.myBtnTab', function() {
		var table = $('#bankDT').DataTable();
		var bankObject = [];
		bankObject.push(table.row( $(this).parents('tr') ).data());
		/*categoryObject.push(table.row(this).data());*/
		console.log(bankObject)
		var object = bankObject[0];
		$.each(object, function(index, value) {
			$('#bankidtext').val(object[2]);
			$('#banktext').val(object[0]);
			
			$('#oprntext').val("UPD");
			
		});
		
		var table = $('#bankDT').DataTable();
		confirmBankDelete(table.row($(this).parents('tr')));
		
	});
	$('#bankDT tbody').on('click', '.edit.myBtnTab', function() {
		var table = $('#bankDT').DataTable();
		var bankObject = [];
		bankObject.push(table.row( $(this).parents('tr') ).data());
		/*categoryObject.push(table.row(this).data());*/
		console.log(bankObject)
		var object = bankObject[0];
		$.each(object, function(index, value) {
			$('#bankidtext').val(object[2]);
			$('#banktext').val(object[0]);
			$('#oprntext').val("UPD");
			
		});
		$("#myModal").modal('show');
		
	});
}


function confirmCategoryDelete(tableRow)
{
/*$('.btn.btn-white').on('click',function()
{*/
	//alert("saras");
	console.log(tableRow);
	$('#oprntext').val("DEL");
	savecategory();
	tableRow.remove().draw();
	clearCategoryFormValues();
	location.reload(true);
//	/*});*/
}


function confirmBankDelete(tableRow)
{
/*$('.btn.btn-white').on('click',function()
{
*/	console.log(tableRow);
	$('#oprntext').val("DEL");
	savebank();
	tableRow.remove().draw();
	clearBankFormValues();
	/*});*/
}

function savecategory()
{
var oprn = $('#oprntext').val();
if(oprn != 'DEL')
	{
	validateonbuttonclick('#categorytext','input');	
	}


var categoryid = $('#categoryidtext').val();
var category = $('#categorytext').val();
console.log(categoryid);
if(oprn == 'INS'){
	categoryid="NEW"
}
var categoryObj = new Object();
categoryObj.categoryname=category;
$.ajax(
{
type:'POST',
contentType:'application/json',
url:'savecategory/'+categoryid+"/"+category+"/"+$('#inventoryuseridtext').val()+"/"+oprn,
data:JSON.stringify(categoryObj),
success:function(data,textStatus,jqXHR)
{
	var responseType = getResponseType(data);
	var responseValue = getResponseValue(data);
	//console.log(data.inventoryResponse.responseObj.id);
	//console.log("data");
	if(responseType == 'S')
		{
		categoryObj.categoryid = data.inventoryResponse.responseObj.id;
		console.log(categoryObj.categoryid);
		//console.log(data);
		prepareCategoryDataTable(categoryObj,oprn);
		clearCategoryFormValues();
		}
	else if(responseType == 'F')
		{
		
		displayFailureMsg(oprn,responseValue);
		
		}
	}
}		
);
}

function savebank()
{
var oprn = $('#oprntext').val();

if(oprn != 'DEL')
	{
	validateonbuttonclick('#banktext','input');	
	}


var bankid = $('#bankidtext').val();
var bank = $('#banktext').val();
//console.log(bankid);
if(oprn == 'INS'){
	bankid="NEW"
}
var bankObj = new Object();
bankObj.bankname=bank;
console.log('savebank/'+bankid+"/"+bank+"/"+$('#inventoryuseridtext').val()+"/"+oprn);
$.ajax(
{
type:'POST',
contentType:'application/json',
url:'savebank/'+bankid+"/"+bank+"/"+$('#inventoryuseridtext').val()+"/"+oprn,
data:JSON.stringify(bankObj),
success:function(data,textStatus,jqXHR)
{
	var responseType = getResponseType(data);
	var responseValue = getResponseValue(data);
	
	if(responseType == 'S')
	{
		//bankObj.bankid = generatedidValue(responseValue);
		bankObj.bankid = data.inventoryResponse.responseObj.id;
		prepareBankDataTable(bankObj,oprn);
		clearBankFormValues();
	}
	else if(responseType == 'F')
	{
	
	displayFailureMsg(oprn,responseValue);
	
	}
}
});
}



function prepareCategoryDataTable(categoryObject, oprn) {
	var table = $('#categoryDT').DataTable();
	
	if (oprn == 'INS') {
		var rowNode = table.row.add(
				[ categoryObject.categoryname, '',categoryObject.categoryid ]).draw().node();
	}
	else if(oprn == 'UPD')
		{
		/*table.row(1).cell(':eq(0)').data(categoryObject.categoryname).draw();*/
		location.reload(false);
		/*table.row( 0 ).data( [ categoryObject.categoryname, '<button class="edit myBtnTab" > <i class="fa fa-edit"></i> </button><button class="delete myBtnTab" data-toggle="modal" data-target="#modal-delet" > <i class="fa fa-remove"></i> </button>  ' ] ).draw();*/
		}
}

function prepareBankDataTable(bankObject, oprn) {
	var table = $('#bankDT').DataTable();
	
	if (oprn == 'INS') {
		var rowNode = table.row.add(
				[ bankObject.bankname, '',bankObject.bankid ]).draw().node();
	}
	else if(oprn == 'UPD')
		{
		/*table.row(1).cell(':eq(0)').data(categoryObject.categoryname).draw();*/
		location.reload(false);
		/*table.row( 0 ).data( [ categoryObject.categoryname, '<button class="edit myBtnTab" > <i class="fa fa-edit"></i> </button><button class="delete myBtnTab" data-toggle="modal" data-target="#modal-delet" > <i class="fa fa-remove"></i> </button>  ' ] ).draw();*/
		}
}

function clearCategoryFormValues()
{
	$('#oprntext').val("INS");
	$('#categoryidtext').val("");
	$('#categorytext').val("");	
	location.reload(true);
}

function clearBankFormValues()
{
	$('#oprntext').val("INS");
	$('#bankidtext').val("");
	$('#banktext').val("");	
	location.reload(true);
}

/*Category Master Script End*/

/*Manufacturer Master Script Start*/

function loadcompany()
{
	validateoninputtyping('#companynametext','input');
$.ajax(
		{
			contentType:'application/json',
			success:function(data,textStatus,jqXHR)
			{
				var responseType = getResponseType(data);
				var responseValue = getResponseValue(data);

				if(responseType == 'S')
					{
					var dataTableSet = $.parseJSON(responseValue);
					displayCompanyDatalist(dataTableSet);
					}
			},
			type:'GET',
			url : "companylist"
			}
		);	
}

function displayCompanyDatalist(dataTableSet) {
	$('#companyDT').DataTable({
		destroy : true,
		data : dataTableSet,
		"fnCreatedRow": function( nRow, aData, iDataIndex ) {
			/*$('td:eq(1)', nRow).append("<label style='cursor:pointer;'>Edit</label><img src='../resources/images/delete.jpg' width='30px;' height='30px' id='editbtn'></img>");*/
			$('td:eq(1)', nRow).append('<button class="edit myBtnTab" > <i class="fa fa-edit"></i> </button><button class="delete myBtnTab" data-toggle="modal" data-target="#modal-delet" > <i class="fa fa-remove"></i> </button>  ');
		}
	});
	selectCompanyItem();
}

function selectCompanyItem() {
	$('#companyDT tbody').on('click', '.delete.myBtnTab', function() {
		var table = $('#companyDT').DataTable();
		var companyObject = [];
		companyObject.push(table.row( $(this).parents('tr') ).data());
		/*categoryObject.push(table.row(this).data());*/
		console.log(companyObject)
		var object = companyObject[0];
		$.each(object, function(index, value) {
			setValuestoCompanyFields(object)
			
		});
		 
			var table = $('#companyDT').DataTable();
			confirmCompanyDelete(table.row($(this).parents('tr')));
		 	
		
		
		
	});
	$('#companyDT tbody').on('click', '.edit.myBtnTab', function() {
		var table = $('#companyDT').DataTable();
		var companyObject = [];
		companyObject.push(table.row( $(this).parents('tr') ).data());
		/*categoryObject.push(table.row(this).data());*/
		var object = companyObject[0];
		$.each(object, function(index, value) {
			setValuestoCompanyFields(object);
			$('#oprntext').val("UPD");
			
		});
		$('#myModal').modal('show');
	});
}

function confirmCompanyDelete(tableRow)
{
/*$('.btn.btn-white').on('click',function()
{*/
	$('#oprntext').val("DEL");
	savecompany();
	tableRow.remove().draw();
	clearCompanyFields();
	/*});*/
}

function savecompany()
{
	var saveClick = true;
	var oprn = $('#oprntext').val();
	if(oprn != 'DEL')
		{
		saveClick = validateonbuttonclick('#companynametext','input');	
		}
	
	if(saveClick)
		{
		
	var companyObj = new Object();
	companyObj.companyid = $('#companyidtext').val();
	companyObj.companyname = $('#companynametext').val();
	companyObj.oprn = $('#oprntext').val();
	
$.ajax(
		{
			contentType:'application/json',
			success:function(data,textStatus,jqXHR)
			{
				var responseType = getResponseType(data);
				var responseValue = getResponseValue(data);
				
				if(responseType == 'S')
					{
					companyObj.companyid = data.inventoryResponse.responseObj.id;
					prepareCompanyDataTable(companyObj,companyObj.oprn);
					clearCompanyFields();	
					}
				else if(responseType == 'F')
					{
					displayFailureMsg(oprn,responseValue);
					}
				
			},
			type:'POST',
			url:"savecompany",
			data:JSON.stringify(companyObj)
			}
		);

		}
}

function setValuestoCompanyFields(object)
{
	
	$('#companynametext').val(object[0]);
	$('#companyidtext').val(object[2]);
		
}

function clearCompanyFields()
{
	$('#companynametext').val("");
	$('#companyidtext').val("");
	$('#oprntext').val("INS");
	location.reload(true);
}

function prepareCompanyDataTable(companyObject, oprn) {
	var table = $('#companyDT').DataTable();
	
	if (oprn == 'INS') {
		var rowNode = table.row.add(
				[ companyObject.companyname, '',companyObject.companyid ]).draw().node();
	}
	else if(oprn == 'UPD')
		{
		location.reload(false);
		}
}

/*Manufacturer Master Script End*/

/*UOM Master Script Start*/

function loaduom()
{
	validateoninputtyping('#uomidtext','input');
	$.ajax({
		type:'GET',
		contentType:'application/json',
		url:'uomlist',
		success:function(data,textStatus,jqXHR)
		{
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);

			if(responseType == 'S')
				{
				var dataTableSet = $.parseJSON(responseValue);
				displayUOMDatalist(dataTableSet);
				}

		}
	});

}

function displayUOMDatalist(dataTableSet) {
	
	$('#uomDT').DataTable({
		destroy : true,
		data : dataTableSet,
		"fnCreatedRow": function( nRow, aData, iDataIndex ) {
			/*$('td:eq(1)', nRow).append("<label style='cursor:pointer;'>Edit</label><img src='../resources/images/delete.jpg' width='30px;' height='30px' id='editbtn'></img>");*/
			$('td:eq(1)', nRow).append('<button class="edit myBtnTab" > <i class="fa fa-edit"></i> </button><button class="delete myBtnTab" data-toggle="modal" data-target="#modal-delet" > <i class="fa fa-remove"></i> </button>  ');
		}
	});
	selectUOMItem();
}

function selectUOMItem() {

	
	$('#uomDT tbody').on('click', '.delete.myBtnTab', function() {
		var table = $('#uomDT').DataTable();
		var uomDTObject = [];
		uomDTObject.push(table.row( $(this).parents('tr') ).data());
		var object = uomDTObject[0];
		$.each(object, function(index, value) {
			$('#uomidtext').val(object[2]);
			$('#uomdesctext').val(object[0]);
			
			$('#oprntext').val("UPD");
			
		});
		var table = $('#uomDT').DataTable();
		confirmUOMDelete(table.row($(this).parents('tr')))	
	});
	$('#uomDT tbody').on('click', '.edit.myBtnTab', function() {
		
		var table = $('#uomDT').DataTable();
		var uomObject = [];
		uomObject.push(table.row( $(this).parents('tr') ).data());
		var object = uomObject[0];
		$.each(object, function(index, value) {
			$('#uomidtext').val(object[2]);
			$('#uomdesctext').val(object[0]);
			$('#oprntext').val("UPD");
			
		});
		$("#addbutton").html("Save");
		$('#myModal').modal('show');
	});
}



function confirmUOMDelete(tableRow)
{
/*$('.btn.btn-white').on('click',function()
{*/
	//alert("js");
	console.log(tableRow);
	
	$('#oprntext').val("DEL");
	saveuom();
	tableRow.remove().draw();
	clearUOMFields();
	location.reload(true);
//	/*});*/
}





function saveuom()
{
	
var isValid = validateonbuttonclick('#uomdesctext','input');

if(isValid)
	{
	
	var unittypeid = $('#uomidtext').val();
	var unit = $('#uomdesctext').val();
	var oprn = $('#oprntext').val();

	var uomObj = new Object();
	uomObj.unittypeid = unittypeid;
	uomObj.unit = unit;
	uomObj.oprn = oprn;
	uomObj.statusid = '1';

	$.ajax(
	{
	type:'POST',
	contentType:'application/json',
	url:'saveuom',
	data:JSON.stringify(uomObj),
	success:function(data,textStatus,jqXHR)
	{
		var responseType = getResponseType(data);
		var responseValue = getResponseValue(data);
		uomObj.unittypeid = data.inventoryResponse.responseObj.id;
		if(responseType == 'S')
			{
			 
			prepareUOMDataTable(uomObj,oprn);
			clearUOMFields();
			}
		else if(responseType == 'F')
			{
			displayFailureMsg(oprn,responseValue);
			}
		}
	}		
	);	
	}

}


function setValuestoUOMFields(object)
{
	
	$('#uomdesctext').val(object[0]);
	$('#uomidtext').val(object[2]);
		
}

function clearUOMFields()
{
	$('#uomdesctext').val("");
	$('#uomidtext').val("");
	$('#oprntext').val("INS");
	location.reload(true);
}



function prepareUOMDataTable(uomObject, oprn) {
	var table = $('#uomDT').DataTable();
	
	if (oprn == 'INS') {
		var rowNode = table.row.add(
				[ uomObject.unit, '',uomObject.unittypeid ]).draw().node();
	}
	else if(oprn == 'UPD')
		{
		location.reload(false);
		}
}


/*UOM Master Script End*/

/*Currency Master Script Start*/

function loadcurrency()
{
	
$.ajax({
	type:'GET',
	contentType:'application/json',
	url:'currencylist',
	success:function(data,textStatus,jqXHR)
	{
		var responseType = getResponseType(data);
		var responseValue = getResponseValue(data);

		if(responseType == 'S')
			{
			var dataTableSet = $.parseJSON(responseValue);
			displayCurrencyDatalist(dataTableSet);
			}

	}
});
}

function displayCurrencyDatalist(dataTableSet) {
	$('#currencyDT').DataTable({
		destroy : true,
		data : dataTableSet,
		"fnCreatedRow": function( nRow, aData, iDataIndex ) {
			/*$('td:eq(1)', nRow).append("<label style='cursor:pointer;'>Edit</label><img src='../resources/images/delete.jpg' width='30px;' height='30px' id='editbtn'></img>");*/
			$('td:eq(5)', nRow).append('<button class="edit myBtnTab" data-toggle="modal" data-target="#currencies"> <i class="fa fa-edit"></i> </button><button class="delete myBtnTab" data-toggle="modal" data-target="#modal-delet" > <i class="fa fa-remove"></i> </button>  ');
		}
	});
	selectCurrencyItem();
}
 
function selectCurrencyItem() {
	$('#currencyDT tbody').on('click', '.delete.myBtnTab', function() {
		var table = $('#currencyDT').DataTable();
		var categoryObject = [];
		categoryObject.push(table.row( $(this).parents('tr') ).data());
		/*categoryObject.push(table.row(this).data());*/
		
		var object = categoryObject[0];
		$.each(object, function(index, value) {
			
			$('#currencynametext').val(object[0]);
			$('#currencysysmboltext').val(object[1]);
			$('#exchngratetext').val(object[2]);
			$('#convratetext').val(object[3]);
			$('#currencydecimal').val(object[4]);
			 
			$('#currencyidtext').val(object[6]);
			
			$('#currencycodeselect').val(object[7]);
			
			
			
			$('#oprntext').val("UPD");
			
		});
		var table = $('#currencyDT').DataTable();
		confirmCurrencyDelete(table.row($(this).parents('tr')));
		
	});
	$('#currencyDT tbody').on('click', '.edit.myBtnTab', function() {
		var table = $('#currencyDT').DataTable();
		var categoryObject = [];
		categoryObject.push(table.row( $(this).parents('tr') ).data());
		/*categoryObject.push(table.row(this).data());*/
		console.log(categoryObject)
		var object = categoryObject[0];
		$.each(object, function(index, value) {
			$('#currencynametext').val(object[0]);
			$('#currencysysmboltext').val(object[1]);
			$('#exchngratetext').val(object[2]);
			$('#convratetext').val(object[3]);
			/*$('#currdecimaltext').val(object[4]);*/
			$('#currencydecimal').val(object[4]) 
			$('#currencyidtext').val(object[6]);
			
			$('#currencycodeselect').val(object[7]);
			
			
			$('#oprntext').val("UPD");
			
			
		});
		$('#myModal').modal('show');
	});
	
}

function confirmCurrencyDelete(tableRow)
/*{
	$('.btn.btn-white').on('click',function()*/
			{
	$('#oprntext').val("DEL");
	savecurrency();
	
	tableRow.remove().draw();
	clearCurrencyFields();
		/*	});*/
}

function savecurrency()
{
var isValid = true;
var oprn = $('#oprntext').val();
if(oprn != 'DEL')
	{
	isValid = validateonbuttonclick('#currencynametext','input');
	/*isValid = validateonbuttonclick('#currencycodeselect','select');*/
	isValid = validateonbuttonclick('#currencycodeselect','input');
	isValid = validateonbuttonclick('#exchngratetext','input');
	isValid = validateonbuttonclick('#convratetext','input');
	isValid = validateonbuttonclick('#currencydecimal','input');
	isValid = validateonbuttonclick('#basecurrchk','input');
	}

if(isValid)
	{
	
	var clientValid = false;
	
	var currencyid = $('#currencyidtext').val();
	var currency = $('#currencynametext').val();
	var statusid = '1';
	var currencysymbol = $('#currencysysmboltext').val();
	var exchangerate = $('#exchngratetext').val();
	var conversionrate = $('#convratetext').val();
	var currencycode = $('#currencycodeselect').val();
	var currdecimal = $('#currencydecimal').val();
	var currencyObj = new Object();
	var basecurr = 0;
	
	if($('#basecurrchk').prop('checked') == true)
		{
		basecurr = 1;
		currencyObj.basecurrdisp = "Yes";
		}
	else
		{
		currencyObj.basecurrdisp = "No";
		}

	if((parseFloat(exchangerate) > 100) || parseFloat(conversionrate) > 100)
		{
		alert("Rate should not be greater than 100");
		return false;
		}
	else
		{
		clientValid = true;
		}
	if(parseInt(basecurr) == 1)
		{
		if((parseFloat(exchangerate) != 1) || parseFloat(conversionrate) != 1)
		{
		alert("Exchange Rate and Conversion Rate value must be 1");
		return false;
		}
		else
			{
			clientValid = true;
			}
		}
	
	
	
	if(clientValid)
		{
		
	currencyObj.currencyid = currencyid;
	currencyObj.currency = currency;
	currencyObj.statusid = statusid;
	currencyObj.currencysymbol = currencysymbol;
	currencyObj.exchangerate = exchangerate;
	currencyObj.conversionrate = conversionrate;
	currencyObj.currencycode = currencycode;
	currencyObj.currdecimal = currdecimal;
	currencyObj.basecurr = basecurr;
	currencyObj.oprn = oprn;
	
	$.ajax(
	{
	type:'POST',
	contentType:'application/json',
	url:'savecurrency',
	data:JSON.stringify(currencyObj),
	success:function(data,textStatus,jqXHR)
	{
		var responseType = getResponseType(data);
		var responseValue = getResponseValue(data);
		
		if(responseType == 'S')
			{
			currencyObj.currencyid = generatedidValue(responseValue);	 
			prepareCurrencyDataTable(currencyObj,oprn);
			clearCurrencyFields();
			}
		else if(responseType == 'F')
			{
			alert(responseMsg(responseValue));
			clearCurrencyFields();
			}
		}
	});
	
	}

	}
else
	{
	return false;
	}
}

function clearCurrencyFields()
{
	$('#uomdesctext').val("");
	$('#uomidtext').val("");
	$('#oprntext').val("INS");
	$('#currencyidtext').val("");
	$('#currencyidtext').val("");
	$('#currencynametext').val("");
	
	$('#currencysysmboltext').val("");
	$('#exchngratetext').val("");
	$('#convratetext').val("");
	/*$('#currencycodeselect').val($("#currencycodeselect option:first").val());*/
	$('#currencycodeselect').val('');
	$('#currdecimaltext').val("0");
	$('#currencydecimal').val("0");
	
	
	$('#oprntext').val("INS");
}

function prepareCurrencyDataTable(currencyObj, oprn) {
	var table = $('#currencyDT').DataTable();
	
	if (oprn == 'INS') {
		/*var rowNode = table.row.add(
				[ currencyObj.currency, currencyObj.currencysymbol,currencyObj.exchangerate,currencyObj.conversionrate,'',currencyObj.currencyid
				  ,currencyObj.currdecimal,currencyObj.basecurrdisp]).draw().node();*/
		location.reload(false);
	}
	else if(oprn == 'UPD')
		{
		location.reload(false);
		}
}
/*Currency Master End*/

/*Tax Master Start*/

function loadtax()
{
	$.ajax({
		type:'GET',
		contentType:'application/json',
		url:'taxlist',
		success:function(data,textStatus,jqXHR)
		{
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			
			if(responseType == 'S')
				{
				var dataTableSet = $.parseJSON(responseValue);
				displayTaxDatalist(dataTableSet);
				}

		}
	});
}

function displayTaxDatalist(dataTableSet) {
	$('#taxDT').DataTable({
		destroy : true,
		data : dataTableSet,
		"fnCreatedRow": function( nRow, aData, iDataIndex ) {
			/*$('td:eq(1)', nRow).append("<label style='cursor:pointer;'>Edit</label><img src='../resources/images/delete.jpg' width='30px;' height='30px' id='editbtn'></img>");*/
			$('td:eq(4)', nRow).append('<button class="edit myBtnTab"  data-toggle="modal" data-target="#tax"> <i class="fa fa-edit"></i> </button><button class="delete myBtnTab" data-toggle="modal" data-target="#modal-delet" > <i class="fa fa-remove"></i> </button>  ');
		}
	});
	selectTaxItem();
}

function selectTaxItem() {
	
	$('#taxDT tbody').on('click', '.delete.myBtnTab', function() {
		var table = $('#taxDT').DataTable();
		var taxObject = [];
		taxObject.push(table.row( $(this).parents('tr') ).data());
		/*categoryObject.push(table.row(this).data());*/
		console.log(taxObject)
		var object = taxObject[0];
		$.each(object, function(index, value) {
			$('#taxnametext').val(object[0]);
			$('#taxcodetext').val(object[1]);
			$('#taxratetext').val(object[2]);
			$('#taxtypeselect').val(object[6]);
			$('#taxidtext').val(object[5]);
			
			$('#oprntext').val("UPD");
			
		});
		var table = $('#taxDT').DataTable();
		confirmTaxDelete(table.row($(this).parents('tr')));
		
		
	});
	$('#taxDT tbody').on('click', '.edit.myBtnTab', function() {
		var table = $('#taxDT').DataTable();
		var categoryObject = [];
		categoryObject.push(table.row( $(this).parents('tr') ).data());
		/*categoryObject.push(table.row(this).data());*/
		console.log(categoryObject)
		var object = categoryObject[0];
		$.each(object, function(index, value) {
			$('#taxnametext').val(object[0]);
			$('#taxcodetext').val(object[1]);
			$('#taxratetext').val(object[2]);
			$('#taxtypeselect').val(object[6]);
			$('#taxidtext').val(object[5]);
			$('#oprntext').val("UPD");
			
		});
		$('#myModal').modal('show');
		
	});
}

function confirmTaxDelete(tableRow)
{

/*$('.btn.btn-white').on('click',function()
{*/

	$('#oprntext').val("DEL");
	savetax();
	tableRow.remove().draw();
	
	/*});*/

}


function savetax()
{
var isValid = true;
var oprn = $('#oprntext').val();
if(oprn != 'DEL')
	{
	/*isValid = validateonbuttonclick('#taxnametext','input');
	isValid = validateonbuttonclick('#taxcodetext','input');
	isValid = validateonbuttonclick('#taxratetext','input');*/
	}

if(isValid)
	{
	
	var taxid = $('#taxidtext').val();
	var taxname = $('#taxnametext').val();
	var taxtype = $('#taxtypeselect').val();
	var taxcode = $('#taxcodetext').val();
	var taxrate = $('#taxratetext').val();
	var statusid = '1';
	var oprn = $('#oprntext').val();

	
	var taxObj = new Object();
	taxObj.taxid = taxid;
	taxObj.taxname = taxname;
	taxObj.taxtype = taxtype;
	taxObj.taxcode = taxcode;
	taxObj.taxrate = taxrate;
	taxObj.statusid = statusid;
	taxObj.oprn = oprn;
	taxObj.taxtypedisp = "";
	
	if(taxObj.taxtype == 'P')
		{
		taxObj.taxtypedisp = "Percentage"
		}
	else if(taxObj.taxtype == 'F')
		{
		taxObj.taxtypedisp = "Fixed"
		}
	
	
	var clientValid = false;
	if(taxObj.taxtype == 'P')
		{
		if(parseFloat(taxObj.taxrate) > 100)
			{
			alert("Tax Rate must be less than 100")
			return false;
			}
		else
			{
			clientValid = true;
			}
		}
	else
		{
		clientValid = true;
		}
	
	if(clientValid)
		{
		$.ajax(
				{
				type:'POST',
				contentType:'application/json',
				url:'savetax',
				data:JSON.stringify(taxObj),
				success:function(data,textStatus,jqXHR)
				{
					var responseType = getResponseType(data);
					var responseValue = getResponseValue(data);
					
					if(responseType == 'S')
						{
						taxObj.taxid = generatedidValue(responseValue);	 
						prepareTaxDataTable(taxObj,oprn);
						clearTaxFields();
						}
					else if(responseType == 'F')
						{
						alert(responseMsg(responseValue))
						}
					}
				});	
		}
	
	}
else
	{
	return false;
	}
}

function changetaxrate(value)
{
	
	$('#taxratetext').attr('placeholder','Rs.')
}

function clearTaxFields()
{
	$('#taxidtext').val("0");
	$('#taxnametext').val("");
	$('#taxtypeselect').val($("#taxtypeselect option:first").val());
	$('#taxcodetext').val("");
	$('#taxratetext').val("");
	
	$('#oprntext').val("INS");
}

function prepareTaxDataTable(taxObj, oprn) {
	var table = $('#taxDT').DataTable();
	
	if (oprn == 'INS') {
		var rowNode = table.row.add(
				[ taxObj.taxname, taxObj.taxcode,taxObj.taxrate,taxObj.taxtypedisp,'',taxObj.taxid ]).draw().node();
	}
	else if(oprn == 'UPD')
		{
		location.reload(false);
		}
}

/*Tax Master End*/

/*Product Master Start*/
function importproduct()
{
	if($('#uploadedfilename').val() != '')
		{
		$('#loadingDiv').show();
		
		var uploadedfilename = $('#uploadedfilename').val();
		var uploadobj = new Object();
		uploadobj.filename = uploadedfilename;
		$.ajax({
			type:'POST',
			url:'importproducttostock',
			contentType:'application/json',
			data:JSON.stringify(uploadobj),
			success:function(data,textStatus,jqXHR)
			{
				$('#loadingDiv').hide();
				var responseType = getResponseType(data);
				var responseValue = getResponseValue(data);
				
				$('#uploadedfilename').val('');
				$('#uploadbtn').attr('disabled',false);
				alert("Process Completed Successfully");
				
				
				$('#statusDIVid').css({"display":"block"});
				
				$('#productUplaodStatusDT').DataTable( {
					"destroy":true,
			    	data: responseMsg(responseValue),
			        columns: [
			            { data: 'recindex' },
			            { data: 'uploadstatus' },
			            { data: 'errormsg' }
			        ]
		            
			    } );
				
			}
		});		
		}
	else
		{
		alert("Please Choose the file and Upload");
		return false;
		}
	//alert("js");
}

function checkuploadfile()
{
	
	
}

function loadproduct()
{
	
	 $.ajax({
			type:'GET',
			contentType:'application/json',
			url:'productlist',
			success:function(data,textStatus,jqXHR)
			{
				var responseType = getResponseType(data);
				var responseValue = getResponseValue(data);
				
				$('#productDT').DataTable( {
			    	data: responseValue,
			    	dom: 'Bfrtip',
			        buttons: [
			             'excel', 'pdf'
			        ],
			        columns: [
			            { data: 'productidhref' },
			            { data: 'productcode' },
			            { data: 'unitdisp' },
			            { data: 'manufacturerdesc' },
			            { data: 'categorydesc' },
			            { data: 'alertcount' },
			            { data: 'statusdisp' },
			            { data: 'action' }
			        ]
		            
			    } ); 
				selectProductItem();
			}
		});


}

function gotoproductview(productid)
{
alert(productid)	
}

function checknotifyqty()
{
	if($('#notifiycheck').prop('checked') == true){
        $("#notifyqtytext").attr("readonly", false);
        $("#notifyqtytext").val($('#notifyqtycopytext').val());
    }
    else{ 
        $("#notifyqtytext").attr("readonly", true);
        $("#notifyqtytext").val(0);
    }	
}

function saveproduct()
{
var isValid = true;
var oprn = $('#oprntext').val();
if(oprn != 'DEL')
{
	isValid = validateonbuttonclick('#productnametext','input');	
	isValid = validateonbuttonclick('#productcodetext','input');
}

if(isValid)
	{
	var productid = $('#productidtext').val();
	var productname = $('#productnametext').val();
	var productcode = $('#productcodetext').val();
	var categoryid = $('#categoryselect').val();
	
	var unittypeid = $('#uomselect').val();
	var unitquantity = $('#unitqtytext').val();
	var description = $('#descriptiontext').val();
	var alertcount = $('#notifyqtytext').val();
	var manufacturerid = $('#manufactureselect').val();
	var productbarcode = $("#barcode").val();
	
	var statusid = 0;
	if($('#myonoffswitch1').prop('checked'))
		{
		statusid = 1;
		}
	else
		{
		statusid = 0;
		}
	
	var productObj = new Object();
	productObj.productid=productid;
	productObj.productname=productname;
	productObj.productcode=productcode;
	productObj.productbarcode=productbarcode;
	productObj.categoryid=categoryid;
	productObj.unittypeid=unittypeid;

	productObj.unitquantity=unitquantity;
	productObj.description=description;
	productObj.alertcount=alertcount;
	productObj.manufacturerid=manufacturerid;
	productObj.statusid=statusid;
	productObj.oprn=oprn;
	productObj.userid = $('#inventoryuseridtext').val();
	
	$.ajax(
	{
	type:'POST',
	contentType:'application/json',
	url:'saveproduct',
	data:JSON.stringify(productObj),
	success:function(data,textStatus,jqXHR)
	{
		var responseType = getResponseType(data);
		var responseValue = getResponseValue(data);
		
		if(responseType == 'S')
			{
			productObj.productid = generatedidValue(responseValue);
			
			window.location.href="product"
			}
		else if(responseType == 'F')
			{
			alert(responseMsg(responseValue));
			}
		}
	}		
	);
	}

}

function selectProductItem() {
	$('#productDT tbody').on('click', '.delete.myBtnTab', function() {
		var table = $('#productDT').DataTable();
		var productObject = [];
		productObject.push(table.row( $(this).parents('tr') ).data());
		/*categoryObject.push(table.row(this).data());*/
		console.log(productObject)
		var object = productObject[0];
		
		var table = $('#productDT').DataTable();
		confirmProductDelete(table.row($(this).parents('tr')),object);
		
	});
	$('#productDT tbody').on('click', '.edit.myBtnTab', function() {
		var table = $('#productDT').DataTable();
		var productObject = [];
		productObject.push(table.row( $(this).parents('tr') ).data());
		/*categoryObject.push(table.row(this).data());*/
		
		var object = productObject[0];
		window.location.href="addproduct?productid="+object['productid'];
		
	});
}

function loadaddproduct()
{
	var productid = getParameterByName('productid');
	$.ajax({
		type:'GET',
		contentType:'application/json',
		url:'productinfo/'+productid,
		success:function(data,textStatus,jqXHR)
		{
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			
			if(responseType == 'S')
				{
				if(responseValue.length > 0)
					{
					var object = responseValue[0];
					
					$('#productidtext').val(productid);
					$('#productnametext').val(object['productname']);
					$('#productcodetext').val(object['productcode']);
					$('#categoryselect').val(object['categoryid']);
					$('#uomselect').val(object['unittypeid']);
					
					$('#unitqtytext').val(object['unitquantity']);

					$('#descriptiontext').val(object['description']);
					
					if(parseInt(object['alertcount']) > 0)
						{
						$('#notifiycheck').prop('checked',true);
						checknotifyqty();
						}
					
					$('#notifyqtytext').val(object['alertcount']);
					$('#notifyqtycopytext').val(object['alertcount']);
					$('#manufactureselect').val(object['manufacturerid']);
					$('#oprntext').val("UPD")
					
					if(parseInt(object['statusid']) == 0)
						{
						$('#myonoffswitch1').attr('checked',false);
						}
					else
						{
						$('#myonoffswitch1').attr('checked',true);
						}
					}
				else
					{
					$('#oprntext').val("INS")
					}
				
				}

		}
	});
	
}

function confirmProductDelete(tableRow,productObject)
{
/*$('.btn.btn-white').on('click',function()
{
*/
	
	var isValid = true;
	/*var oprn = $('#oprntext').val();
	if(oprn != 'DEL')
	{
		isValid = validateonbuttonclick('#productnametext','input');	
		isValid = validateonbuttonclick('#productcodetext','input');
	}*/

	if(isValid)
		{
		console.log("saras");
		productObject.oprn="DEL";
		$.ajax(
		{
		type:'POST',
		contentType:'application/json',
		url:'saveproduct',
		data:JSON.stringify(productObject),
		success:function(data,textStatus,jqXHR)
		{
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			
			if(responseType == 'S')
				{
				tableRow.remove().draw();
				window.location.reload();
				}
			else if(responseType == 'F')
				{
				alert(responseValue);
				}
			}
		}		
		);
		}
	
	/*});*/
}
 
/*Product Master End*/

/*Supplier Master Start*/

function loadsupplier()
{
	
	 $.ajax({
			type:'GET',
			contentType:'application/json',
			url:'supplierlist',
			success:function(data,textStatus,jqXHR)
			{
				var responseType = getResponseType(data);
				var responseValue = getResponseValue(data);
				
				$('#supplierDT').DataTable( {
			    	data: responseValue,
			        columns: [
			            { data: 'suppliernamehref' },
			            { data: 'suppliertypedesc' },
			            { data: 'telephoneno' },
			            { data: 'emailid' },
			            { data: 'city' },
			            { data: 'action' }
			        ]
		            
			    } ); 
				selectSupplierItem();
			}
		});


}

function selectSupplierItem() {
	$('#supplierDT tbody').on('click', '.delete.myBtnTab', function() {
		var table = $('#supplierDT').DataTable();
		var supplierObject = [];
		supplierObject.push(table.row( $(this).parents('tr') ).data());
		/*categoryObject.push(table.row(this).data());*/
		console.log(supplierObject)
		var object = supplierObject[0];
		
		var table = $('#supplierDT').DataTable();
		confirmSupplierDelete(table.row($(this).parents('tr')),object);
		
	});
	$('#supplierDT tbody').on('click', '.edit.myBtnTab', function() {
		var table = $('#supplierDT').DataTable();
		var supplierObject = [];
		supplierObject.push(table.row( $(this).parents('tr') ).data());
		/*categoryObject.push(table.row(this).data());*/
		
		var object = supplierObject[0];
		window.location.href="addsupplier?supplierid="+object['supplierid'];
		
	});
}

function savesupplier()
{
var isValid = true;
var oprn = $('#oprntext').val();
if(oprn != 'DEL')
{
	isValid = validateonbuttonclick('#supplierfirstnametext','input');	
	isValid = validateonbuttonclick('#supplierlastnametext','input');
	isValid = validateonbuttonclick('#suppliermobnotext','input');
}

if(isValid)
	{
	var  supplierid = $('#supplieridtext').val();
	var  supplierfirstname = $('#supplierfirstnametext').val();
	var  supplierlastname = $('#supplierlastnametext').val();
	var  suppliertypeid = $('#suppliertypeselect').val();
	var  supplierinis = $('#supplierinisselect').val();
	var  address = $('#supplieraddresstext').val();
	var  city = $('#suppliercitytext').val();
	var  state = $('#supplierstatetext').val();
	var  zipcode = $('#supplierzipcodetext').val();
	var  countryid = $('#suppliercountrytext').val();
	var  mobile = $('#suppliermobnotext').val();
	var  telephoneno = $('#suppliertelnotext').val();
	var  emailid = $('#supplieremailtext').val();
	var  statusid = '0';
	var  tinnumber = $('#suppliertinnotext').val();
	var  oprn = $('#oprntext').val();
	
	var supplierObj = new Object();
	supplierObj.supplierid = supplierid;
	supplierObj.supplierfirstname = supplierfirstname;
	supplierObj.supplierlastname = supplierlastname;
	supplierObj.suppliertypeid = suppliertypeid;
	supplierObj.supplierinis = supplierinis;
	supplierObj.address = address;
	supplierObj.city = city;
	supplierObj.state = state;
	supplierObj.zipcode = zipcode;
	supplierObj.countryid = countryid;
	supplierObj.mobile = mobile;
	supplierObj.telephoneno = telephoneno;
	supplierObj.emailid = emailid;
	supplierObj.statusid = statusid;
	supplierObj.tinnumber = tinnumber;
	supplierObj.userid = $('#inventoryuseridtext').val();
	supplierObj.oprn = oprn;
	
	$.ajax(
	{
	type:'POST',
	contentType:'application/json',
	url:'savesupplier',
	data:JSON.stringify(supplierObj),
	success:function(data,textStatus,jqXHR)
	{
		var responseType = getResponseType(data);
		var responseValue = getResponseValue(data);
		
		if(responseType == 'S')
			{
			
			window.location.href="supplier"
			}
		else if(responseType == 'F')
			{
			alert(responseValue);
			}
		}
	}		
	);
	}

}

function loadaddsupplier()
{
	validateoninputtyping('#supplierfirstnametext','input');
	validateoninputtyping('#supplierlastnametext','input');
	validateoninputtyping('#suppliermobnotext','input');
	
	var supplierid = getParameterByName('supplierid');
	$.ajax({
		type:'GET',
		contentType:'application/json',
		url:'supplierinfo/'+supplierid,
		success:function(data,textStatus,jqXHR)
		{
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			
			if(responseType == 'S')
				{
				var object = responseValue[0];
					
					$('#supplieridtext').val(object['supplierid']);
					$('#supplierfirstnametext').val(object['supplierfirstname']);
					$('#supplierlastnametext').val(object['supplierlastname']);
					$('#suppliertypeselect').val(object['suppliertypeid']);
					$('#supplierinisselect').val(object['supplierinis']);
					$('#supplieraddresstext').val(object['address']);
					$('#suppliercitytext').val(object['city']);
					$('#supplierstatetext').val(object['state']);
					$('#supplierzipcodetext').val(object['zipcode']);
					$('#suppliercountrytext').val(object['countryid']);
					$('#suppliermobnotext').val(object['mobile']);
					$('#suppliertelnotext').val(object['telephoneno']);
					$('#supplieremailtext').val(object['emailid']);
					$('#suppliertinnotext').val(object['tinnumber']);
					$('#oprntext').val("UPD");
					
				}

		}
	});
}

function confirmSupplierDelete(tableRow,productObject)
{
/*$('.btn.btn-white').on('click',function()
{*/

	var isValid = true;
	var oprn = $('#oprntext').val();
	

		
		productObject.oprn="DEL"
		$.ajax(
		{
		type:'POST',
		contentType:'application/json',
		url:'savesupplier',
		data:JSON.stringify(productObject),
		success:function(data,textStatus,jqXHR)
		{
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			
			if(responseType == 'S')
				{
				tableRow.remove().draw();
				}
			else if(responseType == 'F')
				{
				alert(responseValue);
				}
			}
		}		
		);
		
	
	/*});*/
}
/*Supplier Master End*/

/*store Master Start*/

function loadstore()
{
	
	 $.ajax({
			type:'GET',
			contentType:'application/json',
			url:'storelist',
			success:function(data,textStatus,jqXHR)
			{
				var responseType = getResponseType(data);
				var responseValue = getResponseValue(data);
				
				$('#storeDT').DataTable( {
			    	data: responseValue,
			        columns: [
			            { data: 'storename' },
			            { data: 'currencydesc' },
			            { data: 'country' },
			            { data: 'phone' },
			            { data: 'email' },
			            { data: 'statusdesc' },
			            { data: 'action' }
			        ]
		            
			    } ); 
				selectStoreItem();
			}
		});


}

function savestore()
{
	var taxArray = [];
	var isValid = true;
	var oprn = $('#oprntext').val();
	if(oprn != 'DEL')
	{
		/*isValid = validateonbuttonclick('#supplierfirstnametext','input');	
		isValid = validateonbuttonclick('#supplierlastnametext','input');
		isValid = validateonbuttonclick('#suppliermobnotext','input');*/
	}

	if(isValid)
		{
		var storeid = $('#storeidtext').val();
		var storename = $('#storenametext').val();
		var currencytype = $('#currencytypeselect').val();
		var address = $('#addresstext').val();
		var city = $('#citytext').val();
		var state = $('#statetext').val();
		var country = $('#countrytext').val();
		var zipcode = $('#zipcodetext').val();
		var phone = $('#phonetext').val();
		var email = $('#emailtext').val();
		var statusid = "1";
		var oprn = $('#oprntext').val();
		
		var storeObj = new Object();
		storeObj.storeid = storeid;
		storeObj.storename = storename;
		storeObj.currencytype = currencytype;
		storeObj.address = address;
		storeObj.city = city;
		storeObj.state = state;
		storeObj.country = country;
		storeObj.zipcode = zipcode;
		storeObj.zipcode = zipcode;
		storeObj.phone = phone;
		storeObj.email = email;
		storeObj.statusid = statusid;
		storeObj.userid = $('#inventoryuseridtext').val();
		storeObj.oprn = oprn;
		
		/*TaxidIndex concept added by Ram on 10/02/2018*/
		var taxidIndex = 0;
		for(var index=0;index<$("#taxsizetext").val();index++)
			{
			var idname = "taxcheck"+index;
			if($('#'+idname).is(':checked'))
				{
				
				var taxObj = new Object();
				taxObj.taxid = $('#'+idname).val();
				taxObj.storeid = $('#storeidtext').val();
				taxObj.oprn = $('#oprntext').val();
				taxObj.index = taxidIndex;
				taxArray.push(taxObj);
				
				taxidIndex = parseInt(taxidIndex) + 1;
				}
			/*End*/
			}
		
		storeObj.taxList = JSON.stringify(taxArray);
		console.log(storeObj.taxList);
			/*name='streetaddress"+indexPosition+"'*/
		
		
		$.ajax(
		{
		type:'POST',
		contentType:'application/json',
		url:'savestore',
		data:JSON.stringify(storeObj),
		success:function(data,textStatus,jqXHR)
		{
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			
			if(responseType == 'S')
				{
				
				window.location.href="store"
				}
			else if(responseType == 'F')
				{
				alert(responseValue);
				}
			}
		}		
		);
		}
}

function selectStoreItem() {
	$('#storeDT tbody').on('click', '.delete.myBtnTab', function() {
		var table = $('#storeDT').DataTable();
		var storeObject = [];
		storeObject.push(table.row( $(this).parents('tr') ).data());
		/*categoryObject.push(table.row(this).data());*/
		console.log(storeObject)
		var object = storeObject[0];
		
		var table = $('#storeDT').DataTable();
		confirmStoreDelete(table.row($(this).parents('tr')),object);
		
	});
	$('#storeDT tbody').on('click', '.edit.myBtnTab', function() {
		var table = $('#storeDT').DataTable();
		var storeObject = [];
		storeObject.push(table.row( $(this).parents('tr') ).data());
		/*categoryObject.push(table.row(this).data());*/
		
		var object = storeObject[0];
		console.log(table.row( $(this).parents('tr') ).data());
		window.location.href="addstore?storeid="+object['storeid'];
		$('#myModal').modal('show');
	});
}

function loadaddstore()
{
	/*validateoninputtyping('#supplierfirstnametext','input');
	validateoninputtyping('#supplierlastnametext','input');
	validateoninputtyping('#suppliermobnotext','input');*/
	
	var storeid = getParameterByName('storeid');
	$.ajax({
		type:'GET',
		contentType:'application/json',
		url:'storeinfo/'+storeid,
		success:function(data,textStatus,jqXHR)
		{
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			
			if(responseType == 'S')
				{
				var storeArrayobject = responseValue['storeList'];
				var object = storeArrayobject[0];
				var taxObject = responseValue['storeTaxList'];
					
					$('#storeidtext').val(object['storeid']);
					$('#storenametext').val(object['storename']);
					$('#currencytypeselect').val(object['currencytype']);
					$('#addresstext').val(object['address']);
					$('#citytext').val(object['city']);
					$('#statetext').val(object['state']);
					$('#countrytext').val(object['country']);
					$('#zipcodetext').val(object['zipcode']);
					$('#phonetext').val(object['phone']);
					$('#emailtext').val(object['email']);
					$('#statusidtext').val(object['statusid']);
					$('#oprntext').val("UPD");
					
					for(var taxLoop=0;taxLoop < taxObject.length;taxLoop++)
						{
						for(var index=0;index<$('#taxsizetext').val();index++)
							{
							if(parseInt($('#taxcheck'+index).val()) == parseInt(taxObject[taxLoop]))
								{
								$('#taxcheck'+index).attr('checked',true);
								}
							}
						
						}
				}

		}
	});
}

function confirmStoreDelete(tableRow,storeObject)
{
/*$('.btn.btn-white').on('click',function()
{*/

	var isValid = true;
	var oprn = $('#oprntext').val();
	

		
		storeObject.oprn="DEL"
		$.ajax(
		{
		type:'POST',
		contentType:'application/json',
		url:'savestore',
		data:JSON.stringify(storeObject),
		success:function(data,textStatus,jqXHR)
		{
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			
			if(responseType == 'S')
				{
				tableRow.remove().draw();
				window.location.reload();
				}
			else if(responseType == 'F')
				{
				alert(responseValue);
				}
			}
		}		
		);
		
	
	/*});*/
}
/*store Master End*/

/*Customer  Group Start*/

function loadcustomergroup()
{
	$.ajax({
		type:'GET',
		contentType:'application/json',
		url:'loadcustomergroup',
		success:function(data,textStatus,jqXHR)
		{
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			
			if(responseType == 'S')
				{
				$('#customergroupDT').DataTable( {
					"destroy":true,
			    	data: responseValue,
			        columns: [
			            { data: 'customergroupname' },
			            { data: 'action' }
			        ]
		            
			    } );
				
				selectCustomergroup();	
				}
			
		}
	});
}

function savecustomergroup()
{
	var oprn = $('#oprntext').val();
	if(oprn != 'DEL')
		{
		validateonbuttonclick('#customergrouptext','input');	
		}


	var customergroupid = $('#customergroupidtext').val();
	var customergroupname = $('#customergrouptext').val();

	
	var customergroupObj = new Object();
	customergroupObj.customergroupid=customergroupid;
	customergroupObj.customergroupname=customergroupname;
	customergroupObj.userid=$('#inventoryuseridtext').val();
	customergroupObj.oprn=oprn;
	customergroupObj.action="";
	
	$.ajax(
	{
	type:'POST',
	contentType:'application/json',
	url:'savecustomergroup',
	data:JSON.stringify(customergroupObj),
	success:function(data,textStatus,jqXHR)
	{
		var responseType = getResponseType(data);
		var responseValue = getResponseValue(data);
		
		if(responseType == 'S')
			{
			alert(responseMsg(responseValue))
			clearcustomergroup();
			prepareCustomergroupDataTable(customergroupObj,oprn);
			
			if(oprn == 'UPD')
				{
				location.reload(false);
				}
			}
		else if(responseType == 'F')
			{
			alert(responseValue);
			}
		}
	}		
	);
}

function clearcustomergroup()
{
	$('#customergroupidtext').val(0);
	$('#customergrouptext').val("");
	$('#oprntext').val("INS");
	
}

function selectCustomergroup() {
	$('#customergroupDT tbody').on('click', '.delete.myBtnTab', function() {
		var table = $('#customergroupDT').DataTable();
		var customergroupObject = [];
		customergroupObject.push(table.row( $(this).parents('tr') ).data());
		/*categoryObject.push(table.row(this).data());*/
		 
		var object = customergroupObject[0];
		
		$('#customergroupidtext').val(object['customergroupid']);
		$('#customergrouptext').val(object['customergroupname']);
		$('#oprntext').val("UPD");
		
		var table = $('#customergroupDT').DataTable();
		confirmCustomerMasterDelete(table.row($(this).parents('tr')),object);
		
	});
	$('#customergroupDT tbody').on('click', '.edit.myBtnTab', function() {
		var table = $('#customergroupDT').DataTable();
		var customergroupObject = [];
		customergroupObject.push(table.row( $(this).parents('tr') ).data());
		/*categoryObject.push(table.row(this).data());*/
		console.log(customergroupObject);
		var object = customergroupObject[0];
		
		$('#customergroupidtext').val(object['customergroupid']);
		$('#customergrouptext').val(object['customergroupname']);
		$('#oprntext').val("UPD");
		$('#customerpopup').click();
		$('#myModal').modal('show');
	});
	
}

function confirmCustomerMasterDelete(tableRow)
{
	
/*$('.btn.btn-white').on('click',function()
{*/
	
	$('#oprntext').val("DEL");
	savecustomergroup();
	tableRow.remove().draw();
	console.log("delete");
	clearcustomergroup();
	location.reload(true);
	/*});*/
}

function prepareCustomergroupDataTable(customerGroupObj, oprn) {
	/*var table = $('#customergroupDT').DataTable();
	
	if (oprn == 'INS') {
		var rowNode = table.row.add(
				[ customerGroupObj.customergroupname, customerGroupObj.action ]).draw().node();
	}
	else if(oprn == 'UPD')
		{
		location.reload(false);
		}*/
	if(oprn != 'DEL')
	location.reload(false);
}
/*Customer Group End*/

/*Customer Master Start*/
var customeraddressArray = [];
function loadcustomer()
{
$.ajax({
	type:'GET',
	contentType:'application/json',
	url:'loadcustomers',
	success:function(data,textStatus,jqXHR)
	{
		var responseType = getResponseType(data);
		var responseValue = getResponseValue(data);
		
		if(responseType == 'S')
		{
			
			
		$('#customerDT').DataTable( {
			"destroy":true,
	    	data: responseValue,
	        columns: [
	            { data: 'customercodelink' },
	            { data: 'customermobileno' },
	            { data: 'customeremail' },
	            { data: 'customerstatus' },
	            { data: 'customergroup' },
	            { data: 'action' }
	        ]
            
	    } );
		
		selectCustomerItem();
		}
	}
});	
}

function selectCustomerItem() {
	$('#customerDT tbody').on('click', '.delete.myBtnTab', function() {
		var table = $('#customerDT').DataTable();
		var customerObject = [];
		customerObject.push(table.row( $(this).parents('tr') ).data());
		/*categoryObject.push(table.row(this).data());*/
		console.log(customerObject)
		var object = customerObject[0];
		
		var table = $('#customerDT').DataTable();
		confirmCustomerDataDelete(table.row($(this).parents('tr')),object);
		
	});
	$('#customerDT tbody').on('click', '.edit.myBtnTab', function() {
		var table = $('#customerDT').DataTable();
		var customerObject = [];
		customerObject.push(table.row( $(this).parents('tr') ).data());
		/*categoryObject.push(table.row(this).data());*/
		
		var object = customerObject[0];
		window.location.href="addcustomer?customerid="+object['customerid'];
		
	});
}

function confirmCustomerDataDelete(tableRow,customerObj)
{
/*$('.btn.btn-white').on('click',function()
{
	*/
	customerObj.oprn = 'DEL';
	$.ajax({
		type:'POST',
		contentType:'application/json',
		url:'savecustomer',
		data:JSON.stringify(customerObj),
		success:function(data,textStatus,jqXHR)
		{
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			
			
		}
	});
	
	
	tableRow.remove().draw();
	
	/*});*/
}

function loadaddcustomer()
{
	var customerid = getParameterByName('customerid');
$.ajax({
	type:'GET',
	contentType:'application/json',
	url:'loadaddcustomer/'+customerid,
	success:function(data,textStatus,jqXHR)
	{
		var responseType = getResponseType(data);
		var responseValue = getResponseValue(data);
		
		var customerList = responseValue['customerList'];
		var customerAddressList = responseValue['customerAddressList'];
		
		var customerobject = customerList[0];
		
		if(customerid.length > 0)
			{
			$('#custidtext').val(customerid);	
			}
		
		$('#firstnametext').val(customerobject['customerfirstname']);
		$('#lastnametext').val(customerobject['customerlastname']);
		$('#emailidtext').val(customerobject['customeremail']);
		$('#mobilenotext').val(customerobject['customermobileno']);
		//console.log(customerobject['customerstatus']);
		$('#groupselect').val(customerobject['customergroup']);
		$('#oprntext').val("UPD");
		for (var indx in customerAddressList) {
			var customerAAddressObj = customerAddressList[indx];
		addtoAddresstable(customerAAddressObj);
		}
		if(customerobject['customerstatus'] == "inactive"){
			$("#myonoffswitch1").prop("checked",false);
		}
	}
});	

$.ajax({
	type:'GET',
	contentType:'application/json',
	url:'country',
	success:function(data,textStatus,jqXHR)
	{
		var responseType = getResponseType(data);
		var responseValue = getResponseValue(data);
		var html = '<option value="0">--select--</option>';
		
		$.each(responseValue, function(key,value){
			html += '<option value="'+value.id+'">'+value.countryname+'</option>';
		});
		$(".countries").append(html);
	}
});

}

function getStates(countryId){
	console.log("countryId"+countryId)
	$.ajax({
		type:'GET',
		contentType:'application/json',
		url:'states/'+countryId,
		success:function(data,textStatus,jqXHR)
		{
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			var html = '<option value="0">--select--</option>';
			
			$.each(responseValue, function(key,value){
				html += '<option value="'+value.id+'">'+value.statename+'</option>';
			});
			$(".states").append(html);
		}
	});
}

function getCities(stateId){
	console.log("stateId"+stateId)
	$.ajax({
		type:'GET',
		contentType:'application/json',
		url:'cities/'+stateId,
		success:function(data,textStatus,jqXHR)
		{
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			var html = '<option value="0">--select--</option>';
			
			$.each(responseValue, function(key,value){
				html += '<option value="'+value.id+'">'+value.cityname+'</option>';
			});
			$(".cities").append(html);
		}
	});
}

function addcustomertolist()
{
	var indexPosition = customeraddressArray.length + 1;
	var streetaddress = $("#streetaddresstext").val();
	var city = $("#citytext").val();
	var state = $("#statetext").val();
	var country = $("#countrytext").val();
	var zipcode = $("#zipcodetext").val();
    
	 var customerObj = new Object();
	    customerObj.streetaddress = streetaddress;
	    customerObj.city = city;
	    customerObj.state = state;
	    customerObj.country = country;
	    customerObj.zipcode = zipcode;
	    customerObj.index = indexPosition;
	    customerObj.customerid= $('#custidtext').val();
	    customerObj.oprn = "INS";
	    
	    //console.log(customerObj);
	    
	    addtoAddresstable(customerObj);
}

function addtoAddresstable(customerObj)
{
	/*var markup = "<tr><td><input type='checkbox' name='record'></td><td>" + name + "</td><td><input type='text' name='record' value='" + email + "'></td></tr>";*/
	var indexPosition = customerObj.index;
    var markup = "<tr style='height:40px'>" +
"<td><input type='text' style='height:65px;' name='streetaddress"+indexPosition+"' value='"+customerObj.streetaddress+"' onchange='updateitem(1,"+indexPosition+")'></input></td>" +
"<td><input type='text' name='city"+indexPosition+"' value='"+customerObj.city+"' onchange='updateitem(2,"+indexPosition+")'></td>" +
"<td><input type='text' name='state"+indexPosition+"' value='"+customerObj.state+"' onchange='updateitem(3,"+indexPosition+")'></td>" +
"<td><input type='text' name='country"+indexPosition+"' value='"+customerObj.country+"' onchange='updateitem(4,"+indexPosition+")'></td>" +
"<td><input type='text' name='zipcode"+indexPosition+"' value='"+customerObj.zipcode+"' onchange='updateitem(5,"+indexPosition+")'></td>" +
    							  			  "<td><input type='button' value='Del' onclick='deletecustomeraddress("+customerObj.index+")' " +
    							  			  		"name='record"+customerObj.index+"'></td>" +
    						"</tr>";
    $("table tbody").append(markup);
    addtoCustomeraddressArray(customerObj);
    clearaddressfields();
}

function clearaddressfields()
{
	$("#streetaddresstext").val("");
	$("#citytext").val("");
	$("#statetext").val("");
	$("#countrytext").val("");
	$("#zipcodetext").val("");	
}
function updateitem(itemsno,index)
{
	 
	var idname="";
	if(parseInt(itemsno) == 1)
		{
		idname = "streetaddress";
		}
	else if(parseInt(itemsno) == 2)
		{
		idname = "city";
		}
	else if(parseInt(itemsno) == 3)
		{
		idname = "state";
		}
	else if(parseInt(itemsno) == 4)
		{
		idname = "country";
		}
	else if(parseInt(itemsno) == 5)
		{
		idname = "zipcode";
		}
	
	var bluredIdname = idname+index;
	updateitemvalue(bluredIdname,index,itemsno)
}

function updateitemvalue(bluredIdname,indexPosition,itemsno)
{
	
	$("td input[name="+bluredIdname+"]").each(function(){
		
		var enteredvalue = ($(this).val());
		 var updateAddrObj;
		 var removeItem;
		for (var indx in customeraddressArray) {
			
	updateAddrObj = (customeraddressArray[indx])
     var cusaddrsArrayindex = updateAddrObj.index;
     
     if(indexPosition == cusaddrsArrayindex)
    	 {
    	 removeItem = customeraddressArray[indx];
    	 
    	 if(parseInt(itemsno) == 1)
    		 {
    		 updateAddrObj.streetaddress = enteredvalue;	 
    		 }
    	 else if(parseInt(itemsno) == 2)
    		 {
    		 updateAddrObj.city = enteredvalue;	 
    		 }
    	 else if(parseInt(itemsno) == 3)
    		 {
    		 updateAddrObj.state = enteredvalue;	 
    		 }
    	 else if(parseInt(itemsno) == 4)
    		 {
    		 updateAddrObj.country = enteredvalue;	 
    		 }
    	 else if(parseInt(itemsno) == 5)
    		 {
    		 updateAddrObj.zipcode = enteredvalue;	 
    		 }
    	 
    	 customeraddressArray = jQuery.grep(customeraddressArray, function(value) {
  		   return value != removeItem;
  		 });
  		 
  		 customeraddressArray.push(updateAddrObj);
  		 
    	 break;
    	 }
     
   }
	});
}

function deletecustomeraddress(index)
{
	$("table tbody").find("input[name='record"+index+"']").each(function(){
    	/*if($(this).is(":checked")){*/
            $(this).parents("tr").remove();
        /*}*/
            

    		
    		 var updateAddrObj;
    		 var removeItem;
    		for (var indx in customeraddressArray) {
    			
    	updateAddrObj = (customeraddressArray[indx])
         var cusaddrsArrayindex = updateAddrObj.index;
         
         if(index == cusaddrsArrayindex)
        	 {
        	 removeItem = customeraddressArray[indx];
        	
        	 
        	 customeraddressArray = jQuery.grep(customeraddressArray, function(value) {
      		   return value != removeItem;
      		 });
      		 
	      	break;
        	 }
         
       }
    });	
}
function addtoCustomeraddressArray(customerObj)
{
	customeraddressArray.push(customerObj);	
		
}

function savecustomer()
{
	if(customeraddressArray.length == 0)
		{
		alert("Please Enter atleast one Address");
		return false;
		}
	else
		{
		var customerObj = new Object();
		customerObj.customerid = $('#custidtext').val();
		customerObj.customerfirstname = $('#firstnametext').val();
		customerObj.customerlastname = $('#lastnametext').val();
		customerObj.emailid = $('#emailidtext').val();
		customerObj.mobileno = $('#mobilenotext').val();
		console.log(customerObj.status);
		customerObj.userid = $('#inventoryuseridtext').val();
		customerObj.oprn = oprn = $('#oprntext').val();
		customerObj.customergroupid = $('#groupselect').val();
		
		var status = "active";
		if($('#myonoffswitch1').prop('checked'))
			{
			status = "active";
			}
		else
			{
			status = "inactive";
			}
		customerObj.status = status;
		
		customerObj.addresslist = JSON.stringify(customeraddressArray);
		/*alert(JSON.stringify(customerObj.addresslist))*/
		$.ajax({
			type:'POST',
			contentType:'application/json',
			url:'savecustomer',
			data:JSON.stringify(customerObj),
			success:function(data,textStatus,jqXHR)
			{
				var responseType = getResponseType(data);
				var responseValue = getResponseValue(data);
				
				alert(responseMsg(responseValue));
				window.location.href="customer"
			}
		});
		}

}

function loadcustomerview()
{
	var customerid = getParameterByName('customerid');
	var customerlistsize = $('#customertablesize').val();
	
	for(var loop = 0;loop < customerlistsize;loop++)
		{
		if(parseInt($('#customerradio'+loop).val()) == parseInt(customerid))
			{
			$('#customerradio'+loop).attr('checked',true);
			}
		else
			{
			$('#customerradio'+loop).attr('checked',false);
			}
		}
	displayOverviewDetails(customerid);
}

function getCustomerList(pageno)
{
	var status = $("#statusChange").val();
$.ajax({
	type:'GET',
	contentType : 'application/json',
	url:'loadcustomerviewpagewise/'+pageno+'/'+status,
	success:function(data,textStatus,jqXHR)
	{
		var responseType = getResponseType(data);
		var responsevalue = getResponseValue(data)
		
		$("#customertable > tbody").html("");
		
		var customerHTML = "";
		var customerCount = 0;
		
		$.each(responsevalue,function(index,value){
			customerHTML += "<tr><td width='50px'> <input type='radio' id='customerradio"+index+"' value="+value.value+" name='customerradio' " +
					"onclick='displayOverviewDetails(this.value)'> </td><td> "+value.label+"</td></tr>";
			
			customerCount = parseInt(customerCount) + 1;
		});
		$("#customertable").find('tbody').append(customerHTML);
		$('#customertablesize').val(customerCount);
	}
});	
}

function changebystatus(status){
	$("#statusChange").val(status);
	$.ajax({
		type:'GET',
		contentType : 'application/json',
		url:'loadcustomerviewstatuswise/'+status,
		success:function(data,textStatus,jqXHR)
		{
			var responseType = getResponseType(data);
			var responsevalue = getResponseValue(data);
			
			$("#customertable > tbody").html("");
			
			var customerHTML = "";
			var customerCount = 0;
			
			$.each(responsevalue,function(index,value){
				customerHTML += "<tr><td width='50px'> <input type='radio' id='customerradio"+index+"' value="+value.value+" name='customerradio' " +
						"onclick='displayOverviewDetails(this.value)'> </td><td> "+value.label+"</td></tr>";
				
				customerCount = parseInt(customerCount) + 1;
			});
			$("#customertable").find('tbody').append(customerHTML);
			$('#customertablesize').val(customerCount);
			$("#tableCountCustomer").val(customerCount);
			
			
			var paginationHTML = "";
			var count = customerCount/10;
			var substr = count.toString().split('.');
			var length = substr[0];
			var lengthInt = parseInt(substr[0]);
			var lengthFinal = lengthInt + 2;
			console.log(lengthFinal);
			paginationHTML = '<div id="sortSelect"><select class="selectNor " onchange="getCustomerList(this.value)">';
			
			
			for(i=1;i<lengthFinal;i++)
				{
				paginationHTML += '<option value ="'+i+'">Page '+i+'</option>';
				}
            paginationHTML += '</select></div>';
			
			console.log(paginationHTML);
			$("#sortSelect").html(paginationHTML);
			
		}
	});	
}

function displayOverviewDetails(customerid)
{
	$.ajax({
		type:'GET',
		contentType:'application/json',
		url:'customerviewdetails/'+customerid,
		success:function(data,textStatus,jqXHR)
		{
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			
			if(responseType == 'S')
				{
				  
					var overviewList = responseValue['overViewList'];
					var addressList = responseValue['addressList'];
					var invoiceList = responseValue['invoiceList'];
					var orderListSize = responseValue['orderListSize'];
					var salesList = responseValue['salesList'];
					var customersOrderList = responseValue['customersOrderList'];
					var paymentList = responseValue['paymentList'];
					
					
					if(overviewList.length > 0)
						{
						
					var overviewObj = overviewList[0];
					$('#firstnametext').text(overviewObj.firstname);
					$('#custnametext').text(overviewObj.customername);
					$('#customerNameHeadingText').text(overviewObj.customername);
					$('#emailtext').text(overviewObj.email);
					$('#createdbytext').text(overviewObj.createdby); 
					$('#mobilenotext').text(overviewObj.mobileno);
						}

					var addressText = "";
					$.each(addressList, function(index, value) {
						addressText += (index+1) + ") "+value.streetaddress+", "+value.city+" - "+value.zipcode+", "+value.state+", "+value.country+"."+"<br></br>";
					});
					$('#addressdiv').html(addressText);
					
					if(invoiceList.length > 0)
						{
						
					var invoiceObj = invoiceList[0];
					$('#receivablestext').text(invoiceObj.currency+" "+invoiceObj.totalamount);
					$('#invoicecounttext').text(invoiceObj.invoicecount);
					$('#shippedtext').text(orderListSize);
						}
					else
						{
						$('#receivablestext').text(0);
						$('#invoicecounttext').text(0);
						$('#shippedtext').text(0);
						}
					
					$('#salesDT').DataTable( {
						"destroy":true,
						"ordering":false,
						"info":     false,
						"bLengthChange": false,
				    	data: salesList,
				        columns: [
				            { data: 'type' },
				            { data: 'invoicecode' },
				            { data: 'salesat' },
				            { data: 'salesat' },
				            { data: 'totalamount' },
				            { data: 'balanceamount' },
				            { data: 'status' }
				        ]
			            
				    } );
					
					$('#paymentDT').DataTable( {
						"destroy":true,
						"ordering":false,
						"info":     false,
						"bLengthChange": false,
				    	data: paymentList,
				        columns: [
				            { data: 'poscode' },
				            { data: 'postaxamount' },
				            { data: 'posdiscount' },
				            { data: 'posgrandtotal' },
				            { data: 'pospaidamt' },
				            { data: 'posbalanceamount' }
				        ]
			            
				    } );
					
					$('#ordersDT').DataTable( {
						"destroy":true,
						"ordering":false,
						"info":     false,
						"bLengthChange": false,
				    	data: customersOrderList,
				        columns: [
				            { data: 'batchid' },
				            { data: 'salescount' },
				            { data: 'unitprice' },
				            { data: 'taxamount' },
				            { data: 'totalamount' }
				        ]
			            
				    } );
				}
		}
	});
}

/*Customer Master End*/

/*UserRole Master Start*/

var userroleArray = [];

function loaduserrole()
{
 
for(var index = 0;index < $('#userrolecount').val(); index++)	
	{
		var menudetails = $('#menudetailscheck'+index).val();
		var menudetailsArray = menudetails.split("$$$");
		var ismenuSelected = parseInt(menudetailsArray[0]);
		var issubmodule = parseInt(menudetailsArray[1]);
		var parentid = parseInt(menudetailsArray[2]);
		var moduleid = parseInt(menudetailsArray[3]);
		var usermenusno = parseInt(menudetailsArray[4]);
		
		if(parseInt(ismenuSelected) == 1)
			{
			   $('#menuid'+index).attr('checked',true)	
			}
		else
			{
			   $('#menuid'+index).attr('checked',false)	
			}
		
		var usermenuObj = new Object();
		
		usermenuObj.usertype = $('#usertypeselect').val();
		usermenuObj.userid = "0";
		usermenuObj.moduleid = moduleid;
		usermenuObj.createdby = $('#inventoryuseridtext').val();
		usermenuObj.menuaccess = ismenuSelected;
		usermenuObj.usermenusno = usermenusno;
		
		userroleArray.push(usermenuObj);
	}
}

function getmenuid(checkedid,menutype,parentid,moduleid,p_c_id,t)
{
	if (t.is(':checked')) {
	     var rel = p_c_id.slice(0,-1);
	     var lastChar = p_c_id[p_c_id.length -1];
	     
	     if(rel == "child"){
	    	 $('.parent'+lastChar).prop('checked',true);
	     }
	}   
	
var buildmenudetails = "1$$$"+menutype+"$$$"+parentid;

for(var index = 0;index < $('#userrolecount').val(); index++)	
{
	var menudetails = $('#menudetailscheck'+index).val();
	var menudetailsArray = menudetails.split("$$$");
	var ismenuSelected = parseInt(menudetailsArray[0]);
	var menudetailsissubmodule = parseInt(menudetailsArray[1]);
	var menudetailsparentid = parseInt(menudetailsArray[2]);
	var menudetailsmoduleid = parseInt(menudetailsArray[3]);
	
	checkmenus(checkedid,menutype,moduleid,menudetailsparentid,index);
}
}


function checkmenus(checkedid,menutype,moduleid,menudetailsparentid,index)
{
	if(menutype == 0)
	{
	var menuselected = "0";
	console.log("moduleid  "+moduleid);
	console.log("menudetailsparentid  "+menudetailsparentid);
	console.log("menuselected  "+menuselected);
	if(moduleid == menudetailsparentid)
	{
		
		if($("#menuid"+checkedid).is(':checked'))
			{
			$('#menuid'+index).prop('checked',true);
			menuselected = "1";
			}
		else
			{
			$('#menuid'+index).attr('checked',false);
			menuselected = "0";
			}
		 
	}	
	}	
}
function getusertypemenus(value)
{
	$.ajax({
		type:'GET',
		contentType:'application/json',
		url:'getuserrolemenus/'+value,
		success:function(data,textstatus,jqXHR)
		{
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			
			userroleArray = [];
			
			for(var index = 0;index < responseValue.length; index++)	
			{
				var usermenuObj = (responseValue[index]);
				$('#menudetailscheck'+index).val(usermenuObj.menudetails);
		
				var menudetails = $('#menudetailscheck'+index).val();
				var menudetailsArray = menudetails.split("$$$");
				var ismenuSelected = parseInt(menudetailsArray[0]);
				var issubmodule = parseInt(menudetailsArray[1]);
				var parentid = parseInt(menudetailsArray[2]);
				var moduleid = parseInt(menudetailsArray[3]);
				var usermenusno = parseInt(menudetailsArray[4]);
				
				if(parseInt(ismenuSelected) == 0)
					{
					$('#menuid'+index).prop('checked',false);
					}
				else
					{
					$('#menuid'+index).prop('checked',true);
					}
				userroleArray.push(usermenuObj);
	}
			
		}
	});
}

function saveusermenus()
{
	userroleArray = [];
	for(var index = 0;index < $('#userrolecount').val(); index++)	
	{
	var removeItem;
	var selected = 0;
	/*var updatemenuObj = (userroleArray[index]);*/
	
	console.log(index);
	var menuselect = $("#menuid"+index).is(':checked');
	
	if(menuselect)
		{
		selected = "1";
		}
	
	/*removeItem = userroleArray[index];
	if(updatemenuObj.menuaccess != selected)
	{
	updatemenuObj.menuaccess = selected;
	alert($("#menuid"+index).val()+"   "+menuselect+"   "+selected)
	userroleArray = jQuery.grep(userroleArray, function(value) {
			   return value != removeItem;
			 });
	userroleArray.push(updatemenuObj);
	}*/
	 
	
	var menudetails = $('#menudetailscheck'+index).val();
	var menudetailsArray = menudetails.split("$$$");
	var ismenuSelected = parseInt(menudetailsArray[0]);
	var issubmodule = parseInt(menudetailsArray[1]);
	var parentid = parseInt(menudetailsArray[2]);
	var moduleid = parseInt(menudetailsArray[3]);
	var usermenusno = parseInt(menudetailsArray[4]);
	
	var usermenuObj = new Object();
	
	usermenuObj.usertype = $('#usertypeselect').val();
	usermenuObj.userid = "0";
	usermenuObj.moduleid = moduleid;
	usermenuObj.createdby = $('#inventoryuseridtext').val();
	usermenuObj.menuaccess = selected;
	usermenuObj.usermenusno = usermenusno;
	console.log(usermenuObj);
	userroleArray.push(usermenuObj);
	
	}
	
	$.ajax({
	type:'POST',
	url:'saveuserrole/'+$('#usertypeselect').val(),
	contentType:'application/json',
	data:JSON.stringify(userroleArray),
	success:function(data,textStatus,jqXHR)
	{
		var responseType = getResponseType(data);
		var responseValue = getResponseValue(data);
		
		alert((responseValue));
	}
});	
}

/*UserRole Master End*/

/*New User Creation Start*/

function loadaddnewuser()
{
	var userid = getParameterByName('userid');
	$('#usernameidtext').val(userid);
	
	if(parseInt(userid) > 0)
		{
		$('#oprntext').val("UPD");
		getuserdetail(userid);
		}
	else
		{
		$('#oprntext').val("INS");
		}
}

function getuserdetail(userid)
{
	$.ajax({
		type:'GET',
		url:'getuserdetail/'+userid,
		contentType:'application/json',
		success:function(data,textStatus,jqXHR)
		{
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);

			var userobj = responseValue[0];
			
			$('#usernametext').val(userobj.username);
			$('#firstnametext').val(userobj.firstname);
			$('#lastnametext').val(userobj.lastname);
			$('#usertypeselect').val(userobj.usertype);
			$('#passwordtext').val(userobj.password);
			$('#emailidtext').val(userobj.email);
			$('#storeselect').val(userobj.storeid);
			$('#phonenotext').val(userobj.phoneno);
			if(userobj.status == 1)
				{
				$('#myonoffswitch1').attr('checked',true);	
				}
			else
				{
				$('#myonoffswitch1').attr('checked',false);
				}
			
		}
	});
}

function loadusers()
{
	$.ajax({
		type:"GET",
		contentType:'application/json',
		url:'loadusers',
		success:function(data,textStatus,jqXHR)
		{
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			
			$('#userDT').DataTable( {
				"destroy":true,
		    	data: responseValue,
		        columns: [
		            { data: 'username' },
		            { data: 'email' },
		            { data: 'phoneno' },
		            { data: 'storename' },
		            { data: 'usertypedesc' },
		            { data: 'status' },
		            { data: 'action' }
		        ]
	            
		    } );
			
			selectUserItem();
		}
	});	
}

function selectUserItem() {
	$('#userDT tbody').on('click', '.edit.myBtnTab', function() {
		var table = $('#userDT').DataTable();
		var productObject = [];
		productObject.push(table.row( $(this).parents('tr') ).data());
		/*categoryObject.push(table.row(this).data());*/
		
		var object = productObject[0];
		window.location.href="adduser?userid="+object['userid'];
		
	});
	
}


function savenewuser()
{
	
	validateonbuttonclick('#firstnametext','input');
	validateonbuttonclick('#lastnametext','input');
	validateonbuttonclick('#passwordtext','input');
	
	var oprn = $('#oprntext').val();
	
	var userpassword = $('#passwordtext').val();
	var specialCharacterCount = specialcharactercheck(userpassword);
	if(oprn == 'INS')
		{
		if($('#myonoffswitch1').prop('checked') == false)
			{
			alert("Cant create new user in Inactive Status");
			return false
			}
		}
	if(specialCharacterCount == 0)
		{
		alert("Password must contain Special Characters");
		return false;
		}
	var userobj = new Object();
	userobj.firstname = $('#firstnametext').val();
	userobj.lastname = $('#lastnametext').val();
	userobj.usertype = $('#usertypeselect').val();
	userobj.password = userpassword;
	userobj.email = $('#emailidtext').val();
	userobj.storeid = $('#storeselect').val();
	userobj.phoneno = $('#phonenotext').val();
	userobj.username = $('#usernametext').val();
	userobj.createdby = $('#inventoryuseridtext').val();
	userobj.userid = $('#usernameidtext').val();
	
	if($('#myonoffswitch1').prop('checked') == true)
		{
		userobj.status = 1;	
		}
	else
		{
		userobj.status = 0;
		}
	
	userobj.oprn = $('#oprntext').val();
	
	$.ajax({
		type:'POST',
		contentType:'application/json',
		url:'saveuser',
		data:JSON.stringify(userobj),
		success:function(data,textStatus,jqXHR)
		{
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			
			if(responseType == 'S')
				{
				alert(responseMsg(responseValue));
				window.location.href = "user"
				}
		}
	});
}
/*New User Master Creation End*/