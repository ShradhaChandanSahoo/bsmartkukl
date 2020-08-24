<!-- <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBUITd007ZOEUbgXvMFNj3swpfcaaiOEzg&libraries=places&sensor=false"></script>     -->
<%@include file="/WEB-INF/decorators/taglibs.jsp"%>
<script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyB9s6LWTKn5I-t26CWHdiLFq3jtwvHVFg0&callback=initMap"></script>
<script  type="text/javascript">
 
  window.onload = function(){
	  initialize() ;
	  
  }
  
  
  
var map;
var markers = [];

  function initialize() {
	  var slat="27.696258248089265",slong="85.3136819601059";
	    var myLatlng = new google.maps.LatLng(parseFloat(slat), parseFloat(slong));
	    var mapOptions = {
	        zoom: 16,
	        center: myLatlng,
	        gestureHandling: 'greedy',
            fullscreenControl: true,
	        mapTypeId: google.maps.MapTypeId.ROADMAP
	    };
	    map = new google.maps.Map(document.getElementById("map-canvas"), mapOptions);

	    google.maps.event.addListener(map, "click", function (event) {
	    	swal({
	    	  title: "Latitude: <span style='color:red;font-weight: bold;'>"+event.latLng.lat()+"</span><br> Longitude: <span style='color:red;font-weight: bold;'>"+event.latLng.lng()+"</span><br>",
	    	  text: "",
	    	  type: 'info',
	    	  html: true,
	    	});
	    	
	    	var lat_lng = {lat: event.latLng.lat(), lng: event.latLng.lng()};  
	    	deleteMarkers();
	    	addMarker(lat_lng,map);
	        //alert('Latitude: '+event.latLng.lat()+', long: '+event.latLng.lng());
	    });
	    addMarker(myLatlng,map);
	}

  
  function addMarker(location,map) {  
	  var marker = new google.maps.Marker({  
	    position: location,  
	    map: map  
	  });  
	  markers.push(marker);
	}  
	
  function clearMarkers() {
      setMapOnAll(null);
    }
  
  function setMapOnAll(map) {
      for (var i = 0; i < markers.length; i++) {
        markers[i].setMap(map);
      }
    }
  function deleteMarkers() {
      clearMarkers();
      markers = [];
    }



  </script>
  


<div class="panel panel-flat">
	<div class="panel-body">
	<fieldset class="content-group">
		<legend class="text-bold" style="margin: auto; text-align: center; font-size: 18px;">MAP</legend>
		<br>
		<div id="map-canvas" style="height: 573px; width: 985px; border: solid;"></div>
		<br></br>
	</fieldset>
	</div>
</div>
