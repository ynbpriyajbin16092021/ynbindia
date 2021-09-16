<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Expiry Date Checker</title>
<jsp:include page="../template/library.jsp" />
<script type="text/javascript" src="../js/forms/tools/expirydatechecker.js"></script>
</head>
<body onload="loadexpirydatechecker()">

<jsp:include page="../template/header.jsp" />

<div class="clearfix"></div>
	<div class="breadcrumbs"  style="display: <%= request.getParameter("disp") %>">
		<div class="col-md-4 col-sm-4">
			  <div class="page-header">
			        <p class="cursor-pointer mar-top-10 bread-a-style head-font-size yellow-font" href="stockdashboard"><strong><i class="fa fa-exclamation-circle"></i> Expiry Checker</strong></p>
			  </div>
	    </div>
			            
	    <div class="col-md-8 col-sm-8">
			 <div class="page-header float-right">
			      <a class="pull-right cursor-pointer bread-a-style content-font-size color-font" href="dashboard?uid=1">Home / Dashboard</a>
			 </div>
	    </div>
  </div>

             <div class="clearfix"></div>    <div class="card">	<div class="width_100  ">
				<div class="width_100 ">
					<div class="width_100">
						<div class="width_100 gray-font white-bg content-font-size content-div-height">
							<div class="col-md-12 full-padding-20">
                            <div class="margin-bottom-10 content-font-size  white-font color-bg">
								 <p class="padd-left-right-5"> Expiry Checker </p>
						    </div>
		                
		    <div class="card-body">
				<div class="col-md-12">	
					 <div class="col-md-2" style="width: 87px;">
								<span class="margin-bottom-10 font-17 text-transfrom-upper lite-yellow-font font-weight-bold" >Filter : </span>
						
					</div>
						
		   <div class="col-sm-3">
								
               <select class="form-control form-white selectNor" id="storeselect" onchange="productdetails(this.value)">
				<c:forEach items="${storeList}" var="store" >                  
				<option value="${store.value}"> ${store.label}</option>                    
				</c:forEach>
			    </select>
		  </div>
                                                             
	 <div class="col-md-4 " id="sarsid">
		  <div class="col-md-6 form-group">
		  <input type="radio" id="radio1" value="expiringalready" onclick="checkradio(this.value)" name="expiry"   checked  />Expired already
		  </div>			
																	 
		  <div class="col-md-6 form-group"  >
			   <input type="radio"  id="radio2" onclick="checkradio(this.value)"value="expiringin" name="expiry"> Expiring in 
		  </div>
                                                                     
          <div class="col-md-6 ">
			   <input type="date" id="expireddaytext" class="form-control inpttypestyle" readonly>
		  </div>
                                                                    
		  <div class="col-md-3 ">
			  <button type="button"  class="btn btn-primary"  onclick="onchangefields()" >Search</button>
		  </div>														
	</div>
							
							<div class="col-md-4 ">
								<!--  <div class="col-md-3 "><p>date</p></div> -->
							</div>
		</div>														
																
		
		
		<div class="col-md-12 overcome-col-padd-10">
			 <div class="col-md-12 overcome-col-padd-10">
				  <div class="width_100">
				  
								<!-- <div class="dashboard-ld-header margin-bottom-10 head-font-size white-font color-bg">
									<p><span style="font-size:18px;font-weight:bold"></span> Category </p>
								</div> -->
								<!-- <div class="width_100 gray-font white-bg content-font-size content-div-height mar"> -->
					   
					   <div class="col-md-12 full-padding-20">
									
							<div class="col-md-12" id="expirydateDTdiv">
								 <table id="expirydateDT" class="hero-settings-table"  style="width:100%">
										<thead>
												<th>Product </th>
												<th>Manufacturer</th>
												<th>Batch No</th>
												<th>Expiry Date</th>
												<th>Expiry Count</th>	
										</thead>
								 </table>
							</div>
													  
					  </div>
											<!-- </div> -->
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
	
	<script type="text/javascript">
	    $(document).ready(function(){
		$("#sarsid input[type='radio']").change(function(){
			var status = this.value;
			
			if(status =="expiringalready"){
			  
				$("#inputexpirydays").prop('checked', true); 
				$("#expireddaytext").css("display", "none");
			}
			else{
				 
			 	$("#inputexpirydays").prop('readonly', false);
			
				
			}
		});
	});

	</script>
	<style>
	.col-md-2{
	width:70px;
	}
	.form-group {
    margin-bottom: 15px;
    margin-top: 13px;
}
.mar{
		          margin-top:60px;}
		          .card{
		          margin-left:20px;
		          margin-right:20px;
		          }
	
	.lite-yellow-font{
	color:#edac00;}
	input[type=checkbox], input[type=radio] {
    margin: 4px 5px 0;
    margin-top: 1px\9;
    line-height: normal;
}
	</style>
	<jsp:include page="../template/footer.jsp" />
</body>
</html>