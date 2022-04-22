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
									<p class="z-tutor-name-text" style="font-size: 12px;">
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
				
				<div id="video-div" class="row">
					<!-- table class="table table-borderd">
						<c:forEach var="video" items="${playlist.videos}">
						<tr id="">
							<td style="vertical-align: middle;">
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
								<a href="${pageContext.servletContext.contextPath}/pvideo/${playlist.playlistId}/${video.videoId}">
									${video.title}
								</a>
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
										<span id="tags" class="z-video-detail-tags-text"></span>
									</div>
									<div>
										<span id="tags-level" class="z-video-detail-tags-level-text"></span>
									</div> 
									<span id="creation-date" class="entry-date"><time></time></span>
								</div>
							</td>
						</tr>
						</c:forEach>
					</table -->
				</div>
				<div class="row">
					<div class="z-see-all">
						<a id="video-div-more" href="#video-div-1">
							<input type="button" class="btn btn-default zaad-more" value="Load More">
						</a>
					</div>
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
	listPlaylistVideo(1, 0, 'first');
	
	bindMorePage();
});
</script>


<script>
var listPlaylistVideo = function(page, index, action) {
	var divId = "video-div";
	var pageContext = "${pageContext.servletContext.contextPath}";
	$.ajax({
		type : "POST",
		url : "${pageContext.servletContext.contextPath}/video/list/byplaylist/${playlist.playlistId}?index=" + index + "&size=" + $z.C.paging.list.playlistVideo,
		success : function(data) {
			console.debug(data);
			
			if ( data.length < $z.C.paging.list.playlistVideo) {
				$('#' + divId + '-more').hide();
			}
			
			var lastIndex = 0;
			var html = '<div class="col-sm-12 hidden-sm hidden-xs">\n'
			html += '		<table class="table table-borderd">\n';
			for ( var i = 0; i < data.length; i++ ) {
				html += '	<tr id="">\n';
				html += '		<td style="vertical-align: middle; width: 20px;">\n';
				html += '			' + data[i].index + '\n';
				html += '		</td>\n';
				html += '		<td style="width: 140px;">\n';
				html += '			<div class="z-playlist-video-img-area">\n';
				html += '				<a href="' + $z.link.getPVideoDetail(pageContext, data[i].playlistId, data[i].videoId) + '">\n';
				html += '					<img class="z-playlist-video-img" src="' + $z.formatter.url.getVideoMqImgUrlById(data[i].videoId) + '" alt="' + $z.seo.img.videoAlt(data[i].title, data[i].tutor.tutorName) + '" title="' + $z.seo.img.videoTitle(data[i].title, data[i].tutor.tutorName) + '">\n';
				html += '				</a>\n';
				html += '			</div>\n';
				html += '		</td>\n';
				html += '		<td >\n';
				html += '				<a href="' + $z.link.getPVideoDetail(pageContext, data[i].playlistId, data[i].videoId) + '" alt="' + $z.seo.img.videoAlt(data[i].title, data[i].tutor.tutorName) + '" title="' + $z.seo.img.videoTitle(data[i].title, data[i].tutor.tutorName) + '">\n';
				html += '				' + data[i].title + '\n';
				html += '			</a>\n';
				html += '			<div>\n';
				html += '				<p class="text-right z-video-stats-text">\n';
				html += '					' + $z.formatter.date.friendly(data[i].creationDate) + '</p>\n';
				html += '				</p>\n';
				html += '				<p class="text-right z-video-tags-text">\n';
				html += '					' + $z.link.getTagsAnchor(pageContext, data[i].tags) + '</p>\n';
				html += '				</p>\n';
				html += '				<p class="text-right z-video-tags-level-text">\n';
				html += '					' + $z.link.getTagsAnchor(pageContext, data[i].levels) + '</p>\n';
				html += '				</p>\n';
				html += 				$z.util.generateStarRating($z.util.normalizeRecommendarity(data[i].recommendarity))
				html += '			</div>\n';
				html += '		</td>\n';
				html += '	</tr>\n';
				
				lastIndex = data[i].index;
			}
			html += '	</table>\n';
			html += '</div>\n';
			
			html += '<div class=" col-sm-12 visible-sm visible-xs">\n'
			for ( var i = 0; i < data.length; i++ ) {
				if ( i%4 == 0 ) {
					html += '<div id="' + divId + '-' + page + '" class="row">\n';
				}
				
				html += '<div class="col-sm-3">\n';
				html += '	<article>\n';
				html += '		<div class="article-inner">\n';
				html += '			<div class="img-wrapper">\n';
				html += '				<a href="' + $z.link.getPVideoDetail(pageContext, data[i].playlistId, data[i].videoId) + '">\n';
				html += '					<img class="img-100p" src="' + $z.formatter.url.getVideoMqImgUrlById(data[i].videoId) + '" alt="' + $z.seo.img.videoAlt(data[i].title, data[i].tutor.tutorName) + '" title="' + $z.seo.img.videoTitle(data[i].title, data[i].tutor.tutorName) + '">\n';
				html += '				</a>\n';
				html += '			</div>\n';
				html += '		</div>\n';
				html += '		<div class="" style="margin-top: 5px; min-height: 26px;">\n';
				html += '			<h5 class="">\n';
				html += '				[' + data[i].index + '] \n';
				html += '				<a href="' + $z.link.getPVideoDetail(pageContext, data[i].playlistId, data[i].videoId) + '" alt="' + $z.seo.img.videoAlt(data[i].title, data[i].tutor.tutorName) + '" title="' + $z.seo.img.videoTitle(data[i].title, data[i].tutor.tutorName) + '">\n';
				html += '                   ' + $z.formatter.string.titleFriendly(data[i].title)
				html += '               </a>\n';
				html += '			</h5>\n';
				html += '		</div>\n';
				html += '		<div>\n';
				html += '			<p class="text-right z-video-stats-text">\n';
				html += '				' + $z.formatter.date.friendly(data[i].creationDate) + '</p>\n';
				html += '			</p>\n';
				html += '			<p class="text-right z-video-tags-text">\n';
				html += '				' + $z.link.getTagsAnchor(pageContext, data[i].tags) + '</p>\n';
				html += '			</p>\n';
				html += '			<p class="text-right z-video-tags-level-text">\n';
				html += '				' + $z.link.getTagsAnchor(pageContext, data[i].levels) + '</p>\n';
				html += '			</p>\n';
				html += $z.util.generateStarRating($z.util.normalizeRecommendarity(data[i].recommendarity))
				html += '		</div>\n';
				html += '	</article>\n';
				html += '</div>\n';
				
				if ( i%4 == 3 ) {
					html += '</div>\n';
				}
			}
			if (  (i-1)%4 != 3 ) {
				html += '</div>\n';
			}
			
			if ( "first" == action) {
				$("#" + divId).html(html);
			} else if ( "next" == action) {
				$("#" + divId).append(html);
			}
			
			$('#' + divId + '-more').data('index', lastIndex);
			$('#' + divId + '-more').data('page', page + 1);
			$('#' + divId + '-more').attr('href', '#' + divId + '-' + (page + 1));
		}
	});
};

var bindMorePage = function() {
	$('#video-div-more').bind('click', function() {
		listPlaylistVideo($('#video-div-more').data('page'), $('#video-div-more').data('index'), 'next');
	});
	
};
</script>

