package ru.abelitsky.memorize.client.widget.training;

import ru.abelitsky.memorize.shared.dto.TrainingTest;
import ru.abelitsky.memorize.shared.dto.WordDTO;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ShowKanaWidget extends Composite implements TrainingWidget {

	interface ShowKanaWidgetUiBinder extends
			UiBinder<VerticalPanel, ShowKanaWidget> {
	}

	private static ShowKanaWidgetUiBinder uiBinder = GWT
			.create(ShowKanaWidgetUiBinder.class);

	@UiField
	Label kanji;
	@UiField
	Label kana;
	@UiField
	Label translation;
	@UiField
	Label additional;

	public ShowKanaWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public boolean checkAnswer() {
		return true;
	}

	@Override
	public void setData(TrainingTest test) {
		WordDTO word = test.getWord();
		kanji.setText(word.getKanji());
		kana.setText(word.getKana());
		translation.setText(word.getTranslation());
		additional.setText(word.getAdditionalInfo());
	}

}
