<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
<meta charset="utf-8" />
<title>POS</title>
<jsp:include page="../template/library.jsp" /> 
<link href="../resources/css/template/theme.css" rel="stylesheet">
<link href="../resources/css/template/ui.css" rel="stylesheet">
<link href="../resources/css/plugin/mcustom_scrollbar.min.css" rel="stylesheet">
<link href="../resources/css/plugin/animate.min.css" rel="stylesheet">

<link rel="stylesheet" href="../resources/css/my-style.css">
<link rel="stylesheet" href="../resources/css/ionicons.css">
<link rel="stylesheet" href="../resources/css/pos-style.css">

<script type="text/javascript" src="../js/forms/sales/pos.js"></script>
	  
<style type="text/css" title="currentStyle">
.ui-autocomplete.ui-front.ui-menu{
display:none !important;
}
</style>

<script src="../js/forms/sales/posplugins/jquery.scannerdetection.compatibility.js"></script>
<script src="../js/forms/sales/posplugins/jquery.scannerdetection.js"></script>

</head>
<script>
$(document).ready(function(){
	barcodeclick=0;
	getBarCodeValue();
})

</script>
<body onload="loadpos()">
<jsp:include page="../template/header.jsp" />

<input type="hidden" id="uompackingselect" />
<div class="modal fade 	" id="addCustomer"  role="dialog" >
	<div class="modal-dialog" style="width:700px">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" id="customertoggleclosebtn">&times;</button>
				<h4 class="modal-title">Add Customer</h4>
			</div>
			<div class="modal-body">

   <form class="form-horizontal">
            <div class="col-sm-6">
              <div class="displayTabel ">
                <div class="form-group">
                  <label class="col-sm-12 marginBot control-label">FIRST NAME</label>
                  <div class="col-sm-12">
                    <input class="form-control form-white" placeholder=" FIRST NAME " type="text" id="firstnametext">
                      <input class="form-control form-white" type="hidden" id="custidtext" value="0">
                      <input class="form-control form-white" type="hidden" id="oprntext" value="INS">
                  </div>
                </div>
              </div>
              <div class=" displayTabel ">
                <div class="form-group">
                  <label class="col-sm-12 marginBot control-label">LAST NAME</label>
                  <div class="col-sm-12">
                    <input class="form-control form-white " placeholder=" LAST NAME " type="text" id="lastnametext">
                  </div>
                </div>
              </div>
			</div>
			 <div class="col-sm-6">
              <div class=" displayTabel ">
                <div class="form-group">
                  <label class="col-sm-12 marginBot control-label">E-MAIL</label>
                  <div class="col-sm-12">
                    <input class="form-control form-white " placeholder=" E-MAIL " type="email" id="emailidtext">
                  </div>
                </div>
              </div>
              <div class=" displayTabel ">
                <div class="form-group">
                  <label class="col-sm-12 marginBot control-label">MOBILE</label>
                  <div class="col-sm-12">
                    <input class="form-control form-white " placeholder=" MOBILE " type="number" id="mobilenotext">
                    
                  </div>
                </div>
              </div>
            </div>
            <div class="col-sm-6" style="display:none">
              <div class="displayTabel ">
                <div class="form-group">
                  <label class="col-sm-12 marginBot control-label">GROUP</label>
                  <div class="col-sm-12">
                    <%-- <select  class="form-control form-white" id="groupselect">
                      <c:forEach items="${customergroupList}" var="customergroup" >
                          <option value="${customergroup.value}">
            ${customergroup.label}
        </option>            
                          </c:forEach>
                      </select> --%>
                      <input type="hidden" id="groupselect" value="1">
                      <label>Walk-In</label>
                  </div>
                </div>
              </div>
            </div>
            
            <div class="col-sm-6" style="display:none">
              <div class="displayTabel ">
	             <div class="form-group">
	                  <label class="col-sm-12 marginBot control-label">ACTIVE</label>
	                  <div class="col-sm-12">
	                     <div class="onoffswitch">
	                        <input name="onoffswitch" class="onoffswitch-checkbox" id="myonoffswitch1" checked="" type="checkbox">
	                        <label class="onoffswitch-label" for="myonoffswitch1">
	                        <span class="onoffswitch-inner">
	                        <span class="onoffswitch-active"><span class="onoffswitch-switch">ON</span></span>
	                        <span class="onoffswitch-inactive"><span class="onoffswitch-switch">OFF</span></span>
	                        </span>
	                        </label>
	                  	</div>
	                </div>
	               </div>
            </div>
            </div>
            
            <div class="displayTabel">
              <label class="col-sm-12 marginBot control-label">Address: </label>
            </div>
            <div class="col-sm-12">
              <table class="hero-settings-table" id="custaddresstable">
                <thead>
                  <tr>
                    <th width="400px"> Street address </th>
                    <th width="250px">City</th>
                    <th>State</th>
                    <th>Country </th>
                    <th>Zip Code </th>
                    <th>Action </th>
                  </tr>
                </thead>
                <tbody>
                   


                </tbody>
              </table>
            </div>
            <div class="col-sm-12 full-padding-20">
                <a href="#" onclick="addcustomertolist();"><i class="fa fa-plus" aria-hidden="true"></i> Add Address</a> 
            </div>
            <div class="displayTabel">
              
            </div>
            <div class="col-sm-12 full-padding-20" align="center">
              <input type="hidden" value="<%=session.getAttribute("uid")%>" id="inventoryuseridtext">
              <button type="button" class="btn btn-primary" onclick="savecustomer();">Save</button>
              <button type="button" class="btn btn-normal ">Cancel</button>
            </div>
          </form>
			</div>
			<div class="clearfix"></div>
		</div>
	</div>
</div>

					<div class="clearfix"></div>
					<div class="modal fade 	" id="Discount" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog">
	  <div class="modal-content">
	  <div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">&times;</button>
			<h4 class="modal-title">Receipt discount</h4>
		  </div>
		  
	<div class="modal-body" >
		
		<div class="col-md-9">
			<div class="form-group">
				<label>Select Type</label>
				<select id="discselect" class="form-control" onchange="discchange()">
              <option value="P"> Percentage </option>
              <option value="F"> Fixed </option>
              
            </select>
			</div>
		</div>
		
		<div class="col-md-9">
			<div class="form-group">
				<label>Discount Value</label>
				<input class="form-control" placeholder="Enter Discount Amount" type="number" id="discamttext" value="0" >
			</div>					
		</div>
		<div class="col-md-6">
			<div class="form-group">
				<button  class="btn btn-primary" type="button"   onclick="savediscountcalculate()">Save</button>
			</div>
		</div>
	  </div>
	 <div class="clearfix"></div>
	</div>
</div>
</div>
					<%-- <div class="breadcrumbs"  style="display: <%= request.getParameter("disp") %>">
			            <div class="col-md-4 col-sm-4">
			                <div class="page-header">
			                        <p class="cursor-pointer mar-top-10 bread-a-style head-font-size yellow-font" ><strong><i class="fa fa-lightbulb-o "></i> Point Of Sale</strong></p>
			                </div>
			            </div>
			            <div class="col-md-8 col-sm-8">
			                <div class="page-header float-right">
			                    <a class="pull-right cursor-pointer bread-a-style content-font-size color-font" href="dashboard?uid=1">Home / Dashboard</a>
			                </div>
			            </div>
			        </div>
					<div class="clearfix"></div> --%>


					<div class="col-md-12 overcome-col-padd-10  ">
						<div class="col-md-12 overcome-col-padd-10 ">
						
							<div class="width_100">
								 <h3 class="store-name-pos white-font color-bg full-padding-10">
				                        <c:forEach items="${storeList}" var="store" >
								         <strong id="storenametext">${store.label }</strong> ${store.value }
								        </c:forEach>
							         </h3>
							</div>
							
	<div class="width_50 ">
	
			<div class="myleftpanelorder">
			<label>Pos Tax Type :</label>
				<label class="radio-inline"><input style="margin-top: 6px;" type="radio" name="taxper" value="exclusive" >Exclusive Tax</label>
				<label class="radio-inline"><input style="margin-top: 6px;" type="radio" name="taxper" value="inclusive">Inclusive Tax</label>
				<label class="radio-inline"><input style="margin-top: 6px;" type="radio" name="taxper" value="both">Both</label>
				<%-- <label >  | </label>
				<label class="myyellowfont" ><input class="hero-form-control form-white datepicker " placeholder="Receive Date" type="text" id="receivedate" value="${receivedate }"></label> --%>
			</div>	
	</div>
							<div class="width_100">
							
									<div class="posFullWidth">
		
		<div class="billPOS">
			<div class="leftPoi">
		
		
			
			<div class="samHeight">
				<div class="TetFildSearch">
					<div class="inputFildHalf">
						<div class="input-group stylish-input-group">
							<input type="text" class="form-control CustomerSearchInput akposbot" id="customersearchtext" placeholder="Customer"  autocomplete="off">
							<input type="hidden" class="form-control CustomerSearchInput" id="customeridtext" value="0">    
							<input type="hidden" class="form-control CustomerSearchInput" id="customermobiletext" value="0">
							<input type="hidden" class="form-control CustomerSearchInput" id="customeraddressidtext" value="0">
							<input type="hidden" class="form-control CustomerSearchInput" id="posidtext" value="0">
							<input type="hidden" class="form-control CustomerSearchInput" id="oprntext" value="INS">
							<input type="hidden" class="form-control CustomerSearchInput" id="currsymboltext" value="<%= session.getAttribute("currencyname") %>">
							<input type="hidden" class="form-control CustomerSearchInput" id="currdecimaltext" value="<%= session.getAttribute("currencydecimal") %>">
							<input type="hidden" class="form-control CustomerSearchInput" id="storeidtext" value="<%= session.getAttribute("storeid") %>">
							
							<span class="input-group-addon" onclick="closeAuto()">
								<i class="fa fa-search akpossearch" aria-hidden="true"></i>  
							</span>
						</div>
						<div class="CustomerSearch">
							<div class="arrow"></div>
							<table class="table" id="customerDT">
								<thead>
									<tr>
										<th trans="" class="bold" data-default-translation="Name">Name</th>
										<th trans="" class="bold" data-default-translation="E-mail">E-mail</th>
										<th class="hidden-step-1 bold" trans="" data-default-translation="Phone">Phone</th>
										<th class="hidden-step-1 bold" trans="" data-default-translation="Card number">Location</th>
										<th></th>
									</tr>
								</thead>
								
							</table>								
						</div>							
					</div>
					<div class="inputFildHalf">
						<div class="input-group stylish-input-group">
							<input type="text" class="form-control productSearchInput akposbot"  placeholder="Product" id="productsearchtext" class="productbartext">
							
							<input type="hidden"   placeholder="Product" id="productidtext">
							<input type="hidden" value="<%=session.getAttribute("uid")%>" id="inventoryuseridtext">
							<span class="input-group-addon"  onclick="closeAuto()">
								<i class="fa fa-search akpossearch" aria-hidden="true"></i>  
							</span>
						</div>
						<div class="ProdectSearch">
							<div class="arrow"></div>
							<table class="table" id="productDT">
								<thead>
									<tr>
										<th width="200">Product name</th>
										<th>MFU</th>
										<th>BATCH</th>
										<th>EXP </th>
										<th>Cost</th>
										<!-- <th>fulluom</th>
										<th>fulluomqty</th>
										<th>looseuom</th>
										<th>looseuomqty</th> -->
										<th>uomsel</th>
										<th>In stock</th>
										<th></th>
									</tr>
								</thead>
								
							</table>								
						</div>
					</div>
				</div>
				
				<div class="leftProfile">
					<div class="profilName" data-toggle="modal">
					 <button data-toggle="modal"  data-target="#addCustomer" id="customeraddbtn" style="display: none"></button> 
						<div class="profilPic">
							<i class="fa fa-plus-circle"  id="customerlogoclass" onclick="opennewcustomerpanel();"></i>
						</div>
						<div class="NameProGroup">
							<h3 id="custnametext"></h3>
							<p id="defgrouptext"></p>
						</div>
					</div>
					
					<div class="profilePurchase">
						<button class="buttonProfile Yellow" data-toggle="modal" data-target="#customer" > <i class="fa fa-address-card-o" aria-hidden="true"></i> </button>
						<button class="buttonPurchase blue" data-toggle="modal" data-target="#PreviousPurchases" disabled="disabled" id="purchasebtn"> Purchases </button>
						<button data-toggle="modal" data-dismiss="modal" id="custaddressbtn" class="SaleOptions blue" style="display: none;"> Delivery Address  </button>
						<button data-toggle="modal" data-target="#customerAddressPopup" data-dismiss="modal" id="custaddresshdebtn" class="SaleOptions blue" style="display: none;"> Delivery Address  </button>
						
						<button type="button" style="display: none" class="close" data-dismiss="modal" id="closepopup">&times;</button>
						
					</div>
				</div>
				
				
				<div class="rightTip">
				<div class="scrollbar force-overflow" id="style-3">
					<div class="tipDiv">
					
						<div class="titleTip">
							Tip
						</div>
						
						<p id="customertipdiv">
								
						</p>
						</div>
					</div>			
				</div>
			</div>
<div class="paymentDetails">
					<div class="ProdectDetails">
						<div class="inStorePurchas">
						<p class="head-font-size  gray-font">In-store Purchase / Carryout</p>
						<table class="table myorderTable tablePay  " id="instoretable">
							<thead>
							<tr>
								<td rowspan="2" width="200" align="left">Name</td>
								<td rowspan="2">Batch</td>
								<td rowspan="2">HSNCODE</td>
								<td colspan="3">Sale Qty</td>
                                <td rowspan="2" id="tax1"> CGST %</td>
							    <td rowspan="2" id="tax2"> SGST %</td>	
								<td rowspan="2">Unit Price</td>
								<td rowspan="2">Sub Total</td>
								<td rowspan="2">Action</td>
								</tr>
								<tr>
								<td>Full</td>
								<td>Loose</td>
								<td>Total Qty</td>
								</tr>
							</thead>
							 <tbody class="possal">
							 
							 </tbody>
						</table>
						</div>
						<div class="inStorePurchas" style="display: none" id="salesordertable">
						<p class="head-font-size  yellow-font">Ship to ( Address )</p>
						<table class="table myorderTable tablePay" id="ordertable">
							<thead>
							<tr>
								<td rowspan="2" width="200" align="left">Name </td>
								<td rowspan="2" >BATCH</td>
								<td rowspan="2" >HSNCODE</td>
								<!-- <th>Qty</th> -->
								<!-- <td><table border="0">
									                              <tr align="center">
									                              <td colspan="3">
									                              Sale Qty
									                              </td>
									                              </tr>
                              		<tr align="center">
                              		<td style="width: 100px;">Full </td>
                              		<td style="width: 100px;">&nbsp;Loose </td>
                              		<td style="width: 100px;">&nbsp;Total Qty </td>
                              		</tr>
                              		</table></td>	 -->
                              	<td colspan="3">Sale Qty</td>
                                <td rowspan="2"  id="tax3"> CGST %</td>
							    <td rowspan="2"  id="tax4"> SGST %</td>	
								<td rowspan="2">Unit Price</td>
								<td rowspan="2">Sub Total</td>
								<td rowspan="2">Action</td>
								</tr>
								<tr>
								<td>Full</td>
								<td>Loose</td>
								<td>Total Qty</td>
								</tr>
							</thead>
							<tbody class="posord1">
								
							</tbody>
						</table>
						</div>
					</div>
					
					<div class="totalPayment">
						<div class="TwoDiv">
							<div class="totaltax">Grand Total</div>
							<div class="totaltax"  id="cgsttax">CGST Amount</div>
							<div class="totaltax"  id="sgsttax">SGST Amount</div>
							<div class="totaltax" id="gstsgst" style="display: text;">(CGST+SGST) Amount</div>
							
							
							<input type="hidden" value="${taxList.size() }" id="taxlistsizetext">
							<c:forEach items="${taxList}" var="tax" >
							<div class="totaltax" id="storetax">Tax ( ${tax.label }  ${tax.taxrate } %)</div>
							<input type="hidden" value="${tax.taxrate }" id="taxrate${tax.index }">
							<input type="hidden" value="${tax.value }" id="taxid${tax.index }">
							</c:forEach>
							
							<div class="totaltax" id="totalamtlabel" style="display: none;">Total Amount</div>
							<div class="discount" id="disclabel" style="display: none;">Discount</div>
							<div class="totalCashWithDecimal">Net Amount With Decimal</div>
							<div class="totalCash">Net Amount</div>
						</div>
						
						<div class="TwoDiv " align="right">
							<div class="totaltax alignRight" id="grandtotaltext">0</div>
							<div class="totaltax alignRight" id="gstid">0</div>
						    <div class="totaltax alignRight" id="sgstid">0</div>
						    <div class="totaltax alignRight" id="totaltaxlabel">0</div>
							
							   
							 <div id= "storetaxvalue">  
							    <c:forEach items="${taxList}" var="tax" >
							    <div class="totaltax alignRight" id="taxvalue${tax.index }">0</div>
							    <input type="hidden" id="taxamount${tax.index }">
							    </c:forEach>
						    </div>
						    
							<input type="hidden" id="discountamount" value="0">
							<div class="totaltax alignRight" id="totalamounttext" style="display: none;">0</div>
							<div class="discount alignRight" id="discamountlabel" style="display: none;">0</div>
							<div class=" alignRight" id="netamountwithdecimaltext">0.00</div>
							<div class="totalCash alignRight" id="netamounttext">0</div>
						</div>					
					</div>
				</div>
		
				
			</div>
			
			<div class="rightPoi">

					<button class="Function blue twoButton" onclick="clicknewsale();">New Sale</button>	
					<button data-toggle="modal" data-target="#Discount" class="SaleOptions blue twoButton"> Discount  </button>	

					<button class="Function blue twoButton" onclick="clickordersale();" id="saveorderbtn">Save Order</button>
					<button class="Function blue twoButton" onclick="clicksavesale();" id="savesalebtn">Save Purchase</button>
									
					<button class="pay green twoButton" type="button" onclick="paybuttonclick()" id="payevtbtn"> PAY </button>
					<button class="pay green twoButton" type="button" data-toggle="modal" data-target="#payment" id="paybutton" style="display: none"> PAY </button>
					<button class="pay green twoButton" type="button" onclick="returnbuttonclick()" id="returnevtbtn" style="display: none"> PAY </button>
					
					<button data-toggle="modal" data-target="#Return" class="Return red" style="display: none"> </button>
					<button data-toggle="modal" data-target="#Return" class="Return red twoButton" onclick="openreturnpopup();"> <i class="fa fa-trash-o" aria-hidden="true"></i> Return </button>
					
					
				</div>
			
				</div>
				
				
			</div>
		
		</div>
							
							</div>
						</div>
					</div>



<div class="modal fade paymentPop	" id="payment" tabindex="-1" role="dialog" aria-hidden="true">
<div class="modal-dialog">
  <div class="modal-content">
  <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Modal Header</h4>
      </div>
    <div class="modal-body">
		<div class="payMentLeft" style="display: none">
			<div class="paymentList">
				<p>BALANCE DUE</p>
				<h4 id="balanceid"></h4>
			</div>
		</div>
		<div class="payMentRight" style="width:100%;padding-top:15px;">
			<div class="paymentType" style="display: none">
				<h3>Select Payment Method </h3>
				<button data-toggle="modal" data-target="#CashPopupCount" data-dismiss="modal" id="cashbtn"  class="cash"><div><i class="fa fa-money" aria-hidden="true"></i></div><div>Cash</div></Button>
				<button class="creditDebit"  data-toggle="modal" data-target="#CardPopupCount" data-dismiss="modal" id="cardbtn"><div><i class="fa fa-credit-card" aria-hidden="true" id="creditbtn"></i></div><div>Credit / Debit</div></Button>
				<button class="creditDebit"  data-toggle="modal" data-target="#ChequePopupCount" data-dismiss="modal" id="chequebtn"><div><i class="fa fa-credit-card" aria-hidden="true" id="chequebtn"></i></div><div>Cheque</div></Button>
				<button class="creditDebit"><div><i class="fa fa-google-wallet" aria-hidden="true" id="paytmbtn"></i></div><div>Paytm</div></Button>
				
			</div>
			
			<div class="paymentType">
				<h3>Select Payment Method </h3>
				<button onclick="setpaymenttype('1',cashbtn)" class="cash"><div id="cashstylediv"><i class="fa fa-money" aria-hidden="true"></i></div><div>Cash</div></Button>
				<button onclick="setpaymenttype('2',creditbtn)" class="creditDebit"><div id="cardstylediv"><i class="fa fa-credit-card" aria-hidden="true"></i></div><div>Credit / Debit</div></Button>
				<button onclick="setpaymenttype('4',chequebtn)" class="creditDebit"><div id="chequestylediv"><i class="fa fa-credit-card" aria-hidden="true"></i></div><div>Cheque</div></Button>
				<button onclick="setpaymenttype('4',paytmbtn)" class="creditDebit"><div><i class="fa fa-google-wallet" aria-hidden="true"></i></div><div>Paytm</div></Button>
			</div>
			
		</div>
    </div>
  </div>
</div>
</div>

<div class="modal fade paymentPop	" id="CashPopup" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog">
	  <div class="modal-content">
	  <div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">&times;</button>
			<h4 class="modal-title">Modal Header</h4>
		  </div>
		<div class="modal-body">
			<div class="payMentLeft">
				<div class="paymentList" style="display: none">
					<p>BALANCE DUE</p>
					<h4>$54.54</h4>
				</div>
				<div class="unKnocash">
					<table>
						<tr>
							<td>
								Payment
							</td>
							<td id="paymenttypetdtext">
								
							</td>
							<td>
								
							</td>
						</tr>
					</table>
				</div>
			</div>
			< <div class="payMentRight">
				<div class="paymentType duePaymentTop">	
					<div class="duePayment">
						<div class="moneySection">
							<p>Change Due</p>
							<h3 id="balanceamttext"></h3>
						</div>
						<button data-toggle="modal" data-target="#completTransaction" data-dismiss="modal"  class="ChangeMade btn">Change Made</button>
					</div>
				</div>
			</div> 
		</div>
	  </div>
	</div>
</div>


<div class="modal fade paymentPop	" id="CashPopupCount" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog">
	  <div class="modal-content">
	  <div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">&times;</button>
			<h4 class="modal-title">Modal Header</h4>
		  </div>
		<div class="modal-body">
			<div class="payMentLeft">
				<div class="paymentList" style="display: none">
					<p>BALANCE DUE</p>
					<h4>$54.54</h4>
				</div>
				<div class="unKnocash">
					<table>
						<tr>
							<td>
								Payment
							</td>
							<td>
								cash
							</td>
							<td id="tobepaidtd">
								 
							</td>
						</tr>
					</table>
				</div>
			</div>
			<div class="payMentRight">
				<div class="countNumber">	
					<div class="leftCount">
							<div class="backCount" data-toggle="modal" data-target="#payment">
								<button> < back </button>
							</div>

							<div class="enamoDiv">
								$10.00
							</div>
							<div class="enamoDiv">
								$50.00
							</div>
							<div class="enamoDiv">
								$100.00
							</div>
							<div class="enamoDiv">
								$500.00
							</div>
					</div>
					<div class="rightCount">
							<div class="diplayCalculation">
								<div class="tenderedAmout">
									Tendered Amount
								</div>	
								<input type="text" class="amountInput" class="" name="" id="tenderedamt">
							</div>

							<div class="calculationFull">

								<div class="calculateDiv"> 
									<button>
										7
									</button>
								</div>
								<div class="calculateDiv"> 
									<button>
										8
									</button>
								</div>
								<div class="calculateDiv"> 
									<button>
										9
									</button>
								</div>
								<div class="calculateDiv"> 
									<button class="calGray">
										<i class="fa fa-caret-square-o-left" aria-hidden="true"></i>
									</button>
								</div>
								<div class="calculateDiv"> 
									<button>
										4
									</button>
								</div>
								<div class="calculateDiv"> 
									<button>
										5
									</button>
								</div>
								<div class="calculateDiv"> 
									<button>
										6
									</button>
								</div>
								<div class="calculateDiv"> 
									<button  class="calGray">
										C
									</button>
								</div>
								<div class="calculateDiv"> 
									<button>
										1
									</button>
								</div>
								<div class="calculateDiv"> 
									<button>
										2
									</button>
								</div>
								<div class="calculateDiv"> 
									<button>
										3
									</button>
								</div>
								<div class="calculateDiv"> 
									<button class="calGray">
										<i class="fa fa-plus" aria-hidden="true"></i> / <i class="fa fa-minus" aria-hidden="true"></i>
									</button>
								</div>
								<div class="calculateDiv"> 
									<button>
										0
									</button>
								</div>
								<div class="calculateDiv"> 
									<button>
										00
									</button>
								</div>
								<div class="calculateDiv"> 
									<button>
									.
									</button>
								</div>
								<div class="calculateDiv">
								<button  data-toggle="modal" data-target="#CashPopup" data-dismiss="modal"  class="calblue" style="display: none" id="tendbutton">
										
									</button> 
									<button  class="calblue" onclick="calctendamt()">
										Tend
									</button>
								</div>

							</div>

					</div>
				</div>
			</div>
		</div>
	  </div>
	</div>
</div>


<div class="modal fade paymentPop	" id="CardPopupCount" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog">
	  <div class="modal-content">
	  <div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">&times;</button>
			<button type="button" class="close" data-dismiss="modal" id="cardclosebtn" style="display: none">&times;</button>
			<h4 class="modal-title">Credit / Debit Card Details</h4>
		  </div>
		<div class="modal-body">
		
		
			<div class="displayTable">
			
			<div class="formFieldAlign">
			
			
			<table>
				<tr>
					<td>
						<b>Card Number</b>
					</td>
					<td><input class="form-control" type="number" id="cardnumbertext" maxlength="16"></td>
				</tr>
				<tr>
					<td>
					<b>Card Type</</b>
					</td>
					<td>
						<select class="form-control" id="cardtype" onchange="setcardtype();">
									<option value="2">Credit Card</option>
									<option value="3">Debit Card</option>
									</select>
					</td>
				</tr>
				<tr>
					<td>
						<b>Tendered Amount</b>
					</td>
					<td>
						<input class="form-control"  type="number" id="cardtenderedamount" onchange="settotenderedamount(this.value);">
					</td>
				</tr>
			</table>
								 
								<div class="calculateDiv alignCenter">
									</button> 
									<button  class="calblue" onclick="calccardtendamt()">
										Tend
									</button>
								</div>

							</div> 
							</div>
			 
		</div>
	  </div>
	</div>
</div>

<div class="modal fade paymentPop	" id="ChequePopupCount" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog">
	  <div class="modal-content">
	  <div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">&times;</button>
			<h4 class="modal-title">Cheque Details</h4>
		  </div>
		<div class="modal-body">


			<div class="displayTable">
			
			<div class="formFieldAlign">
			
			
			<table>
				<tr>
					<td>
						<b>Bank Name</b>
					</td>
					<td><select class="selectNor form-control from-white" id="banknameselect" onchange="setbankdetails(${bank.label })">
              <c:forEach items="${bankList}" var="bank" > 
            <option value="${bank.value }"> ${bank.label } </option>
            </c:forEach>
            </select></td>
				</tr>
				<tr>
					<td>
					Cheque No
					</td>
					<td>
						<input placeholder="Cheque No" class="form-control form-white " type="text" id="chequenotext">
					</td>
				</tr>
				<tr>
					<td>
						<b>Tendered Amount</b>
					</td>
					<td>
						<input type="number" class="form-control from-white" id="chequetenderedamount" onchange="settotenderedamount(this.value);">
					</td>
				</tr>
			</table>
 
								<div class="calculateDiv alignCenter">
										
									<button  data-toggle="modal" data-target="#CashPopup" data-dismiss="modal"  class="calblue" style="display: none" id="cashtendbutton">
										
									</button> 
 
									<button  class="calblue" onclick="calcchequetendamt()">
										Tend
									</button>
								</div>

							</div> 
							</div>

		</div>
	  </div>
	</div>
</div>

<div class="modal fade " id="customerAddressPopup" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog">
	  <div class="modal-content">
	  <div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" id="customerAddressPopupClose">&times;</button>
			<h4 class="modal-title">Customer Address</h4>
		  </div>
		<div class="modal-body full-padding-20">
			<div class="col-md-12 calculationFull">

					 <table class="hero-settings-table" id="customeraddressDT">
					 <thead>
					 <td>Select</td>
					 <td>Address</td>
					 </thead>
					 <tbody>
					 
					 </tbody>
					 </table>

			</div> 
							
							<div class="col-md-12 form-group full-padding-10">
							<label>Shippinng Amount</label>
							<input type="number" class="form-control form-white" id="shippingcosttext" value="0">
							</div>
							
					
			 <input class="btn btn-primary" type="button"  value="Ok" onclick="addressselect();">
		
		</div>
		<div class="clearfix"></div>
	  </div>
	</div>
</div>

<div class="modal fade " id="completTransaction" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog">
	  <div class="modal-content">
	  <div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">&times;</button>
			<h4 class="modal-title">Receipt Payment</h4>
		  </div>
		<div class="modal-body">
			<div class="scrollable" style="max-height: 374px;">
			
			<div class="sucessTick">
				<div class="circleTick">
					<i class="fa fa-check" aria-hidden="true"></i>
					<h4>Transaction Complete</h4>
				</div>
				<p class="">
					Customer Copy of Receipt
				</p>
				<div class="EmailRecipt">
					<div class="input-group">
						<span class="input-group-addon"><i class="fa fa-envelope" aria-hidden="true"></i></span>
						<input id="email" type="text" class="form-control" name="email" placeholder="Email">
					</div>
  					<button class="sendButton btn"> Send </button>
				</div>
				<div class="row">
					<div class="col-sm-6" >
						<button class="btn btn-normal" onclick="printinvoice();">
							<i class="fa fa-print" aria-hidden="true"></i> Print Receipt
						</button>
					</div>
					<div class="col-sm-6 alignRight marginull" align="right">
						<!-- <button class="btn btn-primary" data-dismiss="modal" onclick="savepos()"> -->
						<button class="btn btn-primary" onclick="invoicedone()" id="invoicedonebtn">
							<i class="fa fa-print" aria-hidden="true"></i> Done
						</button>
					</div>
				</div>
			</div>
			</div>
		</div>
	  </div>
	</div>
</div>

<div class="modal fade " id="customer" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog">
	  <div class="modal-content">
	  <div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">&times;</button>
			<h4 class="modal-title">Customer Detail</h4>
		  </div>
		<div class="modal-body">
			<div>
			<div class="row"><div class="col-md-12">
			<table class="hero-settings-table table-customer" style="margin-bottom: 0">
			<thead>
				<tr>
					<th>Info</th>
					<th>Details</th>
				</tr>
			</thead>
			<tbody>
			<tr><td><strong>Group</strong></td><td class="text-left" id="grouptdtext"></td></tr>
			<!-- <tr><td style="width: 120px"><strong>Birthday</strong></td><td class="text-left">June 18</td></tr> -->
			<tr><td><strong>E-mail</strong></td><td class="text-left" id="emailtdtext"></td></tr>
			<tr><td><strong>Mobile</strong></td><td class="text-left" id="mobiletdtext"></td></tr>
			<tr><td style="vertical-align:top !important;"><strong>Address</strong></td><td class="text-left" id="addresstdtext"></td></tr>
			<tr style="display: none;"><td><strong>Balance Due</strong></td><td class="text-left" id="balanceduetdtext"></td></tr>
			<tr style="display: none;"><td><strong>Store credit</strong></td><td id="credittdtext"></td></tr>
			
			</tbody>
			
			</table>
			
			</div>
			</div>
			</div>
		</div>
	  </div>
	</div>
</div>


<div class="modal fade 	" id="PreviousPurchases" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog">
	  <div class="modal-content">
	  <div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">&times;</button>
			<h4 class="modal-title">Previous purchases</h4>
		  </div>
		  
	<div class="modal-body">
		<div id="last-sales-documents" class="panel-group"><div class="panel panel-customer-purchases" style="border: 0;">
		<div class="panel-heading" style="cursor:pointer" data-toggle="collapse" href="#14000005">
		<span class="panel-title" style="font-size: 14px;"><!--content added here-->
		<div id="purchaseDIV">	
		<!-- <span class="bold" style="width: 100px; display: inline-block">14000005</span>28-05-2017 10:00 
		<span class="pull-right bold">107.43 INR</span> -->
		
		<table class="hero-settings-table table-purchases" style="margin-bottom: 0" id="customerPurchasesDT">
		<thead><tr style="cursor: default;">
		<th trans="" style="padding-left: 16px; width: 250px;">InvoiceNo</th>
		<th trans="" style="padding-left: 16px; width: 250px;">Date</th>
		<th trans="" style="padding-left: 16px; width: 250px;">Customer </th>
		<th trans="" style="padding-left: 16px; width: 250px;">Store</th>
		<th trans="" style="padding-left: 16px; width: 250px;">State</th>
		<th trans="" style="width: 50px;" class="text-center">Net Amount</th>
		
		</tr>
		</thead>
		</table>
		
		</div>
		</span>
		
		</div>
		<div class="panel-collapse collapse" id="14000005"><div class="panel-body" style="padding: 0; border-top: 0;">
		<div id="custom-purchase-data" class="hidden" style="padding: 16px; padding-top: 8px; padding-bottom: 8px;"></div>
		<table class="table table-white table-thead table-purchases" style="margin-bottom: 0" id="customerPurchasesProdDT">
		<thead style="background-color: #f5f5f5;"><tr style="cursor: default;">
		<th trans="" style="padding-left: 16px; width: 250px;">Name</th>
		<th trans="" style="width: 150px;">Code</th>
		<th trans="" style="width: 50px;" class="text-center">Qty</th>
		<th trans="" style="padding-right: 16px;" class="text-right">Sum</th>
		</tr>
		</thead>
		</table>
		</div>
		</div>
		</div>
		</div>
	  </div>
	  </div>
	</div>
</div>

<div class="modal fade 	" id="Return" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog">
	  <div class="modal-content">
	  <div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">&times;</button>
			<button type="button" class="close" data-dismiss="modal" id="returnpopupclose" style="display: none;">&times;</button>
			
			<h4 class="modal-title">Return</h4>
		  </div>
		  
	<div class="modal-body">
		<div class="searchBo">
						<div class="input-group stylish-input-group">
							<input class="form-control" placeholder="Type Bill No and Press Enter" type="text" id="returnbillno">
							<span class="input-group-addon">
								<i class="fa fa-search" aria-hidden="true" onclick="getbillitemdetils();"></i>  
							</span>
						</div>
						
						<div class="scrollable" style="max-height: 332px; min-height: 332px;">
						</br>

						<table class="table table-white" id="returnDT">
						<thead>
						<tr>
						<th trans="" data-default-translation="Void">Void</th>
						<th trans="" data-default-translation="Name">Name</th>
						<th trans="" data-default-translation="Code">Code</th>
						<th trans="" data-default-translation="Code">Qty</th>
						<th trans="" data-default-translation="Amount">Amount</th>
						</tr>
						</thead>
						<tbody id="return-rows">
						
						</tbody>
						</table>
						</div>
						<div class="col-md-12" style="height:45px;"	>
							<button class="btn btn-info pull-right header Yellow" onclick="savereturnitems()"> Save </button>
						</div>
					</div>
	  </div>
	  
	</div>
</div>



<div class="modal fade 	" id="Function" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog">
	  <div class="modal-content">
	  	<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">&times;</button>
			<h4 class="modal-title">Function</h4>
		</div>
		  
		<div class="modal-body" align="center">
			<!-- <button data-dismiss="modal" data-toggle="modal" data-target="#Recentsales" class="FunctionPOP blue">Recent sales</button>			
			<button data-dismiss="modal" data-toggle="modal" data-target="#Pendingsales" class="FunctionPOP blue">Pending sales</button>			
			<button data-dismiss="modal" data-toggle="modal" data-target="#Pickuporders" class="FunctionPOP blue">Pickup orders</button>			
			<button data-dismiss="modal" data-toggle="modal" data-target="#Discount" class="FunctionPOP blue">Close day</button>			
			<button data-dismiss="modal" data-toggle="modal" data-target="#Xreport" class="FunctionPOP blue">X-report</button>	
			<button data-dismiss="modal" data-toggle="modal" data-target="#StockandPrice" class="FunctionPOP blue">Stock and Price</button>	 -->
			<button class="FunctionPOP blue" data-dismiss="modal" id="functiontoggle" style="display: none;">New Sale</button>
			
			<button class="FunctionPOP blue" onclick="clicknewsale();">New Sale</button>	
			<button class="FunctionPOP blue" onclick="clicksavesale();" id="savesalebtn">Save Purchase</button>	
			<button class="FunctionPOP blue" onclick="clickordersale();" id="saveorderbtn">Save Order</button>	
		</div>
	</div>
	</div>
</div>
<div class="modal fade 	" id="Recentsales" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog">
	  <div class="modal-content">
	  	<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">&times;</button>
			<h4 class="modal-title">Recent Sales</h4>
		</div>
		<div class="modal-body">
			
			<div class="searchBo">
				<div class="input-group stylish-input-group">
					<input class="form-control" placeholder="Customer" type="text">
					<span class="input-group-addon">
						<i class="fa fa-search" aria-hidden="true"></i>  
					</span>
				</div>
				<div class="scrollable" style="max-height: 332px; min-height: 332px;">
				<br>
				<table class="table table-white">
					<thead>
						<tr>
							<th trans="" data-default-translation="Number">Number</th>
							<th trans="" data-default-translation="Date">Date</th>
							<th trans="" data-default-translation="Customer">Customer</th>
							<th trans="" data-default-translation="Sum">Sum</th>
							<th></th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>14000007</td>
							<td>18-06-2017</td>
							<td>siva, yuvaraj</td>
							<td>56.35</td>
							<td class="PrintIcon"><i class="fa fa-print" aria-hidden="true"></i></td>
						</tr>
						<tr>
							<td>14000006</td>
							<td>11-06-2017</td>
							<td>siva, yuvaraj</td>
							<td>350.45</td>
							<td class="PrintIcon"><i class="fa fa-print" aria-hidden="true"></i></td>
						</tr>
						<tr>
							<td>100002</td>
							<td>03-06-2017</td>
							<td>siva, yuvaraj</td>
							<td>382.70</td>
							<td class="PrintIcon"><i class="fa fa-print" aria-hidden="true"></i></td>
						</tr>
						<tr>
							<td>14000005</td>
							<td>28-05-2017</td>
							<td>POS Customer, </td>
							<td>107.43</td>
							<td class="PrintIcon"><i class="fa fa-print" aria-hidden="true"></i></td>
						</tr>
						<tr>
							<td>14000004</td>
							<td>27-05-2017</td>
							<td>siva, yuvaraj</td>
							<td>62.43</td>
							<td class="PrintIcon"><i class="fa fa-print" aria-hidden="true"></i></td>
						</tr>
						<tr>
							<td>14000003</td>
							<td>27-05-2017</td>
							<td>siva, yuvaraj</td>
							<td>236.35</td>
							<td class="PrintIcon"><i class="fa fa-print" aria-hidden="true"></i></td>
						</tr>
						<tr>
							<td>14000002K</td>
							<td>05-05-2017</td>
							<td>siva, yuvaraj</td>
							<td>1210.51</td>
							<td class="PrintIcon"><i class="fa fa-print" aria-hidden="true"></i></td>
						</tr>
						<tr>
							<td>14000001</td>
							<td>17-04-2017</td>
							<td>siva, yuvaraj</td>
							<td>2241.40</td>
							<td class="PrintIcon"><i class="fa fa-print" aria-hidden="true"></i></td>
						</tr>
						<tr>
							<td>100001</td>
							<td>15-04-2017</td>
							<td>siva, yuvaraj</td>
							<td>322.28</td>
							<td class="PrintIcon"><i class="fa fa-print" aria-hidden="true"></i></td>
						</tr>
						<tr>
							<td>100001</td>
							<td>15-04-2017</td>
							<td>siva, yuvaraj</td>
							<td>322.28</td>
							<td class="PrintIcon"><i class="fa fa-print" aria-hidden="true"></i></td>
						</tr>
					</tbody>
				</table>
				</div>
			</div>
			
		</div>
	</div>
	</div>
</div>

<div class="modal fade 	" id="Pendingsales" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Pending sales</h4>
			</div>
			<div class="modal-body">

				<div class="searchBo">
					<div class="input-group stylish-input-group">
						<input class="form-control" placeholder="Customer" type="text">
						<span class="input-group-addon">
						<i class="fa fa-search" aria-hidden="true"></i>  
					</span>
					</div>
					<div class="scrollable" style="max-height: 332px; min-height: 332px;">
						<br>
						<table class="table table-white">
							<thead>
								<tr>
									<th trans="" data-default-translation="Date">Date</th>
									<th trans="" data-default-translation="Customer">Customer</th>
									<th trans="" data-default-translation="Sum">Sum</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>23-07-2017</td>
									<td>POS Customer, </td>
									<td>62.43</td>
								</tr>
								<tr>
									<td>18-06-2017</td>
									<td>siva, yuvaraj</td>
									<td>225.00</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<div class="modal fade 	" id="Pickuporders" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Pickup orders</h4>
			</div>
			<div class="modal-body">

				<div class="searchBo">
					<div class="input-group stylish-input-group">
						<input class="form-control" placeholder="Customer" type="text">
						<span class="input-group-addon">
						<i class="fa fa-search" aria-hidden="true"></i>  
					</span>
					</div>
					<div class="scrollable" style="max-height: 332px; min-height: 332px;">
						<br>
						<table class="table table-white">
							<thead>
								<tr>
									<th>Number</th>
									<th >Date</th>
									<th >Customer</th>
									<th >E-mail</th>
									<th >Paid</th>
									<th >Sum</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>100002</td>
									<td>03-06-2017</td>
									<td>siva, yuvaraj</td>
									<td>yuvi007raj@gmail.com</td>
									<td align="right">370.00</td>
									<td align="right">382.70</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<div class="modal fade 	" id="Xreport" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<button class="btn btn-info pull-right header Yellow"> Print </button>
				<h4 class="modal-title">X-report</h4>
			</div>
			<div class="modal-body">

				<div class="XreportScroll">
					<table width="100%" cellspacing="0" cellpadding="0" border="0">
						<tbody>
							<tr>
								<td valign="top">Point of Sale and Payments Report — INR</td>
							</tr>
						</tbody>
					</table>
					<div>Date: 23-07-2017 14:02</div>
					<div>Report printed by: yuvaraj sivakumar</div>
					<div>Start of period: 2017-07-23</div>
					<div>End of period: 2017-07-23</div>
					<div>Register: Default POS</div>
					<div>Location: Location #1</div>
					<p>&nbsp;</p>
					<table class="report" style="width: 100%;">
						<tbody>
							<tr>
								<td style="background-color: #eee;"><strong>Register: </strong></td>
								<td style="background-color: #eee;"><strong>Default POS</strong></td>
							</tr>
							<tr>
								<td>Initial amount in till: </td>
								<td align="right">0.00 INR</td>
							</tr>
							<tr>
								<td style="background-color: #eee;"><strong>Opened:</strong> yuvaraj sivakumar, 18-06-2017 16:12</td>
								<td style="background-color: #eee;">&nbsp;</td>
							</tr>
							<tr>
								<td>Day income: </td>
								<td align="right">56.35 INR</td>
							</tr>

							<tr>
								<td>Day income + all cash in &amp; out: </td>
								<td align="right">56.35 INR</td>
							</tr>
							<tr>
								<td>Expected amount in register at end of day: </td>
								<td align="right">56.35 INR</td>
							</tr>
							<tr>
								<td style="background-color: #eee;"><strong>Closed:</strong> yuvaraj sivakumar, 23-07-2017 11:09</td>
								<td style="background-color: #eee;">&nbsp;</td>
							</tr>
							<tr>
								<td>Total counted in register: </td>
								<td align="right">0.00 INR</td>
							</tr>
							<tr>
								<td>Deposited: </td>
								<td align="right">0.00 INR</td>
							</tr>
							<tr>
								<td>Left to till as change: </td>
								<td align="right">0.00 INR</td>
							</tr>
							<tr>
								<td style="background-color: #eee;"><strong>Over/short: </strong></td>
								<td style="background-color: #eee;" align="right"><strong>-56.35 INR</strong></td>
							</tr>

							<tr>
								<td style="background-color: #eee;"><strong>Transactions total: </strong></td>
								<td style="background-color: #eee;" align="right"><strong>1</strong></td>
							</tr>

						</tbody>
					</table><br><br>
					<table class="report" style="width: 100%;">
						<tbody>
							<tr>
								<td style="background-color: #eee;"><strong>Register: </strong></td>
								<td style="background-color: #eee;"><strong>Default POS</strong></td>
							</tr>
							<tr>
								<td>Initial amount in till: </td>
								<td align="right">0.00 INR</td>
							</tr>
							<tr>
								<td style="background-color: #eee;"><strong>Opened:</strong> yuvaraj sivakumar, 23-07-2017 11:09</td>
								<td style="background-color: #eee;">&nbsp;</td>
							</tr>
							<tr>
								<td>Day income: </td>
								<td align="right">0.00 INR</td>
							</tr>

							<tr>
								<td>Day income + all cash in &amp; out: </td>
								<td align="right">0.00 INR</td>
							</tr>
							<tr>
								<td>Expected amount in register at end of day: </td>
								<td align="right">0.00 INR</td>
							</tr>
							<tr>
								<td style="background-color: #eee;"><strong>Closed:</strong> yuvaraj sivakumar, 23-07-2017 11:10</td>
								<td style="background-color: #eee;">&nbsp;</td>
							</tr>
							<tr>
								<td>Total counted in register: </td>
								<td align="right">0.00 INR</td>
							</tr>
							<tr>
								<td>Deposited: </td>
								<td align="right">0.00 INR</td>
							</tr>
							<tr>
								<td>Left to till as change: </td>
								<td align="right">0.00 INR</td>
							</tr>
							<tr>
								<td style="background-color: #eee;"><strong>Over/short: </strong></td>
								<td style="background-color: #eee;" align="right"><strong>0.00 INR</strong></td>
							</tr>

							<tr>
								<td style="background-color: #eee;"><strong>Transactions total: </strong></td>
								<td style="background-color: #eee;" align="right"><strong>0</strong></td>
							</tr>

						</tbody>
					</table><br><br>
					<table class="report" style="width: 100%;">
						<tbody>
							<tr>
								<td style="background-color: #eee;"><strong>Register: </strong></td>
								<td style="background-color: #eee;"><strong>Default POS</strong></td>
							</tr>
							<tr>
								<td>Initial amount in till: </td>
								<td align="right">0.00 INR</td>
							</tr>
							<tr>
								<td style="background-color: #eee;"><strong>Opened:</strong> yuvaraj sivakumar, 23-07-2017 11:11</td>
								<td style="background-color: #eee;">&nbsp;</td>
							</tr>
							<tr>
								<td>Day income: </td>
								<td align="right">0.00 INR</td>
							</tr>

							<tr>
								<td>Day income + all cash in &amp; out: </td>
								<td align="right">0.00 INR</td>
							</tr>
							<tr>
								<td>Expected amount in register at end of day: </td>
								<td align="right">0.00 INR</td>
							</tr>
							<tr>
								<td style="background-color: #eee;"><strong>Closed:</strong> ---- </td>
								<td style="background-color: #eee;">&nbsp;</td>
							</tr>
							<tr>
								<td>Total counted in register: </td>
								<td align="right"> ---- </td>
							</tr>
							<tr>
								<td>Deposited: </td>
								<td align="right"> ---- </td>
							</tr>
							<tr>
								<td>Left to till as change: </td>
								<td align="right"> ---- </td>
							</tr>
							<tr>
								<td style="background-color: #eee;"><strong>Over/short: </strong></td>
								<td style="background-color: #eee;" align="right"><strong> ---- </strong></td>
							</tr>
							<tr>
								<td style="background-color: #eee;"><strong>Transactions total: </strong></td>
								<td style="background-color: #eee;" align="right"><strong>0</strong></td>
							</tr>
						</tbody>
					</table><br><br>
					<p>&nbsp;</p>
					<table cellspacing="0" cellpadding="0" border="0">
						<tbody>
							<tr>
								<td>Confirmed: &nbsp; </td>
								<td>.............................................................. </td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td align="center">(name)</td>
							</tr>
							<tr>
								<td>
									<p>&nbsp;</p>
									<p>&nbsp;</p>
								</td>
								<td>&nbsp;</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td align="center">.............................................................. </td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td align="center">(signature)</td>
							</tr>
						</tbody>
					</table>

				</div>
			</div>
		</div>
	</div>
</div>

<div class="modal fade 	" id="StockandPrice" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Stock and price lookup</h4>
			</div>
			<div class="modal-body">

				<div class="searchBo">
					<div class="input-group stylish-input-group">
						<input class="form-control" placeholder="Customer" type="text">
						<span class="input-group-addon">
						<i class="fa fa-search" aria-hidden="true"></i>  
					</span>
					</div>
					<div class="scrollable" style="max-height: 332px; min-height: 332px;">
						<br>
						<table class="table table-white">
							<thead>
								<tr>
									<th>Code</th>
									<th>Product</th>
									<th>Qty in store</th>
									<th>Qty total</th>
									<th>Price</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>XXXXX</td>
									<td>XXXXX</td>
									<td>XXXXX</td>
									<td>XXXXX</td>
									<td>XXXXX</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>



<div class="modal fade" id="order-confirm-popup" role="dialog" >
<div class="modal-dialog">
  <div class="modal-content">
    <div class="modal-body">
      <div class="popDel" align="center">
        <div class="info"><i class="fa fa-info" aria-hidden="true"></i></div>
        <h3>Product Currently not Available in stock</h3>
        Do you want to take Order?
      </div>
    </div>
    <div class="modal-footer alignCenter" align="center ">
    <button type="button" class="close" data-toggle="modal" data-target="#order-confirm-popup" id="orderconfirmbtn" style="display: none">&times;</button>
      <button type="button" class="btn btn-white " data-dismiss="modal" onclick="makeOrder();">Yes, Make Order</button>
      <button type="button" class="btn btn-danger " data-dismiss="modal" onclick="makeOrderCancel();">Cancel</button>
    </div>
  </div>
</div>
</div>

<script>


	function closeAuto(){
		$(".CustomerSearch").hide();
		// $(".CustomerSearchInput").closest('.inputFildHalf').removeClass("big");
		// $(".CustomerSearchInput").closest('.TetFildSearch').removeClass("searFull");
		// $(".CustomerSearchInput").closest('.inputFildHalf').addClass("big").find("i").addClass("fa fa-search");
		
		$(".ProdectSearch").hide();
		$('.inputFildHalf').removeClass("big");
		$('.TetFildSearch').removeClass("searFull");
		$('.inputFildHalf i').removeClass("fa-close");
		$('.inputFildHalf i').addClass("fa-search");

	}
	$(document).ready(function() {
		$(".CustomerSearchInput").on('keyup', function() {
			//but here value for this is textarea where keypress event happened.
			if (this.value.length > 1) {
				$(this).closest('.inputFildHalf').addClass("big").find("i").addClass("fa fa-close ");
				$(this).closest('.inputFildHalf').find("span.input-group-addon").addClass("closeAuto");
				$(this).closest('.TetFildSearch').addClass("searFull");
				$(".CustomerSearch").show();
			} else {
				/* $(".CustomerSearch").hide();
				$(this).closest('.inputFildHalf').removeClass("big");
				$(this).closest('.TetFildSearch').removeClass("searFull");
				$(this).closest('.inputFildHalf').addClass("big").find("i").addClass("fa fa-search "); */
				closeAuto();
			}
		});

		$(".productSearchInput").on('keyup', function() {
			if (this.value.length > 1) {
				$(this).closest('.inputFildHalf').addClass("big").find("i").addClass("fa fa-close ");
				$(this).closest('.inputFildHalf').find("span.input-group-addon").addClass("closeAuto");
				$(this).closest('.TetFildSearch').addClass("searFull");
				$(".ProdectSearch").show();
			} else {
				/* $(".ProdectSearch").hide();
				$(this).closest('.inputFildHalf').removeClass("big");
				$(this).closest('.TetFildSearch').removeClass("searFull");
				$(this).closest('.inputFildHalf').addClass("big").find("i").addClass("fa fa-search "); */
				closeAuto();
			}
		});


		$( "#returnbillno" ).keyup(function(event) {
			 if ( event.which == 13 ) {
			     event.preventDefault();
			     getbillitemdetils();
			  } 
	});
		

		$(".allownumericwithoutdecimal").on("keypress keyup blur",function (event) {    
			var numInput = document.querySelector('.allownumericwithoutdecimal');

			// Listen for input event on numInput.
			numInput.addEventListener('input', function(){
			    // Let's match only digits.
			    var num = this.value.match(/^\d+$/);
			    if (num === null) {
			        // If we have no match, value will be empty.
			        this.value = 0;
			    }
			}, false)
	        });

		$(window).on("load",function () {
		var headerH = $(".topbar.PoinOfSale").height();
		var ssssss = $(".samHeight").height();
		$(".billPOS").css("height",document.documentElement.clientHeight - headerH - 5);
		$(".ProdectDetails").css("height",document.documentElement.clientHeight - ssssss - 110 -headerH);
		}); 

	});

	

	$(function() {
	     
	    var items = [];

	    $.ajax({
	    	type:'GET',
	    	contentType:'application/json',
	    	url:'getcustomerautocomplete',
	    	success:function(data,textStatus,jqXHR)
	    	{
	    		var responseType = getResponseType(data);
	    		var responseValue = getResponseValue(data);
	    		items = responseValue;
	    	
	    	}
	    });
	        
	    function split( val ) {

	    	if (val.length > 1) {
	    		showCustomerSearch();
	    	} else {
	    		hideCustomerSearch();
	    	}
	    	
	        return val.split( /,\s*/ );
	    }
	    function extractLast( term ) {
	      return split( term ).pop();
	    }

	    function showCustomerSearch()
	    {
	    	$(this).closest('.inputFildHalf').addClass("big").find("i").addClass("fa fa-close ");
			$(this).closest('.inputFildHalf').find("span.input-group-addon").addClass("closeAuto");
			$(this).closest('.TetFildSearch').addClass("searFull");
			$(".CustomerSearch").show();
	   }

	   function hideCustomerSearch()
	   {
		   $(".CustomerSearch").hide();
			$(this).closest('.inputFildHalf').removeClass("big");
			$(this).closest('.TetFildSearch').removeClass("searFull");
	   } 

	   function setDTtoCustomerTable(filteredItems)
	   {
		   $('#customerDT').DataTable( {
	           "destroy":true,
	           "sortable":false,
	           "bLengthChange": false,
	           "bPaginate": false,
	           "bFilter": false,
	           "bInfo": false,
					data: filteredItems,
			        columns: [
			            { data: 'custname' },
			            {data: 'email'},
			            {data: 'mobile'},
			            {data: 'address'}
			        ]
		            
			    } );
		   
		   selectAutoCompleteCustomerItem();
		   }
		
	   function selectAutoCompleteCustomerItem() {
		  
			$('#customerDT tbody').on('click', 'tr', function() {
				var table = $('#customerDT').DataTable();
				var customerObject = table.row($(this)).data();
				$('#customersearchtext').val(customerObject.custname);
				$('#customeridtext').val(customerObject.value);
				$('#customermobiletext').val(customerObject.mobile);

				$('#purchasebtn').attr('disabled',false);
				customerDetails();
				hideCustomerSearch();
				closeAuto();
			});
			
		}
	   
	   var filteredItems = [];
	   
	    $( "#customersearchtext" )
	      .autocomplete({
	        minLength: 0,
	        source: function( request, response ) {
		        
	        	var custname = $('#customersearchtext').val();
	        	if(custname.length > 1)
	        		{
	        		
	          filteredItems = ($.ui.autocomplete.filter(items, extractLast( request.term ) ));

	          setDTtoCustomerTable(filteredItems);
			    
	          response( $.ui.autocomplete.filter(
	            items, extractLast( request.term ) ) );
	          
	        		}
	        	else
	        		{
	        		filteredItems = [];
	        		response( $.ui.autocomplete.filter(
	        				filteredItems, extractLast( request.term ) ) );
	                setDTtoCustomerTable(filteredItems);
	                hideCustomerSearch();
	                return false;
	        		}
	        },
	        focus: function() {
	          return false;
	        },
	        select: function( event, ui ) {
		      
	          var terms = split( this.value );
	          terms.pop();
	          terms.push( ui.item.label );
	          terms.push( "" );
	          $('#customersearchtext').val(ui.item.custname);
	          $('#customeridtext').val(ui.item.value);
	          filteredItems = [];
	          setDTtoCustomerTable(filteredItems);
	          hideCustomerSearch();
	          /* customerDetails(); */
	          return false;
	        }
	      });

	   
	    var product_items = [];
	    var userid = $('#inventoryuseridtext').val();
	    
	    $.ajax({
	    	type:'GET',
	    	contentType:'application/json',
	    	url:'getposproductautocomplete/'+userid,
	    	success:function(data,textStatus,jqXHR)
	    	{
	    	
	    		var responseType = getResponseType(data);
	    		var responseValue = getResponseValue(data);
	    		product_items = responseValue;
	    		
	    	
	    	}
	    });
	        
	    function product_split( val ) {

	    	if (val.length > 1) {
	    		showProductSearch();
	    	} else {
	    		hideProductSearch();
	    	}
	    	
	        return val.split( /,\s*/ );
	    }
	    function extractProductLast( productterm ) {
	      return product_split( productterm ).pop();
	    }

	    

	   function setDTtoProductTable(filteredItems)
	   {
		   
		   $('#productDT').DataTable( {
	           "destroy":true,
					data: filteredItems,
					"bLengthChange": false,
			        "bPaginate": false,
			        "bFilter": false,
			        "bInfo": false,
			        columns: [
			            { data: 'label' },
			            {data: 'companyname'},
			            {data: 'batchno'},
			            {data:'expirydate'},
			            {data:'price'},
			            /* {data:'purfullqty'},
			            {data:'purfulluom'},
			            {data:'purlooseqty'},
			            {data:'purlooseuom'}, */
			            {data:'ordereduompackingid'},
			            {data:'productcount'}
			        ]
		            
			    } );
		   
		   selectProductItem();
		   }
		
	   function selectProductItem() {
			$('#productDT tbody tr').on('click',function(event) {
				event.preventDefault();
				var table = $('#productDT').DataTable();
				var productObject = table.row($(this)).data();
				$('#productsearchtext').val(productObject.productname);
				$('#productidtext').val(productObject.value);
				hideProductSearch();
		
				productObject['purfulluomselectedoption'] = 1;
				setitemtoPurchaseDT(productObject);

				closeAuto();
			});
			
		}
	   
	   
	   
	  
	   
	    $( "#productsearchtext" )
	      .autocomplete({
	        minLength: 0,
	        source: function( request, response ) {
	        	var prodname = $('#productsearchtext').val();
	        	if(prodname.length > 1)
	        		{
	        			if(parseInt(barcodeclick) == 1)
	        			{
	        			
	        			$(".ProdectSearch").hide();
	        			$('.inputFildHalf').removeClass("big");
	        			$('.TetFildSearch').removeClass("searFull");
	        			$('.inputFildHalf i').removeClass("fa-close");
	        			$('.inputFildHalf i').addClass("fa-search");
	        			
	        			if(prodname.length > 5){  			
	        				$.ajax({
	        		    	type:'GET',
	        		    	contentType:'application/json',
	        		    	url:'getposproductusingbarcode/'+prodname,
	        		    	success:function(data,textStatus,jqXHR)
	        		    	{
	        		    		var responseType = getResponseType(data);
	        		    		var responseValue = getResponseValue(data);
	        		    		
	        		    		setitemtoPurchaseDT(responseValue[0]);
	        		    	}
	        		    });
	        			}
	        		}
	        		else
	        		{
			          productFilteredItems = ($.ui.autocomplete.filter(product_items, extractProductLast( request.term ) ));
			        
			          setDTtoProductTable(productFilteredItems);
					    
			          response( $.ui.autocomplete.filter(
	        		  product_items, extractProductLast( request.term ) ) );
	        		}
	          
	        		}
	        	else
	        		{
	        		
	        		if(parseInt(barcodeclick) == 1){
	        			
	        			$(".ProdectSearch").hide();
	        			$('.inputFildHalf').removeClass("big");
	        			$('.TetFildSearch').removeClass("searFull");
	        			$('.inputFildHalf i').removeClass("fa-close");
	        			$('.inputFildHalf i').addClass("fa-search");
	        			
	        			if(prodname.length > 5){  			
	        			$.ajax({
	        		    	type:'GET',
	        		    	contentType:'application/json',
	        		    	url:'getposproductusingbarcode/'+prodname,
	        		    	success:function(data,textStatus,jqXHR)
	        		    	{
	        		    		var responseType = getResponseType(data);
	        		    		var responseValue = getResponseValue(data);
	        		    		
	        		    		setitemtoPurchaseDT(responseValue[0]);
	        		    	}
	        		    });
	        			}
	        		}
	        		else
	        		{
	        		productFilteredItems = [];
	        		response( $.ui.autocomplete.filter(
	        				productFilteredItems, extractProductLast( request.term ) ) );
	        		setDTtoProductTable(productFilteredItems);
	                hideProductSearch();
	                return false;
	        		}
	        		}
	        },
	        focus: function() {
	          return false;
	        },
	        select: function( event, ui ) {
	          var terms = product_split( this.value );
	         
	          terms.pop();
	          terms.push( ui.item.label );
	          terms.push( "" );
	          $('#productsearchtext').val(ui.item.productname);
	          $('#productidtext').val(ui.item.value);
	          /*productFilteredItems = [];*/
	          setDTtoProductTable(productFilteredItems);
	          hideProductSearch();
	          return false;
	        } 
	      });
	  }); 
		
	
	
   
    </script>
	/*Product AutoComplete End*/
	
</script>

 <script>
 $(document).ready(function(){
 	$('input[name="taxper"]').on('change', function(){
 		var selvalue = $('input[name="taxper"]:checked').val();
 		if(selvalue == "exclusive"){
 			$("#tax1").css({'display':'none'});
 			$("#gstsgst").css({'display':'none'});
 			$("#gstid").css({'display':'none'});
 			$("#totaltaxlabel").css({'display':'none'});
 			$("#sgstid").css({'display':'none'});
 			$("#cgsttax").css({'display':'none'});
 			$("#sgsttax").css({'display':'none'});
 			$("#tax2").css({'display':'none'});
 			$("#tax3").css({'display':'none'});
 			$("#tax4").css({'display':'none'});
 			$("#storetax").css({'display':'block'});
 			$("#storetaxvalue").css({'display':'block'});
 		
 			
 		}else if(selvalue == "inclusive"){
 			
 			
 			$("#gstsgst").css({'display':'block'});
 			$("#gstid").css({'display':'block'});
 			$("#totaltaxlabel").css({'display':'block'});
 			$("#sgstid").css({'display':'block'});
 			$("#cgsttax").css({'display':'block'});
 			$("#sgsttax").css({'display':'block'});
 			$("#tax1").css({'display':'table-cell'});
 			$("#tax2").css({'display':'table-cell'});
 			$("#tax3").css({'display':'table-cell'});
 			$("#tax4").css({'display':'table-cell'});
 			$("#storetax").css({'display':'none'});
 			$("#storetaxvalue").css({'display':'none'});
 			
 			
 		}
 		
 		if(selvalue == "both"){
 			
 			$("#gstsgst").css({'display':'block'});
 			$("#gstid").css({'display':'block'});
 			$("#totaltaxlabel").css({'display':'block'});
 			$("#sgstid").css({'display':'block'});
 			$("#cgsttax").css({'display':'block'});
 			$("#sgsttax").css({'display':'block'});
 			$("#tax1").css({'display':'table-cell'});
 			$("#tax2").css({'display':'table-cell'});
 			$("#tax3").css({'display':'table-cell'});
 			$("#tax4").css({'display':'table-cell'});
 			$("#storetax").css({'display':'block'});
 			$("#storetaxvalue").css({'display':'block'});
 				
 		}
 	});
 	
 });
 </script>
<style>

</style>
<jsp:include page="../template/footer.jsp" />
</body>
</html>