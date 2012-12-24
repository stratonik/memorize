package ru.abelitsky.memorize.client.view;

import java.util.List;

import ru.abelitsky.memorize.shared.dto.CourseInfo;
import ru.abelitsky.memorize.shared.dto.WordDTO;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;

public interface ViewCourseView extends IsWidget {

	int WORDS_PER_PAGE = 20;

	void prepareView();

	void setData(CourseInfo data);

	void setData(List<WordDTO> words);

	void setPresenter(Presenter presenter);

	interface Presenter {

		Place getBackPlace();

		Place getLoadWordsPlace();

		void getWords(int from, int count);

		void goTo(Place place);

		void startCourse(CourseInfo courseInfo);

		void startTraining(CourseInfo courseInfo, String mode);

		void stopCourse(CourseInfo courseInfo);

	}
}
