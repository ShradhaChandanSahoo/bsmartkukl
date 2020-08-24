<%@include file="/WEB-INF/decorators/taglibs.jsp"%>

 <script type="text/javascript" src="./resources/layout_3/assets/js/core/libraries/jquery_ui/interactions.min.js"></script>
	<script type="text/javascript" src="./resources/layout_3/assets/js/plugins/forms/selects/select2.min.js"></script>

	<script type="text/javascript" src="./resources/layout_3/assets/js/pages/form_select2.js"></script>
	<script type="text/javascript" src="./resources/layout_3/assets/js/pages/form_validation.js"></script>
<script type="text/javascript" src="./resources/layout_3/assets/js/plugins/forms/validation/validate.min.js"></script>
<c:if test = "${not empty msg}"> 			        
		        <script>		        
		            var msg = "${msg}";
		            swal({
		                title: msg,
		                text: "",
		                confirmButtonColor: "#2196F3",
		            });
		        </script>
 </c:if>
<div class="panel panel-flat">
										<div class="panel-heading">
											<fieldset class="content-group"> 
											<legend class="text-bold">Change Password</legend></fieldset>
										</div>

										<div class="panel-body">
											<form action="./changepassword" class="form-horizontal form-validate-jquery">
												<div class="form-group">
													<div class="row">
														<div class="col-md-6">
															<label>Username</label>
															<input type="text" name="username" id="username" value="${userName}" readonly="readonly" class="form-control">
														</div>

														<div class="col-md-6">
															<label>Current password</label>
															<input type="password" name="currentpassword" id="currentpassword" required="required" placeholder="Enter Current Password" class="form-control">
														</div>
													</div>
												</div>

												<div class="form-group">
													<div class="row">
														<div class="col-md-6">
															<label>New password</label>
															<input type="password" id="newpassword" name="newpassword" required="required" placeholder="Enter new password" class="form-control">
														</div> 

														<div class="col-md-6">
															<label>Repeat New password</label>
															<input type="password" id="repeatpassword" name="repeatpassword" required="required"  placeholder="Repeat new password" class="form-control">
														</div>
													</div>
												</div>

						                        <div class="text-center">
						                        	<button type="submit" class="btn btn-primary">Change Password <i class="icon-arrow-right14 position-right"></i></button>
						                        </div>
					                        </form>
										</div>
									</div>
<script>
var validator = $(".form-validate-jquery").validate({
    ignore: 'input[type=hidden], .select2-search__field', // ignore hidden fields
    errorClass: 'validation-error-label',
    successClass: 'validation-valid-label',
    highlight: function(element, errorClass) {
        $(element).removeClass(errorClass);
    },
    unhighlight: function(element, errorClass) {
        $(element).removeClass(errorClass);
    },

    // Different components require proper error label placement
    errorPlacement: function(error, element) {

        // Styled checkboxes, radios, bootstrap switch
        if (element.parents('div').hasClass("checker") || element.parents('div').hasClass("choice") || element.parent().hasClass('bootstrap-switch-container') ) {
            if(element.parents('label').hasClass('checkbox-inline') || element.parents('label').hasClass('radio-inline')) {
                error.appendTo( element.parent().parent().parent().parent() );
            }
             else {
                error.appendTo( element.parent().parent().parent().parent().parent() );
            }
        }

        // Unstyled checkboxes, radios
        else if (element.parents('div').hasClass('checkbox') || element.parents('div').hasClass('radio')) {
            error.appendTo( element.parent().parent().parent() );
        }

        // Input with icons and Select2
        else if (element.parents('div').hasClass('has-feedback') || element.hasClass('select2-hidden-accessible')) {
            error.appendTo( element.parent() );
        }

        // Inline checkboxes, radios
        else if (element.parents('label').hasClass('checkbox-inline') || element.parents('label').hasClass('radio-inline')) {
            error.appendTo( element.parent().parent() );
        }

        // Input group, styled file input
        else if (element.parent().hasClass('uploader') || element.parents().hasClass('input-group')) {
            error.appendTo( element.parent().parent() );
        }

        else {
            error.insertAfter(element);
        }
    },
    validClass: "validation-valid-label",
    success: function(label) {
        label.addClass("validation-valid-label").text("Success.")
    },
    rules: {
    	password: {
            minlength: 5
        },
    	newpassword: {
            minlength: 5
        },
        repeatpassword: {
            equalTo: "#newpassword"
        },
        email_id: {
            email: true
        },
        mobileno: {
        	minlength: 10,
            maxlength: 10,
            digits: true
        },
        minimum_number: {
            min: 10
        },
        maximum_number: {
            max: 10
        },
        number_range: {
            range: [10, 20]
        },
        url: {
            url: true
        },
        date: {
            date: true
        },
        date_iso: {
            dateISO: true
        },
        numbers: {
            number: true
        },
        digits: {
            digits: true
        },
        creditcard: {
            creditcard: true
        },
        basic_checkbox: {
            minlength: 2
        },
        styled_checkbox: {
            minlength: 2
        },
        switchery_group: {
            minlength: 2
        },
        switch_group: {
            minlength: 2
        }
    },
    messages: {
        custom: {
            required: "This is a custom error message",
        },
        mobileno : "Please Enter 10 Digit Mobile Number",
        agree: "Please accept our policy"
    }
});

</script>							
									
<style>
.content-group {
    margin-bottom: 0px !important;
}

</style>