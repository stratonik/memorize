package ru.abelitsky.memorize.client.service;

import java.util.List;

import ru.abelitsky.memorize.shared.dto.TrainingTest;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("training")
public interface TrainingService extends RemoteService {

	/**
	 * Добавить новые слова в изучение.
	 * 
	 * @param courseStatusId
	 *            идентификатор статуса прохождения курса
	 * @return список начальных тестов для добавленных слов
	 */
	List<TrainingTest> addNewWordsToTraining(Long courseStatusId);

	/**
	 * Проверка слова прошла неудачно.
	 * 
	 * @param wordStatusId
	 *            идентификатор записи статуса изучения слова
	 */
	void fail(String wordStatusKey);

	/**
	 * Получить слова для повторения.
	 * 
	 * @param courseStatusId
	 *            идентификатор статуса прохождения курса
	 * @return список тестов слов, которые пора повторить
	 */
	List<TrainingTest> getWordsForTraining(Long courseStatusId);

	/**
	 * Проверка слова прошла удачно.
	 * 
	 * @param wordStatusId
	 *            идентификатор записи статуса изучения слова
	 */
	void pass(String wordStatusKey);

}
