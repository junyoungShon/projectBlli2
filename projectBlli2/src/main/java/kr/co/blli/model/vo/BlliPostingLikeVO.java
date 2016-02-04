package kr.co.blli.model.vo;

public class BlliPostingLikeVO {
	private String memberId;
	private String postingUrl;
	private String smallProductId;
	private String likeTime;
	public BlliPostingLikeVO() {
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
	public String getLikeTime() {
		return likeTime;
	}
	public void setLikeTime(String likeTime) {
		this.likeTime = likeTime;
	}
	@Override
	public String toString() {
		return "BlliPostingLikeVO [memberId=" + memberId + ", postingUrl="
				+ postingUrl + ", smallProductId=" + smallProductId
				+ ", likeTime=" + likeTime + "]";
	}
}
