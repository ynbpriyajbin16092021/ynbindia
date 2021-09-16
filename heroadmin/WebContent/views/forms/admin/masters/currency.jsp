<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Currency Master</title>
<%if(request.getParameter("disp") == null){ %>
<jsp:include page="../home/homelibrary.jsp" />
<%} %>
<script type="text/javascript" src="../js/forms/admin/masters/masters.js"></script>
</head>
<body onload="loadcurrency();">
  
<%if(request.getParameter("disp") == null){ %>
  	 <jsp:include page="../home/homeheader.jsp" />
<%} %>


     <!-- Main content starts here -->
				  
					<div class="clearfix"></div>
					<div class="breadcrumbs"  style="display: <%= request.getParameter("disp") %>">
			            <div class="col-md-4 col-sm-4">
			                <div class="page-header">
			                        <p class="cursor-pointer mar-top-10 bread-a-style head-font-size yellow-font" href="stockdashboard"><strong><i class="fa fa-shopping-bag"></i>Currency Master</strong></p>
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
									<table id="currencyDT" class="hero-settings-table">
											<thead>
												<tr>
												    
													<th>Name</th>
													<th>Symbol</th>
													<th>From Date</th>
													<th>To Date</th>
												  	<th>Currency Decimal</th> 
													<th>Actions</th>
													
												</tr>
											</thead>
											
								    </table>
									</div>
								</div>
							</div>
						</div><!-- .card -->

					
  <!-- Main content ends here -->
  
  <!-- <a  href="adduser?userid=0" class="button-bg button-space pull-right mar-bot-15" >New <i class="fa fa-plus-circle"></i></a><br> -->
  
						<div class="col-md-5 overcome-col-padd-10" >
						
							<div class="width_100 " >
								<div class="width_100 gray-font white-bg content-font-size side-content-height content-div-height content-div-heightnw" id="style-5">
									<div class="width_100">
							<button data-dismiss="modal" class="button-bg button-space pull-right mar-bot-15 mar"   onclick="clearCurrencyFields()">New <i class="fa fa-plus-circle"></i></button>
						</div>
									<div class="col-md-12 full-padding-20" >
									
										<div class="margin-bottom-10 content-font-size  white-font color-bg">
											<p class="padd-left-right-5">currency </p>
										</div>
  
 
  
	
		              		<div class="col-md-12 form-group">
								   <label for="Currencycode">Currency Code <span style="color:red">*</span></label>
								   <input type="number" id="currencycodeselect" class="form-control form-white" value="${random_int}" readonly>
							</div>
		                  
		                      
		                      <div class="col-md-12 form-group" >
		                        
								   <label for="Currencyname">Currency Name <span style="color:red">*</span></label>
								  <input  class="form-control form-white" type="text" id="currencynametext" maxlength="64">
                                  <input  class="form-control form-white" type="hidden" id="currencyidtext">
                                  <input  class="form-control form-white" type="hidden" id="oprntext" value="INS">
								   
								</div>
								<!-- <div class="col-md-12 form-group">
								   <label for="Currencycode">Currency Code</label>
								   <input type="text" id="currencycodeselect" class="form-control form-white">
								</div> -->
								<div class="col-md-12 form-group">
								   <label for="Currencysymbol">Currency Symbol <span style="color:red">*</span></label>
								   <div class="width_60">
								   <div class="width_81">
									   <input class="form-control form-white"  type="text" id="currencysysmbolsuggestiontext" onclick="enablecurrencysymbollist()" >
									   <input class="form-control form-white"  type="hidden" id="currencysysmboltext">
								   </div>
								   <div class="width_19">
								   		<span class="symbolstyle" id="currencyhtmlcode"></span>
								   </div>
								   </div>
									
								</div>
								<div class="col-md-12 form-group">
											   <label for="currencydecimal">Currency Decimal <span style="color:red">*</span><i data-rel="tooltip"  data-toggle="tooltip" data-placement="right" data-original-title=" Multiple with base currency"></i></label>
											   <input class="form-control form-white" type="hidden" id="currdecimaltext" maxlength="3"  value="0" >
                                              <input class="form-control form-white" type="number" id="currencydecimal" maxlength="3" value="0">
											</div>
											<div  class="col-md-12 form-group" >
			                                <label>Base Currency</label>
			                                <input id="basecurrchk" type="checkbox" onclick="disableconvratetext()" ></legend>
			                                
			                               <!-- <input type="checkbox" id="basecurrchk" onclick="disableconvratetext()" > -->
			                               
			                                
			                              </div>
			                              
			                              <div id="basecurrencyhide">
											<div class="col-md-12 form-group">
											   <label for="Exchangerate">Exchange Rate (Exchange rate of Currency with Base currency) <span style="color:red">*</span></label>
											   <input type="number" id="exchngratetext" class="form-control form-white">
											   <!-- <input class="form-control form-white" type="number" id="exchngratetext" maxlength="7"> -->
											</div>
											<div class="col-md-12 form-group">
											   <label for="Conversionrate">Conversion Rate <span style="color:red">*</span></label>
											   <input type="number" id="convratetext" class="form-control form-white" value="1">
											     <!-- <input class="form-control form-white" type="number" id="convratetext" maxlength="7"> -->
											</div>
											</div>
											
											<div class="col-md-12 form-group">
											   <label for="Fromdate">Rate From Date <span style="color:red">*</span></label>
											   <input type="text" id="fromdatetext" class="datepicker form-control form-white">
											</div>
											
											<div class="col-md-12 form-group">
											   <label for="Fromdate">Rate To Date <span style="color:red">*</span></label>
											   <input type="text" id="todatetext" class="datepicker form-control form-white">
											</div>
											
			                              <!-- <input type="checkbox" id="checkMe" name="checkMe" onclick="disableMyText()"/>   
                                            <input type="text" value="" id="myText" /> -->
		                      
		                         
		                          <div class="col-md-12 form-group">
								   <button data-dismiss="modal" class="btn btn-primary" type="button" onclick="savecurrency()">Save</button>
		                      </div>
		                      </div>
								</div>
							</div>
						</div>
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
					 
					 
					/* function toggle(exchngratetext, obj) {
					    var $input = $(obj);
					    if ($input.prop('checked')) $(exchngratetext).hide();
					    else $(exchngratetext).show();
					}	 */	
     </script> 
		                      
		                      
		               <style>
		               
		               .mar{
		               margin-top:10px;
		               margin-right:10px;
		               }
		               input[type="checkbox"] {
					margin-top:20px;
					}
		               </style>       
		                      
		                  
		              
		          		
		          <jsp:include page="../home/homefooter.jsp" />	
</body>
</html>					
                 