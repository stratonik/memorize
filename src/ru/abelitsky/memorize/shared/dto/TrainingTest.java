package ru.abelitsky.memorize.shared.dto;

public class TrainingTest {

	public enum TrainingTestAction {
		showInfo, writeAnswer, selectVariant, selectTranslation
	}

	public enum TrainingTestType {
		kana, kanji
	}

	private TrainingTestType type;
	private TrainingTestAction action;

	private WordDTO word;
	private Long wordStatusId;

	private String question;
	private String answer;
	private String[] variants;

	public TrainingTestAction getAction() {
		return action;
	}

	public String getAnswer() {
		return answer;
	}

	public String getQuestion() {
		return question;
	}

	public TrainingTestType getType() {
		return type;
	}

	public String[] getVariants() {
		return variants;
	}

	public WordDTO getWord() {
		return word;
	}

	public Long getWordStatusId() {
		return wordStatusId;
	}

	public void setAction(TrainingTestAction action) {
		this.action = action;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public void setType(TrainingTestType type) {
		this.type = type;
	}

	public void setVariants(String[] variants) {
		this.variants = variants;
	}

	public void setWord(WordDTO word) {
		this.word = word;
	}

	public void setWordStatusId(Long wordStatusId) {
		this.wordStatusId = wordStatusId;
	}

}
