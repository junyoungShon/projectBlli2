package kr.co.blli.model.vo;

import java.util.List;

public class BlliMemberVO{
	private String memberId;
	private String memberEmail;
	private String memberPassword;
	private String memberName;
	private int memberState;
	private String authority;
	//blliMember테이블의 1:N 관계 테이블 VO 추가 - 자동로그인 테이블 제외
	private List<BlliBabyVO> blliBabyVOList ;
	private List<BlliScheduleVO> blliScheduleVOList;
	private List<BlliMidCategoryVO> blliRecommendingMidCategoryVOList;
	
	
	public BlliMemberVO() {
		super();
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}


	public String getMemberEmail() {
		return memberEmail;
	}


	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}


	public String getMemberPassword() {
		return memberPassword;
	}


	public void setMemberPassword(String memberPassword) {
		this.memberPassword = memberPassword;
	}


	public String getMemberName() {
		return memberName;
	}


	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}


	public int getMemberState() {
		return memberState;
	}


	public void setMemberState(int memberState) {
		this.memberState = memberState;
	}


	public String getAuthority() {
		return authority;
	}


	public void setAuthority(String authority) {
		this.authority = authority;
	}


	public List<BlliBabyVO> getBlliBabyVOList() {
		return blliBabyVOList;
	}


	public void setBlliBabyVOList(List<BlliBabyVO> blliBabyVOList) {
		this.blliBabyVOList = blliBabyVOList;
	}


	public List<BlliScheduleVO> getBlliScheduleVOList() {
		return blliScheduleVOList;
	}


	public void setBlliScheduleVOList(List<BlliScheduleVO> blliScheduleVOList) {
		this.blliScheduleVOList = blliScheduleVOList;
	}


	public List<BlliMidCategoryVO> getBlliRecommendingMidCategoryVOList() {
		return blliRecommendingMidCategoryVOList;
	}


	public void setBlliRecommendingMidCategoryVOList(
			List<BlliMidCategoryVO> blliRecommendingMidCategoryVOList) {
		this.blliRecommendingMidCategoryVOList = blliRecommendingMidCategoryVOList;
	}


	@Override
	public String toString() {
		return "BlliMemberVO [memberId=" + memberId + ", memberEmail="
				+ memberEmail + ", memberPassword=" + memberPassword
				+ ", memberName=" + memberName + ", memberState=" + memberState
				+ ", authority=" + authority + ", blliBabyVOList="
				+ blliBabyVOList + ", blliScheduleVOList=" + blliScheduleVOList
				+ ", blliRecommendingMidCategoryVOList="
				+ blliRecommendingMidCategoryVOList + "]";
	}
	
}
