package ru.abelitsky.memorize.client.widget;

import ru.abelitsky.memorize.client.UserService;
import ru.abelitsky.memorize.shared.dto.CourseDTO;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CellPanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;

public class CourseWidget extends Composite {

	public interface Delegator {

		void selectCourse(CourseDTO course);

		void changeCourse(CourseDTO course);

		void deleteCourse(CourseDTO course);

	}

	interface CourseWidgetUiBinder extends UiBinder<CellPanel, CourseWidget> {
	}

	private static CourseWidgetUiBinder uiBinder = GWT
			.create(CourseWidgetUiBinder.class);

	@UiField
	Anchor title;
	@UiField
	Label words;
	@UiField
	Label description;
	@UiField
	Button changeCourse;
	@UiField
	Button deleteCourse;

	private final CourseDTO course;
	private final Delegator delegator;

	public CourseWidget(CourseDTO course, Delegator delegator) {
		this.course = course;
		this.delegator = delegator;

		initWidget(uiBinder.createAndBindUi(this));

		title.setText(course.getName());
		words.setText("Слов: " + course.getWordsNumber());
		description.setText(course.getDescription());

		changeCourse.setVisible(UserService.getCurrentUserInfo().isAdmin());
		deleteCourse.setVisible(UserService.getCurrentUserInfo().isAdmin());
	}

	@UiHandler("changeCourse")
	void onClickChangeCourse(ClickEvent event) {
		delegator.changeCourse(course);
	}

	@UiHandler("deleteCourse")
	void onClickDeleteCourse(ClickEvent event) {
		delegator.deleteCourse(course);
	}

	@UiHandler("title")
	void onClickTitle(ClickEvent event) {
		delegator.selectCourse(course);
	}
}
