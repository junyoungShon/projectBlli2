<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>싫어요</title>
<style type="text/css">
html, body {
	width: 100%;
	height: 100%;
}
iframe {
	width: 100%;
	height: 90%;
}
.detailPosting{
	display: inline-block;
	margin: 20px;
}
</style>
<link href="${initParam.root}css/css.css" rel="stylesheet" />
<link href="${initParam.root}css/font-awesome.min.css" rel="stylesheet" />
<script src="//code.jquery.com/jquery-1.12.0.min.js"></script>
<script src="//code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bPopup/0.11.0/jquery.bpopup.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	var postingUrl;
	$(".main_left_con").css("width", "90%");
	$(".main_left_con").css("float", "none");
	$(".main_left_con").css("margin", "auto");
	$(".main_left_con").css("text-align", "center");
	$(".postingImg").click(function(){
		postingUrl = $(this).parent().parent().next().children().eq(1).children(".postingUrlInfo").val();
		var postingTitle = $(this).parent().parent().prev().children().first().text();
		var scrapeCount = $(this).parent().parent().next().children().eq(1).children(".scrapeCount").text();
		var likeCount = $(this).parent().parent().next().children().eq(1).children(".likeCount").text();
		var dislikeCount = $(this).parent().parent().next().children().eq(1).children(".disLikeCount").text();
		$("#postingTitle").text(postingTitle);
		$(".top_blog2 .scrapeCount").text(scrapeCount);
		$(".top_blog2 .likeCount").text(likeCount);
		$(".top_blog2 .disLikeCount").text(dislikeCount);
		$("#postingPopUp").bPopup({
			content:"iframe",
			loadUrl:postingUrl,
			onClose: function(){
				parent.location.reload(true); 
			}
		});
	});
	
	$(".fa.fa-floppy-o.fa-lg").click(function(){
		if(confirm("광고 포스팅이 아닙니까?")){
			$.ajax({
				url:"notAdvertisingPosting.do",
				type:"post",
				data:"postingUrl="+postingUrl,
				success:function(){
					alert("확정 완료");
					$("#postingPopUp").bPopup().close();
					parent.location.reload(true);
				},
				error:function(jqXHR, textStatus, errorThrown){
		            alert("에러 발생~~ \n" + textStatus + " : " + errorThrown);
		        }
			});
		}
	});
	
	$(".fa.fa-trash.fa-lg").click(function(){
		if(confirm("삭제하시겠습니까?")){
			$.ajax({
				url:"deletePosting.do",
				type:"post",
				data:"postingUrl="+postingUrl,
				success:function(){
					alert("삭제 완료");
					$("#postingPopUp").bPopup().close();
					parent.location.reload(true);
				},
				error:function(jqXHR, textStatus, errorThrown){
		            alert("에러 발생~~ \n" + textStatus + " : " + errorThrown);
		        }
			});
		}
	});
});
</script>
</head>
<body>
<div class="main_left_con">
	<c:forEach items="${requestScope.postingList}" var="postingList">
	<div style="height:325px; border: 1px solid #ccc;border-radius: 10px; margin-bottom:30px;" class="detailPosting">
		<div class="result_ti">
			<div style='width:450px; overflow:hidden;white-space:nowrap; text-overflow:ellipsis;'>
					${postingList.postingTitle}
			</div>
		</div>
		<div style="height:245px;">
			<div class="result_foto fl">
				<img src="${postingList.postingPhotoLink}" style="width: 335px; height: 235px; MARGIN-LEFT:5px; cursor: pointer;" class="postingImg">
			</div>
			<div class="fl">
				<div class="product_text2">
					${postingList.postingSummary}
				</div>
				<div class="product_id">
					<div style='width:180px; overflow:hidden;white-space:nowrap; text-overflow:ellipsis;'>
						${postingList.postingAuthor}
					</div>
				</div>
			</div>
		</div>
		<div class="product_sns">
			<div class="fl score">
				${postingList.postingScore}점
			</div>
			<div class="fr sns">
				<input type="hidden" class="smallProductIdInfo" value="${postingList.smallProductId}">
				<input type="hidden" class="postingUrlInfo" value="${postingList.postingUrl}">
				<i class="fa fa-bookmark-o postingScrapeBtn" style="color: gray"></i>
				<span style="font-size: 11px; line-height: 16px ;margin-right:15px" class="scrapeCount">${postingList.postingScrapeCount}</span>
				<i class="fa fa-thumbs-up postingLikeBtn" style="color: gray"></i>
				<span style="font-size: 11px; line-height: 16px ;margin-right:15px" class="likeCount">${postingList.postingLikeCount}</span>
				<i class="fa fa-thumbs-down postingDisLikeBtn" style="color: gray"></i>
				<span style="font-size: 11px; line-height: 16px ;margin-right:15px" class="disLikeCount">${postingList.postingDislikeCount}</span>
			</div>
		</div>
	</div>
	</c:forEach>
</div>
<div id="postingPopUp" style="display: none; width: 80%; height: 80%;">
	<div class="jbMenu" style="height: 10%;">
      <div class="in_fr">
		<img src="./img/top_logo.png" alt="탑로고" class="logo">
		<div class="top_blog">
			<div style='width:450px; overflow:hidden;white-space:nowrap; text-overflow:ellipsis;' id="postingTitle">
			</div>
		</div>
		<div class="top_blog2">
			<i class="fa fa-floppy-o fa-lg" style="color: blue; cursor: pointer;" data-tooltip-text="확정"></i> &nbsp; &nbsp;
			<i class="fa fa-bookmark-o postingScrapeBtn" style="color: gray" data-tooltip-text="스크랩"></i>
			<span style="font-size: 11px; line-height: 16px ;margin-right:15px" class="scrapeCount"></span>
			<i class="fa fa-thumbs-up postingLikeBtn" style="color: gray" data-tooltip-text="좋아요"></i>
			<span style="font-size: 11px; line-height: 16px ;margin-right:15px" class="likeCount"></span>
			<i class="fa fa-thumbs-down postingDisLikeBtn" style="color: gray" data-tooltip-text="싫어요"></i>
			<span style="font-size: 11px; line-height: 16px ;margin-right:15px" class="disLikeCount"></span>
			<i class="fa fa-trash fa-lg" style="color: red; cursor: pointer;" data-tooltip-text="삭제"></i>
		</div>
	  </div>
	</div>
</div>
</body>
</html>