<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>View Purchase Order</title>
<jsp:include page="../../template/library.jsp" />
<script type="text/javascript" src="../js/forms/transactions/purchaseorder.js"></script>
<link rel="stylesheet" href="../../heroadmin/resources/css/stylebass.css">
	<link rel="stylesheet" href="../../heroadmin/resources/css/stylej.css">
</head>
<body onload="loadpurchaseorderview();">

<jsp:include page="../../template/header.jsp" />
	
	<div class="clearfix"></div>
	<div class="breadcrumbs"  style="display: <%= request.getParameter("disp") %>">
         <div class="col-md-4 col-sm-4">
			  <div class="page-header">
			       <p class="cursor-pointer mar-top-10 bread-a-style head-font-size yellow-font" href="stockdashboard"><strong>Purchase view</strong></p>
			  </div>
		</div>
			            
	 <div class="col-md-8 col-sm-8">
		  <div class="page-header float-right">
			   <a class="pull-right cursor-pointer bread-a-style content-font-size color-font" href="dashboard?uid=1">Home / Dashboard</a>
		  </div>
	</div>
	</div>
					
	<div class="clearfix"></div>
					
				
				
	<div class="col-md-12 overcome-col-padd-10">
		 <div class="col-md-12 overcome-col-padd-10">
		 <div class="width_100">
		 <div class="width_100 gray-font white-bg content-font-size">
		 <div class="col-md-12 full-padding-20">
									
		 <div class="width_25" >
		 <% if(session.getAttribute("usertypestr").equals("3")) { %>
		 <div class="width_100" >
			<a href="addpurchaseorder?purchaseorderid=0" class="button-bg button-space pull-right mar-bot-15">New <i class="fa fa-plus-circle"></i></a>
		 </div><% } %>
									 
		<div class="width_100 scrolly">
							
			<div class="rowNor ">
			         <input type="hidden" value="${firstid }" id="firstidtext">  
				 <input type="hidden" id="totalpurchasetext" value="${viewpurchaseorderList.size() }"></input>
				 <input type="hidden" id="pidtext"></input>
								            
			<table class="table table-hover selectCheckTabel" id="txrtable">
				   <tbody style="overflow: scroll;display: block;height: 450px;overflow-x: hidden;" id="style-5">
				   
				   <c:forEach items="${viewpurchaseorderList}" var="viewpurchase" > 
								            
		<tr> 
			<td width="20px"> 
								               
					<input name="purchaseorderidcheck" type="radio" id="purchaseorderidcheck${viewpurchase.index }" onclick="getPurchaseid('${ viewpurchase.headerid}','${ viewpurchase.receive_status }','${viewpurchase.index }','${viewpurchaseorderList.size() }')"> 
					<input type="hidden" value="${ viewpurchase.purchasecode}" id="purchasecodetext${viewpurchase.index }">
				
			</td> 
				
			<td onclick="getPurchaseList('${ viewpurchase.headerid}','${ viewpurchase.purchasecode}')"> 
					<div>${ viewpurchase.suppliername} <span class="pull-right"> ${ viewpurchase.totalamt} </span> </div> 
					<div class="leftTableAdd">
						 <a> ${ viewpurchase.purchasecode} </a> ${ viewpurchase.purchasedate}  
						 <span class="text-success pull-right">${ viewpurchase.receivestatusdesc}</span>
				    </div>
								                  
					<input type="hidden" id="headerid${viewpurchase.index }" value="${ viewpurchase.headerid }">
					<input type="hidden" id="recvstatus${viewpurchase.index }" value="${ viewpurchase.receive_status }">
			</td>
		 </tr>
								            
				    </c:forEach> 
					</tbody>                               
			 </table>

     
     <div class="paginationView" align="right">
              <span class="tabelCount pull-left">Total Count : ${purchaselistsize} </span> 
              <span class=" pull-right"> 
                
                <select class="selectNor " onchange="gettxndetails(this.value)">
                        <c:forEach var="page" begin="1" end="${((purchaselistsize % 10) == 0) ? (purchaselistsize / 10) : (purchaselistsize / 10) + 1 }">
                                    <option value="${page }">Page${page }</option>
                         </c:forEach>     
                </select>
              </span>
     </div>         
        
        </div>
		</div>
		</div>
									
									
									
	<div class="width_75 gray-font white-bg content-font-size content-div-heightnw"  id="style-5"> 
	<div class="col-md-12" >
		 <div class="col-md-4" >
			  <h3>  <strong id="postrongid"></strong></h3>
		 </div>
										
	<div class="col-md-8 margin-bottom-12">	
		 <button class="button-bg button-space mar-bot-15" onclick="generatepurchasereport();"><i class="fa fa-print"></i></button> 
		 <% if(session.getAttribute("usertypestr").equals("2")  || session.getAttribute("usertypestr").equals("10")) { %>
		 	<!-- <button class="button-bg button-space mar-bot-15" type="button"  onclick="receivestockprocess();" id="recvbtn" >Receive Stock</button> -->
		 	<!-- <button class="button-bg button-space mar-bot-15" data-toggle="modal" data-target="#purchase_payment" >Add Payment</button> -->
		 
		 	<!-- <button class="button-bg button-space mar-bot-15" onclick="redirecttoReturn();">Return</button> -->
		 <% } %>
		 
		 <a class="button-bg button-space  pull-right" href="purchaseorderhistory" class="" >Go Back</a> 
	</div>
	</div>
										
	<div class="clearfix"></div>
										
	<div class="col-md-12">
	     <ul class="nav nav-tabs">
			 <li class="nav active"><a data-toggle="tab" href="#Receives">Receives</a></li>
			 <li class="nav"><a data-toggle="tab" href="#Bills">Bills</a></li> 
		 </ul>

	<div class="tab-content">
		 <div id="Receives" class="tab-pane active pill-space">
			  <div class="col-md-12" >
				   <span class="select">Selected Bill No:</span>
				   <label id="selectedbillnotext" style="color: red;font-size:18px"></label>
			  </div>
												
	<table id="receivelistDT" class="hero-settings-table hover-row" style="width:100%">
		   <thead>
				 <tr>
					 <th>PURCHASE SERVICE#</th>
					 <th>RECEIVED ON</th>
					 <th>BILLS#</th>
					 <th>BILL AMOUNT</th>
					 <th>ACTION</th>  									
				</tr>
		   </thead>
												 
	</table>
				
			
	<div class="main-template" style="
    margin-top: 0;
    border: none;
    padding: 0;
"
	>
		 <div class="main-template-body">
			<!--   <table style="width:100%;table-layout: fixed;">
			   
			     <tbody>
						<tr>
							<td style="vertical-align: top; width:50%;">
		
		                         <div> <img style="width: auto; height: auto; max-width: 300px; max-height: 100px;" 
		                         id="logo_content" src="/heroadmin/forms/HeroImageView?index=0"></div>
																		
							</td>
					
					<span class="pcs-orgname"><b></b></span><br>
                          <span class="pcs-label">
                          <span style="white-space: pre-wrap;word-wrap: break-word;" id="tmp_org_address"></span>
                          </span>
					
					<td style="vertical-align: top; text-align:right;width:50%;">
						<span class="main-entity-title">PURCHASE ORDER</span><br>
						<span id="tmp_entity_number" style="font-size: 10pt;" class="pcs-label"></span>
					</td>												
																	   
						 </tr>											
					</tbody>											
				</table>	 -->										
														
												
	<!-- 	<table style="width:100%;margin-top:30px;table-layout:fixed;">
			<tbody>
				<tr>
					<td style="width:55%;vertical-align:bottom;word-wrap: break-word;">
						<div><label id="tmp_billing_address_label">Supplier Details </label>  <br>
							 <span class="pcs-customer-name" id="zb-pdf-customer-detail">
						     <a><label id="suppliernametext"></label>  </a></span><br><br>
						</div>          
						
						 <label style="font-size: 10pt;" id="tmp_shipping_address_label" class="pcs-label"></label><br>
						 
						  <span style="white-space: pre-wrap;" id="tmp_shipping_address"> </span><br>
						  <span style="white-space: pre-wrap;" id="tmp_shipping_city"></span><br>
						  <span style="white-space: pre-wrap;" id="tmp_shipping_state"></span><br>
						  <span style="white-space: pre-wrap;" id="tmp_shipping_zipcode"></span><br>
						  <span style="white-space: pre-wrap;" id="tmp_shipping_country"></span><br>
					</td>

			<td style="vertical-align:bottom;width: 45%;" align="right">
				<table style="float:right;width: 100%;table-layout: fixed;word-wrap: break-word;" cellspacing="0" cellpadding="0" border="0">
					<tbody>
							<tr>
								<td style="text-align:right;padding:5px 10px 5px 0px;font-size:10pt;width:60%;">
									<span class="pcs-label">Ordered Date:</span>
								</td>
																				  
								<td style="text-align:right;width:40%;">
									<span id="tmp_entity_date"></span>
								</td>
							</tr>

							<tr>
								<td style="text-align:right;padding:5px 10px 5px 0px;font-size: 10pt;width:60%;">
									<span class="pcs-label" >Received Date :</span>
								</td>
																				  
								<td style="text-align:right;width:40%;">
									<span id="tmp_due_date"></span>
								</td>
						    </tr>
																			  
							<tr>
								<td style="text-align:right;padding:5px 10px 5px 0px;font-size: 10pt;width:60%;">
									<span class="pcs-label" >Ref :</span>
								</td>
																				  
								<td style="text-align:right;width:40%;">
									<span id="tmp_ref_number"></span>
								</td>
							</tr>
					</tbody>
			</table>
			</td>
			</tr>
			</tbody>
			</table> -->
											     
														
<table style="width:100%;margin-top:20px;table-layout:fixed;" id="itemlistDT" 
                class="tablenew" cellspacing="0" cellpadding="0" border="0">
         
 <thead>
 <tr style="height:32px;"> 
                    
     <th  style="padding:5px 0 5px 5px;text-align: center;word-wrap: break-word;width: 5%;" class="pcs-itemtable-header">#</th>
     <th  style="padding:5px 10px 5px 20px;word-wrap: break-word;" class="pcs-itemtable-header pcs-itemtable-description">Item &amp; Description</th>
     <th  style="padding:5px 10px 5px 5px;word-wrap: break-word;width: 11%;" class="pcs-itemtable-header" align="right">  Bill No</th>
     <th  style="padding:5px 10px 5px 5px;word-wrap: break-word;width: 15%;" class="pcs-itemtable-header"> Purchase Qty</th>
  <!--      <th  style="padding:5px 10px 5px 5px;word-wrap: break-word;width: 8%;" class="pcs-itemtable-header" align="right"> Bonus Qty </th>-->
     <th  style="padding:5px 10px 5px 5px;word-wrap: break-word;width: 8%;" class="pcs-itemtable-header" align="right">   Rate </th>
     <th  style="padding:5px 10px 5px 5px;word-wrap: break-word;width:80px;" class="pcs-itemtable-header" align="right">   Amount </th>
                                         
 </tr>
<!--                                 
  <tr>
     <td style="text-align: center;padding:5px 10px 5px 5px;word-wrap: break-word;width: 6%;" class="pcs-itemtable-header">Full</td>
     <td style="text-align: center;padding:5px 10px 5px 5px;word-wrap: break-word;width: 6%;" class="pcs-itemtable-header">Loose</td>
     <td style="text-align: center !important;padding:5px 10px 5px 5px;word-wrap: break-word;width: 23%;" class="pcs-itemtable-header">Total Qty</td>
  </tr>  -->
</thead>
                               
</table>
											     
			
														 <div style="width: 100%;margin-top: 1px;">
                              <div style="width: 45%;padding: 3px 10px 3px 3px;font-size: 9pt;float: left;">
                                <div style="white-space: pre-wrap;"></div>
                              </div>
                              <div style="width: 50%;float:right;">
                                <table cellspacing="0" width="100%" border="0">
                                  <tbody>
                                    <tr>
                                          <td style="padding: 5px 10px 5px 0;" valign="middle" align="right">Sub Total</td>
                                          <td id="tmp_subtotal" style="width:120px;padding: 10px 10px 10px 5px;" valign="middle" align="right">
                                          <label id="subtotallabel"></label>
                                          </td>
                                    </tr>
                                    <tr style="height:10px;">
                                      <td style="padding: 5px 10px 5px 0;" valign="middle" align="right">
                                      Discount (&nbsp;<label id="discountvaluelabel"></label>&nbsp;<label id="discounttypelabel"></label>&nbsp;) 
                                      </td>
                                      <td style="width:120px;padding: 10px 10px 10px 5px;" valign="middle" align="right">
<label id="discountamtlabel"></label>
</td>
                                    </tr>
                                    <tr style="height:10px;">
                                      <td style="padding: 5px 10px 5px 0;" valign="middle" align="right">Tax </td>
                                      <td style="width:120px;padding: 10px 10px 10px 5px;" valign="middle" align="right">
<label id="taxamtlabel"></label>
</td>
                                    </tr>
<tr style="height:10px;">
                                      <td style="padding: 5px 10px 5px 0;" valign="middle" align="right">Shipping Cost </td>
                                      <td style="width:120px;padding: 10px 10px 10px 5px;" valign="middle" align="right">
<label id="shippingamtlabel"></label>
</td>
                                    </tr>
                                    <tr>
                                      <td style="padding: 5px 10px 5px 0;" valign="middle" align="right"><b>Total </b></td>
                                      <td id="tmp_total" style="width:120px;;padding: 10px 10px 10px 5px;" valign="middle" align="right">
                                      <span id="currency_code"></span></span><label id="totalalbel"></label>
                                      </td>
                                    </tr>
                                  </tbody>
                                </table>
                              </div>
                              <div style="clear: both;"></div>
                            </div>
													</div>	
														
												</div>																				
											</div>
											
											<div id="Bills" class="tab-pane pill-space">
											
											
											<!-- 	<div class="col-md-12" >
													<p><span class="select">Selected Bill No</span> <strong><span style="font-size:18px" class="bill">BILL-13</span></strong></p>
												</div>   -->
												
												<table id="billDT" class="table table-striped table-bordered nowrap example2" style="width:100%">
												  <thead>
													<tr>
														<th> Bill# </th> 
														<th> Supplier Invoice no </th>  
                                         <th> Date </th>  
                                         <th> Status </th>  
                                         <th> Payment Type  </th>  
                                         <th> Cheque No  </th>
                                         <th> Amount  </th>  
                                         <th> Paid Amount  </th>
                                         <th> Balance Due </th> 
                                         <th> Paid At </th>
													</tr>
												  </thead>
													<!-- <tbody>
														<tr>
															<td>BILL-11</td>
															<td>13/2/2018</td>
															<td>Paid</td>
															<td>Cash</td>
															<td></td>
															<td>438.53</td>
															<td>438.53</td>
															<td>0</td>
															<td>13/2/2018 - 15:09</td>
														</tr>
													</tbody> -->
												</table>
											
											</div><!-- .nbill -->
										  </div>
										</div>
										
									</div>

								</div>
							</div> 

							</div>
						</div> 
						 <!-- <script type="text/javascript">
				$(document).ready(function(){
					
				jQuery(".new").click(function(){
					jQuery('#myModal').modal({backdrop: true});
				   
					
					});	
					
					jQuery(".edit").click(function(){
					jQuery('#myPopup').modal({backdrop: true});
				   });	
				});
				</script>   -->
				<!-- <script type="text/javascript">
				$(document).ready(function(){
					
				jQuery(".addPaymentbtn").click(function(){
					jQuery('#purchase_payment').modal({backdrop: true});
				   
					
					});	
					
				});
				</script>  -->
					  <!-- Main content ends here -->
<div class="modal fade" id="purchase_payment" tabindex="-1" role="dialog" aria-hidden="true">
  <div class="modal-dialog topZero">
    <div class="modal-content">
      <div class="modal-header">
 <h4 class="modal-title">Add Payment <button type="button" class="close" data-dismiss="modal">&times;</button></h4>
 
      </div>
      <div class="modal-body">
        <p> Your batch numbers are set on auto-generate mode to save your time. Do you want to change settings? </p>
        <div class="row">
          <div  class="col-sm-6 form-group">
            <label>Date</label>
            <input placeholder="" class="form-control form-white datepicker" type="text" id="paymentdatetext">
          </div>
          <div  class="col-sm-6 form-group">
            <label>Reference No</label>
            <input placeholder="" class="form-control form-white" readonly="readonly" type="text" id="payment_refno">
          </div>
          <div  class="col-sm-6 form-group">
            <label>Supplier Invoice No</label>
            <input placeholder="" class="form-control form-white" type="text" id="supplierInvoiceNo">
          </div>
          <div  class="col-sm-6 form-group">
            <label>To be Paid</label>
            <input placeholder="" class="form-control form-white " type="number" id="payingamount">
            <input placeholder="" class="form-control form-white " type="hidden" id="tobepayamount">
            <input placeholder="" class="form-control form-white " type="hidden" id="paymentheaderid">
          </div>
          <div  class="col-sm-12 form-group">
            <label>Paying By</label>
           <div id="supplierdefault" class=" fomr-constant-div-supplier">
              <input placeholder="" class="form-control form-white " type="text" id="paymodename" readonly="readonly">
            </div>
             <div id="supplieroptional">
            <select class="selectNor form-control form-white" style="width:60% !important" onchange="selectpaymenttype()" id="paymenttypeselect" >
              <c:forEach items="${paymenttypeList}" var="paymenttype" > 
            <option value="${paymenttype.value }"> ${paymenttype.label } </option>
            </c:forEach>
            </select>
          </div>
          </div>
          
           <div class="col-sm-6 form-group" id="creditedwithin"  >
            <label>Credited Within</label>
            <input placeholder="" class="form-control form-white " type="text"  id="creditedwithintext"  >
           </div>
          <div id="default"  >       
          <div  class="col-sm-6 form-group" id="bankname">
            <label>Bank Name</label>
            <select class="selectNor form-control form-white"  id="banknameselect">
              <c:forEach items="${bankList}" var="bank" > 
            <option value="${bank.value }"> ${bank.label } </option>
            </c:forEach>
            </select>
          </div>
          
          <div  class="col-sm-6 form-group" id="chequeno">
            <label>Cheque No</label>
       
            <input placeholder="" class="form-control form-white " type="text" id="chequenotext">
          </div>
         </div>   
          <div  class="col-sm-12 form-group">
            <label>Note</label>
            <textarea  class="form-control form-white "></textarea> 
          </div>
        </div>
      </div>
      <div class="modal-footer alignCenter" align="center ">
        <div class="form-group " align="center">
             <button type="button" class="btn btn-primary " data-dismiss="modal" onclick="updatereceiveorderpayment();" id="paymentsavebtn">Save</button>
              <button type="button" class="btn btn-normal " data-dismiss="modal">Cancel</button>
        </div>
      </div>
    </div>
  </div>
</div>



<style>
.width_100 {
height:100%;}
.nav-tabs {
margin-top:10px;}
.main-entity-title {
    font-size: 17pt;
    color: #000000;
}

table.dataTable.no-footer {
   
    cursor: pointer;
}
</style>				
					
					
<jsp:include page="../../template/footer.jsp" />             

</body>
</html>