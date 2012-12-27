package ru.abelitsky.memorize.client.widget.training;

import ru.abelitsky.memorize.shared.dto.TrainingTest;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.IsWidget;

public interface TrainingWidget extends IsWidget {

	public interface TrainingWidgetImageBundle extends ClientBundle {

		ImageResource kana();

		ImageResource kanji();

	}

	boolean checkAnswer();

	void setData(TrainingTest test);

	public interface Delegator {

		void onSelectAnswer();

	}

}
