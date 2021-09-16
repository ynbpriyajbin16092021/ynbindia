<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>CategoryScreen</title>
<%if(request.getParameter("disp") == null){ %>
<jsp:include page="../../template/library.jsp" /> 
<%} %>	
<script type="text/javascript" src="../js/forms/masters/masters.js"></script>
</head>
<body onload="loadaddnewcategory();">
<%if(request.getParameter("disp") == null){ String applnid = request.getParameter("applnid");%>
  	<jsp:include page="../../template/header.jsp" />
  	<input type="hidden" name="applnid" id="applnidli" value="${applnid }" />		
<%} else if(request.getParameter("disp").equals("n")){ String applnid = request.getParameter("applnid"); %>
	<script type="text/javascript">
	loadaddnewcategory();
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
											<p class="padd-left-right-5"> Add Category </p>
										</div>
                        <div class="card-body">
                          <!-- Credit Card -->
                          
                          <div class="col-md-6 form-group">
								   <label for="Manufacturer Company Name">Categories Name</label>
								   <input type="text" id="categorytext" class="form-control inpttypestyle">
								   <input class="form-control" type="hidden" id="oprntext" value="INS">
								   <input class="form-control" type="hidden" id="categoryidtext">
								</div>
                          <div class="col-md-12 com-padd" style="float:right;">
                          
		                         
		                          
								    <button data-dismiss="modal" class="btn btn-primary" type="button" onclick="savecategory()" style="float: right;">Save</button>
								     <button data-dismiss="modal" class="btn btn-default" type="button"  onclick="clearCategoryFormValues();" style="float: right;margin-right:10px;">Clear</button>
		                      

			</div>
									
                          

                        </div>
                    </div> <!-- .card -->
					</div>
					</div>
					</div>
					</div>
					  <!-- Main content ends here --> 
				
                  <div class="width_100">
								
								<div class="width_100 gray-font white-bg content-font-size content-div-height">
									<div class="col-md-12 full-padding-20">
									<table id="categoryDT" class="hero-settings-table" >
										<thead>
											<th>Category</th>
											
										</thead>
									</table>
								</div>
								</div>
							</div>

			<style>
			/* .content-div-height {
    height: 200px; */
			
			</style>


</body>
</html>

