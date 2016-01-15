package kr.co.blli.model.member;

import kr.co.blli.model.vo.BlliBabyVO;
import kr.co.blli.model.vo.BlliMemberVO;

public interface MemberService {

	public void joinMemberByEmail(BlliMemberVO blliMemberVO, BlliBabyVO blliBabyVO);

	public BlliMemberVO findMemberById(BlliMemberVO blliMemberVO);

}
