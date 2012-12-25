package ru.abelitsky.memorize.client.widget.training;

import ru.abelitsky.memorize.shared.dto.TrainingTest;
import ru.abelitsky.memorize.shared.dto.WordDTO;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SelectKanaWidget extends Composite implements TrainingWidget,
		ClickHandler {

	interface SelectKanaWidgetUiBinder extends
			UiBinder<VerticalPanel, SelectKanaWidget> {
	}

	private static SelectKanaWidgetUiBinder uiBinder = GWT
			.create(SelectKanaWidgetUiBinder.class);

	@UiField
	Label kanji;
	@UiField
	Label translation;
	@UiField
	Label additional;
	@UiField
	FlexTable variants;

	private final Delegator delegator;

	private TrainingTest test;
	private Boolean result;

	public SelectKanaWidget(Delegator delegator) {
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
		kanji.setText(word.getKanji());
		kanji.addStyleName("invisible");
		translation.setText(word.getTranslation());
		additional.setText(word.getAdditionalInfo());

		variants.removeAllRows();
		for (int i = 0; i < test.getVariants().length; i++) {
			variants.setHTML(i / 2, i % 2, test.getVariants()[i]);
		}
		result = null;
	}

}
