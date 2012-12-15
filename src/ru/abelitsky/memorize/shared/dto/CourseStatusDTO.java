package ru.abelitsky.memorize.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class CourseStatusDTO implements IsSerializable {

	private Long id;
	private int knownWordsNumber;
	private int readyForTrainingWordsNumber;

	public Long getId() {
		return id;
	}

	public int getKnownWordsNumber() {
		return knownWordsNumber;
	}

	public int getReadyForTrainingWordsNumber() {
		return readyForTrainingWordsNumber;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setKnownWordsNumber(int knownWordsNumber) {
		this.knownWordsNumber = knownWordsNumber;
	}

	public void setReadyForTrainingWordsNumber(int readyForTrainingWordsNumber) {
		this.readyForTrainingWordsNumber = readyForTrainingWordsNumber;
	}

}