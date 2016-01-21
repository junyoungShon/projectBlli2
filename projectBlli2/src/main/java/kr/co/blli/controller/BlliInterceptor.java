package kr.co.blli.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class BlliInterceptor extends HandlerInterceptorAdapter{
	
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		//모든 요청에 대해 이전 주소 출력
		String beforeURL = request.getHeader("referer");
		String currentURL = request.getRequestURI();
		System.out.println("이전주소 "+beforeURL+"/ 현재주소 : "+currentURL);
		
		HttpSession session = request.getSession();
		//세션을 확인하여 blliMemberVO가 없을 시 index.do로 반환
		if(session.getAttribute("blliMemberVO")==null){
			response.setHeader("referer", "http://bllidev.dev/projectBlli2");
			response.sendRedirect("/projectBlli2/index.do");
			return false;
		}
		return true;
	}
}
