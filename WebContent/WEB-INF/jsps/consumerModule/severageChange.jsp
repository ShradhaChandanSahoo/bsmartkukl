<%@include file="/WEB-INF/decorators/taglibs.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="panel panel-flat">
				
<div class="panel-body">
	<form action="./sewageChangeOne" method="POST" id="recalculateId">

	<fieldset class="content-group">

		<legend class="text-bold" style="margin: auto;text-align: center;font-size: 18px;">Modify Sewage Used</legend>
		<br>
		
		<div class="row">
			
			<div class="col-md-3">
				<div class="form-group">
					<label>Connection No &nbsp;<font color="red">*</font></label> <input type="text"
						class="form-control" name="connection_no"  id="connection_no" placeholder="Connection No..." onchange="getConnectionDetails(this.value)" onkeyup="convertToUpperCase();">
				</div>
			</div>

			<div class="col-md-3">
				<div class="form-group">
					<label>Name</label> <input type="text" class="form-control" name="name_eng" id="name_eng" readonly="readonly"
						placeholder="Name">
				</div>
			</div>

			<div class="col-md-3">
				<div class="form-group">
					<label>Connection Category</label> <input type="text" class="form-control" name="con_category" id="con_category" readonly="readonly"
						placeholder="Connection Category...">
				</div>
			</div>
			<div class="col-md-3">
				<div class="form-group">
					<label>Connection Type</label> <input type="text" class="form-control" name="con_type" id="con_type" readonly="readonly" 
						placeholder="Connection Type...">
				</div>
			</div>
			
			
		</div>
		
		
		<div class="row">
		
			
			
			
			<div class="col-md-3">
				<div class="form-group">
					<label>Area No</label> <input type="text" class="form-control" name="area_no"  id="area_no" readonly="readonly"
						placeholder="Area No...">
				</div>
			</div>
			
			
			<div class="col-md-3">
				<div class="form-group">
					<label>Pipe Size</label> <input type="text" class="form-control" name="pipe_size"  id="pipe_size" readonly="readonly"
						placeholder="Pipe Diameter...">
				</div>
			</div>
			<div class="col-md-3" id="mthValIdOne">
				<div class="form-group">
					<label>Month Year</label> <input type="text"  class="form-control" name="mthyr"  id="mthyr" readonly="readonly" placeholder="Max Month yr..">
				</div>
			</div>
			<div class="col-md-3" hidden="true" id="mthValId">
				<div class="form-group">
					<label>Month Year</label> <input type="text"  value="${latestNepaliMonth}" class="form-control" name="mthyr1"  id="mthyr1" readonly="readonly">
				</div>
			</div>
							

	<div class="col-md-3"  id="selSU">
						<div class="form-group">
							<label>Sewage Used&nbsp;<font color='red'>*</font></label><select data-placeholder="Select"
								class="select" id="sewage_used" name="sewage_used"
								required="required">
								<option value="" data-icon="icon-git-branch">Select</option>
								<option value="Yes" data-icon="icon-git-branch">Yes</option>
								<option value="No" data-icon="icon-git-branch">No</option>

							</select>
						</div>
					</div>
      
       <!-- <div class="col-md-4"  id="remarks">
				<div class="form-group">
					<label>Remarks &nbsp;<font color="red">*</font></label> <input type="text"   class="form-control" name="remarks1"  id="remarks1">
				</div>
			</div> --><div class="col-md-12">
						<div class="form-group">
							<label>Remarks&nbsp;<font color="red">*</font></label>
							<textarea placeholder="Enter your Remarks here in English"
								class="form-control" cols="1" rows="1" name="remarks1"
								id="remarks1" value="Bill Correction"></textarea>
						</div>
					</div>
       </div>
			
	</fieldset>
	
		<div class="text-center">
				<button type="button" class="btn bg-teal btn-ladda"  data-style="expand-right" data-spinner-size="20" onclick="clearButton();"><span class="ladda-label" >Clear</span></button>
			<button type="submit" class="btn bg-teal btn-ladda"  data-style="expand-right" data-spinner-size="20" onclick="return validateData()"><span class="ladda-label">Submit</span></button>
		<!-- 	<button type="button" class="btn bg-teal btn-sm" data-toggle="modal" data-target="#modal_default1" id="onshow_callback" onclick='ledgerHistoryPopUp()'>View Ledger</button> -->
		</div>
		
	
</form>
<%--  <div id="gridId1">
			<fieldset class="content-group">
				<legend class="text-bold">Sewage Used Change Details</legend>
				<div class="row">
					<div class="table-responsive">
						<div class="col-md-16">
							<div class="panel-body" style="margin-top: -3px; padding: 6px;">
								<table class="table datatable-basic table-bordered"
									id="tableForm">
									<thead style="width: 100px">

										<tr class="bg-blue">
											<th style="width: 50px">Connection No</th>
											<th style="width: 50px">Month Year</th>
											<th style="width: 50px">Old Sewage Used</th>
											<th style="width: 50px">New Sewage Used</th>
											<th style="width: 50px">Updated By</th>
											<th style="width: 50px">Updated Date</th>
											<th style="width: 50px">Remarks</th>
										</tr>

									</thead>
									<tbody>
										<c:forEach var="app" items="${list}">
											<tr>
												<td>${app.connectionNO}</td>
												<td>${app.monthYr}</td>	
											    <td>${app.sewage_Used_Old}</td> 
												<td> ${app.sewage_Used_New}</td>
											     <td> ${app.addBy}</td>
											    <!--   <td> ${app.addDate}</td> -->
											  <td>  <fmt:formatDate value="${app.addDate}" pattern="yyyy-MM-dd" /></td>
											     <td> ${app.remarks}</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>

							</div>

						</div>
					</div>

				</div>

			</fieldset>
		</div> --%>
</div>
</div>



<script>
function clearButton()
{
	window.location.href = "./sewageChange";
	}
 function validateData()
{
	var con=$('#connection_no').val();
	var remarks=$('#remarks1').val();
	
	if(con==null || con==""){
		swal("Please Enter Connection No");
		return false;
	}
	
	else if(remarks==null || remarks=="")
		{
		swal("Please Enter Remarks");
		return false;
		}
	else
		{
		return true;
		}
	
}
 





window.onload = function(){   
	if('${msgVal}'==null || '${msgVal}'=='')
		{
		
		}
	else
		{
		swal('${msgVal}');
		}
	
	$('#ent_date_nep').nepaliDatePicker({
		dateFormat : "%D, %M %d, %y",
		npdMonth : true,
		npdYear : true
	});

	$('#conc_date_nep').nepaliDatePicker({
		dateFormat : "%D, %M %d, %y",
		npdMonth : true,
		npdYear : true
	});
	
	$('#ummdmodify').show();
	$('#masterDataModification').addClass('active');
	
	var activeMod="${activeModulesNew}";
	var module="Master Data Modification";
	var check=activeMod.indexOf(module) > -1;
	
	if(check){
	}else{
		window.location.href="./accessDenied";
	}
	$("#connection_no").focus();
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
						$("#mthValId").hide();
						$("#mthValIdOne").show();
					}	
					else{
		
						
					   for( var i=0;i<response.length;i++)
						{
		 					data = response[i];
		 					$('#name_eng').val(data.name_eng);
		 					$('#con_category').val(data.con_category);
		 					$('#area_no').val(data.area_no);
		 					$('#pipe_size').val(data.pipe_size);
		 					$('#con_type').val(data.con_satatus);
		 					$('#sewage_used').val(data.sewage_used).trigger("change");
		 					//$('#sew_use').find('option').remove().end().append('<option value="'+ response.con_satatus +'">'+ response.con_satatus +'</option>');
		 					
		 					
		 					
						} 
					    $("#mthValId").show();
						$("#mthValIdOne").hide();
					  
					}
			
		
}
});

}

	function convertToUpperCase() {
		$("#connection_no").val($("#connection_no").val().toUpperCase().trim());
	}
</script>


<style>
.modal.fade .modal-dialog {
width: 1260px;
}
.modal-body {
    position: relative;
    padding: 0px;
}
.datatable-header, .datatable-footer {
    padding: 5px 20px 0 20px;
}

body {
    font-family: "Roboto", Helvetica Neue, Helvetica, Arial, sans-serif;
    font-size: 12px;
    line-height: 1.5384616;
    color: #333333;
}
   
</style>