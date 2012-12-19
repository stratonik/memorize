package ru.abelitsky.memorize.server.model;

import ru.abelitsky.memorize.shared.dto.CourseStatusDTO;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;

@Entity
public class CourseStatus {

	@Id
	private Long id;
	@Index
	private User user;
	@Load
	@Index
	private Ref<Course> course;
	private int knownWordsNumber;

	CourseStatus() {
	}

	public CourseStatus(Key<Course> course) {
		setCourse(course);
		setUser(UserServiceFactory.getUserService().getCurrentUser());
	}

	public void fromDto(CourseStatusDTO dto) {
		setKnownWordsNumber(dto.getKnownWordsNumber());
	}

	public Course getCourse() {
		return course.get();
	}

	public Long getId() {
		return id;
	}

	public int getKnownWordsNumber() {
		return knownWordsNumber;
	}

	public User getUser() {
		return user;
	}

	public void setCourse(Course course) {
		this.course = Ref.create(course);
	}

	public void setCourse(Key<Course> courseKey) {
		this.course = Ref.create(courseKey);
	}

	public void setKnownWordsNumber(int knownWordsNumber) {
		this.knownWordsNumber = knownWordsNumber;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public CourseStatusDTO toDto() {
		CourseStatusDTO dto = new CourseStatusDTO();
		dto.setId(getId());
		dto.setKnownWordsNumber(getKnownWordsNumber());
		return dto;
	}
}
