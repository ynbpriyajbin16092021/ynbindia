function tokenSignout(){
		var tokenkey = localStorage.getItem("tokenkey");
		$.ajax({
			type : 'POST',
			contentType : 'application/json',
			url : 'tokensignout/'+tokenkey,
			success : function(data, textStatus, jqXHR) {
				console.log(data);
				if(data.inventoryResponse.responseType == "S"){
					window.location.href = "/heroadmin";
					 localStorage.setItem("tokenkey","");
				}else{
					alert("Error ocuured!!")
				}
				
			}				
		});
	}