package ru.abelitsky.memorize.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class NewCoursePlace extends Place {

	public static class Tokenizer implements PlaceTokenizer<NewCoursePlace> {

		@Override
		public NewCoursePlace getPlace(String token) {
			return new NewCoursePlace();
		}

		@Override
		public String getToken(NewCoursePlace place) {
			return "";
		}

	}

}
