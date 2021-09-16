 <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script type="text/javascript" src="../js/forms/transactions/order.js"></script>
    <title>Create Order Request</title>
        <jsp:include page="../../template/library.jsp" />
</head>
<body onload="loadorderrequest();">
    
    
    <jsp:include page="../../template/header.jsp" />
    <form id="addpurchaseform">
    
 
 					<div class="clearfix"></div>
					<div class="breadcrumbs"  style="display: <%= request.getParameter("disp") %>">
			            <div class="col-md-4 col-sm-4">
			                <div class="page-header">
			                        <p class="cursor-pointer mar-top-10 bread-a-style head-font-size yellow-font" href="stockdashboard"><strong>New Order Request</strong></p>
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
									
										
	                        			<input class="form-control form-white" placeholder=" Purchase Request Number " type="hidden" id="oprntext" value="INS">
									<input class="form-control form-white" placeholder=" Purchase Request Number " type="hidden" id="orderhdrid" value="0">
								 <div class="col-md-6 form-group">
								 	<label for="Reference#">Reference# <span style="color:red">*</span></label>
								 <c:forEach items="${referencenolist}" var="orderrefno" >
										<input class="form-control form-white" placeholder=" Reference " type="text" id="referencenotext" value="${orderrefno.refno }" disabled >
									  </c:forEach>
								 </div>
								  <div class="col-md-6 form-group">
										<label for="Date">Order Date</label>
										<input placeholder="" class="form-control form-white datepicker" type="text" id="orderdatetext"> 
									</div>
								  <div class="col-md-6 form-group">
										<label for="Dishname#">Customer Name <span style="color:red">*</span></label>
										<select id='customerid' class="form-control form-white selectSer" >
											<c:forEach items="${customerList}" var="customer" >
										<option value="${customer.value}">
										            ${customer.label}
										        </option> 
									  </c:forEach>
									  </select> 
									</div> 	
									
						            <div class="col-md-6 form-group">
										<label for="Dishname#">Dish Name <span style="color:red">*</span></label>
										<select id='dishnameselect' class="form-control form-white selectSer" onchange="getdishtype();">
											<c:forEach items="${dishList}" var="dishname" >
										<option value="${dishname.value}">
										            ${dishname.label}
										        </option> 
									  </c:forEach>
									  </select> 
									</div> 	
									   
						            <div class="col-md-6 form-group">
										<label for="Dish Type"> Dish Type</label>
										<select id='dishtypeselect' class="form-control form-white selectSer">
											
									  </select>  
									</div>
										 <div class="col-md-6 form-group">
								
						           
									 <button type="button" onclick="addmenu();" class="btn btn-primary" style= "margin-bottom: 30px">Add Menu</button>
									
									</div>	
								</div>
							
						  
						           
                                   
                                   </div>
                              <!--      <div id="dishDT_wrapper" class="dataTables_wrapper no-footer">
   <div id="style-5" style=" overflow-y: scroll; height: 280px;overflow-x: hidden;"> -->
                                   <div class="new-table-txt">
                                 	<table id="addorderrequest" class="hero-settings-table">
											<thead>
												<th>S.no</th>
												<th>Customer Name</th>
                                                <th>Dish Name</th>	 	
                                                 <th>Dish Type</th>	 	
                                                  <th>Dish Count</th>	 
                                                   <th>Action</th>	 													
											</thead>
											
											<tbody id="style-5"> </tbody> 
												</table>
												
                         </div>
		
			
								
								  <div class="col-md-12 permissionDiv"  style="margin:20px">
								  
				 <div class="col-md-6">
                                        <button type="button" class="btn btn-primary  " onclick="saveorderRequest()">Save</button>
										<a type="button"  href="purchaseorderhistory" class="btn btn-default ">Cancel</a>
			                     </div> </div><br>
			                      
			                     
			                      </div>
			                      </div>
			                      </div>
			                      </div>
			                      <!-- </div>
			                      </div> -->
			       </form>
				   <jsp:include page="../../template/footer.jsp" />
       <style>
   .new-table-txt  table tbody {
  display: block;
  max-height: 300px;
  overflow-y: scroll;
}
.new-table-txt  table thead, table tbody tr {
  display: table;
  width: 100%;
  table-layout: fixed;
}
</style>
    <script>
		$(document).ready(function() {
		    $('#addorderrequest').DataTable();
		    
		});
	      </script>
    
    
</body>
</html>