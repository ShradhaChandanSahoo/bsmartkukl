<%@include file="/WEB-INF/decorators/taglibs.jsp"%>

<script>
		$(document).ready(function(){   
			$('#enquiryScreens').show();
			$('#enquiryModule').addClass('active');
		}); 
</script>

<style>

.form-horizontal .form-group{
	margin-left: 0px !important;
	margin-right: 0px !important;
	
}

select{
width: 100%; border: 1px solid #DDD; border-radius: 3px; height: 36px;
}

legend {
	text-transform: none;
	font-size: 16px;
	border-color: skyblue;
	margin-bottom: 8px;
    padding-bottom: 1px;
    padding-top: 1px;
}

.datatable-header, .datatable-footer {
    padding: 8px 0px 0 0px;
}
</style>

<script type="text/javascript">

function viewConnDetails()
{
	$('#connDetails').show();
}

</script>
	
				<div class="panel panel-flat">
						<div class="panel-body">
							<!-- <form class="form-horizontal" action="#">-->
							<fieldset class="content-group"> 
								<legend class="text-bold" style="text-align: center;font-size: 18px;">Customer Enquiry</legend><br>		
								
										<div class="row">
											<div class="col-md-4">
												<div class="form-group">
													<label>Connection No</label>
					                               <input id="connNo" name="connNo" type="text" class="form-control" placeholder="Connection No" >
				                                </div>
											</div>
											
											<div class="col-md-4">
												<div class="form-group">
													<label>Name&nbsp;<font color="red">*</font></label>
					                                <input id="consumerName" name="consumerName" type="text" class="form-control" placeholder="Name" >
				                                </div>
											</div>
											
											<div class="col-md-4">
												<div class="form-group">
													<label>Area No&nbsp;<font color="red">*</font></label>
					                                <input id="areaNo" name="areaNo" type="text" class="form-control" placeholder="Area No" >
				                                </div>
											</div>
											
									     </div>
									     
									     <div class="row">
											<div class="col-md-4">
												<div class="form-group">
													<label>Address</label>
					                                <input id="consumerAddress" name="consumerAddress" type="text" class="form-control" placeholder="Address" >
					                               </div>
											</div>
											
											<div class="col-md-4">
												<div class="form-group">
													<label>Ward No&nbsp;<font color="red">*</font></label>
					                                <input id="wardNo" name="wardNo" type="text" class="form-control" placeholder="Ward No" >
					                               </div>
											</div>
											
											<div class="col-md-4">
												<div class="form-group">
													<label>&nbsp;</label>
					                                <br><div class="col-md-4">
					                                <button type="button" class="btn bg-blue btn-ladda btn-ladda-progress"  data-spinner-size="20" onclick="viewConnDetails();"><span class="ladda-label" style="font-size: 13px">View Connections</span></button>
					                               </div>
					                               </div>
											</div>
								     	</div></fieldset>
								   <br> 
						   <fieldset class="content-group"> 
							<div id="connDetails" hidden="true">
							 <legend class="text-bold">Connection Details</legend>
								<div class="row">
									<div class="table-responsive">
										<div class="col-md-16">
										<div class="panel-body" style="margin-top: -3px; padding: 6px;" >
										
											<table class="table datatable-basic table-bordered" id="tableForm">
											<thead>
					
												<tr class="bg-blue" width="100%">
													<th width="10%">Connection No</th>
													<th width="30%">Name</th>
													<th width="30%">Address in English</th>
													<th width="20%">Area Number</th>
													<th width="10%">Ward Number</th>
												</tr>
					
											</thead>
											<tbody>
												<tr>
													<td><a href="#">11423</a></td>
													<td>Mr.Dwrakadas</td>
													<td>5th block</td>
													<td>40235</td>
													<td>02</td>
												</tr>
					
												<tr>
													<td><a href="#">11236</a></td>
													<td>Mr.Chandan Lal</td>
													<td>7th block</td>
													<td>39152</td>
													<td>09</td>
												</tr>
					
												<tr>
													<td><a href="#">12535</a></td>
													<td>Mr.Keshavrao</td>
													<td>8th block</td>
													<td>25312</td>
													<td>31</td>
												</tr>
					
												
												<tr>
													<td><a href="#">15129</a></td>
													<td>Mr.Nandan</td>
													<td>9th block</td>
													<td>61230</td>
													<td>07</td>
												</tr>
					
												<tr>
													<td><a href="#">13522</a></td>
													<td>Mr.Subhash Chandra</td>
													<td>2th block</td>
													<td>31562</td>
													<td>01</td>
												</tr>
												
												<tr>
													<td><a href="#">11423</a></td>
													<td>Mr.Dwrakadas</td>
													<td>5th block</td>
													<td>40235</td>
													<td>02</td>
												</tr>
					
												<tr>
													<td><a href="#">11236</a></td>
													<td>Mr.Chandan Lal</td>
													<td>7th block</td>
													<td>39152</td>
													<td>09</td>
												</tr>
					
												<tr>
													<td><a href="#">12535</a></td>
													<td>Mr.Keshavrao</td>
													<td>8th block</td>
													<td>25312</td>
													<td>31</td>
												</tr>
					
												
												<tr>
													<td><a href="#">15129</a></td>
													<td>Mr.Nandan</td>
													<td>9th block</td>
													<td>61230</td>
													<td>07</td>
												</tr>
					
												<tr>
													<td><a href="#">13522</a></td>
													<td>Mr.Subhash Chandra</td>
													<td>2th block</td>
													<td>31562</td>
													<td>01</td>
												</tr>
												
												<tr>
													<td><a href="#">11423</a></td>
													<td>Mr.Dwrakadas</td>
													<td>5th block</td>
													<td>40235</td>
													<td>02</td>
												</tr>
					
												<tr>
													<td><a href="#">11236</a></td>
													<td>Mr.Chandan Lal</td>
													<td>7th block</td>
													<td>39152</td>
													<td>09</td>
												</tr>
					
												<tr>
													<td><a href="#">12535</a></td>
													<td>Mr.Keshavrao</td>
													<td>8th block</td>
													<td>25312</td>
													<td>31</td>
												</tr>
					
												
												<tr>
													<td><a href="#">15129</a></td>
													<td>Mr.Nandan</td>
													<td>9th block</td>
													<td>61230</td>
													<td>07</td>
												</tr>
					
												<tr>
													<td><a href="#">13522</a></td>
													<td>Mr.Subhash Chandra</td>
													<td>2th block</td>
													<td>31562</td>
													<td>01</td>
												</tr>
												
											</tbody>
										</table><br>
					
									</div>
					
								</div>
									</div>
					
								</div>
								</div>     	
								</fieldset>	
				</div>			
			</div>
	