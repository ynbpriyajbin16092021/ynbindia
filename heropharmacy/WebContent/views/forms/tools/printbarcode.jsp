<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Barcode Manager</title>
<jsp:include page="../template/library.jsp" />	
<script type="text/javascript" src="../js/forms/tools/barcode.js"></script>
</head>
<body>

<jsp:include page="../template/header.jsp" />				  
				  
				  <!-- Main content starts here -->
				  
	<div class="clearfix"></div>
					<div class="breadcrumbs"  style="display: <%= request.getParameter("disp") %>">
			            <div class="col-md-4 col-sm-4">
			                <div class="page-header">
			                        <p class="cursor-pointer mar-top-10 bread-a-style head-font-size yellow-font" href="stockdashboard"><strong><i class="fa fa-barcode"></i> Print Barcode/Label</strong></p>
			                </div>
			            </div>
			            <div class="col-md-8 col-sm-8">
			                <div class="page-header float-right">
			                    <a class="pull-right cursor-pointer bread-a-style content-font-size color-font" href="dashboard?uid=1">Home / Dashboard</a>
			                </div>
			            </div>
			        </div>
<div class="clearfix"></div>
				  			  
				  
				  
				  
				  
				          <div class="card">
                       
                                          	<div class="width_100  ">
						<div class="width_100 ">
							<div class="width_100">
								
								<div class="width_100 gray-font white-bg content-font-size content-div-height">
									<div class="col-md-12 full-padding-20">
                           <div class="margin-bottom-10 content-font-size  white-font color-bg">
											<p class="padd-left-right-5">Print Barcode/Label</p>
										</div>
                        <div class="card-body">
                          <!-- Credit Card -->
                          
								<div class="col-md-12">	
								
									<div class="col-md-2 ">
									<p>Select Store </p></div>
									
									<div class="col-md-4 ">
								   
									<select  class="form-control form-white  selectSer" id="storeselect" onchange="changestore(this.value)">
	                        <c:forEach items="${storeList}" var="store" >                  
        <option value="${store.value}">
            ${store.label}
        </option>                    
    </c:forEach>
	                        </select>	
								    </div>									
					                <div class="col-md-1 ">
									<button type="button" class="btn btn-primary" id="currencyspan">RS.</button>
                                    </div>	
								</div><br><br>
                                <div class="col-md-12">	   
						            <div class="col-md-2">
									<p>Select Product </p></div>
									 <div class="col-md-4">	
									
									<select  class="form-control form-white  selectSer" id="productselect" onchange="changeproduct(this.value)">
	                        <c:forEach items="${productList}" var="product" >                  
        <option value="${product.value}">
            ${product.label}
        </option>                    
    </c:forEach>
	                        </select>	<br>
									</div></div>
									<div class="col-md-12 form-group">
									 <div class="col-md-2">
									<p>Select Batch </p></div>
									 <div class="col-md-4 form-group">
								<select  class="form-control form-white  selectSer" id="batchselect" onchange="changebatch(this.value)">
	                        <c:forEach items="${batchList}" var="batch" >                  
        <option value="${batch.value}">
            ${batch.label}
        </option>                    
    </c:forEach>
	                        </select>
									</div></div><br>
									<div class="col-md-12">
										 <div class="col-md-2">	
                                     <p> Style*</p></div>
									 <div class="col-md-6 form-group">
								
									 <select  class="form-control form-white  selectSer" id="styleselect" onchange="stylechange(this.value)">
								<option value="0">Select Style</option>
								<option value="1">40 per sheet (A4)(1.799" x 1.003")</option>
								<option value="2">30 per sheet (2.625" x 1")</option>
								<option value="3">24 per sheet (A4)(2.48" x 1.334")</option>
								<option value="4">20 per sheet (4" x 1.33")</option>
								<option value="5">18 per sheet (A4)(2.5" x 1.835")</option>
								<option value="6">14 per sheet (4" x 1.33")</option>
								<option value="7">12 per sheet (A4)(2.5" x 2.834")</option>
								<option value="8">10 per sheet (4" x 2")</option>
								<option value="9">Continuous Feed</option>
								
	                        </select><br>
	                        <div class="col-md-12 margin">
									<p>Please don't forget to set correct page size and margin for your printer. 
									You can set right and bottom to 0 while left and top margin can be adjusted according to your need.</p>
									</div>
									</div>
									<div class="col-md-3 form-group">
									 <input type="text" value="1" readonly="readonly" id="continuoscountid">
									</div>
										
			                      </div>
						       
								 <div class="col-md-12">
								 
						           
									<label class="checkbox-inline"><input type="checkbox" value="" id="productnamecheck"> Product Name</label>
									<label class="checkbox-inline"><input type="checkbox" value=" "id="skucheck"> Sku</label>
									<label class="checkbox-inline"><input type="checkbox" value=""  id="pricecheck" > Price</label>
									<label class="checkbox-inline"><input type="checkbox" value="" id="expirydatecheck"> Expiry Date</label>
									<label class="checkbox-inline"><input type="checkbox" value="" id="batchnocheck"> Batch No</label>
									<label class="checkbox-inline"><input type="checkbox" value=" "id="storenamecheck" > Store Name</label>
										
						        </div>	
						  
						            
                                  
								  
								
						        
						

                      
                    <br><br><br>
		
				  
					
					<div class="col-md-12" >
					
                                        <button type="button" class="btn btn-primary margin"onclick="printbarcode();">Print</button>
										<button type="button" class="btn btn-default margin ">Reset</button>
			                      </div>
				  <br><br>
<style>
select#productselect {
    width: 203px;
    }
select#batchselect {
    width: 203px;
    }
    .form-group>.form-control {
    width: 46%;}
.margin{
margin-top:20px;}
.card{
margin-left:20px;
margin-right:20px;}
</style>
					
					  <!-- Main content ends here -->
	<jsp:include page="../template/footer.jsp" />

</body>
</html>				
					