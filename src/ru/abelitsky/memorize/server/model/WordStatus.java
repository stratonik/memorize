package ru.abelitsky.memorize.server.model;

import java.util.Date;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.annotation.Parent;

@Entity
public class WordStatus {

	@Id
	private Long id;
	@Parent
	private Ref<CourseStatus> courseStatus;
	@Load
	private Ref<Word> word;
	@Index
	private Date nextTrainingDate;
	private int level;
	private int subLevel;

	WordStatus() {
	}

	public WordStatus(CourseStatus courseStatus, Word word) {
		setCourseStatus(courseStatus);
		setWord(word);
	}

	public CourseStatus getCourseStatus() {
		return courseStatus.get();
	}

	public Long getId() {
		return id;
	}

	public int getLevel() {
		return level;
	}

	public Date getNextTrainingDate() {
		return nextTrainingDate;
	}

	public int getSubLevel() {
		return subLevel;
	}

	public Word getWord() {
		return word.get();
	}

	public void setCourseStatus(CourseStatus status) {
		this.courseStatus = Ref.create(status);
	}

	public void setCourseStatus(Key<CourseStatus> status) {
		this.courseStatus = Ref.create(status);
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setNextTrainingDate(Date nextTrainingDate) {
		this.nextTrainingDate = nextTrainingDate;
	}

	public void setSubLevel(int subLevel) {
		this.subLevel = subLevel;
	}

	public void setWord(Key<Word> word) {
		this.word = Ref.create(word);
	}

	public void setWord(Word word) {
		this.word = Ref.create(word);
	}

}
