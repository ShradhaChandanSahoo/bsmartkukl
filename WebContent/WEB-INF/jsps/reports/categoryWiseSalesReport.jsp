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
<c:url value="/categoryWiseSalesReport/categoryWiseSalesReportRead"
	var="readCategoryUrl" />


<script src="//cdnjs.cloudflare.com/ajax/libs/jszip/2.4.0/jszip.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		
		$('#umbillreport').show();
		$('#reportsbilling').addClass('active');
		
		var activeMod="${activeModulesNew}";
		var module="Billing Reports";
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
				<legend class="text-bold">Category Wise Sales Report</legend>
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
								<span class="ladda-label">Get Data</span>
							</button>
						</div>
					</div>

				</div>
			</fieldset>
			<div class="wethear" style="overflow: scroll;">
				<fieldset class="content-group">
					<legend class="text-bold">Category Wise Sales Report</legend>

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
								<kendo:grid-column title="Sl.No" field="slNo" width="100px"
									filterable="true" groupFooterTemplate="Total  "
									footerTemplate="Total" />
								<kendo:grid-column title="Con Type" field="con_type"
									width="120px" filterable="true" />
								<kendo:grid-column title="Category" field="con_category"
									width="180px" filterable="true" />
								<kendo:grid-column title="Water Cost" field="water_charges"
									width="130px" filterable="true" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(water_charges, 'n2')#" />
								<kendo:grid-column title="Sewerage Cost" field="sw_charges"
									width="170px" filterable="true" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:sw_charges#" />
								<kendo:grid-column title="Meter Rent" field="mtr_rent"
									width="130px" filterable="true" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:mtr_rent#" />
								 <kendo:grid-column title="Add Pen Cost" field="penalty"
									width="145px" filterable="true" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:penalty#" /> 
								<kendo:grid-column title="Total Bill" field="total_bill"
									width="120px" filterable="true" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:total_bill#" />
								<%-- <kendo:grid-column title="Open Bal." field="arrears"
									filterable="true" width="130px" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:arrears#" />
								<kendo:grid-column title="NET(OB+TB)" field="net_amount"
									filterable="true" width="160px" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:net_amount#" /> --%>

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
											<kendo:dataSource-schema-model-field name="con_type"
												type="string" />
											<kendo:dataSource-schema-model-field name="con_category"
												type="string" />
											<kendo:dataSource-schema-model-field name="water_charges"
												type="number" />
											<kendo:dataSource-schema-model-field name="sw_charges"
												type="number" />
											<kendo:dataSource-schema-model-field name="id" type="number" />
											<kendo:dataSource-schema-model-field name="mtr_rent"
												type="number" />
											 <kendo:dataSource-schema-model-field name="penalty"
												type="number" />
											<kendo:dataSource-schema-model-field name="total_bill"
												type="number" />
											<%-- <kendo:dataSource-schema-model-field name="arrears"
												type="number" />
											<kendo:dataSource-schema-model-field name="net_amount"
												type="number" /> --%>
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
	var water_charges = 0;
	var sw_charges = 0;
	var mtr_rent = 0;
	var arrears = 0;
	var net_amount = 0;
	var penalty = 0;
	var total_bill = 0;

	var frndate = "";
	var tondate = "";
	var branch = "";

	var branch = "${BranchName}";

	var monthyearnep1 = "";

	function dataBound(e) {
		var data = this.dataSource.view();
		water_charges = 0;
		sw_charges = 0;
		mtr_rent = 0;
		arrears = 0;
		net_amount = 0;
		penalty = 0;
		total_bill = 0;

		for (var i = 0; i < data.length; i++) {
			water_charges = water_charges + data[i].water_charges;
			sw_charges = sw_charges + data[i].sw_charges;
			mtr_rent = mtr_rent + data[i].mtr_rent;
			total_bill = total_bill + data[i].total_bill;
			/* arrears = arrears + data[i].arrears;
			net_amount = net_amount + data[i].net_amount; */
			penalty = penalty + data[i].penalty;;
		}

		total_bill = parseFloat(total_bill).toFixed(2);
		/* net_amount = parseFloat(net_amount).toFixed(2);
		arrears = parseFloat(arrears).toFixed(2); */
		water_charges = parseFloat(water_charges).toFixed(2);
		sw_charges = parseFloat(sw_charges).toFixed(2);
		mtr_rent = parseFloat(mtr_rent).toFixed(2);
		penalty = parseFloat(penalty).toFixed(2);
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
		var gridElement = $('#categoryGrid'), printableContent = '', win = window
				.open('', '', 'width=550, height=500'), doc = win.document
				.open();

		var htmlStart = '<!DOCTYPE html>'
				+ '<html>'
				+ '<head>'
				+ '<meta charset="utf-8" />'
				+ '<title>Kendo UI Grid</title>'
				+ '<link href="http://kendo.cdn.telerik.com/' + kendo.version + '/styles/kendo.common.min.css" rel="stylesheet" /> '
				+ '<style>'
				+ 'html { font: 10pt sans-serif; }'
				+ '.k-grid { border-top-width: auto; }'
				+ '.k-grid, .k-grid-content { height: auto !important; }'
				+ '.k-grid-content { overflow: visible !important; }'
				+ 'div.k-grid table { table-layout: auto; width: 100% !important; }'
				+ '.k-grid .k-grid-header th { border-top: 1px solid; }'
				+ '.k-grid tr td {border-bottom: 1px solid black;}'
				+ '.k-grid-toolbar, .k-grid-pager > .k-link { display: none; }'
				+'.k-grid .k-grouping-header { display: none;}'
				+'.k-pager-wrap, .k-grid-pager{ display: none;}'
				+'.k-grid td { line-height: 1em;}'
				+ '.k-grid, .k-grid-footer { height: auto !important; width: 100% !important; padding-right: 0px !important; }'
				+ '</style>'
				+ '</head>'
				+ '<body> <center style="line-height: 10px;"><h1>Kathmandu Upatyaka Khanepani Limited</h1></center><center><h2> '
				+ branch
				+ '  Kathmandu</h2></center><center><h3>Category Wise Sales Report</h3></center><center><h3>From : '
				+ frndate + '     To : ' + tondate + '</h3></center>';

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

							var mnthyrnep = to_date.split("-");
							monthyearnep1 = mnthyrnep[0] + "" + mnthyrnep[1];

						}
					}

				});

	}
	function getData() {
		var from_date = $("#from_date_nep").val();
		var to_date = $("#to_date_nep").val();
		var monthyearnep = monthyearnep1;

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

			frndate = from_date;
			tondate = to_date;

			$('#loading').show();
			$
					.ajax({
						type : "GET",
						url : "./categoryWiseSalesReport/categoryWiseSalesReportReadData",
						dataType : "JSON",
						async : false,
						data : {
							from_date : from_date,
							to_date : to_date,
							monthyearnep : monthyearnep,
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
		
		window.location.href="./categoryWiseSalesReport";
	}
</script>
<style>
</style>