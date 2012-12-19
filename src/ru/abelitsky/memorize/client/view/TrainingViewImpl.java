package ru.abelitsky.memorize.client.view;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import ru.abelitsky.memorize.client.widget.training.ShowAnswerWidget;
import ru.abelitsky.memorize.client.widget.training.ShowKanaWidget;
import ru.abelitsky.memorize.client.widget.training.ShowKanjiWidget;
import ru.abelitsky.memorize.client.widget.training.TrainingWidget;
import ru.abelitsky.memorize.client.widget.training.WriteKanaWidget;
import ru.abelitsky.memorize.client.widget.training.WriteKanjiWidget;
import ru.abelitsky.memorize.shared.dto.TrainingTest;
import ru.abelitsky.memorize.shared.dto.TrainingTest.TrainingTestAction;
import ru.abelitsky.memorize.shared.dto.TrainingTest.TrainingTestType;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class TrainingViewImpl extends Composite implements TrainingView {

	interface TrainingViewImplUiBinder extends
			UiBinder<HorizontalPanel, TrainingViewImpl> {
	}

	private static TrainingViewImplUiBinder uiBinder = GWT
			.create(TrainingViewImplUiBinder.class);

	private Presenter presenter;

	private final Map<TrainingTestType, Map<TrainingTestAction, TrainingWidget>> widgets;
	private final TrainingWidget showAnswerWidget = new ShowAnswerWidget();

	private List<TrainingTest> tests;
	private TrainingTest currentTest;
	private boolean showAnswerMode;

	@UiField
	Button stop;
	@UiField
	Button next;
	@UiField
	SimplePanel testWidget;

	private HandlerRegistration enterDownHandler;

	public TrainingViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));

		widgets = new EnumMap<TrainingTestType, Map<TrainingTestAction, TrainingWidget>>(
				TrainingTestType.class);

		widgets.put(TrainingTestType.kanji,
				new EnumMap<TrainingTestAction, TrainingWidget>(
						TrainingTestAction.class));
		widgets.get(TrainingTestType.kanji).put(TrainingTestAction.showInfo,
				new ShowKanjiWidget());
		widgets.get(TrainingTestType.kanji).put(TrainingTestAction.writeAnswer,
				new WriteKanjiWidget());

		widgets.put(TrainingTestType.kana,
				new EnumMap<TrainingTestAction, TrainingWidget>(
						TrainingTestAction.class));
		widgets.get(TrainingTestType.kana).put(TrainingTestAction.showInfo,
				new ShowKanaWidget());
		widgets.get(TrainingTestType.kana).put(TrainingTestAction.writeAnswer,
				new WriteKanaWidget());
	}

	private void goToAnswer() {
		testWidget.setWidget(showAnswerWidget);
		showAnswerWidget.setData(currentTest);
		showAnswerMode = true;
	}

	private void goToNextTest() {
		if (tests.size() > 0) {
			currentTest = tests.remove(0);
			TrainingWidget widget = widgets.get(currentTest.getType()).get(
					currentTest.getAction());
			testWidget.setWidget(widget);
			widget.setData(currentTest);
			showAnswerMode = false;
		} else {
			stop.click();
		}
	}

	@UiHandler("next")
	void onClickNext(ClickEvent event) {
		if (showAnswerMode) {
			if (showAnswerWidget.checkAnswer()) {
				new Timer() {
					@Override
					public void run() {
						goToNextTest();
					}
				}.schedule(1000);
			}
		} else {
			TrainingWidget widget = widgets.get(currentTest.getType()).get(
					currentTest.getAction());
			final boolean result = widget.checkAnswer();
			presenter.saveResult(currentTest.getWordStatusKey(), result);
			new Timer() {
				@Override
				public void run() {
					if (result) {
						goToNextTest();
					} else {
						goToAnswer();
					}
				}
			}.schedule(1000);
		}
	}

	@UiHandler("stop")
	void onClickStop(ClickEvent event) {
		if (enterDownHandler != null) {
			enterDownHandler.removeHandler();
			enterDownHandler = null;
		}
		presenter.goTo(presenter.getBackPlace());
	}

	@Override
	public void prepareView() {
		next.setEnabled(false);
		testWidget.setWidget(new Image("/ajax-loader.gif"));

		enterDownHandler = RootPanel.get().addDomHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					next.click();
				}
			}
		}, KeyDownEvent.getType());
	}

	@Override
	public void setData(List<TrainingTest> data) {
		this.tests = data;
		currentTest = null;
		goToNextTest();
		next.setEnabled(true);
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

}
