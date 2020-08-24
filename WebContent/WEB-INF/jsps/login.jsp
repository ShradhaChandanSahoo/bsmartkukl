<%@include file="/WEB-INF/decorators/taglibs.jsp"%>

<!--For JBOSS & Wild Fly Server-->
<%-- <jsp:include page="/WEB-INF/decorators/loginlayout.jsp"/> --%>
<!--End -->

<!-- Main navbar -->

<c:if test = "${not empty invalidUNP}"> 			        
      <script>		        
          var msg = "${invalidUNP}";
            alert(msg);
      </script>
      <c:remove var="invalidUNP" scope="session" />
</c:if>
<c:if test = "${not empty activeCheck}"> 			        
      <script>		        
          var msg = "${activeCheck}";
            alert(msg);
      </script>
      <c:remove var="activeCheck" scope="page" />
</c:if>
<c:if test = "${not empty expirydate}"> 			        
      <script>		        
          var msg = "${expirydate}";
            alert(msg);
      </script>
     
</c:if>
	
	
	<div class="navbar navbar-inverse">
		<div class="navbar-header">
			 <img src="resources/images/bsmart-water.png" alt=""  height="85px;" onclick="landingPage();" style="cursor: pointer;">
			 
			 <span style="padding-left:165px;white-space: nowrap; font-size: 34px; font-weight: 400;color: white;font-family:Times New Roman;padding-top:24px"> Kathmandu Upatyaka Khanepani Limited
			</span>
						
						
			 <a class="navbar-brand" href="index.html"> </a>

			<ul class="nav navbar-nav pull-right visible-xs-block">
				<li><a data-toggle="collapse" data-target="#navbar-mobile"><i class="icon-tree5"></i></a></li>
			</ul>
		</div>

		<!-- <div class="navbar-collapse collapse" id="navbar-mobile">
			<ul class="nav navbar-nav navbar-right">
				<li>
					<a href="#">
						<i class="icon-display4"></i> <span class="visible-xs-inline-block position-right"> Go to website</span>
					</a>
				</li>

				<li>
					<a href="#">
						<i class="icon-user-tie"></i> <span class="visible-xs-inline-block position-right"> Contact admin</span>
					</a>
				</li>

				<li class="dropdown">
					<a class="dropdown-toggle" data-toggle="dropdown">
						<i class="icon-cog3"></i>
						<span class="visible-xs-inline-block position-right"> Options</span>
					</a>
				</li>
			</ul>
		</div> -->
	</div>
	<!-- /main navbar -->


	<!-- Page container -->
	<div class="page-container">

		<!-- Page content -->
		<div class="page-content">

			<!-- Main content -->
			<div class="content-wrapper">

				<!-- Content area -->
				<div class="content">

					<!-- Simple login form -->
					<form action="${loginUrl}" method="post" class="form-horizontal">
						<div class="panel panel-body login-form">
							<div class="text-center">
								<div ><img src="resources/images/KUKL_logo.jpg" alt=""></div>
								<h5 class="content-group">Login to your account <small class="display-block">Enter your credentials below</small></h5>
							</div>
							<div class="form-group has-feedback has-feedback-left">
							<select class="form-control" id="location2" name="location2"
								placeholder="Select Location" autofocus require="required" onchange="return locationWise(this.value);">
								<option value="CORPORATE">CORPORATE</option>
								<option value="BRANCH">BRANCH</option>
							</select>
							<div class="form-control-feedback">
								<i class="icon-user text-muted"></i>
							</div>
						</div>

							 <div class="form-group has-feedback has-feedback-left" id="branchdiv" style="display: none;">
								<select class="form-control" id="location" name="location" placeholder="Select Location" autofocus require="required">
														<option value="TRIPUR_1115">TRIPURESHWOR</option>
														<option value="BANESH_1112">BANESHWOR</option>
														<option value="LALITPUR_1118">LALITPUR</option>
														<option value="BHAKTAPUR_1116">BHAKTAPUR</option>
														<option value="CHHETRA_1114">CHHETRAPATI</option>
														<option value="MAHAR_1111">MAHARAJGUNG</option>
														<option value="THIMI_1117">THIMI</option>
														<option value="KIRTIPUR_1119">KIRTIPUR</option>
														<option value="MAHAN_1110">MAHANKALCHAUR</option>
														<option value="KAMALADI_1113">KAMALADI</option>
														<option value="TESTLOC_2222">TESTLOCATION</option>
														
														
										</select>
								<div class="form-control-feedback">
									<i class="icon-user text-muted"></i>
								</div>
							</div> 
							
							<div class="form-group has-feedback has-feedback-left">
								<input type="text" class="form-control" id="username" name="ssoId" placeholder="Enter Username" autofocus required>
								<div class="form-control-feedback">
									<i class="icon-user text-muted"></i>
								</div>
							</div>

							<div class="form-group has-feedback has-feedback-left">
								<input type="password" class="form-control" id="password" name="password" placeholder="Enter Password" required>
								<div class="form-control-feedback">
									<i class="icon-lock2 text-muted"></i>
								</div>
							</div>
							
							 <input type="hidden" name="${_csrf.parameterName}"  value="${_csrf.token}" /> 

							<div class="form-group">
								<button type="submit" class="btn btn-primary btn-block">Sign in <i class="icon-circle-right2 position-right"></i></button>
							</div>

							<div class="text-center">
								<a href="./cashCounterLogin">Cash Counter Login</a>
							</div>
							
							<!-- <div class="text-center">
								<a href="login_password_recover.html">Forgot password?</a>
							</div> -->
						</div>
					</form>
					<!-- /simple login form -->

				</div>
				<!-- /content area -->

			</div>
			<!-- /main content -->

		</div>
		<!-- /page content -->

	</div>
	<!-- /page container -->
	
	
<script>

function landingPage(){
	
	window.location.href="./landingpage";
	
}

function locationWise(param){
	
	if(param=="CORPORATE"){
		$("#branchdiv").hide();
	}else if(param=="BRANCH"){
		$("#branchdiv").show();
	}
}

</script>
	
<style>
	.navbar-inverse {
    background-color: #0A6B96;
    border-color: #ffffff;
    height: 92px;
}
	</style>