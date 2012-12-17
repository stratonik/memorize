package ru.abelitsky.memorize.client.place;

import ru.abelitsky.memorize.client.place.ViewCoursePlace.BackPlace;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class TrainingPlace extends Place {

	private final Long courseStatusId;
	private final Long courseId;
	private final BackPlace backPlace;

	public TrainingPlace(Long courseStatusId, Long courseId, BackPlace backPlace) {
		this.courseStatusId = courseStatusId;
		this.courseId = courseId;
		this.backPlace = backPlace;
	}

	public BackPlace getBackPlace() {
		return backPlace;
	}

	public Long getCourseId() {
		return courseId;
	}

	public Long getCourseStatusId() {
		return courseStatusId;
	}

	public static class Tokenizer implements PlaceTokenizer<TrainingPlace> {

		@Override
		public TrainingPlace getPlace(String token) {
			String[] params = token.split("&");
			BackPlace backPlace = BackPlace.currentCourses;
			if (params.length > 1) {
				backPlace = BackPlace.valueOf(params[1]);
			}
			return new TrainingPlace(Long.parseLong(params[0]),
					Long.parseLong(params[1]), backPlace);
		}

		@Override
		public String getToken(TrainingPlace place) {
			return String.valueOf(place.getCourseStatusId()) + "&"
					+ String.valueOf(place.getCourseId()) + "&"
					+ place.getBackPlace().name();
		}

	}

}
