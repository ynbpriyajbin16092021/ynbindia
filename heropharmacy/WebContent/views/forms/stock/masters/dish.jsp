<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Dish Master</title>
<%if(request.getParameter("disp") == null){ %>
<jsp:include page="../../template/library.jsp" /> 
<%} %>	
<script type="text/javascript" src="../js/forms/masters/masters.js"></script>
</head>
<body onload="loaddish()">

<%if(request.getParameter("disp") == null){ %>
  	<jsp:include page="../../template/header.jsp" />
<%} %>	  
				 
					<div class="clearfix"></div>
					<div class="breadcrumbs"  style="display: <%= request.getParameter("disp") %>">
			            <div class="col-md-4 col-sm-4">
			                <div class="page-header">
			                        <p class="cursor-pointer mar-top-10 bread-a-style head-font-size yellow-font" href="stockdashboard"><strong> Dish List</strong></p>
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
						<div class="col-md-7 overcome-col-padd-10">
							<div class="width_100">
				
								<div class="width_100 gray-font white-bg content-font-size content-div-height">
									<div class="col-md-12 full-padding-20">
									<table id="dishDT" class="hero-settings-table" >
										<thead>
											<th>Dish Name</th>
											<th>Status</th>
											<th>Action</th>
										</thead>
									</table>
								</div>
								</div>
							</div>
						</div>
						
						<div class="col-md-5 overcome-col-padd-10">
							<div class="width_100">
								<div class="width_100 gray-font white-bg content-font-size content-div-height">
									<div class="col-md-12 full-padding-20">
									
									<div class="width_100">
											<button class="button-bg button-space pull-right mar-bot-15 mar" 
											onclick="clearDishFields()">New <i class="fa fa-plus-circle"></i></button>
									</div>
									<div class="clearfix"></div>
									<p style="color:red">Enter main dish name like Chappathi Idly etc</p>
									
									<div class="margin-bottom-10 content-font-size  white-font color-bg">
											<p class="padd-left-right-5">Dish Name </p>
										</div>
									<div  id="masterCollapse" >
									   <label for="Manufacturer Company Name">Dish Name</label>
									   <input type="text" id="dishnametext" class="form-control inpttypestyle">
									   <input class="form-control" type="hidden" id="oprntext" value="INS">
									   <input class="form-control" type="hidden" id="dishidtext">
									   
									  		 <div class="col-md-12 akrp">
			                                       		<label class="hero-label">Status</label>
			                                        		<div class="akpcol-sm-8">
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
			                      	                         	 
			                      	                         	 
									   <div class="col-md-12 margin-top-bottom-10">
									   <div class="col-md-12 permissionDiv">
		                         			<button data-dismiss="modal" class="btn btn-primary" type="button" onclick="savedish()">Save</button>
								     		<button data-dismiss="modal" class="btn btn-default" type="button"  onclick="clearDishFields();">Clear</button>
								     		</div>
		                      			</div>
									</div>
									
									
									
								</div>
								</div>
							</div>
						</div>
					</div>
					
				<script>
				$(document).ready(function() {
				    $('.hero-settings-table').DataTable( {
				    	bFilter: false, bInfo: false
				    } );
				} );
				</script>
				 
				 <style>
				 
				 </style>
				
				 
<jsp:include page="../../template/footer.jsp" /> 

</body>
</html>

