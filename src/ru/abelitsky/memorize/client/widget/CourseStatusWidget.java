package ru.abelitsky.memorize.client.widget;

import ru.abelitsky.memorize.shared.dto.CourseInfo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CourseStatusWidget extends Composite {

	public interface Delegator {

		void selectCourse(CourseInfo course);

	}

	interface CourseStatusWidgetUiBinder extends
			UiBinder<VerticalPanel, CourseStatusWidget> {
	}

	private static CourseStatusWidgetUiBinder uiBinder = GWT
			.create(CourseStatusWidgetUiBinder.class);

	@UiField
	Anchor title;
	@UiField
	Label status;
	@UiField
	Label description;

	private final CourseInfo course;
	private final Delegator delegator;

	public CourseStatusWidget(CourseInfo courseInfo, Delegator delegator) {
		this.course = courseInfo;
		this.delegator = delegator;

		initWidget(uiBinder.createAndBindUi(this));

		title.setText(courseInfo.getCourse().getName());
		status.setText(NumberFormat.getFormat("0.0%").format(
				getCompleteProcent(courseInfo))
				+ " ("
				+ courseInfo.getStatus().getKnownWordsNumber()
				+ " из "
				+ courseInfo.getCourse().getWordsNumber() + ")");
		description.setText(courseInfo.getCourse().getDescription());
	}

	private double getCompleteProcent(CourseInfo courseInfo) {
		if (courseInfo.getCourse().getWordsNumber() > 0) {
			return ((double) courseInfo.getStatus().getKnownWordsNumber())
					/ ((double) courseInfo.getCourse().getWordsNumber());
		} else {
			return 0.0;
		}
	}

	@UiHandler("title")
	void onClickTitle(ClickEvent event) {
		delegator.selectCourse(course);
	}

}
