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