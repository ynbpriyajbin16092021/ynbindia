<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML>
<html lang="en">
<head>
	<title>HERO</title>
	<!-- Meta tag Keywords -->
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	
	<link rel="icon" href="/heroadmin/resources/images/logos/favicon.png" type="image/png" sizes="16x16">
 
	<link rel="stylesheet" href="/heroadmin/resources/css/bootstrap.3.3.7.min.css"> 
	<link rel="stylesheet" href="/heroadmin/resources/css/stylej.css">
	<link rel="stylesheet" href="/heroadmin/resources/css/styleindex.css">
	<link rel="stylesheet" href="/heroadmin/resources/css/font-awesome.min.css">
	
	<script src="/heroadmin/js/lib/theme/jquery.1.4.4.min.js"></script>

	<script type="text/javascript" src="/heroadmin/js/lib/theme/jquery.3.3.1.min.js"></script>
  	<script type="text/javascript" src="/heroadmin/js/lib/theme/bootstrap.3.3.7.min.js"></script>
  	<script type="text/javascript" src="/heroadmin/js/lib/theme/html5shiv.min.js"></script>
	
    <script type="text/javascript" src="../js/forms/admin/login/login.js"></script>
	<script type="text/javascript" src="../js/util/invutil.js"></script>
	<script type="application/x-javascript">
		addEventListener("load", function () {
			setTimeout(hideURLbar, 0);
		}, false);

		function hideURLbar() {
			window.scrollTo(0, 1);
		}
	</script>
	 <script type="text/javascript">
		$(document).ready(function(){
			$( "#password" ).keyup(function(event) {
				 if ( event.which == 13 ) {
					signin();
				  }
			}); 
			focuscursor('#name');
		});
	</script>


	
</head>

<body>

<style>
a.color-blue{
 color:#1d4075;
 font-weight:bold;
}
.cursor-pointer{
cursor:pointer;
}
.p{
width:100%;
}
.color-font{
	color:#edac00;
}
.logo_img2{
padding-left:30px;
}

.logo_img2 img{
width:15%;
text-align:center;
}
</style>
	<!--header-->
	
	<!--//header-->
	
	
	<div class="col-md-12" id="loading">
	<div class="col-md-3">
		<div class="logo_img">
			<img src="/heroadmin/resources/images/logos/logo.png" alt="HERO" class="responsive" >
		</div>
		</div>
		<div class="col-md-9">
		<div class="logo_img2">
			<!-- <img src="/heroadmin/resources/images/logos/ynblogo.png" alt="HERO" class="responsive" > -->
				<img src="/heroadmin/forms/HeroImageView?index=0" class="responsive"/>
		</div>
		</div>
		<div class="sub-main-w3">
		<!-- <div class="logo_img">
			<img src="../resources/images/Hero_logo1.png" alt="User Avatar" class="responsive" >
		</div> -->
			<h2 class="login-h1">Login</h2>
			<form class="form-signin" role="form"  name="loginform" >
				<div class="pom-agile">
					
					<input placeholder="Username" name="name" id="name" class="form-white username" type="text" required="" autocomplete="off">
					
				</div>
				<div class="pom-agile">	
					<input placeholder="Password" name="password" id="password" class="form-white username" type="password" required="" autocomplete="off">		 
				</div>
				<div class="other-buttons">
					<a class="cursor-pointer" data-toggle="modal" href="#myModal" id="modalbtn">Forgot Password?</a>
					<!-- <a class=" pull-right cursor-pointer" id="registerlink" href="organization?disp=none">Register New</a>
					<a class=" pull-right cursor-pointer" id="registerlink" onclick="checkOrgnAvailabe()">Register New</a> -->
				</div>
				
				<div class="clearfix"></div>
				<div class="col-md-12">
				    <div class="hoversin">
					<a class="cursor-pointer" id="submit-form" onclick="signin()">Login</a>
					</div>
				</div>
				
			</form>
			<div class="clserrormessage">${message }</div>
		</div>
	</div>
	<!--//main-->
		<div  id="myModal" class="modal fade modalstyle">
		              <div class="modal-dialog widthModalForget">
		                  <div class="modal-content">
		                        <div class="modal-header">
							      <h5 class="modal-title">Forgot password<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button></h5>
		                          
		                        </div><br>
							    <div class="modal-body">
		                            <div class="col-md-8 clsmodal">
                                  
                                        <label>UserName</label>
									    <input type="text" id="forgotusername" class="form-control form-white username" >
                                    </div>
									 
		                      
		                         <div class= "clearfix"> </div>
								</div>
		                      <div class="modal-footer">
		                         

		                                <button type="button" class="btn btn-primary" onclick="saveforgotpassword();">Save</button>
										<button data-dismiss="modal"  type="button" class="btn btn-default">Cancel</button>

		                               

		                      </div>
		                      <div class= "clearfix"> </div>
		              </div>
		          </div>
	</div>	 

	<div class="modal fade" id="alertModalMsg-hero" tabindex="-1" role="dialog" aria-hidden="true">
<div class="modal-dialog">
  <div class="modal-content">
  	  <div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">&times;</button>
			<h4 class="modal-title"><i class="fa fa-warning"></i> Info</h4>
	</div>
    <div class="modal-body">
      <div class="popDel" align="center">
        <p id="alertModalMsg"></p> 
      </div>
    </div>
  </div>
</div>
</div>
<!--     <script src="/heroadmin/js/lib/theme/popper.min.js"></script>
    <script src="/heroadmin/js/lib/theme/plugins.js"></script>
    <script src="/heroadmin/js/lib/theme/main.js"></script> -->
   
<button id="beforeSendLoader" data-target="#modalLoading" data-toggle="modal"  data-backdrop="static" data-keyboard="false"></button>
   
<div class="modal fade" id="modalLoading" tabindex="-1" role="dialog" aria-hidden="true">
<div class="modal-dialog">
  <div class="modal-content">
  	<div class="modal-header">
  		<button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title changeTitle"></h4>
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
    
<div class="modal fade" id="alertModalMsg-hero" tabindex="-1" role="dialog" aria-hidden="true">
<div class="modal-dialog">
  <div class="modal-content">
  	  <div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">&times;</button>
			<h4 class="modal-title"><i class="fa fa-warning"></i> Info</h4>
	</div>
    <div class="modal-body">
      <div class="popDel" align="center">
        <p id="alertModalMsg"></p> 
      </div>
    </div>
  </div>
</div>
</div>
	
</body>

</html>