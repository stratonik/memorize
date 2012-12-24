package ru.abelitsky.memorize.client.widget.training;

import ru.abelitsky.memorize.shared.dto.TrainingTest;
import ru.abelitsky.memorize.shared.dto.TrainingTest.TrainingTestType;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class ShowAnswerWidget extends Composite implements TrainingWidget {

	interface ShowAnswerWidgetUiBinder extends
			UiBinder<VerticalPanel, ShowAnswerWidget> {
	}

	private static ShowAnswerWidgetUiBinder uiBinder = GWT
			.create(ShowAnswerWidgetUiBinder.class);

	private final ShowKanaWidget showKanaWidget = new ShowKanaWidget();
	private final ShowKanjiWidget showKanjiWidget = new ShowKanjiWidget();

	private final KeyDownHandler keyDownHandler;
	private HandlerRegistration keyDownHandlerRegistration;

	@UiField
	SimplePanel info;
	@UiField
	TextBox answer;

	private TrainingTest test;

	public ShowAnswerWidget() {
		initWidget(uiBinder.createAndBindUi(this));
		keyDownHandler = new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				answer.removeStyleName("wrong");
				if (keyDownHandlerRegistration != null) {
					keyDownHandlerRegistration.removeHandler();
					keyDownHandlerRegistration = null;
				}
			}
		};
	}

	@Override
	public boolean checkAnswer() {
		if (answer.getText().trim().equals(test.getAnswer())) {
			answer.addStyleName("right");
			answer.setReadOnly(true);
			return true;
		} else {
			answer.addStyleName("wrong");
			answer.selectAll();
			keyDownHandlerRegistration = answer
					.addKeyDownHandler(keyDownHandler);
			return false;
		}
	}

	@Override
	public void setData(TrainingTest test) {
		this.test = test;
		if (test.getType() == TrainingTestType.kana) {
			showKanaWidget.setData(test);
			info.setWidget(showKanaWidget);
		} else {
			showKanjiWidget.setData(test);
			info.setWidget(showKanjiWidget);
		}

		answer.setText("");
		answer.removeStyleName("right");
		answer.removeStyleName("wrong");
		answer.setReadOnly(false);
		answer.setFocus(true);
	}

}
