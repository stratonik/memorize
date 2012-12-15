package ru.abelitsky.memorize.server;

import ru.abelitsky.memorize.server.model.Course;
import ru.abelitsky.memorize.server.model.CourseStatus;
import ru.abelitsky.memorize.server.model.Word;
import ru.abelitsky.memorize.server.model.WordStatus;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

public class OfyService {

	static {
		ObjectifyService.register(Course.class);
		ObjectifyService.register(Word.class);
		ObjectifyService.register(CourseStatus.class);
		ObjectifyService.register(WordStatus.class);
	}

	public static ObjectifyFactory factory() {
		return ObjectifyService.factory();
	}

	public static Objectify ofy() {
		return ObjectifyService.ofy();
	}

}
