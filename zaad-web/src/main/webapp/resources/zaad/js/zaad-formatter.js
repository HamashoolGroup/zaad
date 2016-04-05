$z.formatter = {};
$z.formatter.url = {};
$z.formatter.number = {};
$z.formatter.date = {};
$z.formatter.string = {};

$z.formatter.url.getVideoUrlById = function(id, rel, autohide, showinfo) {
	if ( !rel ) {
		rel = 0;
	}
	if ( !autohide ) {
		autohide = 1;
	}
	if ( !showinfo ) {
		showinfo = 0;
	}
	return $z.C.YOUTUBE + "/embed/" + id + "?rel=" + rel + "&autohide=" + autohide + "&showinfo=" + showinfo;
};

$z.formatter.url.getVideoDefaultImgUrlById = function(id) {
	return $z.C.YOUTUBE_IMG + "/" + id + "/default.jpg";
};

$z.formatter.url.getVideoMqImgUrlById = function(id) {
	return $z.C.YOUTUBE_IMG + "/" + id + "/mqdefault.jpg";
};

$z.formatter.url.getVideoHqImgUrlById = function(id) {
	return $z.C.YOUTUBE_IMG + "/" + id + "/hqdefault.jpg";
};

$z.formatter.number.digits = function(number) {
	return (number + "").replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,");
};

$z.formatter.number.friendly = function(number, digits) {
	var num = number;
	
    //var units = ['k', 'M', 'G', 'T', 'P', 'E', 'Z', 'Y'];
	var units = ['k', 'M']
    var decimal;

    for(var i=units.length-1; i>=0; i--) {
        decimal = Math.pow(1000, i+1);

        if(num <= -decimal || num >= decimal) {
            return $z.formatter.number.digits(+(num / decimal).toFixed(digits)) + units[i];
        }
    }

    return num;
}

$z.formatter.number.doubleFriendly = function(number) {
	var digits = 1;
	return parseFloat(Math.round(number * 10) / 10).toFixed(digits);
};

$z.formatter.date.friendly = function(timestamp) {
	return $.timeago(new Date(timestamp));
};

$z.formatter.date.standard = function(timestamp) {
	return (new Date(timestamp)).toDateString();
};


$z.formatter.string.friendly = function(str, max_length) {
	if ( str.length <= max_length ) {
		return str;
	} else {
		return str.substring(0, max_length) + "..";
	}
};

$z.formatter.string.titleFriendly = function(str) {
	return $z.formatter.string.friendly(str, 50);
};

$z.formatter.string.tutorNameFriendly = function(str) {
	return $z.formatter.string.friendly(str, 25);
};