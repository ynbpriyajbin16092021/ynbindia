<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Purchase Return History</title>
	<jsp:include page="../../template/library.jsp" />
	<script type="text/javascript" src="../js/forms/transactions/purchaseorder.js"></script>
	
</head>

<body onload="loadpurchaseorder()">

<jsp:include page="../../template/header.jsp" />
					<div class="clearfix"></div>
					<div class="breadcrumbs"  style="display: <%= request.getParameter("disp") %>">
			             <div class="col-md-4 col-sm-4">
			                <div class="page-header">
			                        <a class="cursor-pointer bread-a-style content-font-size color-font" href="stockdashboard">PurchaseOrder History</a>
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
					

							
							<!-- <div class="width_100">
								<a class="button-bg button-space pull-right mar-bot-15 mar" href="addpurchaseorder?purchaseorderid=0">New </a>
							</div> -->
							
							<div class="row col-md-12">
								<table  class="hero-settings-table"  style="width:100%" id="purchaseorderDT" >
                                        <thead>
                                            <tr >
                                               <th> S.no </th>
                                               <th> Purchase No </th>
                                               <th> Date </th>
                                               <th> Purchase Status </th>
                                               <th> Total Amount </th>
                                              <th> Payment Status </th>
                                              <th> Return </th>
                                             
                                            </tr>
                                         </thead>
                                         <tbody>
                                         	<c:forEach items="${purchaseList}" var="purchaseOrder" >
                                         		<tr>
                                         			<td>${purchaseOrder.index }</td>
                                         			<td>${purchaseOrder.purchasecode }</td>
                                         			<td>${purchaseOrder.purchasedate }</td>
                                         			<td>${purchaseOrder.recvstatusdesc }</td>
                                         			<td>${purchaseOrder.totalamt }</td>
                                         			<td>${purchaseOrder.purchasestatusdesc }</td>
                                         			<td>
                                         				<c:choose>    
					                                             <c:when test="${purchaseOrder.receivestatus != 1}"> 
					                                                <% if(session.getAttribute("usertypestr").equals("2") || session.getAttribute("usertypestr").equals("10") ) { %>
																 
																<%--     <span><a class="text-decoration-underline color-font" 
																    href="purchasereturn?pid=${purchaseOrder.prhdrid }&phid=${purchaseOrder.purchaseid }">Return</a></span>
																	<% } --%> 
																	
																	<span><a class="text-decoration-underline color-font" 
																    onclick="getGRNreportdetails('${purchaseOrder.purchasecode }')">Return</a></span>
																	<% }
																	
																	%>
																</c:when> 
														 		<c:otherwise>
																</c:otherwise>
                                           				</c:choose>
                                           				</td>
                                         				
                                         		</tr>
                                         	</c:forEach>
                                         </tbody>
                                    </table>
							</div>
							
							
						
						
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
					
<div class="modal fade" id="GRNReportDetails" tabindex="-1" role="dialog" aria-hidden="true">
  <div class="modal-dialog topZero">
    <div class="modal-content">
      <div class="modal-header">
      <h4 class="modal-title">SELECT GRN BILL NO. <button type="button" class="close" data-dismiss="modal">&times;</button></h4>
 
      </div>
      <div class="modal-body">
        
        <div class="row">
        
          <div  class="col-sm-11 form-group">
            <label>Purchase Received Bill No</label>
            <select class="selectNor form-control form-white"  id="purchasebillnoGRN">
                <%--    <c:forEach items="${bankList}" var="bank" > 
                    <option value="${bank.value }"> ${bank.label } </option>
              </c:forEach> --%>
            </select>
          </div>          
          
          </div>
      </div>
      <div class="modal-footer alignCenter" align="center ">
        <div class="form-group " align="center">
             <button type="button" class="btn btn-primary " data-dismiss="modal" 
             onclick="gotopurchasereturn();" id="paymentsavebtn">Continue</button>
              <button type="button" class="btn btn-normal " data-dismiss="modal">Cancel</button>
        </div>
      </div>
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
	$('#purchaseorderDT').DataTable(); 
	
</script>
				

				
                    
                    <jsp:include page="../../template/footer.jsp" />

</body>
</html>