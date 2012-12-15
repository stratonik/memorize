package ru.abelitsky.memorize.client.activity;

import ru.abelitsky.memorize.client.ClientFactory;
import ru.abelitsky.memorize.client.place.LoadWordsPlace;
import ru.abelitsky.memorize.client.place.ViewCoursePlace;
import ru.abelitsky.memorize.client.place.ViewCoursePlace.BackPlace;
import ru.abelitsky.memorize.client.view.LoadWordsView;
import ru.abelitsky.memorize.client.view.LoadWordsView.Presenter;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class LoadWordsActivity extends AbstractActivity implements Presenter {

	private ClientFactory clientFactory;

	private Long courseId;
	private BackPlace backPlace;

	public LoadWordsActivity(LoadWordsPlace place, ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
		this.courseId = place.getCourseId();
		this.backPlace = place.getBackPlace();
	}

	@Override
	public Place getBackPlace() {
		return new ViewCoursePlace(courseId, backPlace);
	}

	@Override
	public void goTo(Place place) {
		clientFactory.getPlaceController().goTo(place);
	}

	@Override
	public void load(String data) {
		clientFactory.getLoadingDialog().center();
		clientFactory.getCoursesService().loadWords(courseId, data,
				new AsyncCallback<Void>() {
					@Override
					public void onFailure(Throwable caught) {
						clientFactory.getLoadingDialog().hide();
						clientFactory.getRPCFaultDialog().show(caught);
					}

					@Override
					public void onSuccess(Void result) {
						clientFactory.getLoadingDialog().hide();
						goTo(getBackPlace());
					}
				});
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		LoadWordsView view = clientFactory.getLoadWordsView();
		view.setPresenter(this);
		view.prepareView();
		panel.setWidget(view);
	}

}
