<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	${requestScope.blliMemberVO}
	<form action="joinMemberByEmail.do" method="post">
		<c:set var="blliMemberVO" value="${requestScope.blliMemberVO}" scope="request"/>
		아이 이름<input type="text" name="babyName"><br>
		아이 생일<input type="date" name="babyBirthday"><br>
		<input type="submit" value="회원가입 완료">
		<input type="hidden" value="${requestScope.blliMemberVO}">
	</form>
</body>
</html>