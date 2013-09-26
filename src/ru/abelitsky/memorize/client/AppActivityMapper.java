package ru.abelitsky.memorize.client;

import ru.abelitsky.memorize.client.activity.AllCoursesActivity;
import ru.abelitsky.memorize.client.activity.CurrentCoursesActivity;
import ru.abelitsky.memorize.client.activity.EditCourseActivity;
import ru.abelitsky.memorize.client.activity.LoadWordsActivity;
import ru.abelitsky.memorize.client.activity.TrainingActivity;
import ru.abelitsky.memorize.client.activity.ViewCourseActivity;
import ru.abelitsky.memorize.client.place.AllCoursesPlace;
import ru.abelitsky.memorize.client.place.ChangeCoursePlace;
import ru.abelitsky.memorize.client.place.CurrentCoursesPlace;
import ru.abelitsky.memorize.client.place.LoadWordsPlace;
import ru.abelitsky.memorize.client.place.NewCoursePlace;
import ru.abelitsky.memorize.client.place.TrainingPlace;
import ru.abelitsky.memorize.client.place.ViewCoursePlace;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

public class AppActivityMapper implements ActivityMapper {

	@Override
	public Activity getActivity(Place place) {
		if (place instanceof CurrentCoursesPlace) {
			return new CurrentCoursesActivity((CurrentCoursesPlace) place);
		} else if (place instanceof AllCoursesPlace) {
			return new AllCoursesActivity((AllCoursesPlace) place);
		} else if (place instanceof NewCoursePlace) {
			return new EditCourseActivity((NewCoursePlace) place);
		} else if (place instanceof ChangeCoursePlace) {
			return new EditCourseActivity((ChangeCoursePlace) place);
		} else if (place instanceof ViewCoursePlace) {
			return new ViewCourseActivity((ViewCoursePlace) place);
		} else if (place instanceof LoadWordsPlace) {
			return new LoadWordsActivity((LoadWordsPlace) place);
		} else if (place instanceof TrainingPlace) {
			return new TrainingActivity((TrainingPlace) place);
		}
		return new CurrentCoursesActivity(new CurrentCoursesPlace());
	}

}
