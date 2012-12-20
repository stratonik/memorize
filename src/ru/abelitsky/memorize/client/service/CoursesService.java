package ru.abelitsky.memorize.client.service;

import java.util.List;

import ru.abelitsky.memorize.shared.dto.CourseDTO;
import ru.abelitsky.memorize.shared.dto.CourseInfo;
import ru.abelitsky.memorize.shared.dto.UserInfo;
import ru.abelitsky.memorize.shared.dto.WordDTO;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("courses")
public interface CoursesService extends RemoteService {

	/**
	 * Удаляет указанный курс.
	 * 
	 * @param id
	 *            идентификатор курса
	 * @return список оставшихся курсов
	 */
	List<CourseDTO> deleteCourse(Long id);

	/**
	 * Возвращает указанный курс и дополнительную информацию о нем.
	 * 
	 * @param id
	 *            идентификатор курса
	 * @return информация о курсе
	 */
	CourseInfo getCourseInfo(Long id);

	/**
	 * Возвращает список всех курсов.
	 * 
	 * @return список всех курсов
	 */
	List<CourseDTO> getCourses();

	/**
	 * Возвращает статусы изучаемых курсов.
	 * 
	 * @return статусы изучаемых курсов
	 */
	List<CourseInfo> getStatuses();

	/**
	 * Получить данные по текущему пользователю.
	 * 
	 * @return информация о текущем пользователе, или <code>null</code> если
	 *         пользователь не залогинен.
	 */
	UserInfo getUserInfo();

	/**
	 * Возвращает слова указанного курса.
	 * 
	 * @param courseId
	 *            идентификатор курса
	 * @param beginIndex
	 *            индекс первого слова в списке
	 * @param count
	 *            количество слов в списке
	 * @return список слов
	 */
	List<WordDTO> getWords(Long courseId, int beginIndex, int count);

	/**
	 * Загружает слова для указанного курса.
	 * 
	 * @param courseId
	 *            идентификатор курса
	 * @param data
	 *            список слов в csv-формате
	 */
	void importWords(Long courseId, String data);

	/**
	 * Сохранение описания курса в базе данных.
	 * 
	 * @param course
	 *            курс
	 */
	void saveCourse(CourseDTO course);

}
