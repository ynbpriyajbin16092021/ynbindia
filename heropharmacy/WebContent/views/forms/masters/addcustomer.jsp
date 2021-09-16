<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Customer</title>
 <%if(request.getParameter("disp") == null){ %>
<jsp:include page="../../forms/template/library.jsp" /> 
<%} %> 
<script type="text/javascript" src="../js/forms/masters/masters.js"></script>

</head>
<body onload="loadaddcustomer()">

<jsp:include page="../template/header.jsp" /> 
<script type="text/javascript">
$(document).ready(function(){
			document.querySelector(".preventchar").addEventListener("keypress", function (evt) {
			    if (evt.which != 8 && evt.which != 0 && evt.which < 48 || evt.which > 57)
			    {
			        evt.preventDefault();
			    }
			});
});
</script>
				  
				  <!-- Main content starts here -->
				          <div class="clearfix"></div>
					<div class="breadcrumbs"  style="display: <%= request.getParameter("disp") %>">
			            <div class="col-md-4 col-sm-4">
			                <div class="page-header">
			                        <p class="cursor-pointer mar-top-10 bread-a-style head-font-size yellow-font" href="stockdashboard"><strong>Add Customers</strong></p>
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
									<div class="margin-bottom-10 content-font-size  white-font color-bg">
											<p class="padd-left-right-5">Add customer </p>
										</div>
									
									<div class="col-md-6 form-group">
									<label for="FirstName">First Name </label>
								   <input class="form-control form-white" placeholder=" FIRST NAME " type="text" id="firstnametext">
                                   <input class="form-control form-white" type="hidden" id="custidtext" value="0">
                                   <input class="form-control form-white" type="hidden" id="oprntext" value="INS">
								    </div>									
					                <div class="col-md-6 form-group">
									<label for="LastName">Last Name</label>
								   <input class="form-control form-white " placeholder=" LAST NAME " type="text" id="lastnametext">
                                    </div>	
								</div>
                                <div class="col-md-12">	   
						            <div class="col-md-6 form-group">
									<label for="E-MAIL">E-mail</label>
									<input class="form-control form-white " placeholder=" E-MAIL " type="email" id="emailidtext">
									</div>
									<div class="col-md-6 form-group">
									<label for="MOBILE">Mobile</label>
									 <input class="form-control form-white preventchar" placeholder=" MOBILE " type="number"  id="mobilenotext">
									</div>
						        </div>
								 <div class="col-md-12">	   
						            <div class="col-md-6 form-group">
									<label for="GROUP">Group</label>
									<div class="col-sm-12">
                  
                                    <select  class="form-control form-white" id="groupselect">
                                          <c:forEach items="${customergroupList}" var="customergroup" >
                                              <option value="${customergroup.value}">
                                                             ${customergroup.label}
                                              </option>            
                          				</c:forEach>
                      				</select>
                  				</div>
									
						        </div>
								
								<div class="col-md-6 akrp" style="margin-top:36px;">
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
		    <div class="col-md-6 form-group">
                 <label class="col-sm-6 marginBot control-label">GSTIN  Number</label>
            
                	    <div class="col-sm-12">
                  		 <input class="form-control form-white preventchar"  id="customertinnotext" type="text">
               		</div>
               </div> 
             </div> 				
								
						  </div>
						  <br /><br /><br />
						   
						<div class="col-md-12 addaddress">	   
                             <a onclick="statecitynone()"  data-toggle="modal" href="#myModal" class="card-title"> <i class="fa fa-plus"></i> Add Address</a>
									
									</div>
						 
                    
									<table id="addressTable" class="table" style="width:100%">
											<thead>
												
													
													<th>Address</th>
													<th>City</th>
													<th>State</th>
													<th>Country</th>
												    <th>Zip Code</th>	
                                                    <th>Action</th>													
											</thead>
											
											<tbody>
											</tbody>
									</table>
                          

                        </div>
						
                   
					<div class="col-md-12" >
					
                                        <button type="button" class="btn btn-primary" onclick="savecustomer();">Save</button>
										<!-- <button type="button" class="btn btn-danger ">Cancel</button> -->
										 <a href="customer" class="btn btn-default " >Cancel</a>
			                      </div><br><br>
				  	
	 

					</div>
                    </div>
                    </div>

<form id='myForm'>
<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog" tabindex="-1" id="myModal" class="modal fade modalForgetPassword" >
		              <div class="modal-dialog widthModalForget">
		                  <div class="modal-content">
		                      <div class="modal-header">
		                          
		                          <h4 class="modal-title">Add Customer Address</h4>
								  <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		                      </div>
							  <div class="modal-body">
							 <div class="col-md-12 form-group">
								   <p>These Addresses are maintaied for Delivery purpose</p>
								   
								</div>
								<div class="modal-body" autocomplete="off">
								<div class="col-md-12">	
								
									<div class="col-md-6 form-group" >
										<label>Address</label>
										<textarea  class="form-control form-white" id="streetaddresstext" rows="3" cols="50" ></textarea>
								   </div>									
					             <div class="col-md-12">	
					              <div class="col-md-6 form-group">
									<label for="Country ">Country </label>
									<select class="form-control form-white countries">
     
                                    </select>
								    <input  class="form-control form-white " type="hidden" id="countrytext">
                                    </div>	
									<div class="col-md-6 form-group">
									<label for="State"> State</label>
								     <select class="form-control form-white states" >
				                            <option value="0">--select--</option>
                                     </select>
                                      <input class="form-control form-white " type="hidden" id="statetext">
								    </div>	
								     <div class="col-md-6 form-group">
									<label for="City">City </label>
								    <select class="form-control form-white cities" >
				                        <option value="0">--select--</option>
                                   </select>
                                        <input  class="form-control form-white" type="hidden" id="citytext">
								</div>								
					               
									<div class="col-md-6 form-group">
									<label for="Zip code ">Zip code</label>
								    <input  class="form-control form-white " type="number" id="zipcodetext">
                                    </div>
								</div>
		                      
								
		                      </div>
		                      <div class="modal-footer">
		                         
		                          <div class="col-md-12 permissionDiv">
								   <button data-dismiss="modal" class="btn btn-default" type="button" onclick="addcustomertolist();"  id="customeraddressaddbtn">Add</button>
								   <button data-dismiss="modal" class="btn btn-default" type="button">Cancel</button>
								   </div>
		                      </div>
		                  </div>
		              </div>
		              <div class="clearfix"></div>
		          </div>

</div></div>	</form>	
<script type="text/javascript">
function statecitynone(){
	$(".countries").val("0");
	 $(".states").val("0");
	 $(".cities").val("0");
}
$(".countries").on("change", function(ev) {
    var countryId = $(this).val();
    var countryName = $(".countries option:selected").text();
    $("#countrytext").val(countryName);
    $(".states").val("0");
    $(".cities").val("0");
    getStates(countryId); 
});
$(".states").on("change", function(ev) {
    var stateId = $(this).val();
    var stateName = $(".states option:selected").text();
    $("#statetext").val(stateName);
    $(".cities").val("0");
    getCities(stateId); 
});
$(".cities").on("change", function(ev) {
    var stateId = $(this).val();
    var stateName = $(".cities option:selected").text();
    $("#citytext").val(stateName);
});

$(document).ready(function(){
    $('#myForm').on( 'focus', ':input', function(){
        $(this).attr( 'autocomplete', 'off' );
    });
});
</script>	
<jsp:include page="../template/footer.jsp" />		
</body>
</html>					