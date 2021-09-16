<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>TransactionCode Master</title>
<%if(request.getParameter("disp") == null){ %>
<jsp:include page="../home/homelibrary.jsp" />
<%} %>
<script type="text/javascript" src="../js/forms/admin/masters/transactioncodeconfig.js"></script>
</head>
<body onload="loadaddtxnmaster();">
<%if(request.getParameter("disp") == null){ String applnid = request.getParameter("applnid");%>
  	<jsp:include page="../home/homeheader.jsp" />	
  	<input type="hidden" name="applnid" id="applnidli" value="${applnid }" />		
<% 
} %>
	 				<div class="width_100  ">
						<div class="width_100 ">
							<div class="width_100">
								
								<div class="width_100 gray-font white-bg content-font-size content-div-height">
									<div class="col-md-12 full-padding-20">
											<div class="margin-bottom-10 content-font-size  white-font color-bg">
											<p class="padd-left-right-5">TransactionCode Master</p>
										</div>

					
					  <!-- Main content ends here -->
					
    <!---modal start here-->
					 
		              
		                    
     
         <div class="col-sm-4 form-group" >
            <label>Store Name</label>
            <input type="hidden" name="txnid" id="txnid" value="0" />
            <input type="hidden" name="oprntext" id="oprntext" value="INS" />
            <select id="storeselect" class="form-control form-white">
            <c:if test="${storeList.size() == 0}"><option value="">--Select--</option></c:if>
            
            <c:forEach items="${storeList}" var="store" >
              <option value="${store.key }"> ${store.value } </option> 
            </c:forEach>
           
            </select>
          </div>
          
          <div class="col-sm-4 form-group" >
            <label>User Group</label>
            <!-- <select id="taxtypeselect" onchange="changetaxrate(this.val);"> -->
            <select id="usergroupselect" class="form-control form-white">
            <c:if test="${userGroupList.size() == 0}"><option value="">--Select--</option></c:if>
            
            <c:forEach items="${userGroupList}" var="usergroup" >
              <option value="${usergroup.key }"> ${usergroup.value } </option> 
            </c:forEach>
           
            </select>
          </div>
          
          <div class="col-sm-4 form-group" >
            <label>Transaction Type</label>
            <!-- <select id="taxtypeselect" onchange="changetaxrate(this.val);"> -->
            <select id="txntypeselect" class="form-control form-white" onchange="gettxncode()">
            <c:if test="${txnTypeList.size() == 0}"><option value="">--Select--</option></c:if>
            
            <c:forEach items="${txnTypeList}" var="txntype" >
              <option value="${txntype.key }"> ${txntype.value } </option> 
            </c:forEach>
           
            </select>
          </div>
          
          <div class="col-sm-4 form-group" >
            <label>Transaction Code</label>
            <input type="text" id="txncodeid" class="form-control form-white" maxlength="3" value="${txncode }">
          </div>
          
          <div class="col-sm-4 form-group" >
            <label>Transaction Number Length</label>
            <input type="text" id="txnnolengthid" class="form-control form-white" maxlength="1" value="${txnnodeflength }">
          </div>
          
          <div class="col-sm-4 form-group" >
            <label>Transaction No Start From</label>
            <input type="text" id="txnnostartid" class="form-control form-white" onblur="setdefaultfromvalue(this.id,'S')" value="0">
          </div>
          
          <div class="col-sm-4 form-group" >
            <label>Transaction No End To</label>
            <input type="text" id="txnnoendid" class="form-control form-white" onblur="setdefaultfromvalue(this.id,'E')">
          </div>
          
          
          
          <div class="col-sm-4 form-group" >
            <label>Transaction Code Split</label>
            <input type="text" id="txncodesplitid" class="form-control form-white" maxlength="1" value="/">
          </div>
          
          
          <div class="col-sm-4 form-group" >
            <label>Transaction No Format</label>
            <select id="txnnoformatid" class="form-control form-white">
            <option value="1-nil">Currrent Date</option>
            <option value="2-nil">Current Month</option>
            <option value="3-nil">Current Year</option>
            <option value="4-nil">Date Format</option>
            <option value="5-txncodeid">Transaction Code</option>
            <option value="6-storeselect">Store Name</option>
            <option value="7-usergroupselect">User Group</option>
            
            
            </select>
            
            <a class="button-bg button-space pull-right mar-bot-15" onclick="addtransactioncode();">Add <i class="fa fa-plus-circle"></i></a>
            </div>
	
	<div class="col-sm-12 form-group" id="txnformatdispid">
	
	</div>
	
	<div class="col-sm-4 form-group" >
	  <label>Sample Transaction No. as per your combination</label>
	    <span class="label label-primary" id="sampletxnno"></span>
	</div>
			<div class="col-sm-4 form-group" align="left">
			
			</div>
        
	  
        <div class="col-md-12 form-group" >
             <button type="button" class="btn btn-primary"  onclick="savetxncode();">Save</button>
        </div>

      
      </div>
      </div>
      </div>
      </div>
      
        </div>   
    					 
<jsp:include page="../home/homefooter.jsp" />
</body>
</html>
	 