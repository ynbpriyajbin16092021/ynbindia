
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!doctype html>
<html class="no-js" lang="">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>HERO</title>
    <meta name="description" content="Sufee Admin - HTML5 Admin Template">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="apple-touch-icon" href="apple-icon.png">
    <link rel="shortcut icon" href="favicon.ico">
    <link rel="stylesheet" href="../resources/css/normalize.css">
    <!--   <link rel="stylesheet" href="../resources/css/bootstrap.min.css"> -->
    <link rel="stylesheet" href="../resources/css/bootstrap.3.3.7.min.css">
    <link rel="stylesheet" href="../resources/css/style.css">
    <link rel="stylesheet" href="../resources/css/font-awesome.min.css">
    <link rel="stylesheet" href="../resources/css/themify-icons.css">
    <link rel="stylesheet" href="../resources/css/flag-icon.min.css">
    <link rel="stylesheet" href="../resources/css/cs-skin-elastic.css">
    <link rel="stylesheet" href="../resources/css/jquery-ui.css">
    
	
	<link rel="stylesheet" href="../resources/css/stylebass.css">
	<link rel="stylesheet" href="../resources/css/stylej.css">

	<link rel="stylesheet" href="../resources/css/responsive.bootstrap.min.css">
	<link rel="stylesheet" href="../resources/css/dataTables.jqueryui.min.css">
	<link rel="stylesheet" type="text/css" href="//cdn.jsdelivr.net/bootstrap.daterangepicker/2/daterangepicker.css" />
	
	<script src="../js/lib/theme/jquery.3.3.1.min.js"></script>
  	<script src="../js/lib/theme/bootstrap.3.3.7.min.js"></script>
	
<!-- 	<script type="text/javascript" src='../js/jquery-1.8.3.min.js'></script>
	<script type="text/javascript" src=" https://code.jquery.com/jquery-1.12.4.js"></script> 
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script> -->	
    <script type="text/javascript" src="../js/util/invutil.js"></script>  
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:400,600,700,800" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="../js/lib/theme/html5shiv.min.js"></script>
	<script type="text/javascript" src="../js/forms/admin/login/logout.js"></script>
	<style>
	table.dataTable thead .sorting_asc:after {
   display:none;
	}
	table.dataTable thead .sorting:after {
   display:none;
	}
	.row{
		width:100%;
		margin-left:0px;
		margin-right:0px;
	}
	.szhme{
	font-size: 19px;
    color: #2aa0cb;
	}
	</style>
	
	
	<link href="../resources/css/myfinal.css" rel="stylesheet" type="text/css">
	
	
</head>
<body>

 <div class="fullview">
<div class="navview" style="display: <%= (request.getParameter("disp") != null && request.getParameter("disp").equalsIgnoreCase("N") ) ? "none":""%>;">
  <aside id="left-panel" class="left-panel">
        <nav class="navbar navbar-expand-sm navbar-default">
            <div class="navbar-header" style="color:white">
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#main-menu" aria-controls="main-menu" aria-expanded="false" aria-label="Toggle navigation">
                    <i class="fa fa-bars"></i>
                </button>
                <div class="sidebar-top">
                <div class="logopanel">
          <h4> <%= session.getAttribute("companyname") %> </h4>
          <input type="hidden" value="<%=session.getAttribute("uid")%>" id="inventoryuseridtext">
          <input type="hidden" value="<%=session.getAttribute("usertype")%>" id="inventoryusertypetext">
          <input type="hidden" class="form-control CustomerSearchInput" id="currsymboltext" value="<%= session.getAttribute("currencyname") %>">
    	  <input type="hidden" class="form-control CustomerSearchInput" id="currdecimaltext" value="<%= session.getAttribute("currencydecimal") %>">
		  <input type="hidden" class="form-control CustomerSearchInput" id="storeidtext" value="<%= session.getAttribute("storeid") %>">
		  <br />
        </div>
            <div class="userlogged clearfix"> 
              <div class="user-details">
                <h6><i class="fa fa-user"></i>  <%=session.getAttribute("username")%></h6>
                <p><%=session.getAttribute("usertypedesc")%></p>
              </div>
            </div>
          </div>
            </div>
            <div id="main-menu" class="main-menu collapse navbar-collapse">
                <ul class="nav navbar-nav">
                 <%
            List mainmenulist = (List) session.getAttribute("mainmenuList");
            if(mainmenulist != null)
            {
            for(int mainmenuloop = 0;mainmenuloop<mainmenulist.size();mainmenuloop++)
            {
            	Map<String,Object> mainmenumap = (Map)mainmenulist.get(mainmenuloop);
            %>
            <li class="menu-item-has-children dropdown" >
               
              <a class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
              <i class="<%=mainmenumap.get("fafafont") %>" aria-hidden="true"></i>
              <span><%=mainmenumap.get("modulename") %></span>
              <span class="fa arrow"></span>
              </a>
              <ul class="sub-menu children dropdown-menu">
              <%
              List submenulist = (List) mainmenumap.get("submenuList");
              for(int submenuloop = 0;submenuloop<submenulist.size();submenuloop++)
              {
            	  Map<String,Object> submenumap = (Map)submenulist.get(submenuloop);
            	  %>
            	  
                <li><a href="<%=submenumap.get("modulepath") %>"><%=submenumap.get("modulename") %></a></li>
                
             
            	  <%
              }
              %>
               </ul>
            </li>
            <%
            }
            }
            %>
                    
					
                </ul>
            </div>
        </nav>
    </aside>
</div>
        <div class="contentview">
          <div id="right-panel" class="right-panel">
     <header id="header" class="header" style="display: <%= (request.getParameter("disp") != null && request.getParameter("disp").equalsIgnoreCase("N") ) ? "none":""%>;">
     <div class="header-menu">
	    <div class="col-sm-6">
                    <!-- <a id="menuToggle" class="menutoggle pull-left"><i class="fa fa fa-tasks"></i></a> -->
                    <div class="header-left">
                    
                    	<%if(session.getAttribute("uid") != null )
			        	 {
			        	 %>
			         <%if((Integer.parseInt((String)session.getAttribute("uid"))) != 1 )
			        	 {
			        	 %>
                        <a href="pos?posid=0" target="new" class="search-trigger">POS</a>
                        <%}int usertype = 0;
			           //System.out.println("UserType        "+session.getAttribute("usertypestr"));
			           String usertypestr = (String) session.getAttribute("usertypestr");
			           if(usertypestr != null)
			           {
			        	   usertype = Integer.parseInt(usertypestr);
			           }
			        	 
			           if(usertype <= 1)
			           {
			            	 %>
                        <div class="col-md-4 dropdown for-notification">
                          <button class="btn btn-secondary dropdown-toggle blackc" type="button" id="notification" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <i class="fa fa-list"></i>&nbsp; <span>Create New</span> &nbsp;<i class="fa fa-sort-desc"></i>
                          </button>
                          <div class="dropdown-menu">
                            <a  href="addproduct">
                                <p>Product</p>
                            </a>
                            <a  href="addsupplier">
                                <p>Supplier</p>
                            </a>
                            <a  href="addpurchaseorder?purchaseorderid=0">
                                <p>Purchase</p>
                            </a>
                            <a  href="stocktransfer?stocktransferid=0">
                                <p>Transfer</p>
                            </a>
                            <a  href="adjustments-new.php">
                                <p>Adjustment</p>
                            </a>
                          </div>
                        </div>
                        <%} %>
                        <!-- <div class="clearfix"></div> -->
                        <span style="padding-left:10px;"></span>
                        <div class="dropdown for-notification">
                          <a class="search-trigger dropdown-toggle" href="lowstockalert">
                            <p class="lowstockp">Low Stocks
                            <span class="count bg-danger"><%=session.getAttribute("lowstockcount") %></span></p>
                          </a>
                          </div>
                          <%} %>
                    </div>
                </div>
                <div class="col-sm-1">
                <a class="nav-link" href="herohome?uid=<%= session.getAttribute("uid")%>" title="Home"><i class="fa fa-home szhme"></i>&nbsp;<span></a>
                </div>
				<div class="col-sm-5">
                    <div class="user-area dropdown float-right">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <img class="user-avatar rounded-circlelab" src="../resources/images/admin.jpg" alt="User Avatar">
                            <span class="username">Hi, <%=session.getAttribute("username") %></span>
                            &nbsp;<i class="fa fa-sort-desc"></i>
                        </a>
						<div class="user-menu dropdown-menu">
                                <a class="nav-link" href="herohome?uid=<%= session.getAttribute("uid")%>"><i class="fa fa-home"></i>&nbsp;<span>Home</a>
                                <a onclick="openchangepwpopup();" data-toggle="modal" data-target="#changepassword-popup" id="changepwbtn" class="nav-link chngPopup" href="#"><i class="fa fa-eye"></i><span>Change Password</span></a>
                                <!-- <a class="nav-link" href="signout"><i class="fa fa-sign-out"></i>&nbsp;Logout</a> -->
                                <a class="nav-link" onclick="tokenSignout()"><i class="fa fa-sign-out"></i>&nbsp;Logout</a>
                        </div>
                    </div>
					
					</div>
            </div>
</header>

<!-- change password popup -->

<!-- <script type="text/javascript">
				$(document).ready(function(){
					
				jQuery(".chngPopup").click(function(){
					jQuery('#changepassword-popup').modal({backdrop: true});
				   
					
					});	
					
				});
				</script>  -->
                  <div class="modal fade" id="changepassword-popup" tabindex="-1" role="dialog" aria-hidden="true">
  <div class="modal-dialog topZero">
    <div class="modal-content">
      <div class="modal-header">
 <h4 class="modal-title">Change Password</h4>
  <button type="button" class="close" data-dismiss="modal">&times;</button>
      </div>
      <div class="modal-body">
       
        <div class="row">
          <div  class="col-sm-6 form-group">
            <label>Current Password</label>
            <input placeholder="" class="form-control form-white" type="password" id="currentpasswordtext">
          </div>
          <div  class="col-sm-6 form-group">
            <label>New Password</label>
            <input placeholder="" class="form-control form-white" type="password" id="newpasswordtext">
          </div>
          <div  class="col-sm-6 form-group">
            <label>Confirm Password</label>
            <input placeholder="" class="form-control form-white " type="password" id="confirmpasswordtext">
            </div>
          
          
        </div>
      </div>
      <div class="modal-footer alignCenter" align="center ">
        <div class="form-group " align="center">
             <button type="button" class="btn btn-primary " data-dismiss="modal" onclick="changepassword();" id="passwordsavebtn">Save</button>
              <button type="button" class="btn btn-normal " data-dismiss="modal">Cancel</button>
        </div>
      </div>
    </div>
  </div>
</div>


<!-- end change password popup -->


<%-- <div class="breadcrumbs"  style="display: <%= (request.getParameter("disp") != null && request.getParameter("disp").equalsIgnoreCase("N") ) ? "none":""%>;">
            <div class="col-md-4 col-sm-4">
                <div class="page-header float-left">
                    <div class="page-title">
                        <h1>Dashboard</h1>
                    </div>
                </div>
            </div>
            <div class="col-md-8 col-sm-8">
                <div class="page-header float-right">
                    <div class="page-title">
                        <ol class="breadcrumb text-right">
                            <li><a href="herosettings">Config System Settings</a></li>
                           </ol>
                    </div>
                </div>
            </div>
        </div> --%>
       <div class="content mt-3">
            <div class="animated fadeIn">
    <div class="row">
                  <div>
                  <div>

				  