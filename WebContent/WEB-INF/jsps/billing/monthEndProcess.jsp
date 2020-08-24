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
<c:url value="/monthlySalesSummaryReport/monthlySalesSummaryReportRead" var="readCategoryUrl" />


<script src="//cdnjs.cloudflare.com/ajax/libs/jszip/2.4.0/jszip.min.js"></script>
<script type="text/javascript">
	
	$(document).ready(function() {
		
		
		
		
		$("#categoryGrid").kendoGrid({
			pageable : {
				pageSizes : [ 5, 10, 20, 50, 100, 500, 1000, 2000 ]
			},

		});
		$('#fromDatenep').nepaliDatePicker({
			dateFormat : "%D, %M %d, %y",
			npdMonth : true,
			npdYear : true
			
		});

		$('#toDatenep').nepaliDatePicker({
			dateFormat : "%D, %M %d, %y",
			npdMonth : true,
			npdYear : true,
			onChange: function(){
		 		var rdngdtnep = $('#toDatenep').val();
		 		getEngDate(rdngdtnep);
		 	}
			
		});
		
		
		$('#monthEndScreen').show();
		$('#monthEnd').addClass('active');
		
		var activeMod="${activeModulesNew}";
		var module="Month End Process";
		var check=activeMod.indexOf(module) > -1;
		
		 if(check){
		}else{
			window.location.href="./accessDenied";
		}  
		 
	});

	
	function getEngDate(nepalidate){
		
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
		         
				 
				  		var currDate = new Date();
					 	var endDate = new Date(response);
					  
					 	if(currDate<endDate){
					 		alert("To date Nepali should not select future date");
					    	$('#toDatenep').val("");
					    }
				  
			  }
			  
			  
			  
			  
			});
		
	}	
	
</script>


<c:if test = "${not empty msg}"> 			        
        <script>		        
            var msg = "${msg}";
            swal({
                title: msg,
                text: "",
                confirmButtonColor: "#2196F3",
            });
        </script>
 </c:if>
			   
			   
<div class="page-content col-md-12">
	<div class="panel panel-flat">
		<div class="panel-body">
			<fieldset class="content-group">
				<legend class="text-bold">Month End Close</legend>
								
								
								<div class="row">
								
								 <div class="col-md-3">
										<div class="form-group">
										<label>Month Year</label>
										<select data-placeholder="Select" class="select" name="monthyear" id="monthyear" required="required">
												<option value="${latestNepaliMonth}">${monthDesc}</option>
										</select>
									</div>
								</div> 
								
								<div class="form-group col-md-4">
										<label>From Date <font color="red">*</font></label>
										<div class="input-group">
											<span class="input-group-addon"><i class="icon-calendar3"></i></span>
											<input name="fromDatenep" placeholder="Select From Date..."
												id="fromDatenep" class="form-control nepali-calendar" />
										</div>
									</div>
				
								<div class="form-group col-md-4">
									<label>To Date<font color="red">*</font></label>
									<div class="input-group">
										<span class="input-group-addon"><i class="icon-calendar3"></i></span>
										<input name="toDatenep" placeholder="Select To Date..."
											id="toDatenep" class="form-control nepali-calendar" />
									</div>
								</div>
								
								
							</div>
			</fieldset>
			
			
			<div class="text-right">
				<button type="button" class="btn bg-teal btn-ladda"  data-style="expand-right" data-spinner-size="20" onclick="clearButton();"><span class="ladda-label">Clear</span></button>
				
				<c:if test="${userName=='s_admin'}">
				<button type="button" class="btn bg-teal btn-ladda"  data-style="expand-right" data-spinner-size="20" onclick="getCloseMonthME();"><span class="ladda-label">Close ME Manual</span></button>
				</c:if>
				
				<button type="button" class="btn bg-teal btn-ladda"  data-style="expand-right" data-spinner-size="20" onclick="getCloseMonth();"><span class="ladda-label">Close Month End</span></button>
				<button type="button" class="btn bg-teal btn-ladda"  data-style="expand-right" data-spinner-size="20" onclick="getLedgerDetails();"><span class="ladda-label" >Generate Month End Report</span></button>
			
			</div><br>
			
			
			
			     <div class="panel panel-flat" id="showCloseMonthEnd" hidden="true">
						<div class="panel-body">
						 <form  action="./closeMonthEndProcessNew" method="POST">
							<input type="hidden" name="${_csrf.parameterName}"  value="${_csrf.token}" />
							<input type="text"  name="fromdt" id="fromdt" hidden="true">
							<input type="text" name="todt" id="todt" hidden="true">
							<fieldset class="content-group"> 
								
								<legend class="text-bold" style="color: red">Close Month End Process</legend>
									
							<div class="row">
								
								<div class="col-md-3">
									<div class="form-group">
										<label>Month Year&nbsp;<font color="red">*</font></label>
										<input type="text" class="form-control" name="monthyearnep" id="monthyearnep" readonly="readonly">
										
									</div>
								</div>
								
								<div class="col-md-3">
										<div class="form-group">
										<label>Opening Balance&nbsp;<font color="red">*</font></label>
										<input type="text" class="form-control" name="tot_arrears" id="tot_arrears" value="0" readonly="readonly">
										
									</div>
								</div>
								
								<div class="col-md-3">
										<div class="form-group">
										<label>Water Charges&nbsp;<font color="red">*</font></label>
										<input type="text" class="form-control" name="tot_water_charges" id="tot_water_charges"  value="0" readonly="readonly">
										
									</div>
								</div>
								
								<div class="col-md-3">
										<div class="form-group">
										<label>Sewerage Charges&nbsp;<font color="red">*</font></label>
										<input type="text" class="form-control" name="tot_sw_charges" id="tot_sw_charges"  value="0" readonly="readonly">
										
									</div>
								</div>
								
								<div class="col-md-3">
										<div class="form-group">
										<label>Meter Rent&nbsp;<font color="red">*</font></label>
										<input type="text" class="form-control" name="tot_mtr_charges" id="tot_mtr_charges"  value="0" readonly="readonly">
										
									</div>
								</div>
								
								<div class="col-md-3">
										<div class="form-group">
										<label>Miscellaneous&nbsp;<font color="red">*</font></label>
										<input type="text" class="form-control" name="tot_miscellaneous" id="tot_miscellaneous"  value="0" readonly="readonly">
										
									</div>
								</div>
								
								<div class="col-md-3">
										<div class="form-group">
										<label>Total Penalty&nbsp;<font color="red">*</font></label>
										<input type="text" class="form-control" name="tot_penalty" id="tot_penalty"  value="0" readonly="readonly">
										
									</div>
								</div>
								
								<div class="col-md-3">
										<div class="form-group">
										<label>Total Rebate&nbsp;<font color="red">*</font></label>
										<input type="text" class="form-control" name="tot_rebate" id="tot_rebate"  value="0" readonly="readonly">
										
									</div>
								</div>
								
								<div class="col-md-3">
										<div class="form-group">
										<label>Total Demand&nbsp;<font color="red">*</font></label>
										<input type="text" class="form-control" name="tot_net_amount" id="tot_net_amount"  value="0" readonly="readonly">
										
									</div>
								</div>
								
								<div class="col-md-3">
										<div class="form-group">
										<label>Total Received&nbsp;<font color="red">*</font></label>
										<input type="text" class="form-control" name="tot_collection" id="tot_collection"  value="0" readonly="readonly">
										
									</div>
								</div>
								
								<div class="col-md-3">
										<div class="form-group">
										<label>Closing Balance&nbsp;<font color="red">*</font></label>
										<input type="text" class="form-control" name="tot_close_bal" id="tot_close_bal"  value="0" readonly="readonly"> 
										
									</div>
								</div>
								
							</div>
									
								</fieldset>	
								
								<div class="text-right">
									<button type="submit" class="btn bg-teal btn-ladda"  data-style="expand-right" data-spinner-size="20" onclick="return checkConfirmation();" id="showclmonthbtn"><span class="ladda-label" >Submit</span></button>
								</div>
								
								
							</form>
							
						</div>			
					</div>
			
			
			
			<div class="wethear" style="overflow: scroll;" id="kendoledgerid">
				<fieldset class="content-group">
					<legend class="text-bold">Month End Close Report</legend>

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

								<kendo:grid-column title="Sl.No" field="slNo" width="70px"
									filterable="true" groupFooterTemplate="Total :"
									footerTemplate="Grand&nbsp;Total:" />
									
								<kendo:grid-column title="Ward" field="wardNo" width="90px"
									filterable="true" />
										
								<kendo:grid-column title="Open Bal"
									field="opening_balance" width="120px" filterable="true"
									aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(opening_balance,'n2') #" />
								<kendo:grid-column title="WC" field="water_charges"
									width="110px" filterable="true" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(water_charges, 'n2') #" />
								<kendo:grid-column title="SC" field="sw_charges"
									width="90px" filterable="true" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(sw_charges,'n2') #" />
								<kendo:grid-column title="MR" field="mtr_rent"
									filterable="true" width="80px" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(mtr_rent,'n2','0.00') #" />
								
								<kendo:grid-column title="Misc" field="misc" width="90px"
									filterable="true" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(misc, 'n2','0.00') #" />
									
								<kendo:grid-column title="AddPen" field="adpenalty"
									filterable="true" width="100px" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(adpenalty, 'n2','0.00') #" />
									
									
								<kendo:grid-column title="Penalty" field="penalty"
									filterable="true" width="100px" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(penalty, 'n2','0.00') #" />
									
								<kendo:grid-column title="PosAdj" field="posadj"
									filterable="true" width="90px" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(posadj, 'n2','0.00') #" />
									
									
								<kendo:grid-column title="NegAdj" field="negadj"
									filterable="true" width="90px" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(negadj, 'n2','0.00') #" />
									
								<kendo:grid-column title="Total" field="totalbill"
									filterable="true" width="100px" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(totalbill, 'n2','0.00') #" />
									
									
									
								<kendo:grid-column title="Rebate" field="rebate"
									filterable="true" width="95px" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(rebate, 'n2','0.00') #" />
									
								<kendo:grid-column title="AdvReb" field="advreb"
									filterable="true" width="95px" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(advreb, 'n2','0.00') #" />	
									
								<kendo:grid-column title="Paid" field="ramount"
									filterable="true" width="110px" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(ramount, 'n2','0.00') #" />
								
									
								<kendo:grid-column title="CloseBal" field="cbalance"
									filterable="true" width="120px" aggregates="sum"
									groupFooterTemplate="#: kendo.toString(sum, 'n2') #"
									footerTemplate="#:kendo.toString(cbalance, 'n2','0.00') #" />
								
								 <kendo:grid-column title="Type" field="contype"
									width="105px" filterable="true" />  
									
								<kendo:grid-column title="Category" field="concat"
									width="165px" filterable="true" />  
									
									
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
											<kendo:dataSource-schema-model-field name="wardNo"
												type="string" />
											 <kendo:dataSource-schema-model-field name="contype"
												type="string" />  

											<kendo:dataSource-schema-model-field name="opening_balance"
												type="number" />
											<kendo:dataSource-schema-model-field name="water_charges"
												type="number" />
											<kendo:dataSource-schema-model-field name="sw_charges"
												type="number" />
											<kendo:dataSource-schema-model-field name="mtr_rent"
												type="number" />
											<kendo:dataSource-schema-model-field name="adpenalty"
												type="number" />
											<kendo:dataSource-schema-model-field name="posadj"
												type="number" />
											<kendo:dataSource-schema-model-field name="misc"
												type="number" />
											<kendo:dataSource-schema-model-field name="penalty"
												type="number" />
											<kendo:dataSource-schema-model-field name="rebate"
												type="number" />
											<kendo:dataSource-schema-model-field name="advreb"
												type="number" />
											<kendo:dataSource-schema-model-field name="ramount"
												type="number" />
											<kendo:dataSource-schema-model-field name="cbalance"
												type="number" />
											<kendo:dataSource-schema-model-field name="negadj"
												type="number" />
											<kendo:dataSource-schema-model-field name="totalbill"
												type="number" />
											<kendo:dataSource-schema-model-field name="concat"
												type="string" />
											
												
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
	
	var opening_balance = 0;
	var water_charges = 0;
	var sw_charges = 0;
	var mtr_rent = 0;
	var totalbill = 0;
	var penalty = 0;
	var rebate = 0;
	var ramount = 0;
	var misc = 0;
	var posadj = 0;
	var negadj = 0;
	var cbalance = 0;
	var branch = "${BranchName}";
	var adpenalty=0;
	var advreb=0;
	function dataBound(e) {
		
		var data = this.dataSource.view();
		opening_balance = 0;
		water_charges = 0;
		sw_charges = 0;
		mtr_rent = 0;
		penalty = 0;
		rebate = 0;
		misc = 0;
		ramount = 0;
		cbalance = 0;
		posadj = 0;
		negadj = 0;
		adpenalty=0;
		totalbill=0;
		advreb=0;
		for (var i = 0; i < data.length; i++) {
			opening_balance = opening_balance + data[i].opening_balance;
			water_charges = water_charges + data[i].water_charges;
			sw_charges = sw_charges + data[i].sw_charges;
			misc = misc + data[i].misc;
			mtr_rent = mtr_rent + data[i].mtr_rent;
			adpenalty = adpenalty + data[i].adpenalty;
			penalty = penalty + data[i].penalty;
			rebate = rebate + data[i].rebate;
			ramount = ramount + data[i].ramount;
			cbalance = cbalance + data[i].cbalance;
			posadj = posadj + data[i].posadj;
			negadj = negadj + data[i].negadj;
			totalbill = totalbill + data[i].totalbill;
			advreb = advreb + data[i].advreb;
		}
		cbalance =parseFloat(cbalance).toFixed(2);
		posadj =parseFloat(posadj).toFixed(2);
		negadj =parseFloat(negadj).toFixed(2);
	}
	
	function onChange(arg) {
	
	}

	function checkConfirmation(){
		
	   var from_date=$('#fromDatenep').val();
	   var toDatenep=$('#toDatenep').val();
	   var monthyearn=$('#monthyear').val();
	   
	   $('#fromdt').val(from_date);
	   $('#todt').val(toDatenep);
	   
	   var monthy=from_date.split("-");
	   var monthto=toDatenep.split("-");
	   var monthyear=monthy[0]+""+monthy[1];
	   
	   if(monthyearn!=monthyear){
		   alert("From Date & To Date should select proper in MonthYear : "+monthyearn);
		   return false;
	   }
	   
	   if(parseInt(monthto[2])<27){
		   alert("Please select To Date should be last day in MonthYear : "+monthyearn);
		   return false;
	   } 
	   
	   var res="No";
	   $.ajax({
			type : "GET",
			url : "./checkMonthEndExists",	
			dataType : "text",
			data : {
				monthyear : monthyear,
			},
			cache : false,
			async : false,
			success : function(response)
			{	
				if(response=="Yes"){
					res="Yes";
				}else if(response=="No"){
					swal({
		                title: "Selected MonthYear already Closed...",
		                text: "",
		                confirmButtonColor: "#2196F3",
		            });	
				 return false;
				}else{
					
					swal({
		                title: "Sorry Something Went Wrong Please try again..",
		                text: "",
		                confirmButtonColor: "#2196F3",
		            });	
					 return false;
				}
			}

		});
		
	if(res=="Yes"){
	   
		if(confirm("Are You Sure You Want To Close Entering Receipt For The Day")){
			
			
			
		}else{
			return false;
		}
	}else{
		return false;
	}	
		
	}
	

	function getCloseMonth(){
		
		var from_date=$('#fromDatenep').val();
		var to_date=$('#toDatenep').val();
		
		if(from_date=="" || from_date==null){
			alert("Please select From Date");
			return false;
		}else if(to_date=="" || to_date==null){
			alert("Please select To Date");
			return false;
		}else{
		
		  $("#showCloseMonthEnd").show();
		  $("#kendoledgerid").hide();
		  
		  var monthy=from_date.split("-");
		  var monthyear=monthy[0]+""+monthy[1];
		  var tot_arrears=0;
		  var tot_water_charges=0;
		  var tot_sw_charges=0;
		  var tot_mtr_charges=0;
		  var tot_miscellaneous=0;
		  var tot_penalty=0;
		  var tot_net_amount=0; 
		  var tot_collection=0; 
		  var tot_close_bal=0;
	  	  var tot_rebate=0;

		  $("#loading").show();
		  
		  
		  
		  $.ajax({
				type : "GET",
				url : "./checkSession",
				dataType : "text",
				async : false,
				cache : false,
				success : function(response) {
					
					if(response=="false"){
						alert("Your Session is Timed Out.Please Login and try again!!");
						window.location.href="./login";
					}
				}
			});
			
			
			 $.ajax({
					type : "GET",
					url : "./getMonthEndCloseBalanceReport",	
					dataType : "json",
					data : {
						monthyear : monthyear,
						from_date : from_date,
						to_date : to_date,
					},
					cache : false,
					async : false,
					success : function(response)
					{	
							
						if(response.length==0)
						{
							swal({
					             title: "No Data Found.",
					             text: "",
					             confirmButtonColor: "#2196F3",
					         }); 
							$("#loading").hide(); 
							return false;
							
						}else{ 
							
										for( var i=0;i<response.length;i++)
										{
					 					  
										  data = response[i];
					 					  tot_arrears=parseFloat(tot_arrears)+parseFloat(data.opening_balance);
					 					  tot_water_charges=parseFloat(tot_water_charges)+parseFloat(data.water_charges);
					 					  tot_sw_charges=parseFloat(tot_sw_charges)+parseFloat(data.sw_charges);
					 					  tot_mtr_charges=parseFloat(tot_mtr_charges)+parseFloat(data.mtr_rent);
					 					  tot_miscellaneous=parseFloat(tot_miscellaneous)+parseFloat(data.misc);
					 					  tot_penalty=parseFloat(tot_penalty)+parseFloat(data.penalty);
					 					  tot_net_amount=parseFloat(tot_net_amount)+parseFloat(data.totalbill); 
					 					  tot_rebate=parseFloat(tot_rebate)+parseFloat(data.rebate); 
					 					  tot_collection=parseFloat(tot_collection)+parseFloat(data.ramount); 
					 					  tot_close_bal=parseFloat(tot_close_bal)+parseFloat(data.cbalance);
						 					  
										} 
					 			
						 			    $('#tot_arrears').val(parseFloat(tot_arrears).toFixed(2));
										$('#tot_water_charges').val(parseFloat(tot_water_charges).toFixed(2));
										$('#tot_sw_charges').val(parseFloat(tot_sw_charges).toFixed(2));
										$('#tot_mtr_charges').val(parseFloat(tot_mtr_charges).toFixed(2));
										$('#tot_miscellaneous').val(parseFloat(tot_miscellaneous).toFixed(2));
										$('#tot_penalty').val(parseFloat(tot_penalty).toFixed(2));
										$('#tot_net_amount').val(parseFloat(tot_net_amount).toFixed(2));
										$('#tot_collection').val(parseFloat(tot_collection).toFixed(2));
										$('#tot_close_bal').val(parseFloat(tot_close_bal).toFixed(2));
										$('#tot_rebate').val(parseFloat(tot_rebate).toFixed(2)); 
										$('#monthyearnep').val(monthyear);
										
					 			
		 			
				}
						
		
	}

	});
			 
			 
			 
			 $.ajax({
					type : "GET",
					url : "./checkBillCorrectionPending",	
					dataType : "text",
					data : {
						monthyear : monthyear,
					},
					cache : false,
					async : false,
					success : function(response)
					{	
						
						if(response=="Yes"){
							
							$('#showclmonthbtn').hide();
							alert("Bill Correction Approval is not finalized.Please Approve and then Close Month End!!!");
							return false;
						}
						
						if(response=="Exception"){
							alert("Something went Wrong Please Try again");
							return false;
						}
						

					}
					
				 });
				  
				  $.ajax({
						type : "GET",
						url : "./checkBillPenaltyAdjPending",	
						dataType : "text",
						data : {
							monthyear : monthyear,
						},
						cache : false,
						async : false,
						success : function(response)
						{	
							if(response=="Yes"){
								$('#showclmonthbtn').hide();
								alert("Bill Penalty Adjustment Approval is not finalized.Please Approve and then Close Month End!!!");
								return false;
							}
							
							if(response=="Exception"){
								alert("Something went Wrong Please Try again");
								return false;
							}
							

						}
					
					});
				  
				  
				  $.ajax({
						type : "GET",
						url : "./checkBillGenerationPending",	
						dataType : "text",
						data : {
							monthyear : monthyear,
						},
						cache : false,
						async : false,
						success : function(response)
						{	
							if(response=="Yes"){
								
								$('#showclmonthbtn').hide();
								/* swal({
						            title: "<span style='color:red;font-weight: bold;'>Bill Generation is pending!</span>",
						            text: "Please check Missed Bill Report and generate all bills, and then Close Month End!!!",
						            timer: 5000,
						            confirmButtonColor: "#2196F3",
						            html: true,
						        }); */
								
								
								swal({
									  title: "Bill Generation is pending!",
									  text: "Please check Missed Bill Report and generate all bills, and then Close Month End!!!",
									  type: "warning",
									  showCancelButton: true,
									  confirmButtonColor: "rgb(35, 164, 123)",
									  cancelButtonColor: "#fb8585",
									  confirmButtonText: "Missed Bill Report",
									  cancelButtonText: "Cancel",
									  closeOnConfirm: false,
									  closeOnCancel: true
									},
									function(isConfirm) {
									  if (isConfirm) {
										  window.location.href = './missedBillsReport';
									  } 
									});
								
								//alert("Bill Generation is pending, Please check Missed Bill Report and generate all bills, and then Close Month End!!!");
								return false;
							}
							
							if(response=="Exception"){
								alert("Something went Wrong Please Try again");
								return false;
							}
							

						}
					
					});
				  


		$("#loading").hide();
			 
	}

}
	
	
function getCloseMonthME(){
		
		var from_date=$('#fromDatenep').val();
		var to_date=$('#toDatenep').val();
		var monthyearnep="207309";
		if(from_date=="" || from_date==null){
			alert("Please select From Date");
			return false;
		}else if(to_date=="" || to_date==null){
			alert("Please select To Date");
			return false;
		}else{
			
			 $.ajax({
					type : "GET",
					url : "./clsMEManual",	
					dataType : "text",
					data : {
						monthyearnep : monthyearnep,
						from_date : from_date,
						to_date : to_date,
					},
					cache : false,
					async : false,
					success : function(response)
					{
						alert(response);
		
					}

	});
			
		}
		
}
	
	function getLedgerDetails()
	{
		 
		  $("#showCloseMonthEnd").hide();
		  $("#kendoledgerid").show();
		
		var from_date=$('#fromDatenep').val();
		var to_date=$('#toDatenep').val();
		
		if(from_date=="" || from_date==null){
			alert("Please select From Date");
			return false;
		}else if(to_date=="" || to_date==null){
			alert("Please select To Date");
			return false;
		}else{
		
		var monthy=from_date.split("-");
		var monthyear=monthy[0]+""+monthy[1];
		 
		 $('#connectionidspan').html("Monthly Sales Summary Report : "+monthyear);
		 
		 
		  $("#loading").show();
		
			 $.ajax({
					type : "GET",
					url : "./getMonthEndCloseBalanceReport",	
					dataType : "json",
					data : {
						monthyear : monthyear,
						from_date : from_date,
						to_date : to_date,
					},
					cache : false,
					async : false,
					success : function(response) {
						var grid = $("#categoryGrid").getKendoGrid();
						var data = new kendo.data.DataSource();
						grid.dataSource.data(response);
						grid.refresh();
					}
					
			 });
			 
			 $("#loading").hide();
			 
		}
			 
		 
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

		var fromDatenep = $("#fromDatenep").val();
		var toDatenep = $("#toDatenep").val();
		var gridElement = $('#categoryGrid'), printableContent = '', win = window
				.open('', '', 'width=auto, height=900'), doc = win.document
				.open();

		var htmlStart = '<!DOCTYPE html>'
				+ '<html>'
				+ '<head>'
				+ '<meta charset="utf-8" />'
				+ '<title>Monthly Sales Summary Report</title>'
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
				+ '</style>'
				+ '</head>'
				+ '<body> <center><h1>Kathmandu Upatyaka Khanepani Limited</h1>'
				+ '<h2>' + branch
				+ ' kathmandu</h2><h3>Monthly Sales Summary Report</h3>'
				+ '<h4>From :&nbsp;&nbsp;' + fromDatenep
				+ '&nbsp;&nbsp;&nbsp;To :&nbsp;&nbsp;' + toDatenep
				+ '</h4></center>';

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


	function clearButton() {

		window.location.href = "./monthEndProcess";
	}
</script>
<style>

.sweet-alert button.cancel {
    background-color: #db6c6c !important;
    color: #fff;
}

</style>