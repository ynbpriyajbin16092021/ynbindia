<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Stock Transfer History</title>
<%if(request.getParameter("disp") == null){ %>
<jsp:include page="../../template/library.jsp" /> 
<%} %>	
<script type="text/javascript" src="../js/forms/transactions/stock.js"></script>
<link rel="stylesheet" href="../../heroadmin/resources/css/stylebass.css">
<link rel="stylesheet" href="../../heroadmin/resources/css/stylej.css">
</head>
<body onload="loadstocktransferhistoryview();">

<%if(request.getParameter("disp") == null){ %>
  	<jsp:include page="../../template/header.jsp" />
<%} %>	

					<div class="clearfix"></div>
					<div class="breadcrumbs"  style="display: <%= request.getParameter("disp") %>">
			            <div class="col-md-4 col-sm-4">
			                <div class="page-header">
			                        <p class="cursor-pointer mar-top-10 bread-a-style head-font-size yellow-font" href="stockdashboard"><strong>Stock Transfer View</strong></p>
			                </div>
			            </div>
			            <div class="col-md-8 col-sm-8">
			                <div class="page-header float-right">
			                    <a class="pull-right cursor-pointer bread-a-style content-font-size color-font" href="dashboard?uid=1">Home / Dashboard</a>
			                </div>
			            </div>
			        </div>
					<div class="clearfix"></div>
					
				<div class="col-md-12 overcome-col-padd-10">
						<div class="col-md-12 overcome-col-padd-10">
							<div class="width_100">
								<!-- <div class="dashboard-ld-header margin-bottom-10 head-font-size white-font color-bg">
									<p><span style="font-size:18px;font-weight:bold"></span> Category </p>
								</div> -->
								<div class="width_100 gray-font white-bg content-font-size">
									<div class="col-md-12 full-padding-20">
									  
									 <div class="width_25 new-stock-style" id="style-5" >
									 
								<%-- 	 <% if(session.getAttribute("usertypestr").equals("10")) { %>
									 <div class="col-md-12" >
							          <a href="stocktransfer?stocktransferid=0" class="button-bg button-space pull-right mar-bot-15">New <i class="fa fa-plus-circle"></i></a>
									 </div>
									 <% } %> --%>
									 <input type="hidden" id="selectedtxnidtext" value="<%=request.getParameter("tid") %>">
           							<input type="hidden" id="stocklistsizetext" value="${stocklistsize}">
           							<input type="hidden" id="firststockno" value="${firststockno }">
									 <div class="col-md-12" style="overflow-y: auto; height:500px;padding-top:10px;">
									<table id="txrtable" class="table table-striped table-bordered dt-responsive nowrap example" style="width:100%">
									   <tbody>
				                            <c:forEach items="${stockTransferList}" var="stocktransfer" >
				                              <tr> 
				                                <td width="50px"> <input type="radio" name="stockchk" id="stockchk${stocktransfer.index }" 
				                                onclick="selecttransferno(${stocktransfer.transferid},${stocktransfer.index},'${stocktransfer.storename}',
				                                '${stocktransfer.city}','${stocktransfer.state}','${stocktransfer.country}','${stocktransfer.zipcode}');"> </td> 
				                                <td id="stocktxrtd${stocktransfer.index }"> ${stocktransfer.transferno } 
				                                <input type="hidden" value="${stocktransfer.transferid } " id="txridtext${stocktransfer.index }">
				                                <input type="hidden" value="${stocktransfer.address } " id="txraddresstext${stocktransfer.index }">
				                                 </td>
				                                 <td align="right">${stocktransfer.status_desc }</td>
				                              </tr>
				                            </c:forEach>        
				                            </tbody>   
										
								    </table>
								      <div class="paginationView" align="right">
                              <span class="tabelCount pull-left">Total Count : ${stockpagelistsize } </span> 
                              <span class=" pull-right"> 
                                <!-- <select class="selectNor ">
                                    <option>10 per page</option>
                                    <option>20 per page</option>
                                    <option>30 per page</option>
                                    <option>50 per page</option>
                                </select> -->
                                
                                
                                <select class="selectNor " onchange="gettxndetails(this.value)">
                                <c:forEach var="page" begin="1" end="${((stockpagelistsize % 10) == 0) ? (stockpagelistsize / 10) : (stockpagelistsize / 10) + 1 }">
                                    <option value="${page }">Page${page }</option>
                                </c:forEach>     
                                </select>
                                
                              </span>
                            </div>
         
									</div>
									</div>
									 <div class="stock-tab-style gray-font white-bg  content-div-heightnw" id="style-5">
									    <div class="width_100" >
										<div class="col-md-4" >
										<p><b>STOCK MOVE TITLE</b></p>
										</div>
										 <div class="col-sm-8">
					                        <div class="pull-right margin-bottom-12">
						                       <!--   <a class="button-bg button-space mar-bot-15" type="button" onclick="generatereport()"><i class="fa fa-file-pdf-o" aria-hidden="true"></i></a> -->
						                        <a href="stocktransferhistory" class="button-bg button-space mar-bot-15">Go Back</a>
											</div>
					                      </div>
										
										</div>
									
									<div class="width_100" >
									<ul class="nav nav-tabs">
									<li class="active"><a class="tabtop" data-target="#stocktransfer" data-toggle="tab" >Overview</a></li>
									</ul>
									</div>
									
									<div class="tab-content">
											<div id="stocktransfer" class="tab-pane active pill-space">
									
									
									 <table id="stockHeaderDT" class="table hero-settings-table dataTable no-footer table-striped table-bordered  nowrap example1">
									  <thead>
									    <tr>
										
										<th>TXN.NO#</th>
										<th>TRANSFERED ON</th>
										<th>STORE NAME#</th>
										</tr>
									  </thead>
										
								    </table>
									<div class="main-template style-txt-table">
									   <div class="main-template-header main-header-content" id="header">
									    <span id="tmp_entity_number" style="font-size: 13pt;float:right;" class="pcs-label" ><b id="stocknodisptext"> # TX-00010</b></span>
									    </div>
										<div class="main-template-body">
										
                           <!--     <table style="width:100%;table-layout: fixed;">
                                 <tbody>
                                   <tr>
                                     <td style="vertical-align: top; width:50%;">
                                       <div>
                                              <img style="width: auto; height: auto; max-width: 300px; max-height: 100px;" id="logo_content" src="/heroadmin/forms/HeroImageView?index=0">
                                              
                                      </div>
									  </td>
                                     <td style="vertical-align: top; text-align:right;width:50%;">
                                       <span class="akmain-entity-title">Stock Transfer ORDER</span><br>
                                       <span id="tmp_entity_number" style="font-size: 10pt;" class="pcs-label" ><b id="stocknodisptext"> # TX-00010</b></span>
                                       </div>
                                    </td>
                                   </tr>
                                 </tbody>
                                </table> -->
								<%--     <table style="width:100%;margin-top:30px;table-layout:fixed;">
                                <tbody><tr>
                                <td style="width:55%;vertical-align:bottom;word-wrap: break-word;">
                               <div>
						        <label >Store Details</label>
                                      <br>
                                      <span class="pcs-customer-name" id="zb-pdf-customer-detail">
                                      <a class="stdtl">
                                       <label id="storenametext">${txrstorename }</label>  </a></span>
                                    
                                
                                  </div>          
                                        <label style="font-size: 10pt;" id="tmp_shipping_address_label" class="pcs-label">
                                       
                                       </label>
                                      
                                     <span style="white-space: pre-wrap;" id="tmp_shipping_address">${address }</span><br>
                          			 <span style="white-space: pre-wrap;" id="tmp_shipping_city">${city }</span><br>
                          			 <span style="white-space: pre-wrap;" id="tmp_shipping_state">${state } - ${zipcode }</span><br>
                          			 <span style="white-space: pre-wrap;" id="tmp_shipping_zipcode">${zipcode }</span><br>
                          			 <span style="white-space: pre-wrap;" id="tmp_shipping_country">${country }</span><br>
									 <span style="white-space: pre-wrap;" id="tmp_shipping_country">Ph: ${phone }</span><br>
                                </td>

                                <td style="vertical-align:bottom;width: 45%;" align="right">
                                  <table style="float:right;width: 100%;table-layout: fixed;word-wrap: break-word;" cellspacing="0" cellpadding="0" border="0">
                                           <tbody>
                                               <tr>
                                                   <td style="text-align:right;padding:5px 10px 5px 0px;font-size:10pt;width:60%;">
                                                      <span class="pcs-label">Date :</span>
                                                  </td>
                                                  <td style="text-align:right;width:40%;">
                                                      <span id="tmp_entity_date"></span>
                                                  </td>
                                              </tr>

                                              <tr>
                                                   <td style="text-align:right;padding:5px 10px 5px 0px;font-size: 10pt;width:60%;">
                                                      <span class="pcs-label" >Transfer Date :</span>
                                                  </td>
                                                  <td style="text-align:right;width:40%;">
                                                      <span id="tmp_due_date"></span>
                                                  </td>
                                              </tr>
                                              


                                           </tbody>
                                        </table>
                                </td>
                                </tr>
                               </tbody></table> --%>


								
								
								
                                    
									
									
								 <table id="stockProdDT" class="hero-settings-table dataTable no-footer table 
									 table-striped table-bordered dt-responsive nowrap example2 display" style="width:100%">
									
									  
									   <thead>
                 
                                  
                                  <tr >
                                      <td>SNo. </td>
                                      <td>ProductName </td>    
                                      <td>Dish Name </td>              
                                      <td> Company Name </td>                                      
                                      <td>Total Qty </td>
                                     
                                   </tr>
                          
                               </thead>
                                <tbody>
                                
                                 </tbody>
								    </table>

										</div>
									    </div>
									    
									    </div>
									    </div>
									    
									 </div>
									
                                </div>
                          </div>

                        </div>
                    </div> <!-- .card -->
                    
                    <script>
                    		
                   
                    
                    </script>  
<style>
.content-div-heightnw {
    height: 530px;
    }
    .margin{
    margin-left:100px;
    }
   
    tr.group,tr.group:hover {
    background-color: #ddd !important;
    color:brown;
    font-weight:100;
}

    tr.group1,tr.group1:hover {
    background-color: #ddd !important;
    color:blue;
}

tr.odd td:first-child,tr.even td:first-child {
    padding-left: 4em;
   
}


</style>
					
					  <!-- Main content ends here -->
						
<jsp:include page="../../template/footer.jsp" />
</body>
</html>			
			
    