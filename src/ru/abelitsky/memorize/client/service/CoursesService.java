package ru.abelitsky.memorize.client.service;

import java.util.List;

import ru.abelitsky.memorize.shared.dto.CourseDTO;
import ru.abelitsky.memorize.shared.dto.CourseInfo;
import ru.abelitsky.memorize.shared.dto.WordDTO;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("courses")
public interface CoursesService extends RemoteService {

	/**
	 * Создает запись о статусе для указанного курса.
	 * 
	 * @param courseId
	 *            идентификатор курса
	 * @return информация о курсе
	 */
	CourseInfo createCourseStatus(Long courseId);

	/**
	 * Удаляет указанный курс.
	 * 
	 * @param id
	 *            идентификатор курса
	 * @return список оставшихся курсов
	 */
	List<CourseDTO> deleteCourse(Long id);

	/**
	 * Удаляет запись указанного статуса курса.
	 * 
	 * @param statusId
	 *            идентификатор статуса
	 * @return информация о курсе
	 */
	CourseInfo deleteCourseStatus(Long statusId);

	/**
	 * Возвращает указанный курс.
	 * 
	 * @param id
	 *            идентификатор курса
	 * @return курс
	 */
	CourseDTO getCourse(Long id);

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
	void loadWords(Long courseId, String data);

	/**
	 * Сохранение описания курса в базе данных.
	 * 
	 * @param course
	 *            курс
	 */
	void saveCourse(CourseDTO course);

}
