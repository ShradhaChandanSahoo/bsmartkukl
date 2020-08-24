<%@include file="/WEB-INF/decorators/taglibs.jsp"%>	

<script type="text/javascript">

$(document).ready(function(){   
	$('#umconfig').show();
	$('#configModule').addClass('active');
	
	
	$('#tariffdate_Nep').nepaliDatePicker({
		 dateFormat: "%D, %M %d, %y",
			npdMonth: true,
			npdYear: true
	});
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
				<h3 class="panel-title" align="center">Tariff Management</h3>
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
								 Existing Tariff
							</legend>
						
						 <div class="row">
						   <div class="col-md-6">
							<div class="form-group">
								<label>Category</label> <select
									data-placeholder="Select" class="select">
									<option></option>
									<option value="Domestic">Domestic</option>
									<option value="Non Domestic">Non Domestic</option>
								</select>
							</div>
							</div>

							
						   <div class="col-md-6">
							<div class="form-group">
								<label>Pipe Diameter</label> <select
									data-placeholder="Select" class="select">
									<option></option>
									<option value="USA">0.5</option>
									<option value="...">1</option>
								</select>
							</div>
							</div>
						</div>

							<div class="form-group">
								<label>Minimum Charges</label> <input type="text"
									class="form-control" placeholder="Minimum Charge">
							</div>
							
							<div class="row">
								<div class="col-md-6">
								<div class="form-group">
									<label>First Slab</label> <input type="text" class="form-control"
										placeholder="First Slab">
								</div>
								</div>
								
								<div class="col-md-6">
								<div class="form-group">
									<label>Slab Rate</label> <input type="text" class="form-control"
										placeholder="Slab Rate">
								</div>
								</div>
							</div>
							
							<div class="row">
								<div class="col-md-6">
								<div class="form-group">
									<label>Second Slab</label> <input type="text" class="form-control"
										placeholder="Second Slab">
								</div>
								</div>
								
								<div class="col-md-6">
								<div class="form-group">
									<label>Slab Rate</label> <input type="text" class="form-control"
										placeholder="Slab Rate">
								</div>
								</div>
							</div>
							
							
							<div class="row">
								<div class="col-md-6">
								<div class="form-group">
									<label>Third Slab</label> <input type="text" class="form-control"
										placeholder="Third Slab">
								</div>
								</div>
								
								<div class="col-md-6">
								<div class="form-group">
									<label>Slab Rate</label> <input type="text" class="form-control"
										placeholder="Slab Rate">
								</div>
								</div>
							</div>
							
							<div class="row">
								<div class="col-md-6">
								<div class="form-group">
									<label>Fourth Slab</label> <input type="text" class="form-control"
										placeholder="Name">
								</div>
								</div>
								
								<div class="col-md-6">
								<div class="form-group">
									<label>Slab rate</label> <input type="text" class="form-control"
										placeholder="Name">
								</div>
								</div>
							</div>

							

							
								
							<div class="form-group">
								<label>Monthly Charges for Unmetered:</label> <input type="text"
									placeholder="Monthly Charges for Unmetered" class="form-control">
							</div>
								
								
							<div class="row">
								<div class="col-md-6">
								<div class="form-group">
									<label>Tariff Effecting Date in Nepali</label> <input type="text"  id="tariffdate_Nep" name="readingdate_Nep" class="form-control nepali-calendar"
										placeholder="Tariff Effecting Date in Nepali">
								</div>
								</div>
								
								<div class="col-md-6">
								<div class="form-group">
									<label>Tariff Effecting Date in English</label> <input type="text" class="form-control daterange-single"
										placeholder="Tariff Effecting Date in English">
								</div>
								</div>
							</div>

							
							<div class="form-group">
								<label>Tariff Description</label>
								<textarea rows="3" cols="3" class="form-control"
									placeholder="Tariff Description"></textarea>
							</div>
							
							<div class="form-group">
								<label>Pipe Diameter Description</label>
								<textarea rows="3" cols="3" class="form-control"
									placeholder="Pipe Diameter Description"></textarea>
							</div>

							

							
							<br>
							<button class="btn btn-primary" type="button">Transfer</button>

						</fieldset>
					</div>


					<div class="col-md-6">
						<fieldset>
							<legend class="text-semibold">
								 New Tariff
							</legend>

							<div class="row">
						   <div class="col-md-6">
							<div class="form-group">
								<label>Category</label> <select
									data-placeholder="Select" class="select">
									<option></option>
									<option value="Domestic">Domestic</option>
									<option value="Non Domestic">Non Domestic</option>
								</select>
							</div>
							</div>

							
						   <div class="col-md-6">
							<div class="form-group">
								<label>Pipe Diameter</label> <select
									data-placeholder="Select" class="select">
									<option></option>
									<option value="USA">0.5</option>
									<option value="...">1</option>
								</select>
							</div>
							</div>
						</div>

							<div class="form-group">
								<label>Minimum Charges</label> <input type="text"
									class="form-control" placeholder="Minimum Charge">
							</div>
							
							<div class="row">
								<div class="col-md-6">
								<div class="form-group">
									<label>First Slab</label> <input type="text" class="form-control"
										placeholder="First Slab">
								</div>
								</div>
								
								<div class="col-md-6">
								<div class="form-group">
									<label>Slab Rate</label> <input type="text" class="form-control"
										placeholder="Slab Rate">
								</div>
								</div>
							</div>
							
							<div class="row">
								<div class="col-md-6">
								<div class="form-group">
									<label>Second Slab</label> <input type="text" class="form-control"
										placeholder="Second Slab">
								</div>
								</div>
								
								<div class="col-md-6">
								<div class="form-group">
									<label>Slab Rate</label> <input type="text" class="form-control"
										placeholder="Slab Rate">
								</div>
								</div>
							</div>
							
							
							<div class="row">
								<div class="col-md-6">
								<div class="form-group">
									<label>Third Slab</label> <input type="text" class="form-control"
										placeholder="Third Slab">
								</div>
								</div>
								
								<div class="col-md-6">
								<div class="form-group">
									<label>Slab Rate</label> <input type="text" class="form-control"
										placeholder="Slab Rate">
								</div>
								</div>
							</div>
							
							<div class="row">
								<div class="col-md-6">
								<div class="form-group">
									<label>Fourth Slab</label> <input type="text" class="form-control"
										placeholder="Name">
								</div>
								</div>
								
								<div class="col-md-6">
								<div class="form-group">
									<label>Slab rate</label> <input type="text" class="form-control"
										placeholder="Name">
								</div>
								</div>
							</div>

							

							
								
							<div class="form-group">
								<label>Monthly Charges for Unmetered:</label> <input type="text"
									placeholder="Monthly Charges for Unmetered" class="form-control">
							</div>
								
								
							<div class="row">
								<div class="col-md-6">
								<div class="form-group">
									<label>Tariff Effecting Date in Nepali</label> <input type="text"  id="tariffdate_Nep" name="readingdate_Nep" class="form-control nepali-calendar"
										placeholder="Tariff Effecting Date in Nepali">
								</div>
								</div>
								
								<div class="col-md-6">
								<div class="form-group">
									<label>Tariff Effecting Date in English</label> <input type="text" class="form-control daterange-single"
										placeholder="Tariff Effecting Date in English">
								</div>
								</div>
							</div>

							
							<div class="form-group">
								<label>Tariff Description</label>
								<textarea rows="3" cols="3" class="form-control"
									placeholder="Tariff Description"></textarea>
							</div>
							
							<div class="form-group">
								<label>Pipe Diameter Description</label>
								<textarea rows="3" cols="3" class="form-control"
									placeholder="Pipe Diameter Description"></textarea>
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