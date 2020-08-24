<%@include file="/WEB-INF/decorators/taglibs.jsp"%>
<script src="https://code.highcharts.com/highcharts.js"></script>
<script src="https://code.highcharts.com/modules/exporting.js"></script>

<script type="text/javascript">



$(window).load(function () {
	
	trendAnalysis();
	/* foreCoastAnalysis(); */
	
});

var months=[];
var normal=[];
var dl=[];
var defective=[];
function trendAnalysis(){

	var sitecode='2109';
	$.ajax({
		type:'GET',
		url:'./getmonthWiseMeterStatusDetails',
		dataType:'json',
		data:{
			sitecode:sitecode	
		},
		success:function (response){
			 months=[];
			 normal=[];
			dl=[];
			 defective=[];
			for(var i=0;i<response.length;i++){
				months.push(response[i].rdngmonth);
				normal.push(response[i].normal);
				dl.push(response[i].doorlock);
				defective.push(response[i].defective);
			}
			//alert(response[0].rdngmonth);
			//var month=['Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep','Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep'];
			Highcharts.chart('container', {
			    chart: {
			        type: 'line'
			    },
			    title: {
			        text: ''
			    },
			    subtitle: {
			        text: ''
			    },
			    xAxis: {
			        categories: months
			    },
			    yAxis: {
			        title: {
			            text:' Number of Consumers'
			        }
			    },
			    plotOptions: {
			        line: {
			            dataLabels: {
			                enabled: true
			            },
			            enableMouseTracking: false
			        }
			    },
			    series: [ {
			        name: 'Door  Lock',
			        data: dl
			    },
			    {
			        name: 'Defective',
			        data: defective
			    },{
			        name: 'Normal',
			        data: normal
			    }]
			});
		},
	});
	
 	
	
}

/* function foreCoastAnalysis(){
	Highcharts.chart('Revenue', {
	    chart: {
	        zoomType: 'xy'
	    },
	    title: {
	        text: ''
	    },
	    subtitle: {
	        text: ''
	    },
	    xAxis: [{
	        categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun',
	            'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
	        crosshair: true
	    }],
	    yAxis: [{ // Primary yAxis
	        labels: {
	            format: 'INR',
	            style: {
	                color: Highcharts.getOptions().colors[1]
	            }
	        },
	        title: {
	            text: 'Collection',
	            style: {
	                color: Highcharts.getOptions().colors[1]
	            }
	        }
	    },  { // Secondary yAxis
	        title: {
	            text: 'Collection',
	            style: {
	                color: Highcharts.getOptions().colors[0]
	            }
	        },
	        labels: {
	            format: '{value} Lacs',
	            style: {
	                color: Highcharts.getOptions().colors[0]
	            }
	        },
	        opposite: true
	    } ],
	    tooltip: {
	        shared: true
	    },
	    legend: {
	        layout: 'vertical',
	        align: 'left',
	        x: 100,
	        verticalAlign: 'top',
	        y: 100,
	        floating: true,
	        backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'
	    },
	    series: [{
	        name: 'Collection',
	        type: 'column',
	        yAxis: 1,
	        data: [75.9, 71.5, 106.4, 129.2, 144.0, 100, 135.6, 148.5],
	        tooltip: {
	            valueSuffix: ' '
	        }

	    }, {
	        name: 'Projection',
	        type: 'spline',
	        data: [67.0, 66.9, 90.5, 140.5, 76, 50, 25.5, 70.0, 60.0, 59.0, 70, 80],
	        tooltip: {
	            valueSuffix: ''
	        }
	    }]
	});
} */
</script>

<div class="container-fluid">
           
              <div class="row">
                    <div class="col-lg-12">
                        <div class="panel panel-flat">
                          <div class="panel-heading" style=" height:50px;background-color: #26C6DA;">
                                <h4 class="panel-title text-bold " align="center" style="margin-top: -10px; " >Trend Analysis(Dholpur)</h4>
<!--                                 <h4 class="panel-title text-bold " align="right" style="margin-top: -32px; margin-right: 190px;" >Forecast Revenue Projections(Dhoplur)</h4>
 -->                            </div> 
<div class="container-fluid" style="height: 450px;">
<div class="">
<div id="container" align="center" style="width: 1000px; margin-left: 100px;"></div>
</div>

<!-- <div class="col-lg-6">
<div id="Revenue" ></div>
</div> -->
</div></div></div></div></div>
