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
			npdYear : true
			
		});

		$('#to_date_nep').nepaliDatePicker({
			dateFormat : "%D, %M %d, %y",
			npdMonth : true,
			npdYear : true
			
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
			$("#lableDisplay").attr("placeholder", "Enter Number of Times");
		}
		else if(amountradio.checked)
		{
			$("#lableDisplay").attr("placeholder", "Enter Amount");
		}
	 }
	//height : 500
</script>

<div class="page-content col-md-12">
	<div class="panel panel-flat">
		<div class="panel-body">
			<fieldset class="content-group">
				<legend class="text-bold">Multiple/Big Payment</legend>
				<div>
					<div class="col-md-12">
						<label class="radio-inline" style="color: red; margin-left: 25px;">
							<input type="radio" id="monthradio" name="radio-inline-left" value="1"
							class="styled" checked="checked" onclick="return changeLabel();">
							No. of Times
						</label> <label class="radio-inline" style="color: blue;"> <input
							type="radio" id="amountradio" name="radio-inline-left" value="2"
							class="styled" onclick="return changeLabel();"> Amount
						</label>
					</div>
					<div class="col-md-3">
						<div class="form-group">
						<label>&nbsp;</label>
							<input type="text" id="lableDisplay" name="lableDisplay"
								class="form-control" required="required"
								placeholder="Enter Number of Times" onkeyup="checkNumeric();">
						</div>
					</div>
					
					
					
					<div class="col-md-2">
						<div class="form-group">
							<label>Connection Type</label> 
							<select data-placeholder="Select" class="select" name="con_type" id="con_type">
							<option value="All" data-icon="icon-git-branch">All</option>
							<option value="Metered" data-icon="icon-git-branch">Metered</option>
							<option value="Unmetered" data-icon="icon-git-commit">Unmetered</option>
						</select>
						</div>
					</div>
					
					
					
					<div class="col-md-3">
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
						<div class="form-group">
							<label>From Date&nbsp;<font color="red">*</font></label>
							<input type="text" id="from_date_nep" name="from_date_nep"
								class="form-control nepali-calendar" required="required"
								placeholder="From Date in Nepali...">
						</div>
					</div>
					<div class="col-md-2">
						<div class="form-group">
							<label>To Date&nbsp;<font color="red">*</font></label>
							<input type="text" name="to_date_nep" id="to_date_nep"
								class="form-control nepali-calendar" required="required"
								placeholder="To Date in Nepali...">
						</div>
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
					<legend class="text-bold">Multiple/Big Payment</legend>

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
								<kendo:grid-column title="RecptNo" field="RecptNo" width="130px"
									filterable="true" />
								<kendo:grid-column title="Date" field="Rdate" width="90px"
									filterable="true" />
								<kendo:grid-column title="Con No" field="CONNECTION_NO"
									width="100px" filterable="true" />
								<kendo:grid-column title="Name" field="NAME_ENG" width="220px"
									filterable="true" />
								<kendo:grid-column title="OB" field="ARREARS" width="110px"
									filterable="true" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(ARREARS, 'n2') #"/>
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
								<kendo:grid-column title="Misc" field="Misc" width="100px"
									filterable="true" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(Misc, 'n2') #"/>
								<kendo:grid-column title="Paid" field="Paid" width="120px"
									filterable="true" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(Paid, 'n2') #"/>

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
											<kendo:dataSource-schema-model-field name="RecptNo"
												type="number" />
											<kendo:dataSource-schema-model-field name="Rdate"
												type="number" />
											<kendo:dataSource-schema-model-field name="CONNECTION_NO"
												type="number" />
											<kendo:dataSource-schema-model-field name="NAME_ENG"
												type="String" />
											<kendo:dataSource-schema-model-field name="WATER_CHARGES"
												type="number" />
											<kendo:dataSource-schema-model-field name="SW_CHARGES"
												type="number" />
											<kendo:dataSource-schema-model-field name="MTR_RENT"
												type="number" />
											<kendo:dataSource-schema-model-field name="ARREARS"
												type="number" />
											<kendo:dataSource-schema-model-field name="Paid"
												type="number" />
											<kendo:dataSource-schema-model-field name="Misc"
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
	<div id="loading" hidden="true" style="text-align: center;">
		<h3 id="loadingText">Loading..... Please wait.</h3>
		<img alt="image" src="./resources/images/loading.gif"
			style="width: 3%; height: 3%;">
	</div>
</div>


	
<script>
	var WATER_CHARGES = 0;
	var SW_CHARGES=0;
	var MTR_RENT=0;
	var ARREARS=0;
	var Misc=0;
	var Paid=0;
	function dataBound(e) {
		var data = this.dataSource.view();
		WATER_CHARGES = 0;
		SW_CHARGES = 0;
		MTR_RENT=0;
		ARREARS=0;
		Misc=0;
		Paid=0;
		for (var i = 0; i < data.length; i++) {
			WATER_CHARGES = WATER_CHARGES + data[i].WATER_CHARGES;
			SW_CHARGES = SW_CHARGES + data[i].SW_CHARGES;
			MTR_RENT = MTR_RENT + data[i].MTR_RENT;
			ARREARS = ARREARS+data[i].ARREARS;
			Misc = Misc+data[i].Misc;
			Paid = Paid+data[i].Paid;
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
		var flag=0;
		var from_date = $("#from_date_nep").val();
		var to_date = $("#to_date_nep").val();
		var con_type = $("#con_type").val();
		
		if (from_date == "") {
			alert("Please Enter From Date");
			return false;
		}
		if (to_date == "") {
			alert("Please Enter To Date");
			return false;
		}
		
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


		 else {
		    $('#loading').show();
			$.ajax({
				type : "GET",
				url : "./multiPayment/multiPaymentRead",
				dataType : "JSON",
				async : false,
				data : {
					amount : amount,
					criteria : criteria,
					from_date : from_date,
					to_date : to_date,
					flag : flag,
					con_type:con_type
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
		var from_date = $("#from_date_nep").val();
		var to_date = $("#to_date_nep").val();
		var crit="";
		var lableDisplay = $('#lableDisplay').val();
		var criteria = $('#criteria').val();
		var branch="${BranchName}";
		
		if (document.getElementById("monthradio").checked) {
			crit="No. of Months "+criteria+" "+lableDisplay;
		}
		if (document.getElementById("amountradio").checked) {
			crit="Amount "+criteria+" "+lableDisplay;
		}
		
		
		var gridElement = $('#categoryGrid'), printableContent = '', win = window
				.open('', '', 'width=auto, height=900'), doc = win.document
				.open();

		var htmlStart = '<!DOCTYPE html>'
				+ '<html>'
				+ '<head>'
				+ '<meta charset="utf-8" />'
				+ '<title>Multiple/Big Payment</title>'
				+ '<link href="http://kendo.cdn.telerik.com/' + kendo.version + '/styles/kendo.common.min.css" rel="stylesheet" /> '
				+ '<style>'
				+ 'html { font: 9pt sans-serif; }'
				+ '.k-grid { border-top-width: auto; }'
				+ '.k-grid, .k-grid-content { height: auto !important; }'
				+ '.k-grid-content { overflow: visible !important; }'
				+ 'div.k-grid table { table-layout: 110%; width: 100% !important; }'
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
				+ '<h2>'+branch+' kathmandu</h2><h3>Multiple/Big Payment</h3>'
				+ '<h4>From :&nbsp;&nbsp;' + from_date+'&nbsp;&nbsp; To : &nbsp;&nbsp;'+to_date
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
		
		window.location.href="./multiPayment";
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