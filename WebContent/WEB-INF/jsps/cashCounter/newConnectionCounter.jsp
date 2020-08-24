<%@include file="/WEB-INF/decorators/taglibs.jsp"%>
<script>

$(document).ready(function(){
	document.getElementById("connectionNo").focus();
		$('#umpayments').show();
		$('#paymentsModule').addClass('active');
		
   $('#nepali_date').nepaliDatePicker({
		 dateFormat: "%D, %M %d, %y",
			npdMonth: true,
			npdYear: true
	});
});

function onKeyPress(key,id)
{	
     if(key.keyCode== 13){
    	 if(id=="towards"){
    		 document.getElementById("towards").focus();
    		 key.preventDefault();
    		 return false;
    	 }
     }
}  

function checkRemarks(){
	var other_paid_type=$("#other_paid_type").val();
	if(other_paid_type==null||other_paid_type==""){
		$('#other_paid_amt').prop('readonly', true);
		$('#other_paid_amt').val(0);
		checkNumericAmount();
	} else {
		$('#other_paid_amt').prop('readonly', false);
	}
}

function checkNumericAmount()
{
	
	
	var nctap=$("#nctap").val();
	var ncdeposit=$("#ncdeposit").val();
	var mvalue=$("#mvalue").val();
	var advance=$("#advance").val();
	var tempholeamt=$("#tempholeamt").val();
	var nameChangeAmt=$("#nameChangeAmt").val();
	var miscellaneous_cost=$("#miscellaneous_cost").val();
	var ilg_con_amt=$("#ilg_con_amt").val();
	var nc_inst=$("#nc_inst").val();
	var other_paid_amt=$("#other_paid_amt").val();
	
    if(nctap=="" || $.isNumeric(nctap)==false)
	{
    	$("#nctap").val(0);
    	nctap=0;
	}if(ncdeposit=="" || $.isNumeric(ncdeposit)==false)
	{
		$("#ncdeposit").val(0);
		ncdeposit=0;
	}
	if(mvalue=="" || $.isNumeric(mvalue)==false)
	{
		$("#mvalue").val(0);
		mvalue=0;
	}
	if(advance=="" || $.isNumeric(advance)==false)
	{
		$("#advance").val(0);
		advance=0;
	}
	if(tempholeamt=="" || $.isNumeric(tempholeamt)==false)
	{
		 $("#tempholeamt").val(0);
		 tempholeamt=0;
	}
	
	if(nameChangeAmt=="" || $.isNumeric(nameChangeAmt)==false)
	{
		 $("#nameChangeAmt").val(0);
		 nameChangeAmt=0;
	}
	
	if(miscellaneous_cost=="" || $.isNumeric(miscellaneous_cost)==false)
	{
		 $("#miscellaneous_cost").val(0);
		 miscellaneous_cost=0;
	}
	
	if(ilg_con_amt=="" || $.isNumeric(ilg_con_amt)==false)
	{
		 $("#ilg_con_amt").val(0);
		 ilg_con_amt=0;
	}
	
	if(nc_inst=="" || $.isNumeric(nc_inst)==false)
	{
		 $("#nc_inst").val(0);
		 nc_inst=0;
	}
	if(other_paid_amt=="" || $.isNumeric(other_paid_amt)==false)
	{
		 $("#other_paid_amt").val(0);
		 other_paid_amt=0;
	}
	
	
	if(parseFloat(nctap)<0){
		alert("New Connection Registration Amount should be greater than Zero");
		$("#nctap").val(0);
	}
	
	if(parseFloat(nc_inst)<0){
		alert("New Connection Installation Amount should be greater than Zero");
		$("#nc_inst").val(0);
	}
	
	if(parseFloat(miscellaneous_cost)<0){
		alert("Miscellaneous Amount should be greater than Zero");
		$("#miscellaneous_cost").val(0);
	}
	
	if(parseFloat(ilg_con_amt)<0){
		alert("Illegal Connection Amount should be greater than Zero");
		$("#ilg_con_amt").val(0);
	}
	
	if(parseFloat(nameChangeAmt)<0){
		alert("Name Change Amount should be greater than Zero");
		$("#nameChangeAmt").val(0);
	}
	
	if(parseFloat(tempholeamt)<0){
		alert("Temporary Hole Change Amount should be greater than Zero");
		$("#tempholeamt").val(0);
	}
	
	if(parseFloat(ncdeposit)<0){
		alert("New Connection Deposit Amount should be greater than Zero");
		$("#ncdeposit").val(0);
	}
	
	if(parseFloat(mvalue)<0){
		alert("Meter Charge Amount should be greater than Zero");
		$("#mvalue").val(0);
	}
	
	if(parseFloat(advance)<0){
		alert("Advance Amount should be greater than Zero");
		$("#advance").val(0);
	}
	
	
	
	 nctap=$("#nctap").val();
	 ncdeposit=$("#ncdeposit").val();
	 mvalue=$("#mvalue").val();
	 advance=$("#advance").val();
	 tempholeamt=$("#tempholeamt").val();
	 nameChangeAmt=$("#nameChangeAmt").val();
	 miscellaneous_cost=$("#miscellaneous_cost").val();
	 ilg_con_amt=$("#ilg_con_amt").val();
	 nc_inst=$("#nc_inst").val();
	 other_paid_amt=$("#other_paid_amt").val();
	 var totalamount=parseFloat(parseFloat(nctap)+parseFloat(ncdeposit)+parseFloat(mvalue)+parseFloat(advance)+parseFloat(tempholeamt)+parseFloat(nameChangeAmt)+parseFloat(miscellaneous_cost)+parseFloat(ilg_con_amt)+parseFloat(nc_inst)+parseFloat(other_paid_amt)).toFixed(2);
	 $("#amount").val(totalamount);
	
	}
function validateField(){
	
	var connectionNo=$("#connectionNo").val();
	var customerName=$("#customerName").val();
	var towards=$("#towards").val();
	var other_paid_type=$('#other_paid_type').val();
	var other_paid_amt=$('#other_paid_amt').val();
	var amount=$('#amount').val();
	
	if(parseFloat(amount)==0){
		checkNumericAmount();
		amount=$('#amount').val();
		if(parseFloat(amount)==0){
			swal({
		         title: "Total Amount Cannot be 0.",
		         text: "",
		         confirmButtonColor: "#2196F3",
		     });
			return false;
		}
	}
	
	if(connectionNo==0 || connectionNo=="")
	{
	 swal({
         title: "Please Enter Connection No.",
         text: "",
         confirmButtonColor: "#2196F3",
     });
	return false;
	}
	else if(customerName==null || customerName==""){
	   alert("Connection Number is not Valid");
	   return false;
    }
	else if(towards==null || towards==""){
	   alert("Please Select Account Head");
	   return false;
    } 
    else{
    	if(other_paid_type != "" && other_paid_type !=null){
    		//alert(other_paid_type);
    		if(other_paid_amt==""||other_paid_amt==null){
    			alert("Please Enter Other Paid Amount");
    			return false;
    		}
    	}
    	
    	
		if(confirm("Are you sure to submit?")){
			 $('#submitId1').hide();
			   
		 }else{
			 $('#submitId1').show();
			 return false;
		 }
    }
}
function setConsumerData(connectionno){
	
	if($('#connectionNo').val().trim()=='')
	{
	$('#alertDiv').show();
    $('#alertDiv').html('<div class="alert alert-danger alert-bordered"><button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button><span class="text" id="alert">Please enter connection no.</span>');
      return false;
	}
	
	$.ajax({
		  type: "GET",
		  url: "./getConsumerMasterDetails1/"+connectionno,
		  dataType: "json",
		  async       : false,
		  cache       : false,
		  success: function(response){
			 
			  if($('#connectionNo').val()=="NC"){
				  
			  }else{
			 
			  if(response.length==0)
				{
					swal({
			             title: "No Data Found for Connection No : "+connectionno,
			             text: "",
			             confirmButtonColor: "#2196F3",
			         }); 
					$("#connectionNo").val("");
					return false;
				}else{	
			  
				  var data1=response[0];
				  $("#customerName").val(data1.name_eng);
				  $("#conType").val(data1.con_type);
				  $("#areaNo").val(data1.area_no);
				  $("#pipeSize").val(data1.pipe_size);
				  $("#ilg_con_amt").val(data1.illegalConPenalty);
				  $("#applicationId").prop("readonly", true);
				}
			  
		        }
			  }
			  
	
		});
}



function getApplicationId(){
	
	var applicationId=$('#applicationId').val();
	
	if(applicationId=="" || applicationId==null){
		alert("Please Enter Application ID");
		return false;
	}
	else{
		
		
		
		$.ajax({
			  type: "GET",
			  url: "./getApplicationById/"+applicationId,
			  dataType: "json",
			  async       : false,
			  cache       : false,
			  success: function(response){
				 
				  if(response==null || response=="")
					{
						swal({
				             title: "No Data Found for Application Id : "+applicationId,
				             text: "",
				             confirmButtonColor: "#2196F3",
				         }); 
						$("#applicationId").val("");
						$("#customerName").val("");
					    $("#areaNo").val("");
					    $("#conType").val("");
					    $("#pipeSize").val("");
					    $("#connectionNo").val("");
						  
						return false;
					}else{	

						
					  
					  
						$("#applicationId").val(response[0]);
						$("#customerName").val(response[1]);
						$("#areaNo").val(response[2]);
						$("#conType").val(response[3]);
						$("#pipeSize").val(response[4]);

						if (response[5] == null || response[5] == "") {
							$("#connectionNo").val("NC");
						} else {
							$("#connectionNo").val(response[5]);
						}
						document.getElementById("connectionNo").readOnly = true;
						
						if((response[6]==null || response[6]=="") && response[8]!=2){
							
							$('#nctap').val(30);
							$('#nc_inst').val(0);
							$('#ncdeposit').val(0);
							$('#mvalue').val(0);
							$('#advance').val(0);
							$('#tempholeamt').val(0);
							$('#nameChangeAmt').val(0);
							$('#miscellaneous_cost').val(0);
							$('#ilg_con_amt').val(0);
							document.getElementById("nctap").readOnly = true;
							document.getElementById("nc_inst").readOnly = true;
							document.getElementById("ncdeposit").readOnly = true;
							document.getElementById("mvalue").readOnly = true;
							document.getElementById("advance").readOnly = true;
							document.getElementById("tempholeamt").readOnly = true;
							document.getElementById("nameChangeAmt").readOnly = true;
							document.getElementById("miscellaneous_cost").readOnly = true;
							document.getElementById("ilg_con_amt").readOnly = true;
							checkNumericAmount();
							
							
						}else{
							
							if(response[8]!=2){
								
								$.ajax({
									  type: "GET",
									  url: "./getEstimationData/"+applicationId,
									  dataType: "json",
									  async       : false,
									  cache       : false,
									  success: function(response){
										
										  if(response.data==null || response.data=="")
											{
											          
									         var msg=response.data1;
									         alert(msg);
									         paidamountnotsucessful();
											  
											}else{
												alert("hi");
												var data=response.data;
												$('#nc_inst').val(data[1]);
												$('#ncdeposit').val(data[2]);
												$('#mvalue').val(data[3]);
												$('#miscellaneous_cost').val(data[4]);
												$('#advance').val(data[5]);
												$('#tempholeamt').val(0);
												$('#nameChangeAmt').val(0);
												$('#ilg_con_amt').val(0);
												
												document.getElementById("nctap").readOnly = true;
												document.getElementById("nc_inst").readOnly = true;
												document.getElementById("ncdeposit").readOnly = true;
												document.getElementById("mvalue").readOnly = true;
												document.getElementById("advance").readOnly = true;
												document.getElementById("tempholeamt").readOnly = true;
												document.getElementById("nameChangeAmt").readOnly = true;
												document.getElementById("miscellaneous_cost").readOnly = true;
												document.getElementById("ilg_con_amt").readOnly = true;
												checkNumericAmount();
												
											}
										 
										  }
										  
								
									});
								
								
								
							}
							
						}
						

							}
						},
						error: function (xhr) {
							swal({
					             title: "No Data Found for Application Id : "+applicationId,
					             text: "",
					             type: 'warning',
					             confirmButtonColor: "#2196F3",
					         }); 
							$("#applicationId").val("");
							$("#customerName").val("");
						    $("#areaNo").val("");
						    $("#conType").val("");
						    $("#pipeSize").val("");
						    $("#connectionNo").val("");
							  
							return false;
							},
				});

		}

	}



function paidamountnotsucessful()
{


	location.reload();
	}

</script>



<c:if test="${not empty msg}">
	<script>		        
		            
	
					
					var receiptno="${peEntity.receiptNo}";
		            var counterNo="${peEntity.counterno}";
		            var branch="${CBranchName}";
		            var branchcode="${branchcode}";
		            var userOfficialName="${userOfficialName}";

		          
		            var connection_no="${peEntity.connectionNo}";
		            var nepdate="${nepdate}";
					var cns="${consumerMaster}";
					var objnc="${objnc}";
					var consumerName="";
					var areano="";
					
					if(cns==null || cns==""){
						 var re=objnc.split(",");
						 consumerName=re[1];
						 areano=re[2];
					}else{
						 
						 consumerName="${consumerMaster.name_eng}";
			             if(consumerName=="" || consumerName==null){
			            	consumerName="${consumerMaster.name_nep}";
			             }else{
			            	 consumerName=consumerName.charAt(0).toUpperCase()+ consumerName.toLowerCase().slice(1);
			             }
				         areano="${consumerMaster.area_no}";
					}
					
					
					
		           
		            
		            var type="${peEntity.payMode}";
		            var cheqDDcash="";
		            if(type==2){
		            	cheqDDcash="Cheque";
		            }else if(type==3){
		            	cheqDDcash="DD";
		            }else{
		            	cheqDDcash="Cash";
		            }
		            
		            
		            
		            var chequeno="${peEntity.cdno}";
		            var nctap="${peEntity.nctap}";
		            var ncdeposit="${peEntity.ncdeposit}";
		            var mvalue="${peEntity.mvalue}";
		            var tempholeamt="${peEntity.tempholeamt}";
		            var nameChangeAmt="${peEntity.nameChangeAmt}";
		            var miscellaneous_cost="${peEntity.miscellaneous_cost}";
		            var advance="${peEntity.advance}";
		            var ilg_con_amt="${peEntity.ilg_con_amt}";
		            var towards="${peEntity.towards}";
		            var ncinst="${peEntity.nc_inst}";
		            var other_paid_type="${peEntity.other_paid_type}";
		            var other_paid_amt="${peEntity.other_paid_amt}";
					
		            /* if(towards=="NCT"){
		            	towards="New Connection Reg";
		            }else if(towards=="NCD"){
		            	towards="New Connection Deposit";
		            }
		            else if(towards=="MV"){
		            	towards="Meter Value";
		            }
		            else if(towards=="ADP"){
		            	towards="Advance Payment";
		            }
		            else if(towards=="THB"){
		            	towards="Temporary Hole Block";
		            }
		            else if(towards=="NMC"){
		            	towards="Name Change";
		            }
		            else if(towards=="MISC"){
		            	towards="Miscellaneous";
		            }
		            else if(towards=="ILCP"){
		            	towards="Illegal Connection Penalty";
		            }
		            else if(towards=="HC"){
		            	towards="Hole Change";
		            }
			        */     
		            
		            var amount="${peEntity.amount}";
		           
		            var username="${counterUserName}";
		            
		            //Newly Added
     		       
					 
		            var printW = window.open("");
				    var html = "<div>";
				   
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
					   +"  <th colspan='2' align='center'><u>NEW CONNECTION PAYMENT RECEIPT</u></th>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   +"    <td>Receipt No.<br></td>"
					   +"    <td>:&nbsp;&nbsp;<b>"+receiptno+"</b></td>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   +"    <td>Date.<br></td>"
					   +"    <td>:&nbsp;&nbsp;<b>"+nepdate+"</b></td>"
					   +"  </tr>"
					   
					   
					   +"  <tr>"
					   +"    <td>Customer No.<br></td>"
					   +"    <td>:&nbsp;&nbsp;<b>"+connection_no+"</b></td>"
					   +"  </tr>"
					   
					   +"    <td>Connection No.<br></td>"
					   +"    <td>:&nbsp;&nbsp;<b>"+connection_no+"</b></td>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   +"    <td><b>Area No.</b><br></td>"
					   +"    <td>:&nbsp;&nbsp;"+areano+"</td>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   +"    <td>Name<br></td>"
					   +"    <td>:&nbsp;&nbsp;"+consumerName+"</td>"
					   +"  </tr>"
					 
					   
					   +"  <tr>"
					   +"    <td>Payment Mode<br></td>"
					   +"    <td>:&nbsp;&nbsp;"+cheqDDcash+"</td>"
					   +"  </tr>"
					   
					   
					   +"  <tr>"
					   +"    <td>Cheque No.<br></td>"
					   +"    <td>:&nbsp;&nbsp;<b>"+chequeno+"</b></td>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   +"    <td>Pay Towards<br></td>"
					   +"    <td>:&nbsp;&nbsp;<b>"+towards+"</b></td>"
					   +"  </tr>";
					   
					  if(nctap!=0){
					   html+="   <tr>"
					   +"    <td>New Connection Reg<br></td>"
					   +"    <td>:&nbsp;&nbsp;"+nctap+"</td>"
					   +"  </tr>";
					  }
					  if(ncinst!=0){
					   html+="   <tr>"
					   +"    <td>New Connection Inst.<br></td>"
					   +"    <td>:&nbsp;&nbsp;"+ncinst+"</td>"
					   +"  </tr>";
					  }
					  if(ncdeposit!=0){
					   html+="   <tr>"
					   +"    <td>New Connection Deposit<br></td>"
					   +"    <td>:&nbsp;&nbsp;"+ncdeposit+"</td>"
					   +"  </tr>";
					  }
					  if(mvalue!=0){
					   html+="   <tr>"
					   +"    <td>Meter Value<br></td>"
					   +"    <td>:&nbsp;&nbsp;"+mvalue+"</td>"
					   +"  </tr>";
					  }
					  if(advance!=0){ 
					   html+="   <tr>"
					   +"    <td>Advance Payment<br></td>"
					   +"    <td>:&nbsp;&nbsp;"+advance+"</td>"
					   +"  </tr>";
					  }
					  if(tempholeamt!=0){
					   html+="  <tr>"
					   +"    <td>Temporary Hole Block<br></td>"
					   +"    <td>:&nbsp;&nbsp;<b>"+tempholeamt+"</b></td>"
					   +"  </tr>";
					  }
					  if(miscellaneous_cost!=0){
					   html+="  <tr>"
					   +"    <td>Miscellaneous<br></td>"
					   +"    <td>:&nbsp;&nbsp;"+miscellaneous_cost+"</td>"
					   +"  </tr>";
					  }
					  if(nameChangeAmt!=0){
					   html+="  <tr>"
					   +"    <td>Name Change<br></td>"
					   +"    <td>:&nbsp;&nbsp;"+nameChangeAmt+"</td>"
					   +"  </tr>";
					  }
					 if(ilg_con_amt!=0){
					   html+="  <tr>"
					   +"    <td>Illegal Connection Penalty<br></td>"
					   +"    <td>:&nbsp;&nbsp;"+ilg_con_amt+"</td>"
					   +"  </tr>";
					 }
					 
					 if(other_paid_type!=null && other_paid_type!=""){
						   html+="  <tr>"
						   +"    <td>"+other_paid_type+"<br></td>"
						   +"    <td>:&nbsp;&nbsp;"+other_paid_amt+"</td>"
						   +"  </tr>";
						 }
					   
					   html+="  <tr>"
					   +"    <td>Total Amount Paid Rs.<br></td>"
					   +"    <td>:&nbsp;&nbsp;<b><u>"+amount+"</u></b></td>"
					   +"  </tr>"
					   
					   
					   +"  <tr>"
					   +"    <td>Received By <br></td>"
					   +"    <td>:&nbsp;&nbsp;("+username+" - Counter No. :"+counterNo+")</td>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   +"    <td colspan='2'><center>*****Computer Generated Receipt*****</center></td>"
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
				    printW.document.close();
				    printW.focus();
				    printW.print();
				    printW.close();

				    
				    
</script>
</c:if>


<script>

function getDuplicateReceipt(){

var receiptno=$('#receiptNumber').val();
$.ajax({
		  type: "GET",
		  url: "./searchReceiptNoNewCounter/"+receiptno,
		  dataType: "json",
		  cache       : false,
		  async       : false,
		  success: function(response)
		  {
			  if(response[0]=="Receipt Doesn't Exist")
			  	{
			  		swal({
			            title: "Entered Receipt Number is not available",
			            text: "",
			            confirmButtonColor: "#2196F3",
			        });
					return false;
			  	}
			  else{
				   
				  
				    var receiptno=response[1].receiptNo;
		            var counterNo=response[1].counterno;
		            var connection_no=response[1].connectionNo;
		            var branch="${CBranchName}";
		            var userOfficialName="${userOfficialName}";

		          
		            var cns=response[2];
		            var nepdate=response[3];
		            var objnc=response[4];
					var consumerName="";
					var areano="";
					
					if(cns==null || cns==""){
						 consumerName=objnc[1];
						 areano=objnc[2];
					}else{
						 
						    consumerName=response[2].name_eng;
				            if(consumerName=="" || consumerName==null){
				            	consumerName=response[2].name_nep;
				            }else{
				            	consumerName=consumerName.charAt(0).toUpperCase()+ consumerName.toLowerCase().slice(1);
				            }
				            areano=response[2].area_no;
					}
					
					

		           
		            
		            var type=response[1].payMode;
		            var cheqDDcash="";
		            if(type==2){
		            	cheqDDcash="Cheque";
		            }else if(type==3){
		            	cheqDDcash="DD";
		            }else{
		            	cheqDDcash="Cash";
		            }
		            
		            var chequeno=response[1].cdno==null?"":response[1].cdno;
		            var nctap=response[1].nctap;
		            var ncdeposit=response[1].ncdeposit;
		            var mvalue=response[1].mvalue;
		            var tempholeamt=response[1].tempholeamt;
		            var nameChangeAmt=response[1].nameChangeAmt;
		            var miscellaneous_cost=response[1].miscellaneous_cost;
		            var advance=response[1].advance;
		            var ilg_con_amt=response[1].ilg_con_amt;
		            var towards=response[1].towards;
		            var amount=response[1].amount;
		            var ncinst=response[1].nc_inst;
		            var other_paid_type=response[1].other_paid_type;
		            var other_paid_amt=response[1].other_paid_amt;
	                /* if(towards=="NCT"){
		            	towards="New Connection Tap";
		            }else if(towards=="NCD"){
		            	towards="New Connection Deposit";
		            }
		            else if(towards=="MV"){
		            	towards="Meter Value";
		            }
		            else if(towards=="ADP"){
		            	towards="Advance Payment";
		            }
		            else if(towards=="THB"){
		            	towards="Temporary Hole Block";
		            }
		            else if(towards=="NMC"){
		            	towards="Name Change";
		            }
		            else if(towards=="MISC"){
		            	towards="Miscellaneous";
		            }
		            else if(towards=="ILCP"){
		            	towards="Illegal Connection Penalty";
		            }
			    */         
		            var username="${counterUserName}";
		            
		            //Newly Added
   		       
					 
		            var printW = window.open("");
				    var html = "<div>";
				   
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
					   +"  <th colspan='2' align='center'><u>NEW CONNECTION PAYMENT RECEIPT</u></th>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   +"    <td>Receipt No.<br></td>"
					   +"    <td>:&nbsp;&nbsp;<b>"+receiptno+"</b></td>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   +"    <td>Date.<br></td>"
					   +"    <td>:&nbsp;&nbsp;<b>"+nepdate+"</b></td>"
					   +"  </tr>"
					   
					   
					   +"  <tr>"
					   +"    <td>Customer No.<br></td>"
					   +"    <td>:&nbsp;&nbsp;<b>"+connection_no+"</b></td>"
					   +"  </tr>"
					   
					   +"    <td>Connection No.<br></td>"
					   +"    <td>:&nbsp;&nbsp;<b>"+connection_no+"</b></td>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   +"    <td><b>Area No.</b><br></td>"
					   +"    <td>:&nbsp;&nbsp;"+areano+"</td>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   +"    <td>Name<br></td>"
					   +"    <td>:&nbsp;&nbsp;"+consumerName+"</td>"
					   +"  </tr>"
					 
					   
					   +"  <tr>"
					   +"    <td>Payment Mode<br></td>"
					   +"    <td>:&nbsp;&nbsp;"+cheqDDcash+"</td>"
					   +"  </tr>"
					   
					   
					   +"  <tr>"
					   +"    <td>Cheque No<br></td>"
					   +"    <td>:&nbsp;&nbsp;<b>"+chequeno+"</b></td>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   +"    <td>Pay Towards<br></td>"
					   +"    <td>:&nbsp;&nbsp;<b>"+towards+"</b></td>"
					   +"  </tr>";
					   
					  if(nctap!=0){
					   html+="   <tr>"
					   +"    <td>New Connection Reg<br></td>"
					   +"    <td>:&nbsp;&nbsp;"+nctap+"</td>"
					   +"  </tr>";
					  }
					  if(ncinst!=0){
					   html+="   <tr>"
					   +"    <td>New Connection Inst.<br></td>"
					   +"    <td>:&nbsp;&nbsp;"+ncinst+"</td>"
					   +"  </tr>";
					  }
					  if(ncdeposit!=0){
					   html+="   <tr>"
					   +"    <td>New Connection Deposit<br></td>"
					   +"    <td>:&nbsp;&nbsp;"+ncdeposit+"</td>"
					   +"  </tr>";
					  }
					  if(mvalue!=0){
					   html+="   <tr>"
					   +"    <td>Meter Value<br></td>"
					   +"    <td>:&nbsp;&nbsp;"+mvalue+"</td>"
					   +"  </tr>";
					  }
					  if(advance!=0){ 
					   html+="   <tr>"
					   +"    <td>Advance Payment<br></td>"
					   +"    <td>:&nbsp;&nbsp;"+advance+"</td>"
					   +"  </tr>";
					  }
					  if(tempholeamt!=0){
					   html+="  <tr>"
					   +"    <td>Temporary Hole Block<br></td>"
					   +"    <td>:&nbsp;&nbsp;<b>"+tempholeamt+"</b></td>"
					   +"  </tr>";
					  }
					  if(miscellaneous_cost!=0){
					   html+="  <tr>"
					   +"    <td>Miscellaneous<br></td>"
					   +"    <td>:&nbsp;&nbsp;"+miscellaneous_cost+"</td>"
					   +"  </tr>";
					  }
					  if(nameChangeAmt!=0){
					   html+="  <tr>"
					   +"    <td>Name Change<br></td>"
					   +"    <td>:&nbsp;&nbsp;"+nameChangeAmt+"</td>"
					   +"  </tr>";
					  }
					 if(ilg_con_amt!=0){
					   html+="  <tr>"
					   +"    <td>Illegal Connection Penalty<br></td>"
					   +"    <td>:&nbsp;&nbsp;"+ilg_con_amt+"</td>"
					   +"  </tr>";
					 }
					 
					 if(other_paid_type!=null && other_paid_type!=""){
						   html+="  <tr>"
						   +"    <td>"+other_paid_type+"<br></td>"
						   +"    <td>:&nbsp;&nbsp;"+other_paid_amt+"</td>"
						   +"  </tr>";
						 }
					   
					   html+="  <tr>"
					   +"    <td>Total Amount Paid Rs.<br></td>"
					   +"    <td>:&nbsp;&nbsp;<b><u>"+amount+"</u></b></td>"
					   +"  </tr>"
					   
					   
					   +"  <tr>"
					   +"    <td>Received By<br></td>"
					   +"    <td>:&nbsp;&nbsp;("+username+" - Counter No. :"+counterNo+")</td>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   +"    <td colspan='2'><center>*****Computer Generated Receipt*****</center></td>"
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
				    printW.document.close();
				    printW.focus();
				    printW.print();
				    printW.close();

				  
				  
				    
				  
				  
			  }     
		  }
	}); 
}

//Cancelled Receipt Script

function searchObsDetails()
{
	  if($('#receiptnonc').val().trim()=='')
	{
		$('#alertDiv').show();
	    $('#alertDiv').html('<div class="alert alert-danger alert-bordered"><button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button><span class="text" id="alert">Please enter Receipt No</span>');
	    $("#alertDiv").fadeOut(5000);
	     return false;
   }  
	
	 var receiptno=$('#receiptnonc').val();
	$.ajax({
			  type: "GET",
			  url: "./searchReceiptNo/"+receiptno,
			  dataType: "json",
			  cache       : false,
			  async       : false,
			  success: function(response)
			  {
				  if(response[0]=="Receipt Doesn't Exist")
				  	{
				  		swal({
				            title: "Receipt No : "+receiptno+" Does't Exist",
				            text: "",
				            confirmButtonColor: "#2196F3",
				        });
						return false;
				  	}
				  else{
				  var html="";
				  
				  var towards=response[1].towards;
				  if(towards=="NCT"){
		            	towards="New Connection Tap";
		            }else if(towards=="NCD"){
		            	towards="New Connection Deposit";
		            }
		            else if(towards=="MV"){
		            	towards="Meter Value";
		            }
		            else if(towards=="ADP"){
		            	towards="Advance Payment";
		            }
		            else if(towards=="THB"){
		            	towards="Temporary Hole Block";
		            }
		            else if(towards=="NMC"){
		            	towards="Name Change";
		            }
		            else if(towards=="MISC"){
		            	towards="Miscellaneous";
		            }
		            else if(towards=="ILCP"){
		            	towards="Illegal Connection Penalty";
		            }
				  
				  
				  html+="<tr>"
						+" <td>"+response[1].connectionNo+"</td>"
						+" <td>"+response[2].name_eng+"</td>"
						+" <td>"+response[1].receiptNo+"</td>"
						+" <td>"+response[3]+"</td>"
						+" <td>"+response[1].amount+"</td>"
						+" <td>"+towards+"</td>";
						if(response[1].payMode=='1'){
							html+=" <td>Cash</td>";
						}else if(response[1].payMode=='2'){
							html+=" <td>Cheque</td>";
						}else if(response[1].payMode=='3'){
							html+=" <td>DD</td>";
						}else{
							html+=" <td></td>";
						}
						var cancelledremarks="Not Cancelled";
						if(response[1].cancelledremarks=="" || response[1].cancelledremarks==null){
							cancelledremarks="Not Cancelled";
						}else{
							cancelledremarks="Cancelled";
						}
						html+=" <td><a href='#' onclick=\"cancelReceipt('"+response[1].connectionNo+"','"+response[1].receiptNo+"','"+cancelledremarks+"')\">[Cancel Receipt]</a></td>"  
						
						+" </tr>";
				  
				  
				 
				  $("#tableid123").html(html);	
				  $("#observnDetails").show();
				  loadSearchFilter1('tableForm',html,'tableid123');
				  }     
			  }
		}); 
}
function loadSearchFilter1(param,tableData,temp)
{
    $('#'+param).dataTable().fnClearTable();
    $('#'+param).dataTable().fnDestroy();
    $('#'+temp).html(tableData);
    $('#'+param).dataTable();

} 
function cancelReceipt(connectionno,receiptno,cancelledremarks){
	
	if(cancelledremarks=="Not Cancelled"){
		$.ajax({
			  type: "GET",
			  url: "./cancelReceipt/"+connectionno+"/"+receiptno,
			  dataType: "json",
			  cache       : false,
			  async       : false,
			  success: function(response)
			  {
				  alert(response[0]);
				  
			  }
		}); 
	}else{
		swal({
            title: "Record With Receipt No: "+receiptno+" Already Cancelled",
            text: "",
            confirmButtonColor: "#2196F3",
        });
		return false;
	}
	$('#receiptnonc').val();
	location.reload();
}


function clearField(){
	
	window.location.href="./newConnectionCounter";
}


function convertToUpperCase1(){
	$("#applicationId").val($("#applicationId").val().toUpperCase().trim());
}

function convertToUpperCase2(){
	$("#receiptNumber").val($("#receiptNumber").val().toUpperCase().trim());
}

function convertToUpperCase3(){
	$("#connectionNo").val($("#connectionNo").val().toUpperCase().trim());
}


</script>























<div id="viewNewConnectionCounter">

	<div class="panel panel-flat">

		<div class="form-group">
			<div hidden="true" id="alertDiv"></div>
		</div>
		<div class="panel-body">
			<div class="row">
				<div class="col-md-12">
					<fieldset>
						<legend class="text-semibold">
							
							<i class="icon-reading position-left"></i> <font color="red">New connection/Namechange/Hole Block</font>
							CNo:${counterNo} Counter Name:${counterName} Date:${currDate}  Receipts: <b><font color="red">${datalist[0].cashcount}</font></b>  Cash Amount: <b><font color="red">${datalist[0].cashamount}</font></b>   Cheques: <b><font color="red">${datalist[0].cdcount}</font></b>    Cheque Amount: <font color="red">${datalist[0].cdamount}</font>
						</legend>

						<form:form method="POST" id="newConnectioncounter" 
							modelAttribute="npaymentEntity"
							commandName="npaymentEntity" 
							role="form" class="form-horizontal form-validate-jquery" action="./addNewConnectionPaymentEntity">


							<div class="row">
								 <div class="col-md-2">
									<label>Application Id</label> <input type="text" onchange="getApplicationId()" onkeyup="convertToUpperCase1();"
										name="applicationId" id="applicationId" class="form-control"
										placeholder="Application Id" data-mask="9999999999"></input>
								</div>
								
								<!-- <div class="col-md-2">
									<label>&nbsp;</label>
									<div class="input-group">
										<button class="btn btn-primary" type="button" 
											 id="bapplicationId"
											onclick="return getApplicationId()">Search Application ID</button>
									</div>
							   </div>  -->
							   
							   
							   <div class="col-md-3">
									<label>Enter Receipt No</label> <input type="text"
										name="receiptNumber" id="receiptNumber" class="form-control" onkeyup="convertToUpperCase2();"
										placeholder="Receipt Number"></input>
								</div>
								
								<div class="col-md-1">
									<div class="input-group">
									<label>&nbsp;</label>
										<button class="btn btn-primary" type="button" id="duplicateRecID"
											onclick="return getDuplicateReceipt()">Duplicate Receipt</button>
									</div>
							   </div>
							   
							   
							   
							   
							</div>
							
							
							
							<div class="row">
							
							
							
								<div class="col-md-2">
									<label>Connection No</label> <input type="text"
										class="form-control" name="connectionNo" tabindex="1"
										onkeypress="onKeyPress(event,'towards')"
										onchange="setConsumerData(this.value)" id="connectionNo" onkeyup="convertToUpperCase3();"
										placeholder="Connection No" required="required"></input>
								</div>
								
								<div class="col-md-2">
								<label>Receipt No:</label>
								<form:input type="text" name="receiptNo" id="receiptNo"
									path="receiptNo" readonly="true" class="form-control"
									placeholder="Receipt No"></form:input>
							    </div>
							

								<div class="col-md-2">
									<label>Customer Name</label> <input type="text"
										name="customerName" id="customerName" readonly="readonly"
										class="form-control" placeholder="Customer Name">
								</div>
							
							


								<div class="col-md-2">
									<label>Area No</label> <input type="text" name="areaNo"
										id="areaNo" readonly="readonly" class="form-control"
										placeholder="Area No">
								</div>



								<div class="col-md-2">
									<label>Pipe Size</label> <form:input type="text" name="pipeSize"
										id="pipeSize" readonly="readonly" class="form-control" path="pipe_size"
										placeholder="Pipe Size"></form:input>
								</div>

								<div class="col-md-2">
									<label>Connection Type</label> <input type="text"
										name="conType" id="conType" readonly="readonly"
										class="form-control" placeholder="Connection Type">
								</div>

							</div>

							<div class="row">
								
								<div class="col-md-2" style="display: none;">
									<label>Account Head</label> <select
										data-placeholder="Select Account Head" name="towards"
										id="towards" class="select">
										<!-- <option value="">Select</option>
										<option value="NCT">New Connection Reg</option>
										<option value="NCD">New Connection Deposit</option>
										<option value="MV">Meter Value</option>
										<option value="ADP">Advance Payment</option>
										<option value="THB">Temporary Hole Block</option>
										<option value="NMC">Name Change</option>
										<option value="MISC">Miscellaneous</option>
										<option value="ILCP">Illegal Connection Penalty</option>
										<option value="HC">Hole Change</option> -->
										<option value="MISCELLANEOUS">MISCELLANEOUS</option>
										
									</select>
								</div>
								
								<div class="col-md-2">
									<label>New Connection Reg</label> <input type="text" name="nctap" onkeyup="checkNumericAmount()"
										id="nctap" class="form-control"
										placeholder="Enter the Amount" value="0"></input>
								</div>
								
								<div class="col-md-2">
									<label>New Connection Installation</label> <input type="text" name="nc_inst" onkeyup="checkNumericAmount()"
										id="nc_inst" class="form-control"
										placeholder="Enter the Amount" value="0"></input>
								</div>
								
								<div class="col-md-2">
									<label>New Connection Deposit</label> <input type="text" name="ncdeposit" onkeyup="checkNumericAmount()"
										id="ncdeposit" class="form-control"
										placeholder="Enter the Amount" value="0"></input>
								</div>
								
								<div class="col-md-2">
									<label>Meter Value</label> <input type="text" name="mvalue" onkeyup="checkNumericAmount()"
										id="mvalue" class="form-control"
										placeholder="Enter the Amount" value="0"></input>
								</div>


								<div class="col-md-2">
									<label>Advance Payment</label> <input type="text" name="advance" onkeyup="checkNumericAmount()"
										id="advance" class="form-control"
										placeholder="Enter the Amount" value="0"></input>
								</div>
								
								<div class="col-md-2">
									<label>Temporary Hole Block</label> <input type="text" name="tempholeamt" onkeyup="checkNumericAmount()"
										id="tempholeamt" class="form-control"
										placeholder="Enter the Amount" value="0"></input>
								</div>
								
								<div class="col-md-2">
									<label>Ownership Change</label> <input type="text" name="nameChangeAmt" onkeyup="checkNumericAmount()"
										id="nameChangeAmt" class="form-control"
										placeholder="Enter the Amount" value="0"></input>
								</div>
								
								<div class="col-md-2">
									<label>Miscellaneous</label> <input type="text" name="miscellaneous_cost" onkeyup="checkNumericAmount()"
										id="miscellaneous_cost" class="form-control"
										placeholder="Enter the Amount" value="0"></input>
								</div>
								
								<div class="col-md-2">
									<label>Illegal Connection Penalty</label> <input type="text" name="ilg_con_amt" onkeyup="checkNumericAmount()"
										id="ilg_con_amt" class="form-control"
										placeholder="Enter the Amount" value="0" readonly="readonly"></input>
								</div>
								<div class="col-md-2">
								<label>Other Payment Head</label>
								<form:select name="other_paid_type" id="other_paid_type" class="select" onchange="checkRemarks()" path="other_paid_type">
										<form:option value="">Select</form:option>
										<form:option value="Hole Change">Hole Change</form:option>
										<form:option value="Hole Maintenance">Hole Maintenance</form:option>
										<form:option value="Application Charge">Application Charge</form:option>
										<form:option value="Tender/Sealed Quotation">Tender/Sealed Quotation</form:option>
										<form:option value="Card Charge">Card Charge</form:option>
										<form:option value="Others">Others</form:option>
								</form:select>
								</div>
							   <div class="col-md-2">
								<label>Amount</label>
								<form:input type="text" class="form-control" name="other_paid_amt" onkeyup="checkNumericAmount()"
									id="other_paid_amt" placeholder="Amount" readonly="true" path="other_paid_amt"></form:input>
							   </div>
							   <div class="col-md-2">
									<label style="color: red;font-weight: 800;">TOTAL</label> <input type="text" name="amount"
										id="amount" class="form-control"
										 value="0" readonly="readonly"></input>
								</div>
								
								
								
								</div>
								
							<div class="row">
								
								
								
								<div class="col-md-2">
									<label>Payment Mode:</label>
									<form:select data-placeholder="Payment Mode" name="payMode"
										id="payMode" path="payMode" class="select">
										<form:option value="1">Cash</form:option>
										<form:option value="2">Cheque</form:option>
										<form:option value="3">DD</form:option>
									</form:select>
								</div>

							
								<div class="col-md-2">
								<label>Bank Name</label>
								<form:input type="text" class="form-control" name="bankname"
									id="bankname" path="bankname" placeholder="Bank Name"></form:input>
							   </div>				

							<div class="col-md-2">
								<label>Cheque/DD No</label>
								<form:input type="text" class="form-control" name="cdno"
									id="cdno" path="cdno" placeholder="Cheq/DD No"></form:input>
							</div>
								

							<div class="col-md-2">
								<label>Cheq/DD Date Nepali:</label>
								<div class="input-group">
									<span class="input-group-addon"><i
										class="icon-calendar3"></i></span>
									<form:input name="nepali_date" path="nepali_date"
										placeholder="Cheq/DD Date Nepali" id="nepali_date"
										class="form-control nepali-calendar" />
								</div>
							</div>
							
							<div class="col-md-1">
									<label>&nbsp;</label>
								<div class="input-group">
									<button class="btn btn-primary" type="button"  
									id="clearbutton" onclick="return clearField()">Clear</button>
								</div>
							</div>	
									
									
							<div class="col-md-1">
									<label>&nbsp;</label>
								<div class="input-group">
									<button class="btn btn-primary" type="submit"  id="submitId1"
										onclick="return validateField()">Submit</button>
								</div>
							</div>
							
							
							</div>

						</form:form>
					</fieldset>


				</div>
				<br>
			</div>
		</div>


	</div>
</div>




<!--Cancel Receipt  -->


					<div class="panel panel-flat" hidden="true">
						<div class="panel-body">
						<div class="row" hidden="true" id="alertDiv">
								    </div>
								    
							<fieldset class="content-group"> 
								<legend class="text-bold" style="text-align: center;font-size: 18px;">Cancel Receipt</legend>
										
										<div class="row">
											<div class="col-md-4">
												<div class="form-group">
													<label>Receipt No</label>
					                                <input id="receiptnonc" name="receiptnonc" type="text" class="form-control" placeholder="Enter Receipt No" />
				                                </div>
											</div>
											
											<div class="col-md-1">
												<div class="form-group">
													<label>&nbsp;</label>
													<br>&nbsp;&nbsp;&nbsp;&nbsp;<button type="button" class="btn bg-blue"  data-style="expand-right" data-spinner-size="20" onclick="searchObsDetails();"><span class="ladda-label">Search</span></button>
				                                </div>
											</div>
											
											<div class="col-md-7">
												<div class="form-group">
												    <label>&nbsp;</label>
													<br><label><font color="red">Note : Cancel Receipt Option is not available for New Connection Tap Application Charge</font></label>
													
													</div>
													</div>
													
								</div>
							</fieldset>
							
							
							<fieldset>
							<div id="observnDetails">
							<br> <legend class="text-bold" >Receipt Details</legend>
								<div class="row">
									<div class="table-responsive">
										<div class="col-md-16">
										<div class="panel-body" style="margin-top: -3px; padding: 6px;" >
										<table class="table datatable-basic table-bordered" id="tableForm">
											<thead>
					
												<tr class="bg-blue" width="100%">
													<th width="14%">Connection No</th>
													<th width="18%">Consumer&nbsp;Name</th>
													<th width="18%">Receipt No</th>
													<th width="14%">Receipt Date</th>
													<th width="14%">Total Paid</th>
													<th style="min-width: 180px;">Towards</th>
													<th width="18%">Pay Mode</th>
													<th width="18%">Action</th>
												</tr>
					
											</thead>
											<tbody id="tableid123">
												 
											</tbody>
										</table>
					
									</div>
					
									</div>
									</div> 
								</div>
								</div> 
							
							
							</fieldset>
				</div>
		</div>
		
		
		
		
		

<style>
legend {
	text-transform: none;
	font-size: 16px;
	border-color: #F01818;
}

.row {
	margin-left: 0px;
	margin-right: -10px;
}

.input-xs {
	padding: 1px 15px;
}


.modal.fade .modal-dialog {
width: 1260px;
}

</style>

