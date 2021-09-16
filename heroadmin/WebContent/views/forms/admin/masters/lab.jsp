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
<body onload="loadlab();">

<%if(request.getParameter("disp") == null){ %>
  	<jsp:include page="../home/homeheader.jsp" />
<%} %>

   <div class="clearfix"></div>
					<div class="breadcrumbs"  style="display: <%= request.getParameter("disp") %>">
			            <div class="col-md-4 col-sm-4">
			                <div class="page-header">
			                        <p class="cursor-pointer mar-top-10 bread-a-style head-font-size yellow-font" href="stockdashboard"><strong> <i class="fa fa-medkit" ></i> Clinics List </strong></p>
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
									<div class="col-md-12 ">
                             <a href="addlab?lid=0" class="button-bg button-space pull-right mar-bot-15">New <i class="fa fa-plus-circle"></i></a>
                             </div>
                            
						          <table id="clinicDT" class="hero-settings-table">
											<thead>
												<tr>
												  <th>LAB DESC</th>
												    <th>LAB CODE</th>
													<th>ADDRESS</th>
													<th>CONTACT NO</th>
													<th>LAB LOCATION</th>
												    <th>ACTION</th>
												</tr>
											 </thead>
											 <tbody>
											   <c:forEach var="lab" items="${labfullList }">
                              <tr>
                               <td>${lab.hl_clinic_desc}</td>
                            <td>${lab.hl_labcode}</td>
                            <td>${lab.hl_address}</td>
                             <td>${lab.hl_contact_no}</td>
                             <td>${lab.hl_clinic_location}</td>
                            <td><i class="fa fa-edit" onclick="editlab(${lab.hl_id})" id="labid"></i></td>
                           
                             </tr>
						  </c:forEach>  
											 
											 </tbody>
								  </table>
									
							</div>
                        </div>
                    </div> 
                    </div>
                    </div>
                     


<%if(request.getParameter("disp") == null){ %>
<jsp:include page="../home/homefooter.jsp" />
<%} %>

</body>
</html>