<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>WareHouse Details</title>
<jsp:include page="../../template/library.jsp" />
    <script type="text/javascript" src="../js/forms/transactions/stock.js"></script>
</head>
<body>

<jsp:include page="../../template/header.jsp" />


<div class="clearfix"></div>
					<div class="breadcrumbs"  style="display: <%= request.getParameter("disp") %>">
			            <div class="col-md-4 col-sm-4">
			                <div class="page-header">
			                        <p class="cursor-pointer mar-top-10 bread-a-style head-font-size yellow-font" href="stockdashboard"><strong><i class="fa fa-shopping-bag"></i>Stock Monitor</strong></p>
			                </div>
			            </div>
			            <div class="col-md-8 col-sm-8">
			                <div class="page-header float-right">
			                    <a class="pull-right cursor-pointer bread-a-style content-font-size color-font" href="dashboard?uid=1">Home / Dashboard</a>
			                </div>
			            </div>
			        </div>
<div class="clearfix"></div>


				  
		
				  
           
                      
                         
                           <script>
    $(document).ready(function(){
    	$(".warehousestockTable").DataTable();
    });
    </script>
    	
					<div class="col-md-12 overcome-col-padd-10">
						<div class="col-md-12 overcome-col-padd-10">
							<div class="width_100">
								<!-- <div class="dashboard-ld-header margin-bottom-10 head-font-size white-font color-bg">
									<p><span style="font-size:18px;font-weight:bold"></span> Category </p>
								</div> -->
								<div class="width_100 gray-font white-bg content-font-size content-div-height">
									<div class="col-md-12 full-padding-20">
    
    
    
    
    
									<table id="stocktableDT" class="hero-settings-table"  style="width:100%" >
											<thead>
												<tr>
												    <!-- <th>S.NO</th> -->
												    <th>PRODUCT</th>
													<th>CATEGORY</th>
													<th>UNIT TYPE</th>
													<th>AVAILABLE STOCKS</th>
											    </tr>
											</thead>
											<tbody>
												 <c:forEach items="${stockList}" var="stock" > 
              
						              <tr onclick="showbatchdetails(${stock.productid });">
						              
						              		 <td> ${stock.productname } </td>  
						                    <%--  <td> ${stock.batchno } </td> --%>
						                     <td> ${stock.categoryname } </td>  
						                     <td> ${stock.unit } </td>  
						                     <td> ${stock.productcount } </td>  
						                    <%--  <td> <input type="number" style="width: 125px;" value="${stock.sellprice }" id="sellprice${stock.index }">  </td>  
						                     <td> <button class="save myBtnTab" onclick="savestockprice('${stock.index }','${stock.stockid }',${stock.sellprice })"> <i class="fa fa-save"></i> </button> </td> --%>
						                     
						              </tr>
						              
						              </c:forEach>
              
											</tbody>
								    </table>
	  <button  a data-toggle="modal" data-target="#modal-large" href="#" id="togglebutton" style="display: none;"></button>								
									
									

                          

                        

					
					  <!-- Main content ends here -->
					
					
              <script type="text/javascript">
				$(document).ready(function(){
					$("#stocktableDT").dataTable();
				jQuery(".tdclick").click(function(){
					
				   
					jQuery('#myModal').modal({backdrop: true});

					});	
				});
				</script>   
	

   <div  id="myModal" class="modal fade modalForgetPassword" >
		              <div class="modal-dialog widthModalForget" style=" margin-left:140px;">
		                  <div class="modal-content" style="width:1100px;" >
		                      <div class="modal-header" >
		                          
		                          <h4 class="modal-title" id="productname"><strong>Product Name</strong></h4>
								  
		                      </div>
		                      <div class="modal-body">
		                        <table id="itemDetailDT" class="table table-striped table-bordered dt-responsive nowrap example" style="width:100%">
											<thead>
												<tr>
												    <th>Bill NO</th>
													<th>Purchase Date</th>
													<th>Unitprice</th>
													<th>Expiry Date</th>
													<th>supplier</th>
													<!-- <th>sales Price</th> -->
													<th>Available Quantity</th>
													<th>Actions</th>
													
												</tr>
											</thead>
											
								    </table>
		                      </div>
		                      <div class="modal-footer"></div>
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


