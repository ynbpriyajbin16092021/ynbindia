<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SMS Template</title>
 <%if(request.getParameter("disp") == null){ %>
<jsp:include page="../home/homelibrary.jsp" />
<%} %> 
<link rel="stylesheet" href="/heroadmin/resources/css/stylebass.css">
<script type="text/javascript" src="../js/forms/templates/template.js"></script>
    <link rel="stylesheet" href="/heroadmin/resources/css/template/editor.css"> 
<script type="text/javascript" src="../js/lib/theme/editor.js"></script>
</head>
<body onload="loadsmstemplate()">
<%if(request.getParameter("disp") == null){ %>
  <jsp:include page="../home/homeheader.jsp" />
<%} %>

					<div class="clearfix"></div>
					<div class="breadcrumbs"  style="display: <%= request.getParameter("disp") %>">
			            <div class="col-md-4 col-sm-4">
			                <div class="page-header">
			                        <p class="cursor-pointer mar-top-10 bread-a-style head-font-size yellow-font" href="stockdashboard"><strong><i class="fa fa-envelope"></i>SMS Template</strong></p>
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
								
								<div class="width_100 gray-font white-bg content-font-size akcontent-div-height">
								<div class="col-md-12 full-padding-20">
                                         <ul class="nav nav-tabs">
								   <li class="active"><a data-toggle="tab" href="#sms_configuration">SMS Configuration</a></li>
								  <li><a data-toggle="tab" href="#email_configuration">Email Configuration</a></li>
								  <li><a data-toggle="tab" href="#sms_template">SMS Template</a></li>
								  <li><a data-toggle="tab" href="#email_template">EMAIL Template</a></li>
								</ul>
	<div class="tab-content akform">
	 <div id="sms_configuration" class="tab-pane fade in active">
	         <div class="col-md-6 form-group">
	           <label>API Key <span style="color:red">*</span></label>
	           <input class="form-control form-white" placeholder="API Key" type="text" id="apikeytext">
				<input class="form-control" type="hidden" id="oprntext" value="INS">
	         </div>
	 
	  <div class="col-md-6 form-group">
	    <label>UserName <span style="color:red">*</span></label>
	    <input type="text" placeholder="binaryswanstudio@gmail.com" class="form-control inpttypestyle" id="usernametext" >
	  
	  </div>
	  <div class="col-md-6 form-group">
	  <label>Password <span style="color:red">*</span></label>
	  <input type="text" placeholder="Yuvi@123" class="form-control inpttypestyle" id="passwordtext"  >
	  </div>
	   <div class="col-md-6 form-group">
	 <label>Send SMS</label>
	  <input type="checkbox" id="smssendcheck">
	  </div>
	 <div class="modal-footer">
		  <button type="button" class="btn btn-primary  "  onclick="savesettings()">Save</button>
		 <button type="button" class="btn btn-default " style="margin-right:10px;    margin-top: 20px;">Cancel</button>
    </div>
</div> 
	 
	 
	  <div id="email_configuration" class="tab-pane fade  pill-space ">
	 
	 
	 <div class="col-md-6 form-group">
	        <label class="col-sm-3 control-label">Email id</label>
	            <input class="form-control inpttypestyle" placeholder="Email Address" type="text" id="emailidtext">
	         </div>
	 
	  <div class="col-md-6 form-group">
	    <input type="hidden" value="" id="oprntextemail" />
	    <label class="col-sm-3 control-label">Email password</label>
	    <input class="form-control form-white" placeholder="Password" type="password" id="emailpasswordtext">
	  
	  </div>
	 
 <div class="modal-footer">
      <button type="button" class="btn btn-primary " onclick="saveemailsettings()">Save</button>
      <button type="button" class="btn btn-default " style="margin-right:10px;    margin-top: 20px;">Cancel</button> 
							              </div> 
	  </div>
	 
	 
	  <div id="sms_template" class="tab-pane fade"> 
	 
	       
					<div class="col-md-12 overcome-col-padd-10  ">
						<div class="col-md-12 overcome-col-padd-10 ">
							<div class="width_100">
								
								<div class="width_100 gray-font white-bg content-font-size">
									<div class="col-md-12 margin-top-content">
									
										 <div class="width_25" >
										   <div id="content-1" class="panel-content contentFix">
     
									          <div class="rowNor ">             
									               
									              
									              <table class="table table-hover temTabel selectCheckTabel">
									              <c:forEach items="${smsTemplateList}" var="sms" >
									              <tr onclick="displaymsgcontent('${sms.msgcontent}','${sms.paramdesc}','${sms.templateid }')"> 
									                  <td> ${sms.templatename } </td>
									                 
									                </tr>
									              </c:forEach>   
									                                             
									              </table> 
									              
									          </div>

      									</div>
										 
                                        </div>
                                           
										
										 <div class="width_75  bordercls" >
                                            <div class="col-md-12 stydiva">
                                            <div class="col-md-2">NAME</div>
                                            <div class="col-md-2">SMS CONTENT</div>
                                            </div>
                                             <div class="col-md-12 tabnotesa">
                                             <div class="col-md-1"></div>
                                             <div class=" width_100 gray-font white-bg content-font-size content-div-heightnw" id="style-5">
                                          	<td>
												<!-- <input class="form-control form-white" placeholder="Template Name" type="text" id="templatenametext"> -->
												<input class="form-control form-white" placeholder="Template Name" type="hidden" id="templateidtext" value="${smsTemplateList.get(0).get("templateid")}">
											</td>
											<td>
											 <textarea class="templatetextarea" rows="5" cols="75" id="messagecontenttext">${smsTemplateList.get(0).get("msgcontent")}</textarea> 
											
											</td>
											<td>
											<p><strong>Param Desc</strong></p>
											 <p  id="messagecontentparam">${smsTemplateList.get(0).get("paramdesc")}</p> 
											
											</td>
											<!-- <div class="col-md-12">
			    												<div class="col-md-4">
			    													<p>for Walkin</p>
			    													<p>$param represents the dynamic value for,<br /> $param1 = patientname,<br /> $param2 = doctorname, <br />$param3 = clinicname,<br /> $param4 = doctor schedule form date,<br /> $param4 = doctor schedule to date will be displayed.</p>
			    												</div>
			    												<div class="col-md-4">
			    													<p>for Doctor Leave, Hospital on Leave, Patient Confirmation, Cancel Appointment</p>
			    													<p>$param represents the dynamic value for, <br /> $param1 = Doctor name, <br />$param2 = Appointment date and time,<br /> $param3 = Clinic name will be displayed.</p>
			    												</div>
			    												<div class="col-md-4">
			    													<p>for cancel appointment doctor intimation</p>
			    													<p>$param represents the dynamic value for,<br /> $param1 = Patientname, <br />$param2 = Doctorname, <br />$param3 = Clinicname, <br />$param4 = Doctor schedule form date, <br />$param5 = Doctor schedule to date will be displayed.</p>
			    												</div>
			    												
			    												</div> -->
			    												</div>
                                            <div class="col-md-2">
                                               <button type="button" class="btn btn-primary mar-top-10"  onclick="savemsgcontent();">Save</button>
                                            </div>
                                            
                                            
                                        </div>
										</div>
											
										

									</div>
							</div> 
                    </div>
				</div> 
</div>
				          
					  <!-- Main content ends here -->
	</div> 
	
 	<div id="email_template" class="tab-pane fade  pill-space ">
		<div class="clearfix"></div>
		                                    <div class="col-md-12">
		                                    	<div class="width_25" >
													<table class="table table-hover temTabel selectCheckTabel">
										               <c:forEach items="${emailTemplateList}" var="email" >
													              <tr onclick="displayemailcontent('${email.msgcontent}','${email.paramdesc}','${email.templateid }','${email.msgsubject }')"> 
													                  <td> ${email.templatename } </td>
													                </tr>
													   </c:forEach>   
									              	</table>
									              </div>
									              <div class="width_75 email_templates_parent" >
									              	 <c:set var = "i" value = "0"/>
										            
										              <c:forEach items="${emailTemplateList}" var="email" >
										              
										              	<c:choose>
														    <c:when test="${i == 0}">
														    	<div class="width_100 email_templates_child" id="template${email.templateid}" style="display:block">
							    									<div class="col-md-6 margin-bottom-10">
																			<label for="usr">Subject:</label>
																			<input type="text" class="form-control form-white" id="emailsubjecttext" value="${email.msgsubject}" />
																	</div>
																	<div class="col-md-6" >
																			<label >Param desc:</label>
																			<p  class="emailsubjectparamtext">${email.paramdesc}</p>
																	</div>
							    									<div class="width_100">		
							    											<label for="usr" class="exp-label"><b>Email Content:</b></label>
							    											<div class="col-md-9 emailcontent${email.templateid }">
							    													<textarea rows="5" cols="75" id="emailcontenttext${email.templateid}" class="content${email.templateid }" >${email.msgcontent}</textarea>
							    											</div>
							    									</div>
							    								</div>
							    								<c:set var = "i" value = "1"/>
														    </c:when>    
														    <c:otherwise>
														       <div class="width_100 email_templates_child" id="template${email.templateid}" style="display:none">
							    									<div class="col-md-6 margin-bottom-10">
																			<label for="usr">Subject:</label>
																			<input type="text" class="form-control form-white" id="emailsubjecttext" value="${email.msgsubject}" />
																	</div>
																	<div class="col-md-6">
																			<label >Param desc:</label>
																			<p  class="emailsubjectparamtext"  >${email.paramdesc}</p>
																	</div>
																	
							    									<div class="width_100">		
							    											<label for="usr" class="exp-label"><b>Email Content:</b></label>
							    											<div class="col-md-9 emailcontent${email.templateid }">
							    													<textarea rows="5" cols="75" id="emailcontenttext${email.templateid}" class="content${email.templateid }" >${email.msgcontent}</textarea>
							    											</div>
							    											
							    									</div>
							    									
							    								</div>
														    </c:otherwise>
														</c:choose>
										              			
				    									</c:forEach> 
				    									<div class="col-md-12">
				    										<button type="button" class="btn btn-primary pull-left " onclick="saveemailcontent()">Save</button>
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
       
    <script>
function openCity(evt, cityName) {
    var i, tabcontent, tablinks;
    tabcontent = document.getElementsByClassName("tabcontent");
    for (i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";
    }
    tablinks = document.getElementsByClassName("tablinks");
    for (i = 0; i < tablinks.length; i++) {
        tablinks[i].className = tablinks[i].className.replace(" active", "");
    }
    document.getElementById(cityName).style.display = "block";
    evt.currentTarget.className += " active";
}

 function displaymsgcontent(msgcontent,paramdesc,templateid)
{
	$('#templateidtext').val(templateid);
	$('#messagecontenttext').val(msgcontent);
	$('#messagecontentparam').text(paramdesc);
}
function displayemailcontent(emailcontent,paramdesc,templateid, emailsubject)
{
	$(".email_templates_parent>.email_templates_child").css({'display':'none'});
	$("#template"+templateid).css({'display':'block'});
	$('#emailtemplateidtext').val(templateid);
	$('#emailsubjecttext').val(emailsubject);
	$('.emailsubjectparamtext').text(paramdesc);
}

$(document).ready(function() {
  $('.content1').richText();
  $('.content2').richText();
  $('.content3').richText();
  $('.content4').richText();
  $('.content5').richText();
  $('.content6').richText();
  $('.content7').richText();
  $('.content8').richText();
  $('.content9').richText();
  $('.content10').richText();
  $('.richText-toolbar ul li a span.fa-image').parent().css( "display", "none" );
  $('.richText-toolbar ul li a span.fa-file-text-o').parent().css( "display", "none" );
  $('.richText-toolbar ul li a span.fa-video-camera').parent().css( "display", "none" );
  $('.richText-toolbar ul li a span.fa-paint-brush').parent().css( "display", "none" );
  $('.richText-toolbar ul li a span.fa-align-left').parent().css( "display", "none" );
  $('.richText-toolbar ul li a span.fa-align-center').parent().css( "display", "none" );
  $('.richText-toolbar ul li a span.fa-align-right').parent().css( "display", "none" );
  $('.richText-toolbar ul li a span.fa-list-ol').parent().css( "display", "none" );
  $('.richText-toolbar ul li a span.fa-text-height').parent().css( "display", "none" );
  $('.richText-toolbar ul li a span.fa-link').parent().css( "display", "none" );
  $('.richText-toolbar ul li a span.fa-font').parent().css( "display", "none" );
  $('.richText-toolbar ul li a span.fa-list').parent().css( "display", "none" );
  $('.richText-toolbar ul li a span.fa-header').parent().css( "display", "none" );
});

/* function saveemailcontent(){
console.log($('textarea.content').val())
}   */ 
</script>
<style>
.akcontent-div-height{
height:540px;
}
    .richText .richText-editor {
   
    height: 189px;
   
}
.content-div-heightnw {
    height: 328px;
    position: relative;
    overflow-y: scroll;
}
.btn-primary {
   
    float: right;
    margin-top: 20px;
}
.btn-danger {
   
    margin-top: 20px;
    margin-right: 10px;
}
</style>
	 
 <jsp:include page="../home/homefooter.jsp" />

</body>
</html>	 