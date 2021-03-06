package ru.abelitsky.memorize.client.view;

import java.util.List;

import ru.abelitsky.memorize.client.place.AllCoursesPlace;
import ru.abelitsky.memorize.client.widget.CourseStatusWidget;
import ru.abelitsky.memorize.shared.dto.CourseInfo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLPanel;

public class CurrentCoursesViewImpl extends Composite implements CurrentCoursesView {

	interface CurrentCoursesViewImplUiBinder extends UiBinder<HTMLPanel, CurrentCoursesViewImpl> {
	}

	private static CurrentCoursesViewImplUiBinder uiBinder = GWT
			.create(CurrentCoursesViewImplUiBinder.class);

	@UiField
	FlexTable list;

	private CurrentCoursesPresenter presenter;

	public CurrentCoursesViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiHandler("addCourse")
	void onClickAddCourse(ClickEvent e) {
		presenter.goTo(new AllCoursesPlace());
	}

	@Override
	public void setData(List<CourseInfo> info) {
		list.removeAllRows();
		if (info.isEmpty()) {
			list.setHTML(0, 0, "Нет выбранных курсов");
		} else {
			for (CourseInfo courseInfo : info) {
				list.setWidget(list.getRowCount(), 0, new CourseStatusWidget(courseInfo, presenter));
			}
		}
	}

	@Override
	public void setPresenter(CurrentCoursesPresenter presenter) {
		this.presenter = presenter;
	}

}
