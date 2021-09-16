<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
<title>Upload File Request Page</title>
</head>
<body>

	<form method="POST" action="uploadFile" enctype="multipart/form-data">
		<div class="logoUploadDis">
			Select File to Upload
			<input type="file" name="file" size="60" class="file_style">
		</div>
		
		<input type="hidden" name="name" value="<%=request.getParameter("name") %>"> 
		<input type="hidden" name="applntype" value="<%=request.getParameter("applntype") %>">
		<input type="hidden" name="requesttype" value="<%=request.getParameter("requesttype") %>">
		<input type="submit" value="Upload" class="btn"> 
	</form>
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
		    background-color: #337ab7;
		    border-color: #2e6da4;
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

	</style>
</body>
</html>