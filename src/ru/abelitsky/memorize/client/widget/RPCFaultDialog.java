package ru.abelitsky.memorize.client.widget;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

public class RPCFaultDialog extends DialogBox {

	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server: ";

	private HTML serverResponseLabel = new HTML();
	private Button closeButton = new Button("Закрыть");

	public RPCFaultDialog() {
		super();

		setText("Remote Procedure Call - Ошибка");
		setAnimationEnabled(true);

		closeButton.getElement().setId("closeButton");
		serverResponseLabel.addStyleName("serverResponseLabelError");

		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(new HTML("<b>Ответ сервера:</b>"));
		dialogVPanel.add(serverResponseLabel);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogVPanel.add(closeButton);
		setWidget(dialogVPanel);

		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				hide();
			}
		});
	}

	public void show(Throwable caught) {
		caught.printStackTrace();
		serverResponseLabel.setHTML(SERVER_ERROR + caught.getMessage());
		center();
		closeButton.setFocus(true);
	}
}
