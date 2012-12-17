package ru.abelitsky.memorize.client.place;

import java.util.HashMap;
import java.util.Map;

public class ParameterNames {

	public static final String COURSE_PARAM = "course";
	public static final String RETURN_PARAM = "return";
	public static final String TRAINING_PARAM = "training";
	public static final String MODE_PARAM = "mode";

	// Значения для параметра return
	public static final String ALL_COURSES_PLACE = "all";
	public static final String CURRENT_COURSES_PLACE = "current";

	// Значения для параметра mode
	public static final String ADD_NEW_WORDS = "new";
	public static final String REPEAT_WORDS = "repeat";

	public static Map<String, String> parseParamsToken(String token) {
		Map<String, String> paramsMap = new HashMap<String, String>();
		if ((token != null) && !token.isEmpty()) {
			String[] params = token.split("&");
			for (String param : params) {
				String[] parts = param.split("=");
				if (parts.length == 2) {
					paramsMap.put(parts[0], parts[1]);
				}
			}
		}
		return paramsMap;
	}

}
