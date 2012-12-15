package ru.abelitsky.memorize.client.view;

import java.util.List;

import ru.abelitsky.memorize.shared.dto.CourseDTO;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;

public interface AllCoursesView extends IsWidget {

	void setData(List<CourseDTO> courses);

	void setPresenter(Presenter presenter);

	interface Presenter {
		
		void deleteCourse(CourseDTO course);

		void goTo(Place place);

	}
}
