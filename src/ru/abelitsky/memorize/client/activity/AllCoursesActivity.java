package ru.abelitsky.memorize.client.activity;

import java.util.List;

import ru.abelitsky.memorize.client.ClientFactory;
import ru.abelitsky.memorize.client.place.AllCoursesPlace;
import ru.abelitsky.memorize.client.view.AllCoursesView;
import ru.abelitsky.memorize.client.view.AllCoursesView.Presenter;
import ru.abelitsky.memorize.shared.dto.CourseDTO;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class AllCoursesActivity extends AbstractActivity implements Presenter {

	private ClientFactory clientFactory;

	public AllCoursesActivity(AllCoursesPlace place, ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		AllCoursesView view = clientFactory.getAllCoursesView();
		view.setPresenter(this);
		panel.setWidget(view);

		clientFactory.getCoursesService().getCourses(
				new AsyncCallback<List<CourseDTO>>() {
					public void onFailure(Throwable caught) {
						clientFactory.getRPCFaultDialog().show(caught);
					}

					public void onSuccess(List<CourseDTO> courses) {
						clientFactory.getAllCoursesView().setData(courses);
					}
				});
	}

	@Override
	public void deleteCourse(CourseDTO course) {
		clientFactory.getCoursesService().deleteCourse(course.getId(),
				new AsyncCallback<List<CourseDTO>>() {
					public void onFailure(Throwable caught) {
						clientFactory.getRPCFaultDialog().show(caught);
					}

					public void onSuccess(List<CourseDTO> courses) {
						clientFactory.getAllCoursesView().setData(courses);
					}
				});
	}

	@Override
	public void goTo(Place place) {
		clientFactory.getPlaceController().goTo(place);
	}

}
