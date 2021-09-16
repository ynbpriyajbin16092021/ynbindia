<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Expiry Date Checker</title>
<script type="text/javascript" src="../js/forms/tools/expirydatechecker.js"></script>
</head>
<body onload="loadexpirydatechecker()">

<jsp:include page="../template/header.jsp" />
<div class="card">
										<div class="card-header">
											<strong class="card-title"> <i class="fa fa-exclamation-circle"></i> Expiry Checker</strong>
										</div>
		                                     <div class="card-body">
												<div id="pay-invoice">
			                                        
	                                                     <div class="col-md-12">	
																<div class="col-md-4 ">
																	<div class="col-md-2">
																		<label>   Filter: </label>
																	</div>
																	  <div class="col-sm-3">
        <select class="form-control form-white selectNor" id="storeselect" onchange="productdetails(this.value)">
        
          <c:forEach items="${storeList}" var="store" >                  
        <option value="${store.value}">
            ${store.label}
        </option>                    
    </c:forEach>
        </select>
       </div>
       
																	<!-- <div class="col-md-10 ">
																		 <select id="reportsstoreidselect" class="form-control form-white selectSer select2-hidden-accessible" tabindex="-1" aria-hidden="true">
																			   <option value="1">India Store</option>                    
																			   <option value="2">Dubai Store</option>                    
																			   <option value="4">Singapore</option>                    
																			   <option value="5">Chennai Store</option>                    
																			   <option value="11">Madurai medical</option>                    
																			   <option value="14">Kerala store</option>                    
																			   <option value="15">Kochin shop</option>                    
																			   <option value="16">Gayu shop</option>                    
																			   <option value="18">Saistore</option>                    
																		  </select>
																		  <span class="select2 select2-container select2-container--default select2-container--below select2-container--focus" dir="ltr" style="width: 218px;"><span class="selection"><span class="select2-selection select2-selection--single" role="combobox" aria-haspopup="true" aria-expanded="false" tabindex="0" aria-labelledby="select2-reportsstoreidselect-container"><span class="select2-selection__rendered" id="select2-reportsstoreidselect-container" title="INDIA STORE"></span><span class="select2-selection__arrow" role="presentation"><b role="presentation"></b></span></span></span><span class="dropdown-wrapper" aria-hidden="true"></span></span>
																	</div> -->
																</div>
											                    <div class="col-md-4 " id="sarsid">
																	 <div class="col-md-6 form-group">
																		  <input type="radio" id="radio1" value="expiringalready" onclick="checkradio(this.value)" name="expiry"   checked  />Expired already
																	 </div>			
																	 <div class="col-md-6 form-group"  >
																		 <input type="radio"  id="radio2" onclick="checkradio(this.value)"value="expiringin" name="expiry"> Expiring in 
																		  
                                                                     </div>
																</div>
																<div class="col-md-4 ">
																	 <div class="col-md-6 ">
																	      <input type="number" id="expireddaytext" class="form-control inpttypestyle" readonly>
																	 </div>
																    <div class="col-md-3 ">
																	    <p>date</p>
																	</div>
																	<div class="col-md-3 ">
																         <button type="button"  class="btn btn-default"  onclick="onchangefields()" >Search</button>
																	</div>
															   </div>
															</div>
														
															<div class="col-md-12" id="expirydateDTdiv">
									                              <table id="expirydateDT" class="table table-striped table-bordered dt-responsive nowrap example" style="width:100%">
																			<thead>
																				<th>Product </th>
																				<th>Manufacturer</th>
																				<th>Batch No</th>
																				<th>Expiry Date</th>
																				<th>Expiry Count</th>	
																			 </thead>
																			<!-- <tbody>
																				<tr>
																					<td>Paracitamal</td>
																					<td>Herbzalive</td>
																					<td>batch 002</td>
																					<td>19/4/18</td>
																					<td>10</td>
																				</tr>
																				<tr>
																					<td>Paracitamal</td>
																					<td>Herbzalive</td>
																					<td>batch 001</td>
																					<td>17/5/18</td>
																					<td>15</td>
																				</tr>
																			</tbody> -->
																	</table>
															</div>
													</div>   
												</div>
											</div>
	<script type="text/javascript">
	$(function() {

		var start = moment().subtract(29, 'days');
		var end = moment();

		function cb(start, end) {
			$('#reportrange span').html(start.format('MMMM D, YYYY') + ' - ' + end.format('MMMM D, YYYY'));
		}

		$('#reportrange').daterangepicker({
			startDate: start,
			endDate: end,
			ranges: {
			   'Today': [moment(), moment()],
			   'Yesterday': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
			   'Last 7 Days': [moment().subtract(6, 'days'), moment()],
			   'Last 30 Days': [moment().subtract(29, 'days'), moment()],
			   'This Month': [moment().startOf('month'), moment().endOf('month')],
			   'Last Month': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
			}
		}, cb);

		cb(start, end);
		
	});
	
	</script>
	
	<script type="text/javascript">
	    $(document).ready(function(){
		$("#sarsid input[type='radio']").change(function(){
			var status = this.value;
			if(status =="expiringalready"){
				$("#inputexpirydays").attr('readonly', true);
			}
			else{
				$("#inputexpirydays").attr('readonly', false);
			}
		});
	});

	</script>
	<jsp:include page="../template/footer.jsp" />
</body>
</html>