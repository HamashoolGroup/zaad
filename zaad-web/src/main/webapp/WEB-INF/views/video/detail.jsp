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
						<h2 class="z-main-entry-title">${video.title}</h2>
					</div>
					<div class="col-sm-7">
						<div class="z-video-large">
							<iframe id="main-video-iframe" src="" frameborder="0" allowfullscreen></iframe>
						</div>
						<div style="margin: 10px 0 10px 0;">
							<div id="recent-video-large-rating" class="zaad-star-rating">
								<span class="fa fa-star-o" data-rating="1"></span>
								<span class="fa fa-star-o" data-rating="2"></span>
								<span class="fa fa-star-o" data-rating="3"></span>
								<span class="fa fa-star-o" data-rating="4"></span>
								<span class="fa fa-star-o" data-rating="5"></span>
								<input type="hidden" name="whatever" class="rating-value" value="3">
							</div>
							<div class="z-video-tags-div">
								<span id="tags" class="z-video-detail-tags-text"></a></span>
							</div>
							<div>
								<span id="tags-level" class="z-video-detail-tags-level-text"></a></span>
							</div> 
						</div>
					</div>
					<!-- //col-sm-7 -->
					<!-- col-sm-5 -->
					<div class="col-sm-5 pre-scrollable" style="max-height: 270px;">
						<div class="row" id ='video-next-list-div'>
						</div>
					</div>
					<!-- //col-sm-5 -->
				</div>
				<!-- //row -->
				
				<div class="row">
					<div class="col-sm-12">
						<div class="z-video-tutor">
							<div class="">
								<div class="author-avatar">
									<a href="${pageContext.servletContext.contextPath}/tutor/${video.tutor.tutorId}">
										<img src="${video.tutor.profileImagePath}" alt="${video.tutor.tutorName}" title="${video.tutor.tutorName}">
									</a>
								</div>
								<div class="author-avatar-text">
									<p class="author" style="font-size: 12px;">
										<a href="${pageContext.servletContext.contextPath}/tutor/${video.tutor.tutorId}">${video.tutor.tutorName}</a>
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
	
						<div class="z-entry-summary" style="">
							<p>
								
							</p>
						</div>
					</div>
				</div>
			</div>
			<!-- //col-sm-9 -->

			<div class="col-sm-3">
				<!-- div style="margin-top: 56px;">
					<div class="z-section-title">
						<h3 class="pull-left">Featured Videos</h3>
					</div> 
					<div class="row">
						<div class="col-sm-12">
							<h4>#pronunciation</h4>
							<article>
								<div class="article-inner">
									<div class="img-wrapper">
										<a href="">
											<img class="img-100p">
										</a>
									</div>
								</div>
								<div class="" style="margin-top: 5px; min-height: 26px;">
									<h5 class="">
										<a href="">
						                   title
						               </a>
									</h5>
								</div>
								<div>
									<p class="text-right z-tutor-name-text">
										tutorName
									</p>
									<p class="text-right z-video-stats-text">
										10.0gram / 3 years ago
									</p>
									<p class="text-right z-video-tags-text">
										#tag1,#tag2
									</p>
									<p class="text-right z-video-tags-level-text">
										#level1
									</p>
								</div>
							</article>
						</div>
					</div>
				</div-->
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
	var videoIndex = ${video.index};
	
	formatDetail();
	listVideoByPlaylist(videoIndex);
});
</script>



<script>
var formatDetail = function() {
	$('#main-video-iframe').attr('src', $z.formatter.url.getVideoUrlById('${video.videoId}'));
	$('#creation-date').text($z.formatter.date.standard(${video.timestamp}));
	$('#tags').html($z.link.getTagsAnchor("${pageContext.servletContext.contextPath}", $z.util.arrayString2array('${video.tags}')));
	$('#tags-level').html($z.link.getTagsAnchor("${pageContext.servletContext.contextPath}", $z.util.arrayString2array('${video.levels}')));
	
	$('.zaad-start-rating > input').val($z.util.normalizeRecommendarity(${video.recommendarity}));
	$z.util.setStarRating($(".zaad-star-rating .fa"));
};

var listVideoByPlaylist = function(videoIndex) {
	$.ajax({
		type : "GET",
		url : "${pageContext.servletContext.contextPath}/video/list/byplaylist/${video.playlistId}?index=" + videoIndex + "&size=5",
		success : function(data) {
			console.debug(data);
			
			var html = "";
			if ( data.length > 0 ) {
				html += '<div class="col-sm-12">\n';
				html += '    Up Next\n';
				html += '</div>\n';
			}
			for ( var i = 0; i < data.length; i++ ) {
				html += '<div class="col-sm-12">\n';
				html += '	<div class="latest-video z-recent-video-sub-leading-item media">\n';
				html += '		<div class="latest-video-inner">\n';
				html += '			<div class="media-left z-recent-video-sub-img">\n';
				html += '				<img src="' + $z.formatter.url.getVideoMqImgUrlById(data[i].videoId) + '" class="z-recent-video-image-sub">\n';
				html += '			</div>\n';
				html += '			<div class="video-post-info media-body">\n';
				html += '				<h5 class="">\n';
				html += '					<a href="' + $z.link.getPVideoDetail('${pageContext.servletContext.contextPath}', data[i].playlistId, data[i].videoId) + '">\n';
				html += '                       ' + $z.formatter.string.titleFriendly(data[i].title) + '\n';
				html += '                   </a>\n';
				html += '				</h5>\n';
				html += '				<p class="text-right z-video-stats-text">' + $z.formatter.number.doubleFriendly(data[i].recommendarity) + " gram / " + $z.formatter.date.friendly(data[i].creationDate) + '</p>\n';
				html += '				<p class="text-right z-video-tags-text">' + $z.link.getTagsAnchor("${pageContext.servletContext.contextPath}", data[i].tags) + '</p>\n';
				html += '				<p class="text-right z-video-tags-level-text">' + $z.link.getTagsAnchor("${pageContext.servletContext.contextPath}", data[i].levels) + '</p>\n';
				html += '			</div>\n';
				html += '		</div>\n';
				html += '	</div>  \n';
				html += '</div>\n';
			}
			
			$('#video-next-list-div').html(html);
		},
		dataType : "json"
	});
};
</script>

