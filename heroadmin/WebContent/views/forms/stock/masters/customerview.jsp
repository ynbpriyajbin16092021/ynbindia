<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Customer View</title>
<script type="text/javascript" src="../js/forms/masters/masters.js"></script>
</head>
<body onload="loadcustomerview();">



<jsp:include page="../../template/header.jsp" />
				  <!-- Main content starts here -->
				<div class="card">
                        <div class="card-header">
                            <strong class="card-title"><i class="fa fa-shopping-bag"></i> Customer List</strong>
						</div>
							<div class="card-body" style="padding:0;">
                          <!-- Credit Card -->
							  <div id="pay-invoice">
								   <div class="width_100" >
										 <br />
										 <div class="width_25 " >
										 <div class="width_50" >
									       
										 <a href="addcustomer" class="btn btn-secondary pull-right btnmove">New <i class="fa fa-plus-circle"></i></a>
										 
										 </div>
										 <div class="width_100">
										      <input id="customertablesize" value="${customerList.size() }" type="hidden">
                                                         <table class="table table-hover selectCheckTabel" id="customertable">
                                                                  <tbody>
                                                                      <c:forEach items="${customerList}" var="customer" >
                                                                             <tr> 
                                                                                <td width="50px"> <input type="radio" id="customerradio${customer.index }" value="${customer.value }" name="customerradio"
                                                                                             onclick="displayOverviewDetails(this.value)"> </td> 
                                                                                <td> ${customer.label } </td>
                                                                             </tr>
                                                                      </c:forEach>
                                                                    </tbody>
                                                          </table>

								                            <div class="paginationView" align="right">
								                              		<span class="tabelCount pull-left tableCountCustomer">Total Count : ${fullCustomerListSize } </span> 
								                              		<span class=" pull-right"> 
								                              				<div id="sortSelect" >
												                                <select class="selectNor" onchange="getCustomerList(this.value)">
												                                    <!-- <option>10 per page</option>
												                                    <option>20 per page</option>
												                                    <option>30 per page</option>
												                                    <option>50 per page</option> -->
												                                    <c:forEach var="page" begin="1" end="${((fullCustomerListSize % 10) == 0) ? (fullCustomerListSize / 10) : (fullCustomerListSize / 10) + 1 }">
												                                    	<option value="${page }">Page${page }</option>
												                                	</c:forEach>                                      
												                                </select>
								                                			</div>
								                              		</span>
								                           	 </div>

										</div>
										</div>
										 <div class="width_75 bordercls" >
											<div class="col-md-12" >
											<div class="col-md-4" >
											<h3>  <strong id="customerNameHeadingText">Customer Name</strong></h3>
											</div>
											
												<div class="col-md-8 ">
													 <div class="pull-right">
						                                         <a href="customer" class="btn btn-secondary pull-right btnnew">
                                                                   Back
                                                                 </a>
                                                      </div>
											</div> 
											</div>
											<div class="clearfix"></div>
											<div class="col-md-12">
											 <ul class="nav nav-tabs">
												<li class="nav"><a data-toggle="tab" href="#overview">Overview</a></li>
												<li class="nav"><a data-toggle="tab" href="#Salesinvoices">Salesinvoices</a></li>
												<li class="nav"><a data-toggle="tab" href="#Payments">Payments</a></li>
												<li class="nav"><a data-toggle="tab" href="#Orders">Orders</a></li>
											
											  </ul>
										   <div class="tab-content">
												 <div class="tab-pane active" id="overview">
													<div class="col-md-12">
														<div class="col-md-6 stydiv">
														    <div class="contactDetail">
																<div class="panelContact">
																  <div class="font-md" id="firstnametext"></div>
																  <div class="row">
																	<div class="col-md-2">
																		  <div class="cp-pic">
																			  <svg class="icon icon-lg" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512"><path d="M405.8 355.3s-79-22.6-87.8-52.8c-8.8-30.2 26.3-86.7 26.3-86.7s52.7-52 17.6-77.8c0 0 35.1-138-105.4-138-141 0-105.7 137.8-105.7 137.8-35.3 25.8 17.6 77.5 17.6 77.5s35.2 56 26.4 86.1c-8.8 30.2-88.1 53.9-88.1 53.9C-16.8 363.9.9 512.5.9 512.5h510.3c-.1 0 17.5-148.6-105.4-157.2z"></path></svg>
																		  </div>
																	</div>
																	<div class="col-md-10">
																	  <strong id="custnametext"></strong> <br>
																	  <div id="emailtext"></div>
																	  <div id="mobilenotext"></div>
																	  
																	</div>
																  </div>
																</div>


																	<div class="panelContact">
																	  <div class="font-md" data-toggle="collapse" data-target="#address">Address  <i class="fa fa-angle-up pull-right"  aria-hidden="true" ></i>
																	  </div>
																	  <div id="address" class="collapse row in ">
																		
																		<div class="col-md-12">
																		   <div> 
																			 <div> <b> Billing Address </b></div>
																			<div id="addressdiv">
																			</div>
																			</div>
																		 </div>
																	  </div>
																	  
																	 
																	</div>



                                    <div class="panelContact">
                                      <div class="font-md" data-toggle="collapse" data-target="#OtherDet">Other Details  <i class="fa fa-angle-up pull-right" aria-hidden="true"></i>
                                      </div>
                                      <div id="OtherDet" class="collapse row  in">
                                        
                                        <div class="col-md-12">
                                           <table class="table">
                                             <tr>
                                                  <td>Currency Code</td>
                                                  <td class="green">IND</td>
                                              </tr>
                                              <tr>
                                                  <td>Payment Terms</td>
                                                  <td>Due On Receipt</td>
                                              </tr>
                                              <tr>
                                                  <td>Created Source</td>
                                                  <td id="createdbytext">superadmin</td>
                                              </tr>
                                           </table>
                                        </div>
                                      </div>
                                    </div>


                                  </div>
																
                                                        </div>
                                                        <style>
				                                           .table.txn-info-table 
																{
																    table-layout: fixed;
																	margin-bottom: 0;
																	border: 1px solid #EBEBEB;
																	background-color: #F9FFFE;
																 }
																.table.txn-info-table.table tbody > tr > td 
																{
																	border-top: 1px solid #ecf7f5;
																	padding: 15px 35px 10px;
																}
																.font-xlg 
																{
																    font-size: 25px;
																}
																.text-orange 
																{
																	color: #F26C4F;
																}
																.table.txn-info-table.table tbody > tr > td:last-child
																{
																	border-left: 1px solid #ecf7f5;
																	padding: 15px 15px 5px;
																}
																.table.txn-info-table.table tbody > tr > td 
																{
																	border-top: 1px solid #ecf7f5;
																	padding: 15px 35px 10px;
																}
																table tbody tr 
																{
																	background-color: #ffffff;
																	cursor: pointer;
																}
                                                        </style>
														<div class="col-md-6"><br>
															
															<table class="table txn-info-table">
                                      <tbody>
                                      <tr class="receivable-section">
                                      <td rowspan="3" class="txn-info-sections">
                                      <div id="ember1704" class="ember-view"> Receivables
                                      <div>
                                      <div class="font-xlg text-orange over-flow"><strong id="receivablestext"></strong></div>
                                      </div>
                                      </div>
                                      </td>
                                      <td colspan="2">
                                      <label class="pull-left text-void">Items to be packed</label>
                                      <label class="pull-right text-draft font-md" data-ember-action-1705="1705">0</label>
                                      </td>
                                      </tr>
                                      <tr class="receivable-section">
                                      <td colspan="2">
                                      <label class="pull-left text-void">Items to be shipped</label>
                                      <label class="pull-right text-draft font-md" data-ember-action-1706="1706" id="shippedtext"></label>
                                      </td>
                                      </tr>
                                      <tr class="receivable-section">
                                      <td colspan="2">
                                      <label class="pull-left text-void">Items to be invoiced</label>
                                      <label class="pull-right text-draft font-md" id="invoicecounttext"> </label>
                                      </td>
                                      </tr>
                                      </tbody>
                                      </table>
                                                            															
														</div>
													</div>
												</div>
												
												<div id="Salesinvoices" class="tab-pane ">
													  <table id="salesDT" class="table tabel table-hover" width="100%" >
							                              <thead>
							                                  <tr>
							                                     <th> Type </th>  
							                                     <th> Number </th>  
							                                     <th> Date </th>  
							                                     <th> Due Date  </th>
																 <th> Total </th>
																 <th> Balance  </th>
							                                     <th> Status </th>  
							                                     <!-- <th width="160px"> Action </th> -->  
							                                  </tr>
							                              </thead>
							                             
							                          </table>
												</div>
												<div id="Payments" class="tab-pane ">
													  <table id="paymentDT" class="table tabel table-hover" width="100%" >
							                              <thead>
							                                  <tr>
							                                     <th> POS No </th>  
							                                     <th> Tax Amount </th>  
							                                     <th> Discount Amount </th>  
							                                     <th> Grand Total  </th>  
							                                     <th> Paid Amount  </th>  
							                                     <th> Balance Amount </th>  
							                                  </tr>
							                              </thead>
							                              <tbody>
							                                 
							                              </tbody>
							                          </table>
												</div>
												
												<div id="Orders" class="tab-pane ">
													 <table id="ordersDT" class="table tabel table-hover" width="100%" >
							                              <thead>
							                                  <tr>
							                                     <th> Batch No </th>  
							                                     <th> Purchase QTY </th>  
							                                    <!--  <th> AVL QTY </th>  
							                                     <th> MFD  </th>  
							                                     <th> EXP  </th>   -->
							                                     <th> Unit Price </th>  
							                                     <th> Tax </th>  
							                                     <th> Total </th>  
							                                     <!-- <th width="160px"> Action </th>   -->
							                                  </tr>
							                              </thead>
							                              <tbody>
							                                  
							                              </tbody>
							                          </table>
												</div>
												
												
											  </div>
											</div>
											
										</div>

									</div>
							</div> 
                    </div>
				</div> 
					  
 
<jsp:include page="../../template/footer.jsp" />
</body>
</html>
