<%@include file="/WEB-INF/decorators/taglibs.jsp"%>


	<script type="text/javascript" src="resources/layout_3/assets/js/plugins/buttons/spin.min.js"></script>
	<script type="text/javascript" src="resources/layout_3/assets/js/plugins/buttons/ladda.min.js"></script>
	<script type="text/javascript" src="resources/layout_3/assets/js/pages/components_buttons.js"></script>
	


<script type="text/javascript">

	function finalSubmit() {
		
		  swal({
	          title: "Bill Generated Successfully",
	          confirmButtonColor: "#2196F3"
	      });
	}

</script>


				<div class="panel panel-flat">
						

						<div class="panel-body">
							<!-- <form class="form-horizontal" action="#">-->
							
							<fieldset class="content-group"> 
								<legend class="text-bold">Bill Print Metered</legend>
										
										
									<div class="row">
											
											<div class="col-md-2">
												<label>Ward No</label><select data-placeholder="Select" class="select"  id="wardNumber" name="wardNo" onchange="getLedgerData()" required="required">
												    <option value="" data-icon="icon-git-branch">Select</option>
													
													<c:forEach items="${wardNoList}" var="ward">
													<option value="${ward.wardNo}">${ward.wardNo}</option>
												   </c:forEach>
													
												</select>
											</div>
											
											
											 <%-- <div class="col-md-3">
											  <div class="form-group">
												<label>Reading Month</label><select data-placeholder="Select" class="select" id="reading_month" name="reading_month" required="required">
												    <option value="" data-icon="icon-git-branch">Select</option>
													<option value="${latestNepaliMonth}" data-icon="icon-git-branch">${monthDesc}</option>
													
												</select>
											 </div>
										    </div> --%>
										    
										    			 
										    
										    <div class="col-md-2" >
												<label>Year</label>
												
												<select data-placeholder="Select" class="select" id="yearnep" name="yearnep" required="required">
													<c:forEach items="${yearList}" var="ward">
													<option value="${ward.year}">${ward.year}</option>
												   </c:forEach>
												</select>
													 
											 </div>
							   
											   <div class="col-md-2" >
												<label>Month</label>
												<select data-placeholder="Select" class="select"  name="monthnep" id="monthnep" onchange="getLedgerData()"> 
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
											<div class="form-group">
												<label>Reading Day</label><select data-placeholder="Select" class="select" id="reading_day" name="reading_day" onchange="getLedgerData()" required="required">
												    <option value="" data-icon="icon-git-branch">Select</option>
													
													<c:forEach items="${readingDayList}" var="rday">
													<option value="${rday.readingDay}">${rday.readingDay}</option>
												   </c:forEach>
													
												</select>
											</div>
										   </div>
										   
										   <div class="form-group col-md-2">
												<label>Connection Category <font color="red">*</font></label>
												<select class="select" onchange="getLedgerData()"
													name="con_category1" id="con_category1">
													<option value="All" data-icon="icon-git-branch">All</option>
													<option value="Domestic" data-icon="icon-git-branch">Domestic</option>
													<option value="Government" data-icon="icon-git-commit">Government</option>
													<option value="Industry and Company" data-icon="icon-git-commit">Industry and Company</option>
												</select>
											</div>
					
										   <div class="col-md-2">
											<div class="form-group">
												<label>Pipe Size</label><select data-placeholder="Select" class="select" id="pipe_size" name="pipe_size" onchange="getLedgerData()" required="required">
												    <option value="" data-icon="icon-git-branch">Select</option>
													<option value="0.5" data-icon="icon-git-branch">SA</option>
													<option value="0.75" data-icon="icon-git-branch">THA</option>
													
												</select>
											</div>
										  </div>
					  
					  
										</div>	
											
											<div class="row">
												
												<div class="col-md-3">
												<div class="form-group">
													<label>Total Connections Master</label> <input type="text" id="connectionsMaster"
														class="form-control" placeholder="Total Connections Master...">
												</div>
												</div>
											 
								
												
												<div class="col-md-3">
													<div class="form-group">
														<label>Total Connections Ledger</label> <input type="text" id="connectionsLedger"
															class="form-control" placeholder="Total Connections Ledger...">
													</div>
												</div>
								
												<div class="col-md-3">
													<div class="form-group">
														<label>Billed</label> <input type="text" class="form-control" id="billed"
															placeholder="Billed">
													</div>
												</div>
								
												<div class="col-md-3">
													<div class="form-group">
														<label>UnBilled</label> <input type="text" class="form-control" id="unBilled"
															placeholder="Unbilled">
													</div>
												</div>
											</div>
										</fieldset>
								
								
								<div class="text-center">
									<button type="button" class="btn bg-teal btn-ladda" data-style="expand-right" data-spinner-size="20" onclick=" return printBill();"><span class="ladda-label">Print Bill</span></button>
								</div>
							
	</div>			</div>	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
					<div class="panel panel-flat">
						

						<div class="panel-body">
							<!-- <form class="form-horizontal" action="#">-->
							
							<fieldset class="content-group"> 
								<legend class="text-bold">Bill Print Unmetered</legend>
										
										
									<div class="row">
											
											<div class="col-md-3">
												<label>Ward No</label><select data-placeholder="Select" class="select"  id="wardNumberu" name="wardNou" onchange="getLedgerDatau()" required="required">
												    <option value="" data-icon="icon-git-branch">Select</option>
													
													<c:forEach items="${wardNoListu}" var="ward">
													<option value="${ward.wardNou}">${ward.wardNou}</option>
												   </c:forEach>
													
												</select>
											</div>
											
											
											 
										    
										    <div class="col-md-2" >
												<label>Year</label>
												
												<select data-placeholder="Select" class="select" id="yearnep1" name="yearnep" required="required" onchange="getLedgerDatau()" >
													<c:forEach items="${yearList}" var="ward">
													<option value="${ward.year}">${ward.year}</option>
												   </c:forEach>
												</select>
													 
											   </div>
							   
												   <div class="col-md-2" >
													<label>Month</label>
													<select data-placeholder="Select" class="select"  name="monthnep" required="required" id="monthnep1" onchange="getLedgerDatau()" > 
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
												   
							   
							   
										  
										</div>	
											
											<div class="row">
												
												<div class="col-md-3">
												<div class="form-group">
													<label>Total Connections Master</label> <input type="text" id="connectionsMasteru"
														class="form-control" placeholder="Total Connections Master...">
												</div>
												</div>
											 
								
												
												<div class="col-md-3">
													<div class="form-group">
														<label>Total Connections Ledger</label> <input type="text" id="connectionsLedgeru"
															class="form-control" placeholder="Total Connections Ledger...">
													</div>
												</div>
								
												<div class="col-md-3">
													<div class="form-group">
														<label>Billed</label> <input type="text" class="form-control" id="billedu"
															placeholder="Billed">
													</div>
												</div>
								
												<div class="col-md-3">
													<div class="form-group">
														<label>UnBilled</label> <input type="text" class="form-control" id="unBilledu"
															placeholder="Unbilled">
													</div>
												</div>
											</div>
										</fieldset>
								
								
								<div class="text-center">
									<button type="button" class="btn bg-teal btn-ladda" data-style="expand-right" data-spinner-size="20" onclick=" return printBillUnmetered();"><span class="ladda-label">Print Bill</span></button>
								</div>
							
	</div>			</div>
	
	
	
	
	<div class="panel panel-flat">
						

						<div class="panel-body">
							<!-- <form class="form-horizontal" action="#">-->
							
							<fieldset class="content-group"> 
								<legend class="text-bold">Bill Print Government</legend>
										
										
									<div class="row">
										    <div class="col-md-2" >
												<label>Year</label>
												
												<select data-placeholder="Select" class="select" id="yearnep2" name="yearnep2" required="required">
													<c:forEach items="${yearList}" var="ward">
													<option value="${ward.year}">${ward.year}</option>
												   </c:forEach>
												</select>
													 
											 </div>
							   
											   <div class="col-md-2" >
												<label>Month</label>
												<select data-placeholder="Select" class="select"  name="monthnep2" id="monthnep2"> 
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
												<label>Connection Category <font color="red">*</font></label>
												<select data-placeholder="Select" class="select" onchange="getLedgerDataGovt()"
													name="con_category2" id="con_category2">
													<option value="" data-icon="icon-git-branch">Select</option>
													<option value="Government" data-icon="icon-git-commit">Government</option>
													<option value="Industry and Company" data-icon="icon-git-commit">Industry and Company</option>
												</select>
											</div>
					  
										</div>	
											
											<div class="row">
												
												<div class="col-md-3">
												<div class="form-group">
													<label>Total Connections Master</label> <input type="text" id="connectionsMasterG"
														class="form-control" placeholder="Total Connections Master...">
												</div>
												</div>
											 
								
												
												<div class="col-md-3">
													<div class="form-group">
														<label>Total Connections Ledger</label> <input type="text" id="connectionsLedgerG"
															class="form-control" placeholder="Total Connections Ledger...">
													</div>
												</div>
								
												<div class="col-md-3">
													<div class="form-group">
														<label>Billed</label> <input type="text" class="form-control" id="billedG"
															placeholder="Billed">
													</div>
												</div>
								
												<div class="col-md-3">
													<div class="form-group">
														<label>UnBilled</label> <input type="text" class="form-control" id="unBilledG"
															placeholder="Unbilled">
													</div>
												</div>
											</div>
										</fieldset>
								
								
								<div class="text-center">
									<button type="button" class="btn bg-teal btn-ladda" data-style="expand-right" data-spinner-size="20" onclick=" return printBillGovt();"><span class="ladda-label">Print Bill</span></button>
								</div>
							
	</div>			</div>	
	
	
	


<script>

$(document).ready(function(){   
	$('#billPrint').show();
	$('#billPrintScreen').addClass('active');
	
	var activeMod="${activeModulesNew}";
	var module="Bill Print";
	var check=activeMod.indexOf(module) > -1;
	
	if(check){
	}else{
		window.location.href="./accessDenied";
	}
});
	


function getLedgerDatau(){
	
	var wardNo=$('#wardNumberu').val();
	var yearnep=$('#yearnep1').val();
	var monthnep=$('#monthnep1').val();
		
	 
	 
	if(wardNo==null || wardNo==""){
	}else if(yearnep==null || yearnep==""){
	}else if(monthnep==null || monthnep==""){
	}else{
	var reading_month=yearnep+""+monthnep;

	$.ajax({
		  type: "GET",
		  url: "./billing/readingEntryu/"+wardNo+"/"+reading_month,
		  dataType: "JSON",
        
		  async       : false,
		  success: function(response){
		
			       $.each(response, function(index, data){
						
			    	    $('#connectionsMasteru').val(data.masterCount);
			    		$('#connectionsLedgeru').val(data.ledgerCount);
			    		$('#billedu').val(data.billedLedgerCount);
			    		$('#unBilledu').val(data.unbilledLedgerCount);
				 
				 });
			  
			  
			  
		  }
		});
    }
}



function getLedgerData(){
	
	var wardNo=$('#wardNumber').val();
	var reading_day=$('#reading_day').val();
	var pipe_size=$('#pipe_size').val();
	var yearnep=$('#yearnep').val();
	var monthnep=$('#monthnep').val();
	var concategory=$('#con_category1').val();
	
	if(wardNo==null || wardNo==""){
	}else if(reading_day==null || reading_day==""){
	}else if(pipe_size==null || pipe_size==""){
	}else if(yearnep==null || yearnep==""){
	}else if(monthnep==null || monthnep==""){
	}else{
	 var pipesize=pipe_size;
	 var reading_month=yearnep+""+monthnep;
	 
	$.ajax({
		  type: "GET",
		  url: "./billing/readingEntry/"+wardNo+"/"+reading_day+"/"+pipe_size+"/"+reading_month+"/"+concategory,
		  dataType: "JSON",
          data:{
			  
			  pipesize:pipesize,
				  
		  },
		  async       : false,
		  success: function(response){
		
			       $.each(response, function(index, data){
						
			    	    $('#connectionsMaster').val(data.masterCount);
			    		$('#connectionsLedger').val(data.ledgerCount);
			    		$('#billed').val(data.billedLedgerCount);
			    		$('#unBilled').val(data.unbilledLedgerCount);
				 
				 });
			  
			  
			  
		  }
		});
    }
}



function getLedgerDataGovt(){
	
	var yearnep=$('#yearnep2').val();
	var monthnep=$('#monthnep2').val();
	var con_category1=$('#con_category2').val();	
	 
	 if(yearnep==null || yearnep==""){
	}else if(monthnep==null || monthnep==""){
	}else{
	var reading_monthg=yearnep+""+monthnep;
	//alert(con_category1+"========="+reading_monthg);

	$.ajax({
		  type: "GET",
		  url: "./billing/readingEntryGovt/"+con_category1+"/"+reading_monthg,
		  dataType: "JSON",
        
		  async       : false,
		  success: function(response){
		
			       $.each(response, function(index, data){
						
			    	    $('#connectionsMasterG').val(data.masterCount);
			    		$('#connectionsLedgerG').val(data.ledgerCount);
			    		$('#billedG').val(data.billedLedgerCount);
			    		$('#unBilledG').val(data.unbilledLedgerCount);
				 
				 });
			  
			  
			  
		  }
		});
    }
}


function printBill(){
	
	var wardNo=$('#wardNumber').val();
	var reading_day=$('#reading_day').val();
	var pipesize=$('#pipe_size').val();
	var yearnep=$('#yearnep').val();
	var monthnep=$('#monthnep').val();
	var concategory=$('#con_category1').val();
	
	if(wardNo=="" || wardNo==null){
		alert("Please select ward No");
		return false;
		
	}else if(reading_day=="" || reading_day==null){
		alert("Please select Reading day");
		return false;
		
	}else if(yearnep=="" || yearnep==null){
		alert("Please select Year");
		return false;
		
	}else if(monthnep=="" || monthnep==null){
		alert("Please select Month");
		return false;
		
	}else{	
	
		var reading_month=yearnep+""+monthnep;
		
		$.ajax({
		  type: "GET",
		  url: "./billing/printBill/"+wardNo+"/"+reading_day+"/"+reading_month+"/"+concategory,
		  dataType: "JSON",
 		  data:{
			  
			  pipesize:pipesize,
				  
		  },
		  async   : false,
		  cache:false,
		  success: function(response){
			  var result=response;
			  var branch="${BranchName}";
			  var printW = window.open("");
			  
			  
			  for(var i=0;i<result.length;i++)
			  {
				  
				 var data=result[i];
				 var html = "<div style='margin-bottom: -70px;'>";
				   

				    
				     var wcc=data.water_charges==null?parseFloat(0):data.water_charges;
					 var scc=data.sw_charges==null?parseFloat(0):data.sw_charges;
					 var mrc=data.mtr_rent==null?parseFloat(0):data.mtr_rent;
					 var cmt=parseFloat(parseFloat(wcc)+parseFloat(scc)+parseFloat(mrc)).toFixed(2);
					 
					 var board=data.boardbalance==null?parseFloat(0):data.boardbalance;
					 
				     var tolArrs= parseFloat(parseFloat(data.arrears)+parseFloat(board)).toFixed(2);
					 var netAmt= parseFloat(parseFloat(tolArrs)+parseFloat(cmt)).toFixed(2);
					 
				 html += "  <table style='undefined;table-layout: fixed; width: 325px;font-size:11pt;line-height:16px;'>"
				   	   
					   +" <colgroup>"
					   +" <col style='width: 145px'>"
					   +" <col style='width: 185px'>"
					   +"</colgroup>"
					   
					   +"  <tr>"
					   +"    <th><br>Kathmandu Upatyaka Khanepani Limited<br><br>Branch:"+branch+" <br>PAN NO. :600041601</th>"
					   +"    <th><img src='./resources/images/kukl_new.png' width='130px' height='130px' alt='' align='right'></th>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   
					   +"    <th colspan='2'><hr style='height: 2px; background-color: black;'></th>"
					   +"  </tr>"
					   +"  <tr>"
					   
					   +"  <tr>"
					   +"  <th colspan='2' align='center'><u>METER READING BILL: "+data.monthDesc+"</u></th>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   +"    <td>Bill No.<br></td>"
					   +"    <td>:&nbsp;&nbsp;<b>"+data.billno+"</b></td>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   +"    <td>Customer No.<br></td>"
					   +"    <td>:&nbsp;&nbsp;<b>"+data.connection_no+"</b></td>"
					   +"  </tr>"
					   
					   +"    <td>Connection No.<br></td>"
					   +"    <td>:&nbsp;&nbsp;<b>"+data.connection_no+"</b></td>"
					   +"  </tr>"
					   
					   +"    <td>Area No.<br></td>"
					   +"    <td>:&nbsp;&nbsp;<b>"+data.area_no+"</b></td>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   +"    <td><b>Name</b><br></td>"
					   +"    <td>:&nbsp;&nbsp;"+data.name_eng+"</td>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   +"    <td>Address<br></td>"
					   +"    <td>:&nbsp;&nbsp;"+data.address_eng+"</td>"
					   +"  </tr>"
					   
					   
					   +"  <tr>"
					   +"    <td>Connection Type<br></td>"
					   +"    <td>:&nbsp;&nbsp;"+data.con_type+"</td>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   +"    <td>Tap Size<br></td>"
					   +"    <td>:&nbsp;&nbsp;"+data.pipe_size+"</td>"
					   +"  </tr>"
					   
					   
					  /*  +"  <tr>"
					   +"    <th colspan='2'><hr style='height: 3px; background-color: black;'></th>"
					   +"  </tr>" */
					   
					   +"  <tr>"
					   +"    <td>Reading Date<br></td>"
					   +"    <td>:&nbsp;&nbsp;<b>"+data.rdng_date_nep+"</b></td>"
					   +"  </tr>"
					   
					  /*  +"  <tr>"
					   +"    <td>DUE DATE :<br></td>"
					   +"    <td><b>"+data.due_date_nep+"</b></td>"
					   +"  </tr>" */
					   
					  
					   +"  <tr>"
					   +"    <td>Present Reading(KL)<br></td>"
					   +"    <td>:&nbsp;&nbsp;"+data.present_reading+"</td>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   +"    <td>Previous Reading(KL)<br></td>"
					   +"    <td>:&nbsp;&nbsp;"+data.previous_reading+"</td>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   +"    <td>Consumption(KL)<br></td>"
					   +"    <td>:&nbsp;&nbsp;"+data.consumption+"</td>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   +"    <td>Additinal Units(KL)<br></td>"
					   +"    <td>:&nbsp;&nbsp;"+data.additionalUnits+"</td>"
					   +"  </tr>"
					   
					  /*  +"  <tr>"
					   +"    <th colspan='2'><hr style='height: 3px; background-color: black;'></th>"
					   +"  </tr>" */
					   
					   
					   +"  <tr>"
					   +"    <td>Minimum Charges<br></td>"
					   +"    <td>:&nbsp;&nbsp;<b>"+data.minimum_charges+"</b></td>"
					   +"  </tr>"
					   +"   <tr>"
					   +"    <td>Additional Charges<br></td>"
					   +"    <td>:&nbsp;&nbsp;<b>"+data.additional_charges+"</b></td>"
					   +"  </tr>"
					   +"  <tr>"
					   +"    <td>Total Water Charges<br></td>"
					   +"    <td>:&nbsp;&nbsp;<b>"+data.water_charges+"</b></td>"
					   +"  </tr>"
					   +"  <tr>"
					   +"    <td>Sewerage Charges<br></td>"
					   +"    <td>:&nbsp;&nbsp;<b>"+data.sw_charges+"</b></td>"
					   +"  </tr>"
					   +"  <tr>"
					   +"    <td>Meter Rent Charges<br></td>"
					   +"    <td>:&nbsp;&nbsp;<b>"+data.mtr_rent+"</b></td>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   +"    <td>Miscellaneous<br></td>"
					   +"    <td>:&nbsp;&nbsp;0</td>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   +"    <td>Current Month Total<br></td>"
					   +"    <td>:&nbsp;&nbsp;<b>"+cmt+"</b></td>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   +"    <td>Arrears<br></td>"
					   +"    <td>:&nbsp;&nbsp;<b>"+tolArrs+"</b></td>"
					   +"  </tr>"
					   
					   /* +"  <tr>"
					   +"    <th colspan='2'><hr style='height: 5px; background-color: black;'></th>"
					   +"  </tr>" */
					   
					   +"  <tr>"
					   +"    <td><b>Total Amount Rs.</b><br></td>"
					   +"    <td>:&nbsp;&nbsp;<b>"+netAmt+"</b></td>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   +"    <td>Observation<br></td>"
					   +"    <td>:&nbsp;&nbsp;"+data.observation+"</td>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   +"    <td>Meter Reader'S Name<br></td>"
					   +"    <td>:&nbsp;&nbsp;"+data.mrname+"</td>"
					   +"  </tr>"
					   
					   /*  +"  <tr>"
					   +"    <th colspan='2'><hr style='height: 5px; background-color: black;'></th>"
					   +"  </tr>" */
					   
					   +"  <tr>"
					   
					   +"    <td colspan='2'><center>*****Computer Generated Bill*****</center></td>"
					   +"  </tr>"
					   +"  <tr>"
					   +"    <td>&nbsp;</td>"
					   +"    <td>&nbsp;</td>"
					   +"  </tr>"
					   +"  <tr>"
					   +"    <td>&nbsp;</td>"
					   +"    <td><center>.</center></td>"
					   +"  </tr>"
					   +"</table><br><br><br>";
					    html += "</div>";

					    printW.document.write(html);

				   
				    
			  }	
			  
			    printW.document.close();
			    printW.focus();
			    printW.print();
			    printW.close(); 
		  }
		
		   
		    
		});
	}
}







function printBillUnmetered(){
	
	var wardNo=$('#wardNumberu').val();
	var yearnep=$('#yearnep1').val();
	var monthnep=$('#monthnep1').val();
	
	if(wardNo=="" || wardNo==null){
		alert("Please select ward No");
		return false;
		
	}else if(yearnep=="" || yearnep==null){
		alert("Please select Year");
		return false;
		
	}else if(monthnep=="" || monthnep==null){
		alert("Please select Month");
		return false;
		
	 }else{	
	
		var reading_monthu=yearnep+""+monthnep;
		
		$.ajax({
		  type: "GET",
		  url: "./billing/printBillu/"+wardNo+"/"+reading_monthu,
		  dataType: "JSON",
 		  
		  async   : false,
		  cache:false,
		  success: function(response){
			  var result=response;
			  var branch="${BranchName}";
			  var printW = window.open("");
			  
			  
			  for(var i=0;i<result.length;i++)
			  {
				  
				 var data=result[i];
				 var wcc=data.water_charges==null?parseFloat(0):data.water_charges;
				 var scc=data.sw_charges==null?parseFloat(0):data.sw_charges;
				 var mrc=data.mtr_rent==null?parseFloat(0):data.mtr_rent;
				 var cmt=parseFloat(parseFloat(wcc)+parseFloat(scc)+parseFloat(mrc)).toFixed(2);
				 
				 var board=data.boardbalance==null?parseFloat(0):data.boardbalance;
				 
			     var tolArrs= parseFloat(parseFloat(data.arrears)+parseFloat(board)).toFixed(2);
				 var netAmt= parseFloat(parseFloat(tolArrs)+parseFloat(cmt)).toFixed(2);
				 
				
				 var html = "<div style='margin-bottom: -70px;'>";
				 html += "  <table style='undefined;table-layout: fixed; width: 325px;font-size:11pt;line-height:16px;'>"
				   	   
					   +" <colgroup>"
					   +" <col style='width: 145px'>"
					   +" <col style='width: 185px'>"
					   +"</colgroup>"
					   
					   +"  <tr>"
					   +"    <th><br>Kathmandu Upatyaka Khanepani Limited<br><br>Branch:"+branch+" <br>PAN NO. :600041601</th>"
					   +"    <th><img src='./resources/images/kukl_new.png' width='130px' height='130px' alt='' align='right'></th>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   
					   +"    <th colspan='2'><hr style='height: 2px; background-color: black;'></th>"
					   +"  </tr>"
					   +"  <tr>"
					   
					   +"  <tr>"
					   +"  <th colspan='2' align='center'><u>METER READING BILL: "+data.monthDesc+"</u></th>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   +"    <td>Bill No.<br></td>"
					   +"    <td>:&nbsp;&nbsp;<b>"+data.billno+"</b></td>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   +"    <td>Customer No.<br></td>"
					   +"    <td>:&nbsp;&nbsp;<b>"+data.connection_no+"</b></td>"
					   +"  </tr>"
					   
					   +"    <td>Connection No.<br></td>"
					   +"    <td>:&nbsp;&nbsp;<b>"+data.connection_no+"</b></td>"
					   +"  </tr>"
					   
					   +"    <td>Area No.<br></td>"
					   +"    <td>:&nbsp;&nbsp;<b>"+data.area_no+"</b></td>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   +"    <td><b>Name</b><br></td>"
					   +"    <td>:&nbsp;&nbsp;"+data.name_eng+"</td>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   +"    <td>Address<br></td>"
					   +"    <td>:&nbsp;&nbsp;"+data.address_eng+"</td>"
					   +"  </tr>"
					   
					   
					   +"  <tr>"
					   +"    <td>Connection Type<br></td>"
					   +"    <td>:&nbsp;&nbsp;"+data.con_type+"</td>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   +"    <td>Tap Size<br></td>"
					   +"    <td>:&nbsp;&nbsp;"+data.pipe_size+"</td>"
					   +"  </tr>"
					   
					   
					  /*  +"  <tr>"
					   +"    <th colspan='2'><hr style='height: 3px; background-color: black;'></th>"
					   +"  </tr>" */
					   
					   +"  <tr>"
					   +"    <td>Reading Date<br></td>"
					   +"    <td>:&nbsp;&nbsp;<b>"+data.rdng_date_nep+"</b></td>"
					   +"  </tr>"
					   
					  /*  +"  <tr>"
					   +"    <td>DUE DATE :<br></td>"
					   +"    <td><b>"+data.due_date_nep+"</b></td>"
					   +"  </tr>" */
					   
					  
					   +"  <tr>"
					   +"    <td>Present Reading(KL)<br></td>"
					   +"    <td>:&nbsp;&nbsp;"+data.present_reading+"</td>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   +"    <td>Previous Reading(KL)<br></td>"
					   +"    <td>:&nbsp;&nbsp;"+data.previous_reading+"</td>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   +"    <td>Consumption(KL)<br></td>"
					   +"    <td>:&nbsp;&nbsp;"+data.consumption+"</td>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   +"    <td>Additinal Units(KL)<br></td>"
					   +"    <td>:&nbsp;&nbsp;"+(data.additionalUnits==null?"":data.additionalUnits)+"</td>"
					   +"  </tr>"
					   
					  /*  +"  <tr>"
					   +"    <th colspan='2'><hr style='height: 3px; background-color: black;'></th>"
					   +"  </tr>" */
					   
					   
					   +"  <tr>"
					   +"    <td>Minimum Charges<br></td>"
					   +"    <td>:&nbsp;&nbsp;<b>"+data.minimum_charges+"</b></td>"
					   +"  </tr>"
					   +"   <tr>"
					   +"    <td>Additional Charges<br></td>"
					   +"    <td>:&nbsp;&nbsp;<b>"+data.additional_charges+"</b></td>"
					   +"  </tr>"
					   +"  <tr>"
					   +"    <td>Total Water Charges<br></td>"
					   +"    <td>:&nbsp;&nbsp;<b>"+data.water_charges+"</b></td>"
					   +"  </tr>"
					   +"  <tr>"
					   +"    <td>Sewerage Charges<br></td>"
					   +"    <td>:&nbsp;&nbsp;<b>"+data.sw_charges+"</b></td>"
					   +"  </tr>"
					   +"  <tr>"
					   +"    <td>Meter Rent Charges<br></td>"
					   +"    <td>:&nbsp;&nbsp;<b>"+(data.mtr_rent==null?'0':data.mtr_rent)+"</b></td>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   +"    <td>Miscellaneous<br></td>"
					   +"    <td>:&nbsp;&nbsp;0</td>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   +"    <td>Current Month Total<br></td>"
					   +"    <td>:&nbsp;&nbsp;<b>"+cmt+"</b></td>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   +"    <td>Arrears<br></td>"
					   +"    <td>:&nbsp;&nbsp;<b>"+tolArrs+"</b></td>"
					   +"  </tr>"
					   
					   /* +"  <tr>"
					   +"    <th colspan='2'><hr style='height: 5px; background-color: black;'></th>"
					   +"  </tr>" */
					   
					   +"  <tr>"
					   +"    <td><b>Total Amount Rs.</b><br></td>"
					   +"    <td>:&nbsp;&nbsp;<b>"+netAmt+"</b></td>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   +"    <td>Observation<br></td>"
					   +"    <td>:&nbsp;&nbsp;"+(data.observation==null?"":data.observation)+"</td>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   +"    <td>Meter Reader'S Name<br></td>"
					   +"    <td>:&nbsp;&nbsp;"+data.mrname+"</td>"
					   +"  </tr>"
					   
					   /*  +"  <tr>"
					   +"    <th colspan='2'><hr style='height: 5px; background-color: black;'></th>"
					   +"  </tr>" */
					   
					   +"  <tr>"
					   
					   +"    <td colspan='2'><center>*****Computer Generated Bill*****</center></td>"
					   +"  </tr>"
					   +"  <tr>"
					   +"    <td>&nbsp;</td>"
					   +"    <td>&nbsp;</td>"
					   +"  </tr>"
					   +"  <tr>"
					   +"    <td>&nbsp;</td>"
					   +"    <td><center>.</center></td>"
					   +"  </tr>"
					   +"</table><br><br><br>";
					    html += "</div>";

					    printW.document.write(html);

				   
				    
			  }	
			  
			    printW.document.close();
			    printW.focus();
			    printW.print();
			    printW.close(); 
		  }
		
		   
		    
		});
	}
}



function printBillGovt(){
	
	
	var yearnep=$('#yearnep2').val();
	var monthnep=$('#monthnep2').val();
	var con_category1=$('#con_category2').val();
	
	if(yearnep=="" || yearnep==null){
		alert("Please select Year");
		return false;
		
	}else if(monthnep=="" || monthnep==null){
		alert("Please select Month");
		return false;
		
	 }else{	
	
		var reading_monthg=yearnep+""+monthnep;
		
		$.ajax({
		  type: "GET",
		  url: "./billing/printBillGovt/"+con_category1+"/"+reading_monthg,
		  dataType: "JSON",
 		  
		  async   : false,
		  cache:false,
		  success: function(response){
			  var result=response;
			  var branch="${BranchName}";
			  var printW = window.open("");
			  
			  
			  for(var i=0;i<result.length;i++)
			  {
				  
				 var data=result[i];
				 var wcc=data.water_charges==null?parseFloat(0):data.water_charges;
				 var scc=data.sw_charges==null?parseFloat(0):data.sw_charges;
				 var mrc=data.mtr_rent==null?parseFloat(0):data.mtr_rent;
				 var cmt=parseFloat(parseFloat(wcc)+parseFloat(scc)+parseFloat(mrc)).toFixed(2);
				 
				 var board=data.boardbalance==null?parseFloat(0):data.boardbalance;
				 
			     var tolArrs= parseFloat(parseFloat(data.arrears)+parseFloat(board)).toFixed(2);
				 var netAmt= parseFloat(parseFloat(tolArrs)+parseFloat(cmt)).toFixed(2);
				 
				
				 var html = "<div style='margin-bottom: -70px;'>";
				 html += "  <table style='undefined;table-layout: fixed; width: 325px;font-size:11pt;line-height:16px;'>"
				   	   
					   +" <colgroup>"
					   +" <col style='width: 145px'>"
					   +" <col style='width: 185px'>"
					   +"</colgroup>"
					   
					   +"  <tr>"
					   +"    <th><br>Kathmandu Upatyaka Khanepani Limited<br><br>Branch:"+branch+" <br>PAN NO. :600041601</th>"
					   +"    <th><img src='./resources/images/kukl_new.png' width='130px' height='130px' alt='' align='right'></th>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   
					   +"    <th colspan='2'><hr style='height: 2px; background-color: black;'></th>"
					   +"  </tr>"
					   +"  <tr>"
					   
					   +"  <tr>"
					   +"  <th colspan='2' align='center'><u>METER READING BILL: "+data.monthDesc+"</u></th>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   +"    <td>Bill No.<br></td>"
					   +"    <td>:&nbsp;&nbsp;<b>"+data.billno+"</b></td>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   +"    <td>Customer No.<br></td>"
					   +"    <td>:&nbsp;&nbsp;<b>"+data.connection_no+"</b></td>"
					   +"  </tr>"
					   
					   +"    <td>Connection No.<br></td>"
					   +"    <td>:&nbsp;&nbsp;<b>"+data.connection_no+"</b></td>"
					   +"  </tr>"
					   
					   +"    <td>Area No.<br></td>"
					   +"    <td>:&nbsp;&nbsp;<b>"+data.area_no+"</b></td>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   +"    <td><b>Name</b><br></td>"
					   +"    <td>:&nbsp;&nbsp;"+data.name_eng+"</td>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   +"    <td>Address<br></td>"
					   +"    <td>:&nbsp;&nbsp;"+data.address_eng+"</td>"
					   +"  </tr>"
					   
					   
					   +"  <tr>"
					   +"    <td>Connection Type<br></td>"
					   +"    <td>:&nbsp;&nbsp;"+data.con_type+"</td>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   +"    <td>Tap Size<br></td>"
					   +"    <td>:&nbsp;&nbsp;"+data.pipe_size+"</td>"
					   +"  </tr>"
					   
					   
					  /*  +"  <tr>"
					   +"    <th colspan='2'><hr style='height: 3px; background-color: black;'></th>"
					   +"  </tr>" */
					   
					   +"  <tr>"
					   +"    <td>Reading Date<br></td>"
					   +"    <td>:&nbsp;&nbsp;<b>"+data.rdng_date_nep+"</b></td>"
					   +"  </tr>"
					   
					  /*  +"  <tr>"
					   +"    <td>DUE DATE :<br></td>"
					   +"    <td><b>"+data.due_date_nep+"</b></td>"
					   +"  </tr>" */
					   
					  
					   +"  <tr>"
					   +"    <td>Present Reading(KL)<br></td>"
					   +"    <td>:&nbsp;&nbsp;"+data.present_reading+"</td>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   +"    <td>Previous Reading(KL)<br></td>"
					   +"    <td>:&nbsp;&nbsp;"+data.previous_reading+"</td>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   +"    <td>Consumption(KL)<br></td>"
					   +"    <td>:&nbsp;&nbsp;"+data.consumption+"</td>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   +"    <td>Additinal Units(KL)<br></td>"
					   +"    <td>:&nbsp;&nbsp;"+(data.additionalUnits==null?"":data.additionalUnits)+"</td>"
					   +"  </tr>"
					   
					  /*  +"  <tr>"
					   +"    <th colspan='2'><hr style='height: 3px; background-color: black;'></th>"
					   +"  </tr>" */
					   
					   
					   +"  <tr>"
					   +"    <td>Minimum Charges<br></td>"
					   +"    <td>:&nbsp;&nbsp;<b>"+data.minimum_charges+"</b></td>"
					   +"  </tr>"
					   +"   <tr>"
					   +"    <td>Additional Charges<br></td>"
					   +"    <td>:&nbsp;&nbsp;<b>"+data.additional_charges+"</b></td>"
					   +"  </tr>"
					   +"  <tr>"
					   +"    <td>Total Water Charges<br></td>"
					   +"    <td>:&nbsp;&nbsp;<b>"+data.water_charges+"</b></td>"
					   +"  </tr>"
					   +"  <tr>"
					   +"    <td>Sewerage Charges<br></td>"
					   +"    <td>:&nbsp;&nbsp;<b>"+data.sw_charges+"</b></td>"
					   +"  </tr>"
					   +"  <tr>"
					   +"    <td>Meter Rent Charges<br></td>"
					   +"    <td>:&nbsp;&nbsp;<b>"+(data.mtr_rent==null?'0':data.mtr_rent)+"</b></td>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   +"    <td>Miscellaneous<br></td>"
					   +"    <td>:&nbsp;&nbsp;0</td>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   +"    <td>Current Month Total<br></td>"
					   +"    <td>:&nbsp;&nbsp;<b>"+cmt+"</b></td>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   +"    <td>Arrears<br></td>"
					   +"    <td>:&nbsp;&nbsp;<b>"+tolArrs+"</b></td>"
					   +"  </tr>"
					   
					   /* +"  <tr>"
					   +"    <th colspan='2'><hr style='height: 5px; background-color: black;'></th>"
					   +"  </tr>" */
					   
					   +"  <tr>"
					   +"    <td><b>Total Amount Rs.</b><br></td>"
					   +"    <td>:&nbsp;&nbsp;<b>"+netAmt+"</b></td>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   +"    <td>Observation<br></td>"
					   +"    <td>:&nbsp;&nbsp;"+(data.observation==null?"":data.observation)+"</td>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   +"    <td>Meter Reader'S Name<br></td>"
					   +"    <td>:&nbsp;&nbsp;"+data.mrname+"</td>"
					   +"  </tr>"
					   
					   /*  +"  <tr>"
					   +"    <th colspan='2'><hr style='height: 5px; background-color: black;'></th>"
					   +"  </tr>" */
					   
					   +"  <tr>"
					   
					   +"    <td colspan='2'><center>*****Computer Generated Bill*****</center></td>"
					   +"  </tr>"
					   +"  <tr>"
					   +"    <td>&nbsp;</td>"
					   +"    <td>&nbsp;</td>"
					   +"  </tr>"
					   +"  <tr>"
					   +"    <td>&nbsp;</td>"
					   +"    <td><center>.</center></td>"
					   +"  </tr>"
					   +"</table><br><br><br>";
					    html += "</div>";

					    printW.document.write(html);

				   
				    
			  }	
			  
			    printW.document.close();
			    printW.focus();
			    printW.print();
			    printW.close(); 
		  }
		
		   
		    
		});
	}
}





</script>

<style>

select{
width: 100%; border: 1px solid #DDD; border-radius: 3px; height: 36px;
}
legend {
	text-transform: none;
	font-size: 16px;
	border-color: #F01818;
}
</style>