package kr.co.blli.model.vo;

public class BlliNotRecommMidCategoryVO {
	String memberId;
	String midCategory;
	String categoryId;
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

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	@Override
	public String toString() {
		return "BlliNotRecommMidCategoryVO [memberId=" + memberId
				+ ", midCategory=" + midCategory + ", categoryId=" + categoryId
				+ "]";
	}
	
}
