<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
	<link rel="icon" href="../../heroadmin/resources/images/logos/favicon.png" type="image/png" sizes="16x16">
	
    <link rel="stylesheet" href="../../heroadmin/resources/css/normalize.css">
    <link rel="stylesheet" href="../../heroadmin/resources/css/bootstrap.3.3.7.min.css">
    <link rel="stylesheet" href="../../heroadmin/resources/css/style.css">
    <link rel="stylesheet" href="../../heroadmin/resources/css/font-awesome.min.css">
    <link rel="stylesheet" href="../../heroadmin/resources/css/themify-icons.css">
    <link rel="stylesheet" href="../../heroadmin/resources/css/jquery-ui.css">
    
    
    <!-- notificaton css -->
    <link href="../../heroadmin/resources/css/lib/notification/material-dashboard.css?v=1.2.0" rel="stylesheet" />
    <link href='../../heroadmin/resources/css/lib/notification/material-icon.css' rel='stylesheet' type='text/css'>
	
	<link rel="stylesheet" type="text/css" href="../../heroadmin/resources/css/lib/datatable/finalDatatable.min.css" />
	
	<link rel="stylesheet" type="text/css" href="../../heroadmin/resources/css/bootstrap-datepicker.css" />
	<link rel="stylesheet" type="text/css" href="../../heroadmin/resources/css/daterangepicker.css" /> 
	
	<script src="../../heroadmin/js/lib/theme/jquery.3.3.1.min.js"></script>
  	<script src="../../heroadmin/js/lib/theme/bootstrap.3.3.7.min.js"></script>
	
    <script type="text/javascript" src="../../heroadmin/js/util/invutil.js"></script>  
    <script type="text/javascript" src="../../heroadmin/js/lib/theme/html5shiv.min.js"></script>
	<script type="text/javascript" src="../../hero/js/lib/theme/header.js"></script>
	
	<link href="../../heroadmin/resources/css/myfinal.css" rel="stylesheet" type="text/css">
	<link href="../../heroadmin/resources/css/hero-theme.css" rel="stylesheet" type="text/css">
	<link href="../../heroadmin/resources/css/template/ak.css" rel="stylesheet" type="text/css">

	<script src="../../heroadmin/js/lib/theme/jquery.easyPaginate.js" type="text/javascript"></script>
	
	<link href="../../hero/resources/css/animate-3.5.1.min.css" rel="stylesheet" type="text/css">
	
	<script src="../../hero/js/lib/theme/lodash-2.4.1.min.js" type="text/javascript"></script>
	<script src="../../hero/js/lib/theme/list-1.5.0.min.js	" type="text/javascript"></script>
	<script src="../../hero/js/lib/theme/list.pagination-0.1.1.js" type="text/javascript"></script>
		
	<!-- settings for notification -->
	<script>
		var notification_from = "bottom";
		var notification_align = "right";	
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