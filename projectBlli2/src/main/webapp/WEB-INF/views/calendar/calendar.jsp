<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript">
	
	$(document).ready(function(){
		
		var dateObj = new Date();
		var year = dateObj.getFullYear();
		var month = dateObj.getMonth()+1;
		var day = dateObj.getDate();
		
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
	        	if((i+1-d1)==d) {
	        		calendarText += '<td bgcolor="#f2f2f2"; ' + (i%7 ? '' : ' style="color:red;"') + '><p class="fl cal_day">' + (i+1-d1) +'</p><p></p><p></p><p></p></td>';
		        } else {
		        	calendarText += '<td' + (i%7 ? '' : ' style="color:red;"') + '><p class="fl cal_day">' + (i+1-d1) + '</p><p></p><p></p><p></p></td>';
		        }
	        }
	        
	        //달력 주차수가 5줄로 모자를 경우 한줄 더 늘려준다.
	        if(i==34 && (i+1-d1)<numberOfDaysOfThisMonth) {
	        	totalCellNumber = 42;
	        }
	        
	    }
	    document.getElementById('calendarDiv').innerHTML = calendarText + '</tr>\n</table>'; 
	}
	
	//일정을 추가하고자 하는 일자를 클릭시 해당 일자의 정보와 함께 일정 추가 페이지로 이동
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
		<div class="in_fr">
			<div class="result_ti" style='margin-top:10px;'>
				이달의 아이 일정
			</div>
			<div>
				<div id="calendarControllerDiv" style='margin-top:30px;'></div>
				<div id="calendarDiv" class="calendar">
					<!-- <tr>
						<td>
							<p class="fr cal_day">
								1
							</p>
							<p>
							</p>
							<p>
							</p>
							<p>
							</p>
						</td>
						<td>
							<p class="cal_day">
								7
							</p>
							<p class="cal_bg1" onclick="location.href='./calendar1.html'">
								첫째생일
							</p>
							<p class="cal_bg2" onclick="location.href='./calendar2.html'">
								둘째생일
							</p>
							<p class="cal_bg3" onclick="location.href='./calendar3.html'">
								셋째생일
							</p>
						</td>
					</tr> -->
				</div>
				<!-- <div>
					<a href="./calendar_plus.html"><img src="./img/cal_plus2.jpg" alt="일정 추가하기" class="fr"></a>
				</div> -->
			</div>
		</div>
    </div>
</body>
</html>