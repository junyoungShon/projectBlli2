package kr.co.blli.controller;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.blli.model.vo.BlliMemberVO;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class BlliInterceptor extends HandlerInterceptorAdapter{
	
	
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		Boolean flag = true;
		/*//모든 요청에 대해 이전 주소 출력
		String requestUrl = request.getRequestURL().toString();
		// session검사
		HttpSession session = request.getSession(false);
		System.out.println(requestUrl);
		if(session!=null){
			if(session.getAttribute("SPRING_SECURITY_CONTEXT")!=null){
				System.out.println("스프링 세션 있다.");
				BlliMemberVO blliMemberVO = (BlliMemberVO) session.getAttribute("blliMemberVO");
				if(blliMemberVO!=null){
					if(blliMemberVO.getMemberId()!=null){
						flag = true;
					}else{
						session.invalidate();
						response.sendRedirect("loginPage.do");
						flag = false;
					}
				}else{
					session.invalidate();
					response.sendRedirect("loginPage.do");
					flag = false;
				}
			}else{
				
			}
		}else{
			System.out.println(session);
			System.out.println("durldksdhsi");
			if(requestUrl.equals("http://bllidev.dev/projectBlli2/index.do")||requestUrl.equals("http://bllidev.dev/projectBlli2/loginPage.do")){
				flag = true;
			}else if(requestUrl.equals("http://bllidev.dev/projectBlli2/goJoinMemberPage.do")){
				flag = true;
			}else{
				response.sendRedirect("index.do");
				flag = false;
			}
		}*/
		return flag;
	}
}
