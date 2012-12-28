package ru.abelitsky.memorize.client.activity;

import java.util.List;

import ru.abelitsky.memorize.client.ClientFactory;
import ru.abelitsky.memorize.client.place.CurrentCoursesPlace;
import ru.abelitsky.memorize.client.view.CurrentCoursesView;
import ru.abelitsky.memorize.client.view.CurrentCoursesView.Presenter;
import ru.abelitsky.memorize.shared.dto.CourseInfo;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class CurrentCoursesActivity extends AbstractActivity implements Presenter {

	private ClientFactory clientFactory;

	public CurrentCoursesActivity(CurrentCoursesPlace place, ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		CurrentCoursesView view = clientFactory.getCurrentCoursesView();
		view.setPresenter(this);
		panel.setWidget(view);

		clientFactory.getCoursesService().getStatuses(new AsyncCallback<List<CourseInfo>>() {
			public void onFailure(Throwable caught) {
				clientFactory.getRPCFaultDialog().show(caught);
			}

			public void onSuccess(List<CourseInfo> courseInfo) {
				clientFactory.getCurrentCoursesView().setData(courseInfo);
			}
		});
	}

	@Override
	public void goTo(Place place) {
		clientFactory.getPlaceController().goTo(place);
	}

}
