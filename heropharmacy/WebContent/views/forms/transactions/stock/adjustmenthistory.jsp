<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Adjustment History</title>
<jsp:include page="../../template/library.jsp" />
<script type="text/javascript" src="../js/forms/transactions/stock.js"></script>
</head>
<body onload="loadadjustmenthistory();">
<style>
a.mar-bottom-10{
margin-bottom:10px;
}
</style>
<jsp:include page="../../template/header.jsp" />


<div class="clearfix"></div>
					<div class="breadcrumbs"  style="display: <%= request.getParameter("disp") %>">
			            <div class="col-md-4 col-sm-4">
			                <div class="page-header">
			                        <p class="cursor-pointer mar-top-10 bread-a-style head-font-size yellow-font" href="stockdashboard"><strong><i class="fa fa-anchor"></i> Adjustments History</strong></p>
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
				  
				  
				  
                   	<div class="width_100  ">
						<div class="width_100 ">
							<div class="width_100">
								
								<div class="width_100 gray-font white-bg content-font-size content-div-height">
									<div class="col-md-12 full-padding-20">
                         <!--  <div class="margin-bottom-10 content-font-size  white-font color-bg">
											<p class="padd-left-right-5"> Adjustments </p>
										</div>
					
								 <div class="col-md-12">	
									<div class="col-md-3 form-group">
									<p>Filter By:</p></div>
								<div class="col-md-12">	
								
						            <div class="col-md-3 form-group">
									<select class="form-control inpttypestyle">
								    <option value="Stores">Stores</option>
								    <option value="Stores">Stores</option>
								    <option value="Stores">Stores</option>
								    </select>
									</div>
									
									<div class="col-md-3 form-group">
									<select class="form-control inpttypestyle">
								    <option value="Type">Type</option>
								    <option value="Expired">Expired</option>
								    <option value="Stolen">Stolen</option>
								    </select>
									</div>
									
									<div class="col-md-3 form-group">
									<select class="form-control inpttypestyle">
								    <option value="Period">Period</option>
								    <option value="All">All</option>
								    <option value="This Week">This Week</option>
									<option value="This Month">This Month</option>
									<option value="This Quater">This Quater</option>
									<option value="This Year">This Year</option>
									
								    </select>
									</div>
						        </div>
						        </div> -->
						        
						        
					<div class="col-md-12 overcome-col-padd-10">
						<div class="col-md-12 overcome-col-padd-10">
							<div class="width_100">
								<!-- <div class="dashboard-ld-header margin-bottom-10 head-font-size white-font color-bg">
									<p><span style="font-size:18px;font-weight:bold"></span> Category </p>
								</div> -->
							<!-- 	<div class="width_100 gray-font white-bg content-font-size content-div-height"> -->
									<div class="col-md-12 full-padding-20">
									
																		
					               	
								
                       
                          <!-- Credit Card -->
                          <div class="col-md-12 permissionDiv">
                         
                          <a href="addadjustment" class="button-bg button-space pull-right mar-bottom-10" >New <i class="fa fa-plus-circle"></i></a>
                          </div>
									<table id="adjlistDT" class="hero-settings-table"  style="width:100%">
											<thead>
												
													
													<th>Date</th>
													<th>Product Name</th>
													<th>Batch</th>
													<th>Qty</th>
												    <th>Price</th>	
													<th>Reason</th>
											</thead>
											
											 <tbody>
												<tr>
													
												</tr>		
									
											</tbody>
											
										</table><br>
										</div>
										<!-- </div> -->
										</div>
										</div>
										</div>
</div>
										</div>
										</div>
										</div>
										</div>

                         
<style>
.card{
margin-left:20px;
margin-right:20px;}
</style>
                       
                    <!-- .card -->

					
					  <!-- Main content ends here -->
<jsp:include page="../../template/footer.jsp" />
</body>				
					
                 