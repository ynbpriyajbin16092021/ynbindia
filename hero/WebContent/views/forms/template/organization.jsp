<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Company Master</title>

	<meta name="description" content="Sufee Admin - HTML5 Admin Template">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="/heroadmin/resources/css/lib/notification/material-dashboard.css?v=1.2.0" rel="stylesheet" />
    <link href='/heroadmin/resources/css/lib/notification/material-icon.css' rel='stylesheet' type='text/css'>
    <link rel="apple-touch-icon" href="apple-icon.png">
    <link rel="shortcut icon" href="favicon.ico">
    <link rel="stylesheet" href="../resources/css/normalize.css">
    <!--   <link rel="stylesheet" href="../resources/css/bootstrap.min.css"> -->
    <link rel="stylesheet" href="../resources/css/bootstrap.3.3.7.min.css">
    <link rel="stylesheet" href="../resources/css/style.css">
    <link rel="stylesheet" href="../resources/css/font-awesome.min.css">
    <link rel="stylesheet" href="../resources/css/themify-icons.css">
    <!-- <link rel="stylesheet" href="../resources/css/flag-icon.min.css">
    <link rel="stylesheet" href="../resources/css/cs-skin-elastic.css"> -->
    <link rel="stylesheet" href="../resources/css/jquery-ui.css">
  
	
	<link rel="stylesheet" href="../resources/css/stylebass.css">
	<link rel="stylesheet" href="../resources/css/stylej.css">

	<!-- <link rel="stylesheet" href="../resources/css/responsive.bootstrap.min.css">
	<link rel="stylesheet" href="../resources/css/dataTables.jqueryui.min.css"> -->
	<link rel="stylesheet" type="text/css" href="//cdn.jsdelivr.net/bootstrap.daterangepicker/2/daterangepicker.css" />
	
	<script src="../js/lib/theme/jquery.3.3.1.min.js"></script>
  	<script src="../js/lib/theme/bootstrap.3.3.7.min.js"></script>

    <script type="text/javascript" src="../js/util/invutil.js"></script>  
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:400,600,700,800" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="../js/lib/notification/bootstrap-notify.js"></script>
    <script type="text/javascript" src="../js/lib/notification/notification.js"></script>
    <script type="text/javascript" src="../js/lib/theme/html5shiv.min.js"></script>
    <!--  <script type="text/javascript" src="/heroadmin/js/util/invutil.js"></script>   -->
	<script type="text/javascript" src="../js/forms/templates/template.js"></script>
	<style>
	body{
	background:url(../resources/images/img1.jpg)no-repeat;
	width:100%;
	background-attachment: fixed;
	    -webkit-background-size: cover;
    font-family: 'Roboto', sans-serif;
    height: 100vh;
	}
	.bgStyle{
	display:block;
	background:#e0e0e0e3;
	border:5px solid #f3f3f3;
	clear:both;
	padding:20px;
	border:5px solid #acced4;
	}
	.padd-orgn{
	padding: 100px 0px;
	}
	.mar-top-15{
	margin-top:15px;
	}
	.color-font{
	color:#0E7BDD;
}
.modal-header{
	background:#0e7bdd !important;
	color:white !important;
	font-size:18px !important;
	padding-top: 13px !important;
}
.modal-title{
	padding-bottom:5px !important;
}
.button-bg, .button-bg:hover, .button-bg:active {
    background-image: linear-gradient(to bottom,#fede4b, #feb521) !important;
    border: 1px solid #feb521 !important;
    color: #896614 !important;
    text-shadow: 0 1px 0 rgba(255, 255, 0, 1);
    font-size: 15px !important;
    font-weight: bold !important;
}
.pull-right {
        padding: 0px 10px;
    margin-right: 12px;
}
.datepicker .datepicker-switch,
.datepicker .prev,
.datepicker .next,
.datepicker tfoot tr th{
  background: #0e7bdd;
  color:white;
}
.datepicker .datepicker-switch:hover,
.datepicker .prev:hover,
.datepicker .next:hover,
.datepicker tfoot tr th:hover {
  background: #0e7bdd;
  color:white;
}
.col-md-2 {
    width: 32.666667%;
}
.width_60{
	width:60%;
	display:block;
	float:left;
}
.width_75{
	width:75%;
	display:block;
	float:left;
}
.width_25{
	width:25%;
	display:block;
	float:left;
}
iframe.hero-theme-iframe{
	border:none;
	height:110px;
}
/* .datepicker {
   
    width: 445px;
} */
	</style>
	<!-- settings for notification -->
	<script>
		var notification_from = "bottom";
		var notification_align = "right";	
	</script>
</head>
<body onload="loadorganization()" class="bg_orgn">

<div  class="col-md-12 padd-orgn">
<div class=" ">

<form method="post" action="fileUpload" enctype="multipart/form-data">


                          <div class="container bgStyle">

                               <div class="col-md-8 form-group">
									<div class="col-md-4">
										<label for="companyname">Organization Name <span style="color:red">*</span></label>
									</div>
									<div class="col-md-8">
										<input class="form-control form-white" placeholder="Company Name" type="text" id="compnametext">
                                        <input class="form-control form-white" placeholder="Company Name" type="hidden" id="oprntext" >
									</div>	
                                </div>
                               
								<div class="col-md-8 form-group">
									<div class="col-md-4">
										<label for="mobilenumber">Mobile Number <span style="color:red">*</span></label>
									</div>
									<div class="col-md-8">
										 <input class="form-control form-white" placeholder="Mobile Number" type="number" id="mobnotext">
									</div>	
                                </div>
								<div class="col-md-8 form-group">
									<div class="col-md-4">
										<label for="emailaddress">Email Address <span style="color:red">*</span></label>
									</div>
									<div class="col-md-8">
										<input class="form-control form-white" placeholder="E-Mail" type="text" id="emailtext">
									</div>	
                                </div>
                                <div class="col-md-8 form-group">
									<div class="col-md-4">
										<label for="accountstartsyear">Accounts  year <span style="color:red">*</span></label>
									</div>
									<div class="col-md-2 " >
										<input class="yearpicker form-control form-white" placeholder="accountstartsyear" type="text" id="startyear">
										</div>
										<div class="col-md-2">
										<input class="yearpicker form-control form-white" placeholder="accountendyear" type="text" id="endyear">
									</div>	
                                </div>
                                
                                 <div class="col-md-8 form-group">
									<div class="col-md-4" >
										<label for="accountstartsyear">Account start Date <span style="color:red">*</span></label>
									</div>
									<div class="col-md-8">
										<input class="datepicker form-control " placeholder="date" type="text" id="startdate" style=" width: 445px;">									</div>	
                                </div>
                                 <div class="col-md-8 form-group">
									<div class="col-md-4" >
										<label for="needstocktransfer">Need stock transfer  <span style="color:red">*</span></label>
									</div>
									<div class="col-md-8">
										 <span class="checkbox-inline akucheckbox-inline"><input type="radio" name="stocktransfertext" value="yes"  > YES</span>
		                                  <span class="checkbox-inline akucheckbox-inline1"><input type="radio" name="stocktransfertext" value="no" checked> NO</span>	
                                   </div>
                                    </div>
								<div class="col-md-8 form-group">
									<div class="col-md-4">
										<label for="Address">Address <span style="color:red">*</span></label>
									</div>
									<div class="col-md-8">
										<textarea class="form-control form-white" placeholder="Address" type="text" rows="3" cols="50" id="orgnaddresstext"></textarea>
									</div>	
                                </div>
								 
                                
                                <div class="col-md-12 form-group">
                                
                                	<div class="width_75">
                                		<div class="col-md-4">
                                		<label >Upload Profile Photo <span style="color:red">*</span></label>
                                		</div>
                                		<div class="col-md-8">
											<iframe src="/heroadmin/forms/heroupload?name=companylogo.jpg&applntype=0&requesttype=1" id="imgpathtext"
										 	class="hero-theme-iframe"></iframe>
										 	<input class="form-control form-white" placeholder="Upload Profile Photo" type="hidden" id="logotext" value="companylogo.jpg">
										</div>
	                                </div>	
									<div class="width_25">
											<img src="/heroadmin/forms/HeroImageView?applntype=0" width="100px;" style="height: 75px;">

									</div>
								</div>
								
								<div class="col-md-12 form-group">
								 	<div class="width_75">
								 	<div class="col-md-4">
                                 		<label>Note</label>
                                 	</div>
										<div class="col-md-8">
											<span>File taken from</span>
											<span id="imgfilepathspan"></span>
											<span>
												This logo will appear on the documents (sales order, purchase order, ...) that you create Preferred Image Size: 240px x 240px @ 72 DPI Maximum size of 1MB.<br>
											</span>
										</div>
									</div>
									</div>
                                
                                 
									
									<div class="col-md-8 form-group" >
                                        <button type="button" class="btn btn-primary " onclick="saveorgn()">Register</button>
										<button type="button" class="btn btn-danger " onclick="return reset_orgn();">Cancel</button>
										<!-- <a  href="login" class="btn btn-primary " >Back</a><br> -->
			                    </div>
								
						  </div>
						  
</form>	
                        </div>
                        </div>    
                        <div class="modal fade" id="alertModalMsg-hero" tabindex="-1" role="dialog" aria-hidden="true">
						<div class="modal-dialog">
						  <div class="modal-content">
						    <div class="modal-body">
						      <div class="popDel" align="center">
						        <label><h3 class="color-font">Info!</h3></label>
						        
						        <p id="alertModalMsg"></p>
						        
						      </div>
						    </div>
						    <div class="modal-footer alignCenter" align="center ">
						     <!--  <button type="button" class="btn button-bg button-space " data-dismiss="modal">Ok</button> -->
						     <a  href="login" class="button-bg button-space pull-right mar-bot-15" >OK</a><br>
						    </div>
						  </div>
						</div>
						</div> 


  <script type="text/javascript" src="/heroadmin/js/lib/theme/bootstrap-datepicker.js"></script>
	<link rel="stylesheet" type="text/css" href="/heroadmin/resources/css/bootstrap-datepicker.css" />
	
	
   <script type="text/javascript">
	var datePickerOptions = {
			format: 'dd/mm/yyyy',autoclose: true
		}
	 $(document).ready(function () {
		    $(".datepicker").datepicker(datePickerOptions);
		 });
	
	$(".yearpicker").datepicker( {
	    format: " yyyy", // Notice the Extra space at the beginning
	    viewMode: "years", 
	    minViewMode: "years"
	});
     
     
    </script>
	                 		  
				  
</body>
</html>
					
					 