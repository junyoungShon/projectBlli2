package kr.co.blli.model.vo;
public class BlliBuyLinkClickVO {
	private String memberId;
	private String clickTime;
	private String seller;
	private String smallProductId;
	public BlliBuyLinkClickVO() {
		super();
	}
	public BlliBuyLinkClickVO(String memberId, String clickTime,
			String seller, String smallProductId) {
		super();
		this.memberId = memberId;
		this.clickTime = clickTime;
		this.seller = seller;
		this.smallProductId = smallProductId;
	}
	@Override
	public String toString() {
		return "BlliBuyLinkClickVO [memberId=" + memberId + ", clickTime="
				+ clickTime + ", buyLink=" + seller + ", smallProductId="
				+ smallProductId + "]";
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getClickTime() {
		return clickTime;
	}
	public void setClickTime(String clickTime) {
		this.clickTime = clickTime;
	}
	public String getSeller() {
		return seller;
	}
	public void setSeller(String seller) {
		this.seller = seller;
	}
	public String getSmallProductId() {
		return smallProductId;
	}
	public void setSmallProductId(String smallProductId) {
		this.smallProductId = smallProductId;
	}
	
}
