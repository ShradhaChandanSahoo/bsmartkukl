<%@include file="/WEB-INF/decorators/taglibs.jsp"%>

<script type="text/javascript">
	function finalSubmit() {
		swal({
			title : "Added Successfully",
			confirmButtonColor : "#2196F3"
		});
	}
</script>

<c:if test="${not empty msg}">
	<script>
		var msg = "${msg}";
		swal({
			title : msg,
			text : "",
			confirmButtonColor : "#2196F3",
		});
	</script>
</c:if>


<div class="panel panel-flat">


	<div class="panel-body">
		<form class="form-horizontal" action="#" method="POST">
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
			<fieldset class="content-group">
				<legend class="text-bold">Reading Entry</legend>

				<div class="row">

					<div class="col-md-3">
						<div class="form-group">
							<label>Ward Number</label><select data-placeholder="Select"
								class="select" id="wardNumber" name="wardNo"
								onchange="getLedgerData()" required="required">
								<option value="" data-icon="icon-git-branch">Select</option>

								<c:forEach items="${wardNoList}" var="ward">
									<option value="${ward.wardNo}">${ward.wardNo}</option>
								</c:forEach>

							</select>
						</div>
					</div>

					<div class="col-md-3">
						<div class="form-group">
							<label>Reading Month</label><select data-placeholder="Select"
								class="select" id="reading_month" name="reading_month"
								required="required" onchange="getLedgerData()">
								<option value="" data-icon="icon-git-branch">Select</option>
								<option value="${latestNepaliMonth}" data-icon="icon-git-branch">${monthDesc}</option>

							</select>
						</div>
					</div>

					<div class="col-md-2">
						<div class="form-group">
							<label>Reading Day</label><select data-placeholder="Select"
								class="select" id="reading_day" name="reading_day"
								onchange="getLedgerData()" required="required">
								<option value="" data-icon="icon-git-branch">Select</option>
								<c:forEach items="${readingDayList}" var="rday">
									<%-- <c:if test="${rday.readingDay} < 10 ">
								<option value="${rday.readingDay}">0${rday.readingDay}</option></c:if> --%>

									<c:choose>
										<c:when test="${rday.readingDay < 10}">
											<option value="${rday.readingDay}">0${rday.readingDay}</option>
										</c:when>

										<c:otherwise>
											<option value="${rday.readingDay}">${rday.readingDay}</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>


							</select>
						</div>
					</div>


					<div class="col-md-2" id="mrIdDiv">
						<div class="form-group">
							<label>Meter Reader</label><select data-placeholder="Select"
								class="select" id="mr_id" name="mr_id"
								onchange="setMeterReader(this.value)">
								<option value="" data-icon="icon-git-branch">Select</option>

								<c:forEach items="${meterReaderList}" var="mr">
									<option value="${mr.id}">${mr.mrName}-${mr.mrCode}</option>
								</c:forEach>

							</select>
						</div>
					</div>
					<div class="col-md-2" id="mrIdDivNew" hidden="true">
						<div class="form-group">
							<label>Meter Reader</label><select data-placeholder="Select"
								class="select" id="mr_id1" name="mr_id1"
								onchange="setMeterReader(this.value)">
								<option value="" data-icon="icon-git-branch">Select</option>
							</select>
						</div>
					</div>

					<div class="col-md-2">
						<div class="form-group">
							<label>Pipe Size</label><select data-placeholder="Select"
								class="select" id="pipe_size" name="pipe_size"
								onchange="getLedgerData()" required="required">
								<option value="" data-icon="icon-git-branch">Select</option>
								<option value="0.5" data-icon="icon-git-branch">SA</option>
								<option value="0.75" data-icon="icon-git-branch">THA</option>

							</select>
						</div>
					</div>



				</div>

				<div class="row">


					<div class="col-md-3">
						<div class="form-group">
							<label>Total Connections Master</label> <input type="text"
								id="connectionsMaster" class="form-control"
								placeholder="Total Connections Master...">
						</div>
					</div>

					<div class="col-md-3">
						<div class="form-group">
							<label>Total Connections Ledger</label> <input type="text"
								id="connectionsLedger" class="form-control"
								placeholder="Total Connections Ledger...">
						</div>
					</div>

					<div class="col-md-3">
						<div class="form-group">
							<label>Billed</label> <input type="text" class="form-control"
								id="billed" placeholder="Billed">
						</div>
					</div>

					<div class="col-md-3">
						<div class="form-group">
							<label>UnBilled</label> <input type="text" class="form-control"
								id="unBilled" placeholder="Unbilled">
						</div>
					</div>
				</div>





			</fieldset>
			<div class="text-right">

				<button type="submit" id="generateLedButton"
					onclick="return generateLedger();" class="btn bg-teal btn-ladda"
					data-style="expand-right" data-spinner-size="20">
					<span class="ladda-label">Generate Ledger</span>
				</button>

				<button type="button" class="btn bg-teal btn-ladda"
					data-style="expand-right" data-spinner-size="20"
					onclick="showUnbilled();">
					<span class="ladda-label">Submit</span>
				</button>
			</div>
		</form>

	</div>
</div>

<div id="loading" hidden="true" style="text-align: center;">
	<h3 id="loadingText">Loading..... Please wait.</h3>
	<img alt="image" src="./resources/images/loading.gif"
		style="width: 3%; height: 3%;">
</div>

<div class="panel panel-flat" id="unbilledid" style="display: none;">

	<form class="form-horizontal" action="./insertReadingEntryNew1"
		method="POST" id="billApproveEntity">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
		<div class="table-responsive">

			<input type="text" name="readingEntryCount" id="readingEntryCount"
				hidden="true"> <input type="text" name="buttontype"
				id="buttontype" hidden="true"> <input type="text"
				name="wardNo" id="wardNoId" hidden="true"> <input
				type="text" name="mr_id" id="mr_idnew" hidden="true"> <input
				type="text" name="dayofread" id="dayofread" hidden="true"> <input
				type="text" name="nepmonthyear" id="nepmonthyear" hidden="true">
			<input type="text" name="spipe_size" id="spipe_size" hidden="true">

			<table id="tableForm" class="table table-bordered">
				<thead>
					<tr class="bg-primary">
						<th>Connection&nbsp;No.</th>
						<th>AreaNo.&nbsp;&nbsp;&nbsp;</th>
						<th>LR(KL)</th>
						<th>CR(KL)</th>
						<th>Unit(KL)</th>
						<th>MCStatus</th>
						<th>Arrears</th>
						<th>WC&nbsp;Amt</th>
						<th>SWC</th>
						<th>MR</th>
						<th>TOTAL</th>
						<th>Net Amount</th>
						<th style="display: none;"></th>
						<th style="display: none;"></th>
						<th style="display: none;"></th>
						<th style="display: none;"></th>
						<th style="display: none;"></th>
					</tr>

				</thead>

				<tbody id="tbodyId">




				</tbody>



			</table>


		</div>


		<div class="text-right">
			<button type="submit" id="updateButton"
				onclick="return typeOfButton(1);" class="btn bg-teal btn-ladda"
				data-style="expand-right" data-spinner-size="20">
				<span class="ladda-label">Generate</span>
			</button>

			<!-- <button type="submit" id="generateButton" style="display: none;" onclick="typeOfButton(2);"
						class="btn bg-teal btn-ladda btn-ladda-progress"
						data-style="expand-right" data-spinner-size="20">
						<span class="ladda-label">Generate</span>
					</button> -->


		</div>

	</form>

</div>


<div id="modal_default" class="modal fade">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h5 class="modal-title">
					<font color="red"><span id="connectionidspan"></span></font>
				</h5>
			</div>

			<div class="modal-body">

				<table class="table datatable-basic table-bordered"
					id="popupPayment">
					<thead>
						<tr class="bg-primary">
							<!-- <th>CON No.</th> -->
							<th></th>

							<th>Bill No</th>
							<th>MonthYear</th>
							<th>Water Charge</th>
							<th>SW Charge</th>
							<th>MR</th>
							<th>Arrears</th>
							<th>NET</th>
							<th>CR(KL)</th>
							<th>LR(KL)</th>
							<th>MC Status</th>

						</tr>
					</thead>
					<tbody id="viewPayHistotytbody">

					</tbody>
				</table>


			</div>

			<div class="modal-footer"></div>
		</div>
	</div>
</div>


<c:if test="${not empty wardNoR}">

	<script>
		var wardNoR = "${wardNoR}";
		var dayofreadR = "${dayofreadR}";
		var nepmonthyearR = "${nepmonthyearR}";

		var spipe_size = "${spipe_size}";

		if (wardNoR != null && wardNoR != "") {

			$('#wardNumber').val(wardNoR).prop('selected', true);
			$('#reading_month').val(nepmonthyearR).trigger("change");
			$('#reading_day').val(dayofreadR).trigger("change");
			$('#pipe_size').val(spipe_size).trigger("change");
		}
	</script>

	<c:remove var="wardNoR" scope="session" />
	<c:remove var="dayofreadR" scope="session" />
	<c:remove var="nepmonthyearR" scope="session" />
	<c:remove var="spipe_size" scope="session" />
</c:if>

<div hidden="true" id="loading1"
	style="position: absolute; top: 50%; left: 50%; width: 100px; margin-top: -58px; margin-left: -100px; height: 100px;"
	align="center">
	<img id="loading-image" src="./resources/images/loaderP.gif"
		alt="Loading..." />
</div>

<script>
	function setMeterReader(mrid) {

		$('#mr_idnew').val(mrid);

	}
	function typeOfButton(value) {

		if (value == 1) {
			$('#buttontype').val("Update");

			if (confirm("Are you sure to Generate the Bill?")) {
			} else {
				return false;
			}

		} else if (value == 2) {
			$('#buttontype').val("Generate");
		}
	}

	$(document).ready(function() {
		$('#billingscreens').show();
		$('#billingManagement').addClass('active');

		var activeMod = "${activeModulesNew}";
		var module = "Monthly Billing";
		var check = activeMod.indexOf(module) > -1;

		if (check) {
		} else {
			window.location.href = "./accessDenied";
		}

	});

	function getLedgerData() {

		var wardNo = $('#wardNumber').val();
		var reading_day = $('#reading_day').val();
		var pipe_size = $('#pipe_size').val();
		var reading_month = $('#reading_month').val();

		if (wardNo != null && wardNo != "" && reading_day != null
				&& reading_day != "") {
			$
					.ajax({
						type : "GET",
						url : "./billing/getMeterReader/" + wardNo + "/"
								+ reading_day,
						dataType : "JSON",
						async : false,

						success : function(response) {
							//alert(response);
							if (response != null) {
								var mr = response.meterReaderEntity.mrName
										+ "-"
										+ response.meterReaderEntity.mrCode;
								//alert(mr);
								//alert(response.mrid);
								$("#mrIdDiv").hide();
								$("#mrIdDivNew").show();
								$('#mr_id1').find('option').remove().end()
										.append(
												'<option value="'+ response.mrid +'">'
														+ mr + '</option>');
								$("#mr_id1").val(response.mrid).trigger(
										"change");
								$('#mr_idnew').val($("#mr_id1").val());
								$('#mr_id').prop('selectedIndex', -1);
							} else {
								$("#mrIdDiv").show();
								$("#mrIdDivNew").hide();
								$("#mr_id").val("").trigger("change");
								$('#mr_id1')
										.find('option')
										.remove()
										.end()
										.append(
												'<option value="" data-icon="icon-git-branch">Select</option>');
								$('#mr_id1').val('').trigger("change");
								$('#mr_idnew').val("");
							}
						},
						error : function(xhr) {
							$("#mrIdDiv").show();
							$("#mrIdDivNew").hide();
							$("#mr_id").val('').trigger("change");
							$('#mr_id1')
									.find('option')
									.remove()
									.end()
									.append(
											'<option value="" data-icon="icon-git-branch">Select</option>');
							$('#mr_id1').val('').trigger("change");
							$('#mr_idnew').val("");
						},
					});
		}

		if (wardNo == null || wardNo == "") {
		} else if (reading_day == null || reading_day == "") {
		} else if (pipe_size == null || pipe_size == "") {
		} else if (reading_month == null || reading_month == "") {
		} else {

			$('#dayofread').val(reading_day);
			var pipesize = pipe_size;
			var category = "All";
			$.ajax({
				type : "GET",
				url : "./billing/readingEntry/" + wardNo + "/" + reading_day
						+ "/" + pipe_size + "/" + reading_month + "/"
						+ category,
				dataType : "JSON",
				data : {

					pipesize : pipesize,

				},

				async : false,

				success : function(response) {

					$.each(response, function(index, data) {

						$('#connectionsMaster').val(data.masterCount);
						$('#connectionsLedger').val(data.ledgerCount);
						$('#billed').val(data.billedLedgerCount);
						$('#unBilled').val(data.unbilledLedgerCount);

					});

				}
			});

		}
	}

	function generateLedger() {

		var wardNo = $("#wardNumber").val();
		var reading_day = $('#reading_day').val();
		var pipe_size = $('#pipe_size').val();
		var nepmonthyear = $('#reading_month').val();

		if (wardNo == null || wardNo == "") {
			alert("Please Select Ward Number");
			return false;
		} else if (reading_day == null || reading_day == "") {
			alert("Please Select Reading Day");
			return false;
		} else if (pipe_size == null || pipe_size == "") {
			alert("Please Select Pipe Size");
			return false;
		} else if (nepmonthyear == null || nepmonthyear == "") {
			alert("Please Select Month Year");
			return false;
		} else {

			if (confirm("Are you sure to Generate the Ledger?")) {
				$('#generateLedButton').hide();

				$("#loading").show();
				$.ajax({
					type : "GET",
					url : "./billing/generateLedgerByWardRdngPipeSize",
					dataType : "text",
					data : {
						wardNo : wardNo,
						reading_day : reading_day,
						pipe_size : pipe_size,
						nepmonthyear : nepmonthyear
					},
					async : false,
					cache : false,

					success : function(response) {

						swal({
							title : response,
							text : "",
							confirmButtonColor : "#2196F3",
						});
					}

				});
			} else {
				$('#generateLedButton').show();
				return false;
			}
			$("#loading").hide();
		}
	}

	function showUnbilled() {

		var wardNo = $("#wardNumber").val();
		var reading_day = $('#reading_day').val();
		var pipe_size = $('#pipe_size').val();

		var mr_id = $('#mr_id').val();
		var mr_id1 = $('#mr_id1').val();
		var mrChk = null;
		if (mr_id == null || mr_id == "") {
			if (mr_id1 == null || mr_id1 == "") {
				mrChk = null;
			} else {
				mrChk = mr_id1;
			}
		} else {
			mrChk = mr_id;
		}
		var nepmonthyear = $('#reading_month').val();

		if (wardNo == null || wardNo == "") {
			alert("Please Select Ward Number");
			return false;
		} else if (reading_day == null || reading_day == "") {
			alert("Please Select Reading Day");
			return false;
		} else if (pipe_size == null || pipe_size == "") {
			alert("Please Select Pipe Size");
			return false;
		} else if (mrChk == null || mrChk == "") {
			alert("Please Select Meter Reader");
			return false;
		} else {

			$('#wardNoId').val(wardNo);
			$('#nepmonthyear').val(nepmonthyear);

			var pipesize = pipe_size;
			$('#spipe_size').val(pipesize);
			$('#tbodyId').empty();

			$("#loading").show();
			$
					.ajax({
						type : "GET",
						url : "./billing/getReadingEntryUnbilled/" + wardNo
								+ "/" + reading_day + "/" + pipe_size,
						dataType : "JSON",
						data : {

							pipesize : pipesize,

						},
						async : false,
						cache : false,
						success : function(response) {

							var htmlpage = "";
							var i = 1;

							$
									.each(
											response,
											function(index, data) {

												htmlpage += '<tr>'
														+ '<td title="Click here to see Last Ledger"><a href="#"  data-toggle="modal" data-target="#modal_default" onclick="ledgerHistoryPopUp('
														+ i
														+ ')"><input placeholder="" type="text" style="cursor:pointer;cursor:hand;" class="form-control" id="connection_no'+i+'" name="connection_no'+i+'" value='+data.connection_no+' readonly="true"></a></td>'
														+ '<td><input placeholder="" type="text" class="form-control" id="area_no'+i+'" name="area_no'+i+'" value='+data.area_no+' readonly="true"  title="'+data.area_no+'"></td>';

												/* if(data.previous_reading>0){
												   htmlpage+='<td><input placeholder="" type="text" class="form-control" id="previous_reading'+i+'" name="previous_reading'+i+'" value='+data.previous_reading+' readonly="true" onchange="getTotalConsumption('+data.present_reading+',this.value,'+i+')"></td>';
												}else{
												
												   htmlpage+='<td><input placeholder="" type="text" class="form-control" id="previous_reading'+i+'" name="previous_reading'+i+'" value='+data.previous_reading+' onchange="getTotalConsumption('+data.present_reading+',this.value,'+i+')"></td>';
												   
												} */

												htmlpage += '<td><input placeholder="" type="text" class="form-control" id="previous_reading'
														+ i
														+ '" name="previous_reading'
														+ i
														+ '" value='
														+ data.previous_reading
														+ ' onchange="getTotalConsumption('
														+ data.present_reading
														+ ',this.value,'
														+ i
														+ ')"></td>';

												htmlpage += '<td><input placeholder="" type="text" value='
														+ data.present_reading
														+ ' id="present_reading'
														+ i
														+ '" name="present_reading'
														+ i
														+ '" class="form-control" onchange="getTotalConsumption(this.value,'
														+ data.previous_reading
														+ ','
														+ i
														+ ')"></td>'
														+ '<td><input placeholder="" type="text" value='+data.consumption+' id="consumption'+i+'" name="consumption'+i+'" value="0" class="form-control" readonly="true"></td>'
														+ '<td><select data-placeholder="Select" class="select" name="mc_status'
														+ i
														+ '" id="mc_status'
														+ i
														+ '"  onchange="getNetAmount(this.value,'
														+ data.pipe_size
														+ ','
														+ data.previous_reading
														+ ','
														+ data.present_reading
														+ ','   
														+ i
														+ ','
														+ data.arrears
														+ ')"><option value="" >select</option><c:forEach items="${observationList}" var="tar"><option value="${tar.id}-${tar.observationName}">${tar.observationName}</option></c:forEach></select></td>'
														+ '<td><input placeholder="" type="text" value='
														+ data.arrears
														+ ' id="arrears'
														+ i
														+ '" name="arrears'
														+ i
														+ '" value="0" readonly="true" class="form-control" onchange="getArrears('
														+ data.mc_status
														+ ','
														+ data.pipe_size
														+ ','
														+ data.previous_reading
														+ ','
														+ data.present_reading
														+ ','
														+ i
														+ ',this.value)"></td>'
														+ '<td><input placeholder="" type="text" id="water_charges'+i+'" name="water_charges'+i+'" value="0" readonly="true" class="form-control"></td>'
														+ '<td><input placeholder="" type="text" id="sw_charges'+i+'" name="sw_charges'+i+'" value="0" readonly="true" class="form-control"></td>'
														+ '<td><input placeholder="" type="text" id="mtr_rent'+i+'" name="mtr_rent'+i+'" value="0" readonly="true" class="form-control"></td>'
														+ '<td><input placeholder="" type="text" id="totalamt'+i+'" name="totalamt'+i+'" value="0" readonly="true" class="form-control"></td>'
														+ '<td><input placeholder="" type="text" id="net_amount'+i+'" name="net_amount'+i+'" value="0" readonly="true" class="form-control"></td>'
														+ '<td style="display:none;"><input type="text" id="billid'+i+'" name="billid'+i+'" readonly="true" value='+data.billid+' hidden="true"></td>'
														+ '<td style="display:none;"><input type="text" id="minimum_charges'+i+'" name="minimum_charges'+i+'" readonly="true" value='+data.minimum_charges+'></td>'
														+ '<td style="display:none;"><input type="text" id="additional_charges'+i+'" name="additional_charges'+i+'"  value='+data.additionalCharges+'></td>'
														+ '<td style="display:none;"><input type="text" id="pipe_size'+i+'" name="pipe_size'+i+'" readonly="true" value='+data.pipe_size+'></td>'
														+ '<td style="display:none;"><input type="text" id="excess_charges'+i+'" name="excess_charges'+i+'" readonly="true" value="0"></td>'
														+

														'</tr>';

												i++;
											});

							$('#unbilledid').show();

							i = i - 1;
							$('#readingEntryCount').val(i);

							$('#tbodyId').append(htmlpage);
							//loadSearchFilter1('tableForm',htmlpage,'tbodyId');

						}

					});
		}
		$("#loading").hide();
	}

	function ledgerHistoryPopUp(i) {
		$("#viewPayHistotytbody").empty();

		var connectionNo = $('#connection_no' + i).val();

		var psize = "";
		var nameeng = "";
		var namenep = "";
		var tableData = "";
		$
				.ajax({
					type : "GET",
					url : "./billing/viewBillLedgertHistoryForReading/"
							+ connectionNo,
					async : false,
					dataType : "JSON",
					success : function(response) {
						for (var s = 0, len = response.length; s < len; ++s) {
							var obj = response[s];

							tableData += "<tr>" + "<td>" + (s + 1) + "</td>"
									+ "<td>" + obj.billno + "</td>" + "<td>"
									+ obj.monthyearnep + "</td>" + "<td>"
									+ obj.water_charges + "</td>" + "<td>"
									+ obj.sw_charges + "</td>" + "<td>"
									+ obj.mtr_rent + "</td>" + "<td>"
									+ obj.lastarrears + "</td>" + "<td>"
									+ obj.net_amount + "</td>" + "<td>"
									+ obj.previous_reading + "</td>" + "<td>"
									+ obj.present_reading + "</td>" + "<td>"
									+ obj.obname + "</td>" + "</tr>";

							if (s == 0) {
								nameeng = obj.name_eng;
								namenep = obj.name_nep;
								psize = obj.pipe_size;
							}

						}
						$('#connectionidspan')
								.html(
										"Bill Ledger History &nbsp;&nbsp;Connection No : "
												+ connectionNo
												+ " &nbsp;&nbsp;&nbsp;&nbsp;            Name : "
												+ nameeng
												+ "   &nbsp;&nbsp;/&nbsp;&nbsp;   "
												+ namenep
												+ "    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  Pipe Size(inch):"
												+ psize);
						$('#viewPayHistotytbody').append(tableData);
						loadSearchFilter1('popupPayment', tableData,
								'viewPayHistotytbody');
					}

				});

	}

	function getTotalConsumption(currentread, prevread, i) {

		/* var susername="${authenticated}";
		alert(susername);
		if(susername==null || susername==""){
			alert("Your Session is Timed Out.Please Login and try again!!");
			window.location.href="./login";
		} */

		$
				.ajax({
					type : "GET",
					url : "./checkSession",
					dataType : "text",
					async : false,
					cache : false,
					success : function(response) {

						if (response == "false") {
							alert("Your Session is Timed Out.Please Login and try again!!");
							window.location.href = "./login";
						}
					}
				});

		var currentreading = $('#present_reading' + i).val();
		var prevreading = $('#previous_reading' + i).val();

		if (currentreading == null || currentreading == "") {
			$('#present_reading' + i)
					.val(prevreading == null ? 0 : prevreading);
			$('#consumption' + i).val(0);
			return false;
		}
		if (prevreading == null || prevreading == "") {
			$('#previous_reading' + i).val(0);
			$('#present_reading' + i).val(0);
			$('#consumption' + i).val(0);
			return false;
		}

		var units = (parseFloat(currentreading).toFixed(2))
				- (parseFloat(prevreading).toFixed(2));

		if (units < 0) {
			alert("Present Readig should be greater than or equal to Previous Reading");
			$('#present_reading' + i)
					.val(prevreading == null ? 0 : prevreading);
			$('#consumption' + i).val(0);
			return false;
		}

		$('#consumption' + i).val(units);
		$('#mc_status' + i).val(16 + "-" + "Reading");

		var connectionno = $('#connection_no' + i).val();
		var pipesize = $('#pipe_size' + i).val();
		var arrears = $('#arrears' + i).val();

		var mcstat = $('#mc_status' + i).val();
		var resp = mcstat.split("-");
		var mcstatus1 = resp[0];
		var mcstatus2 = resp[1];

		/*  */$
				.ajax({
					type : "GET",
					url : "./billing/calculateNetAmount2/" + connectionno + "/"
							+ pipesize + "/" + units + "/" + mcstatus1 + "/"
							+ mcstatus2,
					dataType : "JSON",
					async : false,
					success : function(response) {

						$
								.each(
										response,
										function(index, data) {

											$('#water_charges' + i).val(
													data.waterCharges);
											$('#sw_charges' + i).val(
													data.sewageCharges);
											//$('#net_amount'+i).val((parseFloat(data.netAmount)+parseFloat(arrears)).toFixed(2));
											$('#arrears' + i).val(
													parseFloat(data.arrears)
															.toFixed(2));
											$('#net_amount' + i).val(
													parseFloat(data.netAmount)
															.toFixed(2));
											$('#mtr_rent' + i).val(
													data.meterRent);
											$('#minimum_charges' + i).val(
													data.minimum_charges);
											$('#additional_charges' + i).val(
													data.additionalCharges);
											$('#totalamt' + i).val(
													data.totalamt);
											$('#excess_charges' + i).val(
													data.others);

											var result = "No bill";
											var statuscheck = result
													.indexOf(data.disconnectedstage) > -1;
											if (statuscheck) {
												alert("No bill for the current month for the Selected Meter Status");
											}

										});

					}
				});

	}

	function getArrears(mcstatus, Pipesize, lr, cr, i, arrears) {

		var pipesize = Pipesize;
		var water_charges = parseFloat($('#water_charges' + i).val());
		var sw_charges = parseFloat($('#sw_charges' + i).val());
		var mtr_rent = parseFloat($('#mtr_rent' + i).val());
		var arrears = parseFloat($('#arrears' + i).val());
		$('#net_amount' + i).val(
				parseFloat((water_charges + sw_charges + mtr_rent + arrears))
						.toFixed(2));

	}

	function getNetAmount(mcstatus, Pipesize, lr, cr, i, arrears) {

		$
				.ajax({
					type : "GET",
					url : "./checkSession",
					dataType : "text",
					async : false,
					cache : false,
					success : function(response) {

						if (response == "false") {
							alert("Your Session is Timed Out.Please Login and try again!!");
							window.location.href = "./login";
						}
					}
				});

		var pipesize = Pipesize;
		var lastread = $('#previous_reading' + i).val();
		var currread = $('#present_reading' + i).val();

		var mcstat = $('#mc_status' + i).val();
		var resp = mcstat.split("-");
		var mcstatus1 = resp[0];
		var mcstatus2 = resp[1];

		if (mcstatus1 != '16'  ) {
			
			if(mcstatus1 !='56'){
			$('#present_reading' + i).val(lastread);
			$('#consumption' + i).val(0);
			currread = lastread;
			}
			
		}
		
		

		var connectionno = $('#connection_no' + i).val();

		var units = parseFloat(currread == null ? 0 : currread).toFixed(2)
				- parseFloat(lastread == null ? 0 : lastread).toFixed(2);
		if (units < 0) {
			alert("Present Readig should be greater than Previous Reading");
			return false;
		}

		$
				.ajax({
					type : "GET",
					url : "./billing/calculateNetAmount1/" + connectionno + "/"
							+ pipesize + "/" + units + "/" + mcstatus1 + "/"
							+ mcstatus2,
					dataType : "JSON",

					async : false,

					success : function(response) {

						$
								.each(
										response,
										function(index, data) {

											$('#water_charges' + i).val(
													data.waterCharges);
											$('#sw_charges' + i).val(
													data.sewageCharges);
											//$('#net_amount'+i).val((parseFloat(data.netAmount)+parseFloat(arrears)).toFixed(2));
											$('#arrears' + i).val(
													parseFloat(data.arrears)
															.toFixed(2));
											$('#net_amount' + i).val(
													parseFloat(data.netAmount)
															.toFixed(2));
											$('#mtr_rent' + i).val(
													data.meterRent);
											$('#minimum_charges' + i).val(
													data.minimum_charges);
											$('#additional_charges' + i).val(
													data.additionalCharges);
											$('#totalamt' + i).val(
													data.totalamt);
											$('#excess_charges' + i).val(
													data.others);

											var result = "No bill";
											var statuscheck = result
													.indexOf(data.disconnectedstage) > -1;
											if (statuscheck) {
												alert("No bill for the current month for the Selected Meter Status");
											}

										});

					}
				});

	}

	/* function showUnbilledAfterUpdate(){
	
	 var wardNo=$("#wardNumber").val();
	
	 var htmlpage="";
	 $.ajax({
	 type: "GET",
	 url: "./billing/getReadingEntryUnbilled/"+wardNo,
	 dataType: "JSON",
	 async       : false,
	 success: function(response){
	 var i=1;
	
	 $('#unbilledid').show();
	 $.each(response, function(index, data){
	
	 var a="undefined";
	 var result = a.indexOf(data.mc_status) > -1;
	
	 if(!result){
	
	 htmlpage+='<tr><td><input placeholder="" type="text" class="form-control" id="wardNo'+i+'" name="wardNo'+i+'" value='+data.ward_no+' readonly="true"></td><td><input placeholder="" type="text" class="form-control" id="connection_no'+i+'" name="connection_no'+i+'" value='+data.connection_no+' readonly="true"></td><td><input placeholder="" type="text" class="form-control" id="previous_reading'+i+'" name="previous_reading'+i+'" value='+data.previous_reading+' readonly="true"></td><td><input placeholder="" type="text" value='+data.mc_status+' id="mc_status'+i+'" name="mc_status'+i+'" class="form-control" readonly="true"></td><td><input placeholder="" type="text" value='+data.present_reading+' id="present_reading'+i+'" name="present_reading'+i+'" class="form-control" readonly="true"></td><td><input type="text" class="form-control" id="readdatenep'+i+'" name="readdatenep'+i+'" value='+data.readdatenep+' readonly="true"></td><td><input type="text" class="form-control" id="duedatenep'+i+'" name="duedatenep'+i+'" value='+data.duedatenep+' readonly="true"></td><td><input type="text" id="billid'+i+'" name="billid'+i+'" value='+data.billid+' hidden="true"></td></tr>';
	
	 i++;
	 }
	
	 });
	
	
	
	 i=i-1;
	 $('#readingEntryCount').val(i);
	 $('#tbodyId').append(htmlpage);
	 loadSearchFilter1('tableForm',htmlpage,'tbodyId');
	
	 }
	 });
	 } */

	function loadSearchFilter1(param, tableData, temp) {
		$('#' + param).dataTable().fnClearTable();
		$('#' + param).dataTable().fnDestroy();
		$('#' + temp).html(tableData);
		$('#' + param).dataTable();

	}
</script>
<style>
.form-horizontal .form-group {
	margin-left: 0px !important;
	margin-right: 0px !important;
}

select {
	width: 100%;
	border: 1px solid #DDD;
	border-radius: 3px;
	height: 36px;
}

legend {
	text-transform: none;
	font-size: 16px;
	border-color: #F01818;
}

.datatable-header,.datatable-footer {
	padding: 0px 0px 0 0px;
}

.datatable-header {
	display: none;
}

.dataTables_filter {
	display: none;
	float: left;
	margin: 0 0 20px 20px;
	position: relative;
}

.dataTables_length>label {
	margin-bottom: 0;
	display: none;
}

.table>thead>tr>th,.table>tbody>tr>th,.table>tfoot>tr>th,.table>thead>tr>td,.table>tbody>tr>td,.table>tfoot>tr>td
	{
	padding: 5px 5px;
	line-height: 1.5384616;
}

.dtr-inline.collapsed tbody tr td:first-child::before,.dtr-inline.collapsed tbody tr th:first-child::before,.dtr-column tbody tr td.control::before,.dtr-column tbody tr th.control::before
	{
	content: unset;
	font-family: 'icomoon';
	display: inline-block;
	font-size: 16px;
	width: 16px;
	line-height: 1;
	position: relative;
	top: -1px;
	vertical-align: middle;
}

.modal.fade .modal-dialog {
	width: 1260px;
}
</style>