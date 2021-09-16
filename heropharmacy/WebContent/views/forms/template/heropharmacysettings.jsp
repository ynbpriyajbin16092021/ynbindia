<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>HERO System Settings</title>
<jsp:include page="../template/library.jsp" />
<style>
body {font-family: Arial;}

/* Style the tab */
.tab {
    overflow: hidden;
    border: 1px solid #ccc;
    background-color: #f1f1f1;
}

/* Style the buttons inside the tab */
.tab button {
    background-color: inherit;
    float: left;
    border: none;
    outline: none;
    cursor: pointer;
    padding: 14px 16px;
    transition: 0.3s;
    font-size: 17px;
}

/* Change background color of buttons on hover */
.tab button:hover {
    background-color: #ddd;
}

/* Create an active/current tablink class */
.tab button.active {
    background-color: #ccc;
}

/* Style the tab content */
.tabcontent {
    display: none;
    padding: 6px 12px;
    border: 1px solid #ccc;
    border-top: none;
}

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
       .bg-gray {
    background: #ffffff;
    padding: 15px;
}
</style>
<script src="../js/forms/login/login.js"></script>
</head>
<body onload="loadherosettings('pharmacy');">
<jsp:include page="../template/header.jsp" />


					<div class="clearfix"></div>
					<div class="breadcrumbs"  style="display: <%= request.getParameter("disp") %>">
			            <div class="col-md-4 ">
			                <div class="page-header">
			                        <p class="cursor-pointer mar-top-10 bread-a-style head-font-size yellow-font" href="stockdashboard"><strong><i class="fa fa-cog"></i> Pharmacy Config Settings</strong></p>
			                </div>
			            </div>
			            <div class="col-md-8 col-sm-8">
			                <div class="page-header float-right">
			                    <a class="pull-right cursor-pointer bread-a-style content-font-size color-font" href="dashboard">Dashboard</a>
			                </div>
			            </div>
			        </div>
					<div class="clearfix"></div>
						<div class="col-md-12 overcome-col-padd-10  ">
					<div class="width_100 gray-font white-bg content-font-size full-padding-next-previous">
        <input class="btn prev-button pad " type="button" value="Prev" onclick="configprev('pharmacy');" id="prevbtn">
	    <input class="btn next-button pad pull-right" type="button" value="Next" onclick="confignext('pharmacy');" id="nextbtn">
	     <input class="btn finish-button pad pull-right" type="button" value="Finish" onclick="configfinish('pharmacy');" id="finishbtn">
	   
        </div>
     
        <div style="height: 440px;">
        <div id="herosettingsdiv">
        
        </div>
       
        </div>
        </div>
       
<jsp:include page="../template/footer.jsp" />


</body>
</html>