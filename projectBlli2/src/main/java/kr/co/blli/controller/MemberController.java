package kr.co.blli.controller;

import java.util.ArrayList;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import kr.co.blli.model.member.MemberService;
import kr.co.blli.model.product.ProductService;
import kr.co.blli.model.security.BlliUserDetailsService;
import kr.co.blli.model.vo.BlliBabyVO;
import kr.co.blli.model.vo.BlliMemberVO;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MemberController {
	@Resource
	private MemberService memberService;
	@Resource
	private ProductService productService;
	@Resource
	private BlliUserDetailsService blliUserDetailsService;
	
	/**
	 * 
	  * @Method Name : goAnyWhere
	  * @Method 설명 : 요청 없는 단순 페이지 이동 시 viewId.jsp로 이동시켜주는 메서드
	  * @작성일 : 2016. 1. 13.
	  * @작성자 : junyoung
	  * @param viewId
	  * @return
	 */
	@RequestMapping("{viewId}.do")
	public String goAnyWhere(@PathVariable String viewId){
		return viewId;
	}
	/**
	  * @Method Name : proceedingToMain
	  * @Method 설명 : 로그인 혹은 회원 가입 후 메인으로 진입하기 위한 중간페이지
	  * @작성일 : 2016. 1. 14.
	  * @작성자 : junyoung
	  * @return
	 */
	@RequestMapping("member_proceedingToMain.do")
	public ModelAndView proceedingToMain(){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:member_goMain.do");
		return mav;
	}
	/**
	 * 
	  * @Method Name : goMainPage
	  * @Method 설명 : 메인 페이지로 이동하는 메서드로서 스프링 시큐리티가 세션에 담은 memberEmail을 key값으로 하여 메인페이지에 필요한 정보를
	  * 세션에 담는다.
	  * @작성일 : 2016. 1. 13.
	  * @작성자 : junyoung
	  * @return
	 */
	@RequestMapping("member_goMain.do")
	public ModelAndView goMainPage(HttpServletRequest request){
		HttpSession session =  request.getSession();
		SecurityContext ctx=(SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
		Authentication auth=ctx.getAuthentication();
		System.out.println(auth.getName());
		//테스트를 위해 임시로 blliMemberVO를 생성하였음
		BlliMemberVO blliMemberVO = memberService.selectBlliMemberInfoByMemberId(auth.getName());
		session.setAttribute("blliMemberVO", blliMemberVO);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/blli/main");
		return mav;
	}
	/**
	  * @Method Name : loginByKakaoId
	  * @Method 설명 : 카카오톡으로 로그인 하는 사용자를 컨트롤러 단에서 강제로 로그인 시켜 시큐리티에 정보를 넣는 메서드
	  * @작성일 : 2016. 1. 18.
	  * @작성자 : junyoung
	  * @param request
	  * @param blliMemberVO
	  * @return
	 */
	@RequestMapping("loginBySNSId.do")
	public String loginBySNSId(HttpServletRequest request,BlliMemberVO blliMemberVO){
		UserDetails userInfo = (UserDetails) blliUserDetailsService.loadUserByUsername(blliMemberVO.getMemberId());
		Authentication authentication = new UsernamePasswordAuthenticationToken(userInfo, "protected",userInfo.getAuthorities());
		SecurityContext securityContext = SecurityContextHolder.getContext();
		securityContext.setAuthentication(authentication);
		blliMemberVO = memberService.findMemberById(blliMemberVO);
		blliMemberVO.setMemberPassword("PROTECTED");
		HttpSession session = request.getSession(true);
		session.setAttribute("blliMemberVO", blliMemberVO);
		session.setAttribute("SPRING_SECURITY_CONTEXT",securityContext);   // 세션에 spring security context 넣음
		return "redirect:member_proceedingToMain.do";
	}
	
	/**
	 * 
	  * @Method Name : goAdminPage
	  * @Method 설명 : 스프링 시큐리티 관리자 테스트 페이지 이동
	  * @작성일 : 2016. 1. 13.
	  * @작성자 : junyoung
	  * @return
	 */
	@RequestMapping("admin_goAdminPage.do")
	public ModelAndView goAdminPage(){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/admin/adminPage");
		return mav;
	}
	/**
	 * 
	  * @Method Name : goJoinMemberPage
	  * @Method 설명 : 로그인 페이지로 이동
	  * @작성일 : 2016. 1. 14.
	  * @작성자 : junyoung
	  * @return
	 */
	@RequestMapping("goJoinMemberPage.do")
	public String goJoinMemberPage(){
		return "memberjoin/memberjoin";
	}
	/**
	  * @Method Name : joinMemberByEmail
	  * @Method 설명 : 이메일을 통한 회원가입 시 커치는 메서드
	  * @작성일 : 2016. 1. 14.
	  * @작성자 : junyoung
	  * @param blliMemberVO
	  * @param blliChildVO
	  * @return
	 */
	@RequestMapping("joinMemberByEmail.do")
	public ModelAndView joinMemberByEmail(HttpServletRequest request, BlliMemberVO blliMemberVO){
		System.out.println(blliMemberVO);
		memberService.joinMemberByEmail(blliMemberVO);
		blliMemberVO.setMemberPassword("protected");
		HttpSession session = request.getSession();
		session.setAttribute("blliMemberVO", blliMemberVO);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:authorityCheck.do");
		return mav;
	}
	/**
	 * 
	  * @Method Name : joinMemberBySNS
	  * @Method 설명 : sns를 통한 회원가입
	  * @작성일 : 2016. 1. 16.
	  * @작성자 : junyoung
	  * @param blliMemberVO
	  * @return
	 */
	@RequestMapping("joinMemberBySNS.do")
	public ModelAndView joinMemberByKakaoId(HttpServletRequest request, BlliMemberVO blliMemberVO){
		if(blliMemberVO.getMemberId().startsWith("naver")){
			String memberId = blliMemberVO.getMemberId();
			blliMemberVO.setMemberEmail(blliMemberVO.getMemberId());
			//사용자 이름이 없으므로 이메일 아이디로 이름 대체
			String memberName = memberId.substring(0, memberId.lastIndexOf("@"));;
			blliMemberVO.setMemberName(memberName);
		}else if(blliMemberVO.getMemberId().startsWith("kakao")){
			blliMemberVO.setMemberEmail("needsYourEmail");
		}else if(blliMemberVO.getMemberId().startsWith("fb")){
			blliMemberVO.setMemberEmail("needsYourEmail");
		}
		blliMemberVO.setAuthority("ROLE_RESTRICTED");
		memberService.joinMemberBySNS(blliMemberVO);
		blliMemberVO.setMemberPassword("protected");
		HttpSession session = request.getSession();
		session.setAttribute("blliMemberVO", blliMemberVO);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:authorityCheck.do");
		return mav;
	}

	/**
	 * 카카오톡 로그인 시도 시 sns로 가입한적 있는 회원인지 확인하는 메서드
	  * @Method Name : findMemberBySNSId
	  * @Method 설명 :
	  * @작성일 : 2016. 1. 14.
	  * @작성자 : junyoung 
	  * @param blliMemberVO
	  * @return
	 */
	@RequestMapping("findMemberBySNSId.do")
	@ResponseBody
	public boolean findMemberBySNSId(BlliMemberVO blliMemberVO){
		System.out.println(blliMemberVO);
		boolean result = false;
		if(memberService.findMemberById(blliMemberVO)!=null){
			result = true;
		}else{
			result = false;
		}
		return result;
	}
	
	/**
	 * 아이 및 이메일을 등록하는 메서드
	  * @Method Name : insertBabyInfoForKakaoUser
	  * @Method 설명 :
	  * @작성일 : 2016. 1. 16.
	  * @작성자 : junyoung
	  * @param request
	 */
	@RequestMapping("insertBabyInfo.do")
	public String insertBabyInfo(HttpServletRequest request,BlliMemberVO blliMemberVO){
		ArrayList<BlliBabyVO> list = new ArrayList<BlliBabyVO>();
		if(request.getParameter("firstBabySex")!=null){
			BlliBabyVO blliBabyVO = new BlliBabyVO();
			blliBabyVO.setMemberId(request.getParameter("memberId"));
			blliBabyVO.setBabyName(request.getParameter("firstBabyName"));
			blliBabyVO.setBabyBirthday(request.getParameter("firstBabyBirthday"));
			blliBabyVO.setBabySex(request.getParameter("firstBabySex"));
			list.add(blliBabyVO);
		}
		if(request.getParameter("secondBabySex")!=null){
			BlliBabyVO blliBabyVO = new BlliBabyVO();
			blliBabyVO.setMemberId(request.getParameter("memberId"));
			blliBabyVO.setBabyName(request.getParameter("secondBabyName"));
			blliBabyVO.setBabyBirthday(request.getParameter("secondBabyBirthday"));
			blliBabyVO.setBabySex(request.getParameter("secondBabySex"));
			list.add(blliBabyVO);
		}
		if(request.getParameter("thirdBabySex")!=null){
			BlliBabyVO blliBabyVO = new BlliBabyVO();
			blliBabyVO.setMemberId(request.getParameter("memberId"));
			blliBabyVO.setBabyName(request.getParameter("thirdBabyName"));
			blliBabyVO.setBabyBirthday(request.getParameter("thirdBabyBirthday"));
			blliBabyVO.setBabySex(request.getParameter("thirdBabySex"));
			list.add(blliBabyVO);
		}
		memberService.insertBabyInfo(list,blliMemberVO);
		return "redirect:member_proceedingToMain.do";
	}
	
}
