package kr.co.blli.model.vo;

public class BlliSmallProductBuyLinkVO {
	private String smallProductId;
	private String buyLink;
	private String buyLinkPrice; // String으로 수정
	private String buyLinkDeliveryCost;
	private String buyLinkOption;
	private String seller;
	private int buyLinkClickCount;
	public BlliSmallProductBuyLinkVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BlliSmallProductBuyLinkVO(String smallProductId, String buyLink,
			String buyLinkPrice, String buyLinkDeliveryCost,
			String buyLinkOption, String seller, int buyLinkClickCount) {
		super();
		this.smallProductId = smallProductId;
		this.buyLink = buyLink;
		this.buyLinkPrice = buyLinkPrice;
		this.buyLinkDeliveryCost = buyLinkDeliveryCost;
		this.buyLinkOption = buyLinkOption;
		this.seller = seller;
		this.buyLinkClickCount = buyLinkClickCount;
	}
	public String getSmallProductId() {
		return smallProductId;
	}
	public void setSmallProductId(String smallProductId) {
		this.smallProductId = smallProductId;
	}
	public String getBuyLink() {
		return buyLink;
	}
	public void setBuyLink(String buyLink) {
		this.buyLink = buyLink;
	}
	public String getBuyLinkPrice() {
		return buyLinkPrice;
	}
	public void setBuyLinkPrice(String buyLinkPrice) {
		this.buyLinkPrice = buyLinkPrice;
	}
	public String getBuyLinkDeliveryCost() {
		return buyLinkDeliveryCost;
	}
	public void setBuyLinkDeliveryCost(String buyLinkDeliveryCost) {
		this.buyLinkDeliveryCost = buyLinkDeliveryCost;
	}
	public String getBuyLinkOption() {
		return buyLinkOption;
	}
	public void setBuyLinkOption(String buyLinkOption) {
		this.buyLinkOption = buyLinkOption;
	}
	public String getSeller() {
		return seller;
	}
	public void setSeller(String seller) {
		this.seller = seller;
	}
	public int getBuyLinkClickCount() {
		return buyLinkClickCount;
	}
	public void setBuyLinkClickCount(int buyLinkClickCount) {
		this.buyLinkClickCount = buyLinkClickCount;
	}
	@Override
	public String toString() {
		return "BlliSmallProductBuyLinkVO [smallProductId=" + smallProductId
				+ ", buyLink=" + buyLink + ", buyLinkPrice=" + buyLinkPrice
				+ ", buyLinkDeliveryCost=" + buyLinkDeliveryCost
				+ ", buyLinkOption=" + buyLinkOption + ", seller=" + seller
				+ ", buyLinkClickCount=" + buyLinkClickCount + "]";
	}
}
