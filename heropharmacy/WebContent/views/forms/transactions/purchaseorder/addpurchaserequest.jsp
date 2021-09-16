 <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script type="text/javascript" src="../js/forms/transactions/purchaseorder.js"></script>
    <title>Create Purchase Request</title>
        <jsp:include page="../../template/library.jsp" />
</head>
<body onload="loadaddpurchaserequest();">
    
    
    
    <jsp:include page="../../template/header.jsp" />
    <form id="addpurchaseform">
    
 
 					<div class="clearfix"></div>
					<div class="breadcrumbs"  style="display: <%= request.getParameter("disp") %>">
			            <div class="col-md-4 col-sm-4">
			                <div class="page-header">
			                        <p class="cursor-pointer mar-top-10 bread-a-style head-font-size yellow-font" href="stockdashboard"><strong>New Purchase Request</strong></p>
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
								
								<div class="width_100 gray-font white-bg content-font-size ">
									<div class="col-md-12 full-padding-20">

                        <div class="card-body">
                          <!-- Credit Card -->
                          
								<div class="col-md-12">	
									<div class="col-md-6 form-group purchaseordernumberDiv">
							            
											<label for="Purchase Request Number">Purchase Request Number</label>
										
										<!-- <input type="text" class="form-control inpttypestyle"  > -->
										<input class="form-control form-white" placeholder=" Purchase Request Number " type="text" id="purchasecodetext" readonly="readonly"> 
	                        			<input class="form-control form-white" placeholder=" Purchase Request Number " type="hidden" id="purchaseordernotext">
	                        			<input class="form-control form-white" placeholder=" Purchase Request Number " type="hidden" id="oprntext" value="INS">
									</div>
									
									<%-- <div class="col-md-6 form-group">
										<label for="Supplier Name">Supplier Name</label>
									   <select id='suppliernameselect' class="form-control form-white selectSer">
										    <c:forEach items="${supplierList}" var="supplier" >                  
										        <option value="${supplier.value}">
										            ${supplier.label}
										        </option>                    
										    </c:forEach>
										</select> 
									
								    </div>								
                                     <div class="col-md-6 form-group">
										<label for="Supplier Name">Store Name</label>
									   <select id='storeidselect' class="form-control form-white selectSer">
										    <c:forEach items="${storeList}" var="store" >                  
										        <option value="${store.value}">
										            ${store.label}
										        </option>                    
										    </c:forEach>
										</select> 
									
								    </div>	 --%>	
						            <div class="col-md-6 form-group">
										<label for="Reference#">Reference# <span style="color:red">*</span></label>
										<!-- <input type="text" class="form-control inpttypestyle"  > -->
											<c:forEach items="${referencenolist}" var="purchaseOrder" >
										<input class="form-control form-white" placeholder=" Reference " type="text" id="referencenotext" value="${purchaseOrder.refno }" disabled >
									  </c:forEach>
									</div> 	
									   
						            <div class="col-md-6 form-group">
										<label for="Date"> Date</label>
										<input placeholder="" class="form-control form-white datepicker" type="text" id="purchasedatetext"> 
									</div>
								</div>
								 <div class="col-md-12">
									<div class="col-md-6 form-group">
										<label for="Product">Product Name</label>
										<input type="text" class="form-control form-white" onblur="checkAvailableProduct()" id="itemcodetext"  placeholder="Please select Product"></input>
                      					<input type="hidden" id="productcodetext" value="0"></input>
									</div>
									<div class="col-md-3 form-group">			
										<!-- <input type="number" class="form-control form-white" id="itemcounttext"  value="1"></input> -->
										 <div class="changeContent">
									 </div>
										<select id='uompackingselect' class="form-control form-white selectSer">
										    <c:forEach items="${uomPackingList}" var="uompacking" >                  
										        <option value="${uompacking.uompackingid}">
										            ${uompacking.uompackingdesc}
										        </option>                    
										    </c:forEach>
										</select> 
										
									</div>	   
						            <div class="col-md-3 form-group">
									 <button type="button" onclick="getUOMforPacking();" class="btn btn-primary" style= "margin-bottom: 30px">Add Product</button>
									<!-- <button type="button" onclick="openDishDetails();" class="btn btn-primary" style= "margin-bottom: 30px; margin-left:15px;">Add Dish</button>
									 -->
									
									</div>	
						  
						           
                                   
                                   </div>
                                   <div class="new-table-txt">
                                 	<table id="addpurchaseDT" class="hero-settings-table">
											<thead>
												<th>Product Name</th>
												<!-- <th>Full UOM</th>
												<th>Loose UOM</th> -->
												<th>Quantity</th>
												<th>Unit Rate</th>
												<th>Total Amount</th>
                                                <th>Action</th>	 													
											</thead>
											
											<tbody id="style-5"> </tbody> 
												</table>
												
                         </div>
					
							<div class="col-md-12 margin-top-20">
									<div class="col-md-6 form-group">
									<label for="Product"></label>
								
									<!--  <textarea class="form-control form-white" placeholder="Notes" id="notestext"></textarea>   -->
									</div>	
							</div>
							
	                        <div class="modal fade" id="productModal" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel" aria-hidden="true"
		style="margin-top: 126px;">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h3 class="modal-title" id="exampleModalLabel">Info</h3>
				</div>
				<div class="modal-body">
						<div class="col-md-12">
							<p>You cannot edit this Purchase Request.Please confirm to proceed</p>
						</div>
				</div> 
				<div class="modal-footer">
				<button type="button" class="btn btn-primary"
						onclick="savepurchaserequest()" style="margin: 8px 4px;">confirm</button>
					<button type="button" class="btn btn-secondary"
						onclick="$('#productModal').modal('hide');">Close</button>
					
				</div>
			</div>
		</div>
	</div>
			
			
			 <div class="modal fade" id="dishDetails" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel" aria-hidden="true"
		style="margin-top: 126px;">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h3 class="modal-title" id="exampleModalLabel">Info</h3>
				</div>
				<div class="modal-body">
						<div class="col-md-12">
							<p>Please enter Dish details for the selected Product.</p>
						</div>
						<div class="col-md-12" style="padding-bottom:20px">
							<div class="col-md-2">
								<label for="oldpassword"> Dish</label>
							</div>
							<div class="col-md-4">
								<input type="text" class="form-control form-white" id="dishname"  placeholder="Dish names" />
								<%-- <select id='dishselect' class="form-control form-white selectSer">
									 <c:forEach items="${dishList}" var="dish" >                  
										   <option value="${dish.value}">
										            ${dish.label}
										    </option>                    
									</c:forEach>
								</select> --%>
							</div>
							<div class="col-md-4">
								<input type="number" class="form-control form-white" id="dishcount"  placeholder="Dish count" />
							</div>
							<div class="col-md-2">
								<button type="button" class="btn btn-primary" onclick="addtodishArray()">Add</button>
							</div>
						</div>
						<div class="col-md-12 ">
							<table id = "dishDT" class="hero-settings-table">
											<thead>
												<th> # </th>
												<th>Dish Name</th>
												<th>Dish Count</th>
                                                <th>Action</th>	 													
											</thead>
											<tbody >
												
											</tbody>
							</table>
						</div>
				</div> 
				<div class="modal-footer ">
				
					<button type="button" class="btn btn-secondary modal-dish-product"
						onclick="$('#dishDetails').modal('hide');"> OK </button>
					
				</div>
			</div>
		</div>
	</div>
			
							<!-- <div class="col-md-12">
									<div class="col-md-6 form-group">
									<label for="Product">Terms & conditions</label>
									<input type="text" class="form-control inpttypestyle"  >
									 <textarea class="form-control form-white" placeholder="Mention your company's terms and conditions." id="termstext"></textarea>  
									</div>	
							</div> -->
								
								  <div class="col-md-12 permissionDiv"  >
					
                                        <button type="button" class="btn btn-primary  " onclick="openConfirmationModal()">Save</button>
										<a type="button"  href="purchaserequesthistory" class="btn btn-default ">Cancel</a>
			                      </div><br>
			                      
			                     
			                      </div>
			                      </div>
			                      </div>
			                      </div>
			                      </div>
			                      </div>
			                      <style>
			                      .new-table-txt table tbody {
  display: block;
  max-height: 300px;
  overflow-y: scroll;
}
.new-table-txt table thead, table tbody tr {
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
			       </form>
				   <jsp:include page="../../template/footer.jsp" />
    
    
</body>
</html>