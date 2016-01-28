package kr.co.blli.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class BlliInterceptor extends HandlerInterceptorAdapter{
	
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		/*//모든 요청에 대해 이전 주소 출력
		String requestUrl = request.getRequestURL().toString();
		HttpSession session = request.getSession();
		System.out.println(requestUrl);
		//세션을 확인하여 blliMemberVO가 없을 시 index.do로 반환
		if(!requestUrl.equals("http://localhost:8888/projectBlli2/index.do")){
			if(session.getAttribute("blliMemberVO")==null){
				session.invalidate();
				response.sendRedirect("/projectBlli2/index.do");
				return false;
			}
		}*/
		return true;
	}
}
