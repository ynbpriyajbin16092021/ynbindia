<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
	<link rel="icon" href="/heroadmin/resources/images/logos/favicon.png" type="image/png" sizes="16x16">
	
    <link rel="stylesheet" href="/heroadmin/resources/css/normalize.css">
    <link rel="stylesheet" href="/heroadmin/resources/css/bootstrap.3.3.7.min.css">
    <link rel="stylesheet" href="/heroadmin/resources/css/style.css">

    <link rel="stylesheet" href="/heroadmin/resources/css/font-awesome.min.css">
    <link rel="stylesheet" href="/heroadmin/resources/css/themify-icons.css">
    <link rel="stylesheet" href="/heroadmin/resources/css/jquery-ui.css"> 
    

    	
 
    <!-- notificaton css -->
    <link href="/heroadmin/resources/css/lib/notification/material-dashboard.css?v=1.2.0" rel="stylesheet" />
    <link href='/heroadmin/resources/css/lib/notification/material-icon.css' rel='stylesheet' type='text/css'>
	
	<link rel="stylesheet" type="text/css" href="/heroadmin/resources/css/bootstrap-datepicker.css" />
	<link rel="stylesheet" type="text/css" href="/heroadmin/resources/css/lib/datatable/finalDatatable.min.css" />
	

	<link rel="stylesheet" type="text/css" href="/heroadmin/resources/css/daterangepicker.css" />
	<link rel="stylesheet" type="text/css" href="/heroadmin/resources/css/template/ak.css" />
	
	<script type="text/javascript" src="/heroadmin/js/lib/theme/jquery.3.3.1.min.js"></script>
  	<script type="text/javascript" src="/heroadmin/js/lib/theme/bootstrap.3.3.7.min.js"></script>
    <script type="text/javascript" src="/heroadmin/js/util/invutil.js"></script>  
    <script type="text/javascript" src="/heroadmin/js/lib/theme/html5shiv.min.js"></script>
    
	<script type="text/javascript" src="/hero/js/lib/theme/header.js"></script>
	
	<link rel="stylesheet" type="text/css" href="/heroadmin/resources/css/myfinal.css">
	<link rel="stylesheet" type="text/css" href="/heroadmin/resources/css/hero-theme.css" >
	<link rel="stylesheet" type="text/css" href="/heroadmin/resources/css/lib/clockpicker.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="/heroadmin/js/lib/theme/clockpicker.js" type="text/javascript"></script>
	<!-- <link href="/heroadmin/resources/css/lib/mdb.css" rel="stylesheet" type="text/css"> -->

	<script src="/heroadmin/js/lib/theme/jquery.easyPaginate.js" type="text/javascript"></script>
	
	<!-- <script src="/heroadmin/js/lib/theme/mdb.min.js" type="text/javascript"></script> -->


	
	<!-- settings for notification -->
	<script>
		var notification_from = "bottom";
		var notification_align = "right";	
		var url = "";
	</script>
	
	<!-- permission to create transacction scripts -->
	<script type="text/javascript">
                          $(document).ready(function(){
                        	  var userType = '<%=session.getAttribute("usertype")%>';
                        		if(userType == 1){
                        			//console.log("permission not granted");
                        			$('.permissionDiv').css({'display':'none'});
                        		}
                        	 
                          });
	</script>
	
	
	<script type="text/javascript">
	var datePickerOptions = {
			format: 'dd/mm/yyyy',autoclose: true
		}
	</script>
	
	