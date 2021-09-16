<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script type="text/javascript" src="../js/forms/transactions/dish.js"></script>
    <title>Dish Request History</title>
  <jsp:include page="../../template/library.jsp" />
</head>
<body > 

<jsp:include page="../../template/header.jsp" />
<div class="clearfix"></div>
					<div class="breadcrumbs"  style="display: <%= request.getParameter("disp") %>">
			            <div class="col-md-4 col-sm-4">
			                <div class="page-header">
			                        <div class="lowstockspan yellow-font"><strong class="card-title"><i class="fa fa-shopping-cart"></i>  Dish Request List <span class="notification-delpo-page">${notification_count}<span></span></span></strong></div>
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
								
								<a href="adddishrequest?id=0&did=0" class="button-bg button-space pull-right mar-bot-15-bp">
							Create Dish Request <i class="fa fa-plus-circle"></i></a>
							
									<div class="col-md-12 full-padding-20">
										<table  class="hero-settings-table"  style="width:100%" id="purchaseRequestDT">
	                                        <thead>
	                                            <tr>
	                                               <th> # S.No</th>
	                                               <th>Dish Name</th>                                             
	                                               <th>Dish Type</th>
	                                               <th>Count</th>
                                                   <th>Action</th>
	                                            </tr>
	                                         </thead>
	                                           <tbody>
                                         	<c:forEach items="${dishList}" var="dish" varStatus="loop">
                                         		<tr>
                                         			<td>${loop.index+1}</td>
                                         			<td>${dish.dishName }</td>
                                         			<td>${dish.dishTypeName }</td>
                                         			 <td>${dish.count }</td>  
                                         			  <td><input class="btn white-bg fa-input color-font" type="button" value="&#xf044;" onclick="editDish(${dish.id },${dish.dishNameId })" ></td>                       			
                                         		
                                         		</tr>
                                         	</c:forEach>
                                         </tbody>
	                                    </table>
									</div>	
								</div>
							</div>
						</div>
					</div>
								
		<script>
		$(document).ready(function() {
		    $('#purchaseRequestDT').DataTable({	ordering:false});
		});
		/* $('#purchaseRequestDT').DataTable();  */
		</script>					

     <jsp:include page="../../template/footer.jsp" />
</body>
</html>