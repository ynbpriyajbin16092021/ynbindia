function loadforgotpw()
{
	$.ajax({
		type:'GET',
		contentType:'application/json',
		url:'forgotpasswordlist',
		success:function(data,textStatus,jqXHR)
		{
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			
			if(responseType == 'S')
				{
				var dataTableSet = $.parseJSON(responseValue);
				displayForgotpwDatalist(dataTableSet);
				}

		}
	});
}


function displayForgotpwDatalist(dataTableSet) {
	$('#forgotpwDT').DataTable({
		destroy : true,
		data : dataTableSet,
		"fnCreatedRow": function( nRow, aData, iDataIndex ) {
			/*$('td:eq(1)', nRow).append("<label style='cursor:pointer;'>Edit</label><img src='../resources/images/delete.jpg' width='30px;' height='30px' id='editbtn'></img>");*/
$('td:eq(4)', nRow).append('<button class="edit myBtnTab"> <i class="fa fa-eye"></i> </button><button class="delete myBtnTab"  data-toggle="modal" data-target="#myModal"> <i class="fa fa-refresh"></i> </button>');
		}
	});
	selectForgotpwItem();
}

function selectForgotpwItem() {
	
	$('#forgotpwDT tbody').on('click', '.delete.myBtnTab', function() {
		var table = $('#forgotpwDT').DataTable();
		var ForgotpwObject = [];
		ForgotpwObject.push(table.row( $(this).parents('tr') ).data());
		/*categoryObject.push(table.row(this).data());*/
		var object = ForgotpwObject[0];
		console.log(object)
		$.each(object, function(index, value) {
			 $("#forgetpwidtext").val(object[6]);
			
		});
		 
		
		
	});
	$('#forgotpwDT tbody').on('click', '.edit.myBtnTab', function() {
		$(this).parents('tr').addClass("selected").siblings().removeClass("selected"); 
		var table = $('#forgotpwDT').DataTable();
		var categoryObject = [];
		categoryObject.push(table.row( $(this).parents('tr') ).data());
		/*categoryObject.push(table.row(this).data());*/
		var object = categoryObject[0];
		console.log(object);
		$('#usernameidlabel').text(object[0]);
		$('#reqraisedatlabel').text(object[1]);
		$('#newpassword').text(object[2]);
		$('#reqraisedcountlabel').text(object[3]);
		//$('#myModal').modal('show');
		
	});
}

function resetpassword()
{
	$.ajax({
		type:'POST',
		contentType:'application/json',
		url:'resetpassword/'+$("#forgetpwidtext").val(),
		success:function(data,textStatus,jqXHR)
		{
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			console.log(data);
			/*if(responseType == 'S')
				{*/
				console.log(responseValue);
				window.location.reload()
				loadforgotpw();
				/*}*/

		}
	});
}

