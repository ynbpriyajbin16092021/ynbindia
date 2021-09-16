<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Forgot Password Req</title>
<%if(request.getParameter("disp") == null){ %>
<jsp:include page="../home/homelibrary.jsp" />
<%} %>
<script type="text/javascript" src="../js/forms/admin/approvals/approval.js"></script>
</head>
<body onload="loadforgotpw()">
<%if(request.getParameter("disp") == null){ %>
  <jsp:include page="../home/homeheader.jsp" />
<%} %>

					<div class="clearfix"></div>
					<div class="breadcrumbs"  style="display: <%= request.getParameter("disp") %>">
			            <div class="col-md-4 col-sm-4">
			                <div class="page-header">
			                      <!--<strong class="card-title yellow-font"> Forgot Password</strong>  -->
			                       <p class="cursor-pointer mar-top-10 bread-a-style  head-font-size yellow-font" href="stockdashboard"><strong>Forgot Password</strong></strong></p> 
			                </div>
			            </div>
			            <div class="col-md-8 col-sm-8">
			                <div class="page-header float-right">
			                    <a class="pull-right cursor-pointer bread-a-style content-font-size color-font" href="herohome">Home / Dashboard</a>
			                </div>
			            </div>
			        </div>
					<div class="clearfix"></div>

                    <div class="col-md-12 overcome-col-padd-10">
						<div class="col-md-7 overcome-col-padd-10">
							<div class="width_100  white-bg">
									
								<div class="width_100 gray-font white-bg content-font-size content-div-height">
								<div class="col-md-12 full-padding-20">
										<table id="forgotpwDT" class="hero-settings-table" style="width:100%">
											<thead>
												<tr>
												   <!--  <th>S.NO</th> -->
													<th>Username</th>
													<th>Request Raised At</th>
													<th>New Password</th>
													<th>Request Raised Count</th>
													 <th>Action</th> 
													
												</tr>
											</thead>
								    </table>
								</div>
								</div>
                          </div>
                        </div>
                        
                   <div class="col-md-5 overcome-col-padd-10 ">
							<div class="width_100">
								<div class="width_100 gray-font white-bg content-font-size side-content-height content-div-height">
									<div class="col-md-12 full-padding-20">
										<div class="margin-bottom-10 content-font-size  white-font color-bg">
											<p class="padd-left-right-5">Forgot password </p>
										</div> 
										
										
										
                        <div class="akfcol-md-12 ">
							<div class="width_100 white-bg">
								<div class="akfcol-md-12">
									<div class="col-md-12 form-group " >
										<div class="col-md-6" >
										   <label for="Manufacturer Company Name" class="akfplsize">User Name</label>
										</div>
										   <div class="col-md-6 akfplpadtop" >
										   <span style="color:#edac00;font-weight:bold"><i id="usernameidlabel"></i></span>
										   </div>
									</div>
										<div class="col-md-12 form-group" >
										<div class="col-md-6" >
									   <label for="Manufacturer Company Name" class="akfplsize">Request Raised At</label>
									   </div>
									   <div class="col-md-6 akfplpadtop" >
									   <span style="color:#edac00;font-weight:bold"><i id="reqraisedatlabel"></i></span>
									   </div>
									</div>
									
									<div class="col-md-12 form-group" >
									<div class="col-md-6" >
									   <label for="Manufacturer Company Name" class="akfplsize">New Password</label>
									   </div>
									   <div class="col-md-6 akfplpadtop" >
									   <span style="color:#edac00;font-weight:bold"><i id="newpassword"></i></span>
									   </div>
									</div>
									<div class="col-md-12 form-group" >
									<div class="col-md-6" >
									   <label for="Manufacturer Company Name" class="akfplsize aknopad">Request Raised Count</label>
									   </div>
									   <div class="col-md-6 akfplpadtop" >
									   <span style="color:#edac00;font-weight:bold"><i id="reqraisedcountlabel"></i></span>
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
                        
<div id="myModal" class="modal fade modalForgetPassword" >
		              <div class="modal-dialog widthModalForget">
		                     <div class="modal-content">
      <div class="modal-header">
       
        
         
      </div>
      <div class="modal-body">
        
        <div class="row">
          <div  class="col-sm-12 form-group">
            <label>Please confirm to Reset Password</label> 
            <input type="hidden" id="forgetpwidtext" value="0">
          </div>
 
		    
		  
        </div>

      </div>
      <div class="clearfix"></div>
      <div class="modal-footer alignCenter" align="center">
        <div class="form-group " align="center">
             <button type="button" class="btn btn-secondary pull-left"  onclick="resetpassword();">Reset Password</button>
              <!-- <button type="button" class="btn btn-normal " data-dismiss="modal">Cancel</button> -->
        </div>
      </div>
    </div>
		              </div>
		          </div>
					 
<jsp:include page="../home/homefooter.jsp" />
</body>
</html>
	 