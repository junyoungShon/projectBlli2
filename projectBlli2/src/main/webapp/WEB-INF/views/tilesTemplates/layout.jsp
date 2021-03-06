<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<!-- 소제품 상세페이지보기페이지에서만 작동한다. -->
<c:if test="${fn:startsWith(requestScope['javax.servlet.forward.request_uri'],'/projectBlli2/goSmallProductDetailView.do')}">
    <meta name="description" content="블리를 통해 충동구매보다 빠르게 합리적인 쇼핑을 즐기세요.">
    <meta name="keywords" content="유아용품,큐레이션,블로그,포스팅">
	<meta property="og:url"           content="http://bllidev.dev/${requestScope['javax.servlet.forward.request_uri']}" />
	<meta property="og:type"          content="website" />
	<meta property="og:title"         content="블리-충동구매보다 빠른 합리적 소비" />
	<meta property="og:description"   content="블리를 통해 충동구매보다 빠르게 합리적인 쇼핑을 즐기세요." />
	<meta property="og:image"         content="http://bllidev.dev/projectBlli2/scrawlImage/smallProduct/${requestScope.smallProductInfo.smallProduct.smallProductMainPhotoLink}" />
</c:if>

<title>블리 - 충동구매보다 빠른 합리적 쇼핑!</title>
<link href="${initParam.root}img/favicon/favicon.ico" rel="shortcut icon" type="image/x-icon" />
<%-- <!-- 부트스트랩 -->
<link href="${initParam.root}css/bootstrap.css" rel="stylesheet">
<script src="${initParam.root}js/ie-emulation-modes-warning.js"></script> --%>

<!-- IE8 에서 HTML5 요소와 미디어 쿼리를 위한 HTML5 shim 와 Respond.js -->
<!-- WARNING: Respond.js 는 당신이 file:// 을 통해 페이지를 볼 때는 동작하지 않습니다. -->
<!--[if lt IE 9]>
<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
<![endif]-->
<!-- 부트스트랩 사용을 위한 상단 설정 완료 -->
 <!-- 슬라이드 쇼를 위한 flickity api -->
<link rel="stylesheet" href="${initParam.root}css/flickity.css" media="screen">
 <!-- 아이콘 만들기 api font-awesome -->
<link href="${initParam.root}css/font-awesome.min.css" rel="stylesheet" />
 <!-- 초기화 css -->
<link href="${initParam.root}css/reset.css" rel="stylesheet" />
 <!-- 메인 css -->
<link href="${initParam.root}css/css.css" rel="stylesheet" />
<!-- 폰트 -->
<link href="https://cdn.rawgit.com/openhiun/hangul/14c0f6faa2941116bb53001d6a7dcd5e82300c3f/nanumbarungothic.css" rel="stylesheet" type="text/css">
<!-- 부트 스트랩 사용을 위한 하단 설정 -->
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<!-- jQuery (부트스트랩의 자바스크립트 플러그인을 위해 필요합니다) -->
<script src="${initParam.root}js/jquery.js"></script>
<script src="${initParam.root}js/jquery-ui.min.js"></script>
 <!-- 슬라이드 쇼를 위한 flickity api -->
<script src="${initParam.root}js/flickity.pkgd.min.js"></script>
<!-- 모든 컴파일된 플러그인을 포함합니다 (아래), 원하지 않는다면 필요한 각각의 파일을 포함하세요 -->
<%-- <script src="${initParam.root}js/bootstrap.min.js"></script>
<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<script	src="${initParam.root}js/ie10-viewport-bug-workaround.js"></script> --%>
<!-- 부트 스트랩 사용을 위한 하단 설정 완료 -->
<!-- jquery UI -->
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>

<link rel="stylesheet" type="text/css" href="${initParam.root}css/rjaccordion.css">

<script type="text/javascript" src="${initParam.root}js/rjAccordion.js"></script>
<script type="text/javascript">
  $(function() {       
	  $(".accordion1").rjAccordion();          

	  $(".accordion2").rjAccordion({toggle:false});          
  });
</script>

<script type="text/javascript">
/* 	$(document).ready(function () {
		$('#menu-wrapper').boxymenu();
	}); */
</script>


<script>
/* $( document ).ready( function() {
	var jbOffset = $( '.jbMenu' ).offset();
	$( window ).scroll( function() {
	  if ( $( document ).scrollTop() > jbOffset.top ) {
		$( '.jbMenu' ).addClass( 'jbFixed' );
	  }
	  else {
		$( '.jbMenu' ).removeClass( 'jbFixed' );
	  }
	});
  } ); */
</script>
</head>


<body>
   
      <!-- Header -->
      <tiles:insertAttribute name="header"/>
 	 <!-- <div class="jbContent"> -->
 	<!-- main -->
 	<tiles:insertAttribute name="main"/>
  	<!-- </div> -->
    <div class="container footer">
    <tiles:insertAttribute name="footer"/>
    <!-- footer -->
    </div>
    

</body>
</html>
