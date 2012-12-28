package ru.abelitsky.memorize.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class CourseInfo implements IsSerializable, Comparable<CourseInfo> {

	private CourseDTO course;
	private CourseStatusDTO status;

	CourseInfo() {
	}

	public CourseInfo(CourseDTO course) {
		this.course = course;
	}

	@Override
	public int compareTo(CourseInfo o) {
		return getCourse().getName().compareToIgnoreCase(o.getCourse().getName());
	}

	public CourseDTO getCourse() {
		return course;
	}

	public CourseStatusDTO getStatus() {
		return status;
	}

	public void setStatus(CourseStatusDTO status) {
		this.status = status;
	}

}
