<%@include file="/WEB-INF/decorators/taglibs.jsp"%>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">


<script type="text/javascript">

var samePeriodDate="";
$(window).load(function() {
	
    //months += d2.getMonth();)
    var makeDate = new Date();
    makeDate = new Date(makeDate.setMonth(makeDate.getMonth() - 2));
	samePeriodDate=moment(makeDate).format('DD-MMM-YY');
	$("#samePeriodDate").html("("+samePeriodDate+")");
	$("#samePeriodDate1").html("("+samePeriodDate+")");
	 $.ajax({
         type:"GET",
         url:"./getBillingProgress",
         dataType:"json",
         success:function(response){

     $.each(
             response,
             function(key, val) {


            	                 $("#mrsLive1").html(parseInt(val.mrs_today));
            	                 $("#count_today").html(parseInt(val.count_today));
            	                // alert(parseInt(val.count_today));
            	                 $("#assessment").html(nFormatter(val.assessment));
            	                /*  var a1=nFormatter(val.assessment).split(".");
            	                 var b1;
            	                 if((a1.toString()).includes(",")){
            	                	  b1=a1[1].substr(1,6);
            	                 }else{
            	                	 a1=a1.toString();
            	                	  b1=a1.substr(1,6); 
            	                 }
            					   */
            					  //$("#billass1").html(b1);

            	                 $("#totalconsumers").html(parseInt(val.total_consumers));
            	                 $("#tobeBilled").html(parseInt(val.tobeBilled));
            	$("#assessmentcurrent").html(nFormatter(val.assessmentcurrent));
            	/*  var a2=nFormatter(val.assessmentcurrent).split(".");
            	 var b2;
            	 if((a2.toString()).includes(",")){
            		 b2=a2[1].substr(1,6);
            	 }else{
            		 a2=a2.toString();
            		  b2=a2.substr(1,6);
            	 } */
            					  //$("#billCurrentass").html(b2);
            	$("#thisMonthsoFar").html(parseInt(val.billed));
            	$("#thisMonthsoFar1").html(parseInt(val.tobeBilled));
            	                 $("#currentbilled").html(parseInt(val.billed));
            	                 $("#billingPerc").html(val.billingPerc);

            	$("#tobeBilledPrevious").html(parseInt(val.tobeBilledPrevious));
            	$("#billedPrevious").html(parseInt(val.billedPrevious));
            	$("#billedPrevious1").html(parseInt(val.tobeBilledPrevious))
            	//$("#total_consumersPrevious").html(parseInt(val.total_consumersPrevious)); 
            	 
            	$("#assessmentPrevious1").html(nFormatter(val.assessmentPrevious));
            	
            	/*  var a3=nFormatter(val.assessmentPrevious).split(".");
            	 var b3;
            	 if((a3.toString()).includes(",")){
            		 b3=a3[1].substr(1,6);
            	 }else{
            		 a3=a3.toString();
            		 b3=a3.substr(1,6);
            	 } */
            	 
            	 var up="<img src='resources/images/progressUp.png' />"
            		 var down="<img src='resources/images/progressDown.png' />"
            		 var assementpercent=val.assessmentDiff;
            	 if(val.IncreaseorDescAss=="UP"){
            		 document.getElementById("assesmentimage").innerHTML = up+"(Current Month)";
            		 document.getElementById("percentageDiffMonthAssessment").innerHTML =assementpercent+"%";
            		 document.getElementById("percentageDiffMonthAssessment").style.color = "green";
            	 }else if(val.IncreaseorDescAss=="DOWN"){
            		 document.getElementById("assesmentimage").innerHTML = down+"(Current Month)";
            		 document.getElementById("percentageDiffMonthAssessment").innerHTML =assementpercent+"%";
            		 document.getElementById("percentageDiffMonthAssessment").style.color = "red";
            	 }
            	
            	/*  if(val.assessmentcurrent<val.assessmentPrevious)
       		  {
       		    //document.getElementById("assesmentimage").innerHTML = down+"(Current Month)";
       		    var percentMonthDiffAssesment = (( val.assessmentPrevious -val.assessmentcurrent)/ val.assessmentPrevious)*100;

       		    var perc=parseFloat(percentMonthDiffAssesment).toFixed(2);
                           if(perc<0){
                           perc= perc*-1;
                           }
                   document.getElementById("percentageDiffMonthAssessment").innerHTML=perc+"%";
                   document.getElementById("percentageDiffMonthAssessment").style.color = "red";
       		  }else if(parseFloat(val.assessmentcurrent).toFixed(0)>parseFloat(val.assessmentPrevious).toFixed(0)){
       		    //document.getElementById("assesmentimage").src = "resources/images/progressUp.png";
       		    var percentMonthDiffAssesment = ((val.assessmentcurrent -  val.assessmentPrevious)/val.assessmentcurrent)*100;

       		    var perc=parseFloat(percentMonthDiffAssesment).toFixed(2);
                           if(perc<0){
                           perc= perc*-1;
                           }
                   document.getElementById("percentageDiffMonthAssessment").innerHTML= perc+"%";
                   document.getElementById("percentageDiffMonthAssessment").style.color = "green";
       		  }else{

       		   // document.getElementById("assesmentimage").src = "";
       		    document.getElementById("percentageDiffMonthAssessment").innerHTML="0%";
       		    document.getElementById("percentageDiffMonthAssessment").style.color = "#333333";
       		  }	 */			 
            	//$("#BillPreviousass").html(b3);
            	$("#billingPercPrevious").html(val.billingPercPrevious);
            	
            	$("#percentMonthDiff").html(val.percentMonthDiff);
            	$("#unBilled").html(parseInt(val.tobeBilled)-parseInt(val.billed));


if(parseInt(val.billingPerc) > parseInt(val.billingPercPrevious))
{

$("#difference1").html((val.percentMonthDiff));
$("#difference2").html(up);

}
else{
$("#difference1").html(val.percentMonthDiff);
$("#difference2").html(down);

}

Highcharts.chart('billprg', {
    chart: {
        type: 'bar'
    },
    title: {
        text: 'Billing Progress'
    },
 /*    subtitle: {
        text: 'Source: <a href="https://en.wikipedia.org/wiki/World_population">Wikipedia.org</a>'
    }, */
    xAxis: {
        categories: ['Billed', 'Assessment','Payments'],
        title: {
            text: null
        }
    },
    yAxis: {
        min: 0,
      /*   title: {
            text: 'Population (millions)',
            align: 'high'
        }, */
        labels: {
            overflow: 'justify'
        }
    },
    tooltip: {
        valueSuffix: ' '
    },
    plotOptions: {
        bar: {
            dataLabels: {
                enabled: true
            }
        }
    },
    legend: {
        layout: 'vertical',
        align: 'right',
        verticalAlign: 'top',
        x: 10,
        y: 18,
        floating: true,
        borderWidth: 1,
        backgroundColor: ((Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'),
        shadow: true
    },
    credits: {
        enabled: false
    },
    series: [{
        name: 'This Month',
        data: [parseInt(val.billed), parseInt(val.assessmentcurrent),parseInt(val.assessmentPrevious)]
    }, {
        name: 'Previouse Month',
        data: [parseInt(val.billedPrevious),parseInt(val.assessmentPrevious),560]
    }/*  {
        name: 'Previouse Month',
        data: [parseInt(val.billedPrevious), 954,350]
    } */]
});
             });
     
   /*    $('.count').each(function () {
		  var $this = $(this);
		  jQuery({ Counter: 0 }).animate({ Counter: $this.text() }, {
		    duration: 20000,
		    easing: 'swing',
		    step: function () {
		      $this.text(this.Counter.toFixed(1));
		    }
		  });
		}); 
      $('.count1').each(function () {
		  var $this = $(this);
		  jQuery({ Counter: 0 }).animate({ Counter: $this.text() }, {
		    duration: 20000,
		    easing: 'swing',
		    step: function () {
		      $this.text(Math.ceil(this.Counter));
		    }
		  });
		}); */
         }
     }); 
	
});

function showrecordminus(param)
{
	$("#drilldown").show();
	$("#drilldown1").hide();
	$("#popuplocationwiseBody1").hide();
	$("#popuplocationwiseBody2").hide();
	
	$("#popuplocationwiseBodydd1").hide();
	$("#popuplocationwiseBodydd2").hide();
	$("#popuplocationwiseBodydd3").hide();
	$("#popuplocationwiseBodycc1").hide();
	$("#popuplocationwiseBodycc2").hide();
	}

function showrecordCCmin(param)
{$("#testCC1").show();
$("#testCC2").hide();
	$("#popuplocationwiseBodycc1").hide();
$("#popuplocationwiseBodycc2").hide();
	
	
	}
	
function showrecordDDmin(param)
{
	$("#testDD1").show();
	$("#testDD2").hide();
	$("#popuplocationwiseBodydd1").hide();
	$("#popuplocationwiseBodydd2").hide();
	$("#popuplocationwiseBodydd3").hide();
	
	}
function showrecord(param){

	$("#drilldown").hide();
	$("#drilldown1").show();
	$("#popuplocationwiseBody1").show();
	$("#popuplocationwiseBody2").show();
	var sitecode="2109";
	$.ajax({
	    type:"GET",
	    url:"./getDrillDownBillingProgress",
	    dataType:"json",
	    data:{
	    	sitecode:sitecode,
	    },
	    success:function(response){
	    	
	    	var htmldata="";
	    	for(var i=0;i<response.length;i++){
	    	var html1="<tr>"+
		     "<td style='width: 1px;'><span id='testDD1' onclick='return showrecorddd(this.id);' class='fa fa-fw fa-plus-circle'  style='color:green;'></span><span id='testDD2' onclick='return showrecordDDmin(this.id);' class='fa fa-fw fa-minus-circle'  style='color:red;display:none;'></span></td>"+
		      "<td>"+response[0].locationName+"</td>"+
			  "<td>"+response[0].total_consumers+"</td>"+
			  "<td>"+response[0].tobeBilled+"</td>"+
			  "<td>"+response[0].billed+"</td>"+
			  "<td>"+response[0].billingPerc+"</td>"+
			  "<td>"+nFormatter(response[0].assessment)+"</td>"+
			  
			   "</tr>"
			   htmldata+=html;
	    	}
			var html2="<tr>"+
		     "<td style='width: 1px;'><span id='testCC1' onclick='return showrecordcc(this.id);' class='fa fa-fw fa-plus-circle'  style='color:green;'></span><span id='testCC2' onclick='return showrecordCCmin(this.id);' class='fa fa-fw fa-minus-circle'  style='color:red;display:none;'></span></td>"+
		      "<td>"+response[1].locationName+"</td>"+
			  "<td>"+response[1].total_consumers+"</td>"+
			  "<td>"+response[1].tobeBilled+"</td>"+
			  "<td>"+response[1].billed+"</td>"+
			  "<td>"+response[1].billingPerc+"</td>"+
			  "<td>"+nFormatter(response[1].assessment)+"</td>"+
			   "</tr>"  
			   
			  
			   $("#popuplocationwiseBody1").html(html1);
			   $("#popuplocationwiseBody2").html(html2);
			   
	                 
	    }
	}); 

 
	
}

function showrecordcc(param){

	$("#testCC1").hide();
	$("#testCC2").show();
	$("#popuplocationwiseBodycc1").show();
	$("#popuplocationwiseBodycc2").show();
	var sitecode="21091";
	$.ajax({
	    type:"GET",
	    url:"./getDrillDownBillingProgress",
	    dataType:"json",
	    data:{
	    	sitecode:sitecode,
	    },
	    success:function(response){
	    	
	    	
	    	var html1="<tr>"+
		     "<td style='width: 1px;'></td>"+
		      "<td>"+response[0].locationName+"</td>"+
			  "<td>"+response[0].total_consumers+"</td>"+
			  "<td>"+response[0].tobeBilled+"</td>"+
			  "<td>"+response[0].billed+"</td>"+
			  "<td>"+response[0].billingPerc+"</td>"+
			  "<td>"+nFormatter(response[0].assessment)+"</td>"+
			   "</tr>"
			var html2="<tr>"+
		     "<td style='width: 1px;'></td>"+
		      "<td>"+response[1].locationName+"</td>"+
			  "<td>"+response[1].total_consumers+"</td>"+
			  "<td>"+response[1].tobeBilled+"</td>"+
			  "<td>"+response[1].billed+"</td>"+
			  "<td>"+response[1].billingPerc+"</td>"+
			  "<td>"+nFormatter(response[1].assessment)+"</td>"+
			   "</tr>"  
			   
			  $("#popuplocationwiseBodycc1").html(html1);
	          $("#popuplocationwiseBodycc2").html(html2);
			 
	                 
	    }
	}); 
	
}

function showrecorddd(param){

$("#testDD2").show();
$("#testDD1").hide();
$("#popuplocationwiseBodydd1").show();
$("#popuplocationwiseBodydd2").show();
$("#popuplocationwiseBodydd3").show();
var sitecode="21092";
$.ajax({
    type:"GET",
    url:"./getDrillDownBillingProgress",
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
		  "<td>"+response[i].tobeBilled+"</td>"+
		  "<td>"+response[i].billed+"</td>"+
		  "<td>"+response[i].billingPerc+"</td>"+
		  "<td>"+nFormatter(response[i].assessment)+"</td>"+
		   "</tr>"
		  htmldata+=html1;
    	}
		
		   $("#popuplocationwiseBodydd1").html(htmldata);
      
		 
		   
                 
    }
}); 

}
var html=""; 
var locationbillng="";
var Namebillng=""; 
function Locationwise(){
	
	var sitecode="ALL";
	/* $('#popuplocationwise').attr("data-toggle", "modal"); */
	 $('#popuplocationwise').attr("data-target","#modal_full"); 
	 $.ajax({
		    type:"GET",
		    url:"./getDrillDownBillingProgress",
		    dataType:"json",
		    data:{
		    	sitecode:sitecode,
		    },
		    success:function(response){
		    	var htmldata="";
		    	if((response[0].locationTitle)=="SUBDIVISION"){
		    		  $("#Namebillng").html(branchName); 
		    		  $("#billng1").html(branchName);
		    		for(var i=0;i<response.length;i++){
			    		
				    	html="<tr>"+
				    	 "<td></td>"+
					      "<td>"+response[i].locationName+"</td>"+
						  "<td>"+response[i].total_consumers+"</td>"+
						  "<td>"+response[i].tobeBilled+"</td>"+
						  "<td>"+response[i].billed+"</td>"+
						  "<td>"+response[i].billingPerc+"</td>"+
						  "<td>"+nFormatter(response[i].assessment)+"</td>"+
						   "</tr>"
						   $("#locationbillng").html(response[i].locationTitle);
						   $("#location1").html(response[i].locationTitle);
		                  htmldata+=html;
				    	}
						   $("#popuplocationwiseBody").html(htmldata);
		    	}else{
		    		$("#Namebillng").html(branchName);
		    		$("#billng1").html(branchName);
		    		for(var i=0;i<response.length;i++){
			    		
				    	html="<tr>"+
					     "<td style='width: 1px;'><span id='drilldown' onclick='return showrecord(this.id);' class='fa fa-fw fa-plus-circle'  style='color:green;'></span><span id='drilldown1' onclick='return showrecordminus(this.id);' class='fa fa-fw fa-minus-circle' style='color: red; display:none; ' ></span></td>"+
					      /* "<td><a data-toggle='collapse'
							data-parent='#accordionss-${i}'
							href='#collapsetwo-${i}' class='collapsed'> <i
							class='fa fa-fw fa-plus-circle txt-color-green' style="float: left; margin-left: -21px;"></i>
							<i class='fa fa-fw fa-minus-circle txt-color-red' style='float: left; margin-left: -21px;'></i></a></td>"+ */
					      "<td>"+response[i].locationName+"</td>"+
						  "<td>"+response[i].total_consumers+"</td>"+
						  "<td>"+response[i].tobeBilled+"</td>"+
						  "<td>"+response[i].billed+"</td>"+
						  "<td>"+response[i].billingPerc+"</td>"+
						  "<td>"+nFormatter(response[i].assessment)+"</td>"+
						   "</tr>"
						   $("#locationbillng").html(response[i].locationTitle);
						   $("#location1").html(response[i].locationTitle);
		                  htmldata+=html;
				    	}
						   $("#popuplocationwiseBody").html(htmldata);
		    	}
		    	
				   
				   $("#popuplocationwiseBody1").hide();
					$("#popuplocationwiseBody2").hide();
					
					$("#popuplocationwiseBodydd1").hide();
					$("#popuplocationwiseBodydd2").hide();
					$("#popuplocationwiseBodydd3").hide();
					$("#popuplocationwiseBodycc1").hide();
					$("#popuplocationwiseBodycc2").hide();       
		    }
		}); 

	 
	
}


function Tariffwise(){

	
	/* $('#popuplocationwise').attr("data-toggle", "modal"); */
	 $('#popupTariffwise').attr("data-target","#modal_fulltariff"); 
	$.ajax({
	     type:"GET",
		 url:"./getBillingTariffDrillDown",
		 dataType:"json",
		 success: function(response){
			var htmlTariff="";
			
			for(var i=0;i<response.length;i++){
		var	 html="<tr>"+
		      "<td>"+response[i].tariffDescription+"</td>"+
		      "<td>"+response[i].total_consumers+"</td>"+
			  "<td>"+response[i].tobeBilled+"</td>"+
			  "<td>"+response[i].billed+"</td>"+
			  "<td>"+response[i].billingPerc+"</td>"+
			  "<td>"+nFormatter(response[i].assessment)+"</td>"+
			   "</tr>"
			  htmlTariff+=html;
			}
			   $("#popupTariffWiseBody").html(htmlTariff);
		 },
	});
}

function MeterStatusWiseBilling(){

	
	 	/* $('#popuplocationwise').attr("data-toggle", "modal"); */
	 	 $('#popupMtrWiseBilling').attr("data-target","#modal_fullMtrWiseBilling"); 
	 	$.ajax({
	 	     type:"GET",
	 		 url:"./getMeterStatusWiseBillng",
	 		 dataType:"json",
	 		 success: function(response){
	 			var htmlTariff="";
	 			
	 			for(var i=0;i<response.length-1;i++){
	 				
	 		var	 html="<tr>"+
	 		      "<td>"+response[i][0]+"</td>"+
	 		      "<td>"+response[i][1]+"</td>"+
	 			  "<td>"+response[i][2]+"</td>"+
	 			 
	 			   "</tr>"
	 			  htmlTariff+=html;
	 			
	 			}
	 			
	 			$("#locationMtrWiseBilling").html(response[3][1]);
	 			$("#NameMtrWiseBilling").html(branchName);
	 			   $("#popupMtrWiseBilling12").html(htmlTariff);
	 		 },
	 	});
		
		}
</script>


<div class="container-fluid">

	<div class="row">
		<!-- Billing Progress -->
		<div class="col-lg-12">
			<div class="panel panel-flat">
				<div class="panel-heading" style="height:50px; background-color: #66BB6A;" >
					<h3 class="panel-title text-bold" align="center" style="margin-top: -10px;">Billing Progress</h3>
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
				<div class="container-fluid">

					<div class="panel-heading">
						<h6 class="panel-title">Today's Live Progress</h6>

					</div>
					<!-- <div class="col-lg-4">
						<div class="panel bg-blue-400">
							<div class="panel-body">
								<div class="heading-elements">
									<ul class="icons-list">
										<li><a data-action="reload"></a></li>
									</ul>
								</div>

								<h3 class="no-margin count1">
									<span id="mrsLive1"></span>
								</h3>
								FI's Live &nbsp; <i class="icon-stats-growth"></i>
								<div class="text-muted text-size-small">view on map</div>
							</div>

							<div id="today-revenue"></div>
						</div>
					</div> -->
					<div class="col-lg-6">
						<!-- Today's revenue -->
						<div class="panel bg-pink-400">
							<div class="panel-body">
								<div class="heading-elements">
									<ul class="icons-list">
										<li><a data-action="reload"></a></li>
									</ul>
								</div>

								<h3 class="no-margin ">
									<span id="count_today" class="count1"></span>
								</h3>
								Billed &nbsp; <i class="icon-file-check2"></i>
								<div class="text-muted text-size-small">Today So For</div>
							</div>

							<div id="today-revenue"></div>
						</div>
					</div>
					<div class="col-lg-6">
						<!-- Today's revenue -->
						<div class="panel bg-blue-400">
							<div class="panel-body">
								<div class="heading-elements">
									<ul class="icons-list">
										<li><a data-action="reload"></a></li>
									</ul>
								</div>

								<h3 class="no-margin ">
									<span id="assessment" class="count"></span><span id="billass1"></span>
								</h3>
								COLLECTION &nbsp;<i class="icon-flip-horizontal2"></i>

								<div class="text-muted text-size-small">Today So For</div>
							</div>

							<div id="today-revenue"></div>
						</div>
					</div>
					<div class="row">
						<div class="container-fluid">
							<div class="panel-heading">
								<h6 class="panel-title">Current Month Progress</h6>

							</div>
							<div class="row text-center">
								<div class="col-md-3">
									<div class="content-group">
										<h5 class="text-semibold no-margin">
											<i class="icon-users4 position-left text-slate "></i><span
												class="count1" id="totalconsumers"></span>
										</h5>
										<span class="text-muted text-size-small">Total
											Consumers</span>
									</div>
								</div>

								<div class="col-md-3">
									<div class="content-group">
										<h5 class="text-semibold no-margin">
											<i class="icon-pencil7 position-left text-slate "></i> <span
												class="count1" id="tobeBilled"></span>
										</h5>
										<span class="text-muted text-size-small">To Be Billed</span>
									</div>
								</div>

								<div class="col-md-3">
									<div class="content-group">
										<h5 class="text-semibold no-margin">
											<i class="icon-file-check2 position-left text-slate "></i><span
												class="count1" id="currentbilled"></span>
										</h5>
										<span class="text-muted text-size-small">Billed</span>
									</div>
								</div>
								<div class="col-md-3">
									<div class="content-group">
										<h5 class="text-semibold no-margin">
											<i class="icon-file-check2 position-left text-slate "></i><span
												class="count1" id="unBilled"></span>
										</h5>
										<span class="text-muted text-size-small">UnBilled</span>
									</div>
								</div>

							</div>
							<div class="row">
								<div class="container-fluid">
									<div class="panel-heading">
										<h6 class="panel-title">Current vs Previous Month
											Progress(Billing)</h6>
									</div>
									<div class="col-md-12 text-center">
										<div class="col-md-4">
											<div class="content-group">
												<h5 class="text-semibold no-margin">
													<i class="icon-users4 position-left text-slate "></i><span
													class="count1"	 id="thisMonthsoFar"></span>/<span id="thisMonthsoFar1" class="count1"></span>
												</h5>
												<span class="text-muted text-size-small">This Month
													So Far</span>
											</div>
										</div>

										<div class="col-md-4">
											<div class="content-group">
												<h5 class="text-semibold no-margin">
													<i class=""></i> <span class="count" id="billingPerc"></span>%
												</h5>
												<span class="text-muted text-size-small">Percent</span>
											</div>
										</div>

										<div class="col-md-4">
											<div class="content-group">
												<h5 class="text-semibold no-margin">
													<i class=""></i> <span class="count" id="difference1"></span>%
												</h5>
												<span id="difference2"></span>
											</div>
										</div>
										</div>
										<div class="col-md-12 text-center">

										<div class="col-md-4">
											<div class="content-group">
												<h5 class="text-semibold no-margin">
													<i class="icon-file-check2 position-left text-slate "></i><span
														 id="billedPrevious" class="count1"></span>/<span id="billedPrevious1" class="count1"></span>
												</h5>
												<span class="text-muted text-size-small">Same Period
													Previous Bill cycle</span><span id="samePeriodDate" style="color: green;"></span>
											</div>
										</div>
										<div class="col-md-4">
											<div class="content-group">
												<h5 class="text-semibold no-margin">
													<i class=""></i><span class="count"
														id="billingPercPrevious"></span>%
												</h5>
												<span class="text-muted text-size-small">Percent</span>
											</div>
										</div>
										<div class="col-md-4">
											<div class="content-group">
												<h5 class="text-semibold no-margin">
													<i class=""></i> <span class="count" id="difference1"></span>%
												</h5>
												<span id="difference2"></span>
											</div>
										</div>


									</div>

								</div>
							</div>
							<div class="row">
								<div class="container-fluid">
									<div class="panel-heading">
										<h6 class="panel-title">Current vs Previous Month
											Progress(Collection)</h6>
									</div>
									<div class="col-md-12 text-center">
										<div class="col-md-4">
											<div class="content-group">
												<h5 class="text-semibold no-margin">
													<i class="icon-users4 position-left text-slate "></i><span
														class="count" id="assessmentcurrent"></span><span id="billCurrentass"></span>
												</h5>
												<span class="text-muted text-size-small">This Month
													So Far</span>
											</div>
										</div>
										<div class="col-md-4">
											<div class="content-group">
												<h5 class="text-semibold no-margin">
													<i class="icon-file-check2 position-left text-slate "></i><span
														class="count" id="assessmentPrevious1"></span><span id="BillPreviousass"></span>
												</h5>
												<span class="text-muted text-size-small">Same Period
													Pervious Bill Cycle</span><span id="samePeriodDate1" style="color: green;"></span>
											</div>
										</div>
										<div class="col-md-4">
											<div class="content-group">
												<h5 class="text-semibold no-margin">
													<i class=""></i><span class="count" id="percentageDiffMonthAssessment"></span>
												</h5>
												<span id="assesmentimage" style="margin-left: 77px;"></span>
											</div>
										</div>

									</div>

								</div>
							</div>
						</div>
						<div class="content-group-sm" id="app_sales"></div>
						<div id="monthly-sales-stats"></div>
					</div>
					<div class="row">
						<div class="col-lg-12">
							<div class="row">
								<div class="container-fluid">

									<div class="col-lg-4" align="center">
										<button type="button"
											class="btn bg-teal-400 btn-labeled btn-rounded"
											data-toggle="modal" data-target="#modal_full"
											onclick="return Locationwise();">
											<b><i class="icon-location4"></i></b>Location Wise
										</button>
									</div>
									<div class="col-lg-4" align="center">
										<button type="button"
											class="btn bg-teal-400 btn-labeled btn-rounded" data-toggle="modal" data-target="#modal_fulltariff"
											onclick="return Tariffwise();">
											<b><i class="icon-coin-dollar"></i></b> Tariff Wise
										</button>

									</div>
									<div class="col-lg-4" align="center">

										<button type="button"
											class="btn bg-teal-400 btn-labeled btn-rounded" data-toggle="modal" data-target="#modal_fullMtrWiseBilling"
											onclick="return MeterStatusWiseBilling();">
											<b><i class="icon-meter-fast"></i></b> Meter Status Wise
										</button>

									</div>
								</div>
							</div>

						</div>
					</div>
				</div>
			</div>
		</div>
		
	</div>
	<!-- end  Billing Progress -->
</div>
<div id="modal_full" class="modal fade">
	<div class="modal-dialog modal-full" style="margin-top: 160px;">
		<div class="modal-content">
			<div class="modal-header bg-teal">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h6 class="modal-title">Billing Progress</h6>
			</div>
			<div class="modal-body">
			<div class="row" style="margin-left: 21px;">
		    <span id="location1" style="color: purple;"></span><span>:</span><span id="billng1"></span>
			</div>
				<div class="row">
					<div class="container-fluid">
						<div class="">
							<div class="panel-body">
								<table class="table" id="popupLocationwise">
									<thead>
										<tr class="bg-teal">
											<th></th>
											<th>Location Name</th>
											<th>Total Installation</th>
											<th>To be billed</th>
											<th>Billed</th>
											<th>Billing %</th>
											<th>Assessment</th>
										</tr>
									</thead>
									<tbody id="popuplocationwiseBody">
									</tbody>
									<tbody id="popuplocationwiseBody2" hidden="true">
									</tbody>
									<tbody id="popuplocationwiseBodycc1" hidden="true">
									</tbody>
									<tbody id="popuplocationwiseBodycc2" hidden="true">
									</tbody>
									<tbody id="popuplocationwiseBody1" hidden="true">
									</tbody>
									<tbody id="popuplocationwiseBodydd1" hidden="true">
									</tbody>
									<tbody id="popuplocationwiseBodydd2" hidden="true">
									</tbody>
									<tbody id="popuplocationwiseBodydd3" hidden="true">
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

<div id="modal_fulltariff" class="modal fade">
	<div class="modal-dialog modal-full" style="margin-top: 160px;">
		<div class="modal-content">
			<div class="modal-header bg-teal">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h6 class="modal-title">Billing Progress</h6>
			</div>
			<div class="modal-body">
			<div class="row" style="margin-left: 21px;">
		    <span id="locationbillng" style="color: purple;"></span><span>:</span><span id="Namebillng"></span>
			</div>
				<div class="row">
					<div class="container-fluid">
						<div class="">
							<div class="panel-body">
								<table class="table" id="popupTariffwise">
									<thead>
										<tr class="bg-teal">
											<th>Tariff</th>
											<th>Total Installation</th>
											<th>To be billed</th>
											<th>Billed</th>
											<th>Billing %</th>
											<th>Assessment</th>
										</tr>
									</thead>
									<tbody id="popupTariffWiseBody">
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
<div id="modal_fullMtrWiseBilling" class="modal fade">
	<div class="modal-dialog modal-full" style="margin-top: 160px;">
		<div class="modal-content">
			<div class="modal-header bg-teal">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h6 class="modal-title">Billing Progress</h6>
			</div>
			<div class="modal-body">
			<div class="row" style="margin-left: 21px;">
		    <span id="locationMtrWiseBilling" style="color: purple;"></span>:<span id="NameMtrWiseBilling"></span>
			</div>
				<div class="row">
					<div class="container-fluid">
						<div class="">
							<div class="panel-body">
								<table class="table" id="popupMtrWiseBilling">
									<thead>
										<tr class="bg-teal">
											<th>Status</th>
											<th>Count</th>
											<th>%</th>
											
										</tr>
									</thead>
									<tbody id="popupMtrWiseBilling12">
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
/* .dataTables_filter {
   
    display: none;
} */
.dt-buttons {
   
    display: none;
}
.dataTables_length {
   
    display: none;
}

.sidebar-default {

    display: initial;
    
    * {
     background-color:#FFFFFF;
     }
}
.fade in{
display: none;
}

</style> 
 