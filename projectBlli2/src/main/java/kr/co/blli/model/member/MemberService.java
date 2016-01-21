package kr.co.blli.model.member;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import kr.co.blli.model.vo.BlliBabyVO;
import kr.co.blli.model.vo.BlliMemberVO;

public interface MemberService {

	public void joinMemberByEmail(BlliMemberVO blliMemberVO);

	public BlliMemberVO findMemberById(BlliMemberVO blliMemberVO);

	public void insertBabyInfo(ArrayList<BlliBabyVO> list,BlliMemberVO blliMemberVO);

	public BlliMemberVO selectBlliMemberInfoByMemberId(String memberId);

	public void joinMemberBySNS(BlliMemberVO blliMemberVO);

	public List<BlliBabyVO> selectBabyListByMemberId(String memberId) throws ParseException;

	public void changeRecommendingBaby(BlliBabyVO blliBabyVO);


}
