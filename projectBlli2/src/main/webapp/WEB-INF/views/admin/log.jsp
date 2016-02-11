<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
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
	margin-top: 100px;
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
<script src="//code.jquery.com/jquery-1.12.0.min.js"></script>
<script src="//code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bPopup/0.11.0/jquery.bpopup.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$(".detail").click(function(){
		var methodName = $(this).attr("name");
		var categoryCount = $(this).parent().children().eq(0).val();
		var insertCategoryCount = $(this).parent().children().eq(1).val();
		var updateCategoryCount = $(this).parent().children().eq(2).val();
		var exceptionCount = $(this).parent().children().eq(3).val();
		$("#bigCategoryCount").html(categoryCount);
		$("#insertCategoryCount").html(insertCategoryCount);
		$("#updateCategoryCount").html(updateCategoryCount);
		$("#exceptionCount").html(exceptionCount);
		if(methodName == "insertBigCategory"){
			$("#detailBigCategory").bPopup();
		}
	});
});
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>로그 조회</title>
</head>
<body>
<table>
	<tr>
		<th>번호</th>
		<th>method</th>
		<th>요청자</th>
		<th>처리 시간</th>
		<th>발생 일자</th>
		<th>상세 보기</th>
	</tr>
	<c:forEach items="${requestScope.logList}" var="log" varStatus="index">
	<tr>
		<td>${log.number}</td>
		<td>${log.methodName}</td>
		<td>${log.executor}</td>
		<td>${log.runTime}</td>
		<td>${log.startTime}</td>
		<td>
			<input type="hidden" value="${log.categoryCount}">
			<input type="hidden" value="${log.insertCategoryCount}">
			<input type="hidden" value="${log.updateCategoryCount}">
			<input type="hidden" value="${log.exceptionCount}">
			<img src="${initParam.root}img/상세보기.PNG" alt="상세보기" width="20px" class="detail" style="cursor: pointer;" name="${log.methodName}" id="${index.index}">
		</td>
	</tr>
	</c:forEach>
</table>
<div id="detailBigCategory" style="display:none; width:70%; height:70%; background-color: white;">
<table>
	<tr>
		<th>총 대분류 개수</th>
		<th>insert한 대분류 개수</th>
		<th>update한 대분류 개수</th>
		<th>Exception 발생 횟수</th>
	</tr>
	<tr>
		<td id="bigCategoryCount"></td>
		<td id="insertCategoryCount"></td>
		<td id="updateCategoryCount"></td>
		<td id="exceptionCount"></td>
	</tr>
	<tr>
		<th>Exception이 발생한 bigCategoryId</th>
		<th>Exception 내용</th>
		<th>Exception이 발생한 bigCategoryId</th>
		<th>Exception 내용</th>
	</tr>
	<tr>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
	</tr>
</table>
</div>
</body>
</html>
