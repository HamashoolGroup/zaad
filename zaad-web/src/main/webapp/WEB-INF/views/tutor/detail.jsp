<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>


<!--==================================
=            START MAIN WRAPPER      =
===================================-->
<section class="main-wrapper">
	<div class="container">
		<div class="row">
			<div class="col-sm-9">
				<div class="z-section-title">
					<table>
						<tr>
							<td class="author-avatar"><img alt="${tutor.tutorName}" title="${tutor.tutorName}" src="${tutor.profileImagePath}"></td>
							<td style="padding-left: 5px;"><h2>${tutor.tutorName}</h2></td>
						</tr>
					</table>
				</div>
				<!-- //section-title -->

				<!-- banner image -->
				<div class="advertisement mtb30">
					<div class="row">
						<div class="col-sm-12">
							<img src="https://${tutor.bannerImagePath}" class="img-100p" alt="${tutor.tutorName}" title="${tutor.tutorName}">
						</div>
						<div class="col-sm-12" style="margin-top: 10px;">
							<a href="${tutor.youtubeChannel}" target="_blank"> Go to ${tutor.tutorName}'s channel <i class="fa fa-youtube-square"></i></a>
						</div>
						<div class="col-sm-12" style="margin-top: 10px;">
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
				</div>
				<!-- //banner image -->
				
				<!-- Tutor Playlist -->
				<div class="single-article-category simple-article-overlay gradient-color10">
					<div class="z-section-title">
						<h3 class="pull-left">Tutor Playlist</h3>
					</div> <!-- //section-title -->
					
					<div id="tutor-playlist-div">
					</div>
					
					<!-- div class="row">
						<div class="z-see-all">
							<a id="tutor-playlist-div-more" href="#tutor-playlist-div-1">-- More --</a>
						</div>
					</div -->
				</div>
				<!-- //Tutor Playlist -->
				
				<!-- Tutor Video -->
				<div class="single-article-category simple-article-overlay gradient-color10">
					<div class="z-section-title">
						<h3 class="pull-left">Tutor Video</h3>
					</div> <!-- //section-title -->
					
					<div id="tutor-video-div">
					</div>
					
					<div class="row">
						<div class="z-see-all">
							<a id="tutor-video-div-more" href="#tutor-video-div-1">-- More --</a>
						</div>
					</div>
				</div>
				<!-- //Tutor Video -->
				
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

<script>
$(document).ready(function() {
	var frstPageNo = 1;
	
	listPlaylistByTutor(frstPageNo);
	listVideoByTutor(frstPageNo);
	
	bindMorePage();
});
</script>

<script>
var bindMorePage = function() {
	$('#tutor-playlist-div-more').bind('click', function() {
		$z.ajax.listPlaylistCol4("${pageContext.servletContext.contextPath}", "/playlist/list/bytutor/${tutor.tutorId}", $(this).data('page'), 4, "tutor-playlist-div", "next");
	});
	
	$('#tutor-video-div-more').bind('click', function() {
		$z.ajax.listVideoCol4("${pageContext.servletContext.contextPath}", "/video/list/bytutor/${tutor.tutorId}", $(this).data('page'), 8, "tutor-video-div", "next", "R");
	});
};
</script>

<script>
var listPlaylistByTutor = function(frstPageNo) {
	$z.ajax.listPlaylistCol4("${pageContext.servletContext.contextPath}", "/playlist/list/bytutor/${tutor.tutorId}", frstPageNo, 40, "tutor-playlist-div", "first");
};

var listVideoByTutor = function(frstPageNo) {
	$z.ajax.listVideoCol4("${pageContext.servletContext.contextPath}", "/video/list/bytutor/${tutor.tutorId}", frstPageNo, 8, "tutor-video-div", "first", "R");	
};



</script>

