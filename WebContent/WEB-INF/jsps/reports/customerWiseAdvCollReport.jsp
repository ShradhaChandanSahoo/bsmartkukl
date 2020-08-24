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
				<legend class="text-bold">Customer Wise Advance Collection Report</legend>
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
								<span class="ladda-label">Get Customer Wise Adv Coll Report</span>
							</button>
						</div>
					</div>

				</div>
			</fieldset>
			<div class="wethear" style="overflow: scroll;">
				<fieldset class="content-group">
					<legend class="text-bold">Customer Wise Advance Collection Report</legend>

					<!-- <button type="button" class="k-button" id="printGrid">Print Grid</button> -->
					<div class="pdf-page">
						<kendo:grid name="categoryGrid" pageable="true"
							dataBound="dataBound" change="onChange" resizable="true"
							sortable="true" reorderable="true" selectable="true"
							scrollable="true" filterable="true" groupable="true">
							<kendo:grid-pageable pageSizes="true" buttonCount="100"
								pageSize="2000" previousNext="true" input="true" numeric="true"></kendo:grid-pageable>
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
							<kendo:grid-editable mode="popup" confirmation="Are you sure you want to remove this item?" /> <kendo:grid-columns>

								<kendo:grid-column title="Sl" field="slNo" width="80px" filterable="true"  />
									
									<kendo:grid-column title="Conn No" field="connNo" width="150px" filterable="true" groupFooterTemplate="Total :" footerTemplate="Grand" />
									
								<kendo:grid-column title="Area No" field="areaNo" width="150px" filterable="true" groupFooterTemplate="Total :" footerTemplate="Total:"/>
										
								<kendo:grid-column title="Open Bal"
									field="openbal" width="150px" filterable="true"
									aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(openbal,'n2') #" />
								<kendo:grid-column title="WC" field="water_charges"
									width="150px" filterable="true" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(water_charges, 'n2') #" />
								<kendo:grid-column title="SC" field="sw_charges"
									width="150px" filterable="true" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(sw_charges,'n2') #" />
								<kendo:grid-column title="MR" field="mtr_rent"
									filterable="true" width="150px" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(mtr_rent,'n2','0.00') #" />
								
								<kendo:grid-column title="Total Bill" field="totalBill"
									filterable="true" width="150px" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(totalBill, 'n2','0.00') #" />
									
								<kendo:grid-column title="Adv Coll" field="advColl" width="150px"
									filterable="true" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(advColl, 'n2','0.00') #" />
									
								 <kendo:grid-column title="Adv Rebate" field="advRebate"
									filterable="true" width="150px" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(advRebate, 'n2','0.00') #" /> 
						
									
								<kendo:grid-column title="CloseBal" field="cbalance"
									filterable="true" width="150px" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(cbalance, 'n2','0.00') #" />
									
								<%-- <kendo:grid-column title="Sus" field="suspense"
									filterable="true" width="80px" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(suspense, 'n2','0.00') #" /> --%>
								
								
										
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
											<kendo:dataSource-schema-model-field name="connNo"
												type="string" />

											<kendo:dataSource-schema-model-field name="areaNo"
												type="number" />
												<kendo:dataSource-schema-model-field name="openbal"
												type="number" />
											<kendo:dataSource-schema-model-field name="water_charges"
												type="number" />
											<kendo:dataSource-schema-model-field name="sw_charges"
												type="number" />
											<kendo:dataSource-schema-model-field name="mtr_rent"
												type="number" />
											 <kendo:dataSource-schema-model-field name="totalBill"
												type="number" /> 
											<kendo:dataSource-schema-model-field name="advColl"
												type="number" />
											<kendo:dataSource-schema-model-field name="advRebate"
												type="number" />
											<kendo:dataSource-schema-model-field name="cbalance"
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
	var openbal = 0;
	var water_charges = 0;
	var sw_charges = 0;
	var mtr_rent = 0;
	var totalBill = 0;	
	var cbalance = 0;
    var advColl=0;
	var advRebate=0;
	function dataBound(e) {
		
		var data = this.dataSource.view();
		openbal = 0;
		water_charges = 0;
		sw_charges = 0;
		mtr_rent = 0;
		totalBill=0;
		cbalance = 0;
		advColl=0;
		advRebate=0;
		
		for (var i = 0; i < data.length; i++) {
			openbal = openbal + data[i].openbal;
			water_charges = water_charges + data[i].water_charges;
			sw_charges = sw_charges + data[i].sw_charges;
		
			mtr_rent = mtr_rent + data[i].mtr_rent;
			
			totalBill = totalBill + data[i].totalBill;
			cbalance=cbalance+data[i].cbalance;
			advColl=advColl+data[i].advColl;
			advRebate = advRebate + data[i].advRebate;
		}
		//cbalance =parseFloat(cbalance).toFixed(2);
		
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
				+ '<title>Customer Wise Advance Collection Report</title>'
				+ '<link href="http://kendo.cdn.telerik.com/' + kendo.version + '/styles/kendo.common.min.css" rel="stylesheet" /> '
				+ '<style>'
				+ 'html { font: 9pt sans-serif; }'
				+ '.k-grid { border-top-width: auto; }'
				+ '.k-grid, .k-grid-content { height: auto !important; }'
				+ '.k-grid-content { overflow: visible !important; }'
				+ 'div.k-grid table { table-layout: 100%; width: auto !important; }'
				+ '.k-grid .k-grid-header th { border-top: 1px solid; }'
				+ '.k-grid tr td {border-bottom: 1px solid black;}'
				+ '.k-grid-toolbar, .k-grid-pager > .k-link { display: none; }'
				+ '.k-grid .k-grouping-header { display: none;}'
				+ '.k-grid td { line-height: 1em;}'
				+ '.k-pager-wrap, .k-grid-pager{ display: none;}'
				+ '.k-grid, .k-grid-footer { height: auto !important; width: auto !important; padding-right: 0px !important; }'
				+ '</style>'
				+ '</head>'
				+ '<body> <center style="line-height: 10px;"><h1>Kathmandu Upatyaka Khanepani Limited</h1>'
				+ '<h2>' 
				+ ' kathmandu</h2><h3>Customer Wise Advance Collection Report</h3>'
				+ '<h4>From :&nbsp;&nbsp;' + mnyr
				+ '&nbsp;&nbsp;&nbsp;To :&nbsp;&nbsp;' + mnyr + '</h4></center>';

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
		
		if (yearnep == "") {
			alert("Please Select Year");
			return false;
		}
		else if (month == "") {
			alert("Please Select Month");
			return false;
		}
	    else {
			
	    	var monthyear=yearnep+""+month;
			$('#loading').show();
			$
					.ajax({
						type : "GET",
						url : "./customerWiseAdvCollReportDetails",
						dataType : "JSON",
						async : false,
						
						data : {
							monthyear : monthyear,
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
		
		window.location.href="./customerWiseAdvCollReport";
	}
</script>
<style>
</style>