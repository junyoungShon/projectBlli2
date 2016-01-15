<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<!-- 메인가는 중간 페이지에 ROLE_USER접근 시 메인페이지 이동 -->
	<sec:authorize access="hasRole('ROLE_USER','ROLE_ADMIN')">
		<script type="text/javascript">
			location.href=${initParam.root}+'member_goMain.do';
		</script>
	</sec:authorize>
	
	<!-- 메인가는 중간 페이지에 ROLE_RESTRICTED접근 시 아이정보 입력페이지로 이동 -->
	<sec:authorize access="hasRole('ROLE_RESTRICTED')">
		<script type="text/javascript">
			location.href=${initParam.root}+'login.do';
		</script>
	</sec:authorize>
	
	<!-- 메인가는 중간 페이지에 비회원 접근 시 로그인 페이지로 이동 -->
	<sec:authorize access="isAnoymous()">
		<script type="text/javascript">
			location.href=${initParam.root}+'login.do';
		</script>
	</sec:authorize>
	
</body>
</html>