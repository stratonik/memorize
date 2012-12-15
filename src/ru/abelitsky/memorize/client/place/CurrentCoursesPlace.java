package ru.abelitsky.memorize.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class CurrentCoursesPlace extends Place {

	public static class Tokenizer implements
			PlaceTokenizer<CurrentCoursesPlace> {

		@Override
		public CurrentCoursesPlace getPlace(String token) {
			return new CurrentCoursesPlace();
		}

		@Override
		public String getToken(CurrentCoursesPlace place) {
			return "";
		}

	}

}
