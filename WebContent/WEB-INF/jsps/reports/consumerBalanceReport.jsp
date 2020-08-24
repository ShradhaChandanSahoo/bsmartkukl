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
<c:url value="/consumerBalanceReport" var="readCategoryUrl" />


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
		
	});
	
	function onChange(arg) {
	}
	
	function dataBound(e) {

	}
	function changeLabel()
	 {
		
		if(monthradio.checked)
		{
			//alert("month");
			$("#lableDisplay").attr("placeholder", "Enter Number of Month");
			//$("#lableDisplay").text("Enter Number of Month");
		}
		else if(amountradio.checked)
		{
			//alert("amount");
			$("#lableDisplay").attr("placeholder", "Enter Amount");
			//$("#lableDisplay").text("Enter Amount");
		}
	 }
	//height : 500
</script>

<div class="page-content col-md-12">
	<div class="panel panel-flat">
		<div class="panel-body">
			<fieldset class="content-group">
				<legend class="text-bold">Customer Balance Report</legend>
				<div>
					<div class="col-md-12">
						<label class="radio-inline" style="color: red; margin-left: 25px;">
							<input type="radio" id="monthradio" name="radio-inline-left" value="1"
							class="styled" checked="checked" onclick="return changeLabel();">
							No. of Months
						</label> <label class="radio-inline" style="color: blue;"> <input
							type="radio" id="amountradio" name="radio-inline-left" value="2"
							class="styled" onclick="return changeLabel();"> Amount
						</label>
					</div>
					<div class="col-md-4">
						<div class="form-group">
						<label>&nbsp;</label>
							<input type="text" id="lableDisplay" name="lableDisplay"
								class="form-control" required="required"
								placeholder="Enter Number of Month" onkeyup="checkNumeric();">
						</div>
					</div>
					<div class="col-md-4">
						<div class="form-group">
							<label>Selection Criteria</label> 
							<select data-placeholder="Select"
							class="select" name="criteria" id="criteria">
							<option value="" data-icon="icon-git-branch">Select</option>
							<option value="<" data-icon="icon-git-branch">is less than</option>
							<option value=">" data-icon="icon-git-branch">is greater than</option>
							<option value="<=" data-icon="icon-git-branch">is less than equal to</option>
							<option value=">=" data-icon="icon-git-branch">is greater than equal to</option>
						</select>
						</div>
					</div>
					<div class="col-md-2">
						<label>Year</label> <select data-placeholder="Select"
							class="select" id="yearnep" name="yearnep" required="required">
							<c:forEach items="${yearList}" var="ward">
								<option value="${ward.year}">${ward.year}</option>
							</c:forEach>
						</select>

					</div>
					<div class="col-md-2">
						<label>Month</label> <select data-placeholder="Select"
							class="select" name="monthnep" id="monthnep"
							>
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
					<div class="col-md-12">
					<div class="text-center">
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
				</div>
			</fieldset>
			<div class="wethear" style="overflow: scroll;">
				<fieldset class="content-group">
					<legend class="text-bold">Customer Balance Report</legend>

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

								<kendo:grid-column title="Sl.No" field="slNo" width="80px"
									filterable="true" groupFooterTemplate="Total :"
									footerTemplate="Grand Total:" />
								<kendo:grid-column title="Con No" field="CONNECTION_NO"
									width="100px" filterable="true" />
								<kendo:grid-column title="Name" field="NAME_ENG" width="200px"
									filterable="true" />
								<kendo:grid-column title="Area" field="area_no" width="100px"
									filterable="true" />
								<kendo:grid-column title="Ward" field="ward_no" width="80px"
									filterable="true" />
								<kendo:grid-column title="Reading Day" field="reading_day" width="90px"
									filterable="true" />
									<kendo:grid-column title="Pipe Size" field="pipeSize" width="90px"
									filterable="true" />
								<kendo:grid-column title="WC" field="WATER_CHARGES" width="100px"
									filterable="true" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(WATER_CHARGES, 'n2') #"  />
								<kendo:grid-column title="SC" field="SW_CHARGES"
									width="100px" filterable="true" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(SW_CHARGES, 'n2') #" />
								<kendo:grid-column title="MR" field="MTR_RENT" width="100px"
									filterable="true" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(MTR_RENT, 'n2') #"/>
								<kendo:grid-column title="ARREARS" field="ARREARS" width="130px"
									filterable="true" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(ARREARS, 'n2') #"/>
								<kendo:grid-column title="NET AMOUNT" field="NET_AMOUNT" width="130px"
									filterable="true" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(NET_AMOUNT, 'n2') #"/>
				
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
											<kendo:dataSource-schema-model-field name="CONNECTION_NO"
												type="number" />
											<kendo:dataSource-schema-model-field name="NAME_ENG"
												type="string" />
											<kendo:dataSource-schema-model-field name="area_no"
												type="string" />
											<kendo:dataSource-schema-model-field name="ward_no"
												type="string" />
											<kendo:dataSource-schema-model-field name="reading_day"
												type="number" />
											<kendo:dataSource-schema-model-field name="WATER_CHARGES"
												type="number" />
											<kendo:dataSource-schema-model-field name="SW_CHARGES"
												type="number" />
											<kendo:dataSource-schema-model-field name="MTR_RENT"
												type="number" />
											<kendo:dataSource-schema-model-field name="ARREARS"
												type="number" />
											<kendo:dataSource-schema-model-field name="NET_AMOUNT"
												type="number" />
											<kendo:dataSource-schema-model-field name="pipeSize"
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
	var WATER_CHARGES = 0;
	var SW_CHARGES=0;
	var MTR_RENT=0;
	var ARREARS=0;
	var NET_AMOUNT=0;
	function dataBound(e) {
		var data = this.dataSource.view();
		WATER_CHARGES = 0;
		SW_CHARGES = 0;
		MTR_RENT=0;
		ARREARS=0;
		NET_AMOUNT=0;
		for (var i = 0; i < data.length; i++) {
			WATER_CHARGES = WATER_CHARGES + data[i].WATER_CHARGES;
			SW_CHARGES = SW_CHARGES + data[i].SW_CHARGES;
			MTR_RENT = MTR_RENT + data[i].MTR_RENT;
			ARREARS = ARREARS+data[i].ARREARS;
			NET_AMOUNT = NET_AMOUNT+data[i].NET_AMOUNT;
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
	
	function getData() {
		var amount = $("#lableDisplay").val();
		var criteria = $("#criteria").val();
		var yearnep=$("#yearnep").val();
		var monthnep=$("#monthnep").val();
		var flag=0;
		
		if (document.getElementById("monthradio").checked) {
			flag=1;
		}
		if (document.getElementById("amountradio").checked) {
			flag=2;
		}
		
		if (amount == "") {
			alert("Please Enter Number of Months or Amount");
			return false;
		}
		else if (criteria == "") {
			alert("Please Select Criteria");
			return false;
		}
		else if (yearnep == "") {
			alert("Please Select Year");
			return false;
		}
		else if (monthnep == "") {
			alert("Please Select Month");
			return false;
		}

		else {
		    var monthYear = yearnep+""+monthnep;
		    $('#loading').show();
			$.ajax({
				type : "GET",
				url : "./consumerBalanceReport/consumerBalanceReportRead",
				dataType : "JSON",
				async : false,
				data : {
					amount : amount,
					criteria : criteria,
					monthYear : monthYear,
					flag : flag,
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
	
	
	function printGrid() {
		var yearnep = $('#yearnep').val();
		var monthnep = $('#monthnep').val();
		var monthyr=yearnep+monthnep;
		var crit="";
		var lableDisplay = $('#lableDisplay').val();
		var criteria = $('#criteria').val();
		var branch="${BranchName}";
		
		if (document.getElementById("monthradio").checked) {
			crit="No. of Months"+criteria+" "+lableDisplay;
		}
		if (document.getElementById("amountradio").checked) {
			crit="Amount"+criteria+" "+lableDisplay;
		}
		
		
		var gridElement = $('#categoryGrid'), printableContent = '', win = window
				.open('', '', 'width=auto, height=900'), doc = win.document
				.open();

		var htmlStart = '<!DOCTYPE html>'
				+ '<html>'
				+ '<head>'
				+ '<meta charset="utf-8" />'
				+ '<title>Daily Revenue Report</title>'
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
				+ '<h2>'+branch+' kathmandu</h2><h3>Customer Balance Report</h3>'
				+ '<h4>Balance Upto :&nbsp;&nbsp;' + monthyr
				+ '&nbsp;&nbsp;&nbsp;Ctriteria :&nbsp;&nbsp;' + crit + '</h4></center>';

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

	

function clearButton(){
		
		window.location.href="./consumerBalanceReport";
	}
	
function checkNumeric() 
{
	var amt=$('#lableDisplay').val();
	if( $.isNumeric(amt)==false)
		{
			//alert(amt);
			$('#lableDisplay').val(amt.substring(0, amt.length - 1));
		}
}
</script>
<style>
</style>