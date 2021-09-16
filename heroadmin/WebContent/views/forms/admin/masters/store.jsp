<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Stores</title>
<%if(request.getParameter("disp") == null){ %>
<jsp:include page="../home/homelibrary.jsp" />
<%} %>
<script type="text/javascript" src="../js/forms/admin/masters/masters.js"></script>
</head>
<body onload="loadstore();">
<%if(request.getParameter("disp") == null){ %>
  	 <jsp:include page="../home/homeheader.jsp" />
<%} %>
				<div class="clearfix"></div>
					<div class="breadcrumbs"  style="display: <%= request.getParameter("disp") %>">
			            <div class="col-md-4 col-sm-4">
			                <div class="page-header">
			                        <!-- <a class="cursor-pointer bread-a-style content-font-size color-font" href="stockdashboard">Category</a> -->
			                        <p class="cursor-pointer mar-top-10 bread-a-style head-font-size yellow-font" href="stockdashboard"><i class="fa fa-shopping-cart margin-top"></i> Store Master</p>
			                </div>
			            </div>
			            <div class="col-md-8 col-sm-8">
			                <div class="page-header float-right">
			                    <a class="pull-right cursor-pointer bread-a-style content-font-size color-font" href="herohome">Home / Dashboard</a>
			                </div>
			            </div>
			        </div>
					<div class="clearfix"></div>
					
					<div class="col-md-12 overcome-col-padd-10">
						<div class="col-md-7 overcome-col-padd-10">
							<div class="width_100">
								<!-- <div class="dashboard-ld-header margin-bottom-10 head-font-size white-font color-bg">
									<p><span style="font-size:18px;font-weight:bold"></span> Category </p>
								</div> -->
								<div class="width_100 gray-font white-bg content-font-size content-div-height">
									<div class="col-md-12 full-padding-20">
									<table id="storeDT" class="hero-settings-table"  style="width:100%">
											<thead>
												<tr>
													<th>Store Name</th>
													<th>Country</th>
													<th>Currency Type</th>
													<!-- <th>Phone</th>
													 <th>Email</th>  -->
													<th>Status</th>
													<th>Actions</th>
													
												</tr>
											</thead>

								    </table>
								</div>
								</div>
							</div>
						</div>
						<div class="col-md-5 overcome-col-padd-10">
							<div class="width_100">
								<div class="width_100 gray-font white-bg content-font-size content-div-heightnw" id="style-5">
								<div class="width_100">
							<button data-dismiss="modal" class="button-bg button-space pull-right mar-bot-15 mar"   onclick="clearStoreFields()">New <i class="fa fa-plus-circle"></i></button>
						</div>
									<div class="col-md-12 full-padding-20">
									
									<div class="margin-bottom-10 content-font-size  white-font color-bg">
											<p class="padd-left-right-5">Store Name </p>
										</div>
									
									<div id="masterCollapse" >
									<div class="col-md-12 form-group">	
									   <label for="form-control Manufacturer Company Name">Store Name  <span style="color:red">*</span></label>
									   <input class="form-control form-white"  type="text" id="storenametext">
									   <input  type="hidden" id="oprntext" value="INS">
									   <input class=" form-white" type="hidden" id="storeidtext">
									    
		                      			</div>
		                      			
		                      			<%-- <div class="col-md-12 form-group">	
									   <label for="Currencytype">Currency Type</label>
									   <select id='currencytypeselect' class="form-control form-white">
                                     <c:forEach items="${currencyList}" var="currency" >                  
                                     <option value="${currency.value}">
                                      ${currency.label}
                                     </option>                    
                                     </c:forEach>
                                     </select>
									   
		                      			</div> --%>
		                      			
		                      			<div class="col-md-12 form-group">	
									   <label for="Manufacturer Company Name">Address <span style="color:red">*</span></label>
									  <input class="form-control form-white" name="comment" rows="3" type="text"  id="addresstext" />
		                      			</div>
		                      			
		                      			<div class="col-md-12 form-group">	
									   <label for="Manufacturer Company Name">City <span style="color:red">*</span></label>
									  <input class="form-control form-white"  type="text" id="citytext">
		                      			</div>
		                      			
		                      			<div class="col-md-12 form-group">	
									   <label for="Manufacturer Company Name">State <span style="color:red">*</span></label>
									   <input class="form-control form-white"  type="text" id="statetext">
		                      			</div>
		                      			
		                      			<div class="col-md-12 form-group">	
									   <label for="Manufacturer Company Name">Country  <span style="color:red">*</span></label>
									   <input class="form-control form-white"  type="text" id="countrytext">
		                      			</div>
		                      			
		                      			<div class="col-md-12 form-group">	
									   <label for="Manufacturer Company Name">Zip code  <span style="color:red">*</span></label>
									   <input class="form-control form-white preventchar" type="number" id="zipcodetext">
		                      			</div>
		                      			
		                      			<div class="col-md-12 form-group">	
									   <label for="Manufacturer Company Name">Phone  <span style="color:red">*</span></label>
									   <input class="form-control form-white preventchar" type="number" id="phonetext">
		                      			</div>
		                      			
		                      			<div class="col-md-12 form-group">	
									   <label for="Manufacturer Company Name">Email  <span style="color:red">*</span></label>
									   <input class="form-control form-white"   type="text" id="emailtext">
		                      			</div>
		                      			<div class="col-md-12 form-group">	
									   <label for="Currencytype">Currency Type <span style="color:red">*</span></label>
									   <select id='currencytypeselect' class="form-control form-white">
                                     <c:forEach items="${currencyList}" var="currency" >                  
                                     <option value="${currency.value}">
                                      ${currency.label}
                                     </option>                    
                                     </c:forEach>
                                     </select>
									   
		                      			</div>
		                      			<div class="col-md-12 form-group">	
									  <!--  <label for="Manufacturer Company Name">Tax</label> -->
									   <div class="col-md-6 form-group"  style="display: none">	
									    <input type="hidden" value="${taxList.size() }" id="taxsizetext">
									    
                                   <c:forEach items="${taxList}" var="tax" > <br> 
                                    <input type="checkbox" value="${tax.value }" id="taxcheck${tax.index }">${tax.label }</input>&nbsp;
                                    </c:forEach></br>
                                   
		                      			</div>
		                      			
		                      			<div class="col-md-12 margin-top-bottom-10">
		                         			<button data-dismiss="modal" class="btn btn-primary" type="button" onclick="savestore()">Save</button>
								     		<!-- <button data-dismiss="modal" class="btn btn-default" type="button">Clear</button> -->
		                      			</div>
									</div>
									
									
									<!-- <p class="yellow-font head-font-size">Category View</p>
									<p class="gray-font content-font-size">Category<span class="pull-right yellow-font"> Tablet</span></p> -->
								</div>
								</div>
							</div>
						</div>
					</div>
					       
		               <style>
		               
		               .mar{
		               margin-top:10px;
		               margin-right:10px;
		               }
		               
		               </style>   

				 
<jsp:include page="../home/homefooter.jsp" />

</body>
</html>

