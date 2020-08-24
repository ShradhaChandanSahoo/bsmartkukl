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
				<legend class="text-bold">Abnormal Billing Report</legend>
				<div>
					<div class="col-md-3">
						<label>Year <font color="red">*</font></label>
						<select data-placeholder="Select" class="select"  id="yearnep" name="yearnep" required="required">
						    <option value="" data-icon="icon-git-branch">Select</option>
							
							<c:forEach items="${yearList}" var="ward">
							<option value="${ward.year}">${ward.year}</option>
						   </c:forEach>
							
						</select>
				</div>
				
				<div class="form-group col-md-3">
					<label>Month <font color="red">*</font></label>
						
						<select data-placeholder="Select" class="select"  name="month" id="month" required="required"> 
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
				
					<div class="col-md-2">
						<div class="form-group">
							<label>Difference&nbsp;<font color="red">*</font></label>
							<input type="text" name="diff" id="diff"
								class="form-control" required="required"
								placeholder="Difference > ">
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
								<span class="ladda-label">Get Report</span>
							</button>
						</div>
					</div>

				</div>
			</fieldset>
			<div class="wethear" style="overflow: scroll;">
				<fieldset class="content-group">
					<legend class="text-bold">Abnormal Billing Report</legend>

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
									filterable="true" />
									
								<kendo:grid-column title="ConNo" field="connection_No" width="100px"
									filterable="true"/>
								
								<kendo:grid-column title="Current Bill" field="current_bill_amt" width="90px"
									filterable="true"/>
										
								<kendo:grid-column title="Avg Bill" field="avg_bill_amt" width="90px" filterable="true"/>
								<kendo:grid-column title="Difference" field="diff" width="90px" filterable="true"/>
									
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
											<kendo:dataSource-schema-model-field name="connection_No"
												type="string" />
											<kendo:dataSource-schema-model-field name="current_bill_amt"
												type="number" />
											<kendo:dataSource-schema-model-field name="avg_bill_amt"
												type="number" />
											<kendo:dataSource-schema-model-field name="diff"
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
	/* var opening_adv = 0;
	var water_charges = 0;
	var sw_charges = 0;
	var mtr_rent = 0;
	var total_bill = 0;
	var advance = 0;
	var advance_rebate = 0;
	var closing_adv = 0; */
	
	var branch = "${BranchName}";
	
	function dataBound(e) {
		
		var data = this.dataSource.view();
		/* opening_adv = 0;
		water_charges = 0;
		sw_charges = 0;
		mtr_rent = 0;
		total_bill = 0;
		advance = 0;
		advance_rebate = 0;
		closing_adv = 0; */
		
		/* for (var i = 0; i < data.length; i++) {
			opening_adv = parseFloat(opening_adv) + parseFloat(data[i].opening_adv);
			water_charges = parseFloat(water_charges) + parseFloat(data[i].water_charges);
			sw_charges = parseFloat(sw_charges) + parseFloat(data[i].sw_charges);
			mtr_rent = parseFloat(mtr_rent) + parseFloat(data[i].mtr_rent);
			total_bill = total_bill + data[i].total_bill;
			advance = parseFloat(advance) + parseFloat(data[i].advance);
			advance_rebate = advance_rebate + data[i].advance_rebate;
			closing_adv = closing_adv + data[i].closing_adv;
			
		}
		opening_adv =parseFloat(opening_adv).toFixed(2);
		water_charges =parseFloat(water_charges).toFixed(2);
		sw_charges =parseFloat(sw_charges).toFixed(2);
		mtr_rent =parseFloat(mtr_rent).toFixed(2);
		total_bill =parseFloat(total_bill).toFixed(2);
		advance =parseFloat(advance).toFixed(2);
		advance_rebate =parseFloat(advance_rebate).toFixed(2);
		closing_adv =parseFloat(closing_adv).toFixed(2); */
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
		
		var yearnep = $("#yearnep").val();
		var month = $("#month").val();
        var mnyr=yearnep+""+month;
		var gridElement = $('#categoryGrid'), printableContent = '', win = window
				.open('', '', 'width=auto, height=900'), doc = win.document
				.open();

		var htmlStart = '<!DOCTYPE html>'
				+ '<html>'
				+ '<head>'
				+ '<meta charset="utf-8" />'
				+ '<title>Abnormal Billing Report</title>'
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
				+ '.k-grid .k-grouping-header { display: none;}'
				+ '.k-grid td { line-height: 1em;}'
				+ '.k-pager-wrap, .k-grid-pager{ display: none;}'
				+ '.k-grid, .k-grid-footer { height: auto !important; width: 100% !important; padding-right: 0px !important; }'
				+ '</style>'
				+ '</head>'
				+ '<body> <center style="line-height: 10px;"><h1>Kathmandu Upatyaka Khanepani Limited</h1>'
				+ '<h2>' + branch
				+ ' kathmandu</h2><h3>Abnormal Billing Report</h3>'
				+ '<h4>Month Year :&nbsp;&nbsp;' + mnyr+ '</h4></center>';

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


	function getData() {
		var yearnep = $("#yearnep").val();
		var month = $("#month").val();
		var diff= $("#diff").val();
		if (yearnep == "") {
			alert("Please Select Year");
			return false;
		}
		else if (month == "") {
			alert("Please Select Month");
			return false;
		}
		else if (diff == "") {
			alert("Please Enter Difference");
			return false;
		}
	    else {
			
	    	var monthyear=yearnep+""+month;
			$('#loading').show();
			$
					.ajax({
						type : "GET",
						url : "./abnormalBillingReport/getAbnormalBillingReport",
						dataType : "JSON",
						async : false,
						
						data : {
							monthyear : monthyear,
							diff : diff,
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
		
		window.location.href="./advanceCollectionReport";
	}
</script>
<style>
</style>