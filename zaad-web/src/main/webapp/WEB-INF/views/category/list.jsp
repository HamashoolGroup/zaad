<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>


<!--==================================
=            START MAIN WRAPPER      =
===================================-->
<section class="main-wrapper">
	<div class="container">
		<div class="row">
			<div class="col-sm-9">
				
				<div class="newedge-latest-videos mtb10">
					<div class="z-section-title">
						<div class="row">
							<div class="col-sm-6">
								<h2 class="pull-left"> 
									#${title}
								</h2>
							</div>
							<div class="col-sm-6">
								<div id="sort-btn" class="btn-group pull-right" role="group">
									<button type="button" class="btn btn-xs btn-default btn-trending active">Trending</button>
								  	<button type="button" class="btn btn-xs btn-default btn-popular">Popular</button>
								</div>
							</div>
						</div>
					</div>
					<!-- tag1 -->
					<div id="tag1-category-video-div-parent" style="display: none;">
						<div class="z-section-title">
							<h3 class="pull-left" id="tag1-category-video-div-title"> 
							</h3>
						</div>
						<div id="tag1-category-video-div">
						</div>
					
						<div clas="row">
							<div class="z-see-all">
								<a id="tag1-category-video-div-more" href="#tag1-category-video-div-1">-- More --</a>
							</div>
						</div>
					</div>
					<!-- //tag1 -->
					
					<!-- tag2 -->
					<div id="tag2-category-video-div-parent" style="display: none;">
						<div class="z-section-title">
							<h3 class="pull-left" id="tag2-category-video-div-title"> 
							</h3>
						</div>
						<div id="tag2-category-video-div">
						</div>
					
						<div clas="row">
							<div class="z-see-all">
								<a id="tag2-category-video-div-more" href="#tag2-category-video-div-1">-- More --</a>
							</div>
						</div>
					</div>
					<!-- //tag2 -->
					
					<!-- tag3 -->
					<div id="tag3-category-video-div-parent" style="display: none;">
						<div class="z-section-title">
							<h3 class="pull-left" id="tag3-category-video-div-title"> 
							</h3>
						</div>
						<div id="tag3-category-video-div">
						</div>
					
						<div clas="row">
							<div class="z-see-all">
								<a id="tag3-category-video-div-more" href="#tag3-category-video-div-1">-- More --</a>
							</div>
						</div>
					</div>
					<!-- //tag3 -->
					
					<!-- tag4 -->
					<div id="tag4-category-video-div-parent" style="display: none;">
						<div class="z-section-title">
							<h3 class="pull-left" id="tag4-category-video-div-title"> 
							</h3>
						</div>
						<div id="tag4-category-video-div">
						</div>
					
						<div clas="row">
							<div class="z-see-all">
								<a id="tag4-category-video-div-more" href="#tag4-category-video-div-1">-- More --</a>
							</div>
						</div>
					</div>
					<!-- //tag4 -->
				</div>
			</div> <!-- //col-sm-9 -->	


			<!--  col-sm-3 -->
			<div class="col-sm-3">
			</div> <!-- //col-sm-3 -->
		</div> <!-- //row -->
	</div> <!-- //container -->
</section>
<!--====  End of MAIN WRAPPER  ====-->


<script>
var sortType = "R";

$(document).ready(function() {
	getData("init");
	
	$('#sort-btn button').click(function() {
	    $('#sort-btn button').addClass('active').not(this).removeClass('active');
	    if ( $(this).hasClass('btn-trending') ) {
		    sortType = "R";
	    } else {
	    	sortType = "P";
	    }
	    
	    getData("");
	});
});
</script>

<script>
var getData = function(type) {
	$.ajax({
		type : "GET",
		url : "${pageContext.servletContext.contextPath}" + "/tag/related/${cat}?size=5",
		success : function(data) {
			var maxTagCnt = 4;
			for ( var i = 0; i < maxTagCnt; i++ ) {
				var firstPageNo = 1;
				listCategoryVideo(firstPageNo, data[i], i+1);
				if ( "init" == type ) {
					bindMorePage(i+1, data[i], callback);
				}
			}
		},
		dataType : "json"
	});
};


var bindMorePage = function(index, subCat, callback) {
	$('#tag' + index + '-category-video-div-more').bind('click', function() {
		$z.ajax.listVideoCol4("${pageContext.servletContext.contextPath}", "/${mapping}/${cat}/" + subCat + "/list", $(this).data('page'), $z.C.paging.list.category, "tag" + index + "-category-video-div", "next", sortType, null, subCat, callback);
	});
};

var callback = function(subCat, data, divId) {
	$('#' + divId + "-title").text("#" + subCat);
	if ( data && data.length > 0 )	{
		$('#' + divId + '-parent').show();
	}
};
</script>

<script>
var listCategoryVideo = function(firstPageNo, subCat, index) {
	$z.ajax.listVideoCol4("${pageContext.servletContext.contextPath}", "/${mapping}/${cat}/" + subCat + "/list", firstPageNo, $z.C.paging.list.category, "tag" + index + "-category-video-div", "first", sortType, null, subCat, callback);
};
</script>
