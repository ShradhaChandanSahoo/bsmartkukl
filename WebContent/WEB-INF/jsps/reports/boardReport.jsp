<%@include file="/WEB-INF/decorators/taglibs.jsp"%>
<%@ taglib prefix="kendo" uri="/WEB-INF/lib/kendo-taglib-2015.1.429.jar"%>

<script src="./resources/kendo/shared/js/pako.min.js"></script>

<link
	href="<c:url value='./resources/kendo/css/web/kendo.common.min.css'/>"
	rel="stylesheet" />
<link
	href="<c:url value='./resources/kendo/css/web/kendo.rtl.min.css'/>"
	rel="stylesheet" />
<link
	href="<c:url value='./resources/kendo/css/web/kendo.silver.min.css'/>"
	rel="stylesheet" />
<link
	href="<c:url value='./resources/kendo/css/dataviz/kendo.dataviz.min.css'/>"
	rel="stylesheet" />
<link
	href="<c:url value='./resources/kendo/css/dataviz/kendo.dataviz.default.min.css'/>"
	rel="stylesheet" />
<link
	href="<c:url value='./resources/kendo/shared/styles/examples-offline.css'/>"
	rel="stylesheet">

<script src="<c:url value='./resources/kendo/js/kendo.all.min.js' />"></script>
<script src="<c:url value='./resources/kendo/js/kendo.excel.min.js' />"></script>
<script src="<c:url value='./resources/kendo/js/kendo.pdf.min.js' />"></script>
<script src="<c:url value='./resources/kendo/shared/js/console.js'/>"></script>
<script src="<c:url value='./resources/kendo/shared/js/prettify.js'/>"></script>
<c:url value="/detailedCashCollReport/boarReportDetails"
	var="readCategoryUrl" />


<script src="//cdnjs.cloudflare.com/ajax/libs/jszip/2.4.0/jszip.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$('#collectionsReportsScreen').show();
		$('#collectionsReports').addClass('active');
		
		var activeMod="${activeModulesNew}";
		var module="Collection Reports";
		var module1="Collection Reports CI/CSR";
		var check=activeMod.indexOf(module) > -1;
		var check1=activeMod.indexOf(module1) > -1;
		
		if(check){
		}else if(check1){
		}else{
			window.location.href="./accessDenied";
		}
		
		
		
		$("#categoryGrid").kendoGrid({
			pageable : {
				pageSizes : [ 5, 10, 20, 50, 100, 500, 1000, 2000 ]
			},

		});
		$('#from_date_nep').nepaliDatePicker({
			dateFormat : "%D, %M %d, %y",
			npdMonth : true,
			npdYear : true,
			onChange : function() {
				var rdngdtnep = $('#from_date_nep').val();
				getEngDate(rdngdtnep, 1);
			}
		});

		$('#to_date_nep').nepaliDatePicker({
			dateFormat : "%D, %M %d, %y",
			npdMonth : true,
			npdYear : true,
			onChange : function() {
				var rdngdtnep = $('#to_date_nep').val();
				getEngDate(rdngdtnep, 2);
			}
		});
	});

	//height : 500
</script>

<div class="page-content col-md-12">
	<div class="panel panel-flat">
		<div class="panel-body">
			<fieldset class="content-group">
				<legend class="text-bold">Board Report</legend>
				<div>

					<input type="text" id="from_date_eng" name="from_date_eng"
						hidden="true" /> <input type="text" id="to_date_eng"
						name="to_date_eng" hidden="true" />

					<div class="col-md-4">
						<div class="form-group">
							<label>From Date in Nepali&nbsp;<font color="red">*</font></label>
							<input type="text" id="from_date_nep" name="from_date_nep"
								class="form-control nepali-calendar" required="required"
								placeholder="From Date in Nepali...">
						</div>
					</div>
					<div class="col-md-4">
						<div class="form-group">
							<label>To Date in Nepali&nbsp;<font color="red">*</font></label>
							<input type="text" name="to_date_nep" id="to_date_nep"
								class="form-control nepali-calendar" required="required"
								placeholder="To Date in Nepali...">
						</div>
					</div>
					<div class="col-md-4">
						<div class="form-group" style="padding-top: 27px;">
							<button type="button" class="btn bg-teal btn-ladda"
								onclick="clearButton();">
								<span class="ladda-label">Clear</span>
							</button>
							<label>&nbsp;</label>
							<button type="button" class="btn bg-teal btn-ladda"
								onclick="return getData()" data-style="expand-right"
								data-spinner-size="20">
								<span class="ladda-label">Get Board Report</span>
							</button>
						</div>
					</div>

				</div>
			</fieldset>
			<div class="wethear" style="overflow: scroll;">
				<fieldset class="content-group">
					<legend class="text-bold">Detailed Board Report</legend>

					<!-- <button type="button" class="k-button" id="printGrid">Print Grid</button> -->
					<div class="pdf-page">
						<kendo:grid name="categoryGrid" pageable="true"
							dataBound="dataBound" change="onChange" resizable="true"
							sortable="true" reorderable="true" selectable="true"
							scrollable="true" filterable="true" groupable="true">
							<kendo:grid-pageable pageSizes="true" buttonCount="100"
								pageSize="500" previousNext="true" input="true" numeric="true"></kendo:grid-pageable>
							<kendo:grid-filterable extra="true">
								<kendo:grid-filterable-operators>
									<kendo:grid-filterable-operators-string eq="Is equal to" />
									<kendo:grid-filterable-operators-number eq="Is equal to"
										gt="Is greather than" gte="IS greather than and equal to"
										lt="Is less than" lte="Is less than and equal to"
										neq="Is not equal to" />
								</kendo:grid-filterable-operators>
							</kendo:grid-filterable>

							<kendo:grid-toolbar>
								<kendo:grid-toolbarItem name="excel"></kendo:grid-toolbarItem>
								<kendo:grid-toolbarItem name="pdf"></kendo:grid-toolbarItem>
								<kendo:grid-toolbarItem name="printGrid" text="Print"></kendo:grid-toolbarItem>
							</kendo:grid-toolbar>
							<%--  <kendo:grid-excel allPages="true" fileName="Kendo UI Grid Export.xlsx" filterable="true"  /> footerTemplate="<span id='sumTotal_Earnings'></span>"--%>
							<kendo:grid-editable mode="popup"
								confirmation="Are you sure you want to remove this item?" />
							<kendo:grid-columns>

								<kendo:grid-column title="SLNO" field="slNo" width="90px"
									filterable="true" />
								<kendo:grid-column title="CON NO" field="connection_no"
									width="100px" filterable="true" groupFooterTemplate="Total :"
									footerTemplate="Grand Total:" />
								<kendo:grid-column title="AMOUNT" field="amount" width="110px"
									filterable="true" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(amount,'n2') #" />
								<kendo:grid-column title="RECEIPT NO" field="receipt_no"
									width="130px" filterable="true" />
								<kendo:grid-column title="RDATE" field="rdate" width="100px"
									filterable="true" />
								<kendo:grid-column title="COUNTER" field="counterno"
									width="100px" filterable="true" />
								<%-- <kendo:grid-column title="TOWARDS" field="towards"
									filterable="true" width="130px" /> --%>
								<%-- <kendo:grid-column title="RECORDTYPE" field="recordtype"
									filterable="true" width="140px" /> --%>
								<kendo:grid-column title="PAY MODE" field="pay_mode"
									filterable="true" width="80px" />
								<kendo:grid-column title="CHEQ/DD NO" field="cdno" filterable="true"
									width="100px" /> 
								<kendo:grid-column title="CHEQ/DD DATE" field="chqDate" filterable="true"
									width="100px" /> 
								<kendo:grid-column title="PENALTY" field="penalty"
									filterable="true" width="100px" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(penalty, 'n2','0.00') #" />
								<kendo:grid-column title="BOARD ADJ AMOUNT" field="board_adj_amt"
									filterable="true" width="100px" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(board_adj_amt, 'n2','0.00') #" />
								<kendo:grid-column title="PENALTY ADJ AMOUNT" field="penalty_adj_amt"
									filterable="true" width="80px" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(penalty_adj_amt, 'n2','0.00') #" />
								<%-- <kendo:grid-column title="MISC"
									field="miscellaneous_cost" filterable="true" width="90px"
									aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(miscellaneous_cost, 'n2','0.00') #" />
								<kendo:grid-column title="PENALTY" field="penalty"
									filterable="true" width="100px" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(penalty, 'n2','0.00') #" /> --%>
								<%-- <kendo:grid-column title="REBATE" field="rebate"
									filterable="true" width="100px" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(rebate, 'n2','0.00') #" />
								<kendo:grid-column title="FRECAMT" field="frecamount"
									filterable="true" width="100px" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(frecamount, 'n2','0.00') #" />
								<kendo:grid-column title="ADV COLL" field="advance"
									filterable="true" width="80px" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(advance, 'n2','0.00') #" />
								<kendo:grid-column title="ADV REB" field="advance_rebate"
									filterable="true" width="100px" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(advance_rebate, 'n2','0.00') #" />
								<kendo:grid-column title="OLD_BAL" field="old_balance"
									filterable="true" width="100px" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(old_balance, 'n2','0.00') #" /> --%>
								<%-- <kendo:grid-column title="USERID" field="user_id"
									filterable="true" width="110px" /> --%>

							</kendo:grid-columns>
							<kendo:dataSource pageSize="500">
								<kendo:dataSource-transport>
									<kendo:dataSource-transport-read url="${readCategoryUrl}"
										dataType="json" type="POST" contentType="application/json" />
								</kendo:dataSource-transport>
								<kendo:dataSource-schema>
									<kendo:dataSource-schema-model id="id">
										<kendo:dataSource-schema-model-fields>
											<kendo:dataSource-schema-model-field name="slNo"
												type="number" />
											<kendo:dataSource-schema-model-field name="connection_no"
												type="string" />
											<kendo:dataSource-schema-model-field name="amount"
												type="number" />
											<kendo:dataSource-schema-model-field name="receipt_no"
												type="string" />
											<kendo:dataSource-schema-model-field name="rdate"
												type="number" />
											<kendo:dataSource-schema-model-field name="counterno"
												type="number" />
											<%-- <kendo:dataSource-schema-model-field name="towards"
												type="string" /> --%>
											<kendo:dataSource-schema-model-field name="pay_mode"
												type="string" /> 
											<kendo:dataSource-schema-model-field name="cdno"
												type="string" /> 
											<kendo:dataSource-schema-model-field name="chqDate"
												type="string" /> 
											<kendo:dataSource-schema-model-field name="penalty"
												type="number" />
											<kendo:dataSource-schema-model-field name="board_adj_amt"
												type="number" />
											<kendo:dataSource-schema-model-field name="penalty_adj_amt"
												type="number" />
											
											<%-- <kendo:dataSource-schema-model-field name="penalty"
												type="number" />
											<kendo:dataSource-schema-model-field name="rebate"
												type="number" />
											<kendo:dataSource-schema-model-field name="frecamount"
												type="number" />
											<kendo:dataSource-schema-model-field name="advance"
												type="number" />
											<kendo:dataSource-schema-model-field name="advance_rebate"
												type="number" />
											<kendo:dataSource-schema-model-field name="old_balance"
												type="number" /> --%>
											<%-- <kendo:dataSource-schema-model-field name="user_id"
												type="string" /> --%>
										</kendo:dataSource-schema-model-fields>
									</kendo:dataSource-schema-model>
								</kendo:dataSource-schema>
							</kendo:dataSource>
						</kendo:grid>
					</div>
				</fieldset>
			</div>
		</div>
	</div>
</div>


<div id="loading" hidden="true" style="text-align: center;">
	<h3 id="loadingText">Loading..... Please wait.</h3>
	<img alt="image" src="./resources/images/loading.gif"
		style="width: 3%; height: 3%;">
</div>





<script>
	var amount = 0;
	var pay_mode = 0;
	
	var penalty = 0;
	var board_adj_amt = 0;
	var penalty_adj_amt=0;
	
	
	

	function dataBound(e) {
		var data = this.dataSource.view();
		amount = 0;
		pay_mode = 0;
		penalty = 0;
		board_adj_amt = 0;
		penalty_adj_amt=0;
		
		for (var i = 0; i < data.length; i++) {
			amount = amount + data[i].amount;
			pay_mode = pay_mode + data[i].pay_mode;
			penalty = penalty + data[i].penalty;
			board_adj_amt = board_adj_amt + data[i].board_adj_amt;
			
			penalty_adj_amt = parseFloat(penalty_adj_amt) + parseFloat(data[i].penalty_adj_amt);
		}
		//old_balance=parseFloat(old_balance).toFixed(2);
	}
	function onChange(arg) {
	}

	function getPDF(selector) {
		kendo.drawing.drawDOM("#categoryGrid", {
			paperSize : "A4",
			landscape : true,
			margin : "2cm"
		}).then(function(group) {
			kendo.drawing.pdf.saveAs(group, "multipage.pdf");
		});

		/* kendo.drawing.drawDOM($(selector)).then(function(group){
		  kendo.drawing.pdf.saveAs(group, "Invoice.pdf");
		}); */
	}
	function printGrid() {
		var frm = $('#from_date_nep').val();
		var to = $('#to_date_nep').val();

		var gridElement = $('#categoryGrid'), printableContent = '', win = window
				.open('', '', 'width=auto, height=900'), doc = win.document
				.open();
		var branch="${BranchName}";
		var htmlStart = '<!DOCTYPE html>'
				+ '<html>'
				+ '<head>'
				+ '<meta charset="utf-8" />'
				+ '<title>Detailed Cash Collection Report</title>'
				+ '<link href="http://kendo.cdn.telerik.com/' + kendo.version + '/styles/kendo.common.min.css" rel="stylesheet" /> '
				+ '<style>'
				+ 'html { font: 8pt sans-serif; }'
				+ '.k-grid { border-top-width: auto; }'
				+ '.k-grid, .k-grid-content { height: auto !important; }'
				+ '.k-grid-content { overflow: visible !important; }'
				+ 'div.k-grid table { table-layout: 110%; width: auto !important; }'
				+ '.k-grid .k-grid-header th { border-top: 1px solid; }'
				+ '.k-grid tr td {border-bottom: 1px solid black;}'
				+ '.k-grid-toolbar, .k-grid-pager > .k-link { display: none; }'
				+ '.k-grid .k-grouping-header { display: none;}'
				+ '.k-pager-wrap, .k-grid-pager{ display: none;}'
				+ '.k-grid td { line-height: 1em;}'
				+ '.k-grid, .k-grid-footer { height: auto !important; width: 100% !important; padding-right: 0px !important; }'
				+ '</style>'
				+ '</head>'
				+ '<body> <center style="line-height: 10px;"><h1>Kathmandu Upatyaka Khanepani Limited</h1>'
				+ '<h2>'+branch+' kathmandu</h2><h3>Detailed Cash Collection Report</h3>'
				+ '<h4>From :&nbsp;&nbsp;' + frm
				+ '&nbsp;&nbsp;&nbsp;To :&nbsp;&nbsp;' + to + '</h4></center>';

		var htmlEnd = '</body>' + '</html>';

		var gridHeader = gridElement.children('.k-grid-header');
		if (gridHeader[0]) {
			var thead = gridHeader.find('thead').clone().addClass(
					'k-grid-header');
			printableContent = gridElement.clone().children('.k-grid-header')
					.remove().end().children('.k-grid-content').find('table')
					.first().children('tbody').before(thead).end().end().end()
					.end()[0].outerHTML;
		} else {
			printableContent = gridElement.clone()[0].outerHTML;
		}

		doc.write(htmlStart + printableContent + htmlEnd);
		doc.close();
		win.print();
	}
	$("#categoryGrid").on("click", ".k-grid-printGrid", function(e) {
		printGrid();
	});

	function getEngDate(nepalidate, value) {

		var date_nep = nepalidate;
		$
				.ajax({
					type : "GET",
					url : "./billing/onChangeNepaliDate",
					dataType : "text",
					async : false,
					data : {

						date_nep : date_nep,

					},

					success : function(response) {

						if (value == 1) {

							$('#from_date_eng').val(response);
						} else {
							var from_date = $("#from_date_eng").val();
							var to_date = response;
							var startDate = new Date(from_date);
							var endDate = new Date(to_date);
							if (startDate > endDate) {
								alert("To date Nepali should be greater than From Date in Nepali");
								$('#to_date_nep').val("");
							} else {
								$('#to_date_eng').val(response);
							}
						}
					}

				});

	}
	function getData() {
		var from_date = $("#from_date_eng").val();
		var to_date = $("#to_date_eng").val();

		if (from_date == "") {
			alert("Please Select From Date");
			return false;
		}

		if (to_date == "") {
			alert("Please Select To Date");
			return false;
		}

		var startDate = new Date(from_date);
		var endDate = new Date(to_date);

		if (startDate > endDate) {
			alert("To date Nepali should be greater than From Date in Nepali");
			$('#to_date_nep').val("");
			return false;
		} else {

			$("#loading").show();

			$
					.ajax({
						type : "GET",
						url : "./detailedCashCollReport/boarReportDetails",
						dataType : "JSON",
						async : false,
						data : {
							from_date : from_date,
							to_date : to_date,
						},

						success : function(response) {
							var grid = $("#categoryGrid").getKendoGrid();
							var data = new kendo.data.DataSource();
							grid.dataSource.data(response);
							grid.refresh();
						}

					});

			$("#loading").hide();
		}

	}
function clearButton(){
		
		window.location.href="./detailedCashCollReport";
	}
</script>
<style>
</style>