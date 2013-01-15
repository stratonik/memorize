package ru.abelitsky.memorize.client.widget.training;

import ru.abelitsky.memorize.client.Resources;
import ru.abelitsky.memorize.shared.dto.TrainingTest;
import ru.abelitsky.memorize.shared.dto.TrainingTest.TrainingTestType;
import ru.abelitsky.memorize.shared.dto.WordDTO;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class SelectVariantWidget extends Composite implements TrainingWidget, ClickHandler,
		AttachEvent.Handler {

	interface SelectVariantWidgetUiBinder extends UiBinder<VerticalPanel, SelectVariantWidget> {
	}

	private static SelectVariantWidgetUiBinder uiBinder = GWT
			.create(SelectVariantWidgetUiBinder.class);

	@UiField
	Image icon;
	@UiField
	Label secondValue;
	@UiField
	Label translation;
	@UiField
	Label additional;
	@UiField
	FlexTable variants;

	private final Delegator delegator;

	private TrainingTest test;
	private Boolean result;

	private HandlerRegistration keyPressHandler;

	public SelectVariantWidget(Delegator delegator) {
		this.delegator = delegator;
		initWidget(uiBinder.createAndBindUi(this));

		addAttachHandler(this);
		variants.addClickHandler(this);
	}

	@Override
	public boolean checkAnswer() {
		return (result != null) ? result : false;
	}

	@Override
	public void onAttachOrDetach(AttachEvent event) {
		if (event.isAttached()) {
			keyPressHandler = RootPanel.get().addDomHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					try {
						int index = Integer.parseInt(String.valueOf(event.getCharCode())) - 1;
						if ((index >= 0) && (index < test.getVariants().length)) {
							onVariantSelect(index);
						}
					} catch (Exception ex) {
					}
				}
			}, KeyPressEvent.getType());
		} else {
			if (keyPressHandler != null) {
				keyPressHandler.removeHandler();
				keyPressHandler = null;
			}
		}
	}

	@Override
	public void onClick(ClickEvent event) {
		Cell cell = variants.getCellForEvent(event);
		if (cell != null) {
			int index = cell.getRowIndex() + cell.getCellIndex() * variants.getRowCount();
			onVariantSelect(index);
		}
	}

	private void onVariantSelect(int index) {
		if (result == null) {
			if (index < test.getVariants().length) {
				secondValue.removeStyleName("invisible");
				result = test.getVariants()[index].equals(test.getAnswer());

				int rowNumber = test.getVariants().length / 2 + test.getVariants().length % 2;
				variants.getCellFormatter().addStyleName(index % rowNumber, index / rowNumber,
						(result) ? "right" : "wrong");

				delegator.onSelectAnswer();
			}
		}
	}

	@Override
	public void setData(TrainingTest test) {
		this.test = test;

		WordDTO word = test.getWord();
		translation.setText(word.getTranslation());
		additional.setText(word.getAdditionalInfo());
		secondValue.addStyleName("invisible");
		if (test.getAction().getType() == TrainingTestType.kana) {
			secondValue.setText(word.getKanji());
			icon.setResource(Resources.getTrainingWidgetImageBundle().kana());
		} else {
			secondValue.setText(word.getKana());
			icon.setResource(Resources.getTrainingWidgetImageBundle().kanji());
		}

		variants.removeAllRows();
		int rowNumber = test.getVariants().length / 2 + test.getVariants().length % 2;
		for (int i = 0; i < test.getVariants().length; i++) {
			variants.setHTML(i % rowNumber, i / rowNumber,
					String.valueOf(i + 1) + ". " + test.getVariants()[i]);
		}
		result = null;
	}
}
