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
<body onload="loadaddclinic()">
<%if(request.getParameter("disp") == null){ String clinicid = request.getParameter("cid");%>
  	<jsp:include page="../home/homeheader.jsp" /> 	
<% } %>
<script>
if(url != ""){
var res = url.split("&");
var checkid =  res[1][res[1].length -1];
if(checkid == 1){
	loadaddclinic();
}
}
</script>
	<input type="hidden"  id="clinicidtext"  />	
					<div class="clearfix"></div>
					<div class="breadcrumbs"  style="display: <%= request.getParameter("disp") %>">
			            <div class="col-md-4 col-sm-4">
			                <div class="page-header">
			                        <p class="cursor-pointer mar-top-10 bread-a-style head-font-size yellow-font" ><strong> <i class="fa fa-medkit" ></i> Add Clinic </strong></p>
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
								  <li class="active"><a data-toggle="tab" href="#clinic_details">Clinic Details</a></li>
								  <li><a data-toggle="tab" href="#communication_details">Communication Details</a></li>
								  <li><a data-toggle="tab" href="#system_configuration">System Configuration</a></li>
								</ul>
							
								<div class="tab-content">
								
									  <div id="clinic_details" class="tab-pane fade in active pill-space">
									  	<div class="col-md-6 form-group">
									    	<label for="clinicname">Clinic Name<span style="color:red">*</span></label>
															<input class="form-control form-white" placeholder="First Name" type="hidden" id="clinicidtext" value="1">
										                    <input class="form-control form-white" placeholder="Clinic Name" type="text" id="clinicnametext" name="clinicnamename">
										                 </div>
										                 <!-- <div class="col-md-6 form-group">
															<label for="EHR Code">EHR Code</label>
															<input class="form-control form-white" placeholder="EHR Code" type="hidden" id="EHR Codeidtext" value="0">
										                    <input class="form-control form-white" placeholder="EHR Code" type="text" id="EHR Codeidtext" name="EHR Codename">
										                 </div> 
										                 <div class="col-md-6 form-group">
															<label for="Clinical Type">Clinical Type</label>
															<input class="form-control form-white" placeholder="Clinical Type" type="hidden" id="Clinical Typeidtext" value="0">
										                    <input class="form-control form-white" placeholder="Clinical Type" type="text" id="Clinical Typetext" name="Clinical Typename">
										                 </div>	-->
										                 <div class="col-md-6 form-group">
															<label for="clinicstartingyear">Clinic Starting Year</label>
															<!-- <input class="form-control form-white" placeholder="Clinic Starting Year" type="hidden" id="clinicstartingyeartext" value="0"> -->
										                    <input class="yearpicker form-control form-white"  placeholder="Clinic Starting Year" type="text" id="clinicstartingyeartext" name="clinicstartingyearname">
										                 </div>	
										                 <div class="col-md-6 form-group">
															<label for="tinno">GST.NO</label>
															<!-- <input class="form-control form-white" placeholder="TIN.NO" type="hidden" id="tinnoidtext" value="0"> -->
										                    <input class="form-control form-white" placeholder="GST.NO" type="text" id="tinnotext" name="tinnoname">
										                 </div>	
										                 <div class="col-md-6 form-group">
															<label for="websitename">Website Name<span style="color:red">*</span></label>
															<!-- <input class="form-control form-white" placeholder="Website Name" type="hidden" id="websitenametext" value="0"> -->
										                    <input class="form-control form-white" placeholder="Website Name" type="text" id="websitenametext" name="websitename">
										                 </div>	
										                 <!-- <div class="col-md-6 form-group">
															<label for="clinicdesc">Clinic Desc</label>
															<input class="form-control form-white" placeholder="Clinic Desc" type="hidden" id="clinicdesctext" value="0">
										                    <input class="form-control form-white" placeholder="Clinic Desc" type="text" id="clinicdesctext" name="clinicdescname">
										                 </div> -->	
										                 <div class="col-md-6 form-group">
															<label for="branchname">Branch Name</label>
															<!-- <input class="form-control form-white" placeholder="Branch Name" type="hidden" id="branchnametext" value="0"> -->
										                    <input class="form-control form-white" placeholder="Branch Name" type="text" id="branchnametext" name="branchname">
										                 </div>	
										                  <div class="col-md-6 form-group">
															<label for="regnno">Regn.No</label>
															<!-- <input class="form-control form-white" placeholder="Regn.No" type="hidden" id="regnnotext" value="0"> -->
										                    <input class="form-control form-white" placeholder="Regn.No" type="text" id="regnnotext" name="regnnoname">
										                 </div>	
										                  <div class="col-md-6 form-group">
															<label for="druglicenseno">Drug License No</label>
															<!-- <input class="form-control form-white" placeholder="Drug License No" type="hidden" id="druglicensenotext" value="0"> -->
										                    <input class="form-control form-white" placeholder="Drug License No" type="text" id="druglicensenotext" name="druglicensenoname">
										                 </div>
									                    <div class="col-md-6 form-group">
																	<label for="city">City<span style="color:red">*</span></label>
																	<input class="form-control form-white" placeholder="City" id="citytext" type="text" name="cityname">
														</div>
														<div class="col-md-6 form-group">
																	<label for="State">State</label>
																	<input class="form-control form-white" placeholder="State" id="statetext" type="text" name="statename">
														</div>
														<div class="col-md-6 form-group">
																<label for="country">Country</label>
																  <input class="form-control form-white" placeholder="Country" id="countrytext" type="text" name="countryname">
														</div>	
														<div class="col-md-6 form-group">
				                            				<label for="workinghours">Working Hours<span style="color:red">*</span><br /></label>
					                      					<div class="col-md-3 clockpicker">
															    <input type="text" class="form-control" value="09:30AM" id="workingfromtime">
															    <input type="hidden" id="workstarthoursel" value="09" />
						                            			<input type="hidden" id="workstartminsel" value="30" />
						                            			<input type="hidden" id="workstartampmsel" value="AM" />    
															</div>
															<span class="col-md-1">TO</span>
															<div class="col-md-3 clockpicker">
															    <input type="text" class="form-control" value="06:30PM" id="workingtotime"> 
															    <input type="hidden" id="workendhoursel" value="06" />
						                            			<input type="hidden" id="workendminsel" value="30" />
						                            			<input type="hidden" id="workendampmsel" value="PM" />   
															</div>
                              							</div>
							                              <div class="col-md-6 form-group">
														<label for="timeperslot">Time Per Slot<span style="color:red">*</span></label>
														<input class="form-control form-white" placeholder="Time per Slot" id="timeperslottext" type="text" name="countryname">
															</div>
															
															 <div class="col-md-6 form-group">
														<label for="timeperslot">Currency Type<span style="color:red">*</span></label>
														 <select id='currencyselect' class="akform-control form-white" onchange="changeusertypedept()">
												 <c:forEach items="${currencyList}" var="currency" >                  
												        <option value="${currency.value}">
												             ${currency.label}
												        </option>                    
												    </c:forEach>
												</select>
															</div>
						

										<div class="col-md-6 form-group">
											<label for="breakhours">Break  Hours<span style="color:red">*</span><br /></label>
					                      					<div class="col-md-3 clockpicker">
															    <input type="text" class="form-control" value="01:30PM" id="breakfromtime">
															    <input type="hidden" id="breakhourstartsel" value="01" />
						                            			<input type="hidden" id="breakminstartsel"  value="30" />
						                            			<input type="hidden" id="breakstartampmsel" value="PM" />    
															</div>
															<span class="col-md-1">TO</span>
															<div class="col-md-3 clockpicker">
															    <input type="text" class="form-control" value="02:30PM" id="breaktotime">  
															    <input type="hidden" id="breakhourendsel" value="02" />
						                            			<input type="hidden" id="breakminendsel" value="30" />
						                            			<input type="hidden" id="breakampmendsel" value="PM" />  
															</div>
									
										</div>
									    
									    <div class="width_100">
										    	<a class="btn btn-primary formNext button pull-right prev-height" ><i class="fa fa-angle-double-right"></i></a>
										    	
										 </div>
									  </div>

									  <div id="communication_details" class="tab-pane fade  pill-space">
											  <div class="col-md-6 form-group">
													<label for="Address">Address</label>
													 <textarea  class="form-control form-white" id="addresstext" name="addressname"> </textarea> 
								                </div>
												<div class="col-md-6 form-group">
													<label for="telephoneno">Telephone No<span style="color:red">*</span></label>
													<!-- <input class="form-control form-white" placeholder="Telephone No" type="hidden" id="telephonenotext" value="0"> -->
								                    <input class="form-control form-white" placeholder="Telephone No" type="text" id="telephonenotext" name="telephonenoname">
								                 </div>	
								                  <!-- <div class="col-md-6 form-group">
													<label for="socialmediatype">Social Media Type</label>
													<input class="form-control form-white" placeholder="Social Media Type" type="hidden" id="socialmediatypetext" value="0">
								                    <input class="form-control form-white" placeholder="Social Media Type" type="text" id="socialmediatypetext" name="socialmediatypename">
								                 </div>	
								                  <div class="col-md-6 form-group">
													<label for="socialmediaid">Social Media ID</label>
													<input class="form-control form-white" placeholder="Social Media ID" type="hidden" id="socialmediaidtext" value="0">
								                    <input class="form-control form-white" placeholder="Social Media ID" type="text" id="socialmediaidtext" name="socialmediaidname">
								                 </div>	 -->
								                  <div class="col-md-6 form-group">
													<label for="faxno">Fax No<span style="color:red">*</span></label>
													<!-- <input class="form-control form-white" placeholder="Fax No" type="hidden" id="faxnotext" value="0"> -->
								                    <input class="form-control form-white" placeholder="Fax No" type="text" id="faxnotext" name="faxnoname">
								                 </div>	
								                 <div class="col-md-6 form-group">									
													<label for="mobilenumber">Mobile Number<span style="color:red">*</span></label>
													<input class="form-control form-white" type="number" placeholder="Mobile Number" id="mobnotext" name="phonename">
												</div>
												<div class="col-md-6 form-group">
													<label for="emailid">Email ID<span style="color:red">*</span></label>
													 <input class="form-control form-white" placeholder="Email ID" type="text" id="emailtext" name="emailname">
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
														<label for="smscounttobesend">SMS Integration</label>
														<input type="checkbox"  id="smsintegrationcheck" name="smsintegrationcheck">
															<input type="hidden" value="<%=session.getAttribute("uid")%>" id="inventoryuseridtext">
													</div>
													<div class="col-md-6 form-group">									
														<label for="smscounttobesend">Total SMS Count<span style="color:red">*</span></label>
														<input class="form-control form-white" type="number"  id="totalsmstext" name="smscounttobesendname">
													</div>
													<div class="col-md-6 form-group">									
														<label for="emailcounttobesend">SMS Sent Count<span style="color:red">*</span></label>
														<input class="form-control form-white" type="number"  id="smssentcount" name="emailcounttobesendname"
														>
													</div>	
													<div class="width_100">
												    	<a class="btn btn-primary formPrevious button mar" ><i class="fa fa-angle-double-left"></i></a>
											   			<a class="btn btn-primary full-margin-10 pull-right" onclick="saveclinic();">Save</a>	
									   				</div>
																			
									  </div>

									  </div>
									  
									  
									</div>
									
								
							</div>
						</div>
						<div id="clinic">
									
						          <table id="clinicDT" class="hero-settings-table">
											<thead>
												<tr>
												    <th>CLINIC NAME</th>
													<th>ADDRESS</th>
													<th>WORKING HOURS</th>
													<th>SLOT PER MINUTE</th>
													<th>BREAK HOURS</th>
													<th>BREAK MINS</th>
												</tr>
											 </thead>
								  </table>
							
                    
                     </div>     

<script type="text/javascript">
		$('.formNext').click(function(){
		  $('.nav-tabs > .active').next('li').find('a').trigger('click');
		});
	
		$('.formPrevious').click(function(){
		  $('.nav-tabs > .active').prev('li').find('a').trigger('click');
		});
		$(document).ready(function(){
			/* $(".timepicker").timepicker(); */
		});

$('.clockpicker').clockpicker({
	  twelvehour: true,
	  donetext: 'Done'
	});
	
var str = window.location.pathname;
var res = str.split("/");
if(res[3] == "addclinic"){	
	$('#clinic').css({'display':'none'});
}
</script>			
				                            			<script type="text/javascript">
				                            			$(document).ready(function(){
				                            				$("#workingfromtime").on("change", function(){
				                            					var timepicker =$("#workingfromtime").val();
				                            					var split1 = timepicker.split(":");
				                            					var hour = split1[0];
				                            					var timepicker2 = split1[1];
				                            					//var split2 = timepicker2.slice( 0, 1 ).text();
				                            					var minute = timepicker2.substr(0, 2);
				                            					var ampm = timepicker2.substr(2,4);
				                            					$("#workstarthoursel").val(hour);
				                            					$("#workstartminsel").val(minute);
				                            					console.log(hour+"  "+minute+" "+ampm);
				                            					$("#workstartampmsel").val(ampm);
				                            				});
				                            				$("#workingtotime").on("change", function(){
				                            					var timepicker =$("#workingtotime").val();
				                            					var split1 = timepicker.split(":");
				                            					var hour = split1[0];
				                            					var timepicker2 = split1[1];
				                            					//var split2 = timepicker2.slice( 0, 1 ).text();
				                            					var minute = timepicker2.substr(0, 2);
				                            					var ampm = timepicker2.substr(2,4);
				                            					$("#workendhoursel").val(hour);
				                            					$("#workendminsel").val(minute);
				                            					$("#workendampmsel").val(ampm);
				                            				});
				                            				$("#breakfromtime").on("change", function(){
				                            					var timepicker =$("#breakfromtime").val();
				                            					var split1 = timepicker.split(":");
				                            					var hour = split1[0];
				                            					var timepicker2 = split1[1];
				                            					//var split2 = timepicker2.slice( 0, 1 ).text();
				                            					var minute = timepicker2.substr(0, 2);
				                            					var ampm = timepicker2.substr(2,4);
				                            					$("#breakhourstartsel").val(hour);
				                            					$("#breakminstartsel").val(minute);
				                            					$("#breakstartampmsel").val(ampm);
				                            				});
				                            				$("#breaktotime").on("change", function(){
				                            					var timepicker =$("#breaktotime").val();
				                            					var split1 = timepicker.split(":");
				                            					var hour = split1[0];
				                            					var timepicker2 = split1[1];
				                            					//var split2 = timepicker2.slice( 0, 1 ).text();
				                            					var minute = timepicker2.substr(0, 2);
				                            					var ampm = timepicker2.substr(2,4);
				                            					$("#breakhourendsel").val(hour);
				                            					$("#breakminendsel").val(minute);
				                            					$("#breakampmendsel").val(ampm);
				                            				});
				                            			});
				                            			</script>
		
					</div>
						  

            </div>
        </div>
	</div>	
</div>				  
			
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