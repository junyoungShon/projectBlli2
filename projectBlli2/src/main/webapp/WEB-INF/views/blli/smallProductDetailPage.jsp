<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="jbContent">
	<div class="result_bg1">
			<div class="in_fr" style="height:330px;">
				<div class="result_num">
					1
				</div>
				<div class="result_con">
					<div class="result_ti">
						${requestScope.smallProductInfo.smallProduct.smallProduct} 
					</div>
					<div>
						<div class="result_foto fl">
							<img src="${requestScope.smallProductInfo.smallProduct.smallProductMainPhotoLink}" alt="${requestScope.smallProductInfo.smallProduct.smallProduct}" style="width: 100%; height: 100%; vertical-align: middle;">
							<div class="product_month">
								${requestScope.smallProductInfo.smallProduct.smallProductWhenToUseMin}~${requestScope.smallProductInfo.smallProduct.smallProductWhenToUseMax}<br/>
								개월
							</div>
						</div>
						<div class="fl">
							<div class="product_text">
								하은맘 프라임 샴푸 의자 이젠 32개월 모야 안고 머리감기는 일 너무 힘들어요~ 무게도 덩치도
								발육이 남다른 모야 ~ 구상도를보면 참 많은 생각을 하시고 제작하신것 같아요
							</div>
							<div class="product_price">
								<div class="fl">
									<p class="result_gray">최저가</p>
									<p class="result_price">${requestScope.smallProductInfo.smallProduct.minPrice}원</p>
								</div>
								<div class="fr">
									<a href="#"><img src="${initParam.root}img/jjim.png" alt="찜" style="margin-top:10px;"></a>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="result_last fr">
					<ul>
						<li>
							<p class="result_sns">${requestScope.smallProductInfo.smallProduct.smallProductPostingCount}</p>
							<p class="result_sns_text">blog</p>
						</li>
						<li>
							<p class="result_sns">${requestScope.smallProductInfo.smallProduct.smallProductScore}</p>
							<p class="result_sns_text">Point</p>
						</li>
					</ul>
					<div style="text-align:center;">
						<a href="#"><img src="${initParam.root}img/facebook.png" alt="페이스북"></a>
						<a href="#"><img src="${initParam.root}img/twitter.png" alt="트위터"></a>
					</div>
				</div>
			</div>
		</div>
		<div class="result_bg2">
			<div class="in_fr" style="clear:both;">
				<div class="detail_list fl">
					<div class="result_ti">
						쇼핑몰 리스트 
					</div>
					<div style="overflow-y:auto; height: 247px;">
						<table>
							<colgroup>
								<col width="15%">
								<col width="15%">
								<col width="20%">
								<col width="30%">
								<col width="20%">
							</colgroup>
							<tr>
								<th>
									쇼핑몰
								</th>
								<th>
									판매가
								</th>
								<th>
									배송비
								</th>
								<th>
									부가정보
								</th>
								<th>
									사러가기
								</th>
							</tr>
							<c:forEach items="${requestScope.smallProductInfo.buyLink}" var="sellerInfo">
								<tr>
									<td>
										${sellerInfo.seller}
									</td>
									<td>
										${sellerInfo.buyLinkPrice}원
									</td>
									<td>
										${sellerInfo.buyLinkDeliveryCost}
									</td>
									<td>
										<c:if test="${sellerInfo.buyLinkOption == null}">
											없음
										</c:if>
										<c:if test="${sellerInfo.buyLinkOption != null}">
											${sellerInfo.buyLinkOption}
										</c:if>
									</td>
									<td>
										<a href="${sellerInfo.buyLink}"><img src="${initParam.root}img/bt_buy.png" alt="사러가기"></a>
									</td>
								</tr>
							</c:forEach>
						</table>
					</div>
				</div>
				<div class="detail_list fr">
					<div class="result_ti">
						동일 제품점수별로 보기
					</div>
					<div style="overflow-y:auto; height: 247px;">
						<table id="otherProductInfo">
							<colgroup>
								<col width="10%">
								<col width="50%">
								<col width="20%">
								<col width="20%">
							</colgroup>
							<tr>
								<th>
									순위
								</th>
								<th>
									제품명
								</th>
								<th>
									점수
								</th>
								<th>
									보러가기
								</th>
							</tr>
							<c:forEach items="${requestScope.smallProductInfo.otherSmallProductList}" var="productList" varStatus="rank">
								<tr>
									<td>
										${rank.count}
									</td>
									<td>
										${productList.smallProduct}
									</td>
									<td>
										${productList.smallProductScore}
									</td>
									<td>
										<a href="${initParam.root}goSmallProductDetailView.do?smallProduct=${productList.smallProduct}"><img src="${initParam.root}img/bt_see.png" alt="보러가기"></a>
									</td>
								</tr>
							</c:forEach>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="jbMenu2">
		<div class="gallery js-flickity" data-flickity-options='{ "imagesLoaded": true }'>
			<c:forEach items="${requestScope.postingList}" var="postingList">
				<a href="${postingList.postingUrl}"><img src="http://t1.daumcdn.net/thumb/R1024x0/?fname=${postingList.postingPhotoLink}" alt="${requestScope.smallProductInfo.smallProduct.smallProduct}"></a>
			</c:forEach>
		</div>
	</div>