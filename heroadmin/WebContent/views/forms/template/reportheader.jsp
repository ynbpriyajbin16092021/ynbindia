<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<!-- Theame Styles -->

<link rel="stylesheet" href="../resources/css/font-awesome.min.css">
<link href="../resources/css/template/style.css" rel="stylesheet">
<link href="../resources/css/template/theme.css" rel="stylesheet">
<link href="../resources/css/template/ui.css" rel="stylesheet">
<link href="../resources/css/plugin/mcustom_scrollbar.min.css" rel="stylesheet">
<link href="../resources/css/plugin/animate.min.css" rel="stylesheet">
<link href="../resources/css/common/inventory.css" rel="stylesheet">
<!-- Andy Styles  
<link rel="stylesheet" href="../css/style.css">-->
<link rel="stylesheet" href="../resources/css/my-style.css">
<link rel="stylesheet" href="../resources/css/ionicons.css">
<link rel="stylesheet" href="../resources/css/jquery-ui.css">

<!-- <link href='../resources/css/common/googleapi.css' rel='stylesheet' type='text/css'> -->

<style type="text/css" title="currentStyle">
@import "../resources/css/datatable/jquery.dataTables.min.css";
</style>

<script type="text/javascript" src="../js/jquery-1.12.3.min.js"></script>
<script type="text/javascript" src="../js/util/invutil.js"></script>
<script type="text/javascript"	src="../js/dataTables.min.js"></script>
	
<!-- for DataTable PDF,Excel Print Start -->	
<link rel="stylesheet" href="../js/plugin/datatable/print/buttons.dataTables.min.css">
<link rel="stylesheet" href="../js/plugin/datatable/print/jquery.dataTables.min.css">

<script type="text/javascript" src="../js/plugin/datatable/print/jquery-1.12.4.js"></script>
<script type="text/javascript" src="../js/plugin/datatable/print/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="../js/plugin/datatable/print/dataTables.buttons.min.js"></script>
<script type="text/javascript" src="../js/plugin/datatable/print/buttons.flash.min.js"></script>
<script type="text/javascript" src="../js/plugin/datatable/print/jszip.min.js"></script>
<script type="text/javascript" src="../js/plugin/datatable/print/pdfmake.min.js"></script>
<script type="text/javascript" src="../js/plugin/datatable/print/vfs_fonts.js"></script>
<script type="text/javascript" src="../js/plugin/datatable/print/buttons.html5.min.js"></script>
<script type="text/javascript" src="../js/plugin/datatable/print/buttons.print.min.js"></script>
<!-- End -->

</head>
<!-- BEGIN BODY -->
<body class="fixed-topbar fixed-sidebar theme-sdtl color-default" onload="loadheader();">
<!--[if lt IE 7]>
<p class="browsehappy">You are using an 
<strong>outdated</strong> browser. Please 
<a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.
</p>
<![endif]-->
<section class="displayTabel">
  <div class="main-content"> 
  
    <!-- BEGIN TOPBAR -->
    <div class="topbar" style="margin-top: 0px; opacity: 1; visibility: inherit;">
          <div class="header-left">       <div class="topnav">
         <a class="menutoggle" href="#" data-toggle="sidebar-collapsed"><span class="menu__handle"><span>Menu</span></span></a>
         <%if(session.getAttribute("uid") != null && (Integer.parseInt((String)session.getAttribute("uid"))) != 1)
        	 {
        	 %>
         <ul class="nav nav-horizontal">
           <!-- <li><a href="#"><span class="pull-right badge badge-primary">8</span><i class="fa fa-envelope-o"></i><span>Mailbox</span></a></li> -->
           <li><a href="pos?posid=0" target="new"><i class="fa fa-credit-card"></i><span>POS</span></a></li>
           <%int usertype = 0;
           //System.out.println("UserType        "+session.getAttribute("usertypestr"));
           String usertypestr = (String) session.getAttribute("usertypestr");
           if(usertypestr != null)
           {
        	   usertype = Integer.parseInt(usertypestr);
           }
           if(usertype <= 2)
           {
            	 %>
           <li class="nav-parent">
             <a href="#" data-toggle="dropdown" data-hover="dropdown" data-close-others="true" data-delay="30" aria-expanded="false">
               <i class="fa fa-list"></i><span>Create New</span> &nbsp;<i class="fa fa-sort-desc"></i>
             </a>
             <ul class="dropdown-menu children">
               <li><a href="addproduct">Product</a></li>
               <li><a href="addsupplier">Supplier</a></li>
               <li><a href="addpurchaseorder?purchaseorderid=0"> Purchase</a></li>
               <li><a href="stocktransfer?stocktransferid=0"> Transfer</a></li>
               <li><a href="adjustments-new.php"> Adjustment</a></li>
             </ul>
           </li>
           <%} %>
           <li><a href="lowstockalert"><span class="pull-right badge badge-danger">
           <%=session.getAttribute("lowstockcount") %>
           </span><i class="fa fa-bar-chart"></i><span>Low Stocks</span></a></li>
           <li><a href="#"><i class="fa fa-cogs"></i><span>Settings</span></a></li>
         </ul>
         <%} %>
       </div>
</div>
          <div class="header-right">
            <ul class="header-menu nav navbar-nav">
              
              <!-- BEGIN NOTIFICATION DROPDOWN -->
              <li class="dropdown" id="notifications-header">
                <a href="#" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
                <i class="fa fa-bell-o"></i>
                <span class="badge badge-danger badge-header">6</span>
                </a>
                <ul class="dropdown-menu">
                  <li class="dropdown-header clearfix">
                    <p class="pull-left">12 Pending Notifications</p>
                  </li>
                  <li>
                    <ul class="dropdown-menu-list withScroll mCustomScrollbar _mCS_1 mCS-autoHide mCS_no_scrollbar" data-height="220" style="height: 220px;"><div id="mCSB_1" class="mCustomScrollBox mCS-dark-thick mCSB_vertical mCSB_inside" style="max-height: 220px;" tabindex="0"><div id="mCSB_1_container" class="mCSB_container mCS_y_hidden mCS_no_scrollbar_y" style="position:relative; top:0; left:0;" dir="ltr">
                      <li>
                        <a href="#">
                        <i class="fa fa-star p-r-10 f-18 c-orange"></i>
                        Steve have rated your photo
                        <span class="dropdown-time">Just now</span>
                        </a>
                      </li>
                      <li>
                        <a href="#">
                        <i class="fa fa-heart p-r-10 f-18 c-red"></i>
                        John added you to his favs
                        <span class="dropdown-time">15 mins</span>
                        </a>
                      </li>
                      <li>
                        <a href="#">
                        <i class="fa fa-file-text p-r-10 f-18"></i>
                        New document available
                        <span class="dropdown-time">22 mins</span>
                        </a>
                      </li>
                      <li>
                        <a href="#">
                        <i class="fa fa-picture-o p-r-10 f-18 c-blue"></i>
                        New picture added
                        <span class="dropdown-time">40 mins</span>
                        </a>
                      </li>
                      <li>
                        <a href="#">
                        <i class="fa fa-bell p-r-10 f-18 c-orange"></i>
                        Meeting in 1 hour
                        <span class="dropdown-time">1 hour</span>
                        </a>
                      </li>
                      <li>
                        <a href="#">
                        <i class="fa fa-bell p-r-10 f-18"></i>
                        Server 5 overloaded
                        <span class="dropdown-time">2 hours</span>
                        </a>
                      </li>
                      <li>
                        <a href="#">
                        <i class="fa fa-comment p-r-10 f-18 c-gray"></i>
                        Bill comment your post
                        <span class="dropdown-time">3 hours</span>
                        </a>
                      </li>
                      <li>
                        <a href="#">
                        <i class="fa fa-picture-o p-r-10 f-18 c-blue"></i>
                        New picture added
                        <span class="dropdown-time">2 days</span>
                        </a>
                      </li>
                    </div><div id="mCSB_1_scrollbar_vertical" class="mCSB_scrollTools mCSB_1_scrollbar mCS-dark-thick mCSB_scrollTools_vertical" style="display: none;"><div class="mCSB_draggerContainer"><div id="mCSB_1_dragger_vertical" class="mCSB_dragger" style="position: absolute; min-height: 30px; top: 0px;"><div class="mCSB_dragger_bar" style="line-height: 30px;"></div></div><div class="mCSB_draggerRail"></div></div></div></div></ul>
                  </li>
                  <li class="dropdown-footer clearfix">
                    <a href="#" class="pull-left">See all notifications</a>
                    <a href="#" class="pull-right">
                    <i class="icon-settings"></i>
                    </a>
                  </li>
                </ul>
              </li>
              <!-- END NOTIFICATION DROPDOWN -->
              
              <!-- BEGIN USER DROPDOWN -->
              <li class="dropdown" id="user-header">
                <a href="#" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
                <i class="fa fa-user" aria-hidden="true"></i>
                <span class="username">Hi, <%=session.getAttribute("username") %></span>
                </a>
                <ul class="dropdown-menu">
                  <!-- <li>
                    <a href="#"><i class="fa fa-user"></i><span>My Profile</span></a>
                  </li>
                  <li>
                    <a href="#"><i class="fa fa-cog"></i><span>Account Settings</span></a>
                  </li> -->
                  <li>
                    <a href="stockdashboard?uid=<%= session.getAttribute("uid")%>"><i class="fa fa-cog"></i><span>Home</span></a>
                  </li>
                  <li>
                    <a href="signout"><i class="fa fa-sign-out"></i><span>Logout</span></a>
                  </li>
                </ul>
              </li>
              <!-- END USER DROPDOWN -->
              <!-- CHAT BAR ICON -->
              
            </ul>
          </div>
          <!-- header-right -->
        </div>
    <!-- END TOPBAR --> 
    <!-- BEGIN PAGE CONTENT -->
    <div class="page-content">
      <div class="sidebar">
        <div class="logopanel">
          <h1> <%=session.getAttribute("companyname")%> </h1>
          <input type="hidden" value="<%=session.getAttribute("uid")%>" id="inventoryuseridtext">
          <input type="hidden" value="<%=session.getAttribute("usertype")%>" id="inventoryusertypetext">
          <input type="hidden" class="form-control CustomerSearchInput" id="currsymboltext" value="<%= session.getAttribute("currencyname") %>">
    	  <input type="hidden" class="form-control CustomerSearchInput" id="currdecimaltext" value="<%= session.getAttribute("currencydecimal") %>">
		  <input type="hidden" class="form-control CustomerSearchInput" id="storeidtext" value="<%= session.getAttribute("storeid") %>">
        </div>
        <div class="sidebar-inner">
          <div class="sidebar-top">
            <div class="userlogged clearfix"> <i class="fa fa-user"></i>
              <div class="user-details">
                <h4><%=session.getAttribute("username")%></h4>
                <p><%=session.getAttribute("usertypedesc")%></p>
              </div>
            </div>
          </div>
          <div class="menu-title"> Navigation
            
          </div>
          <ul class="nav nav-sidebar">
            <%
            List mainmenulist = (List) session.getAttribute("mainmenuList");
            if(mainmenulist != null)
            {
            for(int mainmenuloop = 0;mainmenuloop<mainmenulist.size();mainmenuloop++)
            {
            	Map<String,Object> mainmenumap = (Map)mainmenulist.get(mainmenuloop);
            %>
            <li class="nav-parent">
               
              <a>
              <i class="<%=mainmenumap.get("fafafont") %>" aria-hidden="true"></i>
              <span><%=mainmenumap.get("modulename") %></span>
              <span class="fa arrow"></span>
              </a>
              
              <%
              List submenulist = (List) mainmenumap.get("submenuList");
              for(int submenuloop = 0;submenuloop<submenulist.size();submenuloop++)
              {
            	  Map<String,Object> submenumap = (Map)submenulist.get(submenuloop);
            	  %>
            	  <ul class="children collapse">
                <li><a href="<%=submenumap.get("modulepath") %>"><%=submenumap.get("modulename") %></a></li>
                
              </ul>
            	  <%
              }
              %>
              
            </li>
            <%
            }
            }
            %>
            <%-- <li class="nav-parent">
              <a href="#"><i class="fa fa-shopping-cart" aria-hidden="true"></i><span>Product</span><span class="fa arrow"></span></a>
              <ul class="children collapse">
                <li><a href="product"> Products</a></li>
                <li><a href="supplier"> Suppliers </a></li>
              </ul>
            </li>

            <li class="nav-parent">
              <a href="#"><i class="fa fa-lightbulb-o" aria-hidden="true"></i><span>Inventory</span><span class="fa arrow"></span></a>
              <ul class="children collapse">
                <li><a href="stockviewhistory"> Warehouse Stocks  </a></li>
                <li><a href="stocktransferhistory"> Stock Transfer </a></li>
				<li><a href="adjustments.php"> Adjustments </a></li>
              </ul>
            </li>


            

            <li class="nav-parent">
              <a href="#"><i class="fa fa-credit-card-alt" aria-hidden="true"></i><span>Purchase  </span><span class="fa arrow"></span></a>
              <ul class="children collapse">
                <li><a href="purchaseorderhistory"> Purchase  orders  </a></li>
                <li><a href="expenses.php"> Expenses </a></li>
              </ul>
            </li>

            <li class="nav-parent">
              <a href="#"><i class="fa fa-balance-scale" aria-hidden="true"></i><span>Sales  </span><span class="fa arrow"></span></a>
              <ul class="children collapse">
                <li><a href="indexpoc.php" target="new"> POS</a></li>
				<li><a href="invoices.php"> Invoices </a></li>
                <li><a href="orders.php"> Orders </a></li>
                <li class=""><a href="packages.php"> Packages </a></li>
              </ul>
            </li>
			
			
			<li class="nav-parent">
              <a href="#"><i class="fa fa-balance-scale" aria-hidden="true"></i><span>Customers </span><span class="fa arrow"></span></a>
              <ul class="children collapse">
				<li><a href="customergroup"> Customer groups </a></li>              
                <li><a href="customer"> Customers list  </a></li>
              </ul>
            </li>

            <li class="nav-parent">
              <a href="#"><i class="fa fa-list-alt" aria-hidden="true"></i><span>Reports  </span><span class="fa arrow"></span></a>
              <ul class="children collapse">
			  <li><a href="bills.php"> Purchase Bills</a></li>
                <li><a href="suppliers-report.php"> Suppliers  Report  </a></li>
                <li><a href="batches-report.php"> Batches Report </a></li>
                <li><a href="purchases-report.php"> Purchases Report </a></li>
                <li><a href="customers-report.php"> customers  Report </a></li>
              </ul>
            </li>

			
			<li class="nav-parent">
              <a href="#"><i class="fa fa-list-alt" aria-hidden="true"></i><span>Tools  </span><span class="fa arrow"></span></a>
              <ul class="children collapse">
                <li><a href="stockmonitor"> Stock Moniter  </a></li>
                <li><a href="expirydatechecker"> Expiry Date Checker</a></li>
                <li><a href=""> Low Stock Alert </a></li>
                <li><a href="print.php"> Print Barcodes/Labels </a></li>
              </ul>
            </li>
			
			
			
			
			
            <li class="nav-parent">
              <a href="#"><i class="fa fa-stack-overflow" aria-hidden="true"></i><span>System Settings</span><span class="fa arrow"></span></a>
              <ul class="children collapse">
                <li><a href="category?uid=<%=session.getAttribute("uid")%>"> Product Categories </a></li>
				<li><a href="manufacturercompany"> Manufacturer </a></li>
				<li><a href="uom"> Unit of Measurement </a></li>
                <li><a href="userrole"> Users & Permissions  </a></li>
                <li><a href="currency"> Currencies setup </a></li>
				<li><a href="tax"> Taxes setup  </a></li>
                <li><a href="store"> Stores  </a></li>
              </ul>
            </li>

			<li class="nav-parent">
              <a href="#"><i class="fa fa-balance-scale" aria-hidden="true"></i><span>Preference </span><span class="fa arrow"></span></a>
              <ul class="children collapse">
                <li><a href="company.php"> My Company  </a></li>
                <li><a href="orders.php"> Themes </a></li>
				<li><a href="templates.php"> Invoice Template </a></li>
				<li><a href="email-template.php"> Email Template </a></li>
				<li><a href="sms-templates.php"> SMS Template </a></li>
              </ul>
            </li>
           
		   <li class="nav-parent">
              <a href="#"><i class="fa fa-balance-scale" aria-hidden="true"></i><span>Support</span><span class="fa arrow"></span></a>
              <ul class="children collapse">
                <li><a href="contact.php"> About </a></li>
                <li><a href="need-help.php"> License </a></li>
              </ul>
            </li> --%>
		   
		   
          </ul>
          <div class="sidebar-footer clearfix"> <a class="pull-left footer-settings" href="#" data-rel="tooltip" data-placement="top" data-original-title="Settings"> <i class="fa fa-cogs"></i> </a> <a class="pull-left toggle_fullscreen" href="#" data-rel="tooltip" data-placement="top" data-original-title="Fullscreen"> <i class="fa fa-desktop"></i> </a> <a class="pull-left" href="user-lockscreen.html" data-rel="tooltip" data-placement="top" data-original-title="Lockscreen"> <i class="fa fa-lock"></i> </a> <a class="pull-left btn-effect" href="user-login-v1.html" data-modal="modal-1" data-rel="tooltip" data-placement="top" data-original-title="Logout"> <i class="fa fa-power-off"></i> </a> </div>
        </div>
      </div>



      <!-- BEGIN QUICKVIEW SIDEBAR -->
    <!-- <div id="quickview-sidebar">
      <div class="quickview-header">
        <ul class="nav nav-tabs">
          <li class="active"><a href="#chat" data-toggle="tab">Chat</a></li>
          <li><a href="#notes" data-toggle="tab">Notes</a></li>
          <li><a href="#settings" data-toggle="tab" class="settings-tab">Settings</a></li>
        </ul>
      </div>
      <div class="quickview">
        <div class="tab-content">
          <div class="tab-pane fade active in" id="chat">
            <div class="chat-body current">
              <div class="chat-search">
                <form class="form-inverse" action="#" role="search">
                  <div class="append-icon">
                    <input type="text" class="form-control" placeholder="Search contact...">
                    <i class="icon-magnifier"></i>
                  </div>
                </form>
              </div>
              <div class="chat-groups">
                <div class="title">GROUP CHATS</div>
                <ul>
                  <li><i class="turquoise"></i> Favorites</li>
                  <li><i class="turquoise"></i> Office Work</li>
                  <li><i class="turquoise"></i> Friends</li>
                </ul>
              </div>
              <div class="chat-list">
                <div class="title">FAVORITES</div>
                <ul>
                  <li class="clearfix">
                    <div class="user-img">
                      <img src="../assets/global/images/avatars/avatar13.png" alt="avatar" />
                    </div>
                    <div class="user-details">
                      <div class="user-name">Bobby Brown</div>
                      <div class="user-txt">On the road again...</div>
                    </div>
                    <div class="user-status">
                      <i class="online"></i>
                    </div>
                  </li>
                  <li class="clearfix">
                    <div class="user-img">
                      <img src="../assets/global/images/avatars/avatar5.png" alt="avatar" />
                      <div class="pull-right badge badge-danger">3</div>
                    </div>
                    <div class="user-details">
                      <div class="user-name">Alexa Johnson</div>
                      <div class="user-txt">Still at the beach</div>
                    </div>
                    <div class="user-status">
                      <i class="away"></i>
                    </div>
                  </li>
                  <li class="clearfix">
                    <div class="user-img">
                      <img src="../assets/global/images/avatars/avatar10.png" alt="avatar" />
                    </div>
                    <div class="user-details">
                      <div class="user-name">Bobby Brown</div>
                      <div class="user-txt">On stage...</div>
                    </div>
                    <div class="user-status">
                      <i class="busy"></i>
                    </div>
                  </li>
                </ul>
              </div>
              <div class="chat-list">
                <div class="title">FRIENDS</div>
                <ul>
                  <li class="clearfix">
                    <div class="user-img">
                      <img src="../assets/global/images/avatars/avatar7.png" alt="avatar" />
                      <div class="pull-right badge badge-danger">3</div>
                    </div>
                    <div class="user-details">
                      <div class="user-name">James Miller</div>
                      <div class="user-txt">At work...</div>
                    </div>
                    <div class="user-status">
                      <i class="online"></i>
                    </div>
                  </li>
                  <li class="clearfix">
                    <div class="user-img">
                      <img src="../assets/global/images/avatars/avatar11.png" alt="avatar" />
                    </div>
                    <div class="user-details">
                      <div class="user-name">Fred Smith</div>
                      <div class="user-txt">Waiting for tonight</div>
                    </div>
                    <div class="user-status">
                      <i class="offline"></i>
                    </div>
                  </li>
                  <li class="clearfix">
                    <div class="user-img">
                      <img src="../assets/global/images/avatars/avatar8.png" alt="avatar" />
                    </div>
                    <div class="user-details">
                      <div class="user-name">Ben Addams</div>
                      <div class="user-txt">On my way to NYC</div>
                    </div>
                    <div class="user-status">
                      <i class="offline"></i>
                    </div>
                  </li>
                </ul>
              </div>
            </div>
            <div class="chat-conversation">
              <div class="conversation-header">
                <div class="user clearfix">
                  <div class="chat-back">
                    <i class="icon-action-undo"></i>
                  </div>
                  <div class="user-details">
                    <div class="user-name">James Miller</div>
                    <div class="user-txt">On the road again...</div>
                  </div>
                </div>
              </div>
              <div class="conversation-body">
                <ul>
                  <li class="img">
                    <div class="chat-detail">
                      <span class="chat-date">today, 10:38pm</span>
                      <div class="conversation-img">
                        <img src="../assets/global/images/avatars/avatar4.png" alt="avatar 4"/>
                      </div>
                      <div class="chat-bubble">
                        <span>Hi you!</span>
                      </div>
                    </div>
                  </li>
                  <li class="img">
                    <div class="chat-detail">
                      <span class="chat-date">today, 10:45pm</span>
                      <div class="conversation-img">
                        <img src="../assets/global/images/avatars/avatar4.png" alt="avatar 4"/>
                      </div>
                      <div class="chat-bubble">
                        <span>Are you there?</span>
                      </div>
                    </div>
                  </li>
                  <li class="img">
                    <div class="chat-detail">
                      <span class="chat-date">today, 10:51pm</span>
                      <div class="conversation-img">
                        <img src="../assets/global/images/avatars/avatar4.png" alt="avatar 4"/>
                      </div>
                      <div class="chat-bubble">
                        <span>Send me a message when you come back.</span>
                      </div>
                    </div>
                  </li>
                </ul>
              </div>
              <div class="conversation-message">
                <input type="text" placeholder="Your message..." class="form-control form-white send-message" />
                <div class="item-footer clearfix">
                  <div class="footer-actions">
                    <i class="icon-rounded-marker"></i>
                    <i class="icon-rounded-camera"></i>
                    <i class="icon-rounded-paperclip-oblique"></i>
                    <i class="icon-rounded-alarm-clock"></i>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="tab-pane fade" id="notes">
            <div class="list-notes current withScroll">
              <div class="notes ">
                <div class="row">
                  <div class="col-md-12">
                    <div id="add-note">
                      <i class="fa fa-plus"></i>ADD A NEW NOTE
                    </div>
                  </div>
                </div>
                <div id="notes-list">
                  <div class="note-item media current fade in">
                    <button class="close">×</button>
                    <div>
                      <div>
                        <p class="note-name">Reset my account password</p>
                      </div>
                      <p class="note-desc hidden">Break security reasons.</p>
                      <p><small>Tuesday 6 May, 3:52 pm</small></p>
                    </div>
                  </div>
                  <div class="note-item media fade in">
                    <button class="close">×</button>
                    <div>
                      <div>
                        <p class="note-name">Call John</p>
                      </div>
                      <p class="note-desc hidden">He have my laptop!</p>
                      <p><small>Thursday 8 May, 2:28 pm</small></p>
                    </div>
                  </div>
                  <div class="note-item media fade in">
                    <button class="close">×</button>
                    <div>
                      <div>
                        <p class="note-name">Buy a car</p>
                      </div>
                      <p class="note-desc hidden">I'm done with the bus</p>
                      <p><small>Monday 12 May, 3:43 am</small></p>
                    </div>
                  </div>
                  <div class="note-item media fade in">
                    <button class="close">×</button>
                    <div>
                      <div>
                        <p class="note-name">Don't forget my notes</p>
                      </div>
                      <p class="note-desc hidden">I have to read them...</p>
                      <p><small>Wednesday 5 May, 6:15 pm</small></p>
                    </div>
                  </div>
                  <div class="note-item media current fade in">
                    <button class="close">×</button>
                    <div>
                      <div>
                        <p class="note-name">Reset my account password</p>
                      </div>
                      <p class="note-desc hidden">Break security reasons.</p>
                      <p><small>Tuesday 6 May, 3:52 pm</small></p>
                    </div>
                  </div>
                  <div class="note-item media fade in">
                    <button class="close">×</button>
                    <div>
                      <div>
                        <p class="note-name">Call John</p>
                      </div>
                      <p class="note-desc hidden">He have my laptop!</p>
                      <p><small>Thursday 8 May, 2:28 pm</small></p>
                    </div>
                  </div>
                  <div class="note-item media fade in">
                    <button class="close">×</button>
                    <div>
                      <div>
                        <p class="note-name">Buy a car</p>
                      </div>
                      <p class="note-desc hidden">I'm done with the bus</p>
                      <p><small>Monday 12 May, 3:43 am</small></p>
                    </div>
                  </div>
                  <div class="note-item media fade in">
                    <button class="close">×</button>
                    <div>
                      <div>
                        <p class="note-name">Don't forget my notes</p>
                      </div>
                      <p class="note-desc hidden">I have to read them...</p>
                      <p><small>Wednesday 5 May, 6:15 pm</small></p>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="detail-note note-hidden-sm">
              <div class="note-header clearfix">
                <div class="note-back">
                  <i class="icon-action-undo"></i>
                </div>
                <div class="note-edit">Edit Note</div>
                <div class="note-subtitle">title on first line</div>
              </div>
              <div id="note-detail">
                <div class="note-write">
                  <textarea class="form-control" placeholder="Type your note here"></textarea>
                </div>
              </div>
            </div>
          </div>
          <div class="tab-pane fade" id="settings">
            <div class="settings">
              <div class="title">ACCOUNT SETTINGS</div>
              <div class="setting">
                <span> Show Personal Statut</span>
                <label class="switch pull-right">
                <input type="checkbox" class="switch-input" checked>
                <span class="switch-label" data-on="On" data-off="Off"></span>
                <span class="switch-handle"></span>
                </label>
                <p class="setting-info">Lorem ipsum dolor sit amet consectetuer.</p>
              </div>
              <div class="setting">
                <span> Show my Picture</span>
                <label class="switch pull-right">
                <input type="checkbox" class="switch-input" checked>
                <span class="switch-label" data-on="On" data-off="Off"></span>
                <span class="switch-handle"></span>
                </label>
                <p class="setting-info">Lorem ipsum dolor sit amet consectetuer.</p>
              </div>
              <div class="setting">
                <span> Show my Location</span>
                <label class="switch pull-right">
                <input type="checkbox" class="switch-input">
                <span class="switch-label" data-on="On" data-off="Off"></span>
                <span class="switch-handle"></span>
                </label>
                <p class="setting-info">Lorem ipsum dolor sit amet consectetuer.</p>
              </div>
              <div class="title">CHAT</div>
              <div class="setting">
                <span> Show User Image</span>
                <label class="switch pull-right">
                <input type="checkbox" class="switch-input" checked>
                <span class="switch-label" data-on="On" data-off="Off"></span>
                <span class="switch-handle"></span>
                </label>
              </div>
              <div class="setting">
                <span> Show Fullname</span>
                <label class="switch pull-right">
                <input type="checkbox" class="switch-input" checked>
                <span class="switch-label" data-on="On" data-off="Off"></span>
                <span class="switch-handle"></span>
                </label>
              </div>
              <div class="setting">
                <span> Show Location</span>
                <label class="switch pull-right">
                <input type="checkbox" class="switch-input">
                <span class="switch-label" data-on="On" data-off="Off"></span>
                <span class="switch-handle"></span>
                </label>
              </div>
              <div class="setting">
                <span> Show Unread Count</span>
                <label class="switch pull-right">
                <input type="checkbox" class="switch-input" checked>
                <span class="switch-label" data-on="On" data-off="Off"></span>
                <span class="switch-handle"></span>
                </label>
              </div>
              <div class="title">STATISTICS</div>
              <div class="settings-chart">
                <div class="progress visible">
                  <progress class="progress-bar-primary stat1" value="82" max="100"></progress>
                  <div class="progress-info">
                    <span class="progress-name">Stat 1</span>
                    <span class="progress-value">82%</span>
                  </div>
                </div>
              </div>
              <div class="settings-chart">
                <div class="progress visible">
                  <progress class="progress-bar-primary stat1" value="43" max="100"></progress>
                  <div class="progress-info">
                    <span class="progress-name">Stat 2</span>
                    <span class="progress-value">43%</span>
                  </div>
                </div>
              </div>
              <div class="m-t-30" style="width:100%">
                <canvas id="setting-chart" height="300"></canvas>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div> -->
    <!-- END QUICKVIEW SIDEBAR -->