package kr.co.blli.model.member;

import java.util.List;

import kr.co.blli.model.vo.BlliBabyVO;
import kr.co.blli.model.vo.BlliMemberVO;

public interface MemberDAO {

	public BlliMemberVO findMemberByIdForLogin(String memberId);

	public void insertMemberInfo(BlliMemberVO blliMemberVO);

	public void updateMemberEmail(BlliMemberVO blliMemberVO);

	public void insertBabyInfo(BlliBabyVO blliBabyVO);

	public void updateMemberAuthority(BlliMemberVO blliMemberVO);

	public BlliMemberVO selectBlliMemberInfoByMemberId(String memberId);

	public List<BlliBabyVO> selectBabyListByMemberId(String memberId);

	public void updateRecommendingBabyName(BlliMemberVO blliMemberVO);

}
