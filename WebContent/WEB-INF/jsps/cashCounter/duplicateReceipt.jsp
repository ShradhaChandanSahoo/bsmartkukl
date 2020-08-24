<%@include file="/WEB-INF/decorators/taglibs.jsp"%>



<div class="panel panel-flat">
						<div class="panel-body">
						<div class="row" hidden="true" id="alertDiv">
								    </div>
								    
							<fieldset class="content-group"> 
								<legend class="text-bold" style="text-align: center;font-size: 18px;">Duplicate Receipt</legend>
										
										<div class="row">
											<div class="col-md-4">
												<div class="form-group">
													<label>Receipt No</label>
					                                <input id="receiptno" name="receiptno" type="text" class="form-control" placeholder="Enter Receipt No" onkeyup="convertToUpperCase();"/>
				                                </div>
											</div>
											
											<div class="col-md-4">
												<div class="form-group">
													<label>&nbsp;</label>
													<br>&nbsp;&nbsp;&nbsp;&nbsp;<button type="button" class="btn bg-blue"  data-style="expand-right" data-spinner-size="20" onclick="searchObsDetails();"><span class="ladda-label">Print Receipt</span></button>
				                                </div>
											</div>
								</div>
							</fieldset>
							
				</div>
</div>



<c:if test = "${branchcode ne '1115'}"> 
<div class="panel panel-flat">
						<div class="panel-body">
						<div class="row" hidden="true" id="alertDiv">
								    </div>
								    
							<fieldset class="content-group"> 
								<legend class="text-bold" style="text-align: center;font-size: 18px;">Board Duplicate Receipt</legend>
										
										<div class="row">
											<div class="col-md-4">
												<div class="form-group">
													<label>Receipt No</label>
					                                <input id="receiptnob" name="receiptno" type="text" class="form-control" placeholder="Enter Receipt No" />
				                                </div>
											</div>
											
											<div class="col-md-4">
												<div class="form-group">
													<label>&nbsp;</label>
													<br>&nbsp;&nbsp;&nbsp;&nbsp;<button type="button" class="btn bg-blue"  data-style="expand-right" data-spinner-size="20" onclick="searchObsDetailsB();"><span class="ladda-label">Print Board Receipt</span></button>
				                                </div>
											</div>
								</div>
							</fieldset>
							
				</div>
</div>
		
</c:if>		

		
<script>

function convertToUpperCase(){
	$("#receiptno").val($("#receiptno").val().toUpperCase().trim());
}

function searchObsDetailsB(){
	
	   var receiptno=$('#receiptnob').val();
	
	  if(receiptno=="" || receiptno==null){
		  alert("Please Enter Receipt No");
		  return false;
	  
	  }
		 
		$.ajax({
				  type: "GET",
				  url: "./searchReceiptNoBoard/"+receiptno,
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
						   
						  
						  	if(response[2]==null){
						  		alert("This Receipt No is not in Board Installments Please try in KUKL Receipt Duplicate Print");
						  		return false;
						  	}
						  
						    var nepdate=response[3];
						    var connection_no=response[2][0];
				            var customer_id=response[2][7]==null?"":response[2][7];
				            var paid_amount=response[2][1];
				            var install_amount=response[2][2];
				            
				            //var penalty=response[2][3];
				            var receiptNo=response[1].receiptNo;
				            var penal=response[1].penalty;
				            var penaltyadj=response[1].penalty_adj_amount;
				            var penalty=parseFloat(parseFloat(penal==null?0:penal)+parseFloat(penaltyadj==null?0:penaltyadj)).toFixed(2);
				            
				            var chequeno=response[1].cdno==null?"":response[1].cdno;
				            var branch="${CBranchName}";
				            var type=response[1].payMode;
				            var cheqDDcash="";
				            if(type==2){
				            	cheqDDcash="Cheque";
				            }else if(type==3){
				            	cheqDDcash="DD";
				            }else{
				            	cheqDDcash="Cash";
				            }
				            
				            var amount=response[1].amount;
				            var rem_balance=response[2][4];
				            var name_eng=response[2][5];
				            
				            if(name_eng=="" || name_eng==null){
				            	
				            }else{
				            	name_eng=name_eng.charAt(0).toUpperCase()+ name_eng.toLowerCase().slice(1);
				            }
				            var area_no=response[2][6];
				            var install_due_date=response[2][8];
				            var userOfficialName="${userOfficialName}";
				            var counterNo="${peEntity.counterno}";
				            var username="${counterUserName}";

				            var printW = window.open("");
				            
				            var is_chrome = Boolean(printW.chrome);
							  var isPrinting = false;
							  
						    
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
							   +"  <th colspan='2' align='center'><u>BOARD PAYMENT DUPLICATE-RECEIPT</u></th>"
							   +"  </tr>"
							   
							   +"  <tr>"
							   +"    <td>Receipt No.<br></td>"
							   +"    <td>:&nbsp;&nbsp;<b>"+receiptNo+"</b></td>"
							   +"  </tr>"
							   
							   +"  <tr>"
							   +"    <td>Date.<br></td>"
							   +"    <td>:&nbsp;&nbsp;<b>"+nepdate+"</b></td>"
							   +"  </tr>"
							   
							   
							   +"  <tr>"
							   +"    <td>Customer ID<br></td>"
							   +"    <td>:&nbsp;&nbsp;<b>"+customer_id+"</b></td>"
							   +"  </tr>"
							   
							   +"    <td>Connection No.<br></td>"
							   +"    <td>:&nbsp;&nbsp;<b>"+connection_no+"</b></td>"
							   +"  </tr>"
							   
							   +"  <tr>"
							   +"    <td><b>Area No.</b><br></td>"
							   +"    <td>:&nbsp;&nbsp;"+area_no+"</td>"
							   +"  </tr>"
							   
							   +"  <tr>"
							   +"    <td>Name<br></td>"
							   +"    <td>:&nbsp;&nbsp;"+name_eng+"</td>"
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
							   +"    <td>Installment Amount<br></td>"
							   +"    <td>:&nbsp;&nbsp;<b>"+install_amount+"</b></td>"
							   +"  </tr>"
							   
							  
							   +"  <tr>"
							   +"    <td>Penalty<br></td>"
							   +"    <td>:&nbsp;&nbsp;<b>"+penalty+"</b></td>"
							   +"  </tr>"
							   
							   +"  <tr>"
							   +"    <td>Total Paid<br></td>"
							   +"    <td>:&nbsp;&nbsp;<u><b>"+paid_amount+"</b></u></td>"
							   +"  </tr>"
							   
							   +"  <tr>"
							   +"    <td>Remaining Balance<br></td>"
							   +"    <td>:&nbsp;&nbsp;<b>"+rem_balance+"</b></td>"
							   +"  </tr>"
							   
							   +"   <tr>"
							   +"    <td>Due Date<br></td>"
							   +"    <td>:&nbsp;&nbsp;"+install_due_date+"</td>"
							   +"  </tr>"
							   
							   +"  <tr>"
							   +"    <td>Received By<br></td>"
							   +"    <td>:&nbsp;&nbsp;("+userOfficialName+" - Counter No. :"+counterNo+")</td>"
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
						    /* printW.document.close();
						    printW.focus();
						    printW.print();
						    printW.close(); */
						   
						    if (is_chrome) {
						    	printW.onload = function() { // wait until all resources loaded 
						            isPrinting = true;
						            printW.focus(); // necessary for IE >= 10
						            printW.print();  // change window to mywindow
						            printW.close();// change window to mywindow
						            isPrinting = false;
						        };
						        setTimeout(function () { if(!isPrinting){printW.print();printW.close();} }, 300);
						    }
						    else {
						    	printW.document.close(); // necessary for IE >= 10
						    	printW.focus(); // necessary for IE >= 10
						    	printW.print();
						    	printW.close();
						    }

						    return true;

					  }
				  }
		});
		

	
}

function searchObsDetails()
{
	  if($('#receiptno').val().trim()=='')
	{
	$('#alertDiv').show();
      $('#alertDiv').html('<div class="alert alert-danger alert-bordered"><button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button><span class="text" id="alert">Please enter Receipt No</span>');
      $("#alertDiv").fadeOut(5000);
      return false;
	}  
	 
	var receiptno=$('#receiptno').val();
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
				            title: "Entered Receipt Number is not available",
				            text: "",
				            confirmButtonColor: "#2196F3",
				        });
						return false;
				  	}
				  else{
					  	var towards=response[1].towards;
					  	alert(towards);
					  
					  	if(towards=='MISCELLANEOUS' ||towards=='BOARD PAYMENT'){
					  		swal({
					            title: "Entered Receipt Number is of "+towards+".",
					            text: "Only Bill Payment Receipt Can be printed.",
					            confirmButtonColor: "#2196F3",
					        });
							return false;
					  	} else{
					  		 var receiptno=response[1].receiptNo;
					            var counterNo=response[1].counterno;
					            var branch="${CBranchName}";
					            
					           
					            
					            
					            var userOfficialName=response[1].user_id;
					            var nepalidate=response[3];
					            var str=response[2].name_eng;
					            var consumerName=str.charAt(0).toUpperCase()+ str.toLowerCase().slice(1); 
					            if(consumerName=="" || consumerName==null){
					            	consumerName=response[2].name_nep;
					            }
					            var consumeraddress=response[2].address_nep;
					            if(consumeraddress=="" || consumeraddress==null){
					            	consumeraddress=response[2].address_nep;
					            }
					            var connection_no=response[2].connection_no;
					            var customer_id=response[2].customer_id;
					            
					            var areano=response[2].area_no;
					            var type=response[1].payMode;
					            var cheqDDcash="";
					            if(type==2){
					            	cheqDDcash="Cheque";
					            }else if(type==3){
					            	cheqDDcash="DD";
					            }else{
					            	cheqDDcash="Cash";
					            }
					            
					            var chequeno=response[1].cdno;
					            
					            var arrears=response[1].balance_amount;

					            var penal=response[1].penalty;
					            var penaltyadj=response[1].penalty_adj_amount;
					            var penalty=parseFloat(parseFloat(penal==null?0:penal)+parseFloat(penaltyadj==null?0:penaltyadj)).toFixed(2);
					            
					            var rebate=response[1].rebate;
					            var frecamount=response[1].frecamount;
					            
					            var advance_rebate=response[1].advance_rebate;
					            var from_mon_year1=""+response[1].from_mon_year;
					            var old_balance=response[1].old_balance;
					            var miscellaneous_cost=response[1].miscellaneous_cost;

					            
					            var advanceamt=response[1].advance;
					            var amount=response[1].amount;
					            
					            var wc=response[1].water_charges;
					            var sc=response[1].sw_charges;
					            var mtr_rent=response[1].mtr_rent;
					            var bill_amount=response[1].bill_amount;
					            var username="${counterUserName}";
					            
					            var nepaliMonthYear=response[1].to_mon_year;
					            var rembalance=response[1].rbalance;
					            
					            var lastpaiduptomonth="";
					            var str="";
					            str= ""+nepaliMonthYear;
					            var yr = str.substring(0, 4);
				    	        var mnth = str.substring(4, 6);
				    		
				    	    	if(mnth=="01"){
				    	    		lastpaiduptomonth=yr+" Baisakh";
				    	    	}else if(mnth=="02"){
				    	    		lastpaiduptomonth=yr+" Jestha";
				    	    	}else if(mnth=="03"){
				    	    		lastpaiduptomonth=yr+" Asadh";
				    	    	}else if(mnth=="04"){
				    	    		lastpaiduptomonth=yr+" Shrawan";
				    	    	}else if(mnth=="05"){
				    	    		lastpaiduptomonth=yr+" Bhadra";
				    	    	}else if(mnth=="06"){
				    	    		lastpaiduptomonth=yr+" Ashwin";
				    	    	}else if(mnth=="07"){
				    	    		lastpaiduptomonth=yr+" Kartik";
				    	    	}else if(mnth=="08"){
				    	    		lastpaiduptomonth=yr+" Mangshir";
				    	    	}else if(mnth=="09"){
				    	    		lastpaiduptomonth=yr+" Poush";
				    	    	}else if(mnth=="10"){
				    	    		lastpaiduptomonth=yr+" Magh";
				    	    	}else if(mnth=="11"){
				    	    		lastpaiduptomonth=yr+" Falgun";
				    	    	}else if(mnth=="12"){
				    	    		lastpaiduptomonth=yr+" Chaitra";
				    	    	}
				    	    	
				    	    	var from_mon_year=""; 
				    	    	
				    	    	if(from_mon_year1!=null && from_mon_year1!='' && from_mon_year1!='null'){
					        
										var yr1 = from_mon_year1.substring(0, 4);
										var mnth1 = from_mon_year1.substring(4, 6);

										if (mnth1 == "01") {
											from_mon_year = yr1 + " Baisakh";
										} else if (mnth1 == "02") {
											from_mon_year = yr1 + " Jestha";
										} else if (mnth1 == "03") {
											from_mon_year = yr1 + " Asadh";
										} else if (mnth1 == "04") {
											from_mon_year = yr1 + " Shrawan";
										} else if (mnth1 == "05") {
											from_mon_year = yr1 + " Bhadra";
										} else if (mnth1 == "06") {
											from_mon_year = yr1 + " Ashwin";
										} else if (mnth1 == "07") {
											from_mon_year = yr1 + " Kartik";
										} else if (mnth1 == "08") {
											from_mon_year = yr1 + " Mangshir";
										} else if (mnth1 == "09") {
											from_mon_year = yr1 + " Poush";
										} else if (mnth1 == "10") {
											from_mon_year = yr1 + " Magh";
										} else if (mnth1 == "11") {
											from_mon_year = yr1 + " Falgun";
										} else if (mnth1 == "12") {
											from_mon_year = yr1 + " Chaitra";
										}

									}

									var printW = window.open("");
									
									var is_chrome = Boolean(printW.chrome);
									var isPrinting = false;
									
									var html = "<div>";

									html += "  <table style='undefined;table-layout: fixed; width: 325px;font-size:11pt;line-height:16px;'>"

											+ " <colgroup>"
											+ " <col style='width: 145px'>"
											+ " <col style='width: 185px'>"
											+ "</colgroup>"

											+ "  <tr>"
											+ "  <th><br>Kathmandu Upatyaka Khanepani Limited<br>"
											+ " <br>Branch:"
											+ branch
											+ " <br>PAN NO. : 600041601</th>"
											+ "    <th><img src='./resources/images/kukl_new.png' width='130px' height='130px' alt='' align='right'></th>"
											+ "  </tr>"

											+ "  <tr>"
											+ "    <th colspan='2'><hr style='height: 2px; background-color: black;'></th>"
											+ "  </tr>"
											
											+ "  <tr>"

											+ "  <tr>"
											+ "  <th colspan='2' align='center'><u>PAYMENT DUPLICATE-RECEIPT</u></th>"
											+ "  </tr>"

											+ "  <tr>"
											+ "    <td>Receipt No.<br></td>"
											+ "    <td>:&nbsp;&nbsp;<b>"
											+ receiptno
											+ "</b></td>"
											+ "  </tr>"

											+ "  <tr>"
											+ "    <td>Date<br></td>"
											+ "    <td>:&nbsp;&nbsp;<b>"
											+ nepalidate
											+ "</b></td>"
											+ "  </tr>"

											+ "  <tr>"
											+ "    <td>Customer ID<br></td>"
											+ "    <td>:&nbsp;&nbsp;<b>"
											+ customer_id
											+ "</b></td>"
											+ "  </tr>"

											+ "    <td>Connection No<br></td>"
											+ "    <td>:&nbsp;&nbsp;<b>"
											+ connection_no
											+ "</b></td>"
											+ "  </tr>"

											+ "  <tr>"
											+ "    <td><b>Area No.</b><br></td>"
											+ "    <td>:&nbsp;&nbsp;"
											+ areano
											+ "</td>"
											+ "  </tr>"

											+ "  <tr>"
											+ "    <td>Name<br></td>"
											+ "    <td>:&nbsp;&nbsp;"
											+ consumerName
											+ "</td>"
											+ "  </tr>"

											+ "  <tr>"
											+ "    <td>Payment Mode<br></td>"
											+ "    <td>:&nbsp;&nbsp;"
											+ cheqDDcash
											+ "</td>"
											+ "  </tr>"

											+ "  <tr>"
											+ "    <td>Cheque No<br></td>"
											+ "    <td>:&nbsp;&nbsp;<b>"
											+ (chequeno == null ? "" : chequeno)
											+ "</b></td>"
											+ "  </tr>"

											+ "  <tr>"
											+ "    <td>Arrears<br></td>"
											+ "    <td>:&nbsp;&nbsp;<b>"
											+ (arrears == null ? parseFloat(0).toFixed(
													2) : parseFloat(arrears).toFixed(2))
											+ "</b></td>"
											+ "  </tr>"

											+ "  <tr>"
											+ "    <td>Water Charges<br></td>"
											+ "    <td>:&nbsp;&nbsp;"
											+ (wc == null ? parseFloat(0).toFixed(2)
													: parseFloat(wc).toFixed(2))
											+ "</td>"
											+ "  </tr>"

											+ "  <tr>"
											+ "    <td>Sewerage Charges<br></td>"
											+ "    <td>:&nbsp;&nbsp;"
											+ (sc == null ? parseFloat(0).toFixed(2)
													: parseFloat(sc).toFixed(2))
											+ "</td>"
											+ "  </tr>"

											+ "   <tr>"
											+ "    <td>Meter Rent Charges<br></td>"
											+ "    <td>:&nbsp;&nbsp;"
											+ (mtr_rent == null ? parseFloat(0)
													.toFixed(2) : parseFloat(mtr_rent)
													.toFixed(2))
											+ "</td>"
											+ "  </tr>"

											+ "  <tr>"
											+ "    <td>Miscellaneous<br></td>"
											+ "    <td>:&nbsp;&nbsp;"
											+ (miscellaneous_cost == null ? parseFloat(
													0).toFixed(2) : parseFloat(
													miscellaneous_cost).toFixed(2))
											+ "</td>"
											+ "  </tr>"

											+ "  <tr>"
											+ "    <td>Penalty<br></td>"
											+ "    <td>:&nbsp;&nbsp;"
											+ (penalty == null ? parseFloat(0).toFixed(
													2) : parseFloat(penalty).toFixed(2))
											+ "</td>"
											+ "  </tr>"

											+ "  <tr>"
											+ "    <td>Rebate<br></td>"
											+ "    <td>:&nbsp;&nbsp;"
											+ (rebate == null ? parseFloat(0)
													.toFixed(2) : parseFloat(rebate)
													.toFixed(2))
											+ "</td>"
											+ "  </tr>"

											+ "  <tr>"
											+ "    <td>Previous Balance<br></td>"
											+ "    <td>:&nbsp;&nbsp;"
											+ parseFloat(old_balance).toFixed(2)
											+ "</td>"
											+ "  </tr>"

											+ "  <tr>"
											+ "    <td>Bill Amount<br></td>"
											+ "    <td>:&nbsp;&nbsp;"
											+ (bill_amount == null ? parseFloat(0)
													.toFixed(2) : parseFloat(
													bill_amount).toFixed(2))
											+ "</td>"
											+ "  </tr>"

											+ "  <tr>"
											+ "    <td>Total To Be Paid<br></td>"
											+ "    <td>:&nbsp;&nbsp;"
											+ frecamount
											+ "</td>"
											+ "  </tr>"

											+ "  <tr>"
											+ "    <td>Total Amt Paid Rs.<br></td>"
											+ "    <td>:&nbsp;&nbsp;<b><u>"
											+ (amount == null ? parseFloat(0)
													.toFixed(2) : parseFloat(amount)
													.toFixed(2))
											+ "</u></b></td>"
											+ "  </tr>"

											+ "  <tr>"
											+ "    <td>Payment Period<br></td>"
											+ "    <td>:&nbsp;&nbsp;"
											+ from_mon_year+" - "+lastpaiduptomonth
											+ "</td>"
											+ "  </tr>"

											/* + "  <tr>"
											+ "    <td>Last Piad UPTO:<br></td>"
											+ "    <td><b>"
											+ lastpaiduptomonth
											+ "</b></td>"
											+ "  </tr>" */

											+ "  <tr>"
											+ "    <td>Advance<br></td>"
											+ "    <td>:&nbsp;&nbsp;"
											+ (advanceamt == null ? parseFloat(0)
													.toFixed(2)
													: parseFloat(advanceamt).toFixed(2))
											+ "</td>"
											+ "  </tr>"

											+ "  <tr>"
											+ "    <td>Advance Rebate<br></td>"
											+ "    <td>:&nbsp;&nbsp;"
											+ parseFloat(advance_rebate).toFixed(2)
											+ "</td>"
											+ "  </tr>"

											+ "  <tr>"
											+ "    <td>Balance(After Paid)<br></td>"
											+ "    <td>:&nbsp;&nbsp;<b>"
											+ rembalance
											+ "</b></td>"
											+ "  </tr>"

											+ "  <tr>"
											+ "    <td>Received By<br></td>"
											+ "    <td>:&nbsp;&nbsp;("
											/* + userOfficialName
											+ "-" */
											+ userOfficialName
											+ " - "
											+ counterNo
											+ ")</td>"
											+ "  </tr>"

											+ "  <tr>"
											+ "    <td colspan='2'><center>*****Computer Generated Receipt*****</center></td>"
											+ "  </tr>"
											+ "  <tr>"
											+ "    <td>&nbsp;</td>"
											+ "    <td>&nbsp;</td>"
											+ "  </tr>"
											+ "  <tr>"
											+ "    <td>&nbsp;</td>"
											+ "    <td><center>.</center></td>"
											+ "  </tr>" + "</table><br><br><br>";

									html += "</div>";

									printW.document.write(html);
									/* printW.document.close();
									printW.focus();
									printW.print();
									printW.close(); */ 
									if (is_chrome) {
								    printW.onload = function() { // wait until all resources loaded 
								            isPrinting = true;
								            printW.focus(); // necessary for IE >= 10
								            printW.print();  // change window to mywindow
								            printW.close();// change window to mywindow
								            isPrinting = false;
								        };
								        setTimeout(function () { if(!isPrinting){printW.print();printW.close();} }, 300);
								    }
								    else {
								    	printW.document.close(); // necessary for IE >= 10
								    	printW.focus(); // necessary for IE >= 10
								    	printW.print();
								    	printW.close();
								    }

								    return true;
					  	}

						}
					}
				});
	}
</script>	



<style>

tr {display:inline-block; }
</style>				