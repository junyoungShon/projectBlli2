package kr.co.blli.model.vo;

import java.util.List;

public class BlliMemberVO{
	private String memberId;
	private String memberEmail;
	private String memberPassword;
	private String memberName;
	private int memberState;
	private String recommendingBabyName;
	private String authority;
	private List<BlliBabyVO> babyList ;
	public BlliMemberVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BlliMemberVO(String memberId, String memberEmail,
			String memberPassword, String memberName, int memberState,
			String recommendingBabyName, String authority,
			List<BlliBabyVO> babyList) {
		super();
		this.memberId = memberId;
		this.memberEmail = memberEmail;
		this.memberPassword = memberPassword;
		this.memberName = memberName;
		this.memberState = memberState;
		this.recommendingBabyName = recommendingBabyName;
		this.authority = authority;
		this.babyList = babyList;
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
	public String getRecommendingBabyName() {
		return recommendingBabyName;
	}
	public void setRecommendingBabyName(String recommendingBabyName) {
		this.recommendingBabyName = recommendingBabyName;
	}
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	public List<BlliBabyVO> getBabyList() {
		return babyList;
	}
	public void setBabyList(List<BlliBabyVO> babyList) {
		this.babyList = babyList;
	}
	@Override
	public String toString() {
		return "BlliMemberVO [memberId=" + memberId + ", memberEmail="
				+ memberEmail + ", memberPassword=" + memberPassword
				+ ", memberName=" + memberName + ", memberState=" + memberState
				+ ", recommendingBabyName=" + recommendingBabyName
				+ ", authority=" + authority + ", babyList=" + babyList + "]";
	}
}
