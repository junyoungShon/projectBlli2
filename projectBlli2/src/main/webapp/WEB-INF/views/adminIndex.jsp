<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="//code.jquery.com/jquery-1.12.0.min.js"></script>
<script src="//code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
<link rel="stylesheet" href="${initParam.root}css/flickity.css" media="screen">
<script src="${initParam.root}js/flickity.pkgd.min.js"></script>
</head>
<body>
<a href="iframeTest.do">아이프레임테스트</a>

<form action="${initParam.root}searchSmallProduct.do">
<h1>이곳은 관리자 페이지 인덱스입니다 관리자만 접근할 수 있어요.</h1>
	<ul>
		<li><a href="${initParam.root}member_goMain.do">goMain.do</a></li>
		<li><a href="${initParam.root}sendMail.do?memberId=sk159753&mailForm=findPassword">sendMail.do</a></li>
		<li><a href="${initParam.root}insertBigCategory.do">대분류 리스트 긁어모아</a></li>
		<li><a href="${initParam.root}insertMidCategory.do">중분류 리스트 긁어모아</a></li>
		<li><a href="${initParam.root}insertSmallProduct.do">소분류 리스트 긁어모아</a></li>
		<li><a href="${initParam.root}insertPosting.do">포스팅 리스트 긁어모아</a></li>
		<li><input type = "text" name="searchWord"><input type="submit" value="검색"></li>
		<li><a href="${initParam.root}postingListWithSmallProducts.do">소제품 하나로 추려줘</a></li>
		<li><a href="${initParam.root}unconfirmedSmallProduct.do">소제품 등록해줘</a></li>
		<li><a href="${initParam.root}unconfirmedPosting.do">포스팅 등록해줘</a></li>
		<li><a href="${initParam.root}j_spring_security_logout">로그아웃</a></li>
	</ul>
	<div class="gallery js-flickity"
   data-flickity-options='{ "lazyLoad": 2, "initialIndex": 2 }'>
  <div class="gallery-cell">
    <img class="gallery-cell-image"
      data-flickity-lazyload="https://s3-us-west-2.amazonaws.com/s.cdpn.io/82/tulip.jpg" alt="tulip" />
  </div>
  <div class="gallery-cell">
    <img class="gallery-cell-image"
      data-flickity-lazyload="https://s3-us-west-2.amazonaws.com/s.cdpn.io/82/grapes.jpg" alt="grapes" />
  </div>
  <div class="gallery-cell">
    <img class="gallery-cell-image"
      data-flickity-lazyload="https://s3-us-west-2.amazonaws.com/s.cdpn.io/82/raspberries.jpg" alt="raspberries" />
  </div>
  <div class="gallery-cell">
    <img class="gallery-cell-image"
      data-flickity-lazyload="https://s3-us-west-2.amazonaws.com/s.cdpn.io/82/wolf.jpg" alt="wolf" />
  </div>
  <div class="gallery-cell">
    <img class="gallery-cell-image"
      data-flickity-lazyload="https://s3-us-west-2.amazonaws.com/s.cdpn.io/82/shore.jpg" alt="sea" />
  </div>
  <div class="gallery-cell">
    <img class="gallery-cell-image"
      data-flickity-lazyload="https://s3-us-west-2.amazonaws.com/s.cdpn.io/82/leaf-droplets.jpg" alt="leaf droplets" />
  </div>
  <div class="gallery-cell">
    <img class="gallery-cell-image"
      data-flickity-lazyload="https://s3-us-west-2.amazonaws.com/s.cdpn.io/82/gulls.jpg" alt="gulls" />
  </div>
  <div class="gallery-cell">
    <img class="gallery-cell-image"
      data-flickity-lazyload="https://s3-us-west-2.amazonaws.com/s.cdpn.io/82/touch-screen.jpg" alt="touch screen" />
  </div>
  <div class="gallery-cell">
    <img class="gallery-cell-image"
      data-flickity-lazyload="https://s3-us-west-2.amazonaws.com/s.cdpn.io/82/van.jpg" alt="van" />
  </div>
</div>
	
</form>
<!--
  아래는 소셜 플러그인으로 로그인 버튼을 넣는다.
  이 버튼은 자바스크립트 SDK에 그래픽 기반의 로그인 버튼을 넣어서 클릭시 FB.login() 함수를 실행하게 된다.
-->

</body>
<!-- Google 애널리틱스 추적코드 -->
<script>
(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
})(window,document,'script','//www.google-analytics.com/analytics.js','ga');

ga('create', 'UA-72734813-1', {'cookieDomain': 'none'});
ga('send', 'pageview');

</script>
</html>
