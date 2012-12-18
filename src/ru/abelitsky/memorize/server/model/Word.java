package ru.abelitsky.memorize.server.model;

import ru.abelitsky.memorize.shared.dto.WordDTO;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;

@Entity
public class Word {

	@Id
	private String id;
	@Parent
	private Ref<Course> course;
	@Index
	private Integer index;
	private String kana;
	private String kanji;
	private String translation;
	private String additionalInfo;

	public Word() {
	}

	public Word(Key<Course> course) {
		setCourse(course);
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public Course getCourse() {
		return course.get();
	}

	public String getId() {
		return id;
	}

	public Integer getIndex() {
		return index;
	}

	public String getKana() {
		return kana;
	}

	public String getKanji() {
		return kanji;
	}

	public String getTranslation() {
		return translation;
	}

	public boolean hasKanji() {
		return (kanji != null) && !kanji.trim().isEmpty();
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	public void setCourse(Course course) {
		this.course = Ref.create(course);
	}

	public void setCourse(Key<Course> courseKey) {
		this.course = Ref.create(courseKey);
	}

	public void setCourse(Ref<Course> course) {
		this.course = course;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public void setKana(String kana) {
		this.kana = kana;
		updateId();
	}

	public void setKanji(String kanji) {
		this.kanji = kanji;
		updateId();
	}

	public void setTranslation(String translation) {
		this.translation = translation;
	}

	public WordDTO toDto() {
		WordDTO dto = new WordDTO();
		dto.setId(getId());
		dto.setIndex(getIndex());
		dto.setKana(getKana());
		dto.setKanji(getKanji());
		dto.setTranslation(getTranslation());
		dto.setAdditionalInfo(getAdditionalInfo());
		return dto;
	}

	private void updateId() {
		id = ((getKana() != null) ? getKana() : "") + ":"
				+ ((getKanji() != null) ? getKanji() : "");
	}
}
