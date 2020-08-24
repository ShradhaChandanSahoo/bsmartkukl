<%@include file="/WEB-INF/decorators/taglibs.jsp"%>
<script type="text/javascript">

$(document).ready(function(){   
	$('#umpayments').show();
	$('#paymentsModule').addClass('active');
	
	 $('#receiptdate_Nep').nepaliDatePicker({
		 dateFormat: "%D, %M %d, %y",
			npdMonth: true,
			npdYear: true
	});
   
   $('#receiptdate_Nep1').nepaliDatePicker({
		 dateFormat: "%D, %M %d, %y",
			npdMonth: true,
			npdYear: true
	});
   $('#receiptdate_Nep2').nepaliDatePicker({
		 dateFormat: "%D, %M %d, %y",
			npdMonth: true,
			npdYear: true
	});
});

	</script>
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
<c:if test = "${not empty msgdayclose}"> 			        
		        <script>		        
		            var msg = "${msgdayclose}";
		            swal({
		                title: msg,
		                text: "",
		                confirmButtonColor: "#2196F3",
		            });
		        </script>
 </c:if>
<div class="panel panel-flat">
						<div class="panel-body">
							<fieldset class="content-group"> 
								<legend class="text-bold">Cash Counter Day Close</legend>
									<div class="row">
											<div class="col-md-3">
												<div class="form-group">
													<label>Counter No</label>
					                                <input type="text"  id="counter_no" value="${counterno}" class="form-control" readonly="readonly" placeholder="Counter No" />
				                                </div>
											</div>
											<div class="col-md-3">
												<div class="form-group">
													<label>Counter Name</label>
					                                <input type="text"  id="counter_name" value="${counterName}" class="form-control" readonly="readonly" placeholder="User Name" />
				                                </div>
											</div>
											<div class="col-md-3">
												<div class="form-group">
													<label>Date</label><div class="input-group"> 
					                                <span class="input-group-addon"><i class="icon-calendar3"></i></span>
					                                <input type="text" class="form-control" value="${dayclosedate}" readonly="readonly" name="counterdate" id="counterdate"/>
				                                </div></div>
											</div>
											<div class="col-md-3">
												<div class="form-group">
													<label>Nepali Date</label><div class="input-group"> 
													<span class="input-group-addon"><i class="icon-calendar3"></i></span>
													<input name="receiptdate_Nep"  placeholder="Cheq/DD Date Nepali" id="receiptdate_Nep" class="form-control nepali-calendar" />
										</div>
										</div>
											</div>
											
										</div>
									
									<!-- <div class="col-md-12" id="addId" >
												<div class="form-group text-center" >
													<label>&nbsp;</label>
					                                <button type="button" onclick="getDatafordayclose();" class="btn bg-teal btn-ladda" style="width: 200px;" data-style="expand-right" data-spinner-size="20">
													<span class="ladda-label">Check Day Close</span></button>
				                                </div>
											</div> -->
								</fieldset>	
								<fieldset class="content-group" id="finalsummary"> 
								<legend class="text-bold">Final Cash Summary</legend>
									<div class="row">
									
											<div class="col-md-3">
												<div class="form-group">
													<label>Total Amount(Cash & Cheque)</label>
					                                <input type="text"  id="cashcheque" value="${datalist[0].totalAmount}" class="form-control" placeholder="Total Amount" /><!--  -->
				                                </div>
											</div>
											
											<div class="col-md-3">
												<div class="form-group">
													<label>No of Cheques</label>
					                                <input type="text"  id="chequeEntry" value="${datalist[0].cdcount}" class="form-control" /><!--  -->
				                                </div>
											</div>
											
											
											<div class="col-md-3">
												<div class="form-group">
													<label>Cheque Amount</label>
					                                <input type="text"  id="chequeAmount" value="${datalist[0].cdamount}" class="form-control" /><!--  -->
				                                </div>
											</div>
											
											
											<div class="col-md-3">
												<div class="form-group">
													<label>No of Cash Entries</label>
					                                <input type="text"  id="cashEntries" value="${datalist[0].cashcount}" class="form-control" /><!--  -->
				                                </div>
											</div>
											
											<div class="col-md-3">
												<div class="form-group">
													<label>Cash Amount</label>
					                                <input type="text"  id="totalAmount" value="${datalist[0].cashamount}" class="form-control" placeholder="Total Amount" /><!--  -->
				                                </div>
											</div>
											
											
											
											<div class="col-md-3">
												<div class="form-group">
													<label>Amount In Hand</label>
					                                <input type="text"  id="amount_in_hand" onchange="calculateDiffrence(this.value);" class="form-control" required="required" placeholder="Amount In hand" />
				                                </div>
											</div>
											<div class="col-md-3">
												<div class="form-group">
													<label>Difference</label>
					                                <input type="text"  id="difference" readonly="readonly" class="form-control" required="required" placeholder="Difference" />
				                                </div>
											</div>
											<div class="col-md-3">
												<div class="form-group">
													<label>Date</label><div class="input-group"> 
					                                <span class="input-group-addon"><i class="icon-calendar3"></i></span>
					                                <input type="text" class="form-control" readonly="readonly" value="${dayclosedate}" name="cddate" id="cddate" ></input>
					                                <input name="rece2"  placeholder="Nepali Date" readonly="readonly" id="rece2" class="form-control" />
				                                </div></div>
											</div>
											
											
											
											<div class="col-md-12" id="addId" >
												<div class="form-group text-center" >
													<label>&nbsp;</label>
					                                <button type="button" onclick="return callconfirm();" class="btn bg-teal btn-ladda" style="width: 200px;" data-style="expand-right" data-spinner-size="20">
													<span class="ladda-label">Day Close</span></button>
				                                </div>
											</div>
											
										</div>
									
									
								</fieldset>	
						</div>	
						
								</div>
	<script>
	function calculateDiffrence(val){
		var totalAmount= $("#totalAmount").val();
		var diff=totalAmount-val;
		$("#difference").val(diff);
	}
	function getDatafordayclose(){   
		 $("#totalAmount").val("");
		 $("#cddate").val("");
		 $("#difference").val("");
		 $("#amount_in_hand").val("");
		var counterno=$("#counter_no").val();
		//var countername=$("#counter_name").val();
		var counterdate=$("#counterdate").val();
		var counterdatenep=$("#receiptdate_Nep").val();
		//alert(counterno+countername+counterdate+counterdatenep);
		$.ajax({
			  type: "GET",
			  url: "./getdataforDayClose",
			  dataType: "JSON",
			  data:{
				  counterno : counterno,
				  counterdate : counterdate,
			  },
			  cache       : false,
			  async       : false,
			  success: function(response){
				  if(response!=null && response!=""){
				 $("#totalAmount").val(response[0].totalAmount);
				 $("#cddate").val(counterdate);
				 $("#finalsummary").show();
				  }else{
					  $("#totalAmount").val(0);
						 $("#cddate").val(counterdate);
						 $("#finalsummary").show();
				  }
			  },
		 error: function (xhr) {
			 swal({
	                title: "Unable To Get The Data",
	                text: "",
	                confirmButtonColor: "#2196F3",
	            });     
			  },
			});
		
		
	}
	
	var insertDayRecord="";
	
	function callconfirm(){
		var counter_no=$("#counter_no").val();
		var diff=$("#difference").val();
		var amount_in_hand=$("#amount_in_hand").val();
		var totalAmount=$("#totalAmount").val();
		var counterdate1=$("#counterdate").val();
		if(amount_in_hand==""){
			swal({
                title: "Please Enter Amount In Hand ",
                text: "",
                confirmButtonColor: "#2196F3",
            });
		 return false;
		}
		if(diff=="" || diff!=0 ){
			 swal({
	                title: "Total Amount And Amount In Hand Should Be Same",
	                text: "",
	                confirmButtonColor: "#2196F3",
	            });
			 return false;
		}
		
		
		$.ajax({
			  type: "GET",
			  url: "./checkCloseReceiptForForDay",
			  dataType: "text",
			  data:{
				  counter_no : counter_no,
				  counterdate1 : counterdate1
			  },
			  cache       : true,
			  async       : false,
			  success: function(response){
				
				  if(response=='1'){
					  alert("Cash Counter already Closed for the selected date");
					  window.location.reload();
					  insertDayRecord="yes";
				  }else{
					  
				  }

				  
			  },
		 error: function (xhr) {
			     
			  },
			  
			}); 
		
		
	if(insertDayRecord==""){
		
		if(confirm("Are You Sure You Want To Close Entering Receipt For The Day")){
			  
			var counter_name=$("#counter_name").val();
			var cddate=$("#cddate").val();
			$.ajax({
				  type: "GET",
				  url: "./closeReceiptForForDay",
				  dataType: "text",
				  data:{
					  counter_no : counter_no,
					  counter_name : counter_name,
					  cddate : cddate,
					  amount_in_hand : amount_in_hand,
					  diff : diff,
					  totalAmount : totalAmount,
				  },
				  cache       : true,
				  async       : false,
				  success: function(response){
					  swal({
			                title: "Receipt For The Day "+response+" Has Been Closed",
			                text: "",
			                confirmButtonColor: "#2196F3",
			            });
				  },
			 error: function (xhr) {
				     
				  },
				}); 
			
			
			 
		}
	}
	
	insertDayRecord="";
	}
	</script>
<style>
.form-group {
    margin-bottom: 10px;
    position: relative;
}
legend {
	text-trans none;
	font-size: 16px;
	border-color: #0DB7A5;
}
.form-horizontal .form-group {
    margin-left: 20px;
    margin-right: 40px;
}
</style>