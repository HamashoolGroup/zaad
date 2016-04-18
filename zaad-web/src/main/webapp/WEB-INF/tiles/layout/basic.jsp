<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<title>${seo.title}</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<meta name="keywords" content="${seo.keywordsAsString}">
<meta name="description" content="${seo.description}">
<meta name="author" content="${seo.author}">


<meta property="og:site_name" content="${site_name}">
<meta property="og:type" content="${og_type}">
<meta property="og:title" content="${seo.title}">
<meta property="og:description" content="${seo.description}">
<meta property="og:image" content="${og_img}">
<meta property="og:url" content="${url}">

<!-- site registration -->
<meta name="naver-site-verification" content="75f93d22a88862cd3faea521bec8ea42cfb65284"/>
<meta name="msvalidate.01" content="7BC41492C3729B4E9C7C32D5FA2B0710" />


<!-- FAVICON -->
<link rel="shortcut icon" href="${pageContext.servletContext.contextPath}/resources/img/zaad/favicon.png">
	
<link href="${pageContext.servletContext.contextPath}/resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.servletContext.contextPath}/resources/bootstrap/font-awesome-4.3.0/css/font-awesome.min.css" rel="stylesheet">
<link href="${pageContext.servletContext.contextPath}/resources/theme/css/cs-select.css" rel="stylesheet" >  
<link href="${pageContext.servletContext.contextPath}/resources/theme/css/animate.css" rel="stylesheet" >  
<link href="${pageContext.servletContext.contextPath}/resources/theme/css/nanoscroller.css" rel="stylesheet" >  
<link href="${pageContext.servletContext.contextPath}/resources/theme/css/owl.carousel.css" rel="stylesheet" >  
<link href="${pageContext.servletContext.contextPath}/resources/theme/css/flexslider.css" rel="stylesheet" >  
<link href="${pageContext.servletContext.contextPath}/resources/theme/css/style.css" rel="stylesheet" >  
<link href="${pageContext.servletContext.contextPath}/resources/theme/css/main.css" rel="stylesheet" >  
<link href="${pageContext.servletContext.contextPath}/resources/theme/css/responsive.css" rel="stylesheet" >  
<link href="${pageContext.servletContext.contextPath}/resources/zaad/css/zaad.css" rel="stylesheet" >  

<script src="${pageContext.servletContext.contextPath}/resources/jquery/jquery-1.11.2.min.js" type="text/javascript"></script>
</head>
<body class="sticky-header">
	<div class="body-innerwrapper">
		<tiles:insertAttribute name="header" />
		<tiles:insertAttribute name="body" />
		<tiles:insertAttribute name="footer" />
		
		<script src="${pageContext.servletContext.contextPath}/resources/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
		
		<script src="${pageContext.servletContext.contextPath}/resources/theme/js/smoothscroll.js" type="text/javascript"></script>
		<!-- select menu -->
		<script src="${pageContext.servletContext.contextPath}/resources/theme/js/classie.js" type="text/javascript"></script>
		<script src="${pageContext.servletContext.contextPath}/resources/theme/js/selectFx.js" type="text/javascript"></script>
		<!-- slider -->
		<script src="${pageContext.servletContext.contextPath}/resources/theme/js/jquery.nanoscroller.js" type="text/javascript"></script>
		<script src="${pageContext.servletContext.contextPath}/resources/theme/js/owl.carousel.min.js" type="text/javascript"></script>
		<script src="${pageContext.servletContext.contextPath}/resources/theme/js/jquery.flexslider-min.js" type="text/javascript"></script>
		<!-- sitcky menu -->
		<script src="${pageContext.servletContext.contextPath}/resources/theme/js/jquery.sticky.js" type="text/javascript"></script>
		<!-- custom js -->
		<script src="${pageContext.servletContext.contextPath}/resources/theme/js/main.js" type="text/javascript"></script>

		<!-- plubin js -->
		<script src="${pageContext.servletContext.contextPath}/resources/jquery/plugin/jquery.timeago.js" type="text/javascript"></script>
		
		<!-- zaad js -->
		<script src="${pageContext.servletContext.contextPath}/resources/zaad/js/zaad-ns.js" type="text/javascript"></script>
		<script src="${pageContext.servletContext.contextPath}/resources/zaad/js/zaad-onload.js" type="text/javascript"></script>
		<script src="${pageContext.servletContext.contextPath}/resources/zaad/js/zaad-formatter.js" type="text/javascript"></script>
		<script src="${pageContext.servletContext.contextPath}/resources/zaad/js/zaad-link.js" type="text/javascript"></script>
		<script src="${pageContext.servletContext.contextPath}/resources/zaad/js/zaad-util.js" type="text/javascript"></script>
		<script src="${pageContext.servletContext.contextPath}/resources/zaad/js/zaad-seo.js" type="text/javascript"></script>
		
		<script src="${pageContext.servletContext.contextPath}/resources/zaad/js/zaad-ajax.js" type="text/javascript"></script>
		
		<!-- share this -->
		<script type="text/javascript">var switchTo5x=true;</script>
		<script type="text/javascript" src="http://w.sharethis.com/button/buttons.js"></script>
		<script type="text/javascript">stLight.options({publisher: "70a4ce05-5808-4559-b6d1-5948f5412a7d", doNotHash: false, doNotCopy: false, hashAddressBar: false});</script>
	</div>
	
	<%
		if ("".equals(pageContext.getServletContext().getContextPath()) ) {
	%>
		<script>
		(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
		(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
		m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
		})(window,document,'script','//www.google-analytics.com/analytics.js','ga');
		
		ga('create', 'UA-75504391-1', 'auto');
		ga('send', 'pageview');

		</script>
	<%
		}
	%>
	
</body>
</html>