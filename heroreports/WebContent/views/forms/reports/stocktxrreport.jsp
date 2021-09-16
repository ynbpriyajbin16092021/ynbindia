<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Stock Transfer Report</title>
<jsp:include page="../template/library.jsp" /> 


<script type="text/javascript" src="../js/forms/reports/inventoryreports.js"></script>
    
</head>
<body onload="loadreport()"> 

<jsp:include page="../template/header.jsp" /> 

<div class="clearfix"></div>
					<div class="breadcrumbs"  style="display: <%= request.getParameter("disp") %>">
			            <div class="col-md-4 col-sm-4">
			                <div class="page-header">
			                        <p class="cursor-pointer mar-top-10 bread-a-style head-font-size yellow-font" href="stockdashboard"><strong>Goods Transfer Report</strong></p>
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

						    <jsp:include page="../template/reportfilter.jsp" />

 										<div class="col-md-4 margin-bottom-12 margin-top-10" >
											<button type="button" class="button-bg button-space pull-right" onclick="generatereport('reportDT');" >Generate Report</button>
												</div>	
								
										<div class="col-md-12 ">
									
										
										    <div class="rowNor marginTop">
											   <div class="Report-header text-center">
									            <h3><%=session.getAttribute("username") %></h3>
									            <h4>Sales by Customer Report</h4>
									            <h5><span>From</span>&nbsp;<label id="startdatelabel"></label> <span>To</span> &nbsp; <label id="enddatelabel"></label> </h5>
									          </div>
											 <table id="reportDT"  class="hero-settings-table" style="width:100%">
																	 <thead>
																		  <tr>
																			<!--  <th>STORE NAME</th> -->
																			
																			 <th> PRODUCT NAME</th>  
																			<!--  <th>BATCH ID </th>   -->
																			<th>MANUFACTURER NAME</th>
																			 
																			  <th>QTY TRANSFERED</th>
																			  <th>TRANSFERED DATE</th>
																			  <!--  <th>	AMOUNT </th> -->
																		
																		  </tr>
																	  </thead>
																	
																		
																	
																	  
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
	