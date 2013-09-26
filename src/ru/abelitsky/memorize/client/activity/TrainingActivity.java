package ru.abelitsky.memorize.client.activity;

import java.util.List;

import ru.abelitsky.memorize.client.ClientFactory;
import ru.abelitsky.memorize.client.place.ParameterNames;
import ru.abelitsky.memorize.client.place.TrainingPlace;
import ru.abelitsky.memorize.client.view.TrainingView;
import ru.abelitsky.memorize.client.view.TrainingView.Presenter;
import ru.abelitsky.memorize.shared.dto.TrainingTest;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class TrainingActivity extends AbstractActivity implements Presenter {

	private TrainingPlace place;

	public TrainingActivity(TrainingPlace place) {
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
	public void saveResult(String wordStatusKey, boolean pass) {
		if (wordStatusKey != null) {
			if (pass) {
				ClientFactory.INSTANCE.getTrainingService().pass(wordStatusKey,
						new AsyncCallback<Void>() {
							@Override
							public void onSuccess(Void result) {
								// Ничего не делаем
							}

							@Override
							public void onFailure(Throwable caught) {
								// Ничего не делаем
							}
						});
			} else {
				ClientFactory.INSTANCE.getTrainingService().fail(wordStatusKey,
						new AsyncCallback<Void>() {
							@Override
							public void onSuccess(Void result) {
								// Ничего не делаем
							}

							@Override
							public void onFailure(Throwable caught) {
								// Ничего не делаем
							}
						});
			}
		}
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		TrainingView view = ClientFactory.INSTANCE.getTrainingView();
		view.setPresenter(this);
		panel.setWidget(view);

		view.prepareView();
		if (ParameterNames.ADD_NEW_WORDS.equals(place.getMode())) {
			ClientFactory.INSTANCE.getTrainingService().addWordsToTraining(
					place.getCourseStatusId(), new AsyncCallback<List<TrainingTest>>() {
						@Override
						public void onFailure(Throwable caught) {
							ClientFactory.INSTANCE.getRPCFaultDialog().show(caught);
						}

						@Override
						public void onSuccess(List<TrainingTest> result) {
							ClientFactory.INSTANCE.getTrainingView().setData(result);
						}
					});
		} else {
			ClientFactory.INSTANCE.getTrainingService().getWordsForTraining(
					place.getCourseStatusId(), new AsyncCallback<List<TrainingTest>>() {
						@Override
						public void onFailure(Throwable caught) {
							ClientFactory.INSTANCE.getRPCFaultDialog().show(caught);
						}

						@Override
						public void onSuccess(List<TrainingTest> result) {
							ClientFactory.INSTANCE.getTrainingView().setData(result);
						}
					});
		}
	}

}
