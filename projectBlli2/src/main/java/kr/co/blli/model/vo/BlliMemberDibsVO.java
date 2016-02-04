package kr.co.blli.model.vo;

public class BlliMemberDibsVO {
	private String memberId;
	private String smallProductId;
	private String dipsDate;
	public BlliMemberDibsVO() {
		super();
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getSmallProductId() {
		return smallProductId;
	}
	public void setSmallProductId(String smallProductId) {
		this.smallProductId = smallProductId;
	}
	public String getDipsDate() {
		return dipsDate;
	}
	public void setDipsDate(String dipsDate) {
		this.dipsDate = dipsDate;
	}
	@Override
	public String toString() {
		return "BlliMemberDibs [memberId=" + memberId + ", smallProductId="
				+ smallProductId + ", dipsDate=" + dipsDate + "]";
	}
	
}
