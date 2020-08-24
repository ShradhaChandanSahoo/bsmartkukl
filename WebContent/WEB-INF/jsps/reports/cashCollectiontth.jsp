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
<c:url value="/monthlyObservationReport" var="readCategoryUrl" />


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
		
	});

	$('#toDatenep').nepaliDatePicker({
		dateFormat : "%D, %M %d, %y",
		npdMonth : true,
		npdYear : true,
		
	});
});

	//height : 500
</script>

<div class="page-content col-md-12">
	<div class="panel panel-flat">
		<div class="panel-body">

			<fieldset class="content-group">
				<legend class="text-bold">Cash Collection Report</legend>
				<div>
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
					<legend class="text-bold">Cash Collection Report</legend>

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
								<%-- <c:forEach var="element" items="${sathaList}"> 
	       	<kendo:grid-column title="element" field="" width="130px" filterable="true"/> 
	       
	       </c:forEach> --%>
								<kendo:grid-column title="Sl" field="slNo" width="75px"
									filterable="true" />
								<kendo:grid-column title="Con No" field="ConNo" width="105px"
									filterable="true"/>
								<kendo:grid-column title="Name" field="Name"
									width="200px" filterable="true"  footerTemplate="Grand Total"/>
								<kendo:grid-column title="Receipt No" field="receipt" width="150px"
									filterable="true"/>
								<kendo:grid-column title="Date" field="date" width="100px"
									filterable="true"/>
									
								<%-- <kendo:grid-column title="Dues-2064" field="due2064" width="110px"
									filterable="true" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(due2064,'n2','0.00') #"/>
									
								<kendo:grid-column title="Pen 2064" field="" width="110px"
									filterable="true"/> --%>
									
								<kendo:grid-column title="Due 207209" field="due2072" width="130px"
									filterable="true" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(due2072,'n2','0.00') #"/>
									
								<kendo:grid-column title="Pen 207209" field="penaltyCurrent" width="130px"
									filterable="true" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(penaltyCurrent,'n2','0.00') #"/> 
									
								<%-- <kendo:grid-column title="Total Pen" field="penaltyCurrent" width="100px"
									filterable="true" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(penaltyCurrent,'n2','0.00') #"/> --%>
									
								<kendo:grid-column title="Last Paid" field="lastPaid" width="120px"
									filterable="true" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(lastPaid,'n2','0.00') #"/>	
								
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
											<kendo:dataSource-schema-model-field name="ConNo"
												type="number" />
											<kendo:dataSource-schema-model-field name="Name"
												type="string" />
											<kendo:dataSource-schema-model-field name="receipt"
												type="number" />
											<kendo:dataSource-schema-model-field name="date"
												type="number" />
											<%-- <kendo:dataSource-schema-model-field name="due2064"
												type="number" /> --%>
											<%-- <kendo:dataSource-schema-model-field name="pen2064"
												type="number" /> --%>
											<kendo:dataSource-schema-model-field name="due2072"
												type="number" />
											<%-- <kendo:dataSource-schema-model-field name="pen2072"
												type="number" /> --%>
											<kendo:dataSource-schema-model-field name="penaltyCurrent"
												type="number" />
											<kendo:dataSource-schema-model-field name="lastPaid"
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
	                    <h3 id="loadingText">Loading..... Please wait. </h3> 
		 <img alt="image" src="./resources/images/loading.gif" style="width:3%;height: 3%;">
	</div>
</div>

<script>
//var due2064 = 0;
//var pen2064 = 0;
var due2072 = 0;
//var pen2072 = 0;
var lastPaid = 0;	
var penaltyCurrent = 0;
	function dataBound(e) {
		var data = this.dataSource.view();
		//due2064 = 0;
		//pen2064 = 0;
		//pen2072 = 0;
		penaltyCurrent = 0;
		due2072 = 0;
		lastPaid = 0;	
		
		for (var i = 0; i < data.length; i++) {
			//due2064 = due2064 + data[i].due2064;
			//pen2064 = parseFloat(pen2064) + parseFloat(data[i].pen2064);
			//pen2072 = parseFloat(pen2072) + parseFloat(data[i].pen2072);
			penaltyCurrent = penaltyCurrent + data[i].penaltyCurrent;
			due2072 = parseFloat(due2072) + parseFloat(data[i].due2072);
			lastPaid = lastPaid + data[i].lastPaid;
		}
		//due2064=parseFloat(due2064).toFixed(2);
		//pen2064=parseFloat(pen2064).toFixed(2);
		//pen2072=parseFloat(pen2072).toFixed(2);
		due2072=parseFloat(due2072).toFixed(2);
		lastPaid=parseFloat(lastPaid).toFixed(2);
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
		var from_date = $("#fromDatenep").val();
		var to_date = $("#toDatenep").val();
		var branch="${BranchName}";
		var gridElement = $('#categoryGrid'), printableContent = '', win = window
				.open('', '', 'width=auto, height=900'), doc = win.document
				.open();

		var htmlStart = '<!DOCTYPE html>'
			+ '<html>'
			+ '<head>'
			+ '<meta charset="utf-8" />'
			+ '<title>Cash Collection Report</title>'
			+ '<link href="http://kendo.cdn.telerik.com/' + kendo.version + '/styles/kendo.common.min.css" rel="stylesheet" /> '
			+ '<style>'
			+ 'html { font: 9pt sans-serif; }'
			+ '.k-grid { border-top-width: auto; }'
			+ '.k-grid, .k-grid-content { height: auto !important; }'
			+ '.k-grid-content { overflow: visible !important; }'
			+ 'div.k-grid table { table-layout: 100%; width: 100% !important; }'
			+ '.k-grid, .k-grid-header th { border-top: 1px solid; border-all: 1px solid !important; }'
			+ '.k-grid tr td {border-bottom: 1px solid black;}'
			+ '.k-grid-toolbar, .k-grid-pager > .k-link { display: none; }'
			+ '.k-grid .k-grouping-header { display: none;}'
			+ '.k-pager-wrap, .k-grid-pager{ display: none;}'
			+ '.k-grid td { line-height: 1em;}'
			+ '.k-grid, .k-grid-footer { height: auto !important; width: 100% !important; padding-right: 0px !important; }'
			+ '</style>'
			+ '</head>'
			+ '<body> <center style="line-height: 10px;"><h1>Kathmandu Upatyaka Khanepani Limited</h1>'
			+ '<h2>'+branch+' kathmandu</h2><h3>Cash Collection Report >=10000</h3>'
			+ '<h4>From :&nbsp;&nbsp;' + from_date
			+ '&nbsp;&nbsp;&nbsp;To :&nbsp;&nbsp;' + to_date + '</h4></center>';

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
		if (startDate > endDate) {
			alert("To date Nepali should be greater than From Date in Nepali");
			$('#toDatenep').val("");
			return false;
		} 
		else {
			$('#loading').show();
			$.ajax({
				type : "GET",
				url : "./cashCollectiontth/cashCollectiontthRead",
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
		
		window.location.href="./cashCollectiontth";
	}
</script>
<style>
</style>