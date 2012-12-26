package ru.abelitsky.memorize.client.widget.training;

import ru.abelitsky.memorize.shared.dto.TrainingTest;
import ru.abelitsky.memorize.shared.dto.WordDTO;
import ru.abelitsky.memorize.shared.dto.TrainingTest.TrainingTestType;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class WriteAnswerWidget extends Composite implements TrainingWidget {

	interface WriteAnswerWidgetUiBinder extends
			UiBinder<VerticalPanel, WriteAnswerWidget> {
	}

	private static WriteAnswerWidgetUiBinder uiBinder = GWT
			.create(WriteAnswerWidgetUiBinder.class);

	@UiField
	Image icon;
	@UiField
	Label secondValue;
	@UiField
	Label translation;
	@UiField
	Label additional;
	@UiField
	TextBox answer;

	private TrainingTest test;

	public WriteAnswerWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public boolean checkAnswer() {
		secondValue.removeStyleName("invisible");
		answer.setReadOnly(true);
		if (answer.getText().trim().equals(test.getAnswer())) {
			answer.addStyleName("right");
			return true;
		} else {
			answer.addStyleName("wrong");
			return false;
		}
	}

	@Override
	public void setData(TrainingTest test) {
		this.test = test;

		WordDTO word = test.getWord();
		translation.setText(word.getTranslation());
		additional.setText(word.getAdditionalInfo());
		secondValue.addStyleName("invisible");
		if (test.getType() == TrainingTestType.kana) {
			secondValue.setText(word.getKanji());
			icon.setUrl(KANA_ICON);
		} else {
			secondValue.setText(word.getKana());
			icon.setUrl(KANJI_ICON);
		}

		answer.setText("");
		answer.removeStyleName("right");
		answer.removeStyleName("wrong");
		answer.setReadOnly(false);
		answer.setFocus(true);
	}

}
