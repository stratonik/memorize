package ru.abelitsky.memorize.client.view;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import ru.abelitsky.memorize.client.widget.training.SelectVariantWidget;
import ru.abelitsky.memorize.client.widget.training.ShowAnswerWidget;
import ru.abelitsky.memorize.client.widget.training.ShowKanaWidget;
import ru.abelitsky.memorize.client.widget.training.ShowKanjiWidget;
import ru.abelitsky.memorize.client.widget.training.TrainingWidget;
import ru.abelitsky.memorize.client.widget.training.TrainingWidget.Delegator;
import ru.abelitsky.memorize.client.widget.training.WriteAnswerWidget;
import ru.abelitsky.memorize.shared.dto.TrainingTest;
import ru.abelitsky.memorize.shared.dto.TrainingTest.TrainingTestAction;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class TrainingViewImpl extends Composite implements TrainingView, Delegator,
		AttachEvent.Handler {

	interface TrainingViewImplUiBinder extends UiBinder<HorizontalPanel, TrainingViewImpl> {
	}

	private static TrainingViewImplUiBinder uiBinder = GWT.create(TrainingViewImplUiBinder.class);

	private Presenter presenter;

	private final Map<TrainingTestAction, TrainingWidget> widgets;
	private final TrainingWidget showAnswerWidget = new ShowAnswerWidget();

	private List<TrainingTest> tests;
	private int currentTest;
	private boolean showAnswerMode;

	@UiField
	Button stop;
	@UiField
	Button next;
	@UiField
	SimplePanel testWidget;
	@UiField
	Label counter;
	@UiField
	Label timer;

	private HandlerRegistration enterDownHandler;
	private Timer testTimer;

	public TrainingViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
		addAttachHandler(this);

		TrainingWidget selectVariantWidget = new SelectVariantWidget(this);
		TrainingWidget writeAnswerWidget = new WriteAnswerWidget();

		widgets = new EnumMap<TrainingTestAction, TrainingWidget>(TrainingTestAction.class);

		widgets.put(TrainingTestAction.showKanaInfo, new ShowKanaWidget());
		widgets.put(TrainingTestAction.showKanjiInfo, new ShowKanjiWidget());
		widgets.put(TrainingTestAction.writeKana, writeAnswerWidget);
		widgets.put(TrainingTestAction.writeKanaByKanji, writeAnswerWidget);
		widgets.put(TrainingTestAction.writeKanji, writeAnswerWidget);
		widgets.put(TrainingTestAction.selectKana, selectVariantWidget);
		widgets.put(TrainingTestAction.selectKanji, selectVariantWidget);
	}

	private void goToAnswer() {
		showAnswerMode = true;
		testWidget.setWidget(showAnswerWidget);
		showAnswerWidget.setData(tests.get(currentTest));
	}

	private void goToNextTest() {
		showAnswerMode = false;
		if (++currentTest < tests.size()) {
			TrainingTest test = tests.get(currentTest);
			TrainingWidget widget = widgets.get(test.getAction());
			testWidget.setWidget(widget);
			widget.setData(test);

			if ((test.getAction() != TrainingTestAction.showKanaInfo)
					&& (test.getAction() != TrainingTestAction.showKanjiInfo)) {
				testTimer = new TestTimer();
				testTimer.scheduleRepeating(1000);
			}
			counter.setText(String.valueOf(currentTest + 1) + "/" + tests.size());
		} else {
			stop.click();
		}
	}

	@Override
	public void onAttachOrDetach(AttachEvent event) {
		if (event.isAttached()) {
			enterDownHandler = RootPanel.get().addDomHandler(new KeyDownHandler() {
				@Override
				public void onKeyDown(KeyDownEvent event) {
					if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
						next.click();
					}
				}
			}, KeyDownEvent.getType());
		} else {
			if (enterDownHandler != null) {
				enterDownHandler.removeHandler();
				enterDownHandler = null;
			}
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
			if (testTimer != null) {
				testTimer.cancel();
				testTimer = null;
			}

			TrainingTest test = tests.get(currentTest);
			TrainingWidget widget = widgets.get(test.getAction());
			final boolean result = widget.checkAnswer();
			presenter.saveResult(test.getWordStatusKey(), result);
			new Timer() {
				@Override
				public void run() {
					timer.setText("");
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
		presenter.goTo(presenter.getBackPlace());
	}

	@Override
	public void onSelectAnswer() {
		next.click();
	}

	@Override
	public void prepareView() {
		next.setEnabled(false);
		testWidget.setWidget(new Image("ajax-loader.gif"));
		counter.setText("");
		timer.setText("");
	}

	@Override
	public void setData(List<TrainingTest> data) {
		this.tests = data;
		currentTest = -1;
		goToNextTest();
		next.setEnabled(true);
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

	private class TestTimer extends Timer {

		private int seconds = 30;

		public TestTimer() {
			timer.setText(getSeconds());
		}

		@Override
		public void run() {
			seconds--;
			timer.setText(getSeconds());
			if (seconds <= 0) {
				next.click();
			}
		}

		public String getSeconds() {
			return NumberFormat.getFormat("00").format(seconds);
		}

	}

}
