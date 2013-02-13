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

	private ClientFactory clientFactory;
	private ViewCoursePlace place;

	private Timer refreshTimer;

	public ViewCourseActivity(ViewCoursePlace place, ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
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
		clientFactory.getCoursesService().getWords(place.getCourseId(), from + 1, count,
				new AsyncCallback<List<WordDTO>>() {
					public void onFailure(Throwable caught) {
						clientFactory.getRPCFaultDialog().show(caught);
					}

					public void onSuccess(List<WordDTO> words) {
						clientFactory.getViewCourseView().setData(words);
					}
				});
	}

	@Override
	public void goTo(Place place) {
		clientFactory.getPlaceController().goTo(place);
	}

	private void refreshCourseInfo() {
		clientFactory.getCoursesService().getCourseInfo(place.getCourseId(),
				new AsyncCallback<CourseInfo>() {
					public void onFailure(Throwable caught) {
						clientFactory.getRPCFaultDialog().show(caught);
					}

					public void onSuccess(CourseInfo info) {
						clientFactory.getViewCourseView().setData(info);
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
		ViewCourseView view = clientFactory.getViewCourseView();
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
		refreshTimer.scheduleRepeating(60 * 60 * 1000);
	}

	@Override
	public void startCourse(CourseInfo courseInfo) {
		clientFactory.getTrainingService().startTraining(courseInfo.getCourse().getId(),
				new AsyncCallback<CourseInfo>() {
					public void onFailure(Throwable caught) {
						clientFactory.getRPCFaultDialog().show(caught);
					}

					public void onSuccess(CourseInfo info) {
						clientFactory.getViewCourseView().setData(info);
					}
				});
	}

	@Override
	public void startTraining(CourseInfo courseInfo, String mode) {
		goTo(new TrainingPlace(courseInfo.getStatus().getId(), mode, place));
	}

	@Override
	public void stopCourse(CourseInfo courseInfo) {
		clientFactory.getTrainingService().stopTraining(courseInfo.getStatus().getId(),
				new AsyncCallback<CourseInfo>() {
					public void onFailure(Throwable caught) {
						clientFactory.getRPCFaultDialog().show(caught);
					}

					public void onSuccess(CourseInfo info) {
						clientFactory.getViewCourseView().setData(info);
					}
				});
	}

}
