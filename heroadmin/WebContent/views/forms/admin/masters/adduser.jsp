<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Add User</title>
<%if(request.getParameter("disp") == null){ %>
<jsp:include page="../home/homelibrary.jsp" />
<%} %>
<script type="text/javascript" src="../js/forms/admin/masters/masters.js"></script>
</head>
<body onload="loadaddnewuser()">
 <%if(request.getParameter("disp") == null){ String applnid = request.getParameter("applnid");%>
  	<jsp:include page="../home/homeheader.jsp" />	
  	<input type="hidden" name="applnid" id="applnidli" value="${applnid }" />		
<% 
}else if(request.getParameter("disp").equals("n")){ String applnid = request.getParameter("applnid"); %>
	<script type="text/javascript">
	var res = url.split("&");
	var userid = res[0][res[0].length -1];
	loadaddnewuserConfig(userid);
	</script>	
	<input type="hidden" name="applnid" id="applnidli" value="${applnid }" />	
<%} %>


 <div class="clearfix"></div>
					 <div class="breadcrumbs"  style="display: <%= request.getParameter("disp") %>">
			            <div class="col-md-4 col-sm-4">
			                <div class="page-header">
			                        <p class="cursor-pointer mar-top-10 bread-a-style head-font-size yellow-font" href="stockdashboard"><strong><i class="fa fa-user "></i> Add user</strong></p>
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
 <input type="hidden" value="<%=session.getAttribute("uid")%>" id="herouseridtext">
 
<div class="col-md-12 overcome-col-padd-10  ">
						<div class="col-md-12 overcome-col-padd-10 ">
							<div class="width_100">
								
								<div class="width_100 gray-font white-bg content-font-size akcontent-div-height">
								<div class="col-md-12 full-padding-20">


					
							
								<ul class="nav nav-tabs">
								  <li class="active"><a data-toggle="tab" href="#personal_details"><strong>User Details</strong></a></li>
								  <li><a data-toggle="tab" href="#communication_details"><strong>Communication Details</strong></a></li>
								  <!-- <li><a data-toggle="tab" href="#other_details">Other Details</a></li> -->
								</ul>
							
								<div class="tab-content akform">
							
							
									  <div id="personal_details" class="tab-pane fade in active pill-space">
									  		<div class="col-md-6 form-group">
											      <label for="text">EHR Entry ID<span style="color:red">*</span></label>
											      <input class="akform-control" type="text" id="ehrentryid" value="${ehrvalue }" readonly>
											     
											</div>
								
											<div class="col-md-6 form-group">
											     <label for="UserName">User Name<span style="color:red">*</span></label>
											     <input class="akform-control" type="text" id="usernametext">
											     <input type="hidden" value="<%=session.getAttribute("uid")%>" id="herouseridtext">
											</div>
										  	<div class="col-md-6 form-group akc1form-group">
										  		<label for="firstname">Name<span style="color:red">*</span></label> 
											        <div class="form-group flname1" >
								                    <input class="akform-control1 aku form-white"  type="text" id="firstnametext" placeholder="FirstName">
								                    </div>
								                    <div class="form-group flname2">
								                    <input class="akform-control1 aku1" type="text" id="lastnametext" placeholder="LastName">
								                    </div>
							                    <input type="hidden" id="oprntext" value="INS">
							                    <input type="hidden" id="usernameidtext" value="0">
									    	</div>			
							               
										    <div class="col-md-6 form-group">
										        <label for="Displaying Name">Display Name<span style="color:red">*</span></label>
							                    <input class="akform-control form-white"  type="text" id="displayingnametext">
										    </div>
										    <div class="col-md-6 form-group">
											      <label for="Password">Password<span style="color:red">*</span></label>
											      <input class="akform-control" type="password" id="passwordtext">
											</div>
											<div class="col-md-6 form-group">
											      <label for="retypePassword">Retype Password<span style="color:red">*</span></label>
											      
											      <input class="akform-control" type="password" id="retypepasswordtext">
											</div>
										    <div class="col-md-6 form-group">
										        <label for="DOB">DOB<span style="color:red">*</span></label>
												<input type="text" class="datepicker akform-control form-white " placeholder="MM/DD/YYYY"  id="dobtext" autocomplete="off" >
											 </div>
											 <div class="col-md-6 form-group">
									                <label for="DOJ">DOJ<span style="color:red">*</span></label>
									                <input type="text"  placeholder="MM/DD/YYYY" class="datepicker akform-control inpttypestyle " id="dojtext" autocomplete="off" >
									           </div>
				                      		
				                      	
				                      	 <div class="col-md-6 form-group">
							                  <label class="col-sm-4 control-label">User Role <span style="color:red">*</span></label>
										      <select id='usertypeselect' class="akform-control form-white" onchange="changeusertypedept()">
													<c:forEach items="${usertypeList}" var="usertype" >                  
												        <option value="${usertype.value}">
												            ${usertype.label}
												        </option>                    
												    </c:forEach>
												</select>
				                      		</div>
				                      		
				                      		
				                      		
				                      		 <div class="col-md-6 form-group" style="display:none;" >
							                  <label class="col-sm-4 control-label">Year<span style="color:red">*</span></label>
										      <select id='clinicyearselect' class="akform-control form-white" onchange="changeusertypedept()">
													<c:forEach items="${clinicList}" var="clinic" >                  
												        <option value="${clinic.value}">
												             ${clinic.label}
												        </option>                    
												    </c:forEach>
												</select>
				                      		</div>
				                      		
				                      <%--  <div class="col-md-6 form-group" id="currencySelectDiv" style="display:none;" >
							                  <label class="col-sm-4 control-label">Currenc
							                  y<span style="color:red">*</span></label>
										      <select id='currencyselect' class="akform-control form-white" onchange="changeusertypedept()">
												 <c:forEach items="${currencyList}" var="currency" >                  
												        <option value="${currency.value}">
												             ${currency.label}
												        </option>                    
												    </c:forEach>
												</select>
				                      		</div>  --%>
				                      		<c:forEach items="${usertypeApplnList}" var="usertypeAppln" > 
				                      			<script> 
				                      				var myarray = [];
					                      		    myarray.applntype = ${usertypeAppln.label};
					                      		  	myarray.usertype = ${usertypeAppln.value}; 
					                      		  	//alert("myarray"+myarray.usertype)
					                      		  	finalarray.push(myarray);
				                      		    </script>
				                      		</c:forEach> 
											
										   <div class="col-md-6 form-group" id="storeSelectDiv">
										 	<label class="col-sm-4 control-label">Location Code<span style="color:red">*</span></label>
											 	<select id='storeselect' class="akform-control form-white">
												    
												</select>
	                                 		</div>
	                                 		<div class="col-md-6 form-group" id="clinicSelectDiv">
										 	<label class="col-sm-4 control-label">Location Code<span style="color:red">*</span></label>
											 	<select id='clinicselect' class="akform-control form-white">
												    
												</select>
	                                 		</div>
	                                 		<div class="col-md-6 form-group" id="labSelectDiv">
										 	<label class="col-sm-4 control-label">Location Code<span style="color:red">*</span></label>
											 	<select id='labselect' class="akform-control form-white">
												    
												</select>
	                                 		</div>
				                      	
				                      		
	                                 		<div class="col-md-6 form-group">
										        <label for="Qualification">Qualification<span style="color:red">*</span></label>
										     <div class="width_60">
										     
											<div class="width_100">
											 <div class="width_81"> 
							                   <select class="akform-control form-white" style="width:100%;" id="qualificationsel" class="mydiagformctrl" >
									            <c:forEach items="${qualificationList}" var="qualification" >                  
												        <option value="${qualification.qualification}">
												            ${qualification.qualification}
												        </option>                    
												    </c:forEach>
								            </select>
								            </div>
								            <div class="width_19">
											<button class="btn btn-primary btn-small" onclick="addqualification();">Add</button>
											</div>
											</div>
											<div class="width_100">
										         <div  id="txnformatqualdispid">
												  <input type="hidden"  id="qualificationtext">
												</div> 
											</div>
											</div>
										    </div>
										      
										    <div class="width_50 margin-bottom-15"> 
				                      		<div class="col-md-6 akr">
			                                       		<label class="col-sm-4 hero-label">Status</label>
			                                        		<div class="akcol-sm-8">
			                                                  	<div class="onoffswitch akm">
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
				                      		 </div>
										   
	                                 	
				                      		 
				                      		 <div class="width_100">
				                      		 <a class="btn btn-primary formNext right" ><i class="fa fa-angle-double-right"></i></a>
				                      		 </div>
				                      	
										 
								</div>

									  <div id="communication_details" class="tab-pane fade  pill-space ">
											
												<div class="col-md-6 form-group">
											        <label for="Address">Address</span></label>
								                    <textarea class="form-control form-white" id="addresstext"></textarea>
											    </div>
											    <div class="col-md-6 form-group">
											        <label for="City">City</span></label>
								                    <input class="akform-control form-white"  type="text" id="citytext">
											    </div>
											    <div class="col-md-6 form-group">
											        <label for="State">State</span></label>
								                    <input class="akform-control form-white"  type="text" id="statetext">
											    </div>
											    <div class="col-md-6 form-group">
											        <label for="Country">Country</label>
								                    <input class="akform-control form-white"  type="text" id="countrytext">
											    </div>
											    <div class="col-md-6 form-group">
											        <label for="Pincode">Pincode</label>
								                    <input class="akform-control form-white"  type="number" id="pincodetext">
											    </div>
												    
										   		
								             
										   		 <div class="col-md-6 form-group">
					                      			<label for="Phone">Gender</label>
		                                          	<span class="checkbox-inline akucheckbox-inline"><input type="radio" name="gendertext" value="female"> Female</span>
		                                          	<span class="checkbox-inline akucheckbox-inline1"><input type="radio" name="gendertext" value="male"> Male</span>
					                      		</div>
					                      		
										   		 <div class="col-md-6 form-group">
											      <label for="Phone">Phone<span style="color:red">*</span></label>
											      <input class="akform-control form-white"  type="number" id="phonenotext">
											     
												</div>
												<div class="col-md-6 form-group">
											        <label for=" Emergency Contact Number">Alternate Number </label>
								                    <input class="akform-control form-white"  type="number" id="emergencycontactnumbertext">
											    </div>
											    
											<div class="col-md-6 form-group">
											        <label for="Email">Email<span style="color:red">*</span></label>
								                    <input class="akform-control form-white"  type="text" id="emailidtext">
											  </div>
											  <div class="col-md-6 form-group">
											        <label for="Known Languages">Known Languages <span style="color:red">*</span></label>
											         
											        
											  <div class="width_60">
										     
											<div class="width_100">
											 <div class="width_81"> 
							                   <select class="akform-control form-white" style="width:100%" id="languagesel" class="mydiagformctrl" >
									            <c:forEach items="${languageList}" var="language" >                  
												        <option value="${language.description}">
												            ${language.description}
												        </option>                    
												    </c:forEach>
								            </select>
								            </div>
								            <div class="width_19">
											<button class="btn btn-primary btn-small" onclick="addlanguage();">Add</button>
											</div>
											</div>
											<div class="width_100">
										         <div  id="txnformatdispid">
												  <input type="hidden"  id="knownlanguagestext">
												</div>   
											</div>
											</div>
											 
								          
										   		 </div>
											  <div class="col-md-6">
							                    
							                    	<div class="width_35">
							                    		<label class="exp-label" for="Years">Experience <span style="color:red">*</span></label>
							                    	</div>
							                    	<div class="width_65">
							                    		<div class="width_50">
								                    		<input class="akform-control aku2 form-white "  type="number" id="yearstextinyoe" placeholder="Years">
								                    		
								                    	</div>
							                    		<div class="width_50">
							                    			<input class="akform-control akw form-white"  type="number" id="monthstextinyoe" placeholder="Months">
							                    			
							                    		</div>
							                    	</div>
							                    
							                    	
							                   
										    </div>
										    <div class="col-md-12">
										    <a class="btn btn-primary btn-akprimary formPrevious" ><i class="fa fa-angle-double-left"></i></a></a>
											<a class="btn btn-default pull-right"  type="button"  onclick="clearuserFormValues();">Clear</a>
											<a class="btn btn-primary pull-right margin-right-10" onclick="savenewuser();">Save</a>
											</div>		
									   </div>
									   
									   
									  
									  
									  
													</div>
											
									</div>
				   </div>
				   </div>
				   </div>
				   </div>

									<div id="user" class="col-md-12 margin-top-20">
									<table id="userDT" class="hero-settings-table" >
									<thead>
												<tr>
													<th>User Name</th>
													<th>Email</th>
													<th>Phone No</th>
													<th>Location</th>
													<th>Role</th>
													<th>Status</th>
													<!-- <th>Actions</th> -->
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
		var str = window.location.pathname;
		var res = str.split("/");
		if(res[3] == "adduser"){	
			
			  $('#user').css({'display':'none'});
		}
</script>
 <style>
.float{
float:right;
margin-right: 27px;}
.zip{
margin-left:27px;}
.button{
float:left;
margin-right:27px;
margin-left: 20px;
margin-top: 280px;
}
.s{
margin-left:27px;
margin-top:296px;}

.content-div-height {
    height:452px;
    }
    .right{
    float:right;
    margin-right:20px;}
    .s{
    float:right;
    margin-right: 10px;}
    .zip {
			margin-top:250px;
			}	
			.width_100 {
    width: 101%;
    
    margin-left: -5px;
}	
.m{
margin-right:24px;}
.width{
margin-top:30px;}
.tab-content>.tab-pane {
height:100px;
}
button, html input[type=button], input[type=reset], input[type=submit]{
margin-top:5px;
margin-bottom:10px;
}
	.form-page {
	height:200px;
	}
	.content-div-height {
    height: 530px;	
    }
  /*  input[type="text"].form-control, input[type="email"].form-control, input[type="number"].form-control, input[type="password"].form-control, textarea.form-control, select.form-control {
    border: 0;
    outline: 0;
    background: transparent;
    border-bottom: 1px solid #d0d0d0; 
    font-size: 15px;
    border-top: -3px;
    border-top: 1px solid #d0d0d0;
    border-right:1px solid #d0d0d0;
    border-left:1px solid #d0d0d0;
} */
</style> 


<%if(request.getParameter("disp") == null){ %>
<jsp:include page="../home/homefooter.jsp" />
<%} %>

</body>
</html>


			        
			      
			        
			        
			        
			        
			        
  
  
				               