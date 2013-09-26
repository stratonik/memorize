package ru.abelitsky.memorize.client.activity;

import java.util.List;

import ru.abelitsky.memorize.client.ClientFactory;
import ru.abelitsky.memorize.client.place.LoadWordsPlace;
import ru.abelitsky.memorize.client.place.TrainingPlace;
import ru.abelitsky.memorize.client.place.ViewCoursePlace;
import ru.abelitsky.memorize.client.view.ViewCourseView;
import ru.abelitsky.memorize.client.view.ViewCourseView.Presenter;
import ru.abelitsky.memorize.shared.dto.CourseInfo;
import ru.abelitsky.memorize.shared.dto.WordDTO;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class ViewCourseActivity extends AbstractActivity implements Presenter {

	private ViewCoursePlace place;

	private Timer refreshTimer;

	public ViewCourseActivity(ViewCoursePlace place) {
		this.place = place;
	}

	@Override
	public Place getBackPlace() {
		return place.getBackPlace();
	}

	@Override
	public Place getLoadWordsPlace() {
		return new LoadWordsPlace(place);
	}

	@Override
	public void getWords(int from, int count) {
		ClientFactory.INSTANCE.getCoursesService().getWords(place.getCourseId(), from + 1, count,
				new AsyncCallback<List<WordDTO>>() {
					public void onFailure(Throwable caught) {
						ClientFactory.INSTANCE.getRPCFaultDialog().show(caught);
					}

					public void onSuccess(List<WordDTO> words) {
						ClientFactory.INSTANCE.getViewCourseView().setData(words);
					}
				});
	}

	@Override
	public void goTo(Place place) {
		ClientFactory.INSTANCE.getPlaceController().goTo(place);
	}

	private void refreshCourseInfo() {
		ClientFactory.INSTANCE.getCoursesService().getCourseInfo(place.getCourseId(),
				new AsyncCallback<CourseInfo>() {
					public void onFailure(Throwable caught) {
						ClientFactory.INSTANCE.getRPCFaultDialog().show(caught);
					}

					public void onSuccess(CourseInfo info) {
						ClientFactory.INSTANCE.getViewCourseView().setData(info);
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
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		ViewCourseView view = ClientFactory.INSTANCE.getViewCourseView();
		view.setPresenter(this);
		panel.setWidget(view);

		view.prepareView();
		refreshCourseInfo();
		getWords(0, ViewCourseView.WORDS_PER_PAGE);

		refreshTimer = new Timer() {
			@Override
			public void run() {
				refreshCourseInfo();
			}
		};
		refreshTimer.scheduleRepeating(10 * 60 * 1000);
	}

	@Override
	public void startCourse(CourseInfo courseInfo) {
		ClientFactory.INSTANCE.getTrainingService().startTraining(courseInfo.getCourse().getId(),
				new AsyncCallback<CourseInfo>() {
					public void onFailure(Throwable caught) {
						ClientFactory.INSTANCE.getRPCFaultDialog().show(caught);
					}

					public void onSuccess(CourseInfo info) {
						ClientFactory.INSTANCE.getViewCourseView().setData(info);
					}
				});
	}

	@Override
	public void startTraining(CourseInfo courseInfo, String mode) {
		goTo(new TrainingPlace(courseInfo.getStatus().getId(), mode, place));
	}

	@Override
	public void stopCourse(CourseInfo courseInfo) {
		ClientFactory.INSTANCE.getTrainingService().stopTraining(courseInfo.getStatus().getId(),
				new AsyncCallback<CourseInfo>() {
					public void onFailure(Throwable caught) {
						ClientFactory.INSTANCE.getRPCFaultDialog().show(caught);
					}

					public void onSuccess(CourseInfo info) {
						ClientFactory.INSTANCE.getViewCourseView().setData(info);
					}
				});
	}

}
