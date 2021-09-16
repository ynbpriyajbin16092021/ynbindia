<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Receive Stock</title>
<jsp:include page="../../template/library.jsp" />
<script type="text/javascript" src="../js/forms/transactions/purchaseorder.js"></script>

</head>
<body onload="loadreceivestock();">
<jsp:include page="../../template/header.jsp" />
<script>
$(document).ready(function(){
	 $("#productsearchtext").click(function(){
			$(document).scannerDetection({ 
				  //https://github.com/kabachello/jQuery-Scanner-Detection
				    timeBeforeScanTest: 200, // wait for the next character for upto 200ms
				    avgTimeByChar: 40, // it's not a barcode if a character takes longer than 100ms
				  	endChar: [13],
				  //preventDefault: true, //this would prevent text appearing in the current input field as typed 
				        onComplete: function(barcode, qty){
   					// alert(barcode);
    
    				$("#productsearchtext").val(barcode);
				    
    			} // main callback function 
			});
	 });  
});


</script>
 <input type="hidden" value="${firstid }" id="receiveditemsdetailsfirstid">  

					<div class="clearfix"></div>
					<div class="breadcrumbs"  style="display: <%= request.getParameter("disp") %>">
			            <div class="col-md-4 col-sm-4">
			                <div class="page-header">
			                        <p class="cursor-pointer mar-top-10 bread-a-style head-font-size yellow-font" href="stockdashboard"><strong><i class="fa fa-table"></i> GOODS RECEIVE NOTE</strong></p>
			                </div>
			            </div>
			            <div class="col-md-8 col-sm-8">
			                <div class="page-header float-right">
			                    <a class="pull-right cursor-pointer bread-a-style content-font-size color-font" href="dashboard?uid=1">Home / Dashboard </a>
			                </div>
			            </div>
			        </div>
					<div class="clearfix"></div>
					
<div class="col-md-12 overcome-col-padd-10  ">
						<div class="col-md-12 overcome-col-padd-10 ">
							<div class="width_100">
								
								<div class="width_100 gray-font white-bg content-font-size content-div-height">
									<div class="col-md-12 full-padding-20">




<div class="width_100">
	<ul class="nav nav-tabs">
	<li class="active"><a data-toggle="tab" href="#receiving">Pending Stock  (To be received)</a></li>
	<li ><a data-toggle="tab" href="#ordered">Ordered</a></li>
    <li><a data-toggle="tab" href="#received">Moved To Stock  (Paid)</a></li>
    
  </ul>

  <div class="tab-content">
  
      <div id="ordered" class="tab-pane fade" >
        <div class="width_100">
		<div class="width_100 mypadd10-0">
			<a class="mybackbtn" href="purchaseorderview?pid=${purchaseid }">< Back</a>
		</div>
		<div class="width_50">
			<p class="myorderhead"> Order # ${purchaseorderno } | <label class="supplier_name_text"></label></p>
		</div>
		<div class="width_50 ">
			<div class="myleftpanelorder">
				<label class="myyellowfont" >${receivedate }</label>
			</div>	
		</div>
		
		</div>
		
			<div class="width_100">
			<div class="width_50">
			<p class="myorderhead"> Ordered  </p>
		</div>
			<table class="table myorderTable">
				<thead>
					<tr>
					<td rowspan="2" style="width:18%;padding:10px 0px;text-align:left;padding-left:5px;">Products</td>	 
					<td rowspan="2" style="width:8%;padding:10px 0px;text-align:center;padding-left:5px;">UOM</td>	
					<td colspan="3" class="no-padding" style="width:18%">
						Ordered Qty
                     </td>
					
					</tr>
					 <tr>
                       <!--  <td class="no-padding">Full</td>
                        <td class="no-padding">Loose</td> -->
                        <td class="no-padding">Total </td>
                    </tr>													
				</thead>
											
				<tbody> 
					<c:forEach items="${completeorderList}" var="receivestock" > 
					<tr>
						<td style="text-align:left;color:#8cbde8;">
							<label class="mysubtotallabel"> ${ receivestock.product_name}</label>
						</td>
						<td class="padd-top-10" id="unit"> ${ receivestock.unit}</td>
						<%-- <td class="padd-top-10">${ receivestock.orderedfullqty} &nbsp;${ receivestock.pur_full_qty}&nbsp;${ receivestock.full_uom}</td>
						<td class="padd-top-10">${ receivestock.orderedlooseqty} &nbsp;${ receivestock.pur_loose_qty}&nbsp;${ receivestock.loose_uom}</td> --%>
						<td class="padd-top-10" id="purchaseqty">${ receivestock.pur_quantity}</td>
					</tr>
					</c:forEach>
					
				</tbody> 
			</table>
		</div>
		
		
		<div class="width_100">
		<div class="width_50">
			<p class="myorderhead"> Pending  </p>
		</div>
			<table class="table myorderTable">
				<thead>
					<tr>
					<td rowspan="2" style="width:18%;padding:10px 0px;text-align:left;padding-left:5px;">Products</td>	 
					<td rowspan="2" style="width:8%;padding:10px 0px;text-align:center;padding-left:5px;">UOM</td>	
					<td>Ordered Quantity</td>	
					<td>Received Quantity</td>	
					<td>Pending Quantity</td>													
				</thead>
											
				<tbody> 
					<c:forEach items="${pendingorderitemList}" var="pendingorderitem" > 
					<tr>
						<td style="text-align:left;color:#8cbde8;">
							<label class="mysubtotallabel"> ${ pendingorderitem.product_name}</label>
						</td>
						<td class="padd-top-10" id="unit"> ${ pendingorderitem.unit}</td>
						<td class="padd-top-10">${ pendingorderitem.pur_quantity} </td>
						<td class="padd-top-10">${ pendingorderitem.prec_recving_quantity} </td>
						<td class="padd-top-10">${ pendingorderitem.qty}</td>
					</tr>
					</c:forEach>
					
				</tbody> 
			</table>
		</div>
    </div>
    
    
    <div id="received" class="tab-pane fade">
      <div class="width_100">
		<div class="width_100 mypadd10-0">
			<a class="mybackbtn" href="purchaseorderview?pid=${purchaseid }">< Back</a>
		</div>
		<div class="width_50">
			<p class="myorderhead"> Order # ${purchaseorderno } | <label class="supplier_name_text"></label></p>
		</div>
		<div class="width_50 ">
			<div class="myleftpanelorder">
				<label class="myyellowfont" >${receivedate }</label>
			</div>	
		</div>
		
		</div>
		
		<div class="width_100">
		
												<table id="receivelistDTReceivestock" class="hero-settings-table hover-row" style="width:100%">
												  <thead>
													<tr>
														<th>PURCHASE SERVICE#</th>
														<th>RECEIVED ON</th>
														<th>BILLS#</th>
														<th>BILL AMOUNT</th>
													</tr>
												 </thead>
												 
												</table>
												
												<div class="clearfix"></div>
												<h6 class="bill_details_head"> <label id="selectedbillnotext"></label> Product Details</h6>
												
												
												 <table  id="itemlistDT" class="hero-settings-table  bodyTdPadd"	style="width:100%">
                              <thead>
                                  <tr >
                                      <th >
                                        #
                                      </th>
                                      <th >
                                           Item &amp; Description
                                      </th>
                                      <th >
                                        Batch No
                                      </th>
                                      <th   >
                                        Purchase Qty
                                      </th>
                                      <th  >
                                        Bonus Qty
                                      </th>
                                      <th >
                                           Rate
                                      </th>
                                      <th  >
                                            Amount
                                      </th>
                                  </tr>
                               <!--   <tr>
                                	<td >Full</td>
                                 	<td >Loose</td> 
                                 	<td >Total Qty</td>
                                 </tr> -->
                               </thead>
                               
                              </table>
		
			
		
			
		</div>
    </div>
  
  
    <div id="receiving" class="tab-pane fade in active">
    	<div class="width_100">
		<div class="width_100 mypadd10-0">
			<a class="mybackbtn" href="purchaseorderview?pid=${purchaseid }">< Back</a>
		</div>
		<div class="width_50">
			<p class="myorderhead"> Order # ${purchaseorderno } | <label class="supplier_name_text"></label></p>
			
			<input class="form-control form-white" id="purchaseid" type="hidden" readonly="readonly" value="${purchaseid }">
	        <input class="inputstyle" id="purchaseorderno" type="hidden" readonly="readonly" value="${purchaseorderno }">
	        <input class="form-control form-white " id="purchaseorderhdrno" type="hidden" readonly="readonly" value="${headerid }">
	        <input class="form-control form-white" id="oprntext" type="hidden" readonly="readonly" value=${oprn }>
	        <input class="form-control form-white" id="pidtext" type="hidden" readonly="readonly">
	        <input class="form-control form-white" id="taxidtext" type="hidden" readonly="readonly" value="${taxid }">
			<input class="form-control form-white" id="currsymboltext" type="hidden"  value="${currencycode }">
			<input class="form-control form-white" id="currdecimaltext" type="hidden" value="${currencydecimal }">
			
		</div>
		<div class="width_50 ">
			<div class="myleftpanelorder">
				<!-- <label class="radio-inline"><input style="margin-top: 6px;" type="radio" name="taxper" value="exclusive" >Exclusive Tax</label> -->
				<label class="radio-inline"><input style="margin-top: 6px;" type="radio" name="taxper" value="inclusive" checked>Inclusive Tax</label>
				<!-- <label class="radio-inline"><input style="margin-top: 6px;" type="radio" name="taxper" value="both">Both</label> -->
				<label >  | </label>
				<label class="myyellowfont" ><input class="hero-form-control form-white datepicker " placeholder="Receive Date" type="text" id="receivedate" value="${receivedate }"></label>
			</div>	
		</div>
		
		</div>
		
	<div class="width_100 gray-font white-bg content-font-size content-div-heightnw" id="style-5">
		<div class="width_100">
			<input class="form-control" id="recvstockcount" name="recvstockcount" type="hidden" value="${ receivestockList.size()}"></input>
			<table class="table myorderTable " id="receivestocktable">
				<thead>
					<tr>
					<td colspan="13" style="width:18%;padding:5px 0px;text-align:left;padding-left:5px;">Products</td>	 
					</tr>
					<tr>
					<td colspan="4" class="no-padding" style="width:20%">Receiving Qty</td>
					<!-- <td rowspan="2" style="width:7%">Bonus Qty </td> -->
					<!-- <td rowspan="2" style="width:12%">Batch/  Lot No</td> -->
					<td rowspan="2" style="padding:10px 0px;">HSNCODE</td>
					<!-- <td rowspan="2" style="padding:10px 0px;">Product Barcode</td> -->
					<td rowspan="2" style="width:15%">Expiry Date </td> 
					<td rowspan="2" style="width:12%">Purchase Unit Price</td>
					<!-- <td rowspan="2" style="width:15%;padding:10px 0px;">Sale Price </td> -->
					<td rowspan="2" class="inclusivetd" style="display:none;width:12%;padding:5px 0px;">CGST % </td>
					<td rowspan="2" class="sgsttd" style="display:none;width:12%;padding:5px 0px;">SGST % </td>
					<td rowspan="2" style="width:10%;padding:10px 0px;">Subtotal</td>
					</tr>
					 <tr>
                       <!--  <td class="no-padding">Full</td>
                        <td class="no-padding">Loose</td> -->
                        <td class="no-padding">Total</td>
                        <td class="no-padding">UOM </td>
                         <td class="no-padding">Pending QTY </td>
                        <td class="no-padding">Receiving QTY </td>
                    </tr>		 										
				</thead>
											
				<tbody> 
					<c:forEach items="${receivestockList}" var="receivestock" > 
					
					<tr>
						<td  colspan="10"  style="text-align:left;color:#8cbde8;" id="productname">
							<label class="productnamelabel"> ${ receivestock.productname}</label>
						</td>
						</tr>
						
				<tr class="receiverow" >
							<td>
							<input class="form-control" id="productid" name="productid" type="hidden" value="${ receivestock.productid}">
                			<input class="form-control" id="recvdqty${receivestock.index }" name="recvdqty" type="hidden" value="${ receivestock.receivedqty}">
                			<input class="form-control" id="ordered${receivestock.index }" name="ordered" type="hidden" value="${ receivestock.purquantity}">
							<input type="hidden" class="form-control" name="uompackingsel"  value="${ receivestock.ordereduompackingid}" id="uompackingselect${receivestock.index }" />
							<input type="hidden" onkeydown="if(event.key==='.'){event.preventDefault();}" class="form-control" value="${ receivestock.receivingqty}" name="fullqty" onblur="receivelooseqty(${receivestock.index })" onchange="receivelooseqty(${receivestock.index })"  id="recfullqtytext${receivestock.index }" style="margin: 10px 0 2px 0;padding: 2px 5px;" value="0">
							<input type="hidden" value="${ receivestock.orderedfulluom}" id="receivedfullromvalueforselect${receivestock.index }" />
							<select id="recfulluomsel${receivestock.index }" value="${ receivestock.orderedfulluom}" class="form-control" name="fulluomsel" onchange="receivelooseqty(${receivestock.index })" style="margin:0px 0 10px 0;padding:0;display:none">
								<c:forEach items="${receivestock.uomsel.fulluomPackingList}" var="fulluom" > 
									<option value="${fulluom.uomval }">${fulluom.uomdesc }</option>
								</c:forEach>
							</select>
							<input class="form-control" id="ordered${receivestock.index }" name="ordered" type="number" value="${ receivestock.purquantity}" disabled>
						<input type="hidden" onkeydown="if(event.key==='.'){event.preventDefault();}" name="looseqty" value="${ receivestock.receivingqty}" class="form-control" onblur="receivelooseqty(${receivestock.index })" onchange="receivelooseqty(${receivestock.index })" id="reclooseqtytext${receivestock.index }" style="margin:10px 0 2px 0;padding: 2px 5px;" value="0">
	                        <input type="hidden" value="${ receivestock.orderedlooseuom}" id="receivedlooseuomvalueforselect${receivestock.index }" />
	                        <select id="reclooseuomsel${receivestock.index }" value="${ receivestock.orderedlooseuom}" name="looseuomsel" class="form-control" onchange="receivelooseqty(${receivestock.index })" style="margin: 0px 0 10px 0;padding:0 ;display:none">
								<c:forEach items="${receivestock.uomsel.looseuomPackingList}" var="looseuom"  > 
									<option value="${looseuom.uomval }">${looseuom.uomdesc }</option>
								</c:forEach>
							</select>
						
						</td>
						
						
							<td>
							<label class="form-control form-controlas sze myuomlabel"  name="subtotal"><b>${ receivestock.netunitdesc}</b></label>
						</td>
						<td>
							<input class="form-control form-controlas sze"    type="number" value="${ receivestock.pendingqty}"
							disabled   style="width: 71px;   padding: 6px 4px;" >
						</td>
						<td>
							<input class="form-control form-controlas sze"  id="receivingqty${receivestock.index }" name="receivingqty" type="number" value="${ receivestock.receivingqty}"
							 onchange="checkRecQty('${receivestock.index }')" style="width:71px;   padding: 6px 4px;" >
						</td>
						 <td style="display:none">
							<input class="form-control  sze" type="number" id="bonusqty${receivestock.index }" name="bonusqty" value="${ receivestock.bonusqty}">
						</td> 
						<td style="display:none">
							<input class="form-control form-controlas sze" type="hidden" id="batchno${receivestock.index }" name="batchno" value="${ receivestock.batchno}" onblur="findbarcode('${receivestock.index }','${ receivestock.productid}')">
						</td>
						<td>
							<input class="form-control form-controlas szebar" type="text" id="hsncode${receivestock.index }" name="hsncode" value="${ receivestock.hsncode}" onblur="checkbarcode('${receivestock.index }','${ receivestock.productid}')">
						</td>
						
						<td style="display:none">
							<input class="form-control form-controlas szebar" type="hidden" id="barcode${receivestock.index }" name="barcode" value="${ receivestock.barcode}" onblur="checkbarcode('${receivestock.index }','${ receivestock.productid}')">
						</td>
						
						
						<td>
							<input class="form-control form-controlas sze datepicker" type="text" id="expirydate${receivestock.index }" name="expirydate" value="${ receivestock.expirydate}">
						</td>
						<td>
							<input class="form-control form-controlas " type="number" id="purchaseprice${receivestock.index }" value="${ receivestock.purchaseprice}"
	                              	onchange="calsubtotal('${receivestock.index }');changeSalePrice('${receivestock.index }')"	onblur="calsubtotal('${receivestock.index }');changeSalePrice('${receivestock.index }')" name="purchaseprice">
						
						</td>
						<td style="display:none">
							<input class="form-control form-controlas sze" type="number" name="salesprice" value="${ receivestock.salesprice}" id="salesprice${receivestock.index }"
	                               		onblur="calsubtotal('${receivestock.index }')">
	                               		
							<select class="selectNor form-control form-white " onchange="selectpaymenttype()" id="paymenttypeselect" >
						              <c:forEach items="${paymenttypeList}" var="paymenttype" > 
						            <option value="${paymenttype.value }"> ${paymenttype.label } </option>
						            </c:forEach>
						            </select>
						
						<input placeholder="" class="form-control form-white " type="number" id="payingamount">
						
						 <input placeholder="" class="form-control form-white " type="text"  id="creditedwithintext"  >
						</td>
									

	                   	
	   <td class="inclusivetd" style="display:none">			   	
				<c:choose>
				
						 <c:when test="${receivestock.opttype == 'Default'}">
						
						 <div id="tax1">
				                 	 ${ receivestock.tax}
					    <input type="hidden" id="taxdefaultoptionaltext${receivestock.index }" value="${receivestock.opttype}">
					    
						  <input class="form-control width " id="taxper${receivestock.index }" name="taxper"
						         type="hidden" value="${ receivestock.cgst}" data-value="${ receivestock.cgst}"
						         onload="calsubtotal('${receivestock.index }')">
						</c:when>    
										   
						<c:otherwise>
					        <select  class="form-control width " id="taxper${receivestock.index }" name="taxper"  
					        value="${ receivestock.tax}"  onchange="calsubtotal('${receivestock.index }')">
								
								<c:forEach items="${taxList}" var="tax"> 
					            <option value="${tax.value}" data-type="${tax.taxtype}"  data-value="${tax.taxrate}" ${tax.value == receivestock.purcgst ? 'selected' : ''}> ${tax.label}  </option>
					            </c:forEach>
					        </select>   
					    </c:otherwise>
			   </c:choose>
			
		</td> 			           
					  
					  
		 <td class="sgsttd" style="display:none">			   	
				<c:choose>
						<c:when test="${receivestock.opttype == 'Default'}">
						<div id="tax2">
					${ receivestock.tax2}
						<input class="form-control width " id="sgst${receivestock.index }" name="sgst" type="hidden"
						 value="${ receivestock.sgst}" data-value="${ receivestock.sgst}" onchange="calsubtotal('${receivestock.index }')">
						</c:when>    
										   
						<c:otherwise>
					        <select  class="form-control width " id="sgst${receivestock.index }" name="sgst"  value="${ receivestock.sgst}"  
					        onchange="calsubtotal('${receivestock.index }')">
								
							 <c:forEach items="${taxList}" var="tax"> 
					            <option value="${tax.value}" data-type="${tax.taxtype}" data-value="${tax.taxrate}" ${tax.value == receivestock.pursgst ? 'selected' : ''}> ${tax.label}  </option>
					            </c:forEach> 
					        </select>   
					    </c:otherwise>
			    </c:choose>
			</td> 			           
	                   	
						
						
						
						<td>
							<label class="form-control form-controlas sze mysubtotallabel" id="subtotal${receivestock.index }" 
							name="subtotal"><b>${ receivestock.subtotal}</b></label>
						</td>
					</tr>
					
					</c:forEach>
					
				</tbody>  
			</table>
			</div>
			
			
			
			
		
		
		<div class="width_100">
			<div class="width_100 common-total-style">
				<div class="width_50 min-1-height">
					<div class="width_100">
						<div class="width_50">
							<strong><p>Supplier Invoice Date <span style="color:red">*</span></p></strong>
						</div>
						<div class="width_50">
							<input  autocomplete="off" style="margin-top:0;margin-left:6px;" class="form-control form-white datepicker" type="text" id="supplierInvoiceDate" >
						</div>
					</div>
				</div>
				<div class="width_50">
					<div class="width_100">
						<div class="width_50">
							<p>Total Amount</p>
						</div>
						<div class="width_50 padd-right-20">
							<p><label id="totalamountdisptext">${totalamountdisp }</label></p>
						</div>
					</div>
				</div>
			</div>
			
			<div class="width_100 common-total-style" style="display:none">
				<div class="width_50 min-1-height">
					<div class="width_100">
						<div class="width_50">
							<p>Add Discount</p>
						</div>
						<div class="width_50">
							<div class="width_100">
								<div class="width_40">
								<input type="hidden" value="${discounttype }" id="editdisctype" class="display">
									<select style="margin-top:0;padding:5px;margin-left:6px;" class="form-control" id="discountselect"  value="${discounttype }" onchange="calculatetotal();">
									  <option value="%"> % </option>
									  <option value="Rs"> Rs </option>
									</select>
								</div>
								<div class="width_60">
									<input style="margin-top:0" class="form-control form-white" placeholder="0.00" type="number" id="discounttext" value="${discountvalue }" onblur="calculatetotal();">
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="width_50">
					<div class="width_100">
						<div class="width_50">
							<p>Discount</p>
						</div>
						<div class="width_50 padd-right-20">
							<p><label id="discountamounttext">${discountamount }</label></p>
						</div>
					</div>
				</div>
			</div>
			
			<div class="width_100 common-total-style exclusivetd">
				<div class="width_50 min-1-height">
					<div class="width_100">
						<div class="width_50">
							<p>Add Tax</p>
						</div>
						<div class="width_50">
							<input type="hidden" value="${taxList.size()}" id="totaltaxtext" class="display">
							<select style="margin-top:0;padding:5px;margin-left:6px;" class="form-control" id="taxselect" onchange="calculatetotal()">
						   		<c:forEach items="${taxList}" var="tax" > 
									<option value="${tax.value}"> ${tax.label} </option>
								</c:forEach>
					  		</select> 
					   		<c:forEach items="${taxList}" var="tax" > 
					  			<input type="hidden" value="${tax.taxrate }" id="taxrate${tax.index }">
					  			<input type="hidden" value="${tax.value }" id="taxid${tax.index }">
					  		</c:forEach>
						</div>
					</div>
				</div>
				<div class="width_50">
					<div class="width_100">
						<div class="width_50">
							<p>TAX</p>
						</div>
						<div class="width_50 padd-right-20">
							<p><label id="taxamounttext">${taxamount}</label></p>
						</div>
					</div>
				</div>
			</div>
			
			<div class="width_100 common-total-style">
				<div class="width_50 min-1-height">
					<div class="width_100">
						<div class="width_50">
							<strong><p>Supplier Invoice no <span style="color:red">*</span></p></strong>
						</div>
						<div class="width_50">
							<input autocomplete="off" style="margin-top:0;margin-left:6px;" class="form-control form-white" value="" type="text" id="supplierInvoiceNo" >
						</div>
					</div>
				</div>
				<div class="width_50">
					<div class="width_100">
						<div class="width_50">
							<p>Total With Decimals</p>
						</div>
						<div class="width_50 padd-right-20">
							<p><label id="totalWithDecimaltext">0.00</label></p>
						</div>
					</div>
				</div>
			</div>
			
			<div class="width_100 common-total-style" style="display:none">
				<div class="width_50 min-1-height">
					<div class="width_100">
						<div class="width_50">
							<p>Add Shipping Cost</p>
						</div>
						<div class="width_50">
							<input style="margin-top:0;margin-left:6px;" class="form-control form-white" value="${shippingcost }" type="number" id="shippingcodttext" onkeyup="calculatetotal();">
						</div>
					</div>
				</div>
				<div class="width_50">
					<div class="width_100">
						<div class="width_50">
							<p>Shipping Cost</p>
						</div>
						<div class="width_50 padd-right-20">
							<p><label id="shippingcosttext">${shippingcostdisp }</label></p>
						</div>
					</div>
				</div>
			</div>
			
			<div class="width_100 common-total-style" >
			<div class="width_50 min-1-height">
					<div class="width_100">
						<div class="width_50">
							<p> Terms & Conditions<span style="color:red">*</span></p>
						</div>
						<div class="width_50">
						    <select id='termsidselect' class="form-control form-white" style="margin-top:0;margin-left:6px">
								    	<option value="0">-- select --</option>
									    <c:forEach items="${termsList}" var="terms" >                  
									        <option value="${terms.value}">
									            ${terms.label}
									        </option>                    
									    </c:forEach>
									</select>
						</div>
					</div>
				</div>
			
			<%-- 		<div class="width_50">
					<div class="width_100">
						<div class="width_50">
							<p>Shipping Cost</p>
						</div>
						<div class="width_50 padd-right-20">
							<p><label id="shippingcosttext">${shippingcostdisp }</label></p>
						</div>
					</div>
				</div> --%>
			</div>
			

			
			<div class="width_100 common-total-style">
				<div class="width_50 min-1-height">
					<div class="width_100">
						<div class="width_50">
							<p>Notes (for Internal Use)</p>
						</div>
						<div class="width_50 padd-right-20">
							<textarea style="margin-top:0;margin-left:6px;width:100%;" name="comment"  class="form-control form-white" id="notestext">${notes }</textarea>
									</textarea>
						</div>
					</div>
				</div>
				
				<div class="width_50">
					<div class="width_100">
						<div class="width_50">
							<p>Grand Total</p>
						</div>
						<div class="width_50 padd-right-20">
							<p><label id="grandtotaltext">${grandtotalamount }</label></p>
						</div>
					</div>
				</div>
				<div class="width_50">
					<div class="width_100 ">
					    <button type="button" class="btn btn-default  margin pull-right"  onclick="cancelpurchaseview();">Cancel</button>
						<button type="button" class="btn btn-primary margin pull-right" style="margin-right:10px;"  onclick="savereceivestock();">Save</button>
						
					</div>
				</div>
			</div>
			
			
		</div>
		
		</div>
		
		</div>
		
    </div>
    
    


  </div>
</div>


</div>
</div>
</div>

									<script>
                                    $(document).ready(function(){
                                    	var selvalue = $('input[name="taxper"]:checked').val();
                                    	if(selvalue == "exclusive"){
                                			$(".inclusivetd").css({'display':'none'});
                                			$(".exclusivetd").css({'display':'block'});
                                			$(".sgsttd").css({'display':'none'});
                                		}else if(selvalue == "inclusive"){
                                			$(".inclusivetd").css({'display':'table-cell'});
                                			$(".exclusivetd").css({'display':'none'});
                                			$(".sgsttd").css({'display':'table-cell'});
                                		}
                                		
                                		if(selvalue == "both"){
                                			$(".inclusivetd").css({'display':'table-cell'});
                                			$(".exclusivetd").css({'display':'block'});
                                			$(".sgsttd").css({'display':'table-cell'});
                                		}
                                    	console.log(selvalue)
                                    	$('input[name="taxper"]').on('change', function(){
                                    		var selvalue = $('input[name="taxper"]:checked').val();
                                    		if(selvalue == "exclusive"){
                                    			$(".inclusivetd").css({'display':'none'});
                                    			$(".exclusivetd").css({'display':'block'});
                                    			$(".sgsttd").css({'display':'none'});
                                    			//taxpercalculate(index);
                                    			
                                    		}else if(selvalue == "inclusive"){
                                    			$(".inclusivetd").css({'display':'table-cell'});
                                    			$(".exclusivetd").css({'display':'none'});
                                    			$(".sgsttd").css({'display':'table-cell'});
                                    			//calsubtotal(index); 
                                    			
                                    		}
                                    		
                                    		if(selvalue == "both"){
                                    			$(".inclusivetd").css({'display':'table-cell'});
                                    			$(".exclusivetd").css({'display':'block'});
                                    			$(".sgsttd").css({'display':'table-cell'});
                                    			//taxpercalculate(index);
                                    			
                                    		}
                                    	});
                                    	
                                    });
                                    </script>
                                    
                            <script>
                                      
                            $(document).ready(function() {
                            	var selectedvalue =$("#editdisctype").val();
                            	
                            	if(selectedvalue == "Rs")
                            		{
                            		 $('#discountselect option[value="Rs"]').attr('selected', 'selected');
                            		}
                            	else
                            		{
                            		 $('#discountselect option[value="%"]').attr('selected', 'selected');
                            		}
                         
                            });

                             </script>


                       <!--  <div class="card-header">
                            <strong class="card-title"><i class="fa fa-table"></i> RECEIVE STOCK</strong>
                        </div> -->
                        <%-- <div class="card-body">
                          <!-- Credit Card -->
                          <div id="pay-invoice">
								<div class="col-md-12">	
									<div class="col-md-6 form-group">
									<label for="Purchase Receive#">Purchase Receive#</label>
								    <!-- <input type="text" placeholder="0" class="form-control inpttypestyle"  > -->
								    
								    </div>									
					                <div class="col-md-6 form-group">
									<label for="Receive Date">Receive Date</label>
									<!-- <input class="form-control form-white datepicker" placeholder="Receive Date" type="text" id="receivedate"> -->
									
                                    </div>	
                                    <div class="col-md-6 form-group">
                                    <label for="Receive Date">Select tax type</label>
                                    <span class="checkbox-inline akucheckbox-inline"><input type="radio" name="taxper" value="exclusive" checked> Exclusive</span>
		                               <span class="checkbox-inline akucheckbox-inline1"><input type="radio" name="taxper" value="inclusive" > Inclusive</span>
		                            </div>   
                                    
                                    
                                    
								</div>
                                <div class="col-md-12">	 
                                  
						            <table class="tablenewa"  >
														  <thead>
															<tr>
																  <th>Product</th>
																  <th>UOM</th>
									                              <th style="width: 300px;">
									                              <table border="0">
									                              <tr align="center">
									                              <td colspan="3">
									                              Ordered
									                              </td>
									                              </tr>
                              		<tr align="center">
                              		<td style="width: 100px;">Full </td>
                              		<td style="width: 100px;">&nbsp;Loose </td>
                              		<td style="width: 100px;">&nbsp;Total Qty </td>
                              		</tr>
                              		</table> </th>
									                              <th>Received </th>                              
									                              <th><table border="0">
									                              <tr align="center">
									                              <td colspan="3">
									                              Receiving Qty
									                              </td>
									                              </tr>
                              		<tr align="center">
                              		<td style="width: 100px;">Full </td>
                              		<td style="width: 100px;">&nbsp;Loose </td>
                              		<td style="width: 100px;">&nbsp;Total Qty </td>
                              		</tr>
                              		</table></th>
																  <th>Bonus Qty </th>
									                              <th>Batch/Lot No</th>
									                              <th>Product Barcode</th>
									                              <th>Expiry Date </th> 
									                              <th>Purchase Unit Price</th>
									                              <th>Sale Price </th>
									                            
									                               <th class="inclusivetd" style="display:none">Tax </th>
									                              
									                              <th>Subtotal</th>
															</tr>
														  </thead>
															<tbody>
																
																 <c:forEach items="${receivestockList}" var="receivestock" > 
																 
              					<tr class="receiverow3"> 
                					<td style="width:200px;" >
                						
                					</td>
							  		
                              		<td style="width:50px;"  style="width: 300px;">
                              		<table border="0">
									<tr align="center">
                              		<td style="width: 100px;"> </td>
                              		<td style="width: 100px;"></td>
                              		<td style="width: 100px;"> </td>
                              		</tr>
                              		</table>
                              		</td>
                              		<td style="width:50px;"  align="center"></td>                              
									<td>
									<table border="0">
									<tr align="center">
									
                              		<td style="width: 100px;">
	                              		
									 </td>
                              		<td style="width: 100px;">
	                              		
                              		</td>
                              		<td style="width: 100px;">
									</td>
                              		</tr>
                              		</table>
									
									</td>
                              		<td></td>
								   	<td></td>
								   	<td></td>
	                              	<td></td>
	                              	<td>
	                              		
	                              	</td>
	                              	<td>
	                              		
	                               	</td>
	                               		
	                              	<td align="right">
	                              		
	                              	</td>
              					</tr>
            
            				</c:forEach> 
															</tbody>
								    </table>
									 <table style="float:right;width: 100%;table-layout: fixed;word-wrap: break-word;" cellspacing="0" cellpadding="0" border="0">
															<tbody>
																   <tr>
																	   <td style="text-align:right;padding:5px 10px 5px 0px;font-size: 10pt;width:60%;">
																		  <span class="pcs-label">Total Amount	</span>
																	    </td>
																	    <td style="text-align:right;width:40%;">
																		 <!--  <span id="tmp_entity_date"><b>Rs. 0.00<b></span> -->
																		 
																	    </td>
																    </tr>

																    <tr>
																	   <td style="text-align:right;padding:5px 10px 5px 0px;font-size: 10pt;width:60%;">
																		  <span class="pcs-label" >Discount	</span>
																	    </td>
																	    <td style="text-align:right;width:40%;">
																		  <!-- <span id="tmp_due_date"><b>Rs. 0.00</b></span> -->
																		  
																	    </td>
																    </tr>
																    <tr>
																	    <td style="text-align:right;padding:5px 10px 5px 0px;font-size: 10pt;width:60%;">
																		  <span class="pcs-label" >Tax</span>
																	    </td>
																	    <td style="text-align:right;width:40%;">
																		  <!-- <span id="tmp_due_date"><b>Rs. 0.00</b></span> -->
																		   
																	    </td>
																    </tr>
																    <tr>
																	    <td style="text-align:right;padding:5px 10px 5px 0px;font-size: 10pt;width:60%;">
																		  <span class="pcs-label" >Shipping Cost</span>
																	    </td>
															 		    <td style="text-align:right;width:40%;">
																		 <!--  <span id="tmp_due_date"><b>Rs. 0.00</b></span> -->
																		 
																	    </td>
																    </tr>
																    
																    <tr>
																	   <td style="text-align:right;padding:5px 10px 5px 0px;font-size: 10pt;width:60%;">
																		<span class="pcs-label" >Total With Decimals</span> 
																		 
																	    </td>
																	    <td style="text-align:right;width:40%;"> 
																		 <!--  <span id="tmp_due_date"><b>Rs. 0.00</b></span> -->
																		  
																	    </td> 
																    </tr>
																    
																    <tr>
																	   <td style="text-align:right;padding:5px 10px 5px 0px;font-size: 10pt;width:60%;">
																		<span class="pcs-label" >Grand Total</span> 
																		 
																	    </td>
																	    <td style="text-align:right;width:40%;"> 
																		 <!--  <span id="tmp_due_date"><b>Rs. 0.00</b></span> -->
																		  
																	    </td>
																	    
																    </tr>
																    
															</tbody>
								    </table>
									
						        </div>
						         </div>
								 <div class="col-md-12 ">	   
						            <div class="col-md-3 ">
						            
									    <label for="Discount%">Discount%</label>
									    </div>
											<div class="width-100">
												<div class="width-20">
													<!-- <select class="form-control inpttypestyle">
														<option value="Rs.">RS.</option>
													</select> -->
													<div class="col-md-3 form-group">
													
                         </div>
                           </div>
                        
												
											</div>
									
									</div> 
									 <div class="col-md-12 exclusivetd">
									 <div class="col-md-3">
									    <label for="Discount%" >Order Tax</label>
									    </div>
									    <div class="col-md-6">
									    
					  </div>
									 </div>
						        
								<div class="col-md-12">	  
									<div class="col-md-3">
									  <label for="Shippingcost">Shipping cost</label>
									  </div>
									  <div class="col-md-6">
									  <!-- <input type="number" placeholder="0" class="form-control inpttypestyle"  > -->
									   
						           </div>
						           </div>
									<div style="display:none" class="col-md-12">
									<div class="col-md-6">
									<label for="notes">Notes (For Internal Use)</label>
									</div>
									<!-- <textarea rows="1" col="40" class="form-control inpttypestyle"> -->
									<div class="col-md-6">
									
									</div>
									</div>
									
						        
						       
								
								<div class="col-md-12" >

			                      </div>
								
						  </div>
						  

                        </div>
                        </div>
                    </div> --%>
                    <style>
                    
 table  thead  tr  th {
 
    vertical-align: bottom;
    border-bottom: 1px solid #E3E3E3;
    color: #666666;
    font-family: "Roboto","Helvetica Neue","Open Sans",Arial,sans-serif !important;
    padding: 8px 10px;
    line-height: 24px;
    white-space: nowrap;
    font-size: 12px;
    font-weight: 600;
    text-transform: uppercase;
    background-color: #f5f5f5;
}
div#pay-invoice {
    width: 100%;
    overflow-x: scroll;
    margin-bottom: 20px;
}

.margin{
margin-top:20px;}
input[type="text"].form-control, input[type="email"].form-control, input[type="number"].form-control, input[type="password"].form-control, textarea.form-control, select.form-control {
    
     -webkit-box-shadow: none !important;
    background-color: #f7f7f7;;
    border: 1px solid #ECEDEE;
    box-shadow: none !important;
    color: #555555;
    display: inline-block;
    font-size: 13px;
    height: auto;
    line-height: normal;
    padding: 6px 12px;
    vertical-align: middle;
    width: 90%;
    -webkit-border-radius: 0px;
    -moz-border-radius: 0px;
    border-radius: 0px;
    -webkit-transition: all 0.2s ease-out;
    -moz-transition: all 0.2s ease-out;
    -o-transition: all 0.2s ease-out;
    -ms-transition: all 0.2s ease-out;
    transition: all 0.2s ease-out;
     margin-top:18px;
    }

    
.inputstyle{
    border: 0;
    outline: 0;
    background: transparent;
    border-bottom: 1px solid #d0d0d0;
    border-left: none;
    border-right: none;
    border-top: none;
    box-shadow: none !important;
    border-radius: 0px;
    font-size: 15px;
    margin-top: 31px;
}
.content-div-height{
height:700px;
}

                 </style>
                    <jsp:include page="../../template/footer.jsp" />
</body>
</html>