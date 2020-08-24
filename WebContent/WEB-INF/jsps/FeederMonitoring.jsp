<%@include file="/WEB-INF/decorators/taglibs.jsp"%>

<script type="text/javascript">

$(window).load(function (){
	var d = new Date();
	d.setDate(d.getDate() - 1);
	var d2=moment(d).format('YYYY-MM-DD');
	
	var yesterDay=moment(d).format('DD-MMM-YYYY');
	$("#yesterDayDate").html("("+yesterDay+")");
	var data=2;
	var loginData={"sdoCode":data,"startDate":d2,"endDate":d2};
	
	$.ajax({
		type:'POST',
		url:'./getFeederMonitoringDataDayWise',
		data:JSON.stringify(loginData),
	    dataType : 'json',
	    contentType: 'application/json',
	    timeout:10000,
		success:function (response){
			var htmlData="";
			for(var i=0;i<response.length;i++){
				var html="<tr>"+
				          "<td style='width:208px;'>"+response[i].feeder_name+"</td>"+
				          "<td>"+response[i].r_phase+"</td>"+
				          "<td>"+response[i].y_phase+"</td>"+
				          "<td>"+response[i].b_phase+"</td>"+
				          "<td>"+response[i].no_supply+"</td>"+
				          "</tr>";
				          htmlData+=html;
			}
			$("#feederData").html(htmlData);
		},
	});
	
	
 $.ajax({
	type:'GET',
	url:'./getFeederMonitoringLastThirtyDays',
	dataType:'json',
	success:function (data){
		
      
          Highcharts.chart('Feedercontainer', {
        	  chart: {
        	  type: 'pie',
        	  options3d: {
        	      enabled: false,
        	      alpha: 45,
        	      beta: 0
        	  }
        	  },
        	  exporting: { enabled: false },
        	  title: {
        	  text: 'Last 30 days Feeder Outage Summary with available '+data[0].availableFeeder+' feeders.'
        	  },
        	  tooltip: {
        	  pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
        	  },
        	  plotOptions: {
        	  pie: {
        	      allowPointSelect: true,
        	      cursor: 'pointer',
        	      depth: 35,
        	      dataLabels: {
        	          enabled: true,
        	          format: '{point.name}'
        	      }
        	  }
        	  },
        	  series: [{
        	  type: 'pie',
        	  name: 'Power Supply',
        	  colors: ['#ff4242', '#ffb041', '#6192ed', '#333333'],
        	  data: [
        	      ['Phase1\n\n ' +data[0].r_phase+' hrs', parseFloat(data[0].r_phase)],
        	      ['Phase2\n\n ' +data[0].y_phase+' hrs', parseFloat(data[0].y_phase)],
        	      ['Phase3\n\n ' +data[0].b_phase+' hrs', parseFloat(data[0].b_phase)],
        	      ['Power Out\n\n ' +data[0].no_supply+' hrs', parseFloat(data[0].no_supply)]
        	  ]
        	  }]
        	  });

	},
}); 



 
});




</script>

<style>


</style>
 
 <div class="container-fluid">

             <div class="row"><!-- Defaulters/Arrears Start -->
                <div class="col-lg-8">
                
                <div class="panel panel-flat">
                            <div class="panel-heading " style="height:50px; background-color: #03A9F4">
                                <h3 class="panel-title text-bold" align="center" style="margin-top: -10px;">FEEDER OUTAGE SUMMARY</h3>

                            </div>
                          <!--  <div class="container-fluid">
                                <div class="row">
                                    <div class="col-lg-6">
                                    <ul class="list-inline text-center">
                                            <li>
                                                <a href="#" class="btn border-teal text-teal btn-flat btn-rounded btn-icon btn-xs valign-text-bottom"><i class="icon-plus3"></i></a>
                                            </li>
                                            <li class="text-left">
                                                <div class="text-bold"><h2>Total Consumers:<span class="count61" id="totaldefaulters"></span></h2></div>

                                            </li>
                                        </ul>
                                        <div class="col-lg-10 col-lg-offset-1">
                                            <div class="content-group" id="new-visitors"></div>
                                        </div>
                                    </div>

                                    <div class="col-lg-6">
                                        <ul class="list-inline text-center">
                                            <li>
                                                <a href="#" class="btn border-warning-400 text-warning-400 btn-flat btn-rounded btn-icon btn-xs valign-text-bottom"><i class=" icon-briefcase3"></i></a>
                                            </li>
                                            <li class="text-left">
                                                <div class="text-bold"><h2>Default Amount:<span class="count6" id="totalarrears"></span><span id="tlarrears"></span></h2></div>

                                            </li>
                                        </ul>

                                        <div class="col-lg-10 col-lg-offset-1">
                                            <div class="content-group" id="new-sessions"></div>
                                        </div>
                                    </div>
                                    </div>
                    </div>  -->
                    
                           <!--  Page container -->
                           
	
			<!-- Main content -->
		
 
	<div class="panel panel-flat">
                           <div class="panel-heading" style="height:50px;">
                                <h4 class="panel-title text-bold" align="left" style="margin-top: -10px;">Feeder Outage YesterDay Summary<span id="yesterDayDate" style="color: green;"></span></h4>

                            </div> 
				 <div class="container-fluid">
				
					<table class="table" width="100%">
						<thead>
							<tr class="bg-blue">
								<th>Feeder Name</th>
					            <th>1 Phase</th>
					            <th>2 Phase</th>
					            <th>3 Phase</th>
					            <th>No Supply</th>
					           
							</tr>
						</thead>
						
					</table>
					 <div style="height: 406px; overflow: auto">
					 <table class="table">
					 <tbody id="feederData">
							
						</tbody>
					 </table>
					</div>
				</div>
				
				</div>
		
	
	
<div class="content-group-sm" id="app_sales"></div>
						<div id="monthly-sales-stats"></div>

			<!-- /main content -->


                    </div>
                            </div>
                       
                           <div class="col-lg-4">
                            <div class="row">
                            <div class="container-fluid">
				<div class="panel panel-flat" style="height: 550px;">
					<div class="panel-heading"  style=" height: 50px; background-color: #03A9F4;">
						<h6 class="panel-title text-semibold "  style="margin-top: -10px; margin-left: -49px;" align="center">FEEDER OUTAGE SUMMARY GRAPHICAL VIEW</h6>
						<div class="heading-elements">
							<ul class="icons-list">
								<li><a data-action="collapse"></a></li>
								<li><a data-action="reload"></a></li>
								<li><a data-action="close"></a></li>
							</ul>
						</div>
					</div>

					<div class="panel-body" align="center">
					
							<div class="chart svg-center" align="center" id="Feedercontainer"
								style="width: 390px; margin-top: 15px;"></div>
						
					</div>
				</div>
				</div>
			</div>
                      
                        </div> 
                            </div>
                            </div><!-- End Defaulters/Arrears -->
                            
                            
                            
                            
                            
