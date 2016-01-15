<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<p style="TEXT-ALIGN: center" align="center"><span style="FONT-FAMILY: 돋움체,dotumche,applegothic; COLOR: #61b977; FONT-SIZE: 10pt"><img src="http://postfiles11.naver.net/20140307_218/ranee2011_1394202120020LSnwf_JPEG/2014-03-07_23%3B21%3B37.jpg?type=w1" width="619" height="711" style="cursor: pointer;BORDER-BOTTOM-COLOR: rgb(0,0,0); BORDER-TOP-COLOR: rgb(0,0,0); BORDER-RIGHT-COLOR: rgb(0,0,0); BORDER-LEFT-COLOR: rgb(0,0,0); rwidth: 619px; rheight: 711px" id="20140307_218/ranee2011_1394202120020LSnwf_JPEG/2014-03-07_23%3B21%3B37.jpg" onclick="popview(this, '90000003_0000000000000019A7B60F19')" alt="" class="_photoImage">&nbsp;&#8203;</span> </p>

<img src="http://postfiles11.naver.net/20140307_218/ranee2011_1394202120020LSnwf_JPEG/2014-03-07_23%3B21%3B37.jpg?type=w1">
<table>
<c:forEach items="${requestScope.resultList}" var="postingList">
<tr>
	<td colspan="2"><a href = "${postingList.postingUrl}">${postingList.postingTitle}</a></td><td colspan="3">2016.01.07</td>
</tr>
<tr>
	<td colspan="2" rowspan="2"><img src = "${postingList.postingPhotoLink}"></td><td colspan="3">라니모아</td>
</tr>
<tr>
	<td colspan="3">${postingList.postingSummary}</td>
</tr>
<tr>
	<td>${postingList.postingScore}점</td><td>${postingList.smallProduct}</td><td>스크랩 : ${postingList.postingScrapeCount}</td>
	<td>좋아요 : ${postingList.postingLikeCount}</td><td>싫어요 : ${postingList.postingDislikeCount}</td>
</tr>
</c:forEach>
</table>
</body>
</html>