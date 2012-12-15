package ru.abelitsky.memorize.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class AllCoursesPlace extends Place {

	public static class Tokenizer implements PlaceTokenizer<AllCoursesPlace> {

		@Override
		public AllCoursesPlace getPlace(String token) {
			return new AllCoursesPlace();
		}

		@Override
		public String getToken(AllCoursesPlace place) {
			return "";
		}

	}

}
