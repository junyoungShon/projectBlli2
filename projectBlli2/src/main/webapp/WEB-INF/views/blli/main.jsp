<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 개발시 두었다가 개발 종료 시 선언해제 할 것! -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
					location.href='${initParam.root}member_goMain.do'
				}
			});
		});
		//소제품 찜하기 스크립트
		$('.smallProductDibBtn').click(function(){
			$.ajax({
				type:"get",
				url:"smallProductDib.do?memberId=${sessionScope.blliMemberVO.memberId}&smallProductId="+$(this).next('.smallProductId').val(),
				success:function(result){
					if(result==1){
						alert('찜하기 성공!');
					}else if(result==0){
						alert('찜하기 해제!');
					}
				}
			});
		});
		//포스팅 스크랩 스크립트
		$('.postingScrapBtn').click(function(){
			$.ajax({
				type:"get",
				url:"postingScrape.do?memberId=${sessionScope.blliMemberVO.memberId}&smallProductId="+$(this).parent().parent().parent().siblings('.smallProductIdInfo').val()
						+'&postingUrl='+$(this).parent().parent().parent().siblings('.postingUrlInfo').val(),
				success:function(result){
					if(result==1){
						alert('스크랩 성공!');
					}else if(result==0){
						alert('스크랩 해제!');
					}
				}
			}); 
		});
		//포스팅 좋아요 스크립트
		$('.postingLikeBtn').click(function(){
			$.ajax({
				type:"get",
				url:"postingLike.do?memberId=${sessionScope.blliMemberVO.memberId}&smallProductId="+$(this).parent().parent().parent().siblings('.smallProductIdInfo').val()
						+'&postingUrl='+$(this).parent().parent().parent().siblings('.postingUrlInfo').val(),
				success:function(result){
					if(result==1){
						alert('좋아요 성공!');
					}else if(result==0){
						alert('좋아요 해제!');AKIAIXVJXVD7QC5UNTCQAKIAIXVJXVD7QC5UNTCQAKIAIXVJXVD7QC5UNTCQ
					}
				}
			}); 
		});
		//포스팅 싫어요 스크립트
		$('.postingDisLikeBtn').click(function(){
			$.ajax({
				type:"get",
				url:"postingDisLike.do?memberId=${sessionScope.blliMemberVO.memberId}&smallProductId="+$(this).parent().parent().parent().siblings('.smallProductIdInfo').val()
						+'&postingUrl='+$(this).parent().parent().parent().siblings('.postingUrlInfo').val(),
				success:function(result){
					if(result==1){
						alert('싫어요 성공!');
					}else if(result==0){
						alert('싫어요 해제!');
					}
				}
			}); 
		});
		
		//중분류추천 슬라이드 설정 js
		$('.midRecommProduct').flickity({
			  // options
			  freeScroll: true,
			  wrapAround: true
		});
	});
</script>

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
			<c:if test="${blliSmallProductVOList.isDib==0}">
				<td>${blliSmallProductVOList.smallProductDibsCount}
				<input type="button" value="찜하기" class="smallProductDibBtn"><input type="hidden" value="${blliSmallProductVOList.smallProductId}" class="smallProductId"></td>
			</c:if>
			<c:if test="${blliSmallProductVOList.isDib==1}">
				<td>${blliSmallProductVOList.smallProductDibsCount}
				<input type="button" value="찜취소" class="smallProductDibBtn"><input type="hidden" value="${blliSmallProductVOList.smallProductId}" class="smallProductId"></td>
			</c:if>
			<td>${blliSmallProductVOList.smallProductMainPhotoLink}</td>
			<td>${blliSmallProductVOList.smallProductScore}</td>
		</tr>
	</c:forEach>
	
</table>
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
	<c:if test="${postingList.isScrapped==0}">
		<td align="center" width="10%"><font color="silver"><img src="${initParam.root}image/스크랩.PNG" class="postingScrapBtn" width="20px"> ${postingList.postingScrapeCount}스크랩하기</font></td>
	</c:if>
	<c:if test="${postingList.isScrapped==1}">
		<td align="center" width="10%"><font color="silver"><img src="${initParam.root}image/스크랩.PNG" class="postingScrapBtn" width="20px"> ${postingList.postingScrapeCount}스크랩취소</font></td>
	</c:if>
	<c:if test="${postingList.isLike==0}">
		<td align="center" width="10%"><font color="silver"><img src="${initParam.root}image/좋아요.PNG" class="postingLikeBtn" width="15px">좋아요 ${postingList.postingLikeCount}</font></td>
	</c:if>
	<c:if test="${postingList.isLike==1}">
		<td align="center" width="10%"><font color="silver"><img src="${initParam.root}image/좋아요.PNG" class="postingLikeBtn" width="15px">좋아요 취소${postingList.postingLikeCount}</font></td>
	</c:if>
	<c:if test="${postingList.isDisLike==0}">
		<td align="center" width="10%"><font color="silver"><img src="${initParam.root}image/좋아요.PNG" class="postingLikeBtn" width="15px">싫어요 ${postingList.postingLikeCount}</font></td>
	</c:if>
	<c:if test="${postingList.isDisLike==1}">
		<td align="center" width="10%"><font color="silver"><img src="${initParam.root}image/싫어요.PNG" class="postingDisLikeBtn" width="15px">싫어요 취소${postingList.postingDislikeCount}</font></td>
	</c:if>
</tr>
<input type="hidden" class="smallProductIdInfo" value="${postingList.smallProductId}">
<input type="hidden" class="postingUrlInfo" value="${postingList.postingUrl}">
</c:forEach>
</table>


</html>