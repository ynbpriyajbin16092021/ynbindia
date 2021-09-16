<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>HERO Home</title>
<jsp:include page="../admin/home/homelibrary.jsp" />
<script type="text/javascript">
function gototxnpage(tokenkeyvalidatorurl,applncontxt)
{
	var tokenkey = localStorage.getItem("tokenkey");
	window.open(tokenkeyvalidatorurl+tokenkey,'_blank');
}
</script>
<script src="../js/forms/admin/login/login.js"></script>
</head>
<body onload="loadherosettings('admin')">
<jsp:include page="../admin/home/homeheader.jsp" />


					<div class="clearfix"></div>
					<div class="breadcrumbs"  style="display: <%= request.getParameter("disp") %>">
			            <div class="col-md-4 ">
			                <div class="page-header">
			                        <p class="cursor-pointer mar-top-10 bread-a-style head-font-size yellow-font" href="stockdashboard"><strong><i class="fa fa-cog"></i> Admin Config Settings</strong></p>
			                </div>
			            </div>
			            <div class="col-md-8 col-sm-8">
			                <div class="page-header float-right">
			                    <a class="pull-right cursor-pointer bread-a-style content-font-size color-font" href="herohome?uid=1">Dashboard</a>
			                </div>
			            </div>
			        </div>
					<div class="clearfix"></div>
					
					<div class="col-md-12 overcome-col-padd-10  ">
					<div class="width_100 gray-font white-bg content-font-size full-padding-next-previous">
					        <input class="btn prev-button pad " type="button" value="Prev" onclick="configprev('admin');" id="prevbtn">
						    <input class="btn next-button pad pull-right" type="button" value="Next" onclick="confignext('admin');" id="nextbtn">
						    <input class="btn finish-button pad pull-right" type="button" value="Finish" onclick="configfinish('admin');" id="finishbtn">
					   	</div>
						<div class="col-md-12 overcome-col-padd-10 ">
							<div class="width_100">
						        <div id="herosettingsdiv">
						        
						        </div>
						    </div>
						</div>
						
					</div>
       <style>
        .height{
       height:60px;}
       .width{
       width:98%;
       margin-left: 10px;} 
       .pad{
       margin-left:20px;}
       .loader{
       
 margin-top:100px;
 margin-bottom:400px;
 margin-left:400px;
 margin-right:200px;
 width: 100%; height: 100%;
  background:white;
       }
       body{
       background:white;}
       </style>
      
<jsp:include page="../admin/home/homefooter.jsp" />