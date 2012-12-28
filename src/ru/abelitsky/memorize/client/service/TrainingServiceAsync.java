package ru.abelitsky.memorize.client.service;

import java.util.List;

import ru.abelitsky.memorize.shared.dto.CourseInfo;
import ru.abelitsky.memorize.shared.dto.TrainingTest;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface TrainingServiceAsync {

	/**
	 * Добавить новые слова в изучение.
	 */
	void addWordsToTraining(Long courseStatusId, AsyncCallback<List<TrainingTest>> callback);

	/**
	 * Проверка слова прошла неудачно.
	 */
	void fail(String wordStatusKey, AsyncCallback<Void> callback);

	/**
	 * Получить слова для повторения.
	 */
	void getWordsForTraining(Long courseStatusId, AsyncCallback<List<TrainingTest>> callback);

	/**
	 * Проверка слова прошла удачно.
	 */
	void pass(String wordStatusKey, AsyncCallback<Void> callback);

	/**
	 * Создает запись о статусе для указанного курса.
	 */
	void startTraining(Long courseId, AsyncCallback<CourseInfo> callback);

	/**
	 * Удаляет запись указанного статуса курса.
	 */
	void stopTraining(Long statusId, AsyncCallback<CourseInfo> callback);

}
