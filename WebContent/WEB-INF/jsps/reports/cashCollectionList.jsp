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
<c:url value="/cashCollectionList" var="readCategoryUrl" />


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
				<legend class="text-bold">Cash Collection UpTo 2064/CMY</legend>
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
					<legend class="text-bold">Cash Collection UpTo 2064/CMY</legend>

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

								<kendo:grid-column title="Sl" field="slNo" width="70px"
									filterable="true" />
								<kendo:grid-column title="Recept No" field="RECEIPT_NO"
									width="120px" filterable="true" groupFooterTemplate="Total :"
									footerTemplate="Grand Total:"  />
								<kendo:grid-column title="Date" field="RDATE" width="100px"
									filterable="true" />
								<kendo:grid-column title="Cons No" field="CONNECTION_NO"
									width="100" filterable="true"/>
								<kendo:grid-column title="Name" field="NAME_ENG" width="190px"
									filterable="true" />
								<kendo:grid-column title="Coll. Upto206410"
									field="COL_UPTO" width="150px" filterable="true"
									aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(COL_UPTO,'n2','0.00') #" />
								<kendo:grid-column title="Penalty" field="PENALTY"
									filterable="true" width="110px" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(PENALTY,'n2','0.00') #" />
								<kendo:grid-column title="Total Amount" field="AMOUNT"
									filterable="true" width="110px" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(AMOUNT, 'n2','0.00') #" />
								<kendo:grid-column title="FMY" field="FROM_MON_YEAR"
									width="110px" filterable="true" />
								<kendo:grid-column title="TMY" field="TO_MON_YEAR" width="100px"
									filterable="true" />
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
											<kendo:dataSource-schema-model-field name="RECEIPT_NO"
												type="number" />
											<kendo:dataSource-schema-model-field name="RDATE"
												type="number" />
											<kendo:dataSource-schema-model-field name="CONNECTION_NO"
												type="number" />
											<kendo:dataSource-schema-model-field name="NAME_ENG"
												type="String" />
											<kendo:dataSource-schema-model-field name="COL_UPTO"
												type="number" />
											<kendo:dataSource-schema-model-field name="PENALTY"
												type="number" />
											<kendo:dataSource-schema-model-field name="AMOUNT"
												type="number" />
											<kendo:dataSource-schema-model-field name="FROM_MON_YEAR"
												type="number" />
											<kendo:dataSource-schema-model-field name="TO_MON_YEAR"
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
var branch = "${BranchName}";
	var COL_UPTO = 0.0;
	var PENALTY = 0.0;
	var AMOUNT = 0.0;
	var total_amt=0.0;
	var total_pen=0.0;
	function dataBound(e) {
		var data = this.dataSource.view();
		COL_UPTO = 0;
		PENALTY = 0;
		AMOUNT = 0;
		

		for (var i = 0; i < data.length; i++) {
			COL_UPTO = parseFloat(COL_UPTO) + parseFloat(data[i].COL_UPTO);
			PENALTY = parseFloat(PENALTY) + parseFloat(data[i].PENALTY);
			AMOUNT = parseFloat(AMOUNT) + parseFloat(data[i].AMOUNT);
		}
		
		COL_UPTO = parseFloat(COL_UPTO).toFixed(2);
		PENALTY = parseFloat(PENALTY).toFixed(2);
		AMOUNT = parseFloat(AMOUNT).toFixed(2);
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
		var total=COL_UPTO;
		var penalty=PENALTY;
		var grandTotal=AMOUNT;
		
		//var total_amt=0.0;
		//var total_pen=0.0;
		
		
		//var totalCurrent=parseFloat(total_amt)-parseFloat(total)-parseFloat(penalty);
		var penCurrent=parseFloat(total_pen)-parseFloat(penalty);
		var grandCurrent=parseFloat(total_amt)-parseFloat(grandTotal);
		var totalCurrent=grandCurrent-penCurrent;
		var gridElement = $('#categoryGrid'), printableContent = '', win = window
				.open('', '', 'width=110%, height=900'), doc = win.document
				.open();
		
		var htmlStart = '<!DOCTYPE html>'
				+ '<html>'
				+ '<head>'
				+ '<meta charset="utf-8" />'
				+ '<title>Cash Collection List</title>'
				+ '<link href="http://kendo.cdn.telerik.com/' + kendo.version + '/styles/kendo.common.min.css" rel="stylesheet" /> '
				+ '<style>'
				+ 'html { font: 9pt sans-serif; }'
				+ '.k-grid { border-top-width: auto; }'
				+ '.k-grid, .k-grid-content { height: auto !important; }'
				+'.k-grid .k-grouping-header { display: none;}'
				+ '.k-grid-content { overflow: visible !important; }'
				+ 'div.k-grid table { table-layout: auto; width: 100% !important; }'
				+ '.k-grid .k-grid-header th { border-top: 1px solid; }'
				+ '.k-grid tr td {border-bottom: 1px solid black;}'
				+'.k-grid td { line-height: 1em;}'
				+ '.k-grid-toolbar, .k-grid-pager > .k-link { display: none; }'
				+ '.k-grid, .k-grid-footer { height: auto !important; width: 100% !important; padding-right: 0px !important; }'
				+'.k-pager-wrap, .k-grid-pager{ display: none;}'
				+ '</style>'
				+ '</head>'
				+ '<body> <center style="line-height: 10px;"><h1>Kathmandu Upatyaka Khanepani Limited</h1>'
				+ '<h2>'+ branch + ' kathmandu</h2><h3>Cash Collection UpTo 2064/CMY</h3>'
				+ '<h4>From :&nbsp;&nbsp;' + frm
				+ '&nbsp;&nbsp;&nbsp;To :&nbsp;&nbsp;' + to + '</h4></center>';
		var t_rev=parseFloat(totalCurrent)+parseFloat(total);
		var t_pen=parseFloat(total_pen);
		var t_amt=parseFloat(grandCurrent)+parseFloat(grandTotal);
		//alert(t_amt);
		
		var old_end='<h3>Total Collection upto 2064: '+parseFloat(total).toFixed(2)+'</h3>'
		+'<h3>Penalty upto 2064: '+parseFloat(penalty).toFixed(2)+'</h3>'
		+'<h3>Grant Total (2064) : '+parseFloat(grandTotal).toFixed(2)+'</h3>'
		+'<h3>Total collection current (WC+SC+Misc) : '+parseFloat(totalCurrent).toFixed(2)+'</h3>'
		+'<h3>Penalty Current : '+parseFloat(penCurrent).toFixed(2)+'</h3>'
		+'<h3>Grant Total Current (Total+penalty) : '+parseFloat(grandCurrent).toFixed(2)+'</h3>'
		+'</body>' + '</html>';
		
		var summary='<br><br><br><div><table align="center" style="width:50%" border="1" rules="all" cellpadding="10">'
		+'<tr><td></td><th>Total Revenue</th><th >Penalty</th><th>Total Receivd Amt.</th></tr><tr style="text-align: center;">'
		+'<th style="text-align: end;">After 2064-10 till Now: </th><td>'+parseFloat(totalCurrent).toFixed(2)+'</td><td>'+parseFloat(penCurrent).toFixed(2)+'</td><td>'+parseFloat(grandCurrent).toFixed(2)+'</td></tr><tr style="text-align: center;">'
		+'<th style="text-align: end;">Upto 2064-10: </th><td>'+parseFloat(total).toFixed(2)+'</td><td>'+parseFloat(penalty).toFixed(2)+'</td><td>'+parseFloat(grandTotal).toFixed(2)+'</td></tr><tr style="text-align: center;">'
		+'<th style="text-align: end;">Total: </th><td>'+parseFloat(t_rev).toFixed(2)+'</td>'
		+'<td>'+parseFloat(t_pen).toFixed(2)+'</td>'
		+'<td>'+parseFloat(t_amt).toFixed(2)+'</td></tr></table></div><br><br>';
				
				
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

		doc.write(htmlStart + printableContent+ summary + htmlEnd);
		doc.close();
		win.print();
	}
	$("#categoryGrid").on("click", ".k-grid-printGrid", function(e) {
		printGrid();
	});

	function getEngDate(nepalidate, value) {

		var date_nep = nepalidate;
		$('#loading').show();
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
		$('#loading').hide();
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
			$.ajax({
				type : "GET",
				url : "./cashCollectionList/cashCollectionListReportRead",
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
			$.ajax({
				type : "GET",
				url : "./cashCollectionList/cashCollectionListReportReadAll",
				dataType : "JSON",
				async : false,
				data : {
					from_date : from_date,
					to_date : to_date,
				},

				success : function(response) 
				{
					
					total_amt = response[0].total_amt;
					total_pen = response[0].total_pen;
					
				}

			});
			
			
			
			$('#loading').hide();
		}

	}
	function clearButton() {

		window.location.href = "./cashCollectionList";
	}
</script>
<style>
</style>