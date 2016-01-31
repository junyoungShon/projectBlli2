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
			  freeScroll: false,
			  wrapAround: true,
			  pageDots: false
		});
	});
</script>
<div class="main_top">
		<div class="in_fr">
			<div class="top_nav">
				<a href="#">회원정보수정</a>   ㅣ   <a href="#">아이정보수정</a>   ㅣ   <a href="#">스크랩</a>   ㅣ   <a href="#">알림</a>   ㅣ   <a href="#">아이일정</a>
			</div>
			<div class="main_logo">
				<a href="#"><img src="./img/main_logo.png" alt="로고"></a>
				<input type="text" class="search_text2" placeholder="검색어를 입력하세요">
			<a href="#"><img src="./img/search.png" alt="검색"></a>
			</div>
		</div>
</div>

	

<div class="main_yellow">
		<div class="in_fr">
			<div class="main_yellow_in">
			
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
	</select>
	
		
		
				<div class="main_baby_foto">
				</div>
				<div class="main_baby_name">
				<c:forEach items="${requestScope.blliMemberVO.blliBabyVOList}" var="babyList">
				<c:choose>
			<c:when test="${babyList.recommending==1}">
					 ${babyList.babyName } ${babyList.babyMonthAge} 개월 입니다.
					 	</c:when>
		</c:choose>
	</c:forEach>
				</div>
			</div>
			<div class="main_yellow_in2">
				<div class="main_ti">
					월령별 추천상품 
				</div>
				<div style="width:870px; float:left;" class="midRecommProduct">
					<!-- <div class="fl">
						<a href="#"><img src="./img/allow_l.png" alt="왼쪽화살표" style="margin-top:70px;"></a>
					</div> -->
				
					<div id="menu-wrapper">
						<ul class="boxy-menu ">
						<c:forEach items="${requestScope.blliMidCategoryVOList}" var="recommProductList">
							<li>
								<div class="boxy-menu-item-top gallery-cell">
									<div class="yellow_foto">
									<img src='${recommProductList.midCategoryMainPhotoLink}'>
									</div>
									<div class="yellow_ti">
										${recommProductList.midCategory }
									</div>
								</div>
								<div class="boxy-menu-item-bottom">
									<ul class="items">
										<li class="main_yellow_ti">샴푸의자</li>
										<li style="height:100px; font-weight:normal;">
											샴푸의자에 대한 설명이 들어갑니다.
										</li>
										<li>
											[제품보러가기]
										</li>
									</ul>
								</div>
							</li>
					</c:forEach>
						</ul>
					</div>
					<!-- <div class="fr">
						<a href="#"><img src="./img/allow_r.png" alt="오른쪽화살표" style="margin-top:50px;"></a>
					</div> -->
				</div>
			</div>
		</div>
	</div>

	<div class="in_fr">
		<div class="main_ti">
			또래 자녀를 둔 엄마가 선택한 상품 
		</div>
		<div class="main_product">
		<!-- 	<div class="fl">
				<a href="#"><img src="./img/allow_lgray.jpg" alt="왼쪽화살표" style="margin-top:150px;"></a>
			</div> -->
				
				<%-- 		<td></td><td>개월~개월</td>
						
							<td>
							<input type="button" value="찜하기" class="smallProductDibBtn"><input type="hidden" value="${blliSmallProductVOList.smallProductId}" class="smallProductId"></td>
					
						
						<td></td>
						<td>${blliSmallProductVOList.smallProductScore}</td>
					</tr> --%>
			
			<ul class="midRecommProduct">
			<c:forEach items="${requestScope.blliSmallProductVOList}" var="blliSmallProductVOList">
				<li class="gallery-cell">
				<%-- ${blliSmallProductVOList.midCategoryId} --%>
					<div class="foto205">
						<img src="${blliSmallProductVOList.smallProductMainPhotoLink}">
						<div class="product_month">
							${blliSmallProductVOList.smallProductWhenToUseMin}~${blliSmallProductVOList.smallProductWhenToUseMax}<br/>
							개월
						</div>
					</div>
					<div class="main_product_ti">
						${blliSmallProductVOList.smallProduct} / ${blliSmallProductVOList.smallProductMaker}
					</div>
					<div class="product_price" style="margin:0px;">
						<div class="fl">
							<p class="result_gray">최저가</p>
							<p class="result_price">25,000원</p>
						</div>
						<div class="fr">
						<c:if test="${blliSmallProductVOList.isDib==0}">
						${blliSmallProductVOList.smallProductDibsCount}
							
							</c:if>
							<c:if test="${blliSmallProductVOList.isDib==1}">
							${blliSmallProductVOList.smallProductDibsCount}
							<a href="#"><img src="./img/jjim.png" alt="찜" style="margin-top:10px;"></a>
						</c:if>
						</div>
					</div>
				</li>
					</c:forEach>
			</ul>
		<!-- 	<div class="fr">
				<a href="#"><img src="./img/allow_rgray.jpg" alt="오른쪽화살표" style="margin-top:150px;"></a>
			</div> -->
		</div>
		
		
	</div>



<div class="in_fr" style="margin-top:20px; border-top:1px solid #ccc; clear:both;">
		<div class="main_left_product">
			<div style="height:356px;">
				<div class="foto170">
					<div class="product_month">
						12~34<br/>
						개월
					</div>
				</div>
				<div class="main_product_ti">
					하은맘 프라임 샴푸의자
				</div>
				<div class="product_price" style="margin:0px;">
					<div class="fl">
						<p class="result_gray">최저가</p>
						<p class="result_price">25,000원</p>
					</div>
					<div class="fr">
						<a href="#"><img src="./img/jjim.png" alt="찜" style="margin-top:10px;"></a>
					</div>
				</div>
			</div>
			
		</div>
		<div class="main_left_con">
			<div style="height:356px;">
				<div class="result_ti">
					머리감기가 한결쉬워요~
				</div>
				<div style="height:245px;">
					<div class="result_foto fl">
					</div>
					<div class="fl">
						<div class="product_text2">
							하은맘 프라임 샴푸 의자 이젠 32개월 모야 안고 머리감기는 일 너무 힘들어요~ 무게도 덩치도
							발육이 남다른 모야 ~ 구상도를보면 참 많은 생각을 하시고 제작하신것 같아요
						</div>
						<div class="product_id">
							라니모야
						</div>
					</div>
				</div>
				<div class="product_sns">
					<div class="fl score">
						87점
					</div>
					<div class="fr sns">
						<img src="./img/icon_share.jpg" alt="공유아이콘">
						250
						<img src="./img/icon_like.jpg" alt="좋아요아이콘">
						250
						<img src="./img/icon_hate.jpg" alt="싫어요아이콘">
						250
					</div>
				</div>
			</div>
			
			
		</div>
		<div class="main_right_list">
			<div class="main_right_ti">
				월령별 추천상품
			</div>
			<div class="accordion accordion1">
				<div class="accordion-section">
					<a class="accordion-section-header" data-target="#accordion-1">
						<div class="foto50">
						</div>
						<div class="main_right_name">
							샴푸의자
						</div>
					</a>
					<div id="accordion-1" class="accordion-section-content">
						<table>
							<colgroup>
								<col width="20%">
							</colgroup>
							<tr>
								<th>
									1위
								</th>
								<td>
									하은맘 프라임샴푸 의자 
								</td>
							</tr>
							<tr>
								<th>
									2위
								</th>
								<td>
									하은맘 프라임샴푸 의자 
								</td>
							</tr>
							<tr>
								<th>
									3위
								</th>
								<td>
									하은맘 프라임샴푸 의자 
								</td>
							</tr>
							<tr>
								<th>
									4위
								</th>
								<td>
									하은맘 프라임샴푸 의자 
								</td>
							</tr>
							<tr>
								<th>
									5위
								</th>
								<td>
									하은맘 프라임샴푸 의자 
								</td>
							</tr>
						</table>
					</div>
				</div>
				<div class="accordion-section">
					<a class="accordion-section-header" data-target="#accordion-2">
						<div class="foto50">
						</div>
						<div class="main_right_name">
							샴푸의자
						</div>
					</a>
					<div id="accordion-2" class="accordion-section-content">
						<table>
							<colgroup>
								<col width="20%">
							</colgroup>
							<tr>
								<th>
									1위
								</th>
								<td>
									하은맘 프라임샴푸 의자 
								</td>
							</tr>
							<tr>
								<th>
									2위
								</th>
								<td>
									하은맘 프라임샴푸 의자 
								</td>
							</tr>
							<tr>
								<th>
									3위
								</th>
								<td>
									하은맘 프라임샴푸 의자 
								</td>
							</tr>
							<tr>
								<th>
									4위
								</th>
								<td>
									하은맘 프라임샴푸 의자 
								</td>
							</tr>
							<tr>
								<th>
									5위
								</th>
								<td>
									하은맘 프라임샴푸 의자 
								</td>
							</tr>
						</table>
					</div>
				</div>
				<div class="accordion-section">
					<a class="accordion-section-header" data-target="#accordion-2">
						<div class="foto50">
						</div>
						<div class="main_right_name">
							샴푸의자
						</div>
					</a>
					<div id="accordion-2" class="accordion-section-content">
						<table>
							<colgroup>
								<col width="20%">
							</colgroup>
							<tr>
								<th>
									1위
								</th>
								<td>
									하은맘 프라임샴푸 의자 
								</td>
							</tr>
							<tr>
								<th>
									2위
								</th>
								<td>
									하은맘 프라임샴푸 의자 
								</td>
							</tr>
							<tr>
								<th>
									3위
								</th>
								<td>
									하은맘 프라임샴푸 의자 
								</td>
							</tr>
							<tr>
								<th>
									4위
								</th>
								<td>
									하은맘 프라임샴푸 의자 
								</td>
							</tr>
							<tr>
								<th>
									5위
								</th>
								<td>
									하은맘 프라임샴푸 의자 
								</td>
							</tr>
						</table>
					</div>
				</div>
				<div class="accordion-section">
					<a class="accordion-section-header" data-target="#accordion-2">
						<div class="foto50">
						</div>
						<div class="main_right_name">
							샴푸의자
						</div>
					</a>
					<div id="accordion-2" class="accordion-section-content">
						<table>
							<colgroup>
								<col width="20%">
							</colgroup>
							<tr>
								<th>
									1위
								</th>
								<td>
									하은맘 프라임샴푸 의자 
								</td>
							</tr>
							<tr>
								<th>
									2위
								</th>
								<td>
									하은맘 프라임샴푸 의자 
								</td>
							</tr>
							<tr>
								<th>
									3위
								</th>
								<td>
									하은맘 프라임샴푸 의자 
								</td>
							</tr>
							<tr>
								<th>
									4위
								</th>
								<td>
									하은맘 프라임샴푸 의자 
								</td>
							</tr>
							<tr>
								<th>
									5위
								</th>
								<td>
									하은맘 프라임샴푸 의자 
								</td>
							</tr>
						</table>
					</div>
				</div>
				<div class="accordion-section">
					<a class="accordion-section-header" data-target="#accordion-2">
						<div class="foto50">
						</div>
						<div class="main_right_name">
							샴푸의자
						</div>
					</a>
					<div id="accordion-2" class="accordion-section-content">
						<table>
							<colgroup>
								<col width="20%">
							</colgroup>
							<tr>
								<th>
									1위
								</th>
								<td>
									하은맘 프라임샴푸 의자 
								</td>
							</tr>
							<tr>
								<th>
									2위
								</th>
								<td>
									하은맘 프라임샴푸 의자 
								</td>
							</tr>
							<tr>
								<th>
									3위
								</th>
								<td>
									하은맘 프라임샴푸 의자 
								</td>
							</tr>
							<tr>
								<th>
									4위
								</th>
								<td>
									하은맘 프라임샴푸 의자 
								</td>
							</tr>
							<tr>
								<th>
									5위
								</th>
								<td>
									하은맘 프라임샴푸 의자 
								</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>









<%-- 

<h1>또래 엄마들이 많이 찜한 상품들입니다.</h1>
<table border="1" >
	<tr>
		<td>제품명</td><td>중분류</td><td>중분류id</td><td>제조사</td><td>사용시기</td><td>찜수</td><td>사진</td><td>점수</td>
	</tr>

	
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
	
</table> --%>

</html>