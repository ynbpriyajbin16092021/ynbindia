<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Customer List Screen</title>
<jsp:include page="../template/library.jsp" />
<script type="text/javascript" src="../js/forms/masters/masters.js"></script>
</head>
<body onload="loadcustomer();">
<style>
a.mar-bot-15{
margin-bottom:15px;
}
</style>
<jsp:include page="../template/header.jsp" /> 


<div class="clearfix"></div>
					<div class="breadcrumbs"  style="display: <%= request.getParameter("disp") %>">
			            <div class="col-md-4 col-sm-4">
			                <div class="page-header">
			                        <p class="cursor-pointer mar-top-10 bread-a-style head-font-size yellow-font" href="stockdashboard"><strong>Customers List</strong></p>
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
								<!-- <div class="dashboard-ld-header margin-bottom-10 head-font-size white-font color-bg">
									<p><span style="font-size:18px;font-weight:bold"></span> Category </p>
								</div> -->
								<div class="width_100 gray-font white-bg content-font-size content-div-height">
									<div class="col-md-12 full-padding-20">
									<div class="col-md-12 permissionDiv">
									
                           <a href="addcustomer" class="button-bg pull-right button-space mar-bot-15" > New <i class="fa fa-plus-circle"></i></a><br>
                           </div>
									<table id="customerDT" class="hero-settings-table"  style="width:100%">
											<thead>
											<tr>
													<td>Name</td>
												    <td>Mobile</td>
													<td>Email</td>
													<td>Status</td>
													<td>Group</td>
												    <td>Action</td>		
												    </tr>	
											</thead>
									</table><br>
                                                    
                          </div>
                       </div>
                    </div>
                    </div>
                    </div>
                     
<jsp:include page="../template/footer.jsp" /> 				  
</body>
</html>
				