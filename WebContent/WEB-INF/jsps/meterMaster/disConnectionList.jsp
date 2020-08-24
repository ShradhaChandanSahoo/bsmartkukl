<%@include file="/WEB-INF/decorators/taglibs.jsp"%>

<script type="text/javascript">
$(document).ready(function(){   
	$('#disconnReconnScreens').show();
	$('#disconnReconnModule').addClass('active');
	//alert('${generateDisList}');
	if('${generateDisList.size()}'==0)
	{
		$("#generatedList").hide();
	}
	else
	{
		$("#generatedList").show();
	}
	
	$('#entereddate_Nep').nepaliDatePicker({
		 dateFormat: "%D, %M %d, %y",
			npdMonth: true,
			npdYear: true
  	});
}); 
	
function genDisConnList()
{
	var t_sdate = $("#entereddate_Eng").val()
    var sptdate = String(t_sdate).split("/");
    var myMonth = sptdate[0];
    var myDay = sptdate[1];
    var myYear = sptdate[2];
    var combineDatestr = myYear + "-" + myMonth + "-" + myDay;

    var wardNo=$("#wardNo").val();
	var connCat=$("#connCat").val();
	var pipeSize=$("#pipeSize").val();
	var entereddate_Nep=$("#entereddate_Nep").val();
	var cutoffAmtfrm=$("#cutoffAmtfrm").val();
	var cutoffAmtto=$("#cutoffAmtto").val();
	 var html="";
	$.ajax({
			  type: "GET",
			  url: "./genDisConnList/"+wardNo+"/"+connCat+"/"+pipeSize+"/"+entereddate_Nep+"/"+combineDatestr+"/"+cutoffAmtfrm+"/"+cutoffAmtto,
			  dataType: "json",
			  cache       : false,
			  async       : false,
			  success: function(response)
			  {
				  //alert(response);
				  if(response=='')
				  	{
				  		swal({
				            title: "Data Not Found",
				            text: "",
				            confirmButtonColor: "#2196F3",
				        });
						return false;
				  	}
				  
				  else if(response=='AlreadyExist')
				  	{
				  		swal({
				            title: "Connection No List Already Generated",
				            text: "",
				            confirmButtonColor: "#2196F3",
				        });
						return false;
				  	}
				  else
				  {	
					  
					  for(var i=0;i<response.length;i++)
					  {
						  data=response[i];
					  $("#disConnGenListDetails").show();
					 
					  if(data.name_nep==null)
					  {
						  data.name_nep="";
					  }
					  if(data.address_eng==null)
					  {
						  data.address_eng="";
					  }
					  if(data.address_nep==null)
					  {
						  data.address_nep="";
					  }
					  var ltDate=moment(data.list_date).format('YYYY/MM/DD');
					  html+="<tr>"
							+" <td>"+data.listno+"</td>"
							+" <td>"+ltDate+"</td>"
							+" <td>"+data.monthyear+"</td>"
							+" <td>"+data.connectionno+"</td>"
							+" <td width='55%'>"+data.name_eng+"</td>"
							+" <td>"+data.name_nep+"</td>"
							+" <td>"+data.address_eng+"</td>"
							+" <td>"+data.address_nep+"</td>"
							+" <td>"+data.pipe_size+"</td>"
							+" <td>"+data.con_category+"</td>"
							+" <td>"+data.ward_no+"</td>"
							+" <td>"+data.arrears+"</td>"
							+" </tr>";
					  //loadSearchFilter1('tableForm',html,'tableid123');
				  } 
					  $("#tableid123").html(html);
				  }
			  }
		});
}

/* function loadSearchFilter1(param,tableData,temp)
{
    $('#'+param).dataTable().fnClearTable();
    $('#'+param).dataTable().fnDestroy();
    $('#'+temp).html(tableData);
    $('#'+param).dataTable();

}  */
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

.scroll {
   width: 100px;
   height: 100px;
   overflow: scroll;
}

.table-responsive {
    min-height: 0.01%;
    overflow-x: auto;
}

.datatable-footer {
    display: none;
}
</style>
		
			<div class="panel panel-flat">
						<div class="panel-body">
						<input type="hidden" id="jsonBom" value='${generateDisList}'/>
							<form:form action="./genDisConnList" role="form" modelAttribute="disConnListGen" commandName="disConnListGen" method="POST" id="disConnListGen">
							<fieldset class="content-group"> 
								<legend class="text-bold" style="font-size: 18px;">Disconnection List</legend>
										
										<div class="row">
											<div class="col-md-4">
												<div class="form-group">
													<label>Ward No&nbsp;<font color="red">*</font></label>
					                                <form:select path="" id="wardNo" name="wardNo" class="selectbox input-group">
							   			 				<form:option value="noVal" data-icon="icon-git-branch">Select Ward No's</form:option>
							   			 				<c:forEach items="${wardNoList}" var="ward">
														<form:option value="${ward.wardNo}">${ward.wardNo}</form:option>
														</c:forEach>
													</form:select>
				                                </div>
											</div>
											
											<!-- <div class="col-md-4">
												<div class="form-group">
													<label>Meter Reader</label>
					                                <select id="mtrReader" name="mtrReader" class="selectbox input-group">
							   			 				<option  value="" data-icon="icon-git-branch">All</option>
													</select>
				                                </div>
											</div> -->
											
											<div class="col-md-4">
												<div class="form-group">
													<label>Connection Category&nbsp;<font color="red">*</font></label>
					                                <form:select path="" id="connCat" name="connCat" class="selectbox input-group">
							   			 				<form:option  value="noVal" data-icon="icon-git-branch">Select Conn Category</form:option>
							   			 				<c:forEach items="${connCatList}" var="cat">
														<form:option value="${cat.connCat}">${cat.connCat}</form:option>
														</c:forEach>
													</form:select>
				                                </div>
											</div>
											
											<div class="col-md-4">
												<div class="form-group">
													<label>Pipe Size&nbsp;<font color="red">*</font></label>
					                                <form:select path="" id="pipeSize" name="pipeSize" class="selectbox input-group">
							   			 				<form:option  value="noVal" data-icon="icon-git-branch">Select Pipe Size</form:option>
							   			 				<%-- <form:option  value="2" data-icon="icon-git-branch">2</form:option> --%>
							   			 				 <c:forEach items="${pipeSizeList}" var="ps">
														<form:option value="${ps.pipeSize}">${ps.pipeSize}</form:option>
														</c:forEach>
													</form:select>
				                                </div>
											</div>
											
									     </div>
									     
									     <div class="row">
											<!-- <div class="col-md-4">
												<div class="form-group">
													<label>Pipe Size&nbsp;<font color="red">*</font></label>
					                                <select id="pipeSize" name="pipeSize" class="selectbox input-group">
							   			 				<option  value="" data-icon="icon-git-branch">1/2</option>
													</select>
				                                </div>
											</div> -->
											
											<div class="col-md-4">
												<div class="form-group">
													<label>Entered Date in Nepali&nbsp;<font color="red">*</font></label>
					                                <form:input path="" type="text" id="entereddate_Nep" name="entereddate_Nep" class="form-control nepali-calendar" placeholder="Entered Date in Nepali" />
				                                </div>
											</div>
											
											<div class="col-md-4">
												<div class="form-group">
													<label>Entered Date in English</label>
					                                <div class="input-group">
														<span class="input-group-addon"><i class="icon-calendar22"></i></span>
															<form:input path="" id="entereddate_Eng" name="entereddate_Eng" type="text" class="form-control daterange-single" />
													</div>
				                                </div>
											</div>
											
											
									     </div>
									     
									     <div class="row">
											<div class="col-md-4">
												<div class="form-group">
													<label>Cut Off AMount From&nbsp;<font color="red">*</font></label>
					                                <form:input path="" id="cutoffAmtfrm" name="cutoffAmtfrm" type="text" class="form-control" placeholder="Cut Off AMount From" />
				                                </div>
											</div>
											
											<div class="col-md-4">
												<div class="form-group">
													<label>Cut Off AMount To&nbsp;<font color="red">*</font></label>
					                                <form:input path="" id="cutoffAmtto" name="cutoffAmtto" type="text" class="form-control" placeholder="Cut Off AMount To" />
				                                </div>
											</div>
											
											 											
											<div class="col-md-4">
												<div class="form-group">
													<label>&nbsp;</label>
					                                <br><button type="button" class="btn bg-blue"  data-style="expand-right" data-spinner-size="20" onclick="genDisConnList();"><span class="ladda-label">Generate</span></button>
				                                </div>
											</div>
											
									     </div>	
									
									</fieldset>	</form:form>
									
								<form:form action="#" role="form" method="POST">	     
				<div id="disConnGenListDetails" hidden="true" >
						<legend class="text-bold" >Disconnection List Details</legend>
								<div class="row">
									<div class="table-responsive">
									<div class="col-md-16">
										<div class="panel-body" style="margin-top: -3px; padding: 6px;" >
										<table class="table datatable-basic table-bordered" id="tableForm">
											<thead>
					
												<tr class="bg-blue">
													<th>LISTNO</th>
													<th>LIST_DATE</th>
													<th>MONTHYEAR</th>
													<th>CONNECTIONNO</th>
													<th>NAME_ENG</th>
													<th>NAME_NEP</th>
													<th>ADDRESS_ENG</th>
													<th>ADDRESS_NEP</th>
													<th>PIPE_SIZE</th>
													<th>CONN_CATEGORY</th>
													<th>WARD_NO</th>
													<th>ARREARS</th>
												</tr>
					
											</thead>
											<tbody id="tableid123">
												 <%-- <c:forEach var="data" items="${disConnectionList}"> 
												<tr>
													<td>${data[0]}</td>
													<td>${data[1]}</td>
													<td>${data[2]}</td>
													<td>${data[3]}</td>
													<td>${data[4]}</td>
													<td>${data[5]}</td>
													<td>${data[6]}</td>
												</tr>
												</c:forEach> --%>
											</tbody>
										</table>
									</div></div>
								
					
								</div>
								</div></div> 	
									
								</form:form>
								
								<form:form action="#" role="form" method="POST">	     
				<div id="generatedList" hidden="true">
						<legend class="text-bold" >Disconnection List Details</legend>
								<div class="row">
									<div class="table-responsive">
									<div class="col-md-16">
										<div class="panel-body" style="margin-top: -3px; padding: 6px;" >
										<table class="table datatable-basic table-bordered" id="tableForm">
											<thead>
					
												<tr class="bg-blue">
													<th>LISTNO</th>
													<th>LIST_DATE</th>
													<th>MONTHYEAR</th>
													<th>CONNECTIONNO</th>
													<th>NAME_ENG</th>
													<th>NAME_NEP</th>
													<th>ADDRESS_ENG</th>
													<th>ADDRESS_NEP</th>
													<th>PIPE_SIZE</th>
													<th>CONN_CATEGORY</th>
													<th>WARD_NO</th>
													<th>ARREARS</th>
												</tr>
					
											</thead>
											<tbody>
												 <c:forEach var="data" items="${generateDisList}"> 
												<tr>
													<td>${data.listno}</td>
													<td>${data.list_date}</td>
													<td>${data.monthyear}</td>
													<td>${data.connectionno}</td>
													<td>${data.name_eng}</td>
													<td>${data.name_nep}</td>
													<td>${data.address_eng}</td>
													<td>${data.address_nep}</td>
													<td>${data.pipe_size}</td>
													<td>${data.con_category}</td>
													<td>${data.ward_no}</td>
													<td>${data.arrears}</td>
												</tr>
												</c:forEach> 
											</tbody>
										</table>
									</div></div>
								
					
								</div>
								</div></div> 	
									
								</form:form>
								<!-- <div class="text-center">
									<button type="button" class="btn bg-blue btn-ladda btn-ladda-progress"  data-style="expand-right" data-spinner-size="20"><span class="ladda-label">Generate</span></button>
								</div> -->
				</div>			
			</div>				
