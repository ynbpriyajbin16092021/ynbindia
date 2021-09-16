function loadClinics()
{
	$.ajax({
		type:'GET',
		contentType:'application/json',
		url:'clinicslist',
		success:function(data,textStatus,jqXHR)
		{
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			
			$('#clinicDT').DataTable( {
		    	data: responseValue,
		    	dom: 'Bfrtip',
		        buttons: [
		             'excel', 'pdf'
		        ],
		        columns: [
		            { data: 'clinicname' },
		            { data: 'addressdisp' },
		            { data: 'workinghoursdisp' },
		            { data: 'timeperslot' },
		            { data: 'breakhoursdisp' },
		            { data: 'breakmins' },
		            { data: 'action' }
		        ]
	            
		    } ); 
			
			selectClinicItem();
		}
	});
	
}

function selectClinicItem() {
	$('#clinicDT tbody').on('click', '.delete.myBtnTab', function() {
		var table = $('#clinicDT').DataTable();
		var clinicObject = [];
		clinicObject.push(table.row( $(this).parents('tr') ).data());
		/*categoryObject.push(table.row(this).data());*/
		console.log(clinicObject)
		var object = clinicObject[0];
		
		var table = $('#clinicDT').DataTable();
		confirmClinicDelete(table.row($(this).parents('tr')),object);
		
	});
	$('#clinicDT tbody').on('click', '.edit.myBtnTab', function() {
		$(this).parents('tr').addClass("selected").siblings().removeClass("selected"); 
		var table = $('#clinicDT').DataTable();
		var clinicObject = [];
		clinicObject.push(table.row( $(this).parents('tr') ).data());
		/*categoryObject.push(table.row(this).data());*/
		console.log(clinicObject)
		var object = clinicObject[0];
		window.location.href="addclinic?cid="+object['clinicid'];
		
	});
}


function loadaddclinic()
{
	if(url != ""){
	var res = url.split("&");
	var checkid =  res[1][res[1].length -1];
	console.log(checkid)
	if(checkid == 1){
	var cid = res[0][res[0].length -1];
	$("#clinicidtext").val(cid);
	}
	}
	else{
	$('#clinicidtext').val(getParameterByName("cid"));
	}
	focuscursor('clinicnametext');
	$.ajax(
			{
			type:'GET',
			contentType:'application/json',
			url:'getclinicdetail/'+$('#clinicidtext').val(),
			success:function(data,textStatus,jqXHR)
			{
				var responseType = getResponseType(data);
				var responseValue = getResponseValue(data);
				var cliniclist = responseValue['clinicList'];
				console.log(cliniclist);
				if(responseType == 'S')
					{
					
					if(cliniclist.length > 0)
						{
						var clinicobj = cliniclist[0];
						
					var wstarthourin24f = parseInt(clinicobj.starthour);
					var wendhourin24f = parseInt(clinicobj.endhour);
					var bstarthourin24f = parseInt(clinicobj.breakhourstart);
					var bendhourin24f = parseInt(clinicobj.breakhourend);
					var wstartampm = "AM";
					var wendampm = "AM";
					var bstartampm = "AM";
					var bendampm = "AM";
					
					if(wstarthourin24f > 12)
					{
						wstarthourin24f = wstarthourin24f - 12;
						wstartampm = "PM";
					}
					if(wendhourin24f > 12)
					{
						wendhourin24f = wendhourin24f - 12;
						wendampm = "PM";
					}

					if(bstarthourin24f > 12)
					{
						bstarthourin24f = bstarthourin24f - 12;
						bstartampm = "PM";
					}
					if(bendhourin24f > 12)
					{
						bendhourin24f = bendhourin24f - 12;
						bendampm = "PM";
					}
					console.log(wstarthourin24f+"   "+wendhourin24f+"   "+bstarthourin24f+"   "+bendhourin24f);

						
						$('#clinicnametext').val(clinicobj.clinicdesc);
						$('#statetext').val(clinicobj.state);
						$('#citytext').val(clinicobj.city);
						$('#mobnotext').val(clinicobj.mobileno);
						$('#clinicstartingyeartext').val(clinicobj.startyear);

						$('#emailtext').val(clinicobj.email);
						$('#addresstext').val(clinicobj.address);
						$('#countrytext').val(clinicobj.country);
						
						$('#workstarthoursel').val(wstarthourin24f);
						$('#workstartminsel').val(clinicobj.startmin);
						$('#workstartampmsel').val(wstartampm);
						$("#workingfromtime").val(wstarthourin24f+":"+clinicobj.startmin+" "+wstartampm);
						
						$('#workendhoursel').val(wendhourin24f);
						$('#workendminsel').val(clinicobj.endmin);
						$('#workendampmsel').val(wendampm);
						$("#workingtotime").val(wendhourin24f+":"+clinicobj.endmin+" "+wendampm);
						
						$('#breakhourstartsel').val(bstarthourin24f);
						$('#breakminstartsel').val(clinicobj.breakminstart);
						$('#breakstartampmsel').val(bstartampm);
						$("#breakfromtime").val(bstarthourin24f+":"+clinicobj.breakminstart+" "+bstartampm);
						
						$('#breakhourendsel').val(bendhourin24f);
						$('#breakminendsel').val(clinicobj.breakminend);
						$('#breakampmendsel').val(bendampm);
						$("#breaktotime").val(bendhourin24f+":"+clinicobj.breakminend+" "+bendampm);

						$('#timeperslottext').val(clinicobj.timeperslot);
						
						$('#tinnotext').val(clinicobj.tinno);
						$('#websitenametext').val(clinicobj.websitename);
						$('#branchnametext').val(clinicobj.branchname);
						$('#regnnotext').val(clinicobj.regnno);
						$('#druglicensenotext').val(clinicobj.druglicenceno);
						$('#faxnotext').val(clinicobj.faxno);
						$('#telephonenotext').val(clinicobj.telno);
						$('#totalsmstext').val(clinicobj.totalsmscount);
						
						if(parseInt(clinicobj.smsintegration) == 0)
							{
							$('#smsintegrationcheck').attr('checked',false);
							}
						else
							{
							$('#smsintegrationcheck').attr('checked',true);
							}
						}
					console.log(responseValue['sentsms'])
					$('#smssentcount').val(responseValue['sentsms']);
					}
				}
			}		
			);
	$.ajax({
		type:'GET',
		contentType:'application/json',
		url:'clinicslist',
		success:function(data,textStatus,jqXHR)
		{
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			
			$('#clinicDT').DataTable( {
		    	data: responseValue,
		    	dom: 'Bfrtip',
		        buttons: [
		             'excel', 'pdf'
		        ],
		        columns: [
		            { data: 'clinicname' },
		            { data: 'addressdisp' },
		            { data: 'workinghoursdisp' },
		            { data: 'timeperslot' },
		            { data: 'breakhoursdisp' },
		            { data: 'breakmins' }
		        ]
	            
		    } ); 
			
			selectClinicItem();
		}
	});
}

function saveclinic()
{
var isValidArray = [];
	
	/*isValidArray.push(validateonbuttonclick('#clinicidtext','input'));
	isValidArray.push(validateonbuttonclick('#workstarthoursel','input'));
	isValidArray.push(validateonbuttonclick('#workstartminsel','input'));
	isValidArray.push(validateonbuttonclick('#workstartampmsel','input'));
	isValidArray.push(validateonbuttonclick('#workendhoursel','input'));
	
	isValidArray.push(validateonbuttonclick('#workendminsel','input'));
	isValidArray.push(validateonbuttonclick('#workendampmsel','input'));
	
	isValidArray.push(validateonbuttonclick('#breakhourstartsel','input'));
	isValidArray.push(validateonbuttonclick('#breakminstartsel','input'));
	
	isValidArray.push(validateonbuttonclick('#breakstartampmsel','input'));
	isValidArray.push(validateonbuttonclick('#breakhourendsel','input'));
	
	isValidArray.push(validateonbuttonclick('#breakminendsel','input'));sss

	isValidArray.push(validateonbuttonclick('#breakampmendsel','input'));*/
	
	isValidArray.push(validateonbuttonclick('#faxnotext','input'));
	isValidArray.push(validateonbuttonclick('#mobnotext','input'));
	isValidArray.push(validateonbuttonclick('#telephonenotext','input'));
	isValidArray.push(validateonbuttonclick('#totalsmstext','input'));
	isValidArray.push(validateonbuttonclick('#emailtext','input'));
	isValidArray.push(validateonbuttonclick('#clinicnametext','input'));
	isValidArray.push(validateonbuttonclick('#citytext','input'));
var totalValid = jQuery.inArray( false, isValidArray );
if(totalValid < 0)
{
var clinicobj = new Object();
var clinicseqid = $('#clinicidtext').val();

var startyear = parseInt($('#clinicstartingyeartext').val());
var tinno = $('#tinnotext').val();
var workhourstart = parseInt($('#workstarthoursel').val());
var workminstart = parseInt($('#workstartminsel').val());
var workampmstart = $('#workstartampmsel').val();
var workhourend = parseInt($('#workendhoursel').val());
var workminend = parseInt($('#workendminsel').val());
var workampmend = $('#workendampmsel').val();

var breakhourstart = parseInt($('#breakhourstartsel').val());
var breakminstart = parseInt($('#breakminstartsel').val());
var breakampmstart = $('#breakstartampmsel').val();
var breakhourend = parseInt($('#breakhourendsel').val());
var breakminend = parseInt($('#breakminendsel').val());
var breakampmend = $('#breakampmendsel').val();


var wstarthourin24f = 0,wendhourin24f = 0,bstarthourin24f = 0,bendhourin24f = 0;

if($('#workstartampmsel').val() == 'PM')
	{
	wstarthourin24f = parseInt(wstarthourin24f) + 12;
	}
else
	{
	wstarthourin24f = $('#workstarthoursel').val();
	}
if($('#workendampmsel').val() == 'PM')
{
	wendhourin24f = parseInt($('#workendhoursel').val()) + 12;
}
else
	{
	wendhourin24f = $('#workendhoursel').val();
	}


if($('#breakstartampmsel').val() == 'PM')
{
bstarthourin24f = parseInt($('#breakhourstartsel').val()) + 12;
}
else
{
bstarthourin24f = $('#breakhourstartsel').val();
}
if($('#breakampmendsel').val() == 'PM')
{
bendhourin24f = parseInt($('#breakhourendsel').val()) + 12;
}
else
{
bendhourin24f = $('#breakhourendsel').val();
}

var workstartmin = wstarthourin24f * 60 + parseInt(workminstart);
var workendmin = wendhourin24f * 60 + parseInt(workminend);
var breakstartmin = bstarthourin24f * 60 + parseInt(breakminstart);
var breakendmin = bendhourin24f * 60 + parseInt(breakminend);

if(parseInt(workstartmin) > parseInt(workendmin))
	{
	displayAlertMsg("Working StartTime cannot be less than EndTime");
	return false;
	}
else if(parseInt(breakstartmin) > parseInt(breakendmin))
	{
	displayAlertMsg("Break StartTime cannot be less than EndTime");
	return false;
	}
else if(parseInt(breakstartmin) < parseInt(workstartmin))
{
	displayAlertMsg("Kindly check Break Time");
return false;
}
else
	{
	
clinicobj.startyear = startyear;
clinicobj.tinno = tinno;
clinicobj.websitename = $('#websitenametext').val();
clinicobj.branchname = $('#branchnametext').val();
clinicobj.regnno = $('#regnnotext').val();
clinicobj.druglicenceno = $('#druglicensenotext').val();

clinicobj.telno = $('#telephonenotext').val();
var regex =/\(?([0-9]{3})\)?([ .-]?)([0-9]{3})\2([0-9]{4})/;
if(regex.test(clinicobj.telno)){
	
}
else{
	 heroNotifications.showFailure("Your Telephone no is In correct");
	 return false;
}
clinicobj.faxno = $('#faxnotext').val();
var regex =/[\+? *[1-9]+]?[0-9 ]+/;
if(regex.test(clinicobj.faxno)){
	
}
else{
	 heroNotifications.showFailure("Your FAX no is In correct");
	 return false;
}
if($('#smsintegrationcheck').prop('checked'))
clinicobj.smsintegration = '1';
else
clinicobj.smsintegration = '0';
clinicobj.totalsmscount = $('#totalsmstext').val();

clinicobj.clinicid = clinicseqid;
clinicobj.clinicdesc = $('#clinicnametext').val();
clinicobj.state = $('#statetext').val();
clinicobj.city = $('#citytext').val();
clinicobj.mobileno = $('#mobnotext').val();
var regex =/^((\+[1-9]{1,4}[ \-]*)|(\([0-9]{2,3}\)[ \-]*)|([0-9]{2,4})[ \-]*)*?[0-9]{3,4}?[ \-]*[0-9]{3,4}?$/;
if(regex.test(clinicobj.mobileno)){
	
}
else{
	 heroNotifications.showFailure("Your Mobile no is In correct");
	 return false;
}
clinicobj.email = $('#emailtext').val();
var regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
if(regex.test(clinicobj.email)){
	
}
else{
	 heroNotifications.showFailure("Your Email is In correct");
	 return false;
}



clinicobj.address = $('#addresstext').val();
clinicobj.country = $('#countrytext').val();


clinicobj.starthour = wstarthourin24f;
clinicobj.startmin = $('#workstartminsel').val();

clinicobj.endhour = wendhourin24f;
clinicobj.endmin = $('#workendminsel').val();
clinicobj.workstartampm = $('#workstartampmsel').val();
clinicobj.workendampm = $('#workendampmsel').val();
clinicobj.breakhourstart = bstarthourin24f;

clinicobj.breakhourend = bendhourin24f;
clinicobj.breakminstart = $('#breakminstartsel').val();
clinicobj.breakminend = $('#breakminendsel').val();
clinicobj.breakstartampm = $('#breakstartampmsel').val();
clinicobj.breakendampm = $('#breakampmendsel').val();

clinicobj.timeperslot = $('#timeperslottext').val();
clinicobj.userid = $('#inventoryuseridtext').val();

if(parseInt(clinicseqid) == 0)
clinicobj.oprn = 'INS';
else
clinicobj.oprn = 'UPD';	
	
console.log(clinicobj)
var regex =/^((ftp|http|https):\/\/)?(www.)?(?!.*(ftp|http|https|www.))[a-zA-Z0-9_-]+(\.[a-zA-Z]+)+((\/)[\w#]+)*(\/\w+\?[a-zA-Z0-9_]+=\w+(&[a-zA-Z0-9_]+=\w+)*)?$/gm;
if(regex.test(clinicobj.websitename)){
$.ajax(
		{
		type:'POST',
		contentType:'application/json',
		url:'saveclinic',
		data:JSON.stringify(clinicobj),
		success:function(data,textStatus,jqXHR)
		{
			var responseType = getResponseType(data);
			var responseValue = getResponseValue(data);
			console.log(getResponseType)
			//alert(responseMsg(responseValue));
			if(responseType == 'S')
				{
				displaySuccessMsg(data);
				setTimeout("location.href = 'clinics'",2000);
				//window.location.href = "clinic";
				}
			else if(responseType == 'F')
			{
			displayFailureMsg("",responseMsg(responseValue));
			
		
			}
			}
		}		
		);
}
	
else{
    heroNotifications.showFailure("Your Website name is In correct");
    return false;
}
	}
}
else{
	heroNotifications.showFailure("Please fill the Mandatory fields");
	//setTimeout("location.href = 'adduser'",900);
}
		}
function confirmClinicDelete(tableRow,clinicObject)
{
	
	$('.btn.btn-white').on('click',function()
			{
	var clinicobj = new Object();
	var clinicseqid = $('#clinicidtext').val();
	
	clinicobj.clinicid = clinicObject.clinicid;
	clinicobj.oprn = 'DEL';
	
	console.log(clinicobj);
	
	$.ajax(
			{
			type:'POST',
			contentType:'application/json',
			url:'saveclinic',
			data:JSON.stringify(clinicobj),
			success:function(data,textStatus,jqXHR)
			{
				var responseType = getResponseType(data);
				var responseValue = getResponseValue(data);
				/*alert(responseMsg(responseValue));*/
				console.log(getResponseType)
				if(responseType == 'S')
					{
					displaySuccessMsg(data);
					setTimeout("location.href = 'clinics'",2000);
					}
				else if(responseType == 'F')
				{
					displayFailureMsg("",responseMsg(responseValue));
				}
				}
			}		
			);
			});
}

