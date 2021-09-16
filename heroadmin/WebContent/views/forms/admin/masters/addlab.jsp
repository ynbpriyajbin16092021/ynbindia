<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="../js/forms/admin/masters/clinics.js"></script>

<%if(request.getParameter("disp") == null){ %>
<jsp:include page="../home/homelibrary.jsp" />
<%} %>	

<title>Clinic Registration</title>
</head>
<body onload="loadaddlab()">
<%if(request.getParameter("disp") == null){String clinicid = request.getParameter("lid"); %>
<jsp:include page="../home/homeheader.jsp" /> 	
<% } %>
	<input type="hidden"  id="clinicidtext"  />	
					<div class="clearfix"></div>
					<div class="breadcrumbs"  style="display: <%= request.getParameter("disp") %>">
			            <div class="col-md-4 col-sm-4">
			                <div class="page-header">
			                        <p class="cursor-pointer mar-top-10 bread-a-style head-font-size yellow-font" ><strong> <i class="fa fa-medkit" ></i> Add lab </strong></p>
			                </div>
			            </div>
			            <%if(request.getParameter("disp") == null){ %>
			            <div class="col-md-8 col-sm-8">
			                <div class="page-header float-right">
			                    <a class="pull-right cursor-pointer bread-a-style content-font-size color-font" href="herohome">Home / Dashboard</a>
			                </div>
			            </div>
			        </div>
					<div class="clearfix"></div>	 

 <%} %>
					<div class="width_100 overcome-col-padd-10 ">
						<div class="form-page white-bg">
							<div class="width_100">
							
								<ul class="nav nav-tabs">
								  <li class="active"><a data-toggle="tab" href="#clinic_details">Lab Details</a></li>
								  <li><a data-toggle="tab" href="#communication_details">Branch Details</a></li>
								  <li><a data-toggle="tab" href="#system_configuration">System Configuration</a></li>
								</ul>
							
								<div class="tab-content">
								
									  <div id="clinic_details" class="tab-pane fade in active pill-space">
									  	<div class="col-md-6 form-group">
									    	<label for="clinicname">Laboratory Code<span style="color:red">*</span></label>
									    	             <input class="form-control form-white" placeholder="Laboratory Code" type="hidden" id="oprntext" name="labcode">
															 <input class="form-control form-white" placeholder="Lab Code" type="hidden" id="labidtext" name="labcode" >
										                    <input class="form-control form-white" placeholder="Laboratory Code" type="number" id="labcodetext" name="labcode">
										                 </div>
									                    <div class="col-md-6 form-group">
															<label for="EHR Code">Laboratory Name<span style="color:red">*</span></label>
															
										                    <input class="form-control form-white" placeholder="Laboratory Name" type="text" id="clinicdesc" name="clinicdesc">
										                 </div> 
										                 <div class="col-md-6 form-group">
															<label for="Clinical Type">Website Name</label>
										                    <input class="form-control form-white" placeholder="Website Name" type="text" id="websitetext" name="website name">
										                 </div>	
										              
										                 <div class="col-md-6 form-group">
															<label for="tinno">Regn.NO<span style="color:red">*</span></label>
															<!-- <input class="form-control form-white" placeholder="TIN.NO" type="hidden" id="tinnoidtext" value="0"> -->
										                    <input class="form-control form-white" placeholder="Regn.NO" type="number" id="regnnotext" name="regn no">
										                 </div>	
										                <div class="col-md-6 form-group">
															<label for="clinicstartingyear">Laboratory Starting Year</label>
														
										                    <input class="yearpicker form-control form-white"  placeholder="Laboratory Starting Year" type="text" id="clinicstartingyeartext" name="clinicstartingyearname">
										                 </div>	 
										                 <div class="col-md-6 form-group">
														<label for="timeperslot">Laboratory Currency <span style="color:red">*</span></label>
														 <select id='currencyselect' class="akform-control form-white" onchange="changeusertypedept()">
															 <option value=""> - Select -</option>
															 <c:forEach items="${currencyList}" var="currency" >                  
															        <option value="${currency.value}">
															             ${currency.label}
															        </option>                    
															    </c:forEach>
												</select>
															</div>

									
									    
									    <div class="width_100">
										    	<a class="btn btn-primary formNext button pull-right prev-height" ><i class="fa fa-angle-double-right"></i></a>
										    	
										 </div>
									  </div>

									  <div id="communication_details" class="tab-pane fade  pill-space">
											  <div class="col-md-6 form-group">
													<label for="Address">Laboratory Location </label>
													 <input class="form-control form-white" placeholder="Laboratory Location" type="text" id="cliniclocationtext" name="cliniclocationtext">
								                </div>
												<div class="col-md-6 form-group">
													<label for="telephoneno">Branch Name </label>
													<!-- <input class="form-control form-white" placeholder="Telephone No" type="hidden" id="telephonenotext" value="0"> -->
								                    <input class="form-control form-white" placeholder="Branch Name" type="text" id="branchnametext" name="branchnametext">
								                 </div>	
								                 
								                  <div class="col-md-6 form-group">
													<label for="faxno">Working Time <span style="color:red">*</span></label>
													<!-- <input class="form-control form-white" placeholder="Fax No" type="hidden" id="faxnotext" value="0"> -->
								                    <input class="form-control form-white " placeholder="8 hours" type="text" id="workingtimetext" name="workingtimetext">
								                 </div>	
								                 <div class="col-md-6 form-group">									
													<label for="mobilenumber">Start Time <span style="color:red">*</span></label>
													<input class="form-control form-white clockpicker" type="text" placeholder="09:30AM" id="starttime" name="starttime">
												</div>
												<div class="col-md-6 form-group">
													<label for="emailid">End Time <span style="color:red">*</span></label>
													 <input class="form-control form-white clockpicker" autocomplete="off" placeholder="06:30PM" type="text" id="endtimetext" name="endtimetext">
												</div>
									  
									     
									     
									   <div class="width_100">
										    	<a class="btn btn-primary formNext button pull-right heigh" ><i class="fa fa-angle-double-right"></i></a>
										    	<a class="btn btn-primary formPrevious button heigh" ><i class="fa fa-angle-double-left"></i></a>
										 </div>
									  </div>
									  <div id="system_configuration" class="tab-pane fade pill-space">
													<!-- <div class="col-md-6 form-group">									
														<label for="academicyear">Academic Year</label>
														<input class="form-control form-white" type="number" placeholder="academicyear" id="academicyeartext" name="academicyearname">
													</div> -->
													<div class="col-md-6 form-group">									
														<label for="smscounttobesend">Address</label>
														<input class="form-control form-white" type="text" placeholder="Address"  id="addresstext" name="addressname">
															
													</div>
													<div class="col-md-6 form-group">									
														<label for="smscounttobesend">Email<span style="color:red">*</span></label>
														<input class="form-control form-white" type="text" placeholder="Email"   id="emailtext" name="emailtext">
													</div>
													
													<div class="col-md-6 form-group">									
														<label for="smscounttobesend">Mobile No<span style="color:red">*</span></label>
														<input class="form-control form-white" type="number" placeholder="Mobile No"  id="mobilenotext" name="mobilenotext">
													</div>
													<div class="col-md-6 form-group">									
														<label for="smscounttobesend">Fax No<span style="color:red">*</span></label>
														<input class="form-control form-white" type="number"  placeholder="Fax No" id="faxnotext" name="faxnotext">
													</div>
													<div class="col-md-6 form-group">									
														<label for="smscounttobesend">Social Media Type</label>
														<!-- <input class="form-control form-white" type="text" placeholder="Social Media Type" id="socialmediatypetext" name="socialmediatypetext"> -->
														<select class="akform-control form-white" id="socialmediatypetext">
														<option value=""> - Select -</option>
														<option value="Facebook">Facebook</option>
														<option value="Twitter">Twitter</option>
														<option value="LinkedIn">LinkedIn</option>
														<option value="Google">Google+</option>
														<option value="Instagram">Instagram</option>
														</select>
													</div>
													<div class="col-md-6 form-group">									
														<label for="smscounttobesend">Social Media ID</label>
														<input class="form-control form-white" type="text" placeholder="Social Media ID" id="socialmediaidtext" name="socialmediaidtext">
													</div>
													<div class="col-md-6 form-group">									
														<label for="emailcounttobesend">Contact Person </label>
														<input class="form-control form-white" type="text" placeholder="Contact Person"   id="contactpersontext" name="contactpersontext"
														>
													</div>	
													<div class="col-md-6 form-group">									
														<label for="smscounttobesend">Contact No</label>
														<input class="form-control form-white" type="number" placeholder="Contact No" id="contactnotext" name="contactnotext">
													</div>
													
													<div class="width_100">
												    	<a class="btn btn-primary formPrevious button mar" ><i class="fa fa-angle-double-left"></i></a>
											   			<a class="btn btn-primary full-margin-10 pull-right" onclick="savelab();">Save</a>	
									   				</div>
																			
									  </div>

									  </div>
									  
									  
									</div>
									
								
							</div>
						</div>
						

                        			
		
					</div>
						  

            </div>
        </div>
	</div>	
</div>		<div id="labDIV">		  
		 <table id="clinicDT" class="hero-settings-table">
											<thead>
												<tr>
												    <th>LAB CODE</th>
													<th>ADDRESS</th>
													<th>CONTACT NO</th>
													
												  
												</tr>
											 </thead>
											 <tbody>
											   <c:forEach var="lab" items="${labfullList }">
                              <tr>
                            <td>${lab.hl_labcode}</td>
                            <td>${lab.hl_address}</td>
                             <td>${lab.hl_contact_no}</td>
                     
                           
                             </tr>
						  </c:forEach>  
											 
											 </tbody>
								  </table>    
								  </div> 	
								  <script type="text/javascript">
								  $('.formNext').click(function(){
									  $('.nav-tabs > .active').next('li').find('a').trigger('click');
									});
								
									$('.formPrevious').click(function(){
									  $('.nav-tabs > .active').prev('li').find('a').trigger('click');
									});
								  var str = window.location.pathname;
								  var res = str.split("/");
								  if(res[3] == "addlab"){	
								  	$('#labDIV').css({'display':'none'});
								  }
								  $('.clockpicker').clockpicker({
									  twelvehour: true,
									  donetext: 'Done'
									});
									
								  </script>
	<%if(request.getParameter("disp") == null){ %>
<jsp:include page="../home/homefooter.jsp" />
<%} %>
<style type="text/css">
.form-page {
    height: 500px;
}
.content-div-height {
    height: 400px;
    }
</style>
</body>
</html>