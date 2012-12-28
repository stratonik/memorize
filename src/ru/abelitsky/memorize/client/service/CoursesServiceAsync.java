package ru.abelitsky.memorize.client.service;

import java.util.List;

import ru.abelitsky.memorize.shared.dto.CourseDTO;
import ru.abelitsky.memorize.shared.dto.CourseInfo;
import ru.abelitsky.memorize.shared.dto.UserInfo;
import ru.abelitsky.memorize.shared.dto.WordDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface CoursesServiceAsync {

	/**
	 * Удаляет указанный курс.
	 */
	void deleteCourse(Long id, AsyncCallback<List<CourseDTO>> callback);

	/**
	 * Возвращает указанный курс и дополнительную информацию о нем.
	 */
	void getCourseInfo(Long id, AsyncCallback<CourseInfo> callback);

	/**
	 * Возвращает список всех курсов.
	 */
	void getCourses(AsyncCallback<List<CourseDTO>> callback);

	/**
	 * Возвращает статусы изучаемых курсов.
	 */
	void getStatuses(AsyncCallback<List<CourseInfo>> callback);

	/**
	 * Получить данные по текущему пользователю.
	 */
	void getUserInfo(AsyncCallback<UserInfo> callback);

	/**
	 * Возвращает слова указанного курса.
	 */
	void getWords(Long courseId, int beginIndex, int count, AsyncCallback<List<WordDTO>> callback);

	/**
	 * Загружает слова для указанного курса.
	 */
	void importWords(Long courseId, String data, AsyncCallback<Void> callback);

	/**
	 * Сохраняет курс в базе данных.
	 */
	void saveCourse(CourseDTO course, AsyncCallback<Void> callback);

}
