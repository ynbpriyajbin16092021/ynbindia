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
	
	<!-- <link rel="stylesheet" href="../resources/css/stylebass.css">
	<link rel="stylesheet" href="../resources/css/stylej.css"> -->
	
	<link rel="stylesheet" type="text/css" href="/heroadmin/resources/css/lib/datatable/finalDatatable.min.css" />
	
	<link rel="stylesheet" type="text/css" href="/heroadmin/resources/css/bootstrap-datepicker.css" />
	<link rel="stylesheet" type="text/css" href="/heroadmin/resources/css/daterangepicker.css" />
	
	<script src="/heroadmin/js/lib/theme/jquery.3.3.1.min.js"></script>
  	<script src="/heroadmin/js/lib/theme/bootstrap.3.3.7.min.js"></script>
	

	
	
	<link href="/heroadmin/resources/css/myfinal.css" rel="stylesheet" type="text/css">
	<link href="/heroadmin/resources/css/hero-theme.css" rel="stylesheet" type="text/css">
	<link href="/heroadmin/resources/css/lib/mdb.css" rel="stylesheet" type="text/css">

	<script src="/heroadmin/js/lib/theme/jquery.easyPaginate.js" type="text/javascript"></script>
	<script src="/heroadmin/js/lib/notification/perfect-scrollbar.jquery.min.js" type="text/javascript"></script>
	<script src="/heroadmin/js/lib/theme/mdb.min.js" type="text/javascript"></script>
	
	
	
	<!-- settings for notification -->
	<script>
		var notification_from = "bottom";
		var notification_align = "right";
	</script>
		<!-- notificaton js -->
<script type="text/javascript" src="/heroadmin/js/lib/notification/bootstrap-notify.js"></script>
<script type="text/javascript" src="/heroadmin/js/lib/notification/notification.js"></script>
	
    <script type="text/javascript" src="/heroadmin/js/util/invutil.js"></script>  
    <script type="text/javascript" src="/heroadmin/js/lib/theme/html5shiv.min.js"></script>
	
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