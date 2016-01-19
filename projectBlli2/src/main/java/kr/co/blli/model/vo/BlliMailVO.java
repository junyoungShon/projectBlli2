package kr.co.blli.model.vo;

public class BlliMailVO {
	
	private String mailForm;
	private String mailSubject;
	private String mailContentFile;
	
	public BlliMailVO() {
		super();
	}

	public BlliMailVO(String mailForm, String mailSubject,
			String mailContentFile) {
		super();
		this.mailForm = mailForm;
		this.mailSubject = mailSubject;
		this.mailContentFile = mailContentFile;
	}

	public String getMailForm() {
		return mailForm;
	}

	public void setMailForm(String mailForm) {
		this.mailForm = mailForm;
	}

	public String getMailSubject() {
		return mailSubject;
	}

	public void setMailSubject(String mailSubject) {
		this.mailSubject = mailSubject;
	}

	public String getMailContentFile() {
		return mailContentFile;
	}

	public void setMailContentFile(String mailContentFile) {
		this.mailContentFile = mailContentFile;
	}

	@Override
	public String toString() {
		return "BlliMailVO [mailForm=" + mailForm + ", mailSubject="
				+ mailSubject + ", mailContentFile=" + mailContentFile + "]";
	}

}
