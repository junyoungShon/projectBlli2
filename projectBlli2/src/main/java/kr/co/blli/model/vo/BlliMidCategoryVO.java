package kr.co.blli.model.vo;

public class BlliMidCategoryVO {
	private String midCategory;
	private String midCategoryInfo;
	private String midCategoryMainPhotoLink;
	private int whenToUseMin;
	private int whenToUseMax;
	private String bigCategory;
	private String midCategoryId;
	private int smallProductCount; // 2016.02.05 추가
	public BlliMidCategoryVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BlliMidCategoryVO(String midCategory, String midCategoryInfo,
			String midCategoryMainPhotoLink, int whenToUseMin,
			int whenToUseMax, String bigCategory, String midCategoryId,
			int smallProductCount) {
		super();
		this.midCategory = midCategory;
		this.midCategoryInfo = midCategoryInfo;
		this.midCategoryMainPhotoLink = midCategoryMainPhotoLink;
		this.whenToUseMin = whenToUseMin;
		this.whenToUseMax = whenToUseMax;
		this.bigCategory = bigCategory;
		this.midCategoryId = midCategoryId;
		this.smallProductCount = smallProductCount;
	}
	public String getMidCategory() {
		return midCategory;
	}
	public void setMidCategory(String midCategory) {
		this.midCategory = midCategory;
	}
	public String getMidCategoryInfo() {
		return midCategoryInfo;
	}
	public void setMidCategoryInfo(String midCategoryInfo) {
		this.midCategoryInfo = midCategoryInfo;
	}
	public String getMidCategoryMainPhotoLink() {
		return midCategoryMainPhotoLink;
	}
	public void setMidCategoryMainPhotoLink(String midCategoryMainPhotoLink) {
		this.midCategoryMainPhotoLink = midCategoryMainPhotoLink;
	}
	public int getWhenToUseMin() {
		return whenToUseMin;
	}
	public void setWhenToUseMin(int whenToUseMin) {
		this.whenToUseMin = whenToUseMin;
	}
	public int getWhenToUseMax() {
		return whenToUseMax;
	}
	public void setWhenToUseMax(int whenToUseMax) {
		this.whenToUseMax = whenToUseMax;
	}
	public String getBigCategory() {
		return bigCategory;
	}
	public void setBigCategory(String bigCategory) {
		this.bigCategory = bigCategory;
	}
	public String getMidCategoryId() {
		return midCategoryId;
	}
	public void setMidCategoryId(String midCategoryId) {
		this.midCategoryId = midCategoryId;
	}
	public int getSmallProductCount() {
		return smallProductCount;
	}
	public void setSmallProductCount(int smallProductCount) {
		this.smallProductCount = smallProductCount;
	}
	@Override
	public String toString() {
		return "BlliMidCategoryVO [midCategory=" + midCategory
				+ ", midCategoryInfo=" + midCategoryInfo
				+ ", midCategoryMainPhotoLink=" + midCategoryMainPhotoLink
				+ ", whenToUseMin=" + whenToUseMin + ", whenToUseMax="
				+ whenToUseMax + ", bigCategory=" + bigCategory
				+ ", midCategoryId=" + midCategoryId + ", smallProductCount="
				+ smallProductCount + "]";
	}
}