<%@include file="/WEB-INF/decorators/taglibs.jsp"%>
<script type="text/javascript">
var samePeriodDate="";
var reading=0
var door_lock=0;
var meter_block=0;
var meter_burried=0;
var meter_damaged=0;
var house_not_found=0;
var no_water_supply=0;
var meter_removed=0;
var low_water_supply=0;
var dog_presence=0;
var houce_collapse=0;
var meter_shield_broken=0;
var temp_hole_block=0;
var perm_hole_bock=0;
var service_disconctd=0;
var unemeter=0;
var no_reading=0;
var dual=0;
var pid=0;
var billed=0;
var unbillied=0;
var total_consumer=0;

$(window).load(function () {
	
	var makeDate = new Date();
	makeDate = new Date(makeDate.setMonth(makeDate.getMonth() - 2));
	samePeriodDate=moment(makeDate).format('DD-MMM-YY');
	$("#samePeriodmonth1").html("("+samePeriodDate+")");
	var cDate1=new Date();
	$("#currentdate1").html("("+moment(cDate1).format('DD-MMM-YY')+")")
	 $.ajax({
         type:"GET",
         url:"./getMeterStatusWise",
         dataType:"json",
         success:function(response){

          $.each(response,function(key, val) {
        	  
        	  
        	  reading=parseInt(val.MC16);
        	  door_lock=parseInt(val.MC1);
        	  meter_block=parseInt(val.MC2);
        	  meter_burried=parseInt(val.MC3);
        	  meter_damaged=parseInt(val.MC4);
        	  house_not_found=parseInt(val.MC5);
        	  no_water_supply=parseInt(val.MC6);
        	  meter_removed=parseInt(val.MC7);
        	  low_water_supply=parseInt(val.MC8);
        	  dog_presence=parseInt(val.MC9);
        	  houce_collapse=parseInt(val.MC14);
        	  meter_shield_broken=parseInt(val.MC10);
        	  temp_hole_block=parseInt(val.MC11);
        	  perm_hole_bock=parseInt(val.MC12);
        	  service_disconctd=parseInt(val.MC13);
        	  unemeter=parseInt(val.MC15);
        	  no_reading=parseInt(val.MC17);
        	  dual=parseInt(val.MC18);
        	  pid=parseInt(val.MC19);
        	  billed=reading+door_lock+meter_block+meter_burried+meter_damaged+house_not_found+no_water_supply+meter_removed+low_water_supply+dog_presence+houce_collapse+meter_shield_broken+temp_hole_block+perm_hole_bock+service_disconctd+unemeter+no_reading+dual+pid;
        	  
        	  $("#reading").html(parseInt(val.MC16));
        	  $("#door_lock").html(parseInt(val.MC1));
        	  $("#meter_block").html(parseInt(val.MC2));
        	  $("#meter_burried").html(parseInt(val.MC3));
        	  $("#meter_damaged").html(parseInt(val.MC4));
        	  $("#house_not_found").html(parseInt(val.MC5));
        	  $("#no_water_supply").html(parseInt(val.MC6));
        	  $("#meter_removed").html(parseInt(val.MC7));
        	  $("#low_water_supply").html(parseInt(val.MC8));
        	  $("#dog_presence").html(parseInt(val.MC9));
        	  $("#houce_collapse").html(parseInt(val.MC14));
        	  $("#meter_shield_broken").html(parseInt(val.MC10));
        	  $("#temp_hole_block").html(parseInt(val.MC11));
        	  $("#perm_hole_bock").html(parseInt(val.MC12));
        	  $("#service_disconctd").html(parseInt(val.MC13));
        	  $("#unemeter").html(parseInt(val.MC15));
        	  $("#no_reading").html(parseInt(val.MC17));
        	  $("#dual").html(parseInt(val.MC18));
        	  $("#pid").html(parseInt(val.MC19));
        	  $("#unbilled_consumer").html(parseInt(val.MC99));
        	  $("#total_consumer").html(parseInt(val.total_consumer));
        	  $("#billed_consumer").html(billed);
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
                            <div class="panel-heading" style="height:50px; background-color: #FF7043;">
                                <h3 class="panel-title text-bold" align="center" style="margin-top: -10px;">Billing Status Wise</h3>
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
				<div class="row text-center">

					<div class="container-fluid">

						<div class="panel-heading">
							<h6 class="panel-title">
								Current Month Progress as on &nbsp;<span id="currentdate1"
									style="color: green;"></span>
							</h6>

						</div>

						<div class="col-lg-4">
							<div class="panel bg-blue-400">
							<div class="panel-body">
								<div class="heading-elements">
									<ul class="icons-list">
										<li><a data-action="reload"></a></li>
									</ul>
								</div>

								<h3 class="no-margin ">
									<span id="total_consumer" class="count1"></span>
								</h3>
								Total Consumer &nbsp; <i class="icon-file-check2"></i>
								<div class="text-muted text-size-small">All Branches</div>
							</div>

							<div id="today-revenue"></div>
						</div>
					</div>
					<div class="col-lg-4">
						<div class="panel bg-success-400">
							<div class="panel-body">
								<div class="heading-elements">
									<ul class="icons-list">
										<li><a data-action="reload"></a></li>
									</ul>
								</div>

								<h3 class="no-margin ">
									<span id="billed_consumer" class="count"></span><span id="billass1"></span>
								</h3>
								Billed &nbsp;<i class="icon-flip-horizontal2"></i>

								<div class="text-muted text-size-small">This Month</div>
							</div>

							<div id="today-revenue"></div>
						</div>
					</div>
					<div class="col-lg-4">
						<div class="panel bg-pink-400">
							<div class="panel-body">
								<div class="heading-elements">
									<ul class="icons-list">
										<li><a data-action="reload"></a></li>
									</ul>
								</div>

								<h3 class="no-margin ">
									<span id="unbilled_consumer" class="count"></span><span id="billass1"></span>
								</h3>
								UnBilled &nbsp;<i class="icon-flip-horizontal2"></i>

								<div class="text-muted text-size-small">This Month</div>
							</div>

							<div id="today-revenue"></div>
						</div>
					</div>
						<div class="col-md-3">
							<div class="content-group">
								<h5 class="text-semibold no-margin">
									<i class="icon-shield-check position-left text-slate"></i> <span
										class="Count21" id="reading"></span>
								</h5>
								<span class="text-muted text-size-small">Reading </span>
							</div>
						</div>
						<div class="col-md-3">
							<div class="content-group">
								<h5 class="text-semibold no-margin">
									<i class="icon-lock2 position-left text-slate"></i> <span
										class="Count21" id="door_lock"></span>
								</h5>
								<span class="text-muted text-size-small">Door Lock </span>
							</div>
						</div>

						<div class="col-md-3">
							<div class="content-group">
								<h5 class="text-semibold no-margin">
									<i class=" icon-blocked position-left text-slate"></i> <span
										class="Count21" id="meter_block"></span>
								</h5>
								<span class="text-muted text-size-small">Meter Block </span>
							</div>
						</div>
						
						<div class="col-md-3">
							<div class="content-group">
								<h5 class="text-semibold no-margin">
									<i class="icon-wall position-left text-slate"></i> <span
										class="Count21" id="meter_burried"></span>
								</h5>
								<span class="text-muted text-size-small">Meter Burried </span>
							</div>
						</div>

						<div class="col-md-3">
							<div class="content-group">
								<h5 class="text-semibold no-margin">
									<i class="icon-construction position-left text-slate"></i> <span
										class="Count21" id="meter_damaged"></span>
								</h5>
								<span class="text-muted text-size-small">Meter Damaged </span>
							</div>
						</div>
						<div class="col-md-3">
							<div class="content-group">
								<h5 class="text-semibold no-margin">
									<i class="icon-home5 position-left text-slate"></i> <span
										class="Count21" id="house_not_found"></span>
								</h5>
								<span class="text-muted text-size-small">House not found </span>
							</div>
						</div>
						<div class="col-md-3">
							<div class="content-group">
								<h5 class="text-semibold no-margin">
									<i class="icon-color-clear position-left text-slate"></i> <span
										class="Count21" id="no_water_supply"></span>
								</h5>
								<span class="text-muted text-size-small">No Water Supply </span>
							</div>
						</div>
						<div class="col-md-3">
							<div class="content-group">
								<h5 class="text-semibold no-margin">
									<i class="fa fa-remove position-left text-slate"></i> <span
										class="Count21" id="meter_removed"></span>
								</h5>
								<span class="text-muted text-size-small">Meter Removed </span>
							</div>
						</div>
						<div class="col-md-3">
							<div class="content-group">
								<h5 class="text-semibold no-margin">
									<i class="icon-meter-slow position-left text-slate"></i> <span
										class="Count21" id="low_water_supply"></span>
								</h5>
								<span class="text-muted text-size-small">Low Water Supply </span>
							</div>
						</div>
						<div class="col-md-3">
							<div class="content-group">
								<h5 class="text-semibold no-margin">
									<i class="icon-alert position-left text-slate"></i> <span
										class="Count21" id="dog_presence"></span>
								</h5>
								<span class="text-muted text-size-small">Dog Presence </span>
							</div>
						</div>
						<div class="col-md-3">
							<div class="content-group">
								<h5 class="text-semibold no-margin">
									<i class="icon-home8 position-left text-slate"></i> <span
										class="Count21" id="houce_collapse"></span>
								</h5>
								<span class="text-muted text-size-small">House Collapse </span>
							</div>
						</div>
						<div class="col-md-3">
							<div class="content-group">
								<h5 class="text-semibold no-margin">
									<i class="icon-wrench position-left text-slate"></i> <span
										class="Count21" id="meter_shield_broken"></span>
								</h5>
								<span class="text-muted text-size-small">Meter Sheild Broken </span>
							</div>
						</div>
						<div class="col-md-3">
							<div class="content-group">
								<h5 class="text-semibold no-margin">
									<i class="icon-blocked position-left text-slate"></i> <span
										class="Count21" id="temp_hole_block"></span>
								</h5>
								<span class="text-muted text-size-small">Temporary hole block </span>
							</div>
						</div>
						<div class="col-md-3">
							<div class="content-group">
								<h5 class="text-semibold no-margin">
									<i class="icon-blocked position-left text-slate"></i> <span
										class="Count21" id="perm_hole_bock"></span>
								</h5>
								<span class="text-muted text-size-small">Permanent hole block </span>
							</div>
						</div>
						<div class="col-md-3">
							<div class="content-group">
								<h5 class="text-semibold no-margin">
									<i class="icon-unlink2 position-left text-slate"></i> <span
										class="Count21" id="service_disconctd"></span>
								</h5>
								<span class="text-muted text-size-small">Service Line disconnected </span>
							</div>
						</div>
						<div class="col-md-3">
							<div class="content-group">
								<h5 class="text-semibold no-margin">
									<i class="icon-meter2 position-left text-slate"></i> <span
										class="Count21" id="unemeter"></span>
								</h5>
								<span class="text-muted text-size-small">Unmetered </span>
							</div>
						</div>
						<div class="col-md-3">
							<div class="content-group">
								<h5 class="text-semibold no-margin">
									<i class="icon-minus-circle2 position-left text-slate"></i> <span
										class="Count21" id="no_reading"></span>
								</h5>
								<span class="text-muted text-size-small">No Reading </span>
							</div>
						</div>
						<div class="col-md-3">
							<div class="content-group">
								<h5 class="text-semibold no-margin">
									<i class="icon-mirror position-left text-slate"></i> <span
										class="Count21" id="dual"></span>
								</h5>
								<span class="text-muted text-size-small">Dual Record </span>
							</div>
						</div>
						<div class="col-md-3">
							<div class="content-group">
								<h5 class="text-semibold no-margin">
									<i class="icon-git-pull-request position-left text-slate"></i> <span
										class="Count21" id="pid"></span>
								</h5>
								<span class="text-muted text-size-small">PID </span>
							</div>
						</div>

						
					</div>

					
					<div><div class="row"><h6>&nbsp;</h6></div></div>
					

								<div class="text-center">
									<button type="button"
										class="btn bg-teal-400 btn-labeled btn-rounded"
										data-toggle="modal" data-target="#modal_fullmtr"
										onclick="return Locationwisemtr();">
										<b><i class="icon-location4"></i></b> Location Wise
									</button>

								</div>
								




							
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


		