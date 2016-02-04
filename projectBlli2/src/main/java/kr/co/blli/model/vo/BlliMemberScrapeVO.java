package kr.co.blli.model.vo;
public class BlliMemberScrapeVO {
	private String memberId;
	private String postingUrl;
	private String smallProductId;
	private String scrapeTime;
	public BlliMemberScrapeVO() {
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
	public String getScrapeTime() {
		return scrapeTime;
	}
	public void setScrapeTime(String scrapTime) {
		this.scrapeTime = scrapTime;
	}
	@Override
	public String toString() {
		return "BlliMemberScrapVO [memberId=" + memberId + ", postingUrl="
				+ postingUrl + ", smallProductId=" + smallProductId
				+ ", scrapeTime=" + scrapeTime + "]";
	}
	
}
