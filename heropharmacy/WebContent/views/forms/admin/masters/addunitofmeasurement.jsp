
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>UOM Settings</title>
<%if(request.getParameter("disp") == null){ %>
<jsp:include page="../../template/library.jsp" /> 
<%} %>	
<script type="text/javascript" src="../js/forms/masters/masters.js"></script>
</head>
<body onload="loadaddnewuom();loadadduomsetting();">
<%if(request.getParameter("disp") == null){ String applnid = request.getParameter("applnid");%>
  	<jsp:include page="../../template/header.jsp" />
  	<input type="hidden" name="applnid" id="applnidli" value="${applnid }" />		
<%} else if(request.getParameter("disp").equals("n")){ String applnid = request.getParameter("applnid"); %>
	<script type="text/javascript">
	loadaddnewuom();loadadduomsetting();
	</script>	
	<input type="hidden" name="applnid" id="applnidli" value="${applnid }" />	
<%} %>
<%if(request.getParameter("disp") == null){ %>
  	<jsp:include page="../../template/header.jsp" />
<%} %>	  
				 
					<div class="clearfix"></div>
					<div class="breadcrumbs"  style="display: <%= request.getParameter("disp") %>">
			            <div class="col-md-4 col-sm-4">
			                <div class="page-header">
			                        
			                        <p class="cursor-pointer mar-top-10 bread-a-style head-font-size yellow-font" href="stockdashboard"><strong><i class="fa fa-users"></i>  UOM Settings</strong></p>
			                </div>
			            </div>
			            
			        </div>
					<div class="clearfix"></div>
					
					<ul class="nav nav-tabs">
								  <li class="active"><a data-toggle="tab" href="#uom_master"><strong>UOM Master</strong></a></li>
								  <li><a data-toggle="tab" href="#uom_settings"><strong>UOM Settings</strong></a></li>
								</ul>
								
					<div class="tab-content akform">
					<div id="uom_master" class="tab-pane fade in active pill-space">
					<div class="col-md-12 overcome-col-padd-10">
						<div class="col-md-6 overcome-col-padd-10">
							<div class="width_100">

								<div class="width_100 gray-font white-bg content-font-size content-div-height">
									<div class="col-md-12 full-padding-20">
									<table id="uomDT" class="hero-settings-table" >
										<thead>
												<tr>
												
													<th>UOM Desc</th>
													<th>UOM Code</th>
													<th>UOM Type</th>
													<th>Actions</th>
												</tr>
											</thead>
									</table>
								</div>
								</div>
							</div>
						</div>
						
							<div class="col-md-6 overcome-col-padd-10">
							<div class="width_100">
								<div class="width_100 gray-font white-bg content-font-size content-div-heightnw" id="style-5">
									<div class="col-md-12 full-padding-20">
										
										<div class="clearfix"></div>
									<div class="margin-bottom-10 content-font-size  white-font color-bg">
											<p class="padd-left-right-5">UOM Creation </p>
										</div>
									
									<div class="col-md-12 form-group">
									<label for="SupplierType">UOM Desc.</label>
								     <input class="form-control" placeholder="Ex: Container"  type="text" id="uomdesctext">
								     
								     <input class="form-control form-white"  type="hidden" id="uomidtext" value="0">
					                  		<input class="form-control form-white"  type="hidden" id="oprntext" value="INS">
                                    </div>
                                    
									<div class="col-md-12 form-group">
									<label for="SupplierType">UOM Code</label>
								      <input class="form-control" placeholder="Ex: CTR"  type="text" id="uomcodetext" maxlength="3">
                                    </div>	
									<div class="col-md-12 form-group">
									   <label for="Manufacturer Company Name">UOM Type <span style="color:red">*</span></label>
									 <select id="uomtypesel" class="form-control">
									 <option value="0">Full</option>
									 <option value="1">Loose</option>
									 </select>
									</div>
														
									<div class="col-md-12 margin-top-bottom-10">
									<div class="col-md-12 permissionDiv">
		                         			<button data-dismiss="modal" class="btn btn-primary" type="button" onclick="saveuom()">Save</button>
								     		<button data-dismiss="modal" class="btn btn-default" type="button" onclick="clearUOMFields();">Clear</button>
								     		</div>
		                      			</div>
									<!-- <p class="yellow-font head-font-size">Category View</p>
									<p class="gray-font content-font-size">Category<span class="pull-right yellow-font"> Tablet</span></p> -->
								
							
					</div>
					</div>
					</div>
					</div>
					</div>
					</div>
					
					<div id="uom_settings" class="tab-pane fade  pill-space ">
					
					<div class="col-md-12 overcome-col-padd-10">
						<div class="col-md-6 overcome-col-padd-10">
							<div class="width_100">
                   
								<div class="width_100 gray-font white-bg content-font-size content-div-height">
									<div class="col-md-12 full-padding-20">
									<table id="uomsettingDT" class="hero-settings-table" style="width:100%" >
										<thead>
												<tr>
												
													<th>UOM Setting Desc.</th>
													<th>Status</th>
													<th>Actions</th>
												</tr>
											</thead>
									</table>
								</div>
								</div>
							</div>
						</div>
						
							<div class="col-md-6 overcome-col-padd-10">
							<div class="width_100"  id="uomsettingsdivid">
								<div class="width_100 gray-font white-bg content-font-size content-div-heightnw" id="style-5">
									<div class="col-md-12 full-padding-20">
										 
										<div class="clearfix"></div>
									<div class="margin-bottom-10 content-font-size  white-font color-bg">
											<p class="padd-left-right-5">UOM Setting Creation </p>
										</div>
									
									<div class="col-md-12 form-group">
									<label for="SupplierType">UOM Setting.</label>
								     <input class="form-control" placeholder="Ex: Container Setting"  type="text" id="uomsettingdesctext">
								     
								     <input class="form-control form-white"  type="hidden" id="uomsettingidtext" value="0">
					                  		<input class="form-control form-white"  type="hidden" id="uomsettingoprntext" value="INS">
					                  		<input class="form-control form-white"  type="hidden" id="uomconfigsettingidtext" value="0">
                                    </div>
                                    
								  <div class="col-md-12 akrp">
			                                       		<label class="hero-label">Status</label>
			                                        		<div class="akpcol-sm-8">
			                                                  	<div class="onoffswitch">
			                                                       	<input name="onoffswitch" class="onoffswitch-checkbox" id="myonoffswitch1" checked="" type="checkbox">
			                                                          	<label class="onoffswitch-label" for="myonoffswitch1">
			                                                            	<span class="onoffswitch-inner">
			                                                                 	<span class="onoffswitch-active"><span class="onoffswitch-switch">ON</span></span>
			                                                                    <span class="onoffswitch-inactive"><span class="onoffswitch-switch">OFF</span></span> 
			                      											</span>
			                                                         	</label>
			                      	                         	 </div>
			                                         		</div> 
											</div>
														
									<div class="col-md-12 margin-top-bottom-10">
									<div class="col-md-12 permissionDiv">
		                         			<button data-dismiss="modal" class="btn btn-primary" type="button" onclick="saveuomsetting()">Save</button>
								     		<button data-dismiss="modal" class="btn btn-default" type="button"  onclick="clearUOMSettings()">Clear</button>
								     		</div>
		                      			</div>
									<!-- <p class="yellow-font head-font-size">Category View</p>
									<p class="gray-font content-font-size">Category<span class="pull-right yellow-font"> Tablet</span></p> -->
								
							
					</div>
					</div>
					</div>
					
					
					<div class="width_100"  id="adduomsettingsdivid" style="display: none;">
								<div class="width_100 gray-font white-bg content-font-size content-div-heightnw" id="style-5">
									<div class="col-md-12 full-padding-20">
										<div class="width_100">
											<button class="button-bg button-space pull-right mar-bot-15 mar" onclick="adduom();" id="uomaddbtn">Add <i class="fa fa-plus-circle"></i></button>
											<button class="button-bg button-space pull-right mar-bot-15 mar" onclick="saveuomconfig();" style="display:none;" id="uomsavebtn">Save 
											<i class="fa fa-check"></i></button>
										</div>
										<div class="clearfix"></div>
									<div class="margin-bottom-10 content-font-size  white-font color-bg">
											<p class="padd-left-right-5">UOM Setting Configuration </p>
										</div>
									
									<div class="width_100 margin-top-10">
							<div class="width_100">

								<div class="width_100 gray-font white-bg content-font-size content-div-height">
									<div class="width_100">
									
									<table id="adduomsettingDT" class="hero-settings-table" >
										<thead>
												<tr>
												
												    <th>S.No.</th>
													<th>Full UOM</th>
													<th>=</th>
													<th>Loose Qty</th>
													<th>Loose UOM</th>
													<th>Actions</th>
												</tr>
											</thead>
											<tbody></tbody>
									</table>
								</div>
								</div>
							</div>
						</div>
                                    	
                                   
									 <!--<p class="yellow-font head-font-size">Save button will be enabled once loose UOM type is selected</p>
									<p class="gray-font content-font-size">Category<span class="pull-right yellow-font"> Tablet</span></p> -->
								
							
					</div>
					</div>
					</div>
					
					</div>
					</div>
					
					</div>
					</div>
					
				<script>
				$(document).ready(function() {
				    $('.hero-settings-table').DataTable( {
				    	bFilter: false, bInfo: false
				    } );
				} );
				</script>
	
<jsp:include page="../../template/footer.jsp" /> 

</body>
</html>

