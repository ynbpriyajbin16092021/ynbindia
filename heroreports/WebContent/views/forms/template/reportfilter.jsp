<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
    
<script type="text/javascript" src="../js/forms/reports/inventoryreports.js"></script>    
<script type="text/javascript" src="../js/util/invutil.js"></script>
<body onload="loadproducts();"> 
<div class="filterSection displayTabel">
         <!--  <div class="col-md-2">
            <label class="filter"> Filter By : </label>
          </div> -->
          <div class="col-md-10">
          <div  class="col-md-6 margin-bottom-10" id="storeselectdiv">
         
         
        <%if(request.getParameter("rid") != null && !request.getParameter("rid").equals("1") && !request.getParameter("rid").equals("2")
          && !request.getParameter("rid").equals("4") && !request.getParameter("rid").equals("16")
          && !request.getParameter("rid").equals("5") && !request.getParameter("rid").equals("7"))
        	
        	  {  %>
        	   <label class="filter"> Store Name : </label>
          <select id='reportsstoreidselect' class="form-control form-white selectSer">
			<option value="0">--Select--</option>
    			<c:forEach items="${storeList}" var="store" >                  
        		<option value="${store.value}">${store.label}</option>                    
    			</c:forEach>
    			</select>
			<%}else{ %>
			 
              		<!-- <div class="col-md-12 form-group">
						<label for="Product">Search By product</label>
						<input type="text" class="form-control form-white" onblur="checkAvailableProduct()"
						 id="itemcodetext"  placeholder="Please select Product"></input>
                      	<input type="hidden" id="productcodetext" value="0"></input>
						</div> -->
			<%} %> 
			
		  
		  
          </div>
			<div class="col-md-6">
	          <div id="reportrange"  style="background: #fff; cursor: pointer; padding: 5px 10px; border: 1px solid #ccc;margin-top:27px">
			    <i class="glyphicon glyphicon-calendar fa fa-calendar"></i>&nbsp;
			    <span></span> <b class="caret"></b>
			 </div>
			</div>
         </div>
        </div>
        
