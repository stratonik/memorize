package ru.abelitsky.memorize.client.widget;

import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;

public class LoadingDialog extends DialogBox {

	public LoadingDialog() {
		super();
		setText("Загрузка...");

		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.setWidth("200px");
		dialogVPanel.setHeight("150px");
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		dialogVPanel.setVerticalAlignment(VerticalPanel.ALIGN_MIDDLE);
		dialogVPanel.add(new Image("/ajax-loader.gif"));
		setWidget(dialogVPanel);
	}

}
