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
	List<TrainingTest> addNewWordsForTraining(Long courseStatusId);

}
