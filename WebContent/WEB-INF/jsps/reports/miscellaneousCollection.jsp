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
								<legend class="text-bold">Miscellaneous Cash Collection Report</legend>
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
														<option value="All">All</option>
														<c:forEach var="data" items="${counter}">
																
																<option value="${data.counterNumber}">${data.counterNumber}</option>
														</c:forEach> 
													</select>
				                         </div> 
									
									<div class="col-md-3" id="addId111" >
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
	
	   var tnct=0;
	   var tncd=0;
	   var tncinst=0;
	   var tmval=0;
	   var tth=0;
	   var tnamec=0;
	   var tillg=0;
	   var tmisc=0;
	   var tadv=0;
	   var tholec=0;
	   var treamain=0;
	   var tappch=0;
	   var ttsq=0;
	   var tcrdch=0;
	   var toth=0;
	   var ttot=0;
	
	var nct="",ncd="",ncinst="",mval="",th="",namec="",illg="",misc="",adv="",tot="",amtInWords="",holec="",reamain="",appch="",tsq="",crdch="",oth="";
	function generateDayClosureReport()
	{
		 fromDatenep = $("#fromDatenep").val();
		 toDatenep= $("#toDatenep").val();
		var counter_no=$("#counter_no").val();
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
			  url: "./generatedailyMiscColReport",
			  dataType: "Json",
			  data:{
				  
				  fromDatenep : fromDatenep,
				  toDatenep : toDatenep,
				  counter_no :counter_no
			},
			  cache       : true,
			  async       : false,
			  success: function(response){
				  if(response == "")
					 {
					  	response="0.0";
					 } 
				       var data="";
				
					   tnct=0;
					   tncd=0;
					   tncinst=0;
					   tmval=0;
					   tth=0;
					   tnamec=0;
					   tillg=0;
					   tmisc=0;
					   ttot=0;
					   tadv=0;
					   tholec=0;
					   treamain=0;
					   tappch=0;
					   ttsq=0;
					   tcrdch=0;
					   toth=0;
					  for(var i=0;i<response.length;i++){
						   data = response[i];
						  
						   nct=data[0];
						   ncd=data[1];
						   ncinst=data[2];
						   mval=data[3];
						   th=data[4];
						   namec=data[5];
						   illg=data[6];
						   misc=data[7];
						   adv=data[8];
						   tot=data[9];
						   numberToWords(data[9]);
						   counterNo=data[10];
						   holec=data[11];
						   reamain=data[12];
						   appch=data[13];
						   tsq=data[14];
						   crdch=data[15];
						   oth=data[16];
					 	   test();
					  }
					  if(counter_no == "All"){
					  test1();}
				 
				 
			},
		 	error: function (xhr) {
			},
			}); 
	}
	
	
	function numberToWords(amt)
	{
		
		amt=parseFloat(amt).toFixed(2);
		$.ajax({
			  type: "GET",
			  url: "./numberToWords",
			  dataType: "text",
			  data:{date : amt},
			  cache       : true,
			  async       : false,
			  success: function(response){
				  if(response == "")
					 {
					  	response="--";
					 } 
				  amtInWords = response;
			},
		 	error: function (xhr) {
			},
			}); 
	}
	
	

	
function test()
 {
	 
		
		   var branchname="${BranchName}";
		 
		   tnct=tnct+parseFloat(nct);
		   tncd=tncd+parseFloat(ncd);
		   tncinst=tncinst+parseFloat(ncinst);
		   tmval=tmval+parseFloat(mval);
		   tth=tth+parseFloat(th);
		   tnamec=tnamec+parseFloat(namec);
		   tillg=tillg+parseFloat(illg);
		   tmisc=tmisc+parseFloat(misc);
		   tadv=tadv+parseFloat(adv);
		   tholec=tholec+parseFloat(holec);
		   treamain=treamain+parseFloat(reamain);
		   ttot=ttot+parseFloat(tot);
		   tappch=tappch+parseFloat(appch);
		   ttsq=ttsq+parseFloat(tsq);
		   tcrdch=tcrdch+parseFloat(crdch);
		   toth=toth+parseFloat(oth);
	
	 var printW = window.open("");
	 var html = "<div>";
  	 
	 html += " <pre><span style='font-size: 17px;'><b>Kathmandu Upatyaka Khanepani Limited<br>Branch : "+branchname+"<br>Counter No.: "+counterNo+"<br>From Date: "+fromDatenep+"&nbsp;&nbsp;To Date: "+toDatenep+"<br><u>Miscellaneous Cash Collection Report</u></b></span><br/><br/>"+
											
		  "<span style='font-size:16px;'>  New Connection Reg          <b>"+parseFloat(nct).toFixed(2)+"</b><br/>"+
		  "  New Connection Inst.        <b>"+parseFloat(ncinst).toFixed(2)+"<br/></b>"+
		  "  New Connection Deposit      <b>"+parseFloat(ncd).toFixed(2)+"<br/></b>"+
		  "  Meter Value	              <b>"+parseFloat(mval).toFixed(2)+"<br/></b>"+
		  "  Advance                     <b>"+parseFloat(adv).toFixed(2)+"<br/></b>"+
		  "  Temporary Hole Block	      <b>"+parseFloat(th).toFixed(2)+"<br/></b>"+
		  "  Miscellaneous	 	      <b>"+parseFloat(misc).toFixed(2)+"<br/></b>"+
		  "  Ownership Change	      <b>"+parseFloat(namec).toFixed(2)+"<br/></b>"+
		  "  Illegal Conn Penalty        <b>"+parseFloat(illg).toFixed(2)+"<br/></b>"+
		  "  Hole Change        	      <b>"+parseFloat(holec).toFixed(2)+"<br/></b>"+
		  "  Hole Maintainence           <b>"+parseFloat(reamain).toFixed(2)+"<br/></b>"+
		  "  Application Charge          <b>"+parseFloat(appch).toFixed(2)+"<br/></b>"+
		  "  Tender/Sealed Quotation     <b>"+parseFloat(tsq).toFixed(2)+"<br/></b>"+
		  "  Card Charge       	      <b>"+parseFloat(crdch).toFixed(2)+"<br/></b>"+
		  "  Others       		      <b>"+parseFloat(oth).toFixed(2)+"<br/></b>"+
		  "  <b>Total Collection            <u>"+parseFloat(tot).toFixed(2)+"<br/></u></b>"+
		  "	</span><br/><br/>"+
											
  "  (Amount in word: "+amtInWords+")<br/><br/>"+
											
 "<span style='font-size:17px;'><b> <u>Amount description:</u></b></span><br/><br/>"+
 
  "<span style='font-size:16px;'>  1000.......      50.......     5.......<br/><br/>"+
  "  500........      20.......     2.......<br/><br/>"+
  "  250........      10.......     1.......<br/><br/>"+
  "  100........      Total Amount:.......</span><br/><br/><br/>"+   
											
  "<span style='font-size:17px;'><b> <u>Cheque description:</u></b></span><br/><br/>"+
 
  "<span style='font-size:16px;'>  Cheque No.:                  	Amount:                    Grand total:.......<br/><br/><br/>"+                  	

  "  Hand over:                                               Take over:<br/><br/>"+
  "  Name:                                                    Name:<br/><br/>"+ 
  "  Designation:                                             Designation:<br/><br/>"+  
  "  Signature:                                               Signature:<br/><br/></span>"+
	
	"</pre><br><br>";
	html += "</div>";

    printW.document.write(html);
    printW.document.close();
    printW.focus();
    printW.print();
    printW.close(); 

    alert("Click Ok to get Next Counter Payment");
    
    }
    
    
function test1()
{
	 
	numberToWords(ttot)	;
	 var branchname="${BranchName}";
		
	  var printW = window.open("");
	 var html = "<div>";
 
	 html += " <pre><span style='font-size: 17px;'><b>Kathmandu Upatyaka Khanepani Limited<br>Branch : "+branchname+"<br>Counter No.: All<br>From Date: "+fromDatenep+"&nbsp;&nbsp;To Date: "+toDatenep+"<br><u>Miscellaneous Cash Collection Report</u></b></span><br/><br/>"+
		
	  "<span style='font-size:16px;'>  New Connection Reg          <b>"+parseFloat(tnct).toFixed(2)+"</b><br/>"+
	  "  New Connection Inst.        <b>"+parseFloat(tncinst).toFixed(2)+"<br/></b>"+
	  "  New Connection Deposit      <b>"+parseFloat(tncd).toFixed(2)+"<br/></b>"+
	  "  Meter Value	              <b>"+parseFloat(tmval).toFixed(2)+"<br/></b>"+
	  "  Advance                     <b>"+parseFloat(tadv).toFixed(2)+"<br/></b>"+
	  "  Temporary Hole Block	      <b>"+parseFloat(tth).toFixed(2)+"<br/></b>"+
	  "  Miscellaneous	 	      <b>"+parseFloat(tmisc).toFixed(2)+"<br/></b>"+
	  "  Ownership Change	      <b>"+parseFloat(tnamec).toFixed(2)+"<br/></b>"+
	  "  Illegal Conn Penalty        <b>"+parseFloat(tillg).toFixed(2)+"<br/></b>"+
	  "  Hole Change        	      <b>"+parseFloat(tholec).toFixed(2)+"<br/></b>"+
	  "  Hole Maintainence           <b>"+parseFloat(treamain).toFixed(2)+"<br/></b>"+
	  "  Application Charge          <b>"+parseFloat(tappch).toFixed(2)+"<br/></b>"+
	  "  Tender/Sealed Quotation     <b>"+parseFloat(ttsq).toFixed(2)+"<br/></b>"+
	  "  Card Charge       	      <b>"+parseFloat(tcrdch).toFixed(2)+"<br/></b>"+
	  "  Others       		      <b>"+parseFloat(toth).toFixed(2)+"<br/></b>"+
	  "  <b>Total Collection            <u>"+parseFloat(ttot).toFixed(2)+"<br/></u></b>"+
	  "	</span><br/><br/>"+
	  
	  "  (Amount in word: "+amtInWords+")<br/><br/>"+
											
"<span style='font-size:17px;'><b> <u>Amount description:</u></b></span><br/><br/>"+

 "<span style='font-size:16px;'>  1000.......      50.......     5.......<br/><br/>"+
 "  500........      20.......     2.......<br/><br/>"+
 "  250........      10.......     1.......<br/><br/>"+
 "  100........      Total Amount:.......</span><br/><br/><br/>"+   
											
 "<span style='font-size:17px;'><b> <u>Cheque description:</u></b></span><br/><br/>"+

 "<span style='font-size:16px;'>  Cheque No.:                  	Amount:                    Grand total:.......<br/><br/><br/>"+                  	

 "  Hand over:                                               Take over:<br/><br/>"+
 "  Name:                                                    Name:<br/><br/>"+ 
 "  Designation:                                             Designation:<br/><br/>"+  
 "  Signature:                                               Signature:<br/><br/></span>"+
	
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