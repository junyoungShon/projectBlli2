package kr.co.blli.model.vo;

public class BlliSmallProductVO {
	private String smallProduct;
	private String midCategory;
	private String midCategoryId;
	private String smallProductMaker;
	private int smallProductWhenToUseMin;
	private int smallProductWhenToUseMax;
	private int smallProductDibsCount;
	private String smallProductMainPhotoLink;
	private int smallProductScore;
	private String naverShoppingLink;
	private int naverShoppingRank;
	private int smallProductPostingCount;
	private String productRegisterDay;
	private String smallProductId;
	//16.01.22 추가
	private int isDib;
	
	public BlliSmallProductVO() {
		super();
	}
	
	public BlliSmallProductVO(String smallProduct, String midCategory,
			String midCategoryId, String smallProductMaker,
			int smallProductWhenToUseMin, int smallProductWhenToUseMax,
			int smallProductDibsCount, String smallProductMainPhotoLink,
			int smallProductScore, String naverShoppingLink,
			int naverShoppingRank, int smallProductPostingCount,
			String productRegisterDay, String smallProductId, int isDib) {
		super();
		this.smallProduct = smallProduct;
		this.midCategory = midCategory;
		this.midCategoryId = midCategoryId;
		this.smallProductMaker = smallProductMaker;
		this.smallProductWhenToUseMin = smallProductWhenToUseMin;
		this.smallProductWhenToUseMax = smallProductWhenToUseMax;
		this.smallProductDibsCount = smallProductDibsCount;
		this.smallProductMainPhotoLink = smallProductMainPhotoLink;
		this.smallProductScore = smallProductScore;
		this.naverShoppingLink = naverShoppingLink;
		this.naverShoppingRank = naverShoppingRank;
		this.smallProductPostingCount = smallProductPostingCount;
		this.productRegisterDay = productRegisterDay;
		this.smallProductId = smallProductId;
		this.isDib = isDib;
	}

	public String getSmallProduct() {
		return smallProduct;
	}
	public void setSmallProduct(String smallProduct) {
		this.smallProduct = smallProduct;
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
	public String getSmallProductMaker() {
		return smallProductMaker;
	}
	public void setSmallProductMaker(String smallProductMaker) {
		this.smallProductMaker = smallProductMaker;
	}
	public int getSmallProductWhenToUseMin() {
		return smallProductWhenToUseMin;
	}
	public void setSmallProductWhenToUseMin(int smallProductWhenToUseMin) {
		this.smallProductWhenToUseMin = smallProductWhenToUseMin;
	}
	public int getSmallProductWhenToUseMax() {
		return smallProductWhenToUseMax;
	}
	public void setSmallProductWhenToUseMax(int smallProductWhenToUseMax) {
		this.smallProductWhenToUseMax = smallProductWhenToUseMax;
	}
	public int getSmallProductDibsCount() {
		return smallProductDibsCount;
	}
	public void setSmallProductDibsCount(int smallProductDibsCount) {
		this.smallProductDibsCount = smallProductDibsCount;
	}
	public String getSmallProductMainPhotoLink() {
		return smallProductMainPhotoLink;
	}
	public void setSmallProductMainPhotoLink(String smallProductMainPhotoLink) {
		this.smallProductMainPhotoLink = smallProductMainPhotoLink;
	}
	public int getSmallProductScore() {
		return smallProductScore;
	}
	public void setSmallProductScore(int smallProductScore) {
		this.smallProductScore = smallProductScore;
	}
	public String getNaverShoppingLink() {
		return naverShoppingLink;
	}
	public void setNaverShoppingLink(String naverShoppingLink) {
		this.naverShoppingLink = naverShoppingLink;
	}
	public int getNaverShoppingRank() {
		return naverShoppingRank;
	}
	public void setNaverShoppingRank(int naverShoppingRank) {
		this.naverShoppingRank = naverShoppingRank;
	}
	public int getSmallProductPostingCount() {
		return smallProductPostingCount;
	}
	public void setSmallProductPostingCount(int smallProductPostingCount) {
		this.smallProductPostingCount = smallProductPostingCount;
	}
	public String getProductRegisterDay() {
		return productRegisterDay;
	}
	public void setProductRegisterDay(String productRegisterDay) {
		this.productRegisterDay = productRegisterDay;
	}
	public String getSmallProductId() {
		return smallProductId;
	}
	public void setSmallProductId(String smallProductId) {
		this.smallProductId = smallProductId;
	}
	
	public int getIsDib() {
		return isDib;
	}
	public void setIsDib(int isDib) {
		this.isDib = isDib;
	}
	@Override
	public String toString() {
		return "BlliSmallProductVO [smallProduct=" + smallProduct
				+ ", midCategory=" + midCategory + ", midCategoryId="
				+ midCategoryId + ", smallProductMaker=" + smallProductMaker
				+ ", smallProductWhenToUseMin=" + smallProductWhenToUseMin
				+ ", smallProductWhenToUseMax=" + smallProductWhenToUseMax
				+ ", smallProductDibsCount=" + smallProductDibsCount
				+ ", smallProductMainPhotoLink=" + smallProductMainPhotoLink
				+ ", smallProductScore=" + smallProductScore
				+ ", naverShoppingLink=" + naverShoppingLink
				+ ", naverShoppingRank=" + naverShoppingRank
				+ ", smallProductPostingCount=" + smallProductPostingCount
				+ ", productRegisterDay=" + productRegisterDay
				+ ", smallProductId=" + smallProductId + ", isDib=" + isDib
				+ "]";
	}
}