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
<c:url value="/meterReadingListReport/meterReadingListReportRead1" var="readCategoryUrl"/>

<script src="//cdnjs.cloudflare.com/ajax/libs/jszip/2.4.0/jszip.min.js"></script>
  	<script type="text/javascript">
  	

  	$( document ).ready(function() {
		
  		$('#umbillreport').show();
		$('#reportsbilling').addClass('active');
		
		var activeMod="${activeModulesNew}";
		var module="Billing Reports";
		var module1="Metering Reports";
		var check=activeMod.indexOf(module) > -1;
		var check1=activeMod.indexOf(module1) > -1;
		
		if(check){
		}else if(check1){
		}else{
			window.location.href="./accessDenied";
		}
		
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
	});

  	
 	</script>
   
    
   
	 <div class="page-content col-md-12" >
	<div class="panel panel-flat">
		<div class="panel-body">
		<fieldset class="content-group">
				<legend class="text-bold">Meter Reading List</legend> 
		<div>
				
				<div class="col-md-2">
						<label>Ward No</label><select data-placeholder="Select" class="select"  id="wardNo" name="wardNo" required="required">
						    <option value="" data-icon="icon-git-branch">Select</option>
							
							<c:forEach items="${wardNoList}" var="ward">
							<option value="${ward.wardNo}">${ward.wardNo}</option>
						   </c:forEach>
							
						</select>
				</div>
				
				
				<div class="form-group col-md-2">
					<label>Reading Day<font color="red">*</font></label>
						
						<select data-placeholder="Select" class="select"  name="reading_day" id="reading_day"> 
							<option value="0"  data-icon="icon-git-branch">Select</option>
							<option value="1" data-icon="icon-git-branch">1</option>
							<option value="2" data-icon="icon-git-branch">2</option>
							<option value="3" data-icon="icon-git-branch">3</option>
							<option value="4" data-icon="icon-git-branch">4</option>
							<option value="5" data-icon="icon-git-branch">5</option>
							<option value="6" data-icon="icon-git-branch">6</option>
							<option value="7" data-icon="icon-git-branch">7</option>
							<option value="8" data-icon="icon-git-branch">8</option>
							<option value="9" data-icon="icon-git-branch">9</option>
							<option value="10" data-icon="icon-git-branch">10</option>
							<option value="11" data-icon="icon-git-branch">11</option>
							<option value="12" data-icon="icon-git-branch">12</option>
							<option value="13" data-icon="icon-git-branch">13</option>
							<option value="14" data-icon="icon-git-branch">14</option>
							<option value="15" data-icon="icon-git-branch">15</option>
							<option value="16" data-icon="icon-git-branch">16</option>
							<option value="17" data-icon="icon-git-branch">17</option>
							<option value="18" data-icon="icon-git-branch">18</option>
							<option value="19" data-icon="icon-git-branch">19</option>
							<option value="20" data-icon="icon-git-branch">20</option>
							<option value="21" data-icon="icon-git-branch">21</option>
							<option value="22" data-icon="icon-git-branch">22</option>
							<option value="23" data-icon="icon-git-branch">23</option>
							<option value="24" data-icon="icon-git-branch">24</option>
							<option value="25" data-icon="icon-git-branch">25</option>
							<option value="26" data-icon="icon-git-branch">26</option>
							<option value="27" data-icon="icon-git-branch">27</option>
							<option value="28" data-icon="icon-git-branch">28</option>
							<option value="29" data-icon="icon-git-branch">29</option>
							<option value="30" data-icon="icon-git-branch">30</option>
						</select>
										
				</div>
				
				<div class="col-md-2">
						<label>Year</label><select data-placeholder="Select" class="select"  id="yearnep" name="yearnep" required="required">
						    <option value="" data-icon="icon-git-branch">Select</option>

						 <!--  <option value="2076">2076</option> --> 
							<c:forEach items="${yearList}" var="ward">
							<option value="${ward.year}">${ward.year}</option>
						   </c:forEach>
							
						</select>
				</div>
				
				<div class="form-group col-md-2">
					<label>Month<font color="red">*</font></label>
						
						<select data-placeholder="Select" class="select"  name="month" id="month"> 
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
						<label>Pipe Size</label>
						<select data-placeholder="Select" class="select"  id="pipeSize" name="pipeSize" required="required">
							<option value="0.5">SA</option>
							<option value="0.75">THA</option>
						</select>
				</div>
								
				<div class="col-md-2">
							<div class="form-group" style="padding-top: 27px;">
								<label>&nbsp;</label>
                                <button type="button" class="btn bg-teal btn-ladda" onclick="return getData()" data-style="expand-right" data-spinner-size="20">
								<span class="ladda-label">Get Consumer List</span></button>
                               </div>
				</div>
				
				
				
				
				
				
				
				
				
				</div>
				</fieldset>
				<div class="wethear" style="overflow: scroll;">
			<fieldset class="content-group">
				<legend class="text-bold">Meter Reading List</legend> 
				
				  <!-- <button type="button" class="k-button" id="printGrid">Print Grid</button> -->
				<div class="pdf-page">
   <kendo:grid name="categoryGrid" pageable="true"  change="onChange" resizable="true" sortable="true" reorderable="true" selectable="true" scrollable="true" filterable="true" groupable="true">				
      <kendo:grid-pageable pageSizes="true" buttonCount="100" pageSize="500" previousNext="true" input="true" numeric="true" ></kendo:grid-pageable>
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
	   
	       	<kendo:grid-column title="Sl." field="slNo" width="50px" filterable="true"/>
	       	<kendo:grid-column title="Con No" field="conn_no" width="75px" filterable="true"/>    
	       	<kendo:grid-column title="Area No" field="area_no" width="115px" filterable="true" />
	       	<kendo:grid-column title="Name" field="name" width="150px" filterable="true" />
	       	<kendo:grid-column title="Address" field="address" width="120px" filterable="true" />
	       	<kendo:grid-column title="Reading Status" field="reading_status" width="100px" filterable="true" />
	       	<kendo:grid-column title="Previous Observation" field="prev_obs" width="120px" filterable="true" />
	       	<kendo:grid-column title="Remarks" field="remarks" width="90px" filterable="true" />
	        <kendo:grid-column title="Tap" field="pipe_size" width="60px" filterable="true" />
	        <kendo:grid-column title="Meter No." field="meter_no" width="60px" filterable="true" />
	   </kendo:grid-columns>
	   <kendo:dataSource pageSize="100" >
		 <kendo:dataSource-transport>
			<kendo:dataSource-transport-read url="${readCategoryUrl}" dataType="json" type="POST" contentType="application/json" />
		 </kendo:dataSource-transport>
		 <kendo:dataSource-schema>
			<kendo:dataSource-schema-model id="id">
			  <kendo:dataSource-schema-model-fields>
			  <kendo:dataSource-schema-model-field name="slNo" type="number"/>
			  <kendo:dataSource-schema-model-field name="conn_no" type="number"/>
			  <kendo:dataSource-schema-model-field name="area_no" type="String"/> 
			  <kendo:dataSource-schema-model-field name="name" type="String"/>
			  <kendo:dataSource-schema-model-field name="address" type="String"/>
			  <kendo:dataSource-schema-model-field name="reading_status" type="String"/>
			  <kendo:dataSource-schema-model-field name="prev_obs" type="String"/>
			  <kendo:dataSource-schema-model-field name="remarks" type="String"/>
			  <kendo:dataSource-schema-model-field name="pipe_size" type="number"/>
			  <kendo:dataSource-schema-model-field name="meter_no" type="number"/>
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
	
	
	
	var wardnumber;
	var rdayp;
	var yearp;
	var monthp;
	var branch="${BranchName}";
	function printGrid() {
		
	    var gridElement = $('#categoryGrid'),
	        printableContent = '',
	        win = window.open('', '', 'width=auto, height=900'),
	        doc = win.document.open();

	    var htmlStart =
	            '<!DOCTYPE html>' +
	            '<html>' +
	            '<head>' +
	            '<meta charset="utf-8" />' +
	            '<title>Meter Reading List</title>' +
	            '<link href="http://kendo.cdn.telerik.com/' + kendo.version + '/styles/kendo.common.min.css" rel="stylesheet" /> ' +
	            '<style>' +
	            'html { font: 10pt sans-serif; }' +
	            '.k-grid { border-top-width: auto; }' +
	            '.k-grid, .k-grid-content { height: auto !important;}' +
	            '.k-grid-content { overflow: visible !important; }' +
	            'div.k-grid table { table-layout: 100%; width: 100% !important;}' +
	            '.k-grid .k-grid-header th { border-top: 1px solid; border-all: 1px solid !important; }' +
	            '.k-grid tr td {border-bottom: 1px solid black;}'+
	            '.k-grid-toolbar, .k-grid-pager > .k-link { display: none; }' +
	            '</style>' +
	            '</head>' +
	            '<body> <center><h2>Kathmandu Upatyaka Khanepani Limited</h2>' +
				'<h3>'+branch+' kathmandu</h3><h4>Meter Reading List</h4>' +
				'<h5>Ward Number :&nbsp;&nbsp;'+wardnumber+'&nbsp;&nbsp;&nbsp;MonthYear :&nbsp;&nbsp;'+yearp+''+monthp+'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Reading Day :&nbsp;'+rdayp+'&nbsp;&nbsp</h5></center>';
				
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
	    
	    function getEngDate(nepalidate,value){
	    	
	    	var date_nep=nepalidate;
	    	$.ajax({
	    		  type: "GET",
	    		  url: "./billing/onChangeNepaliDate",
	    		  dataType: "text",
	    		  async   : false,
	    		  data:{
	    			  
	    			 date_nep:date_nep,
	    			  
	    		  },
	    		  
	    		  success: function(response){
	    	         
	    			 
	    			  if(value==1){
	    		
	    			  }else{
	    				    var from_date=$("#from_date_nep").val();
	    				    var to_date=$("#to_date_nep").val();
	    				  	var startDate = new Date(from_date);
	    				 	var endDate = new Date(to_date);
	    				 	if(startDate>endDate){
	    				 		alert("To date Nepali should be greater than From Date in Nepali");
	    				    	$('#to_date_nep').val("");
	    				    }
	    			  }
	    		  }

	    		});
	    	
	    }
	    function getData(){
	    		
	    	    var wardNo=$("#wardNo").val();
			    var reading_day=$("#reading_day").val();
			    var yearnep=$("#yearnep").val();
			    var month=$("#month").val();
			    var pipeSize=$("#pipeSize").val();
			    //alert(pipeSize);
			    if(wardNo==""){
			    	alert("Please select ward number");
			    	return false;
			    }
			    else if(reading_day==""){
			    	alert("Please select Reading Day");
			    	return false;
			    }
			    else if(yearnep==""){
			    	alert("Please select Year");
			    	return false;
			    }
			    else if(month==""){
			    	alert("Please select Month");
			    	return false;
			    }
			    else if(pipeSize==""){
			    	alert("Please select Pipe Size");
			    	return false;
			    }
			    else{
			    	
			    	wardnumber=wardNo;
			    	rdayp=reading_day;
			    	yearp=yearnep;
			    	monthp=month;
			    	pipeSize=pipeSize;
			    	$("#loading").show(); 

			    	
			    	$.ajax({
			    		  type: "GET",
			    		  url: "./meterReadingListReport/meterReadingListReportRead",
			    		  dataType: "JSON",
			    		  async   : false,
			    		  data:{
			    			  wardNo:wardNo,
			    			  reading_day:reading_day,
			    			  yearnep:yearnep,
			    			  month:monthp,
			    			  pipeSize:pipeSize
			    		  },
			    		  
			    		  success: function(response){
			    			
			    			var grid = $("#categoryGrid").getKendoGrid();
			   				var data = new kendo.data.DataSource();
			   				grid.dataSource.data(response);
			   				grid.refresh();
			    		  }
			    		  
			    	});	
			    	
			    	$("#loading").hide(); 

			    }
			 	
	    }
</script>
<style>
</style>