$z.util = {};

$z.util.arrayString2array = function(str) {
	if ( str == "[]" ) {
		return [];
	}
	
	str = str.substring(1, str.length-1);
	str = str.split(', ');
	
	return str;
};

$z.util.getFirstPageNo = function(min, max) {
	return Math.floor(Math.random() * (max - min + 1) + min);
};

$z.util.normalizeRecommendarity = function(recommendarity) {
	var MAX_RECOMMENDARITY = 35;
	
	if ( recommendarity >= 35 ) {
		return 5;
	} else {
		return recommendarity / 7;
	}
};

$z.util.setStarRating = function(elements) {
	return elements.each(function() {
		if (parseInt(elements.siblings('input.rating-value').val()) >= parseInt($(this).data('rating'))) {
			return $(this).removeClass('fa-star-o').addClass('fa-star');
		} else {
			return $(this).removeClass('fa-star').addClass('fa-star-o');
	    }
	});
};

$z.util.generateStarRating = function(val) {
	var html = '';
	if ( val < 1 ) {
		html += '			<div class="zaad-star-rating pull-right ">\n';
		html += '				<span class="fa fa-star-o" data-rating="1"></span>\n';
		html += '				<span class="fa fa-star-o" data-rating="2"></span>\n';
		html += '				<span class="fa fa-star-o" data-rating="3"></span>\n';
		html += '				<span class="fa fa-star-o" data-rating="4"></span>\n';
		html += '				<span class="fa fa-star-o" data-rating="5"></span>\n';
		html += '			</div> \n';
	} else if ( val < 2 ) {
		html += '			<div class="zaad-star-rating pull-right ">\n';
		html += '				<span class="fa fa-star" data-rating="1"></span>\n';
		html += '				<span class="fa fa-star-o" data-rating="2"></span>\n';
		html += '				<span class="fa fa-star-o" data-rating="3"></span>\n';
		html += '				<span class="fa fa-star-o" data-rating="4"></span>\n';
		html += '				<span class="fa fa-star-o" data-rating="5"></span>\n';
		html += '			</div> \n';
	} else if ( val < 3 ) {
		html += '			<div class="zaad-star-rating pull-right ">\n';
		html += '				<span class="fa fa-star" data-rating="1"></span>\n';
		html += '				<span class="fa fa-star" data-rating="2"></span>\n';
		html += '				<span class="fa fa-star-o" data-rating="3"></span>\n';
		html += '				<span class="fa fa-star-o" data-rating="4"></span>\n';
		html += '				<span class="fa fa-star-o" data-rating="5"></span>\n';
		html += '			</div> \n';
	} else if ( val < 4 ) {
		html += '			<div class="zaad-star-rating pull-right ">\n';
		html += '				<span class="fa fa-star" data-rating="1"></span>\n';
		html += '				<span class="fa fa-star" data-rating="2"></span>\n';
		html += '				<span class="fa fa-star" data-rating="3"></span>\n';
		html += '				<span class="fa fa-star-o" data-rating="4"></span>\n';
		html += '				<span class="fa fa-star-o" data-rating="5"></span>\n';
		html += '			</div> \n';
	} else if ( val < 4 ) {
		html += '			<div class="zaad-star-rating pull-right ">\n';
		html += '				<span class="fa fa-star" data-rating="1"></span>\n';
		html += '				<span class="fa fa-star" data-rating="2"></span>\n';
		html += '				<span class="fa fa-star" data-rating="3"></span>\n';
		html += '				<span class="fa fa-star" data-rating="4"></span>\n';
		html += '				<span class="fa fa-star-o" data-rating="5"></span>\n';
		html += '			</div> \n';
	} else {
		html += '			<div class="zaad-star-rating pull-right ">\n';
		html += '				<span class="fa fa-star" data-rating="1"></span>\n';
		html += '				<span class="fa fa-star" data-rating="2"></span>\n';
		html += '				<span class="fa fa-star" data-rating="3"></span>\n';
		html += '				<span class="fa fa-star" data-rating="4"></span>\n';
		html += '				<span class="fa fa-star" data-rating="5"></span>\n';
		html += '			</div> \n';
	}
	
	return html;
};