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

	public AllCoursesActivity(AllCoursesPlace place) {
	}

	@Override
	public void deleteCourse(CourseDTO course) {
		ClientFactory.INSTANCE.getCoursesService().deleteCourse(course.getId(),
				new AsyncCallback<List<CourseDTO>>() {
					public void onFailure(Throwable caught) {
						ClientFactory.INSTANCE.getRPCFaultDialog().show(caught);
					}

					public void onSuccess(List<CourseDTO> courses) {
						ClientFactory.INSTANCE.getAllCoursesView().setData(courses);
					}
				});
	}

	@Override
	public void goTo(Place place) {
		ClientFactory.INSTANCE.getPlaceController().goTo(place);
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		AllCoursesView view = ClientFactory.INSTANCE.getAllCoursesView();
		view.setPresenter(this);
		panel.setWidget(view);

		ClientFactory.INSTANCE.getCoursesService().getCourses(new AsyncCallback<List<CourseDTO>>() {
			public void onFailure(Throwable caught) {
				ClientFactory.INSTANCE.getRPCFaultDialog().show(caught);
			}

			public void onSuccess(List<CourseDTO> courses) {
				ClientFactory.INSTANCE.getAllCoursesView().setData(courses);
			}
		});
	}

}
