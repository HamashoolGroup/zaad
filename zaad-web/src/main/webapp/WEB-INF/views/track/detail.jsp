<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!--==================================
=            START MAIN WRAPPER      =
===================================-->
<section class="main-wrapper single-category">
	<div class="container">
		<div class="row">
			<div class="col-sm-9">
				<div class="row">
					<div class="col-sm-12">
						<h2 class="z-main-entry-title">${md.title}</h2>
					</div>
					<div class="col-sm-12" style="margin: 5px 0 15px 0;">
						<div class="z-shar-div">
							<span class='st_googleplus_large' displayText='Google +'></span>
							<span class='st_facebook_large' displayText='Facebook'></span>
							<span class='st_twitter_large' displayText='Tweet'></span>
							<span class='st_linkedin_large' displayText='LinkedIn'></span>
							<span class='st_pinterest_large' displayText='Pinterest'></span>
							<span class='st_email_large' displayText='Email'></span>
						</div>
					</div>
				</div>
				
				<div class="row" style="margin-bottom:20px; display: ${v1_display};">
					<div class="col-sm-12">
						<h3 class="z-pack-video-title">
							<a href="${pageContext.servletContext.contextPath}/video/${video1.videoId}">${video1.title}</a>
						</h3>
						<p class="z-tutor-name-text">
							by <a href="${pageContext.servletContext.contextPath}/tutor/${video1.tutor.tutorId}">${video1.tutor.tutorName}</a>
						</p>
					</div>
					<div class="col-sm-12 hidden-sm hidden-xs">
						<div class="z-video-xlarge">
							<iframe id="main-video-iframe" src="https://www.youtube.com/embed/${video1.videoId}" frameborder="0" allowfullscreen></iframe>
						</div>
					</div>
					<div class="col-sm-12 visible-sm visible-xs">
						<div class="z-video-small">
							<iframe id="main-video-iframe" src="https://www.youtube.com/embed/${video1.videoId}" frameborder="0" allowfullscreen></iframe>
						</div>
					</div>
					<!-- //col-sm-7 -->				
				</div>
				
				<div class="row">
					<div class="col-sm-9">
						<p>
						${md.desc}
						</p>
					</div>
				</div>
				<!-- //row -->
				
				<div class="row" style="margin-top: 20px; margin-bottom:20px; display: ${v2_display};">
					<div class="col-sm-12">
						<h3 class="z-pack-video-title">
							<a href="${pageContext.servletContext.contextPath}/video/${video2.videoId}">${video2.title}</a>
						</h3>
						<p class="z-tutor-name-text">
							by <a href="${pageContext.servletContext.contextPath}/tutor/${video2.tutor.tutorId}">${video2.tutor.tutorName}</a>
						</p>
					</div>
					<div class="col-sm-12 hidden-sm hidden-xs">
						<div class="z-video-xlarge">
							<iframe id="main-video-iframe" src="https://www.youtube.com/embed/${video2.videoId}" frameborder="0" allowfullscreen></iframe>
						</div>
					</div>
					<div class="col-sm-12 visible-sm visible-xs">
						<div class="z-video-small">
							<iframe id="main-video-iframe" src="https://www.youtube.com/embed/${video2.videoId}" frameborder="0" allowfullscreen></iframe>
						</div>
					</div>
					<!-- //col-sm-7 -->				
				</div>
				
				<div class="row" style="display: ${v3_display};">
					<div class="col-sm-12">
						<h3 class="z-pack-video-title">
							<a href="${pageContext.servletContext.contextPath}/video/${video3.videoId}">${video3.title}</a>
						</h3>
						<p class="z-tutor-name-text">
							by <a href="${pageContext.servletContext.contextPath}/tutor/${video3.tutor.tutorId}">${video3.tutor.tutorName}</a>
						</p>
					</div>
					<div class="col-sm-12 hidden-sm hidden-xs">
						<div class="z-video-xlarge">
							<iframe id="main-video-iframe" src="https://www.youtube.com/embed/${video3.videoId}" frameborder="0" allowfullscreen></iframe>
						</div>
					</div>
					<div class="col-sm-12 visible-sm visible-xs">
						<div class="z-video-small">
							<iframe id="main-video-iframe" src="https://www.youtube.com/embed/${video3.videoId}" frameborder="0" allowfullscreen></iframe>
						</div>
					</div>
					<!-- //col-sm-7 -->				
				</div>
			</div>
			<!-- //col-sm-9 -->

			<div class="col-sm-3">
			</div>
			<!-- //col-sm-3 -->
		</div>
		<!-- //row -->
	</div>
	<!-- //container -->
</section>
<!--====  End of MAIN WRAPPER  ====-->

