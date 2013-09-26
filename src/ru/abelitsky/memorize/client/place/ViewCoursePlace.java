package ru.abelitsky.memorize.client.place;

import java.util.Map;

import ru.abelitsky.memorize.client.ClientFactory;

import com.google.gwt.http.client.URL;
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
			return new ViewCoursePlace(Long.parseLong(params.get(ParameterNames.COURSE_PARAM)),
					ClientFactory.INSTANCE.getPlaceHistoryMapper().getPlace(
							URL.decodeQueryString(params.get(ParameterNames.RETURN_PARAM))));
		}

		@Override
		public String getToken(ViewCoursePlace place) {
			return ParameterNames.COURSE_PARAM
					+ "="
					+ place.getCourseId()
					+ "&"
					+ ParameterNames.RETURN_PARAM
					+ "="
					+ URL.encodeQueryString(ClientFactory.INSTANCE.getPlaceHistoryMapper()
							.getToken(place.getBackPlace()));
		}

	}

}
