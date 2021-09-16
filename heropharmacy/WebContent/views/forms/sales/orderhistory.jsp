<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Customer Orders</title>
<%if(request.getParameter("disp") == null){ %>
<jsp:include page="../../forms/template/library.jsp" /> 
<%} %>
    <script type="text/javascript" src="../js/forms/sales/pos.js"></script>
</head>
<body onload="loadorderhistory()">

<%if(request.getParameter("disp") == null){ %>
<jsp:include page="../../forms/template/header.jsp" />
<%} %>	

<div class="clearfix"></div>
					<div class="breadcrumbs"  style="display: <%= request.getParameter("disp") %>">
			            <div class="col-md-4 col-sm-4">
			                <div class="page-header">
			                        <p class="cursor-pointer bread-a-style content-font-size color-font" href="stockdashboard">order History</p>
			                </div>
			            </div>
			            <div class="col-md-8 col-sm-8">
			                <div class="page-header float-right">
			                    <a class="pull-right cursor-pointer bread-a-style content-font-size color-font" href="dashboard?uid=1">Home / Dashboard</a>
			                </div>
			            </div>
			        </div>
<div class="clearfix"></div>


         <div class="col-md-12">
							<div class="trnx-content-sectiontrnx-section" id="easyPaginate">
								<c:forEach items="${customerList}" var="order" >
								<div class="col-md-3 easypaginatediv overcome-col-padd-10">
									<div class="trnx-content-section margin-right-10px color-border--top-7px box-shadow-theme">
										<div class="trnx-padd">
											<div class="trnx-header white-bg color-font head-font-size">
												<span><i class="fa fa-credit-card small-font-size"></i> ${order.poscodehtml} </span>
											</div>
											<div class="trnx-content">
												<span class="main-head-font-size color-font white-bg"><strong><i class="fa fa-money head-font-size yellow-font"></i>${order.custname}</strong></span>
												<ul class="gray-font content-font-size nrml-ul">
													<li>location- ${order.address }|balanceamt-${order.balanceamt}|paymentstatus-${order.paymentstatus}|Deliverystatus-${order.orderstatus} </li>
												</ul>
												
											</div>
											<div class="trnx-footer">
												<p class="gray-font white-bg content-font-size">Created on <span class="pull-right">${order.billdate}</span>
										 </div>
										</div>
										
										 <div class="width_100 gray-bg light-gray-border-top">
										 <div class="stock-transfer-select"><span class="content-font-size padding-5">${order.orderstatus}</span></div>
											
										</div> 
									</div>
									<div class="clearfix"></div>
								</div>
								</c:forEach>
							</div> 
                       
							
							

							
						</div>
					</div>
					<div class="clearfix"></div>
					 	
							
					
					<style>
					#easyPaginate {width:100%;}
					.easyPaginateNav a {padding:5px;}
					.easyPaginateNav a.current {font-weight:bold;text-decoration:underline;}
					
					</style>

					<script>
						$('#easyPaginate').easyPaginate({
						    paginateElement: 'div.easypaginatediv',
						    elementsPerPage: 8,
						    effect: 'climb'
						});
					</script>

<div class="modal fade" id="order-list-popup" tabindex="-1" role="dialog" aria-hidden="true">
<button type="button" class="close" data-toggle="modal" data-target="#order-list-popup" id="orderlistbtn" style="display: none;">&times;</button>
<div class="modal-dialog">
  <div class="modal-content">
    <div class="modal-body">
      <h3>Order Details</h3>
    <div class="modal-footer alignCenter" align="center ">
    
     <table id="orderlistDT" class="table table-hover" >
	            <thead>
	                <tr>
                     <th> Product Name </th>  
                     <th> Batch </th>  
                     <th> Avail Qty </th>
                     <th> Purchase Qty </th>  
                     <th> Unit Price </th>  
                     <th> Sub Total </th>
                     
                  </tr>
	            </thead>
	            <tbody></tbody>
	        </table>
	        
	        <h3>Status</h3>
	        
	        <div class="modal-body">
	        <select  class="form-control form-white" id="orderstatuselect">
                      <c:forEach items="${orderStatusList}" var="orderstatus" >
                          <option value="${orderstatus.value}">
            ${orderstatus.label}
        </option>            
                          </c:forEach>
                      </select>
	        </div>
    </div>
    
    
     <table>
     <tr>
     <td align="left">
     Grand Total
     </td>
     <td align="right" id="grandtotaltext">
     0
     </td>
     </tr>
     
     <tr>
     <td align="left">
     Tax Amount
     </td>
     <td align="right" id="taxamounttext">
     0
     </td>
     </tr>
     
     <tr style="display: none" id="discounttd">
     <td align="left">
     Discount
     </td>
     <td align="right" id="discounttext">
     0
     </td>
     </tr>
     
     <tr>
     <td align="left">
     Total Amount
     </td>
     <td align="right" id="totalamounttext">
     0
     </td>
     </tr>
     
     
     <tr>
     <td align="left">
     Paid Amount
     </td>
     <td align="right" id="paidamounttext">
     0
     </td>
     </tr>
     
     <tr>
     <td>
     <button type="button" class="button-bg button-space" onclick="saveorders();" id="ordersavebtn">Save</button>
     <input type="hidden" id="posidtext">
     <input type="hidden" id="indexidtext">
     </td>
     </tr>
     </table>
					
  </div>
</div>
</div>
</div>

	 
	 <jsp:include page="../template/footer.jsp" />
	 
	</body>
	</html>
					
