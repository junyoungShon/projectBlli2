package kr.co.blli.model.vo;

import java.util.ArrayList;
import java.util.Arrays;

public class BlliPostingVO {
	private String postingUrl;
	private String smallProduct;
	private String postingTitle;
	private String postingSummary;
	private String postingContent;
	private int postingScore;
	private int postingLikeCount;
	private int postingDislikeCount;
	private int postingMediaCount;
	private String postingPhotoLink;
	private int postingTotalResidenceTime;
	private int postingViewCount;
	private int postingScrapeCount;
	
	public final ArrayList<String> regex = new ArrayList<String>(Arrays.asList("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>","<[^>]*>",
			"<!--.*-->","&nbsp;","&#xfeff;","&#x200b;","&lt;","&gt;","&amp;","&#x1112;","\n"));

	public BlliPostingVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BlliPostingVO(String postingUrl, String smallProduct,
			String postingTitle, String postingSummary, String postingContent,
			int postingScore, int postingLikeCount, int postingDislikeCount,
			int postingMediaCount, String postingPhotoLink,
			int postingTotalResidenceTime, int postingViewCount,
			int postingScrapeCount) {
		super();
		this.postingUrl = postingUrl;
		this.smallProduct = smallProduct;
		this.postingTitle = postingTitle;
		this.postingSummary = postingSummary;
		this.postingContent = postingContent;
		this.postingScore = postingScore;
		this.postingLikeCount = postingLikeCount;
		this.postingDislikeCount = postingDislikeCount;
		this.postingMediaCount = postingMediaCount;
		this.postingPhotoLink = postingPhotoLink;
		this.postingTotalResidenceTime = postingTotalResidenceTime;
		this.postingViewCount = postingViewCount;
		this.postingScrapeCount = postingScrapeCount;
	}

	public String getPostingUrl() {
		return postingUrl;
	}

	public void setPostingUrl(String postingUrl) {
		this.postingUrl = postingUrl;
	}

	public String getSmallProduct() {
		return smallProduct;
	}

	public void setSmallProduct(String smallProduct) {
		this.smallProduct = smallProduct;
	}

	public String getPostingTitle() {
		return postingTitle;
	}

	public void setPostingTitle(String postingTitle) {
		this.postingTitle = postingTitle;
	}

	public String getPostingSummary() {
		return postingSummary;
	}

	public void setPostingSummary(String postingSummary) {
		this.postingSummary = postingSummary;
	}

	public String getPostingContent() {
		return postingContent;
	}

	public void setPostingContent(String postingContent) {
		this.postingContent = postingContent;
	}

	public int getPostingScore() {
		return postingScore;
	}

	public void setPostingScore(int postingScore) {
		this.postingScore = postingScore;
	}

	public int getPostingLikeCount() {
		return postingLikeCount;
	}

	public void setPostingLikeCount(int postingLikeCount) {
		this.postingLikeCount = postingLikeCount;
	}

	public int getPostingDislikeCount() {
		return postingDislikeCount;
	}

	public void setPostingDislikeCount(int postingDislikeCount) {
		this.postingDislikeCount = postingDislikeCount;
	}

	public int getPostingMediaCount() {
		return postingMediaCount;
	}

	public void setPostingMediaCount(int postingMediaCount) {
		this.postingMediaCount = postingMediaCount;
	}

	public String getPostingPhotoLink() {
		return postingPhotoLink;
	}

	public void setPostingPhotoLink(String postingPhotoLink) {
		this.postingPhotoLink = postingPhotoLink;
	}

	public int getPostingTotalResidenceTime() {
		return postingTotalResidenceTime;
	}

	public void setPostingTotalResidenceTime(int postingTotalResidenceTime) {
		this.postingTotalResidenceTime = postingTotalResidenceTime;
	}

	public int getPostingViewCount() {
		return postingViewCount;
	}

	public void setPostingViewCount(int postingViewCount) {
		this.postingViewCount = postingViewCount;
	}

	public int getPostingScrapeCount() {
		return postingScrapeCount;
	}

	public void setPostingScrapeCount(int postingScrapeCount) {
		this.postingScrapeCount = postingScrapeCount;
	}

	public ArrayList<String> getRegex() {
		return regex;
	}

	@Override
	public String toString() {
		return "BlliPostingVO [postingUrl=" + postingUrl + ", smallProduct="
				+ smallProduct + ", postingTitle=" + postingTitle
				+ ", postingSummary=" + postingSummary + ", postingContent="
				+ postingContent + ", postingScore=" + postingScore
				+ ", postingLikeCount=" + postingLikeCount
				+ ", postingDislikeCount=" + postingDislikeCount
				+ ", postingMediaCount=" + postingMediaCount
				+ ", postingPhotoLink=" + postingPhotoLink
				+ ", postingTotalResidenceTime=" + postingTotalResidenceTime
				+ ", postingViewCount=" + postingViewCount
				+ ", postingScrapeCount=" + postingScrapeCount + "]";
	}
}