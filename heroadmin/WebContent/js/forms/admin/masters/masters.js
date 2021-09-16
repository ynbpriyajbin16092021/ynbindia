var currencySymbolLabels = [];
var finalarray  = [];
var txnformatArr = [];
var txnqualformatArr = [];
var productsuggestions = [];
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
			$('td:eq(1)', nRow).append('<button class="edit myBtnTab" > <i class="fa fa-edit"></i> </button><button class="delete myBtnTab" data-toggle="modal" data-target="#modal-delet" > <i class="fa fa-trash"></i> </button>  ');
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
			$('td:eq(1)', nRow).append('<button class="edit myBtnTab" > <i class="fa fa-edit"></i> </button><button class="delete myBtnTab" data-toggle="modal" data-target="#modal-delet" > <i class="fa fa-trash"></i> </button>  ');
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
		
		var object = categoryObject[0];
		$.each(object, function(index, value) {
			$('#categoryidtext').val(object[2]);
			$('#categorytext').val(object[0]);
			
			$('#oprntext').val("UPD");
			
		});
		
		var table = $('#categoryDT').DataTable();
		confirmCategoryDelete(table.row($(this).parents('tr')));
		
	});
	$('#categoryDT tbody').on('click', '.edit.myBtnTab', function() {
		var table = $('#categoryDT').DataTable();
		var categoryObject = [];
		categoryObject.push(table.row( $(this).parents('tr') ).data());
		
		var object = categoryObject[0];
		
		window.location.href="addcategory?categoryid="+object['2'];
		$.each(object, function(index, value) {
			$('#categoryidtext').val(object[2]);
			
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
		console.log(bankObject);
		var object = bankObject[0];
		window.location.href="addbank?bankid="+object['2'];
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
		alert("saved successfully");
		categoryObj.categoryid = data.inventoryResponse.responseObj.id;
		console.log(categoryObj.categoryid);
		//console.log(data);
		prepareCategoryDataTable(categoryObj,oprn);
		clearCategoryFormValues();
		/*window.location.href="category";*/
		}
	else if(responseType == 'F')
		{
		
		displayFailureMsg(oprn,responseValue);
		/*window.location.href="category";*/
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
		alert("saved successfully");
		//bankObj.bankid = generatedidValue(responseValue);
		bankObj.bankid = data.inventoryResponse.responseObj.id;
		prepareBankDataTable(bankObj,oprn);
		clearBankFormValues();
		/*window.location.href="bank";*/
	}
	else if(responseType == 'F')
	{
	
	displayFailureMsg(oprn,responseValue);
	/*window.location.href="bank";*/
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
		/*table.row( 0 ).data( [ categoryObject.categoryname, '<button class="edit myBtnTab" > <i class="fa fa-edit"></i> </button><button class="delete myBtnTab" data-toggle="modal" data-target="#modal-delet" > <i class="fa fa-trash"></i> </button>  ' ] ).draw();*/
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
		/*location.reload(false);*/
		/*table.row( 0 ).data( [ categoryObject.categoryname, '<button class="edit myBtnTab" > <i class="fa fa-edit"></i> </button><button class="delete myBtnTab" data-toggle="modal" data-target="#modal-delet" > <i class="fa fa-trash"></i> </button>  ' ] ).draw();*/
		}
}

function clearCategoryFormValues()
{
	$('#oprntext').val("INS");
	$('#categoryidtext').val("");
	$('#categorytext').val("");	
	/*location.reload(true);*/
}

function clearBankFormValues()
{
	$('#oprntext').val("INS");
	$('#bankidtext').val("");
	$('#banktext').val("");	
	/*location.reload(true);*/
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
			$('td:eq(1)', nRow).append('<button class="edit myBtnTab" > <i class="fa fa-edit"></i> </button><button class="delete myBtnTab" data-toggle="modal" data-target="#modal-delet" > <i class="fa fa-trash"></i> </button>  ');
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
		console.log(object['2']);
		window.location.href="addmanufacturercompany?companyid="+object['2'];
		$.each(object, function(index, value) {
			setValuestoCompanyFields(object);
			$('#oprntext').val("UPD");
			
		});
		
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
					alert("saved successfully");
					companyObj.companyid = data.inventoryResponse.responseObj.id;
					prepareCompanyDataTable(companyObj,companyObj.oprn);
					clearCompanyFields();	
					/*window.location.href="manufacturercompany";*/
					}
				else if(responseType == 'F')
					{
					displayFailureMsg(oprn,responseValue);
					/*window.location.href="manufacturercompany";*/
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
	/*location.reload(true);*/
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
			$('td:eq(1)', nRow).append('<button class="edit myBtnTab" > <i class="fa fa-edit"></i> </button><button class="delete myBtnTab" data-toggle="modal" data-target="#modal-delet" > <i class="fa fa-trash"></i> </button>  ');
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
		window.location.href = "adduom?uomid?="+object[2];
		$.each(object, function(index, value) {
			$('#uomidtext').val(object[2]);
			$('#uomdesctext').val(object[0]);
			$('#oprntext').val("UPD");
			
		});
		/*$("#addbutton").html("Save");
		$('#myModal').modal('show');*/
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
			alert("saved successfully");
			prepareUOMDataTable(uomObj,oprn);
			clearUOMFields();
			/*window.location.href="uom";*/
			}
		else if(responseType == 'F')
			{
			displayFailureMsg(oprn,responseValue);
			/*window.location.href="uom";*/
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
	/*location.reload(true);*/
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

$.ajax(
		{
			contentType:'application/json',
			success : function(data,textStatus,jqXHR)
			{
				var responseType = getResponseType(data);
				var responseValue = getResponseValue(data);
				
				
				
				$.each(responseValue, function(index, value) {
					productsuggestions.push(responseValue[index]);
					currencySymbolLabels.push(responseValue[index]['label']);
				});

				$( "#currencysysmbolsuggestiontext" )
			      .autocomplete({
			        minLength: 0,
			        source: function( request, response ) {
			          response( $.ui.autocomplete.filter(
			        		  productsuggestions, request.term  ) );
			        },
			        focus: function() {
			        	response( $.ui.autocomplete.filter(
				        		  productsuggestions, request.term  ) );
			        },
			        select: function( event, ui ) {
			          //var terms = split( this.value );
			          //terms.pop();
			          $('#currencysysmbolsuggestiontext').val(ui.item.label);
			          $('#currencysysmboltext').val(ui.item.value);
			          $('#currencyhtmlcode').html(ui.item.data);
			         // terms.push( ui.item.label );
			          //terms.push( "" );
			          
			          return false;
			        }
			      });
                
			},
			type:'GET',
			url:"currencysymbolsuggestions"
			}
		);



}
function loadaddnewcurrency()
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
$.ajax(
		{
			contentType:'application/json',
			success : function(data,textStatus,jqXHR)
			{
				var responseType = getResponseType(data);
				var responseValue = getResponseValue(data);
				var productsuggestions = [];
				
				
				$.each(responseValue, function(index, value) {
					productsuggestions.push(responseValue[index]);
					currencySymbolLabels.push(responseValue[index]['label']);
				});

				$( "#currencysysmbolsuggestiontext" )
			      .autocomplete({
			        minLength: 0,
			        source: function( request, response ) {
			          response( $.ui.autocomplete.filter(
			        		  productsuggestions, request.term  ) );
			        },
			        focus: function() {
			          return false;
			        },
			        select: function( event, ui ) {
			          //var terms = split( this.value );
			          //terms.pop();
			          $('#currencysysmbolsuggestiontext').val(ui.item.label);
			          $('#currencysysmboltext').val(ui.item.value);
			          $('#currencyhtmlcode').html(ui.item.data);
			         // terms.push( ui.item.label );
			          //terms.push( "" );
			          
			          return false;
			        }
			      });
                
			},
			type:'GET',
			url:"currencysymbolsuggestions"
			}
		);

}

function displayCurrencyDatalist(dataTableSet) {
	$('#currencyDT').DataTable({
		destroy : true,
		data : dataTableSet,
		ordering: false,
		"fnCreatedRow": function( nRow, aData, iDataIndex ) {
			/*$('td:eq(1)', nRow).append("<label style='cursor:pointer;'>Edit</label><img src='../resources/images/delete.jpg' width='30px;' height='30px' id='editbtn'></img>");*/
			$('td:eq(5)', nRow).append('<button class="edit myBtnTab" data-toggle="modal" data-target="#currencies"> <i class="fa fa-edit"></i> </button><button class="delete myBtnTab" data-toggle="modal" data-target="#modal-delet" > <i class="fa fa-trash"></i> </button>  ');
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
			$('#exchngratetext').val(object[10]);
			$('#convratetext').val(object[11]);
			$('#currencydecimal').val(object[4]);
			 
			$('#currencyidtext').val(object[6]);
			
			$('#currencycodeselect').val(object[7]);
			$('#currencysysmbolsuggestiontext').val(object[8]);
			$('#currencyhtmlcode').html(object[1]);
			
			$('#fromdatetext').val(object[2]);
			$('#todatetext').val(object[3]);
			
			
			
			$('#oprntext').val("UPD");
			
		});
		var table = $('#currencyDT').DataTable();
		confirmCurrencyDelete(table.row($(this).parents('tr')));
		
	});
	$('#currencyDT tbody').on('click', '.edit.myBtnTab', function() {
		$(this).parents('tr').addClass("selected").siblings().removeClass("selected"); 
		var table = $('#currencyDT').DataTable();
		var categoryObject = [];
		categoryObject.push(table.row( $(this).parents('tr') ).data());
		/*categoryObject.push(table.row(this).data());*/
		
		var object = categoryObject[0];
		/*console.log(categoryObject);*/
	     console.log(object);
	    /* window.location.href="addcurrency?currencyid="+object['6'];*/
		/*$.each(object, function(index, value) {*/
			$('#currencynametext').val(object[0]);
			$('#currencysysmboltext').val(object[9]);
			$('#exchngratetext').val(object[10]);
			$('#convratetext').val(object[11]);
			/*$('#currdecimaltext').val(object[4]);*/
			$('#currencydecimal').val(object[4]) 
			$('#currencyidtext').val(object[6]);
			
			$('#currencycodeselect').val(object[7]);
			$('#currencysysmbolsuggestiontext').val(object[8]);
			$('#currencyhtmlcode').html(object[1]);
			
			$('#fromdatetext').val(object[2]);
			$('#todatetext').val(object[3]);
			
			$('#oprntext').val("UPD");
			console.log(object[12])
			if(object[12] == 1 )
			{
				$('#basecurrchk').prop('checked', true);
			}
			
			
		/*});*/
		
		$('#myModal').modal('show');
	});
	
}

function confirmCurrencyDelete(tableRow)
{
	$('.btn.btn-white').on('click',function()
	{
	$('#oprntext').val("DEL");
	savecurrency();
	//tableRow.remove().draw();
	//clearCurrencyFields();
	$("#modal-delet").modal('hide');
	
   //
	
});
}

function savecurrency()

{
var isValidArray = [];
var isValid = true;
var oprn = $('#oprntext').val();
if(oprn != 'DEL')
	{
	isValidArray.push(validateonbuttonclick('#currencynametext','input'));
	/*isValid = validateonbuttonclick('#currencycodeselect','select');*/
	isValidArray.push(validateonbuttonclick('#currencycodeselect','input'));
	isValidArray.push(validateonbuttonclick('#exchngratetext','input'));
	isValidArray.push(validateonbuttonclick('#convratetext','input'));
	isValidArray.push(validateonbuttonclick('#currencydecimal','input'));
	isValidArray.push(validateonbuttonclick('#basecurrchk','input'));
	
	isValidArray.push(validateonbuttonclick('#fromdatetext','input'));
	isValidArray.push(validateonbuttonclick('#todatetext','input'));

	}
var totalValid = jQuery.inArray( false, isValidArray );

if(totalValid < 0)
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
	var currfromdate = $('#fromdatetext').val();
	var currtodate = $('#todatetext').val();
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
		displayAlertMsg("Rate should not be greater than 100");
		return false;
		}
	else
		{
		clientValid = true;
		}
	if(parseInt(basecurr) == 1)
		{
		exchangerate = 1 ;
		conversionrate = 1;
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
	currencyObj.fromdate = currfromdate;
	currencyObj.todate = currtodate;
	currencyObj.oprn = oprn;
	console.log(currencyObj.oprn)
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
		console.log(responseType)
		console.log(responseValue)
		if(responseType == 'S')
			{
			displaySuccessMsg(data);
			clearCurrencyFields();
			console.log(data)
			/*currencyObj.currencyid = generatedidValue(responseValue);	*/ 
			/*prepareCurrencyDataTable(currencyObj,oprn);*/
			var str = window.location.pathname;
			var res = str.split("/");
			if(res[3] == "heroadminsettings"){	
				$.get(url, function(data) {
					$("#herosettingsdiv").html(data);
					$(".datepicker").datepicker(datePickerOptions);	
				});	
			}
			else{
				//alert("saved");
				
				setTimeout("location.href = 'currency'",2000);
				/*setTimeout("location.href = 'redirect_currency.html'",60000000000000000000);*/
			}

			/*window.location.href="currency";*/
			}
		else if(responseType == 'F')
			{
			console.log(oprn)
			if(oprn == 'DEL')
			{
			displayFailureMsg("",responseMsg(responseValue));
			//setTimeout("location.href = 'currency'",2000); 
			}
			else{
				displayFailureMsg("",responseMsg(responseValue));
			return false;
			//setTimeout("location.href = 'currency'",2000);
			}
			}
		}
	});
	
	}

	}
else
	{
	heroNotifications.showFailure("Please fill the Mandatory fields");
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
	$('#currencycodeselect').val(Math.floor((Math.random() * 1000) + 1));
	$('#currdecimaltext').val("0");
	$('#currdecimaltext').val("0");
	
	
	$('#oprntext').val("INS");
	
	
}

function prepareCurrencyDataTable(currencyObj, oprn) {
	var table = $('#currencyDT').DataTable();
	
	if (oprn == 'INS') {
		/*var rowNode = table.row.add(
				[ currencyObj.currency, currencyObj.currencysymbol,currencyObj.exchangerate,currencyObj.conversionrate,'',currencyObj.currencyid
				  ,currencyObj.currdecimal,currencyObj.basecurrdisp]).draw().node();*/
		/*location.reload(false);*/
	}
	else if(oprn == 'UPD')
		{
		/*location.reload(false);*/
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
		ordering: false,
		"fnCreatedRow": function( nRow, aData, iDataIndex ) {
			/*$('td:eq(1)', nRow).append("<label style='cursor:pointer;'>Edit</label><img src='../resources/images/delete.jpg' width='30px;' height='30px' id='editbtn'></img>");*/
			$('td:eq(4)', nRow).append('<button class="edit myBtnTab"  data-toggle="modal" data-target="#tax"> <i class="fa fa-edit"></i> </button><button class="delete myBtnTab" data-toggle="modal" data-target="#modal-delet" > <i class="fa fa-trash"></i> </button>  ');
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
		$(this).parents('tr').addClass("selected").siblings().removeClass("selected"); 
		var table = $('#taxDT').DataTable();
		var categoryObject = [];
		categoryObject.push(table.row( $(this).parents('tr') ).data());
		/*categoryObject.push(table.row(this).data());*/
		var object = categoryObject[0];
		console.log(object['5']);
		/*window.location.href="addtax?taxid="+object['5']*/
		$.each(object, function(index, value) {
			$('#taxnametext').val(object[0]);
			$('#taxcodetext').val(object[1]);
			$('#taxratetext').val(object[2]);
			$('#taxtypeselect').val(object[6]);
			$('#taxidtext').val(object[5]);
			$('#oprntext').val("UPD");
			
		});
		//$('#myModal').modal('show');
		
	});
}

function confirmTaxDelete(tableRow)
{

$('.btn.btn-white').on('click',function()
{

	$('#oprntext').val("DEL");
	savetax();
	/*tableRow.remove().draw();*/
	$("#modal-delet").modal('hide');
	//window.location.href="tax";
	});

}


function savetax()
{
var isValid = true;
var isValidArray = [];
var oprn = $('#oprntext').val();
if(oprn != 'DEL')
	{
	isValidArray.push(validateonbuttonclick('#taxnametext','input'));
	isValidArray.push(validateonbuttonclick('#taxcodetext','input'));
	isValidArray.push(validateonbuttonclick('#taxratetext','input'));
	}
var totalValid = jQuery.inArray( false, isValidArray );

if(totalValid < 0)
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
			displayAlertMsg("Tax Rate must be less than 100")
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
					console.log(data)
					if(responseType == 'S')
						{
						displaySuccessMsg(data);
						/*taxObj.taxid = generatedidValue(responseValue);	 
						prepareTaxDataTable(taxObj,oprn);*/
						clearTaxFields();
						var str = window.location.pathname;
						var res = str.split("/");
						if(res[3] == "heroadminsettings"){	
							
							$.get(url, function(data) {
								console.log(data)
								$("#herosettingsdiv").html(data);
								$(".datepicker").datepicker(datePickerOptions);	
							});	
						}
						else{
							setTimeout("location.href = 'tax'",1000);
							/*setTimeout("location.href = 'redirect_currency.html'",60000000000000000000);*/
						}
						/*window.location.href="tax";*/
						/*window.location.reload();*/
						}
					else if(responseType == 'F')
						{
						if(oprn == 'DEL')
							{
						displayFailureMsg("",responseMsg(responseValue));
						setTimeout("location.href = 'tax'",2000);
							}
						//clearCurrencyFields();
						else{
						displayFailureMsg("",responseMsg(responseValue));
						return false;
						//setTimeout("location.href = 'tax'",900);
						}
						}
					}
				});	
		}
	
	}
else
	{
	heroNotifications.showFailure("Please fill the Mandatory fields");
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
	
	window.location.reload();
	
}

function prepareTaxDataTable(taxObj, oprn) {
	var table = $('#taxDT').DataTable();
	
	if (oprn == 'INS') {
		var rowNode = table.row.add(
				[ taxObj.taxname, taxObj.taxcode,taxObj.taxrate,taxObj.taxtypedisp,'',taxObj.taxid ]).draw().node();
	}
	else if(oprn == 'UPD')
		{
		/*location.reload(false);*/
		}
}

/*Tax Master End*/

/* userTypes Start */


function loadusertypes()
{

	 $.ajax({
			type:'GET',
			contentType:'application/json',
			url:'usertypeslist',
			success:function(data,textStatus,jqXHR)
			{
				
				var responseType = getResponseType(data);
				var responseValue = getResponseValue(data);
				
				$('#usertypeDT').DataTable( {
			    	data: responseValue,
			    	ordering:false,
			        columns: [
			            { data: 'usertypename' },
			            { data: 'deptname' },
			            { data: 'statusdesc' },
			            { data: 'action' }
			        ]
		            
			    } ); 
				selectusertypeItem();
			}
		});


}
function loadaddusertypes()
{

	 $.ajax({
			type:'GET',
			contentType:'application/json',
			url:'usertypeslist',
			success:function(data,textStatus,jqXHR)
			{
				
				var responseType = getResponseType(data);
				var responseValue = getResponseValue(data);
				
				$('#usertypeDT').DataTable( {
			    	data: responseValue,
			        columns: [
			            { data: 'usertypename' },
			            { data: 'deptname' },
			            { data: 'statusdesc' },
			           /* { data: 'action' }*/
			        ]
		            
			    } ); 
				/*selectusertypeItem();*/
			}
		});


}

function selectusertypeItem() {
	$('#usertypeDT tbody').on('click', '.delete.myBtnTab', function() {
		var table = $('#usertypeDT').DataTable();
		var usertypeObject = [];
		usertypeObject.push(table.row( $(this).parents('tr') ).data());
		/*categoryObject.push(table.row(this).data());*/
		console.log(usertypeObject)
		var object = usertypeObject[0];
		
		var table = $('#usertypeDT').DataTable();
		confirmStoreDelete(table.row($(this).parents('tr')),object);
		
	});
	$('#usertypeDT tbody').on('click', '.edit.myBtnTab', function() {
		$(this).parents('tr').addClass("selected").siblings().removeClass("selected"); 
		var table = $('#usertypeDT').DataTable();
		var usertypeObject = [];
		usertypeObject.push(table.row( $(this).parents('tr') ).data());
		
		var object = usertypeObject[0];
		$('#usertypedesctext').val(object.usertypename);
		$('#usertypeidtext').val(object.usertypeid);
		$('#deptselect').val(object.deptid);
      var imgvalue = object.usertypeimg;
		var res = imgvalue.split(".");
		$(".panel-body").css({'border':'none'});
		$("input[name='usertypeimage']").attr('checked', false);
		$("."+res[0]).css({'border' : '2px solid #febd29'});
		$("input[name='usertypeimage'][value='" + imgvalue + "']").attr('checked', true);
		if(object.usertypeid < 7){
			
			$('#deptselect').prop('disabled', true);
		}
		if(parseInt(object.status) == 0)
		{
		$('#myonoffswitch1').attr('checked',false);
		}
		else
		{
		$('#myonoffswitch1').attr('checked',true);
		}
		$('#oprntext').val('UPD');
	});
}

function saveusertype()
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
		
		var usertypeid = $('#usertypeidtext').val();
		var usertypename = $('#usertypedesctext').val();
		var dept = $('#deptselect').val();
		var usertypeimage = $('input[name=usertypeimage]:checked').val();
		
		var status = 0;
		
		if($('#myonoffswitch1').prop('checked'))
			{
			status = 1;
			}
		else
			{
			status = 0;
			}
		var oprn = $('#oprntext').val();
		
		var usertypeObj = new Object();
		usertypeObj.usertypeid = usertypeid;
		usertypeObj.usertypedesc = usertypename;
		usertypeObj.dept = dept;
		usertypeObj.usertypeimage = usertypeimage;
		usertypeObj.status = status;
		usertypeObj.oprn = oprn;
		console.log(usertypeObj);
		var clientValid = isValid;
		if(clientValid)
			{
			$.ajax(
					{
					type:'POST',
					contentType:'application/json',
					url:'saveusertype',
					data:JSON.stringify(usertypeObj),
					success:function(data,textStatus,jqXHR)
					{
						var responseType = getResponseType(data);
						var responseValue = getResponseValue(data);
						
						if(responseType == 'S')
							{
					
							displaySuccessMsg(data);
							clearUsertypeFields();
							var str = window.location.pathname;
							var res = str.split("/");
							if(res[3] == "heroadminsettings"){	
								
								/*window.location.href="currency";*/
							}
							else{
								setTimeout("location.href = 'usertypes'",2000);
								
							}
							
							}
						else if(responseType == 'F')
							{
						//	alert(responseMsg(responseValue))
								displayFailureMsg("",responseMsg(responseValue));

							return false
							}
						}
					});	
			}
		
		}
	else
		{
		return false;
		}
	/*if(responseObj.length < 5){
		
		$('#deptselect').disabled(true);
		
	}*/
	
}

function clearUsertypeFields()
{
	$('#usertypedesctext').val("");
	$('#usertypeidtext').val(0);
	$('#oprntext').val('INS');
}
/*User Types End */

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
		displayAlertMsg("Please Choose the file and Upload");
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
			alert("saved successfully");
			productObj.productid = generatedidValue(responseValue);
			
			/*window.location.href="product"*/
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
				/*alert("saved successfully");*/
				tableRow.remove().draw();
				/*window.location.reload();*/
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
			    	ordering:false,
			        columns: [
			            { data: 'storename' },
			            { data: 'country' },
			            { data: 'currencydesc' },
			            { data: 'statusdesc' },
			            { data: 'action' }
			        ]
		            
			    } ); 
				selectStoreItem();
			}
		});


}
function loadaddnewstore()
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
			            { data: 'country' },
			            { data: 'currencydesc' },
			            { data: 'statusdesc' },
			           /* { data: 'action' }*/
			        ]
		            
			    } ); 
				/*selectStoreItem();*/
				loadaddstore()
			}
		});


}

function savestore()
{
	var isValidArray = [];

	var taxArray = [];
	var isValid = true;
	var oprn = $('#oprntext').val();
	console.log($('#oprntext').val())
	if(oprn != 'DEL')
	{
		isValidArray.push(validateonbuttonclick('#storenametext','input'));	
		isValidArray.push(validateonbuttonclick('#countrytext','input'));
		isValidArray.push(validateonbuttonclick('#zipcodetext','input'));
		isValidArray.push(validateonbuttonclick('#phonetext','input'));
		isValidArray.push(validateonbuttonclick('#emailtext','input'));
	}

	var totalValid = jQuery.inArray( false, isValidArray );

	if(totalValid < 0)
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
				}else{
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
		
	
		
		var regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
		 
		if(regex.test(storeObj.email)){
			
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
			console.log("savestore2"+responseValue)
			if(responseType == 'S')
				{
				displaySuccessMsg(data);
				clearStoreFields();
				var str = window.location.pathname;
				var res = str.split("/");
				if(res[3] == "heroadminsettings"){	
					$.get(url, function(data) {
						$("#herosettingsdiv").html(data);
						$(".datepicker").datepicker(datePickerOptions);	
					});	
				}
				else{
					setTimeout("location.href = 'store'",2000);
					/*setTimeout("location.href = 'redirect_currency.html'",60000000000000000000);*/
				}
				/*window.location.href="store"*/
		}
			else if(responseType == 'F')
				{
				if(oprn == 'DEL')
                	 {
				displayFailureMsg("",responseMsg(responseValue));
				setTimeout("location.href = 'store'",2000);
                	 }
                 else{
                	 displayFailureMsg("",responseMsg(responseValue)); 
                	 return false;
                	 }
				
				
				//setTimeout("location.href = 'store'",2000);
				}
			}
		}		
		);
		
		}else{
			heroNotifications.showFailure("Your Email-id is In correct");
			return false;
		}
		
		}else{
			heroNotifications.showFailure("Please fill the Mandatory fields");
			return false;
		}
}

function clearStoreFields()
{
	$('#storeidtext').val("");
	$('#storenametext').val("");
	/*$('#oprntext').val("INS");*/
	$('#addresstext').val("");
	$('#addresstext').val("");
	$('#statetext').val("");
	
	$('#countrytext').val("");
	$('#citytext').val("");
	$('#zipcodetext').val("");
	$('#phonetext').val("");
	/*$('#currencycodeselect').val($("#currencycodeselect option:first").val());*/
	$('#emailtext').val('');
	$('#statusidtext').val("0");
	
	
	
	$('#oprntext').val("INS");
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
		$(this).parents('tr').addClass("selected").siblings().removeClass("selected"); 
		var table = $('#storeDT').DataTable();
		var storeObject = [];
		storeObject.push(table.row( $(this).parents('tr') ).data());
		/*categoryObject.push(table.row(this).data());*/
		
		var object = storeObject[0];
		console.log(table.row( $(this).parents('tr') ).data());
		//window.location.href="addstore?storeid="+object['storeid'];
		//$('#myModal').modal('show');
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
		
		var taxObject = object['storeTaxList'];
		console.log(taxObject);
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
$('.btn.btn-white').on('click',function()
{

	var isValid = true;
	var oprn = $('#oprntext').val();

	$("#modal-delet").modal('hide');
	//window.location.href="store";

		
		storeObject.oprn="DEL";
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
			console.log(responseType)
			if(responseType == 'S')
			{
			displaySuccessMsg(data);
			clearStoreFields();
			var str = window.location.pathname;
			var res = str.split("/");
			if(res[3] == "heroadminsettings"){	
				
				/*window.location.href="currency";*/
			}
			else{
				setTimeout("location.href = 'store'",2000);
				/*setTimeout("location.href = 'redirect_currency.html'",60000000000000000000);*/
			}
			/*window.location.href="store"*/
			}
			else if(responseType == 'F')
				{
				if(storeObject.oprn == 'DEL')
					{
				displayFailureMsg("",responseMsg(responseValue));
				setTimeout("location.href = 'store'",2000);
					}
				else
					{
					displayFailureMsg("",responseMsg(responseValue));
				return false;
					}
				//setTimeout("location.href = 'store'",2000);
				}
			}
		}		
		);
		
	});
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
			/*prepareCustomergroupDataTable(customergroupObj,oprn);*/
			/*window.location.href="customergroup";*/
			
			if(oprn == 'UPD')
				{
				/*location.reload(false);*/
				}
			}
		else if(responseType == 'F')
			{
			alert(responseValue);
			/*window.location.href="customergroup";*/
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
		console.log(object);
		var object = customergroupObject[0];
		console.log(object['3']);
		window.location.href="addcustomergroup?customergroupid="+object['3'];
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
	/*location.reload(true);*/
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
var reportuserroleArray = [];
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
		var isreportmenu = parseInt(menudetailsArray[5]);
		if(parseInt(ismenuSelected) == 1)
			{
			   $('#menuid'+index).attr('checked',true)	
			}
		else
			{
			   $('#menuid'+index).attr('checked',false)	
			}
		if(parseInt(isreportmenu) == 1){
			if(parseInt(ismenuSelected) == 1){

				$("#reportsMenuDiv").css({'display' : 'block'});
			}else{
				$("#reportsMenuDiv").css({'display' : 'none'});
			}
		}
		var usermenuObj = new Object();
		usermenuObj.usertype = $('#usertypeselect').val();
		usermenuObj.userid = "0";
		usermenuObj.moduleid = moduleid;
		usermenuObj.createdby = $('#inventoryuseridtext').val();
		usermenuObj.menuaccess = ismenuSelected;
		usermenuObj.usermenusno = usermenusno;
		
		userroleArray.push(usermenuObj);
		console.log(usermenuObj);
	}
for(var index = 0;index < $('#userreportrolecount').val(); index++)	
{
	var menudetails = $('#reportmenudetailscheck'+index).val();
	var menudetailsArray = menudetails.split("$$$");
	var ismenuSelected = parseInt(menudetailsArray[0]);
	var issubmodule = parseInt(menudetailsArray[1]);
	var parentid = parseInt(menudetailsArray[2]);
	var moduleid = parseInt(menudetailsArray[3]);
	var usermenusno = parseInt(menudetailsArray[4]);
	
	if(parseInt(ismenuSelected) == 1)
		{
		   $('#reportmenuid'+index).attr('checked',true)	
		}
	else
		{
		   $('#reportmenuid'+index).attr('checked',false)	
		}
	
	
	var usermenuObj = new Object();
	usermenuObj.usertype = $('#usertypeselect').val();
	usermenuObj.userid = "0";
	usermenuObj.moduleid = moduleid;
	usermenuObj.createdby = $('#inventoryuseridtext').val();
	usermenuObj.menuaccess = ismenuSelected;
	usermenuObj.usermenusno = usermenusno;
	
	reportuserroleArray.push(usermenuObj);
	//console.log(usermenuObj);
}

}

function getmenuid(checkedid,menutype,parentid,moduleid,p_c_id,t,reportmenuid)
{
	if (t.is(':checked')) {
	     var rel = p_c_id.slice(0,-1);
	     var lastChar = p_c_id[p_c_id.length -1];
	     
	     if(rel == "child"){
	    	 $('.parent'+lastChar).prop('checked',true);
	     }
	}   
	if(reportmenuid == "1"){
		if( t.is(':checked')){
			$("#reportsMenuDiv").css({'display' : 'block' });
		}
		else{
			$("#reportsMenuDiv").css({'display' : 'none' });
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


function getreportmenuid(checkedid,menutype,parentid,moduleid,p_c_id,t)
{
	if (t.is(':checked')) {
	     var rel = p_c_id.slice(0,-1);
	     var lastChar = p_c_id[p_c_id.length -1];
	     
	     if(rel == "child"){
	    	 $('.parent'+lastChar).prop('checked',true);
	     }
	}   
	
var buildmenudetails = "1$$$"+menutype+"$$$"+parentid;

for(var index = 0;index < $('#userreportrolecount').val(); index++)	
{
	console.log( $('#reportmenudetailscheck'+index).val());
	var menudetails = $('#reportmenudetailscheck'+index).val();
	var menudetailsArray = menudetails.split("$$$");
	var ismenuSelected = parseInt(menudetailsArray[0]);
	var menudetailsissubmodule = parseInt(menudetailsArray[1]);
	var menudetailsparentid = parseInt(menudetailsArray[2]);
	var menudetailsmoduleid = parseInt(menudetailsArray[3]);
	
	checkreportmenus(checkedid,menutype,moduleid,menudetailsparentid,index);
}
}



function checkmenus(checkedid,menutype,moduleid,menudetailsparentid,index)
{
	if(menutype == 0)
	{
	var menuselected = "0";
	if(moduleid == menudetailsparentid)
	{
		
		if($("#menuid"+checkedid).is(':checked'))
			{
			$('#menuid'+index).prop('checked',true);
			menuselected = "1";
			console.log('#menuid'+index+'    selected');
			}
		else
			{
			$('#menuid'+index).prop('checked',false);
			menuselected = "0";
			console.log('#menuid'+index+'    not selected');
			}
		 
	}	
	}	
}
function checkreportmenus(checkedid,menutype,moduleid,menudetailsparentid,index)
{
	if(menutype == 0)
	{
	var menuselected = "0";
	if(moduleid == menudetailsparentid)
	{
		
		if($("#reportmenuid"+checkedid).is(':checked'))
			{
			$('#reportmenuid'+index).prop('checked',true);
			menuselected = "1";
			console.log('#reportmenuid'+index+'    selected');
			}
		else
			{
			$('#reportmenuid'+index).prop('checked',false);
			menuselected = "0";
			console.log('#reportmenuid'+index+'    not selected');
			}
		 
	}	
	}	
}
function getusertypemenus(value)
{
	var applntype = $("#applnidli").val();
	applntype = parseInt(applntype) + 1;
	$.ajax({
		type:'GET',
		contentType:'application/json',
		url:'getuserrolemenus/'+value+'/'+applntype,
		success:function(data,textstatus,jqXHR)
		{
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			
			var responseusermenuobj = responseValue['usermenuList'];
			var responseuserreportmenuobj = responseValue['userreportmenuList'];
			userroleArray = [];
			reportuserroleArray = [];
			console.log(responseValue);
			console.log(responseusermenuobj)
			for(var index = 0;index < responseusermenuobj.length - 1; index++)	
			{
				
				var usermenuObj = (responseusermenuobj[index]);
				console.log(usermenuObj)
				$('#menudetailscheck'+index).val(usermenuObj.menudetails);
				console.log(usermenuObj.menudetails)
				var menudetails = $('#menudetailscheck'+index).val();
				var menudetailsArray = menudetails.split("$$$");
				var ismenuSelected = parseInt(menudetailsArray[0]);
				var issubmodule = parseInt(menudetailsArray[1]);
				var parentid = parseInt(menudetailsArray[2]);
				var moduleid = parseInt(menudetailsArray[3]);
				var usermenusno = parseInt(menudetailsArray[4]);
				var isreportmenu = parseInt(menudetailsArray[5]);
				
				if(parseInt(ismenuSelected) == 0)
					{
					$('#menuid'+index).prop('checked',false);
					}
				else
					{
					$('#menuid'+index).prop('checked',true);
					}
				
				if(parseInt(isreportmenu) == 1){
					if(parseInt(ismenuSelected) == 1){
						$("#reportsMenuDiv").css({'display' : 'block' });
					}else{
						$("#reportsMenuDiv").css({'display' : 'none' });
					}
				}
				
				userroleArray.push(usermenuObj);
			}
			for(var index = 0;index < responseuserreportmenuobj.length; index++)	
			{
				var userreportmenuObj = (responseuserreportmenuobj[index]);
				$('#reportmenudetailscheck'+index).val(userreportmenuObj.menudetails);
		
				var menudetails = $('#reportmenudetailscheck'+index).val();
				var menudetailsArray = menudetails.split("$$$");
				var ismenuSelected = parseInt(menudetailsArray[0]);
				var issubmodule = parseInt(menudetailsArray[1]);
				var parentid = parseInt(menudetailsArray[2]);
				var moduleid = parseInt(menudetailsArray[3]);
				var usermenusno = parseInt(menudetailsArray[4]);
				
				if(parseInt(ismenuSelected) == 0)
					{
					$('#reportmenuid'+index).prop('checked',false);
					}
				else
					{
					$('#reportmenuid'+index).prop('checked',true);
					}
				reportuserroleArray.push(userreportmenuObj);
			}
			
			
		}
	});
	$(".token-subjects").removeClass("border-active");
	$(".border"+value).addClass("border-active");
	$("#usertypeselect").val(value);
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
	usermenuObj.createdby = $('#herouseridtextforuserrole').val();
	usermenuObj.menuaccess = selected;
	usermenuObj.usermenusno = usermenusno;
	//console.log(usermenuObj);
	userroleArray.push(usermenuObj);
	}
	
	
	reportuserroleArray = [];
	for(var index = 0;index < $('#userreportrolecount').val(); index++)	
	{
	var reportremoveItem;
	var reportselected = 0;
	/*var updatemenuObj = (userroleArray[index]);*/
	
	console.log(index);
	var reportmenuselect = $("#reportmenuid"+index).is(':checked');
	
		if(reportmenuselect)
		{
			reportselected = "1";
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
	 
	
	var menudetails = $('#reportmenudetailscheck'+index).val();
	var menudetailsArray = menudetails.split("$$$");
	var ismenuSelected = parseInt(menudetailsArray[0]);
	var issubmodule = parseInt(menudetailsArray[1]);
	var parentid = parseInt(menudetailsArray[2]);
	var moduleid = parseInt(menudetailsArray[3]);
	var usermenusno = parseInt(menudetailsArray[4]);
	
	var userreportmenuObj = new Object();
	
	userreportmenuObj.usertype = $('#usertypeselect').val();
	userreportmenuObj.userid = "0";
	userreportmenuObj.moduleid = moduleid;
	userreportmenuObj.createdby = $('#herouseridtextforuserrole').val();
	userreportmenuObj.menuaccess = reportselected;
	userreportmenuObj.usermenusno = usermenusno;
	//console.log(usermenuObj);
	reportuserroleArray.push(userreportmenuObj);
	}
	
	console.log($('#usertypeselect').val());
	
	console.log(userroleArray)
	
	
	$("#modalLoading").modal('show');
	
	
	
	$.ajax({
	type:'POST',
	url:'saveuserrole/'+$('#usertypeselect').val(),
	contentType:'application/json',
	data:JSON.stringify(userroleArray),
	success:function(data,textStatus,jqXHR)
	{
		
		var responseType = getResponseType(data);
		var responseValue = getResponseValue(data);
		if(responseType == 'S')
		{
			$.ajax({
				type:'POST',
				url:'savereportuserrole/'+$('#usertypeselect').val(),
				contentType:'application/json',
				data:JSON.stringify(reportuserroleArray),
				success:function(res,textStatus,jqXHR)
				{
					displaySuccessMsg(data);
					
					var str = window.location.pathname;
					var res = str.split("/");
					if(res[3] == "heroadminsettings"){	
						$("#modalLoading").modal("hide");
						$.get(url, function(data) {
							$("#herosettingsdiv").html(data);
							$(".datepicker").datepicker(datePickerOptions);	
						});	
					}
					
					else{
						setTimeout("location.href = 'userrole?applnid=1&usertypeid=2'",200);
						setTimeout("location.href = 'redirect_currency.html'",60000000000000000000);
					}
				}
			});

		}
	else if(responseType == 'F')
		{
		displayFailureMsg("",responseMsg(responseValue));
		}
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
	
	changeusertypedept();
}
function loadaddnewuserConfig(userid)
{
/*	alert(userid)*/
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
	
	changeusertypedept();
	
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
		    	ordering:false,
		        columns: [
		            { data: 'username' },
		            { data: 'email' },
		            { data: 'phoneno' },
		            { data: 'storename' },
		            { data: 'usertypedesc' },
		            { data: 'status' }
		           /* { data: 'action' }*/
		        ]
	            
		    } );
			
			/*selectUserItem();*/
		}
	});	
}
function changeusertypedept()
{
	var userrole = $("#usertypeselect").val();
	
		$.ajax({
		type:'GET',
		url:'getuserlocationlist/'+userrole,
		contentType:'application/json',
		success:function(data,textStatus,jqXHR)
		{
			var responseValue = getResponseValue(data);
			console.log(responseValue);
			
			var html = '';
			$(responseValue).each(function(key,value){
				html += '<option value="'+value.value+'" data-year="'+value.starting_year+'">\
			            '+value.label+'\
			        </option>';                   
			});
			
			var applntype = "";
			
			$(finalarray).each(function(key, value) {
				if(value.usertype == userrole){
					applntype = value.applntype;
				}
			  });
			console.log(applntype)
			if(applntype == 2){
				  $('#labSelectDiv').css({'display':'none'});
				  $('#storeSelectDiv').css({'display':'none'});
				  $("#clinicselect").html(html);
				  $('#clinicSelectDiv').css({'display':'block'});
				  $('#currencySelectDiv').show();
				  $("#storeselect").val("0");
			}	
			else if(applntype == 3){
				
				 $('#currencySelectDiv').hide();
				  $('#clinicSelectDiv').css({'display':'none'});
				 
				  $('#storeSelectDiv').css({'display':'none'});
				  $('#labSelectDiv').css({'display':'block'});
				  $("#labselect").html(html);
			}else{
				 $('#currencySelectDiv').hide();
				  $('#clinicSelectDiv').css({'display':'none'});
				  $("#storeselect").html(html);
				  $('#storeSelectDiv').css({'display':'block'});
				  $('#labSelectDiv').css({'display':'none'});
			}
		}
	});

	
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
              //console.log(responseValue)
			var userobj = responseValue[0];
			
			$('#usernametext').val(userobj.username);
			$('#firstnametext').val(userobj.firstname);
			$('#lastnametext').val(userobj.lastname);
			$('#usertypeselect').val(userobj.usertype);
			$('#passwordtext').val(userobj.password);
			$('#retypepasswordtext').val(userobj.password);
			$('#emailidtext').val(userobj.email);
			$('#storeselect').val(userobj.storeid);
			$('#clinicselect').val(userobj.clinic);
			$('#phonenotext').val(userobj.phoneno);
			
			$('#addresstext').val(userobj.address);
			$('#citytext').val(userobj.city);
			$('#statetext').val(userobj.state);
			$('#countrytext').val(userobj.country);
			$('#pincodetext').val(userobj.pincode);
			
			
			if(userobj.status == 1)
				{
				$('#myonoffswitch1').attr('checked',true);	
				}
			else
				{
				$('#myonoffswitch1').attr('checked',false);
				}
			
			$('#ehrentryid').val(userobj.ehrentryid);
			var exsistingqualList = userobj.qualification;
			console.log(exsistingqualList);
			if(exsistingqualList !=""){
				var txqualarr = exsistingqualList.split(',');
				console.log(txqualarr)
				$(txqualarr).each(function(k,v){
					var obj = new Object();
					obj.value = v;
					obj.index = k;
					txnqualformatArr.push(obj);
					writequalitxnformat();
				});
			}
			/*$('#qualificationtext').val(userobj.qualification);*/
			$('#mothertoungetext').val(userobj.mothertounge);
			$('#dobtext').val(userobj.dob);
			$('#mothertoungetext').val(userobj.mothertongue);
			
			var exsistingDiagList = userobj.knownlanguage;
			console.log(exsistingDiagList);
			if(exsistingDiagList !=""){
				var txarr = exsistingDiagList.split(',');
				console.log(txarr)
				$(txarr).each(function(k,v){
					var obj = new Object();
					obj.value = v;
					obj.index = k;
					txnformatArr.push(obj);
					writetxnformat();
				});
			}
			//$('#languagesel').val(userobj.knownlanguage);
			
			$('#emergencycontactnumbertext').val(userobj.emergencycontactno);
			$('#relationtext').val(userobj.relation);
			$('#intercomnotext').val(userobj.intercomno);
			$('#dojtext').val(userobj.doj);
			$('#displayingnametext').val(userobj.displayingname);
			
			/*$('#yearsofexperiencetext').val(userobj.yearsofexperience);*/
			$('#yearstextinyoe').val(userobj.yearsyearsofexp);
			$('#monthstextinyoe').val(userobj.monthsyearsofexp);
			/*$('#aboutmetextinyoe').val(userobj.aboutmeyearsofexp);*/
			console.log(userobj.gender);
			$("input[name=gendertext][value=" + userobj.gender + "]").prop('checked', true);
		}
	});
}

function loadaddnewtax()
{
	var taxid = getParameterByName('taxid');
	$('#taxidtext').val(taxid);
	
	if(parseInt(taxid) > 0)
		{
		$('#oprntext').val("UPD");
		gettaxdetail(taxid);
		}
	else
		{
		$('#oprntext').val("INS");
		}
}
function loadaddnewtax()
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

function gettaxdetail(taxid)
{
	$.ajax({
		type:'GET',
		url:'gettaxdetail/'+taxid,
		contentType:'application/json',
		success:function(data,textStatus,jqXHR)
		{
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			var taxobj = responseValue[0];
			$('#taxnametext').val(taxobj.taxname);
			$('#taxcodetext').val(taxobj.taxcode);
			$('#taxtypetext').val(taxobj.taxtype);
			$('#taxratetext').val(taxobj.taxrate);
		}
	});
}


function getcurrencydetail(currencyid)
{
	$.ajax({
		type:'GET',
		url:'getcurrencydetail/'+currencyid,
		contentType:'application/json',
		success:function(data,textStatus,jqXHR)
		{
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			var currencyobj = responseValue[0];
			$('#currencynametext').val(currencyobj.currencyname);
			$('#currencycodeselect').val(currencyobj.currencycode);
			$('#currencysysmboltext').val(currencyobj.currencysymbol);
			$('#exchngratetext').val(currencyobj.currencyexchangerate);
			$('#convratetext').val(currencyobj.currencyconversionrate);
			$('#currdecimaltext').val(currencyobj.currencydecimal);
		}
	});
}
function loadaddnewcompany()
{
	var companyid = getParameterByName('companyid');
	$('#companyidtext').val(companyid);
	
	if(parseInt(companyid) > 0)
		{
		$('#oprntext').val("UPD");
		getcompanydetail(companyid);
		}
	else
		{
		$('#oprntext').val("INS");
		}
}

function getcompanydetail(companyid)
{
	$.ajax({
		type:'GET',
		url:'getcompanydetail/'+companyid,
		contentType:'application/json',
		success:function(data,textStatus,jqXHR)
		{
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			var companyobj = responseValue[0];
			$('#companynametext').val(companyobj.companyname);
			
		}
	});
}

function loadaddnewuom()
{
	var uomid = getParameterByName('uomid');
	$('#uomidtext').val(uomid);
	
	if(parseInt(uomid) > 0)
		{
		$('#oprntext').val("UPD");
		getuomdetail(uomid);
		}
	else
		{
		$('#oprntext').val("INS");
		}
}

function getuomdetail(uomid)
{
	$.ajax({
		type:'GET',
		url:'getuomdetail/'+uomid,
		contentType:'application/json',
		success:function(data,textStatus,jqXHR)
		{
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			var uomobj = responseValue[0];
			$('#uomnametext').val(uomobj.uomname);
			
		}
	});
}
function loadaddnewcustomergroup()
{
	var customergroupid = getParameterByName('customergroupid');
	$('#customergrouptext').val(customergroupid);
	
	if(parseInt(customergroupid) > 0)
		{
		$('#oprntext').val("UPD");
		getcustomergroupdetail(customergroupid);
		}
	else
		{
		$('#oprntext').val("INS");
		}
}

function getcustomergroupdetail(customergroupid)
{
	$.ajax({
		type:'GET',
		url:'getcustomergroupdetail/'+customergroupid,
		contentType:'application/json',
		success:function(data,textStatus,jqXHR)
		{
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			var customergroupobj = responseValue[0];
			$('#customergroupnametext').val(customergroupobj.customergroupname);
			
		}
	});
}
function loadaddnewcategory()
{
	var categoryid = getParameterByName('categoryid');
	$('#categoryidtext').val(categoryid);
	
	if(parseInt(categoryid) > 0)
		{
		$('#oprntext').val("UPD");
		getcategorydetail(categoryid);
		}
	else
		{
		$('#oprntext').val("INS");
		}
}

function getcategorydetail(categoryid)
{
	$.ajax({
		type:'GET',
		url:'getcategorydetail/'+categoryid,
		contentType:'application/json',
		success:function(data,textStatus,jqXHR)
		{
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			var categoryobj = responseValue[0];
			$('#categorytext').val(categoryobj.categoryname);
			
		}
	});
}
function loadaddnewbank()
{
	var bankid = getParameterByName('bankid');
	$('#bankidtext').val(bankid);
	
	if(parseInt(bankid) > 0)
		{
		$('#oprntext').val("UPD");
		getbankdetail(bankid);
		}
	else
		{
		$('#oprntext').val("INS");
		}
}

function getbankdetail(bankid)
{
	$.ajax({
		type:'GET',
		url:'getbankdetail/'+bankid,
		contentType:'application/json',
		success:function(data,textStatus,jqXHR)
		{
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			var bankobj = responseValue[0];
			console.log(bankobj);
			
			$('#banktext').val(bankobj.bankname);
			
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
		$(this).parents('tr').addClass("selected").siblings().removeClass("selected"); 
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
	var isValidArray = [];
	
	isValidArray.push(validateonbuttonclick('#firstnametext','input'));
	isValidArray.push(validateonbuttonclick('#lastnametext','input'));
	isValidArray.push(validateonbuttonclick('#usernametext','input'));
	isValidArray.push(validateonbuttonclick('#passwordtext','input'));
	isValidArray.push(validateonbuttonclick('#retypepasswordtext','input'));
	
	isValidArray.push(validateonbuttonclick('#ehrentryid','input'));
	
	isValidArray.push(validateonbuttonclick('#qualificationsel','input'));
	isValidArray.push(validateonbuttonclick('#dobtext','input'));
	isValidArray.push(validateonbuttonclick('#displayingnametext','input'));
	
	isValidArray.push(validateonbuttonclick('#phonenotext','input'));
	isValidArray.push(validateonbuttonclick('#emailidtext','input'));
	
	isValidArray.push(validateonbuttonclick('#languagesel','input'));

	isValidArray.push(validateonbuttonclick('#dojtext','input'));
	
	isValidArray.push(validateonbuttonclick('#yearstextinyoe','input'));
	isValidArray.push(validateonbuttonclick('#monthstextinyoe','input'));

	var totalValid = jQuery.inArray( false, isValidArray );

	if(totalValid < 0)
		{

	var oprn = $('#oprntext').val();
	
	var userpassword = $('#passwordtext').val();
	var retypepassword = $('#retypepasswordtext').val();
	var diagStr = "";
	var qualStr = "";

	
	
	var specialCharacterCount = specialcharactercheck(userpassword);
	if(oprn == 'INS')
		{
		if($('#myonoffswitch1').prop('checked') == false)
			{
			displayAlertMsg("Cant create new user in Inactive Status");
			return false
			}
		}
	if(specialCharacterCount == 0)
		{
		displayAlertMsg("Password must contain Special Characters");
		return false;
		}
	if(userpassword != retypepassword)
	{
		displayAlertMsg("Your password and retype password incorrect");
		return false;
	}
	
	var doj = $("#dojtext").val();
	if(doj != ""){
		var res = doj.split("/");
		var dojyear = res[2];
		var clinicstartingyear = $("#clinicselect").find(':selected').data('year');
		if(parseInt(dojyear)<parseInt(clinicstartingyear)){
			displayAlertMsg("Kindly Check your Date of Joining");
			return false;
		}else{
			
			var dob = $("#dobtext").val();
			var resdob = dob.split("/");
			var dobyear = resdob[2];
			if(parseInt(dobyear)>parseInt(dojyear)){
				displayAlertMsg("Kindly Check your Date of Joining");
				return false;
			}
		}
	}
	
	var monthsValidation = $("#monthstextinyoe").val();
	
	if(monthsValidation > 11){
		displayAlertMsg("Experience Month is Incorrect");
		return false;
	}
	console.log(txnformatArr);
	$(txnformatArr).each(function(k,v){
		diagStr += v.value+",";
	});
	console.log(diagStr.length);
	var finalString = diagStr.substring(0, diagStr.length - 1);
	
	
	$(txnqualformatArr).each(function(k,v){
		qualStr += v.value+",";
	});
	
	var finalqualString = qualStr.substring(0, qualStr.length - 1);
	
	var userobj = new Object();
	userobj.firstname = $('#firstnametext').val();
	userobj.lastname = $('#lastnametext').val();
	
	userobj.usertype = $('#usertypeselect').val();
	
	userobj.address = $('#addresstext').val();
	userobj.city = $('#citytext').val();
	userobj.state = $('#statetext').val();
	userobj.country = $('#countrytext').val();
	userobj.pincode = $('#pincodetext').val();
	
	
	userobj.password = userpassword;
	userobj.email = $('#emailidtext').val();
	userobj.storeid = $('#storeselect').val();
	userobj.phoneno = $('#phonenotext').val();
	if((userobj.phoneno).length >10){
		heroNotifications.showFailure("Invalid Mobileno");
		return false;
	}
	userobj.username = $('#usernametext').val();
	userobj.createdby = $('#herouseridtext').val();
	userobj.userid = $('#usernameidtext').val();
	userobj.clinic = $('#clinicselect').val();
	userobj.lab = $('#labselect').val();
	
	
	userobj.ehrentryid = $('#ehrentryid').val();
	userobj.qualification = finalqualString;
	if(userobj.qualification == ""){
		heroNotifications.showFailure("Please fill the Qualification fields");
		return false;
	}
	userobj.dob = $('#dobtext').val();
	userobj.displayingname = $('#displayingnametext').val();
	userobj.knownlanguages = finalString;
	if(userobj.knownlanguages == ""){
		heroNotifications.showFailure("Please fill the Language fields");
		return false;
	}
	userobj.emergencycontactnumber = $('#emergencycontactnumbertext').val();
	userobj.relation = $('#relationtext').val();

	userobj.doj = $('#dojtext').val();
	

	userobj.yearsinyoe = $('#yearstextinyoe').val();
	userobj.monthsinyoe = $('#monthstextinyoe').val();

	userobj.gender = $('input[name=gendertext]:checked').val();
	console.log(userobj)
	
	if($('#myonoffswitch1').prop('checked') == true)
		{
		userobj.status = 1;	
		}
	else
		{
		userobj.status = 0;
		}
	
	userobj.oprn = $('#oprntext').val();
	console.log(userobj)
	
	var regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
    
    if(regex.test(userobj.email)){
	
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
				displaySuccessMsg(data);		
				 clearuserFormValues();
				 var str = window.location.pathname;
					var res = str.split("/");
					if(res[3] == "heroadminsettings"){	
						
						$.get(url, function(data) {
							$("#herosettingsdiv").html(data);
							$(".datepicker").datepicker(datePickerOptions);	
						});	


					}
					else{
						//alert("saved");
						
						setTimeout("location.href = 'user'",2000);
						setTimeout("location.href = 'redirect_currency.html'",60000000000000000000);
					}
				}
			
					else if(responseType == 'F')
						{
						displayFailureMsg("",responseMsg(responseValue));
						//clearuserFormValues();
						$.get(url, function(data) {
							$("#herosettingsdiv").html(data);
							$(".datepicker").datepicker(datePickerOptions);	
						});	
				
						}
					}
				});
	
		    }else{
		        heroNotifications.showFailure("Your Email-id is In correct");
		        return false;
		}
	
		}else{
			heroNotifications.showFailure("Please fill the Mandatory fields");
			return false;
		}
				}

			
			
			

function clearuserFormValues()
{
	$('#firstnametext').val("");
	$('#lastnametext').val("");
	$('#oprntext').val("INS");
	$('#usertypeselect').val("");
	$('#emailidtext').val("");
	$('#storeselect').val("");
	
	$('#phonenotext').val("");
	$('#usernametext').val("");
	$('#dobtext').val("");
	$('#ehrentryid').val("");
	$('#displayingnametext').val("");
	$('#qualificationtext').val("");
	$('#gendertext').val("");
	$('#emergencycontactnumbertext').val("");
	$('#yearstextinyoe').val("");
	$('#monthstextinyoe').val("");
	$('#knownlanguagestext').val("");
	
	$('#addresstext').val("");
	$('#citytext').val("");
	$('#statetext').val("");
	$('#countrytext').val("");
	$('#pincodetext').val("");
	$('#herouseridtext').val("");

	
	$('#relationtext').val("");
	$('#dojtext').val("");
	$('#inventoryuseridtext').val("");
	$('#usernameidtext').val("");
	/*$('#currencycodeselect').val($("#currencycodeselect option:first").val());*/
	$('#passwordtext').val("");
	$('#retypepasswordtext').val("");
	/*$('#passwordtext').val("0");*/
	
	
	
	$('#oprntext').val("INS");
}

function addlanguage()
{
	var obj = new Object();
	obj.value = $('#languagesel').val();
	obj.index = parseInt(txnformatArr.length)+1;
	
	var selectedTxnCode = $('#languagesel').val();
	
	var exists = 0;
	var found = $.map(txnformatArr, function(val) {
	    if(val.value == selectedTxnCode )
	    	{
	    	exists = 1;
	    	}
	});
	
	if(exists == 0)
		{
		
		txnformatArr.push(obj);

		writetxnformat();
		//formattxnno();
		}
	else
		{
		displayFailureMsg("","Selected Language Already Exists");
		return false;
		}	
	console.log(txnformatArr)
}
function addqualification()
{
	var obj = new Object();
	obj.value = $('#qualificationsel').val();
	obj.index = parseInt(txnqualformatArr.length)+1;
	
	var selectedTxnCode = $('#qualificationsel').val();
	
	var exists = 0;
	var found = $.map(txnqualformatArr, function(val) {
	    if(val.value == selectedTxnCode )
	    	{
	    	exists = 1;
	    	}
	});
	
	if(exists == 0)
		{
		
		txnqualformatArr.push(obj);

		writequalitxnformat();
		//formattxnno();
		}
	else
		{
		displayFailureMsg("","Selected Qualification Already Exists");
		return false;
		}	
	console.log(txnqualformatArr)
}

function writetxnformat()
{
	var txnFormatHTML = "";
	$('#txnformatdispid').empty();
	$.each(txnformatArr, function (index, value) {
		var obj = txnformatArr[index];
		console.log(obj)
		var randomcolor =  Math.floor(100000 + Math.random() * 900000);
		txnFormatHTML += "<span class='label label-gray' style='background:#"+randomcolor+";' id='txnspanid"+index+"' onclick='removetxncode("+index+")'>"+obj.value+"&nbsp;<i class='fa fa-close' style='cursor: pointer;'></i></span>&nbsp;"
		$('#txnformatdispid').html(txnFormatHTML);
	});	
}
function writequalitxnformat()
{
	var txnqualFormatHTML = "";
	$('#txnformatqualdispid').empty();
	$.each(txnqualformatArr, function (index, value) {
		var obj = txnqualformatArr[index];
		console.log(obj)
		var randomcolor =  Math.floor(100000 + Math.random() * 900000);
		txnqualFormatHTML += "<span class='label label-gray' style='background:#"+randomcolor+";' id='txnspanid"+index+"' onclick='removequalitxncode("+index+")'>"+obj.value+"&nbsp;<i class='fa fa-close' style='cursor: pointer;'></i></span>&nbsp;"
		$('#txnformatqualdispid').html(txnqualFormatHTML);
	});	
}
function removequalitxncode(removeItemIndex)
{
	var removeItem = txnqualformatArr[removeItemIndex];
	
/*txnformatArr.splice( $.inArray(removeItem,txnformatArr) ,1 );*/
 
	/*txnformatArr = $.grep(txnformatArr, function(e){ 
	     return e.key != removeItem.key; 
	});
	console.log(txnformatArr);*/
	
	for(var i = 0; i < txnqualformatArr.length; i++) {
	    if(txnqualformatArr[i].value == removeItem.value) {
	    	txnqualformatArr.splice(i, 1);
	        break;
	    }
	}

var txnqualFormatHTML = "";
$('#txnformatqualdispid').empty();
$.each(txnqualformatArr, function (index, value) {
	var obj = txnqualformatArr[index];
	var randomcolor =  Math.floor(100000 + Math.random() * 900000);
	txnqualFormatHTML += "<span class='label label-gray' style='background:#"+randomcolor+";' id='txnspanid"+index+"' onclick='removequalitxncode("+index+")'>"+obj.value+"&nbsp;<i class='fa fa-close' style='cursor: pointer;'></i></span>&nbsp;"
	$('#txnformatqualdispid').html(txnqualFormatHTML);
});

}
function removetxncode(removeItemIndex)
{
	var removeItem = txnformatArr[removeItemIndex];
	
/*txnformatArr.splice( $.inArray(removeItem,txnformatArr) ,1 );*/
 
	/*txnformatArr = $.grep(txnformatArr, function(e){ 
	     return e.key != removeItem.key; 
	});
	console.log(txnformatArr);*/
	
	for(var i = 0; i < txnformatArr.length; i++) {
	    if(txnformatArr[i].value == removeItem.value) {
	    	txnformatArr.splice(i, 1);
	        break;
	    }
	}

var txnFormatHTML = "";
$('#txnformatdispid').empty();
$.each(txnformatArr, function (index, value) {
	var obj = txnformatArr[index];
	var randomcolor =  Math.floor(100000 + Math.random() * 900000);
	txnFormatHTML += "<span class='label label-gray' style='background:#"+randomcolor+";' id='txnspanid"+index+"' onclick='removetxncode("+index+")'>"+obj.value+"&nbsp;<i class='fa fa-close' style='cursor: pointer;'></i></span>&nbsp;"
	$('#txnformatdispid').html(txnFormatHTML);
});

}

function enablecurrencysymbollist(){
	$('#currencysysmbolsuggestiontext').val("");
	var e = $.Event('keydown');
    e.which = 65; // Character 'A'
    $('#currencysysmbolsuggestiontext').trigger(e);
}

/*New User Master Creation End*/


function gotoPermission(appid, usertypeid){
	window.location.href = "userrole?applnid="+appid+"&usertypeid="+usertypeid;
}