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
			$('#meteringscreens').show();
			$('#meterManagement').addClass('active');
			
			$('#ledgno').val('');
			$('#obs_date_eng').val('');
			$('#entereddate_eng').val('');
			
			$('#frmobservdate_Nep').nepaliDatePicker({
				 dateFormat: "%D, %M %d, %y",
					npdMonth: true,
					npdYear: true
		  	});
			
			$('#toobservdate_Nep').nepaliDatePicker({
				 dateFormat: "%D, %M %d, %y",
					npdMonth: true,
					npdYear: true
		  	});
			
			$('#obs_date_nep').nepaliDatePicker({
				 dateFormat: "%D, %M %d, %y",
					npdMonth: true,
					npdYear: true,
					onChange: function(){
				 		var obsdtnep = $('#obs_date_nep').val();
				 		getEngDate(obsdtnep,1);
				 	}
		  	});
			
			$('#entereddate_nep').nepaliDatePicker({
				 dateFormat: "%D, %M %d, %y",
					npdMonth: true,
					npdYear: true,
					onChange: function(){
				 		var entdtnep = $('#entereddate_nep').val();
				 		getEngDate(entdtnep,2);
				 	}
		  	});
			
		}); 
		
		function getEngDate(nepalidate,value)
		{
			var date_nep=nepalidate;
			$.ajax({
						  type: "GET",
						  url: "./billing/onChangeNepaliDate",
						  dataType: "text",
						  async   : false,
						  data:{
							 	date_nep:date_nep,
						  		},
						  success: function(response)
						  {
							  if(value==1)
							  {
								  $('#obs_date_eng').val(moment(response).format('YYYY/MM/DD'));
							  }
							  else
							  {
								  $('#entereddate_eng').val(moment(response).format('YYYY/MM/DD'));
							  }
						  }
				});
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
    padding: 8px 0px 0 0px;
}
</style>	

<script type="text/javascript">

function checkObservationDetails() 
{
	if($('#connectionno').val().trim()=='')
	{
	$('#alertDiv').show();
      $('#alertDiv').html('<div class="alert alert-danger alert-bordered"><button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button><span class="text" id="alert">Please enter Connection No</span>');
      $("#alertDiv").fadeOut(5000);
      return false;
	}
	
	if($('#observationno').val().trim()=='')
	{
	$('#alertDiv').show();
      $('#alertDiv').html('<div class="alert alert-danger alert-bordered"><button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button><span class="text" id="alert">Please enter Observation No</span>');
      $("#alertDiv").fadeOut(5000);
      return false;
	}
	    
	if($('#mrcode').val().trim()=='')
	{
	$('#alertDiv').show();
      $('#alertDiv').html('<div class="alert alert-danger alert-bordered"><button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button><span class="text" id="alert">Please Select Meter Reader</span>');
      $("#alertDiv").fadeOut(5000);
      return false;
	}
	
	if($('#observation').val().trim()=='')
	{
	$('#alertDiv').show();
      $('#alertDiv').html('<div class="alert alert-danger alert-bordered"><button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button><span class="text" id="alert">Please enter Observation</span>');
      $("#alertDiv").fadeOut(5000);
      return false;
	}
	
	if($('#obs_date_nep').val().trim()=='')
	{
	$('#alertDiv').show();
      $('#alertDiv').html('<div class="alert alert-danger alert-bordered"><button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button><span class="text" id="alert">Please enter Observation Nepali Date</span>');
      $("#alertDiv").fadeOut(5000);
      return false;
	}
	
	if($('#entereddate_nep').val().trim()=='')
	{
	$('#alertDiv').show();
      $('#alertDiv').html('<div class="alert alert-danger alert-bordered"><button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button><span class="text" id="alert">Please enter Entered Nepali Date </span>');
      $("#alertDiv").fadeOut(5000);
      return false;
	}
	
	var datavalue="";
	var observationno=$("#observationno").val();
	$.ajax({
		  type: "GET",
		  url: "./uniqueObsNo/"+observationno,
		  dataType: "text",
		  cache       : false,
		  async       : false,
		  success: function(response)
		  {
			  if(response=="CanAdd"){
				 return true;
				}else{
					datavalue="AlreadyExist";
				}
		  }
		});
	if(datavalue=='AlreadyExist'){
		swal({
          title: "Obeservation No Already Exist",
          text: "",
          confirmButtonColor: "#2196F3",
      });
		return false;
	}else{
		return true;
	}
}
var data=null;
var obid=null,obconnectionno=null,obobservationno=null,obledgno=null,obfolio=null,obmrcode=null,obobservation=null,
obobsdate_eng=null,obobs_date_nep=null,obentdate_eng=null,obentereddate_nep=null,obremarks=null;
function searchObsDetails()
{
	  if($('#connNo').val().trim()=='')
	{
	$('#alertDiv').show();
      $('#alertDiv').html('<div class="alert alert-danger alert-bordered"><button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button><span class="text" id="alert">Please enter Connection No</span>');
      $("#alertDiv").fadeOut(5000);
      return false;
	}  
	//alert('hio');
	var connectionno=$("#connNo").val();
	$.ajax({
			  type: "GET",
			  url: "./searchConnNo/"+connectionno,
			  dataType: "json",
			  cache       : false,
			  async       : false,
			  success: function(response)
			  {
				  if(response=='')
				  	{
				  		swal({
				            title: "Connection No : "+connectionno+" Does't Exist",
				            text: "",
				            confirmButtonColor: "#2196F3",
				        });
						return false;
				  	}
				  else{
				  var html="";
				  for(var i=0;i<response.length;i++)
				  {
					  data=response[i];
					  var obsdate_eng=moment(data.obs_date_eng).format('YYYY/MM/DD');
					  var entdate_eng=moment(data.entereddate_eng).format('YYYY/MM/DD');
					  html+="<tr>"
						+" <td>"+data.observationno+"</td>"
						+" <td>"+data.connectionno+"</td>"
						+" <td>"+obsdate_eng+"</td>"
						+" <td>"+entdate_eng+"</td>"
						+" <td>"+data.observation+"</td>"
						+" <td><a href='#' onclick=\"editMeterObsDetails('"+data.id+"','"+data.connectionno+"','"+data.observationno+"','"+data.ledgno+"','"+data.folio+"','"+data.mrcode+"','"+data.observation+"','"+obsdate_eng+"','"+data.obs_date_nep+"','"+entdate_eng+"','"+data.entereddate_nep+"','"+data.remarks+"')\">[Edit]</a></td>"  
						//+" <td><a href='#' onclick='editMeterObsDetails("+data.remarks+")'>[Edit]</a></td>"  
						+" </tr>";
				  }
				  $("#tableid123").html(html);	
				  $("#observnDetails").show();
				  loadSearchFilter1('tableForm',html,'tableid123');
				  }     
			  }
		});
}

function editMeterObsDetails(id,connectionno,observationno,ledgno,folio,mrcode,observation,obsdate_eng,obs_date_nep,
		entdate_eng,entereddate_nep,remarks)
{
	$("#id").val(id);
	$("#connectionno").val(connectionno);
	$("#observationno").val(observationno);
	$("#ledgno").val(ledgno);
	$("#folio").val(folio);
	$("#mrcode").val(mrcode);
	$("#observation").val(observation);
	$("#obs_date_eng").val(obsdate_eng);
	$("#obs_date_nep").val(obs_date_nep);
	$("#entereddate_eng").val(entdate_eng);
	$("#entereddate_nep").val(entereddate_nep);
	$("#remarks").val(remarks);
	
	$("#addId").hide();
	$("#editId").show();
}

function modifyButton()
{
	if($('#connectionno').val().trim()=='')
	{
	$('#alertDiv').show();
      $('#alertDiv').html('<div class="alert alert-danger alert-bordered"><button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button><span class="text" id="alert">Please enter Connection No</span>');
      $("#alertDiv").fadeOut(5000);
      return false;
	}
	
	if($('#observationno').val().trim()=='')
	{
	$('#alertDiv').show();
      $('#alertDiv').html('<div class="alert alert-danger alert-bordered"><button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button><span class="text" id="alert">Please enter Observation No</span>');
      $("#alertDiv").fadeOut(5000);
      return false;
	}
	    
	if($('#mrcode').val().trim()=='')
	{
	$('#alertDiv').show();
      $('#alertDiv').html('<div class="alert alert-danger alert-bordered"><button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button><span class="text" id="alert">Please Select Meter Reader</span>');
      $("#alertDiv").fadeOut(5000);
      return false;
	}
	
	if($('#observation').val().trim()=='')
	{
	$('#alertDiv').show();
      $('#alertDiv').html('<div class="alert alert-danger alert-bordered"><button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button><span class="text" id="alert">Please enter Observation</span>');
      $("#alertDiv").fadeOut(5000);
      return false;
	}
	
	if($('#obs_date_nep').val().trim()=='')
	{
	$('#alertDiv').show();
      $('#alertDiv').html('<div class="alert alert-danger alert-bordered"><button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button><span class="text" id="alert">Please enter Observation Nepali Date</span>');
      $("#alertDiv").fadeOut(5000);
      return false;
	}
	
	if($('#entereddate_nep').val().trim()=='')
	{
	$('#alertDiv').show();
      $('#alertDiv').html('<div class="alert alert-danger alert-bordered"><button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button><span class="text" id="alert">Please enter Entered Nepali Date </span>');
      $("#alertDiv").fadeOut(5000);
      return false;
	}
	
	var id=$("#id").val();
	var observationno=$("#observationno").val();
	
	var datavalue="";
	$.ajax({
		  type: "GET",
		  url: "./uniqueObsSearchNoForEdit/"+observationno+"/"+id,
		  dataType: "text",
		  cache       : false,
		  async       : false,
		  success: function(response)
		  {
			  if(response=="CanAdd"){
				 return true;
				}else{
					datavalue="AlreadyExist";
				}
		  }
		});
	if(datavalue=='AlreadyExist'){
		swal({
            title: "Observation No Already Exist",
            text: "",
            confirmButtonColor: "#2196F3",
        });
		return false;
	}
	else{
		$("#meterObservationEntity").attr("action", "./editObsSearch").submit();
	}
}

function resetButton()
{
	$('#meterObservationEntity')[0].reset();
	$('#ledgno').val('');
	$("#addId").show();
	$("#editId").hide();
}

function connectionDetails()
{
	connectionno=$("#connectionno").val();
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

function loadSearchFilter1(param,tableData,temp)
{
    $('#'+param).dataTable().fnClearTable();
    $('#'+param).dataTable().fnDestroy();
    $('#'+temp).html(tableData);
    $('#'+param).dataTable();

} 

</script>

					<div class="panel panel-flat">
						<div class="panel-body">
						<div class="row" hidden="true" id="alertDiv">
								    </div>
								    
							<form:form action="./addNewObservation" role="form" modelAttribute="meterObservationEntity" commandName="meterObservationEntity" method="POST" id="meterObservationEntity">
							<fieldset class="content-group"> 
								<legend class="text-bold" style="text-align: center;font-size: 18px;">Observation Search</legend>
										
										<div class="row">
											<div class="col-md-4">
												<div class="form-group">
													<label>Connection No</label>
					                                <form:input path="" id="connNo" name="connNo" type="text" class="form-control" placeholder="Connection No..." />
				                                </div>
											</div>
											
											<div class="col-md-4">
												<div class="form-group">
													<label>&nbsp;</label>
													<br>&nbsp;&nbsp;&nbsp;&nbsp;<button type="button" class="btn bg-blue"  data-style="expand-right" data-spinner-size="20" onclick="searchObsDetails();"><span class="ladda-label">Search</span></button>
				                                </div>
											</div>
											
											<%-- <div class="col-md-4">
												<div class="form-group">
													<label>To Observn Date in Nepali</label>
					                                <form:input path="" type="text" id="toobservdate_Nep" name="toobservdate_Nep" class="form-control nepali-calendar" placeholder="To Observn Date in Nepali" />
				                                </div>
											</div>--%>
											
									     </div> 
									
									<%-- <div class="row">
											<div class="col-md-4">
												<div class="form-group">
													<label>Observation No</label>
					                                <form:input path="" id="observNo" name="observNo" type="text" class="form-control" placeholder="Observation No..." />
				                                </div>
											</div>
											
										 <div class="col-md-4">
												<div class="form-group">
												<label>From Observn Date in English</label> 
												<div class="input-group">
														<span class="input-group-addon"><i class="icon-calendar22"></i></span>
														<form:input path="" id="frmobservdate_Eng" name="frmobservdate_Eng" type="text" class="form-control daterange-single"/>
												</div>
												</div>
										</div>
									
											<div class="col-md-4">
												<div class="form-group">
												<label>To Observn Date in English</label> 
												<div class="input-group">
														<span class="input-group-addon"><i class="icon-calendar22"></i></span>
														<form:input path="" id="toobservdate_Eng" name="toobservdate_Nep" type="text" class="form-control daterange-single"/>
												</div>
											</div>
										</div>
											
									     </div> --%>
									     
									     
									
								
								<!-- <div class="text-center">
									<button type="button" class="btn bg-blue"  data-style="expand-right" data-spinner-size="20" onclick="searchObsDetails();"><span class="ladda-label">Search</span></button>
								</div> -->
							
					<br>
					<legend class="text-bold">Register New Observation</legend>
					
					<div class="row">
						<div class="col-md-4">
							<div class="form-group">
								<label>Connection No&nbsp;<font color="red">*</font></label>
                                <form:input path="connectionno" id="connectionno" name="connectionno" type="text" class="form-control" placeholder="Connection No" onchange="connectionDetails(this.value)" />
                               </div>
						</div>
						
						<div class="col-md-4">
							<div class="form-group">
								<label>Observation No&nbsp;<font color="red">*</font></label>
                                <form:input path="observationno" id="observationno" name="observationno" type="text" class="form-control" placeholder="Observation No" />
                               </div>
						</div>
						
						<div class="col-md-4">
							<div class="form-group">
								<label>Ledger No</label>
                                <form:input path="ledgno" id="ledgno" name="ledgno" type="text" class="form-control" placeholder="Ledger No" />
                               </div>
						</div>
											
			     	</div>

			     	<div class="row">
						<div class="col-md-4">
							<div class="form-group">
								<label>Folio No</label>
                                <form:input path="folio" id="folio" name="folio" type="text" class="form-control" placeholder="Folio No" />
                               </div>
						</div>
						
						<div class="form-group col-md-4">
							<label>Meter Reader&nbsp;<font color="red">*</font></label>
							<form:select path="mrcode" id="mrcode" name="mrcode" class="selectbox input-group">
							    <form:option  value="MR01" data-icon="icon-git-branch">Select MR Code</form:option>
	   			 				<c:forEach items="${mrNoList}" var="mr">
								<form:option value="${mr.mrcode}">${mr.mrcode}</form:option>
							   </c:forEach>
							</form:select>
						</div>
						
						<div class="col-md-4">
							<div class="form-group">
								<label>Observation&nbsp;<font color="red">*</font></label>
                                <%-- <form:input path="observation" id="observation" name="observation" type="text" class="form-control" placeholder="Observation" /> --%>
                                <form:select path="observation" id="observation" name="observation" class="selectbox input-group">
							    <form:option value="NoVal" data-icon="icon-git-branch">Select Observation's</form:option>
							    <form:option value="INACCESSIBLE" data-icon="icon-git-branch">INACCESSIBLE</form:option>
							    <form:option value="GLASS_BROKEN" data-icon="icon-git-branch">GLASS BROKEN</form:option>
							    <form:option value="SEAL_TAMPERED" data-icon="icon-git-branch">SEAL TAMPERED</form:option>
							    <form:option value="BY_PASS" data-icon="icon-git-branch">BY PASS</form:option>
							</form:select>
                               </div>
						</div>
			     	</div>
					
					<div class="row">
								<div class="col-md-4">
									<div class="form-group">
										<label>Observation Date in Nepali&nbsp;<font color="red">*</font></label>
										<div class="input-group">
										<span class="input-group-addon"><i class="icon-calendar22"></i></span>
			                               <form:input path="obs_date_nep" type="text" id="obs_date_nep" name="obs_date_nep" class="form-control nepali-calendar" placeholder="Observation Date in Nepali" />
			                            </div>
			                          </div>
								</div>
								
								<div class="col-md-4">
									<div class="form-group">
										<label>Entered Date in Nepali&nbsp;<font color="red">*</font></label>
										<div class="input-group">
											<span class="input-group-addon"><i class="icon-calendar22"></i></span>
			                               <form:input path="entereddate_nep" type="text" id="entereddate_nep" name="entereddate_nep" class="form-control nepali-calendar" placeholder="Entered Date in Nepali" />
			                             </div>
			                          </div>
								</div>
								<div class="col-md-4">
										<div class="form-group">
											<label>Remarks</label> 
											
											<form:textarea path="remarks" id="remarks" name="remarks" placeholder="Enter your Remarks here" class="form-control" cols="4" rows="1"></form:textarea>
											
											
										</div>
									</div>
					
									<div class="col-md-4">
											<div class="form-group">
											<label>Observation Date in English</label> 
											<div class="input-group">
													<span class="input-group-addon"><i class="icon-calendar22"></i></span>
													<form:input path="obs_date_eng" id="obs_date_eng" name="obs_date_eng" type="text" class="form-control" placeholder="Observation Date in English" readonly="true"/>
											</div>
										</div>
									</div>
											
										<div class="col-md-4">
											<div class="form-group">
											<label>Entered Date in English</label> 
											<div class="input-group">
													<span class="input-group-addon"><i class="icon-calendar22"></i></span>
													<form:input path="entereddate_eng" id="entereddate_eng" name="entereddate_eng" type="text" class="form-control" placeholder="Entered Date in English" readonly="true"/>
											</div>
										</div>
									</div>
									
									<div class="col-md-4" id="addId">
											<div class="form-group">
											<label>&nbsp;</label> 
											<div class="input-group">
													<button type="submit" class="btn bg-blue"  data-style="expand-right" data-spinner-size="20" onclick="return checkObservationDetails();"><span class="ladda-label">Submit</span></button>
											</div>
										</div>
									</div>
									
									<div class="col-md-4" id="editId" hidden="true">
											<div class="form-group">
											<label>&nbsp;</label> 
											<div class="input-group">
													<button type="button"  class="btn bg-blue"  data-style="expand-right" data-spinner-size="20" onclick="modifyButton();"><span class="ladda-label">Modify</span></button>
													&nbsp;&nbsp;<button type="button"  class="btn bg-blue"  data-style="expand-right" data-spinner-size="20" onclick="resetButton();"><span class="ladda-label">Reset</span></button>
											</div>
										</div>
									</div>
									
									<div hidden="true">
										<form:input path="id" id="id" name="id" type="text" class="form-control" placeholder="Meter ID" />
									</div>
							
							</div>
				
					<!-- <div class="text-center" id="addId">
						<button type="submit" class="btn bg-blue"  data-style="expand-right" data-spinner-size="20" onclick="return checkObservationDetails();"><span class="ladda-label">Submit</span></button>
					</div>
				
					<div class="text-center" id="editId" hidden="true">
						<button type="button"  class="btn bg-blue"  data-style="expand-right" data-spinner-size="20" onclick="modifyButton();"><span class="ladda-label">Modify</span></button>
					</div> -->
						
				<div id="observnDetails" hidden="true">
							<br> <legend class="text-bold" >Observation Details</legend>
								<div class="row">
									<div class="table-responsive">
										<div class="col-md-16">
										<div class="panel-body" style="margin-top: -3px; padding: 6px;" >
										<table class="table datatable-basic table-bordered" id="tableForm">
											<thead>
					
												<tr class="bg-blue" width="100%">
													<th width="18%">Observation No</th>
													<th width="18%">Connection No</th>
													<th width="18%">Observation Date</th>
													<th width="18%">Entered Date</th>
													<th width="18%">Observation</th>
													<th width="18%">Edit</th>
												</tr>
					
											</thead>
											<tbody id="tableid123">
												 
											</tbody>
										</table>
					
									</div>
					
									</div>
									</div> 
								</div>
								</div> 
								</fieldset>	
				</form:form>
				</div>			
			</div>	
