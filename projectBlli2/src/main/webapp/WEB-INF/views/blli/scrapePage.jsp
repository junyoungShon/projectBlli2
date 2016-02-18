<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<style>
.scrape_bg_color_one {
	background-color: #FAFAFA;
	height: 255px;
}
.scrape_bg_color_two {
	background-color: white;
	height: 255px;
}
.scrape_info {
	width: 1000px;
	margin: auto;
	height: 255px;
}
.scrape_img {
	margin-top: 10px;
	margin-bottom: 10px;
	clear: both;
	float: left;
	width: 340px;
	height: 235px;
}
.scrape_content {
	margin-top: 10px;
	margin-bottom: 10px;
	float: right;
	width: 660px;
	height: 235px;
}
.scrape_posting_title {
	height:45px;
	background:url(${initParam.root}img/title_dot.png) no-repeat 5px ;
	font-family:'Nanum Barun Gothic';
	padding-left:25px;
	font-weight:bold;
	font-size:15px;
	line-height:40px;
	width: 625px;
	overflow: hidden;
	white-space: nowrap;
	text-overlow: ellipsis;
	margin-left: 10px;
}
.scrape_posting_content_one {
	width: 640px;
	height:145px;
	background:cornsilk;
	padding:5px;
	font-family:'Nanum Barun Gothic';
	margin-left:10px;
	line-height:20px;
	text-align: justify;
	margin-bottom: 5px;
	overflow-y: auto;
}
.scrape_posting_content_two {
	width: 640px;
	height:145px;
	background:cornsilk;
	padding:5px;
	font-family:'Nanum Barun Gothic';
	margin-left:10px;
	line-height:20px;
	text-align: justify;
	margin-bottom: 5px;
	overflow-y: auto;
}
.scrape_fl {
	float: left;
	height: 190px;
	width: 660px;
}
.scrape_posting_author {
	font-size:12px;
	color:coral;
	font-weight:bold;
	text-align:right;
	font-family:'Nanum Barun Gothic';
	line-height:30px;
	width: 260px;
	overflow: hidden;
	white-space: nowrap;
	text-overflow: ellipsis;
	height: 30px;
	float: right;
}
.scrape_score {
	font-size:15px;
	font-family:'Nanum Barun Gothic';
	font-weight:bold;
	color:darkorange;
	clear:both;
	height: 30px;
	width: 80px;
	float: left;
	margin-left: 20px;
	line-height: 30px;
}
.scrape_sns_info {
	width: 300px;
	line-height: 30px;
	font-size: medium;
}
.scrape_category {
	width: 1000px;
	margin: auto;
	font-size:15px;
	font-weight:bold;
	font-family:'Nanum Barun Gothic';
	line-height:30px;
}
</style>

<script>

//블로그로 이동시키며 체류시간을 측정하는 함수
function goBlogPosting(targetURL,smallProductId){
	//도착시간 체크를 위해 
	var connectDate = new Date();
	var connectTime = connectDate.getTime();
	window.open(targetURL, "_blank");
	//중복 실행 방지 메서드
	var count = 0;
	//다시 사용자가 본 서비스 브라우져에서 움직였을 때 메서드 체류시간 기록
	setTimeout(function(){
		$('body').mouseover(function(){
			if(count==0){
				count=1;
				var exitDate = new Date();
				var exitTime = exitDate.getTime();
				var residenceTime = Math.round((exitTime - connectTime)/1000);
				$.ajax({
					type:"get",
					url:"recordResidenceTime.do?postingUrl="
						+targetURL+"&smallProductId="
						+smallProductId+"&postingTotalResidenceTime="
						+residenceTime,
						success:function(date){
						alert('체류시간 기록 완료 : '+residenceTime+'초');
						}
				});
			}else{
				return false;
			}
	 	}); 
	},2000);
}

$(document).ready(function(){
	//포스팅 스크랩 스크립트
	$(".postingScrapeBtn").click(function(){
		var comp = $(this);
		$.ajax({
			type:"get",
			url:"postingScrape.do?memberId=${sessionScope.blliMemberVO.memberId}&smallProductId="+$(comp).parent().children('.smallProductIdInfo').val()
					+'&postingUrl='+$(comp).parent().children('.postingUrlInfo').val(),
			success:function(result){
				if(result==1){
					$(comp).css('color','orange');
					var count = $(comp).siblings('.scrapeCount').text();
					count *= 1;
					$(comp).siblings('.scrapeCount').text(count+1);
				}else if(result==0){
					$(comp).css('color','gray');
					var count = $(comp).siblings('.scrapeCount').text();
					count *= 1;
					$(comp).siblings('.scrapeCount').text(count-1);
				}
			}
		}); 
	});
	//포스팅 좋아요 스크립트
	$(".postingLikeBtn").click(function(){
		var comp = $(this);
		$.ajax({
			type:"get",
			url:"postingLike.do?memberId=${sessionScope.blliMemberVO.memberId}&smallProductId="+$(comp).parent().children('.smallProductIdInfo').val()
					+'&postingUrl='+$(comp).parent().children('.postingUrlInfo').val(),
			success:function(result){
				if(result==1){
					$(comp).css('color','hotpink');
					var count = $(comp).siblings('.likeCount').text();
					count *= 1;
					$(comp).siblings('.likeCount').text(count+1);
				}else if(result==0){
					$(comp).css('color','gray');
					var count = $(comp).siblings('.likeCount').text();
					count *= 1;
					$(comp).siblings('.likeCount').text(count-1);
				}
			}
		}); 
	});
	//포스팅 싫어요 스크립트
	$(".postingDisLikeBtn").click(function(){
		var comp = $(this);
		$.ajax({
			type:"get",
			url:"postingDisLike.do?memberId=${sessionScope.blliMemberVO.memberId}&smallProductId="+$(comp).parent().children('.smallProductIdInfo').val()
					+'&postingUrl='+$(comp).parent().children('.postingUrlInfo').val(),
			success:function(result){
				if(result==1){
					$(comp).css('color','hotpink');
					var count = $(comp).siblings('.disLikeCount').text();
					count *= 1;
					$(comp).siblings('.disLikeCount').text(count+1);
				}else if(result==0){
					$(comp).css('color','gray');
					var count = $(comp).siblings('.disLikeCount').text();
					count *= 1;
					$(comp).siblings('.disLikeCount').text(count-1);
				}
			}
		}); 
	});
	$(".midCategory").click(function(){
		var midCategory = $(this).text();
		var midCategoryLength = $(this).parent().prev().val();
		for(var i=0;i<midCategoryLength;i++){
			if($(this).parent().children("input").eq(i).val() != midCategory){
				$("."+$(this).parent().children("input").eq(i).val()).hide();
			}else{
				$("."+midCategory).show();
			}
		}
	});
	$(".allMidCategory").click(function(){
		var midCategoryLength = $(this).parent().prev().val();
		for(var i=0;i<midCategoryLength;i++){
			$("."+$(this).parent().children("input").eq(i).val()).show();
		}
	});
});
</script>
<c:if test="${fn:length(requestScope.scrapeList) != 0}">
	<input type="hidden" value="${fn:length(requestScope.midCategoryList)}">
	<div class="scrape_category">
		<c:if test="${fn:length(requestScope.midCategoryList) >= 2}">
			<a href="#" class="allMidCategory">전체</a> &nbsp; &nbsp; 
		</c:if>
		<c:forEach items="${requestScope.midCategoryList}" var="midCategoryList">
			<input type="hidden" value="${midCategoryList}">
			<a href="#" class="midCategory">${midCategoryList}</a> &nbsp; &nbsp;  	
		</c:forEach>
	</div>
	<c:forEach items="${requestScope.midCategoryList}" var="midCategoryList">
		<c:forEach items="${requestScope.scrapeList}" var="postingList" varStatus="index">
			<c:if test="${midCategoryList == postingList.midCategory}">
				<div class="${midCategoryList}">
					<c:if test="${index.count%2 == 0}">
						<div class="scrape_bg_color_one">
					</c:if>
					<c:if test="${index.count%2 == 1}">
						<div class="scrape_bg_color_two">
					</c:if>
					<div class="scrape_info">
						<div class="scrape_img">
							<a href="#" onclick="goBlogPosting('${postingList.postingUrl}','${postingList.smallProductId}')">
								<img src="${postingList.postingPhotoLink}" style="width: 340px; height: 235px;">
							</a>
						</div>
						<div class="scrape_content">
							<div class="scrape_posting_title">
								${postingList.postingTitle}
							</div>
							<div class="scrape_fl">
								<c:if test="${index.count%2 == 0}">
									<div class="scrape_posting_content_one">
								</c:if>
								<c:if test="${index.count%2 == 1}">
									<div class="scrape_posting_content_two">
								</c:if>
									${postingList.postingContent}
								</div>
								<div class="scrape_score">
									${postingList.postingScore}점
								</div>
								<div class="scrape_posting_author">
									${postingList.postingAuthor}
								</div>
								<div class="scrape_sns_info">
									<input type="hidden" class="smallProductIdInfo" value="${postingList.smallProductId}">
									<input type="hidden" class="postingUrlInfo" value="${postingList.postingUrl}">
									<c:if test="${postingList.isScrapped==0}">
										<i class="fa fa-bookmark-o postingScrapeBtn" style="color: gray" data-tooltip-text="스크랩해두시면 스크랩페이지에서 다시 보실 수 있어요!"></i>
										<span style="font-size: 11px; line-height: 16px ;margin-right:15px" class="scrapeCount">${postingList.postingScrapeCount}</span>
									</c:if>
									<c:if test="${postingList.isScrapped==1}">
										<i class="fa fa-bookmark postingScrapeBtn" style="color: orange" data-tooltip-text="스크랩해두시면 스크랩페이지에서 다시 보실 수 있어요!"></i>
										<span style="font-size: 11px; line-height: 16px ;margin-right:15px" class="scrapeCount">${postingList.postingScrapeCount}</span>
									</c:if>		
									<c:if test="${postingList.isLike==0}">
										<i class="fa fa-thumbs-up postingLikeBtn" style="color: gray" data-tooltip-text="유익한 내용의 블로그였다면 좋아요!"></i>
										<span style="font-size: 11px; line-height: 16px ;margin-right:15px" class="likeCount">${postingList.postingLikeCount}</span>
									</c:if>
									<c:if test="${postingList.isLike==1}">
										<i class="fa fa-thumbs-up postingLikeBtn" style="color: hotpink" data-tooltip-text="유익한 내용의 블로그였다면 좋아요!"></i>
										<span style="font-size: 11px; line-height: 16px ;margin-right:15px" class="likeCount">${postingList.postingLikeCount}</span>
									</c:if>
									<c:if test="${postingList.isDisLike==0}">
										<i class="fa fa-thumbs-down postingDisLikeBtn" style="color: gray" data-tooltip-text="광고,상관없는 포스팅등은 가차없이 싫어요!"></i>
										<span style="font-size: 11px; line-height: 16px ;margin-right:15px" class="disLikeCount">${postingList.postingDislikeCount}</span>
									</c:if>
									<c:if test="${postingList.isDisLike==1}">
										<i class="fa fa-thumbs-down postingDisLikeBtn" style="color: hotpink" data-tooltip-text="광고,상관없는 포스팅등은 가차없이 싫어요!"></i>
										<span style="font-size: 11px; line-height: 16px ;margin-right:15px" class="disLikeCount">${postingList.postingDislikeCount}</span>
									</c:if>
								</div>
							</div>
						</div>
						</div>
					</div>
				</div>
			</c:if>
		</c:forEach>
	</c:forEach>
</c:if>
<c:if test="${fn:length(requestScope.scrapeList) == 0}">
스크랩하신 블로그 포스팅이 없습니다.
</c:if>