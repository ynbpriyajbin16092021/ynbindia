<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Invoice History</title>
<%if(request.getParameter("disp") == null){ %>
<jsp:include page="../../forms/template/library.jsp" /> 
<%} %>
    <script type="text/javascript" src="../js/forms/sales/pos.js"></script>
</head>
<body onload="loadposhistory()">

<%if(request.getParameter("disp") == null){ %>
<jsp:include page="../../forms/template/header.jsp" />
<%} %>	


<div class="clearfix"></div>
					<div class="breadcrumbs"  style="display: <%= request.getParameter("disp") %>">
			            <div class="col-md-4 col-sm-4">
			                <div class="page-header">
			                        <p class="cursor-pointer bread-a-style content-font-size color-font" href="stockdashboard">order History</p>
			                </div>
			            </div>
			            <div class="col-md-8 col-sm-8">
			                <div class="page-header float-right">
			                    <a class="pull-right cursor-pointer bread-a-style content-font-size color-font" href="dashboard?uid=1">Home / Dashboard</a>
			                </div>
			            </div>
			        </div>
<div class="clearfix"></div>



		<div id="users" class="col-xs-12">
			<div class="filter-group row">
				<div class=" col-xs-12 col-sm-12 col-md-4 pull-right">
					<input type="text" class="search form-control" placeholder="Search" />
				</div>
				
				
			</div>
			<ul class="list">
				
				<c:choose>
					<c:when test="${invListsize == 0}">
						<li>
						
						<div class="col-md-3 easypaginatediv overcome-col-padd-10">
											<div class="create-new-app color-bg white-font box-shadow-theme">
												<div class="create-new-app-content">
													<div class="create-new-app-text text-center white-font">
														<p><b>NO RECORDS FOUND</b></p>
													</div>
												</div>
												<div class="width_100">
													<ul class="white-font head-font-size text-center">
														
													</ul>
												</div>
											</div>
							</div>
						
						</li>
					</c:when>    
				<c:otherwise>
					<c:forEach items="${invList}" var="inv" varStatus="i">
							<c:set var="posordersavail" value="${invList[i.index].posordersavail}"/>
							<c:set var="posreturnsalescode" value="${invList[i.index].posreturnsalescode}"/>
							<c:set var="posstatusdesc" value="${invList[i.index].statusdesc}"/>
							
							<li class="list--list-item" >
							 <div class="col-md-3 easypaginatediv overcome-col-padd-10">
								<div class="trnx-content-section margin-right-10px color-border--top-7px box-shadow-theme">
									<div class="trnx-padd">
										<div class="trnx-header white-bg color-font head-font-size poscodetext">
											<span class="invoice_name"><i class="fa fa-credit-card small-font-size"></i> ${inv.poscode} </span>
										</div>
										<div class="trnx-content">
											<span class="main-head-font-size color-font white-bg"><strong><i class="fa fa-money head-font-size yellow-font"></i>${inv.customername}</strong></span>
											<ul class="gray-font content-font-size nrml-ul">
												<li class="invoice_details">storename- ${inv.storename }</br>Total-${inv.htmlcode}${inv.totalamount}</br>status-${inv.statusdesc}</br>Due-${inv.htmlcode}${inv.balanceamount} </li>
											</ul>
											
										</div>
										<div class="trnx-footer">
											<p class="gray-font white-bg content-font-size">Created on <span class="pull-right invoice_created_on">${inv.salesat}</span>
											
										</div>
										
										 
									</div>
									<div class="width_100 gray-bg light-gray-border-top">
										<ul class="gray-font head-font-size nrml-ul pull-right trnx-bottom-links">
											<c:choose>
											    <c:when test="${posordersavail > 0}">
											    	<li><a onclick="generateinvoicereport('${inv.posid}')"><i class="fa fa-file-pdf-o"></i></a></li>
											    </c:when>
											    <c:otherwise>
											    	<c:choose>
													    			<c:when test="${posstatusdesc == 'Cancelled'}">
													    				<li><a onclick="generateinvoicereport('${inv.posid}')"><i class="fa fa-file-pdf-o"></i></a></li>
													    			</c:when>    
													    			<c:otherwise>
														    			<c:choose>
																		    <c:when test="${posreturnsalescode == null}">
																		    	<li><a href="pos?posid=${inv.posid}&type=${inv.salestype}"><i class="fa fa-edit"></i></a></li>
																		    	<li><a data-toggle="modal" data-target="#modal-delet" onclick="deleteposHistory('${inv.posid}')"><i class="fa fa-times"></i></a></li>
																		    	<li><a onclick="generateinvoicereport('${inv.posid}')"><i class="fa fa-file-pdf-o"></i></a></li>
																		    </c:when>    
																		    <c:otherwise>
																    			<li><a onclick="generateinvoicereport('${inv.posid}')"><i class="fa fa-file-pdf-o"></i></a></li>
																		    </c:otherwise>
																	    </c:choose>
													    			</c:otherwise>
													    		</c:choose>
											    
											    	
											    </c:otherwise>
											</c:choose>
										</ul>
									</div> 
								</div>
							</div> 
							 </li>
                        </c:forEach>
				</c:otherwise>
				</c:choose>
			</ul>
			<div class="no-result">
			<div class="col-md-3 easypaginatediv overcome-col-padd-10">
								<div class="create-new-app color-bg white-font box-shadow-theme">
									<div class="create-new-app-content">
										<div class="create-new-app-text text-center white-font">
											<p><b>NO RESULTS FOUND</b></p>
										</div>
									</div>
									<div class="width_100">
										<ul class="white-font head-font-size text-center">
											
										</ul>
									</div>
								</div>
				</div>
			</div>
			<div class="col-md-12">
			<ul class="pagination"></ul>
			</div>
			
		</div>

<script>
var options = {
		valueNames: [
			'invoice_name',
			'invoice_created_on',
			'invoice_details'
		],
		page: 8,
		pagination: true
	};
	var userList = new List('users', options);

	function resetList(){
		userList.search();
		userList.filter();
		userList.update();
		$(".filter-all").prop('checked', true);
		$('.filter').prop('checked', false);
		$('.search').val('');
		//console.log('Reset Successfully!');
	};
	  
	
	                               
	$(function(){
	
		userList.on('updated', function (list) {
			if (list.matchingItems.length > 0) {
				$('.no-result').hide()
			} else {
				$('.no-result').show()
			}
		});
	});
</script>








































         	<div class="col-md-12">
						<%-- <div class="trnx-content-sectiontrnx-section" id="easyPaginate">
						 	
							<c:forEach items="${invList}" var="inv" varStatus="i">
							<c:set var="posordersavail" value="${invList[i.index].posordersavail}"/>
							<c:set var="posreturnsalescode" value="${invList[i.index].posreturnsalescode}"/>
							 <div class="col-md-3 easypaginatediv overcome-col-padd-10">
								<div class="trnx-content-section margin-right-10px color-border--top-7px box-shadow-theme">
									<div class="trnx-padd">
										<div class="trnx-header white-bg color-font head-font-size poscodetext">
											<span class="invoice_name"><i class="fa fa-credit-card small-font-size"></i> ${inv.poscode} </span>
										</div>
										<div class="trnx-content">
											<span class="main-head-font-size color-font white-bg"><strong><i class="fa fa-money head-font-size yellow-font"></i>${inv.customername}</strong></span>
											<ul class="gray-font content-font-size nrml-ul">
												<li class="invoice_details">storename- ${inv.storename }</br>Total-${inv.htmlcode}${inv.totalamount}</br>status-${inv.statusdesc}</br>Due-${inv.htmlcode}${inv.balanceamount} </li>
											</ul>
											
										</div>
										<div class="trnx-footer">
											<p class="gray-font white-bg content-font-size">Created on <span class="pull-right invoice_created_on">${inv.salesat}</span>
											
										</div>
										
										 
									</div>
									<div class="width_100 gray-bg light-gray-border-top">
										<ul class="gray-font head-font-size nrml-ul pull-right trnx-bottom-links">
											<c:choose>
											    <c:when test="${posordersavail > 0}">
											    	<li><a onclick="generateinvoicereport('${inv.posid}')"><i class="fa fa-file-pdf-o"></i></a></li>
											    </c:when>
											    <c:otherwise>
											    	<c:choose>
													    <c:when test="${posreturnsalescode == null}">
													    	<li><a href="pos?posid=${inv.posid}&type=${inv.salestype}"><i class="fa fa-edit"></i></a></li>
													    	<li><a data-toggle="modal" data-target="#modal-delet" onclick="deleteposHistory('${inv.posid}')"><i class="fa fa-trash"></i></a></li>
													    	<li><a onclick="generateinvoicereport('${inv.posid}')"><i class="fa fa-file-pdf-o"></i></a></li>
													    </c:when>    
													    <c:otherwise>
													       <li><a onclick="generateinvoicereport('${inv.posid}')"><i class="fa fa-file-pdf-o"></i></a></li>
													    </c:otherwise>
												    </c:choose>
											    </c:otherwise>
											</c:choose>
										</ul>
									</div> 
								</div>
							</div>  
                        </c:forEach>
							
							

							
						</div> --%>
					</div>
					<div class="clearfix"></div>
					 	
							
					
					<!-- <style>
						#easyPaginate {width:100%;}
						.easyPaginateNav a {padding:5px;}
						.easyPaginateNav a.current {font-weight:bold;text-decoration:underline;}
					</style>

					<script>
						$('#easyPaginate').easyPaginate({
						    paginateElement: 'div.easypaginatediv',
						    elementsPerPage: 8,
						    effect: 'climb'
						});
					</script>
 -->


				  <!-- Main content starts here -->
				       
					
					
<jsp:include page="../template/footer.jsp" />					
	

</body>
</html>