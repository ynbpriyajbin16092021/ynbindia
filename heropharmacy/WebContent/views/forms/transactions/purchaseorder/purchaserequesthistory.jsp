<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script type="text/javascript" src="../js/forms/transactions/purchaseorder.js"></script>
    <title>Purchase Request History</title>
  <jsp:include page="../../template/library.jsp" />
</head>
<body onload="loadpurchaseRequest()"> 

<jsp:include page="../../template/header.jsp" />
<div class="clearfix"></div>
					<div class="breadcrumbs"  style="display: <%= request.getParameter("disp") %>">
			            <div class="col-md-4 col-sm-4">
			                <div class="page-header">
			                        <div class="lowstockspan yellow-font"><strong class="card-title"><i class="fa fa-shopping-cart"></i>  Purchase Request  <span class="notification-delpo-page">${notification_count}<span></span></span></strong></div>
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
							
							<!-- <a href="addpurchaserequest?purchaserequestid=0" class="button-bg button-space pull-right mar-bot-15">
							Create Purchase Request <i class="fa fa-plus-circle"></i></a> -->
							
								  <!--  <a class="pull-right cursor-pointer bread-a-style content-font-size color-font"
								    href="addpurchaserequest?purchaserequestid=0">Create Purchase Request</a> -->
								<div class="width_100 gray-font white-bg content-font-size content-div-height">
								
								<a href="addpurchaserequest?purchaserequestid=0" class="button-bg button-space pull-right mar-bot-15-bp">
							Create Purchase Request <i class="fa fa-plus-circle"></i></a>
							
									<div class="col-md-12 full-padding-20">
										<table  class="hero-settings-table"  style="width:100%" id="purchaseRequestDT">
	                                        <thead>
	                                            <tr>
	                                               <th> # </th>
	                                               <th>Purchasecode</th>                                             
	                                               <th>Purchase Status</th>
	                                               <th>Purchase Date</th>
                                                   <!--  <th>Action</th> -->
	                                            </tr>
	                                         </thead>
	                                    </table>
									</div>	
								</div>
							</div>
						</div>
					</div>
								
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
						         onclick="closeRequestmodal()">Close</button>
								</div>							
								
							</div>
						</div>
					</div>	

                

     <jsp:include page="../../template/footer.jsp" />
</body>
</html>