<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Goods Receive History</title>
	<jsp:include page="../../template/library.jsp" />
	<script type="text/javascript" src="../js/forms/transactions/purchaseorder.js"></script>
	
</head>

<body onload="loadpurchaseorder()">

<jsp:include page="../../template/header.jsp" />
					<div class="clearfix"></div>
					<div class="breadcrumbs"  style="display: <%= request.getParameter("disp") %>">
			             <div class="col-md-4 col-sm-4">
			                <div class="page-header">
			                        <a class="cursor-pointer bread-a-style content-font-size color-font" ><strong style="font-size:15px;">Goods Receive History</strong></a>
			                </div>
			            </div>
			            <div class="col-md-8 col-sm-8">
			                <div class="page-header float-right">
			                    <a class="pull-right cursor-pointer bread-a-style content-font-size color-font" href="dashboard?uid=1">Home / Dashboard</a>
			                </div>
			            </div>
			        </div> 
					<div class="clearfix"></div>
					
						<div id="users" class="col-md-12 overcome-col-padd-10  ">
						<div class="width_100 gray-font white-bg content-font-size content-div-height">
						<div class="col-md-12 full-padding-20">
					
				<!-- 	<div id="users"> -->
					
			<!-- <div class="filter-group row">
				 <div class=" col-xs-12 col-sm-12 col-md-4 pull-right">
					<input type="text" class="search form-control" placeholder="Search" />
				</div> 
				
				
			</div> -->
		<%--	<ul class="list">


 <% if(session.getAttribute("usertypestr").equals("3")) { %>
					<div class="col-md-12">
						<div class="trnx-content-sectiontrnx-section" id="easyPaginate">
							
							<div class="col-md-3 easypaginatediv overcome-col-padd-10">
								<div class="create-new-app color-bg white-font box-shadow-theme">
									<div class="create-new-app-content">
										<div class="create-new-app-text text-center white-font">
											<p><a class="white-font" href="addpurchaseorder?purchaseorderid=0">
											<i class="fa fa-plus-circle app-font-size"></i><br />CREATE</a></p>
										</div>
									</div>
									
									<div class="width_100">
										<ul class="white-font head-font-size text-center">											
										</ul>
									</div>
								</div>
							</div>
							<% } %> --%>
							
						<!-- 	<div class="width_100">
								<a class="button-bg button-space pull-right mar-bot-15 mar" href="addpurchaseorder?purchaseorderid=0">New </a>
							</div> -->
							
							<div class="row col-md-12">
								<table  class="hero-settings-table"  style="width:100%" id="purchaseorderDT" >
                                        <thead>
                                            <tr >
                                               <th> S.no </th>
                                               <th> Purchase No </th>
                                                 <th> Purchase Request No </th>
                                               <th> Date </th>
                                               <th> Purchase Status </th>
                                               <th> Total Amount </th>
                                           <th> Payment Status </th>
                                              <th> Stock Receive </th>
                                               <th> Action </th>
                                            </tr>
                                         </thead>
                                         <tbody>
                                         	<c:forEach items="${purchaseList}" var="purchaseOrder" >
                                         		<tr>
                                         			<td>${purchaseOrder.index }</td>
                                         			<td>${purchaseOrder.purchasecodenavigate }</td>
                                         			<td>${purchaseOrder.requestcode }</td>
                                         			<td>${purchaseOrder.purchasedate }</td>
                                         			<td>${purchaseOrder.recvstatusdesc }</td>
                                         			<td>${purchaseOrder.totalamt }</td>
                                         			<td>${purchaseOrder.purchasestatusdesc }</td>
                                         			<td>
                                         				<c:choose>    
					                                             <c:when test="${purchaseOrder.receivestatus != 3}"> 
																    <span><a class="text-decoration-underline color-font" href="receivestock?pid=${purchaseOrder.purchaseid }"> Process GRN </a></span>
																</c:when> 
														 		<c:otherwise>
																</c:otherwise>
                                           				</c:choose>
                                           				</td> 
                                         				<td>
                                         				<c:choose>    
				                                             <c:when test="${purchaseOrder.purchasestatusdesc =='Ordered'}">     
																		<%-- <% if(session.getAttribute("usertypestr").equals("2") || session.getAttribute("usertypestr").equals("10") ) { %>
																			<a data-toggle="modal" data-target="#modal-delet-po" onclick="confirmpurchaseorderDelete(${purchaseOrder.purchaseid })" ><i class="fa fa-trash-o"></i></a> 
																		<% } %> --%>
																		 <a href="purchaseorderview?pid=${purchaseOrder.purchaseid }"><i  class="fa fa-eye"></i></a>									
																</c:when> 
																 <c:otherwise>
																 		 <a onclick="openModalpurchaselistGRN(${purchaseOrder.purchaseid },'${purchaseOrder.purchasecode }')"><i  class="fa fa-print"></i></a>
																		 <a href="purchaseorderview?pid=${purchaseOrder.purchaseid }"><i  class="fa fa-eye"></i></a>									
																  </c:otherwise>
				                                            </c:choose>
				                                           
                                         				
                                         			</td>
                                         		</tr>
                                         	</c:forEach>
                                         </tbody>
                                    </table>
							</div>
							
							
							<div class="modal fade" id="GRNReportDetails" tabindex="-1" role="dialog" aria-hidden="true">
							  <div class="modal-dialog topZero">
							    <div class="modal-content">
							      <div class="modal-header">
							 <h4 class="modal-title">GRN Print <button type="button" class="close" data-dismiss="modal">&times;</button></h4>
							 
							      </div>
							      <div class="modal-body">
							        
							        <div class="row">
							        <div  class="col-sm-11 form-group">
							            <label>Purchase Order No</label>
							            <p id="purchasecodegrntext"></p>
							            <input type="hidden" id="purchaseprintid" />
							          </div>
							        
							          <div  class="col-sm-11 form-group">
							            <label>Supplier Invoice no</label>
							            <select class="selectNor form-control form-white"  id="suuplierbillnoGRN">
							              <c:forEach items="${bankList}" var="bank" > 
							                    <option value="${bank.value }"> ${bank.label } </option>
							              </c:forEach>
							            </select>
							          </div>
							          </div>
							      </div>
							      <div class="modal-footer alignCenter" align="center ">
							        <div class="form-group " align="center">
							             <button type="button" class="btn btn-primary " data-dismiss="modal" onclick="getPrint();" id="paymentsavebtn">Get GRN Bill</button>
							              <button type="button" class="btn btn-normal " data-dismiss="modal">Cancel</button>
							        </div>
							      </div>
							    </div>
							  </div>
							</div>
							
							
							<%-- <c:forEach items="${purchaseList}" var="purchaseOrder" >
                      		<div class="col-md-3 easypaginatediv overcome-col-padd-10">
								<div class="trnx-content-section margin-right-10px color-border--top-7px box-shadow-theme">
									<div class="trnx-padd">
										<div class="trnx-header white-bg color-font head-font-size">
											<span class="invoice_name"><i class="fa fa-credit-card head-font-size"></i></span><span class="font-18"> ${purchaseOrder.purchasecodenavigate } </span>
											
											<a><i class="fa fa-cart-plus" onclick="viewProducts(${purchaseOrder.purchaseid })" ></i></a>
											
											<div class="incredients-img">
												<img src="/heroadmin/resources/images/129220.png"  
												onclick="viewProducts(${purchaseOrder.purchaseid })"/>
											</div> 
											
											<c:choose>    
                                             <c:when test="${purchaseOrder.receivestatus != 3}"> 
                                                <% if(session.getAttribute("usertypestr").equals("2")) { %>
											    <span><a class="pull-right text-decoration-underline yellow-color-font" href="receivestock?pid=${purchaseOrder.purchaseid }">Receive Stock</a></span>
												<% } %>
											</c:when> 
									 <c:otherwise>
											</c:otherwise>
                                            </c:choose>
											
											
										</div>
										<div class="trnx-content">
											<span class="main-head-font-size color-font white-bg transaction-head-font invoice_suppliername"><strong><i class="fa fa-money head-font-size yellow-font  "></i> ${purchaseOrder.suppliername }</strong></span>
											<ul class="gray-font content-font-size nrml-ul">
												<li class="invoice_details">Purchase order - <span class="${purchaseOrder.rec_status_color }">${purchaseOrder.recvstatusdesc }</span> </li>
												<li class="invoice_details"> Total Amount - ${purchaseOrder.totalamt } </li>
												<li class="invoice_details"> Payment status - <span class="${purchaseOrder.pur_status_color }">${purchaseOrder.purchasestatusdesc } </span> </li>
											</ul>
											
										</div>
										<div class="trnx-footer">
											<p class="gray-font white-bg content-font-size ">Created <span class="pull-right invoice_created_on ">${purchaseOrder.purchasedate }</span>
											<p>
											
											</p>
										</div>
									</div>
									 <c:choose>    
                                             <c:when test="${purchaseOrder.purchasestatusdesc =='Ordered'}">     
									<div class="width_100 gray-bg light-gray-border-top">
										<ul class="gray-font head-font-size nrml-ul pull-right trnx-bottom-links">
										 <li><a><i onclick="editPurchaseHistory(${purchaseOrder.purchaseid })" class="fa fa-edit"></i></a></li>
											<% if(session.getAttribute("usertypestr").equals("2")) { %>
												<li><a data-toggle="modal" data-target="#modal-delet-po" onclick="confirmpurchaseorderDelete(${purchaseOrder.purchaseid })" ><i class="fa fa-trash-o"></i></a></li> 
											<% } %>
										</ul>
									</div>
									</c:when> 
									 <c:otherwise>
										 <div class="width_100 gray-bg light-gray-border-top">
											<ul class="gray-font head-font-size nrml-ul pull-right trnx-bottom-links">
											 	<li><a href="purchaseorderview?pid=${purchaseOrder.purchaseid }"><i  class="fa fa-eye"></i></a></li>									
											</ul>
										</div>
									  </c:otherwise>
                                            </c:choose>
								</div>
							</div>
                        </c:forEach> --%>
			<!-- 				
			<div class="no-result">
			<div class="col-md-3 easypaginatediv overcome-col-padd-10">
								<div class="create-new-app color-bg white-font box-shadow-theme">
									<div class="create-new-app-content">
										<div class="create-new-app-text text-center white-font">
											<p><b>NO RESULTS FOUND</b></p>
										</div>
									</div>
									<div class="width_100">
										<ul class="white-font head-font-size text-center">
											
										</ul>
									</div>
								</div>
				</div>
			</div> 
			<ul class="pagination"></ul>-->
		
						
		</div></div>	</div>		
						
	<div class="modal fade" id="productListModal" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel" aria-hidden="true"
		style="margin-top: 126px;">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h3 class="modal-title" id="exampleModalLabel">Info</h3>
				</div>
				<div class="modal-body">
							<div class="col-md-12">
									<table id="dishDT" class="hero-settings-table" style="width:100%">
											<thead>
												<tr>
													<th>Dish Name</th>											
													<th>Dish Quantity</th>		
													<th>created date</th>																					
												</tr>
											</thead>
									
								    </table>
								</div>
								<button type="button" class="btn btn-secondary"
						         onclick="closemodal()">Close</button>
								</div>							
								
							</div>
						</div>
					</div>	
						
		
							
							<!-- <div class="col-md-3 easypaginatediv overcome-col-padd-10">
								<div class="trnx-content-section margin-right-10px color-border--top-7px box-shadow-theme">
									<div class="trnx-padd">
										<div class="trnx-header white-bg color-font head-font-size">
											<span><i class="fa fa-credit-card small-font-size"></i> PO-000002 </span>
										</div>
										<div class="trnx-content">
											<span class="main-head-font-size color-font white-bg"><strong><i class="fa fa-money head-font-size yellow-font"></i> Ram Kumar</strong></span>
											<ul class="gray-font content-font-size nrml-ul">
												<li>Purchase order - Received | Total Amount - 121200 | Payment status - Paid</li>
											</ul>
											
										</div>
										<div class="trnx-footer">
											<p class="gray-font white-bg content-font-size">Created <span class="pull-right">Jun 26, 2015 2:10AM</span>
											<p>
											
											</p>
										</div>
									</div>
									<div class="width_100 gray-bg light-gray-border-top">
										<ul class="gray-font head-font-size nrml-ul pull-right trnx-bottom-links">
											<li><a><i class="fa fa-hdd-o"></i></a></li>
											<li><a><i class="fa fa-trash-o"></i></a></li>
										</ul>
									</div>
								</div>
							</div>
							 -->

							
						
					<div class="clearfix"></div>
					 	
							
					
					<style>
					#easyPaginate {width:100%;}
					.easyPaginateNav a {padding:5px;}
					.easyPaginateNav a.current {font-weight:bold;text-decoration:underline;}
					</style>

					<!-- <script>
						$('#easyPaginate').easyPaginate({
						    paginateElement: 'div.easypaginatediv',
						    elementsPerPage: 8,
						    effect: 'climb'
						});
					</script> -->
					
					
					<script>
var options = {
		valueNames: [
			'invoice_name',
			'invoice_created_on',
			'invoice_details',
			'invoice_suppliername'
			
		],
		page: 8,
		pagination: true
	};
	var userList = new List('users', options);

	function resetList(){
		userList.search();
		userList.filter();
		userList.update();
		$(".filter-all").prop('checked', true);
		$('.filter').prop('checked', false);
		$('.search').val('');
		//console.log('Reset Successfully!');
	};
	  
	
	                               
	$(function(){
	
		userList.on('updated', function (list) {
			
			if (list.matchingItems.length > 0) {
				
				$('.no-result').hide()
			} else {
				
				$('.no-result').show()
			}
		});
	});
</script>
					

					<!-- <!-- <div class="card">
                        <div class="card-header">
                            <h4><strong class="card-title"><i class="fa fa-shopping-bag"> Purchase Order</i></strong></h4>
                        </div>
                        <div class="card-body">
                          Credit Card
								<div id="pay-invoice">
								<a href="addpurchaseorder?purchaseorderid=0" class="btn btn-secondary pull-right mar-bot-15" >New <i class="fa fa-plus-circle"></i></a><br>
									<table id="purchaseorderDT" class="table table-striped table-bordered  nowrap " style="width:100%">
											<thead>
												
													<th>Id</th>
													<th>Purchase Order#</th>
													<th>Purchase Date</th>
													<th>Supplier</th>
													<th>Purchase Status</th>
													<th>Grand Total</th>
													<th>Paid</th>
													<th>Balance Status</th>
													<th>Payment Status</th>
													<th>Action</th>
													
												
											</thead>
											
											
										</table><br>
										
										
                         

                        </div>
                    </div> -->
                    
                    <jsp:include page="../../template/footer.jsp" />

</body>
</html>