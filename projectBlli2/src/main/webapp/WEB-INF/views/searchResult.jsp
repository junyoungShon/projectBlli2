<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
html, body, div{margin: 0;padding: 0;border: 0;font-size: 100%;font: inherit;vertical-align: baseline;}
.jbMenu {background-color: #2d2d2d;width: 100%;height:82px;border-bottom:2px solid #fee04a;position:relative;z-index:1;}
.in_fr{width:1040px;margin:auto;}
.logo{float:left;}
.top_search{margin-left:40px;	margin-top:24px;float:left;}
.search_text{width:320px;	height:33px;border-radius:5px;border:none;}
.top_nav{color:#fff;font-size:16px;float:right;font-family:'Nanum Barun Gothic';line-height:80px;}
.top_nav a{text-decoration:none;color:#fff;}
.product_text{width:auto;height:200px;background:#f7f7f7;padding:10px;font-family:'Nanum Barun Gothic';font-size: small;
margin-left:0px;line-height:20px;overflow-y:scroll;}
.result_num{width: 100%;float:left;font-size:135px;font-family:'Nanum Barun Gothic';font-weight:bold;text-align:center;
line-height:300px;}
</style>
<script src="//code.jquery.com/jquery-1.12.0.min.js"></script>
<script src="//code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	var count = 2;
	var searchWord = "${requestScope.searchWord}";
	var totalPage = ${requestScope.totalPage};
	var order = 6;
	
	//스크롤 이벤트
	$(window).on("scroll",function () {
		infiniteScroll();
	});
	
	//스크롤 감지 및 호출
	function infiniteScroll(){
		var deviceMargin = 0; // 기기별 상단 마진
		var $scrollTop = $(window).scrollTop();
		var $contentsHeight = Math.max($("html").height(),$("#body").height());
		var $screenHeight = window.innerHeight || document.documentElement.clientHeight || 
								document.getElementsByTagName("body")[0].clientHeight; // 스크린 높이 구하기
		if($scrollTop ==  $contentsHeight - $screenHeight) {
			if(totalPage < count){
				$("#loading").hide();
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
				var table = "";
				for(var i=0;i<resultData.length;i++){
					table += "<tr><td rowspan='4' style='width: 20%;'>";
					table += "<div class='result_num'>"+(order++)+"</div></td>";
				    table += "<td colspan='6' style='text-overflow : ellipsis;overflow : hidden; width: 50%; height: 50px;'>";
					table += "<strong><a href='"+resultData[i].postingUrl+"' style='text-decoration:none; color: black;'>";
					table += "<NOBR><img src='${initParam.root}img/title_dot.png'> "+resultData[i].postingTitle+"</NOBR></a></strong></td>";
					table += "<td colspan='3' align='right' width='30%' height='50px'>";
					table += "<font size='2' color='silver'>"+resultData[i].postingDate+"</font></td></tr>";
					table += "<tr><td colspan='6' rowspan='2' align='center' width='50%' height='250px'>";
					table += "<div style='float: left; width: 100%; height: 100%; border: 1px solid; border-color: silver; display: table-cell;'>";
					table += "<a href='"+resultData[i].postingUrl+"'><img src='http://t1.daumcdn.net/thumb/R1024x0/?fname="
							+resultData[i].postingPhotoLink+"' style='max-width: 100%; max-height: 100%;'></a></div></td>";
					table += "<td colspan='3' width='30%' height='200px'><div class='product_text'>"+resultData[i].postingSummary+"</div></td></tr>";
					table += "<tr><td colspan='3' width='30%' align='right' height='50px'><font size='3' color='orange'>"
					+resultData[i].postingAuthor+"</font></td></tr>";
					table += "<tr><td colspan='2' align='center' width='15%' height='50px'><strong><font size='4' color='red'>"
					+resultData[i].postingScore+"점</font></strong></td>";
					table += "<td colspan='4' width='35%' height='50px'><font size='3' color='silver'>"+resultData[i].smallProduct+"</font></td>";
					table += "<td align='center' width='10%' height='50px'><font color='silver'>";
					table += "<img src='${initParam.root}image/스크랩.PNG' width='20px'> "+resultData[i].postingScrapeCount+"</font></td>";
					table += "<td align='center' width='10%' height='50px'><font color='silver'>";
					table += "<img src='${initParam.root}image/좋아요.PNG' width='15px'> "+resultData[i].postingLikeCount+"</font></td>";
					table += "<td align='center' width='10%' height='50px'><font color='silver'>";
					table += "<img src='${initParam.root}image/싫어요.PNG' width='15px'> "+resultData[i].postingDislikeCount+"</font></td></tr>";
				}
				setTimeout(function(){ // 시간 지연
					$("#body").append(table);
				}, 1000);
			}
	    });
	}
});
</script>
</head>
<body>
<div class="jbMenu">
	<div class="in_fr">
		<a href="/main.html"><img src="${initParam.root}img/top_logo.png" alt="탑로고" class="logo"></a>
		<div class="top_search">
			<input type="text" class="search_text" placeholder="검색어를 입력하세요" style="vertical-align: top;">
			<a href="#"><img src="${initParam.root}img/search.png" alt="검색"></a>
		</div>
		<div class="top_nav">
			<a href="#">회원정보수정</a>   ㅣ   <a href="#">아이정보수정</a>   ㅣ   <a href="#">스크랩</a>   
			ㅣ   <a href="#">알림</a>   ㅣ   <a href="#">아이일정</a>
		</div>
	</div>
</div>

<table id="body" align="center" width="55%" style="border-collapse: collapse; table-layout: fixed;" cellpadding="10" rules="none">
<c:forEach items="${requestScope.resultList}" var="postingList" varStatus="order">
<tr>
	<td rowspan="4" style="width: 20%;">
	<div class="result_num">${order.count}</div>
	</td>
	<td colspan="6" style="text-overflow : ellipsis;overflow : hidden; width: 50%; height: 50px;">
	<strong><a href="${postingList.postingUrl}" style="text-decoration:none; color: black;"><NOBR>
	<img src="${initParam.root}img/title_dot.png"> ${postingList.postingTitle}</NOBR></a></strong></td>
	<td colspan="3" align="right" width="30%" height="50px"><font size="2" color="silver">${postingList.postingDate}</font></td>
</tr>
<tr>
	<td colspan="6" rowspan="2" align="center" width="50%" height="250px">
	<div style="float: left; width: 100%; height: 100%; border: 1px solid; border-color: silver; display: table-cell; border-radius:5px;">
	<a href="${postingList.postingUrl}">
	<img src="http://t1.daumcdn.net/thumb/R1024x0/?fname=${postingList.postingPhotoLink}" style="max-width: 100%; max-height: 100%;">
	</a></div></td>
	<td colspan="3" width="30%" height="200px"><div class="product_text">
	${postingList.postingSummary}</div></td>
</tr>
<tr>
	<td colspan="3" width="30%" align="right" height="50px"><font size="3" color="orange">${postingList.postingAuthor}</font></td>
</tr>
<tr>
	<td colspan="2" align="center" width="15%" height="50px">
	<strong><font size="4" color="red">${postingList.postingScore}점</font></strong></td>
	<td colspan="4" width="35%" height="50px"><font size="3" color="silver">${postingList.smallProduct}</font></td>
	<td align="center" width="10%" height="50px"><font color="silver">
	<img src="${initParam.root}image/스크랩.PNG" width="20px"> ${postingList.postingScrapeCount}</font></td>
	<td align="center" width="10%" height="50px"><font color="silver">
	<img src="${initParam.root}image/좋아요.PNG" width="15px"> ${postingList.postingLikeCount}</font></td>
	<td align="center" width="10%" height="50px"><font color="silver">
	<img src="${initParam.root}image/싫어요.PNG" width="15px"> ${postingList.postingDislikeCount}</font></td>
</tr>
</c:forEach>
</table>
<p align="center"><img id="loading" src="${initParam.root}image/loading.gif" style="width: 50px"></p>
</body>
</html>