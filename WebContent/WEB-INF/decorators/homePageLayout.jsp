<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>BSmart-KUKL</title>
    
    <link rel="icon" href="./resources/images/favicon.ico" type="image/x-icon">

<link rel="stylesheet" href="./resources/homePage/css/animations.css" type="text/css">
    <!-- Bootstrap -->
    <!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">

<link href='https://fonts.googleapis.com/css?family=Open+Sans:400,400italic,700' rel='stylesheet' type='text/css'>

<link href='https://fonts.googleapis.com/css?family=Pragati+Narrow|Raleway|Josefin+Sans|Muli|Oxygen|Dosis|Poppins' rel='stylesheet' type='text/css'>

<link href='https://fonts.googleapis.com/css?family=Merriweather' rel='stylesheet' type='text/css'>

<link rel="stylesheet" href="./resources/homePage/css/style.css">

<!-- Optional theme -->
<link rel="stylesheet" href="./resources/homePage/css/bootstrap-theme.min.css" integrity="sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r" crossorigin="anonymous">


		
<link rel="stylesheet" type="text/css" href="./resources/homePage/fonts/pe-icon-7-stroke/css/pe-icon-7-stroke.css">
<link rel="stylesheet" type="text/css" href="./resources/homePage/fonts/font-awesome/css/font-awesome.min.css">
		
<!-- REVOLUTION STYLE SHEETS -->
<link rel="stylesheet" type="text/css" href="./resources/homePage/css/settings.css">
<!-- REVOLUTION LAYERS STYLES -->
<link rel="stylesheet" type="text/css" href="./resources/homePage/css/layers.css">
			
<!-- REVOLUTION NAVIGATION STYLES -->
<link rel="stylesheet" type="text/css" href="./resources/homePage/css/navigation.css">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>
  <body>
  
	<decorator:body />

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <!-- <script src="./resources/homePage/js/jquery.min.js"></script> -->
    <!-- Latest compiled and minified JavaScript -->
	<script src="./resources/homePage/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>
    <script src="./resources/libs/jquery/jquery/dist/jquery.js"></script>
    <script src="./resources/libs/jquery/bootstrap/dist/js/bootstrap.js"></script>
	<script src='./resources/homePage/js/css3-animate-it.js'></script>
	<script type="text/javascript" src="./resources/homePage/js/jquery.themepunch.tools.min.js"></script>
	<script type="text/javascript" src="./resources/homePage/js/jquery.themepunch.revolution.min.js"></script>
	
	<script type="text/javascript" src="./resources/homePage/js/extensions/revolution.extension.video.min.js"></script>
	<script type="text/javascript" src="./resources/homePage/js/extensions/revolution.extension.slideanims.min.js"></script>
	<script type="text/javascript" src="./resources/homePage/js/extensions/revolution.extension.layeranimation.min.js"></script>
	<script type="text/javascript" src="./resources/homePage/js/extensions/revolution.extension.kenburn.min.js"></script>
	<script type="text/javascript" src="./resources/homePage/js/extensions/revolution.extension.navigation.min.js"></script>
	<script type="text/javascript" src="./resources/homePage/js/extensions/revolution.extension.parallax.min.js"></script>
	<script type="text/javascript" src="./resources/homePage/js/counter.js"></script>
	<script src="./resources/externalJs/bootbox.min.js"></script>
	
	
<script type="text/javascript">
					var tpj=jQuery;			
					var revapi4;
					tpj(document).ready(function() {
						document.getElementById("contactUsBean").reset();
						if(tpj("#rev_slider_4_1").revolution == undefined){
							revslider_showDoubleJqueryError("#rev_slider_4_1");
						}else{
							revapi4 = tpj("#rev_slider_4_1").show().revolution({
								sliderType:"standard",
								jsFileLocation:"js/",
								sliderLayout:"auto",
								dottedOverlay:"none",
								delay:9000,
								navigation: {
									keyboardNavigation:"off",
									keyboard_direction: "horizontal",
									mouseScrollNavigation:"off",
									onHoverStop:"off",
									touch:{
										touchenabled:"on",
										swipe_threshold: 75,
										swipe_min_touches: 1,
										swipe_direction: "horizontal",
										drag_block_vertical: false
									}
									,
									arrows: {
										style:"zeus",
										enable:true,
										hide_onmobile:true,
										hide_under:600,
										hide_onleave:true,
										hide_delay:200,
										hide_delay_mobile:1200,
										tmp:'<div class="tp-title-wrap">  	<div class="tp-arr-imgholder"></div> </div>',
										left: {
											h_align:"left",
											v_align:"center",
											h_offset:30,
											v_offset:0
										},
										right: {
											h_align:"right",
											v_align:"center",
											h_offset:30,
											v_offset:0
										}
									}
									/*,
									bullets: {
										enable:true,
										hide_onmobile:true,
										hide_under:600,
										style:"metis",
										hide_onleave:true,
										hide_delay:200,
										hide_delay_mobile:1200,
										direction:"horizontal",
										h_align:"center",
										v_align:"bottom",
										h_offset:0,
										v_offset:30,
										space:5,
										tmp:'<span class="tp-bullet-img-wrap">  <span class="tp-bullet-image"></span></span><span class="tp-bullet-title">{{title}}</span>'
									}*/
								},
								viewPort: {
									enable:true,
									outof:"pause",
									visible_area:"80%"
								},
								responsiveLevels:[1240,1024,778,480],
								gridwidth:[1240,1024,778,480],
								/*gridheight:[600,600,500,400],*/
								gridheight:[145,145,145,145],
								lazyType:"none",
								parallax: {
									type:"mouse",
									origo:"slidercenter",
									speed:2000,
									levels:[2,3,4,5,6,7,12,16,10,50],
								},
								shadow:0,
								spinner:"on",
								stopLoop:"off",
								stopAfterLoops:-1,
								stopAtSlide:-1,
								shuffle:"off",
								autoHeight:"off",
								hideThumbsOnMobile:"off",
								hideSliderAtLimit:0,
								hideCaptionAtLimit:0,
								hideAllCaptionAtLilmit:0,
								debugMode:false,
								fallbacks: {
									simplifyAll:"off",
									nextSlideOnWindowFocus:"off",
									disableFocusListener:false,
								}
							});
						}
					});	/*ready*/
				</script>
  </body>
</html>