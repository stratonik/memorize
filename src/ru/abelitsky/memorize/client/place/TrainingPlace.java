package ru.abelitsky.memorize.client.place;

import java.util.Map;

import ru.abelitsky.memorize.client.AppPlaceHistoryMapper;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.http.client.URL;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class TrainingPlace extends Place {

	private final Long courseStatusId;
	private final String mode;
	private final Place backPlace;

	public TrainingPlace(Long courseStatusId, String mode, Place backPlace) {
		this.courseStatusId = courseStatusId;
		this.mode = mode;
		this.backPlace = backPlace;
	}

	public Place getBackPlace() {
		return backPlace;
	}

	public Long getCourseStatusId() {
		return courseStatusId;
	}

	public String getMode() {
		return mode;
	}

	public static class Tokenizer implements PlaceTokenizer<TrainingPlace> {

		private static final AppPlaceHistoryMapper tokenizer = GWT
				.create(AppPlaceHistoryMapper.class);

		@Override
		public TrainingPlace getPlace(String token) {
			Map<String, String> params = ParameterNames.parseParamsToken(token);
			return new TrainingPlace(Long.parseLong(params.get(ParameterNames.TRAINING_PARAM)),
					params.get(ParameterNames.MODE_PARAM), tokenizer.getPlace(URL
							.decodeQueryString(params.get(ParameterNames.RETURN_PARAM))));
		}

		@Override
		public String getToken(TrainingPlace place) {
			return ParameterNames.TRAINING_PARAM + "=" + place.getCourseStatusId() + "&"
					+ ParameterNames.MODE_PARAM + "=" + place.getMode() + "&"
					+ ParameterNames.RETURN_PARAM + "="
					+ URL.encodeQueryString(tokenizer.getToken(place.getBackPlace()));
		}

	}

}
