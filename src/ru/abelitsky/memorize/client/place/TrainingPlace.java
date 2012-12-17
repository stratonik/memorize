package ru.abelitsky.memorize.client.place;

import java.util.Map;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class TrainingPlace extends Place {

	private final Long courseStatusId;
	private final String mode;
	private final ViewCoursePlace viewCoursePlace;

	public TrainingPlace(Long courseStatusId, String mode,
			ViewCoursePlace backPlace) {
		this.courseStatusId = courseStatusId;
		this.mode = mode;
		this.viewCoursePlace = backPlace;
	}

	public ViewCoursePlace getBackPlace() {
		return viewCoursePlace;
	}

	public Long getCourseStatusId() {
		return courseStatusId;
	}

	public String getMode() {
		return mode;
	}

	public static class Tokenizer implements PlaceTokenizer<TrainingPlace> {

		@Override
		public TrainingPlace getPlace(String token) {
			Map<String, String> params = ParameterNames.parseParamsToken(token);
			ViewCoursePlace.Tokenizer tokenizer = new ViewCoursePlace.Tokenizer();
			return new TrainingPlace(Long.parseLong(params
					.get(ParameterNames.TRAINING_PARAM)),
					params.get(ParameterNames.MODE_PARAM),
					tokenizer.getPlace(token));
		}

		@Override
		public String getToken(TrainingPlace place) {
			ViewCoursePlace.Tokenizer tokenizer = new ViewCoursePlace.Tokenizer();
			return ParameterNames.TRAINING_PARAM + "="
					+ place.getCourseStatusId() + "&"
					+ ParameterNames.MODE_PARAM + "=" + place.getMode() + "&"
					+ tokenizer.getToken(place.getBackPlace());
		}
	}

}
