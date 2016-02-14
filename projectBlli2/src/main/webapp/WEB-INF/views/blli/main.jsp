<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 개발시 두었다가 개발 종료 시 선언해제 할 것! -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script type="text/javascript">
	//현재 스크롤바의 위치를 저장하는 변수 (px)
	var currentScrollTop = 0;
	//무한 스크롤의 현재 페이지 넘버를 저장
	var pageNum = 1;
	// 비동기식 jQuery이므로 window load 후 jQuery를 실행해야 함
	window.onload = function() {
	    // 새로고침 했을 경우를 대비한 메소드 실행
	    scrollController();
	    // 스크롤을 하는 경우에만 실행됨
	    $(window).on('scroll', function() {
	        scrollController();
	    });
	}
	
	// ===== Scroll to Top ==== 
	$(window).scroll(function() {
	    if ($(this).scrollTop() >= 200) {        // If page is scrolled more than 50px
	        $('#return-to-top').fadeIn(200);    // Fade in the arrow
	    } else {
	        $('#return-to-top').fadeOut(200);   // Else fade out the arrow
	    }
	});
	$('#return-to-top').click(function() {      // When arrow is clicked
		$('body,html').stop().animate({
	        scrollTop : 0                       // Scroll to top of body
	    }, 2000);
	});
	
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
	// 메인 메뉴의 위치를 제어하는 함수
	function scrollController() {
	    currentScrollTop = $(window).scrollTop();
	    if (currentScrollTop < 500) {
	    	$('.jbMenu').css("display","none");
			$( '.jbMenu' ).removeClass( 'jbFixed' );
	    } else {
	        $('.jbMenu').css("display","block");
	        $( '.jbMenu' ).addClass( 'jbFixed' );
	    }
	    //사이드의 소분류 제품 리스트를 고정시켜줌
	    if(currentScrollTop>1080){
	    	 $('.main_right_list').css("margin-top",currentScrollTop-1080);
	    }else{
	    	 $('.main_right_list').css("margin-top",0);
	    }
	}

	$(document).ready(function(){
		
		//링크 만들기 귀찮아서 그냥 여기에 함 - 나중에 수정
		//$('#blliMemberVOForm').submit();
		
		$('.productInst').hover(function(){
			$(this).children('.productInstAfter').css('display','block');
		}, function(){
			$(this).children('.productInstAfter').css('display','none');
		})
		
		//사이드 소제품 추천 리스트 고정
		
		//중분류 추천 제거 클릭 시 추천 대상에서 제외
		$('.recommendMidDelete').click(function(){
			if(confirm('정말 삭제하시겠어요??')){
				var btn = $(this);
				$.ajax({
					type:"get",
					url:"deleteRecommendMidCategory.do?midCategory="+$(this).children('.midCategoryValue').val()
							+"&memberId=${sessionScope.blliMemberVO.memberId}&midCategoryId="+$(this).children('.midCategoryIdValue').val(),
					success:function(){
						$(btn).parent().parent('.productInst').css("display","none");
					}
				}); 
			}
		});
		//추천 받을 아이 바꾸기 메서드
		$('.babyChanger').change(function(){
			$.ajax({
				type:"get",
				url:"changeRecommendingBaby.do?babyName="+$(this).val()
						+"&memberId=${sessionScope.blliMemberVO.memberId}",
				success:function(){
					location.href='${initParam.root}member_goMain.do'
				}
			});
		});
		//소제품 찜하기 스크립트
		$('.in_fr').on("click", ".smallProductDibBtn",function(){
			var smallProductId = $(this).children('.smallProductId').val();
			$.ajax({
				type:"get",
				url:"smallProductDib.do?memberId=${sessionScope.blliMemberVO.memberId}&smallProductId="+smallProductId,
				success:function(result){
					$('.smallProductDibBtn').each(function(index){
						if($($('.smallProductDibBtn').get(index)).children('.smallProductId').val()==smallProductId){
							if(result==1){
								$($('.smallProductDibBtn').get(index)).children('.fa').removeClass("fa-heart-o").addClass("fa-heart");
								var dibsCount = $($('.smallProductDibBtn').get(index)).children('.dibsCount').text();
								dibsCount *= 1
								$($('.smallProductDibBtn').get(index)).children('.dibsCount').text(dibsCount+1);
							}else{
								$($('.smallProductDibBtn').get(index)).children('.fa').removeClass("fa-heart").addClass("fa-heart-o");
								var dibsCount = $($('.smallProductDibBtn').get(index)).children('.dibsCount').text();
								dibsCount *= 1
								$($('.smallProductDibBtn').get(index)).children('.dibsCount').text(dibsCount-1);
							}
						}
					}) 
				}
			});
		}); 

		//포스팅 스크랩 스크립트
		$('.in_fr').on("click", ".postingScrapeBtn",function(){
			var comp = $(this);
			$.ajax({
				type:"get",
				url:"postingScrape.do?memberId=${sessionScope.blliMemberVO.memberId}&smallProductId="+$(comp).parent('.sns').children('.smallProductIdInfo').val()
						+'&postingUrl='+$(comp).parent('.sns').children('.postingUrlInfo').val(),
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
		$('.in_fr').on("click", ".postingLikeBtn",function(){
			var comp = $(this);
			$.ajax({
				type:"get",
				url:"postingLike.do?memberId=${sessionScope.blliMemberVO.memberId}&smallProductId="+$(comp).parent('.sns').children('.smallProductIdInfo').val()
						+'&postingUrl='+$(comp).parent('.sns').children('.postingUrlInfo').val(),
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
		$('.in_fr').on("click", ".postingDisLikeBtn",function(){
			var comp = $(this);
			$.ajax({
				type:"get",
				url:"postingDisLike.do?memberId=${sessionScope.blliMemberVO.memberId}&smallProductId="+$(comp).parent('.sns').children('.smallProductIdInfo').val()
						+'&postingUrl='+$(comp).parent('.sns').children('.postingUrlInfo').val(),
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
		
		//중분류추천 슬라이드 설정 js
		$('.midRecommProduct').flickity({
			  // options
			  freeScroll: false,
			  wrapAround: true,
			  pageDots: false
		});
		
		$('.accordion-section').click(function(){
			var accodionindex = $(this).index();
			$.ajax({
				type:"get",
				url:"selectSmallProductRank.do?midCategoryId="+$(this).children('.midCategoryIdValue').val(),
				success:function(data){
					var accordionDetailHTML = '<table><colgroup><col width="20%"></colgroup>';
					for(var i=0;i<data.length;i++){
						accordionDetailHTML += 
							'<tr><th>'+(i+1)+'위</th><td><a href="goSmallProductDetailView.do?smallProduct='+data[i].smallProduct+'">'+data[i].smallProduct+'</a></td></tr>'
					}
					accordionDetailHTML += '</table>';
					$('#accordion-'+accodionindex).html(accordionDetailHTML);
				}
			}); 
		});
		
		//무한 스크롤
		$(window).scroll(function(){ 
		    if($(window).scrollTop() >= $(document).height() - $(window).height()){  
		    	setTimeout(function(){$("#loading").show();}, 100)
		    	setTimeout(function(){loadingPostingList();}, 800)
		    	setTimeout(function(){$("#loading").hide();}, 600)
		    }
		});
		
		function loadingPostingList(){
			pageNum++;
    		var smallProductId;
	    	$('.main_product_ti').each(function(index){
	    		if($('#smallProductId-'+index).val()!=undefined){
		    		if(index==0){
		    			smallProductId = $('#smallProductId-'+index).val();
		    		}else{
		    			smallProductId += "/"+$('#smallProductId-'+index).val();
		    		}
	    		} 
	    	});
	    	$.ajax({
	    		type : "get",
	    		url : "selectPostingBySmallProduct.do?pageNum="+pageNum+"&smallProductIdList="+smallProductId+"&memberId=${sessionScope.blliMemberVO.memberId}",
	    		success : function(data){
	    			var leftProductAjaxHTML = "";
	    			var rightPostingAjaxHTML = "";
	    			
	    			if(data.length==0){
	    				alert('현재 추천되는 소제품 관련 포스팅은 여기까지 입니다 ^^');
	    			}
	    			for(var i=0;i<data.length;i++){
	    				var postingScrapeAjaxHTML = '<i class="fa fa-bookmark-o postingScrapeBtn" style="color: gray" data-tooltip-text="스크랩해두시면 스크랩페이지에서 다시볼 수 있어요!"></i> ';
		    			var postingLikeAjaxHTML = '<i class="fa fa-thumbs-up postingLikeBtn" style="color: gray" data-tooltip-text="유익한 블로그였다면 바로 좋아요!"></i> ';
		    			var postingDisLikeAjaxHTML = '<i class="fa fa-thumbs-down postingDisLikeBtn" style="color: gray" data-tooltip-text="광고,상관없는 포스팅등은 가차없이 싫어요!"></i> ';
		    			
	    				if(data[i].isScrapped==1){
	    					postingScrapeAjaxHTML = '<i class="fa fa-bookmark postingScrapeBtn" style="color: orange" data-tooltip-text="스크랩해두시면 스크랩페이지에서 다시볼 수 있어요!"></i>';
	    				}
	    				if(data[i].isLike==1){
	    					postingLikeAjaxHTML = '<i class="fa fa-thumbs-up postingLikeBtn" style="color: hotpink" data-tooltip-text="유익한 블로그였다면 바로 좋아요!"></i>';
	    				}
	    				if(data[i].isDisLike==1){
	    					postingDisLikeAjaxHTML = '<i class="fa fa-thumbs-down postingDisLikeBtn" style="color: hotpink" data-tooltip-text="광고,상관없는 포스팅등은 가차없이 싫어요!"></i>';
	    				}
	    				$('.postingForSmallProduct').each(function(index){
	    						if(data[i].smallProductId==$($('.postingForSmallProduct').get(index)).children('.product_price').children('.fr').children().children('.smallProductId').val()){
		    						leftProductAjaxHTML += '<div style="height:357px;margin-left: 30px;width: 120px;" class="postingForSmallProduct">';
		    						leftProductAjaxHTML += $($('.postingForSmallProduct').get(index)).html();
		    						leftProductAjaxHTML += '</div>';
		    						return false;
			    				}
	    				})
						
					
						rightPostingAjaxHTML +=
						'<div style="height:325px; border: 1px solid #ccc;border-radius: 10px; margin-bottom:30px;"><div class="result_ti">'+
						'<div style="width:450px; overflow:hidden;white-space:nowrap; text-overflow:ellipsis;">'+
						data[i].postingTitle+
						'</div></div><div style="height:245px;"><div class="result_foto fl">'
						+'<a href="#" onclick=goBlogPosting("'+data[i].postingUrl+'","'+data[i].smallProductId+'")>'
						+'<img src="'+
						data[i].postingPhotoLink+
						'" style="width: 335px; height: 235px; MARGIN-LEFT:5px;"></a></div><div class="fl"><div class="product_text2">'+
						data[i].postingSummary+
						'</div><div class="product_id"><div style="width:180px; overflow:hidden;white-space:nowrap; text-overflow:ellipsis;">'+
						data[i].postingAuthor+
						'</div></div></div></div><div class="product_sns"><div class="fl score">'+
						data[i].postingScore+
						'점</div><div class="fr sns">'+
						'<input type="hidden" class="smallProductIdInfo" value="'+data[i].smallProductId+'">'+
						'<input type="hidden" class="postingUrlInfo" value="'+data[i].postingUrl+'">'+
						postingScrapeAjaxHTML+
						'<span style="font-size: 11px; line-height: 16px ;margin-right:15px" class="scrapeCount">'+data[i].postingScrapeCount+'</span>'+
						postingLikeAjaxHTML+
						'<span style="font-size: 11px; line-height: 16px ;margin-right:15px" class="likeCount">'+data[i].postingLikeCount+'</span>'+
						postingDisLikeAjaxHTML+
						'<span style="font-size: 11px; line-height: 16px ;margin-right:15px" class="disLikeCount">'+data[i].postingDislikeCount+'</span>'+
						'</div></div></div>'
	    			}
	    			
	    			$('.main_left_product').append(leftProductAjaxHTML);
	    			$('.main_left_con').append(rightPostingAjaxHTML);
	    			leftProductAjaxHTML = "";
	    		}
	   		});
		}
		
	});
</script>

<form id="blliMemberVOForm" action="sendPasswordMail.do" method="post">
	<input type="hidden" name="memberName" value="${sessionScope.blliMemberVO.memberName}">
	<input type="hidden" name="memberEmail" value="${sessionScope.blliMemberVO.memberEmail}">
</form>

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
				<c:forEach items="${requestScope.blliMemberVO.blliBabyVOList}" var="babyList">
					<c:if test="${babyList.recommending==1}">
				<div class="main_baby_foto">
					<img alt="아이 프사" src="${initParam.root}babyphoto/${babyList.babyPhoto}" style="border-radius:67px;width: 118px;height: 118px">
				</div>
				<div class="main_baby_name">
						 ${babyList.babyName } ${babyList.babyMonthAge} 개월 입니다.
				</div>
					</c:if>
				</c:forEach>
			</div>
			<div class="main_yellow_in2">
				<div class="main_ti">
					월령별 추천상품 
				</div>
				<div style="width:870px; float:left;">
				
					<div id="menu-wrapper">
						<ul class="midRecommProduct">
						<c:forEach items="${requestScope.blliMidCategoryVOList}" var="recommProductList">
							<li class="gallery-cell productInst" style="margin-left: 30px;">
								<div class="yellow_foto">
								<img src='${recommProductList.midCategoryMainPhotoLink}' style="width: 115px;height: 115px;border-radius:20px; z-index: -1">
								</div>
								<div class="productInstAfter" style="text-align: center;">
									<i class="fa fa-times fa-1-5x recommendMidDelete" style="margin-left: 120px;">
										<input type="hidden" class="midCategoryValue" value="${recommProductList.midCategory}">
										<input type="hidden" class="midCategoryIdValue" value="${recommProductList.midCategoryId}">
									</i>
									<div class="productName" style="font-size: 15px;font-weight: bold; margin-top:-15px">${recommProductList.midCategory}</div>
									<div class="productInstDetail" style="color: white; padding: 10px; text-align: justify;">
										일회용 기저귀는 아이가 항상 철결함을 유지할 수 있게 도와주며, 엄마는 쉽게 아이의 큰일을 처리할 수 있어요!(50자 설명)
									</div>
									<div class="smallProductDetailBtn">
										<a href="searchSmallProduct.do?searchWord=${recommProductList.midCategory}" style="color: white"> 
										상세보기</a>
									</div>
								</div>
								<div class="yellow_ti">
									${recommProductList.midCategory }
								</div>
							</li>
					</c:forEach>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="in_fr">
		<div class="main_ti">
			또래 자녀를 둔 엄마가 선택한 상품 
		</div>
		<div class="main_product">
			<ul class="midRecommProduct">
			<c:forEach items="${requestScope.blliSmallProductVOList}" var="blliSmallProductVOList" varStatus="smallProductIndex">
			
				<li class="gallery-cell">
				<%-- ${blliSmallProductVOList.midCategoryId} --%>
					<div class="foto205">
						<a href = "goSmallProductDetailView.do?smallProduct=${blliSmallProductVOList.smallProduct}">
							<img src="${blliSmallProductVOList.smallProductMainPhotoLink}" style="height: 207px; width: 207px;">
						</a>
						<div class="product_month">
						${blliSmallProductVOList.smallProductWhenToUseMin}~${blliSmallProductVOList.smallProductWhenToUseMax}<br/>
							개월
						</div>
					</div>
					<div class="main_product_ti">
						<div style='width:205px; overflow:hidden;white-space:nowrap; text-overflow:ellipsis;'>
								<input type="hidden" id="smallProductId-${smallProductIndex.index}" value="${blliSmallProductVOList.smallProductId}">
								${blliSmallProductVOList.smallProduct}
								 / ${blliSmallProductVOList.smallProductMaker}
						</div>
					</div>
					<div class="product_price" style="margin:0px;">
						<div class="fl">
							<p class="result_gray">최저가</p>
							<p class="result_price">${blliSmallProductVOList.minPrice}원</p>
						</div>
						<div class="fr">
						<div style="margin-top: 15px" class="smallProductDibBtn" data-tooltip-text="찜해두시면 스크랩페이지에서 다시 보실 수 있어요!">
						<c:if test="${blliSmallProductVOList.isDib==0}">
							<i class="fa fa-heart-o fa-2x" style="color: red"></i>
						</c:if>
						<c:if test="${blliSmallProductVOList.isDib==1}">
							<i class="fa fa-heart fa-2x" style="color: red"></i>
						</c:if>
							<span style="font-size: 15px ;color: gray;" class="dibsCount">${blliSmallProductVOList.smallProductDibsCount}</span>
							<input type="hidden" value="${blliSmallProductVOList.smallProductId}" class="smallProductId">
						</div>
						</div>
					</div>
				</li>
					</c:forEach>
			</ul>
		</div>
	</div>
<div class="in_fr" style="margin-top:20px; clear:both;">
<div class="main_ti">
			추천 상품을 포스팅한 블로그들
		</div>
		<div class="main_left_product">
			<c:forEach items="${requestScope.blliPostingVOList}" var="postingList">
				<c:forEach items="${requestScope.blliSmallProductVOList}" var="blliSmallProductVOList">
				<c:if test="${postingList.smallProductId==blliSmallProductVOList.smallProductId}">
				
			<div style="height:357px;margin-left: 30px;width: 120px;" class="postingForSmallProduct">
				<div class="foto170">
					<a href = "goSmallProductDetailView.do?smallProduct=${blliSmallProductVOList.smallProduct}">
					<img src="${blliSmallProductVOList.smallProductMainPhotoLink}" style="height: 120px; width: 120px;">
					</a>
					<div class="product_month">
						 ${blliSmallProductVOList.smallProductWhenToUseMin}~${blliSmallProductVOList.smallProductWhenToUseMax}<br/>
						개월
					</div>
				</div>
				<div class="main_product_ti">
						<div style='width:120px; overflow:hidden;white-space:nowrap; text-overflow:ellipsis;'>
							${blliSmallProductVOList.smallProduct} / ${blliSmallProductVOList.smallProductMaker}
						</div>
				</div>
				
				<div class="product_price" style="margin:0px;">
					<div class="fl">
						<p class="result_gray">최저가</p>
						<p class="result_price">${blliSmallProductVOList.minPrice}원</p>
					</div>
				
					<div class="fr">
						
						<div style="margin-top: 15px" class="smallProductDibBtn" data-tooltip-text="찜해두시면 스크랩페이지에서 다시 보실 수 있어요!">
						<c:if test="${blliSmallProductVOList.isDib==0}">
							<i class="fa fa-heart-o fa-2x" style="color: red"></i>
						</c:if>
						<c:if test="${blliSmallProductVOList.isDib==1}">
							<i class="fa fa-heart fa-2x" style="color: red"></i>
						</c:if>
							<span style="font-size: 15px ;color: gray;" class="dibsCount">${blliSmallProductVOList.smallProductDibsCount}</span>
							<input type="hidden" value="${blliSmallProductVOList.smallProductId}" class="smallProductId">
						</div>
					</div>
				</div>
			</div>
			
			</c:if>
				</c:forEach>
			</c:forEach>
		</div>
	

		
		<div class="main_left_con">
			<c:forEach items="${requestScope.blliPostingVOList}" var="postingList">
			<div style="height:325px; border: 1px solid #ccc;border-radius: 10px; margin-bottom:30px;">
				<div class="result_ti">
					<div style='width:450px; overflow:hidden;white-space:nowrap; text-overflow:ellipsis;'>
							${postingList.postingTitle}
					</div>
					
				</div>
				<div style="height:245px;">
					<div class="result_foto fl">
					
						<a href="#" onclick="goBlogPosting('${postingList.postingUrl}','${postingList.smallProductId}')">
							<img src="${postingList.postingPhotoLink}" style="width: 335px; height: 235px; MARGIN-LEFT:5px;">
						</a>
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
			<%-- <p align="center"><img id="loading" src="${initParam.root}image/loading.gif" style="width: 50px"></p> --%>
			</c:forEach>
		</div>
		<div class="main_right_list">
			<div class="main_right_ti">
				월령별 추천상품
			</div>
			<div class="accordion accordion1">
				<c:forEach items="${requestScope.blliMidCategoryVOList}" var="recommProductList" varStatus="midProductNum">
				<div class="accordion-section">
					<input type="hidden" value="${recommProductList.midCategoryId}" class="midCategoryIdValue"> 
					<a class="accordion-section-header" data-target="#accordion-${midProductNum.index}">
						<div class="foto50">
							<img alt="" src="${recommProductList.midCategoryMainPhotoLink}" style="width: 50px;height: 50px;">
						</div>
						<div class="main_right_name">
							${recommProductList.midCategory }
						</div>
					</a>
					<div id="accordion-${midProductNum.index}" class="accordion-section-content">
						<table>
							
						</table>
					</div>
				</div>
				</c:forEach>
			</div>

		</div>
		 <a href="#" id="return-to-top"><i class="fa fa-chevron-up"></i></a>
</div>







