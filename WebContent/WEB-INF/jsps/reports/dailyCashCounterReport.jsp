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
<c:url value="/dailycashcounter/dailyCashCounterRead" var="readCategoryUrl"/>
  	
  	
  			<script src="//cdnjs.cloudflare.com/ajax/libs/jszip/2.4.0/jszip.min.js"></script>
  	<script type="text/javascript">
  	

  	$( document ).ready(function() {
		 $("#categoryGrid").kendoGrid({
       pageable: {
       	   pageSizes:[5,10,20,50,100, 500, 1000,2000]
       }, 
      
});   
	});

  	//height : 500
 	</script>
   
	 <div class="page-content">
	<div class="panel panel-flat">
		<div class="panel-body">
			<fieldset class="content-group">
				<legend class="text-bold">Daily Cash Counter Report</legend> 
				  <!-- <button type="button" class="k-button" id="printGrid">Print Grid</button> -->
				<div class="pdf-page">
   <kendo:grid name="categoryGrid" pageable="true" resizable="true" sortable="true" reorderable="true" selectable="true" scrollable="true" filterable="true" groupable="true">				
      <kendo:grid-pageable pageSizes="true" buttonCount="100" pageSize="100" previousNext="true" input="true" numeric="true" ></kendo:grid-pageable>
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
 <%--  <kendo:grid-excel allPages="true" fileName="Kendo UI Grid Export.xlsx" filterable="true"  /> --%>
	 <kendo:grid-editable mode="popup" confirmation="Are you sure you want to remove this item?" />
	   <kendo:grid-columns>
	       	<kendo:grid-column title="Counter No" field="counterno" width="130px" filterable="true"/>
	       	<kendo:grid-column title="RDate" field="rdate" width="130px" filterable="true"/>
	       	<kendo:grid-column title="Total Amount" field="total_amount" width="130px" filterable="true"/>
			<kendo:grid-column title="Total Receipt" field="total_receipts" width="130px" filterable="true"/>
			<kendo:grid-column title="Cash" field="cash" filterable="true" width="170px"/>
			<kendo:grid-column title="Cheque" field="cheque" filterable="true" width="170px"/>
		    <kendo:grid-column title="DD" field="dd" width="140px" filterable="true"/>
		    
	   </kendo:grid-columns>
	   <kendo:dataSource pageSize="100" serverPaging="true" serverSorting="true" serverAggregates="true" serverFiltering="true" serverGrouping="true">
		 <kendo:dataSource-transport>
			<kendo:dataSource-transport-read url="${readCategoryUrl}" dataType="json" type="POST" contentType="application/json" />
			<kendo:dataSource-transport-parameterMap>
                	function(options){return JSON.stringify(options);}
            </kendo:dataSource-transport-parameterMap>
		 </kendo:dataSource-transport>

		 <kendo:dataSource-schema data="data" groups="data" total="total">
			<kendo:dataSource-schema-model id="id">
			  <kendo:dataSource-schema-model-fields>
			  <kendo:dataSource-schema-model-field name="counterno" type="string"/>
			  <kendo:dataSource-schema-model-field name="rdate" type="string"/>
			  <kendo:dataSource-schema-model-field name="total_amount" type="number"/>
			  <kendo:dataSource-schema-model-field name="total_receipts" type="number"/>
			    <kendo:dataSource-schema-model-field name="id" type="number"/>
			    <kendo:dataSource-schema-model-field name="cash" type="number"/>
			    <kendo:dataSource-schema-model-field name="cheque" type="number"/>
			    <kendo:dataSource-schema-model-field name="dd" type="number"/>
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

<script>

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
	        win = window.open('', '', 'width=1600, height=500'),
	        doc = win.document.open();

	    var htmlStart =
	            '<!DOCTYPE html>' +
	            '<html>' +
	            '<head>' +
	            '<meta charset="utf-8" />' +
	            '<title>Kendo UI Grid</title>' +
	            '<link href="http://kendo.cdn.telerik.com/' + kendo.version + '/styles/kendo.common.min.css" rel="stylesheet" /> ' +
	            '<style>' +
	            'html { font: 11pt sans-serif; }' +
	            '.k-grid { border-top-width: 0; }' +
	            '.k-grid, .k-grid-content { height: auto !important; }' +
	            '.k-grid-content { overflow: visible !important; }' +
	            'div.k-grid table { table-layout: auto; width: 100% !important; }' +
	            '.k-grid .k-grid-header th { border-top: 1px solid; }' +
	            '.k-grid tr td {border-bottom: 1px solid black;}'+
	            '.k-grid-toolbar, .k-grid-pager > .k-link { display: none; }' +
	            '</style>' +
	            '</head>' +
	            '<body> <center><h2>Daily Cash Counter Report</h2></center>';

	    var htmlEnd =
	            '</body>' +
	            '</html>';

	    var gridHeader = gridElement.children('.k-grid-header');
	    if (gridHeader[0]) {
	        var thead = gridHeader.find('thead').clone().addClass('k-grid-header');
	        printableContent = gridElement
	            .clone()
	                .children('.k-grid-header').remove()
	            .end()
	                .children('.k-grid-content')
	                    .find('table')
	                        .first()
	                            .children('tbody').before(thead)
	                        .end()
	                    .end()
	                .end()
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

</script>
<style>
</style>