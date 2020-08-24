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
<c:url value="/detailedCashCollReport/detailedCashCollReportReadData"
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
				<legend class="text-bold">Detailed Miscellaneous Collection Report</legend>
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
								<span class="ladda-label">Get Detailed Collection Report</span>
							</button>
						</div>
					</div>

				</div>
			</fieldset>
			<div class="wethear" style="overflow: scroll;">
				<fieldset class="content-group">
					<legend class="text-bold">Detailed Cash Collection Report</legend>

					<!-- <button type="button" class="k-button" id="printGrid">Print Grid</button> -->
					<div class="pdf-page">
						<kendo:grid name="categoryGrid" pageable="true"
							dataBound="dataBound" change="onChange" resizable="true"
							sortable="true" reorderable="true" selectable="true"
							scrollable="true" filterable="true" groupable="true">
							<kendo:grid-pageable pageSizes="true" buttonCount="100"
								pageSize="20" previousNext="true" input="true" numeric="true"></kendo:grid-pageable>
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

								<kendo:grid-column title="Sl No" field="slNo" width="90px"
									filterable="true" groupFooterTemplate="Total :"
									footerTemplate="Grand"  />
								<kendo:grid-column title="Con No" field="connection_no"
									width="120px" filterable="true" groupFooterTemplate="Total :"
									footerTemplate="Total:" />
								<kendo:grid-column title="Amount" field="amount" width="130px"
									filterable="true" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(amount,'n2') #" />
								<kendo:grid-column title="Receipt No" field="receipt_no"
									width="150px" filterable="true" />
								<kendo:grid-column title="Receipt Date" field="rdate" width="120px"
									filterable="true" />
								
								
								<kendo:grid-column title="App ID" field="application_id"
									width="100px" filterable="true" />
								<kendo:grid-column title="Counter No" field="counterno"
									filterable="true" width="90px"/>
								<kendo:grid-column title="Misc" field="miscellaneous_cost"
									filterable="true" width="100px" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(miscellaneous_cost, 'n2','0.00') #" />
								<kendo:grid-column title="Adv Coll" field="advance"
									filterable="true" width="120px" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(advance, 'n2','0.00') #" />
								<kendo:grid-column title="NC Tap"
									field="nc_tap" filterable="true" width="100px"
									aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(nc_tap, 'n2','0.00') #" />
									
								<kendo:grid-column title="NC Deposit" field="nc_deposit"
									filterable="true" width="120px" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(nc_deposit, 'n2','0.00') #" />
								<kendo:grid-column title="Meter Value" field="meter_value"
									filterable="true" width="120px" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(meter_value, 'n2','0.00') #" />
								<kendo:grid-column title="Hole Block" field="temporaty_hole_block"
									filterable="true" width="120px" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(temporaty_hole_block, 'n2','0.00') #" />
								<kendo:grid-column title="Ownership Change" field="name_change"
									filterable="true" width="120px" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(name_change, 'n2','0.00') #" />
								<kendo:grid-column title="NC Installation" field="nc_inst"
									filterable="true" width="130px" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(nc_inst, 'n2','0.00') #" />
									
								<kendo:grid-column title="Hole Change" field="hole_ch"
									filterable="true" width="110px" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(hole_ch, 'n2','0.00') #" />
									
								<kendo:grid-column title="Hole Maintenance" field="hole_main"
									filterable="true" width="130px" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(hole_main, 'n2','0.00') #" />
									
								<kendo:grid-column title="Application Charge" field="app_ch"
									filterable="true" width="130px" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(app_ch, 'n2','0.00') #" />
									
								<kendo:grid-column title="Tender/Sealed Quotation" field="tender"
									filterable="true" width="130px" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(tender, 'n2','0.00') #" />
									
								<kendo:grid-column title="Card Charge" field="card_ch"
									filterable="true" width="100px" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(card_ch, 'n2','0.00') #" />
									
								<kendo:grid-column title="Others" field="others"
									filterable="true" width="90px" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(others, 'n2','0.00') #" />
								
									<kendo:grid-column title="Illegal Con Penalty" field="ilg_con_amt"
									filterable="true" width="110px" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(ilg_con_amt, 'n2','0.00') #" />
								
								<%-- <kendo:grid-column title="USERID" field="user_id"
									filterable="true" width="110px" /> --%>

							</kendo:grid-columns>
							<kendo:dataSource pageSize="20">
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
											<kendo:dataSource-schema-model-field name="application_id"
												type="number" />
											<kendo:dataSource-schema-model-field name="counterno"
												type="number" />
											
											<kendo:dataSource-schema-model-field name="miscellaneous_cost"
												type="number" />
											<kendo:dataSource-schema-model-field name="advance"
												type="number" />
											<kendo:dataSource-schema-model-field name="nc_tap"
												type="number" />
											<kendo:dataSource-schema-model-field
												name="nc_deposit" type="number" />
											<kendo:dataSource-schema-model-field name="meter_value"
												type="number" />
											<kendo:dataSource-schema-model-field name="temporaty_hole_block"
												type="number" />
											<kendo:dataSource-schema-model-field name="name_change"
												type="number" />
											<kendo:dataSource-schema-model-field name="nc_inst"
												type="number" />
											<kendo:dataSource-schema-model-field name="hole_ch"
												type="number" />
											<kendo:dataSource-schema-model-field name="hole_main"
												type="number" />
											<kendo:dataSource-schema-model-field name="app_ch"
												type="number" />
											<kendo:dataSource-schema-model-field name="tender"
												type="number" />
											<kendo:dataSource-schema-model-field name="card_ch"
												type="number" />
											<kendo:dataSource-schema-model-field name="others"
												type="number" />
												<kendo:dataSource-schema-model-field name="ilg_con_amt"
												type="number" />
											
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
	var miscellaneous_cost = 0;
	var advance = 0;
	var nc_tap = 0;
	var nc_deposit = 0;
	var meter_value = 0;
	var temporaty_hole_block = 0;
	var name_change = 0;
	var nc_inst = 0;
	var hole_ch=0;
	var hole_main=0;
	var app_ch=0;
	var tender=0;
	var card_ch=0;
	var others=0;
    var ilg_con_amt=0;
	function dataBound(e) {
		var data = this.dataSource.view();
		amount = 0;
		miscellaneous_cost = 0;
		advance = 0;
		nc_tap = 0;
		nc_deposit = 0;
		meter_value = 0;
		temporaty_hole_block = 0;
		name_change = 0;
		nc_inst = 0;
		hole_ch=0;
		hole_main=0;
		app_ch=0;
		tender=0;
		card_ch=0;
		others=0;
		ilg_con_amt=0;
		for (var i = 0; i < data.length; i++) {
			amount = amount + data[i].amount;
			miscellaneous_cost = miscellaneous_cost+ data[i].miscellaneous_cost;
			advance = advance + data[i].advance;
			nc_tap = nc_tap + data[i].nc_tap;
			nc_deposit = nc_deposit + data[i].nc_deposit;
			meter_value = meter_value + data[i].meter_value;
			temporaty_hole_block = temporaty_hole_block + data[i].temporaty_hole_block;
			name_change = name_change + data[i].name_change;
			nc_inst = nc_inst + data[i].nc_inst;
			app_ch = app_ch + parseFloat(data[i].app_ch);
			hole_main = hole_main + data[i].hole_main;
			tender = tender + data[i].tender;
			card_ch = card_ch + data[i].card_ch;
			others = others + data[i].others;
			hole_ch = hole_ch + data[i].hole_ch;
			ilg_con_amt=ilg_con_amt+data[i].ilg_con_amt;
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
				+ '<title>Detailed Miscellaneous Collection Report</title>'
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
				+ '<h2>'+branch+' kathmandu</h2><h3>Detailed Miscellaneous Collection Report</h3>'
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
						url : "./detailedMiscCollReport/detailedMiscCollReportReadData",
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
		
		window.location.href="./detailedMiscCollReport";
	}
</script>
<style>
</style>