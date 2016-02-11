package kr.co.blli.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.blli.model.member.MemberService;
import kr.co.blli.model.product.ProductService;
import kr.co.blli.model.security.BlliUserDetailsService;
import kr.co.blli.model.vo.BlliBabyVO;
import kr.co.blli.model.vo.BlliMemberDibsVO;
import kr.co.blli.model.vo.BlliMemberScrapeVO;
import kr.co.blli.model.vo.BlliMemberVO;
import kr.co.blli.model.vo.BlliMidCategoryVO;
import kr.co.blli.model.vo.BlliNotRecommMidCategoryVO;
import kr.co.blli.model.vo.BlliPostingDisLikeVO;
import kr.co.blli.model.vo.BlliPostingLikeVO;
import kr.co.blli.model.vo.BlliPostingVO;
import kr.co.blli.model.vo.BlliSmallProductVO;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
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
	@RequestMapping("loginPage.do")
	public ModelAndView goLoginPage(Boolean loginFail){
		ModelAndView mav = new ModelAndView();
		if(loginFail!=null){
			if(loginFail == true){
				mav.addObject("loginFail", "true");
			}
		}
		mav.setViewName("loginPage");
		return mav;
	}
	/**
	  * @Method Name : proceedingToMain
	  * @Method 설명 : 로그인 혹은 회원 가입 후 메인으로 진입하기 위한 중간페이지로서 세션 처리를 담당한다.
	  * @작성일 : 2016. 1. 14.
	  * @작성자 : junyoung
	  * @return
	 */
	@RequestMapping("member_proceedingToMain.do")
	public ModelAndView proceedingToMain(HttpServletRequest request){
		HttpSession session =  request.getSession();
		SecurityContext ctx=(SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
		Authentication auth=ctx.getAuthentication();
		//메인 페이지로 이동하며 세션에 blliMemberVO객체를 담아준다.
		//Query : member_id,member_email,member_name,member_state,authority,recommending_baby_name
		BlliMemberVO blliMemberVO = memberService.selectBlliMemberInfoByMemberId(auth.getName());
		ModelAndView mav = new ModelAndView();
		session.setAttribute("blliMemberVO", blliMemberVO);
		mav.setViewName("redirect:member_goMain.do");
		return mav;
	}
	/**
	  * @Method Name : goMainPage
	  * @Method 설명 : 메인 페이지로 이동하는 메서드로서 스프링 시큐리티가 세션에 담은 memberEmail을 key값으로 하여 메인페이지에 필요한 정보를
	  * 세션에 담는다. 메인페이지 진입 시 담겨 야할 내용 <아이 리스트 , 추천받을  아이의 추천상품-제외상품 제외, 현재 추천 받는 중분류 상품 들 각각 찜하기 상위 2개 소제품, 찜하기 상위 10개의 소제품 중 고득점 포스팅 각 2개 씩 총 20개 , 알림 갯수>
	  * @작성일 : 2016. 1. 13.
	  * @작성자 : junyoung
	  * @return
	 * @throws ParseException 
	 */
	@RequestMapping("member_goMain.do")
	public ModelAndView goMainPage(HttpServletRequest request) throws ParseException{
		HttpSession session =  request.getSession();
		BlliMemberVO blliMemberVO = (BlliMemberVO) session.getAttribute("blliMemberVO");
		ModelAndView mav = new ModelAndView();
		if(blliMemberVO!=null){
			//메인페이지로 이동할 때 회원이 가진 아이리스트를 전달 받는다.
			List <BlliBabyVO> blliBabyVOList=memberService.selectBabyListByMemberId(blliMemberVO.getMemberId());
			blliMemberVO.setBlliBabyVOList(blliBabyVOList);
			BlliBabyVO blliBabyVO = null;
			//추천 받을 아이 추출
			for(int i=0;i<blliBabyVOList.size();i++){
				if(blliBabyVOList.get(i).getRecommending()==1){
					blliBabyVO = blliBabyVOList.get(i);
				}
			}
			//메인페이지로 이동할 때 회원에게 추천될 상품 리스트를 전달받는다.
			List<BlliMidCategoryVO> blliMidCategoryVOList = productService.selectRecommendingMidCategory(blliBabyVO);
			
			//메인페이지로 이동할 때 회원에게 추천 될 소분류 상품 리스트를 전달 받는다.(또래엄마가 많이 찜한 상품)
			List<BlliSmallProductVO> blliSmallProductVOList = productService.selectSameAgeMomBestPickedSmallProductList(blliMidCategoryVOList,blliBabyVO);
			
			//메인페이지로 이동할 때 회원에게 추천 될 소분류 상품과 관련 된 포스팅을 보여준다.<으아아아 여기있으면 아니되오!!>
			List<BlliPostingVO> blliPostingVOList = productService.selectPostingBySmallProductList(blliSmallProductVOList,blliMemberVO.getMemberId(),"1");
			System.out.println(blliSmallProductVOList);
			for(int i=0;i<blliSmallProductVOList.size();i++){
				System.out.println(i+"소제품 명"+blliSmallProductVOList.get(i).getSmallProduct());
			}
			System.out.println(blliPostingVOList);
			mav.setViewName("home");
			//회원정보 삽입
			mav.addObject("blliMemberVO", blliMemberVO);
			//회원에게 추천될 중분류 상품 리스트 삽입
			mav.addObject("blliMidCategoryVOList", blliMidCategoryVOList);
			//회원에게 추천될 소분류 상품 리스트 삽입
			mav.addObject("blliSmallProductVOList", blliSmallProductVOList);
			//회원에게 추천될 소분류 관련 포스팅 리스트 삽입
			mav.addObject("blliPostingVOList", blliPostingVOList);
		}else{
			session.invalidate();
			mav.setViewName("loginPage");
		}
		
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
		UserDetails userInfo 
		= (UserDetails) blliUserDetailsService.loadUserByUsername(blliMemberVO.getMemberId());
		Authentication authentication = 
				new UsernamePasswordAuthenticationToken(userInfo, "protected",userInfo.getAuthorities());
		SecurityContext securityContext = SecurityContextHolder.getContext();
		securityContext.setAuthentication(authentication);
		blliMemberVO = memberService.findMemberById(blliMemberVO);
		blliMemberVO.setMemberPassword("PROTECTED");
		HttpSession session = request.getSession(true);
		session.setAttribute("blliMemberVO", blliMemberVO);
		// 세션에 spring security context 넣음
		session.setAttribute("SPRING_SECURITY_CONTEXT",securityContext);   
		return "redirect:member_proceedingToMain.do";
	}
	
	/**
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
	public ModelAndView joinMemberByKakaoId
	(HttpServletRequest request, BlliMemberVO blliMemberVO){
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
	 * 카카오톡 로그인 시도 시 sns로 가입한적 있는 회원인지 확인하는 메서드
	 * @Method Name : findMemberBySNSId
	 * @Method 설명 :
	 * @작성일 : 2016. 1. 14.
	 * @작성자 : junyoung 
	 * @param blliMemberVO
	 * @return
	 */
	@RequestMapping("findMemberByEmailId.do")
	@ResponseBody
	public boolean findMemberByEmailId(BlliMemberVO blliMemberVO){
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
	  * @Method Name : insertBabyInfo
	  * @Method 설명 : 아이 및 이메일을 등록하는 메서드
	  * @작성일 : 2016. 1. 16.
	  * @작성자 : junyoung
	  * @param request
	 * @throws Exception 
	 */
	@RequestMapping("insertBabyInfo.do")
	public String insertBabyInfo
	(HttpServletRequest request,BlliMemberVO blliMemberVO) throws Exception{
		memberService.insertBabyInfo(blliMemberVO,request);
		return "redirect:member_proceedingToMain.do";
	}
	/**
	 * @Method Name : insertBabyInfo
	 * @Method 설명 : 아이 및 이메일을 등록하는 메서드
	 * @작성일 : 2016. 1. 16.
	 * @작성자 : junyoung
	 * @param request
	 * @throws Exception 
	 */
	@RequestMapping("updateBabyInfo.do")
	public String updateBabyInfo
	(HttpServletRequest request,BlliMemberVO blliMemberVO) throws Exception{
		memberService.deleteBabyInfo(blliMemberVO);
		memberService.insertBabyInfo(blliMemberVO,request);
		return "redirect:member_proceedingToMain.do";
	}
	
	/**
	  * @Method Name : goModifyMemberInfoPage
	  * @Method 설명 : 멤버의 정보를 수정하는 메서드
	  * @작성일 : 2016. 1. 16.
	  * @작성자 : junyoung
	  * @param request
	 */
	@RequestMapping("goModifyMemberInfoPage.do")
	public ModelAndView goModifyMemberInfoPage
	(HttpServletRequest request,BlliMemberVO blliMemberVO){
		blliMemberVO = (BlliMemberVO) request.getSession().getAttribute("blliMemberVO");
		ModelAndView mav = new ModelAndView();
		mav.addObject("blliMemberVO", blliMemberVO);
		mav.setViewName("modifyMemberInfoPage");
		return mav;
	}
	
	/**
	  * @Method Name : goModifyBabyInfoPage
	  * @Method 설명 : 멤버의 정보를 수정하는 메서드
	  * @작성일 : 2016. 1. 16.
	  * @작성자 : junyoung
	  * @param request
	 */
	@RequestMapping("goModifyBabyInfoPage.do")
	public ModelAndView goModifyBabyInfoPage (HttpServletRequest request,BlliMemberVO blliMemberVO){
		blliMemberVO = (BlliMemberVO) request.getSession().getAttribute("blliMemberVO");
		ModelAndView mav = new ModelAndView();
		mav.addObject("blliMemberVO", blliMemberVO);
		System.out.println(blliMemberVO.getBlliBabyVOList());
		mav.setViewName("modifyBabyInfoPage");
		return mav;
	}
	/**
	  * @Method Name : goModifyMemberInfoPage
	  * @Method 설명 : 멤버의 정보를 수정하는 메서드
	  * @작성일 : 2016. 1. 16.
	  * @작성자 : junyoung
	  * @param request
	 */
	@RequestMapping("updateMemberInfoByEmail.do")
	public String updateMemberInfoByEmail(BlliMemberVO blliMemberVO){
		memberService.updateMemberInfoByEmail(blliMemberVO);
		return "redirect:member_goMain.do";
	}
	
	
	//용호 메소드 작성 영역
	
	@RequestMapping("memberCalendar.do")
	@ResponseBody
	public ModelAndView calendar(BlliMemberVO blliMemberVO){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("member_calender.do");
		boolean result = false;
		if(memberService.findMemberById(blliMemberVO)!=null){
			result = true;
		}else{
			result = false;
		}
		return mv;
	}
	
	
	
	
	/**
	  * @Method Name : 사용자가 추천을 제외한 중분류 상품을 지워준다.
	  * @Method 설명 :
	  * @작성일 : 2016. 1. 20.
	  * @작성자 : junyoung
	  * @param blliNotRecommMidCategoryVO
	 */
	@RequestMapping("deleteRecommendMidCategory.do")
	@ResponseBody
	public void deleteRecommendMidCategory(BlliNotRecommMidCategoryVO blliNotRecommMidCategoryVO){
		System.out.println(blliNotRecommMidCategoryVO);
		productService.deleteRecommendMidCategory(blliNotRecommMidCategoryVO);
	}
	/**
	  * @Method Name : changeRecommendingBaby
	  * @Method 설명 : 메인 페이지에서 현재 추천 받는 대상 아이를 바꾼다.
	  * @작성일 : 2016. 1. 21.
	  * @작성자 : junyoung
	  * @param blliBabyVO
	  * @return
	 */
	@RequestMapping("changeRecommendingBaby.do")
	public String changeRecommendingBaby(BlliBabyVO blliBabyVO){
		//아이 디비의 추천 대상을 바꾼다.
		memberService.changeRecommendingBaby(blliBabyVO);
		//메인으로 이동
		return "redirect:member_goMain.do";
	}
	/**
	  * @Method Name : smallProductDib
	  * @Method 설명 : 소제품을 찜하고 그 결과를 반환해주는 컨트롤러 메서드
	  * @작성일 : 2016. 1. 22.
	  * @작성자 : junyoung
	  * @param blliMemberDibsVO
	  * @return
	 */
	@RequestMapping("smallProductDib.do")
	@ResponseBody
	public int smallProductDib(BlliMemberDibsVO blliMemberDibsVO){
		int result=0;
		result = productService.smallProductDib(blliMemberDibsVO);
		return result;
	}
	/**
	 * @Method Name : postingScrape
	 * @Method 설명 : 블로그 스크랩 버튼에 대해 그 결과를 반환해주는 컨트롤러 메서드
	 * @작성일 : 2016. 1. 22.
	 * @작성자 : junyoung
	 * @param blliMemberDibsVO
	 * @return
	 */
	@RequestMapping("postingScrape.do")
	@ResponseBody
	public int postingScrape(BlliMemberScrapeVO blliMemberScrapVO){
		System.out.println(blliMemberScrapVO);
		int result=0;
		result = productService.postingScrape(blliMemberScrapVO);
		return result;
	}
	/**
	 * @Method Name : postingLike
	 * @Method 설명 : 소제품 관련 포스팅을 좋아요 누르면 그 결과를 반환해주는 컨트롤러 메서드
	 * @작성일 : 2016. 1. 22.
	 * @작성자 : junyoung
	 * @param blliMemberDibsVO
	 * @return
	 */
	@RequestMapping("postingLike.do")
	@ResponseBody
	public int postingLike(BlliPostingLikeVO blliPostingLikeVO){
		int result=0;
		result = productService.postingLike(blliPostingLikeVO);
		return result;
	}
	/**
	 * @Method Name : postingDisLike
	 * @Method 설명 : 소제품을 관련 포스팅을  싫어요 누르면 그 결과를 반환해주는 컨트롤러 메서드
	 * @작성일 : 2016. 1. 22.
	 * @작성자 : junyoung
	 * @param blliMemberDibsVO
	 * @return
	 */
	@RequestMapping("postingDisLike.do")
	@ResponseBody
	public int postingDisLike(BlliPostingDisLikeVO blliPostingDisLikeVO){
		int result=0;
		result = productService.postingDisLike(blliPostingDisLikeVO);
		return result;
	}
	
	@RequestMapping("fileCapacityCheck.do")
	@ResponseBody
	public String upload(MultipartHttpServletRequest request, 
	    HttpServletResponse response) throws IOException {
		String result = "true";
		Iterator<String> itr =  request.getFileNames();
	    MultipartFile mpf = request.getFile(itr.next());
	    if(mpf.getSize()>=200000){
	    	result = "fail";
	    }
	    return result;
	}
	
}
