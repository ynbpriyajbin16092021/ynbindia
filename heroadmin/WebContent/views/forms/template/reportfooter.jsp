<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
  </div>
    <!-- END PAGE CONTENT --> 
    <!-- 
    <div class="footer">
    <div class="copyright">
      <p class="pull-left sm-pull-reset">
        <span>Copyright <span class="copyright">©</span> 2016 </span>
        <span>THEMES LAB</span>.
        <span>All rights reserved. </span>
      </p>
      <p class="pull-right sm-pull-reset">
        <span><a href="#" class="m-r-10">Support</a> | <a href="#" class="m-l-10 m-r-10">Terms of use</a> | <a href="#" class="m-l-10">Privacy Policy</a></span>
      </p>
    </div>
  </div>
   --> 
   
  </div>

  
  <!-- END MAIN CONTENT --> 
 
</section>




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
    <div class="modal-body">
      <div class="popDel" align="center">
        <div class="info"><i class="fa fa-info" aria-hidden="true"></i></div>
        <h3>Are you sure?</h3>
        You won't be able to revert this!
      </div>
    </div>
    <div class="modal-footer alignCenter" align="center ">
      <button type="button" class="btn btn-white " data-dismiss="modal"  data-toggle="modal" data-target="#modalDelet-Sus" >Yes, delete it!</button>
      <button type="button" class="btn btn-danger " data-dismiss="modal">Cancel</button>
    </div>
  </div>
</div>
</div>

<div class="modal fade" id="modalDelet-Sus" tabindex="-1" role="dialog" aria-hidden="true">
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


<!-- BEGIN PRELOADER -->
 <div class="loader-overlay">
  <div class="spinner">
    <div class="bounce1"></div>
    <div class="bounce2"></div>
    <div class="bounce3"></div>
  </div>
</div> 
<!-- END PRELOADER --> 
<a href="#" class="scrollup"> <i class="fa fa-angle-up"></i> </a> 
<!-- Template Script -->  

<script type="text/javascript" src="../js/jquery.cookie.js"></script>
<script type="text/javascript" src="../js/jquery-ui.js"></script>
<script type="text/javascript" src="../js/table_editable.js"></script>
<script type="text/javascript" src="../js/template/bootstrap.min.js"></script> 
<script type="text/javascript" src="../js/plugin/jquery.mCustomScrollbar.concat.min.js"></script> 
<script type="text/javascript" src="../js/template/bootstrap-hover-dropdown.min.js"></script> 
<script type="text/javascript" src="../js/template/builder.js"></script> 
<script type="text/javascript" src="../js/template/application.js"></script> 
<script type="text/javascript" src="../js/template/quickview.js"></script> 
<script type="text/javascript" src="../js/template/quickview.js"></script> 
<script type="text/javascript" src="../js/template/layout.js"></script> 

<link rel="stylesheet" type="text/css" href="../resources/css/data-table-min.css"/>

<!-- for DataTable PDF,Excel Print Start -->
<link rel="stylesheet" type="text/css" href="../resources/css/data-table-min.css"/>
<link rel="stylesheet" type="text/css" href="../js/plugin/bootstrap-datepicker/css/bootstrap-datepicker.css"/>
<script type="text/javascript" src="../js/plugin/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
<link href="../js/plugin/select2/css/select2.min.css" rel="stylesheet"/>
<script src="../js/plugin/select2/js/select2.full.min.js"></script>
<link href="../js/plugin/textediter/css/summernote.css" rel="stylesheet"/>
<script src="../js/plugin/textediter/js/summernote.js"></script> 
<!-- End -->
<script type="text/javascript">
$(document).ready(function() {

    $('#example').DataTable();
    $('#categories').DataTable();
    $('#shortingAll').DataTable( {
       responsive: true
    } );
} );
</script>

<!-- Bootstrap Datepicker  -->
<link rel="stylesheet" type="text/css" href="../js/plugin/bootstrap-datepicker/css/bootstrap-datepicker.css"/>
<script type="text/javascript" src="../js/plugin/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
<script type="text/javascript">
  $('.datepicker').datepicker({
    format: 'dd/mm/yyyy'
});
</script>

<!-- Select Inputs -->
<link href="../js/plugin/select2/css/select2.min.css" rel="stylesheet"/>
<script src="../js/plugin/select2/js/select2.full.min.js"></script> <!-- Select Inputs -->

<script type="text/javascript">
$(document).ready(function() {
  $(".selectSer").select2();
});

$(".selectNor").select2({
  minimumResultsForSearch: Infinity
});

$("#shortingAll_length label select").select2({
  minimumResultsForSearch: Infinity
});

</script>

<script>
$(document).ready(function(){
    $('[data-toggle="tooltip"]').tooltip();   
});
</script>

  <script>
  $( function() {
    var availableTags = [
      "aaaaaaaaa",
      "bbbbbbbbb",
      "cccccccccc",
      "ddddddddd",
      "Scheme"
    ];
    $( "#tags" ).autocomplete({
      source: availableTags
    });
  } );
  </script>


  <!-- Select Inputs -->
<link href="../js/plugin/textediter/css/summernote.css" rel="stylesheet"/>
<script src="../js/plugin/textediter/js/summernote.js"></script> <!-- Select Inputs -->
<script src="../js/util/invutil.js"></script>
<script type="text/javascript">
  $(document).ready(function() {
  $('#summernote').summernote({
    height: 300,                 // set editor height
  minHeight: null,             // set minimum height of editor
  maxHeight: null, 
  focus: true 
  });
});
</script>



</body>
</html>