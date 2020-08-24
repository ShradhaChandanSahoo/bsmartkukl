<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<title>BSmart-KUKL</title>
<meta name="description" content="app, web app, responsive, responsive layout, admin, admin panel, admin dashboard, flat, flat ui, ui kit, AngularJS, ui route, charts, widgets, components" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
  
<link href="./resources/layout_3/assets/css/icons/icomoon/styles.css" rel="stylesheet" type="text/css">
<link href="./resources/layout_3/assets/css/bootstrap.css" rel="stylesheet" type="text/css">
<link href="./resources/layout_3/assets/css/core.css" rel="stylesheet" type="text/css">
<link href="./resources/layout_3/assets/css/components.css" rel="stylesheet" type="text/css">
<link href="./resources/layout_3/assets/css/colors.css" rel="stylesheet" type="text/css">
 
  <script>
   function goingToBack(){
	   window.history.back();
   }
  </script>

</head>
<body>
<div class="app">
	<!-- Page container -->
	<div class="page-container">

		<!-- Page content -->
		<div class="page-content">

			<!-- Main content -->
			<div class="content-wrapper">

				<!-- Content area -->
				<div class="content">

					<!-- Error title -->
					<div class="text-center content-group">
						<h1 class="error-title">403</h1>
						<h5>Oops, an error has occurred. Forbidden!</h5>
					</div>
					<!-- /error title -->


					<!-- Error content -->
					<div class="row">
						<div class="col-lg-4 col-lg-offset-4 col-sm-6 col-sm-offset-3">
							<form action="#" class="main-search">
								

								<div class="row">
									<div class="col-sm-3">
									
									</div>
									
									<div class="col-sm-6">
										<a href="#" class="btn btn-primary btn-block content-group" onclick="goingToBack()" ui-sref="app.dashboard" md-ink-ripple class="md-btn indigo md-raised p-h-lg"><i class="icon-circle-left2 position-left"></i> Go Back</a>
									</div>

									
								</div>
								
							</form>
						</div>
					</div>
					<!-- /error wrapper -->


					

				</div>
				<!-- /content area -->

			</div>
			<!-- /main content -->

		</div>
		<!-- /page content -->

	</div>
	<!-- /page container -->

</div>

<script src="../libs/jquery/jquery/dist/jquery.js"></script>
<script src="../libs/jquery/bootstrap/dist/js/bootstrap.js"></script>
<script src="../libs/jquery/waves/dist/waves.js"></script>

<script src="scripts/ui-load.js"></script>
<script src="scripts/ui-jp.config.js"></script>
<script src="scripts/ui-jp.js"></script>
<script src="scripts/ui-nav.js"></script>
<script src="scripts/ui-toggle.js"></script>
<script src="scripts/ui-form.js"></script>
<script src="scripts/ui-waves.js"></script>
<script src="scripts/ui-client.js"></script>

</body>
</html>

<style>
.amber-200 {
    background-color: rgb(255, 243, 255);
}
</style>


