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

			<!-- <ul class="breadcrumb-elements">
				<li><a href="#"><i class="icon-comment-discussion position-left"></i> Support</a></li>
				<li class="dropdown">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown">
						<i class="icon-gear position-left"></i>
						Settings
						<span class="caret"></span>
					</a>

					<ul class="dropdown-menu dropdown-menu-right">
						<li><a href="#"><i class="icon-user-lock"></i> Account security</a></li>
						<li><a href="#"><i class="icon-statistics"></i> Analytics</a></li>
						<li><a href="#"><i class="icon-accessibility"></i> Accessibility</a></li>
						<li class="divider"></li>
						<li><a href="#"><i class="icon-gear"></i> All settings</a></li>
					</ul>
				</li>
			</ul> -->
		</div>

		<div class="page-header-content">
			<%-- <div class="page-title">
				<h4><i class="icon-arrow-left52 position-left"></i> ${childHead1} - ${childHead2}</h4>
			</div> --%>
			<br/>

			<div class="heading-elements">
				<!-- <div class="heading-btn-group">
					<a href="#" class="btn btn-link btn-float has-text"><i class="icon-bars-alt text-primary"></i><span>Statistics</span></a>
					<a href="#" class="btn btn-link btn-float has-text"><i class="icon-calculator text-primary"></i> <span>Invoices</span></a>
					<a href="#" class="btn btn-link btn-float has-text"><i class="icon-calendar5 text-primary"></i> <span>Schedule</span></a>
				</div> -->
			</div>
		</div>
	</div>
	<!-- /page header -->