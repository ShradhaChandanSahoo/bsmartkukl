<%@include file="/WEB-INF/decorators/taglibs.jsp"%>
<script>
$(document).ready(function()
{   
	$('#nepalRdate').nepaliDatePicker({
		 dateFormat: "%D, %M %d, %y",
			npdMonth: true,
			npdYear: true
	});
	
	$('#nepalCdate').nepaliDatePicker({
		 dateFormat: "%D, %M %d, %y",
			npdMonth: true,
			npdYear: true
	});
	$('#cashCounterScreen').show();
	$('#cashCounter').addClass('active');
	
	var activeMod="${activeModulesNew}";
	var module="Cash Counter";
	var check=activeMod.indexOf(module) > -1;
	
	if(check){
	}else{
		window.location.href="./accessDenied";
	}
	$('#engCdate,#engRdate').val('');
	//$('#collectionIdUl').removeClass('hidden-ul');
}); 
</script>
<style>
hr {
    display: block;
    height: 1px;
    border: 0;
    margin: 1em 0;
    padding: 0;
    background-color: red;
}
/* #trHeading {
    font-size: 120%;
} */
/* input[type="checkbox"]
{
outline: 1px solid green !important;

  background-color: #A8AAC1;
  border-radius: 50px;
} */

</style>


<script type="text/javascript">

/* function searchCashDetails()
{
	$('#cashSearch')[0].reset();
	return false;
} */

function disableOptions(checkId,inputId)
{
	if($('#'+checkId).prop('checked')==true)
	{
		$("#"+inputId).prop( "disabled", false );
	}
	else
	{
		var x = document.getElementById(inputId+'').tagName;
		if(x=='SELECT')
		{
			$("#"+inputId).val('0');
		}
		else
		{
			$("#"+inputId).val('');
		}
		$("#"+inputId).prop( "disabled", true );
	}
}

function searchCashDetails()
{
	//var conectionVal='',receiptVal='',payTowardsVal='',amount='',nepalDate='',englishDate='',bankname='',ChequeNo='',ChequedateNepal='',paymode='',ChequedateEng='',wardno='';
	var conectionVal='empty',receiptVal='empty',payTowardsVal='empty',amount='empty',nepalDate='empty',englishDate='empty',bankname='empty',ChequeNo='empty',ChequedateNepal='empty',paymode='empty',ChequedateEng='empty',wardno='empty';
	
  /* if($('#connectionCheck').prop('checked')==true && $('#connectionNo').val().trim()=='')
	{
	 alert('Please enter connection No.');
	 return false;
	} 
 else if($('#connectionCheck').prop('checked')==false && $('#connectionNo').val().trim()!='')
	 {
	  alert('Please Check Connection No.');
	  return false;
	 }
  else if($('#connectionCheck').prop('checked')==false && $('#connectionNo').val().trim()=='')
	 {
	  conectionVal='empty';
	 } 
 else
	 {
	    conectionVal=$('#connectionNo').val();
	 }  */
	 
	 /* conectionVal=$('#connectionNo').val();
	 receiptVal=$('#receiptNo').val();
	 payTowardsVal=$('#payTowards').val();
	 amount=$('#amount').val();
	 nepalDate=$('#nepalRdate').val();
	 englishDate=$('#engRdate').val();
	 bankname=$('#bankName').val();
	 ChequeNo=$('#ChequeNo').val();
	 ChequedateNepal=$('#nepalCdate').val();
	 paymode=$('#paymode').val();
	 ChequedateEng=$('#engCdate').val();
	 wardno=$('#wardNo').val(); */
	 if($('#connectionNo').val().trim()!='')
	 {
		 conectionVal=$('#connectionNo').val().trim();
	 }
	  if($('#receiptNo').val().trim()!='')
	 {
	   receiptVal=$('#receiptNo').val().trim();
	 }
	 if($('#payTowards').val().trim()!='0')
	 {
	  payTowardsVal=$('#payTowards').val().trim();
	 }
	  if($('#amount').val().trim()!='')
	 {
	  amount=$('#amount').val().trim();
	 }
	 if($('#nepalRdate').val().trim()!='')
	 {
	  nepalDate=$('#nepalRdate').val().trim();
	 }
	 if($('#engRdate').val().trim()!='')
	 {
	  englishDate=moment($('#engRdate').val()).format('DD-MM-YYYY');
	 }
	  if($('#bankName').val().trim()!='0')
	 {
	  bankname=$('#bankName').val().trim();
	 }
	  if($('#ChequeNo').val().trim()!='')
	 {
	  ChequeNo=$('#ChequeNo').val().trim();
	 }
	 // alert(moment($('#nepalCdate').val()).format('DD-MM-YYYY'))
	  if($('#nepalCdate').val().trim()!='')
	 {
	  ChequedateNepal=$('#nepalCdate').val().trim();
	 }
	  if($('#paymode').val().trim()!='0')
	 {
	  paymode=$('#paymode').val().trim();
	 }
	  if($('#engCdate').val().trim()!='')
	 {
	  ChequedateEng=moment($('#engCdate').val()).format('DD-MM-YYYY');
	 }
	 if($('#wardNo').val().trim()!='0')
	 {
	   wardno=$('#wardNo').val().trim(); 
	 } 
	 var tableData = "";	
	 $("#loading").show();
$.ajax({
   	    type :"get",
        dataType :'json',
   	    url :"./searchCashDetails/"+conectionVal+"/"+receiptVal+"/"+payTowardsVal+"/"+amount+"/"+nepalDate+"/"+englishDate+"/"+bankname+"/"+ChequeNo+"/"+ChequedateNepal+"/"+paymode+"/"+ChequedateEng+"/"+wardno,
 		async:false,
    	cache:false,
    	success : function(response) 
		
	{
				for (var s = 0, len = response.length; s < len; ++s) {
					var obj = response[s];

					tableData += "<tr>" + "<td>" + (s + 1) + "</td>" + "<td>"
							+ obj.connection_no + "</td>" + "<td>"
							+ obj.receipt_no + "</td>" + "<td>"
							+ obj.receipt_date + "</td>" + "<td>" + obj.cd_no
							+ "</td>";
					if (obj.pay_mode == null) {
						tableData += "<td></td>";
					} else {
						if (obj.pay_mode == '1') {
							tableData += "<td>CASH</td>";
						} else {
							tableData += "<td>CHEQUE</td>";
						}
						/* if(data.payMode == '2'){ConnectionData+="<td>CHEQUE</td>";}
						if(data.payMode == '3'){ConnectionData+="<td>DD</td>";}
						if(data.payMode == '5'){ConnectionData+="<td>ONLINE PAYMENT</td>";} */
					}
					;

					tableData += "<td>" + obj.amount + "</td>" + "<td>"
							+ obj.bank_nam + "</td>" + "<td>" + obj.towards
							+ "</td>" + "<td>" + obj.ward_no + "</td>"
							+ "</tr>";

				}
				loadSearchFilter1('cashDetailsTab', tableData, 'paymentsData');
				$('#paymentsData').empty();
				$('#paymentsData').html(tableData);
				$('#cashDetailsDiv').show();
				$("#loading").hide(); 
			}
		});
	}
	function loadSearchAndFilter(param) {
		$('#' + param).dataTable().fnDestroy();
		$('#' + param).dataTable({
			"aLengthMenu" : [ [ 20, 50, 100, -1 ], [ 20, 50, 100, "All" ] // change per page values here
			],
			"iDisplayLength" : 50
		});
		jQuery('#' + param + '_wrapper .dataTables_filter input').addClass(
				"form-control input-small"); // modify table search input 
		jQuery('#' + param + '_wrapper .dataTables_length select').addClass(
				"form-control input-xsmall"); // modify table per page dropdown 
		jQuery('#' + param + '_wrapper .dataTables_length select').select2(); // initialize select2 dropdown 
	}

	function loadSearchFilter1(param, tableData, temp) {
		$('#' + param).dataTable().fnClearTable();
		$('#' + param).dataTable().fnDestroy();
		$('#' + temp).html(tableData);
		$('#' + param).dataTable();

	}
</script>


<div class="panel panel-flat">
	<div class="panel-heading" style="height: 70px;">
		<h5 class="panel-title text-bold">CASH SEARCH</h5>
		<!-- <div class="heading-elements">
							<ul class="icons-list">
		                		<li><a data-action="collapse"></a></li>
		                		<li><a data-action="reload"></a></li>
		                		<li><a data-action="close"></a></li>
		                	</ul>
	                	</div> -->
		<!-- <hr/> -->
		<!-- <a class="heading-elements-toggle"><i class="icon-menu"></i></a> -->
	</div>

	<!-- <div class="panel-body"> -->
	<div style="overflow-x: auto;">
		<table class="table" id="tableHeading">
			<!-- <tr class="bg-teal-600"><td><b><span id="trHeading">
					<font  style="font-family:Arial, Helvetica, sans-serif;color: white"><i class="icon-stack2"></i>&nbsp;CASH SEARCH</font></span></b></td>
					</tr> -->
			<tr>

				<td>
					<table class="table table-bordered" id="tableForm">
						<tr>
							<td><input class="control-info styled" type="checkbox"
								id="connectionNoCheck"
								onclick="disableOptions(this.id,'connectionNo')">&nbsp;&nbsp;Connection
								No.</td>
							<td><input class="form-control" placeholder="" type="text"
								id="connectionNo" name="connectionNo" disabled="disabled"></td>

							<td><input class="control-info styled" type="checkbox"
								id="receiptNoCheck"
								onclick="disableOptions(this.id,'receiptNo')">&nbsp;&nbsp;Receipt
								No.</td>
							<td><input class="form-control " placeholder="" type="text"
								id="receiptNo" name="receiptNo" disabled="disabled"></td>
						</tr>
						<tr>
							<td><input class="control-info styled" type="checkbox"
								id="payTowardsCheck"
								onclick="disableOptions(this.id,'payTowards')">&nbsp;&nbsp;Pay
								Towards</td>
							<td>
								<!-- <input class="form-control " placeholder="" type="text" > -->
								<select class="form-control select" id="payTowards"
								id="payTowards" disabled="disabled">
									<option Value="0">SELECT</option>
									<option Value="BILL PAYMENT">BILL PAYMENT</option>
									<option Value="DISSCONNECTION">DISSCONNECTION</option>
									<option Value="METER TESTING">METER TESTING</option>
									<option Value="RECONNECTION">RECONNECTION</option>
									<option Value="SEWERAGE CONNECTION">SEWERAGE
										CONNECTION</option>
									<option Value="WATER CONNECTION">WATER CONNECTION</option>
									<option Value="WATER SECURITY">WATER SECURITY</option>
									<option Value="SEWERAGE SECURITY">SEWERAGE SECURITY</option>
									<option Value="WAP APPLICATION">WAP APPLICATION</option>
									<option Value="ROAD CUT FEES">ROAD CUT FEES</option>
									<option Value="PENALTY">PENALTY</option>
							</select>
							</td>
							<td><input class="control-info styled" type="checkbox"
								id="amountCheck" onclick="disableOptions(this.id,'amount')">&nbsp;&nbsp;Amount</td>
							<td><input class="form-control " placeholder="" type="text"
								id="amount" id="amount" disabled="disabled"></td>
						</tr>
						<tr>
							<td><input class="control-info styled" type="checkbox"
								id="nepalRdatecheck"
								onclick="disableOptions(this.id,'nepalRdate')">&nbsp;&nbsp;Receipt
								Date in Nepali</td>
							<td>
								<div class="input-group">
									<span class="input-group-addon "><i
										class="icon-calendar"></i></span> <input id="nepalRdate"
										name="nepalRdate"
										class="form-control nepali-calendar ndp-nepali-calendar"
										disabled="disabled" placeholder=""
										onfocus="showNdpCalendarBox('readingdate_Nep')" type="text">
								</div>
							</td>
							<td><input class="control-info styled " type="checkbox"
								id="engRdateCheck" onclick="disableOptions(this.id,'engRdate')">&nbsp;&nbsp;Receipt
								Date in English</td>
							<td>
								<div class="input-group">
									<span class="input-group-addon "><i
										class="icon-calendar"></i></span> <input type="text" id="engRdate"
										name="engRdate" class="form-control daterange-single "
										placeholder="dd/MM/yyyy" disabled="disabled">
								</div>
							</td>
						</tr>
						<tr>
							<td><input class="control-info styled" type="checkbox"
								id="bankNameCheck" onclick="disableOptions(this.id,'bankName')">&nbsp;&nbsp;Bank
								Name</td>
							<td><select class="form-control select" id="bankName"
								name="bankName" disabled="disabled">
									<option Value="0">SELECT</option>
									<option Value="1">SBM</option>
							</select></td>
							<td><input class="control-info styled" type="checkbox"
								id="ChequeNoCheck" onclick="disableOptions(this.id,'ChequeNo')">&nbsp;&nbsp;Cheque/DD
								No.</td>
							<td><input class="form-control " placeholder="" type="text"
								id="ChequeNo" name="ChequeNo" disabled="disabled"></td>
						</tr>
						<tr>
							<td><input class="control-info styled" type="checkbox"
								id="nepalCdatecheck"
								onclick="disableOptions(this.id,'nepalCdate')">&nbsp;&nbsp;Cheque/DD
								Date in Nepali</td>
							<td><div class="input-group">
									<span class="input-group-addon "><i
										class="icon-calendar"></i></span> <input id="nepalCdate"
										name="nepalCdate"
										class="form-control nepali-calendar ndp-nepali-calendar"
										disabled="disabled" placeholder=""
										onfocus="showNdpCalendarBox('readingdate_Nep')" type="text">
								</div></td>
							<td><input class="control-info styled" type="checkbox"
								id="engCdateCheck" onclick="disableOptions(this.id,'engCdate')">&nbsp;&nbsp;Cheque/DD
								Date in English</td>
							<td><div class="input-group">
									<span class="input-group-addon "><i
										class="icon-calendar"></i></span> <input type="text" id="engCdate"
										name="engCdate" class="form-control daterange-single "
										placeholder="dd/MM/yyyy" disabled="disabled">
								</div></td>
						</tr>
						<tr>
							<td><input class="control-info styled" type="checkbox"
								id="paymodeCheck" onclick="disableOptions(this.id,'paymode')">&nbsp;&nbsp;Payment
								Mode</td>
							<td><select class="form-control select" id="paymode"
								id="paymode" disabled="disabled">
									<option Value="0">SELECT</option>
									<option Value="1">CASH</option>
									<option Value="2">CHEQUE</option>
									<option Value="3">DD</option>
									<option Value="4">FLAT FILE</option>
									<option Value="5">ONLINE</option>
							</select></td>
							<td><input class="control-info styled" type="checkbox"
								id="wardCheck" onclick="disableOptions(this.id,'wardNo')">&nbsp;&nbsp;Ward
								No.</td>
							<td>
							<select class="form-control select" id="wardNo" name="wardNo"
								disabled="disabled">
									<option value="0" data-icon="icon-git-branch">Select</option>

									<c:forEach items="${wardNoList}" var="ward">
										<option value="${ward.wardNo}">${ward.wardNo}</option>
									</c:forEach>
							</select>

							</td>
						</tr>
						<tr>
							<td colspan="2" align="center">
								<button class="btn bg-teal-600"
									onclick="return searchCashDetails();">
									Search&nbsp;&nbsp;<i class="glyphicon glyphicon-search"></i>
								</button>
							</td>
							<td colspan="2" align="center">
								<button class="btn bg-teal-600" onclick="clearForm();">
									Clear&nbsp;&nbsp;<i class="glyphicon glyphicon-remove"></i>
								</button>
							</td>
						</tr>
					</table>
				</td>
			<tr>
		</table>
	</div>
</div>



<div class="panel panel-flat">
	<div class="panel-body" hidden="true" id="cashDetailsDiv"
		style="overflow: scroll;">
		<fieldset class="content-group">
			<!-- <legend class="text-bold">Customer Approval</legend> -->
			<div class="table-responsive">
				<div class="col-md-12">
					<table class="table datatable-basic table-bordered"
						id="cashDetailsTab">
						<thead class="bg-blue">
							<tr>
								<th>SI no.</th>
								<th>ConnectionNo</th>
								<th>ReceiptNo.</th>
								<th>ReceiptDate<br>(ENG)
								</th>
								<th>ChequeNo.</th>
								<th>PayMode</th>
								<th>Amount</th>
								<th>BankName</th>
								<th>Towards</th>
								<th>WardNo.</th>
							</tr>
						</thead>

						<tbody id="paymentsData">
							<tr>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</fieldset>
	</div>
</div>

<div id="loading" hidden="true" style="text-align: center;">
                   	<h3 id="loadingText">Loading..... Please wait. </h3> 
 	<img alt="image" src="./resources/images/loading.gif" style="width:3%;height: 3%;">
</div>

<!-- 		
				</div> -->
				
				
<style>

.datatable-header, .datatable-footer {
    padding: 0px 0px 0 0px;
}

.dataTables_filter {
    display: block;
    float: left;
    margin: 0 0 20px 20px;
    position: relative;
}
.dataTables_length > label {
    margin-bottom: 0;
    display: none;
}

</style>
