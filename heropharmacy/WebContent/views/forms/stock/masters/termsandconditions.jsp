<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Terms & Conditions Master</title>
<%if(request.getParameter("disp") == null){ %>
<jsp:include page="../../template/library.jsp" /> 
<%} %>	
<script type="text/javascript" src="../js/forms/masters/masters.js"></script>
<link rel="stylesheet" href="/heroadmin/resources/css/stylebass.css">
    <link rel="stylesheet" href="/heroadmin/resources/css/template/editor.css"> 
<script type="text/javascript" src="/heroadmin/js/lib/theme/editor.js"></script>
</head>
<body onload="loadterms();">

<%if(request.getParameter("disp") == null){ %>
  	<jsp:include page="../../template/header.jsp" />
<%} %>	  
				 
					<div class="clearfix"></div>
					<div class="breadcrumbs"  style="display: <%= request.getParameter("disp") %>">
			            <div class="col-md-4 col-sm-4">
			                <div class="page-header">
			                        <p class="cursor-pointer mar-top-10 bread-a-style head-font-size yellow-font"><strong><i class="fa fa-lightbulb-o"></i> Terms & Conditions  Master</strong></p>
			                </div>
			            </div>
			            <div class="col-md-8 col-sm-8">
			                <div class="page-header float-right">
			                    <a class="pull-right cursor-pointer bread-a-style content-font-size color-font" href="dashboard?uid=1">Home / Dashboard</a>
			                </div>
			            </div>
			        </div>
					<div class="clearfix"></div>
					
					<div class="col-md-12 overcome-col-padd-10">
						 <div class="col-md-6 overcome-col-padd-10">
							<div class="width_100">
							<!-- 	<div class="dashboard-ld-header margin-bottom-10 head-font-size white-font color-bg">
									<p><span style="font-size:18px;font-weight:bold"></span> Category </p>
								</div> -->
								<div class="width_100 gray-font white-bg content-font-size content-div-height">
									<div class="col-md-12 full-padding-20">
									<table id="termsDT" class="hero-settings-table" style="width:100%">
											<thead>
												<tr>
													<th>Title</th>											
													<th>Terms & Conditons</th>
													<th>Actions</th>
													
												</tr>
											</thead>
									
								    </table>
								</div>
								</div>
							</div>
						</div> 
						
						
						<div class="col-md-6 overcome-col-padd-10">
							<div class="width_100">
								<div class="width_100 gray-font white-bg content-font-size content-div-heightnw" id="style-5">
									<div class="col-md-12 full-padding-20">
							<button class="button-bg button-space pull-right mar-bot-15 mar" onclick="cleartermsfields()">New <i class="fa fa-plus-circle"></i></button>
										<div class="clearfix"></div>
									<div class="margin-bottom-10 content-font-size  white-font color-bg">
											<p class="padd-left-right-5"> Terms & Conditions </p>
										</div>
									<div class="col-md-12 form-group">
									  <label for="SKU">Description<span style="color:red">*</span></label>
									  <input class="form-control form-white" type="hidden" id="oprntext" value="INS">
									  <input class="form-control form-white" type="hidden" id="termsidtext" value="0">
								    <input type="text" class="form-control inpttypestyle" id="termsnametext" >
									</div>
									
					     <div class="col-md-12 form-group">
									 <!--  <label for="product Details"  ><span style="color:red">*</span>Terms & Conditions</label><br> -->
								<!--      	<div class="width_100">		
							    				
							    			<div class="col-md-9 emailcontent">
							    				<textarea rows="5" cols="75" id="descriptiontext " 
							    				 class="content1"  ></textarea>
							    			</div>
							    		</div> -->
							    		
							    		
									 <label for="product Details"  >Terms & Conditions<span style="color:red">*</span></label><br>							
								      <textarea class="form-control" id="descriptiontext"></textarea>
									
						</div>
									
									<div class="col-md-12">
									<div class="col-md-12 permissionDiv">
		                         			<button data-dismiss="modal" class="btn btn-primary" type="button" 
		                         			onclick="saveterms()">Save</button>
								     	
								    </div>
		                      	</div>
									
									</div>
									
									
									</div>
								</div>
							</div>
						</div>
					</div>
					
				<script>
				$(document).ready(function() {
				    $('.hero-settings-table').DataTable( {
				    	bFilter: false, bInfo: false
				    } );
				} );
				
				</script>
				
				  <script type="text/javascript">
                  $( document ).ready(function() {
            	  $("#typeselect input[name='gendertext']").on("change",function(){
            		  var id = ($(this).val());
            			  if (id == "Default")
				            {
                  	            $("#Default").css({"display":"block"});
                  	            $("#SGST").css({"display":"block"});
				   
				            }
            			  else
            				  {
            				    $("#Default ").css({"display":"none"});
            				    $("#SGST ").css({"display":"none"});
            				  }
            	});
             });
                  
                  
                  $(document).ready(function() {
                	  $('.content1').richText();
       
                	  $('.richText-toolbar ul li a span.fa-image').parent().css( "display", "none" );
                	  $('.richText-toolbar ul li a span.fa-file-text-o').parent().css( "display", "none" );
                	  $('.richText-toolbar ul li a span.fa-video-camera').parent().css( "display", "none" );
                	  $('.richText-toolbar ul li a span.fa-paint-brush').parent().css( "display", "none" );
                	  $('.richText-toolbar ul li a span.fa-align-left').parent().css( "display", "none" );
                	  $('.richText-toolbar ul li a span.fa-align-center').parent().css( "display", "none" );
                	  $('.richText-toolbar ul li a span.fa-align-right').parent().css( "display", "none" );
                	  $('.richText-toolbar ul li a span.fa-list-ol').parent().css( "display", "none" );
                	  $('.richText-toolbar ul li a span.fa-text-height').parent().css( "display", "none" );
                	  $('.richText-toolbar ul li a span.fa-link').parent().css( "display", "none" );
                	  $('.richText-toolbar ul li a span.fa-font').parent().css( "display", "none" );
                	  $('.richText-toolbar ul li a span.fa-list').parent().css( "display", "none" );
                	  $('.richText-toolbar ul li a span.fa-header').parent().css( "display", "none" );
                	});
                     
              </script>
	
<style>
	.button-space {
	margin-right:5px;
	}
	.button-height{
	height:28px;
	}

.form-group label{
	width:20%;
	display:block;
	float:left;
	padding:6px 12px 0 12px;
	font-weight:normal;
}

.form-group>.form-control{
	width:80%;
	display:block;
	float:left;
}


	</style>
<jsp:include page="../../template/footer.jsp" /> 

</body>
</html>

