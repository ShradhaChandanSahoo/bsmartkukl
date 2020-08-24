<%@include file="/WEB-INF/decorators/taglibs.jsp"%>	

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
 
<script type="text/javascript">

$(document).ready(function(){   
	$('#billingEntryTempScreen').show();
	$('#billingEntrytemp').addClass('active');
	
	var activeMod="${activeModulesNew}";
	var module="Customer Billing Entry";
	var check=activeMod.indexOf(module) > -1;
	
	if(check){
	}else{
		window.location.href="./accessDenied";
	}
	$("#connection_no").focus();
});


</script>

<style>
.form-horizontal .form-group{
	margin-left: 0px !important;
	margin-right: 0px !important;
	
}
select{
width: 100%; border: 1px solid #DDD; border-radius: 3px; height: 36px;
}
legend {
	text-transform: none;
	font-size: 16px;
	border-color: skyblue;
}

.col-md-3 {
    width: 20%;
}

.col-md-4 {
    width: 16.6667%;
}
.col-md-2 {
    width: 13.6667%;
}

.datatable-header, .datatable-footer {
    padding: 8px 0px 0 0px;
}
</style>

<script>
var swc=null;
function consumerDetails()
{
	var connNo=$("#connection_no").val();
	
	if(connNo==null || connNo==""){
		alert("Please enter Connection Number");
		return false;
	}else{
	
	$.ajax({
		  type: "GET",
		  url: "./consumer/connectionDetails/"+connNo,
		  dataType: "json",
		  cache       : false,
		  async       : false,
		  success: function(response)
		  {
		  	
			  var response1=response[0];
			  var response2=response[1];
			  
			  
			if(response1!=null)
		  	{
			  for(var i=0;i<response1.length;i++)
			  {
				  data=response1[i];
				  $('#name_eng').val(data.name_eng);
				  $('#address_eng').val(data.address_eng);
				  $('#area_no').val(data.area_no);
				  $('#ward_no').val(data.ward_no);
				  $('#meterHired').val(data.meterHired);
				  $('#sewage_used').val(data.sewage_used);
				  $('#con_type').val(data.con_type);
				  $('#pipe_size').val(data.pipe_size);
				  $('#balance').val(data.balance);
				  
				  swc=data.sewage_used;
			  }
		  	}
		  	if(response1=='')
		  	{
		  		swal({
		            title: "Consumer No : "+connNo+" Does't Exist",
		            text: "",
		            confirmButtonColor: "#2196F3",
		        });
		  		  $('#connection_no').val('');
		  		  $('#name_eng').val('');
				  $('#address_eng').val('');
				  $('#area_no').val('');
				  $('#ward_no').val('');
				return false;
		  	}
		  	
		  	if(response2!=null)
		  	{
				  data=response2[0];
				  $('#aub').val(data.water_charges);
			  
		  	}
		  		
	  	}
	});
	
	var abc="";
	
	$.ajax({
		  type: "GET",
		  url: "./billing/checkLedgerReceiptExists/"+connNo,
		  dataType: "text",
		  async   : false,
		  success: function(response){
		
			  if(response=="Yes"){
				  
				  abc="Payment already done for this Connection Number Now we can't update the Ledger";
				  swal({
		                title: abc,
		                text: "",
		                confirmButtonColor: "#2196F3",
		            });
				  
				  $('#connection_no').val("");
				  $('#name_eng').val("");
			  }else{
				  
			  }
			  
		  }
		});
	
	
	
	if(abc==""){

	$.ajax({
		  type: "GET",
		  url: "./billing/checkLedgerLatestExists/"+connNo,
		  dataType: "text",
		  async   : false,
		  success: function(response){
		
			  if(response=="Yes"){
				  
				  var abc="Ledger already generated if you want to change you can change if Payment is not done";
				  alert(abc);
				  
			  }else{
				  
			  }
			  
		  }
		});
	
	
	}
	
	
	
	
	
	}
	



}

function getCalValues()
{
	
	
	//newly added
	
	var aubarr=$('#aub').val();
	var bhaarr=$('#bhaarr').val();
	var asharr=$('#asharr').val();
	var kararr=$('#kararr').val();
	var mangarr=$('#mangarr').val();
	var pouarr=$('#pouarr').val();
	var magarr=$('#magarr').val();
	var magarrf=$('#magarrf').val();
	var magarrfc=$('#magarrfc').val();
	var magarrfcb=$('#magarrfcb').val();
	
	
	var balance=$('#balance').val();
	
	if(balance==null || balance==""){
		$('#balance').val(0);
	}
	
	
	
	if(aubarr==null || aubarr==""){
		$('#aub').val(0);
	}else if(bhaarr==null || bhaarr==""){
		$('#bhaarr').val(0);
	}else if(asharr==null || asharr==""){
		$('#asharr').val(0);
	}else if(kararr==null || kararr=="" ){
		$('#kararr').val(0);
	}else if(mangarr==null || mangarr==""){
		$('#mangarr').val(0);
	}else if(pouarr==null || pouarr==""){
		$('#pouarr').val(0);
	}else if(magarr==null || magarr==""){
		$('#magarr').val(0);
	}else if(magarrf==null || magarrf==""){
		$('#magarrf').val(0);
	}else if(magarrfc==null || magarrfc==""){
		$('#magarrfc').val(0);
	}else if(magarrfcb==null || magarrfcb==""){
		$('#magarrfcb').val(0);
	}else if(parseInt(aubarr)<0 || parseInt(aubarr)>0){
		
		document.getElementById('aub').readOnly = false;
		document.getElementById('bhaarr').readOnly = true;
		document.getElementById('asharr').readOnly = true;
		document.getElementById('kararr').readOnly = true;
		document.getElementById('mangarr').readOnly = true;
		document.getElementById('pouarr').readOnly = true;
		document.getElementById('magarr').readOnly = true;
		document.getElementById('magarrf').readOnly = true;
		document.getElementById('magarrfc').readOnly = true;
		document.getElementById('magarrfcb').readOnly = true;

		
	}
	else if(parseInt(bhaarr)<0  || parseInt(bhaarr)>0){
		document.getElementById('aub').readOnly = true;
		document.getElementById('bhaarr').readOnly = false;
		document.getElementById('asharr').readOnly = true;
		document.getElementById('kararr').readOnly = true;
		document.getElementById('mangarr').readOnly = true;
		document.getElementById('pouarr').readOnly = true;
		document.getElementById('magarr').readOnly = true;
		document.getElementById('magarrf').readOnly = true;
		document.getElementById('magarrfc').readOnly = true;
		document.getElementById('magarrfcb').readOnly = true;

		$('#shr').val(0);
		
		
	}
	else if(parseInt(asharr)<0 || parseInt(asharr)>0){
		document.getElementById('aub').readOnly = true;
		document.getElementById('bhaarr').readOnly = true;
		document.getElementById('asharr').readOnly = false;
		document.getElementById('kararr').readOnly = true;
		document.getElementById('mangarr').readOnly = true;
		document.getElementById('pouarr').readOnly = true;
		document.getElementById('magarr').readOnly = true;
		document.getElementById('magarrf').readOnly = true;
		document.getElementById('magarrfc').readOnly = true;
		document.getElementById('magarrfcb').readOnly = true;

		$('#shr').val(0);
		$('#bha').val(0);
	}
	else if(parseInt(kararr)<0 || parseInt(kararr)>0){
		document.getElementById('aub').readOnly = true;
		document.getElementById('bhaarr').readOnly = true;
		document.getElementById('asharr').readOnly = true;
		document.getElementById('kararr').readOnly = false;
		document.getElementById('mangarr').readOnly = true;
		document.getElementById('pouarr').readOnly = true;
		document.getElementById('magarr').readOnly = true;
		document.getElementById('magarrf').readOnly = true;
		document.getElementById('magarrfc').readOnly = true;
		document.getElementById('magarrfcb').readOnly = true;

		$('#shr').val(0);
		$('#bha').val(0);
		$('#aba').val(0);
		
	}
	else if(parseInt(mangarr)<0 || parseInt(mangarr)>0){
		document.getElementById('aub').readOnly = true;
		document.getElementById('bhaarr').readOnly = true;
		document.getElementById('asharr').readOnly = true;
		document.getElementById('kararr').readOnly = true;
		document.getElementById('mangarr').readOnly = false;
		document.getElementById('pouarr').readOnly = true;
		document.getElementById('magarr').readOnly = true;
		document.getElementById('magarrf').readOnly = true;
		document.getElementById('magarrfc').readOnly = true;
		document.getElementById('magarrfcb').readOnly = true;

		$('#shr').val(0);
		$('#bha').val(0);
		$('#aba').val(0);
		$('#kba').val(0);
	}
	else if(parseInt(pouarr)<0 || parseInt(pouarr)>0){
		document.getElementById('aub').readOnly = true;
		document.getElementById('bhaarr').readOnly = true;
		document.getElementById('asharr').readOnly = true;
		document.getElementById('kararr').readOnly = true;
		document.getElementById('mangarr').readOnly = true;
		document.getElementById('pouarr').readOnly = false;
		document.getElementById('magarr').readOnly = true;
		document.getElementById('magarrf').readOnly = true;
		document.getElementById('magarrfc').readOnly = true;
		document.getElementById('magarrfcb').readOnly = true;
		
		$('#shr').val(0);
		$('#bha').val(0);
		$('#aba').val(0);
		$('#kba').val(0);
		$('#mba').val(0);
		
	}else if(parseInt(magarr)<0 || parseInt(magarr)>0){
		document.getElementById('aub').readOnly = true;
		document.getElementById('bhaarr').readOnly = true;
		document.getElementById('asharr').readOnly = true;
		document.getElementById('kararr').readOnly = true;
		document.getElementById('mangarr').readOnly = true;
		document.getElementById('pouarr').readOnly = true;
		document.getElementById('magarr').readOnly = false;
		document.getElementById('magarrf').readOnly = true;
		document.getElementById('magarrfc').readOnly = true;
		document.getElementById('magarrfcb').readOnly = true;

		
		$('#shr').val(0);
		$('#bha').val(0);
		$('#aba').val(0);
		$('#kba').val(0);
		$('#mba').val(0);
		$('#pba').val(0);
		
	}else if(parseInt(magarrf)<0 || parseInt(magarrf)>0){
		document.getElementById('aub').readOnly = true;
		document.getElementById('bhaarr').readOnly = true;
		document.getElementById('asharr').readOnly = true;
		document.getElementById('kararr').readOnly = true;
		document.getElementById('mangarr').readOnly = true;
		document.getElementById('pouarr').readOnly = true;
		document.getElementById('magarr').readOnly = true;
		document.getElementById('magarrf').readOnly = false;
		document.getElementById('magarrfc').readOnly = true;
		document.getElementById('magarrfcb').readOnly = true;

		
		$('#shr').val(0);
		$('#bha').val(0);
		$('#aba').val(0);
		$('#kba').val(0);
		$('#mba').val(0);
		$('#pba').val(0);
		$('#maga').val(0);
		
	}else if(parseInt(magarrfc)<0 || parseInt(magarrfc)>0){
		
		document.getElementById('aub').readOnly = true;
		document.getElementById('bhaarr').readOnly = true;
		document.getElementById('asharr').readOnly = true;
		document.getElementById('kararr').readOnly = true;
		document.getElementById('mangarr').readOnly = true;
		document.getElementById('pouarr').readOnly = true;
		document.getElementById('magarr').readOnly = true;
		document.getElementById('magarrf').readOnly = true;
		document.getElementById('magarrfc').readOnly = false;
		document.getElementById('magarrfcb').readOnly = true;

		
		$('#shr').val(0);
		$('#bha').val(0);
		$('#aba').val(0);
		$('#kba').val(0);
		$('#mba').val(0);
		$('#pba').val(0);
		$('#maga').val(0);
		$('#magaf').val(0);
		
		
	}else if(parseInt(magarrfcb)<0 || parseInt(magarrfcb)>0){
		document.getElementById('aub').readOnly = true;
		document.getElementById('bhaarr').readOnly = true;
		document.getElementById('asharr').readOnly = true;
		document.getElementById('kararr').readOnly = true;
		document.getElementById('mangarr').readOnly = true;
		document.getElementById('pouarr').readOnly = true;
		document.getElementById('magarr').readOnly = true;
		document.getElementById('magarrf').readOnly = true;
		document.getElementById('magarrfc').readOnly = true;
		document.getElementById('magarrfcb').readOnly = false;

		
		$('#shr').val(0);
		$('#bha').val(0);
		$('#aba').val(0);
		$('#kba').val(0);
		$('#mba').val(0);
		$('#pba').val(0);
		$('#maga').val(0);
		$('#magaf').val(0);
		$('#magafc').val(0);
		
	}else{
		
		document.getElementById('aub').readOnly = false;
		document.getElementById('bhaarr').readOnly = false;
		document.getElementById('asharr').readOnly = false;
		document.getElementById('kararr').readOnly = false;
		document.getElementById('mangarr').readOnly = false;
		document.getElementById('pouarr').readOnly = false;
		document.getElementById('magarr').readOnly = false;
		document.getElementById('magarrf').readOnly = false;
		document.getElementById('magarrfc').readOnly = false;
		document.getElementById('magarrfcb').readOnly = false;

	
		
	}	
	
	//end new code
	
	
	
	
	
	
	
	
	
	var pen=0;
	var arrAmt=$("#aub").val();
	var sw_used=$("#sewage_used").val();
	var mrp=$("#meterHired").val();
	
	if(parseFloat(arrAmt)>0){
	    pen=arrAmt*(50/100);
	}else{
	}
	
	
	var pipesize1=$("#pipe_size").val();
	

	
	
	$('#wtr_charges1').val(0);
	$("#swr_charges1").val(0);
	$("#mtr_rent1").val(0);
	$("#tot_amt1").val(0);
	$('#penalty1').val(pen);
	var reb1=0;
	$('#rebate1').val(reb1);
	
	var sew_charge2=0;
	var sew_charge3=0;
	var sew_charge4=0;
	var sew_charge5=0;
	var sew_charge3s=0;
	var sew_charge1b=0;
	var sew_charge1m=0;
	var sew_charge1mf=0;
	var sew_charge1mfc=0;
	var sew_charge1mfcb=0;
	

	
	var mtr_ren=0;
	var mtr_ren3=0;
	var mtr_ren4=0;
	var mtr_ren5=0;
	var mtr_ren3s=0;
	var mtr_ren1b=0;
	var mtr_ren1m=0;
	var mtr_ren1mf=0;
	var mtr_ren1mfc=0;
	var mtr_ren1mfcb=0;

	
	var shrBillAmt=$("#shr").val();
	$('#wtr_charges2').val(shrBillAmt);
	
	
	if(shrBillAmt>0 && sw_used=="Yes")
	{
		sew_charge3s=shrBillAmt*(50/100);
		$("#swr_charges2").val(sew_charge3s);
	}
	else
	{
		$("#swr_charges2").val(0);
	}
	
	if(shrBillAmt>0 && mrp=="Yes")
	{
		if(pipesize1==0.5){
			mtr_ren3s=5;
		}else if(pipesize1==0.75){
			mtr_ren3s=17;
		}
		else if(pipesize1==1){
			mtr_ren3s=33;				
		}
		else if(pipesize1==1.5){
			mtr_ren3s=67;
		}
		else if(pipesize1==2){
			mtr_ren3s=133;			
		}
		else if(pipesize1==3){
			mtr_ren3s=250;
		}
		else if(pipesize1==4){
			mtr_ren3s=500;
		}
		
		$("#mtr_rent2").val(mtr_ren3s);
	}
	else
	{
		$("#mtr_rent2").val(0);
	}
	
	var us0s=parseFloat(shrBillAmt);
	var us1s=parseFloat($("#swr_charges2").val());
	var us2s=parseFloat($("#mtr_rent2").val());
	var tAmout3s=(us0s+us1s+us2s);
	$("#tot_amt2").val(tAmout3s);
	
	var pen3s=0;
	
	
	if(parseFloat(tAmout3s)>0){
		pen3s=tAmout3s*(50/100);
	}else{
	
	}
	
	$('#penalty2').val(parseFloat(pen3s).toFixed(2));
	
	var reb3s=0;
	$('#rebate2').val(parseFloat(reb3s).toFixed(2));
	
	
	
	
	var bhaBillAmt=$("#bha").val();
	$('#wtr_charges3').val(bhaBillAmt);
	
	
	if(bhaBillAmt>0 && sw_used=="Yes")
	{
		sew_charge1b=bhaBillAmt*(50/100);
		$("#swr_charges3").val(sew_charge1b);
	}
	else
	{
		$("#swr_charges3").val(0);
	}
	
	if(bhaBillAmt>0 && mrp=="Yes")
	{
		
		
		if(pipesize1==0.5){
			mtr_ren1b=5;
		}else if(pipesize1==0.75){
			mtr_ren1b=17;
		}
		else if(pipesize1==1){
			mtr_ren1b=33;				
		}
		else if(pipesize1==1.5){
			mtr_ren1b=67;
		}
		else if(pipesize1==2){
			mtr_ren1b=133;			
		}
		else if(pipesize1==3){
			mtr_ren1b=250;
		}
		else if(pipesize1==4){
			mtr_ren1b=500;
		}
		
		
		$("#mtr_rent3").val(mtr_ren1b);
	}
	else
	{
		$("#mtr_rent3").val(0);
	}
	
	var us0b=parseFloat(bhaBillAmt);
	var us1b=parseFloat($("#swr_charges3").val());
	var us2b=parseFloat($("#mtr_rent3").val());
	var tAmout1b=(us0b+us1b+us2b);
	$("#tot_amt3").val(tAmout1b);
	var pen1b=0;
	if(parseFloat(bhaarr)<0){
		 pen1b=(tAmout1b+parseFloat(bhaarr))*(20/100);
		if(parseFloat(pen1b)>0){
			$('#penalty3').val(parseFloat(pen1b).toFixed(2));
		}
	}else{
		 pen1b=tAmout1b*(20/100);
		$('#penalty3').val(parseFloat(pen1b).toFixed(2));
	}
	
	
	
	var reb1b=0;
	$('#rebate3').val(parseFloat(reb1b).toFixed(2));
	
	
	
	
	var ashBillAmt=$("#aba").val();
	$('#wtr_charges4').val(ashBillAmt);

	
	if(ashBillAmt>0 && sw_used=="Yes")
	{
		sew_charge2=ashBillAmt*(50/100);
		$("#swr_charges4").val(sew_charge2);
	}
	else
	{
		$("#swr_charges4").val(0);
	}
	if(ashBillAmt>0 && mrp=="Yes")
	{
		if(pipesize1==0.5){
			mtr_ren=5;
		}else if(pipesize1==0.75){
			mtr_ren=17;
		}
		else if(pipesize1==1){
			mtr_ren=33;				
		}
		else if(pipesize1==1.5){
			mtr_ren=67;
		}
		else if(pipesize1==2){
			mtr_ren=133;			
		}
		else if(pipesize1==3){
			mtr_ren=250;
		}
		else if(pipesize1==4){
			mtr_ren=500;
		}
		
		$("#mtr_rent4").val(mtr_ren);
	}
	else
	{
		$("#mtr_rent4").val(0);
	}
	
	var us0=parseFloat(ashBillAmt);
	var us1=parseFloat($("#swr_charges4").val());
	var us2=parseFloat($("#mtr_rent4").val());
	var tAmout2=(us0+us1+us2);
	$("#tot_amt4").val(tAmout2);
	var pen2=0;
	if(parseFloat(asharr)<0){
		 pen2=(tAmout2+parseFloat(asharr))*(10/100);
		if(parseFloat(pen2)>0){
			$('#penalty4').val(parseFloat(pen2).toFixed(2));
		}
	}else{
		 pen2=tAmout2*(10/100);
		$('#penalty4').val(parseFloat(pen2).toFixed(2));
	}
	
	
	
	var reb2=0;
	$('#rebate4').val(parseFloat(reb2).toFixed(2));
	
	var karBillAmt=$("#kba").val();
	$('#wtr_charges5').val(karBillAmt);
	
	var sw3=karBillAmt*(50/100);
	$('#swr_charges5').val(sw3);
	
	if(karBillAmt>0 && mrp=="Yes")
	{
		if(pipesize1==0.5){
			mtr_ren3=5;
		}else if(pipesize1==0.75){
			mtr_ren3=17;
		}
		else if(pipesize1==1){
			mtr_ren3=33;				
		}
		else if(pipesize1==1.5){
			mtr_ren3=67;
		}
		else if(pipesize1==2){
			mtr_ren3=133;			
		}
		else if(pipesize1==3){
			mtr_ren3=250;
		}
		else if(pipesize1==4){
			mtr_ren3=500;
		}
		
		$("#mtr_rent5").val(mtr_ren3);
	}
	else
	{
		$("#mtr_rent5").val(0);
	}
	
	if(karBillAmt>0 && sw_used=="Yes")
	{
		sew_charge3=karBillAmt*(50/100);
		$("#swr_charges5").val(sew_charge3);
	}
	else
	{
		$("#swr_charges5").val(0);
	}
	
	var us3=parseFloat(karBillAmt);
	var us4=parseFloat($("#swr_charges5").val());
	var us5=parseFloat($("#mtr_rent5").val());
	var tAmout3=(us3+us4+us5);
	$("#tot_amt5").val(tAmout3);
	
	var pen3=0;
	$('#penalty5').val(parseFloat(pen3).toFixed(2));
	var reb3=0;
	$('#rebate5').val(parseFloat(reb3).toFixed(2));
	
	var manBillAmt=$("#mba").val();
	$('#wtr_charges6').val(manBillAmt);
	
	var sw4=manBillAmt*(50/100);
	$('#swr_charges6').val(sw4);
	
	if(manBillAmt>0 && mrp=="Yes")
	{
		
		
		if(pipesize1==0.5){
			mtr_ren4=5;
		}else if(pipesize1==0.75){
			mtr_ren4=17;
		}
		else if(pipesize1==1){
			mtr_ren4=33;				
		}
		else if(pipesize1==1.5){
			mtr_ren4=67;
		}
		else if(pipesize1==2){
			mtr_ren4=133;			
		}
		else if(pipesize1==3){
			mtr_ren4=250;
		}
		else if(pipesize1==4){
			mtr_ren4=500;
		}
		
		
		$("#mtr_rent6").val(mtr_ren4);
	}
	else
	{
		$("#mtr_rent6").val(0);
	}
	
	if(manBillAmt>0 && sw_used=="Yes")
	{
		sew_charge4=manBillAmt*(50/100);
		$("#swr_charges6").val(sew_charge4);
	}
	else
	{
		$("#swr_charges6").val(0);
	}
	
	var us6=parseFloat(manBillAmt);
	var us7=parseFloat($("#swr_charges6").val());
	var us8=parseFloat($("#mtr_rent6").val());
	var tAmout4=(us6+us7+us8);
	$("#tot_amt6").val(parseFloat(tAmout4).toFixed(2));
	var pen4=0;
	
	if(parseFloat(mangarr)<0){
		 pen4=(tAmout4+parseFloat(mangarr))*(10/100);
		if(parseFloat(pen4)>0){
			$('#penalty6').val(parseFloat(pen4).toFixed(2));
		}
	}else{
		 pen4=tAmout4*(10/100);
		$('#penalty6').val(parseFloat(pen4).toFixed(2));
	}
	
	$('#penalty6').val(parseFloat(pen4).toFixed(2));
	var reb4=0;
	$('#rebate6').val(parseFloat(reb4).toFixed(2));
	
	var pouBillAmt=$("#pba").val();
	$('#wtr_charges7').val(pouBillAmt);
	

	if(pouBillAmt>0 && mrp=="Yes")
	{
		if(pipesize1==0.5){
			mtr_ren5=5;
		}else if(pipesize1==0.75){
			mtr_ren5=17;
		}
		else if(pipesize1==1){
			mtr_ren5=33;				
		}
		else if(pipesize1==1.5){
			mtr_ren5=67;
		}
		else if(pipesize1==2){
			mtr_ren5=133;			
		}
		else if(pipesize1==3){
			mtr_ren5=250;
		}
		else if(pipesize1==4){
			mtr_ren5=500;
		}
		
		
		
		$("#mtr_rent7").val(mtr_ren5);
	}
	else
	{
		$("#mtr_rent7").val(0);
	}
	
	if(pouBillAmt>0 && sw_used=="Yes")
	{
		sew_charge5=pouBillAmt*(50/100);
		$("#swr_charges7").val(sew_charge5);
	}
	else
	{
		$("#swr_charges7").val(0);
	}
	
	
	var us9=parseFloat(pouBillAmt);
	var us10=parseFloat($("#swr_charges7").val());
	var us11=parseFloat($("#mtr_rent7").val());
	var tAmout5=(us9+us10+us11);
	$("#tot_amt7").val(parseFloat(tAmout5).toFixed(2));
	
	var reb5=0;
	var pen5=0;
	$('#penalty7').val(parseFloat(pen5).toFixed(2));
	$('#rebate7').val(parseFloat(reb5).toFixed(2));
	
	
	
	
	
	
	
	
	
	
	
	var magaBillAmt=$("#maga").val();
	$('#wtr_charges8').val(magaBillAmt);
	

	if(magaBillAmt>0 && mrp=="Yes")
	{
		
		
		if(pipesize1==0.5){
			mtr_ren1m=5;
		}else if(pipesize1==0.75){
			mtr_ren1m=17;
		}
		else if(pipesize1==1){
			mtr_ren1m=33;				
		}
		else if(pipesize1==1.5){
			mtr_ren1m=67;
		}
		else if(pipesize1==2){
			mtr_ren1m=133;			
		}
		else if(pipesize1==3){
			mtr_ren1m=250;
		}
		else if(pipesize1==4){
			mtr_ren1m=500;
		}
		
		
		$("#mtr_rent8").val(mtr_ren1m);
	}
	else
	{
		$("#mtr_rent8").val(0);
	}
	
	if(magaBillAmt>0 && sw_used=="Yes")
	{
		sew_charge1m=magaBillAmt*(50/100);
		$("#swr_charges8").val(sew_charge1m);
	}
	else
	{
		$("#swr_charges8").val(0);
	}
	
	
	var us9m=parseFloat(magaBillAmt);
	var us10m=parseFloat($("#swr_charges8").val());
	var us11m=parseFloat($("#mtr_rent8").val());
	var tAmout5m=(us9m+us10m+us11m);
	$("#tot_amt8").val(parseFloat(tAmout5m).toFixed(2));
	
	var reb5m=tAmout5m*(3/100).toFixed(2);
	var pen5m=0;
	$('#penalty8').val(parseFloat(pen5m).toFixed(2));
	$('#rebate8').val(parseFloat(reb5m).toFixed(2));
	
	
	
	
	
	
	
	
	
	
	/* Start Falgun  */
	
	var magaBillAmtf=$("#magaf").val();
	$('#wtr_charges9').val(magaBillAmtf);
	

	if(magaBillAmtf>0 && mrp=="Yes")
	{
		
		
		if(pipesize1==0.5){
			mtr_ren1mf=5;
		}else if(pipesize1==0.75){
			mtr_ren1mf=17;
		}
		else if(pipesize1==1){
			mtr_ren1mf=33;				
		}
		else if(pipesize1==1.5){
			mtr_ren1mf=67;
		}
		else if(pipesize1==2){
			mtr_ren1m=133;			
		}
		else if(pipesize1==3){
			mtr_ren1mf=250;
		}
		else if(pipesize1==4){
			mtr_ren1mf=500;
		}
		
		
		$("#mtr_rent9").val(mtr_ren1mf);
	}
	else
	{
		$("#mtr_rent9").val(0);
	}
	
	if(magaBillAmtf>0 && sw_used=="Yes")
	{
		sew_charge1mf=magaBillAmtf*(50/100);
		$("#swr_charges9").val(sew_charge1mf);
	}
	else
	{
		$("#swr_charges9").val(0);
	}
	
	
	var us9mf=parseFloat(magaBillAmtf);
	var us10mf=parseFloat($("#swr_charges9").val());
	var us11mf=parseFloat($("#mtr_rent9").val());
	var tAmout5mf=(us9mf+us10mf+us11mf);
	$("#tot_amt9").val(parseFloat(tAmout5mf).toFixed(2));
	
	var reb5mf=tAmout5mf*(3/100).toFixed(2);
	var pen5mf=0;
	$('#penalty9').val(parseFloat(pen5mf).toFixed(2));
	$('#rebate9').val(parseFloat(reb5mf).toFixed(2));
	
	
	
	/* End Falgun  */
	
	
	/* Start Chaitra  */
	
	var magaBillAmtfc=$("#magafc").val();
	$('#wtr_charges10').val(magaBillAmtfc);
	

	if(magaBillAmtfc>0 && mrp=="Yes")
	{
		
		
		if(pipesize1==0.5){
			mtr_ren1mfc=5;
		}else if(pipesize1==0.75){
			mtr_ren1mfc=17;
		}
		else if(pipesize1==1){
			mtr_ren1mfc=33;				
		}
		else if(pipesize1==1.5){
			mtr_ren1mfc=67;
		}
		else if(pipesize1==2){
			mtr_ren1mfc=133;			
		}
		else if(pipesize1==3){
			mtr_ren1mfc=250;
		}
		else if(pipesize1==4){
			mtr_ren1mfc=500;
		}
		
		
		$("#mtr_rent10").val(mtr_ren1mfc);
	}
	else
	{
		$("#mtr_rent10").val(0);
	}
	
	if(magaBillAmtfc>0 && sw_used=="Yes")
	{
		sew_charge1mfc=magaBillAmtfc*(50/100);
		$("#swr_charges10").val(sew_charge1mfc);
	}
	else
	{
		$("#swr_charges10").val(0);
	}
	
	
	var us9mfc=parseFloat(magaBillAmtfc);
	var us10mfc=parseFloat($("#swr_charges10").val());
	var us11mfc=parseFloat($("#mtr_rent10").val());
	var tAmout5mfc=(us9mfc+us10mfc+us11mfc);
	$("#tot_amt10").val(parseFloat(tAmout5mfc).toFixed(2));
	
	var reb5mfc=tAmout5mfc*(3/100).toFixed(2);
	var pen5mfc=0;
	$('#penalty10').val(parseFloat(pen5mfc).toFixed(2));
	$('#rebate10').val(parseFloat(reb5mfc).toFixed(2));
	
	
	
	/* End Chaitra  */
	
	
	
	
	/* Start Baisak  */
	
	var magaBillAmtfcb=$("#magafcb").val();
	$('#wtr_charges11').val(magaBillAmtfcb);
	

	if(magaBillAmtfcb>0 && mrp=="Yes")
	{
		
		
		if(pipesize1==0.5){
			mtr_ren1mfcb=5;
		}else if(pipesize1==0.75){
			mtr_ren1mfcb=17;
		}
		else if(pipesize1==1){
			mtr_ren1mfcb=33;				
		}
		else if(pipesize1==1.5){
			mtr_ren1mfcb=67;
		}
		else if(pipesize1==2){
			mtr_ren1mfcb=133;			
		}
		else if(pipesize1==3){
			mtr_ren1mfcb=250;
		}
		else if(pipesize1==4){
			mtr_ren1mfcb=500;
		}
		
		
		$("#mtr_rent11").val(mtr_ren1mfcb);
	}
	else
	{
		$("#mtr_rent11").val(0);
	}
	
	if(magaBillAmtfcb>0 && sw_used=="Yes")
	{
		sew_charge1mfcb=magaBillAmtfcb*(50/100);
		$("#swr_charges11").val(sew_charge1mfcb);
	}
	else
	{
		$("#swr_charges11").val(0);
	}
	
	
	var us9mfcb=parseFloat(magaBillAmtfcb);
	var us10mfcb=parseFloat($("#swr_charges11").val());
	var us11mfcb=parseFloat($("#mtr_rent11").val());
	var tAmout5mfcb=(us9mfcb+us10mfcb+us11mfcb);
	$("#tot_amt11").val(parseFloat(tAmout5mfcb).toFixed(2));
	
	var reb5mfcb=tAmout5mfcb*(3/100).toFixed(2);
	var pen5mfcb=0;
	$('#penalty11').val(parseFloat(pen5mfcb).toFixed(2));
	$('#rebate11').val(parseFloat(reb5mfcb).toFixed(2));
	
	
	
	/* End Baisak  */
	
	
	
	
	
	
	
	var us12=parseFloat($('#wtr_charges1').val());
	var us13=parseFloat(ashBillAmt);
	var us14=parseFloat(karBillAmt);
	var us15=parseFloat(manBillAmt);
	var us16=parseFloat(pouBillAmt);
	var us104=parseFloat(shrBillAmt);
	var us105=parseFloat(bhaBillAmt);
	var us105m=parseFloat(magaBillAmt);
	var us105mf=parseFloat(magaBillAmtf);
	var us105mfc=parseFloat(magaBillAmtfc);
	var us105mfb=parseFloat(magaBillAmtfcb);
	
	var sumOfWC=(us12+us13+us14+us15+us16+us104+us105+us105m+us105mf+us105mfc+us105mfb);
	$('#tot_wc').val(parseFloat(sumOfWC).toFixed(2));
	
	var us17=parseFloat($('#swr_charges1').val());
	var us18=parseFloat(sew_charge2);
	var us19=parseFloat(sew_charge3);
	var us20=parseFloat(sew_charge4);
	var us21=parseFloat(sew_charge5);
	var us102=parseFloat(sew_charge3s);
	var us103=parseFloat(sew_charge1b);
	var us103m=parseFloat(sew_charge1m);
	var us103mf=parseFloat(sew_charge1mf);
	var us103mfc=parseFloat(sew_charge1mfc);
	var us103mfb=parseFloat(sew_charge1mfcb);

	
	var sumOfSC=(us17+us18+us19+us20+us21+us102+us103+us103m+us103mf+us103mfc+us103mfb);
	$('#tot_sc').val(parseFloat(sumOfSC).toFixed(2));
	
	var us22=parseFloat($('#mtr_rent1').val());
	var us23=parseFloat(mtr_ren);
	var us24=parseFloat(mtr_ren3);
	var us25=parseFloat(mtr_ren4);
	var us26=parseFloat(mtr_ren5);
	var us100=parseFloat(mtr_ren3s);
	var us101=parseFloat(mtr_ren1b);
	var us101m=parseFloat(mtr_ren1m);
	var us101mf=parseFloat(mtr_ren1mf);
	var us101mfc=parseFloat(mtr_ren1mfc);
	var us101mfb=parseFloat(mtr_ren1mfcb);
	
	
	var sumOfMrent=(us22+us23+us24+us25+us26+us100+us101+us101m+us101mf+us101mfc+us101mfb);
	$('#tot_mrent').val(parseFloat(sumOfMrent).toFixed(2));
	
	var us27=parseFloat($('#tot_amt1').val());
	var us28=parseFloat(tAmout2);
	var us29=parseFloat(tAmout3);
	var us30=parseFloat(tAmout4);
	var us31=parseFloat(tAmout5);
	var us108=parseFloat(tAmout3s);
	var us109=parseFloat(tAmout1b);
	var us109m=parseFloat(tAmout5m);
	var us109mb=parseFloat(tAmout5mfcb);
	
	var sumOftotAmount=(us27+us28+us29+us30+us31+us108+us109+us109m+us109mb);
	$('#tot_tamt').val(parseFloat(sumOftotAmount).toFixed(2));
	
	var us32=parseFloat($('#penalty1').val());
	var us33=parseFloat(pen2);
	var us34=parseFloat(pen3);
	var us35=parseFloat(pen4);
	var us36=parseFloat(pen5);
	var us106=parseFloat(pen3s);
	var us107=parseFloat(pen1b);
	var us107m=parseFloat(pen5m);
	var us107mf=parseFloat(pen5mf);
	var us107mfb=parseFloat(pen5mfcb);
	
	var sumOfpenalty=(us32+us33+us34+us35+us36+us106+us107+us107m+us107mf+us107mfb);
	$('#tot_pen').val(parseFloat(sumOfpenalty).toFixed(2));
	
	var us37=parseFloat(reb1);
	var us38=parseFloat(reb2);
	var us39=parseFloat(reb3);
	var us40=parseFloat(reb4);
	var us41=parseFloat(reb5);
	var us110=parseFloat(reb3s);
	var us111=parseFloat(reb1b);
	var us111m=parseFloat(reb5m);
	var us111mf=parseFloat(reb5mf);
	var us111mfb=parseFloat(reb5mfcb);
	
	
	
	var sumOfrebate=(us37+us38+us39+us40+us41+us110+us111+us111m+us111mf+us111mfb);
	$('#tot_reb').val(parseFloat(sumOfrebate).toFixed(2));
	
	var us42=parseFloat(arrAmt);
	var totalOfWC=(us42+us13+us14+us15+us16+us104+us105+us105m+us105mf+us111mfb);
	
	var tot_wc=$('#tot_wc').val();
	$('#tot_water_Chrg').val(tot_wc);
	
	var tOfSC=(us17+us18+us19+us20+us21+us102+us103+us103m+us103mf+us103mfb);
	var totalOfSC=tOfSC;
	
	var tot_sc=$('#tot_sc').val();
	$('#tot_sewerage_Chrg').val(tot_sc);
	
	var totalOfMrent=$('#tot_mrent').val();
	$('#tot_Mrent_Chrg').val(totalOfMrent);
	
	var bhaarr=$('#bhaarr').val();
	var asharr=$('#asharr').val();
	var kararr=$('#kararr').val();
	var mangarr=$('#mangarr').val();
	var pouarr=$('#pouarr').val();
	var magarr=$('#magarr').val();
	var magarrf=$('#magarrf').val();
	var magarrfc=$('#magarrfc').val();
	var magarrfcb=$('#magarrfcb').val();
	
	$('#tot_arrears').val(parseFloat(parseFloat(bhaarr)+parseFloat(asharr)+parseFloat(kararr)+parseFloat(mangarr)+parseFloat(pouarr)+parseFloat(magarr)+parseFloat(magarrf)+parseFloat(magarrfc)+parseFloat(magarrfcb)).toFixed(2));
	
	
	var us43=parseFloat(tot_wc);
	var us44=parseFloat(tot_sc);
	var us45=parseFloat(totalOfMrent);
	
	var totarr=parseFloat($('#tot_arrears').val());
	
	var totalOfBillAmount=(us43+us44+us45+totarr);
	$('#tot_bill_amt').val(parseFloat(totalOfBillAmount).toFixed(2));
	
	var totalOfpen=$('#tot_pen').val();
	$('#total_penal').val(parseFloat(totalOfpen).toFixed(2));
	
	var totalOfreb=$('#tot_reb').val();
	$('#total_rebate').val(parseFloat(totalOfreb).toFixed(2));
	
	var us46=parseFloat(totalOfBillAmount);
	var us47=parseFloat(totalOfpen);
	var us48=parseFloat(totalOfreb);
	
	var totalOfPayable=(parseFloat(us46+us47-us48).toFixed(2));
	$('#tot_Payable').val(totalOfPayable);
}

function validCheck(){
	
	var sitecode="${branchcode}";
	
	if(sitecode=='1117'){
		
	}
	
	else if(sitecode=='1119'){
		
	}else{
		
		alert("This Module is not applicable for this branch");
		return false;
	}
	
	
	var magaval=$('#maga').val();
	var connection_no=$('#connection_no').val();
	var name_eng=$('#name_eng').val();
	if(connection_no==null || connection_no==""){
		alert("Please Enter Connection No");
		return false;
	}else if(name_eng==null || name_eng==""){
		alert("Please Click on View Button to get Customer Information");
		return false;
	}/* else if(parseFloat(magaval).toFixed(0)==0){
		alert("Please Enter Magh Month Amount");
		return false;
	} */
	
	if(confirm("Are you sure to Generate the Ledger?")){
	
	}
	else{
	 return false;
	}
	
}



function setValue(number){
	
	var amount=$('#setAmount').val();
	if(amount==null || amount=="" || amount==0){
		alert("Please set the amount first then Select checkbox");
		return false;
	}else{
	
		if(number==0){
			 var x = document.getElementById('id11').checked;
			 if (x) {
				$('#shr').val(amount);
			 }
			 else {
				$('#shr').val(0);
			 }
		}
		
		if(number==1){
			 var x = document.getElementById('id12').checked;
			 if (x) {
				$('#bha').val(amount);
			 }
			 else {
				$('#bha').val(0);
			 }
		}
			
		if(number==2){
			 var x = document.getElementById('id13').checked;
			 if (x) {
				$('#aba').val(amount);
			 }
			 else {
				$('#aba').val(0);
			 }
		}
		
		if(number==3){
			 var x = document.getElementById('id14').checked;
			 if (x) {
				$('#kba').val(amount);
			 }
			 else {
				$('#kba').val(0);
			 }
		}
		
		if(number==4){
			 var x = document.getElementById('id15').checked;
			 if (x) {
				$('#mba').val(amount);
			 }
			 else {
				$('#mba').val(0);
			 }
		}
		
		if(number==5){
			 var x = document.getElementById('id16').checked;
			 if (x) {
				$('#pba').val(amount);
			 }
			 else {
				$('#pba').val(0);
			 }
		}
		if(number==6){
			 var x = document.getElementById('id17').checked;
			 if (x) {
				$('#maga').val(amount);
			 }
			 else {
				$('#maga').val(0);
			 }
		}
		if(number==7){
			 var x = document.getElementById('id18').checked;
			 if (x) {
				$('#magaf').val(amount);
			 }
			 else {
				$('#magaf').val(0);
			 }
		}
		
		if(number==8){
			 var x = document.getElementById('id19').checked;
			 if (x) {
				$('#magafc').val(amount);
			 }
			 else {
				$('#magafc').val(0);
			 }
		}
		
		if(number==9){
			 var x = document.getElementById('id20').checked;
			 if (x) {
				$('#magafcb').val(amount);
			 }
			 else {
				$('#magafcb').val(0);
			 }
		}
	}

}

function clearButton(){
	
	window.location.href="./consumerBillingEntry";

}


function calculateConsumption(){
	 var lr=$('#previous_reading1').val();
	 var cr=$('#present_reading1').val();
	
	if(lr=="" || lr==null){
		alert("Please enter Last Reading");
		return false;
	}else if(cr=="" || cr==null){
		alert("Please enter Present Reading");
		return false;
	}else{
		var lr1=parseInt(lr);
		var cr1=parseInt(cr);
		
		
		
		if(lr1 > cr1){
			alert("Current Reading should be grater than Last Reading");
			$("#present_reading1").val("");
			
			return false;
		}else{
			$("#consumption1").val(cr1-lr1);
		}
		
	}  
	

	
}



function calculateConsumption1(){
	 var lr=$('#previous_reading3').val();
	 var cr=$('#present_reading3').val();
	
	if(lr=="" || lr==null){
		alert("Please enter Last Reading");
		return false;
	}else if(cr=="" || cr==null){
		alert("Please enter Present Reading");
		return false;
	}else{
		var lr1=parseInt(lr);
		var cr1=parseInt(cr);
		
		if(lr1 > cr1){
			alert("Current Reading should be grater than Last Reading");
			$("#present_reading3").val("");
			
			return false;
		}else{
			$("#consumption3").val(cr1-lr1);
		}
		
	}  
	

	
}

function calculateConsumption2(){
	 var lr=$('#previous_reading3m').val();
	 var cr=$('#present_reading3m').val();
	
	if(lr=="" || lr==null){
		alert("Please enter Last Reading");
		return false;
	}else if(cr=="" || cr==null){
		alert("Please enter Present Reading");
		return false;
	}else{
		var lr1=parseInt(lr);
		var cr1=parseInt(cr);
		
		if(lr1 > cr1){
			alert("Current Reading should be grater than Last Reading");
			$("#present_reading3m").val("");
			
			return false;
		}else{
			$("#consumption3m").val(cr1-lr1);
		}
		
	}  
	

	
}


function calculateConsumption2b(){
	 var lr=$('#previous_reading3mb').val();
	 var cr=$('#present_reading3mb').val();
	
	if(lr=="" || lr==null){
		alert("Please enter Last Reading");
		return false;
	}else if(cr=="" || cr==null){
		alert("Please enter Present Reading");
		return false;
	}else{
		var lr1=parseInt(lr);
		var cr1=parseInt(cr);
		
		if(lr1 > cr1){
			alert("Current Reading should be grater than Last Reading");
			$("#present_reading3mb").val("");
			
			return false;
		}else{
			$("#consumption3mb").val(cr1-lr1);
		}
		
	}  
	

	
}



function calculateBill(){
	var connectionNo=$("#connection_no").val();
	
	var pipeSize=$("#pipe_size").val();
	var conType=$("#con_type").val();
	var billperiod=1;
	var previous_reading=$("#previous_reading1").val();
	var present_reading=$("#present_reading1").val();
	var mc_status=$('#mc_status1').val();
	
	
	 if(connectionNo=="" || connectionNo==null){
			alert("Please enter connection number then Calculate");
			return false;
     }else if(pipeSize=="" || pipeSize==null){
			
			alert("Pipe size is not available");
			return false;
     }else if(present_reading=="" || present_reading==null){
			alert("Please enter Present Reading");
			return false;
     }else if(present_reading<previous_reading){
			alert("Present Reading should be greater than or equal to previous Reading");
			return false;
     }else if(mc_status=="" || mc_status==null){
			alert("Please Select Meter Status");
			return false;
  	 }else{
	
     var units=parseInt(present_reading)-parseInt(previous_reading);
	 $.ajax({
		  type: "GET",
		  url: "./billing/calculateBill/"+connectionNo+"/"+pipeSize+"/"+conType+"/"+units+"/"+mc_status,
		  dataType: "JSON",
		  data:{
			  billperiod:billperiod  
			  
		  },
		  async       : false,
		  
		  success: function(response){
		
			       $.each(response, function(index, data){
						
			    	  alert("Amount is Calculated Please Close the Pop Up Window");
			    	  
		    	      $('#wtr_charges8').val(data.waterCharges);
					  $('#swr_charges8').val(data.sewageCharges);
					  $('#mtr_rent8').val(data.meterRent);
					  var wc=data.waterCharges;
					  var sc=data.sewageCharges;
					  var mr=data.meterRent;
					  $('#tot_amt8').val(data.netAmount); 
					  $('#rebate8').val(parseFloat((wc+sc+mr)*0.03).toFixed(2)); 
					  $('#maga').val(data.waterCharges); 
					  
					  $('#previous_reading').val(previous_reading);
					  $('#present_reading').val(present_reading);
					  $('#consumption').val(units);
					  $('#mc_status').val(mc_status);
					  
					  
				 
				 });
			  
			  
			  
		  }
		});
	 
     }
	
}



function calculateBill1(){
	var connectionNo=$("#connection_no").val();
	
	var pipeSize=$("#pipe_size").val();
	var conType=$("#con_type").val();
	var billperiod=1;
	var previous_reading=$("#previous_reading3").val();
	var present_reading=$("#present_reading3").val();
	var mc_status=$('#mc_status3').val();
	
	
	 if(connectionNo=="" || connectionNo==null){
			alert("Please enter connection number then Calculate");
			return false;
     }else if(pipeSize=="" || pipeSize==null){
			
			alert("Pipe size is not available");
			return false;
     }else if(present_reading=="" || present_reading==null){
			alert("Please enter Present Reading");
			return false;
     }else if(present_reading<previous_reading){
			alert("Present Reading should be greater than or equal to previous Reading");
			return false;
     }else if(mc_status=="" || mc_status==null){
			alert("Please Select Meter Status");
			return false;
  	 }else{
	
     var units=parseInt(present_reading)-parseInt(previous_reading);
	 $.ajax({
		  type: "GET",
		  url: "./billing/calculateBill/"+connectionNo+"/"+pipeSize+"/"+conType+"/"+units+"/"+mc_status,
		  dataType: "JSON",
		  data:{
			  billperiod:billperiod  
			  
		  },
		  async       : false,
		  
		  success: function(response){
		
			       $.each(response, function(index, data){
						
			    	  alert("Amount is Calculated Please Close the Pop Up Window");
			    	  
		    	      $('#wtr_charges9').val(data.waterCharges);
					  $('#swr_charges9').val(data.sewageCharges);
					  $('#mtr_rent9').val(data.meterRent);
					  var wc=data.waterCharges;
					  var sc=data.sewageCharges;
					  var mr=data.meterRent;
					  $('#tot_amt9').val(data.netAmount); 
					  $('#rebate9').val(parseFloat((wc+sc+mr)*0.03).toFixed(2)); 
					  $('#magaf').val(data.waterCharges); 
					  
					  $('#previous_reading2').val(previous_reading);
					  $('#present_reading2').val(present_reading);
					  $('#consumption2').val(units);
					  $('#mc_status2').val(mc_status);
					  
					  
				 
				 });
			  
			  
			  
		  }
		});
	 
     }
	
}


function calculateBill2(){
	
	var connectionNo=$("#connection_no").val();
	var pipeSize=$("#pipe_size").val();
	var conType=$("#con_type").val();
	var billperiod=1;
	var previous_reading=$("#previous_reading3m").val();
	var present_reading=$("#present_reading3m").val();
	var mc_status=$('#mc_status3m').val();
	
	
	 if(connectionNo=="" || connectionNo==null){
			alert("Please enter connection number then Calculate");
			return false;
     }else if(pipeSize=="" || pipeSize==null){
			
			alert("Pipe size is not available");
			return false;
     }else if(present_reading=="" || present_reading==null){
			alert("Please enter Present Reading");
			return false;
     }else if(present_reading<previous_reading){
			alert("Present Reading should be greater than or equal to previous Reading");
			return false;
     }else if(mc_status=="" || mc_status==null){
			alert("Please Select Meter Status");
			return false;
  	 }else{
	
     var units=parseInt(present_reading)-parseInt(previous_reading);
	 $.ajax({
		  type: "GET",
		  url: "./billing/calculateBill/"+connectionNo+"/"+pipeSize+"/"+conType+"/"+units+"/"+mc_status,
		  dataType: "JSON",
		  data:{
			  billperiod:billperiod  
			  
		  },
		  async       : false,
		  
		  success: function(response){
		
			          $.each(response, function(index, data){
						
			    	  alert("Amount is Calculated Please Close the Pop Up Window");
			    	  
		    	      $('#wtr_charges10').val(data.waterCharges);
					  $('#swr_charges10').val(data.sewageCharges);
					  $('#mtr_rent10').val(data.meterRent);
					  var wc=data.waterCharges;
					  var sc=data.sewageCharges;
					  var mr=data.meterRent;
					  $('#tot_amt10').val(data.netAmount); 
					  $('#rebate10').val(parseFloat((wc+sc+mr)*0.03).toFixed(2)); 
					  $('#magafc').val(data.waterCharges); 
					  
					  $('#previous_reading3c').val(previous_reading);
					  $('#present_reading3c').val(present_reading);
					  $('#consumption3c').val(units);
					  $('#mc_status3c').val(mc_status);
					  
					  
				 
				 });
			  
			  
			  
		  }
		});
     }
}


function calculateBill2b(){
	
	var connectionNo=$("#connection_no").val();
	var pipeSize=$("#pipe_size").val();
	var conType=$("#con_type").val();
	var billperiod=1;
	var previous_reading=$("#previous_reading3mb").val();
	var present_reading=$("#present_reading3mb").val();
	var mc_status=$('#mc_status3mb').val();
	
	
	 if(connectionNo=="" || connectionNo==null){
			alert("Please enter connection number then Calculate");
			return false;
     }else if(pipeSize=="" || pipeSize==null){
			
			alert("Pipe size is not available");
			return false;
     }else if(present_reading=="" || present_reading==null){
			alert("Please enter Present Reading");
			return false;
     }else if(present_reading<previous_reading){
			alert("Present Reading should be greater than or equal to previous Reading");
			return false;
     }else if(mc_status=="" || mc_status==null){
			alert("Please Select Meter Status");
			return false;
  	 }else{
	
     var units=parseInt(present_reading)-parseInt(previous_reading);
	 $.ajax({
		  type: "GET",
		  url: "./billing/calculateBill/"+connectionNo+"/"+pipeSize+"/"+conType+"/"+units+"/"+mc_status,
		  dataType: "JSON",
		  data:{
			  billperiod:billperiod  
			  
		  },
		  async       : false,
		  
		  success: function(response){
		
			          $.each(response, function(index, data){
						
			    	  alert("Amount is Calculated Please Close the Pop Up Window");
			    	  
		    	      $('#wtr_charges11').val(data.waterCharges);
					  $('#swr_charges11').val(data.sewageCharges);
					  $('#mtr_rent11').val(data.meterRent);
					  var wc=data.waterCharges;
					  var sc=data.sewageCharges;
					  var mr=data.meterRent;
					  $('#tot_amt11').val(data.netAmount); 
					  $('#rebate11').val(parseFloat((wc+sc+mr)*0.03).toFixed(2)); 
					  $('#magafcb').val(data.waterCharges); 
					  
					  $('#previous_reading3cb').val(previous_reading);
					  $('#present_reading3cb').val(present_reading);
					  $('#consumption3cb').val(units);
					  $('#mc_status3cb').val(mc_status);
					  
					  
				 
				 });
			  
			  
			  
		  }
		});
     }
}




function showArrears(){
	
	var aubarr=$('#aub').val();
	var bhaarr=$('#bhaarr').val();
	var asharr=$('#asharr').val();
	var kararr=$('#kararr').val();
	var mangarr=$('#mangarr').val();
	var pouarr=$('#pouarr').val();
	var magarr=$('#magarr').val();
	var magarrf=$('#magarrf').val();
	var magarrfc=$('#magarrfc').val();
	var magarrfcb=$('#magarrfcb').val();
	
	 if(aubarr==null || aubarr==""){
		$('#aub').val(0);
	}else if(bhaarr==null || bhaarr==""){
		$('#bhaarr').val(0);
	}else if(asharr==null || asharr==""){
		$('#asharr').val(0);
	}else if(kararr==null || kararr=="" ){
		$('#kararr').val(0);
	}else if(mangarr==null || mangarr==""){
		$('#mangarr').val(0);
	}else if(pouarr==null || pouarr==""){
		$('#pouarr').val(0);
	}else if(magarr==null || magarr==""){
		$('#magarr').val(0);
	}else if(magarrf==null || magarrf==""){
		$('#magarrf').val(0);
	}else if(magarrfc==null || magarrfc==""){
		$('#magarrfc').val(0);
	}else if(magarrfcb==null || magarrfcb==""){
		$('#magarrfcb').val(0);
	}else if(parseInt(aubarr)<0 || parseInt(aubarr)>0){
		
		document.getElementById('aub').readOnly = false;
		document.getElementById('bhaarr').readOnly = true;
		document.getElementById('asharr').readOnly = true;
		document.getElementById('kararr').readOnly = true;
		document.getElementById('mangarr').readOnly = true;
		document.getElementById('pouarr').readOnly = true;
		document.getElementById('magarr').readOnly = true;
		document.getElementById('magarrf').readOnly = true;
		document.getElementById('magarrfc').readOnly = true;
		document.getElementById('magarrfcb').readOnly = true;

		
	}
	else if(parseInt(bhaarr)<0  || parseInt(bhaarr)>0){
		document.getElementById('aub').readOnly = true;
		document.getElementById('bhaarr').readOnly = false;
		document.getElementById('asharr').readOnly = true;
		document.getElementById('kararr').readOnly = true;
		document.getElementById('mangarr').readOnly = true;
		document.getElementById('pouarr').readOnly = true;
		document.getElementById('magarr').readOnly = true;
		document.getElementById('magarrf').readOnly = true;
		document.getElementById('magarrfc').readOnly = true;
		document.getElementById('magarrfcb').readOnly = true;

		$('#shr').val(0);
		
		
	}
	else if(parseInt(asharr)<0 || parseInt(asharr)>0){
		document.getElementById('aub').readOnly = true;
		document.getElementById('bhaarr').readOnly = true;
		document.getElementById('asharr').readOnly = false;
		document.getElementById('kararr').readOnly = true;
		document.getElementById('mangarr').readOnly = true;
		document.getElementById('pouarr').readOnly = true;
		document.getElementById('magarr').readOnly = true;
		document.getElementById('magarrf').readOnly = true;
		document.getElementById('magarrfc').readOnly = true;
		document.getElementById('magarrfcb').readOnly = true;

		$('#shr').val(0);
		$('#bha').val(0);
	}
	else if(parseInt(kararr)<0 || parseInt(kararr)>0){
		document.getElementById('aub').readOnly = true;
		document.getElementById('bhaarr').readOnly = true;
		document.getElementById('asharr').readOnly = true;
		document.getElementById('kararr').readOnly = false;
		document.getElementById('mangarr').readOnly = true;
		document.getElementById('pouarr').readOnly = true;
		document.getElementById('magarr').readOnly = true;
		document.getElementById('magarrf').readOnly = true;
		document.getElementById('magarrfc').readOnly = true;
		document.getElementById('magarrfcb').readOnly = true;
		$('#shr').val(0);
		$('#bha').val(0);
		$('#aba').val(0);
		
	}
	else if(parseInt(mangarr)<0 || parseInt(mangarr)>0){
		document.getElementById('aub').readOnly = true;
		document.getElementById('bhaarr').readOnly = true;
		document.getElementById('asharr').readOnly = true;
		document.getElementById('kararr').readOnly = true;
		document.getElementById('mangarr').readOnly = false;
		document.getElementById('pouarr').readOnly = true;
		document.getElementById('magarr').readOnly = true;
		document.getElementById('magarrf').readOnly = true;
		document.getElementById('magarrfc').readOnly = true;
		document.getElementById('magarrfcb').readOnly = true;

		$('#shr').val(0);
		$('#bha').val(0);
		$('#aba').val(0);
		$('#kba').val(0);
	}
	else if(parseInt(pouarr)<0 || parseInt(pouarr)>0){
		document.getElementById('aub').readOnly = true;
		document.getElementById('bhaarr').readOnly = true;
		document.getElementById('asharr').readOnly = true;
		document.getElementById('kararr').readOnly = true;
		document.getElementById('mangarr').readOnly = true;
		document.getElementById('pouarr').readOnly = false;
		document.getElementById('magarr').readOnly = true;
		document.getElementById('magarrf').readOnly = true;
		document.getElementById('magarrfc').readOnly = true;
		document.getElementById('magarrfcb').readOnly = true;
		$('#shr').val(0);
		$('#bha').val(0);
		$('#aba').val(0);
		$('#kba').val(0);
		$('#mba').val(0);
		
	}else if(parseInt(magarr)<0 || parseInt(magarr)>0){
		document.getElementById('aub').readOnly = true;
		document.getElementById('bhaarr').readOnly = true;
		document.getElementById('asharr').readOnly = true;
		document.getElementById('kararr').readOnly = true;
		document.getElementById('mangarr').readOnly = true;
		document.getElementById('pouarr').readOnly = true;
		document.getElementById('magarr').readOnly = false;
		document.getElementById('magarrf').readOnly = true;
		document.getElementById('magarrfc').readOnly = true;
		document.getElementById('magarrfcb').readOnly = true;
		$('#shr').val(0);
		$('#bha').val(0);
		$('#aba').val(0);
		$('#kba').val(0);
		$('#mba').val(0);
		$('#pba').val(0);
		
	}else if(parseInt(magarrf)<0 || parseInt(magarrf)>0){
		document.getElementById('aub').readOnly = true;
		document.getElementById('bhaarr').readOnly = true;
		document.getElementById('asharr').readOnly = true;
		document.getElementById('kararr').readOnly = true;
		document.getElementById('mangarr').readOnly = true;
		document.getElementById('pouarr').readOnly = true;
		document.getElementById('magarr').readOnly = true;
		document.getElementById('magarrf').readOnly = false;
		document.getElementById('magarrfc').readOnly = true;
		document.getElementById('magarrfcb').readOnly = true;
		
		$('#shr').val(0);
		$('#bha').val(0);
		$('#aba').val(0);
		$('#kba').val(0);
		$('#mba').val(0);
		$('#pba').val(0);
		$('#maga').val(0);
	}else if(parseInt(magarrfc)<0 || parseInt(magarrfc)>0){
		document.getElementById('aub').readOnly = true;
		document.getElementById('bhaarr').readOnly = true;
		document.getElementById('asharr').readOnly = true;
		document.getElementById('kararr').readOnly = true;
		document.getElementById('mangarr').readOnly = true;
		document.getElementById('pouarr').readOnly = true;
		document.getElementById('magarr').readOnly = true;
		document.getElementById('magarrf').readOnly = true;
		document.getElementById('magarrfc').readOnly = false;
		document.getElementById('magarrfcb').readOnly = true;
		
		$('#shr').val(0);
		$('#bha').val(0);
		$('#aba').val(0);
		$('#kba').val(0);
		$('#mba').val(0);
		$('#pba').val(0);
		$('#maga').val(0);
		$('#magaf').val(0);
	}else if(parseInt(magarrfcb)<0 || parseInt(magarrfcb)>0){
		document.getElementById('aub').readOnly = true;
		document.getElementById('bhaarr').readOnly = true;
		document.getElementById('asharr').readOnly = true;
		document.getElementById('kararr').readOnly = true;
		document.getElementById('mangarr').readOnly = true;
		document.getElementById('pouarr').readOnly = true;
		document.getElementById('magarr').readOnly = true;
		document.getElementById('magarrf').readOnly = true;
		document.getElementById('magarrfc').readOnly = true;
		document.getElementById('magarrfcb').readOnly = false;
		
		$('#shr').val(0);
		$('#bha').val(0);
		$('#aba').val(0);
		$('#kba').val(0);
		$('#mba').val(0);
		$('#pba').val(0);
		$('#maga').val(0);
		$('#magaf').val(0);
		$('#magafb').val(0);
	}else{
		
		document.getElementById('aub').readOnly = false;
		document.getElementById('bhaarr').readOnly = false;
		document.getElementById('asharr').readOnly = false;
		document.getElementById('kararr').readOnly = false;
		document.getElementById('mangarr').readOnly = false;
		document.getElementById('pouarr').readOnly = false;
		document.getElementById('magarr').readOnly = false;
		document.getElementById('magarrf').readOnly = false;
		document.getElementById('magarrfc').readOnly = false;
		document.getElementById('magarrfcb').readOnly = true;

		$('#aub').val(0);
		$('#bhaarr').val(0);
		$('#asharr').val(0);
		$('#kararr').val(0);
		$('#mangarr').val(0);
		$('#pouarr').val(0);
		$('#magarr').val(0);
		$('#magarrf').val(0);
		$('#magarrfc').val(0);
		$('#magarrfcb').val(0);
		
		
	}	
	
	
}


function ledgerHistoryPopUp(){
	$("#viewPayHistotytbody").empty();
	
	var connectionNo=$("#connection_no").val();
	
	if(connectionNo==null || connectionNo==""){
		
	}else{
	
	var tableData = "";
	$.ajax
	({			
		type : "GET",
		url : "./billing/viewBillLedgertHistory/"+connectionNo,
		async: false,
		dataType : "JSON",
		success : function(response) 
		{	    
			for ( var s = 0, len = response.length; s < len; ++s) {
		              	var obj = response[s];
		              	
		              	tableData += "<tr>"
								    +"<td>"+(s+1)+"</td>" 
								    +"<td>"+obj.monthyearnep+"</td>"
									+"<td>"+obj.cr+"</td>"
									+"<td>"+obj.lr+"</td>"
									+"<td>"+obj.units+"</td>"
									+"<td>"+obj.lastarrears+"</td>"
									+"<td>"+obj.water_charges+"</td>"
									+"<td>"+obj.sw_charges+"</td>"
									+"<td>"+obj.mtr_rent+"</td>"
									+"<td>"+obj.net_amount+"</td>"
									+"<td>"+obj.penalty+"</td>"
									+"<td>"+obj.rebate+"</td>"
									+"<td>"+obj.bill_adj+"</td>"
									+"<td>"+obj.penalty_adj+"</td>"
									+"<td>"+obj.receipt_amount+"</td>"
									+"<td>"+obj.close_balnce+"</td>"
									+"<td>"+obj.receipt_no+"</td>"
									+"<td>"+obj.receipt_date+"</td>"
									+"<td>"+obj.billno+"</td>"

								+"</tr>";
				                
				     }
				$('#viewPayHistotytbody1').append(tableData);
				loadSearchFilter1('popupPayment1',tableData,'viewPayHistotytbody1');
		}
	

	});
}
	
}

function loadSearchFilter1(param,tableData,temp)
{
    $('#'+param).dataTable().fnClearTable();
    $('#'+param).dataTable().fnDestroy();
    $('#'+temp).html(tableData);
    $('#'+param).dataTable();

} 


function convertToUpperCase(){
	$("#connection_no").val($("#connection_no").val().toUpperCase().trim());
}

</script>

<div class="panel panel-flat">
						<div class="panel-body">
						<div class="row" hidden="true" id="alertDiv">
								    </div>
							<!-- <form class="form-horizontal form-validate-jquery" action="#">-->
							<form:form action="./updateAddConBillEntry" role="form" modelAttribute="updateAddConBillEntry" commandName="updateAddConBillEntry" method="POST" id="updateAddConBillEntry">
						<fieldset class="content-group"> 
								
								<input type="text" name="previous_reading" id="previous_reading" hidden="true" value="0">
								<input type="text" name="present_reading" id="present_reading" hidden="true" value="0">
								<input type="text" name="consumption" id="consumption" hidden="true" value="0">
								
								
								<input type="text" name="previous_reading2" id="previous_reading2" hidden="true" value="0">
								<input type="text" name="present_reading2" id="present_reading2" hidden="true" value="0">
								<input type="text" name="consumption2" id="consumption2" hidden="true" value="0">
								
								<input type="text" name="previous_reading3c" id="previous_reading3c" hidden="true" value="0">
								<input type="text" name="present_reading3c" id="present_reading3c" hidden="true" value="0">
								<input type="text" name="consumption3c" id="consumption3c" hidden="true" value="0">
								
								<input type="text" name="previous_reading3cb" id="previous_reading3cb" hidden="true" value="0">
								<input type="text" name="present_reading3cb" id="present_reading3cb" hidden="true" value="0">
								<input type="text" name="consumption3cb" id="consumption3cb" hidden="true" value="0">
								
								
								<input type="text" name="mc_status" id="mc_status" hidden="true">
								<input type="text" name="mc_status2" id="mc_status2" hidden="true">
								<input type="text" name="mc_status3c" id="mc_status3c" hidden="true">
								<input type="text" name="mc_status3cb" id="mc_status3cb" hidden="true">
								
								
								<legend class="text-bold" style="font-size: 18px;">Consumer Billing Entry</legend>
										
										<div class="row">
											<div class="col-md-4">
												<div class="form-group">
													<label>Consumer No&nbsp;<font color="red">*</font></label>
					                                <form:input path="connection_no" id="connection_no" name="connection_no" type="text" class="form-control" placeholder="Consumer No" autocomplete="off" onkeyup="convertToUpperCase();"/>
				                                </div>
											</div>
											
											<div class="col-md-1" id="addId">
													<div class="form-group">
													<label>&nbsp;</label> 
													<div class="input-group">
															&nbsp;<button type="button" class="btn bg-blue"  data-style="expand-right" data-spinner-size="20" onclick="return consumerDetails();"><span class="ladda-label">View</span></button>
													</div>
												</div>
											</div>
											
											<div class="col-md-4">
												<div class="form-group">
													<label>Name</label>
					                                <form:input path="name_eng" id="name_eng" name="name_eng" type="text" class="form-control" placeholder="Consumer Name" readonly="true"/>
				                                </div>
											</div>
											
											<div class="col-md-4">
												<div class="form-group">
													<label>Address</label>
					                                <form:input path="address_eng" id="address_eng" name="address_eng" type="text" class="form-control" placeholder=" Consumer Address"/>
				                                </div>
											</div>
											<div class="col-md-4">
												<div class="form-group">
													<label>Area No</label>
					                                <form:input path="area_no" id="area_no" name="area_no" type="text" class="form-control" placeholder="Consumer Area No." readonly="true"/>
				                                </div>
											</div>
											
											<div class="col-md-4">
												<div class="form-group">
													<label>Ward No</label>
					                                <form:input path="ward_no" id="ward_no" name="ward_no" type="text" class="form-control" placeholder="Consumer Ward No." readonly="true"/>
				                                </div>
											</div>
											
											<div class="col-md-3">
												<div class="form-group">
													<label>Meter Rent Applicable&nbsp;<font color="red">*</font></label>
					                                <form:select path="meterHired" id="meterHired" name="meterHired" class="selectbox input-group">
										   				 <form:option  value="noVal" data-icon="icon-git-branch">Select</form:option>
										   				 <form:option  value="Yes" data-icon="icon-git-branch">Yes</form:option>
										   				 <form:option  value="No" data-icon="icon-git-branch">No</form:option>
													</form:select>
				                                </div>
											</div>
											
											<div class="col-md-2">
												<div class="form-group">
													<label>Sewerage&nbsp;<font color="red">*</font></label>
					                                <form:select path="sewage_used" id="sewage_used" name="sewage_used" class="selectbox input-group">
										   				 <form:option  value="noVal" data-icon="icon-git-branch">Select</form:option>
										   				 <form:option  value="Yes" data-icon="icon-git-branch">Yes</form:option>
										   				 <form:option  value="No" data-icon="icon-git-branch">No</form:option>
													</form:select>
				                                </div>
											</div>
											
											<div class="col-md-2">
												<div class="form-group">
													<label>Set Amount</label>
					                                <input id="setAmount" name="setAmount" type="text" class="form-control" placeholder="Set Amount" value="0"/>
				                                </div>
											</div>
											
											<div class="col-md-2">
												<div class="form-group">
													<label>Connection Type</label>
					                               
					                               <form:select path="con_type" id="con_type" name="con_type" class="selectbox input-group">
										   				 <form:option  value="Metered" data-icon="icon-git-branch">Metered</form:option>
										   				 <form:option  value="Unmetered" data-icon="icon-git-branch">Unmetered</form:option>
													</form:select>
					                               
					                               
					                                
				                                </div>
											</div>
											
											<div class="col-md-2">
												<div class="form-group">
													<label>Pipe Size</label>
					                                <input id="pipe_size" name="pipe_size" type="text" class="form-control" placeholder="Pipe Size"/>
				                                </div>
											</div>
											
											<div class="col-md-2">
												<div class="form-group">
													<label>Board Balance</label>
					                                <input id="balance" name="balance" type="text" class="form-control" placeholder="Board Balance" value="0"/>
				                                </div>
											</div>
											
											<div class="col-md-1">
												<div class="form-group">
												<label>&nbsp;</label>
											<button type="button" class="btn bg-teal btn-sm" data-toggle="modal" data-target="#modal_default1" id="onshow_callback" onclick='ledgerHistoryPopUp()'>View Ledger</button>
											</div>
											</div>
											
											<div hidden="true">
												<form:input path="id" id="id" name="id" type="text" class="form-control" placeholder="" />
											</div>
											
									     </div>
									<div class="row">	
										<div class="col-md-2">
												<div class="form-group">
													<label>2073 Asadh</label>
					                                <form:input path="" id="aub" name="aub" type="text" class="form-control" placeholder="Enter Arrears" value="0" onchange="showArrears();"/>
				                                </div>
											</div>
											<form:input path="" hidden="true" id="monYearNep1" name="monYearNep1" value="207303" type="text" />
											
											<div class="col-md-3">
												<div class="form-group">
													<label>Water Charges</label>
					                                <form:input path="" id="wtr_charges1" name="wtr_charges1" type="text" class="form-control"   readonly="true" value="0"/>
				                                </div>
											</div>
											
											<div class="col-md-3">
												<div class="form-group">
													<label>SW Charges</label>
					                                <form:input path="" id="swr_charges1" name="swr_charges1" type="text" class="form-control"  readonly="true" value="0"/>
				                                </div>
											</div>
											
											
											
											
											
											<div class="col-md-1">
												<div class="form-group">
													<label>Meter Rent</label>
					                                <form:input path=""  id="mtr_rent1" name="mtr_rent1" type="text" class="form-control" value="0" readonly="true"/>
				                                </div>
											</div>
											
											<div class="col-md-2">
												<div class="form-group">
													<label>Total Amount</label>
					                                <form:input path=""  id="tot_amt1" name="tot_amt1" type="text" class="form-control"   readonly="true" value="0"/>
				                                </div>
											</div>
											
											<div class="col-md-2">
												<div class="form-group">
													<label>Penalty</label>
					                                <form:input path=""  id="penalty1" name="penalty1" type="text" class="form-control"   readonly="true" value="0"/>
				                                </div>
											</div>
											
											<div class="col-md-1">
												<div class="form-group">
													<label>Rebate</label>
					                                <form:input path="" id="rebate1" name="rebate1" type="text" class="form-control"   readonly="true" value="0"/>
				                                </div>
											</div>
										</div>
									
									<div class="row">	
										     
										    <div class="col-md-2">
												
											</div>
											
										     <div class="col-md-2">
												<div class="form-group">
										     
													<label>2073 Shrawan&nbsp;&nbsp;<input type="checkbox"  id="id11" onClick="setValue(0)"></label>
													
					                                <form:input path="" id="shr" name="shr" type="text" class="form-control" placeholder="Enter BillAmount" value="0"/>
				                                </div>
											</div>
											<form:input path="" hidden="true" id="monYearNep2" name="monYearNep2" value="207304" type="text"/>
											
											
											
											<div class="col-md-2">
												<div class="form-group">
													<label>&nbsp;</label>
					                                <form:input path="" id="wtr_charges2" name="wtr_charges2" type="text" class="form-control"   readonly="true" value="0"/>
				                                </div>
											</div>
											
											<div class="col-md-2">
												<div class="form-group">
													<label>&nbsp;</label>
					                                <form:input path="" id="swr_charges2" name="swr_charges2" type="text" class="form-control"    readonly="true" value="0"/>
				                                </div>
											</div>
											
											<div class="col-md-1">
												<div class="form-group">
													<label>&nbsp;</label>
					                                <form:input path=""  id="mtr_rent2" name="mtr_rent2" type="text" class="form-control" value="0" readonly="true"/>
				                                </div>
											</div>
											
											<div class="col-md-2">
												<div class="form-group">
													<label>&nbsp;</label>
					                                <form:input path=""  id="tot_amt2" name="tot_amt2" type="text" class="form-control"   readonly="true" value="0"/>
				                                </div>
											</div>
											
											<div class="col-md-2">
												<div class="form-group">
													<label>&nbsp;</label>
					                                <form:input path=""  id="penalty2" name="penalty2" type="text" class="form-control"   readonly="true" value="0"/>
				                                </div>
											</div>
											
											<div class="col-md-1">
												<div class="form-group">
													<label>&nbsp;</label>
					                                <form:input path="" id="rebate2" name="rebate2" type="text" class="form-control"   readonly="true" value="0"/>
				                                </div>
											</div>
										</div>
									
									
									<div class="row">	
										     
										     <div class="col-md-2">
												<div class="form-group">
													<label>Open Balance&nbsp;</label>
					                                <form:input path="" id="bhaarr" name="bhaarr" type="text" class="form-control" value="0" onchange="showArrears();"/>
				                               </div>
											</div>
										     
										     
										     <div class="col-md-2">
												<div class="form-group">
													<label>2073 Bhadra&nbsp;&nbsp;<input type="checkbox"  id="id12" onClick="setValue(1)"></label>
					                                <form:input path="" id="bha" name="bha" type="text" class="form-control" placeholder="Enter Arrears" value="0"/>
				                                </div>
											</div>
											<form:input path="" hidden="true" id="monYearNep3" name="monYearNep3" value="207305" type="text" />
											
											<div class="col-md-2">
												<div class="form-group">
													<label>&nbsp;</label>
					                                <form:input path="" id="wtr_charges3" name="wtr_charges3" type="text" class="form-control"   readonly="true" value="0"/>
				                                </div>
											</div>
											
											<div class="col-md-2">
												<div class="form-group">
													<label>&nbsp;</label>
					                                <form:input path="" id="swr_charges3" name="swr_charges3" type="text" class="form-control"  readonly="true" value="0"/>
				                                </div>
											</div>
											
											<div class="col-md-1">
												<div class="form-group">
													<label>&nbsp;</label>
					                                <form:input path=""  id="mtr_rent3" name="mtr_rent3" type="text" class="form-control" value="0" readonly="true"/>
				                                </div>
											</div>
											
											<div class="col-md-2">
												<div class="form-group">
													<label>&nbsp;</label>
					                                <form:input path=""  id="tot_amt3" name="tot_amt3" type="text" class="form-control"   readonly="true" value="0"/>
				                                </div>
											</div>
											
											<div class="col-md-2">
												<div class="form-group">
													<label>&nbsp;</label>
					                                <form:input path=""  id="penalty3" name="penalty3" type="text" class="form-control"   readonly="true" value="0"/>
				                                </div>
											</div>
											
											<div class="col-md-1">
												<div class="form-group">
													<label>&nbsp;</label>
					                                <form:input path="" id="rebate3" name="rebate3" type="text" class="form-control"   readonly="true" value="0"/>
				                                </div>
											</div>
										</div>	
										
										<div class="row">	
											 
											  <div class="col-md-2">
												<div class="form-group">
													<label>Open Balance&nbsp;</label>
					                                <form:input path="" id="asharr" name="asharr" type="text" class="form-control" value="0" onchange="showArrears();"/>
				                               </div>
											  </div>
											 
											 
											 <div class="col-md-2">
												<div class="form-group">
													<label>2073 Ashwin&nbsp;&nbsp;<input type="checkbox"  id="id13" onClick="setValue(2)"></label>
					                                <form:input path="" id="aba" name="aba" type="text" class="form-control" placeholder="Enter BillAmount" value="0"/>
				                                </div>
											</div>
											<form:input path="" hidden="true" id="monYearNep4" name="monYearNep4" value="207306" type="text"  />
											
											<div class="col-md-2">
												<div class="form-group">
													<label>&nbsp;</label>
					                                <form:input path="" id="wtr_charges4" name="wtr_charges4" type="text" class="form-control"   readonly="true" value="0"/>
				                                </div>
											</div>
											
											<div class="col-md-2">
												<div class="form-group">
													<label>&nbsp;</label>
					                                <form:input path="" id="swr_charges4" name="swr_charges4" type="text" class="form-control"  readonly="true" value="0"/>
				                                </div>
											</div>
											
											<div class="col-md-1">
												<div class="form-group">
													<label>&nbsp;</label>
					                                <form:input path=""  id="mtr_rent4" name="mtr_rent4" type="text" class="form-control" value="0" readonly="true"/>
				                                </div>
											</div>
											
											<div class="col-md-2">
												<div class="form-group">
													<label>&nbsp;</label>
					                                <form:input path=""  id="tot_amt4" name="tot_amt4" type="text" class="form-control"   readonly="true" value="0"/>
				                                </div>
											</div>
											
											<div class="col-md-2">
												<div class="form-group">
													<label>&nbsp;</label>
					                                <form:input path=""  id="penalty4" name="penalty4" type="text" class="form-control"   readonly="true" value="0"/>
				                                </div>
											</div>
											
											<div class="col-md-1">
												<div class="form-group">
													<label>&nbsp;</label>
					                                <form:input path="" id="rebate4" name="rebate4" type="text" class="form-control"   readonly="true" value="0"/>
				                                </div>
											</div>
										</div>	
										
										<div class="row">	
											 
											  <div class="col-md-2">
												<div class="form-group">
													<label>Open Balance&nbsp;</label>
					                                <form:input path="" id="kararr" name="kararr" type="text" class="form-control" value="0" onchange="showArrears();"/>
				                               </div>
											  </div>
											 
											 <div class="col-md-2">
												<div class="form-group">
													<label>2073 Kartik&nbsp;&nbsp;<input type="checkbox"  id="id14" onClick="setValue(3)"></label>
					                                <form:input path="" id="kba" name="kba" type="text" class="form-control" placeholder="Enter BillAmount" value="0"/>
				                                </div>
											</div>
											<form:input path="" hidden="true" id="monYearNep5" name="monYearNep5" value="207307" type="text"/>
											
											<div class="col-md-2">
												<div class="form-group">
													<label>&nbsp;</label>
					                                <form:input path="" id="wtr_charges5" name="wtr_charges5" type="text" class="form-control"   readonly="true" value="0"/>
				                                </div>
											</div>
											
											<div class="col-md-2">
												<div class="form-group">
													<label>&nbsp;</label>
					                                <form:input path="" id="swr_charges5" name="swr_charges5" type="text" class="form-control"    readonly="true" value="0"/>
				                                </div>
											</div>
											
											<div class="col-md-1">
												<div class="form-group">
													<label>&nbsp;</label>
					                                <form:input path=""  id="mtr_rent5" name="mtr_rent5" type="text" class="form-control" value="0" readonly="true"/>
				                                </div>
											</div>
											
											<div class="col-md-2">
												<div class="form-group">
													<label>&nbsp;</label>
					                                <form:input path=""  id="tot_amt5" name="tot_amt5" type="text" class="form-control"   readonly="true" value="0"/>
				                                </div>
											</div>
											
											<div class="col-md-2">
												<div class="form-group">
													<label>&nbsp;</label>
					                                <form:input path=""  id="penalty5" name="penalty5" type="text" class="form-control"   readonly="true" value="0"/>
				                                </div>
											</div>
											
											<div class="col-md-1">
												<div class="form-group">
													<label>&nbsp;</label>
					                                <form:input path="" id="rebate5" name="rebate5" type="text" class="form-control"   readonly="true" value="0"/>
				                                </div>
											</div>
										</div>	
										
										<div class="row">	
											 
											 <div class="col-md-2">
												<div class="form-group">
													<label>Open Balance&nbsp;</label>
					                                <form:input path="" id="mangarr" name="mangarr" type="text" class="form-control" value="0" onchange="showArrears();"/>
				                               </div>
											</div>
										
										     <div class="col-md-2">
												<div class="form-group">
													<label>2073 Mangsir&nbsp;&nbsp;<input type="checkbox"  id="id15" onClick="setValue(4)"></label>
					                                <form:input path="" id="mba" name="mba" type="text" class="form-control" placeholder="Enter BillAmount" value="0"/>
				                                </div>
											</div>
											<form:input path="" hidden="true" id="monYearNep6" name="monYearNep6" value="207308" type="text"  />
											
											<div class="col-md-2">
												<div class="form-group">
													<label>&nbsp;</label>
					                                <form:input path="" id="wtr_charges6" name="wtr_charges6" type="text" class="form-control"   readonly="true" value="0"/>
				                                </div>
											</div>
											
											<div class="col-md-2">
												<div class="form-group">
													<label>&nbsp;</label>
					                                <form:input path="" id="swr_charges6" name="swr_charges6" type="text" class="form-control"    readonly="true" value="0"/>
				                                </div>
											</div>
											
											<div class="col-md-1">
												<div class="form-group">
													<label>&nbsp;</label>
					                                <form:input path=""  id="mtr_rent6" name="mtr_rent6" type="text" class="form-control"  value="0" readonly="true"/>
				                                </div>
											</div>
											
											<div class="col-md-2">
												<div class="form-group">
													<label>&nbsp;</label>
					                                <form:input path=""  id="tot_amt6" name="tot_amt6" type="text" class="form-control"   readonly="true" value="0"/>
				                                </div>
											</div>
											
											<div class="col-md-2">
												<div class="form-group">
													<label>&nbsp;</label>
					                                <form:input path=""  id="penalty6" name="penalty6" type="text" class="form-control"   readonly="true" value="0"/>
				                                </div>
											</div>
											
											<div class="col-md-1">
												<div class="form-group">
													<label>&nbsp;</label>
					                                <input id="rebate6" name="rebate6" type="text" class="form-control"   readonly="readonly" value="0"/>
				                                </div>
											</div>
										</div>	
										
										<div class="row">	
										     
										     <div class="col-md-2">
												<div class="form-group">
													<label>Open Balance&nbsp;</label>
					                                <form:input path="" id="pouarr" name="pouarr" type="text" class="form-control" value="0" onchange="showArrears();"/>
				                               </div>
											 </div>
										     
										     <div class="col-md-2">
												<div class="form-group">
													<label>2073 Poush&nbsp;&nbsp;<input type="checkbox"  id="id16" onClick="setValue(5)"></label>
					                                <input id="pba" name="pba" type="text" class="form-control" placeholder="Enter BillAmount" value="0"/>
				                                </div>
											</div>
											<form:input path="" hidden="true" id="monYearNep7" name="monYearNep7" value="207309" type="text"  />
											
											<div class="col-md-2">
												<div class="form-group">
													<label>&nbsp;</label>
					                                <form:input path="" id="wtr_charges7" name="wtr_charges7" type="text" class="form-control"   readonly="true" value="0"/>
				                                </div>
											</div>
											
											<div class="col-md-2">
												<div class="form-group">
													<label>&nbsp;</label>
					                                <form:input path="" id="swr_charges7" name="swr_charges7" type="text" class="form-control"    readonly="true" value="0"/>
				                                </div>
											</div>

											<div class="col-md-1">
												<div class="form-group">
													<label>&nbsp;</label>
					                                <form:input path=""  id="mtr_rent7" name="mtr_rent7" type="text" class="form-control"  value="0" readonly="true"/>
				                                </div>
											</div>
											
											<div class="col-md-2">
												<div class="form-group">
													<label>&nbsp;</label>
					                                <form:input path=""  id="tot_amt7" name="tot_amt7" type="text" class="form-control"   readonly="true" value="0"/>
				                                </div>
											</div>
											
											<div class="col-md-2">
												<div class="form-group">
													<label>&nbsp;</label>
					                                <form:input path=""  id="penalty7" name="penalty7" type="text" class="form-control"   readonly="true" value="0"/>
				                                </div>
											</div>
											
											<div class="col-md-1">
												<div class="form-group">
													<label>&nbsp;</label>
					                                <form:input path="" id="rebate7" name="rebate7" type="text" class="form-control"   readonly="true" value="0"/>
				                                </div>
											</div>
										</div>
										
										
										
										
										
										
										
										<div class="row">	
											
											<div class="col-md-2">
												<div class="form-group">
													<label>Open Balance&nbsp;</label>
					                                <form:input path="" id="magarr" name="magarr" type="text" class="form-control" value="0" onchange="showArrears();"/>
				                               </div>
											 </div>
											 
											 
											<div class="col-md-2">
												<div class="form-group">
													<label>2073 Magh&nbsp;&nbsp;<input type="checkbox"  id="id17" onClick="setValue(6)"></label>
					                                <input id="maga" name="maga" type="text" class="form-control" placeholder="Enter BillAmount" value="0"/>
				                                </div>
											</div>
											<form:input path="" hidden="true" id="monYearNep8" name="monYearNep8" value="207310" type="text"  />
											
											<div class="col-md-2">
												<div class="form-group">
													<label>&nbsp;</label>
					                                <form:input path="" id="wtr_charges8" name="wtr_charges8" type="text" class="form-control"   readonly="true" value="0"/>
				                                </div>
											</div>
											
											<div class="col-md-1">
												<div class="form-group">
													<label>&nbsp;</label>
					                                <form:input path="" id="swr_charges8" name="swr_charges8" type="text" class="form-control"    readonly="true" value="0"/>
				                                </div>
											</div>

											<div class="col-md-1">
												<div class="form-group">
													<label>&nbsp;</label>
					                                <form:input path=""  id="mtr_rent8" name="mtr_rent8" type="text" class="form-control"  value="0" readonly="true"/>
				                                </div>
											</div>
											
											<div class="col-md-2">
												<div class="form-group">
													<label>&nbsp;</label>
					                                <form:input path=""  id="tot_amt8" name="tot_amt8" type="text" class="form-control"   readonly="true" value="0"/>
				                                </div>
											</div>
											
											<div class="col-md-1">
												<div class="form-group">
													<label>&nbsp;</label>
					                                <form:input path=""  id="penalty8" name="penalty8" type="text" class="form-control"   readonly="true" value="0"/>
				                                </div>
											</div>
											
											<div class="col-md-1">
												<div class="form-group">
													<label>&nbsp;</label>
					                                <form:input path="" id="rebate8" name="rebate8" type="text" class="form-control"   readonly="true" value="0"/>
				                                </div>
											</div>
											
											 <div class="col-md-1">
												<div class="form-group">
													<label>&nbsp;</label>
											<button type="button" class="btn bg-teal btn-sm" data-toggle="modal" data-target="#modal_default" id="onshow_callback" style="float: right;">Reading</button>
											</div>
											</div>
											
										</div>
										
										
										
										
										
										
										
										
										
										
										
										
										
										
										
										<div class="row">	
											
											<div class="col-md-2">
												<div class="form-group">
													<label>Open Balance&nbsp;</label>
					                                <form:input path="" id="magarrf" name="magarrf" type="text" class="form-control" value="0" onchange="showArrears();"/>
				                               </div>
											 </div>
											 
											 
											<div class="col-md-2">
												<div class="form-group">
													<label>2073 Falgun&nbsp;&nbsp;<input type="checkbox"  id="id18" onClick="setValue(7)"></label>
					                                <input id="magaf" name="magaf" type="text" class="form-control" placeholder="Enter BillAmount" value="0"/>
				                                </div>
											</div>
											<form:input path="" hidden="true" id="monYearNep9" name="monYearNep9" value="207311" type="text"  />
											
											<div class="col-md-2">
												<div class="form-group">
													<label>&nbsp;</label>
					                                <form:input path="" id="wtr_charges9" name="wtr_charges9" type="text" class="form-control"   readonly="true" value="0"/>
				                                </div>
											</div>
											
											<div class="col-md-1">
												<div class="form-group">
													<label>&nbsp;</label>
					                                <form:input path="" id="swr_charges9" name="swr_charges9" type="text" class="form-control"    readonly="true" value="0"/>
				                                </div>
											</div>

											<div class="col-md-1">
												<div class="form-group">
													<label>&nbsp;</label>
					                                <form:input path=""  id="mtr_rent9" name="mtr_rent9" type="text" class="form-control"  value="0" readonly="true"/>
				                                </div>
											</div>
											
											<div class="col-md-2">
												<div class="form-group">
													<label>&nbsp;</label>
					                                <form:input path=""  id="tot_amt9" name="tot_amt9" type="text" class="form-control"   readonly="true" value="0"/>
				                                </div>
											</div>
											
											<div class="col-md-1">
												<div class="form-group">
													<label>&nbsp;</label>
					                                <form:input path=""  id="penalty9" name="penalty9" type="text" class="form-control"   readonly="true" value="0"/>
				                                </div>
											</div>
											
											<div class="col-md-1">
												<div class="form-group">
													<label>&nbsp;</label>
					                                <form:input path="" id="rebate9" name="rebate9" type="text" class="form-control"   readonly="true" value="0"/>
				                                </div>
											</div>
											
											<div class="col-md-1">
												<div class="form-group">
													<label>&nbsp;</label>
											<button type="button" class="btn bg-teal btn-sm" data-toggle="modal" data-target="#modal_default2" id="onshow_callback1" style="float: right;">Reading</button>
											</div>
											</div>
											
										</div>
										
										
										
										<!-- Start 2073 Chaitra -->
										
										<div class="row">	
											
											<div class="col-md-2">
												<div class="form-group">
													<label>Open Balance&nbsp;</label>
					                                <form:input path="" id="magarrfc" name="magarrfc" type="text" class="form-control" value="0" onchange="showArrears();"/>
				                               </div>
											 </div>
											 
											 
											<div class="col-md-2">
												<div class="form-group">
													<label>2073 Chaitra&nbsp;&nbsp;<input type="checkbox"  id="id19" onClick="setValue(8)"></label>
					                                <input id="magafc" name="magafc" type="text" class="form-control" placeholder="Enter BillAmount" value="0"/>
				                                </div>
											</div>
											<form:input path="" hidden="true" id="monYearNep10" name="monYearNep10" value="207312" type="text"  />
											
											<div class="col-md-2">
												<div class="form-group">
													<label>&nbsp;</label>
					                                <form:input path="" id="wtr_charges10" name="wtr_charges10" type="text" class="form-control"   readonly="true" value="0"/>
				                                </div>
											</div>
											
											<div class="col-md-1">
												<div class="form-group">
													<label>&nbsp;</label>
					                                <form:input path="" id="swr_charges10" name="swr_charges10" type="text" class="form-control"    readonly="true" value="0"/>
				                                </div>
											</div>

											<div class="col-md-1">
												<div class="form-group">
													<label>&nbsp;</label>
					                                <form:input path=""  id="mtr_rent10" name="mtr_rent10" type="text" class="form-control"  value="0" readonly="true"/>
				                                </div>
											</div>
											
											<div class="col-md-2">
												<div class="form-group">
													<label>&nbsp;</label>
					                                <form:input path=""  id="tot_amt10" name="tot_amt10" type="text" class="form-control"   readonly="true" value="0"/>
				                                </div>
											</div>
											
											<div class="col-md-1">
												<div class="form-group">
													<label>&nbsp;</label>
					                                <form:input path=""  id="penalty10" name="penalty10" type="text" class="form-control"   readonly="true" value="0"/>
				                                </div>
											</div>
											
											<div class="col-md-1">
												<div class="form-group">
													<label>&nbsp;</label>
					                                <form:input path="" id="rebate10" name="rebate10" type="text" class="form-control"   readonly="true" value="0"/>
				                                </div>
											</div>
											
											<div class="col-md-1">
												<div class="form-group">
													<label>&nbsp;</label>
											<button type="button" class="btn bg-teal btn-sm" data-toggle="modal" data-target="#modal_default3" id="onshow_callback2" style="float: right;">Reading</button>
											</div>
											</div>
											
										</div>
										
										<!-- End 2073 Chaitra -->
										
										
										<div class="row">	
											
											<div class="col-md-2">
												<div class="form-group">
													<label>Open Balance&nbsp;</label>
					                                <form:input path="" id="magarrfcb" name="magarrfcb" type="text" class="form-control" value="0" onchange="showArrears();"/>
				                               </div>
											 </div>
											 
											 
											<div class="col-md-2">
												<div class="form-group">
													<label>2074 Baisak&nbsp;&nbsp;<input type="checkbox"  id="id20" onClick="setValue(9)"></label>
					                                <input id="magafcb" name="magafcb" type="text" class="form-control" placeholder="Enter BillAmount" value="0"/>
				                                </div>
											</div>
											<form:input path="" hidden="true" id="monYearNep11" name="monYearNep11" value="207401" type="text"  />
											
											<div class="col-md-2">
												<div class="form-group">
													<label>&nbsp;</label>
					                                <form:input path="" id="wtr_charges11" name="wtr_charges11" type="text" class="form-control"   readonly="true" value="0"/>
				                                </div>
											</div>
											
											<div class="col-md-1">
												<div class="form-group">
													<label>&nbsp;</label>
					                                <form:input path="" id="swr_charges11" name="swr_charges11" type="text" class="form-control"    readonly="true" value="0"/>
				                                </div>
											</div>

											<div class="col-md-1">
												<div class="form-group">
													<label>&nbsp;</label>
					                                <form:input path=""  id="mtr_rent11" name="mtr_rent11" type="text" class="form-control"  value="0" readonly="true"/>
				                                </div>
											</div>
											
											<div class="col-md-2">
												<div class="form-group">
													<label>&nbsp;</label>
					                                <form:input path=""  id="tot_amt11" name="tot_amt11" type="text" class="form-control"   readonly="true" value="0"/>
				                                </div>
											</div>
											
											<div class="col-md-1">
												<div class="form-group">
													<label>&nbsp;</label>
					                                <form:input path=""  id="penalty11" name="penalty11" type="text" class="form-control"   readonly="true" value="0"/>
				                                </div>
											</div>
											
											<div class="col-md-1">
												<div class="form-group">
													<label>&nbsp;</label>
					                                <form:input path="" id="rebate11" name="rebate11" type="text" class="form-control"   readonly="true" value="0"/>
				                                </div>
											</div>
											
											<div class="col-md-1">
												<div class="form-group">
													<label>&nbsp;</label>
											<button type="button" class="btn bg-teal btn-sm" data-toggle="modal" data-target="#modal_default4" id="onshow_callback3" style="float: right;">Reading</button>
											</div>
											</div>
											
										</div>
										
										
										
										
										
										
										
										
										
										
										
										
										
										
										
										
										
										
										
										
											
										
										<div class="row">	
											<div class="col-md-2">
												<div class="form-group">
													<label>&nbsp;</label>
					                               <br> <button type="button" class="btn bg-blue"  data-style="expand-right" data-spinner-size="20" onclick="return getCalValues();"><span class="ladda-label">Calculate</span></button>
				                                </div>
											</div>
											
											<div class="col-md-2">
												<div class="form-group">
													<label>&nbsp;</label>
					                                <form:input path="" id="tot_wc" name="tot_wc" type="text" class="form-control"   readonly="true"/>
				                                </div>
											</div>
											
											<div class="col-md-2">
												<div class="form-group">
													<label>&nbsp;</label>
					                                <form:input path="" id="tot_sc" name="tot_sc" type="text" class="form-control"    readonly="true"/>
				                                </div>
											</div>

											<div class="col-md-2">
												<div class="form-group">
													<label>&nbsp;</label>
					                                <form:input path=""  id="tot_mrent" name="tot_mrent" type="text" class="form-control"   readonly="true"/>
				                                </div>
											</div>
											
											<div class="col-md-2">
												<div class="form-group">
													<label>&nbsp;</label>
					                                <form:input path=""  id="tot_tamt" name="tot_tamt" type="text" class="form-control"   readonly="true"/>
				                                </div>
											</div>
											
											<div class="col-md-2">
												<div class="form-group">
													<label>&nbsp;</label>
					                                <form:input path=""  id="tot_pen" name="tot_pen" type="text" class="form-control"   readonly="true"/>
				                                </div>
											</div>
											
											<div class="col-md-2">
												<div class="form-group">
													<label>&nbsp;</label>
					                                <form:input path="" id="tot_reb" name="tot_reb" type="text" class="form-control"   readonly="true"/>
				                                </div>
											</div>
										</div>
										
									<br>
									<div class="row">
									<label class="control-label col-lg-2">Total Water Charges</label>
									<div class="col-lg-4">
										<div class="row">
											<div class="col-lg-6">
												<form:input path="" id="tot_water_Chrg" name="tot_water_Chrg" type="text" class="form-control" readonly="true"/>
											</div>

										</div>
									</div>
									</div>
									<br>
									<div class="row">
									<label class="control-label col-lg-2">Total SW Charges</label>
									<div class="col-lg-4">
										<div class="row">
											<div class="col-lg-6">
												<form:input path="" id="tot_sewerage_Chrg" name="tot_water_Chrg" type="text" class="form-control" readonly="true"/>
											</div>

										</div>
									</div>
									</div>
									<br>
									<div class="row">
									<label class="control-label col-lg-2">Total  Meter Rent Charges</label>
									<div class="col-lg-4">
										<div class="row">
											<div class="col-lg-6">
												<form:input path="" id="tot_Mrent_Chrg" name="tot_Mrent_Chrg" type="text" class="form-control" readonly="true"/>
											</div>

										</div>
									</div>
									</div>
									<br>
									
									<div class="row">
									<label class="control-label col-lg-2">Total  Arrears</label>
									<div class="col-lg-4">
										<div class="row">
											<div class="col-lg-6">
												<form:input path="" id="tot_arrears" name="tot_arrears" type="text" class="form-control" readonly="true"/>
											</div>

										</div>
									</div>
									</div>
									<br>
									
									
									<div class="row">
									<label class="control-label col-lg-2">Total Bill Amount</label>
									<div class="col-lg-4">
										<div class="row">
											<div class="col-lg-6">
												<form:input path="" id="tot_bill_amt" name="tot_bill_amt" type="text" class="form-control" readonly="true"/>
											</div>

										</div>
									</div>
									</div>
									<br>
									<div class="row">
									<label class="control-label col-lg-2">Total Penalty</label>
									<div class="col-lg-4">
										<div class="row">
											<div class="col-lg-6">
												<form:input path="" id="total_penal" name="total_penal" type="text" class="form-control" readonly="true"/>
											</div>

										</div>
									</div>
									</div>
									<br>
									<div class="row">
									<label class="control-label col-lg-2">Total Rebate</label>
									<div class="col-lg-4">
										<div class="row">
											<div class="col-lg-6">
												<form:input path="" id="total_rebate" name="total_rebate" type="text" class="form-control" readonly="true"/>
											</div>

										</div>
									</div>
									</div>
									<br>
									<br>	
									
									<div class="row">
									<label class="control-label col-lg-2">Total Payable</label>
									<div class="col-lg-4">
										<div class="row">
											<div class="col-lg-6">
												<form:input path="" id="tot_Payable" name="tot_Payable" type="text" class="form-control" readonly="true"/>
											</div>

										</div>
									</div>
									</div>
									
							<div class="text-center" id="addId">
								<button type="button" class="btn bg-blue"  data-style="expand-right" data-spinner-size="20" onclick="return clearButton();"><span class="ladda-label">Clear</span></button>
								
								<button type="submit" class="btn bg-blue"  data-style="expand-right" data-spinner-size="20" onclick="return validCheck();"><span class="ladda-label">Submit</span></button>
							</div>
					
							</fieldset>	
								</form:form>						
				</div>			
			</div>	
			
			
			<!-- Basic modal -->
				<div id="modal_default" class="modal fade">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal">&times;</button>
								<h5 class="modal-title">Enter Reading</h5>
							</div>
			<div class="modal-body">
				<div>
					<div class="col-md-12">
					<div class="col-md-2">
						<div class="form-group">
							<label>Last&nbsp;Read(KL)&nbsp;<font color="red">*</font></label>
							<input type="text" class="form-control" name="previous_reading"
								id="previous_reading1" placeholder="Previous Reading1"
								onchange="calculateConsumption();" value="0">
						</div>
					</div>

					<div class="col-md-2">
						<div class="form-group">
							<label>Present&nbsp;Read(KL)&nbsp;<font color="red">*</font></label>
							<input type="text" class="form-control" name="present_reading1"
								id="present_reading1" onchange="calculateConsumption();"
								value="0" placeholder="Present Reading">
						</div>
					</div>

					<div class="col-md-2">
						<div class="form-group">
							<label>Consumption(KL)</label> <input type="text"
								class="form-control" name="consumption1" id="consumption1"
								readonly="readonly" value="0" placeholder="Consumption">
						</div>
					</div>


					<div class="col-md-3" id="mc_statusdiv">
						<div class="form-group">
							<label>Meter Status&nbsp;<font color="red">*</font></label> <select
								data-placeholder="Select" class="select" id="mc_status1"
								name="mc_status1" required="required">
								<option value="" data-icon="icon-git-branch">Select</option>

								<c:forEach items="${observationList}" var="ob">
									<option value="${ob.id}">${ob.observationName}</option>
								</c:forEach>

							</select>

						</div>
					</div>

					<div class="col-md-1">
						<div class="form-group">
							<label>&nbsp;</label>
							<button type="button" class="btn bg-teal btn-ladda"
								data-style="expand-right" data-spinner-size="20"
								onclick="calculateBill();">
								<span class="ladda-label">Calculate</span>
							</button>
						</div>
					</div>
				</div>
			</div>
			</div>
			<div class="modal-footer">
								
							</div>
						</div>
					</div>
				</div>
				<!-- /basic modal -->	
				
				
				
				
				
				<!-- Basic modal -->
				<div id="modal_default2" class="modal fade">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal">&times;</button>
								<h5 class="modal-title">Enter Reading</h5>
							</div>

							<div class="modal-body">
								
								<div class="row">
									<div class="col-md-2">
										<div class="form-group">
											<label>Last&nbsp;Read(KL)&nbsp;<font color="red">*</font></label> <input type="text"
												class="form-control" name="previous_reading" id="previous_reading3" placeholder="Previous Reading3" onchange="calculateConsumption();" value="0">
										</div>
									</div>
					
									<div class="col-md-2">
										<div class="form-group">
											<label>Present&nbsp;Read(KL)&nbsp;<font color="red">*</font></label> <input type="text" class="form-control" name="present_reading3" id="present_reading3" onchange="calculateConsumption1();" value="0"
												placeholder="Present Reading">
										</div>
									</div>
					
									<div class="col-md-2">
										<div class="form-group">
											<label>Consumption(KL)</label> <input type="text" class="form-control" name="consumption3" id="consumption3" readonly="readonly" value="0"
												placeholder="Consumption">
										</div>
									</div>
									
									
									<div class="col-md-3" id="mc_statusdivmc">
										<div class="form-group">
											<label>Meter Status&nbsp;<font color="red">*</font></label>
												
												<select data-placeholder="Select" class="select" id="mc_status3" name="mc_status3" required="required">
												    <option value="" data-icon="icon-git-branch">Select</option>
													
													<c:forEach items="${observationList}" var="ob">
													<option value="${ob.id}">${ob.observationName}</option>
												   </c:forEach>
													
												</select>
												
										</div>
									</div>
									
									<div class="col-md-1">
										<div class="form-group">
											<label>&nbsp;</label> 
									     <button type="button" class="btn bg-teal btn-ladda"  data-style="expand-right" data-spinner-size="20" onclick="calculateBill1();"><span class="ladda-label" >Calculate</span></button>
										</div>
									</div>
									
									

									
									
								
									
					
					
								</div>
								
								
							</div>

							<div class="modal-footer">
								
							</div>
						</div>
					</div>
				</div>
				<!-- /basic modal -->	
				
				
				
				<!--Chaitra  -->
				
				
				<!-- Basic modal -->
				<div id="modal_default3" class="modal fade">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal">&times;</button>
								<h5 class="modal-title">Enter Reading</h5>
							</div>

							<div class="modal-body">
								
								<div class="row">
									<div class="col-md-2">
										<div class="form-group">
											<label>Last&nbsp;Read(KL)&nbsp;<font color="red">*</font></label> <input type="text"
												class="form-control" name="previous_reading3m" id="previous_reading3m" placeholder="Previous Reading" onchange="calculateConsumption2();" value="0">
										</div>
									</div>
					
									<div class="col-md-2">
										<div class="form-group">
											<label>Present&nbsp;Read(KL)&nbsp;<font color="red">*</font></label> <input type="text" class="form-control" name="present_reading3m" id="present_reading3m" onchange="calculateConsumption2();" value="0"
												placeholder="Present Reading">
										</div>
									</div>
					
									<div class="col-md-2">
										<div class="form-group">
											<label>Consumption(KL)</label> <input type="text" class="form-control" name="consumption3m" id="consumption3m" readonly="readonly" value="0"
												placeholder="Consumption">
										</div>
									</div>
									
									
									<div class="col-md-3" id="mc_statusdivmcc">
										<div class="form-group">
											<label>Meter Status&nbsp;<font color="red">*</font></label>
												
												<select data-placeholder="Select" class="select" id="mc_status3m" name="mc_status3m" required="required">
												    <option value="" data-icon="icon-git-branch">Select</option>
													
													<c:forEach items="${observationList}" var="ob">
													<option value="${ob.id}">${ob.observationName}</option>
												   </c:forEach>
													
												</select>
												
										</div>
									</div>
									
									<div class="col-md-1">
										<div class="form-group">
											<label>&nbsp;</label> 
									     <button type="button" class="btn bg-teal btn-ladda"  data-style="expand-right" data-spinner-size="20" onclick="calculateBill2();"><span class="ladda-label" >Calculate</span></button>
										</div>
									</div>
									
									

									
									
								
									
					
					
								</div>
								
								
							</div>

							<div class="modal-footer">
								
							</div>
						</div>
					</div>
				</div>
				<!-- /basic modal -->
				
				
					<div id="modal_default4" class="modal fade">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal">&times;</button>
								<h5 class="modal-title">Enter Reading</h5>
							</div>

							<div class="modal-body">
								
								<div class="row">
									<div class="col-md-2">
										<div class="form-group">
											<label>Last&nbsp;Read(KL)&nbsp;<font color="red">*</font></label> <input type="text"
												class="form-control" name="previous_reading3mb" id="previous_reading3mb" placeholder="Previous Reading" onchange="calculateConsumption2b();" value="0">
										</div>
									</div>
					
									<div class="col-md-2">
										<div class="form-group">
											<label>Present&nbsp;Read(KL)&nbsp;<font color="red">*</font></label> <input type="text" class="form-control" name="present_reading3mb" id="present_reading3mb" onchange="calculateConsumption2b();" value="0"
												placeholder="Present Reading">
										</div>
									</div>
					
									<div class="col-md-2">
										<div class="form-group">
											<label>Consumption(KL)</label> <input type="text" class="form-control" name="consumption3mb" id="consumption3mb" readonly="readonly" value="0"
												placeholder="Consumption">
										</div>
									</div>
									
									
									<div class="col-md-3" id="mc_statusdivmcc">
										<div class="form-group">
											<label>Meter Status&nbsp;<font color="red">*</font></label>
												
												<select data-placeholder="Select" class="select" id="mc_status3mb" name="mc_status3m" required="required">
												    <option value="" data-icon="icon-git-branch">Select</option>
													
													<c:forEach items="${observationList}" var="ob">
													<option value="${ob.id}">${ob.observationName}</option>
												   </c:forEach>
													
												</select>
												
										</div>
									</div>
									
									<div class="col-md-1">
										<div class="form-group">
											<label>&nbsp;</label> 
									     <button type="button" class="btn bg-teal btn-ladda"  data-style="expand-right" data-spinner-size="20" onclick="calculateBill2b();"><span class="ladda-label" >Calculate</span></button>
										</div>
									</div>
									
									

									
									
								
									
					
					
								</div>
								
								
							</div>

							<div class="modal-footer">
								
							</div>
						</div>
					</div>
				</div>
				
					
				<!-- End Chaitra -->
				
				
				
				
			<div id="modal_default1" class="modal fade">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal">&times;</button>
								<h5 class="modal-title">Bill Ledger History</h5>
							</div>

							<div class="modal-body">
								
								<table class="table datatable-basic table-bordered" id="popupPayment1">
									<thead>
										<tr>
											<th></th>
											<th>MY</th>
											<th>CR</th>
											<th>LR</th>
											<th>U</th>
											<th>OB</th>
											<th>WC</th>
											<th>SC</th>
											<th>MR</th>
											<th>NT</th>
											<th style="color: red;">PE</th>
											<th style="color: green;">RB</th>
											<th>Badj</th>
											<th>Padj</th>
											<th>paid</th>
										    <th>CB</th>
											<th>RNo</th>
											<th>RDate</th>
											<th>BillNo</th>
											
										</tr>
									</thead>
									<tbody id="viewPayHistotytbody1">
										
									</tbody>
								</table>
								
								
							</div>

							<div class="modal-footer">
								
							</div>
						</div>
					</div>
				</div>

<style>
.modal.fade .modal-dialog {
width: 1260px;
}
.modal-body {
    position: relative;
    padding: 0px;
}

.datatable-header, .datatable-footer {
    padding: 5px 20px 0 20px;
}

body {
    font-family: "Roboto", Helvetica Neue, Helvetica, Arial, sans-serif;
    font-size: 12px;
    line-height: 1.5384616;
    color: #333333;
}
</style>