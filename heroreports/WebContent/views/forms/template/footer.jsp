<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
	
                  </div><!--/.col-->

                </div>

	<script type="text/javascript" src="/heroadmin/js/lib/theme/jquery-ui.js"></script>
    <script type="text/javascript" src="/heroadmin/js/lib/theme/bootstrap-datepicker.js"></script>
	<script type="text/javascript" src="/heroadmin/js/lib/theme/moment.2.10.3.js"></script>
	<script type="text/javascript" src="/heroadmin/js/lib/theme/daterangepicker.2.1.27.js"></script>
	<!-- <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script> -->
	<script type="text/javascript">
      $('.datepicker').datepicker({
      format: 'dd/mm/yyyy',autoclose: true
      });
    </script>
	
	
		
	
	<script type="text/javascript" src="/heroadmin/js/lib/theme/jquery-ui-1.12.0.min.js"></script> 
	
	
	<script type="text/javascript" src="/heroadmin/js/lib/theme/popper.min.js"></script> 
  	<script type="text/javascript" src="/heroadmin/js/lib/theme/plugins.js"></script>

	<script type="text/javascript" src="/heroadmin/js/lib/theme/finalDatatable.min.js"></script>


<!-- PAYMENT POPUP -->
<div class="modal fade" id="payment" tabindex="-1" role="dialog" aria-hidden="true">
  <div class="modal-dialog topZero">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Add Payment</h4>
      </div>
      <div class="modal-body">
        <p> Your batch numbers are set on auto-generate mode to save your time. Do you want to change settings? </p>
        <div class="row">
          <div  class="col-sm-6 form-group">
            <label>Date</label>
            <input placeholder="" class="form-control form-white datepicker" type="text">
          </div>
          <div  class="col-sm-6 form-group">
            <label>Reference No</label>
            <input placeholder="" class="form-control form-white " type="text">
          </div>
          <div  class="col-sm-6 form-group">
            <label>Amount</label>
            <input placeholder="" class="form-control form-white " type="text">
          </div>
          <div  class="col-sm-6 form-group">
            <label>Paying By</label>
            <select class="selectNor" >
              <option> Cash </option>
              <option> Credit Card </option>
              <option> Debet Card </option>
              <option> Other </option>
            </select>
          </div>
          <div  class="col-sm-12 form-group">
            <label>Attachment</label>
            <input placeholder="" class="form-control form-white " type="file">
          </div>
          <div  class="col-sm-12 form-group">
            <label>Note</label>
            <textarea  class="form-control form-white "></textarea> 
          </div>
        </div>
      </div>
      <div class="modal-footer alignCenter" align="center ">
        <div class="form-group " align="center">
             <button type="button" class="btn btn-primary " data-dismiss="modal">Save</button>
              <button type="button" class="btn btn-normal " data-dismiss="modal">Cancel</button>
        </div>
      </div>
    </div>
  </div>
</div>


<div class="modal fade" id="modal-delet" tabindex="-1" role="dialog" aria-hidden="true">
<div class="modal-dialog">
  <div class="modal-content">
    <div class="modal-header">
		<button type="button" class="close" data-dismiss="modal">&times;</button>
		<h4 class="modal-title"><i class="fa fa-warning"></i> Info</h4>
	</div>
    <div class="modal-body">
      <div class="popDel" align="center">
        <h3>Are you sure?</h3>
        You won't be able to revert this!
      </div>
    </div>
    <div class="modal-footer alignCenter" align="center ">
      <button type="button" class="btn btn-white deletesus "  data-toggle="modal" data-target="#modalDelet-Sus" >Yes, delete it!</button>
      <button type="button" class="btn btn-danger " data-dismiss="modal">Cancel</button>
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
<!-- <div class="modal fade" id="modal-cancel" tabindex="-1" role="dialog" aria-hidden="true">
<div class="modal-dialog">
  <div class="modal-content">
    <div class="modal-body">
      <div class="popDel" align="center">
        <div class="info"><i class="fa fa-info" aria-hidden="true"></i></div>
        <h3>Are you sure?</h3>
        Do you want to cancel this Record?
      </div>
    </div>
    <div class="modal-footer alignCenter" align="center ">
      <button type="reset" class="btn btn-white " onclick="leave_function();">Leave it!</button>
      <button type="button" class="btn btn-danger " data-dismiss="modal">Cancel</button>
    </div>
  </div>
</div>
</div> -->

<!-- <div class="modal fade" id="modalDelet-Sus" tabindex="-1" role="dialog" aria-hidden="true">
<div class="modal-dialog">
  <div class="modal-content">
    <div class="modal-body">
      <div class="popDel" align="center">
        <div class="info-Sucess"><i class="fa fa-check" aria-hidden="true"></i></div>
        <label id="deletehdrivid"><h3>Deleted!</h3></label>
        
        <label id="deletedtlivid">
        Your imaginary file has been deleted.
        </label>
        
      </div>
    </div>
    <div class="modal-footer alignCenter" align="center ">
      <button type="button" class="btn btn-success " data-dismiss="modal">Ok</button>
    </div>
  </div>
</div>
</div>
 -->
<!-- Batch -->

<div class="modal fade" id="batchCodePop" tabindex="-1" role="dialog" aria-hidden="true">
  <div class="modal-dialog topZero">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Batch numbers</h4>
      </div>
      <div class="modal-body">
        <p> Your batch numbers are set on auto-generate mode to save your time. Do you want to change settings? </p>

        <div class="row">
          <div class="col-md-7">
            <div class="radio  radio-primary">
                <input name="radio1" id="radio1" value="option1" type="radio">
                <label for="radio1">
                    Continue auto-generating sales order numbers  
                </label>
            </div>
          </div>
          <div style="padding: 0px 9px;" class="col-md-2">
            <small>Prefix</small>
            <input placeholder="" class="form-control form-white" type="text">
          </div>
          <div class="col-md-3">
            <small>Next Number</small>
            <input placeholder="" class="form-control form-white" type="text">
          </div>
        </div>

        <div class="row">
          <div class="col-md-12">
            <div class="radio  radio-primary">
                <input name="radio1" id="radio2" value="option2" type="radio">
                <label for="radio2">
                    I will add them manually each time 
                </label>
            </div>

          </div>
        </div>


      </div>
      <div class="modal-footer alignCenter" align="center ">
        <div class="form-group " align="center">
             <button type="button" class="btn btn-primary " data-dismiss="modal">Save</button>
              <button type="button" class="btn btn-normal " data-dismiss="modal">Cancel</button>
        </div>
      </div>
    </div>
  </div>
</div>

<!-- Purchase -->
<div class="modal fade" id="purchaseCodePop" tabindex="-1" role="dialog" aria-hidden="true">
  <div class="modal-dialog topZero">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Purchase Order numbers</h4>
      </div>
      <div class="modal-body">
        <p> Your Purchase Order numbers are set on auto-generate mode to save your time. Do you want to change settings? </p>

        <div class="row">
          <div class="col-md-7">
            <div class="radio  radio-primary">
                <input name="radio1" id="radio1" value="option1" type="radio">
                <label for="radio1">
                    Continue auto-generating sales order numbers  
                </label>
            </div>
          </div>
          <div style="padding: 0px 9px;" class="col-md-2">
            <small>Prefix</small>
            <input placeholder="" class="form-control form-white" type="text">
          </div>
          <div class="col-md-3">
            <small>Next Number</small>
            <input placeholder="" class="form-control form-white" type="text">
          </div>
        </div>

        <div class="row">
          <div class="col-md-12">
            <div class="radio  radio-primary">
                <input name="radio1" id="radio2" value="option2" type="radio">
                <label for="radio2">
                    I will add them manually each time 
                </label>
            </div>

          </div>
        </div>


      </div>
      <div class="modal-footer alignCenter" align="center ">
        <div class="form-group " align="center">
             <button type="button" class="btn btn-primary " data-dismiss="modal">Save</button>
              <button type="button" class="btn btn-normal " data-dismiss="modal">Cancel</button>
        </div>
      </div>
    </div>
  </div>
</div>
<!-- 
<script type="text/javascript">
				$(document).ready(function(){
					
				jQuery(".deletesus").click(function(){
					jQuery('#modal-delet').modal('hide');
					jQuery('#modalDelet-Sus').modal('show');
					});	

				});
				
				
				
				
</script>   -->


<script type="text/javascript" src="/heroadmin/js/lib/notification/bootstrap-notify.js"></script>
<script type="text/javascript" src="/heroadmin/js/lib/notification/notification.js"></script>

	<script type="text/javascript" src="/heroadmin/js/lib/theme/dataTables.buttons.min.js"></script>
	<script type="text/javascript" src="/heroadmin/js/lib/theme/buttons.flash.min.js"></script>
	<script type="text/javascript" src="/heroadmin/js/lib/theme/jszip.min.js"></script>
	<script type="text/javascript" src="/heroadmin/js/lib/theme/pdfmake.min.js"></script>
	<script type="text/javascript" src="/heroadmin/js/lib/theme/vfs_fonts.js"></script>
	<script type="text/javascript" src="/heroadmin/js/lib/theme/buttons.html5.min.js"></script>
	<script type="text/javascript" src="/heroadmin/js/lib/theme/buttons.print.min.js"></script>
		
