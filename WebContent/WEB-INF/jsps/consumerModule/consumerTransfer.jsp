<%@include file="/WEB-INF/decorators/taglibs.jsp"%>	

<script type="text/javascript">

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
	
});

function finalSubmit() {
	
	  swal({
          title: "Employee Added Successfully",
          confirmButtonColor: "#2196F3"
      });
}
</script>


<div class="content">


	<form action="#">
		<div class="panel panel-flat">
			<div class="panel-heading">
				<h5 class="panel-title">Customer Transfer</h5>
				<div class="heading-elements">
					<ul class="icons-list">
						<li><a data-action="collapse"></a></li>
						<li><a data-action="reload"></a></li>
						<li><a data-action="close"></a></li>
					</ul>
				</div>
			</div>

			<div class="panel-body">
				<div class="row">
					<div class="col-md-6">
						<fieldset>
							<legend class="text-semibold">
								<i class="icon-reading position-left"></i> From
							</legend>

							<div class="form-group">
								<label>From Branch:</label> <select
									data-placeholder="From branch" class="select">
									<option></option>
									<option value="USA">Branch 1</option>
									<option value="United Kingdom">Branch 2</option>
									<option value="...">Branch 3</option>
								</select>
							</div>

							<h5 align="center" style="color: red;">Customer Details</h5>

							<div class="form-group">
								<label>Connection No:</label> <input type="text"
									class="form-control" placeholder="Connection No">
							</div>

							<div class="form-group">
								<label>Customer Id:</label> <input type="text"
									class="form-control" placeholder="Customer Id">
							</div>

							<div class="form-group">
								<label>Name</label> <input type="text" class="form-control"
									placeholder="Name">
							</div>

							<div class="form-group">
								<label>Address:</label>
								<textarea rows="3" cols="3" class="form-control"
									placeholder="Enter your address here"></textarea>
							</div>

							<div class="row">
								<div class="col-md-3">
									<div class="form-group">
										<label>Area No:</label> <input type="text"
											placeholder="Area No" class="form-control">
									</div>
								</div>

								<div class="col-md-3">
									<div class="form-group">
										<label>Connection Status:</label> <input type="text"
											placeholder="Connection Status" class="form-control">
									</div>
								</div>

								<div class="col-md-6">
									<div class="form-group">
										<label>Connection Category:</label> <input type="text"
											placeholder="Connection Category" class="form-control">
									</div>
								</div>
							</div>

							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label>Reading Day:</label> <input type="text"
											placeholder="Reading Day" class="form-control">
									</div>
								</div>

								<div class="col-md-6">
									<div class="form-group">
										<label>Ward No:</label> <input type="text"
											placeholder="Ward No" class="form-control">
									</div>
								</div>
							</div>
							<br>
							<button class="btn btn-primary" type="button">Transfer</button>

						</fieldset>
					</div>


					<div class="col-md-6">
						<fieldset>
							<legend class="text-semibold">
								<i class="icon-reading position-left"></i> To
							</legend>

							<div class="form-group">
								<label>To Branch:</label> <select data-placeholder="To branch"
									class="select">
									<option></option>
									<option value="USA">Branch 1</option>
									<option value="United Kingdom">Branch 2</option>
									<option value="...">Branch 3</option>
								</select>
							</div>

							<h5 align="center" style="color: red;">Customer Details</h5>

							<div class="form-group">
								<label>Connection No:</label> <input type="text"
									class="form-control" placeholder="Connection No">
							</div>

							<div class="form-group">
								<label>Customer Id:</label> <input type="text"
									class="form-control" placeholder="Customer Id">
							</div>

							<div class="form-group">
								<label>Name</label> <input type="text" class="form-control"
									placeholder="Name">
							</div>

							<div class="form-group">
								<label>Address:</label>
								<textarea rows="3" cols="3" class="form-control"
									placeholder="Enter your address here"></textarea>
							</div>

							<div class="row">
								<div class="col-md-3">
									<div class="form-group">
										<label>Area No:</label> <input type="text"
											placeholder="Area No" class="form-control">
									</div>
								</div>

								<div class="col-md-3">
									<div class="form-group">
										<label>Connection Status:</label> <input type="text"
											placeholder="Connection Status" class="form-control">
									</div>
								</div>

								<div class="col-md-6">
									<div class="form-group">
										<label>Connection Category:</label> <input type="text"
											placeholder="Connection Category" class="form-control">
									</div>
								</div>
							</div>

							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label>Reading Day:</label> <input type="text"
											placeholder="Reading Day" class="form-control">
									</div>
								</div>

								<div class="col-md-6">
									<div class="form-group">
										<label>Ward No:</label> <input type="text"
											placeholder="Ward No" class="form-control">
									</div>
								</div>
							</div>
							<br>
							<button class="btn btn-primary" type="button">Clear</button>
							<button class="btn btn-primary" type="button"
								style="float: right;">Update</button>

						</fieldset>
					</div>
				</div>
			</div>
		</div>
	</form>

</div>


<style>
legend {
	text-transform: none;
	font-size: 16px;
	border-color: #F01818;
}
</style>