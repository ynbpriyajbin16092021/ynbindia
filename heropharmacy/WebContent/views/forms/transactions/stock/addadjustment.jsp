<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>New Adjustment</title>
<jsp:include page="../../template/library.jsp" />
<script type="text/javascript" src="../js/forms/transactions/stock.js"></script>
</head>
<body onload="loadadjustment();">

<jsp:include page="../../template/header.jsp" />


<div class="clearfix"></div>
					<div class="breadcrumbs"  style="display: <%= request.getParameter("disp") %>">
			            <div class="col-md-4 col-sm-4">
			                <div class="page-header">
			                        <p class="cursor-pointer mar-top-10 bread-a-style head-font-size yellow-font" href="stockdashboard"><strong><i class="fa fa-table"></i>  New Adjustment</strong></p>
			                </div>
			            </div>
			            <div class="col-md-8 col-sm-8">
			                <div class="page-header float-right">
			                    <a class="pull-right cursor-pointer bread-a-style content-font-size color-font" href="dashboard?uid=1">Home / Dashboard</a>
			                </div>
			            </div>
			        </div>
					<div class="clearfix"></div>
				  
				  
				  <!-- Main content starts here -->
				          <div class="card">
                        <!-- <div class="card-header">
                            <strong class="card-title"><i class="fa fa-table"></i>  New Adjustment</strong>
                        </div> -->
                        <div class="card-body">
                          <!-- Credit Card -->
                          <div id="pay-invoice">
								<div class="col-md-12">	
									<div class="col-md-6 form-group">
									<label for="Adjustment No#">Adjustment No# </label>
								    <input class="form-control form-white" placeholder=" Ref no " type="text" id="adjustmenttext">
                    <input class="form-control form-white" placeholder=" Transfer Order " type="hidden" readonly="readonly" id="oprntext" value="INS">
								    </div>									
					                <div class="col-md-6 form-group">
									<label for="Date">Date</label>
								     <input class="form-control form-white datepicker" placeholder=" dd/mm/yyyy " type="text" id="adjustmentdatetext">
                                    </div>	
								</div>
                                <div class="col-md-12">	   
						            <div class="col-md-6 form-group">
									<label for="Destination Store">Adjustment From</label>
									<select  class="form-control form-white" id="storeselect" onchange="loadadjproductsuggestion()">>
                          <c:forEach items="${storeList}" var="store" >  
                             <option value="${store.value}">${store.label}</option> 
                            </c:forEach>
                          </select>
									</div>
									<div class="col-md-3 form-group">
									<label for="Reason">Reason</label>
									<select  class="form-control form-white" id="reasonSelect">
                              <c:forEach items="${reasonList}" var="reason" >  
                             <option value="${reason.value}">${reason.label}</option> 
                            </c:forEach>
                          </select>
									</div>
									<div class="col-md-3 nwadjustmnt"  >
					
                                        <button type="button" class="btn btn-primary" style=   "margin-top: 30px";>Inr</button>
										
			                      </div>
						        </div>
								 <div class="col-md-12">	   
						            <div class="col-md-4 form-group">
									<label for="Product">Product</label>
										<input class="form-control form-white" type="text" id="itemcodetext"  placeholder="Please add Products to transfer list" 
										  onblur="loadadjustmentbatchidlist();"></input>
										  <input type="hidden" id="productcodetext" value="0"></input>
						        </div>	
						  
						            <div class="col-md-4 form-group">
									<label for="Batch">Batch</label>
									<select  class="form-control form-white" id="batchidselect">
										  <option value="0">--Select Batch--</option>
										  </select>
									</div>
                                   <div class="col-md-4" form-group"  >
					
                                        <button type="button" class="btn btn-primary" onclick="addadjustmentproduct();" style= "margin-top: 30px";>Add Product</button>
										
			                      </div>
                                   </div>
								
						        
						  

                      
                    </div><br><br><br>
		
				  <div class="card">
                        
						
									
						
						 
                        <!-- <div class="card-body"> -->
                          <!-- Credit Card -->
                          <!-- <div id="pay-invoice"> -->
									<table id="table" class="hero-settings-table">
											<thead>
												
													
													<th>Product Details</th>
													<th>Batch</th>
													<th>Current Availability</th>
													<th>Quantity Available</th>
												    <th>Adjustable Qty	</th>	
                                                    <th>Amount</th>		
													<th>Action</th>	 													
											</thead>
											
											<tbody>
												
												</tbody>
												</table>
												
                         <!--  </div>
 -->
                       <!--  </div> -->
						
                    </div> <br><p>Notes (For Internal Use) </p>
					
					 <div class="input-group">
    
      <input id="notes" type="text" class="form-control" name="msg" placeholder="note">
     
    </div>
					</div><br><br>
					
					<div class="col-md-12 permissionDiv" >
					
                                        <button type="button" class="btn btn-primary"  onclick="saveadjustments();">Save</button>
										<a href="adjustmenthistory" type="button" class="btn btn-default " >Cancel</a>
			                      </div><br><br>
				  

					
					  <!-- Main content ends here -->
					  <style>
					  .card{
					  margin-left:20px;
					  margin-right:20px;}
					  </style>
					
<jsp:include page="../../template/footer.jsp" />
</body>
</html>