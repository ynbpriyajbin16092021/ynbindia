<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>HERO</title>
<jsp:include page="../template/library.jsp" />
<script type="text/javascript" src="../js/forms/loaddashboardgraph.js"></script>
</head>
<body onload="loaddashboardgraph()">



<link rel="stylesheet" href="../resources/css/dashboard.css">

<style>
.page-content h3 {
    font-family: 'Open Sans', sans-serif;
    font-size: 15px;
    margin-bottom: 15px;
    margin-top: 25px;
    text-transform: uppercase;
}
</style>
<jsp:include page="../template/header.jsp" />
<link href="../resources/css/template/layout.css" rel="stylesheet"> 
<div class="clearfix"></div>
			<%-- 		<div class="breadcrumbs"  style="display: <%= request.getParameter("disp") %>">
			            <div class="col-md-4 col-sm-4">
			                <div class="page-header">
			                        <a class="cursor-pointer bread-a-style content-font-size color-font" href="stockdashboard">DashBoard</a>
			                </div>
			            </div>
			            <div class="col-md-8 col-sm-8">
			                <div class="page-header float-right">
			                    <a class="pull-right cursor-pointer bread-a-style content-font-size color-font" href="heropharmacysettings">Config System Settings</a>
			                </div>
			            </div>
			        </div> --%>
			        <div class="col-md-12 " >
				        <div class="token-subjects">
									<div class="token-subjects-img">
										<img src="../resources/images/prod.png" >
									</div>
									<div class="token-subject-p">
										<a href="product" class="content-font-size color-font"><strong>${productCount } Products</strong></a>
									</div>
								</div>
						<div class="token-subjects">
									<div class="token-subjects-img">
										<img src="../resources/images/supp.png" >
									</div>
									<div class="token-subject-p">
										<a href="supplier" class="content-font-size color-font"> <strong>${supplierCount } Suppliers</strong></a>
									</div>
								</div>
						<%-- <div class="token-subjects">
								<div class="token-subjects-img">
									<img src="../resources/images/sto.png" >
								</div>
								<div class="token-subject-p">
									<a href="store" class="content-font-size color-font"> <strong>${storeCount } Stores</strong></a>
								</div>
							</div> --%>
						<div class="token-subjects">
								<div class="token-subjects-img">
									<img src="../resources/images/cus.png" >
									<!-- 2.png is Customers -->
								</div>
								<div class="token-subject-p">
									<a href="customer" class="content-font-size color-font"> <strong>${customerCount } Customers</strong></a>
								</div>
							</div>
						<!-- <div class="token-subjects">
								<div class="token-subjects-img">
									<img src="../resources/images/lp.png" >
								</div>
								<div class="token-subject-p">
									<a class="content-font-size color-font"> <strong>Pending Transfer</strong></a>
								</div>
							</div> -->
						
					</div>
					
					<!-- Start  view order,invoice,po,lowstaock,transfer pending -->
					
						<div class="col-md-12">
							<div class="dashboard-long-div margin-right-5px">
								<div class="dashboard-ld-header head-font-size white-font color-bg">    
									<p><span style="font-size:18px;font-weight:bold">${ordercount} Orders </span></p>
								</div>
								<div class="dashboard-ld-content gray-font white-bg content-font-size">
									<p class="color-font">Recent orders</p>
									<ul>
										<c:forEach items="${orderList}" var="order" >
										<li><span>${order.pos_code }</span><span class="pull-right color-font">${order.pos_status_desc}</span></li>
										</c:forEach>
									</ul>
								</div>
							</div>
							<div class="dashboard-long-div margin-right-5px">
								<div class="dashboard-ld-header head-font-size white-font color-bg">
									<p><span style="font-size:18px;font-weight:bold">${receivestockcount}  Receive stock</span></p>
								</div>
								<div class="dashboard-ld-content gray-font white-bg content-font-size">
									<p class="color-font">Recent Receive stock </p>
									<ul>
										<c:forEach items="${receivestock}" var="order" >
										<li><span>${order.product_name }</span><span class="pull-right color-font">${order.prec_recving_quantity}</span></li>
										</c:forEach>
										
									</ul> 
								</div>
							</div>
							<div class="dashboard-long-div">
								<div class="dashboard-ld-header head-font-size white-font color-bg">
									<p><span style="font-size:18px;font-weight:bold">${purchaseCount }  Purchase Order</span></p>
								</div>
								<div class="dashboard-ld-content gray-font white-bg content-font-size">
									<p class="color-font">Recent purchase</p>
									<ul>
										<c:forEach items="${purchaserecentList}" var="purchaseList" >
									<li><span>${purchaseList.purchase_code}</span><span class="pull-right color-font">${purchaseList.supplier_first_name }</span><span class="pull-right color-font">${purchaseList.ps_status_name }</span></li>
									</c:forEach>
									</ul>
								</div>
							</div>
						</div>
					
						<div class="col-md-12">
							<div class="dashboard-long-div  margin-right-5px">
								<div class="dashboard-ld-header head-font-size white-font color-bg">
									<p><span style="font-size:18px;font-weight:bold">${lowstockcount } Low stock</span></p>
								</div>
								<div class="dashboard-ld-content gray-font white-bg content-font-size">
									<p class="color-font">low stock products</p>
									<ul>
										<c:forEach items="${lowstockList}" var="lowstock" >
										<li><span>${lowstock.product_name } <span class="pull-right color-font">${lowstock.product_count}</span></li>
									 </c:forEach>	
									</ul>
								</div>
							</div>
							<%-- <div class="dashboard-long-div margin-right-5px">
								<div class="dashboard-ld-header head-font-size white-font color-bg">
									<p><span style="font-size:18px;font-weight:bold">${transfersCount} Transfer </span></p>
								</div>
								<div class="dashboard-ld-content gray-font white-bg content-font-size">
									<p class="color-font">Transfer</p>
									<ul>
									 <c:forEach items="${transferList}" var="transfer" >
										<li><span>${transfer.transfer_no } <span class="pull-right color-font">${transfer.status_desc}</span></li>
									 </c:forEach>																
									</ul>
								</div>
							</div> --%>
							<div class="dashboard-long-div">
								<div class="dashboard-ld-header head-font-size white-font color-bg">
									<p><span style="font-size:18px;font-weight:bold">${productCount } Product</span></p>
								</div>
								<div class="dashboard-ld-content gray-font white-bg content-font-size">
									<p class="color-font">Recent products</p>
									<ul>
										<c:forEach items="${productRecentList}" var="recentproduct" >
										<li><span>${recentproduct.product_name} <span class="pull-right color-font">${recentproduct.alert_count}</span></li>
									 </c:forEach>	
									</ul>
								</div>
							</div>
						</div>
						
						<div class="col-md-12">
						<div>
						
<%--             <div class="col-xlg-4 col-financial-stocks">
              <div class="panel">
                <div class="panel-header panel-controls">
                  <h3><i class="icon-graph"></i> <strong>Financial</strong> Stock</h3>
                </div>
                <div class="panel-content widget-full widget-stock stock1">
                  <div class="tabs tabs-linetriangle">
                    <ul class="nav nav-tabs nav-4" role="tablist">
                    
                    	<c:forEach items="${g_storeList}" var="gStore" varStatus = "status" >
	                        <li ${status.first ? 'class = "nav-item lines-3"' : 'class = "nav-item lines-3 active"'} role="tab" data-toggle="tab">
		                        <a href="${gStore.tab_name}" id="${gStore.store_id}" data-toggle="tab">
		                        <span class="title">${gStore.store_name}</span>
		                        <span class="c-gray"><strong>${gStore.state},${gStore.country}</strong></span> 
		                        <!-- <span class="c-green">+6.214%</span> -->
		                        </a>
		                    </li>
						</c:forEach>
                    
                    
                    
                     <!--  <li class="nav-item lines-3" role="tab" data-toggle="tab">
                        <a href="#microsoft-tab" id="microsoft" data-toggle="tab">
                        <span class="title">Microsoft</span>
                        <span class="c-gray"><strong>23.32</strong></span>
                        <span class="c-green">+6.214%</span>
                        </a>
                      </li>
                      <li class="nav-item active lines-3" role="tab" data-toggle="tab">
                        <a href="#sony-tab" id="sony" data-toggle="tab">
                        <span class="title">Sony</span>
                        <span class="c-gray"><strong>23.32</strong></span>
                        <span class="c-red">-8.425%</span>
                        </a>
                      </li>
                      <li class="nav-item lines-3" role="tab" data-toggle="tab">
                        <a href="#samsung-tab"  id="samsung" data-toggle="tab">
                        <span class="title">Samsung</span>
                        <span class="c-gray"><strong>23.32</strong></span>
                        <span class="c-green">+2.035%</span>
                        </a>
                      </li>
                      <li class="nav-item lines-3" role="tab" data-toggle="tab">
                        <a href="#apple-tab"  id="apple" data-toggle="tab">
                        <span class="title">Apple</span>
                        <span class="c-gray"><strong>23.32</strong></span>
                        <span class="c-green">+1.245%</span>
                        </a>
                      </li> -->
                    </ul>
                    <div class="tab-content">
                      <!-- <div role="tabpanel" class="tab-pane" id="microsoft-tab">
                        <div id="stock-microsoft"></div>
                      </div>
                      <div role="tabpanel" class="tab-pane active" id="sony-tab">
                        <div id="stock-sony"></div>
                      </div>
                      <div role="tabpanel" class="tab-pane" id="samsung-tab">
                        <div id="stock-samsung"></div>
                      </div>
                      <div role="tabpanel" class="tab-pane" id="apple-tab">
                        <div id="stock-apple"></div>
                      </div> -->            
                      
                       <c:forEach items="${g_storeList}" var="gStore" varStatus = "status">
                       <div role="tabpanel" ${status.first ? 'class = "tab-pane"' : 'class = "tab-pane active"'}  id="${gStore.store_id}_tab">
                        <div id="store-${gStore.store_id}"></div>
                      </div>
                      </c:forEach>
                      
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="col-xlg-2 col-small-stats">
              <div class="row">
                <div class="col-xlg-12 col-lg-4 col-sm-4">
                  <div class="panel">
                    <div class="panel-content widget-small color-bg white-font">
                      <div class="title">
                        <h1 class="head-font-size">Sales</h1>
                        <p class="head-font-size">Last month trending</p>
                        <!-- <span>-8.452%</span> -->
                      </div>
                      <div class="content">
                        <div id="stock-sales-sm"></div>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="col-xlg-12 col-lg-4 col-sm-4">
                  <div class="panel">
                    <div class="panel-content widget-small yellow-bg white-font">
                      <div class="title">
                        <h1 class="head-font-size">Tax</h1>
                        <p class="head-font-size">Last month trending</p>
                        <!-- <span>+2.124%</span> -->
                      </div>
                      <div class="content">
                        <div id="stock-tax-sm"></div>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="col-xlg-12 col-lg-4 col-sm-4">
                  <div class="panel">
                    <div class="panel-content widget-small color-bg white-font">
                      <div class="title">
                        <h1 class="head-font-size">Customer Due</h1>
                        <p class="head-font-size">Last month trending</p>
                        <!-- <span>+1.054%</span> -->
                      </div>
                      <div class="content">
                        <div id="stock-customerdue-sm"></div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="col-xlg-6">
              <div class="panel">
                <div class="panel-content widget-full widget-stock stock2">
                  <div class="tab_right">
                    <ul class="nav nav-tabs">
                    	<c:forEach items="${g_manufacturerList}" var="gManufaturer" varStatus ="statusM"  >
	                         <li ${statusM.first ? 'class="lines-3"' : 'class="lines-3 active"' }>
		                        <a href="${gManufaturer.tab_name}" id="${gManufaturer.manufacturer_id}" data-toggle="tab" data-color="green" data-value="+6.214%" data-name="${gManufaturer.company_name}">
		                          <div class="clearfix">
		                            <span class="title pull-left">${gManufaturer.company_name}</span>
		                            <span class="pull-right">${gManufaturer.company_name}</span>
		                          </div>
		                          <!-- <div class="clearfix">
		                            <span class="c-gray pull-left"><strong>23.32</strong></span>
		                            <span class="c-green pull-right">+6.214%</span>
		                          </div> -->
		                        </a>
		                      </li>
						</c:forEach>
                   </ul>
                    <div class="tab-content">
                      <div class="title-stock">
                        <h1>Product List</h1>
                        <!-- <span class="c-red">-8.425%</span> -->
                      </div>
                      <c:forEach items="${g_manufacturerList}" var="gManufaturer" varStatus ="statusM">
                      <div class="tab-pane" id="${gManufaturer.manufacturer_id}_manufacturer_tab" ${statusM.first ? 'style="display:none"' : '' }>
                        <div id="manufacturer-${gManufaturer.manufacturer_id}"></div>
                        <div class="company-info">${gManufaturer.description}</div>
                      </div>
                      </c:forEach>
                    </div>
                    
                    
                    
                      
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="row">
         
            <div class="col-md-12 col-sm-12 portlets">
              
              <div class="panel m-t-0">
                <div class="panel-header panel-controls">
                  <h3><i class="icon-basket"></i> <strong>Sales</strong> Volume Stats</h3>
                </div>
                <div class="panel-content p-t-0 p-b-0">
                  <div id="bar-chart"></div>
                </div>
              </div>

            </div>
          </div> --%>
          						</div>
						
						
					
			        <!--  end view order,invoice,po,lowstock,transfer pending -->
				


        <jsp:include page="../template/footer.jsp" />
         <script src="../../heroadmin/js/lib/theme/jquery-migrate-3.0.0.min.js"></script> 
		<script src="../../heroadmin/js/lib/theme/highstock.js"></script>
       

</body>
</html>