
<%@include file="/WEB-INF/decorators/taglibs.jsp"%>

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
		<form action="#" role="form" method="POST" id="adjustmentVoucherEntity">

			<fieldset class="content-group">

				<input type="text" id="con_type" name="con_type" hidden="true">
				<input type="text" id="denoted_by" name="denoted_by" hidden="true">
				<input type="text" id="ward_no" name="ward_no" hidden="true">
				<input type="text" id="sitecode" name="sitecode" hidden="true">
				
				<legend class="text-bold"
					style="margin: auto; text-align: center; font-size: 18px;">Adjustments/Arrears Correction Request</legend>
				<br>

				<div class="row">
					<div class="col-md-2">
						<div class="form-group">
							<label>Connection No &nbsp;<font color="red">*</font></label> <input
								type="text" class="form-control" name="connection_no" id="connection_no"
								placeholder="Connection No..."
								onchange="getConnectionDetails(this.value)" onkeyup="convertToUpperCase()"></input>
						</div>
					</div>

					<div class="col-md-3">
						<div class="form-group">
							<label>Name</label> <input type="text" class="form-control"
								name="name_eng" id="name_eng" readonly="readonly"
								placeholder="Name">
						</div>
					</div>

					<div class="col-md-3">
						<div class="form-group">
							<label>Connection Category</label> <input type="text"
								class="form-control" name="con_category" id="con_category"
								readonly="readonly" placeholder="Connection Category...">
						</div>
					</div>


					<div class="col-md-2">
						<div class="form-group">
							<label>Area No</label> <input type="text" class="form-control"
								name="area_no" id="area_no" readonly="readonly"
								placeholder="Area No...">
						</div>
					</div>
					<!-- <div class="col-md-2">
						<div class="form-group">
							<label>Pipe Diameter(inch)</label> <input type="text"
								class="form-control" name="pipe_size" id="pipe_size"
								readonly="readonly" placeholder="Pipe Diameter...">
						</div>
					</div> -->
					<div class="col-md-2">
						<div class="form-group">
							<label>Board Balance&nbsp;</label> <input type="text"
								class="form-control" name="board_bal" id="board_bal"
								readonly="readonly" placeholder="Board Balance"></input>
						</div>
					</div>
				</div>

				<!-- <div class="row">
					<div class="col-md-2">
						<div class="form-group">
							<label>Board Balance&nbsp;</label> <input type="text"
								class="form-control" name="board_bal" id="board_bal"
								readonly="readonly" placeholder="Board Balance"></input>
						</div>
					</div>


				</div> -->
				<div class="row">
					<div class="col-md-2">
						<div class="form-group">
							<label>Arrears&nbsp;</label> <input
								type="text" class="form-control" name="arrears"
								id="arrears" readonly="readonly" placeholder="Arrears"></input>
						</div>
					</div>
					<div class="col-md-2">
						<div class="form-group">
							<label>Water Charge&nbsp;</label> <input
								type="text" id="water_charge" name="water_charge"
								class="form-control" placeholder="Water Charge" readonly="readonly">
						</div>
					</div>

					<div class="col-md-2">
						<div class="form-group">
							<label>SW Charge&nbsp;</label> <input
								type="text" class="form-control" name="sw_charge"
								id="sw_charge" path="SW Charge"
								placeholder="Adjustment Amount" readonly="readonly"></input>
						</div>
					</div>

					<div class="col-md-2">
						<div class="form-group">
							<label>Meter Rent&nbsp;</label> <input
								type="text" class="form-control" name="mtr_rent"
								id="mtr_rent" placeholder="Meter Rent" readonly="readonly"></input>
						</div>
					</div>

					<div class="col-md-2">
						<div class="form-group">
							<label>Net Amount&nbsp;</label> <input
								type="text" class="form-control" name="net_amt"
								id="net_amt" placeholder="Net Amount" readonly="readonly"></input>
						</div>
					</div>
					<div class="col-md-2">
						<div class="form-group">
							<label>MonthYear Nepali</label> <input
								type="text" class="form-control" name="monthyear"
								id="monthyear" placeholder="MonthYear" readonly="readonly"></input>
						</div>
					</div>
				</div>

				<div class="row">
					<div class="col-md-2" id="adjTowards_div">
						<label>Adjustment Towards&nbsp;<font color="red">*</font></label> <select
							data-placeholder="Select" class="select" id="adjTowards"
							name="adjTowards" onchange="changeLabel();">
							<option value="Correction">Arrears Correction</option>
							<option value="Adjustment">Arrears Adjustment</option>
							<c:if test = "${branchcode == '1112' || branchcode == '1116' || branchcode == '1114' || branchcode == '1119' || branchcode == '1118' || branchcode == '1111' || branchcode == '1117' || branchcode == '2222'}"> 
							<option value="Board">Board Adjustment</option>
						    <option value="BRDCR">Board Correction</option>
							</c:if>
							
						</select>
					</div>
					
					<div class="col-md-2" id="board_div" hidden="true">
						<label>Board Adj/Corr Amount&nbsp;<font color="red">*</font></label>
						<input type="text" name="board_adj_amount" id="board_adj_amount"
							class="form-control"
							placeholder="Board Adjustment" onkeyup="checkNumeric();"></input>
					</div>
					
					<div class="col-md-2" id="arrear_div">
						<label>Arrears Adj/Corr Amt&nbsp;<font color="red">*</font></label>
						<input type="text" name="bill_adj_amount" id="bill_adj_amount"
							 class="form-control"
							placeholder="Arrears Correction" onkeyup="checkNumeric();"></input>
					</div>

					<div class="col-md-2" id="penalty_adj_div">
						<label>Penalty Adj/Corr Amt&nbsp;<font color="red">*</font></label>
						<input type="text" name="penalty_adj_amount" id="penalty_adj_amount"
							 class="form-control"
							placeholder="Penalty Correction" onkeyup="checkNumeric();"></input>
					</div>
					<div class="col-md-2" id="rebate_adj_div">
						<label>Rebate Corr Amt&nbsp;<font color="red">*</font></label>
						<input type="text" name="rebate_adj_amount" id="rebate_adj_amount"
							 class="form-control"
							placeholder="Rebate Correction" onkeyup="checkNumeric();"></input>
					</div>
					
					<div class="col-md-2" id="board_penalty_adj_div" hidden="true">
						<label>Board Penalty Adj Amt&nbsp;<font color="red">*</font></label>
						<input type="text" name="board_penalty_adj" id="board_penalty_adj"
							 class="form-control" value="0"
							placeholder="Penalty Adjustment" onkeyup="checkNumeric();"></input>
					</div>
					
					<div class="col-md-2" id="board_penalty_adj_div_brdcr" hidden="true">
						<label>Board Penalty Adj Amt&nbsp;<font color="">*</font></label>
						<input type="text" name="board_penalty_adj" id="board_penalty_adj"
							 class="form-control" value="0"
							placeholder="Penalty Adjustment" onkeyup="checkNumeric();" readonly="readonly"></input>
					</div>
					
					<div class="col-md-2" id="penaltyid_div">
						<label>Rebate &nbsp;</label>
						<input type="text" name="rebate" id="rebate"
							 class="form-control"
							placeholder="Rebate" readonly="readonly"></input>
					</div>
					<div class="col-md-2" id="penaltyid_div">
						<label>Penalty &nbsp;</label>
						<input type="text" name="penaltyid" id="penaltyid"
							 class="form-control"
							placeholder="Penalty" readonly="readonly"></input>
					</div>
					
					<div class="col-md-2" id="board_penalty_div" hidden="true">
						<label>Board Penalty &nbsp;</label>
						<input type="text" name="board_penalty" id="board_penalty"
							 class="form-control"
							placeholder="Penalty" readonly="readonly"></input>
					</div>
					


				</div>
				<br>
				<div class="row">
					<div class="col-md-12">
						<div class="form-group">
							<label>Remarks&nbsp;<font color="red">*</font></label>
							<textarea placeholder="Enter your Remarks here in English"
								class="form-control" cols="1" rows="1" name="remarks"
								id="remarks"></textarea>
						</div>
					</div>

				</div>

			</fieldset>

			<div class="text-center">
				<button type="button" class="btn bg-teal btn-ladda"  data-style="expand-right" data-spinner-size="20"
				data-toggle="modal" data-target="#modal_default1"
				id="onshow_callback" onclick='ledgerHistoryPopUp()'>View Ledger</button>
											
				<button type="button" class="btn bg-teal btn-ladda"
					data-style="expand-right" data-spinner-size="20"
					onclick="clearButton();">
					<span class="ladda-label">Clear</span>
				</button>
				<button type="submit" class="btn bg-teal btn-ladda" id="arrcorrid"
					data-style="expand-right" data-spinner-size="20"
					onclick="return checkValid();">
					<span class="ladda-label">Submit</span>
				</button>
			</div>

		</form>
	</div>
</div>




<div class="panel panel-flat">
	<div class="panel-body" style="overflow: scroll;">



		<fieldset class="content-group">
			<legend class="text-bold">
				Get Adjustment/Correction List
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<button type="button" class="btn btn-danger btn-ladda"
					data-style="expand-right" data-spinner-size="20"
					onclick="return getPendingList();">
					<span class="ladda-label">Get List</span>
				</button>

			</legend>

			<div class="table-responsive">
			<div class="col-md-12">
				<table class="table datatable-basic table-bordered" id="adjustTable">
					<thead>
						<tr>
							<th>Sl&nbsp;No</th>
							<th>Con&nbsp;No</th>
							<th style="min-width: 180px;">Name</th>
							<th>AreaNo</th>
							<th>Arrear Adj Amt</th>
							<th>Penalty Adj Amt</th>
							<th>Adj Towards</th>
							<th>MonthYear</th>
							<th>Submit By</th>
							<th>Submit Date</th>
							<th>Trans Approved By</th>
							<th>Trans Approved Date</th>
							<th>Approved By</th>
							<th>Approved Date</th>
							<th style="min-width: 180px;">Status</th>
							<th style="min-width: 200px;">Remarks</th>

						</tr>
					</thead>
					<tbody id="tableBodyId">


					</tbody>
				</table>
				</div>
			</div>
		</fieldset>



	</div>

</div>

<div id="modal_default1" class="modal fade">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h5 class="modal-title">Bill Ledger History</h5>
			</div>

			<div class="modal-body">

				<table class="table datatable-basic table-bordered"
					id="popupPayment1">
					<thead>
						<tr>
							<th></th>
							<th>MY</th>
							<th>CR</th>
							<th>LR</th>
							<th>U</th>
							<th>OB</th>
							<th>WC</th>
							<th>SC</th>
							<th>MR</th>
							<th>NT</th>
							<th style="color: red;">PE</th>
							<th style="color: green;">RB</th>
							<th>Badj</th>
							<th>Padj</th>
							<th>Acorr</th>
							<th>Pcorr</th>
							<th>paid</th>
							<th>CB</th>
							<th>RNo</th>
							<th>RDate</th>
							<th>BillNo</th>

						</tr>
					</thead>
					<tbody id="viewPayHistotytbody1">

					</tbody>
				</table>


			</div>

			<div class="modal-footer"></div>
		</div>
	</div>
</div>

<div id="loading" hidden="true" style="text-align: center;">
	<h3 id="loadingText">Loading..... Please wait.</h3>
	<img alt="image" src="./resources/images/loading.gif"
		style="width: 3%; height: 3%;">
</div>

<script>
	$(document).ready(function() {

		$('#arrearsCorrectionScreen').show();
		$('#arrearPenaltyCorrection').addClass('active');

		var activeMod = "${activeModulesNew}";
		var module = "Arrears Correction";
		var check = activeMod.indexOf(module) > -1;

		if (check) {
		} else {
			window.location.href = "./accessDenied";
		}
      if("${designation}" == "Income Inspection Chief"){
			
			$("#arrcorrid").hide();
		}
		
	});

	function getPendingList() {

		$("#loading").show();
		$
				.ajax({
					type : "GET",
					url : "./getAdjCorrList",
					dataType : "json",
					cache : false,
					async : false,
					success : function(response) {
						if (response.length == 0) {
							$("#tableBodyId").empty();
							swal({
								title : "No Data Found.",
								text : "",
								confirmButtonColor : "#2196F3",
							});
							$("#loading").hide();
							return false;
						}

						var ConnectionData = "";

						var count;
						for (var i = 0; i < response.length; i++) {
							data = response[i];
							count = i + 1;
							ConnectionData += "<tr>" + "<td>" + count + "</td>";
							if(data.connection_no == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.connection_no+"</td>"; }
					  		if(data.name_eng == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.name_eng+"</td>"; }
					  		if(data.area_no == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.area_no+"</td>";}
					  		if(data.bill_adj_amount == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.bill_adj_amount+"</td>";}
					  		if(data.penalty_adj_amount == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.penalty_adj_amount+"</td>";}
					  		if(data.adj_type == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.adj_type+"</td>";}
					  		if(data.to_mon_year == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.to_mon_year+"</td>";}
					  		if(data.submit_by == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.submit_by+"</td>";}
					  		if(data.submit_date == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.submit_date+"</td>";}
					  		if(data.trans_approve == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.trans_approve+"</td>";}
					  		if(data.trans_approve_date == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.trans_approve_date+"</td>";}
					  		if(data.approve_by == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.approve_by+"</td>";}
					  		if(data.approve_date == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.approve_date+"</td>";}
					  		if(data.approve_status == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.approve_status+"</td>";}
					  		if(data.remarks == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.remarks+"</td>";}
					  		
					  		ConnectionData+="</tr>";	
						}
						$("#tableBodyId").html(ConnectionData);
						loadSearchFilter1('adjustTable', ConnectionData,
								'tableBodyId');
						$("#loading").hide();

					}
				})
	}

	function loadSearchFilter1(param, tableData, temp) {
		$('#' + param).dataTable().fnClearTable();
		$('#' + param).dataTable().fnDestroy();
		$('#' + temp).html(tableData);
		$('#' + param).dataTable();

	}

	function clearButton() {

		window.location.href = "./arrearsCorrection";
	}
	
	function checkValid() {

		var connectionNo = $("#connection_no").val();
		var bill_adj_amount=$("#bill_adj_amount").val();
		var penalty_adj_amount=$("#penalty_adj_amount").val();
		var rebate_adj_amount=$("#rebate_adj_amount").val();
		var remarks = $('#remarks').val();
		var adjTowards=$('#adjTowards').val();
		var monthyear=$('#monthyear').val();
		var arrears=$("#arrears").val();
		var net_amt=$("#net_amt").val();

		var board_adj_amount=$('#board_adj_amount').val();
		var board_penalty_adj=$('#board_penalty_adj').val();

		var con_type=$("#con_type").val();
		var ward_no=$("#ward_no").val();
		var denoted_by=$("#denoted_by").val();
		var con_category=$("#con_category").val();
		var pipe_size=$("#pipe_size").val();
		var bod_bal=$("#board_bal").val();
		
			
// *********************************   Board Correction code...

		 if(adjTowards=='BRDCR'){
			if (board_adj_amount == "" || board_adj_amount == null) {
				alert("Please enter Board Adj Amount");
				return false;
			} else if (board_penalty_adj == "" || board_penalty_adj == null) {
				alert("Please enter Please enter Board Penalty Adj Amount");
				return false;
			}  else if (remarks == "" || remarks == null) {
				alert("Please enter Remark");
				return false;
			} 
			else if(bod_bal==0 && board_adj_amount <0)
				{
				alert("Board Adj Amount is less than board Bal..");
				return false;
				}
			/*else if(bill_adj_amount==0 && penalty_adj_amount==0){
				alert("Arrears Adj/Corr and Penalty Adj/Corr Both Cannot be 0");
				return false;
			} */else{
				if (confirm("Are you sure to Adjust the Board Corrections?")) {

					$.ajax({
						type : "GET",
						url : "./billpenalty/sendToBoradCorrectionApprove",
						dataType : "text",
						async : false,
						data : {
							connection_no : connectionNo,
							//bill_adj_amount : bill_adj_amount,
							//penalty_adj_amount : penalty_adj_amount,
							board_adj_amount : board_adj_amount,
							board_penalty_adj :board_penalty_adj,
							arrears : arrears,
							net_amt : net_amt,
							//from_mon_year : from_mon_year,
							to_mon_year : monthyear,
							remarks : remarks,
							'adjtype':adjTowards,
							ward_no : ward_no,
							denoted_by : denoted_by,
							con_category : con_category,
							pipe_size : pipe_size
						},
						success : function(response) {
							alert(response);
						}
					});
		}
		else{
			return false;
		}
		
	}
		

}
		
// **********************************  Board Correction code...		
		if(adjTowards!='Correction'){
			rebate_adj_amount=0;
		}
		
		if (connectionNo == "" || connectionNo == null) {
			alert("Please Enter Connection No");
			return false;
		} else if (remarks == "" || remarks == null) {
			alert("Please enter Remarks");
			return false;
		} else if(adjTowards!=null || adjTowards!="" ){
			
			if(adjTowards=='Board'){
				if(board_penalty_adj==""|| board_penalty_adj==null){
					board_penalty_adj=0;
				}
				
				if(board_adj_amount=="" || board_adj_amount==null){
					alert("Please Enter Board Adjustment Amount.");
					return false;
				} else{
					
					
						if (confirm("Are you sure to Adjust the Board Amount?")) {

							$.ajax({
								type : "GET",
								url : "./billpenalty/sendToBoardApprove",
								dataType : "text",
								async : false,
								data : {
									connection_no : connectionNo,
									//bill_adj_amount : bill_adj_amount,
									//penalty_adj_amount : penalty_adj_amount,
									board_adj_amount : board_adj_amount,
									board_penalty_adj :board_penalty_adj,
									arrears : arrears,
									net_amt : net_amt,
									//from_mon_year : from_mon_year,
									to_mon_year : monthyear,
									remarks : remarks,
									'adjtype':adjTowards
								},

								success : function(response) {
									alert(response);
								}
							});

						} else {

							return false;
						}
					}
			}
			else if(adjTowards=='Adjustment'){
				if (bill_adj_amount == "" || bill_adj_amount == null) {
					alert("Please enter Arrear Adj/Corr Amount");
					return false;
				} else if (penalty_adj_amount == "" || penalty_adj_amount == null) {
					alert("Please enter Please enter Penalty Adj/Corr Amount");
					return false;
				} else if(bill_adj_amount==0 && penalty_adj_amount==0){
					alert("Arrears Adj/Corr and Penalty Adj/Corr Both Cannot be 0");
					return false;
				}else{
					
					if (confirm("Are you sure to submit for Adjustment Approval?")) {

						$.ajax({
							type : "GET",
							url : "./billpenalty/sendToApprove",
							dataType : "text",
							async : false,
							data : {
								connection_no : connectionNo,
								bill_adj_amount : bill_adj_amount,
								penalty_adj_amount : penalty_adj_amount,
								arrears : arrears,
								net_amt : net_amt,
								//from_mon_year : from_mon_year,
								to_mon_year : monthyear,
								remarks : remarks,
								'adjtype':adjTowards,
								con_type : con_type,
								ward_no : ward_no,
								denoted_by : denoted_by,
								con_category : con_category,
								pipe_size : pipe_size
							
							},

							success : function(response) {
								alert(response);
							}
						});

					} else {

						return false;
					}
					
					
				}
				
			}else if(adjTowards=='Correction'){
				
				if (bill_adj_amount == "" || bill_adj_amount == null) {
					alert("Please enter Arrear Adj/Corr Amount");
					return false;
				} else if (penalty_adj_amount == "" || penalty_adj_amount == null) {
					alert("Please enter Please enter Penalty Adj/Corr Amount");
					return false;
				} else if (rebate_adj_amount == "" || rebate_adj_amount == null) {
					alert("Please enter Rebate Adj/Corr Amount");
					return false;
				} else if(bill_adj_amount==0 && penalty_adj_amount==0){
					alert("Arrears Adj/Corr and Penalty Adj/Corr Both Cannot be 0");
					return false;
				}else{
					if (confirm("Are you sure to Adjust the Arrears Corrections?")) {

						$.ajax({
							type : "GET",
							url : "./billpenalty/sendToCorrectionApprove",
							dataType : "text",
							async : false,
							data : {
								connection_no : connectionNo,
								bill_adj_amount : bill_adj_amount,
								penalty_adj_amount : penalty_adj_amount,
								arrears : arrears,
								net_amt : net_amt,
								//from_mon_year : from_mon_year,
								to_mon_year : monthyear,
								remarks : remarks,
								'adjtype':adjTowards,
								con_type : con_type,
								ward_no : ward_no,
								denoted_by : denoted_by,
								con_category : con_category,
								pipe_size : pipe_size,
								rebate_adj_amount : rebate_adj_amount
							},

							success : function(response) {
								alert(response);
							}
						});

					} else {

						return false;
					}
					
				}
				
				
			}
			
		}
			

	}

	function getConnectionDetails(connectionno) {
		var monthyr="";
		if($('#connection_no').val().trim()=='')
		{
		$('#alertDiv').show();
	    $('#alertDiv').html('<div class="alert alert-danger alert-bordered"><button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button><span class="text" id="alert">Please enter connection no.</span>');
	      return false;
		}
		
		
		
		if (!/[^a-zA-Z0-9]/.test(connectionno)) {

		} else {
			alert("Connection No does not contain Special Characters");
			$('#connection_no').val("");
			$('#name_eng').val("");
			$('#con_category').val("");
			$('#area_no').val("");
			$('#pipe_size').val("");
			$('#monthyear').val("");
			return false;
		}
		
		$.ajax({
			type : "GET",
			url : "./getconnectionMasterLedgerDetails/" + connectionno,
			dataType : "json",
			cache : false,
			async : false,
			success : function(response) {
				if (response.length == 0) {
					alert("Entered Connection Number is not available");
				} else {

					for (var i = 0; i < response.length; i++) {
						data = response[i];
						$('#sitecode').val(data.sitecode);
						$('#name_eng').val(data.name);
						$('#con_category').val(data.category);
						$('#area_no').val(data.area);
						$('#pipe_size').val(data.pipe);
						$("#arrears").val(data.arrears);
						$("#water_charge").val(data.waterCh);
						$("#sw_charge").val(data.swCh);
						$("#mtr_rent").val(data.mtrRent);
						$("#net_amt").val(data.netAmt);
						$('#monthyear').val(data.monthyr);

						$('#board_bal').val(data.board_amt);


						$('#con_type').val(data.con_type);
						$('#ward_no').val(data.ward_no);
						$('#denoted_by').val(data.denoted_by);
						$('#penaltyid').val(parseFloat(data.totalpenlaty).toFixed(2));
						$('#rebate').val(parseFloat(data.totalrebate).toFixed(2));
						
						monthyr=data.monthyr;
					}

				}

			}
		});

		
		var monthyearLM="${monthyearNepLMY}";
		if(monthyearLM==monthyr){
			
		}else{
			alert("Bill is not yet Generated for Latest Month Year : "+monthyearLM+".After generating Please do the Adjustment.!!!");
			$('#arrcorrid').hide();
		}
		
	}
	
	function changeLabel()
	 {
		var adjTowards=$('#adjTowards').val();
		if(adjTowards == 'Adjustment')
		{
			$('#board_div').hide();
			$('#arrear_div').show();
			$('#penalty_adj_div').show();
			$('#penaltyid_div').show();
			$('#board_penalty_adj_div').hide();
			$('#board_penalty_div').hide();
			$('#rebate_adj_div').hide();
			$("#bill_adj_amount").attr("placeholder", "Arrear Adjustment");
			$("#penalty_adj_amount").attr("placeholder", "Penalty Adjustment");
			
			var monthyr=$('#monthyear').val();
			var monthyearLM="${monthyearNepLMY}";
			if(monthyearLM==monthyr){
			}else{
				$('#arrcorrid').hide();
			    }
		}
		else if(adjTowards == 'Correction')
		{
			$('#board_div').hide();
			$('#arrear_div').show();
			$('#penalty_adj_div').show();
			$('#penaltyid_div').show();
			$('#rebate_adj_div').show();
			$('#board_penalty_adj_div').hide();
			$('#board_penalty_div').hide();
			$("#bill_adj_amount").attr("placeholder", "Arrear Correction");
			$("#penalty_adj_amount").attr("placeholder", "Penalty Correction");
			
			var monthyr=$('#monthyear').val();
			var monthyearLM="${monthyearNepLMY}";
			if(monthyearLM==monthyr){
			}else{
				$('#arrcorrid').hide();
			}
		}
		else if(adjTowards=='BRDCR')
			{
			//alert($("#sitecode").val());
			/* if($("#sitecode").val()!=2222)
				{
				alert("Only for Test Location !");window.location.href = "./arrearsCorrection";
				} */
			//("#var con_type=$("#con_type").val();"))
			$('#board_div').show();
			$('#arrear_div').hide();
			$('#penalty_adj_div').hide();
			$('#penaltyid_div').hide();
			$('#rebate_adj_div').hide();
			$('#board_penalty_adj_div_brdcr').show();
			$('#board_penalty_adj_div').hide();
			$('#board_penalty_div').show();
			var b_pen=$('#board_bal').val();
			/* if(b_pen==0)
				{
				var bol=alert("Board Bal is Zero,board correction can't be performed.");
			
				window.location.href = "./arrearsCorrection";
				
				} */
			$('#board_penalty').val(parseFloat(b_pen)/2);
			var monthyr=$('#monthyear').val();
			var monthyearLM="${monthyearNepLMY}";
			if(monthyearLM==monthyr){
			}else{
				$('#arrcorrid').show();
			    }
			}
		
		else{
			$('#board_div').show();
			$('#arrear_div').hide();
			$('#penalty_adj_div').hide();
			$('#penaltyid_div').hide();
			$('#rebate_adj_div').hide();
			$('#board_penalty_adj_div').show();
			$('#board_penalty_div').show();
			var b_pen=$('#board_bal').val();
			$('#board_penalty').val(parseFloat(b_pen)/2);
			var monthyr=$('#monthyear').val();
			var monthyearLM="${monthyearNepLMY}";
			if(monthyearLM==monthyr){
			}else{
				$('#arrcorrid').show();
			    }
		}
	 }

	function checkNumeric() 
	{
		var bill_adj_amount=$("#bill_adj_amount").val();
		var penalty_adj_amount=$("#penalty_adj_amount").val()
		var rebate_adj_amount=$("#rebate_adj_amount").val()
		var board_adj_amount=$("#board_adj_amount").val();
		var board_penalty_adj=$('#board_penalty_adj').val();
		if(!/[^0-9-.]/.test(bill_adj_amount)==false)
			{
				$('#bill_adj_amount').val(bill_adj_amount.substring(0, bill_adj_amount.length - 1));
			}
		else if(!/[^0-9-.]/.test(penalty_adj_amount)==false)
		{
			$('#penalty_adj_amount').val(penalty_adj_amount.substring(0, penalty_adj_amount.length - 1));
		}
		else if(!/[^0-9-.]/.test(board_adj_amount)==false)
		{
			$('#board_adj_amount').val(board_adj_amount.substring(0, board_adj_amount.length - 1));
		}
		else if(!/[^0-9-.]/.test(board_penalty_adj)==false)
		{
			$('#board_penalty_adj').val(board_penalty_adj.substring(0, board_penalty_adj.length - 1));
		}
		else if(!/[^0-9-.]/.test(rebate_adj_amount)==false)
		{
			$('#rebate_adj_amount').val(rebate_adj_amount.substring(0, rebate_adj_amount.length - 1));
		}
	}
	
	function convertToUpperCase(){
		
		$("#connection_no").val($("#connection_no").val().toUpperCase().trim());
		
	}
	
	
	function ledgerHistoryPopUp(){
		$("#viewPayHistotytbody1").empty();
		
		var connectionNo=$("#connection_no").val();
		
		if(connectionNo==null || connectionNo==""){
			
		}else{
		
		var tableData = "";
		$.ajax
		({			
			type : "GET",
			url : "./billing/viewBillLedgertHistory/"+connectionNo,
			async: false,
			dataType : "JSON",
			success : function(response) 
			{	    
				for ( var s = 0, len = response.length; s < len; ++s) {
			              	var obj = response[s];
			              	
			              	tableData += "<tr>"
									    +"<td>"+(s+1)+"</td>" 
									    +"<td>"+obj.monthyearnep+"</td>"
										+"<td>"+obj.cr+"</td>"
										+"<td>"+obj.lr+"</td>"
										+"<td>"+obj.units+"</td>"
										+"<td>"+obj.lastarrears+"</td>"
										+"<td>"+obj.water_charges+"</td>"
										+"<td>"+obj.sw_charges+"</td>"
										+"<td>"+obj.mtr_rent+"</td>"
										+"<td>"+obj.net_amount+"</td>"
										+"<td>"+obj.penalty+"</td>"
										+"<td>"+obj.rebate+"</td>"
										+"<td>"+obj.bill_adj+"</td>"
										+"<td>"+obj.penalty_adj+"</td>"
										+"<td>"+obj.arrear_correction+"</td>"
										+"<td>"+obj.penalty_correction+"</td>"
										+"<td>"+obj.receipt_amount+"</td>"
										+"<td>"+obj.close_balnce+"</td>"
										+"<td>"+obj.receipt_no+"</td>"
										+"<td>"+obj.receipt_date+"</td>"
										+"<td>"+obj.billno+"</td>"

									+"</tr>";
					                
					     }
					$('#viewPayHistotytbody1').append(tableData);
					loadSearchFilter1('popupPayment1',tableData,'viewPayHistotytbody1');
			}
		

		});
	}
		
	}
	
</script>


<style>
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

.datatable-header, .datatable-footer {
	padding: 0px 0px 0 0px;
}

.modal.fade .modal-dialog {
	width: 1200px;
}
</style>


