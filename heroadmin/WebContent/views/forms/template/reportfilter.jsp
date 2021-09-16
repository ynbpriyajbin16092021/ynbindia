<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

    <!-- <script type="text/javascript" src="../js/daterangepicker/jquery.min.js"></script> -->
    <script type="text/javascript" src="../js/daterangepicker/moment.min.js"></script>
    <link rel="stylesheet" type="text/css" href="../resources/css/daterangepicker/bootstrap.css" />
    
    <script type="text/javascript" src="../js/daterangepicker/daterangepicker.js"></script>
    <link rel="stylesheet" type="text/css" href="../resources/css/daterangepicker/daterangepicker.css" />
    <script type="text/javascript" src="../js/util/invutil.js"></script>
</head>
<body>
<div class="filterSection displayTabel">
          <div class="col-sm-1">
            <label class="filter"> Filter By : </label>
          </div>
          <div  class="col-sm-3" id="storeselectdiv">
          
          <select id='reportsstoreidselect' class="form-control form-white selectSer">
          <%if(request.getParameter("rid") != null && !request.getParameter("rid").equals("1") && !request.getParameter("rid").equals("2")
          && !request.getParameter("rid").equals("4")
          && !request.getParameter("rid").equals("5") && !request.getParameter("rid").equals("7"))
        	  {%>
			<option value="0">--All--</option>
			<%} %>
    			<c:forEach items="${storeList}" var="store" >                  
        		<option value="${store.value}">${store.label}</option>                    
    			</c:forEach>
		  </select>
		  
          </div>
          <div id="reportrange"  class="col-sm-3" style="background: #fff; cursor: pointer; padding: 5px 10px; border: 1px solid #ccc;">
    <i class="glyphicon glyphicon-calendar fa fa-calendar"></i>&nbsp;
    <span></span> <b class="caret"></b>
</div>
         
        </div>
        
        <!-- <input type="button" onclick="generatereport();" value="Generate Report"> -->
        
</body>
</html>