
<%@include file="/WEB-INF/decorators/taglibs.jsp"%>

<script>
$(document).ready(function(){   
	$('#cashCounterScreen').show();
	$('#cashCounter').addClass('active');
	
	var activeMod="${activeModulesNew}";
	var module="Cash Counter";
	var check=activeMod.indexOf(module) > -1;
	
	if(check){
	}else{
		window.location.href="./accessDenied";
	}
}); 

</script>
<style>
/* #trHeading {
    font-size: 120%;
} */
hr {
    display: block;
    height: 1px;
    border: 0;
    margin: 1em 0;
    padding: 0;
    background-color: red;
}
legend {
	text-transform: none;
	font-size: 16px;
	border-color: #F01818;
}
</style>
<div class="panel panel-flat">
						

						<div class="panel-body">
							<!-- <form class="form-horizontal" action="#">-->
							
							<form:form action="./updateChequeBounce" modelAttribute="paymentsEntity" commandName="paymentsEntity" method="POST" id="paymentsEntity" 
							role="form" class="form-horizontal form-validate-jquery" >
							<fieldset class="content-group" > 
								<legend class="text-bold">Cheque Bounce</legend>
										<div class="row" >
											<div class="col-md-4">
												<div class="form-group">
													<label>Connection No.</label>
					                                <form:input  path="connectionNo" type="text" class="form-control" placeholder="" ></form:input>
				                                </div>
											</div>
											<div class="col-md-4">
											<label style="margin-top: 36px;"><b>
									<font  style="font-family:Arial, Helvetica, sans-serif;color: black;" size="2px;"><a href="#"><u>View all check bounces</u></a></font>
									        </b></label>
											</div>
											</div>
											
											<div class="row">
											<div class="col-md-4">
												<div class="form-group">
													<label>Cheque No.</label>
					                                <form:input  path="cdno" type="text" class="form-control" placeholder="" ></form:input>
				                                </div>
											</div>
											<div class="col-md-4">
											<label>Cheque Date</label>
												<div class="input-group">
								<span class="input-group-addon " ><i class="icon-calendar"></i></span>
								<form:input  path="cddate" type="text"  class="form-control daterange-single " placeholder="dd/MM/yyyy"  id="cddate" name="cddate"></form:input>
							</div>
											</div>
											</div>
											
											
											<div class="row">
											<div class="col-md-4">
												<div class="form-group">
													<label>Receipt No.</label>
					                                <form:input  path="receiptNo"  type="text" class="form-control" placeholder="" ></form:input>
				                                </div>
											</div>
											<div class="col-md-4">
											<label>Amount</label>
												 <form:input  path="amount" type="text" class="form-control" placeholder="" ></form:input>
											</div>
											</div>
											
											<div class="row">
											<div class="col-md-4">
												<div class="form-group">
													<label>Bank Name</label>
					                                <form:input  path="bankname" type="text" class="form-control" placeholder="" ></form:input>
				                                </div>
											</div>
										<div class="col-md-4">
											
											<label>Receipt Date</label>
												 <div class="input-group">
										<span class="input-group-addon " ><i class="icon-calendar"></i></span>
										<form:input  path="rdate" type="text"  class="form-control daterange-single" placeholder="dd/MM/yyyy" id="rdate" name="rdate"></form:input>
							</div>
											</div>
											
											</div>
											
											<div class="row">
											<div class="col-md-4">
												<div class="form-group">
													<label>Penalty Amount</label>
					                                <form:input  path="amount" type="text" class="form-control" placeholder="" ></form:input>
				                                </div>
				                                <div class="form-group">
				                                </div>
				                                <div class="text-center" >
									<button type="button" class="btn bg-teal" style="margin-top: 37px;">Search
									<i class="glyphicon glyphicon-search"></i></button>
								</div>
											</div>
											<div class="col-md-4">
											<label>Remaks</label>
												 <form:textarea  path="remarks" class="form-control" placeholder="" ></form:textarea>
											<div class="text-center">
									<button type="button" class="btn bg-teal" >Clear
									<i class="glyphicon glyphicon-remove"></i></button>
								</div>
											</div>
											</div>
											
											</fieldset>
											</form:form>
									     </div>
									
								
								</div>
