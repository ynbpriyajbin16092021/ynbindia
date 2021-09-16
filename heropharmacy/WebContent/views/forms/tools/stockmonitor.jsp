<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Stock Monitor</title>
<jsp:include page="../template/library.jsp" />
<script type="text/javascript" src="../js/forms/tools/stockmonitor.js"></script>

</head>
<body onload="loadstockmonitor();">

<jsp:include page="../template/header.jsp" />



<div class="clearfix"></div>
					<div class="breadcrumbs"  style="display: <%= request.getParameter("disp") %>">
			            <div class="col-md-4 col-sm-4">
			                <div class="page-header">
			                        <p class="cursor-pointer mar-top-10 bread-a-style head-font-size yellow-font" href="stockdashboard"><strong><i class="fa fa-shopping-bag"></i> Store Stocks</strong></p>
			                </div>
			            </div>
			            <div class="col-md-8 col-sm-8">
			                <div class="page-header float-right">
			                    <a class="pull-right cursor-pointer bread-a-style content-font-size color-font" href="dashboard?uid=1">Home / Dashboard</a>
			                </div>
			            </div>
			        </div>
<div class="clearfix"></div>

<div class="col-md-12 overcome-col-padd-10  ">
						<div class="col-md-12 overcome-col-padd-10 ">
							<div class="width_100">
								
								<div class="width_100 gray-font white-bg content-font-size content-div-height">
									<div class="col-md-12 full-padding-20">
                         
								<div class="width_100">	
									 <div class="margin-bottom-10 content-font-size  white-font color-bg">
											<p class="padd-left-right-5">Filter By: </p>
										</div>
								<div class="col-md-12">
								 <div class="col-md-4" id="storediv">
									 
									 <label>Store List</label>
							        <select class="form-control form-white selectNor" id="storeselect" onchange="onchangefields();">
								          <c:forEach items="${storeList}" var="store" >                  
								        	<option value="${store.value}">
								            ${store.label}
								        	</option>                    
								    	</c:forEach>
							        </select>
       							</div>
								
						       
						       <div class="col-md-4">
						        
						        <label>Category List</label>

						        <select class="form-control form-white selectNor" id="categoryselect" onchange="onchangefields();">
						          <c:forEach items="${categoryList}" var="category" >                  
								        <option value="${category.value}">
								            ${category.label}
								        </option>                    
								    </c:forEach>
						        </select>
						       </div>
						       
						       <div class="col-md-4">
						       
						        <label>Manufaturer List</label>
						        
						        <select class="form-control form-white selectNor" id="manufacturerselect" onchange="onchangefields();">
						          <c:forEach items="${manufacturerList}" var="manufacturer" >                  
							        <option value="${manufacturer.value}">
							            ${manufacturer.label}
							        </option>                    
						   		 </c:forEach>
						        </select>
						       </div>
						       
      </div>
			</div>																
																	
																	
																	
																		        
					<div class="col-md-12 overcome-col-padd-10 ">
						<div class="col-md-12 overcome-col-padd-10">
							<div class="width_100">
								<!-- <div class="dashboard-ld-header margin-bottom-10 head-font-size white-font color-bg">
									<p><span style="font-size:18px;font-weight:bold"></span> Category </p>
								</div> -->
								<!-- <div class="width_100 gray-font white-bg content-font-size content-div-height mar"> -->
									<div class="width_100 margin-top-10">
									
												                    <div class="width_100">
												                          
																			    <table id="stockmonitorDT" class="hero-settings-table"  style="width:100%">
																					<thead>
																					   <th>Product Name</th>
																					   <th>Category</th>
																					   <th>Unit Type</th>
																					   <th>Manufacture Name</th>
																					   <th>Stock</th>	
																					</thead>
																			      
																				</table>
																		
																	</div>
																</div>   
															<!-- </div> -->
											</div>
									</div>
									</div>
									
                <script type="text/javascript">
				$(document).ready(function(){
					
				jQuery(".tdclick").click(function(){
					jQuery('#myModal').modal({backdrop: true});
				   
					
					});	
					
				});
				</script> 		
				<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog" tabindex="-1" id="myModal" class="modal fade modalForgetPassword" >
		              <div class="modal-dialog widthModalForget">
		                  <div class="modal-content" style="width:800px;" >
		                        <div class="modal-header">
							      <h5 class="modal-title">MB CABS BATCHES</h5>
		                          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		                        </div><br>
							    <div class="modal-body">
		                            <table id="itemDetailDT" class="table table-striped table-bordered dt-responsive nowrap example1" style="width:100%">
											<thead>
												<tr>
												    <th>CATEGORY</th>
													<th>PRODUCT NAME</th>
													<th>BATCH NO</th>
													<th>UNIT TYPE</th>
													<th>PRICE</th>
													<th>STOCK</th>
													
												</tr>
											</thead>
											
								    </table>
                                    
		                      </div>
		                      
		                         <div class= "clearfix"> </div>
								
		                      <div class="modal-footer">
		                         
		                                
		                      </div>
		                  </div>
		              </div>
		          </div>
		           </div>
		            </div>
		             </div>
		          <style>
		          .mar{
		          margin-top:60px;}

		         
		          </style>
		          <jsp:include page="../template/footer.jsp" />
		          </body>
</html>
											