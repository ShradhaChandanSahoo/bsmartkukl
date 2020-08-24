
<script type="text/javascript" src="./resources/js/jquery_easing.js"></script>
<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDFsv7MwN3q9GNl-kasQWAWqLtgAi1aaF4&callback=initMap"></script>
<script type="text/javascript" src="./resources/js/markerAnimate.js"></script>

<script type="text/javascript">


$(window).load(function() {
	liveDashboard();
	
	var subdiv="ALL";
 
plotmultiple(subdiv);

});
  /* var livedata=10000;
  setTimeout(function() {
	   
    liveDashboard();
   
  }, livedata); 
 */

function liveDashboard(){
	$.ajax({
	    type:"GET",
	    url:"./getliveDashBoard",
	    dataType:"json",
	    success:function(response){
	    	 $.each(
	                 response,
	                 function(key, val) {

	                	 
	                    $("#billed_today").html(parseInt(val.billed_today));
	                    
	                    $("#mrs_livetoday").html(parseInt(val.mrs_today));
	                    $("#mrs_month").html(parseInt(val.mrs_month));
	                    $("#collection_today").html(nFormatter(val.onlinePayment_today));
						 /*  var a2=nFormatter(val.onlinePayment_today).split(".");
						  var b2;
						  if((a2.toString()).includes(",")){
							  b2=a2[1].substr(1,6);
						  }else{
							  a2=a2.toString();
							  b2=a2.substr(1,6);
						  }
						  */
						 /*  $("#coll").html(b2); */
						  
	                    $("#demmand_month").html(nFormatter(val.demmand_month));
						  /* var a3=nFormatter(val.demmand_month).split(".");
						  if(a3==0 || a3==""){
							  
						  }else{
						  var b3=a3[1].substr(1,6);
						  //$("#dem").html(b3);
	                 } */
				         $("#totalbilled").html(parseInt(val.totalbilled));
				        
	                    $("#payments_month").html(nFormatter(val.payments_month));
	                   /*  var pay=nFormatter(val.payments_month).split(".");
	                    if(pay==0){
	                    	
	                    }else{
	                    var cash=pay[1].substr(1,6);
	                    $("#cash").html(cash);
	                    }
						 */
						 
				         $("#totalComplaints_today").html(parseInt(val.totalComplaints_today));
				         $("#pendingCompliants_today").html(parseInt(val.pendingCompliants_today));
	                    $("#resolvedComplaints_today").html(parseInt(val.resolvedComplaints_today));
						  $("#totalComplaints").html(parseInt(val.totalComplaints));
						   $("#totalResolvedComplaints").html(parseInt(val.totalResolvedComplaints));
						    $("#totalPendingComplaints").html(parseInt(val.totalPendingComplaints));
						    $("#totalPendingComplaints1").html(parseInt(val.totalPendingComplaints));
							 $("#totalComplaints1").html(parseInt(val.totalComplaints));
							 $("#totlivefm").html(parseInt(val.totlivefm));

							 $("#totfm").html(parseInt(val.totfm));
							 $("#noofconsumer").html(parseInt(val.noofconsumer));
							 $("#assessment_todaysLive").html(nFormatter(val.assessment));
							 //alert(nFormatter(val.assessment));
		                    /*  var a1=nFormatter(val.assessment).split(".");
		                    
		                     var b1;
		                     if((a1.toString()).includes(",")){
		                    	
		                    	  b1=a1[1].substr(1,6);
		                     }else{
		                    	 a1=a1.toString();
		                    	  b1=a1.substr(2,6);
		                    	  
		                     } */
		                    
							 
							  //$("#ass").html(b1);
							  
	                   
	                 });
	    	/* $('.Count').each(function () {
	    		  var $this = $(this);
	    		  jQuery({ Counter: 0 }).animate({ Counter: $this.text() }, {
	    		    duration: 10000,
	    		    easing: 'swing',
	    		    step: function () {
	    		      $this.text(this.Counter.toFixed(1));
	    		    }
	    		  });
	    		}); 
	    	$('.Count1').each(function () {
	    		  var $this = $(this);
	    		  jQuery({ Counter: 0 }).animate({ Counter: $this.text() }, {
	    		    duration: 10000,
	    		    easing: 'swing',
	    		    step: function () {
	    		      $this.text(Math.ceil(this.Counter));
	    		    }
	    		  });
	    		});  */
	    	 
	    }
	}); 

}

var locations =[];
var result =[];
var flag=0;
var markers=[];
var markersFM=[];
var marker,markerFM, map;
var infowindow = new google.maps.InfoWindow();
var count=0;
var subdiv="ALL";
if(subdiv=='ALL'){
	subdiv="ALL";
}  

  function initialize(locations) {
    var myLatlng = new google.maps.LatLng(locations[0][0], locations[0][1]);
    var mapOptions = {
      zoom: 13,
      center: myLatlng,
      fullscreenControl:true
    }
	
	var bounds = new google.maps.LatLngBounds();
    map = new google.maps.Map(document.getElementById('map_canvas'), mapOptions);
	for (var i = 0; i < locations.length; i++) {
		if(!((locations[i][0]==null) && (locations[i][1]==null))) {
		marker = new google.maps.Marker({
			position: new google.maps.LatLng(locations[i][0], locations[i][1]),
			map: map,
			//animation:google.maps.Animation.BOUNCE,
            icon:'./resources/gmapimages/feeder_reader.png',
            //label: locations[i][2],
			title: locations[i][2],
			
			
		});
		}
		 markers.push(marker);
		bounds.extend(marker.position);

		google.maps.event.addListener(marker, 'click', (function(marker, i) {
			return function() {
				var starttime=moment(locations[i][10]).format('YYYY-MM-DD HH:mm:ss');
				var endtime=moment(locations[i][11]).format('YYYY-MM-DD HH:mm:ss');
			infowindow.setContent( "<div style=width:250px;>"
	                +"<table  id=billTable style='margin-left: 10px;margin-bottom: 10px;'>"
					  +"<tr><th>IMEI NO </th><td>"+locations[i][2]+"</td></tr>"
					  +"<tr><th>FEEDER NAME </th><td >"+locations[i][4]+"</td></tr>"
					  +"<tr><th>FEEDER NO   </th><td>"+locations[i][5]+"</td></tr>"
					  +"<tr><th>SDOCODE    </th><td>"+locations[i][3]+"</td></tr>"
					  +"<tr><th>LT POLE    </th><td>"+locations[i][6]+"</td></tr>"
					  +"<tr><th>CONSUMER    </th><td>"+locations[i][7]+"</td></tr>"
					  +"<tr><th>DTC    </th><td>"+locations[i][8]+"</td></tr>"
					  +"<tr><th>HTPOLE    </th><td>"+locations[i][9]+"</td></tr>"
					  +"<tr><th>START TIME    </th><td>"+starttime+"</td></tr>"
					  +"<tr><th>END TIME    </th><td>"+endtime+"</td></tr>"
				      +"<tr><th>LATITUDE    </th><td>"+locations[i][0]+"</td></tr>"
					  +"<tr><th>LONGITUDE    </th><td>"+locations[i][1]+"</td></tr>" 
					  +"<tr><th>&nbsp; </th><td><a href='#' id='"+locations[i][2]+"' onclick='return viewFMOnMap(this.id,"+locations[i][0]+","+locations[i][1]+")'>Track FM</a></td>"
					  +"</table></div>");
        infowindow.open(map, marker);
		}
		})(marker, i));
		
}
map.fitBounds(bounds);
		//alert(markers.length);
	/* 	
     var millisecondsToWait = 10000;
        setTimeout(function() {
            
            plotmultiple(subdiv);
           
        }, millisecondsToWait); 
 */
 
}


function plotmultiple(subdiv){
	
	var sdo=subdiv;
	
	/*  $('#appendMapVal').html('');
	var html='<div id="map-canvas" class="gmaps" style="height: 570px;"></div>';
	$('#appendMapVal').html(html);  */
	
	$.ajax({
    //url: "http://192.168.4.94:8090/bsmartJVVNL/getMrLiveTrackerData",
    url : './getMrLiveTrackerData/'+sdo,
    type: "POST",
    timeout:10000,
    contentType: "application/json",
    dataType:"json",
    }).done(function(data){
    	
    	if(data.length>0){
    	$('#gmapsContent').show();
    	}
    	else {
    		alert('No LIVE Data Available');
    	}
    //console.log(JSON.stringify(data));
    if(data=="no_data"){
    return false;
    }
    startLoc = new Array();
    endLoc = new Array();

locations=[];
    $.each(data, function(key, val) {
         var array = [];
        
          if(val[1]!="0.0"||val[1]!=0.0||val[1]!=null||val[1]!="null"){
                array.push(val[1]);
                array.push(val[0]);
                array.push(val[3]);
                array.push(val[4]);
                array.push(val[5]);
                array.push(val[6]);
                array.push(val[7]);
                array.push(val[8]);
                array.push(val[9]);
                array.push(val[10]);
                array.push(val[11]);
                array.push(val[12]);
                locations.push(array);
          }

    }); 

   if(flag==0){
	  
     initialize(locations);
     flag=1;
   }else{
		move(locations);
   }

    }).fail(function (jqXHR, textStatus) {

});
}



function move(locations) {
      var duration = 4000;
      
      if (duration < 0) {
        duration = 1;
      }

	  for (var i = 0; i < locations.length; i++) {
	  
		var test=new google.maps.LatLng(locations[i][0],locations[i][1]);

		for(var j=0;j < markers.length;j++){
			if(locations[i][2]==markers[j].getTitle()){
				if (!test.equals(new google.maps.LatLng(markers[j].getPosition().lat(),markers[j].getPosition().lng()))){

					  markers[j].setIcon({
					             url: './resources/gmapimages/feeder_reader_live.png',
					        });
					    map.setCenter(test);

					}else{

					    markers[j].setIcon({
					             url: './resources/gmapimages/feeder_reader.png'
					        });

					}
				markers[j].animateTo(test, {easing: 'linear', duration: duration});
				//count++;
				break;
			}
		}
		
	 }
    var millisecondsToWait = 10000;
        setTimeout(function() {
            plotmultiple(subdiv);
            count++;
        }, millisecondsToWait);
        
        var millisecondsToWait1 = 4000;
        setTimeout(function() {
            for(var l=0;l<locations.length;l++){
                if(markers[l].getIcon().url=='./resources/gmapimages/feeder_reader_live.png'){
                            markers[l].setIcon({
                                 url: './resources/gmapimages/feeder_reader.png'
                            });
                }
            }
        }, millisecondsToWait1);

  }  


//ashwini code
var locarray = [];
function viewFMOnMap(imeiNo,lt,lg)
{
	infowindow.close(map, marker);
	var mark;
	//alert(imeiNo);
	  var fmLiveMap="";
		$.ajax({
			url : './getTrackerFMLive/'+imeiNo,
		    type:"GET",
		    dataType : "json",
			cache:false,
			async:false,
		    success:function(response1)
			  {		
					var first=0;
					 locarray=[];
				     $.each(response1, function(key, val) {
						if(first==0){
							
							 mark = new google.maps.Marker({
								position:new google.maps.LatLng(val[1],val[0]),
								//url: 'images/fmliveiconMove.png',
								map:map
							});
                            moveTrack(lt,lg,mark);
							first++;
						}
					});

					//moveTrack(lt,lg,mark,locarray);
			  }
		});
		
		
		return false;
}



function moveTrack(lat,lng,mark) {
//alert(locarray.length);
      
          var duration = 4000;
          
          if (duration < 0) {
            duration = 1;
          
          }
			var test=new google.maps.LatLng(lat,lng);
			   
			mark.animateTo(test, {easing: 'linear', duration: duration});
		var iTrack=0;
		 var millisecondsToWait111 = 5000;
           var timer= setTimeout(function() {
		 //  alert(iTrack+"-------------"+locarray.length);
              //  if(iTrack<locarray.lenght){

                    //    mark.animateTo(new google.maps.LatLng(locarray[iTrack][0],locarray[iTrack][1]), {easing: 'linear', duration: duration});
                     //   iTrack++;
                //}else{
					mark.setMap(null);
					clearTimeout(timer);
                //}
            }, millisecondsToWait111);
	}
	
	  

</script>

             <div class="container-fluid">
            <div class="row">
                <div class="col-md-12">
              <div class="row">

                    <div class="col-md-12">
                        <div class="panel panel-flat">
                            <div class="panel-heading" style=" height:50px;background-color: #80CFE9;">
                                <h3 class="panel-title text-bold " align="center" style="margin-top: -10px;" >Live Status</h3>
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
                              <div class="col-md-12">
                            <div class="panel-heading">
                                <h6 class="panel-title" >Today's Status</h6>
                            </div>
                             <div class="container-fluid">
                                    <div class="col-lg-4">
                                        <ul class="list-inline text-center">
                                            <li>
                                                <a href="#" class="btn border-teal text-teal btn-flat btn-rounded btn-icon btn-xs valign-text-bottom"><i class="icon-meter2"></i></a>
                                            </li>
                                            <li class="text-left">
                                                <div class="text-bold">Bill Status</div>

                                                
                                                <div class="text-muted">MR Live : <span class="text-bold Count1 " id="mrs_livetoday"></span></div>
                                                <div class="text-muted">Bill Generated : <span class="text-bold Count1" id="billed_today"></span></div>

                                            </li>
                                        </ul>

                                        <div class="col-lg-10 col-lg-offset-1">
                                            <div class="content-group" id="new-visitors"></div>
                                        </div>
                                    </div>

                                    <div class="col-lg-4">
                                        <ul class="list-inline text-center">
                                            <li>
                                                <a href="#" class="btn border-warning-400 text-warning-400 btn-flat btn-rounded btn-icon btn-xs valign-text-bottom"><i class="icon-coins"></i></a>
                                            </li>
                                            <li class="text-left">
                                                <div class="text-bold">Collection Status</div>


                                                <div class="text-muted">Total Demand: <span class="text-bold Count" id="assessment_todaysLive"></span><span id="ass"></span></div>
                                                <div class="text-muted">Total Collection:<span class="text-bold Count" id="collection_today"></span><span id="coll"></span></div>

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
                                                <div class="text-bold">Complaint Status</div>


                                                <div class="text-muted">Total Complains   :<span class="text-bold Count1" id="totalComplaints_today"></span></div>
                                                <div class="text-muted">Resolved Complains:<span class="text-bold Count1" id="resolvedComplaints_today"></span></div>
                                                <div class="text-muted">Pending Complains:<span class="text-bold Count1" id="pendingCompliants_today"></span></div>

                                            </li>
                                        </ul>

                                        <div class="col-lg-10 col-lg-offset-1">
                                            <div class="content-group" id="total-online"></div>
                                        </div>
                                    </div>
                                </div>
                                </div>
                                </div>
                                
                                 <div class="row">
                              <div class="col-lg-12">
                                    <div class="panel-heading">
                                <h6 class="panel-title" >Current Month Status</h6>
                            </div>
                                 <div class="container-fluid">
                                    <div class="col-lg-4">
                                        <ul class="list-inline text-center">
                                            <li>
                                                <a href="#" class="btn border-teal text-teal btn-flat btn-rounded btn-icon btn-xs valign-text-bottom"><i class="icon-meter2"></i></a>
                                            </li>
                                            <li class="text-left">
                                                <div class="text-bold">Bill Status</div>
                                                 <div class="text-muted">Ledger Generated :<span class="text-bold Count1" id="mrs_month"></span></div>
                                                <div class="text-muted">Billed :<span class="text-bold Count1" id="mrs_month"></span></div>
                                                <div class="text-muted">Unbilled :<span class="text-bold Count1" id="totalbilled"></span></div>
                                            </li>
                                        </ul>

                                        <div class="col-lg-10 col-lg-offset-1">
                                            <div class="content-group" id="new-visitors"></div>
                                        </div>
                                    </div>

                                    <div class="col-lg-4">
                                        <ul class="list-inline text-center">
                                            <li>
                                                <a href="#" class="btn border-warning-400 text-warning-400 btn-flat btn-rounded btn-icon btn-xs valign-text-bottom"><i class="icon-coins"></i></a>
                                            </li>
                                            <li class="text-left">
                                                <div class="text-bold">Collection Status</div>
                                                <div class="text-muted">Total Demand    :<span class="text-bold Count" id="demmand_month"></span><span id="dem"></span></div>
                                                <div class="text-muted">Total Collection:<span class="text-bold Count" id="payments_month"></span><span id="cash"></span></div>

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
                                                <div class="text-bold">Complaint Status</div>
                                                <div class="text-muted">Total Complains   :<span class="text-bold Count1" id="totalComplaints"></span></div>
                                                <div class="text-muted">Resolved Complains:<span class="text-bold Count1" id="totalResolvedComplaints"></span></div>
                                                <div class="text-muted">Pending Complains:<span class="text-bold Count1" id="totalPendingComplaints"></span></div>
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


                </div> 
               </div>
               </div>
                
                </div>
                </div>
               
