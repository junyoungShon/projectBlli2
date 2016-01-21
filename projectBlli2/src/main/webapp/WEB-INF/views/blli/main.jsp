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
<script src="${initParam.root}js/flickity.pkgd.min.js"></script>
<link rel="stylesheet" href="${initParam.root}css/flickity.css" media="screen">
<script type="text/javascript">
	$(document).ready(function(){
		
		//중분류 추천 제거 클릭 시 추천 대상에서 제외
		$('.recommendMidDelete').click(function(){
			if(confirm('정말 삭제하시겠어요??')){
				//alert($(this).parent().siblings('.midCategory').text());
				//alert($(this).siblings('.midCategoryId').val());
				$.ajax({
					type:"get",
					url:"deleteRecommendMidCategory.do?midCategory="+$(this).parent().siblings('.midCategory').text()
							+"&memberId=${sessionScope.blliMemberVO.memberId}&midCategoryId="+$(this).siblings('.midCategoryI d').val(),
					success:function(){
						alert("삭제완료!");
					}
				}); 
			}
		});
		//추천 받을 아이 바꾸기 메서드
		$('.babyChanger').change(function(){
			//alert($(this).val());
			$.ajax({
				type:"get",
				url:"changeRecommendingBaby.do?babyName="+$(this).val()
						+"&memberId=${sessionScope.blliMemberVO.memberId}",
				success:function(){
					alert("변경완료!");
					location.href='${initParam.root}member_goMain.do '
				}
			});
		});
		
		$('.midRecommProduct').flickity({
			  // options
			  freeScroll: true,
			  wrapAround: true
			});
		
	});
</script>
</head>
<body>
여기는 메인페이쟈
${sessionScope.blliMemberVO.memberName}님 환영합니다.<br>
<hr>
<div class="midRecommProduct">
	<c:forEach items="${requestScope.blliMidCategoryVOList}" var="recommProductList">
  		<div class="gallery-cell">
  			<img src='${recommProductList.midCategoryMainPhotoLink}'>
  			${recommProductList.midCategory }
  		</div>
  	</c:forEach>
</div>
<br><br>
	<c:forEach items="${requestScope.blliMemberVO.blliBabyVOList}" var="babyList">
		<c:choose>
			<c:when test="${babyList.recommending==1}">
			현재 추천 아이는 ${babyList.babyName }입니다.<br>
			${babyList.babyMonthAge}달째 입니다.<br>
			</c:when>
		</c:choose>
	</c:forEach>
	추천 아이 바꾸기
<select class="babyChanger">
	<c:forEach items="${requestScope.blliMemberVO.blliBabyVOList}" var="babyList">
		<c:choose>
			<c:when test="${babyList.recommending==1}">
				<option value="${babyList.babyName}" selected="selected">${babyList.babyName}</option>
			</c:when>
			<c:otherwise>
				<option value="${babyList.babyName}" >${babyList.babyName}</option>
			</c:otherwise>		
		</c:choose>
	</c:forEach>
</select><br>
<hr>
<h1>추천 중분류 상품 입니다.</h1>
<table border="1" >
	<tr>
		<td>제품명</td><td>설명</td><td>이미지</td><td>누구꺼</td><td>사용시기</td><td>대분류</td><td>추천제외</td>
	</tr>
	<c:forEach items="${requestScope.blliMidCategoryVOList}" var="recommProductList">
		<tr>
			<td class="midCategory">${recommProductList.midCategory }</td><td>${recommProductList.midCategoryInfo}</td><td><img src='${recommProductList.midCategoryMainPhotoLink}'></td>
			<td>
				<c:forEach items="${requestScope.blliMemberVO.blliBabyVOList}" var="babyList">
					<c:if test="${babyList.babyMonthAge>=recommProductList.whenToUseMin && babyList.babyMonthAge<=recommProductList.whenToUseMax}">
						${babyList.babyName}
					</c:if>
				</c:forEach>
			</td>
			<td>${recommProductList.whenToUseMin}개월~${recommProductList.whenToUseMax}</td>
			<td>${recommProductList.bigCategory}</td>
			<td><input type="button" class="recommendMidDelete" value="삭제"><input type="hidden" value="${recommProductList.midCategoryId}" class="midCategoryId"></td>
		</tr>
	</c:forEach>
	
</table>
<hr>
<h1>또래 엄마들이 많이 찜한 상품들입니다.</h1>
<table border="1" >
	<tr>
		<td>제품명</td><td>중분류</td><td>중분류id</td><td>제조사</td><td>사용시기</td><td>찜수</td><td>사진</td><td>점수</td>
	</tr>
	<c:forEach items="${requestScope.blliSmallProductVOList}" var="blliSmallProductVOList">
		<tr>
			<td>${blliSmallProductVOList.smallProduct}</td><td>${blliSmallProductVOList.midCategory}</td><td>${blliSmallProductVOList.midCategoryId}</td>
			<td>${blliSmallProductVOList.smallProductMaker}</td><td>${blliSmallProductVOList.smallProductWhenToUseMin}개월~${blliSmallProductVOList.smallProductWhenToUseMax}개월</td>
			<td>${blliSmallProductVOList.smallProductDibsCount}<input type="button" value="찜하기" ></td>
			<td>${blliSmallProductVOList.smallProductMainPhotoLink}</td>
			<td>${blliSmallProductVOList.smallProductScore}</td>
		</tr>
	</c:forEach>
	
</table>
<!-- <result property="postingUrl" column="posting_url"/>
 		<result property="postingTitle" column="posting_title"/>
 		<result property="postingSummary" column="posting_summary"/>
 		<result property="postingContent" column="posting_content"/>
 		<result property="postingScore" column="posting_score"/>
 		<result property="postingLikeCount" column="posting_like_count"/>
 		<result property="postingDislikeCount" column="posting_dislike_count"/>
 		<result property="postingMediaCount" column="posting_media_count"/>
 		<result property="postingPhotoLink" column="posting_photo_link"/>
 		<result property="postingStatus" column="posting_status"/>
 		<result property="postingTotalResidenceTime" column="posting_total_residence_time"/>
 		<result property="postingViewCount" column="posting_view_count"/>
 		<result property="postingScrapeCount" column="posting_scrape_count"/>
 		<result property="postingAuthor" column="posting_author"/>
 		<result property="postingDate" column="posting_date"/>
 		<result property="postingOrder" column="posting_order"/>
 		<result property="postingReplyCount" column="posting_reply_count"/>
 		<result property="smallProduct" column="small_product"/>
 		<result property="smallProductId" column="small_product_id"/> -->
<h1>현재 추천되고 있는 소제품관련 블로그!</h1>
<table align="center" width="50%" border="1" bordercolor="silver" style="border-collapse: collapse; table-layout: fixed;" cellpadding="10" rules="none">
<c:forEach items="${requestScope.blliPostingVOList}" var="postingList">
<tr>
	<td colspan="6" width="70%" style="text-overflow : ellipsis;overflow : hidden;">
	<strong><a href="${postingList.postingUrl}" style="text-decoration:none; color: black;"><NOBR>${postingList.postingTitle}</NOBR></a></strong></td>
	<td colspan="3" align="right" width="30%"><font size="2" color="silver">${postingList.postingDate}</font></td>
</tr>
<tr>
	<td colspan="6" rowspan="2" align="center" width="70%"><div style="width: 400px; height: 300px; border: 1px solid; border-color: silver; display: table-cell; vertical-align: middle;">
	<a href="goPosting.do?postingUrl=${postingList.postingUrl}&smallProductId=${postingList.smallProductId}"><img src="http://t1.daumcdn.net/thumb/R1024x0/?fname=${postingList.postingPhotoLink}" style="max-width: 100%; max-height: 100%;"></a></div></td>
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
<script>
(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
})(window,document,'script','//www.google-analytics.com/analytics.js','ga');

ga('create', 'UA-72734813-1', {'cookieDomain': 'none'});
ga('send', 'pageview');

</script>

</html>