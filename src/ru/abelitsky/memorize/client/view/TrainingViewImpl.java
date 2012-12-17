package ru.abelitsky.memorize.client.view;

import java.util.List;

import ru.abelitsky.memorize.shared.dto.TrainingTest;

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
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class TrainingViewImpl extends Composite implements TrainingView {

	interface TrainingViewImplUiBinder extends
			UiBinder<HorizontalPanel, TrainingViewImpl> {
	}

	private static TrainingViewImplUiBinder uiBinder = GWT
			.create(TrainingViewImplUiBinder.class);

	private Presenter presenter;

	private List<TrainingTest> tests;

	@UiField
	Button stop;
	@UiField
	Button next;

	private HandlerRegistration enterDownHandler;

	public TrainingViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
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
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

}
