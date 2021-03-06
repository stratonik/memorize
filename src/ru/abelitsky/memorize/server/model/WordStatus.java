package ru.abelitsky.memorize.server.model;

import java.util.Calendar;
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

	public void fail() {
		setLevel(1);
		setSubLevel(0);
		setNextTrainingDateInFourHours();
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

	public void pass() {
		if (getLevel() <= 1) {
			setSubLevel(getSubLevel() + 1);	
		} else {
			if (getSubLevel() <= 1) {
				setSubLevel(4);
			} else {
				setSubLevel(5);
			}
		}
		if ((!getWord().hasKanji() && (getSubLevel() >= 2)) || (getSubLevel() >= 5)) {
			setLevel(Math.min(getLevel() + 1, 6));
			setSubLevel((getLevel() <= 1)? 0 : 1);
			setNextTrainingDateForCurrentLevel();
		} else {
			setNextTrainingDateInFourHours();
		}
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

	private void setNextTrainingDateForCurrentLevel() {
		Calendar cal = Calendar.getInstance();
		switch (getLevel()) {
		case 1:
			cal.add(Calendar.DAY_OF_MONTH, 3);
			break;
		case 2:
			cal.add(Calendar.WEEK_OF_YEAR, 1);
			break;
		case 3:
			cal.add(Calendar.WEEK_OF_YEAR, 2);
			break;
		case 4:
			cal.add(Calendar.MONTH, 1);
			break;
		case 5:
			cal.add(Calendar.MONTH, 3);
			break;
		case 6:
			cal.add(Calendar.MONTH, 6);
			break;
		}
		setNextTrainingDate(cal.getTime());
	}

	public void setNextTrainingDateInFourHours() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR_OF_DAY, 4 * Math.max(1, getLevel()));
		setNextTrainingDate(cal.getTime());
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
