<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
						<h2 class="z-main-entry-title">${playlist.title}</h2>
					</div>
				</div>
				
				<div class="row">
					<div class="col-sm-12">
						<div class="z-video-tutor">
							<div class="">
								<div class="author-avatar">
									<a href="${pageContext.servletContext.contextPath}/tutor/${playlist.tutor.tutorId}">
										<img src="${playlist.tutor.profileImagePath}" alt="${playlist.tutor.tutorName}" title="${playlist.tutor.tutorName}">
									</a>
								</div>
								<div class="author-avatar-text">
									<p class="author" style="font-size: 12px;">
										<a href="${pageContext.servletContext.contextPath}/tutor/${playlist.tutor.tutorId}">${playlist.tutor.tutorName}</a>
									</p>
									<span id="creation-date" class="entry-date"><time></time></span>
								</div>
							</div>
							<!-- //entry-blog-meta-list -->
	
							<div class="z-shar-div" style="margin-top: 10px;">
								<span class='st_googleplus_large' displayText='Google +'></span>
								<span class='st_facebook_large' displayText='Facebook'></span>
								<span class='st_twitter_large' displayText='Tweet'></span>
								<span class='st_linkedin_large' displayText='LinkedIn'></span>
								<span class='st_pinterest_large' displayText='Pinterest'></span>
								<span class='st_email_large' displayText='Email'></span>
							</div>
							<!-- //entry-blog-meta-list -->
						</div>
						<!-- //entry-blog-meta -->
					</div>
				</div>
				
				<div class="row">
					<table class="table table-borderd">
						<c:forEach var="video" items="${playlist.videos}">
						<tr id="">
							<td>
								${video.index}
							</td>
							<td>
								<div class="z-playlist-video-img-area">
									<a href="${pageContext.servletContext.contextPath}/pvideo/${playlist.playlistId}/${video.videoId}">
									<img class="z-playlist-video-img" src="https://i.ytimg.com/vi/${video.videoId}/mqdefault.jpg" alt="${video.title}" title="${video.title}">
									</a>
								</div>
							</td>
							<td>
								${video.title}
								
								<div style="margin: 10px 0 10px 0;">
									<div id="" class="zaad-star-rating">
										<span class="fa fa-star-o" data-rating="1"></span>
										<span class="fa fa-star-o" data-rating="2"></span>
										<span class="fa fa-star-o" data-rating="3"></span>
										<span class="fa fa-star-o" data-rating="4"></span>
										<span class="fa fa-star-o" data-rating="5"></span>
										<input type="hidden" name="whatever" class="rating-value" value="${video.recommendarity}">
									</div>
									<div class="z-video-tags-div">
										<span id="tags" class="z-video-detail-tags-text"></a></span>
									</div>
									<div>
										<span id="tags-level" class="z-video-detail-tags-level-text"></a></span>
									</div> 
									<span id="creation-date" class="entry-date"><time></time></span>
								</div>
							</td>
						</tr>
						</c:forEach>
					</table>
				
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

<script>
$(document).ready(function() {
	formatDetail();
});
</script>



<script>
var formatDetail = function() {
	//$('#main-video-iframe').attr('src', $z.formatter.url.getVideoUrlById('${video.videoId}'));
	//$('#creation-date').text($z.formatter.date.standard(${video.timestamp}));
	//$('#tags').html($z.link.getTagsAnchor("${pageContext.servletContext.contextPath}", $z.util.arrayString2array('${video.tags}')));
	//$('#tags-level').html($z.link.getTagsAnchor("${pageContext.servletContext.contextPath}", $z.util.arrayString2array('${video.levels}')));
	
	$('.zaad-star-rating > input').each(function(){
		$(this).val($z.util.normalizeRecommendarity($(this).val()));
		$z.util.setStarRating($(this).parent().children('.fa'));
	});
	
};
</script>

