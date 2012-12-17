package ru.abelitsky.memorize.client.place;

import java.util.Map;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class ViewCoursePlace extends Place {

	private final Long courseId;
	private final Place backPlace;

	public ViewCoursePlace(Long courseId, Place backPlace) {
		this.courseId = courseId;
		this.backPlace = backPlace;
	}

	public Place getBackPlace() {
		return backPlace;
	}

	public Long getCourseId() {
		return courseId;
	}

	public static class Tokenizer implements PlaceTokenizer<ViewCoursePlace> {

		@Override
		public ViewCoursePlace getPlace(String token) {
			Map<String, String> params = ParameterNames.parseParamsToken(token);

			Place backPlace;
			String returnParam = params.get(ParameterNames.RETURN_PARAM);
			if (ParameterNames.ALL_COURSES_PLACE.equals(returnParam)) {
				backPlace = new AllCoursesPlace();
			} else {
				backPlace = new CurrentCoursesPlace();
			}

			return new ViewCoursePlace(Long.parseLong(params
					.get(ParameterNames.COURSE_PARAM)), backPlace);
		}

		@Override
		public String getToken(ViewCoursePlace place) {
			String backPlaceString;
			if (place.getBackPlace() instanceof AllCoursesPlace) {
				backPlaceString = ParameterNames.ALL_COURSES_PLACE;
			} else {
				backPlaceString = ParameterNames.CURRENT_COURSES_PLACE;
			}
			return ParameterNames.COURSE_PARAM + "=" + place.getCourseId()
					+ "&" + ParameterNames.RETURN_PARAM + "=" + backPlaceString;
		}

	}

}
