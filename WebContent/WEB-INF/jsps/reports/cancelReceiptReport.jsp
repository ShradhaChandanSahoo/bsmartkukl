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
				<legend class="text-bold">Cancel Receipt Report</legend>
				<div class="row">
				
					<div class="form-group col-md-4">
						<label>From Date (Nepali) <font color="red">*</font></label>
						<div class="input-group">
							<span class="input-group-addon"><i class="icon-calendar3"></i></span>
							<input name="fromDatenep" placeholder="Select From Date..."
								id="fromDatenep" class="form-control nepali-calendar" />
						</div>
					</div>

					<div class="form-group col-md-4">
						<label>To Date (Nepali) <font color="red">*</font></label>
						<div class="input-group">
							<span class="input-group-addon"><i class="icon-calendar3"></i></span>
							<input name="toDatenep" placeholder="Select To Date..."
								id="toDatenep" class="form-control nepali-calendar" />
						</div>
					</div>

					<!-- <div class="form-group col-md-4">
						<label>Connection Category <font color="red">*</font></label> 
						<select class="select" id="category" name="category" required="required" data-placeholder="Select Category">
						<option value="" data-icon="icon-git-branch">Select
								Connection Category</option>
							<option value="Domestic" data-icon="icon-git-branch">Domestic</option>
							<option value="Government" data-icon="icon-git-commit">Government</option>
							<option value="Industry and Company" data-icon="icon-git-commit">Industry and Company</option>
						</select>
					</div> -->
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
						<span class="ladda-label">Get Data</span>
					</button>
				</div>
			


			</fieldset>
			<div class="wethear" style="overflow: scroll;">
				<fieldset class="content-group">
					<legend class="text-bold">Cancel Receipt Report</legend>

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
				 	<kendo:grid-column title="SlNo" field="slNo" width="55px" filterable="true" />
					<kendo:grid-column title="Con NO" field="conNo" width="75px" filterable="true"/>
					<kendo:grid-column title="Name" field="name" width="140px" filterable="true" groupFooterTemplate="Total :" footerTemplate="Grand&nbsp;Total:"/>
					<kendo:grid-column title="Area" field="area" width="75px" filterable="true"/>
					<kendo:grid-column title="Receipt NO" field="receipt" width="100px" filterable="true"  />
					<kendo:grid-column title="Date" field="rdate" width="80px" filterable="true"/>
					<kendo:grid-column title="Paid" field="paidAmt" width="75px" filterable="true" aggregates="sum" groupFooterTemplate="#: kendo.toString(sum, 'n2') #" footerTemplate="#:kendo.toString(paidAmt,'n2') #" />
					<kendo:grid-column title="Rebate" field="rebate" width="75px" filterable="true"  />
					<kendo:grid-column title="Penalty" field="penalty" width="80px" filterable="true"  />
					<kendo:grid-column title="Bill Amt" field="billAmt" width="80px" filterable="true"  />
					<kendo:grid-column title="Close Bal" field="closeBal" width="80px" filterable="true"  />

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
											<kendo:dataSource-schema-model-field name="conNo" type="string" />
											<kendo:dataSource-schema-model-field name="name" type="string" />
											<kendo:dataSource-schema-model-field name="area" type="string" />
											<kendo:dataSource-schema-model-field name="receipt" type="number" />
											<kendo:dataSource-schema-model-field name="rdate" type="number" />
											<kendo:dataSource-schema-model-field name="paidAmt" type="number" />
											<kendo:dataSource-schema-model-field name="rebate" type="number" />
											<kendo:dataSource-schema-model-field name="penalty" type="number" />
											<kendo:dataSource-schema-model-field name="billAmt" type="number" />
											<kendo:dataSource-schema-model-field name="closeBal" type="number" />
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
	/*  var openingBal = 0;
	 var arrears = 0;
	 var penalty= 0;
	 var totalPaid = 0;
	 var closingBal = 0;
	 var penaltyAdj = 0;
	 */
	 var paidAmt = 0;
	 //var penalty2064 = 0;
	 //var received2064 = 0;

	function dataBound(e) {
		var data = this.dataSource.view();
		/* openingBal = 0;
		arrears = 0;
		penalty = 0;
		totalPaid=0;
		closingBal = 0;
		penaltyAdj = 0;*/
		paidAmt = 0;

		for (var i = 0; i < data.length; i++) {
			paidAmt = paidAmt + data[i].paidAmt;
			/* arrears = arrears + data[i].arrears;
			penalty = penalty + data[i].penalty;
			totalPaid=totalPaid + data[i].totalPaid;
			closingBal=closingBal + data[i].closingBal;
			penaltyAdj=penaltyAdj + data[i].penaltyAdj; */
			
		}
		paidAmt = parseFloat(paidAmt).toFixed(2);
		/* arrears = parseFloat(arrears).toFixed(2);
		penalty=parseFloat(penalty).toFixed(2);
		totalPaid = parseFloat(totalPaid).toFixed(2);
		closingBal=parseFloat(closingBal).toFixed(2);
		penaltyAdj=parseFloat(penaltyAdj).toFixed(2);  */
		
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
		var category = $("#category").val();
		var gridElement = $('#categoryGrid'), printableContent = '', win = window
				.open('', '', 'width=auto, height=900'), doc = win.document
				.open();
		var branch="${BranchName}";
		var htmlStart = '<!DOCTYPE html>'
				+ '<html>'
				+ '<head>'
				+ '<meta charset="utf-8" />'
				+ '<title>Cancel Receipt Report</title>'
				+ '<link href="http://kendo.cdn.telerik.com/' + kendo.version + '/styles/kendo.common.min.css" rel="stylesheet" /> '
				+ '<style>'
				+ 'html { font: 10pt sans-serif; }'
				+ '.k-grid { border-top-width: auto; }'
				+ '.k-grid, .k-grid-content { height: auto !important; }'
				+ '.k-grid-content { overflow: visible !important; }'
				+ 'div.k-grid table { table-layout: 110%; width: 100% !important; }'
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
				+ '<h2>'+branch+' kathmandu</h2><h3>Cancel Receipt Report</h3>'
				+ '<h4>From :&nbsp;&nbsp;' + frm
				+ '&nbsp;&nbsp;&nbsp;To :&nbsp;&nbsp;' + to + '&nbsp;&nbsp;</h4></center>';

				
		/* var summary='<br><br><br><div><table align="center" style="width:50%" border="1" rules="all" cellpadding="10">'
					+'<tr><td></td><th>Total Revenue</th><th >Penalty</th><th>Total Receivd Amt.</th></tr><tr style="text-align: center;">'
					+'<th style="text-align: end;">Current: </th><td>'+parseFloat(currentRevenue).toFixed(2)+'</td><td>'+parseFloat(currentPenalty).toFixed(2)+'</td><td>'+parseFloat(currentReceived).toFixed(2)+'</td></tr><tr style="text-align: center;">'
					+'<th style="text-align: end;">Upto 2064-10: </th><td>'+parseFloat(total).toFixed(2)+'</td><td>'+parseFloat(penalty).toFixed(2)+'</td><td>'+parseFloat(grandTotal).toFixed(2)+'</td></tr><tr style="text-align: center;">'
					+'<th style="text-align: end;">Total: </th><td>'+parseFloat(t_rev).toFixed(2)+'</td>'
					+'<td>'+parseFloat(t_pen).toFixed(2)+'</td>'
					+'<td>'+parseFloat(t_amt).toFixed(2)+'</td></tr></table></div><br><br>'; */
				
				
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
		//var category = $("#category").val();
		if (startDate > endDate) {
			alert("To date Nepali should be greater than From Date in Nepali");
			$('#toDatenep').val("");
			return false;
		} 
		/* if (category == "" || category ==null) {
			alert("Please Select Category");
			return false;
		} */
		else {
			$('#loading').show();
			$.ajax({
				type : "GET",
				url : "./cancelReceiptReport/cancelReceiptReportRead",
				dataType : "JSON",
				async : false,
				data : {
					from_date : from_date,
					to_date : to_date,
					/* category : category, */
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
		
		window.location.href="./cancelReceiptReport";
	}
	
</script>
<style>
</style>