<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="//code.jquery.com/jquery-1.12.0.min.js"></script>
<script src="//code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	var count = 2;
	var searchWord = "${requestScope.searchWord}";
	var totalPage = ${requestScope.totalPage};
	
	//스크롤 이벤트
	$(window).on("scroll",function () {
		infiniteScroll();
	});
	
	//스크롤 감지 및 호출
	function infiniteScroll(){
		var deviceMargin = 0; // 기기별 상단 마진
		var $scrollTop = $(window).scrollTop();
		var $contentsHeight = Math.max($("html").height(),$("#body").height());
		var $screenHeight = window.innerHeight || document.documentElement.clientHeight || document.getElementsByTagName("body")[0].clientHeight; // 스크린 높이 구하기
		if($scrollTop ==  $contentsHeight - $screenHeight) {
			if(totalPage < count){
				return false;
			}
			loadArticle(count);
			count++;
		}
	}
	
	//ajax 로드
	function loadArticle(pageNo){
	    $.ajax({
			type: "POST",
			url: "${initParam.root}getPostingList.do",
			data: "pageNo="+pageNo+"&searchWord="+searchWord,
			cache: false,
			success: function(resultData){
				$("#loading").show();
				var table = "";
				for(var i=0;i<resultData.length;i++){
				    table += "<tr><td colspan='6' width='70%' style='text-overflow : ellipsis;overflow : hidden;'>";
					table += "<strong><a href='"+resultData[i].postingUrl+"' style='text-decoration:none; color: black;'><NOBR>"+resultData[i].postingTitle+"</NOBR></a></strong></td>";
					table += "<td colspan='3' align='right' width='30%'><font size='2' color='silver'>"+resultData[i].postingDate+"</font></td></tr>";
					table += "<tr><td colspan='6' rowspan='2' align='center' width='70%'><div style='width: 400px; height: 300px; border: 1px solid; border-color: silver; display: table-cell; vertical-align: middle;'>";
					table += "<a href='"+resultData[i].postingUrl+"'><img src='http://t1.daumcdn.net/thumb/R1024x0/?fname="+resultData[i].postingPhotoLink+"' style='max-width: 100%; max-height: 100%;'></a></div></td>";
					table += "<td colspan='3' width='30%' style='word-break:break-all;'><font size='2'>"+resultData[i].postingSummary+"</font></td></tr>";
					table += "<tr><td colspan='3' width='30%' align='right'><font size='3' color='orange'>"+resultData[i].postingAuthor+"</font></td></tr>";
					table += "<tr style='border: 1px solid silver;'>";
					table += "<td colspan='2' align='center' width='20%'><strong><font size='4' color='red'>"+resultData[i].postingScore+"점</font></strong></td>";
					table += "<td colspan='4' width='40%'><font size='3' color='silver'>"+resultData[i].smallProduct+"</font></td>";
					table += "<td align='center' width='10%'><font color='silver'><img src='${initParam.root}image/스크랩.PNG' width='20px'> "+resultData[i].postingScrapeCount+"</font></td>";
					table += "<td align='center' width='10%'><font color='silver'><img src='${initParam.root}image/좋아요.PNG' width='15px'> "+resultData[i].postingLikeCount+"</font></td>";
					table += "<td align='center' width='10%'><font color='silver'><img src='${initParam.root}image/싫어요.PNG' width='15px'> "+resultData[i].postingDislikeCount+"</font></td></tr>";
				}
				setTimeout(function(){ // 시간 지연
					$("#body").append(table);
					$("#loading").hide();
				}, 1000);
			}
	    });
	}
});
</script>
</head>
<body>
<table id="body" align="center" width="50%" border="1" bordercolor="silver" style="border-collapse: collapse; table-layout: fixed;" cellpadding="10" rules="none">
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
<p align="center"><img id="loading" src="${initParam.root}image/loading.gif" style="width: 50px"></p>
</body>
</html>