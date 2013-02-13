package ru.abelitsky.memorize.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class TrainingTest implements IsSerializable {

	public enum TrainingTestAction {
		showKanaInfo(TrainingTestType.kana), showKanjiInfo(TrainingTestType.kanji),
		writeKana(TrainingTestType.kana), writeKanji(TrainingTestType.kanji),
		writeKanaByKanji(TrainingTestType.kana), selectKana(TrainingTestType.kana),
		selectKanji(TrainingTestType.kanji);

		private TrainingTestType type;

		private TrainingTestAction(TrainingTestType type) {
			this.type = type;
		}

		public TrainingTestType getType() {
			return type;
		}
	}

	public enum TrainingTestType {
		kana, kanji
	}

	private TrainingTestAction action;

	private WordDTO word;
	private String wordStatusKey;

	private String question;
	private String answer;
	private String[] variants;
	private String wrongAnswer;

	public TrainingTestAction getAction() {
		return action;
	}

	public String getAnswer() {
		return answer;
	}

	public String getQuestion() {
		return question;
	}

	public String[] getVariants() {
		return variants;
	}

	public WordDTO getWord() {
		return word;
	}

	public String getWordStatusKey() {
		return wordStatusKey;
	}

	public String getWrongAnswer() {
		return wrongAnswer;
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

	public void setVariants(String[] variants) {
		this.variants = variants;
	}

	public void setWord(WordDTO word) {
		this.word = word;
	}

	public void setWordStatusKey(String wordStatusKey) {
		this.wordStatusKey = wordStatusKey;
	}

	public void setWrongAnswer(String wrongAnswer) {
		this.wrongAnswer = wrongAnswer;
	}

}
