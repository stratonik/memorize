package ru.abelitsky.memorize.client.widget.training;

import ru.abelitsky.memorize.shared.dto.TrainingTest;

import com.google.gwt.user.client.ui.IsWidget;

public interface TrainingWidget extends IsWidget {

	String KANA_ICON = "/images/kana.png";
	String KANJI_ICON = "/images/kanji.png";

	boolean checkAnswer();

	void setData(TrainingTest test);

	public interface Delegator {

		void onSelectAnswer();

	}

}
