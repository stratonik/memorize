package ru.abelitsky.memorize.client.service;

import java.util.List;

import ru.abelitsky.memorize.shared.dto.TrainingTest;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface TrainingServiceAsync {

	/**
	 * Добавить новые слова в изучение.
	 */
	void addNewWordsForTraining(Long courseStatusId,
			AsyncCallback<List<TrainingTest>> callback);

}
