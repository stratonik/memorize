package ru.abelitsky.memorize.client.widget.training;

import ru.abelitsky.memorize.client.Resources;
import ru.abelitsky.memorize.shared.dto.TrainingTest;
import ru.abelitsky.memorize.shared.dto.TrainingTest.TrainingTestAction;
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

	interface WriteAnswerWidgetUiBinder extends UiBinder<VerticalPanel, WriteAnswerWidget> {
	}

	private static WriteAnswerWidgetUiBinder uiBinder = GWT.create(WriteAnswerWidgetUiBinder.class);

	@UiField
	Image icon;
	@UiField
	Label secondValue;
	@UiField
	Label question;
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
		question.setText(test.getQuestion());
		additional.setText(word.getAdditionalInfo());
		secondValue.addStyleName("invisible");
		if (test.getAction() == TrainingTestAction.writeKanaByKanji) {
			question.removeStyleName("translation");
			question.addStyleName("value-main");
			secondValue.setText(word.getKana());
			icon.setVisible(false);
		} else {
			question.removeStyleName("value-main");
			question.addStyleName("translation");
			icon.setVisible(true);
			if (test.getAction().getType() == TrainingTestType.kana) {
				secondValue.setText(word.getKanji());
				icon.setResource(Resources.getTrainingWidgetImageBundle().kana());
			} else {
				secondValue.setText(word.getKana());
				icon.setResource(Resources.getTrainingWidgetImageBundle().kanji());
			}
		}

		answer.setText("");
		answer.removeStyleName("right");
		answer.removeStyleName("wrong");
		answer.setReadOnly(false);
		answer.setFocus(true);
	}

}
