<%@include file="/WEB-INF/decorators/taglibs.jsp"%>

	<script src="resources/js/highcharts.js"></script>

	<script type="text/javascript" src="resources/layout_3/assets/js/charts/c3/c3_bars_pies.js"></script>

	<!-- Theme JS files -->
	<script type="text/javascript" src="resources/layout_3/assets/js/plugins/visualization/c3/c3.min.js"></script>
	<c:if test = "${not empty expirydate}"> 			        
      <script>		        
          var msg = "${expirydate}";
            alert(msg);
      </script>
     
</c:if>


					<div class="row">
					
						<div class="col-lg-12">

							<div class="panel panel-flat">
							
							<ul class="nav nav-lg nav-tabs nav-justified no-margin no-border-radius bg-info-400 border-top border-top-info-300">
								<li>
									<a href="#" class="text-size-small text-uppercase" >
										New Connection Statistics
									</a>
								</li>
							</ul><br>
								
								<div class="container-fluid">
								<div class="row">
									
									<div class="col-lg-4">
										<ul class="list-inline text-center">
											<li>
												<a href="#" class="btn border-indigo-400 text-indigo-400 btn-flat btn-rounded btn-icon btn-xs valign-text-bottom"><i class="icon-people"></i></a>
											</li>
											<li class="text-left">
												<div class="text-semibold">Total Connections</div>
												<div class="text-bold"  id="connectionidspan"><span class="status-mark border-indigo position-left"></span></div>
											</li>
										</ul>

										<div class="col-lg-10 col-lg-offset-1">
											<div class="content-group" id="total-online"></div>
										</div>
									</div>
									
									
									<div class="col-lg-4">
										<ul class="list-inline text-center">
											<li>
												<a href="#" class="btn border-teal text-teal btn-flat btn-rounded btn-icon btn-xs valign-text-bottom"><i class="glyphicon glyphicon-check"></i></a>
											</li>
											<li class="text-left">
												<div class="text-semibold">Applications Approved</div>
												<div class="text-bold"><span class="status-mark border-success position-left"></span>${ncapp}</div>
											</li>
										</ul>

										<div class="col-lg-10 col-lg-offset-1">
											<div class="content-group" id="new-visitors"></div>
										</div>
									</div>
									
									
									<div class="col-lg-4">
										<ul class="list-inline text-center">
											<li>
												<a href="#" class="btn border-pink-400 text-pink-400 btn-flat btn-rounded btn-icon btn-xs valign-text-bottom"><i class="glyphicon glyphicon-pencil"></i></a>
											</li>
											<li class="text-left">
												<div class="text-semibold">Connections Pending</div>
												<div class="text-bold"><span class="status-mark border-pink position-left"></span>${ncwap}</div>
											</li>
											
											
										</ul>

										<div class="col-lg-10 col-lg-offset-1">
											<div class="content-group" id="new-sessions"></div>
										</div>
									</div>
									
									
								</div>
							</div>
								
								

								
							</div>

						</div>
						
						
						
						
						
						<!-- <div class="col-lg-3">
								<div class="panel bg-teal-400">
									<div class="panel-body" onclick='monitorUsers()' style="cursor: pointer;">
										
										<div class="heading-elements">
											<span class="heading-text badge bg-teal-800"></span>
										</div>

										<h3 class="no-margin"><i class="icon-people"></i><a href="#" ></a>
										Members online</h3>
										<div class="text-muted text-size-small"></div><br><br><br>
									</div>

									<div class="container-fluid">
										<div id="members-online"></div>
									</div>
								</div>

						</div> -->
						
						
						
						
						
						
						<div class="col-lg-12">

							<div class="panel panel-flat">
								<ul class="nav nav-lg nav-tabs nav-justified no-margin no-border-radius bg-primary-400 border-top border-top-primary-300">
								<li>
									<a href="#" class="text-size-small text-uppercase" >
										Master Statistics
									</a>
								</li>
							   </ul><br>
							
							
								<div class="container-fluid">
								<div class="row">
									
									<div class="col-lg-2">
										<ul class="list-inline text-left">
											<li>
												<a href="#" class="btn border-indigo-400 text-indigo-400 btn-flat btn-rounded btn-icon btn-xs valign-text-bottom"><i class="glyphicon glyphicon-hand-left"></i></a>
											</li>
											<li class="text-left">
												<div class="text-semibold">Domestic</div>
												<div class="text-bold"><span class="status-mark border-indigo position-left"></span>${dom}</div>
											</li>
										</ul>

										<div class="col-lg-10 col-lg-offset-1">
											<div class="content-group" id="total-online"></div>
										</div>
									</div>
									
									
									<div class="col-lg-2">
										<ul class="list-inline text-left">
											<li>
												<a href="#" class="btn border-teal text-teal btn-flat btn-rounded btn-icon btn-xs valign-text-bottom"><i class="glyphicon glyphicon-hand-left"></i></a>
											</li>
											<li class="text-left">
												<div class="text-semibold">Government</div>
												<div class="text-bold"><span class="status-mark border-success position-left"></span>${gov}</div>
											</li>
										</ul>

										<div class="col-lg-10 col-lg-offset-1">
											<div class="content-group" id="new-visitors"></div>
										</div>
									</div>

									<div class="col-lg-2">
										<ul class="list-inline text-left">
											<li>
												<a href="#" class="btn border-warning-400 text-warning-400 btn-flat btn-rounded btn-icon btn-xs valign-text-bottom"><i class="glyphicon glyphicon-hand-left"></i></a>
											</li>
											<li class="text-left">
												<div class="text-semibold">Indus & Comp</div>
												<div class="text-bold"><span class="status-mark border-warning position-left"></span>${indcom}</div>
											</li>
										</ul>

										<div class="col-lg-10 col-lg-offset-1">
											<div class="content-group" id="new-sessions"></div>
										</div>
									</div>
									
									<div class="col-lg-2">
										<ul class="list-inline text-left">
											<li>
												<a href="#" class="btn border-pink-400 text-pink-400 btn-flat btn-rounded btn-icon btn-xs valign-text-bottom"><i class="glyphicon glyphicon-tasks"></i></a>
											</li>
											<li class="text-left">
												<div class="text-semibold">Metered</div>
												<div class="text-bold"><span class="status-mark border-pink position-left"></span>${met}</div>
											</li>
										</ul>

										<div class="col-lg-10 col-lg-offset-1">
											<div class="content-group" id="new-sessions"></div>
										</div>
									</div>
									
									
									<div class="col-lg-2">
										<ul class="list-inline text-left">
											<li>
												<a href="#" class="btn border-indigo-400 text-indigo-400 btn-flat btn-rounded btn-icon btn-xs valign-text-bottom"><i class="glyphicon glyphicon-tasks"></i></a>
											</li>
											<li class="text-left">
												<div class="text-semibold">UnMetered</div>
												<div class="text-bold"><span class="status-mark border-pink position-left"></span>${unm}</div>
											</li>
										</ul>

										<div class="col-lg-10 col-lg-offset-1">
											<div class="content-group" id="new-sessions"></div>
										</div>
									</div>
									
								
								
							    <div class="col-lg-2">
									<ul class="list-inline text-left">
										<li>
											<a href="#" class="btn border-indigo-400 text-indigo-400 btn-flat btn-rounded btn-icon btn-xs valign-text-bottom"><i class="glyphicon glyphicon-star-empty"></i></a>
										</li>
										<li class="text-left">
											<div class="text-semibold">0.5(inch)</div>
											<div class="text-bold"><span class="status-mark border-indigo position-left"></span>${p05}</div>
										</li>
									</ul>

									<div class="col-lg-10 col-lg-offset-1">
										<div class="content-group" id="total-online"></div>
									</div>
								</div>
								
								</div>
								
								<div class="row">	
									
									
									<div class="col-lg-2">
										<ul class="list-inline text-left">
											<li>
												<a href="#" class="btn border-teal text-teal btn-flat btn-rounded btn-icon btn-xs valign-text-bottom"><i class="glyphicon glyphicon-star-empty"></i></a>
											</li>
											<li class="text-left">
												<div class="text-semibold">0.75(inch)</div>
												<div class="text-bold"><span class="status-mark border-puprple position-left"></span>${p075}</div>
											</li>
										</ul>

										<div class="col-lg-10 col-lg-offset-1">
											<div class="content-group" id="new-visitors"></div>
										</div>
									</div>

									<div class="col-lg-2">
										<ul class="list-inline text-left">
											<li>
												<a href="#" class="btn border-warning-400 text-warning-400 btn-flat btn-rounded btn-icon btn-xs valign-text-bottom"><i class="glyphicon glyphicon-star-empty"></i></a>
											</li>
											<li class="text-left">
												<div class="text-semibold">1(inch)</div>
												<div class="text-bold"><span class="status-mark border-warning position-left"></span>${p1}</div>
											</li>
										</ul>

										<div class="col-lg-10 col-lg-offset-1">
											<div class="content-group" id="new-sessions"></div>
										</div>
									</div>
									
									<div class="col-lg-2">
										<ul class="list-inline text-left">
											<li>
												<a href="#" class="btn border-success-400 text-success-400 btn-flat btn-rounded btn-icon btn-xs valign-text-bottom"><i class="glyphicon glyphicon-star-empty"></i></a>
											</li>
											<li class="text-left">
												<div class="text-semibold">1.5(inch)</div>
												<div class="text-bold"><span class="status-mark border-success position-left"></span>${p15}</div>
											</li>
										</ul>

										<div class="col-lg-10 col-lg-offset-1">
											<div class="content-group" id="new-sessions"></div>
										</div>
									</div>
									
									
									<div class="col-lg-2">
										<ul class="list-inline text-left">
											<li>
												<a href="#" class="btn border-info-400 text-info-400 btn-flat btn-rounded btn-icon btn-xs valign-text-bottom"><i class="glyphicon glyphicon-star-empty"></i></a>
											</li>
											<li class="text-left">
												<div class="text-semibold">2(inch)</div>
												<div class="text-bold"><span class="status-mark border-info position-left"></span>${p2}</div>
											</li>
										</ul>

										<div class="col-lg-10 col-lg-offset-1">
											<div class="content-group" id="new-sessions"></div>
										</div>
									</div>
									
									<div class="col-lg-2">
										<ul class="list-inline text-left">
											<li>
												<a href="#" class="btn border-orange-400 text-orange-400 btn-flat btn-rounded btn-icon btn-xs valign-text-bottom"><i class="glyphicon glyphicon-star-empty"></i></a>
											</li>
											<li class="text-left">
												<div class="text-semibold">3(inch)</div>
												<div class="text-bold"><span class="status-mark border-orange position-left"></span>${p3}</div>
											</li>
										</ul>

										<div class="col-lg-10 col-lg-offset-1">
											<div class="content-group" id="new-sessions"></div>
										</div>
									</div>
									
									
									<div class="col-lg-2">
										<ul class="list-inline text-left">
											
											<li>
												<a href="#" class="btn border-orange-400 text-orange-400 btn-flat btn-rounded btn-icon btn-xs valign-text-bottom"><i class="glyphicon glyphicon-star-empty"></i></a>
											</li>
											
											<li class="text-left">
												<div class="text-semibold">4(inch)</div>
												<div class="text-bold"><span class="status-mark border-brown position-left"></span>${p4}</div>
											</li>
										</ul>

										<div class="col-lg-10 col-lg-offset-1">
											<div class="content-group" id="new-sessions"></div>
										</div>
									</div>
									
									
									
									
								</div>
								
								
								
								
								<div class="row">
								
								       <div class="col-lg-2">
										<ul class="list-inline text-left">
											
											<li>
												<a href="#" class="btn border-orange-400 text-orange-400 btn-flat btn-rounded btn-icon btn-xs valign-text-bottom"><i class="glyphicon glyphicon-star-empty"></i></a>
											</li>
											
											<li class="text-left">
												<div class="text-semibold">Other(inch)</div>
												<div class="text-bold"><span class="status-mark border-brown position-left"></span>${po}</div>
											</li>
										</ul>

										<div class="col-lg-10 col-lg-offset-1">
											<div class="content-group" id="new-sessions"></div>
										</div>
									</div>
									
									
									<div class="col-lg-2">
										<ul class="list-inline text-left">
											<li>
												<a href="#" class="btn border-indigo-400 text-indigo-400 btn-flat btn-rounded btn-icon btn-xs valign-text-bottom"><i class="glyphicon glyphicon-hand-left"></i></a>
											</li>
											<li class="text-left">
												<div class="text-semibold">Normal</div>
												<div class="text-bold"><span class="status-mark border-indigo position-left"></span>${pno}</div>
											</li>
										</ul>

										<div class="col-lg-10 col-lg-offset-1">
											<div class="content-group" id="total-online"></div>
										</div>
									</div>
									
									
									<div class="col-lg-2">
										<ul class="list-inline text-left">
											<li>
												<a href="#" class="btn border-warning-400 text-warning-400 btn-flat btn-rounded btn-icon btn-xs valign-text-bottom"><i class="glyphicon glyphicon-eye-open"></i></a>
											</li>
											<li class="text-left">
												<div class="text-semibold">Temporary</div>
												<div class="text-bold"><span class="status-mark border-indigo position-left"></span>${ptem}</div>
											</li>
										</ul>

										<div class="col-lg-10 col-lg-offset-1">
											<div class="content-group" id="total-online"></div>
										</div>
									</div>
									
									
									<div class="col-lg-2">
										<ul class="list-inline text-left">
											<li>
												<a href="#" class="btn border-indigo-400 text-indigo-400 btn-flat btn-rounded btn-icon btn-xs valign-text-bottom"><i class="glyphicon glyphicon-eye-open"></i></a>
											</li>
											<li class="text-left">
												<div class="text-semibold">Permanent</div>
												<div class="text-bold"><span class="status-mark border-indigo position-left"></span>${pper}</div>
											</li>
										</ul>

										<div class="col-lg-10 col-lg-offset-1">
											<div class="content-group" id="total-online"></div>
										</div>
									</div>
									
									
									<div class="col-lg-2">
										<ul class="list-inline text-left">
											<li>
												<a href="#" class="btn border-pink-400 text-pink-400 btn-flat btn-rounded btn-icon btn-xs valign-text-bottom"><i class="glyphicon glyphicon-eye-open"></i></a>
											</li>
											<li class="text-left">
												<div class="text-semibold">Defaulter</div>
												<div class="text-bold"><span class="status-mark border-indigo position-left"></span>${pdef}</div>
											</li>
										</ul>

										<div class="col-lg-10 col-lg-offset-1">
											<div class="content-group" id="total-online"></div>
										</div>
									</div>
									
									
									<div class="col-lg-2">
										<ul class="list-inline text-left">
											<li>
												<a href="#" class="btn border-success-400 text-success-400 btn-flat btn-rounded btn-icon btn-xs valign-text-bottom"><i class="glyphicon glyphicon-eye-open"></i></a>
											</li>
											<li class="text-left">
												<div class="text-semibold">Other</div>
												<div class="text-bold"><span class="status-mark border-indigo position-left"></span>${pcother}</div>
											</li>
										</ul>

										<div class="col-lg-10 col-lg-offset-1">
											<div class="content-group" id="total-online"></div>
										</div>
									</div>
									
									
								
								
								</div>
								
								
								
							</div>
								
								

								
							</div>

						</div>
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						<div class="col-lg-12">

						<div class="panel panel-flat">
							    
							    
							    <ul class="nav nav-lg nav-tabs nav-justified no-margin no-border-radius bg-success-400 border-top border-top-success-300">
								<li>
									<a href="#" class="text-size-small text-uppercase" >
										Collection Statistics
									</a>
								</li>
							   </ul><br>
							<div class="container-fluid">
								<div class="row text-center">
									
									<div class="row">
									
									<div class="col-md-3">
										<div class="content-group">
											<h5 class="text-semibold no-margin"><i class="glyphicon glyphicon-screenshot position-left text-indigo"></i><span>${totalConsumers}</span></h5>
											<span class="text-bold text-size-small">Total Customers</span>
										</div>
									</div>

									<div class="col-md-3">
										<div class="content-group">
											<h5 class="text-semibold no-margin"><i class="glyphicon glyphicon-eye-open position-left text-success"></i><span>${totalBilled}</span></h5>
											<span class="text-bold text-size-small">Total Billed</span>
										</div>
									</div>
									
									<%-- <c:if test="${branchcode==1115 || branchcode==1118}"> --%>
									<div class="col-md-3">
										<div class="content-group">
											<h5 class="text-semibold no-margin"><i class="glyphicon glyphicon-hand-up position-left text-orange"></i><span>NPR.<fmt:formatNumber type="number" minFractionDigits="2" value="${totalDemand}" /></span></h5>
											<span class="text-bold text-size-small">Total Demand</span>
										</div>
									</div>
									<%-- </c:if> --%>
									
									<div class="col-md-3">
										<div class="content-group">
											<h5 class="text-semibold no-margin"><i class="glyphicon glyphicon-folder-close position-left text-teal"></i><span>NPR.<fmt:formatNumber type="number" minFractionDigits="2" value="${totalCollection}" /></span></h5>
											<span class="text-bold text-size-small">Total Collection</span>
										</div>
									</div>
									
									</div>
									
									<div class="row">
									
									    <!-- <div class="chart-container">
											<div class="chart" id="c3-combined-chart1">
											
											</div>
										</div> -->
										
										<div class="chart-container">
											<div id="container123" style="padding-left: 10px;padding-right: 10px;"></div>
										</div>
										
										
										
										
										
										
									</div>
												
									
								</div>
							</div>
						</div>	
						
						</div>
						<div class="col-lg-12">	
						
						<div class="panel panel-flat">
							<div class="panel-body">
							<fieldset class="content-group" > 
							
							<legend class="text-bold">Ward Wise Billing Progress Metered : <span>${monthYear}</span></legend>
							<div class="table-responsive">
								<table class="table datatable-basic table-bordered">
									<thead>
										<tr class="bg-primary">
											<th>Ward No</th>
											<th>Reading&nbsp;Day</th>
											<th style="min-width: 200px;">Total Connections Ledger</th>
											<th>Billed</th>
											<th>Un Billed</th>
										</tr>
									</thead>
					
									<tbody>
										<c:forEach var="app" items="${wardData}">
											<tr>
												<td>${app.wardno}</td>
												<td>${app.reading_day}</td>
												<td>${app.totalbilled}</td>
												<td><a href='#' onclick="paymentHistoryPopUp('${app.wardno}',${app.reading_day==null?45:app.reading_day},1)" rel='tooltip' data-placement='right' data-original-title='Click here to get Connection History'  data-toggle="modal" data-target="#modal_default"><b>${app.billed}</b></a></td>
												<td><a href='#' onclick="paymentHistoryPopUp('${app.wardno}',${app.reading_day==null?45:app.reading_day},0)" rel='tooltip' data-placement='right' data-original-title='Click here to get Connection History'  data-toggle="modal" data-target="#modal_default"><b>${app.unbilled}</b></a></td>
												
											</tr>
									</c:forEach>
										
										
									</tbody>
									
									
								</table>
							</div>
							
							
							
							<legend class="text-bold">Ward Wise Billing Progress UnMetered : <span>${monthYear}</span></legend>
							<div class="table-responsive">
								<table class="table datatable-basic table-bordered">
									<thead>
										<tr class="bg-primary">
											<th>Ward No</th>
											<th style="min-width: 200px;">Total Connections Ledger</th>
											<th>Billed</th>
											<th>Un Billed</th>
										</tr>
									</thead>
					
									<tbody>
										<c:forEach var="app" items="${wardDataun}">
											<tr>
												<td>${app.wardno}</td>
												<td>${app.totalbilled}</td>
												<td><a href='#' onclick="paymentHistoryPopUp1('${app.wardno}',1)" rel='tooltip' data-placement='right' data-original-title='Click here to get Connection History'  data-toggle="modal" data-target="#modal_default"><b>${app.billed}</b></a></td>
												<td><a href='#' onclick="paymentHistoryPopUp1('${app.wardno}',0)" rel='tooltip' data-placement='right' data-original-title='Click here to get Connection History'  data-toggle="modal" data-target="#modal_default"><b>${app.unbilled}</b></a></td>
												
											</tr>
									</c:forEach>
										
										
									</tbody>
									
									
								</table>
							</div>
							
							
							
							
							
							
							
						</fieldset>
						
					   </div>
							
							</div>
						</div>
						</div>
						
						
						

					

						
		<div id="modal_default" class="modal fade">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal">&times;</button>
								<h5 class="modal-title">Connection History</h5>
							</div>

							<div class="modal-body">
								
								<table class="table datatable-basic table-bordered" id="popupPayment">
									<thead>
										<tr>
											<th></th>
											<th>CON&nbsp;No</th>
											<th style="min-width: 140px;">Area&nbsp;No</th>
											<th>R&nbsp;Day</th>
											<th style="min-width: 180px;">Name</th>
											<th style="min-width: 180px;">Name(Nep)</th>
											<th>Tap</th>
											<th>Category</th>
											<th>Con&nbsp;Type</th>
											<th></th>
											
										</tr>
									</thead>
									<tbody id="viewPayHistotytbody">
										
									</tbody>
								</table>
								
								
							</div>

							<div class="modal-footer">
								
							</div>
						</div>
					</div>
				</div>			
					
				
				
				
				<!-- Basic modal -->
				<div id="modal_defaultmon" class="modal fade">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal">&times;</button>
								<h5 class="modal-title">Login History</h5>
							</div>

							<div class="modal-body">
								
								<table class="table datatable-basic table-bordered" id="usermonitor">
									<thead>
										<tr>
											<th></th>
											<th>Name</th>
											<th>Designation</th>
											<th>Login Time</th>
											<th>Logout Time</th>
											<!-- <th>Duration</th> -->
											<th>Status</th>
										</tr>
									</thead>
									<tbody id="viewuserMonitortbody">
										
									</tbody>
								</table>
								
							</div>

							<div class="modal-footer">
								
							</div>
						</div>
					</div>
				</div>
				<!-- /basic modal -->	




<script>

$(document).ready(function(){   
	
	$('#dashboard').addClass('active');	
	getGraphicalViewForMonth();
	
	var wap=${ncwap};
	var app=${ncapp};
	var to=(parseInt(wap)+parseInt(app));
	$('#connectionidspan').html(to);
	
	/* var activeMod="${activeModulesNew}";
	var module="Dashboard";
	var check=activeMod.indexOf(module) > -1;
	
	if(check){
	
	}else{
		window.location.href="./accessDenied";
	} */
	
	
	
}); 

var dates=[], payments=[];
function getGraphicalViewForMonth()
{
	$.ajax({
				type : "GET",
				url : "./getGraphicalViewForMonth",
				dataType : "json",
				async:false,	
				cache:false,
				success : function(response)
				{
					dates =[], payments = [];
					for(var i=0;i<response.length;i++)
					{
						dates.push(response[i][0]);
						payments.push(response[i][1]);
					}
				}
			});
            displayGraph(dates,payments);
}; 



function monitorUsers(){
	
	$("#viewuserMonitortbody").empty();

	var tableData = "";
	$.ajax
	({			
		type : "GET",
		url : "./users/monitorUsers/",
		async: false,
		dataType : "JSON",
		success : function(response) 
		{	    
			for ( var s = 0, len = response.length; s < len; ++s) {
		              	var obj = response[s];
		              	
		              	tableData += "<tr>"
								+"<td>"+(s+1)+"</td>" 
								+"<td>"+obj.name+"</td>"
								+"<td>"+obj.designation+"</td>"
								+"<td>"+obj.logintime+"</td>"
								+"<td>"+obj.logouttime+"</td>"
								/* +"<td>"+obj.duration+"</td>" */
								+"<td>"+obj.loginstatus+"</td>"
								+"</tr>";
				                
				     }
				
				$('#viewuserMonitortbody').append(tableData);
				loadSearchFilter1('usermonitor',tableData,'viewuserMonitortbody');
		}
	

	});

	
}



function billPrint(id,connection_no){
	 
	$.ajax({
	  type: "GET",
	  url: "./billing/printBillByConNo/"+connection_no,
	  dataType: "JSON",
	  async   : false,
	  cache:false,
	  success: function(response){
		  var result=response;
		  
		    var printW = window.open("");
		      var is_chrome = Boolean(printW.chrome);
			  var isPrinting = false;
		  
		  var branch="${BranchName}";
		  
		  for(var i=0;i<result.length;i++)
		  {
			 
			 var data=result[i];
			 var wcc=data.water_charges==null?parseFloat(0):data.water_charges;
			 var scc=data.sw_charges==null?parseFloat(0):data.sw_charges;
			 var mrc=data.mtr_rent==null?parseFloat(0):data.mtr_rent;
			 
			 var cmt=parseFloat(parseFloat(wcc)+parseFloat(scc)+parseFloat(mrc)).toFixed(2);
			 
			 var addr="";
			 var address=data.address_eng;
			 if(address==null || address=="" || address=="undefined"){
				 
			 }else{
				 addr=data.address_eng;;
			 }
			 var board=data.boardbalance==null?parseFloat(0):data.boardbalance;
			 
		     var tolArrs= parseFloat(parseFloat(data.arrears)+parseFloat(board)).toFixed(2);
			 var netAmt= parseFloat(parseFloat(tolArrs)+parseFloat(cmt)).toFixed(2);
			 var html = "<div>";
			    html += "  <table style='undefined;table-layout: fixed; width: 325px;font-size:11pt;line-height:16px;'>"
		   	   
			   +" <colgroup>"
			   +" <col style='width: 145px'>"
			   +" <col style='width: 185px'>"
			   +"</colgroup>"
			   
			   +"  <tr>"
			   +"    <th><br>Kathmandu Upatyaka Khanepani Limited<br><br>Branch:"+branch+" <br>PAN NO. :600041601</th>"
			   +"    <th><img src='./resources/images/kukl_new.png' width='130px' height='130px' alt='' align='right'></th>"
			   +"  </tr>"
			   
			   +"  <tr>"
			   +"    <th colspan='2'><hr style='height: 2px;background-color: black;'></th>"
			   +"  </tr>"
			 
			   
			   +"  <tr>"
			   +"  <th colspan='2' align='center'><u>METER READING BILL: "+data.monthDesc+"</u></th>"
			   +"  </tr>"
			   
			   +"  <tr>"
			   +"    <td>Bill No.<br></td>"
			   +"    <td>:&nbsp;&nbsp;<b>"+data.billno+"</b></td>"
			   +"  </tr>"
			   
			   +"  <tr>"
			   +"    <td>Customer No.<br></td>"
			   +"    <td>:&nbsp;&nbsp;<b>"+data.customer_id+"</b></td>"
			   +"  </tr>"
			   
			   +"  <tr>"
			   +"    <td>Connection No.<br></td>"
			   +"    <td>:&nbsp;&nbsp;<b>"+data.connection_no+"</b></td>"
			   +"  </tr>"
			   
			   +"  <tr>"
			   +"    <td>Area No.<br></td>"
			   +"    <td>:&nbsp;&nbsp;<b>"+data.area_no+"</b></td>"
			   +"  </tr>"
			   
			   +"  <tr>"
			   +"    <td><b>Name</b><br></td>"
			   +"    <td>:&nbsp;&nbsp;"+data.name_eng+"</td>"
			   +"  </tr>"
			   
			   +"  <tr>"
			   +"    <td>Address<br></td>"
			   +"    <td>:&nbsp;&nbsp;"+addr+"</td>"
			   +"  </tr>"
			   
			   
			   +"  <tr>"
			   +"    <td>Connection Type<br></td>"
			   +"    <td>:&nbsp;&nbsp;"+data.con_type+"</td>"
			   +"  </tr>"
			   
			   +"  <tr>"
			   +"    <td>Tap Size<br></td>"
			   +"    <td>:&nbsp;&nbsp;"+data.pipe_size+"</td>"
			   +"  </tr>"
			
			   
			   +"  <tr>"
			   +"    <td>Reading Date<br></td>"
			   +"    <td>:&nbsp;&nbsp;<b>"+data.rdng_date_nep+"</b></td>"
			   +"  </tr>"
			  
			   +"  <tr>"
			   +"    <td>Present Reading(KL)<br></td>"
			   +"    <td>:&nbsp;&nbsp;"+data.present_reading+"</td>"
			   +"  </tr>"
			   
			   +"  <tr>"
			   +"    <td>Previous Reading(KL)<br></td>"
			   +"    <td>:&nbsp;&nbsp;"+data.previous_reading+"</td>"
			   +"  </tr>"
			   
			   +"  <tr>"
			   +"    <td>Consumption(KL)<br></td>"
			   +"    <td>:&nbsp;&nbsp;"+data.consumption+"</td>"
			   +"  </tr>"
			   
			   +"  <tr>"
			   +"    <td>Additinal Units(KL)<br></td>"
			   +"    <td>:&nbsp;&nbsp;"+data.additionalUnits+"</td>"
			   +"  </tr>"
			   
			   +"  <tr>"
			   +"    <td>Minimum Charges<br></td>"
			   +"    <td>:&nbsp;&nbsp;<b>"+data.minimum_charges+"</b></td>"
			   +"  </tr>"
			   
			   +"   <tr>"
			   +"    <td>Additional Charges<br></td>"
			   +"    <td>:&nbsp;&nbsp;<b>"+data.additional_charges+"</b></td>"
			   +"  </tr>"
			   
			   +"  <tr>"
			   +"    <td>Total Water Charges<br></td>"
			   +"    <td>:&nbsp;&nbsp;<b>"+data.water_charges+"</b></td>"
			   +"  </tr>"
			   
			   +"  <tr>"
			   +"    <td>Sewerage Charges<br></td>"
			   +"    <td>:&nbsp;&nbsp;<b>"+data.sw_charges+"</b></td>"
			   +"  </tr>"
			   
			   +"  <tr>"
			   +"    <td>Meter Rent Charges<br></td>"
			   +"    <td>:&nbsp;&nbsp;<b>"+data.mtr_rent+"</b></td>"
			   +"  </tr>"
			 
			   +"  <tr>"
			   +"    <td>Miscellaneous<br></td>"
			   +"    <td>:&nbsp;&nbsp;0</td>"/*"+data.other+"  */
			   +"  </tr>"
			   
			   +"  <tr>"
			   +"    <td>Current Month Total<br></td>"
			   +"    <td>:&nbsp;&nbsp;<b>"+cmt+"</b></td>"
			   +"  </tr>"
			   
			   +"  <tr>"
			   +"    <td>Arrears<br></td>"
			   +"    <td>:&nbsp;&nbsp;<b>"+tolArrs+"</b></td>"
			   +"  </tr>"
			
			   
			   +"  <tr>"
			   +"    <td><b>Total Amount Rs.</b><br></td>"
			   +"    <td>:&nbsp;&nbsp;<b>"+netAmt+"</b></td>"
			   +"  </tr>"
			   
			   
			   +"  <tr>"
			   +"    <td>Observation<br></td>"
			   +"    <td>:&nbsp;&nbsp;"+data.observation+"</td>"
			   +"  </tr>"
			   
			   +"  <tr>"
			   +"    <td>Meter Reader'S Name<br></td>"
			   +"    <td>:&nbsp;&nbsp;"+data.mrname+"</td>"
			   +"  </tr>"
			   
			  
			   
			   +"  <tr>"
			   +"    <td colspan='2'><center>*****Computer Generated Bill*****</center></td>"
			   +"  </tr>"
			   
			   +"  <tr>"
			   +"    <td>&nbsp;</td>"
			   +"    <td>&nbsp;</td>"
			   +"  </tr>"
			   
			   +"  <tr>"
			   +"    <td>&nbsp;</td>"
			   +"    <td><center>.</center></td>"
			   +"  </tr>"
			   
			   +"</table><br><br><br>";
			    html += "</div>";

			    printW.document.write(html);
			    
			   
			   
			    
		  }	
		  
		   /*  printW.document.close();
		    printW.focus();
		    printW.print();
		    printW.close(); */
		  if (is_chrome) {
		    	printW.onload = function() { // wait until all resources loaded 
		            isPrinting = true;
		            printW.focus(); // necessary for IE >= 10
		            printW.print();  // change window to mywindow
		            printW.close();// change window to mywindow
		            isPrinting = false;
		        };
		        setTimeout(function () { if(!isPrinting){printW.print();printW.close();} }, 300);
		    }
		    else {
		    	printW.document.close(); // necessary for IE >= 10
		    	printW.focus(); // necessary for IE >= 10
		    	printW.print();
		    	printW.close();
		    }

		    return true;

	  }
	
	   
	    
	});
}




function displayGraph(dates,payments)
{
	  $(function () {
	       
	    	Highcharts.chart('container123', {
	            chart: {
	                zoomType: 'xy'
	            },
	            title: {
	                text: 'Daily Collection'
	            },
	           
	            xAxis: [{
	                categories: dates,
	                crosshair: true
	            }],
	            yAxis: [{ // Primary yAxis
	               
	                title: {
	                	text: '',
	                   
	                }
	              }, { // Secondary yAxis
	                title: {
	                    text: 'Collection Amount',
	                    style: {
	                        color: Highcharts.getOptions().colors[0]
	                    }
	                },
	                labels: {
	                    format: 'Rs {value}',
	                    style: {
	                        color: Highcharts.getOptions().colors[0]
	                    }
	                },
	                opposite: false
	            }],
	            tooltip: {
	                shared: true
	            },
	            series: [{
	                name: 'Amount',
	                type: 'column',
	                yAxis: 1,
	                data: payments,
	                tooltip: {
	                    valueSuffix: ' Rs'
	                }

	            }]
	        });
	    });
}


function paymentHistoryPopUp(wardno,reading_day,value){
	
	
	$("#viewPayHistotytbody").empty();
	
	$.ajax
	({			
		type : "GET",
		url : "./billing/viewConnectionHistory/"+wardno+"/"+reading_day+"/"+value,
		async: false,
		dataType : "JSON",
		success : function(response) 
		{	 
			var tableData = "";
			for ( var s = 0, len = response.length; s < len; ++s) {
		              	var obj = response[s];
		              	var arean=obj.area_no;
		              	
		              	if(arean==null || arean=="null"){
		              		arean="";
		              	}
		              	
		              	tableData += "<tr>"
								+"<td>"+(s+1)+"</td>"
								+"<td>"+obj.connection_no+"</td>"
								+"<td>"+arean+"</td>"
								+"<td>"+obj.reading_day+"</td>"
								+"<td>"+obj.name_eng+"</td>"
								+"<td>"+obj.name_nep+"</td>"
								+"<td>"+obj.pipe_size+"</td>"
								+"<td>"+obj.connection_category+"</td>"
								+"<td>"+obj.connection_type+"</td>"
								+"<td><a href='#' onclick='return billPrint(this.id,\""+obj.connection_no+"\");'><i title='Click To Print Bill'></i>Print&nbsp;Bill</a></td>"
								+"</tr>";
				                
				     }
				$('#viewPayHistotytbody').append(tableData);
				loadSearchFilter1('popupPayment',tableData,'viewPayHistotytbody');
		}
	

	});
	
}



function paymentHistoryPopUp1(wardno,value){
	
	
	$("#viewPayHistotytbody").empty();
	
	var tableData = "";
	$.ajax
	({			
		type : "GET",
		url : "./billing/viewConnectionHistory/"+wardno+"/"+value,
		async: false,
		dataType : "JSON",
		success : function(response) 
		{	    
			for ( var s = 0, len = response.length; s < len; ++s) {
		              	var obj = response[s];
		              	
						var arean=obj.area_no;
		              	
		              	if(arean==null || arean=="null"){
		              		arean="";
		              	}
		              	
		              	
		              	tableData += "<tr>"
								+"<td>"+(s+1)+"</td>"
								+"<td>"+obj.connection_no+"</td>"
								+"<td>"+arean+"</td>"
								+"<td>"+obj.reading_day+"</td>"
								+"<td>"+obj.name_eng+"</td>"
								+"<td>"+obj.name_nep+"</td>"
								+"<td>"+obj.pipe_size+"</td>"
								+"<td>"+obj.connection_category+"</td>"
								+"<td>"+obj.connection_type+"</td>"
								+"<td><a href='#' onclick='return billPrint(this.id,\""+obj.connection_no+"\");'><i title='Click To Print Bill'></i>Print&nbsp;Bill</a></td>"
								+"</tr>";
				                
				     }
				$('#viewPayHistotytbody').append(tableData);
				loadSearchFilter1('popupPayment',tableData,'viewPayHistotytbody');
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


<style>

legend {
	text-transform: none;
	font-size: 16px;
	border-color: #F01818;
}

.datatable-header, .datatable-footer {
    padding: 0px 0px 0 0px;
}

.dataTables_filter {
    /* display: none; */
    float: left;
    margin: 0 0 20px 20px;
    position: relative;
}
.dataTables_length > label {
    margin-bottom: 0;
    display: none;
}

.modal.fade .modal-dialog {
width: 1200px;
}

</style>