<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Store Request</title>
<jsp:include page="../../template/library.jsp" />
<script type="text/javascript" src="../js/forms/transactions/stock.js"></script>

</head>
<body onload="loadstocktransfer();loadstorerequest();">

<jsp:include page="../../template/header.jsp" />
<div class="clearfix"></div>
					<div class="breadcrumbs"  style="display: <%= request.getParameter("disp") %>">
			            <div class="col-md-4 col-sm-4">
			                <div class="page-header">
			                        <p class="cursor-pointer mar-top-10 bread-a-style head-font-size yellow-font" href="stockdashboard"><strong><i class="fa fa-table"></i>  Store Request</strong></p>
			                </div>
			            </div>
			            <div class="col-md-8 col-sm-8">
			                <div class="page-header float-right">
			                    <a class="pull-right cursor-pointer bread-a-style content-font-size color-font" href="dashboard?uid=1">Home / Dashboard</a>
			                </div>
			            </div>
			        </div>
<div class="clearfix"></div>
				  
				  
				 
				          <div class="card">
                        <div class="width_100  ">
						<div class="width_100 ">
							<div class="width_100">
								
								<div class="width_100 gray-font white-bg content-font-size">
									<div class="col-md-12 full-padding-20">
                          <!--  <div class="margin-bottom-10 content-font-size  white-font color-bg">
											<p class="padd-left-right-5"> Stock Transfer </p>
										</div> -->

                       
       <div class="card-body">
       
       <div class="col-md-12" style="display:none">	
                             <div class="col-md-6 form-group">
									 <label for="Supplier Name">Order No</label>
									<input type="hidden" id="updtxrrefno" value="${txNoList.get(0).value}">
							<select id='orderselect' class="form-control form-white selectSer" onclick="getcustomerTypeList()">
							 <!--   <option value="0">-- Select --</option> -->
									 <c:forEach items="${pendingOrderList}" var="order" >                  
										   <option value="${order.value}">
										            ${order.label}
										    </option>                    
									</c:forEach>
								</select> 
										</div> 
                            
						           <div class="col-md-6 form-group">
									 <label for="Supplier Name">Customer Name</label>
									
							<select id='customerselect' class="form-control form-white selectSer" onclick="getdishList()">
							   <option value="0">-- Select --</option>
									
								</select> 
										</div> 
						            
								<div class="col-md-6 form-group">
									<label for="Batch">Dish Name</label>
									<select  class="form-control form-white" id="dishselect" onchange="getcusdishTypeList()">
										  <option value="0">--Select--</option>
									</select>
						        </div>	
						        	<div class="col-md-6 form-group">
									<label for="Batch">Customer Requested</label>
									<!-- <input type="hidden" id="cusDishTypeSelect" disabled >
									<input class="stock-default-input" id="cusReqDishType" disabled lass="form-control form-white" />  -->
						        	<select  class="form-control form-white" id="cusDishTypeSelect" onchange="getavailabledishTypeList()">
										
									</select>
						        </div>	
						     	 
								<input type="hidden" id="uompackingselect" />	   
				 </div>
                         
                          
					<div class="col-md-12 " style="display:none">
					   	<div class="col-md-6 form-group">
									<label for="Batch">Available Dish Type</label>
									<select  class="form-control form-white" id="availdishtypeselect" onchange="getavailabledishTypeList()">
										
									</select>
						        </div>	
						         	<div class="col-md-6 form-group" id="ReasonDiv">
									<label for="Batch">Reason For Change</label>
									<textarea  class="form-control" id="changeReason"></textarea>
						        </div>	
					          <div class="col-md-6 form-group">
					            <label for="Transfer Order#">Dish count</label>
								<input type="number" class="form-control form-white" id="dishcount"  placeholder="Dish count" disabled />
							</div>
							
									<div class="col-md-6 form-group stocktransfernumberDiv">
										<label for="Transfer Order#">Transfer Order#</label>
										<input type="hidden" id="userstoreid" value="<%= session.getAttribute("storeid")%>">
									    <input class="form-control form-white" placeholder=" Transfer Order" type="text" readonly="readonly" id="transferordernotext">
	                    				<input class="form-control form-white" placeholder=" Transfer Order " type="hidden" readonly="readonly" id="transferorderidtext">
                    					<input class="form-control form-white" placeholder=" Transfer Order " type="hidden" readonly="readonly" id="oprntext" value="INS">
								    </div>									
					                <div class="col-md-6 form-group">
										<label for="Date">Transfer Date</label>
									    <input class="form-control form-white datepicker" placeholder=" dd/mm/yyyy " type="text" id="transferdatetext">
                                    </div>
                                    
                              
                                    <div class="col-md-6 form-group"  style="display:none">
										<label for="Destination Store">Transfer To</label>
										<select  class="form-control form-white" id="storeselect" value="2">
					                          	<c:forEach items="${storeList}" var="store" >  
					                             	<option value="${store.value}">
										            	${store.label}
										      		</option> 
												</c:forEach>
					                    </select>
									</div>  
									
								<div class="col-md-6 form-group" >
									<label for="Product"><b>Add More Product</b></label>
									<input class="form-control form-white " type="text" id="itemcodetext"  
									placeholder=" search here "></input>
									<input type="hidden" id="productcodetext" value="0"></input>
								</div>   
								
								       <div class="col-md-6 form-group">
					            <label for="Transfer Order#">Product count</label>
								<input type="number" class="form-control form-white" id="productcount" value="0" placeholder="product count" />
							</div>
								
				</div>
                                
       
       
       		<div class="new-table-txt">
						<table id="storerequesttDT" class="hero-settings-table"  style="width:100%">
								<thead>									
															
								</thead>
											
								<tbody id="style-5"></tbody>
						</table>
				</div>
								
				<div class="col-md-12 permissionDiv" >
					
                     <button type="button" class="btn btn-primary mar" onclick="TransferIngredients();">Save</button>
					 <a href="stocktransferhistory"><button type="button" class="btn btn-default mar ">Cancel</button></a>
			   </div><br><br>	
                          
                        
                        </div>
						
                    </div> 
					
					</div>
					</div>
					</div>
					</div>
					</div>
					
					
					   <div  id="myModal" class="modal fade modalForgetPassword" >
		              <div class="modal-dialog widthModalForget" style=" margin-left:140px;">
		                  <div class="modal-content" style="width:1100px;" >
		                      <div class="modal-header" >
		                          
		                      <h4 class="modal-title" id="productname"><strong>Product Name</strong></h4>
								  
		                      </div>
		                      <div class="modal-body">
		                        <table id="itemDetailDT" class="table table-striped table-bordered dt-responsive nowrap example" style="width:100%">
											<thead>
												<tr>
												<!--     <th>Dish</th>
													<th>Dish Type</th>
												    <th>Dishcount</th> -->
													<th>ProductName</th>	
													<th>Qty</th>											
													
													
												</tr>
											</thead>
											<tbody>
											</tbody>
											
								    </table>
		                      </div>
		                      <div class="modal-footer"></div>
		                       </div>
		              </div>
		          </div>
					
					<style>
					
					.mar{
					margin-top:50px;
					}
					.active{
					background-color:#948fca !important;
					
					}
					
					.nav-tabs {
    /* background: #222222; */
    border: 0;
    border-radius: 3px;
    padding: 0;
}
	
	.card{
						margin-left:20px;
						margin-right:20px;}			
						
						.new-table-txt  table tbody {
  display: block;
  max-height: 400px;
  overflow-y: scroll;
}
.new-table-txt  table thead,.new-table-txt  table tbody tr {
  display: table;
  width: 100%;
  table-layout: fixed;
}
							</style>
						<script>
		$(document).ready(function() {
		    $('#dishDT').DataTable();
		    
		});
	      </script>	
	  
						
				  

					
					  <!-- Main content ends here -->
					
<jsp:include page="../../template/footer.jsp" />
</body>
</html>

	