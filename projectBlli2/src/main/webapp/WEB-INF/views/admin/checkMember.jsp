<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>회원 목록</title>
<style type="text/css">
html, body{
	height: 100%;
}
table {
	width: 70%;
	line-height: 21px;
	border-top: 1px solid #cccccc;
	border-left: 1px solid #cccccc;
	border-collapse: collapse;
	margin: auto;
	table-layout: fixed;
}
table th, table td {
	color: #678197;
	text-align: center;
	border-right: 1px solid #cccccc;
	border-bottom: 1px solid #cccccc;
	padding: 3px 0;
	text-align: center;
}
table th {
	background-color: #eeeeee;
}
</style>
</head>
<body>
<div style="width: 70%; margin: auto;">
총 회원 수 : ${fn:length(requestScope.memberList)} 명
</div>
<br>
<table>
	<tr>
		<th>회원 ID</th>
		<th>e-mail</th>
		<th>회원 이름</th>
		<th>회원 구분</th>
		<th>이메일 수신 동의</th>
	</tr>
	<c:forEach items="${requestScope.memberList}" var="memberList">
	<tr>
		<td>${memberList.memberId}</td>
		<td>${memberList.memberEmail}</td>
		<td>${memberList.memberName}</td>
		<td>
		<c:if test="${memberList.authority == 'ROLE_USER'}">
		일반 회원
		</c:if>
		<c:if test="${memberList.authority == 'ROLE_RESTRICTED'}">
		아이 정보 입력하지 않은 회원
		</c:if>
		<c:if test="${memberList.authority == 'ROLE_DROP'}">
		탈퇴 회원
		</c:if>
		<c:if test="${memberList.authority == 'ROLE_ADMIN'}">
		관리자
		</c:if>
		</td>
		<td>
		<c:if test="${memberList.mailAgree == 0}">
		수신 동의
		</c:if>
		<c:if test="${memberList.mailAgree == 1}">
		수신 비동의
		</c:if>
		</td>
	</tr>
	</c:forEach>
</table>
</body>
</html>