<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>HERO Home</title>
<jsp:include page="../home/homelibrary.jsp" />
<script type="text/javascript">

function gototxnpage(tokenkeyvalidatorurl,applncontxt)
{
	var tokenkey = localStorage.getItem("tokenkey");
	window.open(tokenkeyvalidatorurl+tokenkey,'_blank');
}
</script>
</head>

<body>
<jsp:include page="../home/homeheader.jsp" />
					<div class="clearfix"></div>
					<div class="breadcrumbs"  style="display: <%= request.getParameter("disp") %>">
			            <div class="col-md-4 col-sm-4">
			                <div class="page-header">
			                        <a class="cursor-pointer bread-a-style content-font-size color-font" href="stockdashboard">DashBoard</a>
			                </div>
			            </div>
			            <div class="col-md-8 col-sm-8">
			                <div class="page-header float-right">
			                    <a class="pull-right cursor-pointer bread-a-style content-font-size color-font" href="heroadminsettings">Config System Settings</a>
			                </div>
			            </div>
			        </div>
					<div class="clearfix"></div>

			<%-- 		<div class="col-md-12">
					<c:forEach items="${heroappConfigList}" var="appconfig" >
							<div class="token-subjects">
								<div class="token-subjects-img">
									<img src="/heroadmin/resources/images/heroIcons/${appconfig.logo }" >
								</div>
								<div class="token-subject-p">
									<a class="content-font-size color-font" onclick="gototxnpage('${appconfig.tokenkeyvalidatorurl }','${appconfig.applncontxt }');" >${appconfig.appname }</a>
								</div>
							</div>
					</c:forEach>
					</div> --%>
					
					<div>
					<div class="col-md-12">
						<div class="dashboard-long-div margin-right-5px">
							<div class="dashboard-ld-header head-font-size white-font color-bg">
								<p><span style="font-size:18px;font-weight:bold">${usersCount } </span> Users </p>
							</div>
							<div class="dashboard-ld-content gray-font white-bg content-font-size">
								<p class="color-font">Recently created Users</p>
								<ul>
									<c:forEach items="${userRecentList}" var="userList" >
									<li><span>${userList.username }</span><span class="pull-right color-font">${userList.usertype_name }</span></li>
									</c:forEach>
								</ul>
							</div>
						</div>
						<div class="dashboard-long-div margin-right-5px">
							<div class="dashboard-ld-header head-font-size white-font color-bg">
								<p><span style="font-size:18px;font-weight:bold">${taxCount } </span> Taxes </p>
							</div>
							<div class="dashboard-ld-content gray-font white-bg content-font-size">
								<p class="color-font">Recently created Taxes</p>
								<c:forEach items="${taxRecentList}" var="taxList" >
								<ul>
									
									<li><span>${taxList.TAX_NAME }</span><span class="pull-right color-font">${taxList.TAX_RATE }</span></li>
									</c:forEach>
								</ul>
							</div>
						</div>
						
						<div class="dashboard-long-div">
							<div class="dashboard-ld-header head-font-size white-font color-bg">
								<p><span style="font-size:18px;font-weight:bold">${currencyCount }  </span> Currency </p>
							</div>
							<div class="dashboard-ld-content gray-font white-bg content-font-size">
								<p class="color-font">Recently created Currencies</p>
								<c:forEach items="${currencyRecentList}" var="currencyList" >
								<ul>
									<li><span>${currencyList.currency }</span><span class="pull-right color-font">${currencyList.gcs_html_code }</span></li>
									</c:forEach>
								</ul>
							</div>
						</div>
					</div>
					
					<div class="clearfix"></div>
					<div class="col-md-12">
					
					<div class="dashboard-long-div margin-right-5px">
							<div class="dashboard-ld-header head-font-size white-font color-bg">
								<p><span style="font-size:18px;font-weight:bold">${storeCount } </span> Stores </p>
							</div>
							<div class="dashboard-ld-content gray-font white-bg content-font-size">
								<p class="color-font">Recently created Stores</p>
								<c:forEach items="${storeRecentList}" var="storeList" >
								<ul>
									
									<li><span>${storeList.store_name }</span><span class="pull-right color-font">${storeList.phone }</span></li>
									</c:forEach>
								</ul>
							</div>
						</div>
						<div class="dashboard-long-div margin-right-5px">
							<div class="dashboard-ld-header head-font-size white-font color-bg">
								<p><span style="font-size:18px;font-weight:bold">${stockmanagerCount } </span> Stockmanager </p>
							</div>
							<div class="dashboard-ld-content gray-font white-bg content-font-size">
								<p class="color-font">Recently created Stock Manager </p>
								<c:forEach items="${stockmanagerRecentList}" var="managList" >
								<ul>
									
									<li><span>${managList.username }</span><span class="pull-right color-font">${managList.phone }</span></li>
									</c:forEach>
								</ul>
							</div>
						</div>
					
						
					<%-- <div class="dashboard-long-div ">
							<div class="dashboard-ld-header head-font-size white-font color-bg">
								<p><span style="font-size:18px;font-weight:bold">${receptCount } </span> Physician </p>
							</div>
							<div class="dashboard-ld-content gray-font white-bg content-font-size">
								<p class="color-font">Recently created Physician </p>
								<c:forEach items="${receptRecentList}" var="receptList" >
								<ul>
									
									<li><span>${receptList.username }</span><span class="pull-right color-font">${receptList.phone }</span></li>
									</c:forEach>
								</ul>
							</div>
						</div> --%>
						
					</div>
					</div>
					
					


<jsp:include page="../home/homefooter.jsp" />
</body>
</html>

