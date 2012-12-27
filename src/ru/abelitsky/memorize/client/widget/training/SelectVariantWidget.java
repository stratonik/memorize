package ru.abelitsky.memorize.client.widget.training;

import ru.abelitsky.memorize.client.Resources;
import ru.abelitsky.memorize.shared.dto.TrainingTest;
import ru.abelitsky.memorize.shared.dto.TrainingTest.TrainingTestType;
import ru.abelitsky.memorize.shared.dto.WordDTO;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SelectVariantWidget extends Composite implements TrainingWidget,
		ClickHandler {

	interface SelectVariantWidgetUiBinder extends
			UiBinder<VerticalPanel, SelectVariantWidget> {
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

	public SelectVariantWidget(Delegator delegator) {
		this.delegator = delegator;
		initWidget(uiBinder.createAndBindUi(this));
		variants.addClickHandler(this);
	}

	@Override
	public boolean checkAnswer() {
		return result;
	}

	@Override
	public void onClick(ClickEvent event) {
		Cell cell = variants.getCellForEvent(event);
		if ((result == null) && (cell != null)) {
			int index = cell.getRowIndex() * 2 + cell.getCellIndex();
			if (index < test.getVariants().length) {
				secondValue.removeStyleName("invisible");
				result = test.getVariants()[index].equals(test.getAnswer());
				if (result) {
					cell.getElement().addClassName("right");
				} else {
					cell.getElement().addClassName("wrong");
				}
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
		if (test.getType() == TrainingTestType.kana) {
			secondValue.setText(word.getKanji());
			icon.setResource(Resources.getTrainingWidgetImageBundle().kana());
		} else {
			secondValue.setText(word.getKana());
			icon.setResource(Resources.getTrainingWidgetImageBundle().kanji());
		}

		variants.removeAllRows();
		for (int i = 0; i < test.getVariants().length; i++) {
			variants.setHTML(i / 2, i % 2, test.getVariants()[i]);
		}
		result = null;
	}

}
