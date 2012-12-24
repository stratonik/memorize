package ru.abelitsky.memorize.client.widget.training;

import ru.abelitsky.memorize.shared.dto.TrainingTest;
import ru.abelitsky.memorize.shared.dto.WordDTO;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class WriteKanjiWidget extends Composite implements TrainingWidget {

	interface WriteKanjiWidgetUiBinder extends
			UiBinder<VerticalPanel, WriteKanjiWidget> {
	}

	private static WriteKanjiWidgetUiBinder uiBinder = GWT
			.create(WriteKanjiWidgetUiBinder.class);

	@UiField
	Label kana;
	@UiField
	Label translation;
	@UiField
	Label additional;
	@UiField
	TextBox answer;

	private TrainingTest test;

	public WriteKanjiWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public boolean checkAnswer() {
		kana.removeStyleName("invisible");
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
		kana.setText(word.getKana());
		kana.addStyleName("invisible");
		translation.setText(word.getTranslation());
		additional.setText(word.getAdditionalInfo());

		answer.setText("");
		answer.removeStyleName("right");
		answer.removeStyleName("wrong");
		answer.setReadOnly(false);
		answer.setFocus(true);
	}

}
