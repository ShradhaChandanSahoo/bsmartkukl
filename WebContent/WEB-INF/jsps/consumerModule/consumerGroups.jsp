<%@include file="/WEB-INF/decorators/taglibs.jsp"%>


<script type="text/javascript">

$(document).ready(function(){   
	$('#umConsumer').show();
	$('#consumerModule').addClass('active');
});

function finalSubmit(param) {
	if(param =='0')
	{
	  swal({
          title: "Customer Group Added Successfully",
          confirmButtonColor: "#2196F3"
      });
	}
	else{
		  swal({
	          title: "Customer Group Updated Successfully",
	          confirmButtonColor: "#2196F3"
	      });
			
	}
}
</script>


<div class="panel panel-flat">
					

						<div class="panel-body">
						<form class="form-horizontal form-validate-jquery" action="#">
							<fieldset class="content-group"> 
									<legend class="text-bold">Customer Group Particulars</legend>
										<div class="row">
											<div class="col-md-4">
												<div class="form-group">
													<label>Group ID</label>
					                                <input id ="gId" type="text" class="form-control" required="required" placeholder="Group ID..." >
				                                </div>
											</div>

											<div class="col-md-4">
												<div class="form-group">
													<label>Connection No.</label>
					                                <input id="conNum" type="text" class="form-control" placeholder="Connection No..." >
				                                </div>
											</div>
											<div class="col-md-4">
												<div class="form-group">
													<label>Ward No.</label>
					                                <input id="wNum" type="text" class="form-control" placeholder="Ward No..." >
				                                </div>
											</div>
											
											
											
										</div>
										
										<div class="row">
											<div class="col-md-4">
												<div class="form-group">
													<label>Name</label>
					                                <input id="namee" type="text" class="form-control" placeholder="Name..." >
				                                </div>
											</div>
											
											<div class="col-md-4">
												<div class="form-group">
													<label>Address in English</label>
					                                <textarea id="addrEng" class="form-control" placeholder="Address in English..." ></textarea>
				                                </div>
											</div>

											<div class="col-md-4">
												<div class="form-group">
													<label>Address in Neplai</label>
					                                <textarea id="addrNepali" class="form-control" placeholder="Address in Neplai..." ></textarea>
				                                </div>
											</div>
										</div>
								</fieldset>
						
								</form>	
								<div class="text-center">
									<button type="submit"   class="btn btn-primary" onclick="finalSubmit(0)" id="addOption" style="display: block;"> Add Customer Group <i class="icon-arrow-right14 position-right"></i></button>
									<button type="submit" class="btn btn-primary" onclick="finalSubmit(1)" id="updateOption" style="display: none;">Update Customer Group <i class="icon-arrow-right14 position-right"></i></button>
								</div>
								
								<br/><br/>
								
								<div id ="gridId1" >
								<fieldset class="content-group" > 
									<legend class="text-bold">Customer Groups</legend>
										<div class="col-md-6">
					<div class="panel-body" style="margin-top: -38px;">
						<table class="table datatable-basic table-bordered">
							<thead>
								<tr>
									<th>Group ID</th>
									<th>No. of Connections</th>
									<th hidden="true">Job Title</th>
									<th hidden="true">DOB</th>
									<th hidden="true">Status</th>
									<th hidden="true"></th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td id="gridOneId0"><a>0</a></td>
									<td id="gridOneConn0">2</td>
									<td hidden="true"></td>
									<td hidden="true"></td>
									<td hidden="true"></td>
									<td hidden="true"></td>
								</tr>
								<tr>
									<td id="gridOneId1"><a href="#" onclick="return groupDataByGroupId()">1</a></td>
									<td id="gridOneConn1">2</td>
									<td hidden="true"></td>
									<td hidden="true"></td>
									<td hidden="true"></td>
									<td hidden="true"></td>
								</tr>
								<tr>
									<td id="gridOneId2"><a href="#">2</a></td>
									<td id="gridOneConn2">5</td>
									<td hidden="true"></td>
									<td hidden="true"></td>
									<td hidden="true"></td>
									<td hidden="true"></td>
								</tr>
							</tbody>
						</table>
						</div>
					</div>
								</fieldset>
					</div>			
								
				<!-- 3rd grid view -->
				<div id ="gridId2" hidden="true">
				<fieldset class="content-group" > 
									<legend class="text-bold">Customer Groups Details: <span style="background-color: #EFC4BA">&nbsp;Group ID:1&nbsp;</span></legend>
										<!-- <div class="panel panel-flat"> -->
										<div class="col-md-12">
						<!-- <div class="panel-heading"> -->
							<!-- <h5 class="panel-title">Bordered table</h5> -->
							<!-- <div class="heading-elements"> -->
								<!-- <ul class="icons-list">
			                		<li><a data-action="collapse"></a></li>
			                		<li><a data-action="reload"></a></li>
			                		<li><a data-action="close"></a></li>
			                	</ul> -->
		                	<!-- </div> -->
						<!-- </div> -->
					<div  class="panel-body" style="margin-top: -38px;">
						<table class="table datatable-basic table-bordered">
							<thead>
								<tr>
									<th>Connection No.</th>
									<th hidden="true">Group ID</th>
									<th>Name</th>
									<th>Address in English</th>
									<th>Address in Nepali</th>
									<th>Ward No.</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td id="con1" ><a href="#" onclick="return getDetailsByConnNum()">00124578</a></td>
									<td id="gid1" hidden="true">1</td>
									<td id="cust1">Customer Name1</td>
									<td id="addr011">Address 01</td>
									<td id="addr021">Address 02</td>
									<td id="wNum1">001</td>
								</tr>
								<tr>
									<td>00124579</td>
									<td hidden="true">1</td>
									<td>Customer Name2</td>
									<td>Address 11</td>
									<td>Address 12</td>
									<td>001</td>
								</tr>
								<tr>
									<td>00124580</td>
									<td hidden="true">1</td>
									<td>Customer Name3</td>
									<td>Address 21</td>
									<td>Address 22</td>
									<td>001</td>
								</tr>
							</tbody>
						</table>
						</div>
					</div>
								</fieldset>
								</div>
								
							</div>
						</div>
				
					
					
<script>
$(document).ready(function(){   

	$('#openUl').show();
	$('#secUl').show();
	$('#genericLibrary').addClass('active');
	$('#dataTables1').addClass('active');
});

function groupDataByGroupId()
{
	$("#gId").val($("#gridOneId1").text());
	$("#gridId1").hide();
	$("#gridId2").show(); 
	document.getElementById('updateOption').style.display='block'; 
	document.getElementById('addOption').style.display = 'none';  
}

function getDetailsByConnNum()
{
	$("#gId").val($("#gid1").text());
	$("#conNum").val($("#gid1").text());
	$("#wNum").val($("#wNum1").text());
	$("#namee").val($("#wNum1").text());
	$("#addrEng").val($("#addr021").text());
	$("#addrNepali").val($("#addr021").text());
	document.getElementById('updateOption').style.display='block'; 
	document.getElementById('addOption').style.display = 'none';  
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
	border-color: #F01818;
}
</style>