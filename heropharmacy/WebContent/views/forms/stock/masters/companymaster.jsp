<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Customer Master</title>
<%if(request.getParameter("disp") == null){ %>
<jsp:include page="../../template/library.jsp" /> 
<%} %>	
<script type="text/javascript" src="../js/forms/masters/masters.js"></script>
</head>
<body onload="loadcompanymaster();">

<%if(request.getParameter("disp") == null){ %>
  	<jsp:include page="../../template/header.jsp" />
<%} %>	  
				 
					<div class="clearfix"></div>
					<div class="breadcrumbs"  style="display: <%= request.getParameter("disp") %>">
			            <div class="col-md-4 col-sm-4">
			                <div class="page-header">
			                    <p class="cursor-pointer mar-top-10 bread-a-style head-font-size yellow-font" href="stockdashboard"><strong><i class="fa fa-users"></i>  Company Master</strong></p>
			                </div>
			            </div>
			            <div class="col-md-8 col-sm-8">
			                <div class="page-header float-right">
			                    <a class="pull-right cursor-pointer bread-a-style content-font-size color-font" href="dashboard?uid=1">Home / Dashboard</a>
			                </div>
			            </div>
			        </div>
					<div class="clearfix"></div>			
								
					<div class="tab-content akform">
					<div id="uom_master" class="tab-pane fade in active pill-space">
					<div class="col-md-12 overcome-col-padd-10">
						<div class="col-md-6 overcome-col-padd-10">
							<div class="width_100">

								<div class="width_100 gray-font white-bg content-font-size content-div-height">
									<div class="col-md-12 full-padding-20">
									<table id="companymasterDT" class="hero-settings-table" >
										<thead>
												<tr>
													<th>Customer Name</th>
													<th>Phone</th>
													<th>Actions</th>
													<th>Add Menu</th>
												</tr>
											</thead>
									</table>
								</div>
								</div>
							</div>
						</div>
						
							<div class="col-md-6 overcome-col-padd-10">
							<div class="width_100" >
								<div class="width_100 gray-font white-bg content-font-size content-div-heightnw" id="style-5">
									<div class="col-md-12 full-padding-20">
										<div class="width_100">
											<button class="button-bg button-space pull-right mar-bot-15 mar" onclick="clearcompanymasterFields()">New <i class="fa fa-plus-circle"></i></button>
										</div>
										<div class="clearfix"></div>
										
									<div class="margin-bottom-10 content-font-size  white-font color-bg">
											<p class="padd-left-right-5">Customer Master </p>
										</div>
									
									<div class="col-md-12 form-group">
									<label for="SupplierType">Customer Name<span style="color:red">*</span></label>
								     <input class="form-control" placeholder="Company Name"  type="text" id="companymasternametext">
								     
								     <input class="form-control form-white"  type="hidden" id="companymasteridtext" value="0">
					                  		<input class="form-control form-white"  type="hidden" id="oprntext" value="INS">
                                    </div>
                                    
									<div class="col-md-12 form-group">
									<label for="SupplierType">Email<span style="color:red">*</span></label>
								      <input class="form-control" placeholder="Email"  type="text" id="emailid" >
                                    </div>	
								
							       <div class="col-md-12 form-group">
									<label for="SupplierType">Contact Person<span style="color:red">*</span></label>
								      <input class="form-control" placeholder="Contact Person"  type="text" id="Contacttext" >
                                    </div>	
                                    
                                    <div class="col-md-12 form-group">
									<label for="SupplierType">phone<span style="color:red">*</span></label>
								      <input class="form-control" placeholder="phone number"  type="number" id="phonetext" >
                                    </div>	
                                    
                                      <div class="col-md-12 form-group">
									<label for="SupplierType">GSTIN NO<span style="color:red">*</span></label>
								      <input class="form-control" placeholder="GSTIN NO"  type="text" id="gstinnotext" >
                                    </div>	
                                    
                                      <div class="col-md-12 form-group">
									<label for="SupplierType">Breakfast Rate</label>
								      <input class="form-control" placeholder="Breakfast Rate"  type="number" id="breakfastrate" >
                                    </div>	
                                    
                                     <div class="col-md-12 form-group">
									<label for="SupplierType">Lunch Rate</label>
								      <input class="form-control" placeholder="Lunch Rate"  type="number" id="lunchrate" >
                                    </div>
                                    
                                     <div class="col-md-12 form-group">
									<label for="SupplierType">Dinner Rate</label>
								      <input class="form-control" placeholder="Dinner Rate"  type="number" id="dinnerrate" >
                                    </div>	
                                    
                                     <div class="col-md-12 form-group">
									<label for="SupplierType">Supper Rate</label>
								      <input class="form-control" placeholder="Supper Rate"  type="number" id="supperrate" >
                                    </div>	
                                    
                                    
                                    <div class="col-md-12 form-group">
									<label for="SupplierType">Address<span style="color:red">*</span></label>
								      <textarea class="form-control" placeholder="Address"  type="text" id="addresstext"></textarea> 
                                    </div>	
                                    
                                       <div class="col-md-12 form-group">
									<label for="SupplierType">Logo</label>
								     
                                 
                                         <div class="col-sm-12 upload-sec-txt ">
        <div class="form-group">
          <label class="col-sm-3 control-label"><p>Please Select File</p></label>
            <div class="col-sm-5">
              <div class="logoUploadDis">
                <span>
                
                   Select file to upload: <input type="file" name="file" size="60" id="logofiletype" accept="image/*" />
                   <input type="hidden" id="uploadedfilename" 
                   value="<%= request.getAttribute("uploadfilepath") == null ? "" :  request.getAttribute("uploadfilepath")%>">
                </span>
                  
              </div>
          </div>
    <div class="col-md-4"  style="padding:10px;">
										<img  id="ApplnorgnLogoImage" width="100px;">
											<script type="text/javascript">
													var img = new Image();
													img.onLoad = getcustomerlogo('ApplnorgnLogoImage');
											</script>
									</div>
          <div class="col-md-12">
         <!--  <div class="col-sm-4"> -->
              <div class="col-sm-2" style="float:right;" >
                <span>
                  
                <button onclick="uploadcustomerlogo()">upload</button>
                   
                </span>
                  
             <!--  </div> -->
         
           
          </div>  
          
           </div>

				</div>    
              
     
				</div>
                                  
		                            
                                    </div>
														
									<div class="col-md-12 margin-top-bottom-10">
									<div class="col-md-12 permissionDiv">
		                         			<button data-dismiss="modal" class="btn btn-primary" type="button" onclick="savecompanymaster()">Save</button>
								     		<button data-dismiss="modal" class="btn btn-default" type="button" onclick="clearcompanymasterFields();">Clear</button>
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
				$(document).ready(function() {
				    $('.hero-settings-table').DataTable( {
				    	bFilter: false, bInfo: false
				    } );
				} );
				</script>
	
<jsp:include page="../../template/footer.jsp" /> 

</body>
</html>

