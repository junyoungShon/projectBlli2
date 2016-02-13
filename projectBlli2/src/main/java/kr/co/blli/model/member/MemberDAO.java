package kr.co.blli.model.member;

import java.util.List;

import kr.co.blli.model.vo.BlliBabyVO;
import kr.co.blli.model.vo.BlliMailVO;
import kr.co.blli.model.vo.BlliMemberVO;

public interface MemberDAO {

	public BlliMemberVO findMemberByIdForLogin(String memberId);

	public void insertMemberInfo(BlliMemberVO blliMemberVO);

	public void updateMemberEmail(BlliMemberVO blliMemberVO);

	public void insertBabyInfo(BlliBabyVO blliBabyVO);

	public void updateMemberAuthority(BlliMemberVO blliMemberVO);

	public BlliMemberVO selectBlliMemberInfoByMemberId(String memberId);

	public List<BlliBabyVO> selectBabyListByMemberId(String memberId);

	public void changeRecommendingBaby(BlliBabyVO blliBabyVO);

	
	//용호 메소드 영역
	public BlliMemberVO findMemberInfoById(String memberId);
	public BlliMailVO findMailSubjectAndContentByMailForm(String mailForm);
	public List<BlliMemberVO> getMemberHavingBabyAgeChangedList();
	public List<BlliBabyVO> getBabyAgeChangedListOfMember(String memberId);
	public void updateMemberInfoByEmail(BlliMemberVO blliMemberVO);
	public void deleteBabyInfo(BlliMemberVO blliMemberVO);
	public String findMemberNameByEmail(String memberEmail);
	public void updateMemberPasswordToTemporaryPassword(BlliMemberVO blliMemberVO);

}
