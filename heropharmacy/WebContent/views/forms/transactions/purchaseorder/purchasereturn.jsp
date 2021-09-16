<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<jsp:include page="../../template/library.jsp" />
<title>Purchase Return</title>
<script type="text/javascript" src="../js/forms/transactions/purchaseorder.js"></script>
</head>
<body onload="loadpurchasereturn();">

<jsp:include page="../../template/header.jsp" />

<div class="clearfix"></div>
					<div class="breadcrumbs"  style="display: <%= request.getParameter("disp") %>">
			            <div class="col-md-4 col-sm-4">
			                <div class="page-header">
			                        <p class="cursor-pointer mar-top-10 bread-a-style head-font-size yellow-font" href="stockdashboard"><strong> Return purchase</strong></p>
			                </div>
			            </div>
			            <div class="col-md-8 col-sm-8">
			                <div class="page-header float-right">
			                    <a class="pull-right cursor-pointer bread-a-style content-font-size color-font" href="dashboard?uid=1">Home / Dashboard</a>
			                </div>
			            </div>
			        </div>
<div class="clearfix"></div>
    
 
                       
                          <!-- Credit Card -->
                          	<div class="col-md-12 overcome-col-padd-10  ">
						<div class="col-md-12 overcome-col-padd-10 ">
							<div class="width_100">
								
								<div class="width_100 gray-font white-bg content-font-size content-div-height">
									<div class="col-md-12 full-padding-20">
                          
                          
								<div class="col-md-12">	
									<div class="col-md-6 form-group">
									<label for="Purchase Receive#">Date</label>
								    <!-- <input class="form-control form-white datepicker" placeholder="Date" type="text" > -->
								     <input class="form-control form-white datepicker" placeholder="Return Date" type="text" id="returndatetext">
                             <input class="form-control form-white" placeholder="Return Date" type="hidden" id="pidtext">
                             <input class="form-control form-white" placeholder="Return Date" type="hidden" id="phidtext">
								    </div>									 
					                <div class="col-md-6 form-group">
									<label for="Receive Date">Return Surcharge</label>
									<!-- <input type="text" class="form-control inpttypestyle"  > -->
									<input class="form-control form-white " type="number" value="0" id="returnchargetext">
                                    </div>	
								</div>
                                    <div class="col-md-12">
                                    <p>Order Items (Please edit the return quantity below.
                                     You can remove the item or set the return quantity to zero if it is not being returned)</p>
                                    </div>
                                     <table id="returnproductDT" class="hero-settings-table">
												  <thead>
													<tr>
														<th>PRODUCT</th>
														<!-- <th>BATCH NO</th> -->
														<th>PURCHASE UNIT PRICE</th>
														<th>AVAILABLE STOCK</th>
                                                        <th>RECEIVED</th>	
													  <!--   <th>BONUS</th> -->
													 <!--  <th>FULL QTY</th>
													  <th>FULL UOM</th>
													  <th>LOOSE QTY</th>
													   <th>LOOSE UOM</th> -->
                                                        <th>RETURNED QTY</th>
                                                        <th>RETURNING QTY</th>
                                                        <th>UOM</th>
                                                       <!--  <th>DISCOUNT</th> 
                                                        <th>TAX</th>-->
                                                        <th>SUBTOTAL</th>
                                                        <th>ACTION</th>
													</tr>
												 </thead>
									
										</table>
                                    <div class="col-md-12">
                                       <label for="note">Notes (For Internal Use) </label>
                                       <input type="text" class="form-control inpttypestyle" placeholder="note" id="returnnotestext" >
                                    </div>
                                
								 
								
								<div class="col-md-12" >
                                        <button type="button" class="btn btn-primary top"  onclick="processreturn();">Submit</button>
										<button type="button" class="btn btn-danger top"  onclick="cancelreturn();">Cancel</button>
			                      </div>
								
						</div>
						</div>
						</div>
						</div>
						</div>
						  

            <style>
						 
						  
						  #example_info{font-size:10px;}
                          #example_wrapper{font-size:10px;}
                          .top{
                          margin-top:20px;
                          }
                          label {
    
    margin-top: 20px;
}
						  </style>           
                    
<jsp:include page="../../template/footer.jsp" />
</body>
</html>
		