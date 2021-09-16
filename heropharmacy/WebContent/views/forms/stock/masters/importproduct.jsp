<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Product Import</title>
<%if(request.getParameter("disp") == null){ %>
<jsp:include page="../../template/library.jsp" /> 
<%} %>	
 
<script type="text/javascript" src="../js/forms/masters/masters.js"></script>
 
    
</head>
<body>
 
<form method="post" action="importProduct"
		enctype="multipart/form-data">
<%if(request.getParameter("disp") == null){ %>
  	<jsp:include page="../../template/header.jsp" />
<%} %>	


<div class="clearfix"></div>
					<div class="breadcrumbs"  style="display: <%= request.getParameter("disp") %>">
			            <div class="col-md-4 col-sm-4">
			                <div class="page-header">
			                        <p class="cursor-pointer mar-top-10 bread-a-style head-font-size yellow-font" href="stockdashboard"><strong> <i class="fa fa-table"></i> Import Product</strong></p>
			                </div>
			            </div>
			            <div class="col-md-8 col-sm-8">
			                <div class="page-header float-right">
			                    <a class="pull-right cursor-pointer bread-a-style content-font-size color-font" href="dashboard?uid=1">Home / Dashboard</a>
			                </div>
			            </div>
			        </div>
<div class="clearfix">
                 	<div class="col-md-12 overcome-col-padd-10  ">
						<div class="col-md-12 overcome-col-padd-10 ">
							<div class="width_100">
								
								<div class="width_100 gray-font white-bg content-font-size">
									<div class="col-md-12 full-padding-20">         
                       

			<div class="rowNor marginTop">	
			<div class="col-sm-12 ">	
			<label style="color: green"><%=request.getAttribute("message") == null ? "" : request.getAttribute("message") %></label> 
			<div class="col-md-12">
			<label class="col-sm-3 control-label yellow-font"><p>Import product</p></label>
			 <span class="pull-right">
                  <!-- <a href="exportsampleformats"> -->
                   <button
								class="btn impfr waves-effect waves-light button-bg button-space  right color-bg white-font"
								type="button" name="action" onclick="downloadsampleformat()">Download
								Sample Format</button>
                <!--   <input type="button"  value="Download Sample Format"  class="button-bg button-space " id="sampleformatbtn"/> -->
                  </a> 
                </span>
			</div>
			
			
				<div class="modal fade" id="productModal" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel" aria-hidden="true"
		style="margin-top: 126px;">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h3 class="modal-title" id="exampleModalLabel">How many
						products upload?</h3>
				</div>
				<div class="modal-body">
					<form class="col s12">
						<div class="row">
							<div class="input-field col s6">
							<label
									for="oldpassword">Row Count</label>
								<input id="sheetcount" type="number" class="validate"> 
							</div>

						</div>
					</form>
				</div>
				<div class="modal-footer">
				<button type="button" class="btn btn-primary"
						onclick="saveexcelcolumn()" style="margin: 8px 4px;">Create
						Sheet</button>
					<button type="button" class="btn btn-secondary"
						onclick="modalClose('productModal')">Close</button>
					
				</div>
			</div>
		</div>
	</div>
     
     
      <div class="col-sm-12">
        <div class="form-group">
          <label class="col-sm-3 control-label"><p>Please Select File</p></label>
            <div class="col-sm-5">
              <div class="logoUploadDis">
                <span>
                  <!-- <input class="logoUp" type="file" id="logofiletype">
                   <a>Upload your logo</a> --> 
                   
                   Select file to upload: <input type="file" name="file" size="60" id="logofiletype" accept=".xls"/>
                   <input type="hidden" id="uploadedfilename" 
                   value="<%= request.getAttribute("uploadfilepath") == null ? "" :  request.getAttribute("uploadfilepath")%>">
                </span>
                  
              </div>
          </div>
          <div id="loadingDiv">
          <img class="logoDis" src="../../heroadmin/resources/images/loader_green.gif" style="width:75%">
          </div>
          <div class="col-md-12">
          <div class="col-sm-4">
              <div class="col-sm-2" >
                <span>
                  
                  <input type="submit" value="Upload"  class="btn btn-primary btn-embossed" id="uploadbtn" 
                  <% if(request.getAttribute("uploadfilepath") != null) {%> disabled="disabled" <% }%>"/>
                   
                </span>
                  
              </div>
         
           
          </div>  
          
           </div>

      
     
<%if(request.getAttribute("uploadfilepath") != null) { %>
				          <div class="form-group col-sm-12 aligncenter" align="center">
    						<button type="button" class="btn btn-primary btn-embossed" onclick="importproduct()">Import to Stock</button>
                <button type="button" class="btn btn-danger btn-embossed">Cancel</button>
    					</div>
    					<%} %>
				</div>    
              
               <div class="panel-content" style="display: none" id="statusDIVid">
		
	        <table   id="productUplaodStatusDT"  class="hero-settings-table">
                <thead>
                    <tr >
                        <th>Row No </th>
                        <th>Status</th>
                        <th>Status / Error Desc</th>
                       
                    </tr>
                </thead>
                
            </table>
	    </div>
				</div>
			</form>
			</div>
			</div>
	    </div>
    </div>
</div>


</div>
</div>
</div>
</div>
</div>
<style>
.col-sm-3 {
    margin-top: 10px;
    width: 25%;
}
.width_100 {
    width: 98%;
    display: block;
    float: left;
    margin-left: 10px;
}
.form-group label {
    width: 17%;
    }
    .col-sm-2 {
    width: 32.666667%;
}
</style>
<jsp:include page="../../template/footer.jsp" />
 
           <script type="text/javascript">
           $(document).ready(function() {
        	   $('#loadingDiv').hide();
           });
           </script>
                    </form>
</body>
</html>