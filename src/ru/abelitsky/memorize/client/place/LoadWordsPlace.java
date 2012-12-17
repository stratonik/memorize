package ru.abelitsky.memorize.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class LoadWordsPlace extends Place {

	private final ViewCoursePlace viewCoursePlace;

	public LoadWordsPlace(ViewCoursePlace backPlace) {
		this.viewCoursePlace = backPlace;
	}

	public ViewCoursePlace getBackPlace() {
		return viewCoursePlace;
	}

	public Long getCourseId() {
		return viewCoursePlace.getCourseId();
	}

	public static class Tokenizer implements PlaceTokenizer<LoadWordsPlace> {

		@Override
		public LoadWordsPlace getPlace(String token) {
			ViewCoursePlace.Tokenizer tokenizer = new ViewCoursePlace.Tokenizer();
			return new LoadWordsPlace(tokenizer.getPlace(token));
		}

		@Override
		public String getToken(LoadWordsPlace place) {
			ViewCoursePlace.Tokenizer tokenizer = new ViewCoursePlace.Tokenizer();
			return tokenizer.getToken(place.getBackPlace());
		}

	}

}
