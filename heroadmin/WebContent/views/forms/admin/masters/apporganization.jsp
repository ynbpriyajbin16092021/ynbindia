<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Organization</title>
<%if(request.getParameter("disp") == null){ %>
<jsp:include page="../home/homelibrary.jsp" />
<%} %>
<script type="text/javascript" src="../js/forms/templates/template.js"></script>
</head>
<body onload="loadorganization()" class="bg_orgn">
  
 <%if(request.getParameter("disp") == null){ %> 
  	 <jsp:include page="../home/homeheader.jsp" />
 <%} %> 
<div class="clearfix"></div>
					<div class="breadcrumbs"  style="display: <%= request.getParameter("disp") %>">
			            <div class="col-md-4 col-sm-4">
			                <div class="page-header">
			                        <p class="cursor-pointer mar-top-10 bread-a-style head-font-size yellow-font" href="stockdashboard"><strong><i class="fa fa-building-o"></i> My company</strong></p>
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

								<form method="post" action="fileUpload" enctype="multipart/form-data">

                               <div class="col-md-8 form-group">
										<label for="companyname">Organization Name <span style="color:red">*</span></label>
										<input class="form-control form-white" placeholder="Company Name" type="text" id="compnametext">
                                        <input class="form-control form-white" placeholder="Company Name" type="hidden" id="oprntext" value="INS">
                                </div>
								<div class="col-md-8 form-group">
										<label for="mobilenumber">Mobile Number <span style="color:red">*</span></label>
										 <input class="form-control form-white" placeholder="Mobile Number" type="number" id="mobnotext">
                                </div>
								<div class="col-md-8 form-group">
										<label for="emailaddress">Email Address <span style="color:red">*</span></label>
										<input class="form-control form-white" placeholder="E-Mail" type="text" id="emailtext">
                                </div>
                                 <div class="col-md-8 margin-bottom-15">
                                 		<div class="width_40">
											<label  class="exp-label" for="accountstartsyear">Accounts year <span style="color:red">*</span></label>
										</div>
										 <div class="width_60">
											 <div class="width_45" >
												<input class="yearpicker form-control form-white" placeholder="accountstartsyear" type="text" id="startyear">
											</div>
											<div class="width_10" >
												<p style="padding: 0 12px;"> - </p>
											</div>
											 <div class="width_45" >
												<input class="yearpicker form-control form-white" placeholder="accountendyear" type="text" id="endyear">
											</div>
										</div>
                                </div>
                                
                                 <div class="col-md-8 form-group">
										<label for="date">Account start Date <span style="color:red">*</span></label>
											<input class="datepicker form-control" placeholder="Date" type="text" id="startdate" style="padding: 6px 12px;">
                                </div>
								<div class="col-md-8 form-group">
										<label for="Address">Address <span style="color:red">*</span></label>
										<textarea class=" form-control form-white" placeholder="Address" type="text" rows="3" cols="50" id="orgnaddresstext" style="padding: 0px 12px;"></textarea>
                                </div>
                                <div class="col-md-8 form-group">
										<label for="Address">GSTIN No <span style="color:red">*</span></label>
										<input class=" form-control form-white" placeholder="GSTIN No" type="text" rows="3" cols="50" id="gstinnotext" style="padding: 0px 12px;">
                                </div>
                                 <div class="col-md-8 form-group" style="display:none">
										<label for="accountstartsyear">Need stock transfer  <span style="color:red">*</span></label>
										<span class="checkbox-inline akucheckbox-inline"><input type="radio" name="stocktransfertext" value="yes"  > YES</span>
		                                  <span class="checkbox-inline akucheckbox-inline1"><input type="radio" name="stocktransfertext" value="no" checked> NO</span>	
                                </div>
								 
                                </div>
                                <div class="col-md-12 form-group">
                                
                                	<div class="col-md-8">
                                		<label >Upload Profile Photo <span style="color:red">*</span></label>
                                		<div class="width_60">
											<iframe src="/heroadmin/forms/heroupload?name=companylogo.jpg&applntype=0&requesttype=1" id="imgpathtext"
										 	class="hero-theme-iframe" width="100%"></iframe>
										 	<input class="form-control form-white" placeholder="Upload Profile Photo" type="hidden" id="logotext" value="companylogo.jpg">
										</div>
	                                </div>	
									<div class="col-md-4">
											<img  id="ApplnorgnLogoImage" width="100px;" style="height: 75px;">
											<script type="text/javascript">
													var img = new Image();
													img.onLoad = getlogo('ApplnorgnLogoImage');
											</script>
									</div>
								</div>
								 <div class="col-md-12 form-group">
								 	<div class="col-md-8">
                                 		<label>Note</label>
										<div class="width_60">
											<span>File taken from</span>
											<span id="imgfilepathspan"></span>
											<span>
												This logo will appear on the documents (sales order, purchase order, ...) that you create Preferred Image Size: 240px x 240px @ 72 DPI Maximum size of 1MB.<br>
											</span>
										</div>
									</div>
									</div>
								<div class="col-md-8 form-group" >
                                        <button type="button" class="btn btn-primary " onclick="saveorgn()">Save</button>
										<button type="button" class="btn btn-grey " onclick="return reset_orgn();">Cancel</button>
										
			                    </div>
						</form>	
                        </div>
                        </div>
                        </div>
                        </div>
                        </div>
              <style>
              .content-div-height {
    height: 693px;}
              </style>   
                 
<jsp:include page="../home/homefooter.jsp" />	
</body>
</html>					
                 