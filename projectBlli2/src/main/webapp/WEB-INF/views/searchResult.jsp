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
<table align="center" width="50%" border="1" bordercolor="silver" style="border-collapse: collapse; table-layout: fixed;" cellpadding="10" rules="none">
<c:forEach items="${requestScope.resultList}" var="postingList">
<tr>
	<td colspan="6" width="70%" style="text-overflow : ellipsis;overflow : hidden;">
	<strong><a href="${postingList.postingUrl}" style="text-decoration:none; color: black;"><NOBR>${postingList.postingTitle}</NOBR></a></strong></td>
	<td colspan="3" align="right" width="30%"><font size="2" color="silver">${postingList.postingDate}</font></td>
</tr>
<tr>
	<td colspan="6" rowspan="2" align="center" width="70%"><div style="width: 400px; height: 300px; border: 1px solid; border-color: silver; display: table-cell; vertical-align: middle;">
	<a href="${postingList.postingUrl}"><img src="http://t1.daumcdn.net/thumb/R1024x0/?fname=${postingList.postingPhotoLink}" style="max-width: 100%; max-height: 100%;"></a></div></td>
	<td colspan="3" width="30%" style="word-break:break-all;"><font size="2">${postingList.postingSummary}</font></td>
</tr>
<tr>
	<td colspan="3" width="30%" align="right"><font size="3" color="orange">${postingList.postingAuthor}</font></td>
</tr>
<tr style="border: 1px solid silver;">
	<td colspan="2" align="center" width="20%"><strong><font size="4" color="red">${postingList.postingScore}점</font></strong></td>
	<td colspan="4" width="40%"><font size="3" color="silver">${postingList.smallProduct}</font></td>
	<td align="center" width="10%"><font color="silver"><img src="${initParam.root}image/스크랩.PNG" width="20px"> ${postingList.postingScrapeCount}</font></td>
	<td align="center" width="10%"><font color="silver"><img src="${initParam.root}image/좋아요.PNG" width="15px"> ${postingList.postingLikeCount}</font></td>
	<td align="center" width="10%"><font color="silver"><img src="${initParam.root}image/싫어요.PNG" width="15px"> ${postingList.postingDislikeCount}</font></td>
</tr>
</c:forEach>
</table>
</body>
</html>