<%@include file="/WEB-INF/decorators/taglibs.jsp"%>

<!-- Page header -->
	<div class="page-header">
		<div class="breadcrumb-line">
			<%-- <ul class="breadcrumb">
				<li><a href="./dashboard"><i class="icon-home2 position-left"></i> Home</a></li>
				<c:if test="${not empty mainHead}">
					<li><a onclick="reload()"><i class="position-left"></i>${mainHead}</a></li>
				</c:if>
				<c:if test="${not empty mainHead}">
					<li><a onclick="reload()"><i class="position-left"></i>${childHead1}</a></li>
				</c:if>	
				<li class="active">${childHead2}</li>
			</ul> --%>
		</div>

		<div class="page-header-content">
			<br/>

			<div class="heading-elements">
			<div class="btn-group btn-group-justified">
								<a href="./cashCounterPayments" class="btn bg-slate-700">Cash Payments</a>
								<!-- <a href="./cancelReceipt" class="btn bg-slate-700">Cancel Receipt</a> -->
								<a href="./duplicateReceipt" class="btn bg-slate-700">Duplicate Receipt</a>
								<a href="./cashCounterDayClose" class="btn bg-slate-700">Cash Counter Day Close</a>
								<a href="./dayClosureReport" class="btn bg-slate-700">Day Close Report</a>
								<a href="./newConnectionCounter" class="btn bg-slate-700">New Connection Counter</a>
								<a href="./cashCounterCustomerHistory" class="btn bg-slate-700">Customer History</a>
								<a href="./cashCounterLogout" class="btn bg-slate-700">Cash Counter Logout</a>
								
							</div>
			</div>
		</div>
	</div>
	<!-- /page header -->