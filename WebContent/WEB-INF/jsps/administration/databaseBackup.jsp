<%@include file="/WEB-INF/decorators/taglibs.jsp"%>
<script>
		$(document).ready(function(){   
			$('#administrationScreens').show();
			$('#administrationModule').addClass('active');
		
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
}

.datatable-header, .datatable-footer {
    padding: 0px 0px 0 0px;
}
</style>

					<div class="panel panel-flat">
						<div class="panel-body">
							<!-- <form class="form-horizontal" action="#">-->
							<fieldset class="content-group"> 
								<legend class="text-bold" style="margin: auto;text-align: center;font-size: 18px;">Database Backup</legend>
										<br>
										<div class="row">
											<div class="col-md-4">
												<div class="form-group">
													<label>Enter User Name&nbsp;<font color="red">*</font></label>
					                                <input id="usrName" name="usrName" type="text" class="form-control" placeholder="User Name" >
				                                </div>
											</div>
											<div class="col-md-4">
												<div class="form-group" style="padding-top: 27px;">
													<label>&nbsp;</label>
					                                <button type="button" class="btn bg-blue btn-ladda btn-ladda-progress" data-style="expand-right" data-spinner-size="20">
													<span class="ladda-label">Start Database Backup</span></button>
				                                </div>
											</div>

										</div>
									
									     
						     		
								</fieldset>	
						</div>			
			</div>	
