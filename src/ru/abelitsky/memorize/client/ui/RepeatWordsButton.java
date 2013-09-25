package ru.abelitsky.memorize.client.ui;

import com.google.gwt.user.client.ui.Button;

public class RepeatWordsButton extends Button {

	private int wordsCount;

	public RepeatWordsButton() {
		super();
		setWordsCount(0);
	}

	private String getProperPluralForm(int number, String form1, String form2, String form3) {
		int lastDigit = number % 10;
		if ((lastDigit == 1) && (number != 11)) {
			return form1;
		} else if ((lastDigit > 1 && lastDigit < 5) && (number < 11 || number > 14)) {
			return form2;
		} else {
			return form3;
		}
	}

	public int getWordsCount() {
		return wordsCount;
	}

	public void setWordsCount(int wordsCount) {
		this.wordsCount = wordsCount;

		setText("Повторить слова (" + wordsCount + " "
				+ getProperPluralForm(wordsCount, "слово", "слова", "слов") + ")...");
		setEnabled(wordsCount > 0);
	}

}
