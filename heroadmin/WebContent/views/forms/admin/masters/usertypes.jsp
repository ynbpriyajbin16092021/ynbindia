<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Departments </title>
<%if(request.getParameter("disp") == null){ %>
<jsp:include page="../home/homelibrary.jsp" />
<%} %>
<script type="text/javascript" src="../js/forms/admin/masters/masters.js"></script>
</head>
<body onload="loadusertypes()">
<%if(request.getParameter("disp") == null){ %>
  <jsp:include page="../home/homeheader.jsp" />
<%} %>






					<div class="clearfix"></div>
					<div class="breadcrumbs"  style="display: <%= request.getParameter("disp") %>">
			            <div class="col-md-4 col-sm-4">
			                <div class="page-header">
			                        <p class="cursor-pointer mar-top-10 bread-a-style head-font-size yellow-font" href="stockdashboard"><strong><i class="fa fa-user-circle-o"></i>Department Master</strong></p>
			                </div>
			            </div>
			            <div class="col-md-8 col-sm-8">
			                <div class="page-header float-right">
			                    <a class="pull-right cursor-pointer bread-a-style content-font-size color-font" href="herohome">Home / Dashboard</a>
			                </div>
			            </div>
			        </div>


				 <div class="clearfix"></div>
					
					<div class="col-md-12 overcome-col-padd-10  ">
						<div class="col-md-7 overcome-col-padd-10 ">
							<div class="width_100">
								
								<div class="width_100 gray-font white-bg content-font-size content-div-height">
								
									<div class="col-md-12 full-padding-20">
									<table id="usertypeDT" class="hero-settings-table">
											<thead>
												<tr>
												   <!--  <th>S.NO</th> -->
													<th>Department Desc</th>
													<th>Dept.</th>
													<th>Status</th>
													<th>Actions</th>
													
												</tr>
											</thead>
											
								    </table>
										
								  
					<!---Modal End here--->				
									
</div>
								</div>
							</div>
						</div><!-- .card -->
						
						
						
						<div class="col-md-5 overcome-col-padd-10 ">
							<div class="width_100">
								<div class="width_100 gray-font white-bg content-font-size side-content-height content-div-height">
								<div class="width_100">
							<button data-dismiss="modal" class="button-bg button-space pull-right mar-bot-15 mar"   onclick="clearUsertypeFields()">New <i class="fa fa-plus-circle"></i></button>
						</div>
									<div class="col-md-12 full-padding-20">
										<div class="margin-bottom-10 content-font-size  white-font color-bg">
											<p class="padd-left-right-5">Department </p>
										</div>

					
					  <!-- Main content ends here -->
					
    <!---modal start here-->
					 
		              
		                    
     
      <div  class="col-sm-12 form-group">
            <label>Department Desc.</label>
            <input placeholder="" class="form-control form-white" type="text" id="usertypedesctext">
            <input placeholder="" class="form-control form-white" type="hidden" id="usertypeidtext" value="0">
            <input placeholder="" class="form-control form-white" type="hidden" id="oprntext" value="INS">
          </div>
        
          <div class="col-md-12 form-group" >
            <label>Company</label>
            <!-- <select id="taxtypeselect" onchange="changetaxrate(this.val);"> -->
            <select id="deptselect" class="form-control form-white">
            <c:if test="${deptList.size() == 0}"><option value="">--Select--</option></c:if>
            
            <c:forEach items="${deptList}" var="dept" >
              <option value="${dept.value }"> ${dept.label } </option> 
            </c:forEach>
           
            </select>
          </div>
         <!--  <div class="col-md-12">
          <label class="col-sm-12 form-group">User Type Image</label>
          </div>
          <div class="col-md-12 pull-right " style="margin-top:20px;" >
        <div class="col-md-3 " >
          <label class="radio-inline left"><input type="radio" name="usertypeimage" checked value="up_admin.png"><img src="/heroadmin/resources/images/heroIcons/up_admin.png" style=" max-width: 25%;"></label>
          <label class="radio-inline"><input type="radio" name="usertypeimage" value="up_phy.png" ><img src="/heroadmin/resources/images/heroIcons/up_phy.png" style=" max-width: 25%;"></label>
         <label class="radio-inline"><input type="radio" name="usertypeimage" value="up_pharm.png"><img src="/heroadmin/resources/images/heroIcons/up_pharm.png" style=" max-width: 25%;"></label>
</div>
 <div class="col-md-3 " >
          <label class="radio-inline left"><input type="radio" name="usertypeimage" value="up_rec.png"><img src="/heroadmin/resources/images/heroIcons/up_rec.png" style=" max-width: 25%;"></label>
          <label class="radio-inline"><input type="radio" name="usertypeimage" value="up_stkman.png"><img src="/heroadmin/resources/images/heroIcons/up_stkman.png" style=" max-width: 25%;"></label>
         <label class="radio-inline"><input type="radio" name="usertypeimage" value="up_phy.png"><img src="/heroadmin/resources/images/heroIcons/up_pharm.png" style=" max-width: 25%;"></label>

</div>
	</div>			 -->	
	
	<div class="col-md-12">
			<div class="width_35">
            	<label class="hero-label">Department Image</label>
            </div>
            <div class="width_65">
			   <div class="width_100">
			   <div class="width_32">
			     <div class="panel">
			        <div class="panel-body up_admin">
			        <div class="">
			             <input type="radio" style="display:none" name="usertypeimage" checked value="up_admin.png"><img onclick="checkimg('up_admin.png')" src="/heroadmin/resources/images/heroIcons/up_admin.png" class="img-responsive">
			           <p>Superadmin</p> 
			         </div>
			      </div>
			     </div>
			  </div>
			   <div class="width_32">
			     <div class="panel">
			        <div class="panel-body up_stkman">
			        <div class="">
			         <input type="radio" style="display:none" name="usertypeimage" value="up_stkman.png"><img onclick="checkimg('up_stkman.png')" src="/heroadmin/resources/images/heroIcons/up_stkman.png" class="img-responsive">
			         <p>Manager</p>
			         </div>
			      </div>
			     </div>
			  </div>
			  <div class="width_32">
			     <div class="panel">
			        <div class="panel-body up_rec">
			        <div class="">
			         <input type="radio" style="display:none" name="usertypeimage" value="up_rec.png" ><img onclick="checkimg('up_rec.png')" src="/heroadmin/resources/images/heroIcons/up_rec.png" class="img-responsive">
			         <p> User</p>
			         </div>
			      </div>
			     </div>
			  </div>
			  
			  <!-- <div class="width_32">
			     <div class="panel">
			        <div class="panel-body up_rec">
			        <div class="">
			         <input type="radio" style="display:none" name="usertypeimage" value="up_rec.png"><img onclick="checkimg('up_rec.png')" src="/heroadmin/resources/images/heroIcons/up_rec.png" class="img-responsive">
			         <p>Receptionist</p>
			         </div> 
			      </div>
			     </div>
			  </div> -->
			  </div>
			 <!--  <div class="clearfix"></div>
			  <div class="width_100">
			  <div class="width_32">
			     <div class="panel">
			        <div class="panel-body up_stkman">
			        <div class="">
			         <input type="radio" style="display:none" name="usertypeimage" value="up_stkman.png"><img onclick="checkimg('up_stkman.png')" src="/heroadmin/resources/images/heroIcons/up_stkman.png" class="img-responsive">
			         <p>Manager</p>
			         </div>
			      </div>
			     </div>
			  </div>
			   <div class="width_32">
			     <div class="panel">
			        <div class="panel-body up_lab_technician">
			        <div class="">
			         <input type="radio" style="display:none" name="usertypeimage" value="up_lab_technician.png"><img onclick="checkimg('up_lab_technician.png')" src="/heroadmin/resources/images/heroIcons/up_lab_technician.png" class="img-responsive">
			         <p>Lab Assit.</p>
			         </div>
			      </div>
			     </div>
			  </div>
			   <div class="width_32">
			     <div class="panel">
			        <div class="panel-body up_lab_verifier">
			        <div class="">
			         <input type="radio" style="display:none" name="usertypeimage" value="up_lab_verifier.png"><img onclick="checkimg('up_lab_verifier.png')" src="/heroadmin/resources/images/heroIcons/up_lab_verifier.png" class="img-responsive">
			         <p>Verifier</p>
			         </div>
			      </div>
			     </div>
			  </div>
			  </div> -->
			 
			 
			 </div>
	</div>
	<script type="text/javascript">
		function checkimg(imgvalue){
			var res = imgvalue.split(".");
			$(".panel-body").css({'border':'none'});
			$("input[name='usertypeimage']").attr('checked', false);
			$("."+res[0]).css({'border' : '2px solid #febd29'});
			$("input[name='usertypeimage'][value='" + imgvalue + "']").attr('checked', 'checked');
		}
	</script>
	<style>
	.up_admin{
	border : 2px solid #febd29;
	}
	</style>
						  <div class="col-md-10">
										<div class="col-sm-4 hero-label form-group">
											 <label for="Status">Status</label>
										</div>
										<div class="col-sm-8">
								     		<div class="onoffswitch">
						                        <input name="onoffswitch" class="onoffswitch-checkbox" id="myonoffswitch1" checked="" type="checkbox">
						                        <label class="onoffswitch-label" for="myonoffswitch1">
						                        <span class="onoffswitch-inner">
						                       <span class="onoffswitch-active"><span class="onoffswitch-switch">ON</span></span>
						                        <span class="onoffswitch-inactive"><span class="onoffswitch-switch">OFF</span></span> 
						                      
						                        </span>
						                        </label>
						                      	</div>
					                      	</div> 
									</div>
								 
        
        <div class="col-md-12 form-group" >
             <button type="button" class="btn btn-primary"  onclick="saveusertype();">Save</button>
             <button type="button" class="btn btn-normal " onclick="clearUsertypeFields();">Cancel</button> 
        </div>

      
      </div>
      </div>
      </div>
      </div>
      
 </div>

</div>
		
           
		               <style>
		               
		               .mar{
		               margin-top:10px;
		               margin-right:10px;
		               }
		               .margin-bottom-10 {
                        margin-bottom: -11px;
                        }
                        .form-group {
   
                        margin-top: 10px;
                        }
		            .col-md-3 {
					    width: 50%;
					}
					.left{
					margin-left:9px;
					}
					.outerpadding{
    padding:10% 0%;}
.boximg{
	position:relative;
	overflow:hidden;
	}
	
.boximg img{
		transition:all ease-in 500ms;
		border:1px solid #fff;
	}	
.boximg img:hover{
	transform:scale(1.3,1.3);
	cursor:pointer;
	
	}	
	
.boximg:hover{
	border:1px solid #fff;
	}	
	
.date{
	 left: 0;
    position: absolute;
    top: 15px;
	padding:5px;
	background-color:teal;
	opacity:0;
	transition:all ease-in 300ms;
	
	}	
	
.likebut{
	  background: none repeat scroll 0 0 teal;
    height: 25px;
    padding: 7px;
    position: absolute;
    right: 5px;
    top: 130px;
    width: 25px;
	opacity:0.4;
	transition:all ease-in 300ms;
	}
	
	
.boximg:hover .date{
	opacity:1;
	
	}	
.boximg:hover .likebut{
	opacity:1;
	
}

 .img-responsive, .thumbnail a>img, .thumbnail>img {
    display: block;
    height: auto;
    }

.panel {
    margin-bottom: 21px;
    }
					
					 </style>       
		                      
		                  
		         
<jsp:include page="../home/homefooter.jsp" />
</body>
</html>
	 