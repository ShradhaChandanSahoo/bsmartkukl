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
		
		$('#umbillreport').show();
		$('#reportsbilling').addClass('active');
		
		var activeMod="${activeModulesNew}";
		var module="Billing Reports";
		var check=activeMod.indexOf(module) > -1;
		
		if(check){
		}else{
			window.location.href="./accessDenied";
		} 
		
		/* var activeMod="${activeModulesNew}";
		var module="Ledger Verification";
		var check=activeMod.indexOf(module) > -1;
		
		if(check){
		}else{
			window.location.href="./accessDenied";
		} */
		
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
				<legend class="text-bold">Ledger Details Report</legend>

				<div class="col-md-2">
					<label>Ward <font color="red">*</font></label> <select
						data-placeholder="Select" class="select" id="wardno" name="wardno"
						required="required" onchange="return allWard()">
						<option value="" data-icon="icon-git-branch">Select</option>
						<option value="ALL">ALL</option>
						<c:forEach items="${wardNoList}" var="ward">
							<option value="${ward.wardNo}">${ward.wardNo}</option>
						</c:forEach>

					</select>
				</div>
				<div class="col-md-2">
					<div class="form-group">
						<label>Reading Day <font color="red">*</font></label><select data-placeholder="Select"
							class="select" id="reading_day" name="reading_day" required="required">
							<option value="" data-icon="icon-git-branch">Select</option>
							
							<c:forEach items="${readingDayList}" var="rday">
								<option value="${rday.readingDay}">${rday.readingDay}</option>
							</c:forEach>

						</select>
					</div>
				</div>
				<div>
					<div class="col-md-2">
						<label>Year <font color="red">*</font></label> <select
							data-placeholder="Select" class="select" id="yearnep"
							name="yearnep" required="required">
							<option value="" data-icon="icon-git-branch">Select</option>

							<c:forEach items="${yearList}" var="ward">
								<option value="${ward.year}">${ward.year}</option>
							</c:forEach>
						</select>
					</div>

					<div class="form-group col-md-2">
						<label>Month <font color="red">*</font></label> <select
							data-placeholder="Select" class="select" name="month" id="month"
							required="required">
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


					<div class="form-group col-md-2">
						<label>Con Category <font color="red">*</font></label> <select
							data-placeholder="Select" class="select" name="catagory" id="catagory"
							required="required">
							<option value="" data-icon="icon-git-branch">Select</option>
							<option value="Domestic" data-icon="icon-git-branch">Domestic</option>
							<option value="Government" data-icon="icon-git-branch">Government</option>
							<option value="Industry and Company" data-icon="icon-git-branch">Industry and Company</option>

						</select>

					</div>
					<div class="col-md-2">
						<div class="form-group" style="padding-top: 27px;">
						
							<button type="button" class="btn bg-teal btn-ladda"
								onclick="return getData()" data-style="expand-right"
								data-spinner-size="20">
								<span class="ladda-label">Get Ledger Details</span>
							</button>	
						<!-- 	<label>&nbsp;</label>
							<button type="button" class="btn bg-teal btn-ladda"
								onclick="clearButton();">
								<span class="ladda-label">Clear</span>
							</button> -->
						</div>
					</div>

				</div>
			</fieldset>
			
			<div class="wethear" style="overflow: scroll;">
				<fieldset class="content-group">
					<legend class="text-bold">Ledger Details Report</legend>

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

								<kendo:grid-column title="Sl" field="slNo" width="50px"
									filterable="true" />
									
								<kendo:grid-column title="Con No" field="CONNECTION_NO" width="90px"
									filterable="true" />
										
								<kendo:grid-column title="Name"
									field="NAME_ENG" width="150px" filterable="true" groupFooterTemplate="Total :"
									footerTemplate="Grand&nbsp;Total:"/>
								<kendo:grid-column title="Area" field="areano"
									width="90px" filterable="true" />
								
								<kendo:grid-column title="KUKL" field="kukl"
									width="80px" filterable="true" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(kukl, 'n2') #" />
								<kendo:grid-column title="Board" field="board"
									width="85px" filterable="true" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(board, 'n2') #" />
								<kendo:grid-column title="Arrears" field="arrears"
									filterable="true" width="90px" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(arrears, 'n2','0.00') #" />
								
								<kendo:grid-column title="LR" field="l_reading"
									width="60px" filterable="true" />
								<kendo:grid-column title="PR" field="p_reading"
									width="60px" filterable="true" />
								<kendo:grid-column title="Unit" field="consumption"
									width="65px" filterable="true" />
									
								<kendo:grid-column title="WC" field="watercharge"
									width="80px" filterable="true" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(watercharge, 'n2') #" />
								<kendo:grid-column title="SC" field="swcharge"
									width="80px" filterable="true" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(swcharge,'n2') #" />
								<kendo:grid-column title="MR" field="mtrrent"
									filterable="true" width="70px" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(mtrrent,'n2','0.00') #" />
								
								<kendo:grid-column title="Misc" field="misc" width="70px"
									filterable="true" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(misc, 'n2','0.00') #" />
									
									
								<kendo:grid-column title="Net" field="net"
									filterable="true" width="85px" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(net, 'n2','0.00') #" />
									
								<kendo:grid-column title="Penalty" field="penalty"
									filterable="true" width="85px" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(penalty, 'n2','0.00') #" />
									
								<kendo:grid-column title="Rebate" field="rebate"
									filterable="true" width="85px" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(rebate, 'n2','0.00') #" />
									
								<kendo:grid-column title="Paid" field="lastpaid"
									filterable="true" width="85px" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(lastpaid, 'n2','0.00') #" />
									
								<kendo:grid-column title="CloseBal" field="closingbal"
									filterable="true" width="85px" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(closingbal, 'n2','0.00') #" />
									
								<kendo:grid-column title="ReceiptNo" field="receipt"
									filterable="true" width="120px"  />
									
								<kendo:grid-column title="Date" field="rdate"
									filterable="true" width="95px"  />
									
										
							</kendo:grid-columns>
							<kendo:dataSource pageSize="100">
								<kendo:dataSource-transport>
									<kendo:dataSource-transport-read url="${readCategoryUrl}"
										dataType="json" type="POST" contentType="application/json" />
								</kendo:dataSource-transport>
								<kendo:dataSource-schema>
									<kendo:dataSource-schema-model id="id">
										<kendo:dataSource-schema-model-fields>
											<kendo:dataSource-schema-model-field name="slNo" type="number" />
											<kendo:dataSource-schema-model-field name="CONNECTION_NO" type="string" />
											<kendo:dataSource-schema-model-field name="NAME_ENG" type="string" />
											<kendo:dataSource-schema-model-field name="areano" type="string" />
											<kendo:dataSource-schema-model-field name="board" type="number" />
											<kendo:dataSource-schema-model-field name="kukl" type="number" />
											<kendo:dataSource-schema-model-field name="l_reading" type="number" />
											<kendo:dataSource-schema-model-field name="p_reading" type="number" />
											<kendo:dataSource-schema-model-field name="consumption" type="number" />
											<kendo:dataSource-schema-model-field name="watercharge" type="number" />
											<kendo:dataSource-schema-model-field name="swcharge" type="number" />
											<kendo:dataSource-schema-model-field name="mtrrent" type="number" />
											<kendo:dataSource-schema-model-field name="misc" type="number" />
											<kendo:dataSource-schema-model-field name="arrears" type="number" />
											<kendo:dataSource-schema-model-field name="net" type="number" />
											<kendo:dataSource-schema-model-field name="penalty" type="number" />
											<kendo:dataSource-schema-model-field name="rebate" type="number" />
											<kendo:dataSource-schema-model-field name="lastpaid" type="number" />
											<kendo:dataSource-schema-model-field name="closingbal" type="number" />
											<kendo:dataSource-schema-model-field name="receipt" type="number" />
											<kendo:dataSource-schema-model-field name="rdate" type="number" />

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
	var board = 0;
	var kukl = 0;
	var watercharge = 0;
	var swcharge = 0;
	var mtrrent = 0;
	var misc = 0;
	var arrears = 0;
	var net = 0;
	var penalty = 0;
	var rebate = 0;
	var lastpaid = 0;
	var closingbal = 0;
	var branch = "${BranchName}";
	var reading_day='';
	function dataBound(e) {
		
		var data = this.dataSource.view();
		board = 0;
		kukl = 0;
		watercharge = 0;
		swcharge = 0;
		mtrrent = 0;
		misc = 0;
		arrears = 0;
		net = 0;
		penalty = 0;
		rebate = 0;
		lastpaid = 0;
		closingbal = 0;
		for (var i = 0; i < data.length; i++) {
			board = board + data[i].board;
			kukl = kukl + data[i].kukl;
			watercharge = watercharge + data[i].watercharge;
			swcharge = swcharge + data[i].swcharge;
			mtrrent = mtrrent + data[i].mtrrent;
			misc = misc + data[i].misc;
			penalty = penalty + data[i].penalty;
			arrears = arrears + data[i].arrears;
			net = net + data[i].net;
			rebate = rebate + data[i].rebate;
			lastpaid = lastpaid + data[i].lastpaid;
			closingbal = closingbal + data[i].closingbal;
			
		}
		//cbalance =parseFloat(cbalance).toFixed(2);
		//posadj =parseFloat(posadj).toFixed(2);
		//negadj =parseFloat(negadj).toFixed(2);
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
		var wardno = $("#wardno").val();
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
				+ '<title>Ledger Details Report</title>'
				+ '<link href="http://kendo.cdn.telerik.com/' + kendo.version + '/styles/kendo.common.min.css" rel="stylesheet" /> '
				+ '<style>'
				+ 'html { font: 9pt sans-serif; }'
				+ '.k-grid { border-top-width: auto; }'
				+ '.k-grid, .k-grid-content { height: auto !important; }'
				+ '.k-grid-content { overflow: visible !important; }'
				+ 'div.k-grid table { table-layout: 190%; width: auto !important; }'
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
				+ ' kathmandu</h2><h3>Ledger Details Report</h3>'
				+ '<h4> Ward No:&nbsp;&nbsp;'+wardno+'&nbsp;&nbsp;&nbsp; From :&nbsp;&nbsp;' + mnyr
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
		var wardno = $("#wardno").val();
		var yearnep = $("#yearnep").val();
		var month = $("#month").val();
		var catagory=$("#catagory").val();
		
		reading_day=reading_day+$('#reading_day').val();
		if (wardno == "") {
			alert("Please Select Ward");
			return false;
		}else if(reading_day=="" || reading_day==null){
			alert("Please select Reading day");
			return false;
			
		}
		else if (yearnep == "") {
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
						url : "./ledgerVerification/ledgerVerificationRead",
						dataType : "JSON",
						async : false,
						
						data : {
							wardno : wardno,
							monthyear : monthyear,
							reading_day : reading_day,
							catagory : catagory,
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
	
	function allWard(){
		var wardno = $("#wardno").val();
		if(wardno=='ALL'){
			$('#reading_day').attr("disabled", true);
			reading_day='All';
			}
		else
			{
			$('#reading_day').attr("disabled", false);
			reading_day='';
			}
	}
function clearButton(){
		
		window.location.href="./newMonthlySalesReport";
	}
</script>
<style>
</style>