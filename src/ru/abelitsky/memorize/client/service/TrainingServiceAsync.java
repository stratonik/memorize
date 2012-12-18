package ru.abelitsky.memorize.client.service;

import java.util.List;

import ru.abelitsky.memorize.shared.dto.TrainingTest;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface TrainingServiceAsync {

	/**
	 * Добавить новые слова в изучение.
	 */
	void addNewWordsToTraining(Long courseStatusId,
			AsyncCallback<List<TrainingTest>> callback);

	/**
	 * Проверка слова прошла неудачно.
	 */
	void fail(Long wordStatusId, AsyncCallback<Void> callback);

	/**
	 * Получить слова для повторения.
	 */
	void getWordsForTraining(Long courseStatusId,
			AsyncCallback<List<TrainingTest>> callback);

	/**
	 * Проверка слова прошла удачно.
	 */
	void pass(Long wordStatusId, AsyncCallback<Void> callback);

}
