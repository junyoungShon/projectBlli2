package kr.co.blli.model.member;

import java.util.ArrayList;

import javax.annotation.Resource;

import kr.co.blli.model.security.BlliUserDetails;
import kr.co.blli.model.vo.BlliBabyVO;
import kr.co.blli.model.vo.BlliMemberVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {
	
	@Resource
	private MemberDAO memberDAO ;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	/**
	 * 
	  * @Method Name : joinMemberByEmail
	  * @Method 설명 : 이메일을 통해 가입하는 회원들의 권한 부여 및 패스워드 암호화 등을 진행
	  * @작성일 : 2016. 1. 14.
	  * @작성자 : junyoung
	  * @param blliMemberVO
	  * @param blliChildVO
	 */
	@Override
	public void joinMemberByEmail(BlliMemberVO blliMemberVO) {
		blliMemberVO.setMemberEmail(blliMemberVO.getMemberId());
		blliMemberVO.setAuthority("ROLE_RESTRICTED_EMAIL");
		blliMemberVO.setMemberPassword(passwordEncoder.encode(blliMemberVO.getMemberPassword()));
		memberDAO.insertMemberInfo(blliMemberVO);
		//회원 가입 직후 권한을 부여하여 로그인을 시킨 뒤 페이지 이동 시켜줌
		BlliUserDetails blliUserDetails = new BlliUserDetails
				(blliMemberVO.getMemberId(), blliMemberVO.getMemberPassword(), blliMemberVO.getAuthority());
		Authentication authentication = new UsernamePasswordAuthenticationToken(blliUserDetails, null,blliUserDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
	/**
	  * @Method Name : joinMemberByKakao
	  * @Method 설명 :카카오톡 통해 가입하는 회원들의 권한 부여 및 패스워드 암호화 등을 진행
	  * @작성일 : 2016. 1. 16.
	  * @작성자 : junyoung
	  * @param blliMemberVO
	 */
	@Override
	public void joinMemberByKakao(BlliMemberVO blliMemberVO) {
		blliMemberVO.setMemberEmail(blliMemberVO.getMemberId());
		blliMemberVO.setMemberId("kakao_"+blliMemberVO.getMemberId());
		blliMemberVO.setAuthority("ROLE_RESTRICTED_KAKAO");
		blliMemberVO.setMemberPassword(passwordEncoder.encode("kakaoMember"));
		memberDAO.insertMemberInfo(blliMemberVO);
		//회원 가입 직후 권한을 부여하여 로그인을 시킨 뒤 페이지 이동 시켜줌
		BlliUserDetails blliUserDetails = new BlliUserDetails
				(blliMemberVO.getMemberId(), blliMemberVO.getMemberPassword(), blliMemberVO.getAuthority());
		Authentication authentication = new UsernamePasswordAuthenticationToken(blliUserDetails, null,blliUserDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
	/**
	  * @Method Name : joinMemberByNaver
	  * @Method 설명 :네이버를 통해 가입하는 회원들의 권한 부여 및 패스워드 암호화 등을 진행
	  * @작성일 : 2016. 1. 16.
	  * @작성자 : junyoung
	  * @param blliMemberVO
	 */
	@Override
	public void joinMemberByNaver(BlliMemberVO blliMemberVO) {
		String memberId = blliMemberVO.getMemberId();
		//사용자 이름이 없으므로 이메일 아이디로 이름 대체
		String memberName = memberId.substring(0, memberId.lastIndexOf("@"));;
		blliMemberVO.setMemberName(memberName);
		blliMemberVO.setMemberEmail(blliMemberVO.getMemberId());
		blliMemberVO.setMemberId("naver_"+blliMemberVO.getMemberId());
		blliMemberVO.setAuthority("ROLE_RESTRICTED_NAVER");
		blliMemberVO.setMemberPassword(passwordEncoder.encode("naverMember"));
		memberDAO.insertMemberInfo(blliMemberVO);
		//회원 가입 직후 권한을 부여하여 로그인을 시킨 뒤 페이지 이동 시켜줌
		BlliUserDetails blliUserDetails = new BlliUserDetails
				(blliMemberVO.getMemberId(), blliMemberVO.getMemberPassword(), blliMemberVO.getAuthority());
		Authentication authentication = new UsernamePasswordAuthenticationToken(blliUserDetails, null,blliUserDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	};
	
	/**
	 * 
	  * @Method Name : findMemberById
	  * @Method 설명 : 멤버아이디를 기준으로 회원이 있는지 없는지 판단해주는 메서드
	  * @작성일 : 2016. 1. 14.
	  * @작성자 : junyoung
	  * @param blliMemberVO
	 */
	@Override
	public BlliMemberVO findMemberById(BlliMemberVO blliMemberVO) {
		return memberDAO.findMemberByIdForLogin(blliMemberVO.getMemberId());
	}
	/**
	  * @Method Name : insertBabyInfoForKakaoUser
	  * @Method 설명 : 카카오톡 가입자의 아이정보와 이메일을 입력해주는 서비스 메서드
	  * @작성일 : 2016. 1. 16.
	  * @작성자 : junyoung
	  * @param list
	  * @param memberEmail
	 */
	@Override
	public void insertBabyInfoForKakaoUser(ArrayList<BlliBabyVO> list,
			BlliMemberVO blliMemberVO) {
		System.out.println(blliMemberVO);
		 memberDAO.updateMemberEmail(blliMemberVO);
		 blliMemberVO.setAuthority("ROLE_USER_KAKAO");
		 memberDAO.updateMemberAuthority(blliMemberVO);
		 for(int i=0;i<list.size();i++){
			 memberDAO.insertBabyInfo(list.get(i));
		 }
		//아이정보 입력 후 권한을 갱신하여 로그인을 시킨 뒤 페이지 이동 시켜줌
		BlliUserDetails blliUserDetails = new BlliUserDetails
				(blliMemberVO.getMemberId(), blliMemberVO.getMemberPassword(), blliMemberVO.getAuthority());
		Authentication authentication = new UsernamePasswordAuthenticationToken(blliUserDetails, null,blliUserDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
	/**
	  * @Method Name : insertBabyInfoForEmailUser
	  * @Method 설명 : 이메일 가입자의 아이정보와 이메일을 입력해주는 서비스 메서드
	  * @작성일 : 2016. 1. 16.
	  * @작성자 : junyoung
	  * @param list
	 */
	@Override
	public void insertBabyInfoForEmailUser(ArrayList<BlliBabyVO> list) {
		for(int i=0;i<list.size();i++){
			 memberDAO.insertBabyInfo(list.get(i));
			 System.out.println(list.size());
		 }
		BlliMemberVO blliMemberVO = new BlliMemberVO();
		blliMemberVO.setAuthority("ROLE_USER_EMAIL");
		blliMemberVO.setMemberId(list.get(0).getMemberId());
		memberDAO.updateMemberAuthority(blliMemberVO);
		//아이정보 입력 후 권한을 갱신하여 로그인을 시킨 뒤 페이지 이동 시켜줌
		BlliUserDetails blliUserDetails = new BlliUserDetails
			(blliMemberVO.getMemberId(), blliMemberVO.getMemberPassword(), blliMemberVO.getAuthority());
		Authentication authentication = new UsernamePasswordAuthenticationToken(blliUserDetails, null,blliUserDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
	/**
	 * 
	  * @Method Name : selectBlliMemberInfoByMemberId
	  * @Method 설명 : 사용자가 메인에 접근할 때 필요로하는 정보들을 담는 메서드
	  * @작성일 : 2016. 1. 18.
	  * @작성자 : junyoung
	  * @param memberId
	  * @return
	 */
	@Override
	public BlliMemberVO selectBlliMemberInfoByMemberId(String memberId) {
		//1. 비밀번호를 제외한 사용자의 모든 정보
		BlliMemberVO blliMemberVO= memberDAO.selectBlliMemberInfoByMemberId(memberId);
		//2. 사용자의 아이정보
		blliMemberVO.setBabyList(memberDAO.selectBabyListByMemberId(memberId));
		return blliMemberVO;
	}
}
