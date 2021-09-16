<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>View Purchase Request</title>
<jsp:include page="../../template/library.jsp" />
<script type="text/javascript" src="../js/forms/transactions/purchaseorder.js"></script>
<link rel="stylesheet" href="../../heroadmin/resources/css/stylebass.css">
	<link rel="stylesheet" href="../../heroadmin/resources/css/stylej.css">
</head>
<body onload="loadpurchaserequestview();">

<jsp:include page="../../template/header.jsp" />
	
	<div class="clearfix"></div>
	<div class="breadcrumbs"  style="display: <%= request.getParameter("disp") %>">
         <div class="col-md-4 col-sm-4">
			  <div class="page-header">
			       <p class="cursor-pointer mar-top-10 bread-a-style head-font-size yellow-font"
			        href="stockdashboard"><strong>Purchase Request view</strong></p>
			  </div>
		</div>
			            
	 <div class="col-md-8 col-sm-8">
		  <div class="page-header float-right">
			   <a class="pull-right cursor-pointer bread-a-style content-font-size color-font" 
			   href="dashboard?uid=1">Home / Dashboard</a>
		  </div>
	</div>
	</div>
					
	<div class="clearfix"></div>
					
				
				
	<div class="col-md-12 overcome-col-padd-10">
		 <div class="col-md-12 overcome-col-padd-10">
		 <div class="width_100">
		 <div class="width_100 gray-font white-bg content-font-size content-div-height">
		 <div class="col-md-12 full-padding-20">
									
		 <div class="width_25" style="overflow-y:scroll;height:500px;" id="style-5">

									 
		<div class="width_100 scrolly">
							
			<div class="rowNor ">
				 <input type="hidden" value="${firstid }" id="firstidtext">  
				 <input type="hidden" id="totalpurchasetext" value="${viewpurchaseorderList.size() }"></input>
				 <input type="hidden" id="pidtext"></input>           
								            
			<table class="table table-hover selectCheckTabel" id="txrtable">
				   <tbody>
				   
				   <c:forEach items="${viewpurchaseorderList}" var="viewpurchase" > 
								            
		<tr> 
			<td width="20px"> 
								               
					<input name="purchaseorderidcheck" type="radio" onclick="getPurchaseRequestList ('${ viewpurchase.purchaseid}')"> 
					
					<input type="hidden" value="${ viewpurchase.purchasecode}" id="purchasecodetext${viewpurchase.index }">
				
			</td> 
				
			<td onclick="getPurchaseRequestList('${ viewpurchase.purchaseid}')"> 
				
					<div class="leftTableAdd">
						 <a> ${ viewpurchase.purchasecode} </a> 
					<%-- 	 ${ viewpurchase.purchasedate}   --%>
						 <span class="text-success pull-right">${ viewpurchase.purchasestatus}</span>
				    </div>
								                  
					<%-- <input type="hidden" id="headerid${viewpurchase.index }" value="${ viewpurchase.headerid }">
					<input type="hidden" id="recvstatus${viewpurchase.index }" value="${ viewpurchase.receive_status }"> --%>
			</td>
		 </tr>
								            
				    </c:forEach> 
					</tbody>                               
			 </table>

     
     <div class="paginationView" align="right">
              <span class="tabelCount pull-left">Total Count : ${purchaselistsize} </span> 
              <span class=" pull-right"> 
                
                <select class="selectNor " onchange="gettxndetails(this.value)">
                        <c:forEach var="page" begin="1" end="${((purchaselistsize % 10) == 0) ? (purchaselistsize / 10) : (purchaselistsize / 10) + 1 }">
                                    <option value="${page }">Page${page }</option>
                         </c:forEach>     
                </select>
              </span>
     </div>         
        
        </div>
		</div>
		</div>
									
									
									
	<div class="width_75 gray-font white-bg content-font-size content-div-heightnw"  id="style-5"> 
	<div class="col-md-12" >
		 <div class="col-md-4" >
			  <h3>  <strong id="postrongid"></strong></h3>
		 </div>
				 <div class="col-md-8 ">
								<a href="purchaserequesthistory" >	<button class="button-bg button-space pull-right" >Go Back</button></a>
							  </div> 						
<%-- 	<div class="col-md-8 margin-bottom-12">	
		 <button class="button-bg button-space mar-bot-15" onclick="generatepurchasereport();"><i class="fa fa-print"></i></button>
		 <% if(session.getAttribute("usertypestr").equals("2")) { %>
		 	<button class="button-bg button-space mar-bot-15" type="button"  onclick="receivestockprocess();" id="recvbtn" >Receive Stock</button>
		 	<button class="button-bg button-space mar-bot-15" data-toggle="modal" data-target="#purchase_payment" >Add Payment</button>
		 
		 	<button class="button-bg button-space mar-bot-15" onclick="redirecttoReturn();">Return</button>
		 <% } %>
		 
		 <a class="button-bg button-space mar-bot-15" href="purchaserequesthistory" class="" >Go Back</a> 
	</div> --%>
	
	
	</div>
										
	<div class="clearfix"></div>
										
	<div class="col-md-12">
	

	<div class="tab-content">
		 <div id="Receives" class="tab-pane active pill-space padding-null">
				
			
	<div class="main-template new-main-tem-style">
		 <div class="main-template-body">
	<!-- 		  <table style="width:100%;table-layout: fixed;">
			   
			     <tbody>
						<tr>
							<td style="vertical-align: top; width:50%;">
		
		                         <div> <img style="width: auto; height: auto; max-width: 300px; max-height: 100px;" 
		                         id="logo_content" src="/heroadmin/forms/HeroImageView?index=0"></div>
																		
							</td>
					
					<span class="pcs-orgname"><b></b></span><br>
                          <span class="pcs-label">
                          <span style="white-space: pre-wrap;word-wrap: break-word;" id="tmp_org_address"></span>
                          </span>
					
					<td style="vertical-align: top; text-align:right;width:50%;">
						<span class="main-entity-title">PURCHASE REQUEST</span><br>
						<span id="tmp_entity_number" style="font-size: 10pt;" class="pcs-label"></span>
					</td>												
																	   
						 </tr>											
					</tbody>											
				</table> -->											
														
												
<!-- 		<table style="width:100%;margin-top:30px;table-layout:fixed;">
			<tbody>
				<tr>
					<td style="width:55%;vertical-align:bottom;word-wrap: break-word;">
						
					</td>

			<td style="vertical-align:bottom;width: 45%;" align="right">
				<table style="float:right;width: 100%;table-layout: fixed;word-wrap: 
				break-word;" cellspacing="0" cellpadding="0" border="0">
					<tbody>
			
					</tbody>
			</table>
			</td>
			</tr>
			</tbody>
			</table> -->
											     
														
<table style="width:100%;margin-top:20px;table-layout:fixed;" id="itemlistDT" 
                class="tablenew" cellspacing="0" cellpadding="0" border="0">
         
 <thead>
  <tr style="height:32px;">
                    
     <td  style="word-wrap: break-word;width: 5%;" class="pcs-itemtable-header">#</td>
     <td  style="word-wrap: break-word;width: 35%" class="pcs-itemtable-header pcs-itemtable-description">Product</td>     
    <!--  <td  style="word-wrap: break-word;width: 15%;" class="pcs-itemtable-header"> Full Qty</td>
     <td  style="word-wrap: break-word;width: 15%;" class="pcs-itemtable-header"> Loose Qty</td> -->
     <td  style="word-wrap: break-word;width: 15%;" class="pcs-itemtable-header"> Net Qty</td>
     <td  style="word-wrap: break-word;width: 15%;" class="pcs-itemtable-header" align="right"> Purchase Date</td> 

  </tr>
                                

</thead>
                               
</table>
											     
			
													</div>	
														
												</div>																				
											</div>
											
								
										  </div>
										</div>
										
									</div>

								</div>
							</div> 

							</div>
						</div> 

<style>
.width_100 {
height:100%;}
.nav-tabs {
margin-top:10px;}
.main-entity-title {
    font-size: 17pt;
    color: #000000;
}

table.dataTable.no-footer {
   
    cursor: pointer;
}
</style>				
					
					
<jsp:include page="../../template/footer.jsp" />             

</body>
</html>