package ru.abelitsky.memorize.client.view;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;

public interface LoadWordsView extends IsWidget {

	void prepareView();

	void setPresenter(Presenter presenter);

	interface Presenter {

		void load(String data);

		Place getBackPlace();

		void goTo(Place place);

	}

}
