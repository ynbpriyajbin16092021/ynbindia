<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
<title>Upload File Request Page</title>
<script src="/heroadmin/js/lib/theme/jquery.3.3.1.min.js"></script>
</head>
<body>

	<form method="POST" action="uploadFile" enctype="multipart/form-data">
		<div class="logoUploadDis">
			Select File to Upload
			<input type="file" name="file" size="60" class="file_style" id="file" onchange="validateFileType()">
		</div>
		
		<input type="hidden" name="name" value="<%=request.getParameter("name") %>"> 
		<input type="hidden" id="applntype1" name="applntype" value="<%=request.getParameter("applntype") %>">
		<input type="hidden" id="requesttype1"  name="requesttype" value="<%=request.getParameter("requesttype") %>">
		<input type="submit" value="Upload" class="btn" id="submitbtn"> 
	</form>
	<script type="text/javascript">
	function validateFileType(){
		var applntype=$('#applntype1').val();
		var requesttype=$('#requesttype1').val();
		if(applntype == 0 && requesttype==1){
			var fileName = document.getElementById("file").value;
	        var idxDot = fileName.lastIndexOf(".") + 1;
	        var extFile = fileName.substr(idxDot, fileName.length).toLowerCase();
	        if (extFile=="jpg" || extFile=="jpeg" || extFile=="png"){
	        	$('#submitbtn').show()
	        }else{
	        	$('#submitbtn').hide()
	            alert("Only jpg/jpeg and png files are allowed!");
	        }   
		}
	}
	</script>
	<style type="text/css">
		.logoUploadDis {
		    width: 100%;
		    max-height: 90px;
		    background-color: #FBFBFB;
		    border: 1px dashed #DDD;
		    border-radius: 4px;
		    float: left;
		    position: relative;
		    text-align: center;
		    font-size:13px;
		    font-family: Calibri;
		}
		.file_style{
			float: left;
		    font-size: 13px;
		    font-family: calibri;
		    padding-top: 5px;
		    width: 100%;
		}
		.btn{
		    color: #fff;
		    background-color: #6259ca;
		    border-color: #6259ca;
	    	box-shadow: inset 0 3px 5px rgba(0,0,0,.125);
	        display: inline-block;
	    padding: 4px 8px;
	    margin: 10px 0;
	    margin-bottom: 0;
	    font-size: 11px;
	    font-weight: 400;
	    line-height: 1.42857143;
	    text-align: center;
        background-image: none;
	    border: 1px solid transparent;
	    border-radius: 4px;
			}
input[type="file"]:focus  {
    border:0;
	outline:0;
	background:transparent;
	box-shadow:none !important;
	border-radius:0px;
	font-size:12px;
	font-weight:normal;
}
	</style>
</body>
</html>