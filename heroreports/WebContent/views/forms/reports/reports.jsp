<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
 <%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Reports</title>
<jsp:include page="../template/library.jsp" /> 

<script type="text/javascript" src="../../js/forms/masters/masters.js"></script>

</head>
<body> 

<jsp:include page="../template/header.jsp" />


<div class="clearfix"></div>
					<div class="breadcrumbs"  style="display: <%= request.getParameter("disp") %>"> 
			            <div class="col-md-4 col-sm-4">
			                <div class="page-header">
			                        <p class="cursor-pointer mar-top-10 bread-a-style head-font-size yellow-font" href="stockdashboard"><strong> Dashboard </strong></p>
			                </div>
			            </div>
			            <div class="col-md-8 col-sm-8">
			                <div class="page-header float-right">
			                    <a class="pull-right cursor-pointer bread-a-style content-font-size color-font" href="dashboard?uid=1">Home / Dashboard</a>
			                </div>
			            </div>
			        </div> 
					<div class="clearfix"></div>
					
					<div class="col-md-12 overcome-col-padd-10  " style="display:none">
							<div class="width_100 white-bg padding-container-10">

						  <div class="reposrList col-md-4">
							<p class="fontLg">Sales</p>
							<ul class="nav">
							  <li class="ember-view">
								<a href="salesbyproduct?rid=1" class="ember-view">                
								<i class="fa fa-angle-right" aria-hidden="true"></i> Sales by Product </a>
							  </li>
							  <li class="ember-view">
								<a href="salesbycustomer?rid=2" class="ember-view">                
								 <i class="fa fa-angle-right" aria-hidden="true"></i> Sales by Customer </a>
							  </li>
							  <!-- <li class="ember-view">
								<a href="report-view.php" class="ember-view">                
								<i class="fa fa-angle-right" aria-hidden="true"></i> Product Sales Report </a>
							  </li> -->
							  <li class="ember-view">
								<a href="salesbyorder?rid=4" class="ember-view">                
								<i class="fa fa-angle-right" aria-hidden="true"></i> Sales by Order </a>
							  </li>
							  <li class="ember-view">
								<a href="salesfinance?rid=5" class="ember-view">                
								 <i class="fa fa-angle-right" aria-hidden="true"></i> Sales Finance Report </a>
							  </li>
							  <li class="ember-view">
								<a href="salesbymonth?rid=6" class="ember-view">                
								<i class="fa fa-angle-right" aria-hidden="true"></i> Sales by Month </a>
							  </li>
							  <li class="ember-view">
								<a href="salesbypaymenttype?rid=7" class="ember-view">                
								<i class="fa fa-angle-right" aria-hidden="true"></i> Sales by Payment Type </a>
							  </li>
							  <li class="ember-view">
								<a href="salesbytaxtype?rid=8" class="ember-view">                
								<i class="fa fa-angle-right" aria-hidden="true"></i> Sales by Tax Type </a>
							  </li>
							  <li class="ember-view">
								<a href="productorders?rid=9" class="ember-view">                
								<i class="fa fa-angle-right" aria-hidden="true"></i> Orders by Product </a>
							  </li>
							</ul>
						  </div>
	
								  <div class="reposrList col-md-4">
									<p class="fontLg">Purchase Order</p>
									<ul class="nav">
									
									 <li class="ember-view">
										<a href="purchasebilldetails?rid=12" class="ember-view">                
										<i class="fa fa-angle-right" aria-hidden="true"></i> Bill Details </a>
									  </li>
									  <li class="ember-view">
										<a href="purchasereceivehistory?rid=10" class="ember-view">                
										<i class="fa fa-angle-right" aria-hidden="true"></i> Purchase Receive History </a>
									  </li>
									  <li class="ember-view">
										<a href="purchasebyitem?rid=11" class="ember-view">                
										 <i class="fa fa-angle-right" aria-hidden="true"></i> Purchase by Item </a>
									  </li>
									  
									  <li class="ember-view">
										<a href="vendorbalance?rid=13" class="ember-view">                
										<i class="fa fa-angle-right" aria-hidden="true"></i> Vendor Balance </a>
									  </li>
									 
									 
									</ul>
								  </div>
	
							<div class="reposrList col-md-4">
								<p class="fontLg">Inventory</p>
								<ul class="nav">
								  <li class="ember-view">
									<a href="warehousestock?rid=14" class="ember-view">                
									<i class="fa fa-angle-right" aria-hidden="true"></i> Warehouse Stock Report </a>
								  </li>
								  <li class="ember-view">
									<a href="stocktxrreport?rid=15" class="ember-view">                
									 <i class="fa fa-angle-right" aria-hidden="true"></i> Stock Transfer Report </a>
								  </li>
								  <!-- <li class="ember-view">
									<a href="report-view.php" class="ember-view">                
									<i class="fa fa-angle-right" aria-hidden="true"></i> Product Sales Report </a>
								  </li>
								  <li class="ember-view">
									<a href="report-view.php" class="ember-view">                
									<i class="fa fa-angle-right" aria-hidden="true"></i> Product Sales Report </a>
								  </li>
								  <li class="ember-view">
									<a href="report-view.php" class="ember-view">                
									 <i class="fa fa-angle-right" aria-hidden="true"></i> Product Sales Report </a>
								  </li>
								  <li class="ember-view">
									<a href="report-view.php" class="ember-view">                
									<i class="fa fa-angle-right" aria-hidden="true"></i> Product Sales Report </a>
								  </li> -->
							</ul>
			                </div>
			</div>				
		</div>

	<jsp:include page="../template/footer.jsp" />
	</body>
</html>