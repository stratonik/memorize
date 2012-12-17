package ru.abelitsky.memorize.client.view;

import java.util.List;

import ru.abelitsky.memorize.client.place.AllCoursesPlace;
import ru.abelitsky.memorize.client.place.ChangeCoursePlace;
import ru.abelitsky.memorize.client.place.CurrentCoursesPlace;
import ru.abelitsky.memorize.client.place.NewCoursePlace;
import ru.abelitsky.memorize.client.place.ViewCoursePlace;
import ru.abelitsky.memorize.client.widget.CourseWidget;
import ru.abelitsky.memorize.client.widget.CourseWidget.Delegator;
import ru.abelitsky.memorize.shared.dto.CourseDTO;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLPanel;

public class AllCoursesViewImpl extends Composite implements AllCoursesView,
		Delegator {

	interface AllCoursesViewImplUiBinder extends
			UiBinder<HTMLPanel, AllCoursesViewImpl> {
	}

	private static AllCoursesViewImplUiBinder uiBinder = GWT
			.create(AllCoursesViewImplUiBinder.class);

	@UiField
	FlexTable list;

	private Presenter presenter;

	public AllCoursesViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void changeCourse(CourseDTO course) {
		presenter.goTo(new ChangeCoursePlace(course.getId()));
	}

	@Override
	public void deleteCourse(CourseDTO course) {
		if (Window.confirm("Вы действительно хотите удалить этот курс?")) {
			presenter.deleteCourse(course);
		}
	}

	@UiHandler("currentCourses")
	void onClickCurrentCourses(ClickEvent event) {
		presenter.goTo(new CurrentCoursesPlace());
	}

	@UiHandler("newCourse")
	void onClickNewCourse(ClickEvent event) {
		presenter.goTo(new NewCoursePlace());
	}

	@Override
	public void selectCourse(CourseDTO course) {
		presenter.goTo(new ViewCoursePlace(course.getId(),
				new AllCoursesPlace()));
	}

	@Override
	public void setData(List<CourseDTO> courses) {
		list.removeAllRows();
		if (courses.isEmpty()) {
			list.setHTML(0, 0, "Нет курсов");
		} else {
			for (CourseDTO course : courses) {
				list.setWidget(list.getRowCount(), 0, new CourseWidget(course,
						this));
			}
		}
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

}
