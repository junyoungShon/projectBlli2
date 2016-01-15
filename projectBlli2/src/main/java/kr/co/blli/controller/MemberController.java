package kr.co.blli.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import kr.co.blli.model.member.MemberService;
import kr.co.blli.model.product.ProductService;
import kr.co.blli.model.vo.BlliBabyVO;
import kr.co.blli.model.vo.BlliMemberVO;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
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
		mav.setViewName("/blli/main");
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
		BlliMemberVO blliMemberVO = new BlliMemberVO();
		blliMemberVO.setMemberId(auth.getName());
		session.setAttribute("blliMemberVO", blliMemberVO);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/blli/main");
		return mav;
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
	public ModelAndView joinMemberByEmail(BlliMemberVO blliMemberVO,BlliBabyVO blliChildVO){
		System.out.println(blliMemberVO);
		System.out.println(blliChildVO);
		//memberService.joinMemberByEmail(blliMemberVO,blliChildVO);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:member_goMain.do");
		return mav;
	}
	
	@RequestMapping("goInsertBabyInfoByEmail.do")
	public ModelAndView goInsertBabyInfoByEmail(BlliMemberVO blliMemberVO){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("memberjoin/insertBabyInfoByEmail");
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
		blliMemberVO.setMemberId("kakao_"+blliMemberVO.getMemberId());
		if(memberService.findMemberById(blliMemberVO)!=null){
			result = true;
		}else{
			result = false;
		}
		return result;
	}
	
	
	
}
