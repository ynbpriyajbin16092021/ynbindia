<!doctype html>
 
<html lang="en">
<head>
  <meta charset="utf-8" />
  <title>Autocomplete</title>
  <link rel="stylesheet" href="../resources/css/pos/jquery-ui.css" />
  <script src="../js/forms/sales/posplugins/jquery-1.9.1.js"></script>
  <script src="../js/forms/sales/posplugins/jquery-ui.js"></script>
  
  
<style type="text/css" title="currentStyle">
@import "../resources/css/datatable/jquery.dataTables.min.css";
</style>

<script type="text/javascript"
	src="../js/dataTables.min.js"></script>
  <script>
  $(function() {
    /* var items = [ 'France', 'Italy', 'Malta', 'England', 
        'Australia', 'Spain', 'Scotland' ]; */

    var items = [];

    var item = new Object();
    item.value=1;
    item.label='AAA';
    item.address='Address1';
    items.push(item);   

    item = new Object();
    item.value=2;
    item.label='BBB';
    item.address='Address1';
    items.push(item);   

    item = new Object();
    item.value=3;
    item.label='AAABBB';
    item.address='Address1';
    items.push(item);    
        
    function split( val ) {
        return val.split( /,\s*/ );
    }
    function extractLast( term ) {
      return split( term ).pop();
    }
 
    $( "#search" )
      .autocomplete({
        minLength: 0,
        source: function( request, response ) {
          var filteredItems = [];
          filteredItems = ($.ui.autocomplete.filter(items, extractLast( request.term ) ));

          $('#sampleDT').DataTable( {
              "destroy":true,
				data: filteredItems,
		        columns: [
		            { data: 'value' },
		            { data: 'label' },
		            {data: 'address'}
		        ]
	            
		    } );
		    
          response( $.ui.autocomplete.filter(
            items, extractLast( request.term ) ) );
        },
        focus: function() {
          return false;
        },
        select: function( event, ui ) {
          var terms = split( this.value );
          // remove the current input
          terms.pop();
          // add the selected item
          /* terms.push( ui.item.value ); */
          console.log(ui.item);
          terms.push( ui.item.label );
          // add placeholder to get the comma-and-space at the end
          terms.push( "" );
          this.value = terms.join( ", " );
          return false;
        }
      });
  });
  </script>
</head>
<body>
 
<div>
  <input id="search" size="50" />
</div>
 
 <table id="sampleDT" class="table table-hover" >
	            <thead>
	                <tr>
	                <th> Id </th>
                     <th> Name </th>
                     <th> Address </th>
                     </tr>
	            </thead>
	            
	        </table>
 
</body>
</html>