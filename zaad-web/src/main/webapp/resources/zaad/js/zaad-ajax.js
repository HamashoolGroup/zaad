$z.ajax = {};

$z.ajax.listVideoCol4 = function(pageContext, restURL, page, size, divId, action, sortType, queryText, subCat, callback) {
	var targetUrl = pageContext + restURL + "?page=" + page + "&size=" + size + "&sortType=" + sortType;
	
	if ( queryText ) {
		targetUrl = targetUrl + "&text=" + encodeURIComponent(queryText);
	}
	
	$.ajax({
		type : "GET",
		url : targetUrl,
		success : function(data) {
			if ( data.length < size) {
				$('#' + divId + '-more').hide();
			}
			
			
			var html = '';
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
				html += '				<a href="' + $z.link.getPVideoDetail(pageContext, data[i].playlistId, data[i].videoId) + '" alt="' + $z.seo.img.videoAlt(data[i].title, data[i].tutor.tutorName) + '" title="' + $z.seo.img.videoTitle(data[i].title, data[i].tutor.tutorName) + '">\n';
				html += '                   ' + $z.formatter.string.titleFriendly(data[i].title)
				html += '               </a>\n';
				html += '			</h5>\n';
				html += '		</div>\n';
				html += '		<div>\n';
				html += '			<p class="text-right z-tutor-name-text">\n';
				html += '				' + $z.link.getTutorDetailAnchor(pageContext, data[i].tutor.tutorId, $z.formatter.string.tutorNameFriendly(data[i].tutor.tutorName));
				html += '			</p>\n';
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
			
			$('#' + divId + '-more').data('page', page + 1);
			$('#' + divId + '-more').attr('href', '#' + divId + '-' + (page + 1));
			
			if ( callback ) {
				callback(subCat, data, divId);
			}
		},
		dataType : "json"
	});
};

$z.ajax.listPlaylistCol4 = function(pageContext, restURL, page, size, divId, action, sortType) {
	$.ajax({
		type : "POST",
		url : pageContext + restURL + "?page=" + page + "&size=" + size,
		success : function(data) {
			var html = '';
			for ( var i = 0; i < data.length; i++ ) {
				if ( i%4 == 0 ) {
					html += '<div class="row">\n';
				}
				
				html += '<div class="col-sm-3">\n';
				html += '	<article>\n';
				html += '		<div class="article-inner">\n';
				html += '			<div class="img-wrapper">\n';
				html += '				<a href="' + $z.link.getPlaylistDetail(pageContext, data[i].playlistId) + '">\n';
				html += '					<img class="img-100p" src="' + $z.formatter.url.getVideoMqImgUrlById(data[i].videos[0].videoId) + '" alt="' + $z.seo.img.videoAlt(data[i].title, data[i].tutor.tutorName) + '" title="' + $z.seo.img.videoTitle(data[i].title, data[i].tutor.tutorName) + '">\n';
				html += '				</a>\n';
				html += '			</div>\n';
				html += '		</div>\n';
				html += '		<div class="" style="margin-top: 5px; min-height: 26px;">\n';
				html += '			<h5 class="">\n';
				html += '				<a href="' + $z.link.getPlaylistDetail(pageContext, data[i].playlistId) + '" alt="' + $z.seo.img.videoAlt(data[i].title, data[i].tutor.tutorName) + '" title="' + $z.seo.img.videoTitle(data[i].title, data[i].tutor.tutorName) + '">\n';
				html += '                   ' + $z.formatter.string.titleFriendly(data[i].title)
				html += '               </a>\n';
				html += '			</h5>\n';
				html += '		</div>\n';
				html += '		<div>\n';
				html += '			<p class="text-right z-tutor-name-text">\n';
				html += '				' + $z.link.getTutorDetailAnchor(pageContext, data[i].tutor.tutorId, $z.formatter.string.tutorNameFriendly(data[i].tutor.tutorName));
				html += '			</p>\n';
				html += '			<p class="text-right z-video-stats-text">\n';
				html += '				' + $z.formatter.number.digits(data[i].videos.length) + " videos / " + $z.formatter.date.friendly(data[i].videos[0].creationDate) + '</p>\n';
				html += '			</p>\n';
				html += '			<p class="text-right z-video-tags-level-text">\n';
				html += '				' + $z.link.getTagsAnchor(pageContext, data[i].levels) + '</p>\n';
				html += '			</p>\n';
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
			
			$('#' + divId + '-more').data('page', page + 1);
			$('#' + divId + '-more').attr('href', '#' + divId + '-' + (page + 1));
		},
		dataType : "json"
	});
};