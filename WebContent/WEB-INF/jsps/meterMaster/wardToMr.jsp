<%@include file="/WEB-INF/decorators/taglibs.jsp"%>
	
	<script type="text/javascript">
	
		function finalSubmit() {
		  swal({
	          title: "Added Successfully",
	          confirmButtonColor: "#2196F3"
	      });
		}
	
		
		function putMrid(val){
			
			var a=val.split("-");
			
			/* alert(a[1]);
			alert(a[0]); */
			$('#mr_id').val(a[0]);
			$('#mrid').val(a[1]);
			
			
		}
		
		</script>
		
		      <c:if test = "${not empty msg}"> 			        
					        <script>		        
					            var msg = "${msg}";
					            swal({
					                title: msg,
					                text: "",
					                confirmButtonColor: "#2196F3",
					            });
					        </script>
			   </c:if>
	
	
	<div class="panel panel-flat">
	
	
		<div class="panel-body">
		<div class="row" hidden="true" id="alertDiv"></div>
		<form:form action="./addWardToMrEntity" role="form"
			modelAttribute="wardToMrEntity" commandName="wardToMrEntity"
			method="POST" id="wardToMrEntity">
			<fieldset class="content-group">
				<legend class="text-bold">Meter Reader Assign</legend>
	
				<div class="col-md-12">
	
						<div class="col-md-3">
						<div class="form-group">
							<label>Ward Number</label><form:select data-placeholder="Select" class="select" id="wardNumber" name="wardNo" path="ward_no" required="required">
							    <option value="" data-icon="icon-git-branch">Select</option>
								
								<c:forEach items="${wardNoList}" var="ward">
								<option value="${ward.wardNo}">${ward.wardNo}</option>
							   </c:forEach>
								
							</form:select>
						</div>
					  </div>
					  
					  
						<div class="col-md-2">
						<div class="form-group">
							<label>Reading Day</label><form:select data-placeholder="Select" class="select" id="reading_day" name="reading_day" path="reading_day" required="required">
							    <option value="" data-icon="icon-git-branch">Select</option>
								
								<c:forEach items="${readingDayList}" var="rday">
								<option value="${rday.readingDay}">${rday.readingDay}</option>
							   </c:forEach>
								
							</form:select>
						</div>
					  </div>


					<div class="col-md-3">
						<div class="form-group">
							<label>Meter Reader</label>
							<select data-placeholder="Select" class="select" id="mrSelect"
								name="mrSelect"  required="required" onchange="putMrid(this.value)">
								<option value="" data-icon="icon-git-branch">Select</option>

								<c:forEach items="${meterReaderList}" var="mr">
									<option value="${mr.mrCode}-${mr.id}">${mr.mrName}-${mr.mrCode}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div hidden="true">
						<form:input path="id" id="id" name="id" type="text"
							class="form-control"/>
					</div>
					<div hidden="true">
						<form:input path="added_by" id="added_by" name="added_by" type="text"
							class="form-control"/>
					</div>
					<div hidden="true">
						<form:input path="added_date" id="added_date" name="added_date" type="text"
							class="form-control"/>
					</div>

					<div hidden="true">
						<form:input path="mrid" id="mrid" name="mrid" type="text"
							class="form-control"/>
					</div>
					<div hidden="true">
						<form:input path="mr_id" id="mr_id" name="mr_id" type="text"
							class="form-control"/>
					</div>
						
					<div class="col-md-2" id="addId">
						<div class="form-group">
							<label>&nbsp;</label>
							<div class="input-group">
								<button type="submit"
									class="btn bg-teal btn-ladda" data-style="expand-right"
									data-spinner-size="20" style="width: 120px;" onclick="return addMr();">
									<span class="ladda-label">Add</span>
								</button>
							</div>
						</div>
					</div>



					<div class="col-md-2" id="editId" hidden="true">
						<div class="form-group">
							<label>&nbsp;</label>
							<div class="input-group">
								<button type="button" class="btn bg-teal btn-ladda"
									data-style="expand-right" data-spinner-size="20"
									onclick="return updateMr();" style="width: 120px;">
									<span class="ladda-label">Modify</span>
								</button>
							</div>
						</div>
					</div>
					
					<div class="col-md-2">
						<div class="form-group">
							<label>&nbsp;</label>
							<div class="input-group">
								<button type="button"
									class="btn bg-teal btn-ladda" data-style="expand-right" style="width: 120px;"
									data-spinner-size="20" onclick="return reload();">
									<span class="ladda-label">Reset</span>
								</button>
							</div>
						</div>
					</div>


				</div>
			</fieldset>
			<!-- <div class="text-center">
					
			        <button type="submit" id="generateLedButton"
						class="btn bg-teal btn-ladda"
						data-style="expand-right" data-spinner-size="20" style="width: 120px;">
						<span class="ladda-label">Add</span>
					</button>
				
				<button type="button"
					class="btn bg-teal btn-ladda"
					data-style="expand-right" data-spinner-size="20" onclick="showUnbilled();" style="width: 120px; display: none;">
					<span class="ladda-label" >Edit</span>
				</button>
			</div> -->
			</form:form>
	
		</div>
	</div>


<div class="panel panel-flat">
	<div class="panel-body" style="overflow: scroll;">
		<fieldset class="content-group">
			<legend class="text-bold">Ward Wise Meter Reader Details</legend>
			<div class="table-responsive">
				<div class="col-md-12">
					<div class="panel-body" style="margin-top: -3px; padding: 6px;">
						<table class="table datatable-basic table-bordered" id="tableForm">
							<thead>

								<tr class="bg-blue">
									<th>Ward No</th>
									<th>Reading Day</th>
									<th>Meter Reader</th>
									<th>Edit</th>
								</tr>

							</thead>
							<tbody>
								<c:forEach var="data" items="${mtrDetailsList}">
									<tr>
									
										<td>${data.ward_no}</td>
										<td>${data.reading_day}</td>
										<td>${data.mr_name} - ${data.mr_id}</td>
										<td class="text-center"><a href="#"
											onclick="editMrDetails('${data.id}','${data.ward_no}','${data.reading_day}','${data.mr_id}','${data.added_by}','${data.added_date}','${data.mrid}')">[Edit]</a></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>

					</div>
				</div>
			</div>


		</fieldset>
	</div>

</div>

		
<script>


window.onload = function(){ 
	$('#meteringscreens').show();
	$('#meterManagement').addClass('active');
	
	var activeMod="${activeModulesNew}";
	var module="Metering";
	var check=activeMod.indexOf(module) > -1;
	if(check){
		
	}else{
		window.location.href="./accessDenied";
	}
	//$('#wardNumber option:not(:selected)').prop('disabled', false);
	//$('#reading_day option:not(:selected)').prop('disabled', false);
}


function editMrDetails(id,ward_no,reading_day,mr_id,added_by,added_date,mrCode)
{
	//alert(ins_date_eng)
	$("#id").val(id);
	$("#added_by").val(added_by);
	$("#added_date").val(added_date);
	//$("#wardNumber").val(ward_no).trigger("change");
	//$("#reading_day").val(reading_day).trigger("change");
	var mrSelect=mr_id+"-"+mrCode;
	//alert(mrSelect);
	$('#mr_id').val(mr_id);
	$('#mrid').val(mrCode);
	$("#mrSelect").val(mrSelect).trigger("change");
	
	//$('#wardNumber option:not(:selected)').prop('disabled', true);
	//$('#reading_day option:not(:selected)').prop('disabled', true);
	
	$('#wardNumber').find('option').remove().end().append('<option value="'+ ward_no +'">'+ ward_no +'</option>');
	$("#wardNumber").val(ward_no).trigger("change");
	$('#reading_day').find('option').remove().end().append('<option value="'+ reading_day +'">'+ reading_day +'</option>');
	$("#reading_day").val(reading_day).trigger("change");
	//$("#wardNumber option[value=" + ward_no + "]").prop('disabled',false).siblings().prop('disabled',true);
	//$("#reading_day option[value=" + reading_day + "]").prop('disabled',false).siblings().prop('disabled',true);
	
	$("#addId").hide();
	$("#editId").show();
}

function addMr()
{
	var ward_no=$("#wardNumber").val();
	var reading_day=$("#reading_day").val();
	var mr_id=$("#mr_id").val();
	
	if($("#wardNumber").val().trim()=='')
	{
	$('#alertDiv').show();
      $('#alertDiv').html('<div class="alert alert-danger alert-bordered"><button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button><span class="text" id="alert">Please Select Ward No</span>');
      $("#alertDiv").fadeOut(5000);
      return false;
	}
	
	if($("#reading_day").val().trim()=='')
	{
	$('#alertDiv').show();
      $('#alertDiv').html('<div class="alert alert-danger alert-bordered"><button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button><span class="text" id="alert">Please Select Reading Day</span>');
      $("#alertDiv").fadeOut(5000);
      return false;
	}
	if($("#mr_id").val().trim()=='')
	{
	$('#alertDiv').show();
      $('#alertDiv').html('<div class="alert alert-danger alert-bordered"><button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button><span class="text" id="alert">Please Select Meter Reader</span>');
      $("#alertDiv").fadeOut(5000);
      return false;
	}
	
	
	var datavalue="";
	$.ajax({
		  type: "GET",
		  url: "./uniqueWardToMrDetails/"+ward_no+"/"+reading_day,
		  dataType: "text",
		  cache       : false,
		  async       : false,
		  success: function(response)
		  {
			  if(response=="CanAdd"){
				 return true;
				}else{
					datavalue="AlreadyExist";
				}
		  }
		});
	if(datavalue=='AlreadyExist'){
		swal({
            title: "Meter Reader Already Assigned For Ward No "+ward_no+", and Reading Day "+reading_day+".",
            text: "",
            confirmButtonColor: "#2196F3",
        });
		return false;
	}
	else{
		return true;
	}

}

function updateMr()
{
	var id=$("#id").val();
	var ward_no=$("#wardNumber").val();
	var reading_day=$("#reading_day").val();
	var mr_id=$("#mr_id").val();
	//$('#wardNumber').attr("style", "pointer-events: none;");
	//$('#reading_day').attr("style", "pointer-events: none;");
	//$('#wardNumber option:not(:selected)').prop('disabled', true);
	//$('#reading_day option:not(:selected)').prop('disabled', true);
	
	if($("#wardNumber").val().trim()=='')
	{
	$('#alertDiv').show();
      $('#alertDiv').html('<div class="alert alert-danger alert-bordered"><button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button><span class="text" id="alert">Please Select Ward No</span>');
      $("#alertDiv").fadeOut(5000);
      return false;
	}
	
	if($("#reading_day").val().trim()=='')
	{
	$('#alertDiv').show();
      $('#alertDiv').html('<div class="alert alert-danger alert-bordered"><button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button><span class="text" id="alert">Please Select Reading Day</span>');
      $("#alertDiv").fadeOut(5000);
      return false;
	}
	if($("#mr_id").val().trim()=='')
	{
	$('#alertDiv').show();
      $('#alertDiv').html('<div class="alert alert-danger alert-bordered"><button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button><span class="text" id="alert">Please Select Meter Reader</span>');
      $("#alertDiv").fadeOut(5000);
      return false;
	}
	
	
	var datavalue="CanAdd";
	/* $.ajax({
		  type: "GET",
		  url: "./uniqueWardToMrDetails/"+ward_no+"/"+reading_day,
		  dataType: "text",
		  cache       : false,
		  async       : false,
		  success: function(response)
		  {
			  if(response=="CanAdd"){
				 return true;
				}else{
					datavalue="AlreadyExist";
				}
		  }
		}); */
	if(datavalue=='AlreadyExist'){
		swal({
            title: "Meter Reader Already Assigned For Ward No "+ward_no+", and Reading Day "+reading_day+".",
            text: "",
            confirmButtonColor: "#2196F3",
        });
		return false;
	}
	else{
		$("#wardToMrEntity").attr("action", "./updateWardToMrEntity").submit();
	}
	
}


function reload(){
	window.location.href = "./wardToMr";
}


/* function loadSearchFilter1(param,tableData,temp)
{
    $('#'+param).dataTable().fnClearTable();
    $('#'+param).dataTable().fnDestroy();
    $('#'+temp).html(tableData);
    $('#'+param).dataTable();

}  */

</script>
<style>
.form-horizontal .form-group {
	margin-left: 0px !important;
	margin-right: 0px !important;
}

select {
	width: 100%;
	border: 1px solid #DDD;
	border-radius: 3px;
	height: 36px;
}

legend {
	text-transform: none;
	font-size: 16px;
	border-color: #F01818;
}

.datatable-header, .datatable-footer {
    padding: 0px 0px 0 0px;
}
.datatable-header {
    display: block;
}
.dataTables_filter {
    display: block;
    float: left;
    margin: 0 0 20px 20px;
    position: relative;
}
.dataTables_length > label {
    margin-bottom: 0;
    display: none;
}
/* .table > thead > tr > th, .table > tbody > tr > th, .table > tfoot > tr > th, .table > thead > tr > td, .table > tbody > tr > td, .table > tfoot > tr > td {
    padding: 5px 5px;
    line-height: 1.5384616;
   
} */
.dtr-inline.collapsed tbody tr td:first-child::before, .dtr-inline.collapsed tbody tr th:first-child::before, .dtr-column tbody tr td.control::before, .dtr-column tbody tr th.control::before {
    content: unset;
    font-family: 'icomoon';
    display: inline-block;
    font-size: 16px;
    width: 16px;
    line-height: 1;
    position: relative;
    top: -1px;
    vertical-align: middle;
}


.modal.fade .modal-dialog {
width: 1260px;
}



</style>