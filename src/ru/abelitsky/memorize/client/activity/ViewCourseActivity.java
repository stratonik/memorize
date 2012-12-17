package ru.abelitsky.memorize.client.activity;

import java.util.List;

import ru.abelitsky.memorize.client.ClientFactory;
import ru.abelitsky.memorize.client.place.LoadWordsPlace;
import ru.abelitsky.memorize.client.place.ViewCoursePlace;
import ru.abelitsky.memorize.client.view.ViewCourseView;
import ru.abelitsky.memorize.client.view.ViewCourseView.Presenter;
import ru.abelitsky.memorize.shared.dto.CourseInfo;
import ru.abelitsky.memorize.shared.dto.WordDTO;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class ViewCourseActivity extends AbstractActivity implements Presenter {

	private ClientFactory clientFactory;
	private ViewCoursePlace place;

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
		clientFactory.getCoursesService().getWords(place.getCourseId(),
				from + 1, count, new AsyncCallback<List<WordDTO>>() {
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

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		ViewCourseView view = clientFactory.getViewCourseView();
		view.setPresenter(this);
		panel.setWidget(view);

		view.prepareView();
		clientFactory.getCoursesService().getCourseInfo(place.getCourseId(),
				new AsyncCallback<CourseInfo>() {
					public void onFailure(Throwable caught) {
						clientFactory.getRPCFaultDialog().show(caught);
					}

					public void onSuccess(CourseInfo info) {
						clientFactory.getViewCourseView().setData(info);
					}
				});
		getWords(0, ViewCourseView.WORDS_PER_PAGE);
	}

	@Override
	public void startCourse(CourseInfo courseInfo) {
		clientFactory.getCoursesService().createCourseStatus(
				courseInfo.getCourse().getId(),
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
	public void stopCourse(CourseInfo courseInfo) {
		clientFactory.getCoursesService().deleteCourseStatus(
				courseInfo.getStatus().getId(),
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
