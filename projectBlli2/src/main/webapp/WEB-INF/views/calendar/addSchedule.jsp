<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript">
	
	$(document).ready(function(){
		
		var dateObj = new Date();
		var year = dateObj.getFullYear();
		var month = dateObj.getMonth()+1;
		var day = dateObj.getDate();
		var today = year + "-" + month + "-" + day;
		
		showCalendar(year, month, day);
	});
	
	
	function calendarController(y, m){
		var text = "<a href='#'><img src='./img/allow_lgray.jpg' alt='왼쪽 화살표' class='fl' onclick='showCalendar("+(m==1?(y-1)+","+12:y+","+(m-1))+")'></a>"
					+"<div class='fl cal'><p class='cal_year'>"+y+"년</p><p class='cal_month'>"+m+"월</p></div>"
					+"<a href='#'><img src='./img/allow_rgray.jpg' alt='오른쪽 화살표' class='fr' onclick='showCalendar("+(m==12?(y+1)+","+1:y+","+(m+1))+")'></a>";
					
		document.getElementById('calendarControllerDiv').innerHTML = text; 				
	}
	
	function showCalendar(y, m, d) { 
		
		calendarController(y, m);
		
	    var text = "<table><colgroup><col width='148px'><col width='148px'><col width='148px'>"
	    			+"<col width='148px'><col width='148px'><col width='148px'><col width='148px'></colgroup>"
	    			+"<tr><th>Sun</th><th>Mon</th><th>Tue</th><th>Wed</th><th>Thu</th><th>Fri</th><th>Sat</th></tr>"; 

	    var d1 = (y+(y-y%4)/4-(y-y%100)/100+(y-y%400)/400 + m*2+(m*5-m*5%9)/9-(m<3?y%4||y%100==0&&y%400?2:3:4))%7;
	    var numberOfDaysOfThisMonth = (m*9-m*9%8)/8%2+(m==2?y%4||y%100==0&&y%400?28:29:30);
	    var totalCellNumber = 35;
	    
	    for (i = 0; i < totalCellNumber; i++) {
	    	
	        if (i%7==0) {
	        	text += '</tr>\n<tr>'; 
	        }
	    	
	        if (i < d1 || i >= d1+numberOfDaysOfThisMonth) {
	            text += '<td><p></p><p></p><p></p><p></p></td>';
	        } else {
	        	if((i+1-d1)==d) {
	            	text += '<td bgcolor="#f2f2f2"; ' + (i%7 ? '' : ' style="color:red;"') + '><p class="fl cal_day">' + (i+1-d1) +'</p><p></p><p></p><p></p></td>';
		        } else {
	            	text += '<td' + (i%7 ? '' : ' style="color:red;"') + '><p class="fl cal_day">' + (i+1-d1) + '</p><p></p><p></p><p></p></td>';
		        }
	        }
	        
	        if(i==34 && (i+1-d1)<numberOfDaysOfThisMonth) {
	        	totalCellNumber = 42;
	        }
	        
	    } 
	    document.getElementById('calendarDiv').innerHTML = text + '</tr>\n</table>'; 
	}
	
	$(document).on("click", "td", function(){
		
		var thisYear = $(".cal_year").text().substring(2,4);
		var thisMonth = $(".cal_month").text().substring(0,$(".cal_month").text().length-1);
		var thisDay = $(this).children().eq(0).text();
		
		if(thisMonth<10) {
			thisMonth = "0" + thisMonth;
		}
		if(thisDay<10) {
			thisDay = "0" + thisDay;
		}
		
		location.href="goToAddScheduleOnCalendar.do?today="+thisYear+thisMonth+thisDay;
	});
	
	
	/* function showCalendar(y, m) {
	    var text = '<table>\n<tr><td colspan=7 style="text-align:center">'; 
	    text += '<span onclick="showCalendar('+(y-1)+','+m+')"> Y- </span>'; 
	    text += '<span onclick="showCalendar('+(m==1?(y-1)+','+12:y+','+(m-1))+')"> M- </span>'; 
	    text += '[' + y + '/' + ((m < 10) ? ('0' + m) : m) + ']'; 
	    text += '<span onclick="showCalendar('+(m==12?(y+1)+','+1:y+','+(m+1))+')"> M+ </span>'; 
	    text += '<span onclick="showCalendar('+(y+1)+','+m+')"> Y+ </span>'; 
	    text += '</td>';

	    var d1 = (y+(y-y%4)/4-(y-y%100)/100+(y-y%400)/400 + m*2+(m*5-m*5%9)/9-(m<3?y%4||y%100==0&&y%400?2:3:4))%7; 
	    for (i = 0; i < 42; i++) {
	        if (i%7==0) {
	        	text += '</tr>\n<tr>'; 
	        }
	        if (i < d1 || i >= d1+(m*9-m*9%8)/8%2+(m==2?y%4||y%100==0&&y%400?28:29:30)) {
	            text += '<td> </td>';
	        } else {
	            text += '<td' + (i%7 ? '' : ' style="color:red;"') + '>' + (i+1-d1) + '</td>';
	        }
	    } 
	    document.getElementById('calendarDiv').innerHTML = text + '</tr>\n</table>'; 
	} */
	
</script>

<body> 
<!-- <div id="calendarDiv" style="font-family:Gulim;font-size:9pt;"></div> -->

    <div class="jbContent">
    <div class="cal_plus_form">
			<div class="cal_plus_ti">
				아이 일정 추가
			</div>
			<!--div class="cal_plus_quti">
				누구의 일정인가요?
			</div>
			<div class="cal_plus_answer">
				<input type="checkbox"> 똘기
				<input type="checkbox"> 떵이
				<input type="checkbox"> 새초미
			</div-->
			<table>
				<tr>
					<th colspan="3">
						누구의 일정인가요?
					</th>
				</tr>
				<tr>
					<td colspan="3">
						<input type="checkbox" style="height:12px;"> 똘기
						<input type="checkbox" style="height:12px;"> 떵이
						<input type="checkbox" style="height:12px;"> 새초미
					</td>
				</tr>
				<tr>
					<th>
						날짜입력
					</th>
					<th colspan="2">
						장소입력
					</th>
				</tr>
				<tr>
					<td>
						<input type="text" style="width:100px;">
					</td>
					<td colspan="2">
						<input type="text">
					</td>
				</tr>
				<tr>
					<th colspan="3">
						제목
					</th>
				</tr>
				<tr>
					<td colspan="3">
						<input type="text" style="width:100%;">
					</td>
				</tr>
				<tr>
					<th colspan="3">
						상세내용
					</th>
				</tr>
				<tr>
					<td colspan="3">
						<textarea>
						</textarea>
					</td>
				</tr>
				<tr>
					<td colspan="3">
						<a href="#"><img src="./img/cal_plus3.jpg" alt="일정추가하기" class="fl"></a>
						<a href="#"><img src="./img/cal_cancle.jpg" alt="취소하기" class="fr"></a>
					</td>
				</tr>
			</table>
		</div>
		<div class="in_fr">
			<div class="result_ti" style='margin-top:10px;'>
				이달의 아이 일정
			</div>
			<div>
				<div id="calendarControllerDiv" style='margin-top:30px;'></div>
				<div id="calendarDiv" class="calendar">	</div>
			</div>
		</div>
    </div>
</body>
</html>