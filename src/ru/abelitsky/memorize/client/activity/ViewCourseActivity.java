package ru.abelitsky.memorize.client.activity;

import java.util.List;

import ru.abelitsky.memorize.client.ClientFactory;
import ru.abelitsky.memorize.client.place.AllCoursesPlace;
import ru.abelitsky.memorize.client.place.CurrentCoursesPlace;
import ru.abelitsky.memorize.client.place.LoadWordsPlace;
import ru.abelitsky.memorize.client.place.ViewCoursePlace;
import ru.abelitsky.memorize.client.place.ViewCoursePlace.BackPlace;
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

	private Long courseId;
	private BackPlace backPlace;

	public ViewCourseActivity(ViewCoursePlace place, ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
		this.courseId = place.getCourseId();
		this.backPlace = place.getBackPlace();
	}

	@Override
	public Place getBackPlace() {
		if (backPlace == BackPlace.currentCourses) {
			return new CurrentCoursesPlace();
		} else {
			return new AllCoursesPlace();
		}
	}

	@Override
	public Place getLoadWordsPlace() {
		return new LoadWordsPlace(courseId, backPlace);
	}

	@Override
	public void getWords(int from, int count) {
		clientFactory.getCoursesService().getWords(courseId, from + 1, count,
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

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		ViewCourseView view = clientFactory.getViewCourseView();
		view.setPresenter(this);
		panel.setWidget(view);

		view.prepareView();
		clientFactory.getCoursesService().getCourseInfo(courseId,
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
