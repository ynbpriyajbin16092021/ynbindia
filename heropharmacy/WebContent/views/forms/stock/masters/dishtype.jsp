<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Dish Type Master</title>
<%if(request.getParameter("disp") == null){ %>
<jsp:include page="../../template/library.jsp" /> 
<%} %>	
<script type="text/javascript" src="../js/forms/masters/masters.js"></script>
</head>
<body onload="loaddishtype();">

<%if(request.getParameter("disp") == null){ %>
  	<jsp:include page="../../template/header.jsp" />
<%} %>	  
				 
					<div class="clearfix"></div>
					<div class="breadcrumbs"  style="display: <%= request.getParameter("disp") %>">
			            <div class="col-md-4 col-sm-4">
			                <div class="page-header">
			                        <p class="cursor-pointer mar-top-10 bread-a-style head-font-size yellow-font" href="stockdashboard"><strong><i class="fa fa-lightbulb-o"></i> Dish Type List</strong></p>
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
						<div class="col-md-6 overcome-col-padd-10">
							<div class="width_100">								
								<div class="width_100 gray-font white-bg content-font-size content-div-height">
									<div class="col-md-12 full-padding-20">
									<table id="DishTypeDT" class="hero-settings-table" style="width:100%">
											<thead>
												<tr>
													<th>Dish Name</th>													
													<th>Dish Type</th>
													<th>Status</th>
													<th>Action</th>
													
												</tr>
											</thead>
									
								    </table>
								</div>
								</div>
							</div>
						</div>
						<div class="col-md-6 overcome-col-padd-10">
							<div class="width_100">
								<div class="width_100 gray-font white-bg content-font-size content-div-heightnw" id="style-5">
									<div class="col-md-12 full-padding-20">
									<div class="width_100">
										
										<button class="button-bg button-space pull-right mar-bot-15 mar" onclick="cleardishtypefields()">New <i class="fa fa-plus-circle"></i></button>
										
											
										</div>
										<div class="clearfix"></div>
										<p  style="color:red">Enter dish type name like Mini Idly,Egg Noodles,Egg fried Rice etc</p>
									<div class="margin-bottom-10 content-font-size  white-font color-bg">
											<p class="padd-left-right-5"> DishType </p>
										</div>
								
									<div class="col-md-12 form-group">
									  <label for="Manufacturer">Dish Name <span style="color:red">*</span></label>
									   <select id='dishidselect' class="form-control form-white">
										<option value="">-- Select Dish --</option>               
										    <c:forEach items="${dishList}" var="Dish" >                  
										        <option value="${Dish.value}">
										            ${Dish.label}
										        </option>                    
										    </c:forEach>
										</select>
									   
									</div>
									
									<div class="col-md-12 form-group">
									  <label for="Manufacturer">Dishtype Name <span style="color:red">*</span></label>
									  <input class="form-control form-white" type="text" id="dishtypenametext">
                        				<input class="form-control form-white" type="hidden" id="dishtypeidtext" value="0">
                        				<input class="form-control form-white" type="hidden" id="oprntext" value="INS">
									</div>
									
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
								 
								 
									<div class="col-md-12">
									<div class="col-md-12 permissionDiv">
		                         			<button data-dismiss="modal" class="btn btn-primary" type="button" onclick="savedishtype()">Save</button>								     		
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
				
				  <script type="text/javascript">
                  $( document ).ready(function() {
            	  $("#typeselect input[name='gendertext']").on("change",function(){
            		  var id = ($(this).val());
            			  if (id == "Default")
				            {
                  	            $("#Default").css({"display":"block"});
                  	            $("#SGST").css({"display":"block"});
				   
				            }
            			  else
            				  {
            				    $("#Default ").css({"display":"none"});
            				    $("#SGST ").css({"display":"none"});
            				  }
            	});
             });
                     
              </script>
	
<style>
	.button-space {
	margin-right:5px;
	}
	.button-height{
	height:28px;
	}

	</style>
<jsp:include page="../../template/footer.jsp" /> 

</body>
</html>

