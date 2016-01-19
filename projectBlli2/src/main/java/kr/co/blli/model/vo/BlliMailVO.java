package kr.co.blli.model.vo;

public class BlliMailVO {
	
	private String mailForm;
	private String mailSubject;
	private String mailContent;
	private String mailFormFile;
	
	public BlliMailVO() {
		super();
	}

	public BlliMailVO(String mailForm, String mailSubject, String mailContent,
			String mailFormFile) {
		super();
		this.mailForm = mailForm;
		this.mailSubject = mailSubject;
		this.mailContent = mailContent;
		this.mailFormFile = mailFormFile;
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

	public String getMailContent() {
		return mailContent;
	}

	public void setMailContent(String mailContent) {
		this.mailContent = mailContent;
	}

	public String getMailFormFile() {
		return mailFormFile;
	}

	public void setMailFormFile(String mailFormFile) {
		this.mailFormFile = mailFormFile;
	}

	@Override
	public String toString() {
		return "BlliMailVO [mailForm=" + mailForm + ", mailSubject="
				+ mailSubject + ", mailContent=" + mailContent
				+ ", mailFormFile=" + mailFormFile + "]";
	}
	
}
