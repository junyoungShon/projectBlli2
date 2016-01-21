package kr.co.blli.model.vo;

public class BlliBigCategoryVO {
	private String bigCategory;
	private String bigCategoryId;
	public BlliBigCategoryVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BlliBigCategoryVO(String bigCategory, String bigCategoryId) {
		super();
		this.bigCategory = bigCategory;
		this.bigCategoryId = bigCategoryId;
	}
	public String getBigCategory() {
		return bigCategory;
	}
	public void setBigCategory(String bigCategory) {
		this.bigCategory = bigCategory;
	}
	public String getBigCategoryId() {
		return bigCategoryId;
	}
	public void setBigCategoryId(String bigCategoryId) {
		this.bigCategoryId = bigCategoryId;
	}
	@Override
	public String toString() {
		return "BlliBigCategoryVO [bigCategory=" + bigCategory
				+ ", bigCategoryId=" + bigCategoryId + "]";
	}
}