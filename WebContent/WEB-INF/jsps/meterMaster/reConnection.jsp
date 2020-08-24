<%@include file="/WEB-INF/decorators/taglibs.jsp"%>

<script>
		$(document).ready(function(){   
			
			$('#disconnReconnScreens').show();
			$('#disconnReconnModule').addClass('active');
			
			var activeMod="${activeModulesNew}";
			var module="Reconnection/Disconnection";
			var check=activeMod.indexOf(module) > -1;
			
			if(check){
			}else{
				window.location.href="./accessDenied";
			}
		
			$('#reconndate_Nep').nepaliDatePicker({
				 dateFormat: "%D, %M %d, %y",
					npdMonth: true,
					npdYear: true
		  	});
		}); 
		
		function getListDetailsByConnNo(value)
		{
			connectionno=$("#connectionno").val();
			$.ajax({
				  type: "GET",
				  url: "./getListDetailsByConnNo/"+connectionno,
				  dataType: "json",
				  cache       : false,
				  async       : false,
				  success: function(response)
				  {
				  	 if(response.length > 0)
				  	 {
				  		if(value!='submit')
						  {
				  			swal({
					            title: "Connection No : "+connectionno+" is Valid Reconnection Allowed",
					            text: "",
					            confirmButtonColor: "#2196F3",
					        });
				  			checkRecStatus(value);
				  			
						  }
				  		
						  for(var i=0;i<response.length;i++)
						  {
							  data=response[i];
							  $('#listno').val(data.listno);
						  } 
						  if(value=='submit')
							  {
							  		
							  		 var v=checkRecStatus(value);
							  		if(v=='no')
							  			{
							  			  return false;
							  			} 
							     $("#reConnEntry").attr("action", "./reConnEntry").submit();
							     swal({
							            title: "Connection No : "+connectionno+" Reconnection Successfully",
							            text: "",
							            confirmButtonColor: "#2196F3",
							        });
							     
							  }
						  
				  	}
				  	 else
				  	{
				  		swal({
				            title: "Connection No : "+connectionno+" Does't Exist in this List",
				            text: "",
				            confirmButtonColor: "#2196F3",
				        });
				  		//return false;
				  	} 
			  	}
			});
			return false;
		}

		function checkRecStatus(value)
		{
			connectionno=$("#connectionno").val();
			var varl='yes';
			$.ajax({
					  type: "GET",
					  url: "./uniqueRecConnEntry/"+connectionno,
					  dataType: "json",
					  cache       : false,
					  async       : false,
					  success: function(response)
					  {		
						  	data=response[0];
						  	if(response.length > 0)
						  	 {
					  			
				  				swal({
						            title: "Connection No : "+connectionno+" is Already Reconnected",
						            text: "",
						            confirmButtonColor: "#2196F3",
						        });
				  				
				  				if(data.status < 1)
							  	 {
				  					swal({
							            title: "Connection No : "+connectionno+" is Waiting For DisConnection Entry",
							            text: "",
							            confirmButtonColor: "#2196F3",
							        });
							  	 }
				  				
				  				varl='no';
							  }
				  	   }
			});
			return varl;
		}
		
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
							<form:form action="" role="form" modelAttribute="reConnEntry" commandName="reConnEntry" method="POST" id="reConnEntry">
							
							<fieldset class="content-group"> 
								<legend class="text-bold" style="font-size: 18px;">Reconnection</legend>
										
										<div class="row">
											<div class="col-md-4">
												<div class="form-group">
													<label>Connection No&nbsp;<font color="red">*</font></label>
					                                <form:input path="connectionno" id="connectionno" name="connectionno" type="text" class="form-control" placeholder="Connection No " onchange="return getListDetailsByConnNo(this.id)" />
				                                </div>
											</div>
											
											<div class="col-md-4">
												<div class="form-group">
													<label>List No&nbsp;<font color="red">*</font></label>
					                               <form:input path="listno" id="listno" name="listno" type="text" class="form-control" placeholder="List No" readonly="true" />
				                                </div>
											</div>
											
											<div class="col-md-4">
												<div class="form-group">
													<label>Reconnection Date in Nepali&nbsp;<font color="red">*</font></label>
					                                <form:input path="rec_date_nep" type="text" id="reconndate_Nep" name="reconndate_Nep" class="form-control nepali-calendar" placeholder="Reconnection Date in Nepali" />
					                               </div>
											</div>
											
									     </div>
									     
								     	<div class="row">
											<div class="col-md-4">
												<div class="form-group">
													<label>Reconnection Date in English</label>
					                                <div class="input-group">
														<span class="input-group-addon"><i class="icon-calendar22"></i></span>
															<form:input path="rec_date_eng" id="reconndate_Eng" name="reconndate_Eng" type="text" class="form-control daterange-single"/>
													</div>
				                                </div>
											</div>
											
											<div class="col-md-8">
												<div class="form-group">
													<label>Remarks</label> 
													<form:textarea path="remarks" id="remarks" name="remarks" placeholder="Enter your Remarks here" class="form-control" cols="2" rows="1"></form:textarea>
												</div>
											</div>
									 </div>
								
								</fieldset>
								</form:form>
								<div class="text-center">
									<button type="submit" class="btn bg-blue"  data-style="expand-right" data-spinner-size="20" onclick="return getListDetailsByConnNo('submit');"><span class="ladda-label">Submit</span></button>
								</div>	
				</div>			
			</div>
	
					<!-- <div class="row">
						
						<div class="panel panel-flat">
						<div class="panel-heading">
						    <h4 class="panel-title">
						   <center>
						    <span class="label bg-info" STYLE="font-size: 11pt">Reconnection</span></h4>
						    </center>
						    <div class="heading-elements">
								<ul class="icons-list">
			                		<li><a data-action="collapse"></a></li>
			                		<li><a data-action="reload"></a></li>
			                	</ul>
	                		</div>
						</div>

		                <div class="panel-body">
		                 <div class="table-responsive">
							<table class="table">
						
								<tr class="danger"><td>Connection No.&nbsp;<font color="red">*</font></td><td><input type="text" class="form-control" placeholder="Enter Connection No."></td><td>List No</td><td><input type="text" class="form-control" placeholder="Enter List No."></td></tr>
								<tr class="active"><td>Reconnection Date in Nepali&nbsp;<font color="red">*</font></td><td><input type="text" class="form-control daterange-single" ></td><td>Remarks</td><td><textarea rows="1" cols="2" class="form-control" placeholder="Enter Remarks"></textarea></td></tr>
								<tr class="success"><td>Reconnection Date in English</td><td><input type="text" class="form-control daterange-single" ></td><td></td><td></td></tr>
								
			                    </tbody>
			                </table>
						</div> 
						
		                <div class="text-left">
		                
		                		<br><center><button type="reconnect" class="btn bg-teal-400">Reconnect</i></button>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<button type="clear" class="btn bg-teal-400">Clear</i></button></center>	
		                
		                </div>
		              </div>

						
					</div> -->
	