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
	private int postingOrder;
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
	
	
	public final ArrayList<String> regex = new ArrayList<String>(Arrays.asList("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>","<[^>]*>",
			"<!--.*-->","&nbsp;","&#xfeff;","&#x200b;","&lt;","&gt;","&amp;","&#x1112;","\n"));

	public BlliPostingVO() {
		super();
	}

	public BlliPostingVO(String postingUrl, String smallProduct,
			String smallProductId, String postingTitle, String postingSummary,
			String postingContent, int postingScore, int postingLikeCount,
			int postingDislikeCount, int postingMediaCount,
			String postingPhotoLink, int postingTotalResidenceTime,
			int postingViewCount, int postingScrapeCount, String postingAuthor,
			String postingDate, int postingOrder, int postingReplyCount,
			String postingStatus, int isLike, int isDisLike, int isScrapped,
			ArrayList<String> imageList,
			HashMap<String, String> smallProductImage,
			ArrayList<String> smallProductList) {
		super();
		this.postingUrl = postingUrl;
		this.smallProduct = smallProduct;
		this.smallProductId = smallProductId;
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
		this.postingAuthor = postingAuthor;
		this.postingDate = postingDate;
		this.postingOrder = postingOrder;
		this.postingReplyCount = postingReplyCount;
		this.postingStatus = postingStatus;
		this.isLike = isLike;
		this.isDisLike = isDisLike;
		this.isScrapped = isScrapped;
		this.imageList = imageList;
		this.smallProductImage = smallProductImage;
		this.smallProductList = smallProductList;
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

	public int getPostingOrder() {
		return postingOrder;
	}

	public void setPostingOrder(int postingOrder) {
		this.postingOrder = postingOrder;
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

	public ArrayList<String> getRegex() {
		return regex;
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
				+ postingDate + ", postingOrder=" + postingOrder
				+ ", postingReplyCount=" + postingReplyCount
				+ ", postingStatus=" + postingStatus + ", isLike=" + isLike
				+ ", isDisLike=" + isDisLike + ", isScrapped=" + isScrapped
				+ ", imageList=" + imageList + ", smallProductImage="
				+ smallProductImage + ", smallProductList=" + smallProductList
				+ "]";
	}
}