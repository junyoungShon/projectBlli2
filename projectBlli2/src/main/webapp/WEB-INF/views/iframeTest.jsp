<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html style="height: 100%">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<script src="//code.jquery.com/jquery-1.12.0.min.js"></script>
<script src="//code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		$('.backBtn').click(function(){
			/* window.location.replace('http://localhost:8888/projectBlli2/iframeTest.do'); */
			window.history.back();
		});
	});
</script>
<body style="height: 100%; margin: 0; overflow-y: hidden " onunLoad="return window.location.replace(self.location);">
<div class="topMenu" style="width: 100%;height: 10%">
	여기가 상단바 
	<!-- <a href="javascript:history.back()">뒤로 가기</a> -->
	<input type="button" value="이전페이지가기" class="backBtn"><input type="hidden" >
</div>
<iframe id="socialbar-frame" name="socialbar-frame" 
height="90%" width="100%" src="http://blog.naver.com/open8370/50184783151" frameborder="0"></iframe>
</body>
</html>