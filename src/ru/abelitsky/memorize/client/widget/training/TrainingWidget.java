package ru.abelitsky.memorize.client.widget.training;

import ru.abelitsky.memorize.shared.dto.TrainingTest;

import com.google.gwt.user.client.ui.IsWidget;

public interface TrainingWidget extends IsWidget {

	boolean checkAnswer();

	void setData(TrainingTest test);

	public interface Delegator {

		void onSelectAnswer();

	}

}
