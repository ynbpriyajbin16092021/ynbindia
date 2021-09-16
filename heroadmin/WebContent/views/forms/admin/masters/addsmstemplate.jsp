<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title> SMS SETTINGS</title>
<%if(request.getParameter("disp") == null){ %>
<jsp:include page="../home/homelibrary.jsp" />
<%} %>
<script type="text/javascript" src="../js/forms/templates/template.js"></script>
</head>
<body onload="getsmsapidetails();">
<%if(request.getParameter("disp") == null){ %>
  	<jsp:include page="../home/homeheader.jsp" />
<%}else if(request.getParameter("disp").equals("n")){ %>
	<script type="text/javascript">
	getsmsapidetails();
	</script>	
<%} %>			 
					<div class="width_100  ">
						<div class="width_100 ">
							<div class="width_100">
								
								<div class="width_100 gray-font white-bg content-font-size content-div-height">
									<div class="col-md-12 full-padding-20">
									
										<div class="margin-bottom-10 content-font-size  white-font color-bg">
                            <p class="padd-left-right-5"> <i class="fa fa-shopping-bag"></i>SMS SETTINGS</p>
                        </div>
                        <div class="card-body">
                          <!-- Credit Card -->
                         
                          <div class="row">
          <div  class="col-sm-6 form-group">
            <label><strong>API Key</strong></label>
            <!-- <select id="taxtypeselect" onchange="changetaxrate(this.val);"> -->
            <input class="form-control form-white" placeholder="API Key" type="text" id="apikeytext">
            <input class="form-control" type="hidden" id="oprntext" value="INS">
           <!--  <select id="taxtypeselect" class="form-control form-white">
              <option value="P"> Percentage </option>
              <option value="F"> Fixed </option>
              
            </select> -->
          </div>

          <div  class="col-sm-6 form-group">
            <label><strong>UserName</strong></label>
            <input type="text" placeholder="binaryswanstudio@gmail.com" class="form-control inpttypestyle" id="usernametext" >
            <!-- <input placeholder="" class="form-control form-white" type="hidden" id="taxidtext">
            <input placeholder="" class="form-control form-white" type="hidden" id="oprntext" value="INS"> -->
          </div>
		  
		  <div  class="col-sm-6 form-group">
            <label><strong>Password</strong></label>
            <input type="text" placeholder="Yuvi@123" class="form-control inpttypestyle" id="passwordtext"  >
          </div>
		  
		  <div  class="col-sm-6 form-group">
            <label><strong>send SMS</strong></label>
            <input type="checkbox" id="smssendcheck">
          </div>
		  
        </div>
                         
								  
					<!---Modal End here--->				
									

                          

                       
                        
                        <div class="col-md-12 margin-bottom-button">
									
		                                 <button type="button" class="btn btn-primary  "  onclick="savesettings()">Save</button>
										<!-- <button type="button" class="btn btn-danger ">Cancel</button> -->
								</div>
								
								</div>
                    </div> <!-- .card -->
									
					</div>
					</div>
					</div>
					</div>				

					
					  <!-- Main content ends here -->
					
					
  	 
  	
    <!---modal start here-->
					 
<%if(request.getParameter("disp") == null){ %>
<jsp:include page="../home/homefooter.jsp" />
<%} %>
</body>
</html>
	 