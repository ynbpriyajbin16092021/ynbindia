 <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script type="text/javascript" src="../js/forms/transactions/stock.js"></script>
    <title>Process Store Request</title>
        <jsp:include page="../../template/library.jsp" />
</head>
<body onload="loadstorerequestItems()">
    
    
    <jsp:include page="../../template/header.jsp" />
    <form id="addpurchaseform">
    
 
 					<div class="clearfix"></div>
					<div class="breadcrumbs"  style="display: <%= request.getParameter("disp") %>">
			            <div class="col-md-4 col-sm-4">
			                <div class="page-header">
			                        <p class="cursor-pointer mar-top-10 bread-a-style head-font-size yellow-font" href="stockdashboard"><strong>Process Store Request</strong></p>
			                </div>
			            </div>
			            <div class="col-md-8 col-sm-8">
			                <div class="page-header float-right">
			                    <a class="pull-right cursor-pointer bread-a-style content-font-size color-font" href="dashboard?uid=1">Home / Dashboard</a>
			                </div>
			            </div>
			        </div>
					<div class="clearfix"></div>
					
<input type="hidden" value="<%=session.getAttribute("uid")%>" id="herouseridtext">
                   	<div class="col-md-12 overcome-col-padd-10  ">
						<div class="col-md-12 overcome-col-padd-10 ">
							<div class="width_100">
								
								<div class="width_100 gray-font white-bg content-font-size content-div-height">
									<div class="col-md-12 full-padding-20">

                        <div class="card-body">
                          <!-- Credit Card -->
                          
								<div class="col-md-12">	
							
									
									<div class="col-md-6 form-group">
										<label for="Supplier Name">Order Request No</label>
									   <select id='pridselect' class="form-control form-white selectSer" onchange="loadstorerequestItems()">
									   	  <%--   <c:if test="${requestList.size() == 0}"><option value="0">--Select--</option></c:if> --%>
										    <c:forEach items="${requestList}" var="pr" >                  
										        <option value="${pr.value}">
										            ${pr.label}
										        </option>                    
										    </c:forEach>
										</select> 
									
								    </div>
								
								</div>
						
                                    <div class="col-md-12 form-group">
									  <c:if test="${requestList.get(0).value == 0}"><p class="prlable">Store Request Not Available For Approve Yet</p></c:if>
								</div>
                                   <div id="PORcontents" style="display:none">
                                   <input type="hidden" id="totalorderlength">
                                   <input type="hidden" id="userarraylength">
                                   <div class="new-table-txt">
                                 	<table id="approveDT" class="hero-settings-table">
											<thead>
												<!-- <th>S.no</th>
												<th>Customer Name</th>
												<th>Dish Name</th> 
												<th>Dish Type</th> 
												<th>Product Name</th> 
												<th>Dish Count</th> -->
                                               <!--  <th>Action</th>	
                                                 <th>Remarks</th>	 --> 													
											</thead>
											
											<tbody id="style-5"> </tbody> 
												</table>
												</div>
				
	
								  <div class="col-md-12 permissionDiv aprove-txt-align"  >
					
                                        <button type="button" class="btn btn-primary approve-btn " onclick="approvestoreRequest()"> Approve </button>
                                      
			                      </div><br>
			                      
			                     </div>
			                      </div>
			                      </div>
			                      </div>
			                      </div>
			                      </div>
			                      </div>
			       </form>
			          <style>
   .new-table-txt  table tbody {
  display: block;
  max-height: 400px;
  overflow-y: scroll;
}
.new-table-txt  table thead, table tbody tr {
  display: table;
  width: 100%;
  table-layout: fixed;
}
</style>
    		<script>
/* 		$(document).ready(function() {
		    $('#approveDT').DataTable();
		    
		}); */
	      </script>	
			       
				   <jsp:include page="../../template/footer.jsp" />
    
    
</body>
</html>