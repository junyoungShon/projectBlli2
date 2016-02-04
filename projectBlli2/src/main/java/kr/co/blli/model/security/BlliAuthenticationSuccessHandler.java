package kr.co.blli.model.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.blli.model.vo.BlliMemberVO;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class BlliAuthenticationSuccessHandler implements AuthenticationSuccessHandler{
	/**
	  * @Method Name : onAuthenticationSuccess
	  * @Method 설명 : 자동로그인 후 세션 정보 담아줌
	  * @작성일 : 2016. 1. 18.
	  * @작성자 : junyoung
	  * @param request
	  * @param response
	  * @param authentication
	  * @throws IOException
	  * @throws ServletException
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		HttpSession session = request.getSession();
		BlliMemberVO blliMemberVO = new BlliMemberVO();
		blliMemberVO.setMemberId(authentication.getName());
		session.setAttribute("blliMemberVO", blliMemberVO);
		response.sendRedirect("authorityCheck.do");
	}
}
