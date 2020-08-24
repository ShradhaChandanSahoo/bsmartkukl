<%@include file="/WEB-INF/decorators/taglibs.jsp"%>
<script type="text/javascript">
var counterNo="";
var fromDatenep="";
var toDatenep="";
$(document).ready(function(){   
	
	var activeMod="${activeModulesNew}";
	var module="Collection Reports";
	var module1="Collection Reports CI/CSR";
	var check=activeMod.indexOf(module) > -1;
	var check1=activeMod.indexOf(module1) > -1;
	
	if(check){
	}else if(check1){
	}else{
		window.location.href="./accessDenied";
	}
	
 	 $('#fromDatenep').nepaliDatePicker({
		 dateFormat: "%D, %M %d, %y",
			npdMonth: true,
			npdYear: true,
			
	});   
 	 
 	   $('#toDatenep').nepaliDatePicker({
		 dateFormat: "%D, %M %d, %y",
			npdMonth: true,
			npdYear: true,
			
	}); 
	 
	 	 
});


	</script>

<div class="panel panel-flat">
						<div class="panel-body">
							<fieldset class="content-group"> 
								<legend class="text-bold">Cash Collection Report</legend>
									<div class="row">
									
									 <div class="form-group col-md-3" >
										<label>From Date (Nepali)</label>
					                          <div class="input-group"> 
				         						<span class="input-group-addon"><i class="icon-calendar3"></i></span>
									     		<input name="fromDatenep"  placeholder="Select From Date..." id="fromDatenep"  class="form-control nepali-calendar" />
											</div>
				                         </div> 
				                         
				                          <div class="form-group col-md-3" >
				                         <label>To Date (Nepali)</label>
					                          <div class="input-group"> 
				         						<span class="input-group-addon"><i class="icon-calendar3"></i></span>
									     		<input name="toDatenep"  placeholder="Select To Date..." id="toDatenep"  class="form-control nepali-calendar" />
											</div>
				                         </div> 
											
										 <div class="form-group col-md-3" >
										<label>Counter No</label>
					                         <select class="select" id="counter_no" name="counter_no"
														required="required" data-placeholder="Select Counter No">
														<option value="0">All</option>
														<c:forEach var="data" items="${counter}">
																
																<option value="${data.counterNumber}">${data.counterNumber}</option>
														</c:forEach> 
													</select>
				                         </div> 
				                        
				                       
				                        
											<div class="form-group col-md-3">
												<label>Ward No. <font color="red">*</font></label>
												<select class="select" id="ward_no" name="ward_no" data-placeholder="Select Ward No...">
													<option value="0">All</option>
												<c:forEach var="data" items="${wardList}">
														
														<option value="${data.wardNo}">${data.wardNo}</option>
													</c:forEach> 
												</select>
											</div>
									</div>
											<div class="row">
											 <div class="form-group col-md-3">
												<label>Pipe Size(inch) <font color="red">*</font></label>
												<select class="select" name="pipe_size" id="pipe_size">
													<option value="0">All</option>
													<option value="0.5" >0.5</option>
													<option value="0.75" >0.75</option>
													<option value="1" >1</option>
													<option value="1.5" >1.5</option>
													<option value="2" >2</option>
													<option value="3" >3</option>
													<option value="4" >4</option>
												</select>
											</div>
										
										
											
										
											<div class="col-md-3" id="addId" >
												<div class="form-group text-center" >
													<label>&nbsp;</label>
					                                <button type="button" onclick="return generateDayClosureReport();" class="btn bg-teal btn-ladda"  style="width: 200px; margin-top: 28px;" data-style="expand-right" data-spinner-size="20">
													<span class="ladda-label">Print Report</span></button>
				                                </div>
											</div>
										</div>
										
								</fieldset>	
								
						</div>	
						
						
								</div>
								

	<script>
	
	var twc=0;
	var tsw=0;
	var tmr=0;
	var tbill=0;
	var tmisc=0;
	var tpenalty=0;
	var trebate=0;
	var toldbal=0;
	var ttotrec=0;
	var ttotcol=0;
	var tap=0;
	var tar=0;
	var tctp=0;
	var kukl=0;
	var board=0;
	var board_penalty=0;
	var monthYear=0;
	var waterP="",drainageP="",otherP="",meterC="",meterChangeFine="",excuses="",totalP="",advanceP="",cashCollectionAmt="",amtInWords="",advancerebate="",oldbalance="",totalreceivable="",kuklamt="",boardamt="", boardP="";
	function generateDayClosureReport()
	{
		 var fromDatenep = $("#fromDatenep").val();
		 var toDatenep= $("#toDatenep").val();

		 var counter_no = $("#counter_no").val();
		 var ward_no= $("#ward_no").val();

		 var pipe_size = $("#pipe_size").val();
		
		if(fromDatenep == "")
		{
			 swal({
	                title: "Please Select From Date",
	                text: "",
	                confirmButtonColor: "#2196F3",
	            });	
			 return false;
		}
		if(toDatenep == "")
		{
			 swal({
	                title: "Please Select To Date",
	                text: "",
	                confirmButtonColor: "#2196F3",
	            });	
			 return false;
		}
		$.ajax({
			  type: "GET",
			  url: "./generatedailyCashCounterCollReport",
			  dataType: "Json",
			  data:{
				  
				  fromDatenep : fromDatenep,
				  toDatenep : toDatenep,
				  counter_no:counter_no,
				  ward_no:ward_no,
				  pipe_size:pipe_size
			},
			  cache       : true,
			  async       : false,
			  success: function(response){
				 
				  if(response == "")
					 {
					  	response="0.0";
					 } 
				  var data="";


				  var data="";
				  var html=""
				  
				  twc=0;
				   tsw=0;
				   tmr=0;
				   tbill=0;
				   tmisc=0;
				   tpenalty=0;
				   trebate=0;
				   toldbal=0;
				   ttotrec=0;
				   ttotcol=0;
				   tap=0;
				   tar=0;
				   tctp=0;
				   kukl=0;
				   board=0;
				   board_penalty=0;
				  console.log(response.length);
				  for(var i=0;i<response.length;i++){

			
					  data = response[i];
					  
					  console.log(response.length);
					  waterP=data[0];
					  drainageP= data[1];
					  otherP=data[2];
					  meterC=data[3];
					  meterChangeFine=data[4];
					  excuses=data[5];
					  totalP=data[6];
					  advanceP=data[7];
					  cashCollectionAmt=data[8];
					  advancerebate=data[9];
					  oldbalance=data[10];
					  totalreceivable=data[11];
					  numberToWords(data[6]); 
					  counterNo=data[13];
					  boardamt=data[14];
					  kuklamt=data[15];
					  boardP=data[16];
					  monthYear=data[17];

					  var branchname = "${BranchName}";
						var tcad = 0;
						if (parseInt(monthYear) < 207404) {
							tcad = (parseFloat(advanceP)) + (parseFloat(advanceP * (0.03)))
									+ (parseFloat(totalP));
						} else {
							tcad = (parseFloat(advanceP)) + (parseFloat(advancerebate))
									+ (parseFloat(totalP));
						}

						twc = twc + parseFloat(waterP);
						tsw = tsw + parseFloat(drainageP);
						tmr = tmr + parseFloat(meterC);
						tbill = tbill + parseFloat((waterP + meterC + drainageP));
						tmisc = tmisc + parseFloat(otherP);
						tpenalty = tpenalty + parseFloat(meterChangeFine);
						trebate = trebate + parseFloat(excuses);
						toldbal = toldbal + parseFloat(oldbalance);
						ttotrec = ttotrec + parseFloat(totalreceivable);
						ttotcol = ttotcol + parseFloat(totalP);
						tap = tap + parseFloat(advanceP);
						if (parseInt(monthYear) < 207404) {
							tar = tar + parseFloat(advanceP * (0.03));
						} else {
							tar = tar + parseFloat(advancerebate);
						}
						tctp = tctp + parseFloat(tcad);
						board = board + parseFloat(boardamt);
						kukl = kukl + parseFloat(kuklamt);
						board_penalty = board_penalty + parseFloat(boardP);
						
						 html += "<div>";

						html += " <pre><span style='font-size: 17px;'><b>Kathmandu Upatyaka Khanepani Limited<br>Branch : "
								+ branchname
								+ "<br>Counter No.: "
								+ counterNo
								+ "<br>From Date: "
								+ fromDatenep
								+ "&nbsp;&nbsp;To Date: "
								+ toDatenep
								+ "<br><u>Cash Collection Report</u></b></span><br/><br/>"
								+

								"<span style='font-size:16px;'>  Water Charges          <b>"
								+ waterP
								+ "</b><br/>"
								+ "  Sewerage Charges       <b>"
								+ drainageP
								+ "<br/></b>"
								+ "  Meter Charges	         <b>"
								+ meterC
								+ "<br/></b>"
								+ "  Total Bill	         <b>"
								+ (waterP + meterC + drainageP)
								+ "<br/></b>"
								+ "  Total Rebate           <b>"
								+ excuses
								+ "<br/></b>"
								+ "  Net Bill		 <b>"
								+ parseFloat(
										parseFloat(waterP + meterC + drainageP)
												- parseFloat(excuses)).toFixed(2)
								+ "<br/></b>"
								+ "  KUKL Penalty	 	 <b>"
								+ parseFloat(
										parseFloat(meterChangeFine) - parseFloat(boardP))
										.toFixed(2)
								+ "<br/></b>"
								+ "  BOARD penalty	 	 <b>"
								+ parseFloat(boardP).toFixed(2)
								+ "<br/></b>"
								+ "  Total Penalty          <b>"
								+ parseFloat(meterChangeFine).toFixed(2)
								+ "<br/></b>"
								+ "  Miscellaneous Charges  <b>"
								+ parseFloat(otherP).toFixed(2)
								+ "<br/></b>"
								+
								//  "  Rebate	 	<b> "+parseFloat(excuses-parseFloat(advanceP*(0.03))).toFixed(2)+"<br/></b>"+
								"  Previous Month Arrear  <b>"
								+ oldbalance
								+ "<br/></b>"
								+
								//  "  Total Receivable       <b>"+totalreceivable+"<br/></b>"+
								"  Advance Payment        <b>"
								+ parseFloat(advanceP).toFixed(2) + "<br/></b>";
						if (parseInt(monthYear) < 207404) {
							html += "  Advance Rebate         <b>"
									+ parseFloat(advanceP * (0.03)).toFixed(2)
									+ "<br/></b>";
						} else {
							html += "  Advance Rebate         <b>"
									+ parseFloat(advancerebate).toFixed(2) + "<br/></b>";
						}

						html += "  <b>KUKL       		 <u>" + parseFloat(kuklamt).toFixed(2)
								+ "<br/></u></b>" + "  <b>Board      		 <u>"
								+ parseFloat(boardamt).toFixed(2) + "<br/></u></b>";

						html += "  <b>Total Collection       <u>"
								+ totalP
								+ "<br/></u></b>"
								+
								//  "  Total(TP+AP+AR)<b>        "+parseFloat(tcad).toFixed(2)+"</b>"+
								"	</span><br/><br/>"
								+ /* cashCollectionAmt */

								"  (Amount in word: "
								+ amtInWords
								+ ")<br/><br/>"
								+

								"<span style='font-size:17px;'><b> <u>Amount description:</u></b></span><br/><br/>"
								+

								"<span style='font-size:16px;'>  1000.......      50.......     5.......<br/><br/>"
								+ "  500........      20.......     2.......<br/><br/>"
								+ "  250........      10.......     1.......<br/><br/>"
								+ "  100........      Total Amount:.......</span><br/><br/><br/>"
								+

								"<span style='font-size:17px;'><b> <u>Cheque description:</u></b></span><br/><br/>"
								+

								"<span style='font-size:16px;'>  Cheque No.:                  	Amount:                    Grand total:.......<br/><br/><br/>"
								+

								"  Hand over:                                               Take over:<br/><br/>"
								+ "  Name:                                                    Name:<br/><br/>"
								+ "  Designation:                                             Designation:<br/><br/>"
								+ "  Signature:                                               Signature:<br/><br/></span>"
								+

								"</pre><br><br>";
						html += "</div>";
				  }
	           
				
	            console.log(html);
	            var printW = window.open("");
				printW.document.write(html);
				printW.document.close();
				printW.focus();
				printW.print();
				printW.close();
					  
				
				  console.log(response.length);
				  
					test1();
					
				},
				error : function(xhr) {
				},
			});
		}

		function numberToWords(amt) {

			amt = parseFloat(amt).toFixed(2);
			$.ajax({
				type : "GET",
				url : "./numberToWords",
				dataType : "text",
				data : {
					date : amt
				},
				cache : true,
				async : false,
				success : function(response) {
					if (response == "") {
						response = "--";
					}
					amtInWords = response;
				},
				error : function(xhr) {
				},
			});
		}

		function test(response) {
	
			  var data="";
			  var html=""
			  
			  twc=0;
			   tsw=0;
			   tmr=0;
			   tbill=0;
			   tmisc=0;
			   tpenalty=0;
			   trebate=0;
			   toldbal=0;
			   ttotrec=0;
			   ttotcol=0;
			   tap=0;
			   tar=0;
			   tctp=0;
			   kukl=0;
			   board=0;
			   board_penalty=0;
			  console.log(response.length);
			  for(var i=0;i<response.length;i++){

		
				  data = response[i];
				  
				  console.log(response.length);
				  waterP=data[0];
				  drainageP= data[1];
				  otherP=data[2];
				  meterC=data[3];
				  meterChangeFine=data[4];
				  excuses=data[5];
				  totalP=data[6];
				  advanceP=data[7];
				  cashCollectionAmt=data[8];
				  advancerebate=data[9];
				  oldbalance=data[10];
				  totalreceivable=data[11];
				  numberToWords(data[6]); 
				  counterNo=data[13];
				  boardamt=data[14];
				  kuklamt=data[15];
				  boardP=data[16];
				  monthYear=data[17];

				  var branchname = "${BranchName}";
					var tcad = 0;
					if (parseInt(monthYear) < 207404) {
						tcad = (parseFloat(advanceP)) + (parseFloat(advanceP * (0.03)))
								+ (parseFloat(totalP));
					} else {
						tcad = (parseFloat(advanceP)) + (parseFloat(advancerebate))
								+ (parseFloat(totalP));
					}

					twc = twc + parseFloat(waterP);
					tsw = tsw + parseFloat(drainageP);
					tmr = tmr + parseFloat(meterC);
					tbill = tbill + parseFloat((waterP + meterC + drainageP));
					tmisc = tmisc + parseFloat(otherP);
					tpenalty = tpenalty + parseFloat(meterChangeFine);
					trebate = trebate + parseFloat(excuses);
					toldbal = toldbal + parseFloat(oldbalance);
					ttotrec = ttotrec + parseFloat(totalreceivable);
					ttotcol = ttotcol + parseFloat(totalP);
					tap = tap + parseFloat(advanceP);
					if (parseInt(monthYear) < 207404) {
						tar = tar + parseFloat(advanceP * (0.03));
					} else {
						tar = tar + parseFloat(advancerebate);
					}
					tctp = tctp + parseFloat(tcad);
					board = board + parseFloat(boardamt);
					kukl = kukl + parseFloat(kuklamt);
					board_penalty = board_penalty + parseFloat(boardP);
					
					 html += "<div>";

					html += " <pre><span style='font-size: 17px;'><b>Kathmandu Upatyaka Khanepani Limited<br>Branch : "
							+ branchname
							+ "<br>Counter No.: "
							+ counterNo
							+ "<br>From Date: "
							+ fromDatenep
							+ "&nbsp;&nbsp;To Date: "
							+ toDatenep
							+ "<br><u>Cash Collection Report</u></b></span><br/><br/>"
							+

							"<span style='font-size:16px;'>  Water Charges          <b>"
							+ waterP
							+ "</b><br/>"
							+ "  Sewerage Charges       <b>"
							+ drainageP
							+ "<br/></b>"
							+ "  Meter Charges	         <b>"
							+ meterC
							+ "<br/></b>"
							+ "  Total Bill	         <b>"
							+ (waterP + meterC + drainageP)
							+ "<br/></b>"
							+ "  Total Rebate           <b>"
							+ excuses
							+ "<br/></b>"
							+ "  Net Bill		 <b>"
							+ parseFloat(
									parseFloat(waterP + meterC + drainageP)
											- parseFloat(excuses)).toFixed(2)
							+ "<br/></b>"
							+ "  KUKL Penalty	 	 <b>"
							+ parseFloat(
									parseFloat(meterChangeFine) - parseFloat(boardP))
									.toFixed(2)
							+ "<br/></b>"
							+ "  BOARD penalty	 	 <b>"
							+ parseFloat(boardP).toFixed(2)
							+ "<br/></b>"
							+ "  Total Penalty          <b>"
							+ parseFloat(meterChangeFine).toFixed(2)
							+ "<br/></b>"
							+ "  Miscellaneous Charges  <b>"
							+ parseFloat(otherP).toFixed(2)
							+ "<br/></b>"
							+
							//  "  Rebate	 	<b> "+parseFloat(excuses-parseFloat(advanceP*(0.03))).toFixed(2)+"<br/></b>"+
							"  Previous Month Arrear  <b>"
							+ oldbalance
							+ "<br/></b>"
							+
							//  "  Total Receivable       <b>"+totalreceivable+"<br/></b>"+
							"  Advance Payment        <b>"
							+ parseFloat(advanceP).toFixed(2) + "<br/></b>";
					if (parseInt(monthYear) < 207404) {
						html += "  Advance Rebate         <b>"
								+ parseFloat(advanceP * (0.03)).toFixed(2)
								+ "<br/></b>";
					} else {
						html += "  Advance Rebate         <b>"
								+ parseFloat(advancerebate).toFixed(2) + "<br/></b>";
					}

					html += "  <b>KUKL       		 <u>" + parseFloat(kuklamt).toFixed(2)
							+ "<br/></u></b>" + "  <b>Board      		 <u>"
							+ parseFloat(boardamt).toFixed(2) + "<br/></u></b>";

					html += "  <b>Total Collection       <u>"
							+ totalP
							+ "<br/></u></b>"
							+
							//  "  Total(TP+AP+AR)<b>        "+parseFloat(tcad).toFixed(2)+"</b>"+
							"	</span><br/><br/>"
							+ /* cashCollectionAmt */

							"  (Amount in word: "
							+ amtInWords
							+ ")<br/><br/>"
							+

							"<span style='font-size:17px;'><b> <u>Amount description:</u></b></span><br/><br/>"
							+

							"<span style='font-size:16px;'>  1000.......      50.......     5.......<br/><br/>"
							+ "  500........      20.......     2.......<br/><br/>"
							+ "  250........      10.......     1.......<br/><br/>"
							+ "  100........      Total Amount:.......</span><br/><br/><br/>"
							+

							"<span style='font-size:17px;'><b> <u>Cheque description:</u></b></span><br/><br/>"
							+

							"<span style='font-size:16px;'>  Cheque No.:                  	Amount:                    Grand total:.......<br/><br/><br/>"
							+

							"  Hand over:                                               Take over:<br/><br/>"
							+ "  Name:                                                    Name:<br/><br/>"
							+ "  Designation:                                             Designation:<br/><br/>"
							+ "  Signature:                                               Signature:<br/><br/></span>"
							+

							"</pre><br><br>";
					html += "</div>";
			  }
           
			
            console.log(html);
            var printW = window.open("");
			printW.document.write(html);
			printW.document.close();
			printW.focus();
			printW.print();
			printW.close();

		}

		function test1() {

			numberToWords(ttotcol);
			var branchname = "${BranchName}";

			var printW = window.open("");
			var html = "<div>";

			html += " <pre><span style='font-size: 17px;'><b>Kathmandu Upatyaka Khanepani Limited<br>Branch : "
					+ branchname
					+ "<br>Counter No.: All<br>From Date: "
					+ fromDatenep
					+ "&nbsp;&nbsp;To Date: "
					+ toDatenep
					+ "<br><u>Cash Collection Report</u></b></span><br/><br/>"
					+

					"<span style='font-size:16px;'>  Water Charges          <b>"
					+ parseFloat(twc).toFixed(2)
					+ "</b><br/>"
					+ "  Sewerage Charges       <b>"
					+ parseFloat(tsw).toFixed(2)
					+ "<br/></b>"
					+ "  Meter Charges	         <b>"
					+ parseFloat(tmr).toFixed(2)
					+ "<br/></b>"
					+ "  Total Bill	         <b>"
					+ parseFloat(tbill).toFixed(2)
					+ "<br/></b>"
					+ "  Total Rebate           <b>"
					+ parseFloat(trebate).toFixed(2)
					+ "<br/></b>"
					+ "  Net Bill		 <b>"
					+ parseFloat(parseFloat(tbill) - parseFloat(trebate))
							.toFixed(2)
					+ "<br/></b>"
					+ "  KUKL Penalty	 	 <b>"
					+ parseFloat(
							parseFloat(tpenalty) - parseFloat(board_penalty))
							.toFixed(2)
					+ "<br/></b>"
					+ "  BOARD penalty	 	 <b>"
					+ parseFloat(board_penalty).toFixed(2)
					+ "<br/></b>"
					+ "  Total Penalty          <b>"
					+ parseFloat(tpenalty).toFixed(2)
					+ "<br/></b>"
					+ "  Miscellaneous Charges  <b>"
					+ parseFloat(tmisc).toFixed(2)
					+ "<br/></b>"
					+
					// "  Rebate	 	<b> "+parseFloat(parseFloat(trebate)-parseFloat(tar)).toFixed(2)+"<br/></b>"+
					"  Previous Month Arrear  <b>"
					+ parseFloat(toldbal).toFixed(2)
					+ "<br/></b>"
					+
					// "  Total Receivable       <b>"+parseFloat(ttotrec).toFixed(2)+"<br/></b>"+
					"  Advance Payment        <b>"
					+ parseFloat(tap).toFixed(2)
					+ "<br/></b>"
					+ "  Advance Rebate         <b>"
					+ parseFloat(tar).toFixed(2)
					+ "<br/></b>"
					+ "  <b>KUKL       		 <u>"
					+ parseFloat(kukl).toFixed(2)
					+ "<br/></u></b>"
					+ "  <b>Board      		 <u>"
					+ parseFloat(board).toFixed(2)
					+ "<br/></u></b>"
					+ "  <b>Total Collection       <b><u>"
					+ parseFloat(ttotcol).toFixed(2)
					+ "<br/></u></b>"
					+
					// "  Total(TP+AP+AR)<b>        <b>"+parseFloat(tctp).toFixed(2)+"</b>"+
					"</span><br/><br/>"
					+ /* cashCollectionAmt */

					"  (Amount in word: "
					+ amtInWords
					+ ")<br/><br/>"
					+

					"<span style='font-size:17px;'><b> <u>Amount description:</u></b></span><br/><br/>"
					+

					"<span style='font-size:16px;'>  1000.......      50.......     5.......<br/><br/>"
					+ "  500........      20.......     2.......<br/><br/>"
					+ "  250........      10.......     1.......<br/><br/>"
					+ "  100........      Total Amount:.......</span><br/><br/><br/>"
					+

					"<span style='font-size:17px;'><b> <u>Cheque description:</u></b></span><br/><br/>"
					+

					"<span style='font-size:16px;'>  Cheque No.:                  	Amount:                    Grand total:.......<br/><br/><br/>"
					+

					"  Hand over:                                               Take over:<br/><br/>"
					+ "  Name:                                                    Name:<br/><br/>"
					+ "  Designation:                                             Designation:<br/><br/>"
					+ "  Signature:                                               Signature:<br/><br/></span>"
					+

					"</pre><br><br>";
			html += "</div>";

			printW.document.write(html);
			printW.document.close();
			printW.focus();
			printW.print();
			printW.close();

		}
	</script>
<style>
.form-group {
    margin-bottom: 10px;
    position: relative;
}
legend {
	text-trans none;
	font-size: 16px;
	border-color: #0DB7A5;
}
.form-horizontal .form-group {
    margin-left: 20px;
    margin-right: 40px;
}
</style>