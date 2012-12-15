package ru.abelitsky.memorize.server.model;

import ru.abelitsky.memorize.shared.dto.CourseDTO;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Course {

	@Id
	private Long id;
	private String name;
	private String description;
	private int wordsNumber;

	public Course() {
	}

	public Course(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public void fromDto(CourseDTO dto) {
		setName(dto.getName());
		setDescription(dto.getDescription());
		setWordsNumber(dto.getWordsNumber());
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

	public void setName(String name) {
		this.name = name;
	}

	public void setWordsNumber(int wordsNumber) {
		this.wordsNumber = wordsNumber;
	}

	public CourseDTO toDto() {
		CourseDTO dto = new CourseDTO();
		dto.setId(getId());
		dto.setName(getName());
		dto.setDescription(getDescription());
		dto.setWordsNumber(getWordsNumber());
		return dto;
	}
}
