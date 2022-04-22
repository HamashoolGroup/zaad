
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!--==================================
=            START Header            =
===================================-->
<header>
	<!-- top-bar -->
	<div id="zaad-top-bar">
		<div class="container">
			<div class="row">
				<div id="logo" class="col-xs-4 col-sm-3 col-md-3 hidden-sm hidden-xs">
					<a href="${pageContext.servletContext.contextPath}/"><img src="${pageContext.servletContext.contextPath}/resources/img/zaad/logo.png" alt="logo"></a>
				</div> <!-- //logo -->
				<div class="col-sm-12 col-md-9">
					<div class="top-right">
						<div class="zaad-login">
							<!-- a href="#" role="button" data-toggle="modal" data-target="#login">
								<i class="fa fa-user"></i>
							</a -->
							<!-- Login modal -->
							<div id="login" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true">
								<div class="modal-dialog">
									<!-- Modal content-->
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal" aria-hidden="true"><i class="fa fa-close"></i></button>
											<h5 class="text-left">Log in</h5>
										</div>
										<div class="modal-body">
											<form action="#" method="post" id="login-form">
												<fieldset class="userdata">
													<input id="username" placeholder="Username" type="text" name="username" class="form-control">
													<input id="password" type="password" placeholder="Password" name="password" class="form-control">
													<div class="remember-wrap">
														<input id="remember" type="checkbox" name="remember" class="inputbox" value="yes">
														<label for="remember">Remember Me</label>
													</div>
													<div class="button-wrap pull-left">
														<input type="submit" name="Submit" class="btn btn-block btn-success" value="Log in">
													</div>
													<p class="forget-name-link pull-left">
														Forgot <a href="#">
														Username</a> or <a href="#">
														Password</a>
													</p>
												</fieldset>
											</form>
										</div> <!--/Modal body-->
										<div class="modal-footer">
											New Here? <a href="#">
											Create an account</a>
										</div> <!--/Modal footer-->
									</div> <!-- Modal content-->
								</div> <!-- /.modal-dialog -->
							</div> <!-- //Login modal -->
						</div> <!-- //login -->
						<div class="z-search">
							<div class="search-icon-wrapper">
								<i class="fa fa-search"></i>
							</div>
							<div class="search-wrapper">
								<form action="${pageContext.servletContext.contextPath}/search/list" method="get">
									<input name="text" maxlength="200" class="inputboxnewedge-top-search" type="text" size="20" placeholder="Search ...">
									<i id="search-close" class="fa fa-close"></i>
								</form>
							</div> <!-- //search-wrapper -->
						</div> <!-- //search -->
					</div> <!-- //top-right -->
				</div> 
			</div> <!-- //row -->
		</div> <!-- //container -->
	</div> <!-- //top-bar -->

	<!-- navigation mobile version -->
	<div id="mobile-nav-bar" class="mobile-nav-bar">
		<div class="container">
			<div class="row">
				<div class="visible-sm visible-xs col-sm-12">
					<div class="mobile-logo pull-left">
						<a href="${pageContext.servletContext.contextPath}/"><img src="${pageContext.servletContext.contextPath}/resources/img/zaad/logo.png" alt="logo"></a>
					</div>
					<a id="offcanvas-toggler" class="pull-right" href="#"><i class="fa fa-bars"></i></a>
				</div>
			</div>
		</div>
	</div>

	<!-- navigation -->
	<nav id="navigation-bar" class="navigation hidden-sm hidden-xs">
		<div class="container">
			<div class="row">
				<div class="col-sm-12">
					<ul class="list-inline megamenu-parent">
						<li class="">
							<a href="${pageContext.servletContext.contextPath}/">Home</a>
						</li>
						<li class="has-child">
							<a href="#">Topic</a>
							<div class="dropdown-inner">
								<ul class="dropdown-items">
									<li><a href="${pageContext.servletContext.contextPath}/category/everyday">Everyday</a></li>
									<li><a href="${pageContext.servletContext.contextPath}/category/tip">Culture & Tips</a></li>
									<li><a href="${pageContext.servletContext.contextPath}/category/business">Business</a></li>
									<li><a href="${pageContext.servletContext.contextPath}/category/interview">Job Interview</a></li>
									<li><a href="${pageContext.servletContext.contextPath}/category/travel">Travel</a></li>
									<li><a href="${pageContext.servletContext.contextPath}/category/motivational">Motivational</a></li>
									<li><a href="${pageContext.servletContext.contextPath}/category/slang">Slang</a></li>
									<li><a href="${pageContext.servletContext.contextPath}/category/methodology">Methodology</a></li>
									<li><a href="${pageContext.servletContext.contextPath}/category/news">News</a></li>
									<li><a href="${pageContext.servletContext.contextPath}/category/kid">For Kids</a></li>
								</ul>
							</div>
						</li>
						<li class="has-child">
							<a href="#">Section</a>
							<div class="dropdown-inner">
								<ul class="dropdown-items">
									<li><a href="${pageContext.servletContext.contextPath}/category/speaking">Speaking</a></li>
									<li><a href="${pageContext.servletContext.contextPath}/category/listening">Listening</a></li>
									<li><a href="${pageContext.servletContext.contextPath}/category/writing">Writing</a></li>
									<li><a href="${pageContext.servletContext.contextPath}/category/reading">Reading</a></li>
									<li><a href="${pageContext.servletContext.contextPath}/category/grammar">Grammar</a></li>
									<li><a href="${pageContext.servletContext.contextPath}/category/vocabulary">Vocabulary</a></li>
									<li><a href="${pageContext.servletContext.contextPath}/category/pronunciation">Pronunciation</a></li>
								</ul>
							</div>
						</li>
						<li class="has-child">
							<a href="#">Test</a>
							<div class="dropdown-inner">
								<ul class="dropdown-items">
									<li><a href="${pageContext.servletContext.contextPath}/category/ielts">IELTS</a></li>
									<li><a href="${pageContext.servletContext.contextPath}/category/toeic">TOEIC</a></li>
									<li><a href="${pageContext.servletContext.contextPath}/category/toefl">TOEFL</a></li>
								</ul>
							</div>
						</li>
						<li class="has-child">
							<a href="#">Level</a>
							<div class="dropdown-inner">
								<ul class="dropdown-items">
									<li><a href="${pageContext.servletContext.contextPath}/category/beginner">For Beginner</a></li>
									<li><a href="${pageContext.servletContext.contextPath}/category/intermediate">For Intermediate</a></li>
									<li><a href="${pageContext.servletContext.contextPath}/category/advanced">For Advanced</a></li>
								</ul>
							</div>
						</li>
						<li class="has-child">
							<a href="#">Accent</a>
							<div class="dropdown-inner">
								<ul class="dropdown-items">
									<li><a href="${pageContext.servletContext.contextPath}/category/american">American</a></li>
									<li><a href="${pageContext.servletContext.contextPath}/category/canada">Canadian</a></li>
									<li><a href="${pageContext.servletContext.contextPath}/category/british">British</a></li>
									<li><a href="${pageContext.servletContext.contextPath}/category/australia">Australian</a></li>
									<li><a href="${pageContext.servletContext.contextPath}/category/irish">Irish</a></li>
								</ul>
							</div>
						</li>
						<li class="">
							<a href="${pageContext.servletContext.contextPath}/track/index">Track</a>
						</li>
					</ul>
				</div> <!-- col-sm-12 -->
			</div> <!-- //row -->
		</div> <!-- //container -->
	</nav> <!-- //navigation -->
</header>
<!--====  End of Header  ====-->

<!-- Offcanvas Start-->
<div class="offcanvas-overlay"></div>
<div class="offcanvas-menu visible-sm visible-xs">
	<a href="#" class="close-offcanvas"><i class="fa fa-remove"></i></a>
	<div class="offcanvas-inner">
		<!-- h3 class="title">Search</h3>
		<div class="search">
			<form action="#" method="post">
				<input name="searchword" maxlength="200" class="inputbox search-query" type="text" placeholder="Search ...">
			</form>
		</div-->
		<ul style="margin-top: 25px;">
			<li class="">
				<a href="${pageContext.servletContext.contextPath}/">Home</a>
			</li>
			<li>
				<a href="javascript:$('#xs-menu-topic').click();">Topic</a>
				<span id="xs-menu-topic" role="button" class="offcanvas-menu-toggler collapsed" data-toggle="collapse" data-target="#collapse-menu-01" aria-expanded="false" aria-controls="collapse-menu-01"><i class="fa fa-plus"></i><i class="fa fa-minus"></i></span>
				<ul class="collapse" id="collapse-menu-01" aria-expanded="false">
					<li><a href="${pageContext.servletContext.contextPath}/category/everyday">Everyday</a></li>
					<li><a href="${pageContext.servletContext.contextPath}/category/tip">Culture & Tips</a></li>
					<li><a href="${pageContext.servletContext.contextPath}/category/business">Business</a></li>
					<li><a href="${pageContext.servletContext.contextPath}/category/interview">Job Interview</a></li>
					<li><a href="${pageContext.servletContext.contextPath}/category/travel">Travel</a></li>
					<li><a href="${pageContext.servletContext.contextPath}/category/motivational">Motivational</a></li>
					<li><a href="${pageContext.servletContext.contextPath}/category/slang">Slang</a></li>
					<li><a href="${pageContext.servletContext.contextPath}/category/methodology">Methodology</a></li>
					<li><a href="${pageContext.servletContext.contextPath}/category/news">News</a></li>
					<li><a href="${pageContext.servletContext.contextPath}/category/kid">For Kids</a></li>
				</ul>
			</li>
			<li>
				<a href="javascript:$('#xs-menu-section').click();">Section</a>
				<span id="xs-menu-section" role="button" class="offcanvas-menu-toggler collapsed" data-toggle="collapse" data-target="#collapse-menu-02" aria-expanded="false" aria-controls="collapse-menu-02"><i class="fa fa-plus"></i><i class="fa fa-minus"></i></span>
				<ul class="collapse" id="collapse-menu-02" aria-expanded="false">
					<li><a href="${pageContext.servletContext.contextPath}/category/speaking">Speaking</a></li>
					<li><a href="${pageContext.servletContext.contextPath}/category/listening">Listening</a></li>
					<li><a href="${pageContext.servletContext.contextPath}/category/writing">Writing</a></li>
					<li><a href="${pageContext.servletContext.contextPath}/category/reading">Reading</a></li>
					<li><a href="${pageContext.servletContext.contextPath}/category/grammar">Grammar</a></li>
					<li><a href="${pageContext.servletContext.contextPath}/category/vocabulary">Vocabulary</a></li>
					<li><a href="${pageContext.servletContext.contextPath}/category/pronunciation">Pronunciation</a></li>
				</ul>
			</li>
			<li>
				<a href="javascript:$('#xs-menu-test').click();">Test</a>
				<span id="xs-menu-test" role="button" class="offcanvas-menu-toggler collapsed" data-toggle="collapse" data-target="#collapse-menu-03" aria-expanded="false" aria-controls="collapse-menu-03"><i class="fa fa-plus"></i><i class="fa fa-minus"></i></span>
				<ul class="collapse" id="collapse-menu-03" aria-expanded="false">
					<li><a href="${pageContext.servletContext.contextPath}/category/ielts">IELTS</a></li>
					<li><a href="${pageContext.servletContext.contextPath}/category/toeic">TOEIC</a></li>
					<li><a href="${pageContext.servletContext.contextPath}/category/toefl">TOEFL</a></li>
				</ul>
			</li>
			<li>
				<a href="javascript:$('#xs-menu-level').click();">Level</a>
				<span id="xs-menu-level" role="button" class="offcanvas-menu-toggler collapsed" data-toggle="collapse" data-target="#collapse-menu-04" aria-expanded="false" aria-controls="collapse-menu-04"><i class="fa fa-plus"></i><i class="fa fa-minus"></i></span>
				<ul class="collapse" id="collapse-menu-04" aria-expanded="false">
					<li><a href="${pageContext.servletContext.contextPath}/category/beginner">For Beginner</a></li>
					<li><a href="${pageContext.servletContext.contextPath}/category/intermediate">For Intermediate</a></li>
					<li><a href="${pageContext.servletContext.contextPath}/category/advanced">For Advanced</a></li>
				</ul>
			</li>
			<li>
				<a href="javascript:$('#xs-menu-accent').click();">Accent</a>
				<span id="xs-menu-accent" role="button" class="offcanvas-menu-toggler collapsed" data-toggle="collapse" data-target="#collapse-menu-05" aria-expanded="false" aria-controls="collapse-menu-05"><i class="fa fa-plus"></i><i class="fa fa-minus"></i></span>
				<ul class="collapse" id="collapse-menu-05" aria-expanded="false">
					<li><a href="${pageContext.servletContext.contextPath}/category/american">American</a></li>
					<li><a href="${pageContext.servletContext.contextPath}/category/canada">Canadian</a></li>
					<li><a href="${pageContext.servletContext.contextPath}/category/british">British</a></li>
					<li><a href="${pageContext.servletContext.contextPath}/category/australia">Australian</a></li>
					<li><a href="${pageContext.servletContext.contextPath}/category/irish">Irish</a></li>
				</ul>
			</li>
			<li class="">
				<a href="${pageContext.servletContext.contextPath}/track/index">Track</a>
			</li>
		</ul>
	</div>
</div>
<!-- end Offcanvas -->