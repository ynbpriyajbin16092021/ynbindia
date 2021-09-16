<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Store</title>
<%if(request.getParameter("disp") == null){ %>
<jsp:include page="../home/homelibrary.jsp" />
<%} %>	
<script type="text/javascript" src="../js/forms/admin/masters/masters.js"></script>
</head>

<body onload="loadaddnewstore()">

<%if(request.getParameter("disp") == null){ String applnid = request.getParameter("applnid");%>
  	<jsp:include page="../home/homeheader.jsp" />	
  	<input type="hidden" name="applnid" id="applnidli" value="${applnid }" />		
<% 
}else if(request.getParameter("disp").equals("n")){ String applnid = request.getParameter("applnid"); %>
	<script type="text/javascript">
	loadaddnewstore();
	</script>	
	<input type="hidden" name="applnid" id="applnidli" value="${applnid }" />	
<%} %>


				  <!-- Main content starts here -->
				         <div class="width_100  ">
						<div class="width_100 ">
							<div class="width_100">
								
								<div class="width_100 gray-font white-bg content-font-size content-div-height">
									<div class="col-md-12 full-padding-20">
									
										<div class="margin-bottom-10 content-font-size  white-font color-bg">
											<p class="padd-left-right-5"> Add Store </p>
										</div>
									<div class="col-md-6 form-group">
									<label for="StoreName"><strong>StoreName  <span style="color:red">*</span> </strong></label>
								    <input class="form-control form-white"  type="text" id="storenametext">
								    </div>									
					                <div class="col-md-6 form-group">
									<label for="Currencytype"><strong>Currency Type</strong></label>
								     <select id='currencytypeselect' class="form-control form-white">
                                     <c:forEach items="${currencyList}" var="currency" >                  
                                     <option value="${currency.value}">
                                      ${currency.label}
                                     </option>                    
                                     </c:forEach>
                                     </select>
                                    </div>	
								</div>
                                <div class="col-md-12">	   
						            <div class="col-md-6 form-group">
						            <label for="StoreName"><strong>Address <span style="color:red">*</span></strong> </label>
									<input class="form-control form-white" name="comment" rows="3" type="text"  id="addresstext" />
									
                                     <input class="form-control form-white" type="hidden" id="storeidtext"> 
                                    <input class="form-control form-white" type="hidden" id="oprntext" value="INS">
									</div>
									<div class="col-md-6 form-group">
									<label for="city"><strong>City <span style="color:red">*</span></strong></label>
									<input class="form-control form-white"  type="text" id="citytext">
									</div>
						        </div>
								 <div class="col-md-12">	   
						            <div class="col-md-6 form-group">
									<label for="state"><strong>State <span style="color:red">*</span></strong></label>
									<input class="form-control form-white"  type="text" id="statetext">

									</div>
									<div class="col-md-6 form-group">
									<label for="Country"><strong>Country  <span style="color:red">*</span></strong></label>
									<input class="form-control form-white"  type="text" id="countrytext">
									</div>
						        </div>
								<div class="col-md-12">	   
						            <div class="col-md-6 form-group">
									<label for="Zipcode"><strong>Zip code  <span style="color:red">*</span></strong></label>
									<input class="form-control form-white" type="number" id="zipcodetext">
									</div>
									
									<div class="col-md-6 form-group">
									<label for="Phone"><strong>Phone  <span style="color:red">*</span></strong></label>
									 <input class="form-control form-white preventchar" type="number" id="phonetext">
									</div>
									</div>
						        
								<div class="col-md-12">	   
						            <div class="col-md-6 form-group">
									<label for="Email"><strong>Email  <span style="color:red">*</span></strong></label>
									<input class="form-control form-white"   type="text" id="emailtext">
									</div>
									<div class="col-md-6 form-group">
									<label for="Tax"><strong>Tax <span style="color:red">*</span></strong></label><br>
									<input type="hidden" value="${taxList.size() }" id="taxsizetext">
                                    <c:forEach items="${taxList}" var="tax" > 
                                    <input type="checkbox" value="${tax.value }" id="taxcheck${tax.index }">${tax.label }</input>&nbsp;
                                    </c:forEach>
									</div>
						        </div>
								<div class="col-md-12" >
                                        <button type="button" class="btn btn-primary " onclick="savestore()" style="float:right;">Save</button>
										<!-- <button type="button" class="btn btn-danger ">Cancel</button> -->
			                      </div>
								
						  </div>
						  

                        </div>
                    </div>
                    </div>
                   
							
									<table id="storeDT" class="hero-settings-table"  style="width:100%">
											<thead>
												<tr>
													<th>Store Name</th>
													<th>Country</th>
													<th>Currency Type</th>
													<!-- <th>Phone</th>
													 <th>Email</th>  -->
													<th>Status</th>
													<!-- <th>Actions</th> -->
													
												</tr>
											</thead>

								    </table>
								
						
                    <style>
					.content-div-height {
    height: 400px;
    }
					label {
    display: inline-block;
    max-width: 100%;
    margin-bottom: 11px;
}
					</style>
	<script type="text/javascript">
$(document).ready(function(){
			document.querySelector(".preventchar").addEventListener("keypress", function (evt) {
			    if (evt.which != 8 && evt.which != 0 && evt.which < 48 || evt.which > 57)
			    {
			        evt.preventDefault();
			    }
			});
});
</script>			
<%if(request.getParameter("disp") == null){ %>
  	<jsp:include page="../home/homefooter.jsp" />
<%} %>
</body>
</html>