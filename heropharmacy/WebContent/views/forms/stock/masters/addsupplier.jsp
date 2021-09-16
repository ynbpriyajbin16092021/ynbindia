<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Supplier</title>
<%if(request.getParameter("disp") == null){ %>
<jsp:include page="../../template/library.jsp" />
<%} %>
<script type="text/javascript" src="../js/forms/masters/masters.js"></script>
</head>
<body onload="loadaddsupplier()">

<%if(request.getParameter("disp") == null){ %>
  	<jsp:include page="../../template/header.jsp" />
<%} %>
				  <!-- Main content starts here -->
				          <div class="card">
                        <div class="card-header">
                            <strong class="card-title"><i class="fa fa-table"></i> Add stock count list</strong>
                        </div>
                        <div class="card-body">
                          <!-- Credit Card -->
                          <div id="pay-invoice">
								<div class="col-md-12">	
									<div class="col-md-6 form-group">
									<div class="col-md-12">	
									<label for="SupplierName">Supplier Name</label>
									</div>
									<div class="col-md-2 form-group">
									
								   <select class="form-control form-white" id="supplierinisselect">
								    <option value="Mr">Mr.</option>
								    <option value="Mrs">Mrs.</option>
									<option value="Ms">Ms.</option>
									<option value="Dr">Dr.</option>
								    </select>
									</div>
									<div class="col-md-5 form-group">
									 <input class="form-control"  type="text" id="supplierfirstnametext">
									 
									</div>
									<div class="col-md-5 form-group">
									<input class="form-control"  type="text" id="supplierlastnametext">
                  <input class="form-control form-white"  type="hidden" id="supplieridtext">
                  <input class="form-control form-white"  type="hidden" id="oprntext" value="INS">
									</div>
								    </div>									
					                <div class="col-md-6 form-group">
									<label for="SupplierType">Supplier Type</label>
								      <select id='suppliertypeselect' class="form-control form-white">
                                       <c:forEach items="${suppliertypeList}" var="suppliertype" >                  
                                       <option value="${suppliertype.value}">
                                        ${suppliertype.label}
                                       </option>                    
                                        </c:forEach>
                                        </select>
                                    </div>	
								</div>
                                <div class="col-md-12">	   
						            <div class="col-md-6 form-group">
									<label for="Mobile Number">Mobile Number</label>
									
									 <input class="form-control" type="number"  type="text" id="suppliermobnotext">
									</div>
									<div class="col-md-6 form-group">
									<label for="Telephone">Telephone</label>
									 <input class="form-control form-white" type="number" type="text" id="suppliertelnotext">
									</div>
						        </div>
								 <div class="col-md-12">	   
						            <div class="col-md-6 form-group">
									<label for="emailid">Eamil ID</label>
									 <input class="form-control form-white"  type="text" id="supplieremailtext">
									</div>
									<div class="col-md-6 form-group">
									<label for="Address">Address</label>
									<textarea  class="form-control form-white" id="supplieraddresstext"> </textarea> 
									</div>
						        </div>
								<div class="col-md-12">	   
						            <div class="col-md-6 form-group">
									<label for="City">City</label>
									 <input class="form-control form-white" id="suppliercitytext" type="text">
									</div>
									<div class="col-md-6 form-group">
									<label for="state">State</label>
									<input class="form-control form-white"  id="supplierstatetext" type="text">
									</div>
						        </div>
								<div class="col-md-12">	   
						            <div class="col-md-6 form-group">
									<label for="Zipcode">Zipcode</label>
									<input class="form-control form-white" type="number" type="text" id="supplierzipcodetext">
									</div>
									<div class="col-md-6 form-group">
									<label for="Country">Country</label><br>
									<input class="form-control form-white"  id="suppliercountrytext" type="text">
									</div>
						        </div>
								<div class="col-md-12">
                                <div class="col-md-6 form-group">								
								<label for="tinnumber">Tin number</label>
								<input class="form-control form-white"  id="suppliertinnotext" type="text">
								</div>
								</div>
								
								<div class="col-md-12">
								<div class="col-md-6 form-group">		
                    <label class="col-sm-4 control-label">Terms of Payment</label>
                      
                      
                        
                           <div class="col-md-4 form-group">
					             
		                        <div class="col-sm-16" id="typeselect">
					               
                                             	<span class="checkbox-inline akucheckbox-inline"><input type="radio" name="gendertext" value="Default"> Default</span>
		                                        <span class="checkbox-inline akucheckbox-inline1"><input type="radio" name="gendertext" value="Custom" checked> Custom</span>
                               
                                                
                                             <div class="col-sm-16 control-label" id="Default" class="colors " style="display:none"> 
                                                  <select id='paymenttypeselect' class="form-control form-white">
						                          <option value="" disabled selected>Choose your option</option>
                                                  <option value="1">Cash</option>
                                                  <option value="2">Credit Card</option>
                                                  <option value="3">DebitCard</option>
                                                  <option value="4">Cheque</option> 
                                                  <option value="5">Credit</option>
                                                  </select>
           
                                             </div>
                                              <div id="5" class="colors" style="display:none"> Required Days 
                                                   <input class="form-control form-white"  id="requireddaystext" type="text">
                                             </div>
			                     </div> 
					        </div>
					        
					         
              </div> 
								
								<div class="col-md-12" >
                                        <button type="button" class="btn btn-primary btnalgin" onclick="savesupplier();">Save</button>
										<button type="button" class="btn btn-danger btnalgin">Cancel</button>
			                      </div>
								
						  </div>
						  

                        </div>
                    </div>
		
				  
				  

					
					  <!-- Main content ends here -->
					
					