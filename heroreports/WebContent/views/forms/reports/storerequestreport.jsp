<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Bill Details</title>
<jsp:include page="../template/library.jsp" /> 


<script type="text/javascript" src="../js/forms/reports/inventoryreports.js"></script>
    
</head>
<body onload="loadreport();"> 

<jsp:include page="../template/header.jsp" /> 

<div class="clearfix"></div>
					<div class="breadcrumbs"  style="display: <%= request.getParameter("disp") %>">
			            <div class="col-md-4 col-sm-4">
			                <div class="page-header">
			                        <p class="cursor-pointer mar-top-10 bread-a-style head-font-size yellow-font" href="stockdashboard"><strong>StoreRequest Report</strong></p>
			                </div>
			            </div>
			            <div class="col-md-8 col-sm-8">
			                <div class="page-header float-right">
			                    <a class="pull-right cursor-pointer bread-a-style content-font-size color-font" href="dashboard?uid=1">Home / Dashboard</a>
			                </div>
			            </div>
			        </div>
					<div class="clearfix"></div>
					
					<div class="col-md-12 overcome-col-padd-10  ">
							<div class="width_100 white-bg padding-container-10">

						    <div class="col-md-12">
          <div  class="col-md-6 margin-bottom-10" id="storeselectdiv">
         
         
        <%if(request.getParameter("rid") != null && !request.getParameter("rid").equals("1") && !request.getParameter("rid").equals("2")
          && !request.getParameter("rid").equals("4") && !request.getParameter("rid").equals("16")
          && !request.getParameter("rid").equals("5") && !request.getParameter("rid").equals("7"))
        	
        	  {  %>
        	   <label class="filter"> Company Name : </label>
          <select id='reportsstoreidselect' class="form-control form-white selectSer">
			<option value="0">--All--</option>
    			<c:forEach items="${storeList}" var="store" >                  
        		<option value="${store.value}">${store.label}</option>                    
    			</c:forEach>
    			</select>
			<%}else{ %>
			 
              		<!-- <div class="col-md-12 form-group">
						<label for="Product">Search By product</label>
						<input type="text" class="form-control form-white" onblur="checkAvailableProduct()"
						 id="itemcodetext"  placeholder="Please select Product"></input>
                      	<input type="hidden" id="productcodetext" value="0"></input>
						</div> -->
			<%} %> 
			
		  
		  
          </div>
			<div class="col-md-8" style="padding: 10px;">
			<div>
	          <div class="col-md-4"><p>Date Range</p></div>
	          <div class="col-md-8" id="reportrange"  style="background: #fff; cursor: pointer; padding: 5px 10px; border: 1px solid #ccc;margin-top:0px">
			    <i class="glyphicon glyphicon-calendar fa fa-calendar"></i>&nbsp;
			    <span></span> <b class="caret"></b>
			 </div>
			</div>
         </div>
          <div class=" col-md-2 margin-bottom-12 margin-top-10" >
			<button type="button" class="button-bg button-space pull-right" onclick="loadstorerequestreport();" >Generate Report</button>
		 </div>
 			<div class="cust-txt noprint">
				<button onclick="printClick()"><i class="fa fa-print" aria-hidden="true"></i></button></div>		
									<!-- <div class="col-md-4 margin-bottom-12 margin-top-10" >
											<button type="button" class="button-bg button-space pull-right" onclick="generatereport('reportDT');" >Generate Report</button>
												</div> -->	
								
										<div class="col-md-12 ">
									
										
										    <div class="rowNor marginTop" id='section-to-print'>
											   <div class="Report-header text-center" style=" padding-top: 23px;">
									            <h2>YNB CATERING SERVICES</h2>
									          
									            <h5><span>From</span>&nbsp;<label id="startdatelabel"></label> <span>To</span> &nbsp; <label id="enddatelabel"></label> </h5>
									          </div>
									         <!--  <div class="cust-txt">
									         <i class="fa fa-print" aria-hidden="true"></i>
									          </div> -->
									         <div >
											 <table id="storerequestreportDT"  class="hero-settings-table" style="width:100%">
																	 <thead>
                                                                             <!--   <tr>
											                                      <th> SL.NO </th>
											                                      <th> Food  </th>  
											                                      <th> SEOYON-1 </th>  
											                                      <th> SEOYON-2</th>
											                                      <th> HWAS-1</th>  
											                                      <th> CNF </th>
											                                      <th> HANYANG</th>
											                                      <th> TOTAL</th>  
											                                     
                                                                               </tr> -->
                                  </thead>
										<tbody>
										</tbody>							
																		
																	
																	  
											 </table>
                                            </div>
								
										</div>
											</div>  
											</div> 

	<script type="text/javascript">
	$(function() {

		var start = moment().subtract(29, 'days');
		var end = moment();

		function cb(start, end) {
			$('#reportrange span').html(start.format('MMMM D, YYYY') + ' - ' + end.format('MMMM D, YYYY'));
		}

		$('#reportrange').daterangepicker({
			startDate: start,
			endDate: end,
			ranges: {
			   'Today': [moment(), moment()],
			   'Yesterday': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
			   'Last 7 Days': [moment().subtract(6, 'days'), moment()],
			   'Last 30 Days': [moment().subtract(29, 'days'), moment()],
			   'This Month': [moment().startOf('month'), moment().endOf('month')],
			   'Last Month': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
			}
		}, cb);

		cb(start, end);
		
	});
	
	</script>
	
	<jsp:include page="../template/footer.jsp" /> 	

	
</body>
</html>