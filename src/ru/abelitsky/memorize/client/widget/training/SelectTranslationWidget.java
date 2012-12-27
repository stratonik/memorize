package ru.abelitsky.memorize.client.widget.training;

import ru.abelitsky.memorize.shared.dto.TrainingTest;
import ru.abelitsky.memorize.shared.dto.TrainingTest.TrainingTestType;
import ru.abelitsky.memorize.shared.dto.WordDTO;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SelectTranslationWidget extends Composite implements
		TrainingWidget {

	interface SelectTranslationWidgetUiBinder extends
			UiBinder<VerticalPanel, SelectTranslationWidget> {
	}

	private static SelectTranslationWidgetUiBinder uiBinder = GWT
			.create(SelectTranslationWidgetUiBinder.class);

	@UiField
	Label mainValue;
	@UiField
	Label secondValue;
	@UiField
	Label additional;
	@UiField
	Button showTranslation;
	@UiField
	VerticalPanel translationPanel;
	@UiField
	Label translation;

	private final Delegator delegator;

	private Boolean result;

	public SelectTranslationWidget(Delegator delegator) {
		this.delegator = delegator;
		initWidget(uiBinder.createAndBindUi(this));

		showTranslation.addDomHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					event.stopPropagation();
				}
			}
		}, KeyDownEvent.getType());
	}

	@Override
	public boolean checkAnswer() {
		return (result != null) ? result : false;
	}

	@UiHandler("right")
	void onClickRight(ClickEvent event) {
		result = true;
		delegator.onSelectAnswer();
	}

	@UiHandler("showTranslation")
	void onClickShowTranslation(ClickEvent event) {
		showTranslation.setVisible(false);
		showTranslation.setFocus(false);
		translationPanel.setVisible(true);
		secondValue.removeStyleName("invisible");
	}

	@UiHandler("wrong")
	void onClickWrong(ClickEvent event) {
		result = false;
		delegator.onSelectAnswer();
	}

	@Override
	public void setData(TrainingTest test) {
		WordDTO word = test.getWord();
		mainValue.setText(test.getQuestion());
		additional.setText(word.getAdditionalInfo());
		secondValue.addStyleName("invisible");
		if (test.getType() == TrainingTestType.kana) {
			secondValue.setText(word.getKanji());
		} else {
			secondValue.setText(word.getKana());
		}
		translation.setText(test.getAnswer());

		showTranslation.setVisible(true);
		showTranslation.setFocus(true);
		translationPanel.setVisible(false);
	}

}
