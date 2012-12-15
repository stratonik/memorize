package ru.abelitsky.memorize.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

public class LoadWordsViewImpl extends Composite implements LoadWordsView {

	interface LoadWordsViewImplUiBinder extends
			UiBinder<VerticalPanel, LoadWordsViewImpl> {
	}

	private static LoadWordsViewImplUiBinder uiBinder = GWT
			.create(LoadWordsViewImplUiBinder.class);

	private Presenter presenter;

	@UiField
	TextArea data;

	public LoadWordsViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiHandler("cancel")
	void onClickCancel(ClickEvent event) {
		presenter.goTo(presenter.getBackPlace());
	}

	@UiHandler("load")
	void onClickLoad(ClickEvent event) {
		presenter.load(data.getText());
	}

	@Override
	public void prepareView() {
		data.setText("");
		data.setFocus(true);
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

}
