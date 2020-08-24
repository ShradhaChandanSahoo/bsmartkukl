<%@include file="/WEB-INF/decorators/taglibs.jsp"%>
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>

<script>
$(document).ready(function(){   
	
	$('#cashCounterScreen').show();
	$('#cashCounter').addClass('active');
	
	var activeMod="${activeModulesNew}";
	var module="Cash Counter";
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

	

}); 

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

var fromConnectionNo=null;

function searchByAccno()
{
	if($('#fromConnectionNo').val().trim()=='')
		{
		$('#alertDiv').show();
	      $('#alertDiv').html('<div class="alert alert-danger alert-bordered"><button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button><span class="text" id="alert">Please enter From connection no.</span>');
	      return false;
		}
	
	
	 fromConnectionNo=$('#fromConnectionNo').val();
	 $("#loading").show();
	 
	$.ajax({
   	    type :"get",
        dataType :'json',
   	    url :"./searchByConnectionNo/"+fromConnectionNo,
 		async:false,
    	cache:false,
 		  success : function(response)
 		  {
	    		 var html2 = "";
	    	       		if(response.length==0)
	    	       			{
	    	       			  $('#alertDiv').show();
	    	     		      $('#alertDiv').html('<div class="alert alert-danger alert-bordered"><button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button><span class="text-semibold" id="alert">Connection No.does not exists.</span>');
	    	     		      $("#loading").hide(); 
	    	     		      return false;
	    	       			}
	    	       		else
	    	       			{
	    	       			$('#alertDiv').hide();
		    	       		 for(var j=0;j<response.length;j++)
					    		{		
					    		     
					    		     html2 += '<tr><td>'+(j+1)+'</td>';
					    		     html2 += '<td><a href="#" onclick=wrongPostingData("'+response[j][0]+'","'+response[j][8]+'","'+response[j][9]+'")>'+response[j][0]+'</a></td>';
					    		     html2 += '<td>'+response[j][1]+'</td>'; 
					    		     html2 += '<td>'+response[j][2]+'</td>';
					    		     html2 += '<td>'+response[j][9]+'</td>';
					    		     html2 += '<td>'+response[j][3]+'</td>';
					    		     html2 += '<td>'+response[j][4]+'</td>';
					    		     html2 += '<td>'+response[j][5]+'</td>';
					    		     html2 += '<td>'+response[j][6]+'</td>';
					    		     html2 += '<td>'+response[j][7]+'</td></tr>';
					    		}
	    	       			}
	    	    	  
		    		
	    	    loadSearchFilter1('cashDetailsTab',html2,'paymentsData');
	    	    $('#paymentsData').empty();
		    	$('#paymentsData').html(html2);
		    	$('#cashDetailsDiv').show(); 
		    	$("#loading").hide(); 
		    	
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

function wrongPostingData(connectionno,rdate,monthyear)
{
	
	var nepmonth="${monthyearNepLMY}";
	
	/* if(nepmonth!=monthyear){
		alert("Wrong Posting applicable for the Latest Ledger Month");
		return false;
	}else{ */
		
	
	$("#monthyearnep").val(monthyear);
	var currDate=moment(Date()).format('MM/DD/YYYY');
	
	
	$.ajax({
   	    type :"get",
        dataType :'json',
   	    url :"./wrongPostingData/"+connectionno+'/'+rdate,
 		async:false,
    	cache:false,
 		  success : function(response)
 		  {
 			 for(var j=0;j<response.length;j++)
	    		{
 				$('#fromConnectionNo').val(response[j][0]);
 				$('#receiptNo').val(response[j][1]);
 				$('#engRdate').val(response[j][2]);
 				$('#receiptAmnt').val('-'+response[j][5]);
 				$('#adjustmentAmnt').val(response[j][5]);
 				$('#engAdate').val(currDate);
 				$('#adjustmentNo').val(response[j][8]);
 				$('#payMode').val(response[j][4]);
 				$('#towards').val(response[j][7]);
 				$('#cdNo').val(response[j][3]);
 				$('#bankName').val(response[j][6]);
 				$('#cdDate').val(response[j][9]);
	    		}
 			 
 			document.getElementById('fromConnectionNo').readOnly = true;
 		  }
});

//}
}

 function transferAmount()
{
	 
	 if($('#fromConnectionNo').val().trim()=='')
		{
		$('#alertDiv').show();
	      $('#alertDiv').html('<div class="alert alert-danger alert-bordered"><button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button><span class="text" id="alert">Please enter From connection no.</span>');
	      return false;
		}
	 if($('#to_connection_no').val().trim()=='')
		{
		$('#alertDiv').show();
	      $('#alertDiv').html('<div class="alert alert-danger alert-bordered"><button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button><span class="text" id="alert">Please enter To connection no.</span>');
	      return false;
		}
	 if($('#fromConnectionNo').val()==$('#to_connection_no').val())
		 {
		   $('#alertDiv').show();
		   $('#alertDiv').html('<div class="alert alert-danger alert-bordered"><button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button><span class="text-semibold" id="alert">From Connection no. and To Connection no. should not be same.</span>');
		   return false;
		 }
	 if($('#remarks').val().trim()=='')
		{
		  $('#alertDiv').show();
	      $('#alertDiv').html('<div class="alert alert-danger alert-bordered"><button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button><span class="text" id="alert">Please enter Remarks</span>');
	      return false;
		}
	 else
		{
		 $('#alertDiv').hide();
		}
	 
	 
	
} 

 function clearButton(){
	 window.location.href="./wrongPosting";
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
 
 <div class="panel panel-flat">
	<div class="panel-body" id="wrongPostingDiv">
		<!-- <form class="form-horizontal" action="#">-->
		<div class="row" hidden="true" id="alertDiv"></div>
		
		
		
		<form:form action="./updateWrongPost" role="form"  modelAttribute="wrongPost" commandName="wrongPost" method="POST" id="wrongPost">
		
		
			<fieldset class="content-group">
				<legend class="text-bold">Wrong Posting</legend>

				<div class="row">
					<div class="col-md-4">
						<div class="form-group">
							<label>From Connection No.<font color="red">*</font></label> <form:input
								type="text" class="form-control" placeholder=""
								id="fromConnectionNo" name="fromConnectionNo" path="fromConnectionNo"></form:input>
						</div>
					</div>
					<div class="col-md-1"></div>
					<div class="col-md-4">
						<div class="form-group">
							<label>To Connection No.<font color="red">*</font></label> <form:input
								type="text" class="form-control" placeholder=""
								id="to_connection_no" name="to_connection_no" path="to_connection_no"></form:input>
						</div>
					</div>
					
					<div class="col-md-3">
						<div class="form-group">
							<label>Month Year<font color="red">*</font></label> <form:input
								type="text" class="form-control" placeholder=""
								id="monthyearnep" name="monthyearnep" path="monthyearnep"></form:input>
						</div>
					</div>
					
					
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="form-group">
							<label>Receipt No. <font color="red">*</font></label> <form:input
								type="text" class="form-control" placeholder="" id="receiptNo"
								name="receiptNo" path="receiptNo" readonly="true"></form:input>
						</div>
					</div>
					<div class="col-md-1"></div>
					<div class="col-md-4">
						<div class="form-group">
							<label>Adjustment No.</label> <form:input type="text"
								class="form-control" placeholder="" id="adjustmentNo"
								name="adjustmentNo" path="adjustmentNo" readonly="true"></form:input>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="form-group">
							<label>Receipt Date</label>
							<div class="input-group">
								<span class="input-group-addon "><i class="icon-calendar"></i></span>
								<form:input type="text" class="form-control" id="engRdate" name="engRdate" path="engRdate" readonly="true"></form:input>
							</div>
						</div>
					</div>
					<div class="col-md-1"></div>
					<div class="col-md-4">
						<div class="form-group">
							<label>Adjustment Date</label>
							<div class="input-group">
								<span class="input-group-addon "><i class="icon-calendar"></i></span>
								<form:input type="text" class="form-control daterange-single "
									placeholder="dd/MM/yyyy" id="engAdate" name="engAdate" path="engAdate" readonly="true"></form:input>
							</div>
						</div>
					</div>
					

				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="form-group">
							<label>Receipt Amount</label> <form:input type="text"
								class="form-control" placeholder="" id="receiptAmnt"
								name="receiptAmnt" path="receiptAmnt" readonly="true"></form:input>
						</div>
					</div>
					<div class="col-md-1"></div>
					<div class="col-md-4">
						<div class="form-group">
							<label>Adjustment Amount</label> <form:input type="text"
								class="form-control" placeholder="" id="adjustmentAmnt"
								name="adjustmentAmnt" path="adjustmentAmnt" readonly="true"></form:input>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-sm-9">
						<div class="form-group">
							<label>Remarks</label>
							<textarea class="form-control" placeholder="" id="remarks"
								name="remarks"></textarea>
						</div>
					
					</div>
					    <div class="col-md-4">
						<div class="text-center">
							<button type="button" class="btn bg-teal"
								style="text-align: left;" onclick="return searchByAccno();">
								View
							</button>
						</div>
						</div>
						
						<div class="col-md-2">
						<div class="text-center">
							<button type="button" class="btn bg-teal"
								style="text-align: left;" onclick="return clearButton();">
								Clear
							</button>
						</div>
						</div>
						
						
						<div class="col-md-1"></div>
						<div class="col-md-2">
						<div class="text-center">
							<button type="submit" class="btn bg-teal"
								onclick="return transferAmount()">
								Amount Transfer
							</button>
						</div>
					</div>
				</div>
			</fieldset>
			
			
			<div class="row" hidden="true">
				<div class="col-md-4">
					<div class="form-group">
						<input type="text" class="form-control" placeholder="" id="payMode" name="payMode"> 
						<input type="text" class="form-control" placeholder="" id="towards" name="towards"> <input type="text" class="form-control" placeholder="" id="cdNo" name="cdNo"> 
						<input type="text" class="form-control" placeholder="" id="cdDate" name="cdDate"> 
						<input type="text" class="form-control" placeholder="" id="bankName" name="bankName">
					</div>
				</div>
			</div>
			
		</form:form>
	</div>
</div>


<div class="panel panel-flat">
	<div class="panel-body" hidden="true" id="cashDetailsDiv"
		style="overflow: scroll;">
		<fieldset class="content-group">
			<div class="table-responsive">
				<div class="col-md-12">
					<table class="table datatable-basic table-bordered"
						id="cashDetailsTab">
						<thead class="bg-blue">
							<tr>
								<th>SL</th>
								<th>Con No</th>
								<th>Recpt No.</th>
								<th>RecptDt(ENG)</th>
								<th>MonthYear</th>
								<th>ChqNo</th>
								<th>PayMode</th>
								<th>Amt</th>
								<th>Bank</th>
								<th>Towards</th>
							</tr>
						</thead>

						<tbody id="paymentsData">
							<tr>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>

						</tbody>


					</table>
				</div>
			</div>
		</fieldset>

	</div>

</div>

<div id="loading" hidden="true" style="text-align: center;">
                   	<h3 id="loadingText">Loading..... Please wait. </h3> 
 	<img alt="image" src="./resources/images/loading.gif" style="width:3%;height: 3%;">
</div>

<style>

.datatable-header, .datatable-footer {
    padding: 0px 0px 0 0px;
}

.dataTables_filter {
    float: left;
    margin: 0 0 20px 20px;
    position: relative;
}
.dataTables_length > label {
    margin-bottom: 0;
}

</style>