package kr.co.blli.model.vo;

public class BlliPostingDisLikeVO {
	private String memberId;
	private String postingUrl;
	private String smallProductId;
	private String disLikeTime;
	public BlliPostingDisLikeVO() {
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
	public String getDisLikeTime() {
		return disLikeTime;
	}
	public void setDisLikeTime(String disLikeTime) {
		this.disLikeTime = disLikeTime;
	}
	@Override
	public String toString() {
		return "BlliPostingDisLikeVO [memberId=" + memberId + ", postingUrl="
				+ postingUrl + ", smallProductId=" + smallProductId
				+ ", disLikeTime=" + disLikeTime + "]";
	}
}
