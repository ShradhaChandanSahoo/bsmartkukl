
<%@include file="/WEB-INF/decorators/taglibs.jsp"%>

<c:if test="${not empty msg}">
	<script>
		var msg = "${msg}";
		swal({
			title : msg,
			text : "",
			confirmButtonColor : "#2196F3",
		});
	</script>
</c:if>



<div class="panel panel-flat">
						<div class="panel-body">
							<form:form action="./addOwnerShipChangeApprove" role="form"  enctype="multipart/form-data" modelAttribute="ownerShipChangeEntity" commandName="ownerShipChangeEntity" method="POST" id="ownerShipChangeEntity">
					
							<fieldset class="content-group">
					
								<legend class="text-bold" style="margin: auto;text-align: center;font-size: 18px;">Ownership Change Request</legend>
								<br>
								
								<div class="row">
									
									<div class="col-md-3">
										<div class="form-group">
											<label>Connection No &nbsp;<font color="red">*</font></label> <form:input type="text"
												class="form-control" name="connection_no" path="connection_no" id="connection_no" placeholder="Connection No..." onchange="getConnectionDetails(this.value)" onkeyup="convertToUpperCase();"></form:input>
										</div>
									</div>
					
									<div class="col-md-3">
										<div class="form-group">
											<label>Old Title</label> <form:input type="text" path="old_consumer_title" class="form-control" name="old_consumer_title" id="old_consumer_title" readonly="true"
												placeholder="Title"></form:input>
										</div>
									</div>
									
									
									<div class="col-md-3">
										<div class="form-group">
											<label>Old Customer Name(Eng)</label> <form:input type="text" path="old_name_eng" class="form-control" name="old_name_eng" id="old_name_eng" readonly="true"
												placeholder=""></form:input>
										</div>
									</div>
									
									<div class="col-md-3">
										<div class="form-group">
											<label>Old Customer Name(Nep)</label> <form:input type="text" path="old_name_nep" class="form-control" name="old_name_nep" id="old_name_nep" readonly="true"
												placeholder=""></form:input>
										</div>
									</div>
									
									</div>
									
									<div class="row">
									
									
									<div class="col-md-3">
										<div class="form-group">
											<label>Old Customer Father Name(Eng)</label> <form:input type="text" path="old_fname_eng" class="form-control" name="old_fname_eng" id="old_fname_eng" readonly="true"
												placeholder=""></form:input>
										</div>
									</div>
									
									<div class="col-md-3">
										<div class="form-group">
											<label>Old Customer Father Name(Nep)</label> <form:input type="text" path="old_fname_nep" class="form-control" name="old_fname_nep" id="old_fname_nep" readonly="true"
												placeholder=""></form:input>
										</div>
									</div>
									
									
									<div class="col-md-3">
										<div class="form-group">
											<label>Old Customer GrandFather(Eng)</label> <form:input type="text" path="old_gfname_eng" class="form-control" name="old_gfname_eng" id="old_gfname_eng" readonly="true"
												placeholder=""></form:input>
										</div>
									</div>
									
									<div class="col-md-3">
										<div class="form-group">
											<label>Old Customer GrandFather(Nep)</label> <form:input type="text" path="old_gfname_nep" class="form-control" name="old_gfname_nep" id="old_gfname_nep" readonly="true"
												placeholder=""></form:input>
										</div>
									</div>
									
									</div>
									
									<div class="row">
									
									<div class="col-md-3">
										<div class="form-group">
											<label>Old CitizenShipNo</label> <form:input type="text" path="old_citizenshipno" class="form-control" name="old_citizenshipno" id="old_citizenshipno" readonly="true"
												placeholder=""></form:input>
										</div>
									</div>
									
									
									<div class="form-group col-md-3">
											<label>New Title <font color="red">*</font></label>
											<form:select class="selectbox input-group" path="new_consumer_title"
												name="new_consumer_title" id="new_consumer_title">
												
												<option value="Mr." data-icon="icon-git-commit">Mr.</option>
												<option value="Campus" data-icon="icon-git-branch">Campus</option>
												<option value="Comp" data-icon="icon-git-commit">Comp</option>
												<option value="Corp" data-icon="icon-git-commit">Corp</option>
												
												<option value="Dr." data-icon="icon-git-commit">Dr.</option>
												<option value="Eng." data-icon="icon-git-commit">Eng.</option>
												<option value="HMG" data-icon="icon-git-commit">HMG</option>
												
												
												<option value="Hospital" data-icon="icon-git-commit">Hospital</option>
												<option value="Hotel" data-icon="icon-git-commit">Hotel</option>
												<option value="Miss." data-icon="icon-git-commit">Miss.</option>
												
												<option value="Mrs." data-icon="icon-git-commit">Mrs.</option>
												<option value="Ms" data-icon="icon-git-commit">Ms</option>
												
												<option value="Mun." data-icon="icon-git-commit">Mun.</option>
												<option value="Hotel" data-icon="icon-git-commit">Hotel</option>
												
												<option value="NG" data-icon="icon-git-commit">NG</option>
												<option value="Office" data-icon="icon-git-commit">Office</option>
												<option value="Park" data-icon="icon-git-commit">Park</option>
												
												<option value="School" data-icon="icon-git-commit">School</option>

												
											</form:select>
										</div>
									
									
									<div class="col-md-3">
										<div class="form-group">
											<label>New Customer Name(Eng)&nbsp;<font color="red">*</font></label> <form:input type="text" path="new_name_eng" class="form-control" name="new_name_eng" id="new_name_eng"
												placeholder=""></form:input>
										</div>
									</div>
									
									<div class="col-md-3">
										<div class="form-group">
											<label>New Customer Name(Nep)</label> <form:input type="text" path="new_name_nep" class="form-control" name="new_name_nep" id="new_name_nep"
												placeholder=""></form:input>
										</div>
									</div>
									
									</div>
									
									<div class="row">
									
									<div class="col-md-3">
										<div class="form-group">
											<label>New Customer Father Name(Eng)&nbsp;<font color="red">*</font></label> <form:input type="text" path="new_fname_eng" class="form-control" name="new_fname_eng" id="new_fname_eng"
												placeholder=""></form:input>
										</div>
									</div>
									
									<div class="col-md-3">
										<div class="form-group">
											<label>New Customer Father Name(Nep)</label> <form:input type="text" path="new_fname_nep" class="form-control" name="new_fname_nep" id="new_fname_nep"
												placeholder=""></form:input>
										</div>
									</div>
									
									
									<div class="col-md-3">
										<div class="form-group">
											<label>New Customer GrandFather(Eng)</label> <form:input type="text" path="new_gfname_eng" class="form-control" name="new_gfname_eng" id="new_gfname_eng"
												placeholder=""></form:input>
										</div>
									</div>
									
									<div class="col-md-3">
										<div class="form-group">
											<label>New Customer GrandFather(Nep)</label> <form:input type="text" path="new_gfname_nep" class="form-control" name="new_gfname_nep" id="new_gfname_nep"
												placeholder=""></form:input>
										</div>
									</div>
									
									
					
					
					
								</div>
								
								
								<div class="row">
								
									<div class="col-md-3">
										<div class="form-group">
											<label>New CitizenShipNo</label> <form:input type="text" path="new_citizenshipno" class="form-control" name="new_citizenshipno" id="new_citizenshipno" 
												placeholder=""></form:input>
										</div>
									</div>
									
									<div class="col-md-3">
										<div class="form-group">
											<label>Effected Date(Nep)&nbsp;<font color="red">*</font></label> 
											<input type="text" id="effective_date" name="effective_date" class="form-control nepali-calendar"
												placeholder="Effected Date">
									</div>
									</div>
									
									<div class="col-md-3">
										<div class="form-group">
											<label>Public Notice Published <font color="red">*</font></label>
											<form:select class="selectbox input-group" path="public_notice_published"
												name="public_notice_published" id="public_notice_published" onchange="showUploadDoc();">
												<option value="Yes" data-icon="icon-git-commit">Yes</option>
												<option value="No" data-icon="icon-git-branch">No</option>
											</form:select>
									</div>
									</div>
									<div class="col-md-3" id="pubNoticeDoc">
										<div class="form-group">
											<label>Public Notice <font color="red">*</font></label>
											<input type="file" name="public_notice_doc" id="public_notice_doc" class="file-styled" onchange="readURL1(this)">
											
									</div>
									</div>
									</div>
									<div class="row">
									<div class="col-md-12">
										<div class="form-group">
											<label>Reason To Change&nbsp;<font color="red">*</font></label> 
											<textarea placeholder="Enter your Reason here in English" class="form-control" cols="1" rows="1" name="reason" id="reason"></textarea>
										</div>
									</div>
					
								</div>
								
							</fieldset>
							
								<div class="text-center">
									<button type="button" class="btn bg-teal btn-ladda"  data-style="expand-right" data-spinner-size="20" onclick="clearButton();"><span class="ladda-label" >Clear</span></button>
									<button type="submit" class="btn bg-teal btn-ladda"  data-style="expand-right" data-spinner-size="20" onclick="return checkValid();"><span class="ladda-label">Submit</span></button>
								</div>
							
						</form:form>
						</div>
					</div>
					
					
					
					
					<div class="panel panel-flat">
					<div class="panel-body" style="overflow: scroll;">
							
							
							
							<fieldset class="content-group" > 
								<legend class="text-bold">List of Connections(Ownership Change)
								 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<button type="button" class="btn btn-danger btn-ladda"  data-style="expand-right" data-spinner-size="20" onclick="return getPendingList();"><span class="ladda-label" >Get Ownership Change List</span></button>
								
								</legend>
							
							<div class="table-responsive" >
								<table class="table datatable-basic table-bordered" id="consumerTable">
									<thead>
										<tr>
											<th>Sl&nbsp;No</th>
											<th>Con&nbsp;No</th>
											<th style="min-width: 180px;">Old Name</th>
											<th style="min-width: 180px;">New Name</th>
											<th>Citizenship No</th>
											<th>Requested By</th>
											<th>Requested Date</th>
											<th>Effected Date</th>
											<th>Approved By</th>
											<th>Approved Date</th>
											<th style="min-width: 250px;">Status</th>
											
										</tr>
									</thead>
									<tbody id="tableBodyId">
										
									
									</tbody>
								</table>
							</div>
						</fieldset>
					
						
						
						</div>
						
						</div>
					
					
					    <div id="loading" hidden="true" style="text-align: center;">
                         	<h3 id="loadingText">Loading..... Please wait. </h3> 
						 	<img alt="image" src="./resources/images/loading.gif" style="width:3%;height: 3%;">
						</div>
					
<script>

$(document).ready(function(){   
	$('#ummdmodify').show();
	$('#masterDataModification').addClass('active');
	
	var activeMod="${activeModulesNew}";
	var module="Master Data Modification";
	var check=activeMod.indexOf(module) > -1;
	
	if(check){
	}else{
		window.location.href="./accessDenied";
	}
	
	  $('#effective_date').nepaliDatePicker({
			 dateFormat: "%D, %M %d, %y",
				npdMonth: true,
				npdYear: true,
				
	  });
	  
	   
	  
	  
});

function convertToUpperCase(){
	$("#connection_no").val($("#connection_no").val().toUpperCase().trim());
}

function getPendingList(){
	
	
	$("#loading").show();
	 $.ajax({
			type : "GET",
			url : "./getOwnerShipChangeList",	
			dataType : "json",
			cache : false,
			async : false,
			success : function(response)
			{	
				if(response.length==0)
				{
					$("#tableBodyId").empty();
					swal({
			             title: "No Data Found.",
			             text: "",
			             confirmButtonColor: "#2196F3",
			         }); 
					$("#loading").hide(); 
					return false;
				}	
				
				var ConnectionData="";
								
							    var count;
								for( var i=0;i<response.length;i++)
								{
				 					data = response[i];
				 					count=i+1;
				 					ConnectionData+="<tr>"+
									"<td>"+count+"</td>";
							  		if(data.connection_no == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.connection_no+"</td>"; }
							  		if(data.old_name_eng == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.old_name_eng+"</td>"; }
							  		if(data.new_name_eng == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.new_name_eng+"</td>";}
							  		if(data.new_citizenshipno == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.new_citizenshipno+"</td>";}
							  		if(data.request_by == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.request_by+"</td>";}
							  		if( data.request_date == null ){ ConnectionData+="<td>-</td>"; }
									else{ ConnectionData+="<td>"+moment(data.request_date).format('DD-MM-YYYY')+"</td>"; }
							  		if(data.effective_date == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.effective_date+"</td>";}
							  		if(data.approve_by == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.approve_by+"</td>";}
							  		if( data.approve_date == null ){ ConnectionData+="<td>-</td>"; }
									else{ ConnectionData+="<td>"+moment(data.approve_date).format('DD-MM-YYYY')+"</td>"; }
							  		if(data.approve_status == null ){ConnectionData+="<td></td>";}
							  		else
							  		{ 
							  			if(data.approve_status == '0'){ConnectionData+="<td>Inprogress</td>";}
							  			else if(data.approve_status == '1'){ConnectionData+="<td>Approved</td>";}
							  			else{ConnectionData+="<td>Rejected</td>";}
							  		}
							  		
							  		ConnectionData+="</tr>";	
								} 
			$("#tableBodyId").html(ConnectionData);
			loadSearchFilter1('consumerTable',ConnectionData,'tableBodyId');
			$("#loading").hide(); 
	
	
	
}
	 })
}

	 function loadSearchFilter1(param,tableData,temp)
	 {
	     $('#'+param).dataTable().fnClearTable();
	     $('#'+param).dataTable().fnDestroy();
	     $('#'+temp).html(tableData);
	     $('#'+param).dataTable();

	 } 
	 
	 
	 
function clearButton(){
	
	window.location.href="./ownerShipChangeReq";
}
function checkValid(){
	
	var connectionNo=$("#connection_no").val();
	var new_name_eng=$("#new_name_eng").val();
	var new_fname_eng=$("#new_fname_eng").val();
	var new_citizenshipno=$("#new_citizenshipno").val();
	var effective_date=$('#effective_date').val();
	var reason=$('#reason').val();
	
	if(connectionNo=="" || connectionNo==null){
		alert("Please Enter Connection No");
		return false;
	}else if(new_name_eng=="" || new_name_eng==null){
		alert("Please enter  New Customer Name");
		return false;
	}
	else if(new_fname_eng=="" || new_fname_eng==null){
		alert("Please enter Father Name");
		return false;
	}else if(new_citizenshipno=="" || new_citizenshipno==null){
		alert("Please Enter New CitizenShip No");
		return false;
	}
	else if(effective_date=="" || effective_date==null){
		alert("Please Select Effected Date Nepali");
		return false;
	}else if(reason=="" || reason==null){
		alert("Please enter Reason to Change");
		return false;
	}
	else{
    	 if(confirm("Are you sure to Send Application for Approval?")){
		 }else{
			 return false;
		 }
		    
    }
}


function getConnectionDetails(connectionno){

$.ajax({
	type : "GET",
	url : "./getConsumerMasterDetails1/"+connectionno,	
	dataType : "json",
	cache : false,
	async : false,
	success : function(response)
	{	
					if(response.length==0)
					{
						alert("Entered Connection Number is not available");
						$("#connection_no").val("");
					}	
					else{
		
						
					   for( var i=0;i<response.length;i++)
						{
		 					data = response[i];
		 					$('#old_consumer_title').val(data.title);
		 					$('#old_name_eng').val(data.name_eng);
		 					$('#old_name_nep').val(data.name_nep);
		 					$('#old_fname_eng').val(data.fname_eng);
		 					$('#old_fname_nep').val(data.fname_nep);
		 					$('#old_gfname_eng').val(data.gfname_eng);
		 					$('#old_gfname_nep').val(data.gfname_nep);
		 					$('#old_citizenshipno').val(data.citizenshipno);
						} 
						
					}
			
		
}
});

$.ajax({
	type : "GET",
	url : "./checkOwnerShipChngPendingStatus/"+connectionno,	
	dataType : "text",
	cache : false,
	async : false,
	success : function(response)
	{	
		if(response=='Can Add'){
			
		} else if(response=='Pending'){
			alert("Connection No is already pending for approval.!");
			window.location.href="./ownerShipChangeReq";
		}
		
}
});

}


function showUploadDoc(){
	var pnp=$("#public_notice_published").val();
	if(pnp=='Yes'){
		$('#pubNoticeDoc').show();
	} else {
		$('#pubNoticeDoc').hide();
	}
	
	
}

function readURL1(input) {
	if (input.files && input.files[0]) {

		var fsize = parseFloat(input.files[0].size / 512).toFixed(2);
		if (Math.floor(fsize) > parseFloat(512.0)) {
			alert(" File size can't be greater than 512 KB\n Steps to Reduce Size of Image File if >512MB [For Image]\n1.Right Click on the image -->open with Paint-->Resize-->select Pixels-->Reduce pixels and Save then upload");
			$('#public_notice_doc').val("");

			return false;
		}

		var reader = new FileReader();
		var regex = /^([a-zA-Z0-9\s_\\.\-:])+(.jpg|.jpeg|.gif|.png|.bmp|.pdf)$/;
		if (regex.test(input.files[0].name.toLowerCase())) {

			reader.onload = function(e) {
				reader.readAsDataURL(input.files[0]);
			}
		} else {
			alert("Only images and pdf files are allowed with Valid File name");
			$('#public_notice_doc').val("");
			return false;
		}
	}
}
 

</script>


<style>

select{
width: 100%; border: 1px solid #DDD; border-radius: 3px; height: 36px;
}

legend {
	text-transform: none;
	font-size: 16px;
	border-color: #F01818;
}

.datatable-header, .datatable-footer {
    padding: 0px 0px 0 0px;
}

.modal.fade .modal-dialog {
width: 1200px;
}

input[type="file"] {
    display: ruby-base-container;
}

</style>


