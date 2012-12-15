package ru.abelitsky.memorize.client.view;

import ru.abelitsky.memorize.client.place.AllCoursesPlace;
import ru.abelitsky.memorize.shared.dto.CourseDTO;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class EditCourseViewImpl extends Composite implements EditCourseView {

	interface EditCourseViewImplUiBinder extends
			UiBinder<VerticalPanel, EditCourseViewImpl> {
	}

	private static EditCourseViewImplUiBinder uiBinder = GWT
			.create(EditCourseViewImplUiBinder.class);

	private Presenter presenter;

	private CourseDTO course;

	@UiField
	Label title;
	@UiField
	TextBox name;
	@UiField
	TextArea description;
	@UiField
	Button save;

	public EditCourseViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiHandler("cancel")
	void onClickCancel(ClickEvent event) {
		RootPanel.get("global-error").clear();
		presenter.goTo(new AllCoursesPlace());
	}

	@UiHandler("save")
	void onClickSave(ClickEvent event) {
		RootPanel.get("global-error").clear();
		if (name.getText().trim().isEmpty()) {
			RootPanel.get("global-error").add(
					new HTML("Наименование не может быть пустым."));
		} else {
			course.setName(name.getText());
			course.setDescription(description.getText());
			presenter.save(course);
		}
	}

	@Override
	public void prepareForCreate() {
		title.setText("Создание курса");
		save.setText("Создать");

		name.setText("");
		description.setText("");
	}

	@Override
	public void prepareForEdit() {
		title.setText("Редактирование курса");
		save.setText("Сохранить");

		name.setText("");
		description.setText("");
	}

	@Override
	public void setData(CourseDTO course) {
		if (course == null) {
			course = new CourseDTO();
		}
		this.course = course;

		name.setText(course.getName());
		description.setText(course.getDescription());

		name.setFocus(true);
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

}
