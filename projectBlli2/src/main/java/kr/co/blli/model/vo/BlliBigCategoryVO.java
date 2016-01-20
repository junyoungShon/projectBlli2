package kr.co.blli.model.vo;

public class BlliBigCategoryVO {
	private String bigCategory;
	private String categoryId;
	public BlliBigCategoryVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BlliBigCategoryVO(String bigCategory, String categoryId) {
		super();
		this.bigCategory = bigCategory;
		this.categoryId = categoryId;
	}
	public String getBigCategory() {
		return bigCategory;
	}
	public void setBigCategory(String bigCategory) {
		this.bigCategory = bigCategory;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	@Override
	public String toString() {
		return "BlliBigCategoryVO [bigCategory=" + bigCategory
				+ ", categoryId=" + categoryId + "]";
	}
}