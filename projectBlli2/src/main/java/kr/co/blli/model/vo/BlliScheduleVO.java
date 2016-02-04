package kr.co.blli.model.vo;

public class BlliScheduleVO {
	private int scheduleId;
	private String memberId;
	private String babyName;
	private String scheduleDate;
	private String scheduleTitle;
	private String scheduleContent;
	private String scheduleLocation;
	private int scheduleCheckState;
	
	public BlliScheduleVO() {
		super();
	}
	public int getScheduleId() {
		return scheduleId;
	}
	public void setScheduleId(int scheduleId) {
		this.scheduleId = scheduleId;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getBabyName() {
		return babyName;
	}
	public void setBabyName(String babyName) {
		this.babyName = babyName;
	}
	public String getScheduleDate() {
		return scheduleDate;
	}
	public void setScheduleDate(String scheduleDate) {
		this.scheduleDate = scheduleDate;
	}
	public String getScheduleTitle() {
		return scheduleTitle;
	}
	public void setScheduleTitle(String scheduleTitle) {
		this.scheduleTitle = scheduleTitle;
	}
	public String getScheduleContent() {
		return scheduleContent;
	}
	public void setScheduleContent(String scheduleContent) {
		this.scheduleContent = scheduleContent;
	}
	public String getScheduleLocation() {
		return scheduleLocation;
	}
	public void setScheduleLocation(String scheduleLocation) {
		this.scheduleLocation = scheduleLocation;
	}
	public int getScheduleCheckState() {
		return scheduleCheckState;
	}
	public void setScheduleCheckState(int scheduleCheckState) {
		this.scheduleCheckState = scheduleCheckState;
	}
	@Override
	public String toString() {
		return "BlliScheduleVO [scheduleId=" + scheduleId + ", memberId="
				+ memberId + ", babyName=" + babyName + ", scheduleDate="
				+ scheduleDate + ", scheduleTitle=" + scheduleTitle
				+ ", scheduleContent=" + scheduleContent
				+ ", scheduleLocation=" + scheduleLocation
				+ ", scheduleCheckState=" + scheduleCheckState + "]";
	}
}
