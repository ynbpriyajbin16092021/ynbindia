 <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Company Master</title>

<script type="text/javascript" src="../js/forms/admin/masters/masters.js"></script>

</head>
<body onload="loadaddnewcompany()">
<%if(request.getParameter("disp") == null){ String applnid = request.getParameter("applnid");%>
  	<jsp:include page="../../template/header.jsp" />
  	<input type="hidden" name="applnid" id="applnidli" value="${applnid }" />		
<%} else if(request.getParameter("disp").equals("n")){ String applnid = request.getParameter("applnid"); %>
	<script type="text/javascript">
	loadaddnewcompany();
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
											<p class="padd-left-right-5"> Add Manufacturer Company </p>
										</div>
                        <div class="card-body">
                         
                          <div class="col-md-6 form-group">
								   <label for="Manufacturer Company Name">Manufacturer Company Name</label>
								   <input type="text" id="companynametext" class="form-control inpttypestyle">
			                        <input class="form-control" type="hidden" id="oprntext" value="INS">
					                <input class="form-control" type="hidden" id="companyidtext">
								</div>
								
                          
                          <div class="col-md-12 com-padd" style="float:right;">
									 <button data-dismiss="modal" class="btn btn-primary" type="button" onclick="savecompany()" style="float: right;">save</button>
								     <button data-dismiss="modal" class="btn btn-default" type="button" onclick="clearCompanyFields()" style="float: right;margin-right:10px;">Clear</button>
								</div>
										
                        </div>
                    </div> <!-- .card -->
                         </div>
					</div>
					</div>
					</div> 
                            <div class="width_100">
								
								<div class="width_100 gray-font white-bg content-font-size content-div-height">
									<div class="col-md-12 full-padding-20">
									<table id="companyDT" class="hero-settings-table" >
										<thead>
											<th>Manufacturer Company Name</th>
											
										</thead>
									</table>
								</div>
								</div>
							</div>
						
					  <!-- Main content ends here -->
					  <!--  <script type="text/javascript">
				$(document).ready(function(){
					
				jQuery(".new").click(function(){
					jQuery('#myModal').modal({backdrop: true});
				   
					
					});	
					
					jQuery(".edit").click(function(){
					jQuery('#myPopup').modal({backdrop: true});
				   });	
				});
				</script>  
		 -->
		<!--  <style>
		 .content-div-height {
    height: 200px; -->
		 </style>
		
		
	<jsp:include page="../../template/footer.jsp" /> 			  
				 
		               

</body>
</html>
 