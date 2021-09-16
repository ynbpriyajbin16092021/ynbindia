<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Users</title>
    <%if(request.getParameter("disp") == null){ %>
	<jsp:include page="../home/homelibrary.jsp" />
	<%} %>
    <script type="text/javascript" src="../js/forms/admin/masters/masters.js"></script>
    
</head>
<body onload="loadusers()"> 

<%if(request.getParameter("disp") == null){ %>
  <jsp:include page="../home/homeheader.jsp" />
<%} %>
		
					<div class="clearfix"></div>
					<div class="breadcrumbs"  style="display: <%= request.getParameter("disp") %>">
			            <div class="col-md-4 col-sm-4">
			                <div class="page-header">
			                        <p class="cursor-pointer bread-a-style head-font-size yellow-font" ><i class="fa fa-user "></i>User Master</p>
			                </div>
			            </div>
			            <div class="col-md-8 col-sm-8">
			                <div class="page-header float-right">
			                    <a class="pull-right cursor-pointer bread-a-style content-font-size color-font" href="herohome">Home / Dashboard</a>
			                </div>
			            </div>
			        </div>
					<div class="clearfix"></div>	
					
					<div class="col-md-12 overcome-col-padd-10  ">
						<div class="col-md-12 overcome-col-padd-10 ">
							<div class="width_100">
								
								<div class="width_100 gray-font white-bg content-font-size content-div-height">
									<div class="col-md-12 full-padding-20">
									<a  href="adduser?userid=0" class="button-bg button-space pull-right mar-bot-15" >New <i class="fa fa-plus-circle"></i></a><br>
									<table id="userDT" class="hero-settings-table" >
									<thead>
												<tr>
													<th>User Name</th>
													<th>Email</th>
													<th>Phone No</th>
													<th>Location</th>
													<th>Role</th>
													<th>Status</th>
													<th>Action</th>
												</tr>
											</thead>
											
											
										</table>
									</div>
								</div>
							
							</div>
						</div>
						
					</div>
					
					<div>
					
					
					</div>
					
					
					
					
					
					  <!-- Ak Add new user start-->

					  <!-- Ak Add new user end -->
							
									
					  <jsp:include page="../home/homefooter.jsp" /> 		
</body>
</html>		
					
					
                 