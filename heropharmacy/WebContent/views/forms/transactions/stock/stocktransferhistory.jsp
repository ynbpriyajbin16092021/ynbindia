<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
   pageEncoding="ISO-8859-1"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
      <title>Stock Transfer History</title>
      <%if(request.getParameter("disp") == null){ %>
      <jsp:include page="../../template/library.jsp" />
      <%} %>	
      <script type="text/javascript" src="../js/forms/transactions/stock.js"></script>
   </head>
   <body onload="loadstocktxrhistory();">
      <style>
         a.mar-bottom-10{
         margin-bottom:10px;
         }
      </style>
      <%if(request.getParameter("disp") == null){ %>
      <jsp:include page="../../template/header.jsp" />
      <%} %>	
      <div class="clearfix"></div>
      <div class="breadcrumbs"  style="display: <%= request.getParameter("disp") %>">
         <div class="col-md-4 col-sm-4">
            <div class="page-header">
               <a class="cursor-pointer bread-a-style content-font-size color-font" >Stock Transfer History</a>
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
         <div class="width_100 gray-font white-bg content-font-size content-div-height">
            <div class="col-md-12 full-padding-20">
               <!-- <div class="filter-group row">
                  <div class=" col-xs-12 col-sm-12 col-md-4 pull-right">
                  	<input type="text" class="search form-control" placeholder="Search" />
                  </div>
                  
                  
                  </div> 
                  <ul class="list">-->
               <div class="col-md-12">
                  <div class="trnx-content-sectiontrnx-section" id="easyPaginate">
                     <!-- <div class="col-md-3 easypaginatediv overcome-col-padd-10">
                        <div class="create-new-app color-bg white-font box-shadow-theme">
                        	<div class="create-new-app-content">
                        		<div class="create-new-app-text text-center white-font">
                        			<p><a class="white-font" href="stocktransfer?stocktransferid=0">
                        			<i class="fa fa-plus-circle app-font-size"></i><br />CREATE</a></p>
                        		</div>
                        	</div>
                        	<div class="width_100">
                        		<ul class="white-font head-font-size text-center">
                        			
                        		</ul>
                        	</div>
                        </div>
                        </div>	 -->
                     <% if(!session.getAttribute("usertypestr").equals("10")) { %>
                     <div class="width_100">
                        <a class="button-bg button-space pull-right mar-bot-15 mar" href="transferProducts?stocktransferid=0">New Request<i class="fa fa-plus-circle" style="padding-left:5px;"
                        ></i></a>
                     </div>
                     <% } %> 
                     <div class="row col-md-12">
                        <table  class="hero-settings-table"  style="width:100%" id="stocktransferDT" >
                           <thead>
                              <tr >
                                 <th> # </th>
                                 <th> Goods Receive No </th>
                                 <th> Order Ref.no </th>
                                 <th> Total Items </th>
                                 <th> Total Amount </th>
                                 <th> Date </th>
                                 <th> Status </th>
                                 <th></th>
                                 <th> Action </th>
                              </tr>
                           </thead>
                           <tbody>
                              <c:forEach items="${stList}" var="stock" varStatus="i" >
                                 <c:set var="deliveryStatus" value="${stList[i.index].deliverystatus}"/>
                                 <tr>
                                    <td>${stock.index }</td>
                                    <td class="color-font">${stock.stocktransfernavigate}</td>
                                    <td>${stock.orderRefNo }</td>
                                    <td>${stock.productcount }</td>
                                    <td>${stock.gcshtmlcode} ${stock.amount} </td>
                                    <td>${stock.transferdate }</td>
                                    <td>
                                       <c:choose>
                                          <c:when test="${deliveryStatus == 1 }">
                                             <% if(session.getAttribute("usertypestr").equals("10")) { %>
                                              <c:if test="${stock.order_approved == 1}">
													       	${stock.status }
													       </c:if>
		                                         		
		                                         			 <c:if test="${stock.order_approved == 0}">
													       ${stock.status_desc }
													       </c:if>
                                             <% }else{%>
                                             ${stock.status_desc }
                                             <% } %>
                                          </c:when>
                                          <c:when test="${deliveryStatus == 3 }">
                                             <% if(!session.getAttribute("usertypestr").equals("10")) { %>
                                             ${stock.status }
                                             <% }else{%>
                                             ${stock.status_desc }
                                             <% } %>
                                          </c:when>
                                             <c:when test="${stock.statusid == 2 }">
                                            Delivered
                                              </c:when>
                                          <c:otherwise>
                                                ${stock.status_desc }
                                               
                                          </c:otherwise>
                                       </c:choose>
                                    </td>
                                    <td>  <% if(session.getAttribute("usertypestr").equals("3")) { %>
                                    <%--  <c:if test="${stock.sub_sequent == 0 }">
                                  <button class="button-bg button-space pull-right mar-bot-15 mar" onclick="addmorerequest(${stock.transferid })">Add More <i class="fa fa-plus-circle"></i></button>  
                                   </c:if> --%>
                                      <c:if test="${deliveryStatus == 2 && stock.statusid == 1}">
                                    <button class="button-bg button-space pull-right mar-bot-15 mar ready-deliver-btn
                                    " onclick="readytodeliver(${stock.transferid })">Ready to deliver</button> 
                                   </c:if>
                                    <% } %></td>
                                    <td>
                                       <ul class="gray-font head-font-size nrml-ul pull-right trnx-bottom-links">
                                          <c:if test="${deliveryStatus == 1 }">
                                             <% if(session.getAttribute("usertypestr").equals("10")) { %>
                                             <c:if test="${stock.order_approved == 1 }">
                                             <li><a title="Save Status" data-toggle="tooltip"><i onclick="saverecvstatus('${stock.index }','${stock.transferid }')" class="fa fa-hdd-o"></i></a></li>
                                              </c:if>
                                             <% } %>
                                             <li><a><i data-toggle="modal" data-target="#modal-delet" onclick="deleteStockTransferHistory('${stock.transferdate}','${stock.transferid }','${stock.orderid }')" class="fa fa-trash-o"></i></a></li>
                                          </c:if>
                                          <c:if test="${deliveryStatus == 3 }">
                                             <% if(!session.getAttribute("usertypestr").equals("10")) { %>
                                              <c:if test="${stock.order_approved == 1 }">
                                             <li><a title="Save Status" data-toggle="tooltip"><i onclick="saverecvstatus('${stock.index }','${stock.transferid }')" class="fa fa-hdd-o"></i></a></li>
                                             </c:if>
                                             <% } %>
                                             <li><a><i onclick="deleteStockTransferHistory('${stock.transferdate}','${stock.transferid }','${stock.orderid }')" class="fa fa-trash-o"></i></a></li>
                                          </c:if>
                                       </ul>
                                    </td>
                                 </tr>
                              </c:forEach>
                           </tbody>
                        </table>
                     </div>
                     <%-- <c:forEach items="${stList}" var="stock" varStatus="i">
                        <c:set var="deliveryStatus" value="${stList[i.index].deliverystatus}"/>
                        <li class="list--list-item" >
                        <div class="col-md-3 easypaginatediv overcome-col-padd-10">
                        	<div class="trnx-content-section margin-right-10px color-border--top-7px box-shadow-theme">
                        		<div class="trnx-padd">
                        			<div class="trnx-header white-bg color-font head-font-size">
                        				<span class="invoice_name"><i class="fa fa-credit-card small-font-size"></i> ${stock.stocktransfernavigate} </span>
                        			</div>
                        			<div class="trnx-content">
                        				<span class="main-head-font-size color-font white-bg invoice_details"><strong><i class="fa fa-money head-font-size yellow-font"></i>  ${stock.storename}</strong></span>
                        				<ul class="gray-font content-font-size nrml-ul">
                        					<li  class="invoice_details">No of Items- ${stock.productcount }</br>Grand Total- ${stock.gcshtmlcode} ${stock.amount} </li>
                        				</ul>
                        				
                        			</div>
                        			<div class="trnx-footer">
                        				<p class="gray-font white-bg content-font-size">Created on <span class="pull-right invoice_created_on">${stock.transferdate}</span>
                        				
                        			</div>
                        			
                        		</div>
                        		<div class="width_100 gray-bg light-gray-border-top">
                        		 <div class="stock-transfer-select">${stock.status }</div>
                        			<ul class="gray-font head-font-size nrml-ul pull-right trnx-bottom-links">
                        				<c:if test="${deliveryStatus == 1 }">
                        					<li><a title="Save Status" data-toggle="tooltip"><i onclick="saverecvstatus('${stock.index }','${stock.transferid }')" class="fa fa-hdd-o"></i></a></li>
                        					<li><a><i data-toggle="modal" data-target="#modal-delet" onclick="deleteStockTransferHistory('${stock.transferdate}','${stock.transferid }')" class="fa fa-trash-o"></i></a></li>
                        				</c:if>
                        				<c:if test="${deliveryStatus == 3 }">
                        					<li><a><i onclick="deleteStockTransferHistory('${stock.transferdate}','${stock.transferid }')" class="fa fa-trash-o"></i></a></li>											
                        				</c:if>
                        			</ul>
                        		</div>
                        	</div>
                        </div> 
                         </li>
                                         </c:forEach> --%>
                     </ul>
                     <!-- <div class="no-result">
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
                        </div> -->
                     <!-- <ul class="pagination"></ul> -->
                  </div>
                  <script type="text/javascript">
                     $(function () {
                         $("[rel='tooltip']").tooltip();
                     });
                  </script>
               </div>
            </div>
         </div>
      </div>
      <div class="clearfix"></div>
      <style>
         #easyPaginate {width:100%;}
         .easyPaginateNav a {padding:5px;}
         .easyPaginateNav a.current {font-weight:bold;text-decoration:underline;}
         .stock-transfer-select{
         width:50%;
         display:block;
         float:left;
         border:none;
         }
         .stock-transfer-select span{
         padding:2px;
         display:block;
         }
         .select-pad{
         padding:3px;
         border:none;
         }
      </style>
      <script>
         $(document).ready(function(){
         	$("#stocktransferDT").dataTable();
         });
      </script>
      <script>
         $('#easyPaginate').easyPaginate({
             paginateElement: 'div.easypaginatediv',
             elementsPerPage: 8,
             effect: 'climb'
         });
      </script>
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
      <jsp:include page="../../template/footer.jsp" />
   </body>
</html>