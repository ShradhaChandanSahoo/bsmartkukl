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
				<legend class="text-bold">Ward Wise Customer's Billing Report</legend>
				<div>

					<div class="col-md-2">
						<label>Ward No<font color="red">*</font></label><select
							data-placeholder="Select" class="select" id="wardNo"
							name="wardNo" required="required">
							<option value="" data-icon="icon-git-branch">Select</option>

							<c:forEach items="${wardNoList}" var="ward">
								<option value="${ward.wardNo}">${ward.wardNo}</option>
							</c:forEach>

						</select>
					</div>


					<div class="col-md-2">
						<label>Year<font color="red">*</font></label><select
							data-placeholder="Select" class="select" id="yearnep"
							name="yearnep" required="required">
							<option value="" data-icon="icon-git-branch">Select</option>

							<c:forEach items="${yearList}" var="ward">
								<option value="${ward.year}">${ward.year}</option>
							</c:forEach>

						</select>
					</div>

					<div class="form-group col-md-2">
						<label>Month<font color="red">*</font></label> <select
							data-placeholder="Select" class="select" name="month" id="month">
							<option value="" data-icon="icon-git-branch">Select</option>
							<option value="01" data-icon="icon-git-branch">Baisakh</option>
							<option value="02" data-icon="icon-git-branch">Jestha</option>
							<option value="03" data-icon="icon-git-branch">Asadh</option>
							<option value="04" data-icon="icon-git-branch">Shrawan</option>
							<option value="05" data-icon="icon-git-branch">Bhadra</option>
							<option value="06" data-icon="icon-git-branch">Ashwin</option>
							<option value="07" data-icon="icon-git-branch">Kartik</option>
							<option value="08" data-icon="icon-git-branch">Mangshir</option>
							<option value="09" data-icon="icon-git-branch">Poush</option>
							<option value="10" data-icon="icon-git-branch">Magh</option>
							<option value="11" data-icon="icon-git-branch">Falgun</option>
							<option value="12" data-icon="icon-git-branch">Chaitra</option>

						</select>

					</div>
					<div class="form-group col-md-3">
						<label>Connection Category <font color="red">*</font></label> <select
							data-placeholder="Select" class="select" name="con_category"
							id="con_category">

							<option value="Domestic" data-icon="icon-git-branch">Domestic</option>
							<option value="Government" data-icon="icon-git-commit">Government</option>
							<option value="Industry and Company" data-icon="icon-git-commit">Industry
								and Company</option>
						</select>
					</div>

					<div class="col-md-3">
						<div class="form-group" style="padding-top: 27px;">
							<button type="button" class="btn bg-teal btn-ladda"
								onclick="clearButton();">
								<span class="ladda-label">Clear</span>
							</button>
							<label>&nbsp;</label>
							<button type="button" class="btn bg-teal btn-ladda"
								onclick="return getData()" data-style="expand-right"
								data-spinner-size="20">
								<span class="ladda-label">Get Consumer List</span>
							</button>
						</div>
					</div>
				</div>
			</fieldset>




			</fieldset>
			<div class="wethear" style="overflow: scroll;">
				<fieldset class="content-group">
					<legend class="text-bold">Ward Wise Customer's Billing Report</legend>

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
				 	<kendo:grid-column title="Sl No" field="slNo" width="75px" filterable="true" />
					<kendo:grid-column title="Con No" field="CONNECTION_NO" width="75px" filterable="true" />
					<kendo:grid-column title="Name" field="name" width="180px" filterable="true" groupFooterTemplate="Total :" footerTemplate="Grand&nbsp;Total :"/>
					<kendo:grid-column title="Area" field="area" width="85px" filterable="true"/>
					<kendo:grid-column title="Pipe" field="pipe_size" width="80px" filterable="true"/>
					<kendo:grid-column title="Arrear" field="arrears" width="100px" filterable="true" aggregates="sum" groupFooterTemplate="#: kendo.toString(sum, 'n2') #" footerTemplate="#:kendo.toString(arrears,'n2') #" />
					<kendo:grid-column title="WC" field="water_ch" width="85px" filterable="true" aggregates="sum" groupFooterTemplate="#: kendo.toString(sum, 'n2') #" footerTemplate="#:kendo.toString(water_ch,'n2') #" />
					<kendo:grid-column title="SC" field="sw_ch" width="85px" filterable="true" aggregates="sum" groupFooterTemplate="#: kendo.toString(sum, 'n2') #" footerTemplate="#:kendo.toString(sw_ch,'n2') #" />
					<kendo:grid-column title="MR" field="mtr_rent" width="85px" filterable="true" aggregates="sum" groupFooterTemplate="#: kendo.toString(sum, 'n2') #" footerTemplate="#:kendo.toString(mtr_rent,'n2') #" />
					<kendo:grid-column title="Net" field="net_amt" width="100px" filterable="true" aggregates="sum" groupFooterTemplate="#: kendo.toString(sum, 'n2') #" footerTemplate="#:kendo.toString(net_amt,'n2') #" />
							</kendo:grid-columns>
							<kendo:dataSource pageSize="100">
								<kendo:dataSource-transport>
									<kendo:dataSource-transport-read url="${readCategoryUrl}"
										dataType="json" type="POST" contentType="application/json" />
								</kendo:dataSource-transport>
								<kendo:dataSource-schema>
									<kendo:dataSource-schema-model id="id">
										<kendo:dataSource-schema-model-fields>
											 <kendo:dataSource-schema-model-field name="slNo" type="number"/> 
											<kendo:dataSource-schema-model-field name="CONNECTION_NO" type="number" />
											<kendo:dataSource-schema-model-field name="name" type="string" />
											<kendo:dataSource-schema-model-field name="area" type="string" />
											<kendo:dataSource-schema-model-field name="pipe_size" type="number" />
											<kendo:dataSource-schema-model-field name="arrears" type="number" />
											<kendo:dataSource-schema-model-field name="water_ch" type="number" />
											<kendo:dataSource-schema-model-field name="sw_ch" type="number" />
											<kendo:dataSource-schema-model-field name="mtr_rent" type="number" />
											<kendo:dataSource-schema-model-field name="net_amt" type="number" />
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
	 var arrears = 0;
	 var water_ch = 0;
	 var sw_ch = 0;
	 var mtr_rent=0;
	 var net_amt = 0;

	function dataBound(e) {
		var data = this.dataSource.view();
		arrears = 0;
		water_ch = 0;
		sw_ch = 0;
		net_amt=0;
		mtr_rent=0;
		for (var i = 0; i < data.length; i++) {
			arrears = arrears + data[i].arrears;
			water_ch = water_ch + data[i].water_ch;
			sw_ch = sw_ch + data[i].sw_ch;
			net_amt=net_amt + data[i].net_amt;
			mtr_rent=mtr_rent + data[i].mtr_rent;
		}
		arrears = parseFloat(arrears).toFixed(2);
		water_ch = parseFloat(water_ch).toFixed(2);
		sw_ch = parseFloat(sw_ch).toFixed(2);
		net_amt=parseFloat(net_amt).toFixed(2);
		mtr_rent=parseFloat(mtr_rent).toFixed(2);
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
		var wardNo = $("#wardNo").val();
		var yearnep = $("#yearnep").val();
		var month = $("#month").val();
		var con_category = $("#con_category").val();
		var myn=yearnep+""+month;
		var gridElement = $('#categoryGrid'), printableContent = '', win = window
				.open('', '', 'width=auto, height=900'), doc = win.document
				.open();
		var branch="${BranchName}";
		var htmlStart = '<!DOCTYPE html>'
			+ '<html>'
			+ '<head>'
			+ '<meta charset="utf-8" />'
			+ '<title>Ward Wise Customer\'s Billing Report</title>'
			+ '<link href="http://kendo.cdn.telerik.com/' + kendo.version + '/styles/kendo.common.min.css" rel="stylesheet" /> '
			+ '<style>'
			+ 'html { font: 10pt sans-serif; }'
			+ '.k-grid { border-top-width: auto; }'
			+ '.k-grid, .k-grid-content { height: auto !important;}'
			+ '.k-grid-content { overflow: visible !important; }'
			+ 'div.k-grid table { table-layout: 100%; width: 100% !important;}'
			+ '.k-grid .k-grid-header th { border-top: 1px solid; border-all: 1px solid !important; }'
			+ '.k-grid tr td {border-bottom: 1px solid black;}'
			+ '.k-grid-toolbar, .k-grid-pager > .k-link { display: none; }'
			+ '.k-grid .k-grouping-header { display: none;}'
			+ '.k-pager-wrap, .k-grid-pager{ display: none;}'
			+ '.k-grid td { line-height: 1em;}'
			+ '.k-grid-toolbar, .k-grid-pager > .k-link { display: none; }'
			+ '.k-grid, .k-grid-footer { height: auto !important; width: 100% !important; padding-right: 0px !important; }'
			+'.k-pager-wrap, .k-grid-pager{ display: none;}'
			+ '</style>'
			+ '</head>'
			+ '<body> <center style="line-height: 10px;"><h2>Kathmandu Upatyaka Khanepani Limited</h2>'
			+ '<h3>'
			+ branch
			+ ' kathmandu</h2><h3>Ward Wise Customer\'s Billing Report</h3>'
			+ '<h4>Ward Number :&nbsp;&nbsp;'
			+ wardNo
			+ '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;MonthYear :&nbsp;&nbsp;'
			+ myn
			+ '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Category :&nbsp;'
			+ category + '&nbsp;&nbsp</h4></center>';

				
					
				
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

		doc.write(htmlStart + printableContent+ htmlEnd);
		doc.close();
		win.print();
	}
	$("#categoryGrid").on("click", ".k-grid-printGrid", function(e) {
		printGrid();
	});

	
	function getData() {

		var wardNo = $("#wardNo").val();
		var yearnep = $("#yearnep").val();
		var month = $("#month").val();
		var con_category = $("#con_category").val();
		var myn=yearnep+""+month;
		if (wardNo == "") {
			alert("Please select ward number");
			return false;
		} else if (yearnep == "") {
			alert("Please select Year");
			return false;
		} else if (month == "") {
			alert("Please select Month");
			return false;
		} else if (con_category == "") {
			alert("Please select Category");
			return false;
		} else {

			wardnumber = wardNo;
			myn = myn;
			category = con_category;
			$("#loading").show();

			$.ajax({
				type : "GET",
				url : "./wardWiseCustomerBillingReport/wardWiseCustomerBillingReportRead",
				dataType : "JSON",
				async : false,
				data : {
					wardNo : wardNo,
					myn : myn,
					category : category
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
		
		window.location.href="./wardWiseCustomerBillingReport";
	}
	
</script>
<style>
</style>