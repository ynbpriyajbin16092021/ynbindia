<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
   pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
      <title>View Purchase Order</title>
      <jsp:include page="../../template/library.jsp" />
      <script type="text/javascript" src="../js/forms/transactions/order.js"></script>
      <link rel="stylesheet" href="../../heroadmin/resources/css/stylebass.css">
      <link rel="stylesheet" href="../../heroadmin/resources/css/stylej.css">
   </head>
   <body onload="loadorderReqview();">
      <jsp:include page="../../template/header.jsp" />
      <div class="clearfix"></div>
      <div class="breadcrumbs"  style="display: <%= request.getParameter("disp") %>">
         <div class="col-md-4 col-sm-4">
            <div class="page-header">
               <p class="cursor-pointer mar-top-10 bread-a-style head-font-size yellow-font" href="stockdashboard"><strong>Purchase view</strong></p>
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
            <div class="width_100 gray-font white-bg content-font-size content-div-height">
               <div class="col-md-12 full-padding-20">
                  <div class="width_25" >
                     <div class="width_100 scrolly" style="overflow-y:scroll;height:1000px" id="style-10">
                        <div class="rowNor ">
                           <input type="hidden" value="${firstid }" id="firstidtext">  
                           <input type="hidden" id="totalpurchasetext" value="${orderfullList.size() }"></input>
                           <input type="hidden" id="pidtext"></input>           
                           <table class="table table-hover selectCheckTabel" id="txrtable">
                              <tbody>
                                 <c:forEach items="${orderfullList}" var="viewpurchase" >
                                    <tr>
                                       <td><input name="purchaseorderidcheck" type="radio"  onclick="loadviewlist(${viewpurchase.value})"> ${viewpurchase.label}</td>
                                    </tr>
                                 </c:forEach>
                              </tbody>
                           </table>
                        </div>
                     </div>
                  </div>
                  <div class="width_75 gray-font white-bg content-font-size content-div-heightnw"  id="style-5">
                     <div class="col-md-12" >
                        <div class="col-md-4" >
                           <c:forEach items="${orderList}" var="viewpurchase" >
                              <h3>  <strong id="postrongid">${viewpurchase.label}</strong></h3>
                           </c:forEach>
                        </div>
                        <div class="col-md-8 margin-bottom-12">	
                           <a class="button-bg button-space  pull-right" href="OrderRequestHistory" class="" >Go Back</a> 
                        </div>
                     </div>
                     <div class="clearfix"></div>
                     <div class="col-md-12">
                        <div class="tab-content">
                           <div id="Receives" class="tab-pane active pill-space">
                              <div class="main-template margin-txt">
                                 <div class="main-template-body order-request-setting-table">
                                    <table style="width:100%;margin-top:20px ;table-layout:fixed;" id="itemlistDT" 
                                       class="tablenew" cellspacing="0" cellpadding="0" border="0">
                                       <thead>
                                          <!--  <tr style="height:32px;">  -->
                                          <th >#</th>
                                          <th >Customer Name</th>
                                          <th >Dish Name</th>
                                          <th >Dish Type</th>
                                          <th >Dish Count</th>
                                          <!--  </tr> -->
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