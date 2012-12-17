package ru.abelitsky.memorize.client.view;

import java.util.List;

import ru.abelitsky.memorize.shared.dto.TrainingTest;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;

public interface TrainingView extends IsWidget {
	
	void prepareView();

	void setData(List<TrainingTest> data);

	void setPresenter(Presenter presenter);

	interface Presenter {
		
		Place getBackPlace();

		void goTo(Place place);

	}
}
