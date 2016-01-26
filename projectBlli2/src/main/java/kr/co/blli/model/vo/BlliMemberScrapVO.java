package kr.co.blli.model.vo;
public class BlliMemberScrapVO {
	private String memberId;
	private String postingUrl;
	private String smallProductId;
	private String scrapTime;
	public BlliMemberScrapVO() {
		super();
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getPostingUrl() {
		return postingUrl;
	}
	public void setPostingUrl(String postingUrl) {
		this.postingUrl = postingUrl;
	}
	public String getSmallProductId() {
		return smallProductId;
	}
	public void setSmallProductId(String smallProductId) {
		this.smallProductId = smallProductId;
	}
	public String getScrapTime() {
		return scrapTime;
	}
	public void setScrapTime(String scrapTime) {
		this.scrapTime = scrapTime;
	}
	@Override
	public String toString() {
		return "BlliMemberScrapVO [memberId=" + memberId + ", postingUrl="
				+ postingUrl + ", smallProductId=" + smallProductId
				+ ", scrapTime=" + scrapTime + "]";
	}
	
}
