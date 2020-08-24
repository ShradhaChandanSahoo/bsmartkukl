
<%@include file="/WEB-INF/decorators/taglibs.jsp"%>
<!-- Core JS files -->
<script type="text/javascript"
	src="assets/js/plugins/loaders/pace.min.js"></script>
<script type="text/javascript"
	src="assets/js/core/libraries/jquery.min.js"></script>
<script type="text/javascript"
	src="assets/js/core/libraries/bootstrap.min.js"></script>
<script type="text/javascript"
	src="assets/js/plugins/loaders/blockui.min.js"></script>

<script type="text/javascript"
	src="assets/js/plugins/visualization/d3/d3.min.js"></script>
<script type="text/javascript"
	src="assets/js/plugins/visualization/d3/d3_tooltip.js"></script>
<script type="text/javascript"
	src="assets/js/plugins/forms/styling/switchery.min.js"></script>
<script type="text/javascript"
	src="assets/js/plugins/forms/styling/uniform.min.js"></script>
<script type="text/javascript"
	src="assets/js/plugins/forms/selects/bootstrap_multiselect.js"></script>
<script type="text/javascript"
	src="assets/js/plugins/ui/moment/moment.min.js"></script>
<script type="text/javascript"
	src="assets/js/plugins/pickers/daterangepicker.js"></script>

<script type="text/javascript" src="assets/js/core/app.js"></script>
<script type="text/javascript" src="assets/js/pages/dashboard.js"></script>
<script src="./resources/js/highcharts.js"></script>
<script src="./resources/js/highcharts-more.js"></script>
<script src="./resources/js/exporting.js"></script>

<!-- <script src="https://code.highcharts.com/highcharts-more.js"></script>
<script src="https://code.highcharts.com/modules/exporting.js"></script>
 -->
<script type="text/javascript"
	src="resources/layout_3/assets/js/charts/c3/c3_bars_pies.js"></script>

<script type="text/javascript"
	src="assets/js/plugins/visualization/echarts/echarts.js"></script>

<script type="text/javascript" src="assets/js/core/app.js"></script>
<script type="text/javascript"
	src="assets/js/charts/echarts/candlesticks_others.js"></script>

<script type="text/javascript"
	src="resources/layout_3/assets/js/plugins/visualization/c3/c3.min.js"></script>

<script>

$(window).load(function() {
	   $('#loading').hide();
	    $('#ongoingLoaderId').show();
	  });
</script>

<style>
[title]:hover:after{
  content: attr(title);
  background:pink;
  padding: 6px;
  border-radius:5px;
  border:1px solid;
}
</style>

<div id="loading" hidden="true" style="text-align: center;">
		<h3 id="loadingText">Loading..... Please wait.</h3>
		<img alt="image" src="./resources/images/loading.gif"
			style="width: 3%; height: 3%;">
</div>
	
	
<!-- Main content -->
<div class="content-wrapper" id="ongoingLoaderId" hidden="true">

	<!-- Main charts -->
	<%-- <div class="row">
					<div class="col-lg-7">

						<!-- Traffic sources -->
						<div class="panel panel-flat">
							<div class="panel-heading">
								<h6 class="panel-title">Customers</h6>
								<div class="heading-elements">
									<form class="heading-form" action="#">
										<div class="form-group">
											<label class="checkbox-inline checkbox-switchery checkbox-right switchery-xs">
												<input type="checkbox" class="switch" checked="checked">
												Live update:
											</label>
										</div>
									</form>
								</div>
							</div>

							<div class="container-fluid">
								<div class="row">
									<div class="col-lg-4">
										<ul class="list-inline text-center">
											<li>
												<a href="#" class="btn border-teal text-teal btn-flat btn-rounded btn-icon btn-xs valign-text-bottom"><i class="icon-plus3"></i></a>
											</li>
											<li class="text-left">
												<div class="text-semibold">New Customers</div>
												<div class="text-muted">200</div>
											</li>
										</ul>

										<div class="col-lg-10 col-lg-offset-1">
											<div class="content-group" id="new-visitors"></div>
										</div>
									</div>

									<div class="col-lg-4">
										<ul class="list-inline text-center">
											<li>
												<a href="#" class="btn border-warning-400 text-warning-400 btn-flat btn-rounded btn-icon btn-xs valign-text-bottom"><i class="icon-watch2"></i></a>
											</li>
											<li class="text-left">
												<div class="text-semibold">Waiting for Connection</div>
												<div class="text-muted">22</div>
											</li>
										</ul>

										<div class="col-lg-10 col-lg-offset-1">
											<div class="content-group" id="new-sessions"></div>
										</div>
									</div>

									<div class="col-lg-4">
										<ul class="list-inline text-center">
											<li>
												<a href="#" class="btn border-indigo-400 text-indigo-400 btn-flat btn-rounded btn-icon btn-xs valign-text-bottom"><i class="icon-people"></i></a>
											</li>
											<li class="text-left">
												<div class="text-semibold">Total Customers</div>
												<div class="text-muted"><span class="status-mark border-success position-left"></span> 1,75,234</div>
											</li>
										</ul>

										<div class="col-lg-10 col-lg-offset-1">
											<div class="content-group" id="total-online"></div>
										</div>
									</div>
								</div>
							</div>

							<div class="position-relative" id="traffic-sources"></div>
						</div>
						<!-- /traffic sources -->

					</div>

					<div class="col-lg-5">

						<!-- Sales stats -->
						<div class="panel panel-flat">
							<div class="panel-heading">
								<h6 class="panel-title">Statistics</h6>
								<div class="heading-elements">
									<form class="heading-form" action="#">
										<div class="form-group">
											<select class="select" id="select_date">
												<optgroup label="<i class='icon-watch pull-right'></i> Time period">
													
													<option value="val4">Dec, 1 - Dec, 27</option>
												</optgroup>
											</select>
										</div>
									</form>
			                	</div>
							</div>

							<div class="container-fluid">
								<div class="row text-center">
									<div class="col-md-4">
										<div class="content-group">
											<h5 class="text-semibold no-margin"><i class="icon-calendar5 position-left text-slate"></i> 65,689</h5>
											<span class="text-muted text-size-small">Unbilled</span>
										</div>
									</div>

									<div class="col-md-4">
										<div class="content-group">
											<h5 class="text-semibold no-margin"><i class="icon-calendar52 position-left text-slate"></i> 30, 32,568</h5>
											<span class="text-muted text-size-small">Balance Total(NPR)</span>
										</div>
									</div>

									<div class="col-md-4">
										<div class="content-group">
											<h5 class="text-semibold no-margin"><i class="icon-cash3 position-left text-slate"></i> 2,23,464,00</h5>
											<span class="text-muted text-size-small">Total revenue(NPR)</span>
										</div>
									</div>
								</div>
							</div>

							<div class="content-group-sm" id="app_sales"></div>
							<div id="monthly-sales-stats"></div>
						</div>
						<!-- /sales stats -->

					</div>
				</div> --%>
	<!-- /main charts -->



	<!-- Gauges -->
	<div class="row">
		<div class="col-lg-6">

			<!-- Basic gauge chart -->
			<div class="panel panel-flat">
					<ul class="nav nav-lg nav-tabs nav-justified no-margin no-border-radius bg-pink-400 border-top border-top-pink-300">
						<li>
							<a href="#" class="text-size-small text-uppercase" >
								Revenue Realization
							</a>
						</li>
					</ul>
				
				
				

				<div class="panel-body">
					<div class="chart-container">
						<div style="width: 400px; height: 300px; margin: 0 auto"
							id="containerGauyysd"></div>
						<!-- <div style="width: 600px; height: 400px; margin: 0 auto">
						    <div id="container-speed" style="width: 300px; height: 200px; float: left"></div>
						    <div id="container-rpm" style="width: 300px; height: 200px; float: left"></div>
						</div> -->
					</div>
				</div>
			</div>
			<!-- /basic gauge chart -->

		</div>

		<div class="col-lg-6">

			<!-- Gauge styling options -->
			<div class="panel panel-flat">
					<ul class="nav nav-lg nav-tabs nav-justified no-margin no-border-radius bg-info-400 border-top border-top-info-300">
						<li>
							<a href="#" class="text-size-small text-uppercase" >
								Billing Progress
							</a>
						</li>
					</ul>
				

				<div class="panel-body">
					<div class="chart-container">
						<div id="container1211111"
							style="min-width: 310px; max-width: 400px; height: 300px; margin: 0 auto"></div>
					</div>
				</div>
			</div>
			<!-- /gauge styling options -->

		</div>
	</div>
	<!-- /gauges -->
	<!-- Gauges title -->
	<h6 class="content-group text-semibold">Branch Wise Dashboard</h6>
	<!-- /gauges title -->


	<!-- Dashboard content -->
	<div class="row">
		<div class="col-lg-4">

			<!-- Members online -->
			<div class="panel bg-teal-400">
				<div class="panel-body">
					<div class="heading-elements">
						<span class="heading-text badge bg-teal-800" title="Billed Perecentage">${billed_MAHAR}%</span>
						<span class="heading-text badge bg-teal-800" title="UnBilled Perecentage">${unBilled_MAHAR}%</span>
					</div>


					<h3 class="no-margin">${MAHAR_1111}</h3>
					Maharajgung
					<!-- <div class="text-muted text-size-small">489 avg</div> -->
				</div>

				<div class="container-fluid">
					<div id="members-online"></div>
				</div>
			</div>
			<!-- /members online -->

		</div>

		<div class="col-lg-4">

			<!-- Members online -->
			<div class="panel bg-teal-400">
				<div class="panel-body">
					<div class="heading-elements">
						<span class="heading-text badge bg-teal-800" title="Billed Perecentage">${billed_BANESH }%</span>
						<span class="heading-text badge bg-teal-800" title="UnBilled Perecentage">${unBilled_BANESH }%</span>
					</div>

					<h3 class="no-margin">${BANESH_1112}</h3>
					Baneshwor
					<!-- <div class="text-muted text-size-small">489 avg</div> -->
				</div>

				<div class="container-fluid">
					<div id="members-online"></div>
				</div>
			</div>
			<!-- /members online -->

		</div>


		<div class="col-lg-4">

			<!-- Members online -->
			<div class="panel bg-teal-400">
				<div class="panel-body">
					<div class="heading-elements">
						<span class="heading-text badge bg-teal-800" title="Billed Perecentage">${billed_TRIPUR}%</span>
						<span class="heading-text badge bg-teal-800" title="UnBilled Perecentage">${unBilled_TRIPUR}%</span>
					</div>

					<h3 class="no-margin">${TRIPUR_1115}</h3>
					Tripureshwor
					<!-- <div class="text-muted text-size-small">489 avg</div> -->
				</div>

				<div class="container-fluid">
					<div id="members-online"></div>
				</div>
			</div>
			<!-- /members online -->

		</div>


		<div class="col-lg-4">

			<!-- Members online -->
			<div class="panel bg-teal-400">
				<div class="panel-body">
					<div class="heading-elements">
						<span class="heading-text badge bg-teal-800" title="Billed Perecentage">${billed_LALITPUR}%</span>
						<span class="heading-text badge bg-teal-800" title="UnBilled Perecentage">${unBilled_LALITPUR}%</span>
					</div>

					<h3 class="no-margin">${LALITPUR_1118}</h3>
					Lalitpur
					<!-- <div class="text-muted text-size-small">489 avg</div> -->
				</div>

				<div class="container-fluid">
					<div id="members-online"></div>
				</div>
			</div>
			<!-- /members online -->

		</div>

		<div class="col-lg-4">

			<!-- Members online -->
			<div class="panel bg-teal-400">
				<div class="panel-body">
					<div class="heading-elements">
						<span class="heading-text badge bg-teal-800" title="Billed Perecentage">${billed_CHHETRA}%</span>
						<span class="heading-text badge bg-teal-800" title="UnBilled Perecentage">${unBilled_CHHETRA}%</span>
					</div>

					<h3 class="no-margin">${CHHETRA_1114}</h3>
					Chhetrapati
					<!-- <div class="text-muted text-size-small">489 avg</div> -->
				</div>

				<div class="container-fluid">
					<div id="members-online"></div>
				</div>
			</div>
			<!-- /members online -->

		</div>
		<div class="col-lg-4">

			<!-- Members online -->
			<div class="panel bg-teal-400">
				<div class="panel-body">
					<div class="heading-elements">
						<span class="heading-text badge bg-teal-800" title="Billed Perecentage">${billed_BHAKTAPUR}%</span>
						<span class="heading-text badge bg-teal-800" title="UnBilled Perecentage">${unBilled_BHAKTAPUR}%</span>
					</div>

					<h3 class="no-margin">${BHAKTAPUR_1116}</h3>
					Bhaktapur
					<!-- <div class="text-muted text-size-small">489 avg</div> -->
				</div>

				<div class="container-fluid">
					<div id="members-online"></div>
				</div>
			</div>
			<!-- /members online -->

		</div>


		<div class="col-lg-12">



			<!-- Quick stats boxes -->
			<div class="row">





				<div class="col-lg-4">

					<!-- Members online -->
					<div class="panel bg-teal-400">
						<div class="panel-body">
							<div class="heading-elements">
								<span class="heading-text badge bg-teal-800" title="Billed Percentage">${billed_MAHAN}%</span>
								<span class="heading-text badge bg-teal-800" title="UnBilled Percentage">${unBilled_MAHAN}%</span>
							</div>

							<h3 class="no-margin">
								${MAHAN_1110}
							</h3>
							Mahankalchaur
							<!-- <div class="text-muted text-size-small">489 avg</div> -->
						</div>

						<div class="container-fluid">
							<div id="members-online"></div>
						</div>
					</div>
					<!-- /members online -->

				</div>

				<div class="col-lg-4">

					<!-- Members online -->
					<div class="panel bg-teal-400">
						<div class="panel-body">
							<div class="heading-elements">
								<span class="heading-text badge bg-teal-800" title="Billed Percentage">${billed_KIRTIPUR}%</span>
								<span class="heading-text badge bg-teal-800" title="UnBilled Percentage">${unBilled_KIRTIPUR}%</span>
							</div>

							<h3 class="no-margin">
								${KIRTIPUR_1119}
							</h3>
							Kirtipur
							<!-- <div class="text-muted text-size-small">489 avg</div> -->
						</div>

						<div class="container-fluid">
							<div id="members-online"></div>
						</div>
					</div>
					<!-- /members online -->

				</div>









				<div class="col-lg-4">

					<!-- Members online -->
					<div class="panel bg-teal-400">
						<div class="panel-body">
							<div class="heading-elements">
								<span class="heading-text badge bg-teal-800" title="Billed Percentage">${billed_THIMI}%</span>
								<span class="heading-text badge bg-teal-800" title="UnBilled Percentage">${unBilled_THIMI}%</span>
							</div>

							<h3 class="no-margin">
								${THIMI_1117}
							</h3>
							Madhyapur Thimi
							<!-- <div class="text-muted text-size-small">489 avg</div> -->
						</div>

						<div class="container-fluid">
							<div id="members-online"></div>
						</div>
					</div>
					<!-- /members online -->

				</div>


				<div class="col-lg-4">

					<!-- Members online -->
					<div class="panel bg-teal-400">
						<div class="panel-body">
							<div class="heading-elements">
								<span class="heading-text badge bg-teal-800" title="Billed Percentage">0%<%-- ${billed_KAMALADI } --%></span>
								<span class="heading-text badge bg-teal-800" title="UnBilled Percentage">0%<%-- ${unBilled_KAMALADI } --%></span>
							</div>

							<h3 class="no-margin">
								 ${KAMALADI_1113} 
							</h3>
							Kamaladi
							<!-- <div class="text-muted text-size-small">489 avg</div> -->
						</div>

						<div class="container-fluid">
							<div id="members-online"></div>
						</div>
					</div>
					<!-- /members online -->

				</div>






			</div>
			<!-- /quick stats boxes -->

			<div class="row">

				<!-- <div class="chart-container">
					<div class="chart" id="c3-combined-chart1">
					
					</div>
				</div> -->

				<div class="chart-container">
					<fieldset style="padding-left: 10px; padding-right: 10px;">
					<ul style="padding-left: 10px; padding-right: 10px;" class="nav nav-lg nav-tabs nav-justified no-margin no-border-radius bg-pink-400 border-top border-top-pink-300">
						<li>
							<a href="#" class="text-size-small text-uppercase" style="font-size: 15px;">
								MonthWise Collection
							</a>
						</li>
					</ul>
					</fieldset>
					<div id="container121" style="padding-left: 10px; padding-right: 10px;">
					
					
					
					</div>
				</div>






			</div>

			<!-- Support tickets -->
			<!-- <div class="panel panel-flat">
							<div class="panel-heading">
								<h6 class="panel-title">Support tickets</h6>
								<div class="heading-elements">
									<button type="button" class="btn btn-link daterange-ranges heading-btn text-semibold">
										<i class="icon-calendar3 position-left"></i> <span></span> <b class="caret"></b>
									</button>
			                	</div>
							</div>

							<div class="table-responsive">
								<table class="table table-xlg text-nowrap">
									<tbody>
										<tr>
											<td class="col-md-4">
												<div class="media-left media-middle">
													<div id="tickets-status"></div>
												</div>

												<div class="media-left">
													<h5 class="text-semibold no-margin">14,327 <small class="text-success text-size-base"><i class="icon-arrow-up12"></i> (+2.9%)</small></h5>
													<span class="text-muted"><span class="status-mark border-success position-left"></span> Jun 16, 10:00 am</span>
												</div>
											</td>

											<td class="col-md-3">
												<div class="media-left media-middle">
													<a href="#" class="btn border-indigo-400 text-indigo-400 btn-flat btn-rounded btn-xs btn-icon"><i class="icon-alarm-add"></i></a>
												</div>

												<div class="media-left">
													<h5 class="text-semibold no-margin">
														1,132 <small class="display-block no-margin">total tickets</small>
													</h5>
												</div>
											</td>

											<td class="col-md-3">
												<div class="media-left media-middle">
													<a href="#" class="btn border-indigo-400 text-indigo-400 btn-flat btn-rounded btn-xs btn-icon"><i class="icon-spinner11"></i></a>
												</div>

												<div class="media-left">
													<h5 class="text-semibold no-margin">
														06:25:00 <small class="display-block no-margin">response time</small>
													</h5>
												</div>
											</td>

											<td class="text-right col-md-2">
												<a href="#" class="btn bg-teal-400"><i class="icon-statistics position-left"></i> Report</a>
											</td>
										</tr>
									</tbody>
								</table>	
							</div>

							


					
					</div> -->
		</div>
		<!-- /dashboard content -->

	</div>
	<!-- /main content -->


</div>



	<script>





$(function () {
	
	
   
});

var dates=[], collection=[], totalRevnue1=0,onlyYear=0;
function getMonthWiseCollection()
{
	$.ajax({
		type : "GET",
		url : "./getDataforHighCharts",
		dataType : "json",
		async:false,	
		cache:false,
		success : function(response)
		{
			
			var lisvalue=response.listvalues;
			var totalRevnue=response.totalRevenue;
			response=lisvalue;
			var maxYear=0;
			dates =[], collection = []; 
			for(var i=0;i<response.length;i++)
			{
				 maxYear=response[0][1];
				collection.push(response[i][0]);
				dates.push(response[i][1]);
			}
			totalRevnue1=totalRevnue;
			var res=maxYear.split("-");
			onlyYear =res[0];
			
		}
	});
    displayDatainGraph(collection,dates);
	
}



$(document).ready(function(){  
	
	
	$('#cordashboard').addClass('active');
	
	var activeMod="${activeModulesNew}";
	var module="Corporate Dashboard";
	var check=activeMod.indexOf(module) > -1;
	
	if(check){
	
	}else{
		window.location.href="./accessDenied";
	}
	
	getMonthWiseCollection();
	
}); 


	
	
	
	var monthlyWiseProgessValue=${monthlyWiseProgessValue};
function displayDatainGraph(collection,dates)
	{
	
	
	Highcharts.chart('container1211111', {

        chart: {
            type: 'gauge',
            plotBackgroundColor: null,
            plotBackgroundImage: null,
            plotBorderWidth: 0,
            plotShadow: false
        },

        title: {
            text: 'Month Wise Billing Progress in %'
        },

        pane: {
            startAngle: -150,
            endAngle: 150,
            background: [{
                backgroundColor: {
                    linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 },
                    stops: [
                        [0, '#FFF'],
                        [1, '#333']
                    ]
                },
                borderWidth: 0,
                outerRadius: '109%'
            }, {
                backgroundColor: {
                    linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 },
                    stops: [
                        [0, '#333'],
                        [1, '#FFF']
                    ]
                },
                borderWidth: 1,
                outerRadius: '107%'
            }, {
                // default background
            }, {
                backgroundColor: '#DDD',
                borderWidth: 0,
                outerRadius: '105%',
                innerRadius: '103%'
            }]
        },

        // the value axis
        yAxis: {
            min: 0,
            max: 100,

            minorTickInterval: 'auto',
            minorTickWidth: 1,
            minorTickLength: 10,
            minorTickPosition: 'inside',
            minorTickColor: '#666',

            tickPixelInterval: 30,
            tickWidth: 2,
            tickPosition: 'inside',
            tickLength: 10,
            tickColor: '#666',
            labels: {
                step: 2,
                rotation: 'auto'
            },
            title: {
                text: '%'
            },
            plotBands: [{
                from: 0,
                to: 20,
                color: '#DF5353' // green
            }, {
                from: 20,
                to: 80,
                color: '#DDDF0D' // yellow
            }, {
                from: 80,
                to: 100,
                color: '#55BF3B' // red
            }]
        },

        series: [{
            name: 'Progress',
            data: [monthlyWiseProgessValue],
            tooltip: {
                valueSuffix: ' %'
            }
        }]

    }
    // Add some life
 /*    function (chart) {
        if (!chart.renderer.forExport) {
            setInterval(function () {
                var point = chart.series[0].points[0],
                    newVal,
                    inc = Math.round((Math.random() - 0.5) * 20);

                newVal = point.y + inc;
                if (newVal < 0 || newVal > 200) {
                    newVal = point.y - inc;
                }

                point.update(newVal);

            }, 3000);
        }
    } */);
	
	
	
	

	 Highcharts.chart('containerGauyysd', {

	        chart: {
	            type: 'gauge',
	            alignTicks: false,
	            plotBackgroundColor: null,
	            plotBackgroundImage: null,
	            plotBorderWidth: 0,
	            plotShadow: false
	        },

	        title: {
	            text: 'Revenue Realization of '+onlyYear
	        },

	        pane: {
	            startAngle: -150,
	            endAngle: 150
	        },

	        yAxis: [{
	            min: 0,
	            max: 200,
	            lineColor: '#339',
	            tickColor: '#339',
	            minorTickColor: '#339',
	            offset: -25,
	            lineWidth: 2,
	            labels: {
	                distance: -20,
	                rotation: 'auto'
	            },
	            tickLength: 5,
	            minorTickLength: 5,
	            endOnTick: false
	        }, {
	            min: 0,
	            max: 124,
	            tickPosition: 'outside',
	            lineColor: '#933',
	            lineWidth: 2,
	            minorTickPosition: 'outside',
	            tickColor: '#933',
	            minorTickColor: '#933',
	            tickLength: 5,
	            minorTickLength: 5,
	            labels: {
	                distance: 15,
	                rotation: 'auto'
	            },
	            offset: -20,
	            endOnTick: false
	        }],

	        series: [{
	            name: 'TotalRevenue(NPR)',
	          data: [totalRevnue1], 
	            dataLabels: {
	                formatter: function () {
	                	
	                     /* var kmh = this.y,
	                        mph = Math.round(kmh * 0.621); 
	                    return  '<span style="color:#339">' + kmh + ' lakhs</span><br/>' + 
	                        '<span style="color:#933">' + mph + ' lakhs</span>'; */
	                        return '<span style="color:#339">' +totalRevnue1 + ' </span><br/>';
	                },
	                backgroundColor: {
	                    linearGradient: {
	                        x1: 0,
	                        y1: 0,
	                        x2: 0,
	                        y2: 1
	                    },
	                    stops: [
	                        [0, '#DDD'],
	                        [1, '#FFF']
	                    ]
	                }
	            },
	            tooltip: {
	                valueSuffix: ''
	            }
	        }]

	    }
	        // Add some life
	    /*     function (chart) {
	            setInterval(function () {
	                if (chart.axes) { // not destroyed
	                    var point = chart.series[0].points[0],
	                        newVal,
	                        inc = Math.round((Math.random() - 0.5) * 20);

	                    newVal = point.y + inc;
	                    if (newVal < 0 || newVal > 200) {
	                        newVal = point.y - inc;
	                    }

	                    point.update(newVal);
	                }
	            }, 3000);

	        } */);
	
	Highcharts.chart('container121', {
        chart: {
            zoomType: 'xy'
        },
        title: {
            text: ''
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
                format: ' {value}',
                style: {
                    color: Highcharts.getOptions().colors[0]
                }
            },
            opposite: false
        }],
        tooltip: {
            shared: true
        },
        /* legend: {
            layout: 'vertical',
            align: 'left',
            x: 120,
            verticalAlign: 'top',
            y: 100,
            floating: true,
            backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'
        }, */
        series: [{
            name: 'Amount(NPR)',
            type: 'column',
            yAxis: 1,
            data: collection,
            tooltip: {
                valueSuffix: '  '
            }

        }]
    });
	
	
	


	
}

function paymentHistoryPopUp(wardno,value){
	
	
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
		              	
		              	tableData += "<tr>"
								+"<td>"+(s+1)+"</td>"
								+"<td>"+obj.connection_no+"</td>"
								+"<td>"+obj.name_eng+"</td>"
								+"<td>"+obj.name_nep+"</td>"
								+"<td>"+obj.address_eng+"</td>"
								+"<td>"+obj.mobile_no+"</td>"
								+"<td>"+obj.pipe_size+"</td>"
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

//Generate chart
     // Generate chart
    /* var combined_chart = c3.generate({
        bindto: '#c3-combined-chart1',
        size: { height: 400 },
        color: {
            pattern: ['#FF9800', '#F44336', '#009688', '#4CAF50', '#03A9F4', '#8BC34A']
        },
        data: {
            columns: [
                ['data1', 30, 20, 50, 40, 60, 50],
                ['data2', 200, 130, 90, 240, 130, 220],
                ['data3', 300, 200, 160, 400, 250, 250],
                ['data4', 200, 130, 90, 240, 130, 220],
                ['data5', 130, 120, 150, 140, 160, 150],
                ['data6', 90, 70, 20, 50, 60, 120],
            ],
            type: 'bar',
            types: {
                data3: 'spline',
                data4: 'line',
                data6: 'area',
            },
            groups: [
                ['data1','data2']
            ],
            grid: {
                x: {
                    show: true
                },
                y: {
                    lines: [{value:0}]
                }
            }
        }
    }); */



    $(function () {
       
    	
    });
    
    

</script>


	<style>
legend {
	text-transform: none;
	font-size: 16px;
	border-color: #F01818;
}

.datatable-header,.datatable-footer {
	padding: 0px 0px 0 0px;
}

.dataTables_filter {
	display: none;
	float: left;
	margin: 0 0 20px 20px;
	position: relative;
}

.dataTables_length>label {
	margin-bottom: 0;
	display: none;
}

.modal.fade .modal-dialog {
	width: 1000px;
}
</style>