<%@include file="/WEB-INF/decorators/taglibs.jsp"%>


<script type="text/javascript">
$(document).ready(function(){
   $('#themeLayout').val('${themeVal}');
   $('#dateFormate').val('${dateVal}');
});

function submitForm() {
	$.ajax({
		  type: "GET",
		  url: "./updateApplicationSettings",
		  data: {
			  themeLayout:$('#themeLayout').val(),
			  dateFormate:$('#dateFormate').val()
		  },
		  dataType: "text",
		  cache: false,
		  success: function(response){
		    
		    swal({
	            title: "Success!",
	            text: "It will effect in 2 seconds.",
	            confirmButtonColor: "#2196F3",
	            timer: 2000
	        });
		    
		  }
		});
	
	setTimeout(function(){location.href="./dashboard"; }, 2000);
	return false;
}
</script>

<div class="panel panel-flat">
						<div class="panel-heading">
							<h5 class="panel-title">Application Settings</h5>
							<div class="heading-elements">
								<ul class="icons-list">
			                		<li><a data-action="collapse"></a></li>
			                		<li><a data-action="reload"></a></li>
			                		<li><a data-action="close"></a></li>
			                	</ul>
		                	</div>
						</div>

						<div class="panel-body">
							

							<form class="form-horizontal" action="#">
								<fieldset class="content-group">		
			                        <div class="form-group">
			                        	<label class="control-label col-lg-2">Select Theme Layout</label>
			                        	<div class="col-lg-10">
				                            <select name="themeLayout" class="form-control" id="themeLayout">
				                                <option value="layout_1">Layout 1</option>
				                                <!-- <option value="layout_2">Layout 2</option> -->
				                                <option value="layout_3">Layout 3</option>
				                                <option value="layout_4">Layout 4</option>
				                                <option value="layout_5">Layout 5</option>
				                                
				                            </select>
			                            </div>
			                        </div>
			                        
			                        <div class="form-group">
			                        	<label class="control-label col-lg-2">Select Date Format</label>
			                        	<div class="col-lg-10">
				                            <select name="dateFormate" class="form-control" id="dateFormate">
				                                <option value="MM/dd/yyyy">MM/dd/yyyy</option>
				                                <option value="dd/MM/yyyy">dd/MM/yyyy</option>
				                                <option value="MM/dd/yy">MM/dd/yy</option>
				                                <option value="yyyy/MM/dd">yyyy/MM/dd</option>
				                                <option value="dd-MM-yy">dd-MM-yy</option>
				                                <option value="dd-MM-yyyy">dd-MM-yyyy</option>
				                                <option value="MM-dd-yyyy">MM-dd-yyyy</option>
				                                <option value="yyyy-MM-dd">yyyy-MM-dd</option>
				                                
				                                 <option value="dd-MM-yyyy hh:mm:ss">dd-MM-yyyy hh:mm:ss</option>
				                                  <option value="MM-dd-yyyy hh:mm:ss">MM-dd-yyyy hh:mm:ss</option>
				                                   <option value="yyyy-MM-dd hh:mm:ss">yyyy-MM-dd hh:mm:ss</option>
				                             
				                            </select>
			                            </div>
			                        </div>

									</fieldset>
								

								<div class="text-right">
									<button type="submit" class="btn btn-primary" onclick="return submitForm()">Update <i class="icon-arrow-right14 position-right"></i></button>
								</div>
							</form>
						</div>
					</div>