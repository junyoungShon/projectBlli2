<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<!-- 회원가입페이지에 ROLE_USER접근 시 alert과 메인페이지 이동 -->
	<sec:authorize access="hasRole('ROLE_USER','ROLE_ADMIN')">
		<script type="text/javascript">
			alert('이미 회원가입은 완료하셨습니다^^ 메인으로 이동할게요~');
			location.href=${initParam.root}+'member_proceedingToMain.do';
		</script>
	</sec:authorize>
	
	<!-- 회원가입페이지에 ROLE_RESTRICTED 접근 시 아이정보 입력 페이지 이동 -->
	<sec:authorize access="hasRole('ROLE_RESTRICTED')">
		<script type="text/javascript">
			location.href=${initParam.root}+'login.do';
		</script>
	</sec:authorize>
	
	<!-- 회원가입페이지에 비회원 접근 시 회원가입 폼 출력 -->
	<sec:authorize access="isAnoymous()">
		<form name="memberJoin" action="goInsertBabyInfoByEmail.do" method="post">
			회원아이디(이메일)<input type="text" name="memberId"><br>
			회원 비밀번호<input type="password" name="memberPassword"><br>
			회원 이름<input type="text" name="memberName"><br>
			<input type="submit" value="아이정보 입력하러가기!">
		</form>
	</sec:authorize>
	
</body>
</html>