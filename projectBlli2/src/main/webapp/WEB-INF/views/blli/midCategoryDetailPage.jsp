<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
$(document).ready(function(){
	$( '.jbMenu' ).addClass( 'jbFixed' );
	
	//소제품 찜하기 스크립트
	$('.in_fr').on("click", ".smallProductDibBtn",function(){
		var smallProductId = $(this).children('.smallProductId').val();
		$.ajax({
			type:"get",
			url:"smallProductDib.do?memberId=${sessionScope.blliMemberVO.memberId}&smallProductId="+smallProductId,
			success:function(result){
				alert(result);
				$('.smallProductDibBtn').each(function(index){
					if($($('.smallProductDibBtn').get(index)).children('.smallProductId').val()==smallProductId){
						if(result==1){
							$($('.smallProductDibBtn').get(index)).children('.fa').removeClass("fa-heart-o").addClass("fa-heart");
							$($('.smallProductDibBtn').get(index)).children('.dibsCount').text(Number($($('.smallProductDibBtn').get(index)).children('.dibsCount').text())+1);							
						}else{
							$($('.smallProductDibBtn').get(index)).children('.fa').removeClass("fa-heart").addClass("fa-heart-o");
							$($('.smallProductDibBtn').get(index)).children('.dibsCount').text(Number($($('.smallProductDibBtn').get(index)).children('.dibsCount').text())-1);		
						}
					}
				}) 
			}
		});
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
					var wordCloudList = resultData[i].blliWordCloudVOList;
					var wordCloudHTML = '<div class="midKeyword"><ul>';
					for(var i=0;i<wordCloudList.length;i++){
						wordCloudHTML += '<li><span class="midKeyword'+wordCloudList[i].wordLevel+'">'+wordCloudList[i].word+'</span></li>'
					}
					wordCloudHTML += '</ul></div>';
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
						div += wordCloudHTML;
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
						div += "<p class='result_sns'>"+resultData[i].dbInsertPostingCount+"</p>";
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
						div += wordCloudHTML;
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
						div += "<p class='result_sns'>"+resultData[i].dbInsertPostingCount+"</p>";
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
					<div class="midKeyword">
					<ul>
						<c:forEach items="${smallProductList.blliWordCloudVOList}" var="wordList">
							<li>
								<span class="midKeyword${wordList.wordLevel}">${wordList.word}</span>
							</li>
						</c:forEach>
					</ul>
					</div>
					<div class="product_price">
						<div class="fl">
							<p class="result_gray">최저가</p>
							<p class="result_price">${smallProductList.minPrice}원</p>
						</div>
						<div class="fr">
								<div style="margin-top: 15px" class="smallProductDibBtn">
						<c:if test="${smallProductList.isDib==0}">
							<i class="fa fa-heart-o fa-2x" style="color: red"></i>
						</c:if>
						<c:if test="${smallProductList.isDib==1}">
							<i class="fa fa-heart fa-2x" style="color: red"></i>
						</c:if>
							<span class="dibsCount" style="font-size: 15px ;color: gray;">${smallProductList.smallProductDibsCount}</span>
							<input type="hidden" value="${smallProductList.smallProductId}" class="smallProductId">
						</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="result_last fr">
			<ul>
				<li>
					<p class="result_sns">${smallProductList.dbInsertPostingCount}</p>
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
				<div class="midKeyword">
				<ul>
					<c:forEach items="${smallProductList.blliWordCloudVOList}" var="wordList">
						<li>
							<span class="midKeyword${wordList.wordLevel}">${wordList.word}</span>
						</li>
					</c:forEach>
				</ul>
				</div>
					<div class="product_price">
						<div class="fl">
							<p class="result_gray">최저가</p>
							<p class="result_price">${smallProductList.minPrice}원</p>
						</div>
						<div class="fr">
						<div style="margin-top: 15px" class="smallProductDibBtn">
						<c:if test="${smallProductList.isDib==0}">
							<i class="fa fa-heart-o fa-2x" style="color: red"></i>
						</c:if>
						<c:if test="${smallProductList.isDib==1}">
							<i class="fa fa-heart fa-2x" style="color: red"></i>
						</c:if>
							<span class="dibsCount" style="font-size: 15px ;color: gray;">${smallProductList.smallProductDibsCount}</span>
							<input type="hidden" value="${smallProductList.smallProductId}" class="smallProductId">
						</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="result_last fr">
			<ul>
				<li>
					<p class="result_sns">${smallProductList.dbInsertPostingCount}</p>
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