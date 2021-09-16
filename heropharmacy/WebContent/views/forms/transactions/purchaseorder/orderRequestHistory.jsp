<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script type="text/javascript" src="../js/forms/transactions/order.js"></script>
    <title>Order Request History</title>
  <jsp:include page="../../template/library.jsp" />
</head>
<body > 

<jsp:include page="../../template/header.jsp" />
<div class="clearfix"></div>
					<div class="breadcrumbs"  style="display: <%= request.getParameter("disp") %>">
			            <div class="col-md-4 col-sm-4">
			                <div class="page-header">
			                        <div class="lowstockspan yellow-font"><strong class="card-title"><i class="fa fa-shopping-cart"></i>  Order Request List <span class="notification-delpo-page">${notification_count}<span></span></span></strong></div>
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
								
								<a href="addOrderRequest?id=0" class="button-bg button-space pull-right mar-bot-15-bp">
							Create Order Request <i class="fa fa-plus-circle"></i></a>
							
									<div class="col-md-12 full-padding-20">
										<table  class="hero-settings-table"  style="width:100%" id="purchaseRequestDT">
	                                        <thead>
	                                            <tr>
	                                               <th> # S.No</th>
	                                               <th>Order No</th>                                             
	                                               <th>Status</th>
	                                               <th>Order Date</th>
                                                   <th>Action</th>
	                                            </tr>
	                                         </thead>
	                                           <tbody>
                                         	<c:forEach items="${orderList}" var="order" varStatus="loop">
                                         		<tr>
                                         			<td>${loop.index+1}</td>
                                         			<td><a href="orderRequestView?id=${order.ord_req_id }">${order.ord_ref_no }</a></td>
                                         			<td>${order.status_desc }</td>
                                         			 <td>${order.ord_order_date }</td>  
                                         			  <td>
                                         			   <c:if test="${order.status == 1 }">
                                    <input class="btn white-bg fa-input color-font" type="button" value="&#xf044;" onclick="editOrder(${order.ord_req_id })" >
                                         			 <button class="delete myBtnTab" data-toggle="modal" data-target="#modal-delet" > <i class="fa fa-trash-o" onclick="deleteOrder(${order.ord_req_id })"></i> </button>  
                                   </c:if>
                                         			  
                                         			  
                                         			  </td>                       			
                                         		
                                         		</tr>
                                         	</c:forEach>
                                         </tbody>
	                                    </table>
									</div>	
								</div>
							</div>
						</div>
					</div>
								
		<script>
		$(document).ready(function() {
		    $('#purchaseRequestDT').DataTable({	ordering:false});
		});
		/* $('#purchaseRequestDT').DataTable();  */
		</script>					

     <jsp:include page="../../template/footer.jsp" />
</body>
</html>