package ru.abelitsky.memorize.client.place;

import ru.abelitsky.memorize.client.place.ViewCoursePlace.BackPlace;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class LoadWordsPlace extends Place {

	private final Long courseId;
	private final BackPlace backPlace;

	public LoadWordsPlace(Long courseId, BackPlace backPlace) {
		this.courseId = courseId;
		this.backPlace = backPlace;
	}

	public BackPlace getBackPlace() {
		return backPlace;
	}

	public Long getCourseId() {
		return courseId;
	}

	public static class Tokenizer implements PlaceTokenizer<LoadWordsPlace> {

		@Override
		public LoadWordsPlace getPlace(String token) {
			String[] params = token.split("&");
			BackPlace backPlace = BackPlace.currentCourses;
			if (params.length > 1) {
				backPlace = BackPlace.valueOf(params[1]);
			}
			return new LoadWordsPlace(Long.parseLong(params[0]), backPlace);
		}

		@Override
		public String getToken(LoadWordsPlace place) {
			return String.valueOf(place.getCourseId()) + "&"
					+ place.getBackPlace().name();
		}

	}

}
