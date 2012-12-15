package ru.abelitsky.memorize.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class ViewCoursePlace extends Place {

	public enum BackPlace {
		allCourses, currentCourses
	};

	private final Long courseId;
	private final BackPlace backPlace;

	public ViewCoursePlace(Long courseId, BackPlace backPlace) {
		this.courseId = courseId;
		this.backPlace = backPlace;
	}

	public BackPlace getBackPlace() {
		return backPlace;
	}

	public Long getCourseId() {
		return courseId;
	}

	public static class Tokenizer implements PlaceTokenizer<ViewCoursePlace> {

		@Override
		public ViewCoursePlace getPlace(String token) {
			String[] params = token.split("&");
			BackPlace backPlace = BackPlace.currentCourses;
			if (params.length > 1) {
				backPlace = BackPlace.valueOf(params[1]);
			}
			return new ViewCoursePlace(Long.parseLong(params[0]), backPlace);
		}

		@Override
		public String getToken(ViewCoursePlace place) {
			return String.valueOf(place.getCourseId()) + "&"
					+ place.getBackPlace().name();
		}

	}

}
