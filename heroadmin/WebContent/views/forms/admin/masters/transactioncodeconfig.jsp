<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="../js/forms/admin/masters/transactioncodeconfig.js"></script>


<%if(request.getParameter("disp") == null){ %>
<jsp:include page="../home/homelibrary.jsp" />
<%} %>	

<title>TransactionCode Configurations</title>
</head>
<body onload="loadtransactioncodes();">

<%if(request.getParameter("disp") == null){ %>
  	<jsp:include page="../home/homeheader.jsp" />
<%} %>

   <div class="clearfix"></div>
					<div class="breadcrumbs"  style="display: <%= request.getParameter("disp") %>">
			            <div class="col-md-4 col-sm-4">
			                <div class="page-header">
			                        <p class="cursor-pointer mar-top-10 bread-a-style head-font-size yellow-font" href="stockdashboard"><strong> <i class="fa fa-medkit" ></i> Transaction Code Generation </strong></p>
			                </div>
			            </div>
			            <div class="col-md-8 col-sm-8">
			                <div class="page-header float-right">
			                    <a class="pull-right cursor-pointer bread-a-style content-font-size color-font" href="herohome">Home / Dashboard</a>
			                </div>
			            </div>
			        </div>
					<div class="clearfix"></div>
                        <div class="col-md-12 overcome-col-padd-10  ">
						<div class="col-md-12 overcome-col-padd-10 ">
							<div class="width_100">
								
								<div class="width_100 gray-font white-bg content-font-size content-div-height">
									<div class="col-md-12 full-padding-20">
									<div class="col-md-4 ">
                             <label>Store Name</label>
                            <select id="storeselect" class="form-control form-white">
            <c:if test="${storeList.size() == 0}"><option value="">--Select--</option></c:if>
            
            <c:forEach items="${storeList}" var="store" >
              <option value="${store.key }"> ${store.value } </option> 
            </c:forEach>
           
            </select>
                             </div>
									<div class="col-md-8 ">
                             <a href="addtransactioncode?txncodeid=0" class="button-bg button-space pull-right mar-bot-15">New <i class="fa fa-plus-circle"></i></a>
                             </div>
						          <table id="txncodesDT" class="hero-settings-table">
											<thead>
												<tr>
												    <th>UserType Desc</th>
													<th>Txn. Code Desc.</th>
													<th>Txn. No. Length</th>
													<th>Txn. Start From</th>
													<th>Txn. End To</th>
													<th>Action</th>
												</tr>
											 </thead>
								  </table>
									
							</div>
                        </div>
                    </div> 
                    </div>
                    </div>
                          


<%if(request.getParameter("disp") == null){ %>
<jsp:include page="../home/homefooter.jsp" />
<%} %>

</body>
</html>