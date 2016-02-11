package kr.co.blli.model.vo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class BlliPostingVO {
	private String postingUrl;
	private String smallProduct;
	private String smallProductId;
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
	private String postingAuthor;
	private String postingDate;
	private int postingRank;
	private int postingReplyCount;
	private String postingStatus;
	//16.01.22 추가
	private int isLike;
	//16.01.22 추가
	private int isDisLike;
	//16.01.22 추가
	private int isScrapped;
	private ArrayList<String> imageList;
	private HashMap<String, String> smallProductImage;
	private ArrayList<String> smallProductList;
	//16.02.05추가
	private String postingDbInsertDate;
	
	
	public final ArrayList<String> regex = new ArrayList<String>(Arrays.asList("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>","<[^>]*>",
			"<!--.*-->","&nbsp;","&#xfeff;","&#x200b;","&lt;","&gt;","&amp;","&#x1112;","\n", "&#xfffd;"));
	
	public final ArrayList<String> denyWord = new ArrayList<String>(Arrays.asList("무상", "체험", "무료", "제공", "이벤트", "공짜", "지원"));

	public BlliPostingVO() {
		super();
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


	public String getSmallProductId() {
		return smallProductId;
	}


	public void setSmallProductId(String smallProductId) {
		this.smallProductId = smallProductId;
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


	public String getPostingAuthor() {
		return postingAuthor;
	}


	public void setPostingAuthor(String postingAuthor) {
		this.postingAuthor = postingAuthor;
	}


	public String getPostingDate() {
		return postingDate;
	}


	public void setPostingDate(String postingDate) {
		this.postingDate = postingDate;
	}


	public int getPostingRank() {
		return postingRank;
	}


	public void setPostingRank(int postingRank) {
		this.postingRank = postingRank;
	}


	public int getPostingReplyCount() {
		return postingReplyCount;
	}


	public void setPostingReplyCount(int postingReplyCount) {
		this.postingReplyCount = postingReplyCount;
	}


	public String getPostingStatus() {
		return postingStatus;
	}


	public void setPostingStatus(String postingStatus) {
		this.postingStatus = postingStatus;
	}


	public int getIsLike() {
		return isLike;
	}


	public void setIsLike(int isLike) {
		this.isLike = isLike;
	}


	public int getIsDisLike() {
		return isDisLike;
	}


	public void setIsDisLike(int isDisLike) {
		this.isDisLike = isDisLike;
	}


	public int getIsScrapped() {
		return isScrapped;
	}


	public void setIsScrapped(int isScrapped) {
		this.isScrapped = isScrapped;
	}


	public ArrayList<String> getImageList() {
		return imageList;
	}


	public void setImageList(ArrayList<String> imageList) {
		this.imageList = imageList;
	}


	public HashMap<String, String> getSmallProductImage() {
		return smallProductImage;
	}


	public void setSmallProductImage(HashMap<String, String> smallProductImage) {
		this.smallProductImage = smallProductImage;
	}


	public ArrayList<String> getSmallProductList() {
		return smallProductList;
	}


	public void setSmallProductList(ArrayList<String> smallProductList) {
		this.smallProductList = smallProductList;
	}


	public String getPostingDbInsertDate() {
		return postingDbInsertDate;
	}


	public void setPostingDbInsertDate(String postingDbInsertDate) {
		this.postingDbInsertDate = postingDbInsertDate;
	}


	public ArrayList<String> getRegex() {
		return regex;
	}


	@Override
	public String toString() {
		return "BlliPostingVO [postingUrl=" + postingUrl + ", smallProduct="
				+ smallProduct + ", smallProductId=" + smallProductId
				+ ", postingTitle=" + postingTitle + ", postingSummary="
				+ postingSummary + ", postingContent=" + postingContent
				+ ", postingScore=" + postingScore + ", postingLikeCount="
				+ postingLikeCount + ", postingDislikeCount="
				+ postingDislikeCount + ", postingMediaCount="
				+ postingMediaCount + ", postingPhotoLink=" + postingPhotoLink
				+ ", postingTotalResidenceTime=" + postingTotalResidenceTime
				+ ", postingViewCount=" + postingViewCount
				+ ", postingScrapeCount=" + postingScrapeCount
				+ ", postingAuthor=" + postingAuthor + ", postingDate="
				+ postingDate + ", postingRank=" + postingRank
				+ ", postingReplyCount=" + postingReplyCount
				+ ", postingStatus=" + postingStatus + ", isLike=" + isLike
				+ ", isDisLike=" + isDisLike + ", isScrapped=" + isScrapped
				+ ", imageList=" + imageList + ", smallProductImage="
				+ smallProductImage + ", smallProductList=" + smallProductList
				+ ", postingDbInsertDate=" + postingDbInsertDate + ", regex="
				+ regex + "]";
	}

	
}