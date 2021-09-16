<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Users & Permissions</title>
<%if(request.getParameter("disp") == null){ %>
<jsp:include page="../home/homelibrary.jsp" />
<%} %>
<script type="text/javascript" src="../js/forms/admin/masters/masters.js"></script>
</head>

<style type="text/css">
        .mainmenuhidedisp
        {
            display: none;
        }
        .submenuhidedisp
        {
            display: none;
        }
        
.col-sm-4 {
    float:right;
}
.active{
 background-image: linear-gradient(to bottom,#fede4b, #feb521) !important;
	border: 1px solid #feb521 !important;
	color: #896614 !important;
	text-shadow: 0 1px 0 rgba(255, 255, 0, 50);
	font-size:15px !important;
	font-weight: bold !important;
	
}
.nav-tabs>li>a, .nav-tabs>li>a:hover, .nav-tabs>li>a:focus {
   color: #896614 !important;
   
	
}
</style>
    
<body onload="loaduserrole()">

<%if(request.getParameter("disp") == null){ String applnid = request.getParameter("applnid");%>
  	<jsp:include page="../home/homeheader.jsp" />	
  	<input type="hidden" name="applnid" id="applnidli" value="${applnid }" />		
<% 
}else if(request.getParameter("disp").equals("n")){ String applnid = request.getParameter("applnid"); %>
	<script type="text/javascript">
	loaduserrole();
	</script>	
	<input type="hidden" name="applnid" id="applnidli" value="${applnid }" />	
<%} %>

<div class="clearfix"></div>
					<div class="breadcrumbs"  style="display: <%= request.getParameter("disp") %>">
			            <div class="col-md-4 ">
			                <div class="page-header">
			                        <p class="cursor-pointer bread-a-style head-font-size yellow-font " >User Permissions Master</p>
			                </div>
			            </div>
			                  <%if(request.getParameter("disp") == null){ %>
			            <div class="col-md-8 col-sm-8">
			                <div class="page-header float-right">
			                    <a class="pull-right cursor-pointer bread-a-style content-font-size color-font" href="herohome">Home / Dashboard</a>
			                </div>
			            </div>
			        </div>
					<div class="clearfix"></div>	
 <%} %>
					<div class="width_100 overcome-col-padd-10 ">
					<div class="width_100 gray-font white-bg content-font-size" >
						<div class="form-page white-bg akpad3">
							<input type="hidden" value="<%=session.getAttribute("uid")%>" id="herouseridtextforuserrole">
							
							
							<div class="width_100">
								<ul class="nav nav-tabs">
									<%if(request.getParameter("disp") == null){ %>
									 <c:forEach items="${applnTypeList }" var="applnType">
										<%-- <c:choose>
										    <c:when test="${applnType.value == applnid}">
										        <li class="active"><a href="userrole?applnid=${applnType.value}">${applnType.label}</a></li>
										    </c:when>    
										    <c:otherwise>
										        <li><a href="userrole?applnid=${applnType.value}">${applnType.label}</a></li>
										    </c:otherwise>
										</c:choose> --%>
									</c:forEach>		
									<%}else if(request.getParameter("disp").equals("n")){ %>
									<c:forEach items="${applnTypeList }" var="applnType">
										<c:choose>
										    <c:when test="${applnType.value == applnid}">
										        <li class="active"><a onclick="loadsettinguserrole('userrole?applnid=${applnType.value}&disp=n')">${applnType.label}</a></li>
										    </c:when>    
										    <c:otherwise>
										        <li><a onclick="loadsettinguserrole('userrole?applnid=${applnType.value}&disp=n')">${applnType.label}</a></li>
										    </c:otherwise>
										</c:choose>
									</c:forEach>
									<%} %>
								</ul>
							
							
							<form class="form-horizontal">   
								<div class="width_100">
									
					
									<!-- <select id='usertypeselect' class="form-control selectSer" onchange="getusertypemenus(this.value)"> -->
										<c:forEach items="${usertypeList}" var="usertype" >
											<c:choose>
											    <c:when test="${usertype.index == 0}">
											    <input type="hidden" id="usertypeselect" value="${usertype.usertypeid}">
											        <div onclick="getusertypemenus('${usertype.usertypeid}')" class="token-subjects border${usertype.usertypeid}" style="position:relative;cursor:pointer">
														<div class="token-subjects-img">
														<img src="/heroadmin/resources/images/heroIcons/${usertype.usertypeimg}" >
														</div>
														<div class="token-subject-p">
														<a class="content-font-size color-font"> ${usertype.usertypename}</a>
														</div>
														<div class="token-subject-p">
														${usertype.deptname}
														</div>
													</div>
											    </c:when>    
											    <c:otherwise>
											        <div onclick="getusertypemenus(${usertype.usertypeid})" class="token-subjects border${usertype.usertypeid} " style="position:relative;cursor:pointer">
														<div class="token-subjects-img">
														<img src="/heroadmin/resources/images/heroIcons/${usertype.usertypeimg}" >
														</div>
														<div class="token-subject-p">
														<a class="content-font-size color-font"> ${usertype.usertypename}</a>
														</div>
														<div class="token-subject-p">
														${usertype.deptname}
														</div>
													</div>
											    </c:otherwise>
											</c:choose>
										</c:forEach>
										
										<style>
										.border-active{
										border-bottom:3px solid #feb824;
										z-index: 777;
										}
										.border-top-userrole{
										border-top: 3px solid #6259ca;
										top:-3px;
										position:relative
										}
										</style>
										
										<script type="text/javascript">
										$(document).ready(function(){
											var defaultusertypeid = getParameterByName('usertypeid');
											selectusertype(defaultusertypeid);
										});
										function selectusertype(id){
											$(".token-subjects").removeClass("border-active");
											$(".border"+id).addClass("border-active");
											$("#usertypeselect").val(id);
										}
										
										</script>
										
									<!-- </select>  -->
								</div>     	
				
									<%-- <div class="form-group margin-top-10">
										<div class="col-md-4">
				                      		<label class="control-label">User Type</label>
				                      	</div>
				                      	<div class="col-sm-4">
											 <select id='usertypeselect' class="form-control selectSer" onchange="getusertypemenus(this.value)">
											 <c:forEach items="${usertypeList}" var="usertype" >                  
											        <option value="${usertype.value}">
											            ${usertype.label}
											        </option>                    
											    </c:forEach>
											</select> 
				                      </div>
				                      
				                      <div class="col-sm-4 margin-top-10" align="right">
				                      	<button type="button" class="btn btn-primary " data-dismiss="modal" id="userrolesavebtn" onclick="saveusermenus();">Save</button>
				                      </div>
				                    </div> --%>
								
									<div class="col-sm-6 akcontent-div-heightnw border-top-userrole" id="style-5">
										<div class="form-group">
										<input type="hidden" id="userrolecount" value="${usermenuList.size() }">
											<table class="table table-hover selectCheckTabel">
									            <c:forEach items="${usermenuList}" var="usermenu"> 
										              <tr class="filterwrapper"> 
										              <input type="hidden" id="menudetailscheck${usermenu.index }" value="${usermenu.menudetails}"></input>
											                <td width="20px" ${usermenu.style }> 
											                ${usermenu.space }  
											                <input type="checkbox" id="menuid${usermenu.index }" 
											                onclick="getmenuid(${usermenu.index },${usermenu.issubmodule },${usermenu.parentid },${usermenu.moduleid },'${ usermenu.property}',$(this),'${ usermenu.isreportmenu}')"
											                value="${ usermenu.modulename} " class="${ usermenu.property}"> 
											               ${ usermenu.modulename} 
											                </td>  
										              </tr>
									            </c:forEach>                        
							            </table>
										</div>
									</div>
									<div class="col-sm-6 akcontent-div-heightnw border-top-userrole" id="style-5" >
										<div class="form-group" id="reportsMenuDiv">
											<input type="hidden" id="userreportrolecount" value="${userreportmenuList.size() }">
											<table class="table table-hover selectCheckTabel">
									            <c:forEach items="${userreportmenuList}" var="userreportmenu"> 
										              <tr class="filterwrapper"> 
										              <input type="hidden" id="reportmenudetailscheck${userreportmenu.index }" value="${userreportmenu.menudetails}"></input>
											                <td width="20px" ${userreportmenu.style }  > 
											                ${userreportmenu.space }  
											                <input type="checkbox" id="reportmenuid${userreportmenu.index }" 
											                onclick="getreportmenuid(${userreportmenu.index },${userreportmenu.issubmodule },${userreportmenu.parentid },${userreportmenu.moduleid },'${ userreportmenu.property}',$(this))"
											                value="${ userreportmenu.modulename} " class="${ userreportmenu.property}"> 
											               ${ userreportmenu.modulename} 
											                </td>  
										              </tr>
									            </c:forEach> 
											</table>
										</div>
									</div>
									
									<style>
									#reportsMenuDiv{
									display:none;
									}
									</style>
									
				                      <div class="col-sm-4 margin-top-10" align="right">
				                      	<button type="button" class="btn btn-primary akbtnmar" id="userrolesavebtn" onclick="saveusermenus();">Save</button>
				                      </div>
									<div class="clearfix"></div>
							</form>
							
							<div class="clearfix"></div>
							</div>
					</div>
				</div>
	    	</div>
	    	
</section>



<script type="text/javascript">
$(document).ready(function(){
	$("input[type='checkbox'].child").on("change", function(){
		var link = $(this).closest(":has(tr td input[type='checkbox'].parent)").find("td input[type='checkbox'].parent:first");
		link.attr('checked',true);
		console.log(link);
	});
});
</script>


<style>
.mysection{
border:2px solid #0e7bdd;
border-top:none;
}
.padding-10{
padding:10px;
}
</style>

<%if(request.getParameter("disp") == null){ %>
  	<jsp:include page="../home/homefooter.jsp" />
<%} %>
</body>
</html>





<!-- OLD STYLE Start-->



<%-- 
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Users & Permissions</title>
<%if(request.getParameter("disp") == null){ %>
<jsp:include page="../home/homelibrary.jsp" />
<%} %>
<script type="text/javascript" src="../js/forms/admin/masters/masters.js"></script>
</head>

<style type="text/css">
        .mainmenuhidedisp
        {
            display: none;
        }
        .submenuhidedisp
        {
            display: none;
        }
        
.col-sm-4 {
    float:right;
}
.active{
 background-image: linear-gradient(to bottom,#fede4b, #feb521) !important;
	border: 1px solid #feb521 !important;
	color: #896614 !important;
	text-shadow: 0 1px 0 rgba(255, 255, 0, 50);
	font-size:15px !important;
	font-weight: bold !important;
	
}
.nav-tabs>li>a, .nav-tabs>li>a:hover, .nav-tabs>li>a:focus {
   color: #896614 !important;
   
	
}
</style>
    
<body onload="loaduserrole()">

<%if(request.getParameter("disp") == null){ String applnid = request.getParameter("applnid");%>
  	<jsp:include page="../home/homeheader.jsp" />	
  	<input type="hidden" name="applnid" id="applnidli" value="${applnid }" />		
<% 
}else if(request.getParameter("disp").equals("n")){ String applnid = request.getParameter("applnid"); %>
	<script type="text/javascript">
	loaduserrole();
	</script>	
	<input type="hidden" name="applnid" id="applnidli" value="${applnid }" />	
<%} %>

<div class="clearfix"></div>
					<div class="breadcrumbs"  style="display: <%= request.getParameter("disp") %>">
			            <div class="col-md-4 ">
			                <div class="page-header">
			                        <p class="cursor-pointer bread-a-style head-font-size yellow-font " >User role</p>
			                </div>
			            </div>
			            <div class="col-md-8 col-sm-8">
			                <div class="page-header float-right">
			                    <a class="pull-right cursor-pointer bread-a-style content-font-size color-font" href="herohome">Home / Dashboard</a>
			                </div>
			            </div>
			        </div>
					<div class="clearfix"></div>	

					<div class="width_100 overcome-col-padd-10 ">
					<div class="width_100 gray-font white-bg content-font-size content-div-heightnw" id="style-5">
						<div class="form-page white-bg">
							<input type="hidden" value="<%=session.getAttribute("uid")%>" id="herouseridtextforuserrole">
							<div class="width_100">
								<ul class="nav nav-tabs">
									<%if(request.getParameter("disp") == null){ %>
									 <c:forEach items="${applnTypeList }" var="applnType">
										<c:choose>
										    <c:when test="${applnType.value == applnid}">
										        <li class="active"><a href="userrole?applnid=${applnType.value}">${applnType.label}</a></li>
										    </c:when>    
										    <c:otherwise>
										        <li><a href="userrole?applnid=${applnType.value}">${applnType.label}</a></li>
										    </c:otherwise>
										</c:choose>
									</c:forEach>		
									<%}else if(request.getParameter("disp").equals("n")){ %>
									<c:forEach items="${applnTypeList }" var="applnType">
										<c:choose>
										    <c:when test="${applnType.value == applnid}">
										        <li class="active"><a onclick="loadsettinguserrole('userrole?applnid=${applnType.value}&disp=n')">${applnType.label}</a></li>
										    </c:when>    
										    <c:otherwise>
										        <li><a onclick="loadsettinguserrole('userrole?applnid=${applnType.value}&disp=n')">${applnType.label}</a></li>
										    </c:otherwise>
										</c:choose>
									</c:forEach>
									<%} %>
								</ul>
							
							<div class="mysection padding-10">	
							<form class="form-horizontal">   
								<div class="width_100">
									
					
									<!-- <select id='usertypeselect' class="form-control selectSer" onchange="getusertypemenus(this.value)"> -->
										<c:forEach items="${usertypeList}" var="usertype" >
											<c:choose>
											    <c:when test="${usertype.index == 0}">
											    <input type="hidden" id="usertypeselect" value="${usertype.usertypeid}">
											        <div onclick="getusertypemenus('${usertype.usertypeid}')" class="token-subjects border${usertype.usertypeid} border-active" style="position:relative;cursor:pointer">
														<div class="token-subjects-img">
														<img src="/heroadmin/resources/images/heroIcons/${usertype.usertypeimg}" >
														</div>
														<div class="token-subject-p">
														<a class="content-font-size color-font"> ${usertype.usertypename}</a>
														</div>
														<div class="token-subject-p">
														${usertype.deptname}
														</div>
													</div>
											    </c:when>    
											    <c:otherwise>
											        <div onclick="getusertypemenus(${usertype.usertypeid})" class="token-subjects border${usertype.usertypeid} " style="position:relative;cursor:pointer">
														<div class="token-subjects-img">
														<img src="/heroadmin/resources/images/heroIcons/${usertype.usertypeimg}" >
														</div>
														<div class="token-subject-p">
														<a class="content-font-size color-font"> ${usertype.usertypename}</a>
														</div>
														<div class="token-subject-p">
														${usertype.deptname}
														</div>
													</div>
											    </c:otherwise>
											</c:choose>
										</c:forEach>
										
										<style>
										.border-active{
										border-bottom:3px solid #feb824;
										z-index: 777;
										}
										.border-top-userrole{
										border-top: 3px solid #6259ca;
										top:-3px;
										position:relative
										}
										</style>
										
										<script type="text/javascript">
										function selectusertype(id){
											$(".token-subjects").removeClass("border-active");
											$(".border"+id).addClass("border-active");
											$("#usertypeselect").val(id);
										}
										</script>
										
									<!-- </select>  -->
								</div>     	
				
									<div class="form-group margin-top-10">
										<div class="col-md-4">
				                      		<label class="control-label">User Type</label>
				                      	</div>
				                      	<div class="col-sm-4">
											 <select id='usertypeselect' class="form-control selectSer" onchange="getusertypemenus(this.value)">
											 <c:forEach items="${usertypeList}" var="usertype" >                  
											        <option value="${usertype.value}">
											            ${usertype.label}
											        </option>                    
											    </c:forEach>
											</select> 
				                      </div>
				                      
				                      <div class="col-sm-4 margin-top-10" align="right">
				                      	<button type="button" class="btn btn-primary " data-dismiss="modal" id="userrolesavebtn" onclick="saveusermenus();">Save</button>
				                      </div>
				                    </div>
								
									<div class="col-sm-12 border-top-userrole">
										<div class="form-group">
										<input type="hidden" id="userrolecount" value="${usermenuList.size() }">
											<table class="table table-hover selectCheckTabel">
									            <c:forEach items="${usermenuList}" var="usermenu"> 
										              <tr class="filterwrapper"> 
										              <input type="hidden" id="menudetailscheck${usermenu.index }" value="${usermenu.menudetails}"></input>
											                <td width="20px" ${usermenu.style }  > 
											                ${usermenu.space }  
											                <input type="checkbox" id="menuid${usermenu.index }" 
											                onclick="getmenuid(${usermenu.index },${usermenu.issubmodule },${usermenu.parentid },${usermenu.moduleid },'${ usermenu.property}',$(this))"
											                value="${ usermenu.modulename} " class="${ usermenu.property}"> 
											               ${ usermenu.modulename} 
											                </td>  
										              </tr>
									            </c:forEach>                        
							            </table>
										</div>
									</div>
									
				                      <div class="col-sm-4 margin-top-10" align="right">
				                      	<button type="button" class="btn btn-primary" id="userrolesavebtn" onclick="saveusermenus();">Save</button>
				                      </div>
									<div class="clearfix"></div>
							</form>
							</div>
							<div class="clearfix"></div>
							</div>
					</div>
				</div>
	    	</div>
	    	
</section>



<script type="text/javascript">
$(document).ready(function(){
	$("input[type='checkbox'].child").on("change", function(){
		var link = $(this).closest(":has(tr td input[type='checkbox'].parent)").find("td input[type='checkbox'].parent:first");
		link.attr('checked',true);
		console.log(link);
	});
});
</script>


<style>
.mysection{
border:2px solid #0e7bdd;
border-top:none;
}
.padding-10{
padding:10px;
}
</style>

<%if(request.getParameter("disp") == null){ %>
  	<jsp:include page="../home/homefooter.jsp" />
<%} %>
</body>
</html> --%>