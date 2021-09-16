<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Goods Receive Note</title>
<jsp:include page="../../template/library.jsp" />
<script type="text/javascript" src="../js/forms/transactions/stock.js"></script>


</head>
<body onload="loadstocktransfer();loadPurchasedProductItems();">

<jsp:include page="../../template/header.jsp" />
<div class="clearfix"></div>
					<div class="breadcrumbs"  style="display: <%= request.getParameter("disp") %>">
			            <div class="col-md-4 col-sm-4">
			                <div class="page-header">
			                        <p class="cursor-pointer mar-top-10 bread-a-style head-font-size yellow-font" href="stockdashboard"><strong><i class="fa fa-table"></i>  Stock Transfer</strong></p>
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
                        <div class="width_100  ">
						<div class="width_100 ">
							<div class="width_100">
								
								<div class="width_100 gray-font white-bg content-font-size content-div-height">
									<div class="col-md-12 full-padding-20">
                           <div class="margin-bottom-10 content-font-size  white-font color-bg">
											<p class="padd-left-right-5"> Stock Transfer </p>
										</div>

                       
                        <div class="card-body">
                          <!-- Credit Card -->
                          
								<div class="col-md-12">	
									<div class="col-md-6 form-group stocktransfernumberDiv">
										<label for="Transfer Order#">Transfer Order#</label>
										<input type="hidden" id="userstoreid" value="<%= session.getAttribute("storeid")%>">
									    <input class="form-control form-white" placeholder=" Transfer Order" type="text" readonly="readonly" id="transferordernotext">
	                    				<input class="form-control form-white" placeholder=" Transfer Order " type="hidden" readonly="readonly" id="transferorderidtext">
                    					<input class="form-control form-white" placeholder=" Transfer Order " type="hidden" readonly="readonly" id="oprntext" value="INS">
								    </div>									
					                <div class="col-md-6 form-group">
										<label for="Date">Date</label>
									    <input class="form-control form-white datepicker" placeholder=" dd/mm/yyyy " type="text" id="transferdatetext">
                                    </div>
                                    <div class="col-md-6 form-group" style="display:none">
										<label for="Destination Store">Destination Store</label>
										<select  class="form-control form-white" id="storeselect" value="2">
					                          	<c:forEach items="${storeList}" var="store" >  
					                             	<option value="${store.value}">
										            	${store.label}
										      		</option> 
												</c:forEach>
					                    </select>
									</div>  
								<%--  <div class="col-md-3 form-group">			
										<!-- <input type="number" class="form-control form-white" id="itemcounttext"  value="1"></input> -->
										
										<select id='uompackingselect' class="form-control form-white selectSer">
										    <c:forEach items="${uomPackingList}" var="uompacking" >                  
										        <option value="${uompacking.uompackingid}">
										            ${uompacking.uompackingdesc}
										        </option>                    
										    </c:forEach>
										</select> 
										
									</div>	     --%>
								</div>
                                
                                <div class="col-md-12">	
                              
                              <!--   <div class="col-md-5 form-group">
									<label for="Product"><b>Product</b></label>
									<input class="form-control form-white " type="text" id="itemcodetext" 
									 placeholder="Please add Products to transfer list" onblur="loadbatchidlist()"></input>
									<input type="hidden" id="productcodetext" value="0"></input>
								</div>   
						            -->
						            
						             <div class="col-md-6 form-group">
									 <label for="Supplier Name">Purchase Order No</label>
									   <select id='pridselect' class="form-control form-white selectSer" 
									   onchange="loadPurchasedProductItems()">
									   		<option value="0">-- Select --</option>
										    <c:forEach items="${prList}" var="pr" >                  
										        <option value="${pr.label}">
										            ${pr.value}
										        </option>                    
										    </c:forEach>
										</select> 
										</div> 
						            
								<div class="col-md-6 form-group">
									<label for="Batch"><b>Purchase Bill no</b></label>
									<select  class="form-control form-white" id="batchidselect" onchange="getStkTransferProducts()">
										  <option value="0">--Select Batch--</option>
									</select>
						        </div>	
									 
								
									  
						       
									<input type="hidden" id="uompackingselect" />	   
						            	
						  <!-- 
						            <div class="col-md-2 form-group">
									 <button type="button" onclick="addproduct();" class="btn button-bg button-space">Add Product</button>
								   
								    
								    </select>	
									</div> -->
                                   
                                   </div>
                                   
				
									<table id="stockTxrDT" class="hero-settings-table"  style="width:100%">
											<thead>
											<tr>
													<th rowspan="2">Product Details</th>
													<th rowspan="2">Batch</th>
													<th colspan="2" align="center">Current Availability</th>
													<th rowspan="2">Available Transfer Qty</th>
													<th rowspan="2">Full UOM</th>
												    <th rowspan="2">Loose UOM</th>
												    <th rowspan="2">Quantity</th> 
												     <th rowspan="2">Net Unit Price</th>	    
                                                    <!-- <th rowspan="2">Conversion Rate</th>	
                                                  -->
													<th rowspan="2">Action</th>	 
													</tr>
													<tr>
														<th>Source Stock</th>
														<th>Destination Stock</th>
													</tr>													
											</thead>
											
											<tbody>
												
												</tbody>
												</table>
								<div class="col-md-12 permissionDiv" >
					
                                        <button type="button" class="btn btn-primary mar" onclick="savestocktransfer();">Save</button>
										<a href="stocktransferhistory"><button type="button" class="btn btn-default mar ">Cancel</button></a>
			                      </div><br><br>	
                          

                        </div>
						
                    </div> 
					
					</div>
					</div>
					</div>
					</div>
					</div>
					
					<style>
					
					.mar{
					margin-top:50px;
					}
	
	.card{
						margin-left:20px;
						margin-right:20px;}				</style>
				  

					
					  <!-- Main content ends here -->
					
<jsp:include page="../../template/footer.jsp" />

	