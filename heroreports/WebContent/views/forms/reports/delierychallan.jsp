<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="../../heroadmin/resources/css/hero-theme.css" type="text/css" media="print"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
 
    <title>Delivery challan</title>
  <jsp:include page="../template/library.jsp" />
</head>
<body onload="loadreport()"> 
<script type="text/javascript" src="../js/forms/reports/inventoryreports.js"></script>
<jsp:include page="../template/header.jsp" />
<div class="clearfix"></div>

					<div class="breadcrumbs"  style="display: <%= request.getParameter("disp") %>">
			            <div class="col-md-4 col-sm-4">
			                <div class="page-header">
			                        <div class="lowstockspan yellow-font"><strong class="card-title"><i class="fa fa-shopping-cart"></i>  Delivery challan  <span class="notification-delpo-page">${notification_count}<span></span></span></strong></div>
			                </div>
			            </div>
			            <div class="col-md-8 col-sm-8">
			                <div class="page-header float-right">
			                    <a class="pull-right cursor-pointer bread-a-style content-font-size color-font" href="dashboard?uid=1">Home / Dashboard</a>
			                </div>
			            </div>
			        </div>
<div class="clearfix"></div>
<div class="width_100 white-bg padding-container-10" style="height:216px;">
<div class="col-md-12 overcome-col-padd-10  ">
<div class="col-md-4 form-group">
										<label for="Date"> Date</label>
										<input placeholder=""  autocomplete="off" class="form-control form-white datepicker" type="text" id="purchasedatetext"> 
									</div>
	<div class="col-md-6 form-group control-label" >
								<label>Customer Name</label>
									<select	id='customeridselect' class="form-control form-white" onchange="changecxustomerselect()">
										<option value="0">-- Select --</option>
										<c:forEach items="${customerList}" var="customer">
											<option value="${customer.value}">${customer.label}</option>
										</c:forEach>
									</select>
									
  								</div>	  
          
									<div class="cust-txt">
									<button onclick="print()"><i class="fa fa-print" aria-hidden="true"></i></button>
									</div>
									<div class="col-md-12">
									<p id="response"></p>
									</div>
						<div class="col-md-12 overcome-col-padd-10 ">
							<div class="width_100">				
			  <%--  <div class="col-md-6 form-group control-label" >
								<label>Customer Name</label>
									<select	id='customeridselect' class="form-control form-white" onchange="changecxustomerselect()">
										<option value="0">-- Select --</option>
										<c:forEach items="${customerList}" var="customer">
											<option value="${customer.value}">${customer.label}</option>
										</c:forEach>
									</select>
									<button onclick="print()">print</button>
  								</div>	 --%>  
<!--   <h2>Customer</h2> -->
  
  <!-- <deliver header start> -->
  <div id="challan-hdr-top"  style="display:none;">
  
 <div class="col-md-12 delivery-top delivery-header-top-sec" style="
  
    border: 1px solid #ddd;
    padding: 0px;
   
    text-align: center;"
>
  <span>DELIVERY CHELLAN </span>
  </div>
  <div class="col-md-12 new-style-challan" style=" border: 1px solid #ddd;">
  <div class="col-md-9 ynb-span">
  <div style="padding:5px;">
  <img src=<%= session.getAttribute("applicationcontextforprint") %> class="img-logo" style="width:7%;">
  <span style="
    font-size: 20px;
    font-weight: bold;">YNB Catering Services</span>
    <div class="ynv-h3">
          <span id="address">REGISTERD OFFICE</span>
         
    </div>
  </div>
  </div>
   <div class="col-md-3 right-chellan" style="text-align:left;border-left: 1px solid #ddd;
           padding:0;">
         <div>
 <!--  <div class="column">
  <div class="col-md-12" style="border-bottom: 1px solid #ddd !important;
   
    padding-left: 10px ;
    padding-bottom: 10px;
    text-align: center;" >
  <p  class="orginal" >ORGINAL</p>
    </div>
  <p  class="orginal"style="border-bottom: 1px solid #ddd;
    width: 100% ;
    padding-left: 10px ;
    padding-bottom: 10px;">ORDER NO:</p>
  <p  class="orginal" style="border-bottom: 1px solid #ddd;
    width: 230px ;
    padding-left: 10px ;
    padding-bottom: 10px">DC NO:</p>
  <p style ="padding-left: 10px ;" >DC DATE:</p>
  </div> -->
  <div class="col-md-12" style="border-bottom: 1px solid #ddd !important;text-align: center;padding: 4px;" >

    <span  class="orginal" style="font-weight:900px">ORGINAL</span>
 
  </div>
   <div class="col-md-12" style="border-bottom: 1px solid #ddd !important;padding:4px" >
  <div class="col-md-6">
    <span  class="orginal" >ORDER NO:</span>
  </div>
  <div class="col-md-6">
   <span  class="orginal" id="refno"></span>
  </div>
  </div>
   <div class="col-md-12" style="border-bottom: 1px solid #ddd !important;padding:4px" >
  <div class="col-md-6">
    <span  class="orginal" >DC NO:</span>
  </div>
  <div class="col-md-6">
   <span  class="orginal" id="refno"></span>
  </div>
  </div>
     <div class="col-md-12" style="padding:4px" >
  <div class="col-md-6">
    <span  class="orginal" >DC DATE:</span>
  </div>
  <div class="col-md-6">
   <span  class="orginal" id="date"></span>
  </div>
  </div>
  </div>
  </div>
  
  </div>
 <div class="col-md-12 cust-text-challan" style=" border: 1px solid #ddd;height:30px;padding-top:10px;font-weight:bold;
   
    ">
 <span  style="font-size:15px,font-weight:bold;margin:10px;">Customer : seoyonm 1</span>
 </div>
  
   <!-- <deliver header end> -->
  
 <!--  <p>The .table-bordered class adds borders on all sides of the table and the cells:</p>  -->  
 <div id="section-to-print" class="col-md-6 table-section" style="margin:0px;width:100%;">   
       <div class="col-md-6">
  <table id="deliverychallanfirst" class="table table-bordered">
    <thead>
      <tr>
        <th>S.No</th>
        <th>Item Description</th>
        <th>Strength</th>
       
      </tr>
    </thead>
    <tbody>
     
      
    </tbody>
  </table>
  </div>
         <div class="col-md-6">
  <table id="deliverychallansecond" class="table table-bordered">
    <thead>
      <tr>
        <th>S.No</th>
        <th>Item Description</th>
        <th>Strength</th>
       
      </tr>
    </thead>
    <tbody>
     
      
    </tbody>
  </table>
  </div>
  
  </div>
   <div class="col-md-12 cust-text-challan" style=" border: 1px solid #ddd;height:25px;
   
   padding-top: 10px ">
    <div class="col-md-6">
 <span  style="font-size:15px;font-weight:bold;margin:10px;">FOOD DESPATCH SECTION</span>
 </div>
 <div class="col-md-6">
 <span  style="font-size:15px;font-weight:bold;margin:10px;">SECURITY ENTRY :  OUTWARD</span>
 </div>
 </div>
 <div class="col-md-12 cust-text-challan" style=" border: 1px solid #ddd;height:45px
   
    ">
    </div>
      <div class="col-md-12 cust-text-challan" style=" border: 1px solid #ddd;height:25px;padding-top: 10px;
   
    ">
    <div class="col-md-6">
 <span  style="font-size:15px;font-weight:bold;margin:10px;">CUSTOMER SIGN AND SEAL</span>
 </div>
 <div class="col-md-6">
 <span  style="font-size:15px;font-weight:bold;margin:5px 10px;"></span>
 </div>
 </div>
 <div class="col-md-12 cust-text-challan" style=" border: 1px solid #ddd;height:45px
   
    ">
    </div>
	</div>
	</div>
	</div>				
	</div>			
	  </div>							
<style>
		.width_100 h2
		{
		padding-bottom:25px;
		}
		.column {float: left; width: 50%;}
		 @page { 
        size: portrait;
    }
		</style>		
 
<script>
function print(){
 	//var w = window.open('', 'printform', 'width=1500,height=1500'); 
 	var w = window.open('', '_blank');
 	var div = document.getElementById("challan-hdr-top");
 	div.style.display = "block";  
	 var html = $("#challan-hdr-top").html(); 
	 div.style.display = "none";  
	 $(w.document.body).html(html+'<style>' +
			
		        'table {border: 1px solid #ddd;border-spacing: 0; border-collapse: collapse;width: 100%;' +
	' max-width: 100%}  ' +
			   'tr{border-bottom-width: 2px;border: 1px solid #ddd;}td,th{padding:5px; border-right: solid 1px #ddd; ' +
  'border-left: solid 1px #ddd;}'+
	' .column {float: left; width: 48%;}' + 
	'.col-md-1 {width:8%; ;}'+
	' .col-md-2 {width:16%;}' +
	'.col-md-3 {width:25%;float:left !important }' +
	'.col-md-4 {width:33%;}' +
	'.col-md-5 {width:42%; }' +
	'.col-md-6 {width:50%;float:left !important}' +
	'.col-md-7 {width:58%; }' +
	'.col-md-8 {width:66%; }' +
	'.col-md-9 {width:74%;float:left !important }' +
	'.col-md-10{width:83%; }' +
	'.col-md-11{width:92%; }' +
	'.col-md-12{width:100%;float:left !important  }' +
	 
		        '}'+
		       
		        '</style>');
	 setTimeout(function(){w.print()}, 1500);
    } 
  /*   var w = window.open('', 'printform', 'width=600,height=600');  
    var html = $("#section-to-print").html(); 
    $(w.document.body).html(html+"<style>thead{background:black;color:white;font-size:20px;}table{width:1200;border-collapse: separate;border-spacing: 0px 10px;}tr{border-spacing: 0px 20px;height:45px;}h5{text-align:center;}h2{text-align:center;};</style>");  */
   
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