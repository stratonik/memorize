package ru.abelitsky.memorize.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class CourseDTO implements IsSerializable, Comparable<CourseDTO> {

	private Long id;
	private String name;
	private String description;
	private int wordsNumber;

	@Override
	public int compareTo(CourseDTO o) {
		return getName().compareToIgnoreCase(o.getName());
	}

	public String getDescription() {
		return description;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getWordsNumber() {
		return wordsNumber;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setWordsNumber(int wordsNumber) {
		this.wordsNumber = wordsNumber;
	}

}