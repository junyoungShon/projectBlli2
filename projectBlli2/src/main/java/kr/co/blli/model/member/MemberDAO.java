package kr.co.blli.model.member;

import kr.co.blli.model.vo.BlliBabyVO;
import kr.co.blli.model.vo.BlliMemberVO;

public interface MemberDAO {

	public BlliMemberVO findMemberByIdForLogin(String memberId);

	public void insertMemberInfo(BlliMemberVO blliMemberVO);
	public void insertChildInfo(BlliBabyVO blliChildVO);

}
