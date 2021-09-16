<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
 <%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
    

<div class="fullview">
<div class="full-width-header white-bg">
	<div class="col-md-12">
		<div class="col-md-7 padding-header">
			
	 	<div class="logo_div client-logo" style="margin: 1px 5px -14px -28px;">
				<img class="responsive" id="logoImage"/>
				<script type="text/javascript">
						var img = new Image();
						img.onLoad = getlogo('logoImage');
				</script>
			</div> 
			 <div class="logo_div " style="width: 6%;">
				<img src="/heroadmin/resources/images/hero.png"  />
			</div>
			
			<div class="col-md-10">
				<span class="product-button-style color-bg white-font">YNB</span>
				<p class="product-p-style content-font-size gray-font italic-font"> <%-- <%= session.getAttribute("companyname") %>, --%><%=session.getAttribute("usertypedesc")%></p>
			</div>
		</div>
		<div class="col-md-5 ">
			<ul class="right-menu-fonts pull-right">
				<%if(session.getAttribute("uid") != null )
			        	 {
			        	 %>
			         <%if((Integer.parseInt((String)session.getAttribute("uid"))) != 1 )
			        	 {
			        	 %>
                        <li><a href="pos?posid=0" target="new" class="search-trigger">POS</a></li>
                        <%}int usertype = 0;
			           //System.out.println("UserType        "+session.getAttribute("usertypestr"));
			           String usertypestr = (String) session.getAttribute("usertypestr");
			           if(usertypestr != null)
			           {
			        	   usertype = Integer.parseInt(usertypestr);
			           }
			           %>
			           
                          <%} %>
				<li><a href="herohome"><i class="fa fa-home gray-font"></i></a></li> 
				<%if(session.getAttribute("applntype") != null )
			        	 {
			        	 %>
			         <%if((Integer.parseInt((String)session.getAttribute("applntype"))) !=0)
			        	 {
			        	 %>
				<li><a href="lowstockalert"><i class="fa fa-bell gray-font"></i></a><span class="notification-lowstock"><%=session.getAttribute("lowstockcount") %><span></li>
				 <%}int applntype = 0;
			           //System.out.println("applntype        "+session.getAttribute("applntypestr"));
			           String applntypestr = (String) session.getAttribute("applntypestr");
			           if(applntypestr != null)
			           {
			        	   applntype = Integer.parseInt(applntypestr);
			           }
			           %>
			           
                          <%} %>
				<li class="padd-10">
				
					
					
                  <div class="product-intial-style button-bg" >
                       
						      <c:set var = "string1" value = "${username}"/>
						      <c:set var = "string2" value = "${fn:substring(string1, 0, 1)}" />
						      <c:set var = "string3" value = "${usertypedesc}"/>
						      <c:set var = "string4" value = "${fn:substring(string3, 0, 1)}" />
						
						      <span> ${string2}${string4}</span>
						      
					</div>
					<div class="product-sec-style gray-font content-font-size" >
					Hi,<span class="text-style-p"> <%=session.getAttribute("username")%>,  <%=session.getAttribute("usertypedesc")%></span><br />
					<a onclick="openchangepwpopup();" data-toggle="modal" data-target="#changepassword-popup" id="changepwbtn" class="cursor-pointer color-font content-font-size">change password</a>
					<span class="color-font content-font-size"> | </span>
					<%if((session.getAttribute("tokenkey")) == null )
			        	 {
			        %>
					<a href="signout" class="cursor-pointer color-font content-font-size">logout</a>
					<%}else{ %>
					<a onclick="tokenSignout()" class="cursor-pointer color-font content-font-size">logout</a>
					
					<%} %>
					</div>
				</li>
				
			</ul>
		</div>
		
	</div>
	<div class="clearfix"></div>
</div>


<div class="navview color-bg white-font" style="display: <%= (request.getParameter("disp") != null && request.getParameter("disp").equalsIgnoreCase("N") ) ? "none":""%>;">
  <aside id="left-panel" class="left-panel">
        <nav class="navbar navbar-expand-sm navbar-default color-bg white-font">
            <div class="navbar-header" style="color:white">
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#main-menu" aria-controls="main-menu" aria-expanded="false" aria-label="Toggle navigation">
                    <i class="fa fa-bars"></i>
                </button>
                <div class="sidebar-top">
                <div class="logopanel">
         
          <input type="hidden" value="<%=session.getAttribute("uid")%>" id="inventoryuseridtext">
          <input type="hidden" value="<%=session.getAttribute("usertype")%>" id="inventoryusertypetext">
          <input type="hidden" class="form-control CustomerSearchInput" id="currsymboltext" value="<%= session.getAttribute("currencyname") %>">
    	  <input type="hidden" class="form-control CustomerSearchInput" id="currdecimaltext" value="<%= session.getAttribute("currencydecimal") %>">
		  <input type="hidden" class="form-control CustomerSearchInput" id="storeidtext" value="<%= session.getAttribute("storeid") %>">
		  
        </div>
            
          </div>
            </div>
            <div id="main-menu" class="main-menu collapse navbar-collapse">
                <ul class="nav navbar-nav color-bg white-font">
               
              
               
                 <%
            List mainmenulist = (List) session.getAttribute("mainmenuList");
            if(mainmenulist != null)
            {
            for(int mainmenuloop = 0;mainmenuloop<mainmenulist.size();mainmenuloop++)
            {
            	Map<String,Object> mainmenumap = (Map)mainmenulist.get(mainmenuloop);
            	
            if(mainmenumap.get("modulepath").equals("")){ %>
            <li class="menu-item-has-children dropdown selecteddropdown<%=mainmenumap.get("moduleid") %>" >  
              <a class="dropdown-toggle white-font homeheder-hover-a selectedaareaexpanded<%=mainmenumap.get("moduleid") %>" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
              <i class="<%=mainmenumap.get("fafafont") %>" aria-hidden="true"></i>
              <span style="padding-left:16px"><%=mainmenumap.get("modulename") %></span>
              <span class="fa arrow"></span>
              </a>
              <ul class="sub-menu color-bg  white-font children dropdown-menu selectedul<%=mainmenumap.get("moduleid") %>" >
              <%
              List submenulist = (List) mainmenumap.get("submenuList");
              for(int submenuloop = 0;submenuloop<submenulist.size();submenuloop++)
              {
            	  Map<String,Object> submenumap = (Map)submenulist.get(submenuloop);
            	  %>
            	  
                <li><a class="selectedsubmenu<%=submenumap.get("moduleid") %>" onclick="setactivemenu(<%=submenumap.get("moduleid") %>,1,<%=mainmenumap.get("moduleid") %>)" href="<%=submenumap.get("modulepath") %>"><%=submenumap.get("modulename") %></a></li>
            	  <%
              }
              %>
               </ul>
            </li>
            
            <%}else{ %>
            <li><a class="selectedsubmenu<%=mainmenumap.get("moduleid") %>" onclick="setactivemenu(<%=mainmenumap.get("moduleid") %>,0,'')" href="<%=mainmenumap.get("modulepath") %>"> <i class="<%=mainmenumap.get("fafafont") %>" aria-hidden="true"></i>  <%=mainmenumap.get("modulename") %></span></a></li>
            <%  }
            
            }
            }
            %>
                    
					
                </ul>
                
                
                <script>
                	function setactivemenu(menuid, issubmenu, mainmenuid){
                		localStorage.removeItem("adminmenuid");
                		localStorage.removeItem("adminissubmenu");
                		localStorage.removeItem("adminmainmenuid");
                		localStorage.setItem('adminmenuid', menuid);
                		localStorage.setItem('adminissubmenu', issubmenu);
                		localStorage.setItem('adminmainmenuid', mainmenuid);	
                	}
                	
                	$(document).ready(function(){
                		var adminmenuid = localStorage.getItem('adminmenuid');
                		var adminissubmenu = localStorage.getItem('adminissubmenu');
                		var adminmainmenuid = localStorage.getItem('adminmainmenuid');
                		if(adminissubmenu == 1){
                			$(".selectedul"+adminmainmenuid).addClass("show");
                			$(".selecteddropdown"+adminmainmenuid).addClass("show");
                			console.log(adminmenuid);
                			$("a.selectedsubmenu"+adminmenuid).addClass('submenuactive');
                			$(".selectedaareaexpanded"+adminmainmenuid).attr("aria-expanded","true");
                		}
                	});
                </script>
            </div>
        </nav>
    </aside>
</div>
        <div class="contentview">
          <div id="right-panel" class="right-panel">
   
<div class="modal fade" id="changepassword-popup" tabindex="-1" role="dialog" aria-hidden="true">
  <div class="modal-dialog topZero">
    <div class="modal-content">
      <div class="modal-header">
 <h4 class="modal-title">Change Password <button type="button" class="close" data-dismiss="modal">&times;</button></h4>
  
      </div>
      <div class="modal-body">
       
        <div class="row">
          <div  class="col-sm-10 form-group">
            <label>Current Password</label>
            <input placeholder="" class="form-control form-white" type="password" id="currentpasswordtext">
          </div>
          <div  class="col-sm-10 form-group">
            <label>New Password</label>
            <input placeholder="" class="form-control form-white" type="password" id="newpasswordtext">
          </div>
          <div  class="col-sm-10 form-group">
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

<style>
.homeheder-hover-a a:hover{
	background-color: #6259ca !important;
}
</style>


