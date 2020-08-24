<%@include file="/WEB-INF/decorators/taglibs.jsp"%>
<script type="text/javascript">
$(window).load(function () {
	
	var makeDate = new Date();
	makeDate = new Date(makeDate.setMonth(makeDate.getMonth() - 2));
	samePeriodDate=moment(makeDate).format('DD-MMM-YY');
	$("#samePeriodmonth1").html("("+samePeriodDate+")");
	var cDate1=new Date();
	$("#currentdate1").html("("+moment(cDate1).format('DD-MMM-YY')+")")
	 $.ajax({
         type:"GET",
         url:"./getAdjustmentDetails",
         dataType:"json",
         success:function(response){

          $.each(response,function(key, val) {
        	  
        	  var total=parseInt(val.approved)+parseInt(val.total_pending)+parseInt(val.rejected);
        	  $("#total_consumer_adj").html(parseInt(total));
        	  $("#approved_consumer").html(parseInt(val.approved));
        	  $("#pending_consumer").html(parseInt(val.total_pending));
        	  $("#approved_amount").html(parseInt(val.approved_amount));
        	  $("#rejected_consumer").html(parseInt(val.rejected));


        	  $("#tripur").html(parseInt(val.TRIPURESHWOR));
        	  $("#banesh").html(parseInt(val.BANESHWOR));
        	  $("#lalitpur").html(parseInt(val.LALITPUR));
        	  $("#bhakta").html(parseInt(val.BHAKTAPUR));
        	  $("#chhetra").html(parseInt(val.CHHETRAPATI));
        	  $("#mahar").html(parseInt(val.MAHARAJGUNJ));
        	  $("#thimi").html(parseInt(val.THIMI));
        	  $("#kirtipur").html(parseInt(val.KIRTIPUR));
        	  $("#mahan").html(parseInt(val.MAHANKALCHAUR));
        	  $("#kamaladi").html(parseInt(val.KAMALADI));
        	  
          });

         }
     });
  
});





function showrecordminusmtr(param)
{
	$("#drilldownmtr").show();
	$("#drilldown1mtr").hide();
	$("#popuplocationwiseBody1mtr").hide();
	$("#popuplocationwiseBody2mtr").hide();
	$("#popuplocationwiseBodydd1mtr").hide();
	$("#popuplocationwiseBodydd2mtr").hide();
	$("#popuplocationwiseBodydd3mtr").hide();
	$("#popuplocationwiseBodycc1mtr").hide();
	$("#popuplocationwiseBodycc2mtr").hide();
	}

function showrecordCCminmtr(param)
{$("#testCC1mtr").show();
$("#testCC2mtr").hide();
	$("#popuplocationwiseBodycc1mtr").hide();
$("#popuplocationwiseBodycc2mtr").hide();
	
	
	}
	
function showrecordDDminmtr(param)
{
	$("#testDD1mtr").show();
	$("#testDD2mtr").hide();
	$("#popuplocationwiseBodydd1mtr").hide();
	$("#popuplocationwiseBodydd2mtr").hide();
	$("#popuplocationwiseBodydd3mtr").hide();
	
	}
function showrecordmtr(param){

	$("#drilldownmtr").hide();
	$("#drilldown1mtr").show();
	$("#popuplocationwiseBody1mtr").show();
	$("#popuplocationwiseBody2mtr").show();
	var sitecode="2109";
	$.ajax({
	    type:"GET",
	    url:"./getMtrLocationWiseDrillDown",
	    dataType:"json",
	    data:{
	    	sitecode:sitecode,
	    },
	    success:function(response){
	    	
	    	var htmldata="";
	    	for(var i=0;i<response.length;i++){
	    	var html1="<tr>"+
		     "<td style='width: 1px;'><span id='testDD1mtr' onclick='return showrecordddmtr(this.id);' class='fa fa-fw fa-plus-circle'  style='color:green;'></span><span id='testDD2mtr' onclick='return showrecordDDminmtr(this.id);' class='fa fa-fw fa-minus-circle'  style='color:red;display:none;'></span></td>"+
		      "<td>"+response[0].locationName+"</td>"+
			  "<td>"+response[0].total_consumers+"</td>"+
			  "<td>"+response[0].normal+"</td>"+
			  "<td>"+response[0].doorlock+"</td>"+
			  "<td>"+response[0].defective+"</td>"+
			  
			   "</tr>"
			   htmldata+=html;
	    	}
			var html2="<tr>"+
		     "<td style='width: 1px;'><span id='testCC1mtr' onclick='return showrecordccmtr(this.id);' class='fa fa-fw fa-plus-circle'  style='color:green;'></span><span id='testCC2mtr' onclick='return showrecordCCminmtr(this.id);' class='fa fa-fw fa-minus-circle'  style='color:red;display:none;'></span></td>"+
		      "<td>"+response[1].locationName+"</td>"+
			  "<td>"+response[1].total_consumers+"</td>"+
			  "<td>"+response[1].normal+"</td>"+
			  "<td>"+response[1].doorlock+"</td>"+
			  "<td>"+response[1].defective+"</td>"+
			   "</tr>"  
			   
			  
			   $("#popuplocationwiseBody1mtr").html(html1);
			   $("#popuplocationwiseBody2mtr").html(html2);
			   
	                 
	    }
	}); 

 
	
}

function showrecordccmtr(param){

	$("#testCC1mtr").hide();
	$("#testCC2mtr").show();
	$("#popuplocationwiseBodycc1mtr").show();
	$("#popuplocationwiseBodycc2mtr").show();
	var sitecode="21091";
	$.ajax({
	    type:"GET",
	    url:"./getMtrLocationWiseDrillDown",
	    dataType:"json",
	    data:{
	    	sitecode:sitecode,
	    },
	    success:function(response){
	    	
	    	
	    	var html1="<tr>"+
		     "<td style='width: 1px;'></td>"+
		      "<td>"+response[0].locationName+"</td>"+
			  "<td>"+response[0].total_consumers+"</td>"+
			  "<td>"+response[0].normal+"</td>"+
			  "<td>"+response[0].doorlock+"</td>"+
			  "<td>"+response[0].defective+"</td>"+
			   "</tr>"
			var html2="<tr>"+
		     "<td style='width: 1px;'></td>"+
		      "<td>"+response[1].locationName+"</td>"+
			  "<td>"+response[1].total_consumers+"</td>"+
			  "<td>"+response[1].normal+"</td>"+
			  "<td>"+response[1].doorlock+"</td>"+
			  "<td>"+response[1].defective+"</td>"+
			   "</tr>"  
			   
			  $("#popuplocationwiseBodycc1mtr").html(html1);
	          $("#popuplocationwiseBodycc2mtr").html(html2);
			 
	                 
	    }
	}); 
	
}

function showrecordddmtr(param){

$("#testDD2mtr").show();
$("#testDD1mtr").hide();
$("#popuplocationwiseBodydd1mtr").show();
$("#popuplocationwiseBodydd2mtr").show();
$("#popuplocationwiseBodydd3mtr").show();
var sitecode="21092";
$.ajax({
    type:"GET",
    url:"./getMtrLocationWiseDrillDown",
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
		  "<td>"+response[i].normal+"</td>"+
			  "<td>"+response[i].doorlock+"</td>"+
			  "<td>"+response[i].defective+"</td>"+
		   "</tr>"
		  htmldata+=html1;
    	}
		
		   $("#popuplocationwiseBodydd1mtr").html(htmldata);
      
		 
		   
                 
    }
}); 

}
var html="";
var locationMtr="";
var NameMtr="";


function Locationwisemtr(){
	
	var sitecode="ALL";
	 $('#popuplocationwise').attr("data-target","#modal_fullmtr"); 
	 $.ajax({
		    type:"GET",
		    url:"./getMtrLocationWiseDrillDown",
		    dataType:"json",
		    data:{
		    	sitecode:sitecode,
		    },
		    success:function(response){
		    	var htmldata="";
		    	if((response[0].locationTitle)=="SUBDIVISION"){
		    		$("#NameMtr1").html(branchName);
		    		$("#NameMtr").html(branchName);
		    		for(var i=0;i<response.length;i++){
		    			html="<tr>"+
					     "<td></td>"+
					      /* "<td><a data-toggle='collapse'
							data-parent='#accordionss-${i}'
							href='#collapsetwo-${i}' class='collapsed'> <i
							class='fa fa-fw fa-plus-circle txt-color-green' style="float: left; margin-left: -21px;"></i>
							<i class='fa fa-fw fa-minus-circle txt-color-red' style='float: left; margin-left: -21px;'></i></a></td>"+ */
					      "<td>"+response[i].locationName+"</td>"+
						  "<td>"+response[i].total_consumers+"</td>"+
						 "<td>"+response[i].normal+"</td>"+
					  "<td>"+response[i].doorlock+"</td>"+
					  "<td>"+response[i].defective+"</td>"+
						   "</tr>"
						   htmldata+=html;
						   $("#locationMtr").html(response[i].locationTitle);
						   $("#locationMtr1").html(response[i].locationTitle);
		    		}
		    		  $("#popuplocationwiseBodymtr").html(htmldata);
		    	}else{
		    		$("#NameMtr1").html(branchName);
		    		$("#NameMtr").html(branchName);
                        for(var i=0;i<response.length;i++){
                        	html="<tr>"+
           			     "<td style='width: 1px;'><span id='drilldownmtr' onclick='return showrecordmtr(this.id);' class='fa fa-fw fa-plus-circle'  style='color:green;'></span><span id='drilldown1mtr' onclick='return showrecordminusmtr(this.id);' class='fa fa-fw fa-minus-circle' style='color: red; display:none; ' ></span></td>"+
           			      /* "<td><a data-toggle='collapse'
           					data-parent='#accordionss-${i}'
           					href='#collapsetwo-${i}' class='collapsed'> <i
           					class='fa fa-fw fa-plus-circle txt-color-green' style="float: left; margin-left: -21px;"></i>
           					<i class='fa fa-fw fa-minus-circle txt-color-red' style='float: left; margin-left: -21px;'></i></a></td>"+ */
           			      "<td>"+response[i].locationName+"</td>"+
           				  "<td>"+response[i].total_consumers+"</td>"+
           				 "<td>"+response[i].normal+"</td>"+
           			  "<td>"+response[i].doorlock+"</td>"+
           			  "<td>"+response[i].defective+"</td>"+
           				   "</tr>"
           				     htmldata+=html;
           				   $("#locationMtr").html(response[i].locationTitle);
           				  $("#locationMtr1").html(response[i].locationTitle);
		    		}
                        $("#popuplocationwiseBodymtr").html(htmldata);
		    	}
		    	

				 
				   
				   $("#popuplocationwiseBody1mtr").hide();
					$("#popuplocationwiseBody2mtr").hide();
					$("#popuplocationwiseBodydd1mtr").hide();
					$("#popuplocationwiseBodydd2mtr").hide();
					$("#popuplocationwiseBodydd3mtr").hide();
					$("#popuplocationwiseBodycc1mtr").hide();
					$("#popuplocationwiseBodycc2mtr").hide();
		    }
		}); 

	 
	
}


function Tariffwisemtr(){

	
	/* $('#popuplocationwise').attr("data-toggle", "modal"); */
	 $('#popupTariffwise').attr("data-target","#modal_fulltariffmtr"); 
	$.ajax({
	     type:"GET",
		 url:"./getMtrTariffWiseDrillDown",
		 dataType:"json",
		 success: function(response){
			var htmlTariff="";
		
			for(var i=0;i<response.length;i++){
		var	 html="<tr>"+
		      "<td>"+response[i].tariffDescription+"</td>"+
		      "<td>"+response[i].total_consumers+"</td>"+
			  "<td>"+response[i].normal+"</td>"+
			  "<td>"+response[i].doorlock+"</td>"+
			  "<td>"+response[i].defective+"</td>"+
			   "</tr>"
			  htmlTariff+=html;
			}
			   $("#popupTariffWiseBodymtr").html(htmlTariff);
		 },
	});
}

</script>



 <div class="container-fluid">
        
			 <div class="row"><!-- Meter status Wise Start -->
                        
                         <div class="col-lg-12"> <!--8 Meter status Wise Start -->
                         <div class="panel panel-flat">
                            <div class="panel-heading" style="height:50px; background-color: #89A1F6;">
                                <h3 class="panel-title text-bold" align="center" style="margin-top: -10px;">Adjustments</h3>
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
							<h6 class="panel-title">Adjustment Details<span id="currentdate" style="color: green;"></span></h6>

						</div>
						
						<div class="col-lg-2">
							<div class="panel bg-blue-400">
							<div class="panel-body">
								<div class="heading-elements">
									<ul class="icons-list">
										<li><a data-action="reload"></a></li>
									</ul>
								</div>

								<h3 class="no-margin ">
									<span id="total_consumer_adj" class="count1"></span>
								</h3>
								Total Consumer &nbsp; <i class="icon-file-check2"></i>
								<div class="text-muted text-size-small">All Branches</div>
							</div>

							<div id="today-revenue"></div>
						</div>
					</div>
					<div class="col-lg-2">
						<div class="panel bg-info-400">
							<div class="panel-body">
								<div class="heading-elements">
									<ul class="icons-list">
										<li><a data-action="reload"></a></li>
									</ul>
								</div>

								<h3 class="no-margin ">
									<span id="approved_consumer" class="count"></span>
								</h3>
								Approved Consumer &nbsp;<i class="icon-flip-horizontal2"></i>

								<div class="text-muted text-size-small">This Month</div>
							</div>

							<div id="today-revenue"></div>
						</div>
					</div>
					<div class="col-lg-2">
						<div class="panel bg-warning-400">
							<div class="panel-body">
								<div class="heading-elements">
									<ul class="icons-list">
										<li><a data-action="reload"></a></li>
									</ul>
								</div>

								<h3 class="no-margin ">
									<span id="pending_consumer" class="count"></span><span id="billass1"></span>
								</h3>
								Pending Consumer &nbsp;<i class="icon-flip-horizontal2"></i>

								<div class="text-muted text-size-small">This Month</div>
							</div>

							<div id="today-revenue"></div>
						</div>
					</div>
					<div class="col-lg-2">
						<div class="panel bg-success-400">
							<div class="panel-body">
								<div class="heading-elements">
									<ul class="icons-list">
										<li><a data-action="reload"></a></li>
									</ul>
								</div>

								<h3 class="no-margin ">
									<span id="approved_amount" class="count"></span>
								</h3>
								Arrears Adjusted Amount &nbsp;<i class="icon-flip-horizontal2"></i>

								<div class="text-muted text-size-small">Approved This Month</div>
							</div>

							<div id="today-revenue"></div>
						</div>
					</div>
					<div class="col-lg-2">
						<div class="panel bg-danger-400">
							<div class="panel-body">
								<div class="heading-elements">
									<ul class="icons-list">
										<li><a data-action="reload"></a></li>
									</ul>
								</div>

								<h3 class="no-margin ">
									<span id="rejected_consumer" class="count"></span>
								</h3>
								Rejected Consumer&nbsp;<i class="icon-flip-horizontal2"></i>

								<div class="text-muted text-size-small">This Month</div>
							</div>

							<div id="today-revenue"></div>
						</div>
					</div>
					</div>
					
					<div class="container-fluid">

						<div class="panel-heading">
							<h6 class="panel-title">Branch Wise Pending Details for Approval<span id="currentdate" style="color: green;"></span></h6>

						</div>
						
						<div class="col-lg-2">
							<div class="panel bg-pink-600">
							<div class="panel-body">
								<div class="heading-elements">
									<ul class="icons-list">
					                		<li class="dropdown">
					                			<a href="#" class="dropdown-toggle" data-toggle="dropdown"><span class="caret"></span></a>
												<ul class="dropdown-menu dropdown-menu-right" style="padding: 0px;min-width: 113px;">
													<li><a href="#" data-toggle="modal" data-target="#modal_default" onclick="setBranchToPopup('TRIPUR_1115')"><i class="icon-menu7"></i> Details</a></li>
												</ul>
					                		</li>
					                </ul>
								</div>

								<h3 class="no-margin ">
									<span id="tripur" class="count1"></span>
								</h3>
								Tripureshwor &nbsp; <i class="icon-file-check2"></i>
								<div class="text-muted text-size-small">Pending</div>
							</div>

							<div id="today-revenue"></div>
						</div>
					</div>
					<div class="col-lg-2">
						<div class="panel bg-green-600">
							<div class="panel-body">
								<div class="heading-elements">
									<ul class="icons-list">
					                		<li class="dropdown">
					                			<a href="#" class="dropdown-toggle" data-toggle="dropdown"><span class="caret"></span></a>
												<ul class="dropdown-menu dropdown-menu-right" style="padding: 0px;min-width: 113px;">
													<li><a href="#" data-toggle="modal" data-target="#modal_default" onclick="setBranchToPopup('BANESH_1112')"><i class="icon-menu7"></i> Details</a></li>
												</ul>
					                		</li>
					                </ul>
								</div>

								<h3 class="no-margin ">
									<span id="banesh" class="count"></span>
								</h3> Baneshwor&nbsp;<i class="icon-flip-horizontal2"></i>

								<div class="text-muted text-size-small">Pending</div>
							</div>

							<div id="today-revenue"></div>
						</div>
					</div>
					<div class="col-lg-2">
						<div class="panel bg-info-700">
							<div class="panel-body">
								<div class="heading-elements">
									<ul class="icons-list">
					                		<li class="dropdown">
					                			<a href="#" class="dropdown-toggle" data-toggle="dropdown"><span class="caret"></span></a>
												<ul class="dropdown-menu dropdown-menu-right" style="padding: 0px;min-width: 113px;">
													<li><a href="#" data-toggle="modal" data-target="#modal_default" onclick="setBranchToPopup('LALITPUR_1118')"><i class="icon-menu7"></i> Details</a></li>
												</ul>
					                		</li>
					                </ul>
								</div>

								<h3 class="no-margin ">
									<span id="lalitpur" class="count"></span>
								</h3> Lalitpur &nbsp;<i class="icon-flip-horizontal2"></i>

								<div class="text-muted text-size-small">Pending</div>
							</div>

							<div id="today-revenue"></div>
						</div>
					</div>
					<div class="col-lg-2">
						<div class="panel bg-indigo">
							<div class="panel-body">
								<div class="heading-elements">
									<ul class="icons-list">
					                		<li class="dropdown">
					                			<a href="#" class="dropdown-toggle" data-toggle="dropdown"><span class="caret"></span></a>
												<ul class="dropdown-menu dropdown-menu-right" style="padding: 0px;min-width: 113px;">
													<li><a href="#" data-toggle="modal" data-target="#modal_default" onclick="setBranchToPopup('BHAKTAPUR_1116')"><i class="icon-menu7"></i> Details</a></li>
												</ul>
					                		</li>
					                </ul>
								</div>

								<h3 class="no-margin ">
									<span id="bhakta" class="count"></span>
								</h3>
								Bhaktapur &nbsp;<i class="icon-flip-horizontal2"></i>

								<div class="text-muted text-size-small">Pending</div>
							</div>

							<div id="today-revenue"></div>
						</div>
					</div>
					<div class="col-lg-2">
						<div class="panel bg-teal-300">
							<div class="panel-body">
								<div class="heading-elements">
									<ul class="icons-list">
					                		<li class="dropdown">
					                			<a href="#" class="dropdown-toggle" data-toggle="dropdown"><span class="caret"></span></a>
												<ul class="dropdown-menu dropdown-menu-right" style="padding: 0px;min-width: 113px;">
													<li><a href="#" data-toggle="modal" data-target="#modal_default" onclick="setBranchToPopup('CHHETRA_1114')"><i class="icon-menu7"></i> Details</a></li>
												</ul>
					                		</li>
					                </ul>
								</div>

								<h3 class="no-margin ">
									<span id="chhetra" class="count"></span>
								</h3>
								Chhetrapati&nbsp;<i class="icon-flip-horizontal2"></i>

								<div class="text-muted text-size-small">Pending</div>
							</div>

							<div id="today-revenue"></div>
						</div>
					</div>
					</div>


					<div class="container-fluid">

						
						
						<div class="col-lg-2">
							<div class="panel bg-pink-600">
							<div class="panel-body">
								<div class="heading-elements">
									<ul class="icons-list">
					                		<li class="dropdown">
					                			<a href="#" class="dropdown-toggle" data-toggle="dropdown"><span class="caret"></span></a>
												<ul class="dropdown-menu dropdown-menu-right" style="padding: 0px;min-width: 113px;">
													<li><a href="#" data-toggle="modal" data-target="#modal_default" onclick="setBranchToPopup('MAHAR_1110')"><i class="icon-menu7"></i> Details</a></li>
												</ul>
					                		</li>
					                </ul>
								</div>

								<h3 class="no-margin ">
									<span id="mahar" class="count1"></span>
								</h3>
								Maharajgunj &nbsp; <i class="icon-file-check2"></i>
								<div class="text-muted text-size-small">Pending</div>
							</div>

							<div id="today-revenue"></div>
						</div>
					</div>
					<div class="col-lg-2">
						<div class="panel bg-green-600">
							<div class="panel-body">
								<div class="heading-elements">
									<ul class="icons-list">
					                		<li class="dropdown">
					                			<a href="#" class="dropdown-toggle" data-toggle="dropdown"><span class="caret"></span></a>
												<ul class="dropdown-menu dropdown-menu-right" style="padding: 0px;min-width: 113px;">
													<li><a href="#" data-toggle="modal" data-target="#modal_default" onclick="setBranchToPopup('THIMI_1117')"><i class="icon-menu7"></i> Details</a></li>
												</ul>
					                		</li>
					                </ul>
								</div>

								<h3 class="no-margin ">
									<span id="thimi" class="count"></span>
								</h3> Thimi&nbsp;<i class="icon-flip-horizontal2"></i>

								<div class="text-muted text-size-small">Pending</div>
							</div>

							<div id="today-revenue"></div>
						</div>
					</div>
					<div class="col-lg-2">
						<div class="panel bg-info-700">
							<div class="panel-body">
								<div class="heading-elements">
									<ul class="icons-list">
					                		<li class="dropdown">
					                			<a href="#" class="dropdown-toggle" data-toggle="dropdown"><span class="caret"></span></a>
												<ul class="dropdown-menu dropdown-menu-right" style="padding: 0px;min-width: 113px;">
													<li><a href="#" data-toggle="modal" data-target="#modal_default" onclick="setBranchToPopup('KIRTIPUR_1119')"><i class="icon-menu7"></i> Details</a></li>
												</ul>
					                		</li>
					                </ul>
								</div>

								<h3 class="no-margin ">
									<span id="kirtipur" class="count"></span><span id="billass1"></span>
								</h3> Kirtipur &nbsp;<i class="icon-flip-horizontal2"></i>

								<div class="text-muted text-size-small">Pending</div>
							</div>

							<div id="today-revenue"></div>
						</div>
					</div>
					<div class="col-lg-2">
						<div class="panel bg-indigo">
							<div class="panel-body">
								<div class="heading-elements">
									<ul class="icons-list">
					                		<li class="dropdown">
					                			<a href="#" class="dropdown-toggle" data-toggle="dropdown"><span class="caret"></span></a>
												<ul class="dropdown-menu dropdown-menu-right" style="padding: 0px;min-width: 113px;">
													<li><a href="#" data-toggle="modal" data-target="#modal_default" onclick="setBranchToPopup('MAHAN_1110')"><i class="icon-menu7"></i> Details</a></li>
												</ul>
					                		</li>
					                </ul>
								</div>

								<h3 class="no-margin ">
									<span id="mahan" class="count"></span><span id="billass1"></span>
								</h3>
								Mahankalchaur &nbsp;<i class="icon-flip-horizontal2"></i>

								<div class="text-muted text-size-small">Pending</div>
							</div>

							<div id="today-revenue"></div>
						</div>
					</div>
					<div class="col-lg-2">
						<div class="panel bg-teal-300">
							<div class="panel-body">
								<div class="heading-elements">
									<ul class="icons-list">
					                		<li class="dropdown">
					                			<a href="#" class="dropdown-toggle" data-toggle="dropdown"><span class="caret"></span></a>
												<ul class="dropdown-menu dropdown-menu-right" style="padding: 0px;min-width: 113px;">
													<li><a href="#" data-toggle="modal" data-target="#modal_default" onclick="setBranchToPopup('KAMALADI_1113')"><i class="icon-menu7"></i> Details</a></li>
												</ul>
					                		</li>
					                </ul>
								</div>

								<h3 class="no-margin ">
									<span id="kamaladi" class="count"></span><span id="billass1"></span>
								</h3>
								Kamaladi&nbsp;<i class="icon-flip-horizontal2"></i>

								<div class="text-muted text-size-small">Pending</div>
							</div>

							<div id="today-revenue"></div>
						</div>
					</div>
					</div>
					
					
					
					<div><div class="row"><h6>&nbsp;</h6>
					
					
					<!-- <div class="col-lg-2">
						<div class="panel bg-teal-300">
							<div class="panel-body">
								<div class="heading-elements">
									<ul class="icons-list">
					                		<li class="dropdown">
					                			<a href="#" class="dropdown-toggle" data-toggle="dropdown"><span class="caret"></span></a>
												<ul class="dropdown-menu dropdown-menu-right" style="padding: 0px;min-width: 113px;">
													<li><a href="#" data-toggle="modal" data-target="#modal_default" onclick="setBranchToPopup('TESTLOC_2222')"><i class="icon-menu7"></i> Details</a></li>
												</ul>
					                		</li>
					                </ul>
								</div>

								<h3 class="no-margin ">
									<span id="kamaladi" class="count"></span><span id="billass1"></span>
								</h3>
								TestLocation&nbsp;<i class="icon-flip-horizontal2"></i>

								<div class="text-muted text-size-small">Pending</div>
							</div>

							<div id="today-revenue"></div>
						</div>
					</div> -->
					
					</div></div>
					

								<!-- <div class="text-center">
									<button type="button"
										class="btn bg-teal-400 btn-labeled btn-rounded"
										data-toggle="modal" data-target="#modal_fullmtr"
										onclick="return Locationwisemtr();">
										<b><i class="icon-location4"></i></b> Location Wise
									</button>

								</div> -->
								




							
					<div><div class="row"><h6>&nbsp;</h6></div></div>
				</div>


			</div>
                            

                        </div>   

		
		</div>
		
		</div>
		
		
		
		
<div id="modal_fullmtr" class="modal fade">
	<div class="modal-dialog modal-full" style="margin-top: 160px;">
		<div class="modal-content">
			<div class="modal-header bg-teal">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h6 class="modal-title">Billing Status Progress</h6>
			</div>
			<div class="modal-body">
			<div class="row" style="margin-left: 21px;">
		    <span id="locationMtr1" style="color: purple;"></span>:<span id="NameMtr1"></span>
			</div>
				<div class="row">
					<div class="container-fluid">
						<div class="">
							<div class="panel-body">
								<table class="table" id="popupLocationwisemtr">
									<thead>
										<tr class="bg-teal">
											<th></th>
											<th>Location Name</th>
											<th>Total Installation</th>
											<th>Normal</th>
											<th>Door Lock</th>
											<th>Defective</th>
					
										</tr>
									</thead>
									<tbody id="popuplocationwiseBodymtr">
									</tbody>
									<tbody id="popuplocationwiseBody2mtr" hidden="true">
									</tbody>
									<tbody id="popuplocationwiseBodycc1mtr" hidden="true">
									</tbody>
									<tbody id="popuplocationwiseBodycc2mtr" hidden="true">
									</tbody>
									<tbody id="popuplocationwiseBody1mtr" hidden="true">
									</tbody>
									<tbody id="popuplocationwiseBodydd1mtr" hidden="true">
									</tbody>
									<tbody id="popuplocationwiseBodydd2mtr" hidden="true">
									</tbody>
									<tbody id="popuplocationwiseBodydd3mtr" hidden="true">
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




<div id="modal_default" class="modal fade">
	<div class="modal-dialog" style="width: 1200px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<!-- <a onclick="printDiv('print-content')"><i
					class="glyphicon glyphicon-print"
					style="top: 0px !important; left: 1175px !important;"></i></a> -->
			</div>
			<div class="modal-body">
				<div class="container-fluid" id="print-content" style="margin-bottom: -40px;">
					<h5 class="modal-title">
						<font color="black" style="font-size: 15pt;"><b>Pending Connection List</b></font> <font color="red" style="font-size: 12pt;">
							<div id="connectionidspan" style="width: 1200px;"></div>
						</font>
					</h5>
					<hr id="ledger_hr" style="margin-top: 4px;">
					<form action="./billpenaltyAdjApproveStatus" method="POST" style="margin-top: -35px;">

			<input type="text" id="connectId" name="connectId" hidden="true">
			<input type="text" id="conStatus" name="conStatus" hidden="true">
			<input type="text" id="branch" name="branch" hidden="true">

			<fieldset class="content-group">
				<!-- <legend class="text-bold">
					BILL OR PENALTY ADJUSTMENT APPROVAL
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<button type="submit" class="btn btn-success btn-ladda"
						data-style="expand-right" data-spinner-size="20"
						onclick="return billDetailsPopUp('CHECKBOXYES',1)">
						<span class="ladda-label">Approve</span>
					</button>
					<button type="submit" class="btn btn-danger btn-ladda"
						data-style="expand-right" data-spinner-size="20"
						onclick="return billDetailsPopUp('CHECKBOXYES',2)">
						<span class="ladda-label">Reject</span>
					</button>

				</legend> -->

				<div class="table-responsive">
					<table class="table datatable-basic table-bordered" id="adjustTable">
						<thead>
							<tr>
								<th>Sl&nbsp;No</th>
								<th>Con&nbsp;No</th>
								<th style="min-width: 180px;">Name</th>
								<th>AreaNo</th>
								<th style="min-width: 137px;">Arrear Adj</th>
								<th style="min-width: 123px;">Penalty Adj</th>
								<th>MonthYear</th>
								<th style="min-width: 113px;">Submit By</th>
								<th style="min-width: 124px;">Submit Date</th>
								<th style="min-width: 91px;">TA By</th>
								<th style="min-width: 102px;">TA Date</th>
								<th style="min-width: 300px;">Remarks</th>
								<c:if test = "${userbdesignation=='General Manager' || userbdesignation=='Manager-Finance division' || userbdesignation=='Developer' || userbdesignation=='Asst Manager- PMU'}">
								<th>Approve</th>
								<th>Reject</th>
								</c:if>
							</tr>
						</thead>
						<tbody id="tableBodyId">
						</tbody>
						
					</table>
				</div>
			</fieldset>



		</form>

				</div>
			</div>
			<div class="modal-footer">
			<button type="button" class="btn btn-link" data-dismiss="modal" style="background-color: #26b3ea;">Close</button>
			</div>
		</div>
	</div>
</div>

<script>

function setBranchToPopup(branchCode){
	var desig="${userbdesignation}";
	
	var branch=branchCode.split("_");
	var branchName="";
	if(branch[1]=="1115"){ branchName="TRIPURESHWOR"; } 
	else if(branch[1]=="1112"){ branchName="BANESHWOR"; } 
	else if(branch[1]=="1116"){ branchName="BHAKTAPUR"; } 
	else if(branch[1]=="1114"){ branchName="CHHETRAPATI"; } 
	else if(branch[1]=="1113"){ branchName="KAMALADI"; } 
	else if(branch[1]=="1119"){ branchName="KIRTIPUR"; } 
	else if(branch[1]=="1118"){ branchName="LALITPUR"; } 
	else if(branch[1]=="1110"){ branchName="MAHANKALCHAUR"; } 
	else if(branch[1]=="1111"){ branchName="MAHARAJGUNJ"; } 
	else if(branch[1]=="1117"){ branchName="THIMI"; } 
	
	 $('#connectionidspan').html("Branch : "+branchName);	
	 $('#branch').val(branchCode);
	 
	 
	 $ .ajax({
			type : "GET",
			url : "./getAdjTransListByBranch",
			dataType : "json",
			data:{
				branchCode : branchCode,
			    },
			cache : false,
			async : false,
			success : function(response) {
				if (response.length == 0) {
					$("#tableBodyId").empty();
					swal({
						title : "No Data Found.",
						text : "",
						confirmButtonColor : "#2196F3",
					});
					$("#loading").hide();
					return false;
				}

				var ConnectionData = "";

				var count;
				for (var i = 0; i < response.length; i++) {
					data = response[i];
					count = i + 1;
					ConnectionData += "<tr>" + "<td>" + count + "</td>";
					if(data.connection_no == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.connection_no+"</td>"; }
			  		if(data.name_eng == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.name_eng+"</td>"; }
			  		if(data.area_no == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.area_no+"</td>";}
			  		if(data.bill_adj_amount == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.bill_adj_amount+"</td>";}
			  		if(data.penalty_adj_amount == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.penalty_adj_amount+"</td>";}
			  		if(data.to_mon_year == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.to_mon_year+"</td>";}
			  		if(data.submit_by == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.submit_by+"</td>";}
			  		if(data.submit_date == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.submit_date+"</td>";}
			  		if(data.trans_approve == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.trans_approve+"</td>";}
			  		if(data.trans_approve_date == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.trans_approve_date+"</td>";}
			  		if(data.remarks == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.remarks+"</td>";}
			  		
			  		if(desig=='General Manager'|| desig=='Manager-Finance division' || desig=='Asst Manager- PMU' || desig=='Developer'){
				  		ConnectionData+="<td><button type='button' class='btn btn-success btn-ladda' data-style='expand-right' data-spinner-size='20'"+
						"onclick='return approveAdjustment(\""+data.connection_no+"\",\""+branchCode+"\",\"1\")' style='margin-top: -5px;margin-bottom: -5px;'> <span class='ladda-label'>Approve</span> </td>";
				  		ConnectionData+="<td><button type='button' class='btn btn-danger' data-style='expand-right' data-spinner-size='20'"+
						"onclick='return approveAdjustment(\""+data.connection_no+"\",\""+branchCode+"\",\"2\")' style='margin-top: -5px;margin-bottom: -5px;'> <span class='ladda-label'>Reject</span> </td>";
			  		}
			  		ConnectionData+="</tr>";	
				}
				$("#tableBodyId").html(ConnectionData);
				loadSearchFilter1('adjustTable', ConnectionData, 'tableBodyId');
				$("#loading").hide();

			}
		});
	 
}

function loadSearchFilter1(param, tableData, temp) {
	$('#' + param).dataTable().fnClearTable();
	$('#' + param).dataTable().fnDestroy();
	$('#' + temp).html(tableData);
	$('#' + param).dataTable();

}

function approveAdjustment(conNo,branch,status){
	
	if(status=='1'){
		swal({
			  title: "Are You Sure You want to Approve?",
			  text: "Once Approved, Cannot be undone!!",
			  type: "warning",
			  showCancelButton: true,
			  confirmButtonColor: "rgb(35, 164, 123)",
			  cancelButtonColor: "#fb8585",
			  confirmButtonText: "Yes",
			  cancelButtonText: "No",
			  closeOnConfirm: true,
			  closeOnCancel: true
			},
			function(isConfirm) {
			  if (isConfirm) {
				  $.ajax({
					  type:"GET",
					  url:"./billpenaltyAdjApproveStatusCorporate",
					  dataType:"text",
					  data : {
						  conNo : conNo,
						  branch : branch,
						  status : status,
					  },
					  success:function(response){
						  alert(response);
						  window.location.reload();
					   }

					 });
				  
				  
			  } 
			});
	} else{
		swal({
			  title: "Are You Sure You want to Reject?",
			  text: "Once Rejected, Cannot be undone!!",
			  type: "warning",
			  showCancelButton: true,
			  confirmButtonColor: "rgb(35, 164, 123)",
			  cancelButtonColor: "#fb8585",
			  confirmButtonText: "Yes",
			  cancelButtonText: "No",
			  closeOnConfirm: true,
			  closeOnCancel: true
			},
			function(isConfirm) {
			  if (isConfirm) {
				  $.ajax({
					  type:"GET",
					  url:"./billpenaltyAdjApproveStatusCorporate",
					  dataType:"text",
					  data : {
						  conNo : conNo,
						  branch : branch,
						  status : status,
					  },
					  success:function(response){
						  alert(response);
						  window.location.reload();
					   }

					 });
				  
			  } 
			});
		
	}
	
	
	
}

</script>

<style>

select{
width: 100%; border: 1px solid #DDD; border-radius: 3px; height: 36px;
}

legend {
	text-transform: none;
	font-size: 16px;
	border-color: #F01818;
}

.datatable-header, .datatable-footer {
    padding: 0px 0px 0 0px;
}

.dataTables_filter {
    display: block;
    float: left;
    margin: 0 0 20px 20px;
    position: relative;
}
.dataTables_length{
margin-left: -52px;
}
.dataTables_length > label {
    margin-bottom: 0;
    display: block;
}

.modal.fade .modal-dialog {
width: 1260px;
}

.modal-body {
    position: relative;
    padding: 0px;
}

.datatable-header, .datatable-footer {
    padding: 5px 20px 0px 20px;
}

.modal-footer {
	padding-bottom: 0px;
}

.dataTables_filter{
	padding-top: 36px;
}

body {
    font-family: "Roboto", Helvetica Neue, Helvetica, Arial, sans-serif;
    line-height: 1.5384616;
    color: #333333;
}
.sweet-alert button.cancel {
    background-color: #db6c6c !important;
    color: #fff;
}
</style>



