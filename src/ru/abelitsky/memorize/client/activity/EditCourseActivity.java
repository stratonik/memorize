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

	private ClientFactory clientFactory;

	private Long courseId;

	public EditCourseActivity(NewCoursePlace place, ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
		this.courseId = null;
	}

	public EditCourseActivity(ChangeCoursePlace place, ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
		this.courseId = place.getCourseId();
	}

	@Override
	public void goTo(Place place) {
		clientFactory.getPlaceController().goTo(place);
	}

	@Override
	public void save(CourseDTO course) {
		clientFactory.getCoursesService().saveCourse(course, new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable caught) {
				clientFactory.getRPCFaultDialog().show(caught);
			}

			@Override
			public void onSuccess(Void result) {
				goTo(new AllCoursesPlace());
			}
		});
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		EditCourseView view = clientFactory.getEditCourseView();
		view.setPresenter(this);
		panel.setWidget(view);

		if (courseId == null) {
			view.prepareForCreate();
			view.setData(null);
		} else {
			view.prepareForEdit();
			clientFactory.getCoursesService().getCourseInfo(courseId,
					new AsyncCallback<CourseInfo>() {
						public void onFailure(Throwable caught) {
							clientFactory.getRPCFaultDialog().show(caught);
						}

						public void onSuccess(CourseInfo courseInfo) {
							clientFactory.getEditCourseView().setData(courseInfo.getCourse());
						}
					});
		}
	}

}
