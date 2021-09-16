<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>bankScreen</title>
<%if(request.getParameter("disp") == null){ %>
<jsp:include page="../../template/library.jsp" />
<%} %>	
<script type="text/javascript" src="../js/forms/masters/masters.js"></script>
</head>
<body onload="loadaddnewbank();">
<%if(request.getParameter("disp") == null){ String applnid = request.getParameter("applnid");%>
  	<jsp:include page="../../template/header.jsp" />
  	<input type="hidden" name="applnid" id="applnidli" value="${applnid }" />		
<%} else if(request.getParameter("disp").equals("n")){ String applnid = request.getParameter("applnid"); %>
	<script type="text/javascript">
	loadaddnewbank();
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
											<p class="padd-left-right-5"> Add Bank Name </p>
										</div>
                        <div class="card-body">
                          <!-- Credit Card -->
                         
                          <div class="col-md-4 form-group">
								   <label for="Manufacturer Company Name">Bank Name</label>
								   <input class="form-control" type="text" id="banktext">
					               <input class="form-control" type="hidden" id="oprntext" value="INS">
					               <input class="form-control" type="hidden" id="bankidtext">
								</div>
                          <div class="col-md-12 com-padd" style="float:right;">
                          <button data-dismiss="modal" class="btn btn-primary" type="button" onclick="savebank()" style="float: right;">save</button>
								     <button data-dismiss="modal" class="btn btn-default" type="button" onclick="clearBankFormValues();" style="float: right;margin-right:10px;">Clear</button>
                          
			                        </div>
			                         </div> <!-- .card -->
                         </div>
					</div>
					</div>
					</div> 
					
							<div class="width_100">
								<!-- <div class="dashboard-ld-header margin-bottom-10 head-font-size white-font color-bg">
									<p><span style="font-size:18px;font-weight:bold"></span> Category </p>
								</div> -->
								<div class="width_100 gray-font white-bg content-font-size content-div-height">
									<div class="col-md-12 full-padding-20">
									<table id="bankDT" class="hero-settings-table" >
										<thead>
											<th>Bank Name</th>
											
										</thead>
									</table>
								</div>
								</div>
							</div>
						
					<style>
		 .content-div-height {
    height: 439px;
		 </style>
			                        
                          
 

</body>
</html>