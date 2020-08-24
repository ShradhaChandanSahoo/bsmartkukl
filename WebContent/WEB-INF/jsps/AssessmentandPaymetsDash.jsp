   
  <%@include file="/WEB-INF/decorators/taglibs.jsp"%> 
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<script type="text/javascript">

var samePeriodDate="";
$(window).load(function() {
	
	var makeDate = new Date();
    makeDate = new Date(makeDate.setMonth(makeDate.getMonth() - 2));
    
	samePeriodDate=moment(makeDate).format('DD-MMM-YY');
	$("#samePeriodmonth").html("("+samePeriodDate+")");
	var cDate=new Date();
	$("#currentdate").html("("+moment(cDate).format('DD-MMM-YY')+")")
	 $.ajax({
	        type:"GET",
	        url:"./getAssessmentandPayments",
	        dataType:"json",
	        success:function(response){
	    $.each(response, function(key, val) {

	            	$("#total_customer_paid").html(parseInt(val.total_customer_paid));
	            	$("#total_coll").html(parseFloat(val.total_coll));
	            	$("#bill").html(parseFloat(val.bill));
	            	$("#board").html(parseFloat(val.board));
	            	$("#misc").html(parseFloat(val.misc));
	            	$("#rebate").html(parseFloat(val.rebate));
	            	$("#penalty").html(parseFloat(val.penalty));
	            	$("#b_penalty").html(parseFloat(val.b_penalty));
	            	$("#total_customer_paid_m").html(parseInt(val.total_customer_paid_m));
	            	$("#total_coll_m").html(parseFloat(val.total_coll_m));
	            	$("#bill_m").html(parseFloat(val.bill_m));
	            	$("#board_m").html(parseFloat(val.board_m));
	            	$("#misc_m").html(parseFloat(val.misc_m));
	            	$("#rebate_m").html(parseFloat(val.rebate_m));
	            	$("#penalty_m").html(parseFloat(val.penalty_m));
	            	$("#b_penalty_m").html(parseFloat(val.b_penalty_m));
	            	 
	Highcharts.chart('assessmnttttt', {
	    chart: {
	        type: 'bar'
	    },
	    title: {
	        text: 'Assessments'
	    },
	    xAxis: {
	        categories: ['Consumption', 'Assessments', 'Payments']
	    },
	    yAxis: {
	        min: 0,
	      /*   title: {
	            text: 'Total fruit consumption'
	        } */
	    },
	    legend: {
	        reversed: true
	    },
	    plotOptions: {
	        series: {
	            stacking: 'normal'
	        }
	    },
	    series: [{
	        name: 'Current Month',
	        data: [parseInt(val.consumption),parseInt(val.assessment), parseInt(val.payments)]
	    }, {
	        name: 'Previous Month',
	        data: [parseInt(val.consumptionprevious), parseInt(val.assessmentprevious),parseInt(val.paymentsprevious)]
	    },]
	});
	            });
	    
	     /* $('.Count2').each(function () {
  		  var $this = $(this);
  		  jQuery({ Counter: 0 }).animate({ Counter: $this.text() }, {
  		    duration: 30000,
  		    easing: 'swing',
  		    step: function () {
  		      $this.text(this.Counter.toFixed(1));
  		    }
  		  });
  		});
	     $('.Count21').each(function () {
	  		  var $this = $(this);
	  		  jQuery({ Counter: 0 }).animate({ Counter: $this.text() }, {
	  		    duration: 30000,
	  		    easing: 'swing',
	  		    step: function () {
	  		      $this.text(Math.ceil(this.Counter));
	  		    }
	  		  });
	  		}); */
	  
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
function LocationwiseColl(){
	var sitecode="ALL";
	/* $('#popuplocationwise').attr("data-toggle", "modal"); */
	 $('#popuplocationwiseColl').attr("data-target","#modal_fullCollection"); 
	 
	$.ajax({
			type : "GET",
			url : "./getCollectionLocationWiseMonthlyCollection",
			dataType : "json",
			data : {
				sitecode : sitecode,
			},
			success : function(response) {
				var htmldata = "";

				for (var i = 0; i < response.length; i++) {
					html = "<tr>" +
					"<td>" + response[i].brach + "</td>" + 
					"<td>" + response[i].total_customer_paid + "</td>" + 
					"<td>" + response[i].total_coll + "</td>" + 
					"<td>" + response[i].bill + "</td>" + 
					"<td>" + response[i].board + "</td>" + 
					"<td>" + response[i].misc + "</td>" + 
					"<td>" + response[i].rebate + "</td>" + 
					"<td>" + response[i].penalty + "</td>" + 
					"<td>" + response[i].b_penalty + "</td>" + "</tr>"
					htmldata += html;
				}
				$("#popuplocationwiseBodyColl").html(htmldata);

				$("#popuplocationwiseBody1Coll").hide();
				
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
					<h3 class="panel-title text-bold" align="center" style="margin-top: -10px;">Collection</h3>
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
							<h6 class="panel-title">Todays Collection Details<span id="currentdate" style="color: green;"></span></h6>

						</div>
						<div class="container-fluid">

							<div class="row text-center">
								<div class="col-md-3">
									<div class="content-group">
										<h5 class="text-semibold no-margin">
											<i class="icon-add position-left text-slate"></i> <span
												class="Count21" id="total_customer_paid"></span>
										</h5>
										<span class="text-muted text-size-small">Total
											Consumers paid</span>
									</div>
								</div>

								<div class="col-md-3">
									<div class="content-group">
										<h5 class="text-semibold no-margin">
											<i class="icon-cash3 position-left text-slate"></i> <span
												class="Count2" id="total_coll"></span><span id="billcollass"></span>
										</h5>
										<span class="text-muted text-size-small">Total Payments</span>
									</div>
								</div>

								
								<div class="col-md-3">
									<div class="content-group">
										<h5 class="text-semibold no-margin">
											<i class="icon-cash3 position-left text-slate"></i> <span
												class="Count2" id="bill"></span><span id="billcollass"></span>
										</h5>
										<span class="text-muted text-size-small">Bill Payments</span>
									</div>
								</div>
								<div class="col-md-3">
									<div class="content-group">
										<h5 class="text-semibold no-margin">
											<i class="icon-cash3 position-left text-slate"></i> <span
												class="Count2" id="board"></span><span id="billasspay"></span>
										</h5>
										<span class="text-muted text-size-small">Board Payments</span>
									</div>
								</div>
								<div class="col-md-3">
									<div class="content-group">
										<h5 class="text-semibold no-margin">
											<i class="icon-cash3 position-left text-slate"></i> <span
												class="Count2" id="misc"></span><span id="billasspay"></span>
										</h5>
										<span class="text-muted text-size-small">Miscelleneous Payments</span>
									</div>
								</div>
								<div class="col-md-3">
									<div class="content-group">
										<h5 class="text-semibold no-margin">
											<i class="icon-cash3 position-left text-slate"></i> <span
												class="Count2" id="rebate"></span><span id="billasspay"></span>
										</h5>
										<span class="text-muted text-size-small">Rebate</span>
									</div>
								</div>
								<div class="col-md-3">
									<div class="content-group">
										<h5 class="text-semibold no-margin">
											<i class="icon-cash3 position-left text-slate"></i> <span
												class="Count2" id="penalty"></span><span id="billasspay"></span>
										</h5>
										<span class="text-muted text-size-small">Penalty</span>
									</div>
								</div>
								<div class="col-md-3">
									<div class="content-group">
										<h5 class="text-semibold no-margin">
											<i class="icon-cash3 position-left text-slate"></i> <span
												class="Count2" id="b_penalty"></span><span id="billasspay"></span>
										</h5>
										<span class="text-muted text-size-small">Board Penalty</span>
									</div>
								</div>
							</div>
						</div>

						

					</div>
				</div>



				<div class="row">
					<div class="container-fluid">
						<div class="panel-heading">
							<h6 class="panel-title">Current Month Collection Details<span id="samePeriodmonth" style="color:  green;"></span></h6>

						</div>
						<div class="container-fluid">

							<div class="row text-center">
								<div class="col-md-3">
									<div class="content-group">
										<h5 class="text-semibold no-margin">
											<i class="icon-add position-left text-slate"></i> <span
												class="Count21" id="total_customer_paid_m"></span>
										</h5>
										<span class="text-muted text-size-small">Total
											Consumers paid</span>
									</div>
								</div>

								<div class="col-md-3">
									<div class="content-group">
										<h5 class="text-semibold no-margin">
											<i class="icon-cash3 position-left text-slate"></i> <span
												class="Count2" id="total_coll_m"></span><span id="billcollass"></span>
										</h5>
										<span class="text-muted text-size-small">Total Payments</span>
									</div>
								</div>

								
								<div class="col-md-3">
									<div class="content-group">
										<h5 class="text-semibold no-margin">
											<i class="icon-cash3 position-left text-slate"></i> <span
												class="Count2" id="bill_m"></span><span id="billcollass"></span>
										</h5>
										<span class="text-muted text-size-small">Bill Payments</span>
									</div>
								</div>
								<div class="col-md-3">
									<div class="content-group">
										<h5 class="text-semibold no-margin">
											<i class="icon-cash3 position-left text-slate"></i> <span
												class="Count2" id="board_m"></span><span id="billasspay"></span>
										</h5>
										<span class="text-muted text-size-small">Board Payments</span>
									</div>
								</div>
								<div class="col-md-3">
									<div class="content-group">
										<h5 class="text-semibold no-margin">
											<i class="icon-cash3 position-left text-slate"></i> <span
												class="Count2" id="misc_m"></span><span id="billasspay"></span>
										</h5>
										<span class="text-muted text-size-small">Miscelleneous Payments</span>
									</div>
								</div>
								<div class="col-md-3">
									<div class="content-group">
										<h5 class="text-semibold no-margin">
											<i class="icon-cash3 position-left text-slate"></i> <span
												class="Count2" id="rebate_m"></span><span id="billasspay"></span>
										</h5>
										<span class="text-muted text-size-small">Rebate</span>
									</div>
								</div>
								<div class="col-md-3">
									<div class="content-group">
										<h5 class="text-semibold no-margin">
											<i class="icon-cash3 position-left text-slate"></i> <span
												class="Count2" id="penalty_m"></span><span id="billasspay"></span>
										</h5>
										<span class="text-muted text-size-small">Penalty</span>
									</div>
								</div>
								<div class="col-md-3">
									<div class="content-group">
										<h5 class="text-semibold no-margin">
											<i class="icon-cash3 position-left text-slate"></i> <span
												class="Count2" id="b_penalty_m"></span><span id="billasspay"></span>
										</h5>
										<span class="text-muted text-size-small">Board Penalty</span>
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
							data-toggle="modal" data-target="#modal_fullCollection"
							onclick="return LocationwiseColl();">
							<b><i class="icon-location4"></i></b> Location Wise
						</button>
					</div>

			</div>
		</div>
		
	</div>
	
</div>

<div id="modal_fullCollection" class="modal fade">
	<div class="modal-dialog modal-full" style="margin-top: 160px;">
		<div class="modal-content">
			<div class="modal-header bg-teal">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h6 class="modal-title">Daily Collection Progress</h6>
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
											<th>Bill Payment</th>
											<th>Board Payment</th>
											<th>Misc Payment</th>
											<th>Rebate</th>
											<th>Penalty</th>
											<th>Board Penalty</th>
										</tr>
									</thead>
									<tbody id="popuplocationwiseBodyColl">
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

	