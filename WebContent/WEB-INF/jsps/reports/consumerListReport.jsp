<%@include file="/WEB-INF/decorators/taglibs.jsp"%>
<%@ taglib prefix="kendo" uri="/WEB-INF/lib/kendo-taglib-2015.1.429.jar"%>

<script src="./resources/kendo/shared/js/pako.min.js"></script>

<link href="<c:url value='./resources/kendo/css/web/kendo.common.min.css'/>" rel="stylesheet" />
<link href="<c:url value='./resources/kendo/css/web/kendo.rtl.min.css'/>" rel="stylesheet" />
<link href="<c:url value='./resources/kendo/css/web/kendo.silver.min.css'/>" rel="stylesheet" />
<link href="<c:url value='./resources/kendo/css/dataviz/kendo.dataviz.min.css'/>" rel="stylesheet" />
<link href="<c:url value='./resources/kendo/css/dataviz/kendo.dataviz.default.min.css'/>" rel="stylesheet" />
<link href="<c:url value='./resources/kendo/shared/styles/examples-offline.css'/>" rel="stylesheet">
		
<script src="<c:url value='./resources/kendo/js/kendo.all.min.js' />"></script>
<script src="<c:url value='./resources/kendo/js/kendo.excel.min.js' />"></script>
<script src="<c:url value='./resources/kendo/js/kendo.pdf.min.js' />"></script>
<script src="<c:url value='./resources/kendo/shared/js/console.js'/>"></script>
<script src="<c:url value='./resources/kendo/shared/js/prettify.js'/>"></script>
<c:url value="/consumerListReport/consumerListReportReadData" var="readCategoryUrl"/>
  	
  	
  			<script src="//cdnjs.cloudflare.com/ajax/libs/jszip/2.4.0/jszip.min.js"></script>
  	<script type="text/javascript">
  	

  	$( document ).ready(function() {
		 $("#categoryGrid").kendoGrid({
       pageable: {
       	   pageSizes:[5,10,20,50,100, 500, 1000,2000]
       }, 
      
});   
		 $('#from_date_nep').nepaliDatePicker({
			 dateFormat: "%D, %M %d, %y",
				npdMonth: true,
				npdYear: true,
				onChange: function(){
			 		var rdngdtnep = $('#from_date_nep').val();
			 		getEngDate(rdngdtnep,1);
			 	}
	  });
	  
	  
	  $('#to_date_nep').nepaliDatePicker({
			 dateFormat: "%D, %M %d, %y",
				npdMonth: true,
				npdYear: true,
				onChange: function(){
			 		var rdngdtnep = $('#to_date_nep').val();
			 		getEngDate(rdngdtnep,2);
			 	}
	  });
	  
	  
	  	$('#masterReportsScreen').show();
		$('#masterReports').addClass('active');
		
		var activeMod="${activeModulesNew}";
		var module="Master Reports";
		var check=activeMod.indexOf(module) > -1;
		
		if(check){
		}else{
			window.location.href="./accessDenied";
		}
		
	});

  	//height : 500
 	</script>
   
	 <div class="page-content">
	<div class="panel panel-flat">
		<div class="panel-body">
			<fieldset class="content-group">
				<legend class="text-bold">Consumer List Report
				
				</legend> 
			
				
			<div class="col-md-4">
				<div class="form-group" style="padding-top: 27px;">
					<label>&nbsp;</label>
	                            <button type="button" class="btn bg-teal btn-ladda" onclick="return getData()" data-style="expand-right" data-spinner-size="20">
					<span class="ladda-label">Get Consumer List</span></button>
	                           </div>
			</div>
				
				
				</fieldset>
				<div class="wethear" style="overflow: scroll;">
				
			<fieldset class="content-group">
				  <!-- <button type="button" class="k-button" id="printGrid">Print Grid</button> -->
				<div class="pdf-page">
   <kendo:grid name="categoryGrid" pageable="true"  change="onChange" resizable="true" sortable="true" reorderable="true" selectable="true" scrollable="true" filterable="true" groupable="true">				
      <kendo:grid-pageable pageSizes="true" buttonCount="20" pageSize="20" previousNext="true" input="true" numeric="true" ></kendo:grid-pageable>
	  <kendo:grid-filterable extra="true">
	    <kendo:grid-filterable-operators>
		 <kendo:grid-filterable-operators-string eq="Is equal to"/>
		 <kendo:grid-filterable-operators-number eq="Is equal to" gt="Is greather than" gte="IS greather than and equal to" lt="Is less than" lte="Is less than and equal to" neq="Is not equal to"/>
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
	  
	       	<kendo:grid-column title="Sl.No" field="slNo" width="70px" filterable="true"/>    
	       	<kendo:grid-column title="Connection No" field="con_no" width="80px" filterable="true"/>
	       	<kendo:grid-column title="Ward No" field="ward_no" width="60px" filterable="true"/>      
	       	<kendo:grid-column title="Area No" field="area_no" width="60px" filterable="true"/>    
	       	<kendo:grid-column title="Name" field="name" width="80px" filterable="true"/>    
	       	<kendo:grid-column title="Address" field="address" width="130px" filterable="true"/>
	       	<kendo:grid-column title="Connection Type" field="con_type" width="70px" filterable="true"/>
	       	<kendo:grid-column title="Pipe Size" field="pipe_size" width="60px" filterable="true"/>
	       	<%-- <kendo:grid-column title="New Area No" field="new_area_no" width="130px" filterable="true"/> --%>
	       	<kendo:grid-column title="Meter No" field="meter_no" width="70px" filterable="true"/>
		    
	   </kendo:grid-columns>
	  <%--  <kendo:dataSource pageSize="100" > --%>
	     <kendo:dataSource pageSize="20" >
	   
		 <kendo:dataSource-transport>
			<kendo:dataSource-transport-read url="${readCategoryUrl}" dataType="json" type="POST" contentType="application/json" />
		 </kendo:dataSource-transport>
		 <kendo:dataSource-schema>
			<kendo:dataSource-schema-model id="id">
			  <kendo:dataSource-schema-model-fields>
			  <kendo:dataSource-schema-model-field name="slNo" type="number"/>
			  <kendo:dataSource-schema-model-field name="con_no" type="string"/>
			  <kendo:dataSource-schema-model-field name="area_no" type="string"/> 
			   <kendo:dataSource-schema-model-field name="ward_no" type="string"/> 
			  <kendo:dataSource-schema-model-field name="name" type="string"/>
			  <kendo:dataSource-schema-model-field name="address" type="string"/>
			  <kendo:dataSource-schema-model-field name="con_type" type="string"/>
			  <kendo:dataSource-schema-model-field name="pipe_size" type="number"/>
			  <%-- <kendo:dataSource-schema-model-field name="new_area_no" type="number"/> --%>
			   <kendo:dataSource-schema-model-field name="meter_no" type="string"/>
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
	
var branch="${BranchName}";
function onChange(arg) {
}

	function getPDF(selector) {
	 kendo.drawing.drawDOM("#categoryGrid", {
          paperSize: "A4",
          landscape: true,
          margin: "2cm"
        })
        .then(function(group){
            kendo.drawing.pdf.saveAs(group, "multipage.pdf");
        });
	
        /* kendo.drawing.drawDOM($(selector)).then(function(group){
          kendo.drawing.pdf.saveAs(group, "Invoice.pdf");
        }); */
      }
	function printGrid() {
		
		
	    var gridElement = $('#categoryGrid'),
	        printableContent = '',
	        win = window.open('', '', 'width=1600, height=900'),
	        doc = win.document.open();

	    var htmlStart =
	            
	'<!DOCTYPE html>'
				+ '<html>'
				+ '<head>'
				+ '<meta charset="utf-8" />'
				+ '<title>Consumer List Report</title>'
				+ '<link href="http://kendo.cdn.telerik.com/' + kendo.version + '/styles/kendo.common.min.css" rel="stylesheet" /> '
				+ '<style>'
				+ 'html { font: 9pt sans-serif; }'
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

				+ '</style>'
				+ '</head>'
				+ '<body> <center style="line-height: 10px;"><h1>Kathmandu Upatyaka Khanepani Limited</h1>'
				+ '<h2>' + branch
				+ ' kathmandu</h2><h3>Consumer List Report</h3>';

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
						}
					}

				});

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
				url : "./consumerListReport/consumerListReportReadData",
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
</script>
<style>
</style>