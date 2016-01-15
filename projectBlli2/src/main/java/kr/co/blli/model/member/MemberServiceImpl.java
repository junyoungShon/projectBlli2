package kr.co.blli.model.member;

import javax.annotation.Resource;

import kr.co.blli.model.vo.BlliBabyVO;
import kr.co.blli.model.vo.BlliMemberVO;

import org.springframework.beans.factory.annotation.Autowired;
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
	public void joinMemberByEmail(BlliMemberVO blliMemberVO,BlliBabyVO blliBabyVO) {
		blliMemberVO.setMemberEmail(blliMemberVO.getMemberId());
		blliMemberVO.setRecommendingBabyName(blliBabyVO.getBabyName());
		blliMemberVO.setAuthority("ROLE_USER");
		blliMemberVO.setMemberPassword(passwordEncoder.encode(blliMemberVO.getMemberPassword()));
		memberDAO.insertMemberInfo(blliMemberVO);
		memberDAO.insertChildInfo(blliBabyVO);
	}
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
}
