<%@include file="/WEB-INF/decorators/taglibs.jsp"%>
<script type="text/javascript" src="./resources/js/widgets.min.js"></script>
<script type="text/javascript" src="./resources/js/jqueryui_components.js"></script>

	
	<div class="panel panel-flat">	
		<div class="panel-body">
			<fieldset class="content-group" > 
				<legend class="text-bold">Existing Slab Details</legend>
						<div class="col-md-4" id="addId" >
							<div class="form-group" >
				            </div>
						</div>
						<div class="panel-body" style="margin-top: -38px;">
							<table class="table datatable-basic table-bordered">
								<thead>
									<tr style="background-color:#dbd7d7;">
										<th>Sl. No.</th>
										<th>Slab No.</th>
										<th>Slab Type</th>
										<th>No. of</th>
										<th>Days/Months</th>
										<th>Rebate</th>
										<th>Adv. Rebate</th>
										<th>Penalty</th>
										<th>Description</th>
										<th>Action</th>
									</tr>
								</thead>
								<tbody>
									<%-- <c:forEach var="data" items="${userList}">
										<tr>
											<td>${data.user_type}</td>
											<td class="text-center"><a href="#" onclick="editUserType('${data.id}','${data.user_type}')">[Edit]</a></td>
										</tr>
									</c:forEach> --%>
								</tbody>
							</table>
						</div>
			</fieldset>
		</div>
	</div>	

<!-- <div id="jui-dialog-form-vertical" title="Vertical form">
					<form action="#">
						<div class="form-group">
							<div class="row">
								<div class="col-sm-6">
									<label>First name</label>
									<input type="text" placeholder="Eugene" class="form-control">
								</div>

								<div class="col-sm-6">
									<label>Last name</label>
									<input type="text" placeholder="Kopyov" class="form-control">
								</div>
							</div>
						</div>

						<div class="form-group">
							<div class="row">
								<div class="col-sm-6">
									<label>Address line 1</label>
									<input type="text" placeholder="Ring street 12" class="form-control">
								</div>

								<div class="col-sm-6">
									<label>Address line 2</label>
									<input type="text" placeholder="building D, flat #67" class="form-control">
								</div>
							</div>
						</div>

						<div class="form-group">
							<div class="row">
								<div class="col-sm-4">
									<label>City</label>
									<input type="text" placeholder="Munich" class="form-control">
								</div>

								<div class="col-sm-4">
									<label>State/Province</label>
									<input type="text" placeholder="Bayern" class="form-control">
								</div>

								<div class="col-sm-4">
									<label>ZIP code</label>
									<input type="text" placeholder="1031" class="form-control">
								</div>
							</div>
						</div>

						<div class="form-group">
							<div class="row">
								<div class="col-sm-6">
									<label>Email</label>
									<input type="text" placeholder="eugene@kopyov.com" class="form-control">
									<span class="help-block">name@domain.com</span>
								</div>

								<div class="col-sm-6">
									<label>Phone #</label>
									<input type="text" placeholder="+99-99-9999-9999" data-mask="+99-99-9999-9999" class="form-control">
									<span class="help-block">+99-99-9999-9999</span>
								</div>
							</div>
						</div>
					</form>
				</div> -->
				
				<!-- Basic modal -->
				<div id="modal_default" class="modal fade">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal">&times;</button>
								<h5 class="modal-title">Payment History</h5>
							</div>

							<div class="modal-body">
								
								<table class="table datatable-basic table-bordered" id="popupPayment">
									<thead>
										<tr>
											<th>Sl No</th>
											<th>Payment Amount</th>
											<th>Reciept No</th>
											<th>Paid Date in English</th>
											<th>paid Date in Nepali</th>
											<th>Remarks</th>
										</tr>
									</thead>
									<tbody id="viewPayHistotytbody">
										
									</tbody>
								</table>
								
								
							</div>

							<div class="modal-footer">
								
							</div>
						</div>
					</div>
				</div>
				<!-- /basic modal -->	

					
<script type="text/javascript">
$(document).ready(function(){   
	$('#umconfig').show();
	$('#configModule').addClass('active');
});
</script>
<style>
legend {
	text-transform: none;
	font-size: 16px;
	border-color: #F01818;
}
</style>

