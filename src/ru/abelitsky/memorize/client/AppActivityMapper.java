package ru.abelitsky.memorize.client;

import ru.abelitsky.memorize.client.activity.AllCoursesActivity;
import ru.abelitsky.memorize.client.activity.CurrentCoursesActivity;
import ru.abelitsky.memorize.client.activity.EditCourseActivity;
import ru.abelitsky.memorize.client.activity.LoadWordsActivity;
import ru.abelitsky.memorize.client.activity.ViewCourseActivity;
import ru.abelitsky.memorize.client.place.AllCoursesPlace;
import ru.abelitsky.memorize.client.place.ChangeCoursePlace;
import ru.abelitsky.memorize.client.place.CurrentCoursesPlace;
import ru.abelitsky.memorize.client.place.LoadWordsPlace;
import ru.abelitsky.memorize.client.place.NewCoursePlace;
import ru.abelitsky.memorize.client.place.ViewCoursePlace;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

public class AppActivityMapper implements ActivityMapper {

	private ClientFactory clientFactory;

	public AppActivityMapper(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	@Override
	public Activity getActivity(Place place) {
		if (place instanceof CurrentCoursesPlace) {
			return new CurrentCoursesActivity((CurrentCoursesPlace) place,
					clientFactory);
		} else if (place instanceof AllCoursesPlace) {
			return new AllCoursesActivity((AllCoursesPlace) place,
					clientFactory);
		} else if (place instanceof NewCoursePlace) {
			return new EditCourseActivity((NewCoursePlace) place, clientFactory);
		} else if (place instanceof ChangeCoursePlace) {
			return new EditCourseActivity((ChangeCoursePlace) place,
					clientFactory);
		} else if (place instanceof ViewCoursePlace) {
			return new ViewCourseActivity((ViewCoursePlace) place,
					clientFactory);
		} else if (place instanceof LoadWordsPlace) {
			return new LoadWordsActivity((LoadWordsPlace) place, clientFactory);
		}
		return new CurrentCoursesActivity(new CurrentCoursesPlace(),
				clientFactory);
	}

}
