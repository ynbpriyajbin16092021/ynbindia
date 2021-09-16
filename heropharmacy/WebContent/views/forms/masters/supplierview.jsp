<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Supplier View</title>
<jsp:include page="../template/library.jsp" />
<script type="text/javascript" src="../js/forms/masters/view/mastersview.js"></script>
<link rel="stylesheet" href="../../heroadmin/resources/css/stylebass.css">
	<link rel="stylesheet" href="../../heroadmin/resources/css/stylej.css">
</head>
<body onload="loadsupplierview()">

<jsp:include page="../template/header.jsp" />
				  <!-- Main content starts here -->
		
		<div class="clearfix"></div>
					<div class="breadcrumbs"  style="display: <%= request.getParameter("disp") %>">
			            <div class="col-md-4 col-sm-4">
			                <div class="page-header">
			                        <p class="cursor-pointer mar-top-10 bread-a-style head-font-size yellow-font" href="stockdashboard"><strong>Supplier view</strong></p>
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
								
								
<div class="width_100 gray-font white-bg content-font-size">
									<div class="col-md-12 full-padding-20">
		
		
										 <div class="width_25 " >
										<!--  
										 <a href="addsupplier" class="button-bg button-space pull-right">New 
										 <i class="fa fa-plus-circle"></i></a>
										  -->
										
										 <div class="width_100">
										       <input type="hidden" id="fistsupplierid" value="<%=request.getParameter("supplier") %>">
 	  											<input type="hidden" id="supplierlistsize" value="${suppliertypeList.size() }">
										<table id="" class="table table-striped table-bordered dt-responsive nowrap" style="width:100%">
										 
											<c:forEach items="${suppliertypeList}" var="supplier" >
                                            <tr> 
                                              <td width="50px"> <input type="radio" id="suppliercheck${supplier.index}" value="${supplier.value }" name="suppliernameid" 
                                                   onclick="getsupplierdetails(this.value)"> </td> 
                                             <td> ${supplier.label }</td>
                                            </tr>
                                            </c:forEach>     
										</table>
												<div class="paginationView" align="right">
													<span class="tabelCount pull-left">Total Count : ${suppliertypeList.size() } </span> 
													<span class=" pull-right"> 
														<select class="selectNor ">
														<option>10 per page</option>
														<option>20 per page</option>
														<option>30 per page</option>
														<option>50 per page</option>
														</select>
													</span>
												</div>

										</div>
										</div>
										 <div class="width_75 " >
											<div class="col-md-12" >
											<div class="col-md-4" >
											<p class="select"><b>SUPPLIERS TITLE</b></p>
											</div>
											
												<div class="col-md-8 ">
													 <div class="pull-right">
						                                         <a href="supplier" class="button-bg button-space pull-right">
                                                                   Go Back
                                                                 </a>
                                                      </div>
											</div> 
											</div>
											<div class="clearfix"></div>
											<div class="col-md-12">
											 <ul class="nav nav-tabs">
												<li class="nav"><a data-toggle="tab" href="#overview">Overview</a></li>
												<li class="nav"><a data-toggle="tab" href="#Purchases">Purchases</a></li>
												<li class="nav"><a data-toggle="tab" href="#Payments">Payments</a></li>
											
											  </ul>
										   <div class="tab-content">
												<div class="tab-pane active" id="overview">
													<div class="col-md-8">
														 <!-- <div class="col-md-12">
															<div class="col-md-6 ">
															<br>
																<p id="suppliernametdid"><b>Supplier Name</b></p>
															</div>
															<br>
															<div class="col-md-6">
																<p>	SUPPLIER 1</p>
															</div>
														</div>	  -->
														<!-- <div class="col-md-12">
															<div class="col-md-6">
																<p><b>Mobile Phone</b></p>
															</div>
															<div class="col-md-6">
																<p>	68768668678</p>
															</div>
														</div>	 --> 
														<!-- <div class="col-md-12">
															<div class="col-md-6">
																<p><b>E-mail</b></p>
															</div>
															<div class="col-md-6">
															<p>	TEST@TEST.COM</p>
															</div>
														</div> -->
														<!-- <div class="col-md-12">
															<div class="col-md-6">
																<p><b>Address</b></p>
															</div>
															<div class="col-md-6">
																<p>	ADDRESS</p>
															</div>
														</div> -->
														<!-- <div class="col-md-12">
															<div class="col-md-6">
																<p><b>City</b></p>
															</div>
															<div class="col-md-6">
																<p>CHENNAI</p>
															</div>
														</div> -->
														<!-- <div class="col-md-12">
															<div class="col-md-6">
																<p><b>State</b></p>
															</div>
															<div class="col-md-6">
																<p>TAMILNADU</p>
															</div>
														</div> -->
														<!-- <div class="col-md-12">
															<div class="col-md-6">
																<p><b>Zip Code</b></p>
															</div>
															<div class="col-md-6">
																<p>	600001</p>
															</div>
														</div> -->
														<!-- <div class="col-md-12">
															<div class="col-md-6">
																<p><b>Country</b></p>
															</div>
															<div class="col-md-6">
																<p> INDIA</p>
															</div>
														</div>  -->
														 <table class="table"">
                                <tr> <td><b> Supplier Name </b></td> <td id="suppliernametdid"> </td> </tr>
                                <tr> <td><b> Mobile Phone  </b></td> <td id="mobiletdid">  </td> </tr> 
                                <tr> <td><b> E-mail  </b></td> <td id="mailidtdid"> </td>  </tr>
                                 <tr> <td><b> Payment Mode  </b></td> <td id="paymodeid"> </td>  </tr>
                                <tr> <td><b> Address  </b></td>  <td id="addresstdid"></td>  </tr>
								<tr> <td><b> City  </b></td>  <td id="citytdid"> </td>  </tr>
								<tr> <td><b> State  </b></td>  <td id="statetdid"></td>  </tr>
								<tr> <td><b> Zip Code  </b></td>  <td id="zipcodetdid"></td>  </tr>
								<tr> <td><b> Country  </b></td>  <td id="countrytdid"></td>  </tr>
                              </table>
														
															<div class="col-md-12">
																
																	<!-- <table id="payables" class="table table-striped table-bordered dt-responsive nowrap" style="width:100%" id="orderedid">
																		
																		<tbody>
																			<tr>
																				<td >Payables </td>
																				<td>Items to be received</td>
																				<td>0</td>
																			</tr>
																			<tr>
																				<td class="stycolor">Rs.0.00</td>
																				<td>Total items ordered</td>
																				<td>0</td>
																			</tr>
																		</tbody>
																	</table>					 -->
																	          <table class="table txn-info-table">
                <tbody>
                    <tr class="receivable-section">
                      <td rowspan="3" class="txn-info-sections">
                          <div id="ember1704" class="ember-view">    <div><strong>Payables</strong></div>
                          <!---->     <div>
                                    <label class="font-xlg text-orange over-flow" id="balanceid"><strong></strong></label> 
                          <!---->    </div>
                          </div>
                      </td>
                      <td colspan="2">
                            <label class="pull-left text-void" ><strong  >Total items ordered</strong></label>
                      <label class="pull-right text-draft font-md"    data-ember-action-1705="1705" id="orderedid"> </label>
                             <!-- <input placeholder="" class="form-control form-white" type="text" id="receivedid"> -->
                      </td>
                    </tr>
                    <tr class="receivable-section">
                        <td colspan="2">
                            <label class="pull-left text-void" ><strong >Total items received</strong></label>
                       <label class="pull-right text-draft font-md"   data-ember-action-1706="1706" id="receivedid"></label> 
                                   <!--<input placeholder="" class="form-control form-white" type="text"id="orderedid">-->
                        </td>
                    </tr>
                   
<!---->                </tbody>
            </table>								
															</div>
															<br>
															<div class="col-md-12">
															  <br>
																<div class="col-md-6">
																	<p><b>CONTACT PERSONS</b><p>
																</div>
																<a href="#" onclick="setnewsupplier();" class="pull-right btnRightHead">
						                                       <i class="fa fa-plus" aria-hidden="true"></i> New 
					                                           </a>
			                                              <!-- <a href="#" data-toggle="modal" data-target="#contact" class="pull-right btnRightHead" id="contactpopup" style="display: none;">
						                                 <i class="fa fa-plus" aria-hidden="true"></i> New  -->
						                                 </a>
															</div>	
															<br>
															<table class="table" id="suppliercontacttable">
                                                                   <tbody class="dynamicdatafromload">
                      <%--  <c:forEach items="${supplierContactList}" var="contact" >
                            <tr>
                              <td>
                                ${contact.prefix} ${contact.contactname}<br>
                                ${contact.email}<br>
                                ${contact.designation}<br>
                                Phone : ${contact.phoneno}<br>
                                
                                <!---->
                              </td>
                              <td>
                                <div class="btn-group pull-right cursor-pointer">
                                  <button data-toggle="dropdown" class="btn btn-default btn-sm dropdown-toggle">
                                    <i class="fa fa-cog" aria-hidden="true"></i><b class="caret"></b>
                                  </button>
                                  <ul class="dropdown-menu">
                                    <li><a  data-ember-action-1926="1926" onclick="setcontact('${contact.iscid}','${contact.supplierid}','${contact.prefix}',
                                    '${contact.contactname}','${contact.email}','${contact.phoneno}','${contact.designation}','UPD')">Edit</a></li>
                                    <!-- <li><a  data-ember-action-1927="1927">Mark as primary</a></li> -->
                                    <li><a  data-ember-action-1928="1928" onclick="deletecontact('${contact.iscid}')">Delete</a>
                                    <a  data-ember-action-1928="1928" id="deletecontactid" style="display: none;"
                                     class="delete myBtnTab" data-toggle="modal" data-target="#modal-delet">Delete</a>
                                    </li>
                              </ul>
                                </div>
                              </td>
                            </tr>
                            </c:forEach>  --%>
                          </tbody>
                        </table>
                        
                        
                        	<div class="modal fade" id="contact"  role="dialog" >
  <div class="modal-dialog topZero modal-curr">
    <div class="modal-content">
      <div class="modal-header">
        
        <h4 class="modal-title">New Contact</h4>
        <button type="button" class="close" data-dismiss="modal">&times;</button>
      </div>
      <div class="modal-body">
        
        <div class="row">
          <div  class="col-sm-4 form-group">
            <label>Name</label>
            <select class="form-control form-white" id="contactprefixselect">
              <option value="Mr."> Mr. </option>
              <option value="Mrs."> Mrs. </option>
			  <option value="Ms."> Ms. </option>
              <option value="Dr."> Dr. </option>
            </select>
          </div>

          <div  class="col-sm-8 form-group">
            <label>Name</label>
            <input placeholder="" class="form-control form-white" type="hidden" id="iscidtext" value="0">
            <input placeholder="" class="form-control form-white" type="hidden" id="oprntext" value="INS">
            <input placeholder="" class="form-control form-white" type="text" id="contactnametext">
          </div>
		  
		  <div  class="col-sm-12 form-group">
            <label>Email</label>
            <input placeholder="" class="form-control form-white" type="text" id="emailtext">
          </div>
		  
		  
		  <div  class="col-sm-12 form-group">
            <label>Contact Phone</label>
            <input placeholder="" type="number" class="form-control form-white" type="text" id="contactphonetext">
          </div>
		  
		  <div  class="col-sm-12 form-group">
            <label>Designation</label>
            <input placeholder="" class="form-control form-white" type="text" id="designationtext">
          </div>
		  
        </div>

      </div>
      <div class="modal-footer alignCenter" align="center ">
        <div class="form-group " align="center">
             <button type="button" class="btn btn-primary " data-dismiss="modal" onclick="savesuppliercontact();">Save</button>
              <button type="button" class="btn btn-danger "  data-dismiss="modal" onclick="clearsuppliercontactfields();">Cancel</button>
        </div>
      </div>
    </div>
  </div>
</div>

													</div>
												</div>
												
												<div id="Purchases" class="tab-pane ">
													<div class="col-md-12 selectbox">
														 <select id="statusselect" class="form-control form-white selectSer" onchange="changestatus(this.value)">
	                                                         <option value="0">All</option>
	                                                         <option value="2">Partially Received</option>
	                                                         <option value="3">Fully Received</option>
	                                                        <option value="9"> Partially Returned</option>
	                                                        <option value="8"> Fully Returned</option>
                                                         </select>
													</div>
													<table class="table table-striped table-bordered dt-responsive nowrap" width="100%" id="purchaseDT">
													  <thead>
														<tr> 	 	      	 		 
															<th>DATE</th>
															<th>ORD NO</th>
															<th>PRODUCT (QTY)</th>
															<th>STATUS</th>
															<th>GRAND TOTAL</th>
															<th>PAID</th>
															<th>BALANCE</th>
															
														</tr>
													  </thead>
														
													</table>
												</div>
												<div id="Payments" class="tab-pane ">
													 <table id="paymenttypeDT" class="table table-striped table-bordered dt-responsive nowrap" width="100%">
													  <thead>
														<tr> 	 	      	 		 
															<th>DATE</th>
															<th>BILL#</th>
															<th>REFERNCE</th>
															<th>AMOUNT</th>
															<th>RECEIVEDBY</th>
															<th>TYPE</th>
															
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
					  <!-- Main content ends here -->
					
				<!-- <div  id="myModal" class="modal fade modalForgetPassword" >
		              <div class="modal-dialog widthModalForget">
		                  <div class="modal-content">
		                        <div class="modal-header">
							      <h4 class="modal-title">NEW CONTACT</h4>
		                          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		                        </div><br>
							    <div class="modal-body">
		                            <div class="col-md-12">
										<div class="col-md-6 form-group">
											<label>Prefix</label>
											<select>
												<option>Mr.</option>
												<option>Mrs.</option>
												<option>Ms.</option>
												<option>Dr.</option>
											</select>
										</div>
										<div class="col-md-6 form-group">
											<label>Name</label>
											<input type="text" placeholder="ref112" class="form-control inpttypestyle"  >
										</div>
									</div>
                                    <div class="col-md-12">
										<div class="col-md-6 form-group">
                                            <label>E-mail</label>
											<input type="text" placeholder="0" class="form-control inpttypestyle"  >
										</div>
                                        <div class="col-md-6 form-group">
                                            <label>Contact Phone</label>
											<input type="text" placeholder="0" class="form-control inpttypestyle"  >
										</div>
									</div>
                                    <div class="col-md-12 form-group">
										
											<label>Desigination</label>
                                            <input type="text" placeholder="0" class="form-control inpttypestyle"  >
                                    </div>
                                    
                                    
		                      </div>
		                      
		                         <div class= "clearfix"> </div>
								
		                      <div class="modal-footer">
		                         
		                                <button type="button" class="btn btn-primary  ">Save</button>
										<button type="button" class="btn btn-danger ">Cancel</button>
		                      </div>
		                  </div>
		              </div>
		          </div> -->
 
<jsp:include page="../template/footer.jsp" />
<script type="text/javascript">


$('.btn.btn-white').on('click',function(){
	savesuppliercontact();
});
</script>
</body>
</html>
