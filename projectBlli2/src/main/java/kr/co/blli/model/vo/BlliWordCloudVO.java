package kr.co.blli.model.vo;
public class BlliWordCloudVO {
	private String smallProductId;
	private String word;
	private int wordCount;
	private int wordLevel;
	public BlliWordCloudVO() {
		super();
	}
	public String getSmallProductId() {
		return smallProductId;
	}
	public void setSmallProductId(String smallProductId) {
		this.smallProductId = smallProductId;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public int getWordCount() {
		return wordCount;
	}
	public void setWordCount(int wordCount) {
		this.wordCount = wordCount;
	}
	public int getWordLevel() {
		return wordLevel;
	}
	public void setWordLevel(int wordLevel) {
		this.wordLevel = wordLevel;
	}
	@Override
	public String toString() {
		return "BlliWordCloudVO [smallProductId=" + smallProductId + ", word="
				+ word + ", wordCount=" + wordCount + ", wordLevel="
				+ wordLevel + "]";
	}
}
