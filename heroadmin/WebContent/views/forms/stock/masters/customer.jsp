<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Customer List Screen</title>
<script type="text/javascript" src="../js/forms/admin/masters/masters.js"></script>
</head>
<body onload="loadcustomer();">
<style>
a.mar-bot-15{
margin-bottom:15px;
}
</style>
<jsp:include page="../../template/header.jsp" /> 
                    <div class="card">
                        <div class="card-header">
                            <strong class="card-title">Customers List</strong>
                        </div>
                        <div class="card-body">
                          <!-- Credit Card -->
                          <div id="pay-invoice">
                           <a href="addcustomer" class="btn btn-secondary pull-right mar-bot-15" > New <i class="fa fa-plus-circle"></i></a><br>
									<table id="customerDT" class="table table-striped table-bordered dt-responsive nowrap">
											<thead>
											<tr>
													<td>Name</td>
												    <td>Mobile</td>
													<td>Email</td>
													<td>Status</td>
													<td>Group</td>
												    <td>Action</td>		
												    </tr>	
											</thead>
									</table><br>
                                                    
                          </div>
                       </div>
                    </div> 
<jsp:include page="../../template/footer.jsp" /> 				  
</body>
</html>
				