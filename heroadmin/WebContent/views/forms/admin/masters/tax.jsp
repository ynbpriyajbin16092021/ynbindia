<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Tax Master</title>
<%if(request.getParameter("disp") == null){ %>
<jsp:include page="../home/homelibrary.jsp" />
<%} %>
<script type="text/javascript" src="../js/forms/admin/masters/masters.js"></script>
</head>
<body onload="loadtax()">
<%if(request.getParameter("disp") == null){ %>
  <jsp:include page="../home/homeheader.jsp" />
<%} %>






					<div class="clearfix"></div>
					<div class="breadcrumbs"  style="display: <%= request.getParameter("disp") %>">
			            <div class="col-md-4 col-sm-4">
			                <div class="page-header">
			                        <p class="cursor-pointer mar-top-10 bread-a-style head-font-size yellow-font" href="stockdashboard"><strong><i class="fa fa-shopping-bag"></i>Tax Master</strong></p>
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
						<div class="col-md-7 overcome-col-padd-10 ">
							<div class="width_100">
								
								<div class="width_100 gray-font white-bg content-font-size content-div-height">
								
									<div class="col-md-12 full-padding-20">
									<table id="taxDT" class="hero-settings-table">
											<thead>
												<tr>
												   <!--  <th>S.NO</th> -->
													<th>Name</th>
													<th>Code</th>
													<th>Tax Rate</th>
													<th>Type</th>
													<th>Actions</th>
													
												</tr>
											</thead>
											
								    </table>
										
								  
					<!---Modal End here--->				
									
</div>
								</div>
							</div>
						</div><!-- .card -->
						
						
						
						<div class="col-md-5 overcome-col-padd-10 ">
							<div class="width_100">
								<div class="width_100 gray-font white-bg content-font-size side-content-height content-div-height">
								<div class="width_100">
							<button data-dismiss="modal" class="button-bg button-space pull-right mar-bot-15 mar"   onclick="clearTaxFields()">New <i class="fa fa-plus-circle"></i></button>
						</div>
									<div class="col-md-12 full-padding-20">
										<div class="margin-bottom-10 content-font-size  white-font color-bg">
											<p class="padd-left-right-5">Tax </p>
										</div>

					
					  <!-- Main content ends here -->
					
    <!---modal start here-->
					 
		  <div  class="col-sm-12 form-group" style="display:none">
            <label>Code <span style="color:red">*</span></label>
            <input placeholder="" class="form-control form-white" type="number" id="taxcodetext" value="${random_int}" readonly >
          </div>       
               
		  <div  class="col-sm-12 form-group">
            <label>Name  <span style="color:red">*</span></label>
            <input placeholder="" class="form-control form-white" type="text" id="taxnametext">
            <input placeholder="" class="form-control form-white" type="hidden" id="taxidtext">
            <input placeholder="" class="form-control form-white" type="hidden" id="oprntext" value="INS">
          </div>                  
     
      
        
          <div class="col-md-12 form-group" >
            <label>Type</label>
            <!-- <select id="taxtypeselect" onchange="changetaxrate(this.val);"> -->
            <select id="taxtypeselect" class="form-control form-white">
              <option value="P"> Percentage </option>
              <option value="F"> Fixed </option>
              
            </select>
          </div>

          <!-- <div  class="col-sm-12 form-group">
            <label>Name</label>
            <input placeholder="" class="form-control form-white" type="text" id="taxnametext">
            <input placeholder="" class="form-control form-white" type="hidden" id="taxidtext">
            <input placeholder="" class="form-control form-white" type="hidden" id="oprntext" value="INS">
          </div> -->
		  
		  <!-- <div  class="col-sm-12 form-group">
            <label>Code</label>
            <input placeholder="" class="form-control form-white" type="text" id="taxcodetext">
          </div> -->
		  
		  <div  class="col-sm-12 form-group">
            <label>Rate <span style="color:red">*</span></label>
            <input placeholder="" class="form-control form-white" type="number" id="taxratetext">
          </div>
		  
       

      
     
      <div class="modal-footer alignCenter" align="center ">
        <div class="form-group " align="center">
             <button type="button" class="btn btn-primary pull-left"  onclick="savetax();">Save</button>
              <!-- <button type="button" class="btn btn-normal " data-dismiss="modal">Cancel</button> -->
        </div>
      </div>
      
      </div>
      </div>
      </div>
      </div>
      
           
		               <style>
		               
		               .mar{
		               margin-top:10px;
		               margin-right:10px;
		               }
		               .margin-bottom-10 {
                        margin-bottom: -11px;
                        }
                        .form-group {
   
                        margin-top: 10px;
                        }
		               
		               </style>       
		                      
		                  
		         
<jsp:include page="../home/homefooter.jsp" />
</body>
</html>
	 