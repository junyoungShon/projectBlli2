package kr.co.blli.model.vo;

public class BlliNotRecommMidCategoryVO {
	String memberId;
	String midCategory;
	String midCategoryId;
	
	public BlliNotRecommMidCategoryVO() {
		super();
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getMidCategory() {
		return midCategory;
	}
	public void setMidCategory(String midCategory) {
		this.midCategory = midCategory;
	}
	public String getMidCategoryId() {
		return midCategoryId;
	}
	public void setMidCategoryId(String midCategoryId) {
		this.midCategoryId = midCategoryId;
	}
	@Override
	public String toString() {
		return "BlliNotRecommMidCategoryVO [memberId=" + memberId
				+ ", midCategory=" + midCategory + ", midCategoryId="
				+ midCategoryId + "]";
	}
	
}
