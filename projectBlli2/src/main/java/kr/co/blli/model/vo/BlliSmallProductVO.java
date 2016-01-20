package kr.co.blli.model.vo;

public class BlliSmallProductVO {
	private String smallProduct;
	private String midCategory;
	private String smallProductMaker;
	private int smallProductWhenToUseMin;
	private int smallProductWhenToUseMax;
	private int smallProductDibsCount;
	private String smallProductMainPhotoLink;
	private int smallProductScore;
	private String naverShoppingLink;
	private int naverShoppingOrder;
	private int smallProductPostingCount;
	private String productRegisterDay;
	public BlliSmallProductVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BlliSmallProductVO(String smallProduct, String midCategory,
			String smallProductMaker, int smallProductWhenToUseMin,
			int smallProductWhenToUseMax, int smallProductDibsCount,
			String smallProductMainPhotoLink, int smallProductScore,
			String naverShoppingLink, int naverShoppingOrder,
			int smallProductPostingCount, String productRegisterDay) {
		super();
		this.smallProduct = smallProduct;
		this.midCategory = midCategory;
		this.smallProductMaker = smallProductMaker;
		this.smallProductWhenToUseMin = smallProductWhenToUseMin;
		this.smallProductWhenToUseMax = smallProductWhenToUseMax;
		this.smallProductDibsCount = smallProductDibsCount;
		this.smallProductMainPhotoLink = smallProductMainPhotoLink;
		this.smallProductScore = smallProductScore;
		this.naverShoppingLink = naverShoppingLink;
		this.naverShoppingOrder = naverShoppingOrder;
		this.smallProductPostingCount = smallProductPostingCount;
		this.productRegisterDay = productRegisterDay;
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
	public int getNaverShoppingOrder() {
		return naverShoppingOrder;
	}
	public void setNaverShoppingOrder(int naverShoppingOrder) {
		this.naverShoppingOrder = naverShoppingOrder;
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
	@Override
	public String toString() {
		return "BlliSmallProductVO [smallProduct=" + smallProduct
				+ ", midCategory=" + midCategory + ", smallProductMaker="
				+ smallProductMaker + ", smallProductWhenToUseMin="
				+ smallProductWhenToUseMin + ", smallProductWhenToUseMax="
				+ smallProductWhenToUseMax + ", smallProductDibsCount="
				+ smallProductDibsCount + ", smallProductMainPhotoLink="
				+ smallProductMainPhotoLink + ", smallProductScore="
				+ smallProductScore + ", naverShoppingLink="
				+ naverShoppingLink + ", naverShoppingOrder="
				+ naverShoppingOrder + ", smallProductPostingCount="
				+ smallProductPostingCount + ", productRegisterDay="
				+ productRegisterDay + "]";
	}
}