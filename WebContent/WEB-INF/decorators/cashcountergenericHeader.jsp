<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
 
<!-- Main navbar -->
	
	<c:if test = "${empty userName}"> 			        
		    <script>
				window.location.href="./cashCounterLogin";
			</script>
    </c:if> 
	
	
	<div class="navbar navbar-inverse">
		<div class="navbar-header">
			<a class="navbar-brand" href="./dashboard"><spring:message code="applicationName" text="default text"/></a>

			<ul class="nav navbar-nav visible-xs-block">
				<li><a data-toggle="collapse" data-target="#navbar-mobile"><i class="icon-tree5"></i></a></li>
				<li><a class="sidebar-mobile-main-toggle"><i class="icon-paragraph-justify3"></i></a></li>
			</ul>
		</div>

		<div class="navbar-collapse collapse" id="navbar-mobile">
			<ul class="nav navbar-nav">
				<li><a class="sidebar-control sidebar-main-toggle hidden-xs"><i class="icon-paragraph-justify3"></i></a></li>

				<li class="dropdown">
					<!-- <a href="#" class="dropdown-toggle" data-toggle="dropdown">
						<i class="icon-git-compare"></i>
						<span class="visible-xs-inline-block position-right">Git updates</span>
						<span class="badge bg-warning-400">9</span>
					</a> -->
					
					<!-- <div class="dropdown-menu dropdown-content">
						<div class="dropdown-content-heading">
							Git updates
							<ul class="icons-list">
								<li><a href="#"><i class="icon-sync"></i></a></li>
							</ul>
						</div>

						<ul class="media-list dropdown-content-body width-350">
							<li class="media">
								<div class="media-left">
									<a href="#" class="btn border-primary text-primary btn-flat btn-rounded btn-icon btn-sm"><i class="icon-git-pull-request"></i></a>
								</div>

								<div class="media-body">
									Drop the IE <a href="#">specific hacks</a> for temporal inputs
									<div class="media-annotation">4 minutes ago</div>
								</div>
							</li>

							<li class="media">
								<div class="media-left">
									<a href="#" class="btn border-warning text-warning btn-flat btn-rounded btn-icon btn-sm"><i class="icon-git-commit"></i></a>
								</div>
								
								<div class="media-body">
									Add full font overrides for popovers and tooltips
									<div class="media-annotation">36 minutes ago</div>
								</div>
							</li>

							<li class="media">
								<div class="media-left">
									<a href="#" class="btn border-info text-info btn-flat btn-rounded btn-icon btn-sm"><i class="icon-git-branch"></i></a>
								</div>
								
								<div class="media-body">
									<a href="#">Chris Arney</a> created a new <span class="text-semibold">Design</span> branch
									<div class="media-annotation">2 hours ago</div>
								</div>
							</li>

							<li class="media">
								<div class="media-left">
									<a href="#" class="btn border-success text-success btn-flat btn-rounded btn-icon btn-sm"><i class="icon-git-merge"></i></a>
								</div>
								
								<div class="media-body">
									<a href="#">Eugene Kopyov</a> merged <span class="text-semibold">Master</span> and <span class="text-semibold">Dev</span> branches
									<div class="media-annotation">Dec 18, 18:36</div>
								</div>
							</li>

							<li class="media">
								<div class="media-left">
									<a href="#" class="btn border-primary text-primary btn-flat btn-rounded btn-icon btn-sm"><i class="icon-git-pull-request"></i></a>
								</div>
								
								<div class="media-body">
									Have Carousel ignore keyboard events
									<div class="media-annotation">Dec 12, 05:46</div>
								</div>
							</li>
						</ul>

						<div class="dropdown-content-footer">
							<a href="#" data-popup="tooltip" title="All activity"><i class="icon-menu display-block"></i></a>
						</div>
					</div> -->
				</li>
			</ul>

			<p class="navbar-text" align="center">Welcome :<b>&nbsp;&nbsp;&nbsp;<c:if test = "${not empty userOfficialName}">${userOfficialName}</c:if>&nbsp;&nbsp;&nbsp;&nbsp;Branch : ${CBranchName}&nbsp;&nbsp;&nbsp;&nbsp;Login Id: ${userName}</b> &nbsp;&nbsp;&nbsp;<b>Designation: <c:if test = "${not empty usercdesignation}">${usercdesignation}</c:if></b>&nbsp;&nbsp;&nbsp;&nbsp;Login Date: ${defaultDate}</p>

			<ul class="nav navbar-nav navbar-right">
				<%-- <li class="dropdown language-switch">
					<a class="dropdown-toggle" data-toggle="dropdown">
						
						<c:choose>
							<c:when test="${pageContext.response.locale eq 'en'}">
								<img src="resources/layout_3/assets/images/flags/gb.png" class="position-left" alt="">
								English
							</c:when>
							<c:when test="${pageContext.response.locale eq 'ne'}">
								<img src="resources/layout_3/assets/images/flags/ne.png" class="position-left" alt="">
								Nepali
							</c:when>
							<c:when test="${pageContext.response.locale eq 'hi'}">
								<img src="resources/layout_3/assets/images/flags/in.png" class="position-left" alt="">
								Hindi
							</c:when>
							<c:otherwise>
								<img src="resources/layout_3/assets/images/flags/in.png" class="position-left" alt="">
								Kannada
							</c:otherwise>		
						</c:choose>
						<span class="caret"></span>
					</a>

					 <ul class="dropdown-menu">
						<li><a href="?lang=en" class="english"><img src="resources/layout_3/assets/images/flags/gb.png" alt=""> English</a></li>
						<li><a href="?lang=hi" class="deutsch"><img src="resources/layout_3/assets/images/flags/in.png" alt=""> Hindi</a></li>
						<li><a href="?lang=ka" class="ukrainian"><img src="resources/layout_3/assets/images/flags/in.png" alt=""> Kannada</a></li>
						<li><a href="?lang=ne" class="english"><img src="resources/layout_3/assets/images/flags/ne.png" alt=""> Nepali</a></li>
					</ul> 
				</li> --%>

				<li class="dropdown">
					<!-- <a href="#" class="dropdown-toggle" data-toggle="dropdown">
						<i class="icon-bubbles4"></i>
						<span class="visible-xs-inline-block position-right">Messages</span>
						<span class="badge bg-warning-400">2</span>
					</a> -->
					
					<div class="dropdown-menu dropdown-content width-350">
						<div class="dropdown-content-heading">
							Messages
							<ul class="icons-list">
								<li><a href="#"><i class="icon-compose"></i></a></li>
							</ul>
						</div>

						<!-- <ul class="media-list dropdown-content-body">
							<li class="media">
								<div class="media-left">
									<img src="resources/layout_3/assets/images/placeholder.jpg" class="img-circle img-sm" alt="">
									<span class="badge bg-danger-400 media-badge">5</span>
								</div>

								<div class="media-body">
									<a href="#" class="media-heading" data-popup="tooltip" title="Chat" data-toggle="modal" data-target="#chat">
										<span class="text-semibold">James Alex</span>
										<span class="media-annotation pull-right">04:58</span>
									</a>

									<span class="text-muted">who knows, maybe that would be the best thing for me...</span>
								</div>
							</li>

							<li class="media">
								<div class="media-left">
									<img src="resources/layout_3/assets/images/placeholder.jpg" class="img-circle img-sm" alt="">
									<span class="badge bg-danger-400 media-badge">4</span>
								</div>

								<div class="media-body">
									<a href="#" class="media-heading">
										<span class="text-semibold">Margo Baker</span>
										<span class="media-annotation pull-right">12:16</span>
									</a>

									<span class="text-muted">That was something he was unable to do because...</span>
								</div>
							</li>

							<li class="media">
								<div class="media-left"><img src="resources/layout_3/assets/images/placeholder.jpg" class="img-circle img-sm" alt=""></div>
								<div class="media-body">
									<a href="#" class="media-heading">
										<span class="text-semibold">Jeremy Victorino</span>
										<span class="media-annotation pull-right">22:48</span>
									</a>

									<span class="text-muted">But that would be extremely strained and suspicious...</span>
								</div>
							</li>

							<li class="media">
								<div class="media-left"><img src="resources/layout_3/assets/images/placeholder.jpg" class="img-circle img-sm" alt=""></div>
								<div class="media-body">
									<a href="#" class="media-heading">
										<span class="text-semibold">Beatrix Diaz</span>
										<span class="media-annotation pull-right">Tue</span>
									</a>

									<span class="text-muted">What a strenuous career it is that I've chosen...</span>
								</div>
							</li>

							<li class="media">
								<div class="media-left"><img src="resources/layout_3/assets/images/placeholder.jpg" class="img-circle img-sm" alt=""></div>
								<div class="media-body">
									<a href="#" class="media-heading">
										<span class="text-semibold">Richard Vango</span>
										<span class="media-annotation pull-right">Mon</span>
									</a>
									
									<span class="text-muted">Other travelling salesmen live a life of luxury...</span>
								</div>
							</li>
						</ul> -->

						<!-- <div class="dropdown-content-footer">
							<a href="#" data-popup="tooltip" title="All messages"><i class="icon-menu display-block"></i></a>
						</div> -->
					</div>
				</li>

				<li class="dropdown dropdown-user">
					<a class="dropdown-toggle" data-toggle="dropdown">
						<img src="resources/layout_3/assets/images/placeholder.jpg" alt="">
						<span>${userName}</span>
						<i class="caret"></i>
					</a>

					<ul class="dropdown-menu dropdown-menu-right">
						<!-- <li><a href="#"><i class="icon-user-plus"></i> My profile</a></li> 
						<li><a href="#"><i class="icon-coins"></i> My balance</a></li>
						<li><a href="./dateTimeSettings"><i class="icon-coins"></i> DateTime Settings</a></li>
						<li><a href="#"><span class="badge bg-teal-400 pull-right">58</span> <i class="icon-comment-discussion"></i> Messages</a></li>
						<li class="divider"></li>
						<li><a href="./applicationSettings"><i class="icon-cog5"></i> Account settings</a></li> -->
						 <li><a href="./cashCounterLogout"><i class="icon-switch2"></i> Logout</a></li> 
					</ul>
				</li>
			</ul>
		</div>
	</div>
	<!-- /main navbar -->
<!-- Chat modal -->
					<div id="chat" class="modal fade">
						<div class="modal-dialog modal-lg">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal">&times;</button>
									<h6 class="modal-title"><span class="status-mark bg-success position-left"></span> James Alex</h6>
								</div>

								<div class="modal-body">
									<ul class="media-list chat-list content-group">
										<li class="media date-step">
											<span>Monday, Feb 10</span>
										</li>

										<li class="media">
											<div class="media-left">
												<a href="assets/images/placeholder.jpg">
													<img src="resources/layout_3/assets/images/placeholder.jpg" class="img-circle" alt="">
												</a>
											</div>

											<div class="media-body">
												<div class="media-content">Below mounted advantageous spread yikes bat stubbornly crud a and a excepting</div>
												<span class="media-annotation display-block mt-10">Mon, 9:54 am <a href="#"><i class="icon-pin-alt position-right text-muted"></i></a></span>
											</div>
										</li>

										<li class="media reversed">
											<div class="media-body">
												<div class="media-content">Far squid and that hello fidgeted and when. As this oh darn but slapped casually husky sheared that cardinal hugely one and some unnecessary factiously hedgehog a feeling one rudely much but one owing sympathetic regardless more astonishing evasive tasteful much.</div>
												<span class="media-annotation display-block mt-10">Mon, 10:24 am <a href="#"><i class="icon-pin-alt position-right text-muted"></i></a></span>
											</div>

											<div class="media-right">
												<a href="assets/images/placeholder.jpg">
													<img src="resources/layout_3/assets/images/placeholder.jpg" class="img-circle" alt="">
												</a>
											</div>
										</li>

										<li class="media">
											<div class="media-left">
												<a href="assets/images/placeholder.jpg">
													<img src="resources/layout_3/assets/images/placeholder.jpg" class="img-circle" alt="">
												</a>
											</div>

											<div class="media-body">
												<div class="media-content">Darn over sour then cynically less roadrunner up some cast buoyant. Macaw krill when and upon less contrary warthog jeez some koala less since therefore minimal.</div>
												<span class="media-annotation display-block mt-10">Mon, 10:56 am <a href="#"><i class="icon-pin-alt position-right text-muted"></i></a></span>
											</div>
										</li>

										<li class="media reversed">
											<div class="media-body">
												<div class="media-content">Some upset impious a and submissive when far crane the belched coquettishly. More the puerile dove wherever</div>
												<span class="media-annotation display-block mt-10">Mon, 11:29 am <a href="#"><i class="icon-pin-alt position-right text-muted"></i></a></span>
											</div>

											<div class="media-right">
												<a href="assets/images/placeholder.jpg">
													<img src="resources/layout_3/assets/images/placeholder.jpg" class="img-circle" alt="">
												</a>
											</div>
										</li>

										<li class="media date-step">
											<span>Yesterday</span>
										</li>

										<li class="media">
											<div class="media-left">
												<a href="assets/images/placeholder.jpg">
													<img src="resources/layout_3/assets/images/placeholder.jpg" class="img-circle" alt="">
												</a>
											</div>

											<div class="media-body">
												<div class="media-content">Regardless equitably hello heron glum cassowary jocosely before reliably a jeepers wholehearted shuddered more that some where far by koala.</div>
												<span class="media-annotation display-block mt-10">Tue, 6:40 am <a href="#"><i class="icon-pin-alt position-right text-muted"></i></a></span>
											</div>
										</li>

										<li class="media">
											<div class="media-left">
												<a href="assets/images/placeholder.jpg">
													<img src="resources/layout_3/assets/images/placeholder.jpg" class="img-circle" alt="">
												</a>
											</div>

											<div class="media-body">
												<div class="media-content">Crud reran and while much withdrew ardent much crab hugely met dizzily that more jeez gent equivalent unsafely far one hesitant so therefore.</div>
												<span class="media-annotation display-block mt-10">Tue, 10:28 am <a href="#"><i class="icon-pin-alt position-right text-muted"></i></a></span>
											</div>
										</li>

										<li class="media reversed">
											<div class="media-body">
												<div class="media-content">Thus superb the tapir the wallaby blank frog execrably much since dalmatian by in hot. Uninspiringly arose mounted stared one curt safe</div>
												<span class="media-annotation display-block mt-10">Tue, 8:12 am <a href="#"><i class="icon-pin-alt position-right text-muted"></i></a></span>
											</div>

											<div class="media-right">
												<a href="assets/images/placeholder.jpg">
													<img src="resources/layout_3/assets/images/placeholder.jpg" class="img-circle" alt="">
												</a>
											</div>
										</li>

										<li class="media date-step">
											<span>Today</span>
										</li>

										<li class="media">
											<div class="media-left">
												<a href="assets/images/placeholder.jpg">
													<img src="resources/layout_3/assets/images/placeholder.jpg" class="img-circle" alt="">
												</a>
											</div>

											<div class="media-body">
												<div class="media-content">Tolerantly some understood this stubbornly after snarlingly frog far added insect into snorted more auspiciously heedless drunkenly jeez foolhardy oh.</div>
												<span class="media-annotation display-block mt-10">Wed, 4:20 pm <a href="#"><i class="icon-pin-alt position-right text-muted"></i></a></span>
											</div>
										</li>

										<li class="media reversed">
											<div class="media-body">
												<div class="media-content">Satisfactorily strenuously while sleazily dear frustratingly insect menially some shook far sardonic badger telepathic much jeepers immature much hey.</div>
												<span class="media-annotation display-block mt-10">2 hours ago <a href="#"><i class="icon-pin-alt position-right text-muted"></i></a></span>
											</div>

											<div class="media-right">
												<a href="assets/images/placeholder.jpg">
													<img src="resources/layout_3/assets/images/placeholder.jpg" class="img-circle" alt="">
												</a>
											</div>
										</li>

										<li class="media">
											<div class="media-left">
												<a href="assets/images/placeholder.jpg">
													<img src="resources/layout_3/assets/images/placeholder.jpg" class="img-circle" alt="">
												</a>
											</div>

											<div class="media-body">
												<div class="media-content">Grunted smirked and grew less but rewound much despite and impressive via alongside out and gosh easy manatee dear ineffective yikes.</div>
												<span class="media-annotation display-block mt-10">13 minutes ago <a href="#"><i class="icon-pin-alt position-right text-muted"></i></a></span>
											</div>
										</li>

										<li class="media reversed">
											<div class="media-body">
												<div class="media-content"><i class="icon-menu display-block"></i></div>
											</div>

											<div class="media-right">
												<a href="assets/images/placeholder.jpg">
													<img src="resources/layout_3/assets/images/placeholder.jpg" class="img-circle" alt="">
												</a>
											</div>
										</li>
									</ul>

			                    	<textarea name="enter-message" class="form-control content-group" rows="3" cols="1" placeholder="Enter your message..."></textarea>

			                    	<div class="row">
			                    		<div class="col-xs-6">
				                        	<ul class="icons-list icons-list-extended mt-10">
				                                <li><a href="#" data-popup="tooltip" title="Send photo" data-container="body"><i class="icon-file-picture"></i></a></li>
				                            	<li><a href="#" data-popup="tooltip" title="Send video" data-container="body"><i class="icon-file-video"></i></a></li>
				                                <li><a href="#" data-popup="tooltip" title="Send file" data-container="body"><i class="icon-file-plus"></i></a></li>
				                            </ul>
			                    		</div>

			                    		<div class="col-xs-6 text-right">
				                            <button type="button" class="btn bg-teal-400 btn-labeled btn-labeled-right"><b><i class="icon-circle-right2"></i></b> Send</button>
			                    		</div>
			                    	</div>
								</div>
							</div>
						</div>
					</div>
					<!-- /chat modal -->