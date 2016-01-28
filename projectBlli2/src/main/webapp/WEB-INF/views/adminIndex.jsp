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
</head>
<body>
<a href="iframeTest.do">아이프레임테스트</a>

<form action="${initParam.root}search_jsoupTest.do">
<h1>이곳은 관리자 페이지 인덱스입니다 관리자만 접근할 수 있어요.</h1>
	<ul>
		<li><a href="${initParam.root}member_goMain.do">goMain.do</a>
		<li><a href="${initParam.root}sendMail.do?memberId=sk159753&mailForm=findPassword">sendMail.do</a>
		<li><a href="${initParam.root}insert_big_category.do">대분류 리스트 긁어모아</a>
		<li><a href="${initParam.root}insert_mid_category.do">중분류 리스트 긁어모아</a>
		<li><a href="${initParam.root}insert_small_product.do">소분류 리스트 긁어모아</a>
		<li><a href="${initParam.root}schedule_jsoupTest.do">포스팅 리스트 긁어모아</a>
		<li><input type = "text" name="searchWord"><input type="submit" value="검색">
		<li><a href="${initParam.root}postingListWithSmallProducts.do">소제품 하나로 추려줘</a></li>
		<li><a href="${initParam.root}j_spring_security_logout">로그아웃</a>
	</ul>
	
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
