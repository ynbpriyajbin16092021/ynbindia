<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Purchase Order</title>
</head>
 
<body>
<div id="non-printable">
       
    </div>
<div id="printable">
<div class="container">

<div class="col-sm-12" style=" border-bottom: 2px solid #0099CC;">
<c:forEach items="${purchaseList}" var="print" >  
<div class="col-sm-4" style="margin-top: -15px;">
           <img src="/heroadmin/resources/images/logo.png" />
       </div>
<div class="col-sm-3">
      <p class="orgn_name"></p> 
       </div>
       <div class="col-sm-5" align="right">
           <p style="font-size:20px;">Original For Recipient</p>
           <p class="code">${print.purchasecode }</p>
             <p>Date ${print.currentdate }</p>
             <p>P.O Date${print.podate }</p>
             <p>P.O.Number ${print.ponumber }</p>
             
       </div>
       </c:forEach>
</div>
<c:forEach items="${purchaseList}" var="order" > 
<div class="col-sm-12" style="padding:3px !important ;" >
   <div class="col-sm-5" style="margin-bottom:0 px;">
   <h3>${order.suppliername }</h3>
   </div>
   <div class="col-sm-4">
    <h3>Bill To :</h3>
   
   </div>
<div class="col-sm-3">
    <h3></h3>
   
   </div>

</div>
<div class="col-sm-12" >
 
<div class="col-sm-5">
 
  
<p> ${order.address },</p>
  <P>${order.city },</P>
   <p>${order.state },</p>
  <p>${order.mobile },</p>
    <p>${order.email },</p>
    <p>GSTIN:123567</p>
   </div>
   <div class="col-sm-4">
 
  <p> ${order.address },</p>
   <P>${order.city },</P>
   <p>${order.state },</p>
   <p>${order.zipcode }</p>
    
   </div>
      <div class="col-sm-3">
                <P>Bill No: ${order.billno } </P>
                 <P>Shipping Cost: Rs.${order.shippingcost } </P>
   </div>
 


</div>
</c:forEach>
<div class="col-sm-12" >
 <table>
 
   <thead >
     <tr>
        <th rowspan="2">S.No</th>
        <th rowspan="2">Product Name</th>
        <th rowspan="2">HSN</th>
        <th rowspan="2">Batch No</th>
        <th rowspan="2">Expiry Date</th>
         <th rowspan="2">QTY</th>
         <th rowspan="2">Unit Price</th>
          <th rowspan="2" >Taxable Value</th>
           <th colspan="2" class="centre">GST</th>
           <th rowspan="2" >Amount</th>
     </tr>
     <tr>
     	<th>CGST</th>
     	<th>SGST</th>
     </tr>
      </thead>
  <tbody>
      <c:forEach items="${purchaseitemList}" var="order" > 
     <tr>
       <td  class="bgcolor border-bottom">${order.index+1 }</td>
       <td  class=" border-bottom" style=" text-align: left!important;"><p style="font-weight:bold;">${order.categoryname}:</p></br>-${order.productname }</td>
        <td  class="bgcolor border-bottom">1234</td>
       <td class="border-bottom">${order.batchno }</td>
       <td  class="bgcolor border-bottom">${order.expirydate }</td>
       <td  class="border-bottom">ordered - ${order.totalqty }</br>bonus - ${order.bonusqty }</td>
       <td  class="bgcolor border-bottom">${order.rate }</td>
        <td class="border-bottom">${order.SUBTOTAL }</td>
         <td  class="bgcolor border-bottom">${order.TAXAMOUNT }</br>@${order.gsttax }</td>
        <td class="border-bottom">${order.TAXAMOUNT }</br>@${order.gsttax }</td>
        
       <td  class="bgcolor align border-bottom">${order.amount }</td>
     </tr>
    
     </c:forEach>
     </tbody>
   
      <tfoot >
        <c:forEach items="${purchaseList}" var="print" >  
    <tr style="height:120px; border-bottom: 2px solid #0099CC;">
     <td  class="bgcolor "></td>
       <td  class="footer">Shipping & Packages</td>
        <td  class="bgcolor "></td>
       <td ></td>
       <td  class="bgcolor "></td>
       <td ></td>
       <td  class="bgcolor " style="vertical-align:bottom;">${print.shippingcost }</td>
        <td ></td>
         <td  class="bgcolor "></td>
        <td></td>
        
       <td  class="bgcolor " style="vertical-align:bottom;">Rs.${print.shippingcost }</td>
    </tr>
     <tr class="last-row-footer">
     <td  class="bgcolor "></td>
       <td  >TOTAL</td>
        <td  class="bgcolor "></td>
       <td ></td>
       <td  class="bgcolor "></td>
       <td ></td>
       <td  class="bgcolor ">${print.prodtotal }</td>
        <td ></td>
         <td  class="bgcolor ">${print.totalwithtax }</td>
        <td></td>
        
       <td  class="bgcolor ">${print.grandtotal }</td>
    </tr>
    </c:forEach>
  </tfoot>
</table>


</div>
<div class="clearfix"></div>
 <c:forEach items="${purchaseList}" var="order" > 
<div class="col-sm-12 " >
<div class="col-sm-6 " >
<p >Total: <strong> ${order.grandtotalinwords } only</strong></p></br>
<p style="color:#0099CC;">AUTHORIZED SIGNATORY</p>   
</div>
<div class="col-sm-6 ">
<div class="col-sm-8 " align="right">
   <p class="font-style">TOTAL BEFORE TAX :</p>
    <p class="font-style">DISCOUNT :</p>
     <p class="font-style">TOTAL TAX AMOUNT :</p>
       <p class="font-style">TOTAL AMOUNT :</p>
  </div>
  <div class="col-sm-4 " align="right">
   <p class="font-style"> Rs.${order.prodtotal }</p>
    <p class="font-style">(-)${order.discount }</p>
    <p class="font-style"> Rs.${order.totalwithtax }</p>
    <p class="font-style">Rs.${order.grandtotal }</p>
     
  </div>
   </div>
</div>

</c:forEach>







</div>
</div>
</body>



<style>
  #printable { display: none; } 
 @media print{ 
 

#non-printable { display: none; }
 #printable { display: block; }
 @page {
margin-top: 0;
margin-bottom: 0;
} 
 a[href]:after {
content: none !important;
 }
 body{
 font-family: Calibri;
 }
.col-sm-1, .col-sm-2, .col-sm-3, .col-sm-4, .col-sm-5, .col-sm-6, .col-sm-7, .col-sm-8, .col-sm-9, .col-sm-10, .col-sm-11, .col-sm-12 {
  float: left;
 
}
.col-sm-12 {
  width: 100%;
  
   padding:5px;
  
}
.col-sm-11 {
  width: 91.66666666666666%;
}
.col-sm-10 {
  width: 83.33333333333334%;
}
.col-sm-9 {
  width: 75%;
}
.col-sm-8 {
  width: 66.66666666666666%;
}
.col-sm-7 {
  width: 58.333333333333336%;
}
.col-sm-6 {
  width: 50%;
}
.col-sm-5 {
  width: 41.66666666666667%;
}
.col-sm-4 {
  width: 33.33333333333333%;
 }
 .col-sm-3 {
   width: 25%;
 }
 .col-sm-2 {
   width: 16.666666666666664%;
 }
 .col-sm-1 {
  width: 8.333333333333332%;
 }

.container{
background-color:white;
width:70%;
margin:0 auto;
height:700px;
}
 
 img {
    max-width: 700px;
    margin-top:20px;
    height:100px;
   
} 
 p.orgn_name{
font-size:50px;
color:#0099CC;
margin-top:30px;
} 
p { margin:  0;}
  
p.code{
font-size:20px;
color:#0099CC;
}
h3{
margin: 0;
 color:#0099CC;
}
li{
  margin: 0  ;

}
.font-style{
 margin:  0  ;
font-size:17px;
color:#0099CC;
font-weight: bold;
}
table {
  font-family: arial, sans-serif;
  border-collapse: collapse;
  width: 100%;
 table-layout: fixed;
}
 th {
  border-bottom: 2px solid #0099CC;
   border-top: 2px solid #0099CC;
  text-align: left;
  /* padding: 5px;  */
   background-color: #00ABE5 !important;
 -webkit-print-color-adjust: exact; 
 color:#FFFFFF;
 font-size:15px;;
}

td.border-bottom{
font-size:12px;
 border-bottom: 1.5px solid #0099CC;
   border-top: 1.5px solid #0099CC;
  text-align: right;
  padding: 10px; 
}
td{
font-size:12px;

  text-align: right;
  padding: 10px; 
}
td.bgcolor{
 background-color: #f5f5f5 !important;
 -webkit-print-color-adjust: exact; 
}


th.centre{
text-align: center;
}
.footer{
text-align: center;
  position: absolute;
  margin-top:80px;
}
.last-row-footer{
height:40px;
/* vertical-align:bottom; */
color:#0099CC;
border-bottom: 1.5px solid #0099CC;


}
thead, tfoot { display: table-row-group }
 table { page-break-inside:auto }
    tr   { page-break-inside:avoid; page-break-after:auto }
   
  }  
</style>
</html>