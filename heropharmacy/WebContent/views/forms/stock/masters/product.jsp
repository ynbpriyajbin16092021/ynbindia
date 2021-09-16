<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Product Master</title>
<%if(request.getParameter("disp") == null){ %>
<jsp:include page="../../template/library.jsp" /> 
<%} %>	
<script type="text/javascript" src="../js/forms/masters/masters.js"></script>
</head>
<body onload="loadproduct();">

<%if(request.getParameter("disp") == null){ %>
  	<jsp:include page="../../template/header.jsp" />
<%} %>	  
				 
					<div class="clearfix"></div>
					<div class="breadcrumbs"  style="display: <%= request.getParameter("disp") %>">
			            <div class="col-md-4 col-sm-4">
			                <div class="page-header">
			                        <p class="cursor-pointer mar-top-10 bread-a-style head-font-size yellow-font" href="stockdashboard"><strong><i class="fa fa-lightbulb-o"></i> Product Master</strong></p>
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
						<div class="col-md-6 overcome-col-padd-10">
							<div class="width_100">
								<!-- <div class="dashboard-ld-header margin-bottom-10 head-font-size white-font color-bg">
									<p><span style="font-size:18px;font-weight:bold"></span> Category </p>
								</div> -->
								<div class="width_100 gray-font white-bg content-font-size content-div-height">
									<div class="col-md-12 full-padding-20">
									<table id="productDT" class="hero-settings-table" style="width:100%">
											<thead>
												<tr>
													<th>Product Name</th>
													<!-- <th>SKU</th>
													<th>UOM</th>
													<th>Manufacturer</th>
													<th>category</th> -->
													<!-- <th>Alert Quantity</th> -->
													<th>Rate</th>
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
							<div class="width_100">
								<div class="width_100 gray-font white-bg content-font-size content-div-heightnw" id="style-5">
									<div class="col-md-12 full-padding-20">
									<div class="width_100">
											
										<a class="button-bg button-space pull-right mar-bot-15 mar" href="importproduct"><i class="fa fa-download"></i> Import Product</a>
										<button class="button-bg button-space pull-right mar-bot-15 mar" onclick="clearproductfields()">New <i class="fa fa-plus-circle"></i></button>
										
											
										</div>
										<div class="clearfix"></div>
									<div class="margin-bottom-10 content-font-size  white-font color-bg">
											<p class="padd-left-right-5"> product </p>
										</div>
									<div class="col-md-12 form-group">
									  <label for="SKU">Product Code <span style="color:red">*</span></label>
								    <input type="text" class="form-control inpttypestyle" id="productcodetext" value="${random_int}" readonly  >
									</div>
									<div class="col-md-12 form-group">
									  <label for="Manufacturer">Product Name <span style="color:red">*</span></label>
									  <input class="form-control form-white" type="text" id="productnametext">
                        				<input class="form-control form-white" type="hidden" id="productidtext" value="0">
                        				<input class="form-control form-white" type="hidden" id="oprntext" value="INS">
									</div>
									
									<div class="col-md-12 form-group">
									     <label for="Manufacturer">HSNCODE </label>
									     <input class="form-control form-white" type="text" id="hsncodetext">
                        				
									</div>
									
									<div class="col-md-12 form-group">
									     <label for="Manufacturer">Unit Rate <span style="color:red">*</span></label>
									     <input class="form-control form-white" type="number" id="productratetext">
                        				
									</div>
									
									<div class="col-md-12 form-group">
									  <label for="producttype">Product Type <span style="color:red">*</span></label>
								    <select id='categoryselect' class="form-control form-white">
								    	<option value="">-- select --</option>
									    <c:forEach items="${categoryList}" var="category" >                  
									        <option value="${category.value}">
									            ${category.label}
									        </option>                    
									    </c:forEach>
									</select>
									</div>
									
									<div class="col-md-12 form-group">
									  <label for="Manufacturer">Manufacturer <span style="color:red">*</span></label>
									   <select id='manufactureselect' class="form-control form-white">
										<option value="">-- select --</option>               
										    <c:forEach items="${manufactureList}" var="manufacture" >                  
										        <option value="${manufacture.value}">
										            ${manufacture.label}
										        </option>                    
										    </c:forEach>
										</select>
									   
									</div>

									 <div class="col-md-12 form-group">
									 
									  <label for="Unit of Measure" >UOM <span style="color:red">*</span></label>
									  <input id="unitqtytext" value="0" class="akform-control1 form-white" type="number" >
									<select id='uomselect' class="akform-controlpp akuom-align form-white" style="width:24%;margin-left:5px;">
									<option value="">-- select --</option>
									    <c:forEach items="${uomList}" var="uom" >                  
									        <option value="${uom.value}">
									            ${uom.label}
									        </option>                    
									    </c:forEach>
									</select>  
								    
								    </div>
									

								     <input type="hidden" id="barcode" class="form-control form-white" value="${random_int}" />
									
									
									<div class="col-md-12 margin-bottom-15">
									  <label class="hero-label" for="Notify Below Quantity">Notify Below Quantity</label>
									  <div class="width_60">
									  	<div class="notify-checkbox-style">
									   		<input type="checkbox"   id="notifiycheck"  onclick="checknotifyqty();">  
								     	</div>
								     	<div class="notify-input-style">
								     		<input id="notifyqtytext" readonly="readonly" value="0" name="appendedcheckbox" class=""  type="number" >
                            				<input id="notifyqtycopytext" readonly="readonly" value="0" name="appendedcheckbox" class="" type="hidden" >
                            		 	</div>
                            		 </div>
								 </div>
								 
								 
				<!-- <div class="col-md-12 form-group">
                     <label class="col-sm-4 control-label">Tax Type</label>
                      
                           <div class="col-md-8 form-group">
					             
		                        <div class="col-sm-12" id="typeselect">
					               
                                     <span class="checkbox-inline akucheckbox-inline"><input type="radio" name="gendertext" value="Default"  > Default</span>
		                             <span class="checkbox-inline akucheckbox-inline1"><input type="radio" name="gendertext" value="Custom" checked> Custom</span>
                                            
					           </div>
                         </div> 
                </div>	     -->
                
                <div class="col-md-12 form-group"  id="typeselect" style="display:none">
									 
					<label for="Tax" >Tax Type </label>
									 
					<!-- <div class="col-md-12 form-group" id="typeselect"> -->
					               
                               <span class="checkbox-inline akucheckbox-inline"><input type="radio" name="gendertext" value="Default"  > Default</span>
		                       <span class="checkbox-inline akucheckbox-inline1"><input type="radio" name="gendertext" value="Custom" checked> Custom</span>
                                            
					
								    
				</div>
                
                
                <div class="col-md-12 form-group">
				     <div class="control-label" id="Default" class="colors " style="display:none"> 
                           <!-- <div class="moreTax"> -->
           						<label class="col-sm-4 control-label">Select CGST %</label>
							            <div class="col-sm-6"> 
							              		  
							              			<select id='cgst'  class="form-control form-white">
                                                           <option value="0" > -- Select CGST Tax --</option>
                                                           <c:forEach items="${taxList}" var="taxtype" > 
                                                           <option value="${taxtype.value}"> ${taxtype.label} </option>                    
                                                           </c:forEach>
                                                   </select>
		                                  </div>		
		                                  
		                                  					                  
									      <div class="col-md-12 form-group" id='SGST' >
						                     <label class="col-sm-4 control-label">Select SGST %</label>		
						                            <div class="col-sm-6"> 
													   <select id='sgst' class="form-control form-white">
						                                    <option value="0" > -- Select SGST Tax --</option>
						                                    <c:forEach items="${taxList}" var="taxtype" > 
						                                    <option value="${taxtype.value}"> ${taxtype.label} </option>                    
						                                    </c:forEach>
						                               </select>
								                     </div>	
						                    </div>	
						                  <!--   </div>	                -->
			                     </div> 
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
								 
								 
									<div class="col-md-12 form-group">
									  <label for="product Details"  >Product Details</label><br>
								     <textarea class="form-control" id="descriptiontext"></textarea>
									</div>
									
									<div class="col-md-12">
									<div class="col-md-12 permissionDiv">
		                         			<button data-dismiss="modal" class="btn btn-primary" type="button" onclick="saveproduct()">Save</button>
								     		<!-- <button data-dismiss="modal" class="btn btn-default" type="button"  onclick="clearCategoryFormValues();">Clear</button> -->
								     		</div>
		                      			</div>
									
									</div>
									
									
									
									
									
									
									
									<!-- <p class="yellow-font head-font-size">Category View</p>
									<p class="gray-font content-font-size">Category<span class="pull-right yellow-font"> Tablet</span></p> -->
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
				
				  <script type="text/javascript">
                  $( document ).ready(function() {
            	  $("#typeselect input[name='gendertext']").on("change",function(){
            		  var id = ($(this).val());
            			  if (id == "Default")
				            {
                  	            $("#Default").css({"display":"block"});
                  	            $("#SGST").css({"display":"block"});
				   
				            }
            			  else
            				  {
            				    $("#Default ").css({"display":"none"});
            				    $("#SGST ").css({"display":"none"});
            				  }
            	});
             });
                     
              </script>
	
<style>
	.button-space {
	margin-right:5px;
	}
	.button-height{
	height:28px;
	}

	</style>
<jsp:include page="../../template/footer.jsp" /> 

</body>
</html>

