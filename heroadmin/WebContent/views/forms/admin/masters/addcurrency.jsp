<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Currency Master</title>
<%if(request.getParameter("disp") == null){ %>
<jsp:include page="../home/homelibrary.jsp" />
<%} %>
<script type="text/javascript" src="../js/forms/admin/masters/masters.js"></script>
</head>
<body onload="loadaddnewcurrency();">
  
<%if(request.getParameter("disp") == null){ String applnid = request.getParameter("applnid");%>
  	<jsp:include page="../home/homeheader.jsp" />	
  	<input type="hidden" name="applnid" id="applnidli" value="${applnid }" />		
<% 
}else if(request.getParameter("disp").equals("n")){ String applnid = request.getParameter("applnid"); %>
	<script type="text/javascript">
	loadaddnewcurrency();
	</script>	
	<input type="hidden" name="applnid" id="applnidli" value="${applnid }" />	
<%} %>


				
					<div class="width_100 ">
						<div class="width_100 ">
							<div class="width_100" style="margin-bottom:100px;">
								
								<div class="width_100 gray-font white-bg content-font-size content-div-height">
									<div class="col-md-12 full-padding-20">
									
										<div class="margin-bottom-10 content-font-size  white-font color-bg">
											<p class="padd-left-right-5"> Add Currency </p>
										</div>
											<div class="col-md-6 form-group">
											   <label for="Currencycode"><strong>Currency Code <span style="color:red">*</span></strong></label>
											   <input type="number" id="currencycodeselect" class="form-control form-white" value="${random_int}" readonly>
											</div>
											<div class="col-md-6 form-group">
											<label for="Currencyname"><strong>Currency Name <span style="color:red">*</span></strong></label>
											<input  class="form-control form-white" type="text" id="currencynametext" maxlength="64" required>
			                                <input  class="form-control form-white" type="hidden" id="currencyidtext">
			                                <input  class="form-control form-white" type="hidden" id="oprntext" value="INS">
											</div>
											<!-- <div class="col-md-6 form-group">
											   <label for="Currencycode"><strong>Currency Code</strong></label>
											   <input type="text" id="currencycodeselect" class="form-control form-white">
											</div> -->
											<div class="col-md-6 form-group">
											   <label for="Currencysymbol"><strong>Currency Symbol <span style="color:red">*</span></strong></label>
											    
											   <div class="width_60">
											   <div class="width_81">
												   <input class="form-control form-white"  type="text" id="currencysysmbolsuggestiontext" >
												   <input class="form-control form-white"  type="hidden" id="currencysysmboltext">
											   </div>
											   <div class="width_19">
											   		<span class="symbolstyle" id="currencyhtmlcode"></span>
											   </div>
											   </div>
											</div>
											<div class="col-md-6 form-group">
											   <label for="currencydecimal"><strong>Currency Decimal <span style="color:red">*</span></strong><i data-rel="tooltip"  data-toggle="tooltip" data-placement="right" data-original-title=" Multiple with base currency"></i></label>
											   <input class="form-control form-white" type="hidden" id="currdecimaltext" maxlength="3" >
                                              <input class="form-control form-white" type="number" id="currencydecimal" maxlength="3" >
											</div>
											<div  class="col-md-7 form-group" >
			                                <label><strong>Base Currency</strong></label>
			                                
			                               <input id="basecurrchk" type="checkbox" onclick="disableconvratetext()" ></legend>
			                               
			                                
			                              </div>
			                              <div id="basecurrencyhide">
											<div class="col-md-6 form-group">
											   <label for="Exchangerate"><strong>Exchange Rate (Exchange rate of Currency with Base currency) <span style="color:red">*</span></strong></label>
											   <input type="number" id="exchngratetext" class="form-control form-white">
											   <!-- <input class="form-control form-white" type="number" id="exchngratetext" maxlength="7"> -->
											</div>
											<div class="col-md-6 form-group">
											   <label for="Conversionrate"><strong>Conversion Rate <span style="color:red">*</span></strong></label>
											   <input type="number" id="convratetext" class="form-control form-white" value="1" >
											     <!-- <input class="form-control form-white" type="number" id="convratetext" maxlength="7"> -->
											</div>
											
											</div>
			                              <!-- <input type="checkbox" id="checkMe" name="checkMe" onclick="disableMyText()"/>   
                                            <input type="text" value="" id="myText" /> -->
											<div class="col-md-6 form-group">
											   <label for="Fromdate"><strong>Rate From Date <span style="color:red">*</span></strong></label>
											   <input type="text" id="fromdatetext" class="datepicker form-control form-white">
											</div>
											
											<div class="col-md-6 form-group">
											   <label for="Fromdate"><strong>Rate To Date <span style="color:red">*</span></strong></label>
											   <input type="text" id="todatetext" class="datepicker form-control form-white">
											</div>	
										 <div class="col-md-12 margin-bottom-button">
												  <button data-dismiss="modal" class="btn btn-primary" type="button" onclick="savecurrency()" style="float:right;">Save</button>
											     <!-- <button data-dismiss="modal" class="btn btn-danger" type="button" onclick="clearcurrencyFields()">Clear</button> -->
											</div>
												
									</div>
								</div>
							</div>
						</div>
					</div>
				<div id="style-5 ">
			
									<table id="currencyDT" class="hero-settings-table" >
											<thead>
												<tr>
												    
													<th>Name</th>
													<th>Symbol</th>
													<th>From Date</th>
													<th>To Date</th>
												  <th>Currency Decimal</th> 
													
													
												</tr>
											</thead>
											
								    </table>
									
						
				</div>
				
					 <script type="text/javascript" >
					 function disableconvratetext(){  
				          if(document.getElementById("basecurrchk").checked == true){  
				               $('#basecurrencyhide').css({'display':'none'});
				               $("#exchngratetext").val("1");
				               $("#convratetext").val("1");
				               
				          }else{
				        	  $('#basecurrencyhide').css({'display':'block'});
				          }  
				     }   
				
     </script> 

   
					<style>
					input[type="checkbox"] {
					margin-top:20px;
					}
					.content-div-height {
    height: 369px;
    }
			
					</style>
				
	<%if(request.getParameter("disp") == null){ %>
<jsp:include page="../home/homefooter.jsp" />
<%} %>
</body>
</html>					
                 