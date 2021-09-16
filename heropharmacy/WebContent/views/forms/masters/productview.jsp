<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Product View</title>
<jsp:include page="../template/library.jsp" />
<script type="text/javascript" src="../js/forms/masters/view/mastersview.js"></script>
<link rel="stylesheet" href="../../heroadmin/resources/css/stylebass.css">
	<link rel="stylesheet" href="../../heroadmin/resources/css/stylej.css">
</head>
<body onload="loadproductview()">

<jsp:include page="../template/header.jsp" />
  
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
								
								
<%-- <div class="width_100 gray-font white-bg content-font-size">
									<div class="col-md-12 full-padding-20">
							
					<div class="width_25" >
						 <div class="width_100" >
							   <a href="addproduct?productid=0" class="<div class="clearfix"></div>
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
							<div class="width_100"> --%>
								
								
<div class="width_100 gray-font white-bg content-font-size">
									<div class="col-md-12 full-padding-20">
							   
						<div class="width_25">
						 <a href="product" class="button-bg button-space pull-right scroll-sec-right">New 
						 <i class="fa fa-plus-circle"></i></a> 
						 
						<div id="content-1" class="panel-content contentFix">
                               <div class="width_100 gray-font white-bg content-font-size">
									<div class="col-md-12 full-padding-20"> 
                                         <i class="fa fa-shopping-cart"></i> Product List </div>
					               
								        <div class="rowNor ">  
								             <input type="hidden" id="fistproductid" value="<%=request.getParameter("prodid") %>">
								             <input type="hidden" id="productlistsize" value="${productList.size() }">
								                    
								                    <table class="table table-hover selectCheckTabel">
								                    <tbody class="common-scroll-table" id="style-5">
								                            <c:forEach items="${productList}" var="product" >
								                            <tr> 
								                                <td width="50px"> <input type="radio" id="productcheck${product.index}" 
								                                value="${product.value }" name="productnameid"  onclick="getproductdetails(this.value)"> </td> 
								                                <td> ${product.label }</td>
								                            </tr>
								                            </c:forEach> </tbody>                                                            
								                    </table>
										  
								
								                            <div class="paginationView" align="right">
								                              <span class="tabelCount pull-left">Total Count :${productList.size() } </span> 
								                              <span class=" pull-right"> 
								                                    <select class="selectNor ">
								                                    <option>10 per page</option>
								                                    <option>20 per page</option>
								                                    <option>30 per page</option>
								                                    <option>50 per page</option>
								                                    </select>
								                              </span>
								                            </div>
								         </div>
								        </div>
								        </div>
								        </div>
							
					<div class="width_75 gray-font white-bg content-font-size content-div-heightnw"  id="style-5"> 
									    <div class="col-md-12" >
										
										<h3>  <strong id="postrongid"></strong></h3>
										
										
						  <div class="col-md-12" >
							   <div class="col-md-4" >
										<p class="select"><b>PRODUCT NAME</b></p>
							  </div>
										
							  <div class="col-md-8 ">
								<a href="product" >	<button class="button-bg button-space pull-right" >Go Back</button></a>
							  </div> 
											
						 </div>
										
										
					<div class="clearfix"></div>
						 <div class="col-md-12">
										 <ul class="nav nav-tabs">
											<li class="nav"><a data-toggle="tab" href="#overview">Overview</a></li>
											<li class="nav"><a data-toggle="tab" href="#Batches">Batches</a></li>
                                            <li class="nav"><a data-toggle="tab" href="#Purchases">Purchases</a></li>
                                            <li class="nav"><a data-toggle="tab" href="#Sales">Sales</a></li>
                                            <li class="nav"><a data-toggle="tab" href="#Transfer">Transfer</a></li>
										  </ul>
                                       
                                       
                                       
                    <div class="tab-content">
                         <div class="tab-pane active " id="overview">
                              <div class="col-md-8">
				                   <table class="table brdr ">
                                          <tr> <td><b>Product Name </b></td> <td id="productnametd">  </td> </tr>
								          <tr> <td><b>SKU </b></td> <td id="skutdid">  </td></tr>
                                          <tr> <td><b>Product Type </b></td> <td id="producttypetd">  </td>  </tr>
                                           <tr> <td><b>HSNCODE </b></td> <td id="hsncode">  </td>  </tr>
                                          <tr> <td><b>Unit of Measure </b></td> <td id="uomtd">  </td> </tr> 
                                          <tr> <td><b>CGST % </b></td> <td id="cgstid">  </td> </tr> 
                                          <tr> <td><b>SGST % </b></td> <td id="sgstid">  </td> </tr> 
                                          <tr> <td><b>Notify Below Quantity </b></td> <td id="notifytd">  </td>  </tr>
                                          <tr> <td><b>Manufacturer</b></td> <td id="manufacturertd"> </td>  </tr>
                                          <tr> <td><b>Status </b></td> 
                                          
                                               <td> 
                                               <div type="button" class="badge badge-danger" id="statusinactivetd">Inactive</div> 
                                               <div type="button" class="badge badge-primary" id="statusactivetd">Active</div>
                                              </td>   
                                         </tr>
                                         <tr> <td><b>Product Description </b></td> <td id="descriptiontd"> </td>  </tr>
                                   </table>
																											
							  </div>
											
                                                <div class="col-md-4 stydiv">
													<p class="parasty">Available</p>
													  <p class='crouupCount' id="availstockid"> </p>
													<p class="parasty" >Quantity to be<br> Shipped</p>
													  <p class='crouupCount' id="tobeshippedtd"> </p>
													<p class="parasty">Quantity to be<br>Received</p>
													  <p class='crouupCount' id="tobemovedtd"></p>
                                               
                                                 </div>
                          </div>
                                            
                                            
                                            
                                            
                                            <div id="Batches" class="tab-pane ">
											    <table id="batchlistDT" class="table table-striped table-bordered dt-responsive nowrap example2" style="width:100%">
												  <thead>
													<tr> 	 	      	 		 
														<th>BATCH NO</th>
														<th>INITIAL QTY</th>
														<th>AVL QTY</th>
														<th>MFD</th>
														<th>EXP</th>
														<th>UNITPRICE</th>
														
													</tr>
												  </thead>
													
												</table>
											</div>
											
											<div id="Purchases" class="tab-pane ">
											    <table id="purchaselistDT" class="table table-striped table-bordered dt-responsive nowrap example3" style="width:100%" >
												  <thead>
													<tr> 	 	      	 		 
														<td>DATE</td>
														<td>PURCHASE ORDER</td>
														<td>SUPPLIER</td>
														<td>RECEIVED QTY</td>
														<td>PURCHASE RATE</td>
														<td>TAX AMOUNT</td>
														<td>TOTAL </td>
													</tr>
												  </thead>
													
												</table>
											</div>
											
											<div id="Sales" class="tab-pane ">
											     <table id="salesDT" class="table table-striped table-bordered dt-responsive nowrap example4" style="width:100%">
												        <thead>
													           <tr> 	 	      	 		 
														            <th> Batch No </th>  
                                                                    <th> Customer Name </th>  
                                                                    <th> Purchase quantity </th>  
                                                                    <th> storename </th>  
                                                                    <th> total cost</th> 
														
													           </tr>
												        </thead>
													
												 </table>
											</div>
											
											<div id="Transfer" class="tab-pane ">
											     <table id="transferlistDT" class="table table-striped table-bordered dt-responsive nowrap example5" style="width:200%">
												        <thead>
													             <tr> 	 	      	 		 
														             <th>DATE</th>
														             <th>TRANSFER ORDER#</th>
														             <th>QUANTITY TRANSFERED</th>
														             <th>STORE#</th>
														             <th>BATCH</th>
														             <th>TANSFERED VALUE</th>
														
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
						</div> 
									
					  <!-- Main content ends here -->
					
<jsp:include page="../template/footer.jsp" />     
	<script>
    (function($){
      $(window).on("load",function(){
        
        $("#content-1").mCustomScrollbar({
          theme:"minimal-dark"
        });

        $("#content-2").mCustomScrollbar({
          theme:"minimal-dark"
        });
        
      });
    })(jQuery);
  </script>

</body>
</html>
