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
<body onload="loadaddnewtax()">
<%if(request.getParameter("disp") == null){ String applnid = request.getParameter("applnid");%>
  	<jsp:include page="../home/homeheader.jsp" />	
  	<input type="hidden" name="applnid" id="applnidli" value="${applnid }" />		
<% 
}else if(request.getParameter("disp").equals("n")){ String applnid = request.getParameter("applnid"); %>
	<script type="text/javascript">
	loadaddnewtax();
	</script>	
	<input type="hidden" name="applnid" id="applnidli" value="${applnid }" />	
<%} %>



					<div class="width_100  ">
						<div class="width_100 ">
							<div class="width_100">
								
								<div class="width_100 gray-font white-bg content-font-size content-div-height">
									<div class="col-md-12 full-padding-20">
									
										<div class="margin-bottom-10 content-font-size  white-font color-bg">
											<p class="padd-left-right-5"> Add Tax </p>
										</div>
										<div  class="col-sm-6 form-group">
								            <label><strong>Code <span style="color:red">*</span></strong></label>
								            <input placeholder="" class="form-control form-white" type="number" id="taxcodetext" value="${random_int}" readonly >
								          </div>
										 <!-- <div  class="col-sm-6 form-group">
								            <label><strong>Type</strong></label>
								            <select id="taxtypeselect" onchange="changetaxrate(this.val);">
								            <select id="taxtypeselect" class="form-control form-white">
								              <option value="P"> Percentage </option>
								              <option value="F"> Fixed </option>
								              
								            </select>
								          </div> -->
								
								          <div  class="col-sm-6 form-group">
								            <label><strong>Name <span style="color:red">*</span></strong></label>
								            <input placeholder="" class="form-control form-white" type="text" id="taxnametext">
								            <input placeholder="" class="form-control form-white" type="hidden" id="taxidtext">
								            <input placeholder="" class="form-control form-white" type="hidden" id="oprntext" value="INS">
								          </div>
								          <div  class="col-sm-6 form-group">
								            <label><strong>Type</strong></label>
								            <!-- <select id="taxtypeselect" onchange="changetaxrate(this.val);"> -->
								            <select id="taxtypeselect" class="form-control form-white">
								              <option value="P"> Percentage </option>
								              <option value="F"> Fixed </option>
								              
								            </select>
								          </div>
										  
										  <!-- <div  class="col-sm-6 form-group">
								            <label><strong>Code</strong></label>
								            <input placeholder="" class="form-control form-white" type="text" id="taxcodetext">
								          </div> -->
										  
										  <div  class="col-sm-6 form-group">
								            <label><strong>Rate <span style="color:red">*</span></strong></label>
								            <input placeholder="" class="form-control form-white" type="number" id="taxratetext">
								          </div>
										
										 <div class="col-md-12 margin-bottom-button">
											 <button type="button" class="btn btn-primary "  onclick="savetax();" style="float:right;">Save</button>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					
							<div >
								
								
									<table id="taxDT" class="hero-settings-table">
											<thead>
												<tr>
												   <!--  <th>S.NO</th> -->
													<th>Name</th>
													<th>Code</th>
													<th>Tax Rate</th>
													<th>Type</th>
													
													
												</tr>
											</thead>
											
								    </table>
										
				
							</div>
						
					<style>
					.content-div-height {
    height: 300px;
    }
					
					</style>
					 
<%if(request.getParameter("disp") == null){ %>
<jsp:include page="../home/homefooter.jsp" />
<%} %>
</body>
</html>
	 