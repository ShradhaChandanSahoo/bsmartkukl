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
<c:url value="./dailyRevenueReport" var="readCategoryUrl" />


<script src="//cdnjs.cloudflare.com/ajax/libs/jszip/2.4.0/jszip.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		
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
		$('#fromDatenep').nepaliDatePicker({
			dateFormat : "%D, %M %d, %y",
			npdMonth : true,
			npdYear : true,
			onChange : function() {
				var rdngdtnep = $('#fromDatenep').val();
				getEngDate(rdngdtnep, 1);
			}
		});

		$('#toDatenep').nepaliDatePicker({
			dateFormat : "%D, %M %d, %y",
			npdMonth : true,
			npdYear : true,
			onChange : function() {
				var rdngdtnep = $('#toDatenep').val();
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
				<legend class="text-bold">Ward Wise Daily Revenue Report</legend>
				<div class="row">
					<div class="form-group col-md-4">
						<label>From Date (Nepali)</label>
						<div class="input-group">
							<span class="input-group-addon"><i class="icon-calendar3"></i></span>
							<input name="fromDatenep" placeholder="Select From Date..."
								id="fromDatenep" class="form-control nepali-calendar" />
						</div>
					</div>

					<div class="form-group col-md-4">
						<label>To Date (Nepali)</label>
						<div class="input-group">
							<span class="input-group-addon"><i class="icon-calendar3"></i></span>
							<input name="toDatenep" placeholder="Select To Date..."
								id="toDatenep" class="form-control nepali-calendar" />
						</div>
					</div>

					<div class="form-group col-md-4">
						<label>Counter No</label> <select class="select" id="counter_no"
							name="counter_no" required="required"
							data-placeholder="Select Counter No">
							<option value="All">All</option>
							<c:forEach var="data" items="${counter}">
								<option value="${data.counterNumber}">${data.counterNumber}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="text-center">
					<button type="button" class="btn bg-teal btn-ladda"
						onclick="clearButton();">
						<span class="ladda-label">Clear</span>
					</button>
					<label>&nbsp;</label>
					<button type="button" class="btn bg-teal btn-ladda"
						onclick="return getDailyRevenueData()" data-style="expand-right"
						data-spinner-size="20">
						<span class="ladda-label">Get Daily Revenue Data</span>
					</button>
				</div>




			</fieldset>
			<div class="wethear" style="overflow: scroll;">
				<fieldset class="content-group">
					<legend class="text-bold">Ward Wise Daily Revenue Report</legend>

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
			<kendo:grid-editable mode="popup" confirmation="Are you sure you want to remove this item?" />
				<kendo:grid-columns>
				 	<kendo:grid-column title="Date" field="rdate" width="90px" filterable="true" groupFooterTemplate="Total :" footerTemplate="Grand&nbsp;Tot:" />
					<kendo:grid-column title="Ward" field="ward_no" width="65px" filterable="true" groupFooterTemplate="Total :" footerTemplate="Grand&nbsp;Tot:" />
					<kendo:grid-column title="OB" field="opening_arrears" width="95px" filterable="true" aggregates="sum" groupFooterTemplate="#: kendo.toString(sum, 'n2') #" footerTemplate="#:kendo.toString(opening_arrears,'n2') #" />
					<kendo:grid-column title="WC" field="water_charge" width="95px" filterable="true" aggregates="sum" groupFooterTemplate="#: kendo.toString(sum, 'n2') #" footerTemplate="#:kendo.toString(water_charge,'n2') #" />
					<kendo:grid-column title="SC" field="sw_charge" width="95px" filterable="true" aggregates="sum" groupFooterTemplate="#: kendo.toString(sum, 'n2') #" footerTemplate="#:kendo.toString(sw_charge,'n2') #" />
					<kendo:grid-column title="MR" field="meter_rent" width="75px" filterable="true" aggregates="sum" groupFooterTemplate="#: kendo.toString(sum, 'n2') #" footerTemplate="#:kendo.toString(meter_rent,'n2') #" />
					<kendo:grid-column title="Net" field="net_ammount" width="100px" filterable="true" aggregates="sum" groupFooterTemplate="#: kendo.toString(sum, 'n2') #" footerTemplate="#:kendo.toString(net_ammount,'n2') #" />
					<kendo:grid-column title="Pnlty" field="penalty" width="85px" filterable="true" aggregates="sum" groupFooterTemplate="#: kendo.toString(sum, 'n2') #" footerTemplate="#:kendo.toString(penalty,'n2') #" />
					<kendo:grid-column title="Rbt" field="rebate" width="85px" filterable="true" aggregates="sum" groupFooterTemplate="#: kendo.toString(sum, 'n2') #" footerTemplate="#:kendo.toString(rebate, 'n2') #" />
					<kendo:grid-column title="Misc" field="miscellaneous" width="80px" filterable="true" aggregates="sum" groupFooterTemplate="#: kendo.toString(sum, 'n2') #" footerTemplate="#:kendo.toString(miscellaneous, 'n2') #" />
					<kendo:grid-column title="ToBePaid" field="total_toBePaid" width="95px" filterable="true" aggregates="sum" groupFooterTemplate="#: kendo.toString(sum, 'n2') #" footerTemplate="#:kendo.toString(total_toBePaid,'n2') #" />
					<kendo:grid-column title="TotlPaid" field="total_paid" filterable="true" width="95px" aggregates="sum" groupFooterTemplate="#: kendo.toString(sum, 'n2') #" footerTemplate="#:kendo.toString(total_paid,'n2','0.00') #" />
					<kendo:grid-column title="Advance" field="advance" filterable="true" width="95px" aggregates="sum" groupFooterTemplate="#: kendo.toString(sum, 'n2') #" footerTemplate="#:kendo.toString(advance,'n2','0.00') #" />
					<kendo:grid-column title="AdvRbt" field="advance_rebate" filterable="true" width="95px" aggregates="sum" groupFooterTemplate="#: kendo.toString(sum, 'n2') #" footerTemplate="#:kendo.toString(advance_rebate,'n2','0.00') #" />
					<kendo:grid-column title="CloBal" field="closing_balance" filterable="true" width="95px" aggregates="sum" groupFooterTemplate="#: kendo.toString(sum, 'n2') #" footerTemplate="#:kendo.toString(closing_balance, 'n2','0.00') #" />

							</kendo:grid-columns>
							<kendo:dataSource pageSize="100">
								<kendo:dataSource-transport>
									<kendo:dataSource-transport-read url="${readCategoryUrl}"
										dataType="json" type="POST" contentType="application/json" />
								</kendo:dataSource-transport>
								<kendo:dataSource-schema>
									<kendo:dataSource-schema-model id="id">
										<kendo:dataSource-schema-model-fields>
											<%-- <kendo:dataSource-schema-model-field name="slNo" type="number"/> --%>
											<kendo:dataSource-schema-model-field name="rdate" type="number" />
											<kendo:dataSource-schema-model-field name="ward_no" type="number" />
											<kendo:dataSource-schema-model-field name="opening_arrears" type="number" />
											<kendo:dataSource-schema-model-field name="water_charge" type="number" />
											<kendo:dataSource-schema-model-field name="sw_charge" type="number" />
											<kendo:dataSource-schema-model-field name="meter_rent" type="number" />
											<kendo:dataSource-schema-model-field name="net_ammount" type="number" />
											<kendo:dataSource-schema-model-field name="penalty" type="number" />
											<kendo:dataSource-schema-model-field name="rebate" type="number" />
											<kendo:dataSource-schema-model-field name="miscellaneous" type="number" />
											<kendo:dataSource-schema-model-field name="total_toBePaid" type="number" />
											<kendo:dataSource-schema-model-field name="total_paid" type="number" />
											<kendo:dataSource-schema-model-field name="advance" type="number" />
											<kendo:dataSource-schema-model-field name="advance_rebate" type="number" />
											<kendo:dataSource-schema-model-field name="closing_balance" type="number" />
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
	<div id="loading" hidden="true" style="text-align: center;">
		<h3 id="loadingText">Loading..... Please wait.</h3>
		<img alt="image" src="./resources/images/loading.gif"
			style="width: 3%; height: 3%;">
	</div>
</div>

<script>
<!--  ward_no,opening_arrears,water_charge,sw_charge,meter_rent,penalty,rebate,adv_payment,next_payment,arrear_balance -->
	var ward_no = 0;
	var opening_arrears = 0;
	var water_charge = 0;
	var sw_charge = 0;
	var meter_rent = 0;
	var net_ammount = 0;
	var penalty = 0;
	var rebate = 0;
	var miscellaneous = 0;
	var total_toBePaid = 0;
	var total_paid = 0;
	var advance = 0;
	var advance_rebate = 0;
	var closing_balance = 0;

	function dataBound(e) {
		var data = this.dataSource.view();
		ward_no = 0;
		opening_arrears = 0;
		water_charge = 0;
		sw_charge = 0;
		meter_rent = 0;
		net_ammount = 0;
		penalty = 0;
		rebate = 0;
		miscellaneous = 0;
		total_toBePaid = 0;
		total_paid = 0;
		advance = 0;
		advance_rebate = 0;
		closing_balance = 0;

		for (var i = 0; i < data.length; i++) {
			ward_no = ward_no + data[i].ward_no;
			opening_arrears = opening_arrears + data[i].opening_arrears;
			water_charge = water_charge + data[i].water_charge;
			sw_charge = sw_charge + data[i].sw_charge;
			meter_rent = meter_rent + data[i].meter_rent;
			net_ammount = net_ammount + data[i].net_ammount;
			penalty = penalty + data[i].penalty;
			rebate = rebate + data[i].rebate;
			miscellaneous = miscellaneous + data[i].miscellaneous;
			total_toBePaid = total_toBePaid + data[i].total_toBePaid;
			total_paid = total_paid + data[i].total_paid;
			advance = advance + data[i].advance;
			advance_rebate = advance_rebate + data[i].advance_rebate;
			closing_balance = closing_balance + data[i].closing_balance;
		}
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
		var frm = $('#fromDatenep').val();
		var to = $('#toDatenep').val();

		var gridElement = $('#categoryGrid'), printableContent = '', win = window
				.open('', '', 'width=auto, height=900'), doc = win.document
				.open();
		var branch="${BranchName}";
		var htmlStart = '<!DOCTYPE html>'
				+ '<html>'
				+ '<head>'
				+ '<meta charset="utf-8" />'
				+ '<title>Ward Wise Daily Revenue Report</title>'
				+ '<link href="http://kendo.cdn.telerik.com/' + kendo.version + '/styles/kendo.common.min.css" rel="stylesheet" /> '
				+ '<style>'
				+ 'html { font: 9pt sans-serif; }'
				+ '.k-grid { border-top-width: auto; }'
				+ '.k-grid, .k-grid-content { height: auto !important; }'
				+ '.k-grid-content { overflow: visible !important; }'
				+ 'div.k-grid table { table-layout: 110%; width:100% !important; }'
				+ '.k-grid .k-grid-header th { border-top: 1px solid; }'
				+ '.k-grid tr td {border-bottom: 1px solid black;}'
				+ '.k-grid-toolbar, .k-grid-pager > .k-link { display: none; }'
				+'.k-grid .k-grouping-header { display: none;}'
				+'.k-pager-wrap, .k-grid-pager{ display: none;}'
				+'.k-grid td { line-height: 1em;}'
				+ '.k-grid, .k-grid-footer { height: auto !important; width: 100% !important; padding-right: 0px !important; }'
				+ '</style>'
				+ '</head>'
				+ '<body> <center style="line-height: 10px;"><h1>Kathmandu Upatyaka Khanepani Limited</h1>'
				+ '<h2>'+branch+' kathmandu</h2><h3>Ward Wise Revenue Report</h3>'
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
							var from_date = $("#fromDatenep").val();
							var to_date = $("#toDatenep").val();
							var startDate = new Date(from_date);
							var endDate = new Date(to_date);
							if (startDate > endDate) {
								alert("To date Nepali should be greater than From Date in Nepali");
								$('#toDatenep').val("");
							}
						}
					}

				});

	}
	function getDailyRevenueData() {
		var from_date = $("#fromDatenep").val();
		var to_date = $("#toDatenep").val();
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
		var counterNo = $("#counter_no").val();
		if (startDate > endDate) {
			alert("To date Nepali should be greater than From Date in Nepali");
			$('#toDatenep').val("");
			return false;
		} else {
			$('#loading').show();
			$.ajax({
				type : "GET",
				url : "./dailyRevenueReport/dailyRevenueReportRead",
				dataType : "JSON",
				async : false,
				data : {
					from_date : from_date,
					to_date : to_date,
					counter_no : counterNo,
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
		
		window.location.href="./dailyRevenueReport";
	}
	
</script>
<style>
</style>