<%@include file="/WEB-INF/decorators/taglibs.jsp"%>
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>

<script>
$(document).ready(function(){   
	$('#collectionIdUl').show();
	//$('#collectionIdUl').removeClass('hidden-ul');
	$('#collectionId,#wrongPosting').addClass('active');
	// $('#nepaliRdate').val('2016-12-07'); 
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

/* 	$('#engRdate').val('');
	$('#engAdate').val('');
	$('#wrongPostingDiv').find('input').attr('disabled','disabled');
	//$('#receiptNo','#adjustmentNo','#nepaliRdate','#engRdate','#nepaliAdate','#engAdate','#receiptAmnt','#adjustmentAmnt').attr('disabled','disabled');
	$('#fromConnectionNo').prop('disabled',false);
	$('#toConnectionNo').prop('disabled',false);
	$('#adjustmentAmnt').prop('disabled',false); */
	

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
var fromConnectionNo=null
function searchByAccno()
{
	if($('#fromConnectionNo').val().trim()=='')
		{
		$('#alertDiv').show();
	      $('#alertDiv').html('<div class="alert alert-danger alert-bordered"><button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button><span class="text" id="alert">Please enter From connection no.</span>');
	     // $("#alertDiv").fadeOut(3000);
	      return false;
		}
	
	
	 fromConnectionNo=$('#fromConnectionNo').val();
	
	$.ajax({
   	    type :"get",
        dataType :'json',
        //dataType: 'text',
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
	    	     		      return false;
	    	       			}
	    	       		else
	    	       			{
	    	       			$('#alertDiv').hide();
		    	       		 for(var j=0;j<response.length;j++)
					    		{		
					    		     
					    		     html2 += '<tr><td>'+(j+1)+'</td>';
					    		     html2 += '<td><a href="#" onclick=wrongPostingData("'+response[j][0]+'","'+response[j][8]+'")>'+response[j][0]+'</a></td>';
					    		     html2 += '<td>'+response[j][1]+'</td>'; 
					    		     html2 += '<td>'+response[j][2]+'</td>';
					    		     html2 += '<td>'+response[j][3]+'</td>';
					    		     html2 += '<td>'+response[j][4]+'</td>';
					    		     html2 += '<td>'+response[j][5]+'</td>';
					    		     html2 += '<td>'+response[j][6]+'</td>';
					    		     html2 += '<td>'+response[j][7]+'</td></tr>';
					    		}
	    	       			}
	    	    	  
		    		
	    	      // loadSearchAndFilter('cashDetailsTab')
	    	        $('#paymentsData').empty();
		    	$('#paymentsData').html(html2);
		    	$('#cashDetailsDiv').show(); 
		    	
 		  }
});
}

function wrongPostingData(connectionno,rdate)
{
	
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
 				$('#receiptAmnt').val(response[j][5]);
 				//$('#adjustmentAmnt').val(response[j][5]);
 				$('#engAdate').val(currDate);
 				$('#adjustmentNo').val(response[j][8]);
 				$('#payMode').val(response[j][4]);
 				$('#towards').val(response[j][7]);
 				
 				$('#cdNo').val(response[j][3]);
 				$('#bankName').val(response[j][6]);
 				$('#cdDate').val(response[j][9]);
 				
	    		}
 		  }
});
	return false;
}

 function trnasferAmount()
{
	 
	 if($('#fromConnectionNo').val().trim()=='')
		{
		$('#alertDiv').show();
	      $('#alertDiv').html('<div class="alert alert-danger alert-bordered"><button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button><span class="text" id="alert">Please enter From connection no.</span>');
	     // $("#alertDiv").fadeOut(3000);
	      return false;
		}
	 
	 //else if ()
	 alert($('#receiptNo').val());
	 
	/*  var token = $("meta[name='_csrf']").attr("content");
	 var header = $("meta[name='_csrf_header']").attr("content");
	 $('#wrongPostingDiv').find('input').attr('disabled',false);
	 $.ajax({
	   	    type :"post",
	        dataType :'text',
	        data :$('#wrongPost').serialize(),
	   	    url :"./updateCashAdjustment",
	 		async:false,
	    	cache:false,
	    	beforeSend: function(xhr) {
	            xhr.setRequestHeader(header, token);
	        },
	 		  success : function(response)
	 		  {
	 			 if(response=='success')
	 				 {
	 				  $('#alertDiv').show();
	 			      $('#alertDiv').html('<div class="alert alert-success alert-bordered"><button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button><span class="text-semibold" id="alert">Cash Updated successfully.</span>');
	 			       return false;
	 				 }
	 		  }
	}); */
	 
	 /* $('#wrongPostingDiv').find('input').attr('disabled',true);
	 $('#fromConnectionNo').prop('disabled',false);
		$('#toConnectionNo').prop('disabled',false);
		$('#adjustmentAmnt').prop('disabled',false);
	 searchByAccno(); */
} 


</script>
<div class="panel panel-flat">
	<div class="panel-body" id="wrongPostingDiv">
		<!-- <form class="form-horizontal" action="#">-->
		<div class="row" hidden="true" id="alertDiv"></div>
		<form:form action="./updateCashAdjustment" role="form"  modelAttribute="cashAdjApprove" commandName="cashAdjApprove" method="POST" id="cashAdjApprove">
			<fieldset class="content-group">
				<legend class="text-bold">Cash Adjustment</legend>

				<div class="row">
						<div class="form-group">
						<div class="col-md-12">
						<div><label>Connection No.<font color="red">*</font></label></div>
							 <div class="col-md-4">
							 <form:input
								type="text" class="form-control" placeholder=""
								id="fromConnectionNo" name="fromConnectionNo" path="fromConnectionNo"/>
						</div>
						<div class="col-md-1"></div>
						<div class="col-md-4">
						
							<button type="button" class="btn bg-teal"
								style="text-align: left;" onclick="return searchByAccno();">
								View<i class="icon-arrow-right14 position-right"></i>
							</button>
						
					</div>
					</div>
					</div>
				</div>
				<br>
				<div class="row">
					<div class="col-md-4">
						<div class="form-group">
							<label>Receipt No. <font color="red">*</font></label> <form:input
								type="text" class="form-control" placeholder="" id="receiptNo"
								name="receiptNo" path="receiptNo"/>
						</div>
					</div>
					<div class="col-md-1"></div>
					<div class="col-md-4">
						<div class="form-group">
							<label>Adjustment No.</label> <form:input type="text"
								class="form-control" placeholder="" id="adjustmentNo"
								name="adjustmentNo" path="adjustmentNo"/>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="form-group">
							<label>Receipt Date</label>
							<div class="input-group">
								<span class="input-group-addon "><i class="icon-calendar"></i></span>
								<form:input type="text" class="form-control daterange-single"
									placeholder="dd/MM/yyyy" id="engRdate" name="engRdate" path="engRdate" />
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
									placeholder="dd/MM/yyyy" id="engAdate" name="engAdate" path="engAdate"/>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="form-group">
							<label>Receipt Amount</label> <form:input type="text"
								class="form-control" placeholder="" id="receiptAmnt"
								name="receiptAmnt" path="receiptAmnt"/>
						</div>
					</div>
					<div class="col-md-1"></div>
					<div class="col-md-4">
						<div class="form-group">
							<label>Adjustment Amount</label> <form:input type="text"
								class="form-control" placeholder="" id="adjustmentAmnt"
								name="adjustmentAmnt" path="adjustmentAmnt"/>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-sm-9">
						<div class="form-group">
							<label>Remarks</label>
							<form:textarea class="form-control" placeholder="" id="fromRemarks"
								name="fromRemarks" path="fromRemarks"/>
						</div>
						
						<!-- <div class="text">
							<font color="red">*</font><b>Required Fields</b>
						</div> -->
					</div>
					
				</div>
				
				<div class="col-sm-9">
						<div class="text-center">
							<button type="submit" class="btn bg-teal"
								onclick="return trnasferAmount()">
								Adjust Amount<i class="icon-arrow-right14 position-right"></i>
							</button>
						</div>
				</div>
			</fieldset>
			<%-- <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/> --%>
		</form:form>
	</div>
</div>
<div class="panel panel-flat">
	<div class="panel-body" hidden="true" id="cashDetailsDiv" style="overflow: scroll;">
		<fieldset class="content-group">
			<!-- <legend class="text-bold">Customer Approval</legend> -->
			<div class="col-md-12">
				<div class="table-responsive">
					
					<table class="table datatable-basic table-bordered"
						id="cashDetailsTab">
						<thead class="bg-teal-600">
							<tr>
								<th>SL</th>
								<th>Con No</th>
								<th>Recpt No.</th>
								<th>RecptDt(ENG)</th>
								<th>ChqNO</th>
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
							</tr>

						</tbody>


					</table>
				</div>
				
			</div>
		</fieldset>

	</div>

</div>

<style>

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
}

</style>
