package kr.co.blli.model.vo;

public class BlliBabyVO {
	private String memberId;
	private String babyName;
	private String babyBirthday;
	private String babySex;
	private String babyPhoto;
	public BlliBabyVO() {
		super();
	}
	
	public BlliBabyVO(String memberId, String babyName, String babyBirthday,
			String babySex, String babyPhoto) {
		super();
		this.memberId = memberId;
		this.babyName = babyName;
		this.babyBirthday = babyBirthday;
		this.babySex = babySex;
		this.babyPhoto = babyPhoto;
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
	public String getBabyBirthday() {
		return babyBirthday;
	}
	public void setBabyBirthday(String babyBirthday) {
		this.babyBirthday = babyBirthday;
	}
	public String getBabySex() {
		return babySex;
	}
	public void setBabySex(String babySex) {
		this.babySex = babySex;
	}
	public String getBabyPhoto() {
		return babyPhoto;
	}
	public void setBabyPhoto(String babyPhoto) {
		this.babyPhoto = babyPhoto;
	}
	@Override
	public String toString() {
		return "BlliBabyVO [memberId=" + memberId + ", babyName=" + babyName
				+ ", babyBirthday=" + babyBirthday + ", babySex=" + babySex
				+ ", babyPhoto=" + babyPhoto + "]";
	}
	
}
