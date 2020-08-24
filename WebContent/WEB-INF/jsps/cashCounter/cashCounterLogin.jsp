<%@include file="/WEB-INF/decorators/taglibs.jsp"%>

 <script type="text/javascript" src="./resources/layout_3/assets/js/core/libraries/jquery_ui/interactions.min.js"></script>
	<script type="text/javascript" src="./resources/layout_3/assets/js/plugins/forms/selects/select2.min.js"></script>

	<script type="text/javascript" src="./resources/layout_3/assets/js/pages/form_select2.js"></script>
	<script type="text/javascript" src="./resources/layout_3/assets/js/pages/form_validation.js"></script>
<script type="text/javascript" src="./resources/layout_3/assets/js/plugins/forms/validation/validate.min.js"></script>

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
	</div>
	
	<c:remove var="counterUserName" scope="session" />
	<c:remove var="dayclose" scope="session" />
	<c:remove var="branch" scope="session" />
	<c:remove var="holidayExist" scope="session" />
	<c:remove var="timeoutcounter" scope="session" />
	<c:if test = "${not empty counterAClose}"> 			        
        <script>		        
            var msg = "${counterAClose}";
              alert(msg);
        </script>
        <c:remove var="counterAClose" scope="session" />
	</c:if>
	
	
	<c:if test = "${not empty invalidUNPCC}"> 			        
        <script>		        
            var msg = "${invalidUNPCC}";
              alert(msg);
        </script>
        <c:remove var="invalidUNPCC" scope="session" />
	</c:if>
 
 
	
	<div class="page-container">
		<div class="page-content">
			<div class="content-wrapper">
				<div class="content">
					<form action="./loginValidate" method="post" class="form-horizontal">
						<div class="panel panel-body login-form">
							<div class="text-center">
								<div ><img src="resources/images/KUKL_logo.jpg" alt=""></div>
								<h5 class="content-group">Login to your Cash Counter<small class="display-block">Enter your credentials below</small></h5>
							</div>

							 <div class="form-group has-feedback has-feedback-left">
								<select class="form-control" id="location" onchange="getAllCounters(this.value)" name="location" required="required" placeholder="Select Location" autofocus>
														<option value="" disabled="disabled" selected="selected">Select Location</option>
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
								<select class="form-control" id="counter_no" name="counter_no" required="required" placeholder="Select Counter" autofocus >
														<option value="" disabled="disabled" selected="selected">Select Counter</option>
														
										</select>
								<div class="form-control-feedback">
									<i class="icon-user text-muted"></i>
								</div>
							</div> 
							
							<div class="form-group has-feedback has-feedback-left">
								<input type="text" id="user_login_name" name="user_login_name" required="required" class="form-control" placeholder="User Login Name"></input>
								<div class="form-control-feedback">
									<i class="icon-user text-muted"></i>
								</div>
							</div>

							<div class="form-group has-feedback has-feedback-left">
								<input type="password" id="password" name="password" required="required" class="form-control" placeholder="Password"></input>
								<div class="form-control-feedback">
									<i class="icon-lock2 text-muted"></i>
								</div>
							</div>
							
							 <input type="hidden" name="${_csrf.parameterName}"  value="${_csrf.token}" /> 

							<div class="form-group">
								<button type="submit" class="btn btn-primary btn-block">Sign in <i class="icon-circle-right2 position-right"></i></button>
							</div>

							<div class="text-center">
								<a href="./login">Login</a>
							</div>
							
							<!-- <div class="text-center">
								<a href="login_password_recover.html">Forgot password?</a>
							</div> -->
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>		
<script>
function landingPage(){
	
	window.location.href="./landingpage";
	
}
function getAllCounters(location){
	$("#counter_no").val("");
	$.ajax({
		  type: "GET",
		  url: "./getAllCounters/"+location,
		  dataType: "JSON",
		  cache       : false,
		  async       : false,
		  success: function(response){
			  var htmldata="<option value='' disabled='disabled' selected='selected'>Select Counter</option>";
			  for(var i=0;i<response.length;i++){
				  htmldata+="<option value='"+response[i].counterno+"'>"+response[i].countername+"</option>"
			  }
			  $("#counter_no").html(htmldata);
		  }
		});
}
</script>
	
<style>
	.navbar-inverse {
   background-color: #0A6B96;
    border-color: #ffffff;
    height: 92px;
}
	</style>