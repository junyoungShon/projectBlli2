<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
$(document).ready(function(){
	var jbOffset = $( '.jbMenu' ).offset();
	$( window ).scroll( function() {
	  if ( $( document ).scrollTop() > jbOffset.top ) {
		$( '.jbMenu' ).addClass( 'jbFixed' );
	  }
	  else {
		$( '.jbMenu' ).removeClass( 'jbFixed' );
	  }
	});

	var count = 2;
	var searchWord = "${requestScope.searchWord}";
	var totalPage = ${requestScope.totalPage};
	var rank = 11;
	
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
			url: "${initParam.root}getSmallProductList.do",
			data: "pageNo="+pageNo+"&searchWord="+searchWord,
			success: function(resultData){
				var div = "";
				for(var i=0;i<resultData.length;i++){
					if(i%2 == 0){
						div += "<div class='result_bg1'>";
						div += "<div class='in_fr' style='height:330px;'>";
						div += "<div class='result_num'>"+(rank++);
						div += "</div>";
						div += "<div class='result_con'>";
						div += "<div class='result_ti'>";
						div += "<a href='${initParam.root}goSmallProductDetailView.do?smallProduct="+resultData[i].smallProduct+"' style='text-decoration:none; color: black;'>"+resultData[i].smallProduct+"</a>";
						div += "</div>";
						div += "<div>";
						div += "<div class='result_foto fl'>";
						div += "<img src='"+resultData[i].smallProductMainPhotoLink+"' alt='"+resultData[i].smallProduct+"' style='width: 100%; height: 100%; vertical-align: middle;'>";
						div += "<div class='product_month'>";
						div += resultData[i].smallProductWhenToUseMin+"~"+resultData[i].smallProductWhenToUseMax+"<br/>개월";
						div += "</div>";
						div += "</div>";
						div += "<div class='fl'>";
						div += "<div class='product_text'>";
						div += "하은맘 프라임 샴푸 의자 이젠 32개월 모야 안고 머리감기는 일 너무 힘들어요~ 무게도 덩치도"+
								"발육이 남다른 모야 ~ 구상도를보면 참 많은 생각을 하시고 제작하신것 같아요";
						div += "</div>";
						div += "<div class='product_price'>";
						div += "<div class='fl'>";
						div += "<p class='result_gray'>최저가</p>";
						div += "<p class='result_price'>"+resultData[i].minPrice+"원</p>";
						div += "</div>";
						div += "<div class='fr'>";
						div += "<a href='#'><img src='${initParam.root}img/jjim.png' alt='찜' style='margin-top:10px;'></a>";
						div += "</div>";
						div += "</div>";
						div += "</div>";
						div += "</div>";
						div += "</div>";
						div += "<div class='result_last fr'>";
						div += "<ul>";
						div += "<li>";
						div += "<p class='result_sns'>"+resultData[i].smallProductPostingCount+"</p>";
						div += "<p class='result_sns_text'>blog</p>";
						div += "</li>";
						div += "<li>";
						div += "<p class='result_sns'>"+resultData[i].smallProductScore+"</p>";
						div += "<p class='result_sns_text'>Point</p>";
						div += "</li>";
						div += "</ul>";
						div += "<div style='text-align:center;'>";
						div += "<a href='#'><img src='${initParam.root}img/facebook.png' alt='페이스북'></a>";
						div += "<a href='#'><img src='${initParam.root}img/twitter.png' alt='트위터'></a>";
						div += "</div>";
						div += "</div>";
						div += "</div>";
						div += "</div>";
					}else{
						div += "<div class='result_bg2'>";
						div += "<div class='in_fr' style='clear:both;'>";
						div += "<div class='result_num'>"+(rank++);
						div += "</div>";
						div += "<div class='result_con'>";
						div += "<div class='result_ti'>";
						div += "<a href='${initParam.root}goSmallProductDetailView.do?smallProduct="+resultData[i].smallProduct+"' style='text-decoration:none; color: black;'>"+resultData[i].smallProduct+"</a>";
						div += "</div>";
						div += "<div>";
						div += "<div class='result_foto fl'>";
						div += "<img src='"+resultData[i].smallProductMainPhotoLink+"' alt='"+resultData[i].smallProduct+"' style='width: 100%; height: 100%; vertical-align: middle;'>";
						div += "<div class='product_month'>";
						div += resultData[i].smallProductWhenToUseMin+"~"+resultData[i].smallProductWhenToUseMax+"<br/>개월";
						div += "</div>";
						div += "</div>";
						div += "<div class='fl'>";
						div += "<div class='product_text'>";
						div += "하은맘 프라임 샴푸 의자 이젠 32개월 모야 안고 머리감기는 일 너무 힘들어요~ 무게도 덩치도"+
								"발육이 남다른 모야 ~ 구상도를보면 참 많은 생각을 하시고 제작하신것 같아요";
						div += "</div>";
						div += "<div class='product_price'>";
						div += "<div class='fl'>";
						div += "<p class='result_gray'>최저가</p>";
						div += "<p class='result_price'>"+resultData[i].minPrice+"원</p>";
						div += "</div>";
						div += "<div class='fr'>";
						div += "<a href='#'><img src='${initParam.root}img/jjim.png' alt='찜' style='margin-top:10px;'></a>";
						div += "</div>";
						div += "</div>";
						div += "</div>";
						div += "</div>";
						div += "</div>";
						div += "<div class='result_last fr'>";
						div += "<ul>";
						div += "<li>";
						div += "<p class='result_sns'>"+resultData[i].smallProductPostingCount+"</p>";
						div += "<p class='result_sns_text'>blog</p>";
						div += "</li>";
						div += "<li>";
						div += "<p class='result_sns'>"+resultData[i].smallProductScore+"</p>";
						div += "<p class='result_sns_text'>Point</p>";
						div += "</li>";
						div += "</ul>";
						div += "<div style='text-align:center;'>";
						div += "<a href='#'><img src='${initParam.root}img/facebook.png' alt='페이스북'></a>";
						div += "<a href='#'><img src='${initParam.root}img/twitter.png' alt='트위터'></a>";
						div += "</div>";
						div += "</div>";
						div += "</div>";
						div += "</div>";
					}
				}
				setTimeout(function(){ // 시간 지연
					$("#body").append(div);
				}, 1000);
			}
	    });
	}
});
</script>
<div id="body">
<c:forEach items="${requestScope.resultList}" var="smallProductList" varStatus="i">
<c:if test="${i.count%2 == 1}">
<div class="result_bg1">
	<div class="in_fr" style="height:330px;">
		<div class="result_num">
			${i.count}
		</div>
		<div class="result_con">
			<div class="result_ti">
				<a href="${initParam.root}goSmallProductDetailView.do?smallProduct=${smallProductList.smallProduct}" style="text-decoration:none; color: black;">${smallProductList.smallProduct}</a> 
			</div>
			<div>
				<div class="result_foto fl">
					<img src="${smallProductList.smallProductMainPhotoLink}" alt="${smallProductList.smallProduct}" style="width: 100%; height: 100%; vertical-align: middle;">
					<div class="product_month">
						${smallProductList.smallProductWhenToUseMin}~${smallProductList.smallProductWhenToUseMax}<br/>
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
							<p class="result_price">${smallProductList.minPrice}원</p>
						</div>
						<div class="fr">
							<c:if test="${smallProductList.isDib==0}">
											<div style="margin-top: 15px">
												<i class="fa fa-heart-o fa-2x smallProductDibBtn" style="color: red"></i>
												<span style="font-size: 15px ;color: gray;">${smallProductList.smallProductDibsCount}</span>
												<input type="hidden" value="${smallProductList.smallProductId}" class="smallProductId">
											</div>
									</c:if>
									<c:if test="${smallProductList.isDib==1}">
											<div style="margin-top: 15px">
												<i class="fa fa-heart fa-2x smallProductDibBtn" style="color: red"></i>
													<span style="font-size: 15px ;color: gray;">${smallProductList.smallProductDibsCount}</span>
												<input type="hidden" value="${smallProductList.smallProductId}" class="smallProductId">
									</div>
									</c:if>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="result_last fr">
			<ul>
				<li>
					<p class="result_sns">${smallProductList.smallProductPostingCount}</p>
					<p class="result_sns_text">blog</p>
				</li>
				<li>
					<p class="result_sns">${smallProductList.smallProductScore}</p>
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
</c:if>
<c:if test="${i.count%2 == 0}">
<div class="result_bg2">
	<div class="in_fr" style="clear:both;">
		<div class="result_num">
			${i.count}
		</div>
		<div class="result_con">
			<div class="result_ti">
				<a href="${initParam.root}goSmallProductDetailView.do?smallProduct=${smallProductList.smallProduct}" style="text-decoration:none; color: black;">${smallProductList.smallProduct}</a> 
			</div>
			<div>
				<div class="result_foto fl">
					<img src="${smallProductList.smallProductMainPhotoLink}" alt="${smallProductList.smallProduct}" style="width: 100%; height: 100%; vertical-align: middle;">
					<div class="product_month">
						${smallProductList.smallProductWhenToUseMin}~${smallProductList.smallProductWhenToUseMax}<br/>
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
							<p class="result_price">${smallProductList.minPrice}원</p>
						</div>
						<div class="fr">
							<c:if test="${smallProductList.isDib==0}">
											<div style="margin-top: 15px">
												<i class="fa fa-heart-o fa-2x smallProductDibBtn" style="color: red"></i>
												<span style="font-size: 15px ;color: gray;">${smallProductList.smallProductDibsCount}</span>
												<input type="hidden" value="${smallProductList.smallProductId}" class="smallProductId">
											</div>
									</c:if>
									<c:if test="${smallProductList.isDib==1}">
											<div style="margin-top: 15px">
												<i class="fa fa-heart fa-2x smallProductDibBtn" style="color: red"></i>
													<span style="font-size: 15px ;color: gray;">${smallProductList.smallProductDibsCount}</span>
												<input type="hidden" value="${smallProductList.smallProductId}" class="smallProductId">
									</div>
									</c:if>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="result_last fr">
			<ul>
				<li>
					<p class="result_sns">${smallProductList.smallProductPostingCount}</p>
					<p class="result_sns_text">blog</p>
				</li>
				<li>
					<p class="result_sns">${smallProductList.smallProductScore}</p>
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
</c:if>
</c:forEach>
</div>
<p align="center"><img id="loading" src="${initParam.root}image/loading.gif" style="width: 50px"></p>