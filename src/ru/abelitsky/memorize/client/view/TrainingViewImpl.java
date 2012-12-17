package ru.abelitsky.memorize.client.view;

import java.util.List;

import ru.abelitsky.memorize.shared.dto.TrainingTest;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;

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

	public TrainingViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiHandler("stop")
	void onClickStop(ClickEvent event) {
		presenter.goTo(presenter.getBackPlace());
	}

	@Override
	public void prepareView() {
		next.setEnabled(false);
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
