<%@include file="/WEB-INF/decorators/taglibs.jsp"%>
<script type="text/javascript">
			$(document).ready(function()
			{
				  <c:forEach var="elem" items="${activeModules}">
				  	var ac = '<c:out value="${elem.module_id}"></c:out>';
				  
				  	document.getElementById(ac).style.display='block';
				  </c:forEach>
			}); 
</script>

		<!-- Page content -->
			
			<div class="sidebar sidebar-main sidebar-default">
				<div class="sidebar-content">

					<!-- Main navigation -->
					<div class="sidebar-category sidebar-category-visible">

						<div class="category-content no-padding">
							<ul class="navigation navigation-main navigation-accordion">

							
							<!-- Start New -->
							
								<li id="dashboard" style="display: none;" class=""><a href="./dashboard"><i class="icon-home4"></i> <span>Dashboard</span></a></li>
								
								<li id="cordashboard" style="display: none;" class=""><a href="./corporateDashboard1"><i class="icon-home4"></i> <span>Corporate Dashboard</span></a></li> 
							
							     <li id="usermanagementId" style="display: none;" class="">
									<a href="#"><i class="glyphicon glyphicon-user"></i> <span>User Management</span></a>
									<ul id="umchild">
										<li><a href="./usertype">User Type</a></li>
										<li><a href="./userRoles">Modules</a></li>
										<li><a href="./userdesignation">User Designation</a></li>
										<li><a href="./usermaster">User Creation</a></li>
										
										<li><a href="./resetUsersPwd">Reset Users Password</a></li>
									</ul>
								</li>
								
								<li id="userLogModule" style="display: none;" class="">
									<a href="#"><i class="glyphicon glyphicon-thumbs-up"></i><span>User Activity</span></a>
									<ul id="userLogModuleScreen">
										<li id="userLogId" ><a href="./userLog"><i class="icon-copy"></i> <span>User Log</span></a></li>	
									</ul>
								</li>
								
								<li id="administrationModule" style="display: none;" class="">
									<a href="#"><i class="icon-puzzle4"></i><span> Administration </span></a>
									<ul id="administrationScreens">
									
									
									    
										<li><a href="./sqlEditor">SQL Editor</a></li>
										<li><a href="./wardDetails">Ward Details</a></li>
										<li><a href="./counterDetails">Counter Details</a></li>
										<li><a href="./tariffRateMaster">Tariff Rate Master</a></li>
										<li><a href="./muncipalityDetails">Municipality Details</a></li>
										<li><a href="./bankDetails">Bank Details</a></li>
										<li><a href="./branchDetails">Branch Details</a></li>
										<li><a href="./disconnectionRules">Disconnection Rules</a></li>
										
									</ul>
								</li>
								
								 <li id="masterDataEntry" style="display: none;" class="">
									<a href="#"><i class="glyphicon glyphicon-user"></i> <span>Master Data Entry</span></a>
									<ul id="ummdentry">
										<li><a href="./consumerMaster">Customer Master</a></li>
										<li><a href="./consumerHistory">Customer History</a></li>
									</ul>
								</li>
								
								<li id="customerHistory" style="display: none;" class="">
									<a href="#"><i class="glyphicon glyphicon-user"></i> <span>Customer History</span></a>
									<ul id="customerHistoryScreen">
										<li><a href="./consumerHistory">Customer History</a></li>
									</ul>
								</li>
								
								
								<li id="masterDataModification" style="display: none;" class="">
									<a href="#"><i class="glyphicon glyphicon-user"></i> <span>Master Data Modification</span></a>
									<ul id="ummdmodify">
										<li><a href="./consumerTransfer">Customer Transfer</a></li>
										<li><a href="./ownerShipChangeReq">Ownership Change</a></li>
										<li><a href="./sewageChange">Sewage Change</a></li>
										
									</ul>
								</li>
								
								<li id="masterDataModification1" style="display: none;" class="">
									<a href="#"><i class="glyphicon glyphicon-user"></i> <span>Master Data Modification</span></a>
									<ul id="ummdmodify1">
										<li><a href="./meterChangeDetails">Meter Change</a></li>
										<li><a href="./conTypeChangeReq">Connection Type Change</a></li>
										<!-- <li><a href="./conSizeChangeReq">Connection Size Change</a></li> -->
										<!-- <li><a href="./holeChange">Hole Change</a></li> -->
									</ul>
								</li>
								
								<li id="billingObservation" style="display: none;" class="">
									
									<a href="#"><i class="icon-meter2"></i> <span>Billing Observations </span></a>
									<ul id="billingObservationScreen">
										<li><a href="./observationDetails"><span>Observation Master</span></a></li>
									</ul>
								</li>
								
								
								<li id="billingManagement" style="display: none;" class="">
									<a href="#"><i class="glyphicon glyphicon-th"></i><span>Monthly Billing</span></a>
									<ul id="billingscreens">
										<li><a href="./billCorrection"><span>New Bill/Bill Correction</span></a></li>
										<li><a href="./readingEntry"><span>Bulk Billing Metered</span></a></li>
										<li><a href="./bulkBillUnmetered"><span>Bulk Billing UnMetered</span></a></li>
										<li><a href="./missedBillsReport"><span>Missed Record</span></a></li>
									    <!-- <li><a href="./blankRecordBill">Blank Record</a></li> -->
									</ul>
								</li>
								
								<li id="monthEnd" style="display: none;" class="">
									<a href="#"><i class="glyphicon glyphicon-th"></i><span>Month End Process</span></a>
									<ul id="monthEndScreen">
										<li><a href="./monthEndProcess"><span>Month End Process</span></a></li>
									</ul>
								</li>
								
								
								<li id="billPrint" style="display: none;" class="">
									<a href="#"><i class="glyphicon glyphicon-th"></i><span>Bill Print</span></a>
									<ul id="billPrintScreen">
										
										<li><a href="./billPrint"><span>Bill Print</span></a></li>
										
									</ul>
								</li>
								
								<li id="arrearPenaltyCorrection" style="display: none;" class="">
									<a href="#"><i class="glyphicon glyphicon-adjust"></i><span>Arrears Corr/Adj</span></a>
									<ul id="arrearsCorrectionScreen">
										<li><a href="./arrearsCorrection">Arrears Correction</a></li>
									</ul>
								</li>
								
								<li id="configuration" style="display: none;" class="">
									<a href="#"><i class="glyphicon glyphicon-folder-close"></i> <span>Configuration</span></a>
									<ul id="configurationScreen">
										<li><a href="./cashCounterConfiguration">Counter Configuration</a></li>
									</ul>
								</li> 
								
								
								<li id="cashCounter" style="display: none;" class="">
									<a href="#"><i class="glyphicon glyphicon-folder-close"></i> <span>Cash Counter</span></a>
									<ul id="cashCounterScreen">
										<c:if test = "${userbdesignation=='Counter Incharge' || userbdesignation=='Revenue Incharge' || userbdesignation=='Developer' || userbdesignation=='Asst Revenue Incharge'}">
										<li><a href="./receiptCancel">Cancel Receipt</a></li>
										<li><a href="./misReceiptCancel">Cancel Misc Receipt</a></li>
										</c:if>
										
										<li><a href="./closeAdminReport">Day Close Report</a></li>
										<li id="wrongPosting"><a href="./wrongPosting">Wrong Posting</a></li>
										<!-- <li id="cashAdjustment"><a href="./cashAdjustment">Cash Adjustment</a></li> -->
										<!-- <li id="cashDeposit"><a href="./viewCashDepositDetails">Deposits</a></li> -->
										<li id="cashSearch"><a href="./viewCashSearch">Cash Search</a></li>
										<li id="checkBounce"><a href="./viewChequeBounce" >Cheque Bounce</a></li>
									</ul>
								</li>  
								
								
								<li id="disconnReconnModule" style="display: none;" class="">
									<a href="#"><i class="icon-alignment-unalign"></i><span>Reconnection/Disconnection</span></a>
									<ul id="disconnReconnScreens">
										<li><a href="./disConnectionEntry">Disconnection Entry</a></li>
										<li><a href="./reConnection">Reconnection</a></li>
									</ul>
								</li>
								
								
								<li id="billingEntrytemp" style="display: none;" class="">
									<a href="#"><i class="glyphicon glyphicon-thumbs-up"></i> <span>Customer Billing Entry</span></a>
									<ul id="billingEntryTempScreen">
										<li><a href="./consumerBillingEntry">Customer Billing Entry(Temporary)</a></li>
									</ul>
								</li>
								
								<li id="arrearsRecal" style="display: none;" class="">
								<c:if test = "${userName=='s_admin'}"> 
									<a href="#"><i class="glyphicon glyphicon-thumbs-up"></i> <span>Arrears ReCalculation</span></a>
									<ul id="arrearsRecalScreen">
										<li><a href="./recalculate">ReCalculate Arrears</a></li>
									</ul>
								</c:if>
								</li>
								
								<li id="ledgerVerification" style="display: none;" class="">
									<a href="#"><i class="glyphicon glyphicon-thumbs-up"></i> <span>Ledger Verification</span></a>
									<ul id="ledgerVerificationScreen">
										<li><a href="./ledgerVerification"><span>Ledger Verification Report</span></a></li>
									</ul>
								</li>
								
								<li id="adjvocApproval" style="display: none;" class="">
									<a href="#"><i class="glyphicon glyphicon-thumbs-up"></i> <span>Adjustment Voucher</span></a>
									
									<ul id="adjvocApprovalScreen">
										<li><a href="./adjustmentVoucher">Adjustment Voucher</a></li>
									</ul>
								</li>
								
								
								<li id="routePaln" style="display: none;" class="">
									<a href="#"><i class="glyphicon glyphicon-th-list"></i><span>Route Plan</span></a>
									<ul id="routePalnScreen">
									<li><a href="./routePlanGeneration">Route Plan Generation</a></li>
									
									</ul>
								</li>
								
								<li id="meterManagement" style="display: none;" class="">
									<a href="#"><i class="icon-meter2"></i><span>Metering</span></a>
									<ul id="meteringscreens">
										<li><a href="./mrDetails"><span>Meter Reader Details</span></a></li>
										<li><a href="./wardToMr"><span>Assign Meter Reader</span></a></li>
										<li><a href="./meterDetails"><span>Meter Details</span></a></li>
										<li><a href="./meterChangeDetails"><span>Meter Change Details</span></a></li>
									</ul>
								</li>
									
								
								<li id="masterApproval"style="display: none;" class="">
									<a href="#"><i class="glyphicon glyphicon-thumbs-up"></i> <span>Master Approval</span></a>
									<ul id="masterApprovalScreen">
										<li><a href="./consumerApproval">Customer Approval</a></li>
										<!-- <li><a href="./meterChangeApproval">Meter Change Approval</a></li> -->
										<li><a href="./conTypeChangeApprove">Connection Type Approval</a></li>
										<li><a href="./ownerShipApprove">Ownership Change Approval</a></li>
										<li><a href="./newConnectionApproval">New Connection Approval</a></li>
										<li><a href="./connectionApproval">Reconnection/DisConnection Approval</a></li>
										<li><a href="./sewageApproval">Sewage Change Approval</a></li>
									</ul>
								</li> 
								
								<li id="bilapprovalModule" style="display: none;" class="">
									<a href="#"><i class="glyphicon glyphicon-thumbs-up"></i><span>Bill Approval</span></a>
									<ul id="bapapprovalScreen">
										<li><a href="./billApproval"><span>Bill Correction Approval</span></a></li>
										
									</ul>
								</li>
								
								
								
								
								<li id="arrearsPenaltyModule" style="display: none;" class="">
									<a href="#"><i class="glyphicon glyphicon-thumbs-up"></i><span>Arrears/Penalty Adjustment</span></a>
									<ul id="arrearsPenaltyModuleScreen">
										<li><a href="./billPenaltyAdjApprove">Bill/Penalty Adj Approve</a></li>
									</ul>
								</li>
								<li id="arrearsPenaltyTrancModule" style="display: none;" class="">
									<a href="#"><i class="glyphicon glyphicon-thumbs-up"></i><span>Arrears/Penalty Adjustment</span></a>
									<ul id="arrearsPenaltyModuleTrancScreen">
										<li><a href="./billPenaltyAdjTrancApprove">Transaction Approve</a></li>
									</ul>
								</li>
								
								
								<li id="arrearsPenaltyCorrectionApproval" style="display: none;" class="">
									<a href="#"><i class="glyphicon glyphicon-thumbs-up"></i><span>Arrears Correction Approval</span></a>
									<ul id="arrearsPenaltyCorrectionScreen">
										<li><a href="./billPenaltyCorrApprove">Arrears Correction Approve</a></li>
									</ul>
								</li>
								<!-- Board Correction Approval -->
								
								<li id="boardCorrectionApproval" style="display: none;" class="">
									<a href="#"><i class="glyphicon glyphicon-thumbs-up"></i><span>Board Correction Approval</span></a>
									<ul id="boardCorrectionApprovalScreen">
										<li><a href="./boardCorrApprove">Board Correction Approve</a></li>
									</ul>
								</li>
								<!-- Board Correction Approval -->
								<li id="boardAdjApproveModule" style="display: none;" class="">
									<a href="#"><i class="glyphicon glyphicon-thumbs-up"></i><span>Board Adj Approve</span></a>
									<ul id="boardAdjApproveModuleScreen">
										<li><a href="./boardAdjApprove">Adjustment Approve</a></li>
									</ul>
								</li>
								<li id="boardAdjApproveTransModule" style="display: none;" class="">
									<a href="#"><i class="glyphicon glyphicon-thumbs-up"></i><span>Board Adj Trans Approve</span></a>
									<ul id="boardAdjApproveTransModuleScreen">
										<li><a href="./boardAdjTransApprove">Transaction Approve</a></li>
									</ul>
								</li>
							
								<li id="oldReports" style="display: none;" class="">
									<a href="#"><i class="glyphicon glyphicon-thumbs-up"></i><span>Old Reports</span></a>
									<ul id="oldReportsScreen">
										<li id="reports" ><a href="./viewReports"><i class="icon-copy"></i> <span>Reports</span></a></li>	
									</ul>
								</li>
								
								<li id="masterReports" style="display: none;" class="">
									<a href="#"><i class="glyphicon glyphicon-th-list"></i> <span>Master Reports</span></a>
									<ul id="masterReportsScreen">
										<li><a href="./consumerListReport"><span>Customer List</span></a></li>
										<li><a href="./WardCountReport"><span>Ward Wise Customers</span></a></li>
										<li><a href="./holeBlockReport">Hole Block Report</a></li>
										<li><a href="./conTypeReport">Connection Type Report</a></li>
										<li><a href="./ownerShipChangeReport">Ownership Change Report</a></li>
										<li><a href="./conTypeChngReport">Con Type Change Report</a></li>
										<li><a href="./categoryTypeReport">Con Category Report</a></li>
										<li><a href="./newConnectionReport">New Connection Report</a></li>
										
										<li><a href="./sewageChangedReprots">Sewage Changed Report</a></li>
										
										<li><a href="./categoryNewConnectionReport">Category New Connection Report</a></li>
									</ul>
								</li>
								
								<li id="reportsbilling" style="display: none;" class="">
									<a href="#"><i class="glyphicon glyphicon-th-list"></i><span>Billing Reports</span></a>
									<ul id="umbillreport">
										<li><a href="./categoryWiseSalesReport"><span>Category Wise Billing Report</span></a></li>
										<li><a href="./wardWiseSalesReport"><span>Ward Wise Billing Report</span></a></li>
										<li><a href="./wardWiseCustomerBillingReport"><span>Ward Wise Customer's Billing</span></a></li>
										<li><a href="./meterReadingListReport"><span>Meter Reading List</span></a></li>
										<li><a href="./consumerBalanceReport"><span>Customer Balance Report</span></a></li>
										<li><a href="./wardWiseMrReaderReport"><span>Ward Wise Meter Reader Report</span></a></li>
										<li><a href="./ledgerVerification"><span>Ledger Verification Report</span></a></li>
										<li><a href="./abnormalBillingReport"><span>Abnormal Billing Report</span></a></li>
										
									</ul> 
								</li>
								
								<li id="meteringReports" style="display: none;" class="">
									<a href="#"><i class="glyphicon glyphicon-th-list"></i><span>Metering Reports</span></a>
									<ul id="meteringReportsScreen">
										
										<li><a href="./monthlyObservationReport"><span>Monthly Observation Report</span></a></li>
										<li><a href="./meterReadingListReport"><span>Meter Reading List</span></a></li>
										<li><a href="./wardWiseMrReaderReport"><span>Ward Wise Meter Reader Report</span></a></li>
										<li><a href="./missedBillsReport"><span>Missed Bills Report</span></a></li>
										<li><a href="./custObservation"><span>Customer Observation Report</span></a></li>
										<li><a href="./meterChangeDetailsReport"><span>Meter Change Details Report</span></a></li>
									</ul>
								</li>
								
								<li id="collectionsReports" style="display: none;" class="">
									<a href="#"><i class="glyphicon glyphicon-th-list"></i><span>Collection Reports</span></a>
									<ul id="collectionsReportsScreen">
										<li><a href="./detailedCashCollReport"><span>Detailed Cash Collection Report</span></a></li>
										<c:if test = "${branchcode=='1115' || branchcode=='1113'|| branchcode=='1110'}"> 
										<li><a href="./cashCollectionList"><span>Cash Collection upto 2064/CMY</span></a></li>
										<li><a href="./cashCollectiontth">Collection >=10000 Report</a></li>
										</c:if>
										<li><a href="./dailyRevenueReport"><span>Ward Wise Daily Revenue Report</span></a></li>
										<li><a href="./revenueReport"><span>Daily Revenue Report</span></a></li>
										<li><a href="./counterRevenueReport"><span>Counter Wise Revenue Report</span></a></li>
										<li><a href="./dailyCashCollectionReport">Monthly Collection Report</a></li>
										<li><a href="./multiPayment">Multiple Payment/Big Amount</a></li>
										<li><a href="./categoryCollectionReport">Category Collection Report</a></li>
										<li><a href="./newMonthlySalesReport"><span>Monthly Sales Summary Report</span></a></li>
										<li><a href="./categoryMonthlySalesReport">Category Monthly Sales Report</a></li>
										<%-- <c:if test = "${branchcode=='1114' || branchcode=='1111'}"> 
											<li><a href="./monthlySalesSummaryReport"><span>Monthly Sales Summary Report</span></a></li>
										</c:if> --%>
										<li><a href="./monthlyAdjustmentReport">Billing Adjustment Report</a></li>
										
										<li><a href="./boardBalAdjCorrReport">Board Balance Adjustment/Correction Report</a></li>
										<c:if test = "${branchcode=='1114' || branchcode=='1111'|| branchcode=='1112' || branchcode=='1116' || branchcode=='1118'|| branchcode=='1119'|| branchcode=='1117'}"> 
										<li><a href="./categoryBoardCollectionReport"><span>Board Collection Report</span></a></li>
										<li><a href="./detailedBoardCollReport"><span>Detailed Board Coll Report</span></a></li>
										<li><a href="./boardSalesReport"><span>Board Sales Report</span></a></li>
										</c:if>
										<li><a href="./cancelReceiptReport">Cancel Receipt Report</a></li>
										<li><a href="./advanceCollectionReportNew">Advance Collection Report</a></li>
										<li><a href="./detailedMiscCollReport"> Detailed Misc Collection Report</a></li>
										<li><a href="./miscellaneousCollectionReport">Miscellaneous Collection Report</a></li>
										<!-- <li><a href="./customerWiseAdvCollReport">Customer Wise Advance Collection Report</a></li> -->
									</ul>
								</li>
								
								<li id="collectionsReportsCashier" style="display: none;" class="">
									<a href="#"><i class="glyphicon glyphicon-th-list"></i><span>Collection Reports</span></a>
									<ul id="collectionsReportsCasheirSc">
										<li><a href="./detailedCashCollReport"><span>Detailed Cash Collection Report</span></a></li>
										<li><a href="./dailyRevenueReport"><span>Ward Wise Daily Revenue Report</span></a></li>
										<li><a href="./revenueReport"><span>Daily Revenue Report</span></a></li>
										<li><a href="./dailyCashCollectionReport">Monthly Collection Report</a></li>
										<li><a href="./cancelReceiptReport">Cancel Receipt Report</a></li>
										<li><a href="./detailedMiscCollReport"> Detailed Misc Collection Report</a></li>
										<li><a href="./miscellaneousCollectionReport">Miscellaneous Collection Report</a></li>
										<li><a href="./boardRevenueReport">Board Daily Revenue Report</a></li>
									</ul>
								</li>
								
								
								
								
							
								<!-- End New  -->
								
								
								
						</ul>

						</div>
						
						
						
						
					</div>
					<!-- /main navigation -->

				</div>
			</div>
			<!-- /main sidebar -->
	
	<style>
	
	.navigation {
	    list-style: outside none none;
	    margin: 0;
	    padding: 0 !important;
	    position: relative;
	 }
	
	.sidebar-default .navigation > li.active > a, .sidebar-default .navigation > li.active > a:hover, .sidebar-default .navigation > li.active > a:focus {
    	background-color: #2196F3 !important;
    	color: #ffffff;
    }

	</style>
