function signout()
{
	var tokenkey = localStorage.getItem("tokenkey");
	localStorage.removeItem("tokenkey");
	localStorage.removeItem("userid");
	window.location.href="signout/"+tokenkey;	
}

function tokenSignout(){
	var tokenkey = localStorage.getItem("tokenkey");
	//console.log('../../hero/forms/tokensignout/'+tokenkey);
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : '../../hero/forms/tokensignout/'+tokenkey,
		success : function(data, textStatus, jqXHR) {
			console.log(data);
			if(data.inventoryResponse.responseType == "S"){
				$.ajax({
					type : 'GET',
					contentType : 'application/json',
					url : '../../hero/forms/signout',
					success : function(data, textStatus, jqXHR) {
					}				
				});
				window.location.href="../../hero";
				localStorage.setItem("tokenkey","");
				localStorage.removeItem("userid");
			}else{
				displayFailureMsg("",responseMsg(data.inventoryResponse.responseObj));
			}
			
		}				
	});
}

function getlogo(img_src_id){
	var imgObject = new Object();
	imgObject.applntype = 0;
	imgObject.requesttype = 0;
	imgObject.imgname = "0000";
	
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : '../../hero/forms/heroviewimage',
		data : JSON.stringify(imgObject),
		success : function(data, textStatus, jqXHR) {
			var responseValue = data.inventoryResponse.responseObj;
			if(data.inventoryResponse.responseType == "S"){
				$('#'+img_src_id).attr('src', 'data:image/jpg;base64,'+responseValue);
			}
		}
	});
}

function getPatientImage(img_src_id , p_img_id){
	console.log("img   "+img_src_id+"  "+p_img_id);
	var imgObject = new Object();
	imgObject.applntype = 2;
	imgObject.requesttype = 1;
	imgObject.imgname = p_img_id;
	
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : '../../hero/forms/heroviewimage',
		data : JSON.stringify(imgObject),
		success : function(data, textStatus, jqXHR) {
			console.log(data)
			var responseValue = data.inventoryResponse.responseObj;
			if(data.inventoryResponse.responseType == "S"){
				$('#'+img_src_id).attr('src', 'data:image/jpg;base64,'+responseValue);
			}
		}
	});
}

function getDoctorImage(img_src_id, d_img_id){
	var imgObject = new Object();
	imgObject.applntype = 2;
	imgObject.requesttype = 2;
	imgObject.imgname = d_img_id;
	
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : '../../hero/forms/heroviewimage',
		data : JSON.stringify(imgObject),
		success : function(data, textStatus, jqXHR) {
			var responseValue = data.inventoryResponse.responseObj;
			if(data.inventoryResponse.responseType == "S"){
				$('#'+img_src_id).attr('src', 'data:image/jpg;base64,'+responseValue);
			}
		}
	});
}
