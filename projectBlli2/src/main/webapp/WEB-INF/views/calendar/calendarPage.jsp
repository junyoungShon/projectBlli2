<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
	
	var dateObj = new Date();
	var year = dateObj.getFullYear();
	var month = dateObj.getMonth()+1;
	var day = dateObj.getDate();
	
	$(document).ready(function(){
		showCalendar(year, month, day);
	});
	
	//달력 츌력
	function showCalendar(y, m, d) {
		
		var calendarControllerText = "<a href='#'><img src='./img/allow_lgray.jpg' alt='왼쪽 화살표' class='fl' onclick='showCalendar("+(m==1?(y-1)+","+12:y+","+(m-1))+")'></a>"
		+"<div class='fl cal'><p class='cal_year'>"+y+"년</p><p class='cal_month'>"+m+"월</p></div>"
		+"<a href='#'><img src='./img/allow_rgray.jpg' alt='오른쪽 화살표' class='fr' onclick='showCalendar("+(m==12?(y+1)+","+1:y+","+(m+1))+")'></a>";
		
		document.getElementById('calendarControllerDiv').innerHTML = calendarControllerText;		
		
	    var calendarText = "<table><colgroup><col width='148px'><col width='148px'><col width='148px'>"
	    			+"<col width='148px'><col width='148px'><col width='148px'><col width='148px'></colgroup>"
	    			+"<tr><th>Sun</th><th>Mon</th><th>Tue</th><th>Wed</th><th>Thu</th><th>Fri</th><th>Sat</th></tr>"; 

	    var d1 = (y+(y-y%4)/4-(y-y%100)/100+(y-y%400)/400 + m*2+(m*5-m*5%9)/9-(m<3?y%4||y%100==0&&y%400?2:3:4))%7;
	    var numberOfDaysOfThisMonth = (m*9-m*9%8)/8%2+(m==2?y%4||y%100==0&&y%400?28:29:30);
	    var totalCellNumber = 35;
	    
	    for (i = 0; i < totalCellNumber; i++) {
	    	
	        if (i%7==0) {
	        	calendarText += '</tr>\n<tr>';
	        }
	    	
	        if (i < d1 || i >= d1+numberOfDaysOfThisMonth) {
	        	calendarText += '<td><p></p><p></p><p></p><p></p></td>';
	        } else {
	        	//오늘 일자 표시
	        	if((i+1-d1)==day) {
	        		calendarText += '<td class="calendarDateCell" bgcolor="#f2f2f2"; ' + (i%7 ? '' : ' style="color:red;"') + '><p class="fl cal_day">' + (i+1-d1) +'</p><p></p><p></p><p></p></td>';
		        } else {
		        	calendarText += '<td class="calendarDateCell"' + (i%7 ? '' : ' style="color:red;"') + ((i-6)%7 ? '' : ' style="color:blue;"')
		        					+ '><p class="fl cal_day">' + (i+1-d1) + '</p><p></p><p></p><p></p></td>';
		        }
	        }
	        
	        //달력 주차수가 5줄로 모자를 경우 한줄 더 늘려준다.
	        if(i==34 && (i+1-d1)<numberOfDaysOfThisMonth) {
	        	totalCellNumber = 42;
	        }
	    }
	    document.getElementById('calendarDiv').innerHTML = calendarText + '</tr>\n</table>'; 
	}
	
	//일정을 추가하고자 하는 일자를 클릭시 해당 일자의 정보와 함께 일정 추가 양식 우측에 출력
	$(document).on("click", ".calendarDateCell", function(){
		
		var thisYear = $(".cal_year").text().substring(0,4);
		var thisMonth = $(".cal_month").text().substring(0,$(".cal_month").text().length-1);
		var thisDay = $(this).children().eq(0).text();
		
		showAddSchduleForm(thisYear, thisMonth, thisDay);
	});
	
	
	function showAddSchduleForm(thisYear, thisMonth, thisDay) {
		
		var thisDate = thisYear+"년 " + thisMonth+"월 "+thisDay+"일"
		
		var memberBabyNameList = "";
		for(var i=0;i<$("input[name=blliBabyVOList]").length;i++) {
			memberBabyNameList += '<td><input type="checkbox" name="babyName" value="'+$("input[name=blliBabyVOList]")[i].value+'" style="height:12px;">'
								+ $("input[name=blliBabyVOList]")[i].value + '</td>';
		}
		
		var showAddSchduleFormText = '<div class="cal_plus_form"><div class="cal_plus_ti">아이 일정 추가</div>'
										+'<table><tr><th colspan="3">누구의 일정인가요?</th></tr>'
										+'<tr>'+memberBabyNameList+'</tr>'
										+'<tr><th colspan="2">날짜</th><th>장소입력</th></tr>'
										+'<tr><td colspan="2">'+thisDate+'</td>'
										+'<td><input type="text" name="scheduleLocation" size="10"></td></tr>'
										+'<tr><th colspan="3">제목</th></tr><tr><td colspan="3"><input type="text" style="width:100%;" name="scheduleTitle"></td></tr><tr><th colspan="3">'
										+'상세내용</th></tr><tr><td colspan="3"><textarea name="scheduleContent"></textarea></td></tr><tr><td colspan="3">'
										+'<a href="#"><img src="./img/cal_plus3.jpg" alt="일정추가하기" class="fl" id="addScheduleBtn"></a>&nbsp'
										+'<a href="#"><img src="./img/cal_cancle.jpg" alt="취소하기" class="fr" id="addScheduleCancelBtn"></a>'
										+'</td></tr></table></div>';
		
		document.getElementById('addScheduleForm').innerHTML = showAddSchduleFormText;
	}
	
	
	$(document).on("click", "#addScheduleBtn", function(){
		
		if($("input[name=babyName]:checked").length<1) {
			alert("아이를 체크해주세요.");
			return;
		}
		if($("input[name=scheduleLocation]").val()=="") {
			alert("일정 장소를 입력해주세요.");
			return;
		}
		if($("input[name=scheduleTitle]").val()=="") {
			alert("일정 제목을 입력해주세요.");
			return;
		}
		if($("textarea[name=scheduleContent]").val()=="") {
			alert("내용을 입력해주세요.");
			return;
		}
		
		$.ajax({
			type: "POST",
			url: "${initParam.root}addSchedule.do",
			data: "babyName="+$("input[name=babyName]:checked").val()
					+"&scheduleDate="+$("input[name=scheduleDate]").val()
					+"&memberId=${sessionScope.blliMemberVO.memberId}"
					+"&scheduleTitle="+$("input[name=scheduleTitle]").val()
					+"&scheduleLocation="+$("input[name=scheduleLocation]").val()
					+"&scheduleContent="+$("textarea[name=scheduleContent]").val(),
			cache: false,
			success: function(bsvo){
				
				var thisYear = bsvo.scheduleDate.substring(0,4);
				var thisMonth = bsvo.scheduleDate.substring(4,6);
				if(thisMonth.indexOf('0')==0) {
					thisMonth = thisMonth.substring(1);
				}
				var thisDay = bsvo.scheduleDate.substring(6,8);
				if(thisDay.indexOf('0')==0) {
					thisDay = thisDay.substring(1);
				}
				
				//1씩 곱해서 string으로 변한 숫자를 다시 number로 만든다
				thisYear = thisYear*1;
				thisMonth = thisMonth*1;
				thisDay = thisDay*1;
				
				showCalendar(thisYear, thisMonth, thisDay);
				alert("일정이 추가되었습니다.");
				showSchduleDetail(bsvo);
			}
	    });
	});
	
	
	function showSchduleDetail(bsvo) {
		
		var yearToString = bsvo.scheduleDate.substring(0,4)+"년 ";
		
		//1의 자리라서 앞에 0이 붙어있었다면 지워준다.
		var monthToString = bsvo.scheduleDate.substring(4,6)+"월 ";
		if(monthToString.indexOf('0')==0) {
			monthToString = monthToString.substring(1);
		}
		var dayToString = bsvo.scheduleDate.substring(6,8)+"일";
		if(dayToString.indexOf('0')==0) {
			dayToString = dayToString.substring(1);
		}
		
		var dateToString = yearToString + monthToString + dayToString;
		
		var showSchduleDetailText = '<div class="cal_plus_form"><div class="cal_plus_ti">일정 보기</div>'
										+'<table><tr><th colspan="3">누구의 일정인가요?</th></tr>'
										+'<tr><td>'+bsvo.babyName+'</td></tr>'
										+'<tr><th colspan="2">날짜</th><th>장소</th></tr>'
										+'<tr><td colspan="2">'+dateToString+'</td>'
										+'<td>'+bsvo.scheduleLocation+'</td></tr>'
										+'<tr><th colspan="3">제목</th></tr><tr><td colspan="3">'+bsvo.scheduleTitle+'</td></tr>'
										+'<tr><th colspan="3">내용</th></tr><tr><td colspan="3">'+bsvo.scheduleContent+'</td>'
										+'<input type="hidden" name="scheduleId" value="'+bsvo.scheduleId+'"></tr>'
										+'<tr><td colspan="3">'
										+'<a href="#"><img src="./img/cal_plus3.jpg" alt="수정하기" class="fl" id="changeFormToUpdateScheduleBtn"></a>&nbsp'
										+'<a href="#"><img src="./img/cal_cancle.jpg" alt="취소하기" class="fr" id="addScheduleCancelBtn"></a>'
										+'</td></tr></table></div>';
			
		document.getElementById('addScheduleForm').innerHTML = showSchduleDetailText;
	}
	
	
	$(document).on("click", "#addScheduleCancelBtn", function(){
		document.getElementById('addScheduleForm').innerHTML = '';
	});
	
	$(document).on("click", "#changeFormToUpdateScheduleBtn", function(){
		
		var babyName = $(this).parent().parent().parent().siblings().eq(1).children().text();
		var date = $(this).parent().parent().parent().siblings().eq(3).children().siblings().eq(0).text();
		var location = $(this).parent().parent().parent().siblings().eq(3).children().siblings().eq(1).text();
		var title = $(this).parent().parent().parent().siblings().eq(5).children().text();
		var content = $(this).parent().parent().parent().siblings().eq(7).children().text();
						
		var scheduleId = $(this).parent().parent().parent().siblings().eq(7).children().siblings().eq(1).val();
		
		var updateScheduleText = '<div class="cal_plus_form"><div class="cal_plus_ti">일정 수정</div>'
										+'<table><tr><th colspan="3">누구의 일정인가요?</th></tr>'
										+'<tr><td>'+babyName+'</td></tr>'
										+'<tr><th colspan="2">날짜</th><th>장소</th></tr>'
										+'<tr><td colspan="2">'+date+'</td>'
										+'<td><input type="text" size="14" name="scheduleLocation" value="'+location+'"></td></tr>'
										+'<tr><th colspan="3">제목</th></tr><tr><td colspan="3"><input type="text" style="width:100%;" name="scheduleTitle" value="'
										+title+'"></td></tr>'
										+'<tr><th colspan="3">내용</th></tr><tr><td colspan="3"><input type="text" style="width:100%;" name="scheduleContent" value="'
										+content+'"></td><input type="hidden" name="scheduleId" value="'+scheduleId+'"></tr><tr><td colspan="3">'
										+'<a href="#"><img src="./img/cal_plus3.jpg" alt="확인" class="fl" id="updateScheduleBtn"></a>&nbsp'
										+'<a href="#"><img src="./img/cal_cancle.jpg" alt="취소" class="fr" id="addScheduleCancelBtn"></a>'
										+'</td></tr></table></div>';
									
		document.getElementById('addScheduleForm').innerHTML = updateScheduleText;
	});
	
	$(document).on("click", "#updateScheduleBtn", function(){
		
		var locationVal = $(this).parent().parent().parent().siblings().eq(3).children().siblings().eq(1).children().val();
		var titleVal = $(this).parent().parent().parent().siblings().eq(5).children().children().val();
		var contentVal = $(this).parent().parent().parent().siblings().eq(7).children().children().val();
		
		var scheduleIdVal = $(this).parent().parent().parent().siblings().eq(7).children().siblings().eq(1).val();
		
		alert(scheduleIdVal);
		
		if(locationVal=="") {
			alert("일정 장소를 입력해주세요.");
			return;
		}
		if(titleVal=="") {
			alert("일정 제목을 입력해주세요.");
			return;
		}
		if(contentVal=="") {
			alert("내용을 입력해주세요.");
			return;
		}
		
		$.ajax({
			type: "POST",
			url: "${initParam.root}updateSchedule.do",
			data: "scheduleId="+scheduleIdVal
					+"&scheduleTitle="+titleVal
					+"&scheduleLocation="+locationVal
					+"&scheduleContent="+contentVal,
			cache: false,
			success: function(bsvo){
				
				alert(bsvo.memberId);
				alert(bsvo.scheduleDate);
				alert(bsvo.scheduleLocation);
				alert(bsvo.scheduleTitle);
				alert(bsvo.scheduleContent);
				alert(bsvo.babyName);
				
				//1의 자리라서 앞에 0이 붙어있었다면 지워준다.
				var thisYear = bsvo.scheduleDate.substring(0,4);
				var thisMonth = bsvo.scheduleDate.substring(4,6);
				if(thisMonth.indexOf('0')==0) {
					thisMonth = thisMonth.substring(1);
				}
				var thisDay = bsvo.scheduleDate.substring(6,8);
				if(thisDay.indexOf('0')==0) {
					thisDay = thisDay.substring(1);
				}
				
				//1씩 곱해서 string으로 변한 숫자를 다시 number로 만든다
				thisYear = thisYear*1;
				thisMonth = thisMonth*1;
				thisDay = thisDay*1;
				
				showCalendar(thisYear, thisMonth, thisDay);
				alert("일정이 수정되었습니다.");
				showSchduleDetail(bsvo);
			}
	    });
		
	});
	
</script>

<body>

	<c:forEach items="${sessionScope.blliMemberVO.blliBabyVOList}" var="blliBabyVOList">
		<input type="hidden" name="blliBabyVOList" value="${blliBabyVOList.babyName}">
	</c:forEach>

    <div class="jbContent">
		<div class="in_fr">
			<div>
				<div class="result_ti" style='margin-top:10px; text-shadow: 0 0 3px #b3b3b3; -moz-text-shadow: 0 0 3px #b3b3b3; -webkit-text-shadow: 0 0 3px #b3b3b3;'>
					이달의 아이 일정
				</div>
				<div>
					<div id="calendarControllerDiv" style='margin-top:30px;'></div>
					<div id="calendarDiv" class="calendar"></div>
				</div>
			</div>
		</div>
		<div id="addScheduleForm"></div>
    </div>
</body>