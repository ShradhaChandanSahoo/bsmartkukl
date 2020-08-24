   
  <%@include file="/WEB-INF/decorators/taglibs.jsp"%> 
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<script type="text/javascript">

var samePeriodDate="";
$(window).load(function() {
	
	var makeDate = new Date();
    makeDate = new Date(makeDate.setMonth(makeDate.getMonth() - 2));
    
	samePeriodDate=moment(makeDate).format('DD-MMM-YY');
	$("#samePeriodmonthm").html("("+samePeriodDate+")");
	var cDate=new Date();
	$("#currentdatem").html("("+moment(cDate).format('DD-MMM-YY')+")")
	 $.ajax({
	        type:"GET",
	        url:"./getMiscelleneousPaymentData",
	        dataType:"json",
	        success:function(response){
	    $.each(response, function(key, val) {

	            	$("#total_customer_paid_misc").html(parseInt(val.total_customer_paid));
	            	$("#total_coll_misc").html(parseFloat(val.total_coll));
	            	$("#new_connection_tap").html(parseFloat(val.nc_tap));
	            	$("#new_connection_dep").html(parseFloat(val.nc_deposit));
	            	$("#meter_value").html(parseFloat(val.m_value));
	            	$("#temp_hole_block").html(parseFloat(val.hole_block));
	            	$("#name_change").html(parseFloat(val.name_change));
	            	$("#illegal_penalty").html(parseFloat(val.illigal_con));
	            	$("#new_con_inst").html(parseInt(val.nc_inst));
	            	$("#other_payment").html(parseFloat(val.other));
	            	
	            	
	            	$("#total_customer_paid_misc_m").html(parseFloat(val.total_customer_paid_m));
	            	$("#total_coll_misc_m").html(parseFloat(val.total_coll_m));
	            	$("#new_connection_tap_m").html(parseFloat(val.nc_tap_m));
	            	$("#new_connection_dep_m").html(parseFloat(val.nc_deposit_m));
	            	$("#meter_value_m").html(parseFloat(val.m_value_m));
	            	$("#temp_hole_block_m").html(parseFloat(val.hole_block_m));
	            	$("#name_change_m").html(parseFloat(val.name_change_m));
	            	$("#illegal_penalty_m").html(parseFloat(val.illigal_con_m));
	            	$("#new_con_inst_m").html(parseFloat(val.nc_inst_m));
	            	$("#other_payment_m").html(parseFloat(val.other_m));
	            	 
	            });
	    
	        }
	    });
		
});

function showrecordminusColl(param)
{
	$("#drilldownColl").show();
    $("#drilldown1Coll").hide();
	$("#popuplocationwiseBody1Coll").hide();
	$("#popuplocationwiseBody2Coll").hide();
	
	$("#popuplocationwiseBodydd1Coll").hide();
	$("#popuplocationwiseBodydd2Coll").hide();
	$("#popuplocationwiseBodydd3Coll").hide();
	$("#popuplocationwiseBodycc1Coll").hide();
	$("#popuplocationwiseBodycc2Coll").hide();
	}

function showrecordCCminColl(param)
{$("#testCC1Coll").show();
$("#testCC2Coll").hide();
	$("#popuplocationwiseBodycc1Coll").hide();
$("#popuplocationwiseBodycc2Coll").hide();
	
	
	}
	
function showrecordDDminColl(param)
{
	$("#testDD1Coll").show();
	$("#testDD2Coll").hide();
	$("#popuplocationwiseBodydd1Coll").hide();
	$("#popuplocationwiseBodydd2Coll").hide();
	$("#popuplocationwiseBodydd3Coll").hide();
	
	}
function showrecordColl(param){

	$("#drilldownColl").hide();
	$("#drilldown1Coll").show();
	$("#popuplocationwiseBody1Coll").show();
	$("#popuplocationwiseBody2Coll").show();
	var sitecode="2109";
	$.ajax({
	    type:"GET",
	    url:"./getCollectionLocationWiseDrillDown",
	    dataType:"json",
	    data:{
	    	sitecode:sitecode,
	    },
	    success:function(response){
	    	
	    	var htmldata="";
	    	
	    	var html1="<tr>"+
		     "<td style='width: 1px;'><span id='testDD1Coll' onclick='return showrecordddColl(this.id);' class='fa fa-fw fa-plus-circle'  style='color:green;'></span><span id='testDD2Coll' onclick='return showrecordDDminColl(this.id);' class='fa fa-fw fa-minus-circle'  style='color:red;display:none;'></span></td>"+
		      "<td>"+response[0].locationName+"</td>"+
			  "<td>"+response[0].total_consumers+"</td>"+
			  "<td>"+test(response[0].consumption)+"</td>"+
			  "<td>"+nFormatter(response[0].assessment)+"</td>"+
			  "<td>"+nFormatter(response[0].payments)+"</td>"+
			  
			   "</tr>"
		
			var html2="<tr>"+
		     "<td style='width: 1px;'><span id='testCC1Coll' onclick='return showrecordccColl(this.id);' class='fa fa-fw fa-plus-circle'  style='color:green;'></span><span id='testCC2Coll' onclick='return showrecordCCminColl(this.id);' class='fa fa-fw fa-minus-circle'  style='color:red;display:none;'></span></td>"+
		      "<td>"+response[1].locationName+"</td>"+
			  "<td>"+response[1].total_consumers+"</td>"+
			  "<td>"+test(response[1].consumption)+"</td>"+
			  "<td>"+nFormatter(response[1].assessment)+"</td>"+
			  "<td>"+nFormatter(response[1].payments)+"</td>"+
			   "</tr>"  
			   
			  
			   $("#popuplocationwiseBody1Coll").html(html1);
			   $("#popuplocationwiseBody2Coll").html(html2);
			   
	                 
	    }
	}); 

 
	
}

function showrecordccColl(param){
	$("#testCC1Coll").hide();
	$("#testCC2Coll").show();
	$("#popuplocationwiseBodycc1Coll").show();
	$("#popuplocationwiseBodycc2Coll").show();
	var sitecode="21091";
	$.ajax({
	    type:"GET",
	    url:"./getCollectionLocationWiseDrillDown",
	    dataType:"json",
	    data:{
	    	sitecode:sitecode,
	    },
	    success:function(response){
	    	
	    	
	    	var html1="<tr>"+
		     "<td style='width: 1px;'></td>"+
		      "<td>"+response[0].locationName+"</td>"+
			  "<td>"+response[0].total_consumers+"</td>"+
			  "<td>"+test(response[0].consumption)+"</td>"+
			  "<td>"+nFormatter(response[0].assessment)+"</td>"+
			  "<td>"+nFormatter(response[0].payments)+"</td>"+
			   "</tr>"
			var html2="<tr>"+
		     "<td style='width: 1px;'></td>"+
		      "<td>"+response[1].locationName+"</td>"+
			  "<td>"+response[1].total_consumers+"</td>"+
			  "<td>"+test(response[1].consumption)+"</td>"+
			  "<td>"+nFormatter(response[1].assessment)+"</td>"+
			  "<td>"+nFormatter(response[1].payments)+"</td>"+
			   "</tr>"  
			   
			  $("#popuplocationwiseBodycc1Coll").html(html1);
	          $("#popuplocationwiseBodycc2Coll").html(html2);
			 
	                 
	    }
	}); 
	
}

function showrecordddColl(param){
$("#testDD2Coll").show();
$("#testDD1Coll").hide();
$("#popuplocationwiseBodydd1Coll").show();
$("#popuplocationwiseBodydd2Coll").show();
$("#popuplocationwiseBodydd3Coll").show();
var sitecode="21092";
$.ajax({
    type:"GET",
    url:"./getCollectionLocationWiseDrillDown",
    dataType:"json",
    data:{
    	sitecode:sitecode,
    },
    success:function(response){
    	var htmldata="";
    	for(var i=0;i<response.length;i++){
    	var html1="<tr>"+
	     "<td style='width: 1px;'></td>"+
	      "<td>"+response[i].locationName+"</td>"+
		  "<td>"+response[i].total_consumers+"</td>"+
		  "<td>"+test(response[i].consumption)+"</td>"+
		  "<td>"+nFormatter(response[i].assessment)+"</td>"+
		  "<td>"+nFormatter(response[i].payments)+"</td>"+
		   "</tr>"
		  htmldata+=html1;
    	}
		
		   $("#popuplocationwiseBodydd1Coll").html(htmldata);
      
		 
		   
                 
    }
}); 

}
var html="";
var locationCollection="";
var NameCollection="";
function locationwiseCollmisc(){
	var sitecode="ALL";
	/* $('#popuplocationwise').attr("data-toggle", "modal"); */
	 //$('#popupLocationwiseColl').attr("data-target","#modal_fullCollection_misc"); 
	 
	$.ajax({
			type : "GET",
			url : "./getCollectionLocationWiseMonthlyCollectionMisc",
			dataType : "json",
			data : {
				sitecode : sitecode,
			},
			success : function(response) {
				var htmldata = "";

				for (var i = 0; i < response.length; i++) {
					var obj = response[i];
					
					htmldata += "<tr>" +
					"<td>" + obj.brach + "</td>" + 
					"<td>" + obj.total_customer_paid + "</td>" + 
					"<td>" + obj.total_coll + "</td>" + 
					"<td>" + obj.nc_tap + "</td>" + 
					"<td>" + obj.nc_deposit + "</td>" + 
					"<td>" + obj.m_value + "</td>" + 
					"<td>" + obj.hole_block + "</td>" + 
					"<td>" + obj.name_change + "</td>" + 
					"<td>" + obj.illigal_con + "</td>" + 
					"<td>" + obj.nc_inst + "</td>" + 
					"<td>" + obj.other + "</td></tr>";
				}
				$("#popuplocationwiseBodyCollMisc").append(htmldata);
				
			}
		});

	}

	function TariffwiseColl() {

		/* $('#popuplocationwise').attr("data-toggle", "modal"); */
		$('#popupTariffwiseColl').attr("data-target",
				"#modal_fulltariffCollection");
		$.ajax({
			type : "GET",
			url : "./getCollectionTariffWiseDrillDown",
			dataType : "json",
			success : function(response) {
				var htmlTariff = "";

				for (var i = 0; i < response.length; i++) {
					var html = "<tr>" + "<td>" + response[i].tariffDescription
							+ "</td>" + "<td>" + response[i].total_consumers
							+ "</td>" + "<td>" + test(response[i].consumption)
							+ "</td>" + "<td>"
							+ nFormatter(response[i].assessment) + "</td>"
							+ "<td>" + nFormatter(response[i].payments)
							+ "</td>" + "</tr>"
					htmlTariff += html;
				}
				$("#popupTariffWiseBodyColl").html(htmlTariff);
			},
		});
	}
</script>

<div class="container-fluid">
	<div class="row">
		<!-- Assessment Payment starts -->

		<div class="col-lg-12">
			<!--8 Assessment Payment  -->
			<div class="panel panel-flat" style="height: 500px; ">
				<div class="panel-heading" style="height:50px; background-color: #1fba9f;">
					<h3 class="panel-title text-bold" align="center" style="margin-top: -10px;">Miscellaneous Collection Details</h3>
					<div class="heading-elements">
					<ul class="icons-list">
					                		<li class="dropdown">
					                			<a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="icon-menu7"></i> <span class="caret"></span></a>
												<ul class="dropdown-menu dropdown-menu-right">
													<li><a href="#"><i class="icon-sync"></i> Update data</a></li>
												</ul>
					                		</li>
					                	</ul>
					</div>
				</div>

				<div class="row">
					<div class="container-fluid">
						<div class="panel-heading">
							<h6 class="panel-title">Todays Collection Details&nbsp;<span id="currentdatem" style="color: green;"></span></h6>

						</div>
						<div class="container-fluid">

							<div class="row text-center">
								<div class="col-md-2">
									<div class="content-group">
										<h5 class="text-semibold no-margin">
											<i class="icon-add position-left text-slate"></i> <span
												class="Count21" id="total_customer_paid_misc"></span>
										</h5>
										<span class="text-muted text-size-small">Total
											Consumers paid</span>
									</div>
								</div>

								<div class="col-md-2">
									<div class="content-group">
										<h5 class="text-semibold no-margin">
											<i class="icon-cash3 position-left text-slate"></i> <span
												class="Count2" id="total_coll_misc"></span><span id="billcollass"></span>
										</h5>
										<span class="text-muted text-size-small">Total Payments</span>
									</div>
								</div>

								
								<div class="col-md-2">
									<div class="content-group">
										<h5 class="text-semibold no-margin">
											<i class="icon-cash3 position-left text-slate"></i> <span
												class="Count2" id="new_connection_tap"></span><span id="billcollass"></span>
										</h5>
										<span class="text-muted text-size-small">New Connection Tap</span>
									</div>
								</div>
								<div class="col-md-2">
									<div class="content-group">
										<h5 class="text-semibold no-margin">
											<i class="icon-cash3 position-left text-slate"></i> <span
												class="Count2" id="new_connection_dep"></span><span id="billasspay"></span>
										</h5>
										<span class="text-muted text-size-small">New Connection Deposit</span>
									</div>
								</div>
								<div class="col-md-2">
									<div class="content-group">
										<h5 class="text-semibold no-margin">
											<i class="icon-cash3 position-left text-slate"></i> <span
												class="Count2" id="meter_value"></span><span id="billasspay"></span>
										</h5>
										<span class="text-muted text-size-small">Meter value</span>
									</div>
								</div>
								<div class="col-md-2">
									<div class="content-group">
										<h5 class="text-semibold no-margin">
											<i class="icon-cash3 position-left text-slate"></i> <span
												class="Count2" id="temp_hole_block"></span><span id="billasspay"></span>
										</h5>
										<span class="text-muted text-size-small">Temp Hole Block</span>
									</div>
								</div>
								<div class="col-md-2">
									<div class="content-group">
										<h5 class="text-semibold no-margin">
											<i class="icon-cash3 position-left text-slate"></i> <span
												class="Count2" id="name_change"></span><span id="billasspay"></span>
										</h5>
										<span class="text-muted text-size-small">Name Change</span>
									</div>
								</div>
								<div class="col-md-2">
									<div class="content-group">
										<h5 class="text-semibold no-margin">
											<i class="icon-cash3 position-left text-slate"></i> <span
												class="Count2" id="illegal_penalty"></span><span id="billasspay"></span>
										</h5>
										<span class="text-muted text-size-small">Illegal Connection Penalty</span>
									</div>
								</div>
								<div class="col-md-2">
									<div class="content-group">
										<h5 class="text-semibold no-margin">
											<i class="icon-cash3 position-left text-slate"></i> <span
												class="Count2" id="new_con_inst"></span><span id="billasspay"></span>
										</h5>
										<span class="text-muted text-size-small">New Connection Installation</span>
									</div>
								</div>
								<div class="col-md-2">
									<div class="content-group">
										<h5 class="text-semibold no-margin">
											<i class="icon-cash3 position-left text-slate"></i> <span
												class="Count2" id="other_payment"></span><span id="billasspay"></span>
										</h5>
										<span class="text-muted text-size-small">Others</span>
									</div>
								</div>
							</div>
						</div>

						

					</div>
				</div>



				<div class="row">
					<div class="container-fluid">
						<div class="panel-heading">
							<h6 class="panel-title">Current Month Collection Details&nbsp;<span id="samePeriodmonthm" style="color:  green;"></span></h6>

						</div>
						<div class="container-fluid">

							<div class="row text-center">
								<div class="col-md-2">
									<div class="content-group">
										<h5 class="text-semibold no-margin">
											<i class="icon-add position-left text-slate"></i> <span
												class="Count21" id="total_customer_paid_misc_m"></span>
										</h5>
										<span class="text-muted text-size-small">Total
											Consumers paid</span>
									</div>
								</div>

								<div class="col-md-2">
									<div class="content-group">
										<h5 class="text-semibold no-margin">
											<i class="icon-cash3 position-left text-slate"></i> <span
												class="Count2" id="total_coll_misc_m"></span><span id="billcollass"></span>
										</h5>
										<span class="text-muted text-size-small">Total Payments</span>
									</div>
								</div>

								
								<div class="col-md-2">
									<div class="content-group">
										<h5 class="text-semibold no-margin">
											<i class="icon-cash3 position-left text-slate"></i> <span
												class="Count2" id="new_connection_tap_m"></span><span id="billcollass"></span>
										</h5>
										<span class="text-muted text-size-small">New Connection Tap</span>
									</div>
								</div>
								<div class="col-md-2">
									<div class="content-group">
										<h5 class="text-semibold no-margin">
											<i class="icon-cash3 position-left text-slate"></i> <span
												class="Count2" id="new_connection_dep_m"></span><span id="billasspay"></span>
										</h5>
										<span class="text-muted text-size-small">New Connection Deposit</span>
									</div>
								</div>
								<div class="col-md-2">
									<div class="content-group">
										<h5 class="text-semibold no-margin">
											<i class="icon-cash3 position-left text-slate"></i> <span
												class="Count2" id="meter_value_m"></span><span id="billasspay"></span>
										</h5>
										<span class="text-muted text-size-small">Meter value</span>
									</div>
								</div>
								<div class="col-md-2">
									<div class="content-group">
										<h5 class="text-semibold no-margin">
											<i class="icon-cash3 position-left text-slate"></i> <span
												class="Count2" id="temp_hole_block_m"></span><span id="billasspay"></span>
										</h5>
										<span class="text-muted text-size-small">Temp Hole Block</span>
									</div>
								</div>
								<div class="col-md-2">
									<div class="content-group">
										<h5 class="text-semibold no-margin">
											<i class="icon-cash3 position-left text-slate"></i> <span
												class="Count2" id="name_change_m"></span><span id="billasspay"></span>
										</h5>
										<span class="text-muted text-size-small">Name Change</span>
									</div>
								</div>
								<div class="col-md-2">
									<div class="content-group">
										<h5 class="text-semibold no-margin">
											<i class="icon-cash3 position-left text-slate"></i> <span
												class="Count2" id="illegal_penalty_m"></span><span id="billasspay"></span>
										</h5>
										<span class="text-muted text-size-small">Illegal Connection Penalty</span>
									</div>
								</div>
								<div class="col-md-2">
									<div class="content-group">
										<h5 class="text-semibold no-margin">
											<i class="icon-cash3 position-left text-slate"></i> <span
												class="Count2" id="new_con_inst_m"></span><span id="billasspay"></span>
										</h5>
										<span class="text-muted text-size-small">New Connection Installation</span>
									</div>
								</div>
								<div class="col-md-2">
									<div class="content-group">
										<h5 class="text-semibold no-margin">
											<i class="icon-cash3 position-left text-slate"></i> <span
												class="Count2" id="other_payment_m"></span><span id="billasspay"></span>
										</h5>
										<span class="text-muted text-size-small">Others</span>
									</div>
								</div>

							</div>
						</div>
						<!-- <div class="container-fluid">
							<div class="row text-center">

								<div class="col-md-3">
									<div class="content-group">
										<h5 class="text-semibold no-margin">
											<i class="icon-mobile position-left text-slate"></i><span
												class="Count21" id="total_Online_txnPrevious"></span>
										</h5>
										<span class="text-muted text-size-small">Online
											Transaction</span>
									</div>
								</div>

								<div class="col-md-3">
									<div class="content-group">
										<h5 class="text-semibold no-margin">
											<i class="icon-piggy-bank position-left text-slate"></i><span
												class="Count2" id="total_Online_amtPrevious"></span><span id="billassonlinePr"></span>
										</h5>
										<span class="text-muted text-size-small">Online
											Transaction Amount</span>
									</div>
								</div>
							</div>
						</div> -->





					</div>

				</div>

					<div class="text-center">
						<button type="button"
							class="btn bg-teal-400 btn-labeled btn-rounded"
							data-toggle="modal" data-target="#modal_fullCollection_misc"
							onclick="return locationwiseCollmisc();">
							<b><i class="icon-location4"></i></b> Location Wise
						</button>
					</div>

			</div>
		</div>
		
	</div>
	
</div>

<div id="modal_fullCollection_misc" class="modal fade">
	<div class="modal-dialog modal-full" style="margin-top: 160px;">
		<div class="modal-content">
			<div class="modal-header bg-teal">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h6 class="modal-title">Daily Misc Collection Progress</h6>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="container-fluid">
						<div class="">
							<div class="panel-body">
								<table class="table" id="popupLocationwiseColl">
									<thead>
										<tr class="bg-teal">
											<th>Branch Name</th>
											<th>Customer Paid</th>
											<th>Total Collection</th>
											<th>New Connection Tap</th>
											<th>New Connection Deposit</th>
											<th>Meter value</th>
											<th>Temp Hole Block</th>
											<th>Name Change</th>
											<th>Illegal Connection Penalty</th>
											<th>New Connection Installation</th>
											<th>Others</th>
										</tr>
									</thead>
									<tbody id="popuplocationwiseBodyCollMisc">
									</tbody>
									
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-link" data-dismiss="modal" style="background-color: #2fb6f5;">Close</button>
			</div>
		</div>
	</div>
</div>

<div id="modal_fulltariffCollection" class="modal fade">
	<div class="modal-dialog modal-full" style="margin-top: 160px;">
		<div class="modal-content">
			<div class="modal-header bg-teal">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h6 class="modal-title">Collection Progress</h6>
			</div>
			<div class="modal-body">
			<div class="row" style="margin-left: 21px;">
		    <span id="locationCollection" style="color: purple;"></span>:<span id="NameCollection"></span>
			</div>
				<div class="row">
					<div class="container-fluid">
						<div class="">
							<div class="panel-body">
								<table class="table" id="popupTariffwiseColl">
									<thead>
										<tr class="bg-teal">
											<th>Tariff</th>
											<th>Total Installation</th>
											<th>Consumption(in Units)</th>
											<th>Assessment</th>
											<th>Payment</th>
										</tr>
									</thead>
									<tbody id="popupTariffWiseBodyColl">
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-link" data-dismiss="modal">Close</button>
			</div>
		</div>
	</div>
</div>

	