package ru.abelitsky.memorize.client.activity;

import ru.abelitsky.memorize.client.ClientFactory;
import ru.abelitsky.memorize.client.place.LoadWordsPlace;
import ru.abelitsky.memorize.client.view.LoadWordsView;
import ru.abelitsky.memorize.client.view.LoadWordsView.Presenter;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class LoadWordsActivity extends AbstractActivity implements Presenter {

	private LoadWordsPlace place;

	public LoadWordsActivity(LoadWordsPlace place) {
		this.place = place;
	}

	@Override
	public Place getBackPlace() {
		return place.getBackPlace();
	}

	@Override
	public void goTo(Place place) {
		ClientFactory.INSTANCE.getPlaceController().goTo(place);
	}

	@Override
	public void load(String data) {
		ClientFactory.INSTANCE.getLoadingDialog().center();
		ClientFactory.INSTANCE.getCoursesService().importWords(place.getCourseId(), data,
				new AsyncCallback<Void>() {
					@Override
					public void onFailure(Throwable caught) {
						ClientFactory.INSTANCE.getLoadingDialog().hide();
						ClientFactory.INSTANCE.getRPCFaultDialog().show(caught);
					}

					@Override
					public void onSuccess(Void result) {
						ClientFactory.INSTANCE.getLoadingDialog().hide();
						goTo(getBackPlace());
					}
				});
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		LoadWordsView view = ClientFactory.INSTANCE.getLoadWordsView();
		view.setPresenter(this);
		view.prepareView();
		panel.setWidget(view);
	}

}
