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
<c:url value="/monthlySalesSummaryReport/monthlySalesSummaryReportRead"
	var="readCategoryUrl" />


<script src="//cdnjs.cloudflare.com/ajax/libs/jszip/2.4.0/jszip.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		
		$('#collectionsReportsScreen').show();
		$('#collectionsReports').addClass('active');
		
		var activeMod="${activeModulesNew}";
		var module="Collection Reports";
		var check=activeMod.indexOf(module) > -1;
		
		if(check){
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
				<legend class="text-bold">Monthly Sales Summary Report</legend>
				<div>
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
								<span class="ladda-label">Get Monthly Sales Report</span>
							</button>
						</div>
					</div>

				</div>
			</fieldset>
			<div class="wethear" style="overflow: scroll;">
				<fieldset class="content-group">
					<legend class="text-bold">Monthly Sales Summary Report</legend>

					<!-- <button type="button" class="k-button" id="printGrid">Print Grid</button> -->
					<div class="pdf-page">
						<kendo:grid name="categoryGrid" pageable="true"
							dataBound="dataBound" change="onChange" resizable="true"
							sortable="true" reorderable="true" selectable="true"
							scrollable="true" filterable="true" groupable="true">
							<kendo:grid-pageable pageSizes="true" buttonCount="100"
								pageSize="100" previousNext="true" input="true" numeric="true"></kendo:grid-pageable>
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

								<kendo:grid-column title="Sl" field="slNo" width="60px"
									filterable="true" groupFooterTemplate="Total :"
									footerTemplate="Grand&nbsp;Total :" />
								<kendo:grid-column title="Ward" field="wardNo" width="90px"
									filterable="true" />
								<kendo:grid-column title="Open Bal"
									field="opening_balance" width="145px" filterable="true"
									aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(opening_balance,'n2') #" />
								<kendo:grid-column title="WC" field="water_charges"
									width="140px" filterable="true" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(water_charges, 'n2') #" />
								<kendo:grid-column title="SC" field="sw_charges"
									width="130px" filterable="true" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(sw_charges,'n2') #" />
								<kendo:grid-column title="MR" field="mtr_rent"
									filterable="true" width="110px" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(mtr_rent,'n2','0.00') #" />
								<kendo:grid-column title="Total" field="total_bill"
									filterable="true" width="130px" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(total_bill, 'n2','0.00') #" />
								<kendo:grid-column title="Net" field="net_amount"
									filterable="true" width="140px" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(net_amount, 'n2','0.00') #" />
								<kendo:grid-column title="Misc" field="misc" width="100px"
									filterable="true" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(misc, 'n2','0.00') #" />
								<kendo:grid-column title="Penalty" field="penalty"
									filterable="true" width="120px" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(penalty, 'n2','0.00') #" />
								<kendo:grid-column title="Rebate" field="rebate"
									filterable="true" width="120px" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(rebate, 'n2','0.00') #" />
								<kendo:grid-column title="Paid" field="ramount"
									filterable="true" width="110px" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(ramount, 'n2','0.00') #" />
								
								<kendo:grid-column title="Adv" field="adv"
									filterable="true" width="120px" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(adv, 'n2','0.00') #" />
									
								<kendo:grid-column title="AdvReb" field="adv_reb"
									filterable="true" width="120px" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(adv_reb, 'n2','0.00') #" />
									
									
								<kendo:grid-column title="CloseBal" field="cbalance"
									filterable="true" width="120px" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(cbalance, 'n2','0.00') #" />
								<%-- <kendo:grid-column title="Type" field="con_type"
									width="105px" filterable="true" /> --%>
									
							</kendo:grid-columns>
							<kendo:dataSource pageSize="100">
								<kendo:dataSource-transport>
									<kendo:dataSource-transport-read url="${readCategoryUrl}"
										dataType="json" type="POST" contentType="application/json" />
								</kendo:dataSource-transport>
								<kendo:dataSource-schema>
									<kendo:dataSource-schema-model id="id">
										<kendo:dataSource-schema-model-fields>
											<kendo:dataSource-schema-model-field name="slNo"
												type="number" />
											<kendo:dataSource-schema-model-field name="wardNo"
												type="string" />
											<%-- <kendo:dataSource-schema-model-field name="con_type"
												type="string" /> --%>
											<kendo:dataSource-schema-model-field name="opening_balance"
												type="number" />
											<kendo:dataSource-schema-model-field name="water_charges"
												type="number" />
											<kendo:dataSource-schema-model-field name="sw_charges"
												type="number" />
											<kendo:dataSource-schema-model-field name="mtr_rent"
												type="number" />
											<kendo:dataSource-schema-model-field name="total_bill"
												type="number" />
											<kendo:dataSource-schema-model-field name="net_amount"
												type="number" />
											<kendo:dataSource-schema-model-field name="misc"
												type="number" />
											<kendo:dataSource-schema-model-field name="penalty"
												type="number" />
											<kendo:dataSource-schema-model-field name="rebate"
												type="number" />
											<kendo:dataSource-schema-model-field name="ramount"
												type="number" />
											<kendo:dataSource-schema-model-field name="cbalance"
												type="number" />
											<kendo:dataSource-schema-model-field name="adv"
												type="number" />
											<kendo:dataSource-schema-model-field name="adv_reb"
												type="number" />
												
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
	var opening_balance = 0;
	var water_charges = 0;
	var sw_charges = 0;
	var mtr_rent = 0;
	var total_bill = 0;
	var net_amount = 0;
	var penalty = 0;
	var rebate = 0;
	var ramount = 0;
	var misc = 0;
	var adv = 0;
	var adv_reb = 0;
	var cbalance = 0;
	var branch = "${BranchName}";
	function dataBound(e) {
		var data = this.dataSource.view();
		opening_balance = 0;
		water_charges = 0;
		sw_charges = 0;
		mtr_rent = 0;
		total_bill = 0;
		net_amount = 0;
		penalty = 0;
		rebate = 0;
		misc = 0;
		ramount = 0;
		cbalance = 0;
		adv = 0;
		adv_reb = 0;

		for (var i = 0; i < data.length; i++) {
			opening_balance = opening_balance + data[i].opening_balance;
			water_charges = water_charges + data[i].water_charges;
			sw_charges = sw_charges + data[i].sw_charges;
			mtr_rent = mtr_rent + data[i].mtr_rent;
			total_bill = total_bill + data[i].total_bill;
			net_amount = net_amount + data[i].net_amount;
			penalty = penalty + data[i].penalty;
			rebate = rebate + data[i].rebate;
			ramount = ramount + data[i].ramount;
			misc = misc + data[i].misc;
			cbalance = cbalance + data[i].cbalance;
			adv = adv + data[i].adv;
			adv_reb = adv_reb + data[i].adv_reb;
		}
		cbalance =parseFloat(cbalance).toFixed(2);
		adv =parseFloat(adv).toFixed(2);
		adv_reb =parseFloat(adv_reb).toFixed(2);
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

		var htmlStart = '<!DOCTYPE html>'
				+ '<html>'
				+ '<head>'
				+ '<meta charset="utf-8" />'
				+ '<title>Monthly Sales Summary Report</title>'
				+ '<link href="http://kendo.cdn.telerik.com/' + kendo.version + '/styles/kendo.common.min.css" rel="stylesheet" /> '
				+ '<style>'
				+ 'html { font: 9pt sans-serif; }'
				+ '.k-grid { border-top-width: auto; }'
				+ '.k-grid, .k-grid-content { height: auto !important; }'
				+ '.k-grid-content { overflow: visible !important; }'
				+ 'div.k-grid table { table-layout: 190%; width: auto !important; }'
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
				+ '<h2>' + branch
				+ ' kathmandu</h2><h3>Monthly Sales Summary Report</h3>'
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

						} else {
							var from_date = $("#from_date_nep").val();
							var to_date = $("#to_date_nep").val();
							var startDate = new Date(from_date);
							var endDate = new Date(to_date);
							if (startDate > endDate) {
								alert("To date Nepali should be greater than From Date in Nepali");
								$('#to_date_nep').val("");
							}
						}
					}

				});

	}
	function getData() {
		var from_date = $("#from_date_nep").val();
		var to_date = $("#to_date_nep").val();
		if (from_date == "") {
			alert("Please Enter From Date");
			return false;
		}
		if (to_date == "") {
			alert("Please Enter To Date");
			return false;
		}
		var startDate = new Date(from_date);
		var endDate = new Date(to_date);

		if (startDate > endDate) {
			alert("To date Nepali should be greater than From Date in Nepali");
			$('#to_date_nep').val("");
			return false;
		} else {

			$('#loading').show();
			$
					.ajax({
						type : "GET",
						url : "./monthlySalesSummaryReport/monthlySalesSummaryReportReadData",
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

			$('#loading').hide();
		}

	}
function clearButton(){
		
		window.location.href="./monthlySalesSummaryReport";
	}
</script>
<style>
</style>