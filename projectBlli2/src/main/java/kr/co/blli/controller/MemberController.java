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
		System.out.println("요기오냐");
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
	@RequestMapping("loginByKakaoId.do")
	public String loginByKakaoId(HttpServletRequest request,BlliMemberVO blliMemberVO){
		UserDetails userInfo = (UserDetails) blliUserDetailsService.loadUserByUsername(blliMemberVO.getMemberId());
		Authentication authentication = new UsernamePasswordAuthenticationToken(userInfo, "kakaoMember",userInfo.getAuthorities());
		SecurityContext securityContext = SecurityContextHolder.getContext();
		securityContext.setAuthentication(authentication);
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
	  * @Method Name : joinMemberByKakaoId
	  * @Method 설명 : 카카오톡을 통한 회원가입
	  * @작성일 : 2016. 1. 16.
	  * @작성자 : junyoung
	  * @param blliMemberVO
	  * @return
	 */
	@RequestMapping("joinMemberByKakaoId.do")
	public ModelAndView joinMemberByKakaoId(HttpServletRequest request, BlliMemberVO blliMemberVO){
		
		memberService.joinMemberByKakao(blliMemberVO);
		blliMemberVO.setMemberPassword("protected");
		HttpSession session = request.getSession();
		session.setAttribute("blliMemberVO", blliMemberVO);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:authorityCheck.do");
		return mav;
	}
	
	/**
	  * @Method Name : joinMemberByNaverMail
	  * @Method 설명 : 네이버 아이디로 회원가입
	  * @작성일 : 2016. 1. 18.
	  * @작성자 : junyoung
	  * @param request
	  * @param blliMemberVO
	  * @return
	 */
	@RequestMapping("joinMemberByNaverMail.do")
	public ModelAndView joinMemberByNaverMail(HttpServletRequest request, BlliMemberVO blliMemberVO){
		
		memberService.joinMemberByNaver(blliMemberVO);
		blliMemberVO.setMemberPassword("protected");
		HttpSession session = request.getSession();
		session.setAttribute("blliMemberVO", blliMemberVO);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:authorityCheck.do");
		return mav;
	}
	/**
	 * 카카오톡 로그인 시도 시 카카오톡으로 가입한적 있는 회원인지 확인하는 메서드
	  * @Method Name : findMemberByKakaoId
	  * @Method 설명 :
	  * @작성일 : 2016. 1. 14.
	  * @작성자 : junyoung 
	  * @param blliMemberVO
	  * @return
	 */
	@RequestMapping("findMemberByKakaoId.do")
	@ResponseBody
	public boolean findMemberByKakaoId(BlliMemberVO blliMemberVO){
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
	 * 카카오톡의 유저의 이메일과 아이를 함께 등록하는 메서드
	  * @Method Name : insertBabyInfoForKakaoUser
	  * @Method 설명 :
	  * @작성일 : 2016. 1. 16.
	  * @작성자 : junyoung
	  * @param request
	 */
	@RequestMapping("insertBabyInfoForKakaoUser.do")
	public String insertBabyInfoForKakaoUser(HttpServletRequest request,BlliMemberVO blliMemberVO){
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
		memberService.insertBabyInfoForKakaoUser(list,blliMemberVO);
		return "redirect:member_proceedingToMain.do";
	}
	/**
	 * 
	  * @Method Name : insertBabyInfoForEmailUser
	  * @Method 설명 :이메일 유저의 이메일과 아이를 함께 등록하는 메서드
	  * @작성일 : 2016. 1. 16.
	  * @작성자 : junyoung
	  * @param request
	 */
	@RequestMapping("insertBabyInfoForEmailUser.do")
	public String insertBabyInfoForEmailUser(HttpServletRequest request){
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
		memberService.insertBabyInfoForEmailUser(list);
		return "redirect:member_proceedingToMain.do";
	}
}
