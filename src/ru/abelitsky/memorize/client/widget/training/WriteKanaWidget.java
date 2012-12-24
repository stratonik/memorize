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

public class WriteKanaWidget extends Composite implements TrainingWidget {

	interface WriteKanaWidgetUiBinder extends
			UiBinder<VerticalPanel, WriteKanaWidget> {
	}

	private static WriteKanaWidgetUiBinder uiBinder = GWT
			.create(WriteKanaWidgetUiBinder.class);

	@UiField
	Label kanji;
	@UiField
	Label translation;
	@UiField
	Label additional;
	@UiField
	TextBox answer;

	private TrainingTest test;

	public WriteKanaWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public boolean checkAnswer() {
		kanji.removeStyleName("invisible");
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
		kanji.setText(word.getKanji());
		kanji.addStyleName("invisible");
		translation.setText(word.getTranslation());
		additional.setText(word.getAdditionalInfo());

		answer.setText("");
		answer.removeStyleName("right");
		answer.removeStyleName("wrong");
		answer.setReadOnly(false);
		answer.setFocus(true);
	}

}
