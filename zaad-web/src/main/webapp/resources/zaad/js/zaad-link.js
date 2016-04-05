$z.link = {};

$z.link.getTutorDetail = function(context, tutorId) {
	return context + "/" + "tutor" + "/" + tutorId;
};

$z.link.getTutorDetailAnchor = function(context, tutorId, tutorName) {
	return "<a href='" + $z.link.getTutorDetail(context, tutorId) + "'>" + tutorName + "</a>";
};

$z.link.getVideoDetail = function(context, videoId) {
	return context + "/" + "video" + "/" + videoId;
};

$z.link.getPVideoDetail = function(context, playlistId, videoId) {
	return context + "/" + "pvideo" + "/" + playlistId + "/" + videoId;
};

$z.link.getTagsAnchor = function(context, tags, maxTagCnt) {
	if ( tags== null || tags.length < 1 ) {
		return "";
	}

	var MAX_TAG_CNT = 3;
	if ( maxTagCnt) {
		MAX_TAG_CNT = maxTagCnt;
	}
	
	var toCnt = tags.length > MAX_TAG_CNT ? MAX_TAG_CNT : tags.length;
	
	var tagsText = "";
	for ( var i = 0; i < toCnt-1; i++ ) {
		tagsText += "#" + "<a href='" + context + "/tag/" + tags[i] + "'>" + tags[i] + "</a>" + ",";
	}
	tagsText += "#" + "<a href='" + context + "/tag/" + tags[i] + "'>" + tags[i] + "</a>";
	
	return tagsText;
};