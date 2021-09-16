<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>HERO System Settings</title>

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
</style>
<script src="../js/forms/admin/login/login.js"></script>
</head>
<body onload="loadherosettings('pharmacy');">
<jsp:include page="../template/header.jsp" />

<div class="breadcrumbs"  style="display: <%= request.getParameter("disp") %>">
            <div class="col-md-4 col-sm-4">
                <div class="page-header float-left">
                    <div class="page-title">
                    <div class="page-title">
                        <h1><a href="herohome">DashBoard</a></h1>
                    </div>
                       
                    </div>
                </div>
            </div>
            <div class="col-md-8 col-sm-8">
                <div class="page-header float-right">
                    <div class="page-title">
                        <ol class="breadcrumb text-right">
                            <li><a href="herosettings">Config System Settings</a></li>
                           </ol>
                    </div>
                </div>
            </div>
        </div>
        
        <div style="height: 440px;">
        <div id="herosettingsdiv">
        
        </div>
       
        </div>
        <div class="col-md-12 bg-gray">
        <input class="btn prev-button" type="button" value="Prev" onclick="configprev('pharmacy');" id="prevbtn">
	    <input class="btn next-button" type="button" value="Next" onclick="confignext('pharmacy');" id="nextbtn">
	     <input class="btn finish-button" type="button" value="Finish" onclick="configfinish('pharmacy');" id="finishbtn">
	   
        </div>
       
<jsp:include page="../template/footer.jsp" />


</body>
</html>