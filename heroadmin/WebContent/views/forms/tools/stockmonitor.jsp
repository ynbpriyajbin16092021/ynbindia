<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Stock Monitor</title>

<script type="text/javascript" src="../js/forms/tools/stockmonitor.js"></script>

</head>
<body onload="loadstockmonitor();">

<jsp:include page="../template/header.jsp" />
<div class="card">
                                          <div class="card-header">
												<strong class="card-title"> <i class="fa fa-shopping-bag"></i> Store Stocks</strong>
										  </div>
													<div class="card-body">
														<div id="pay-invoice">
															<div class="filterSection displayTabel">
																	<div class="col-md-12">	
																			<div class="col-md-2 form-group">
																			   Filter By : 
																			 </div>
																			  <div class="col-sm-2" id="storediv">
        <select class="form-control form-white selectNor" id="storeselect" onchange="onchangefields();">
        
          <c:forEach items="${storeList}" var="store" >                  
        <option value="${store.value}">
            ${store.label}
        </option>                    
    </c:forEach>
        </select>
       </div>
       <div class="col-sm-2">
        <select class="form-control form-white selectNor" id="manufacturerselect" onchange="onchangefields();">
        
          <c:forEach items="${manufacturerList}" var="manufacturer" >                  
        <option value="${manufacturer.value}">
            ${manufacturer.label}
        </option>                    
    </c:forEach>
        </select>
       </div>
       <div class="col-sm-2">
        <select class="form-control form-white selectNor" id="categoryselect" onchange="onchangefields();">
        
          <c:forEach items="${categoryList}" var="category" >                  
        <option value="${category.value}">
            ${category.label}
        </option>                    
    </c:forEach>
        </select>
       </div>
																			<!--  <div class="col-md-3" id="storeselectdiv" style="display:&quot;&quot;">
																				 <select id="reportsstoreidselect" class="form-control form-white selectSer select2-hidden-accessible" tabindex="-1" aria-hidden="true">
																					  <option value="1">India Store</option>                    
																					  <option value="2">Dubai Store</option>                    
																					  <option value="4">Singapore</option>                    
																					  <option value="5">Chennai Store</option>                    
																					  <option value="11">Madurai medical</option>                    
																					  <option value="14">Kerala store</option>                    
																					  <option value="15">Kochin shop</option>                    
																					  <option value="16">Gayu shop</option>                    
																					  <option value="18">Saistore</option>                    
																				  </select>
																				 
																			</div> -->
													                       <!--  <div class="col-md-3 form-group">
                                                                             <select class="form-control inpttypestyle">
																				<option value="PHARMASIST">All</option>
																				<option value="PHYSICIAN">Herbzalive</option>
																			  </select>   
																		     </div>
														                      <div class="col-md-4 form-group">
																					<select class="form-control inpttypestyle">
																						<option value="PHARMASIST">All</option>
																						<option value="PHYSICIAN">Tablets</option>
																						<option value="PHARMASIST">Syrup</option>
																						<option value="PHARMASIST">Nutricians</option>
																					</select>
																			 </div> -->
																	</div>
												                    <div class="col-md-12">
												                          
																			    <table id="stockmonitorDT" class="table table-striped table-bordered dt-responsive nowrap example" style="width:100%">
																					<thead>
																					   <th>Product Name</th>
																					   <th>Category</th>
																					   <th>Unit Type</th>
																					   <th>Manufacture Name</th>
																					   <th>Stock</th>	
																					</thead>
																			       <!--  <tbody>
																						<tr>
																							<td data-toggle="modal" href="#myModal"  class="tdclick">Cough Syrup</td>
																							<td>Syrup</td>
																							<td>250 ml;</td>
																							<td>Herbzalive</td>
																							<td>10</td>
																						</tr>
																				        <tr>
																							<td>Crocin</td>
																							<td>Tablets</td>
																							<td>10 mg;</td>
																							<td>Herbzalive</td>
																							<td>2</td>
																						</tr>
																						<tr>
																							<td>Paracitamal</td>
																							<td>Tablets</td>
																							<td>5 mg;</td>
																							<td>Herbzalive</td>
																							<td>1</td>
																						</tr>
																					</tbody> -->
																				</table>
																		
																	</div>
																</div>   
															</div>
											</div>
									</div>
									
                <script type="text/javascript">
				$(document).ready(function(){
					
				jQuery(".tdclick").click(function(){
					jQuery('#myModal').modal({backdrop: true});
				   
					
					});	
					
				});
				</script> 		
				<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog" tabindex="-1" id="myModal" class="modal fade modalForgetPassword" >
		              <div class="modal-dialog widthModalForget">
		                  <div class="modal-content" style="width:800px;" >
		                        <div class="modal-header">
							      <h5 class="modal-title">MB CABS BATCHES</h5>
		                          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		                        </div><br>
							    <div class="modal-body">
		                            <table id="itemDetailDT" class="table table-striped table-bordered dt-responsive nowrap example1" style="width:100%">
											<thead>
												<tr>
												    <th>CATEGORY</th>
													<th>PRODUCT NAME</th>
													<th>BATCH NO</th>
													<th>UNIT TYPE</th>
													<th>PRICE</th>
													<th>STOCK</th>
													
												</tr>
											</thead>
											<!-- <tbody>
												<tr>
												    <td></td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
												</tr>
											</tbody> -->
								    </table>
                                    
		                      </div>
		                      
		                         <div class= "clearfix"> </div>
								
		                      <div class="modal-footer">
		                         
		                                
		                      </div>
		                  </div>
		              </div>
		          </div>
		          <jsp:include page="../template/footer.jsp" />
		          </body>
</html>
											