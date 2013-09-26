package ru.abelitsky.memorize.client.activity;

import java.util.List;

import ru.abelitsky.memorize.client.ClientFactory;
import ru.abelitsky.memorize.client.place.CurrentCoursesPlace;
import ru.abelitsky.memorize.client.place.ParameterNames;
import ru.abelitsky.memorize.client.place.TrainingPlace;
import ru.abelitsky.memorize.client.place.ViewCoursePlace;
import ru.abelitsky.memorize.client.view.CurrentCoursesView;
import ru.abelitsky.memorize.client.view.CurrentCoursesView.CurrentCoursesPresenter;
import ru.abelitsky.memorize.shared.dto.CourseInfo;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class CurrentCoursesActivity extends AbstractActivity implements CurrentCoursesPresenter {

	private Timer refreshTimer;

	public CurrentCoursesActivity(CurrentCoursesPlace place) {
	}

	@Override
	public void goTo(Place place) {
		ClientFactory.INSTANCE.getPlaceController().goTo(place);
	}

	private void refreshCurrentCourses() {
		ClientFactory.INSTANCE.getCoursesService().getStatuses(
				new AsyncCallback<List<CourseInfo>>() {
					public void onFailure(Throwable caught) {
						ClientFactory.INSTANCE.getRPCFaultDialog().show(caught);
					}

					public void onSuccess(List<CourseInfo> courseInfo) {
						ClientFactory.INSTANCE.getCurrentCoursesView().setData(courseInfo);
					}
				});
	}

	@Override
	public void onStop() {
		super.onStop();
		if (refreshTimer != null) {
			refreshTimer.cancel();
		}
		refreshTimer = null;
	}

	@Override
	public void selectCourse(CourseInfo courseInfo) {
		goTo(new ViewCoursePlace(courseInfo.getCourse().getId(), new CurrentCoursesPlace()));
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		CurrentCoursesView view = ClientFactory.INSTANCE.getCurrentCoursesView();
		view.setPresenter(this);
		panel.setWidget(view);

		refreshCurrentCourses();

		refreshTimer = new Timer() {
			@Override
			public void run() {
				refreshCurrentCourses();
			}
		};
		refreshTimer.scheduleRepeating(10 * 60 * 1000);
	}

	@Override
	public void startTraining(CourseInfo courseInfo) {
		goTo(new TrainingPlace(courseInfo.getStatus().getId(), ParameterNames.REPEAT_WORDS,
				new CurrentCoursesPlace()));
	}

}
