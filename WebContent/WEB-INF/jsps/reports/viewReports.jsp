<%-- <meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
 --%>
 <%@include file="/WEB-INF/decorators/taglibs.jsp"%>
<script>
$(document).ready(function(){  
	
	$('#oldReportsScreen').show();
	$('#oldReports').addClass('active');
	
	var activeMod="${activeModulesNew}";
	var module="Old Reports";
	var check=activeMod.indexOf(module) > -1;
	
	if(check){
	}else{
		window.location.href="./accessDenied";
	}
	
	$('#nepaliRdate').nepaliDatePicker({
		 dateFormat: "%D, %M %d, %y",
			npdMonth: true,
			npdYear: true
});
	$('#nepaliAdate').nepaliDatePicker({
		 dateFormat: "%D, %M %d, %y",
			npdMonth: true,
			npdYear: true
});
if('${listData.size()}'>0)
	{
	    $('#uniform-monthlyledgerradio span').addClass('checked');
	    $('#monthlyledger').attr('disabled',false);
	   $('#tableDate').show();
	   $("#monthlyLedgerSpan").html('<a href="#" id="excelExport" onclick=tableToExcel("monthlyLedger","MonthlyLedgerReport")><button class="btn bg-grey-400">Export to Excel</button></a>');
	}
	
if('${DaywiseMeterReading.size()}'>0)
{
    $('#uniform-daywisemrradio span').addClass('checked');
    $('#daywisemr').attr('disabled',false);
    $('#DaywiseMeterReading-table').show();
    $("#DaywiseMeterReadingSpan").html('<a href="#" id="excelExport" onclick=tableToExcel("DaywiseMeterReading","DaywiseMeterReadingReport")><button class="btn bg-grey-400">Export to Excel</button></a>');
}
if('${DetailedCollectionSummary.size()}'>0)
{
	$('#uniform-detailedcollectionsummaryradio span').addClass('checked');
    $('#detailedcollectionsummary').attr('disabled',false);
    $('#DetailedCash-table').show();
    $("#DetailedCash-tableSpan").html('<a href="#" id="excelExport" onclick=tableToExcel("detailedCollectionTableId","DetailedCollectionSummaryReport")><button class="btn bg-grey-400">Export to Excel</button></a>');
}
if('${WeeklyMeterReading.size()}'>0)
{
    $('#uniform-weeklymrradio span').addClass('checked');
    $('#weeklymr').attr('disabled',false);
    $('#WeeklyMeterReading-table').show();
    $("#WeeklyMeterReadingSpan").html('<a href="#" id="excelExport" onclick=tableToExcel("WeeklyMeterReading","WeeklyMeterReadingReport")><button class="btn bg-grey-400">Export to Excel</button></a>');
}

if('${MonthlyCollectionSummary.size()}'>0)
{
    $('#uniform-monthlycollectionsummaryradio span').addClass('checked');
    $('#MonthlyCollectionSummary').attr('disabled',false);
    $('#MonthlyCollectionSummary-table').show();
    $("#MonthlyCollectionSummarySpan").html('<a href="#" id="excelExport" onclick=tableToExcel("MonthlyCollectionSummary","MonthlyCollectionSummary")><button class="btn bg-grey-400">Export to Excel</button></a>');
}

if('${DailyCollection.size()}'>0)
{
    $('#uniform-dailycollectionradio span').addClass('checked');
    $('#DailyCollection').attr('disabled',false);
    $('#DailyCollection-table').show();
    $("#DailyCollectionSpan").html('<a href="#" id="excelExport" onclick=tableToExcel("DailyCollection","DailyCollection")><button class="btn bg-grey-400">Export to Excel</button></a>');
}

if('${CounterwisedailyCollection.size()}'>0)
{
    $('#uniform-counterwisedailycollectionradio span').addClass('checked');
    $('#counterwisedailycollection').attr('disabled',false);
    $('#CounterwisedailyCollection-table').show();
    $("#CounterwisedailyCollectionSpan").html('<a href="#" id="excelExport" onclick=tableToExcel("CounterwisedailyCollection","CounterwiseDailyCollection")><button class="btn bg-grey-400">Export to Excel</button></a>');
}

if('${MonthlyCollectionDetail.size()}'>0)
{
    $('#uniform-monthlycollectiondetailradio span').addClass('checked');
    $('#monthlycollectiondetail').attr('disabled',false);
    $('#MonthlyCollectionDetail-table').show();
    $("#MonthlyCollectionDetailSpan").html('<a href="#" id="excelExport" onclick=tableToExcel("MonthlyCollectionDetail","MonthlyCollectionDetails")><button class="btn bg-grey-400">Export to Excel</button></a>');
}

if('${MonthwiseMeterReading.size()}'>0)
{
    $('#uniform-monthlymrreadingradio span').addClass('checked');
    $('#monthlymrreading').attr('disabled',false);
    $('#MonthwiseMeterReading-table').show();
    $("#MonthwiseMeterReadingSpan").html('<a href="#" id="excelExport" onclick=tableToExcel("MonthwiseMeterReading","MonthwiseMeterReading")><button class="btn bg-grey-400">Export to Excel</button></a>');
}

}); 

</script>

<style>
 table{
    border-collapse: collapse;
    border: 1px solid #eaeaea;
    width: 60%;
}
table th,
table td
{
border: 1px solid #eaeaea;
    padding: 5px;
    width: 50%;
} 
select{
width: 100%; border: 1px solid #DDD; border-radius: 3px; height: 36px;
}
legend {
	text-transform: none;
	font-size: 16px;
	border-color: #F01818;
}
/* #trHeading {
    font-size: 120%;
} */
/* .topic td
{ 
height: 14px 
}; */
hr {
    display: block;
    height: 1px;
    border: 0;
    margin: 1em 0;
    padding: 0;
    background-color: red;
}

</style>


<script type="text/javascript">

 function generateReport(reportName)
{
	/* $('#report').addClass('glyphicon glyphicon-ok');
	return false; */
	var frNDate=$('#nepaliRdate').val();
	var frEDate=moment($('#engRdate').val()).format('DD-MM-YYYY');
	var toNDate=$('#nepaliAdate').val();
	var toEDate=moment($('#engAdate').val()).format('DD-MM-YYYY');
	var counterNoVal=$('#counterNo').val();
	var mrCodeVal=$('#mrCode').val();
	var wardno=$('#wardNo').val();
	
	$('#neplaliFMonth').val(frNDate);
	$('#engFmonth').val(frEDate);
	$('#neplaliTMonth').val(toNDate);
	$('#engTmonth').val(toEDate);
	$('#counterNoSel').val(counterNoVal);
	$('#mrcodeSel').val(mrCodeVal);
	$('#reportName').val(reportName);
	$('#wardnoForm').val(wardno);
	
	$('#generateRpt').submit();
	
	/* $.ajax({
   	    type :"post",
        dataType :'json',
        data :$('#generateRpt').serialize(),
   	    url :"./generateReports",
 		async:false,
    	cache:false,
 		success : function(response)
 		  {
 			var arrayLength=response[0].length;
 			alert(arrayLength);
   		 var html2 = "<table  style='height: 60px;width: 60px; border-collapse: collapse;border:1px solid;'>" +
					   		"<thead style='border:1px solid;background-color:#a3c2c2'> " +
					   		"	<tr > " +
					   		"	   <th style='border:1px solid'>SIno.</th> " +
					   		"		<th style='border:1px solid'>ConnectionNo</th> " +
					   		"		<th style='border:1px solid' >ReceiptNo.</th> " +
					   		"		<th style='border:1px solid'>ReceiptDate(ENG) </th> " +
					   		"		<th>ChequeNo.</th> " +
					   		"		<th>PayMode</th> " +
					   		"		<th>Amount</th> " +
					   		"		<th>BankName</th> " +
					   		"		<th>Towards</th> " +
					   		"		<th>Towards</th> " +
					   		"		<th>Towards</th> " +
					   		"		<th>Towards</th> " +
					   		"		<th>Towards</th> " +
					   		"		<th>Towards</th>" +
					   		"	</tr> " +
					   		"</thead> " +
					   		"<tbody >";
	    	       		  for(var j=0;j<response.length;j++)
				    		{		
	    	       			html2 += '<tr>';
	    	       			 for(var m=0;m<arrayLength;m++)
					    		{
	    	       				 html2 += '<td>'+response[j][m]+'</td>'; 
					    		}
				    		     html2 +='</tr>';
				    		} 
   	       			
   	    	  
 			
 			
 			
 			
 			/* var arrayLength=response.length;
 			var html 
 			//window.open('http://www.example.com','mywindow','width=400,height=200')
 			   //my_window=window.open("./wrongPosting", "mywindow1", "status=1,width=1500,height=1500"); 
 			my_window=window.open("", "mywindow1", "status=1,width=1500,height=1500"); 
 		    my_window.document.write(html2);
 		  }
}); */
	return false;
}
 

function disableOptions(checkId,inputId)
{
	var typeValue = document.getElementById(checkId+'').type;
	if(typeValue=='radio')
	{
		$('#tableForm').find('button').attr('disabled','disabled');
	}
	if($('#'+checkId).prop('checked')==true)
	{
		$("#"+inputId).prop( "disabled", false );
	}
}
</script>
<div class="panel panel-flat">
						

						<div class="panel-body" id="wrongPostingDiv">
							<!-- <form class="form-horizontal" action="#">-->
							<div class="row" hidden="true" id="alertDiv">
								    </div>
							<!-- <form action="" method="post" id="wrongPost"> -->
							<!-- <fieldset class="content-group" > -->
								<legend class="text-bold">Reports</legend> 
										
										<div class="row">
											<div class="col-md-3">
												<div class="form-group">
													<label>From Billed Month in Nepali/Eng</label>
													<div class="input-group">
								                <span class="input-group-addon " ><i class="icon-calendar"></i></span>
											 <input type="text" id="nepaliRdate" id="nepaliRdate"  class="form-control nepali-calendar"
												placeholder="nepal date">
				                                </div>
											</div>
											</div>
											
											<div class="col-md-3">
												<div class="form-group">
													<label>&nbsp;</label>
					                                <div class="input-group">
												<span class="input-group-addon " ><i class="icon-calendar"></i></span>
												<input type="text" class="form-control daterange-single" placeholder="dd/MM/yyyy"  id="engRdate" name="engRdate">
											</div>
				                                </div>
											</div>
											
											<div class="col-md-3">
												<div class="form-group">
													<label>To Billed Month in Nepali/Eng</label>
													<div class="input-group">
								             <span class="input-group-addon " ><i class="icon-calendar"></i></span>
											 <input type="text"  id="nepaliAdate" id="nepaliAdate" class="form-control nepali-calendar"
												placeholder="nepal date">
				                                </div>
											</div>
											</div>
											
											
											<div class="col-md-3">
												<div class="form-group">
													<label>&nbsp;</label>
					                                <div class="input-group">
												<span class="input-group-addon " ><i class="icon-calendar"></i></span>
												<input type="text"  class="form-control daterange-single " placeholder="dd/MM/yyyy"  id="engAdate" name="engAdate">
											</div>
				                                </div>
											</div>
											
											</div>
											
											<div class="row">
											<div class="col-md-4">
												<div class="form-group">
													<label>Counter No.</label>
													<select  class="form-control select" id="counterNo" id="counterNo" >
			                                <option Value="0">Select</option>
			                                					  <c:forEach var="element" items="${counterList}">
			                                                       <option Value="${element}">${element}</option>
			                                                      </c:forEach>
												</select>
											</div>
											</div>
											<div class="col-md-4">
												<div class="form-group">
													<label>Meter Reader</label>
													<select  class="form-control select" id="mrCode" id="mrCode" >
                                					<option Value="0">Select</option>
												</select>
				                                </div>
											</div>
											<div class="col-md-4">
												<div class="form-group">
													<label>Ward No</label>
													<select  class="form-control select" id="wardNo" id="wardNo" >
                                                       <option Value="0">Select</option>
                                                      <c:forEach var="element" items="${wardList}">
                                                       <option Value="${element}">${element}</option>
                                                      </c:forEach>
									                </select>
				                                </div>
											</div>
											</div>
											<!-- </fieldset>
											<fieldset class="content-group" >  -->
								<legend class="text-bold">List Of Reports</legend>
								 <!-- border-collapse: collapse;
    border: 1px solid #eaeaea;
    width: 60%; -->
		<table id="tableForm" style="height: 10%;width: 100%;border:1px solid #eaeaea;border-collapse:collapse" >
					  <tr >
						  <td  style="width: 25%;" ><input name="stacked-radio-left" class="styled" type="radio" onclick="disableOptions(this.id,'daywisemr')" id="daywisemrradio">
						  &nbsp;&nbsp;Day wise Meter Reading Report</td>
						  <td  style="width: 25%;"   align="center"><button class="btn bg-teal-600" onclick="return generateReport('DaywiseMeterReading');" disabled="disabled" id="daywisemr" >Generate&nbsp;&nbsp;<i id="report" ></i></button></td>
					      <td  style="width: 25%;"   style="width: 25%;"    ><input name="stacked-radio-left" class="styled" type="radio" onclick="disableOptions(this.id,'dailycollection')" id="dailycollectionradio">&nbsp;&nbsp;Daily Collection Report</td>
						  <td  style="width: 25%;"   align="center"><button class="btn bg-teal-600" onclick="return generateReport('DailyCollection');"disabled="disabled" id="dailycollection">Generate&nbsp;&nbsp;</button></td>
					  </tr>
					<!--   <tr >
						  <td  style="width: 25%;"   style="width: 25%;"    ><input name="stacked-radio-left" class="styled" type="radio" onclick="disableOptions(this.id,'dailycollection')" id="dailycollectionradio">&nbsp;&nbsp;Daily Collection Report</td>
						  <td  style="width: 25%;"   align="center"><button class="btn bg-teal-600" onclick="return generateReport('DailyCollection');"disabled="disabled" id="dailycollection">Generate&nbsp;&nbsp;</button></td>
					  </tr> -->
					  <tr >
						  <td  style="width: 25%;"    ><input name="stacked-radio-left" class="styled" type="radio" onclick="disableOptions(this.id,'counterwisedailycollection')" id="counterwisedailycollectionradio" >
						  &nbsp;&nbsp;Counter wise daily Collection Report</td>
						  <td  style="width: 25%;"   align="center"><button class="btn bg-teal-600" onclick="return generateReport('CounterwisedailyCollection');"disabled="disabled"disabled="disabled" id="counterwisedailycollection">Generate&nbsp;&nbsp;</button></td>
 							<td  style="width: 25%;"    ><input name="stacked-radio-left" class="styled" type="radio" onclick="disableOptions(this.id,'monthlycollectiondetail')" id="monthlycollectiondetailradio">
						  &nbsp;&nbsp;Monthly Collection Detail Report</td>
						  <td  style="width: 25%;"   align="center"><button class="btn bg-teal-600" onclick="return generateReport('MonthlyCollectionDetail');"disabled="disabled" id="monthlycollectiondetail">Generate&nbsp;&nbsp;</button></td>
 							 <!-- <td  style="width: 25%;"    ><input name="stacked-radio-left" class="styled" type="radio" onclick="disableOptions(this.id,'newconnection')" id="newconnectionradio" >
						  &nbsp;&nbsp;New Connections Report</td>
						  <td  style="width: 25%;"  align="center" ><button class="btn bg-teal-600" onclick="return generateReport('NewConnections');"disabled="disabled" id="newconnection">
						  Generate&nbsp;&nbsp;</button></td> -->
 					 </tr>
					  <!-- <tr >
						  <td  style="width: 25%;"    ><input name="stacked-radio-left" class="styled" type="radio" onclick="disableOptions(this.id,'newconnection')" id="newconnectionradio" >
						  &nbsp;&nbsp;New Connections Report</td>
						  <td  style="width: 25%;"  align="center" ><button class="btn bg-teal-600" onclick="return generateReport('NewConnections');"disabled="disabled" id="newconnection">
						  Generate&nbsp;&nbsp;</button></td>
					  </tr> -->
					  
					  <tr >
						  <td  style="width: 25%;"    ><input name="stacked-radio-left" class="styled" type="radio" onclick="disableOptions(this.id,'monthlyledger')" id="monthlyledgerradio">
						  &nbsp;&nbsp;Monthly Ledger Report</td>
						  <td  style="width: 25%;"   align="center"><button class="btn bg-teal-600" onclick="return generateReport('MonthlyLedger');"disabled="disabled" id="monthlyledger">Generate&nbsp;&nbsp;</button></td>
					  <td  style="width: 25%;"    ><input name="stacked-radio-left" class="styled" type="radio" onclick="disableOptions(this.id,'weeklymr')" id="weeklymrradio">
						  &nbsp;&nbsp;  Weekly  Meter Reading Report </td>
						  <td  style="width: 25%;"  align="center" ><button class="btn bg-teal-600" onclick="return generateReport('WeeklyMeterReading');"disabled="disabled" id="weeklymr">Generate&nbsp;&nbsp;</button></td>
					  
					  <!-- <td  style="width: 25%;"    ><input name="stacked-radio-left" class="styled" type="radio" onclick="disableOptions(this.id,'monthlymrreading')" id="monthlymrreadingradio">
						  &nbsp;&nbsp;Month wise Meter Reading Report</td>
						  <td  style="width: 25%;"   align="center"><button class="btn bg-teal-600" onclick="return generateReport('MonthwiseMeterReading');"disabled="disabled" id="monthlymrreading">Generate&nbsp;&nbsp;</button></td> -->
					  </tr>
					  
					  
					 <!--  <tr >
						  <td  style="width: 25%;"    ><input name="stacked-radio-left" class="styled" type="radio" onclick="disableOptions(this.id,'monthlymrreading')" id="monthlymrreadingradio">
						  &nbsp;&nbsp;Monthly wise Meter Reading Report</td>
						  <td  style="width: 25%;"   align="center"><button class="btn bg-teal-600" onclick="return generateReport();"disabled="disabled" id="monthlymrreading">Generate&nbsp;&nbsp;</button></td>
					  </tr> -->
					  
					  <tr >
						  <!-- <td  style="width: 25%;"    ><input name="stacked-radio-left" class="styled" type="radio" onclick="disableOptions(this.id,'mrconsumerlist')" id="mrconsumerlistradio">
						  &nbsp;&nbsp;Meter Reader wise Customer List</td>
						  <td  style="width: 25%;"   align="center"><button class="btn bg-teal-600" onclick="return generateReport('MonthlywiseMeterReading');"disabled="disabled" id="mrconsumerlist">Generate&nbsp;&nbsp;</button></td> -->
					   <td  style="width: 25%;"    ><input name="stacked-radio-left" class="styled" type="radio" onclick="disableOptions(this.id,'monthlycollectionsummary')" id="monthlycollectionsummaryradio">
						  &nbsp;&nbsp;Monthly Collection Summary Report</td>
						  <td  style="width: 25%;"   align="center"><button class="btn bg-teal-600" onclick="return generateReport('MonthlyCollectionSummary');"disabled="disabled" id="monthlycollectionsummary">Generate&nbsp;&nbsp;</button></td>
					 
					 <td  style="width: 25%;"    ><input name="stacked-radio-left" class="styled" type="radio" onclick="disableOptions(this.id,'detailedcollectionsummary')" id="detailedcollectionsummaryradio">
						  &nbsp;&nbsp;Detailed Cash Collection Report</td>
						  <td  style="width: 25%;"   align="center"><button class="btn bg-teal-600" onclick="return generateReport('DetailedCollectionSummary');"disabled="disabled" id="detailedcollectionsummary">Generate&nbsp;&nbsp;</button></td>
					  
					 
					  </tr>
					  
					  <!-- <tr >
						  <td  style="width: 25%;"    ><input name="stacked-radio-left" class="styled" type="radio" onclick="disableOptions(this.id,'monthlycollectionsummary')" id="monthlycollectionsummaryradio">
						  &nbsp;&nbsp;Monthly Collection Summary Report</td>
						  <td  style="width: 25%;"   align="center"><button class="btn bg-teal-600" onclick="return generateReport('MonthlyCollectionSummary');"disabled="disabled" id="monthlycollectionsummary">Generate&nbsp;&nbsp;</button></td>
					  </tr> -->
					  
					   <!-- <tr >
						  <td  style="width: 25%;"    ><input name="stacked-radio-left" class="styled" type="radio" onclick="disableOptions(this.id,'monthlycollectiondetail')" id="monthlycollectiondetailradio">
						  &nbsp;&nbsp;Monthly Collection Detail Report</td>
						  <td  style="width: 25%;"   align="center"><button class="btn bg-teal-600" onclick="return generateReport('MonthlyCollectionDetail');"disabled="disabled" id="monthlycollectiondetail">Generate&nbsp;&nbsp;</button></td>
					   <td  style="width: 25%;"    ><input name="stacked-radio-left" class="styled" type="radio" onclick="disableOptions(this.id,'meterinstallation')" id="meterinstallationradio">
						  &nbsp;&nbsp; Meter Installation Report</td>
						  <td  style="width: 25%;"   align="center"><button class="btn bg-teal-600" onclick="return generateReport('MeterInstallation');"disabled="disabled" id="meterinstallation">Generate&nbsp;&nbsp;</button></td>
					  </tr> -->
					  
					 <!--  <tr >
						  <td  style="width: 25%;"    ><input name="stacked-radio-left" class="styled" type="radio" onclick="disableOptions(this.id,'meterinstallation')" id="meterinstallationradio">
						  &nbsp;&nbsp; Meter Installation Report</td>
						  <td  style="width: 25%;"   align="center"><button class="btn bg-teal-600" onclick="return generateReport('MeterInstallation');"disabled="disabled" id="meterinstallation">Generate&nbsp;&nbsp;</button></td>
					  </tr> -->
					  
					  <!-- <tr >
						  <td  style="width: 25%;"    ><input name="stacked-radio-left" class="styled" type="radio" onclick="disableOptions(this.id,'weeklymr')" id="weeklymrradio">
						  &nbsp;&nbsp;  Weekly  Meter Reading Report </td>
						  <td  style="width: 25%;"  align="center" ><button class="btn bg-teal-600" onclick="return generateReport('WeeklyMeterReading');"disabled="disabled" id="weeklymr">Generate&nbsp;&nbsp;</button></td>
					  <td  style="width: 25%;"    ><input name="stacked-radio-left" class="styled" type="radio" onclick="disableOptions(this.id,'branchtrailbalance')" id="branchtrailbalanceradio">
						  &nbsp;&nbsp;  Branch wise Trail balance </td>
						  <td  style="width: 25%;"   align="center"><button class="btn bg-teal-600" onclick="return generateReport('BranchwiseTrailbalance');"disabled="disabled" id="branchtrailbalance">Generate&nbsp;&nbsp;</button></td>
					  </tr> -->
					  
					  <!-- <tr >
						  <td  style="width: 25%;"    ><input name="stacked-radio-left" class="styled" type="radio" onclick="disableOptions(this.id,'branchtrailbalance')" id="branchtrailbalanceradio">
						  &nbsp;&nbsp;  Branch wise Trail balance </td>
						  <td  style="width: 25%;"   align="center"><button class="btn bg-teal-600" onclick="return generateReport('BranchwiseTrailbalance');"disabled="disabled" id="branchtrailbalance">Generate&nbsp;&nbsp;</button></td>
					  </tr> -->
					  
						</table>
								
								<!-- </fieldset> -->
								<div id="tableDate" hidden="true">
											<legend class="text-bold"><span id="reportNamedd">${reportName}</span></legend>
											<div id="monthlyLedgerSpan">  </div> 
								<div style="overflow-x:auto;">
								
								
								<table class="table  table-bordered" id="monthlyLedger">
											<thead >
												<tr class="bg-teal-600">
													<th >ConnectionNo.</th>
													<th >Rdate</th>
													<th >Present Reading</th>
													<th >Previous Reading</th>
													<th >Consumption</th>
													<th >Water Charge</th>
													<th >Service Charge</th>
													<th >Arrears</th>
													<th >Net Amount</th>
													<th >Bill Date</th>
													<th >Ward No</th>
												</tr>
					
											</thead>
											<tbody>
												<c:forEach var="data" items="${listData}"> 
												<tr>
												<c:forEach var="i" begin="0" end="${arrayLength-1}">
													<td>${data[i]}</td>
													</c:forEach>
												</tr>
												</c:forEach>
											</tbody>
										</table>
										</div>
										</div>
										
										
										</div>
										</div>
										
										
										
										<div id="DaywiseMeterReading-table" hidden="true">
											<legend class="text-bold"><span id="reportNamedd">${reportName}</span></legend>
											<div id="DaywiseMeterReadingSpan"></div>
								<div style="overflow-x:auto;">
								
								
								<table class="table  table-bordered" id="DaywiseMeterReading" style="width: 100%;">
											<thead >
												<tr class="bg-teal-600">
													<th style="width: 10%;">Reading Date</th>
													<th style="width: 10%;">Total Connections Billed</th>
													<th style="width: 10%;">Total Consumption(KL-Metered)</th>
													<th style="width: 10%;">Total Demand</th>
												</tr>
					
											</thead>
											<tbody>
												<c:forEach var="data" items="${DaywiseMeterReading}"> 
												<tr>
												<c:forEach var="i" begin="0" end="${DaywiseMeterReadingLength-1}">
													<td style="width: 10%;">${data[i]}</td>
													</c:forEach>
												</tr>
												</c:forEach>
											</tbody>
										</table>
										</div>
										</div>
										
										
										
										<div id="DetailedCash-table" hidden="true">
											<legend class="text-bold"><span id="reportNamedd">${reportName}</span></legend>
											<div id="DetailedCash-tableSpan"></div>
								        <div style="overflow-x:auto;">
								
										<table class="table  table-bordered" id="detailedCollectionTableId">
											<thead >
												<tr class="bg-teal-600">
													<th>Connection No.</th>
													<th>Amount</th>
													<th>Receipt No</th>
													<th>Receipt Date</th>
													<th>Counter No</th>
													<th>Towards</th>
													<th>Receipt Type</th>
													<th>Payment Mode</th>
													<th>Cheque/DD No<th>
													<th>Water Charges</th>
													<th>Sewarage Charges</th>
													<th>Meter Charges</th>
													<th>Miscellaneous</th>
													<th>Penalty</th>
													<th>Rebate</th>
													<th>Total to be Paid</th>
													<th>Advance</th>
													<th>Advance Rebate</th>
													<th>Old Balance</th>
												</tr>
					
											</thead>
											<tbody>
												 <c:forEach var="data" items="${DetailedCollectionSummary}"> 
												<tr>
												<c:forEach var="i" begin="0" end="${DetailedCollectionSummarylength-1}">
													<td>${data[i]}</td>
													</c:forEach>
												</tr>
												</c:forEach>
											</tbody>
										</table>
										</div>
										</div>
										
										
										<div id="WeeklyMeterReading-table" hidden="true">
											<legend class="text-bold"><span id="reportNamedd">${reportName}</span></legend>
											<div id="WeeklyMeterReadingSpan"></div>
								<div style="overflow-x:auto;">
								
								
								<%-- <table class="table  table-bordered" id="WeeklyMeterReading" style="width: 100%;">
											<thead >
												<tr class="bg-teal-600">
												<th style="width: 10%;">Week</th>
													<th style="width: 10%;">Reading Date</th>
													<th style="width: 10%;">Total Connections Billed</th>
													<th style="width: 10%;">Toatl Consumption</th>
													<th style="width: 10%;">Total Demand</th>
												</tr>
					
											</thead>
											<tbody>
											<c:set value="1" var="count"/> 
												<c:forEach var="data" items="${WeeklyMeterReading}"> 
												
												<tr>
													 <c:choose>
														<c:when test="${data[5]==count}">
														<td style="width: 10%;">Week1</td>
														</c:when>
														<c:otherwise>
														<td style="width: 10%;"></td>
														</c:otherwise>
												</c:choose> 
												<c:forEach var="i" begin="0" end="${WeeklyMeterReadingLength-1}">
												   <c:if test="${i<4}">
													<td style="width: 10%;">${data[i]}</td>
													</c:if>
													</c:forEach>
												</tr><c:set value="${count}" var="count"/> 
												</c:forEach>
											</tbody>
										</table> --%>
										
										<table class="table  table-bordered" id="WeeklyMeterReading" style="width: 100%;">
											<thead >
												<tr class="bg-teal-600">
												<th style="width: 10%;">Date in Month</th>
													<th style="width: 10%;">Reading Date</th>
													<th style="width: 10%;">Total Connections Billed</th>
													<th style="width: 10%;">Toatl Consumption(KL-Metered)</th>
													<th style="width: 10%;">Total Demand</th>
												</tr>
					
											</thead>
											<tbody>
												<c:forEach var="data" items="${WeeklyMeterReading}"> 
												<tr>
												<c:forEach var="i" begin="0" end="${WeeklyMeterReadingLength-1}">
												  <td style="width: 10%;">${data[i]}</td>
													</c:forEach>
												</tr>
												</c:forEach>
											</tbody>
										</table> 
										
										</div>
										</div>
										
										
										
										<div id="CounterwisedailyCollection-table" hidden="true">
											<legend class="text-bold"><span id="reportNamedd">${reportName}</span></legend>
											<div id="CounterwisedailyCollectionSpan"></div>
								<div style="overflow-x:auto;">
								
								
								<table class="table  table-bordered" id="CounterwisedailyCollection" style="width: 100%;">
											<thead >
												<tr class="bg-teal-600">
													<th style="width: 10%;">CounterNo.</th>
													<th style="width: 10%;">Rdate</th>
													<th style="width: 10%;">Total Amount</th>
													<th style="width: 10%;">Total Receipts</th>
													<th style="width: 10%;">Cash Receipts</th>
													<th style="width: 10%;">Cheque Receipts</th>
													<th style="width: 10%;">DD Receipts</th>
												</tr>
					
											</thead>
											<tbody>
												<c:forEach var="data" items="${CounterwisedailyCollection}"> 
												<tr>
												<c:forEach var="i" begin="0" end="${CounterwisedailyCollectionLength-1}">
													<td style="width: 10%;">${data[i]}</td>
													</c:forEach>
												</tr>
												</c:forEach>
											</tbody>
										</table>
										</div>
										</div>
										
										
										
										<div id="DailyCollection-table" hidden="true">
											<legend class="text-bold"><span id="reportNamedd">${reportName}</span></legend>
											<div id="DailyCollectionSpan"></div>
								<div style="overflow-x:auto;">
								
								
								<table class="table  table-bordered" id="DailyCollection" style="width: 100%;">
											<thead >
												<tr class="bg-teal-600">
													<th style="width: 10%;">Rdate</th>
													<th style="width: 10%;">Total Amount</th>
													<th style="width: 10%;">Total Receipts</th>
													<th style="width: 10%;">Cash Receipts</th>
													<th style="width: 10%;">Cheque Receipts</th>
													<th style="width: 10%;">DD Receipts</th>
												</tr>
					
											</thead>
											<tbody>
												<c:forEach var="data" items="${DailyCollection}"> 
												<tr>
												<c:forEach var="i" begin="0" end="${DailyCollectionLength-1}">
													<td style="width: 10%;">${data[i]}</td>
													</c:forEach>
												</tr>
												</c:forEach>
											</tbody>
										</table>
										</div>
										</div>
										
										
										
										
										<div id="MonthlyCollectionSummary-table" hidden="true">
											<legend class="text-bold"><span id="reportNamedd">${reportName}</span></legend>
											<div id="MonthlyCollectionSummarySpan"></div>
								<div style="overflow-x:auto;">
								
								
								<table class="table  table-bordered" id="MonthlyCollectionSummary" style="width: 100%;">
											<thead >
												<tr class="bg-teal-600">
													<th style="width: 10%;">Rmonth</th>
													<th style="width: 10%;">Total Amount</th>
													<th style="width: 10%;">Total Receipts</th>
													<th style="width: 10%;">Cash Receipts</th>
													<th style="width: 10%;">Cheque Receipts</th>
													<th style="width: 10%;">DD Receipts</th>
												</tr>
					
											</thead>
											<tbody>
												<c:forEach var="data" items="${MonthlyCollectionSummary}"> 
												<tr>
												<c:forEach var="i" begin="0" end="${MonthlyCollectionSummaryLength-1}">
													<td style="width: 10%;">${data[i]}</td>
													</c:forEach>
												</tr>
												</c:forEach>
											</tbody>
										</table>
										</div>
										</div>
										
										<div id="MonthlyCollectionDetail-table" hidden="true">
											<legend class="text-bold"><span id="reportNamedd">${reportName}</span></legend>
											<div id="MonthlyCollectionDetailSpan"></div>
								<div style="overflow-x:auto;">
								
								
								<table class="table  table-bordered" id="MonthlyCollectionDetail" style="width: 100%;">
											<thead >
												<tr class="bg-teal-600">
													<th style="width: 10%;">Rdate</th>
													<th style="width: 10%;">Connection No.</th>
													<th style="width: 10%;">Amount</th>
													<th style="width: 10%;">Receipt No.</th>
													<th style="width: 10%;">Pay Mode</th>
													<th style="width: 10%;">Bank Name</th>
													<th style="width: 10%;">Towards</th>
												</tr>
					
											</thead>
											<tbody>
											<c:set var="dateValue" value="0"/>
												<c:forEach var="data" items="${MonthlyCollectionDetail}"> 
												<tr>
												<c:choose>
												   <c:when test="${data[0] ne dateValue}">
												   <td style="width: 10%;">${data[0]}</td>
												   </c:when>
												   <c:otherwise>
												   <td style="width: 10%;"></td>
												   </c:otherwise>
												</c:choose> 
												<c:set var="dateValue" value="${data[0]}"/>
												<c:forEach var="i" begin="0" end="${MonthlyCollectionDetailLength-1}">
												<c:if test="${i>0}">
												<td style="width: 10%;">${data[i]}</td>
												</c:if>
													</c:forEach>
												</tr>
												</c:forEach>
											</tbody>
										</table>
										</div>
										</div>
										
										
										
										
										<div id="MonthwiseMeterReading-table" hidden="true">
											<legend class="text-bold"><span id="reportNamedd">${reportName}</span></legend>
											<div id="MonthwiseMeterReadingSpan"></div>
								<div style="overflow-x:auto;">
								
								
								<table class="table  table-bordered" id="MonthwiseMeterReading" style="width: 100%;">
											<thead >
												<tr class="bg-teal-600">
													<th style="width: 10%;">Reading Month</th>
													<th style="width: 10%;">Total Connections Billed</th>
													<th style="width: 10%;">Total Consumption(KL-Metered)</th>
													<th style="width: 10%;">Total Demand</th>
												</tr>
					
											</thead>
											<tbody>
												<c:forEach var="data" items="${MonthwiseMeterReading}"> 
												<tr>
												<c:forEach var="i" begin="0" end="${MonthwiseMeterReadingLength-1}">
													<td style="width: 10%;">${data[i]}</td>
													</c:forEach>
												</tr>
												</c:forEach>
											</tbody>
										</table>
										</div>
										</div>
										
									     </div>
								<form action="./generateReports" method="post" id="generateRpt" hidden="true" >
									<input name="neplaliFMonth"  type="text"  id="neplaliFMonth">
									<input name="engFmonth"  type="text"  id="engFmonth">
									<input name="neplaliTMonth"  type="text"  id="neplaliTMonth">
									<input name="engTmonth"  type="text"  id="engTmonth">
									<input name="counterNoSel"  type="text"  id="counterNoSel">
									<input name="mrcodeSel"  type="text"  id="mrcodeSel">
									<input name="reportName"  type="text"  id="reportName">
									<input name="wardnoForm"  type="text"  id="wardnoForm">
									 <input type="hidden" name="${_csrf.parameterName}"  value="${_csrf.token}" /> 
								</form>
								<!-- <fieldset class="content-group" >  -->
								
										<!-- </fieldset> -->
								</div>
								
							
<style>
/* #sss {
    width: 1000px;
    overflow-x: scroll;
} */
/* 
.datatable-header, .datatable-footer {
    padding: 0px 0px 0 0px;
}

.dataTables_filter {
    display: none;
    float: left;
    margin: 0 0 20px 20px;
    position: relative;
}
.dataTables_length > label {
    margin-bottom: 0;
    display: none;
} */

</style>