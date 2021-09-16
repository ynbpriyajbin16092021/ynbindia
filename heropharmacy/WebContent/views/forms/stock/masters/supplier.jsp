<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Supplier Master</title>
<%if(request.getParameter("disp") == null){ %>
<jsp:include page="../../template/library.jsp" /> 
<%} %>	
<script type="text/javascript" src="../js/forms/masters/masters.js"></script>
</head>
<body onload="loadsupplier();">

<%if(request.getParameter("disp") == null){ %>
  	<jsp:include page="../../template/header.jsp" />
<%} %>	  
				 
					<div class="clearfix"></div>
					<div class="breadcrumbs"  style="display: <%= request.getParameter("disp") %>">
			            <div class="col-md-4 col-sm-4">
			                <div class="page-header">
			                        
 								<p class="cursor-pointer mar-top-10 bread-a-style head-font-size yellow-font" href="stockdashboard"><strong><i class="fa fa-users"></i>  Supplier Master</strong></p>
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

								<div class="width_100 gray-font white-bg content-font-size content-div-height">
									<div class="col-md-12 full-padding-20">
									<table id="supplierDT" class="hero-settings-table" >
										<thead>
												<tr>
												
													<th>Supplier</th>
													<!-- <th>Type</th> -->
													<th>Phone</th>
													<!-- <th>Email</th>
													<th>city</th> -->
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
											<button class="button-bg button-space pull-right mar-bot-15 mar" onclick="clearSupplierFields()">New <i class="fa fa-plus-circle"></i></button>
										</div>
										<div class="clearfix"></div>
									<div class="margin-bottom-10 content-font-size  white-font color-bg">
											<p class="padd-left-right-5">supplier Name </p>
										</div>
									
									<div id="masterCollapse" >
									<div class="col-md-12 form-group">
										      <label for="Manufacturer Company Name" class="hero-label">Supplier Company Name <span style="color:red">*</span></label>
									    <!--  <div class="select-honor">
											  <select class=" form-white" id="supplierinisselect">
											    <option value="Mr">Mr.</option>
											    <option value="Mrs">Mrs.</option>
												<option value="Ms">Ms.</option>
											  </select>
											</div> -->
									     
										   		<input class="form-control form-white" placeholder="firstname"  type="text" id="supplierfirstnametext">
										 
										  <!--  <div class="lastname-input">
										   		<input class="form-control" placeholder="lastname" type="text" id="supplierlastnametext">
										   </div>  -->
					                  		<input class="form-control form-white"  type="hidden" id="supplieridtext">
					                  		<input class="form-control form-white"  type="hidden" id="oprntext" value="INS">
                  					</div>
									</div>
									<div class="col-md-12 form-group">
									<label for="SupplierType">Supplier Type <span style="color:red">*</span></label>
								      <select id='suppliertypeselect' class="form-control form-white">
									    <c:forEach items="${suppliertypeList}" var="suppliertype" >                  
									        <option value="${suppliertype.value}">
									            ${suppliertype.label}
									        </option>                    
									    </c:forEach>
									</select>
                                    </div>	
									<div class="col-md-12 form-group">
									   <label for="Manufacturer Company Name">Mobile Number <span style="color:red" >*</span></label>
									  <input class="form-control" type="number"  type="text" id="suppliermobnotext">
									</div>
									
									<div class="col-md-12 form-group">
									   <label for="Manufacturer Company Name">Telephone</label>
									  <input class="form-control form-white" type="number" type="text" id="suppliertelnotext">
									</div>
									
								    <div class="col-md-12 form-group">
									   <label for="Manufacturer Company Name">Email ID <span style="color:red">*</span></label>
									  <input class="form-control form-white"  type="text" id="supplieremailtext">
									</div>
									<div class="col-md-12 form-group">
									   <label for="Manufacturer Company Name">City <span style="color:red">*</span></label>
									  <input class="form-control form-white" id="suppliercitytext" type="text"> 
									</div>
									
									<div class="col-md-12 form-group">
									   <label for="Manufacturer Company Name">State <span style="color:red">*</span></label>
									  <input class="form-control form-white"  id="supplierstatetext" type="text"> 
									</div>
									
									<div class="col-md-12 form-group">
									   <label for="Manufacturer Company Name">Zipcode <span style="color:red">*</span></label>
									  <input class="form-control form-white" type="number" type="text" id="supplierzipcodetext"> 
									</div>
									
									<div class="col-md-12 form-group">
									   <label for="Manufacturer Company Name">Country <span style="color:red"></span></label>
									  <input class="form-control form-white"  id="suppliercountrytext" type="text"> 
									</div>

									<div class="col-md-12 form-group">
									   <label for="Manufacturer Company Name">GST NO <span style="color:red">*</span></label>
									  <input class="form-control form-white"  id="suppliertinnotext" type="text"> 
									</div>
									
									 
						<div class="col-md-12 form-group">
                     <label class="col-sm-4 control-label">Credit in Advance</label>
                	<div class="col-sm-8 creditinadvancediv">
                  		
						  <label><input type="checkbox" name="creditinadvancecheckbox" id="creditinadvancecheckbox" value="yes"></label>
						<script>
						$(document).ready(function(){
							$(".creditinadvancediv input[type='checkbox']").on("change", function(){
								if(this.checked){
									$("#creditinadvance_reqdays").css({'display':'block'});
								}else{
									$("#creditinadvance_reqdays").css({'display':'none'});
								}
								
							});
						});
						</script>
						<div id="creditinadvance_reqdays" style="display:none"> Required Days 
                                                   <input class="form-control form-white"  id="requireddaystext" type="text">
                                             </div>
               		</div>
        </div>		 
				
                     
                      
                          <div class="col-md-12 form-group" id="typeselect">
                               <label class="col-sm-4 control-label">Terms of Payment</label>
					               
                                     <span class="checkbox-inline akucheckbox-inline"><input type="radio" name="gendertext" value="Default"> Default</span>
		                             <span class="checkbox-inline akucheckbox-inline1"><input type="radio" name="gendertext" value="Custom" checked> Custom</span>
                               
                                                
                                             <div class="col-sm-6 form-group" id="Default" class="colors " style="display:none"> 
                                                  <select id='paymenttypeselect' class="form-control form-white">
						                          <option value="" disabled selected>Choose your option</option>
                                                  <option value="1">Cash</option>
                                                  <option value="2">Credit Card</option>
                                                  <option value="3">DebitCard</option>
                                                  <option value="4">Cheque</option> 
                                                <!--   <option value="5">Credit</option> -->
                                                  </select>
           
                                             </div>
                                             
                                       
			                     </div> 
					  
              
									<div class="col-md-12 form-group">
									   <label for="Manufacturer Company Name">Address <span style="color:red">*</span></label>
									  <textarea  class="form-control form-white" id="supplieraddresstext"> </textarea> 
									</div>
									
							<!-- 		<div class="col-md-12 form-group">
									   <label for="Manufacturer Company Name">Terms & Conditions</label>
									 
									</div>
									<div class="col-md-12 form-group">
									
									  <textarea  class="form-control form-white" id="suppliertermstext" style="width:100%"> </textarea> 
									</div> -->
																		
									<div class="col-md-12 margin-top-bottom-10">
									<div class="col-md-12 permissionDiv">
		                         			<button data-dismiss="modal" class="btn btn-primary" type="button" onclick="savesupplier()">Save</button>
								     		<button data-dismiss="modal" class="btn btn-default" type="button" >Clear</button>
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
                  	            if($("#Default select").val("5")){
                  	              $("#5 ").css({"display":"block"});
                  	             
                  	            }else{
                  	            	$("#5 ").css({"display":"none"});
                  	            }
				   
				            }
            			  else
            				  {
            				    $("#Default ").css({"display":"none"});
            				    $("#5 ").css({"display":"none"});
            				  }
            	});
             });
                     
              </script>
              
              <script>
$('#paymenttypeselect').change(function() {
    $('.colors').hide();
    $('#' + $(this).val()).show();
});	
</script>
				
	
<jsp:include page="../../template/footer.jsp" /> 

</body>
</html>

