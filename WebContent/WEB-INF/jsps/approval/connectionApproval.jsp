<%@include file="/WEB-INF/decorators/taglibs.jsp"%>

				<div class="panel panel-flat">
					<div class="panel-body">
							<fieldset class="content-group" > 
							<legend class="text-bold">ReConnection/DisConnection Approval</legend>
							<div class="table-responsive">
								<table class="table datatable-basic table-bordered">
									<thead>
										<tr>
											<th>Connection No</th>
											<th>Name</th>
											<th>Mobile No</th>
											<th>Address</th>
											<th>Plot No</th>
											<th>Connection Type</th>
										</tr>
									</thead>
									
									<tbody>
										<tr>
											<td><a href="#" onclick="showApprovalRecord(1110111111);">1110111111</a></td>
											<td>Raj</td>
											<td>9444444444</td>
											<td>Bangalore</td>
											<td>2</td>
											<td>Domestic</td>
										</tr>
										<tr>
											<td><a href="#" onclick="showApprovalRecord(1110111112);">1110111112</a></td>
											<td>Kumar</td>
											<td>9444444445</td>
											<td>Bangalore</td>
											<td>2</td>
											<td>Domestic</td>
										</tr>
										
									</tbody>
									
									
								</table>
							</div>
						</fieldset>
						
			</div>
			</div>
	
	
<script>

$(document).ready(function(){   
	
	$('#masterApprovalScreen').show();
	$('#masterApproval').addClass('active');
	
	var activeMod="${activeModulesNew}";
	var module="Master Approval";
	var check=activeMod.indexOf(module) > -1;
	
	if(check){
	}else{
		window.location.href="./accessDenied";
	}
});
</script>