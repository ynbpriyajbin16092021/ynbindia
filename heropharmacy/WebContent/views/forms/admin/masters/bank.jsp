<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>CategoryScreen</title>
<%if(request.getParameter("disp") == null){ %>
<jsp:include page="../../template/library.jsp" /> 
<%} %>	
<script type="text/javascript" src="../js/forms/masters/masters.js"></script>
</head>
<body onload="loadbank();">

<%if(request.getParameter("disp") == null){ %>
  	<jsp:include page="../../template/header.jsp" />
<%} %>	  
				 
					<div class="clearfix"></div>
					<div class="breadcrumbs"  style="display: <%= request.getParameter("disp") %>">
			            <div class="col-md-4 col-sm-4">
			                <div class="page-header">
			                        <p class="cursor-pointer mar-top-10 bread-a-style head-font-size yellow-font" href="stockdashboard"><strong>Bank Master</strong></p>
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
								<!-- <div class="dashboard-ld-header margin-bottom-10 head-font-size white-font color-bg">
									<p><span style="font-size:18px;font-weight:bold"></span> Category </p>
								</div> -->
								<div class="width_100 gray-font white-bg content-font-size content-div-height">
									<div class="col-md-12 full-padding-20">
									<table id="bankDT" class="hero-settings-table" >
										<thead>
											<th>Bank Name</th>
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
											<button class="button-bg button-space pull-right mar-bot-15 mar" onclick="clearBankFormValues();">New <i class="fa fa-plus-circle"></i></button>
									</div>
									<div class="clearfix"></div>
									<div class="margin-bottom-10 content-font-size  white-font color-bg">
											<p class="padd-left-right-5">Bank Name </p>
										</div>
									
									<div id="masterCollapse" >
									   <label for="Manufacturer Company Name">Bank Name</label>
									   <input type="text" id="banktext" class="form-control inpttypestyle">
									   <input class="form-control" type="hidden" id="oprntext" value="INS">
									   <input class="form-control" type="hidden" id="bankidtext">
									   <div class="col-md-12 margin-top-bottom-10">
									   <div class="col-md-12 permissionDiv">
		                         			<button data-dismiss="modal" class="btn btn-primary" type="button" onclick="savebank()">Save</button>
								     		<button data-dismiss="modal" class="btn btn-default" type="button"  onclick="clearBankFormValues();">Clear</button>
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
				
				 
				 
				 
				 
				 
				 
				 
				 
				 
				 
				 
				 
				 
				 
				 
				 
				 
				 
				 
				  <!-- Main content starts here -->
				  
				  
					
					
				
  
					  <!-- Main content ends here -->
			
	<!--  <div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog" tabindex="-1" id="myModal" class="modal fade modalForgetPassword" >
		              <div class="modal-dialog widthModalForget">
		                  <div class="modal-content">
		                      <div class="modal-header">
		                          
		                          <h4 class="modal-title"><i class="fa fa-table"></i> Categories<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button></h4>
								  
		                      </div>
		                      <div class="modal-body">
		                        <div class="col-md-12 form-group">
								   <label for="Manufacturer Company Name">Categories Name</label>
								   <input type="text" id="categorytext" class="form-control inpttypestyle">
								   <input class="form-control" type="hidden" id="oprntext" value="INS">
								   <input class="form-control" type="hidden" id="categoryidtext">
								</div>
								
								
								
		                      </div>
		                      <div class="clearfix"></div>
		                      <div class="modal-footer">
		                         
		                          
								   <button data-dismiss="modal" class="btn btn-default" type="button" onclick="savecategory()">Save</button>
								     <button data-dismiss="modal" class="btn btn-default" type="button"  onclick="clearCategoryFormValues();">Clear</button>
		                      </div>
		                  </div>
		              </div>
		          </div> -->
				  
				 

			
<jsp:include page="../../template/footer.jsp" /> 

</body>
</html>