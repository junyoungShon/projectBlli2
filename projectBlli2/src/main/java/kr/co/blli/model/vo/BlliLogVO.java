package kr.co.blli.model.vo;

import java.util.ArrayList;

public class BlliLogVO {
	private int number;
	private String startTime;
	private String methodName;
	private String runTime;
	private String executor;
	private String highRankCategoryCount;
	private String categoryCount;
	private String insertCategoryCount;
	private String updateCategoryCount;
	private String exceptionCount;
	private String denySmallProductCount;
	private String notUpdateProductCount;
	private String denyPostingCount;
	private String notUpdatePostingCount;
	private ArrayList<BlliDetailException> detailException;
	public BlliLogVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getRunTime() {
		return runTime;
	}
	public void setRunTime(String runTime) {
		this.runTime = runTime;
	}
	public String getExecutor() {
		return executor;
	}
	public void setExecutor(String executor) {
		this.executor = executor;
	}
	public String getHighRankCategoryCount() {
		return highRankCategoryCount;
	}
	public void setHighRankCategoryCount(String highRankCategoryCount) {
		this.highRankCategoryCount = highRankCategoryCount;
	}
	public String getCategoryCount() {
		return categoryCount;
	}
	public void setCategoryCount(String categoryCount) {
		this.categoryCount = categoryCount;
	}
	public String getInsertCategoryCount() {
		return insertCategoryCount;
	}
	public void setInsertCategoryCount(String insertCategoryCount) {
		this.insertCategoryCount = insertCategoryCount;
	}
	public String getUpdateCategoryCount() {
		return updateCategoryCount;
	}
	public void setUpdateCategoryCount(String updateCategoryCount) {
		this.updateCategoryCount = updateCategoryCount;
	}
	public String getExceptionCount() {
		return exceptionCount;
	}
	public void setExceptionCount(String exceptionCount) {
		this.exceptionCount = exceptionCount;
	}
	public ArrayList<BlliDetailException> getDetailException() {
		return detailException;
	}
	public void setDetailException(ArrayList<BlliDetailException> detailException) {
		this.detailException = detailException;
	}
	public String getDenySmallProductCount() {
		return denySmallProductCount;
	}
	public void setDenySmallProductCount(String denySmallProductCount) {
		this.denySmallProductCount = denySmallProductCount;
	}
	public String getNotUpdateProductCount() {
		return notUpdateProductCount;
	}
	public void setNotUpdateProductCount(String notUpdateProductCount) {
		this.notUpdateProductCount = notUpdateProductCount;
	}
	public String getDenyPostingCount() {
		return denyPostingCount;
	}
	public void setDenyPostingCount(String denyPostingCount) {
		this.denyPostingCount = denyPostingCount;
	}
	public String getNotUpdatePostingCount() {
		return notUpdatePostingCount;
	}
	public void setNotUpdatePostingCount(String notUpdatePostingCount) {
		this.notUpdatePostingCount = notUpdatePostingCount;
	}
	@Override
	public String toString() {
		return "BlliLogVO [number=" + number + ", startTime=" + startTime
				+ ", methodName=" + methodName + ", runTime=" + runTime
				+ ", executor=" + executor + ", highRankCategoryCount="
				+ highRankCategoryCount + ", categoryCount=" + categoryCount
				+ ", insertCategoryCount=" + insertCategoryCount
				+ ", updateCategoryCount=" + updateCategoryCount
				+ ", exceptionCount=" + exceptionCount
				+ ", denySmallProductCount=" + denySmallProductCount
				+ ", notUpdateProductCount=" + notUpdateProductCount
				+ ", denyPostingCount=" + denyPostingCount
				+ ", notUpdatePostingCount=" + notUpdatePostingCount
				+ ", detailException=" + detailException + "]";
	}
}