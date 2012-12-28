package ru.abelitsky.memorize.client;

import ru.abelitsky.memorize.client.place.AllCoursesPlace;
import ru.abelitsky.memorize.client.place.ChangeCoursePlace;
import ru.abelitsky.memorize.client.place.CurrentCoursesPlace;
import ru.abelitsky.memorize.client.place.LoadWordsPlace;
import ru.abelitsky.memorize.client.place.NewCoursePlace;
import ru.abelitsky.memorize.client.place.TrainingPlace;
import ru.abelitsky.memorize.client.place.ViewCoursePlace;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;

@WithTokenizers({ AllCoursesPlace.Tokenizer.class, ChangeCoursePlace.Tokenizer.class,
		CurrentCoursesPlace.Tokenizer.class, LoadWordsPlace.Tokenizer.class,
		NewCoursePlace.Tokenizer.class, TrainingPlace.Tokenizer.class,
		ViewCoursePlace.Tokenizer.class })
public interface AppPlaceHistoryMapper extends PlaceHistoryMapper {
}
