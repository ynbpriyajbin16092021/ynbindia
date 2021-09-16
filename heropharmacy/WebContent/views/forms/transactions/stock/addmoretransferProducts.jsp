<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Store Request</title>
<jsp:include page="../../template/library.jsp" />
<script type="text/javascript" src="../js/forms/transactions/stock.js"></script>


</head>
<body onload="loadstocktransfer();loadPurchasedProductItems();">

<jsp:include page="../../template/header.jsp"/>
<div class="clearfix"></div>
					<div class="breadcrumbs"  style="display: <%= request.getParameter("disp") %>">
			            <div class="col-md-4 col-sm-4">
			                <div class="page-header">
			                        <p class="cursor-pointer mar-top-10 bread-a-style head-font-size yellow-font" href="stockdashboard"><strong><i class="fa fa-table"></i>  Store Request</strong></p>
			                </div>
			            </div>
			            <div class="col-md-8 col-sm-8">
			                <div class="page-header float-right">
			                    <a class="pull-right cursor-pointer bread-a-style content-font-size color-font" href="dashboard?uid=1">Home / Dashboard</a>
			                </div>
			            </div>
			        </div>
<div class="clearfix"></div>
				  
				  
				  <!-- Main content starts here -->
				          <div class="card">
                        <div class="width_100  ">
						<div class="width_100 ">
							<div class="width_100">
								
								<div class="width_100 gray-font white-bg content-font-size">
									<div class="col-md-12 full-padding-20">
                           <div class="margin-bottom-10 content-font-size  white-font color-bg">
										<!-- 	<p class="padd-left-right-5"> Stock Transfer </p> -->
										</div>

                       
       <div class="card-body">
       
                  
					<div class="col-md-12">	
					  
							<div class="col-md-6 form-group" >
									<label for="Product">Add More Product</label>
									<input class="form-control form-white " type="text" id="itemcodetext"  
									placeholder=" search here "></input>
									<input type="hidden" id="productcodetext" value="0"></input>
								</div>   
								
								       <div class="col-md-6 form-group">
					            <label for="Transfer Order#">Product count</label>
								<input type="number" class="form-control form-white" id="productcount" value="0" placeholder="product count" />
							</div>
								
				</div>
				
				    					<div class="col-md-6 form-group" style=" display: none">
										<label for="Date">Transfer Date</label>
									    <input class="form-control form-white datepicker" placeholder=" dd/mm/yyyy " type="hidden" id="transferdatetext">
                                    </div>
                                

                               
                               <div class="col-md-12"    style=" margin-bottom: 20px;" >	    
                         
							
							<div class="form-group txt-align">
									 <button type="button" onclick="addmoreproducts();" class="btn button-bg button-space">Add Product</button>		   
							</div>
							
									
							</div>
	
				        <div class="new-table-txt">
                                 	<table id="addorderrequest" class="hero-settings-table">
											<thead>
											
                                                <th>Product Name </th>	 	
                                                 <th>Count</th>	 	
                                                  	 
                                                   <th>Action</th>	 													
											</thead>
											
											<tbody id="style-5">
											
											 </tbody> 
												</table>
												
                         </div>
								<div class="col-md-12 permissionDiv" >
					
                                        <button type="button" class="btn btn-primary mar" onclick="saveaddtransferproduct();">Save</button>
										<a href="stocktransferhistory"><button type="button" class="btn btn-default mar ">Cancel</button></a>
			                      </div><br><br>	
                          
                     
                        </div>
						
                    </div> 
					
					</div>
					</div>
					</div>
					</div>
					</div>
					
					<style>
					
					.mar{
					margin-top:50px;
					}
					.active{
					background-color:#948fca !important;
					
					}
					
					.nav-tabs {
    /* background: #222222; */
    border: 0;
    border-radius: 3px;
    padding: 0;
} 
	
	.card{
						margin-left:20px;
						margin-right:20px;}			
						
						.new-table-txt  table tbody {
  display: block;
  max-height: 400px;
  overflow-y: scroll;
}
.new-table-txt  table thead,.new-table-txt  table tbody tr {
  display: table;
  width: 100%;
  table-layout: fixed;
}
							</style>
	
	  
						
				  

					
					  <!-- Main content ends here -->
					
<jsp:include page="../../template/footer.jsp" />
</body>
</html>

	