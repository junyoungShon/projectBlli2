package kr.co.blli.model.member;

import java.util.ArrayList;

import kr.co.blli.model.vo.BlliBabyVO;
import kr.co.blli.model.vo.BlliMemberVO;

public interface MemberService {

	public void joinMemberByEmail(BlliMemberVO blliMemberVO);

	public BlliMemberVO findMemberById(BlliMemberVO blliMemberVO);

	public void joinMemberByKakao(BlliMemberVO blliMemberVO);

	public void insertBabyInfoForKakaoUser(ArrayList<BlliBabyVO> list,
			BlliMemberVO blliMemberVO);

	public void insertBabyInfoForEmailUser(ArrayList<BlliBabyVO> list);

	public BlliMemberVO selectBlliMemberInfoByMemberId(String memberId);

	public void joinMemberByNaver(BlliMemberVO blliMemberVO);

}
