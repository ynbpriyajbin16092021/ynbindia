<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Product View</title>
<script type="text/javascript" src="../js/forms/masters/view/mastersview.js"></script>
</head>
<body onload="loadproductview()">

<jsp:include page="../../template/header.jsp" />
    

				  
				  
				  <!-- Main content starts here -->
				  
  
				  
                    <div class="card">
                        <div class="card-header">
                            <strong class="card-title"><i class="fa fa-shopping-cart"></i> Product List</strong>
							
					      </div>
                          
                        <div class="card-body" style="padding:0;">
                          <!-- Credit Card -->
						  
                          <div id="pay-invoice">
						       <div class="width_100" >
							         <br />
									 <div class="width_25 " >
									 <div class="width_100" >
							         <a href="addproduct?productid=0" class="btn btn-secondary pull-right btnmove">New <i class="fa fa-plus-circle"></i></a>
							         <span class="dropdown newDrop arrowRight">
                              <button  class="btn btn-default dropdown-toggle" type="button" data-toggle="dropdown">
                                <i class="fa fa-bars" aria-hidden="true"></i>
                              </button>

                               <ul class="dropdown-menu pull-right">              
                                       <li><a href="importproduct"> 
					      	<i class="fa fa-download" aria-hidden="true"></i>
 							Import Product
 						  </a></li>  
 						  
 						  <li class="divider"></li>
					      <li><a href="exportsampleformats"> 
					      	<i class="fa fa-upload" aria-hidden="true"></i>
 							Export Product
 						  </a></li>           
                                </ul>
                              </span>
									 </div>
									    <div id="content-1" class="panel-content contentFix">
      


        <div class="rowNor ">  
           
 <input type="hidden" id="fistproductid" value="<%=request.getParameter("prodid") %>">
 <input type="hidden" id="productlistsize" value="${productList.size() }">
                            <table class="table table-hover selectCheckTabel">
           <c:forEach items="${productList}" var="product" >
           <tr> 
                                <td width="50px"> <input type="radio" id="productcheck${product.index}" value="${product.value }" name="productnameid" 
                                onclick="getproductdetails(this.value)"> </td> 
                                <td> ${product.label }</td>
                              </tr>
                              
          </c:forEach>                                                             
                            </table>
		  

                            <div class="paginationView" align="right">
                              <span class="tabelCount pull-left">Total Count : 4 </span> 
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
									 <div class="width_75 bordercls" >
									    <div class="col-md-12" >
										<div class="col-md-4" >
										<p class="select"><b>PRODUCT NAME</b></p>
										</div>
										
									        <div class="col-md-8 ">
												<button class="btn btn-outline-secondary pull-right" ><i class="fa fa-remove"></i></button>
												
												
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
                                <tr> <td><b>Unit of Measure </b></td> <td id="uomtd">  </td> </tr> 
                                <tr> <td><b>Notify Below Quantity </b></td> <td id="notifytd">  </td>  </tr>
                                <tr> <td><b>Manufacturer</b></td> <td id="manufacturertd"> </td>  </tr>
                                <tr> <td><b>Status </b></td> <td> 
                                      <div type="button" class="badge badge-danger" id="statusinactivetd">Inactive</div> 
                                      <div type="button" class="badge badge-primary" id="statusactivetd">Active</div>
                                    </td>   
                                 </tr>
                                <tr> <td><b>Product Description </b></td> <td id="descriptiontd"> </td>  </tr>
                              </table>
																											
												</div>
											
                                                <div class="col-md-4 stydiv">
													<p class="parasty">Available</p>
													<p><b>21</b></p>
													<p class="parasty" >Quantity to be<br> Shipped</p>
													<p><b>21</b></p>
													<p class="parasty">Quantity to be<br>Received</p>
													<p><b>0</b></p>
                                               
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
														<td>PURCHASE QTY</td>
														<td>PURCHASE VALUE</td>
														<td>PURCHASE VAT</td>
														<td>TOTAL COST</td>
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
                                   <th >Purchase quantity </th>  
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
					  <!-- Main content ends here -->
					
       <jsp:include page="../../template/footer.jsp" />         
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
