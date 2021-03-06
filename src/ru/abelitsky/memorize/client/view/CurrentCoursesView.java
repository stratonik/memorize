package ru.abelitsky.memorize.client.view;

import java.util.List;

import ru.abelitsky.memorize.client.widget.CourseStatusWidget.CourseStatusWidgetPresenter;
import ru.abelitsky.memorize.shared.dto.CourseInfo;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;

public interface CurrentCoursesView extends IsWidget {

	void setData(List<CourseInfo> data);

	void setPresenter(CurrentCoursesPresenter presenter);

	interface CurrentCoursesPresenter extends CourseStatusWidgetPresenter {

		void goTo(Place place);

	}
	
}
