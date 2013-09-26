package ru.abelitsky.memorize.client.activity;

import ru.abelitsky.memorize.client.ClientFactory;
import ru.abelitsky.memorize.client.place.AllCoursesPlace;
import ru.abelitsky.memorize.client.place.ChangeCoursePlace;
import ru.abelitsky.memorize.client.place.NewCoursePlace;
import ru.abelitsky.memorize.client.view.EditCourseView;
import ru.abelitsky.memorize.client.view.EditCourseView.Presenter;
import ru.abelitsky.memorize.shared.dto.CourseDTO;
import ru.abelitsky.memorize.shared.dto.CourseInfo;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class EditCourseActivity extends AbstractActivity implements Presenter {

	private Long courseId;

	public EditCourseActivity(NewCoursePlace place) {
		this.courseId = null;
	}

	public EditCourseActivity(ChangeCoursePlace place) {
		this.courseId = place.getCourseId();
	}

	@Override
	public void goTo(Place place) {
		ClientFactory.INSTANCE.getPlaceController().goTo(place);
	}

	@Override
	public void save(CourseDTO course) {
		ClientFactory.INSTANCE.getCoursesService().saveCourse(course, new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable caught) {
				ClientFactory.INSTANCE.getRPCFaultDialog().show(caught);
			}

			@Override
			public void onSuccess(Void result) {
				goTo(new AllCoursesPlace());
			}
		});
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		EditCourseView view = ClientFactory.INSTANCE.getEditCourseView();
		view.setPresenter(this);
		panel.setWidget(view);

		if (courseId == null) {
			view.prepareForCreate();
			view.setData(null);
		} else {
			view.prepareForEdit();
			ClientFactory.INSTANCE.getCoursesService().getCourseInfo(courseId,
					new AsyncCallback<CourseInfo>() {
						public void onFailure(Throwable caught) {
							ClientFactory.INSTANCE.getRPCFaultDialog().show(caught);
						}

						public void onSuccess(CourseInfo courseInfo) {
							ClientFactory.INSTANCE.getEditCourseView().setData(
									courseInfo.getCourse());
						}
					});
		}
	}

}
