package kr.co.blli.model.vo;

import java.util.List;

public class BlliBabyVO {
	private String memberId;
	private String babyName;
	private String babyBirthday;
	private String babySex;
	private String babyPhoto;
	//BlliBaby테이블의 1:N 관계 테이블 VO 추가
	//private List<BlliScheduleVO> blliScheduleVOList;
	//월령 계산 후 저장하기 위한 변수 설정
	private int babyMonthAge;
	//태어나고 지난 일 수
	private int babyDayAge;
	
	public BlliBabyVO() {
		super();
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

	/*public List<BlliScheduleVO> getBlliScheduleVOList() {
		return blliScheduleVOList;
	}

	public void setBlliScheduleVOList(List<BlliScheduleVO> blliScheduleVOList) {
		this.blliScheduleVOList = blliScheduleVOList;
	}*/

	public int getBabyMonthAge() {
		return babyMonthAge;
	}

	public void setBabyMonthAge(int babyMonthAge) {
		this.babyMonthAge = babyMonthAge;
	}
	
	public int getBabyDayAge() {
		return babyDayAge;
	}

	public void setBabyDayAge(int babyDayAge) {
		this.babyDayAge = babyDayAge;
	}

	/*@Override
	public String toString() {
		return "BlliBabyVO [memberId=" + memberId + ", babyName=" + babyName
				+ ", babyBirthday=" + babyBirthday + ", babySex=" + babySex
				+ ", babyPhoto=" + babyPhoto + ", blliScheduleVOList="
				+ blliScheduleVOList + ", babyMonthAge=" + babyMonthAge
				+ ", babyDayAge=" + babyDayAge + "]";
	}*/

	
}
