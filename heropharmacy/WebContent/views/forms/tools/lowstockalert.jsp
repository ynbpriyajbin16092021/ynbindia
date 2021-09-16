<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script type="text/javascript" src="../js/forms/tools/lowstock.js"></script>
    <title>Low Stock Alert</title>
    <jsp:include page="../template/library.jsp" />
</head>
<body onload="loadlowstock()"> 

<jsp:include page="../template/header.jsp" />
<div class="clearfix"></div>
					<div class="breadcrumbs"  style="display: <%= request.getParameter("disp") %>">
			            <div class="col-md-4 col-sm-4">
			                <div class="page-header">
			                        <div class="lowstockspan yellow-font"><strong class="card-title"><i class="fa fa-shopping-cart"></i> Low Stock List <span class="notification-lowstock-page"><%=session.getAttribute("lowstockcount") %><span></span></span></strong></div>
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
						<div class="col-md-12 overcome-col-padd-10 ">
							<div class="width_100">
								
								<div class="width_100 gray-font white-bg content-font-size content-div-height">
									<div class="col-md-12 full-padding-20">
								<%if(request.getSession().getAttribute("usertypestr") != null && request.getSession().getAttribute("usertypestr").equals("1")){ %>
				                        <div class="col-md-12" style="margin-top:10px;">
					                        <div class="col-sm-3">
						                        <label> Store List </label>
					                        </div>
					                     <div class="col-md-3">
									        <select class="form-control form-white selectNor" id="storeselect" onchange="productdetails(this.value)" >
									        <option value="0">Ware House</option>
									          <c:forEach items="${storeList}" var="store" >                  
										        <option value="${store.value}">
										            ${store.label}
										        </option>                    
									    	  </c:forEach>
									        </select>
									       </div>
									</div>
									<%} %>
 
									<table  class="hero-settings-table"  style="width:100%" id="lowstockDT">
                                        <thead>
                                            <tr >
                                               <th>Product Name </th>
                                               <th>Notified Qty</th>
                                               <th>Available Qty in Stock</th>
                                               <th>Location</th>
                                            </tr>
                                         </thead>
                                    </table>
								</div>	
							
							</div>
							</div>
						</div>
					</div>
								

                

       <jsp:include page="../template/footer.jsp" />
</body>
</html>