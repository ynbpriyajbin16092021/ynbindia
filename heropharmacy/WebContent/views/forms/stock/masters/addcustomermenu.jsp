<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Customer Master</title>
<%if(request.getParameter("disp") == null){ %>
<jsp:include page="../../template/library.jsp" /> 
<%} %>	
<script type="text/javascript" src="../js/forms/masters/addmenu.js"></script>
</head>
<body onload="loadmenu()">

<%if(request.getParameter("disp") == null){ %>
  	<jsp:include page="../../template/header.jsp" />
<%} %>	  
				 
					<div class="clearfix"></div>
					<div class="breadcrumbs"  style="display: <%= request.getParameter("disp") %>">
			            <div class="col-md-4 col-sm-4">
			                <div class="page-header">
			                    <p class="cursor-pointer mar-top-10 bread-a-style head-font-size yellow-font" href="stockdashboard"><strong><i class="fa fa-users"></i>  Company Master</strong></p>
			                </div>
			            </div>
			            <div class="col-md-8 col-sm-8">
			                <div class="page-header float-right">
			                    <a class="pull-right cursor-pointer bread-a-style content-font-size color-font" href="dashboard?uid=1">Home / Dashboard</a>
			                </div>
			            </div>
			        </div>
					<div class="clearfix"></div>
					<!-- accordian start -->
					<div class="Accordian">	
			<!-- 	<h3>Customer Name:SEOYON-1</h3> -->
<div class="accord-btn">
					<input id="daytext" type="hidden">	
					
						<p  id="myDIV">
  <button class="btn  btn-active" onclick="setday('Monday')">
   Monday
  </button>
  <button class="btn" type="button" data-toggle="collapse" data-target="#collapseExample" aria-expanded="false" aria-controls="collapseExample" onclick="setday('Tuesday')">
Tuesday
  </button>
    <button class="btn"  onclick="setday('Wednesday')">
   Wednesday
  </button>
  <button class="btn"  type="button" onclick="setday('Thursday')">
   Thursday
  </button>
    <button class="btn"  onclick="setday('Friday')">
   Friday
  </button>
  <button class="btn " type="button" onclick="setday('Saturday')">
    Saturday
  </button>
   <button class="btn" type="button" onclick="setday('Sunday')">
   Sunday
  </button>
</p>
</div>
<input id="foottimetext" type="hidden">
<!-- <div class="collapse" id="collapseExample">
  <div class="card card-body"> -->
  <div class="tab-style-section">
  <div class="row">
  <div class="col-md-4">
      <ul class="nav nav-tabs dish-style-tab">

        <li class="active" onclick="tabevent('Breakfast')">
          <a id="Breakfast" href="#tab1" data-toggle="tab">Breakfast</a>
        </li>
        <li onclick="tabevent('Lunch')">
          <a id="Lunch" href="#tab2" data-toggle="tab">Lunch</a>
        </li>
        <li onclick="tabevent('Dinner')">
          <a id="Dinner" href="#tab3" data-toggle="tab">Dinner</a>
        </li>
        <li onclick="tabevent('Supper')">
          <a id="Supper" href="#tab4" data-toggle="tab">Supper</a>
        </li>
      </ul>
      </div>
      </div>
      <div class="tab-content">
        <div class="col-md-12 tab-pane active new-formgroup-accordian" id="tab1">
        <div class="col-md-5">
           <div class="form-group form-label-new">
    <label for="exampleFormControlSelect1">Dish</label>
    <select class="form-control" id="dishnameselect" onchange="getdishtype()">
     <c:forEach items="${dishList}" var="dishname" >
										<option value="${dishname.value}">
										            ${dishname.label}
										        </option> 
									  </c:forEach>
    </select>
  </div>
  </div>
    <div class="col-md-5">
           <div class="form-group form-label-new">
    <label for="exampleFormControlSelect1">DishType</label>
    <select class="form-control" id="dishtypeselect">
    
    </select>
  </div>
  </div>
    <div class="col-md-2">
      <button class="button-bg button-space" onclick="addmenu()">Add<i class="fa fa-plus-circle"></i></button>
  </div>
  <div class="row col-md-12 form-control-style-table">
                        <table  class="hero-settings-table"  style="width:100%" id="addmenuDT" >
                           <thead>
                              <tr>
                                <!--  <th> # </th> -->
                                 <th> Dish</th>
                                 <th> DishType </th>
                                 <th> Discount </th>
                                 <th> Total Count </th>
                                 <th> Action </th>
                              </tr>
                           </thead>
                           <tbody>
                            <!--  <td>1</td> 
                              <td>Idly</td> 
                               <td>Rice</td> 
                                <td><input type="text" class="form-control" value="10%" ></td> 
                                 <td><input type="text" class="form-control"value="500"  ></td> 
                                   <td><i onclick="deleteStockTransferHistory('2/9/2021 12:00','16','6')" class="fa fa-trash-o"></i></td> -->
                           </tbody>
                        </table>
                     </div>
        </div>
  <!--       <div class="tab-pane" id="tab2">
          <p>Tab 2 content goes here...</p>
        </div>
        <div class="tab-pane" id="tab3">
          <p>Tab 3 content goes here...</p>
        </div>
        <div class="tab-pane" id="tab4">
          <p>Tab 4 content goes here...</p>
        </div> -->

  </div>
  </div>
<!-- </div>	
</div> -->
<div class="add-menu">
<button data-target="#modalLoading" data-toggle="modal"  class="btn btn-primary" type="button" onclick="savecustomermenu()">Save</button></div>	
</div>	
<div class="modal fade" id="modalLoading" tabindex="-1" role="dialog" aria-hidden="true">
<div class="modal-dialog">
  <div class="modal-content">
  	<div class="modal-header">
  		<button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title changeTitle">Please Wait your Request will be processing</h4>
      </div>
    
    <div class="modal-body">
      <div class="popDel" align="center">
        	<div class="col-md-12 changeContent">
        		
        	</div>
        	<div class="clearfix"></div>
      </div>
    </div>

  </div>
</div>
</div>
<!-- accordian end -->
	<script>
         $(document).ready(function(){
         	$("#stocktransferDT").dataTable();
         });
      </script>				
				<script>
    
         var header = document.getElementById("myDIV");
         var btns = header.getElementsByClassName("btn");
         for (var i = 0; i < btns.length; i++) {
           btns[i].addEventListener("click", function() {
           var current = document.getElementsByClassName("btn-active");
           current[0].className = current[0].className.replace(" btn-active", "");
           this.className += " btn-active";
           });
         }
          
           
         
      </script>	
	<style>
		.btn {
  border: none;
  outline: none;
  padding: 10px 16px;
  background-color: #f1f1f1;
  cursor: pointer;
  font-size: 18px;
}

/* Style the active class, and buttons on mouse-over */


/* Style the active class, and buttons on mouse-over */
.btn-active, .btn:hover {
  background-color: #666;
  color: white;
}


.col-md-4 {
    width: 47.333333%;
}
		</style>	
<jsp:include page="../../template/footer.jsp" /> 

</body>
</html>

