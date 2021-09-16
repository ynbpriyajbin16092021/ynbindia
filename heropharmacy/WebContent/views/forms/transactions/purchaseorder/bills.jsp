<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Purchase Bills</title>
<%if(request.getParameter("disp") == null){ %>
<jsp:include page="../../template/library.jsp" />
<%} %>
<script type="text/javascript" src="../js/forms/transactions/purchaseorder.js"></script>
</head>
<body onload="loadbills();">

<%if(request.getParameter("disp") == null){ %>
  	<jsp:include page="../../template/header.jsp" />
<%} %>

           <div class="clearfix"></div>
			    
			    <div class="breadcrumbs"  style="display: <%= request.getParameter("disp") %>">
			         <div class="col-md-4 col-sm-4">
			              <div class="page-header">
			                   <p class="cursor-pointer mar-top-10 bread-a-style head-font-size yellow-font" href="stockdashboard"><strong><i class="fa fa-shopping-cart"></i>  Bill List</strong></p>
			              </div>
			         </div>
			           
			           
			         <div class="col-md-8 col-sm-8">
			              <div class="page-header float-right">
			                   <a class="pull-right cursor-pointer bread-a-style content-font-size color-font" href="dashboard?uid=1">Home / Dashboard</a>
			              </div>
			         </div>
			   </div>

            <div class="clearfix"></div>
				  


            <div class="card">
					
				 <div class="col-md-12 ">
			     <div class="col-md-9 sales"></div>
					  
				 <div class="col-md-3 sales">
					
					  <select id="supplierselect" onchange="getBillList(this.value)">
						      <option value="0"> --All--</option>
						              <c:forEach items="${supplierList}" var="supplier" >                  
					                          <%--   <option value="${supplier.value}">${supplier.label}</option>        --%>  
					           <option value="${supplier.supplierid},${ supplier.opttype},${ supplier.paymode},${supplier.credireddays}">${supplier.suppliername}           
					                  </c:forEach>
					  </select>
				 </div>
				 </div>
					
					
				<div class="col-md-12 overcome-col-padd-10  ">
					 <div class="col-md-12 overcome-col-padd-10 ">
						  <div class="width_100">
								
								<div class="width_100 gray-font white-bg content-font-size content-div-height">
									 <div class="col-md-12 full-padding-20">
                       
                         
					  <table id="billsDT" class="hero-settings-table" >
							 <thead>
									 <tr>
									     <th><!-- <input type="checkbox" onclick="checkAllBills();" checked="checked" id="topcheck"> --> </th>
										 <th>Id</th>
                                         <th>Purhase Code</th>
                                         <th>Bill No</th>
                                         <th>Bill Amount</th>
                                         <th>Bill Date</th>
                                         <th>Already Paid</th>
                                         <th>Status</th>
													
									 </tr>
					           </thead>
											
					  </table>
									
									
					<div class="col-md-12 margin-bot-15 permissionDiv">
                         <button  class="btn btn-primary" type="button" data-toggle="modal" data-target="#purchase_payment"  id="paybillbtnhidden" style="display: none;">  Pay Bill </button>
						 <button  class="btn btn-primary" type="button" onclick="paybulkbill();"  id="recvbtn">  Pay Bill </button>
			        </div>	
					
		</div>
        </div>
        </div>
        </div>
        </div>
                   
					
   <div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog" tabindex="-1" id="purchase_payment" class="modal fade modalForgetPassword" >
   <div class="modal-dialog widthModalForget">
		
		
<div class="modal-content">
	    <div class="modal-header">
		                          
		    <h4 class="modal-title">Add Payment</h4>
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        </div><br>
							 
		
        <div class="col-md-12">
             <p class="pbiller">Your batch numbers are set on auto-generate mode to save your time. Do you want to change settings?</p>
		</div>
		                     
<div class="modal-body">
		<div class="col-md-12">	   
			
			 <div class="col-md-6 form-group">
			    <label for="Date">Date</label>
				<input placeholder="" class="form-control form-white datepicker" type="text" id="paymentdatetext" readonly="readonly">
			 </div>
									
		     <div class="col-md-6 form-group">
				<label for="Total Bill Amount">Total Bill Amount</label>
				<input placeholder="" class="form-control form-white" readonly="readonly" type="text" id="totalbillamttext">
			</div>
		</div>
								
	     <div class="col-md-12">	   
		 <div class="col-md-6 form-group">
			  <label for="To be Paid">To be Paid</label>
			  <input placeholder="" class="form-control form-white " type="number" id="payingamount">
              <input placeholder="" class="form-control form-white " type="hidden" id="tobepayamount" value="0">
              <input placeholder="" class="form-control form-white " type="hidden" id="paymentheaderid" value="0">
		 </div>
									
		<div class="col-md-6 form-group">
			<label for="Paying By ">Paying By </label>
			<div id="supplierdefault">
              <input placeholder="" class="form-control form-white " type="text" id="paymodename" readonly="readonly">
            </div>
            <div id="supplieroptional">
            <select class="selectNor" onchange="selectpaymenttype()" id="paymenttypeselect">
            <c:forEach items="${paymenttypeList}" var="paymenttype" > 
            <option value="${paymenttype.value }"> ${paymenttype.label } </option>
            </c:forEach>
            </select> 
		</div>
		</div>
		
	   <div class="col-md-6 form-group">
       <div id="creditedwithin"  >
            <label>Credited Within</label>
            <input placeholder="" class="form-control form-white " type="text"  id="creditedwithintext"  >
       </div>
       </div>  
					
								
		<div id="default"  >
		<div class="col-md-12">	   
		<div class="col-md-6 form-group">
			 <label for="Bank Name ">Bank Name </label>
			 <select class="selectNor form-control inpttypestyle" disabled="disabled" id="banknameselect">
             <c:forEach items="${bankList}" var="bank" > 
             <option value="${bank.value }"> ${bank.label } </option>
             </c:forEach>
             </select>
		</div>
									
		<div class="col-md-6 form-group">
			 <label for="Cheque No">Cheque No</label>
			 <input placeholder="" class="form-control form-white " type="text" id="chequenotext" >
		</div>
		</div>
		</div>
								
 </div>
		                    
		<div class="clearfix"></div>                        
		                          
		<div class="col-md-12 " >
             <button type="button" class="btn btn-primary " onclick="paybulkbillsave();" id="paymentsavebtn">Save</button>
             <button type="button" class="btn btn-primary " data-dismiss="modal"  id="paymentsavebtnhide" style="display: none">Save</button>
             <button type="button" class="btn btn-normal " data-dismiss="modal">Cancel</button>
	    </div><br><br><br>
								   
</div>
</div>
</div>
		          <style>
		          .card{
		          margin-left:20px;
		          margin-right:20px;}
		          button, input, select, textarea {
    
    margin-top: 20px;
}
		          
		          </style>
		          <jsp:include page="../../template/footer.jsp" />

</body>
</html>