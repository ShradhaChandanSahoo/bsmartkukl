<%@include file="/WEB-INF/decorators/taglibs.jsp"%>
<script type="text/javascript">

window.onload = function() {
	
	$('#usermanagementId').addClass('active');
	$('#umchild').show();
	
	var activeMod="${activeModulesNew}";
	var module="User Management";
	
	var check=activeMod.indexOf(module) > -1;
	
	if(check){
	}else{
		window.location.href="./accessDenied";
	}
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
					<div class="panel-body" style="overflow: scroll;">
							
							<form action="#" method="POST">
							
							    <input type="text" id="connectId" name="connectId" hidden="true">
								<input type="text" id="conStatus" name="conStatus" hidden="true">
							
							<fieldset class="content-group" > 
								<legend class="text-bold">Reset Users Password
								 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								
								</legend>
							
							<div class="table-responsive" >
								<table class="table datatable-basic table-bordered" >
									<thead>
										<tr>
											
											<th >User Name</th>
											<th style="min-width: 300px;">User Login Name</th> <!-- style="min-width: 180px;" -->
											<th >Mobile Number</th>
											<th >Status</th>
											<th style="min-width: 100px;"></th>
											
											
										</tr>
									</thead>
									<tbody>
										
									<c:forEach var="app" items="${userMasterData}">
											<tr>
												
												
												<td>${app.user_name}</td>
												<td>${app.user_login_name}</td>
												<td>${app.mobileno}</td>
												<td><a>${app.status}</a></td>
												<td><button type="button" class="btn bg-teal btn-sm" onclick="newCalculate(${app.id});"  id="onshow_callback2" style="float: right;">Reset Password</button></td>
												
											</tr>
									</c:forEach>
									</tbody>
								</table>
							</div>
						</fieldset>
					
						
						
						</form>
						</div>
						
						</div>
			
				<script>
				

				function newCalculate(id){
					
					swal({
						  title: 'Are you sure to Reset Password?',
						  //text: "You won't be able to revert this!",
						  type: 'warning',
						  showCancelButton: true,
						  confirmButtonColor: "rgb(35, 164, 123)",
						  cancelButtonColor: "#fb8585",
						  confirmButtonText: "Yes, Reset it!",
						  cancelButtonText: "Cancel",
						  closeOnConfirm: false,
						  closeOnCancel: true
						},
						function(isConfirm) {
						  if (isConfirm) {
							  $.ajax({
									type : "GET",
									url : "./resetUsersPwdSubmit",
									dataType : "text",
									data:{
										'uid':id,
										
									},
									async : false,
									cache : false,
									success : function(response) {
										
										swal({
											type: 'success',
											title: response,
											showConfirmButton: false,	
										});
										
										window.location.reload();
										
									}
								});
						  } 
						});
					
					
					
					
					
				}
				
				
				
				</script>