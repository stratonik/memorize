package ru.abelitsky.memorize.client.view;

import ru.abelitsky.memorize.shared.dto.CourseDTO;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;

public interface EditCourseView extends IsWidget {

	void prepareForCreate();

	void prepareForEdit();

	void setData(CourseDTO course);

	void setPresenter(Presenter presenter);

	interface Presenter {

		void save(CourseDTO course);

		void goTo(Place place);

	}
}
