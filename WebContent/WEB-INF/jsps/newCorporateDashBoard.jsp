<%@include file="/WEB-INF/decorators/taglibs.jsp"%>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<script type="text/javascript" src="resources/js/exporting.js"></script>
<script type="text/javascript" src="resources/js/highcharts.js"></script>
<script type="text/javascript" src="resources/js/highcharts-3d.js"></script>
<script type="text/javascript" src="<c:url value='/resources/js/amountconversion.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/js/decimalFormate.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/js/jquery.counterup.min.js' />"></script>

<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<link href="http://www.jqueryscript.net/css/jquerysctipttop.css" rel="stylesheet" type="text/css">
<link href='http://fonts.googleapis.com/css?family=Roboto+Condensed' rel='stylesheet' type='text/css'>
        
<link href="resources/js/marquee.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="resources/js/marquee.js"></script>

<script>
 
 

 var mrsdata=0;

 
 $(window).load(function() {
	
	 $("#hidegeneric").hide();
	 
       
            $(function () {
                $('#myCarousel').carousel({
                    interval:100,
                    pause: "false"
                });
                $('#playButton').click(function () {
                    $('#homeCarousel').carousel('cycle');
                });
                $('#pauseButton').click(function () {
                    $('#homeCarousel').carousel('pause');
                });
            });
            
            
 });



 

</script>
<input value="${branchcode}" id="codedata" hidden="true"></input>
<div class="container-fluid">
	<!--  container div -->

	<div class="row" style="margin-left: -59px;">
		<div class="col-lg-2" align="center">
			<a data-target="#myCarousel" data-slide-to="0">LIVE STATUS</a>
		</div>
		<div class="col-lg-2" align="center">
			<a data-target="#myCarousel" data-slide-to="1">BILLING PROGRESS
			</a>
		</div>
		<div class="col-lg-2" align="center">
			<a data-target="#myCarousel" data-slide-to="2">COLLECTION STATUS</a>
		</div>
		<div class="col-lg-2" align="center">
			<a data-target="#myCarousel" data-slide-to="3">MISCELLENEOUS</a>
		</div>
		<div class="col-lg-2" align="center">
			<a data-target="#myCarousel" data-slide-to="4">ADJUSTMENTS</a>
		</div>
		
		
		</div>
	</div>

<div class="container-fluid">
	
	<div class="row">
		
		<div id="myCarousel" class="carousel slide" data-ride="carousel">
			
			<!-- Items -->
			<div class="carousel-inner">

				<!-- Item 1 -->
				<div class="item active">
					<%@include file="/WEB-INF/jsps/LiveStatusDash.jsp"%>
				</div>
				<!-- item 1 -->
				
				<!-- Item 2 -->
				<div class="item">
					<%@include file="/WEB-INF/jsps/MeterStatusDash.jsp"%>
				</div>
				<!-- item 2 -->

				<!-- Item 3 -->
				<div class="item">
				<%@include file="/WEB-INF/jsps/AssessmentandPaymetsDash.jsp"%>
				</div>
				<!-- item 3 -->
                <div class="item">
				<%@include file="/WEB-INF/jsps/Miscelleneous.jsp"%>
				</div>
				<!-- Item 4 -->
				<div class="item">
				<%@include file="/WEB-INF/jsps/adjustmentDash.jsp"%>
				</div>
				<!-- item 4 -->
				
				<!-- Item 5 -->
				
				
				
			</div>
			


		</div>

	</div>
	
</div>


<script type="text/javascript">
var  branchName=$("#codedata").val();

$('.container-fluid').click(function () {
    $('#myCarousel').carousel('pause');
});

</script>
<style>

.modal-backdrop fade in{
display: contents !important;
}
.modal-backdrop.fade {
 
    display: inline-table !important;
} 
body{ 

  height: 100%;

    
    background-position: center;
    background-repeat: repeat;
    background-size: cover;
  }
  
  .col-lg-2 {
    width: 20%;
}
 .col-lg-1 {
    width: 10.333%;
}

.sidebar-category sidebar-category-visible{
 display: none !important;
}
.sidebar-content{
display: none !important;
}

.sidebar-default{
display: none !important;
}
  
</style>