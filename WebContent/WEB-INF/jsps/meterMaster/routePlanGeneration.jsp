<%@include file="/WEB-INF/decorators/taglibs.jsp"%>
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
<script>
		$(document).ready(function(){   
			$('#routePalnScreen').show();
			$('#routePaln').addClass('active');
			
			var activeMod="${activeModulesNew}";
			var module="Route Plan";
			var check=activeMod.indexOf(module) > -1;
			
			if(check){
			}else{
				window.location.href="./accessDenied";
			}
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
var leng=0;
var rpgConnNo=null,rpgMtrNo=null,rpgReadDay=null,rpgWardNo=null,rpgSeqNo=null,rpgSeqAbbr=null,rpgAreaNo=null,routePlan=null;
function viewrtPlanGen()
{
	//location.reload(true);
		if($('#mrCode').val().trim()=="Noval")
		{
		$('#alertDiv').show();
	      $('#alertDiv').html('<div class="alert alert-danger alert-bordered"><button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button><span class="text" id="alert">Please Select MR Code</span>');
	      //$("#alertDiv").fadeOut(5000);
	      return false;
		}  
		if($('#readingDay').val().trim()=="Noval")
		{
		$('#alertDiv').show();
	      $('#alertDiv').html('<div class="alert alert-danger alert-bordered"><button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button><span class="text" id="alert">Please Select Reading Days</span>');
	     // $("#alertDiv").fadeOut(5000);
	      return false;
		}  
		if($('#wardNo').val().trim()=="Noval")
		{
		$('#alertDiv').show();
	      $('#alertDiv').html('<div class="alert alert-danger alert-bordered"><button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button><span class="text" id="alert">Please Select Ward No</span>');
	      //$("#alertDiv").fadeOut(5000);
	      return false;
		}  
		$('#alertDiv').hide();
		var mrCode=$("#mrCode").val();
		var readingDay=$("#readingDay").val();
		var wardNo=$("#wardNo").val();
		var areaNumber=null;
		if($('#areaNumber').val().trim()=="Noval")
		{
			areaNumber="Noval";
		}
		else
		{
			areaNumber=$('#areaNumber').val();
		}
		
		$.ajax({
				  type: "GET",
				  url: "./viewRtPlanGen/"+mrCode+"/"+readingDay+"/"+wardNo+"/"+areaNumber,
				  dataType: "json",
				  cache       : false,
				  async       : false,
				  success: function(response)
				  {
					  if(response=="")
					  {
						  $("#rtPlanGen").hide();
						  $('#conns').val("No Connection Found");
						  swal({
					            title: "No Connections Found!",
					            text: "",
					            confirmButtonColor: "#2196F3",
					        });
							return false;
							
					  }
					  else
					  {
						  $('#rtPlanGen').show();
						  var html="";
						  $('#conns').val(response.length+" Connections Found");
						  routePlan=response;
						  leng=0;
						  $.each(routePlan, function(index, data)
							{
							 
							  html+="<tr>"
								  		+" <td hidden='true'><input  placeholder='' type='text' id='id"+leng+"' name='id"+leng+"' value='"+data[7]+"'></td>"
										+" <td><input  placeholder='' type='text' id='connection_no"+leng+"' name='connection_no"+leng+"' onchange='connectionDetails("+leng+")' value='"+data[0]+"'></td>"
										+" <td><input  placeholder='' type='text' id='mtr_ser_no"+leng+"' name='mtr_ser_no"+leng+"' value='"+data[1]+"'></td>"
										+" <td><input  placeholder='' type='text' id='reading_day"+leng+"' name='reading_day"+leng+"' value='"+data[2]+"'></td>"
										+" <td><input  placeholder='' type='text' id='ward_no"+leng+"' name='ward_no"+leng+"' value='"+data[3]+"'></td>"
										+" <td><input  placeholder='' type='text' id='seq_no"+leng+"' name='seq_no"+leng+"' value='"+data[4]+"'></td>"
										+" <td><input  placeholder='' type='text' id='seq_abbr"+leng+"' name='seq_abbr"+leng+"' value='"+data[5]+"'></td>"
										+" <td><input  placeholder='' type='text' id='area_no"+leng+"' name='area_no"+leng+"' value='"+data[6]+"'></td>"
									+" </tr>"; 
									leng++;
						  });
							$('#lengthVal').val(leng);
						  $("#tableid123").html(html);
						 
					  }
				  }
			});
		
}

function modifyButton()
{
	$("#routePlanForm").attr("action", "./updateRpgConns").submit();
}

function connectionDetails(id)
{
	//var res = id.substring(13, 15);
	connectionno=$("#connection_no"+id).val();
	$.ajax({
		  type: "GET",
		  url: "./metering/connectionDetails/"+connectionno,
		  dataType: "json",
		  cache       : false,
		  async       : false,
		  success: function(response)
		  {
		  	if(response!=null)
		  	{
			  for(var i=0;i<response.length;i++)
			  {
				  data=response[i];
				  $('#ledgno').val(data.ledgerno);
				  $('#folio').val(data.folio);
			  }
		  	}
		  	if(response=='')
		  	{
		  		swal({
		            title: "Connection No : "+connectionno+" Does't Exist",
		            text: "",
		            confirmButtonColor: "#2196F3",
		        });
				return false;
		  	}
		  		
	  	}
	});
}

</script>
	
				<div class="panel panel-flat">
						<div class="panel-body">
						<div class="row" hidden="true" id="alertDiv">
								    </div>
							<!-- <form class="form-horizontal" action="#">-->
							<form:form action=" " role="form" modelAttribute="mtrRoutePlanGen" commandName="mtrRoutePlanGen" method="POST" id="mtrRoutePlanGen">
							<fieldset class="content-group"> 
								<legend class="text-bold" style="text-align: center;font-size: 18px;">Route Plan Generation</legend><br>		
								
										<div class="row">
											<div class="col-md-4">
												<div class="form-group">
													<label>Meter Reader Code&nbsp;<font color="red">*</font></label>
					                                <form:select path="" id="mrCode" name="mrCode" class="selectbox input-group">
							   			 				<form:option  value="Noval" data-icon="icon-git-branch">Select MR Code</form:option>
							   			 				<c:forEach items="${mrNoList}" var="mr">
														<form:option value="${mr.mrCode}">${mr.mrCode}</form:option>
													   </c:forEach>
													</form:select>
				                                </div>
											</div>
											
											<div class="col-md-4">
												<div class="form-group">
													<label>Reading Day&nbsp;<font color="red">*</font></label>
					                                <form:select path=""  id="readingDay" name="readingDay" class="selectbox input-group">
							   			 				<form:option  value="Noval" data-icon="icon-git-branch">Select Reading Day</form:option>
							   			 				<c:forEach items="${readingDayList}" var="rdays">
														<form:option value="${rdays.readingDay}">${rdays.readingDay}</form:option>
													   </c:forEach>
													</form:select>
				                                </div>
											</div>
											
											<div class="col-md-4">
												<div class="form-group">
													<label>Ward No&nbsp;<font color="red">*</font></label>
					                                <form:select path=""  id="wardNo" name="wardNo" class="selectbox input-group">
							   			 				<form:option  value="Noval" data-icon="icon-git-branch">Select Ward No</form:option>
							   			 				<c:forEach items="${wardNoList}" var="ward">
														<form:option value="${ward.wardNo}">${ward.wardNo}</form:option>
													   </c:forEach>
													</form:select>
				                                </div>
											</div>
											
									     </div>
									     
									     <div class="row">
											<!-- <div class="col-md-4">
												<div class="form-group">
													<label>Area No</label>
					                                <input id="areaNo" name="areaNo" type="text" class="form-control" placeholder="Area No" >
					                               </div>
											</div> -->
											
											<div class="col-md-4">
												<div class="form-group">
													<label>Area No</label>
														<form:select  path=""  class="selectbox input-group" id="areaNumber" name="areaNo">
													    	<form:option value="Noval" data-icon="icon-git-branch">Select</form:option>
															<c:forEach items="${areaNoList}" var="area">
															<form:option value="${area.areaNo}">${area.areaNo}</form:option>
													   		</c:forEach>
													</form:select>
												</div>
											  </div>
											
											<div class="col-md-1">
												<div class="form-group">
													<label>&nbsp;</label>
					                               </div>
											</div>
											
											<div class="col-md-4">
												<div class="form-group">
													<label>&nbsp;</label>
					                                <br>
					                                <button type="button" class="btn bg-blue"  data-spinner-size="20" onclick="return viewrtPlanGen();"><span class="ladda-label">View Connections</span></button>
					                               </div>
											</div>
								     	</div>
								     
							 <legend></legend>
							<br>	
							<div class="form-group">
									<label class="control-label col-lg-2">Connections&nbsp;</label>
									<div class="col-lg-4">
										<div class="row">
											<div class="col-lg-6">
												<form:input path="" id="conns" name="conns" type="text" class="form-control" placeholder="Connections" />
											</div>

										</div>
									</div>
								</div>
								</fieldset>	     	
							    
						  	
								</form:form>	
								
								<form:form action="#" role="form" modelAttribute="routePlanForm" commandName="routePlanForm" method="POST" id="routePlanForm">
								<input type="text" id="lengthVal" name="lengthVal" hidden="true"/>
								<div id="rtPlanGen" hidden="true">
						
								<div class="row">
									<div class="table-responsive">
					
										<table class="table table-bordered" id="tableForm">
											<thead>
					
												<tr class="bg-blue">
													<th>Connection No</th>
													<th>Meter Reader ID</th>
													<th>Reading Day</th>
													<th>Ward No</th>
													<th>Sequence No</th>
													<th>Sequence Abr</th>
													<th>Area No</th>
													
												</tr>
					
											</thead>
											<tbody id="tableid123">
												 <!-- <tr>
													<td><input placeholder="" type="text" id="rpgConnNo" name=""></td>
													<td><input placeholder="" type="text" id="rpgMtrNo" name=""></td>
													<td><input placeholder="" type="text" id="rpgReadDay" name=""></td>
													<td><input placeholder="" type="text" id="rpgWardNo" name=""></td>
													<td><input placeholder="" type="text" id="rpgSeqNo" name=""></td>
													<td><input placeholder="" type="text" id="rpgSeqAbbr" name=""></td>
													<td><input placeholder="" type="text" id="rpgAreaNo" name=""></td>
												</tr> -->
												
											</tbody>
											
										</table>
										<br>     
							     			<div class="text-center">
												<button type="button" class="btn bg-blue"  data-style="expand-right" data-spinner-size="20" onclick="modifyButton();"><span class="ladda-label">Update</span></button>
												<!-- <button type="button" class="btn bg-blue btn-ladda btn-ladda-progress"  data-style="expand-right" data-spinner-size="20"><span class="ladda-label">Reset</span></button> -->
											</div>
										<br>
									</div>
								
								</div>
								</div>   
								
								</form:form>
								
				</div>			
			</div>
	
	
					<!-- <div class="row">
						
						<div class="panel panel-flat">
						<div class="panel-heading">
						    <h4 class="panel-title">
						   <center>
						    <span class="label bg-info" STYLE="font-size: 11pt">Route Plan Generation</span></h4>
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
						
								<tr class="info"><td>Meter Reader Code&nbsp;<font color="red">*</font></td><td><select name="select" class="form-control"><option value="opt1">Select MR Code</option></select></td></td><td>Reading Day&nbsp;<font color="red">*</font></td><td><select name="select" class="form-control"><option value="opt1">Select Reading Day</option></select></td></tr>
								<tr class="active"><td>Ward No&nbsp;<font color="red">*</font></td><td><select name="select" class="form-control"><option value="opt1">Select Ward No</option></select></td><td>Area No</td><td><input type="text" class="form-control" placeholder="Enter Area No."></td></tr>
								
			                    </tbody>
			                </table>
						</div> 
						
		                <div class="text-left">
		                		<br><center><button type="view" class="btn bg-teal-400">View Connections</i></button>
		                </div>
		                
		                <legend></legend> 
		                
		                  <div class="table-responsive">
							<table class="table">
						
								<tr class="danger"><td>Connection No</td><td></td><td></td><td><input type="text" class=" form-control" placeholder="Enter Connection No."></td><td></td><td><td></td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
			                  </tbody>
			                </table>
						</div> 
						<legend></legend> 
		                
		              </div>

						
					</div> -->
	