package ru.abelitsky.memorize.client.place;

import java.util.Map;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class ChangeCoursePlace extends Place {

	private final Long courseId;

	public ChangeCoursePlace(Long courseId) {
		this.courseId = courseId;
	}

	public Long getCourseId() {
		return courseId;
	}

	public static class Tokenizer implements PlaceTokenizer<ChangeCoursePlace> {

		@Override
		public ChangeCoursePlace getPlace(String token) {
			Map<String, String> params = ParameterNames.parseParamsToken(token);
			return new ChangeCoursePlace(Long.parseLong(params.get(ParameterNames.COURSE_PARAM)));
		}

		@Override
		public String getToken(ChangeCoursePlace place) {
			return ParameterNames.COURSE_PARAM + "=" + place.getCourseId();
		}

	}
}
