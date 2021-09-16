<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Product</title>
<%if(request.getParameter("disp") == null){ %>
<jsp:include page="../../template/library.jsp" />
<%} %>
<script type="text/javascript" src="../js/forms/masters/masters.js"></script>
</head>
<body onload="loadaddproduct()">

<%if(request.getParameter("disp") == null){ %>
  	<jsp:include page="../../template/header.jsp" />
<%} %>
				  
				  <!-- Main content starts here -->
				          <div class="card">
                        <div class="card-header">
                            <strong class="card-title"><i class="fa fa-table"></i> Add product</strong>
                        </div>
                        <div class="card-body">
                          <!-- Credit Card -->
                          <div id="pay-invoice">
								<div class="col-md-12">
                                    <div class="col-md-6 form-group">
									<label for="Manufacturer">Manufacturer</label>
								     
					 <select id='manufactureselect' class="form-control form-white">
					 <option value="0">-- SELECT --</option>               
    <c:forEach items="${manufactureList}" var="manufacture" >                  
        <option value="${manufacture.value}">
            ${manufacture.label}
        </option>                    
    </c:forEach>
</select>
						
                                    </div>								
									<div class="col-md-6 form-group">
									<label for="Manufacturer">Product Name</label>
								  <input class="form-control form-white" type="text" id="productnametext">
                        <input class="form-control form-white" type="hidden" id="productidtext">
                        <input class="form-control form-white" type="hidden" id="oprntext" value="INS">
								    </div>									
					            </div>
                               <div class="col-md-12">
							   <div class="col-md-6 form-group">
									<label for="SKU">SKU</label>
								    <input type="text" class="form-control inpttypestyle" id="productcodetext"  >
								    </div>
                                    <div class="col-md-6 form-group">
									<label for="producttype">Product Type</label>
								   <select id='categoryselect' class="form-control form-white">
    <c:forEach items="${categoryList}" var="category" >                  
        <option value="${category.value}">
            ${category.label}
        </option>                    
    </c:forEach>
</select>
                                    </div>								
								 </div>
								 <div class="col-md-12">	   
						            <div class="col-md-6 form-group">
									<div class="col-md-12">
									<label for="Unit of Measure" >Unit of Measure</label>
									
									</div>
									<div class="col-md-6 form-group">
									<input id="unitqtytext" value="0" class="form-control form-white" type="number" >
									</div>
									<div class="col-md-6 form-group">
									<select id='uomselect' class="form-control form-white">
    <c:forEach items="${uomList}" var="uom" >                  
        <option value="${uom.value}">
            ${uom.label}
        </option>                    
    </c:forEach>
</select>
									</div>
									</div>
									<div class="col-md-6 form-group">
									<label for="productbarcode">Prouct Bar code</label>
									 <input type="text" id="barcode" class="form-control form-white" value="${random_int}" />
									</div>
						        </div>
								 <div class="col-md-12">
								 <div class="col-md-6 form-group">
								 <label for="Notify Below Quantity">Notify Below Quantity</label>
								
								 <input id="notifyqtytext" readonly="readonly" value="0" name="appendedcheckbox" class="form-control form-white"  type="number" >
                            <input id="notifyqtycopytext" readonly="readonly" value="0" name="appendedcheckbox" class="form-control form-white" type="hidden" >
								 <input type="checkbox" id="notifiycheck" onclick="checknotifyqty();">   
								 
                                 
								</div>
								<div class="col-md-6 form-group">
								 <label for="Status">Status</label><br>
								<!-- <label class="switch"><input type="checkbox" id="togBtn"><div class="slider round"></div></label> -->
								 <div class="col-sm-8">
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
								 <div class="col-md-12">
								 <label for="product Details"  id="descriptiontext">Product Details</label><br>
								 <textarea style="width:100%"></textarea>
								 
								 </div>
								<div class="col-md-12" >
                                        <button type="button" class="btn btn-primary " onclick="saveproduct()">Save</button>
										<a href="product" button type="button" class="btn btn-danger ">Cancel</button></a>
			                      </div>
								
						  </div>
						  

                        </div>
                    </div>
<jsp:include page="../../template/footer.jsp" />
</body>
</html>				  
				  

					
					 