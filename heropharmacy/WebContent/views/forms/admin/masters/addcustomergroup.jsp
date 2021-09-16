<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Customer Group Screen</title>
<%if(request.getParameter("disp") == null){ %>
<jsp:include page="../../template/library.jsp" />
<%} %>
<script type="text/javascript" src="../js/forms/masters/masters.js"></script>
</head>
<body onload="loadaddnewcustomergroup();">
<%if(request.getParameter("disp") == null){ String applnid = request.getParameter("applnid");%>
  	<jsp:include page="../../template/header.jsp" />
  	<input type="hidden" name="applnid" id="applnidli" value="${applnid }" />		
<%} else if(request.getParameter("disp").equals("n")){ String applnid = request.getParameter("applnid"); %>
	<script type="text/javascript">
	loadaddnewcustomergroup();
	</script>	
	<input type="hidden" name="applnid" id="applnidli" value="${applnid }" />	
<%} %>	

				  
				  <!-- Main content starts here -->
				  
				  
				 
                   	<div class="width_100  ">
						<div class="width_100 ">
							<div class="width_100">
								
								<div class="width_100 gray-font white-bg content-font-size ">
									<div class="col-md-12 full-padding-20">
                           <div class="margin-bottom-10 content-font-size  white-font color-bg">
                           <p class="padd-left-right-5">Add Customer Group Name </p>  
                        </div>
                        <div class="card-body">
                          <!-- Credit Card -->
                           
                          
                          <div class="form-group col-sm-4">
                           <label for="Manufacturer Company Name">Customer Group Name</label>
					<input class="form-control" type="text" id="customergrouptext" >
					<input class="form-control" type="hidden" id="oprntext" value="INS">
					<input class="form-control" type="hidden" id="customergroupidtext" value="0">
				</div>
									
											
										 <div class="col-md-12 margin-bottom-button">
									 <button data-dismiss="modal" class="btn btn-primary" type="button" onclick="savecustomergroup()" style="float: right;">save</button>
								     <button data-dismiss="modal" class="btn btn-default" type="button" onclick="clearcustomergroupFields()" style="float: right;margin-right:10px;">Clear</button>
								</div>
                          

                        </div>
                    </div> <!-- .card -->
 
                        <div class="width_100">
								<!-- <div class="dashboard-ld-header margin-bottom-10 head-font-size white-font color-bg">
									<p><span style="font-size:18px;font-weight:bold"></span> Category </p>
								</div> -->
								<div class="width_100 gray-font white-bg content-font-size content-div-height">
									<div class="col-md-12 full-padding-20">
											<table id="customergroupDT" class="hero-settings-table"  style="width:100%">
												<thead>
													<tr>
														<th>Customer Group Name </th>
														
													</tr>
												</thead>
											</table>
									</div>
								</div>
							</div>
					  <!-- Main content ends here -->
							
				
				<!-- <script type="text/javascript">
				$(document).ready(function(){
					jQuery("#new").click(function(){
						jQuery('#myModal').modal('show');
					});	
					jQuery(".edit").click(function(){
						jQuery('#myPopup').modal('show');
					});
				});
				</script>  
                 -->
                 <style>
		 .content-div-height {
    height: 439px;
		 </style>
				
				   
		          
</body>
</html>