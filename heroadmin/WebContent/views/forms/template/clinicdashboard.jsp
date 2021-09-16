<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<style>
.bg_dashboard{
background:#f5f5f5;
margin:10px;padding:10px;
color:#545454;
width:98%;
}
.text-right{
list-style:none;
}
</style>
<jsp:include page="../template/clinicheader.jsp" />
<div class="clearfix"></div>
<div class="col-md-12 bg_dashboard" >
<div   style="display: <%= request.getParameter("disp") %>">
            <div class="col-md-4 col-sm-4">
                        <h5><a href="clinicdashboard">DashBoard</a></h5>  
            </div>
            <div class="col-md-8 col-sm-8">
                        <ol class=" text-right">
                            <li><a href="heroclinicsettings">Config System Settings</a></li>
                           </ol>
            </div>
        </div>
  </div>     
        <div  class="col-lg-12">
<div class="card">
                        <div class="card-header">
                            <strong class="card-title"><i class="fa fa-table"></i> Dashboard</strong>
							
					      </div>
                          
                        <div class="card-body" style="padding:0;">
							<div id="pay-invoice">
							
							<h1>Clinic Dashboard</h1>
							</div>
							</div>
							</div>
							</div>
        
<jsp:include page="../template/clinicfooter.jsp" />
</body>
</html>