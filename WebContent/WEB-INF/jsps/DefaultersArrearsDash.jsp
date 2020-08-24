    <%@include file="/WEB-INF/decorators/taglibs.jsp"%>
    
    
<script type="text/javascript">
 
$(window).load(function () {
	
	getGraphicalViewForMonth();
/*  $("#fixTable").tableHeadFixer({"head":false,"left":2});
 */	  
	  
	/*   getarrearsData(); */
	  
	
}) ;
 
 /* function getarrearsData(){
	 alert("");
	 $.ajax({
		 type:'GET',
		 url:'./getTop100Arrearsdata',
		 dataType:'json',
		 success:function (response){
			alert(response.length);
			 var arrearsData="";
			 for(var i=0;i<response.length;i++){
				 var html="<tr>"+
				  "<td>"+response[i][2]+"</td>"+
				  "<td>"+response[i][0]+"</td>"+
				  "<td>"+response[i][5]+"</td>"+
				  "<td>"+response[i][3]+"</td>"+
				  "<td>"+nFormatter(response[i][1])+"</td>"+
				
				   "</tr>";
				 arrearsData+=html;
			 }
			 $("#top100Defaulters").html(arrearsData);
		 },
	 });
 } */
 
 var dates=[], ammountdefaults=[];
function getGraphicalViewForMonth()
{
    $.ajax({
        type:"GET",
        url:"./getDefaulters",
        dataType:"json",
        async:false,
        cache:false,
        success:function(response){
            dates =[], ammountdefaults = [];

             $("#totaldefaulters").html(parseInt(response[0].totalInst));
                $("#totalarrears").html(nFormatter(response[0].totalarrears));
                /* var a1=nFormatter(response[0].totalarrears).split(".");
           	 var b1;
           	 if((a1.toString()).includes(",")){
           					 b1=a1[1].substr(1,6);
           	 }else{
           		 a1=a1.toString();
           		 b1=a1.substr(1,6);
           	 } */
           	 //$("#tlarrears").html(b1);
ammountdefaults.push(parseInt(response[0].ARREARS0));
ammountdefaults.push(parseInt(response[0].ARREARS1));
ammountdefaults.push(parseInt(response[0].ARREARS2));
ammountdefaults.push(parseInt(response[0].ARREARS3));
ammountdefaults.push(parseInt(response[0].ARREARS4));

                dates.push(response[0].ACCNO0);
                dates.push(response[0].ACCNO1);
                dates.push(response[0].ACCNO2);
                dates.push(response[0].ACCNO3);
                dates.push(response[0].ACCNO4);

/* 
                $('.count6').each(function () {
          		  var $this = $(this);
          		  jQuery({ Counter: 0 }).animate({ Counter: $this.text() }, {
          		    duration: 50000,
          		    easing: 'swing',
          		    step: function () {
          		      $this.text(this.Counter.toFixed(1));
          		    }
          		  });
          		}); 

                $('.count61').each(function () {
            		  var $this = $(this);
            		  jQuery({ Counter: 0 }).animate({ Counter: $this.text() }, {
            		    duration: 50000,
            		    easing: 'swing',
            		    step: function () {
            		      $this.text(Math.ceil(this.Counter));
            		    }
            		  });
            		});  */

        }
    });


            displayGraph(dates,ammountdefaults);

};

 function displayGraph(dates,ammountdefaults)
{

            Highcharts.chart('DefaulterGraph', {
                chart: {
                    zoomType: 'xy'
                },
                title: {
                     text: 'TOP 5 DEFAULTERS'
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
                        text: 'ARREARS AMOUNT',
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
                    name: 'Arrears Amount.',
                    type: 'column',
                    yAxis: 1,
                    data: ammountdefaults,
                    tooltip: {
                        valueSuffix: ' Rs'
                    }

                }]
            });
}

 function showrecordminusArrears(param)
 {
    $("#drilldownArrears").show();
 	$("#drilldown1Arrears").hide();
 	$("#popuplocationwiseBody1Arrears").hide();
 	$("#popuplocationwiseBody2Arrears").hide();
 	$("#popuplocationwiseBodydd1Arrears").hide();
 	$("#popuplocationwiseBodydd2Arrears").hide();
 	$("#popuplocationwiseBodydd3Arrears").hide();
 	$("#popuplocationwiseBodycc1Arrears").hide();
 	$("#popuplocationwiseBodycc2Arrears").hide();
 	}

 function showrecordCCminArrears(param)
 {$("#testCC1Arrears").show();
 $("#testCC2Arrears").hide();
 	$("#popuplocationwiseBodycc1Arrears").hide();
 $("#popuplocationwiseBodycc2Arrears").hide();
 	
 	
 	}
 	
 function showrecordDDminArrears(param)
 {
 	$("#testDD1Arrears").show();
 	$("#testDD2Arrears").hide();
 	$("#popuplocationwiseBodydd1Arrears").hide();
 	$("#popuplocationwiseBodydd2Arrears").hide();
 	$("#popuplocationwiseBodydd3Arrears").hide();
 	
 	}
 function showrecordArrears(param){

 	$("#drilldownArrears").hide();
 	$("#drilldown1Arrears").show();
 	$("#popuplocationwiseBody1Arrears").show();
 	$("#popuplocationwiseBody2Arrears").show();
 	var sitecode="2109";
 	$.ajax({
 	    type:"GET",
 	    url:"./getArrearsLocationWiseDrillDown",
 	    dataType:"json",
 	    data:{
 	    	sitecode:sitecode,
 	    },
 	    success:function(response){
 	    	
 	    	var htmldata="";
 	    	for(var i=0;i<response.length;i++){
 	    	var html1="<tr>"+
 		     "<td style='width: 1px;'><span id='testDD1Arrears' onclick='return showrecordddArrears(this.id);' class='fa fa-fw fa-plus-circle'  style='color:green;'></span><span id='testDD2Arrears' onclick='return showrecordDDminArrears(this.id);' class='fa fa-fw fa-minus-circle'  style='color:red;display:none;'></span></td>"+
 		      "<td>"+response[0].locationName+"</td>"+
 			  "<td>"+response[0].total_consumers+"</td>"+
 			  "<td>"+nFormatter(response[0].arrears)+"</td>"+
 			 
 			  
 			   "</tr>"
 			   htmldata+=html;
 	    	}
 			var html2="<tr>"+
 		     "<td style='width: 1px;'><span id='testCC1Arrears' onclick='return showrecordccArrears(this.id);' class='fa fa-fw fa-plus-circle'  style='color:green;'></span><span id='testCC2Arrears' onclick='return showrecordCCminArrears(this.id);' class='fa fa-fw fa-minus-circle'  style='color:red;display:none;'></span></td>"+
 		      "<td>"+response[1].locationName+"</td>"+
 			  "<td>"+response[1].total_consumers+"</td>"+
 			  "<td>"+nFormatter(response[1].arrears)+"</td>"+
 			  
 			   "</tr>"  
 			   
 			  
 			   $("#popuplocationwiseBody1Arrears").html(html1);
 			   $("#popuplocationwiseBody2Arrears").html(html2);
 			   
 	                 
 	    }
 	}); 

  
 	
 }

 function showrecordccArrears(param){

 	$("#testCC1Arrears").hide();
 	$("#testCC2Arrears").show();
 	$("#popuplocationwiseBodycc1Arrears").show();
 	$("#popuplocationwiseBodycc2Arrears").show();
 	var sitecode="21091";
 	$.ajax({
 	    type:"GET",
 	    url:"./getArrearsLocationWiseDrillDown",
 	    dataType:"json",
 	    data:{
 	    	sitecode:sitecode,
 	    },
 	    success:function(response){
 	    	
 	    	
 	    	var html1="<tr>"+
 		     "<td style='width: 1px;'></td>"+
 		      "<td>"+response[0].locationName+"</td>"+
 			  "<td>"+response[0].total_consumers+"</td>"+
 			  "<td>"+nFormatter(response[0].arrears)+"</td>"+
 			
 			   "</tr>"
 			var html2="<tr>"+
 		     "<td style='width: 1px;'></td>"+
 		      "<td>"+response[1].locationName+"</td>"+
 			  "<td>"+response[1].total_consumers+"</td>"+
 			  "<td>"+nFormatter(response[1].arrears)+"</td>"+
 			  
 			   "</tr>"  
 			   
 			  $("#popuplocationwiseBodycc1Arrears").html(html1);
 	          $("#popuplocationwiseBodycc2Arrears").html(html2);
 			 
 	                 
 	    }
 	}); 
 	
 }

 function showrecordddArrears(param){

 $("#testDD2Arrears").show();
 $("#testDD1Arrears").hide();
 $("#popuplocationwiseBodydd1Arrears").show();
 $("#popuplocationwiseBodydd2Arrears").show();
 $("#popuplocationwiseBodydd3Arrears").show();
 var sitecode="21092";
 $.ajax({
     type:"GET",
     url:"./getArrearsLocationWiseDrillDown",
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
 		  "<td>"+nFormatter(response[i].arrears)+"</td>"+
 		
 		   "</tr>"
 		  htmldata+=html1;
     	}
 		
 		   $("#popuplocationwiseBodydd1Arrears").html(htmldata);
       
 		 
 		   
                  
     }
 }); 

 }
 var html=""; 
 var locationArrears="";
 var NameArrears=""; 
 
 var locationRange="";
 var NameMtrRange="";
 function LocationwiseArrears(){
 	
 	var sitecode="ALL";
 	 $('#popuplocationwise').attr("data-target","#modal_fullArrears"); 
 	
 	 $.ajax({
 		    type:"GET",
 		    url:"./getArrearsLocationWiseDrillDown",
 		    dataType:"json",
 		    data:{
 		    	sitecode:sitecode,
 		    },
 		    success:function(response){
 		    	var htmldata="";
		    	if((response[0].locationTitle)=="SUBDIVISION"){
		    		 $("#NameArrears").html(branchName); 
		    		 $("#NameArrears1").html(branchName); 
		    		for(var i=0;i<response.length;i++){
		    			html="<tr>"+
		 			     "<td style='width: 1px;'><span id='drilldownArrears' onclick='return showrecordArrears(this.id);' class='fa fa-fw fa-plus-circle'  style='color:green;'></span><span id='drilldown1Arrears' onclick='return showrecordminusArrears(this.id);' class='fa fa-fw fa-minus-circle' style='color: red; display:none; ' ></span></td>"+
		 			     
		 			      "<td>"+response[i].locationName+"</td>"+
		 				  "<td>"+response[i].total_consumers+"</td>"+
		 				  "<td>"+nFormatter(response[i].arrears)+"</td>"+
		 				 
		 				   "</tr>"
		 				   $("#locationArrears").html(response[i].locationTitle);
		 				  $("#locationArrears1").html(response[i].locationTitle);

		                   $("#locationRange").html(response[i].locationTitle);
		                   $("#NameRange").html(response[i].locationName);
		                   htmldata+=html;
		 				   
		    		}
		    		  $("#popuplocationwiseBodyArrears").html(htmldata);
		    	}else{
		    		 $("#NameArrears").html(branchName); 
		    		 $("#NameArrears1").html(branchName); 
                        for(var i=0;i<response.length;i++){
                        	html="<tr>"+
            			     "<td style='width: 1px;'><span id='drilldownArrears' onclick='return showrecordArrears(this.id);' class='fa fa-fw fa-plus-circle'  style='color:green;'></span><span id='drilldown1Arrears' onclick='return showrecordminusArrears(this.id);' class='fa fa-fw fa-minus-circle' style='color: red; display:none; ' ></span></td>"+
            			     
            			      "<td>"+response[i].locationName+"</td>"+
            				  "<td>"+response[i].total_consumers+"</td>"+
            				  "<td>"+nFormatter(response[i].arrears)+"</td>"+
            				 
            				   "</tr>"
            				   $("#locationArrears").html(response[i].locationTitle);
            				   $("#locationArrears1").html(response[i].locationTitle);

                              $("#locationRange").html(response[i].locationTitle);
                              $("#NameRange").html(response[i].locationName);
                              htmldata+=html;
		    		}
                        $("#popuplocationwiseBodyArrears").html(htmldata);
		    	}
 		    	
 		    	
 				   
 				   $("#popuplocationwiseBody1Arrears").hide();
 	$("#popuplocationwiseBody2Arrears").hide();
 	$("#popuplocationwiseBodydd1Arrears").hide();
 	$("#popuplocationwiseBodydd2Arrears").hide();
 	$("#popuplocationwiseBodydd3Arrears").hide();
 	$("#popuplocationwiseBodycc1Arrears").hide();
 	$("#popuplocationwiseBodycc2Arrears").hide();
 		                 
 		    }
 		}); 

 	 
 	
 }


 function TariffwiseArrears(){

 	
 	 $('#popupTariffwise').attr("data-target","#modal_fulltariffArrears"); 
 	$.ajax({
 	     type:"GET",
 		 url:"./getArrearsTariffWiseDrillDown",
 		 dataType:"json",
 		 success: function(response){
 			var htmlTariff="";
 			
 			for(var i=0;i<response.length;i++){
 		var	 html="<tr>"+
 		      "<td>"+response[i].tariffDescription+"</td>"+
 		      "<td>"+response[i].total_consumers+"</td>"+
 			  "<td>"+nFormatter(response[i].arrears)+"</td>"+
 			 
 			   "</tr>"
 			  htmlTariff+=html;
 			}
 			   $("#popupTariffWiseBodyArrears").html(htmlTariff);
 		 },
 	});
 }
 
 function RangewiseArrears(){

	 	var sitecode="2";
	 	 $('#popupRangewiseArrears').attr("data-target","#modal_fullRangeArrears"); 
	 	$.ajax({
	 	     type:"GET",
	 		 url:"./getArrearsRangeWiseDrillDown",
	 		 dataType:"json",
	 		 data:{
	 		    	sitecode:sitecode,
	 		    },
	 		 success: function(response){
	 			var htmlTariff="";
	 			
	 			for(var i=0;i<response.length;i++){
	 				
	 		var	 html="<tr>"+
	 		      "<td>"+response[i][0]+"</td>"+
	 		      "<td>"+response[i][1]+"</td>"+
	 			  "<td>"+nFormatter(response[i][2])+"</td>"+
	 			 
	 			   "</tr>"
	 			  htmlTariff+=html;
	 			
	 			}
	 			
	 			   $("#popupRangeWiseBodyArrears").html(htmlTariff);
	 		 },
	 	});
	 } 
	
</script>

<div class="container-fluid">

             <div class="row"><!-- Defaulters/Arrears Start -->
                <div class="col-lg-8">
                
                <div class="panel panel-flat">
                            <div class="panel-heading" style="height:50px; background-color: #26A69A;">
                                <h3 class="panel-title text-bold" align="center" style="margin-top: -10px;">Defaulters/Arrears</h3>

                            </div>
                            <div class="container-fluid">
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
                    </div>
                    
                           <!--  Page container -->
                           
	
			<!-- Main content -->
		
 
	<div class="panel panel-flat">
                            <div class="panel-heading" style="height:50px; background-color: #7CB5EC;">
                                <h3 class="panel-title text-bold" align="center" style="margin-top: -10px;">Top 100 Defaulters</h3>

                            </div>
				 <div class="container-fluid">
					<table class="table datatable-scroll-y" width="100%">
						<thead>
							<tr>
								<th>Sub Division</th>
					            <th>Account No</th>
					            <th>Tariff</th>
					            <th>Name</th>
					            <th>Amount</th>
					            <!-- <th>Mobile NO</th> -->
							</tr>
						</thead>
						<tbody >
							<c:forEach var="app" items="${list}">
							 <tr>
							 <td>${app.subdivision}</td>
 							 <td>${app.accno}</td>
 							 <td>${app.tariff}</td>
 							 <td>${app.name}</td>
 							 <td>${app.amount}</td>
 							<%--  <td>${app.mobileno}</td> --%>
							 </tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				
				</div>
		
	
	
<div class="content-group-sm" id="app_sales"></div>
						<div id="monthly-sales-stats"></div>

			<!-- /main content -->

                    <div class="row">
                    <div class="col-lg-12">

                            <div class="container-fluid"> <!-- drop down start -->
                            <div class="row">
                            <div class="container-fluid">

                            <div class="col-lg-4" align="center" >
                            <button type="button" class="btn bg-teal-400 btn-labeled btn-rounded" data-toggle="modal" data-target="#modal_fullArrears"
											onclick="return LocationwiseArrears();"><b><i class="icon-location4"></i></b> Location Wise</button>
                  
                            </div>
                            <div class="col-lg-4" align="center" >
                            <button type="button" class="btn bg-teal-400 btn-labeled btn-rounded" data-toggle="modal" data-target="#modal_fulltariffArrears"
											onclick="return TariffwiseArrears();"><b><i class="icon-coin-dollar"></i></b> Tariff Wise</button>
                          
                            </div>
                            <div class="col-lg-4" align="center" >

                            <button type="button" class="btn bg-teal-400 btn-labeled btn-rounded" data-toggle="modal" data-target="#modal_fullRangeArrears"
											onclick="return RangewiseArrears();"><b><i class="icon-calendar"></i></b> Range Wise</button>
                           
                            </div>
                            </div>
                            </div>
                            </div><!-- drop down end -->

                    </div>
                    </div>

                    </div>
                            </div>
                       
                           <div class="col-lg-4">
                            <div class="row">
                            <div class="container-fluid">
				<div class="panel panel-flat" style="height: 550px;">
					<div class="panel-heading" style=" height: 50px; background-color: #26A69A;">
						<h6 class="panel-title text-semibold" style="margin-top: -10px;" align="center">Defaulters Graphical View</h6>
						<div class="heading-elements">
							<ul class="icons-list">
								<li><a data-action="collapse"></a></li>
								<li><a data-action="reload"></a></li>
								<li><a data-action="close"></a></li>
							</ul>
						</div>
					</div>

					<div class="panel-body" align="center">
					
							<div class="chart svg-center" align="center" id="DefaulterGraph"
								style="width: 300px; margin-top: 25px;"></div>
						
					</div>
				</div>
				</div>
			</div>
                      
                        </div> 
                            </div>
                            </div><!-- End Defaulters/Arrears -->
                            
                            
                             
	<div id="modal_fullArrears" class="modal fade">
	<div class="modal-dialog modal-full" style="margin-top: 160px;">
		<div class="modal-content">
			<div class="modal-header bg-teal">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h6 class="modal-title">Arrears</h6>
			</div>
			<div class="modal-body">
			<div class="row" style="margin-left: 21px;">
		    <span id="locationArrears1" style="color: purple;"></span>:<span id="NameArrears1"></span>
			</div>
				<div class="row">
					<div class="container-fluid">
						<div class="">
							<div class="panel-body">
								<table class="table" id="popupLocationwiseArrears">
									<thead>
										<tr class="bg-teal">
											<th></th>
											<th>Location Name</th>
											<th>Total Installation</th>
											<th>arrears</th>
											
										</tr>
									</thead>
									<tbody id="popuplocationwiseBodyArrears">
									</tbody>
									<tbody id="popuplocationwiseBody2Arrears" hidden="true">
									</tbody>
									<tbody id="popuplocationwiseBodycc1Arrears" hidden="true">
									</tbody>
									<tbody id="popuplocationwiseBodycc2Arrears" hidden="true">
									</tbody>
									<tbody id="popuplocationwiseBody1Arrears" hidden="true">
									</tbody>
									<tbody id="popuplocationwiseBodydd1Arrears" hidden="true">
									</tbody>
									<tbody id="popuplocationwiseBodydd2Arrears" hidden="true">
									</tbody>
									<tbody id="popuplocationwiseBodydd3Arrears" hidden="true">
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

<div id="modal_fulltariffArrears" class="modal fade">
	<div class="modal-dialog modal-full" style="margin-top: 160px;">
		<div class="modal-content">
			<div class="modal-header bg-teal">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h6 class="modal-title">Arrears</h6>
			</div>
			<div class="modal-body">
			<div class="row" style="margin-left: 21px;">
		    <span id="locationArrears" style="color: purple;"></span>:<span id="NameArrears"></span>
			</div>
				<div class="row">
					<div class="container-fluid">
						<div class="">
							<div class="panel-body">
								<table class="table" id="popupTariffwiseArrears">
									<thead>
										<tr class="bg-teal">
											<th>Tariff</th>
											<th>Total Installation</th>
											<th>arrears</th>
											
										</tr>
									</thead>
									<tbody id="popupTariffWiseBodyArrears">
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


<div id="modal_fullRangeArrears" class="modal fade">
	<div class="modal-dialog modal-full" style="margin-top: 160px;">
		<div class="modal-content">
			<div class="modal-header bg-teal">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h6 class="modal-title">Arrears</h6>
			</div>
			<div class="modal-body">
			<div class="row" style="margin-left: 21px;">
		    <span id="locationRange" style="color: purple;"></span>:<span id="NameRange"></span>
			</div>
				<div class="row">
					<div class="container-fluid">
						<div class="">
							<div class="panel-body">
								<table class="table" id="popupRangewiseArrears">
									<thead>
										<tr class="bg-teal">
											<th>Range</th>
											<th>Consumers</th>
											<th>arrears</th>
											
										</tr>
									</thead>
									<tbody id="popupRangeWiseBodyArrears">
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

<style>


</style>